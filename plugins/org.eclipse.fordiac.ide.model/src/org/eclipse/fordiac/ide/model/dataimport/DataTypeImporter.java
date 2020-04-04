/********************************************************************************
 * Copyright (c) 2020 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr - initial implementation and documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;

/**
 * Managing class for importing *.dtp files
 *
 */

public class DataTypeImporter extends TypeImporter {

	@Override
	protected AnyDerivedType getType() {
		return (AnyDerivedType) super.getType();
	}

	public DataTypeImporter() {
	}

	protected DataTypeImporter(final XMLStreamReader reader) {
		super(reader);
	}

	@Override
	protected AnyDerivedType createType() {
		return DataFactory.eINSTANCE.createAnyDerivedType();
	}

	@Override
	protected String getStartElementName() {
		return LibraryElementTags.DATA_TYPE;
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
			case LibraryElementTags.ASN1_TAG:
				parseASN1Tag(getType());
				break;
			case LibraryElementTags.STRUCTURED_TYPE_ELEMENT:
				setType(convertToStructuredType(getType()));
				parseStructuredType((StructuredType) getType());
				break;
			// TODO support other AnyDerivedTypes such as ArrayType
			default:
				return false;
			}
			return true;
		};
	}

	private void parseASN1Tag(AnyDerivedType type) throws XMLStreamException {
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
		StructuredType structuredType = DataFactory.eINSTANCE.createStructuredType();
		copyGeneralTypeInformation(structuredType, type);
		return structuredType;
	}

	private static void copyGeneralTypeInformation(DataType dstType, DataType srcType) {
		dstType.setName(srcType.getName());
		dstType.setComment(srcType.getComment());
		dstType.setIdentification(srcType.getIdentification());
		dstType.getVersionInfo().addAll(srcType.getVersionInfo());
	}

	/**
	 * This method parses the contents of a StructuredType
	 *
	 * @param
	 *
	 * @
	 */
	private void parseStructuredType(StructuredType struct) throws TypeImportException, XMLStreamException {
		processChildren(LibraryElementTags.STRUCTURED_TYPE_ELEMENT, name -> {
			switch (name) {
			case LibraryElementTags.VAR_DECLARATION_ELEMENT:
				struct.getMemberVariables().add(parseVarDeclaration());
				break;
			default:
				return false;
			}
			return true;
		});
	}
}
