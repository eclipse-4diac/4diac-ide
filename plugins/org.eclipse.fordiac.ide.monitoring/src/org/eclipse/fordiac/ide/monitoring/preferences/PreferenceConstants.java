/*******************************************************************************
 * Copyright (c) 2012, 2017 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.preferences;

import org.eclipse.fordiac.ide.monitoring.Activator;

/**
 * Constant definitions for plug-in preferences
 */
public class PreferenceConstants {

	public static final String P_WATCH_COLOR = "watchColor"; //$NON-NLS-1$

	public static final String P_FORCE_COLOR = "forceColor"; //$NON-NLS-1$

	static final String P_POLLING_INTERVAL = "pollingInterval"; //$NON-NLS-1$
	
	static final int P_POLLING_INTERVAL_DEVAULT_VALUE = 300;

	static final String P_RESPONSE_TIMEOUT = "responseTimeout"; //$NON-NLS-1$
	
	static final int P_RESPONSE_TIMEOUT_DEVAULT_VALUE = 3000;
	
	public static int getTimeOutValue(){
		int timeout = Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.P_RESPONSE_TIMEOUT);
		if(0 == timeout){
			timeout = P_RESPONSE_TIMEOUT_DEVAULT_VALUE;
		}		
		return timeout;
	}
	
	public static int getPollingInterval(){
		int timeout = Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.P_POLLING_INTERVAL);
		if(0 == timeout){
			timeout = P_POLLING_INTERVAL_DEVAULT_VALUE;
		}		
		return timeout;
	}

}
