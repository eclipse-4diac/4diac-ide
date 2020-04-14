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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry;
import org.eclipse.fordiac.ide.model.data.BaseType1;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.ElementaryType;

public final class DataTypeLibrary {

	private final Map<String, DataType> typeMap = new HashMap<>();
	private final Map<String, DataTypePaletteEntry> derivedTypeMap = new HashMap<>();

	/**
	 * Instantiates a new data type library.
	 */
	DataTypeLibrary() {
		initElementaryTypes();
	}

	public void addPaletteEntry(DataTypePaletteEntry entry) {
		derivedTypeMap.put(entry.getLabel(), entry);
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
	public List<DataType> getDataTypes() {
		List<DataType> dataTypes = new ArrayList<>(typeMap.size() + derivedTypeMap.size());
		dataTypes.addAll(typeMap.values());
		dataTypes.addAll(
				derivedTypeMap.values().stream().map(DataTypePaletteEntry::getType).collect(Collectors.toList()));
		return dataTypes;
	}

	/**
	 * Gets the data types sorted alphabetically from a to z.
	 *
	 * @return the sorted data types list
	 */
	public List<DataType> getDataTypesSorted() {
		List<DataType> dataTypes = getDataTypes();
		Collections.sort(dataTypes, NamedElementComparator.INSTANCE);
		return dataTypes;
	}

	/**
	 * FIXME only return type if it really exists!
	 *
	 * @param name the name
	 *
	 * @return the type
	 */
	public DataType getType(final String name) {
		if (null == name) {
			return typeMap.get("ANY"); //$NON-NLS-1$
		}
		DataType type = typeMap.get(name.toUpperCase());

		if (null == type) {
			type = getDerivedType(name);
			if (null == type) {
				// TODO reconsider if in this case a new type should be created
				type = DataFactory.eINSTANCE.createElementaryType();
				type.setName(name);
				typeMap.put(name, type);
			}
		}
		return type;
	}

	private DataType getDerivedType(String name) {
		DataTypePaletteEntry entry = derivedTypeMap.get(name);
		if (null != entry) {
			return entry.getType();
		}
		return null;
	}

}
