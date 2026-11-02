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
import java.util.Optional;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.library.model.LibraryDiagnostic;
import org.eclipse.fordiac.ide.library.model.Messages;
import org.eclipse.fordiac.ide.library.model.library.LibraryElement;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;

public final class LibraryElementAnnotations {

	public static boolean validatePattern(final LibraryElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {

		final String pattern = element.getValue();

		if (pattern != null && isValidPattern(pattern)) {
			return true;
		}

		if (diagnostics != null) {
			final String message = MessageFormat.format(Messages.LibraryElementAnnotations_InvalidPattern, pattern);
			diagnostics.add(LibraryDiagnostic.error(message, LibraryValidator.LIBRARY_ELEMENT__VALIDATE_PATTERN,
					element, LibraryPackage.Literals.LIBRARY_ELEMENT__VALUE, pattern));
		}

		return false;
	}

	private static boolean isValidPattern(final String pattern) {
		final String[] segments = pattern.split(PackageNameHelper.PACKAGE_NAME_DELIMITER, -1);
		for (final String segment : segments) {
			if ("**".equals(segment) || "*".equals(segment)) { //$NON-NLS-1$ //$NON-NLS-2$
				continue;
			}
			final Optional<String> message = IdentifierVerifier.verifyIdentifier(segment);
			if (message.isPresent()) {
				return false;
			}
		}
		return true;
	}

	private LibraryElementAnnotations() {
		/* This utility class should not be instantiated */
	}

}
