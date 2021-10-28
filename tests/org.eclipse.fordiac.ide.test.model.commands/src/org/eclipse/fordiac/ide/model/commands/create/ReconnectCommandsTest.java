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

import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectEventConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.junit.jupiter.params.provider.Arguments;

public class ReconnectCommandsTest extends FBNetworkTestBase {


	private static State addThirdFB(final State state) {
		state.setCommand(new FBCreateCommand(state.getFunctionblock(), state.getFbNetwork(), 0, 0));
		return commandExecution(state);
	}

	private static State initState() {
		State state = ConnectionCommandsTest.initState();
		state = addThirdFB(state);
		state = ConnectionCommandsTest.workingAddDataConnection(state);
		state = ConnectionCommandsTest.workingAddEventConnection(state);
		return state;
	}

	private static void verifyInitialState(final State state, final State oldState, final TestFunction t) {
		// if (!state.isViaUndo()) {
		t.test(state.getFbNetwork().getNetworkElements().size(), 3);
		ConnectionCommandsTest.verifyDataConnection(state, oldState, t);
		ConnectionCommandsTest.verifyEventConnection(state, oldState, t);
		// }
	}

	private static Collection<Arguments> threeFunctionBlocks(
			final List<ExecutionDescription<?>> executionDescriptions) {
		return describeCommand("Start with three functionblocks and Connections", // //$NON-NLS-1$
				ReconnectCommandsTest::initState, //
				(StateVerifier<State>) ReconnectCommandsTest::verifyInitialState, //
				executionDescriptions //
				);
	}

	private static State reconnectEventDestination(final State state) {
		state.setCommand(new ReconnectEventConnectionCommand(//
				state.getFbNetwork().getEventConnections().get(0),//
				false, //
				state.getFbNetwork().getNetworkElements().get(2).getInterface().getEventInputs().get(0), //
				state.getFbNetwork()));
		return commandExecution(state);
	}

	private static void reconnectEventDestinationVerifier(final State s, final State o, final TestFunction t) {
		final InterfaceList fb1 = s.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = s.getFbNetwork().getNetworkElements().get(1).getInterface();
		final InterfaceList fb3 = s.getFbNetwork().getNetworkElements().get(2).getInterface();

		t.test(s.getMessages().isEmpty());
		t.test(fb1.getEventOutputs().get(0).getOutputConnections().size(), 1);
		t.test(fb2.getEventInputs().get(0).getInputConnections().size(), 0);
		t.test(fb3.getEventInputs().get(0).getInputConnections().size(), 1);
		t.test(fb1.getEventOutputs().get(0).getOutputConnections().get(0).getDestination(),
				fb3.getEventInputs().get(0));

	}

	private static State reconnectEventSource(final State state) {
		state.setCommand(new ReconnectEventConnectionCommand(//
				state.getFbNetwork().getEventConnections().get(0),//
				true, //
				state.getFbNetwork().getNetworkElements().get(2).getInterface().getEventOutputs().get(0), //
				state.getFbNetwork()));
		return commandExecution(state);
	}

	private static void reconnectEventSourceVerifier(final State s, final State o, final TestFunction t) {
		final InterfaceList fb1 = s.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = s.getFbNetwork().getNetworkElements().get(1).getInterface();
		final InterfaceList fb3 = s.getFbNetwork().getNetworkElements().get(2).getInterface();

		t.test(s.getMessages().isEmpty());
		t.test(fb1.getEventOutputs().get(0).getOutputConnections().size(), 0);
		t.test(fb2.getEventInputs().get(0).getInputConnections().size(), 1);
		t.test(fb3.getEventOutputs().get(0).getOutputConnections().size(), 1);
		t.test(fb2.getEventInputs().get(0).getInputConnections().get(0).getSource(),
				fb3.getEventOutputs().get(0));

	}

	private static State reconnectDataDestination(final State state) {
		state.setCommand(new ReconnectDataConnectionCommand(//
				state.getFbNetwork().getDataConnections().get(0),//
				false, //
				state.getFbNetwork().getNetworkElements().get(2).getInterface().getInputVars().get(0), //
				state.getFbNetwork()));
		return commandExecution(state);
	}

	private static void reconnectDataDestinationVerifier(final State s, final State o, final TestFunction t) {
		final InterfaceList fb1 = s.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = s.getFbNetwork().getNetworkElements().get(1).getInterface();
		final InterfaceList fb3 = s.getFbNetwork().getNetworkElements().get(2).getInterface();

		t.test(s.getMessages().isEmpty());
		t.test(fb1.getOutputVars().get(0).getOutputConnections().size(), 1);
		t.test(fb2.getInputVars().get(0).getInputConnections().size(), 0);
		t.test(fb3.getInputVars().get(0).getInputConnections().size(), 1);
		t.test(fb1.getOutputVars().get(0).getOutputConnections().get(0).getDestination(), fb3.getInputVars().get(0));

	}

	private static State reconnectDataSource(final State state) {
		state.setCommand(new ReconnectDataConnectionCommand(//
				state.getFbNetwork().getDataConnections().get(0),//
				true, //
				state.getFbNetwork().getNetworkElements().get(2).getInterface().getOutputVars().get(0), //
				state.getFbNetwork()));
		return commandExecution(state);
	}

