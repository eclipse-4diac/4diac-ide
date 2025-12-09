/*******************************************************************************
 * Copyright (c) 2008, 2009, 2012, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;

/**
 * The Class FBSelectionPolicy. Highlights the In/Outconnections of an FB.
 *
 * Returns AlignmentCommand for alignment of FBs.
 *
 */
public class FBNElementSelectionPolicy extends SelectionEditPolicy {

	@Override
	protected void hideSelection() {
		for (final EditPart object : getHost().getChildren()) {
			if (object instanceof final InterfaceEditPart iep) {
				iep.setInOutConnectionsWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);

			}
		}
	}

	@Override
	protected void showSelection() {
		for (final EditPart object : getHost().getChildren()) {
			if (object instanceof final InterfaceEditPart iep) {
				iep.setInOutConnectionsWidth(ConnectionPreferenceValues.HIGHLIGTHED_LINE_WIDTH);

			}
		}
	}

	/**
	 * @see org.eclipse.gef.EditPolicy#getCommand(org.eclipse.gef.Request)
	 */
	@Override
	public Command getCommand(final Request request) {
		final Object type = request.getType();
		if (REQ_ALIGN.equals(type) && request instanceof final AlignmentRequest ar) {
			return getAlignCommand(ar);
		}
		return null;
	}

	/**
	 * Returns the command contribution to an alignment request
	 *
	 * @param request the alignment request
	 * @return the contribution to the alignment
	 */
	protected Command getAlignCommand(final AlignmentRequest request) {
		final AlignmentRequest req = new AlignmentRequest(REQ_ALIGN_CHILDREN);
		req.setEditParts(getHost());
		req.setAlignment(request.getAlignment());
		req.setAlignmentRectangle(request.getAlignmentRectangle());
		return getHost().getParent().getCommand(req);
	}

}
