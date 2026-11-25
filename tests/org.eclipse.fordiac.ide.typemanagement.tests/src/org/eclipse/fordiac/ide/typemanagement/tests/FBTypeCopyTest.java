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

import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CONVERT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CORE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.IEC_61131_3;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.APPLICATION_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PRODUCER_OUT_PIN;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PRODUCER_TYPE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_PATH;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.SYSTEM_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Copies the StructProducer FB type into another package and checks that the
 * copy is a new, independent type entry that leaves the original and its
 * instance untouched.
 */
class FBTypeCopyTest {

	private static final String TARGET_PACKAGE_FOLDER = "Type Library/otherpackage"; //$NON-NLS-1$
	private static final String PRODUCER_FILE = "Type Library/StructProducer.fbt"; //$NON-NLS-1$
	private static final String COPIED_PRODUCER_FILE = "Type Library/otherpackage/StructProducer.fbt"; //$NON-NLS-1$
	private static final String COPIED_PRODUCER_TYPE = "otherpackage::StructProducer"; //$NON-NLS-1$
	private static final String PRODUCER_INSTANCE = "Producer"; //$NON-NLS-1$

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
	void copyStructProducer_createsCopyFileAndKeepsOriginalFile() throws Exception {
		assertTrue(file(PRODUCER_FILE).exists());
		assertFalse(file(COPIED_PRODUCER_FILE).exists());

		copyProducerToTargetPackage();

		assertTrue(file(PRODUCER_FILE).exists());
		assertTrue(file(COPIED_PRODUCER_FILE).exists());
	}

	@Test
	void copyStructProducer_copyIsNewIndependentTypeEntry() throws Exception {
		assertEntryTypeName(file(PRODUCER_FILE), PRODUCER_TYPE);

		copyProducerToTargetPackage();

		// The copy is its own entry under the target package, and the original entry
		// keeps its name, so the two never alias the same type.
		assertEntryTypeName(file(COPIED_PRODUCER_FILE), COPIED_PRODUCER_TYPE);
		assertEntryTypeName(file(PRODUCER_FILE), PRODUCER_TYPE);
	}

	@Test
	void copyStructProducer_leavesProducerInstanceOnOriginalType() throws Exception {
		assertEquals(PRODUCER_TYPE, producerInstanceType());

		copyProducerToTargetPackage();

		// Unlike a move, a copy must not repoint the existing instance onto the copy.
		assertEquals(PRODUCER_TYPE, producerInstanceType());
	}

	@Test
	void copyStructProducer_copyKeepsStructInterfaceReference() throws Exception {
		copyProducerToTargetPackage();

		assertEquals(INNER_STRUCT, copyOutPinTypeName());
	}

	private void copyProducerToTargetPackage() throws Exception {
		final IFolder destination = project.getFolder(TARGET_PACKAGE_FOLDER);
		if (!destination.exists()) {
			destination.create(IResource.FORCE, true, new NullProgressMonitor());
		}
		RefactoringTestSupport.performCopy(new IResource[] { file(PRODUCER_FILE) }, destination);
	}

	private String copyOutPinTypeName() {
		final FBType copy = (FBType) typeLibrary.getTypeEntry(file(COPIED_PRODUCER_FILE)).getType();
		final VarDeclaration pin = copy.getInterfaceList().getOutputVars().stream()
				.filter(v -> PRODUCER_OUT_PIN.equals(v.getName())).findFirst().orElseThrow();
		return PackageNameHelper.getFullTypeName(pin.getType());
	}

	private String producerInstanceType() {
		final AutomationSystem system = (AutomationSystem) typeLibrary.getTypeEntry(file(SYSTEM_FILE)).getType();
		final FBNetworkElement producer = system.getApplicationNamed(APPLICATION_NAME).getFBNetwork()
				.getNetworkElements().stream().filter(e -> PRODUCER_INSTANCE.equals(e.getName())).findFirst()
				.orElseThrow();
		return producer.getFullTypeName();
	}

	private void assertEntryTypeName(final IFile typeFile, final String expectedFullTypeName) {
		final TypeEntry entry = typeLibrary.getTypeEntry(typeFile);
		assertTrue(typeFile.exists());
		assertFalse(entry.hasError());
		assertEquals(expectedFullTypeName, entry.getFullTypeName());
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
