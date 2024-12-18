/*******************************************************************************
 * Copyright (c) 2011, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ingo Hegny, Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr, Lisa Sonnleithner - updated color scheme
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = FBTypeEditorPreferenceConstants.STORE;

		/* blue */
		PreferenceConverter.setDefault(store, FBTypeEditorPreferenceConstants.P_ECC_STATE_COLOR,
				new RGB(184, 182, 206));
		PreferenceConverter.setDefault(store, FBTypeEditorPreferenceConstants.P_ECC_STATE_TEXT_COLOR,
				new RGB(21, 17, 72));
		PreferenceConverter.setDefault(store, FBTypeEditorPreferenceConstants.P_ECC_TRANSITION_COLOR,
				new RGB(68, 64, 121));

		/* yellow */
		PreferenceConverter.setDefault(store, FBTypeEditorPreferenceConstants.P_ECC_ALGORITHM_COLOR,
				new RGB(235, 242, 179));
		PreferenceConverter.setDefault(store, FBTypeEditorPreferenceConstants.P_ECC_ALGORITHM_TEXT_COLOR,
				new RGB(91, 101, 11));

		/* red */
		PreferenceConverter.setDefault(store, FBTypeEditorPreferenceConstants.P_ECC_EVENT_COLOR,
				new RGB(245, 181, 183));
		PreferenceConverter.setDefault(store, FBTypeEditorPreferenceConstants.P_ECC_EVENT_TEXT_COLOR,
				new RGB(141, 45, 47));
	}

}
