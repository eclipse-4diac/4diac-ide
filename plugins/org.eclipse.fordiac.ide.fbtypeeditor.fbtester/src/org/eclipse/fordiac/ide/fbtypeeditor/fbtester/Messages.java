/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.fbtypeeditor.fbtester.messages"; //$NON-NLS-1$
	public static String FBTester_CreateDataConnectionFailed;
	public static String FBTester_CreateEventConnectionFailed;
	public static String FBTester_CreateFBInstanceFailed;
	public static String FBTester_CreateResourceFailed;
	public static String FBTester_CreateResourceInstance;
	public static String FBTester_CreateFBInstance;
	public static String FBTester_CreateEventConnection;
	public static String FBTester_CreateDataConnection;
	public static String FBTester_StartingResourceFailed;
	public static String FBTester_WriteFBParameterFailed;
	public static String FBTester_WriteParameter;
	public static String FBTester_Start;
	public static String FBTester_Kill;
	public static String FBTester_KillFB;
	public static String FBTester_DeleteFB;
	public static String FBTester_StartFB;
	public static String FBTester_WriteResourceParameterFailed;
	public static String FBTester_DisconnectFailed;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
