/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Lisa Sonnleithner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.commands.testinfra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;
import org.junit.jupiter.params.provider.Arguments;

public abstract class ValueCommandTestBase extends CommandTestBase<ValueCommandTestBase.State> {

	private static final String SET_VALUE = "default"; //$NON-NLS-1$

	// create a state description that fits our purpose
	public static class State implements CommandTestBase.StateBase {
		private final VarDeclaration var = LibraryElementFactory.eINSTANCE.createVarDeclaration();

		public State() {
			super();
			var.setValue(LibraryElementFactory.eINSTANCE.createValue());
		}

		private Command cmd;

		public VarDeclaration getVar() {
			return var;
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
			return new State();
		}
	}

	protected static Collection<Arguments> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, CommandTestBase::defaultUndoCommand,
				CommandTestBase::defaultRedoCommand);
	}

	protected static Collection<Arguments> createCommands(List<ExecutionDescription<?>> executionDescriptions) {
		final Collection<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				executionDescriptions //
		));

		commands.addAll(describeCommand("Start from set values", // //$NON-NLS-1$
				() -> setInitialValues(), //
				(State state, State oldState, TestFunction t) -> verifySetInitialValues(state, oldState, t), //
				executionDescriptions //
		));
		return commands;
	}

	@SuppressWarnings("unused")
	protected static void verifyDefaultInitialValues(State state, State oldState, TestFunction t) {
		final VarDeclaration varDec = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDec.setValue(LibraryElementFactory.eINSTANCE.createValue());

		final String DEFAULT_VALUE = varDec.getValue().getValue();

		t.test(state.getVar().getValue().getValue(), DEFAULT_VALUE);
	}

	protected static State setInitialValues() {
		final State state = new State();
		state.getVar().getValue().setValue(SET_VALUE);
		return state;
	}

	@SuppressWarnings("unused")
	protected static void verifySetInitialValues(State state, State oldState, TestFunction t) {
		t.test(state.getVar().getValue().getValue(), SET_VALUE);

	}

}
