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
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT_RENAMED;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.OUTER_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StructDataTypeRenameTest {

	private static final String RENAMED_FILE = "Type Library/mypackage/InnerStructRenamed.dtp"; //$NON-NLS-1$
	private static final String NEW_FILE_NAME = "InnerStructRenamed.dtp"; //$NON-NLS-1$
	private static final String OLD_NAME = "InnerStruct"; //$NON-NLS-1$
	private static final String NEW_NAME = "InnerStructRenamed"; //$NON-NLS-1$

	private IProject project;
	private TypeLibrary typeLibrary;

	@BeforeAll
	static void preloadSystemManager() {
		// Touch the SystemManager singleton so its resource change listener is
		// active before any fixture project is imported.
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
	void renameInnerStruct_renamesTheDtpFile() throws Exception {
		assertTrue(file(INNER_STRUCT_FILE).exists());
		assertFalse(file(RENAMED_FILE).exists());

		renameInnerStruct();

		assertFalse(file(INNER_STRUCT_FILE).exists());
		assertTrue(file(RENAMED_FILE).exists());
	}

	@Test
	void renameInnerStruct_updatesDataTypeNameInLibrary() throws Exception {
		assertEquals(OLD_NAME, structuredType(INNER_STRUCT).getName());
		assertTypeEntryFullTypeNameEqual(file(INNER_STRUCT_FILE), INNER_STRUCT);

		renameInnerStruct();

		assertEquals(NEW_NAME, structuredType(INNER_STRUCT_RENAMED).getName());
		assertTypeEntryFullTypeNameEqual(file(RENAMED_FILE), INNER_STRUCT_RENAMED);
	}

	@Test
	void renameInnerStruct_updatesOuterStructMemberReference() throws Exception {
		assertOuterMember(OLD_NAME, INNER_STRUCT);
		assertTypeEntryFullTypeNameEqual(file(INNER_STRUCT_FILE), INNER_STRUCT);

		renameInnerStruct();

		assertOuterMember(NEW_NAME, INNER_STRUCT_RENAMED);
		assertTypeEntryFullTypeNameEqual(file(RENAMED_FILE), INNER_STRUCT_RENAMED);
	}

	@Test
	void renameInnerStruct_undoRedoRoundTrip() throws Exception {
		renameInnerStruct();
		assertRenamedState();

		RefactoringTestSupport.undoLastRefactoring();
		assertOriginalState();

		RefactoringTestSupport.redoLastRefactoring();
		assertRenamedState();
	}

	private void assertRenamedState() {
		assertFalse(file(INNER_STRUCT_FILE).exists());
		assertTrue(file(RENAMED_FILE).exists());
		assertOuterMember(NEW_NAME, INNER_STRUCT_RENAMED);
		// Pin the .dtp content: the entry must resolve without error, so undo and
		// redo never leave the stale internal name fixed in #2514.
		assertTypeEntryFullTypeNameEqual(file(RENAMED_FILE), INNER_STRUCT_RENAMED);
	}

	private void assertOriginalState() {
		assertTrue(file(INNER_STRUCT_FILE).exists());
		assertFalse(file(RENAMED_FILE).exists());
		assertOuterMember(OLD_NAME, INNER_STRUCT);
		assertTypeEntryFullTypeNameEqual(file(INNER_STRUCT_FILE), INNER_STRUCT);
	}

	private void renameInnerStruct() throws Exception {
		RefactoringTestSupport.performRename(file(INNER_STRUCT_FILE), NEW_FILE_NAME);
	}

	private void assertOuterMember(final String expectedName, final String expectedQualifiedType) {
		final VarDeclaration inner = structuredType(OUTER_STRUCT).getMemberVariables().get(0);
		assertEquals(expectedName, inner.getTypeName());
		// Comparing the resolved type by identity is fragile because the library
		// can be reloaded during the refactoring; compare full-qualified names.
		assertEquals(expectedQualifiedType, PackageNameHelper.getFullTypeName(inner.getType()));
	}

	private void assertTypeEntryFullTypeNameEqual(final IFile typeFile, final String expectedFullTypeName) {
		final TypeEntry entry = typeLibrary.getTypeEntry(typeFile);
		assertNotNull(entry);
		assertFalse(entry.hasError());
		assertEquals(expectedFullTypeName, entry.getFullTypeName());
	}

	private StructuredType structuredType(final String qualifiedName) {
		return typeLibrary.getDataTypeLibrary().getStructuredType(qualifiedName);
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
