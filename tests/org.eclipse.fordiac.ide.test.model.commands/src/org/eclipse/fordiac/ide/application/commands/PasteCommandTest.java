/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.create.ConnectionCommandsTest;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.junit.jupiter.params.provider.Arguments;

public class PasteCommandTest extends FBNetworkTestBase {

	private static State initEmptyState() {
		return new State();
	}

	private static State initState() {
		State s = initEmptyState();
		WithCreateTest.createInterfaceElements(s);
		s = ConnectionCommandsTest.addIOWiths(s);
		s = WithCreateTest.updateNetworkElements(s);
		return s;
	}

	private static void verifyInitialState(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 2);
	}

	private static State copyFB(final State s) {
		s.setCommand(
				new PasteCommand(List.of(s.getFbNetwork().getNetworkElements().get(1)), s.getFbNetwork(), 0, 0));
		return commandExecution(s);
	}

	private static void verifyCopyFB(final State s, final State o, final TestFunction t) {

		t.test(s.getFbNetwork().getNetworkElements().size(), 3);

		t.test(s.getFbNetwork().getDataConnections().size(), 1);
		t.test(s.getFbNetwork().getEventConnections().size(), 1);

		t.test(s.getMessages().size(), 0);

	}

	private static State copyFBWithConnections(final State s) {
		s.setCommand(new PasteCommand(List.of(s.getFbNetwork().getNetworkElements().get(1), //
				new ConnectionReference(s.getFbNetwork().getDataConnections().get(0)), //
				new ConnectionReference(s.getFbNetwork().getEventConnections().get(0)) //
				), s.getFbNetwork(), 0, 0));
		return commandExecution(s);
	}

	private static void verifyCopyFBWithConnections(final State s, final State o, final TestFunction t) {

		t.test(s.getFbNetwork().getNetworkElements().size(), 3);

		t.test(s.getFbNetwork().getDataConnections().size(), 2);
		t.test(s.getFbNetwork().getEventConnections().size(), 2);

		t.test(s.getFbNetwork().getNetworkElements().get(0).getInterface().getOutputVars().get(0).getOutputConnections()
				.get(1).getDestination(),
				s.getFbNetwork().getNetworkElements().get(2).getInterface().getInputVars().get(0));

		t.test(s.getFbNetwork().getNetworkElements().get(0).getInterface().getEventOutputs().get(0)
				.getOutputConnections().get(1).getDestination(),
				s.getFbNetwork().getNetworkElements().get(2).getInterface().getEventInputs().get(0));

		t.test(s.getMessages().size(), 0);

	}

	// parameter creation function
	protected static Collection<Arguments> data() {
		final Collection<Arguments> a = new ArrayList<>();

		a.addAll(describeCommand("Start with two FBs in FBNetwork", //$NON-NLS-1$
				PasteCommandTest::initState, //
				(StateVerifier<State>) PasteCommandTest::verifyInitialState, //
				List.of( //
						new ExecutionDescription<>("Create Data Connections", //$NON-NLS-1$
								ConnectionCommandsTest::workingAddDataConnection, //
								ConnectionCommandsTest::verifyDataConnection //
								), //
						new ExecutionDescription<>("Create Event Connections", //$NON-NLS-1$
								ConnectionCommandsTest::workingAddEventConnection, //
								ConnectionCommandsTest::verifyEventConnection //
								), //
						new ExecutionDescription<>("copy Functionblock", //$NON-NLS-1$
								PasteCommandTest::copyFB, //
								PasteCommandTest::verifyCopyFB //
								)) //
				));

		a.addAll(describeCommand("Start with two FBs in FBNetwork", //$NON-NLS-1$
				PasteCommandTest::initState, //
				(StateVerifier<State>) PasteCommandTest::verifyInitialState, //
				List.of( //
						new ExecutionDescription<>("Create Data Connections", //$NON-NLS-1$
								ConnectionCommandsTest::workingAddDataConnection, //
								ConnectionCommandsTest::verifyDataConnection //
								), //
						new ExecutionDescription<>("Create Event Connections", //$NON-NLS-1$
								ConnectionCommandsTest::workingAddEventConnection, //
								ConnectionCommandsTest::verifyEventConnection //
								), //
						new ExecutionDescription<>("copy Functionblock with connections", //$NON-NLS-1$
								PasteCommandTest::copyFBWithConnections, //
								PasteCommandTest::verifyCopyFBWithConnections //
								)) //
				));

		return a;
	}

}
