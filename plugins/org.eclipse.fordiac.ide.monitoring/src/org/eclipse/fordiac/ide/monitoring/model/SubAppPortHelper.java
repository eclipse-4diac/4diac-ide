/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.model;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement;
import org.eclipse.fordiac.ide.monitoring.MonitoringManager;
import org.eclipse.fordiac.ide.monitoring.MonitoringManagerUtils;

public class SubAppPortHelper {


	public static IInterfaceElement findAnchorInterfaceElement(final IInterfaceElement subappPin) {

		final boolean isInput = subappPin.isIsInput();

		final EList<Connection> connections = isInput ? subappPin.getOutputConnections()
				: subappPin.getInputConnections();

		if (connections.isEmpty()) {
			return null;
		}

		IInterfaceElement current = subappPin;
		while ((current.getFBNetworkElement() instanceof SubApp)) {
			final EList<Connection> outConns = isInput ? current.getOutputConnections() : current.getInputConnections();
			if (outConns.isEmpty()) {
				return null;
			}
			final Connection connection = outConns.get(0);
			current = isInput ? connection.getDestination() : connection.getSource();
		}
		return current;

	}

	public static String findConnectedMonitoredSubappPort(final IInterfaceElement ie,
			final Map<String, List<MonitoringElement>> subappElements) {

		final boolean isInput = !ie.isIsInput();

		final EList<Connection> connections = isInput ? ie.getOutputConnections()
				: ie.getInputConnections();

		if (connections.isEmpty()) {
			return null;
		}

		IInterfaceElement current = ie;
		do {
			final EList<Connection> outConns = isInput ? current.getOutputConnections() : current.getInputConnections();
			if (outConns.isEmpty()) {
				return null;
			}
			final Connection connection = outConns.get(0);
			current = isInput ? connection.getDestination() : connection.getSource();

			final PortElement createPortElement = MonitoringManagerUtils
					.createPortElement(current.getFBNetworkElement(), current);
			String portString = createPortElement.getPortString();

			final MonitoringBaseElement monitoringElement = MonitoringManager.getInstance()
					.getMonitoringElement(createPortElement.getInterfaceElement());
			if (monitoringElement instanceof SubappMonitoringElement) {
				final SubappMonitoringElement e = (SubappMonitoringElement) monitoringElement;
				portString = e.getAnchor().getPort().getPortString();
				if (subappElements.containsKey(portString)) {
					return portString;
				}
			}

			if (subappElements.containsKey(portString)) {
				return portString;
			}

		} while ((current.getFBNetworkElement() instanceof SubApp));

		return null;
	}


}
