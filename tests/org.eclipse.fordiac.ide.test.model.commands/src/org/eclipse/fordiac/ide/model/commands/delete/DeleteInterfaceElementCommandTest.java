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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.insert.CopyInterfaceElementCommandTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class DeleteInterfaceElementCommandTest extends FBNetworkTestBase {

	public static State executeCommandDeleteInput(final State state) {
		final IInterfaceElement interfaceElement = state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME)
				.getInterface().getInputVars().get(0);
		state.setCommand(new DeleteInterfaceCommand(interfaceElement));

		return commandExecution(state);
	}

	public static State executeCommandDeleteOutput(final State state) {
		final IInterfaceElement interfaceElement = state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME)
				.getInterface().getOutputVars().get(0);
		state.setCommand(new DeleteInterfaceCommand(interfaceElement));

		return commandExecution(state);
	}

	public static State executeCommandDeleteEventInput(final State state) {
		final IInterfaceElement interfaceElement = state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME)
				.getInterface().getEventInputs().get(0);
		state.setCommand(new DeleteInterfaceCommand(interfaceElement));

		return commandExecution(state);
	}

	public static State executeCommandDeleteEventOutput(final State state) {
		final IInterfaceElement interfaceElement = state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME)
				.getInterface().getEventOutputs().get(0);
		state.setCommand(new DeleteInterfaceCommand(interfaceElement));

		return commandExecution(state);
	}

	private static State fbInitializer() {
		State s = new State();
		s = FBCreateCommandTest.executeCommand(s);
		s = CopyInterfaceElementCommandTest.executeCommand(s, FordiacKeywords.DWORD, true);
		s = CopyInterfaceElementCommandTest.executeCommand(s, FordiacKeywords.DWORD, false);
		s = CopyInterfaceElementCommandTest.executeEventCommand(s, true);
		return CopyInterfaceElementCommandTest.executeEventCommand(s, false);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Delete Data Input", //$NON-NLS-1$
						DeleteInterfaceElementCommandTest::executeCommandDeleteInput, //
						(final State s, final State o, final TestFunction t) -> {
							CopyInterfaceElementCommandTest.verifyStateNoDataInput(s, t);
							CopyInterfaceElementCommandTest.verifyStateHasDataOutput(s, t);
							CopyInterfaceElementCommandTest.verifyStateHasEventInput(s, t);
							CopyInterfaceElementCommandTest.verifyStateHasEventOutput(s, t);
						}), //
				new ExecutionDescription<>("Delete Data Output", //$NON-NLS-1$
						DeleteInterfaceElementCommandTest::executeCommandDeleteOutput, //
						(final State s, final State o, final TestFunction t) -> {
							CopyInterfaceElementCommandTest.verifyStateNoDataInput(s, t);
							CopyInterfaceElementCommandTest.verifyStateNoDataOutput(s, t);
							CopyInterfaceElementCommandTest.verifyStateHasEventInput(s, t);
							CopyInterfaceElementCommandTest.verifyStateHasEventOutput(s, t);
						}), //
				new ExecutionDescription<>("Delete Event Input", //$NON-NLS-1$
						DeleteInterfaceElementCommandTest::executeCommandDeleteEventInput, //
						(final State s, final State o, final TestFunction t) -> {
							CopyInterfaceElementCommandTest.verifyStateNoDataInput(s, t);
							CopyInterfaceElementCommandTest.verifyStateNoDataOutput(s, t);
							CopyInterfaceElementCommandTest.verifyStateNoEventInput(s, t);
							CopyInterfaceElementCommandTest.verifyStateHasEventOutput(s, t);
						}), //
				new ExecutionDescription<>("Delete Event Output", //$NON-NLS-1$
						DeleteInterfaceElementCommandTest::executeCommandDeleteEventOutput, //
						(final State s, final State o, final TestFunction t) -> {
							CopyInterfaceElementCommandTest.verifyStateNoDataInput(s, t);
							CopyInterfaceElementCommandTest.verifyStateNoDataOutput(s, t);
							CopyInterfaceElementCommandTest.verifyStateNoEventInput(s, t);
							CopyInterfaceElementCommandTest.verifyStateNoEventOutput(s, t);
						}) //
				);

		final Collection<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start with Functionblock", // //$NON-NLS-1$
				DeleteInterfaceElementCommandTest::fbInitializer, //
				(final State s, final State o, final TestFunction t) -> {
					CopyInterfaceElementCommandTest.verifyStateHasDataInput(s, t);
					CopyInterfaceElementCommandTest.verifyStateHasDataOutput(s, t);
					CopyInterfaceElementCommandTest.verifyStateHasEventInput(s, t);
					CopyInterfaceElementCommandTest.verifyStateHasEventOutput(s, t);
				}, //
				executionDescriptions //
				));

		return commands;
	}

}
