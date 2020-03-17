/********************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 - 2020 Johannes Kepler University, Linz
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
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;

public abstract class TypeImporter extends CommonElementImporter {
	private LibraryElement type;

	protected TypeImporter() {
		super();
	}

	protected TypeImporter(XMLStreamReader reader) {
		super(reader);
	}

	protected LibraryElement getType() {
		return type;
	}

	public void setType(LibraryElement type) {
		this.type = type;
	}

	public LibraryElement importType(IFile typeFile) throws TypeImportException {
		try (ImporterStreams streams = createInputStreams(typeFile.getContents())) {
			type = createType();
			proceedToStartElementNamed(getStartElementName());
			readNameCommentAttributes(type);
			processChildren(getStartElementName(), getTypeChildrenHandler());
		} catch (TypeImportException e) {
			throw e;
		} catch (Exception e) {
			throw new TypeImportException(e.getMessage(), e);
		}
		return type;
	}

	protected abstract LibraryElement createType();

	protected abstract String getStartElementName();

	protected abstract IChildHandler getTypeChildrenHandler();

	/**
	 * This method parses VariableDeclaration.
	 *
	 * @return v - the parsed VariableDeclaration
	 *
	 * @throws TypeImportException the FBT import exception
	 * @throws XMLStreamException
	 */
	protected VarDeclaration parseVarDeclaration() throws TypeImportException, XMLStreamException {
		VarDeclaration v = LibraryElementFactory.eINSTANCE.createVarDeclaration();

		readNameCommentAttributes(v);

		String typeName = getAttributeValue(LibraryElementTags.TYPE_ATTRIBUTE);
		if (null != typeName) {
			DataType dataType = DataTypeLibrary.getInstance().getType(typeName);
			v.setTypeName(typeName);
			if (dataType != null) {
				v.setType(dataType);
			}
		} else {
			throw new TypeImportException(Messages.Import_ERROR_InputVariableTypeNotDefined);
		}

		String arraySize = getAttributeValue(LibraryElementTags.ARRAYSIZE_ATTRIBUTE);
		if (null != arraySize) {
			try {
				v.setArraySize(Integer.parseInt(arraySize));
			} catch (NumberFormatException nfe) {
				throw new TypeImportException(Messages.Import_ERROR_ArraySize_NumberFormat);
			}
		} else {
			v.setArraySize(-1);
		}

		String initialValue = getAttributeValue(LibraryElementTags.INITIALVALUE_ATTRIBUTE);
		if (null != initialValue) {
			Value varInitialization = LibraryElementFactory.eINSTANCE.createValue();
			varInitialization.setValue(initialValue);
			v.setValue(varInitialization);
		}
		proceedToEndElementNamed(LibraryElementTags.VAR_DECLARATION_ELEMENT);
		return v;
	}
}
