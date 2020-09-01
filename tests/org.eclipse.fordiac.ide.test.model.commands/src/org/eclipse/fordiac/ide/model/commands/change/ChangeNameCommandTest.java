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

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;
import org.junit.runners.Parameterized.Parameters;

public class ChangeNameCommandTest extends CommandTestBase<CommandTestBase.StateBase> {

	public static class State implements StateBase {

		private Command cmd;
		private boolean isCurrentNameValid = false;

		private INamedElement element;

		public void setNameValid() {
			isCurrentNameValid = true;
		}

		public void setNameInvalid() {
			isCurrentNameValid = false;
		}

		public boolean isNameValid() {
			return isCurrentNameValid;
		}

		public INamedElement getElement() {
			return element;
		}

		@Override
		public Command getCommand() {
			return cmd;
		}

		@Override
		public void setCommand(Command cmd) {
			this.cmd = cmd;
		}

		@Override
		public Object getClone() {
			return new State(this);
		}

		public State() {
			AutomationSystem a = LibraryElementFactory.eINSTANCE.createAutomationSystem();
			element = LibraryElementFactory.eINSTANCE.createApplication();
			a.getApplication().add((Application) element);
		}

		private State(State s) {
			element = EcoreUtil.copy(s.element);
		}

	}

	protected static State undoCommand(Object stateObj) {
		final State state = (State) stateObj;
		if (state.isNameValid()) {
			assumeTrue(state.getCommand().canExecute() && state.getCommand().canUndo());
			state.getCommand().undo();
		} else {
			assumeFalse(state.getCommand().canExecute() && state.getCommand().canUndo());
		}
		return (state);
	}

	protected static State redoCommand(Object stateObj) {
		final State state = (State) stateObj;
		if (state.isNameValid()) {
			assumeTrue(state.getCommand().canExecute() && state.getCommand().canRedo());
			state.getCommand().redo();
		} else {
			assumeFalse(state.getCommand().canExecute() && state.getCommand().canRedo());
		}
		return (state);
	}

	protected static Collection<Object[]> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<Object> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, ChangeNameCommandTest::undoCommand,
				ChangeNameCommandTest::redoCommand);
	}

	protected static void verifyNothing(State state, State oldState, TestFunction t) {
		//
	}

	private static State executeCommand(State state, String newName, boolean isValid) {
		state.setCommand(new ChangeNameCommand(state.getElement(), newName));
		assumeNotNull(state.getCommand());
		if (isValid) {
			state.setNameValid();
			assumeTrue(state.getCommand().canExecute());
			state.getCommand().execute();
		} else {
			state.setNameInvalid();
			assumeFalse(state.getCommand().canExecute());
		}
		return state;
	}

	protected static List<Object[]> createCommands(List<Object> executionDescriptions) {
		final List<Object[]> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyNothing(state, oldState, t), //
				executionDescriptions //
		));

		return commands;
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<>("Try setting to invalid name", //$NON-NLS-1$
						(State s) -> executeCommand(s, "1bla", false), //
						ChangeNameCommandTest::verifyNothing //
				),
				new ExecutionDescription<>("Try setting to valid name", //$NON-NLS-1$
						(State s) -> executeCommand(s, "bla", true), //
						ChangeNameCommandTest::verifyNothing //
				)//
		);

		return createCommands(executionDescriptions);
	}

}
