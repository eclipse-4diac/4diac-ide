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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.fordiac.ide.model.data.DataPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
 * @model kind="package"
 * @generated
 */
public interface LibraryElementPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "libraryElement"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.model.libraryElement"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "libraryElement"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LibraryElementPackage eINSTANCE = org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableObjectImpl <em>Configurable Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableObjectImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConfigurableObject()
	 * @generated
	 */
	int CONFIGURABLE_OBJECT = 22;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_OBJECT__ATTRIBUTES = 0;

	/**
	 * The number of structural features of the '<em>Configurable Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_OBJECT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 25;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__ATTRIBUTES = CONFIGURABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Broken Connection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__BROKEN_CONNECTION = CONFIGURABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE = CONFIGURABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__DESTINATION = CONFIGURABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Routing Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__ROUTING_DATA = CONFIGURABLE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__COMMENT = CONFIGURABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = CONFIGURABLE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterConnectionImpl <em>Adapter Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterConnectionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterConnection()
	 * @generated
	 */
	int ADAPTER_CONNECTION = 0;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__ATTRIBUTES = CONNECTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Broken Connection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__BROKEN_CONNECTION = CONNECTION__BROKEN_CONNECTION;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__DESTINATION = CONNECTION__DESTINATION;

	/**
	 * The feature id for the '<em><b>Routing Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__ROUTING_DATA = CONNECTION__ROUTING_DATA;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__COMMENT = CONNECTION__COMMENT;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION__FB_NETWORK = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Adapter Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_CONNECTION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement <em>INamed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getINamedElement()
	 * @generated
	 */
	int INAMED_ELEMENT = 54;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INAMED_ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INAMED_ELEMENT__COMMENT = 1;

	/**
	 * The number of structural features of the '<em>INamed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INAMED_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.ITypedElement <em>ITyped Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ITypedElement
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getITypedElement()
	 * @generated
	 */
	int ITYPED_ELEMENT = 57;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITYPED_ELEMENT__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITYPED_ELEMENT__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>ITyped Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITYPED_ELEMENT_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement <em>IInterface Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIInterfaceElement()
	 * @generated
	 */
	int IINTERFACE_ELEMENT = 52;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__NAME = ITYPED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__COMMENT = ITYPED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__ATTRIBUTES = ITYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__IS_INPUT = ITYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__INPUT_CONNECTIONS = ITYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS = ITYPED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT__TYPE = ITYPED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>IInterface Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINTERFACE_ELEMENT_FEATURE_COUNT = ITYPED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl <em>Adapter Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterDeclaration()
	 * @generated
	 */
	int ADAPTER_DECLARATION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__NAME = IINTERFACE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__COMMENT = IINTERFACE_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__ATTRIBUTES = IINTERFACE_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__IS_INPUT = IINTERFACE_ELEMENT__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__INPUT_CONNECTIONS = IINTERFACE_ELEMENT__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__OUTPUT_CONNECTIONS = IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__TYPE = IINTERFACE_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Adapter FB</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__ADAPTER_FB = IINTERFACE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Interface Only Adapter FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION__INTERFACE_ONLY_ADAPTER_FB = IINTERFACE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Adapter Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_DECLARATION_FEATURE_COUNT = IINTERFACE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TypedConfigureableObjectImpl <em>Typed Configureable Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TypedConfigureableObjectImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypedConfigureableObject()
	 * @generated
	 */
	int TYPED_CONFIGUREABLE_OBJECT = 100;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT__NAME = ITYPED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT__COMMENT = ITYPED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT__ATTRIBUTES = ITYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY = ITYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Typed Configureable Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT = ITYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl <em>FB Network Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBNetworkElement()
	 * @generated
	 */
	int FB_NETWORK_ELEMENT = 42;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__NAME = TYPED_CONFIGUREABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__COMMENT = TYPED_CONFIGUREABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__ATTRIBUTES = TYPED_CONFIGUREABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__TYPE_ENTRY = TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__POSITION = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__INTERFACE = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__MAPPING = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT__GROUP = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>FB Network Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_ELEMENT_FEATURE_COUNT = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl <em>FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFB()
	 * @generated
	 */
	int FB = 40;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__NAME = FB_NETWORK_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__COMMENT = FB_NETWORK_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__ATTRIBUTES = FB_NETWORK_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__TYPE_ENTRY = FB_NETWORK_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__POSITION = FB_NETWORK_ELEMENT__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__INTERFACE = FB_NETWORK_ELEMENT__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__MAPPING = FB_NETWORK_ELEMENT__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__GROUP = FB_NETWORK_ELEMENT__GROUP;

	/**
	 * The number of structural features of the '<em>FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_FEATURE_COUNT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBImpl <em>Adapter FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterFB()
	 * @generated
	 */
	int ADAPTER_FB = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__NAME = FB__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__COMMENT = FB__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__ATTRIBUTES = FB__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__TYPE_ENTRY = FB__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__POSITION = FB__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__INTERFACE = FB__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__MAPPING = FB__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__GROUP = FB__GROUP;

	/**
	 * The feature id for the '<em><b>Adapter Decl</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB__ADAPTER_DECL = FB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Adapter FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_FB_FEATURE_COUNT = FB_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl <em>Library Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLibraryElement()
	 * @generated
	 */
	int LIBRARY_ELEMENT = 59;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__ATTRIBUTES = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__VERSION_INFO = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__IDENTIFICATION = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__COMPILER_INFO = INAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT__TYPE_ENTRY = INAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Library Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_ELEMENT_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl <em>Adapter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterType()
	 * @generated
	 */
	int ADAPTER_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__NAME = DataPackage.DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__COMMENT = DataPackage.DATA_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__ATTRIBUTES = DataPackage.DATA_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__VERSION_INFO = DataPackage.DATA_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__IDENTIFICATION = DataPackage.DATA_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__COMPILER_INFO = DataPackage.DATA_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__TYPE_ENTRY = DataPackage.DATA_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__INTERFACE_LIST = DataPackage.DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__SERVICE = DataPackage.DATA_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Plug Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE__PLUG_TYPE = DataPackage.DATA_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Adapter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_TYPE_FEATURE_COUNT = DataPackage.DATA_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.ICallable <em>ICallable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ICallable
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getICallable()
	 * @generated
	 */
	int ICALLABLE = 50;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICALLABLE__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICALLABLE__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>ICallable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICALLABLE_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AlgorithmImpl <em>Algorithm</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AlgorithmImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAlgorithm()
	 * @generated
	 */
	int ALGORITHM = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__NAME = ICALLABLE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM__COMMENT = ICALLABLE__COMMENT;

	/**
	 * The number of structural features of the '<em>Algorithm</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALGORITHM_FEATURE_COUNT = ICALLABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ApplicationImpl <em>Application</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ApplicationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getApplication()
	 * @generated
	 */
	int APPLICATION = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION__ATTRIBUTES = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION__FB_NETWORK = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ArraySizeImpl <em>Array Size</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ArraySizeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getArraySize()
	 * @generated
	 */
	int ARRAY_SIZE = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_SIZE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Var Declaration</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_SIZE__VAR_DECLARATION = 1;

	/**
	 * The number of structural features of the '<em>Array Size</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_SIZE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__NAME = ITYPED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__COMMENT = ITYPED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attribute Declaration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__ATTRIBUTE_DECLARATION = ITYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__TYPE = ITYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__VALUE = ITYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = ITYPED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl <em>Attribute Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAttributeDeclaration()
	 * @generated
	 */
	int ATTRIBUTE_DECLARATION = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__NAME = ITYPED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__COMMENT = ITYPED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__ATTRIBUTES = ITYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__VERSION_INFO = ITYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__IDENTIFICATION = ITYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__COMPILER_INFO = ITYPED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__TYPE_ENTRY = ITYPED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__TYPE = ITYPED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Attribute Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION_FEATURE_COUNT = ITYPED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBTypeImpl <em>FB Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBType()
	 * @generated
	 */
	int FB_TYPE = 43;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__NAME = LIBRARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__COMMENT = LIBRARY_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__ATTRIBUTES = LIBRARY_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__VERSION_INFO = LIBRARY_ELEMENT__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__IDENTIFICATION = LIBRARY_ELEMENT__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__COMPILER_INFO = LIBRARY_ELEMENT__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__TYPE_ENTRY = LIBRARY_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__INTERFACE_LIST = LIBRARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE__SERVICE = LIBRARY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>FB Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_TYPE_FEATURE_COUNT = LIBRARY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl <em>Base FB Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getBaseFBType()
	 * @generated
	 */
	int BASE_FB_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__NAME = FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__COMMENT = FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__ATTRIBUTES = FB_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__VERSION_INFO = FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__IDENTIFICATION = FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__COMPILER_INFO = FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__TYPE_ENTRY = FB_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__INTERFACE_LIST = FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__SERVICE = FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>Internal Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__INTERNAL_VARS = FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Internal Const Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__INTERNAL_CONST_VARS = FB_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Internal Fbs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__INTERNAL_FBS = FB_TYPE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Algorithm</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__ALGORITHM = FB_TYPE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Methods</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__METHODS = FB_TYPE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Callables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE__CALLABLES = FB_TYPE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Base FB Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_FB_TYPE_FEATURE_COUNT = FB_TYPE_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BasicFBTypeImpl <em>Basic FB Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.BasicFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getBasicFBType()
	 * @generated
	 */
	int BASIC_FB_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__NAME = BASE_FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__COMMENT = BASE_FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__ATTRIBUTES = BASE_FB_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__VERSION_INFO = BASE_FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__IDENTIFICATION = BASE_FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__COMPILER_INFO = BASE_FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__TYPE_ENTRY = BASE_FB_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__INTERFACE_LIST = BASE_FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__SERVICE = BASE_FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>Internal Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__INTERNAL_VARS = BASE_FB_TYPE__INTERNAL_VARS;

	/**
	 * The feature id for the '<em><b>Internal Const Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__INTERNAL_CONST_VARS = BASE_FB_TYPE__INTERNAL_CONST_VARS;

	/**
	 * The feature id for the '<em><b>Internal Fbs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__INTERNAL_FBS = BASE_FB_TYPE__INTERNAL_FBS;

	/**
	 * The feature id for the '<em><b>Algorithm</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__ALGORITHM = BASE_FB_TYPE__ALGORITHM;

	/**
	 * The feature id for the '<em><b>Methods</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__METHODS = BASE_FB_TYPE__METHODS;

	/**
	 * The feature id for the '<em><b>Callables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__CALLABLES = BASE_FB_TYPE__CALLABLES;

	/**
	 * The feature id for the '<em><b>ECC</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE__ECC = BASE_FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Basic FB Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_TYPE_FEATURE_COUNT = BASE_FB_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl <em>Automation System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAutomationSystem()
	 * @generated
	 */
	int AUTOMATION_SYSTEM = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__NAME = LIBRARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__COMMENT = LIBRARY_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__ATTRIBUTES = LIBRARY_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__VERSION_INFO = LIBRARY_ELEMENT__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__IDENTIFICATION = LIBRARY_ELEMENT__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__COMPILER_INFO = LIBRARY_ELEMENT__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__TYPE_ENTRY = LIBRARY_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Application</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__APPLICATION = LIBRARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__MAPPING = LIBRARY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>System Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION = LIBRARY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Command Stack</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM__COMMAND_STACK = LIBRARY_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Automation System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATION_SYSTEM_FEATURE_COUNT = LIBRARY_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CFBInstanceImpl <em>CFB Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CFBInstanceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCFBInstance()
	 * @generated
	 */
	int CFB_INSTANCE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFB_INSTANCE__NAME = FB__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFB_INSTANCE__COMMENT = FB__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFB_INSTANCE__ATTRIBUTES = FB__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFB_INSTANCE__TYPE_ENTRY = FB__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFB_INSTANCE__POSITION = FB__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFB_INSTANCE__INTERFACE = FB__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFB_INSTANCE__MAPPING = FB__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFB_INSTANCE__GROUP = FB__GROUP;

	/**
	 * The feature id for the '<em><b>Cfb Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFB_INSTANCE__CFB_NETWORK = FB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>CFB Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CFB_INSTANCE_FEATURE_COUNT = FB_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ColorImpl <em>Color</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ColorImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getColor()
	 * @generated
	 */
	int COLOR = 13;

	/**
	 * The feature id for the '<em><b>Red</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__RED = 0;

	/**
	 * The feature id for the '<em><b>Green</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__GREEN = 1;

	/**
	 * The feature id for the '<em><b>Blue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__BLUE = 2;

	/**
	 * The number of structural features of the '<em>Color</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ColorizableElementImpl <em>Colorizable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ColorizableElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getColorizableElement()
	 * @generated
	 */
	int COLORIZABLE_ELEMENT = 14;

	/**
	 * The feature id for the '<em><b>Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLORIZABLE_ELEMENT__COLOR = 0;

	/**
	 * The number of structural features of the '<em>Colorizable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLORIZABLE_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CommentImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__NAME = FB_NETWORK_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__COMMENT = FB_NETWORK_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__ATTRIBUTES = FB_NETWORK_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__TYPE_ENTRY = FB_NETWORK_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__POSITION = FB_NETWORK_ELEMENT__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__INTERFACE = FB_NETWORK_ELEMENT__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__MAPPING = FB_NETWORK_ELEMENT__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__GROUP = FB_NETWORK_ELEMENT__GROUP;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__WIDTH = FB_NETWORK_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT__HEIGHT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CommunicationChannelImpl <em>Communication Channel</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CommunicationChannelImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCommunicationChannel()
	 * @generated
	 */
	int COMMUNICATION_CHANNEL = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_CHANNEL__NAME = FB__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_CHANNEL__COMMENT = FB__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_CHANNEL__ATTRIBUTES = FB__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_CHANNEL__TYPE_ENTRY = FB__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_CHANNEL__POSITION = FB__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_CHANNEL__INTERFACE = FB__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_CHANNEL__MAPPING = FB__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_CHANNEL__GROUP = FB__GROUP;

	/**
	 * The number of structural features of the '<em>Communication Channel</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_CHANNEL_FEATURE_COUNT = FB_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration <em>Communication Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCommunicationConfiguration()
	 * @generated
	 */
	int COMMUNICATION_CONFIGURATION = 17;

	/**
	 * The number of structural features of the '<em>Communication Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_CONFIGURATION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MappingTargetImpl <em>Mapping Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MappingTargetImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMappingTarget()
	 * @generated
	 */
	int MAPPING_TARGET = 63;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_TARGET__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_TARGET__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>Mapping Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_TARGET_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CommunicationMappingTargetImpl <em>Communication Mapping Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CommunicationMappingTargetImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCommunicationMappingTarget()
	 * @generated
	 */
	int COMMUNICATION_MAPPING_TARGET = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_MAPPING_TARGET__NAME = MAPPING_TARGET__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_MAPPING_TARGET__COMMENT = MAPPING_TARGET__COMMENT;

	/**
	 * The feature id for the '<em><b>Mapped Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_MAPPING_TARGET__MAPPED_ELEMENTS = MAPPING_TARGET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Communication Mapping Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_MAPPING_TARGET_FEATURE_COUNT = MAPPING_TARGET_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerImpl <em>Compiler</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompiler()
	 * @generated
	 */
	int COMPILER = 19;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER__LANGUAGE = 0;

	/**
	 * The feature id for the '<em><b>Product</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER__PRODUCT = 1;

	/**
	 * The feature id for the '<em><b>Vendor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER__VENDOR = 2;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER__VERSION = 3;

	/**
	 * The number of structural features of the '<em>Compiler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl <em>Compiler Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompilerInfo()
	 * @generated
	 */
	int COMPILER_INFO = 20;

	/**
	 * The feature id for the '<em><b>Compiler</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER_INFO__COMPILER = 0;

	/**
	 * The feature id for the '<em><b>Classdef</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER_INFO__CLASSDEF = 1;

	/**
	 * The feature id for the '<em><b>Header</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER_INFO__HEADER = 2;

	/**
	 * The feature id for the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER_INFO__PACKAGE_NAME = 3;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER_INFO__IMPORTS = 4;

	/**
	 * The number of structural features of the '<em>Compiler Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPILER_INFO_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompositeFBTypeImpl <em>Composite FB Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompositeFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompositeFBType()
	 * @generated
	 */
	int COMPOSITE_FB_TYPE = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__NAME = FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__COMMENT = FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__ATTRIBUTES = FB_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__VERSION_INFO = FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__IDENTIFICATION = FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__COMPILER_INFO = FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__TYPE_ENTRY = FB_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__INTERFACE_LIST = FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__SERVICE = FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE__FB_NETWORK = FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Composite FB Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_TYPE_FEATURE_COUNT = FB_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableFBImpl <em>Configurable FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableFBImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConfigurableFB()
	 * @generated
	 */
	int CONFIGURABLE_FB = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FB__NAME = FB__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FB__COMMENT = FB__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FB__ATTRIBUTES = FB__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FB__TYPE_ENTRY = FB__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FB__POSITION = FB__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FB__INTERFACE = FB__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FB__MAPPING = FB__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FB__GROUP = FB__GROUP;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FB__DATA_TYPE = FB_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Configurable FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_FB_FEATURE_COUNT = FB_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableMoveFBImpl <em>Configurable Move FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableMoveFBImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConfigurableMoveFB()
	 * @generated
	 */
	int CONFIGURABLE_MOVE_FB = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_MOVE_FB__NAME = CONFIGURABLE_FB__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_MOVE_FB__COMMENT = CONFIGURABLE_FB__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_MOVE_FB__ATTRIBUTES = CONFIGURABLE_FB__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_MOVE_FB__TYPE_ENTRY = CONFIGURABLE_FB__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_MOVE_FB__POSITION = CONFIGURABLE_FB__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_MOVE_FB__INTERFACE = CONFIGURABLE_FB__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_MOVE_FB__MAPPING = CONFIGURABLE_FB__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_MOVE_FB__GROUP = CONFIGURABLE_FB__GROUP;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_MOVE_FB__DATA_TYPE = CONFIGURABLE_FB__DATA_TYPE;

	/**
	 * The number of structural features of the '<em>Configurable Move FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURABLE_MOVE_FB_FEATURE_COUNT = CONFIGURABLE_FB_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl <em>Connection Routing Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConnectionRoutingData()
	 * @generated
	 */
	int CONNECTION_ROUTING_DATA = 26;

	/**
	 * The feature id for the '<em><b>Dx1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ROUTING_DATA__DX1 = 0;

	/**
	 * The feature id for the '<em><b>Dx2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ROUTING_DATA__DX2 = 1;

	/**
	 * The feature id for the '<em><b>Dy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ROUTING_DATA__DY = 2;

	/**
	 * The feature id for the '<em><b>Needs Validation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ROUTING_DATA__NEEDS_VALIDATION = 3;

	/**
	 * The number of structural features of the '<em>Connection Routing Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_ROUTING_DATA_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DataConnectionImpl <em>Data Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DataConnectionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDataConnection()
	 * @generated
	 */
	int DATA_CONNECTION = 27;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__ATTRIBUTES = CONNECTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Broken Connection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__BROKEN_CONNECTION = CONNECTION__BROKEN_CONNECTION;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__DESTINATION = CONNECTION__DESTINATION;

	/**
	 * The feature id for the '<em><b>Routing Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__ROUTING_DATA = CONNECTION__ROUTING_DATA;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__COMMENT = CONNECTION__COMMENT;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION__FB_NETWORK = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Data Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONNECTION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.StructManipulatorImpl <em>Struct Manipulator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.StructManipulatorImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getStructManipulator()
	 * @generated
	 */
	int STRUCT_MANIPULATOR = 93;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_MANIPULATOR__NAME = CONFIGURABLE_FB__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_MANIPULATOR__COMMENT = CONFIGURABLE_FB__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_MANIPULATOR__ATTRIBUTES = CONFIGURABLE_FB__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_MANIPULATOR__TYPE_ENTRY = CONFIGURABLE_FB__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_MANIPULATOR__POSITION = CONFIGURABLE_FB__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_MANIPULATOR__INTERFACE = CONFIGURABLE_FB__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_MANIPULATOR__MAPPING = CONFIGURABLE_FB__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_MANIPULATOR__GROUP = CONFIGURABLE_FB__GROUP;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_MANIPULATOR__DATA_TYPE = CONFIGURABLE_FB__DATA_TYPE;

	/**
	 * The number of structural features of the '<em>Struct Manipulator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_MANIPULATOR_FEATURE_COUNT = CONFIGURABLE_FB_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DemultiplexerImpl <em>Demultiplexer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DemultiplexerImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDemultiplexer()
	 * @generated
	 */
	int DEMULTIPLEXER = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER__NAME = STRUCT_MANIPULATOR__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER__COMMENT = STRUCT_MANIPULATOR__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER__ATTRIBUTES = STRUCT_MANIPULATOR__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER__TYPE_ENTRY = STRUCT_MANIPULATOR__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER__POSITION = STRUCT_MANIPULATOR__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER__INTERFACE = STRUCT_MANIPULATOR__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER__MAPPING = STRUCT_MANIPULATOR__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER__GROUP = STRUCT_MANIPULATOR__GROUP;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER__DATA_TYPE = STRUCT_MANIPULATOR__DATA_TYPE;

	/**
	 * The feature id for the '<em><b>Is Configured</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER__IS_CONFIGURED = STRUCT_MANIPULATOR_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Demultiplexer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEMULTIPLEXER_FEATURE_COUNT = STRUCT_MANIPULATOR_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl <em>Device</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDevice()
	 * @generated
	 */
	int DEVICE = 29;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__NAME = TYPED_CONFIGUREABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__COMMENT = TYPED_CONFIGUREABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__ATTRIBUTES = TYPED_CONFIGUREABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__TYPE_ENTRY = TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__POSITION = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__COLOR = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__VAR_DECLARATIONS = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__RESOURCE = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Profile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__PROFILE = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>In Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE__IN_CONNECTIONS = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Device</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_FEATURE_COUNT = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceTypeImpl <em>Device Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDeviceType()
	 * @generated
	 */
	int DEVICE_TYPE = 30;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__NAME = LIBRARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__COMMENT = LIBRARY_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__ATTRIBUTES = LIBRARY_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__VERSION_INFO = LIBRARY_ELEMENT__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__IDENTIFICATION = LIBRARY_ELEMENT__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__COMPILER_INFO = LIBRARY_ELEMENT__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__TYPE_ENTRY = LIBRARY_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Var Declaration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__VAR_DECLARATION = LIBRARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Resource Type Name</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__RESOURCE_TYPE_NAME = LIBRARY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__RESOURCE = LIBRARY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__FB_NETWORK = LIBRARY_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Profile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__PROFILE = LIBRARY_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Attribute Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE__ATTRIBUTE_DECLARATIONS = LIBRARY_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Device Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEVICE_TYPE_FEATURE_COUNT = LIBRARY_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl <em>EC Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECAction()
	 * @generated
	 */
	int EC_ACTION = 31;

	/**
	 * The feature id for the '<em><b>Algorithm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_ACTION__ALGORITHM = 0;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_ACTION__OUTPUT = 1;

	/**
	 * The feature id for the '<em><b>EC State</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_ACTION__EC_STATE = 2;

	/**
	 * The number of structural features of the '<em>EC Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_ACTION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl <em>ECC</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECC()
	 * @generated
	 */
	int ECC = 32;

	/**
	 * The feature id for the '<em><b>EC State</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECC__EC_STATE = 0;

	/**
	 * The feature id for the '<em><b>EC Transition</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECC__EC_TRANSITION = 1;

	/**
	 * The feature id for the '<em><b>Start</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECC__START = 2;

	/**
	 * The feature id for the '<em><b>Basic FB Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECC__BASIC_FB_TYPE = 3;

	/**
	 * The number of structural features of the '<em>ECC</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ECC_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl <em>EC State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECState()
	 * @generated
	 */
	int EC_STATE = 33;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__POSITION = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>EC Action</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__EC_ACTION = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Out Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__OUT_TRANSITIONS = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>In Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__IN_TRANSITIONS = INAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>ECC</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE__ECC = INAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>EC State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_STATE_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.PositionableElementImpl <em>Positionable Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.PositionableElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPositionableElement()
	 * @generated
	 */
	int POSITIONABLE_ELEMENT = 72;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITIONABLE_ELEMENT__POSITION = 0;

	/**
	 * The number of structural features of the '<em>Positionable Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITIONABLE_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECTransitionImpl <em>EC Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECTransitionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECTransition()
	 * @generated
	 */
	int EC_TRANSITION = 34;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__POSITION = POSITIONABLE_ELEMENT__POSITION;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__COMMENT = POSITIONABLE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__CONDITION_EXPRESSION = POSITIONABLE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__SOURCE = POSITIONABLE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__DESTINATION = POSITIONABLE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Condition Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__CONDITION_EVENT = POSITIONABLE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>ECC</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION__ECC = POSITIONABLE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>EC Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EC_TRANSITION_FEATURE_COUNT = POSITIONABLE_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerDataTypeImpl <em>Error Marker Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerDataTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getErrorMarkerDataType()
	 * @generated
	 */
	int ERROR_MARKER_DATA_TYPE = 35;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_DATA_TYPE__NAME = DataPackage.DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_DATA_TYPE__COMMENT = DataPackage.DATA_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_DATA_TYPE__ATTRIBUTES = DataPackage.DATA_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_DATA_TYPE__VERSION_INFO = DataPackage.DATA_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_DATA_TYPE__IDENTIFICATION = DataPackage.DATA_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_DATA_TYPE__COMPILER_INFO = DataPackage.DATA_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_DATA_TYPE__TYPE_ENTRY = DataPackage.DATA_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Error Marker Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_DATA_TYPE_FEATURE_COUNT = DataPackage.DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerFBNElementImpl <em>Error Marker FBN Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerFBNElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getErrorMarkerFBNElement()
	 * @generated
	 */
	int ERROR_MARKER_FBN_ELEMENT = 36;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_FBN_ELEMENT__NAME = FB_NETWORK_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_FBN_ELEMENT__COMMENT = FB_NETWORK_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_FBN_ELEMENT__ATTRIBUTES = FB_NETWORK_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_FBN_ELEMENT__TYPE_ENTRY = FB_NETWORK_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_FBN_ELEMENT__POSITION = FB_NETWORK_ELEMENT__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_FBN_ELEMENT__INTERFACE = FB_NETWORK_ELEMENT__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_FBN_ELEMENT__MAPPING = FB_NETWORK_ELEMENT__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_FBN_ELEMENT__GROUP = FB_NETWORK_ELEMENT__GROUP;

	/**
	 * The feature id for the '<em><b>Repaired Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_FBN_ELEMENT__REPAIRED_ELEMENT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Error Marker FBN Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_FBN_ELEMENT_FEATURE_COUNT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerInterfaceImpl <em>Error Marker Interface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerInterfaceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getErrorMarkerInterface()
	 * @generated
	 */
	int ERROR_MARKER_INTERFACE = 37;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_INTERFACE__NAME = IINTERFACE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_INTERFACE__COMMENT = IINTERFACE_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_INTERFACE__ATTRIBUTES = IINTERFACE_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_INTERFACE__IS_INPUT = IINTERFACE_ELEMENT__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_INTERFACE__INPUT_CONNECTIONS = IINTERFACE_ELEMENT__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_INTERFACE__OUTPUT_CONNECTIONS = IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_INTERFACE__TYPE = IINTERFACE_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_INTERFACE__VALUE = IINTERFACE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Error Marker Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERROR_MARKER_INTERFACE_FEATURE_COUNT = IINTERFACE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 38;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NAME = IINTERFACE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__COMMENT = IINTERFACE_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__ATTRIBUTES = IINTERFACE_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__IS_INPUT = IINTERFACE_ELEMENT__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__INPUT_CONNECTIONS = IINTERFACE_ELEMENT__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__OUTPUT_CONNECTIONS = IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__TYPE = IINTERFACE_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>With</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__WITH = IINTERFACE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FEATURE_COUNT = IINTERFACE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventConnectionImpl <em>Event Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.EventConnectionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getEventConnection()
	 * @generated
	 */
	int EVENT_CONNECTION = 39;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__ATTRIBUTES = CONNECTION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Broken Connection</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__BROKEN_CONNECTION = CONNECTION__BROKEN_CONNECTION;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__DESTINATION = CONNECTION__DESTINATION;

	/**
	 * The feature id for the '<em><b>Routing Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__ROUTING_DATA = CONNECTION__ROUTING_DATA;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__COMMENT = CONNECTION__COMMENT;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION__FB_NETWORK = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_CONNECTION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl <em>FB Network</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBNetwork()
	 * @generated
	 */
	int FB_NETWORK = 41;

	/**
	 * The feature id for the '<em><b>Network Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK__NETWORK_ELEMENTS = 0;

	/**
	 * The feature id for the '<em><b>Data Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK__DATA_CONNECTIONS = 1;

	/**
	 * The feature id for the '<em><b>Event Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK__EVENT_CONNECTIONS = 2;

	/**
	 * The feature id for the '<em><b>Adapter Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK__ADAPTER_CONNECTIONS = 3;

	/**
	 * The number of structural features of the '<em>FB Network</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_NETWORK_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 44;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__NAME = ICALLABLE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__COMMENT = ICALLABLE__COMMENT;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = ICALLABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionBodyImpl <em>Function Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionBodyImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFunctionBody()
	 * @generated
	 */
	int FUNCTION_BODY = 45;

	/**
	 * The number of structural features of the '<em>Function Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_BODY_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionFBTypeImpl <em>Function FB Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFunctionFBType()
	 * @generated
	 */
	int FUNCTION_FB_TYPE = 46;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE__NAME = FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE__COMMENT = FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE__ATTRIBUTES = FB_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE__VERSION_INFO = FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE__IDENTIFICATION = FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE__COMPILER_INFO = FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE__TYPE_ENTRY = FB_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE__INTERFACE_LIST = FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE__SERVICE = FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>Body</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE__BODY = FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Function FB Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_TYPE_FEATURE_COUNT = FB_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.GlobalConstantsImpl <em>Global Constants</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.GlobalConstantsImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getGlobalConstants()
	 * @generated
	 */
	int GLOBAL_CONSTANTS = 47;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTANTS__NAME = LIBRARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTANTS__COMMENT = LIBRARY_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTANTS__ATTRIBUTES = LIBRARY_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTANTS__VERSION_INFO = LIBRARY_ELEMENT__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTANTS__IDENTIFICATION = LIBRARY_ELEMENT__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTANTS__COMPILER_INFO = LIBRARY_ELEMENT__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTANTS__TYPE_ENTRY = LIBRARY_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Constants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTANTS__CONSTANTS = LIBRARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTANTS__SOURCE = LIBRARY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Global Constants</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_CONSTANTS_FEATURE_COUNT = LIBRARY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.GroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.GroupImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 48;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__NAME = FB_NETWORK_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__COMMENT = FB_NETWORK_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__ATTRIBUTES = FB_NETWORK_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__TYPE_ENTRY = FB_NETWORK_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__POSITION = FB_NETWORK_ELEMENT__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__INTERFACE = FB_NETWORK_ELEMENT__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__MAPPING = FB_NETWORK_ELEMENT__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__GROUP = FB_NETWORK_ELEMENT__GROUP;

	/**
	 * The feature id for the '<em><b>Group Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__GROUP_ELEMENTS = FB_NETWORK_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__WIDTH = FB_NETWORK_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__HEIGHT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__LOCKED = FB_NETWORK_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_FEATURE_COUNT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.HiddenElementImpl <em>Hidden Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.HiddenElementImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getHiddenElement()
	 * @generated
	 */
	int HIDDEN_ELEMENT = 49;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HIDDEN_ELEMENT__ATTRIBUTES = CONFIGURABLE_OBJECT__ATTRIBUTES;

	/**
	 * The number of structural features of the '<em>Hidden Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HIDDEN_ELEMENT_FEATURE_COUNT = CONFIGURABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.IdentificationImpl <em>Identification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.IdentificationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIdentification()
	 * @generated
	 */
	int IDENTIFICATION = 51;

	/**
	 * The feature id for the '<em><b>Application Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__APPLICATION_DOMAIN = 0;

	/**
	 * The feature id for the '<em><b>Classification</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__CLASSIFICATION = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__FUNCTION = 3;

	/**
	 * The feature id for the '<em><b>Standard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__STANDARD = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION__TYPE = 5;

	/**
	 * The number of structural features of the '<em>Identification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDENTIFICATION_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ImportImpl <em>Import</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ImportImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getImport()
	 * @generated
	 */
	int IMPORT = 53;

	/**
	 * The feature id for the '<em><b>Imported Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT__IMPORTED_NAMESPACE = 0;

	/**
	 * The number of structural features of the '<em>Import</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.PrimitiveImpl <em>Primitive</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.PrimitiveImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPrimitive()
	 * @generated
	 */
	int PRIMITIVE = 73;

	/**
	 * The feature id for the '<em><b>Event</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE__EVENT = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE__PARAMETERS = 1;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE__INTERFACE = 2;

	/**
	 * The number of structural features of the '<em>Primitive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIMITIVE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InputPrimitiveImpl <em>Input Primitive</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.InputPrimitiveImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getInputPrimitive()
	 * @generated
	 */
	int INPUT_PRIMITIVE = 55;

	/**
	 * The feature id for the '<em><b>Event</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PRIMITIVE__EVENT = PRIMITIVE__EVENT;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PRIMITIVE__PARAMETERS = PRIMITIVE__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PRIMITIVE__INTERFACE = PRIMITIVE__INTERFACE;

	/**
	 * The number of structural features of the '<em>Input Primitive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INPUT_PRIMITIVE_FEATURE_COUNT = PRIMITIVE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl <em>Interface List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getInterfaceList()
	 * @generated
	 */
	int INTERFACE_LIST = 56;

	/**
	 * The feature id for the '<em><b>Event Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__EVENT_INPUTS = 0;

	/**
	 * The feature id for the '<em><b>Event Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__EVENT_OUTPUTS = 1;

	/**
	 * The feature id for the '<em><b>Input Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__INPUT_VARS = 2;

	/**
	 * The feature id for the '<em><b>Output Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__OUTPUT_VARS = 3;

	/**
	 * The feature id for the '<em><b>In Out Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__IN_OUT_VARS = 4;

	/**
	 * The feature id for the '<em><b>Out Mapped In Out Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS = 5;

	/**
	 * The feature id for the '<em><b>Plugs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__PLUGS = 6;

	/**
	 * The feature id for the '<em><b>Sockets</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__SOCKETS = 7;

	/**
	 * The feature id for the '<em><b>Error Marker</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST__ERROR_MARKER = 8;

	/**
	 * The number of structural features of the '<em>Interface List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERFACE_LIST_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.IVarElement <em>IVar Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IVarElement
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIVarElement()
	 * @generated
	 */
	int IVAR_ELEMENT = 58;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IVAR_ELEMENT__VAR_DECLARATIONS = 0;

	/**
	 * The number of structural features of the '<em>IVar Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IVAR_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LinkImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 60;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__ATTRIBUTES = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Segment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__SEGMENT = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Device</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__DEVICE = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl <em>Var Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getVarDeclaration()
	 * @generated
	 */
	int VAR_DECLARATION = 104;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__NAME = IINTERFACE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__COMMENT = IINTERFACE_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__ATTRIBUTES = IINTERFACE_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__IS_INPUT = IINTERFACE_ELEMENT__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__INPUT_CONNECTIONS = IINTERFACE_ELEMENT__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__OUTPUT_CONNECTIONS = IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__TYPE = IINTERFACE_ELEMENT__TYPE;

	/**
	 * The feature id for the '<em><b>Array Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__ARRAY_SIZE = IINTERFACE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Withs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__WITHS = IINTERFACE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION__VALUE = IINTERFACE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Var Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_DECLARATION_FEATURE_COUNT = IINTERFACE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LocalVariableImpl <em>Local Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LocalVariableImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLocalVariable()
	 * @generated
	 */
	int LOCAL_VARIABLE = 61;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__NAME = VAR_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__COMMENT = VAR_DECLARATION__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__ATTRIBUTES = VAR_DECLARATION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__IS_INPUT = VAR_DECLARATION__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__INPUT_CONNECTIONS = VAR_DECLARATION__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__OUTPUT_CONNECTIONS = VAR_DECLARATION__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__TYPE = VAR_DECLARATION__TYPE;

	/**
	 * The feature id for the '<em><b>Array Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__ARRAY_SIZE = VAR_DECLARATION__ARRAY_SIZE;

	/**
	 * The feature id for the '<em><b>Withs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__WITHS = VAR_DECLARATION__WITHS;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__VALUE = VAR_DECLARATION__VALUE;

	/**
	 * The feature id for the '<em><b>Array Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__ARRAY_START = VAR_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Array Stop</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE__ARRAY_STOP = VAR_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Local Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_FEATURE_COUNT = VAR_DECLARATION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MappingImpl <em>Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MappingImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMapping()
	 * @generated
	 */
	int MAPPING = 62;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING__TO = 1;

	/**
	 * The number of structural features of the '<em>Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MemberVarDeclarationImpl <em>Member Var Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MemberVarDeclarationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMemberVarDeclaration()
	 * @generated
	 */
	int MEMBER_VAR_DECLARATION = 64;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__NAME = VAR_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__COMMENT = VAR_DECLARATION__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__ATTRIBUTES = VAR_DECLARATION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__IS_INPUT = VAR_DECLARATION__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__INPUT_CONNECTIONS = VAR_DECLARATION__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__OUTPUT_CONNECTIONS = VAR_DECLARATION__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__TYPE = VAR_DECLARATION__TYPE;

	/**
	 * The feature id for the '<em><b>Array Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__ARRAY_SIZE = VAR_DECLARATION__ARRAY_SIZE;

	/**
	 * The feature id for the '<em><b>Withs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__WITHS = VAR_DECLARATION__WITHS;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__VALUE = VAR_DECLARATION__VALUE;

	/**
	 * The feature id for the '<em><b>Parent Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION__PARENT_NAMES = VAR_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Member Var Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_VAR_DECLARATION_FEATURE_COUNT = VAR_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MethodImpl <em>Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MethodImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMethod()
	 * @generated
	 */
	int METHOD = 65;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__NAME = ICALLABLE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD__COMMENT = ICALLABLE__COMMENT;

	/**
	 * The number of structural features of the '<em>Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_FEATURE_COUNT = ICALLABLE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MultiplexerImpl <em>Multiplexer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MultiplexerImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMultiplexer()
	 * @generated
	 */
	int MULTIPLEXER = 66;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLEXER__NAME = STRUCT_MANIPULATOR__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLEXER__COMMENT = STRUCT_MANIPULATOR__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLEXER__ATTRIBUTES = STRUCT_MANIPULATOR__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLEXER__TYPE_ENTRY = STRUCT_MANIPULATOR__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLEXER__POSITION = STRUCT_MANIPULATOR__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLEXER__INTERFACE = STRUCT_MANIPULATOR__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLEXER__MAPPING = STRUCT_MANIPULATOR__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLEXER__GROUP = STRUCT_MANIPULATOR__GROUP;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLEXER__DATA_TYPE = STRUCT_MANIPULATOR__DATA_TYPE;

	/**
	 * The number of structural features of the '<em>Multiplexer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTIPLEXER_FEATURE_COUNT = STRUCT_MANIPULATOR_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OriginalSourceImpl <em>Original Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OriginalSourceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOriginalSource()
	 * @generated
	 */
	int ORIGINAL_SOURCE = 67;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORIGINAL_SOURCE__TEXT = 0;

	/**
	 * The number of structural features of the '<em>Original Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORIGINAL_SOURCE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextAlgorithmImpl <em>Text Algorithm</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TextAlgorithmImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTextAlgorithm()
	 * @generated
	 */
	int TEXT_ALGORITHM = 96;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ALGORITHM__NAME = ALGORITHM__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ALGORITHM__COMMENT = ALGORITHM__COMMENT;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ALGORITHM__TEXT = ALGORITHM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Algorithm</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_ALGORITHM_FEATURE_COUNT = ALGORITHM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OtherAlgorithmImpl <em>Other Algorithm</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OtherAlgorithmImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOtherAlgorithm()
	 * @generated
	 */
	int OTHER_ALGORITHM = 68;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM__NAME = TEXT_ALGORITHM__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM__COMMENT = TEXT_ALGORITHM__COMMENT;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM__TEXT = TEXT_ALGORITHM__TEXT;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM__LANGUAGE = TEXT_ALGORITHM_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Other Algorithm</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_ALGORITHM_FEATURE_COUNT = TEXT_ALGORITHM_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextMethodImpl <em>Text Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TextMethodImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTextMethod()
	 * @generated
	 */
	int TEXT_METHOD = 99;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_METHOD__NAME = METHOD__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_METHOD__COMMENT = METHOD__COMMENT;

	/**
	 * The feature id for the '<em><b>Input Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_METHOD__INPUT_PARAMETERS = METHOD_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Output Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_METHOD__OUTPUT_PARAMETERS = METHOD_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>In Out Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_METHOD__IN_OUT_PARAMETERS = METHOD_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_METHOD__RETURN_TYPE = METHOD_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Varargs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_METHOD__VARARGS = METHOD_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_METHOD__TEXT = METHOD_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Text Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_METHOD_FEATURE_COUNT = METHOD_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OtherMethodImpl <em>Other Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OtherMethodImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOtherMethod()
	 * @generated
	 */
	int OTHER_METHOD = 69;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_METHOD__NAME = TEXT_METHOD__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_METHOD__COMMENT = TEXT_METHOD__COMMENT;

	/**
	 * The feature id for the '<em><b>Input Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_METHOD__INPUT_PARAMETERS = TEXT_METHOD__INPUT_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Output Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_METHOD__OUTPUT_PARAMETERS = TEXT_METHOD__OUTPUT_PARAMETERS;

	/**
	 * The feature id for the '<em><b>In Out Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_METHOD__IN_OUT_PARAMETERS = TEXT_METHOD__IN_OUT_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_METHOD__RETURN_TYPE = TEXT_METHOD__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Varargs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_METHOD__VARARGS = TEXT_METHOD__VARARGS;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_METHOD__TEXT = TEXT_METHOD__TEXT;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_METHOD__LANGUAGE = TEXT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Other Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OTHER_METHOD_FEATURE_COUNT = TEXT_METHOD_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OutputPrimitiveImpl <em>Output Primitive</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OutputPrimitiveImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOutputPrimitive()
	 * @generated
	 */
	int OUTPUT_PRIMITIVE = 70;

	/**
	 * The feature id for the '<em><b>Event</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PRIMITIVE__EVENT = PRIMITIVE__EVENT;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PRIMITIVE__PARAMETERS = PRIMITIVE__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PRIMITIVE__INTERFACE = PRIMITIVE__INTERFACE;

	/**
	 * The number of structural features of the '<em>Output Primitive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OUTPUT_PRIMITIVE_FEATURE_COUNT = PRIMITIVE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.PositionImpl <em>Position</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.PositionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPosition()
	 * @generated
	 */
	int POSITION = 71;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION__Y = 1;

	/**
	 * The number of structural features of the '<em>Position</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 74;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__NAME = TYPED_CONFIGUREABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__COMMENT = TYPED_CONFIGUREABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__ATTRIBUTES = TYPED_CONFIGUREABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__TYPE_ENTRY = TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__VAR_DECLARATIONS = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	 * The feature id for the '<em><b>Device</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__DEVICE = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Device Type Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__DEVICE_TYPE_RESOURCE = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeNameImpl <em>Resource Type Name</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeNameImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceTypeName()
	 * @generated
	 */
	int RESOURCE_TYPE_NAME = 75;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_NAME__NAME = 0;

	/**
	 * The number of structural features of the '<em>Resource Type Name</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_NAME_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl <em>Resource Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceType()
	 * @generated
	 */
	int RESOURCE_TYPE = 76;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__NAME = LIBRARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__COMMENT = LIBRARY_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__ATTRIBUTES = LIBRARY_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__VERSION_INFO = LIBRARY_ELEMENT__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__IDENTIFICATION = LIBRARY_ELEMENT__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__COMPILER_INFO = LIBRARY_ELEMENT__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__TYPE_ENTRY = LIBRARY_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Var Declaration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__VAR_DECLARATION = LIBRARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__FB_NETWORK = LIBRARY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Supported FB Types</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE__SUPPORTED_FB_TYPES = LIBRARY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Resource Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FEATURE_COUNT = LIBRARY_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeFBImpl <em>Resource Type FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeFBImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceTypeFB()
	 * @generated
	 */
	int RESOURCE_TYPE_FB = 77;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__NAME = FB__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__COMMENT = FB__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__ATTRIBUTES = FB__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__TYPE_ENTRY = FB__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__POSITION = FB__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__INTERFACE = FB__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__MAPPING = FB__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB__GROUP = FB__GROUP;

	/**
	 * The number of structural features of the '<em>Resource Type FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_TYPE_FB_FEATURE_COUNT = FB_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl <em>Segment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSegment()
	 * @generated
	 */
	int SEGMENT = 78;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__NAME = TYPED_CONFIGUREABLE_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__COMMENT = TYPED_CONFIGUREABLE_OBJECT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__ATTRIBUTES = TYPED_CONFIGUREABLE_OBJECT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__TYPE_ENTRY = TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__POSITION = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Color</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__COLOR = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__VAR_DECLARATIONS = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__WIDTH = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Out Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__OUT_CONNECTIONS = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Communication</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT__COMMUNICATION = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Segment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_FEATURE_COUNT = TYPED_CONFIGUREABLE_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentTypeImpl <em>Segment Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSegmentType()
	 * @generated
	 */
	int SEGMENT_TYPE = 79;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__NAME = LIBRARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__COMMENT = LIBRARY_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__ATTRIBUTES = LIBRARY_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__VERSION_INFO = LIBRARY_ELEMENT__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__IDENTIFICATION = LIBRARY_ELEMENT__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__COMPILER_INFO = LIBRARY_ELEMENT__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__TYPE_ENTRY = LIBRARY_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Var Declaration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE__VAR_DECLARATION = LIBRARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Segment Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEGMENT_TYPE_FEATURE_COUNT = LIBRARY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl <em>Service</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getService()
	 * @generated
	 */
	int SERVICE = 80;

	/**
	 * The feature id for the '<em><b>Right Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE__RIGHT_INTERFACE = 0;

	/**
	 * The feature id for the '<em><b>Left Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE__LEFT_INTERFACE = 1;

	/**
	 * The feature id for the '<em><b>Service Sequence</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE__SERVICE_SEQUENCE = 2;

	/**
	 * The number of structural features of the '<em>Service</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl <em>Service Sequence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceSequence()
	 * @generated
	 */
	int SERVICE_SEQUENCE = 81;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__ATTRIBUTES = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Service Transaction</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__SERVICE_TRANSACTION = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Service Sequence Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Start State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__START_STATE = INAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Event Manager</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE__EVENT_MANAGER = INAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Service Sequence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SEQUENCE_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceTransactionImpl <em>Service Transaction</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceTransactionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceTransaction()
	 * @generated
	 */
	int SERVICE_TRANSACTION = 82;

	/**
	 * The feature id for the '<em><b>Input Primitive</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TRANSACTION__INPUT_PRIMITIVE = 0;

	/**
	 * The feature id for the '<em><b>Output Primitive</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TRANSACTION__OUTPUT_PRIMITIVE = 1;

	/**
	 * The number of structural features of the '<em>Service Transaction</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_TRANSACTION_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceImpl <em>Service Interface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceInterface()
	 * @generated
	 */
	int SERVICE_INTERFACE = 83;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>Service Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceFBTypeImpl <em>Service Interface FB Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceInterfaceFBType()
	 * @generated
	 */
	int SERVICE_INTERFACE_FB_TYPE = 84;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__NAME = FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__COMMENT = FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__ATTRIBUTES = FB_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__VERSION_INFO = FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__IDENTIFICATION = FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__COMPILER_INFO = FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__TYPE_ENTRY = FB_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__INTERFACE_LIST = FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE__SERVICE = FB_TYPE__SERVICE;

	/**
	 * The number of structural features of the '<em>Service Interface FB Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_TYPE_FEATURE_COUNT = FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECActionImpl <em>Simple EC Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECActionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSimpleECAction()
	 * @generated
	 */
	int SIMPLE_EC_ACTION = 85;

	/**
	 * The feature id for the '<em><b>Algorithm</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EC_ACTION__ALGORITHM = 0;

	/**
	 * The feature id for the '<em><b>Output</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EC_ACTION__OUTPUT = 1;

	/**
	 * The feature id for the '<em><b>Simple EC State</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EC_ACTION__SIMPLE_EC_STATE = 2;

	/**
	 * The number of structural features of the '<em>Simple EC Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EC_ACTION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECStateImpl <em>Simple EC State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECStateImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSimpleECState()
	 * @generated
	 */
	int SIMPLE_EC_STATE = 86;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EC_STATE__NAME = INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EC_STATE__COMMENT = INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Simple EC Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS = INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Input Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EC_STATE__INPUT_EVENT = INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Simple FB Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EC_STATE__SIMPLE_FB_TYPE = INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Simple EC State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_EC_STATE_FEATURE_COUNT = INAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl <em>Simple FB Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSimpleFBType()
	 * @generated
	 */
	int SIMPLE_FB_TYPE = 87;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__NAME = BASE_FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__COMMENT = BASE_FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__ATTRIBUTES = BASE_FB_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__VERSION_INFO = BASE_FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__IDENTIFICATION = BASE_FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__COMPILER_INFO = BASE_FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__TYPE_ENTRY = BASE_FB_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__INTERFACE_LIST = BASE_FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__SERVICE = BASE_FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>Internal Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__INTERNAL_VARS = BASE_FB_TYPE__INTERNAL_VARS;

	/**
	 * The feature id for the '<em><b>Internal Const Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__INTERNAL_CONST_VARS = BASE_FB_TYPE__INTERNAL_CONST_VARS;

	/**
	 * The feature id for the '<em><b>Internal Fbs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__INTERNAL_FBS = BASE_FB_TYPE__INTERNAL_FBS;

	/**
	 * The feature id for the '<em><b>Algorithm</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__ALGORITHM = BASE_FB_TYPE__ALGORITHM;

	/**
	 * The feature id for the '<em><b>Methods</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__METHODS = BASE_FB_TYPE__METHODS;

	/**
	 * The feature id for the '<em><b>Callables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__CALLABLES = BASE_FB_TYPE__CALLABLES;

	/**
	 * The feature id for the '<em><b>Simple EC States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE__SIMPLE_EC_STATES = BASE_FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simple FB Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_TYPE_FEATURE_COUNT = BASE_FB_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.STAlgorithmImpl <em>ST Algorithm</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.STAlgorithmImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSTAlgorithm()
	 * @generated
	 */
	int ST_ALGORITHM = 88;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM__NAME = TEXT_ALGORITHM__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM__COMMENT = TEXT_ALGORITHM__COMMENT;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM__TEXT = TEXT_ALGORITHM__TEXT;

	/**
	 * The number of structural features of the '<em>ST Algorithm</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ALGORITHM_FEATURE_COUNT = TEXT_ALGORITHM_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionImpl <em>Text Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTextFunction()
	 * @generated
	 */
	int TEXT_FUNCTION = 97;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION__NAME = FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION__COMMENT = FUNCTION__COMMENT;

	/**
	 * The feature id for the '<em><b>Input Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION__INPUT_PARAMETERS = FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Output Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION__OUTPUT_PARAMETERS = FUNCTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>In Out Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION__IN_OUT_PARAMETERS = FUNCTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION__RETURN_TYPE = FUNCTION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Varargs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION__VARARGS = FUNCTION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION__TEXT = FUNCTION_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Text Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION_FEATURE_COUNT = FUNCTION_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.STFunctionImpl <em>ST Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.STFunctionImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSTFunction()
	 * @generated
	 */
	int ST_FUNCTION = 89;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__NAME = TEXT_FUNCTION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__COMMENT = TEXT_FUNCTION__COMMENT;

	/**
	 * The feature id for the '<em><b>Input Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__INPUT_PARAMETERS = TEXT_FUNCTION__INPUT_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Output Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__OUTPUT_PARAMETERS = TEXT_FUNCTION__OUTPUT_PARAMETERS;

	/**
	 * The feature id for the '<em><b>In Out Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__IN_OUT_PARAMETERS = TEXT_FUNCTION__IN_OUT_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__RETURN_TYPE = TEXT_FUNCTION__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Varargs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__VARARGS = TEXT_FUNCTION__VARARGS;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION__TEXT = TEXT_FUNCTION__TEXT;

	/**
	 * The number of structural features of the '<em>ST Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION_FEATURE_COUNT = TEXT_FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionBodyImpl <em>Text Function Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionBodyImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTextFunctionBody()
	 * @generated
	 */
	int TEXT_FUNCTION_BODY = 98;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION_BODY__TEXT = FUNCTION_BODY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text Function Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_FUNCTION_BODY_FEATURE_COUNT = FUNCTION_BODY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.STFunctionBodyImpl <em>ST Function Body</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.STFunctionBodyImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSTFunctionBody()
	 * @generated
	 */
	int ST_FUNCTION_BODY = 90;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION_BODY__TEXT = TEXT_FUNCTION_BODY__TEXT;

	/**
	 * The number of structural features of the '<em>ST Function Body</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FUNCTION_BODY_FEATURE_COUNT = TEXT_FUNCTION_BODY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.STMethodImpl <em>ST Method</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.STMethodImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSTMethod()
	 * @generated
	 */
	int ST_METHOD = 91;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_METHOD__NAME = TEXT_METHOD__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_METHOD__COMMENT = TEXT_METHOD__COMMENT;

	/**
	 * The feature id for the '<em><b>Input Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_METHOD__INPUT_PARAMETERS = TEXT_METHOD__INPUT_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Output Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_METHOD__OUTPUT_PARAMETERS = TEXT_METHOD__OUTPUT_PARAMETERS;

	/**
	 * The feature id for the '<em><b>In Out Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_METHOD__IN_OUT_PARAMETERS = TEXT_METHOD__IN_OUT_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_METHOD__RETURN_TYPE = TEXT_METHOD__RETURN_TYPE;

	/**
	 * The feature id for the '<em><b>Varargs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_METHOD__VARARGS = TEXT_METHOD__VARARGS;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_METHOD__TEXT = TEXT_METHOD__TEXT;

	/**
	 * The number of structural features of the '<em>ST Method</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_METHOD_FEATURE_COUNT = TEXT_METHOD_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl <em>Sub App</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSubApp()
	 * @generated
	 */
	int SUB_APP = 92;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__NAME = FB_NETWORK_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__COMMENT = FB_NETWORK_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__ATTRIBUTES = FB_NETWORK_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__TYPE_ENTRY = FB_NETWORK_ELEMENT__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__POSITION = FB_NETWORK_ELEMENT__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__INTERFACE = FB_NETWORK_ELEMENT__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__MAPPING = FB_NETWORK_ELEMENT__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__GROUP = FB_NETWORK_ELEMENT__GROUP;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__WIDTH = FB_NETWORK_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__HEIGHT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP__LOCKED = FB_NETWORK_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Sub App</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_FEATURE_COUNT = FB_NETWORK_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppTypeImpl <em>Sub App Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppTypeImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSubAppType()
	 * @generated
	 */
	int SUB_APP_TYPE = 94;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__NAME = COMPOSITE_FB_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__COMMENT = COMPOSITE_FB_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__ATTRIBUTES = COMPOSITE_FB_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__VERSION_INFO = COMPOSITE_FB_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__IDENTIFICATION = COMPOSITE_FB_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__COMPILER_INFO = COMPOSITE_FB_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__TYPE_ENTRY = COMPOSITE_FB_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Interface List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__INTERFACE_LIST = COMPOSITE_FB_TYPE__INTERFACE_LIST;

	/**
	 * The feature id for the '<em><b>Service</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__SERVICE = COMPOSITE_FB_TYPE__SERVICE;

	/**
	 * The feature id for the '<em><b>FB Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE__FB_NETWORK = COMPOSITE_FB_TYPE__FB_NETWORK;

	/**
	 * The number of structural features of the '<em>Sub App Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_TYPE_FEATURE_COUNT = COMPOSITE_FB_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SystemConfigurationImpl <em>System Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SystemConfigurationImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSystemConfiguration()
	 * @generated
	 */
	int SYSTEM_CONFIGURATION = 95;

	/**
	 * The feature id for the '<em><b>Devices</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CONFIGURATION__DEVICES = 0;

	/**
	 * The feature id for the '<em><b>Segments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CONFIGURATION__SEGMENTS = 1;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CONFIGURATION__LINKS = 2;

	/**
	 * The number of structural features of the '<em>System Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_CONFIGURATION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TypedSubAppImpl <em>Typed Sub App</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TypedSubAppImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypedSubApp()
	 * @generated
	 */
	int TYPED_SUB_APP = 101;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__NAME = SUB_APP__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__COMMENT = SUB_APP__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__ATTRIBUTES = SUB_APP__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__TYPE_ENTRY = SUB_APP__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__POSITION = SUB_APP__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__INTERFACE = SUB_APP__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__MAPPING = SUB_APP__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__GROUP = SUB_APP__GROUP;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__WIDTH = SUB_APP__WIDTH;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__HEIGHT = SUB_APP__HEIGHT;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__LOCKED = SUB_APP__LOCKED;

	/**
	 * The feature id for the '<em><b>Sub App Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP__SUB_APP_NETWORK = SUB_APP_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Typed Sub App</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUB_APP_FEATURE_COUNT = SUB_APP_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.UntypedSubAppImpl <em>Untyped Sub App</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.UntypedSubAppImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getUntypedSubApp()
	 * @generated
	 */
	int UNTYPED_SUB_APP = 102;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__NAME = SUB_APP__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__COMMENT = SUB_APP__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__ATTRIBUTES = SUB_APP__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__TYPE_ENTRY = SUB_APP__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__POSITION = SUB_APP__POSITION;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__INTERFACE = SUB_APP__INTERFACE;

	/**
	 * The feature id for the '<em><b>Mapping</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__MAPPING = SUB_APP__MAPPING;

	/**
	 * The feature id for the '<em><b>Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__GROUP = SUB_APP__GROUP;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__WIDTH = SUB_APP__WIDTH;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__HEIGHT = SUB_APP__HEIGHT;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__LOCKED = SUB_APP__LOCKED;

	/**
	 * The feature id for the '<em><b>Sub App Network</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP__SUB_APP_NETWORK = SUB_APP_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Untyped Sub App</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUB_APP_FEATURE_COUNT = SUB_APP_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ValueImpl <em>Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ValueImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getValue()
	 * @generated
	 */
	int VALUE = 103;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE__VALUE = 0;

	/**
	 * The number of structural features of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VersionInfoImpl <em>Version Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.VersionInfoImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getVersionInfo()
	 * @generated
	 */
	int VERSION_INFO = 105;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__AUTHOR = 0;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__DATE = 1;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__ORGANIZATION = 2;

	/**
	 * The feature id for the '<em><b>Remarks</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__REMARKS = 3;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO__VERSION = 4;

	/**
	 * The number of structural features of the '<em>Version Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VERSION_INFO_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.WithImpl <em>With</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.WithImpl
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getWith()
	 * @generated
	 */
	int WITH = 106;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH__VARIABLES = 0;

	/**
	 * The number of structural features of the '<em>With</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WITH_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.libraryElement.Language <em>Language</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Language
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLanguage()
	 * @generated
	 */
	int LANGUAGE = 107;

	/**
	 * The meta object id for the '<em>Command Stack</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gef.commands.CommandStack
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCommandStack()
	 * @generated
	 */
	int COMMAND_STACK = 108;

	/**
	 * The meta object id for the '<em>IFile</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.core.resources.IFile
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIFile()
	 * @generated
	 */
	int IFILE = 109;

	/**
	 * The meta object id for the '<em>Interface Element Stream</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.stream.Stream
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getInterfaceElementStream()
	 * @generated
	 */
	int INTERFACE_ELEMENT_STREAM = 110;

	/**
	 * The meta object id for the '<em>IProject</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.core.resources.IProject
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIProject()
	 * @generated
	 */
	int IPROJECT = 111;

	/**
	 * The meta object id for the '<em>Named Element Stream</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.stream.Stream
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getNamedElementStream()
	 * @generated
	 */
	int NAMED_ELEMENT_STREAM = 112;

	/**
	 * The meta object id for the '<em>Point</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.draw2d.geometry.Point
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPoint()
	 * @generated
	 */
	int POINT = 113;

	/**
	 * The meta object id for the '<em>Type Entry</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.typelibrary.TypeEntry
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypeEntry()
	 * @generated
	 */
	int TYPE_ENTRY = 114;

	/**
	 * The meta object id for the '<em>Type Library</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypeLibrary()
	 * @generated
	 */
	int TYPE_LIBRARY = 115;

	/**
	 * The meta object id for the '<em>Var Decl List</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.util.List
	 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getVarDeclList()
	 * @generated
	 */
	int VAR_DECL_LIST = 116;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection <em>Adapter Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection
	 * @generated
	 */
	EClass getAdapterConnection();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection#getFBNetwork()
	 * @see #getAdapterConnection()
	 * @generated
	 */
	EReference getAdapterConnection_FBNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration <em>Adapter Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
	 * @generated
	 */
	EClass getAdapterDeclaration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getAdapterFB <em>Adapter FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Adapter FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getAdapterFB()
	 * @see #getAdapterDeclaration()
	 * @generated
	 */
	EReference getAdapterDeclaration_AdapterFB();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getInterfaceOnlyAdapterFB <em>Interface Only Adapter FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Interface Only Adapter FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration#getInterfaceOnlyAdapterFB()
	 * @see #getAdapterDeclaration()
	 * @generated
	 */
	EReference getAdapterDeclaration_InterfaceOnlyAdapterFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFB <em>Adapter FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
	 * @generated
	 */
	EClass getAdapterFB();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterFB#getAdapterDecl <em>Adapter Decl</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Adapter Decl</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterFB#getAdapterDecl()
	 * @see #getAdapterFB()
	 * @generated
	 */
	EReference getAdapterFB_AdapterDecl();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterType <em>Adapter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterType
	 * @generated
	 */
	EClass getAdapterType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AdapterType#getPlugType <em>Plug Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Plug Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AdapterType#getPlugType()
	 * @see #getAdapterType()
	 * @generated
	 */
	EReference getAdapterType_PlugType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Algorithm <em>Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Algorithm
	 * @generated
	 */
	EClass getAlgorithm();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Application <em>Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Application
	 * @generated
	 */
	EClass getApplication();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Application#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Application#getFBNetwork()
	 * @see #getApplication()
	 * @generated
	 */
	EReference getApplication_FBNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ArraySize <em>Array Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Size</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ArraySize
	 * @generated
	 */
	EClass getArraySize();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ArraySize#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ArraySize#getValue()
	 * @see #getArraySize()
	 * @generated
	 */
	EAttribute getArraySize_Value();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ArraySize#getVarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ArraySize#getVarDeclaration()
	 * @see #getArraySize()
	 * @generated
	 */
	EReference getArraySize_VarDeclaration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getAttributeDeclaration <em>Attribute Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Attribute#getAttributeDeclaration()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_AttributeDeclaration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Attribute#getType()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Attribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Attribute#getValue()
	 * @see #getAttribute()
	 * @generated
	 */
	EAttribute getAttribute_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration <em>Attribute Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration
	 * @generated
	 */
	EClass getAttributeDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AttributeDeclaration#getType()
	 * @see #getAttributeDeclaration()
	 * @generated
	 */
	EReference getAttributeDeclaration_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType <em>Base FB Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
	 * @generated
	 */
	EClass getBaseFBType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getInternalVars <em>Internal Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Internal Vars</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getInternalVars()
	 * @see #getBaseFBType()
	 * @generated
	 */
	EReference getBaseFBType_InternalVars();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getInternalConstVars <em>Internal Const Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Internal Const Vars</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getInternalConstVars()
	 * @see #getBaseFBType()
	 * @generated
	 */
	EReference getBaseFBType_InternalConstVars();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getInternalFbs <em>Internal Fbs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Internal Fbs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getInternalFbs()
	 * @see #getBaseFBType()
	 * @generated
	 */
	EReference getBaseFBType_InternalFbs();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getAlgorithm <em>Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getAlgorithm()
	 * @see #getBaseFBType()
	 * @generated
	 */
	EReference getBaseFBType_Algorithm();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getMethods <em>Methods</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Methods</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getMethods()
	 * @see #getBaseFBType()
	 * @generated
	 */
	EReference getBaseFBType_Methods();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getCallables <em>Callables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Callables</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getCallables()
	 * @see #getBaseFBType()
	 * @generated
	 */
	EReference getBaseFBType_Callables();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.BasicFBType <em>Basic FB Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
	 * @generated
	 */
	EClass getBasicFBType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.BasicFBType#getECC <em>ECC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>ECC</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BasicFBType#getECC()
	 * @see #getBasicFBType()
	 * @generated
	 */
	EReference getBasicFBType_ECC();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem <em>Automation System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Automation System</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem
	 * @generated
	 */
	EClass getAutomationSystem();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getApplication <em>Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Application</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getApplication()
	 * @see #getAutomationSystem()
	 * @generated
	 */
	EReference getAutomationSystem_Application();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mapping</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getMapping()
	 * @see #getAutomationSystem()
	 * @generated
	 */
	EReference getAutomationSystem_Mapping();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getSystemConfiguration <em>System Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>System Configuration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getSystemConfiguration()
	 * @see #getAutomationSystem()
	 * @generated
	 */
	EReference getAutomationSystem_SystemConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getCommandStack <em>Command Stack</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Command Stack</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem#getCommandStack()
	 * @see #getAutomationSystem()
	 * @generated
	 */
	EAttribute getAutomationSystem_CommandStack();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.CFBInstance <em>CFB Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CFB Instance</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CFBInstance
	 * @generated
	 */
	EClass getCFBInstance();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.CFBInstance#getCfbNetwork <em>Cfb Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cfb Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CFBInstance#getCfbNetwork()
	 * @see #getCFBInstance()
	 * @generated
	 */
	EReference getCFBInstance_CfbNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Color <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Color</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Color
	 * @generated
	 */
	EClass getColor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getRed <em>Red</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Red</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Color#getRed()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_Red();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getGreen <em>Green</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Green</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Color#getGreen()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_Green();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Color#getBlue <em>Blue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Blue</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Color#getBlue()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_Blue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement <em>Colorizable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Colorizable Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement
	 * @generated
	 */
	EClass getColorizableElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Color</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ColorizableElement#getColor()
	 * @see #getColorizableElement()
	 * @generated
	 */
	EReference getColorizableElement_Color();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Comment#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Comment#getWidth()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Width();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Comment#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Comment#getHeight()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Height();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel <em>Communication Channel</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Communication Channel</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CommunicationChannel
	 * @generated
	 */
	EClass getCommunicationChannel();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration <em>Communication Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Communication Configuration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration
	 * @generated
	 */
	EClass getCommunicationConfiguration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget <em>Communication Mapping Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Communication Mapping Target</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget
	 * @generated
	 */
	EClass getCommunicationMappingTarget();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget#getMappedElements <em>Mapped Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mapped Elements</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget#getMappedElements()
	 * @see #getCommunicationMappingTarget()
	 * @generated
	 */
	EReference getCommunicationMappingTarget_MappedElements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler <em>Compiler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compiler</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler
	 * @generated
	 */
	EClass getCompiler();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler#getLanguage()
	 * @see #getCompiler()
	 * @generated
	 */
	EAttribute getCompiler_Language();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getProduct <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Product</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler#getProduct()
	 * @see #getCompiler()
	 * @generated
	 */
	EAttribute getCompiler_Product();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVendor <em>Vendor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVendor()
	 * @see #getCompiler()
	 * @generated
	 */
	EAttribute getCompiler_Vendor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVersion()
	 * @see #getCompiler()
	 * @generated
	 */
	EAttribute getCompiler_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo <em>Compiler Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compiler Info</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo
	 * @generated
	 */
	EClass getCompilerInfo();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getCompiler <em>Compiler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compiler</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getCompiler()
	 * @see #getCompilerInfo()
	 * @generated
	 */
	EReference getCompilerInfo_Compiler();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getClassdef <em>Classdef</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Classdef</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getClassdef()
	 * @see #getCompilerInfo()
	 * @generated
	 */
	EAttribute getCompilerInfo_Classdef();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getHeader <em>Header</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Header</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getHeader()
	 * @see #getCompilerInfo()
	 * @generated
	 */
	EAttribute getCompilerInfo_Header();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getPackageName <em>Package Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getPackageName()
	 * @see #getCompilerInfo()
	 * @generated
	 */
	EAttribute getCompilerInfo_PackageName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getImports <em>Imports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Imports</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getImports()
	 * @see #getCompilerInfo()
	 * @generated
	 */
	EReference getCompilerInfo_Imports();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType <em>Composite FB Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
	 * @generated
	 */
	EClass getCompositeFBType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType#getFBNetwork()
	 * @see #getCompositeFBType()
	 * @generated
	 */
	EReference getCompositeFBType_FBNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject <em>Configurable Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configurable Object</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject
	 * @generated
	 */
	EClass getConfigurableObject();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject#getAttributes()
	 * @see #getConfigurableObject()
	 * @generated
	 */
	EReference getConfigurableObject_Attributes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB <em>Configurable FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configurable FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB
	 * @generated
	 */
	EClass getConfigurableFB();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Data Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB#getDataType()
	 * @see #getConfigurableFB()
	 * @generated
	 */
	EReference getConfigurableFB_DataType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB <em>Configurable Move FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configurable Move FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB
	 * @generated
	 */
	EClass getConfigurableMoveFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#isBrokenConnection <em>Broken Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Broken Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#isBrokenConnection()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_BrokenConnection();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#getSource()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Destination</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#getDestination()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Destination();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getRoutingData <em>Routing Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Routing Data</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#getRoutingData()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_RoutingData();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Connection#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Connection#getComment()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_Comment();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData <em>Connection Routing Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection Routing Data</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData
	 * @generated
	 */
	EClass getConnectionRoutingData();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDx1 <em>Dx1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dx1</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDx1()
	 * @see #getConnectionRoutingData()
	 * @generated
	 */
	EAttribute getConnectionRoutingData_Dx1();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDx2 <em>Dx2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dx2</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDx2()
	 * @see #getConnectionRoutingData()
	 * @generated
	 */
	EAttribute getConnectionRoutingData_Dx2();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDy <em>Dy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dy</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#getDy()
	 * @see #getConnectionRoutingData()
	 * @generated
	 */
	EAttribute getConnectionRoutingData_Dy();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#isNeedsValidation <em>Needs Validation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Needs Validation</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData#isNeedsValidation()
	 * @see #getConnectionRoutingData()
	 * @generated
	 */
	EAttribute getConnectionRoutingData_NeedsValidation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.DataConnection <em>Data Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DataConnection
	 * @generated
	 */
	EClass getDataConnection();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.DataConnection#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DataConnection#getFBNetwork()
	 * @see #getDataConnection()
	 * @generated
	 */
	EReference getDataConnection_FBNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer <em>Demultiplexer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Demultiplexer</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer
	 * @generated
	 */
	EClass getDemultiplexer();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer#isIsConfigured <em>Is Configured</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Configured</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Demultiplexer#isIsConfigured()
	 * @see #getDemultiplexer()
	 * @generated
	 */
	EAttribute getDemultiplexer_IsConfigured();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Device <em>Device</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Device</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Device
	 * @generated
	 */
	EClass getDevice();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resource</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Device#getResource()
	 * @see #getDevice()
	 * @generated
	 */
	EReference getDevice_Resource();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getProfile <em>Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Profile</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Device#getProfile()
	 * @see #getDevice()
	 * @generated
	 */
	EAttribute getDevice_Profile();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Device#getInConnections <em>In Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>In Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Device#getInConnections()
	 * @see #getDevice()
	 * @generated
	 */
	EReference getDevice_InConnections();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType <em>Device Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Device Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType
	 * @generated
	 */
	EClass getDeviceType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getVarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getVarDeclaration()
	 * @see #getDeviceType()
	 * @generated
	 */
	EReference getDeviceType_VarDeclaration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getResourceTypeName <em>Resource Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resource Type Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getResourceTypeName()
	 * @see #getDeviceType()
	 * @generated
	 */
	EReference getDeviceType_ResourceTypeName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resource</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getResource()
	 * @see #getDeviceType()
	 * @generated
	 */
	EReference getDeviceType_Resource();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getFBNetwork()
	 * @see #getDeviceType()
	 * @generated
	 */
	EReference getDeviceType_FBNetwork();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getProfile <em>Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Profile</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getProfile()
	 * @see #getDeviceType()
	 * @generated
	 */
	EAttribute getDeviceType_Profile();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getAttributeDeclarations <em>Attribute Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Declarations</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.DeviceType#getAttributeDeclarations()
	 * @see #getDeviceType()
	 * @generated
	 */
	EReference getDeviceType_AttributeDeclarations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction <em>EC Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EC Action</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECAction
	 * @generated
	 */
	EClass getECAction();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getAlgorithm <em>Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECAction#getAlgorithm()
	 * @see #getECAction()
	 * @generated
	 */
	EReference getECAction_Algorithm();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getOutput <em>Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Output</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECAction#getOutput()
	 * @see #getECAction()
	 * @generated
	 */
	EReference getECAction_Output();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getECState <em>EC State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>EC State</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECAction#getECState()
	 * @see #getECAction()
	 * @generated
	 */
	EReference getECAction_ECState();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC <em>ECC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ECC</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC
	 * @generated
	 */
	EClass getECC();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getECState <em>EC State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>EC State</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC#getECState()
	 * @see #getECC()
	 * @generated
	 */
	EReference getECC_ECState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getECTransition <em>EC Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>EC Transition</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC#getECTransition()
	 * @see #getECC()
	 * @generated
	 */
	EReference getECC_ECTransition();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Start</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC#getStart()
	 * @see #getECC()
	 * @generated
	 */
	EReference getECC_Start();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getBasicFBType <em>Basic FB Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Basic FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC#getBasicFBType()
	 * @see #getECC()
	 * @generated
	 */
	EReference getECC_BasicFBType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState <em>EC State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EC State</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState
	 * @generated
	 */
	EClass getECState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getECAction <em>EC Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>EC Action</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getECAction()
	 * @see #getECState()
	 * @generated
	 */
	EReference getECState_ECAction();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getOutTransitions <em>Out Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Out Transitions</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getOutTransitions()
	 * @see #getECState()
	 * @generated
	 */
	EReference getECState_OutTransitions();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getInTransitions <em>In Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>In Transitions</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getInTransitions()
	 * @see #getECState()
	 * @generated
	 */
	EReference getECState_InTransitions();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getECC <em>ECC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>ECC</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getECC()
	 * @see #getECState()
	 * @generated
	 */
	EReference getECState_ECC();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition <em>EC Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EC Transition</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition
	 * @generated
	 */
	EClass getECTransition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getComment()
	 * @see #getECTransition()
	 * @generated
	 */
	EAttribute getECTransition_Comment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionExpression <em>Condition Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition Expression</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionExpression()
	 * @see #getECTransition()
	 * @generated
	 */
	EAttribute getECTransition_ConditionExpression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getSource()
	 * @see #getECTransition()
	 * @generated
	 */
	EReference getECTransition_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Destination</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getDestination()
	 * @see #getECTransition()
	 * @generated
	 */
	EReference getECTransition_Destination();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionEvent <em>Condition Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Condition Event</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getConditionEvent()
	 * @see #getECTransition()
	 * @generated
	 */
	EReference getECTransition_ConditionEvent();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getECC <em>ECC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>ECC</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getECC()
	 * @see #getECTransition()
	 * @generated
	 */
	EReference getECTransition_ECC();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType <em>Error Marker Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Error Marker Data Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType
	 * @generated
	 */
	EClass getErrorMarkerDataType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement <em>Error Marker FBN Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Error Marker FBN Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement
	 * @generated
	 */
	EClass getErrorMarkerFBNElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement#getRepairedElement <em>Repaired Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Repaired Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement#getRepairedElement()
	 * @see #getErrorMarkerFBNElement()
	 * @generated
	 */
	EReference getErrorMarkerFBNElement_RepairedElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface <em>Error Marker Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Error Marker Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface
	 * @generated
	 */
	EClass getErrorMarkerInterface();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface#getValue()
	 * @see #getErrorMarkerInterface()
	 * @generated
	 */
	EReference getErrorMarkerInterface_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Event
	 * @generated
	 */
	EClass getEvent();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Event#getWith <em>With</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>With</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Event#getWith()
	 * @see #getEvent()
	 * @generated
	 */
	EReference getEvent_With();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.EventConnection <em>Event Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Connection</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.EventConnection
	 * @generated
	 */
	EClass getEventConnection();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.EventConnection#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.EventConnection#getFBNetwork()
	 * @see #getEventConnection()
	 * @generated
	 */
	EReference getEventConnection_FBNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.FB <em>FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FB
	 * @generated
	 */
	EClass getFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork
	 * @generated
	 */
	EClass getFBNetwork();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getNetworkElements <em>Network Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Network Elements</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getNetworkElements()
	 * @see #getFBNetwork()
	 * @generated
	 */
	EReference getFBNetwork_NetworkElements();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getDataConnections <em>Data Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getDataConnections()
	 * @see #getFBNetwork()
	 * @generated
	 */
	EReference getFBNetwork_DataConnections();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getEventConnections <em>Event Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getEventConnections()
	 * @see #getFBNetwork()
	 * @generated
	 */
	EReference getFBNetwork_EventConnections();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getAdapterConnections <em>Adapter Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Adapter Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetwork#getAdapterConnections()
	 * @see #getFBNetwork()
	 * @generated
	 */
	EReference getFBNetwork_AdapterConnections();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement <em>FB Network Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB Network Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement
	 * @generated
	 */
	EClass getFBNetworkElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getInterface <em>Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getInterface()
	 * @see #getFBNetworkElement()
	 * @generated
	 */
	EReference getFBNetworkElement_Interface();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getMapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getMapping()
	 * @see #getFBNetworkElement()
	 * @generated
	 */
	EReference getFBNetworkElement_Mapping();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Group</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement#getGroup()
	 * @see #getFBNetworkElement()
	 * @generated
	 */
	EReference getFBNetworkElement_Group();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.FBType <em>FB Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBType
	 * @generated
	 */
	EClass getFBType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.FBType#getInterfaceList <em>Interface List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Interface List</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBType#getInterfaceList()
	 * @see #getFBType()
	 * @generated
	 */
	EReference getFBType_InterfaceList();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.FBType#getService <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Service</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FBType#getService()
	 * @see #getFBType()
	 * @generated
	 */
	EReference getFBType_Service();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.FunctionBody <em>Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Body</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FunctionBody
	 * @generated
	 */
	EClass getFunctionBody();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType <em>Function FB Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
	 * @generated
	 */
	EClass getFunctionFBType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Body</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType#getBody()
	 * @see #getFunctionFBType()
	 * @generated
	 */
	EReference getFunctionFBType_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants <em>Global Constants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Constants</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants
	 * @generated
	 */
	EClass getGlobalConstants();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants#getConstants <em>Constants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constants</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants#getConstants()
	 * @see #getGlobalConstants()
	 * @generated
	 */
	EReference getGlobalConstants_Constants();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants#getSource()
	 * @see #getGlobalConstants()
	 * @generated
	 */
	EReference getGlobalConstants_Source();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Group <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Group
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Group#getGroupElements <em>Group Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Group Elements</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Group#getGroupElements()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_GroupElements();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Group#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Group#getWidth()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_Width();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Group#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Group#getHeight()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_Height();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Group#isLocked <em>Locked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Locked</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Group#isLocked()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_Locked();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.HiddenElement <em>Hidden Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hidden Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.HiddenElement
	 * @generated
	 */
	EClass getHiddenElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ICallable <em>ICallable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ICallable</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ICallable
	 * @generated
	 */
	EClass getICallable();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification <em>Identification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identification</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification
	 * @generated
	 */
	EClass getIdentification();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getApplicationDomain <em>Application Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Application Domain</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getApplicationDomain()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_ApplicationDomain();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getClassification <em>Classification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Classification</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getClassification()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_Classification();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getDescription()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getFunction()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_Function();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getStandard <em>Standard</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Standard</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getStandard()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_Standard();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Identification#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Identification#getType()
	 * @see #getIdentification()
	 * @generated
	 */
	EAttribute getIdentification_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement <em>IInterface Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IInterface Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
	 * @generated
	 */
	EClass getIInterfaceElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#isIsInput <em>Is Input</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Input</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#isIsInput()
	 * @see #getIInterfaceElement()
	 * @generated
	 */
	EAttribute getIInterfaceElement_IsInput();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getInputConnections <em>Input Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Input Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getInputConnections()
	 * @see #getIInterfaceElement()
	 * @generated
	 */
	EReference getIInterfaceElement_InputConnections();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getOutputConnections <em>Output Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Output Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getOutputConnections()
	 * @see #getIInterfaceElement()
	 * @generated
	 */
	EReference getIInterfaceElement_OutputConnections();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement#getType()
	 * @see #getIInterfaceElement()
	 * @generated
	 */
	EReference getIInterfaceElement_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Import <em>Import</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Import
	 * @generated
	 */
	EClass getImport();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Import#getImportedNamespace <em>Imported Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Imported Namespace</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Import#getImportedNamespace()
	 * @see #getImport()
	 * @generated
	 */
	EAttribute getImport_ImportedNamespace();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement <em>INamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>INamed Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * @generated
	 */
	EClass getINamedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getName()
	 * @see #getINamedElement()
	 * @generated
	 */
	EAttribute getINamedElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement#getComment()
	 * @see #getINamedElement()
	 * @generated
	 */
	EAttribute getINamedElement_Comment();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive <em>Input Primitive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Input Primitive</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InputPrimitive
	 * @generated
	 */
	EClass getInputPrimitive();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList <em>Interface List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Interface List</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList
	 * @generated
	 */
	EClass getInterfaceList();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getEventInputs <em>Event Inputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event Inputs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getEventInputs()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_EventInputs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getEventOutputs <em>Event Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Event Outputs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getEventOutputs()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_EventOutputs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getInputVars <em>Input Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input Vars</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getInputVars()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_InputVars();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getOutputVars <em>Output Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Vars</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getOutputVars()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_OutputVars();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getInOutVars <em>In Out Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>In Out Vars</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getInOutVars()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_InOutVars();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getOutMappedInOutVars <em>Out Mapped In Out Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Out Mapped In Out Vars</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getOutMappedInOutVars()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_OutMappedInOutVars();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getPlugs <em>Plugs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plugs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getPlugs()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_Plugs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getSockets <em>Sockets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sockets</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getSockets()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_Sockets();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getErrorMarker <em>Error Marker</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Error Marker</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.InterfaceList#getErrorMarker()
	 * @see #getInterfaceList()
	 * @generated
	 */
	EReference getInterfaceList_ErrorMarker();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ITypedElement <em>ITyped Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ITyped Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ITypedElement
	 * @generated
	 */
	EClass getITypedElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.IVarElement <em>IVar Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IVar Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IVarElement
	 * @generated
	 */
	EClass getIVarElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.IVarElement#getVarDeclarations <em>Var Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declarations</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.IVarElement#getVarDeclarations()
	 * @see #getIVarElement()
	 * @generated
	 */
	EReference getIVarElement_VarDeclarations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement <em>Library Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Library Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
	 * @generated
	 */
	EClass getLibraryElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getVersionInfo <em>Version Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Version Info</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getVersionInfo()
	 * @see #getLibraryElement()
	 * @generated
	 */
	EReference getLibraryElement_VersionInfo();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getIdentification <em>Identification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Identification</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getIdentification()
	 * @see #getLibraryElement()
	 * @generated
	 */
	EReference getLibraryElement_Identification();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getCompilerInfo <em>Compiler Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Compiler Info</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getCompilerInfo()
	 * @see #getLibraryElement()
	 * @generated
	 */
	EReference getLibraryElement_CompilerInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getTypeEntry <em>Type Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElement#getTypeEntry()
	 * @see #getLibraryElement()
	 * @generated
	 */
	EAttribute getLibraryElement_TypeEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Link#getSegment <em>Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Segment</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Link#getSegment()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Segment();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Link#getDevice <em>Device</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Device</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Link#getDevice()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Device();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.LocalVariable <em>Local Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Variable</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LocalVariable
	 * @generated
	 */
	EClass getLocalVariable();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.LocalVariable#getArrayStart <em>Array Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array Start</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LocalVariable#getArrayStart()
	 * @see #getLocalVariable()
	 * @generated
	 */
	EAttribute getLocalVariable_ArrayStart();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.LocalVariable#getArrayStop <em>Array Stop</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array Stop</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LocalVariable#getArrayStop()
	 * @see #getLocalVariable()
	 * @generated
	 */
	EAttribute getLocalVariable_ArrayStop();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Mapping <em>Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Mapping
	 * @generated
	 */
	EClass getMapping();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Mapping#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Mapping#getFrom()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_From();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Mapping#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Mapping#getTo()
	 * @see #getMapping()
	 * @generated
	 */
	EReference getMapping_To();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.MappingTarget <em>Mapping Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Target</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.MappingTarget
	 * @generated
	 */
	EClass getMappingTarget();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration <em>Member Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Member Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration
	 * @generated
	 */
	EClass getMemberVarDeclaration();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration#getParentNames <em>Parent Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parent Names</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.MemberVarDeclaration#getParentNames()
	 * @see #getMemberVarDeclaration()
	 * @generated
	 */
	EAttribute getMemberVarDeclaration_ParentNames();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Method <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Method</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Method
	 * @generated
	 */
	EClass getMethod();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Multiplexer <em>Multiplexer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multiplexer</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Multiplexer
	 * @generated
	 */
	EClass getMultiplexer();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.OriginalSource <em>Original Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Original Source</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OriginalSource
	 * @generated
	 */
	EClass getOriginalSource();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.OriginalSource#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OriginalSource#getText()
	 * @see #getOriginalSource()
	 * @generated
	 */
	EAttribute getOriginalSource_Text();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm <em>Other Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Other Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm
	 * @generated
	 */
	EClass getOtherAlgorithm();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm#getLanguage()
	 * @see #getOtherAlgorithm()
	 * @generated
	 */
	EAttribute getOtherAlgorithm_Language();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.OtherMethod <em>Other Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Other Method</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OtherMethod
	 * @generated
	 */
	EClass getOtherMethod();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.OtherMethod#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OtherMethod#getLanguage()
	 * @see #getOtherMethod()
	 * @generated
	 */
	EAttribute getOtherMethod_Language();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive <em>Output Primitive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Output Primitive</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.OutputPrimitive
	 * @generated
	 */
	EClass getOutputPrimitive();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Position <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Position</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Position
	 * @generated
	 */
	EClass getPosition();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Position#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Position#getX()
	 * @see #getPosition()
	 * @generated
	 */
	EAttribute getPosition_X();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Position#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Position#getY()
	 * @see #getPosition()
	 * @generated
	 */
	EAttribute getPosition_Y();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement <em>Positionable Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Positionable Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.PositionableElement
	 * @generated
	 */
	EClass getPositionableElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Position</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.PositionableElement#getPosition()
	 * @see #getPositionableElement()
	 * @generated
	 */
	EReference getPositionableElement_Position();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Primitive <em>Primitive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Primitive</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Primitive
	 * @generated
	 */
	EClass getPrimitive();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Primitive#getEvent <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Primitive#getEvent()
	 * @see #getPrimitive()
	 * @generated
	 */
	EAttribute getPrimitive_Event();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Primitive#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Primitive#getParameters()
	 * @see #getPrimitive()
	 * @generated
	 */
	EAttribute getPrimitive_Parameters();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Primitive#getInterface <em>Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Primitive#getInterface()
	 * @see #getPrimitive()
	 * @generated
	 */
	EReference getPrimitive_Interface();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#getFBNetwork()
	 * @see #getResource()
	 * @generated
	 */
	EReference getResource_FBNetwork();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#getX()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_X();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#getY()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Y();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#getDevice <em>Device</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Device</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#getDevice()
	 * @see #getResource()
	 * @generated
	 */
	EReference getResource_Device();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Resource#isDeviceTypeResource <em>Device Type Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Device Type Resource</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Resource#isDeviceTypeResource()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_DeviceTypeResource();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName <em>Resource Type Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Type Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName
	 * @generated
	 */
	EClass getResourceTypeName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeName#getName()
	 * @see #getResourceTypeName()
	 * @generated
	 */
	EAttribute getResourceTypeName_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceType <em>Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceType
	 * @generated
	 */
	EClass getResourceType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getVarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getVarDeclaration()
	 * @see #getResourceType()
	 * @generated
	 */
	EReference getResourceType_VarDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getFBNetwork <em>FB Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>FB Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getFBNetwork()
	 * @see #getResourceType()
	 * @generated
	 */
	EReference getResourceType_FBNetwork();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getSupportedFBTypes <em>Supported FB Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Supported FB Types</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceType#getSupportedFBTypes()
	 * @see #getResourceType()
	 * @generated
	 */
	EReference getResourceType_SupportedFBTypes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB <em>Resource Type FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Type FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ResourceTypeFB
	 * @generated
	 */
	EClass getResourceTypeFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Segment <em>Segment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Segment</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Segment
	 * @generated
	 */
	EClass getSegment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Segment#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Segment#getWidth()
	 * @see #getSegment()
	 * @generated
	 */
	EAttribute getSegment_Width();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Segment#getOutConnections <em>Out Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Out Connections</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Segment#getOutConnections()
	 * @see #getSegment()
	 * @generated
	 */
	EReference getSegment_OutConnections();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Segment#getCommunication <em>Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Communication</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Segment#getCommunication()
	 * @see #getSegment()
	 * @generated
	 */
	EReference getSegment_Communication();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SegmentType <em>Segment Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Segment Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SegmentType
	 * @generated
	 */
	EClass getSegmentType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.SegmentType#getVarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SegmentType#getVarDeclaration()
	 * @see #getSegmentType()
	 * @generated
	 */
	EReference getSegmentType_VarDeclaration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Service <em>Service</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Service
	 * @generated
	 */
	EClass getService();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getRightInterface <em>Right Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Service#getRightInterface()
	 * @see #getService()
	 * @generated
	 */
	EReference getService_RightInterface();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getLeftInterface <em>Left Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Service#getLeftInterface()
	 * @see #getService()
	 * @generated
	 */
	EReference getService_LeftInterface();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.Service#getServiceSequence <em>Service Sequence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Service Sequence</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Service#getServiceSequence()
	 * @see #getService()
	 * @generated
	 */
	EReference getService_ServiceSequence();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence <em>Service Sequence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Sequence</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence
	 * @generated
	 */
	EClass getServiceSequence();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getServiceTransaction <em>Service Transaction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Service Transaction</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getServiceTransaction()
	 * @see #getServiceSequence()
	 * @generated
	 */
	EReference getServiceSequence_ServiceTransaction();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getServiceSequenceType <em>Service Sequence Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Service Sequence Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getServiceSequenceType()
	 * @see #getServiceSequence()
	 * @generated
	 */
	EAttribute getServiceSequence_ServiceSequenceType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getStartState <em>Start State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start State</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getStartState()
	 * @see #getServiceSequence()
	 * @generated
	 */
	EAttribute getServiceSequence_StartState();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getEventManager <em>Event Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Event Manager</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence#getEventManager()
	 * @see #getServiceSequence()
	 * @generated
	 */
	EReference getServiceSequence_EventManager();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction <em>Service Transaction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Transaction</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction
	 * @generated
	 */
	EClass getServiceTransaction();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getInputPrimitive <em>Input Primitive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Input Primitive</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getInputPrimitive()
	 * @see #getServiceTransaction()
	 * @generated
	 */
	EReference getServiceTransaction_InputPrimitive();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getOutputPrimitive <em>Output Primitive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Primitive</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction#getOutputPrimitive()
	 * @see #getServiceTransaction()
	 * @generated
	 */
	EReference getServiceTransaction_OutputPrimitive();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface <em>Service Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Interface</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceInterface
	 * @generated
	 */
	EClass getServiceInterface();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType <em>Service Interface FB Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Interface FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType
	 * @generated
	 */
	EClass getServiceInterfaceFBType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction <em>Simple EC Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple EC Action</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction
	 * @generated
	 */
	EClass getSimpleECAction();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getAlgorithm <em>Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getAlgorithm()
	 * @see #getSimpleECAction()
	 * @generated
	 */
	EAttribute getSimpleECAction_Algorithm();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getOutput <em>Output</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Output</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getOutput()
	 * @see #getSimpleECAction()
	 * @generated
	 */
	EReference getSimpleECAction_Output();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getSimpleECState <em>Simple EC State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Simple EC State</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECAction#getSimpleECState()
	 * @see #getSimpleECAction()
	 * @generated
	 */
	EReference getSimpleECAction_SimpleECState();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState <em>Simple EC State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple EC State</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECState
	 * @generated
	 */
	EClass getSimpleECState();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleECActions <em>Simple EC Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Simple EC Actions</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleECActions()
	 * @see #getSimpleECState()
	 * @generated
	 */
	EReference getSimpleECState_SimpleECActions();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getInputEvent <em>Input Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Input Event</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getInputEvent()
	 * @see #getSimpleECState()
	 * @generated
	 */
	EReference getSimpleECState_InputEvent();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleFBType <em>Simple FB Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Simple FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleECState#getSimpleFBType()
	 * @see #getSimpleECState()
	 * @generated
	 */
	EReference getSimpleECState_SimpleFBType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType <em>Simple FB Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple FB Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
	 * @generated
	 */
	EClass getSimpleFBType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType#getSimpleECStates <em>Simple EC States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Simple EC States</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType#getSimpleECStates()
	 * @see #getSimpleFBType()
	 * @generated
	 */
	EReference getSimpleFBType_SimpleECStates();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm <em>ST Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
	 * @generated
	 */
	EClass getSTAlgorithm();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.STFunction <em>ST Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Function</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.STFunction
	 * @generated
	 */
	EClass getSTFunction();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody <em>ST Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Function Body</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody
	 * @generated
	 */
	EClass getSTFunctionBody();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.STMethod <em>ST Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Method</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.STMethod
	 * @generated
	 */
	EClass getSTMethod();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp <em>Sub App</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub App</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SubApp
	 * @generated
	 */
	EClass getSubApp();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#getWidth <em>Width</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SubApp#getWidth()
	 * @see #getSubApp()
	 * @generated
	 */
	EAttribute getSubApp_Width();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#getHeight <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SubApp#getHeight()
	 * @see #getSubApp()
	 * @generated
	 */
	EAttribute getSubApp_Height();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.SubApp#isLocked <em>Locked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Locked</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SubApp#isLocked()
	 * @see #getSubApp()
	 * @generated
	 */
	EAttribute getSubApp_Locked();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.StructManipulator <em>Struct Manipulator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Struct Manipulator</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.StructManipulator
	 * @generated
	 */
	EClass getStructManipulator();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SubAppType <em>Sub App Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub App Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SubAppType
	 * @generated
	 */
	EClass getSubAppType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration <em>System Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>System Configuration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration
	 * @generated
	 */
	EClass getSystemConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getDevices <em>Devices</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Devices</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getDevices()
	 * @see #getSystemConfiguration()
	 * @generated
	 */
	EReference getSystemConfiguration_Devices();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getSegments <em>Segments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Segments</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getSegments()
	 * @see #getSystemConfiguration()
	 * @generated
	 */
	EReference getSystemConfiguration_Segments();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.SystemConfiguration#getLinks()
	 * @see #getSystemConfiguration()
	 * @generated
	 */
	EReference getSystemConfiguration_Links();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm <em>Text Algorithm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Algorithm</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm
	 * @generated
	 */
	EClass getTextAlgorithm();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextAlgorithm#getText()
	 * @see #getTextAlgorithm()
	 * @generated
	 */
	EAttribute getTextAlgorithm_Text();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.TextFunction <em>Text Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Function</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextFunction
	 * @generated
	 */
	EClass getTextFunction();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.TextFunction#getInputParameters <em>Input Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextFunction#getInputParameters()
	 * @see #getTextFunction()
	 * @generated
	 */
	EReference getTextFunction_InputParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.TextFunction#getOutputParameters <em>Output Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextFunction#getOutputParameters()
	 * @see #getTextFunction()
	 * @generated
	 */
	EReference getTextFunction_OutputParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.TextFunction#getInOutParameters <em>In Out Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>In Out Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextFunction#getInOutParameters()
	 * @see #getTextFunction()
	 * @generated
	 */
	EReference getTextFunction_InOutParameters();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.TextFunction#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Return Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextFunction#getReturnType()
	 * @see #getTextFunction()
	 * @generated
	 */
	EReference getTextFunction_ReturnType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.TextFunction#isVarargs <em>Varargs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Varargs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextFunction#isVarargs()
	 * @see #getTextFunction()
	 * @generated
	 */
	EAttribute getTextFunction_Varargs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.TextFunction#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextFunction#getText()
	 * @see #getTextFunction()
	 * @generated
	 */
	EAttribute getTextFunction_Text();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.TextFunctionBody <em>Text Function Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Function Body</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextFunctionBody
	 * @generated
	 */
	EClass getTextFunctionBody();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.TextFunctionBody#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextFunctionBody#getText()
	 * @see #getTextFunctionBody()
	 * @generated
	 */
	EAttribute getTextFunctionBody_Text();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.TextMethod <em>Text Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Method</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextMethod
	 * @generated
	 */
	EClass getTextMethod();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.TextMethod#getInputParameters <em>Input Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextMethod#getInputParameters()
	 * @see #getTextMethod()
	 * @generated
	 */
	EReference getTextMethod_InputParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.TextMethod#getOutputParameters <em>Output Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextMethod#getOutputParameters()
	 * @see #getTextMethod()
	 * @generated
	 */
	EReference getTextMethod_OutputParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.TextMethod#getInOutParameters <em>In Out Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>In Out Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextMethod#getInOutParameters()
	 * @see #getTextMethod()
	 * @generated
	 */
	EReference getTextMethod_InOutParameters();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.TextMethod#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Return Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextMethod#getReturnType()
	 * @see #getTextMethod()
	 * @generated
	 */
	EReference getTextMethod_ReturnType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.TextMethod#isVarargs <em>Varargs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Varargs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextMethod#isVarargs()
	 * @see #getTextMethod()
	 * @generated
	 */
	EAttribute getTextMethod_Varargs();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.TextMethod#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TextMethod#getText()
	 * @see #getTextMethod()
	 * @generated
	 */
	EAttribute getTextMethod_Text();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject <em>Typed Configureable Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Configureable Object</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject
	 * @generated
	 */
	EClass getTypedConfigureableObject();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject#getTypeEntry <em>Type Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject#getTypeEntry()
	 * @see #getTypedConfigureableObject()
	 * @generated
	 */
	EAttribute getTypedConfigureableObject_TypeEntry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp <em>Typed Sub App</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Sub App</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp
	 * @generated
	 */
	EClass getTypedSubApp();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp#getSubAppNetwork <em>Sub App Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sub App Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.TypedSubApp#getSubAppNetwork()
	 * @see #getTypedSubApp()
	 * @generated
	 */
	EReference getTypedSubApp_SubAppNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp <em>Untyped Sub App</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Untyped Sub App</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp
	 * @generated
	 */
	EClass getUntypedSubApp();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp#getSubAppNetwork <em>Sub App Network</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Sub App Network</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp#getSubAppNetwork()
	 * @see #getUntypedSubApp()
	 * @generated
	 */
	EReference getUntypedSubApp_SubAppNetwork();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Value
	 * @generated
	 */
	EClass getValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.Value#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Value#getValue()
	 * @see #getValue()
	 * @generated
	 */
	EAttribute getValue_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
	 * @generated
	 */
	EClass getVarDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getArraySize <em>Array Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Array Size</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getArraySize()
	 * @see #getVarDeclaration()
	 * @generated
	 */
	EReference getVarDeclaration_ArraySize();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getWiths <em>Withs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Withs</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getWiths()
	 * @see #getVarDeclaration()
	 * @generated
	 */
	EReference getVarDeclaration_Withs();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getValue()
	 * @see #getVarDeclaration()
	 * @generated
	 */
	EReference getVarDeclaration_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo <em>Version Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Version Info</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo
	 * @generated
	 */
	EClass getVersionInfo();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getAuthor()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Author();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getDate()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Date();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getOrganization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Organization</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getOrganization()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Organization();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getRemarks <em>Remarks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remarks</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getRemarks()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Remarks();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getVersion()
	 * @see #getVersionInfo()
	 * @generated
	 */
	EAttribute getVersionInfo_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.libraryElement.With <em>With</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>With</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.With
	 * @generated
	 */
	EClass getWith();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.libraryElement.With#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variables</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.With#getVariables()
	 * @see #getWith()
	 * @generated
	 */
	EReference getWith_Variables();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.model.libraryElement.Language <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Language</em>'.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Language
	 * @generated
	 */
	EEnum getLanguage();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.gef.commands.CommandStack <em>Command Stack</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Command Stack</em>'.
	 * @see org.eclipse.gef.commands.CommandStack
	 * @model instanceClass="org.eclipse.gef.commands.CommandStack"
	 * @generated
	 */
	EDataType getCommandStack();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.core.resources.IFile <em>IFile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IFile</em>'.
	 * @see org.eclipse.core.resources.IFile
	 * @model instanceClass="org.eclipse.core.resources.IFile"
	 * @generated
	 */
	EDataType getIFile();

	/**
	 * Returns the meta object for data type '{@link java.util.stream.Stream <em>Interface Element Stream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Interface Element Stream</em>'.
	 * @see java.util.stream.Stream
	 * @model instanceClass="java.util.stream.Stream&lt;org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement&gt;"
	 * @generated
	 */
	EDataType getInterfaceElementStream();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.core.resources.IProject <em>IProject</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>IProject</em>'.
	 * @see org.eclipse.core.resources.IProject
	 * @model instanceClass="org.eclipse.core.resources.IProject"
	 * @generated
	 */
	EDataType getIProject();

	/**
	 * Returns the meta object for data type '{@link java.util.stream.Stream <em>Named Element Stream</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Named Element Stream</em>'.
	 * @see java.util.stream.Stream
	 * @model instanceClass="java.util.stream.Stream&lt;org.eclipse.fordiac.ide.model.libraryElement.INamedElement&gt;"
	 * @generated
	 */
	EDataType getNamedElementStream();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.draw2d.geometry.Point <em>Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Point</em>'.
	 * @see org.eclipse.draw2d.geometry.Point
	 * @model instanceClass="org.eclipse.draw2d.geometry.Point"
	 * @generated
	 */
	EDataType getPoint();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.fordiac.ide.model.typelibrary.TypeEntry <em>Type Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Type Entry</em>'.
	 * @see org.eclipse.fordiac.ide.model.typelibrary.TypeEntry
	 * @model instanceClass="org.eclipse.fordiac.ide.model.typelibrary.TypeEntry"
	 * @generated
	 */
	EDataType getTypeEntry();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary <em>Type Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Type Library</em>'.
	 * @see org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
	 * @model instanceClass="org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary"
	 * @generated
	 */
	EDataType getTypeLibrary();

	/**
	 * Returns the meta object for data type '{@link java.util.List <em>Var Decl List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Var Decl List</em>'.
	 * @see java.util.List
	 * @model instanceClass="java.util.List&lt;org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration&gt;"
	 * @generated
	 */
	EDataType getVarDeclList();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LibraryElementFactory getLibraryElementFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterConnectionImpl <em>Adapter Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterConnectionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterConnection()
		 * @generated
		 */
		EClass ADAPTER_CONNECTION = eINSTANCE.getAdapterConnection();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_CONNECTION__FB_NETWORK = eINSTANCE.getAdapterConnection_FBNetwork();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl <em>Adapter Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterDeclarationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterDeclaration()
		 * @generated
		 */
		EClass ADAPTER_DECLARATION = eINSTANCE.getAdapterDeclaration();

		/**
		 * The meta object literal for the '<em><b>Adapter FB</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_DECLARATION__ADAPTER_FB = eINSTANCE.getAdapterDeclaration_AdapterFB();

		/**
		 * The meta object literal for the '<em><b>Interface Only Adapter FB</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_DECLARATION__INTERFACE_ONLY_ADAPTER_FB = eINSTANCE.getAdapterDeclaration_InterfaceOnlyAdapterFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBImpl <em>Adapter FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterFBImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterFB()
		 * @generated
		 */
		EClass ADAPTER_FB = eINSTANCE.getAdapterFB();

		/**
		 * The meta object literal for the '<em><b>Adapter Decl</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_FB__ADAPTER_DECL = eINSTANCE.getAdapterFB_AdapterDecl();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl <em>Adapter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AdapterTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAdapterType()
		 * @generated
		 */
		EClass ADAPTER_TYPE = eINSTANCE.getAdapterType();

		/**
		 * The meta object literal for the '<em><b>Plug Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_TYPE__PLUG_TYPE = eINSTANCE.getAdapterType_PlugType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AlgorithmImpl <em>Algorithm</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AlgorithmImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAlgorithm()
		 * @generated
		 */
		EClass ALGORITHM = eINSTANCE.getAlgorithm();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ApplicationImpl <em>Application</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ApplicationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getApplication()
		 * @generated
		 */
		EClass APPLICATION = eINSTANCE.getApplication();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION__FB_NETWORK = eINSTANCE.getApplication_FBNetwork();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ArraySizeImpl <em>Array Size</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ArraySizeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getArraySize()
		 * @generated
		 */
		EClass ARRAY_SIZE = eINSTANCE.getArraySize();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_SIZE__VALUE = eINSTANCE.getArraySize_Value();

		/**
		 * The meta object literal for the '<em><b>Var Declaration</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARRAY_SIZE__VAR_DECLARATION = eINSTANCE.getArraySize_VarDeclaration();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Attribute Declaration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__ATTRIBUTE_DECLARATION = eINSTANCE.getAttribute_AttributeDeclaration();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__TYPE = eINSTANCE.getAttribute_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE__VALUE = eINSTANCE.getAttribute_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl <em>Attribute Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AttributeDeclarationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAttributeDeclaration()
		 * @generated
		 */
		EClass ATTRIBUTE_DECLARATION = eINSTANCE.getAttributeDeclaration();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE_DECLARATION__TYPE = eINSTANCE.getAttributeDeclaration_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl <em>Base FB Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.BaseFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getBaseFBType()
		 * @generated
		 */
		EClass BASE_FB_TYPE = eINSTANCE.getBaseFBType();

		/**
		 * The meta object literal for the '<em><b>Internal Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE_FB_TYPE__INTERNAL_VARS = eINSTANCE.getBaseFBType_InternalVars();

		/**
		 * The meta object literal for the '<em><b>Internal Const Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE_FB_TYPE__INTERNAL_CONST_VARS = eINSTANCE.getBaseFBType_InternalConstVars();

		/**
		 * The meta object literal for the '<em><b>Internal Fbs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE_FB_TYPE__INTERNAL_FBS = eINSTANCE.getBaseFBType_InternalFbs();

		/**
		 * The meta object literal for the '<em><b>Algorithm</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE_FB_TYPE__ALGORITHM = eINSTANCE.getBaseFBType_Algorithm();

		/**
		 * The meta object literal for the '<em><b>Methods</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE_FB_TYPE__METHODS = eINSTANCE.getBaseFBType_Methods();

		/**
		 * The meta object literal for the '<em><b>Callables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE_FB_TYPE__CALLABLES = eINSTANCE.getBaseFBType_Callables();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.BasicFBTypeImpl <em>Basic FB Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.BasicFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getBasicFBType()
		 * @generated
		 */
		EClass BASIC_FB_TYPE = eINSTANCE.getBasicFBType();

		/**
		 * The meta object literal for the '<em><b>ECC</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASIC_FB_TYPE__ECC = eINSTANCE.getBasicFBType_ECC();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl <em>Automation System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.AutomationSystemImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getAutomationSystem()
		 * @generated
		 */
		EClass AUTOMATION_SYSTEM = eINSTANCE.getAutomationSystem();

		/**
		 * The meta object literal for the '<em><b>Application</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTOMATION_SYSTEM__APPLICATION = eINSTANCE.getAutomationSystem_Application();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTOMATION_SYSTEM__MAPPING = eINSTANCE.getAutomationSystem_Mapping();

		/**
		 * The meta object literal for the '<em><b>System Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AUTOMATION_SYSTEM__SYSTEM_CONFIGURATION = eINSTANCE.getAutomationSystem_SystemConfiguration();

		/**
		 * The meta object literal for the '<em><b>Command Stack</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AUTOMATION_SYSTEM__COMMAND_STACK = eINSTANCE.getAutomationSystem_CommandStack();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CFBInstanceImpl <em>CFB Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CFBInstanceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCFBInstance()
		 * @generated
		 */
		EClass CFB_INSTANCE = eINSTANCE.getCFBInstance();

		/**
		 * The meta object literal for the '<em><b>Cfb Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CFB_INSTANCE__CFB_NETWORK = eINSTANCE.getCFBInstance_CfbNetwork();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ColorImpl <em>Color</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ColorImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getColor()
		 * @generated
		 */
		EClass COLOR = eINSTANCE.getColor();

		/**
		 * The meta object literal for the '<em><b>Red</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__RED = eINSTANCE.getColor_Red();

		/**
		 * The meta object literal for the '<em><b>Green</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__GREEN = eINSTANCE.getColor_Green();

		/**
		 * The meta object literal for the '<em><b>Blue</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__BLUE = eINSTANCE.getColor_Blue();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ColorizableElementImpl <em>Colorizable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ColorizableElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getColorizableElement()
		 * @generated
		 */
		EClass COLORIZABLE_ELEMENT = eINSTANCE.getColorizableElement();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLORIZABLE_ELEMENT__COLOR = eINSTANCE.getColorizableElement_Color();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CommentImpl <em>Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CommentImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__WIDTH = eINSTANCE.getComment_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMENT__HEIGHT = eINSTANCE.getComment_Height();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CommunicationChannelImpl <em>Communication Channel</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CommunicationChannelImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCommunicationChannel()
		 * @generated
		 */
		EClass COMMUNICATION_CHANNEL = eINSTANCE.getCommunicationChannel();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration <em>Communication Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCommunicationConfiguration()
		 * @generated
		 */
		EClass COMMUNICATION_CONFIGURATION = eINSTANCE.getCommunicationConfiguration();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CommunicationMappingTargetImpl <em>Communication Mapping Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CommunicationMappingTargetImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCommunicationMappingTarget()
		 * @generated
		 */
		EClass COMMUNICATION_MAPPING_TARGET = eINSTANCE.getCommunicationMappingTarget();

		/**
		 * The meta object literal for the '<em><b>Mapped Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION_MAPPING_TARGET__MAPPED_ELEMENTS = eINSTANCE.getCommunicationMappingTarget_MappedElements();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerImpl <em>Compiler</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompiler()
		 * @generated
		 */
		EClass COMPILER = eINSTANCE.getCompiler();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER__LANGUAGE = eINSTANCE.getCompiler_Language();

		/**
		 * The meta object literal for the '<em><b>Product</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER__PRODUCT = eINSTANCE.getCompiler_Product();

		/**
		 * The meta object literal for the '<em><b>Vendor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER__VENDOR = eINSTANCE.getCompiler_Vendor();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER__VERSION = eINSTANCE.getCompiler_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl <em>Compiler Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompilerInfoImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompilerInfo()
		 * @generated
		 */
		EClass COMPILER_INFO = eINSTANCE.getCompilerInfo();

		/**
		 * The meta object literal for the '<em><b>Compiler</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILER_INFO__COMPILER = eINSTANCE.getCompilerInfo_Compiler();

		/**
		 * The meta object literal for the '<em><b>Classdef</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER_INFO__CLASSDEF = eINSTANCE.getCompilerInfo_Classdef();

		/**
		 * The meta object literal for the '<em><b>Header</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER_INFO__HEADER = eINSTANCE.getCompilerInfo_Header();

		/**
		 * The meta object literal for the '<em><b>Package Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPILER_INFO__PACKAGE_NAME = eINSTANCE.getCompilerInfo_PackageName();

		/**
		 * The meta object literal for the '<em><b>Imports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPILER_INFO__IMPORTS = eINSTANCE.getCompilerInfo_Imports();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.CompositeFBTypeImpl <em>Composite FB Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.CompositeFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCompositeFBType()
		 * @generated
		 */
		EClass COMPOSITE_FB_TYPE = eINSTANCE.getCompositeFBType();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITE_FB_TYPE__FB_NETWORK = eINSTANCE.getCompositeFBType_FBNetwork();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableObjectImpl <em>Configurable Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableObjectImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConfigurableObject()
		 * @generated
		 */
		EClass CONFIGURABLE_OBJECT = eINSTANCE.getConfigurableObject();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURABLE_OBJECT__ATTRIBUTES = eINSTANCE.getConfigurableObject_Attributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableFBImpl <em>Configurable FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableFBImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConfigurableFB()
		 * @generated
		 */
		EClass CONFIGURABLE_FB = eINSTANCE.getConfigurableFB();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURABLE_FB__DATA_TYPE = eINSTANCE.getConfigurableFB_DataType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableMoveFBImpl <em>Configurable Move FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConfigurableMoveFBImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConfigurableMoveFB()
		 * @generated
		 */
		EClass CONFIGURABLE_MOVE_FB = eINSTANCE.getConfigurableMoveFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '<em><b>Broken Connection</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION__BROKEN_CONNECTION = eINSTANCE.getConnection_BrokenConnection();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__SOURCE = eINSTANCE.getConnection_Source();

		/**
		 * The meta object literal for the '<em><b>Destination</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__DESTINATION = eINSTANCE.getConnection_Destination();

		/**
		 * The meta object literal for the '<em><b>Routing Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__ROUTING_DATA = eINSTANCE.getConnection_RoutingData();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION__COMMENT = eINSTANCE.getConnection_Comment();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl <em>Connection Routing Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ConnectionRoutingDataImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getConnectionRoutingData()
		 * @generated
		 */
		EClass CONNECTION_ROUTING_DATA = eINSTANCE.getConnectionRoutingData();

		/**
		 * The meta object literal for the '<em><b>Dx1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION_ROUTING_DATA__DX1 = eINSTANCE.getConnectionRoutingData_Dx1();

		/**
		 * The meta object literal for the '<em><b>Dx2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION_ROUTING_DATA__DX2 = eINSTANCE.getConnectionRoutingData_Dx2();

		/**
		 * The meta object literal for the '<em><b>Dy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION_ROUTING_DATA__DY = eINSTANCE.getConnectionRoutingData_Dy();

		/**
		 * The meta object literal for the '<em><b>Needs Validation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION_ROUTING_DATA__NEEDS_VALIDATION = eINSTANCE.getConnectionRoutingData_NeedsValidation();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DataConnectionImpl <em>Data Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DataConnectionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDataConnection()
		 * @generated
		 */
		EClass DATA_CONNECTION = eINSTANCE.getDataConnection();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_CONNECTION__FB_NETWORK = eINSTANCE.getDataConnection_FBNetwork();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DemultiplexerImpl <em>Demultiplexer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DemultiplexerImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDemultiplexer()
		 * @generated
		 */
		EClass DEMULTIPLEXER = eINSTANCE.getDemultiplexer();

		/**
		 * The meta object literal for the '<em><b>Is Configured</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEMULTIPLEXER__IS_CONFIGURED = eINSTANCE.getDemultiplexer_IsConfigured();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl <em>Device</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDevice()
		 * @generated
		 */
		EClass DEVICE = eINSTANCE.getDevice();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE__RESOURCE = eINSTANCE.getDevice_Resource();

		/**
		 * The meta object literal for the '<em><b>Profile</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE__PROFILE = eINSTANCE.getDevice_Profile();

		/**
		 * The meta object literal for the '<em><b>In Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE__IN_CONNECTIONS = eINSTANCE.getDevice_InConnections();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceTypeImpl <em>Device Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.DeviceTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getDeviceType()
		 * @generated
		 */
		EClass DEVICE_TYPE = eINSTANCE.getDeviceType();

		/**
		 * The meta object literal for the '<em><b>Var Declaration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE_TYPE__VAR_DECLARATION = eINSTANCE.getDeviceType_VarDeclaration();

		/**
		 * The meta object literal for the '<em><b>Resource Type Name</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE_TYPE__RESOURCE_TYPE_NAME = eINSTANCE.getDeviceType_ResourceTypeName();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE_TYPE__RESOURCE = eINSTANCE.getDeviceType_Resource();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE_TYPE__FB_NETWORK = eINSTANCE.getDeviceType_FBNetwork();

		/**
		 * The meta object literal for the '<em><b>Profile</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEVICE_TYPE__PROFILE = eINSTANCE.getDeviceType_Profile();

		/**
		 * The meta object literal for the '<em><b>Attribute Declarations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEVICE_TYPE__ATTRIBUTE_DECLARATIONS = eINSTANCE.getDeviceType_AttributeDeclarations();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl <em>EC Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECActionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECAction()
		 * @generated
		 */
		EClass EC_ACTION = eINSTANCE.getECAction();

		/**
		 * The meta object literal for the '<em><b>Algorithm</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_ACTION__ALGORITHM = eINSTANCE.getECAction_Algorithm();

		/**
		 * The meta object literal for the '<em><b>Output</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_ACTION__OUTPUT = eINSTANCE.getECAction_Output();

		/**
		 * The meta object literal for the '<em><b>EC State</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_ACTION__EC_STATE = eINSTANCE.getECAction_ECState();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl <em>ECC</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECCImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECC()
		 * @generated
		 */
		EClass ECC = eINSTANCE.getECC();

		/**
		 * The meta object literal for the '<em><b>EC State</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECC__EC_STATE = eINSTANCE.getECC_ECState();

		/**
		 * The meta object literal for the '<em><b>EC Transition</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECC__EC_TRANSITION = eINSTANCE.getECC_ECTransition();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECC__START = eINSTANCE.getECC_Start();

		/**
		 * The meta object literal for the '<em><b>Basic FB Type</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ECC__BASIC_FB_TYPE = eINSTANCE.getECC_BasicFBType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl <em>EC State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECStateImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECState()
		 * @generated
		 */
		EClass EC_STATE = eINSTANCE.getECState();

		/**
		 * The meta object literal for the '<em><b>EC Action</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_STATE__EC_ACTION = eINSTANCE.getECState_ECAction();

		/**
		 * The meta object literal for the '<em><b>Out Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_STATE__OUT_TRANSITIONS = eINSTANCE.getECState_OutTransitions();

		/**
		 * The meta object literal for the '<em><b>In Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_STATE__IN_TRANSITIONS = eINSTANCE.getECState_InTransitions();

		/**
		 * The meta object literal for the '<em><b>ECC</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_STATE__ECC = eINSTANCE.getECState_ECC();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ECTransitionImpl <em>EC Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ECTransitionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getECTransition()
		 * @generated
		 */
		EClass EC_TRANSITION = eINSTANCE.getECTransition();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EC_TRANSITION__COMMENT = eINSTANCE.getECTransition_Comment();

		/**
		 * The meta object literal for the '<em><b>Condition Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EC_TRANSITION__CONDITION_EXPRESSION = eINSTANCE.getECTransition_ConditionExpression();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_TRANSITION__SOURCE = eINSTANCE.getECTransition_Source();

		/**
		 * The meta object literal for the '<em><b>Destination</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_TRANSITION__DESTINATION = eINSTANCE.getECTransition_Destination();

		/**
		 * The meta object literal for the '<em><b>Condition Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_TRANSITION__CONDITION_EVENT = eINSTANCE.getECTransition_ConditionEvent();

		/**
		 * The meta object literal for the '<em><b>ECC</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EC_TRANSITION__ECC = eINSTANCE.getECTransition_ECC();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerDataTypeImpl <em>Error Marker Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerDataTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getErrorMarkerDataType()
		 * @generated
		 */
		EClass ERROR_MARKER_DATA_TYPE = eINSTANCE.getErrorMarkerDataType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerFBNElementImpl <em>Error Marker FBN Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerFBNElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getErrorMarkerFBNElement()
		 * @generated
		 */
		EClass ERROR_MARKER_FBN_ELEMENT = eINSTANCE.getErrorMarkerFBNElement();

		/**
		 * The meta object literal for the '<em><b>Repaired Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ERROR_MARKER_FBN_ELEMENT__REPAIRED_ELEMENT = eINSTANCE.getErrorMarkerFBNElement_RepairedElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerInterfaceImpl <em>Error Marker Interface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ErrorMarkerInterfaceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getErrorMarkerInterface()
		 * @generated
		 */
		EClass ERROR_MARKER_INTERFACE = eINSTANCE.getErrorMarkerInterface();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ERROR_MARKER_INTERFACE__VALUE = eINSTANCE.getErrorMarkerInterface_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.EventImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getEvent()
		 * @generated
		 */
		EClass EVENT = eINSTANCE.getEvent();

		/**
		 * The meta object literal for the '<em><b>With</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT__WITH = eINSTANCE.getEvent_With();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.EventConnectionImpl <em>Event Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.EventConnectionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getEventConnection()
		 * @generated
		 */
		EClass EVENT_CONNECTION = eINSTANCE.getEventConnection();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_CONNECTION__FB_NETWORK = eINSTANCE.getEventConnection_FBNetwork();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl <em>FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFB()
		 * @generated
		 */
		EClass FB = eINSTANCE.getFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl <em>FB Network</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBNetwork()
		 * @generated
		 */
		EClass FB_NETWORK = eINSTANCE.getFBNetwork();

		/**
		 * The meta object literal for the '<em><b>Network Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_NETWORK__NETWORK_ELEMENTS = eINSTANCE.getFBNetwork_NetworkElements();

		/**
		 * The meta object literal for the '<em><b>Data Connections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_NETWORK__DATA_CONNECTIONS = eINSTANCE.getFBNetwork_DataConnections();

		/**
		 * The meta object literal for the '<em><b>Event Connections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_NETWORK__EVENT_CONNECTIONS = eINSTANCE.getFBNetwork_EventConnections();

		/**
		 * The meta object literal for the '<em><b>Adapter Connections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_NETWORK__ADAPTER_CONNECTIONS = eINSTANCE.getFBNetwork_AdapterConnections();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl <em>FB Network Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBNetworkElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBNetworkElement()
		 * @generated
		 */
		EClass FB_NETWORK_ELEMENT = eINSTANCE.getFBNetworkElement();

		/**
		 * The meta object literal for the '<em><b>Interface</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_NETWORK_ELEMENT__INTERFACE = eINSTANCE.getFBNetworkElement_Interface();

		/**
		 * The meta object literal for the '<em><b>Mapping</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_NETWORK_ELEMENT__MAPPING = eINSTANCE.getFBNetworkElement_Mapping();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_NETWORK_ELEMENT__GROUP = eINSTANCE.getFBNetworkElement_Group();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FBTypeImpl <em>FB Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFBType()
		 * @generated
		 */
		EClass FB_TYPE = eINSTANCE.getFBType();

		/**
		 * The meta object literal for the '<em><b>Interface List</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_TYPE__INTERFACE_LIST = eINSTANCE.getFBType_InterfaceList();

		/**
		 * The meta object literal for the '<em><b>Service</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_TYPE__SERVICE = eINSTANCE.getFBType_Service();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFunction()
		 * @generated
		 */
		EClass FUNCTION = eINSTANCE.getFunction();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionBodyImpl <em>Function Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionBodyImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFunctionBody()
		 * @generated
		 */
		EClass FUNCTION_BODY = eINSTANCE.getFunctionBody();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionFBTypeImpl <em>Function FB Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.FunctionFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getFunctionFBType()
		 * @generated
		 */
		EClass FUNCTION_FB_TYPE = eINSTANCE.getFunctionFBType();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION_FB_TYPE__BODY = eINSTANCE.getFunctionFBType_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.GlobalConstantsImpl <em>Global Constants</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.GlobalConstantsImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getGlobalConstants()
		 * @generated
		 */
		EClass GLOBAL_CONSTANTS = eINSTANCE.getGlobalConstants();

		/**
		 * The meta object literal for the '<em><b>Constants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_CONSTANTS__CONSTANTS = eINSTANCE.getGlobalConstants_Constants();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GLOBAL_CONSTANTS__SOURCE = eINSTANCE.getGlobalConstants_Source();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.GroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.GroupImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getGroup()
		 * @generated
		 */
		EClass GROUP = eINSTANCE.getGroup();

		/**
		 * The meta object literal for the '<em><b>Group Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP__GROUP_ELEMENTS = eINSTANCE.getGroup_GroupElements();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GROUP__WIDTH = eINSTANCE.getGroup_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GROUP__HEIGHT = eINSTANCE.getGroup_Height();

		/**
		 * The meta object literal for the '<em><b>Locked</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GROUP__LOCKED = eINSTANCE.getGroup_Locked();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.HiddenElementImpl <em>Hidden Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.HiddenElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getHiddenElement()
		 * @generated
		 */
		EClass HIDDEN_ELEMENT = eINSTANCE.getHiddenElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.ICallable <em>ICallable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.ICallable
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getICallable()
		 * @generated
		 */
		EClass ICALLABLE = eINSTANCE.getICallable();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.IdentificationImpl <em>Identification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.IdentificationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIdentification()
		 * @generated
		 */
		EClass IDENTIFICATION = eINSTANCE.getIdentification();

		/**
		 * The meta object literal for the '<em><b>Application Domain</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__APPLICATION_DOMAIN = eINSTANCE.getIdentification_ApplicationDomain();

		/**
		 * The meta object literal for the '<em><b>Classification</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__CLASSIFICATION = eINSTANCE.getIdentification_Classification();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__DESCRIPTION = eINSTANCE.getIdentification_Description();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__FUNCTION = eINSTANCE.getIdentification_Function();

		/**
		 * The meta object literal for the '<em><b>Standard</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__STANDARD = eINSTANCE.getIdentification_Standard();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDENTIFICATION__TYPE = eINSTANCE.getIdentification_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement <em>IInterface Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIInterfaceElement()
		 * @generated
		 */
		EClass IINTERFACE_ELEMENT = eINSTANCE.getIInterfaceElement();

		/**
		 * The meta object literal for the '<em><b>Is Input</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IINTERFACE_ELEMENT__IS_INPUT = eINSTANCE.getIInterfaceElement_IsInput();

		/**
		 * The meta object literal for the '<em><b>Input Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IINTERFACE_ELEMENT__INPUT_CONNECTIONS = eINSTANCE.getIInterfaceElement_InputConnections();

		/**
		 * The meta object literal for the '<em><b>Output Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IINTERFACE_ELEMENT__OUTPUT_CONNECTIONS = eINSTANCE.getIInterfaceElement_OutputConnections();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IINTERFACE_ELEMENT__TYPE = eINSTANCE.getIInterfaceElement_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ImportImpl <em>Import</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ImportImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getImport()
		 * @generated
		 */
		EClass IMPORT = eINSTANCE.getImport();

		/**
		 * The meta object literal for the '<em><b>Imported Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT__IMPORTED_NAMESPACE = eINSTANCE.getImport_ImportedNamespace();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement <em>INamed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getINamedElement()
		 * @generated
		 */
		EClass INAMED_ELEMENT = eINSTANCE.getINamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INAMED_ELEMENT__NAME = eINSTANCE.getINamedElement_Name();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INAMED_ELEMENT__COMMENT = eINSTANCE.getINamedElement_Comment();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InputPrimitiveImpl <em>Input Primitive</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.InputPrimitiveImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getInputPrimitive()
		 * @generated
		 */
		EClass INPUT_PRIMITIVE = eINSTANCE.getInputPrimitive();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl <em>Interface List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.InterfaceListImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getInterfaceList()
		 * @generated
		 */
		EClass INTERFACE_LIST = eINSTANCE.getInterfaceList();

		/**
		 * The meta object literal for the '<em><b>Event Inputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__EVENT_INPUTS = eINSTANCE.getInterfaceList_EventInputs();

		/**
		 * The meta object literal for the '<em><b>Event Outputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__EVENT_OUTPUTS = eINSTANCE.getInterfaceList_EventOutputs();

		/**
		 * The meta object literal for the '<em><b>Input Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__INPUT_VARS = eINSTANCE.getInterfaceList_InputVars();

		/**
		 * The meta object literal for the '<em><b>Output Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__OUTPUT_VARS = eINSTANCE.getInterfaceList_OutputVars();

		/**
		 * The meta object literal for the '<em><b>In Out Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__IN_OUT_VARS = eINSTANCE.getInterfaceList_InOutVars();

		/**
		 * The meta object literal for the '<em><b>Out Mapped In Out Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__OUT_MAPPED_IN_OUT_VARS = eINSTANCE.getInterfaceList_OutMappedInOutVars();

		/**
		 * The meta object literal for the '<em><b>Plugs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__PLUGS = eINSTANCE.getInterfaceList_Plugs();

		/**
		 * The meta object literal for the '<em><b>Sockets</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__SOCKETS = eINSTANCE.getInterfaceList_Sockets();

		/**
		 * The meta object literal for the '<em><b>Error Marker</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERFACE_LIST__ERROR_MARKER = eINSTANCE.getInterfaceList_ErrorMarker();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.ITypedElement <em>ITyped Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.ITypedElement
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getITypedElement()
		 * @generated
		 */
		EClass ITYPED_ELEMENT = eINSTANCE.getITypedElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.IVarElement <em>IVar Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.IVarElement
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIVarElement()
		 * @generated
		 */
		EClass IVAR_ELEMENT = eINSTANCE.getIVarElement();

		/**
		 * The meta object literal for the '<em><b>Var Declarations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IVAR_ELEMENT__VAR_DECLARATIONS = eINSTANCE.getIVarElement_VarDeclarations();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl <em>Library Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLibraryElement()
		 * @generated
		 */
		EClass LIBRARY_ELEMENT = eINSTANCE.getLibraryElement();

		/**
		 * The meta object literal for the '<em><b>Version Info</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_ELEMENT__VERSION_INFO = eINSTANCE.getLibraryElement_VersionInfo();

		/**
		 * The meta object literal for the '<em><b>Identification</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_ELEMENT__IDENTIFICATION = eINSTANCE.getLibraryElement_Identification();

		/**
		 * The meta object literal for the '<em><b>Compiler Info</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_ELEMENT__COMPILER_INFO = eINSTANCE.getLibraryElement_CompilerInfo();

		/**
		 * The meta object literal for the '<em><b>Type Entry</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIBRARY_ELEMENT__TYPE_ENTRY = eINSTANCE.getLibraryElement_TypeEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LinkImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Segment</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__SEGMENT = eINSTANCE.getLink_Segment();

		/**
		 * The meta object literal for the '<em><b>Device</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__DEVICE = eINSTANCE.getLink_Device();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.LocalVariableImpl <em>Local Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LocalVariableImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLocalVariable()
		 * @generated
		 */
		EClass LOCAL_VARIABLE = eINSTANCE.getLocalVariable();

		/**
		 * The meta object literal for the '<em><b>Array Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_VARIABLE__ARRAY_START = eINSTANCE.getLocalVariable_ArrayStart();

		/**
		 * The meta object literal for the '<em><b>Array Stop</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCAL_VARIABLE__ARRAY_STOP = eINSTANCE.getLocalVariable_ArrayStop();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MappingImpl <em>Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MappingImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMapping()
		 * @generated
		 */
		EClass MAPPING = eINSTANCE.getMapping();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__FROM = eINSTANCE.getMapping_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING__TO = eINSTANCE.getMapping_To();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MappingTargetImpl <em>Mapping Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MappingTargetImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMappingTarget()
		 * @generated
		 */
		EClass MAPPING_TARGET = eINSTANCE.getMappingTarget();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MemberVarDeclarationImpl <em>Member Var Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MemberVarDeclarationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMemberVarDeclaration()
		 * @generated
		 */
		EClass MEMBER_VAR_DECLARATION = eINSTANCE.getMemberVarDeclaration();

		/**
		 * The meta object literal for the '<em><b>Parent Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEMBER_VAR_DECLARATION__PARENT_NAMES = eINSTANCE.getMemberVarDeclaration_ParentNames();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MethodImpl <em>Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MethodImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMethod()
		 * @generated
		 */
		EClass METHOD = eINSTANCE.getMethod();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.MultiplexerImpl <em>Multiplexer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.MultiplexerImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getMultiplexer()
		 * @generated
		 */
		EClass MULTIPLEXER = eINSTANCE.getMultiplexer();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OriginalSourceImpl <em>Original Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OriginalSourceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOriginalSource()
		 * @generated
		 */
		EClass ORIGINAL_SOURCE = eINSTANCE.getOriginalSource();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORIGINAL_SOURCE__TEXT = eINSTANCE.getOriginalSource_Text();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OtherAlgorithmImpl <em>Other Algorithm</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OtherAlgorithmImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOtherAlgorithm()
		 * @generated
		 */
		EClass OTHER_ALGORITHM = eINSTANCE.getOtherAlgorithm();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OTHER_ALGORITHM__LANGUAGE = eINSTANCE.getOtherAlgorithm_Language();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OtherMethodImpl <em>Other Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OtherMethodImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOtherMethod()
		 * @generated
		 */
		EClass OTHER_METHOD = eINSTANCE.getOtherMethod();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OTHER_METHOD__LANGUAGE = eINSTANCE.getOtherMethod_Language();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.OutputPrimitiveImpl <em>Output Primitive</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.OutputPrimitiveImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getOutputPrimitive()
		 * @generated
		 */
		EClass OUTPUT_PRIMITIVE = eINSTANCE.getOutputPrimitive();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.PositionImpl <em>Position</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.PositionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPosition()
		 * @generated
		 */
		EClass POSITION = eINSTANCE.getPosition();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION__X = eINSTANCE.getPosition_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POSITION__Y = eINSTANCE.getPosition_Y();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.PositionableElementImpl <em>Positionable Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.PositionableElementImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPositionableElement()
		 * @generated
		 */
		EClass POSITIONABLE_ELEMENT = eINSTANCE.getPositionableElement();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference POSITIONABLE_ELEMENT__POSITION = eINSTANCE.getPositionableElement_Position();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.PrimitiveImpl <em>Primitive</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.PrimitiveImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPrimitive()
		 * @generated
		 */
		EClass PRIMITIVE = eINSTANCE.getPrimitive();

		/**
		 * The meta object literal for the '<em><b>Event</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMITIVE__EVENT = eINSTANCE.getPrimitive_Event();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRIMITIVE__PARAMETERS = eINSTANCE.getPrimitive_Parameters();

		/**
		 * The meta object literal for the '<em><b>Interface</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIMITIVE__INTERFACE = eINSTANCE.getPrimitive_Interface();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResource()
		 * @generated
		 */
		EClass RESOURCE = eINSTANCE.getResource();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE__FB_NETWORK = eINSTANCE.getResource_FBNetwork();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE__X = eINSTANCE.getResource_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE__Y = eINSTANCE.getResource_Y();

		/**
		 * The meta object literal for the '<em><b>Device</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE__DEVICE = eINSTANCE.getResource_Device();

		/**
		 * The meta object literal for the '<em><b>Device Type Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE__DEVICE_TYPE_RESOURCE = eINSTANCE.getResource_DeviceTypeResource();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeNameImpl <em>Resource Type Name</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeNameImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceTypeName()
		 * @generated
		 */
		EClass RESOURCE_TYPE_NAME = eINSTANCE.getResourceTypeName();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_TYPE_NAME__NAME = eINSTANCE.getResourceTypeName_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl <em>Resource Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceType()
		 * @generated
		 */
		EClass RESOURCE_TYPE = eINSTANCE.getResourceType();

		/**
		 * The meta object literal for the '<em><b>Var Declaration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_TYPE__VAR_DECLARATION = eINSTANCE.getResourceType_VarDeclaration();

		/**
		 * The meta object literal for the '<em><b>FB Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_TYPE__FB_NETWORK = eINSTANCE.getResourceType_FBNetwork();

		/**
		 * The meta object literal for the '<em><b>Supported FB Types</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_TYPE__SUPPORTED_FB_TYPES = eINSTANCE.getResourceType_SupportedFBTypes();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeFBImpl <em>Resource Type FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ResourceTypeFBImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getResourceTypeFB()
		 * @generated
		 */
		EClass RESOURCE_TYPE_FB = eINSTANCE.getResourceTypeFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl <em>Segment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSegment()
		 * @generated
		 */
		EClass SEGMENT = eINSTANCE.getSegment();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEGMENT__WIDTH = eINSTANCE.getSegment_Width();

		/**
		 * The meta object literal for the '<em><b>Out Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEGMENT__OUT_CONNECTIONS = eINSTANCE.getSegment_OutConnections();

		/**
		 * The meta object literal for the '<em><b>Communication</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEGMENT__COMMUNICATION = eINSTANCE.getSegment_Communication();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentTypeImpl <em>Segment Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SegmentTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSegmentType()
		 * @generated
		 */
		EClass SEGMENT_TYPE = eINSTANCE.getSegmentType();

		/**
		 * The meta object literal for the '<em><b>Var Declaration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEGMENT_TYPE__VAR_DECLARATION = eINSTANCE.getSegmentType_VarDeclaration();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl <em>Service</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getService()
		 * @generated
		 */
		EClass SERVICE = eINSTANCE.getService();

		/**
		 * The meta object literal for the '<em><b>Right Interface</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE__RIGHT_INTERFACE = eINSTANCE.getService_RightInterface();

		/**
		 * The meta object literal for the '<em><b>Left Interface</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE__LEFT_INTERFACE = eINSTANCE.getService_LeftInterface();

		/**
		 * The meta object literal for the '<em><b>Service Sequence</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE__SERVICE_SEQUENCE = eINSTANCE.getService_ServiceSequence();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl <em>Service Sequence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceSequenceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceSequence()
		 * @generated
		 */
		EClass SERVICE_SEQUENCE = eINSTANCE.getServiceSequence();

		/**
		 * The meta object literal for the '<em><b>Service Transaction</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_SEQUENCE__SERVICE_TRANSACTION = eINSTANCE.getServiceSequence_ServiceTransaction();

		/**
		 * The meta object literal for the '<em><b>Service Sequence Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_SEQUENCE__SERVICE_SEQUENCE_TYPE = eINSTANCE.getServiceSequence_ServiceSequenceType();

		/**
		 * The meta object literal for the '<em><b>Start State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_SEQUENCE__START_STATE = eINSTANCE.getServiceSequence_StartState();

		/**
		 * The meta object literal for the '<em><b>Event Manager</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_SEQUENCE__EVENT_MANAGER = eINSTANCE.getServiceSequence_EventManager();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceTransactionImpl <em>Service Transaction</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceTransactionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceTransaction()
		 * @generated
		 */
		EClass SERVICE_TRANSACTION = eINSTANCE.getServiceTransaction();

		/**
		 * The meta object literal for the '<em><b>Input Primitive</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_TRANSACTION__INPUT_PRIMITIVE = eINSTANCE.getServiceTransaction_InputPrimitive();

		/**
		 * The meta object literal for the '<em><b>Output Primitive</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_TRANSACTION__OUTPUT_PRIMITIVE = eINSTANCE.getServiceTransaction_OutputPrimitive();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceImpl <em>Service Interface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceInterface()
		 * @generated
		 */
		EClass SERVICE_INTERFACE = eINSTANCE.getServiceInterface();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceFBTypeImpl <em>Service Interface FB Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ServiceInterfaceFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getServiceInterfaceFBType()
		 * @generated
		 */
		EClass SERVICE_INTERFACE_FB_TYPE = eINSTANCE.getServiceInterfaceFBType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECActionImpl <em>Simple EC Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECActionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSimpleECAction()
		 * @generated
		 */
		EClass SIMPLE_EC_ACTION = eINSTANCE.getSimpleECAction();

		/**
		 * The meta object literal for the '<em><b>Algorithm</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_EC_ACTION__ALGORITHM = eINSTANCE.getSimpleECAction_Algorithm();

		/**
		 * The meta object literal for the '<em><b>Output</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_EC_ACTION__OUTPUT = eINSTANCE.getSimpleECAction_Output();

		/**
		 * The meta object literal for the '<em><b>Simple EC State</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_EC_ACTION__SIMPLE_EC_STATE = eINSTANCE.getSimpleECAction_SimpleECState();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECStateImpl <em>Simple EC State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleECStateImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSimpleECState()
		 * @generated
		 */
		EClass SIMPLE_EC_STATE = eINSTANCE.getSimpleECState();

		/**
		 * The meta object literal for the '<em><b>Simple EC Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_EC_STATE__SIMPLE_EC_ACTIONS = eINSTANCE.getSimpleECState_SimpleECActions();

		/**
		 * The meta object literal for the '<em><b>Input Event</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_EC_STATE__INPUT_EVENT = eINSTANCE.getSimpleECState_InputEvent();

		/**
		 * The meta object literal for the '<em><b>Simple FB Type</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_EC_STATE__SIMPLE_FB_TYPE = eINSTANCE.getSimpleECState_SimpleFBType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl <em>Simple FB Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SimpleFBTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSimpleFBType()
		 * @generated
		 */
		EClass SIMPLE_FB_TYPE = eINSTANCE.getSimpleFBType();

		/**
		 * The meta object literal for the '<em><b>Simple EC States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMPLE_FB_TYPE__SIMPLE_EC_STATES = eINSTANCE.getSimpleFBType_SimpleECStates();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.STAlgorithmImpl <em>ST Algorithm</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.STAlgorithmImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSTAlgorithm()
		 * @generated
		 */
		EClass ST_ALGORITHM = eINSTANCE.getSTAlgorithm();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.STFunctionImpl <em>ST Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.STFunctionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSTFunction()
		 * @generated
		 */
		EClass ST_FUNCTION = eINSTANCE.getSTFunction();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.STFunctionBodyImpl <em>ST Function Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.STFunctionBodyImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSTFunctionBody()
		 * @generated
		 */
		EClass ST_FUNCTION_BODY = eINSTANCE.getSTFunctionBody();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.STMethodImpl <em>ST Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.STMethodImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSTMethod()
		 * @generated
		 */
		EClass ST_METHOD = eINSTANCE.getSTMethod();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl <em>Sub App</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSubApp()
		 * @generated
		 */
		EClass SUB_APP = eINSTANCE.getSubApp();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUB_APP__WIDTH = eINSTANCE.getSubApp_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUB_APP__HEIGHT = eINSTANCE.getSubApp_Height();

		/**
		 * The meta object literal for the '<em><b>Locked</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUB_APP__LOCKED = eINSTANCE.getSubApp_Locked();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.StructManipulatorImpl <em>Struct Manipulator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.StructManipulatorImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getStructManipulator()
		 * @generated
		 */
		EClass STRUCT_MANIPULATOR = eINSTANCE.getStructManipulator();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppTypeImpl <em>Sub App Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SubAppTypeImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSubAppType()
		 * @generated
		 */
		EClass SUB_APP_TYPE = eINSTANCE.getSubAppType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.SystemConfigurationImpl <em>System Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.SystemConfigurationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getSystemConfiguration()
		 * @generated
		 */
		EClass SYSTEM_CONFIGURATION = eINSTANCE.getSystemConfiguration();

		/**
		 * The meta object literal for the '<em><b>Devices</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_CONFIGURATION__DEVICES = eINSTANCE.getSystemConfiguration_Devices();

		/**
		 * The meta object literal for the '<em><b>Segments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_CONFIGURATION__SEGMENTS = eINSTANCE.getSystemConfiguration_Segments();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYSTEM_CONFIGURATION__LINKS = eINSTANCE.getSystemConfiguration_Links();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextAlgorithmImpl <em>Text Algorithm</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TextAlgorithmImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTextAlgorithm()
		 * @generated
		 */
		EClass TEXT_ALGORITHM = eINSTANCE.getTextAlgorithm();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_ALGORITHM__TEXT = eINSTANCE.getTextAlgorithm_Text();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionImpl <em>Text Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTextFunction()
		 * @generated
		 */
		EClass TEXT_FUNCTION = eINSTANCE.getTextFunction();

		/**
		 * The meta object literal for the '<em><b>Input Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXT_FUNCTION__INPUT_PARAMETERS = eINSTANCE.getTextFunction_InputParameters();

		/**
		 * The meta object literal for the '<em><b>Output Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXT_FUNCTION__OUTPUT_PARAMETERS = eINSTANCE.getTextFunction_OutputParameters();

		/**
		 * The meta object literal for the '<em><b>In Out Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXT_FUNCTION__IN_OUT_PARAMETERS = eINSTANCE.getTextFunction_InOutParameters();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXT_FUNCTION__RETURN_TYPE = eINSTANCE.getTextFunction_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Varargs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_FUNCTION__VARARGS = eINSTANCE.getTextFunction_Varargs();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_FUNCTION__TEXT = eINSTANCE.getTextFunction_Text();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionBodyImpl <em>Text Function Body</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TextFunctionBodyImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTextFunctionBody()
		 * @generated
		 */
		EClass TEXT_FUNCTION_BODY = eINSTANCE.getTextFunctionBody();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_FUNCTION_BODY__TEXT = eINSTANCE.getTextFunctionBody_Text();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TextMethodImpl <em>Text Method</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TextMethodImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTextMethod()
		 * @generated
		 */
		EClass TEXT_METHOD = eINSTANCE.getTextMethod();

		/**
		 * The meta object literal for the '<em><b>Input Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXT_METHOD__INPUT_PARAMETERS = eINSTANCE.getTextMethod_InputParameters();

		/**
		 * The meta object literal for the '<em><b>Output Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXT_METHOD__OUTPUT_PARAMETERS = eINSTANCE.getTextMethod_OutputParameters();

		/**
		 * The meta object literal for the '<em><b>In Out Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXT_METHOD__IN_OUT_PARAMETERS = eINSTANCE.getTextMethod_InOutParameters();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXT_METHOD__RETURN_TYPE = eINSTANCE.getTextMethod_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Varargs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_METHOD__VARARGS = eINSTANCE.getTextMethod_Varargs();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_METHOD__TEXT = eINSTANCE.getTextMethod_Text();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TypedConfigureableObjectImpl <em>Typed Configureable Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TypedConfigureableObjectImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypedConfigureableObject()
		 * @generated
		 */
		EClass TYPED_CONFIGUREABLE_OBJECT = eINSTANCE.getTypedConfigureableObject();

		/**
		 * The meta object literal for the '<em><b>Type Entry</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPED_CONFIGUREABLE_OBJECT__TYPE_ENTRY = eINSTANCE.getTypedConfigureableObject_TypeEntry();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.TypedSubAppImpl <em>Typed Sub App</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.TypedSubAppImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypedSubApp()
		 * @generated
		 */
		EClass TYPED_SUB_APP = eINSTANCE.getTypedSubApp();

		/**
		 * The meta object literal for the '<em><b>Sub App Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPED_SUB_APP__SUB_APP_NETWORK = eINSTANCE.getTypedSubApp_SubAppNetwork();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.UntypedSubAppImpl <em>Untyped Sub App</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.UntypedSubAppImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getUntypedSubApp()
		 * @generated
		 */
		EClass UNTYPED_SUB_APP = eINSTANCE.getUntypedSubApp();

		/**
		 * The meta object literal for the '<em><b>Sub App Network</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNTYPED_SUB_APP__SUB_APP_NETWORK = eINSTANCE.getUntypedSubApp_SubAppNetwork();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.ValueImpl <em>Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.ValueImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getValue()
		 * @generated
		 */
		EClass VALUE = eINSTANCE.getValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VALUE__VALUE = eINSTANCE.getValue_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl <em>Var Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.VarDeclarationImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getVarDeclaration()
		 * @generated
		 */
		EClass VAR_DECLARATION = eINSTANCE.getVarDeclaration();

		/**
		 * The meta object literal for the '<em><b>Array Size</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR_DECLARATION__ARRAY_SIZE = eINSTANCE.getVarDeclaration_ArraySize();

		/**
		 * The meta object literal for the '<em><b>Withs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR_DECLARATION__WITHS = eINSTANCE.getVarDeclaration_Withs();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VAR_DECLARATION__VALUE = eINSTANCE.getVarDeclaration_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.VersionInfoImpl <em>Version Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.VersionInfoImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getVersionInfo()
		 * @generated
		 */
		EClass VERSION_INFO = eINSTANCE.getVersionInfo();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__AUTHOR = eINSTANCE.getVersionInfo_Author();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__DATE = eINSTANCE.getVersionInfo_Date();

		/**
		 * The meta object literal for the '<em><b>Organization</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__ORGANIZATION = eINSTANCE.getVersionInfo_Organization();

		/**
		 * The meta object literal for the '<em><b>Remarks</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__REMARKS = eINSTANCE.getVersionInfo_Remarks();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VERSION_INFO__VERSION = eINSTANCE.getVersionInfo_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.impl.WithImpl <em>With</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.WithImpl
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getWith()
		 * @generated
		 */
		EClass WITH = eINSTANCE.getWith();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WITH__VARIABLES = eINSTANCE.getWith_Variables();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.libraryElement.Language <em>Language</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.libraryElement.Language
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getLanguage()
		 * @generated
		 */
		EEnum LANGUAGE = eINSTANCE.getLanguage();

		/**
		 * The meta object literal for the '<em>Command Stack</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gef.commands.CommandStack
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getCommandStack()
		 * @generated
		 */
		EDataType COMMAND_STACK = eINSTANCE.getCommandStack();

		/**
		 * The meta object literal for the '<em>IFile</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.core.resources.IFile
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIFile()
		 * @generated
		 */
		EDataType IFILE = eINSTANCE.getIFile();

		/**
		 * The meta object literal for the '<em>Interface Element Stream</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.stream.Stream
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getInterfaceElementStream()
		 * @generated
		 */
		EDataType INTERFACE_ELEMENT_STREAM = eINSTANCE.getInterfaceElementStream();

		/**
		 * The meta object literal for the '<em>IProject</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.core.resources.IProject
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getIProject()
		 * @generated
		 */
		EDataType IPROJECT = eINSTANCE.getIProject();

		/**
		 * The meta object literal for the '<em>Named Element Stream</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.stream.Stream
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getNamedElementStream()
		 * @generated
		 */
		EDataType NAMED_ELEMENT_STREAM = eINSTANCE.getNamedElementStream();

		/**
		 * The meta object literal for the '<em>Point</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.draw2d.geometry.Point
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getPoint()
		 * @generated
		 */
		EDataType POINT = eINSTANCE.getPoint();

		/**
		 * The meta object literal for the '<em>Type Entry</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.typelibrary.TypeEntry
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypeEntry()
		 * @generated
		 */
		EDataType TYPE_ENTRY = eINSTANCE.getTypeEntry();

		/**
		 * The meta object literal for the '<em>Type Library</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getTypeLibrary()
		 * @generated
		 */
		EDataType TYPE_LIBRARY = eINSTANCE.getTypeLibrary();

		/**
		 * The meta object literal for the '<em>Var Decl List</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.List
		 * @see org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementPackageImpl#getVarDeclList()
		 * @generated
		 */
		EDataType VAR_DECL_LIST = eINSTANCE.getVarDeclList();

	}

} //LibraryElementPackage
