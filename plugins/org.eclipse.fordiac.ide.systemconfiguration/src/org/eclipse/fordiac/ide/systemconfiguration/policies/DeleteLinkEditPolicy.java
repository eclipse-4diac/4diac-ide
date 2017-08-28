/*******************************************************************************
 * Copyright (c) 2008, 2016 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.policies;

import org.eclipse.fordiac.ide.systemconfiguration.commands.DeleteLinkCommand;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.LinkEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

public class DeleteLinkEditPolicy extends org.eclipse.gef.editpolicies.ConnectionEditPolicy {

	@Override
	protected Command getDeleteCommand(final GroupRequest request) {
		if (getHost() instanceof LinkEditPart) {
			return new DeleteLinkCommand(((LinkEditPart) getHost()).getModel());
		}
		return null;
	}
}
