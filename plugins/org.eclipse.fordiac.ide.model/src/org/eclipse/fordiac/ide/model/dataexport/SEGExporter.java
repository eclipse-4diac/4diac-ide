/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.dataexport;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class SEGExporter extends AbstractTypeExporter {

	public SEGExporter(final SegmentType type) {
		super(type);
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.SEGMENT_TYPE_ELEMENT;
	}

	@Override
	public SegmentType getType() {
		return (SegmentType) super.getType();
	}

	@Override
	protected void createTypeSpecificXMLEntries() throws XMLStreamException {
		addCompilerInfo(getType().getCompilerInfo());

		for (final VarDeclaration varDecl : getType().getVarDeclaration()) {
			addVarDeclaration(varDecl);
		}

		addAttributes(getType().getAttributes());
	}

}
