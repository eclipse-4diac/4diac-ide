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

public class ChangeFBNetworkElementNameTest extends FBNetworkTestBase {

	private static final String NEW_NAME = "ModifiedName"; //$NON-NLS-1$

	private static State executeRenameCommand(State state) {
		state.setCommand(new ChangeFBNetworkElementName(state.getFbNetwork().getNetworkElements().get(0), NEW_NAME));
		return commandExecution(state);
	}

	private static void verifyRenamedState(State state, State oldState, TestFunction t) {
		t.test(state.getFbNetwork().getElementNamed(NEW_NAME));
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<State>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						FBCreateCommandTest::verifyState //
				), //
				new ExecutionDescription<State>("Rename Functionblock", //$NON-NLS-1$
						ChangeFBNetworkElementNameTest::executeRenameCommand, //
						ChangeFBNetworkElementNameTest::verifyRenamedState //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
