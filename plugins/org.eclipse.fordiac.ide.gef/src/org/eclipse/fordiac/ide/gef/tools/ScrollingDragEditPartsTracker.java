/*******************************************************************************
 * Copyright (c) 2019, 2025 Johannes Kepler University Linz, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - Added Figures with Margin to other Elements
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.utilities.CollisionChangeBoundsRequest;
import org.eclipse.fordiac.ide.gef.utilities.MarginBoundsHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;

public class ScrollingDragEditPartsTracker extends DragEditPartsTracker {
	private static final int CORNER_RADIUS = 4;
	private static final Color BORDER_COLOR = ModifiedMoveHandle.getSelectionColor();
	private final MarginBoundsHelper boundsHelper = new MarginBoundsHelper();

	private IFigure feedbackLayer;
	private List<? extends EditPart> selection;
	private EditPart currentTarget = null;
	private final List<Figure> figureList = new ArrayList<>();

	public ScrollingDragEditPartsTracker(final EditPart sourceEditPart) {
		super(sourceEditPart);
	}

	@Override
	protected Request createTargetRequest() {
		if (isCloneActive()) {
			return new ChangeBoundsRequest(REQ_CLONE);
		}
		return new CollisionChangeBoundsRequest(REQ_MOVE, figureList);
	}

	@Override
	public void mouseDrag(final MouseEvent me, final EditPartViewer viewer) {
		if (isActive() && viewer instanceof final AdvancedScrollingGraphicalViewer scrollingViewer) {
			final Point oldViewPort = scrollingViewer.getViewLocation();
			((AdvancedScrollingGraphicalViewer) viewer).checkScrollPositionDuringDrag(me);
			final Dimension delta = oldViewPort.getDifference(scrollingViewer.getViewLocation());
			// Compensate the moved scrolling in the start position for correct dropping of
			// moved parts
			setStartLocation(getStartLocation().getTranslated(delta));
		}
		super.mouseDrag(me, viewer);
	}

	@Override
	protected boolean handleDragStarted() {
		boundsHelper.updateMargins(getSourceEditPart().getModel());
		feedbackLayer = LayerManager.Helper.find(getSourceEditPart()).getLayer(LayerConstants.FEEDBACK_LAYER);
		selection = getCurrentViewer().getSelectedEditParts();
		return super.handleDragStarted();
	}

	@Override
	protected void eraseSourceFeedback() {
		clearFigureList();
		super.eraseSourceFeedback();
	}

	@Override
	protected void showSourceFeedback() {
		if (currentTarget != getTargetEditPart()) {
			currentTarget = getTargetEditPart();
			clearFigureList();

			getTargetEditPart().getChildren().stream().filter(AbstractPositionableElementEditPart.class::isInstance)
					.map(AbstractPositionableElementEditPart.class::cast).filter(Predicate.not(selection::contains))
					.forEach(ep -> {
						final Rectangle bounds = ep.getFigure().getBounds().getCopy();
						if (!(ep.getModel() instanceof Comment)) {
							boundsHelper.expandRectangle(bounds);
						}

						final Figure figure = createFigure(bounds);
						figure.validate();
						figureList.add(figure);
					});

			figureList.forEach(feedbackLayer::add);
		}

		super.showSourceFeedback();
	}

	private void clearFigureList() {
		figureList.forEach(figure -> {
			if (feedbackLayer.getChildren().contains(figure)) {
				feedbackLayer.remove(figure);
			}
		});
		figureList.clear();
	}

	private static RoundedRectangle createFigure(final Rectangle bounds) {
		final RoundedRectangle figure = new RoundedRectangle();
		figure.setBounds(bounds);
		figure.setFill(false);
		figure.setOutline(true);
		figure.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
		figure.setCornerDimensions(new Dimension(CORNER_RADIUS, CORNER_RADIUS));
		figure.setForegroundColor(BORDER_COLOR);
		figure.setBackgroundColor(BORDER_COLOR);
		figure.setLineWidth(ModifiedMoveHandle.SELECTION_BORDER_WIDTH);
		return figure;
	}
}