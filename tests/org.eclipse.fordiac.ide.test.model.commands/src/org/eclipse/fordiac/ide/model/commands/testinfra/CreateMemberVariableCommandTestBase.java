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

import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.DataFactory;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.gef.commands.Command;

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
			assert (struct.getMemberVariables().isEmpty());
		}

		public State(State s) {
			struct = EcoreUtil.copy(s.struct);
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
				CreateMemberVariableCommandTestBase::undoCommand, CreateMemberVariableCommandTestBase::redoCommand);
	}

	protected static void verifyDefaultInitialValues(State state, State oldState, TestFunction t) {
		t.test(null != state.getStructuredType());
		t.test(state.getStructuredType().getMemberVariables().isEmpty());
	}

	protected static List<Object[]> createCommands(List<Object> executionDescriptions) {
		List<Object[]> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from default values", //
				State::new, //
				(State state, State oldState, TestFunction t) -> verifyDefaultInitialValues(state, oldState, t), //
				executionDescriptions //
				));
		return commands;
	}

}
