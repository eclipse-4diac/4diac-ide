/*******************************************************************************
 * Copyright (c) 2008, 2012 Profactor GmbH, TU Wien ACIN
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

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

/**
 * An EditPolicy for showing feedback when selected.
 * 
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class FeedbackConnectionEndpointEditPolicy extends
		ConnectionEndpointEditPolicy {

	
	private int selectedLineWidth = 3;
	private int unselectedLineWidth = 0;
	
	
	
	public FeedbackConnectionEndpointEditPolicy(int unselectedLineWidth, int selectedLineWidth) {
		super();
		this.selectedLineWidth = selectedLineWidth;
		this.unselectedLineWidth = unselectedLineWidth;
	}
	
	

	public FeedbackConnectionEndpointEditPolicy() {
		super();
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy#addSelectionHandles()
	 */
	@Override
	protected void addSelectionHandles() {
		super.addSelectionHandles();
		getConnectionFigure().setLineWidth(selectedLineWidth);
	}

	/**
	 * Gets the connection figure.
	 * 
	 * @return the connection figure
	 */
	protected PolylineConnection getConnectionFigure() {
		return (PolylineConnection) ((GraphicalEditPart) getHost()).getFigure();
	}

	/*
	 *
	
	-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy#removeSelectionHandles()
	 */
	@Override
	protected void removeSelectionHandles() {
		super.removeSelectionHandles();
		getConnectionFigure().setLineWidth(unselectedLineWidth);
	}

}
