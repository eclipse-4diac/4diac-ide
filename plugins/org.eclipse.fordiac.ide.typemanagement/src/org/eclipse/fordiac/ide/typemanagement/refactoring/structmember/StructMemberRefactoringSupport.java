/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

final class StructMemberRefactoringSupport {

	static TypeEntry getTypeEntry(final URI uri) {
		return TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri);
	}

	static StructuredType getStructType(final URI uri) {
		final TypeEntry typeEntry = getTypeEntry(uri);
		return typeEntry != null && typeEntry.getType() instanceof final StructuredType type ? type : null;
	}

	static boolean isWritable(final TypeEntry typeEntry) {
		return typeEntry != null && typeEntry.getFile() != null && !typeEntry.getFile().isReadOnly();
	}

	static DataType resolveDataType(final TypeLibrary typeLibrary, final String fullTypeName) {
		if (typeLibrary == null || fullTypeName == null || fullTypeName.isBlank()) {
			return null;
		}
		final DataType result = typeLibrary.getDataTypeLibrary().getTypeIfExists(fullTypeName);
		return result instanceof ErrorDataType ? null : result;
	}

	static boolean containsMember(final StructuredType type, final String name) {
		return type.getMemberVariables().stream().anyMatch(member -> member.getName().equalsIgnoreCase(name));
	}

	static int getInsertionIndex(final StructuredType type, final String insertBefore) {
		if (insertBefore == null) {
			return type.getMemberVariables().size();
		}
		for (int index = 0; index < type.getMemberVariables().size(); index++) {
			if (type.getMemberVariables().get(index).getName().equalsIgnoreCase(insertBefore)) {
				return index;
			}
		}
		return -1;
	}

	static boolean createsRecursiveType(final StructuredType parentType, final DataType memberType) {
		return memberType instanceof final StructuredType structuredMember
				&& referencesType(structuredMember, PackageNameHelper.getFullTypeName(parentType), new HashSet<>());
	}

	private static boolean referencesType(final StructuredType type, final String searchedType,
			final Set<String> visitedTypes) {
		final String currentType = PackageNameHelper.getFullTypeName(type);
		if (searchedType.equals(currentType)) {
			return true;
		}
		if (!visitedTypes.add(currentType)) {
			return false;
		}
		for (final VarDeclaration member : type.getMemberVariables()) {
			if (member.getType() instanceof final StructuredType nestedType
					&& referencesType(nestedType, searchedType, visitedTypes)) {
				return true;
			}
		}
		return false;
	}

	static boolean isConcreteType(final DataType dataType) {
		return dataType != null && !(dataType instanceof ErrorDataType) && !GenericTypes.isAnyType(dataType);
	}

	private StructMemberRefactoringSupport() {
		throw new UnsupportedOperationException();
	}
}
