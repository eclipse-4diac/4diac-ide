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

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class SetPositionCommandTest extends FBNetworkTestBase {

	public static State executeCommand(State state, int x, int y, String request) {
		prepareCommand(state, x, y, request);

		return commandExecution(state);
	}

	public static State unexecutableCommand(State state) {
		prepareCommand(state, 10, 15, RequestConstants.REQ_RESIZE);

		return disabledCommandExecution(state);
	}

	private static void prepareCommand(State state, int x, int y, String request) {
		final int INVALID = -1;

		ChangeBoundsRequest req = new ChangeBoundsRequest();
		req.setType(request);

		state.setCommand(new SetPositionCommand(
				(PositionableElement) state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME), req,
				new Rectangle(x, y, INVALID, INVALID)));
	}

	public static void verifyState(State state, State oldState, TestFunction t, int x, int y) {
		t.test(state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getX(), x);
		t.test(state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getY(), y);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final String MOVE_FB = "Move Functionblock ({0})";

		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<State>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						(State s, State o, TestFunction t) -> { //
							FBCreateCommandTest.verifyState(s, o, t); //
							verifyState(s, o, t, 0, 0);
						}), //
				new ExecutionDescription<State>(MessageFormat.format(MOVE_FB, RequestConstants.REQ_MOVE),
						(State s) -> executeCommand(s, 10, 20, RequestConstants.REQ_MOVE), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, 10, 20) //
				), //
				new ExecutionDescription<State>(MessageFormat.format(MOVE_FB, RequestConstants.REQ_MOVE_CHILDREN),
						(State s) -> executeCommand(s, 15, 25, RequestConstants.REQ_MOVE_CHILDREN), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, 15, 25) //
				), //
				new ExecutionDescription<State>(MessageFormat.format(MOVE_FB, RequestConstants.REQ_ALIGN_CHILDREN),
						(State s) -> executeCommand(s, 5, 10, RequestConstants.REQ_ALIGN_CHILDREN), //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, 5, 10) //
				) //
		);

		Collection<Arguments> unexecutable = describeCommand("Start from default values", // //$NON-NLS-1$
				() -> FBCreateCommandTest.executeCommand(new State()), //
				(State s, State o, TestFunction t) -> {
					FBCreateCommandTest.verifyState(s, o, t);
					verifyState(s, o, t, 0, 0);
				}, //
				List.of(new ExecutionDescription<>("Unexecutable case: invalid request", // //$NON-NLS-1$
						SetPositionCommandTest::unexecutableCommand, //
						(State s, State o, TestFunction t) -> verifyState(s, o, t, 0, 0) //
				) //
				), //
				CommandTestBase::disabledUndoCommand, //
				CommandTestBase::disabledRedoCommand //
		);

		Collection<Arguments> commands = createCommands(executionDescriptions);

		commands.addAll(unexecutable);

		return commands;
	}

}
