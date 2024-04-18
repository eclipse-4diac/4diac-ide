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
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.GraphicalEditPart;

public class ExpandedInterfacePositionMap {

	final private SubAppForFBNetworkEditPart ep;
	public Map<IFigure, Integer> positions = null;
	public Map<IFigure, Integer> distances = new HashMap<>();

	private int inputUnconnectedStart = Integer.MAX_VALUE;
	private int outputUnconnectedStart = Integer.MAX_VALUE;

	public ExpandedInterfacePositionMap(final SubAppForFBNetworkEditPart ep) {
		this.ep = ep;
	}

	public int getInputUnconnectedStart() {
		return inputUnconnectedStart;
	}

	public int getOutputUnconnectedStart() {
		return outputUnconnectedStart;
	}

	public void calculate(final boolean isInput) {
		// positions map is set further down and does not have to be cleared
		distances.clear();

		// @formatter:off
		final var interfaceMap = ep.getChildren().stream()
			.filter(InterfaceEditPart.class::isInstance)
			.map(InterfaceEditPart.class::cast)
			.collect(Collectors.groupingBy(InterfaceEditPart::isInput));
		// @formatter:on

		if (isInput) {
			final var inputList = interfaceMap.getOrDefault(Boolean.TRUE, new ArrayList<>());
			final var inputMap = calculateInput(inputList);
			resolveCollisions(inputMap);
			inputUnconnectedStart = calculateUnconnectedStartPositions(inputMap);
			this.positions = inputMap;
		} else {
			final var outputList = interfaceMap.getOrDefault(Boolean.FALSE, new ArrayList<>());
			final var outputMap = calculateOutput(outputList);
			resolveCollisions(outputMap);
			outputUnconnectedStart = calculateUnconnectedStartPositions(outputMap);
			this.positions = outputMap;
		}
	}

	/**
	 * Calculates the start position for unconnected pins which is always below the
	 * connected pins.
	 */
	private static int calculateUnconnectedStartPositions(final Map<IFigure, Integer> map) {
		final Optional<Entry<IFigure, Integer>> inputOptional = map.entrySet().stream()
				.filter(entry -> entry.getValue().intValue() != Integer.MAX_VALUE)
				.max((entry1, entry2) -> Integer.compare(entry1.getValue().intValue(), entry2.getValue().intValue()));
		if (inputOptional.isPresent()) {
			final var entry = inputOptional.get();
			return entry.getValue().intValue() + entry.getKey().getBounds().height;
		}
		return Integer.MAX_VALUE;
	}

	private Map<IFigure, Integer> calculateInput(final List<InterfaceEditPart> inputList) {
		final Map<IFigure, Integer> map = new HashMap<>();
		for (final var ie : inputList) {
			final var connections = ie.getSourceConnections();
			final Optional<? extends ConnectionEditPart> max = connections.stream()
					.filter(ConnectionEditPart.class::isInstance).map(obj -> (ConnectionEditPart) obj)
					.min((conn1, conn2) -> Integer.compare(conn1.getConnectionFigure().getEnd().y,
							conn2.getConnectionFigure().getEnd().y));
			if (max.isPresent()) {
				final Point start = max.get().getConnectionFigure().getStart();
				final Point end = max.get().getConnectionFigure().getEnd();
				final int distance = Math.abs(end.x - start.x);
				distances.put(ie.getFigure(), Integer.valueOf(distance));

				final int pinHeight = ((GraphicalEditPart) max.get().getSource()).getFigure().getBounds().height;
				final int y = end.y - (pinHeight / 2);
				map.put(ie.getFigure(), Integer.valueOf(y));
			} else {
				map.put(ie.getFigure(), Integer.valueOf(Integer.MAX_VALUE));
			}
		}
		return map;
	}

	private Map<IFigure, Integer> calculateOutput(final List<InterfaceEditPart> outputList) {
		final Map<IFigure, Integer> map = new HashMap<>();
		for (final var ie : outputList) {
			final var connections = ie.getTargetConnections();
			final Optional<ConnectionEditPart> max = connections.stream()
					.filter(ConnectionEditPart.class::isInstance).map(obj -> (ConnectionEditPart) obj)
					.min((conn1, conn2) -> Integer.compare(conn1.getConnectionFigure().getStart().y,
							conn2.getConnectionFigure().getStart().y));
			if (max.isPresent()) {
				final Point start = max.get().getConnectionFigure().getStart();
				final Point end = max.get().getConnectionFigure().getEnd();
				final int distance = Math.abs(end.x - start.x);
				distances.put(ie.getFigure(), Integer.valueOf(distance));

				final int pinHeight = ((GraphicalEditPart) max.get().getTarget()).getFigure().getBounds().height;
				final int y = start.y - (pinHeight / 2);
				map.put(ie.getFigure(), Integer.valueOf(y));
			} else {
				map.put(ie.getFigure(), Integer.valueOf(Integer.MAX_VALUE));
			}
		}
		return map;
	}

	/**
	 * Resolves collisions of pins (according to their calculated y positions) by
	 * checking which pin is closest to interface bar and putting all other
	 * candidates below according to the height of their predecessor.
	 */
	public void resolveCollisions(final Map<IFigure, Integer> positions) {
		final var entryList = new ArrayList<>(positions.entrySet());
		entryList.sort(Comparator.comparing(Map.Entry::getValue));

		for (int i = 1; i < entryList.size(); i++) {
			final var prevEntry = entryList.get(i - 1);
			final var currEntry = entryList.get(i);
			final int prevHeight = prevEntry.getKey().getBounds().height;
			final int currHeight = currEntry.getKey().getBounds().height;
			final int prev = prevEntry.getValue().intValue();
			final int curr = currEntry.getValue().intValue();

			// reached the end of connected pins
			if (curr == Integer.MAX_VALUE) {
				break;
			}

			if (prev + prevHeight > curr) {
				// collision detected
				// adjust position for element that has the farthest to the interface because it
				// is most likely that it would go through another block
				final Integer prevDistance = distances.get(prevEntry.getKey());
				final Integer currDistance = distances.get(currEntry.getKey());

				assert prevDistance != null;
				assert currDistance != null;

				if (prevDistance.intValue() > currDistance.intValue()) {
					// move prev below curr
					final int newPos = curr + currHeight;
					positions.put(prevEntry.getKey(), Integer.valueOf(newPos));
				} else {
					// move curr below prev
					final int newPos = prev + prevHeight;
					positions.put(currEntry.getKey(), Integer.valueOf(newPos));
				}
			}
		}
	}

}