	private static void reconnectDataSourceVerifier(final State s, final State o, final TestFunction t) {
		final InterfaceList fb1 = s.getFbNetwork().getNetworkElements().get(0).getInterface();
		final InterfaceList fb2 = s.getFbNetwork().getNetworkElements().get(1).getInterface();
		final InterfaceList fb3 = s.getFbNetwork().getNetworkElements().get(2).getInterface();

		t.test(s.getMessages().isEmpty());
		t.test(fb1.getOutputVars().get(0).getOutputConnections().size(), 0);
		t.test(fb2.getInputVars().get(0).getInputConnections().size(), 1);
		t.test(fb3.getOutputVars().get(0).getOutputConnections().size(), 1);
		t.test(fb2.getInputVars().get(0).getInputConnections().get(0).getSource(), fb3.getOutputVars().get(0));

	}

	private static void hasErrorMessage(final State s, final State o, final TestFunction t) {
		t.test(!s.getMessages().isEmpty());
	}

	private static void hasNoErrorMessage(final State s, final State o, final TestFunction t) {
		t.test(s.getMessages().isEmpty());
	}

	private static State connectDataOutputToOutput(final State state) {
		final InterfaceList fb3 = state.getFbNetwork().getNetworkElements().get(2).getInterface();
		state.setCommand(new ReconnectDataConnectionCommand(//
				state.getFbNetwork().getDataConnections().get(0),//
				false, //
				fb3.getOutputVars().get(0), //
				state.getFbNetwork()));
		return disabledCommandExecution(state);
	}

	private static State connectDataInputToInput(final State state) {
		final InterfaceList fb3 = state.getFbNetwork().getNetworkElements().get(2).getInterface();
		state.setCommand(new ReconnectDataConnectionCommand(//
				state.getFbNetwork().getDataConnections().get(0),//
				true, //
				fb3.getInputVars().get(0), //
				state.getFbNetwork()));
		return disabledCommandExecution(state);
	}

	private static State connectEventOutputToOutput(final State state) {
		final InterfaceList fb3 = state.getFbNetwork().getNetworkElements().get(2).getInterface();
		state.setCommand(new ReconnectEventConnectionCommand(//
				state.getFbNetwork().getEventConnections().get(0),//
				false, //
				fb3.getEventOutputs().get(0), //
				state.getFbNetwork()));
		return disabledCommandExecution(state);
	}

	private static State connectEventInputToInput(final State state) {
		final InterfaceList fb3 = state.getFbNetwork().getNetworkElements().get(2).getInterface();
		state.setCommand(new ReconnectEventConnectionCommand(//
				state.getFbNetwork().getEventConnections().get(0),//
				true, //
				fb3.getEventInputs().get(0), //
				state.getFbNetwork()));
		return disabledCommandExecution(state);
	}

	// parameter creation function
	protected static Collection<Arguments> data() {
		final Collection<Arguments> commands = new ArrayList<>();

		commands.addAll(threeFunctionBlocks(List.of( //
				new ExecutionDescription<>("Reconnect EventConnection to third FB", //$NON-NLS-1$
						ReconnectCommandsTest::reconnectEventDestination,//
						ReconnectCommandsTest::reconnectEventDestinationVerifier//
						),//
				new ExecutionDescription<>("Reconnect DataConnection to third FB", //$NON-NLS-1$
						ReconnectCommandsTest::reconnectDataDestination,//
						ReconnectCommandsTest::reconnectDataDestinationVerifier//
						) //
				)));

		commands.addAll(threeFunctionBlocks(List.of( //
				new ExecutionDescription<>("Reconnect Source of EventConnection to third FB", //$NON-NLS-1$
						ReconnectCommandsTest::reconnectEventSource,//
						ReconnectCommandsTest::reconnectEventSourceVerifier//
						), //
				new ExecutionDescription<>("Reconnect Source of EventConnection to third FB", //$NON-NLS-1$
						ReconnectCommandsTest::reconnectDataSource,//
						ReconnectCommandsTest::reconnectDataSourceVerifier//
						) //
				)));

		commands.addAll(threeFunctionBlocks(List.of( //
				new ExecutionDescription<>("Reconnect Data Output to Data Output", //$NON-NLS-1$
						ReconnectCommandsTest::connectDataOutputToOutput, //
						ReconnectCommandsTest::hasErrorMessage))));
		commands.addAll(threeFunctionBlocks(List.of( //
				new ExecutionDescription<>("Reconnect Data Input to Data Input", //$NON-NLS-1$
						ReconnectCommandsTest::connectDataInputToInput, //
						ReconnectCommandsTest::hasErrorMessage))));
		commands.addAll(threeFunctionBlocks(List.of( //
				new ExecutionDescription<>("Reconnect Event Output to Event Output", //$NON-NLS-1$
						ReconnectCommandsTest::connectEventOutputToOutput, //
						ReconnectCommandsTest::hasNoErrorMessage)))); // For events there is no error message...
		commands.addAll(threeFunctionBlocks(List.of( //
				new ExecutionDescription<>("Reconnect Event Input to Event Input", //$NON-NLS-1$
						ReconnectCommandsTest::connectEventInputToInput, //
						ReconnectCommandsTest::hasNoErrorMessage)))); // For events there is no error message...

		return commands;
	}
}
