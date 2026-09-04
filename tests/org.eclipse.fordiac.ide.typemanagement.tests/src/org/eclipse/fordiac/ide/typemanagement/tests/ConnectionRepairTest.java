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

import static org.eclipse.fordiac.ide.typemanagement.tests.ConnectionRepairTestFixture.APPLICATION_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.ConnectionRepairTestFixture.CONSUMER_INPUT_PIN;
import static org.eclipse.fordiac.ide.typemanagement.tests.ConnectionRepairTestFixture.CONSUMER_INSTANCE;
import static org.eclipse.fordiac.ide.typemanagement.tests.ConnectionRepairTestFixture.PRODUCER_INSTANCE;
import static org.eclipse.fordiac.ide.typemanagement.tests.ConnectionRepairTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.ConnectionRepairTestFixture.PROJECT_PATH;
import static org.eclipse.fordiac.ide.typemanagement.tests.ConnectionRepairTestFixture.REPAIR_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.ConnectionRepairTestFixture.REPAIR_STRUCT_MEMBER;
import static org.eclipse.fordiac.ide.typemanagement.tests.ConnectionRepairTestFixture.STRUCT_DEMUX_TYPE;
import static org.eclipse.fordiac.ide.typemanagement.tests.ConnectionRepairTestFixture.SYSTEM_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CONVERT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CORE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.IEC_61131_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.refactoring.connection.commands.RepairBrokenConnectionCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Repairs a struct-member connection left broken after the struct dropped the
 * member, driving RepairBrokenConnectionCommand the way the Repair Broken
 * Connection handler does but without the wizard or command stack.
 */
class ConnectionRepairTest {

	private IProject project;
	private TypeLibrary typeLibrary;

	@BeforeAll
	static void preloadSystemManager() {
		SystemManager.INSTANCE.name();
	}

	@BeforeEach
	void loadFixture() throws Exception {
		project = RefactoringTestSupport.importProjectIntoWorkspace(PROJECT_NAME, PROJECT_PATH);
		RefactoringTestSupport.linkStandardLibraries(project, CORE, CONVERT, IEC_61131_3);
		typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
	}

	@AfterEach
	void disposeFixture() throws Exception {
		RefactoringTestSupport.deleteProject(project);
	}

	@Test
	void repairBrokenConnection_removesTheErrorMarker() throws Exception {
		assertEquals(1, producerErrorMarkers().size());

		repairBrokenConnection();

		assertTrue(producerErrorMarkers().isEmpty());
		assertFalse(typeLibrary.getTypeEntry(file(SYSTEM_FILE)).hasError());
	}

	@Test
	void repairBrokenConnection_reconnectsConsumerThroughInsertedStructDemux() throws Exception {
		assertTrue(structDemuxInstances().isEmpty());

		repairBrokenConnection();

		assertEquals(1, structDemuxInstances().size());
		final IInterfaceElement consumerSource = consumerInputSource();
		assertEquals(REPAIR_STRUCT_MEMBER, consumerSource.getName());
		assertEquals(STRUCT_DEMUX_TYPE, consumerSource.getBlockFBNetworkElement().getTypeName());
	}

	private void repairBrokenConnection() {
		final ErrorMarkerInterface marker = producerErrorMarkers().get(0);
		final Connection broken = marker.getOutputConnections().get(0);
		final StructuredType struct = typeLibrary.getDataTypeLibrary().getStructuredType(REPAIR_STRUCT);
		final RepairBrokenConnectionCommand command = new RepairBrokenConnectionCommand(broken, !marker.isIsInput(),
				struct, REPAIR_STRUCT_MEMBER);
		assertTrue(command.canExecute());
		command.execute();
	}

	private List<ErrorMarkerInterface> producerErrorMarkers() {
		return instance(PRODUCER_INSTANCE).getInterface().getErrorMarker();
	}

	private List<FBNetworkElement> structDemuxInstances() {
		return applicationNetworkElements().stream().filter(e -> STRUCT_DEMUX_TYPE.equals(e.getTypeName())).toList();
	}

	private IInterfaceElement consumerInputSource() {
		final VarDeclaration input = instance(CONSUMER_INSTANCE).getInterface().getInputVars().stream()
				.filter(v -> CONSUMER_INPUT_PIN.equals(v.getName())).findFirst().orElseThrow();
		return input.getInputConnections().get(0).getSource();
	}

	private BlockFBNetworkElement instance(final String instanceName) {
		final FBNetworkElement element = applicationNetworkElements().stream()
				.filter(e -> instanceName.equals(e.getName())).findFirst().orElseThrow();
		return (BlockFBNetworkElement) element;
	}

	private List<FBNetworkElement> applicationNetworkElements() {
		final AutomationSystem system = (AutomationSystem) typeLibrary.getTypeEntry(file(SYSTEM_FILE)).getType();
		return system.getApplicationNamed(APPLICATION_NAME).getFBNetwork().getNetworkElements();
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
