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
 *   Bianca Wiesmayr, Ernst Blecha - initial documentation 
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.change;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateMemberVariableCommandTestBase.State;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.junit.runners.Parameterized.Parameters;

public class ToggleSubappRepresentationTest extends CommandTestBase<State> {
	protected static class State implements CommandTestBase.StateBase {

		private final SubApp subapp;

		private Command cmd;

		public SubApp getSubApp() {
			return subapp;
		}

		@Override
		public Command getCommand() {
			return cmd;
		}

		@Override
		public void setCommand(Command command) {
			this.cmd = command;
		}

		public State() {
			subapp = LibraryElementFactory.eINSTANCE.createSubApp();
		}

		public State(State s) {
			subapp = EcoreUtil.copy(s.getSubApp());
		}

		@Override
		public Object getClone() {
			return new State(this);
		}
	}

	protected static State undoCommand(Object stateObj) {
		State state = (State) stateObj;
		assumeTrue(state.getCommand().canUndo());
		state.getCommand().undo();
		return (state);
	}

	protected static State redoCommand(Object stateObj) {
		State state = (State) stateObj;
		assumeTrue(state.getCommand().canRedo());
		state.getCommand().redo();
		return (state);
	}

	protected static Collection<Object[]> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<Object> commands) {
		return describeCommand(description, initializer, initialVerifier, commands,
				ToggleSubappRepresentationTest::undoCommand, ToggleSubappRepresentationTest::redoCommand);
	}

	protected static void verifyDefaultInitialValues(State state, State oldState, TestFunction t) {
		t.test(null != state.getSubApp());
		t.test(!state.getSubApp().isUnfolded());
	}

	protected static void verifyUnfolded(State state, State oldState, TestFunction t) {
		t.test(null != state.getSubApp());
		t.test(state.getSubApp().isUnfolded());
	}

	private static State toggleFolding(State state) {
		state.setCommand(new ToggleSubAppRepresentationCommand(state.getSubApp()));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}

	// define here the list of test sequences
	// multiple execution descriptions are possible -> define in test class
	protected static List<Object[]> createCommands(List<Object> executionDescriptions) {
		List<Object[]> commands = new ArrayList<>();
		// test series 1
		commands.addAll(describeCommand("Toggling Attribute", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				executionDescriptions //
				));
		return commands;
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<>("Unfold", // //$NON-NLS-1$
						ToggleSubappRepresentationTest::toggleFolding, //
						ToggleSubappRepresentationTest::verifyUnfolded), //
				new ExecutionDescription<>("Fold", // //$NON-NLS-1$
						ToggleSubappRepresentationTest::toggleFolding, //
						ToggleSubappRepresentationTest::verifyDefaultInitialValues //
						) //
				);
		return createCommands(executionDescriptions);
	}
}
