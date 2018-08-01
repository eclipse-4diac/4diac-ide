/********************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.ElementaryType;
import org.eclipse.fordiac.ide.model.dataimport.FBTImporter;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public final class DataTypeLibrary {

	/** The type map. */
	private Map<String, DataType> typeMap;

	/**
	 * Instantiates a new data type library.
	 */
	private DataTypeLibrary() {
		typeMap = new HashMap<String, DataType>();
		initElementaryTypes();
	}

	/** The instance. */
	private static DataTypeLibrary instance;

	/**
	 * Gets the single instance of DataTypeLibrary.
	 * 
	 * @return single instance of DataTypeLibrary
	 */
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
			typeMap = new HashMap<String, DataType>();
		}

		// List<> BaseType1.VALUES;
		List<BaseType1> elementaryTypes = BaseType1.VALUES;

		for (Iterator<BaseType1> iterator = elementaryTypes.iterator(); iterator
				.hasNext();) {
			BaseType1 baseType = iterator.next();
			ElementaryType type = DataFactory.eINSTANCE.createElementaryType();
			type.setName(baseType.getLiteral());
			typeMap.put(baseType.getLiteral(), type);
		}
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
		ArrayList<DataType> dataTypes = new ArrayList<DataType>(getDataTypes());
		Collections.sort(dataTypes, NamedElementComparator.INSTANCE);
		
		return dataTypes;
	}

	/**
	 * FIXME only return type if it really exists! --> after parsing/importing
	 * of types is implemented --> planned for V0.2
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

	
	public static void loadReferencedDataTypes(File srcFile, Shell shell) throws TypeImportException{		
		List<String> referencedDataTypes = FBTImporter.getReferencedDataTypes(srcFile);
		
		for (Iterator<String> iterator = referencedDataTypes.iterator(); iterator.hasNext();) {
			String dataType = iterator.next();
			DataType type = DataTypeLibrary.getInstance().getType(
					dataType);
			if (type == null) {
				if (shell == null) {
					shell = Display.getCurrent().getActiveShell();
				}
				FileDialog fd = new FileDialog(shell);
				String msg = MessageFormat
						.format(
								Messages.TypeLibrary_ImportDataTypeFileDialogTitle,
								new Object[] { dataType });
				fd.setText(msg);
				fd.setFilterExtensions(new String[] { "*.dtp" }); //$NON-NLS-1$
				String typeFile = fd.open();
				if (typeFile != null) {
					// TODO import datatype
				} else {
					throw new TypeImportException(
							Messages.TypeLibrary_ERROR_ReferencedDataTypeNotFound);
				}
	
			}
		}
	}
	
}
