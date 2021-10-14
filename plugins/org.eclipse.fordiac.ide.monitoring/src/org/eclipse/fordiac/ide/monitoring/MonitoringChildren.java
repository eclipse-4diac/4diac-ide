/*******************************************************************************
 * Copyright (c) 2012 - 2021 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							 Johannes Kepler University,
 * 							 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *   Daniel Lindhuber - refined conditions for children
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.deployment.monitoringbase.IMonitoringListener;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.gef.editparts.IChildrenProvider;
import org.eclipse.fordiac.ide.gef.editparts.IEditPartCreator;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement;


public class MonitoringChildren implements IMonitoringListener, IChildrenProvider {

	public MonitoringChildren() {
		MonitoringManager.getInstance().registerMonitoringListener(this);
	}

	@Override
	public List<IEditPartCreator> getChildren(final FBNetwork fbNetwork) {
		if (null != fbNetwork) {
			final Collection<MonitoringBaseElement> elementList = MonitoringManager.getInstance()
					.getElementsToMonitor(fbNetwork.getAutomationSystem());
			return elementList.stream().filter(el -> shouldBeAdded(el, fbNetwork)).collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	private static boolean shouldBeAdded(final MonitoringBaseElement element, final FBNetwork fbNetwork) {
		if (element != null && element.getPort().getFb().getFbNetwork() != null) {
			if (element.getPort().getFb().getFbNetwork().equals(fbNetwork)) {
				return true;
			} else if (element instanceof SubappMonitoringElement || checkResource(element)) {
				final Object parent = element.getPort().getFb().getFbNetwork().eContainer();
				return isInsideMonitoredSubApp(parent, fbNetwork);
			}
		}
		return false;
	}

	private static boolean checkResource(final MonitoringBaseElement element) {
		final FBNetworkElement fb = element.getPort().getFb();
		return element.getPort().getFb().getResource() != null && (fb instanceof FB) && (!((FB) fb).isResourceFB());
	}

	private static boolean isInsideMonitoredSubApp(final Object parent, final FBNetwork network) {
		if (parent instanceof SubApp) {
			final SubApp subapp = (SubApp) parent;
			if (network.equals(subapp.getSubAppNetwork())) {
				return true;
			} else if (subapp.isUnfolded()) {
				return isInsideMonitoredSubApp(subapp.eContainer().eContainer(), network);
			}
		}
		if (parent instanceof Application) {
			return network.equals(((Application) parent).getFBNetwork());
		}
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void notifyAddPort(final PortElement port) {
		// nothing to do
	}

	@Override
	public void notifyRemovePort(final PortElement port) {
		// nothing to do
	}

	@Override
	public void notifyTriggerEvent(final PortElement port) {
		// nothing to do
	}

	@Override
	public void notifyWatchesChanged() {
		// nothing to do
	}

}
