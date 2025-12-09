/*******************************************************************************
 * Copyright (c) 2009 Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.preferences;

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
				.getNode(PreferenceConstants.EXPORT_PREFERENCES_ID);
		preferences.put(PreferenceConstants.P_COMPARE_EDITOR, "None"); //$NON-NLS-1$

		preferences.putBoolean(PreferenceConstants.ENABLE_TYPE_EXPORT, false);
		preferences.put(PreferenceConstants.OUTPUT_FOLDER, ""); //$NON-NLS-1$
		preferences.put(PreferenceConstants.EXPORT_FILTER_ID, ""); //$NON-NLS-1$
	}

}
