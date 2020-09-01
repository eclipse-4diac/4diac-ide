/*******************************************************************************
 * Copyright (c) 2019 - 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - added check if subapp interface is selected and mark that in 
 *                 parent
 *   Daniel Lindhuber - MoveElementsFromSubappCommand integration
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.MoveElementFromSubappCommand;
import org.eclipse.fordiac.ide.application.editors.ApplicationEditorInput;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubAppNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.SubApplicationEditorInput;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class MoveToParentHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SubAppNetworkEditor subappEditor = (SubAppNetworkEditor) HandlerUtil.getActiveEditor(event);
		FBNetworkEditor editor = openParentEditor(subappEditor);
		if (null != editor) {
			StructuredSelection selection = (StructuredSelection) subappEditor.getViewer().getSelection();
			CompoundCommand cmd = new CompoundCommand();
			for (Object ne : selection) {
				if ((ne instanceof EditPart) && (((EditPart) ne).getModel() instanceof FBNetworkElement)) {
					FBNetworkElement element = (FBNetworkElement) ((EditPart) ne).getModel();
					SubApp subapp = (SubApp) subappEditor.getModel().eContainer();
					GraphicalEditPart ep = (GraphicalEditPart) editor.getViewer().getEditPartRegistry().get(subapp);
					MoveElementFromSubappCommand moveCmd = new MoveElementFromSubappCommand(subapp, element,
							ep.getFigure().getBounds());
					cmd.add(moveCmd);
				}
			}
			((IEditorPart) subappEditor).getAdapter(CommandStack.class).execute(cmd);
			preventFBPiling(cmd.getCommands());
		}
		return Status.OK_STATUS;
	}

	private FBNetworkEditor openParentEditor(SubAppNetworkEditor editor) {
		EObject model = editor.getModel().eContainer().eContainer().eContainer();
		return (FBNetworkEditor) EditorUtils.openEditor(getEditorInput(model), getEditorId(model));
	}

	// prevents the FBs from lying on top of one another
	private void preventFBPiling(List<MoveElementFromSubappCommand> commands) {
		final int OFFSET = 90;
		int left = 0;
		int right = 0;
		int below = 0;
		for (MoveElementFromSubappCommand cmd : commands) {
			switch (cmd.getSide()) {
			case LEFT:
				cmd.getElement().setY(cmd.getElement().getY() + (left * OFFSET));
				left++;
				break;
			case RIGHT:
				cmd.getElement().setY(cmd.getElement().getY() + (right * OFFSET));
				right++;
				break;
			default:
				cmd.getElement().setY(cmd.getElement().getY() + (below * OFFSET));
				below++;
			}
		}
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_EDITOR_ID_NAME);
		setBaseEnabled(SubAppNetworkEditor.class.getName().equals(selection));
	}

	private static IEditorInput getEditorInput(EObject model) {
		if (model instanceof SubApp) {
			return new SubApplicationEditorInput((SubApp) model);
		}
		if (model instanceof Application) {
			return new ApplicationEditorInput((Application) model);
		}
		return null;
	}

	private static String getEditorId(EObject model) {
		if (model instanceof SubApp) {
			return SubAppNetworkEditor.class.getName();
		}
		if (model instanceof Application) {
			return FBNetworkEditor.class.getName();
		}
		return null;
	}

}
