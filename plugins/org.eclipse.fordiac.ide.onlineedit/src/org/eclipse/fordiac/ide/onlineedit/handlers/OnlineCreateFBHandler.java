/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.onlineedit.handlers;

import org.eclipse.fordiac.ide.deployment.IDeploymentExecutor;

public class OnlineCreateFBHandler extends AbstractOnlineFBHandler {

	@Override
	protected void executeCommand(IDeploymentExecutor executor) throws Exception {
		try {
			executor.createFBInstance(resFB, resource);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override	
	protected String getErrorMessageHeader() {
		return "Online Create Function Block Error";
	}

}
