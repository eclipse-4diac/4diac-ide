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
 *   Alois Zoitl - reworked connection selection and hover feedback
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.tools;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ReconnectRequest;

public class MultiFBNScrollingconnectionEndpointTracker extends FBNScrollingConnectionEndpointTracker {
	final List<ConnectionEditPart> coSelectedConnections;

	public MultiFBNScrollingconnectionEndpointTracker(final List<ConnectionEditPart> coSelectedConnections) {
		super(coSelectedConnections.get(0));
		this.coSelectedConnections = new ArrayList<>(coSelectedConnections);
		this.coSelectedConnections.remove(0);
	}

	@Override
	protected void showSourceFeedback() {
		final ReconnectRequest targetRequest = (ReconnectRequest) getTargetRequest();
		coSelectedConnections.forEach(con -> {
			targetRequest.setConnectionEditPart(con);
			con.showSourceFeedback(targetRequest);
		});
		targetRequest.setConnectionEditPart(getConnectionEditPart());
		super.showSourceFeedback();
	}

	@Override
	protected void eraseSourceFeedback() {
		final ReconnectRequest targetRequest = (ReconnectRequest) getTargetRequest();
		coSelectedConnections.forEach(con -> {
			targetRequest.setConnectionEditPart(con);
			con.eraseSourceFeedback(getTargetRequest());
		});
		targetRequest.setConnectionEditPart(getConnectionEditPart());
		super.eraseSourceFeedback();
	}

	@Override
	protected Command getCommand() {
		if (getTargetEditPart() == null) {
			return null;
		}
		final CompoundCommand cmd = new CompoundCommand();
		final ReconnectRequest targetRequest = (ReconnectRequest) getTargetRequest();
		coSelectedConnections.forEach(con -> {
			targetRequest.setConnectionEditPart(con);
			cmd.add(getTargetEditPart().getCommand(targetRequest));
		});
		targetRequest.setConnectionEditPart(getConnectionEditPart());
		cmd.add(super.getCommand());
		return cmd;
	}

	@Override
	protected void performSelection() {
		// don't change the selection
	}
}