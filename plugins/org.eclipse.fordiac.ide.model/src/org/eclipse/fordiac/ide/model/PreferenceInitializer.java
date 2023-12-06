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
package org.eclipse.fordiac.ide.model;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				"org.eclipse.fordiac.ide.model");//$NON-NLS-1$
		store.setDefault(PreferenceConstants.P_ALLOCATION_SIZE, PreferenceConstants.P_ALLOCATION_SIZE_DEFAULT_VALUE);

		store.setDefault(PreferenceConstants.MARGIN_TOP_BOTTOM, PreferenceConstants.MARGIN_TOP_BOTTOM_DEFAULT_VALUE);
		store.setDefault(PreferenceConstants.MARGIN_LEFT_RIGHT, PreferenceConstants.MARGIN_LEFT_RIGHT_DEFAULT_VALUE);
	}
}
