/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2017 Profactor GbmH, fortiss GmbH
 * 				 2020 Primetals Technologies Germany GmbH
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
 *   Alois Zoitl - added selection hover feedback
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

/**
 * NonResizeableEditPolicy with a rounded EtchedBorder and moveHandles (move
 * cursor) on each side.
 */
public class ModifiedNonResizeableEditPolicy extends NonResizableEditPolicy {

	private int arc = DiagramPreferences.CORNER_DIM;

	private Insets insets = new Insets(2);

	/**
	 * Constructor.
	 *
	 * @param arc    the arc
	 * @param insets the insets
	 */
	public ModifiedNonResizeableEditPolicy(int arc, Insets insets) {
		super();
		this.arc = arc;
		this.insets = insets;
	}

	/**
	 * Default constructor.
	 */
	public ModifiedNonResizeableEditPolicy() {
		super();
	}

	@Override
	protected List<? extends IFigure> createSelectionHandles() {
		List<ModifiedMoveHandle> list = new ArrayList<>(1);
		list.add(new ModifiedMoveHandle((GraphicalEditPart) getHost(), insets, arc));
		removeSelectionFeedbackFigure();
		return list;
	}

	private RoundedRectangle selectionFeedback;

	@Override
	public void showTargetFeedback(Request request) {
		super.showTargetFeedback(request);

		if (((REQ_SELECTION.equals(request.getType())) || (REQ_SELECTION_HOVER.equals(request.getType())))
				&& (null == selectionFeedback) && (null == handles)) { // we don't have already a feedback showing and
																		// we are not selected
			selectionFeedback = createSelectionFeedbackFigure(request);
			if (null != selectionFeedback) {
				addFeedback(selectionFeedback);
			}
		}
	}

	private RoundedRectangle createSelectionFeedbackFigure(Request request) {
		if (getTargetEditPart(request) instanceof GraphicalEditPart) {
			GraphicalEditPart ep = ((GraphicalEditPart) getTargetEditPart(request));
			RoundedRectangle newSelFeedbackFigure = new RoundedRectangle();
			newSelFeedbackFigure.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
			newSelFeedbackFigure.setOutline(false);
			newSelFeedbackFigure.setBounds(getSelectableFigureBounds(ep));
			newSelFeedbackFigure.setCornerDimensions(new Dimension(arc, arc));
			newSelFeedbackFigure.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
			newSelFeedbackFigure.setBackgroundColor(ModifiedMoveHandle.getSelectionColor());
			return newSelFeedbackFigure;
		}
		return null;
	}

	private static Rectangle getSelectableFigureBounds(GraphicalEditPart ep) {
		Rectangle bounds = ep.getFigure().getBounds().getExpanded(2, 2);
		ZoomManager zoomManager = ((ScalableFreeformRootEditPart) ep.getRoot()).getZoomManager();
		bounds.scale(zoomManager.getZoom());
		return bounds;
	}

	@Override
	public void eraseTargetFeedback(Request request) {
		super.showTargetFeedback(request);
		removeSelectionFeedbackFigure();
	}

	private void removeSelectionFeedbackFigure() {
		if (selectionFeedback != null) {
			removeFeedback(selectionFeedback);
			selectionFeedback = null;
		}
	}
}
