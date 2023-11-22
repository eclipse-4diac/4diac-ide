/********************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University, Linz
 * 				 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Martijn Rooker,Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved coordinate system resolution conversion to dedicated class,
 *   			   moved connection value parsing to fbnetwork importer where it
 *                 belongs
 *               - extracted from import utils to better fit to new staxx based
 *                 importer
 *  			 - Changed XML parsing to Staxx cursor interface for improved
 *  			   parsing performance
 *  Fabio Gandolfi - added errormarker for missing datatype
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.setArraySize;

import java.io.InputStream;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public abstract class TypeImporter extends CommonElementImporter {

	protected TypeImporter(final IFile file) {
		super(file);
	}

	protected TypeImporter(final InputStream inputStream, final TypeLibrary typeLibrary) {
		super(inputStream, typeLibrary);
	}

	protected TypeImporter(final CommonElementImporter importer) {
		super(importer);
	}

	/**
	 * This method parses VariableDeclaration.
	 *
	 * @return v - the parsed VariableDeclaration
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	protected VarDeclaration parseVarDeclaration() throws TypeImportException, XMLStreamException {
		final VarDeclaration v = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		readNameCommentAttributes(v);

		final String typeName = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null == typeName) {
			throw new TypeImportException(Messages.Import_ERROR_InputVariableTypeNotDefined);
		}
		final DataType dataType = getDataTypeLibrary().getType(typeName);
		v.setType(dataType);

		final String arraySize = getAttributeValue(LibraryElementTags.ARRAYSIZE_ATTRIBUTE);
		if (null != arraySize) {
			setArraySize(v, arraySize);
		}

		final String initialValue = getAttributeValue(LibraryElementTags.INITIALVALUE_ATTRIBUTE);
		if (null != initialValue) {
			final Value varInitialization = LibraryElementFactory.eINSTANCE.createValue();
			varInitialization.setValue(initialValue);
			v.setValue(varInitialization);
		}

		processChildren(LibraryElementTags.VAR_DECLARATION_ELEMENT, name -> {
			if (LibraryElementTags.ATTRIBUTE_ELEMENT.equals(name)) {
				parseGenericAttributeNode(v);
				proceedToEndElementNamed(LibraryElementTags.ATTRIBUTE_ELEMENT);
				return true;
			}
			return false;
		});

		proceedToEndElementNamed(LibraryElementTags.VAR_DECLARATION_ELEMENT);
		return v;
	}
}
