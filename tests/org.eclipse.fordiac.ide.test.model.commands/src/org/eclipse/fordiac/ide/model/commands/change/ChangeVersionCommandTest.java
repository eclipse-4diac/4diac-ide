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
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.testinfra.VersionInfoTestBase;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ChangeVersionCommandTest extends VersionInfoTestBase {

	private static final String NEW_VERSION = "3.141592"; //$NON-NLS-1$

	private static State executeCommand(State state) {
		state.setCommand(new ChangeVersionCommand(state.getVersionInfo(), NEW_VERSION));

		return commandExecution(state);
	}

	private static void verifyState(State state, State oldState, TestFunction t) {
		t.test(state.getVersionInfo().getVersion(), NEW_VERSION);
		t.test(state.getVersionInfo().getDate(), oldState.getVersionInfo().getDate());
		t.test(state.getVersionInfo().getOrganization(), oldState.getVersionInfo().getOrganization());
		t.test(state.getVersionInfo().getRemarks(), oldState.getVersionInfo().getRemarks());
		t.test(state.getVersionInfo().getAuthor(), oldState.getVersionInfo().getAuthor());
	}

	private static State executeCommandToNull(State state) {
		state.setCommand(new ChangeVersionCommand(state.getVersionInfo(), null));

		return commandExecution(state);
	}

	private static void verifyStateNull(State state, State oldState, TestFunction t) {
		t.test(state.getVersionInfo().getVersion(), EMPTY);
		t.test(state.getVersionInfo().getDate(), oldState.getVersionInfo().getDate());
		t.test(state.getVersionInfo().getOrganization(), oldState.getVersionInfo().getOrganization());
		t.test(state.getVersionInfo().getRemarks(), oldState.getVersionInfo().getRemarks());
		t.test(state.getVersionInfo().getAuthor(), oldState.getVersionInfo().getAuthor());
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Change Version", // //$NON-NLS-1$
						ChangeVersionCommandTest::executeCommand, //
						ChangeVersionCommandTest::verifyState //
				), //
				new ExecutionDescription<>("Change Version to null", // //$NON-NLS-1$
						ChangeVersionCommandTest::executeCommandToNull, //
						ChangeVersionCommandTest::verifyStateNull //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
