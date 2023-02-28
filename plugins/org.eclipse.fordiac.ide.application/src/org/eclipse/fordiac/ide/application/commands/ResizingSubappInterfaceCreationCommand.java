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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.policies.ContainerContentLayoutPolicy;
import org.eclipse.fordiac.ide.application.policies.FBNetworkXYLayoutEditPolicy;
import org.eclipse.fordiac.ide.model.commands.change.AbstractChangeContainerBoundsCommand;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.ui.providers.CreationCommand;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public final class ResizingSubappInterfaceCreationCommand extends CreationCommand {

	public static CreationCommand createCommand(final CreateSubAppInterfaceElementCommand cmd, final SubApp parent) {
		final SubAppForFBNetworkEditPart subappEP = findSubappEP(parent);
		if (subappEP != null) {
			return new ResizingSubappInterfaceCreationCommand(cmd, subappEP);
		}
		// we couldn't create a resizing command return the given one as fallback
		return cmd;
	}

	private final CreateSubAppInterfaceElementCommand ieCreationCmd;
	private final SubAppForFBNetworkEditPart subAppEP;
	private final ResizeGroupOrSubappCommand resizingCmd;
	private AbstractChangeContainerBoundsCommand changeSubappHeightCmd;

	private ResizingSubappInterfaceCreationCommand(final CreateSubAppInterfaceElementCommand cmd,
			final SubAppForFBNetworkEditPart subAppEP) {
		this.ieCreationCmd = cmd;
		this.subAppEP = subAppEP;
		resizingCmd = new ResizeGroupOrSubappCommand(subAppEP);
	}

	@Override
	public void execute() {
		ieCreationCmd.execute();
		getViewer().flush();
		checkSubappHeight();
		// at last perform resizing to check any additional resizes and parent resizes
		resizingCmd.execute();
	}

	@Override
	public void undo() {
		resizingCmd.undo();
		if (changeSubappHeightCmd != null) {
			changeSubappHeightCmd.undo();
		}
		ieCreationCmd.undo();
	}

	@Override
	public void redo() {
		ieCreationCmd.redo();
		if (changeSubappHeightCmd != null) {
			changeSubappHeightCmd.redo();
		}
		resizingCmd.redo();
	}

	@Override
	public Object getCreatedElement() {
		return ieCreationCmd.getCreatedElement();
	}

	private EditPartViewer getViewer() {
		return subAppEP.getViewer();
	}

	private static SubAppForFBNetworkEditPart findSubappEP(final SubApp parent) {
		final IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		if (editor != null) {
			final GraphicalViewer viewer = HandlerHelper.getViewer(editor);
			if (viewer != null) {
				final Object ep = viewer.getEditPartRegistry().get(parent);
				if (ep instanceof SubAppForFBNetworkEditPart) {
					return (SubAppForFBNetworkEditPart) ep;
				}
			}
		}
		return null;
	}

	private void checkSubappHeight() {
		final int expandedIOHeight = subAppEP.getFigure().getExpandedIOHeight();
		final Rectangle containerBounds = ContainerContentLayoutPolicy.getContainerAreaBounds(subAppEP.getContentEP());
		if (containerBounds.height < expandedIOHeight) {
			final int heightDelta = expandedIOHeight - containerBounds.height;
			changeSubappHeightCmd = FBNetworkXYLayoutEditPolicy.createChangeBoundsCommand(subAppEP.getModel(),
					new Dimension(0, heightDelta),
					new Point());
			changeSubappHeightCmd.execute();
		}
	}

}
