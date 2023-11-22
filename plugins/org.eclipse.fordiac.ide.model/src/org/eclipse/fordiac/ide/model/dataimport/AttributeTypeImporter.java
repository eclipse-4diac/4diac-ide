/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * Managing class for importing *.atp files
 *
 */

public class AttributeTypeImporter extends TypeImporter {

	public AttributeTypeImporter(final IFile typeFile) {
		super(typeFile);
	}

	public AttributeTypeImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	@Override
	public AttributeDeclaration getElement() {
		return (AttributeDeclaration) super.getElement();
	}

	@Override
	public LibraryElement createRootModelElement() {
		return LibraryElementFactory.eINSTANCE.createAttributeDeclaration();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.ATTRIBUTE_DECLARATION_ELEMENT;
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
			case LibraryElementTags.STRUCTURED_TYPE_ELEMENT:
				parseStructuredType(getElement());
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

	private void parseStructuredType(final AttributeDeclaration attribute)
			throws XMLStreamException, TypeImportException {
		final StructuredType struct = DataFactory.eINSTANCE.createStructuredType();
		processChildren(LibraryElementTags.STRUCTURED_TYPE_ELEMENT, name -> {
			if (LibraryElementTags.VAR_DECLARATION_ELEMENT.equals(name)) {
				struct.getMemberVariables().add(parseVarDeclaration());
				return true;
			}
			return false;
		});
		attribute.setType(struct);
	}
}
