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
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class CreateInterfaceElementCommandInputsTest extends CreateInterfaceElementCommandTestBase {

	private static final String ELEMENT1_NAME = FordiacKeywords.DATA_INPUT;
	private static final String ELEMENT2_NAME = "MyInput"; //$NON-NLS-1$
	private static final String ELEMENT3_NAME = "DI2"; //$NON-NLS-1$

	private static State executeCommandInputWithoutName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.BOOL),
				getTypeInterfaceList(state), /* isInput */ true, /* index */ 0));

		return commandExecution(state);
	}

	private static void verifyInterfaceListWithName(InterfaceList interfacelist, String element, String type,
			TestFunction t) {
		t.test(!interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getInterfaceElement(element));
		t.test(interfacelist.getInterfaceElement(element).getTypeName(), type);
	}

	private static void verifyStateInputWithoutName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = getTypeInterfaceList(state);
		InterfaceList oldInterfacelist = getTypeInterfaceList(oldState);

		verifyInterfaceListWithName(interfacelist, ELEMENT1_NAME, FordiacKeywords.BOOL, t);
		t.test(interfacelist.getInputVars().size(), oldInterfacelist.getInputVars().size() + 1);
	}

	private static State executeCommandInputWithName(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.DWORD),
				ELEMENT2_NAME, getTypeInterfaceList(state), /* isInput */ true, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateInputWithName(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = getTypeInterfaceList(state);
		InterfaceList oldInterfacelist = getTypeInterfaceList(oldState);

		verifyInterfaceListWithName(interfacelist, ELEMENT2_NAME, FordiacKeywords.DWORD, t);
		t.test(interfacelist.getInputVars().size(), oldInterfacelist.getInputVars().size() + 1);
	}

	private static State executeCommandInputWithNameNull(State state) {
		state.setCommand(new CreateInterfaceElementCommand(getDatatypelib().getType(FordiacKeywords.DWORD), null,
				getTypeInterfaceList(state), /* isInput */ true, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateInputWithNameNull(State state, State oldState, TestFunction t) {
		InterfaceList interfacelist = getTypeInterfaceList(state);
		InterfaceList oldInterfacelist = getTypeInterfaceList(oldState);

		verifyInterfaceListWithName(interfacelist, ELEMENT3_NAME, FordiacKeywords.DWORD, t);
		t.test(interfacelist.getInputVars().size(), oldInterfacelist.getInputVars().size() + 1);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Input without name", // //$NON-NLS-1$
						CreateInterfaceElementCommandInputsTest::executeCommandInputWithoutName, //
						CreateInterfaceElementCommandInputsTest::verifyStateInputWithoutName //
				), //
				new ExecutionDescription<>("Add Input with name \"" + ELEMENT2_NAME + "\"", // //$NON-NLS-1$ //$NON-NLS-2$
						CreateInterfaceElementCommandInputsTest::executeCommandInputWithName, //
						CreateInterfaceElementCommandInputsTest::verifyStateInputWithName //
				), //
				new ExecutionDescription<>("Add Input with null as name", // //$NON-NLS-1$
						CreateInterfaceElementCommandInputsTest::executeCommandInputWithNameNull, //
						CreateInterfaceElementCommandInputsTest::verifyStateInputWithNameNull //
				) //
		);

		final Collection<ExecutionDescription<State>> reordering = createReordering(
				(State s) -> getTypeInterfaceList(s).getInputVars(), ELEMENT1_NAME, ELEMENT3_NAME, ELEMENT2_NAME);

		final Collection<ExecutionDescription<State>> updateFBandValidate = createUpdateAndValidate(
				(State s, State o, TestFunction t) -> {
					InterfaceList interfacelist = getInstanceInterfaceList(s);

					verifyInterfaceListWithName(interfacelist, ELEMENT1_NAME, FordiacKeywords.BOOL, t);
					verifyInterfaceListWithName(interfacelist, ELEMENT2_NAME, FordiacKeywords.DWORD, t);
					verifyInterfaceListWithName(interfacelist, ELEMENT3_NAME, FordiacKeywords.DWORD, t);
					t.test(interfacelist.getInputVars().size(), 3);
				});

		return createCommands(Stream.concat( //
				Stream.concat( //
						executionDescriptions.stream(), //
						reordering.stream()), //
				updateFBandValidate.stream() //
		).collect(Collectors.toList()));
	}

}
