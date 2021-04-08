/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, AIT, fortiss GmbH
 * 							 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Filip Andren, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseFactory;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringFactory;

public final class MonitoringManagerUtils {

	private MonitoringManagerUtils() {
		throw new AssertionError(); // class should not be instantiated
	}

	public static boolean canBeMonitored(final IInterfaceElement ie) {
		final PortElement port = MonitoringManagerUtils.createPortElement(ie); // FIXME think how we can get away
		// without creating a port element
		return ((port != null) && (port.getPortString() != null));
	}

	public static boolean canBeMonitored(final FBNetworkElement obj) {
		// As a first solution try to find the first interface element and see if we
		// can monitor it
		for (final IInterfaceElement child : obj.getInterface().getAllInterfaceElements()) {
			return canBeMonitored(child);
		}
		return false;
	}

	public static PortElement createPortElement(
			final IInterfaceElement ie) {
		final FBNetworkElement obj = ie.getFBNetworkElement();
		if (obj instanceof FB) {
			final FB fb = (FB) obj;
			return createPortElement(fb, ie);
		}

		return null;

	}

	private static PortElement createPortElement(final FBNetworkElement fb, final IInterfaceElement ie) {
		PortElement p;
		if (ie instanceof AdapterDeclaration) {
			p = MonitoringFactory.eINSTANCE.createAdapterPortElement();
		} else {
			p = MonitoringBaseFactory.eINSTANCE.createPortElement();
		}

		final Resource res = fb.getResource();
		if (res == null) {
			return null;
		}

		p.setResource(res);
		// TODO adapt or remove this
		if (fb instanceof FB) {
			p.setFb((FB) fb);
		}
		setupFBHierarchy(fb, p);
		p.setInterfaceElement(ie);
		return p;
	}

	private static void setupFBHierarchy(final FBNetworkElement element, final PortElement p) {
		if (!element.isMapped() && element.getFbNetwork().eContainer() instanceof SubApp) {
			final SubApp subApp = (SubApp) element.getFbNetwork().eContainer();
			setupFBHierarchy(subApp, p);
			p.getHierarchy().add(subApp.getName());
		}
	}

}
