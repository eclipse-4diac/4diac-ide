/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorDataTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class ErrorDataTypeEntryImpl extends AbstractTypeEntryImpl implements ErrorDataTypeEntry {
	@Override
	public synchronized ErrorMarkerDataType getType() {
		final LibraryElement type = super.getType();
		if (type instanceof final ErrorMarkerDataType dataType) {
			return dataType;
		}
		return null;
	}

	@Override
	public synchronized ErrorMarkerDataType getTypeEditable() {
		final LibraryElement type = super.getTypeEditable();
		if (type instanceof final ErrorMarkerDataType dataType) {
			return dataType;
		}
		return null;
	}

	@Override
	public synchronized void setType(final LibraryElement type) {
		if (type instanceof ErrorMarkerDataType) {
			super.setType(type);
		} else {
			super.setType(null);
			if (null != type) {
				FordiacLogHelper.logError("tried to set no ErrorMarkerDataType as type entry for ErrorDataTypeEntry"); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected void encloseInResource(final LibraryElement newType) {
		new ResourceImpl(URI.createFileURI(newType.getName() + ".datalib.dtp")).getContents().add(newType); //$NON-NLS-1$
	}

	@Override
	protected CommonElementImporter getImporter() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected AbstractTypeExporter getExporter() {
		throw new UnsupportedOperationException();
	}
}
