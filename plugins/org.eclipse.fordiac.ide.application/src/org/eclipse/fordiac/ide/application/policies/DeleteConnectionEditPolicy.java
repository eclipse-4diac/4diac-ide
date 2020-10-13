/*******************************************************************************
 * Copyright (c) 2008, 2016, 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

/**
 * This EditPolicy returns a command for deleting a Connection.
 */
public class DeleteConnectionEditPolicy extends org.eclipse.gef.editpolicies.ConnectionEditPolicy {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gef.editpolicies.ConnectionEditPolicy#getDeleteCommand(org.
	 * eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command getDeleteCommand(final GroupRequest request) {
		if (getHost().getModel() instanceof Connection) {
			return new DeleteConnectionCommand((Connection) getHost().getModel());
		}
		return null;
	}
}
