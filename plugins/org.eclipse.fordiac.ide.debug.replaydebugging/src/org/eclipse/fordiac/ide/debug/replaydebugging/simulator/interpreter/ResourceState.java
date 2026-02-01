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

package org.eclipse.fordiac.ide.debug.replaydebugging.simulator.interpreter;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Utils;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * @brief Representation of the current state of the resource being interpreted.
 *
 *        It offers the information needed for monitoring and replay debugging.
 *        The current values of variables are taken form the interface elements
 *        themselves being updated by the interpreter, and the event counters
 *        are done via the EventCounter class.
 *
 *        A map from watch names to these elements provides access for the
 *        monitoring feature.
 */
public class ResourceState {

	private final Map<String, IInterfaceElement> valueHolders = new HashMap<>();
	private final EventCounter eventCounter;
	private final ReplayNavigator.DatapointsState currentState = new ReplayNavigator.DatapointsState();

	public ResourceState(final Map<String, IInterfaceElement> interfaceElements, final String deviceResourcePrefix) {

		// get actual watch names taking into account the deviceResourcePrefix
		for (final var valueHoldersAndName : interfaceElements.entrySet()) {
			final var interfaceQualifiedName = valueHoldersAndName.getKey();
			final var interfaceElement = valueHoldersAndName.getValue();
			final var watchName = Utils.getWatchName(deviceResourcePrefix, interfaceQualifiedName);
			valueHolders.put(watchName, interfaceElement);
		}

		eventCounter = new EventCounter(
				valueHolders.entrySet().stream().filter(entry -> entry.getValue() instanceof Event)
						.map(entry -> (Event) entry.getValue()).collect(java.util.stream.Collectors.toSet()));
		updateState();
	}

	public void eventTriggered(final Event event) {
		eventCounter.incrementEventCount(event);
	}

	public ReplayNavigator.DatapointsState getCurrentState() {
		return currentState;
	}

	public void updateState() {
		for (final var entry : valueHolders.entrySet()) {
			final var watchName = entry.getKey();
			final var interfaceElement = entry.getValue();

			switch (interfaceElement) {
			case final Event event -> currentState.put(watchName, Integer.toString(eventCounter.getEventCount(event)));
			case final VarDeclaration varDecl -> {
				final var value = varDecl.getValue();
				if (value == null) {
					varDecl.setValue(LibraryElementFactory.eINSTANCE.createValue());
				}
				currentState.put(watchName, varDecl.getValue().getValue());
			}
			default -> {
				// ignore other interface elements if any as currently only events and var
				// declarations are part of the state
			}
			}
		}
	}
}
