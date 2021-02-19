/*******************************************************************************
 * Copyright (c) 2020, 2021 Johannes Kepler University Linz
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Muddasir Shakil - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - reworked for breadcrumb editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class UpdateAllFBTypesHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final CompoundCommand cmd = new CompoundCommand();

		final IEditorPart editor = HandlerUtil.getActiveEditor(event);
		final FBNetwork fbnetwork = editor.getAdapter(FBNetwork.class);
		final CommandStack stack = editor.getAdapter(CommandStack.class);

		for (final FBNetworkElement element : fbnetwork.getNetworkElements()) {
			if (null != element.getType()) {
				final Command updateFBTypeCmd = UpdateFBTypeHandler.getUpdateCommand(element);
				if (updateFBTypeCmd.canExecute()) {
					cmd.add(updateFBTypeCmd);
				}
			}
		}
		if (null != stack) {
			stack.execute(cmd);
		}

		return Status.OK_STATUS;
	}

}
