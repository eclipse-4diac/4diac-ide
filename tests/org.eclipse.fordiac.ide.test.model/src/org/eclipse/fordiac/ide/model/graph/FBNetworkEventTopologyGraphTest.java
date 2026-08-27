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
import java.util.List;

import org.eclipse.fordiac.ide.model.graph.FBNetworkEventTopologyGraph.EventTopologyNode;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.test.model.graph.FBNetworkGraphTest;
import org.junit.jupiter.api.Test;

@SuppressWarnings("nls")
class FBNetworkEventTopologyGraphTest extends FBNetworkGraphTest {

	@Test
	void testEmpty() {
		final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
		assertNodes(List.of(), graph);
	}

	@Test
	void testSingle() {
		setupSingle();
		final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
		assertNodes(List.of("A"), graph);
		assertSequenceNumber(1, "A", graph);
	}

	@Test
	void testUnconnected() {
		setupUnconnected();
		final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
		assertNodes(List.of("A", "B", "C"), graph);
		assertSequenceNumber(1, "A", graph);
		assertSequenceNumber(1, "B", graph);
		assertSequenceNumber(1, "C", graph);
	}

	@Test
	void testHalfConnected() {
		setupHalfConnected();
		final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
		assertNodes(List.of("B", "A", "C"), graph);
		assertSequenceNumber(2, "A", graph);
		assertSequenceNumber(1, "B", graph);
		assertSequenceNumber(1, "C", graph);
	}

	@Test
	void testMultipleComponents() {
		setupMultipleComponents();
		final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
		final FBNetworkEventTopologyGraph repeatedGraph = new FBNetworkEventTopologyGraph(network);
		assertNodes(List.of("B", "A", "E", "D", "C", "F"), graph);
		assertNodes(List.of("B", "A", "E", "D", "C", "F"), repeatedGraph);
		assertSequenceNumber(2, "A", graph);
		assertSequenceNumber(1, "B", graph);
		assertSequenceNumber(3, "C", graph);
		assertSequenceNumber(2, "D", graph);
		assertSequenceNumber(1, "E", graph);
		assertSequenceNumber(1, "F", graph);
	}

