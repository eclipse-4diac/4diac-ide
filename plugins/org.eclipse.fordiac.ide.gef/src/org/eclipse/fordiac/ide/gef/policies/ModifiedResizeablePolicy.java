/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Germany GmbH
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

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.fordiac.ide.gef.editparts.AbstractConnectableEditPart;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;

public class ModifiedResizeablePolicy extends ResizableEditPolicy {

	private final Insets insets = new Insets(2);

	private IFigure selectionFeedback;

	@Override
	public void deactivate() {
		super.deactivate();
		removeSelectionFeedbackFigure();
	}

	@Override
	protected void createMoveHandle(final List<Handle> handles) {
		handles.add(new ModifiedMoveHandle(getHost(), insets, GefPreferenceConstants.CORNER_DIM));
		removeSelectionFeedbackFigure();
	}

	@Override
	protected IFigure createDragSourceFeedbackFigure() {
		final RectangleFigure figure = (RectangleFigure) super.createDragSourceFeedbackFigure();
		figure.setFillXOR(false);
		figure.setOutlineXOR(false);
		figure.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
		figure.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
		figure.setBackgroundColor(ModifiedMoveHandle.getSelectionColor());
		figure.setLineWidth(ModifiedMoveHandle.SELECTION_BORDER_WIDTH);
		return figure;
	}

	@Override
	public void showTargetFeedback(final Request request) {
		super.showTargetFeedback(request);

		if (isFeedbackRequest(request) && (null == selectionFeedback) && (null == handles)) {
			// we don't have already a feedback showing and we are not selected
			selectionFeedback = createSelectionFeedbackFigure();
			if (null != selectionFeedback) {
				addFeedback(selectionFeedback);
			}
		}
	}

	protected IFigure createSelectionFeedbackFigure() {
		return ModifiedNonResizeableEditPolicy.createSelectionFeedbackFigure(getHost(), GefPreferenceConstants.CORNER_DIM);
	}

	@Override
	public void eraseTargetFeedback(final Request request) {
		super.showTargetFeedback(request);
		removeSelectionFeedbackFigure();
	}

	private void removeSelectionFeedbackFigure() {
		if (selectionFeedback != null) {
			removeFeedback(selectionFeedback);
			selectionFeedback = null;
		}
	}

	private boolean isFeedbackRequest(final Request request) {
		return (REQ_SELECTION.equals(request.getType()))
				|| (REQ_SELECTION_HOVER.equals(request.getType()) || isValidConnectionRequest(request));
	}

	private boolean isValidConnectionRequest(final Request request) {
		return (getHost() instanceof final AbstractConnectableEditPart acEP) && acEP.isConnectable()
				&& (REQ_RECONNECT_SOURCE.equals(request.getType()) || REQ_RECONNECT_TARGET.equals(request.getType())
						|| REQ_CONNECTION_END.equals(request.getType()));
	}

}