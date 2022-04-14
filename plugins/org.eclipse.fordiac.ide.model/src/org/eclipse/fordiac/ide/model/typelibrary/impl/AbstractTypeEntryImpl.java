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
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResource;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractTypeEntryImpl implements TypeEntry {

	protected IFile file;

	protected long lastModificationTimestamp = IResource.NULL_STAMP;

	protected LibraryElement type;
	protected LibraryElement typeEditable;

	protected TypeLibrary typeLibray;

	@Override
	public IFile getFile() {
		return file;
	}

	@Override
	public void setFile(final IFile newFile) {
		file = newFile;
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
		type = newType;
		if (newType != null) {
			encloseInResource(newType);
			newType.setTypeEntry(this);
		}
	}

	private void encloseInResource(final LibraryElement newType) {
		final IFile file = getFile();
		if (file != null) {
			final IPath path = file.getFullPath();
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
		typeEditable = newTypeEditable;
		if (newTypeEditable != null) {
			encloseInResource(newTypeEditable);
			newTypeEditable.setTypeEntry(this);
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

}
