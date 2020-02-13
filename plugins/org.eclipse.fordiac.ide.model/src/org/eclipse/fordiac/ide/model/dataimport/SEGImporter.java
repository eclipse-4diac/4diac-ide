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

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * Managing class for importing *.seg files
 *
 */

public final class SEGImporter extends TypeImporter {

	public SEGImporter(final IFile iFile) throws XMLStreamException, CoreException {
		super(iFile);
	}

	@Override
	protected SegmentType getType() {
		return (SegmentType) super.getType();
	}

	@Override
	protected LibraryElement createType() {
		return LibraryElementFactory.eINSTANCE.createSegmentType();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.SEGMENT_TYPE_ELEMENT;
	}

	@Override
	protected IChildHandler getTypeChildrenHandler() {
		return name -> {
			switch (name) {
			case LibraryElementTags.IDENTIFICATION_ELEMENT:
				parseIdentification(getType());
				break;
			case LibraryElementTags.VERSION_INFO_ELEMENT:
				parseVersionInfo(getType());
				break;
			case LibraryElementTags.COMPILER_INFO_ELEMENT:
				parseCompilerInfo(getType());
				break;
			case LibraryElementTags.VAR_DECLARATION_ELEMENT:
				VarDeclaration v = parseVarDeclaration();
				v.setIsInput(true);
				getType().getVarDeclaration().add(v);
				break;
			default:
				return false;
			}
			return true;
		};
	}

}
