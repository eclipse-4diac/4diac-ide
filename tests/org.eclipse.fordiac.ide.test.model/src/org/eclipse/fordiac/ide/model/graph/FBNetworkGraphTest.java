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

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.value.TimeValueConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.opentest4j.AssertionFailedError;

@SuppressWarnings("nls")
public abstract class FBNetworkGraphTest {

	protected String testName;
	protected FBNetwork network;

	@BeforeEach
	protected void setup(final TestInfo info) {
		testName = info.getDisplayName();
		network = LibraryElementFactory.eINSTANCE.createFBNetwork();
	}

	protected void setupSingle() {
		newFB("A");
	}

	protected void setupUnconnected() {
		newFB("A");
		newFB("B");
		newFB("C");
	}

	protected void setupHalfConnected() {
		/*-
		 * B -> A, C
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		newFB("C");
		newEventConnection(fbB, "CNF", fbA, "REQ");
	}

	protected void setupMultipleComponents() {
		/*-
		 * B -> A   E -> D -> C   F
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		final FB fbE = newFB("E");
		newFB("F");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbD, "CNF", fbC, "REQ");
		newEventConnection(fbE, "CNF", fbD, "REQ");
	}

	protected void setupChain() {
		/*-
		 * C -> B -> A
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbC, "CNF", fbB, "REQ");
	}

	protected void setupSplit() {
		/*-
		 * B ---> A
		 *   \------> D -> C
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbB, "CNF", fbD, "REQ");
		newEventConnection(fbD, "CNF", fbC, "REQ");
	}

	protected void setupSplitLong() {
		/*-
		 * B ---> A
		 *   \------> D -> C -> E -> F
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		final FB fbE = newFB("E");
		final FB fbF = newFB("F");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbB, "CNF", fbD, "REQ");
		newEventConnection(fbD, "CNF", fbC, "REQ");
		newEventConnection(fbC, "CNF", fbE, "REQ");
		newEventConnection(fbE, "CNF", fbF, "REQ");
	}

	protected void setupJoin() {
		/*-
		 * B -> A --------> C
		 *        \-> D -/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbA, "CNF", fbC, "REQ");
		newEventConnection(fbB, "CNF", fbD, "REQ");
		newEventConnection(fbD, "CNF", fbC, "REQ");
	}

	protected void setupJoinIndependent() {
		/*-
		 * C -> B -----> A
		 *         D -/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		newEventConnection(fbC, "CNF", fbB, "REQ");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbD, "CNF", fbA, "REQ");
	}

	protected void setupJoinIndependentLong() {
		/*-
		 * E -> C -> B ----------> A
		 *              F -> D -/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		final FB fbE = newFB("E");
		final FB fbF = newFB("F");
		newEventConnection(fbE, "CNF", fbC, "REQ");
		newEventConnection(fbC, "CNF", fbB, "REQ");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbF, "CNF", fbD, "REQ");
		newEventConnection(fbD, "CNF", fbA, "REQ");
	}

	protected void setupRootPathLengths() {
		/*-
		 * B ----------> A
		 * D -> C -----/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbC, "CNF", fbA, "REQ");
		newEventConnection(fbD, "CNF", fbC, "REQ");
	}

	protected void setupRootPathLengthTie() {
		/*-
		 * C -> A
		 * B --/
		 */
		final FB fbA = newFB("A");
		final FB fbC = newFB("C");
		final FB fbB = newFB("B");
		newEventConnection(fbC, "CNF", fbA, "REQ");
		newEventConnection(fbB, "CNF", fbA, "REQ");
	}

