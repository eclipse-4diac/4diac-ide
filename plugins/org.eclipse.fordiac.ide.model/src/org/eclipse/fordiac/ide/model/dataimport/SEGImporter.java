/********************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2014  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Alois Zoitl - Changed XML parsing to Staxx cursor interface for improved
 *  			  parsing performance
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * Managing class for importing *.seg files
 *
 */

public class SEGImporter extends TypeImporter {

	public SEGImporter(final IFile typeFile) {
		super(typeFile);
	}

	public SEGImporter(final InputStream inputStream, final TypeLibrary typeLib) {
		super(inputStream, typeLib);
	}

	@Override
	public SegmentType getElement() {
		return (SegmentType) super.getElement();
	}

	@Override
	protected LibraryElement createRootModelElement() {
		return LibraryElementFactory.eINSTANCE.createSegmentType();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.SEGMENT_TYPE_ELEMENT;
	}

	@Override
	protected IChildHandler getBaseChildrenHandler() {
		return name -> {
			switch (name) {
			case LibraryElementTags.IDENTIFICATION_ELEMENT:
				parseIdentification(getElement());
				break;
			case LibraryElementTags.VERSION_INFO_ELEMENT:
				parseVersionInfo(getElement());
				break;
			case LibraryElementTags.COMPILER_INFO_ELEMENT:
				getElement().setCompilerInfo(parseCompilerInfo());
				break;
			case LibraryElementTags.VAR_DECLARATION_ELEMENT:
				final VarDeclaration v = parseVarDeclaration();
				v.setIsInput(true);
				getElement().getVarDeclaration().add(v);
				break;
			default:
				return false;
			}
			return true;
		};
	}

}
