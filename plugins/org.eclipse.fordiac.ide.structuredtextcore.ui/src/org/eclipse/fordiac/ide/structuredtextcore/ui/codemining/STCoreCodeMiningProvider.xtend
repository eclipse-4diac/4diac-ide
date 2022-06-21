/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.codemining

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.codemining.ICodeMining
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.codemining.AbstractXtextCodeMiningProvider
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.util.IAcceptor

class STCoreCodeMiningProvider extends AbstractXtextCodeMiningProvider {
	@Inject extension STCoreGrammarAccess
	@Inject extension STCoreCodeMiningPreferences

	override void createCodeMinings(IDocument document, XtextResource resource, CancelIndicator indicator,
		IAcceptor<? super ICodeMining> acceptor) throws BadLocationException {
		if (isEnableCodeMinings) {
			EcoreUtil.getAllProperContents(resource, true).forEach[createCodeMinings(acceptor)]
		}
	}

	def dispatch void createCodeMinings(EObject element,
		IAcceptor<? super ICodeMining> acceptor) throws BadLocationException {
	}

	def dispatch void createCodeMinings(STNumericLiteral literal,
		IAcceptor<? super ICodeMining> acceptor) throws BadLocationException {
		if (isEnableLiteralTypeCodeMinings && literal.declaredResultType === null) {
			val inferredType = literal.resultType
			if (inferredType !== null) {
				NodeModelUtils.findActualNodeFor(literal).asTreeIterable.filter [
					STNumericLiteralAccess.valueNumericParserRuleCall_1_0.equals(grammarElement)
				].forEach [ value |
					acceptor.accept(createNewLineContentCodeMining(value.offset, '''«inferredType.name»#'''))
				]
			}
		}
	}

	def dispatch void createCodeMinings(STStringLiteral literal,
		IAcceptor<? super ICodeMining> acceptor) throws BadLocationException {
		if (isEnableLiteralTypeCodeMinings && literal.declaredResultType === null) {
			val inferredType = literal.resultType
			if (inferredType !== null) {
				NodeModelUtils.findActualNodeFor(literal).asTreeIterable.filter [
					STStringLiteralAccess.valueSTRINGTerminalRuleCall_1_0.equals(grammarElement)
				].forEach [ value |
					acceptor.accept(createNewLineContentCodeMining(value.offset, '''«inferredType.name»#'''))
				]
			}
		}
	}
}
