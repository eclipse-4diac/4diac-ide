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

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public final class ErrorMarkerInterfaceAnnotations {

	public static boolean validateValue(final ErrorMarkerInterface element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (element.getValue() != null && element.getValue().getValue() != null
				&& !element.getValue().getValue().isBlank()) {
			if (diagnostics != null) {
				diagnostics
						.add(new BasicDiagnostic(Diagnostic.ERROR, LibraryElementValidator.DIAGNOSTIC_SOURCE,
								LibraryElementValidator.ERROR_MARKER_INTERFACE__VALIDATE_VALUE,
								MessageFormat.format(Messages.ErrorMarkerInterfaceAnnotations_MissingVariableForValue,
										element.getValue().getValue()),
								FordiacMarkerHelper.getDiagnosticData(element)));
			}
			return false;
		}
		return true;
	}

	public static boolean validateAttributes(final ErrorMarkerInterface element, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (!element.getAttributes().isEmpty()) {
			if (diagnostics != null) {
				element.getAttributes()
						.forEach(attribute -> diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
								LibraryElementValidator.DIAGNOSTIC_SOURCE,
								LibraryElementValidator.ERROR_MARKER_INTERFACE__VALIDATE_ATTRIBUTES,
								MessageFormat.format(Messages.ErrorMarkerInterfaceAnnotations_MissingVariableForAttribute, attribute.getName()),
								FordiacMarkerHelper.getDiagnosticData(element))));
			}
			return false;
		}
		return true;
	}

	private ErrorMarkerInterfaceAnnotations() {
		throw new UnsupportedOperationException();
	}
}
