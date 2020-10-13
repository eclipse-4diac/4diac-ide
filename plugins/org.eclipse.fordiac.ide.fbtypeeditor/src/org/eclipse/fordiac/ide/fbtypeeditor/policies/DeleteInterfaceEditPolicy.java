/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.policies;

import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

public class DeleteInterfaceEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command getDeleteCommand(final GroupRequest request) {
		if (getHost().getModel() instanceof IInterfaceElement) {
			return new DeleteInterfaceCommand((IInterfaceElement) getHost().getModel());
		}
		return super.getDeleteCommand(request);
	}
}
