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
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FBTypeMoveTest {

	private static final String TARGET_PACKAGE_FOLDER = "Type Library/otherpackage"; //$NON-NLS-1$
	private static final String PRODUCER_FILE = "Type Library/StructProducer.fbt"; //$NON-NLS-1$
	private static final String MOVED_PRODUCER_FILE = "Type Library/otherpackage/StructProducer.fbt"; //$NON-NLS-1$
	private static final String MOVED_PRODUCER_TYPE = "otherpackage::StructProducer"; //$NON-NLS-1$
	private static final String PRODUCER_INSTANCE = "Producer"; //$NON-NLS-1$

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
	void moveStructProducer_movesTheFbtFileToTargetPackage() throws Exception {
		assertTrue(file(PRODUCER_FILE).exists());
		assertFalse(file(MOVED_PRODUCER_FILE).exists());

		moveProducerToTargetPackage();

		assertFalse(file(PRODUCER_FILE).exists());
		assertTrue(file(MOVED_PRODUCER_FILE).exists());
	}

	@Test
	void moveStructProducer_updatesProducerInstanceType() throws Exception {
		assertEquals(PRODUCER_TYPE, producerInstanceType());

		moveProducerToTargetPackage();

		assertEquals(MOVED_PRODUCER_TYPE, producerInstanceType());
	}

	@Test
	void moveStructProducer_undoRedoRoundTrip() throws Exception {
		moveProducerToTargetPackage();
		assertMovedState();

		RefactoringTestSupport.undoLastRefactoring();
		assertOriginalState();

		RefactoringTestSupport.redoLastRefactoring();
		assertMovedState();
	}

	private void assertMovedState() {
		assertFalse(file(PRODUCER_FILE).exists());
		assertTrue(file(MOVED_PRODUCER_FILE).exists());
		assertEquals(MOVED_PRODUCER_TYPE, producerInstanceType());
	}

	private void assertOriginalState() {
		assertTrue(file(PRODUCER_FILE).exists());
		assertFalse(file(MOVED_PRODUCER_FILE).exists());
		assertEquals(PRODUCER_TYPE, producerInstanceType());
	}

	private void moveProducerToTargetPackage() throws Exception {
		final IFolder destination = project.getFolder(TARGET_PACKAGE_FOLDER);
		if (!destination.exists()) {
			destination.create(IResource.FORCE, true, new NullProgressMonitor());
		}
		RefactoringTestSupport.performMove(file(PRODUCER_FILE), destination);
	}

	// StructProducer is a test FB type in the fixture with one output OUT typed
	// mypackage::InnerStruct, so a struct is used in an FB interface. Producer is its
	// instance in the application, so moving the type has to repoint the instance.
	private String producerInstanceType() {
		final AutomationSystem system = (AutomationSystem) typeLibrary.getTypeEntry(file(SYSTEM_FILE)).getType();
		final FBNetworkElement producer = system.getApplicationNamed(APPLICATION_NAME).getFBNetwork()
				.getNetworkElements().stream().filter(e -> PRODUCER_INSTANCE.equals(e.getName())).findFirst()
				.orElseThrow();
		return producer.getFullTypeName();
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
