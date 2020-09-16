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

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.testinfra.ValueCommandTestBase;
import org.junit.jupiter.params.provider.Arguments;

public class ChangeValueCommandTest extends ValueCommandTestBase {

	private static final String NEW_VALUE = "new"; //$NON-NLS-1$

	private static State executeCommand(State state) {
		state.setCommand(new ChangeValueCommand(state.getVar(), NEW_VALUE));

		return commandExecution(state);
	}

	private static void verifyState(State state, State oldState, TestFunction t) {
		t.test(state.getVar().getValue().getValue(), NEW_VALUE);
	}

	private static State executeCommandToNull(State state) {
		state.setCommand(new ChangeValueCommand(state.getVar(), null));

		return commandExecution(state);
	}

	private static void verifyStateNull(State state, State oldState, TestFunction t) {
		t.test(state.getVar().getValue().getValue().isEmpty());
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Change Value", // //$NON-NLS-1$
						ChangeValueCommandTest::executeCommand, //
						ChangeValueCommandTest::verifyState //
				), //
				new ExecutionDescription<>("Change Value to null", // //$NON-NLS-1$
						ChangeValueCommandTest::executeCommandToNull, //
						ChangeValueCommandTest::verifyStateNull //
				) //
		);

		return createCommands(executionDescriptions);
	}

}