/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug;

import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public interface IEvaluatorDebugTarget extends IDebugTarget {

	/**
	 * Get the variable update count
	 *
	 * @return The count
	 */
	long getVariableUpdateCount();

	/**
	 * Get the type library
	 *
	 * @return The type library
	 */
	TypeLibrary getTypeLibrary();

	default EvaluatorDebugVariable createVariable(final Variable<?> variable, final String expression) {
		return new EvaluatorDebugVariable(variable, expression, this);
	}
}
