/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.replayer.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.DatapointsState;
import org.eclipse.fordiac.ide.fb.interpreter.mm.NetworkRuntimeState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;

/**
 * @brief Representation of the current state of the resource being interpreted.
 *
 *        This class translate the information in the NetworkRuntimeState
 *        from/to a DatapointsState which is used for the monitoring and replay
 *        debugging features.
 *
 *        It allows getting and setting the whole state of the NetworkRuntime
 *        state. It handles forced values and adds current state of BasicFBs to
 *        the state as well.
 */
public class ResourceState {

	private final NetworkRuntimeState networkRuntimeState;

	private final Map<String, String> forcedValues = new HashMap<>();
	private final EventCounter eventCounter;
	private final DatapointsState currentState = new DatapointsState();
	private static final String ECCName = ".!ECC";

	public ResourceState(final NetworkRuntimeState networkRuntimeState) {
		this.networkRuntimeState = networkRuntimeState;
		eventCounter = new EventCounter(
				networkRuntimeState.getEvents().values().stream().collect(java.util.stream.Collectors.toSet()));
		updateState();
	}

	public void eventTriggered(final Event event) {
		eventCounter.incrementEventCount(event);
	}

	public DatapointsState getCurrentState() {
		return currentState;
	}

	public void forceValue(final String watchPoint, final String value) {
		forcedValues.put(watchPoint, value);
	}

	public void clearForce(final String watchPoint) {
		forcedValues.remove(watchPoint);
	}

	public void applyForceValues() {
		if (forcedValues.isEmpty()) {
			return;
		}

		// store first all forced values which are output (source in a connection) so in
		// case the corresponding target is also force, we get the value from the target
		for (final var entry : forcedValues.entrySet()) {
			final var watchPoint = entry.getKey();
			final var valueToStore = entry.getValue();
			networkRuntimeState.getConnectionWithSource(watchPoint).forEach(value -> value.setValue(valueToStore));
		}

		for (final var entry : forcedValues.entrySet()) {
			final var watchPoint = entry.getKey();
			final var valueToStore = entry.getValue();
			final var destinationValue = networkRuntimeState.getConnectionsWithDestination(watchPoint);
			if (destinationValue != null) {
				destinationValue.setValue(valueToStore);
			}
		}

		for (final var forcedValue : forcedValues.entrySet()) {
			final var watchPoint = forcedValue.getKey();
			final var value = forcedValue.getValue();
			applyValue(watchPoint, value);
		}
		updateState();
	}

	private void applyValue(final String watchPoint, final String value) {
		if (networkRuntimeState.getDataValues().containsKey(watchPoint)) {
			networkRuntimeState.getDataValues().get(watchPoint).setValue(value);
		} else if (networkRuntimeState.getEvents().containsKey(watchPoint)) {
			eventCounter.setEventCount(networkRuntimeState.getEvents().get(watchPoint), Integer.parseInt(value));
		} else if (networkRuntimeState.getBasicFBRTs()
				.containsKey(watchPoint.substring(0, watchPoint.indexOf(ECCName)))) {
			final var watchNameWithoutECC = watchPoint.substring(0, watchPoint.indexOf(ECCName));
			final var stateWithoutQoutes = value.substring(1, value.length() - 1);
			networkRuntimeState.getBasicFBRTs().get(watchNameWithoutECC).setActiveState(stateWithoutQoutes);
		} else {
			// unknown
		}
	}

	/**
	 * Set the current sate of the Network Runtime in the interpreter from a
	 * DatapointsState.
	 *
	 * @param targetState the desired state to set in the interpreter.
	 */
	public void setCurrentState(final DatapointsState targetState) {
		for (final var entry : targetState.entrySet()) {
			final var watchPoint = entry.getKey();
			final var value = entry.getValue();
			applyValue(watchPoint, value);
		}
		updateState();
	}

	/**
	 * @brief Reads the current state of the interpreter and updates the
	 *        DatapointsState offered to the outside.
	 */
	public void updateState() {
		for (final var entry : networkRuntimeState.getDataValues().entrySet()) {
			final var watchName = entry.getKey();
			final var value = entry.getValue();
			currentState.put(watchName, value.getValue());
		}

		for (final var entry : networkRuntimeState.getEvents().entrySet()) {
			final var watchName = entry.getKey();
			final var value = entry.getValue();
			currentState.put(watchName, Integer.toString(eventCounter.getEventCount(value)));
		}
		for (final var entry : networkRuntimeState.getBasicFBRTs().entrySet()) {
			final var watchName = entry.getKey() + ECCName;
			final var basicFBRuntime = entry.getValue();
			currentState.put(watchName, "'" + basicFBRuntime.getActiveState() + "'"); // quoutes to make it work for
																						// watches
		}
	}
}
