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
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.testinfra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.gef.commands.Command;
import org.junit.jupiter.params.provider.Arguments;

public abstract class VersionInfoTestBase extends CommandTestBase<VersionInfoTestBase.State> {

	protected static final String EMPTY = ""; //$NON-NLS-1$

	private static final String SET_NAME = "first name surname"; //$NON-NLS-1$
	private static final String SET_DATE = "1984-08-04"; //$NON-NLS-1$
	private static final String SET_ORG = "4diac"; //$NON-NLS-1$
	private static final String SET_REMARKS = "remark something"; //$NON-NLS-1$
	private static final String SET_VERSION = "13.0"; //$NON-NLS-1$

	// create a state description that fits our purpose
	public static class State implements CommandTestBase.StateBase {
		private final VersionInfo vInfo;

		private Command cmd;

		public VersionInfo getVersionInfo() {
			return vInfo;
		}

		@Override
		public Command getCommand() {
			return cmd;
		}

		@Override
		public void setCommand(Command cmd) {
			this.cmd = cmd;
		}

		public State() {
			vInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();
		}

		private State(State s) {
			vInfo = s.vInfo;
		}

		@Override
		public Object getClone() {
			return new State(this);
		}
	}

	protected static Collection<Arguments> describeCommand(String description, StateInitializer<?> initializer,
			StateVerifier<?> initialVerifier, List<ExecutionDescription<?>> commands) {
		return describeCommand(description, initializer, initialVerifier, commands, CommandTestBase::defaultUndoCommand,
				CommandTestBase::defaultRedoCommand);
	}

	protected static void verifyDefaultInitialValues(State state, State oldState, TestFunction t) {
		final VersionInfo vInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();

		final String DEFAULT_NAME = vInfo.getAuthor();
		final String DEFAULT_DATE = vInfo.getDate();
		final String DEFAULT_ORG = vInfo.getOrganization();
		final String DEFAULT_REMARKS = vInfo.getRemarks();
		final String DEFAULT_VERSION = vInfo.getVersion();

		t.test(state.getVersionInfo().getAuthor(), DEFAULT_NAME);
		t.test(state.getVersionInfo().getDate(), DEFAULT_DATE);
		t.test(state.getVersionInfo().getOrganization(), DEFAULT_ORG);
		t.test(state.getVersionInfo().getRemarks(), DEFAULT_REMARKS);
		t.test(state.getVersionInfo().getVersion(), DEFAULT_VERSION);
	}

	protected static State setInitialValues() {
		final State state = new State();
		state.getVersionInfo().setAuthor(SET_NAME);
		state.getVersionInfo().setDate(SET_DATE);
		state.getVersionInfo().setOrganization(SET_ORG);
		state.getVersionInfo().setRemarks(SET_REMARKS);
		state.getVersionInfo().setVersion(SET_VERSION);
		return state;
	}

	protected static void verifySetInitialValues(State state, State oldState, TestFunction t) {
		t.test(state.getVersionInfo().getAuthor(), SET_NAME);
		t.test(state.getVersionInfo().getDate(), SET_DATE);
		t.test(state.getVersionInfo().getOrganization(), SET_ORG);
		t.test(state.getVersionInfo().getRemarks(), SET_REMARKS);
		t.test(state.getVersionInfo().getVersion(), SET_VERSION);
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

}
