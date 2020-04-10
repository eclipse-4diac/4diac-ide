/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   			 - added the option that also within a subapp flatten can be
 *                 invoked, this included clean-up and the use of a compound
 *                 command for a better undo redo behavior
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.commands.FlattenSubAppCommand;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UISubAppNetworkEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class FlattenSubApplication extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FBNetworkEditor editor = (FBNetworkEditor) HandlerUtil.getActiveEditor(event);

		CompoundCommand mainCmd = checkSelection(event);
		if (!mainCmd.isEmpty()) {
			editor.getCommandStack().execute(mainCmd);
		}
		return Status.OK_STATUS;
	}

	private static CompoundCommand checkSelection(ExecutionEvent event) {
		CompoundCommand mainCmd = new CompoundCommand();
		for (Object currentElement : getSelectionList(event)) {
			SubApp subApp = getSubApp(currentElement);
			if (null != subApp) {
				FlattenSubAppCommand cmd = new FlattenSubAppCommand(subApp);
				if (cmd.canExecute()) {
					mainCmd.add(cmd);
				}
			}
		}
		return mainCmd;
	}

	private static SubApp getSubApp(Object currentElement) {
		if (currentElement instanceof SubApp) {
			return (SubApp) currentElement;
		} else if (currentElement instanceof SubAppForFBNetworkEditPart) {
			return ((SubAppForFBNetworkEditPart) currentElement).getModel();
		} else if (currentElement instanceof UISubAppNetworkEditPart) {
			return (SubApp) ((UISubAppNetworkEditPart) currentElement).getModel().eContainer();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static List<Object> getSelectionList(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof StructuredSelection) {
			return ((StructuredSelection) selection).toList();
		}
		return Collections.emptyList();
	}
}
