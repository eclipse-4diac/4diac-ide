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
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.library.model.LibraryDiagnostic;
import org.eclipse.fordiac.ide.library.model.Messages;
import org.eclipse.fordiac.ide.library.model.library.Dependencies;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;

public final class DependenciesAnnotations {

	public static boolean validateUniqueSymbolicNames(final Dependencies dependencies,
			final DiagnosticChain diagnostics, final Map<Object, Object> context) {

		final EList<Required> required = dependencies.getRequired();

		if (required == null) {
			return true;
		}

		final Map<String, Long> counts = required.stream().map(Required::getSymbolicName).filter(Objects::nonNull)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		boolean valid = true;

		for (final Required req : required) {
			final String name = req.getSymbolicName();

			if (counts.getOrDefault(name, Long.valueOf(0)).intValue() > 1) {
				valid = false;
				if (diagnostics != null) {
					final String message = MessageFormat.format(Messages.DependenciesAnnotations_DuplicateSymName,
							name);
					diagnostics.add(LibraryDiagnostic.error(message,
							LibraryValidator.DEPENDENCIES__VALIDATE_UNIQUE_SYMBOLIC_NAMES, req,
							LibraryPackage.Literals.REQUIRED__SYMBOLIC_NAME, name));
				}
			}
		}

		return valid;
	}

	private DependenciesAnnotations() {
		/* This utility class should not be instantiated */
	}
}
