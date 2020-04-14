/********************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.ElementaryType;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class DataTypeLibrary {

	/** The type map. */
	private Map<String, DataType> typeMap;

	/**
	 * Instantiates a new data type library.
	 */
	DataTypeLibrary() {
		typeMap = new HashMap<>();
		initElementaryTypes();
	}

	/** The instance. */
	private static DataTypeLibrary instance;

	/**
	 * Gets the single instance of DataTypeLibrary.
	 *
	 * @return single instance of DataTypeLibrary
	 * @deprecated will be replaced by a project specific datatype library managed
	 *             by {@link TypeLibrary}
	 */
	@Deprecated
	public static DataTypeLibrary getInstance() {
		if (instance == null) {
			instance = new DataTypeLibrary();

		}
		return instance;
	}

	/**
	 * Inits the elementary types.
	 */
	private void initElementaryTypes() {
		if (typeMap == null) {
			typeMap = new HashMap<>();
		}

		BaseType1.VALUES.forEach(baseType -> {
			ElementaryType type = DataFactory.eINSTANCE.createElementaryType();
			type.setName(baseType.getLiteral());
			typeMap.put(baseType.getLiteral(), type);
		});
	}

	/**
	 * Gets the data types.
	 *
	 * @return the data types
	 */
	public Collection<DataType> getDataTypes() {
		return typeMap.values();
	}

	/**
	 * Gets the data types sorted alphabetically from a to z.
	 *
	 * @return the sorted data types list
	 */
	public Collection<DataType> getDataTypesSorted() {
		List<DataType> dataTypes = new ArrayList<>(getDataTypes());
		Collections.sort(dataTypes, NamedElementComparator.INSTANCE);
		return dataTypes;
	}

	/**
	 * FIXME only return type if it really exists! --> after parsing/importing of
	 * types is implemented --> planned for V0.2
	 *
	 * @param name the name
	 *
	 * @return the type
	 */
	public DataType getType(final String name) {
		if (name == null) {
			return typeMap.get("ANY"); //$NON-NLS-1$
		}
		Object value = typeMap.get(name.toUpperCase());
		if (value != null) {
			return (DataType) value;
		}
		ElementaryType type = DataFactory.eINSTANCE.createElementaryType();
		type.setName(name);
		typeMap.put(name, type);
		return type;
	}

	public static void loadReferencedDataTypes(File srcFile, Shell shell) throws TypeImportException {
		List<String> referencedDataTypes = getReferencedDataTypes(srcFile);

		for (String dataType : referencedDataTypes) {
			DataType type = DataTypeLibrary.getInstance().getType(dataType);
			if (type == null) {
				if (shell == null) {
					shell = Display.getCurrent().getActiveShell();
				}
				FileDialog fd = new FileDialog(shell);
				String msg = MessageFormat.format(Messages.TypeLibrary_ImportDataTypeFileDialogTitle, dataType);
				fd.setText(msg);
				fd.setFilterExtensions(new String[] { "*.dtp" }); //$NON-NLS-1$
				String typeFile = fd.open();
				if (typeFile != null) {
					// TODO import datatype
				} else {
					throw new TypeImportException(Messages.TypeLibrary_ERROR_ReferencedDataTypeNotFound);
				}

			}
		}
	}

	/**
	 * This method returns a list with all the data types that are referenced by the
	 * imported FBTypes.
	 *
	 * @param file - the file that is being checked if it has references
	 *
	 * @return references - a list containing all the references
	 */
	private static List<String> getReferencedDataTypes(final File file) {
		List<String> references = new ArrayList<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;

		dbf.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", //$NON-NLS-1$
				Boolean.FALSE);
		try {
			db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			// parse document for "FBNetwork" tag
			Node rootNode = document.getDocumentElement();
			NodeList childNodes = rootNode.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node n = childNodes.item(i);
				if (n.getNodeName().equals(LibraryElementTags.VAR_DECLARATION_ELEMENT)) {
					String dataType = ""; //$NON-NLS-1$
					dataType = n.getAttributes().getNamedItem(LibraryElementTags.TYPE_ATTRIBUTE).getNodeValue();
					references.add(dataType);
				}

			}
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		return references;

	}

}
