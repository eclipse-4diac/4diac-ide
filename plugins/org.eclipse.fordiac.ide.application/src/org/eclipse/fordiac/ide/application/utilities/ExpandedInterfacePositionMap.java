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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.application.editparts.UntypedSubAppInterfaceElementEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.preferences.GefPreferenceConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;

public class ExpandedInterfacePositionMap {

	private static final String PREFERENCE_STORE = "org.eclipse.fordiac.ide.gef"; //$NON-NLS-1$

	private final SubAppForFBNetworkEditPart ep;
	private Rectangle clientArea = null;
	public Map<IFigure, Integer> inputPositions = null;
	public Map<IFigure, Integer> outputPositions = null;
	public Map<IFigure, Integer> directPositions = null;
	public Map<IFigure, InterfaceEditPart> editPartMap = new HashMap<>();
	public Map<IFigure, Integer> sizes = null;

	private int inputUnconnectedStart = Integer.MAX_VALUE;
	private int outputUnconnectedStart = Integer.MAX_VALUE;
	private int inputDirectEnd;
	private int outputDirectEnd;
	private int inputDirectEndWithoutEvents;
	private int outputDirectEndWithoutEvents;

	public ExpandedInterfacePositionMap(final SubAppForFBNetworkEditPart ep) {
		this.ep = ep;
	}

	public int getInputUnconnectedStart() {
		return inputUnconnectedStart;
	}

	public int getOutputUnconnectedStart() {
		return outputUnconnectedStart;
	}

	public void refreshParent() {
		ep.getChildren().stream().filter(InterfaceEditPart.class::isInstance).forEach(EditPart::refresh);
	}

	public int getInputDirectEnd() {
		return inputDirectEnd;
	}

	public int getOutputDirectEnd() {
		return outputDirectEnd;
	}

	public int getInputDirectEndWithoutEvents() {
		return inputDirectEndWithoutEvents;
	}

	public int getOutputDirectEndWithoutEvents() {
		return outputDirectEndWithoutEvents;
	}

	public void calculate() {
		editPartMap.clear();

		// @formatter:off
		final var interfaceMap = ep.getChildren().stream()
			.filter(InterfaceEditPart.class::isInstance)
			.map(InterfaceEditPart.class::cast)
			.collect(Collectors.groupingBy(InterfaceEditPart::isInput));
		// @formatter:on

		var inputList = interfaceMap.getOrDefault(Boolean.TRUE, new ArrayList<>());
		var outputList = interfaceMap.getOrDefault(Boolean.FALSE, new ArrayList<>());

		clientArea = getClientArea(inputList, outputList);
		if (clientArea == null) {
			return;
		}

		sizes = getPinSizes(inputList, outputList);

		final boolean directFlag = Platform.getPreferencesService().getBoolean(PREFERENCE_STORE,
				GefPreferenceConstants.EXPANDED_INTERFACE_OLD_DIRECT_BEHAVIOUR, false, null);
		if (directFlag) {
			directPositions = calculateDirectPositionsStack(inputList, outputList);
		} else {
			directPositions = calculateDirectPositions(inputList);
		}

		final var inputScrollPane = (!inputList.isEmpty())
				? (ScrollPane) inputList.get(0).getFigure().getParent().getParent().getParent()
				: null;
		final var outputScrollPane = (!outputList.isEmpty())
				? (ScrollPane) outputList.get(0).getFigure().getParent().getParent().getParent()
				: null;

		inputList = inputList.stream().filter(ie -> !directPositions.containsKey(ie.getFigure())).toList();
		outputList = outputList.stream().filter(ie -> !directPositions.containsKey(ie.getFigure())).toList();

		final var inputMap = calculateInput(inputList);
		final var outputMap = calculateOutput(outputList);

		resolveCollisions(inputMap);
		resolveCollisions(outputMap);

		inputUnconnectedStart = calculateUnconnectedStartPositions(inputMap);
		outputUnconnectedStart = calculateUnconnectedStartPositions(outputMap);

		if (inputUnconnectedStart == Integer.MAX_VALUE && !directPositions.isEmpty()) {
			inputUnconnectedStart = inputDirectEnd;
		}
		if (outputUnconnectedStart == Integer.MAX_VALUE && !directPositions.isEmpty()) {
			outputUnconnectedStart = outputDirectEnd;
		}

		inputPositions = inputMap;
		outputPositions = outputMap;

		stackToFit(clientArea, inputPositions, true);
		stackToFit(clientArea, outputPositions, false);

		applyScrollOffset(inputScrollPane, outputScrollPane);
	}

