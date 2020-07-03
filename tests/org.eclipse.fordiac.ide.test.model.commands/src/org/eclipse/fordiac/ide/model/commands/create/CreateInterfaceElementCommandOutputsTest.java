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

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateInterfaceElementCommandTestBase;
import org.junit.runners.Parameterized.Parameters;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateInterfaceElementCommandOutputsTest extends CreateInterfaceElementCommandTestBase {

	private static State executeCommandOutputWithoutName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.BOOL),
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ false, /* index */ 0));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateOutputWithoutName(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars().isEmpty());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().size() == 1);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs().isEmpty());
		t.test(null != state.getFbNetwork().getNetworkElements().get(0).getInterface()
				.getInterfaceElement(FordiacKeywords.DATA_OUTPUT));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface()
				.getInterfaceElement(FordiacKeywords.DATA_OUTPUT).getTypeName().equals(FordiacKeywords.BOOL));
	}

	private static State executeCommandOutputWithName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.WORD), "MyOutput",
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ false, /* index */ 1));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateOutputWithName(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars().isEmpty());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().size() == 2);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs().isEmpty());
		t.test(null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getInterfaceElement("MyOutput"));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInterfaceElement("MyOutput")
				.getTypeName().equals(FordiacKeywords.WORD));
	}

	private static State executeCommandOutputWithNameNull(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.BYTE), null,
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ false, /* index */ 1));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateOutputWithNameNull(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInputVars().isEmpty());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().size() == 3);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs().isEmpty());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs().isEmpty());
		t.test(null != state.getFbNetwork().getNetworkElements().get(0).getInterface().getInterfaceElement("DO2"));
		t.test(state.getFbNetwork().getNetworkElements().get(0).getInterface().getInterfaceElement("DO2").getTypeName()
				.equals(FordiacKeywords.BYTE));
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<State>("Add Output without name", //
						CreateInterfaceElementCommandOutputsTest::executeCommandOutputWithoutName, //
						CreateInterfaceElementCommandOutputsTest::verifyStateOutputWithoutName //
				), //
				new ExecutionDescription<State>("Add Output with name \"MyOutput\"", //
						CreateInterfaceElementCommandOutputsTest::executeCommandOutputWithName, //
						CreateInterfaceElementCommandOutputsTest::verifyStateOutputWithName //
				), //
				new ExecutionDescription<State>("Add Output with null as name", //
						CreateInterfaceElementCommandOutputsTest::executeCommandOutputWithNameNull, //
						CreateInterfaceElementCommandOutputsTest::verifyStateOutputWithNameNull //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
