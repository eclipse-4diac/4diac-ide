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

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor

class STCoreHighlightingConfiguration extends DefaultHighlightingConfiguration {

	// lexical highlighting Styles
	public static final String VAR_KEYWORD_ID = STCoreHighlightingStyles.VAR_KEYWORD_ID;
	public static final String DATA_TYPE_ID = STCoreHighlightingStyles.DATA_TYPE_ID;
	public static final String FUNCTION_BLOCK_ID = STCoreHighlightingStyles.FUNCTION_BLOCK_ID;
	public static final String METHOD_BLOCK_ID = STCoreHighlightingStyles.METHOD_BLOCK_ID;
	public static final String ALGORITHM_BLOCK_ID = STCoreHighlightingStyles.ALGORITHM_BLOCK_ID;
	public static final String FUNCTIONS_ID = STCoreHighlightingStyles.FUNCTIONS_ID;

	// Semantic highlighting Styles
	public static final String STATIC_VAR_ID = STCoreHighlightingStyles.STATIC_VAR_ID;
	public static final String CALL_FUNCTION_ID = STCoreHighlightingStyles.CALL_FUNCTION_ID;
	public static final String OUTPUT_FUNCTION_ID = STCoreHighlightingStyles.OUTPUT_FUNCTION_ID;
	public static final String RETURN_FUNCTION_ID = STCoreHighlightingStyles.RETURN_FUNCTION_ID;
	public static final String FUNCTIONS_NAME_ID = STCoreHighlightingStyles.FUNCTIONS_NAME_ID;

	override configure(IHighlightingConfigurationAcceptor acceptor) {

		// lexical highlighting Styles
		acceptor.acceptDefaultHighlighting(KEYWORD_ID, "Keyword", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(VAR_KEYWORD_ID, "Variable Keyword", commentTextStyle());
		acceptor.acceptDefaultHighlighting(DATA_TYPE_ID, "Date Types", commentTextStyle());
		acceptor.acceptDefaultHighlighting(FUNCTIONS_ID, "Functions", commentTextStyle());
		acceptor.acceptDefaultHighlighting(METHOD_BLOCK_ID, "MethodBlock", commentTextStyle());
		acceptor.acceptDefaultHighlighting(ALGORITHM_BLOCK_ID, "AlgorithmBlock", commentTextStyle());
		acceptor.acceptDefaultHighlighting(FUNCTION_BLOCK_ID, "FunctionBlock", commentTextStyle());
		acceptor.acceptDefaultHighlighting(PUNCTUATION_ID, "Punctuation character", punctuationTextStyle());
		acceptor.acceptDefaultHighlighting(COMMENT_ID, "Comment", commentTextStyle());
		acceptor.acceptDefaultHighlighting(TASK_ID, "Task Tag", taskTextStyle());
		acceptor.acceptDefaultHighlighting(STRING_ID, "String", stringTextStyle());
		acceptor.acceptDefaultHighlighting(NUMBER_ID, "Number", numberTextStyle());
		acceptor.acceptDefaultHighlighting(DEFAULT_ID, "Default", defaultTextStyle());
		acceptor.acceptDefaultHighlighting(INVALID_TOKEN_ID, "Invalid Symbol", errorTextStyle());

		// Semantic highlighting Styles	
		acceptor.acceptDefaultHighlighting(CALL_FUNCTION_ID, "CallFunction", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(STATIC_VAR_ID, "StaticVar", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(OUTPUT_FUNCTION_ID, "OutputFunctionVar", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(RETURN_FUNCTION_ID, "ReturnFunctionVar", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(FUNCTIONS_NAME_ID, "FunctionsName", commentTextStyle());

	}

}
