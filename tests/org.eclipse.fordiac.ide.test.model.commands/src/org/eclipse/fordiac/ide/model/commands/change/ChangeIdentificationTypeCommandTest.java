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

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.junit.runners.Parameterized.Parameters;

public class ChangeIdentificationTypeCommandTest extends FBNetworkTestBase {

	private static final String IDENTIFICATION_TYPE_STRING = "new value";

	public static State executeCommand(State state, String setValue) {
		state.setCommand(new ChangeIdentifcationTypeCommand(state.getFbNetwork().getNetworkElements().get(0).getType(),
				setValue));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}

	public static void verifyStateBefore(State state, State oldState, TestFunction t) {
		FBCreateCommandTest.verifyState(state, oldState, t);
	}

	@SuppressWarnings("unused")
	public static void verifyState(State state, State oldState, TestFunction t, String expectedValue) {
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getIdentification().getType()
				.equals(expectedValue));
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<State>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						ChangeIdentificationTypeCommandTest::verifyStateBefore //
				), //
				new ExecutionDescription<State>("Change Identification Type", //$NON-NLS-1$
						(State state) -> executeCommand(state, IDENTIFICATION_TYPE_STRING), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, IDENTIFICATION_TYPE_STRING) //
				), //
				new ExecutionDescription<State>("Change Identification Type to null", //$NON-NLS-1$
						(State state) -> executeCommand(state, null), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, "") //$NON-NLS-1$
				) //
		);

		return createCommands(executionDescriptions);
	}

}
