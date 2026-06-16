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

import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CORE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FBTypeDeleteNestedSubAppTest {

	private static final String PROJECT_NAME = "FBTypeDeleteNestedSubAppTest"; //$NON-NLS-1$
	private static final String PROJECT_PATH = "data/FBTypeDeleteNestedSubAppTest"; //$NON-NLS-1$

	private static final String FB_FILE = "Type Library/mypackage/MyBlock.fbt"; //$NON-NLS-1$
	private static final String SYSTEM_FILE = "FBTypeDeleteNestedSubAppTest.sys"; //$NON-NLS-1$

	private static final String MY_BLOCK_TYPE_NAME = "MyBlock"; //$NON-NLS-1$

	private static final String APPLICATION_NAME = "App"; //$NON-NLS-1$
	private static final String TOP_INSTANCE = "TopInstance"; //$NON-NLS-1$
	private static final String CONTAINER_SUBAPP = "Container"; //$NON-NLS-1$
	private static final String NESTED_INSTANCE = "NestedInstance"; //$NON-NLS-1$
	private static final String DEEP_CONTAINER_SUBAPP = "DeepContainer"; //$NON-NLS-1$
	private static final String DEEPLY_NESTED_INSTANCE = "DeeplyNestedInstance"; //$NON-NLS-1$

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
		assertTrue(file(FB_FILE).exists());

		deleteMyBlock();

		assertFalse(file(FB_FILE).exists());
	}

	@Test
	void deleteMyBlock_clearsItFromTypeLibrary() throws Exception {
		assertNotNull(typeLibrary.getFBTypeEntry(MY_BLOCK_TYPE_NAME));

		deleteMyBlock();

		assertNull(typeLibrary.getFBTypeEntry(MY_BLOCK_TYPE_NAME));
	}

	@Test
	void deleteMyBlock_leavesAllNestedSubAppInstancesStructurallyIntact() throws Exception {
		findInstance(TOP_INSTANCE);
		findInstance(CONTAINER_SUBAPP, NESTED_INSTANCE);
		findInstance(CONTAINER_SUBAPP, DEEP_CONTAINER_SUBAPP, DEEPLY_NESTED_INSTANCE);

		deleteMyBlock();

		// DeleteTypeRefactoringParticipant only removes internal FBs whose
		// container is a BaseFBType, so System FB instances at every nesting
		// level remain in place; findInstance throws if any is missing.
		findInstance(TOP_INSTANCE);
		findInstance(CONTAINER_SUBAPP, NESTED_INSTANCE);
		findInstance(CONTAINER_SUBAPP, DEEP_CONTAINER_SUBAPP, DEEPLY_NESTED_INSTANCE);
	}

	private void deleteMyBlock() throws Exception {
		RefactoringTestSupport.performDelete(file(FB_FILE));
	}

	private FBNetworkElement findInstance(final String... namePath) {
		FBNetwork network = system().getApplicationNamed(APPLICATION_NAME).getFBNetwork();
		for (int i = 0; i < namePath.length - 1; i++) {
			final String subAppName = namePath[i];
			final UntypedSubApp subApp = (UntypedSubApp) network.getNetworkElements().stream()
					.filter(element -> subAppName.equals(element.getName())).findFirst().orElseThrow();
			network = subApp.getSubAppNetwork();
		}
		final String instanceName = namePath[namePath.length - 1];
		return network.getNetworkElements().stream().filter(element -> instanceName.equals(element.getName()))
				.findFirst().orElseThrow();
	}

	private AutomationSystem system() {
		return (AutomationSystem) typeLibrary.getTypeEntry(file(SYSTEM_FILE)).getType();
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
