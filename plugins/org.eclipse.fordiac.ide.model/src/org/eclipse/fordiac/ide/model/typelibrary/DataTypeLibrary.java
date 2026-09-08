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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.typelibrary.impl.DataTypeEntryImpl;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;

public final class DataTypeLibrary {

	private static final Pattern STRING_MAX_LENGTH_PATTERN = Pattern.compile("(W?STRING)\\[(\\d+)\\]", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);

	private final TypeLibrary typeLibrary;
	private final Map<String, DataType> typeMap = new ConcurrentHashMap<>();
	private final Map<String, DataTypeEntry> derivedTypes = new ConcurrentHashMap<>();

	/** Instantiates a new data type library. */
	public DataTypeLibrary(final TypeLibrary typeLibrary) {
		this.typeLibrary = typeLibrary;
		initElementaryTypes();
		initGenericTypes();
	}

	public boolean addTypeEntry(final DataTypeEntry entry) {
		final String uppercaseName = entry.getFullTypeName().toUpperCase();
		// remove stale error data type
		final DataTypeEntry oldEntry = removeErrorTypeEntry(uppercaseName);
		// add new type entry
		final boolean added = derivedTypes.putIfAbsent(uppercaseName, entry) == null;
		// trigger transitive refresh after new entry has been added
		if (oldEntry != null) {
			oldEntry.setTypeLibrary(null);
		}
		return added;
	}

	public void removeTypeEntry(final DataTypeEntry entry) {
		derivedTypes.remove(entry.getFullTypeName().toUpperCase(), entry);
	}

	private DataTypeEntry removeErrorTypeEntry(final String uppercaseName) {
		DataTypeEntry oldEntry = derivedTypes.get(uppercaseName);
		while (oldEntry != null && oldEntry.getFile() == null) {
			if (derivedTypes.remove(uppercaseName, oldEntry)) {
				return oldEntry;
			}
			oldEntry = derivedTypes.get(uppercaseName);
		}
		return null;
	}

	private void addToTypeMap(final DataType type) {
		typeMap.putIfAbsent(type.getName().toUpperCase(), type);
	}

	/** Inits the elementary types. */
	private void initElementaryTypes() {
		ElementaryTypes.getAllElementaryType().forEach(this::addToTypeMap);
	}

	private void initGenericTypes() {
		GenericTypes.getAllGenericTypes().forEach(this::addToTypeMap);
	}

	public Stream<DataTypeEntry> getDerivedDataTypes() {
		return derivedTypes.values().stream().filter(Predicate.not(TypeEntry::hasError));
	}

	/**
	 * Gets the data types.
	 *
	 * @return the data types
	 */
	public List<DataType> getDataTypes() {
		return Stream.concat(typeMap.values().stream(),
				derivedTypes.values().stream().map(DataTypeEntry::getType).filter(Objects::nonNull)).toList();
	}

	public static List<DataType> getNonUserDefinedDataTypes() {
		return IecTypes.ElementaryTypes.getAllElementaryType();
	}

	/**
	 * Gets the data types sorted alphabetically from a to z.
	 *
	 * @return the sorted data types list
	 */
	public Stream<DataType> getDataTypesSorted() {
		return getDataTypes().stream().sorted(NamedElementComparator.INSTANCE);
	}

	/**
	 * get type
	 *
	 * @param name the name
	 *
	 * @return the type or ErrorMarker
	 */
	public DataType getType(final String name) {
		if (null == name) {
			return GenericTypes.ANY;
		}
		DataType type = getTypeIfExists(name);
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
		final String uppercaseName = name.toUpperCase();
		final DataType dataType = typeMap.get(uppercaseName);
		if (dataType != null) {
			return dataType;
		}
		final DataTypeEntry entry = derivedTypes.get(uppercaseName);
		if (entry != null && !entry.hasError()) {
			return entry.getType();
		}
		return null;
	}

	public List<StructuredType> getStructuredTypes() {
		return Stream.concat(Stream.of(GenericTypes.ANY_STRUCT), derivedTypes.values().stream().map(TypeEntry::getType)
				.filter(StructuredType.class::isInstance).map(StructuredType.class::cast)).toList();
	}

	public List<StructuredType> getStructuredTypesSorted() {
		return getStructuredTypes().stream().sorted(NamedElementComparator.INSTANCE).toList();
	}

	public DataTypeEntry getDerivedTypeEntry(final String name) {
		return derivedTypes.get(name.toUpperCase());
	}

	private DataType createParametricType(final String typeName) {
		final Matcher matcher = STRING_MAX_LENGTH_PATTERN.matcher(typeName);
		if (matcher.matches()) {
			try {
				final String plainTypeName = matcher.group(1);
				final String maxLengthString = matcher.group(2);
				final DataType plainType = typeMap.get(plainTypeName.toUpperCase());
				if (plainType instanceof AnyStringType) {
					final int maxLength = Integer.parseUnsignedInt(maxLengthString);
					return typeMap.computeIfAbsent(typeName.toUpperCase(), name -> {
						final AnyStringType type = (AnyStringType) DataFactory.eINSTANCE.create(plainType.eClass());
						type.setName(name);
						type.setMaxLength(maxLength);
						encloseInResource(type);
						return type;
					});
				}
			} catch (final NumberFormatException e) {
				return createErrorMarkerType(typeName,
						MessageFormat.format(Messages.DataTypeLibrary_InvalidMaxLengthInStringType, typeName));
			}
		}
		return null;
	}

	private AnyDerivedType createErrorMarkerType(final String typeName, final String message) {
		return derivedTypes.computeIfAbsent(typeName.toUpperCase(), name -> {
			FordiacLogHelper.logInfo(message);
			final DataTypeEntry entry = new DataTypeEntryImpl();
			final DataType type = entry.getType();
			PackageNameHelper.setFullTypeName(type, typeName);
			entry.setType(type); // update type name in entry
			entry.setTypeLibrary(typeLibrary);
			return entry;
		}).getType();
	}

	private static void encloseInResource(final DataType type) {
		new ResourceImpl(URI.createFileURI(type.getName() + ".datalib.dtp")).getContents().add(type); //$NON-NLS-1$
	}

	public StructuredType getStructuredType(final String name) {
		final DataType derivedType = getTypeIfExists(name);
		if (derivedType instanceof final StructuredType structuredType) {
			return structuredType;
		}
		return GenericTypes.ANY_STRUCT;
	}

	void clear() {
		derivedTypes.clear();
	}
}
