/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring

import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles

interface STCoreHighlightingStyles extends HighlightingStyles {

//lexical highlighting Styles
	String VAR_KEYWORD_ID = "VarKeyword";
	String DATA_TYPE_ID = "DataType";
	String FUNCTION_BLOCK_ID = "FunctionBlock";
	String FUNCTIONS_ID = "Functions";
	String METHOD_BLOCK_ID = "MethodBlock";
	String ALGORITHM_BLOCK_ID = "AlgorithmBlock";

//Semantic highlighting Styles
	String GLOBAL_CONST_ID = "GlobalConstant";
	String MEMBER_VARIABLE_ID = "MemberVariable";
	String LOCAL_VARIABLE_ID = "Variables";
	String CALL_FUNCTION_ID = "CallFunction";
	String CALL_FUNCTION_BLOCK_ID = "CallFunctionBlock";
	String CALL_METHOD_ID = "CallMethod";
	String RETURN_FUNCTION_ID = "OutputFunction";
	String RETURN_METHOD_ID = "OutputMethod";
	String FUNCTIONS_NAME_ID = "FunctionsName";
	String METHODS_NAME_ID = "MethodsName";

}
