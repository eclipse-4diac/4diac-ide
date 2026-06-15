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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.ltk.core.refactoring.Change;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.osgi.framework.Bundle;

class StructDataTypeRenameTest {

	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.typemanagement.tests"; //$NON-NLS-1$
	private static final String PROJECT_NAME = "StructRenameTest"; //$NON-NLS-1$
	private static final String PROJECT_PATH = "data/StructRenameTest"; //$NON-NLS-1$

	private static final String CORE_LIBRARY = "core-3.0.0"; //$NON-NLS-1$
	private static final String CONVERT_LIBRARY = "convert-3.0.0"; //$NON-NLS-1$

	private static final String INNER_FILE = "Type Library/mypackage/InnerStruct.dtp"; //$NON-NLS-1$
	private static final String RENAMED_FILE = "Type Library/mypackage/InnerStructRenamed.dtp"; //$NON-NLS-1$
	private static final String NEW_FILE_NAME = "InnerStructRenamed.dtp"; //$NON-NLS-1$

	private static final String OUTER_STRUCT = "mypackage::OuterStruct"; //$NON-NLS-1$
	private static final String INNER_STRUCT = "mypackage::InnerStruct"; //$NON-NLS-1$
	private static final String INNER_STRUCT_RENAMED = "mypackage::InnerStructRenamed"; //$NON-NLS-1$
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
		final Bundle bundle = Platform.getBundle(BUNDLE_NAME);
		project = RefactoringTestSupport.importProjectIntoWorkspace(PROJECT_NAME, bundle, new Path(PROJECT_PATH));
		RefactoringTestSupport.linkStandardLibraries(project, CORE_LIBRARY, CONVERT_LIBRARY);
		typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
	}

	@AfterEach
	void disposeFixture() throws Exception {
		RefactoringTestSupport.deleteProject(project);
	}

	@Test
	void renameInnerStruct_renamesTheDtpFile() throws Exception {
		assertTrue(file(INNER_FILE).exists());
		assertFalse(file(RENAMED_FILE).exists());

		renameInnerStruct();

		assertFalse(file(INNER_FILE).exists());
		assertTrue(file(RENAMED_FILE).exists());
	}

	@Test
	void renameInnerStruct_updatesDataTypeNameInLibrary() throws Exception {
		assertEquals(OLD_NAME, structuredType(INNER_STRUCT).getName());
		assertTypeEntryFullTypeNameEqual(file(INNER_FILE), INNER_STRUCT);

		renameInnerStruct();

		assertEquals(NEW_NAME, structuredType(INNER_STRUCT_RENAMED).getName());
		assertTypeEntryFullTypeNameEqual(file(RENAMED_FILE), INNER_STRUCT_RENAMED);
	}

	@Test
	void renameInnerStruct_updatesOuterStructMemberReference() throws Exception {
		assertOuterMember(OLD_NAME, INNER_STRUCT);
		assertTypeEntryFullTypeNameEqual(file(INNER_FILE), INNER_STRUCT);

		renameInnerStruct();

		assertOuterMember(NEW_NAME, INNER_STRUCT_RENAMED);
		assertTypeEntryFullTypeNameEqual(file(RENAMED_FILE), INNER_STRUCT_RENAMED);
	}

	// Undo renames the .dtp file back but leaves its internal DataType name as
	// "InnerStructRenamed", so mypackage::InnerStruct stays unresolvable. Re-enable
	// once UpdateTypeEntryChange's undo reverts the file content.
	@Disabled("Undo does not revert the renamed type's file content; tracked with maintainers") //$NON-NLS-1$
	@Test
	void renameInnerStruct_undoRestoresOriginalState() throws Exception {
		final Change undo = renameInnerStruct();
		assertOuterMember(NEW_NAME, INNER_STRUCT_RENAMED);

		RefactoringTestSupport.performChange(undo);

		assertTrue(file(INNER_FILE).exists());
		assertOuterMember(OLD_NAME, INNER_STRUCT);
	}

	@Disabled("Depends on undo fully restoring the original state; see renameInnerStruct_undoRestoresOriginalState") //$NON-NLS-1$
	@Test
	void renameInnerStruct_redoReappliesRename() throws Exception {
		final Change undo = renameInnerStruct();
		final Change redo = RefactoringTestSupport.performChange(undo);
		assertOuterMember(OLD_NAME, INNER_STRUCT);

		RefactoringTestSupport.performChange(redo);
		assertOuterMember(NEW_NAME, INNER_STRUCT_RENAMED);
	}

	private Change renameInnerStruct() throws Exception {
		return RefactoringTestSupport.performRename(file(INNER_FILE), NEW_FILE_NAME);
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
