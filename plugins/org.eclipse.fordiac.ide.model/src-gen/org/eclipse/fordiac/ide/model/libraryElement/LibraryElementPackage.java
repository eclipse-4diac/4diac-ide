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
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.model.data.DataPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
 * @model kind="package"
 * @generated
 */
public interface LibraryElementPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "libraryElement";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.model.libraryElement";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "libraryElement";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	LibraryElementPackage eINSTANCE = org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement <em>INamed Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getINamedElement()
	 * @generated
	 */
	int INAMED_ELEMENT = 53;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableObjectImpl <em>Configurable Object</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableObjectImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConfigurableObject()
	 * @generated
	 */
	int CONFIGURABLE_OBJECT = 43;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl <em>Adapter Declaration</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterDeclaration()
	 * @generated
	 */
	int ADAPTER_DECLARATION = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.I4DIACElementImpl
	 * <em>I4DIAC Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.I4DIACElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getI4DIACElement()
	 * @generated
	 */
	int I4DIAC_ELEMENT = 55;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int I4DIAC_ELEMENT__ANNOTATIONS = 0;

	/**
	 * The number of structural features of the '<em>I4DIAC Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int I4DIAC_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INAMED_ELEMENT__ANNOTATIONS = I4DIAC_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INAMED_ELEMENT__NAME = I4DIAC_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INAMED_ELEMENT__COMMENT = I4DIAC_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>INamed Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INAMED_ELEMENT_FEATURE_COUNT = I4DIAC_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl
	 * <em>Library Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLibraryElement()
	 * @generated
	 */
	int LIBRARY_ELEMENT = 41;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilableTypeImpl
	 * <em>Compilable Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompilableTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompilableType()
	 * @generated
	 */
	int COMPILABLE_TYPE = 42;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl <em>Adapter Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterType()
	 * @generated
	 */
	int ADAPTER_TYPE = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AlgorithmImpl <em>Algorithm</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AlgorithmImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAlgorithm()
	 * @generated
	 */
	int ALGORITHM = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ApplicationImpl <em>Application</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ApplicationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getApplication()
	 * @generated
	 */
	int APPLICATION = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBTypeImpl <em>FB Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBType()
	 * @generated
	 */
	int FB_TYPE = 18;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BasicFBTypeImpl <em>Basic FB Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.BasicFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getBasicFBType()
	 * @generated
	 */
	int BASIC_FB_TYPE = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl <em>Compiler Info</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompilerInfo()
	 * @generated
	 */
	int COMPILER_INFO = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerImpl <em>Compiler</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompiler()
	 * @generated
	 */
	int COMPILER = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl <em>Device</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDevice()
	 * @generated
	 */
	int DEVICE = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceTypeImpl <em>Device Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDeviceType()
	 * @generated
	 */
	int DEVICE_TYPE = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl <em>EC Action</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECAction()
	 * @generated
	 */
	int EC_ACTION = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl <em>ECC</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECC()
	 * @generated
	 */
	int ECC = 11;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl <em>EC State</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECState()
	 * @generated
	 */
	int EC_STATE = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECTransitionImpl <em>EC Transition</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECTransitionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECTransition()
	 * @generated
	 */
	int EC_TRANSITION = 13;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement <em>IInterface Element</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIInterfaceElement()
	 * @generated
	 */
	int IINTERFACE_ELEMENT = 50;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__ANNOTATIONS = INAMED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__IS_INPUT = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__INPUT_CONNECTIONS = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__TYPE = INAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__TYPE_NAME = INAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>IInterface Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl <em>FB Network</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBNetwork()
	 * @generated
	 */
	int FB_NETWORK = 35;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl <em>FB</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFB()
	 * @generated
	 */
	int FB = 15;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.IdentificationImpl
	 * <em>Identification</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.IdentificationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIdentification()
	 * @generated
	 */
	int IDENTIFICATION = 19;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InputPrimitiveImpl
	 * <em>Input Primitive</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.InputPrimitiveImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getInputPrimitive()
	 * @generated
	 */
	int INPUT_PRIMITIVE = 20;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl
	 * <em>Interface List</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getInterfaceList()
	 * @generated
	 */
	int INTERFACE_LIST = 21;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LinkImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 22;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MappingImpl <em>Mapping</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MappingImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMapping()
	 * @generated
	 */
	int MAPPING = 23;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextAlgorithmImpl
	 * <em>Text Algorithm</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TextAlgorithmImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTextAlgorithm()
	 * @generated
	 */
	int TEXT_ALGORITHM = 45;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OtherAlgorithmImpl
	 * <em>Other Algorithm</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OtherAlgorithmImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOtherAlgorithm()
	 * @generated
	 */
	int OTHER_ALGORITHM = 24;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OutputPrimitiveImpl
	 * <em>Output Primitive</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OutputPrimitiveImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOutputPrimitive()
	 * @generated
	 */
	int OUTPUT_PRIMITIVE = 25;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 27;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeNameImpl <em>Resource Type Name</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeNameImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceTypeName()
	 * @generated
	 */
	int RESOURCE_TYPE_NAME = 28;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl <em>Resource Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceType()
	 * @generated
	 */
	int RESOURCE_TYPE = 29;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl <em>Segment</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSegment()
	 * @generated
	 */
	int SEGMENT = 30;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl
	 * <em>Service Sequence</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceSequence()
	 * @generated
	 */
	int SERVICE_SEQUENCE = 31;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceTransactionImpl <em>Service Transaction</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceTransactionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceTransaction()
	 * @generated
	 */
	int SERVICE_TRANSACTION = 32;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceFBTypeImpl <em>Service Interface FB Type</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceInterfaceFBType()
	 * @generated
	 */
	int SERVICE_INTERFACE_FB_TYPE = 33;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.STAlgorithmImpl <em>ST Algorithm</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.STAlgorithmImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSTAlgorithm()
	 * @generated
	 */
	int ST_ALGORITHM = 34;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl <em>Sub App</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSubApp()
	 * @generated
	 */
	int SUB_APP = 17;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppTypeImpl <em>Sub App Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSubAppType()
	 * @generated
	 */
	int SUB_APP_TYPE = 36;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl
	 * <em>Automation System</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAutomationSystem()
	 * @generated
	 */
	int AUTOMATION_SYSTEM = 37;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl
	 * <em>Var Declaration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getVarDeclaration()
	 * @generated
	 */
	int VAR_DECLARATION = 38;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__ANNOTATIONS = IINTERFACE_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__NAME = IINTERFACE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__COMMENT = IINTERFACE_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__IS_INPUT = IINTERFACE_ELEMENT__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__INPUT_CONNECTIONS = IINTERFACE_ELEMENT__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__OUTPUT_CONNECTIONS = IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__TYPE = IINTERFACE_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__TYPE_NAME = IINTERFACE_ELEMENT__TYPE_NAME;

	/**
	 * The feature id for the '<em><b>Array Size</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__ARRAY_SIZE = IINTERFACE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Withs</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__WITHS = IINTERFACE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__VALUE = IINTERFACE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Var Declaration</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION_FEATURE_COUNT = IINTERFACE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__ANNOTATIONS = VAR_DECLARATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__NAME = VAR_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__COMMENT = VAR_DECLARATION__COMMENT;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__IS_INPUT = VAR_DECLARATION__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__INPUT_CONNECTIONS = VAR_DECLARATION__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__OUTPUT_CONNECTIONS = VAR_DECLARATION__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__TYPE = VAR_DECLARATION__TYPE;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__TYPE_NAME = VAR_DECLARATION__TYPE_NAME;

	/**
	 * The feature id for the '<em><b>Array Size</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__ARRAY_SIZE = VAR_DECLARATION__ARRAY_SIZE;

	/**
	 * The feature id for the '<em><b>Withs</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__WITHS = VAR_DECLARATION__WITHS;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__VALUE = VAR_DECLARATION__VALUE;

	/**
	 * The feature id for the '<em><b>Adapter FB</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__ADAPTER_FB = VAR_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__PALETTE_ENTRY = VAR_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Adapter Declaration</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION_FEATURE_COUNT = VAR_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__ANNOTATIONS = INAMED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__VERSION_INFO = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__IDENTIFICATION = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__PALETTE_ENTRY = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Library Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__ANNOTATIONS = DataPackage.DATA_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__NAME = DataPackage.DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__COMMENT = DataPackage.DATA_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__VERSION_INFO = DataPackage.DATA_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__IDENTIFICATION = DataPackage.DATA_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__PALETTE_ENTRY = DataPackage.DATA_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Adapter FB Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__ADAPTER_FB_TYPE = DataPackage.DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Adapter Type</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_FEATURE_COUNT = DataPackage.DATA_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__ANNOTATIONS = INAMED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>Algorithm</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ALGORITHM_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_OBJECT__ANNOTATIONS = INAMED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_OBJECT__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_OBJECT__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_OBJECT__ATTRIBUTES = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Configurable Object</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_OBJECT_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int APPLICATION__ANNOTATIONS = CONFIGURABLE_OBJECT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION__NAME = CONFIGURABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int APPLICATION__COMMENT = CONFIGURABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION__ATTRIBUTES = CONFIGURABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION__FB_NETWORK = CONFIGURABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Application</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int APPLICATION_FEATURE_COUNT = CONFIGURABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_TYPE__ANNOTATIONS = LIBRARY_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_TYPE__NAME = LIBRARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_TYPE__COMMENT = LIBRARY_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_TYPE__VERSION_INFO = LIBRARY_ELEMENT__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_TYPE__IDENTIFICATION = LIBRARY_ELEMENT__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_TYPE__PALETTE_ENTRY = LIBRARY_ELEMENT__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_TYPE__COMPILER_INFO = LIBRARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Compilable Type</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILABLE_TYPE_FEATURE_COUNT = LIBRARY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__ANNOTATIONS = COMPILABLE_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__NAME = COMPILABLE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__COMMENT = COMPILABLE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__VERSION_INFO = COMPILABLE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__IDENTIFICATION = COMPILABLE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__PALETTE_ENTRY = COMPILABLE_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__COMPILER_INFO = COMPILABLE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__INTERFACE_LIST = COMPILABLE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__SERVICE = COMPILABLE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>FB Type</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_FEATURE_COUNT = COMPILABLE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl <em>Base FB Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getBaseFBType()
	 * @generated
	 */
	int BASE_FB_TYPE = 71;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__ANNOTATIONS = FB_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__NAME = FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__COMMENT = FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__VERSION_INFO = FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__IDENTIFICATION = FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__PALETTE_ENTRY = FB_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__COMPILER_INFO = FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__INTERFACE_LIST = FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__SERVICE = FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>Internal Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__INTERNAL_VARS = FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Base FB Type</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE_FEATURE_COUNT = FB_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__ANNOTATIONS = BASE_FB_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__NAME = BASE_FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__COMMENT = BASE_FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__VERSION_INFO = BASE_FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__IDENTIFICATION = BASE_FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__PALETTE_ENTRY = BASE_FB_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__COMPILER_INFO = BASE_FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__INTERFACE_LIST = BASE_FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__SERVICE = BASE_FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>Internal Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__INTERNAL_VARS = BASE_FB_TYPE__INTERNAL_VARS;

	/**
	 * The feature id for the '<em><b>ECC</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__ECC = BASE_FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Algorithm</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__ALGORITHM = BASE_FB_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Basic FB Type</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE_FEATURE_COUNT = BASE_FB_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Compiler</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER_INFO__COMPILER = 0;

	/**
	 * The feature id for the '<em><b>Classdef</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILER_INFO__CLASSDEF = 1;

	/**
	 * The feature id for the '<em><b>Header</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILER_INFO__HEADER = 2;

	/**
	 * The number of structural features of the '<em>Compiler Info</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILER_INFO_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILER__LANGUAGE = 0;

	/**
	 * The feature id for the '<em><b>Product</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILER__PRODUCT = 1;

	/**
	 * The feature id for the '<em><b>Vendor</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILER__VENDOR = 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILER__VERSION = 3;

	/**
	 * The number of structural features of the '<em>Compiler</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPILER_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION__ANNOTATIONS = CONFIGURABLE_OBJECT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__NAME = CONFIGURABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION__COMMENT = CONFIGURABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__ATTRIBUTES = CONFIGURABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Dx1</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__DX1 = CONFIGURABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Dx2</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__DX2 = CONFIGURABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Dy</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__DY = CONFIGURABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Res Type Connection</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION__RES_TYPE_CONNECTION = CONFIGURABLE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Broken Connection</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION__BROKEN_CONNECTION = CONFIGURABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE = CONFIGURABLE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION__DESTINATION = CONFIGURABLE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Connection</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = CONFIGURABLE_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TypedConfigureableObjectImpl <em>Typed Configureable Object</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TypedConfigureableObjectImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypedConfigureableObject()
	 * @generated
	 */
	int TYPED_CONFIGUREABLE_OBJECT = 61;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT__ANNOTATIONS = CONFIGURABLE_OBJECT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT__NAME = CONFIGURABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT__COMMENT = CONFIGURABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT__ATTRIBUTES = CONFIGURABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY = CONFIGURABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Typed Configureable Object</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT = CONFIGURABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE__ANNOTATIONS = TYPED_CONFIGUREABLE_OBJECT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__NAME = TYPED_CONFIGUREABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE__COMMENT = TYPED_CONFIGUREABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__ATTRIBUTES = TYPED_CONFIGUREABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE__PALETTE_ENTRY = TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__X = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__Y = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Color</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE__COLOR = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__VAR_DECLARATIONS = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__RESOURCE = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Profile</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE__PROFILE = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>In Connections</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE__IN_CONNECTIONS = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Device</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE_FEATURE_COUNT = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__ANNOTATIONS = COMPILABLE_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__NAME = COMPILABLE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__COMMENT = COMPILABLE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__VERSION_INFO = COMPILABLE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__IDENTIFICATION = COMPILABLE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__PALETTE_ENTRY = COMPILABLE_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__COMPILER_INFO = COMPILABLE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Var Declaration</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__VAR_DECLARATION = COMPILABLE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Resource Type Name</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__RESOURCE_TYPE_NAME = COMPILABLE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__RESOURCE = COMPILABLE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__FB_NETWORK = COMPILABLE_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Profile</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__PROFILE = COMPILABLE_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attribute Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__ATTRIBUTE_DECLARATIONS = COMPILABLE_TYPE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Device Type</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_FEATURE_COUNT = COMPILABLE_TYPE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Algorithm</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_ACTION__ALGORITHM = 0;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_ACTION__OUTPUT = 1;

	/**
	 * The feature id for the '<em><b>EC State</b></em>' container reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_ACTION__EC_STATE = 2;

	/**
	 * The number of structural features of the '<em>EC Action</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_ACTION_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>EC State</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECC__EC_STATE = 0;

	/**
	 * The feature id for the '<em><b>EC Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECC__EC_TRANSITION = 1;

	/**
	 * The feature id for the '<em><b>Start</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECC__START = 2;

	/**
	 * The feature id for the '<em><b>Basic FB Type</b></em>' container reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECC__BASIC_FB_TYPE = 3;

	/**
	 * The number of structural features of the '<em>ECC</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ECC_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_STATE__ANNOTATIONS = INAMED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_STATE__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__X = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__Y = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>EC Action</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__EC_ACTION = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Out Transitions</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_STATE__OUT_TRANSITIONS = INAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>In Transitions</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_STATE__IN_TRANSITIONS = INAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>ECC</b></em>' container reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_STATE__ECC = INAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>EC State</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_STATE_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.PrimitiveImpl <em>Primitive</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.PrimitiveImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPrimitive()
	 * @generated
	 */
	int PRIMITIVE = 63;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VersionInfoImpl <em>Version Info</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.VersionInfoImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getVersionInfo()
	 * @generated
	 */
	int VERSION_INFO = 39;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.WithImpl <em>With</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.WithImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getWith()
	 * @generated
	 */
	int WITH = 40;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompositeFBTypeImpl
	 * <em>Composite FB Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompositeFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompositeFBType()
	 * @generated
	 */
	int COMPOSITE_FB_TYPE = 44;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DataConnectionImpl
	 * <em>Data Connection</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DataConnectionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDataConnection()
	 * @generated
	 */
	int DATA_CONNECTION = 46;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventConnectionImpl
	 * <em>Event Connection</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.EventConnectionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getEventConnection()
	 * @generated
	 */
	int EVENT_CONNECTION = 47;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterConnectionImpl <em>Adapter Connection</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterConnectionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterConnection()
	 * @generated
	 */
	int ADAPTER_CONNECTION = 48;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceImpl
	 * <em>Service Interface</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceInterface()
	 * @generated
	 */
	int SERVICE_INTERFACE = 49;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ValueImpl <em>Value</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ValueImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getValue()
	 * @generated
	 */
	int VALUE = 51;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SystemConfigurationImpl <em>System Configuration</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SystemConfigurationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSystemConfiguration()
	 * @generated
	 */
	int SYSTEM_CONFIGURATION = 52;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentTypeImpl <em>Segment Type</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSegmentType()
	 * @generated
	 */
	int SEGMENT_TYPE = 56;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBTypeImpl
	 * <em>Adapter FB Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterFBType()
	 * @generated
	 */
	int ADAPTER_FB_TYPE = 57;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AnnotationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 58;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterEventImpl <em>Adapter Event</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterEventImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterEvent()
	 * @generated
	 */
	int ADAPTER_EVENT = 59;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl <em>Service</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getService()
	 * @generated
	 */
	int SERVICE = 60;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBImpl <em>Adapter FB</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterFB()
	 * @generated
	 */
	int ADAPTER_FB = 62;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.PositionableElementImpl <em>Positionable Element</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.PositionableElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPositionableElement()
	 * @generated
	 */
	int POSITIONABLE_ELEMENT = 64;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITIONABLE_ELEMENT__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITIONABLE_ELEMENT__Y = 1;

	/**
	 * The number of structural features of the '<em>Positionable Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITIONABLE_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__X = POSITIONABLE_ELEMENT__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__Y = POSITIONABLE_ELEMENT__Y;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__COMMENT = POSITIONABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition Expression</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__CONDITION_EXPRESSION = POSITIONABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__SOURCE = POSITIONABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__DESTINATION = POSITIONABLE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Condition Event</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__CONDITION_EVENT = POSITIONABLE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>ECC</b></em>' container reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__ECC = POSITIONABLE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>EC Transition</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION_FEATURE_COUNT = POSITIONABLE_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT__ANNOTATIONS = IINTERFACE_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NAME = IINTERFACE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT__COMMENT = IINTERFACE_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT__IS_INPUT = IINTERFACE_ELEMENT__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__INPUT_CONNECTIONS = IINTERFACE_ELEMENT__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__OUTPUT_CONNECTIONS = IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__TYPE = IINTERFACE_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT__TYPE_NAME = IINTERFACE_ELEMENT__TYPE_NAME;

	/**
	 * The feature id for the '<em><b>With</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__WITH = IINTERFACE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT_FEATURE_COUNT = IINTERFACE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl <em>FB Network Element</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBNetworkElement()
	 * @generated
	 */
	int FB_NETWORK_ELEMENT = 16;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__ANNOTATIONS = TYPED_CONFIGUREABLE_OBJECT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__NAME = TYPED_CONFIGUREABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__COMMENT = TYPED_CONFIGUREABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__ATTRIBUTES = TYPED_CONFIGUREABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__PALETTE_ENTRY = TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__X = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__Y = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__INTERFACE = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__MAPPING = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>FB Network Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT_FEATURE_COUNT = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB__ANNOTATIONS = FB_NETWORK_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__NAME = FB_NETWORK_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB__COMMENT = FB_NETWORK_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__ATTRIBUTES = FB_NETWORK_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB__PALETTE_ENTRY = FB_NETWORK_ELEMENT__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__X = FB_NETWORK_ELEMENT__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__Y = FB_NETWORK_ELEMENT__Y;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__INTERFACE = FB_NETWORK_ELEMENT__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB__MAPPING = FB_NETWORK_ELEMENT__MAPPING;

	/**
	 * The number of structural features of the '<em>FB</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_FEATURE_COUNT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APP__ANNOTATIONS = FB_NETWORK_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__NAME = FB_NETWORK_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APP__COMMENT = FB_NETWORK_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__ATTRIBUTES = FB_NETWORK_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APP__PALETTE_ENTRY = FB_NETWORK_ELEMENT__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__X = FB_NETWORK_ELEMENT__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__Y = FB_NETWORK_ELEMENT__Y;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__INTERFACE = FB_NETWORK_ELEMENT__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APP__MAPPING = FB_NETWORK_ELEMENT__MAPPING;

	/**
	 * The feature id for the '<em><b>Sub App Network</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__SUB_APP_NETWORK = FB_NETWORK_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sub App</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APP_FEATURE_COUNT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Application Domain</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__APPLICATION_DOMAIN = 0;

	/**
	 * The feature id for the '<em><b>Classification</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__CLASSIFICATION = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Function</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__FUNCTION = 3;

	/**
	 * The feature id for the '<em><b>Standard</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__STANDARD = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__TYPE = 5;

	/**
	 * The number of structural features of the '<em>Identification</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION_FEATURE_COUNT = 6;

	/**
	 * The feature id for the '<em><b>Event</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE__EVENT = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE__PARAMETERS = 1;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE__INTERFACE = 2;

	/**
	 * The number of structural features of the '<em>Primitive</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Event</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PRIMITIVE__EVENT = PRIMITIVE__EVENT;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INPUT_PRIMITIVE__PARAMETERS = PRIMITIVE__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int INPUT_PRIMITIVE__INTERFACE = PRIMITIVE__INTERFACE;

	/**
	 * The number of structural features of the '<em>Input Primitive</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PRIMITIVE_FEATURE_COUNT = PRIMITIVE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Plugs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__PLUGS = 0;

	/**
	 * The feature id for the '<em><b>Sockets</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__SOCKETS = 1;

	/**
	 * The feature id for the '<em><b>Event Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__EVENT_INPUTS = 2;

	/**
	 * The feature id for the '<em><b>Event Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__EVENT_OUTPUTS = 3;

	/**
	 * The feature id for the '<em><b>Input Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__INPUT_VARS = 4;

	/**
	 * The feature id for the '<em><b>Output Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__OUTPUT_VARS = 5;

	/**
	 * The number of structural features of the '<em>Interface List</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST_FEATURE_COUNT = 6;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK__ANNOTATIONS = CONFIGURABLE_OBJECT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__NAME = CONFIGURABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK__COMMENT = CONFIGURABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__ATTRIBUTES = CONFIGURABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Segment</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK__SEGMENT = CONFIGURABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Device</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK__DEVICE = CONFIGURABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Link</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = CONFIGURABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__TO = 1;

	/**
	 * The number of structural features of the '<em>Mapping</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MAPPING_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_ALGORITHM__ANNOTATIONS = ALGORITHM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ALGORITHM__NAME = ALGORITHM__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_ALGORITHM__COMMENT = ALGORITHM__COMMENT;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ALGORITHM__TEXT = ALGORITHM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Algorithm</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ALGORITHM_FEATURE_COUNT = ALGORITHM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM__ANNOTATIONS = TEXT_ALGORITHM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM__NAME = TEXT_ALGORITHM__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM__COMMENT = TEXT_ALGORITHM__COMMENT;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM__TEXT = TEXT_ALGORITHM__TEXT;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM__LANGUAGE = TEXT_ALGORITHM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Other Algorithm</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM_FEATURE_COUNT = TEXT_ALGORITHM_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Event</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PRIMITIVE__EVENT = PRIMITIVE__EVENT;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PRIMITIVE__PARAMETERS = PRIMITIVE__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PRIMITIVE__INTERFACE = PRIMITIVE__INTERFACE;

	/**
	 * The feature id for the '<em><b>Test Result</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PRIMITIVE__TEST_RESULT = PRIMITIVE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Output Primitive</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PRIMITIVE_FEATURE_COUNT = PRIMITIVE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeFBImpl
	 * <em>Resource Type FB</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeFBImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceTypeFB()
	 * @generated
	 */
	int RESOURCE_TYPE_FB = 54;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ColorImpl <em>Color</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ColorImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getColor()
	 * @generated
	 */
	int COLOR = 65;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ColorizableElementImpl <em>Colorizable Element</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ColorizableElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getColorizableElement()
	 * @generated
	 */
	int COLORIZABLE_ELEMENT = 66;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.IVarElement <em>IVar Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IVarElement
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIVarElement()
	 * @generated
	 */
	int IVAR_ELEMENT = 67;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl <em>Attribute Declaration</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAttributeDeclaration()
	 * @generated
	 */
	int ATTRIBUTE_DECLARATION = 68;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 26;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__ANNOTATIONS = INAMED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYPE = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Attribute Declaration</b></em>' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__ATTRIBUTE_DECLARATION = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VALUE = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE__ANNOTATIONS = TYPED_CONFIGUREABLE_OBJECT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__NAME = TYPED_CONFIGUREABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE__COMMENT = TYPED_CONFIGUREABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__ATTRIBUTES = TYPED_CONFIGUREABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE__PALETTE_ENTRY = TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__VAR_DECLARATIONS = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__FB_NETWORK = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__X = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__Y = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Device</b></em>' container reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE__DEVICE = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Device Type Resource</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE__DEVICE_TYPE_RESOURCE = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Resource</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_NAME__NAME = 0;

	/**
	 * The number of structural features of the '<em>Resource Type Name</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_NAME_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__ANNOTATIONS = COMPILABLE_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__NAME = COMPILABLE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__COMMENT = COMPILABLE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__VERSION_INFO = COMPILABLE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__IDENTIFICATION = COMPILABLE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__PALETTE_ENTRY = COMPILABLE_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__COMPILER_INFO = COMPILABLE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Var Declaration</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__VAR_DECLARATION = COMPILABLE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__FB_NETWORK = COMPILABLE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Supported FB Types</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__SUPPORTED_FB_TYPES = COMPILABLE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Resource Type</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FEATURE_COUNT = COMPILABLE_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__ANNOTATIONS = TYPED_CONFIGUREABLE_OBJECT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__NAME = TYPED_CONFIGUREABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__COMMENT = TYPED_CONFIGUREABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__ATTRIBUTES = TYPED_CONFIGUREABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__PALETTE_ENTRY = TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__X = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__Y = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Color</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__COLOR = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__WIDTH = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__VAR_DECLARATIONS = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Out Connections</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT__OUT_CONNECTIONS = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Segment</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT_FEATURE_COUNT = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__ANNOTATIONS = INAMED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Service Transaction</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__SERVICE_TRANSACTION = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Test Result</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__TEST_RESULT = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Service Sequence</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Input Primitive</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TRANSACTION__INPUT_PRIMITIVE = 0;

	/**
	 * The feature id for the '<em><b>Output Primitive</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TRANSACTION__OUTPUT_PRIMITIVE = 1;

	/**
	 * The feature id for the '<em><b>Test Result</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_TRANSACTION__TEST_RESULT = 2;

	/**
	 * The number of structural features of the '<em>Service Transaction</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TRANSACTION_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__ANNOTATIONS = FB_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__NAME = FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__COMMENT = FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__VERSION_INFO = FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__IDENTIFICATION = FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__PALETTE_ENTRY = FB_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__COMPILER_INFO = FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__INTERFACE_LIST = FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__SERVICE = FB_TYPE__SERVICE;

	/**
	 * The number of structural features of the '<em>Service Interface FB Type</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE_FEATURE_COUNT = FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM__ANNOTATIONS = TEXT_ALGORITHM__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM__NAME = TEXT_ALGORITHM__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM__COMMENT = TEXT_ALGORITHM__COMMENT;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM__TEXT = TEXT_ALGORITHM__TEXT;

	/**
	 * The number of structural features of the '<em>ST Algorithm</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM_FEATURE_COUNT = TEXT_ALGORITHM_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Network Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK__NETWORK_ELEMENTS = 0;

	/**
	 * The feature id for the '<em><b>Data Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK__DATA_CONNECTIONS = 1;

	/**
	 * The feature id for the '<em><b>Event Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK__EVENT_CONNECTIONS = 2;

	/**
	 * The feature id for the '<em><b>Adapter Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK__ADAPTER_CONNECTIONS = 3;

	/**
	 * The number of structural features of the '<em>FB Network</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__ANNOTATIONS = FB_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__NAME = FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__COMMENT = FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__VERSION_INFO = FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__IDENTIFICATION = FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__PALETTE_ENTRY = FB_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__COMPILER_INFO = FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__INTERFACE_LIST = FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__SERVICE = FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__FB_NETWORK = FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Composite FB Type</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE_FEATURE_COUNT = FB_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__ANNOTATIONS = COMPOSITE_FB_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__NAME = COMPOSITE_FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__COMMENT = COMPOSITE_FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__VERSION_INFO = COMPOSITE_FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__IDENTIFICATION = COMPOSITE_FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__PALETTE_ENTRY = COMPOSITE_FB_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__COMPILER_INFO = COMPOSITE_FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__INTERFACE_LIST = COMPOSITE_FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__SERVICE = COMPOSITE_FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__FB_NETWORK = COMPOSITE_FB_TYPE__FB_NETWORK;

	/**
	 * The number of structural features of the '<em>Sub App Type</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE_FEATURE_COUNT = COMPOSITE_FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__ANNOTATIONS = LIBRARY_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__NAME = LIBRARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__COMMENT = LIBRARY_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__VERSION_INFO = LIBRARY_ELEMENT__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__IDENTIFICATION = LIBRARY_ELEMENT__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__PALETTE_ENTRY = LIBRARY_ELEMENT__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Application</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__APPLICATION = LIBRARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__MAPPING = LIBRARY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Palette</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__PALETTE = LIBRARY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>System Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION = LIBRARY_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>System File</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__SYSTEM_FILE = LIBRARY_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Automation System</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM_FEATURE_COUNT = LIBRARY_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__AUTHOR = 0;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__DATE = 1;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__ORGANIZATION = 2;

	/**
	 * The feature id for the '<em><b>Remarks</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__REMARKS = 3;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__VERSION = 4;

	/**
	 * The number of structural features of the '<em>Version Info</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO_FEATURE_COUNT = 5;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WITH__VARIABLES = 0;

	/**
	 * The number of structural features of the '<em>With</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int WITH_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__ANNOTATIONS = CONNECTION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__COMMENT = CONNECTION__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__ATTRIBUTES = CONNECTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Dx1</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__DX1 = CONNECTION__DX1;

	/**
	 * The feature id for the '<em><b>Dx2</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__DX2 = CONNECTION__DX2;

	/**
	 * The feature id for the '<em><b>Dy</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__DY = CONNECTION__DY;

	/**
	 * The feature id for the '<em><b>Res Type Connection</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__RES_TYPE_CONNECTION = CONNECTION__RES_TYPE_CONNECTION;

	/**
	 * The feature id for the '<em><b>Broken Connection</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__BROKEN_CONNECTION = CONNECTION__BROKEN_CONNECTION;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__DESTINATION = CONNECTION__DESTINATION;

	/**
	 * The number of structural features of the '<em>Data Connection</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__ANNOTATIONS = CONNECTION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__COMMENT = CONNECTION__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__ATTRIBUTES = CONNECTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Dx1</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__DX1 = CONNECTION__DX1;

	/**
	 * The feature id for the '<em><b>Dx2</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__DX2 = CONNECTION__DX2;

	/**
	 * The feature id for the '<em><b>Dy</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__DY = CONNECTION__DY;

	/**
	 * The feature id for the '<em><b>Res Type Connection</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__RES_TYPE_CONNECTION = CONNECTION__RES_TYPE_CONNECTION;

	/**
	 * The feature id for the '<em><b>Broken Connection</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__BROKEN_CONNECTION = CONNECTION__BROKEN_CONNECTION;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__DESTINATION = CONNECTION__DESTINATION;

	/**
	 * The number of structural features of the '<em>Event Connection</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__ANNOTATIONS = CONNECTION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__NAME = CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__COMMENT = CONNECTION__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__ATTRIBUTES = CONNECTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Dx1</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__DX1 = CONNECTION__DX1;

	/**
	 * The feature id for the '<em><b>Dx2</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__DX2 = CONNECTION__DX2;

	/**
	 * The feature id for the '<em><b>Dy</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__DY = CONNECTION__DY;

	/**
	 * The feature id for the '<em><b>Res Type Connection</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__RES_TYPE_CONNECTION = CONNECTION__RES_TYPE_CONNECTION;

	/**
	 * The feature id for the '<em><b>Broken Connection</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__BROKEN_CONNECTION = CONNECTION__BROKEN_CONNECTION;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__DESTINATION = CONNECTION__DESTINATION;

	/**
	 * The number of structural features of the '<em>Adapter Connection</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__ANNOTATIONS = INAMED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>Service Interface</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Value</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VALUE_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CONFIGURATION__ANNOTATIONS = I4DIAC_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Devices</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CONFIGURATION__DEVICES = I4DIAC_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CONFIGURATION__SEGMENTS = I4DIAC_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CONFIGURATION__LINKS = I4DIAC_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>System Configuration</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CONFIGURATION_FEATURE_COUNT = I4DIAC_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__ANNOTATIONS = FB__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__NAME = FB__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__COMMENT = FB__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__ATTRIBUTES = FB__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__PALETTE_ENTRY = FB__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__X = FB__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__Y = FB__Y;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__INTERFACE = FB__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__MAPPING = FB__MAPPING;

	/**
	 * The number of structural features of the '<em>Resource Type FB</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB_FEATURE_COUNT = FB_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__ANNOTATIONS = COMPILABLE_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__NAME = COMPILABLE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__COMMENT = COMPILABLE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__VERSION_INFO = COMPILABLE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__IDENTIFICATION = COMPILABLE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__PALETTE_ENTRY = COMPILABLE_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__COMPILER_INFO = COMPILABLE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Var Declaration</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__VAR_DECLARATION = COMPILABLE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Segment Type</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_FEATURE_COUNT = COMPILABLE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE__ANNOTATIONS = FB_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE__NAME = FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE__COMMENT = FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE__VERSION_INFO = FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE__IDENTIFICATION = FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE__PALETTE_ENTRY = FB_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE__COMPILER_INFO = FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE__INTERFACE_LIST = FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE__SERVICE = FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>Adapter Type</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE__ADAPTER_TYPE = FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Adapter FB Type</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_TYPE_FEATURE_COUNT = FB_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Servity</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__SERVITY = 1;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT__ANNOTATIONS = EVENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT__NAME = EVENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT__COMMENT = EVENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT__IS_INPUT = EVENT__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT__INPUT_CONNECTIONS = EVENT__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT__OUTPUT_CONNECTIONS = EVENT__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT__TYPE = EVENT__TYPE;

	/**
	 * The feature id for the '<em><b>Type Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT__TYPE_NAME = EVENT__TYPE_NAME;

	/**
	 * The feature id for the '<em><b>With</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT__WITH = EVENT__WITH;

	/**
	 * The feature id for the '<em><b>Adapter Declaration</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT__ADAPTER_DECLARATION = EVENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Adapter Event</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_EVENT_FEATURE_COUNT = EVENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE__ANNOTATIONS = I4DIAC_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Right Interface</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE__RIGHT_INTERFACE = I4DIAC_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Left Interface</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE__LEFT_INTERFACE = I4DIAC_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Service Sequence</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE__SERVICE_SEQUENCE = I4DIAC_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Service</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_FEATURE_COUNT = I4DIAC_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__ANNOTATIONS = FB__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__NAME = FB__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__COMMENT = FB__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__ATTRIBUTES = FB__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__PALETTE_ENTRY = FB__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__X = FB__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__Y = FB__Y;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__INTERFACE = FB__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__MAPPING = FB__MAPPING;

	/**
	 * The feature id for the '<em><b>Adapter Decl</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__ADAPTER_DECL = FB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Adapter FB</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_FEATURE_COUNT = FB_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Red</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__RED = 0;

	/**
	 * The feature id for the '<em><b>Green</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__GREEN = 1;

	/**
	 * The feature id for the '<em><b>Blue</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__BLUE = 2;

	/**
	 * The number of structural features of the '<em>Color</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLOR_FEATURE_COUNT = 3;

	/**
	 * The feature id for the '<em><b>Color</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COLORIZABLE_ELEMENT__COLOR = 0;

	/**
	 * The number of structural features of the '<em>Colorizable Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLORIZABLE_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IVAR_ELEMENT__VAR_DECLARATIONS = 0;

	/**
	 * The number of structural features of the '<em>IVar Element</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IVAR_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__ANNOTATIONS = INAMED_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__TYPE = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__INITIAL_VALUE = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Attribute Declaration</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedElement <em>Typed Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedElement
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypedElement()
	 * @generated
	 */
	int TYPED_ELEMENT = 69;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Typed Element</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TYPED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl
	 * <em>Simple FB Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSimpleFBType()
	 * @generated
	 */
	int SIMPLE_FB_TYPE = 70;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__ANNOTATIONS = BASE_FB_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__NAME = BASE_FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__COMMENT = BASE_FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__VERSION_INFO = BASE_FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__IDENTIFICATION = BASE_FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__PALETTE_ENTRY = BASE_FB_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__COMPILER_INFO = BASE_FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__INTERFACE_LIST = BASE_FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__SERVICE = BASE_FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>Internal Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__INTERNAL_VARS = BASE_FB_TYPE__INTERNAL_VARS;

	/**
	 * The feature id for the '<em><b>Algorithm</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__ALGORITHM = BASE_FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simple FB Type</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE_FEATURE_COUNT = BASE_FB_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.Language <em>Language</em>}' enum.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Language
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLanguage()
	 * @generated
	 */
	int LANGUAGE = 72;

	/**
	 * The meta object id for the '<em>IProject</em>' data type.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see org.eclipse.core.resources.IProject
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIProject()
	 * @generated
	 */
	int IPROJECT = 73;

	/**
	 * The meta object id for the '<em>IFile</em>' data type.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see org.eclipse.core.resources.IFile
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIFile()
	 * @generated
	 */
	int IFILE = 74;

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration <em>Adapter Declaration</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
	 * @generated
	 */
	EClass getAdapterDeclaration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getAdapterFB <em>Adapter FB</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Adapter FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getAdapterFB()
	 * @see #getAdapterDeclaration()
	 * @generated
	 */
	EReference getAdapterDeclaration_AdapterFB();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getPaletteEntry <em>Palette Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getPaletteEntry()
	 * @see #getAdapterDeclaration()
	 * @generated
	 */
	EReference getAdapterDeclaration_PaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterType <em>Adapter Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterType
	 * @generated
	 */
	EClass getAdapterType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterType#getAdapterFBType <em>Adapter FB Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Adapter FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterType#getAdapterFBType()
	 * @see #getAdapterType()
	 * @generated
	 */
	EReference getAdapterType_AdapterFBType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Algorithm <em>Algorithm</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Algorithm
	 * @generated
	 */
	EClass getAlgorithm();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Application <em>Application</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Application
	 * @generated
	 */
	EClass getApplication();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Application#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Application#getFBNetwork()
	 * @see #getApplication()
	 * @generated
	 */
	EReference getApplication_FBNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.BasicFBType <em>Basic FB Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
	 * @generated
	 */
	EClass getBasicFBType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.BasicFBType#getECC <em>ECC</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>ECC</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BasicFBType#getECC()
	 * @see #getBasicFBType()
	 * @generated
	 */
	EReference getBasicFBType_ECC();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.BasicFBType#getAlgorithm <em>Algorithm</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BasicFBType#getAlgorithm()
	 * @see #getBasicFBType()
	 * @generated
	 */
	EReference getBasicFBType_Algorithm();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo <em>Compiler Info</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compiler Info</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo
	 * @generated
	 */
	EClass getCompilerInfo();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getCompiler <em>Compiler</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compiler</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getCompiler()
	 * @see #getCompilerInfo()
	 * @generated
	 */
	EReference getCompilerInfo_Compiler();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getClassdef <em>Classdef</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Classdef</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getClassdef()
	 * @see #getCompilerInfo()
	 * @generated
	 */
	EAttribute getCompilerInfo_Classdef();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getHeader <em>Header</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getHeader()
	 * @see #getCompilerInfo()
	 * @generated
	 */
	EAttribute getCompilerInfo_Header();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler <em>Compiler</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compiler</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler
	 * @generated
	 */
	EClass getCompiler();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler#getLanguage()
	 * @see #getCompiler()
	 * @generated
	 */
	EAttribute getCompiler_Language();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getProduct <em>Product</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Product</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler#getProduct()
	 * @see #getCompiler()
	 * @generated
	 */
	EAttribute getCompiler_Product();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVendor <em>Vendor</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVendor()
	 * @see #getCompiler()
	 * @generated
	 */
	EAttribute getCompiler_Vendor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVersion()
	 * @see #getCompiler()
	 * @generated
	 */
	EAttribute getCompiler_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDx1 <em>Dx1</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dx1</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#getDx1()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_Dx1();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDx2 <em>Dx2</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dx2</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#getDx2()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_Dx2();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDy <em>Dy</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dy</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#getDy()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_Dy();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isResTypeConnection <em>Res Type Connection</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Res Type Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#isResTypeConnection()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_ResTypeConnection();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isBrokenConnection <em>Broken Connection</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Broken Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#isBrokenConnection()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_BrokenConnection();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#getSource()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Destination</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#getDestination()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Destination();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Device <em>Device</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Device</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Device
	 * @generated
	 */
	EClass getDevice();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resource</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Device#getResource()
	 * @see #getDevice()
	 * @generated
	 */
	EReference getDevice_Resource();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getProfile <em>Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Profile</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Device#getProfile()
	 * @see #getDevice()
	 * @generated
	 */
	EAttribute getDevice_Profile();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getInConnections <em>In Connections</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>In Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Device#getInConnections()
	 * @see #getDevice()
	 * @generated
	 */
	EReference getDevice_InConnections();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType <em>Device Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Device Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType
	 * @generated
	 */
	EClass getDeviceType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getVarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getVarDeclaration()
	 * @see #getDeviceType()
	 * @generated
	 */
	EReference getDeviceType_VarDeclaration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getResourceTypeName <em>Resource Type Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resource Type Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getResourceTypeName()
	 * @see #getDeviceType()
	 * @generated
	 */
	EReference getDeviceType_ResourceTypeName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resource</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getResource()
	 * @see #getDeviceType()
	 * @generated
	 */
	EReference getDeviceType_Resource();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getFBNetwork()
	 * @see #getDeviceType()
	 * @generated
	 */
	EReference getDeviceType_FBNetwork();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getProfile <em>Profile</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Profile</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getProfile()
	 * @see #getDeviceType()
	 * @generated
	 */
	EAttribute getDeviceType_Profile();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getAttributeDeclarations
	 * <em>Attribute Declarations</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the containment reference list '<em>Attribute
	 *         Declarations</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getAttributeDeclarations()
	 * @see #getDeviceType()
	 * @generated
	 */
	EReference getDeviceType_AttributeDeclarations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction <em>EC Action</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>EC Action</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECAction
	 * @generated
	 */
	EClass getECAction();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getAlgorithm <em>Algorithm</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECAction#getAlgorithm()
	 * @see #getECAction()
	 * @generated
	 */
	EReference getECAction_Algorithm();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getOutput <em>Output</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Output</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECAction#getOutput()
	 * @see #getECAction()
	 * @generated
	 */
	EReference getECAction_Output();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getECState <em>EC State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>EC State</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECAction#getECState()
	 * @see #getECAction()
	 * @generated
	 */
	EReference getECAction_ECState();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC <em>ECC</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>ECC</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC
	 * @generated
	 */
	EClass getECC();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getECState <em>EC State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>EC State</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC#getECState()
	 * @see #getECC()
	 * @generated
	 */
	EReference getECC_ECState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getECTransition <em>EC Transition</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>EC Transition</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC#getECTransition()
	 * @see #getECC()
	 * @generated
	 */
	EReference getECC_ECTransition();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC#getStart()
	 * @see #getECC()
	 * @generated
	 */
	EReference getECC_Start();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getBasicFBType <em>Basic FB Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Basic FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC#getBasicFBType()
	 * @see #getECC()
	 * @generated
	 */
	EReference getECC_BasicFBType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState <em>EC State</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>EC State</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState
	 * @generated
	 */
	EClass getECState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getECAction <em>EC Action</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>EC Action</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getECAction()
	 * @see #getECState()
	 * @generated
	 */
	EReference getECState_ECAction();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getOutTransitions <em>Out Transitions</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Out Transitions</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getOutTransitions()
	 * @see #getECState()
	 * @generated
	 */
	EReference getECState_OutTransitions();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getInTransitions <em>In Transitions</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>In Transitions</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getInTransitions()
	 * @see #getECState()
	 * @generated
	 */
	EReference getECState_InTransitions();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getECC <em>ECC</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>ECC</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getECC()
	 * @see #getECState()
	 * @generated
	 */
	EReference getECState_ECC();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition <em>EC Transition</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>EC Transition</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition
	 * @generated
	 */
	EClass getECTransition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getComment()
	 * @see #getECTransition()
	 * @generated
	 */
	EAttribute getECTransition_Comment();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionExpression
	 * <em>Condition Expression</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Condition Expression</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionExpression()
	 * @see #getECTransition()
	 * @generated
	 */
	EAttribute getECTransition_ConditionExpression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Destination</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getDestination()
	 * @see #getECTransition()
	 * @generated
	 */
	EReference getECTransition_Destination();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionEvent <em>Condition Event</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Condition Event</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionEvent()
	 * @see #getECTransition()
	 * @generated
	 */
	EReference getECTransition_ConditionEvent();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getECC <em>ECC</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>ECC</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getECC()
	 * @see #getECTransition()
	 * @generated
	 */
	EReference getECTransition_ECC();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getSource()
	 * @see #getECTransition()
	 * @generated
	 */
	EReference getECTransition_Source();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Event <em>Event</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Event
	 * @generated
	 */
	EClass getEvent();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Event#getWith <em>With</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>With</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Event#getWith()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_With();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork
	 * @generated
	 */
	EClass getFBNetwork();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getNetworkElements <em>Network Elements</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Network Elements</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getNetworkElements()
	 * @see #getFBNetwork()
	 * @generated
	 */
	EReference getFBNetwork_NetworkElements();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getDataConnections <em>Data Connections</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getDataConnections()
	 * @see #getFBNetwork()
	 * @generated
	 */
	EReference getFBNetwork_DataConnections();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getEventConnections <em>Event Connections</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getEventConnections()
	 * @see #getFBNetwork()
	 * @generated
	 */
	EReference getFBNetwork_EventConnections();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getAdapterConnections <em>Adapter Connections</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Adapter Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getAdapterConnections()
	 * @see #getFBNetwork()
	 * @generated
	 */
	EReference getFBNetwork_AdapterConnections();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.FB <em>FB</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FB
	 * @generated
	 */
	EClass getFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement <em>FB Network Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB Network Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement
	 * @generated
	 */
	EClass getFBNetworkElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getInterface <em>Interface</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getInterface()
	 * @see #getFBNetworkElement()
	 * @generated
	 */
	EReference getFBNetworkElement_Interface();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getMapping()
	 * @see #getFBNetworkElement()
	 * @generated
	 */
	EReference getFBNetworkElement_Mapping();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.FBType <em>FB Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBType
	 * @generated
	 */
	EClass getFBType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.FBType#getInterfaceList <em>Interface List</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Interface List</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBType#getInterfaceList()
	 * @see #getFBType()
	 * @generated
	 */
	EReference getFBType_InterfaceList();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.FBType#getService <em>Service</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Service</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBType#getService()
	 * @see #getFBType()
	 * @generated
	 */
	EReference getFBType_Service();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification <em>Identification</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identification</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification
	 * @generated
	 */
	EClass getIdentification();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getApplicationDomain <em>Application Domain</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Application Domain</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getApplicationDomain()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_ApplicationDomain();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getClassification <em>Classification</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Classification</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getClassification()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_Classification();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getDescription()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getFunction()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_Function();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getStandard <em>Standard</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Standard</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getStandard()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_Standard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getType <em>Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getType()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive <em>Input Primitive</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Input Primitive</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive
	 * @generated
	 */
	EClass getInputPrimitive();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList <em>Interface List</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interface List</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList
	 * @generated
	 */
	EClass getInterfaceList();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getPlugs <em>Plugs</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plugs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getPlugs()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_Plugs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getSockets <em>Sockets</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sockets</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getSockets()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_Sockets();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getEventInputs <em>Event Inputs</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event Inputs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getEventInputs()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_EventInputs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getEventOutputs <em>Event Outputs</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event Outputs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getEventOutputs()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_EventOutputs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getInputVars <em>Input Vars</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input Vars</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getInputVars()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_InputVars();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getOutputVars <em>Output Vars</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Vars</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getOutputVars()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_OutputVars();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Link <em>Link</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Link#getSegment <em>Segment</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Segment</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Link#getSegment()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Segment();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Link#getDevice <em>Device</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Device</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Link#getDevice()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Device();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Mapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Mapping
	 * @generated
	 */
	EClass getMapping();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Mapping#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Mapping#getFrom()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_From();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Mapping#getTo <em>To</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Mapping#getTo()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_To();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm <em>Other Algorithm</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Other Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm
	 * @generated
	 */
	EClass getOtherAlgorithm();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm#getLanguage()
	 * @see #getOtherAlgorithm()
	 * @generated
	 */
	EAttribute getOtherAlgorithm_Language();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive <em>Output Primitive</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Output Primitive</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive
	 * @generated
	 */
	EClass getOutputPrimitive();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive#getTestResult <em>Test Result</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Test Result</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive#getTestResult()
	 * @see #getOutputPrimitive()
	 * @generated
	 */
	EAttribute getOutputPrimitive_TestResult();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#getFBNetwork()
	 * @see #getResource()
	 * @generated
	 */
	EReference getResource_FBNetwork();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getX <em>X</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#getX()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_X();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getY <em>Y</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#getY()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Y();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getDevice <em>Device</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Device</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#getDevice()
	 * @see #getResource()
	 * @generated
	 */
	EReference getResource_Device();

	/**
	 * Returns the meta object for the attribute
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#isDeviceTypeResource
	 * <em>Device Type Resource</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the attribute '<em>Device Type Resource</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#isDeviceTypeResource()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_DeviceTypeResource();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName <em>Resource Type Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Type Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName
	 * @generated
	 */
	EClass getResourceTypeName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName#getName()
	 * @see #getResourceTypeName()
	 * @generated
	 */
	EAttribute getResourceTypeName_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceType <em>Resource Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceType
	 * @generated
	 */
	EClass getResourceType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getVarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getVarDeclaration()
	 * @see #getResourceType()
	 * @generated
	 */
	EReference getResourceType_VarDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getFBNetwork()
	 * @see #getResourceType()
	 * @generated
	 */
	EReference getResourceType_FBNetwork();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getSupportedFBTypes <em>Supported FB Types</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Supported FB Types</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getSupportedFBTypes()
	 * @see #getResourceType()
	 * @generated
	 */
	EReference getResourceType_SupportedFBTypes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Segment <em>Segment</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Segment</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Segment
	 * @generated
	 */
	EClass getSegment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Segment#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Segment#getWidth()
	 * @see #getSegment()
	 * @generated
	 */
	EAttribute getSegment_Width();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Segment#getVarDeclarations <em>Var Declarations</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declarations</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Segment#getVarDeclarations()
	 * @see #getSegment()
	 * @generated
	 */
	EReference getSegment_VarDeclarations();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Segment#getOutConnections <em>Out Connections</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Out Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Segment#getOutConnections()
	 * @see #getSegment()
	 * @generated
	 */
	EReference getSegment_OutConnections();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence <em>Service Sequence</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Sequence</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence
	 * @generated
	 */
	EClass getServiceSequence();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getServiceTransaction <em>Service Transaction</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Service Transaction</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getServiceTransaction()
	 * @see #getServiceSequence()
	 * @generated
	 */
	EReference getServiceSequence_ServiceTransaction();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getTestResult <em>Test Result</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Test Result</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getTestResult()
	 * @see #getServiceSequence()
	 * @generated
	 */
	EAttribute getServiceSequence_TestResult();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction <em>Service Transaction</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Transaction</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction
	 * @generated
	 */
	EClass getServiceTransaction();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getInputPrimitive <em>Input Primitive</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Input Primitive</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getInputPrimitive()
	 * @see #getServiceTransaction()
	 * @generated
	 */
	EReference getServiceTransaction_InputPrimitive();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getOutputPrimitive <em>Output Primitive</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Primitive</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getOutputPrimitive()
	 * @see #getServiceTransaction()
	 * @generated
	 */
	EReference getServiceTransaction_OutputPrimitive();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getTestResult <em>Test Result</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Test Result</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getTestResult()
	 * @see #getServiceTransaction()
	 * @generated
	 */
	EAttribute getServiceTransaction_TestResult();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType <em>Service Interface FB Type</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>Service Interface FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType
	 * @generated
	 */
	EClass getServiceInterfaceFBType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm <em>ST Algorithm</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
	 * @generated
	 */
	EClass getSTAlgorithm();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp <em>Sub App</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub App</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SubApp
	 * @generated
	 */
	EClass getSubApp();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#getSubAppNetwork <em>Sub App Network</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sub App Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SubApp#getSubAppNetwork()
	 * @see #getSubApp()
	 * @generated
	 */
	EReference getSubApp_SubAppNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SubAppType <em>Sub App Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub App Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SubAppType
	 * @generated
	 */
	EClass getSubAppType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem <em>Automation System</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Automation System</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem
	 * @generated
	 */
	EClass getAutomationSystem();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getApplication <em>Application</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Application</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getApplication()
	 * @see #getAutomationSystem()
	 * @generated
	 */
	EReference getAutomationSystem_Application();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mapping</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getMapping()
	 * @see #getAutomationSystem()
	 * @generated
	 */
	EReference getAutomationSystem_Mapping();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getPalette <em>Palette</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Palette</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getPalette()
	 * @see #getAutomationSystem()
	 * @generated
	 */
	EReference getAutomationSystem_Palette();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getSystemConfiguration
	 * <em>System Configuration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the containment reference '<em>System
	 *         Configuration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getSystemConfiguration()
	 * @see #getAutomationSystem()
	 * @generated
	 */
	EReference getAutomationSystem_SystemConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getSystemFile <em>System File</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>System File</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getSystemFile()
	 * @see #getAutomationSystem()
	 * @generated
	 */
	EAttribute getAutomationSystem_SystemFile();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
	 * @generated
	 */
	EClass getVarDeclaration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getArraySize <em>Array Size</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array Size</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getArraySize()
	 * @see #getVarDeclaration()
	 * @generated
	 */
	EAttribute getVarDeclaration_ArraySize();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getWiths <em>Withs</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Withs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getWiths()
	 * @see #getVarDeclaration()
	 * @generated
	 */
	EReference getVarDeclaration_Withs();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getValue()
	 * @see #getVarDeclaration()
	 * @generated
	 */
	EReference getVarDeclaration_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo <em>Version Info</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Info</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo
	 * @generated
	 */
	EClass getVersionInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getAuthor()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Author();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getDate()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Date();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getOrganization <em>Organization</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Organization</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getOrganization()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Organization();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getRemarks <em>Remarks</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remarks</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getRemarks()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Remarks();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getVersion()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.With <em>With</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>With</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.With
	 * @generated
	 */
	EClass getWith();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.With#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variables</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.With#getVariables()
	 * @see #getWith()
	 * @generated
	 */
	EReference getWith_Variables();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement <em>Library Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
	 * @generated
	 */
	EClass getLibraryElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getVersionInfo <em>Version Info</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Version Info</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getVersionInfo()
	 * @see #getLibraryElement()
	 * @generated
	 */
	EReference getLibraryElement_VersionInfo();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getIdentification <em>Identification</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Identification</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getIdentification()
	 * @see #getLibraryElement()
	 * @generated
	 */
	EReference getLibraryElement_Identification();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getPaletteEntry <em>Palette Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getPaletteEntry()
	 * @see #getLibraryElement()
	 * @generated
	 */
	EReference getLibraryElement_PaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilableType <em>Compilable Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compilable Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilableType
	 * @generated
	 */
	EClass getCompilableType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilableType#getCompilerInfo <em>Compiler Info</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Compiler Info</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilableType#getCompilerInfo()
	 * @see #getCompilableType()
	 * @generated
	 */
	EReference getCompilableType_CompilerInfo();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject <em>Configurable Object</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configurable Object</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject
	 * @generated
	 */
	EClass getConfigurableObject();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject#getAttributes()
	 * @see #getConfigurableObject()
	 * @generated
	 */
	EReference getConfigurableObject_Attributes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType <em>Composite FB Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
	 * @generated
	 */
	EClass getCompositeFBType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType#getFBNetwork()
	 * @see #getCompositeFBType()
	 * @generated
	 */
	EReference getCompositeFBType_FBNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm <em>Text Algorithm</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm
	 * @generated
	 */
	EClass getTextAlgorithm();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm#getText <em>Text</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm#getText()
	 * @see #getTextAlgorithm()
	 * @generated
	 */
	EAttribute getTextAlgorithm_Text();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.DataConnection <em>Data Connection</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DataConnection
	 * @generated
	 */
	EClass getDataConnection();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.EventConnection <em>Event Connection</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.EventConnection
	 * @generated
	 */
	EClass getEventConnection();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection <em>Adapter Connection</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection
	 * @generated
	 */
	EClass getAdapterConnection();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface <em>Service Interface</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface
	 * @generated
	 */
	EClass getServiceInterface();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement <em>IInterface Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>IInterface Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
	 * @generated
	 */
	EClass getIInterfaceElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#isIsInput <em>Is Input</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Input</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#isIsInput()
	 * @see #getIInterfaceElement()
	 * @generated
	 */
	EAttribute getIInterfaceElement_IsInput();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getInputConnections <em>Input Connections</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Input Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getInputConnections()
	 * @see #getIInterfaceElement()
	 * @generated
	 */
	EReference getIInterfaceElement_InputConnections();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getOutputConnections <em>Output Connections</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Output Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getOutputConnections()
	 * @see #getIInterfaceElement()
	 * @generated
	 */
	EReference getIInterfaceElement_OutputConnections();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getType()
	 * @see #getIInterfaceElement()
	 * @generated
	 */
	EReference getIInterfaceElement_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getTypeName <em>Type Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getTypeName()
	 * @see #getIInterfaceElement()
	 * @generated
	 */
	EAttribute getIInterfaceElement_TypeName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Value <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Value
	 * @generated
	 */
	EClass getValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Value#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Value#getValue()
	 * @see #getValue()
	 * @generated
	 */
	EAttribute getValue_Value();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration
	 * <em>System Configuration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>System Configuration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration
	 * @generated
	 */
	EClass getSystemConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getDevices <em>Devices</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Devices</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getDevices()
	 * @see #getSystemConfiguration()
	 * @generated
	 */
	EReference getSystemConfiguration_Devices();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getSegments <em>Segments</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Segments</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getSegments()
	 * @see #getSystemConfiguration()
	 * @generated
	 */
	EReference getSystemConfiguration_Segments();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getLinks()
	 * @see #getSystemConfiguration()
	 * @generated
	 */
	EReference getSystemConfiguration_Links();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement <em>INamed Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>INamed Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * @generated
	 */
	EClass getINamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getName()
	 * @see #getINamedElement()
	 * @generated
	 */
	EAttribute getINamedElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getComment()
	 * @see #getINamedElement()
	 * @generated
	 */
	EAttribute getINamedElement_Comment();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB <em>Resource Type FB</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Type FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB
	 * @generated
	 */
	EClass getResourceTypeFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement <em>I4DIAC Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>I4DIAC Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement
	 * @generated
	 */
	EClass getI4DIACElement();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Annotations</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.I4DIACElement#getAnnotations()
	 * @see #getI4DIACElement()
	 * @generated
	 */
	EReference getI4DIACElement_Annotations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SegmentType <em>Segment Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Segment Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SegmentType
	 * @generated
	 */
	EClass getSegmentType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.SegmentType#getVarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SegmentType#getVarDeclaration()
	 * @see #getSegmentType()
	 * @generated
	 */
	EReference getSegmentType_VarDeclaration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType <em>Adapter FB Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType
	 * @generated
	 */
	EClass getAdapterFBType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType#getAdapterType <em>Adapter Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Adapter Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType#getAdapterType()
	 * @see #getAdapterFBType()
	 * @generated
	 */
	EReference getAdapterFBType_AdapterType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Annotation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Annotation#getName()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Annotation#getServity <em>Servity</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Servity</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Annotation#getServity()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Servity();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent <em>Adapter Event</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter Event</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent
	 * @generated
	 */
	EClass getAdapterEvent();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent#getAdapterDeclaration <em>Adapter Declaration</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Adapter Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent#getAdapterDeclaration()
	 * @see #getAdapterEvent()
	 * @generated
	 */
	EReference getAdapterEvent_AdapterDeclaration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Service <em>Service</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Service
	 * @generated
	 */
	EClass getService();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getServiceSequence <em>Service Sequence</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Service Sequence</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Service#getServiceSequence()
	 * @see #getService()
	 * @generated
	 */
	EReference getService_ServiceSequence();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject <em>Typed Configureable Object</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>Typed Configureable Object</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject
	 * @generated
	 */
	EClass getTypedConfigureableObject();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject#getPaletteEntry <em>Palette Entry</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Palette Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject#getPaletteEntry()
	 * @see #getTypedConfigureableObject()
	 * @generated
	 */
	EReference getTypedConfigureableObject_PaletteEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFB <em>Adapter FB</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
	 * @generated
	 */
	EClass getAdapterFB();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFB#getAdapterDecl <em>Adapter Decl</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Adapter Decl</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterFB#getAdapterDecl()
	 * @see #getAdapterFB()
	 * @generated
	 */
	EReference getAdapterFB_AdapterDecl();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Primitive <em>Primitive</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primitive</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Primitive
	 * @generated
	 */
	EClass getPrimitive();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Primitive#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Primitive#getEvent()
	 * @see #getPrimitive()
	 * @generated
	 */
	EAttribute getPrimitive_Event();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Primitive#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Primitive#getParameters()
	 * @see #getPrimitive()
	 * @generated
	 */
	EAttribute getPrimitive_Parameters();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Primitive#getInterface <em>Interface</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Primitive#getInterface()
	 * @see #getPrimitive()
	 * @generated
	 */
	EReference getPrimitive_Interface();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement
	 * <em>Positionable Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Positionable Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.PositionableElement
	 * @generated
	 */
	EClass getPositionableElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getX <em>X</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getX()
	 * @see #getPositionableElement()
	 * @generated
	 */
	EAttribute getPositionableElement_X();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getY <em>Y</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getY()
	 * @see #getPositionableElement()
	 * @generated
	 */
	EAttribute getPositionableElement_Y();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Color <em>Color</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Color</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Color
	 * @generated
	 */
	EClass getColor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getRed <em>Red</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Red</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Color#getRed()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_Red();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getGreen <em>Green</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Green</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Color#getGreen()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_Green();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getBlue <em>Blue</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Blue</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Color#getBlue()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_Blue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement <em>Colorizable Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Colorizable Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement
	 * @generated
	 */
	EClass getColorizableElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Color</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement#getColor()
	 * @see #getColorizableElement()
	 * @generated
	 */
	EReference getColorizableElement_Color();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.IVarElement <em>IVar Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>IVar Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IVarElement
	 * @generated
	 */
	EClass getIVarElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.IVarElement#getVarDeclarations <em>Var Declarations</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declarations</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IVarElement#getVarDeclarations()
	 * @see #getIVarElement()
	 * @generated
	 */
	EReference getIVarElement_VarDeclarations();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration
	 * <em>Attribute Declaration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for class '<em>Attribute Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration
	 * @generated
	 */
	EClass getAttributeDeclaration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration#getInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration#getInitialValue()
	 * @see #getAttributeDeclaration()
	 * @generated
	 */
	EAttribute getAttributeDeclaration_InitialValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedElement <em>Typed Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedElement
	 * @generated
	 */
	EClass getTypedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedElement#getType()
	 * @see #getTypedElement()
	 * @generated
	 */
	EAttribute getTypedElement_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType <em>Simple FB Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
	 * @generated
	 */
	EClass getSimpleFBType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType#getAlgorithm <em>Algorithm</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType#getAlgorithm()
	 * @see #getSimpleFBType()
	 * @generated
	 */
	EReference getSimpleFBType_Algorithm();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType <em>Base FB Type</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
	 * @generated
	 */
	EClass getBaseFBType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getInternalVars <em>Internal Vars</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Internal Vars</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getInternalVars()
	 * @see #getBaseFBType()
	 * @generated
	 */
	EReference getBaseFBType_InternalVars();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getAttributeDeclaration
	 * <em>Attribute Declaration</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the meta object for the reference '<em>Attribute Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Attribute#getAttributeDeclaration()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_AttributeDeclaration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Attribute#getValue()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Value();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getRightInterface <em>Right Interface</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Service#getRightInterface()
	 * @see #getService()
	 * @generated
	 */
	EReference getService_RightInterface();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getLeftInterface <em>Left Interface</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Service#getLeftInterface()
	 * @see #getService()
	 * @generated
	 */
	EReference getService_LeftInterface();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.model.libraryElement.Language <em>Language</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Language</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Language
	 * @generated
	 */
	EEnum getLanguage();

	/**
	 * Returns the meta object for data type
	 * '{@link org.eclipse.core.resources.IProject <em>IProject</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>IProject</em>'.
	 * @see org.eclipse.core.resources.IProject
	 * @model instanceClass="org.eclipse.core.resources.IProject"
	 * @generated
	 */
	EDataType getIProject();

	/**
	 * Returns the meta object for data type
	 * '{@link org.eclipse.core.resources.IFile <em>IFile</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for data type '<em>IFile</em>'.
	 * @see org.eclipse.core.resources.IFile
	 * @model instanceClass="org.eclipse.core.resources.IFile"
	 * @generated
	 */
	EDataType getIFile();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LibraryElementFactory getLibraryElementFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl <em>Adapter Declaration</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterDeclaration()
		 * @generated
		 */
		EClass ADAPTER_DECLARATION = eINSTANCE.getAdapterDeclaration();

		/**
		 * The meta object literal for the '<em><b>Adapter FB</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_DECLARATION__ADAPTER_FB = eINSTANCE.getAdapterDeclaration_AdapterFB();

		/**
		 * The meta object literal for the '<em><b>Palette Entry</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_DECLARATION__PALETTE_ENTRY = eINSTANCE.getAdapterDeclaration_PaletteEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl <em>Adapter Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterType()
		 * @generated
		 */
		EClass ADAPTER_TYPE = eINSTANCE.getAdapterType();

		/**
		 * The meta object literal for the '<em><b>Adapter FB Type</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_TYPE__ADAPTER_FB_TYPE = eINSTANCE.getAdapterType_AdapterFBType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AlgorithmImpl <em>Algorithm</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AlgorithmImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAlgorithm()
		 * @generated
		 */
		EClass ALGORITHM = eINSTANCE.getAlgorithm();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ApplicationImpl <em>Application</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ApplicationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getApplication()
		 * @generated
		 */
		EClass APPLICATION = eINSTANCE.getApplication();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION__FB_NETWORK = eINSTANCE.getApplication_FBNetwork();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BasicFBTypeImpl <em>Basic FB Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.BasicFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getBasicFBType()
		 * @generated
		 */
		EClass BASIC_FB_TYPE = eINSTANCE.getBasicFBType();

		/**
		 * The meta object literal for the '<em><b>ECC</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASIC_FB_TYPE__ECC = eINSTANCE.getBasicFBType_ECC();

		/**
		 * The meta object literal for the '<em><b>Algorithm</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASIC_FB_TYPE__ALGORITHM = eINSTANCE.getBasicFBType_Algorithm();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl <em>Compiler Info</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompilerInfo()
		 * @generated
		 */
		EClass COMPILER_INFO = eINSTANCE.getCompilerInfo();

		/**
		 * The meta object literal for the '<em><b>Compiler</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILER_INFO__COMPILER = eINSTANCE.getCompilerInfo_Compiler();

		/**
		 * The meta object literal for the '<em><b>Classdef</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER_INFO__CLASSDEF = eINSTANCE.getCompilerInfo_Classdef();

		/**
		 * The meta object literal for the '<em><b>Header</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER_INFO__HEADER = eINSTANCE.getCompilerInfo_Header();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerImpl <em>Compiler</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompiler()
		 * @generated
		 */
		EClass COMPILER = eINSTANCE.getCompiler();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER__LANGUAGE = eINSTANCE.getCompiler_Language();

		/**
		 * The meta object literal for the '<em><b>Product</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER__PRODUCT = eINSTANCE.getCompiler_Product();

		/**
		 * The meta object literal for the '<em><b>Vendor</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER__VENDOR = eINSTANCE.getCompiler_Vendor();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER__VERSION = eINSTANCE.getCompiler_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '<em><b>Dx1</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONNECTION__DX1 = eINSTANCE.getConnection_Dx1();

		/**
		 * The meta object literal for the '<em><b>Dx2</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONNECTION__DX2 = eINSTANCE.getConnection_Dx2();

		/**
		 * The meta object literal for the '<em><b>Dy</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute CONNECTION__DY = eINSTANCE.getConnection_Dy();

		/**
		 * The meta object literal for the '<em><b>Res Type Connection</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION__RES_TYPE_CONNECTION = eINSTANCE.getConnection_ResTypeConnection();

		/**
		 * The meta object literal for the '<em><b>Broken Connection</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION__BROKEN_CONNECTION = eINSTANCE.getConnection_BrokenConnection();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__SOURCE = eINSTANCE.getConnection_Source();

		/**
		 * The meta object literal for the '<em><b>Destination</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__DESTINATION = eINSTANCE.getConnection_Destination();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl <em>Device</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDevice()
		 * @generated
		 */
		EClass DEVICE = eINSTANCE.getDevice();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE__RESOURCE = eINSTANCE.getDevice_Resource();

		/**
		 * The meta object literal for the '<em><b>Profile</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE__PROFILE = eINSTANCE.getDevice_Profile();

		/**
		 * The meta object literal for the '<em><b>In Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE__IN_CONNECTIONS = eINSTANCE.getDevice_InConnections();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceTypeImpl <em>Device Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDeviceType()
		 * @generated
		 */
		EClass DEVICE_TYPE = eINSTANCE.getDeviceType();

		/**
		 * The meta object literal for the '<em><b>Var Declaration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE_TYPE__VAR_DECLARATION = eINSTANCE.getDeviceType_VarDeclaration();

		/**
		 * The meta object literal for the '<em><b>Resource Type Name</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference DEVICE_TYPE__RESOURCE_TYPE_NAME = eINSTANCE.getDeviceType_ResourceTypeName();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE_TYPE__RESOURCE = eINSTANCE.getDeviceType_Resource();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE_TYPE__FB_NETWORK = eINSTANCE.getDeviceType_FBNetwork();

		/**
		 * The meta object literal for the '<em><b>Profile</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE_TYPE__PROFILE = eINSTANCE.getDeviceType_Profile();

		/**
		 * The meta object literal for the '<em><b>Attribute Declarations</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference DEVICE_TYPE__ATTRIBUTE_DECLARATIONS = eINSTANCE.getDeviceType_AttributeDeclarations();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl <em>EC Action</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECAction()
		 * @generated
		 */
		EClass EC_ACTION = eINSTANCE.getECAction();

		/**
		 * The meta object literal for the '<em><b>Algorithm</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_ACTION__ALGORITHM = eINSTANCE.getECAction_Algorithm();

		/**
		 * The meta object literal for the '<em><b>Output</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_ACTION__OUTPUT = eINSTANCE.getECAction_Output();

		/**
		 * The meta object literal for the '<em><b>EC State</b></em>' container reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_ACTION__EC_STATE = eINSTANCE.getECAction_ECState();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl <em>ECC</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECC()
		 * @generated
		 */
		EClass ECC = eINSTANCE.getECC();

		/**
		 * The meta object literal for the '<em><b>EC State</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECC__EC_STATE = eINSTANCE.getECC_ECState();

		/**
		 * The meta object literal for the '<em><b>EC Transition</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECC__EC_TRANSITION = eINSTANCE.getECC_ECTransition();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECC__START = eINSTANCE.getECC_Start();

		/**
		 * The meta object literal for the '<em><b>Basic FB Type</b></em>' container reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECC__BASIC_FB_TYPE = eINSTANCE.getECC_BasicFBType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl <em>EC State</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECState()
		 * @generated
		 */
		EClass EC_STATE = eINSTANCE.getECState();

		/**
		 * The meta object literal for the '<em><b>EC Action</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_STATE__EC_ACTION = eINSTANCE.getECState_ECAction();

		/**
		 * The meta object literal for the '<em><b>Out Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_STATE__OUT_TRANSITIONS = eINSTANCE.getECState_OutTransitions();

		/**
		 * The meta object literal for the '<em><b>In Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_STATE__IN_TRANSITIONS = eINSTANCE.getECState_InTransitions();

		/**
		 * The meta object literal for the '<em><b>ECC</b></em>' container reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_STATE__ECC = eINSTANCE.getECState_ECC();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECTransitionImpl <em>EC Transition</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECTransitionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECTransition()
		 * @generated
		 */
		EClass EC_TRANSITION = eINSTANCE.getECTransition();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EC_TRANSITION__COMMENT = eINSTANCE.getECTransition_Comment();

		/**
		 * The meta object literal for the '<em><b>Condition Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EC_TRANSITION__CONDITION_EXPRESSION = eINSTANCE.getECTransition_ConditionExpression();

		/**
		 * The meta object literal for the '<em><b>Destination</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_TRANSITION__DESTINATION = eINSTANCE.getECTransition_Destination();

		/**
		 * The meta object literal for the '<em><b>Condition Event</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_TRANSITION__CONDITION_EVENT = eINSTANCE.getECTransition_ConditionEvent();

		/**
		 * The meta object literal for the '<em><b>ECC</b></em>' container reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_TRANSITION__ECC = eINSTANCE.getECTransition_ECC();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_TRANSITION__SOURCE = eINSTANCE.getECTransition_Source();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getEvent()
		 * @generated
		 */
		EClass EVENT = eINSTANCE.getEvent();

		/**
		 * The meta object literal for the '<em><b>With</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__WITH = eINSTANCE.getEvent_With();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl <em>FB Network</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBNetwork()
		 * @generated
		 */
		EClass FB_NETWORK = eINSTANCE.getFBNetwork();

		/**
		 * The meta object literal for the '<em><b>Network Elements</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference FB_NETWORK__NETWORK_ELEMENTS = eINSTANCE.getFBNetwork_NetworkElements();

		/**
		 * The meta object literal for the '<em><b>Data Connections</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference FB_NETWORK__DATA_CONNECTIONS = eINSTANCE.getFBNetwork_DataConnections();

		/**
		 * The meta object literal for the '<em><b>Event Connections</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference FB_NETWORK__EVENT_CONNECTIONS = eINSTANCE.getFBNetwork_EventConnections();

		/**
		 * The meta object literal for the '<em><b>Adapter Connections</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference FB_NETWORK__ADAPTER_CONNECTIONS = eINSTANCE.getFBNetwork_AdapterConnections();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl <em>FB</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFB()
		 * @generated
		 */
		EClass FB = eINSTANCE.getFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl <em>FB Network Element</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBNetworkElement()
		 * @generated
		 */
		EClass FB_NETWORK_ELEMENT = eINSTANCE.getFBNetworkElement();

		/**
		 * The meta object literal for the '<em><b>Interface</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_NETWORK_ELEMENT__INTERFACE = eINSTANCE.getFBNetworkElement_Interface();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_NETWORK_ELEMENT__MAPPING = eINSTANCE.getFBNetworkElement_Mapping();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBTypeImpl <em>FB Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBType()
		 * @generated
		 */
		EClass FB_TYPE = eINSTANCE.getFBType();

		/**
		 * The meta object literal for the '<em><b>Interface List</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_TYPE__INTERFACE_LIST = eINSTANCE.getFBType_InterfaceList();

		/**
		 * The meta object literal for the '<em><b>Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_TYPE__SERVICE = eINSTANCE.getFBType_Service();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.IdentificationImpl
		 * <em>Identification</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.IdentificationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIdentification()
		 * @generated
		 */
		EClass IDENTIFICATION = eINSTANCE.getIdentification();

		/**
		 * The meta object literal for the '<em><b>Application Domain</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__APPLICATION_DOMAIN = eINSTANCE.getIdentification_ApplicationDomain();

		/**
		 * The meta object literal for the '<em><b>Classification</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__CLASSIFICATION = eINSTANCE.getIdentification_Classification();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__DESCRIPTION = eINSTANCE.getIdentification_Description();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__FUNCTION = eINSTANCE.getIdentification_Function();

		/**
		 * The meta object literal for the '<em><b>Standard</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__STANDARD = eINSTANCE.getIdentification_Standard();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__TYPE = eINSTANCE.getIdentification_Type();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InputPrimitiveImpl
		 * <em>Input Primitive</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.InputPrimitiveImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getInputPrimitive()
		 * @generated
		 */
		EClass INPUT_PRIMITIVE = eINSTANCE.getInputPrimitive();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl
		 * <em>Interface List</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getInterfaceList()
		 * @generated
		 */
		EClass INTERFACE_LIST = eINSTANCE.getInterfaceList();

		/**
		 * The meta object literal for the '<em><b>Plugs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__PLUGS = eINSTANCE.getInterfaceList_Plugs();

		/**
		 * The meta object literal for the '<em><b>Sockets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__SOCKETS = eINSTANCE.getInterfaceList_Sockets();

		/**
		 * The meta object literal for the '<em><b>Event Inputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__EVENT_INPUTS = eINSTANCE.getInterfaceList_EventInputs();

		/**
		 * The meta object literal for the '<em><b>Event Outputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__EVENT_OUTPUTS = eINSTANCE.getInterfaceList_EventOutputs();

		/**
		 * The meta object literal for the '<em><b>Input Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__INPUT_VARS = eINSTANCE.getInterfaceList_InputVars();

		/**
		 * The meta object literal for the '<em><b>Output Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__OUTPUT_VARS = eINSTANCE.getInterfaceList_OutputVars();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LinkImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Segment</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__SEGMENT = eINSTANCE.getLink_Segment();

		/**
		 * The meta object literal for the '<em><b>Device</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__DEVICE = eINSTANCE.getLink_Device();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MappingImpl <em>Mapping</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MappingImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMapping()
		 * @generated
		 */
		EClass MAPPING = eINSTANCE.getMapping();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__FROM = eINSTANCE.getMapping_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MAPPING__TO = eINSTANCE.getMapping_To();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OtherAlgorithmImpl
		 * <em>Other Algorithm</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OtherAlgorithmImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOtherAlgorithm()
		 * @generated
		 */
		EClass OTHER_ALGORITHM = eINSTANCE.getOtherAlgorithm();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OTHER_ALGORITHM__LANGUAGE = eINSTANCE.getOtherAlgorithm_Language();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OutputPrimitiveImpl
		 * <em>Output Primitive</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OutputPrimitiveImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOutputPrimitive()
		 * @generated
		 */
		EClass OUTPUT_PRIMITIVE = eINSTANCE.getOutputPrimitive();

		/**
		 * The meta object literal for the '<em><b>Test Result</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OUTPUT_PRIMITIVE__TEST_RESULT = eINSTANCE.getOutputPrimitive_TestResult();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResource()
		 * @generated
		 */
		EClass RESOURCE = eINSTANCE.getResource();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE__FB_NETWORK = eINSTANCE.getResource_FBNetwork();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute RESOURCE__X = eINSTANCE.getResource_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute RESOURCE__Y = eINSTANCE.getResource_Y();

		/**
		 * The meta object literal for the '<em><b>Device</b></em>' container reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE__DEVICE = eINSTANCE.getResource_Device();

		/**
		 * The meta object literal for the '<em><b>Device Type Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE__DEVICE_TYPE_RESOURCE = eINSTANCE.getResource_DeviceTypeResource();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeNameImpl <em>Resource Type Name</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeNameImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceTypeName()
		 * @generated
		 */
		EClass RESOURCE_TYPE_NAME = eINSTANCE.getResourceTypeName();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_TYPE_NAME__NAME = eINSTANCE.getResourceTypeName_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl <em>Resource Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceType()
		 * @generated
		 */
		EClass RESOURCE_TYPE = eINSTANCE.getResourceType();

		/**
		 * The meta object literal for the '<em><b>Var Declaration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_TYPE__VAR_DECLARATION = eINSTANCE.getResourceType_VarDeclaration();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_TYPE__FB_NETWORK = eINSTANCE.getResourceType_FBNetwork();

		/**
		 * The meta object literal for the '<em><b>Supported FB Types</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_TYPE__SUPPORTED_FB_TYPES = eINSTANCE.getResourceType_SupportedFBTypes();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl <em>Segment</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSegment()
		 * @generated
		 */
		EClass SEGMENT = eINSTANCE.getSegment();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEGMENT__WIDTH = eINSTANCE.getSegment_Width();

		/**
		 * The meta object literal for the '<em><b>Var Declarations</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference SEGMENT__VAR_DECLARATIONS = eINSTANCE.getSegment_VarDeclarations();

		/**
		 * The meta object literal for the '<em><b>Out Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEGMENT__OUT_CONNECTIONS = eINSTANCE.getSegment_OutConnections();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl
		 * <em>Service Sequence</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceSequence()
		 * @generated
		 */
		EClass SERVICE_SEQUENCE = eINSTANCE.getServiceSequence();

		/**
		 * The meta object literal for the '<em><b>Service Transaction</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference SERVICE_SEQUENCE__SERVICE_TRANSACTION = eINSTANCE.getServiceSequence_ServiceTransaction();

		/**
		 * The meta object literal for the '<em><b>Test Result</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_SEQUENCE__TEST_RESULT = eINSTANCE.getServiceSequence_TestResult();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceTransactionImpl <em>Service Transaction</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceTransactionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceTransaction()
		 * @generated
		 */
		EClass SERVICE_TRANSACTION = eINSTANCE.getServiceTransaction();

		/**
		 * The meta object literal for the '<em><b>Input Primitive</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_TRANSACTION__INPUT_PRIMITIVE = eINSTANCE.getServiceTransaction_InputPrimitive();

		/**
		 * The meta object literal for the '<em><b>Output Primitive</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference SERVICE_TRANSACTION__OUTPUT_PRIMITIVE = eINSTANCE.getServiceTransaction_OutputPrimitive();

		/**
		 * The meta object literal for the '<em><b>Test Result</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_TRANSACTION__TEST_RESULT = eINSTANCE.getServiceTransaction_TestResult();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceFBTypeImpl <em>Service Interface FB Type</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceInterfaceFBType()
		 * @generated
		 */
		EClass SERVICE_INTERFACE_FB_TYPE = eINSTANCE.getServiceInterfaceFBType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.STAlgorithmImpl <em>ST Algorithm</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.STAlgorithmImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSTAlgorithm()
		 * @generated
		 */
		EClass ST_ALGORITHM = eINSTANCE.getSTAlgorithm();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl <em>Sub App</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSubApp()
		 * @generated
		 */
		EClass SUB_APP = eINSTANCE.getSubApp();

		/**
		 * The meta object literal for the '<em><b>Sub App Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUB_APP__SUB_APP_NETWORK = eINSTANCE.getSubApp_SubAppNetwork();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppTypeImpl <em>Sub App Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSubAppType()
		 * @generated
		 */
		EClass SUB_APP_TYPE = eINSTANCE.getSubAppType();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl
		 * <em>Automation System</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAutomationSystem()
		 * @generated
		 */
		EClass AUTOMATION_SYSTEM = eINSTANCE.getAutomationSystem();

		/**
		 * The meta object literal for the '<em><b>Application</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTOMATION_SYSTEM__APPLICATION = eINSTANCE.getAutomationSystem_Application();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTOMATION_SYSTEM__MAPPING = eINSTANCE.getAutomationSystem_Mapping();

		/**
		 * The meta object literal for the '<em><b>Palette</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTOMATION_SYSTEM__PALETTE = eINSTANCE.getAutomationSystem_Palette();

		/**
		 * The meta object literal for the '<em><b>System Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION = eINSTANCE.getAutomationSystem_SystemConfiguration();

		/**
		 * The meta object literal for the '<em><b>System File</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTOMATION_SYSTEM__SYSTEM_FILE = eINSTANCE.getAutomationSystem_SystemFile();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl
		 * <em>Var Declaration</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getVarDeclaration()
		 * @generated
		 */
		EClass VAR_DECLARATION = eINSTANCE.getVarDeclaration();

		/**
		 * The meta object literal for the '<em><b>Array Size</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR_DECLARATION__ARRAY_SIZE = eINSTANCE.getVarDeclaration_ArraySize();

		/**
		 * The meta object literal for the '<em><b>Withs</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR_DECLARATION__WITHS = eINSTANCE.getVarDeclaration_Withs();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR_DECLARATION__VALUE = eINSTANCE.getVarDeclaration_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VersionInfoImpl <em>Version Info</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.VersionInfoImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getVersionInfo()
		 * @generated
		 */
		EClass VERSION_INFO = eINSTANCE.getVersionInfo();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__AUTHOR = eINSTANCE.getVersionInfo_Author();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__DATE = eINSTANCE.getVersionInfo_Date();

		/**
		 * The meta object literal for the '<em><b>Organization</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__ORGANIZATION = eINSTANCE.getVersionInfo_Organization();

		/**
		 * The meta object literal for the '<em><b>Remarks</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__REMARKS = eINSTANCE.getVersionInfo_Remarks();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__VERSION = eINSTANCE.getVersionInfo_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.WithImpl <em>With</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.WithImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getWith()
		 * @generated
		 */
		EClass WITH = eINSTANCE.getWith();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference WITH__VARIABLES = eINSTANCE.getWith_Variables();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl
		 * <em>Library Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLibraryElement()
		 * @generated
		 */
		EClass LIBRARY_ELEMENT = eINSTANCE.getLibraryElement();

		/**
		 * The meta object literal for the '<em><b>Version Info</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_ELEMENT__VERSION_INFO = eINSTANCE.getLibraryElement_VersionInfo();

		/**
		 * The meta object literal for the '<em><b>Identification</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_ELEMENT__IDENTIFICATION = eINSTANCE.getLibraryElement_Identification();

		/**
		 * The meta object literal for the '<em><b>Palette Entry</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_ELEMENT__PALETTE_ENTRY = eINSTANCE.getLibraryElement_PaletteEntry();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilableTypeImpl
		 * <em>Compilable Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompilableTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompilableType()
		 * @generated
		 */
		EClass COMPILABLE_TYPE = eINSTANCE.getCompilableType();

		/**
		 * The meta object literal for the '<em><b>Compiler Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILABLE_TYPE__COMPILER_INFO = eINSTANCE.getCompilableType_CompilerInfo();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableObjectImpl <em>Configurable Object</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableObjectImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConfigurableObject()
		 * @generated
		 */
		EClass CONFIGURABLE_OBJECT = eINSTANCE.getConfigurableObject();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURABLE_OBJECT__ATTRIBUTES = eINSTANCE.getConfigurableObject_Attributes();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompositeFBTypeImpl
		 * <em>Composite FB Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompositeFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompositeFBType()
		 * @generated
		 */
		EClass COMPOSITE_FB_TYPE = eINSTANCE.getCompositeFBType();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITE_FB_TYPE__FB_NETWORK = eINSTANCE.getCompositeFBType_FBNetwork();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextAlgorithmImpl
		 * <em>Text Algorithm</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TextAlgorithmImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTextAlgorithm()
		 * @generated
		 */
		EClass TEXT_ALGORITHM = eINSTANCE.getTextAlgorithm();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_ALGORITHM__TEXT = eINSTANCE.getTextAlgorithm_Text();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DataConnectionImpl
		 * <em>Data Connection</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DataConnectionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDataConnection()
		 * @generated
		 */
		EClass DATA_CONNECTION = eINSTANCE.getDataConnection();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventConnectionImpl
		 * <em>Event Connection</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.EventConnectionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getEventConnection()
		 * @generated
		 */
		EClass EVENT_CONNECTION = eINSTANCE.getEventConnection();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterConnectionImpl <em>Adapter Connection</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterConnectionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterConnection()
		 * @generated
		 */
		EClass ADAPTER_CONNECTION = eINSTANCE.getAdapterConnection();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceImpl
		 * <em>Service Interface</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceInterface()
		 * @generated
		 */
		EClass SERVICE_INTERFACE = eINSTANCE.getServiceInterface();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement <em>IInterface Element</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIInterfaceElement()
		 * @generated
		 */
		EClass IINTERFACE_ELEMENT = eINSTANCE.getIInterfaceElement();

		/**
		 * The meta object literal for the '<em><b>Is Input</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IINTERFACE_ELEMENT__IS_INPUT = eINSTANCE.getIInterfaceElement_IsInput();

		/**
		 * The meta object literal for the '<em><b>Input Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference IINTERFACE_ELEMENT__INPUT_CONNECTIONS = eINSTANCE.getIInterfaceElement_InputConnections();

		/**
		 * The meta object literal for the '<em><b>Output Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS = eINSTANCE.getIInterfaceElement_OutputConnections();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference IINTERFACE_ELEMENT__TYPE = eINSTANCE.getIInterfaceElement_Type();

		/**
		 * The meta object literal for the '<em><b>Type Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IINTERFACE_ELEMENT__TYPE_NAME = eINSTANCE.getIInterfaceElement_TypeName();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ValueImpl <em>Value</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ValueImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getValue()
		 * @generated
		 */
		EClass VALUE = eINSTANCE.getValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE__VALUE = eINSTANCE.getValue_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SystemConfigurationImpl <em>System Configuration</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SystemConfigurationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSystemConfiguration()
		 * @generated
		 */
		EClass SYSTEM_CONFIGURATION = eINSTANCE.getSystemConfiguration();

		/**
		 * The meta object literal for the '<em><b>Devices</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_CONFIGURATION__DEVICES = eINSTANCE.getSystemConfiguration_Devices();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_CONFIGURATION__SEGMENTS = eINSTANCE.getSystemConfiguration_Segments();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_CONFIGURATION__LINKS = eINSTANCE.getSystemConfiguration_Links();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement <em>INamed Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getINamedElement()
		 * @generated
		 */
		EClass INAMED_ELEMENT = eINSTANCE.getINamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INAMED_ELEMENT__NAME = eINSTANCE.getINamedElement_Name();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INAMED_ELEMENT__COMMENT = eINSTANCE.getINamedElement_Comment();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeFBImpl
		 * <em>Resource Type FB</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeFBImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceTypeFB()
		 * @generated
		 */
		EClass RESOURCE_TYPE_FB = eINSTANCE.getResourceTypeFB();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.I4DIACElementImpl
		 * <em>I4DIAC Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.I4DIACElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getI4DIACElement()
		 * @generated
		 */
		EClass I4DIAC_ELEMENT = eINSTANCE.getI4DIACElement();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference I4DIAC_ELEMENT__ANNOTATIONS = eINSTANCE.getI4DIACElement_Annotations();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentTypeImpl <em>Segment Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSegmentType()
		 * @generated
		 */
		EClass SEGMENT_TYPE = eINSTANCE.getSegmentType();

		/**
		 * The meta object literal for the '<em><b>Var Declaration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEGMENT_TYPE__VAR_DECLARATION = eINSTANCE.getSegmentType_VarDeclaration();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBTypeImpl
		 * <em>Adapter FB Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterFBType()
		 * @generated
		 */
		EClass ADAPTER_FB_TYPE = eINSTANCE.getAdapterFBType();

		/**
		 * The meta object literal for the '<em><b>Adapter Type</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_FB_TYPE__ADAPTER_TYPE = eINSTANCE.getAdapterFBType_AdapterType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AnnotationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAnnotation()
		 * @generated
		 */
		EClass ANNOTATION = eINSTANCE.getAnnotation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__NAME = eINSTANCE.getAnnotation_Name();

		/**
		 * The meta object literal for the '<em><b>Servity</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__SERVITY = eINSTANCE.getAnnotation_Servity();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterEventImpl <em>Adapter Event</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterEventImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterEvent()
		 * @generated
		 */
		EClass ADAPTER_EVENT = eINSTANCE.getAdapterEvent();

		/**
		 * The meta object literal for the '<em><b>Adapter Declaration</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_EVENT__ADAPTER_DECLARATION = eINSTANCE.getAdapterEvent_AdapterDeclaration();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl <em>Service</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getService()
		 * @generated
		 */
		EClass SERVICE = eINSTANCE.getService();

		/**
		 * The meta object literal for the '<em><b>Service Sequence</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference SERVICE__SERVICE_SEQUENCE = eINSTANCE.getService_ServiceSequence();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TypedConfigureableObjectImpl <em>Typed Configureable Object</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TypedConfigureableObjectImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypedConfigureableObject()
		 * @generated
		 */
		EClass TYPED_CONFIGUREABLE_OBJECT = eINSTANCE.getTypedConfigureableObject();

		/**
		 * The meta object literal for the '<em><b>Palette Entry</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPED_CONFIGUREABLE_OBJECT__PALETTE_ENTRY = eINSTANCE.getTypedConfigureableObject_PaletteEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBImpl <em>Adapter FB</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterFB()
		 * @generated
		 */
		EClass ADAPTER_FB = eINSTANCE.getAdapterFB();

		/**
		 * The meta object literal for the '<em><b>Adapter Decl</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_FB__ADAPTER_DECL = eINSTANCE.getAdapterFB_AdapterDecl();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.PrimitiveImpl <em>Primitive</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.PrimitiveImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPrimitive()
		 * @generated
		 */
		EClass PRIMITIVE = eINSTANCE.getPrimitive();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMITIVE__EVENT = eINSTANCE.getPrimitive_Event();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMITIVE__PARAMETERS = eINSTANCE.getPrimitive_Parameters();

		/**
		 * The meta object literal for the '<em><b>Interface</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMITIVE__INTERFACE = eINSTANCE.getPrimitive_Interface();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.PositionableElementImpl <em>Positionable Element</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.PositionableElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPositionableElement()
		 * @generated
		 */
		EClass POSITIONABLE_ELEMENT = eINSTANCE.getPositionableElement();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute POSITIONABLE_ELEMENT__X = eINSTANCE.getPositionableElement_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute POSITIONABLE_ELEMENT__Y = eINSTANCE.getPositionableElement_Y();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ColorImpl <em>Color</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ColorImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getColor()
		 * @generated
		 */
		EClass COLOR = eINSTANCE.getColor();

		/**
		 * The meta object literal for the '<em><b>Red</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COLOR__RED = eINSTANCE.getColor_Red();

		/**
		 * The meta object literal for the '<em><b>Green</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__GREEN = eINSTANCE.getColor_Green();

		/**
		 * The meta object literal for the '<em><b>Blue</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__BLUE = eINSTANCE.getColor_Blue();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ColorizableElementImpl <em>Colorizable Element</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ColorizableElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getColorizableElement()
		 * @generated
		 */
		EClass COLORIZABLE_ELEMENT = eINSTANCE.getColorizableElement();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLORIZABLE_ELEMENT__COLOR = eINSTANCE.getColorizableElement_Color();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.IVarElement <em>IVar Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.IVarElement
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIVarElement()
		 * @generated
		 */
		EClass IVAR_ELEMENT = eINSTANCE.getIVarElement();

		/**
		 * The meta object literal for the '<em><b>Var Declarations</b></em>'
		 * containment reference list feature. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @generated
		 */
		EReference IVAR_ELEMENT__VAR_DECLARATIONS = eINSTANCE.getIVarElement_VarDeclarations();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl <em>Attribute Declaration</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAttributeDeclaration()
		 * @generated
		 */
		EClass ATTRIBUTE_DECLARATION = eINSTANCE.getAttributeDeclaration();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_DECLARATION__INITIAL_VALUE = eINSTANCE.getAttributeDeclaration_InitialValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedElement <em>Typed Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedElement
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypedElement()
		 * @generated
		 */
		EClass TYPED_ELEMENT = eINSTANCE.getTypedElement();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPED_ELEMENT__TYPE = eINSTANCE.getTypedElement_Type();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl
		 * <em>Simple FB Type</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSimpleFBType()
		 * @generated
		 */
		EClass SIMPLE_FB_TYPE = eINSTANCE.getSimpleFBType();

		/**
		 * The meta object literal for the '<em><b>Algorithm</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_FB_TYPE__ALGORITHM = eINSTANCE.getSimpleFBType_Algorithm();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl <em>Base FB Type</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getBaseFBType()
		 * @generated
		 */
		EClass BASE_FB_TYPE = eINSTANCE.getBaseFBType();

		/**
		 * The meta object literal for the '<em><b>Internal Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE_FB_TYPE__INTERNAL_VARS = eINSTANCE.getBaseFBType_InternalVars();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Attribute Declaration</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__ATTRIBUTE_DECLARATION = eINSTANCE.getAttribute_AttributeDeclaration();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__VALUE = eINSTANCE.getAttribute_Value();

		/**
		 * The meta object literal for the '<em><b>Right Interface</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE__RIGHT_INTERFACE = eINSTANCE.getService_RightInterface();

		/**
		 * The meta object literal for the '<em><b>Left Interface</b></em>' containment reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE__LEFT_INTERFACE = eINSTANCE.getService_LeftInterface();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.Language <em>Language</em>}' enum.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.Language
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLanguage()
		 * @generated
		 */
		EEnum LANGUAGE = eINSTANCE.getLanguage();

		/**
		 * The meta object literal for the '<em>IProject</em>' data type. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.core.resources.IProject
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIProject()
		 * @generated
		 */
		EDataType IPROJECT = eINSTANCE.getIProject();

		/**
		 * The meta object literal for the '<em>IFile</em>' data type. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.core.resources.IFile
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIFile()
		 * @generated
		 */
		EDataType IFILE = eINSTANCE.getIFile();

	}

} // LibraryElementPackage
