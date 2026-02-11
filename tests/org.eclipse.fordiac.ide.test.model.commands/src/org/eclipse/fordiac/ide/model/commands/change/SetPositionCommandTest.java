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
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.junit.jupiter.params.provider.Arguments;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class SetPositionCommandTest extends FBNetworkTestBase {

	public static State executeCommand(final State state, final double newX, final double newY) {
		prepareCommand(state, newX, newY);
		return commandExecution(state);
	}

	private static void prepareCommand(final State state, final double newX, final double newY) {
		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		pos.setX(newX);
		pos.setY(newY);
		state.setCommand(
				new SetPositionCommand(state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME), pos));
	}

	public static void verifyState(final State state, final TestFunction t, final double x, final double y) {
		final double epsilon = 0.01d; // we are storing two digits of position this is our current epsilon
		t.test(state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getPosition().getX(), x, epsilon);
		t.test(state.getFbNetwork().getElementNamed(State.FUNCTIONBLOCK_NAME).getPosition().getY(), y, epsilon);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final String MOVE_FB = "Move Functionblock"; //$NON-NLS-1$

		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						(final State s, final State o, final TestFunction t) -> { //
							FBCreateCommandTest.verifyState(s, o, t); //
							verifyState(s, t, 0.0, 0.0);
						}), //
				new ExecutionDescription<>(MOVE_FB, (final State s) -> executeCommand(s, 10.0, 20.0), //
						(final State s, final State o, final TestFunction t) -> verifyState(s, t, 10.0, 20.0) //
				), //
				new ExecutionDescription<>(MOVE_FB, (final State s) -> executeCommand(s, 25.0, 45.0), //
						(final State s, final State o, final TestFunction t) -> verifyState(s, t, 25.0, 45.0) //
				), //
				new ExecutionDescription<>(MOVE_FB, (final State s) -> executeCommand(s, 20.0, 35.0), //
						(final State s, final State o, final TestFunction t) -> verifyState(s, t, 20.0, 35.0) //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
