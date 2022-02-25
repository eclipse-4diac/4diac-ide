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
 *   Alois Zoitl - implemented Handler for Goto Pin Below command/shortcut
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import static org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper.getCommandStack;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.GroupEditPart;
import org.eclipse.fordiac.ide.application.policies.GroupResizePolicy;
import org.eclipse.fordiac.ide.application.policies.GroupXYLayoutPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class TrimGroupHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final GroupEditPart groupEditPart = getGroupEditPart(HandlerUtil.getCurrentSelection(event));
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		if (groupEditPart != null && editor != null) {
			final GraphicalEditPart groupContentEP = GroupResizePolicy.getGroupContent(groupEditPart);
			if(groupContentEP != null) {
				final Rectangle groupContentContainerBounds = GroupXYLayoutPolicy.getGroupAreaBounds(groupEditPart,
						groupContentEP);
				final Rectangle groupContentBounds = GroupResizePolicy.getGroupContentBounds(groupContentEP);
				final Command cmd = GroupXYLayoutPolicy.createChangeGroupBoundsCommand(groupEditPart.getModel(),
						groupContentContainerBounds, groupContentBounds);
				getCommandStack(editor).execute(cmd);
			}
		}
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(getGroupEditPart(selection) != null);
	}

	private static GroupEditPart getGroupEditPart(final ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structSel = (IStructuredSelection) selection;
			if (structSel.size() == 1 && structSel.getFirstElement() instanceof GroupEditPart) {
				return (GroupEditPart) structSel.getFirstElement();
			}
		}
		return null;
	}


}
