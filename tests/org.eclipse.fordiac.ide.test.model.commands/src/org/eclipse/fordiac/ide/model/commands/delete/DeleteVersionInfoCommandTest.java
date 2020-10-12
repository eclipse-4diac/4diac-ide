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

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.create.AddNewVersionInfoCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.junit.jupiter.params.provider.Arguments;

public class DeleteVersionInfoCommandTest extends FBNetworkTestBase {

	public static void verifyDefaultState(final State state, final State oldState, final TestFunction t) {
		FBCreateCommandTest.verifyState(state, oldState, t);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo().isEmpty());
	}

	public static State executeCreateCommand(final State state) {
		state.setCommand(new AddNewVersionInfoCommand(state.getFbNetwork().getNetworkElements().get(0).getType()));
		return commandExecution(state);
	}

	public static void verifyCreatedState(final State state, final State oldState, final TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo());
		t.test(!state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo().isEmpty());
	}

	public static State executeDeleteCommand(final State state) {
		state.setCommand(new DeleteVersionInfoCommand(state.getFbNetwork().getNetworkElements().get(0).getType(),
				state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo().get(0)));
		return commandExecution(state);
	}

	public static void verifyDeletedState(final State state, final State oldState, final TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo());
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo().isEmpty());
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						DeleteVersionInfoCommandTest::verifyDefaultState //
				), //
				new ExecutionDescription<>("Add VersionInfo", //$NON-NLS-1$
						DeleteVersionInfoCommandTest::executeCreateCommand, //
						DeleteVersionInfoCommandTest::verifyCreatedState //
				), //
				new ExecutionDescription<>("Delete VersionInfo", //$NON-NLS-1$
						DeleteVersionInfoCommandTest::executeDeleteCommand, //
						DeleteVersionInfoCommandTest::verifyDeletedState //
				) //
		);
		return createCommands(executionDescriptions);
	}
}
