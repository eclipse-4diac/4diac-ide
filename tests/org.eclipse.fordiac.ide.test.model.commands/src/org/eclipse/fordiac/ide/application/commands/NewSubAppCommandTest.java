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

import org.eclipse.fordiac.ide.application.editparts.ElementEditPartFactory;
import org.eclipse.fordiac.ide.gef.editparts.Abstract4diacEditPartFactory;
import org.eclipse.fordiac.ide.model.commands.create.ConnectionCommandsTest;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.junit.jupiter.params.provider.Arguments;

public class NewSubAppCommandTest extends FBNetworkTestBase {

	protected static State initEmptyState() {
		return new State();
	}

	protected static void verifyEmptyInitialState(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 0);
	}

	public static State createEmptySubApp(final State s) {
		s.setCommand(new NewSubAppCommand(s.getFbNetwork(), List.of(), 0, 0));
		return commandExecution(s);
	}

	protected static void verifyEmptySubAppCreation(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 1);
		t.test(s.getFbNetwork().getNetworkElements().get(0) instanceof SubApp);
		final SubApp subapp = (SubApp) s.getFbNetwork().getNetworkElements().get(0);
		t.test(subapp.getSubAppNetwork().getNetworkElements().size(), 0);
	}

	protected static State initState() {
		State s = initEmptyState();
		WithCreateTest.createInterfaceElements(s);
		s = ConnectionCommandsTest.addIOWiths(s);
		s = WithCreateTest.updateNetworkElements(s);
		return s;
	}

	protected static void verifyInitialState(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 2);
	}

	private static EditPart asEditPart(final Object o) {
		final Abstract4diacEditPartFactory e = new ElementEditPartFactory(null);
		return e.createEditPart(null, o);
	}

	protected static State createSubApp(final State s) {
		s.setCommand(
				new NewSubAppCommand(s.getFbNetwork(),
						List.of(asEditPart(s.getFbNetwork().getNetworkElements().get(1))), 0, 0));
		return commandExecution(s);
	}

	protected static void verifySubAppCreation(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 2);
		t.test(!s.getFbNetwork().isSubApplicationNetwork());

		final SubApp subapp = (SubApp) s.getFbNetwork().getNetworkElements().stream().filter(SubApp.class::isInstance)
				.findFirst().orElse(null);
		if (subapp == null) {
			t.test(subapp);
		} else {
			t.test(subapp.getSubAppNetwork().getNetworkElements().size(), 1);

			t.test(subapp.getSubAppNetwork().isSubApplicationNetwork());

			t.test(!subapp.isNestedInSubApp());
		}
	}

	protected static void verifySubAppCreationWithConnections(final State s, final State o, final TestFunction t) {
		verifySubAppCreation(s, o, t);

		// extract the functionblock in the outer network
		final FB fb = (FB) s.getFbNetwork().getNetworkElements().stream().filter(FB.class::isInstance)
				.findFirst().orElse(null);


		// get the subapp from the outer network
		final SubApp subapp = (SubApp) s.getFbNetwork().getNetworkElements().stream().filter(SubApp.class::isInstance)
				.findFirst().orElse(null);

		if (subapp == null || fb == null) {
			t.test(subapp);
			t.test(fb);
		} else {
			// get the functionblock in the subapp
			t.test(subapp.getSubAppNetwork().getNetworkElements().size(), 1);
			final FB subappFB = (FB) subapp.getSubAppNetwork().getNetworkElements().get(0);
			t.test(subappFB);

			// data connection from outer fb output to subapp interface
			t.test(fb.getInterface().getOutputVars().get(0).getOutputConnections().get(0).getDestination(),
					subapp.getInterface().getInputVars().get(0));
			// data connection from subapp interface to inner fb
			t.test(subapp.getInterface().getInputVars().get(0).getOutputConnections().get(0).getDestination(),
					subappFB.getInterface().getInputVars().get(0));

			// event connection from outer fb output to subapp interface
			t.test(fb.getInterface().getEventOutputs().get(0).getOutputConnections().get(0).getDestination(),
					subapp.getInterface().getEventInputs().get(0));
			// event connection from subapp interface to inner fb
			t.test(subapp.getInterface().getEventInputs().get(0).getOutputConnections().get(0).getDestination(),
					subappFB.getInterface().getEventInputs().get(0));

		}
	}

	// parameter creation function
	protected static Collection<Arguments> data() {
		final Collection<Arguments> a = new ArrayList<>();
		a.addAll(describeCommand("Start with an empty FBNetwork", //$NON-NLS-1$
				NewSubAppCommandTest::initEmptyState, //
				(StateVerifier<State>) NewSubAppCommandTest::verifyEmptyInitialState, //
				List.of( //
						new ExecutionDescription<>("Create empty SubApp", //$NON-NLS-1$
								NewSubAppCommandTest::createEmptySubApp, //
								NewSubAppCommandTest::verifyEmptySubAppCreation //
								)) //
				));

		//

		a.addAll(describeCommand("Start with two FBs in FBNetwork", //$NON-NLS-1$
				NewSubAppCommandTest::initState, //
				(StateVerifier<State>) NewSubAppCommandTest::verifyInitialState, //
				List.of( //
						new ExecutionDescription<>("Create SubApp", //$NON-NLS-1$
								NewSubAppCommandTest::createSubApp, //
								NewSubAppCommandTest::verifySubAppCreation //
								)) //
				));

		a.addAll(describeCommand("Start with two FBs in FBNetwork", //$NON-NLS-1$
				NewSubAppCommandTest::initState, //
				(StateVerifier<State>) NewSubAppCommandTest::verifyInitialState, //
				List.of( //
						new ExecutionDescription<>("Create Data Connections", //$NON-NLS-1$
								ConnectionCommandsTest::workingAddDataConnection, //
								ConnectionCommandsTest::verifyDataConnection //
								), //
						new ExecutionDescription<>("Create Event Connections", //$NON-NLS-1$
								ConnectionCommandsTest::workingAddEventConnection, //
								ConnectionCommandsTest::verifyEventConnection //
								), //
						new ExecutionDescription<>("Create SubApp", //$NON-NLS-1$
								NewSubAppCommandTest::createSubApp, //
								NewSubAppCommandTest::verifySubAppCreationWithConnections //
								)) //
				));

		return a;
	}

}
