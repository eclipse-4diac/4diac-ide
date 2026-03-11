/*******************************************************************************
 * Copyright (c) 2008, 2025 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 *    Alois Zoitl  - turned the Palette model into POJOs
 *                 - added library element hash
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.ConcurrentNotifierImpl;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorLibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResource;
import org.eclipse.fordiac.ide.model.typelibrary.InterfaceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.model.util.LibraryElementHashException;
import org.eclipse.fordiac.ide.model.util.LibraryElementHasher;
import org.eclipse.fordiac.ide.model.value.StringValueConverter;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractTypeEntryImpl extends ConcurrentNotifierImpl implements TypeEntry, Adapter.Internal {

	protected static class TypeEntryNotificationImpl extends NotificationImpl {
		protected final TypeEntry notifier;
		protected final String feature;
		protected final int featureID;

		public TypeEntryNotificationImpl(final TypeEntry notifier, final int eventType, final String feature,
				final int featureID, final Object oldValue, final Object newValue) {
			super(eventType, oldValue, newValue, NO_INDEX);
			this.notifier = notifier;
			this.feature = feature;
			this.featureID = featureID;
		}

		@Override
		public TypeEntry getNotifier() {
			return notifier;
		}

		@Override
		public Object getFeature() {
			return feature;
		}

		@Override
		public int getFeatureID(final Class<?> expectedClass) {
			return featureID;
		}
	}

	private static final Pattern TYPE_NAME_PATTERN = Pattern.compile("Name=\\\"(\\w*)\\\""); //$NON-NLS-1$
	private static final Pattern TYPE_COMMENT_PATTERN = Pattern.compile("Comment=\\\"([^\"]*)\\\""); //$NON-NLS-1$
	private static final Pattern TYPE_PACKAGE_NAME_PATTERN = Pattern.compile("packageName=\\\"([\\w:]*)\\\""); //$NON-NLS-1$

	private IFile file;
	private String fullTypeName;
	private final AtomicReference<String> comment = new AtomicReference<>();

	private final AtomicLong lastModificationTimestamp = new AtomicLong(IResource.NULL_STAMP);
	private final AtomicLong lastModificationTimestampEditable = new AtomicLong(IResource.NULL_STAMP);

	private SoftReference<LibraryElement> typeRef;
	private SoftReference<String> typeHashRef;
	private final AtomicReference<Set<TypeEntry>> dependencies = new AtomicReference<>(Collections.emptySet());
	private boolean loading;

	private TypeLibrary typeLibrary;

	@Override
	public IFile getFile() {
		return file;
	}

	@Override
	public void setFile(final IFile newFile) {
		if (typeLibrary != null) {
			throw new IllegalStateException("Cannot change file while added to type library"); //$NON-NLS-1$
		}
		final NotificationChain notifications = basicSetFile(newFile, null);
		if (notifications != null) {
			notifications.dispatch();
		}
	}

	protected synchronized NotificationChain basicSetFile(final IFile newFile, NotificationChain notifications) {
		final IFile oldFile = file;
		if (Objects.equals(oldFile, newFile)) {
			return notifications; // skip if files are equal
		}

		// set file
		file = newFile;

		// set URL in type resource
		final LibraryElement type = basicGetType();
		if (type != null) {
			type.eResource().setURI(getURI());
		}

		if (eNotificationRequired()) {
			notifications = chainNotification(notifications, new TypeEntryNotificationImpl(this, Notification.SET,
					TypeEntry.TYPE_ENTRY_FILE_FEATURE, TypeEntry.TYPE_ENTRY_FILE_FEATURE_ID, oldFile, newFile));
		}
		return notifications;
	}

	@Override
	public String getTypeName() {
		return PackageNameHelper.extractPlainTypeName(getFullTypeName());
	}

	@Override
	public String getFullTypeName() {
		// check if type name is present
		String result = fullTypeName;
		if (result != null) {
			return result; // simple, non-contended case
		}

		// the hard way
		NotificationChain notifications = null;
		synchronized (this) {
			// check again
			result = fullTypeName;
			if (result != null) {
				return result; // concurrent update
			}

			// _we_ need to load the type name

			// load the type name
			result = loadTypeNameFromFile();

			// set type name
			notifications = basicSetFullTypeName(result, notifications);
		}

		// dispatch notifications
		if (notifications != null) {
			notifications.dispatch();
		}
		return result;
	}

	protected synchronized NotificationChain basicSetFullTypeName(final String newFullTypeName,
			NotificationChain notifications) {
		Objects.requireNonNull(newFullTypeName, "New full type name must not be null"); //$NON-NLS-1$
		final String oldFullTypeName = fullTypeName;
		if (Objects.equals(oldFullTypeName, newFullTypeName)) {
			return notifications; // skip if names are equal
		}

		// remove from type library (if name is set)
		if (typeLibrary != null && fullTypeName != null) {
			typeLibrary.removeTypeEntryNameReference(this);
		}

		// set type name
		fullTypeName = newFullTypeName;

		// add (back) to type library
		if (typeLibrary != null) {
			typeLibrary.addTypeEntryNameReference(this);
		}

		if (eNotificationRequired()) {
			notifications = chainNotification(notifications,
					new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_FULL_TYPE_NAME_FEATURE,
							TypeEntry.TYPE_ENTRY_FULL_TYPE_NAME_FEATURE_ID, oldFullTypeName, newFullTypeName));
		}
		return notifications;
	}

	@Override
	public String getComment() {
		// check if comment is present
		String result = comment.get();
		if (result != null) {
			return result;
		}

		// load comment from file
		NotificationChain notifications = null;
		result = loadTypeCommentFromFile();
		notifications = basicSetComment(result, notifications);

		// dispatch notifications
		if (notifications != null) {
			notifications.dispatch();
		}
		return result;
	}

	protected NotificationChain basicSetComment(final String newComment, NotificationChain notifications) {
		Objects.requireNonNull(newComment, "New comment must not be null"); //$NON-NLS-1$
		final String oldComment = comment.getAndSet(newComment);
		if (!Objects.equals(oldComment, newComment) && eNotificationRequired()) {
			notifications = chainNotification(notifications,
					new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_COMMENT_FEATURE,
							TypeEntry.TYPE_ENTRY_COMMENT_FEATURE_ID, oldComment, newComment));
		}
		return notifications;
	}

	@Override
	public LibraryElement getType() {
		// check if type is present and current
		LibraryElement type = basicGetType();
		if (type != null) {
			return type; // simple, non-contended case
		}

		// the hard way
		NotificationChain notifications = null;
		synchronized (this) {
			// check again
			type = basicGetType();
			if (type != null) {
				return type; // concurrent update
			}

			// _we_ need to (re-)load the type

			final long modificationStamp;
			final IFile fileCached = getFile();
			if (fileCached != null && fileCached.exists()) {
				// read modification stamp at the beginning to ensure the loaded type is at
				// least as recent as the read modification stamp
				modificationStamp = fileCached.getModificationStamp();

				// load the type
				type = loadType();
			} else {
				// set modification stamp to NULL_STAMP to ensure the type is reloaded as soon
				// as a file becomes available
				modificationStamp = IResource.NULL_STAMP;
			}

			// create error type if it could not be loaded (no file or error)
			if (type == null) {
				type = createErrorLibraryElement();
				PackageNameHelper.setFullTypeName(type, getFullTypeName());
			}

			// set type
			notifications = basicSetType(type, notifications);

			// update the last modification stamp _after_ setting the type to ensure other
			// readers see the new stamp only together with the new type
			lastModificationTimestamp.set(modificationStamp);
		}
		// dispatch notifications
		if (notifications != null) {
			notifications.dispatch();
		}
		return type;
	}

	protected LibraryElement basicGetType() {
		final SoftReference<LibraryElement> typeRefCached = typeRef;
		return typeRefCached != null ? typeRefCached.get() : null;
	}

	private final boolean isFileContentChanged() {
		final IFile fileCached = getFile();
		if (fileCached != null) {
			final long modificationStamp = fileCached.getModificationStamp();
			return modificationStamp != IResource.NULL_STAMP && modificationStamp != lastModificationTimestamp.get();
		}
		return false;
	}

	@Override
	public void setType(final LibraryElement newType) {
		final NotificationChain notifications = basicSetType(newType, null);
		if (notifications != null) {
			notifications.dispatch();
		}
	}

	protected synchronized NotificationChain basicSetType(final LibraryElement newType,
			NotificationChain notifications) {
		final LibraryElement oldType = (typeRef != null) ? typeRef.get() : null;
		if (newType != null) {
			Objects.requireNonNull(newType.getName(), "No name in new type"); //$NON-NLS-1$
			encloseInResource(newType);
			newType.setTypeEntry(this);
			notifications = basicSetFullTypeName(PackageNameHelper.getFullTypeName(newType), notifications);
			notifications = basicSetComment(newType.getComment(), notifications);
			typeRef = new SoftReference<>(newType);
		} else {
			typeRef = null;
		}
		typeHashRef = null; // our type is invalidated clear the hash
		if (eNotificationRequired()) {
			notifications = chainNotification(notifications, new TypeEntryNotificationImpl(this, Notification.SET,
					TypeEntry.TYPE_ENTRY_TYPE_FEATURE, TypeEntry.TYPE_ENTRY_TYPE_FEATURE_ID, oldType, newType));
		}
		return notifications;
	}

	protected void encloseInResource(final LibraryElement newType) {
		if (newType.eResource() == null) {
			new FordiacTypeResource(Objects.requireNonNullElseGet(getURI(),
					() -> URI.createFileURI(newType.getName() + "." + getFileExtension()))).getContents().add(newType); //$NON-NLS-1$
		}
	}

	private LibraryElement loadType() {
		if (loading) {
			FordiacLogHelper.logWarning("Circular dependency when loading type " + getFile().getName()); //$NON-NLS-1$
			return null;
		}
		try {
			loading = true;
			final CommonElementImporter importer = getImporter();
			importer.loadElement();
			updateDependencies(importer.getDependencies());
			final LibraryElement retval = importer.getElement();
			retval.setTypeEntry(this);
			return retval;
		} catch (final Exception e) {
			FordiacLogHelper.logWarning("Error loading type " + getFile().getName() + ": " + e.getMessage(), e); //$NON-NLS-1$ //$NON-NLS-2$
			return null;
		} finally {
			loading = false;
		}
	}

	@Override
	public LibraryElement copyType() {
		final LibraryElement copy = EcoreUtil.copy(getType());
		encloseInResource(copy);
		copy.setTypeEntry(this);
		return copy;
	}

	@Override
	public Set<TypeEntry> getDependencies() {
		if (getType() != null) { // ensure type is loaded
			return dependencies.get();
		}
		return Collections.emptySet();
	}

	@Override
	public String getTypeHash() throws LibraryElementHashException {
		final SoftReference<String> typeHashRefCached = typeHashRef;

		if (!isFileContentChanged() && typeHashRefCached != null) {
			final String typeHash = typeHashRefCached.get();
			if (typeHash != null) {
				return typeHash;
			}
		}

		final String newTypeHash = basicGetTypeHash();
		typeHashRef = new SoftReference<>(newTypeHash);
		return newTypeHash;
	}

	private String basicGetTypeHash() throws LibraryElementHashException {
		final LibraryElement type = getType();
		if (type == null) {
			return ""; //$NON-NLS-1$
		}
		final Attribute typeHashAttribute = getTypeHashAttribute(type);
		if (typeHashAttribute != null) {
			final String value = typeHashAttribute.getValue();
			if (value.isEmpty()) {
				return value;
			}
			return StringValueConverter.INSTANCE.toValue(value);
		}
		return LibraryElementHasher.hash(type);
	}

	private static Attribute getTypeHashAttribute(final LibraryElement type) {
		final Attribute typeHashAttribute = type.getAttribute(TypeLibraryTags.TYPE_HASH_ATTRIBUTE_FULL_NAME);
		if (typeHashAttribute != null) {
			return typeHashAttribute;
		}
		return type.getAttribute(TypeLibraryTags.TYPE_HASH_ATTRIBUTE_NAME);
	}

	private void updateDependencies(final Set<TypeEntry> dependencies) {
		final Set<TypeEntry> oldDependencies = this.dependencies.getAndSet(Set.copyOf(dependencies));
		oldDependencies.stream().filter(Predicate.not(dependencies::contains))
				.forEachOrdered(entry -> entry.eAdapters().remove(this));
		dependencies.stream().filter(Predicate.not(oldDependencies::contains))
				.forEachOrdered(entry -> entry.eAdapters().add(this));
	}

	@Override
	public void notifyChanged(final Notification notification) {
		if (((notification.getFeature() == TypeEntry.TYPE_ENTRY_TYPE_FEATURE
				&& !(notification.getNotifier() instanceof InterfaceTypeEntry))
				|| notification.getFeature() == TypeEntry.TYPE_ENTRY_INTERFACE_FEATURE
				|| notification.getFeature() == TypeEntry.TYPE_ENTRY_TYPE_LIBRARY_FEATURE)
				&& dependencies.get().contains(notification.getNotifier())) {

			NotificationChain notifications = null;
			synchronized (this) {
				if (basicGetType() != null) {
					notifications = basicSetType(null, notifications);
				}
			}
			if (notifications != null) {
				notifications.dispatch();
			}
		}
	}

	@Override
	public boolean isAdapterForType(final Object type) {
		return false;
	}

	@Override
	public Notifier getTarget() {
		return null;
	}

	@Override
	public void setTarget(final Notifier newTarget) {
		// do nothing
	}

	@Override
	public void unsetTarget(final Notifier oldTarget) {
		// do nothing
	}

	protected abstract CommonElementImporter getImporter();

	protected abstract ErrorLibraryElement createErrorLibraryElement();

	@Override
	public TypeLibrary getTypeLibrary() {
		return typeLibrary;
	}

	@Override
	public void setTypeLibrary(final TypeLibrary newTypeLibrary) {
		final TypeLibrary oldTypeLibrary = typeLibrary;
		typeLibrary = newTypeLibrary;
		if (eNotificationRequired()) {
			eNotify(new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_TYPE_LIBRARY_FEATURE,
					TypeEntry.TYPE_ENTRY_TYPE_LIBRARY_FEATURE_ID, oldTypeLibrary, newTypeLibrary));
		}
	}

	protected void doSaveInternal(final AbstractTypeExporter exporter, final IProgressMonitor monitor)
			throws CoreException {
		if (null != exporter) {
			final InputStream fileContent = exporter.getFileContent();
			if (fileContent != null) {
				try (fileContent) {
					writeToFile(fileContent, exporter, monitor);
				} catch (final IOException e) {
					throw new CoreException(Status.error(e.getMessage(), e));
				}
			}
		}
	}

	@Override
	public void refresh() {
		NotificationChain notifications = null;
		synchronized (this) {
			notifications = performTypeRefresh(notifications);
		}
		// dispatch notifications
		if (notifications != null) {
			notifications.dispatch();
		}
	}

	protected NotificationChain performTypeRefresh(NotificationChain notifications) {
		// check content changed
		if (isFileContentChanged()) {
			// load type name
			loadTypeNameFromFile();
			// clear cached type
			notifications = basicSetType(null, notifications);
			// also notify changed contents
			if (eNotificationRequired()) {
				notifications = chainNotification(notifications,
						new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE,
								TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE_ID, null, null));
			}
		}
		return notifications;
	}

	@Override
	public boolean hasError() {
		return file == null || !file.exists() || basicGetType() instanceof ErrorLibraryElement;
	}

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (label: "); //$NON-NLS-1$
		result.append(getFullTypeName());
		result.append(", file: "); //$NON-NLS-1$
		result.append(file);
		result.append(", lastModificationTimestamp: "); //$NON-NLS-1$
		result.append(lastModificationTimestamp);
		result.append(", lastModificationTimestampEditable: "); //$NON-NLS-1$
		result.append(lastModificationTimestampEditable);
		result.append(')');
		return result.toString();
	}

	private void writeToFile(final InputStream fileContent, final AbstractTypeExporter exporter,
			final IProgressMonitor monitor) throws CoreException {
		NotificationChain notifications = null;
		synchronized (this) {
			// get and check file
			final IFile fileCached = getFile();
			if (fileCached == null) {
				return; // no file, nothing to write
			}

			// write or create file
			if (fileCached.exists()) {
				fileCached.setContents(fileContent, IResource.KEEP_HISTORY | IResource.FORCE, monitor);
			} else {
				checkAndCreateFolderHierarchy(fileCached.getParent(), monitor);
				fileCached.create(fileContent, IResource.KEEP_HISTORY | IResource.FORCE, monitor);
			}
			updateDependencies(exporter.getDependencies());

			// get updated modification stamp
			final long modificationStamp = fileCached.getModificationStamp();

			// update the last modification stamp editable _after_ setting the type editable
			// to ensure readers see the new stamp only together with the new type editable
			lastModificationTimestampEditable.set(modificationStamp);

			notifications = updateTypeOnSave(exporter.getType(), notifications);

			// update the last modification stamp _after_ setting the type to ensure other
			// readers see the new stamp only together with the new type
			lastModificationTimestamp.set(modificationStamp);

			// send out file content notifications to update editors
			if (eNotificationRequired()) {
				notifications = chainNotification(notifications,
						new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE,
								TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE_ID, null, null));
			}
		}
		// dispatch notifications
		if (notifications != null) {
			notifications.dispatch();
		}
	}

	protected NotificationChain updateTypeOnSave(final LibraryElement savedType,
			final NotificationChain notifications) {
		// make the edit result available for the reading entities
		return basicSetType(EcoreUtil.copy(savedType), notifications);
	}

	/**
	 * Recursively check if the parents in the resource's path exist and if not
	 * create them
	 *
	 * @param container for which the path should be checked
	 * @param monitor
	 * @throws CoreException
	 */
	private static void checkAndCreateFolderHierarchy(final IContainer container, final IProgressMonitor monitor)
			throws CoreException {
		if (container == null || container.exists()) {
			return;
		}
		checkAndCreateFolderHierarchy(container.getParent(), monitor);
		if (container instanceof final IFolder folder) {
			folder.create(true, true, monitor);
			folder.refreshLocal(IResource.DEPTH_ZERO, monitor);
		}
	}

	private String loadTypeNameFromFile() {
		final IFile cachedFile = getFile();
		if (cachedFile != null) {
			if (cachedFile.exists()) {
				try (Scanner scanner = new Scanner(cachedFile.getContents())) {
					if (scanner.findWithinHorizon(TYPE_NAME_PATTERN, 0) != null) {
						final String foundTypeName = scanner.match().group(1);
						if (scanner.findWithinHorizon(TYPE_PACKAGE_NAME_PATTERN, 0) != null) {
							final String foundPackageName = scanner.match().group(1);
							return foundPackageName + "::" + foundTypeName; //$NON-NLS-1$
						}
						return foundTypeName;
					}
				} catch (final Exception e) {
					FordiacLogHelper.logWarning(e.getMessage(), e);
				}
			}
			return TypeEntry.getTypeNameFromFile(cachedFile);
		}
		return ""; //$NON-NLS-1$
	}

	private String loadTypeCommentFromFile() {
		final IFile cachedFile = getFile();
		if (cachedFile != null && cachedFile.exists()) {
			try (Scanner scanner = new Scanner(cachedFile.getContents())) {
				if (scanner.findWithinHorizon(TYPE_COMMENT_PATTERN, 0) != null) {
					return CommonElementImporter.fullyUnEscapeValue(scanner.match().group(1));
				}
			} catch (final Exception e) {
				FordiacLogHelper.logWarning(e.getMessage(), e);
			}
		}
		return ""; //$NON-NLS-1$
	}

	protected static NotificationChain chainNotification(final NotificationChain notifications,
			final TypeEntryNotificationImpl notification) {
		if (notifications == null) {
			return notification;
		}
		notifications.add(notification);
		return notifications;
	}
}
