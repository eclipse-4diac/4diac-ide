/**
 * ******************************************************************************
 * * Copyright (c) 2012, 2013, 2018 Profactor GmbH, fortiss GmbH, Johannes Kepler University
 * *
 * * This program and the accompanying materials are made available under the
 * * terms of the Eclipse Public License 2.0 which is available at
 * * http://www.eclipse.org/legal/epl-2.0.
 * *
 * * SPDX-License-Identifier: EPL-2.0
 * *
 * * Contributors:
 * *   Gerhard Ebenhofer, Alois Zoitl
 * *     - initial API and implementation and/or initial documentation
 * *   Alois Zoitl - moved to deployment and reworked it to a device response model
 * ******************************************************************************
 *
 */
package org.eclipse.fordiac.ide.deployment.devResponse.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.fordiac.ide.deployment.devResponse.Connection;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage;
import org.eclipse.fordiac.ide.deployment.devResponse.EndpointList;
import org.eclipse.fordiac.ide.deployment.devResponse.FB;
import org.eclipse.fordiac.ide.deployment.devResponse.FBList;
import org.eclipse.fordiac.ide.deployment.devResponse.FBType;
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Resource;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.devResponse.Watches;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class DevResponseFactoryImpl extends EFactoryImpl implements DevResponseFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static DevResponseFactory init() {
		try {
			final DevResponseFactory theDevResponseFactory = (DevResponseFactory) EPackage.Registry.INSTANCE
					.getEFactory(DevResponsePackage.eNS_URI);
			if (theDevResponseFactory != null) {
				return theDevResponseFactory;
			}
		} catch (final Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DevResponseFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public DevResponseFactoryImpl() {
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(final EClass eClass) {
		return switch (eClass.getClassifierID()) {
		case DevResponsePackage.RESOURCE -> createResource();
		case DevResponsePackage.FB -> createFB();
		case DevResponsePackage.FB_TYPE -> createFBType();
		case DevResponsePackage.PORT -> createPort();
		case DevResponsePackage.DATA -> createData();
		case DevResponsePackage.RESPONSE -> createResponse();
		case DevResponsePackage.WATCHES -> createWatches();
		case DevResponsePackage.FB_LIST -> createFBList();
		case DevResponsePackage.ENDPOINT_LIST -> createEndpointList();
		case DevResponsePackage.CONNECTION -> createConnection();
		default -> throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		};
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Resource createResource() {
		return new ResourceImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FB createFB() {
		return new FBImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBType createFBType() {
		return new FBTypeImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Port createPort() {
		return new PortImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Data createData() {
		return new DataImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Response createResponse() {
		return new ResponseImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Watches createWatches() {
		return new WatchesImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public FBList createFBList() {
		return new FBListImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EndpointList createEndpointList() {
		return new EndpointListImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Connection createConnection() {
		return new ConnectionImpl();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public DevResponsePackage getDevResponsePackage() {
		return (DevResponsePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DevResponsePackage getPackage() {
		return DevResponsePackage.eINSTANCE;
	}

} // DevResponseFactoryImpl
