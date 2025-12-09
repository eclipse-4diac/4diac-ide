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
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.OriginalSource;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class GlobalConstantsImporter extends TypeImporter {

	public GlobalConstantsImporter(final IFile typeFile) {
		super(typeFile);
	}

	public GlobalConstantsImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	@Override
	protected GlobalConstants createRootModelElement() {
		return LibraryElementFactory.eINSTANCE.createGlobalConstants();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.GLOBAL_CONSTANTS_ELEMENT;
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
			case LibraryElementTags.GLOBAL_CONSTANTS_ELEMENT:
				parseGlobalConstants(getElement());
				break;
			case LibraryElementTags.ORIGINAL_SOURCE_ELEMENT:
				getElement().setSource(parseOriginalSource());
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

	private void parseGlobalConstants(final GlobalConstants globalConstants)
			throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.GLOBAL_CONSTANTS_ELEMENT, name -> {
			if (LibraryElementTags.VAR_DECLARATION_ELEMENT.equals(name)) {
				globalConstants.getConstants().add(parseVarDeclaration());
				return true;
			}
			return false;
		});
	}

	private OriginalSource parseOriginalSource() throws XMLStreamException {
		final OriginalSource source = LibraryElementFactory.eINSTANCE.createOriginalSource();
		parseOriginalSourceText(source);
		proceedToEndElementNamed(LibraryElementTags.ORIGINAL_SOURCE_ELEMENT);
		return source;
	}

	private void parseOriginalSourceText(final OriginalSource source) throws XMLStreamException {
		final String text = getAttributeValue(LibraryElementTags.TEXT_ATTRIBUTE);
		if (null != text) {
			source.setText(text);
		} else {
			source.setText(readCDataSection());
		}
	}

	@Override
	public GlobalConstants getElement() {
		return (GlobalConstants) super.getElement();
	}
}
