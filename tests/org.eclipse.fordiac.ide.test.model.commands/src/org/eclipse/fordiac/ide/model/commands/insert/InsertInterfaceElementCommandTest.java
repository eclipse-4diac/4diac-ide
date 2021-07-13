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
package org.eclipse.fordiac.ide.model.commands.insert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class InsertInterfaceElementCommandTest extends FBNetworkTestBase {

	private static final String INTERFACE_ELEMENT = "InterfaceElement"; //$NON-NLS-1$

	public static State executeCommand(final State state, final String typeName, final boolean isInput) {
		final IInterfaceElement element = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		element.setIsInput(isInput);
		element.setName(INTERFACE_ELEMENT);
		element.setTypeName(typeName);
		final DataType type = getDatatypelib().getType(typeName);

		state.setCommand(new InsertInterfaceElementCommand(element, type,
				state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getInterface(), isInput, 0));

		return commandExecution(state);
	}

	public static State executeEventCommand(final State state, final boolean isInput) {
		final IInterfaceElement element = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		element.setName(INTERFACE_ELEMENT);
		final DataType type = EventTypeLibrary.getInstance().getType(null);

		state.setCommand(new InsertInterfaceElementCommand(element, type,
				state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getInterface(), isInput, 0));

		return commandExecution(state);
	}

	public static State executeCommandWithoutType(final State state, final boolean isInput) {
		final IInterfaceElement element = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		element.setName(INTERFACE_ELEMENT);
		final DataType type = null;

		state.setCommand(new InsertInterfaceElementCommand(element, type,
				state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getInterface(), isInput, 0));

		return disabledCommandExecution(state);
	}

	public static State executeCommandWithoutInterfaceList(final State state, final String typeName, final boolean isInput) {
		final IInterfaceElement element = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		element.setName(INTERFACE_ELEMENT);
		final DataType type = getDatatypelib().getType(typeName);

		state.setCommand(new InsertInterfaceElementCommand(element, type, null, isInput, 0));

		return disabledCommandExecution(state);
	}

	public static void verifyStateNoDataInput(final State state, final TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getInputVars().isEmpty());
	}

	public static void verifyStateNoDataOutput(final State state, final TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getOutputVars().isEmpty());
	}

	public static void verifyStateNoEventInput(final State state, final TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getEventInputs().isEmpty());
	}

	public static void verifyStateNoEventOutput(final State state, final TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getEventOutputs().isEmpty());
	}

	public static void verifyStateHasDataInput(final State state, final TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getInputVars().size(), 1);
	}

	private static void verifyGetters(final State state, final TestFunction t) {
		t.test(state.getCommand() instanceof InsertInterfaceElementCommand);
		final InsertInterfaceElementCommand c = ((InsertInterfaceElementCommand) state.getCommand());
		t.test(c.isInput(), true);
		t.test(c.getInterfaceList(), state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface());
		t.test(c.getDataType(), getDatatypelib().getType(FordiacKeywords.DWORD));
		t.test(c.getEntry());
		t.test(c.getInterfaceElement());
	}

	public static void verifyStateHasDataOutput(final State state, final TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getOutputVars().size(), 1);
	}

	public static void verifyStateHasEventInput(final State state, final TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getEventInputs().size(), 1);
	}

	public static void verifyStateHasEventOutput(final State state, final TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getEventOutputs().size(), 1);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Input", //$NON-NLS-1$
						(final State s) -> {
							final State result = executeCommand(s, FordiacKeywords.DWORD, true);
							verifyGetters(s, tester.get());
							return result;
						}, //
						(final State s, final State o, final TestFunction t) -> {
							verifyStateHasDataInput(s, t);
							verifyStateNoDataOutput(s, t);
							verifyStateNoEventInput(s, t);
							verifyStateNoEventOutput(s, t);
						}), //
				new ExecutionDescription<>("Add Output", //$NON-NLS-1$
						(final State s) -> executeCommand(s, FordiacKeywords.DWORD, false), //
						(final State s, final State o, final TestFunction t) -> {
							verifyStateHasDataInput(s, t);
							verifyStateHasDataOutput(s, t);
							verifyStateNoEventInput(s, t);
							verifyStateNoEventOutput(s, t);
						}), //
				new ExecutionDescription<>("Add Event Input", //$NON-NLS-1$
						(final State s) -> executeEventCommand(s, true), //
						(final State s, final State o, final TestFunction t) -> {
							verifyStateHasDataInput(s, t);
							verifyStateHasDataOutput(s, t);
							verifyStateHasEventInput(s, t);
							verifyStateNoEventOutput(s, t);
						}), //
				new ExecutionDescription<>("Add Event Output", //$NON-NLS-1$
						(final State s) -> executeEventCommand(s, false), //
						(final State s, final State o, final TestFunction t) -> {
							verifyStateHasDataInput(s, t);
							verifyStateHasDataOutput(s, t);
							verifyStateHasEventInput(s, t);
							verifyStateHasEventOutput(s, t);
						}) //
				);

		final Collection<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start with Functionblock", // //$NON-NLS-1$
				() -> FBCreateCommandTest.executeCommand(new State()), //
				(final State s, final State o, final TestFunction t) -> {
					FBCreateCommandTest.verifyState(s, o, t);
					verifyStateNoDataInput(s, t);
					verifyStateNoDataOutput(s, t);
					verifyStateNoEventInput(s, t);
					verifyStateNoEventOutput(s, t);
				}, //
				executionDescriptions //
				));

		final List<ExecutionDescription<?>> unexecutableDescriptions = List.of( //
				new ExecutionDescription<>("Add Interface Element without type", //$NON-NLS-1$
						(final State s) -> executeCommandWithoutType(s, true), //
						CommandTestBase::verifyNothing //
						), //
				new ExecutionDescription<>("Add Interface Element without interface list", //$NON-NLS-1$
						(final State s) -> executeCommandWithoutInterfaceList(s, FordiacKeywords.DWORD, true), //
						CommandTestBase::verifyNothing //
						), //
				new ExecutionDescription<>("Add Interface Element without type", //$NON-NLS-1$
						(final State s) -> executeCommandWithoutType(s, false), //
						CommandTestBase::verifyNothing //
						), //
				new ExecutionDescription<>("Add Interface Element without interface list", //$NON-NLS-1$
						(final State s) -> executeCommandWithoutInterfaceList(s, FordiacKeywords.DWORD, false), //
						CommandTestBase::verifyNothing //
						) //
				);

		commands.addAll(describeCommand("Start with Functionblock, disabled undo&redo", // //$NON-NLS-1$
				() -> FBCreateCommandTest.executeCommand(new State()), //
				(StateVerifier<State>) FBCreateCommandTest::verifyState, //
				unexecutableDescriptions, //
				CommandTestBase::disabledUndoCommand, //
				CommandTestBase::disabledRedoCommand //
				));

		return commands;
	}

}
