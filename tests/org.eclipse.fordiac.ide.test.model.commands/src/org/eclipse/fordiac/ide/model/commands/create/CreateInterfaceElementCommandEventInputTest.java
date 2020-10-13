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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateInterfaceElementCommandTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateInterfaceElementCommandEventInputTest extends CreateInterfaceElementCommandTestBase {

	private static final String ELEMENT1_NAME = FordiacKeywords.EVENT_INPUT;
	private static final String ELEMENT2_NAME = "MyInput"; //$NON-NLS-1$
	private static final String ELEMENT3_NAME = "EI2"; //$NON-NLS-1$

	private static State executeCommandInputWithoutName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null),
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ true, /* index */ 0));

		return commandExecution(state);
	}

	private static void verifyStateInputWithoutName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(!interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventInputs().size(), oldInterfacelist.getEventInputs().size() + 1);
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getInterfaceElement(ELEMENT1_NAME));
		t.test(interfacelist.getInterfaceElement(ELEMENT1_NAME).getTypeName(), EVENT_TYPE);
	}

	private static State executeCommandInputWithName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), ELEMENT2_NAME,
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ true, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateInputWithName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(!interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventInputs().size(), oldInterfacelist.getEventInputs().size() + 1);
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getInterfaceElement(ELEMENT2_NAME));
		t.test(interfacelist.getInterfaceElement(ELEMENT2_NAME).getTypeName(), EVENT_TYPE);
	}

	private static State executeCommandInputWithNameNull(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), null,
				state.getFbNetwork().getNetworkElements().get(0).getInterface(), /* isInput */ true, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateInputWithNameNull(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = state.getFbNetwork().getNetworkElements().get(0).getInterface();
		InterfaceList oldInterfacelist = oldState.getFbNetwork().getNetworkElements().get(0).getInterface();

		t.test(interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(!interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventInputs().size(), oldInterfacelist.getEventInputs().size() + 1);
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getInterfaceElement(ELEMENT3_NAME));
		t.test(interfacelist.getInterfaceElement(ELEMENT3_NAME).getTypeName(), EVENT_TYPE);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Event Input without name", // //$NON-NLS-1$
						CreateInterfaceElementCommandEventInputTest::executeCommandInputWithoutName, //
						CreateInterfaceElementCommandEventInputTest::verifyStateInputWithoutName //
				), //
				new ExecutionDescription<>("Add Event Input with name \"" + ELEMENT2_NAME + "\"", // //$NON-NLS-1$ //$NON-NLS-2$
						CreateInterfaceElementCommandEventInputTest::executeCommandInputWithName, //
						CreateInterfaceElementCommandEventInputTest::verifyStateInputWithName //
				), //
				new ExecutionDescription<>("Add Event Input with null as name", // //$NON-NLS-1$
						CreateInterfaceElementCommandEventInputTest::executeCommandInputWithNameNull, //
						CreateInterfaceElementCommandEventInputTest::verifyStateInputWithNameNull //
				) //
		);

		final Collection<ExecutionDescription<State>> reordering = createReordering(
				(State s) -> s.getFbNetwork().getNetworkElements().get(0).getInterface().getEventInputs(),
				ELEMENT1_NAME, ELEMENT3_NAME, ELEMENT2_NAME);

		return createCommands(
				Stream.concat(executionDescriptions.stream(), reordering.stream()).collect(Collectors.toList()));
	}

}
