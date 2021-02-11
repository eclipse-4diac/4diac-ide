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
package org.eclipse.fordiac.ide.model.libraryElement.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
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
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
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
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.IVarElement;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
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
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.TypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.model.libraryElement.With;

/** <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage
 * @generated */
public class LibraryElementAdapterFactory extends AdapterFactoryImpl {
	/** The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	protected static LibraryElementPackage modelPackage;

	/** Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	public LibraryElementAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = LibraryElementPackage.eINSTANCE;
		}
	}

	/** Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
	 * implementation returns <code>true</code> if the object is either the model's package or is an instance object of
	 * the model. <!-- end-user-doc -->
	 * 
	 * @return whether this factory is applicable for the type of the object.
	 * @generated */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/** The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	protected LibraryElementSwitch<Adapter> modelSwitch = new LibraryElementSwitch<Adapter>() {
		@Override
		public Adapter caseAdapterDeclaration(AdapterDeclaration object) {
			return createAdapterDeclarationAdapter();
		}

		@Override
		public Adapter caseAdapterType(AdapterType object) {
			return createAdapterTypeAdapter();
		}

		@Override
		public Adapter caseAlgorithm(Algorithm object) {
			return createAlgorithmAdapter();
		}

		@Override
		public Adapter caseApplication(Application object) {
			return createApplicationAdapter();
		}

		@Override
		public Adapter caseBasicFBType(BasicFBType object) {
			return createBasicFBTypeAdapter();
		}

		@Override
		public Adapter caseCompilerInfo(CompilerInfo object) {
			return createCompilerInfoAdapter();
		}

		@Override
		public Adapter caseCompiler(org.eclipse.fordiac.ide.model.libraryElement.Compiler object) {
			return createCompilerAdapter();
		}

		@Override
		public Adapter caseConnection(Connection object) {
			return createConnectionAdapter();
		}

		@Override
		public Adapter caseDevice(Device object) {
			return createDeviceAdapter();
		}

		@Override
		public Adapter caseDeviceType(DeviceType object) {
			return createDeviceTypeAdapter();
		}

		@Override
		public Adapter caseECAction(ECAction object) {
			return createECActionAdapter();
		}

		@Override
		public Adapter caseECC(ECC object) {
			return createECCAdapter();
		}

		@Override
		public Adapter caseECState(ECState object) {
			return createECStateAdapter();
		}

		@Override
		public Adapter caseECTransition(ECTransition object) {
			return createECTransitionAdapter();
		}

		@Override
		public Adapter caseEvent(Event object) {
			return createEventAdapter();
		}

		@Override
		public Adapter caseFB(FB object) {
			return createFBAdapter();
		}

		@Override
		public Adapter caseFBNetworkElement(FBNetworkElement object) {
			return createFBNetworkElementAdapter();
		}

		@Override
		public Adapter caseSubApp(SubApp object) {
			return createSubAppAdapter();
		}

		@Override
		public Adapter caseFBType(FBType object) {
			return createFBTypeAdapter();
		}

		@Override
		public Adapter caseIdentification(Identification object) {
			return createIdentificationAdapter();
		}

		@Override
		public Adapter caseInputPrimitive(InputPrimitive object) {
			return createInputPrimitiveAdapter();
		}

		@Override
		public Adapter caseInterfaceList(InterfaceList object) {
			return createInterfaceListAdapter();
		}

		@Override
		public Adapter caseLink(Link object) {
			return createLinkAdapter();
		}

		@Override
		public Adapter caseMapping(Mapping object) {
			return createMappingAdapter();
		}

		@Override
		public Adapter caseOtherAlgorithm(OtherAlgorithm object) {
			return createOtherAlgorithmAdapter();
		}

		@Override
		public Adapter caseOutputPrimitive(OutputPrimitive object) {
			return createOutputPrimitiveAdapter();
		}

		@Override
		public Adapter caseAttribute(Attribute object) {
			return createAttributeAdapter();
		}

		@Override
		public Adapter caseResource(Resource object) {
			return createResourceAdapter();
		}

		@Override
		public Adapter caseResourceTypeName(ResourceTypeName object) {
			return createResourceTypeNameAdapter();
		}

		@Override
		public Adapter caseResourceType(ResourceType object) {
			return createResourceTypeAdapter();
		}

		@Override
		public Adapter caseSegment(Segment object) {
			return createSegmentAdapter();
		}

		@Override
		public Adapter caseServiceSequence(ServiceSequence object) {
			return createServiceSequenceAdapter();
		}

		@Override
		public Adapter caseServiceTransaction(ServiceTransaction object) {
			return createServiceTransactionAdapter();
		}

		@Override
		public Adapter caseServiceInterfaceFBType(ServiceInterfaceFBType object) {
			return createServiceInterfaceFBTypeAdapter();
		}

		@Override
		public Adapter caseSTAlgorithm(STAlgorithm object) {
			return createSTAlgorithmAdapter();
		}

		@Override
		public Adapter caseFBNetwork(FBNetwork object) {
			return createFBNetworkAdapter();
		}

		@Override
		public Adapter caseSubAppType(SubAppType object) {
			return createSubAppTypeAdapter();
		}

		@Override
		public Adapter caseAutomationSystem(AutomationSystem object) {
			return createAutomationSystemAdapter();
		}

		@Override
		public Adapter caseVarDeclaration(VarDeclaration object) {
			return createVarDeclarationAdapter();
		}

		@Override
		public Adapter caseVersionInfo(VersionInfo object) {
			return createVersionInfoAdapter();
		}

		@Override
		public Adapter caseWith(With object) {
			return createWithAdapter();
		}

		@Override
		public Adapter caseLibraryElement(LibraryElement object) {
			return createLibraryElementAdapter();
		}

		@Override
		public Adapter caseCompilableType(CompilableType object) {
			return createCompilableTypeAdapter();
		}

		@Override
		public Adapter caseConfigurableObject(ConfigurableObject object) {
			return createConfigurableObjectAdapter();
		}

		@Override
		public Adapter caseCompositeFBType(CompositeFBType object) {
			return createCompositeFBTypeAdapter();
		}

		@Override
		public Adapter caseTextAlgorithm(TextAlgorithm object) {
			return createTextAlgorithmAdapter();
		}

		@Override
		public Adapter caseDataConnection(DataConnection object) {
			return createDataConnectionAdapter();
		}

		@Override
		public Adapter caseEventConnection(EventConnection object) {
			return createEventConnectionAdapter();
		}

		@Override
		public Adapter caseAdapterConnection(AdapterConnection object) {
			return createAdapterConnectionAdapter();
		}

		@Override
		public Adapter caseServiceInterface(ServiceInterface object) {
			return createServiceInterfaceAdapter();
		}

		@Override
		public Adapter caseIInterfaceElement(IInterfaceElement object) {
			return createIInterfaceElementAdapter();
		}

		@Override
		public Adapter caseValue(Value object) {
			return createValueAdapter();
		}

		@Override
		public Adapter caseSystemConfiguration(SystemConfiguration object) {
			return createSystemConfigurationAdapter();
		}

		@Override
		public Adapter caseINamedElement(INamedElement object) {
			return createINamedElementAdapter();
		}

		@Override
		public Adapter caseResourceTypeFB(ResourceTypeFB object) {
			return createResourceTypeFBAdapter();
		}

		@Override
		public Adapter caseSegmentType(SegmentType object) {
			return createSegmentTypeAdapter();
		}

		@Override
		public Adapter caseAdapterFBType(AdapterFBType object) {
			return createAdapterFBTypeAdapter();
		}

		@Override
		public Adapter caseAdapterEvent(AdapterEvent object) {
			return createAdapterEventAdapter();
		}

		@Override
		public Adapter caseService(Service object) {
			return createServiceAdapter();
		}

		@Override
		public Adapter caseTypedConfigureableObject(TypedConfigureableObject object) {
			return createTypedConfigureableObjectAdapter();
		}

		@Override
		public Adapter caseAdapterFB(AdapterFB object) {
			return createAdapterFBAdapter();
		}

		@Override
		public Adapter casePrimitive(Primitive object) {
			return createPrimitiveAdapter();
		}

		@Override
		public Adapter casePositionableElement(PositionableElement object) {
			return createPositionableElementAdapter();
		}

		@Override
		public Adapter casePosition(Position object) {
			return createPositionAdapter();
		}

		@Override
		public Adapter caseColor(Color object) {
			return createColorAdapter();
		}

		@Override
		public Adapter caseColorizableElement(ColorizableElement object) {
			return createColorizableElementAdapter();
		}

		@Override
		public Adapter caseIVarElement(IVarElement object) {
			return createIVarElementAdapter();
		}

		@Override
		public Adapter caseAttributeDeclaration(AttributeDeclaration object) {
			return createAttributeDeclarationAdapter();
		}

		@Override
		public Adapter caseTypedElement(TypedElement object) {
			return createTypedElementAdapter();
		}

		@Override
		public Adapter caseSimpleFBType(SimpleFBType object) {
			return createSimpleFBTypeAdapter();
		}

		@Override
		public Adapter caseBaseFBType(BaseFBType object) {
			return createBaseFBTypeAdapter();
		}

		@Override
		public Adapter caseStructManipulator(StructManipulator object) {
			return createStructManipulatorAdapter();
		}

		@Override
		public Adapter caseDemultiplexer(Demultiplexer object) {
			return createDemultiplexerAdapter();
		}

		@Override
		public Adapter caseMultiplexer(Multiplexer object) {
			return createMultiplexerAdapter();
		}

		@Override
		public Adapter caseLocalVariable(LocalVariable object) {
			return createLocalVariableAdapter();
		}

		@Override
		public Adapter caseDataType(DataType object) {
			return createDataTypeAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/** Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration <em>Adapter Declaration</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
	 * @generated */
	public Adapter createAdapterDeclarationAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterType
	 * <em>Adapter Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterType
	 * @generated */
	public Adapter createAdapterTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Algorithm
	 * <em>Algorithm</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Algorithm
	 * @generated */
	public Adapter createAlgorithmAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Application
	 * <em>Application</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Application
	 * @generated */
	public Adapter createApplicationAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
	 * <em>Basic FB Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
	 * @generated */
	public Adapter createBasicFBTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo
	 * <em>Compiler Info</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo
	 * @generated */
	public Adapter createCompilerInfoAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler
	 * <em>Compiler</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler
	 * @generated */
	public Adapter createCompilerAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection
	 * <em>Connection</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection
	 * @generated */
	public Adapter createConnectionAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Device
	 * <em>Device</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Device
	 * @generated */
	public Adapter createDeviceAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType
	 * <em>Device Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType
	 * @generated */
	public Adapter createDeviceTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction
	 * <em>EC Action</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECAction
	 * @generated */
	public Adapter createECActionAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC
	 * <em>ECC</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC
	 * @generated */
	public Adapter createECCAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState <em>EC
	 * State</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState
	 * @generated */
	public Adapter createECStateAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition
	 * <em>EC Transition</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition
	 * @generated */
	public Adapter createECTransitionAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Event
	 * <em>Event</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Event
	 * @generated */
	public Adapter createEventAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork
	 * <em>FB Network</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork
	 * @generated */
	public Adapter createFBNetworkAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.FB
	 * <em>FB</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FB
	 * @generated */
	public Adapter createFBAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement <em>FB Network Element</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement
	 * @generated */
	public Adapter createFBNetworkElementAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.FBType <em>FB
	 * Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBType
	 * @generated */
	public Adapter createFBTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification
	 * <em>Identification</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification
	 * @generated */
	public Adapter createIdentificationAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive
	 * <em>Input Primitive</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive
	 * @generated */
	public Adapter createInputPrimitiveAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList
	 * <em>Interface List</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList
	 * @generated */
	public Adapter createInterfaceListAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Link
	 * <em>Link</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Link
	 * @generated */
	public Adapter createLinkAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Mapping
	 * <em>Mapping</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Mapping
	 * @generated */
	public Adapter createMappingAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm
	 * <em>Other Algorithm</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm
	 * @generated */
	public Adapter createOtherAlgorithmAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive <em>Output Primitive</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive
	 * @generated */
	public Adapter createOutputPrimitiveAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource
	 * <em>Resource</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource
	 * @generated */
	public Adapter createResourceAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName <em>Resource Type Name</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName
	 * @generated */
	public Adapter createResourceTypeNameAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceType
	 * <em>Resource Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceType
	 * @generated */
	public Adapter createResourceTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Segment
	 * <em>Segment</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Segment
	 * @generated */
	public Adapter createSegmentAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence <em>Service Sequence</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence
	 * @generated */
	public Adapter createServiceSequenceAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction <em>Service Transaction</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction
	 * @generated */
	public Adapter createServiceTransactionAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType <em>Service Interface FB Type</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
	 * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType
	 * @generated */
	public Adapter createServiceInterfaceFBTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
	 * <em>ST Algorithm</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
	 * @generated */
	public Adapter createSTAlgorithmAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp <em>Sub
	 * App</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SubApp
	 * @generated */
	public Adapter createSubAppAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.SubAppType
	 * <em>Sub App Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SubAppType
	 * @generated */
	public Adapter createSubAppTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem <em>Automation System</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem
	 * @generated */
	public Adapter createAutomationSystemAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
	 * <em>Var Declaration</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
	 * @generated */
	public Adapter createVarDeclarationAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo
	 * <em>Version Info</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo
	 * @generated */
	public Adapter createVersionInfoAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.With
	 * <em>With</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.With
	 * @generated */
	public Adapter createWithAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
	 * <em>Library Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
	 * @generated */
	public Adapter createLibraryElementAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilableType
	 * <em>Compilable Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilableType
	 * @generated */
	public Adapter createCompilableTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject <em>Configurable Object</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject
	 * @generated */
	public Adapter createConfigurableObjectAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType <em>Composite FB Type</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
	 * @generated */
	public Adapter createCompositeFBTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm
	 * <em>Text Algorithm</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm
	 * @generated */
	public Adapter createTextAlgorithmAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.DataConnection
	 * <em>Data Connection</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DataConnection
	 * @generated */
	public Adapter createDataConnectionAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.EventConnection <em>Event Connection</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.EventConnection
	 * @generated */
	public Adapter createEventConnectionAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection <em>Adapter Connection</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection
	 * @generated */
	public Adapter createAdapterConnectionAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface <em>Service Interface</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface
	 * @generated */
	public Adapter createServiceInterfaceAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement <em>IInterface Element</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
	 * @generated */
	public Adapter createIInterfaceElementAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Value
	 * <em>Value</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Value
	 * @generated */
	public Adapter createValueAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration <em>System Configuration</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration
	 * @generated */
	public Adapter createSystemConfigurationAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * <em>INamed Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * @generated */
	public Adapter createINamedElementAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB
	 * <em>Resource Type FB</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB
	 * @generated */
	public Adapter createResourceTypeFBAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.SegmentType
	 * <em>Segment Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SegmentType
	 * @generated */
	public Adapter createSegmentTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType
	 * <em>Adapter FB Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
	 * end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType
	 * @generated */
	public Adapter createAdapterFBTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent
	 * <em>Adapter Event</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent
	 * @generated */
	public Adapter createAdapterEventAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Service
	 * <em>Service</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Service
	 * @generated */
	public Adapter createServiceAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject <em>Typed Configureable
	 * Object</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject
	 * @generated */
	public Adapter createTypedConfigureableObjectAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
	 * <em>Adapter FB</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
	 * @generated */
	public Adapter createAdapterFBAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Primitive
	 * <em>Primitive</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Primitive
	 * @generated */
	public Adapter createPrimitiveAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement <em>Positionable Element</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.PositionableElement
	 * @generated */
	public Adapter createPositionableElementAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Position
	 * <em>Position</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Position
	 * @generated */
	public Adapter createPositionAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Color
	 * <em>Color</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Color
	 * @generated */
	public Adapter createColorAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement <em>Colorizable Element</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement
	 * @generated */
	public Adapter createColorizableElementAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.IVarElement
	 * <em>IVar Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IVarElement
	 * @generated */
	public Adapter createIVarElementAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration <em>Attribute Declaration</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration
	 * @generated */
	public Adapter createAttributeDeclarationAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedElement
	 * <em>Typed Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedElement
	 * @generated */
	public Adapter createTypedElementAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
	 * <em>Simple FB Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
	 * @generated */
	public Adapter createSimpleFBTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
	 * <em>Base FB Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
	 * @generated */
	public Adapter createBaseFBTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.StructManipulator <em>Struct Manipulator</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful to
	 * ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.StructManipulator
	 * @generated */
	public Adapter createStructManipulatorAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer
	 * <em>Demultiplexer</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer
	 * @generated */
	public Adapter createDemultiplexerAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Multiplexer
	 * <em>Multiplexer</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Multiplexer
	 * @generated */
	public Adapter createMultiplexerAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.LocalVariable
	 * <em>Local Variable</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LocalVariable
	 * @generated */
	public Adapter createLocalVariableAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute
	 * <em>Attribute</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
	 * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
	 * -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Attribute
	 * @generated */
	public Adapter createAttributeAdapter() {
		return null;
	}

	/** Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.data.DataType
	 * <em>Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.data.DataType
	 * @generated */
	public Adapter createDataTypeAdapter() {
		return null;
	}

	/** Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null.
	 * <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // LibraryElementAdapterFactory
