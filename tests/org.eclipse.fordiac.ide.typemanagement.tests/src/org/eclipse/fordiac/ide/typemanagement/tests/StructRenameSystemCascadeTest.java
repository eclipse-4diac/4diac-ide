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
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT_FILE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.INNER_STRUCT_RENAMED;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PRODUCER_OUT_PIN;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PRODUCER_TYPE;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_NAME;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.PROJECT_PATH;
import static org.eclipse.fordiac.ide.typemanagement.tests.StructRenameTestFixture.SYSTEM_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class StructRenameSystemCascadeTest {

	private static final String NEW_INNER_FILE_NAME = "InnerStructRenamed.dtp"; //$NON-NLS-1$
	private static final String CONSUMER_TYPE = "StructConsumer"; //$NON-NLS-1$
	private static final String CONSUMER_INPUT_PIN = "DI"; //$NON-NLS-1$
	private static final String CONSUMER_OUTPUT_PIN = "DO1"; //$NON-NLS-1$
	private static final String DEMUX_INSTANCE = "Demux"; //$NON-NLS-1$
	private static final String MUX_INSTANCE = "Mux"; //$NON-NLS-1$
	private static final String FMOVE_INSTANCE = "Move"; //$NON-NLS-1$

	private static final String CONTAINER_SUBAPP = "Container"; //$NON-NLS-1$
	private static final String CONTAINER_INPUT_PIN = "DI"; //$NON-NLS-1$
	private static final String HIDDEN_CONNECTION = "Producer.OUT.C -> Consumer.DI.C"; //$NON-NLS-1$

	private static final String PRODUCER_INSTANCE = "Producer"; //$NON-NLS-1$
	private static final String MEMBER = "A"; //$NON-NLS-1$
	private static final String MEMBER_RENAMED = "ARenamed"; //$NON-NLS-1$
	private static final String CUSTOM_ATTRIBUTE = "TestAttribute"; //$NON-NLS-1$
	private static final String CUSTOM_ATTRIBUTE_VALUE = "42"; //$NON-NLS-1$

	private static final Set<String> APP_DATA_CONNECTIONS = Set.of("Producer.OUT.A -> Consumer.DI.A", //$NON-NLS-1$
			"Producer.OUT.C -> Consumer.DI.C", "Producer.OUT -> Container.DI"); //$NON-NLS-1$ //$NON-NLS-2$
	private static final Set<String> CONTAINER_DATA_CONNECTIONS = Set.of("Container.DI -> NestedConsumer.DI"); //$NON-NLS-1$

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
	void renameInnerStruct_updatesStructProducerInterfacePinType() throws Exception {
		assertFBInterfacePinType(PRODUCER_TYPE, PRODUCER_OUT_PIN, INNER_STRUCT);

		renameInnerStruct();

		assertFBInterfacePinType(PRODUCER_TYPE, PRODUCER_OUT_PIN, INNER_STRUCT_RENAMED);
	}

	@Test
	void renameInnerStruct_updatesStructConsumerInterfacePinTypes() throws Exception {
		assertFBInterfacePinType(CONSUMER_TYPE, CONSUMER_INPUT_PIN, INNER_STRUCT);
		assertFBInterfacePinType(CONSUMER_TYPE, CONSUMER_OUTPUT_PIN, INNER_STRUCT);

		renameInnerStruct();

		assertFBInterfacePinType(CONSUMER_TYPE, CONSUMER_INPUT_PIN, INNER_STRUCT_RENAMED);
		assertFBInterfacePinType(CONSUMER_TYPE, CONSUMER_OUTPUT_PIN, INNER_STRUCT_RENAMED);
	}

	@Test
	void renameInnerStruct_updatesConfiguredStructDemuxInstance() throws Exception {
		assertConfigurableFBDataType(DEMUX_INSTANCE, Demultiplexer.class, INNER_STRUCT);

		renameInnerStruct();

		assertConfigurableFBDataType(DEMUX_INSTANCE, Demultiplexer.class, INNER_STRUCT_RENAMED);
	}

	@Test
	void renameInnerStruct_keepsExpandedMemberConnections() throws Exception {
		assertEquals(APP_DATA_CONNECTIONS, applicationDataConnections());
		assertEquals(CONTAINER_DATA_CONNECTIONS, containerDataConnections());

		renameInnerStruct();

		assertEquals(APP_DATA_CONNECTIONS, applicationDataConnections());
		assertEquals(CONTAINER_DATA_CONNECTIONS, containerDataConnections());
	}

	@Test
	void renameInnerStruct_updatesContainerSubAppInterfacePinType() throws Exception {
		assertEquals(INNER_STRUCT, containerInputPinType());

		renameInnerStruct();

		assertEquals(INNER_STRUCT_RENAMED, containerInputPinType());
	}

	@Test
	void renameInnerStruct_keepsHiddenConnectionHidden() throws Exception {
		assertFalse(hiddenConnection().isVisible());

		renameInnerStruct();

		assertFalse(hiddenConnection().isVisible());
	}

	@Test
	@Disabled("Custom attributes on expanded struct member pins are lost when the member is renamed, re-enable " //$NON-NLS-1$
			+ "when https://github.com/eclipse-4diac/4diac-ide/issues/2646 is fixed")
	void renameStructMember_preservesCustomPinAttribute() throws Exception {
		assertEquals(CUSTOM_ATTRIBUTE_VALUE, producerOutMemberAttribute(MEMBER));

		RefactoringTestSupport.performElementRename(innerStructMemberURI(MEMBER), MEMBER_RENAMED);

		assertEquals(CUSTOM_ATTRIBUTE_VALUE, producerOutMemberAttribute(MEMBER_RENAMED));
	}

	@Test
	void renameInnerStruct_updatesConfiguredStructMuxInstance() throws Exception {
		assertConfigurableFBDataType(MUX_INSTANCE, Multiplexer.class, INNER_STRUCT);

		renameInnerStruct();

		assertConfigurableFBDataType(MUX_INSTANCE, Multiplexer.class, INNER_STRUCT_RENAMED);
	}

	@Test
	void renameInnerStruct_updatesConfiguredFMoveInstance() throws Exception {
		assertConfigurableFBDataType(FMOVE_INSTANCE, ConfigurableMoveFB.class, INNER_STRUCT);

		renameInnerStruct();

		assertConfigurableFBDataType(FMOVE_INSTANCE, ConfigurableMoveFB.class, INNER_STRUCT_RENAMED);
	}

	private void renameInnerStruct() throws Exception {
		RefactoringTestSupport.performRename(file(INNER_STRUCT_FILE), NEW_INNER_FILE_NAME);
	}

	private void assertFBInterfacePinType(final String fbTypeName, final String pinName,
			final String expectedQualifiedType) {
		final FBType fbType = typeLibrary.getFBTypeEntry(fbTypeName).getType();
		final VarDeclaration pin = Stream
				.concat(fbType.getInterfaceList().getInputVars().stream(),
						fbType.getInterfaceList().getOutputVars().stream())
				.filter(v -> pinName.equals(v.getName())).findFirst().orElseThrow();
		assertEquals(expectedQualifiedType, PackageNameHelper.getFullTypeName(pin.getType()));
	}

	private <T extends ConfigurableFB> void assertConfigurableFBDataType(final String instanceName,
			final Class<T> instanceClass, final String expectedQualifiedType) {
		final FBNetworkElement element = system().getApplicationNamed(APPLICATION_NAME).getFBNetwork()
				.getNetworkElements().stream().filter(e -> instanceName.equals(e.getName())).findFirst().orElseThrow();
		final T configurable = assertInstanceOf(instanceClass, element);
		assertEquals(expectedQualifiedType, PackageNameHelper.getFullTypeName(configurable.getDataType()));
	}

	private Set<String> applicationDataConnections() {
		return dataConnectionNames(system().getApplicationNamed(APPLICATION_NAME).getFBNetwork());
	}

	private Set<String> containerDataConnections() {
		return dataConnectionNames(container().getSubAppNetwork());
	}

	private static Set<String> dataConnectionNames(final FBNetwork network) {
		return network.getDataConnections().stream().map(StructRenameSystemCascadeTest::connectionName)
				.collect(Collectors.toSet());
	}

	private static String connectionName(final Connection connection) {
		return endpointName(connection.getSource()) + " -> " + endpointName(connection.getDestination()); //$NON-NLS-1$
	}

	private static String endpointName(final IInterfaceElement pin) {
		final BlockFBNetworkElement block = pin.getBlockFBNetworkElement();
		return block.getName() + "." + pin.getRelativeName(block); //$NON-NLS-1$
	}

	private String containerInputPinType() {
		final VarDeclaration pin = container().getInterface().getInputVars().stream()
				.filter(v -> CONTAINER_INPUT_PIN.equals(v.getName())).findFirst().orElseThrow();
		return PackageNameHelper.getFullTypeName(pin.getType());
	}

	private URI innerStructMemberURI(final String memberName) {
		final VarDeclaration member = typeLibrary.getDataTypeLibrary().getStructuredType(INNER_STRUCT)
				.getMemberVariables().stream().filter(m -> memberName.equals(m.getName())).findFirst().orElseThrow();
		return EcoreUtil.getURI(member);
	}

	private String producerOutMemberAttribute(final String memberName) {
		final FBNetworkElement producer = system().getApplicationNamed(APPLICATION_NAME).getFBNetwork()
				.getNetworkElements().stream().filter(e -> PRODUCER_INSTANCE.equals(e.getName())).findFirst()
				.orElseThrow();
		final IInterfaceElement pin = assertInstanceOf(BlockFBNetworkElement.class, producer).getInterface()
				.getInterfaceElement(List.of(PRODUCER_OUT_PIN, memberName));
		// Fail on the missing pin instead of returning null, so a lost attribute
		// cannot be confused with a member pin that the rename never created.
		assertNotNull(pin, () -> PRODUCER_OUT_PIN + "." + memberName + " does not exist"); //$NON-NLS-1$ //$NON-NLS-2$
		return pin.getAttributeValue(CUSTOM_ATTRIBUTE);
	}

	private Connection hiddenConnection() {
		return system().getApplicationNamed(APPLICATION_NAME).getFBNetwork().getDataConnections().stream()
				.filter(connection -> HIDDEN_CONNECTION.equals(connectionName(connection))).findFirst().orElseThrow();
	}

	private UntypedSubApp container() {
		final FBNetworkElement element = system().getApplicationNamed(APPLICATION_NAME).getFBNetwork()
				.getNetworkElements().stream().filter(e -> CONTAINER_SUBAPP.equals(e.getName())).findFirst()
				.orElseThrow();
		return assertInstanceOf(UntypedSubApp.class, element);
	}

	private AutomationSystem system() {
		return (AutomationSystem) typeLibrary.getTypeEntry(file(SYSTEM_FILE)).getType();
	}

	private IFile file(final String projectRelativePath) {
		return project.getFile(projectRelativePath);
	}
}
