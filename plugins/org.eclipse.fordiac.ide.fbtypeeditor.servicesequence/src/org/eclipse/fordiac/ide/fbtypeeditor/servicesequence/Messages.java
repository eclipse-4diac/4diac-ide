/*******************************************************************************
 * Copyright (c) 2020 Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Andrea Zoitl
 *      - externalized all translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.messages"; //$NON-NLS-1$

	public static String PrimitiveSection_CreateEventSection_Event;
	public static String PrimitiveSection_CreatePrimitiveSection_Interface;
	public static String PrimitiveSection_CreatePrimitiveSection_Parameters;
	public static String PrimitiveSection_None;
	public static String ServiceInterfacePaletteFactory_LeftInterface;
	public static String ServiceInterfacePaletteFactory_InputPrimitive;
	public static String ServiceInterfacePaletteFactory_InputPrimitive_Desc;
	public static String ServiceInterfacePaletteFactory_OutputPrimitive;
	public static String ServiceInterfacePaletteFactory_OutputPrimitive_Desc;
	public static String ServiceInterfacePaletteFactory_RightInterface;
	public static String ServiceInterfacePaletteFactory_ServiceTransaction;
	public static String ServiceInterfacePaletteFactory_ServiceTransaction_Desc;
	public static String ServiceSection_Delete;
	public static String ServiceSection_LeftInterface;
	public static String ServiceSection_LeftInterface_Comment;
	public static String ServiceSection_LeftInterface_Name;
	public static String ServiceSection_New;
	public static String ServiceSection_RightInterface;
	public static String ServiceSection_RightInterface_Comment;
	public static String ServiceSection_RightInterface_Name;
	public static String ServiceSection_ServiceSequences;
	public static String ServiceSequenceEditor_Service;
	public static String ServiceSequenceSection_Comment;
	public static String ServiceSequenceSection_Delete;
	public static String ServiceSequenceSection_Name;
	public static String ServiceSequenceSection_New;
	public static String ServiceSequenceSection_Transaction;
	public static String TransactionSection_CreateLeftPrimitive;
	public static String TransactionSection_CreateRightPrimitive;
	public static String TransactionSection_Delete;
	public static String TransactionSection_InputPrimitive;
	public static String TransactionSection_LeftOutputPrimitives;
	public static String TransactionSection_New;
	public static String TransactionSection_RightOutputPrimitives;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}