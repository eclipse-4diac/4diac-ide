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
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PRODUCER_OUT_PIN;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PRODUCER_TYPE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_PATH;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.SYSTEM_FILE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.refactoring.RefactorElementPropertyTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RefactorElementPropertyTesterTest {

	private static final String RENAME_SUPPORTED = "renameSupported"; //$NON-NLS-1$
	private static final String PRODUCER_INSTANCE = "Producer"; //$NON-NLS-1$
	private static final String MEMBER = "A"; //$NON-NLS-1$

	private final RefactorElementPropertyTester tester = new RefactorElementPropertyTester();

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
	void structMember_supportsRename() {
		assertTrue(renameSupported(innerStructMember()));
	}

	@Test
	void fbTypeInterfacePin_supportsRename() {
		assertTrue(renameSupported(producerTypeOutPin()));
	}

	@Test
	void instancePin_doesNotSupportRename() {
		assertFalse(renameSupported(producerInstancePin(List.of(PRODUCER_OUT_PIN))));
	}

	@Test
	void instanceMemberPin_doesNotSupportRename() {
		assertFalse(renameSupported(producerInstancePin(List.of(PRODUCER_OUT_PIN, MEMBER))));
	}

	private boolean renameSupported(final Object element) {
		return tester.test(element, RENAME_SUPPORTED, null, null);
	}

	private VarDeclaration innerStructMember() {
		return typeLibrary.getDataTypeLibrary().getStructuredType(INNER_STRUCT).getMemberVariables().stream()
				.filter(member -> MEMBER.equals(member.getName())).findFirst().orElseThrow();
	}

	private VarDeclaration producerTypeOutPin() {
		final FBType type = (FBType) typeLibrary.getFBTypeEntry(PRODUCER_TYPE).getType();
		return type.getInterfaceList().getOutputVars().stream()
				.filter(pin -> PRODUCER_OUT_PIN.equals(pin.getName())).findFirst().orElseThrow();
	}

	private IInterfaceElement producerInstancePin(final List<String> path) {
		final AutomationSystem system = (AutomationSystem) typeLibrary.getTypeEntry(file(SYSTEM_FILE)).getType();
		final BlockFBNetworkElement producer = (BlockFBNetworkElement) system.getApplicationNamed(APPLICATION_NAME)
				.getFBNetwork().getNetworkElements().stream()
				.filter(element -> PRODUCER_INSTANCE.equals(element.getName())).findFirst().orElseThrow();
		return Objects.requireNonNull(producer.getInterface().getInterfaceElement(path, true));
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
