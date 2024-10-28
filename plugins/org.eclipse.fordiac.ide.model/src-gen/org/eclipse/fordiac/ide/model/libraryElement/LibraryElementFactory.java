/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage
 * @generated
 */
public interface LibraryElementFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LibraryElementFactory eINSTANCE = org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Adapter Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Adapter Connection</em>'.
	 * @generated
	 */
	AdapterConnection createAdapterConnection();

	/**
	 * Returns a new object of class '<em>Adapter Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Adapter Declaration</em>'.
	 * @generated
	 */
	AdapterDeclaration createAdapterDeclaration();

	/**
	 * Returns a new object of class '<em>Adapter FB</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Adapter FB</em>'.
	 * @generated
	 */
	AdapterFB createAdapterFB();

	/**
	 * Returns a new object of class '<em>Adapter Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Adapter Type</em>'.
	 * @generated
	 */
	AdapterType createAdapterType();

	/**
	 * Returns a new object of class '<em>Application</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application</em>'.
	 * @generated
	 */
	Application createApplication();

	/**
	 * Returns a new object of class '<em>Array Size</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Array Size</em>'.
	 * @generated
	 */
	ArraySize createArraySize();

	/**
	 * Returns a new object of class '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute</em>'.
	 * @generated
	 */
	Attribute createAttribute();

	/**
	 * Returns a new object of class '<em>Attribute Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Declaration</em>'.
	 * @generated
	 */
	AttributeDeclaration createAttributeDeclaration();

	/**
	 * Returns a new object of class '<em>Base FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Base FB Type</em>'.
	 * @generated
	 */
	BaseFBType createBaseFBType();

	/**
	 * Returns a new object of class '<em>Basic FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Basic FB Type</em>'.
	 * @generated
	 */
	BasicFBType createBasicFBType();

	/**
	 * Returns a new object of class '<em>Automation System</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Automation System</em>'.
	 * @generated
	 */
	AutomationSystem createAutomationSystem();

	/**
	 * Returns a new object of class '<em>CFB Instance</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>CFB Instance</em>'.
	 * @generated
	 */
	CFBInstance createCFBInstance();

	/**
	 * Returns a new object of class '<em>Color</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Color</em>'.
	 * @generated
	 */
	Color createColor();

	/**
	 * Returns a new object of class '<em>Colorizable Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Colorizable Element</em>'.
	 * @generated
	 */
	ColorizableElement createColorizableElement();

	/**
	 * Returns a new object of class '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 */
	Comment createComment();

	/**
	 * Returns a new object of class '<em>Communication Channel</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Communication Channel</em>'.
	 * @generated
	 */
	CommunicationChannel createCommunicationChannel();

	/**
	 * Returns a new object of class '<em>Communication Mapping Target</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Communication Mapping Target</em>'.
	 * @generated
	 */
	CommunicationMappingTarget createCommunicationMappingTarget();

	/**
	 * Returns a new object of class '<em>Compiler</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compiler</em>'.
	 * @generated
	 */
	Compiler createCompiler();

	/**
	 * Returns a new object of class '<em>Compiler Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compiler Info</em>'.
	 * @generated
	 */
	CompilerInfo createCompilerInfo();

	/**
	 * Returns a new object of class '<em>Composite FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composite FB Type</em>'.
	 * @generated
	 */
	CompositeFBType createCompositeFBType();

	/**
	 * Returns a new object of class '<em>Configurable Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Configurable Object</em>'.
	 * @generated
	 */
	ConfigurableObject createConfigurableObject();

	/**
	 * Returns a new object of class '<em>Configurable Move FB</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Configurable Move FB</em>'.
	 * @generated
	 */
	ConfigurableMoveFB createConfigurableMoveFB();

	/**
	 * Returns a new object of class '<em>Connection Routing Data</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connection Routing Data</em>'.
	 * @generated
	 */
	ConnectionRoutingData createConnectionRoutingData();

	/**
	 * Returns a new object of class '<em>Data Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Data Connection</em>'.
	 * @generated
	 */
	DataConnection createDataConnection();

	/**
	 * Returns a new object of class '<em>Demultiplexer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Demultiplexer</em>'.
	 * @generated
	 */
	Demultiplexer createDemultiplexer();

	/**
	 * Returns a new object of class '<em>Device</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Device</em>'.
	 * @generated
	 */
	Device createDevice();

	/**
	 * Returns a new object of class '<em>Device Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Device Type</em>'.
	 * @generated
	 */
	DeviceType createDeviceType();

	/**
	 * Returns a new object of class '<em>EC Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EC Action</em>'.
	 * @generated
	 */
	ECAction createECAction();

	/**
	 * Returns a new object of class '<em>ECC</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ECC</em>'.
	 * @generated
	 */
	ECC createECC();

	/**
	 * Returns a new object of class '<em>EC State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EC State</em>'.
	 * @generated
	 */
	ECState createECState();

	/**
	 * Returns a new object of class '<em>EC Transition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>EC Transition</em>'.
	 * @generated
	 */
	ECTransition createECTransition();

	/**
	 * Returns a new object of class '<em>Error Marker Data Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Error Marker Data Type</em>'.
	 * @generated
	 */
	ErrorMarkerDataType createErrorMarkerDataType();

	/**
	 * Returns a new object of class '<em>Error Marker FBN Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Error Marker FBN Element</em>'.
	 * @generated
	 */
	ErrorMarkerFBNElement createErrorMarkerFBNElement();

	/**
	 * Returns a new object of class '<em>Error Marker Interface</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Error Marker Interface</em>'.
	 * @generated
	 */
	ErrorMarkerInterface createErrorMarkerInterface();

	/**
	 * Returns a new object of class '<em>Event</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event</em>'.
	 * @generated
	 */
	Event createEvent();

	/**
	 * Returns a new object of class '<em>Event Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Event Connection</em>'.
	 * @generated
	 */
	EventConnection createEventConnection();

	/**
	 * Returns a new object of class '<em>FB</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>FB</em>'.
	 * @generated
	 */
	FB createFB();

	/**
	 * Returns a new object of class '<em>FB Network</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>FB Network</em>'.
	 * @generated
	 */
	FBNetwork createFBNetwork();

	/**
	 * Returns a new object of class '<em>FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>FB Type</em>'.
	 * @generated
	 */
	FBType createFBType();

	/**
	 * Returns a new object of class '<em>Function FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function FB Type</em>'.
	 * @generated
	 */
	FunctionFBType createFunctionFBType();

	/**
	 * Returns a new object of class '<em>Global Constants</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Global Constants</em>'.
	 * @generated
	 */
	GlobalConstants createGlobalConstants();

	/**
	 * Returns a new object of class '<em>Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Group</em>'.
	 * @generated
	 */
	Group createGroup();

	/**
	 * Returns a new object of class '<em>Hidden Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hidden Element</em>'.
	 * @generated
	 */
	HiddenElement createHiddenElement();

	/**
	 * Returns a new object of class '<em>Identification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Identification</em>'.
	 * @generated
	 */
	Identification createIdentification();

	/**
	 * Returns a new object of class '<em>Import</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Import</em>'.
	 * @generated
	 */
	Import createImport();

	/**
	 * Returns a new object of class '<em>Input Primitive</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Input Primitive</em>'.
	 * @generated
	 */
	InputPrimitive createInputPrimitive();

	/**
	 * Returns a new object of class '<em>Interface List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Interface List</em>'.
	 * @generated
	 */
	InterfaceList createInterfaceList();

	/**
	 * Returns a new object of class '<em>Library Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Library Element</em>'.
	 * @generated
	 */
	LibraryElement createLibraryElement();

	/**
	 * Returns a new object of class '<em>Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Link</em>'.
	 * @generated
	 */
	Link createLink();

	/**
	 * Returns a new object of class '<em>Local Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Local Variable</em>'.
	 * @generated
	 */
	LocalVariable createLocalVariable();

	/**
	 * Returns a new object of class '<em>Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping</em>'.
	 * @generated
	 */
	Mapping createMapping();

	/**
	 * Returns a new object of class '<em>Mapping Target</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Target</em>'.
	 * @generated
	 */
	MappingTarget createMappingTarget();

	/**
	 * Returns a new object of class '<em>Multiplexer</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Multiplexer</em>'.
	 * @generated
	 */
	Multiplexer createMultiplexer();

	/**
	 * Returns a new object of class '<em>Original Source</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Original Source</em>'.
	 * @generated
	 */
	OriginalSource createOriginalSource();

	/**
	 * Returns a new object of class '<em>Other Algorithm</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Other Algorithm</em>'.
	 * @generated
	 */
	OtherAlgorithm createOtherAlgorithm();

	/**
	 * Returns a new object of class '<em>Other Method</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Other Method</em>'.
	 * @generated
	 */
	OtherMethod createOtherMethod();

	/**
	 * Returns a new object of class '<em>Output Primitive</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Output Primitive</em>'.
	 * @generated
	 */
	OutputPrimitive createOutputPrimitive();

	/**
	 * Returns a new object of class '<em>Position</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Position</em>'.
	 * @generated
	 */
	Position createPosition();

	/**
	 * Returns a new object of class '<em>Positionable Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Positionable Element</em>'.
	 * @generated
	 */
	PositionableElement createPositionableElement();

	/**
	 * Returns a new object of class '<em>Primitive</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Primitive</em>'.
	 * @generated
	 */
	Primitive createPrimitive();

	/**
	 * Returns a new object of class '<em>Resource</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource</em>'.
	 * @generated
	 */
	Resource createResource();

	/**
	 * Returns a new object of class '<em>Resource Type Name</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Type Name</em>'.
	 * @generated
	 */
	ResourceTypeName createResourceTypeName();

	/**
	 * Returns a new object of class '<em>Resource Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Type</em>'.
	 * @generated
	 */
	ResourceType createResourceType();

	/**
	 * Returns a new object of class '<em>Resource Type FB</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Resource Type FB</em>'.
	 * @generated
	 */
	ResourceTypeFB createResourceTypeFB();

	/**
	 * Returns a new object of class '<em>Segment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Segment</em>'.
	 * @generated
	 */
	Segment createSegment();

	/**
	 * Returns a new object of class '<em>Segment Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Segment Type</em>'.
	 * @generated
	 */
	SegmentType createSegmentType();

	/**
	 * Returns a new object of class '<em>Service</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service</em>'.
	 * @generated
	 */
	Service createService();

	/**
	 * Returns a new object of class '<em>Service Sequence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service Sequence</em>'.
	 * @generated
	 */
	ServiceSequence createServiceSequence();

	/**
	 * Returns a new object of class '<em>Service Transaction</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service Transaction</em>'.
	 * @generated
	 */
	ServiceTransaction createServiceTransaction();

	/**
	 * Returns a new object of class '<em>Service Interface</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service Interface</em>'.
	 * @generated
	 */
	ServiceInterface createServiceInterface();

	/**
	 * Returns a new object of class '<em>Service Interface FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service Interface FB Type</em>'.
	 * @generated
	 */
	ServiceInterfaceFBType createServiceInterfaceFBType();

	/**
	 * Returns a new object of class '<em>Simple EC Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple EC Action</em>'.
	 * @generated
	 */
	SimpleECAction createSimpleECAction();

	/**
	 * Returns a new object of class '<em>Simple EC State</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple EC State</em>'.
	 * @generated
	 */
	SimpleECState createSimpleECState();

	/**
	 * Returns a new object of class '<em>Simple FB Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple FB Type</em>'.
	 * @generated
	 */
	SimpleFBType createSimpleFBType();

	/**
	 * Returns a new object of class '<em>ST Algorithm</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Algorithm</em>'.
	 * @generated
	 */
	STAlgorithm createSTAlgorithm();

	/**
	 * Returns a new object of class '<em>ST Function</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Function</em>'.
	 * @generated
	 */
	STFunction createSTFunction();

	/**
	 * Returns a new object of class '<em>ST Function Body</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Function Body</em>'.
	 * @generated
	 */
	STFunctionBody createSTFunctionBody();

	/**
	 * Returns a new object of class '<em>ST Method</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Method</em>'.
	 * @generated
	 */
	STMethod createSTMethod();

	/**
	 * Returns a new object of class '<em>Struct Manipulator</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Struct Manipulator</em>'.
	 * @generated
	 */
	StructManipulator createStructManipulator();

	/**
	 * Returns a new object of class '<em>Sub App Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Sub App Type</em>'.
	 * @generated
	 */
	SubAppType createSubAppType();

	/**
	 * Returns a new object of class '<em>System Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>System Configuration</em>'.
	 * @generated
	 */
	SystemConfiguration createSystemConfiguration();

	/**
	 * Returns a new object of class '<em>Typed Configureable Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Typed Configureable Object</em>'.
	 * @generated
	 */
	TypedConfigureableObject createTypedConfigureableObject();

	/**
	 * Returns a new object of class '<em>Typed Sub App</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Typed Sub App</em>'.
	 * @generated
	 */
	TypedSubApp createTypedSubApp();

	/**
	 * Returns a new object of class '<em>Untyped Sub App</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Untyped Sub App</em>'.
	 * @generated
	 */
	UntypedSubApp createUntypedSubApp();

	/**
	 * Returns a new object of class '<em>Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Value</em>'.
	 * @generated
	 */
	Value createValue();

	/**
	 * Returns a new object of class '<em>Var Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Var Declaration</em>'.
	 * @generated
	 */
	VarDeclaration createVarDeclaration();

	/**
	 * Returns a new object of class '<em>Version Info</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Version Info</em>'.
	 * @generated
	 */
	VersionInfo createVersionInfo();

	/**
	 * Returns a new object of class '<em>With</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>With</em>'.
	 * @generated
	 */
	With createWith();

	/**
	 * Returns a new object of class '<em>Member Var Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Member Var Declaration</em>'.
	 * @generated
	 */
	MemberVarDeclaration createMemberVarDeclaration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	LibraryElementPackage getLibraryElementPackage();

} //LibraryElementFactory
