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
import org.eclipse.fordiac.ide.model.commands.testinfra.ErrorMessageTestReceiver;
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
		private boolean viaUndo = false;

		private INamedElement element;
		private List<String> messages;

		public List<String> getMessages() {
			return messages;
		}

		public void setMessages(List<String> messages) {
			this.messages = messages;
		}

		public boolean viaUndo() {
			return viaUndo;
		}

		public void setViaUndo() {
			this.viaUndo = true;
		}

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
			messages = s.messages;
		}

	}

	protected static State undoCommand(Object stateObj) {
		final State state = (State) stateObj;
		emh.start();
		if (state.isNameValid()) {
			defaultUndoCommand(state);
		} else {
			disabledUndoCommand(state);
		}
		state.setViaUndo();
		state.setMessages(emh.getMessages());
		emh.stop();
		return (state);
	}

	protected static State redoCommand(Object stateObj) {
		final State state = (State) stateObj;
		emh.start();
		if (state.isNameValid()) {
			defaultRedoCommand(state);
		} else {
			disabledRedoCommand(state);
		}
		state.setMessages(emh.getMessages());
		emh.stop();
		return (state);
	}

	private static ErrorMessageTestReceiver emh = new ErrorMessageTestReceiver();

	protected static Collection<Arguments> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, ChangeNameCommandTest::undoCommand,
				ChangeNameCommandTest::redoCommand);
	}

	private static State executeCommand(State state, String newName, boolean isValid) {
		state.setCommand(new ChangeNameCommand(state.getElement(), newName));
		emh.start();
		State s;
		if (isValid) {
			state.setNameValid();
			s = commandExecution(state);
		} else {
			state.setNameInvalid();
			s = disabledCommandExecution(state);
		}
		s.setMessages(emh.getMessages());
		emh.stop();
		return s;
	}

	private static void verifyMessages(State s, State o, TestFunction t) {
		if (!s.viaUndo()) {
			t.test(s.getMessages().size(), 1);
		}
	}

	protected static List<Arguments> createCommands(List<ExecutionDescription<?>> executionDescriptions) {
		final List<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> CommandTestBase.verifyNothing(state, oldState, t), //
				executionDescriptions //
		));

		return commands;
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Try setting to invalid name", //$NON-NLS-1$
						(State s) -> executeCommand(s, "1bla", false), //
						ChangeNameCommandTest::verifyMessages //
				), new ExecutionDescription<>("Try setting to valid name", //$NON-NLS-1$
						(State s) -> executeCommand(s, "bla", true), //
						CommandTestBase::verifyNothing //
				)//
		);

		return createCommands(executionDescriptions);
	}

}
