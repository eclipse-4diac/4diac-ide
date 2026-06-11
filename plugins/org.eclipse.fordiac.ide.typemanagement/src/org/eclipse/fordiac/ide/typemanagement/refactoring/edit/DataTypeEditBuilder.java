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
package org.eclipse.fordiac.ide.typemanagement.refactoring.edit;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.search.types.DataTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateConfigurableFBModelEdit;

public class DataTypeEditBuilder {

	public static void createStructuredDataTypeChanges(final DataTypeEntry dataTypeEntry,
			final List<ModelEdit<?>> modelEdits, final String targetTypeName) {
		DataTypeInstanceSearch.createNonDerivedDataTypeSearch(dataTypeEntry).performSearch().forEach(obj -> {
			if (obj instanceof final VarDeclaration varDecl) {
				modelEdits.add(new DataTypeEdit(Messages.MoveTypeToPackage_UpdateDataTypeInstance,
						EcoreUtil.getURI(varDecl), targetTypeName));
			} else if (obj instanceof final ConfigurableFB configurableFB) {
				modelEdits.add(new UpdateConfigurableFBModelEdit(configurableFB, dataTypeEntry));
			}
		});
	}

	public static void createStructuredDataTypeImportChanges(final DataTypeEntry dataTypeEntry,
			final List<ModelEdit<?>> modelEdits, final String targetTypeName) {
		final Set<URI> handledImports = new HashSet<>();
		DataTypeInstanceSearch.createNonDerivedDataTypeSearch(dataTypeEntry).performSearch()
				.forEach(obj -> addImportChanges(obj, modelEdits, dataTypeEntry.getFullTypeName(), targetTypeName,
						handledImports));
	}

	private static void addImportChanges(final EObject object, final List<ModelEdit<?>> modelEdits,
			final String sourceTypeName, final String targetTypeName, final Set<URI> handledImports) {
		if (!(EcoreUtil.getRootContainer(object) instanceof final LibraryElement libraryElement)
				|| libraryElement.getCompilerInfo() == null) {
			return;
		}

		for (final Import imp : libraryElement.getCompilerInfo().getImports()) {
			if (sourceTypeName.equals(imp.getImportedNamespace())) {
				final URI uri = EcoreUtil.getURI(imp);
				if (handledImports.add(uri)) {
					final String newValue = hasImport(libraryElement, targetTypeName, imp) ? null : targetTypeName;
					modelEdits.add(new ImportEdit(Messages.ImportChange_ImportedNamespaceChanged, uri, newValue));
				}
			}
		}
	}

	private static boolean hasImport(final LibraryElement libraryElement, final String targetTypeName,
			final Import currentImport) {
		return libraryElement.getCompilerInfo().getImports().stream()
				.anyMatch(imp -> imp != currentImport && Objects.equals(imp.getImportedNamespace(), targetTypeName));
	}

	public static String getFullTypeName(final TypeEntry typeEntry, final IPath newPath) {
		return getFullTypeName(typeEntry.getPackageName(), newPath);
	}

	public static String getFullTypeName(final IPath newPath) {
		final String packageName = PackageNameHelper
				.getPackageNameFromURI(URI.createPlatformResourceURI(newPath.toString(), true));
		return getFullTypeName(packageName, newPath);
	}

	private static String getFullTypeName(final String packageName, final IPath newPath) {
		final String typeName = TypeEntry.getTypeNameFromFileName(newPath.lastSegment());
		return getFullTypeName(packageName, typeName);
	}

	public static String getFullTypeName(final String packageName, final String typeName) {
		if (packageName.isEmpty()) {
			return typeName;
		}
		return packageName + PackageNameHelper.PACKAGE_NAME_DELIMITER + typeName;
	}

}
