/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, fortiss GmbH, 
 * 							 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.deployment.Activator;
import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

/** Factory for loading DeviceManagementInteractors and handle related data (e.g., profile names)
 * 
 */
public enum DeviceManagementInteractorFactory {
	
	INSTANCE;
	
	private List<IDeviceManagementInteractorProvider> deviceManagementInteractorProviders = null;
	
	/**
	 * Gets the device managment interactor.
	 * 
	 * @param device
	 *            the device for which a deployment executor should be get
	 * @param overrideComHandler
	 *            if not null this com handler will be given to the executor
	 * 
	 * @return the deployment executor
	 */
	public IDeviceManagementInteractor getDeviceManagementInteractor(final Device device,
			final IDeviceManagementCommunicationHandler overrideComHandler, String profile) {
		String profileToUse = (null != profile) ? profile : device.getProfile();
		for (IDeviceManagementInteractorProvider idepExec : getDeviceManagementInteractorList()) {
			if (idepExec.supports(profileToUse)) {
				return idepExec.createInteractor(device, overrideComHandler);
			}
		}
		return null;
	}

	public IDeviceManagementInteractor getDeviceManagementInteractor(final Device device) {
		return getDeviceManagementInteractor(device, null, null);
	}
	
	/** Get a list of names of the profiles supported by the registered device managment interactors
	 * 
	 * @return list of strings with the names of the profiles 
	 */
	public List<String> getAvailableProfileNames() {
		return getDeviceManagementInteractorList().stream().map(interactor -> interactor.getProfileName())
				.collect(Collectors.toList());
	}
	
	private List<IDeviceManagementInteractorProvider> getDeviceManagementInteractorList(){
		if (null == deviceManagementInteractorProviders) {
			deviceManagementInteractorProviders = loadDeviceManagmentInteractors();
		}
		return deviceManagementInteractorProviders;
	}

	private static List<IDeviceManagementInteractorProvider> loadDeviceManagmentInteractors() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] elems = registry.getConfigurationElementsFor(
				Activator.PLUGIN_ID, "devicemanagementinteractor"); //$NON-NLS-1$
		ArrayList<IDeviceManagementInteractorProvider> interactors = new ArrayList<>(elems.length);
		for (IConfigurationElement element : elems) {
			try {
				Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof IDeviceManagementInteractorProvider) {
					interactors.add((IDeviceManagementInteractorProvider) object);
				}
			} catch (CoreException corex) {
				Activator.getDefault().logError(Messages.DeploymentCoordinator_ERROR_Message, corex);
			}
		}
		return interactors;
	}	
}
