/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement.impl;

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
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Color;
import org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement;
import org.eclipse.fordiac.ide.model.libraryElement.CompilableType;
import org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
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
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Language;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Link;
import org.eclipse.fordiac.ide.model.libraryElement.LocalVariable;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.Multiplexer;
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.PositionableElement;
import org.eclipse.fordiac.ide.model.libraryElement.Primitive;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;
import org.eclipse.fordiac.ide.model.libraryElement.SegmentType;
import org.eclipse.fordiac.ide.model.libraryElement.Service;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.gef.commands.CommandStack;

/** <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated */
public class LibraryElementFactoryImpl extends EFactoryImpl implements LibraryElementFactory {
	/** Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public static LibraryElementFactory init() {
		try {
			final LibraryElementFactory theLibraryElementFactory = (LibraryElementFactory) EPackage.Registry.INSTANCE
					.getEFactory(LibraryElementPackage.eNS_URI);
			if (theLibraryElementFactory != null) {
				return theLibraryElementFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LibraryElementFactoryImpl();
	}

	/** Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public LibraryElementFactoryImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EObject create(final EClass eClass) {
		switch (eClass.getClassifierID()) {
		case LibraryElementPackage.ADAPTER_DECLARATION:
			return createAdapterDeclaration();
		case LibraryElementPackage.ADAPTER_TYPE:
			return createAdapterType();
		case LibraryElementPackage.APPLICATION:
			return createApplication();
		case LibraryElementPackage.BASIC_FB_TYPE:
			return createBasicFBType();
		case LibraryElementPackage.COMPILER_INFO:
			return createCompilerInfo();
		case LibraryElementPackage.COMPILER:
			return createCompiler();
		case LibraryElementPackage.DEVICE:
			return createDevice();
		case LibraryElementPackage.DEVICE_TYPE:
			return createDeviceType();
		case LibraryElementPackage.EC_ACTION:
			return createECAction();
		case LibraryElementPackage.ECC:
			return createECC();
		case LibraryElementPackage.EC_STATE:
			return createECState();
		case LibraryElementPackage.EC_TRANSITION:
			return createECTransition();
		case LibraryElementPackage.EVENT:
			return createEvent();
		case LibraryElementPackage.FB:
			return createFB();
		case LibraryElementPackage.FB_NETWORK_ELEMENT:
			return createFBNetworkElement();
		case LibraryElementPackage.SUB_APP:
			return createSubApp();
		case LibraryElementPackage.FB_TYPE:
			return createFBType();
		case LibraryElementPackage.IDENTIFICATION:
			return createIdentification();
		case LibraryElementPackage.INPUT_PRIMITIVE:
			return createInputPrimitive();
		case LibraryElementPackage.INTERFACE_LIST:
			return createInterfaceList();
		case LibraryElementPackage.LINK:
			return createLink();
		case LibraryElementPackage.MAPPING:
			return createMapping();
		case LibraryElementPackage.OTHER_ALGORITHM:
			return createOtherAlgorithm();
		case LibraryElementPackage.OUTPUT_PRIMITIVE:
			return createOutputPrimitive();
		case LibraryElementPackage.ATTRIBUTE:
			return createAttribute();
		case LibraryElementPackage.RESOURCE:
			return createResource();
		case LibraryElementPackage.RESOURCE_TYPE_NAME:
			return createResourceTypeName();
		case LibraryElementPackage.RESOURCE_TYPE:
			return createResourceType();
		case LibraryElementPackage.SEGMENT:
			return createSegment();
		case LibraryElementPackage.SERVICE_SEQUENCE:
			return createServiceSequence();
		case LibraryElementPackage.SERVICE_TRANSACTION:
			return createServiceTransaction();
		case LibraryElementPackage.SERVICE_INTERFACE_FB_TYPE:
			return createServiceInterfaceFBType();
		case LibraryElementPackage.ST_ALGORITHM:
			return createSTAlgorithm();
		case LibraryElementPackage.FB_NETWORK:
			return createFBNetwork();
		case LibraryElementPackage.SUB_APP_TYPE:
			return createSubAppType();
		case LibraryElementPackage.AUTOMATION_SYSTEM:
			return createAutomationSystem();
		case LibraryElementPackage.VAR_DECLARATION:
			return createVarDeclaration();
		case LibraryElementPackage.VERSION_INFO:
			return createVersionInfo();
		case LibraryElementPackage.WITH:
			return createWith();
		case LibraryElementPackage.LIBRARY_ELEMENT:
			return createLibraryElement();
		case LibraryElementPackage.COMPILABLE_TYPE:
			return createCompilableType();
		case LibraryElementPackage.CONFIGURABLE_OBJECT:
			return createConfigurableObject();
		case LibraryElementPackage.COMPOSITE_FB_TYPE:
			return createCompositeFBType();
		case LibraryElementPackage.DATA_CONNECTION:
			return createDataConnection();
		case LibraryElementPackage.EVENT_CONNECTION:
			return createEventConnection();
		case LibraryElementPackage.ADAPTER_CONNECTION:
			return createAdapterConnection();
		case LibraryElementPackage.SERVICE_INTERFACE:
			return createServiceInterface();
		case LibraryElementPackage.VALUE:
			return createValue();
		case LibraryElementPackage.SYSTEM_CONFIGURATION:
			return createSystemConfiguration();
		case LibraryElementPackage.RESOURCE_TYPE_FB:
			return createResourceTypeFB();
		case LibraryElementPackage.SEGMENT_TYPE:
			return createSegmentType();
		case LibraryElementPackage.ADAPTER_FB_TYPE:
			return createAdapterFBType();
		case LibraryElementPackage.ADAPTER_EVENT:
			return createAdapterEvent();
		case LibraryElementPackage.SERVICE:
			return createService();
		case LibraryElementPackage.TYPED_CONFIGUREABLE_OBJECT:
			return createTypedConfigureableObject();
		case LibraryElementPackage.ADAPTER_FB:
			return createAdapterFB();
		case LibraryElementPackage.PRIMITIVE:
			return createPrimitive();
		case LibraryElementPackage.POSITIONABLE_ELEMENT:
			return createPositionableElement();
		case LibraryElementPackage.POSITION:
			return createPosition();
		case LibraryElementPackage.COLOR:
			return createColor();
		case LibraryElementPackage.COLORIZABLE_ELEMENT:
			return createColorizableElement();
		case LibraryElementPackage.ATTRIBUTE_DECLARATION:
			return createAttributeDeclaration();
		case LibraryElementPackage.SIMPLE_FB_TYPE:
			return createSimpleFBType();
		case LibraryElementPackage.BASE_FB_TYPE:
			return createBaseFBType();
		case LibraryElementPackage.DEMULTIPLEXER:
			return createDemultiplexer();
		case LibraryElementPackage.MULTIPLEXER:
			return createMultiplexer();
		case LibraryElementPackage.LOCAL_VARIABLE:
			return createLocalVariable();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Object createFromString(final EDataType eDataType, final String initialValue) {
		switch (eDataType.getClassifierID()) {
		case LibraryElementPackage.LANGUAGE:
			return createLanguageFromString(eDataType, initialValue);
		case LibraryElementPackage.IPROJECT:
			return createIProjectFromString(eDataType, initialValue);
		case LibraryElementPackage.IFILE:
			return createIFileFromString(eDataType, initialValue);
		case LibraryElementPackage.COMMAND_STACK:
			return createCommandStackFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public String convertToString(final EDataType eDataType, final Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case LibraryElementPackage.LANGUAGE:
			return convertLanguageToString(eDataType, instanceValue);
		case LibraryElementPackage.IPROJECT:
			return convertIProjectToString(eDataType, instanceValue);
		case LibraryElementPackage.IFILE:
			return convertIFileToString(eDataType, instanceValue);
		case LibraryElementPackage.COMMAND_STACK:
			return convertCommandStackToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public AdapterDeclaration createAdapterDeclaration() {
		final AdapterDeclarationImpl adapterDeclaration = new AdapterDeclarationImpl();
		return adapterDeclaration;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public AdapterType createAdapterType() {
		final AdapterTypeImpl adapterType = new AdapterTypeImpl();
		return adapterType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Application createApplication() {
		final ApplicationImpl application = new ApplicationImpl();
		return application;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public BasicFBType createBasicFBType() {
		final BasicFBTypeImpl basicFBType = new BasicFBTypeImpl();
		return basicFBType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public CompilerInfo createCompilerInfo() {
		final CompilerInfoImpl compilerInfo = new CompilerInfoImpl();
		return compilerInfo;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public org.eclipse.fordiac.ide.model.libraryElement.Compiler createCompiler() {
		final CompilerImpl compiler = new CompilerImpl();
		return compiler;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ConnectionRoutingData createConnectionRoutingData() {
		final ConnectionRoutingDataImpl connectionRoutingData = new ConnectionRoutingDataImpl();
		return connectionRoutingData;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Device createDevice() {
		final DeviceImpl device = new DeviceImpl();
		return device;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public DeviceType createDeviceType() {
		final DeviceTypeImpl deviceType = new DeviceTypeImpl();
		return deviceType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ECAction createECAction() {
		final ECActionImpl ecAction = new ECActionImpl();
		return ecAction;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ECC createECC() {
		final ECCImpl ecc = new ECCImpl();
		return ecc;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ECState createECState() {
		final ECStateImpl ecState = new ECStateImpl();
		return ecState;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ECTransition createECTransition() {
		final ECTransitionImpl ecTransition = new ECTransitionImpl();
		return ecTransition;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Event createEvent() {
		final EventImpl event = new EventImpl();
		return event;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public FBNetwork createFBNetwork() {
		final FBNetworkImpl fbNetwork = new FBNetworkImpl();
		return fbNetwork;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public FB createFB() {
		final FBImpl fb = new FBImpl();
		return fb;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public FBNetworkElement createFBNetworkElement() {
		final FBNetworkElementImpl fbNetworkElement = new FBNetworkElementImpl();
		return fbNetworkElement;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public FBType createFBType() {
		final FBTypeImpl fbType = new FBTypeImpl();
		return fbType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Identification createIdentification() {
		final IdentificationImpl identification = new IdentificationImpl();
		return identification;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public InputPrimitive createInputPrimitive() {
		final InputPrimitiveImpl inputPrimitive = new InputPrimitiveImpl();
		return inputPrimitive;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public InterfaceList createInterfaceList() {
		final InterfaceListImpl interfaceList = new InterfaceListImpl();
		return interfaceList;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Link createLink() {
		final LinkImpl link = new LinkImpl();
		return link;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Mapping createMapping() {
		final MappingImpl mapping = new MappingImpl();
		return mapping;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public OtherAlgorithm createOtherAlgorithm() {
		final OtherAlgorithmImpl otherAlgorithm = new OtherAlgorithmImpl();
		return otherAlgorithm;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public OutputPrimitive createOutputPrimitive() {
		final OutputPrimitiveImpl outputPrimitive = new OutputPrimitiveImpl();
		return outputPrimitive;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Resource createResource() {
		final ResourceImpl resource = new ResourceImpl();
		return resource;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ResourceTypeName createResourceTypeName() {
		final ResourceTypeNameImpl resourceTypeName = new ResourceTypeNameImpl();
		return resourceTypeName;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ResourceType createResourceType() {
		final ResourceTypeImpl resourceType = new ResourceTypeImpl();
		return resourceType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Segment createSegment() {
		final SegmentImpl segment = new SegmentImpl();
		return segment;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ServiceSequence createServiceSequence() {
		final ServiceSequenceImpl serviceSequence = new ServiceSequenceImpl();
		return serviceSequence;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ServiceTransaction createServiceTransaction() {
		final ServiceTransactionImpl serviceTransaction = new ServiceTransactionImpl();
		return serviceTransaction;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ServiceInterfaceFBType createServiceInterfaceFBType() {
		final ServiceInterfaceFBTypeImpl serviceInterfaceFBType = new ServiceInterfaceFBTypeImpl();
		return serviceInterfaceFBType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public STAlgorithm createSTAlgorithm() {
		final STAlgorithmImpl stAlgorithm = new STAlgorithmImpl();
		return stAlgorithm;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public SubApp createSubApp() {
		final SubAppImpl subApp = new SubAppImpl();
		return subApp;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public SubAppType createSubAppType() {
		final SubAppTypeImpl subAppType = new SubAppTypeImpl();
		return subAppType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public AutomationSystem createAutomationSystem() {
		final AutomationSystemImpl automationSystem = new AutomationSystemImpl();
		return automationSystem;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public VarDeclaration createVarDeclaration() {
		final VarDeclarationImpl varDeclaration = new VarDeclarationImpl();
		return varDeclaration;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public VersionInfo createVersionInfo() {
		final VersionInfoImpl versionInfo = new VersionInfoImpl();
		return versionInfo;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public With createWith() {
		final WithImpl with = new WithImpl();
		return with;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public LibraryElement createLibraryElement() {
		final LibraryElementImpl libraryElement = new LibraryElementImpl();
		return libraryElement;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public CompilableType createCompilableType() {
		final CompilableTypeImpl compilableType = new CompilableTypeImpl();
		return compilableType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ConfigurableObject createConfigurableObject() {
		final ConfigurableObjectImpl configurableObject = new ConfigurableObjectImpl();
		return configurableObject;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public CompositeFBType createCompositeFBType() {
		final CompositeFBTypeImpl compositeFBType = new CompositeFBTypeImpl();
		return compositeFBType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public DataConnection createDataConnection() {
		final DataConnectionImpl dataConnection = new DataConnectionImpl();
		return dataConnection;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public EventConnection createEventConnection() {
		final EventConnectionImpl eventConnection = new EventConnectionImpl();
		return eventConnection;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public AdapterConnection createAdapterConnection() {
		final AdapterConnectionImpl adapterConnection = new AdapterConnectionImpl();
		return adapterConnection;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ServiceInterface createServiceInterface() {
		final ServiceInterfaceImpl serviceInterface = new ServiceInterfaceImpl();
		return serviceInterface;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Value createValue() {
		final ValueImpl value = new ValueImpl();
		return value;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public SystemConfiguration createSystemConfiguration() {
		final SystemConfigurationImpl systemConfiguration = new SystemConfigurationImpl();
		return systemConfiguration;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ResourceTypeFB createResourceTypeFB() {
		final ResourceTypeFBImpl resourceTypeFB = new ResourceTypeFBImpl();
		return resourceTypeFB;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public SegmentType createSegmentType() {
		final SegmentTypeImpl segmentType = new SegmentTypeImpl();
		return segmentType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public AdapterFBType createAdapterFBType() {
		final AdapterFBTypeImpl adapterFBType = new AdapterFBTypeImpl();
		return adapterFBType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public AdapterEvent createAdapterEvent() {
		final AdapterEventImpl adapterEvent = new AdapterEventImpl();
		return adapterEvent;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Service createService() {
		final ServiceImpl service = new ServiceImpl();
		return service;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public TypedConfigureableObject createTypedConfigureableObject() {
		final TypedConfigureableObjectImpl typedConfigureableObject = new TypedConfigureableObjectImpl();
		return typedConfigureableObject;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public AdapterFB createAdapterFB() {
		final AdapterFBImpl adapterFB = new AdapterFBImpl();
		return adapterFB;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Primitive createPrimitive() {
		final PrimitiveImpl primitive = new PrimitiveImpl();
		return primitive;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public PositionableElement createPositionableElement() {
		final PositionableElementImpl positionableElement = new PositionableElementImpl();
		return positionableElement;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Position createPosition() {
		final PositionImpl position = new PositionImpl();
		return position;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Color createColor() {
		final ColorImpl color = new ColorImpl();
		return color;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public ColorizableElement createColorizableElement() {
		final ColorizableElementImpl colorizableElement = new ColorizableElementImpl();
		return colorizableElement;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public AttributeDeclaration createAttributeDeclaration() {
		final AttributeDeclarationImpl attributeDeclaration = new AttributeDeclarationImpl();
		return attributeDeclaration;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public SimpleFBType createSimpleFBType() {
		final SimpleFBTypeImpl simpleFBType = new SimpleFBTypeImpl();
		return simpleFBType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public BaseFBType createBaseFBType() {
		final BaseFBTypeImpl baseFBType = new BaseFBTypeImpl();
		return baseFBType;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Demultiplexer createDemultiplexer() {
		final DemultiplexerImpl demultiplexer = new DemultiplexerImpl();
		return demultiplexer;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Multiplexer createMultiplexer() {
		final MultiplexerImpl multiplexer = new MultiplexerImpl();
		return multiplexer;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public LocalVariable createLocalVariable() {
		final LocalVariableImpl localVariable = new LocalVariableImpl();
		return localVariable;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public Attribute createAttribute() {
		final AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public Language createLanguageFromString(final EDataType eDataType, final String initialValue) {
		final Language result = Language.get(initialValue);
		if (result == null)
		 {
			throw new IllegalArgumentException(
					"The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		return result;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public String convertLanguageToString(final EDataType eDataType, final Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public IProject createIProjectFromString(final EDataType eDataType, final String initialValue) {
		return (IProject) super.createFromString(eDataType, initialValue);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public String convertIProjectToString(final EDataType eDataType, final Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public IFile createIFileFromString(final EDataType eDataType, final String initialValue) {
		return (IFile) super.createFromString(eDataType, initialValue);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public String convertIFileToString(final EDataType eDataType, final Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public CommandStack createCommandStackFromString(final EDataType eDataType, final String initialValue) {
		return (CommandStack) super.createFromString(eDataType, initialValue);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public String convertCommandStackToString(final EDataType eDataType, final Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public Point createPointFromString(final EDataType eDataType, final String initialValue) {
		return (Point) super.createFromString(eDataType, initialValue);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public String convertPointToString(final EDataType eDataType, final Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public LibraryElementPackage getLibraryElementPackage() {
		return (LibraryElementPackage) getEPackage();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated */
	@Deprecated
	public static LibraryElementPackage getPackage() {
		return LibraryElementPackage.eINSTANCE;
	}

} // LibraryElementFactoryImpl
