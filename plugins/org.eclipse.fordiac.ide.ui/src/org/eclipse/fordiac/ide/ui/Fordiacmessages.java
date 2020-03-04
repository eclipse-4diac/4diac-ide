/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *   Alois Zoitl - moved preference messages from utils plugin
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class Fordiacmessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.ui.messages"; //$NON-NLS-1$

	public static String Algorithm;

	/** The Directory chooser control_ labe l_ browse. */
	public static String DirectoryChooserControl_LABEL_Browse;

	/** The Directory chooser control_ labe l_ choose directory. */
	public static String DirectoryChooserControl_LABEL_ChooseDirectory;

	/** The Directory chooser control_ labe l_ selectd directory dialog message. */
	public static String DirectoryChooserControl_LABEL_SelectdDirectoryDialogMessage;

	public static String FordiacPreferencePage_LABEL_DefaultComplianceProfile;

	public static String FordiacPreferencePage_LABEL_DefaultEventConnectorColor;

	public static String FordiacPreferencePage_LABEL_BoolConnectorColor;
	public static String FordiacPreferencePage_LABEL_AnyBitConnectorColor;
	public static String FordiacPreferencePage_LABEL_AnyIntConnectorColor;
	public static String FordiacPreferencePage_LABEL_AnyRealConnectorColor;
	public static String FordiacPreferencePage_LABEL_AnyStringConnectorColor;
	public static String FordiacPreferencePage_LABEL_DataConnectorColor;

	public static String FordiacPreferencePage_LABEL_DefaultAdapterConnectorColor;

	public static String FordiacPreferencePage_LABEL_PreferencePageDescription;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Fordiacmessages.class);
	}

	private Fordiacmessages() {
		// private empty constructor
	}
}