	protected void setupDetour() {
		/*-
		 * A -> B -> C -> D -> E
		 *       \--------/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		final FB fbE = newFB("E");
		newEventConnection(fbA, "CNF", fbB, "REQ");
		newEventConnection(fbB, "CNF", fbC, "REQ");
		newEventConnection(fbC, "CNF", fbD, "REQ");
		newEventConnection(fbD, "CNF", fbE, "REQ");
		newEventConnection(fbB, "CNF", fbD, "REQ");
	}

	protected void setupInterconnectedLanes() {
		/*-
		 * C -> B --------\
		 *         F -> D --> A
		 *                 \----> E
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		final FB fbE = newFB("E");
		final FB fbF = newFB("F");
		newEventConnection(fbC, "CNF", fbB, "REQ");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbF, "CNF", fbD, "REQ");
		newEventConnection(fbD, "CNF", fbA, "REQ");
		newEventConnection(fbD, "CNF", fbE, "REQ");
	}

	protected void setupInterconnectedLanesAlternate() {
		/*-
		 * C -> B ---------------> A
		 *         F -> D -/-> E
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		final FB fbE = newFB("E");
		final FB fbF = newFB("F");
		newEventConnection(fbC, "CNF", fbB, "REQ");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbF, "CNF", fbD, "REQ");
		newEventConnection(fbD, "CNF", fbE, "REQ");
		newEventConnection(fbD, "CNF", fbA, "REQ");
	}

	protected void setupSelfLoop() {
		/*-
		 * B -> A -> C -> D
		 *          ^-/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbA, "CNF", fbC, "REQ");
		newEventConnection(fbC, "CNF", fbD, "REQ");
		newEventConnection(fbA, "CNF", fbA, "REQ");
	}

	protected void setupCycle() {
		/*-
		 * B -> A -> C -> D
		 *      ^---------/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbA, "CNF", fbC, "REQ");
		newEventConnection(fbC, "CNF", fbD, "REQ");
		newEventConnection(fbD, "CNF", fbA, "REQ");
	}

	protected void setupCycleWithTail() {
		/*-
		 * A -> B -> C -> D
		 * ^----/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		newEventConnection(fbA, "CNF", fbB, "REQ");
		newEventConnection(fbB, "CNF", fbC, "REQ");
		newEventConnection(fbC, "CNF", fbD, "REQ");
		newEventConnection(fbB, "CNF", fbA, "REQ");
	}

	protected void setupMultipleCycles() {
		/*-
		 * D ------> E ------------> F -> G -> H
		 * |--> A ---\--> B -> C          ^----/
		 * \--<--<--<--<--<--<-/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		final FB fbE = newFB("E");
		final FB fbF = newFB("F");
		final FB fbG = newFB("G");
		final FB fbH = newFB("H");
		newEventConnection(fbA, "CNF", fbB, "REQ");
		newEventConnection(fbB, "CNF", fbC, "REQ");
		newEventConnection(fbC, "CNF", fbD, "REQ");
		newEventConnection(fbD, "CNF", fbE, "REQ");
		newEventConnection(fbE, "CNF", fbF, "REQ");
		newEventConnection(fbF, "CNF", fbG, "REQ");
		newEventConnection(fbG, "CNF", fbH, "REQ");
		newEventConnection(fbD, "CNF", fbA, "REQ");
		newEventConnection(fbE, "CNF", fbB, "REQ");
		newEventConnection(fbH, "CNF", fbG, "REQ");
	}

	protected void setupInterleavedCycles() {
		/*-
		 * D --> A ----\
		 *    E ---> B --> C
		 *       ^--<--<---|
		 *           ^--<--/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		final FB fbE = newFB("E");
		newEventConnection(fbA, "CNF", fbC, "REQ");
		newEventConnection(fbB, "CNF", fbC, "REQ");
		newEventConnection(fbC, "CNF", fbA, "REQ");
		newEventConnection(fbC, "CNF", fbB, "REQ");
		newEventConnection(fbD, "CNF", fbA, "REQ");
		newEventConnection(fbE, "CNF", fbB, "REQ");
	}

	protected void setupInterleavedUnconnected() {
		/*-
		 * A -> D, B -> C
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		newEventConnection(fbA, "CNF", fbD, "REQ");
		newEventConnection(fbB, "CNF", fbC, "REQ");
	}

	protected void setupUntypedSubApp() {
		/*-
		 * A -> SubApp -> B
		 *      ( -> C -> )
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final UntypedSubApp subApp = newUntypedSubApp("SubApp");
		final FB fbC = newFB(subApp.getSubAppNetwork(), "C");
		newEventConnection(fbA, "CNF", subApp, "REQ");
		newEventConnection(subApp.getSubAppNetwork(), subApp, "REQ", fbC, "REQ");
		newEventConnection(subApp.getSubAppNetwork(), fbC, "CNF", subApp, "CNF");
		newEventConnection(subApp, "CNF", fbB, "REQ");
	}

	protected void setupUntypedSubAppUnconnected() {
		/*-
		 * A -> SubApp -> B
		 *      ( -> D ---> )
		 *              C
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final UntypedSubApp subApp = newUntypedSubApp("SubApp");
		newFB(subApp.getSubAppNetwork(), "C");
		final FB fbD = newFB(subApp.getSubAppNetwork(), "D");
		newEventConnection(fbA, "CNF", subApp, "REQ");
		newEventConnection(subApp.getSubAppNetwork(), subApp, "REQ", fbD, "REQ");
		newEventConnection(subApp.getSubAppNetwork(), fbD, "CNF", subApp, "CNF");
		newEventConnection(subApp, "CNF", fbB, "REQ");
	}

	protected void setupNestedUntypedSubApp() {
		/*-
		 * A -> SubApp -> B
		 *      ( -> NestedSubApp -> )
		 *           ( -> C -> )
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final UntypedSubApp subApp = newUntypedSubApp("SubApp");
		final UntypedSubApp nestedSubApp = newUntypedSubApp(subApp.getSubAppNetwork(), "NestedSubApp");
		final FB fbC = newFB(nestedSubApp.getSubAppNetwork(), "C");
		newEventConnection(fbA, "CNF", subApp, "REQ");
		newEventConnection(subApp.getSubAppNetwork(), subApp, "REQ", nestedSubApp, "REQ");
		newEventConnection(nestedSubApp.getSubAppNetwork(), nestedSubApp, "REQ", fbC, "REQ");
		newEventConnection(nestedSubApp.getSubAppNetwork(), fbC, "CNF", nestedSubApp, "CNF");
		newEventConnection(subApp.getSubAppNetwork(), nestedSubApp, "CNF", subApp, "CNF");
		newEventConnection(subApp, "CNF", fbB, "REQ");
	}

	protected void setupUntypedSubAppCycle() {
		/*-
		 * A -> SubApp -> B
		 *      ( -> C -> D -> )
		 *           ^----/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final UntypedSubApp subApp = newUntypedSubApp("SubApp");
		final FB fbC = newFB(subApp.getSubAppNetwork(), "C");
		final FB fbD = newFB(subApp.getSubAppNetwork(), "D");
		newEventConnection(fbA, "CNF", subApp, "REQ");
		newEventConnection(subApp.getSubAppNetwork(), subApp, "REQ", fbC, "REQ");
		newEventConnection(subApp.getSubAppNetwork(), fbC, "CNF", fbD, "REQ");
		newEventConnection(subApp.getSubAppNetwork(), fbD, "CNF", fbC, "REQ");
		newEventConnection(subApp.getSubAppNetwork(), fbD, "CNF", subApp, "CNF");
		newEventConnection(subApp, "CNF", fbB, "REQ");
	}

	protected void setupPerformanceLinearChain(final int count) {
		FB last = newFB("Start");
		for (int i = 0; i < count; i++) {
			final FB fb = newFB("FB_" + i);
			newEventConnection(last, "CNF", fb, "REQ");
			last = fb;
		}
	}

	protected void setupPerformanceDoubleLinearChain(final int count) {
		FB last1 = newFB("Start1");
		FB last2 = newFB("Start2");
		for (int i = 0; i < count; i++) {
			final FB fb1 = newFB("FB1_" + i);
			newEventConnection(last1, "CNF", fb1, "REQ");
			last1 = fb1;

			final FB fb2 = newFB("FB2_" + i);
			newEventConnection(last2, "CNF", fb2, "REQ");
			last2 = fb2;
		}
	}

	protected void setupPerformanceBranchingChain(final int count) {
		FB last = newFB("Start");
		for (int i = 0; i < count; i++) {
			final FB fb1 = newFB("FB1_" + i);
			final FB fb2 = newFB("FB2_" + i);
			newEventConnection(last, "CNF", fb1, "REQ");
			newEventConnection(last, "CNF", fb2, "REQ");
			last = fb1;
		}
	}

	protected void setupPerformanceLoop(final int count) {
		final FB start = newFB("Start");
		FB last = start;
		for (int i = 0; i < count; i++) {
			final FB fb = newFB("FB_" + i);
			newEventConnection(last, "CNF", fb, "REQ");
			last = fb;
		}
		newEventConnection(last, "CNF", start, "REQ");
	}

	protected static void assertNodes(final Iterable<String> expected, final FBNetworkGraph<?> graph) {
		assertNodes(expected, graph.getSortedNodes(), graph);
	}

	protected static void assertNodes(final Iterable<String> expected,
			final Iterable<? extends FBNetworkGraph<?>.Node> actual, final FBNetworkGraph<?> graph) {
		final List<?> expectedNodes = StreamSupport.stream(expected.spliterator(), false)
				.map(graph.getNetwork()::getElementNamed).map(BlockFBNetworkElement.class::cast).map(graph::getNode)
				.toList();
		assertIterableEquals(expectedNodes, actual);
	}

	protected FB newFB(final String name) {
		return newFB(name, newInterfaceList());
	}

	protected FB newFB(final String name, final InterfaceList interfaceList) {
		return newFB(network, name, interfaceList);
	}

	protected static FB newFB(final FBNetwork network, final String name) {
		return newFB(network, name, newInterfaceList());
	}

	protected static FB newFB(final FBNetwork network, final String name, final InterfaceList interfaceList) {
		final var result = LibraryElementFactory.eINSTANCE.createFB();
		result.setName(name);
		result.setInterface(interfaceList);
		network.getNetworkElements().add(result);
		return result;
	}

	protected static InterfaceList newInterfaceList() {
		return newInterfaceList(newEvent("REQ", true), newEvent("CNF", false));
	}

	protected static InterfaceList newInterfaceList(final Event... events) {
		final var result = LibraryElementFactory.eINSTANCE.createInterfaceList();
		for (final Event event : events) {
			if (event.isIsInput()) {
				result.getEventInputs().add(event);
			} else {
				result.getEventOutputs().add(event);
			}
		}
		return result;
	}

	protected UntypedSubApp newUntypedSubApp(final String name) {
		return newUntypedSubApp(network, name);
	}

	protected static UntypedSubApp newUntypedSubApp(final FBNetwork network, final String name) {
		final var result = LibraryElementFactory.eINSTANCE.createUntypedSubApp();
		result.setName(name);
		result.setInterface(newInterfaceList());
		result.setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		network.getNetworkElements().add(result);
		return result;
	}

	protected static Event newEvent(final String name, final boolean input) {
		final var result = LibraryElementFactory.eINSTANCE.createEvent();
		result.setName(name);
		result.setType(EventTypeLibrary.getInstance().getType(null));
		result.setIsInput(input);
		return result;
	}

	protected EventConnection newEventConnection(final BlockFBNetworkElement sourceElement,
			final String sourceEventName, final BlockFBNetworkElement destinationElement,
			final String destinationEventName) {
		return newEventConnection(sourceElement.getInterface().getEvent(sourceEventName),
				destinationElement.getInterface().getEvent(destinationEventName));
	}

	protected EventConnection newEventConnection(final Event source, final Event destination) {
		final var result = LibraryElementFactory.eINSTANCE.createEventConnection();
		result.setSource(source);
		result.setDestination(destination);
		network.getEventConnections().add(result);
		return result;
	}

	protected static EventConnection newEventConnection(final FBNetwork network, final Event source,
			final Event destination) {
		final var result = LibraryElementFactory.eINSTANCE.createEventConnection();
		result.setSource(source);
		result.setDestination(destination);
		network.getEventConnections().add(result);
		return result;
	}

	protected static EventConnection newEventConnection(final FBNetwork network,
			final BlockFBNetworkElement sourceElement, final String sourceEventName,
			final BlockFBNetworkElement destinationElement, final String destinationEventName) {
		return newEventConnection(network, sourceElement.getInterface().getEvent(sourceEventName),
				destinationElement.getInterface().getEvent(destinationEventName));
	}

	protected void time(final int count, final Runnable setup, final Runnable runnable) {
		setup.run();
		final long start = System.nanoTime();
		for (int i = 0; i < count; i++) {
			runnable.run();
		}
		final long end = System.nanoTime();
		System.out.println(
				testName + ": elapsed: " + TimeValueConverter.INSTANCE.toString(Duration.ofNanos(end - start)));
	}

	protected void forEachElementPermutation(final Runnable setup, final Runnable assertions) {
		setup.run();
		final PermutationGenerator<FBNetworkElement> generator = new PermutationGenerator<>(
				network.getNetworkElements());
		do {
			try {
				assertions.run();
			} catch (final AssertionFailedError error) {
				throw new AssertionFailedError("Assertion failed for network order " + network.getNetworkElements()
						.stream().map(INamedElement::getName).collect(Collectors.joining(", ")), error);
			}
			ECollections.setEList(network.getNetworkElements(), generator.next());
		} while (generator.hasNext());
	}

	public class PermutationGenerator<T> {
		private final List<T> list;

		private int i = 1;
		private final int[] c;
		private boolean hasNext = true;

		public PermutationGenerator(final List<T> input) {
			this.list = new ArrayList<>(input);
			this.c = new int[list.size()];
		}

		public boolean hasNext() {
			return hasNext;
		}

		public List<T> next() {
			if (!hasNext) {
				throw new NoSuchElementException();
			}
			hasNext = advance();
			return list;
		}

		private boolean advance() {
			final int n = list.size();
			while (i < n) {
				if (c[i] < i) {
					if ((i & 1) == 0) {
						Collections.swap(list, 0, i);
					} else {
						Collections.swap(list, c[i], i);
					}
					c[i]++;
					i = 1;
					return true;
				}
				c[i] = 0;
				i++;
			}
			return false;
		}
	}
}
