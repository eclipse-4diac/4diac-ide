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
package org.eclipse.fordiac.ide.model.commands.delete;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.insert.InsertInterfaceElementCommandTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.junit.runners.Parameterized.Parameters;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class DeleteInterfaceElementCommandTest extends FBNetworkTestBase {

	public static State executeCommandDeleteInput(State state) {
		IInterfaceElement interfaceElement = state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getInterface().getInputVars().get(0);
		state.setCommand(new DeleteInterfaceCommand(interfaceElement ));
		
		return commandExecution(state);
	}
	
	public static State executeCommandDeleteOutput(State state) {
		IInterfaceElement interfaceElement = state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getInterface().getOutputVars().get(0);
		state.setCommand(new DeleteInterfaceCommand(interfaceElement));
		
		return commandExecution(state);
	}

	public static State executeCommandDeleteEventInput(State state) {
		IInterfaceElement interfaceElement = state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getInterface().getEventInputs().get(0);
		state.setCommand(new DeleteInterfaceCommand(interfaceElement ));
		
		return commandExecution(state);
	}
	
	public static State executeCommandDeleteEventOutput(State state) {
		IInterfaceElement interfaceElement = state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getInterface().getEventOutputs().get(0);
		state.setCommand(new DeleteInterfaceCommand(interfaceElement));
		
		return commandExecution(state);
	}
	
	private static State commandExecution(State state) {
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}

	private static State fbInitializer() {
		State s = new State();
		s = FBCreateCommandTest.executeCommand(s);
		s = InsertInterfaceElementCommandTest.executeCommand(s, FordiacKeywords.DWORD, true);
		s = InsertInterfaceElementCommandTest.executeCommand(s, FordiacKeywords.DWORD, false);
		s = InsertInterfaceElementCommandTest.executeEventCommand(s, true);
		return InsertInterfaceElementCommandTest.executeEventCommand(s, false);
	}
	
	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<State>("Delete Input", //$NON-NLS-1$
						DeleteInterfaceElementCommandTest::executeCommandDeleteInput, // //$NON-NLS-1$
						(State s, State o, TestFunction t) -> {
							InsertInterfaceElementCommandTest.verifyStateNoDataInput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateHasDataOutput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateHasEventInput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateHasEventOutput(s, o, t);
						}
				), //
				new ExecutionDescription<State>("Delete Output", //$NON-NLS-1$
						DeleteInterfaceElementCommandTest::executeCommandDeleteOutput, // //$NON-NLS-1$
						(State s, State o, TestFunction t) -> {
							InsertInterfaceElementCommandTest.verifyStateNoDataInput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateNoDataOutput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateHasEventInput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateHasEventOutput(s, o, t);
						}
				), //
				new ExecutionDescription<State>("Delete Event Input", //$NON-NLS-1$
						DeleteInterfaceElementCommandTest::executeCommandDeleteEventInput, // //$NON-NLS-1$
						(State s, State o, TestFunction t) -> {
							InsertInterfaceElementCommandTest.verifyStateNoDataInput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateNoDataOutput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateNoEventInput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateHasEventOutput(s, o, t);
						}
				), //
				new ExecutionDescription<State>("Delete Event Output", //$NON-NLS-1$
						DeleteInterfaceElementCommandTest::executeCommandDeleteEventOutput, // //$NON-NLS-1$
						(State s, State o, TestFunction t) -> {
							InsertInterfaceElementCommandTest.verifyStateNoDataInput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateNoDataOutput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateNoEventInput(s, o, t);
							InsertInterfaceElementCommandTest.verifyStateNoEventOutput(s, o, t);
						}
				) //
		);

		final List<Object[]> commands = new ArrayList<>();


		
		commands.addAll(describeCommand("Start with Functionblock", // //$NON-NLS-1$
				() -> fbInitializer(), //
				(State s, State o, TestFunction t) -> {
					InsertInterfaceElementCommandTest.verifyStateHasDataInput(s, o, t);
					InsertInterfaceElementCommandTest.verifyStateHasDataOutput(s, o, t);
					InsertInterfaceElementCommandTest.verifyStateHasEventInput(s, o, t);
					InsertInterfaceElementCommandTest.verifyStateHasEventOutput(s, o, t);
				}, //
				executionDescriptions //
		));

		return commands;
	}

}
