/*******************************************************************************
 * Copyright (c) 2015, 2023 Profactor GbmH, fortiss GmbH
 *                          Martin Erich Jobst
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
 *   Martin Jobst
 *     - allow subclasses to implement connectable property
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.editparts;

import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.fordiac.ide.gef.policies.ConnectionMovementHighlightEditPolicy;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public abstract class AbstractConnectableEditPart extends AbstractGraphicalEditPart {

	@SuppressWarnings("static-method") // allow subclasses to override
	public boolean isConnectable() {
		return false;
	}

	private ConnectionMovementHighlightEditPolicy cmPolicy;

	@Override
	protected void createEditPolicies() {
		if (isConnectable()) {
			cmPolicy = new ConnectionMovementHighlightEditPolicy();
			installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, cmPolicy);
		}
	}

	@Override
	public DragTracker getDragTracker(final Request request) {
		if (useConnectionTool()) {
			return new ConnCreateDirectEditDragTrackerProxy(this);
		}
		return super.getDragTracker(request);
	}

	protected boolean useConnectionTool() {
		if (!isConnectable() || cmPolicy == null) {
			return false;
		}
		return cmPolicy.getCurrentInteractionStyle() == InteractionStyleFigure.REGION_CONNECTION;
	}

}
