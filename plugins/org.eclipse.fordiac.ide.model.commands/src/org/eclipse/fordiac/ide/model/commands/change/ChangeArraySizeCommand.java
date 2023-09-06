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

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.getArraySize;
import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.setArraySize;

import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.CompoundCommand;

public class ChangeArraySizeCommand extends AbstractChangeInterfaceElementCommand {
	private final String newArraySize;
	private String oldArraySize;
	private final CompoundCommand additionalCommands = new CompoundCommand();

	protected ChangeArraySizeCommand(final VarDeclaration variable, final String newArraySize) {
		super(variable);
		this.newArraySize = newArraySize;
	}

	public static ChangeArraySizeCommand forArraySize(final VarDeclaration variable, final String newArraySize) {
		final ChangeArraySizeCommand result = new ChangeArraySizeCommand(variable, newArraySize);
		if (variable != null && variable.getFBNetworkElement() instanceof final SubApp subApp && subApp.isMapped()) {
			result.getAdditionalCommands().add(new ChangeArraySizeCommand(
					subApp.getOpposite().getInterface().getVariable(variable.getName()), newArraySize));
		}
		return result;
	}

	@Override
	public boolean canExecute() {
		return super.canExecute() && !isSubAppPinAndConnected();
	}

	@Override
	protected void doExecute() {
		final VarDeclaration variable = getInterfaceElement();
		oldArraySize = getArraySize(variable);
		setArraySize(variable, newArraySize);
		additionalCommands.execute();
	}

	@Override
	protected void doUndo() {
		additionalCommands.undo();
		setArraySize(getInterfaceElement(), oldArraySize);
	}

	@Override
	protected void doRedo() {
		setArraySize(getInterfaceElement(), newArraySize);
		additionalCommands.redo();
	}

	@Override
	public VarDeclaration getInterfaceElement() {
		return (VarDeclaration) super.getInterfaceElement();
	}

	public CompoundCommand getAdditionalCommands() {
		return additionalCommands;
	}
}
