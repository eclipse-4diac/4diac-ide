/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
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
package org.eclipse.fordiac.ide.deployment.debug.snapshot;

import java.util.List;
import java.util.Objects;

import org.eclipse.fordiac.ide.model.eval.variable.FBVariable;

/**
 * Values of a function block instance read from the runtime.
 *
 * @param variable the variable of the function block with the values from the
 *                 runtime; internal function blocks are its FB variables, the
 *                 ECC state of a basic function block is its {@code __STATE};
 *                 variables without a usable value keep their initial value
 * @param missing  the paths of the variables without a usable value relative to
 *                 the function block, e.g. {@code COUNT} or {@code trig.MEM}
 */
public record FBSnapshot(FBVariable variable, List<String> missing) {

	public FBSnapshot {
		Objects.requireNonNull(variable);
		missing = List.copyOf(Objects.requireNonNull(missing));
	}
}
