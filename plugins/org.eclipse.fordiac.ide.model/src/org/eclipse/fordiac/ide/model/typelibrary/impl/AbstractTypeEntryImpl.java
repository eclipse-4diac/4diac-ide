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

import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.BasicNotifierImpl;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResource;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractTypeEntryImpl extends BasicNotifierImpl implements TypeEntry {

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

	private IFile file;

	private long lastModificationTimestamp = IResource.NULL_STAMP;

	protected LibraryElement type;
	private LibraryElement typeEditable;

	private TypeLibrary typeLibray;

	private BasicEList<Adapter> eAdapters;

	private boolean updateTypeOnSave = true;

	@Override
	public IFile getFile() {
		return file;
	}

	@Override
	public void setFile(final IFile newFile) {
		final IFile oldFile = file;
		file = newFile;
		if (eNotificationRequired()) {
			eNotify(new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_FILE_FEATURE, oldFile,
					file));
		}
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
		if (getFile() != null) {
			if (type == null) {
				reloadType();
			} else if (isFileContentChanged()) {
				// reset editable type to force a fresh copy the next time the editable type is accessed
				// also needs to happen before the reload, since SystemEntry delegates to setType,
				// which would otherwise reset the freshly reloaded type
				setTypeEditable(null);
				reloadType();
			}
		}
		return type;
	}

	private void reloadType() {
		lastModificationTimestamp = getFile().getModificationStamp();
		setType(loadType());
	}

	private boolean isFileContentChanged() {
		return getFile().getModificationStamp() != IResource.NULL_STAMP
				&& getFile().getModificationStamp() != lastModificationTimestamp;
	}

	@Override
	public synchronized void setType(final LibraryElement newType) {
		final LibraryElement oldType = type;
		type = newType;
		if (newType != null) {
			encloseInResource(newType);
			newType.setTypeEntry(this);
		}
		if (eNotificationRequired()) {
			eNotify(new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_TYPE_FEATURE, oldType,
					type));
		}
	}

	private void encloseInResource(final LibraryElement newType) {
		if (getFile() != null) {
			final IPath path = getFile().getFullPath();
			if (path != null) {
				new FordiacTypeResource(URI.createPlatformResourceURI(path.toString(), true)).getContents()
				.add(newType);
			}
		}
	}

	@Override
	public synchronized LibraryElement getTypeEditable() {
		if ((getFile() != null) && (typeEditable == null || isFileContentChanged())) {
			// if the editable type is null load it from the file and set a copy
			setTypeEditable(EcoreUtil.copy(getType()));
		}
		return typeEditable;
	}

	@Override
	public synchronized void setTypeEditable(final LibraryElement newTypeEditable) {
		final LibraryElement oldTypeEditable = typeEditable;
		typeEditable = newTypeEditable;
		if (newTypeEditable != null) {
			encloseInResource(newTypeEditable);
			newTypeEditable.setTypeEntry(this);
		}
		if (eNotificationRequired()) {
			eNotify(new TypeEntryNotificationImpl(this, Notification.SET, TypeEntry.TYPE_ENTRY_TYPE_EDITABLE_FEATURE,
					oldTypeEditable, type));
		}
	}

	private LibraryElement loadType() {
		final CommonElementImporter importer = getImporter();
		importer.loadElement();
		final LibraryElement retval = importer.getElement();

		if (null == retval) {
			FordiacLogHelper.logError("Error loading type: " + getFile().getName()); //$NON-NLS-1$
		} else {
			retval.setTypeEntry(this);
		}
		return retval;
	}

	protected abstract CommonElementImporter getImporter();

	@Override
	public TypeLibrary getTypeLibrary() {
		return typeLibray;
	}

	@Override
	public void setTypeLibrary(final TypeLibrary typeLib) {
		typeLibray = typeLib;
	}

	@Override
	public void save() {
		final AbstractTypeExporter exporter = getExporter();

		if (null != exporter) {
			final InputStream fileContent = exporter.getFileContent();
			if (fileContent != null) {
				final WorkspaceJob job = new WorkspaceJob("Save type file: " + getFile().getName()) {
					@Override
					public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
						try {
							try (fileContent) {
								writeToFile(fileContent, monitor);
							} catch (final CoreException e) {
								FordiacLogHelper.logError(e.getMessage(), e);
							}
						} catch (final Exception e) {
							FordiacLogHelper.logError(e.getMessage(), e);
						}
						return Status.OK_STATUS;
					}
				};
				job.setUser(false);
				job.setSystem(true);
				job.setPriority(Job.SHORT);
				job.setRule(getRuleScope());
				job.schedule();
			}
		}
	}

	protected abstract AbstractTypeExporter getExporter();

	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder(super.toString());
		result.append(" (label: "); //$NON-NLS-1$
		result.append(getTypeName());
		result.append(", file: "); //$NON-NLS-1$
		result.append(file);
		result.append(", lastModificationTimestamp: "); //$NON-NLS-1$
		result.append(lastModificationTimestamp);
		result.append(')');
		return result.toString();
	}

	@Override
	public EList<Adapter> eAdapters() {
		if (eAdapters == null) {
			eAdapters = new EAdapterList<>(this);
		}
		return eAdapters;
	}

	@Override
	protected BasicEList<Adapter> eBasicAdapters() {
		return eAdapters;
	}

	protected final void setUpdateTypeOnSave(final boolean updateTypeOnSave) {
		this.updateTypeOnSave = updateTypeOnSave;
	}

	/** Search for the first directory parent which is existing. If none can be found we will return the workspace root.
	 * This directory is then used as scheduling rule for locking the workspace. The direct parent of the entry's file
	 * can not be used as it may need to be created.
	 *
	 * @return the current folder or workspace root */
	private IContainer getRuleScope() {
		IContainer parent = getFile().getParent();
		while (parent != null && !parent.exists()) {
			parent = parent.getParent();
		}
		return (parent != null) ? parent : ResourcesPlugin.getWorkspace().getRoot();
	}

	private synchronized void writeToFile(final InputStream fileContent, final IProgressMonitor monitor)
			throws CoreException {
		// writing to the file and setting the time stamp need to be atomic
		if (getFile().exists()) {
			getFile().setContents(fileContent, IResource.KEEP_HISTORY | IResource.FORCE, monitor);
		} else {
			checkAndCreateFolderHierarchy(getFile(), monitor);
			getFile().create(fileContent, IResource.KEEP_HISTORY | IResource.FORCE, monitor);
		}
		// "reset" the modification timestamp in the TypeEntry to avoid reload - as for this
		// timestamp
		// it is not necessary as the data is in memory
		setLastModificationTimestamp(getFile().getModificationStamp());
		if (updateTypeOnSave) {
			// make the edit result available for the reading entities
			setType(EcoreUtil.copy(getTypeEditable()));
		}
	}

	/** Check if the folders in the file's path exist and if not create them accordingly
	 *
	 * @param file    for which the path should be checked
	 * @param monitor
	 * @throws CoreException */
	private static void checkAndCreateFolderHierarchy(final IFile file, final IProgressMonitor monitor)
			throws CoreException {
		final IContainer container = file.getParent();
		if (!container.exists() && container instanceof IFolder) {
			final IFolder folder = ((IFolder) container);
			folder.create(true, true, monitor);
			folder.refreshLocal(IResource.DEPTH_ZERO, monitor);
		}
	}

}
