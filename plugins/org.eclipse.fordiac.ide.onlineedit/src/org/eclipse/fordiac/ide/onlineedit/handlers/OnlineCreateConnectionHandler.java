/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.onlineedit.handlers;

import java.util.List;

import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.ui.handlers.AbstractDeploymentCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.gef.ConnectionEditPart;

public class OnlineCreateConnectionHandler extends AbstractDeploymentCommand{

	Connection conn = null;
	Resource res = null;
	
	@Override
	protected boolean prepareParametersToExecute(Object element) {
		if (element instanceof ConnectionEditPart){
			conn = (Connection)((ConnectionEditPart) element).getModel();
			Connection resCon = getResourceConnection(conn);
			if (null != resCon){
				res = (Resource) resCon.getFBNetwork().eContainer();
				if (null != res){
					device = res.getDevice();
					return (null != device);
				}
			}
		}
		return false;
	}

	@Override
	protected void executeCommand(IDeviceManagementInteractor executor) throws DeploymentException {
		executor.createConnection(res, new ConnectionDeploymentData("", conn.getSource(), "", conn.getDestination())); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	private static Connection getResourceConnection(Connection conn) {
		if(conn.getFBNetwork().eContainer() instanceof Resource){
			return conn;
		}		
		FBNetworkElement oppositeSource = conn.getSourceElement().getOpposite();
		if(null != oppositeSource){
			List<Connection> conns = oppositeSource.getInterfaceElement(conn.getSource().getName()).getOutputConnections();
			for (Connection connection : conns) {
				if(connection.getDestination().getName().equals(conn.getDestination().getName())){
					return connection;
				}
			}
		}
		
		return null;
	}
	
	@Override
	protected String getErrorMessageHeader() {
		return "Create Connection Error";
	}

	@Override
	protected String getCurrentElementName() {
		return "Connection: " + conn.getName();
	}
}
