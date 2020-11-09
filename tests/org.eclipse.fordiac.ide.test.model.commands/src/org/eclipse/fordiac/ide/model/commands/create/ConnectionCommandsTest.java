/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.gef.commands.CompoundCommand;
import org.junit.jupiter.params.provider.Arguments;

public class ConnectionCommandsTest extends FBNetworkTestBase {

	private static State addIOWiths(State state) {
		CompoundCommand c = new CompoundCommand();
		c.add(new WithCreateCommand(state.getFunctionblock().getFBType().getInterfaceList().getEventInputs().get(0),
						state.getFunctionblock().getFBType().getInterfaceList().getInputVars().get(0)));
		c.add(new WithCreateCommand(state.getFunctionblock().getFBType().getInterfaceList().getEventOutputs().get(0),
				state.getFunctionblock().getFBType().getInterfaceList().getOutputVars().get(0)));
		state.setCommand(c);
		return commandExecution(state);
	}

	private static State initState() {
		State state = WithCreateTest.createInterfaceElements(new State());
		state = addIOWiths(state);
		state = WithCreateTest.updateNetworkElements(state);
		return state;
	}
	

	private static State workingAddDataConnection(State state) {
		addDataConnection(state);
		return commandExecution(state);
	}

	private static void addDataConnection(State state) {
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = state.getFbNetwork().getNetworkElements().get(1).getInterface();
		
		AbstractConnectionCreateCommand c = new DataConnectionCreateCommand(state.getFbNetwork());
		c.setSource(fb1.getOutputVars().get(0));
		c.setDestination(fb2.getInputVars().get(0));
		state.setCommand(c);
	}

	private static State workingAddReverseDataConnection(State state) {
		addReverseDataConnection(state);
		return commandExecution(state);
	}

	private static void addReverseDataConnection(State state) {
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = state.getFbNetwork().getNetworkElements().get(1).getInterface();
		
		AbstractConnectionCreateCommand c = new DataConnectionCreateCommand(state.getFbNetwork());
		c.setSource(fb2.getInputVars().get(0));
		c.setDestination(fb1.getOutputVars().get(0));
		state.setCommand(c);
	}

	private static void verifyDataConnection(State state, State oldState, TestFunction t) {
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = state.getFbNetwork().getNetworkElements().get(1).getInterface();
		
		t.test(state.getMessages().isEmpty());
		t.test(fb1.getOutputVars().get(0).getOutputConnections().size(), 1);
		t.test(fb1.getOutputVars().get(0).getOutputConnections().get(0).getDestination(), fb2.getInputVars().get(0));
	}

	private static State workingAddEventConnection(State state) {
		addEventConnection(state);
		return commandExecution(state);
	}

	private static void addEventConnection(State state) {
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = state.getFbNetwork().getNetworkElements().get(1).getInterface();
		
		AbstractConnectionCreateCommand c = new EventConnectionCreateCommand(state.getFbNetwork());
		c.setSource(fb1.getEventOutputs().get(0));
		c.setDestination(fb2.getEventInputs().get(0));
		state.setCommand(c);
	}

	private static State workingAddReverseEventConnection(State state) {
		addReverseEventConnection(state);
		return commandExecution(state);
	}

	private static void addReverseEventConnection(State state) {
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = state.getFbNetwork().getNetworkElements().get(1).getInterface();
		
		AbstractConnectionCreateCommand c = new EventConnectionCreateCommand(state.getFbNetwork());
		c.setSource(fb2.getEventInputs().get(0));
		c.setDestination(fb1.getEventOutputs().get(0));
		state.setCommand(c);
	}

	private static void verifyEventConnection(State state, State oldState, TestFunction t) {
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = state.getFbNetwork().getNetworkElements().get(1).getInterface();
		
		t.test(state.getMessages().isEmpty());
		t.test(fb1.getEventOutputs().get(0).getOutputConnections().size(), 1);
		t.test(fb1.getEventOutputs().get(0).getOutputConnections().get(0).getDestination(), fb2.getEventInputs().get(0));
	}

	private static Collection<Arguments> twoFunctionBlocks(List<ExecutionDescription<?>> executionDescriptions) {
		return describeCommand("Start with two functionblocks", // //$NON-NLS-1$
				ConnectionCommandsTest::initState, //
				(State state, State oldState, TestFunction t) -> CommandTestBase.verifyNothing(state, oldState, t), //
				executionDescriptions //
				);
	}

	private static State deleteDataConnection(State state) {
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		state.setCommand(new DeleteConnectionCommand(fb1.getOutputVars().get(0).getOutputConnections().get(0)));
		return commandExecution(state);
	}
	
