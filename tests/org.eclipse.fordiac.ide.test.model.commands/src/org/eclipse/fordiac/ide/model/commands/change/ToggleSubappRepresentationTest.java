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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.testinfra.CommandTestBase;
import org.eclipse.fordiac.ide.model.commands.testinfra.CreateMemberVariableCommandTestBase.State;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.junit.jupiter.params.provider.Arguments;

public class ToggleSubappRepresentationTest extends CommandTestBase<State> {
	protected static class State extends CommandTestBase.StateBase {

		private final SubApp subapp;

		public SubApp getSubApp() {
			return subapp;
		}

		public State() {
			subapp = LibraryElementFactory.eINSTANCE.createUntypedSubApp();
		}

		public State(final State s) {
			subapp = EcoreUtil.copy(s.getSubApp());
		}

		@Override
		public Object getClone() {
			return new State(this);
		}
	}

	protected static Collection<Arguments> describeCommand(final String description,
			final StateInitializer<?> initializer, final StateVerifier<?> initialVerifier,
			final List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, CommandTestBase::defaultUndoCommand,
				CommandTestBase::defaultRedoCommand);
	}

	protected static void verifyDefaultInitialValues(final State state, final State oldState, final TestFunction t) {
		t.test(state.getSubApp());
		t.test(!state.getSubApp().isUnfolded());
	}

	protected static void verifyUnfolded(final State state, final State oldState, final TestFunction t) {
		t.test(state.getSubApp());
		t.test(state.getSubApp().isUnfolded());
	}

	private static State toggleFolding(final State state) {
		state.setCommand(new ToggleSubAppRepresentationCommand(state.getSubApp()));

		return commandExecution(state);
	}

	// define here the list of test sequences
	// multiple execution descriptions are possible -> define in test class
	protected static List<Arguments> createCommands(final List<ExecutionDescription<?>> executionDescriptions) {
		final List<Arguments> commands = new ArrayList<>();
		// test series 1
		commands.addAll(describeCommand("Toggling Attribute", // //$NON-NLS-1$
				State::new, //
				(StateVerifier<State>) ToggleSubappRepresentationTest::verifyDefaultInitialValues, //
				executionDescriptions //
		));
		return commands;
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
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
