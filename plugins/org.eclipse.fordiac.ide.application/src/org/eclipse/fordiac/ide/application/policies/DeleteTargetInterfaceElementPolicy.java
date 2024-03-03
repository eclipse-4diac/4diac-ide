/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElement;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class DeleteTargetInterfaceElementPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(final GroupRequest request) {
		if (getHost().getModel() instanceof final TargetInterfaceElement targetIE) {
			final IInterfaceElement host = targetIE.getHost();
			if (host.isIsInput()) {
				return createInputSideDeleteCommands(host);
			}
			return createOutputSideDeleteCommand(targetIE);
		}
		return null;
	}

	private static Command createInputSideDeleteCommands(final IInterfaceElement host) {
		Command cmd = new DeleteSubAppInterfaceElementCommand(host);

		if (!host.getInputConnections().isEmpty()) {
			final IInterfaceElement source = host.getInputConnections().get(0).getSource();
			if (source.getFBNetworkElement() instanceof final SubApp subapp && subapp.isUnfolded()
					&& source.getOutputConnections().size() == 1) {
				// The other end of the connection is an interface element of an expanded subapp
				// and there is only one connection left. So we also need to delete that pin.
				cmd = cmd.chain(new DeleteSubAppInterfaceElementCommand(source));
			}
		}

		return cmd;
	}

	private static Command createOutputSideDeleteCommand(final TargetInterfaceElement targetIE) {
		final Connection connection = targetIE.getRefElement().getInputConnections().get(0);

		if (connection.getSource().getOutputConnections().size() == 1) {
			// we are the last connection to this pin remove all
			return createInputSideDeleteCommands(connection.getSource());
		}

		return new DeleteConnectionCommand(connection);
	}
}
