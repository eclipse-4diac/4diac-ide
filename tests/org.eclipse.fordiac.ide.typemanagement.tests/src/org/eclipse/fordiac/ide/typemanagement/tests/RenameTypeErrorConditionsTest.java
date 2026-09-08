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
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.refactoring.rename.RenameElementRefactoringProcessor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RenameTypeErrorConditionsTest {

	private static final String EXISTING_TYPE_FILE_NAME = "OuterStruct.dtp"; //$NON-NLS-1$
	private static final String FILE_EXISTS_MESSAGE = "File already exists!"; //$NON-NLS-1$
	private static final String RESERVED_NAME = "ADD"; //$NON-NLS-1$
	private static final String RESERVED_TYPE_FILE_NAME = "ADD.dtp"; //$NON-NLS-1$

	private IProject project;

	@BeforeAll
	static void preloadSystemManager() {
		SystemManager.INSTANCE.name();
	}

	@BeforeEach
	void loadFixture() throws Exception {
		project = RefactoringTestSupport.importProjectIntoWorkspace(PROJECT_NAME, PROJECT_PATH);
		RefactoringTestSupport.linkStandardLibraries(project, CORE, CONVERT, IEC_61131_3);
	}

	@AfterEach
	void disposeFixture() throws Exception {
		RefactoringTestSupport.deleteProject(project);
	}

	@Test
	void renameToExistingFileName_yieldsFatalStatus() throws Exception {
		final RefactoringStatus status = RefactoringTestSupport.checkRenameConditions(file(INNER_STRUCT_FILE),
				EXISTING_TYPE_FILE_NAME);

		assertTrue(status.hasFatalError());
		assertTrue(Stream.of(status.getEntries()).anyMatch(entry -> FILE_EXISTS_MESSAGE.equals(entry.getMessage())));
	}

	@Test
	void renameToReservedKeyword_processorFlagsFatalStatus() throws Exception {
		final RenameElementRefactoringProcessor processor = new RenameElementRefactoringProcessor(
				URI.createPlatformResourceURI(file(INNER_STRUCT_FILE).getFullPath().toString(), true), RESERVED_NAME);

		final RefactoringStatus status = processor.checkInitialConditions(new NullProgressMonitor());

		assertTrue(status.hasFatalError());
		assertEquals(IdentifierVerifier.verifyIdentifier(RESERVED_NAME).orElseThrow(),
				status.getEntryWithHighestSeverity().getMessage());
	}

	@Test
	void renameFileToReservedKeyword_isNotFlaggedByFileRename() throws Exception {
		// The file rename path checks only the file ending and name collisions, so a
		// reserved keyword is rejected through the model rename processor, not here.
		final RefactoringStatus status = RefactoringTestSupport.checkRenameConditions(file(INNER_STRUCT_FILE),
				RESERVED_TYPE_FILE_NAME);

		assertFalse(status.hasFatalError());
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
