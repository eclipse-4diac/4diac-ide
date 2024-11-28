/*******************************************************************************
 * Copyright (c) 2010, 2023 IBM Corporation and others.
 * 				 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Research Group Software Construction,
 *     RWTH Aachen University, Germany - initial API and implementation
 *     Daniel Lindhuber - copied from gef-classic and altered for use without scrollpane
 */
package org.eclipse.fordiac.ide.application.policies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.draw2d.GhostImageFigure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editpolicies.SelectionEditPolicy;
import org.eclipse.gef.util.EditPartUtilities;

public class SelectionFeedbackHiddenChildren extends SelectionEditPolicy {

	private static int feedbackAlpha = 100;

	private final List<IFigure> feedbackFigures = new ArrayList<>();

	@Override
	protected void showSelection() {
		if (getHost().getSelected() == EditPart.SELECTED_PRIMARY) {
			showFeedback();
		}
	}

	@Override
	protected void hideSelection() {
		feedbackFigures.forEach(this::removeFeedback);
		feedbackFigures.clear();
	}

	@Override
	protected IFigure getFeedbackLayer() {
		return getLayer(LayerConstants.SCALED_FEEDBACK_LAYER);
	}

	@Override
	public SubAppForFBNetworkEditPart getHost() {
		return (SubAppForFBNetworkEditPart) super.getHost();
	}

	private void showFeedback() {
		final Rectangle parentBounds = getHost().getContentEP().getFigure().getBounds();

		// nodes
		getHost().getContentEP().getChildren().stream().filter(child -> isOutsideParentBounds(child, parentBounds))
				.forEach(this::createNodeFeedbackFigure);

		// connections
		final HashSet<? extends ConnectionEditPart> transitiveNestedConnections = EditPartUtilities
				.getAllNestedConnectionEditParts(getHost());
		transitiveNestedConnections.stream().filter(child -> isOutsideParentBounds(child, parentBounds))
				.forEach(this::createConnectionFeedbackFigure);
	}

	private static boolean isOutsideParentBounds(final GraphicalEditPart child, final Rectangle parentBounds) {
		final Rectangle childBounds = child.getFigure().getBounds();
		return !parentBounds.contains(childBounds);
	}

	private void addFeedbackFigure(final IFigure feedbackFigure, final Rectangle feedbackFigureAbsoluteBounds) {
		getFeedbackLayer().translateToRelative(feedbackFigureAbsoluteBounds);
		getFeedbackLayer().translateFromParent(feedbackFigureAbsoluteBounds);
		feedbackFigure.setBounds(feedbackFigureAbsoluteBounds);
		feedbackFigure.validate();
		addFeedback(feedbackFigure);
		feedbackFigures.add(feedbackFigure);
	}

	private void createConnectionFeedbackFigure(final ConnectionEditPart connectionEditPart) {
		addFeedbackFigure(
				new GhostImageFigure(connectionEditPart.getFigure(), feedbackAlpha,
						getLayer(LayerConstants.CONNECTION_LAYER).getBackgroundColor().getRGB()),
				getAbsoluteBounds(connectionEditPart.getFigure()));
	}

	private void createNodeFeedbackFigure(final GraphicalEditPart childEditPart) {
		addFeedbackFigure(new GhostImageFigure(childEditPart.getFigure(), feedbackAlpha, null),
				getAbsoluteBounds(childEditPart.getFigure()));
	}

	private static Rectangle getAbsoluteBounds(final IFigure figure) {
		final Rectangle bounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(bounds);
		return bounds;
	}

}