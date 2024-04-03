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
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.ConcurrentNotifierImpl;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResource;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractTypeEntryImpl extends ConcurrentNotifierImpl implements TypeEntry, Adapter.Internal {

	private static class TypeEntryNotificationImpl extends NotificationImpl {
		protected final TypeEntry notifier;
		protected final String feature;

		public TypeEntryNotificationImpl(final TypeEntry notifier, final int eventType, final String feature,
				final Object oldValue, final Object newValue) {
			super(eventType, oldValue, newValue, NO_INDEX);
			this.notifier = notifier;
			this.feature = feature;
		}

		@Override
		public TypeEntry getNotifier() {
			return notifier;
		}

		@Override
		public Object getFeature() {
			return feature;
		}

	}

	private static final Pattern TYPE_NAME_PATTERN = Pattern.compile("Name=\\\"(\\w*)\\\""); //$NON-NLS-1$
	private static final Pattern TYPE_COMMENT_PATTERN = Pattern.compile("Comment=\\\"([^\"]*)\\\""); //$NON-NLS-1$
	private static final Pattern TYPE_PACKAGE_NAME_PATTERN = Pattern.compile("packageName=\\\"([\\w:]*)\\\""); //$NON-NLS-1$

	private IFile file;
	private String typeName;
	private String fullTypeName;
	private final AtomicReference<String> comment = new AtomicReference<>();

	private long lastModificationTimestamp = IResource.NULL_STAMP;

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
		if (eNotificationRequired()) {
			eNotify(new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_FILE_FEATURE, oldFile,
					file));
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
	public final synchronized long getLastModificationTimestamp() {
		return lastModificationTimestamp;
	}

	@Override
	public final synchronized void setLastModificationTimestamp(final long newLastModificationTimestamp) {
		lastModificationTimestamp = newLastModificationTimestamp;
	}

	@Override
	public synchronized LibraryElement getType() {
		if (typeRef != null) {
			final LibraryElement type = typeRef.get();
			if (type != null && !isFileContentChanged()) {
				return type;
			}
		}
		return reloadType();
	}

	private LibraryElement reloadType() {
		// reset editable type to force a fresh copy the next time the editable type is
		// accessed
		// also needs to happen before the reload, since SystemEntry delegates to
		// setType,
		// which would otherwise reset the freshly reloaded type
		if (getFile() != null) {
			setTypeEditable(null);
			lastModificationTimestamp = getFile().getModificationStamp();
			final LibraryElement loadType = loadType();
			setType(loadType);
			return loadType;
		}
		return null;
	}

	private boolean isFileContentChanged() {
		return getFile() != null && getFile().getModificationStamp() != IResource.NULL_STAMP
				&& getFile().getModificationStamp() != lastModificationTimestamp;
	}

	@Override
	public synchronized void setType(final LibraryElement newType) {
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
			eNotify(new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_TYPE_FEATURE, oldType,
					newType));
		}
	}

	protected void encloseInResource(final LibraryElement newType) {
		if (newType.eResource() == null) {
			new FordiacTypeResource(getURI()).getContents().add(newType);
		}
	}

	@Override
	public synchronized LibraryElement getTypeEditable() {
		final LibraryElement typeEditable = getTypeEditableFromRef();
		if (typeEditable != null && !isFileContentChanged()) {
			return typeEditable;
		}

		// we need to get a fresh type editable in order to ensure consistency take a
		// copy of the none editable type
		final LibraryElement loadType = EcoreUtil.copy(getType());
		setTypeEditable(loadType);
		return loadType;
	}

	private LibraryElement getTypeEditableFromRef() {
		return (typeEditableRef != null) ? typeEditableRef.get() : null;
	}

	@Override
	public synchronized void setTypeEditable(final LibraryElement newTypeEditable) {
		final LibraryElement oldTypeEditable = (typeEditableRef != null) ? typeEditableRef.get() : null;
		if (newTypeEditable != null) {
			encloseInResource(newTypeEditable);
			newTypeEditable.setTypeEntry(this);
			typeEditableRef = new SoftReference<>(newTypeEditable);
		} else {
			typeEditableRef = null;
		}
		if (eNotificationRequired()) {
			eNotify(new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_TYPE_EDITABLE_FEATURE,
					oldTypeEditable, newTypeEditable));
		}
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
			synchronized (this) {

				setType(null);
				setTypeEditable(null);

				if (notification.getNewValue() != null && notification.getOldValue() != null) {
					// if there is an editor opened then this notification will be delagted to the
					// corresponding editor,
					// If not, then nothing will happen
					delagateNotifiactionToEditor(notification);
				}

			}
		}
	}

	private void delagateNotifiactionToEditor(final Notification notification) {
		if (eNotificationRequired() && notification.getNewValue() instanceof final LibraryElement element) {
			eNotify(new TypeEntryNotificationImpl(this, Notification.SET,
					TypeEntry.TYPE_ENTRY_EDITOR_INSTANCE_UPDATE_FEATURE, notification.getOldValue(),
					element.getTypeEntry()));
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
					oldTypeLibrary, newTypeLibrary));
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
	public synchronized void refresh() {
		if (isFileContentChanged()) {
			loadTypeNameFromFile();
			setType(null);
			if (eNotificationRequired()) {
				eNotify(new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_FILE_CONTENT_FEATURE,
						null, null));
			}
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
		result.append(')');
		return result.toString();
	}

	protected final void setUpdateTypeOnSave(final boolean updateTypeOnSave) {
		this.updateTypeOnSave = updateTypeOnSave;
	}

	private synchronized void writeToFile(final InputStream fileContent, final AbstractTypeExporter exporter,
			final IProgressMonitor monitor) throws CoreException {
		// writing to the file and setting the time stamp need to be atomic
		if (getFile().exists()) {
			getFile().setContents(fileContent, IResource.KEEP_HISTORY | IResource.FORCE, monitor);
		} else {
			checkAndCreateFolderHierarchy(getFile(), monitor);
			getFile().create(fileContent, IResource.KEEP_HISTORY | IResource.FORCE, monitor);
		}
		// "reset" the modification timestamp in the TypeEntry to avoid reload - as
		// for
		// this timestamp it is not necessary as the data is in memory
		setLastModificationTimestamp(getFile().getModificationStamp());
		updateDependencies(exporter.getDependencies());

		final LibraryElement currentTypeEditable = getTypeEditableFromRef();
		if (exporter.getType() != currentTypeEditable) {
			setTypeEditable(exporter.getType());
		}

		if (updateTypeOnSave) {
			// make the edit result available for the reading entities
			setType(EcoreUtil.copy(exporter.getType()));
		}
	}

	/**
	 * Check if the folders in the file's path exist and if not create them
	 * accordingly
	 *
	 * @param file    for which the path should be checked
	 * @param monitor
	 * @throws CoreException
	 */
	private static void checkAndCreateFolderHierarchy(final IFile file, final IProgressMonitor monitor)
			throws CoreException {
		final IContainer container = file.getParent();
		if (!container.exists() && container instanceof final IFolder folder) {
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
}
