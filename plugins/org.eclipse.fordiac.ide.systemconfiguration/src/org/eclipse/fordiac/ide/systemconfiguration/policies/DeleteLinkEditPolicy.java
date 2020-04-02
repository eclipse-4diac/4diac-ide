/*******************************************************************************
 * Copyright (c) 2008, 2016 Profactor GbmH, fortiss GmbH
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

import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.systemconfiguration.commands.DeleteLinkCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

public class DeleteLinkEditPolicy extends org.eclipse.gef.editpolicies.ConnectionEditPolicy {

	@Override
	protected Command getDeleteCommand(final GroupRequest request) {
		if (getHost().getModel() instanceof Link) {
			return new DeleteLinkCommand((Link) getHost().getModel());
		}
		return null;
	}
}
