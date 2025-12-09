/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public static final int DEFAULT_MAX_INTERFACE_BAR_SIZE = 40;

	@Override
	public void initializeDefaultPreferences() {
		final IEclipsePreferences preferences = DefaultScope.INSTANCE
				.getNode(ModelPreferenceConstants.MODEL_PREFERENCES_ID);

		preferences.putInt(ModelPreferenceConstants.P_ALLOCATION_SIZE,
				ModelPreferenceConstants.P_ALLOCATION_SIZE_DEFAULT_VALUE);
		preferences.putInt(ModelPreferenceConstants.MARGIN_TOP_BOTTOM,
				ModelPreferenceConstants.MARGIN_TOP_BOTTOM_DEFAULT_VALUE);
		preferences.putInt(ModelPreferenceConstants.MARGIN_LEFT_RIGHT,
				ModelPreferenceConstants.MARGIN_LEFT_RIGHT_DEFAULT_VALUE);

		preferences.putInt(ModelPreferenceConstants.MAX_VALUE_LABEL_SIZE, 25); // big enough to fully show an IP address
																				// and port

		preferences.putInt(ModelPreferenceConstants.MAX_PIN_LABEL_SIZE, 12);
		preferences.putInt(ModelPreferenceConstants.MAX_INTERFACE_BAR_SIZE, DEFAULT_MAX_INTERFACE_BAR_SIZE);
		preferences.putInt(ModelPreferenceConstants.MIN_INTERFACE_BAR_SIZE, 0);
		preferences.putInt(ModelPreferenceConstants.MAX_HIDDEN_CONNECTION_LABEL_SIZE, 15);
		preferences.putInt(ModelPreferenceConstants.MAX_TYPE_LABEL_SIZE, 15);
	}
}
