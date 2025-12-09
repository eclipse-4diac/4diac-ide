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

	private static State executeCommandInputWithoutName(final State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null),
				getTypeInterfaceList(state), /* isInput */ true, /* index */ 0));

		return commandExecution(state);
	}

	private static void verifyInterfaceListWithName(final InterfaceList interfacelist, final String element,
			final TestFunction t) {
		t.test(interfacelist.getInputVars().isEmpty());
		t.test(interfacelist.getOutputVars().isEmpty());
		t.test(!interfacelist.getEventInputs().isEmpty());
		t.test(interfacelist.getEventOutputs().isEmpty());
		t.test(interfacelist.getInterfaceElement(element));
		t.test(interfacelist.getInterfaceElement(element).getTypeName(), EVENT_TYPE);
	}

	private static void verifyStateInputWithoutName(final State state, final State oldState, final TestFunction t) {
		final InterfaceList interfacelist = getTypeInterfaceList(state);
		final InterfaceList oldInterfacelist = getTypeInterfaceList(oldState);

		verifyInterfaceListWithName(interfacelist, ELEMENT1_NAME, t);
		t.test(interfacelist.getEventInputs().size(), oldInterfacelist.getEventInputs().size() + 1);
	}

	private static State executeCommandInputWithName(final State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), ELEMENT2_NAME,
				getTypeInterfaceList(state), /* isInput */ true, /* index */ 1));

		return commandExecution(state);
	}

	private static void verifyStateInputWithName(final State state, final State oldState, final TestFunction t) {
		final InterfaceList interfacelist = getTypeInterfaceList(state);
		final InterfaceList oldInterfacelist = getTypeInterfaceList(oldState);

		verifyInterfaceListWithName(interfacelist, ELEMENT2_NAME, t);
		t.test(interfacelist.getEventInputs().size(), oldInterfacelist.getEventInputs().size() + 1);
	}

	private static State executeCommandInputWithNameNull(final State state) {
		state.setCommand(new CreateInterfaceElementCommand(EventTypeLibrary.getInstance().getType(null), null,
				getTypeInterfaceList(state), /* isInput */ true, /* index */ 1));

		final State result = commandExecution(state);

		tester.get().test(state.getCommand() instanceof CreateInterfaceElementCommand);
		final CreateInterfaceElementCommand c = ((CreateInterfaceElementCommand) state.getCommand());
		tester.get().test(c.getInterfaceList(), getTypeInterfaceList(state));
		tester.get().test(getTypeInterfaceList(state).getInterfaceElement(ELEMENT3_NAME), c.getCreatedElement());
		tester.get().test(c.getCreatedElement(), c.getCreatedElement());

		return result;
	}

	private static void verifyStateInputWithNameNull(final State state, final State oldState, final TestFunction t) {
		final InterfaceList interfacelist = getTypeInterfaceList(state);
		final InterfaceList oldInterfacelist = getTypeInterfaceList(oldState);

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
				(final State s) -> getTypeInterfaceList(s).getEventInputs(), ELEMENT1_NAME, ELEMENT3_NAME,
				ELEMENT2_NAME);

		final Collection<ExecutionDescription<State>> updateFBandValidate = createUpdateAndValidate(
				(final State s, final State o, final TestFunction t) -> {
					final InterfaceList interfacelist = getInstanceInterfaceList(s);

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
		).toList());
	}

}
