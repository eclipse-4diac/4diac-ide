/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *               2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 *  Bianca Wiesmayr - fix positioning of subapp
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.commands.NewSubAppCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.FBNetworkContextMenuProvider;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class NewSubApplication extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		FBNetworkEditor editor = (FBNetworkEditor) HandlerUtil.getActiveEditor(event);

		StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);
		Point pos = getInsertPos(editor, selection);
		NewSubAppCommand cmd = new NewSubAppCommand(editor.getModel(), selection.toList(), pos.x, pos.y);
		editor.getCommandStack().execute(cmd);
		editor.selectElement(cmd.getElement());

		return Status.OK_STATUS;
	}

	public static Point getInsertPos(FBNetworkEditor editor, StructuredSelection selection) {
		Point pos = null;
		if (createNewEmptySubapp(selection)) {
			// new empty subapp at mouse cursor location
			pos = ((FBNetworkContextMenuProvider) editor.getViewer().getContextMenu()).getPoint();
		} else {
			org.eclipse.swt.graphics.Point swtPos1 = FBNetworkHelper.getTopLeftCornerOfFBNetwork(selection.toList());
			pos = new Point(swtPos1.x, swtPos1.y);
		}
		return pos;
	}

	private static boolean createNewEmptySubapp(StructuredSelection selection) {
		return (selection.size() == 1)
				&& !(((EditPart) selection.getFirstElement()).getModel() instanceof FBNetworkElement);
	}
}
