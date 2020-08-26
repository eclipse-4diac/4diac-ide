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
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.junit.runners.Parameterized.Parameters;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateInterfaceElementCommandInputsTest extends CreateInterfaceElementCommandTestBase {

	private static State executeCommandInputWithoutName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.BOOL),
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ true, /* index */ 0));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateInputWithoutName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(!interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getInputVars().size() == oldInterfacelist.getInputVars().size() + 1);
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(null != interfacelist.getInterfaceElement(FordiacKeywords.DATA_INPUT));
		t.test(interfacelist.getInterfaceElement(FordiacKeywords.DATA_INPUT).getTypeName()
				.equals(FordiacKeywords.BOOL));
	}

	private static State executeCommandInputWithName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.DWORD), "MyInput", //$NON-NLS-1$
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ true, /* index */ 1));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateInputWithName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(!interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getInputVars().size() == oldInterfacelist.getInputVars().size() + 1);
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(null != interfacelist.getInterfaceElement("MyInput")); //$NON-NLS-1$
		t.test(interfacelist.getInterfaceElement("MyInput").getTypeName().equals(FordiacKeywords.DWORD)); //$NON-NLS-1$
	}

	private static State executeCommandInputWithNameNull(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.DWORD), null,
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ true, /* index */ 1));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();

		return state;
	}

	private static void verifyStateInputWithNameNull(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(!interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getInputVars().size() == oldInterfacelist.getInputVars().size() + 1);
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(null != interfacelist.getInterfaceElement("DI2")); //$NON-NLS-1$
		t.test(interfacelist.getInterfaceElement("DI2").getTypeName().equals(FordiacKeywords.DWORD)); //$NON-NLS-1$
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<>("Add Input without name", // //$NON-NLS-1$
						CreateInterfaceElementCommandInputsTest::executeCommandInputWithoutName, //
						CreateInterfaceElementCommandInputsTest::verifyStateInputWithoutName //
				), //
				new ExecutionDescription<>("Add Input with name \"MyInput\"", // //$NON-NLS-1$
						CreateInterfaceElementCommandInputsTest::executeCommandInputWithName, //
						CreateInterfaceElementCommandInputsTest::verifyStateInputWithName //
				), //
				new ExecutionDescription<>("Add Input with null as name", // //$NON-NLS-1$
						CreateInterfaceElementCommandInputsTest::executeCommandInputWithNameNull, //
						CreateInterfaceElementCommandInputsTest::verifyStateInputWithNameNull //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
