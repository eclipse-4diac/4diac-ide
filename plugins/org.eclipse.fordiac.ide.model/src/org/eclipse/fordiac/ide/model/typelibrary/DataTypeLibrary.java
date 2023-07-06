/********************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2013, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2020 Johannes Kepler Universiy Linz
 * 				 2022 Primetals Technologies Austria GmbH
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
 *  Alois Zoitl - Changed to a per project Type and Data TypeLibrary
 *  Martin Melik-Merkumians - Changes to reflect returned List instead of array
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class DataTypeLibrary {

	private final Map<String, DataType> typeMap = new HashMap<>();
	private final Map<String, DataTypeEntry> derivedTypes = new HashMap<>();

	/** Instantiates a new data type library. */
	public DataTypeLibrary() {
		initElementaryTypes();
		initGenericTypes();
	}

	public void addTypeEntry(final DataTypeEntry entry) {
		derivedTypes.put(entry.getTypeName(), entry);
	}

	public void removeTypeEntry(final DataTypeEntry entry) {
		derivedTypes.remove(entry.getTypeName());
	}

	private void addToTypeMap(final DataType type) {
		typeMap.put(type.getName(), type);
	}

	/** Inits the elementary types. */
	private void initElementaryTypes() {
		ElementaryTypes.getAllElementaryType().forEach(this::addToTypeMap);
	}

	private void initGenericTypes() {
		GenericTypes.getAllGenericTypes().forEach(this::addToTypeMap);
	}

	public Map<String, DataTypeEntry> getDerivedDataTypes() {
		return derivedTypes;
	}

	/** Gets the data types.
	 *
	 * @return the data types */
	public List<DataType> getDataTypes() {
		final List<DataType> dataTypes = new ArrayList<>(typeMap.size() + derivedTypes.size());
		dataTypes.addAll(typeMap.values());
		dataTypes.addAll(derivedTypes.values().stream().map(DataTypeEntry::getType).filter(Objects::nonNull)
				.collect(Collectors.toList()));
		return dataTypes;
	}

	public static List<DataType> getNonUserDefinedDataTypes() {
		return IecTypes.ElementaryTypes.getAllElementaryType();
	}

	/** Gets the data types sorted alphabetically from a to z.
	 *
	 * @return the sorted data types list */
	public List<DataType> getDataTypesSorted() {
		final List<DataType> dataTypes = getDataTypes();
		Collections.sort(dataTypes, NamedElementComparator.INSTANCE);
		return dataTypes;
	}

	/** get type
	 *
	 * @param name the name
	 *
	 * @return the type or ErrorMarker */
	public DataType getType(final String name) {
		if (null == name) {
			return typeMap.get("ANY"); //$NON-NLS-1$
		}
		DataType type = typeMap.get(name);

		if (null == type) {
			type = getDerivedType(name);
			if (null == type) {
				FordiacLogHelper.logInfo("Missing Datatype: " + name); //$NON-NLS-1$
				type = LibraryElementFactory.eINSTANCE.createErrorMarkerDataType();
				type.setName(name);
				typeMap.put(name, type);
			}
		}
		return type;
	}

	public DataType getTypeIfExists(final String name) {
		final DataType dataType = typeMap.get(name.toUpperCase());
		if (dataType != null) {
			return dataType;
		}
		return getDerivedType(name);
	}

	public List<StructuredType> getStructuredTypes() {
		final List<StructuredType> types = getDerivedDataTypes().entrySet().stream()
				.filter(entry -> (entry.getValue().getType() instanceof StructuredType))
				.map(entry -> ((StructuredType) entry.getValue().getType())).collect(Collectors.toList());
		types.add((StructuredType) getType(FordiacKeywords.ANY_STRUCT));
		return types;
	}

	public List<StructuredType> getStructuredTypesSorted() {
		final List<StructuredType> structTypes = getStructuredTypes();
		Collections.sort(structTypes, NamedElementComparator.INSTANCE);
		return structTypes;
	}

	private DataType getDerivedType(final String name) {
		final DataTypeEntry entry = derivedTypes.get(name);
		if (null != entry) {
			return entry.getType();
		}
		return null;
	}

	public StructuredType getStructuredType(final String name) {
		final DataType derivedType = getDerivedType(name);
		if (derivedType instanceof StructuredType) {
			return (StructuredType) derivedType;
		}
		return (StructuredType) typeMap.get(FordiacKeywords.ANY_STRUCT);

	}

}
