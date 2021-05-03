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

import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.junit.jupiter.params.provider.Arguments;

public class DeleteFBNetworkElementCommandTest extends FBNetworkTestBase {

	/**
	 * undo method that expects that undo is possible
	 *
	 * @param stateObj current state description
	 * @return new state description
	 */
	protected static State undoCommand(Object stateObj) {
		final State state = (State) stateObj;
		assumption.test(state.getCommand().canUndo());
		state.getCommand().undo();
		return (state);
	}

	/**
	 * redo method that expects that redo is possible
	 *
	 * @param stateObj current state description
	 * @return new state description
	 */
	protected static State redoCommand(Object stateObj) {
		final State state = (State) stateObj;
		assumption.test(state.getCommand().canRedo());
		state.getCommand().redo();
		return (state);
	}

	public static State executeDeleteCommand(final State state) {
		state.setCommand(new DeleteFBNetworkElementCommand(state.getFbNetwork().getNetworkElements().get(0)));
		return commandExecution(state);
	}

	public static void verifyDeletedState(final State state, final State oldState, final TestFunction t) {
		t.test(state.getFbNetwork().getNetworkElements().isEmpty());
	}

	// parameter creation function
	public static Collection<Arguments> data() {

		return describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				List.of( //
						new ExecutionDescription<>("Add Functionblock", //$NON-NLS-1$
								FBCreateCommandTest::executeCommand, //
								(State s, State o, TestFunction t) -> FBCreateCommandTest.verifyState(s, o, t) //
						), //
						new ExecutionDescription<>("Delete Functionblock from Network", //$NON-NLS-1$
								DeleteFBNetworkElementCommandTest::executeDeleteCommand, //
								DeleteFBNetworkElementCommandTest::verifyDeletedState //
						) //
							// TODO: add connections, add mapping
				), //
				DeleteFBNetworkElementCommandTest::undoCommand, //
				DeleteFBNetworkElementCommandTest::redoCommand //
		);

	}

}
