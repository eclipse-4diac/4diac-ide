/*******************************************************************************
 * Copyright (c) 2008, 2012, 2014, 2016, 2018 Profactor GmbH, fortiss GmbH,
 * 											  Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.application.commands.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.LinkConstraints;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * An EditPolicy which allows drawing Connections between VariableInterfaces.
 */
public class VariableNodeEditPolicy extends InterfaceElementEditPolicy {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(final CreateConnectionRequest request) {
		final DataConnectionCreateCommand cmd = new DataConnectionCreateCommand(getParentNetwork());
		cmd.setSource(((InterfaceEditPart) getHost()).getModel());
		if ((cmd.getSource() instanceof VarDeclaration)
				&& (!LinkConstraints.isWithConstraintOK(cmd.getSource()))) {
			return null; // Elements which are not connected by a with construct are not allowed to be
			// connected
		}
		request.setStartCommand(cmd);
		return new DataConnectionCreateCommand(getParentNetwork());

	}

	@Override
	protected Command createReconnectCommand(final ReconnectRequest request) {
		return new ReconnectDataConnectionCommand(request, getParentNetwork());
	}

}
