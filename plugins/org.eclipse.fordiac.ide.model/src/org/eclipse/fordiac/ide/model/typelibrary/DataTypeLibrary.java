/********************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *                          Johannes Kepler Universiy Linz
 *                          Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
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
 *  Martin Jobst - Separate error marker data types into a distinct map
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class DataTypeLibrary {

	private static final Pattern STRING_MAX_LENGTH_PATTERN = Pattern.compile("(W?STRING)\\[(\\d+)\\]"); //$NON-NLS-1$

	private final Map<String, DataType> typeMap = new ConcurrentHashMap<>();
	private final Map<String, DataTypeEntry> derivedTypes = new ConcurrentHashMap<>();
	private final Map<String, ErrorMarkerDataType> errorTypes = new ConcurrentHashMap<>();

	/** Instantiates a new data type library. */
	public DataTypeLibrary() {
		initElementaryTypes();
		initGenericTypes();
	}

	public boolean addTypeEntry(final DataTypeEntry entry) {
		errorTypes.remove(entry.getFullTypeName()); // remove stale error marker data type
		return derivedTypes.putIfAbsent(entry.getFullTypeName(), entry) == null;
	}

	public void removeTypeEntry(final DataTypeEntry entry) {
		derivedTypes.remove(entry.getFullTypeName(), entry);
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
		derivedTypes.values().stream().map(DataTypeEntry::getType).filter(Objects::nonNull)
				.forEachOrdered(dataTypes::add);
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
		if (type != null) {
			return type;
		}

		type = getDerivedType(name);
		if (type != null) {
			return type;
		}

		type = createParametricType(name);
		if (type != null) {
			return type;
		}

		return createErrorMarkerType(name, MessageFormat.format(Messages.DataTypeLibrary_MissingDatatype, name));
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

	private DataType createParametricType(final String name) {
		final Matcher matcher = STRING_MAX_LENGTH_PATTERN.matcher(name);
		if (matcher.matches()) {
			try {
				final String plainTypeName = matcher.group(1);
				final String maxLengthString = matcher.group(2);
				final DataType plainType = typeMap.get(plainTypeName);
				if (plainType instanceof AnyStringType) {
					final int maxLength = Integer.parseUnsignedInt(maxLengthString);
					final AnyStringType type = (AnyStringType) DataFactory.eINSTANCE.create(plainType.eClass());
					type.setName(name);
					type.setMaxLength(maxLength);
					typeMap.put(name, type);
					return type;
				}
			} catch (final NumberFormatException e) {
				return createErrorMarkerType(name,
						MessageFormat.format(Messages.DataTypeLibrary_InvalidMaxLengthInStringType, name));
			}
		}
		return null;
	}

	private ErrorMarkerDataType createErrorMarkerType(final String typeName, final String message) {
		return errorTypes.computeIfAbsent(typeName, name -> {
			FordiacLogHelper.logInfo(message);
			final ErrorMarkerDataType type = LibraryElementFactory.eINSTANCE.createErrorMarkerDataType();
			type.setName(name);
			type.setErrorMessage(message);
			return type;
		});
	}

	public StructuredType getStructuredType(final String name) {
		final DataType derivedType = getDerivedType(name);
		if (derivedType instanceof final StructuredType structuredType) {
			return structuredType;
		}
		return (StructuredType) typeMap.get(FordiacKeywords.ANY_STRUCT);

	}

}
