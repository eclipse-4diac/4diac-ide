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
 *   Martin Erich Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.model.graph.FBNetworkEventTopologyGraph;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.fordiac.ide.test.model.graph.FBNetworkGraphTest;
import org.junit.jupiter.api.Test;

@SuppressWarnings("nls")
class ReorderEventSequenceCommandTest extends FBNetworkGraphTest {

	void setupLongChain() {
		/*-
		 * A -> B -> C -> D -> E -> F -> G -> H
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
	}

	void setupFullCycleMultipleComponents() {
		/*-
		 * B -> A -> C -> D, E -> F
		 * ^--------------/
		 */
		final FB fbA = newFB("A");
		final FB fbB = newFB("B");
		final FB fbC = newFB("C");
		final FB fbD = newFB("D");
		final FB fbE = newFB("E");
		final FB fbF = newFB("F");
		newEventConnection(fbB, "CNF", fbA, "REQ");
		newEventConnection(fbA, "CNF", fbC, "REQ");
		newEventConnection(fbC, "CNF", fbD, "REQ");
		newEventConnection(fbD, "CNF", fbB, "REQ");
		newEventConnection(fbE, "CNF", fbF, "REQ");
	}

	void setupMultipleEventChain() {
		/*-
		 * B (EO2) -> (EI1) A (EO1) -> (EI2) C
		 * D (EO1) -> (EI2) E (EO1) -> (EI1) F
		 */
		final InterfaceList interfaceList = newInterfaceList(newEvent("EI1", true), newEvent("EI2", true),
				newEvent("EO1", false), newEvent("EO2", false));
		final FB fbA = newFB("A", EcoreUtil.copy(interfaceList));
		final FB fbB = newFB("B", EcoreUtil.copy(interfaceList));
		final FB fbC = newFB("C", EcoreUtil.copy(interfaceList));
		final FB fbD = newFB("D", EcoreUtil.copy(interfaceList));
		final FB fbE = newFB("E", EcoreUtil.copy(interfaceList));
		final FB fbF = newFB("F", EcoreUtil.copy(interfaceList));
		newEventConnection(fbB, "EO2", fbA, "EI1");
		newEventConnection(fbA, "EO1", fbC, "EI2");
		newEventConnection(fbD, "EO1", fbE, "EI2");
		newEventConnection(fbE, "EO1", fbF, "EI1");
	}

