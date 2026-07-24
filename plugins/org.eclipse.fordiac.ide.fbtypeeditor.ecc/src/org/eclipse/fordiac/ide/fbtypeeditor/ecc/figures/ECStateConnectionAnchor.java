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

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;

public class ECStateConnectionAnchor extends AbstractConnectionAnchor {

	private record EdgeSlot(ECTransition transition, boolean source) {
	}

	private final ECState state;
	private final ECTransition transition;
	private final boolean isSource;

	public ECStateConnectionAnchor(final ECStateFigure owner, final ECState state, final ECTransition transition,
			final boolean isSource) {
		super(owner);
		this.state = state;
		this.transition = transition;
		this.isSource = isSource;
	}

	@Override
	public ECStateFigure getOwner() {
		return (ECStateFigure) super.getOwner();
	}

	@Override
	public Point getReferencePoint() {
		if (transition != null && transition.getPosition() != null) {
			final Point p = transition.getPosition().toScreenPoint();
			getOwner().translateToAbsolute(p);
			return p;
		}
		final Rectangle bounds = getOwner().getBounds().getCopy();
		getOwner().translateToAbsolute(bounds);
		return bounds.getCenter();
	}

	@Override
	public Point getLocation(final Point reference) {
		final Rectangle nameBounds = getOwner().getNameLabel().getBounds().getCopy();
		getOwner().getNameLabel().translateToAbsolute(nameBounds);
		final Point ref = (reference != null) ? reference : nameBounds.getCenter();
		final EdgeDirection edge = EdgeDirection.of(ref, nameBounds, state);
		return applySpacing(nameBounds, edge, nameBounds);
	}

	private Point applySpacing(final Rectangle bounds, final EdgeDirection edge, final Rectangle nameBounds) {
		final List<EdgeSlot> slots = getSlotsOnEdge(edge, nameBounds);
		final int index = indexOf(slots);
		final int count = Math.max(1, slots.size());
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

	private List<EdgeSlot> getSlotsOnEdge(final EdgeDirection edge, final Rectangle nameBounds) {
		if (state == null) {
			return List.of();
		}
		final Stream<EdgeSlot> outgoing = state.getOutTransitions().stream().map(t -> new EdgeSlot(t, true));
		final Stream<EdgeSlot> incoming = state.getInTransitions().stream().map(t -> new EdgeSlot(t, false));
		return Stream.concat(outgoing, incoming).filter(s -> s.transition().getPosition() != null).filter(s -> {
			final Point p = s.transition().getPosition().toScreenPoint();
			getOwner().translateToAbsolute(p);
			return EdgeDirection.of(p, nameBounds, state) == edge;
		}).sorted((s1, s2) -> {
			final Point p1 = s1.transition().getPosition().toScreenPoint();
			getOwner().translateToAbsolute(p1);
			final Point p2 = s2.transition().getPosition().toScreenPoint();
			getOwner().translateToAbsolute(p2);
			final int positionalOrder = (edge == EdgeDirection.TOP || edge == EdgeDirection.BOTTOM)
					? Integer.compare(p1.x, p2.x)
					: Integer.compare(p1.y, p2.y);
			if (positionalOrder != 0) {
				return positionalOrder;
			}
			return Boolean.compare(!s1.source(), !s2.source());
		}).toList();
	}

	private int indexOf(final List<EdgeSlot> slots) {
		if (transition == null) {
			return 0;
		}
		for (int i = 0; i < slots.size(); i++) {
			final EdgeSlot slot = slots.get(i);
			if (slot.transition() == transition && slot.source() == isSource) {
				return i;
			}
		}
		return 0;
	}
}