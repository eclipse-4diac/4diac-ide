/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.commands.ConnectionReference;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

public class CutEditPartsAction extends SelectionAction {

	CompoundCommand deleteCommands;

	/**
	 * Instantiates a new copy edit parts action.
	 *
	 * @param editor the editor
	 */
	public CutEditPartsAction(IEditorPart editor) {
		super(editor);
		setId(ActionFactory.CUT.getId());
		setText(Messages.CutEditPartsAction_Text);
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
	}

	@Override
	protected boolean calculateEnabled() {
		for (Object obj : getSelectedObjects()) {
			if (obj instanceof EditPart) {
				Object model = ((EditPart) obj).getModel();
				if ((model instanceof FBNetworkElement) || (model instanceof Connection)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void run() {
		deleteCommands = new CompoundCommand();
		List<Object> templates = analyzeSelection();
		execute(deleteCommands);
		Clipboard.getDefault().setContents(templates);
	}

	private List<Object> analyzeSelection() {
		List<Object> templates = new ArrayList<>();
		List<DeleteFBNetworkElementCommand> deleteFBCommands = new ArrayList<>();
		List<DeleteConnectionCommand> deleteConnCommands = new ArrayList<>();
		for (Object obj : getSelectedObjects()) {
			if (obj instanceof EditPart) {
				Object model = ((EditPart) obj).getModel();
				if (model instanceof FBNetworkElement) {
					deleteFBCommands.add(new DeleteFBNetworkElementCommand((FBNetworkElement) model));
					templates.add(model);
				} else if (model instanceof Connection) {
					// add connections to the beginning so that they will be deleted first
					deleteConnCommands.add(new DeleteConnectionCommand((Connection) model));
					templates.add(new ConnectionReference((Connection) model));
				}
			}
		}
		// first add all connection commands to the command list needed for undo/redo
		deleteConnCommands.forEach(deleteCommands::add);
		deleteFBCommands.forEach(deleteCommands::add);
		return templates;
	}

}
