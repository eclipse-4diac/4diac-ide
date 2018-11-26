/*******************************************************************************
 * Copyright (c) 2012, 2017, 2018 Profactor GbmH, fortiss GmbH, 
 * 								  Johannes Keppler University	
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring     
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring.preferences;

import org.eclipse.fordiac.ide.monitoring.Activator;

/**
 * Constant definitions for plug-in preferences
 */
public final class PreferenceConstants {

	public static final String P_WATCH_COLOR = "watchColor"; //$NON-NLS-1$

	public static final String P_FORCE_COLOR = "forceColor"; //$NON-NLS-1$

	public static final String P_POLLING_INTERVAL = "pollingInterval"; //$NON-NLS-1$
	
	public static final int P_POLLING_INTERVAL_DEVAULT_VALUE = 300;
	
	public static final String P_MONITORING_TRANSPARENCY = "monitoringTransparency"; //$NON-NLS-1$
	
	public static final int P_MONITORING_TRANSPARENCY_VALUE = 190;
	
	public static int getPollingInterval(){
		int timeout = Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.P_POLLING_INTERVAL);
		if(0 == timeout){
			timeout = P_POLLING_INTERVAL_DEVAULT_VALUE;
		}		
		return timeout;
	}
	
	public static int getMonitoringTransparency(){
		int transparency = Activator.getDefault().getPreferenceStore().getInt(PreferenceConstants.P_MONITORING_TRANSPARENCY);
		if(0 == transparency){
			transparency = P_MONITORING_TRANSPARENCY_VALUE;
		}		
		return transparency;
	}
	
	private PreferenceConstants() {
		//class should not be instantiable
	}

}
