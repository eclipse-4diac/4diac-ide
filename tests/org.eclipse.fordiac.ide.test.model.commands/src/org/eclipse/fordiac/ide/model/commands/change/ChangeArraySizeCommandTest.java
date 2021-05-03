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
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.ValueCommandTestBase;
import org.junit.jupiter.params.provider.Arguments;

public class ChangeArraySizeCommandTest extends ValueCommandTestBase {

	private static State executeCommand(State state, String newSize) {
		state.setCommand(new ChangeArraySizeCommand(state.getVar(), newSize));

		return commandExecution(state);
	}

	private static State executeCommandOnNull(State state) {
		state.setCommand(new ChangeArraySizeCommand(null, "123"));

		return disabledCommandExecution(state);
	}

	private static void verifyState(State state, State oldState, TestFunction t, int newSize) {
		t.test(state.getVar().getArraySize(), newSize);
		t.test(state.getVar().isArray(), (newSize > 0));
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Change Array Size to empty String", // //$NON-NLS-1$
						(State s) -> executeCommand(s, ""), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, 0) //
				), //
				new ExecutionDescription<>("Change Array Size to 2", // //$NON-NLS-1$
						(State s) -> executeCommand(s, "2"), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, 2) //
				), //
				new ExecutionDescription<>("Change Array Size to 0", // //$NON-NLS-1$
						(State s) -> executeCommand(s, "0"), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, 0) //
				), //
				new ExecutionDescription<>("Change Array Size to -1", // //$NON-NLS-1$
						(State s) -> executeCommand(s, "-1"), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, 0) //
				), //
				new ExecutionDescription<>("Change Array Size to abc", // //$NON-NLS-1$
						(State s) -> executeCommand(s, "abc"), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, 0) //
				));

		Collection<Arguments> unexecutable = describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				List.of(new ExecutionDescription<>("Unexecutable case: variable is null", // //$NON-NLS-1$
						ChangeArraySizeCommandTest::executeCommandOnNull, //
						CommandTestBase::verifyNothing //
				) //
				), //
				CommandTestBase::disabledUndoCommand, //
				CommandTestBase::disabledRedoCommand //
		);

		Collection<Arguments> commands = createCommands(executionDescriptions);

		commands.addAll(unexecutable);

		return commands;
	}

}