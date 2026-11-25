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

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionTextUpdater;
import org.eclipse.gef.commands.Command;
import org.eclipse.xtext.util.ReplaceRegion;

public class ChangeFunctionParameterCommand extends Command implements ScopedCommand {
	private final Command changeCommand;
	private final STFunctionBody body;
	private final String newText;
	private final String oldText;

	private <C extends Command & ScopedCommand> ChangeFunctionParameterCommand(final C changeCommand,
			final STFunctionBody body, final Optional<ReplaceRegion> textChange) {
		this.changeCommand = changeCommand;
		this.body = body;
		oldText = body.getText();
		newText = textChange.map(region -> {
			final StringBuilder builder = new StringBuilder(oldText);
			region.applyTo(builder);
			return builder.toString();
		}).orElse(null);
	}

	public static Command forValue(final VarDeclaration variable, final String value) {
		final ChangeValueCommand changeValueCommand = new ChangeValueCommand(variable, value);
		return create(variable, changeValueCommand, (type, text) -> STFunctionTextUpdater
				.changeParameterInitialValue(type, text, variable.getName(), changeValueCommand.getNewValue()));
	}

	public static Command forComment(final VarDeclaration variable, final String comment) {
		final String newComment = Objects.requireNonNullElse(comment, ""); //$NON-NLS-1$
		return create(variable, new ChangeCommentCommand(variable, newComment), (type, text) -> STFunctionTextUpdater
				.changeParameterComment(type, text, variable.getName(), newComment));
	}

	private static <C extends Command & ScopedCommand> Command create(final VarDeclaration variable,
			final C changeCommand, final BiFunction<FunctionFBType, String, Optional<ReplaceRegion>> textChange) {
		if (variable.getFBType() instanceof final FunctionFBType type
				&& type.getBody() instanceof final STFunctionBody body && body.getText() != null) {
			return new ChangeFunctionParameterCommand(changeCommand, body, textChange.apply(type, body.getText()));
		}
		return changeCommand;
	}

	@Override
	public boolean canExecute() {
		return newText != null && changeCommand.canExecute();
	}

	@Override
	public void execute() {
		changeCommand.execute();
		body.setText(newText);
	}

	@Override
	public boolean canUndo() {
		return changeCommand.canUndo();
	}

	@Override
	public void undo() {
		body.setText(oldText);
		changeCommand.undo();
	}

	@Override
	public void redo() {
		changeCommand.redo();
		body.setText(newText);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return ((ScopedCommand) changeCommand).getAffectedObjects();
	}
}
