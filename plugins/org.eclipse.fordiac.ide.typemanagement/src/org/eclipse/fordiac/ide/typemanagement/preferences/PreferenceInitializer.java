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
import org.eclipse.fordiac.ide.typemanagement.Activator;
import org.eclipse.jface.preference.IPreferenceStore;

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
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_STANDARD, "");//$NON-NLS-1$
		store.setDefault(PreferenceConstants.P_CLASSIFICATION, ""); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.P_APPLICATION_DOMAIN, ""); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.P_FUNCTION, ""); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.P_TYPE, ""); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.P_DESCRIPTION, ""); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.P_VERSION, "1.0"); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.P_ORGANIZATION, ""); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.P_AUTHOR, System.getProperty("user.name")); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.P_REMARKS, ""); //$NON-NLS-1$
	}

}
