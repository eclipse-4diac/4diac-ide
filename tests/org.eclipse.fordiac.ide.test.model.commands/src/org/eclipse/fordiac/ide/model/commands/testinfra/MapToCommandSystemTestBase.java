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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.testinfra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.test.model.FordiacProjectLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.Arguments;
import org.osgi.framework.Bundle;

public abstract class MapToCommandSystemTestBase extends CommandTestBase<MapToCommandSystemTestBase.State> {

	protected static final String PROJECT_NAME = "MapToCommandTest"; //$NON-NLS-1$
	protected static final String SYSTEM_NAME = "MapToCommandTest"; //$NON-NLS-1$
	protected static final String APPLICATION_NAME = "App"; //$NON-NLS-1$
	protected static final String DEVICE_NAME = "Device1"; //$NON-NLS-1$
	protected static final String RESOURCE_NAME = "Res1"; //$NON-NLS-1$

	protected static final String FB_SRC = "SRC"; //$NON-NLS-1$
	protected static final String FB_MID = "MID"; //$NON-NLS-1$
	protected static final String FB_DST = "DST"; //$NON-NLS-1$

	private static AutomationSystem templateSystem;

	@BeforeAll
	static void loadSystemOnce() throws Exception {
		final Bundle bundle = Platform.getBundle("org.eclipse.fordiac.ide.test.model.commands"); //$NON-NLS-1$
		final var loader = new FordiacProjectLoader(bundle, new Path("data/" + PROJECT_NAME)); //$NON-NLS-1$
		templateSystem = loader.getAutomationSystem(SYSTEM_NAME);
	}

	protected static class State extends CommandTestBase.StateBase {
		private final AutomationSystem system;
		private final Application application;
		private final Resource resource;

		private final BlockFBNetworkElement src;
		private final BlockFBNetworkElement mid;
		private final BlockFBNetworkElement dst;

		protected State() {
			this(EcoreUtil.copy(templateSystem)); // fresh working copy per test execution
		}

		private State(final AutomationSystem system) {
			this.system = system;
			this.application = system.getApplicationNamed(APPLICATION_NAME);
			this.resource = system.getDeviceNamed(DEVICE_NAME).getResourceNamed(RESOURCE_NAME);
			this.src = (BlockFBNetworkElement) application.getFBNetwork().getElementNamed(FB_SRC);
			this.mid = (BlockFBNetworkElement) application.getFBNetwork().getElementNamed(FB_MID);
			this.dst = (BlockFBNetworkElement) application.getFBNetwork().getElementNamed(FB_DST);
		}

		@Override
		public Object getClone() {
			return new State(EcoreUtil.copy(system));
		}

		public AutomationSystem getSystem() {
			return system;
		}

		public Application getApplication() {
			return application;
		}

		public Resource getResource() {
			return resource;
		}

		public FBNetwork getApplicationNetwork() {
			return application.getFBNetwork();
		}

		public FBNetwork getResourceNetwork() {
			return resource.getFBNetwork();
		}

		public BlockFBNetworkElement getSrc() {
			return src;
		}

		public BlockFBNetworkElement getMid() {
			return mid;
		}

		public BlockFBNetworkElement getDst() {
			return dst;
		}

		public BlockFBNetworkElement getResourceElement(final String name) {
			return (BlockFBNetworkElement) getResourceNetwork().getElementNamed(name);
		}
	}

	protected static Collection<Arguments> createCommands(final List<ExecutionDescription<?>> executionDescriptions) {
		final List<Arguments> commands = new ArrayList<>();

		commands.addAll(describeCommand("Start from loaded mapping system", State::new, //$NON-NLS-1$
				(MapToCommandSystemTestBase.StateVerifier<State>) MapToCommandSystemTestBase::verifyInitial,
				executionDescriptions, CommandTestBase::defaultUndoCommand, CommandTestBase::defaultRedoCommand));

		return commands;
	}

	protected static void verifyInitial(final State state, final State oldState, final TestFunction t) {
		t.test(state.getSrc().isMapped());
		t.test(state.getDst().isMapped());
		t.test(!state.getMid().isMapped());

		t.test(state.getSrc().getOpposite() != null);
		t.test(state.getDst().getOpposite() != null);
		t.test(state.getSrc().getOpposite().eContainer() == state.getResourceNetwork());
		t.test(state.getDst().getOpposite().eContainer() == state.getResourceNetwork());

		t.test(state.getResourceNetwork().getEventConnections().isEmpty());
		t.test(state.getResourceNetwork().getDataConnections().isEmpty());
		t.test(state.getSystem().getMapping().size() == 2);
	}

	protected static boolean hasEventConnection(final FBNetwork fbNetwork, final String srcFb, final String srcEvent,
			final String dstFb, final String dstEvent) {
		return fbNetwork.getEventConnections().stream()
				.anyMatch(con -> matchesEventConnection(con, srcFb, srcEvent, dstFb, dstEvent));
	}

	private static boolean matchesEventConnection(final Connection con, final String srcFb, final String srcEvent,
			final String dstFb, final String dstEvent) {
		if (con.getSourceElement() == null || con.getDestinationElement() == null) {
			return false;
		}
		if (!(con.getSource() instanceof Event) || !(con.getDestination() instanceof Event)) {
			return false;
		}
		return srcFb.equals(con.getSourceElement().getName()) && dstFb.equals(con.getDestinationElement().getName())
				&& srcEvent.equals(con.getSource().getName()) && dstEvent.equals(con.getDestination().getName());
	}
}