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

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.junit.jupiter.params.provider.Arguments;

public class AddNewVersionInfoCommandTest extends FBNetworkTestBase {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$

	private static Long millisBeforeFirstExecute;
	private static final long DAY_IN_MILLISECONDS = 24l * 60l * 60l * 1000l;

	public static State executeCommand(State state) {
		if (null == millisBeforeFirstExecute) {
			millisBeforeFirstExecute = System.currentTimeMillis();
		}

		state.setCommand(new AddNewVersionInfoCommand(state.getFbNetwork().getNetworkElements().get(0).getType()));

		return commandExecution(state);
	}

	public static void verifyStateBefore(State state, State oldState, TestFunction t) {
		FBCreateCommandTest.verifyState(state, oldState, t);
		t.test(state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo().isEmpty());
	}

	public static void verifyState(State state, State oldState, TestFunction t, int index, int expectedSize) {
		final EList<VersionInfo> vinfo = state.getFbNetwork().getNetworkElements().get(0).getType().getVersionInfo();

		t.test(vinfo.size(), expectedSize);
		t.test(vinfo.get(index).getAuthor(), FordiacMessages.Unknown);
		t.test(vinfo.get(index).getOrganization(), FordiacMessages.Unknown);
		t.test(vinfo.get(index).getVersion(), "1.0"); //$NON-NLS-1$
		t.test(vinfo.get(index).getRemarks(), ""); //$NON-NLS-1$

		String dateBeforeFirstExecute = dateFormat.format(new Date(millisBeforeFirstExecute));
		String dateDuringVerify = dateFormat.format(new Date());

		// if this test skips here, this single test had a runtime (walltime) of more
		// than one day. please fix the development system
		assumption.test((System.currentTimeMillis() - millisBeforeFirstExecute) < DAY_IN_MILLISECONDS);

		t.test(vinfo.get(index).getDate().equals(dateBeforeFirstExecute) || // this may skip across midnight, so we
																			// allow both days around midnight
				vinfo.get(index).getDate().equals(dateDuringVerify));
	}

	public static void verifyState(State state, State oldState, TestFunction t) {
		verifyState(state, oldState, t, 0, 1);
	}

	public static void verifyState2(State state, State oldState, TestFunction t) {
		verifyState(state, oldState, t, 1, 2);
	}

	// parameter creation function
	public static Collection<Arguments> data() {
		final List<ExecutionDescription<?>> executionDescriptions = List.of( //
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
