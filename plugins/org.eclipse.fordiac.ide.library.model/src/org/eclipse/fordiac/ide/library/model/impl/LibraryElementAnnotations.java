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
import java.util.regex.Pattern;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.library.model.Messages;
import org.eclipse.fordiac.ide.library.model.library.LibraryElement;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;

public final class LibraryElementAnnotations {

	public static boolean validatePattern(final LibraryElement element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {

		final String pattern = element.getValue();

		if (pattern != null && isValidPattern(pattern)) {
			return true;
		}

		if (diagnostics != null) {
			diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryValidator.DIAGNOSTIC_SOURCE,
					LibraryValidator.LIBRARY_ELEMENT__VALIDATE_PATTERN,
					MessageFormat.format(Messages.LibraryElementAnnotations_InvalidPattern, pattern),
					FordiacMarkerHelper.getDiagnosticData(element, LibraryPackage.Literals.LIBRARY_ELEMENT__VALUE,
							pattern)));
		}

		return false;
	}

	private static boolean isValidPattern(final String pattern) {
		final Pattern packageSegmentPattern = Pattern.compile("(?:\\*{1,2}|\\w+)"); //$NON-NLS-1$
		final String[] segments = pattern.split(PackageNameHelper.PACKAGE_NAME_DELIMITER);
		for (final String segment : segments) {
			if (!packageSegmentPattern.matcher(segment).matches()) {
				return false;
			}
		}
		return true;
	}

	private LibraryElementAnnotations() {
		/* This utility class should not be instantiated */
	}

}
