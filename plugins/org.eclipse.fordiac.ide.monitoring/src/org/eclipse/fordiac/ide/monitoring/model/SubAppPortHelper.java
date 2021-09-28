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

public final class SubAppPortHelper {


	public static IInterfaceElement findAnchorInterfaceElement(final IInterfaceElement subappPin) {

		final boolean isInput = subappPin.isIsInput();
		if (hasEmptyConnections(subappPin, isInput)) {
			return null;
		}

		IInterfaceElement current = subappPin;
		while ((current.getFBNetworkElement() instanceof SubApp)) {
			final EList<Connection> outConns = getConnections(current, isInput);
			if (outConns.isEmpty()) {
				return null;
			}
			current = assignNextInterfaceElement(isInput, outConns);
		}
		return current;

	}

	public static String findConnectedMonitoredSubappPort(IInterfaceElement interfaceElement,
			final Map<String, List<MonitoringElement>> subappElements) {

		final boolean searchDirection = !interfaceElement.isIsInput();

		do {
			final EList<Connection> connections = getConnections(interfaceElement, searchDirection);

			if (connections.isEmpty()) {
				return null;
			}

			interfaceElement = assignNextInterfaceElement(searchDirection, connections);

			if (!interfaceElement.getFBNetworkElement().isMapped()) {
				return null;
			}
			final PortElement subappPortCanidate = MonitoringManagerUtils
					.createPortElement(interfaceElement.getFBNetworkElement(), interfaceElement);

			if (subAppAnchorFound(subappElements, subappPortCanidate)) {
				return subappPortCanidate.getPortString();
			}

		} while ((interfaceElement.getFBNetworkElement() instanceof SubApp));

		return null;
	}

	public static IInterfaceElement assignNextInterfaceElement(final boolean isInput,
			final List<Connection> connections) {
		final Connection connection = connections.get(0);
		return isInput ? connection.getDestination() : connection.getSource();
	}

	public static boolean hasEmptyConnections(final IInterfaceElement ie, final boolean isInput) {
		final EList<Connection> connections = getConnections(ie, isInput);
		return connections.isEmpty();
	}

	public static EList<Connection> getConnections(final IInterfaceElement ie, final boolean isInput) {
		return isInput ? ie.getOutputConnections()
				: ie.getInputConnections();
	}

	public static boolean subAppAnchorFound(final Map<String, List<MonitoringElement>> subappElements,
			final PortElement createPortElement) {

		final MonitoringBaseElement monitoringElement = MonitoringManager.getInstance()
				.getMonitoringElement(createPortElement.getInterfaceElement());

		if (!(monitoringElement instanceof SubappMonitoringElement)) {
			return false;
		}
		final MonitoringBaseElement anchor = ((SubappMonitoringElement) monitoringElement).getAnchor();

		return subappElements.containsKey(anchor.getPort().getPortString());
	}

	private SubAppPortHelper() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}
}
