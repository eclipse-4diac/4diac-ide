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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory;
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
			current = assignNextInterfaceElement(isInput, outConns).get(0);
		}
		return current;

	}

	public static String findConnectedMonitoredSubappPort(final IInterfaceElement interfaceElement,
			final Map<String, List<MonitoringElement>> subappElements) {

		final boolean searchDirection = interfaceElement.isIsInput();

		IInterfaceElement currentIe = interfaceElement;
		do {
			final EList<Connection> connections = getConnections(currentIe, searchDirection);

			if (connections.isEmpty()) {
				return null;
			}

			final List<IInterfaceElement> nextElements = assignNextInterfaceElement(searchDirection, connections);

			for (final IInterfaceElement nextIe : nextElements) {
				currentIe = nextIe;
				if (!currentIe.getFBNetworkElement().isMapped()) {
					continue;
				}
				final PortElement subappPortCandidate = MonitoringManagerUtils
						.createPortElement(currentIe.getFBNetworkElement(), currentIe);

				final String anchor = searchSubappAnchor(subappPortCandidate);
				if (subappElements.containsKey(anchor)) {
					return anchor;
				}

			}

		} while ((currentIe.getFBNetworkElement() instanceof SubApp));

		return null;
	}

	public static List<MonitoringElement> findConnectedElements(final IInterfaceElement interfaceElement) {
		final List<MonitoringElement> elements = new ArrayList<>();
		final boolean searchDirection = interfaceElement.isIsInput();
		findConnectedElements(elements, interfaceElement, searchDirection);
		return elements;

	}

	public static List<MonitoringElement> findConnectedElements(final IInterfaceElement interfaceElement,
			final boolean searchDirection) {
		final List<MonitoringElement> elements = new ArrayList<>();
		findConnectedElements(elements, interfaceElement, searchDirection);
		return elements;

	}

	public static void findConnectedElements(final List<MonitoringElement> elements,
			final IInterfaceElement interfaceElement) {
		final boolean searchDirection = interfaceElement.isIsInput();
		findConnectedElements(elements, interfaceElement, searchDirection);
	}

	public static void findConnectedElements(final List<MonitoringElement> elements,
			final IInterfaceElement interfaceElement, final boolean searchDirection) {
		IInterfaceElement currentIe = interfaceElement;

		final EList<Connection> connections = getConnections(currentIe, searchDirection);

		if (connections.isEmpty()) {
			return;
		}

		final List<IInterfaceElement> nextElements = assignNextInterfaceElement(searchDirection, connections);

		for (final IInterfaceElement nextIe : nextElements) {
			currentIe = nextIe;
			if (nextIe.getFBNetworkElement() instanceof SubApp) {
				findConnectedElements(elements, nextIe, searchDirection);
			} else if (!(!nextIe.getFBNetworkElement().isNestedInSubApp()
					&& !nextIe.getFBNetworkElement().isMapped())) {
				final MonitoringBaseElement element = MonitoringFactory.eINSTANCE.createMonitoringElement();
				final PortElement anchorPort = MonitoringManagerUtils.createPortElement(currentIe);
				element.setPort(anchorPort);
				elements.add((MonitoringElement) element);
			}

		}
	}

	public static List<IInterfaceElement> assignNextInterfaceElement(final boolean isInput,
			final Collection<Connection> connections) {
		return connections.stream().map(connection -> isInput ? connection.getDestination() : connection.getSource())
				.toList();
	}

	public static boolean hasEmptyConnections(final IInterfaceElement ie, final boolean isInput) {
		final EList<Connection> connections = getConnections(ie, isInput);
		return connections.isEmpty();
	}

	public static EList<Connection> getConnections(final IInterfaceElement ie, final boolean isInput) {
		return isInput ? ie.getOutputConnections() : ie.getInputConnections();
	}

	public static String searchSubappAnchor(final PortElement createPortElement) {

		final MonitoringBaseElement monitoringElement = MonitoringManager.getInstance()
				.getMonitoringElement(createPortElement.getInterfaceElement());

		if (!(monitoringElement instanceof SubappMonitoringElement)) {
			return ""; //$NON-NLS-1$
		}
		final MonitoringBaseElement anchor = ((SubappMonitoringElement) monitoringElement).getAnchor();

		return anchor.getPort().getPortString();
	}

	private SubAppPortHelper() {
		throw new UnsupportedOperationException("Helper class should not be instantiated!"); //$NON-NLS-1$
	}
}
