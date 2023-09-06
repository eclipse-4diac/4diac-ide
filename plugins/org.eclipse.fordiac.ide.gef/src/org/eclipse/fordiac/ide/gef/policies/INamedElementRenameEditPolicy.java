/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GbmH, fortiss GmbH,
 *                          Primetals Technologies Austria GmbH
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
 *   Alois Zoitl - added a default implementation for revertOldEditValue
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.fordiac.ide.gef.editparts.AbstractDirectEditableEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class INamedElementRenameEditPolicy extends DirectEditPolicy {

	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		if (getHost() instanceof final AbstractDirectEditableEditPart viewEditPart) {
			return ChangeNameCommand.forName(viewEditPart.getINamedElement(), (String) request.getCellEditor().getValue());
		}
		return null;
	}

	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		final String value = (String) request.getCellEditor().getValue();
		if (getHost() instanceof final AbstractDirectEditableEditPart viewEditPart) {
			viewEditPart.getNameLabel().setText(value);
		}
	}

	@Override
	protected void revertOldEditValue(final DirectEditRequest request) {
		if (getHost() instanceof final AbstractDirectEditableEditPart viewEditPart) {
			viewEditPart.getNameLabel().setText(viewEditPart.getINamedElement().getName());
		}
	}
}
