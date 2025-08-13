/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.gef.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.editparts.AbstractFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.AbstractPositionableElementEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class TrackerMarginBoundsHelper extends MarginBoundsHelper {
	private static final int CORNER_RADIUS = 4;
	private static final int FIGURE_ALPHA = 100;
	private static Color fillColor = null;

	public static Color getFillColor() {
		if (null == fillColor) {
			fillColor = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
		}
		return fillColor;
	}

	private EditPart currentTarget = null;
	private IFigure feedbackLayer;
	private List<? extends EditPart> selection;
	private final List<Figure> figureList = new ArrayList<>();
	private final List<Figure> parentFigureList = Arrays.asList((Figure) null);

	public void initDrag(final EditPart sourceEP, final List<? extends EditPart> selected) {
		super.updateMargins(sourceEP.getModel());
		feedbackLayer = LayerManager.Helper.find(sourceEP).getLayer(LayerConstants.FEEDBACK_LAYER);
		selection = selected;
	}

	public List<Figure> getFigures() {
		return figureList;
	}

	public List<Figure> getParentFigure() {
		return parentFigureList;
	}

	public void createFigures(final EditPart targetEditPart) {
		if (currentTarget != targetEditPart) {
			clearFigureList();
			currentTarget = targetEditPart;

			targetEditPart.getChildren().stream().filter(AbstractPositionableElementEditPart.class::isInstance)
					.map(AbstractPositionableElementEditPart.class::cast).filter(Predicate.not(selection::contains))
					.forEach(ep -> {
						final Rectangle bounds = ep.getFigure().getBounds().getCopy();
						if (!(ep.getModel() instanceof Comment)) {
							expandRectangle(bounds);
						}

						final Figure figure = TrackerMarginBoundsHelper.createFigure(bounds,
								ep.getFigure().getForegroundColor());
						figure.validate();
						figureList.add(figure);
					});

			if (targetEditPart instanceof final AbstractFBNetworkEditPart networkEditPart
					&& networkEditPart.getModel() != null
					&& networkEditPart.getModel().eContainer() instanceof final SubApp subApp && subApp.isUnfolded()) {
				final Rectangle bounds = networkEditPart.getFigure().getBounds().getCopy();
				final Figure figureFigure = new Figure();
				figureFigure.setBounds(bounds);
				figureFigure.validate();
				parentFigureList.set(0, figureFigure);
			}

			figureList.forEach(feedbackLayer::add);
		}
	}

	public void clearFigureList() {
		figureList.forEach(figure -> {
			if (feedbackLayer.getChildren().contains(figure)) {
				feedbackLayer.remove(figure);
			}
		});
		figureList.clear();
		parentFigureList.set(0, null);
		currentTarget = null;
	}

	public static RoundedRectangle createFigure(final Rectangle bounds, final Color foregroundColor) {
		final RoundedRectangle figure = new RoundedRectangle();
		figure.setBounds(bounds);
		figure.setOutline(false);
		figure.setAlpha(FIGURE_ALPHA);
		figure.setCornerDimensions(new Dimension(CORNER_RADIUS, CORNER_RADIUS));
		figure.setForegroundColor(foregroundColor);
		figure.setBackgroundColor(getFillColor());
		return figure;
	}
}
