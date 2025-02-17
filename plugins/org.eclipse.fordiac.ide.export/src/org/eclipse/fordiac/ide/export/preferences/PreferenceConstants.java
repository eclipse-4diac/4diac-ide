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

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * Constant definitions.
 */
public final class PreferenceConstants {

	public static final String EXPORT_PREFERENCES_ID = "org.eclipse.fordiac.ide.export"; //$NON-NLS-1$

	public static final IPreferenceStore STORE = new ScopedPreferenceStore(InstanceScope.INSTANCE,
			EXPORT_PREFERENCES_ID);

	private PreferenceConstants() {
		/* Util class shall not have a public ctor */
	}

	/** The Constant for the Compare Editor Preference. */
	public static final String P_COMPARE_EDITOR = "compareeditor"; //$NON-NLS-1$

}