	private void applyScrollOffset(final ScrollPane inputScrollPane, final ScrollPane outputScrollPane) {
		final int inputScrollValue = (inputScrollPane != null) ? inputScrollPane.getVerticalScrollBar().getValue() : 0;
		final int outputScrollValue = (outputScrollPane != null) ? outputScrollPane.getVerticalScrollBar().getValue()
				: 0;

		if (inputScrollValue != 0) {
			inputDirectEnd -= inputScrollValue;
			inputUnconnectedStart -= inputScrollValue;
			for (final var entry : inputPositions.entrySet()) {
				inputPositions.put(entry.getKey(), Integer.valueOf(entry.getValue().intValue() - inputScrollValue));
			}
		}

		if (outputScrollValue != 0) {
			outputDirectEnd -= outputScrollValue;
			outputUnconnectedStart -= outputScrollValue;
			for (final var entry : outputPositions.entrySet()) {
				outputPositions.put(entry.getKey(), Integer.valueOf(entry.getValue().intValue() - outputScrollValue));
			}
		}

		for (final var entry : directPositions.entrySet()) {
			if (editPartMap.get(entry.getKey()).isInput()) {
				directPositions.put(entry.getKey(), Integer.valueOf(entry.getValue().intValue() - inputScrollValue));
			} else {
				directPositions.put(entry.getKey(), Integer.valueOf(entry.getValue().intValue() - outputScrollValue));
			}
		}
	}

	private Map<IFigure, Integer> calculateDirectPositionsStack(final List<InterfaceEditPart> inputList,
			final List<InterfaceEditPart> outputList) {
		// @formatter:off
		final var direct = inputList
				.stream()
				.map(InterfaceEditPart::getSourceConnections)
				.flatMap(Collection::stream)
				.map(ConnectionEditPart.class::cast)
				.filter(ExpandedInterfacePositionMap::isSkipConnection)
				.toList();
		// @formatter:on

		final var pos = new HashMap<IFigure, Integer>();

		// cannot use a set as we need the order to stay intact
		final var input = new ArrayList<InterfaceEditPart>();
		final var output = new ArrayList<InterfaceEditPart>();

		for (final var conn : direct) {
			final var source = (InterfaceEditPart) conn.getSource();
			final var target = (InterfaceEditPart) conn.getTarget();
			if (!input.contains(source)) {
				input.add(source);
			}
			if (!output.contains(source)) {
				output.add(target);
			}
		}

		final boolean eventFlag = Platform.getPreferencesService().getBoolean(PREFERENCE_STORE,
				GefPreferenceConstants.EXPANDED_INTERFACE_EVENTS_TOP, false, null);

		int y = clientArea.top();
		for (final var pin : input) {
			y = applySize(pos, y, pin);
		}
		inputDirectEndWithoutEvents = y;
		if (eventFlag) {
			for (final var pin : getEvents(inputList, input)) {
				y = applySize(pos, y, pin);
			}
		}
		inputDirectEnd = y;

		y = clientArea.top();
		for (final var pin : output) {
			y = applySize(pos, y, pin);
		}
		outputDirectEndWithoutEvents = y;
		if (eventFlag) {
			for (final var pin : getEvents(outputList, output)) {
				y = applySize(pos, y, pin);
			}
		}
		outputDirectEnd = y;

		return pos;
	}

	private static List<InterfaceEditPart> getEvents(final List<InterfaceEditPart> eventPins,
			final List<InterfaceEditPart> directPins) {
		return eventPins.stream().filter(ie -> ie.getModel() instanceof Event)
				.filter(Predicate.not(directPins::contains)).toList();
	}

