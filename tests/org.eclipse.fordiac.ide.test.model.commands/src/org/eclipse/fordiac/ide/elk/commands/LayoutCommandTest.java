/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.elk.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.application.commands.MoveElementsFromSubAppCommandTest;
import org.eclipse.fordiac.ide.application.commands.NewSubAppCommandTest;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.create.ConnectionCommandsTest;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.junit.jupiter.params.provider.Arguments;

public class LayoutCommandTest extends FBNetworkTestBase {

	private static final String SUBAPP = "SubApp"; //$NON-NLS-1$

	private static State initState() {
		State s = new State();
		WithCreateTest.createInterfaceElements(s);
		s = ConnectionCommandsTest.addIOWiths(s);
		s = WithCreateTest.updateNetworkElements(s);
		/* All elements are initialized with position (0,0) */
		return s;
	}

	private static State initSubAppState() {
		State s = initState();
		s = NewSubAppCommandTest.createEmptySubApp(s);

		final FB fb0 = (FB) s.getFbNetwork().getNetworkElements().remove(0);
		final FB fb1 = (FB) s.getFbNetwork().getNetworkElements().remove(0);
		final SubApp sub = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		sub.getSubAppNetwork().getNetworkElements().add(fb0);
		sub.getSubAppNetwork().getNetworkElements().add(fb1);

		return s;
	}

