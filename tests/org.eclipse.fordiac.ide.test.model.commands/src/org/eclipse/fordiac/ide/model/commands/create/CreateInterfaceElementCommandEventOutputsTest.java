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

import org.eclipse.fordiac.ide.model.commands.testinfra.CreateInterfaceElementCommandTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateInterfaceElementCommandEventOutputsTest extends CreateInterfaceElementCommandTestBase {

	private static State executeCommandOutputWithoutName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null),
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ false, /* index */ 0));

		return commandExecution(state);
	}

	private static void verifyStateOutputWithoutName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(interfacelist.getEventInputs().isEmpty());
		t.test(!interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getEventOutputs().size(), oldInterfacelist.getEventOutputs().size() + 1);
		t.test(interfacelist.getInterfaceElement("EO1")); //$NON-NLS-1$
		t.test(interfacelist.getInterfaceElement("EO1").getTypeName(),"Event"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static State executeCommandOutputWithName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), "MyOutput", //$NON-NLS-1$
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ false, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateOutputWithName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(interfacelist.getEventInputs().isEmpty());
		t.test(!interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getEventOutputs().size(), oldInterfacelist.getEventOutputs().size() + 1);
		t.test(interfacelist.getInterfaceElement("MyOutput")); //$NON-NLS-1$
		t.test(interfacelist.getInterfaceElement("MyOutput").getTypeName(), "Event"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static State executeCommandOutputWithNameNull(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), null,
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ false, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateOutputWithNameNull(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(interfacelist.getEventInputs().isEmpty());
		t.test(!interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getEventOutputs().size(), oldInterfacelist.getEventOutputs().size() + 1);
		t.test(interfacelist.getInterfaceElement("EO2")); //$NON-NLS-1$
		t.test(interfacelist.getInterfaceElement("EO2").getTypeName(), "Event"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Event Output without name", // //$NON-NLS-1$
						CreateInterfaceElementCommandEventOutputsTest::executeCommandOutputWithoutName, //
						CreateInterfaceElementCommandEventOutputsTest::verifyStateOutputWithoutName //
				), //
				new ExecutionDescription<>("Add Event Output with name \"MyOutput\"", // //$NON-NLS-1$
						CreateInterfaceElementCommandEventOutputsTest::executeCommandOutputWithName, //
						CreateInterfaceElementCommandEventOutputsTest::verifyStateOutputWithName //
				), //
				new ExecutionDescription<>("Add Event Output with null as name", // //$NON-NLS-1$
						CreateInterfaceElementCommandEventOutputsTest::executeCommandOutputWithNameNull, //
						CreateInterfaceElementCommandEventOutputsTest::verifyStateOutputWithNameNull //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
