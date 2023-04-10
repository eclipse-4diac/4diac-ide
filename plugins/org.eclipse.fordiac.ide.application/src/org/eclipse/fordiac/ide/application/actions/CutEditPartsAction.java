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
import org.eclipse.fordiac.ide.application.actions.CopyPasteMessage.CopyStatus;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.ui.FordiacClipboard;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/** Cut action which will use the copy part and delete the selected FBs */
public class CutEditPartsAction extends CopyEditPartsAction {

	/** Instantiates a new copy edit parts action.
	 *
	 * @param editor the editor */
	public CutEditPartsAction(final IEditorPart editor) {
		super(editor);
		setId(ActionFactory.CUT.getId());
		setText(Messages.CutEditPartsAction_Text);
		final ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
	}

	@Override
	public void run() {
		final List<Object> templates = getSelectedTemplates();
		final CopyPasteMessage message = new CopyPasteMessage(CopyStatus.CUT_FROM_ROOT, templates);
		final Command fbDeleteCommands = getFBDeleteCommands(templates);

		execute(fbDeleteCommands);
		// add the src FBNetwork to the model as the cut deletes the FBs from the
		// network and we therefore loose the source fbnetwork for checks in pasting
		templates.add(getWorkbenchPart().getAdapter(FBNetwork.class));
		FordiacClipboard.INSTANCE.setGraphicalContents(message);
	}

	private static Command getFBDeleteCommands(final List<Object> templates) {
		final CompoundCommand cmd = new CompoundCommand();
		for (final Object obj : templates) {
			if (obj instanceof final FBNetworkElement fbnEl) {
				cmd.add(new DeleteFBNetworkElementCommand(fbnEl));
			}
		}
		return cmd;
	}

}
