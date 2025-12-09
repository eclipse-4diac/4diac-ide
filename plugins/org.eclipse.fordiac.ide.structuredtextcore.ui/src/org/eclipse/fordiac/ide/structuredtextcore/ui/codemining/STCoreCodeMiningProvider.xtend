/*******************************************************************************
 * Copyright (c) 2022, 2023 Martin Erich Jobst
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
import java.util.Collections
import java.util.Set
import java.util.concurrent.CompletableFuture
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.jface.text.BadLocationException
import org.eclipse.jface.text.IDocument
import org.eclipse.jface.text.ITextViewer
import org.eclipse.jface.text.codemining.ICodeMining
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.ui.codemining.AbstractXtextCodeMiningProvider
import org.eclipse.xtext.util.CancelIndicator
import org.eclipse.xtext.util.IAcceptor

import static extension org.eclipse.xtext.EcoreUtil2.*

class STCoreCodeMiningProvider extends AbstractXtextCodeMiningProvider {
	@Inject extension STCoreGrammarAccess
	@Inject extension STCoreCodeMiningPreferences

	override provideCodeMinings(ITextViewer viewer, IProgressMonitor monitor) {
		if (isEnableCodeMinings)
			super.provideCodeMinings(viewer, monitor)
		else
			CompletableFuture.completedFuture(Collections.emptyList())
	}

	override void createCodeMinings(IDocument document, XtextResource resource, CancelIndicator indicator,
		IAcceptor<? super ICodeMining> acceptor) throws BadLocationException {
		EcoreUtil.getAllProperContents(resource, true).forEach[createCodeMinings(document, acceptor)]
	}

	def dispatch void createCodeMinings(EObject element, IDocument document,
		IAcceptor<? super ICodeMining> acceptor) throws BadLocationException {
	}

	def dispatch void createCodeMinings(STNumericLiteral literal, IDocument document,
		IAcceptor<? super ICodeMining> acceptor) throws BadLocationException {
		if (isEnableLiteralTypeCodeMinings && literal.type === null &&
			literal.showLiteralTypeCodeMining) {
			val inferredType = literal.resultType
			if (inferredType !== null) {
				NodeModelUtils.findActualNodeFor(literal).asTreeIterable.filter [
					STNumericLiteralAccess.valueNumericParserRuleCall_1_1_0.equals(grammarElement) ||
						STNumericLiteralAccess.valueSignedNumericParserRuleCall_0_2_0.equals(grammarElement) ||
						STSignedNumericLiteralAccess.valueSignedNumericParserRuleCall_0.equals(grammarElement)
				].forEach [ value |
					acceptor.accept(createNewLineContentCodeMining(value.offset, '''«inferredType.name»#'''))
				]
			}
		}
	}

	def dispatch void createCodeMinings(STStringLiteral literal, IDocument document,
		IAcceptor<? super ICodeMining> acceptor) throws BadLocationException {
		if (isEnableLiteralTypeCodeMinings && literal.type === null &&
			literal.showLiteralTypeCodeMining) {
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

	def static boolean isShowLiteralTypeCodeMining(STExpression expression) {
		switch (it : expression.eContainer) {
			STVarDeclaration case ranges.contains(expression), // suppress in array bounds
			STArrayAccessExpression case index.contains(expression), // suppress in array subscript
			STMultibitPartialExpression: // suppress in bit access
				false
			STExpression:
				showLiteralTypeCodeMining
			default:
				true
		}
	}

	def dispatch void createCodeMinings(STFeatureExpression expression, IDocument document,
		IAcceptor<? super ICodeMining> acceptor) throws BadLocationException {
		if (isEnableReferencedVariablesCodeMinings && expression.call) {
			val referencedVariables = expression.findReferencedNonLocalVariables
			if (!referencedVariables.empty) {
				val node = NodeModelUtils.findActualNodeFor(expression)
				if (node !== null) {
					acceptor.accept(createNewLineHeaderCodeMining(node.startLine, document, referencedVariables.map [
						name
					].join(", ")))
				}
			}
		}
	}

	def protected Set<INamedElement> findReferencedNonLocalVariables(STFeatureExpression expression) {
		val visitedCallables = newHashSet
		val referencedVariables = newHashSet
		expression.findReferencedNonLocalVariables(visitedCallables, referencedVariables)
		referencedVariables
	}

	def protected void findReferencedNonLocalVariables(STFeatureExpression expression, Set<ICallable> visitedCallables,
		Set<INamedElement> result) {
		switch (feature:expression.feature) {
			VarDeclaration,
			STVarDeclaration case feature.eContainer instanceof STVarGlobalDeclarationBlock:
				result += feature
			ICallable case expression.call && feature.eResource == expression.eResource:
				if (visitedCallables += feature) {
					feature.getAllContentsOfType(STFeatureExpression).forEach [
						findReferencedNonLocalVariables(visitedCallables, result)
					]
				}
		}
	}
}
