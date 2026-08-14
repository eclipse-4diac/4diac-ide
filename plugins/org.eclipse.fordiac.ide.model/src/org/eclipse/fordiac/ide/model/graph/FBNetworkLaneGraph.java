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

import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;

/**
 * A lane graph for an FB network
 */
public abstract class FBNetworkLaneGraph<N extends FBNetworkLaneGraph<N, L>.LaneNode, L extends FBNetworkLaneGraph<N, L>.Lane>
		extends FBNetworkTopologyGraph<N> {

	/**
	 * A lane representing connections between nodes
	 */
	public abstract class Lane {
		private int position;
		private int length = 1;
		private boolean backward;

		/**
		 * Get the lane position
		 *
		 * @return the lane position
		 */
		public int getPosition() {
			return position;
		}

		protected void setPosition(final int position) {
			this.position = position;
		}

		/**
		 * Get whether the lane represents a backward connection
		 *
		 * @return true if backward, false otherwise
		 */
		public boolean isBackward() {
			return backward;
		}

		protected void setBackward(final boolean backward) {
			this.backward = backward;
		}

		/**
		 * Get the length of the lane
		 *
		 * @return the length
		 */
		public int getLength() {
			return length;
		}

		protected int incrementLength() {
			return this.length++;
		}

		/**
		 * Get the containing graph
		 *
		 * @return the containing graph
		 */
		public FBNetworkLaneGraph<N, L> getGraph() {
			return FBNetworkLaneGraph.this;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return String.format("%s [position=%s]", getClass().getName(), Integer.valueOf(position)); //$NON-NLS-1$
		}
	}

	/**
	 * A node with additional lane information
	 */
	public abstract class LaneNode extends TopologyNode {
		private L lane;
		private boolean laneEntry;
		private boolean laneExit;
		private final Set<L> passingLanes = new HashSet<>();
		private final Set<L> forkingLanes = new HashSet<>();
		private final Set<L> joiningLanes = new HashSet<>();

		protected LaneNode(final BlockFBNetworkElement element) {
			super(element);
		}

		/**
		 * Get the assigned lane
		 *
		 * @return the assigned lane or null if none
		 */
		public L getLane() {
			return lane;
		}

		protected void setLane(final L lane) {
			this.lane = lane;
		}

		/**
		 * Get whether the node has a directly incoming lane
		 *
		 * @return true if there is a lane entry, false otherwise
		 */
		public boolean hasLaneEntry() {
			return laneEntry;
		}

		protected void setLaneEntry(final boolean laneEntry) {
			this.laneEntry = laneEntry;
		}

		/**
		 * Get whether the node has a directly outgoing lane
		 *
		 * @return true if there is a lane exit, false otherwise
		 */
		public boolean hasLaneExit() {
			return laneExit;
		}

		protected void setLaneExit(final boolean laneExit) {
			this.laneExit = laneExit;
		}

		/**
		 * Get the passing lanes
		 *
		 * @return a set of passing lanes
		 */
		public Set<L> getPassingLanes() {
			return Collections.unmodifiableSet(passingLanes);
		}

		/**
		 * Get the forking lanes
		 *
		 * @return a set of forking lanes
		 */
		public Set<L> getForkingLanes() {
			return Collections.unmodifiableSet(forkingLanes);
		}

		/**
		 * Get the joining lanes
		 *
		 * @return a set of joining lanes
		 */
		public Set<L> getJoiningLanes() {
			return Collections.unmodifiableSet(joiningLanes);
		}

		protected void addPassingLane(final L lane) {
			passingLanes.add(lane);
		}

		protected void addForkingLane(final L lane) {
			forkingLanes.add(lane);
		}

		protected void addJoiningLane(final L lane) {
			joiningLanes.add(lane);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public FBNetworkLaneGraph<N, L> getGraph() {
			return FBNetworkLaneGraph.this;
		}
	}

	private final int laneCount;

	/**
	 * Create a new FB network lane graph
	 *
	 * @param network the network
	 * @param parent  the parent graph or null if none
	 */
	protected FBNetworkLaneGraph(final FBNetwork network, final N parent) {
		super(network, parent);
		laneCount = createLaneAssignmentComputer().assignLanes();
	}

	/**
	 * Get the number of lanes, including parents
	 *
	 * @return number of lanes
	 */
	public int getLaneCount() {
		return laneCount;
	}

	/**
	 * Create a new lane
	 *
	 * @return the new lane, must not be null
	 */
	protected abstract L createLane();

	/**
	 * Create a new lane assignment computer
	 *
	 * @return a lane assignment computer
	 * @implNote This can be overridden by subclasses to provide custom behavior.
	 */
	protected LaneAssignmentComputer createLaneAssignmentComputer() {
		return new LaneAssignmentComputer();
	}

	/**
	 * A helper class to assign lanes to nodes
	 */
	protected class LaneAssignmentComputer {
		private final Set<L> activeLanes = new HashSet<>();
		private final BitSet freePositions = new BitSet();
		private int nextPosition = 0;
		private L containerEntryLane;

		/**
		 * Assign lanes to nodes
		 *
		 * @return the total number of lanes
		 */
		public int assignLanes() {
			// initialize with parent information
			final N parent = getParent();
			if (parent != null) {
				// keep lanes of sub-graph to the right of parent graph
				nextPosition = parent.getGraph().getLaneCount();
				// continue lanes from parents
				continueParentLanes(findAncestorForLaneContinuation(parent));
			}

			// initialize container entry lane
			if (!getEntryNodes().isEmpty()) {
				containerEntryLane = openLane();
			}

			// assign nodes
			for (final N node : getSortedNodes()) {
				assignLane(node);
			}

			// handle container exit
			if (!getExitNodes().isEmpty()) {
				final L containerExitLane = createLane();
				containerExitLane.setPosition(nextPosition++);
				// add forking lane to all exit nodes
				for (final N node : getExitNodes()) {
					node.addForkingLane(containerExitLane);
				}
				// add passing lane to all nodes starting after the first exit node
				addPassingLaneBetween(getExitNodes().getFirst(), null, containerExitLane);
			}

			return nextPosition;
		}

		/**
		 * Continue lanes from provided parent in the current graph
		 *
		 * @param parent the parent or null if none
		 * @implNote This adds all ongoing lanes from the parent to the current active
		 *           lanes, so they are continued in this sub-graph.
		 */
		protected void continueParentLanes(final N parent) {
			if (parent == null) {
				return;
			}
			// continue passing and forking lanes from parent node
			activeLanes.addAll(parent.getPassingLanes());
			activeLanes.addAll(parent.getForkingLanes());
			// also continue lane from parent node if still active
			if (parent.hasLaneExit()) {
				activeLanes.add(parent.getLane());
			}
		}

		/**
		 * Find ancestor for continuing lanes starting from the provided parent
		 *
		 * @param parent the parent
		 * @return an ancestor with lanes that should be continued in this sub-graph
		 * @implNote This skips parents if they are the last node in their graphs, since
		 *           those have no active lanes that should be continued.
		 */
		protected N findAncestorForLaneContinuation(N parent) {
			while (parent != null && parent.getGraph().getSortedNodes().getLast() == parent) {
				parent = parent.getGraph().getParent();
			}
			return parent;
		}

		/**
		 * Assign a lane to a node
		 *
		 * @param node the node
		 */
		protected void assignLane(final N node) {
			final Optional<N> chosenPredecessor = findSuitablePredecessor(node);
			if (chosenPredecessor.isPresent()) {
				continueLane(node, chosenPredecessor.get());
				handleForkAndJoin(node, chosenPredecessor.get());
			} else {
				node.setLane(openLane());
				handleForkAndJoin(node, null);
			}
			handleCycles(node);
			handleContainerEntry(node);

			closeStaleLanes(node);
			addPassingLanes(node);
		}

		/**
		 * Find a suitable predecessor for the provided node
		 *
		 * @param node the node
		 * @return a suitable predecessor or empty if none
		 * @implNote This looks for a predecessor with the longest lane length among all
		 *           predecessors where the node is the first successor.
		 */
		protected Optional<N> findSuitablePredecessor(final N node) {
			return node.getPredecessors().stream().filter(candidate -> candidate.getSuccessors().getFirst() == node)
					.max(Comparator.comparingInt((final N candidate) -> candidate.getLane().getLength())
							.thenComparingInt(N::getIndex));
		}

		/**
		 * Continue a lane directly from predecessor to node
		 *
		 * @param node        a node
		 * @param predecessor its predecessor
		 */
		protected void continueLane(final N node, final N predecessor) {
			predecessor.getLane().incrementLength();
			node.setLane(predecessor.getLane());
			predecessor.setLaneExit(true);
			node.setLaneEntry(true);
		}

		/**
		 * Close stale lanes after a node
		 *
		 * @param node a node
		 */
		protected void closeStaleLanes(final N node) {
			// close lane of current node if there are no successors
			if (node.getSuccessors().isEmpty()) {
				closeLane(node.getLane());
			} else {
				node.setLaneExit(true);
			}

			// close lanes of predecessors that have no successor on same lane
			for (final N predecessor : node.getPredecessors()) {
				final L firstSuccessorLane = predecessor.getSuccessors().getFirst().getLane();
				if (firstSuccessorLane != null && firstSuccessorLane != predecessor.getLane()) {
					closeLane(predecessor.getLane());
				}
			}
		}

		/**
		 * Add passing lanes to a node based on the currently active lanes
		 *
		 * @param node a node
		 */
		protected void addPassingLanes(final N node) {
			activeLanes.stream().filter(Predicate.not(node.getLane()::equals)).forEach(node::addPassingLane);
		}

		/**
		 * Handle forks and joins for a provided node, excluding its chosen predecessor
		 *
		 * @param node              a node
		 * @param chosenPredecessor its chosen predecessor
		 */
		protected void handleForkAndJoin(final N node, final N chosenPredecessor) {
			for (final N predecessor : node.getPredecessors()) {
				if (predecessor == chosenPredecessor) {
					continue; // skip chosen
				}
				if (predecessor.getSuccessors().getFirst() != node) {
					predecessor.addForkingLane(handleFork(node, chosenPredecessor, predecessor));
				} else {
					node.addJoiningLane(predecessor.getLane());
				}
			}
		}

		/**
		 * Handle a fork from a predecessor to a node
		 *
		 * @param node              a node
		 * @param chosenPredecessor its chosen predecessor
		 * @param predecessor       a predecessor
		 * @return the used lane
		 * @implNote This navigates around the chosen predecessor of the node and
		 *           blocked positions occupied by other nodes between the node and its
		 *           predecessor if necessary.
		 */
		protected L handleFork(final N node, final N chosenPredecessor, final N predecessor) {
			L lane = node.getLane();
			final BitSet blockedPositions = getBlockedPositionsBetween(predecessor, node);
			if (blockedPositions.get(node.getLane().getPosition())) {
				if (chosenPredecessor != null && chosenPredecessor.getIndex() > predecessor.getIndex()) {
					// chosen predecessor is blocking our current lane -> add detour lane
					lane = openLane(blockedPositions);
					node.addJoiningLane(lane);
					closeLane(lane);
				} else {
					// move lane to new position that is not blocked
					final int newPosition = allocatePosition(blockedPositions);
					freePositions.set(lane.getPosition());
					lane.setPosition(newPosition);
					node.setLaneEntry(true);
				}
			} else {
				node.setLaneEntry(true);
			}
			addPassingLaneBetween(predecessor, node, lane);
			return lane;
		}

		/**
		 * Handle cycles for the provided node
		 *
		 * @param node a node
		 * @implNote This creates backward lanes for the provided node, navigating
		 *           around any blocked positions between the node and its cycle
		 *           destination.
		 */
		protected void handleCycles(final N node) {
			for (final N destination : node.getCycleDestinations()) {
				final BitSet blockedPositions = getBlockedPositionsBetween(destination, node);
				addBlockedPositions(blockedPositions, destination);
				addBlockedPositions(blockedPositions, node);
				final L lane = openLane(blockedPositions);
				lane.setBackward(true);
				node.addJoiningLane(lane);
				destination.addForkingLane(lane);
				closeLane(lane);
				addPassingLaneBetween(destination, node, lane);
			}
		}

		/**
		 * Handle incoming connections from the container to the provided node
		 *
		 * @param node a node
		 * @implNote This adds a joining lane from the container entry lane and closes
		 *           the entry lane if the node is the last entry.
		 */
		protected void handleContainerEntry(final N node) {
			if (node.hasContainerEntry()) {
				node.addJoiningLane(containerEntryLane);
				// close container entry lane if we are the last
				if (node == getEntryNodes().getLast()) {
					activeLanes.remove(containerEntryLane);
				}
			}
		}

		/**
		 * Add a lane as a passing lane for each node between begin and end, both
		 * exclusive
		 *
		 * @param begin the beginning node
		 * @param end   the end node
		 * @param lane  the lane
		 */
		protected void addPassingLaneBetween(final N begin, final N end, final L lane) {
			final int fromIndex = begin != null ? begin.getIndex() + 1 : 1;
			final int toIndex = end != null ? end.getIndex() : getSortedNodes().size();
			for (final N node : getSortedNodes().subList(fromIndex, toIndex)) {
				node.addPassingLane(lane);
			}
		}

		/**
		 * Get all occupied positions for each node between begin and end, both
		 * exclusive
		 *
		 * @param begin the beginning node
		 * @param end   the end node
		 * @return a bit set with the blocked positions
		 */
		protected BitSet getBlockedPositionsBetween(final N begin, final N end) {
			final BitSet result = new BitSet(nextPosition);
			final int fromIndex = begin != null ? begin.getIndex() + 1 : 1;
			final int toIndex = end != null ? end.getIndex() : getSortedNodes().size();
			for (final N node : getSortedNodes().subList(fromIndex, toIndex)) {
				addBlockedPositions(result, node);
			}
			return result;
		}

		protected void addBlockedPositions(final BitSet result, final N node) {
			if (node.getLane() != null) {
				result.set(node.getLane().getPosition());
			}
			node.getForkingLanes().stream().mapToInt(L::getPosition).forEachOrdered(result::set);
			node.getJoiningLanes().stream().mapToInt(L::getPosition).forEachOrdered(result::set);
		}

		/**
		 * Open a new lane and add it to the active lanes
		 *
		 * @return a new lane
		 */
		protected L openLane() {
			return openLane(null);
		}

		/**
		 * Open a new lane, excluding the provided positions, and add it to the active
		 * lanes
		 *
		 * @param blockedPositions the positions to exclude
		 * @return a new lane
		 */
		protected L openLane(final BitSet blockedPositions) {
			final L lane = createLane();
			lane.setPosition(allocatePosition(blockedPositions));
			activeLanes.add(lane);
			return lane;
		}

		protected int allocatePosition(final BitSet blockedPositions) {
			for (int i = freePositions.nextSetBit(0); i >= 0; i = freePositions.nextSetBit(i + 1)) {
				if (blockedPositions == null || !blockedPositions.get(i)) {
					freePositions.clear(i);
					return i;
				}
			}
			return nextPosition++;
		}

		/**
		 * Close a lane, removing it from the active lanes and allowing it to be re-used
		 *
		 * @param lane a lane
		 */
		protected void closeLane(final L lane) {
			if (activeLanes.remove(lane)) {
				freePositions.set(lane.getPosition());
			}
		}

		protected Set<L> getActiveLanes() {
			return activeLanes;
		}

		protected BitSet getFreePositions() {
			return freePositions;
		}

		protected int getNextPosition() {
			return nextPosition;
		}

		protected L getContainerEntryLane() {
			return containerEntryLane;
		}
	}
}
