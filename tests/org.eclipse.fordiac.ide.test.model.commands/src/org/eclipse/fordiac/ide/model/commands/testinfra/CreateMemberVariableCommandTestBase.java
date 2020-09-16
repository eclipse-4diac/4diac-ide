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
 *   Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.testinfra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.gef.commands.Command;
import org.junit.jupiter.params.provider.Arguments;

public abstract class CreateMemberVariableCommandTestBase
		extends CommandTestBase<CreateMemberVariableCommandTestBase.State> {
	protected static DataTypeLibrary datatypeLib = new DataTypeLibrary();

	// create a state description that holds the struct and the command
	public static class State implements CommandTestBase.StateBase {

		private final StructuredType struct;

		private Command cmd;

		public StructuredType getStructuredType() {
			return struct;
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
			struct = DataFactory.eINSTANCE.createStructuredType();
			// TODO add user-defined datatypes to datatype library for testing
		}

		public State(State s) {
			struct = EcoreUtil.copy(s.struct);
		}

		@Override
		public Object getClone() {
			return new State(this);
		}
	}

	protected static Collection<Arguments> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands,
				CommandTestBase::defaultUndoCommand, CommandTestBase::defaultRedoCommand);
	}

	protected static void verifyDefaultInitialValues(State state, State oldState, TestFunction t) {
		t.test(state.getStructuredType());
		t.test(state.getStructuredType().getMemberVariables().isEmpty());
	}

	// define here the list of test sequences
	// multiple execution descriptions are possible -> define in test class
	protected static Collection<Arguments> createCommands(List<ExecutionDescription<?>> autofilledExecutionDescriptions,
			List<ExecutionDescription<?>> configuredExecutionDescriptions) {
		Collection<Arguments> commands = new ArrayList<>();
		// test series 1
		commands.addAll(describeCommand("Autofilled Command", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				autofilledExecutionDescriptions //
		));
		// test series 2
		commands.addAll(describeCommand("Configured Command", // //$NON-NLS-1$
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				configuredExecutionDescriptions //
		));
		return commands;
	}

}
