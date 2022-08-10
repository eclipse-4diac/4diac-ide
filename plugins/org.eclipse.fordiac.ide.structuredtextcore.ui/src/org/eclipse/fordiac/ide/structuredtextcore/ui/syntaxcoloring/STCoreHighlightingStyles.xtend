/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH
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
	String STATIC_VAR_ID = "StaticVar";
	String CALL_FUNCTION_ID = "CallFunction";
	String OUTPUT_FUNCTION_ID = "OutputFunction";
	String RETURN_FUNCTION_ID = "ReturnFunction";
	String FUNCTIONS_NAME_ID = "FunctionsName";

}
