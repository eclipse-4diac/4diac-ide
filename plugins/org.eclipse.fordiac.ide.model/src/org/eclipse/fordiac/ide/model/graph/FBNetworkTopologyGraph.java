/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;

/**
 * A topology graph for an FB network
 */
public abstract class FBNetworkTopologyGraph<N extends FBNetworkTopologyGraph<N>.TopologyNode>
		extends FBNetworkGraph<N> {

	public static final int NULL_SEQUENCE_NUMBER = 0;

	/**
	 * A node with additional topology information
	 */
	public abstract class TopologyNode extends Node {
		private int index;
		private int sequenceNumber = NULL_SEQUENCE_NUMBER;
		private final SequencedSet<N> predecessors = new LinkedHashSet<>();
		private final SequencedSet<N> successors = new LinkedHashSet<>();
		private final SequencedSet<N> cycleSources = new LinkedHashSet<>();
		private final SequencedSet<N> cycleDestinations = new LinkedHashSet<>();
		private boolean containerEntry;
		private boolean containerExit;
		private boolean selfLoop;

		protected TopologyNode(final BlockFBNetworkElement element) {
			super(element);
		}

		/**
		 * Get the index in the sorted nodes
		 *
		 * @return sorted node index
		 */
		public int getIndex() {
			return index;
		}

		protected void setIndex(final int index) {
			this.index = index;
		}

		/**
		 * Get the sequence number
		 *
		 * @return the sequence number starting at 1 or
		 *         {@link FBNetworkTopologyGraph#NULL_SEQUENCE_NUMBER} if not available
		 * @apiNote The sequence number is the minimum of the sequence numbers of all
		 *          predecessors plus 1, or 1 if no predecessors exist.
		 */
		public int getSequenceNumber() {
			return sequenceNumber;
		}

		protected void setSequenceNumber(final int sequenceNumber) {
			this.sequenceNumber = sequenceNumber;
		}

		/**
		 * Get the predecessors, excluding cycles
		 *
		 * @return a sequenced set of predecessors
		 */
		public SequencedSet<N> getPredecessors() {
			return Collections.unmodifiableSequencedSet(predecessors);
		}

		/**
		 * Get the successors, excluding cycles
		 *
		 * @return a sequenced set of successors
		 */
		public SequencedSet<N> getSuccessors() {
			return Collections.unmodifiableSequencedSet(successors);
		}

		/**
		 * Get the sources of this node that form a cycle
		 *
		 * @return a sequenced set of sources
		 */
		public SequencedSet<N> getCycleSources() {
			return Collections.unmodifiableSequencedSet(cycleSources);
		}

		/**
		 * Get the destinations of this node that form a cycle
		 *
		 * @return a sequenced set of destinations
		 */
		public SequencedSet<N> getCycleDestinations() {
			return Collections.unmodifiableSequencedSet(cycleDestinations);
		}

		protected void addPredecessor(final N node) {
			predecessors.add(node);
		}

		protected void addSuccessor(final N node) {
			successors.add(node);
		}

		protected void removePredecessor(final N node) {
			predecessors.remove(node);
		}

		protected void removeSuccessor(final N node) {
			successors.remove(node);
		}

		protected void addCycleSource(final N node) {
			cycleSources.add(node);
		}

		protected void addCycleDestination(final N node) {
			cycleDestinations.add(node);
		}

		/**
		 * Get if the node has an incoming connection from its container
		 *
		 * @return true if there is such a connection, false otherwise
		 */
		public boolean hasContainerEntry() {
			return containerEntry;
		}

		protected void setContainerEntry(final boolean containerEntry) {
			this.containerEntry = containerEntry;
		}

		/**
		 * Get if the node has an outgoing connection to its container
		 *
		 * @return true if there is such a connection, false otherwise
		 */
		public boolean hasContainerExit() {
			return containerExit;
		}

		protected void setContainerExit(final boolean containerExit) {
			this.containerExit = containerExit;
		}

		/**
		 * Get if the node has a self loop
		 *
		 * @return true if there is a self loop, false otherwise
		 */
		public boolean hasSelfLoop() {
			return selfLoop;
		}

		protected void setSelfLoop(final boolean selfLoop) {
			this.selfLoop = selfLoop;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FBNetworkTopologyGraph<N> getGraph() {
			return FBNetworkTopologyGraph.this;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return String.format("%s [element=%s, sequenceNumber=%s]", getClass().getSimpleName(), //$NON-NLS-1$
					getElement().getQualifiedName(), Integer.valueOf(sequenceNumber));
		}
	}

	private final List<N> sortedNodes;
	private final SequencedSet<N> entryNodes;
	private final SequencedSet<N> exitNodes;

	/**
	 * Create a new FB network topology graph
	 *
	 * @param network the network
	 * @param parent  the parent graph or null if none
	 */
	protected FBNetworkTopologyGraph(final FBNetwork network, final N parent) {
		super(network, parent);
		final TopologyComputer topologyComputer = createTopologyComputer();
		topologyComputer.computeTopology();
		sortedNodes = List.copyOf(topologyComputer.getSortedNodes());
		entryNodes = Collections.unmodifiableSequencedSet(new LinkedHashSet<>(topologyComputer.getEntryNodes()));
		exitNodes = Collections.unmodifiableSequencedSet(new LinkedHashSet<>(topologyComputer.getExitNodes()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<N> getSortedNodes() {
		return sortedNodes;
	}

	/**
	 * Get the nodes that have an incoming connection from their container
	 *
	 * @return a set of entry nodes
	 */
	public SequencedSet<N> getEntryNodes() {
		return entryNodes;
	}

	/**
	 * Get the nodes that have an outgoing connection to their container
	 *
	 * @return a set of exit nodes
	 */
	public SequencedSet<N> getExitNodes() {
		return exitNodes;
	}

	/**
	 * Determine if the provided element is relevant for this graph
	 *
	 * @param element an element
	 * @return true if relevant, false otherwise
	 * @implNote This includes all elements in the current network by default.
	 */
	protected boolean isRelevant(final BlockFBNetworkElement element) {
		return element != null && element.getFbNetwork() == getNetwork();
	}

	/**
	 * Determine if the provided connection is relevant for this graph
	 *
	 * @param connection a connection
	 * @return true if relevant, false otherwise
	 */
	protected abstract boolean isRelevant(Connection connection);

	/**
	 * Create a new topology computer
	 *
	 * @return a topology computer
	 * @implNote This can be overridden by subclasses to provide custom behavior.
	 */
	protected TopologyComputer createTopologyComputer() {
		return new TopologyComputer();
	}

	/**
	 * A helper class to compute the topology
	 */
	protected class TopologyComputer {

		protected class ElementState {

			// initial inputs
			protected final BlockFBNetworkElement element;
			protected final SequencedSet<BlockFBNetworkElement> sourceElements;
			protected final SequencedSet<BlockFBNetworkElement> destinationElements;
			protected final boolean containerEntry;
			protected final boolean containerExit;
			protected final boolean selfLoop;

			// weak-component bookkeeping
			protected int remainingDestinationCount;
			protected int longestPathLength;

			// strong-component bookkeeping
			protected int componentIndex = -1;
			protected int discoveryIndex = -1;
			protected int lowLink = -1;
			protected boolean onStack;
			protected boolean componentEntry;
			protected boolean componentExit;

			protected ElementState(final BlockFBNetworkElement element) {
				this.element = element;
				sourceElements = findSourceElements(element);
				destinationElements = findDestinationElements(element);
				containerEntry = containerEntryElements.contains(element);
				containerExit = containerExitElements.contains(element);
				selfLoop = destinationElements.contains(element);
				remainingDestinationCount = destinationElements.size() - (selfLoop ? 1 : 0);
			}

			protected boolean hasContainerEntry() {
				return containerEntry;
			}

			protected boolean hasContainerExit() {
				return containerExit;
			}

			protected int getLongestPathLength() {
				return longestPathLength;
			}

			protected int getSourceWeight() {
				return (containerEntry ? 2 : 0) + (componentEntry ? 1 : 0);
			}

			protected int getSinkWeight() {
				return (containerExit ? 2 : 0) + (componentExit ? 1 : 0);
			}

			protected String getName() {
				return element.getName();
			}

			@Override
			public String toString() {
				return String.format("%s [element=%s, componentIndex=%s]", getClass().getSimpleName(), //$NON-NLS-1$
						element.getName(), Integer.valueOf(componentIndex));
			}
		}

		// initial inputs
		protected final SequencedMap<BlockFBNetworkElement, ElementState> elementStates;
		protected final SequencedSet<BlockFBNetworkElement> containerEntryElements;
		protected final SequencedSet<BlockFBNetworkElement> containerExitElements;

		// strong-component bookkeeping
		protected final List<List<ElementState>> components = new ArrayList<>(getNodes().size());
		protected final Deque<ElementState> componentStack = new ArrayDeque<>();
		protected int nextDiscoveryIndex;
		protected int nextComponentIndex;

		// final topology
		protected final List<N> sortedNodes = new ArrayList<>(getNodes().size());
		protected final SequencedSet<N> entryNodes = new LinkedHashSet<>();
		protected final SequencedSet<N> exitNodes = new LinkedHashSet<>();

		/**
		 * Create a new topology computer
		 */
		protected TopologyComputer() {
			containerEntryElements = findContainerDestinationElements();
			containerExitElements = findContainerSourceElements();
			elementStates = LinkedHashMap.newLinkedHashMap(getElements().size());
			for (final BlockFBNetworkElement element : getElements()) {
				elementStates.put(element, new ElementState(element));
			}
		}

		/**
		 * Compute the topology
		 */
		public void computeTopology() {
			findStrongComponents(findWeakComponents(sortedElements()));
			populateTopology();
		}

		/**
		 * Get the sorted elements
		 *
		 * @return the sorted elements
		 * @implNote This sorts the elements by name, while keeping container entry
		 *           elements first.
		 */
		protected SequencedSet<ElementState> sortedElements() {
			final TreeSet<ElementState> result = new TreeSet<>(Comparator.comparing(ElementState::hasContainerEntry)
					.reversed().thenComparing(ElementState::getName));
			result.addAll(elementStates.values());
			return result;
		}

		/**
		 * Find weakly-connected components among element states
		 *
		 * @param remaining remaining elements to process
		 * @return a list of weakly-connected components
		 * @implNote This performs a simple traversal among the source and destination
		 *           elements to find all connected elements.
		 */
		protected List<List<ElementState>> findWeakComponents(final SequencedSet<ElementState> remaining) {
			final List<List<ElementState>> result = new ArrayList<>();
			while (!remaining.isEmpty()) {
				final ElementState start = remaining.removeFirst();
				final Deque<ElementState> worklist = new ArrayDeque<>(List.of(start));
				final List<ElementState> component = new ArrayList<>();

				while (!worklist.isEmpty()) {
					final ElementState state = worklist.removeFirst();
					component.add(state);
					for (final BlockFBNetworkElement sourceElement : state.sourceElements) {
						final ElementState source = elementStates.get(sourceElement);
						if (remaining.remove(source)) {
							worklist.add(source);
						}
					}
					for (final BlockFBNetworkElement destinationElement : state.destinationElements) {
						final ElementState destination = elementStates.get(destinationElement);
						if (remaining.remove(destination)) {
							worklist.add(destination);
						}
					}
				}
				computeLongestPathLengths(component);
				sortWeakComponent(component);
				result.add(component);
			}
			return result;
		}

		/**
		 * Compute longest acyclic path lengths for the given weak component
		 *
		 * @param component a weakly-connected component
		 * @implNote This uses a reverse Kahn pass starting at sinks. Nodes that are
		 *           part of cycles are not finalized and keep their unresolved path
		 *           length.
		 */
		protected void computeLongestPathLengths(final List<ElementState> component) {
			final Deque<ElementState> worklist = new ArrayDeque<>();
			for (final ElementState state : component) {
				if (state.remainingDestinationCount == 0) {
					worklist.addLast(state);
				}
			}

			while (!worklist.isEmpty()) {
				final ElementState state = worklist.removeFirst();
				for (final BlockFBNetworkElement sourceElement : state.sourceElements) {
					final ElementState source = elementStates.get(sourceElement);
					if (source == state) {
						continue; // skip self loops
					}
					source.longestPathLength = Math.max(source.longestPathLength, state.longestPathLength + 1);
					if (--source.remainingDestinationCount == 0) {
						worklist.addLast(source);
					}
				}
			}
		}

		/**
		 * Sort the given weakly-connected component in place
		 *
		 * @param component a list of weakly-connected elements
		 * @implNote Roots are sorted last by resolved longest path length descending
		 *           and then by name. Remaining elements are sorted by name. This
		 *           ensures that root are processed first by the following reverse
		 *           Tarjan algorithm.
		 */
		protected void sortWeakComponent(final List<ElementState> component) {
			component.sort(TopologyComputer::compareWeakComponentElements);
		}

		/**
		 * Compare elements of a weakly-connected component
		 *
		 * @param first  the first element
		 * @param second the second element
		 * @return 0 if both elements compare equal, a value less than 0 if the first is
		 *         lexicographically less than the second element, and a value greater
		 *         than 0 if the first is lexicographically greater than the second
		 *         element.
		 * @implNote This first compares whether first or second is a root node, their
		 *           longest paths if both are root nodes and longest path length
		 *           calculation completed, and finally by element name.
		 */
		protected static int compareWeakComponentElements(final ElementState first, final ElementState second) {
			final boolean firstRoot = first.sourceElements.isEmpty();
			final boolean secondRoot = second.sourceElements.isEmpty();

			if (firstRoot != secondRoot) {
				return Boolean.compare(firstRoot, secondRoot);
			}

			if (firstRoot) {
				final boolean firstResolved = first.remainingDestinationCount == 0;
				final boolean secondResolved = second.remainingDestinationCount == 0;
				if (firstResolved && secondResolved) {
					final int result = Integer.compare(second.longestPathLength, first.longestPathLength);
					if (result != 0) {
						return result;
					}
				}
			}

			return first.getName().compareTo(second.getName());
		}

		/**
		 * Find strongly-connected components
		 *
		 * @param weakComponents a list of weakly-connected components
		 * @implNote The used algorithm by Tarjan naturally emits components in
		 *           <em>reverse</em> topological order, so the input elements, edges,
		 *           and the final result are reversed to obtain the desired forward
		 *           topological order.
		 */
		protected void findStrongComponents(final List<List<ElementState>> weakComponents) {
			for (final List<ElementState> weakComponent : weakComponents.reversed()) {
				findStrongComponents(weakComponent);
			}
			Collections.reverse(components);
		}

		/**
		 * Find strongly-connected components among element states
		 *
		 * @param orderedElements a list of weakly-connected elements
		 * @implNote This is based on <em>Depth-first search and linear graph
		 *           algorithms</em> by Tarjan (1972).
		 */
		protected void findStrongComponents(final SequencedCollection<ElementState> orderedElements) {
			for (final ElementState state : orderedElements.reversed()) {
				if (state.discoveryIndex < 0) {
					findStrongComponent(state);
				}
			}
		}

		/**
		 * Find a strongly-connected component among element states
		 *
		 * @param state a starting element state
		 * @implNote This is based on <em>Depth-first search and linear graph
		 *           algorithms</em> by Tarjan (1972), modified to avoid recursion.
		 */
		protected void findStrongComponent(final ElementState state) {
			final Deque<StrongComponentFrame> callStack = new ArrayDeque<>();
			initializeStrongComponent(state);
			callStack.push(new StrongComponentFrame(state));

			while (!callStack.isEmpty()) {
				final StrongComponentFrame frame = callStack.peek();
				final ElementState currentState = frame.state;

				if (frame.destinationElements.hasNext()) {
					final BlockFBNetworkElement destinationElement = frame.destinationElements.next();
					final ElementState destinationState = elementStates.get(destinationElement);
					if (destinationState.discoveryIndex < 0) {
						initializeStrongComponent(destinationState);
						callStack.push(new StrongComponentFrame(destinationState));
					} else if (destinationState.onStack) {
						currentState.lowLink = Math.min(currentState.lowLink, destinationState.discoveryIndex);
					}
					continue;
				}

				callStack.pop();

				if (!callStack.isEmpty()) {
					final ElementState parentState = callStack.peek().state;
					parentState.lowLink = Math.min(parentState.lowLink, currentState.lowLink);
				}

				if (currentState.lowLink == currentState.discoveryIndex) {
					final int componentIndex = nextComponentIndex++;
					if (componentStack.peek() == currentState) {
						final ElementState componentState = componentStack.pop();
						componentState.onStack = false;
						componentState.componentIndex = componentIndex;
						addStrongComponent(componentState);
					} else {
						final List<ElementState> component = new ArrayList<>();
						ElementState componentState;
						do {
							componentState = componentStack.pop();
							componentState.onStack = false;
							componentState.componentIndex = componentIndex;
							component.add(componentState);
						} while (componentState != currentState);
						addStrongComponent(component);
					}
				}
			}
		}

		protected void initializeStrongComponent(final ElementState state) {
			final int discoveryIndex = nextDiscoveryIndex++;
			state.discoveryIndex = discoveryIndex;
			state.lowLink = discoveryIndex;
			state.onStack = true;
			componentStack.push(state);
		}

		protected class StrongComponentFrame {
			protected final ElementState state;
			protected final Iterator<BlockFBNetworkElement> destinationElements;

			protected StrongComponentFrame(final ElementState state) {
				this.state = state;
				destinationElements = state.destinationElements.reversed().iterator();
			}
		}

		/**
		 * Add a new strongly-connected component with a single element
		 *
		 * @param state the single element state
		 */
		protected void addStrongComponent(final ElementState state) {
			state.componentEntry = hasOtherComponents(state.componentIndex, state.sourceElements);
			state.componentExit = hasOtherComponents(state.componentIndex, state.destinationElements);
			components.add(List.of(state));
		}

		/**
		 * Add a new strongly-connected component
		 *
		 * @param component a list of strongly-connected elements
		 */
		protected void addStrongComponent(final List<ElementState> component) {
			for (final ElementState state : component) {
				state.componentEntry = hasOtherComponents(state.componentIndex, state.sourceElements);
				state.componentExit = hasOtherComponents(state.componentIndex, state.destinationElements);
			}
			components.add(sortStrongComponent(component));
		}

		protected boolean hasOtherComponents(final int componentIndex,
				final SequencedSet<BlockFBNetworkElement> elements) {
			for (final BlockFBNetworkElement element : elements) {
				final ElementState source = elementStates.get(element);
				if (source.componentIndex != componentIndex) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Sort the given strongly-connected component
		 *
		 * @param component a list of strongly-connected elements
		 * @return an ordered list of strongly-connected elements
		 * @implNote This is based on <em>A fast and effective heuristic for the
		 *           feedback arc set problem</em> by Eades et al. (1993).
		 */
		protected List<ElementState> sortStrongComponent(final List<ElementState> component) {
			if (component.size() < 2) {
				return component;
			}
			final Set<ElementState> remaining = new HashSet<>(component);
			final List<ElementState> sources = new ArrayList<>(component.size());
			final Deque<ElementState> sinks = new ArrayDeque<>(component.size());
			while (!remaining.isEmpty()) {
				final Optional<ElementState> sink = selectSink(remaining);
				if (sink.isPresent()) {
					remaining.remove(sink.get());
					sinks.addFirst(sink.get());
					continue;
				}
				final Optional<ElementState> source = selectSource(remaining);
				if (source.isPresent()) {
					remaining.remove(source.get());
					sources.addLast(source.get());
					continue;
				}
				final ElementState element = selectDegreeCandidate(remaining);
				remaining.remove(element);
				sources.addLast(element);
			}
			sources.addAll(sinks);
			return sources;
		}

		/**
		 * Select a source element from the provided elements
		 *
		 * @param remaining a collection of remaining elements to choose from
		 * @return the selected element or empty if no elements meet the criteria
		 * @implNote The selected element is chosen among the remaining elements that
		 *           have zero in-degree and the one with the highest source weight is
		 *           preferred. The element name is used as a tie breaker.
		 */
		protected Optional<ElementState> selectSource(final Collection<ElementState> remaining) {
			return remaining.stream().filter(element -> componentInDegree(element, remaining) == 0)
					.max(Comparator.comparingInt(ElementState::getSourceWeight).thenComparing(ElementState::getName,
							Comparator.reverseOrder()));
		}

		/**
		 * Select a sink element from the provided elements
		 *
		 * @param remaining a collection of remaining elements to choose from
		 * @return the selected element or empty if no elements meet the criteria
		 * @implNote The selected element is chosen among the remaining elements that
		 *           have zero out-degree and the one with the highest sink weight is
		 *           preferred. The element name is used as a tie breaker.
		 */
		protected Optional<ElementState> selectSink(final Collection<ElementState> remaining) {
			return remaining.stream().filter(element -> componentOutDegree(element, remaining) == 0)
					.max(Comparator.comparingInt(ElementState::getSinkWeight).thenComparing(ElementState::getName));
		}

		/**
		 * Select an element from the provided elements based on the out- and in-degree
		 * difference
		 *
		 * @param remaining a collection of remaining elements to choose from
		 * @return the selected element
		 * @throws NoSuchElementException if remaining is empty
		 * @implNote The selected element is chosen as the one with the highest
		 *           difference between out- and in-degree. The element name is used as
		 *           a tie breaker.
		 */
		protected ElementState selectDegreeCandidate(final Collection<ElementState> remaining) {
			return remaining.stream()
					.max(Comparator
							.comparingLong((final ElementState state) -> componentDegreeDifference(state, remaining))
							.thenComparingInt(TopologyComputer::componentWeightDifference)
							.thenComparing(ElementState::getName, Comparator.reverseOrder()))
					.orElseThrow();
		}

		protected long componentDegreeDifference(final ElementState state, final Collection<ElementState> elements) {
			return componentOutDegree(state, elements) - componentInDegree(state, elements);
		}

		protected long componentInDegree(final ElementState state, final Collection<ElementState> elements) {
			return state.sourceElements.stream().filter(source -> source != state.element).map(elementStates::get)
					.filter(elements::contains).count();
		}

		protected long componentOutDegree(final ElementState state, final Collection<ElementState> elements) {
			return state.destinationElements.stream().filter(destination -> destination != state.element)
					.map(elementStates::get).filter(elements::contains).count();
		}

		protected static int componentWeightDifference(final ElementState state) {
			return state.getSourceWeight() - state.getSinkWeight();
		}

		/**
		 * Populate the topology based on the components
		 */
		protected void populateTopology() {
			// first populate sortedNodes and assign indices
			for (final List<ElementState> component : components) {
				for (final ElementState state : component) {
					final N node = getNode(state.element);
					node.setIndex(sortedNodes.size());
					sortedNodes.add(node);
				}
			}

			// then populate adjacency and cycles
			for (final ElementState source : elementStates.values()) {
				final N sourceNode = getNode(source.element);
				for (final BlockFBNetworkElement destinationElement : source.destinationElements) {
					final N destinationNode = getNode(destinationElement);
					if (sourceNode == destinationNode) {
						sourceNode.setSelfLoop(true);
					} else if (sourceNode.getIndex() < destinationNode.getIndex()) {
						destinationNode.addPredecessor(sourceNode);
						sourceNode.addSuccessor(destinationNode);
					} else {
						destinationNode.addCycleSource(sourceNode);
						sourceNode.addCycleDestination(destinationNode);
					}
				}
			}

			// then assign sequence numbers
			sortedNodes.forEach(node -> node.setSequenceNumber(
					node.getPredecessors().stream().mapToInt(N::getSequenceNumber).min().orElse(0) + 1));

			// then populate entry and exit nodes
			containerEntryElements.stream().map(FBNetworkTopologyGraph.this::getNode).map(Objects::requireNonNull)
					.sorted(Comparator.comparingInt(N::getIndex)).forEachOrdered(entryNodes::add);
			containerExitElements.stream().map(FBNetworkTopologyGraph.this::getNode).map(Objects::requireNonNull)
					.sorted(Comparator.comparingInt(N::getIndex)).forEachOrdered(exitNodes::add);

			// finally mark each entry and exit node
			entryNodes.forEach(destination -> destination.setContainerEntry(true));
			exitNodes.forEach(destination -> destination.setContainerExit(true));
		}

		protected SequencedSet<BlockFBNetworkElement> findContainerSourceElements() {
			return switch (getNetwork().eContainer()) {
			case final BlockFBNetworkElement bfbne -> findSourceElements(bfbne.getInterface().getAllOutputs());
			case final FBType type -> findSourceElements(type.getInterfaceList().getAllOutputs());
			case null, default -> new LinkedHashSet<>();
			};
		}

		protected SequencedSet<BlockFBNetworkElement> findContainerDestinationElements() {
			return switch (getNetwork().eContainer()) {
			case final BlockFBNetworkElement bfbne -> findDestinationElements(bfbne.getInterface().getAllInputs());
			case final FBType type -> findDestinationElements(type.getInterfaceList().getAllInputs());
			case null, default -> new LinkedHashSet<>();
			};
		}

		protected SequencedSet<BlockFBNetworkElement> findSourceElements(final BlockFBNetworkElement element) {
			return findSourceElements(element.getInterface().getAllInputs());
		}

		protected SequencedSet<BlockFBNetworkElement> findDestinationElements(final BlockFBNetworkElement element) {
			return findDestinationElements(element.getInterface().getAllOutputs());
		}

		protected SequencedSet<BlockFBNetworkElement> findSourceElements(final Stream<IInterfaceElement> elements) {
			return elements.map(IInterfaceElement::getInputConnections).flatMap(Collection::stream)
					.filter(FBNetworkTopologyGraph.this::isRelevant).map(Connection::getSourceElement)
					.filter(FBNetworkTopologyGraph.this::isRelevant)
					.collect(Collectors.toCollection(LinkedHashSet::new));
		}

		protected SequencedSet<BlockFBNetworkElement> findDestinationElements(
				final Stream<IInterfaceElement> elements) {
			return elements.map(IInterfaceElement::getOutputConnections).flatMap(Collection::stream)
					.filter(FBNetworkTopologyGraph.this::isRelevant).map(Connection::getDestinationElement)
					.filter(FBNetworkTopologyGraph.this::isRelevant)
					.collect(Collectors.toCollection(LinkedHashSet::new));
		}

		public List<N> getSortedNodes() {
			return sortedNodes;
		}

		public SequencedSet<N> getEntryNodes() {
			return entryNodes;
		}

		public SequencedSet<N> getExitNodes() {
			return exitNodes;
		}
	}
}
