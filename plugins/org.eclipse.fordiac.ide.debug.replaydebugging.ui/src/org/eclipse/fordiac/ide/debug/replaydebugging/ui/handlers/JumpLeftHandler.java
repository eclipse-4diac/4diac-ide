/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.ui.handlers;

import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.EventMarkerEditPart;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.NavigationRequest;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.NavigationRequest.Direction;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart.TimelineEditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class JumpLeftHandler extends AbstractNavigationHandler {
	@Override
	protected NavigationRequest.Direction getDirection() {
		return Direction.LEFT;
	}

	@Override
	protected boolean isJump() {
		return true;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (!(selection instanceof final IStructuredSelection s) || s.isEmpty()) {
			setBaseEnabled(false);
			return;
		}
		final Object first = s.getFirstElement();
		// Only enable when a Timeline or Event is selected, not a Resource
		setBaseEnabled(first instanceof TimelineEditPart || first instanceof EventMarkerEditPart);
	}
}
