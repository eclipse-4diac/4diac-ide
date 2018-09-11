/**
 * ******************************************************************************
 *  * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *  * 
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *  *     - initial API and implementation and/or initial documentation
 *  ******************************************************************************
 * 
 */
package org.eclipse.fordiac.ide.deployment.monitoringbase;

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
 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseFactory
 * @model kind="package"
 * @generated
 */
public interface MonitoringBasePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "monitoringbase";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.depolyment.monitoringbase";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "monitoringbase";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MonitoringBasePackage eINSTANCE = org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBasePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator <em>IEdit Part Creator</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBasePackageImpl#getIEditPartCreator()
	 * @generated
	 */
	int IEDIT_PART_CREATOR = 2;

	/**
	 * The number of structural features of the '<em>IEdit Part Creator</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEDIT_PART_CREATOR_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBaseElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBaseElementImpl
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBasePackageImpl#getMonitoringBaseElement()
	 * @generated
	 */
	int MONITORING_BASE_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORING_BASE_ELEMENT__PORT = IEDIT_PART_CREATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Offline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORING_BASE_ELEMENT__OFFLINE = IEDIT_PART_CREATOR_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORING_BASE_ELEMENT_FEATURE_COUNT = IEDIT_PART_CREATOR_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.impl.PortElementImpl <em>Port Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.impl.PortElementImpl
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBasePackageImpl#getPortElement()
	 * @generated
	 */
	int PORT_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Fb</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_ELEMENT__FB = 0;

	/**
	 * The feature id for the '<em><b>Interface Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_ELEMENT__INTERFACE_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_ELEMENT__RESOURCE = 2;

	/**
	 * The feature id for the '<em><b>Hierarchy</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_ELEMENT__HIERARCHY = 3;

	/**
	 * The number of structural features of the '<em>Port Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_ELEMENT_FEATURE_COUNT = 4;

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement
	 * @generated
	 */
	EClass getMonitoringBaseElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#getPort <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Port</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#getPort()
	 * @see #getMonitoringBaseElement()
	 * @generated
	 */
	EReference getMonitoringBaseElement_Port();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#isOffline <em>Offline</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offline</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement#isOffline()
	 * @see #getMonitoringBaseElement()
	 * @generated
	 */
	EAttribute getMonitoringBaseElement_Offline();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement <em>Port Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port Element</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement
	 * @generated
	 */
	EClass getPortElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getFb <em>Fb</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Fb</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getFb()
	 * @see #getPortElement()
	 * @generated
	 */
	EReference getPortElement_Fb();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getInterfaceElement <em>Interface Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Interface Element</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getInterfaceElement()
	 * @see #getPortElement()
	 * @generated
	 */
	EReference getPortElement_InterfaceElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Resource</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getResource()
	 * @see #getPortElement()
	 * @generated
	 */
	EReference getPortElement_Resource();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getHierarchy <em>Hierarchy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Hierarchy</em>'.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement#getHierarchy()
	 * @see #getPortElement()
	 * @generated
	 */
	EAttribute getPortElement_Hierarchy();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator <em>IEdit Part Creator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IEdit Part Creator</em>'.
	 * @see org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator
	 * @model instanceClass="org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator"
	 * @generated
	 */
	EClass getIEditPartCreator();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MonitoringBaseFactory getMonitoringBaseFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBaseElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBaseElementImpl
		 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBasePackageImpl#getMonitoringBaseElement()
		 * @generated
		 */
		EClass MONITORING_BASE_ELEMENT = eINSTANCE.getMonitoringBaseElement();

		/**
		 * The meta object literal for the '<em><b>Port</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MONITORING_BASE_ELEMENT__PORT = eINSTANCE.getMonitoringBaseElement_Port();

		/**
		 * The meta object literal for the '<em><b>Offline</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MONITORING_BASE_ELEMENT__OFFLINE = eINSTANCE.getMonitoringBaseElement_Offline();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.deployment.monitoringbase.impl.PortElementImpl <em>Port Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.impl.PortElementImpl
		 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBasePackageImpl#getPortElement()
		 * @generated
		 */
		EClass PORT_ELEMENT = eINSTANCE.getPortElement();

		/**
		 * The meta object literal for the '<em><b>Fb</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_ELEMENT__FB = eINSTANCE.getPortElement_Fb();

		/**
		 * The meta object literal for the '<em><b>Interface Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_ELEMENT__INTERFACE_ELEMENT = eINSTANCE.getPortElement_InterfaceElement();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT_ELEMENT__RESOURCE = eINSTANCE.getPortElement_Resource();

		/**
		 * The meta object literal for the '<em><b>Hierarchy</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT_ELEMENT__HIERARCHY = eINSTANCE.getPortElement_Hierarchy();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator <em>IEdit Part Creator</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator
		 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.impl.MonitoringBasePackageImpl#getIEditPartCreator()
		 * @generated
		 */
		EClass IEDIT_PART_CREATOR = eINSTANCE.getIEditPartCreator();

	}

} //MonitoringBasePackage
