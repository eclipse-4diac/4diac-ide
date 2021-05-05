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
import org.eclipse.fordiac.ide.application.actions.CopyPasteMessage.CopyStatus;
import org.eclipse.fordiac.ide.application.commands.CutAndPasteFromSubAppCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
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
		final CopyPasteMessage message = new CopyPasteMessage(CopyStatus.CUT, templates);
		final CutAndPasteFromSubAppCommand cutAndPasteFromSubAppCommand = isCutFromSubapp(templates);
		final Command fbDeleteCommands = getFBDeleteCommands(templates);
		message.setDeleteCommandos(new CompoundCommand());
		message.getDeleteCommandos().add(fbDeleteCommands);

		if (cutAndPasteFromSubAppCommand != null) {
			execute(cutAndPasteFromSubAppCommand);
			message.setCutAndPasteFromSubAppCommandos(cutAndPasteFromSubAppCommand);
			message.setCopyInfo(CopyStatus.CUT_FROM_SUBAPP);
		} else {
			// add the src FBNetwork to the model as the cut deletes the FBs from the
			// network and we therefore loose the source fbnetwork for checks in pasting
			execute(fbDeleteCommands);
		}
		templates.add(getWorkbenchPart().getAdapter(FBNetwork.class));
		Clipboard.getDefault().setContents(message);
	}

	private static Command getFBDeleteCommands(final List<Object> templates) {
		final CompoundCommand cmd = new CompoundCommand();
		for (final Object obj : templates) {
			if (obj instanceof FBNetworkElement) {
				cmd.add(new DeleteFBNetworkElementCommand((FBNetworkElement) obj));
			}
		}
		return cmd;
	}

	private static CutAndPasteFromSubAppCommand isCutFromSubapp(final List<Object> templates) {
		FBNetworkElement parent = null;
		final List<FBNetworkElement> elements = new ArrayList<>();
		for (final Object obj : templates) {
			if (obj instanceof FBNetworkElement) {
				final FBNetworkElement fbNetworkElement = (FBNetworkElement) obj;
				if (isNotPartOfSameSubapp(parent, fbNetworkElement)) {
					return null;
				}
				elements.add(fbNetworkElement);
				parent = fbNetworkElement.getOuterFBNetworkElement();
			}
		}
		return createCutAndPasteCommand(elements);
	}

	protected static boolean isNotPartOfSameSubapp(final FBNetworkElement parent,
			final FBNetworkElement fbNetworkElement) {
		return !fbNetworkElement.isNestedInSubApp()
				|| parent != null && !parent.equals(fbNetworkElement.getOuterFBNetworkElement());
	}

	protected static CutAndPasteFromSubAppCommand createCutAndPasteCommand(
			final List<FBNetworkElement> fbNetworkElements) {
		return new CutAndPasteFromSubAppCommand(fbNetworkElements,
				FBNetworkHelper.getTopLeftCornerOfFBNetwork(fbNetworkElements));
	}
}
