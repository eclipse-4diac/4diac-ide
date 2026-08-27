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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.graph.FBNetworkEventLaneGraph.EventLane;
import org.eclipse.fordiac.ide.model.graph.FBNetworkEventLaneGraph.EventLaneNode;
import org.eclipse.fordiac.ide.model.graph.FBNetworkLaneGraph.Lane;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.test.model.graph.FBNetworkGraphTest;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "nls", "java:S5961" })
class FBNetworkEventLaneGraphTest extends FBNetworkGraphTest {

	@Test
	void testSingle() {
		setupSingle();
		final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
		assertEquals(1, graph.getLaneCount());
		assertLanePosition("A", graph, 0);
		assertLaneEntry("A", graph, false);
		assertLaneExit("A", graph, false);
		assertPassingLanes("A", graph);
		assertForkingLanes("A", graph);
		assertJoiningLanes("A", graph);
	}

	@Test
	void testUnconnected() {
		setupUnconnected();
		final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
		assertEquals(1, graph.getLaneCount());

		assertLanePosition("A", graph, 0);
		assertLaneEntry("A", graph, false);
		assertLaneExit("A", graph, false);
		assertPassingLanes("A", graph);
		assertForkingLanes("A", graph);
		assertJoiningLanes("A", graph);

		assertLanePosition("B", graph, 0);
		assertLaneEntry("B", graph, false);
		assertLaneExit("B", graph, false);
		assertPassingLanes("B", graph);
		assertForkingLanes("B", graph);
		assertJoiningLanes("B", graph);

		assertLanePosition("C", graph, 0);
		assertLaneEntry("C", graph, false);
		assertLaneExit("C", graph, false);
		assertPassingLanes("C", graph);
		assertForkingLanes("C", graph);
		assertJoiningLanes("C", graph);
	}

	@Test
	void testHalfConnected() {
		setupHalfConnected();
		final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
		assertEquals(1, graph.getLaneCount());

		assertLanePosition("A", graph, 0);
		assertLaneEntry("A", graph, true);
		assertLaneExit("A", graph, false);
		assertPassingLanes("A", graph);
		assertForkingLanes("A", graph);
		assertJoiningLanes("A", graph);

		assertLanePosition("B", graph, 0);
		assertLaneEntry("B", graph, false);
		assertLaneExit("B", graph, true);
		assertPassingLanes("B", graph);
		assertForkingLanes("B", graph);
		assertJoiningLanes("B", graph);

		assertLanePosition("C", graph, 0);
		assertLaneEntry("C", graph, false);
		assertLaneExit("C", graph, false);
		assertPassingLanes("C", graph);
		assertForkingLanes("C", graph);
		assertJoiningLanes("C", graph);
	}

