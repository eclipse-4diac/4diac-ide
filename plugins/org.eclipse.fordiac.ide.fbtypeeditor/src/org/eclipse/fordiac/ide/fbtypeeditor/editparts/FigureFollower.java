/*******************************************************************************
 * Copyright (c) 2026 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.fbtypeeditor.editparts;

import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;

class FigureFollower {

	private IFigure referenceFigure;
	private final GraphicalEditPart follower;
	private final Object refModel;

	private final FigureListener pinMovedListener = this::updateYPos;

	public FigureFollower(final GraphicalEditPart follower, final Object refModel) {
		this.follower = follower;
		this.refModel = refModel;
		setRefFigure(findRefFigure());
	}

	public void unhookFromRefFigure() {
		if (referenceFigure != null) {
			referenceFigure.removeFigureListener(pinMovedListener);
			referenceFigure = null;
		}
	}

	public void refresh() {
		if (referenceFigure == null) {
			setRefFigure(findRefFigure());
		} else {
			updateYPos(referenceFigure);
		}
	}

	private final void setRefFigure(final IFigure refFigure) {
		referenceFigure = refFigure;
		if (referenceFigure != null) {
			referenceFigure.addFigureListener(pinMovedListener);
			updateYPos(referenceFigure);
		}
	}

	private IFigure findRefFigure() {
		for (final var entry : follower.getViewer().getEditPartRegistry().entrySet()) {
			if (entry.getValue().getModel() == refModel && entry.getValue() instanceof final GraphicalEditPart gep) {
				return gep.getFigure();
			}
		}
		return null;
	}

	private void updateYPos(final IFigure source) {
		final IFigure parent = follower.getFigure().getParent();
		if (parent == null) {
			return;
		}

		final Rectangle srcBounds = source.getBounds();
		final int newY = srcBounds.y + (srcBounds.height - follower.getFigure().getPreferredSize().height) / 2;

		parent.setConstraint(follower.getFigure(), Integer.valueOf(newY));
		parent.revalidate();
	}

}
