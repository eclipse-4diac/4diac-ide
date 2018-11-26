/*******************************************************************************
 * Copyright (c) 2015 Profactor GbmH 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.figures.InteractionStyleFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

/**
 * The Class ConnectionMovementHighlightEditPolicy.
 */
public class ConnectionMovementHighlightEditPolicy extends
		org.eclipse.gef.editpolicies.GraphicalEditPolicy {

	private int style;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.AbstractEditPolicy#eraseTargetFeedback(org
	 * .eclipse.gef.Request)
	 */
	@Override
	public void eraseTargetFeedback(Request request) {
		getHostFigure().setCursor(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.AbstractEditPolicy#getTargetEditPart(org
	 * .eclipse.gef.Request)
	 */
	@Override
	public EditPart getTargetEditPart(Request request) {
		return request.getType().equals(RequestConstants.REQ_SELECTION_HOVER) ? getHost()
				: null;
	}

	public int getCurrentInteractionStyle() {
		return style;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.AbstractEditPolicy#showTargetFeedback(org
	 * .eclipse.gef.Request)
	 */
	@Override
	public void showTargetFeedback(Request request) {
		if (request instanceof SelectionRequest) {
			Point pos = ((SelectionRequest) request).getLocation();
			getHostFigure().translateToRelative(pos);
			if (getHostFigure() instanceof InteractionStyleFigure) {
				style = ((InteractionStyleFigure)getHostFigure()).getIntersectionStyle(pos);
				if (style == InteractionStyleFigure.REGION_CONNECTION) {
					getHostFigure().setCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_CROSS));
				} else if (style == InteractionStyleFigure.REGION_DRAG) {
					getHostFigure().setCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_SIZEALL));
				} else {
					getHostFigure().setCursor(null);
				}
			}
		}
	}
}