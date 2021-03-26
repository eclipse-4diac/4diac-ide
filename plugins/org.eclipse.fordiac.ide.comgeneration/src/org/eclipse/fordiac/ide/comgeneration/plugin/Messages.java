/*******************************************************************************
 * Copyright (c) 2019 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.comgeneration.plugin;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.comgeneration.plugin.messages"; //$NON-NLS-1$

	public static String CommFBGenerator_NoSelectionFor;

	public static String CommFBGenerator_NoGeneratorForProtocol;

	public static String CommFBGenerator_NoStartFBInResource;

	public static String ProtocolSelector_NoConnectionAvailable;

	public static String CommGenerator_FBTypePaletteEntryNotFound;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
