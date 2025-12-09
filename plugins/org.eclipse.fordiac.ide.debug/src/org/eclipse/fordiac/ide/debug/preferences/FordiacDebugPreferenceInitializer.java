/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class FordiacDebugPreferenceInitializer extends AbstractPreferenceInitializer {

	public static final int VALUE_MAX_DISPLAY_LENGTH_DEFAULT_VALUE = 20;
	public static final int DEBUG_VIEW_VALUE_MAX_DISPLAY_LENGTH_DEFAULT_VALUE = 25;

	@Override
	public void initializeDefaultPreferences() {
		final IEclipsePreferences debugPreferencesNode = DefaultScope.INSTANCE
				.getNode(FordiacDebugPreferences.DEBUG_PREFERENCES_ID);
		debugPreferencesNode.putInt(FordiacDebugPreferences.VALUE_MAX_DISPLAY_LENGTH,
				VALUE_MAX_DISPLAY_LENGTH_DEFAULT_VALUE);
		debugPreferencesNode.putInt(FordiacDebugPreferences.DEBUG_VIEW_VALUE_MAX_DISPLAY_LENGTH,
				DEBUG_VIEW_VALUE_MAX_DISPLAY_LENGTH_DEFAULT_VALUE);
	}
}
