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
import org.junit.jupiter.params.provider.Arguments;

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
			defaultUndoCommand(state);
		} else {
			disabledUndoCommand(state);
		}
		return (state);
	}

	protected static State redoCommand(Object stateObj) {
		final State state = (State) stateObj;
		if (state.isNameValid()) {
			defaultRedoCommand(state);
		} else {
			disabledRedoCommand(state);
		}
		return (state);
	}

	protected static Collection<Arguments> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, ChangeNameCommandTest::undoCommand,
				ChangeNameCommandTest::redoCommand);
	}

	private static State executeCommand(State state, String newName, boolean isValid) {
		state.setCommand(new ChangeNameCommand(state.getElement(), newName));
		if (isValid) {
			state.setNameValid();
			return commandExecution(state);
		} else {
			state.setNameInvalid();
			return disabledCommandExecution(state);
		}
	}

	protected static List<Arguments> createCommands(List<ExecutionDescription<?>> executionDescriptions) {
		final List<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyNothing(state, oldState, t), //
				executionDescriptions //
		));

		return commands;
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Try setting to invalid name", //$NON-NLS-1$
						(State s) -> executeCommand(s, "1bla", false), //
						CommandTestBase::verifyNothing //
				), new ExecutionDescription<>("Try setting to valid name", //$NON-NLS-1$
						(State s) -> executeCommand(s, "bla", true), //
						CommandTestBase::verifyNothing //
				)//
		);

		return createCommands(executionDescriptions);
	}

}
