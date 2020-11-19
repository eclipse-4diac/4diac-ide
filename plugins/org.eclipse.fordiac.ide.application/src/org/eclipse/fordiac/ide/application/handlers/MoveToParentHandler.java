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
 *   				  - adjusted for unfolded subapps
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.MoveElementFromSubappCommand;
import org.eclipse.fordiac.ide.application.commands.MoveElementFromSubappCommand.MoveOperation;
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
import org.eclipse.ui.handlers.HandlerUtil;

public class MoveToParentHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FBNetworkEditor editor = (FBNetworkEditor) HandlerUtil.getActiveEditor(event);
		SubAppNetworkEditor subEditor = null;
		if (editor instanceof SubAppNetworkEditor) {
			subEditor = (SubAppNetworkEditor) editor;
			editor = openParentEditor(subEditor);
		}
		if (null != editor) {
			final StructuredSelection selection = getEditorSelection((subEditor == null) ? editor : subEditor);
			final CompoundCommand cmd = new CompoundCommand();
			for (final Object ne : selection) {
				if ((ne instanceof EditPart) && (((EditPart) ne).getModel() instanceof FBNetworkElement)) {
					final FBNetworkElement element = (FBNetworkElement) ((EditPart) ne).getModel();
					final SubApp subapp = (subEditor == null) ? (SubApp) element.eContainer().eContainer()
							: (SubApp) subEditor.getModel().eContainer();
					final GraphicalEditPart ep = (GraphicalEditPart) editor.getViewer().getEditPartRegistry()
							.get(subapp);
					final MoveElementFromSubappCommand moveCmd = new MoveElementFromSubappCommand(subapp, element,
							ep.getFigure().getBounds(), MoveOperation.CONTEXT_MENU);
					cmd.add(moveCmd);
				}
			}
			getCommandStack((subEditor == null) ? editor : subEditor).execute(cmd);
			preventFBPiling(cmd.getCommands());
		}
		return Status.OK_STATUS;
	}

	private CommandStack getCommandStack(FBNetworkEditor editor) {
		return ((IEditorPart) editor).getAdapter(CommandStack.class);
	}

	private StructuredSelection getEditorSelection(FBNetworkEditor editor) {
		return (StructuredSelection) editor.getViewer().getSelection();
	}

	private FBNetworkEditor openParentEditor(SubAppNetworkEditor editor) {
		final EObject model = editor.getModel().eContainer().eContainer().eContainer();
		return (FBNetworkEditor) EditorUtils.openEditor(getEditorInput(model), getEditorId(model));
	}

	// prevents the FBs from lying on top of one another
	private void preventFBPiling(List<MoveElementFromSubappCommand> commands) {
		final int OFFSET = 90;
		int left = 0;
		int right = 0;
		int below = 0;
		for (final MoveElementFromSubappCommand cmd : commands) {
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
