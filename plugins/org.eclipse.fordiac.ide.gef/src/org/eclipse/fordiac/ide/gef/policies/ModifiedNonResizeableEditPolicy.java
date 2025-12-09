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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.editparts.AbstractConnectableEditPart;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

/**
 * NonResizeableEditPolicy with a rounded EtchedBorder and moveHandles (move
 * cursor) on each side.
 */
public class ModifiedNonResizeableEditPolicy extends NonResizableEditPolicy {

	private int arc = GefPreferenceConstants.CORNER_DIM;

	private Insets insets = new Insets(2);

	/**
	 * Constructor.
	 *
	 * @param arc    the arc
	 * @param insets the insets
	 */
	public ModifiedNonResizeableEditPolicy(final int arc, final Insets insets) {
		this.arc = arc;
		this.insets = insets;
	}

	/**
	 * Default constructor.
	 */
	public ModifiedNonResizeableEditPolicy() {
	}

	@Override
	public void deactivate() {
		super.deactivate();
		removeSelectionFeedbackFigure();
	}

	@Override
	protected List<Handle> createSelectionHandles() {
		final List<Handle> list = new ArrayList<>(1);
		list.add(new ModifiedMoveHandle(getHost(), insets, arc));
		removeSelectionFeedbackFigure();
		return list;
	}

	private RoundedRectangle selectionFeedback;

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

	private boolean isFeedbackRequest(final Request request) {
		return (REQ_SELECTION.equals(request.getType()))
				|| (REQ_SELECTION_HOVER.equals(request.getType()) || isValidConnectionRequest(request));
	}

	private boolean isValidConnectionRequest(final Request request) {
		return (getHost() instanceof final AbstractConnectableEditPart acEP) && acEP.isConnectable()
				&& (REQ_RECONNECT_SOURCE.equals(request.getType()) || REQ_RECONNECT_TARGET.equals(request.getType())
						|| REQ_CONNECTION_END.equals(request.getType()));
	}

	protected RoundedRectangle createSelectionFeedbackFigure() {
		return createSelectionFeedbackFigure(getHost(), arc);
	}

	public static RoundedRectangle createSelectionFeedbackFigure(final EditPart editPart, final int arc) {
		if (editPart instanceof final GraphicalEditPart ep) {
			final RoundedRectangle newSelFeedbackFigure = new RoundedRectangle();
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

	private static Rectangle getSelectableFigureBounds(final GraphicalEditPart ep) {
		return ep.getFigure().getBounds().getExpanded(2, 2);
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

	@Override
	protected IFigure createDragSourceFeedbackFigure() {
		final Figure r = new Figure();
		r.setBorder(new ModifiedMoveHandle.SelectionBorder(arc));
		r.setBounds(getInitialFeedbackBounds());
		r.validate();
		addFeedback(r);
		return r;
	}
}
