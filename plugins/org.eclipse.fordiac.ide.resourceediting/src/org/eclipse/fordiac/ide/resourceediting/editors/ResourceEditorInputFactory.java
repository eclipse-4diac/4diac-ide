/*******************************************************************************
 * Copyright (c) 2013, 2015 - 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.resourceediting.editors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.util.AbstractUntypedEditorInputFactory;
import org.eclipse.ui.IMemento;

public class ResourceEditorInputFactory extends AbstractUntypedEditorInputFactory {

	/**
	 * Factory id. The workbench plug-in registers a factory by this name with the
	 * "org.eclipse.ui.elementFactories" extension point.
	 */
	private static final String ID_FACTORY = "org.eclipse.fordiac.ide.resourceediting.editors.ResourceEditorInputFactory"; //$NON-NLS-1$

	/**
	 * Tag for the device name.
	 */
	private static final String TAG_DEVICE = "DEVICE"; //$NON-NLS-1$

	/**
	 * Tag for the resource name.
	 */
	private static final String TAG_RESORUCE = "RESOURCE"; //$NON-NLS-1$

	@Override
	public IAdaptable createElement(IMemento memento) {
		String systemName = loadAutomationSystemName(memento);
		String deviceName = memento.getString(TAG_DEVICE);
		String resourceName = memento.getString(TAG_RESORUCE);

		if ((null != systemName) && (null != deviceName) && (null != resourceName)) {
			AutomationSystem system = SystemManager.INSTANCE.getSystemForName(systemName);
			if (null != system) {
				org.eclipse.fordiac.ide.model.libraryElement.Device device = system.getDeviceNamed(deviceName);
				if (null != device) {
					Resource res = device.getResourceNamed(resourceName);
					if (null != res) {
						return new ResourceEditorInput(res);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Returns the element factory id for this class.
	 * 
	 * @return the element factory id
	 */
	public static String getFactoryId() {
		return ID_FACTORY;
	}

	/**
	 * Saves the state of the given file editor input into the given memento.
	 *
	 * @param memento the storage area for element state
	 * @param input   the application editor input
	 */
	public static void saveState(IMemento memento, ResourceEditorInput input) {
		if (null != input.getContent().getDevice()) {
			saveAutomationSystem(memento, input.getContent().getAutomationSystem());
			memento.putString(TAG_DEVICE, input.getContent().getDevice().getName());
			memento.putString(TAG_RESORUCE, input.getContent().getName());
		}
	}

}
