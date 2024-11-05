/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH,
 * 				 2018 Johannes Kepler University
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
package org.eclipse.fordiac.ide.model.monitoring.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringEvent;
import org.eclipse.fordiac.ide.model.monitoring.AdapterMonitoringVarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.AdapterPortElement;
import org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringAdapterElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement;
import org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class MonitoringFactoryImpl extends EFactoryImpl implements MonitoringFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static MonitoringFactory init() {
		try {
			final MonitoringFactory theMonitoringFactory = (MonitoringFactory) EPackage.Registry.INSTANCE
					.getEFactory(MonitoringPackage.eNS_URI);
			if (theMonitoringFactory != null) {
				return theMonitoringFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MonitoringFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public MonitoringFactoryImpl() {
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(final EClass eClass) {
		return switch (eClass.getClassifierID()) {
		case MonitoringPackage.MONITORING_ELEMENT -> createMonitoringElement();
		case MonitoringPackage.MONITORING_ADAPTER_ELEMENT -> createMonitoringAdapterElement();
		case MonitoringPackage.ADAPTER_PORT_ELEMENT -> createAdapterPortElement();
		case MonitoringPackage.ADAPTER_MONITORING_EVENT -> createAdapterMonitoringEvent();
		case MonitoringPackage.ADAPTER_MONITORING_VAR_DECLARATION -> createAdapterMonitoringVarDeclaration();
		case MonitoringPackage.SUB_APP_PORT_ELEMENT -> createSubAppPortElement();
		case MonitoringPackage.SUBAPP_MONITORING_ELEMENT -> createSubappMonitoringElement();
		case MonitoringPackage.INTERNAL_VAR_INSTANCE -> createInternalVarInstance();
		default -> throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		};
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public MonitoringElement createMonitoringElement() {
		return new MonitoringElementImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public MonitoringAdapterElement createMonitoringAdapterElement() {
		return new MonitoringAdapterElementImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AdapterPortElement createAdapterPortElement() {
		return new AdapterPortElementImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AdapterMonitoringEvent createAdapterMonitoringEvent() {
		return new AdapterMonitoringEventImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AdapterMonitoringVarDeclaration createAdapterMonitoringVarDeclaration() {
		return new AdapterMonitoringVarDeclarationImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SubAppPortElement createSubAppPortElement() {
		return new SubAppPortElementImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SubappMonitoringElement createSubappMonitoringElement() {
		return new SubappMonitoringElementImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public InternalVarInstance createInternalVarInstance() {
		return new InternalVarInstanceImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public MonitoringPackage getMonitoringPackage() {
		return (MonitoringPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MonitoringPackage getPackage() {
		return MonitoringPackage.eINSTANCE;
	}

} // MonitoringFactoryImpl
