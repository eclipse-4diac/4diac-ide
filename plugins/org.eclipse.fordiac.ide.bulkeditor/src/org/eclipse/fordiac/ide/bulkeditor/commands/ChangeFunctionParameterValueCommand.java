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
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.commands;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionTextUpdater;
import org.eclipse.gef.commands.Command;

public class ChangeFunctionParameterValueCommand extends Command implements ScopedCommand {
	private final ChangeValueCommand changeValueCommand;
	private final STFunctionBody body;
	private final String newText;
	private String oldText;

	private ChangeFunctionParameterValueCommand(final VarDeclaration variable, final FunctionFBType type,
			final STFunctionBody body, final String value) {
		changeValueCommand = new ChangeValueCommand(variable, value);
		this.body = body;
		oldText = body.getText();
		newText = STFunctionTextUpdater.changeParameterInitialValue(type, oldText,
				changeValueCommand.getInterfaceElement().getName(), changeValueCommand.getNewValue()).map(region -> {
					final StringBuilder builder = new StringBuilder(oldText);
					region.applyTo(builder);
					return builder.toString();
				}).orElse(null);
	}

	public static Command forValue(final VarDeclaration variable, final String value) {
		if (variable.getFBType() instanceof final FunctionFBType type
				&& type.getBody() instanceof final STFunctionBody body && body.getText() != null) {
			return new ChangeFunctionParameterValueCommand(variable, type, body, value);
		}
		return new ChangeValueCommand(variable, value);
	}

	@Override
	public boolean canExecute() {
		return newText != null && changeValueCommand.canExecute();
	}

	@Override
	public void execute() {
		changeValueCommand.execute();
		body.setText(newText);
	}

	@Override
	public boolean canUndo() {
		return changeValueCommand.canUndo();
	}

	@Override
	public void undo() {
		body.setText(oldText);
		changeValueCommand.undo();
	}

	@Override
	public void redo() {
		changeValueCommand.redo();
		body.setText(newText);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return changeValueCommand.getAffectedObjects();
	}
}
