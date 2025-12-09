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

import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

@SuppressWarnings("java:S4144")
public class STCoreHighlightingConfiguration extends DefaultHighlightingConfiguration {
	public static final String VAR_KEYWORD_ID = STCoreHighlightingStyles.VAR_KEYWORD_ID;
	public static final String DATA_TYPE_ID = STCoreHighlightingStyles.DATA_TYPE_ID;
	public static final String FUNCTION_BLOCK_ID = STCoreHighlightingStyles.FUNCTION_BLOCK_ID;
	public static final String METHOD_BLOCK_ID = STCoreHighlightingStyles.METHOD_BLOCK_ID;
	public static final String ALGORITHM_BLOCK_ID = STCoreHighlightingStyles.ALGORITHM_BLOCK_ID;
	public static final String FUNCTIONS_ID = STCoreHighlightingStyles.FUNCTIONS_ID;
	public static final String GLOBAL_CONST_ID = STCoreHighlightingStyles.GLOBAL_CONST_ID;
	public static final String MEMBER_VARIABLE_ID = STCoreHighlightingStyles.MEMBER_VARIABLE_ID;
	public static final String LOCAL_VARIABLE_ID = STCoreHighlightingStyles.LOCAL_VARIABLE_ID;
	public static final String CALL_FUNCTION_ID = STCoreHighlightingStyles.CALL_FUNCTION_ID;
	public static final String CALL_FUNCTION_BLOCK_ID = STCoreHighlightingStyles.CALL_FUNCTION_BLOCK_ID;
	public static final String CALL_METHOD_ID = STCoreHighlightingStyles.CALL_METHOD_ID;
	public static final String RETURN_FUNCTION_ID = STCoreHighlightingStyles.RETURN_FUNCTION_ID;
	public static final String RETURN_METHOD_ID = STCoreHighlightingStyles.RETURN_METHOD_ID;
	public static final String FUNCTIONS_NAME_ID = STCoreHighlightingStyles.FUNCTIONS_NAME_ID;
	public static final String METHODS_NAME_ID = STCoreHighlightingStyles.METHODS_NAME_ID;

	@Override
	public void configure(final IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);

		acceptor.acceptDefaultHighlighting(VAR_KEYWORD_ID, Messages.STCoreHighlightingConfiguration_VariableKeyword,
				varKeywordTextStyle());
		acceptor.acceptDefaultHighlighting(DATA_TYPE_ID, Messages.STCoreHighlightingConfiguration_DataTypes,
				dataTypeTextStyle());
		acceptor.acceptDefaultHighlighting(FUNCTIONS_ID, Messages.STCoreHighlightingConfiguration_Functions,
				functionsTextStyle());
		acceptor.acceptDefaultHighlighting(METHOD_BLOCK_ID, Messages.STCoreHighlightingConfiguration_MethodKeyword,
				methodBlockTextStyle());
		acceptor.acceptDefaultHighlighting(ALGORITHM_BLOCK_ID,
				Messages.STCoreHighlightingConfiguration_AlgorithmKeyword, algorithmTextStyle());
		acceptor.acceptDefaultHighlighting(FUNCTION_BLOCK_ID,
				Messages.STCoreHighlightingConfiguration_FunctionBlockKeyword, functionBockTextStyle());
		acceptor.acceptDefaultHighlighting(CALL_FUNCTION_ID, Messages.STCoreHighlightingConfiguration_FunctionCalls,
				callFunctionTextStyle());
		acceptor.acceptDefaultHighlighting(CALL_FUNCTION_BLOCK_ID,
				Messages.STCoreHighlightingConfiguration_FunctionBlockCalls, callFunctionBlockTextStyle());
		acceptor.acceptDefaultHighlighting(CALL_METHOD_ID, Messages.STCoreHighlightingConfiguration_MethodCalls,
				callMethodTextStyle());
		acceptor.acceptDefaultHighlighting(GLOBAL_CONST_ID, Messages.STCoreHighlightingConfiguration_GlobalConstants,
				globalConstTextStyle());
		acceptor.acceptDefaultHighlighting(MEMBER_VARIABLE_ID, Messages.STCoreHighlightingConfiguration_MemberVariables,
				memberVarTextStyle());
		acceptor.acceptDefaultHighlighting(LOCAL_VARIABLE_ID, Messages.STCoreHighlightingConfiguration_LocalVariables,
				localVarTextStyle());
		acceptor.acceptDefaultHighlighting(RETURN_FUNCTION_ID,
				Messages.STCoreHighlightingConfiguration_FunctionReturnVariables, returnFunctionTextStyle());
		acceptor.acceptDefaultHighlighting(RETURN_METHOD_ID,
				Messages.STCoreHighlightingConfiguration_MethodReturnVariables, returnMethodTextStyle());
		acceptor.acceptDefaultHighlighting(FUNCTIONS_NAME_ID, Messages.STCoreHighlightingConfiguration_FunctionName,
				functionNameTextStyle());
		acceptor.acceptDefaultHighlighting(METHODS_NAME_ID, Messages.STCoreHighlightingConfiguration_MethodName,
				methodNameTextStyle());
	}

	public TextStyle algorithmTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 0, 128));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	public TextStyle callFunctionTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 128, 255));
		return textStyle;
	}

	public TextStyle callFunctionBlockTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 128, 255));
		return textStyle;
	}

	public TextStyle callMethodTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 128, 255));
		return textStyle;
	}

	public TextStyle dataTypeTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 128, 255));
		return textStyle;
	}

	public TextStyle functionBockTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 0, 128));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	public TextStyle functionsTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 0, 128));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	public TextStyle functionNameTextStyle() {
		return defaultTextStyle();
	}

	public TextStyle methodNameTextStyle() {
		return defaultTextStyle();
	}

	@Override
	public TextStyle keywordTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(255, 128, 0));
		return textStyle;
	}

	public TextStyle methodBlockTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 0, 128));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	@Override
	public TextStyle numberTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 164, 164));
		return textStyle;
	}

	public TextStyle returnFunctionTextStyle() {
		return defaultTextStyle();
	}

	public TextStyle returnMethodTextStyle() {
		return defaultTextStyle();
	}

	public TextStyle globalConstTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(141, 218, 248));
		textStyle.setStyle(SWT.BOLD | SWT.ITALIC);
		return textStyle;
	}

	public TextStyle memberVarTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 64, 64));
		return textStyle;
	}

	public TextStyle localVarTextStyle() {
		return defaultTextStyle();
	}

	@Override
	public TextStyle stringTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 255));
		return textStyle;
	}

	public TextStyle varKeywordTextStyle() {
		final TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 128, 255));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}
}
