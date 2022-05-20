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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.BasicNotifierImpl;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
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
	public long getLastModificationTimestamp() {
		return lastModificationTimestamp;
	}

	@Override
	public void setLastModificationTimestamp(final long newLastModificationTimestamp) {
		lastModificationTimestamp = newLastModificationTimestamp;
	}

	@Override
	public LibraryElement getType() {
		if (getFile() != null) {
			if (type == null) {
				reloadType();
			} else if (isFileContentChanged()) {
				reloadType();
				// reset editable type
				setTypeEditable(null);
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
	public void setType(final LibraryElement newType) {
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
	public LibraryElement getTypeEditable() {
		if ((getFile() != null) && (typeEditable == null || isFileContentChanged())) {
			// if the editable type is null load it from the file and set a copy
			setTypeEditable(EcoreUtil.copy(getType()));
		}
		return typeEditable;
	}

	@Override
	public void setTypeEditable(final LibraryElement newTypeEditable) {
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

}
