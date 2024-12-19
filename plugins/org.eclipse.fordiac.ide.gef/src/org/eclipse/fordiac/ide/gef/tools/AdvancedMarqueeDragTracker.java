/*******************************************************************************
 * Copyright (c) 2022 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Alois Zoitl - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.tools;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.MarqueeDragTracker;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.MouseEvent;

/**
 * MarqueeDragTracker which deselects all elements on right click if nothing so
 * that the correct context menu is shown. We are only here if there is no
 * element under the cursor.
 *
 * Furthermore it performs autoscrolling if the user went beyond the viewport
 * boundaries.
 */
public class AdvancedMarqueeDragTracker extends MarqueeDragTracker {

	private static final Request MARQUEE_REQUEST = new Request(RequestConstants.REQ_SELECTION);

	// Safety border around the canvas to ensure that during dragging marquee
	// selection does not grow the canvas
	private static final Insets MARQUEE_DRAG_BORDER = new Insets(1, 1, 1, 1);

	private static class MarqueeRectangleFigure extends Figure {

		@Override
		protected void paintFigure(final Graphics graphics) {
			graphics.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
			graphics.setLineStyle(Graphics.LINE_DASH);
			graphics.setLineWidth(ModifiedMoveHandle.SELECTION_BORDER_WIDTH + 1);
			final Rectangle bounds = Rectangle.SINGLETON.setBounds(getBounds());
			graphics.drawRectangle(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);
		}
	}

	private EditPartViewer startingViewer;

	@Override
	protected boolean handleButtonDown(final int button) {
		if (3 == button && getCurrentViewer() != null) {
			// on right click deselect everything
			getCurrentViewer().setSelection(getDefaultSelectionForRightMouseDown());
		}
		return super.handleButtonDown(button);
	}

	@SuppressWarnings("static-method") // allow sub-classes to provide own default selections
	protected StructuredSelection getDefaultSelectionForRightMouseDown() {
		return StructuredSelection.EMPTY;
	}

	@Override
	protected EditPartViewer getCurrentViewer() {
		// use the viewer in which the selection started as this is where the feedback
		// figure is located
		if (startingViewer != null) {
			return startingViewer;
		}
		return super.getCurrentViewer();
	}

	@Override
	public void mouseDown(final MouseEvent me, final EditPartViewer viewer) {
		if (viewer instanceof final AdvancedScrollingGraphicalViewer advScrollingGraphicalViewer) {
			CanvasHelper.bindToContentPane(me, advScrollingGraphicalViewer, MARQUEE_DRAG_BORDER);
		}
		startingViewer = viewer;
		super.mouseDown(me, viewer);
	}

	@Override
	public void mouseUp(final MouseEvent me, final EditPartViewer viewer) {
		super.mouseUp(me, startingViewer);
		startingViewer = null;
	}

	@Override
	public void mouseDrag(final MouseEvent me, final EditPartViewer viewer) {
		if (isActive() && viewer instanceof final AdvancedScrollingGraphicalViewer advScrollingGraphicalViewer) {
			final Point oldViewPort = advScrollingGraphicalViewer.getViewLocation();
			((AdvancedScrollingGraphicalViewer) viewer).checkScrollPositionDuringDrag(me);
			final Dimension delta = oldViewPort.getDifference(advScrollingGraphicalViewer.getViewLocation());
			// Compensate the moved scrolling in the start position for correct dropping of
			// moved parts
			setStartLocation(getStartLocation().getTranslated(delta));
			CanvasHelper.bindToContentPane(me, advScrollingGraphicalViewer, MARQUEE_DRAG_BORDER);
		}
		super.mouseDrag(me, viewer);
	}

	@Override
	protected boolean handleDoubleClick(final int button) {
		if (1 == button) {
			performOpen();
		}
		return true;
	}

	@Override
	protected IFigure createMarqueeRectangleFigure() {
		return new MarqueeRectangleFigure();
	}

	protected void performOpen() {
		final EditPart editPart = getCurrentViewer().findObjectAt(getLocation());
		if (null != editPart) {
			final SelectionRequest request = new SelectionRequest();
			request.setLocation(getLocation());
			request.setType(RequestConstants.REQ_OPEN);
			editPart.performRequest(request);
		}
	}

	// In the base class version not shown elements can not be selected, as we have
	// now auto-scrolling this is not a
	// good behavior therefore this overridden version. For details see base class.
	@Override
	protected boolean isMarqueeSelectable(final GraphicalEditPart editPart) {
		return editPart.getTargetEditPart(MARQUEE_REQUEST) == editPart && editPart.isSelectable();
	}
}