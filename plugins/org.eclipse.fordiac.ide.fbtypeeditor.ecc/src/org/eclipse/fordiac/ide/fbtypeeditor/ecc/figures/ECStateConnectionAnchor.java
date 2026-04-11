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
 *   Vikash Kumar - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;

public class ECStateConnectionAnchor extends AbstractConnectionAnchor {

	private enum Edge {
		TOP, BOTTOM, LEFT, RIGHT
	}

	private final ECState state;
	private final ECTransition transition;

	public ECStateConnectionAnchor(final IFigure owner, final ECState state, final ECTransition transition) {
		super(owner);
		this.state = state;
		this.transition = transition;
	}

	@Override
	public Point getReferencePoint() {
		final Rectangle bounds = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(bounds);
		return bounds.getCenter();
	}

	@Override
	public Point getLocation(final Point reference) {
		final Rectangle totalBounds = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(totalBounds);

		final Point center = totalBounds.getCenter();

		final Point ref = (reference != null) ? reference : center;

		final Edge edge = edgeForReference(center, ref);

		if (edge == Edge.RIGHT) {
			return applySpacing(totalBounds, new Point(totalBounds.x + totalBounds.width, totalBounds.getCenter().y),
					false, edge);
		}

		if (getOwner() instanceof ECStateFigure) {
			final Label nameLabel = ((ECStateFigure) getOwner()).getNameLabel();
			final Rectangle nameBounds = nameLabel.getBounds().getCopy();

			nameLabel.translateToAbsolute(nameBounds);

			return switch (edge) {
			case TOP -> applySpacing(nameBounds, nameBounds.getTop(), true, edge);
			case LEFT -> applySpacing(nameBounds, nameBounds.getLeft(), false, edge);
			case BOTTOM -> applySpacing(nameBounds, nameBounds.getBottom(), true, edge);
			default -> applySpacing(totalBounds, new Point(totalBounds.x + totalBounds.width, center.y), false, edge);
			};
		}

		return center;
	}

	private Edge edgeForReference(final Point center, final Point ref) {
		final int dx = ref.x - center.x;
		final int dy = ref.y - center.y;
		final boolean hasAction = (state != null) && !state.getECAction().isEmpty();

		if (dx >= 0 && dy > 0 && hasAction) {
			return Edge.RIGHT;
		}
		if (Math.abs(dy) > Math.abs(dx)) {
			return (dy >= 0) ? Edge.BOTTOM : Edge.TOP;
		}
		return (dx >= 0) ? Edge.RIGHT : Edge.LEFT;
	}

	private Point applySpacing(final Rectangle bounds, final Point base, final boolean verticalEdge, final Edge edge) {
		final List<ECTransition> ordered = getTransitionsOnEdgeOrdered(edge);
		final int index = indexOf(ordered, transition);
		final int count = Math.max(1, ordered.size());

		final Point p = base.getCopy();

		if (verticalEdge) {
			final int slotWidth = bounds.width / (count + 1);
			p.x = bounds.x + (index + 1) * slotWidth;
		} else {
			final int slotHeight = bounds.height / (count + 1);
			p.y = bounds.y + (index + 1) * slotHeight;
		}

		if (edge == Edge.RIGHT) {
			p.x = bounds.x + bounds.width;
		} else if (edge == Edge.LEFT) {
			p.x = bounds.x;
		} else if (edge == Edge.BOTTOM) {
			p.y = bounds.y + bounds.height;
		} else if (edge == Edge.TOP) {
			p.y = bounds.y;
		}

		return p;
	}

	private List<ECTransition> getTransitionsOnEdgeOrdered(final Edge edge) {
		final List<ECTransition> result = new ArrayList<>();
		if (state == null) {
			return result;
		}

		final List<ECTransition> list = (transition != null && transition.getSource() == state)
				? state.getOutTransitions()
				: state.getInTransitions();

		for (final ECTransition t : list) {
			if (edgeForTransition(t) == edge) {
				result.add(t);
			}
		}

		result.sort(Comparator.comparingDouble(this::angleForTransitionNormalized));
		return result;
	}

	private static int indexOf(final List<ECTransition> list, final ECTransition t) {
		if (t == null) {
			return 0;
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == t) {
				return i;
			}
		}
		return 0;
	}

	private Edge edgeForTransition(final ECTransition t) {
		if (t == null || state == null) {
			return Edge.RIGHT;
		}
		final ECState other = (t.getSource() == state) ? t.getDestination() : t.getSource();
		if (other == null) {
			return Edge.RIGHT;
		}

		final Rectangle bounds = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(bounds);
		final Point center = bounds.getCenter();

		final Point otherPos = other.getPosition().toScreenPoint();
		final int halfW = bounds.width / 2;
		final int halfH = bounds.height / 4; // ← 12 hata diya
		final Point otherCenter = new Point(otherPos.x + halfW, otherPos.y + halfH);

		final int dx = otherCenter.x - center.x;
		final int dy = otherCenter.y - center.y;
		final boolean hasAction = !state.getECAction().isEmpty();

		if (dx >= 0 && dy > 0 && hasAction) {
			return Edge.RIGHT;
		}
		if (Math.abs(dy) > Math.abs(dx)) {
			return (dy >= 0) ? Edge.BOTTOM : Edge.TOP;
		}
		return (dx >= 0) ? Edge.RIGHT : Edge.LEFT;
	}

	private double angleForTransitionNormalized(final ECTransition t) {
		if (t == null || state == null) {
			return 0.0;
		}
		final ECState other = (t.getSource() == state) ? t.getDestination() : t.getSource();
		if (other == null) {
			return 0.0;
		}

		final double dx = other.getPosition().getX() - state.getPosition().getX();
		final double dy = other.getPosition().getY() - state.getPosition().getY();

		double angle = Math.atan2(dy, dx);
		if (angle < 0) {
			angle += Math.PI * 2.0;
		}
		return angle;
	}
}
