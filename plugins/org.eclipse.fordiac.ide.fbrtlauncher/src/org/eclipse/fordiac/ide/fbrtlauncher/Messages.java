/*******************************************************************************
 * Copyright (c) 2008 - 2010 Profactor GmbH
 * 				 2020		 Andrea Zoitl
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martijn Rooker, Gerhard Ebenhofer, Thomas Strasser
 *     - initial API and implementation and/or initial documentation
 *   Andrea Zoitl
 *     - externalized all translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbrtlauncher;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.fbrtlauncher.messages"; //$NON-NLS-1$

	/** The FBRT launcher_ error_ wrong port. */
	public static String FBRTLauncher_ERROR_WrongPort;

	/** The FBRT launcher_ label_ port param. */
	public static String FBRTLauncher_LABEL_PortParam;

	/** The FBRT launcher_ label_ device type param. */
	public static String FBRTLauncher_LABEL_DeviceTypeParam;

	/** The FBRT launcher_ label_ missing platform. */
	public static String FBRTLauncher_ERROR_MissingPlatform;

	/** The FBRT launcher_ label_ missing java vm. */
	public static String FBRTLauncher_ERROR_MissingJavaVM;

	public static String FBRTPreferencePage_FBRTLibrary;

	public static String FBRTPreferencePage_FBRTLocation;

	public static String FBRTPreferencePage_FBRTPreferencePage;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
