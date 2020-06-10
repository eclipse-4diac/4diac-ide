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

package org.eclipse.fordiac.ide.model.commands.change;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.junit.runners.Parameterized.Parameters;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

public class ChangeRemarksCommandTest extends CommandTestBase<ChangeRemarksCommandTest.State> {

	// create a state description that fits our purpose
	public static class State implements CommandTestBase.StateBase {
		private VersionInfo vInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();

		private ChangeRemarksCommand cmd;

		public VersionInfo getVersionInfo() {
			return vInfo;
		}

		public ChangeRemarksCommand getCommand() {
			return cmd;
		}

		public void setCommand(ChangeRemarksCommand cmd) {
			this.cmd = cmd;
		}
	}

	private static final String NEW_REMARK = "a great remark"; //$NON-NLS-1$

	private static boolean verifyState(State state, State oldState) {
		return state.getVersionInfo().getRemarks().equals(NEW_REMARK)
				&& state.getVersionInfo().getDate().equals(oldState.getVersionInfo().getDate())
				&& state.getVersionInfo().getOrganization().equals(oldState.getVersionInfo().getOrganization())
				&& state.getVersionInfo().getAuthor().equals(oldState.getVersionInfo().getAuthor())
				&& state.getVersionInfo().getVersion().equals(oldState.getVersionInfo().getVersion());
	}

	private static boolean verifyStateNull(State state, State oldState) {
		return state.getVersionInfo().getRemarks().equals("") //$NON-NLS-1$
				&& state.getVersionInfo().getDate().equals(oldState.getVersionInfo().getDate())
				&& state.getVersionInfo().getOrganization().equals(oldState.getVersionInfo().getOrganization())
				&& state.getVersionInfo().getAuthor().equals(oldState.getVersionInfo().getAuthor())
				&& state.getVersionInfo().getVersion().equals(oldState.getVersionInfo().getVersion());
	}

	private static void createCommand(Object stateObj) {
		State state = (State) stateObj;
		state.setCommand(new ChangeRemarksCommand(state.getVersionInfo(), NEW_REMARK));//
		assumeNotNull(state.getCommand());
	}

	private static State executeCommand(State state) {
		assumeTrue(state.cmd.canExecute());
		state.cmd.execute();
		return state;
	}

	private static State undoCommand(Object stateObj) {
		State state = (State) stateObj;
		assumeTrue(state.cmd.canUndo());
		state.cmd.undo();
		return (state);
	}

	private static State redoCommand(Object stateObj) {
		State state = (State) stateObj;
		assumeTrue(state.cmd.canRedo());
		state.cmd.redo();
		return (state);
	}

	private static State executeCommandToNull(State state) {
		state.setCommand(new ChangeRemarksCommand(state.getVersionInfo(), null));
		assumeTrue(state.getCommand().canExecute());
		state.getCommand().execute();
		return state;
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		VersionInfo vInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();

		final String DEFAULT_NAME = vInfo.getAuthor();
		final String DEFAULT_DATE = vInfo.getDate();
		final String DEFAULT_ORG = vInfo.getOrganization();
		final String DEFAULT_REMARKS = vInfo.getRemarks();
		final String DEFAULT_VERSION = vInfo.getVersion();

		final String SET_NAME = "first name surname"; //$NON-NLS-1$
		final String SET_DATE = "1984-08-04"; //$NON-NLS-1$
		final String SET_ORG = "4diac"; //$NON-NLS-1$
		final String SET_REMARKS = "remark something"; //$NON-NLS-1$
		final String SET_VERSION = "13.0"; //$NON-NLS-1$

		List<Object[]> commands = new ArrayList<>();

		List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<State>("Change Remarks", //
						ChangeRemarksCommandTest::executeCommand, //
						ChangeRemarksCommandTest::verifyState //
				), //
				new ExecutionDescription<State>("Change Remarks to null", //
						ChangeRemarksCommandTest::executeCommandToNull, //
						ChangeRemarksCommandTest::verifyStateNull //
				) //
		);

		commands.addAll(describeCommand("Start from default values", //
				State::new, //
				(State state, State oldState) -> state.getVersionInfo().getAuthor().equals(DEFAULT_NAME)
						&& state.getVersionInfo().getDate().equals(DEFAULT_DATE)
						&& state.getVersionInfo().getOrganization().equals(DEFAULT_ORG)
						&& state.getVersionInfo().getRemarks().equals(DEFAULT_REMARKS)
						&& state.getVersionInfo().getVersion().equals(DEFAULT_VERSION), //
				ChangeRemarksCommandTest::createCommand, //
				executionDescriptions, //
				ChangeRemarksCommandTest::undoCommand, //
				ChangeRemarksCommandTest::redoCommand //
		));

		commands.addAll(describeCommand("Start from set values", //
				() -> {
					State state = new State();
					state.getVersionInfo().setAuthor(SET_NAME);
					state.getVersionInfo().setDate(SET_DATE);
					state.getVersionInfo().setOrganization(SET_ORG);
					state.getVersionInfo().setRemarks(SET_REMARKS);
					state.getVersionInfo().setVersion(SET_VERSION);
					return state;
				}, //
				(State state, State oldState) -> state.getVersionInfo().getAuthor().equals(SET_NAME)
						&& state.getVersionInfo().getDate().equals(SET_DATE)
						&& state.getVersionInfo().getOrganization().equals(SET_ORG)
						&& state.getVersionInfo().getRemarks().equals(SET_REMARKS)
						&& state.getVersionInfo().getVersion().equals(SET_VERSION), //
				ChangeRemarksCommandTest::createCommand, //
				executionDescriptions, //
				ChangeRemarksCommandTest::undoCommand, //
				ChangeRemarksCommandTest::redoCommand //
		));

		return commands;
	}

}
