/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.monitoring;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

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
 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory
 * @model kind="package"
 * @generated
 */
public interface MonitoringPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "monitoring"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.monitoring"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "monitoring"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	MonitoringPackage eINSTANCE = org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringElementImpl
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getMonitoringElement()
	 * @generated
	 */
	int MONITORING_ELEMENT = 0;

	/**
	 * The meta object id for the
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringAdapterElementImpl
	 * <em>Adapter Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringAdapterElementImpl
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getMonitoringAdapterElement()
	 * @generated
	 */
	int MONITORING_ADAPTER_ELEMENT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator <em>IEdit Part Creator</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getIEditPartCreator()
	 * @generated
	 */
	int IEDIT_PART_CREATOR = 5;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORING_ELEMENT__PORT = MonitoringBasePackage.MONITORING_BASE_ELEMENT__PORT;

	/**
	 * The feature id for the '<em><b>Offline</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MONITORING_ELEMENT__OFFLINE = MonitoringBasePackage.MONITORING_BASE_ELEMENT__OFFLINE;

	/**
	 * The feature id for the '<em><b>Force</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORING_ELEMENT__FORCE = MonitoringBasePackage.MONITORING_BASE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Force Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MONITORING_ELEMENT__FORCE_VALUE = MonitoringBasePackage.MONITORING_BASE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Current Value</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MONITORING_ELEMENT__CURRENT_VALUE = MonitoringBasePackage.MONITORING_BASE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Sec</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORING_ELEMENT__SEC = MonitoringBasePackage.MONITORING_BASE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Usec</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORING_ELEMENT__USEC = MonitoringBasePackage.MONITORING_BASE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Element</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MONITORING_ELEMENT_FEATURE_COUNT = MonitoringBasePackage.MONITORING_BASE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORING_ADAPTER_ELEMENT__PORT = MonitoringBasePackage.MONITORING_BASE_ELEMENT__PORT;

	/**
	 * The feature id for the '<em><b>Offline</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MONITORING_ADAPTER_ELEMENT__OFFLINE = MonitoringBasePackage.MONITORING_BASE_ELEMENT__OFFLINE;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORING_ADAPTER_ELEMENT__ELEMENTS = MonitoringBasePackage.MONITORING_BASE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Monitored Adapter FB</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int MONITORING_ADAPTER_ELEMENT__MONITORED_ADAPTER_FB = MonitoringBasePackage.MONITORING_BASE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Adapter Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONITORING_ADAPTER_ELEMENT_FEATURE_COUNT = MonitoringBasePackage.MONITORING_BASE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterPortElementImpl <em>Adapter Port Element</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.AdapterPortElementImpl
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getAdapterPortElement()
	 * @generated
	 */
	int ADAPTER_PORT_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Fb</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_PORT_ELEMENT__FB = MonitoringBasePackage.PORT_ELEMENT__FB;

	/**
	 * The feature id for the '<em><b>Interface Element</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADAPTER_PORT_ELEMENT__INTERFACE_ELEMENT = MonitoringBasePackage.PORT_ELEMENT__INTERFACE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADAPTER_PORT_ELEMENT__RESOURCE = MonitoringBasePackage.PORT_ELEMENT__RESOURCE;

	/**
	 * The feature id for the '<em><b>Hierarchy</b></em>' attribute list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADAPTER_PORT_ELEMENT__HIERARCHY = MonitoringBasePackage.PORT_ELEMENT__HIERARCHY;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_PORT_ELEMENT__PORTS = MonitoringBasePackage.PORT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Adapter Port Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_PORT_ELEMENT_FEATURE_COUNT = MonitoringBasePackage.PORT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IEdit Part Creator</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEDIT_PART_CREATOR_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl <em>Adapter Monitoring Event</em>}' class.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getAdapterMonitoringEvent()
	 * @generated
	 */
	int ADAPTER_MONITORING_EVENT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_EVENT__NAME = IEDIT_PART_CREATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_EVENT__COMMENT = IEDIT_PART_CREATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_EVENT__ATTRIBUTES = IEDIT_PART_CREATOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_EVENT__IS_INPUT = IEDIT_PART_CREATOR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_EVENT__INPUT_CONNECTIONS = IEDIT_PART_CREATOR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_EVENT__OUTPUT_CONNECTIONS = IEDIT_PART_CREATOR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_EVENT__TYPE = IEDIT_PART_CREATOR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>With</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_EVENT__WITH = IEDIT_PART_CREATOR_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Adapter Monitoring Event</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_EVENT_FEATURE_COUNT = IEDIT_PART_CREATOR_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl <em>Adapter Monitoring Var Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getAdapterMonitoringVarDeclaration()
	 * @generated
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION__NAME = IEDIT_PART_CREATOR_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION__COMMENT = IEDIT_PART_CREATOR_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION__ATTRIBUTES = IEDIT_PART_CREATOR_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION__IS_INPUT = IEDIT_PART_CREATOR_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION__INPUT_CONNECTIONS = IEDIT_PART_CREATOR_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION__OUTPUT_CONNECTIONS = IEDIT_PART_CREATOR_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION__TYPE = IEDIT_PART_CREATOR_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Array Size</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION__ARRAY_SIZE = IEDIT_PART_CREATOR_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Withs</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION__WITHS = IEDIT_PART_CREATOR_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION__VALUE = IEDIT_PART_CREATOR_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>Adapter Monitoring Var Declaration</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADAPTER_MONITORING_VAR_DECLARATION_FEATURE_COUNT = IEDIT_PART_CREATOR_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.SubAppPortElementImpl <em>Sub App Port Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.SubAppPortElementImpl
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getSubAppPortElement()
	 * @generated
	 */
	int SUB_APP_PORT_ELEMENT = 6;

	/**
	 * The feature id for the '<em><b>Fb</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_PORT_ELEMENT__FB = MonitoringBasePackage.PORT_ELEMENT__FB;

	/**
	 * The feature id for the '<em><b>Interface Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_PORT_ELEMENT__INTERFACE_ELEMENT = MonitoringBasePackage.PORT_ELEMENT__INTERFACE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_PORT_ELEMENT__RESOURCE = MonitoringBasePackage.PORT_ELEMENT__RESOURCE;

	/**
	 * The feature id for the '<em><b>Hierarchy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_PORT_ELEMENT__HIERARCHY = MonitoringBasePackage.PORT_ELEMENT__HIERARCHY;

	/**
	 * The feature id for the '<em><b>Anchor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_PORT_ELEMENT__ANCHOR = MonitoringBasePackage.PORT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Sub App Port Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUB_APP_PORT_ELEMENT_FEATURE_COUNT = MonitoringBasePackage.PORT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.SubappMonitoringElementImpl <em>Subapp Monitoring Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.SubappMonitoringElementImpl
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getSubappMonitoringElement()
	 * @generated
	 */
	int SUBAPP_MONITORING_ELEMENT = 7;

	/**
	 * The feature id for the '<em><b>Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_MONITORING_ELEMENT__PORT = MONITORING_ELEMENT__PORT;

	/**
	 * The feature id for the '<em><b>Offline</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_MONITORING_ELEMENT__OFFLINE = MONITORING_ELEMENT__OFFLINE;

	/**
	 * The feature id for the '<em><b>Force</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_MONITORING_ELEMENT__FORCE = MONITORING_ELEMENT__FORCE;

	/**
	 * The feature id for the '<em><b>Force Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_MONITORING_ELEMENT__FORCE_VALUE = MONITORING_ELEMENT__FORCE_VALUE;

	/**
	 * The feature id for the '<em><b>Current Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_MONITORING_ELEMENT__CURRENT_VALUE = MONITORING_ELEMENT__CURRENT_VALUE;

	/**
	 * The feature id for the '<em><b>Sec</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_MONITORING_ELEMENT__SEC = MONITORING_ELEMENT__SEC;

	/**
	 * The feature id for the '<em><b>Usec</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_MONITORING_ELEMENT__USEC = MONITORING_ELEMENT__USEC;

	/**
	 * The feature id for the '<em><b>Anchor</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_MONITORING_ELEMENT__ANCHOR = MONITORING_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Subapp Monitoring Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_MONITORING_ELEMENT_FEATURE_COUNT = MONITORING_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.InternalVarInstanceImpl <em>Internal Var Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.InternalVarInstanceImpl
	 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getInternalVarInstance()
	 * @generated
	 */
	int INTERNAL_VAR_INSTANCE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__NAME = LibraryElementPackage.VAR_DECLARATION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__COMMENT = LibraryElementPackage.VAR_DECLARATION__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__ATTRIBUTES = LibraryElementPackage.VAR_DECLARATION__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Is Input</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__IS_INPUT = LibraryElementPackage.VAR_DECLARATION__IS_INPUT;

	/**
	 * The feature id for the '<em><b>Input Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__INPUT_CONNECTIONS = LibraryElementPackage.VAR_DECLARATION__INPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Output Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__OUTPUT_CONNECTIONS = LibraryElementPackage.VAR_DECLARATION__OUTPUT_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__TYPE = LibraryElementPackage.VAR_DECLARATION__TYPE;

	/**
	 * The feature id for the '<em><b>Array Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__ARRAY_SIZE = LibraryElementPackage.VAR_DECLARATION__ARRAY_SIZE;

	/**
	 * The feature id for the '<em><b>Withs</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__WITHS = LibraryElementPackage.VAR_DECLARATION__WITHS;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__VALUE = LibraryElementPackage.VAR_DECLARATION__VALUE;

	/**
	 * The feature id for the '<em><b>Fb</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE__FB = LibraryElementPackage.VAR_DECLARATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Internal Var Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_VAR_INSTANCE_FEATURE_COUNT = LibraryElementPackage.VAR_DECLARATION_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement <em>Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringElement
	 * @generated
	 */
	EClass getMonitoringElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#isForce <em>Force</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Force</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#isForce()
	 * @see #getMonitoringElement()
	 * @generated
	 */
	EAttribute getMonitoringElement_Force();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getForceValue <em>Force Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Force Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getForceValue()
	 * @see #getMonitoringElement()
	 * @generated
	 */
	EAttribute getMonitoringElement_ForceValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getCurrentValue <em>Current Value</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Current Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getCurrentValue()
	 * @see #getMonitoringElement()
	 * @generated
	 */
	EAttribute getMonitoringElement_CurrentValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getSec <em>Sec</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sec</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getSec()
	 * @see #getMonitoringElement()
	 * @generated
	 */
	EAttribute getMonitoringElement_Sec();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getUsec <em>Usec</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Usec</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringElement#getUsec()
	 * @see #getMonitoringElement()
	 * @generated
	 */
	EAttribute getMonitoringElement_Usec();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement <em>Adapter Element</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>Adapter Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement
	 * @generated
	 */
	EClass getMonitoringAdapterElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement#getElements()
	 * @see #getMonitoringAdapterElement()
	 * @generated
	 */
	EReference getMonitoringAdapterElement_Elements();

	/**
	 * Returns the meta object for the reference
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement#getMonitoredAdapterFB
	 * <em>Monitored Adapter FB</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the meta object for the reference '<em>Monitored Adapter FB</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement#getMonitoredAdapterFB()
	 * @see #getMonitoringAdapterElement()
	 * @generated
	 */
	EReference getMonitoringAdapterElement_MonitoredAdapterFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator <em>IEdit Part Creator</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for class '<em>IEdit Part Creator</em>'.
	 * @see org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator
	 * @model instanceClass="org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator"
	 * @generated
	 */
	EClass getIEditPartCreator();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement <em>Sub App Port Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sub App Port Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement
	 * @generated
	 */
	EClass getSubAppPortElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement#getAnchor <em>Anchor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Anchor</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement#getAnchor()
	 * @see #getSubAppPortElement()
	 * @generated
	 */
	EReference getSubAppPortElement_Anchor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement <em>Subapp Monitoring Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subapp Monitoring Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement
	 * @generated
	 */
	EClass getSubappMonitoringElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement#getAnchor <em>Anchor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Anchor</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement#getAnchor()
	 * @see #getSubappMonitoringElement()
	 * @generated
	 */
	EReference getSubappMonitoringElement_Anchor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance <em>Internal Var Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Internal Var Instance</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance
	 * @generated
	 */
	EClass getInternalVarInstance();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance#getFb <em>Fb</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Fb</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance#getFb()
	 * @see #getInternalVarInstance()
	 * @generated
	 */
	EReference getInternalVarInstance_Fb();

	/**
	 * Returns the meta object for class
	 * '{@link org.eclipse.fordiac.ide.model.monitoring.AdapterPortElement
	 * <em>Adapter Port Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @return the meta object for class '<em>Adapter Port Element</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.AdapterPortElement
	 * @generated
	 */
	EClass getAdapterPortElement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.monitoring.AdapterPortElement#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.AdapterPortElement#getPorts()
	 * @see #getAdapterPortElement()
	 * @generated
	 */
	EReference getAdapterPortElement_Ports();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringEvent <em>Adapter Monitoring Event</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>Adapter Monitoring Event</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringEvent
	 * @generated
	 */
	EClass getAdapterMonitoringEvent();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringVarDeclaration <em>Adapter Monitoring Var Declaration</em>}'.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the meta object for class '<em>Adapter Monitoring Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringVarDeclaration
	 * @generated
	 */
	EClass getAdapterMonitoringVarDeclaration();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MonitoringFactory getMonitoringFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringElementImpl
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getMonitoringElement()
		 * @generated
		 */
		EClass MONITORING_ELEMENT = eINSTANCE.getMonitoringElement();

		/**
		 * The meta object literal for the '<em><b>Force</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MONITORING_ELEMENT__FORCE = eINSTANCE.getMonitoringElement_Force();

		/**
		 * The meta object literal for the '<em><b>Force Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MONITORING_ELEMENT__FORCE_VALUE = eINSTANCE.getMonitoringElement_ForceValue();

		/**
		 * The meta object literal for the '<em><b>Current Value</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MONITORING_ELEMENT__CURRENT_VALUE = eINSTANCE.getMonitoringElement_CurrentValue();

		/**
		 * The meta object literal for the '<em><b>Sec</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute MONITORING_ELEMENT__SEC = eINSTANCE.getMonitoringElement_Sec();

		/**
		 * The meta object literal for the '<em><b>Usec</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MONITORING_ELEMENT__USEC = eINSTANCE.getMonitoringElement_Usec();

		/**
		 * The meta object literal for the
		 * '{@link org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringAdapterElementImpl
		 * <em>Adapter Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 *
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringAdapterElementImpl
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getMonitoringAdapterElement()
		 * @generated
		 */
		EClass MONITORING_ADAPTER_ELEMENT = eINSTANCE.getMonitoringAdapterElement();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference MONITORING_ADAPTER_ELEMENT__ELEMENTS = eINSTANCE.getMonitoringAdapterElement_Elements();

		/**
		 * The meta object literal for the '<em><b>Monitored Adapter FB</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference MONITORING_ADAPTER_ELEMENT__MONITORED_ADAPTER_FB = eINSTANCE.getMonitoringAdapterElement_MonitoredAdapterFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator <em>IEdit Part Creator</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getIEditPartCreator()
		 * @generated
		 */
		EClass IEDIT_PART_CREATOR = eINSTANCE.getIEditPartCreator();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.SubAppPortElementImpl <em>Sub App Port Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.SubAppPortElementImpl
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getSubAppPortElement()
		 * @generated
		 */
		EClass SUB_APP_PORT_ELEMENT = eINSTANCE.getSubAppPortElement();

		/**
		 * The meta object literal for the '<em><b>Anchor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUB_APP_PORT_ELEMENT__ANCHOR = eINSTANCE.getSubAppPortElement_Anchor();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.SubappMonitoringElementImpl <em>Subapp Monitoring Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.SubappMonitoringElementImpl
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getSubappMonitoringElement()
		 * @generated
		 */
		EClass SUBAPP_MONITORING_ELEMENT = eINSTANCE.getSubappMonitoringElement();

		/**
		 * The meta object literal for the '<em><b>Anchor</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBAPP_MONITORING_ELEMENT__ANCHOR = eINSTANCE.getSubappMonitoringElement_Anchor();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.InternalVarInstanceImpl <em>Internal Var Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.InternalVarInstanceImpl
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getInternalVarInstance()
		 * @generated
		 */
		EClass INTERNAL_VAR_INSTANCE = eINSTANCE.getInternalVarInstance();

		/**
		 * The meta object literal for the '<em><b>Fb</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_VAR_INSTANCE__FB = eINSTANCE.getInternalVarInstance_Fb();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterPortElementImpl <em>Adapter Port Element</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.AdapterPortElementImpl
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getAdapterPortElement()
		 * @generated
		 */
		EClass ADAPTER_PORT_ELEMENT = eINSTANCE.getAdapterPortElement();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADAPTER_PORT_ELEMENT__PORTS = eINSTANCE.getAdapterPortElement_Ports();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl <em>Adapter Monitoring Event</em>}' class.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringEventImpl
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getAdapterMonitoringEvent()
		 * @generated
		 */
		EClass ADAPTER_MONITORING_EVENT = eINSTANCE.getAdapterMonitoringEvent();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl <em>Adapter Monitoring Var Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.AdapterMonitoringVarDeclarationImpl
		 * @see org.eclipse.fordiac.ide.model.monitoring.impl.MonitoringPackageImpl#getAdapterMonitoringVarDeclaration()
		 * @generated
		 */
		EClass ADAPTER_MONITORING_VAR_DECLARATION = eINSTANCE.getAdapterMonitoringVarDeclaration();

	}

} // MonitoringPackage
