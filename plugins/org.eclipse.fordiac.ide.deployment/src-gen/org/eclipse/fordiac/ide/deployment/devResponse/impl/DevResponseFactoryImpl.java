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
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Resource;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.devResponse.Watches;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DevResponseFactoryImpl extends EFactoryImpl implements DevResponseFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DevResponseFactory init() {
		try {
			DevResponseFactory theDevResponseFactory = (DevResponseFactory)EPackage.Registry.INSTANCE.getEFactory(DevResponsePackage.eNS_URI);
			if (theDevResponseFactory != null) {
				return theDevResponseFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DevResponseFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DevResponseFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DevResponsePackage.RESOURCE: return createResource();
			case DevResponsePackage.FB: return createFB();
			case DevResponsePackage.PORT: return createPort();
			case DevResponsePackage.DATA: return createData();
			case DevResponsePackage.RESPONSE: return createResponse();
			case DevResponsePackage.WATCHES: return createWatches();
			case DevResponsePackage.FB_LIST: return createFBList();
			case DevResponsePackage.ENDPOINT_LIST: return createEndpointList();
			case DevResponsePackage.CONNECTION: return createConnection();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Resource createResource() {
		ResourceImpl resource = new ResourceImpl();
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FB createFB() {
		FBImpl fb = new FBImpl();
		return fb;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Port createPort() {
		PortImpl port = new PortImpl();
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Data createData() {
		DataImpl data = new DataImpl();
		return data;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Response createResponse() {
		ResponseImpl response = new ResponseImpl();
		return response;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Watches createWatches() {
		WatchesImpl watches = new WatchesImpl();
		return watches;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBList createFBList() {
		FBListImpl fbList = new FBListImpl();
		return fbList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EndpointList createEndpointList() {
		EndpointListImpl endpointList = new EndpointListImpl();
		return endpointList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Connection createConnection() {
		ConnectionImpl connection = new ConnectionImpl();
		return connection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DevResponsePackage getDevResponsePackage() {
		return (DevResponsePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DevResponsePackage getPackage() {
		return DevResponsePackage.eINSTANCE;
	}

} //DevResponseFactoryImpl
