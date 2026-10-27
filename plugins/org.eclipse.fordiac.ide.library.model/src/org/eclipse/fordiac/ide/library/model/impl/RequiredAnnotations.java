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

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.library.model.Messages;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;

public final class RequiredAnnotations {

	public static boolean validateVersionRange(final Required required, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {

		if (required.getVersion() != null && VersionComparator.isValidRange(required.getVersion())) {
			return true;
		}

		if (diagnostics != null) {
			diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryValidator.DIAGNOSTIC_SOURCE,
					LibraryValidator.REQUIRED__VALIDATE_VERSION_RANGE,
					MessageFormat.format(Messages.VersionValidaton_DeclarationError, required.getVersion(),
							required.getSymbolicName()),
					FordiacMarkerHelper.getDiagnosticData(required, LibraryPackage.Literals.REQUIRED__VERSION,
							required.getVersion())));
		}

		return false;
	}

	private RequiredAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
