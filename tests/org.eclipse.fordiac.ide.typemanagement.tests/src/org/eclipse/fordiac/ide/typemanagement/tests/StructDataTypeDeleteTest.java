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
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.data.ErrorDataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StructDataTypeDeleteTest {

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
	void deleteInnerStruct_removesItFromWorkspace() throws Exception {
		assertTrue(file(INNER_STRUCT_FILE).exists());

		deleteInnerStruct();

		assertFalse(file(INNER_STRUCT_FILE).exists());
	}

	@Test
	void deleteInnerStruct_clearsItFromTypeLibrary() throws Exception {
		assertEquals(INNER_STRUCT, structuredType(INNER_STRUCT).getTypeEntry().getFullTypeName());

		deleteInnerStruct();

		// getStructuredType returns ANY_STRUCT for unknown names instead of null.
		assertSame(GenericTypes.ANY_STRUCT, structuredType(INNER_STRUCT));
	}

	@Test
	void deleteInnerStruct_breaksOuterStructMemberTypeResolution() throws Exception {
		assertEquals(1, structuredType(OUTER_STRUCT).getMemberVariables().size());

		deleteInnerStruct();

		// The member stays but its type can no longer resolve, so EMF returns
		// an ErrorDataType placeholder.
		assertEquals(1, structuredType(OUTER_STRUCT).getMemberVariables().size());
		assertInstanceOf(ErrorDataType.class,
				structuredType(OUTER_STRUCT).getMemberVariables().get(0).getType());
	}

	@Test
	void deleteInnerStruct_undoRedoRoundTrip() throws Exception {
		deleteInnerStruct();
		assertFalse(file(INNER_STRUCT_FILE).exists());

		RefactoringTestSupport.undoLastRefactoring();
		assertTrue(file(INNER_STRUCT_FILE).exists());
		assertEquals(INNER_STRUCT, structuredType(INNER_STRUCT).getTypeEntry().getFullTypeName());

		RefactoringTestSupport.redoLastRefactoring();
		assertFalse(file(INNER_STRUCT_FILE).exists());
	}

	private void deleteInnerStruct() throws Exception {
		RefactoringTestSupport.performDelete(file(INNER_STRUCT_FILE));
	}

	private StructuredType structuredType(final String qualifiedName) {
		return typeLibrary.getDataTypeLibrary().getStructuredType(qualifiedName);
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
