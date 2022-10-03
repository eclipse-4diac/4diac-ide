/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2016 Profactor GmbH, AIT, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Filip Andren, Alois Zoitl
 *   - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;

/** A command for reconnecting data connections. */
public class ReconnectDataConnectionCommand extends AbstractReconnectConnectionCommand {

	public ReconnectDataConnectionCommand(final Connection connection, final boolean isSourceReconnect,
			final IInterfaceElement newTarget, final FBNetwork parent) {
		super(Messages.ReconnectDataConnectionCommand_LABEL, connection, isSourceReconnect, newTarget, parent);
	}

	@Override
	protected boolean checkSourceAndTarget(final IInterfaceElement sourceIE, final IInterfaceElement targetIE) {
		return LinkConstraints.canExistDataConnection(sourceIE, targetIE, getParent(), getConnnection());
	}

	@Override
	protected AbstractConnectionCreateCommand createConnectionCreateCommand(final FBNetwork parent) {
		return new DataConnectionCreateCommand(parent);
	}
}
