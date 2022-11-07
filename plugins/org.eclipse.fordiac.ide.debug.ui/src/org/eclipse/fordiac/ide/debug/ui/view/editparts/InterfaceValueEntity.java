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

import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

public class InterfaceValueEntity {

	private final IInterfaceElement ie;
	private final Variable<?> variable;

	public InterfaceValueEntity(final IInterfaceElement ie, final Variable<?> variable) {
		super();
		this.ie = ie;
		this.variable = variable;
	}

	public Variable<?> getVariable() {
		return variable;
	}

	public IInterfaceElement getInterfaceElement() {
		return ie;
	}

}
