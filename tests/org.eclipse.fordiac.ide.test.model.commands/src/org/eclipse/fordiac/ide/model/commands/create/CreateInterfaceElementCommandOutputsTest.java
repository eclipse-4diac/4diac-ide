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

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateInterfaceElementCommandTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateInterfaceElementCommandOutputsTest extends CreateInterfaceElementCommandTestBase {

	private static State executeCommandOutputWithoutName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.BOOL),
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ false, /* index */ 0));

		return commandExecution(state);
	}

	private static void verifyStateOutputWithoutName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(interfacelist.getInputVars().isEmpty());
		t.test(!interfacelist.getOutputVars().isEmpty());
		t.test(interfacelist.getOutputVars().size(), oldInterfacelist.getOutputVars().size() + 1);
		t.test(interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getInterfaceElement(FordiacKeywords.DATA_OUTPUT));
		t.test(interfacelist.getInterfaceElement(FordiacKeywords.DATA_OUTPUT).getTypeName(), FordiacKeywords.BOOL);
	}

	private static State executeCommandOutputWithName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.WORD), "MyOutput", //$NON-NLS-1$
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ false, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateOutputWithName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(interfacelist.getInputVars().isEmpty());
		t.test(!interfacelist.getOutputVars().isEmpty());
		t.test(interfacelist.getOutputVars().size(), oldInterfacelist.getOutputVars().size() + 1);
		t.test(interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getInterfaceElement("MyOutput")); //$NON-NLS-1$
		t.test(interfacelist.getInterfaceElement("MyOutput").getTypeName(), FordiacKeywords.WORD); //$NON-NLS-1$
	}

	private static State executeCommandOutputWithNameNull(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.BYTE), null,
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ false, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateOutputWithNameNull(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(interfacelist.getInputVars().isEmpty());
		t.test(!interfacelist.getOutputVars().isEmpty());
		t.test(interfacelist.getOutputVars().size(), oldInterfacelist.getOutputVars().size() + 1);
		t.test(interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getInterfaceElement("DO2")); //$NON-NLS-1$
		t.test(interfacelist.getInterfaceElement("DO2").getTypeName(), FordiacKeywords.BYTE); //$NON-NLS-1$
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Output without name", // //$NON-NLS-1$
						CreateInterfaceElementCommandOutputsTest::executeCommandOutputWithoutName, //
						CreateInterfaceElementCommandOutputsTest::verifyStateOutputWithoutName //
				), //
				new ExecutionDescription<>("Add Output with name \"MyOutput\"", // //$NON-NLS-1$
						CreateInterfaceElementCommandOutputsTest::executeCommandOutputWithName, //
						CreateInterfaceElementCommandOutputsTest::verifyStateOutputWithName //
				), //
				new ExecutionDescription<>("Add Output with null as name", // //$NON-NLS-1$
						CreateInterfaceElementCommandOutputsTest::executeCommandOutputWithNameNull, //
						CreateInterfaceElementCommandOutputsTest::verifyStateOutputWithNameNull //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
