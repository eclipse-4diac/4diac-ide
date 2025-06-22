/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

/** Class used to initialize default preference values. */
public class ElkPreferenceInitializer extends AbstractPreferenceInitializer {

	public static final int CONNECTION_LAYOUT_TIMEOUT_DEFAULT_VALUE = 10000;

	@Override
	public void initializeDefaultPreferences() {
		final IEclipsePreferences preferences = DefaultScope.INSTANCE.getNode(ElkPreferences.ELK_PREFERENCES_ID);

		preferences.putInt(ElkPreferences.CONNECTION_LAYOUT_TIMEOUT, CONNECTION_LAYOUT_TIMEOUT_DEFAULT_VALUE);

	}
}
