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
package org.eclipse.fordiac.ide.deployment.debug.watch;

import org.eclipse.debug.core.DebugException;
import org.eclipse.fordiac.ide.model.eval.value.AnyValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public interface IVarDeclarationWatch extends IInterfaceElementWatch, IVariableWatch {

	/**
	 * {@inheritDoc}
	 */
	@Override
	VarDeclaration getWatchedElement();

	/**
	 * {@inheritDoc}
	 */
	@Override
	AnyValue getInternalValue();

	/**
	 * Set the value
	 *
	 * @param value The value to set
	 * @throws DebugException if there is a problem setting the value
	 */
	void setValue(Value value) throws DebugException;

	/**
	 * Force the variable value
	 *
	 * @param value The forced value
	 * @throws DebugException if there is a problem forcing the value
	 */
	void forceValue(final String value) throws DebugException;

	/**
	 * Force the variable value
	 *
	 * @param value The forced value
	 * @throws DebugException if there is a problem forcing the value
	 */
	void forceValue(final Value value) throws DebugException;

	/**
	 * Clear the force of the value
	 *
	 * @throws DebugException if there is a problem clearing the force
	 */
	void clearForce() throws DebugException;

	/**
	 * Check whether the value is forced
	 *
	 * @return true if forced, false otherwise
	 */
	boolean isForced();
}
