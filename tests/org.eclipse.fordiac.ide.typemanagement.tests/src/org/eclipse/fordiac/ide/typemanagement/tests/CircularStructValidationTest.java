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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Documents that a circular struct member reference is not validated: CycleA
 * references CycleB and CycleB references CycleA, yet both load without error.
 */
class CircularStructValidationTest {

	private static final String PROJECT_NAME = "CircularStructTest"; //$NON-NLS-1$
	private static final String PROJECT_PATH = "data/CircularStructTest"; //$NON-NLS-1$
	private static final String CYCLE_A_FILE = "Type Library/mypackage/CycleA.dtp"; //$NON-NLS-1$
	private static final String CYCLE_B_FILE = "Type Library/mypackage/CycleB.dtp"; //$NON-NLS-1$

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
	@Disabled("The datatype editor does not validate circular struct member references: CycleA references " //$NON-NLS-1$
			+ "CycleB and CycleB references CycleA, yet both load without an error. Re-enable once circular " //$NON-NLS-1$
			+ "references are reported as errors, see https://github.com/eclipse-4diac/4diac-ide/issues/2792") //$NON-NLS-1$
	void circularStructReference_isReportedAsError() throws Exception {
		assertTrue(typeLibrary.getTypeEntry(file(CYCLE_A_FILE)).hasError());
		assertTrue(typeLibrary.getTypeEntry(file(CYCLE_B_FILE)).hasError());
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
