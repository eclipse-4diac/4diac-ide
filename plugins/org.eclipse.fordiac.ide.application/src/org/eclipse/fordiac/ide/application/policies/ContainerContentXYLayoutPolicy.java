/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Germany GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr, Alois Zoitl - initial implementation and/or documentation
 *   Alexander Lumplecker, Bianca Wiesmayr, Alois Zoitl - Bug: 568569
 *   Alois Zoitl - extracted most code into common base class for group
 *                 infrastructure
 *               - extracted this policy from the AbstractContainerContentEditPart
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;

public class ContainerContentXYLayoutPolicy extends FBNetworkXYLayoutEditPolicy {

	private Figure moveHandle;

	@Override
	protected void showLayoutTargetFeedback(final Request request) {
		if (REQ_ADD.equals(request.getType()) && (moveHandle == null)) {
			// we want to show the handle for the parent which is the whole group
			moveHandle = new ModifiedMoveHandle((GraphicalEditPart) getTargetEditPart(request).getParent(),
					new Insets(1), DiagramPreferences.CORNER_DIM_HALF);
			addFeedback(moveHandle);
		}
	}

	@Override
	protected void eraseLayoutTargetFeedback(final Request request) {
		if (moveHandle != null) {
			removeFeedback(moveHandle);
			moveHandle = null;
		}
	}
}