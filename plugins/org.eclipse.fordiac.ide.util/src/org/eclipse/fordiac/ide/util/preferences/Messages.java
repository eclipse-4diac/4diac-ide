/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015, 2017 Profactor GbmH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.preferences;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.util.preferences.messages"; //$NON-NLS-1$	
	public static String FordiacPreferencePage_LABEL_DefaultBasicFBBackgroundColor;
	public static String FordiacPreferencePage_LABEL_DefaultComplianceProfile;
	public static String FordiacPreferencePage_LABEL_DefaultCompositeFBBackgroundColor;
	public static String FordiacPreferencePage_LABEL_DefaultDataConnectorColor;	
	public static String FordiacPreferencePage_LABEL_DefaultAdapterConnectorColor;
	public static String FordiacPreferencePage_LABEL_SyncFBColorWithDeviceColor;
	public static String FordiacPreferencePage_LABEL_DefaultEventConnectorColor;
	public static String FordiacPreferencePage_LABEL_DefaultSIFBBackgroundColor;
	public static String FordiacPreferencePage_LABEL_PreferencePageDescription;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// private empty constructor
	}
}
