/*******************************************************************************
 * Copyright (c) 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.systemmanagement.messages"; //$NON-NLS-1$
	public static String AutomationSystemEditor_Title;
	public static String AutomationSystemEditor_Info;
	public static String AutomationSystemEditor_Discard_Changes;
	public static String AutomationSystemEditor_Save_Changes;
	public static String AutomationSystemEditor_Overwrite_Changes;
	public static String FordiacResourceChangeListener_0;
	public static String FordiacResourceChangeListener_1;
	public static String FordiacResourceChangeListener_2;
	public static String FordiacResourceChangeListener_3;
	public static String FordiacResourceChangeListener_4;
	public static String FordiacResourceChangeListener_5;
	public static String FordiacResourceChangeListener_7;
	public static String ValidateTypeLibrary;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
