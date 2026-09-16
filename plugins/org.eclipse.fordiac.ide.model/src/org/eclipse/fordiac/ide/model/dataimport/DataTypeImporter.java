/********************************************************************************
 * Copyright (c) 2020, 2025 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr - initial implementation and documentation
 *  Alois Zoitl     - added enumerated type parsing
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.EnumeratedType;
import org.eclipse.fordiac.ide.model.data.EnumeratedValue;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

/**
 * Managing class for importing *.dtp files
 *
 */

public class DataTypeImporter extends TypeImporter {

	@Override
	public AnyDerivedType getElement() {
		return (AnyDerivedType) super.getElement();
	}

	public DataTypeImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	@Override
	protected AnyDerivedType createRootModelElement() {
		return DataFactory.eINSTANCE.createAnyDerivedType();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.DATA_TYPE;
	}

	@Override
	protected IChildHandler getBaseChildrenHandler() {
		return name -> {
			switch (name) {
			case LibraryElementTags.IDENTIFICATION_ELEMENT -> parseIdentification(getElement());
			case LibraryElementTags.VERSION_INFO_ELEMENT -> parseVersionInfo(getElement());
			case LibraryElementTags.COMPILER_INFO_ELEMENT -> getElement().setCompilerInfo(parseCompilerInfo());
			case LibraryElementTags.ASN1_TAG -> parseASN1Tag();
			case LibraryElementTags.STRUCTURED_TYPE_ELEMENT -> {
				setElement(convertToStructuredType(getElement()));
				parseStructuredType((StructuredType) getElement());
			}
			case LibraryElementTags.ENUMERATED_TYPE_ELEMENT -> {
				setElement(convertToEnumeratedType(getElement()));
				parseEnumeratedType((EnumeratedType) getElement());
			}
			case LibraryElementTags.ATTRIBUTE_ELEMENT -> {
				parseGenericAttributeNode(getElement());
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
			}
			default -> {
				return false;
			}
			}
			return true;
		};
	}

	private void parseASN1Tag() throws XMLStreamException {
		proceedToEndElementNamed(LibraryElementTags.ASN1_TAG);
	}

	/**
	 * This method converts the data type AnyDerivedType to a StructuredType.
	 *
	 * @param type - The AnyDerivedType that is being converted to StructuredType
	 *
	 * @return - A StructuredType that is converted
	 */
	private static StructuredType convertToStructuredType(final AnyDerivedType type) {
		final StructuredType structuredType = DataFactory.eINSTANCE.createStructuredType();
		copyGeneralTypeInformation(structuredType, type);
		return structuredType;
	}

	/**
	 * This method converts the data type AnyDerivedType to a EnumeratedType.
	 *
	 * @param type - The AnyDerivedType that is being converted to EnumeratedType
	 *
	 * @return - A EnumeratedType that is converted
	 */
	private static EnumeratedType convertToEnumeratedType(final AnyDerivedType type) {
		final EnumeratedType enumeratedType = DataFactory.eINSTANCE.createEnumeratedType();
		copyGeneralTypeInformation(enumeratedType, type);
		return enumeratedType;
	}

	private static void copyGeneralTypeInformation(final AnyDerivedType dstType, final AnyDerivedType srcType) {
		dstType.setName(srcType.getName());
		dstType.setComment(srcType.getComment());
		dstType.setIdentification(srcType.getIdentification());
		dstType.getVersionInfo().addAll(srcType.getVersionInfo());
		dstType.setCompilerInfo(srcType.getCompilerInfo());
	}

	/**
	 * This method parses the contents of a StructuredType
	 *
	 * @param
	 */
	private void parseStructuredType(final StructuredType struct) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.STRUCTURED_TYPE_ELEMENT, name -> {
			if (LibraryElementTags.VAR_DECLARATION_ELEMENT.equals(name)) {
				struct.getMemberVariables().add(parseVarDeclaration());
				return true;
			}
			return false;
		});
	}

	private void parseEnumeratedType(final EnumeratedType enumType) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.ENUMERATED_TYPE_ELEMENT, name -> {
			if (LibraryElementTags.ENUMERATED_VALUE_ELEMENT.equals(name)) {
				enumType.getEnumeratedValues().add(parseEnumeratedValue());
				return true;
			}
			return false;
		});
	}

	private EnumeratedValue parseEnumeratedValue() throws TypeImportException, XMLStreamException {
		final EnumeratedValue ev = DataFactory.eINSTANCE.createEnumeratedValue();
		readNameCommentAttributes(ev);
		proceedToEndElementNamed(LibraryElementTags.ENUMERATED_VALUE_ELEMENT);
		return ev;
	}
}
