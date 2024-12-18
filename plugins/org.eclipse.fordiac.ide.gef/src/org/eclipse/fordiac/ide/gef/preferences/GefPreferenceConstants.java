/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class GefPreferenceConstants {
	public static final String GEF_PREFERENCES_ID = "org.eclipse.fordiac.ide.gef"; //$NON-NLS-1$

	public static final IPreferenceStore STORE = new ScopedPreferenceStore(InstanceScope.INSTANCE, GEF_PREFERENCES_ID);

	private GefPreferenceConstants() {
	}

	/** The Constant CORNER_DIM. */
	public static final int CORNER_DIM = 6;
	public static final int CORNER_DIM_HALF = CORNER_DIM / 2;

	public static final String SNAP_TO_GRID = "SnapToGrid"; //$NON-NLS-1$

	public static final String SHOW_GRID = "ShowGrid"; //$NON-NLS-1$

	public static final String SHOW_RULERS = "ShowRulers"; //$NON-NLS-1$

	public static final String PIN_LABEL_STYLE = "PinLabelStyle"; //$NON-NLS-1$
	public static final String PIN_LABEL_STYLE_PIN_NAME = "PinLabelStyle_PinName"; //$NON-NLS-1$
	public static final String PIN_LABEL_STYLE_PIN_COMMENT = "PinLabelStyle_PinComment"; //$NON-NLS-1$
	public static final String PIN_LABEL_STYLE_SRC_PIN_NAME = "PinLabelStyle_SourcePinName"; //$NON-NLS-1$

	public static final String MAX_VALUE_LABEL_SIZE = "MaxValueLabelSize"; //$NON-NLS-1$

	public static final String MIN_PIN_LABEL_SIZE = "MinPinLabelSize"; //$NON-NLS-1$
	public static final String MAX_PIN_LABEL_SIZE = "MaxPinLabelSize"; //$NON-NLS-1$

	public static final String MAX_INTERFACE_BAR_SIZE = "MaxInterfaceBarSize"; //$NON-NLS-1$
	public static final String MIN_INTERFACE_BAR_SIZE = "MinInterfaceBarSize"; //$NON-NLS-1$

	public static final String MAX_HIDDEN_CONNECTION_LABEL_SIZE = "MaxHiddenConnectionLabelSize"; //$NON-NLS-1$

	public static final String MAX_TYPE_LABEL_SIZE = "MaxTypeLabelSize"; //$NON-NLS-1$

	public static final String MAX_DEFAULT_VALUE_LENGTH = "MaxDefaultValueLength"; //$NON-NLS-1$

	public static final String CONNECTION_AUTO_LAYOUT = "ConnectionAutoLayout"; //$NON-NLS-1$

	public static final String EXPANDED_INTERFACE_OLD_DIRECT_BEHAVIOUR = "ExpandedInterfaceOldDirectBehaviour"; //$NON-NLS-1$
	public static final String EXPANDED_INTERFACE_EVENTS_TOP = "ExpandedInterfaceEventsTop"; //$NON-NLS-1$
}
