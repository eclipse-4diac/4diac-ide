/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.gef.commands.Command;

public class DeleteErrorMarkerCommand extends Command {

	private final DeleteInterfaceCommand deleteErrorMarkerIECmd;
	private final DeleteFBNetworkElementCommand deleteErrorMarkerFBN;
	private final ErrorMarkerInterface errorIe;

	public DeleteErrorMarkerCommand(final ErrorMarkerInterface errorIe) {
		super();
		deleteErrorMarkerIECmd = new DeleteInterfaceCommand(errorIe);
		deleteErrorMarkerFBN = createDeleteFBNCommand(errorIe);
		this.errorIe = errorIe;
	}

	@Override
	public void execute() {
		performDeletion();
	}

	private void performDeletion() {
		deleteErrorMarkerIECmd.execute();
		if (deleteErrorMarkerFBN != null) {
			deleteErrorMarkerFBN.execute();
		}
	}

	@Override
	public void undo() {
		if (deleteErrorMarkerFBN != null) {
			deleteErrorMarkerFBN.undo();
		}
		deleteErrorMarkerIECmd.undo();
	}

	@Override
	public void redo() {
		deleteErrorMarkerIECmd.redo();
		if (deleteErrorMarkerFBN != null) {
			deleteErrorMarkerFBN.redo();
		}
	}

	private static DeleteFBNetworkElementCommand createDeleteFBNCommand(final ErrorMarkerInterface errorIe) {
		if (errorIe.getFBNetworkElement() instanceof ErrorMarkerFBNElement
				&& errorIe.getFBNetworkElement().getInterface().getErrorMarker().size() == 1) {
			return new DeleteFBNetworkElementCommand(errorIe.getFBNetworkElement());
		}
		return null;
	}
}
