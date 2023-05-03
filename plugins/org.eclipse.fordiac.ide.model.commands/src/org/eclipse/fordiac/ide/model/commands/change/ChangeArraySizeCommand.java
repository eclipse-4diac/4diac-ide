/*******************************************************************************
 * Copyright (c) 2012 - 2016 TU Wien ACIN, fortiss GmbH
 *               2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst - add value validation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class ChangeArraySizeCommand extends AbstractChangeInterfaceElementCommand {
	private final String newArraySize;
	private String oldArraySize;

	public ChangeArraySizeCommand(final VarDeclaration variable, final String newArraySize) {
		super(variable);
		this.newArraySize = newArraySize;
	}

	@Override
	public boolean canExecute() {
		return (null != getInterfaceElement());
	}

	@Override
	protected void doExecute() {
		final VarDeclaration variable = getInterfaceElement();
		oldArraySize = variable.getArraySize();
		setArraySize(newArraySize);
	}

	@Override
	protected void doUndo() {
		setArraySize(oldArraySize);
	}

	@Override
	protected void doRedo() {
		setArraySize(newArraySize);
	}

	@Override
	public VarDeclaration getInterfaceElement() {
		return (VarDeclaration) super.getInterfaceElement();
	}

	private void setArraySize(final String arraySize) {
		getInterfaceElement().setArraySize(arraySize);
	}
}
