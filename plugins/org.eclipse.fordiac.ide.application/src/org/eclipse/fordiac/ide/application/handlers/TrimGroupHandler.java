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
import org.eclipse.fordiac.ide.model.commands.change.ChangeGroupBoundsCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
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
				final Rectangle groupContentContainerBounds = getGroupAreaBounds(groupEditPart, groupContentEP);
				final Rectangle groupContentBounds = GroupResizePolicy.getGroupContentBounds(groupContentEP);
				final Command cmd = createChangeGroupBoundsCommand(groupEditPart.getModel(),
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

	private static Rectangle getGroupAreaBounds(final GraphicalEditPart groupEP,
			final GraphicalEditPart groupContentEP) {
		final Rectangle groupContentBounds = groupContentEP.getFigure().getBounds().getCopy();
		final Rectangle groupBounds = groupEP.getFigure().getBounds();
		final int borderSize = groupContentBounds.x - groupBounds.x;
		if (groupBounds.width < groupContentBounds.width) {
			groupContentBounds.width = groupBounds.width - borderSize;
		}
		final int dy = groupContentBounds.y - groupBounds.y;
		if ((groupBounds.height - dy) < groupContentBounds.height) {
			groupContentBounds.height = groupBounds.height - dy - borderSize;
		}
		return groupContentBounds;
	}

	public static ChangeGroupBoundsCommand createChangeGroupBoundsCommand(final Group group,
			final Rectangle groupContentContainerBounds, final Rectangle groupContentBounds) {
		final int dx = groupContentBounds.x - groupContentContainerBounds.x;
		final int dy = groupContentBounds.y - groupContentContainerBounds.y;
		final int dw = groupContentBounds.width - groupContentContainerBounds.width;
		final int dh = groupContentBounds.height - groupContentContainerBounds.height;
		return new ChangeGroupBoundsCommand(group, dx, dy, dw, dh);
	}

}
