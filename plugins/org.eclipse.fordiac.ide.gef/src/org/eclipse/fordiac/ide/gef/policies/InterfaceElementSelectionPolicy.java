/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class InterfaceElementSelectionPolicy extends SelectionEditPolicy {
	/**
	 * 
	 */
	private final InterfaceEditPart interfaceEditPart;

	/**
	 * @param interfaceEditPart
	 */
	public InterfaceElementSelectionPolicy(InterfaceEditPart interfaceEditPart) {
		this.interfaceEditPart = interfaceEditPart;
	}

	@Override
	protected void showSelection() {
		this.interfaceEditPart.setInOutConnectionsWidth(2);
	}

	@Override
	protected void hideSelection() {
		this.interfaceEditPart.setInOutConnectionsWidth(0);
	}

	@Override
	public void eraseTargetFeedback(Request request) {
		getHostFigure().setCursor(null);
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		return request.getType().equals(RequestConstants.REQ_SELECTION_HOVER) ? getHost()
				: null;
	}

	@Override
	public void showTargetFeedback(Request request) {
		if (request instanceof SelectionRequest) {
			Point pos = ((SelectionRequest) request).getLocation();
			getHostFigure().translateToRelative(pos);
			getHostFigure().setCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_CROSS));
		}
	}
}