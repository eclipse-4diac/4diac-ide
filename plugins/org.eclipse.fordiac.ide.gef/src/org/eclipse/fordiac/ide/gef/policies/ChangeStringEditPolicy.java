/*******************************************************************************
 * Copyright (c) 2008, 2015 Profactor GbmH, fortiss GmbH
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
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class ChangeStringEditPolicy extends DirectEditPolicy {

	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		if (getHost() instanceof IChangeStringEditPart) {
			IChangeStringEditPart viewEditPart = (IChangeStringEditPart) getHost();
			return ChangeNameCommand.forName((INamedElement) viewEditPart.getElement(),
					(String) request.getCellEditor().getValue());
		}
		return null;
	}

	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		if (getHost() instanceof IChangeStringEditPart) {
			IChangeStringEditPart editPart = (IChangeStringEditPart) getHost();
			editPart.getLabel().setText(value);
		}
	}
}
