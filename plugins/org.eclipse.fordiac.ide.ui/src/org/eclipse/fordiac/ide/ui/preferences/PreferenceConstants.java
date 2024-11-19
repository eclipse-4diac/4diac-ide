/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015, 2017 Profactor GbmH, fortiss GmbH
 * 				 2019, 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added diagram font preference
 *               - added separate colors for different data types
 *   Ernst Blecha - add allocation block size
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

/**
 * Constant definitions for plug-in preferences.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public final class PreferenceConstants {

	public static final String FORDIAC_UI_PREFERENCES_ID = "org.eclipse.fordiac.ide.ui"; //$NON-NLS-1$

	/** The Constant P_EVENT_CONNECTOR_COLOR. */
	public static final String P_EVENT_CONNECTOR_COLOR = "EventConnectionConnectorColor"; //$NON-NLS-1$

	public static final String P_BOOL_CONNECTOR_COLOR = "BoolConnectionConnectorColor"; //$NON-NLS-1$
	public static final String P_ANY_BIT_CONNECTOR_COLOR = "AnyBitConnectionConnectorColor"; //$NON-NLS-1$
	public static final String P_ANY_INT_CONNECTOR_COLOR = "AnyIntConnectionConnectorColor"; //$NON-NLS-1$
	public static final String P_ANY_REAL_CONNECTOR_COLOR = "AnyRealConnectionConnectorColor"; //$NON-NLS-1$
	public static final String P_ANY_STRING_CONNECTOR_COLOR = "AnyStringConnectionConnectorColor"; //$NON-NLS-1$
	public static final String P_REMAINING_DATA_CONNECTOR_COLOR = "DataConnectionConnectorColor";//$NON-NLS-1$

	/** The Constant P_ADAPTER_CONNECTOR_COLOR. */
	public static final String P_ADAPTER_CONNECTOR_COLOR = "AdapterConnectionConnectorColor";//$NON-NLS-1$

	/** The Constant P_DEFAULT_COMPLIANCE_PROFILE. */
	public static final String P_DEFAULT_COMPLIANCE_PROFILE = "P_DEFAULT_COMPLIANCE_PROFILE";//$NON-NLS-1$

	/** The Constant P_HIDE_EVENT_CON. */
	public static final String P_HIDE_EVENT_CON = "hideEventConnections";//$NON-NLS-1$

	/** The Constant P_HIDE_DATA_CON. */
	public static final String P_HIDE_DATA_CON = "hideDataConnections";//$NON-NLS-1$

	/** The Constant P_TOGGLE_JUMP_STEP. */
	public static final String P_TOGGLE_JUMP_STEP = "toggleJumpStep";//$NON-NLS-1$

	/**
	 * The font to be used on all 4diac IDE graphical editors and diagrams,
	 * currently defaults to the Text_Font
	 */
	public static final String DIAGRAM_FONT = "org.eclipse.fordiac.ide.preferences.diagramFontDefinition"; //$NON-NLS-1$

	public static boolean isDataConnectorProperty(final String property) {
		return P_BOOL_CONNECTOR_COLOR.equals(property) || P_ANY_BIT_CONNECTOR_COLOR.equals(property)
				|| P_ANY_INT_CONNECTOR_COLOR.equals(property) || P_ANY_REAL_CONNECTOR_COLOR.equals(property)
				|| P_ANY_STRING_CONNECTOR_COLOR.equals(property) || P_REMAINING_DATA_CONNECTOR_COLOR.equals(property);
	}

	/**
	 * Allocation block size used during xml file export.
	 *
	 * Additional constants defined in
	 * org.eclipse.fordiac.ide.model.PreferenceConstants
	 */
	public static final String P_ALLOCATION_SIZE = "allocationSize"; //$NON-NLS-1$
	public static final int P_ALLOCATION_SIZE_DEFAULT_VALUE = 100;
	public static final int P_ALLOCATION_SIZE_MIN_VALUE = 50;
	public static final int P_ALLOCATION_SIZE_MAX_VALUE = 1000;

	public static final String P_SHOW_ERRORS_AT_MOUSE_CURSOR = "ShowErrorsAtMouseCursor"; //$NON-NLS-1$
	public static final boolean P_SHOW_ERRORS_AT_MOUSE_CURSOR_DEFAULT_VALUE = true;

	public static final String P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX = "DeactivateCommentsTransferringDemuxToMux"; //$NON-NLS-1$
	public static final boolean P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX_DEFAULT_VALUE = false;

	private PreferenceConstants() {
		throw new UnsupportedOperationException();
	}

}
