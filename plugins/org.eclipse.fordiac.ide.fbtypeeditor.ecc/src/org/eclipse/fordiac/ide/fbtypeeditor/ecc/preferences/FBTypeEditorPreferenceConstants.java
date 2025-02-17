/*******************************************************************************
 * Copyright (c) 2011 Profactor GmbH, TU Wien ACIN
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ingo Hegny, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.ui.preferences.PreferenceGetter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * Constant definitions for plug-in preferences
 */
public final class FBTypeEditorPreferenceConstants {

	public static final String FBTYPEEDITOR_ECC_PREFERENCES_ID = "org.eclipse.fordiac.ide.fbtypeeditor.ecc"; //$NON-NLS-1$

	public static final IPreferenceStore STORE = new ScopedPreferenceStore(InstanceScope.INSTANCE,
			FBTYPEEDITOR_ECC_PREFERENCES_ID);

	/** The Constant P_ECC_STATE_COLOR. */
	public static final String P_ECC_STATE_COLOR = "ECCEditorStateColor"; //$NON-NLS-1$

	/** The Constant P_ECC_STATE_BORDER_COLOR. */
	public static final String P_ECC_STATE_TEXT_COLOR = "ECCEditorStateTextColor"; //$NON-NLS-1$

	/** The Constant P_ECC_TRANSITION_COLOR. */
	public static final String P_ECC_TRANSITION_COLOR = "ECCEditorTransitionColor"; //$NON-NLS-1$

	/** The Constant P_ECC_ALGORITHM_COLOR. */
	public static final String P_ECC_ALGORITHM_COLOR = "ECCEditorAlgorithmColor"; //$NON-NLS-1$

	/** The Constant P_ECC_ALGORITHM_BORDER_COLOR. */
	public static final String P_ECC_ALGORITHM_TEXT_COLOR = "ECCEditorAlgorithmTextColor"; //$NON-NLS-1$

	/** The Constant P_ECC_EVENT_COLOR. */
	public static final String P_ECC_EVENT_COLOR = "ECCEditorEventColor"; //$NON-NLS-1$

	/** The Constant P_ECC_EVENT_BORDER_COLOR. */
	public static final String P_ECC_EVENT_TEXT_COLOR = "ECCEditorEventTextColor"; //$NON-NLS-1$

	/** The margin of state/action labels to create rectangles */
	public static final int MARGIN_VERTICAL = 3;
	public static final int MARGIN_HORIZONTAL = 2 * MARGIN_VERTICAL;

	private FBTypeEditorPreferenceConstants() {
		throw new UnsupportedOperationException("PreferenceConstants utility class should not be instantiated!"); //$NON-NLS-1$
	}

	/**
	 * Returns the color for the specified preference.
	 *
	 * @param pref The preference.
	 *
	 * @return the color
	 */
	public static Color getColor(final String pref) {
		return PreferenceGetter.getColor(STORE, pref);
	}
}
