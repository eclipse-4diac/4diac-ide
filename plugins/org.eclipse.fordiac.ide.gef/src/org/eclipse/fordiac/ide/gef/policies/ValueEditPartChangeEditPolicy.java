/*******************************************************************************
 * Copyright (c) 2008, 2009, 2015 - 2017 Profactor GbmH, fortiss GmbH 
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Monika Wenger, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.fordiac.ide.gef.editparts.ValueEditPart;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

public class ValueEditPartChangeEditPolicy extends DirectEditPolicy {

	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		ValueEditPart valueEditPart = getValueEditPart();
		if (null != valueEditPart) {
			return new ChangeValueCommand(valueEditPart.getModel().getVarDeclaration(),
					(String) request.getCellEditor().getValue());
		}
		return null;
	}

	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		String value = (String) request.getCellEditor().getValue();
		ValueEditPart valueEditPart = getValueEditPart();
		if (null != valueEditPart) {
			valueEditPart.getFigure().setText(value);
		}
	}

	/**
	 * Retrieve the correct valueedit part for this policy Referencing classes like
	 * the interfaceedit part may override it
	 */
	protected ValueEditPart getValueEditPart() {
		ValueEditPart retval = null;
		if (getHost() instanceof ValueEditPart) {
			retval = (ValueEditPart) getHost();
		}
		return retval;
	}
}
