/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

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
		final IEclipsePreferences preferences = DefaultScope.INSTANCE
				.getNode(TypeManagementPreferenceConstants.TYPE_MANAGEMENT_PREFERENCES_ID);
		preferences.put(TypeManagementPreferenceConstants.P_STANDARD, "");//$NON-NLS-1$
		preferences.put(TypeManagementPreferenceConstants.P_CLASSIFICATION, ""); //$NON-NLS-1$
		preferences.put(TypeManagementPreferenceConstants.P_APPLICATION_DOMAIN, ""); //$NON-NLS-1$
		preferences.put(TypeManagementPreferenceConstants.P_FUNCTION, ""); //$NON-NLS-1$
		preferences.put(TypeManagementPreferenceConstants.P_TYPE, ""); //$NON-NLS-1$
		preferences.put(TypeManagementPreferenceConstants.P_DESCRIPTION, ""); //$NON-NLS-1$
		preferences.put(TypeManagementPreferenceConstants.P_VERSION, "1.0"); //$NON-NLS-1$
		preferences.put(TypeManagementPreferenceConstants.P_ORGANIZATION, ""); //$NON-NLS-1$
		preferences.put(TypeManagementPreferenceConstants.P_AUTHOR, System.getProperty("user.name")); //$NON-NLS-1$
		preferences.put(TypeManagementPreferenceConstants.P_REMARKS, ""); //$NON-NLS-1$
	}

}
