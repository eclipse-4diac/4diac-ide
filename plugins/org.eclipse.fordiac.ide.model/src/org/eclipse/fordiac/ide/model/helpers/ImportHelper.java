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
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

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

	private ImportHelper() {
		throw new UnsupportedOperationException();
	}
}
