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
import java.util.Collection;
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
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.GraphicalEditPart;

public class ExpandedInterfacePositionMap {

	private final SubAppForFBNetworkEditPart ep;
	public Map<IFigure, Integer> inputPositions = null;
	public Map<IFigure, Integer> outputPositions = null;
	public Map<IFigure, Integer> directPositions = null;

	private int inputUnconnectedStart = Integer.MAX_VALUE;
	private int outputUnconnectedStart = Integer.MAX_VALUE;
	private int inputDirectEnd = Integer.MAX_VALUE;
	private int outputDirectEnd = Integer.MAX_VALUE;

	public ExpandedInterfacePositionMap(final SubAppForFBNetworkEditPart ep) {
		this.ep = ep;
	}

	public int getInputUnconnectedStart() {
		return inputUnconnectedStart;
	}

	public int getOutputUnconnectedStart() {
		return outputUnconnectedStart;
	}

	public void calculate() {
		// @formatter:off
		final var interfaceMap = ep.getChildren().stream()
			.filter(InterfaceEditPart.class::isInstance)
			.map(InterfaceEditPart.class::cast)
			.collect(Collectors.groupingBy(InterfaceEditPart::isInput));
		// @formatter:on

		final var inputList = interfaceMap.getOrDefault(Boolean.TRUE, new ArrayList<>());
		final var outputList = interfaceMap.getOrDefault(Boolean.FALSE, new ArrayList<>());

		directPositions = calculateDirectPositions(inputList);

		final var inputMap = calculateInput(inputList);
		resolveCollisions(inputMap);
		inputUnconnectedStart = calculateUnconnectedStartPositions(inputMap);
		if (inputUnconnectedStart == Integer.MAX_VALUE && !directPositions.isEmpty()) {
			inputUnconnectedStart = inputDirectEnd;
		}
		inputPositions = inputMap;

		final var outputMap = calculateOutput(outputList);
		resolveCollisions(outputMap);
		outputUnconnectedStart = calculateUnconnectedStartPositions(outputMap);
		if (outputUnconnectedStart == Integer.MAX_VALUE && !directPositions.isEmpty()) {
			outputUnconnectedStart = outputDirectEnd;
		}
		outputPositions = outputMap;
	}

	/**
	 * Calculate direct pin positions. Input pins are stacked whereas their
	 * corresponding output pins are placed in a way that the resulting connection
	 * is straight. An exception to this is where a multi-pin on the output side
	 * would be drawn beyond the client area, where the connection cannot be drawn
	 * straight.
	 */
	private Map<IFigure, Integer> calculateDirectPositions(final List<InterfaceEditPart> inputList) {
		// @formatter:off
		final var connections = inputList
				.stream()
				.map(ie -> (List<ConnectionEditPart>) ie.getSourceConnections())
				.flatMap(Collection::stream)
				.filter(ExpandedInterfacePositionMap::isSkipConnection)
				.collect(Collectors.toList());
		// @formatter:on

		final var inputMap = new HashMap<IFigure, Integer>();
		final var outputMap = new HashMap<IFigure, Integer>();
		final var clientAreaY = (inputList.isEmpty()) ? 0 : inputList.get(0).getFigure().getParent().getBounds().y;
		int inputY = clientAreaY;

		for (final var conn : connections) {
			final var inputFigure = ((GraphicalEditPart) conn.getSource()).getFigure();
			final var outputFigure = ((GraphicalEditPart) conn.getTarget()).getFigure();
			if (!inputMap.containsKey(inputFigure)) {
				inputMap.put(inputFigure, Integer.valueOf(inputY));
				inputY += inputFigure.getBounds().height + 1; // add spacing to account for multi-pin border
			}
			if (!outputMap.containsKey(outputFigure)) {
				final int connStart = inputMap.get(inputFigure).intValue() + (inputFigure.getBounds().height / 2);
				final int outputY = connStart - (outputFigure.getBounds().height / 2);
				outputMap.put(outputFigure, Integer.valueOf(Math.max(outputY, clientAreaY)));
			}
		}

		resolveCollisions(outputMap);
		inputDirectEnd = inputY;
		final var max = outputMap.entrySet().stream()
				.max((e1, e2) -> Integer.compare(e1.getValue().intValue(), e2.getValue().intValue()));
		if (max.isPresent()) {
			outputDirectEnd = max.get().getValue().intValue() + max.get().getKey().getBounds().height;
		} else {
			outputDirectEnd = clientAreaY;
		}
		inputMap.putAll(outputMap);
		return inputMap;
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
			final var max = connections.stream()
					.min((conn1, conn2) -> Integer.compare(
							((ConnectionEditPart) conn1).getConnectionFigure().getEnd().y,
							((ConnectionEditPart) conn2).getConnectionFigure().getEnd().y));
			if (max.isPresent()) {
				final ConnectionEditPart connEp = (ConnectionEditPart) max.get();
				final Point end = connEp.getConnectionFigure().getEnd();
				final int pinHeight = ((GraphicalEditPart) max.get().getSource()).getFigure().getBounds().height;
				final int y = end.y - (pinHeight / 2);

				if (!isSkipConnection(connEp)) {
					map.put(ie.getFigure(), Integer.valueOf(Math.max(y, inputDirectEnd)));
				}
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
			final var max = connections.stream()
					.min((conn1, conn2) -> Integer.compare(
							((ConnectionEditPart) conn1).getConnectionFigure().getStart().y,
							((ConnectionEditPart) conn2).getConnectionFigure().getStart().y));
			if (max.isPresent()) {
				final ConnectionEditPart connEp = (ConnectionEditPart) max.get();
				final Point start = connEp.getConnectionFigure().getStart();
				final int pinHeight = ((GraphicalEditPart) max.get().getTarget()).getFigure().getBounds().height;
				final int y = start.y - (pinHeight / 2);

				if (!isSkipConnection(connEp)) {
					map.put(ie.getFigure(), Integer.valueOf(Math.max(y, outputDirectEnd)));
				}
			} else {
				map.put(ie.getFigure(), Integer.valueOf(Integer.MAX_VALUE));
			}
		}
		return map;
	}

	private static boolean isSkipConnection(final ConnectionEditPart ep) {
		final var sourceModel = (IInterfaceElement) ep.getSource().getModel();
		final var targetModel = (IInterfaceElement) ep.getTarget().getModel();
		return sourceModel.getFBNetworkElement() == targetModel.getFBNetworkElement();
	}

	private static void resolveCollisions(final Map<IFigure, Integer> positions) {
		final var entryList = new ArrayList<>(positions.entrySet());
		entryList.sort(Comparator.comparing(Map.Entry::getValue));

		for (int i = 1; i < entryList.size(); i++) {
			final var prevEntry = entryList.get(i - 1);
			final var currEntry = entryList.get(i);
			final int prevHeight = prevEntry.getKey().getBounds().height;
			final int prev = prevEntry.getValue().intValue();
			final int curr = currEntry.getValue().intValue();

			// reached the end of connected pins
			if (curr == Integer.MAX_VALUE) {
				break;
			}

			if (prev + prevHeight > curr) {
				// move curr below prev
				final int newPos = prev + prevHeight;
				positions.put(currEntry.getKey(), Integer.valueOf(newPos));
			}
		}
	}

}
