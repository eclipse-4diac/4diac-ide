/********************************************************************************
 * Copyright (c) 2010, 2024  Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *							 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - Changed XML parsing to Staxx cursor interface for improved
 *  			  parsing performance
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * Managing class for importing *.atp files
 *
 */

public class ADPImporter extends FBTImporter {

	public ADPImporter(final IFile typeFile) {
		super(typeFile);
	}

	public ADPImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	@Override
	public AdapterType getElement() {
		return (AdapterType) super.getElement();
	}

	@Override
	public LibraryElement createRootModelElement() {
		final AdapterType newType = LibraryElementFactory.eINSTANCE.createAdapterType();
		newType.setService(LibraryElementFactory.eINSTANCE.createService());
		return newType;
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.ADAPTER_TYPE;
	}

}
