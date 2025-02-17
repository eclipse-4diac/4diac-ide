/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public final class ImportHelper {

	public static final String PACKAGE_NAME_DELIMITER = "::"; //$NON-NLS-1$

	public static final String WILDCARD_IMPORT = "*"; //$NON-NLS-1$
	public static final String WILDCARD_IMPORT_SUFFIX = PACKAGE_NAME_DELIMITER + WILDCARD_IMPORT;

	public static List<Import> getImports(final LibraryElement libraryElement) {
		if (libraryElement != null) {
			final CompilerInfo compilerInfo = libraryElement.getCompilerInfo();
			if (compilerInfo != null) {
				return compilerInfo.getImports();
			}
		}
		return Collections.emptyList();
	}

	public static EList<Import> getMutableImports(final LibraryElement libraryElement) {
		CompilerInfo compilerInfo = libraryElement.getCompilerInfo();
		if (compilerInfo == null) {
			compilerInfo = LibraryElementFactory.eINSTANCE.createCompilerInfo();
			libraryElement.setCompilerInfo(compilerInfo);
		}
		return compilerInfo.getImports();
	}

	public static List<Import> getContainerImports(final EObject object) {
		if (EcoreUtil.getRootContainer(object) instanceof final LibraryElement libraryElement) {
			return getImports(libraryElement);
		}
		return Collections.emptyList();
	}

	public static <T> T resolveImport(final String name, final EObject context, final Function<String, T> typeResolver,
			final Function<String, T> fallbackTypeResolver) {
		final String packageName = PackageNameHelper.getContainerPackageName(context);
		final List<Import> imports = ImportHelper.getContainerImports(context);
		return resolveImport(name, packageName, imports, typeResolver, fallbackTypeResolver);
	}

	public static <T> T resolveImport(final String name, final String packageName, final List<Import> imports,
			final Function<String, T> typeResolver, final Function<String, T> fallbackTypeResolver) {
		// resolve name directly
		T result = typeResolver.apply(name);
		if (result != null) {
			return result;
		}

		// resolve name in current package (if present)
		if (packageName != null && !packageName.isEmpty()) {
			result = typeResolver.apply(packageName + PACKAGE_NAME_DELIMITER + name);
			if (result != null) {
				return result;
			}
		}

		// resolve imports
		for (final Import imp : imports) {
			String importedNamespace = imp.getImportedNamespace();
			if (importedNamespace.endsWith(WILDCARD_IMPORT_SUFFIX)) { // wildcard import?
				importedNamespace = importedNamespace.substring(0, importedNamespace.length() - 3)
						+ PACKAGE_NAME_DELIMITER + name;
			} else if (!importedNamespace.endsWith(PACKAGE_NAME_DELIMITER + name)) { // import matches exactly?
				continue; // otherwise, continue
			}
			final T imported = typeResolver.apply(importedNamespace);
			if (imported != null) {
				if (result != null && result != imported) { // check ambiguous import
					return null;
				}
				result = imported;
			}
		}
		if (result != null) {
			return result;
		}

		return fallbackTypeResolver.apply(name);
	}

	public static String deresolveImport(final LibraryElement imported, final EObject context) {
		if (imported == null) {
			return null;
		}
		if (PackageNameHelper.getPackageName(imported).isEmpty()) {
			return imported.getName();
		}
		final String importedName = PackageNameHelper.getFullTypeName(imported);
		final String packageName = PackageNameHelper.getContainerPackageName(context);
		final List<Import> imports = ImportHelper.getContainerImports(context);
		return deresolveImport(importedName, packageName, imports);
	}

	public static String deresolveImport(final String importedName, final String packageName,
			final List<Import> imports) {
		final String name = PackageNameHelper.extractPlainTypeName(importedName);

		// deresolve name in current package (if present)
		if (packageName != null && !packageName.isEmpty()
				&& importedName.equalsIgnoreCase(packageName + PACKAGE_NAME_DELIMITER + name)) {
			return name;
		}

		// deresolve imports
		for (final Import imp : imports) {
			final String importedNamespace = imp.getImportedNamespace();
			if (importedName.equalsIgnoreCase(importedNamespace) // exact match
					|| (importedNamespace.endsWith(WILDCARD_IMPORT_SUFFIX) // or wildcard import
							&& importedName
									.equalsIgnoreCase(importedNamespace.substring(0, importedNamespace.length() - 3)
											+ PACKAGE_NAME_DELIMITER + name))) { // and full name matches prefix
				return name;
			}
		}

		return importedName;
	}

	public static void organizeImports(final LibraryElement libraryElement, final Set<String> usedTypes) {
		final String packageName = PackageNameHelper.getPackageName(libraryElement);
		final EList<Import> imports = getMutableImports(libraryElement);

		final Set<String> imported = new HashSet<>(usedTypes.stream()
				// skip implicit imports
				.filter(imp -> !isImplicitImport(imp, packageName))
				// create map with simple names as keys and merge imported types
				// (avoids ambiguous imports)
				.collect(Collectors.toMap(PackageNameHelper::extractPlainTypeName, Function.identity(), //
						(a, b) -> mergeImportedTypes(a, b, imports)))
				// construct new set from values
				.values());
		// remove unnecessary imports (and existing imports from imported)
		imports.removeIf(imp -> !imported.remove(imp.getImportedNamespace()));
		// create and add new imports based on imported
		imported.stream().map(ImportHelper::createImport).forEachOrdered(imports::add);
		// sort imports
		ECollections.sort(imports, Comparator.comparing(Import::getImportedNamespace));
	}

	private static String mergeImportedTypes(final String first, final String second, final List<Import> imports) {
		if (matchesImports(first, imports)) {
			return first;
		}
		if (matchesImports(second, imports)) {
			return second;
		}
		return null; // skip colliding names
	}

	public static boolean matchesImports(final String name, final LibraryElement libraryElement) {
		return getImports(libraryElement).stream().anyMatch(imp -> matchesImport(name, imp));
	}

	public static boolean matchesImports(final String name, final List<Import> imports) {
		return imports.stream().anyMatch(imp -> matchesImport(name, imp));
	}

	private static boolean matchesImport(final String name, final Import imp) {
		final String importedNamespace = imp.getImportedNamespace();
		return importedNamespace.equalsIgnoreCase(name) || (importedNamespace.endsWith(WILDCARD_IMPORT_SUFFIX)
				&& PackageNameHelper.extractPackageName(importedNamespace)
						.equalsIgnoreCase(PackageNameHelper.extractPackageName(name)));
	}

	public static boolean isImplicitImport(final String imported, final String packageName) {
		final int lastDelimiterIndex = imported.lastIndexOf(PACKAGE_NAME_DELIMITER);
		return lastDelimiterIndex < 0 || imported.substring(0, lastDelimiterIndex).equals(packageName);
	}

	protected static Import createImport(final String importedNamespace) {
		final Import result = LibraryElementFactory.eINSTANCE.createImport();
		result.setImportedNamespace(importedNamespace);
		return result;
	}

	private ImportHelper() {
		throw new UnsupportedOperationException();
	}
}
