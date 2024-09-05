/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.model.eval.messages"; //$NON-NLS-1$
	public static String EvaluatorInitializerException_DefaultMessage;
	public static String ValueOperations_Absolute;
	public static String ValueOperations_Add;
	public static String ValueOperations_And;
	public static String ValueOperations_Compare;
	public static String ValueOperations_Divide;
	public static String ValueOperations_DivisionByZero;
	public static String ValueOperations_MemberMapCastMessage;
	public static String ValueOperations_Multiply;
	public static String ValueOperations_Negate;
	public static String ValueOperations_Not;
	public static String ValueOperations_Or;
	public static String ValueOperations_Power;
	public static String ValueOperations_Remainder;
	public static String ValueOperations_ReverseBytes;
	public static String ValueOperations_RotateLeft;
	public static String ValueOperations_RotateRight;
	public static String ValueOperations_ShiftLeft;
	public static String ValueOperations_ShiftRight;
	public static String ValueOperations_Sqrt;
	public static String ValueOperations_Subtract;
	public static String ValueOperations_UnsupportedBinaryOperation;
	public static String ValueOperations_UnsupportedCast;
	public static String ValueOperations_UnsupportedPartialOperation;
	public static String ValueOperations_UnsupportedType;
	public static String ValueOperations_UnsupportedUnaryOperation;
	public static String ValueOperations_Xor;
	public static String VariableOperations_NoEvaluatorForAttribute;
	public static String VariableOperations_NoEvaluatorForDirectlyDerivedType;
	public static String VariableOperations_NoEvaluatorForVarDeclaration;
	public static String VariableOperations_TypeMustNotBeNull;
	public static String VariableOperations_UnsupportedType;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
