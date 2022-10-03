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
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper

class STCoreAntlrTokenToAttributeIdMapper extends DefaultAntlrTokenToAttributeIdMapper {

	val VAR_DECLARATION_KEYWORDS = #{"'VAR'", "'VAR_TEMP'", "'VAR_INPUT'", "'VAR_OUTPUT'", "'VAR_IN_OUT'", "'END_VAR'",
		"'VAR_EXTERNAL'", "'VAR_GLOBAL'", "'VAR_ACCESS'", "'RETAIN'", "'CONSTANT'", "'AT'"}

	val DATA_TYPES_KEYWORDS = #{"'CHAR'", "'WCHAR'", "'SINT'", "'INT'", "'DINT'", "'LINT'", "'LINT'", "'USINT'",
		"'UINT'", "'LDINT'", "'ULINT'", "'REAL'", "'LREAL'", "'TIME'", "'DATE'", "'TIME_OF_DAY'", "'DATE_AND_TIME'",
		"'STRING'", "'WSTRING'", "'BOOL'", "'BYTE'", "'WORD'", "'DWORD'", "'LWORD'", "'ARRAY'"}

	val FUNCTIONS_KEYWORDS = #{"'FUNCTION'", "'END_FUNCTION'"}

	val FUNCTION_BLOCK_KEYWORDS = #{"'FUNCTION_BLOCK'", "'END_FUNCTION_BLOCK'"}

	val METHOD_BLOCK_KEYWORDS = #{"'METHOD'", "'END_METHOD'"}

	val ALGORITHM_BLOCK_KEYWORDS = #{"'ALGORITHM'", "'END_ALGORITHM'"}

	val BOOLEAN_KEYWORDS = #{"'TRUE'", "'FALSE'"}

	override protected String calculateId(String tokenName, int tokenType) {

		if (VAR_DECLARATION_KEYWORDS.contains(tokenName)) {
			return STCoreHighlightingStyles.VAR_KEYWORD_ID;
		}
		if (DATA_TYPES_KEYWORDS.contains(tokenName)) {
			return STCoreHighlightingStyles.DATA_TYPE_ID;
		}
		if (FUNCTION_BLOCK_KEYWORDS.contains(tokenName)) {
			return STCoreHighlightingStyles.FUNCTION_BLOCK_ID;
		}
		if (METHOD_BLOCK_KEYWORDS.contains(tokenName)) {
			return STCoreHighlightingStyles.METHOD_BLOCK_ID;
		}
		if (ALGORITHM_BLOCK_KEYWORDS.contains(tokenName)) {
			return STCoreHighlightingStyles.ALGORITHM_BLOCK_ID;
		}
		if (FUNCTIONS_KEYWORDS.contains(tokenName)) {
			return STCoreHighlightingStyles.FUNCTIONS_ID;
		}
		if (BOOLEAN_KEYWORDS.contains(tokenName)) {
			return STCoreHighlightingStyles.NUMBER_ID;
		}
		return super.calculateId(tokenName, tokenType);

	}

}