	private int applySize(final Map<IFigure, Integer> pos, final int y, final InterfaceEditPart pin) {
		pos.put(pin.getFigure(), Integer.valueOf(y));
		return y + sizes.get(pin.getFigure()).intValue();
	}

	private Map<IFigure, Integer> getPinSizes(final List<InterfaceEditPart> inputList,
			final List<InterfaceEditPart> outputList) {
		final var sizes = new HashMap<IFigure, Integer>();
		for (final var pin : inputList) {
			prozessInterfacEditPartSize(sizes, pin);
		}
		for (final var pin : outputList) {
			prozessInterfacEditPartSize(sizes, pin);
		}
		return sizes;
	}

	protected void prozessInterfacEditPartSize(final HashMap<IFigure, Integer> sizes, final InterfaceEditPart pin) {
		editPartMap.put(pin.getFigure(), pin);
		if (pin instanceof final UntypedSubAppInterfaceElementEditPart subAppPinEP && subAppPinEP.isOverflow()) {
			subAppPinEP.setOverflow(false);
			subAppPinEP.refresh();
			sizes.put(subAppPinEP.getFigure(), Integer.valueOf(subAppPinEP.getUncollapsedFigureHeight()));
		} else {
			sizes.put(pin.getFigure(), Integer.valueOf(pin.getFigure().getBounds().height));
		}
	}

	private static Rectangle getClientArea(final List<InterfaceEditPart> inputList,
			final List<InterfaceEditPart> outputList) {
		if (!inputList.isEmpty()) {
			return inputList.get(0).getFigure().getParent().getBounds();
		}
		if (!outputList.isEmpty()) {
			return outputList.get(0).getFigure().getParent().getBounds();
		}
		return null;
	}

