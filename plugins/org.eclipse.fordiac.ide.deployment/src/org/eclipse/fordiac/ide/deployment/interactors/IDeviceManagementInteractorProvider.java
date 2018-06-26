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
	

    IDeviceManagementInteractor createInteractor(AbstractDeviceManagementCommunicationHandler commHandler);
	
}
