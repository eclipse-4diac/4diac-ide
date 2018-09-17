/*******************************************************************************
 * Copyright (c) 2008 - 2011, 2013, 2017, 2018 Profactor GmbH, fortiss GmbH,
 * 						Johanns Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse  License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * Interface for classes that allow deployment and monitoring to interact with a
 * device's device management functionality.
 * 
 */
public interface IDeviceManagementInteractor {
	
	/** simple interface that allows to easily use an IDeviceMangemetnInteractor in a
	 *  try with resources setup. 
	 */
	public interface IDeviceManagementInteractorCloser extends AutoCloseable {
		@Override
		void close() throws DeploymentException;
	}

	/*************** Communication commands **************************/

	/**Check if this interactor is currently connected with its device
	 * 
	 * @return true if a connection is open.
	 */
	public abstract boolean isConnected();

	public abstract void connect() throws DeploymentException;

	public abstract void disconnect() throws DeploymentException;

	/**** Listener commands ***/

	void addDeploymentListener(final IDeploymentListener listener);

	void removeDeploymentListener(final IDeploymentListener listener);
	
	/*** Management Commands ***/

	/**
	 * Provide for the given device the address needed for the commhandler to
	 * connect to it
	 * 
	 * @param device the dvice to connect to
	 * @return the device's address
	 */

	/**
	 * Creates the resource.
	 * 
	 * @param resource the resource
	 * 
	 * @throws CreateResourceInstanceException the create resource instance
	 *                                         exception
	 */
	void createResource(Resource resource) throws DeploymentException;

	/**
	 * Write resource parameter.
	 * 
	 * @param resource  the resource
	 * @param parameter the parameter
	 * @param value     the value
	 * 
	 * @throws WriteResourceParameterException the write resource parameter
	 *                                         exception
	 */
	void writeResourceParameter(Resource resource, String parameter, String value) throws DeploymentException;

	/**
	 * Write device parameter.
	 * 
	 * @param device    the device
	 * @param parameter the parameter
	 * @param value     the value
	 * 
	 * @throws WriteDeviceParameterException the write device parameter exception
	 */
	void writeDeviceParameter(Device device, String parameter, String value) throws DeploymentException;

	/**
	 * Creates the fb instance.
	 * 
	 * @param fb  the fb
	 * @param res the res
	 * 
	 * @throws CreateFBInstanceException the create fb instance exception
	 */
	void createFBInstance(FBDeploymentData fb, Resource res) throws DeploymentException;

	/**
	 * Write fb parameter.
	 * 
	 * @param resource the resource
	 * @param value    the value
	 * @param fb       the fb
	 * @param varDecl  the var decl
	 * 
	 * @throws WriteFBParameterException the write fb parameter exception
	 */
	void writeFBParameter(Resource resource, String value, FBDeploymentData fb, VarDeclaration varDecl)
			throws DeploymentException;

	/**
	 * Creates the connection.
	 * 
	 * @param res            the res
	 * @param connectionData information on the connection to create
	 * 
	 * @throws CreateConnectionException the create connection exception
	 */
	void createConnection(Resource res, ConnectionDeploymentData connectionData) throws DeploymentException;

	/**
	 * Start FB Instance.
	 * 
	 * @param res the res
	 * @param fb  the fb
	 * 
	 * @throws StartException the start exception
	 */
	void startFB(Resource res, FBDeploymentData fb) throws DeploymentException;

	/**
	 * Start resource.
	 * 
	 * @param res the res
	 * 
	 * @throws StartException the start exception
	 */
	void startResource(Resource res) throws DeploymentException;

	/**
	 * Start device.
	 * 
	 * @param dev the dev
	 * 
	 * @throws StartException the start exception
	 */
	void startDevice(Device dev) throws DeploymentException;

	/**
	 * Delete resource.
	 * 
	 * @param res the res
	 * 
	 * @throws Exception the exception
	 */
	void deleteResource(Resource res) throws DeploymentException;

	/**
	 * Delete fb instance.
	 * 
	 * @param res the res
	 * @param fb  the fb instance
	 * 
	 * @throws Exception the exception
	 */
	void deleteFB(Resource res, FBDeploymentData fb) throws DeploymentException;

	/**
	 * Delete connection.
	 * 
	 * @param res the res
	 * @param con the connection
	 * 
	 * @throws Exception the exception
	 */
	void deleteConnection(Resource res, ConnectionDeploymentData con) throws DeploymentException;

	/**
	 * Clear device.
	 * 
	 * @param dev the device
	 * 
	 * @throws Exception the exception
	 */
	void clearDevice(Device dev) throws DeploymentException;

	/**
	 * Kill device.
	 * 
	 * @param dev the device
	 * 
	 * @throws Exception the exception
	 */
	void killDevice(Device dev) throws DeploymentException;

	/***********************
	 * monitoring commands
	 ****************************************************/
	Response readWatches() throws DeploymentException;

	void addWatch(MonitoringBaseElement element) throws DeploymentException;

	void removeWatch(MonitoringBaseElement element) throws DeploymentException;

	void triggerEvent(MonitoringBaseElement element) throws DeploymentException;

	void forceValue(MonitoringBaseElement element, String value) throws DeploymentException;

	void clearForce(MonitoringBaseElement element) throws DeploymentException;

}
