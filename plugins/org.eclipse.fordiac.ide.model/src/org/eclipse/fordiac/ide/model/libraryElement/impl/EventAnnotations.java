/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
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

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;
import org.eclipse.fordiac.ide.model.validation.ValidationPreferences;

public final class EventAnnotations {

	public static boolean validateMultipleOutputConnections(final Event event, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {
		if (event.getOutputConnections().size() > 1) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(
						ValidationPreferences.getDiagnosticSeverity(
								ValidationPreferences.EVENT_MULTIPLE_OUTPUT_CONNECTIONS, Diagnostic.OK, event),
						LibraryElementValidator.DIAGNOSTIC_SOURCE,
						LibraryElementValidator.EVENT__VALIDATE_MULTIPLE_OUTPUT_CONNECTIONS,
						Messages.EventAnnotations_MultipleOutputConnections, FordiacMarkerHelper.getDiagnosticData(
								event, LibraryElementPackage.Literals.IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS)));
			}
			return false;
		}
		return true;
	}

	private EventAnnotations() {
		throw new UnsupportedOperationException();
	}
}
