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

/** MarqueeDragTracker which deselects all elements on right click if nothing so that the correct context menu is
 * shown. We are only here if there is no element under the cursor.
 *
 * Furthermore it performs autoscrolling if the user went beyond the viewport boundaries. */
public class AdvancedMarqueeDragTracker extends MarqueeDragTracker {

	private static final Request MARQUEE_REQUEST = new Request(RequestConstants.REQ_SELECTION);

	// Safety border around the canvas to ensure that during dragging marquee selection does not grow the canvas
	private static final Insets MARQUEE_DRAG_BORDER = new Insets(1, 1, 1, 1);

	private static class MarqueeRectangleFigure extends Figure {

		@Override
		protected void paintFigure(final Graphics graphics) {
			final Rectangle bounds = getBounds().getCopy();
			graphics.translate(getLocation());

			graphics.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
			graphics.setLineStyle(Graphics.LINE_DASH);
			graphics.setLineWidth(ModifiedMoveHandle.SELECTION_BORDER_WIDTH + 1);

			graphics.drawRectangle(0, 0, bounds.width - 1, bounds.height - 1);
		}
	}

	@Override
	protected boolean handleButtonDown(final int button) {
		if (3 == button && getCurrentViewer() != null) {
			// on right click deselect everything

			getCurrentViewer().setSelection(getDefaultSelectionForRightMouseDown());

		}
		return super.handleButtonDown(button);
	}

	@SuppressWarnings("static-method")  // allow sub-classes to provide own default selections
	protected StructuredSelection getDefaultSelectionForRightMouseDown() {
		return StructuredSelection.EMPTY;
	}

	@Override
	public void mouseDown(final MouseEvent me, final EditPartViewer viewer) {
		if (viewer instanceof AdvancedScrollingGraphicalViewer) {
			CanvasHelper.bindToContentPane(me, (AdvancedScrollingGraphicalViewer) viewer, MARQUEE_DRAG_BORDER);
		}
		super.mouseDown(me, viewer);
	}

	@Override
	public void mouseDrag(final MouseEvent me, final EditPartViewer viewer) {
		if (isActive() && viewer instanceof AdvancedScrollingGraphicalViewer) {
			final Point oldViewPort = ((AdvancedScrollingGraphicalViewer) viewer).getViewLocation();
			((AdvancedScrollingGraphicalViewer) viewer).checkScrollPositionDuringDrag(me);
			final Dimension delta = oldViewPort
					.getDifference(((AdvancedScrollingGraphicalViewer) viewer).getViewLocation());
			// Compensate the moved scrolling in the start position for correct dropping of
			// moved parts
			setStartLocation(getStartLocation().getTranslated(delta));
			CanvasHelper.bindToContentPane(me, (AdvancedScrollingGraphicalViewer) viewer, MARQUEE_DRAG_BORDER);
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

	// In the base class version not shown elements can not be selected, as we have now auto-scrolling this is not a
	// good behavior therefore this overridden version. For details see base class.
	@Override
	protected boolean isMarqueeSelectable(final GraphicalEditPart editPart) {
		return editPart.getTargetEditPart(MARQUEE_REQUEST) == editPart && editPart.isSelectable();
	}
}