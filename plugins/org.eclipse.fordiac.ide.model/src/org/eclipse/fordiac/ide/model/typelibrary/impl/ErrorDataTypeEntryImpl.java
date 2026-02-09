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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.dataexport.AbstractTypeExporter;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.ErrorDataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class ErrorDataTypeEntryImpl extends AbstractCheckedTypeEntryImpl<ErrorDataType> implements ErrorDataTypeEntry {

	public ErrorDataTypeEntryImpl() {
		super(ErrorDataType.class);
	}

	@Override
	public void save(final LibraryElement toSave, final IProgressMonitor monitor) throws CoreException {
		throw new UnsupportedOperationException();
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
	protected AbstractTypeExporter getTypeExporter(final ErrorDataType type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public EClass getTypeEClass() {
		return getType().eClass();
	}

	@Override
	public String getFileExtension() {
		return TypeLibraryTags.DATA_TYPE_FILE_ENDING;
	}
}