	@Test
	void testChain() {
		forEachElementPermutation(this::setupChain, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("C", "B", "A"), graph);
			assertSequenceNumber(3, "A", graph);
			assertSequenceNumber(2, "B", graph);
			assertSequenceNumber(1, "C", graph);
		});
	}

	@Test
	void testSplit() {
		forEachElementPermutation(this::setupSplit, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("B", "A", "D", "C"), graph);
			assertSequenceNumber(2, "A", graph);
			assertSequenceNumber(1, "B", graph);
			assertSequenceNumber(3, "C", graph);
			assertSequenceNumber(2, "D", graph);
		});
	}

	@Test
	void testSplitLong() {
		forEachElementPermutation(this::setupSplitLong, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("B", "A", "D", "C", "E", "F"), graph);
			assertSequenceNumber(2, "A", graph);
			assertSequenceNumber(1, "B", graph);
			assertSequenceNumber(3, "C", graph);
			assertSequenceNumber(2, "D", graph);
			assertSequenceNumber(4, "E", graph);
			assertSequenceNumber(5, "F", graph);
		});
	}

	@Test
	void testJoin() {
		forEachElementPermutation(this::setupJoin, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("B", "A", "D", "C"), graph);
			assertSequenceNumber(2, "A", graph);
			assertSequenceNumber(1, "B", graph);
			assertSequenceNumber(3, "C", graph);
			assertSequenceNumber(2, "D", graph);
		});
	}

	@Test
	void testJoinIndependent() {
		forEachElementPermutation(this::setupJoinIndependent, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("C", "B", "D", "A"), graph);
			assertSequenceNumber(2, "A", graph);
			assertSequenceNumber(2, "B", graph);
			assertSequenceNumber(1, "C", graph);
			assertSequenceNumber(1, "D", graph);
		});
	}

	@Test
	void testJoinIndependentLong() {
		forEachElementPermutation(this::setupJoinIndependentLong, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("E", "C", "B", "F", "D", "A"), graph);
			assertSequenceNumber(3, "A", graph);
			assertSequenceNumber(3, "B", graph);
			assertSequenceNumber(2, "C", graph);
			assertSequenceNumber(2, "D", graph);
			assertSequenceNumber(1, "E", graph);
			assertSequenceNumber(1, "F", graph);
		});
	}

	@Test
	void testRootPathLengths() {
		forEachElementPermutation(this::setupRootPathLengths, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("D", "C", "B", "A"), graph);
			assertSequenceNumber(2, "A", graph);
			assertSequenceNumber(1, "B", graph);
			assertSequenceNumber(2, "C", graph);
			assertSequenceNumber(1, "D", graph);
		});
	}

	@Test
	void testRootPathLengthTie() {
		forEachElementPermutation(this::setupRootPathLengthTie, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			final FBNetworkEventTopologyGraph repeatedGraph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("B", "C", "A"), graph);
			assertNodes(List.of("B", "C", "A"), repeatedGraph);
			assertSequenceNumber(2, "A", graph);
			assertSequenceNumber(1, "B", graph);
			assertSequenceNumber(1, "C", graph);
		});
	}

	@Test
	void testInterconnectedLanes() {
		forEachElementPermutation(this::setupInterconnectedLanes, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("C", "B", "F", "D", "A", "E"), graph);
			assertSequenceNumber(3, "A", graph);
			assertSequenceNumber(2, "B", graph);
			assertSequenceNumber(1, "C", graph);
			assertSequenceNumber(2, "D", graph);
			assertSequenceNumber(3, "E", graph);
			assertSequenceNumber(1, "F", graph);
		});
	}

	@Test
	void testInterconnectedLanesAlternate() {
		forEachElementPermutation(this::setupInterconnectedLanesAlternate, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("C", "B", "F", "D", "E", "A"), graph);
			assertSequenceNumber(3, "A", graph);
			assertSequenceNumber(2, "B", graph);
			assertSequenceNumber(1, "C", graph);
			assertSequenceNumber(2, "D", graph);
			assertSequenceNumber(3, "E", graph);
			assertSequenceNumber(1, "F", graph);
		});
	}

	@Test
	void testCycle() {
		forEachElementPermutation(this::setupCycle, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("B", "A", "C", "D"), graph);
			assertSequenceNumber(2, "A", graph);
			assertSequenceNumber(1, "B", graph);
			assertSequenceNumber(3, "C", graph);
			assertSequenceNumber(4, "D", graph);
		});
	}

	@Test
	void testCycleWithTail() {
		forEachElementPermutation(this::setupCycleWithTail, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("A", "B", "C", "D"), graph);

			assertNodes(List.of(), getNode("A", graph).getPredecessors(), graph);
			assertNodes(List.of("A"), getNode("B", graph).getPredecessors(), graph);
			assertNodes(List.of("B"), getNode("C", graph).getPredecessors(), graph);
			assertNodes(List.of("C"), getNode("D", graph).getPredecessors(), graph);

			assertNodes(List.of("B"), getNode("A", graph).getCycleSources(), graph);
			assertNodes(List.of(), getNode("B", graph).getCycleSources(), graph);
			assertNodes(List.of("A"), getNode("B", graph).getCycleDestinations(), graph);
			assertNodes(List.of(), getNode("C", graph).getCycleDestinations(), graph);
			assertNodes(List.of(), getNode("D", graph).getCycleDestinations(), graph);
		});
	}

	@Test
	void testMultipleCycles() {
		forEachElementPermutation(this::setupMultipleCycles, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("D", "A", "E", "B", "C", "F", "G", "H"), graph);
			assertSequenceNumber(2, "A", graph);
			assertSequenceNumber(3, "B", graph);
			assertSequenceNumber(4, "C", graph);
			assertSequenceNumber(1, "D", graph);
			assertSequenceNumber(2, "E", graph);
			assertSequenceNumber(3, "F", graph);
			assertSequenceNumber(4, "G", graph);
			assertSequenceNumber(5, "H", graph);
		});
	}

	@Test
	void testInterleavedCycles() {
		forEachElementPermutation(this::setupInterleavedCycles, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("D", "E", "A", "B", "C"), graph);
			assertSequenceNumber(2, "A", graph);
			assertSequenceNumber(2, "B", graph);
			assertSequenceNumber(3, "C", graph);
			assertSequenceNumber(1, "D", graph);
			assertSequenceNumber(1, "E", graph);
		});
	}

	@Test
	void testInterleavedUnconnected() {
		forEachElementPermutation(this::setupInterleavedUnconnected, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("A", "D", "B", "C"), graph);
			assertSequenceNumber(1, "A", graph);
			assertSequenceNumber(1, "B", graph);
			assertSequenceNumber(2, "C", graph);
			assertSequenceNumber(2, "D", graph);
		});
	}

	@Test
	void testSelfLoop() {
		forEachElementPermutation(this::setupSelfLoop, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("B", "A", "C", "D"), graph);

			assertSelfLoop("A", graph, true);
			assertSelfLoop("B", graph, false);
			assertSelfLoop("C", graph, false);
			assertSelfLoop("D", graph, false);

			assertSequenceNumber(2, "A", graph);
			assertSequenceNumber(1, "B", graph);
			assertSequenceNumber(3, "C", graph);
			assertSequenceNumber(4, "D", graph);
		});
	}

	@Test
	void testUntypedSubApp() {
		forEachElementPermutation(this::setupUntypedSubApp, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("A", "SubApp", "B"), graph);
			assertNodes(List.of(), graph.getEntryNodes(), graph);
			assertNodes(List.of(), graph.getExitNodes(), graph);

			assertSequenceNumber(1, "A", graph);
			assertContainerEntry(false, "A", graph);
			assertContainerExit(false, "A", graph);

			assertSequenceNumber(2, "SubApp", graph);
			assertContainerEntry(false, "SubApp", graph);
			assertContainerExit(false, "SubApp", graph);

			assertSequenceNumber(3, "B", graph);
			assertContainerEntry(false, "B", graph);
			assertContainerExit(false, "B", graph);

			final EventTopologyNode subNode = graph.getNode(network.getSubAppNamed("SubApp"));
			final FBNetworkEventTopologyGraph subGraph = (FBNetworkEventTopologyGraph) subNode.loadSubgraph();
			assertNodes(List.of("C"), subGraph);
			assertNodes(List.of("C"), subGraph.getEntryNodes(), subGraph);
			assertNodes(List.of("C"), subGraph.getExitNodes(), subGraph);

			assertSequenceNumber(1, "C", subGraph);
			assertContainerEntry(true, "C", subGraph);
			assertContainerExit(true, "C", subGraph);
		});
	}

	@Test
	void testUntypedSubAppUnconnected() {
		forEachElementPermutation(this::setupUntypedSubAppUnconnected, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("A", "SubApp", "B"), graph);
			assertNodes(List.of(), graph.getEntryNodes(), graph);
			assertNodes(List.of(), graph.getExitNodes(), graph);

			assertSequenceNumber(1, "A", graph);
			assertContainerEntry(false, "A", graph);
			assertContainerExit(false, "A", graph);

			assertSequenceNumber(2, "SubApp", graph);
			assertContainerEntry(false, "SubApp", graph);
			assertContainerExit(false, "SubApp", graph);

			assertSequenceNumber(3, "B", graph);
			assertContainerEntry(false, "B", graph);
			assertContainerExit(false, "B", graph);

			final EventTopologyNode subNode = graph.getNode(network.getSubAppNamed("SubApp"));
			final FBNetworkEventTopologyGraph subGraph = (FBNetworkEventTopologyGraph) subNode.loadSubgraph();
			assertNodes(List.of("D", "C"), subGraph);
			assertNodes(List.of("D"), subGraph.getEntryNodes(), subGraph);
			assertNodes(List.of("D"), subGraph.getExitNodes(), subGraph);

			assertSequenceNumber(1, "C", subGraph);
			assertContainerEntry(false, "C", subGraph);
			assertContainerExit(false, "C", subGraph);

			assertSequenceNumber(1, "D", subGraph);
			assertContainerEntry(true, "D", subGraph);
			assertContainerExit(true, "D", subGraph);
		});
	}

	@Test
	void testNestedUntypedSubApp() {
		forEachElementPermutation(this::setupNestedUntypedSubApp, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("A", "SubApp", "B"), graph);
			assertNodes(List.of(), graph.getEntryNodes(), graph);
			assertNodes(List.of(), graph.getExitNodes(), graph);

			assertSequenceNumber(1, "A", graph);
			assertContainerEntry(false, "A", graph);
			assertContainerExit(false, "A", graph);

			assertSequenceNumber(2, "SubApp", graph);
			assertContainerEntry(false, "SubApp", graph);
			assertContainerExit(false, "SubApp", graph);

			assertSequenceNumber(3, "B", graph);
			assertContainerEntry(false, "B", graph);
			assertContainerExit(false, "B", graph);

			final EventTopologyNode subNode = graph.getNode(network.getSubAppNamed("SubApp"));
			final FBNetworkEventTopologyGraph subGraph = (FBNetworkEventTopologyGraph) subNode.loadSubgraph();
			assertNodes(List.of("NestedSubApp"), subGraph);
			assertNodes(List.of("NestedSubApp"), subGraph.getEntryNodes(), subGraph);
			assertNodes(List.of("NestedSubApp"), subGraph.getExitNodes(), subGraph);

			assertSequenceNumber(1, "NestedSubApp", subGraph);
			assertContainerEntry(true, "NestedSubApp", subGraph);
			assertContainerExit(true, "NestedSubApp", subGraph);

			final EventTopologyNode nestedSubNode = subGraph
					.getNode(network.getSubAppNamed("SubApp").getSubAppNetwork().getSubAppNamed("NestedSubApp"));
			final FBNetworkEventTopologyGraph nestedSubGraph = (FBNetworkEventTopologyGraph) nestedSubNode
					.loadSubgraph();
			assertNodes(List.of("C"), nestedSubGraph);
			assertNodes(List.of("C"), nestedSubGraph.getEntryNodes(), nestedSubGraph);
			assertNodes(List.of("C"), nestedSubGraph.getExitNodes(), nestedSubGraph);

			assertSequenceNumber(1, "C", nestedSubGraph);
			assertContainerEntry(true, "C", nestedSubGraph);
			assertContainerExit(true, "C", nestedSubGraph);
		});
	}

	@Test
	void testUntypedSubAppCycle() {
		forEachElementPermutation(this::setupUntypedSubAppCycle, () -> {
			final FBNetworkEventTopologyGraph graph = new FBNetworkEventTopologyGraph(network);
			assertNodes(List.of("A", "SubApp", "B"), graph);
			assertNodes(List.of(), graph.getEntryNodes(), graph);
			assertNodes(List.of(), graph.getExitNodes(), graph);

			assertSequenceNumber(1, "A", graph);
			assertContainerEntry(false, "A", graph);
			assertContainerExit(false, "A", graph);

			assertSequenceNumber(2, "SubApp", graph);
			assertContainerEntry(false, "SubApp", graph);
			assertContainerExit(false, "SubApp", graph);

			assertSequenceNumber(3, "B", graph);
			assertContainerEntry(false, "B", graph);
			assertContainerExit(false, "B", graph);

			final EventTopologyNode subNode = graph.getNode(network.getSubAppNamed("SubApp"));
			final FBNetworkEventTopologyGraph subGraph = (FBNetworkEventTopologyGraph) subNode.loadSubgraph();
			assertNodes(List.of("C", "D"), subGraph);
			assertNodes(List.of("C"), subGraph.getEntryNodes(), subGraph);
			assertNodes(List.of("D"), subGraph.getExitNodes(), subGraph);

			assertSequenceNumber(1, "C", subGraph);
			assertContainerEntry(true, "C", subGraph);
			assertContainerExit(false, "C", subGraph);

			assertSequenceNumber(2, "D", subGraph);
			assertContainerEntry(false, "D", subGraph);
			assertContainerExit(true, "D", subGraph);
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
				() -> timeGraph(100, () -> setupPerformanceBranchingChain(1000)));
	}

	@Test
	void testPerformanceLoop() {
		assertTimeoutPreemptively(Duration.ofSeconds(10), () -> timeGraph(100, () -> setupPerformanceLoop(100)));
	}

	protected void timeGraph(final int count, final Runnable setup) {
		time(count, setup, () -> new FBNetworkEventTopologyGraph(network));
	}

	static void assertSequenceNumber(final int expected, final String elementName,
			final FBNetworkEventTopologyGraph graph) {
		assertEquals(expected, getNode(elementName, graph).getSequenceNumber());
	}

	static void assertSelfLoop(final String elementName, final FBNetworkEventTopologyGraph graph,
			final boolean expected) {
		if (expected) {
			assertTrue(getNode(elementName, graph).hasSelfLoop());
		} else {
			assertFalse(getNode(elementName, graph).hasSelfLoop());
		}
	}

	static void assertContainerEntry(final boolean expected, final String elementName,
			final FBNetworkEventTopologyGraph graph) {
		if (expected) {
			assertTrue(getNode(elementName, graph).hasContainerEntry());
		} else {
			assertFalse(getNode(elementName, graph).hasContainerEntry());
		}
	}

	static void assertContainerExit(final boolean expected, final String elementName,
			final FBNetworkEventTopologyGraph graph) {
		if (expected) {
			assertTrue(getNode(elementName, graph).hasContainerExit());
		} else {
			assertFalse(getNode(elementName, graph).hasContainerExit());
		}
	}

	private static EventTopologyNode getNode(final String elementName, final FBNetworkEventTopologyGraph graph) {
		return graph.getNode((BlockFBNetworkElement) graph.getNetwork().getElementNamed(elementName));
	}
}
