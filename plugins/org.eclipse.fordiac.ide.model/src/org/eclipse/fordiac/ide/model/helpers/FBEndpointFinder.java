/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Philipp Bauer - initial implementation, including developing the algorithm; and initial documentation
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class FBEndpointFinder {

	private FBEndpointFinder() {
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
	 * find the interfaces of the connected atomic FBs which are the destinations for the supplied interface
	 * (De)Multiplexer and SubApps between atomic FBs are ignored
	 * if the supplied interface is an input the sources are traced
	 * else the destinations are traced
	 *
	 * @param ifElem the interface to find the destinations/sources from
	 * @return a Set of end-point interfaces/pins
	 */
	public static Set<IInterfaceElement> findConnectedInterfaceElements(final IInterfaceElement ifElem){
		final Set<IInterfaceElement> connectedInt = new HashSet<>();
		for (final Connection con: (ifElem.isIsInput()) ? ifElem.getInputConnections() : ifElem.getOutputConnections()) {
			trace(new ArrayDeque<>(), ifElem.isIsInput(), ifElem.isIsInput() ? con.getSource() : con.getDestination(), connectedInt);
		}
		return connectedInt;
	}

	/**
	 * find all connected interfaces for the supplied FB
	 * (only checks the output side so far, input side for this function was not needed)
	 *
	 * @param fb the FB to find other interfaces to this FBs interfaces
	 * @return a Map of the source interfaces and a set of their destination interfaces
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
	 * find all possible connected FBs, from one given FB. SubApps, Multiplexer and Demultiplexer between 2 FBs are ignored.
	 *
	 * @param fb the FB to search for connected FBs
	 * @param includeInputs true if destinations to the input side of the FB should be included
	 * @param includeOutputs true if destinations to the output side of the FB should be included
	 * @param traverseParent if true the search will extend to higher levels of the hierarchy,
	 * otherwise the parent of the supplied FB will be the top limit, it will still search lower parts of the hierarchy
	 * @return a Set of unique FB as destinations including the number of connections to that FB from the given start point
	 */
	public static Map<FBNetworkElement, Integer> findConnectedElements(
			final FBNetworkElement fb,
			final boolean includeInputs,
			final boolean includeOutputs,
			final boolean traverseParent) {

		final Map<FBNetworkElement, Integer> result = new HashMap<>();
		final boolean tp = traverseParent || fb.getOuterFBNetworkElement() == null;

		final List<IInterfaceElement> ifs = new ArrayList<>();
		if (includeInputs) {
			ifs.addAll(fb.getInterface().getEventInputs());
			ifs.addAll(fb.getInterface().getInputVars());
			ifs.addAll(fb.getInterface().getSockets());
		}

		//include destinations to the output side of the given FB
		if (includeOutputs) {
			ifs.addAll(fb.getInterface().getEventOutputs());
			ifs.addAll(fb.getInterface().getOutputVars());
			ifs.addAll(fb.getInterface().getPlugs());
		}

		final Set<IInterfaceElement> connectedIfs = new HashSet<>();
		ifs.stream().forEach(ifElem -> (ifElem.isIsInput() ? ifElem.getInputConnections() : ifElem.getOutputConnections())
				.forEach(con -> trace(new ArrayDeque<>(),
						ifElem.isIsInput(), ifElem.isIsInput() ? con.getSource() : con.getDestination(),
								connectedIfs)));
		connectedIfs.stream().map(IInterfaceElement::getFBNetworkElement)
		.forEach(destFB -> result.put(destFB, result.containsKey(destFB) ? result.get(destFB)+1 : 1));

		if (!tp) {
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
	 * find "feature envy connections"
	 *
	 * @param fb the FB to check
	 * @return an array of {number of external connections, number of internal connections} based on the supplied FB
	 */
	public static int[] getNumberOfConnections(final FBNetworkElement fb) {
		final FBNetworkElement parent = fb.getOuterFBNetworkElement();
		int externalConnections = 0, internalConnections = 0;

		//destinations to input side of the given FB
		final List<IInterfaceElement> inInt = new ArrayList<>();
		inInt.addAll(fb.getInterface().getEventInputs());
		inInt.addAll(fb.getInterface().getInputVars());
		inInt.addAll(fb.getInterface().getSockets());
		final List<Boolean> isInInt = flattenConnections(inInt, true)
				.map(con -> isInterfaceConnection(new ArrayDeque<>(), con, true, parent))
				.collect(Collectors.toList());

		//include destinations to the output side of the given FB
		final List<IInterfaceElement> outInt = new ArrayList<>();
		outInt.addAll(fb.getInterface().getEventOutputs());
		outInt.addAll(fb.getInterface().getOutputVars());
		outInt.addAll(fb.getInterface().getPlugs());
		final List<Boolean> isOutInt = flattenConnections(outInt, false)
				.map(con -> isInterfaceConnection(new ArrayDeque<>(), con, false, parent))
				.collect(Collectors.toList());

		externalConnections += (int) isInInt.stream().filter(v -> v).count() + isOutInt.stream().filter(v -> v).count();
		internalConnections += (int) isInInt.stream().filter(v -> !v).count() + isOutInt.stream().filter(v -> !v).count();

		return new int[] {externalConnections, internalConnections};
	}

	/**
	 * helper method to avoid duplicated code in {@link #findConnectedElements(FBNetworkElement, boolean, boolean)}
	 *
	 * @param fbInt Interface list
	 * @param inputs true if interface is a list of inputs
	 * @return Stream of {@link Connection}
	 */
	private static Stream<Connection> flattenConnections(final List<? extends IInterfaceElement> fbInt, final boolean inputs) {
		return fbInt.stream().flatMap(e -> inputs ? e.getInputConnections().stream() : e.getOutputConnections().stream());
	}

	/**
	 * skips all (De)Multiplexers (and SubApps) between FBs
	 *
	 * @param plexStack a stack of visited (De)Multiplexer-Interfaces (muxers interfaces get pushed onto the stack demuxers pop that interface from the stack)
	 * @param ifElem the {@link IInterfaceElement} to traverse to the next destination (until the destination is a "regular" FB)
	 * @param inputSide true if skipping should be done through inputs (find left side FBs of the supplied connection), false otherwise
	 * @param connections a set of destination interfaces from the initial starting point to that destinations
	 */
	private static void trace(
			final Deque<String> plexStack,
			final boolean inputSide,
			final IInterfaceElement ifElem,
			final Set<IInterfaceElement> connections) {

		if (ifElem == null) {
			return;
		}
		final FBNetworkElement fb = ifElem.getFBNetworkElement();
		if (fb == null) {
			return;
		}

		//fb is a SubApp traverse down over the interface connections of the subapp
		if (fb instanceof SubApp) {
			final EList<Connection> subCons = (inputSide) ? ifElem.getInputConnections() : ifElem.getOutputConnections();
			for (final Connection subInt: subCons) {
				trace(plexStack, inputSide, inputSide ? subInt.getSource() : subInt.getDestination(), connections);
			}
		}
		//fb is mux/demux
		else if(fb instanceof Multiplexer || fb instanceof Demultiplexer) {
			//from left into mux or from right into demux
			if ((inputSide && fb instanceof Demultiplexer) || (!inputSide && fb instanceof Multiplexer)) {
				//get single in/output
				final EList<VarDeclaration> vars = (inputSide) ? fb.getInterface().getInputVars() : fb.getInterface().getOutputVars();
				if (vars.isEmpty()) {
					return;
				}
				final EList<Connection> varCons = (inputSide) ? vars.get(0).getInputConnections() : vars.get(0).getOutputConnections();
				if (varCons.isEmpty()) {
					return;
				}

				//push interface onto the stack
				plexStack.push(ifElem.getName());
				//next item to skip through the only in/output of the plexer
				trace(plexStack, inputSide, inputSide ? varCons.get(0).getSource() : varCons.get(0).getDestination(), connections);
			}
			//from right into mux or from left into demux
			else if ((inputSide && fb instanceof Multiplexer) || (!inputSide && fb instanceof Demultiplexer)) {
				for (final IInterfaceElement var: ((StructManipulator)fb).getStructType().getMemberVariables()) {
					final IInterfaceElement realInt = fb.getInterfaceElement(var.getName());
					if (realInt == null) {
						continue;
					}

					Deque<String> subStack;
					/* case stack empty: occurs when a FB has a struct as output data type which is connected to a demux first
					 * all of the inputs therefore have to be traversed */
					if (plexStack.isEmpty()) {
						subStack = new ArrayDeque<>();
					}
					/* case stack is not empty: first destination plexer was a mux
					 * -> go to last added interface from the stack */
					else if (var.getName().equals(plexStack.peek())) {
						subStack = ((ArrayDeque<String>) plexStack).clone();
						subStack.pop();
					}
					else {
						continue;
					}

					//find destinations (skip plexers between) from the interface of the plexer accourding to the previously updated interface-stack
					for (final Connection next: (inputSide) ? realInt.getInputConnections() : realInt.getOutputConnections()) {
						trace(subStack, inputSide, inputSide ? next.getSource() : next.getDestination(), connections);
					}
				}
			}
		}
		//no SubApp or plexer -> add to found FBs
		else {
			connections.add(ifElem);
		}
	}

	/**
	 * check if a supplied connection is connected to the surrounding SubApp interface
	 * algorithm is a modified copy of the {@link #trace(Deque, boolean, IInterfaceElement, Set)} function
	 *
	 * @param plexStack a stack of visited (De)Multiplexer-Interfaces (muxers interfaces get pushed onto the stack demuxers pop that interface from the stack)
	 * @param con the staring connection to check whether or not it is connected to the parent subapp interface
	 * @param inputSide true if skipping should be done through inputs (find left side FBs of the supplied connection), false otherwise
	 * @param parent the parent to check for connection
	 * @return true if it is an "interface connection"
	 */
	private static boolean isInterfaceConnection(
			final Deque<String> plexStack,
			final Connection con,
			final boolean inputSide,
			final FBNetworkElement parent) {

		final IInterfaceElement fbInt = (inputSide) ? con.getSource() : con.getDestination();
		if (fbInt == null) {
			return false;
		}
		final FBNetworkElement fb = fbInt.getFBNetworkElement();
		if (fb == null) {
			return false;
		}
		if (parent != null && fb.equals(parent)) {
			return true;
		}

		//fb is mux/demux
		if(fb instanceof Multiplexer || fb instanceof Demultiplexer) {
			//from left into mux or from right into demux
			if ((inputSide && fb instanceof Demultiplexer) || (!inputSide && fb instanceof Multiplexer)) {
				//get single in/output
				final EList<VarDeclaration> vars = (inputSide) ? fb.getInterface().getInputVars() : fb.getInterface().getOutputVars();
				if (vars.isEmpty()) {
					return false;
				}
				final EList<Connection> varCons = (inputSide) ? vars.get(0).getInputConnections() : vars.get(0).getOutputConnections();
				if (varCons.isEmpty()) {
					return false;
				}

				//push interface onto the stack
				plexStack.push(fbInt.getName());
				//next item to skip through the only in/output of the plexer
				return isInterfaceConnection(plexStack, varCons.get(0), inputSide, parent);
			}
			if ((inputSide && fb instanceof Multiplexer) || (!inputSide && fb instanceof Demultiplexer)) {
				for (final IInterfaceElement var: ((StructManipulator)fb).getStructType().getMemberVariables()) {
					final IInterfaceElement realInt = fb.getInterfaceElement(var.getName());
					if (realInt == null) {
						continue;
					}

					Deque<String> subStack;
					/* case stack empty: occurs when a FB has a struct as output data type which is connected to a demux first
					 * all of the inputs therefore have to be traversed */
					if (plexStack.isEmpty()) {
						subStack = new ArrayDeque<>();
					}
					/* case stack is not empty: first destination plexer was a mux
					 * -> go to last added interface from the stack */
					else if (var.getName().equals(plexStack.peek())) {
						subStack = ((ArrayDeque<String>) plexStack).clone();
						subStack.pop();
					}
					else {
						continue;
					}

					//find destinations (skip plexers between) from the interface of the plexer accourding to the previously updated interface-stack
					for (final Connection next: (inputSide) ? realInt.getInputConnections() : realInt.getOutputConnections()) {
						return isInterfaceConnection(subStack, next, inputSide, parent);
					}
				}
			}
		}

		return false;
	}
}