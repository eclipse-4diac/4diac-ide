/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Tisya Gupta - initial API and implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock;
import org.junit.jupiter.api.Test;

public class CreateSubAppCrossingConnectionsCommandTest {

	private static FB createFB(final String name) {
		final FB fb = LibraryElementFactory.eINSTANCE.createFB();
		fb.setName(name);
		fb.setPosition(LibraryElementFactory.eINSTANCE.createPosition());
		final InterfaceList il = LibraryElementFactory.eINSTANCE.createInterfaceList();
		final Event ei = LibraryElementFactory.eINSTANCE.createEvent();
		ei.setName("EI"); //$NON-NLS-1$
		ei.setIsInput(true);
		ei.setType(org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary.getInstance().getType(null));
		il.getEventInputs().add(ei);

		final Event eo = LibraryElementFactory.eINSTANCE.createEvent();
		eo.setName("EO"); //$NON-NLS-1$
		eo.setIsInput(false);
		eo.setType(org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary.getInstance().getType(null));
		il.getEventOutputs().add(eo);

		fb.setInterface(il);
		final BasicFBType type = LibraryElementFactory.eINSTANCE.createBasicFBType();
		type.setName("Type_" + name); //$NON-NLS-1$
		fb.setTypeEntry(new FBTypeEntryMock(type, null, null));
		return fb;
	}

	private static UntypedSubApp createSubApp(final String name) {
		final UntypedSubApp subApp = LibraryElementFactory.eINSTANCE.createUntypedSubApp();
		subApp.setName(name);
		subApp.setPosition(LibraryElementFactory.eINSTANCE.createPosition());
		subApp.setInterface(LibraryElementFactory.eINSTANCE.createInterfaceList());
		subApp.setSubAppNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
		return subApp;
	}

	@Test
	public void testNoCommonNetworkDirectCall() {
		final Application app1 = LibraryElementFactory.eINSTANCE.createApplication();
		app1.setName("App1"); //$NON-NLS-1$
		final FBNetwork net1 = LibraryElementFactory.eINSTANCE.createFBNetwork();
		app1.setFBNetwork(net1);
		final FB fb1 = createFB("FB1"); //$NON-NLS-1$
		net1.getNetworkElements().add(fb1);

		final Application app2 = LibraryElementFactory.eINSTANCE.createApplication();
		app2.setName("App2"); //$NON-NLS-1$
		final FBNetwork net2 = LibraryElementFactory.eINSTANCE.createFBNetwork();
		app2.setFBNetwork(net2);
		final FB fb2 = createFB("FB2"); //$NON-NLS-1$
		net2.getNetworkElements().add(fb2);

		assertFalse(CreateSubAppCrossingConnectionsCommand.hasCommonFBNetwork(
				fb1.getInterface().getEventOutputs().get(0),
				fb2.getInterface().getEventInputs().get(0)));

		final CreateSubAppCrossingConnectionsCommand cmd = CreateSubAppCrossingConnectionsCommand
				.createProcessBorderCrossingConnection(
						fb1.getInterface().getEventOutputs().get(0),
						fb2.getInterface().getEventInputs().get(0));

		// Expecting canExecute to be false when there is no common network
		assertFalse(cmd.canExecute());

		// And if execute is called, it should not throw ClassCastException
		cmd.execute();
	}

	@Test
	public void testMoveFBToSubAppInDifferentApplication() {
		final AutomationSystem system = LibraryElementFactory.eINSTANCE.createAutomationSystem();

		final Application app1 = LibraryElementFactory.eINSTANCE.createApplication();
		app1.setName("App1"); //$NON-NLS-1$
		final FBNetwork net1 = LibraryElementFactory.eINSTANCE.createFBNetwork();
		app1.setFBNetwork(net1);
		system.getApplication().add(app1);

		final Application app2 = LibraryElementFactory.eINSTANCE.createApplication();
		app2.setName("App2"); //$NON-NLS-1$
		final FBNetwork net2 = LibraryElementFactory.eINSTANCE.createFBNetwork();
		app2.setFBNetwork(net2);
		system.getApplication().add(app2);

		final FB fb1 = createFB("RAMPO_Count_Q1"); //$NON-NLS-1$
		final FB fb2 = createFB("RAMPO_Count_Q2"); //$NON-NLS-1$
		net1.getNetworkElements().add(fb1);
		net1.getNetworkElements().add(fb2);

		final AbstractConnectionCreateCommand connCmd = new EventConnectionCreateCommand(net1);
		connCmd.setSource(fb1.getInterface().getEventOutputs().get(0));
		connCmd.setDestination(fb2.getInterface().getEventInputs().get(0));
		connCmd.execute();

		final UntypedSubApp subApp2 = createSubApp("SubAppInApp2"); //$NON-NLS-1$
		net2.getNetworkElements().add(subApp2);

		// Move fb1 from app1 into subApp2 in app2
		final MoveAndReconnectCommand moveCmd = new MoveAndReconnectCommand(
				List.of(fb1), new Point(0, 0), subApp2.getSubAppNetwork());

		moveCmd.execute();
		assertTrue(subApp2.getSubAppNetwork().getNetworkElements().contains(fb1));
		assertFalse(net1.getNetworkElements().contains(fb1));
		assertTrue(net1.getEventConnections().isEmpty());
		assertTrue(net2.getEventConnections().isEmpty());
		assertTrue(subApp2.getSubAppNetwork().getEventConnections().isEmpty());
		assertTrue(fb1.getInterface().getEventOutputs().get(0).getOutputConnections().isEmpty());
		assertTrue(fb2.getInterface().getEventInputs().get(0).getInputConnections().isEmpty());

		moveCmd.undo();
		assertTrue(net1.getNetworkElements().contains(fb1));
		assertFalse(subApp2.getSubAppNetwork().getNetworkElements().contains(fb1));
		assertTrue(net1.getEventConnections().size() == 1);
		assertTrue(net1.getEventConnections().contains(connCmd.getConnection()));
		assertTrue(connCmd.getConnection().getSource() == fb1.getInterface().getEventOutputs().get(0));
		assertTrue(connCmd.getConnection().getDestination() == fb2.getInterface().getEventInputs().get(0));
		assertTrue(fb1.getInterface().getEventOutputs().get(0).getOutputConnections()
				.contains(connCmd.getConnection()));
		assertTrue(fb2.getInterface().getEventInputs().get(0).getInputConnections()
				.contains(connCmd.getConnection()));

		moveCmd.redo();
		assertTrue(subApp2.getSubAppNetwork().getNetworkElements().contains(fb1));
		assertFalse(net1.getNetworkElements().contains(fb1));
		assertTrue(net1.getEventConnections().isEmpty());
		assertTrue(net2.getEventConnections().isEmpty());
		assertTrue(subApp2.getSubAppNetwork().getEventConnections().isEmpty());
		assertTrue(fb1.getInterface().getEventOutputs().get(0).getOutputConnections().isEmpty());
		assertTrue(fb2.getInterface().getEventInputs().get(0).getInputConnections().isEmpty());
	}
}
