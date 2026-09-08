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
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.DEEP_CONTAINER_SUBAPP;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.MY_BLOCK;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.MY_BLOCK_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.NESTED_INSTANCE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.PROJECT_PATH;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.SYSTEM_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedSubAppTestFixture.TOP_INSTANCE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CORE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FBTypeRenameNestedSubAppTest {

	private static final String RENAMED_FB_FILE = "Type Library/mypackage/MyBlockRenamed.fbt"; //$NON-NLS-1$
	private static final String NEW_FB_FILE_NAME = "MyBlockRenamed.fbt"; //$NON-NLS-1$
	private static final String MY_BLOCK_RENAMED = "mypackage::MyBlockRenamed"; //$NON-NLS-1$

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
		RefactoringTestSupport.linkStandardLibraries(project, CORE);
		typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
	}

	@AfterEach
	void disposeFixture() throws Exception {
		RefactoringTestSupport.deleteProject(project);
	}

	@Test
	void renameMyBlock_repointsInstancesAtEveryNestingLevel() throws Exception {
		assertInstanceTypeNamesAtEveryNestingLevel(MY_BLOCK);

		renameMyBlock();

		assertInstanceTypeNamesAtEveryNestingLevel(MY_BLOCK_RENAMED);
	}

	@Test
	void renameMyBlock_undoRedoRoundTrip() throws Exception {
		renameMyBlock();
		assertRenamedState();

		RefactoringTestSupport.undoLastRefactoring();
		assertOriginalState();

		RefactoringTestSupport.redoLastRefactoring();
		assertRenamedState();
	}

	private void assertRenamedState() {
		assertFalse(file(MY_BLOCK_FILE).exists());
		assertTrue(file(RENAMED_FB_FILE).exists());
		assertInstanceTypeNamesAtEveryNestingLevel(MY_BLOCK_RENAMED);
	}

	private void assertOriginalState() {
		assertTrue(file(MY_BLOCK_FILE).exists());
		assertFalse(file(RENAMED_FB_FILE).exists());
		assertInstanceTypeNamesAtEveryNestingLevel(MY_BLOCK);
	}

	private void assertInstanceTypeNamesAtEveryNestingLevel(final String expectedFullTypeName) {
		assertEquals(expectedFullTypeName, findInstance(TOP_INSTANCE).getFullTypeName());
		assertEquals(expectedFullTypeName, findInstance(CONTAINER_SUBAPP, NESTED_INSTANCE).getFullTypeName());
		assertEquals(expectedFullTypeName,
				findInstance(CONTAINER_SUBAPP, DEEP_CONTAINER_SUBAPP, DEEPLY_NESTED_INSTANCE).getFullTypeName());
	}

	private void renameMyBlock() throws Exception {
		RefactoringTestSupport.performRename(file(MY_BLOCK_FILE), NEW_FB_FILE_NAME);
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
