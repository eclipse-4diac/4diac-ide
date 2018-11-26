/*******************************************************************************
 * Copyright (c) 2012, 2017, 2018 Profactor GbmH, fortiss GmbH, 
 * 								  Johannes Kepler University
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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.fordiac.ide.monitoring.Activator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;


/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		PreferenceConverter.setDefault(store, PreferenceConstants.P_FORCE_COLOR, ColorConstants.orange.getRGB());
		PreferenceConverter.setDefault(store, PreferenceConstants.P_WATCH_COLOR, ColorConstants.yellow.getRGB());

		store.setDefault(PreferenceConstants.P_POLLING_INTERVAL, PreferenceConstants.P_POLLING_INTERVAL_DEVAULT_VALUE);
		store.setDefault(PreferenceConstants.P_MONITORING_TRANSPARENCY, PreferenceConstants.P_MONITORING_TRANSPARENCY_VALUE);
	}

}