	@Test
	void testChain() {
		forEachElementPermutation(this::setupChain, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(1, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, false);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 0);
			assertLaneEntry("C", graph, false);
			assertLaneExit("C", graph, true);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);
		});
	}

	@Test
	void testSplit() {
		forEachElementPermutation(this::setupSplit, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(2, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, false);
			assertPassingLanes("A", graph, 1);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, false);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph, 1);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 1);
			assertLaneEntry("C", graph, true);
			assertLaneExit("C", graph, false);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);

			assertLanePosition("D", graph, 1);
			assertLaneEntry("D", graph, true);
			assertLaneExit("D", graph, true);
			assertPassingLanes("D", graph);
			assertForkingLanes("D", graph);
			assertJoiningLanes("D", graph);
		});
	}

	@Test
	void testSplitLong() {
		forEachElementPermutation(this::setupSplitLong, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(2, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, false);
			assertPassingLanes("A", graph, 1);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, false);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph, 1);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 1);
			assertLaneEntry("C", graph, true);
			assertLaneExit("C", graph, true);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);

			assertLanePosition("D", graph, 1);
			assertLaneEntry("D", graph, true);
			assertLaneExit("D", graph, true);
			assertPassingLanes("D", graph);
			assertForkingLanes("D", graph);
			assertJoiningLanes("D", graph);

			assertLanePosition("E", graph, 1);
			assertLaneEntry("E", graph, true);
			assertLaneExit("E", graph, true);
			assertPassingLanes("E", graph);
			assertForkingLanes("E", graph);
			assertJoiningLanes("E", graph);

			assertLanePosition("F", graph, 1);
			assertLaneEntry("F", graph, true);
			assertLaneExit("F", graph, false);
			assertPassingLanes("F", graph);
			assertForkingLanes("F", graph);
			assertJoiningLanes("F", graph);
		});
	}

	@Test
	void testJoin() {
		forEachElementPermutation(this::setupJoin, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(2, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, true);
			assertPassingLanes("A", graph, 1);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, false);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph, 1);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 0);
			assertLaneEntry("C", graph, true);
			assertLaneExit("C", graph, false);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph, 1);

			assertLanePosition("D", graph, 1);
			assertLaneEntry("D", graph, true);
			assertLaneExit("D", graph, true);
			assertPassingLanes("D", graph, 0);
			assertForkingLanes("D", graph);
			assertJoiningLanes("D", graph);
		});
	}

	@Test
	void testJoinIndependent() {
		forEachElementPermutation(this::setupJoinIndependent, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(2, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, false);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph, 1);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 0);
			assertLaneEntry("C", graph, false);
			assertLaneExit("C", graph, true);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);

			assertLanePosition("D", graph, 1);
			assertLaneEntry("D", graph, false);
			assertLaneExit("D", graph, true);
			assertPassingLanes("D", graph, 0);
			assertForkingLanes("D", graph);
			assertJoiningLanes("D", graph);
		});
	}

	@Test
	void testJoinIndependentLong() {
		forEachElementPermutation(this::setupJoinIndependentLong, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(2, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, false);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph, 1);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 0);
			assertLaneEntry("C", graph, true);
			assertLaneExit("C", graph, true);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);

			assertLanePosition("D", graph, 1);
			assertLaneEntry("D", graph, true);
			assertLaneExit("D", graph, true);
			assertPassingLanes("D", graph, 0);
			assertForkingLanes("D", graph);
			assertJoiningLanes("D", graph);

			assertLanePosition("E", graph, 0);
			assertLaneEntry("E", graph, false);
			assertLaneExit("E", graph, true);
			assertPassingLanes("E", graph);
			assertForkingLanes("E", graph);
			assertJoiningLanes("E", graph);

			assertLanePosition("F", graph, 1);
			assertLaneEntry("F", graph, false);
			assertLaneExit("F", graph, true);
			assertPassingLanes("F", graph, 0);
			assertForkingLanes("F", graph);
			assertJoiningLanes("F", graph);
		});
	}

	@Test
	void testDetour() {
		forEachElementPermutation(this::setupDetour, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(2, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, false);
			assertLaneExit("A", graph, true);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph, 1);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 0);
			assertLaneEntry("C", graph, true);
			assertLaneExit("C", graph, true);
			assertPassingLanes("C", graph, 1);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);

			assertLanePosition("D", graph, 0);
			assertLaneEntry("D", graph, true);
			assertLaneExit("D", graph, true);
			assertPassingLanes("D", graph);
			assertForkingLanes("D", graph);
			assertJoiningLanes("D", graph, 1);

			assertLanePosition("E", graph, 0);
			assertLaneEntry("E", graph, true);
			assertLaneExit("E", graph, false);
			assertPassingLanes("E", graph);
			assertForkingLanes("E", graph);
			assertJoiningLanes("E", graph);
		});
	}

	@Test
	void testInterconnectedLanes() {
		forEachElementPermutation(this::setupInterconnectedLanes, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(3, graph.getLaneCount());

			assertLanePosition("A", graph, 1);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, false);
			assertPassingLanes("A", graph, 2);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph, 0);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 0);
			assertLaneEntry("C", graph, false);
			assertLaneExit("C", graph, true);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);

			assertLanePosition("D", graph, 1);
			assertLaneEntry("D", graph, true);
			assertLaneExit("D", graph, true);
			assertPassingLanes("D", graph, 0);
			assertForkingLanes("D", graph, 2);
			assertJoiningLanes("D", graph);

			assertLanePosition("E", graph, 2);
			assertLaneEntry("E", graph, true);
			assertLaneExit("E", graph, false);
			assertPassingLanes("E", graph);
			assertForkingLanes("E", graph);
			assertJoiningLanes("E", graph);

			assertLanePosition("F", graph, 1);
			assertLaneEntry("F", graph, false);
			assertLaneExit("F", graph, true);
			assertPassingLanes("F", graph, 0);
			assertForkingLanes("F", graph);
			assertJoiningLanes("F", graph);
		});
	}

	@Test
	void testInterconnectedLanesAlternate() {
		forEachElementPermutation(this::setupInterconnectedLanesAlternate, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(2, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, false);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 0);
			assertLaneEntry("C", graph, false);
			assertLaneExit("C", graph, true);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);

			assertLanePosition("D", graph, 1);
			assertLaneEntry("D", graph, true);
			assertLaneExit("D", graph, true);
			assertPassingLanes("D", graph, 0);
			assertForkingLanes("D", graph, 0);
			assertJoiningLanes("D", graph);

			assertLanePosition("E", graph, 1);
			assertLaneEntry("E", graph, true);
			assertLaneExit("E", graph, false);
			assertPassingLanes("E", graph, 0);
			assertForkingLanes("E", graph);
			assertJoiningLanes("E", graph);

			assertLanePosition("F", graph, 1);
			assertLaneEntry("F", graph, false);
			assertLaneExit("F", graph, true);
			assertPassingLanes("F", graph, 0);
			assertForkingLanes("F", graph);
			assertJoiningLanes("F", graph);
		});
	}

	@Test
	void testSelfLoop() {
		forEachElementPermutation(this::setupSelfLoop, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(1, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, true);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, false);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 0);
			assertLaneEntry("C", graph, true);
			assertLaneExit("C", graph, true);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);

			assertLanePosition("D", graph, 0);
			assertLaneEntry("D", graph, true);
			assertLaneExit("D", graph, false);
			assertPassingLanes("D", graph);
			assertForkingLanes("D", graph);
			assertJoiningLanes("D", graph);
		});
	}

	@Test
	void testCycle() {
		forEachElementPermutation(this::setupCycle, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(2, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, true);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph, -1);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, false);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 0);
			assertLaneEntry("C", graph, true);
			assertLaneExit("C", graph, true);
			assertPassingLanes("C", graph, -1);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);

			assertLanePosition("D", graph, 0);
			assertLaneEntry("D", graph, true);
			assertLaneExit("D", graph, false);
			assertPassingLanes("D", graph);
			assertForkingLanes("D", graph);
			assertJoiningLanes("D", graph, -1);
		});
	}

	@Test
	void testCycleWithTail() {
		forEachElementPermutation(this::setupCycleWithTail, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(2, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, false);
			assertLaneExit("A", graph, true);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph, -1);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph, -1);

			assertLanePosition("C", graph, 0);
			assertLaneEntry("C", graph, true);
			assertLaneExit("C", graph, true);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph);

			assertLanePosition("D", graph, 0);
			assertLaneEntry("D", graph, true);
			assertLaneExit("D", graph, false);
			assertPassingLanes("D", graph);
			assertForkingLanes("D", graph);
			assertJoiningLanes("D", graph);
		});
	}

	@Test
	void testMultipleCycles() {
		forEachElementPermutation(this::setupMultipleCycles, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(3, graph.getLaneCount());

			assertLanePosition("A", graph, 1);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, true);
			assertPassingLanes("A", graph, -2, 0);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 1);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph, -2, 0);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 1);
			assertLaneEntry("C", graph, true);
			assertLaneExit("C", graph, false);
			assertPassingLanes("C", graph, 0);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph, -2);

			assertLanePosition("D", graph, 0);
			assertLaneEntry("D", graph, false);
			assertLaneExit("D", graph, true);
			assertPassingLanes("D", graph);
			assertForkingLanes("D", graph, -2, 1);
			assertJoiningLanes("D", graph);

			assertLanePosition("E", graph, 0);
			assertLaneEntry("E", graph, true);
			assertLaneExit("E", graph, true);
			assertPassingLanes("E", graph, -2, 1);
			assertForkingLanes("E", graph, 1);
			assertJoiningLanes("E", graph);

			assertLanePosition("F", graph, 0);
			assertLaneEntry("F", graph, true);
			assertLaneExit("F", graph, true);
			assertPassingLanes("F", graph);
			assertForkingLanes("F", graph);
			assertJoiningLanes("F", graph);

			assertLanePosition("G", graph, 0);
			assertLaneEntry("G", graph, true);
			assertLaneExit("G", graph, true);
			assertPassingLanes("G", graph);
			assertForkingLanes("G", graph, -1);
			assertJoiningLanes("G", graph);

			assertLanePosition("H", graph, 0);
			assertLaneEntry("H", graph, true);
			assertLaneExit("H", graph, false);
			assertPassingLanes("H", graph);
			assertForkingLanes("H", graph);
			assertJoiningLanes("H", graph, -1);
		});
	}

	@Test
	void testInterleavedCycles() {
		forEachElementPermutation(this::setupInterleavedCycles, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(4, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, true);
			assertLaneExit("A", graph, true);
			assertPassingLanes("A", graph, 1);
			assertForkingLanes("A", graph, -2);
			assertJoiningLanes("A", graph);

			assertLanePosition("B", graph, 1);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, true);
			assertPassingLanes("B", graph, 0, -2);
			assertForkingLanes("B", graph, -3);
			assertJoiningLanes("B", graph);

			assertLanePosition("C", graph, 1);
			assertLaneEntry("C", graph, true);
			assertLaneExit("C", graph, false);
			assertPassingLanes("C", graph);
			assertForkingLanes("C", graph);
			assertJoiningLanes("C", graph, -2, 0, -3);

			assertLanePosition("D", graph, 0);
			assertLaneEntry("D", graph, false);
			assertLaneExit("D", graph, true);
			assertPassingLanes("D", graph);
			assertForkingLanes("D", graph);
			assertJoiningLanes("D", graph);

			assertLanePosition("E", graph, 1);
			assertLaneEntry("E", graph, false);
			assertLaneExit("E", graph, true);
			assertPassingLanes("E", graph, 0);
			assertForkingLanes("E", graph);
			assertJoiningLanes("E", graph);
		});
	}

	@Test
	void testInterleavedUnconnected() {
		setupInterleavedUnconnected();
		final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
		assertEquals(1, graph.getLaneCount());

		assertLanePosition("A", graph, 0);
		assertLaneEntry("A", graph, false);
		assertLaneExit("A", graph, true);
		assertPassingLanes("A", graph);
		assertForkingLanes("A", graph);
		assertJoiningLanes("A", graph);

		assertLanePosition("B", graph, 0);
		assertLaneEntry("B", graph, false);
		assertLaneExit("B", graph, true);
		assertPassingLanes("B", graph);
		assertForkingLanes("B", graph);
		assertJoiningLanes("B", graph);

		assertLanePosition("C", graph, 0);
		assertLaneEntry("C", graph, true);
		assertLaneExit("C", graph, false);
		assertPassingLanes("C", graph);
		assertForkingLanes("C", graph);
		assertJoiningLanes("C", graph);

		assertLanePosition("D", graph, 0);
		assertLaneEntry("D", graph, true);
		assertLaneExit("D", graph, false);
		assertPassingLanes("D", graph);
		assertForkingLanes("D", graph);
		assertJoiningLanes("D", graph);
	}

	@Test
	void testUntypedSubApp() {
		forEachElementPermutation(this::setupUntypedSubApp, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(1, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, false);
			assertLaneExit("A", graph, true);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("SubApp", graph, 0);
			assertLaneEntry("SubApp", graph, true);
			assertLaneExit("SubApp", graph, true);
			assertPassingLanes("SubApp", graph);
			assertForkingLanes("SubApp", graph);
			assertJoiningLanes("SubApp", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, false);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			final EventLaneNode subNode = graph.getNode(network.getSubAppNamed("SubApp"));
			final FBNetworkEventLaneGraph subGraph = (FBNetworkEventLaneGraph) subNode.loadSubgraph();
			assertEquals(4, subGraph.getLaneCount());

			assertLanePosition("C", subGraph, 2);
			assertLaneEntry("C", subGraph, false);
			assertLaneExit("C", subGraph, false);
			assertPassingLanes("C", subGraph, 0);
			assertForkingLanes("C", subGraph, 3);
			assertJoiningLanes("C", subGraph, 1);
		});
	}

	@Test
	void testNestedUntypedSubApp() {
		forEachElementPermutation(this::setupNestedUntypedSubApp, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(1, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, false);
			assertLaneExit("A", graph, true);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("SubApp", graph, 0);
			assertLaneEntry("SubApp", graph, true);
			assertLaneExit("SubApp", graph, true);
			assertPassingLanes("SubApp", graph);
			assertForkingLanes("SubApp", graph);
			assertJoiningLanes("SubApp", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, false);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			final EventLaneNode subNode = graph.getNode(network.getSubAppNamed("SubApp"));
			final FBNetworkEventLaneGraph subGraph = (FBNetworkEventLaneGraph) subNode.loadSubgraph();
			assertEquals(4, subGraph.getLaneCount());

			assertLanePosition("NestedSubApp", subGraph, 2);
			assertLaneEntry("NestedSubApp", subGraph, false);
			assertLaneExit("NestedSubApp", subGraph, false);
			assertPassingLanes("NestedSubApp", subGraph, 0);
			assertForkingLanes("NestedSubApp", subGraph, 3);
			assertJoiningLanes("NestedSubApp", subGraph, 1);

			final EventLaneNode nestedSubNode = subGraph
					.getNode(network.getSubAppNamed("SubApp").getSubAppNetwork().getSubAppNamed("NestedSubApp"));
			final FBNetworkEventLaneGraph nestedSubGraph = (FBNetworkEventLaneGraph) nestedSubNode.loadSubgraph();
			assertEquals(7, nestedSubGraph.getLaneCount());

			assertLanePosition("C", nestedSubGraph, 5);
			assertLaneEntry("C", nestedSubGraph, false);
			assertLaneExit("C", nestedSubGraph, false);
			assertPassingLanes("C", nestedSubGraph, 0);
			assertForkingLanes("C", nestedSubGraph, 6);
			assertJoiningLanes("C", nestedSubGraph, 4);
		});
	}

	@Test
	void testUntypedSubAppCycle() {
		forEachElementPermutation(this::setupUntypedSubAppCycle, () -> {
			final FBNetworkEventLaneGraph graph = new FBNetworkEventLaneGraph(network);
			assertEquals(1, graph.getLaneCount());

			assertLanePosition("A", graph, 0);
			assertLaneEntry("A", graph, false);
			assertLaneExit("A", graph, true);
			assertPassingLanes("A", graph);
			assertForkingLanes("A", graph);
			assertJoiningLanes("A", graph);

			assertLanePosition("SubApp", graph, 0);
			assertLaneEntry("SubApp", graph, true);
			assertLaneExit("SubApp", graph, true);
			assertPassingLanes("SubApp", graph);
			assertForkingLanes("SubApp", graph);
			assertJoiningLanes("SubApp", graph);

			assertLanePosition("B", graph, 0);
			assertLaneEntry("B", graph, true);
			assertLaneExit("B", graph, false);
			assertPassingLanes("B", graph);
			assertForkingLanes("B", graph);
			assertJoiningLanes("B", graph);

			final EventLaneNode subNode = graph.getNode(network.getSubAppNamed("SubApp"));
			final FBNetworkEventLaneGraph subGraph = (FBNetworkEventLaneGraph) subNode.loadSubgraph();
			assertEquals(5, subGraph.getLaneCount());

			assertLanePosition("C", subGraph, 2);
			assertLaneEntry("C", subGraph, false);
			assertLaneExit("C", subGraph, true);
			assertPassingLanes("C", subGraph, 0);
			assertForkingLanes("C", subGraph, -3);
			assertJoiningLanes("C", subGraph, 1);

			assertLanePosition("D", subGraph, 2);
			assertLaneEntry("D", subGraph, true);
			assertLaneExit("D", subGraph, false);
			assertPassingLanes("D", subGraph, 0);
			assertForkingLanes("D", subGraph, 4);
			assertJoiningLanes("D", subGraph, -3);
		});
	}

	@Test
	void testPerformanceLinearChain() {
		assertTimeoutPreemptively(Duration.ofSeconds(10),
				() -> timeGraph(100, () -> setupPerformanceLinearChain(1000)));
	}

	@Test
	void testPerformanceDoubleLinearChain() {
		assertTimeoutPreemptively(Duration.ofSeconds(10),
				() -> timeGraph(100, () -> setupPerformanceDoubleLinearChain(1000)));
	}

	@Test
	void testPerformanceBranchingChain() {
		assertTimeoutPreemptively(Duration.ofSeconds(10),
				() -> timeGraph(100, () -> setupPerformanceBranchingChain(100)));
	}

	@Test
	void testPerformanceLoop() {
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> timeGraph(100, () -> setupPerformanceLoop(100)));
	}

	protected void timeGraph(final int count, final Runnable setup) {
		time(count, setup, () -> new FBNetworkEventLaneGraph(network));
	}

	private static void assertLanePosition(final String elementName, final FBNetworkEventLaneGraph graph,
			final int expected) {
		assertEquals(expected, graph.getNode((BlockFBNetworkElement) graph.getNetwork().getElementNamed(elementName))
				.getLane().getPosition());
	}

	private static void assertLaneEntry(final String elementName, final FBNetworkEventLaneGraph graph,
			final boolean expected) {
		if (expected) {
			assertTrue(graph.getNode((BlockFBNetworkElement) graph.getNetwork().getElementNamed(elementName))
					.hasLaneEntry());
		} else {
			assertFalse(graph.getNode((BlockFBNetworkElement) graph.getNetwork().getElementNamed(elementName))
					.hasLaneEntry());
		}
	}

	private static void assertLaneExit(final String elementName, final FBNetworkEventLaneGraph graph,
			final boolean expected) {
		if (expected) {
			assertTrue(graph.getNode((BlockFBNetworkElement) graph.getNetwork().getElementNamed(elementName))
					.hasLaneExit());
		} else {
			assertFalse(graph.getNode((BlockFBNetworkElement) graph.getNetwork().getElementNamed(elementName))
					.hasLaneExit());
		}
	}

	private static void assertPassingLanes(final String elementName, final FBNetworkEventLaneGraph graph,
			final int... expected) {
		assertLanes(expected, graph.getNode((BlockFBNetworkElement) graph.getNetwork().getElementNamed(elementName))
				.getPassingLanes());
	}

	private static void assertForkingLanes(final String elementName, final FBNetworkEventLaneGraph graph,
			final int... expected) {
		assertLanes(expected, graph.getNode((BlockFBNetworkElement) graph.getNetwork().getElementNamed(elementName))
				.getForkingLanes());
	}

	private static void assertJoiningLanes(final String elementName, final FBNetworkEventLaneGraph graph,
			final int... expected) {
		assertLanes(expected, graph.getNode((BlockFBNetworkElement) graph.getNetwork().getElementNamed(elementName))
				.getJoiningLanes());
	}

	private static void assertLanes(final int[] expected, final Set<EventLane> actual) {
		assertEquals(Arrays.stream(expected).boxed().collect(Collectors.toSet()),
				actual.stream().map(FBNetworkEventLaneGraphTest::getEncodedLanePosition).collect(Collectors.toSet()));

		assertEquals(Arrays.stream(expected).map(Math::abs).boxed().collect(Collectors.toSet()),
				actual.stream().map(Lane::getPosition).collect(Collectors.toSet()));
	}

	private static int getEncodedLanePosition(final EventLane lane) {
		return lane.isBackward() ? -lane.getPosition() : lane.getPosition();
	}
}
