/*******************************************************************************
 * Copyright (c) 2012, 2013 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.monCom;

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
 * @see org.eclipse.fordiac.ide.monitoring.monCom.MonComFactory
 * @model kind="package"
 * @generated
 */
public interface MonComPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "monCom";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.monitoring";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "monCom";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MonComPackage eINSTANCE = org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.ResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.ResourceImpl
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getResource()
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
	 * The feature id for the '<em><b>Fbs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__FBS = 1;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.FBImpl <em>FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.FBImpl
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getFB()
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
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB__PORTS = 1;

	/**
	 * The number of structural features of the '<em>FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FB_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.PortImpl
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getPort()
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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.DataImpl <em>Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.DataImpl
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getData()
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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.ResponseImpl <em>Response</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.ResponseImpl
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getResponse()
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
	 * The number of structural features of the '<em>Response</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESPONSE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.WatchesImpl <em>Watches</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.WatchesImpl
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getWatches()
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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.monitoring.monCom.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.monitoring.monCom.Resource#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Resource#getName()
	 * @see #getResource()
	 * @generated
	 */
	EAttribute getResource_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.monitoring.monCom.Resource#getFbs <em>Fbs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Fbs</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Resource#getFbs()
	 * @see #getResource()
	 * @generated
	 */
	EReference getResource_Fbs();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.monitoring.monCom.FB <em>FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>FB</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.FB
	 * @generated
	 */
	EClass getFB();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.monitoring.monCom.FB#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.FB#getName()
	 * @see #getFB()
	 * @generated
	 */
	EAttribute getFB_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.monitoring.monCom.FB#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.FB#getPorts()
	 * @see #getFB()
	 * @generated
	 */
	EReference getFB_Ports();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.monitoring.monCom.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.monitoring.monCom.Port#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Port#getName()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.monitoring.monCom.Port#getDataValues <em>Data Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Values</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Port#getDataValues()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_DataValues();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.monitoring.monCom.Data <em>Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Data
	 * @generated
	 */
	EClass getData();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.monitoring.monCom.Data#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Data#getValue()
	 * @see #getData()
	 * @generated
	 */
	EAttribute getData_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.monitoring.monCom.Data#getTime <em>Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Data#getTime()
	 * @see #getData()
	 * @generated
	 */
	EAttribute getData_Time();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.monitoring.monCom.Data#getForced <em>Forced</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Forced</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Data#getForced()
	 * @see #getData()
	 * @generated
	 */
	EAttribute getData_Forced();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.monitoring.monCom.Response <em>Response</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Response</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Response
	 * @generated
	 */
	EClass getResponse();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.monitoring.monCom.Response#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Response#getID()
	 * @see #getResponse()
	 * @generated
	 */
	EAttribute getResponse_ID();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.monitoring.monCom.Response#getWatches <em>Watches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Watches</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Response#getWatches()
	 * @see #getResponse()
	 * @generated
	 */
	EReference getResponse_Watches();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.monitoring.monCom.Response#getReason <em>Reason</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reason</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Response#getReason()
	 * @see #getResponse()
	 * @generated
	 */
	EAttribute getResponse_Reason();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.monitoring.monCom.Watches <em>Watches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Watches</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Watches
	 * @generated
	 */
	EClass getWatches();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.fordiac.ide.monitoring.monCom.Watches#getResources <em>Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Resources</em>'.
	 * @see org.eclipse.fordiac.ide.monitoring.monCom.Watches#getResources()
	 * @see #getWatches()
	 * @generated
	 */
	EReference getWatches_Resources();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MonComFactory getMonComFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.ResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.ResourceImpl
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getResource()
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
		 * The meta object literal for the '<em><b>Fbs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE__FBS = eINSTANCE.getResource_Fbs();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.FBImpl <em>FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.FBImpl
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getFB()
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
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FB__PORTS = eINSTANCE.getFB_Ports();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.PortImpl
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getPort()
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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.DataImpl <em>Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.DataImpl
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getData()
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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.ResponseImpl <em>Response</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.ResponseImpl
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getResponse()
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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.monitoring.monCom.impl.WatchesImpl <em>Watches</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.WatchesImpl
		 * @see org.eclipse.fordiac.ide.monitoring.monCom.impl.MonComPackageImpl#getWatches()
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

	}

} //MonComPackage
