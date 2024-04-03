/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr - initial implementation and/or initial documentation
 *   Lukas Wais      - added updateElement and requestChange for expand/collapse
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.commands.ResizeGroupOrSubappCommand;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ToggleSubAppRepresentationCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.menus.UIElement;

public class ToggleSubAppRepresentation extends AbstractHandler implements IElementUpdater {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		final SubApp subapp = (SubApp) ((EditPart) selection.getFirstElement()).getModel();
		Command cmd = new ToggleSubAppRepresentationCommand(subapp);

		if (!subapp.isUnfolded()
				&& (selection.getFirstElement() instanceof final SubAppForFBNetworkEditPart subAppEP)) {
			// we are going to get unfolded wrap resize command
			cmd = new ResizeGroupOrSubappCommand(subAppEP, cmd);
		}

		final CommandStack commandStack = HandlerUtil.getActiveEditor(event).getAdapter(CommandStack.class);
		if (cmd.canExecute()) {
			commandStack.execute(cmd);
		}

		// requesting a change is needed for the Source dropdown menu
		requestChange();
		return Status.OK_STATUS;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		final SubApp subApp = getSelectedSubApp(selection);

		// for now we only allow untyped subapps to be expanded or collapsed
		final boolean isUntypedSubapp = (null != subApp) && (!subApp.isTyped());
		final boolean isInResource = (null != subApp) && subApp.eContainer().eContainer() instanceof Resource;
		setBaseEnabled(isUntypedSubapp && !isInResource);
	}

	@Override
	public void updateElement(final UIElement element, final Map parameters) {
		final IEditorPart currentActiveEditor = EditorUtils.getCurrentActiveEditor();
		if (currentActiveEditor != null) {
			final GraphicalViewer viewer = currentActiveEditor.getAdapter(GraphicalViewer.class);
			final EditPart editPart = viewer.getSelectedEditParts().get(0);

			if ((editPart.getModel() instanceof final SubApp subApp)) {
				if (subApp.isUnfolded()) {
					element.setText(Messages.ToggleSubAppRepresentation_Collapse);
				} else {
					element.setText(Messages.ToggleSubAppRepresentation_Expand);
				}
			}
		}
	}

	private static void requestChange() {
		final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		final ICommandService commandService = window.getService(ICommandService.class);
		if (null != commandService) {
			commandService.refreshElements("org.eclipse.fordiac.ide.application.commands.expandSubApp", null); //$NON-NLS-1$
		}
	}

	private static SubApp getSelectedSubApp(final Object selection) {
		if ((selection instanceof final IStructuredSelection structSel)
				&& (!structSel.isEmpty() && (structSel.size() == 1))) {
			return getSubApp(structSel.getFirstElement());
		}
		return null;
	}

	private static SubApp getSubApp(final Object currentElement) {
		if (currentElement instanceof final SubApp subApp) {
			return subApp;
		}
		if (currentElement instanceof final SubAppForFBNetworkEditPart subAppNetworkEP) {
			return subAppNetworkEP.getModel();
		}
		if (currentElement instanceof UISubAppNetworkEditPart) {
			return (SubApp) ((UISubAppNetworkEditPart) currentElement).getModel().eContainer();
		}
		return null;
	}

}
