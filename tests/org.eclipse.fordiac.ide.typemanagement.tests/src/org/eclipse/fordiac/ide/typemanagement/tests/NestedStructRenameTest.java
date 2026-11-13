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

import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.APPLICATION_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.LEAF_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.LEAF_STRUCT_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.LEAF_STRUCT_RENAMED;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.MIDDLE_LEAF_MEMBER;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.MIDDLE_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.MIDDLE_STRUCT_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.PRODUCER_INSTANCE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.PRODUCER_OUT_PIN;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.PROJECT_PATH;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.ROOT_MIDDLE_MEMBER;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.ROOT_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.ROOT_STRUCT_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.NestedStructRenameTestFixture.SYSTEM_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CONVERT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CORE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.IEC_61131_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Renames the innermost struct of a Root -> Middle -> Leaf hierarchy and checks
 * that the change reaches the referencing struct two levels up and the connected
 * Root-typed instance below it.
 */
class NestedStructRenameTest {

	private static final String NEW_LEAF_FILE_NAME = "LeafRenamed.dtp"; //$NON-NLS-1$
	private static final String RENAMED_LEAF_FILE = "Type Library/mypackage/LeafRenamed.dtp"; //$NON-NLS-1$

	private static final String LEAF_MEMBER = "A"; //$NON-NLS-1$
	private static final String LEAF_MEMBER_RENAMED = "ARenamed"; //$NON-NLS-1$
	private static final String CUSTOM_ATTRIBUTE = "TestAttribute"; //$NON-NLS-1$
	private static final String CUSTOM_ATTRIBUTE_VALUE = "42"; //$NON-NLS-1$

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
	void renameLeafStruct_updatesMiddleMemberReference() throws Exception {
		assertEquals(LEAF_STRUCT, memberTypeName(MIDDLE_STRUCT, MIDDLE_LEAF_MEMBER));
		assertEntryTypeName(file(LEAF_STRUCT_FILE), LEAF_STRUCT);
		assertEntryTypeName(file(MIDDLE_STRUCT_FILE), MIDDLE_STRUCT);

		renameLeafStruct();

		assertEquals(LEAF_STRUCT_RENAMED, memberTypeName(MIDDLE_STRUCT, MIDDLE_LEAF_MEMBER));
		assertEntryTypeName(file(RENAMED_LEAF_FILE), LEAF_STRUCT_RENAMED);
		assertEntryTypeName(file(MIDDLE_STRUCT_FILE), MIDDLE_STRUCT);
	}

	@Test
	void renameLeafStruct_keepsRootTypedInstanceResolvable() throws Exception {
		// Root's name does not change, but its transitive Leaf reference does; the
		// instance pin stays typed Root and the Root entry keeps resolving through it.
		assertEquals(MIDDLE_STRUCT, memberTypeName(ROOT_STRUCT, ROOT_MIDDLE_MEMBER));
		assertEquals(ROOT_STRUCT, producerOutPinTypeName());
		assertEntryTypeName(file(ROOT_STRUCT_FILE), ROOT_STRUCT);

		renameLeafStruct();

		assertEquals(ROOT_STRUCT, producerOutPinTypeName());
		assertEntryTypeName(file(ROOT_STRUCT_FILE), ROOT_STRUCT);
		assertFalse(typeLibrary.getTypeEntry(file(SYSTEM_FILE)).hasError());
	}

	@Test
	@Disabled("Renaming a struct member is not propagated to a nested (multi-level) expanded instance pin: the " //$NON-NLS-1$
			+ "deep pin keeps the old member name, becomes an unresolved (ANY) error-marker pin, and its custom " //$NON-NLS-1$
			+ "attribute is lost. One-level expanded pins are preserved since #2651; re-enable when nested " //$NON-NLS-1$
			+ "expanded pins follow the member rename too, see " //$NON-NLS-1$
			+ "https://github.com/eclipse-4diac/4diac-ide/issues/2646") //$NON-NLS-1$
	void renameLeafStructMember_preservesDeeplyExpandedPinAttribute() throws Exception {
		assertEquals(CUSTOM_ATTRIBUTE_VALUE, expandedLeafMemberAttribute(LEAF_MEMBER));

		RefactoringTestSupport.performElementRename(leafMemberURI(LEAF_MEMBER), LEAF_MEMBER_RENAMED);

		assertEquals(CUSTOM_ATTRIBUTE_VALUE, expandedLeafMemberAttribute(LEAF_MEMBER_RENAMED));
	}

	private void renameLeafStruct() throws Exception {
		RefactoringTestSupport.performRename(file(LEAF_STRUCT_FILE), NEW_LEAF_FILE_NAME);
	}

	private String memberTypeName(final String structName, final String memberName) {
		return PackageNameHelper.getFullTypeName(structMember(structName, memberName).getType());
	}

	private String producerOutPinTypeName() {
		final VarDeclaration pin = producer().getInterface().getOutputVars().stream()
				.filter(v -> PRODUCER_OUT_PIN.equals(v.getName())).findFirst().orElseThrow();
		return PackageNameHelper.getFullTypeName(pin.getType());
	}

	private String expandedLeafMemberAttribute(final String leafMemberName) {
		final IInterfaceElement pin = producer().getInterface()
				.getInterfaceElement(List.of(PRODUCER_OUT_PIN, ROOT_MIDDLE_MEMBER, MIDDLE_LEAF_MEMBER, leafMemberName));
		// Fail on the missing pin instead of returning null, so a lost attribute
		// cannot be confused with a member pin that the rename never created.
		assertNotNull(pin, () -> PRODUCER_OUT_PIN + "." + ROOT_MIDDLE_MEMBER + "." + MIDDLE_LEAF_MEMBER + "." //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ leafMemberName + " does not exist"); //$NON-NLS-1$
		return pin.getAttributeValue(CUSTOM_ATTRIBUTE);
	}

	private URI leafMemberURI(final String memberName) {
		return EcoreUtil.getURI(structMember(LEAF_STRUCT, memberName));
	}

	private VarDeclaration structMember(final String structName, final String memberName) {
		return structuredType(structName).getMemberVariables().stream().filter(m -> memberName.equals(m.getName()))
				.findFirst().orElseThrow();
	}

	private StructuredType structuredType(final String qualifiedName) {
		return typeLibrary.getDataTypeLibrary().getStructuredType(qualifiedName);
	}

	private BlockFBNetworkElement producer() {
		final FBNetworkElement element = system().getApplicationNamed(APPLICATION_NAME).getFBNetwork()
				.getNetworkElements().stream().filter(e -> PRODUCER_INSTANCE.equals(e.getName())).findFirst()
				.orElseThrow();
		return assertInstanceOf(BlockFBNetworkElement.class, element);
	}

	private AutomationSystem system() {
		return (AutomationSystem) typeLibrary.getTypeEntry(file(SYSTEM_FILE)).getType();
	}

	private void assertEntryTypeName(final IFile typeFile, final String expectedFullTypeName) {
		final TypeEntry entry = typeLibrary.getTypeEntry(typeFile);
		assertNotNull(entry);
		assertFalse(entry.hasError());
		assertEquals(expectedFullTypeName, entry.getFullTypeName());
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
