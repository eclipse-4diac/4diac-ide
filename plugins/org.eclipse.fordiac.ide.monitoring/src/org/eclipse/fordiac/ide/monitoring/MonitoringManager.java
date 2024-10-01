/*******************************************************************************
 * Copyright (c) 2012, 2024 Profactor GmbH, AIT, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Filip Andren, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *   Michael Oberlehner - added subapp monitoring
 *   Martin Jobst - adopt new ST editor for values
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.AbstractMonitoringManager;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Mapping;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.InternalVarInstance;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement;
import org.eclipse.fordiac.ide.monitoring.handlers.RemoveAllWatchesHandler;
import org.eclipse.fordiac.ide.monitoring.model.SubAppPortHelper;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * Singleton instance that Coordinates and manages all the Ports to be
 * monitored.
 */
public class MonitoringManager extends AbstractMonitoringManager {

	private final Map<AutomationSystem, SystemMonitoringData> systemMonitoringData = new HashMap<>();

	/**
	 * Gets the single instance of MonitoringManager.
	 *
	 * @return single instance of MonitoringManager
	 */
	public static MonitoringManager getInstance() {
		return (MonitoringManager) AbstractMonitoringManager.getMonitoringManager();
	}

	/**
	 * Gets the monitoring element.
	 *
	 * @param port the port
	 *
	 * @return the monitoring element
	 */
	public MonitoringBaseElement getMonitoringElement(final IInterfaceElement port) {
		final AutomationSystem sys = getAutomationSystem(port);
		if (sys != null) {
			final SystemMonitoringData data = systemMonitoringData.get(sys);
			if (data != null) {
				if (port instanceof final InternalVarInstance intVar) {
					final PortElement portEl = MonitoringManagerUtils.createPortElement(intVar);
					return (portEl != null) ? data.getMonitoringElementByPortString(portEl.getPortString()) : null;
				}
				return data.getMonitoredElement(port);
			}
		}
		return null;
	}

	private static AutomationSystem getAutomationSystem(final IInterfaceElement port) {
		if (port != null) {
			final FBNetworkElement fbNetworkElement = port.getFBNetworkElement();
			if (fbNetworkElement != null) {
				if (fbNetworkElement instanceof final AdapterFB adpFB) {
					return getAutomationSystem(adpFB.getAdapterDecl());
				}
				if (fbNetworkElement.getFbNetwork() != null) {
					return fbNetworkElement.getFbNetwork().getAutomationSystem();
				}
			}
		}
		return null;
	}

