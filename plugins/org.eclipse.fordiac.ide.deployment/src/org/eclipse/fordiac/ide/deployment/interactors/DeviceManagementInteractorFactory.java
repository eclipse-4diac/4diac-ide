/*******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, fortiss GmbH,
 * 							 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/**
 * Factory for loading DeviceManagementInteractors and handle related data
 * (e.g., profile names)
 *
 */
public enum DeviceManagementInteractorFactory {

	INSTANCE;

	private static final String PLUGIN_ID = "org.eclipse.fordiac.ide.deployment"; //$NON-NLS-1$
	private List<IDeviceManagementInteractorProvider> deviceManagementInteractorProviders = null;

	/**
	 * Gets the device managment interactor.
	 *
	 * @param device             the device for which a deployment executor should
	 *                           be get
	 * @param overrideComHandler if not null this com handler will be given to the
	 *                           executor
	 *
	 * @return the deployment executor
	 */
	public IDeviceManagementInteractor getDeviceManagementInteractor(final Device device,
			final IDeviceManagementCommunicationHandler overrideComHandler, final String profile) {
		final String profileToUse = (null != profile) ? profile : device.getProfile();
		for (final IDeviceManagementInteractorProvider idepExec : getDeviceManagementInteractorList()) {
			if (idepExec.supports(profileToUse)) {
				return idepExec.createInteractor(device, overrideComHandler);
			}
		}
		return null;
	}

	public IDeviceManagementInteractor getDeviceManagementInteractor(final Device device) {
		return getDeviceManagementInteractor(device, null, null);
	}

	/**
	 * Get a list of names of the profiles supported by the registered device
	 * managment interactors
	 *
	 * @return list of strings with the names of the profiles
	 */
	public List<String> getAvailableProfileNames() {
		return getDeviceManagementInteractorList().stream().map(IDeviceManagementInteractorProvider::getProfileName)
				.toList();
	}

	private List<IDeviceManagementInteractorProvider> getDeviceManagementInteractorList() {
		if (null == deviceManagementInteractorProviders) {
			deviceManagementInteractorProviders = loadDeviceManagmentInteractors();
		}
		return deviceManagementInteractorProviders;
	}

	private static List<IDeviceManagementInteractorProvider> loadDeviceManagmentInteractors() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		final IConfigurationElement[] elems = registry.getConfigurationElementsFor(PLUGIN_ID,
				"devicemanagementinteractor"); //$NON-NLS-1$
		final ArrayList<IDeviceManagementInteractorProvider> interactors = new ArrayList<>(elems.length);
		for (final IConfigurationElement element : elems) {
			try {
				final Object object = element.createExecutableExtension("class"); //$NON-NLS-1$
				if (object instanceof final IDeviceManagementInteractorProvider iDeviceManagementInteractorProvider) {
					interactors.add(iDeviceManagementInteractorProvider);
				}
			} catch (final CoreException corex) {
				FordiacLogHelper.logError(Messages.DeploymentCoordinator_ERROR_Message, corex);
			}
		}
		return interactors;
	}
}