	private static void verifyDeleteDataConnection(State state, State oldState, TestFunction t) {
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = state.getFbNetwork().getNetworkElements().get(1).getInterface();
		t.test(fb1.getOutputVars().get(0).getOutputConnections().isEmpty());
		t.test(fb2.getInputVars().get(0).getInputConnections().isEmpty());
	}

	private static State deleteEventConnection(State state) {
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		state.setCommand(new DeleteConnectionCommand(fb1.getEventOutputs().get(0).getOutputConnections().get(0)));
		return commandExecution(state);
	}

	private static void verifyDeleteEventConnection(State state, State oldState, TestFunction t) {
		final InterfaceList fb1 = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = state.getFbNetwork().getNetworkElements().get(1).getInterface();
		t.test(fb1.getEventOutputs().get(0).getOutputConnections().isEmpty());
		t.test(fb2.getEventInputs().get(0).getInputConnections().isEmpty());
	}

	private static State deleteFB2(State state) {
		state.setCommand(new DeleteFBNetworkElementCommand(state.getFbNetwork().getNetworkElements().get(1)));
		assertion.test(((DeleteFBNetworkElementCommand)state.getCommand()).getFBNetworkElement(), state.getFbNetwork().getNetworkElements().get(1));
		return commandExecution(state);
	}

	private static void verifyDeleteFB2(State state, State oldState, TestFunction t) {
		t.test(state.getCommand() instanceof DeleteFBNetworkElementCommand);
		t.test(state.getFbNetwork().getNetworkElements().size(), 1);
		t.test(state.getFbNetwork().getDataConnections().isEmpty());
		t.test(state.getFbNetwork().getEventConnections().isEmpty());
	}

	// parameter creation function
	protected static Collection<Arguments> data() {
		final Collection<Arguments> commands = new ArrayList<>();
		
		commands.addAll(twoFunctionBlocks(List.of( //
				new ExecutionDescription<State>("Add data connection between data interface points", //$NON-NLS-1$
						ConnectionCommandsTest::workingAddDataConnection, //
						ConnectionCommandsTest::verifyDataConnection //
				), //
				new ExecutionDescription<State>("Add event connection between event interface points", //$NON-NLS-1$
						ConnectionCommandsTest::workingAddEventConnection, //
						ConnectionCommandsTest::verifyEventConnection //
				), //
				new ExecutionDescription<State>("Delete data connection between event interface points", //$NON-NLS-1$
						ConnectionCommandsTest::deleteDataConnection, //
						ConnectionCommandsTest::verifyDeleteDataConnection //
				), //
				new ExecutionDescription<State>("Delete event connection between event interface points", //$NON-NLS-1$
						ConnectionCommandsTest::deleteEventConnection, //
						ConnectionCommandsTest::verifyDeleteEventConnection //
				) //
		)));

		commands.addAll(twoFunctionBlocks(List.of( //
				new ExecutionDescription<State>("Add reverse data connection between data interface points", //$NON-NLS-1$
						ConnectionCommandsTest::workingAddReverseDataConnection, //
						ConnectionCommandsTest::verifyDataConnection //
				), //
				new ExecutionDescription<State>("Add reverse event connection between event interface points", //$NON-NLS-1$
						ConnectionCommandsTest::workingAddReverseEventConnection, //
						ConnectionCommandsTest::verifyEventConnection //
				), //
				new ExecutionDescription<State>("Delete data connection between event interface points", //$NON-NLS-1$
						ConnectionCommandsTest::deleteDataConnection, //
						ConnectionCommandsTest::verifyDeleteDataConnection //
				), //
				new ExecutionDescription<State>("Delete event connection between event interface points", //$NON-NLS-1$
						ConnectionCommandsTest::deleteEventConnection, //
						ConnectionCommandsTest::verifyDeleteEventConnection //
				) //
		)));
		
		commands.addAll(twoFunctionBlocks(List.of( //
				new ExecutionDescription<State>("Add reverse data connection between data interface points", //$NON-NLS-1$
						ConnectionCommandsTest::workingAddReverseDataConnection, //
						ConnectionCommandsTest::verifyDataConnection //
				), //
				new ExecutionDescription<State>("Add reverse event connection between event interface points", //$NON-NLS-1$
						ConnectionCommandsTest::workingAddReverseEventConnection, //
						ConnectionCommandsTest::verifyEventConnection //
				), //
				new ExecutionDescription<State>("Delete second functionblock und remove connections", //$NON-NLS-1$
						ConnectionCommandsTest::deleteFB2, //
						ConnectionCommandsTest::verifyDeleteFB2 //
				) //
		)));
		return commands;
	}
}
