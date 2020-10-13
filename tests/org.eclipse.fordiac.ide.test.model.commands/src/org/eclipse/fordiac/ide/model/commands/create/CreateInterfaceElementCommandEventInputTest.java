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
				getTypeInterfaceList(state), /* isInput */ true, /* index */ 0));

		return commandExecution(state);
	}

	private static void verifyInterfaceListWithName(InterfaceList interfacelist, String element, TestFunction t) {
		t.test(interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(!interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getInterfaceElement(element));
		t.test(interfacelist.getInterfaceElement(element).getTypeName(), EVENT_TYPE);
	}

	private static void verifyStateInputWithoutName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = getTypeInterfaceList(state);
		InterfaceList oldInterfacelist = getTypeInterfaceList(oldState);

		verifyInterfaceListWithName(interfacelist, ELEMENT1_NAME, t);
		t.test(interfacelist.getEventInputs().size(), oldInterfacelist.getEventInputs().size() + 1);
	}

	private static State executeCommandInputWithName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), ELEMENT2_NAME,
				getTypeInterfaceList(state), /* isInput */ true, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateInputWithName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = getTypeInterfaceList(state);
		InterfaceList oldInterfacelist = getTypeInterfaceList(oldState);

		verifyInterfaceListWithName(interfacelist, ELEMENT2_NAME, t);
		t.test(interfacelist.getEventInputs().size(), oldInterfacelist.getEventInputs().size() + 1);
	}

	private static State executeCommandInputWithNameNull(State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), null,
				getTypeInterfaceList(state), /* isInput */ true, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateInputWithNameNull(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = getTypeInterfaceList(state);
		InterfaceList oldInterfacelist = getTypeInterfaceList(oldState);

		verifyInterfaceListWithName(interfacelist, ELEMENT3_NAME, t);
		t.test(interfacelist.getEventInputs().size(), oldInterfacelist.getEventInputs().size() + 1);
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
				(State s) -> getTypeInterfaceList(s).getEventInputs(), ELEMENT1_NAME, ELEMENT3_NAME, ELEMENT2_NAME);

		final Collection<ExecutionDescription<State>> updateFBandValidate = createUpdateAndValidate(
				(State s, State o, TestFunction t) -> {
					InterfaceList interfacelist = getInstanceInterfaceList(s);

					verifyInterfaceListWithName(interfacelist, ELEMENT1_NAME, t);
					verifyInterfaceListWithName(interfacelist, ELEMENT2_NAME, t);
					verifyInterfaceListWithName(interfacelist, ELEMENT3_NAME, t);
					t.test(interfacelist.getEventInputs().size(), 3);
				});

		return createCommands(Stream.concat( //
				Stream.concat( //
						executionDescriptions.stream(), //
						reordering.stream()), //
				updateFBandValidate.stream() //
		).collect(Collectors.toList()));
	}

}
