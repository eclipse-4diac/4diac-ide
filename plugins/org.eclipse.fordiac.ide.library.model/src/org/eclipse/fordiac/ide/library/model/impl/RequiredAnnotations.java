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

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.library.model.LibraryDiagnostic;
import org.eclipse.fordiac.ide.library.model.Messages;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;

public final class RequiredAnnotations {

	public static boolean validateVersionRange(final Required required, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {

		if (required.getVersion() != null && VersionComparator.isValidRange(required.getVersion())) {
			return true;
		}

		if (diagnostics != null) {
			final String message = MessageFormat.format(Messages.VersionValidaton_DeclarationError,
					required.getVersion(), required.getSymbolicName());
			diagnostics.add(LibraryDiagnostic.error(message, LibraryValidator.REQUIRED__VALIDATE_VERSION_RANGE,
					required, LibraryPackage.Literals.REQUIRED__VERSION, required.getVersion()));
		}

		return false;
	}

	private RequiredAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
