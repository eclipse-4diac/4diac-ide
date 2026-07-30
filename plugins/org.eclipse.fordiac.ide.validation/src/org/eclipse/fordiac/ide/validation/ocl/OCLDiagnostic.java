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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.validation.ocl;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;

public record OCLDiagnostic(String message, int severity, EObject markerTarget) {

	public OCLDiagnostic {
		Objects.requireNonNull(message);
		Objects.requireNonNull(markerTarget);
	}
}
