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
package org.eclipse.fordiac.ide.model.commands.change;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.LinkConstraints;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

/** A command for reconnecting event connections. */
public class ReconnectEventConnectionCommand extends AbstractReconnectConnectionCommand {

	public ReconnectEventConnectionCommand(final Connection connection, final boolean isSourceReconnect,
			final IInterfaceElement newTarget, final FBNetwork parent) {
		super(Messages.ReconnectEventConnectionCommand_LABEL, connection, isSourceReconnect, newTarget, parent);
	}

	@Override
	protected boolean checkSourceAndTarget(final IInterfaceElement sourceIE, final IInterfaceElement targetIE) {
		return LinkConstraints.canExistEventConnection(sourceIE, targetIE, getParent());
	}

	@Override
	protected AbstractConnectionCreateCommand createConnectionCreateCommand(final FBNetwork parent) {
		return new EventConnectionCreateCommand(parent);
	}

}
