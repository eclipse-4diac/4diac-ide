/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha, Bianca Wiesmayr
 *      - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class UpdateFBTypeHandler extends AbstractHandler {

	private final List<FBNetworkElement> selectedNetworkElements = new ArrayList<>();

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final CompoundCommand cmd = new CompoundCommand();
		final CommandStack stack = HandlerUtil.getActiveEditor(event).getAdapter(CommandStack.class);

		for (final FBNetworkElement element : selectedNetworkElements) {
			final Command updateFBTypeCmd = getUpdateCommand(element);
			if (updateFBTypeCmd.canExecute()) {
				cmd.add(updateFBTypeCmd);
			}
		}
		if (null != stack) {
			stack.execute(cmd);
		}
		return Status.OK_STATUS;
	}

	public static Command getUpdateCommand(final FBNetworkElement element) {
		if (element instanceof final StructManipulator muxer) {
			return new ChangeStructCommand(muxer);
		}
		return new UpdateFBTypeCommand(element);
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		updateSelection(evaluationContext);
		setBaseEnabled(!selectedNetworkElements.isEmpty());
	}

	protected void updateSelection(final Object evaluationContext) {
		selectedNetworkElements.clear();
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof final StructuredSelection structSel) {
			for (Object element : structSel.toList()) {
				if (element instanceof final EditPart ep) {
					element = ep.getModel();
				}
				if ((element instanceof final FBNetworkElement fb) && (null != fb.getType())) {
					selectedNetworkElements.add(fb);
				}
			}
		}
	}

}
