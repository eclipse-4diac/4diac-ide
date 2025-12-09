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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public final class CompilerInfoAnnotations {
	public static boolean validatePackageName(final CompilerInfo compilerInfo, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		final Optional<String> errorMessage = IdentifierVerifier.verifyPackageName(compilerInfo.getPackageName());
		if (errorMessage.isPresent()) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.COMPILER_INFO__VALIDATE_PACKAGE_NAME, errorMessage.get(),
						FordiacMarkerHelper.getDiagnosticData(compilerInfo,
								LibraryElementPackage.Literals.COMPILER_INFO__PACKAGE_NAME,
								compilerInfo.getPackageName())));
			}
			return false;
		}
		return true;
	}

	private CompilerInfoAnnotations() {
		throw new UnsupportedOperationException("Helper class must not be instantiated"); //$NON-NLS-1$
	}
}
