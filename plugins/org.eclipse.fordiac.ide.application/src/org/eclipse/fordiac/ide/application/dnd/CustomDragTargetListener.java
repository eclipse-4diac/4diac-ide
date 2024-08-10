/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *               - added connection re-connection across editor boundaries
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.dnd;

import java.util.List;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.dnd.AbstractTransferDropTargetListener;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class CustomDragTargetListener extends AbstractTransferDropTargetListener {

	public CustomDragTargetListener(final EditPartViewer viewer) {
		super(viewer, CustomSourceTransfer.getInstance());
		setEnablementDeterminedByCommand(true);
	}

	@Override
	protected Request createTargetRequest() {
		final Request req = CustomSourceTransfer.getInstance().getObject();
		if (req != null) {
			return req;
		}
		return super.createTargetRequest();
	}

	@Override
	protected Command getCommand() {
		final Request req = getTargetRequest();
		if (req instanceof final ReconnectRequest reconReq) {
			@SuppressWarnings("unchecked")
			final List<ConnectionEditPart> connections = (List<ConnectionEditPart>) req.getExtendedData()
					.get(CustomSourceTransfer.CONNECTIONS_LIST);
			if (connections != null) {
				// we
				final CompoundCommand cmd = new CompoundCommand();
				connections.forEach(con -> {
					reconReq.setConnectionEditPart(con);
					cmd.add(getTargetEditPart().getCommand(reconReq));
				});
				return cmd;
			}
		}
		return super.getCommand();
	}

	@Override
	protected void updateTargetRequest() {
		final Request req = getTargetRequest();
		if (req instanceof final CreateConnectionRequest createConReq) {
			createConReq.setLocation(getDropLocation());
		} else if (req instanceof final ReconnectRequest reconReq) {
			reconReq.setLocation(getDropLocation());
			reconReq.setTargetEditPart(getTargetEditPart());
		} else if (req instanceof final ChangeBoundsRequest boundReq) {
			boundReq.setLocation(getDropLocation());
		}
	}

}
