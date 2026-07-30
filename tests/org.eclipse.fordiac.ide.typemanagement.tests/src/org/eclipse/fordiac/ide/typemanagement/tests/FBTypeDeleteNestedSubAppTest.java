/*******************************************************************************
 * Copyright (c) 2026 Dimitrios Kalligaridis
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dimitrios Kalligaridis - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.tests;

import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.APPLICATION_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.CONTAINER_SUBAPP;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.DEEPLY_NESTED_INSTANCE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.DEEPLY_NESTED_INSTANCE_SINK;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.DEEP_CONTAINER_SUBAPP;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.MY_BLOCK;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.MY_BLOCK_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.NESTED_INSTANCE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.NESTED_INSTANCE_SINK;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.PROJECT_PATH;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.SYSTEM_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.TOP_INSTANCE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.TOP_INSTANCE_SINK;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CORE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FBTypeDeleteNestedSubAppTest {

	private static final Set<String> MY_BLOCK_INSTANCES = Set.of(TOP_INSTANCE, TOP_INSTANCE_SINK, NESTED_INSTANCE,
			NESTED_INSTANCE_SINK, DEEPLY_NESTED_INSTANCE, DEEPLY_NESTED_INSTANCE_SINK);

	private static final Set<String> INSTANCE_CONNECTIONS = Set.of("TopInstance.CNF -> TopInstanceSink.REQ", //$NON-NLS-1$
			"TopInstance.DO -> TopInstanceSink.DI", "NestedInstance.CNF -> NestedInstanceSink.REQ", //$NON-NLS-1$ //$NON-NLS-2$
			"NestedInstance.DO -> NestedInstanceSink.DI", //$NON-NLS-1$
			"DeeplyNestedInstance.CNF -> DeeplyNestedInstanceSink.REQ", //$NON-NLS-1$
			"DeeplyNestedInstance.DO -> DeeplyNestedInstanceSink.DI"); //$NON-NLS-1$

	private IProject project;
	private TypeLibrary typeLibrary;

	@BeforeAll
	static void preloadSystemManager() {
		SystemManager.INSTANCE.name();
	}

	@BeforeEach
	void loadFixture() throws Exception {
		project = RefactoringTestSupport.importProjectIntoWorkspace(PROJECT_NAME, PROJECT_PATH);
		RefactoringTestSupport.linkStandardLibraries(project, CORE);
		typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
	}

	@AfterEach
	void disposeFixture() throws Exception {
		RefactoringTestSupport.deleteProject(project);
	}

	@Test
	void deleteMyBlock_removesItFromWorkspace() throws Exception {
		assertTrue(file(MY_BLOCK_FILE).exists());

		deleteMyBlock();

		assertFalse(file(MY_BLOCK_FILE).exists());
	}

	@Test
	void deleteMyBlock_clearsItFromTypeLibrary() throws Exception {
		assertNotNull(typeLibrary.getFBTypeEntry(MY_BLOCK));

		deleteMyBlock();

		assertNull(typeLibrary.getFBTypeEntry(MY_BLOCK));
	}

	@Test
	void deleteMyBlock_keepsInstancesAndConnectionsAtEveryNestingLevel() throws Exception {
		assertEquals(MY_BLOCK_INSTANCES, myBlockInstanceNames());
		assertEquals(INSTANCE_CONNECTIONS, connectionNames());

		deleteMyBlock();

		// DeleteTypeRefactoringParticipant only removes internal FBs whose container is
		// a BaseFBType, so the instances in the application and the connections between
		// them stay untouched on every nesting level.
		assertEquals(MY_BLOCK_INSTANCES, myBlockInstanceNames());
		assertEquals(INSTANCE_CONNECTIONS, connectionNames());
	}

	private void deleteMyBlock() throws Exception {
		RefactoringTestSupport.performDelete(file(MY_BLOCK_FILE));
	}

	private Set<String> myBlockInstanceNames() {
		return networksAtEveryNestingLevel().flatMap(network -> network.getNetworkElements().stream())
				.filter(FB.class::isInstance).map(FBNetworkElement::getName).collect(Collectors.toSet());
	}

	private Set<String> connectionNames() {
		return networksAtEveryNestingLevel()
				.flatMap(network -> Stream.concat(network.getEventConnections().stream(),
						network.getDataConnections().stream()))
				.map(FBTypeDeleteNestedSubAppTest::connectionName).collect(Collectors.toSet());
	}

	private Stream<FBNetwork> networksAtEveryNestingLevel() {
		return Stream.of(system().getApplicationNamed(APPLICATION_NAME).getFBNetwork(),
				subAppNetwork(CONTAINER_SUBAPP), subAppNetwork(CONTAINER_SUBAPP, DEEP_CONTAINER_SUBAPP));
	}

	private FBNetwork subAppNetwork(final String... namePath) {
		return ((UntypedSubApp) findInstance(namePath)).getSubAppNetwork();
	}

	private static String connectionName(final Connection connection) {
		return endpointName(connection.getSource()) + " -> " + endpointName(connection.getDestination()); //$NON-NLS-1$
	}

	private static String endpointName(final IInterfaceElement pin) {
		final BlockFBNetworkElement block = pin.getBlockFBNetworkElement();
		return block.getName() + "." + pin.getRelativeName(block); //$NON-NLS-1$
	}

	private FBNetworkElement findInstance(final String... namePath) {
		return RefactoringTestSupport.findInstance(system(), APPLICATION_NAME, namePath);
	}

	private AutomationSystem system() {
		return (AutomationSystem) typeLibrary.getTypeEntry(file(SYSTEM_FILE)).getType();
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