	@Test
	void reorderSingleElementAfter() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("A", false, "D");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "D", "B", "C", "E", "F", "G", "H");

		assertTrue(command.canUndo());
		command.undo();
		assertChain("A", "B", "C", "D", "E", "F", "G", "H");

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "D", "B", "C", "E", "F", "G", "H");
	}

	@Test
	void reorderSingleElementBefore() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("B", true, "D");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "D", "B", "C", "E", "F", "G", "H");

		assertTrue(command.canUndo());
		command.undo();
		assertChain("A", "B", "C", "D", "E", "F", "G", "H");

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "D", "B", "C", "E", "F", "G", "H");
	}

	@Test
	void reorderMultipleElementsAfter() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("A", false, "D", "E", "G");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "D", "E", "G", "B", "C", "F", "H");

		assertTrue(command.canUndo());
		command.undo();
		assertChain("A", "B", "C", "D", "E", "F", "G", "H");

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "D", "E", "G", "B", "C", "F", "H");
	}

	@Test
	void reorderMultipleElementsBefore() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("B", true, "D", "E", "G");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "D", "E", "G", "B", "C", "F", "H");

		assertTrue(command.canUndo());
		command.undo();
		assertChain("A", "B", "C", "D", "E", "F", "G", "H");

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "D", "E", "G", "B", "C", "F", "H");
	}

	@Test
	void reorderTargetUnconnectedAfter() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("H", false, "D", "E", "G");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "F", "H", "D", "E", "G");

		assertTrue(command.canUndo());
		command.undo();
		assertChain("A", "B", "C", "D", "E", "F", "G", "H");

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "F", "H", "D", "E", "G");
	}

	@Test
	void reorderTargetUnconnectedBefore() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("A", true, "D", "E", "G");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("D", "E", "G", "A", "B", "C", "F", "H");

		assertTrue(command.canUndo());
		command.undo();
		assertChain("A", "B", "C", "D", "E", "F", "G", "H");

		assertTrue(command.canRedo());
		command.redo();
		assertChain("D", "E", "G", "A", "B", "C", "F", "H");
	}

	@Test
	void reorderElementUnconnectedAfter() {
		setupLongChain();
		newFB("U");
		final ReorderEventSequenceCommand command = createCommand("C", false, "U");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "U", "D", "E", "F", "G", "H");
		assertConnections("U", 1, 1);

		assertTrue(command.canUndo());
		command.undo();
		sanityCheckConnections();
		assertConnections("U", 0, 0);
		assertNodes(List.of("A", "B", "C", "D", "E", "F", "G", "H", "U"), new FBNetworkEventTopologyGraph(network));

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "U", "D", "E", "F", "G", "H");
		assertConnections("U", 1, 1);
	}

	@Test
	void reorderElementUnconnectedBefore() {
		setupLongChain();
		newFB("U");
		final ReorderEventSequenceCommand command = createCommand("D", true, "U");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "U", "D", "E", "F", "G", "H");
		assertConnections("U", 1, 1);

		assertTrue(command.canUndo());
		command.undo();
		sanityCheckConnections();
		assertConnections("U", 0, 0);
		assertNodes(List.of("A", "B", "C", "D", "E", "F", "G", "H", "U"), new FBNetworkEventTopologyGraph(network));

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "U", "D", "E", "F", "G", "H");
		assertConnections("U", 1, 1);
	}

	@Test
	void reorderElementAndTargetUnconnectedAfter() {
		setupLongChain();
		newFB("U");
		final ReorderEventSequenceCommand command = createCommand("H", false, "U");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "D", "E", "F", "G", "H", "U");
		assertConnections("U", 1, 0);

		assertTrue(command.canUndo());
		command.undo();
		sanityCheckConnections();
		assertConnections("U", 0, 0);
		assertNodes(List.of("A", "B", "C", "D", "E", "F", "G", "H", "U"), new FBNetworkEventTopologyGraph(network));

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "D", "E", "F", "G", "H", "U");
		assertConnections("U", 1, 0);
	}

	@Test
	void reorderElementAndTargetUnconnectedBefore() {
		setupLongChain();
		newFB("U");
		final ReorderEventSequenceCommand command = createCommand("A", true, "U");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("U", "A", "B", "C", "D", "E", "F", "G", "H");
		assertConnections("U", 0, 1);

		assertTrue(command.canUndo());
		command.undo();
		sanityCheckConnections();
		assertConnections("U", 0, 0);
		assertNodes(List.of("A", "B", "C", "D", "E", "F", "G", "H", "U"), new FBNetworkEventTopologyGraph(network));

		assertTrue(command.canRedo());
		command.redo();
		assertChain("U", "A", "B", "C", "D", "E", "F", "G", "H");
		assertConnections("U", 0, 1);
	}

	@Test
	void reorderElementsInplaceAfter() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("C", false, "F", "E", "D");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "F", "E", "D", "G", "H");

		assertTrue(command.canUndo());
		command.undo();
		assertChain("A", "B", "C", "D", "E", "F", "G", "H");

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "F", "E", "D", "G", "H");
	}

	@Test
	void reorderElementsInplaceBefore() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("G", true, "F", "E", "D");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "F", "E", "D", "G", "H");

		assertTrue(command.canUndo());
		command.undo();
		assertChain("A", "B", "C", "D", "E", "F", "G", "H");

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "F", "E", "D", "G", "H");
	}

	@Test
	void reorderElementsMultipleComponentsAfter() {
		setupMultipleComponents();
		final ReorderEventSequenceCommand command = createCommand("A", false, "B", "C", "D", "E", "F");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "D", "E", "F");

		assertTrue(command.canUndo());
		command.undo();
		sanityCheckConnections();
		assertNodes(List.of("B", "A", "E", "D", "C", "F"), new FBNetworkEventTopologyGraph(network));
		assertConnections("A", 1, 0);
		assertConnections("B", 0, 1);
		assertConnections("C", 1, 0);
		assertConnections("D", 1, 1);
		assertConnections("E", 0, 1);
		assertConnections("F", 0, 0);

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "D", "E", "F");
	}

	@Test
	void reorderElementsMultipleComponentsBefore() {
		setupMultipleComponents();
		final ReorderEventSequenceCommand command = createCommand("F", true, "A", "B", "C", "D", "E");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "D", "E", "F");

		assertTrue(command.canUndo());
		command.undo();
		sanityCheckConnections();
		assertNodes(List.of("B", "A", "E", "D", "C", "F"), new FBNetworkEventTopologyGraph(network));
		assertConnections("A", 1, 0);
		assertConnections("B", 0, 1);
		assertConnections("C", 1, 0);
		assertConnections("D", 1, 1);
		assertConnections("E", 0, 1);
		assertConnections("F", 0, 0);

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "D", "E", "F");
	}

	@Test
	void reorderElementsCycleAfter() {
		setupFullCycleMultipleComponents();
		final ReorderEventSequenceCommand command = createCommand("F", false, "A", "B", "C", "D");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("E", "F", "A", "B", "C", "D");

		assertTrue(command.canUndo());
		command.undo();
		sanityCheckConnections();
		assertNodes(List.of("A", "C", "D", "B", "E", "F"), new FBNetworkEventTopologyGraph(network));
		assertConnections("A", 1, 1);
		assertConnections("B", 1, 1);
		assertConnections("C", 1, 1);
		assertConnections("D", 1, 1);
		assertConnections("E", 0, 1);
		assertConnections("F", 1, 0);

		assertTrue(command.canRedo());
		command.redo();
		assertChain("E", "F", "A", "B", "C", "D");
	}

	@Test
	void reorderElementsCycleBefore() {
		setupFullCycleMultipleComponents();
		final ReorderEventSequenceCommand command = createCommand("E", true, "A", "B", "C", "D");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "D", "E", "F");

		assertTrue(command.canUndo());
		command.undo();
		sanityCheckConnections();
		assertNodes(List.of("A", "C", "D", "B", "E", "F"), new FBNetworkEventTopologyGraph(network));
		assertConnections("A", 1, 1);
		assertConnections("B", 1, 1);
		assertConnections("C", 1, 1);
		assertConnections("D", 1, 1);
		assertConnections("E", 0, 1);
		assertConnections("F", 1, 0);

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "D", "E", "F");
	}

	@Test
	void reorderMultipleEventChainAfter() {
		setupMultipleEventChain();
		final ReorderEventSequenceCommand command = createCommand("A", false, "B", "C", "D", "E", "F");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "D", "E", "F");
		assertConnected("A", "EO1", "B", "EI1");
		assertConnected("B", "EO2", "C", "EI2");
		assertConnected("C", "EO1", "D", "EI1");
		assertConnected("D", "EO1", "E", "EI2");
		assertConnected("E", "EO1", "F", "EI1");

		assertTrue(command.canUndo());
		command.undo();
		sanityCheckConnections();
		assertNodes(List.of("B", "A", "C", "D", "E", "F"), new FBNetworkEventTopologyGraph(network));
		assertConnections("A", 1, 1);
		assertConnections("B", 0, 1);
		assertConnections("C", 1, 0);
		assertConnections("D", 0, 1);
		assertConnections("E", 1, 1);
		assertConnections("F", 1, 0);
		assertConnected("B", "EO2", "A", "EI1");
		assertConnected("A", "EO1", "C", "EI2");
		assertConnected("D", "EO1", "E", "EI2");
		assertConnected("E", "EO1", "F", "EI1");

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "D", "E", "F");
	}

	@Test
	void reorderMultipleEventChainBefore() {
		setupMultipleEventChain();
		final ReorderEventSequenceCommand command = createCommand("F", true, "A", "B", "C", "D", "E");

		assertTrue(command.canExecute());
		command.execute();
		assertChain("A", "B", "C", "D", "E", "F");
		assertConnected("A", "EO1", "B", "EI1");
		assertConnected("B", "EO2", "C", "EI2");
		assertConnected("C", "EO1", "D", "EI1");
		assertConnected("D", "EO1", "E", "EI2");
		assertConnected("E", "EO1", "F", "EI1");

		assertTrue(command.canUndo());
		command.undo();
		sanityCheckConnections();
		assertNodes(List.of("B", "A", "C", "D", "E", "F"), new FBNetworkEventTopologyGraph(network));
		assertConnections("A", 1, 1);
		assertConnections("B", 0, 1);
		assertConnections("C", 1, 0);
		assertConnections("D", 0, 1);
		assertConnections("E", 1, 1);
		assertConnections("F", 1, 0);
		assertConnected("B", "EO2", "A", "EI1");
		assertConnected("A", "EO1", "C", "EI2");
		assertConnected("D", "EO1", "E", "EI2");
		assertConnected("E", "EO1", "F", "EI1");

		assertTrue(command.canRedo());
		command.redo();
		assertChain("A", "B", "C", "D", "E", "F");
	}

	@Test
	void rejectEmpty() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("A", false);
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_InvalidElements, command.getErrorMessage());
	}

	@Test
	void rejectSelf() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("D", false, "D");
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_InvalidElements, command.getErrorMessage());
	}

	@Test
	void rejectDuplicate() {
		setupLongChain();
		final ReorderEventSequenceCommand command = createCommand("A", false, "D", "C", "D");
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_InvalidElements, command.getErrorMessage());
	}

	@Test
	void rejectDifferentNetwork() {
		setupUntypedSubApp();
		final ReorderEventSequenceCommand command = new ReorderEventSequenceCommand(
				List.of(network.getSubAppNamed("SubApp").getSubAppNetwork().getFBNamed("C")), network.getFBNamed("A"),
				false);
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_NotInSameNetwork, command.getErrorMessage());
	}

	@Test
	void rejectElementNoEvents() {
		setupLongChain();
		newFB("X", LibraryElementFactory.eINSTANCE.createInterfaceList());
		final ReorderEventSequenceCommand command = createCommand("A", false, "X");
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_ElementsNoEvents, command.getErrorMessage());
	}

	@Test
	void rejectElementJoin() {
		setupJoinIndependentLong();
		final ReorderEventSequenceCommand command = createCommand("C", false, "A");
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_ElementsMultipleConnections, command.getErrorMessage());
	}

	@Test
	void rejectElementSplit() {
		setupSplitLong();
		final ReorderEventSequenceCommand command = createCommand("C", false, "B");
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_ElementsMultipleConnections, command.getErrorMessage());
	}

	@Test
	void rejectTargetNoOutputEvents() {
		setupLongChain();
		newFB("X", LibraryElementFactory.eINSTANCE.createInterfaceList());
		final ReorderEventSequenceCommand command = createCommand("X", false, "D");
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_TargetNoOutputEvents, command.getErrorMessage());
	}

	@Test
	void rejectTargetNoInputEvents() {
		setupLongChain();
		newFB("X", LibraryElementFactory.eINSTANCE.createInterfaceList());
		final ReorderEventSequenceCommand command = createCommand("X", true, "D");
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_TargetNoInputEvents, command.getErrorMessage());
	}

	@Test
	void rejectTargetJoin() {
		setupJoinIndependentLong();
		final ReorderEventSequenceCommand command = createCommand("A", true, "C");
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_TargetMultipleInputConnections, command.getErrorMessage());
	}

	@Test
	void rejectTargetSplit() {
		setupSplitLong();
		final ReorderEventSequenceCommand command = createCommand("B", false, "C");
		assertFalse(command.canExecute());
		assertEquals(Messages.ReorderEventSequenceCommand_TargetMultipleOutputConnections, command.getErrorMessage());
	}

	@Test
	void rejectUnchanged() {
		setupLongChain();
		ReorderEventSequenceCommand command = createCommand("D", false, "E");
		assertFalse(command.canExecute());
		assertTrue(command.getErrorMessage().isEmpty());

		command = createCommand("D", true, "C");
		assertFalse(command.canExecute());
		assertTrue(command.getErrorMessage().isEmpty());

		command = createCommand("D", false, "E", "F");
		assertFalse(command.canExecute());
		assertTrue(command.getErrorMessage().isEmpty());

		command = createCommand("D", true, "B", "C");
		assertFalse(command.canExecute());
		assertTrue(command.getErrorMessage().isEmpty());
	}

	private ReorderEventSequenceCommand createCommand(final String target, final boolean insertBefore,
			final String... sources) {
		return new ReorderEventSequenceCommand(Stream.of(sources).map(network::getFBNamed).toList(),
				network.getFBNamed(target), insertBefore);
	}

	private void assertChain(final String... expected) {
		sanityCheckConnections();
		if (expected.length > 0) {
			assertConnections(expected[0], 0, 1);
			for (int i = 1; i < expected.length - 1; i++) {
				assertConnections(expected[i], 1, 1);
			}
			assertConnections(expected[expected.length - 1], 1, 0);
		}
		assertNodes(List.of(expected), new FBNetworkEventTopologyGraph(network));
	}

	private void assertConnections(final String name, final int expectedInput, final int expectedOutput) {
		final FB fb = network.getFBNamed(name);
		assertEquals(expectedInput, fb.getInterface().getEventInputs().stream()
				.map(IInterfaceElement::getInputConnections).mapToInt(Collection::size).sum(),
				"input connections for " + name);
		assertEquals(
				expectedOutput, fb.getInterface().getEventOutputs().stream()
						.map(IInterfaceElement::getOutputConnections).mapToInt(Collection::size).sum(),
				"output connections for " + name);
	}

	private void assertConnected(final String sourceElementName, final String sourceName,
			final String destinationElementName, final String destinationName) {
		final Event source = network.getFBNamed(sourceElementName).getInterface().getEvent(sourceName);
		final Event destination = network.getFBNamed(destinationElementName).getInterface().getEvent(destinationName);
		assertTrue(LinkConstraints.duplicateConnection(source, destination),
				"connection " + source.getQualifiedName() + " -> " + destination.getQualifiedName() + " exists");
	}

	private void sanityCheckConnections() {
		network.getBlockFBNetworkElements().forEach(ReorderEventSequenceCommandTest::sanityCheckConnections);
	}

	private static void sanityCheckConnections(final BlockFBNetworkElement fb) {
		assertTrue(fb.getInterface().getEventInputs().stream().map(IInterfaceElement::getOutputConnections)
				.allMatch(List::isEmpty), "output connections on input event");
		assertTrue(fb.getInterface().getEventOutputs().stream().map(IInterfaceElement::getInputConnections)
				.allMatch(List::isEmpty), "input connections on output event");
	}
}
