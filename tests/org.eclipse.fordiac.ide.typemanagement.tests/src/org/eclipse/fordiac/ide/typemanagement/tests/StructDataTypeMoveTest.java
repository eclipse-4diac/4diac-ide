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
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.OUTER_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PRODUCER_OUT_PIN;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PRODUCER_TYPE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StructDataTypeMoveTest {

	private static final String TARGET_PACKAGE_FOLDER = "Type Library/otherpackage"; //$NON-NLS-1$
	private static final String MOVED_INNER_FILE = "Type Library/otherpackage/InnerStruct.dtp"; //$NON-NLS-1$
	private static final String MOVED_INNER_STRUCT = "otherpackage::InnerStruct"; //$NON-NLS-1$

	private IProject project;
	private TypeLibrary typeLibrary;

	@BeforeAll
	static void preloadSystemManager() {
		SystemManager.INSTANCE.name();
	}

	@BeforeEach
	void loadFixture() throws Exception {
		RefactoringTestSupport.flushUndoHistory();
		project = RefactoringTestSupport.importProjectIntoWorkspace(PROJECT_NAME, PROJECT_PATH);
		RefactoringTestSupport.linkStandardLibraries(project, CORE, CONVERT, IEC_61131_3);
		typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
	}

	@AfterEach
	void disposeFixture() throws Exception {
		RefactoringTestSupport.deleteProject(project);
	}

	@Test
	void moveInnerStruct_movesTheDtpFileToTargetPackage() throws Exception {
		assertTrue(file(INNER_STRUCT_FILE).exists());
		assertFalse(file(MOVED_INNER_FILE).exists());

		moveInnerStructToTargetPackage();

		assertFalse(file(INNER_STRUCT_FILE).exists());
		assertTrue(file(MOVED_INNER_FILE).exists());
	}

	@Test
	void moveInnerStruct_updatesOuterStructMemberReference() throws Exception {
		assertEquals(INNER_STRUCT, outerMemberType());

		moveInnerStructToTargetPackage();

		assertEquals(MOVED_INNER_STRUCT, outerMemberType());
	}

	@Test
	void moveInnerStruct_updatesStructProducerInterfacePinType() throws Exception {
		assertEquals(INNER_STRUCT, structProducerOutPinType());

		moveInnerStructToTargetPackage();

		assertEquals(MOVED_INNER_STRUCT, structProducerOutPinType());
	}

	@Test
	void moveInnerStruct_undoRedoRoundTrip() throws Exception {
		moveInnerStructToTargetPackage();
		assertMovedState();

		RefactoringTestSupport.undoLastRefactoring();
		assertOriginalState();

		RefactoringTestSupport.redoLastRefactoring();
		assertMovedState();
	}

	private void assertMovedState() {
		assertFalse(file(INNER_STRUCT_FILE).exists());
		assertTrue(file(MOVED_INNER_FILE).exists());
		assertEquals(MOVED_INNER_STRUCT, outerMemberType());
	}

	private void assertOriginalState() {
		assertTrue(file(INNER_STRUCT_FILE).exists());
		assertFalse(file(MOVED_INNER_FILE).exists());
		assertEquals(INNER_STRUCT, outerMemberType());
	}

	private void moveInnerStructToTargetPackage() throws Exception {
		final IFolder destination = project.getFolder(TARGET_PACKAGE_FOLDER);
		if (!destination.exists()) {
			destination.create(IResource.FORCE, true, new NullProgressMonitor());
		}
		RefactoringTestSupport.performMove(file(INNER_STRUCT_FILE), destination);
	}

	private String outerMemberType() {
		return PackageNameHelper.getFullTypeName(
				typeLibrary.getDataTypeLibrary().getStructuredType(OUTER_STRUCT).getMemberVariables().get(0).getType());
	}

	// StructProducer.OUT is an interface pin typed InnerStruct, so moving InnerStruct repoints it.
	private String structProducerOutPinType() {
		final FBType structProducer = (FBType) typeLibrary.getFBTypeEntry(PRODUCER_TYPE).getType();
		final VarDeclaration outPin = structProducer.getInterfaceList().getOutputVars().stream()
				.filter(v -> PRODUCER_OUT_PIN.equals(v.getName())).findFirst().orElseThrow();
		return PackageNameHelper.getFullTypeName(outPin.getType());
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
