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
 *   Lisa Sonnleithner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.testinfra.ValueCommandTestBase;
import org.junit.runners.Parameterized.Parameters;

public class ChangeValueCommandTest extends ValueCommandTestBase {

	private static final String NEW_VALUE = "new";

	private static State executeCommand(State state) {
		state.setCommand(new ChangeValueCommand(state.getVar(), NEW_VALUE));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}

	private static void verifyState(State state, State oldState, TestFunction t) {
		t.test(state.getVar().getValue().getValue().equals(NEW_VALUE));
	}

	private static State executeCommandToNull(State state) {
		state.setCommand(new ChangeValueCommand(state.getVar(), null));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}

	private static void verifyStateNull(State state, State oldState, TestFunction t) {
		t.test(state.getVar().getValue().getValue().isEmpty());
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<State>("Change Value", //
						ChangeValueCommandTest::executeCommand, //
						ChangeValueCommandTest::verifyState //
				), //
				new ExecutionDescription<State>("Change Value to null", //
						ChangeValueCommandTest::executeCommandToNull, //
						ChangeValueCommandTest::verifyStateNull //
				) //
		);

		return createCommands(executionDescriptions);
	}

}