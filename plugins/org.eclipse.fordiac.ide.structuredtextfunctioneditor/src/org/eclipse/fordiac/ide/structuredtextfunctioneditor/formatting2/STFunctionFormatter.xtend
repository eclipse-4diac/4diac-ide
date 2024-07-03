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
 *   Ulzii Jargalsaikhan - initial API and implementation and/or initial documentation,
 * 		comment formatter
 *   Martin Melik-Merkumians - comment formatter
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.formatting2

import com.google.inject.Inject
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.KeywordCaseTextReplacer
import org.eclipse.fordiac.ide.structuredtextcore.formatting2.STCoreFormatter
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.services.STFunctionGrammarAccess
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionPackage
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.formatting2.IFormattableDocument

class STFunctionFormatter extends STCoreFormatter {

	@Inject extension STFunctionGrammarAccess

	def dispatch void format(STFunctionSource stFunctionSource, extension IFormattableDocument document) {

		val regions = textRegionAccess.regionForRootEObject.allSemanticRegions;
		regions.forEach [
			it.append[autowrap]
		]

		stFunctionSource.allSemanticRegions.filter [
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

		stFunctionSource.allRegionsFor.keywords(STPrimaryExpressionAccess.leftParenthesisKeyword_0_0).forEach [
			append[noSpace]
		]
		stFunctionSource.allRegionsFor.keywords(STPrimaryExpressionAccess.rightParenthesisKeyword_0_2).forEach [
			prepend[noSpace]
		]
		stFunctionSource.allRegionsFor.ruleCallsTo(getML_COMMENTRule).forEach [
			append[setNewLines(1, 2, 2)]
		]

		if (!stFunctionSource.name.nullOrEmpty) {
			stFunctionSource.regionFor.keyword(STFunctionSourceAccess.PACKAGEKeyword_1_0).prepend[noSpace].append [
				oneSpace
			]
			stFunctionSource.regionFor.keyword(STFunctionSourceAccess.semicolonKeyword_1_2).prepend[noSpace].append [
				newLines = 2
			]
		}
		stFunctionSource.imports.forEach [
			format
			if (it == stFunctionSource.imports.last)
				regionFor.keyword(STImportAccess.semicolonKeyword_2).append[setNewLines(2, 2, 2)]
			else
				regionFor.keyword(STImportAccess.semicolonKeyword_2).append[setNewLines(1, 1, 2)]
		]
		stFunctionSource.functions.forEach[format]
	}

	def dispatch void format(STFunction stFunction, extension IFormattableDocument document) {
		stFunction.regionFor.keyword("FUNCTION").prepend[noIndentation].append[oneSpace]

		if (stFunction.returnType !== null) {
			// We have a return type
			stFunction.regionFor.keyword(STFunctionAccess.colonKeyword_3_0).surround[oneSpace]
			stFunction.regionFor.feature(STFunctionPackage.Literals.ST_FUNCTION__RETURN_TYPE).prepend[oneSpace].append [
				setNewLines(1, 1, 2)
			]
		} else {
			stFunction.regionFor.assignment(STFunctionAccess.nameAssignment_2).append[setNewLines(1, 1, 2)]
		}

		stFunction.varDeclarations.forEach[format]
		if (!stFunction.varDeclarations.empty) {
			stFunction.varDeclarations.last.append[setNewLines(1, 2, 2)]
		}

		stFunction.code.forEach[format]

		stFunction.regionFor.keyword("END_FUNCTION").prepend[noSpace].append[setNewLines(1, 2, 2)]
	}
}
