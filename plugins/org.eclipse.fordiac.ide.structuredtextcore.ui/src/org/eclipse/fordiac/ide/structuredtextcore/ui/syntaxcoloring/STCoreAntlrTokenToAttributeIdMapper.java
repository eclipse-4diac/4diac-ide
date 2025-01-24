/**
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
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring;

import java.util.Set;

import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;

@SuppressWarnings("nls")
public class STCoreAntlrTokenToAttributeIdMapper extends DefaultAntlrTokenToAttributeIdMapper {
	private static final Set<String> VAR_DECLARATION_KEYWORDS = Set.of("\'VAR\'", "\'VAR_TEMP\'", "\'VAR_INPUT\'",
			"\'VAR_OUTPUT\'", "\'VAR_IN_OUT\'", "\'END_VAR\'", "\'VAR_EXTERNAL\'", "\'VAR_GLOBAL\'", "\'VAR_ACCESS\'",
			"\'RETAIN\'", "\'CONSTANT\'", "\'AT\'");
	private static final Set<String> DATA_TYPES_KEYWORDS = Set.of("\'CHAR\'", "\'WCHAR\'", "\'SINT\'", "\'INT\'",
			"\'DINT\'", "\'LINT\'", "\'LINT\'", "\'USINT\'", "\'UINT\'", "\'LDINT\'", "\'ULINT\'", "\'REAL\'",
			"\'LREAL\'", "\'TIME\'", "\'DATE\'", "\'TIME_OF_DAY\'", "\'DATE_AND_TIME\'", "\'STRING\'", "\'WSTRING\'",
			"\'BOOL\'", "\'BYTE\'", "\'WORD\'", "\'DWORD\'", "\'LWORD\'", "\'ARRAY\'");
	private static final Set<String> FUNCTIONS_KEYWORDS = Set.of("\'FUNCTION\'", "\'END_FUNCTION\'");
	private static final Set<String> FUNCTION_BLOCK_KEYWORDS = Set.of("\'FUNCTION_BLOCK\'", "\'END_FUNCTION_BLOCK\'");
	private static final Set<String> METHOD_BLOCK_KEYWORDS = Set.of("\'METHOD\'", "\'END_METHOD\'");
	private static final Set<String> ALGORITHM_BLOCK_KEYWORDS = Set.of("\'ALGORITHM\'", "\'END_ALGORITHM\'");
	private static final Set<String> BOOLEAN_KEYWORDS = Set.of("\'TRUE\'", "\'FALSE\'");

	@Override
	protected String calculateId(final String tokenName, final int tokenType) {
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
			return HighlightingStyles.NUMBER_ID;
		}
		return super.calculateId(tokenName, tokenType);
	}
}
