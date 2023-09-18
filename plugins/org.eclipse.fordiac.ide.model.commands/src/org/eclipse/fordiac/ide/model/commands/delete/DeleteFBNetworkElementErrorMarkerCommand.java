/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.commands.util.FordiacMarkerCommandHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.commands.Command;

public class DeleteFBNetworkElementErrorMarkerCommand extends Command {
	private final FBNetworkElement fbNetworkElement;
	private String errorMessage;
	Command deleteMarkerCmd;

	public DeleteFBNetworkElementErrorMarkerCommand(final FBNetworkElement fbNE) {
		this.fbNetworkElement = fbNE;
		this.deleteMarkerCmd = FordiacMarkerCommandHelper
				.newDeleteMarkersCommand(FordiacMarkerHelper.findMarkers(fbNetworkElement));
	}

	@Override
	public boolean canExecute() {
		return canRedo();
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public boolean canRedo() {
		return fbNetworkElement != null && fbNetworkElement.hasError() && deleteMarkerCmd.canRedo();
	}

	@Override
	public void redo() {
		this.errorMessage = fbNetworkElement.getErrorMessage();
		fbNetworkElement.setErrorMessage(null);
		deleteMarkerCmd.execute();
	}

	@Override
	public boolean canUndo() {
		return fbNetworkElement != null && errorMessage != null && deleteMarkerCmd.canUndo();
	}

	@Override
	public void undo() {
		deleteMarkerCmd.undo();
		fbNetworkElement.setErrorMessage(this.errorMessage);
	}
}