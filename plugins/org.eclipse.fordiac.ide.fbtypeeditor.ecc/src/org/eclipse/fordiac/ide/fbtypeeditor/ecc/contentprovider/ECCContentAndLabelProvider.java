/*******************************************************************************
 * Copyright (c) 2016, 2013, 2017 fortiss GmbH
 *               2019 Johannes Kepler University Linz
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
 *   Bianca Wiesmayr
 *     - expanded for input events
 *   Virendra Ashiwal, Bianca Wiesmayr
 *   	- expanded for Transitions
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.contentprovider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.NamedElementComparator;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
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
			type.getInterfaceList().getSockets().stream().filter(socket -> (null != socket.getType()))
			.forEach(socket -> events.addAll(
							createAdapterEventList(socket.getAdapterType().getInterfaceList().getEventInputs(),
									socket)));
			type.getInterfaceList().getPlugs().stream().filter(plug -> (null != plug.getType())).forEach(plug -> events
					.addAll(createAdapterEventList(plug.getAdapterType().getInterfaceList().getEventOutputs(), plug)));
			Collections.sort(events, NamedElementComparator.INSTANCE);
		}

		return events;
	}

	public static List<String> getOutputEventNames(final BasicFBType type) {
		final List<String> eventNames = getOutputEvents(type).stream().map(Event::getName).collect(Collectors.toList());
		eventNames.add(EMPTY_FIELD);
		return eventNames;
	}

	public static List<Event> getInputEvents(final BasicFBType type) {
		final List<Event> transitionConditions = new ArrayList<>();
		if (null != type) {
			transitionConditions.addAll(type.getInterfaceList().getEventInputs());
			type.getInterfaceList().getSockets().stream().filter(socket -> (null != socket.getType()))
			.forEach(socket -> transitionConditions.addAll(
							createAdapterEventList(socket.getAdapterType().getInterfaceList().getEventOutputs(),
									socket)));
			type.getInterfaceList().getPlugs().stream().filter(plug -> (null != plug.getType()))
			.forEach(plug -> transitionConditions
							.addAll(createAdapterEventList(plug.getAdapterType().getInterfaceList().getEventInputs(),
									plug)));
			Collections.sort(transitionConditions, NamedElementComparator.INSTANCE);
		}
		return transitionConditions;
	}

	public static List<String> getInputEventNames(final BasicFBType type) {
		final List<String> inputEventNames = getInputEvents(type).stream().map(Event::getName)
				.collect(Collectors.toList());
		inputEventNames.add(EMPTY_FIELD);
		return inputEventNames;
	}

	public static List<String> getTransitionConditionEventNames(final BasicFBType type) {
		final List<String> transitionConditionEvents = new ArrayList<>();
		transitionConditionEvents.add(ONE_CONDITION);
		transitionConditionEvents.addAll(getInputEventNames(type));
		return transitionConditionEvents;
	}

	// TODO move to a utility class as same function is used in
	// ECTransitionEditPart
	public static List<Event> createAdapterEventList(final EList<Event> events, final AdapterDeclaration adapter) {
		final List<Event> adapterEvents = new ArrayList<>();

		for (final Event event : events) {
			final AdapterEvent ae = LibraryElementFactory.eINSTANCE.createAdapterEvent();
			ae.setName(event.getName());
			ae.setComment(event.getComment());
			ae.setAdapterDeclaration(adapter);
			adapterEvents.add(ae);
		}
		return adapterEvents;
	}

	public static List<Algorithm> getAlgorithms(final BasicFBType type) {
		final List<Algorithm> algorithms = new ArrayList<>();
		algorithms.addAll(type.getAlgorithm());

		Collections.sort(algorithms, NamedElementComparator.INSTANCE);
		return algorithms;
	}

	public static List<String> getAlgorithmNames(final BasicFBType type) {
		final List<String> algNames = getAlgorithms(type).stream().map(Algorithm::getName).collect(Collectors.toList());
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
		return getStates(type).stream().map(ECState::getName).collect(Collectors.toList());
	}

	private ECCContentAndLabelProvider() {
		throw new UnsupportedOperationException("ECActionHelpers should not be instantiated!"); //$NON-NLS-1$
	}
}
