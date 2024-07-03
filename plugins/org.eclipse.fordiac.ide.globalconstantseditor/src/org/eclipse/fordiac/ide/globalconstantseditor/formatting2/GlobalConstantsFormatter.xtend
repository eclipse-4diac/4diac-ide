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
 *       - add global formatting from STFunctionFormatter
 *       - add formatting for package declaration and imports
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.formatting2

import com.google.inject.Inject
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STGlobalConstsSource
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock
import org.eclipse.fordiac.ide.globalconstantseditor.services.GlobalConstantsGrammarAccess
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.KeywordCaseTextReplacer
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.STCoreFormatter
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.formatting2.IFormattableDocument

class GlobalConstantsFormatter extends STCoreFormatter {

	@Inject extension GlobalConstantsGrammarAccess

	def dispatch void format(STGlobalConstsSource stGlobalConstsSource, extension IFormattableDocument document) {
		val regions = textRegionAccess.regionForRootEObject.allSemanticRegions;
		regions.forEach [
			it.append[autowrap]
		]

		stGlobalConstsSource.allSemanticRegions.filter [
			switch (element : grammarElement) {
				Keyword case element.value.matches("[_a-zA-Z]+"): true
				RuleCall case element.rule == numericRule: true
				RuleCall case element.rule == STNumericLiteralTypeRule: true
				RuleCall case element.rule == STDateLiteralTypeRule: true
				RuleCall case element.rule == STTimeLiteralTypeRule: true
				RuleCall case element.rule == orOperatorRule: true
				RuleCall case element.rule == xorOperatorRule: true
				RuleCall case element.rule == getUnaryOperatorRule: true
				default: false
			}
		].forEach [
			document.addReplacer(new KeywordCaseTextReplacer(it))
		]

		stGlobalConstsSource.allRegionsFor.keywords(STPrimaryExpressionAccess.leftParenthesisKeyword_0_0).forEach [
			append[noSpace]
		]
		stGlobalConstsSource.allRegionsFor.keywords(STPrimaryExpressionAccess.rightParenthesisKeyword_0_2).forEach [
			prepend[noSpace]
		]
		stGlobalConstsSource.allRegionsFor.ruleCallsTo(getML_COMMENTRule).forEach [
			append[setNewLines(1, 2, 2)]
		]

		if (!stGlobalConstsSource.name.nullOrEmpty) {
			stGlobalConstsSource.regionFor.keyword(STGlobalConstsSourceAccess.PACKAGEKeyword_1_0).prepend[noSpace].
				append[oneSpace]
			stGlobalConstsSource.regionFor.keyword(STGlobalConstsSourceAccess.semicolonKeyword_1_2).prepend[noSpace].
				append[newLines = 2]
		}
		stGlobalConstsSource.imports.forEach [
			format
			if (it == stGlobalConstsSource.imports.last)
				regionFor.keyword(STImportAccess.semicolonKeyword_2).append[setNewLines(2, 2, 2)]
			else
				regionFor.keyword(STImportAccess.semicolonKeyword_2).append[setNewLines(1, 1, 2)]
		]
		stGlobalConstsSource.elements.forEach [
			format
			regionFor.keyword(STVarGlobalDeclarationBlockAccess.END_VARKeyword_4).append[newLines = 2]
		]
	}

	def dispatch void format(STVarGlobalDeclarationBlock varDeclarationBlock, extension IFormattableDocument document) {
		if (varDeclarationBlock.constant) {
			varDeclarationBlock.regionFor.keyword(STVarGlobalDeclarationBlockAccess.constantCONSTANTKeyword_2_0).prepend [
				oneSpace
			].append[newLine]
		} else {
			varDeclarationBlock.regionFor.keyword(STVarGlobalDeclarationBlockAccess.VAR_GLOBALKeyword_1).append[newLine]
		}
		interior(
			varDeclarationBlock.regionFor.keyword(STVarGlobalDeclarationBlockAccess.VAR_GLOBALKeyword_1),
			varDeclarationBlock.regionFor.keyword(STVarGlobalDeclarationBlockAccess.END_VARKeyword_4),
			[indent]
		)
		for (STVarDeclaration varDeclaration : varDeclarationBlock.varDeclarations) {
			varDeclaration.format
		}
	}
}
