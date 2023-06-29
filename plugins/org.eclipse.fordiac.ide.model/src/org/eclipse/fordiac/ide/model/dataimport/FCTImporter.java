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
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.TextFunctionBody;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class FCTImporter extends FBTImporter {

	public FCTImporter(final IFile file) {
		super(file);
	}

	public FCTImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	public FCTImporter(final CommonElementImporter importer) {
		super(importer);
	}

	@Override
	protected LibraryElement createRootModelElement() {
		return LibraryElementFactory.eINSTANCE.createFunctionFBType();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.FUNCTION_ELEMENT;
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
			case LibraryElementTags.INTERFACE_LIST_ELEMENT:
				getElement().setInterfaceList(parseInterfaceList(LibraryElementTags.INTERFACE_LIST_ELEMENT));
				break;
			case LibraryElementTags.FUNCTION_BODY_ELEMENT:
				parseFunctionBody(getElement());
				break;
			case LibraryElementTags.ATTRIBUTE_ELEMENT:
				parseGenericAttributeNode(getElement());
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				break;
			default:
				return false;
			}
			return true;
		};
	}

	private void parseFunctionBody(final FunctionFBType type) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.FUNCTION_BODY_ELEMENT, name -> {
			if (name.equals(LibraryElementTags.ST_ELEMENT) && type.getBody() == null) {
				getElement().setBody(parseSTFunctionBody());
				return true;
			}
			return false;
		});
	}

	private STFunctionBody parseSTFunctionBody() throws XMLStreamException {
		final STFunctionBody body = LibraryElementFactory.eINSTANCE.createSTFunctionBody();
		parseFunctionBodyText(body);
		proceedToEndElementNamed(LibraryElementTags.ST_ELEMENT);
		return body;
	}

	private void parseFunctionBodyText(final TextFunctionBody body) throws XMLStreamException {
		final String text = getAttributeValue(LibraryElementTags.TEXT_ATTRIBUTE);
		if (null != text) {
			body.setText(text);
		} else {
			body.setText(readCDataSection());
		}
	}

	@Override
	public FunctionFBType getElement() {
		return (FunctionFBType) super.getElement();
	}
}
