/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.policies;

import org.eclipse.fordiac.ide.systemconfiguration.commands.LinkCreateCommand;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.SegmentEditPart;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.SystemNetworkEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class SegmentNodeEditPolicy extends org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy {
	@Override
	protected Command getConnectionCompleteCommand(final CreateConnectionRequest request) {
		if (request.getStartCommand() instanceof LinkCreateCommand) {
			LinkCreateCommand command = (LinkCreateCommand) request.getStartCommand();
			if (command.isSegmentDeviceLink()) {
				if (getHost() instanceof DeviceEditPart) {
					command.setDestination(((DeviceEditPart) getHost()).getModel());
				} else if (getHost() instanceof SegmentEditPart) {
					command.setDestination(null);
				}
			} else {
				if (getHost() instanceof SegmentEditPart) {
					command.setSource(((SegmentEditPart) getHost()).getModel());
				} else if (getHost() instanceof DeviceEditPart) {
					command.setSource(null);
				}
			}
			return command;
		}
		return null;
	}

	@Override
	protected Command getConnectionCreateCommand(final CreateConnectionRequest request) {
		LinkCreateCommand cmd = new LinkCreateCommand();
		if (getHost() instanceof SegmentEditPart) {
			cmd.setSource(((SegmentEditPart) getHost()).getModel());
			cmd.setSegmentDeviceLink(true);
		} else if (getHost() instanceof DeviceEditPart) {
			cmd.setDestination(((DeviceEditPart) getHost()).getModel());
		}
		EditPart parent = getHost().getParent();
		if (parent instanceof SystemNetworkEditPart) {
			SystemNetworkEditPart systemNetworkEditPart = (SystemNetworkEditPart) parent;
			cmd.setSystemConfigurationNetwork(systemNetworkEditPart.getModel());
		}
		request.setStartCommand(cmd);
		return cmd;
	}

	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		return null;
	}

	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
		return null;
	}
}