/**
 * ******************************************************************************
 * * Copyright (c) 2012, 2013, 2018 Profactor GmbH, fortiss GmbH, Johannes Kepler University
 * * 
 * * All rights reserved. This program and the accompanying materials
 * * are made available under the terms of the Eclipse Public License v1.0
 * * which accompanies this distribution, and is available at
 * * http://www.eclipse.org/legal/epl-v10.html
 * *
 * * Contributors:
 * *   Gerhard Ebenhofer, Alois Zoitl
 * *     - initial API and implementation and/or initial documentation
 * *   Alois Zoitl - moved to deployment and reworked it to a device response model
 * ******************************************************************************
 * 
 */
package org.eclipse.fordiac.ide.deployment.devResponse;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory
 * @model kind="package"
 * @generated
 */
public interface DevResponsePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "devResponse";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.deployment";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "devResponse";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DevResponsePackage eINSTANCE = org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.ResourceImpl
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Fbs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__FBS = 2;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.FBImpl <em>FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.FBImpl
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getFB()
	 * @generated
	 */
	int FB = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__PORTS = 2;

	/**
	 * The number of structural features of the '<em>FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.PortImpl
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getPort()
	 * @generated
	 */
	int PORT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Data Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__DATA_VALUES = 1;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.DataImpl <em>Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DataImpl
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getData()
	 * @generated
	 */
	int DATA = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA__TIME = 1;

	/**
	 * The feature id for the '<em><b>Forced</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA__FORCED = 2;

	/**
	 * The number of structural features of the '<em>Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl <em>Response</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getResponse()
	 * @generated
	 */
	int RESPONSE = 4;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE__ID = 0;

	/**
	 * The feature id for the '<em><b>Watches</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE__WATCHES = 1;

	/**
	 * The feature id for the '<em><b>Reason</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE__REASON = 2;

	/**
	 * The feature id for the '<em><b>Fblist</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE__FBLIST = 3;

	/**
	 * The feature id for the '<em><b>Endpointlist</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE__ENDPOINTLIST = 4;

	/**
	 * The number of structural features of the '<em>Response</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.WatchesImpl <em>Watches</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.WatchesImpl
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getWatches()
	 * @generated
	 */
	int WATCHES = 5;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WATCHES__RESOURCES = 0;

	/**
	 * The number of structural features of the '<em>Watches</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WATCHES_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.FBListImpl <em>FB List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.FBListImpl
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getFBList()
	 * @generated
	 */
	int FB_LIST = 6;

	/**
	 * The feature id for the '<em><b>Fbs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_LIST__FBS = 0;

	/**
	 * The number of structural features of the '<em>FB List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_LIST_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.EndpointListImpl <em>Endpoint List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.EndpointListImpl
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getEndpointList()
	 * @generated
	 */
	int ENDPOINT_LIST = 7;

	/**
	 * The feature id for the '<em><b>Connection</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_LIST__CONNECTION = 0;

	/**
	 * The number of structural features of the '<em>Endpoint List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENDPOINT_LIST_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.ConnectionImpl
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 8;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__DESTINATION = 1;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.devResponse.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.Resource#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Resource#getName()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.Resource#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Resource#getType()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.deployment.devResponse.Resource#getFbs <em>Fbs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fbs</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Resource#getFbs()
	 * @see #getResource()
	 * @generated
	 */
	EReference getResource_Fbs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.devResponse.FB <em>FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.FB
	 * @generated
	 */
	EClass getFB();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.FB#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.FB#getName()
	 * @see #getFB()
	 * @generated
	 */
	EAttribute getFB_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.FB#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.FB#getType()
	 * @see #getFB()
	 * @generated
	 */
	EAttribute getFB_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.deployment.devResponse.FB#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.FB#getPorts()
	 * @see #getFB()
	 * @generated
	 */
	EReference getFB_Ports();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.devResponse.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.Port#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Port#getName()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.deployment.devResponse.Port#getDataValues <em>Data Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Values</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Port#getDataValues()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_DataValues();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.devResponse.Data <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Data
	 * @generated
	 */
	EClass getData();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.Data#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Data#getValue()
	 * @see #getData()
	 * @generated
	 */
	EAttribute getData_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.Data#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Data#getTime()
	 * @see #getData()
	 * @generated
	 */
	EAttribute getData_Time();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.Data#getForced <em>Forced</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Forced</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Data#getForced()
	 * @see #getData()
	 * @generated
	 */
	EAttribute getData_Forced();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response <em>Response</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Response</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Response
	 * @generated
	 */
	EClass getResponse();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Response#getID()
	 * @see #getResponse()
	 * @generated
	 */
	EAttribute getResponse_ID();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getWatches <em>Watches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Watches</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Response#getWatches()
	 * @see #getResponse()
	 * @generated
	 */
	EReference getResponse_Watches();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getReason <em>Reason</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reason</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Response#getReason()
	 * @see #getResponse()
	 * @generated
	 */
	EAttribute getResponse_Reason();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getFblist <em>Fblist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Fblist</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Response#getFblist()
	 * @see #getResponse()
	 * @generated
	 */
	EReference getResponse_Fblist();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.deployment.devResponse.Response#getEndpointlist <em>Endpointlist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Endpointlist</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Response#getEndpointlist()
	 * @see #getResponse()
	 * @generated
	 */
	EReference getResponse_Endpointlist();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.devResponse.Watches <em>Watches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Watches</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Watches
	 * @generated
	 */
	EClass getWatches();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.deployment.devResponse.Watches#getResources <em>Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Resources</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Watches#getResources()
	 * @see #getWatches()
	 * @generated
	 */
	EReference getWatches_Resources();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.devResponse.FBList <em>FB List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB List</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.FBList
	 * @generated
	 */
	EClass getFBList();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.deployment.devResponse.FBList#getFbs <em>Fbs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Fbs</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.FBList#getFbs()
	 * @see #getFBList()
	 * @generated
	 */
	EReference getFBList_Fbs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.devResponse.EndpointList <em>Endpoint List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Endpoint List</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.EndpointList
	 * @generated
	 */
	EClass getEndpointList();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.deployment.devResponse.EndpointList#getConnection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Connection</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.EndpointList#getConnection()
	 * @see #getEndpointList()
	 * @generated
	 */
	EReference getEndpointList_Connection();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.devResponse.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.Connection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Connection#getSource()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_Source();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.devResponse.Connection#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Destination</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.Connection#getDestination()
	 * @see #getConnection()
	 * @generated
	 */
	EAttribute getConnection_Destination();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DevResponseFactory getDevResponseFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.ResourceImpl
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getResource()
		 * @generated
		 */
		EClass RESOURCE = eINSTANCE.getResource();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE__NAME = eINSTANCE.getResource_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE__TYPE = eINSTANCE.getResource_Type();

		/**
		 * The meta object literal for the '<em><b>Fbs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE__FBS = eINSTANCE.getResource_Fbs();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.FBImpl <em>FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.FBImpl
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getFB()
		 * @generated
		 */
		EClass FB = eINSTANCE.getFB();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FB__NAME = eINSTANCE.getFB_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FB__TYPE = eINSTANCE.getFB_Type();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB__PORTS = eINSTANCE.getFB_Ports();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.PortImpl
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__NAME = eINSTANCE.getPort_Name();

		/**
		 * The meta object literal for the '<em><b>Data Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__DATA_VALUES = eINSTANCE.getPort_DataValues();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.DataImpl <em>Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DataImpl
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getData()
		 * @generated
		 */
		EClass DATA = eINSTANCE.getData();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA__VALUE = eINSTANCE.getData_Value();

		/**
		 * The meta object literal for the '<em><b>Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA__TIME = eINSTANCE.getData_Time();

		/**
		 * The meta object literal for the '<em><b>Forced</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA__FORCED = eINSTANCE.getData_Forced();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl <em>Response</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.ResponseImpl
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getResponse()
		 * @generated
		 */
		EClass RESPONSE = eINSTANCE.getResponse();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESPONSE__ID = eINSTANCE.getResponse_ID();

		/**
		 * The meta object literal for the '<em><b>Watches</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESPONSE__WATCHES = eINSTANCE.getResponse_Watches();

		/**
		 * The meta object literal for the '<em><b>Reason</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESPONSE__REASON = eINSTANCE.getResponse_Reason();

		/**
		 * The meta object literal for the '<em><b>Fblist</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESPONSE__FBLIST = eINSTANCE.getResponse_Fblist();

		/**
		 * The meta object literal for the '<em><b>Endpointlist</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESPONSE__ENDPOINTLIST = eINSTANCE.getResponse_Endpointlist();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.WatchesImpl <em>Watches</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.WatchesImpl
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getWatches()
		 * @generated
		 */
		EClass WATCHES = eINSTANCE.getWatches();

		/**
		 * The meta object literal for the '<em><b>Resources</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WATCHES__RESOURCES = eINSTANCE.getWatches_Resources();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.FBListImpl <em>FB List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.FBListImpl
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getFBList()
		 * @generated
		 */
		EClass FB_LIST = eINSTANCE.getFBList();

		/**
		 * The meta object literal for the '<em><b>Fbs</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB_LIST__FBS = eINSTANCE.getFBList_Fbs();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.EndpointListImpl <em>Endpoint List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.EndpointListImpl
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getEndpointList()
		 * @generated
		 */
		EClass ENDPOINT_LIST = eINSTANCE.getEndpointList();

		/**
		 * The meta object literal for the '<em><b>Connection</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENDPOINT_LIST__CONNECTION = eINSTANCE.getEndpointList_Connection();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.devResponse.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.ConnectionImpl
		 * @see org.eclipse.fordiac.ide.deployment.devResponse.impl.DevResponsePackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION__SOURCE = eINSTANCE.getConnection_Source();

		/**
		 * The meta object literal for the '<em><b>Destination</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONNECTION__DESTINATION = eINSTANCE.getConnection_Destination();

	}

} //DevResponsePackage
