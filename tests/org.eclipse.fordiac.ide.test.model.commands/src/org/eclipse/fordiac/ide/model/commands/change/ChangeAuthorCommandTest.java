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
 *   Bianca Wiesmayr, Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.testinfra.VersionInfoTestBase;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ChangeAuthorCommandTest extends VersionInfoTestBase {

	private static final String NEW_AUTHOR = "new author"; //$NON-NLS-1$

	private static State executeCommand(State state) {
		state.setCommand(new ChangeAuthorCommand(state.getVersionInfo(), NEW_AUTHOR));

		return commandExecution(state);
	}

	private static void verifyState(State state, State oldState, TestFunction t) {
		t.test(state.getVersionInfo().getAuthor(), NEW_AUTHOR);
		t.test(state.getVersionInfo().getDate(), oldState.getVersionInfo().getDate());
		t.test(state.getVersionInfo().getOrganization(), oldState.getVersionInfo().getOrganization());
		t.test(state.getVersionInfo().getRemarks(), oldState.getVersionInfo().getRemarks());
		t.test(state.getVersionInfo().getVersion(), oldState.getVersionInfo().getVersion());
	}

	private static State executeCommandToNull(State state) {
		state.setCommand(new ChangeAuthorCommand(state.getVersionInfo(), null));

		return commandExecution(state);
	}

	private static void verifyStateNull(State state, State oldState, TestFunction t) {
		t.test(state.getVersionInfo().getAuthor(), EMPTY);
		t.test(state.getVersionInfo().getDate(), oldState.getVersionInfo().getDate());
		t.test(state.getVersionInfo().getOrganization(), oldState.getVersionInfo().getOrganization());
		t.test(state.getVersionInfo().getRemarks(), oldState.getVersionInfo().getRemarks());
		t.test(state.getVersionInfo().getVersion(), oldState.getVersionInfo().getVersion());
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Change Author", // //$NON-NLS-1$
						ChangeAuthorCommandTest::executeCommand, //
						ChangeAuthorCommandTest::verifyState //
				), //
				new ExecutionDescription<>("Change Author to null", // //$NON-NLS-1$
						ChangeAuthorCommandTest::executeCommandToNull, //
						ChangeAuthorCommandTest::verifyStateNull //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
