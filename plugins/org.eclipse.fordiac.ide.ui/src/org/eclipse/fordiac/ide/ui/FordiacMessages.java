/*******************************************************************************
 * Copyright (c) 2008 Profactor GmbH
 * 				 2019 Johannes Kepler University Linz
 * 				 2020 Andrea Zoitl
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
 *   Alois Zoitl 
 *     - moved preference messages from utils plugin
 *   Andrea Zoitl 
 *     - Externalized translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class FordiacMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.ui.messages"; //$NON-NLS-1$

	public static String Algorithm;
	public static String ArraySize;
	public static String Comment;
	public static String DataType;
	public static String Event;
	public static String Name;
	public static String InitialValue;
	public static String Interface;
	public static String Type;
	public static String Variable;
	public static String With;

	public static String DirectoryChooserControl_LABEL_Browse;
	public static String DirectoryChooserControl_LABEL_ChooseDirectory;
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
		NLS.initializeMessages(BUNDLE_NAME, FordiacMessages.class);
	}

	private FordiacMessages() {
		// private empty constructor
	}
}
