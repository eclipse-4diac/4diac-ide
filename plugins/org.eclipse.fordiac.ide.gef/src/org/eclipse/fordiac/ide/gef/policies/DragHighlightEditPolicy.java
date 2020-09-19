/*******************************************************************************
 * Copyright (c) 2020 Primetals Technology Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class DragHighlightEditPolicy extends GraphicalEditPolicy {

	@Override
	public void eraseTargetFeedback(Request request) {
		getHostFigure().setCursor(null);
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		return request.getType().equals(RequestConstants.REQ_SELECTION_HOVER) ? getHost() : null;
	}

	@Override
	public void showTargetFeedback(Request request) {
		if (request instanceof SelectionRequest) {
			getHostFigure().setCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_SIZEALL));
		}
	}
}
