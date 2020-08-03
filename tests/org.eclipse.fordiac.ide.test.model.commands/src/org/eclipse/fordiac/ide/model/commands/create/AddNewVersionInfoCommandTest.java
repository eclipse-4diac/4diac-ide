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
package org.eclipse.fordiac.ide.model.commands.create;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.junit.Assume;
import org.junit.runners.Parameterized.Parameters;

public class AddNewVersionInfoCommandTest extends FBNetworkTestBase {

	private static final SimpleDateFormat dayOnlyFormat = new SimpleDateFormat("d"); //$NON-NLS-1$
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	private static final long DELAY_TIME = 60000; // timelimit between execute and verify in miliseconds

	public static State executeCommand(State state) {
		state.setCommand(new AddNewVersionInfoCommand(state.getFbNetwork().getNetworkElements().get(0).getType()));
		assumeNotNull(state.getCommand());
		assumeTrue(state.getCommand().canExecute());

		final int before = Integer.parseInt(dayOnlyFormat.format(new Date()));
		state.getCommand().execute();
		final int after = Integer.parseInt(dayOnlyFormat.format(new Date(System.currentTimeMillis() + DELAY_TIME)));

		Assume.assumeTrue(after == before); // if this test skips here, the test was probably run close to midnight and
											// we would possibly check against an incorrect value during verify

		return state;
	}

	public static void verifyStateBefore(State state, State oldState, TestFunction t) {
		FBCreateCommandTest.verifyState(state, oldState, t);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo().isEmpty());
	}

	public static void verifyState(State state, State oldState, TestFunction t, int index, int expectedSize) {
		final EList<VersionInfo> vinfo = state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo();

		t.test(vinfo.size() == expectedSize);
		t.test(vinfo.get(index).getAuthor().equals(FordiacMessages.Unknown));
		t.test(vinfo.get(index).getOrganization().equals(FordiacMessages.Unknown));
		t.test(vinfo.get(index).getVersion().equals("1.0")); //$NON-NLS-1$
		t.test(vinfo.get(index).getRemarks().equals("")); //$NON-NLS-1$

		t.test(vinfo.get(index).getDate().equals(dateFormat.format(new Date()))); // This test will fail
																					// if more than 1 minute
																					// passes between
																					// execute and verify
	}

	public static void verifyState(State state, State oldState, TestFunction t) {
		verifyState(state, oldState, t, 0, 1);
	}

	public static void verifyState2(State state, State oldState, TestFunction t) {
		verifyState(state, oldState, t, 1, 2);
	}

	// parameter creation function, also contains description of how the textual
	// description will be used
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data() {
		final List<Object> executionDescriptions = ExecutionDescription.commandList( //
				new ExecutionDescription<State>("Add Functionblock", //$NON-NLS-1$
						FBCreateCommandTest::executeCommand, //
						AddNewVersionInfoCommandTest::verifyStateBefore //
				), //
				new ExecutionDescription<State>("Add VersionInfo", //$NON-NLS-1$
						AddNewVersionInfoCommandTest::executeCommand, //
						AddNewVersionInfoCommandTest::verifyState //
				), //
				new ExecutionDescription<State>("Add second VersionInfo", //$NON-NLS-1$
						AddNewVersionInfoCommandTest::executeCommand, //
						AddNewVersionInfoCommandTest::verifyState2 //
				) //
		);

		return createCommands(executionDescriptions);
	}

}
