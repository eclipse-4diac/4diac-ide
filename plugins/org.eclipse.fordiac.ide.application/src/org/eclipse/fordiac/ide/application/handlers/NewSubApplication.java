/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.fordiac.ide.application.commands.NewSubAppCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editors.UIFBNetworkContextMenuProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.handlers.HandlerUtil;

public class NewSubApplication extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws org.eclipse.core.commands.ExecutionException {
		FBNetworkEditor editor = (FBNetworkEditor)HandlerUtil.getActiveEditor(event);
		Point pt = ((UIFBNetworkContextMenuProvider)editor.getViewer().getContextMenu()).getPoint();
		StructuredSelection selection = (StructuredSelection) HandlerUtil.getCurrentSelection(event);				
		NewSubAppCommand cmd = new NewSubAppCommand(editor.getModel(), selection.toList(), pt.x, pt.y);
		editor.getCommandStack().execute(cmd);
		return null;
	}

}
