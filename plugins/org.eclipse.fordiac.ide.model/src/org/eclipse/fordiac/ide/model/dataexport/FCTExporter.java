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
import org.eclipse.fordiac.ide.model.libraryElement.FunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;

public class FCTExporter extends AbstractBlockTypeExporter {

	public FCTExporter(final FunctionFBType type) {
		super(type);
	}

	@Override
	protected String getRootTag() {
		return LibraryElementTags.FUNCTION_ELEMENT;
	}

	@Override
	protected void createBlockTypeSpecificXMLEntries() throws XMLStreamException {
		addFunctionBody(getType().getBody());
	}

	private void addFunctionBody(final FunctionBody body) throws XMLStreamException {
		addStartElement(LibraryElementTags.FUNCTION_BODY_ELEMENT);
		if (body instanceof final STFunctionBody stFunctionBody) {
			addSTFunctionBody(stFunctionBody);
		}
		addEndElement();
	}

	private void addSTFunctionBody(final STFunctionBody body) throws XMLStreamException {
		addStartElement(LibraryElementTags.ST_ELEMENT);
		writeFunctionBodyText(body.getText());
		addInlineEndElement();
	}

	private void writeFunctionBodyText(final String text) throws XMLStreamException {
		if (null != text) {
			writeCDataSection(text);
		} else {
			getWriter().writeCharacters(""); //$NON-NLS-1$
		}
	}

	@Override
	public FunctionFBType getType() {
		return (FunctionFBType) super.getType();
	}
}
