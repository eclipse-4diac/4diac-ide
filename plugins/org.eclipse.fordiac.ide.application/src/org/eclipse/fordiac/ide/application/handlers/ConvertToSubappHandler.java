/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.commands.ConvertGroupToSubappCommand;
import org.eclipse.fordiac.ide.application.editparts.IContainerEditPart;
import org.eclipse.fordiac.ide.application.policies.ContainerContentLayoutPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class ConvertToSubappHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		final Group group = getSelectedGroup(selection);
		if (null != group) {
			final CommandStack commandStack = HandlerHelper.getCommandStack(editor);
			final ConvertGroupToSubappCommand conversion = new ConvertGroupToSubappCommand(group);
			if (conversion.canExecute()) {
				commandStack.execute(conversion);
				refreshSelection(conversion.getCreatedElement());
				final GraphicalViewer v = editor.getAdapter(GraphicalViewer.class);
				v.flush();
				final EditPart newSubappEP = v.getEditPartForModel(conversion.getCreatedElement());
				adjustMinBounds(commandStack, newSubappEP);
			}
		}
		return Status.OK_STATUS;
	}

	private static void adjustMinBounds(final CommandStack cmdStack, final EditPart subappEP) {
		// similar adjustment as trimming (cf. TrimHandler)
		final IContainerEditPart containerEditPart = (IContainerEditPart) subappEP;
		final GraphicalEditPart contentEP = containerEditPart.getContentEP();

		final Rectangle contentContainerBounds = ContainerContentLayoutPolicy.getContainerAreaBounds(contentEP);
		final Rectangle subappContentBounds = containerEditPart.getMinContentBounds();
		subappContentBounds.setWidth(Math.max(subappContentBounds.width, contentEP.getFigure().getSize().width));
		subappContentBounds.setHeight(Math.max(subappContentBounds.height, contentEP.getFigure().getSize().height));

		final Command cmd = ContainerContentLayoutPolicy.createChangeBoundsCommand(
				(FBNetworkElement) containerEditPart.getModel(), contentContainerBounds, subappContentBounds);
		if (cmd.canExecute()) {
			cmdStack.execute(cmd);
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		final Group group = getSelectedGroup(selection);
		setBaseEnabled((null != group) && (!group.isContainedInTypedInstance()));
	}

	private static Group getSelectedGroup(final Object selection) {
		if ((selection instanceof final IStructuredSelection structSel)
				&& (!structSel.isEmpty() && (structSel.size() == 1))) {
			return getGroup(structSel.getFirstElement());
		}
		return null;
	}

	private static Group getGroup(final Object currentElement) {
		Object elementToCheck = currentElement;
		if (elementToCheck instanceof final EditPart ep) {
			elementToCheck = ep.getModel();
		}

		if (elementToCheck instanceof final Group group) {
			return group;
		}
		return null;
	}

	private static void refreshSelection(final SubApp subapp) {
		final IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		HandlerHelper.getViewer(editor).deselectAll();
		HandlerHelper.selectElement(subapp, HandlerHelper.getViewer(editor));
	}
}
