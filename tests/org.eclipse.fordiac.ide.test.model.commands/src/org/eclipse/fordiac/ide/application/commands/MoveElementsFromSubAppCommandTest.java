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
 *   Ernst Blecha, Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.ConnectionCommandsTest;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.swt.graphics.Point;
import org.junit.jupiter.params.provider.Arguments;

public class MoveElementsFromSubAppCommandTest extends FBNetworkTestBase {

	private static final String SUBAPP = "SubApp"; //$NON-NLS-1$
	private static final String FB1 = "functionblock"; //$NON-NLS-1$
	private static final String FB2 = "functionblock_1"; //$NON-NLS-1$

	private static State initState() {
		State s = new State();
		WithCreateTest.createInterfaceElements(s);
		s = ConnectionCommandsTest.addIOWiths(s);
		s = WithCreateTest.updateNetworkElements(s);
		s = NewSubAppCommandTest.createEmptySubApp(s);

		final FB fb0 = (FB) s.getFbNetwork().getNetworkElements().remove(0);
		final FB fb1 = (FB) s.getFbNetwork().getNetworkElements().remove(0);
		final SubApp sub = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		sub.getSubAppNetwork().getNetworkElements().add(fb0);
		sub.getSubAppNetwork().getNetworkElements().add(fb1);

		return s;
	}

	private static void verifyInitialState(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 1);
		final SubApp sub = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		t.test(sub.getSubAppNetwork().getNetworkElements().size(), 2);
	}

	private static State moveFB(final State s) {
		final SubApp sub = (SubApp) s.getFbNetwork().getNetworkElements().get(0);
		final FBNetworkElement elem = sub.getSubAppNetwork().getNetworkElements().get(0);
		s.setCommand(
				new MoveAndReconnectCommand(Arrays.asList(elem), new Point(0, 0)));
		return commandExecution(s);
	}

	private static void verifyMoveFB(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 2);
		final SubApp sub = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		t.test(sub.getSubAppNetwork().getNetworkElements().size(), 1);
	}

	private static void verifyMoveFBwithConnections(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 2);
		final SubApp sub = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		t.test(sub.getSubAppNetwork().getNetworkElements().size(), 1);

		t.test(s.getFbNetwork().getDataConnections().size(), 1);
		t.test(s.getFbNetwork().getEventConnections().size(), 1);
		t.test(sub.getSubAppNetwork().getEventConnections().size(), 1);
		t.test(sub.getSubAppNetwork().getDataConnections().size(), 1);
	}

	public static State addEventConnection(final State state) {
		final SubApp sub = (SubApp) state.getFbNetwork().getElementNamed(SUBAPP);
		final InterfaceList fb1 = sub.getSubAppNetwork().getFBNamed(FB1).getInterface();
		final InterfaceList fb2 = sub.getSubAppNetwork().getFBNamed(FB2).getInterface();

		final AbstractConnectionCreateCommand c = new EventConnectionCreateCommand(sub.getSubAppNetwork());
		c.setSource(fb1.getEventOutputs().get(0));
		c.setDestination(fb2.getEventInputs().get(0));
		state.setCommand(c);
		return commandExecution(state);
	}

	public static void verifyEventConnection(final State state, final State oldState, final TestFunction t) {
		final SubApp sub = (SubApp) state.getFbNetwork().getElementNamed(SUBAPP);
		final InterfaceList fb1 = sub.getSubAppNetwork().getFBNamed(FB1).getInterface();
		final InterfaceList fb2 = sub.getSubAppNetwork().getFBNamed(FB2).getInterface();

		t.test(state.getMessages().isEmpty());
		t.test(state.getFbNetwork().getEventConnections().size(), 0);
		t.test(sub.getSubAppNetwork().getEventConnections().size(), 1);

		t.test(fb1.getEventOutputs().get(0).getOutputConnections().size(), 1);
		t.test(fb1.getEventOutputs().get(0).getOutputConnections().get(0).getDestination(),
				fb2.getEventInputs().get(0));
	}

	public static State addDataConnection(final State state) {
		final SubApp sub = (SubApp) state.getFbNetwork().getElementNamed(SUBAPP);
		final InterfaceList fb1 = sub.getSubAppNetwork().getFBNamed(FB1).getInterface();
		final InterfaceList fb2 = sub.getSubAppNetwork().getFBNamed(FB2).getInterface();

		final AbstractConnectionCreateCommand c = new DataConnectionCreateCommand(sub.getSubAppNetwork());
		c.setSource(fb1.getOutputVars().get(0));
		c.setDestination(fb2.getInputVars().get(0));
		state.setCommand(c);
		return commandExecution(state);
	}

	public static void verifyDataConnection(final State state, final State oldState, final TestFunction t) {
		final SubApp sub = (SubApp) state.getFbNetwork().getElementNamed(SUBAPP);
		final InterfaceList fb1 = sub.getSubAppNetwork().getFBNamed(FB1).getInterface();
		final InterfaceList fb2 = sub.getSubAppNetwork().getFBNamed(FB2).getInterface();

		t.test(state.getMessages().isEmpty());

		t.test(state.getFbNetwork().getDataConnections().size(), 0);
		t.test(sub.getSubAppNetwork().getDataConnections().size(), 1);

		t.test(fb1.getOutputVars().get(0).getOutputConnections().size(), 1);
		t.test(fb1.getOutputVars().get(0).getOutputConnections().get(0).getDestination(), fb2.getInputVars().get(0));
	}

	// parameter creation function
	protected static Collection<Arguments> data() {
		final Collection<Arguments> a = new ArrayList<>();

		a.addAll(describeCommand("Start with two FBs in SubAppNetwork", //$NON-NLS-1$
				MoveElementsFromSubAppCommandTest::initState, //
				(StateVerifier<State>) MoveElementsFromSubAppCommandTest::verifyInitialState, //
				List.of( //
						new ExecutionDescription<>("move Functionblock from SubApp", //$NON-NLS-1$
								MoveElementsFromSubAppCommandTest::moveFB, //
								MoveElementsFromSubAppCommandTest::verifyMoveFB //
								)) //
				));

		a.addAll(describeCommand("Start with two FBs in SubAppNetwork", //$NON-NLS-1$
				MoveElementsFromSubAppCommandTest::initState, //
				(StateVerifier<State>) MoveElementsFromSubAppCommandTest::verifyInitialState, //
				List.of( //
						new ExecutionDescription<>("Create Data Connections", //$NON-NLS-1$
								MoveElementsFromSubAppCommandTest::addDataConnection, //
								MoveElementsFromSubAppCommandTest::verifyDataConnection //
								), //
						new ExecutionDescription<>("Create Event Connections", //$NON-NLS-1$
								MoveElementsFromSubAppCommandTest::addEventConnection, //
								MoveElementsFromSubAppCommandTest::verifyEventConnection //
								), //
						new ExecutionDescription<>("move Functionblock from SubApp", //$NON-NLS-1$
								MoveElementsFromSubAppCommandTest::moveFB, //
								MoveElementsFromSubAppCommandTest::verifyMoveFBwithConnections //
								)) //
				));

		return a;
	}

}
