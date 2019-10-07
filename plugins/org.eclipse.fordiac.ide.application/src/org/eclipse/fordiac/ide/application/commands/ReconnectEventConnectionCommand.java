/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2016 Profactor GmbH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl 
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.LinkConstraints;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * A command for reconnecting event connections.
 */
public class ReconnectEventConnectionCommand extends AbstractReconnectConnectionCommand {

	/**
	 * A command for reconnecting event connection.
	 * 
	 * @param request the request
	 */
	public ReconnectEventConnectionCommand(final ReconnectRequest request, final FBNetwork parent) {
		super(Messages.ReconnectEventConnectionCommand_LABEL, request, parent);
	}

	@Override
	protected boolean checkSourceAndTarget(IInterfaceElement sourceIE, IInterfaceElement targetIE) {
		if((sourceIE instanceof Event) && (targetIE instanceof Event)){
			return LinkConstraints.canExistEventConnection((Event)sourceIE, (Event)targetIE);
		}
		return false;
	}	

	@Override
	protected AbstractConnectionCreateCommand createConnectionCreateCommand(FBNetwork parent) {
		return new EventConnectionCreateCommand(parent);
	}

}
