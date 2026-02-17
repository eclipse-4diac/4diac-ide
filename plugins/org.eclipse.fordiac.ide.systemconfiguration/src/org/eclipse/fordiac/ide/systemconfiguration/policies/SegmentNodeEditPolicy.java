/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GbmH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.policies;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.systemconfiguration.commands.LinkCreateCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class SegmentNodeEditPolicy extends org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy {
	@Override
	protected Command getConnectionCompleteCommand(final CreateConnectionRequest request) {
		if (request.getStartCommand() instanceof final LinkCreateCommand command) {
			if (command.isSegmentDeviceLink()) {
				if (getHost().getModel() instanceof final Device device) {
					command.setDestination(device);
				} else if (getHost().getModel() instanceof Segment) {
					command.setDestination(null);
				}
			} else if (getHost().getModel() instanceof final Segment segment) {
				command.setSource(segment);
			} else if (getHost().getModel() instanceof Device) {
				command.setSource(null);
			}
			return command;
		}
		return null;
	}

	@Override
	protected Command getConnectionCreateCommand(final CreateConnectionRequest request) {
		final LinkCreateCommand cmd = new LinkCreateCommand();
		Object parentModel = getHost().getParent().getModel();

		if (getHost().getModel() instanceof final Segment segment) {
			cmd.setSource(segment);
			cmd.setSegmentDeviceLink(true);
		} else if (getHost().getModel() instanceof final Device device) {
			cmd.setDestination(device);
		} else if (getHost().getModel() instanceof IInterfaceElement
				&& getHost().getParent().getModel() instanceof final Device device) {
			cmd.setDestination(device);
			parentModel = device.eContainer();
		}

		if (parentModel instanceof final SystemConfiguration sysConfig) {
			cmd.setSystemConfigurationNetwork(sysConfig);
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