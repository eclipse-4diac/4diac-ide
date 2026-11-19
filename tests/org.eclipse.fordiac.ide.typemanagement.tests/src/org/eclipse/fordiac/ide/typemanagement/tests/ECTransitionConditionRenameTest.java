/*******************************************************************************
 * Copyright (c) 2026 Martin Erich Jobst and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("nls")
class ECTransitionConditionRenameTest {

	private static final String PROJECT_NAME = "ECConditionRenameTest";
	private static final String PROJECT_PATH = "data/ECConditionRenameTest";
	private static final String FB_FILE = "Type Library/ConditionRename.fbt";

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
		typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
		project.build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
	}

	@AfterEach
	void disposeFixture() throws Exception {
		RefactoringTestSupport.deleteProject(project);
	}

	@Test
	void renameVariableUpdatesConditionsAndSupportsUndoRedo() throws Exception {
		assertConditions("Available AND Enabled AND Other", "NOT enabled", "Data.Flag AND Other", "", "Missing",
				"conditionrename::constants::ConditionConstants::Enabled");
		RefactoringTestSupport.performElementRename(EcoreUtil.getURI(fbType().getInterfaceList().getInputVars().get(0)),
				"Ready");
		assertConditions("Available AND Ready AND Other", "NOT Ready", "Data.Flag AND Other", "", "Missing",
				"conditionrename::constants::ConditionConstants::Enabled");
		RefactoringTestSupport.undoLastRefactoring();
		assertConditions("Available AND Enabled AND Other", "NOT enabled", "Data.Flag AND Other", "", "Missing",
				"conditionrename::constants::ConditionConstants::Enabled");
		RefactoringTestSupport.redoLastRefactoring();
		assertConditions("Available AND Ready AND Other", "NOT Ready", "Data.Flag AND Other", "", "Missing",
				"conditionrename::constants::ConditionConstants::Enabled");
	}

	@Test
	void renameStructuredMemberUpdatesQualifiedCondition() throws Exception {
		final var member = typeLibrary.getDataTypeLibrary().getStructuredType("ConditionData").getMemberVariables()
				.get(0);
		RefactoringTestSupport.performElementRename(EcoreUtil.getURI(member), "Active");
		assertConditions("Available AND Enabled AND Other", "NOT enabled", "Data.Active AND Other", "", "Missing",
				"conditionrename::constants::ConditionConstants::Enabled");
	}

	private void assertConditions(final String... expected) {
		final List<ECTransition> transitions = fbType().getECC().getECTransition();
		assertEquals(expected.length, transitions.size());
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], transitions.get(i).getConditionExpression());
			assertNotNull(transitions.get(i).getConditionEvent());
			assertEquals("EI", transitions.get(i).getConditionEvent().getName());
		}
	}

	private BasicFBType fbType() {
		final IFile file = project.getFile(FB_FILE);
		final TypeEntry entry = typeLibrary.getTypeEntry(file);
		assertNotNull(entry);
		return assertInstanceOf(BasicFBType.class, entry.getType());
	}
}
