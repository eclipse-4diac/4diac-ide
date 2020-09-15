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
package org.eclipse.fordiac.ide.model.commands.testinfra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.junit.jupiter.params.provider.Arguments;

public class UnexecutableCommandTest extends CommandTestBase<CommandTestBase.StateBase> {

	public static class State implements StateBase {

		Command cmd;

		@Override
		public Command getCommand() {
			return cmd;
		}

		@Override
		public void setCommand(Command cmd) {
			this.cmd = cmd;
		}

		@Override
		public Object getClone() {
			return new State(this);
		}

		public State() {
		}

		private State(State s) {
		}

	}

	protected static State undoCommand(Object stateObj) {
		final State state = (State) stateObj;
		assumeFalse(state.getCommand().canUndo());
		return (state);
	}

	protected static State redoCommand(Object stateObj) {
		final State state = (State) stateObj;
		assumeFalse(state.getCommand().canRedo());
		return (state);
	}

	protected static Collection<Arguments> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands,
				UnexecutableCommandTest::undoCommand, UnexecutableCommandTest::redoCommand);
	}

	protected static void verifyNothing(State state, State oldState, TestFunction t) {
		//
	}

	private static State executeCommand(State state) {
		state.setCommand(UnexecutableCommand.INSTANCE);
		assumeNotNull(state.getCommand());
		assumeFalse(state.getCommand().canExecute());
		return state;
	}

	protected static Collection<Arguments> createCommands(List<ExecutionDescription<?>> executionDescriptions) {
		final List<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyNothing(state, oldState, t), //
				executionDescriptions //
		));

		return commands;
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Check if unexecutable command is not executable", //$NON-NLS-1$
						UnexecutableCommandTest::executeCommand, //
						UnexecutableCommandTest::verifyNothing //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
