/*******************************************************************************
 * Copyright (c) 2016, 2023 fortiss GmbH, Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - expanded for input events
 *   Virendra Ashiwal, Bianca Wiesmayr - expanded for Transitions
 *   Alois Zoitl - updated for new adapter FB handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.ui.FordiacMessages;

/**
 * Helper functions need by the action and transition edit parts.
 *
 *
 */
public final class ECCContentAndLabelProvider {

	public static final String EMPTY_FIELD = FordiacMessages.EmptyField; // drop-down menu entry for
	// selecting nothing
	public static final String ONE_CONDITION = "1"; //$NON-NLS-1$

	public static List<Event> getOutputEvents(final BasicFBType type) {
		final List<Event> events = new ArrayList<>();
		if (null != type) {
			events.addAll(type.getInterfaceList().getEventOutputs());
			type.getInterfaceList().getSockets().stream()
					.forEach(socket -> events.addAll(socket.getAdapterFB().getInterface().getEventInputs()));
			type.getInterfaceList().getPlugs().stream()
					.forEach(plug -> events.addAll(plug.getAdapterFB().getInterface().getEventInputs()));
			Collections.sort(events, NamedElementComparator.INSTANCE);
		}

		return events;
	}

	public static boolean isOneConditionExpression(final ECTransition transition) {
		return transition.getConditionExpression() != null && transition.getConditionExpression().equals(ONE_CONDITION);
	}

	public static List<String> getOutputEventNames(final BasicFBType type) {
		final List<String> eventNames = new ArrayList<>(getEventNames(getOutputEvents(type)));
		eventNames.add(EMPTY_FIELD);
		return eventNames;
	}

	public static List<Event> getInputEvents(final BasicFBType type) {
		final List<Event> events = new ArrayList<>();
		if (null != type) {
			events.addAll(type.getInterfaceList().getEventInputs());
			type.getInterfaceList().getSockets().stream()
					.forEach(socket -> events.addAll(socket.getAdapterFB().getInterface().getEventOutputs()));
			type.getInterfaceList().getPlugs().stream()
					.forEach(plug -> events.addAll(plug.getAdapterFB().getInterface().getEventOutputs()));
			Collections.sort(events, NamedElementComparator.INSTANCE);
		}
		return events;
	}

	public static List<String> getInputEventNames(final BasicFBType type) {
		final List<String> inputEventNames = new ArrayList<>(getEventNames(getInputEvents(type)));
		inputEventNames.add(EMPTY_FIELD);
		return inputEventNames;
	}

	public static List<String> getTransitionConditionEventNames(final BasicFBType type) {
		final List<String> transitionConditionEvents = new ArrayList<>();
		transitionConditionEvents.add(ONE_CONDITION);
		transitionConditionEvents.addAll(getInputEventNames(type));
		return transitionConditionEvents;
	}

	public static List<Algorithm> getAlgorithms(final BaseFBType type) {
		final List<Algorithm> algorithms = new ArrayList<>(type.getAlgorithm());
		Collections.sort(algorithms, NamedElementComparator.INSTANCE);
		return algorithms;
	}

	public static List<String> getAlgorithmNames(final BasicFBType type) {
		final List<String> algNames = new ArrayList<>(getAlgorithms(type).stream().map(Algorithm::getName).toList());
		algNames.add(EMPTY_FIELD);
		return algNames;
	}

	public static BasicFBType getFBType(final ECAction action) {
		if ((null != action.getECState()) && (null != action.getECState().getECC())) {
			return action.getECState().getECC().getBasicFBType();
		}
		return null;
	}

	public static List<ECState> getStates(final BasicFBType type) {
		return type.getECC().getECState();
	}

	public static List<String> getStateNames(final BasicFBType type) {
		return getStates(type).stream().map(ECState::getName).toList();
	}

	private static List<String> getEventNames(final List<Event> eventList) {
		return eventList.stream().map(ECCContentAndLabelProvider::getEventName).toList();
	}

	public static String getEventName(final Event event) {
		if (event.getFBNetworkElement() instanceof AdapterFB) {
			return event.getFBNetworkElement().getName() + "." + event.getName(); //$NON-NLS-1$
		}
		return event.getName();
	}

	private ECCContentAndLabelProvider() {
		throw new UnsupportedOperationException("ECActionHelpers should not be instantiated!"); //$NON-NLS-1$
	}
}
