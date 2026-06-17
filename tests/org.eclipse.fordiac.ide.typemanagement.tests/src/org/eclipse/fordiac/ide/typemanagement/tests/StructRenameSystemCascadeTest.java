/*******************************************************************************
 * Copyright (c) 2026
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

import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CONVERT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CORE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.IEC_61131_3;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.APPLICATION_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT_RENAMED;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_PATH;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.SYSTEM_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StructRenameSystemCascadeTest {

	private static final String NEW_INNER_FILE_NAME = "InnerStructRenamed.dtp"; //$NON-NLS-1$
	private static final String PRODUCER_TYPE = "StructProducer"; //$NON-NLS-1$
	private static final String PRODUCER_OUT_PIN = "OUT"; //$NON-NLS-1$
	private static final String DEMUX_INSTANCE = "Demux"; //$NON-NLS-1$
	private static final String MUX_INSTANCE = "Mux"; //$NON-NLS-1$
	private static final String FMOVE_INSTANCE = "Move"; //$NON-NLS-1$

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
	void renameInnerStruct_updatesStructProducerInterfacePinType() throws Exception {
		assertProducerOutputType(INNER_STRUCT);

		renameInnerStruct();

		assertProducerOutputType(INNER_STRUCT_RENAMED);
	}

	@Test
	void renameInnerStruct_updatesConfiguredStructDemuxInstance() throws Exception {
		assertConfigurableFBDataType(DEMUX_INSTANCE, Demultiplexer.class, INNER_STRUCT);

		renameInnerStruct();

		assertConfigurableFBDataType(DEMUX_INSTANCE, Demultiplexer.class, INNER_STRUCT_RENAMED);
	}

	@Test
	void renameInnerStruct_updatesConfiguredStructMuxInstance() throws Exception {
		assertConfigurableFBDataType(MUX_INSTANCE, Multiplexer.class, INNER_STRUCT);

		renameInnerStruct();

		assertConfigurableFBDataType(MUX_INSTANCE, Multiplexer.class, INNER_STRUCT_RENAMED);
	}

	@Test
	void renameInnerStruct_updatesConfiguredFMoveInstance() throws Exception {
		assertConfigurableFBDataType(FMOVE_INSTANCE, ConfigurableMoveFB.class, INNER_STRUCT);

		renameInnerStruct();

		assertConfigurableFBDataType(FMOVE_INSTANCE, ConfigurableMoveFB.class, INNER_STRUCT_RENAMED);
	}

	private void renameInnerStruct() throws Exception {
		RefactoringTestSupport.performRename(file(INNER_STRUCT_FILE), NEW_INNER_FILE_NAME);
	}

	private void assertProducerOutputType(final String expectedQualifiedType) {
		final FBType producer = (FBType) typeLibrary.getFBTypeEntry(PRODUCER_TYPE).getType();
		final VarDeclaration outPin = producer.getInterfaceList().getOutputVars().stream()
				.filter(v -> PRODUCER_OUT_PIN.equals(v.getName())).findFirst().orElseThrow();
		assertEquals(expectedQualifiedType, PackageNameHelper.getFullTypeName(outPin.getType()));
	}

	private <T extends ConfigurableFB> void assertConfigurableFBDataType(final String instanceName,
			final Class<T> instanceClass, final String expectedQualifiedType) {
		final FBNetworkElement element = system().getApplicationNamed(APPLICATION_NAME).getFBNetwork()
				.getNetworkElements().stream().filter(e -> instanceName.equals(e.getName())).findFirst()
				.orElseThrow();
		final T configurable = assertInstanceOf(instanceClass, element);
		assertEquals(expectedQualifiedType, PackageNameHelper.getFullTypeName(configurable.getDataType()));
	}

	private AutomationSystem system() {
		return (AutomationSystem) typeLibrary.getTypeEntry(file(SYSTEM_FILE)).getType();
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
