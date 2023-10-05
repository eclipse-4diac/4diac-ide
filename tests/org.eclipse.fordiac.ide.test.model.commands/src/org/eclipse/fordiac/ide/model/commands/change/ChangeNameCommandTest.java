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
import org.junit.jupiter.params.provider.Arguments;

public class ChangeNameCommandTest extends CommandTestBase<CommandTestBase.StateBase> {

	public static class State extends StateBase {

		private INamedElement element;

		public INamedElement getElement() {
			return element;
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

	protected static Collection<Arguments> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, CommandTestBase::defaultUndoCommand,
				CommandTestBase::defaultRedoCommand);
	}

	private static State executeCommand(State state, String newName, boolean isValidName) {
		state.setCommand(ChangeNameCommand.forName(state.getElement(), newName));
		state.setUndoAllowed(isValidName);
		if (isValidName) {
			return commandExecution(state);
		}
		return disabledCommandExecution(state);
	}

	private static void verifyMessages(State s, State o, TestFunction t) {
		if (!s.isViaUndo()) {
			t.test(s.getMessages().size(), 1);
		}
	}

	protected static List<Arguments> createCommands(List<ExecutionDescription<?>> executionDescriptions) {
		final List<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(StateVerifier<State>) CommandTestBase::verifyNothing, //
				executionDescriptions //
		));

		return commands;
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
				new ExecutionDescription<>("Try setting to invalid name", //$NON-NLS-1$
						(State s) -> executeCommand(s, "1bla", false), // //$NON-NLS-1$
						ChangeNameCommandTest::verifyMessages //
				), //
				new ExecutionDescription<>("Try setting to valid name", //$NON-NLS-1$
						(State s) -> executeCommand(s, "bla", true), // //$NON-NLS-1$
						CommandTestBase::verifyNothing //
				)//
		);

		return createCommands(executionDescriptions);
	}

}
