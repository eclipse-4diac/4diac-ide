/*******************************************************************************
 * Copyright (c) 2008 - 2011, 2013, 2017, 2018 Profactor GmbH, fortiss GmbH,
 * 						Johanns Kepler University
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
package org.eclipse.fordiac.ide.deployment.interactors;

import java.util.Set;

import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.CreateConnectionException;
import org.eclipse.fordiac.ide.deployment.exceptions.CreateFBInstanceException;
import org.eclipse.fordiac.ide.deployment.exceptions.CreateResourceInstanceException;
import org.eclipse.fordiac.ide.deployment.exceptions.StartException;
import org.eclipse.fordiac.ide.deployment.exceptions.WriteDeviceParameterException;
import org.eclipse.fordiac.ide.deployment.exceptions.WriteFBParameterException;
import org.eclipse.fordiac.ide.deployment.exceptions.WriteResourceParameterException;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/** Interface for classes that allow deployment and monitoring to interact with
 * a device's device management functionality.
 * 
 */
public interface IDeviceManagementInteractor {
	
	public Set<String> getTypes();

	public Set<String> getAdapterTypes();
	
	public void resetTypes();
	
	public void addDeploymentListener(final IDeploymentListener listener);

	public void removeDeploymentListener(final IDeploymentListener listener);
	
	//FIXME remove this function
	public AbstractDeviceManagementCommunicationHandler getDevMgmComHandler();
	
	
	/**
	 * Creates the resource.
	 * 
	 * @param resource
	 *          the resource
	 * 
	 * @throws CreateResourceInstanceException
	 *           the create resource instance exception
	 */
	public void createResource(Resource resource)
			throws CreateResourceInstanceException;

	/**
	 * Write resource parameter.
	 * 
	 * @param resource
	 *          the resource
	 * @param parameter
	 *          the parameter
	 * @param value
	 *          the value
	 * 
	 * @throws WriteResourceParameterException
	 *           the write resource parameter exception
	 */
	public void writeResourceParameter(Resource resource, String parameter,
			String value) throws WriteResourceParameterException;

	/**
	 * Write device parameter.
	 * 
	 * @param device
	 *          the device
	 * @param parameter
	 *          the parameter
	 * @param value
	 *          the value
	 * 
	 * @throws WriteDeviceParameterException
	 *           the write device parameter exception
	 */
	public void writeDeviceParameter(Device device, String parameter, String value)
			throws WriteDeviceParameterException;

	/**
	 * Creates the fb instance.
	 * 
	 * @param fb
	 *          the fb
	 * @param res
	 *          the res
	 * 
	 * @throws CreateFBInstanceException
	 *           the create fb instance exception
	 */
	public void createFBInstance(FBDeploymentData fb, Resource res)
			throws CreateFBInstanceException;

	/**
	 * Write fb parameter.
	 * 
	 * @param resource
	 *          the resource
	 * @param value
	 *          the value
	 * @param fb
	 *          the fb
	 * @param varDecl
	 *          the var decl
	 * 
	 * @throws WriteFBParameterException
	 *           the write fb parameter exception
	 */
	public void writeFBParameter(Resource resource, String value, FBDeploymentData fb,
			VarDeclaration varDecl) throws WriteFBParameterException;

	/**
	 * Creates the  connection.
	 * 
	 * @param res
	 *          the res
	 * @param connectionData information on the connection to create
	 * 
	 * @throws CreateConnectionException
	 *           the create connection exception
	 */
	public void createConnection(Resource res, ConnectionDeploymentData connectionData) throws CreateConnectionException;

	/**
	 * Start FB Instance.
	 * 
	 * @param res the res
	 * @param fb the fb
	 * 
	 * @throws StartException the start exception
	 */
	public void startFB(Resource res, FBDeploymentData fb) throws StartException;

	/**
	 * Start resource.
	 * 
	 * @param res
	 *          the res
	 * 
	 * @throws StartException
	 *           the start exception
	 */
	public void startResource(Resource res) throws StartException;


	/**
	 * Start device.
	 * 
	 * @param dev
	 *          the dev
	 * 
	 * @throws StartException
	 *           the start exception
	 */
	public void startDevice(Device dev) throws StartException;

	
	/**
	 * Delete resource.
	 * 
	 * @param res
	 *          the res
	 * 
	 * @throws Exception
	 *           the exception
	 */
	public void deleteResource(Resource res) throws Exception;

	/**
	 * Delete fb instance.
	 * 
	 * @param res the res
	 * @param fb the fb instance
	 * 
	 * @throws Exception the exception
	 */
	public void deleteFB(Resource res, FBDeploymentData fb) throws Exception;

	/**
	 * Delete connection.
	 * 
	 * @param res the res
	 * @param con the connection
	 * 
	 * @throws Exception the exception
	 */
	public void deleteConnection(Resource res, ConnectionDeploymentData con) throws Exception;

	/**
	 * Clear device.
	 * 
	 * @param dev
	 *          the device
	 * 
	 * @throws Exception
	 *           the exception
	 */
	public void clearDevice(Device dev) throws Exception;

	/**
	 * Kill device.
	 * 
	 * @param dev
	 *          the device
	 * 
	 * @throws Exception
	 *           the exception
	 */
	public void killDevice(Device dev) throws Exception;
}
