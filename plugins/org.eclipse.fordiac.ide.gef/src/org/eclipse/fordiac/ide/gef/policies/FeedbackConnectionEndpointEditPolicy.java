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
package org.eclipse.fordiac.ide.gef.policies;

import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

/**
 * An EditPolicy for showing feedback when selected.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class FeedbackConnectionEndpointEditPolicy extends ConnectionEndpointEditPolicy {

	private int selectedLineWidth = ConnectionPreferenceValues.SELECTED_LINE_WIDTH;
	private int unselectedLineWidth = ConnectionPreferenceValues.NORMAL_LINE_WIDTH;

	public FeedbackConnectionEndpointEditPolicy(int unselectedLineWidth, int selectedLineWidth) {
		super();
		this.selectedLineWidth = selectedLineWidth;
		this.unselectedLineWidth = unselectedLineWidth;
	}

	public FeedbackConnectionEndpointEditPolicy() {
		super();
	}

	@Override
	protected void showSelection() {
		super.showSelection();
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

	@SuppressWarnings("rawtypes")
	@Override
	protected List createSelectionHandles() {
		List list = super.createSelectionHandles();
		for (Object object : list) {
			if (object instanceof Figure) {
				((Figure) object).setPreferredSize(ConnectionPreferenceValues.HANDLE_SIZE,
						ConnectionPreferenceValues.HANDLE_SIZE);
			}
		}
		return list;
	}

	@Override
	protected void hideSelection() {
		super.hideSelection();
		getConnectionFigure().setLineWidth(unselectedLineWidth);
	}

}
