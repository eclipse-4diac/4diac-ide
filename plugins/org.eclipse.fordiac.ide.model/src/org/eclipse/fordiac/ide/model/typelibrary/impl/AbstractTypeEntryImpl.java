/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
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

import javax.xml.stream.XMLStreamException;

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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.ConcurrentNotifierImpl;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResource;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractTypeEntryImpl extends ConcurrentNotifierImpl implements TypeEntry, Adapter.Internal {

	private static class TypeEntryNotificationImpl extends NotificationImpl {
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
	private String typeName;
	private String fullTypeName;
	private final AtomicReference<String> comment = new AtomicReference<>();

	private final AtomicLong lastModificationTimestamp = new AtomicLong(IResource.NULL_STAMP);
	private final AtomicLong lastModificationTimestampEditable = new AtomicLong(IResource.NULL_STAMP);

	private SoftReference<LibraryElement> typeRef;
	private SoftReference<LibraryElement> typeEditableRef;
	private final AtomicReference<Set<TypeEntry>> dependencies = new AtomicReference<>(Collections.emptySet());

	private TypeLibrary typeLibrary;

	private boolean updateTypeOnSave = true;

	@Override
	public IFile getFile() {
		return file;
	}

	@Override
	public void setFile(final IFile newFile) {
		if (typeLibrary != null) {
			throw new IllegalStateException("Cannot change file while added to type library"); //$NON-NLS-1$
		}
		final IFile oldFile = file;
		file = newFile;
		final LibraryElement type = basicGetType();
		if (type != null) {
			type.eResource().setURI(getURI());
		}
		final LibraryElement typeEditable = basicGetTypeEditable();
		if (typeEditable != null) {
			typeEditable.eResource().setURI(getURI());
		}
		if (eNotificationRequired()) {
			eNotify(new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_FILE_FEATURE,
					TypeEntry.TYPE_ENTRY_FILE_FEATURE_ID, oldFile, file));
		}
	}

	@Override
	public String getTypeName() {
		if (typeName == null) {
			loadTypeNameFromFile();
		}
		return typeName;
	}

	private void setTypeName(final String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String getFullTypeName() {
		if (fullTypeName == null) {
			loadTypeNameFromFile();
		}
		return fullTypeName;
	}

	private void setFullTypeName(final String fullTypeName) {
		if (!Objects.equals(this.fullTypeName, fullTypeName)) {
			if (typeLibrary != null && this.fullTypeName != null) {
				typeLibrary.removeTypeEntryNameReference(this);
			}
			this.fullTypeName = fullTypeName;
			if (typeLibrary != null && fullTypeName != null) {
				typeLibrary.addTypeEntryNameReference(this);
			}
		}
	}

	@Override
	public String getComment() {
		String result = comment.get();
		if (result == null) {
			result = loadTypeCommentFromFile();
			comment.compareAndSet(null, result); // only set if still null
			// we do not care about a (slightly) stale result here
		}
		return result;
	}

	@Override
	public final long getLastModificationTimestamp() {
		return lastModificationTimestamp.get();
	}

	@Override
	public final void setLastModificationTimestamp(final long newLastModificationTimestamp) {
		lastModificationTimestamp.set(newLastModificationTimestamp);
		lastModificationTimestampEditable.set(newLastModificationTimestamp);
	}

	@Override
	public LibraryElement getType() {
		// check if type is present and current
		LibraryElement type = basicGetType();
		if (type != null && !isFileContentChanged()) {
			return type; // simple, non-contended case
		}

		// the hard way
		NotificationChain notifications = null;
		synchronized (this) {
			// check again
			type = basicGetType();
			if (type != null && !isFileContentChanged()) {
				return type; // concurrent update
			}

			// get and check file
			final IFile fileCached = getFile();
			if (fileCached == null) {
				return null; // no file, no type
			}

			// _we_ need to (re-)load the type

			// read modification stamp at the beginning to ensure the loaded type is at
			// least as recent as the read modification stamp
			final long modificationStamp = fileCached.getModificationStamp();

			// load and set the type
			type = loadType();
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

	private LibraryElement basicGetType() {
		final SoftReference<LibraryElement> typeRefCached = typeRef;
		return typeRefCached != null ? typeRefCached.get() : null;
	}

	private boolean isFileContentChanged() {
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
			setTypeName(newType.getName());
			setFullTypeName(PackageNameHelper.getFullTypeName(newType));
			comment.set(newType.getComment());
			typeRef = new SoftReference<>(newType);
		} else {
			typeRef = null;
		}
		if (eNotificationRequired()) {
			notifications = chainNotification(notifications, new TypeEntryNotificationImpl(this, Notification.SET,
					TypeEntry.TYPE_ENTRY_TYPE_FEATURE, TypeEntry.TYPE_ENTRY_TYPE_FEATURE_ID, oldType, newType));
		}
		return notifications;
	}

	protected void encloseInResource(final LibraryElement newType) {
		if (newType.eResource() == null) {
			new FordiacTypeResource(getURI()).getContents().add(newType);
		}
	}

	/**
	 * @deprecated see {@link TypeEntry#getTypeEditable()}
	 */
	@Override
	@Deprecated(since = "3.0.0", forRemoval = true)
	public LibraryElement getTypeEditable() {
		// check if type is present and current
		LibraryElement typeEditable = basicGetTypeEditable();
		if (typeEditable != null && !isFileContentChangedEditable()) {
			return typeEditable; // simple, non-contended case
		}

		// the hard way
		NotificationChain notifications = null;
		synchronized (this) {
			// check again
			typeEditable = basicGetTypeEditable();
			if (typeEditable != null && !isFileContentChangedEditable()) {
				return typeEditable; // concurrent update
			}

			// get and check file
			final IFile fileCached = getFile();
			if (fileCached == null) {
				return null; // no file, no type
			}

			// _we_ need to get a fresh type editable

			final long modificationStamp;

			// try loaded type first
			final LibraryElement type = basicGetType();
			if (type != null && !isFileContentChanged()) {
				// read modification stamp at the beginning to ensure the copied type is at
				// least as recent as the modification stamp
				modificationStamp = lastModificationTimestamp.get();

				typeEditable = EcoreUtil.copy(type);
			} else { // load a fresh copy ourselves
				// read modification stamp at the beginning to ensure the loaded type is at
				// least as recent as the read modification stamp
				modificationStamp = fileCached.getModificationStamp();

				typeEditable = loadType();
			}

			// set the type editable
			notifications = basicSetTypeEditable(typeEditable, notifications);

			// update the last modification stamp _after_ setting the type to ensure other
			// readers see the new stamp only together with the new type
			lastModificationTimestampEditable.set(modificationStamp);
		}
		// dispatch notifications
		if (notifications != null) {
			notifications.dispatch();
		}
		return typeEditable;
	}

	private LibraryElement basicGetTypeEditable() {
		final SoftReference<LibraryElement> typeEditableRefCached = typeEditableRef;
		return typeEditableRefCached != null ? typeEditableRefCached.get() : null;
	}

	private boolean isFileContentChangedEditable() {
		final IFile fileCached = getFile();
		if (fileCached != null) {
			final long modificationStamp = fileCached.getModificationStamp();
			return modificationStamp != IResource.NULL_STAMP
					&& modificationStamp != lastModificationTimestampEditable.get();
		}
		return false;
	}

	/**
	 * @deprecated see {@link TypeEntry#setTypeEditable(LibraryElement)}
	 */
	@Override
	@Deprecated(since = "3.0.0", forRemoval = true)
	public void setTypeEditable(final LibraryElement newTypeEditable) {
		final NotificationChain notifications = basicSetTypeEditable(newTypeEditable, null);
		if (notifications != null) {
			notifications.dispatch();
		}
	}

	private synchronized NotificationChain basicSetTypeEditable(final LibraryElement newTypeEditable,
			NotificationChain notifications) {
		final LibraryElement oldTypeEditable = (typeEditableRef != null) ? typeEditableRef.get() : null;
		if (newTypeEditable != null) {
			encloseInResource(newTypeEditable);
			newTypeEditable.setTypeEntry(this);
			typeEditableRef = new SoftReference<>(newTypeEditable);
		} else {
			typeEditableRef = null;
		}
		if (eNotificationRequired()) {
			notifications = chainNotification(notifications,
					new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_TYPE_EDITABLE_FEATURE,
							TypeEntry.TYPE_ENTRY_TYPE_EDITABLE_FEATURE_ID, oldTypeEditable, newTypeEditable));
		}
		return notifications;
	}

	private LibraryElement loadType() {
		try {
			final CommonElementImporter importer = getImporter();
			importer.loadElement();
			updateDependencies(importer.getDependencies());
			final LibraryElement retval = importer.getElement();
			retval.setTypeEntry(this);
			return retval;
		} catch (IOException | XMLStreamException | TypeImportException e) {
			FordiacLogHelper.logWarning("Error loading type " + getFile().getName() + ": " + e.getMessage(), e); //$NON-NLS-1$ //$NON-NLS-2$
			return null;
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

	private void updateDependencies(final Set<TypeEntry> dependencies) {
		final Set<TypeEntry> oldDependencies = this.dependencies.getAndSet(Set.copyOf(dependencies));
		oldDependencies.stream().filter(Predicate.not(dependencies::contains))
				.forEachOrdered(entry -> entry.eAdapters().remove(this));
		dependencies.stream().filter(Predicate.not(oldDependencies::contains))
				.forEachOrdered(entry -> entry.eAdapters().add(this));
	}

	@Override
	public void notifyChanged(final Notification notification) {
		if ((notification.getFeature() == TypeEntry.TYPE_ENTRY_TYPE_FEATURE
				|| notification.getFeature() == TypeEntry.TYPE_ENTRY_TYPE_LIBRARY_FEATURE)
				&& dependencies.get().contains(notification.getNotifier())) {

			NotificationChain notifications = null;
			synchronized (this) {
				if (!getTypeEClass().equals(LibraryElementPackage.Literals.AUTOMATION_SYSTEM)) {
					// currently we only want to purge the models if it is not an automation system,
					// as it is used as identification in system explorer and monitoring
					// FIXME remove if when monitoring is fixed
					notifications = basicSetType(null, notifications);
					notifications = basicSetTypeEditable(null, notifications);
				}

				// if there is an editor opened then this notification will be delegated to the
				// corresponding editor.
				// If not, then nothing will happen
				notifications = delegateNotificationToEditor(notification, notifications);
			}

			if (notifications != null) {
				notifications.dispatch();
			}
		}
	}

	private NotificationChain delegateNotificationToEditor(final Notification notification,
			NotificationChain notifications) {
		if (eNotificationRequired()) {
			notifications = chainNotification(notifications,
					new TypeEntryNotificationImpl(this, Notification.SET,
							TypeEntry.TYPE_ENTRY_EDITOR_INSTANCE_UPDATE_FEATURE,
							TypeEntry.TYPE_ENTRY_EDITOR_INSTANCE_UPDATE_FEATURE_ID, null, notification.getNotifier()));
		}
		return notifications;
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
			// check content changed
			if (isFileContentChanged()) {
				// load type name
				loadTypeNameFromFile();
				// clear cached type
				notifications = basicSetType(null, notifications);
				// also notify changed contents
				if (eNotificationRequired()) {
					notifications = chainNotification(notifications,
							new TypeEntryNotificationImpl(this, Notification.SET,
									TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE,
									TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE_ID, null, null));
				}
			}
		}
		// dispatch notifications
		if (notifications != null) {
			notifications.dispatch();
		}
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

	protected final void setUpdateTypeOnSave(final boolean updateTypeOnSave) {
		this.updateTypeOnSave = updateTypeOnSave;
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

			// set type editable (if different)
			final LibraryElement currentTypeEditable = basicGetTypeEditable();
			if (exporter.getType() != currentTypeEditable) {
				notifications = basicSetTypeEditable(exporter.getType(), notifications);
			}

			// update the last modification stamp editable _after_ setting the type editable
			// to ensure readers see the new stamp only together with the new type editable
			lastModificationTimestampEditable.set(modificationStamp);

			// set type (if requested)
			if (updateTypeOnSave) {
				// make the edit result available for the reading entities
				notifications = basicSetType(EcoreUtil.copy(exporter.getType()), notifications);
			}

			// update the last modification stamp _after_ setting the type to ensure other
			// readers see the new stamp only together with the new type
			lastModificationTimestamp.set(modificationStamp);
		}
		// dispatch notifications
		if (notifications != null) {
			notifications.dispatch();
		}
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

	private void loadTypeNameFromFile() {
		if (getFile() != null) {
			if (getFile().exists()) {
				try (Scanner scanner = new Scanner(getFile().getContents())) {
					if (scanner.findWithinHorizon(TYPE_NAME_PATTERN, 0) != null) {
						setTypeName(scanner.match().group(1));
						if (scanner.findWithinHorizon(TYPE_PACKAGE_NAME_PATTERN, 0) != null) {
							final String packageName = scanner.match().group(1);
							setFullTypeName(packageName + "::" + typeName); //$NON-NLS-1$
						} else {
							setFullTypeName(typeName);
						}
						return;
					}
				} catch (final Exception e) {
					FordiacLogHelper.logWarning(e.getMessage(), e);
				}
			}
			setTypeName(TypeEntry.getTypeNameFromFile(getFile()));
		} else {
			setTypeName(""); //$NON-NLS-1$
		}
		setFullTypeName(typeName);
	}

	private String loadTypeCommentFromFile() {
		if (getFile() != null && getFile().exists()) {
			try (Scanner scanner = new Scanner(getFile().getContents())) {
				if (scanner.findWithinHorizon(TYPE_COMMENT_PATTERN, 0) != null) {
					return scanner.match().group(1);
				}
			} catch (final Exception e) {
				FordiacLogHelper.logWarning(e.getMessage(), e);
			}
		}
		return ""; //$NON-NLS-1$
	}

	private static NotificationChain chainNotification(final NotificationChain notifications,
			final TypeEntryNotificationImpl notification) {
		if (notifications == null) {
			return notification;
		}
		notifications.add(notification);
		return notifications;
	}
}
