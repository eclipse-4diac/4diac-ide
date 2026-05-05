/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vikash Kumar Sinha - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.AbstractConnectionAnchor;
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

	public ECStateConnectionAnchor(final ECStateFigure owner, final ECState state, final ECTransition transition) {
		super(owner);
		this.state = state;
		this.transition = transition;
	}

	@Override
	public ECStateFigure getOwner() {
		return (ECStateFigure) super.getOwner();
	}

	@Override
	public Point getReferencePoint() {
		if (transition != null && transition.getPosition() != null) {
			return transition.getPosition().toScreenPoint();
		}
		final Rectangle bounds = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(bounds);
		return bounds.getCenter();
	}

	private Edge edgeForReference(final Point center, final Point ref) {
		final int dx = ref.x - center.x;
		final int dy = ref.y - center.y;

		if (Math.abs(dy) > Math.abs(dx)) {
			return (dy >= 0) ? Edge.BOTTOM : Edge.TOP;
		}
		if (dx >= 0 && state != null && !state.getECAction().isEmpty()) {
			return (dy >= 0) ? Edge.BOTTOM : Edge.TOP;
		}

		return (dx >= 0) ? Edge.RIGHT : Edge.LEFT;
	}

	@Override
	public Point getLocation(final Point reference) {
		final Rectangle nameBounds = getOwner().getNameLabel().getBounds().getCopy();
		getOwner().getNameLabel().translateToAbsolute(nameBounds);

		final Point ref = (reference != null) ? reference : nameBounds.getCenter();
		final Edge edge = edgeForReference(nameBounds.getCenter(), ref);

		return applySpacing(nameBounds, edge);
	}

	private Point applySpacing(final Rectangle bounds, final Edge edge) {
		final List<ECTransition> ordered = getTransitionsOnEdgeOrdered(edge);
		final int index = indexOf(ordered, transition);
		final int count = Math.max(1, ordered.size());

		return switch (edge) {
		case TOP -> {
			final int slotWidth = bounds.width / (count + 1);
			yield new Point(bounds.x + (index + 1) * slotWidth, bounds.y);
		}
		case BOTTOM -> {
			final int slotWidth = bounds.width / (count + 1);
			yield new Point(bounds.x + (index + 1) * slotWidth, bounds.y + bounds.height);
		}
		case LEFT -> {
			final int slotHeight = bounds.height / (count + 1);
			yield new Point(bounds.x, bounds.y + (index + 1) * slotHeight);
		}
		case RIGHT -> {
			final int slotHeight = bounds.height / (count + 1);
			yield new Point(bounds.x + bounds.width, bounds.y + (index + 1) * slotHeight);
		}
		};
	}

	private List<ECTransition> getTransitionsOnEdgeOrdered(final Edge edge) {
		final List<ECTransition> result = new ArrayList<>();
		if (state == null) {
			return result;
		}

		final List<ECTransition> list = (transition != null && transition.getSource() == state)
				? state.getOutTransitions()
				: state.getInTransitions();

		final Rectangle nameBounds = getOwner().getNameLabel().getBounds().getCopy();
		getOwner().getNameLabel().translateToAbsolute(nameBounds);
		final Point center = nameBounds.getCenter();

		for (final ECTransition t : list) {

			if (t.getPosition() == null) {
				continue;
			}

			final Point bendPoint = t.getPosition().toScreenPoint();
			if (edgeForReference(center, bendPoint) == edge) {
				result.add(t);
			}
		}
		result.sort((t1, t2) -> {
			final Point p1 = t1.getPosition().toScreenPoint();
			final Point p2 = t2.getPosition().toScreenPoint();
			if (edge == Edge.TOP || edge == Edge.BOTTOM) {
				return Integer.compare(p1.x, p2.x);
			}
			return Integer.compare(p1.y, p2.y);
		});

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
}
