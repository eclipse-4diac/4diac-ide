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

/**
 * Constant definitions.
 */
public final class PreferenceConstants {

	public static final String EXPORT_PREFERENCES_ID = "org.eclipse.fordiac.ide.export"; //$NON-NLS-1$

	private PreferenceConstants() {
		/* Util class shall not have a public ctor */
	}

	/** The Constant for the Compare Editor Preference. */
	public static final String P_COMPARE_EDITOR = "compareeditor"; //$NON-NLS-1$

	/** The Constant for the Type Export Preferences. */
	public static final String ENABLE_TYPE_EXPORT = "ENABLE_TYPE_EXPORT"; //$NON-NLS-1$
	public static final String OUTPUT_FOLDER = "OUTPUT_FOLDER"; //$NON-NLS-1$
	public static final String EXPORT_FILTER_ID = "EXPORT_FILTER_ID"; //$NON-NLS-1$

}
