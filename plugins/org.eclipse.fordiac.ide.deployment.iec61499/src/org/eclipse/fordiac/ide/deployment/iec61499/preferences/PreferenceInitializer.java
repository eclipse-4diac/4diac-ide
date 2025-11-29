/*******************************************************************************
 * Copyright (c) 2009, 2025 Profactor GmbH, fortiss GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst - add maximum request size
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		final IEclipsePreferences preferences = DefaultScope.INSTANCE
				.getNode(IEC61499PreferenceConstants.DEPLOYMENT_IEC61499_PREFERENCES_ID);
		preferences.putInt(IEC61499PreferenceConstants.P_CONNECTION_TIMEOUT,
				IEC61499PreferenceConstants.P_CONNECTION_TIMEOUT_DEFAULT);
		preferences.putInt(IEC61499PreferenceConstants.P_MAX_REQUEST_SIZE,
				IEC61499PreferenceConstants.P_MAX_REQUEST_SIZE_DEFAULT);

	}

}