	private final Adapter contentAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final Notification notification) {
			super.notifyChanged(notification);
			if (notification.getEventType() == Notification.REMOVE
					&& notification.getOldValue() instanceof final Mapping mapping) {
				final FBNetworkElement fb = mapping.getFrom();
				if (fb instanceof final SubApp subApp) {
					removeMonitoringElementsFromSubApp(subApp);
				} else {
					removeMonitoringElementsFromFB(fb);
				}
				notifyWatchesChanged();

				final GraphicalViewer viewer = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.getActiveEditor().getAdapter(GraphicalViewer.class);
				if (viewer != null) {
					viewer.getRootEditPart().refresh();
					final List<?> children = viewer.getRootEditPart().getChildren();
					children.forEach(child -> ((EditPart) child).refresh());
				}
			}
		}
	};

	/**
	 * Adds monitoring elements.
	 *
	 * @param elemeent the monitoring element
	 */
	public void addMonitoringElement(final MonitoringBaseElement element) {
		final PortElement port = element.getPort();
		final SystemMonitoringData data = getSystemMonitoringData(port.getSystem());
		data.addMonitoringElement(element);
		notifyAddPort(port);
	}

	/**
	 * Removes the monitoring element.
	 *
	 * @param element the monitoring element
	 */
	public void removeMonitoringElement(final MonitoringBaseElement element) {
		final SystemMonitoringData data = getSystemMonitoringData(element.getPort().getSystem());
		data.removeMonitoringElement(element);
		notifyRemovePort(element.getPort());
	}

	/**
	 * Removes the monitoring elements from a given FBNetworkElement.
	 *
	 * @param element the FBNetworkElement
	 */
	public void removeMonitoringElementsFromFB(final FBNetworkElement element) {
		for (final IInterfaceElement ieElement : RemoveAllWatchesHandler
				.getWatchedIfElementsForFB(MonitoringManager.this, element)) {
			removeMonitoringElement(getMonitoringElement(ieElement));
		}
	}

	/**
	 * Removes the monitoring elements from a given FBNetworkElement.
	 *
	 * @param element the Subapp
	 */
	public void removeMonitoringElementsFromSubApp(final SubApp element) {

		removeMonitoringElementsFromFB(element);

		for (final FBNetworkElement nestedFB : element.getSubAppNetwork().getNetworkElements()) {
			if (nestedFB instanceof final SubApp subApp) {
				removeMonitoringElementsFromSubApp(subApp);
			} else {
				removeMonitoringElementsFromFB(nestedFB);
			}
		}
	}

	/**
	 * Contains port.
	 *
	 * @param port the port
	 *
	 * @return true, if successful
	 */
	public boolean containsPort(final IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			return (null != getMonitoringElement(interfaceElement));
		}
		return false;
	}

	public Collection<MonitoringBaseElement> getAllElementsToMonitor() {
		final List<MonitoringBaseElement> elements = new ArrayList<>();

		for (final SystemMonitoringData data : systemMonitoringData.values()) {
			elements.addAll(data.getMonitoredElements());
		}
		return elements;
	}

	public Collection<MonitoringBaseElement> getElementsToMonitor(final AutomationSystem sys) {
		if (sys != null) {
			final SystemMonitoringData sysData = systemMonitoringData.get(sys);
			if (sysData != null) {
				return sysData.getMonitoredElements();
			}
		}
		return Collections.emptyList();
	}

	/**
	 * Enable system.
	 *
	 * @param system the system
	 */
	@Override
	public void enableSystem(final AutomationSystem system) {
		getSystemMonitoringData(system).enableSystem();
		if (!system.eAdapters().contains(contentAdapter)) {
			system.eAdapters().add(contentAdapter);
		}
	}

	@Override
	public void enableSystemSynch(final AutomationSystem system, final IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		getSystemMonitoringData(system).enableSystemSynch(monitor);
		if (!system.eAdapters().contains(contentAdapter)) {
			system.eAdapters().add(contentAdapter);
		}
	}

	/**
	 * Disable system.
	 *
	 * @param system the system
	 */
	@Override
	public void disableSystem(final AutomationSystem system) {
		getSystemMonitoringData(system).disableSystem();
		notifyWatchesChanged();
		if (system.eAdapters().contains(contentAdapter)) {
			system.eAdapters().remove(contentAdapter);
		}
	}

	@Override
	public void disableSystemSynch(final AutomationSystem system, final IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		getSystemMonitoringData(system).disableSystemSynch(monitor);
		if (system.eAdapters().contains(contentAdapter)) {
			system.eAdapters().remove(contentAdapter);
		}
	}

	@Override
	public boolean isSystemMonitored(final AutomationSystem system) {
		return getSystemMonitoringData(system).isMonitoringForSystemEnabled();
	}

	/**
	 * Contains system.
	 *
	 * @param system the system
	 *
	 * @return true, if successful
	 */
	public boolean monitoringForSystemEnabled(final AutomationSystem system) {
		final SystemMonitoringData data = getSystemMonitoringData(system);
		return data.isMonitoringForSystemEnabled();
	}

	public Set<AutomationSystem> getMonitoredSystems() {
		return systemMonitoringData.keySet();
	}

	/**
	 * Trigger event.
	 *
	 * @param port the port
	 */
	public void triggerEvent(final IInterfaceElement interfaceElement) {
		final MonitoringBaseElement element = getMonitoringElement(interfaceElement);

		if (element instanceof final MonitoringElement monitoringElement) {
			final List<MonitoringElement> elements = new ArrayList<>();
			final SystemMonitoringData data = getSystemMonitoringData(monitoringElement.getPort().getSystem());

			if (element instanceof SubappMonitoringElement) {

				if (!element.getPort().getInterfaceElement().isIsInput()) {
					elements.add((MonitoringElement) ((SubappMonitoringElement) element).getAnchor());
				} else {

					final List<MonitoringElement> findElements = SubAppPortHelper
							.findConnectedElements(element.getPort().getInterfaceElement(), true);
					elements.addAll(findElements);
				}

			} else {
				elements.add((MonitoringElement) element);
			}

			for (final MonitoringElement me : elements) {
				final IDeviceManagementInteractor devMgmInteractor = data.getDevMgmInteractor(me.getPort().getDevice());
				if (devMgmInteractor != null) {
					try {
						devMgmInteractor.triggerEvent(me);
					} catch (final DeploymentException e) {
						// TODO think if error should be shown to the user
						FordiacLogHelper.logError("Could not trigger event for " + element.getQualifiedString(), e); //$NON-NLS-1$
					}
					notifyTriggerEvent(me.getPort());
				}
			}

		}
	}

	public void writeValue(final MonitoringElement element, String value) {
		final AutomationSystem automationSystem = element.getPort().getSystem();

		if (automationSystem == null) {
			showSystemNotFoundErrorMsg(element);
			return;
		}
		final Device device = element.getPort().getDevice();
		if (device == null) {
			showDeviceNotFounderroMsg(element);
			return;
		}
		final IInterfaceElement interfaceElement = element.getPort().getInterfaceElement();
		if (interfaceElement instanceof final VarDeclaration varDecl) {
			try {
				value = VariableOperations.newVariable(varDecl, value).toString(false);
			} catch (final Exception e) {
				showInvalidValueErrorMsg(element, value);
				return;
			}
		}

		final SystemMonitoringData data = getSystemMonitoringData(automationSystem);
		final IDeviceManagementInteractor devMgmInteractor = data.getDevMgmInteractor(device);

		if (devMgmInteractor != null) {
			writeElements(element, value, devMgmInteractor);
		}
	}

	public static void writeElements(final MonitoringElement element, final String value,
			final IDeviceManagementInteractor devMgmInteractor) {
		final List<MonitoringElement> elements = new ArrayList<>();

		if (element instanceof SubappMonitoringElement) {
			handleSubappPinWrite(element, elements);
		} else {
			elements.add(element);
		}

		for (final MonitoringElement e : elements) {

			String fullName = e.getQualifiedString();
			fullName = fullName.substring(0, fullName.lastIndexOf('.')); // strip interface name
			fullName = fullName.substring(0, fullName.lastIndexOf('.') + 1); // strip fbName

			final FBDeploymentData d = new FBDeploymentData(fullName, e.getPort().getFb());
			try {
				devMgmInteractor.writeFBParameter(e.getPort().getResource(), value, d,
						(VarDeclaration) e.getPort().getInterfaceElement());
			} catch (final DeploymentException ex) {
				// TODO think if error should be shown to the user
				FordiacLogHelper.logError("Could not write value to " + e.getQualifiedString(), ex); //$NON-NLS-1$
			}
		}
	}

	public static void handleSubappPinWrite(final MonitoringElement element,
			final Collection<MonitoringElement> elements) {
		final List<MonitoringElement> findElements = SubAppPortHelper
				.findConnectedElements(element.getPort().getInterfaceElement());
		elements.addAll(findElements);
	}

	public void forceValue(final MonitoringElement element, final String value) {
		final AutomationSystem automationSystem = element.getPort().getSystem();

		if (automationSystem == null) {
			showSystemNotFoundErrorMsg(element);
			return;
		}
		final Device device = element.getPort().getDevice();
		if (device == null) {
			showDeviceNotFounderroMsg(element);
			return;
		}

		final List<MonitoringElement> elements = new ArrayList<>();
		if (element instanceof SubappMonitoringElement) {
			handleSubappPinWrite(element, elements);
		} else {
			elements.add(element);
		}

		for (final MonitoringElement e : elements) {
			e.forceValue(value);
			final IDeviceManagementInteractor devMgmInteractor = getSystemMonitoringData(automationSystem)
					.getDevMgmInteractor(device);

			if (devMgmInteractor != null) {
				try {
					if (e.isForce()) {
						devMgmInteractor.forceValue(e, value);
					} else {
						devMgmInteractor.clearForce(e);
					}
				} catch (final DeploymentException ex) {
					// TODO think if error should be shown to the user
					FordiacLogHelper.logError("Could not force value of " + e.getQualifiedString() + "to " + value, ex); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}
	}

	public SystemMonitoringData getSystemMonitoringData(final AutomationSystem system) {
		return systemMonitoringData.computeIfAbsent(system, SystemMonitoringData::new);
	}

	private static void showDeviceNotFounderroMsg(final MonitoringElement element) {
		MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", //$NON-NLS-1$
				"Device could not be found for FB port: " + element.getPort() + "."); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static void showSystemNotFoundErrorMsg(final MonitoringElement element) {
		MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", //$NON-NLS-1$
				"System could not be found for FB port: " + element.getPort() + "."); //$NON-NLS-1$//$NON-NLS-2$
	}

	private static void showInvalidValueErrorMsg(final MonitoringElement element, final String value) {
		ErrorMessenger.popUpErrorMessage(MessageFormat.format(Messages.MonitoringManager_InvalidValue,
				element.getPort().getInterfaceElement().getQualifiedName(), value));
	}
}
