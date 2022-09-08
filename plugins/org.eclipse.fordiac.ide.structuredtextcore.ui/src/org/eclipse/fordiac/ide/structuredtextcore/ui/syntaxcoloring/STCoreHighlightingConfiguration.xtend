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

import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.RGB
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
	public static final String CALL_FUNCTION_BLOCK_ID = STCoreHighlightingStyles.CALL_FUNCTION_BLOCK_ID;
	public static final String CALL_METHOD_ID = STCoreHighlightingStyles.CALL_METHOD_ID;
	public static final String RETURN_FUNCTION_ID = STCoreHighlightingStyles.RETURN_FUNCTION_ID;
	public static final String RETURN_METHOD_ID = STCoreHighlightingStyles.RETURN_METHOD_ID;
	public static final String FUNCTIONS_NAME_ID = STCoreHighlightingStyles.FUNCTIONS_NAME_ID;
	public static final String METHODS_NAME_ID = STCoreHighlightingStyles.METHODS_NAME_ID;

	override configure(IHighlightingConfigurationAcceptor acceptor) {

		// lexical highlighting Styles
		acceptor.acceptDefaultHighlighting(KEYWORD_ID, "Keyword", keywordTextStyle());
		acceptor.acceptDefaultHighlighting(VAR_KEYWORD_ID, "Variable Keyword", varKeywordTextStyle());
		acceptor.acceptDefaultHighlighting(DATA_TYPE_ID, "Date Types", dataTypeTextStyle());
		acceptor.acceptDefaultHighlighting(FUNCTIONS_ID, "Functions", functionsTextStyle());
		acceptor.acceptDefaultHighlighting(METHOD_BLOCK_ID, "MethodBlock", methodBlockTextStyle());
		acceptor.acceptDefaultHighlighting(ALGORITHM_BLOCK_ID, "AlgorithmBlock", algorithmTextStyle());
		acceptor.acceptDefaultHighlighting(FUNCTION_BLOCK_ID, "FunctionBlock", functionBockTextStyle());
		acceptor.acceptDefaultHighlighting(PUNCTUATION_ID, "Punctuation character", punctuationTextStyle());
		acceptor.acceptDefaultHighlighting(COMMENT_ID, "Comment", commentTextStyle());
		acceptor.acceptDefaultHighlighting(TASK_ID, "Task Tag", taskTextStyle());
		acceptor.acceptDefaultHighlighting(STRING_ID, "String", stringTextStyle());
		acceptor.acceptDefaultHighlighting(NUMBER_ID, "Number", numberTextStyle());
		acceptor.acceptDefaultHighlighting(DEFAULT_ID, "Default", defaultTextStyle());
		acceptor.acceptDefaultHighlighting(INVALID_TOKEN_ID, "Invalid Symbol", errorTextStyle());

		// Semantic highlighting Styles	
		acceptor.acceptDefaultHighlighting(CALL_FUNCTION_ID, "CallFunction", callFunctionTextStyle());
		acceptor.acceptDefaultHighlighting(CALL_FUNCTION_BLOCK_ID, "CallFunctionBlock", callFunctionBlockTextStyle());
		acceptor.acceptDefaultHighlighting(CALL_METHOD_ID, "CallMethod", callMethodTextStyle());
		acceptor.acceptDefaultHighlighting(STATIC_VAR_ID, "StaticVar", staticVarTextStyle());
		acceptor.acceptDefaultHighlighting(RETURN_FUNCTION_ID, "ReturnFunctionVar", returnFunctionTextStyle());
		acceptor.acceptDefaultHighlighting(RETURN_METHOD_ID, "ReturnMethodVar", returnMethodTextStyle());
		acceptor.acceptDefaultHighlighting(FUNCTIONS_NAME_ID, "FunctionsName", functionNameTextStyle());
		acceptor.acceptDefaultHighlighting(METHODS_NAME_ID, "MethodsName", methodNameTextStyle());

	}

	def algorithmTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 0, 128));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	def callFunctionTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 128, 255));
		return textStyle;
	}

	def callFunctionBlockTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 128, 255));
		return textStyle;
	}

	def callMethodTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 128, 255));
		return textStyle;
	}

	def dataTypeTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 128, 255));
		return textStyle;
	}

	override defaultTextStyle() {
		var textStyle = super.defaultTextStyle().copy();
		textStyle.setColor(new RGB(64, 0, 0));
		return textStyle;
	}

	def functionBockTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 0, 128));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	def functionsTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 0, 128));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	def functionNameTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 0));
		return textStyle;
	}

	def methodNameTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 0));
		return textStyle;
	}

	override keywordTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(255, 128, 0));
		return textStyle;
	}

	def methodBlockTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 0, 128));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	override numberTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 164, 164));
		return textStyle;
	}

	def outputFunctionTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 0));
		return textStyle;
	}

	def returnFunctionTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 0));
		return textStyle;
	}

	def returnMethodTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 0));
		return textStyle;
	}

	def staticVarTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 0));
		return textStyle;
	}

	override stringTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 255));
		return textStyle;
	}

	def varKeywordTextStyle() {
		var textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 128, 255));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}
}
