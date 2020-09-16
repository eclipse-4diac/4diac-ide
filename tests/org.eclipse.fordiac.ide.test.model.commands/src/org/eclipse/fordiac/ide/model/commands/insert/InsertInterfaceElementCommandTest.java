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

	private static final String INTERFACE_ELEMENT = "InterfaceElement";

	public static State executeCommand(State state, String typeName, boolean isInput) {
		IInterfaceElement element = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		element.setName(INTERFACE_ELEMENT);
		DataType type = getDatatypelib().getType(typeName);

		state.setCommand(new InsertInterfaceElementCommand(element, type,
				state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getInterface(), isInput, 0));

		return commandExecution(state);
	}

	public static State executeEventCommand(State state, boolean isInput) {
		IInterfaceElement element = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		element.setName(INTERFACE_ELEMENT);
		DataType type = EventTypeLibrary.getInstance().getType(null);

		state.setCommand(new InsertInterfaceElementCommand(element, type,
				state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getInterface(), isInput, 0));

		return commandExecution(state);
	}

	public static State executeCommandWithoutType(State state, boolean isInput) {
		IInterfaceElement element = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		element.setName(INTERFACE_ELEMENT);
		DataType type = null;

		state.setCommand(new InsertInterfaceElementCommand(element, type,
				state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getInterface(), isInput, 0));

		return disabledCommandExecution(state);
	}

	public static State executeCommandWithoutInterfaceList(State state, String typeName, boolean isInput) {
		IInterfaceElement element = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		element.setName(INTERFACE_ELEMENT);
		DataType type = getDatatypelib().getType(typeName);

		state.setCommand(new InsertInterfaceElementCommand(element, type, null, isInput, 0));

		return disabledCommandExecution(state);
	}

	public static void verifyStateNoDataInput(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getInputVars().isEmpty());
	}

	public static void verifyStateNoDataOutput(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getOutputVars().isEmpty());
	}

	public static void verifyStateNoEventInput(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getEventInputs().isEmpty());
	}

	public static void verifyStateNoEventOutput(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getEventOutputs().isEmpty());
	}

	public static void verifyStateHasDataInput(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getInputVars().size(), 1);
	}

	public static void verifyStateHasDataOutput(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getOutputVars().size(), 1);
	}

	public static void verifyStateHasEventInput(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getEventInputs().size(), 1);
	}

	public static void verifyStateHasEventOutput(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getFBNamed(State.FUNCTIONBLOCK_NAME).getInterface().getEventOutputs().size(), 1);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<State>("Add Input", //$NON-NLS-1$
						(State s) -> executeCommand(s, FordiacKeywords.DWORD, true), //
						(State s, State o, TestFunction t) -> {
							verifyStateHasDataInput(s, o, t);
							verifyStateNoDataOutput(s, o, t);
							verifyStateNoEventInput(s, o, t);
							verifyStateNoEventOutput(s, o, t);
						}), //
				new ExecutionDescription<State>("Add Output", //$NON-NLS-1$
						(State s) -> executeCommand(s, FordiacKeywords.DWORD, false), //
						(State s, State o, TestFunction t) -> {
							verifyStateHasDataInput(s, o, t);
							verifyStateHasDataOutput(s, o, t);
							verifyStateNoEventInput(s, o, t);
							verifyStateNoEventOutput(s, o, t);
						}), //
				new ExecutionDescription<State>("Add Event Input", //$NON-NLS-1$
						(State s) -> executeEventCommand(s, true), //
						(State s, State o, TestFunction t) -> {
							verifyStateHasDataInput(s, o, t);
							verifyStateHasDataOutput(s, o, t);
							verifyStateHasEventInput(s, o, t);
							verifyStateNoEventOutput(s, o, t);
						}), //
				new ExecutionDescription<State>("Add Event Output", //$NON-NLS-1$
						(State s) -> executeEventCommand(s, false), //
						(State s, State o, TestFunction t) -> {
							verifyStateHasDataInput(s, o, t);
							verifyStateHasDataOutput(s, o, t);
							verifyStateHasEventInput(s, o, t);
							verifyStateHasEventOutput(s, o, t);
						}) //
		);

		final Collection<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start with Functionblock", // //$NON-NLS-1$
				() -> FBCreateCommandTest.executeCommand(new State()), //
				(State s, State o, TestFunction t) -> {
					FBCreateCommandTest.verifyState(s, o, t);
					verifyStateNoDataInput(s, o, t);
					verifyStateNoDataOutput(s, o, t);
					verifyStateNoEventInput(s, o, t);
					verifyStateNoEventOutput(s, o, t);
				}, //
				executionDescriptions //
		));

		final List<ExecutionDescription<?>> unexecutableDescriptions = List.of( //
				new ExecutionDescription<State>("Add Interface Element without type", //$NON-NLS-1$
						(State s) -> executeCommandWithoutType(s, true), //
						CommandTestBase::verifyNothing //
				), //
				new ExecutionDescription<State>("Add Interface Element without interface list", //$NON-NLS-1$
						(State s) -> executeCommandWithoutInterfaceList(s, FordiacKeywords.DWORD, true), //
						CommandTestBase::verifyNothing //
				), //
				new ExecutionDescription<State>("Add Interface Element without type", //$NON-NLS-1$
						(State s) -> executeCommandWithoutType(s, false), //
						CommandTestBase::verifyNothing //
				), //
				new ExecutionDescription<State>("Add Interface Element without interface list", //$NON-NLS-1$
						(State s) -> executeCommandWithoutInterfaceList(s, FordiacKeywords.DWORD, false), //
						CommandTestBase::verifyNothing //
				) //
		);

		commands.addAll(describeCommand("Start with Functionblock, disabled undo&redo", // //$NON-NLS-1$
				() -> FBCreateCommandTest.executeCommand(new State()), //
				(State s, State o, TestFunction t) -> FBCreateCommandTest.verifyState(s, o, t), //
				unexecutableDescriptions, //
				CommandTestBase::disabledUndoCommand, //
				CommandTestBase::disabledRedoCommand //
		));

		return commands;
	}

}
