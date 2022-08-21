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

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationImpl
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor
import org.eclipse.xtext.impl.RuleCallImpl
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.impl.CompositeNodeWithSemanticElement
import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.util.CancelIndicator

class STCoreSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {

	@Inject
	STCoreGrammarAccess ga

	def protected dispatch boolean highlightElement(EObject object, IHighlightedPositionAcceptor acceptor,
		CancelIndicator cancelIndicator) {
		return super.highlightElement(object, acceptor, cancelIndicator);
	}

	// semantic highlighting for Name of Function by defining the Function and output of Function by defining the Function	 
	def protected dispatch boolean highlightElement(STFunction stFunction, IHighlightedPositionAcceptor acceptor,
		CancelIndicator cancelIndicator) {
		for (ILeafNode n : NodeModelUtils.findActualNodeFor(stFunction).getLeafNodes()) {

			if ((n.grammarElement?.eContainer == ga.STAnyBitTypeRule.alternatives)) {
				acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.OUTPUT_FUNCTION_ID);
			}
			if ((n.grammarElement instanceof RuleCallImpl) && (n.parent instanceof CompositeNodeWithSemanticElement)) {
				if (n.grammarElement.eContainer.eContainingFeature.name.equals("elements")) {
					acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.FUNCTIONS_NAME_ID);
				}
			}

		}
		return super.highlightElement(stFunction, acceptor, cancelIndicator);
	}

// semantic highlighting for variables and some data types
	def protected dispatch boolean highlightElement(STVarDeclaration varDeclaration,
		IHighlightedPositionAcceptor acceptor, CancelIndicator cancelIndicator) {
		for (ILeafNode n : NodeModelUtils.findActualNodeFor(varDeclaration).getLeafNodes()) {
			if (n.grammarElement == ga.STVarDeclarationAccess.nameIDTerminalRuleCall_1_0) {
				acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.STATIC_VAR_ID);
			}
			if ((n.grammarElement?.eContainer == ga.STAnyTypeRule.alternatives)) {
				acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.DATA_TYPE_ID);
			}
		}
		return super.highlightElement(varDeclaration, acceptor, cancelIndicator);
	}

	// semantic highlighting for assignment and expressions
	def protected dispatch boolean highlightElement(STFeatureExpression featuresExpression,
		IHighlightedPositionAcceptor acceptor, CancelIndicator cancelIndicator) {
		for (ILeafNode n : NodeModelUtils.findActualNodeFor(featuresExpression).getLeafNodes()) {
			if (( n.grammarElement?.eContainer == ga.STFeatureNameRule.alternatives) && (featuresExpression.call)) {
				acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.CALL_FUNCTION_ID);
			} else if (( n.grammarElement?.eContainer == ga.STFeatureNameRule.alternatives) &&
				(featuresExpression.feature instanceof STVarDeclarationImpl)) {
				acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.STATIC_VAR_ID);
			} else if (!(featuresExpression.call)) {
				acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.RETURN_FUNCTION_ID);
			}
		}
		return super.highlightElement(featuresExpression, acceptor, cancelIndicator);
	}

}
