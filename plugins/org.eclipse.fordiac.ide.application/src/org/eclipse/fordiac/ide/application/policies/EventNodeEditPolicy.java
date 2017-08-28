/*******************************************************************************
 * Copyright (c) 2008, 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.application.commands.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.application.commands.ReconnectEventConnectionCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * An EditPolicy which allows drawing Connections between EventInterfaces.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class EventNodeEditPolicy extends
		InterfaceElementEditPolicy {

	public EventNodeEditPolicy() {
		super(EventConnectionCreateCommand.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(
			final CreateConnectionRequest request) {
		EventConnectionCreateCommand cmd = new EventConnectionCreateCommand(getParentNetwork());
		cmd.setSource((InterfaceEditPart) getHost());
		request.setStartCommand(cmd);
		return new EventConnectionCreateCommand(getParentNetwork());
	}
	
	@Override
	protected Command createReconnectCommand(ReconnectRequest request) {
		return new ReconnectEventConnectionCommand(request, getParentNetwork());
	}
}
