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

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;

/**
 * Constant definitions for plug-in preferences
 */
public final class FBTypeEditorPreferenceConstants {

	public static final String FBTYPEEDITOR_ECC_PREFERENCES_ID = "org.eclipse.fordiac.ide.fbtypeeditor.ecc"; //$NON-NLS-1$

	/** The Constant P_ECC_STATE_COLOR. */
	public static final String P_ECC_STATE_COLOR = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.ECCEditorStateColor"; //$NON-NLS-1$

	/** The Constant P_ECC_STATE_BORDER_COLOR. */
	public static final String P_ECC_STATE_TEXT_COLOR = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.ECCEditorStateTextColor"; //$NON-NLS-1$

	/** The Constant P_ECC_TRANSITION_COLOR. */
	public static final String P_ECC_TRANSITION_COLOR = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.ECCEditorTransitionColor"; //$NON-NLS-1$

	/** The Constant P_ECC_ALGORITHM_COLOR. */
	public static final String P_ECC_ALGORITHM_COLOR = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.ECCEditorAlgorithmColor"; //$NON-NLS-1$

	/** The Constant P_ECC_ALGORITHM_BORDER_COLOR. */
	public static final String P_ECC_ALGORITHM_TEXT_COLOR = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.ECCEditorAlgorithmTextColor"; //$NON-NLS-1$

	/** The Constant P_ECC_EVENT_COLOR. */
	public static final String P_ECC_EVENT_COLOR = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.ECCEditorEventColor"; //$NON-NLS-1$

	/** The Constant P_ECC_EVENT_BORDER_COLOR. */
	public static final String P_ECC_EVENT_TEXT_COLOR = "org.eclipse.fordiac.ide.fbtypeeditor.ecc.ECCEditorEventTextColor"; //$NON-NLS-1$

	/** The margin of state/action labels to create rectangles */
	public static final int MARGIN_VERTICAL = 3;
	public static final int MARGIN_HORIZONTAL = 2 * MARGIN_VERTICAL;

	private FBTypeEditorPreferenceConstants() {
		throw new UnsupportedOperationException("PreferenceConstants utility class should not be instantiated!"); //$NON-NLS-1$
	}

	public static Color getEccStateColor() {
		return JFaceResources.getColorRegistry().get(P_ECC_STATE_COLOR);
	}

	public static Color getEccStateTextColor() {
		return JFaceResources.getColorRegistry().get(P_ECC_STATE_TEXT_COLOR);
	}

	public static Color getEccTransitionColor() {
		return JFaceResources.getColorRegistry().get(P_ECC_TRANSITION_COLOR);
	}

	public static Color getEccAlgorithmColor() {
		return JFaceResources.getColorRegistry().get(P_ECC_ALGORITHM_COLOR);
	}

	public static Color getEccAlgorithmTextColor() {
		return JFaceResources.getColorRegistry().get(P_ECC_ALGORITHM_TEXT_COLOR);
	}

	public static Color getEccEventColor() {
		return JFaceResources.getColorRegistry().get(P_ECC_EVENT_COLOR);
	}

	public static Color getEccEventTextColor() {
		return JFaceResources.getColorRegistry().get(P_ECC_EVENT_TEXT_COLOR);
	}
}
