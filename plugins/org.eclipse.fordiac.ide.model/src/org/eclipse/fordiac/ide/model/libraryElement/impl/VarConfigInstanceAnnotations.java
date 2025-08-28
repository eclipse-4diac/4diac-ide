/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Stemmer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.VarConfigInstance;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public class VarConfigInstanceAnnotations {
	private VarConfigInstanceAnnotations() {
		throw new UnsupportedOperationException();
	}

	public static boolean validateName(final VarConfigInstance instance, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		final Optional<String> err = IdentifierVerifier.verifyQualifiedIdentifier(instance.getName());
		if (err.isPresent()) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.VAR_CONFIG_INSTANCE__VALIDATE_NAME, err.get(), FordiacMarkerHelper
								.getDiagnosticData(instance, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME)));
			}
			return false;
		}
		return true;
	}
}