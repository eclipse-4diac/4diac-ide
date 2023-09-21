/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.util.FordiacMarkerCommandHelper;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public abstract class AbstractChangeAttributeCommand extends Command {
	private final Attribute attribute;
	private final CompoundCommand errorMarkerUpdateCmds = new CompoundCommand();

	protected AbstractChangeAttributeCommand(final Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public boolean canExecute() {
		return attribute != null;
	}

	@Override
	public final void execute() {
		doExecute();
		validateValue();
		errorMarkerUpdateCmds.execute();
	}

	private void validateValue() {
		errorMarkerUpdateCmds.add(FordiacMarkerCommandHelper.newDeleteMarkersCommand(
				FordiacMarkerHelper.findMarkers(attribute, FordiacErrorMarker.INITIAL_VALUE_MARKER)));

		final String newValueErrorMessage = VariableOperations.validateValue(attribute.getType(), attribute.getValue());
		if (!newValueErrorMessage.isBlank()) {
			ErrorMessenger.popUpErrorMessage(newValueErrorMessage);
			errorMarkerUpdateCmds.add(FordiacMarkerCommandHelper
					.newCreateMarkersCommand(ErrorMarkerBuilder.createErrorMarkerBuilder(newValueErrorMessage)
							.setType(FordiacErrorMarker.INITIAL_VALUE_MARKER).setTarget(attribute)));
		}
	}

	@Override
	public final void undo() {
		errorMarkerUpdateCmds.undo();
		doUndo();
	}

	@Override
	public final void redo() {
		doRedo();
		errorMarkerUpdateCmds.redo();
	}

	protected abstract void doExecute();

	protected abstract void doRedo();

	protected abstract void doUndo();

	public Attribute getAttribute() {
		return attribute;
	}

	protected CompoundCommand getErrorMarkerUpdateCmds() {
		return errorMarkerUpdateCmds;
	}
}
