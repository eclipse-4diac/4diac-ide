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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.library.model.LibraryDiagnostic;
import org.eclipse.fordiac.ide.library.model.Messages;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.Product;
import org.eclipse.fordiac.ide.library.model.library.VersionInfo;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;
import org.osgi.framework.Version;

public final class VersionInfoAnnotations {

	public static boolean validateVersionInfo(final VersionInfo versionInfo, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {

		boolean result = validateVersion(versionInfo, diagnostics);
		result &= validateDate(versionInfo, diagnostics);

		return result;
	}

	private static boolean validateDate(final VersionInfo versionInfo, final DiagnosticChain diagnostics) {
		if (versionInfo.getDate() == null || isValidDate(versionInfo.getDate())) {
			return true;
		}
		if (diagnostics != null) {
			final String message = MessageFormat.format(Messages.VersionInfoAnnotations_InvalidDate,
					versionInfo.getDate());
			diagnostics.add(LibraryDiagnostic.error(message, LibraryValidator.VERSION_INFO__VALIDATE_VERSION_INFO,
					versionInfo, LibraryPackage.Literals.VERSION_INFO__DATE, versionInfo.getDate()));
		}
		return false;
	}

	private static boolean validateVersion(final VersionInfo versionInfo, final DiagnosticChain diagnostics) {
		if (versionInfo.getVersion() == null || isValidVersion(versionInfo.getVersion())) {
			return true;
		}
		if (diagnostics != null) {
			final String message = MessageFormat.format(Messages.VersionValidaton_DeclarationError,
					versionInfo.getVersion(), getProductName(versionInfo));
			diagnostics.add(LibraryDiagnostic.error(message, LibraryValidator.VERSION_INFO__VALIDATE_VERSION_INFO,
					versionInfo, LibraryPackage.Literals.VERSION_INFO__VERSION, versionInfo.getVersion()));
		}
		return false;
	}

	public static boolean isValidVersion(final String value) {
		if (value.isBlank()) {
			return false;
		}

		try {
			final Version version = new Version(value);
			return version.getQualifier().isEmpty() && !Version.emptyVersion.equals(version);
		} catch (final IllegalArgumentException _) {
			return false;
		}
	}

	private static boolean isValidDate(final String value) {
		if (value.isBlank()) {
			return false;
		}

		try {
			LocalDate.parse(value);
		} catch (final DateTimeParseException _) {
			return false;
		}

		return true;
	}

	private static String getProductName(final VersionInfo versionInfo) {
		if (versionInfo.eContainer() instanceof final Product product) {
			return product.getSymbolicName();
		}
		return ""; //$NON-NLS-1$
	}

	private VersionInfoAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}

}
