/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class ConnectionGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
		AbstractConnectionCreateCommand command = (AbstractConnectionCreateCommand) request.getStartCommand();
		command.setDestination(command.getSource().isIsInput() ?  //if the source is an input we need to deliver our source i.e., the output				
				((ConnectionEditPart)getHost()).getModel().getSource() : ((ConnectionEditPart)getHost()).getModel().getDestination());
		return command;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		// currently we don't want to create connection by draging from conneciotns
		return null;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		// nothing to do here for connections
		return null;
	}

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		// nothing to do here for connections
		return null;
	}
}