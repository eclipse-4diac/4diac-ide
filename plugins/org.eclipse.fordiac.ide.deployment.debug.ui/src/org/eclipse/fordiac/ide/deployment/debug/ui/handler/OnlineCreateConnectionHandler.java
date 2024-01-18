/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.util.List;

import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.gef.ConnectionEditPart;

public class OnlineCreateConnectionHandler extends AbstractDeploymentCommand {

	private Connection conn = null;
	private Resource res = null;

	@Override
	protected boolean prepareParametersToExecute(final Object element) {
		if (element instanceof ConnectionEditPart) {
			conn = (Connection) ((ConnectionEditPart) element).getModel();
			final Connection resCon = getResourceConnection(conn);
			if (null != resCon) {
				res = (Resource) resCon.getFBNetwork().eContainer();
				if (null != res) {
					setDevice(res.getDevice());
					return (null != getDevice());
				}
			}
		}
		return false;
	}

	@Override
	protected void executeCommand(final IDeviceManagementInteractor executor) throws DeploymentException {
		executor.createConnection(res, new ConnectionDeploymentData("", conn.getSource(), "", conn.getDestination())); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static Connection getResourceConnection(final Connection conn) {
		if (conn.getFBNetwork().eContainer() instanceof Resource) {
			return conn;
		}
		final FBNetworkElement oppositeSource = conn.getSourceElement().getOpposite();
		if (null != oppositeSource) {
			final List<Connection> conns = oppositeSource.getInterfaceElement(conn.getSource().getName())
					.getOutputConnections();
			for (final Connection connection : conns) {
				if (connection.getDestination().getName().equals(conn.getDestination().getName())) {
					return connection;
				}
			}
		}

		return null;
	}

	@Override
	protected String getErrorMessageHeader() {
		return Messages.OnlineCreateConnectionHandler_CreateConnectionError;
	}

	@Override
	protected String getCurrentElementName() {
		return conn.getSource().getQualifiedName() + " -> " + conn.getDestination().getQualifiedName(); //$NON-NLS-1$
	}
}
