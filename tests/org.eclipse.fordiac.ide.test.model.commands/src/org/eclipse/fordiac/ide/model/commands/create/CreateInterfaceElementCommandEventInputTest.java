/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
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

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.testinfra.CreateInterfaceElementCommandTestBase;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.junit.runners.Parameterized.Parameters;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateInterfaceElementCommandEventInputTest extends CreateInterfaceElementCommandTestBase {

	private static State executeCommandInputWithoutName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null),
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ true, /* index */ 0));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateInputWithoutName(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().isEmpty());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().size() == 1);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs().isEmpty());
		t.test(null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getInterfaceElement("EI1"));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInterfaceElement("EI1").getTypeName()
				.equals("Event"));
	}

	private static State executeCommandInputWithName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), "MyInput",
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ true, /* index */ 1));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateInputWithName(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().isEmpty());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().size() == 2);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs().isEmpty());
		t.test(null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getInterfaceElement("MyInput"));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInterfaceElement("MyInput")
				.getTypeName().equals("Event"));
	}

	private static State executeCommandInputWithNameNull(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), null,
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ true, /* index */ 1));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateInputWithNameNull(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().isEmpty());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().size() == 3);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs().isEmpty());
		t.test(null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getInterfaceElement("EI2"));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInterfaceElement("EI2").getTypeName()
				.equals("Event"));
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<State>("Add Event Input without name", //
						CreateInterfaceElementCommandEventInputTest::executeCommandInputWithoutName, //
						CreateInterfaceElementCommandEventInputTest::verifyStateInputWithoutName //
				), //
				new ExecutionDescription<State>("Add Event Input with name \"MyInput\"", //
						CreateInterfaceElementCommandEventInputTest::executeCommandInputWithName, //
						CreateInterfaceElementCommandEventInputTest::verifyStateInputWithName //
				), //
				new ExecutionDescription<State>("Add Event Input with null as name", //
						CreateInterfaceElementCommandEventInputTest::executeCommandInputWithNameNull, //
						CreateInterfaceElementCommandEventInputTest::verifyStateInputWithNameNull //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
