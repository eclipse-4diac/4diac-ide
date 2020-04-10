/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

/**
 * Interface for classes that provide device mangement interactors
 * 
 */
public interface IDeviceManagementInteractorProvider {

	/**
	 * Check if the interactor supports a given profile
	 * 
	 * @param profile the profile name
	 * 
	 * @return true, if successful
	 */
	boolean supports(String profile);

	String getProfileName();

	/**
	 * Create an instance of the device managment interactor this provider is
	 * providing
	 * 
	 * @param dev             the device for which this interactor should be created
	 * @param overrideHandler if not null a device comm handler for overriding the
	 *                        default. This is mainly used for bootfile generation.
	 * @return
	 */
	IDeviceManagementInteractor createInteractor(Device dev, IDeviceManagementCommunicationHandler overrideHandler);

}
