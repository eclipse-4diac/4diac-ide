/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.tests;

import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CONVERT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.CORE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StandardLibrary.IEC_61131_3;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.APPLICATION_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.OUTER_STRUCT;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_PATH;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.SYSTEM_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.helpers.BlockInstanceFactory;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.AddStructMemberConfiguration;
import org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.AddStructMemberContext;
import org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.AddStructMemberRefactoring;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddStructMemberRefactoringTest {

	private static final String PRODUCER = "Producer"; //$NON-NLS-1$
	private static final String CONSUMER = "Consumer"; //$NON-NLS-1$
	private static final String MUX = "Mux"; //$NON-NLS-1$
	private static final String DEMUX = "Demux"; //$NON-NLS-1$
	private static final String GENERIC_SELECTOR = "GenericSelector"; //$NON-NLS-1$
	private static final String F_SEL = "F_SEL"; //$NON-NLS-1$

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
	void addAndConnectDirectStructPin_undoRedoRoundTrip() throws Exception {
		final VarDeclaration source = pin(PRODUCER, "OUT", "A"); //$NON-NLS-1$ //$NON-NLS-2$
		final VarDeclaration target = pin(CONSUMER, "DI"); //$NON-NLS-1$
		final AddStructMemberRefactoring refactoring = refactoring(source, target);
		final String memberName = refactoring.getConfiguration().memberName();
		assertEquals("A_1", memberName); //$NON-NLS-1$
		assertTrue(refactoring.getName().contains(memberName));
		assertTrue(refactoring.getName().contains("InnerStruct.dtp")); //$NON-NLS-1$
		final String connectionName = PRODUCER + ".OUT.A -> " + CONSUMER + ".DI." + memberName; //$NON-NLS-1$ //$NON-NLS-2$

		perform(refactoring);
		assertAdded(memberName, connectionName);

		RefactoringTestSupport.undoLastRefactoring();
		assertRemoved(memberName, connectionName);

		RefactoringTestSupport.redoLastRefactoring();
		assertAdded(memberName, connectionName);
	}

	@Test
	void addMemberFromSelectedStructPin_undoRedoRoundTrip() throws Exception {
		final AddStructMemberContext context = AddStructMemberContext.forStructPin(pin(CONSUMER, "DI")) //$NON-NLS-1$
				.orElseThrow();
		final AddStructMemberRefactoring refactoring = new AddStructMemberRefactoring(context);
		final AddStructMemberConfiguration current = refactoring.getConfiguration();
		final String memberName = current.memberName();
		assertTrue(refactoring.isTypeSelectionRequired());
		assertFalse(refactoring.setConfiguration(new AddStructMemberConfiguration(memberName, current.comment(), "BOOL", //$NON-NLS-1$
				current.arraySize(), current.insertBefore())).hasFatalError());

		perform(refactoring);
		assertNotNull(structuredType().getMemberVar(memberName));

		RefactoringTestSupport.undoLastRefactoring();
		assertNull(structuredType().getMemberVar(memberName));

		RefactoringTestSupport.redoLastRefactoring();
		assertNotNull(structuredType().getMemberVar(memberName));
	}

	@Test
	void addAndConnectMultiplexer_undoRedoRoundTrip() throws Exception {
		final VarDeclaration source = pin(PRODUCER, "OUT", "A"); //$NON-NLS-1$ //$NON-NLS-2$
		final Multiplexer target = assertInstanceOf(Multiplexer.class, block(MUX));
		final AddStructMemberRefactoring refactoring = refactoring(source, target);
		final String memberName = refactoring.getConfiguration().memberName();
		final String connectionName = PRODUCER + ".OUT.A -> " + MUX + "." + memberName; //$NON-NLS-1$ //$NON-NLS-2$

		perform(refactoring);
		assertAdded(memberName, connectionName);

		RefactoringTestSupport.undoLastRefactoring();
		assertRemoved(memberName, connectionName);

		RefactoringTestSupport.redoLastRefactoring();
		assertAdded(memberName, connectionName);
	}

	@Test
	void addAndConnectDemultiplexer_undoRedoRoundTrip() throws Exception {
		final VarDeclaration destination = pin(MUX, "B"); //$NON-NLS-1$
		final Demultiplexer target = assertInstanceOf(Demultiplexer.class, block(DEMUX));
		final AddStructMemberRefactoring refactoring = refactoring(destination, target);
		final String memberName = refactoring.getConfiguration().memberName();
		final String connectionName = DEMUX + "." + memberName + " -> " + MUX + ".B"; //$NON-NLS-1$ //$NON-NLS-2$

		perform(refactoring);
		assertAdded(memberName, connectionName);

		RefactoringTestSupport.undoLastRefactoring();
		assertRemoved(memberName, connectionName);

		RefactoringTestSupport.redoLastRefactoring();
		assertAdded(memberName, connectionName);
	}

	@Test
	void genericConnectionPin_requiresConcreteTypeSelection() throws Exception {
		addGenericSelector();
		final AddStructMemberRefactoring refactoring = refactoring(pin(GENERIC_SELECTOR, "OUT"), //$NON-NLS-1$
				pin(CONSUMER, "DI")); //$NON-NLS-1$
		assertTrue(refactoring.isTypeSelectionRequired());
		assertTrue(refactoring.getConfiguration().memberTypeName().isEmpty());

		final AddStructMemberConfiguration current = refactoring.getConfiguration();
		final RefactoringStatus status = refactoring.setConfiguration(new AddStructMemberConfiguration(
				current.memberName(), current.comment(), "BOOL", current.arraySize(), current.insertBefore())); //$NON-NLS-1$
		assertFalse(status.hasFatalError());

		perform(refactoring);
		final String connectionName = GENERIC_SELECTOR + ".OUT -> " + CONSUMER + ".DI." //$NON-NLS-1$ //$NON-NLS-2$
				+ current.memberName();
		assertAdded(current.memberName(), connectionName);
		final VarDeclaration member = structuredType().getMemberVar(current.memberName());
		assertEquals("BOOL", PackageNameHelper.getFullTypeName(member.getType())); //$NON-NLS-1$
	}

	@Test
	void recursiveMemberType_isRejected() throws Exception {
		addGenericSelector();
		final AddStructMemberRefactoring refactoring = refactoring(pin(GENERIC_SELECTOR, "OUT"), //$NON-NLS-1$
				pin(CONSUMER, "DI")); //$NON-NLS-1$
		final AddStructMemberConfiguration current = refactoring.getConfiguration();

		final RefactoringStatus status = refactoring.setConfiguration(new AddStructMemberConfiguration(
				current.memberName(), current.comment(), OUTER_STRUCT, current.arraySize(), current.insertBefore()));

		assertTrue(status.hasFatalError());
		assertTrue(Stream.of(status.getEntries())
				.anyMatch(entry -> Messages.AddStructMemberRefactoring_RecursiveType.equals(entry.getMessage())));
		assertNull(structuredType().getMemberVar(current.memberName()));
	}

	private AddStructMemberRefactoring refactoring(final VarDeclaration connectionPin, final EObject target) {
		return new AddStructMemberRefactoring(AddStructMemberContext.forTarget(connectionPin, target).orElseThrow());
	}

	private static void perform(final AddStructMemberRefactoring refactoring) throws Exception {
		assertFalse(refactoring.checkFinalConditions(new NullProgressMonitor()).hasFatalError());
		RefactoringTestSupport.performRefactoring(refactoring);
	}

	private void assertAdded(final String memberName, final String connectionName) {
		assertNotNull(structuredType().getMemberVar(memberName));
		assertTrue(dataConnectionNames().anyMatch(connectionName::equals));
	}

	private void assertRemoved(final String memberName, final String connectionName) {
		assertNull(structuredType().getMemberVar(memberName));
		assertFalse(dataConnectionNames().anyMatch(connectionName::equals));
	}

	private Stream<String> dataConnectionNames() {
		return network().getDataConnections().stream().map(AddStructMemberRefactoringTest::connectionName);
	}

	private static String connectionName(final Connection connection) {
		return endpointName(connection.getSource()) + " -> " + endpointName(connection.getDestination()); //$NON-NLS-1$
	}

	private static String endpointName(final IInterfaceElement pin) {
		final BlockFBNetworkElement block = pin.getBlockFBNetworkElement();
		return block.getName() + "." + pin.getRelativeName(block); //$NON-NLS-1$
	}

	private void addGenericSelector() throws Exception {
		final FBTypeEntry entry = typeLibrary.getFbTypes().filter(candidate -> F_SEL.equals(candidate.getTypeName()))
				.findFirst().orElseThrow();
		final FB selector = BlockInstanceFactory.createFBInstanceForTypeEntry(entry);
		selector.setName(GENERIC_SELECTOR);
		selector.setInterface(entry.getInterface().instanceCopy());
		selector.setTypeEntry(entry);
		network().getNetworkElements().add(selector);
		typeLibrary.getTypeEntry(file(SYSTEM_FILE)).save(system());
	}

	private StructuredType structuredType() {
		return typeLibrary.getDataTypeLibrary().getStructuredType(INNER_STRUCT);
	}

	private VarDeclaration pin(final String blockName, final String... path) {
		return (VarDeclaration) block(blockName).getInterface().getInterfaceElement(List.of(path), true);
	}

	private BlockFBNetworkElement block(final String name) {
		return network().getNetworkElements().stream().filter(BlockFBNetworkElement.class::isInstance)
				.map(BlockFBNetworkElement.class::cast).filter(element -> name.equals(element.getName())).findFirst()
				.orElseThrow();
	}

	private static BlockFBNetworkElement nestedConsumer(final SubApp container) {
		return container.getSubAppNetwork().getNetworkElements().stream()
				.filter(BlockFBNetworkElement.class::isInstance).map(BlockFBNetworkElement.class::cast)
				.filter(element -> NESTED_CONSUMER.equals(element.getName())).findFirst().orElseThrow();
	}

	private FBNetwork network() {
		return system().getApplicationNamed(APPLICATION_NAME).getFBNetwork();
	}

	private AutomationSystem system() {
		return (AutomationSystem) typeLibrary.getTypeEntry(file(SYSTEM_FILE)).getType();
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
