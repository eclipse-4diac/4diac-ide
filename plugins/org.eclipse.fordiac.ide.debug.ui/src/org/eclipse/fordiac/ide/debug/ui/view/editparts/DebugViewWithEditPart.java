/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.debug.ui.view.editparts;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.debug.ui.view.figure.FBDebugViewFigure;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.WithAnchor;
import org.eclipse.fordiac.ide.fbtypeeditor.editparts.WithEditPart;

public class DebugViewWithEditPart extends WithEditPart {

	@Override
	protected void activateFigure() {
		final IFigure targetFigure = getTargetFigure();
		if (targetFigure != null) {
			targetFigure.add(getFigure());
		} else {
			super.activateFigure();
		}
	}

	@Override
	protected void deactivateFigure() {
		final IFigure targetFigure = getTargetFigure();
		if (targetFigure != null) {
			targetFigure.remove(getFigure());
			getConnectionFigure().setSourceAnchor(null);
			getConnectionFigure().setTargetAnchor(null);
		} else {
			super.deactivateFigure();
		}

	}

	@Override
	protected void createEditPolicies() {
		// we don't want any edit policies for the debug view withs
	}

	private IFigure getTargetFigure() {
		final FBDebugViewFigure debugViewFigure = getDebugViewFigure();
		if (debugViewFigure != null) {
			return (isInput()) ? debugViewFigure.getInputWiths() : debugViewFigure.getOutputWiths();
		}
		return null;
	}

	private FBDebugViewFigure getDebugViewFigure() {
		if (getRoot().getChildren().getFirst() instanceof final FBDebugViewRootEditPart debugViewEP) {
			return debugViewEP.getFigure();
		}
		return null;
	}

	@Override
	protected ConnectionAnchor getSourceConnectionAnchor() {
		final ConnectionAnchor sourceConnectionAnchor = super.getSourceConnectionAnchor();
		if (sourceConnectionAnchor instanceof final WithAnchor withAnchor) {
			if (isInput()) {
				return createInputAnchor(withAnchor);
			}
			return createOutputAnchor(withAnchor);
		}
		return sourceConnectionAnchor;
	}

	@Override
	protected ConnectionAnchor getTargetConnectionAnchor() {
		final ConnectionAnchor targetConnectionAnchor = super.getTargetConnectionAnchor();
		if (targetConnectionAnchor instanceof final WithAnchor withAnchor) {
			if (isInput()) {
				return createInputAnchor(withAnchor);
			}
			return createOutputAnchor(withAnchor);
		}
		return targetConnectionAnchor;
	}

	private ConnectionAnchor createInputAnchor(final WithAnchor anchor) {
		return new WithAnchor(anchor.getOwner(), anchor.getPos(), this) {
			@Override
			public Point getLocation(final Point reference) {
				final Rectangle r = Rectangle.SINGLETON;
				r.setBounds(getBox());
				r.translate(0, -1);
				r.resize(1, 1);
				final int leftX = getFigure().getParent().getBounds().getRight().x - (int) (WITH_DISTANCE * getPos());
				final int centerY = r.y + r.height / 2;
				return new Point(leftX, centerY);
			}
		};
	}

	private ConnectionAnchor createOutputAnchor(final WithAnchor anchor) {
		return new WithAnchor(anchor.getOwner(), anchor.getPos(), this) {
			@Override
			public Point getLocation(final Point reference) {
				final Rectangle r = Rectangle.SINGLETON;
				r.setBounds(getBox());
				r.translate(-1, -1);
				r.resize(1, 1);
				getOwner().translateToAbsolute(r);
				final int leftX = getFigure().getParent().getBounds().x + (int) (WITH_DISTANCE * getPos());
				final int centerY = r.y + r.height / 2;
				return new Point(leftX, centerY);
			}
		};
	}

}
