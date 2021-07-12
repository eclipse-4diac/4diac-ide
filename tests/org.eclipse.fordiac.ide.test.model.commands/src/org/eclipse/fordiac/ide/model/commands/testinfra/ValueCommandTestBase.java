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

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.jupiter.params.provider.Arguments;

public abstract class ValueCommandTestBase extends CommandTestBase<ValueCommandTestBase.State> {

	private static final String SET_VALUE = "default"; //$NON-NLS-1$

	// create a state description that fits our purpose
	public static class State extends CommandTestBase.StateBase {
		private final VarDeclaration variable = LibraryElementFactory.eINSTANCE.createVarDeclaration();

		public State() {
			super();
			variable.setValue(LibraryElementFactory.eINSTANCE.createValue());
			variable.setType(IecTypes.ElementaryTypes.STRING);
		}

		public VarDeclaration getVar() {
			return variable;
		}

		@Override
		public Object getClone() {
			return new State();
		}
	}

	protected static Collection<Arguments> describeCommand(final String description, final StateInitializer<?> initializer,
			final StateVerifier<?> initialVerifier, final List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, CommandTestBase::defaultUndoCommand,
				CommandTestBase::defaultRedoCommand);
	}

	protected static Collection<Arguments> createCommands(final List<ExecutionDescription<?>> executionDescriptions) {
		final Collection<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", // //$NON-NLS-1$
				State::new, //
				(StateVerifier<State>) ValueCommandTestBase::verifyDefaultInitialValues, //
				executionDescriptions //
				));

		commands.addAll(describeCommand("Start from set values", // //$NON-NLS-1$
				ValueCommandTestBase::setInitialValues, //
				(StateVerifier<State>) ValueCommandTestBase::verifySetInitialValues, //
				executionDescriptions //
				));
		return commands;
	}

	protected static void verifyDefaultInitialValues(final State state, final State oldState, final TestFunction t) {
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

	protected static void verifySetInitialValues(final State state, final State oldState, final TestFunction t) {
		t.test(state.getVar().getValue().getValue(), SET_VALUE);

	}

}
