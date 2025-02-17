/*******************************************************************************
 * Copyright (c) 2021 - 2022, 2025  Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Philipp Bauer - initial implementation, including developing the algorithm; and initial documentation
 *   Fabio Gandolfi - added find all elements connected to fb
 *   Sebastian Hollersbacher - added tracing of struct members
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.helpers;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class FBEndpointFinder {

	protected FBEndpointFinder() {
	}

	/**
	 * data class for recursively called methods
	 */
	private static class RecursionState {
		public final Deque<String> plexStack;
		public final boolean inputSide;
		public final IInterfaceElement ifElem;
		public final Set<IInterfaceElement> connections;
		public final boolean traceMember;

		/**
		 * @param plexStack   a stack of visited (De)Multiplexer-Interfaces (muxers
		 *                    interfaces get pushed onto the stack demuxers pop that
		 *                    interface from the stack)
		 * @param ifElem      the {@link IInterfaceElement} to traverse to the next
		 *                    destination (until the destination is a "regular" FB)
		 * @param inputSide   true if skipping should be done through inputs (find left
		 *                    side FBs of the supplied connection), false otherwise
		 * @param connections a set of destination interfaces from the initial starting
		 *                    point to that destinations
		 */
		public RecursionState(final Deque<String> plexStack, final boolean inputSide, final IInterfaceElement ifElem,
				final Set<IInterfaceElement> connections, final boolean traceMember) {
			this.plexStack = plexStack;
			this.inputSide = inputSide;
			this.ifElem = ifElem;
			this.connections = connections;
			this.traceMember = traceMember;
		}
	}

	/**
	 * find the opposite interfaces of a (De)Multiplexer's member variable
	 *
	 * @param startDecl    the interface to find the destinations/sources from
	 * @param connectedIfs Set where the corresponding interfaces are stored
	 */
	public static void traceMembers(final MemberVarDeclaration startDecl, final Set<IInterfaceElement> connectedIfs) {
		final ArrayDeque<String> queue = new ArrayDeque<>();
		queue.add(startDecl.getName());

		trace(new RecursionState(new ArrayDeque<>(), !startDecl.isIsInput(), startDecl, connectedIfs, true));
	}

	/**
	 * find end-points (FBs on the output-side of the supplied FBs connections)
	 *
	 * @param from the FB to get the destinations from
	 * @return a Set of end-point FBs
	 */
	public static Set<FBNetworkElement> findDestinations(final FBNetworkElement from) {
		return findConnectedElements(from, false, true, true).keySet();
	}

	/**
	 * find source points (FBs on the input-side of the supplied FBs connections)
	 *
	 * @param from the FB to get the sources from
	 * @return a Set of end-point FBs
	 */
	public static Set<FBNetworkElement> findSources(final FBNetworkElement from) {
		return findConnectedElements(from, true, false, true).keySet();
	}

	/**
	 * find both source and destination end-points
	 *
	 * @param from the FB to get the sources from
	 * @return a Set of end-point FBs
	 */
	public static Set<FBNetworkElement> findAllConnectedElements(final FBNetworkElement from) {
		return findConnectedElements(from, true, true, true).keySet();
	}

	/**
	 * find the interfaces of the connected atomic FBs which are the destinations
	 * for the supplied interface (De)Multiplexer and SubApps between atomic FBs are
	 * ignored if the supplied interface is an input the sources are traced else the
	 * destinations are traced
	 *
	 * @param ifElem the interface to find the destinations/sources from
	 * @return a Set of end-point interfaces/pins
	 */
	public static Set<IInterfaceElement> findConnectedInterfaceElements(final IInterfaceElement ifElem) {
		final Set<IInterfaceElement> connectedInt = new HashSet<>();
		for (final Connection con : (ifElem.isIsInput()) ? ifElem.getInputConnections()
				: ifElem.getOutputConnections()) {
			trace(new RecursionState(new ArrayDeque<>(), ifElem.isIsInput(),
					ifElem.isIsInput() ? con.getSource() : con.getDestination(), connectedInt, false));
		}
		return connectedInt;
	}

	/**
	 * find the interfaces connected to the source (if traceThroughInput is true) or
	 * destination (if traceThroughInput is false) of this connection
	 *
	 * @param connection        the connection to trace
	 * @param traceThroughInput if set to true the connection will be traced through
	 *                          the left (source side), if set to false it will be
	 *                          traced through the right (destination side)
	 * @return a Set of end-point interfaces/pins
	 */
	public static Set<IInterfaceElement> findConnectedInterfaceElements(final Connection connection,
			final boolean traceThroughInput) {
		final Set<IInterfaceElement> connectedInt = new HashSet<>();
		trace(new RecursionState(new ArrayDeque<>(), traceThroughInput,
				traceThroughInput ? connection.getSource() : connection.getDestination(), connectedInt, false));
		return connectedInt;
	}

	/**
	 * find all connected interfaces for the supplied FB (only checks the output
	 * side so far, input side for this function was not needed)
	 *
	 * @param fb the FB to find other interfaces to this FBs interfaces
	 * @return a Map of the source interfaces and a set of their destination
	 *         interfaces
	 */
	public static Map<IInterfaceElement, Set<IInterfaceElement>> findConnectedInterfaces(final FBNetworkElement fb) {
		final Map<IInterfaceElement, Set<IInterfaceElement>> results = new HashMap<>();

		final List<IInterfaceElement> outInt = new ArrayList<>();
		outInt.addAll(fb.getInterface().getEventOutputs());
		outInt.addAll(fb.getInterface().getOutputVars());
		outInt.addAll(fb.getInterface().getPlugs());
		flattenConnections(outInt, false)
				.forEach(con -> results.put(con.getSource(), findConnectedInterfaceElements(con.getSource())));

		return results;
	}

	/**
	 * find all possible connected FBs, from one given FB. SubApps, Multiplexer and
	 * Demultiplexer between 2 FBs are ignored.
	 *
	 * @param fb             the FB to search for connected FBs
	 * @param includeInputs  true if destinations to the input side of the FB should
	 *                       be included
	 * @param includeOutputs true if destinations to the output side of the FB
	 *                       should be included
	 * @param traverseParent if true the search will extend to higher levels of the
	 *                       hierarchy, otherwise the parent of the supplied FB will
	 *                       be the top limit, it will still search lower parts of
	 *                       the hierarchy
	 * @return a Set of unique FB as destinations including the number of
	 *         connections to that FB from the given start point
	 */
	public static Map<FBNetworkElement, Integer> findConnectedElements(final FBNetworkElement fb,
			final boolean includeInputs, final boolean includeOutputs, final boolean traverseParent) {

		final List<IInterfaceElement> ifs = new ArrayList<>();
		// include sources to the input side of the given FB
		if (includeInputs) {
			// gather interfaces
			ifs.addAll(fb.getInterface().getEventInputs());
			ifs.addAll(fb.getInterface().getInputVars());
			ifs.addAll(fb.getInterface().getSockets());
		}

		// include destinations to the output side of the given FB
		if (includeOutputs) {
			// gather interfaces
			ifs.addAll(fb.getInterface().getEventOutputs());
			ifs.addAll(fb.getInterface().getOutputVars());
			ifs.addAll(fb.getInterface().getPlugs());
		}

		// trace connections
		final Set<IInterfaceElement> connectedIfs = new HashSet<>();
		ifs.stream()
				.forEach(ifElem -> (ifElem.isIsInput() ? ifElem.getInputConnections() : ifElem.getOutputConnections())
						.forEach(con -> trace(new RecursionState(new ArrayDeque<>(), ifElem.isIsInput(),
								(ifElem.isIsInput() ? con.getSource() : con.getDestination()), connectedIfs, false))));

		// count connections between blocks
		final Map<FBNetworkElement, Integer> result = new HashMap<>();
		connectedIfs.stream().map(IInterfaceElement::getFBNetworkElement).forEach(destFB -> result.put(destFB,
				result.containsKey(destFB) ? Integer.valueOf(result.get(destFB).intValue() + 1) : Integer.valueOf(1)));

		// if parent should not be traversed
		// remove connected elements that share a parent with the outer parents of this
		// block
		if (!(traverseParent || fb.getOuterFBNetworkElement() == null)) {
			final List<FBNetworkElement> parents = new ArrayList<>();
			FBNetworkElement parent = fb.getOuterFBNetworkElement();
			while (parent != null) {
				parents.add(parent);
				parent = parent.getOuterFBNetworkElement();
			}
			result.keySet().removeIf(k -> parents.contains(k.getOuterFBNetworkElement()));
		}

		return result;
	}

	/**
	 * find recursively all possible connected FBs, from one given FB. No Block gets
	 * ignored
	 *
	 * @param connectedElements empty list if new call, otherwise recursively called
	 *                          with already visited elements
	 * @param src               FBElement of the source element
	 * @return a List of connected elements
	 */
	public static List<FBNetworkElement> getConnectedFbs(List<FBNetworkElement> connectedElements,
			final FBNetworkElement src) {
		final List<FBNetworkElement> foundElements = new ArrayList<>();

		final List<IInterfaceElement> pins = new ArrayList<>();
		pins.addAll(src.getInterface().getEventOutputs());
		pins.addAll(src.getInterface().getOutputVars());

		for (final IInterfaceElement pin : pins) {
			foundElements.addAll(getConnectedFbs(pin));
		}

		// search for connected elements of connected elements
		if (!foundElements.isEmpty()
				&& ((foundElements.size() == 1 && !foundElements.get(0).equals(src)) || (foundElements.size() > 1))) {
			for (final FBNetworkElement element : foundElements) {
				if (!element.equals(src) && !connectedElements.contains(element)) {
					connectedElements.add(element);
					connectedElements = getConnectedFbs(connectedElements, element);
				}
			}
		}
		return connectedElements.stream().distinct().toList();
	}

	/**
	 * find all connected Fbs to a given Pin
	 *
	 * @param connectedElements empty list if new call, otherwise recursively called
	 *                          with already visited elements
	 * @param src               source pin
	 * @return a List of connected elements
	 */
	private static List<FBNetworkElement> getConnectedFbs(final IInterfaceElement srcPin) {

		final List<FBNetworkElement> connectedElements = new ArrayList<>();
		for (final Connection con : srcPin.getOutputConnections()) {
			if (con.getDestinationElement() instanceof SubApp) {
				connectedElements.addAll(getConnectedFbs(con.getDestination()));
			} else {
				connectedElements.add(con.getDestinationElement());
			}
		}
		return connectedElements.stream().distinct().toList();
	}

	/**
	 * helper method to avoid duplicated code in
	 * {@link #findConnectedElements(FBNetworkElement, boolean, boolean)}
	 *
	 * @param fbInt  Interface list
	 * @param inputs true if interface is a list of inputs
	 * @return Stream of {@link Connection}
	 */
	private static Stream<Connection> flattenConnections(final List<? extends IInterfaceElement> fbInt,
			final boolean inputs) {
		return fbInt.stream()
				.flatMap(e -> inputs ? e.getInputConnections().stream() : e.getOutputConnections().stream());
	}

	/**
	 * skips all (De)Multiplexers (and SubApps) between FBs
	 *
	 * @param state the current state of the recursion
	 */
	private static void trace(final RecursionState state) {
		if (state.ifElem == null) {
			return;
		}
		final FBNetworkElement fb = state.ifElem.getFBNetworkElement();
		if (fb == null) {
			return;
		}

		// trace an event interface -> handled differently to regular connections since
		// (de)muxer just forward that event
		if (!state.traceMember && state.ifElem instanceof Event) {
			traceEvent(state);
			return;
		}
		// fb is a SubApp traverse down over the interface connections of the subapp
		if (fb instanceof final SubApp subapp && !subapp.isTyped()) {
			traceSubApp(state);
		}
		// fb is mux/demux
		else if (fb instanceof StructManipulator) {
			// from left into mux or from right into demux
			if ((state.inputSide && fb instanceof Demultiplexer) || (!state.inputSide && fb instanceof Multiplexer)) {
				traceTrunk(state);
			}
			// from right into mux or from left into demux
			else {
				traceFan(state);
			}
		}
		// FBs, CFBs, typed SubApps
		else if (!state.traceMember) {
			state.connections.add(state.ifElem);
		}
	}

	/**
	 * traces an event connection to an endpoint
	 *
	 * @param state the current state of the recursion
	 */
	private static void traceEvent(final RecursionState state) {
		final FBNetworkElement fb = state.ifElem.getFBNetworkElement();

		if (fb instanceof final SubApp subapp && !subapp.isTyped()) {
			final EList<Connection> subCons = state.inputSide ? state.ifElem.getInputConnections()
					: state.ifElem.getOutputConnections();
			subCons.forEach(subInt -> traceEvent(new RecursionState(null, state.inputSide,
					state.inputSide ? subInt.getSource() : subInt.getDestination(), state.connections, false)));
			return;
		}
		if (fb instanceof StructManipulator) {
			final EList<Connection> plexCons = state.inputSide
					? fb.getInterface().getEventInputs().get(0).getInputConnections()
					: fb.getInterface().getEventOutputs().get(0).getOutputConnections();

			plexCons.forEach(nextInt -> traceEvent(new RecursionState(null, state.inputSide,
					state.inputSide ? nextInt.getSource() : nextInt.getDestination(), state.connections, false)));
			return;
		}

		state.connections.add(state.ifElem);
	}

	/**
	 * trace connections through subapp
	 *
	 * @param state the current state of the recursion
	 */
	private static void traceSubApp(final RecursionState state) {
		final EList<Connection> subCons = state.inputSide ? state.ifElem.getInputConnections()
				: state.ifElem.getOutputConnections();
		for (final Connection subInt : subCons) {
			trace(new RecursionState(state.plexStack, state.inputSide,
					state.inputSide ? subInt.getSource() : subInt.getDestination(), state.connections,
					state.traceMember));
		}
	}

	/**
	 * trace connections of the given interface from left into mux or from right
	 * into demux in other words trace the trunk connection (the side with only one
	 * connections) of either a mux or demux
	 *
	 * @param state the current state of the recursion
	 */
	private static void traceTrunk(final RecursionState state) {
		final FBNetworkElement fb = state.ifElem.getFBNetworkElement();
		// get single in/output
		final EList<VarDeclaration> vars = (state.inputSide) ? fb.getInterface().getInputVars()
				: fb.getInterface().getOutputVars();
		if (vars.isEmpty()) {
			return;
		}
		final EList<Connection> varCons = (state.inputSide) ? vars.get(0).getInputConnections()
				: vars.get(0).getOutputConnections();
		if (varCons.isEmpty()) {
			return;
		}

		// push interface onto the stack
		state.plexStack.push(state.ifElem.getName());
		// next item to skip through the only in/output of the plexer
		if (state.traceMember) {
			for (final var con : varCons) {
				trace(new RecursionState(state.plexStack, state.inputSide,
						state.inputSide ? con.getSource() : con.getDestination(), state.connections,
						state.traceMember));
			}
		} else {
			trace(new RecursionState(state.plexStack, state.inputSide,
					state.inputSide ? varCons.get(0).getSource() : varCons.get(0).getDestination(), state.connections,
					state.traceMember));
		}
	}

	/**
	 * trace connection of the given interface from right into mux or from left into
	 * demux in other words trace all the connections of the fan side (side where
	 * more the multiple connections are) of either the mux or demux
	 *
	 * @param state the current state of the recursion
	 */
	private static void traceFan(final RecursionState state) {
		final FBNetworkElement fb = state.ifElem.getFBNetworkElement();

		// trace find right interface from the interface stack
		for (final IInterfaceElement structMem : ((StructManipulator) fb).getMemberVars().stream()
				.filter(mem -> fb.getInterfaceElement(mem.getName()) != null).toList()) {
			final IInterfaceElement realInt = fb.getInterfaceElement(structMem.getName());

			Deque<String> subStack;
			/*
			 * case stack empty: occurs when a FB has a struct as output data type which is
			 * connected to a demux first all of the inputs therefore have to be traversed
			 */
			if (state.plexStack.isEmpty()) {
				subStack = new ArrayDeque<>();
			}
			/*
			 * case stack is not empty: first destination plexer was a mux -> go to last
			 * added interface from the stack
			 */
			else if (structMem.getName().equals(state.plexStack.peek())) {
				subStack = ((ArrayDeque<String>) state.plexStack).clone();
				subStack.pop();
				if (state.traceMember && subStack.isEmpty()) {
					state.connections.add(realInt);
				}
			} else {
				continue;
			}

			// find destinations (skip plexers between) from the interface of the plexer
			// according to the previously updated interface-stack
			for (final Connection next : (state.inputSide) ? realInt.getInputConnections()
					: realInt.getOutputConnections()) {
				trace(new RecursionState(subStack, state.inputSide,
						state.inputSide ? next.getSource() : next.getDestination(), state.connections,
						state.traceMember));
			}
		}
	}
}