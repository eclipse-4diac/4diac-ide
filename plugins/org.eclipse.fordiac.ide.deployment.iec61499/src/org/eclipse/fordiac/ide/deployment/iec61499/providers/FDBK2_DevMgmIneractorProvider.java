/*******************************************************************************
 * Copyright (c) 2014, 2018 fortiss GmbH, Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.providers;

import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.iec61499.DeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.iec61499.Messages;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;

public class FDBK2_DevMgmIneractorProvider extends DefaultDevMgmInteractorProvider {
	private static final String PROFILE_NAME = "FBDK2"; //$NON-NLS-1$	
	
	@Override
	public String getProfileName(){
		return PROFILE_NAME;
	}
	
	@Override
	public IDeviceManagementInteractor createInteractor(AbstractDeviceManagementCommunicationHandler commHandler) {
		return new DeploymentExecutor(commHandler) {
			
			@Override
			protected String getWriteParameterMessage() {
				return Messages.FBDK2_WriteParameter;
			}			
		};
	}
}
