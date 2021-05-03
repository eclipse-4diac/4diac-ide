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

import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.junit.jupiter.params.provider.Arguments;

public class ChangeCommentCommandTest extends FBNetworkTestBase {

	private static final String COMMENT1 = "first comment"; //$NON-NLS-1$
	private static final String COMMENT2 = "second comment"; //$NON-NLS-1$

	public static State executeCommand(State state, String comment) {
		state.setCommand(new ChangeCommentCommand(state.getFbNetwork().getNetworkElements().get(0).getType(), comment));

		return commandExecution(state);
	}

	public static void verifyStateBefore(State state, State oldState, TestFunction t) {
		FBCreateCommandTest.verifyState(state, oldState, t);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getComment().isEmpty());
	}

	public static void verifyState(State state, State oldState, TestFunction t, String comment) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getComment(), comment);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						ChangeCommentCommandTest::verifyStateBefore //
				), //
				new ExecutionDescription<>("Change comment", //$NON-NLS-1$
						(State state) -> executeCommand(state, COMMENT1), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, COMMENT1) //
				), //
				new ExecutionDescription<>("Change comment", //$NON-NLS-1$
						(State state) -> executeCommand(state, COMMENT2), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, COMMENT2) //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
