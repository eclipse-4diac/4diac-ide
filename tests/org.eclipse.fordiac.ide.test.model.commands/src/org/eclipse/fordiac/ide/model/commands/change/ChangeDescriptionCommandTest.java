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

public class ChangeDescriptionCommandTest extends FBNetworkTestBase {

	private static final String DESCRIPTION_STRING = "new value"; //$NON-NLS-1$

	public static State executeCommand(State state, String setValue) {
		state.setCommand(
				new ChangeDescriptionCommand(state.getFbNetwork().getNetworkElements().get(0).getType(), setValue));

		return commandExecution(state);
	}

	public static void verifyState(State state, State oldState, TestFunction t, String expectedValue) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getIdentification().getDescription(), expectedValue);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						FBCreateCommandTest::verifyState //
				), //
				new ExecutionDescription<>("Change Description", //$NON-NLS-1$
						(State state) -> executeCommand(state, DESCRIPTION_STRING), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, DESCRIPTION_STRING) //
				), //
				new ExecutionDescription<>("Change Description", //$NON-NLS-1$
						(State state) -> executeCommand(state, null), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, "") //$NON-NLS-1$
				) //
		);

		return createCommands(executionDescriptions);
	}

}
