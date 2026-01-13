/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University
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

import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.ui.UtilityMarkerHelper;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
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
	public InterfaceElementSelectionPolicy(final InterfaceEditPart interfaceEditPart) {
		this.interfaceEditPart = interfaceEditPart;
	}

	@Override
	protected void showSelection() {
		this.interfaceEditPart.setInOutConnectionsWidth(ConnectionPreferenceValues.HIGHLIGTHED_LINE_WIDTH);
	}

	@Override
	protected void hideSelection() {
		this.interfaceEditPart.setInOutConnectionsWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
	}

	@Override
	public void eraseTargetFeedback(final Request request) {
		getHostFigure().setCursor(null);
	}

	@Override
	public EditPart getTargetEditPart(final Request request) {
		return request.getType().equals(RequestConstants.REQ_SELECTION_HOVER) ? getHost() : null;
	}

	@Override
	public void showTargetFeedback(final Request request) {
		final int cursorId = UtilityMarkerHelper.getMarkedElement(UtilityMarkerHelper.CONNECTION_SRC_MARKER_ID,
				interfaceEditPart.getModel()) != null ? SWT.CURSOR_HAND : SWT.CURSOR_CROSS;
		getHostFigure().setCursor(Display.getDefault().getSystemCursor(cursorId));
	}
}
