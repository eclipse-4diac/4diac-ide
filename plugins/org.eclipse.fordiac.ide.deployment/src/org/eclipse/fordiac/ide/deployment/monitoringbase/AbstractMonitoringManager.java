/*******************************************************************************
 * Copyright (c) 2012, 2015, 2016, 2018 Profactor GmbH, fortiss GmbH, Johannes
 *                                      Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.monitoringbase;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.deployment.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public abstract class AbstractMonitoringManager {

	public static final String PLUGIN_ID = "org.eclipse.fordiac.ide.deployment"; //$NON-NLS-1$
	private static AbstractMonitoringManager monitoringManager = null;

	private static final AbstractMonitoringManager dummyMonitoringManager = new AbstractMonitoringManager() {

		@Override
		public void enableSystem(final AutomationSystem system) {
			// in the dummy manager we don't do anything here
		}

		@Override
		public void enableSystemSynch(final AutomationSystem system, final IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			// in the dummy manager we don't do anything here
		}

		@Override
		public void disableSystem(final AutomationSystem system) {
			// in the dummy manager we don't do anything here
		}

		@Override
		public void disableSystemSynch(final AutomationSystem system, final IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			// in the dummy manager we don't do anything here
		}

		@Override
		public boolean isSystemMonitored(final AutomationSystem system) {
			return false;
		}
	};

	public static AbstractMonitoringManager getMonitoringManager() {
		if (null == monitoringManager) {
			monitoringManager = createMonitoringManager();
		}
		return monitoringManager;
	}

	private static AbstractMonitoringManager createMonitoringManager() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry.getConfigurationElementsFor(PLUGIN_ID, "monitoringmanager"); //$NON-NLS-1$
		for (final IConfigurationElement element : elems) {
			try {
				final Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof final AbstractMonitoringManager abstractMonitoringManager) {
					return abstractMonitoringManager;
				}
			} catch (final CoreException corex) {
				FordiacLogHelper.logError(Messages.AbstractMonitoringManager_ErrorInCreatingMonitoringManager, corex);
			}
		}
		FordiacLogHelper.logError(Messages.AbstractMonitoringManager_NoMonitoringManagerProvided);
		return dummyMonitoringManager;
	}

	/** The monitoring listeners. */
	private final List<IMonitoringListener> monitoringListeners = new ArrayList<>();

	/**
	 * Register IMonitoringListener.
	 *
	 * @param listener the listener
	 */
	public void registerMonitoringListener(final IMonitoringListener listener) {
		if (!monitoringListeners.contains(listener)) {
			monitoringListeners.add(listener);
		}
	}

	public void deRegisterMonitoringListener(final IMonitoringListener adapter) {
		monitoringListeners.remove(adapter);
	}

	/**
	 * Notify add port.
	 *
	 * @param port the port
	 */
	public void notifyAddPort(final PortElement port) {
		for (final IMonitoringListener monitoringListener : monitoringListeners) {
			monitoringListener.notifyAddPort(port);
		}
	}

	/**
	 * Notify remove port.
	 *
	 * @param port the port
	 */
	public void notifyRemovePort(final PortElement port) {
		for (final IMonitoringListener monitoringListener : monitoringListeners) {
			monitoringListener.notifyRemovePort(port);
		}
	}

	/**
	 * Notify trigger event.
	 *
	 * @param port the port
	 */
	public void notifyTriggerEvent(final PortElement port) {
		for (final IMonitoringListener monitoringListener : monitoringListeners) {
			monitoringListener.notifyTriggerEvent(port);
		}
	}

	public void notifyWatchesChanged() {
		for (final IMonitoringListener adapter : monitoringListeners) {
			adapter.notifyWatchesChanged();
		}
	}

	public abstract void disableSystem(AutomationSystem system);

	public abstract void disableSystemSynch(AutomationSystem system, IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException;

	public abstract void enableSystem(AutomationSystem system);

	public abstract void enableSystemSynch(AutomationSystem system, IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException;

	public abstract boolean isSystemMonitored(AutomationSystem system);
}
