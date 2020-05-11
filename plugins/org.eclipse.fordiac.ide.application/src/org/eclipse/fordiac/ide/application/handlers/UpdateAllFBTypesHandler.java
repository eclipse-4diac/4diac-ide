/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Muddasir Shakil
 *      - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.editors.FBNetworkEditor;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.handlers.HandlerUtil;

public class UpdateAllFBTypesHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		CompoundCommand cmd = new CompoundCommand();
		FBNetworkEditor fbEditor = HandlerUtil.getActiveEditor(event).getAdapter(FBNetworkEditor.class);
		CommandStack stack = fbEditor.getFBEditorCommandStack();

		for (FBNetworkElement element : fbEditor.getModel().getNetworkElements()) {
			if (null != element.getType()) {
				Command updateFBTypeCmd = UpdateFBTypeHandler.getUpdateCommand(element);
				if (updateFBTypeCmd.canExecute()) {
					cmd.add(updateFBTypeCmd);
				}
			}
		}
		if ((null != stack) && !cmd.isEmpty()) {
			stack.execute(cmd);
		}

		return Status.OK_STATUS;
	}

}
