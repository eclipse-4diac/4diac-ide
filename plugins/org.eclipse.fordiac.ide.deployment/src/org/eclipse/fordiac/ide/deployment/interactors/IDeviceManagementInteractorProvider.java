/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.interactors;

import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

/** Interface for classes that provide device mangement interactors 
 * 
 */
public interface IDeviceManagementInteractorProvider {
	

	/** Check if the interactor supports a given profile
	 * 
	 * @param profile the profile name
	 * 
	 * @return true, if successful
	 */
	public boolean supports(String profile);
	
	
	public String getProfileName();
	
	/** Create an instance of the device managment interactor this provider is providing
	 * 
	 * @param dev				the device for which this interactor should be created
	 * @param overrideHandler   if not null a device comm handler for overriding the default. This is mainly used for bootfile generation.
	 * @return
	 */
    IDeviceManagementInteractor createInteractor(Device dev, AbstractDeviceManagementCommunicationHandler overrideHandler);
	
}
