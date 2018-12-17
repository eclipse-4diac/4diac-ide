/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.preferences;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.typemanagement.preferences.messages"; //$NON-NLS-1$
	public static String typeManagementPreferencePageTitle;
	public static String typeManagementPreferencePageIdentificationTitle;
	public static String typeManagementPreferencePageVersionTitle;
	public static String typeManagementPreferencePageDescription;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
