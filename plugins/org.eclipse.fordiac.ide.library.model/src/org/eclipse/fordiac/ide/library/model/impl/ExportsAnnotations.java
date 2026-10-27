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
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.model.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.library.model.Messages;
import org.eclipse.fordiac.ide.library.model.library.Exports;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;

public final class ExportsAnnotations {

	public static boolean validateUniqueSymbolicNames(final Exports exports, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {

		final EList<Library> libraries = exports.getLibrary();

		if (libraries == null) {
			return true;
		}

		final Map<String, Long> counts = libraries.stream()
				.collect(Collectors.groupingBy(Library::getSymbolicName, HashMap::new, Collectors.counting()));

		boolean valid = true;

		for (final Library library : libraries) {
			final String name = library.getSymbolicName();

			if (counts.get(name).intValue() > 1) {
				valid = false;
				if (diagnostics != null) {
					diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryValidator.DIAGNOSTIC_SOURCE,
							LibraryValidator.EXPORTS__VALIDATE_UNIQUE_SYMBOLIC_NAMES,
							MessageFormat.format(Messages.ExportAnnotations_DuplicateLibrary, name), FordiacMarkerHelper
									.getDiagnosticData(library, LibraryPackage.Literals.LIBRARY__SYMBOLIC_NAME, name)));
				}
			}
		}

		return valid;
	}

	private ExportsAnnotations() {
		/* This utility class should not be instantiated */
	}
}
