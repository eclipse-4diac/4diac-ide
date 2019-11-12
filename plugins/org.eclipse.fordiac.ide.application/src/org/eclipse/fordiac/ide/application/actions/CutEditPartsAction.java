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

import java.util.List;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * Cut action which will use the copy part and delete the selected FBs
 *
 */
public class CutEditPartsAction extends CopyEditPartsAction {

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
	public void run() {
		List<Object> templates = getSelectedTemplates();
		execute(getFBDeleteCommands(templates));
		Clipboard.getDefault().setContents(templates);
	}

	private static Command getFBDeleteCommands(List<Object> templates) {
		CompoundCommand cmd = new CompoundCommand();
		for (Object obj : templates) {
			if (obj instanceof FBNetworkElement) {
				cmd.add(new DeleteFBNetworkElementCommand((FBNetworkElement) obj));
			}
		}
		return cmd;
	}
}
