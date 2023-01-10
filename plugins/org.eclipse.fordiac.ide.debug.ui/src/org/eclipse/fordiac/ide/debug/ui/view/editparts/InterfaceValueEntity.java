/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.ui.view.editparts;

import org.eclipse.fordiac.ide.debug.EvaluatorDebugTarget;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class InterfaceValueEntity {

	private final IInterfaceElement ie;
	private final Variable<?> variable;
	private final EvaluatorDebugTarget debugTarget;

	public InterfaceValueEntity(final IInterfaceElement ie, final Variable<?> variable,
			final EvaluatorDebugTarget debugTarget) {
		super();
		this.ie = ie;
		this.variable = variable;
		this.debugTarget = debugTarget;
	}

	public Variable<?> getVariable() {
		return variable;
	}

	public IInterfaceElement getInterfaceElement() {
		return ie;
	}

	public void updateValue(final String newValue) {
		final EvaluatorDebugVariable debugVar = debugTarget.getDebugger().getVariable(getVariable());
		debugVar.setValue(newValue);
	}

}
