/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.debug.value;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.debug.IEvaluatorDebugTarget;

public interface IEvaluatorDebugValue extends IValue {

	/**
	 * {@inheritDoc}
	 */
	@Override
	String getReferenceTypeName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	String getValueString();

	/**
	 * {@inheritDoc}
	 */
	@Override
	boolean isAllocated();

	/**
	 * {@inheritDoc}
	 */
	@Override
	IVariable[] getVariables();

	/**
	 * {@inheritDoc}
	 */
	@Override
	boolean hasVariables();

	/**
	 * Get a sub-variable from this variable
	 *
	 * @param name The name of the variable
	 * @return The variable or null if not found
	 */
	EvaluatorDebugVariable getVariable(final String name);

	/**
	 * {@inheritDoc}
	 */
	@Override
	IEvaluatorDebugTarget getDebugTarget();
}
