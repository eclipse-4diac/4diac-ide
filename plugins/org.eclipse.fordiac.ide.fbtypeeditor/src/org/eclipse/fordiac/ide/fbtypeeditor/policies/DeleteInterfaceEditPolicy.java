/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.policies;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class DeleteInterfaceEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command getDeleteCommand(final GroupRequest request) {
		if (getHost() instanceof InterfaceEditPart) {
			InterfaceEditPart iep = (InterfaceEditPart) getHost();
			DeleteInterfaceCommand c = new DeleteInterfaceCommand(iep
					.getCastedModel());
			return c;
		}
		return super.getDeleteCommand(request);
	}
}
