/*******************************************************************************
 * Copyright (c) 2019 - 2020 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl
 *     - Externalized all translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.fbtypeeditor.messages"; //$NON-NLS-1$

	public static String CreateFromNewAdapterAction_NewAdapter;
	public static String CreateInputEventAction_CreateInputEvent;
	public static String CreateOutputEventAction_CreateOutputEvent;

	public static String FBInterfacePaletteFactory_AdapterTypes;
	public static String FBInterfacePaletteFactory_DataTypes;
	public static String FBInterfacePaletteFactory_EventTypes;
	public static String FBInterfacePaletteFactory_RootGroup;

	public static String FBTypeXtextEditor_PerformanceModeDialogMessage;

	public static String FBTypeXtextEditor_PerformanceModeDialogTitle;

	public static String FBTypeXtextEditor_PerformanceModeOutlineMessage;

	public static String InterfaceContextMenuProvider_CreateDataInput;
	public static String InterfaceContextMenuProvider_CreateDataOutput;
	public static String InterfaceContextMenuProvider_CreatePlug;
	public static String InterfaceContextMenuProvider_CreateSocket;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}