/*******************************************************************************
 * Copyright (c) 2012, 2015, 2016, 2018 Profactor GmbH, fortiss GmbH, Johannes
 *                                      Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.monitoringbase;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.deployment.Activator;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;

public abstract class AbstractMonitoringManager {
	
	private static AbstractMonitoringManager monitoringManager = null;
	
	private static final AbstractMonitoringManager dummyMonitoringManager = new AbstractMonitoringManager() {
		
		@Override
		public void enableSystem(AutomationSystem system) {
			//in the dummy manager we don't do anything here
		}
		
		@Override
		public void disableSystem(AutomationSystem system) {
			//in the dummy manager we don't do anything here			
		}
	};
	
	public static AbstractMonitoringManager getMonitoringManager() {
		if(null == monitoringManager) {
			monitoringManager = createMonitoringManager();
		}
		return monitoringManager;
	}
	
	private static AbstractMonitoringManager createMonitoringManager() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				Activator.PLUGIN_ID, "monitoringmanager"); //$NON-NLS-1$
		for (IConfigurationElement element : elems) {
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof AbstractMonitoringManager) {
					return (AbstractMonitoringManager)object;
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError("error in creating monitoring manager", corex);
			}
		}
		Activator.getDefault().logError("No monitoring manager provided");
		return dummyMonitoringManager;
	}
	

	protected final Breakpoints breakpoints = MonitoringBaseFactory.eINSTANCE
			.createBreakpoints();

	/** The monitoring listeners. */
	protected final List<IMonitoringListener> monitoringListeners = new ArrayList<>();

	/**
	 * Register IMonitoringListener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void registerMonitoringListener(IMonitoringListener listener) {
		if (!monitoringListeners.contains(listener)) {
			monitoringListeners.add(listener);
		}
	}


	public void addBreakpointsAdapter(EContentAdapter adapter) {
		if (!breakpoints.eAdapters().contains(adapter)) {
			breakpoints.eAdapters().add(adapter);
		}
	}

	public void removeBreakpointsAdapter(EContentAdapter adapter) {
		breakpoints.eAdapters().remove(adapter);
	}

	List<IMonitoringListener> watchesAdapter = new ArrayList<>();
	
	public void addWatchesAdapter(IMonitoringListener adapter) {
		if (!watchesAdapter.contains(adapter)) {
			watchesAdapter.add(adapter);
		}
	}

	public void removeWatchesAdapter(IMonitoringListener adapter) {
		watchesAdapter.remove(adapter);
	}
	
	public void notifyWatchesAdapterPortAdded(PortElement port) {
		for (IMonitoringListener adapter : watchesAdapter) {
			adapter.notifyAddPort(port);
		}
	}
	public void notifyWatchesAdapterPortRemoved(PortElement port) {
		for (IMonitoringListener adapter : watchesAdapter) {
			adapter.notifyRemovePort(port);
		}
	}
	
	public abstract void disableSystem(AutomationSystem system);
	
	public abstract void enableSystem(AutomationSystem system);
}
