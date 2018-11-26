/*******************************************************************************
 * Copyright (c) 2008, 2011, 2013 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateTransitionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ReconnectTransitionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.editparts.ECStateEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * The Class TransitionNodeEditPolicy.
 */
public class TransitionNodeEditPolicy extends GraphicalNodeEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCompleteCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCompleteCommand(
			final CreateConnectionRequest request) {
		if (request.getStartCommand() instanceof CreateTransitionCommand) {
			CreateTransitionCommand command = (CreateTransitionCommand) request
					.getStartCommand();
			if (getHost() instanceof ECStateEditPart) {

				Point destination = request.getLocation().getCopy();
				getHostFigure().translateToRelative(destination);
				
				command.setDestinationLocation(destination);
				command.setDestination(((ECStateEditPart) getHost())
						.getCastedModel());
				return command;
			}
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getConnectionCreateCommand(org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(
			final CreateConnectionRequest request) {

		CreateTransitionCommand cmd = new CreateTransitionCommand();
		if (getHost() instanceof ECStateEditPart) {
				

			Point source = request.getLocation().getCopy();
			getHostFigure().translateToRelative(source);
			
			cmd.setSource(((ECStateEditPart) getHost()).getCastedModel());
			cmd.setSourceLocation(source);
			cmd.setViewer(getHost().getViewer());
		}
		request.setStartCommand(cmd);
		return cmd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
		return new ReconnectTransitionCommand(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		return new ReconnectTransitionCommand(request);
	}

}
