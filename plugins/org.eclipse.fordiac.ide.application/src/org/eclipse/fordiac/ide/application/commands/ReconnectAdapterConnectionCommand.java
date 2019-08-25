/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.LinkConstraints;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.requests.ReconnectRequest;

public class ReconnectAdapterConnectionCommand extends AbstractReconnectConnectionCommand {

	public ReconnectAdapterConnectionCommand(final ReconnectRequest request, final FBNetwork parent) {
		super(Messages.ReconnectDataConnectionCommand_LABEL, request, parent);
	}
	
	@Override
	protected boolean checkSourceAndTarget(IInterfaceElement sourceIE, IInterfaceElement targetIE) {
		if((sourceIE instanceof AdapterDeclaration) && (targetIE instanceof AdapterDeclaration)){
			return LinkConstraints.canExistAdapterConnection((AdapterDeclaration)sourceIE, (AdapterDeclaration)targetIE,
					(Connection)getRequest().getConnectionEditPart().getModel());
		}
		return false;
	}
	
	@Override
	protected AbstractConnectionCreateCommand createConnectionCreateCommand(FBNetwork parent) {
		return new AdapterConnectionCreateCommand(parent);
	}
}
