/**
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *   Martin Jobst
 *       - distinguish variable scope
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring;

import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles;

public interface STCoreHighlightingStyles extends HighlightingStyles {
	String VAR_KEYWORD_ID = "VarKeyword"; //$NON-NLS-1$
	String DATA_TYPE_ID = "DataType"; //$NON-NLS-1$
	String FUNCTION_BLOCK_ID = "FunctionBlock"; //$NON-NLS-1$
	String FUNCTIONS_ID = "Functions"; //$NON-NLS-1$
	String METHOD_BLOCK_ID = "MethodBlock"; //$NON-NLS-1$
	String ALGORITHM_BLOCK_ID = "AlgorithmBlock"; //$NON-NLS-1$
	String GLOBAL_CONST_ID = "GlobalConstant"; //$NON-NLS-1$
	String MEMBER_VARIABLE_ID = "MemberVariable"; //$NON-NLS-1$
	String LOCAL_VARIABLE_ID = "Variables"; //$NON-NLS-1$
	String CALL_FUNCTION_ID = "CallFunction"; //$NON-NLS-1$
	String CALL_FUNCTION_BLOCK_ID = "CallFunctionBlock"; //$NON-NLS-1$
	String CALL_METHOD_ID = "CallMethod"; //$NON-NLS-1$
	String RETURN_FUNCTION_ID = "OutputFunction"; //$NON-NLS-1$
	String RETURN_METHOD_ID = "OutputMethod"; //$NON-NLS-1$
	String FUNCTIONS_NAME_ID = "FunctionsName"; //$NON-NLS-1$
	String METHODS_NAME_ID = "MethodsName"; //$NON-NLS-1$
}