	private static void verifyInitialState(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 2);
		s.getFbNetwork().getNetworkElements().forEach(elem -> {
			final Position pos = elem.getPosition();
			t.test(pos.getX(), 0.0);
			t.test(pos.getY(), 0.0);
		});
	}

	private static void verifyInitialSubAppState(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 1);
		final SubApp sub = s.getFbNetwork().getSubAppNamed(SUBAPP);
		t.test(sub);
		sub.getSubAppNetwork().getNetworkElements().forEach(elem -> {
			final Position pos = elem.getPosition();
			t.test(pos.getX(), 0.0);
			t.test(pos.getY(), 0.0);
		});
	}

	private static State layoutFBNetwork(final State s) {
		return commandExecution(createLayoutCommand(s, s.getFbNetwork()));
	}

	private static State layoutSubAppNetwork(final State s) {
		final FBNetwork network = s.getFbNetwork().getSubAppNamed(SUBAPP).getSubAppNetwork();
		return commandExecution(createLayoutCommand(s, network));
	}

	private static State createLayoutCommand(final State s, final FBNetwork network) {
		final FordiacLayoutData data = new FordiacLayoutData();

		final FBNetworkElement elem1 = network.getNetworkElements().get(0);
		final FBNetworkElement elem2 = network.getNetworkElements().get(1);
		final Position pos1 = LibraryElementFactory.eINSTANCE.createPosition();
		final Position pos2 = LibraryElementFactory.eINSTANCE.createPosition();
		pos1.setX(100);
		pos1.setY(100);
		pos2.setX(200);
		pos2.setY(200);
		data.addPosition(elem1, pos1);
		data.addPosition(elem2, pos2);

		final PointList pList = new PointList();
		pList.addPoint(100, 100);
		pList.addPoint(150, 150);
		pList.addPoint(200, 200);
		data.addConnectionPoints(network.getEventConnections().get(0), pList);
		data.addConnectionPoints(network.getDataConnections().get(0), pList.getCopy());

		s.setCommand(new LayoutCommand(data));
		return s;
	}

	private static void verifyLayout(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 2);
		final FB fb1 = (FB) s.getFbNetwork().getNetworkElements().get(0);
		final FB fb2 = (FB) s.getFbNetwork().getNetworkElements().get(1);
		t.test(fb1);
		t.test(fb2);

		t.test(s.getFbNetwork().getDataConnections().size(), 1);
		t.test(fb1.getInterface().getOutputVars().get(0).getOutputConnections().get(0).getDestination(),
				fb2.getInterface().getInputVars().get(0));

		t.test(s.getFbNetwork().getEventConnections().size(), 1);
		t.test(fb1.getInterface().getEventOutputs().get(0).getOutputConnections().get(0).getDestination(),
				fb2.getInterface().getEventInputs().get(0));

		t.test(s.getMessages().size(), 0);

		final Position pos1 = fb1.getPosition();
		final Position pos2 = fb2.getPosition();
		t.test(pos1.getX(), 100.0);
		t.test(pos1.getY(), 100.0);
		t.test(pos2.getX(), 200.0);
		t.test(pos2.getY(), 200.0);

		/* dx1 = middlePoint.x - firstPoint.x = 50 */
		t.test(s.getFbNetwork().getDataConnections().get(0).getRoutingData().getDx1(), fromScreen(50));
		t.test(s.getFbNetwork().getEventConnections().get(0).getRoutingData().getDx1(), fromScreen(50));
	}

	private static void verifySubAppLayout(final State s, final State o, final TestFunction t) {
		final SubApp sub = s.getFbNetwork().getSubAppNamed(SUBAPP);
		final FBNetwork network = sub.getSubAppNetwork();
		t.test(sub);
		t.test(network.getNetworkElements().size(), 2);

		final FB fb1 = (FB) network.getNetworkElements().get(0);
		final FB fb2 = (FB) network.getNetworkElements().get(1);
		t.test(fb1);
		t.test(fb2);

		t.test(network.getDataConnections().size(), 1);
		t.test(fb1.getInterface().getOutputVars().get(0).getOutputConnections().get(0).getDestination(),
				fb2.getInterface().getInputVars().get(0));

		t.test(network.getEventConnections().size(), 1);
		t.test(fb1.getInterface().getEventOutputs().get(0).getOutputConnections().get(0).getDestination(),
				fb2.getInterface().getEventInputs().get(0));

		t.test(s.getMessages().size(), 0);

		final Position pos1 = fb1.getPosition();
		final Position pos2 = fb2.getPosition();
		t.test(pos1.getX(), 100.0);
		t.test(pos1.getY(), 100.0);
		t.test(pos2.getX(), 200.0);
		t.test(pos2.getY(), 200.0);

		/* dx1 = middlePoint.x - firstPoint.x = 50 */
		t.test(network.getDataConnections().get(0).getRoutingData().getDx1(), fromScreen(50));
		t.test(network.getEventConnections().get(0).getRoutingData().getDx1(), fromScreen(50));
	}

	// parameter creation function
	protected static Collection<Arguments> data() {
		final Collection<Arguments> a = new ArrayList<>();

		a.addAll(describeCommand("Start with two FBs in FBNetwork", //$NON-NLS-1$
				LayoutCommandTest::initState, //
				(StateVerifier<State>) LayoutCommandTest::verifyInitialState, //
				List.of( //
						new ExecutionDescription<>("Create Data Connections", //$NON-NLS-1$
								ConnectionCommandsTest::workingAddDataConnection, //
								ConnectionCommandsTest::verifyDataConnection //
						), //
						new ExecutionDescription<>("Create Event Connections", //$NON-NLS-1$
								ConnectionCommandsTest::workingAddEventConnection, //
								ConnectionCommandsTest::verifyEventConnection //
						), //
						new ExecutionDescription<>("layout FBNetwork", //$NON-NLS-1$
								LayoutCommandTest::layoutFBNetwork, //
								LayoutCommandTest::verifyLayout //
						)) //
		));

		a.addAll(describeCommand("Start with two FBs in SubAppNetwork", //$NON-NLS-1$
				LayoutCommandTest::initSubAppState, //
				(StateVerifier<State>) LayoutCommandTest::verifyInitialSubAppState, //
				List.of( //
						new ExecutionDescription<>("Create Data Connections", //$NON-NLS-1$
								MoveElementsFromSubAppCommandTest::addDataConnection, //
								MoveElementsFromSubAppCommandTest::verifyDataConnection //
						), //
						new ExecutionDescription<>("Create Event Connections", //$NON-NLS-1$
								MoveElementsFromSubAppCommandTest::addEventConnection, //
								MoveElementsFromSubAppCommandTest::verifyEventConnection //
						), //
						new ExecutionDescription<>("layout SubAppNetwork", //$NON-NLS-1$
								LayoutCommandTest::layoutSubAppNetwork, //
								LayoutCommandTest::verifySubAppLayout //
						)) //
		));

		return a;
	}

	private static double fromScreen(final int val) {
		return CoordinateConverter.INSTANCE.screenToIEC61499(val);
	}
}
