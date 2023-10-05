/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.dataexport;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.OriginalSource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class GlobalConstantsExporter extends AbstractTypeExporter {

	public GlobalConstantsExporter(final GlobalConstants type) {
		super(type);
	}

	@Override
	protected GlobalConstants getType() {
		return (GlobalConstants) super.getType();
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.GLOBAL_CONSTANTS_ELEMENT;
	}

	@Override
	protected void createTypeSpecificXMLEntries() throws XMLStreamException {
		addCompilerInfo(getType().getCompilerInfo());
		addGlobalConstants(getType().getConstants());
		if (getType().getSource() != null) {
			addOriginalSource(getType().getSource());
		}
		addAttributes(getType().getAttributes());
	}

	private void addGlobalConstants(final Iterable<? extends VarDeclaration> vars) throws XMLStreamException {
		addStartElement(LibraryElementTags.GLOBAL_CONSTANTS_ELEMENT);
		for (final VarDeclaration varDecl : vars) {
			addVarDeclaration(varDecl);
		}
		addEndElement();
	}

	private void addOriginalSource(final OriginalSource source) throws XMLStreamException {
		addStartElement(LibraryElementTags.ORIGINAL_SOURCE_ELEMENT);
		writeOriginalSourceText(source.getText());
		addInlineEndElement();
	}

	private void writeOriginalSourceText(final String text) throws XMLStreamException {
		if (null != text) {
			writeCDataSection(text);
		} else {
			getWriter().writeCharacters(""); //$NON-NLS-1$
		}
	}
}
