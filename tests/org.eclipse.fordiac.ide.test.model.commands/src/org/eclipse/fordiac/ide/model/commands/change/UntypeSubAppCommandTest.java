/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.fordiac.ide.application.commands.NewSubAppCommandTest;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommandTest;
import org.eclipse.fordiac.ide.model.commands.testinfra.FBNetworkTestBase;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.test.model.typelibrary.SubAppTypeEntryMock;
import org.junit.jupiter.params.provider.Arguments;

public class UntypeSubAppCommandTest extends FBNetworkTestBase {

	private static final String SUBAPP = "SubApp"; //$NON-NLS-1$
	private static final String FUNCTIONBLOCK = "functionblock"; //$NON-NLS-1$
	private static final String FUNCTIONBLOCK_1 = "functionblock_1"; //$NON-NLS-1$

	private static State initState() {
		State s = new State();
		s = NewSubAppCommandTest.createEmptySubApp(s);
		final SubApp subapp = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);

		final SubAppType subappType = LibraryElementFactory.eINSTANCE.createSubAppType();
		subappType.setInterfaceList(LibraryElementFactory.eINSTANCE.createInterfaceList());
		subappType.setName(SUBAPP);
		subappType.setFBNetwork(subapp.getSubAppNetwork());
		subapp.setSubAppNetwork(null);

		subapp.setTypeEntry(
				new SubAppTypeEntryMock(subappType, TypeLibraryManager.INSTANCE.getTypeLibrary(null), null));

		return s;
	}

	private static void verifyInitialState(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 1);
		final SubApp subapp = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		t.test(subapp);
		t.test(subapp.getTypeEntry());
		t.test(subapp.getType());
		t.test(subapp.isTyped());
		t.test(subapp.getType().getFBNetwork().getNetworkElements().size(), 0);
	}

	private static State initStateWithFilledSubAppNetwork() {
		State s = initState();
		s = FBCreateCommandTest.executeCommand(s);
		s = FBCreateCommandTest.executeCommand(s);
		final SubApp subapp = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		final FB fb0 = (FB) s.getFbNetwork().getElementNamed(FUNCTIONBLOCK);
		final FB fb1 = (FB) s.getFbNetwork().getElementNamed(FUNCTIONBLOCK_1);
		s.getFbNetwork().getNetworkElements().remove(fb0);
		s.getFbNetwork().getNetworkElements().remove(fb1);
		subapp.getType().getFBNetwork().getNetworkElements().add(fb0);
		subapp.getType().getFBNetwork().getNetworkElements().add(fb1);
		return s;
	}

	private static void verifyInitialStateWithFilledSubAppNetwork(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 1);
		final SubApp subapp = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		t.test(subapp);
		t.test(subapp.getTypeEntry());
		t.test(subapp.getType());
		t.test(subapp.isTyped());
		t.test(subapp.getType().getFBNetwork().getNetworkElements().size(), 2);
	}

	private static State untypeSubApp(final State s) {
		final SubApp subapp = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		s.setCommand(new UntypeSubAppCommand(subapp));
		return commandExecution(s);
	}

	private static void verifyUntypeSubApp(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 1);
		final SubApp subapp = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		t.test(subapp);
		t.test(subapp.getSubAppNetwork());
		t.test(subapp.getSubAppNetwork().getNetworkElements().size(), 0);
		t.test(subapp.getType(), (SubAppType) null);
		t.test(subapp.getTypeEntry(), (TypeEntry) null);
	}

	private static void verifyUntypeSubAppWithFilledSubAppNetwork(final State s, final State o, final TestFunction t) {
		t.test(s.getFbNetwork().getNetworkElements().size(), 1);
		final SubApp subapp = (SubApp) s.getFbNetwork().getElementNamed(SUBAPP);
		t.test(subapp);
		t.test(subapp.getSubAppNetwork());
		t.test(subapp.getSubAppNetwork().getNetworkElements().size(), 2);
		t.test(subapp.getType(), (SubAppType) null);
		t.test(subapp.getTypeEntry(), (TypeEntry) null);
	}

	// parameter creation function
	protected static Collection<Arguments> data() {
		final Collection<Arguments> a = new ArrayList<>();

		a.addAll(describeCommand("Start with typed subapp and an empty subapp network", //$NON-NLS-1$
				UntypeSubAppCommandTest::initState, //
				(StateVerifier<State>) UntypeSubAppCommandTest::verifyInitialState, //
				List.of( //
						new ExecutionDescription<>("untype subapp", //$NON-NLS-1$
								UntypeSubAppCommandTest::untypeSubApp, //
								UntypeSubAppCommandTest::verifyUntypeSubApp //
								)) //
				));

		a.addAll(describeCommand("Start with typed subapp and two FBs in subapp network", //$NON-NLS-1$
				UntypeSubAppCommandTest::initStateWithFilledSubAppNetwork, //
				(StateVerifier<State>) UntypeSubAppCommandTest::verifyInitialStateWithFilledSubAppNetwork, //
				List.of( //
						new ExecutionDescription<>("untype subapp", //$NON-NLS-1$
								UntypeSubAppCommandTest::untypeSubApp, //
								UntypeSubAppCommandTest::verifyUntypeSubAppWithFilledSubAppNetwork //
								)) //
				));

		return a;
	}

}
