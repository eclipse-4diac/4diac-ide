/*******************************************************************************
 * Copyright (c) 2008, 2009, 2015, 2019 Profactor GbmH, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Thomas Strasser, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Jose Cabral
 *     - Add strings for the virtualDNS view
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.systemconfiguration.messages"; //$NON-NLS-1$
	
	/** The Device create command_ labe l_ create device. */
	public static String DeviceCreateCommand_LABEL_CreateDevice;
		
	/** The Device edit part_ labe l_ not defined. */
	public static String DeviceEditPart_LABEL_NotDefined;

	public static String OpenSystemConfEditorAction_Name;
	
	/** The System conf palette factory_ labe l_ tools. */
	public static String SystemConfPaletteFactory_LABEL_Tools;
	
	
	public static String virtualDNSConfigurationTitle;
	public static String virtualDNSNewConfiguration;
	public static String virtualDNSDeleteConfiguration;
	public static String virtualDNSNewConfigTitle;
	public static String virtualDNSNewConfigMessage;
	public static String virtualDNSNewConfigDefaultName;
	public static String virtualDNSValueNotSet;
	public static String virtualDNSNewEntryButtonText;
	public static String virtualDNSDeleteEntryButtonText;
	public static String virtualDNSActiveConfigurationText;
	public static String virtualDNSFirstColumnTitle;
	public static String virtualDNSSecondColumnTitle;
	public static String virtualDNSSameVariableNameError;
	public static String virtualDNSEmptyNameError;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
