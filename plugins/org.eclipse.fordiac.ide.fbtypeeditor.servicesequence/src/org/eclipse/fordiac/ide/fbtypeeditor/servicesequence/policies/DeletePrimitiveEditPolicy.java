/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.policies;

import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteInputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.DeleteOutputPrimitiveCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.InputPrimitiveEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.OutputPrimitiveEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * Policy for deleting ServiceSequence.
 * 
 * @author gebenh
 */
public class DeletePrimitiveEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command getDeleteCommand(GroupRequest request) {
		if (getHost() instanceof InputPrimitiveEditPart) {
			DeleteInputPrimitiveCommand c = new DeleteInputPrimitiveCommand(
					((InputPrimitiveEditPart) getHost()).getCastedModel());
			return c;
		}
		if (getHost() instanceof OutputPrimitiveEditPart) {
			DeleteOutputPrimitiveCommand c = new DeleteOutputPrimitiveCommand(
					((OutputPrimitiveEditPart) getHost()).getCastedModel());
			return c;
		}
		return null;
	}

}
