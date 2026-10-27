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
package org.eclipse.fordiac.ide.library.model;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryValidator;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;

public class LibraryDiagnostic {

	public static Diagnostic error(final String message, final int code, final EObject element,
			final EStructuralFeature feature, final String value) {
		return create(message, Diagnostic.ERROR, code, element, feature, value);
	}

	public static Diagnostic warning(final String message, final int code, final EObject element,
			final EStructuralFeature feature, final String value) {
		return create(message, Diagnostic.WARNING, code, element, feature, value);
	}

	public static Diagnostic info(final String message, final int code, final EObject element,
			final EStructuralFeature feature, final String value) {
		return create(message, Diagnostic.INFO, code, element, feature, value);
	}

	private static Diagnostic create(final String message, final int severity, final int code, final EObject element,
			final EStructuralFeature feature, final String value) {
		return new BasicDiagnostic(severity, LibraryValidator.DIAGNOSTIC_SOURCE, code, message,
				FordiacMarkerHelper.getDiagnosticData(element, feature, value));
	}

	private LibraryDiagnostic() {
		/* This utility class should not be instantiated */
	}
}