	private void stackToFit(final Rectangle clientArea, final Map<IFigure, Integer> positions, final boolean isInput) {
		if (positions.isEmpty()) {
			return;
		}

		final var entryList = positions.entrySet().stream().filter(e -> !directPositions.containsKey(e.getKey()))
				.collect(Collectors.toList());
		entryList.sort(Comparator.comparing(Map.Entry::getValue));

		final int bottom = clientArea.bottom() - 10; // add padding to ensure no pin is drawn out of bounds
		int i = entryList.size() - 1;
		int totalSize = 0;

		// add unconnected pins from the end
		for (; i >= 0; i--) {
			final var entry = entryList.get(i);
			if (entry.getValue().intValue() != Integer.MAX_VALUE) {
				break;
			}
			totalSize += sizes.get(entry.getKey()).intValue();
		}

		// all pins were unconnected
		if (i < 0) {
			return;
		}

		// all pins fit, no need to continue
		if ((isInput) ? inputUnconnectedStart + totalSize < bottom : outputUnconnectedStart + totalSize < bottom) {
			return;
		}

		int lastY = -1;

		// find out when stacking is enough and collapse pins in the process
		for (; i >= 0; i--) {
			final var fig = entryList.get(i).getKey();
			final var pinEp = editPartMap.get(fig);

			int size = 0;
			int collapsedSize = 0;
			if (pinEp instanceof final UntypedSubAppInterfaceElementEditPart untypeSubappPinEP) {
				untypeSubappPinEP.setOverflow(true);
				untypeSubappPinEP.refresh();
				size = untypeSubappPinEP.getCollapsedFigureHeight();
				collapsedSize = untypeSubappPinEP.getUncollapsedFigureHeight();
			} else {
				collapsedSize = size = pinEp.getFigure().getBounds().height;
			}

			sizes.put(fig, Integer.valueOf(size));
			totalSize += size;

			final int y = entryList.get(i).getValue().intValue();
			final int collapsedY = y + ((collapsedSize - size) / 2);
			if (totalSize + collapsedY < bottom) {
				lastY = collapsedY;
				break;
			}
		}

		int y;
		if (i == -1) {
			i = 0;
			y = isInput ? inputDirectEnd : outputDirectEnd;
		} else {
			y = lastY;
		}

		// do the actual stacking
		for (; i < entryList.size(); i++) {
			// reached unconnected pins again
			if (entryList.get(i).getValue().intValue() == Integer.MAX_VALUE) {
				break;
			}
			final var fig = entryList.get(i).getKey();
			positions.put(fig, Integer.valueOf(y));

			final Integer newSize = sizes.get(fig);
			y += (newSize != null) ? newSize.intValue() : fig.getBounds().height;
		}

		if (isInput) {
			inputUnconnectedStart = y;
		} else {
			outputUnconnectedStart = y;
		}
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
				.map(InterfaceEditPart::getSourceConnections)
				.flatMap(Collection::stream)
				.map(ConnectionEditPart.class::cast)
				.filter(ExpandedInterfacePositionMap::isSkipConnection)
				.toList();
		// @formatter:on

		final var inputMap = new HashMap<IFigure, Integer>();
		final var outputMap = new HashMap<IFigure, Integer>();
		int inputY = clientArea.top();

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
				outputMap.put(outputFigure, Integer.valueOf(Math.max(outputY, clientArea.top())));
			}
		}

		resolveCollisions(outputMap);
		inputDirectEnd = inputY;
		final var max = outputMap.entrySet().stream()
				.max((e1, e2) -> Integer.compare(e1.getValue().intValue(), e2.getValue().intValue()));
		if (max.isPresent()) {
			outputDirectEnd = max.get().getValue().intValue() + max.get().getKey().getBounds().height;
		} else {
			outputDirectEnd = clientArea.top();
		}

		inputMap.putAll(outputMap);
		return inputMap;
	}

	/**
	 * Calculates the start position for unconnected pins which is always below the
	 * connected pins.
	 */
	private int calculateUnconnectedStartPositions(final Map<IFigure, Integer> map) {
		final Optional<Entry<IFigure, Integer>> inputOptional = map.entrySet().stream()
				.filter(entry -> entry.getValue().intValue() != Integer.MAX_VALUE)
				.max((entry1, entry2) -> Integer.compare(entry1.getValue().intValue(), entry2.getValue().intValue()));
		if (inputOptional.isPresent()) {
			final var entry = inputOptional.get();
			return entry.getValue().intValue() + sizes.get(entry.getKey()).intValue();
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
				if (connEp.getSource() instanceof final GraphicalEditPart graphicalEditPart) {
					final Point end = connEp.getConnectionFigure().getEnd();

					final int y = end.y - (sizes.get(graphicalEditPart.getFigure()).intValue() / 2);

					if (!isSkipConnection(connEp)) {
						map.put(ie.getFigure(), Integer.valueOf(Math.max(y, inputDirectEnd)));
					}
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
				if (connEp.getTarget() instanceof final GraphicalEditPart graphicalEditPart) {
					final Point start = connEp.getConnectionFigure().getStart();
					final int y = start.y - (sizes.get(graphicalEditPart.getFigure()).intValue() / 2);

					if (!isSkipConnection(connEp)) {
						map.put(ie.getFigure(), Integer.valueOf(Math.max(y, outputDirectEnd)));
					}
				}
			} else {
				map.put(ie.getFigure(), Integer.valueOf(Integer.MAX_VALUE));
			}
		}
		return map;
	}

	private static boolean isSkipConnection(final ConnectionEditPart ep) {
		if (ep.getSource() != null && ep.getTarget() != null) {
			final var sourceModel = (IInterfaceElement) ep.getSource().getModel();
			final var targetModel = (IInterfaceElement) ep.getTarget().getModel();
			return sourceModel.eContainer().eContainer() instanceof final SubApp sourceSub
					&& targetModel.eContainer().eContainer() instanceof final SubApp targetSub
					&& sourceSub == targetSub;
		}
		return false;
	}

	private void resolveCollisions(final Map<IFigure, Integer> positions) {
		final var entryList = new ArrayList<>(positions.entrySet());
		entryList.sort(Comparator.comparing(Map.Entry::getValue));

		for (int i = 1; i < entryList.size(); i++) {
			final var prevEntry = entryList.get(i - 1);
			final var currEntry = entryList.get(i);
			final int prev = prevEntry.getValue().intValue();
			final int curr = currEntry.getValue().intValue();
			final int prevHeight = sizes.get(prevEntry.getKey()).intValue();

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
