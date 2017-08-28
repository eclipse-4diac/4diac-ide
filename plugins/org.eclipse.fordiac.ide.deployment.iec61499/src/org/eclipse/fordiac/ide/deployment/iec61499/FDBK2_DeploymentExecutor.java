/*******************************************************************************
 * Copyright (c) 2014 fortiss GmbH
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
package org.eclipse.fordiac.ide.deployment.iec61499;

public class FDBK2_DeploymentExecutor extends DeploymentExecutor {
	private static final String PROFILE_NAME = "FBDK2"; //$NON-NLS-1$	
	
	public FDBK2_DeploymentExecutor() {
		super();
	}
	
	@Override
	public String getProfileName(){
		return PROFILE_NAME;
	}

	@Override
	protected String getWriteParameterMessage() {
		return Messages.FBDK2_WriteParameter;
	}
}
