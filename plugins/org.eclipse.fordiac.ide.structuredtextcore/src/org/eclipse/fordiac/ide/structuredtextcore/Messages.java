/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.structuredtextcore;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.structuredtextcore.messages"; //$NON-NLS-1$
	public static String STCoreValidator_Consecutive_Underscores_In_Identifier;
	public static String STCoreValidator_Identifier_Is_Reserved;
	public static String STCoreValidator_Implicit_Conversion_In_Literal;
	public static String STCoreValidator_Invalid_Literal;
	public static String STCoreValidator_Trailing_Underscore_In_Identifier;
	public static String STCoreValidator_Argument_Not_Assignable;
	public static String STCoreValidator_Assignment_Invalid_Left_Side;
	public static String STCoreValidator_Attempting_To_Call_Standard_Function_With_Formal_Arguments;
	public static String STCoreValidator_Feature_Not_Callable;
	public static String STCoreValidator_For_Variable_Not_Integral_Type;
	public static String STCoreValidator_Mixing_Formal_And_NonFormal;
	public static String STCoreValidator_BinaryOperator_Not_Applicable;
	public static String STCoreValidator_Non_Compatible_Types;
	public static String STCoreValidator_Wrong_Name_Case;
	public static String STCoreValidator_Wrong_Number_Of_Arguments;
	public static String STCoreValidator_UnaryOperator_Not_Applicable;
	public static String STCoreValidator_Unqualified_FB_Call_On_FB_With_Input_Event_Size_Not_One;
	public static String STCoreValidator_NameNotVisible;
	public static String STCoreValidator_CallableHasNoReturnType;
	public static String STCoreValidator_BitAccessOutOfRange;
	public static String STCoreValidator_BitAccessInvalidForType;
	public static String STCoreValidator_BitAccessInvalidForReciever;
	public static String STCoreValidator_BitAccessExpressionNotOfTypeAnyInt;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
