/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler Universiy Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.text.MessageFormat;
import java.util.Map;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.util.LibraryElementValidator;

public class SimpleECStateAnnotations {

	static boolean validateActionsNonEmpty(final SimpleECState state, final DiagnosticChain diagnostics,
			final Map<Object, Object> context) {

		final boolean valid = !state.getSimpleECActions().isEmpty();
		if (!valid) {
			diagnostics.add(new BasicDiagnostic(Diagnostic.WARNING, LibraryElementValidator.DIAGNOSTIC_SOURCE,
					LibraryElementValidator.SIMPLE_EC_STATE__VALIDATE_ACTIONS_NON_EMPTY,
					MessageFormat.format(Messages.SimpleECStateAnnotations_EmptyActions, state.getName()),
					FordiacMarkerHelper.getDiagnosticData(state, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME)));
		}
		return valid;
	}

	private SimpleECStateAnnotations() {
		/* This utility class should not be instantiated */
	}
}
