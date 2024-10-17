/********************************************************************************
 * Copyright (c) 2020 Johannes Keppler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl, Bianca Wiesmayr - initial implementation
 ********************************************************************************/

package org.eclipse.fordiac.ide.model.dataexport;

import java.text.MessageFormat;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DataTypeExporter extends AbstractTypeExporter {

	public DataTypeExporter(final AnyDerivedType type) {
		super(type);
	}

	@Override
	public AnyDerivedType getType() {
		return (AnyDerivedType) super.getType();
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.DATA_TYPE;
	}

	@Override
	protected void createTypeSpecificXMLEntries() throws XMLStreamException {
		addCompilerInfo(getType().getCompilerInfo());

		if (!(getType() instanceof StructuredType)) {
			throw new XMLStreamException(
					MessageFormat.format(Messages.DataTypeExporter_UNSUPPORTED_DATA_TYPE, getType()));
		}
		createStructContent((StructuredType) getType());

		if (!getType().getAttributes().isEmpty()) {
			addAttributes(getType().getAttributes());
		}
	}

	private void createStructContent(final StructuredType type) throws XMLStreamException {
		addStartElement(LibraryElementTags.STRUCTURED_TYPE_ELEMENT);
		for (final VarDeclaration varDecl : type.getMemberVariables()) {
			addVarDeclaration(varDecl);
		}
		addEndElement();
	}

}
