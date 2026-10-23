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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;

public class ECStateConnectionAnchor extends AbstractConnectionAnchor {

	private final ECState state;
	private final ECTransition transition;
	private final boolean sourceEnd;

	public ECStateConnectionAnchor(final ECStateFigure owner, final ECState state, final ECTransition transition,
			final boolean sourceEnd) {
		super(owner);
		this.state = state;
		this.transition = transition;
		this.sourceEnd = sourceEnd;
	}

	private record Endpoint(ECTransition transition, boolean sourceEnd) {
	}

	@Override
	public ECStateFigure getOwner() {
		return (ECStateFigure) super.getOwner();
	}

	ECTransition getTransition() {
		return transition;
	}

	@Override
	public Point getReferencePoint() {
		if (transition != null && transition.getPosition() != null) {
			return bendPoint(transition);
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

		return applySpacing(nameBounds, edgeOf(transition, sourceEnd, ref, nameBounds), ref);
	}

	EdgeDirection getEdgeForReference(final Point reference) {
		final Rectangle nameBounds = getOwner().getNameLabel().getBounds().getCopy();
		getOwner().getNameLabel().translateToAbsolute(nameBounds);
		return edgeOf(transition, sourceEnd, reference, nameBounds);
	}

	private EdgeDirection edgeOf(final ECTransition ecTransition, final boolean isSourceEnd, final Point reference,
			final Rectangle nameBounds) {
		if (isSelfTransition(ecTransition) && ecTransition.getPosition() != null) {
			return EdgeDirection.selfLoopEdge(bendPoint(ecTransition), nameBounds, isSourceEnd);
		}
		return EdgeDirection.of(reference, nameBounds, state);
	}

	private Point applySpacing(final Rectangle bounds, final EdgeDirection edge, final Point reference) {
		final List<Endpoint> ordered = new ArrayList<>(endpointsOnEdge(edge, bounds, reference));
		final Endpoint current = new Endpoint(transition, sourceEnd);

		if (transition != null && !ordered.contains(current)) {
			ordered.add(current);
			ordered.sort(comparator(edge, reference));
		}

		final int index = transition == null ? 0 : Math.max(0, ordered.indexOf(current));
		final int count = Math.max(1, ordered.size());

		return switch (edge) {
		case TOP -> new Point(interpolatedCoordinate(bounds.x, bounds.width, index, count), bounds.y);
		case BOTTOM ->
			new Point(interpolatedCoordinate(bounds.x, bounds.width, index, count), bounds.y + bounds.height);
		case LEFT -> new Point(bounds.x, interpolatedCoordinate(bounds.y, bounds.height, index, count));
		case RIGHT -> new Point(bounds.x + bounds.width, interpolatedCoordinate(bounds.y, bounds.height, index, count));
		};
	}

	private static int interpolatedCoordinate(final int start, final int size, final int index, final int count) {
		if (size <= 0) {
			return start;
		}

		final int offset = (int) Math.round((index + 1.0) * size / (count + 1.0));
		return Math.max(start, Math.min(start + size, start + offset));
	}

	private List<Endpoint> endpointsOnEdge(final EdgeDirection edge, final Rectangle nameBounds,
			final Point reference) {
		if (state == null) {
			return List.of();
		}

		return Stream
				.concat(state.getOutTransitions().stream().map(t -> new Endpoint(t, true)),
						state.getInTransitions().stream().map(t -> new Endpoint(t, false)))
				.filter(e -> e.transition() != null).filter(e -> e.transition().getPosition() != null).filter(e -> {
					final EdgeDirection effectiveEdge = ECCTransitionRouter.getEffectiveEdge(e.transition(),
							e.sourceEnd());

					if (effectiveEdge != null) {
						return effectiveEdge == edge;
					}

					return edgeOf(e.transition(), e.sourceEnd(), bendPoint(e.transition()), nameBounds) == edge;
				}).distinct().sorted(comparator(edge, reference)).collect(Collectors.toCollection(ArrayList::new));
	}

	@SuppressWarnings("boxing")
	private Comparator<Endpoint> comparator(final EdgeDirection edge, final Point reference) {
		final boolean horizontal = (edge == EdgeDirection.TOP) || (edge == EdgeDirection.BOTTOM);

		return Comparator.comparingInt((final Endpoint e) -> {
			if (reference != null && transition != null && e.transition() == transition) {
				return horizontal ? reference.x : reference.y;
			}

			final Integer effective = ECCTransitionRouter.getEffectiveCoordinate(e.transition(), horizontal);

			if (effective != null) {
				return effective;
			}

			return horizontal ? bendPoint(e.transition()).x : bendPoint(e.transition()).y;
		}).thenComparing(Endpoint::sourceEnd);
	}

	private static boolean isSelfTransition(final ECTransition ecTransition) {
		return ecTransition != null && ecTransition.getSource() != null
				&& ecTransition.getSource() == ecTransition.getDestination();
	}

	private Point bendPoint(final ECTransition ecTransition) {
		final Point p = CoordinateConverter.INSTANCE.toScreenPoint(ecTransition.getPosition());
		getOwner().translateToAbsolute(p);
		return p;
	}
}
