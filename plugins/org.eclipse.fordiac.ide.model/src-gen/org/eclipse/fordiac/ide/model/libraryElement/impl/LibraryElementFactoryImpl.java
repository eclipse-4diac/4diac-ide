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
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import java.util.List;
import java.util.stream.Stream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import org.eclipse.draw2d.geometry.Point;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.ArraySize;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CFBInstance;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Comment;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection;
import org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.HiddenElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.LocalVariable;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.MappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.OriginalSource;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OtherMethod;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.STFunction;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.eclipse.fordiac.ide.model.libraryElement.STMethod;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.libraryElement.With;

import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

import org.eclipse.gef.commands.CommandStack;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LibraryElementFactoryImpl extends EFactoryImpl implements LibraryElementFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LibraryElementFactory init() {
		try {
			LibraryElementFactory theLibraryElementFactory = (LibraryElementFactory)EPackage.Registry.INSTANCE.getEFactory(LibraryElementPackage.eNS_URI);
			if (theLibraryElementFactory != null) {
				return theLibraryElementFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LibraryElementFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryElementFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case LibraryElementPackage.ADAPTER_CONNECTION: return createAdapterConnection();
			case LibraryElementPackage.ADAPTER_DECLARATION: return createAdapterDeclaration();
			case LibraryElementPackage.ADAPTER_FB: return createAdapterFB();
			case LibraryElementPackage.ADAPTER_TYPE: return createAdapterType();
			case LibraryElementPackage.APPLICATION: return createApplication();
			case LibraryElementPackage.ARRAY_SIZE: return createArraySize();
			case LibraryElementPackage.ATTRIBUTE: return createAttribute();
			case LibraryElementPackage.ATTRIBUTE_DECLARATION: return createAttributeDeclaration();
			case LibraryElementPackage.BASE_FB_TYPE: return createBaseFBType();
			case LibraryElementPackage.BASIC_FB_TYPE: return createBasicFBType();
			case LibraryElementPackage.AUTOMATION_SYSTEM: return createAutomationSystem();
			case LibraryElementPackage.CFB_INSTANCE: return createCFBInstance();
			case LibraryElementPackage.COLOR: return createColor();
			case LibraryElementPackage.COLORIZABLE_ELEMENT: return createColorizableElement();
			case LibraryElementPackage.COMMENT: return createComment();
			case LibraryElementPackage.COMMUNICATION_CHANNEL: return createCommunicationChannel();
			case LibraryElementPackage.COMMUNICATION_MAPPING_TARGET: return createCommunicationMappingTarget();
			case LibraryElementPackage.COMPILER: return createCompiler();
			case LibraryElementPackage.COMPILER_INFO: return createCompilerInfo();
			case LibraryElementPackage.COMPOSITE_FB_TYPE: return createCompositeFBType();
			case LibraryElementPackage.CONFIGURABLE_OBJECT: return createConfigurableObject();
			case LibraryElementPackage.CONFIGURABLE_MOVE_FB: return createConfigurableMoveFB();
			case LibraryElementPackage.CONNECTION_ROUTING_DATA: return createConnectionRoutingData();
			case LibraryElementPackage.DATA_CONNECTION: return createDataConnection();
			case LibraryElementPackage.DEMULTIPLEXER: return createDemultiplexer();
			case LibraryElementPackage.DEVICE: return createDevice();
			case LibraryElementPackage.DEVICE_TYPE: return createDeviceType();
			case LibraryElementPackage.EC_ACTION: return createECAction();
			case LibraryElementPackage.ECC: return createECC();
			case LibraryElementPackage.EC_STATE: return createECState();
			case LibraryElementPackage.EC_TRANSITION: return createECTransition();
			case LibraryElementPackage.ERROR_MARKER_DATA_TYPE: return createErrorMarkerDataType();
			case LibraryElementPackage.ERROR_MARKER_FBN_ELEMENT: return createErrorMarkerFBNElement();
			case LibraryElementPackage.ERROR_MARKER_INTERFACE: return createErrorMarkerInterface();
			case LibraryElementPackage.EVENT: return createEvent();
			case LibraryElementPackage.EVENT_CONNECTION: return createEventConnection();
			case LibraryElementPackage.FB: return createFB();
			case LibraryElementPackage.FB_NETWORK: return createFBNetwork();
			case LibraryElementPackage.FB_TYPE: return createFBType();
			case LibraryElementPackage.FUNCTION_FB_TYPE: return createFunctionFBType();
			case LibraryElementPackage.GLOBAL_CONSTANTS: return createGlobalConstants();
			case LibraryElementPackage.GROUP: return createGroup();
			case LibraryElementPackage.HIDDEN_ELEMENT: return createHiddenElement();
			case LibraryElementPackage.IDENTIFICATION: return createIdentification();
			case LibraryElementPackage.IMPORT: return createImport();
			case LibraryElementPackage.INPUT_PRIMITIVE: return createInputPrimitive();
			case LibraryElementPackage.INTERFACE_LIST: return createInterfaceList();
			case LibraryElementPackage.LIBRARY_ELEMENT: return createLibraryElement();
			case LibraryElementPackage.LINK: return createLink();
			case LibraryElementPackage.LOCAL_VARIABLE: return createLocalVariable();
			case LibraryElementPackage.MAPPING: return createMapping();
			case LibraryElementPackage.MAPPING_TARGET: return createMappingTarget();
			case LibraryElementPackage.MEMBER_VAR_DECLARATION: return createMemberVarDeclaration();
			case LibraryElementPackage.MULTIPLEXER: return createMultiplexer();
			case LibraryElementPackage.ORIGINAL_SOURCE: return createOriginalSource();
			case LibraryElementPackage.OTHER_ALGORITHM: return createOtherAlgorithm();
			case LibraryElementPackage.OTHER_METHOD: return createOtherMethod();
			case LibraryElementPackage.OUTPUT_PRIMITIVE: return createOutputPrimitive();
			case LibraryElementPackage.POSITION: return createPosition();
			case LibraryElementPackage.POSITIONABLE_ELEMENT: return createPositionableElement();
			case LibraryElementPackage.PRIMITIVE: return createPrimitive();
			case LibraryElementPackage.RESOURCE: return createResource();
			case LibraryElementPackage.RESOURCE_TYPE_NAME: return createResourceTypeName();
			case LibraryElementPackage.RESOURCE_TYPE: return createResourceType();
			case LibraryElementPackage.RESOURCE_TYPE_FB: return createResourceTypeFB();
			case LibraryElementPackage.SEGMENT: return createSegment();
			case LibraryElementPackage.SEGMENT_TYPE: return createSegmentType();
			case LibraryElementPackage.SERVICE: return createService();
			case LibraryElementPackage.SERVICE_SEQUENCE: return createServiceSequence();
			case LibraryElementPackage.SERVICE_TRANSACTION: return createServiceTransaction();
			case LibraryElementPackage.SERVICE_INTERFACE: return createServiceInterface();
			case LibraryElementPackage.SERVICE_INTERFACE_FB_TYPE: return createServiceInterfaceFBType();
			case LibraryElementPackage.SIMPLE_FB_TYPE: return createSimpleFBType();
			case LibraryElementPackage.ST_ALGORITHM: return createSTAlgorithm();
			case LibraryElementPackage.ST_FUNCTION: return createSTFunction();
			case LibraryElementPackage.ST_FUNCTION_BODY: return createSTFunctionBody();
			case LibraryElementPackage.ST_METHOD: return createSTMethod();
			case LibraryElementPackage.STRUCT_MANIPULATOR: return createStructManipulator();
			case LibraryElementPackage.SUB_APP_TYPE: return createSubAppType();
			case LibraryElementPackage.SYSTEM_CONFIGURATION: return createSystemConfiguration();
			case LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT: return createTypedConfigureableObject();
			case LibraryElementPackage.TYPED_SUB_APP: return createTypedSubApp();
			case LibraryElementPackage.UNTYPED_SUB_APP: return createUntypedSubApp();
			case LibraryElementPackage.VALUE: return createValue();
			case LibraryElementPackage.VAR_DECLARATION: return createVarDeclaration();
			case LibraryElementPackage.VERSION_INFO: return createVersionInfo();
			case LibraryElementPackage.WITH: return createWith();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case LibraryElementPackage.LANGUAGE:
				return createLanguageFromString(eDataType, initialValue);
			case LibraryElementPackage.COMMAND_STACK:
				return createCommandStackFromString(eDataType, initialValue);
			case LibraryElementPackage.IFILE:
				return createIFileFromString(eDataType, initialValue);
			case LibraryElementPackage.INTERFACE_ELEMENT_STREAM:
				return createInterfaceElementStreamFromString(eDataType, initialValue);
			case LibraryElementPackage.IPROJECT:
				return createIProjectFromString(eDataType, initialValue);
			case LibraryElementPackage.NAMED_ELEMENT_STREAM:
				return createNamedElementStreamFromString(eDataType, initialValue);
			case LibraryElementPackage.POINT:
				return createPointFromString(eDataType, initialValue);
			case LibraryElementPackage.TYPE_ENTRY:
				return createTypeEntryFromString(eDataType, initialValue);
			case LibraryElementPackage.TYPE_LIBRARY:
				return createTypeLibraryFromString(eDataType, initialValue);
			case LibraryElementPackage.VAR_DECL_LIST:
				return createVarDeclListFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case LibraryElementPackage.LANGUAGE:
				return convertLanguageToString(eDataType, instanceValue);
			case LibraryElementPackage.COMMAND_STACK:
				return convertCommandStackToString(eDataType, instanceValue);
			case LibraryElementPackage.IFILE:
				return convertIFileToString(eDataType, instanceValue);
			case LibraryElementPackage.INTERFACE_ELEMENT_STREAM:
				return convertInterfaceElementStreamToString(eDataType, instanceValue);
			case LibraryElementPackage.IPROJECT:
				return convertIProjectToString(eDataType, instanceValue);
			case LibraryElementPackage.NAMED_ELEMENT_STREAM:
				return convertNamedElementStreamToString(eDataType, instanceValue);
			case LibraryElementPackage.POINT:
				return convertPointToString(eDataType, instanceValue);
			case LibraryElementPackage.TYPE_ENTRY:
				return convertTypeEntryToString(eDataType, instanceValue);
			case LibraryElementPackage.TYPE_LIBRARY:
				return convertTypeLibraryToString(eDataType, instanceValue);
			case LibraryElementPackage.VAR_DECL_LIST:
				return convertVarDeclListToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterConnection createAdapterConnection() {
		AdapterConnectionImpl adapterConnection = new AdapterConnectionImpl();
		return adapterConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterDeclaration createAdapterDeclaration() {
		AdapterDeclarationImpl adapterDeclaration = new AdapterDeclarationImpl();
		return adapterDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterFB createAdapterFB() {
		AdapterFBImpl adapterFB = new AdapterFBImpl();
		return adapterFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AdapterType createAdapterType() {
		AdapterTypeImpl adapterType = new AdapterTypeImpl();
		return adapterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Application createApplication() {
		ApplicationImpl application = new ApplicationImpl();
		return application;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ArraySize createArraySize() {
		ArraySizeImpl arraySize = new ArraySizeImpl();
		return arraySize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AttributeDeclaration createAttributeDeclaration() {
		AttributeDeclarationImpl attributeDeclaration = new AttributeDeclarationImpl();
		return attributeDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BaseFBType createBaseFBType() {
		BaseFBTypeImpl baseFBType = new BaseFBTypeImpl();
		return baseFBType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BasicFBType createBasicFBType() {
		BasicFBTypeImpl basicFBType = new BasicFBTypeImpl();
		return basicFBType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AutomationSystem createAutomationSystem() {
		AutomationSystemImpl automationSystem = new AutomationSystemImpl();
		return automationSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CFBInstance createCFBInstance() {
		CFBInstanceImpl cfbInstance = new CFBInstanceImpl();
		return cfbInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Color createColor() {
		ColorImpl color = new ColorImpl();
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ColorizableElement createColorizableElement() {
		ColorizableElementImpl colorizableElement = new ColorizableElementImpl();
		return colorizableElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Comment createComment() {
		CommentImpl comment = new CommentImpl();
		return comment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommunicationChannel createCommunicationChannel() {
		CommunicationChannelImpl communicationChannel = new CommunicationChannelImpl();
		return communicationChannel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommunicationMappingTarget createCommunicationMappingTarget() {
		CommunicationMappingTargetImpl communicationMappingTarget = new CommunicationMappingTargetImpl();
		return communicationMappingTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public org.eclipse.fordiac.ide.model.libraryElement.Compiler createCompiler() {
		CompilerImpl compiler = new CompilerImpl();
		return compiler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompilerInfo createCompilerInfo() {
		CompilerInfoImpl compilerInfo = new CompilerInfoImpl();
		return compilerInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompositeFBType createCompositeFBType() {
		CompositeFBTypeImpl compositeFBType = new CompositeFBTypeImpl();
		return compositeFBType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConfigurableObject createConfigurableObject() {
		ConfigurableObjectImpl configurableObject = new ConfigurableObjectImpl();
		return configurableObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConfigurableMoveFB createConfigurableMoveFB() {
		ConfigurableMoveFBImpl configurableMoveFB = new ConfigurableMoveFBImpl();
		return configurableMoveFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ConnectionRoutingData createConnectionRoutingData() {
		ConnectionRoutingDataImpl connectionRoutingData = new ConnectionRoutingDataImpl();
		return connectionRoutingData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataConnection createDataConnection() {
		DataConnectionImpl dataConnection = new DataConnectionImpl();
		return dataConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Demultiplexer createDemultiplexer() {
		DemultiplexerImpl demultiplexer = new DemultiplexerImpl();
		return demultiplexer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Device createDevice() {
		DeviceImpl device = new DeviceImpl();
		return device;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeviceType createDeviceType() {
		DeviceTypeImpl deviceType = new DeviceTypeImpl();
		return deviceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ECAction createECAction() {
		ECActionImpl ecAction = new ECActionImpl();
		return ecAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ECC createECC() {
		ECCImpl ecc = new ECCImpl();
		return ecc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ECState createECState() {
		ECStateImpl ecState = new ECStateImpl();
		return ecState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ECTransition createECTransition() {
		ECTransitionImpl ecTransition = new ECTransitionImpl();
		return ecTransition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ErrorMarkerDataType createErrorMarkerDataType() {
		ErrorMarkerDataTypeImpl errorMarkerDataType = new ErrorMarkerDataTypeImpl();
		return errorMarkerDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ErrorMarkerFBNElement createErrorMarkerFBNElement() {
		ErrorMarkerFBNElementImpl errorMarkerFBNElement = new ErrorMarkerFBNElementImpl();
		return errorMarkerFBNElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ErrorMarkerInterface createErrorMarkerInterface() {
		ErrorMarkerInterfaceImpl errorMarkerInterface = new ErrorMarkerInterfaceImpl();
		return errorMarkerInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Event createEvent() {
		EventImpl event = new EventImpl();
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EventConnection createEventConnection() {
		EventConnectionImpl eventConnection = new EventConnectionImpl();
		return eventConnection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FB createFB() {
		FBImpl fb = new FBImpl();
		return fb;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBNetwork createFBNetwork() {
		FBNetworkImpl fbNetwork = new FBNetworkImpl();
		return fbNetwork;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBType createFBType() {
		FBTypeImpl fbType = new FBTypeImpl();
		return fbType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FunctionFBType createFunctionFBType() {
		FunctionFBTypeImpl functionFBType = new FunctionFBTypeImpl();
		return functionFBType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GlobalConstants createGlobalConstants() {
		GlobalConstantsImpl globalConstants = new GlobalConstantsImpl();
		return globalConstants;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Group createGroup() {
		GroupImpl group = new GroupImpl();
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public HiddenElement createHiddenElement() {
		HiddenElementImpl hiddenElement = new HiddenElementImpl();
		return hiddenElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Identification createIdentification() {
		IdentificationImpl identification = new IdentificationImpl();
		return identification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Import createImport() {
		ImportImpl import_ = new ImportImpl();
		return import_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InputPrimitive createInputPrimitive() {
		InputPrimitiveImpl inputPrimitive = new InputPrimitiveImpl();
		return inputPrimitive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InterfaceList createInterfaceList() {
		InterfaceListImpl interfaceList = new InterfaceListImpl();
		return interfaceList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LibraryElement createLibraryElement() {
		LibraryElementImpl libraryElement = new LibraryElementImpl();
		return libraryElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Link createLink() {
		LinkImpl link = new LinkImpl();
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LocalVariable createLocalVariable() {
		LocalVariableImpl localVariable = new LocalVariableImpl();
		return localVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Mapping createMapping() {
		MappingImpl mapping = new MappingImpl();
		return mapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MappingTarget createMappingTarget() {
		MappingTargetImpl mappingTarget = new MappingTargetImpl();
		return mappingTarget;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Multiplexer createMultiplexer() {
		MultiplexerImpl multiplexer = new MultiplexerImpl();
		return multiplexer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OriginalSource createOriginalSource() {
		OriginalSourceImpl originalSource = new OriginalSourceImpl();
		return originalSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OtherAlgorithm createOtherAlgorithm() {
		OtherAlgorithmImpl otherAlgorithm = new OtherAlgorithmImpl();
		return otherAlgorithm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OtherMethod createOtherMethod() {
		OtherMethodImpl otherMethod = new OtherMethodImpl();
		return otherMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OutputPrimitive createOutputPrimitive() {
		OutputPrimitiveImpl outputPrimitive = new OutputPrimitiveImpl();
		return outputPrimitive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Position createPosition() {
		PositionImpl position = new PositionImpl();
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PositionableElement createPositionableElement() {
		PositionableElementImpl positionableElement = new PositionableElementImpl();
		return positionableElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Primitive createPrimitive() {
		PrimitiveImpl primitive = new PrimitiveImpl();
		return primitive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource createResource() {
		ResourceImpl resource = new ResourceImpl();
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceTypeName createResourceTypeName() {
		ResourceTypeNameImpl resourceTypeName = new ResourceTypeNameImpl();
		return resourceTypeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceType createResourceType() {
		ResourceTypeImpl resourceType = new ResourceTypeImpl();
		return resourceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceTypeFB createResourceTypeFB() {
		ResourceTypeFBImpl resourceTypeFB = new ResourceTypeFBImpl();
		return resourceTypeFB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Segment createSegment() {
		SegmentImpl segment = new SegmentImpl();
		return segment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SegmentType createSegmentType() {
		SegmentTypeImpl segmentType = new SegmentTypeImpl();
		return segmentType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Service createService() {
		ServiceImpl service = new ServiceImpl();
		return service;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ServiceSequence createServiceSequence() {
		ServiceSequenceImpl serviceSequence = new ServiceSequenceImpl();
		return serviceSequence;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ServiceTransaction createServiceTransaction() {
		ServiceTransactionImpl serviceTransaction = new ServiceTransactionImpl();
		return serviceTransaction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ServiceInterface createServiceInterface() {
		ServiceInterfaceImpl serviceInterface = new ServiceInterfaceImpl();
		return serviceInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ServiceInterfaceFBType createServiceInterfaceFBType() {
		ServiceInterfaceFBTypeImpl serviceInterfaceFBType = new ServiceInterfaceFBTypeImpl();
		return serviceInterfaceFBType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SimpleFBType createSimpleFBType() {
		SimpleFBTypeImpl simpleFBType = new SimpleFBTypeImpl();
		return simpleFBType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STAlgorithm createSTAlgorithm() {
		STAlgorithmImpl stAlgorithm = new STAlgorithmImpl();
		return stAlgorithm;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STFunction createSTFunction() {
		STFunctionImpl stFunction = new STFunctionImpl();
		return stFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STFunctionBody createSTFunctionBody() {
		STFunctionBodyImpl stFunctionBody = new STFunctionBodyImpl();
		return stFunctionBody;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STMethod createSTMethod() {
		STMethodImpl stMethod = new STMethodImpl();
		return stMethod;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StructManipulator createStructManipulator() {
		StructManipulatorImpl structManipulator = new StructManipulatorImpl();
		return structManipulator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SubAppType createSubAppType() {
		SubAppTypeImpl subAppType = new SubAppTypeImpl();
		return subAppType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SystemConfiguration createSystemConfiguration() {
		SystemConfigurationImpl systemConfiguration = new SystemConfigurationImpl();
		return systemConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypedConfigureableObject createTypedConfigureableObject() {
		TypedConfigureableObjectImpl typedConfigureableObject = new TypedConfigureableObjectImpl();
		return typedConfigureableObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypedSubApp createTypedSubApp() {
		TypedSubAppImpl typedSubApp = new TypedSubAppImpl();
		return typedSubApp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UntypedSubApp createUntypedSubApp() {
		UntypedSubAppImpl untypedSubApp = new UntypedSubAppImpl();
		return untypedSubApp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Value createValue() {
		ValueImpl value = new ValueImpl();
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VarDeclaration createVarDeclaration() {
		VarDeclarationImpl varDeclaration = new VarDeclarationImpl();
		return varDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public VersionInfo createVersionInfo() {
		VersionInfoImpl versionInfo = new VersionInfoImpl();
		return versionInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public With createWith() {
		WithImpl with = new WithImpl();
		return with;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MemberVarDeclaration createMemberVarDeclaration() {
		MemberVarDeclarationImpl memberVarDeclaration = new MemberVarDeclarationImpl();
		return memberVarDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Language createLanguageFromString(EDataType eDataType, String initialValue) {
		Language result = Language.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLanguageToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CommandStack createCommandStackFromString(EDataType eDataType, String initialValue) {
		return (CommandStack)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCommandStackToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IFile createIFileFromString(EDataType eDataType, String initialValue) {
		return (IFile)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIFileToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Stream<IInterfaceElement> createInterfaceElementStreamFromString(EDataType eDataType, String initialValue) {
		return (Stream<IInterfaceElement>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertInterfaceElementStreamToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IProject createIProjectFromString(EDataType eDataType, String initialValue) {
		return (IProject)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertIProjectToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Stream<INamedElement> createNamedElementStreamFromString(EDataType eDataType, String initialValue) {
		return (Stream<INamedElement>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertNamedElementStreamToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Point createPointFromString(EDataType eDataType, String initialValue) {
		return (Point)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPointToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeEntry createTypeEntryFromString(EDataType eDataType, String initialValue) {
		return (TypeEntry)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypeEntryToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeLibrary createTypeLibraryFromString(EDataType eDataType, String initialValue) {
		return (TypeLibrary)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTypeLibraryToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public List<VarDeclaration> createVarDeclListFromString(EDataType eDataType, String initialValue) {
		return (List<VarDeclaration>)super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVarDeclListToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LibraryElementPackage getLibraryElementPackage() {
		return (LibraryElementPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LibraryElementPackage getPackage() {
		return LibraryElementPackage.eINSTANCE;
	}

} //LibraryElementFactoryImpl
