/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Muddasir Shakil - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.commands.change.UnmapCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class UnmapHandler extends AbstractHandler {

	private final List<FBNetworkElement> selectedNetworkElements = new ArrayList<>();

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final CompoundCommand cmd = new CompoundCommand();
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final CommandStack stack = editor.getAdapter(CommandStack.class);
		for (final FBNetworkElement element : selectedNetworkElements) {
			final Command unmapCmd = new UnmapCommand(element);
			if (unmapCmd.canExecute()) {
				cmd.add(unmapCmd);
			}
		}
		if (null != stack) {
			stack.execute(cmd);
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		setBaseEnabled(!getMappedFBList(getSelectedElements(evaluationContext)).isEmpty());
	}

	protected List<?> getSelectedElements(Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof StructuredSelection) {
			return ((StructuredSelection) selection).toList();
		}
		return Collections.emptyList();
	}

	private List<FBNetworkElement> getMappedFBList(List<?> fbNetwork){
		selectedNetworkElements.clear();
		for (Object element : fbNetwork) {
			if(element instanceof EditPart) {
				element = ((EditPart)element).getModel();
			}

			if ((element instanceof FBNetworkElement)) {
				checkMapping((FBNetworkElement) element);
			}
		}
		return selectedNetworkElements;
	}

	private void checkMapping(FBNetworkElement model) {
		if (model.isMapped()) {
			selectedNetworkElements.add(model);
		}
	}
}
