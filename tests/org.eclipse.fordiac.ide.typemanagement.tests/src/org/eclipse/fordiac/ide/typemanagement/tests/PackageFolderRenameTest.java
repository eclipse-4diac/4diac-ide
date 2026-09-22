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

import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.APPLICATION_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.CONSUMER_DI_PIN;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.CONSUMER_DO1_PIN;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.CONSUMER_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.CONSUMER_INSTANCE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.CONSUMER_RENAMED_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.CONSUMER_TYPE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.CONSUMER_TYPE_RENAMED;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.MY_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.MY_STRUCT_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.MY_STRUCT_RENAMED;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.MY_STRUCT_RENAMED_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.OUTSIDE_TYPE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.OUTSIDE_TYPE_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.PACKAGE_FOLDER;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.PRODUCER_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.PRODUCER_INSTANCE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.PRODUCER_OUT_PIN;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.PRODUCER_RENAMED_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.PRODUCER_TYPE;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.PRODUCER_TYPE_RENAMED;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.PROJECT_PATH;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.RENAMED_PACKAGE_FOLDER;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.RENAMED_PACKAGE_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.PackageRenameTestFixture.SYSTEM_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CONVERT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CORE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.IEC_61131_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.refactoring.rename.RenameTypeRefactoringParticipant;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.RefactoringProcessor;
import org.eclipse.ltk.core.refactoring.participants.RenameArguments;
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceDescriptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PackageFolderRenameTest {

	private static final String RENAMED_PRODUCER_FILE_NAME = "ProducerRenamed.fbt"; //$NON-NLS-1$
	private static final String RENAMED_PRODUCER_FILE = "Type Library/mypackage/ProducerRenamed.fbt"; //$NON-NLS-1$
	private static final String RENAMED_PRODUCER_TYPE = "mypackage::ProducerRenamed"; //$NON-NLS-1$

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
	void renamePackageFolder_repointsContainedTypesToNewPackage() throws Exception {
		assertTypeEntryFullTypeNameEqual(file(MY_STRUCT_FILE), MY_STRUCT);
		assertTypeEntryFullTypeNameEqual(file(PRODUCER_FILE), PRODUCER_TYPE);
		assertTypeEntryFullTypeNameEqual(file(CONSUMER_FILE), CONSUMER_TYPE);
		assertTypeEntryFullTypeNameEqual(file(OUTSIDE_TYPE_FILE), OUTSIDE_TYPE);

		renamePackageFolder();

		assertFalse(folder(PACKAGE_FOLDER).exists());
		assertTrue(folder(RENAMED_PACKAGE_FOLDER).exists());
		assertTypeEntryFullTypeNameEqual(file(MY_STRUCT_RENAMED_FILE), MY_STRUCT_RENAMED);
		assertTypeEntryFullTypeNameEqual(file(PRODUCER_RENAMED_FILE), PRODUCER_TYPE_RENAMED);
		assertTypeEntryFullTypeNameEqual(file(CONSUMER_RENAMED_FILE), CONSUMER_TYPE_RENAMED);
		// ControlBlock lives outside mypackage, so the folder rename must leave it in place.
		assertTypeEntryFullTypeNameEqual(file(OUTSIDE_TYPE_FILE), OUTSIDE_TYPE);
	}

	@Test
	void renamePackageFolder_keepsInstancesResolvable() throws Exception {
		assertEquals(PRODUCER_TYPE, producerInstance().getFullTypeName());
		assertEquals(CONSUMER_TYPE, consumerInstance().getFullTypeName());
		assertFalse(systemEntry().hasError());

		renamePackageFolder();

		assertEquals(PRODUCER_TYPE_RENAMED, producerInstance().getFullTypeName());
		assertEquals(CONSUMER_TYPE_RENAMED, consumerInstance().getFullTypeName());
		assertFalse(systemEntry().hasError());
	}

	@Test
	void renameTypeFile_changesTypeNameButNotPackage() throws Exception {
		assertTypeEntryFullTypeNameEqual(file(PRODUCER_FILE), PRODUCER_TYPE);
		assertEquals(PRODUCER_TYPE, producerInstance().getFullTypeName());

		RefactoringTestSupport.performRename(file(PRODUCER_FILE), RENAMED_PRODUCER_FILE_NAME);

		// The file rename changes the type name but stays in mypackage, and the producer
		// instance follows it, unlike the folder rename which moves the package instead.
		assertFalse(file(PRODUCER_FILE).exists());
		assertTypeEntryFullTypeNameEqual(file(RENAMED_PRODUCER_FILE), RENAMED_PRODUCER_TYPE);
		assertEquals(RENAMED_PRODUCER_TYPE, producerInstance().getFullTypeName());
	}

	@Test
	void createPreChange_returnsNullForFileRename() throws Exception {
		final IFile producerFile = file(PRODUCER_FILE);
		final RenameResourceDescriptor descriptor = new RenameResourceDescriptor();
		descriptor.setResourcePath(producerFile.getFullPath());
		descriptor.setNewName(RENAMED_PRODUCER_FILE_NAME);
		final RenameRefactoring refactoring = (RenameRefactoring) descriptor.createRefactoring(new RefactoringStatus());
		final RefactoringProcessor processor = refactoring.getProcessor();

		final RenameTypeRefactoringParticipant participant = new RenameTypeRefactoringParticipant();
		final boolean initialized = participant.initialize(processor, producerFile,
				new RenameArguments(RENAMED_PRODUCER_FILE_NAME, true));

		assertTrue(initialized);
		assertNull(participant.createPreChange(new NullProgressMonitor()));
	}

	@Test
	void renamePackageFolder_repointsStructReferencingPins() throws Exception {
		assertEquals(MY_STRUCT, pinType(PRODUCER_TYPE, PRODUCER_OUT_PIN));
		assertEquals(MY_STRUCT, pinType(CONSUMER_TYPE, CONSUMER_DI_PIN));
		assertEquals(MY_STRUCT, pinType(CONSUMER_TYPE, CONSUMER_DO1_PIN));

		renamePackageFolder();

		// Producer.OUT, Consumer.DI and Consumer.DO1 are typed with MyStruct, so the folder rename
		// must repoint their data type to renamedpackage::MyStruct, not only rename the type entries.
		assertEquals(MY_STRUCT_RENAMED, pinType(PRODUCER_TYPE_RENAMED, PRODUCER_OUT_PIN));
		assertEquals(MY_STRUCT_RENAMED, pinType(CONSUMER_TYPE_RENAMED, CONSUMER_DI_PIN));
		assertEquals(MY_STRUCT_RENAMED, pinType(CONSUMER_TYPE_RENAMED, CONSUMER_DO1_PIN));
	}

	@Test
	void renamePackageFolder_undoRedoRoundTrip() throws Exception {
		assertOriginalState();

		renamePackageFolder();
		assertRenamedState();

		RefactoringTestSupport.undoLastRefactoring();
		assertOriginalState();

		RefactoringTestSupport.redoLastRefactoring();
		assertRenamedState();
	}

	private void assertOriginalState() {
		assertTrue(folder(PACKAGE_FOLDER).exists());
		assertFalse(folder(RENAMED_PACKAGE_FOLDER).exists());
		assertTypeEntryFullTypeNameEqual(file(MY_STRUCT_FILE), MY_STRUCT);
		assertTypeEntryFullTypeNameEqual(file(PRODUCER_FILE), PRODUCER_TYPE);
		assertTypeEntryFullTypeNameEqual(file(CONSUMER_FILE), CONSUMER_TYPE);
		assertEquals(PRODUCER_TYPE, producerInstance().getFullTypeName());
		assertEquals(CONSUMER_TYPE, consumerInstance().getFullTypeName());
		assertFalse(systemEntry().hasError());
	}

	private void assertRenamedState() {
		assertFalse(folder(PACKAGE_FOLDER).exists());
		assertTrue(folder(RENAMED_PACKAGE_FOLDER).exists());
		assertTypeEntryFullTypeNameEqual(file(MY_STRUCT_RENAMED_FILE), MY_STRUCT_RENAMED);
		assertTypeEntryFullTypeNameEqual(file(PRODUCER_RENAMED_FILE), PRODUCER_TYPE_RENAMED);
		assertTypeEntryFullTypeNameEqual(file(CONSUMER_RENAMED_FILE), CONSUMER_TYPE_RENAMED);
		assertEquals(PRODUCER_TYPE_RENAMED, producerInstance().getFullTypeName());
		assertEquals(CONSUMER_TYPE_RENAMED, consumerInstance().getFullTypeName());
		assertFalse(systemEntry().hasError());
	}

	// Reads the resolved data type of an interface pin on the given FB type, so a stale reference
	// after the package rename shows up as the old qualified name instead of the renamed one.
	private String pinType(final String fbTypeName, final String pinName) {
		final FBType fbType = (FBType) typeLibrary.getFBTypeEntry(fbTypeName).getType();
		final VarDeclaration pin = fbType.getInterfaceList().getVariable(pinName);
		return PackageNameHelper.getFullTypeName(pin.getType());
	}

	private void renamePackageFolder() throws Exception {
		final IFolder packageFolder = project.getFolder(PACKAGE_FOLDER);
		RefactoringTestSupport.performFolderRename(packageFolder, RENAMED_PACKAGE_NAME);
	}

	private FBNetworkElement producerInstance() {
		return RefactoringTestSupport.findInstance(system(), APPLICATION_NAME, PRODUCER_INSTANCE);
	}

	private FBNetworkElement consumerInstance() {
		return RefactoringTestSupport.findInstance(system(), APPLICATION_NAME, CONSUMER_INSTANCE);
	}

	private AutomationSystem system() {
		return (AutomationSystem) systemEntry().getType();
	}

	private TypeEntry systemEntry() {
		return typeLibrary.getTypeEntry(file(SYSTEM_FILE));
	}

	private void assertTypeEntryFullTypeNameEqual(final IFile typeFile, final String expectedFullTypeName) {
		final TypeEntry entry = typeLibrary.getTypeEntry(typeFile);
		assertNotNull(entry);
		assertFalse(entry.hasError());
		assertEquals(expectedFullTypeName, entry.getFullTypeName());
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}

	private IFolder folder(final String projectRelativePath) {
		return project.getFolder(projectRelativePath);
	}
}
