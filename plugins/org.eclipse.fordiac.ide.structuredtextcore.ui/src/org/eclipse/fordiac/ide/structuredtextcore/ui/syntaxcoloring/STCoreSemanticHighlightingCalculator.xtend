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
 *       - distinguish variable scope
 *       - do not resolve features during highlighting
 *******************************************************************************/

package org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
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

	// semantic highlighting for Name of Method  
	def protected dispatch boolean highlightElement(STMethod stMethod, IHighlightedPositionAcceptor acceptor,
		CancelIndicator cancelIndicator) {
		for (ILeafNode n : NodeModelUtils.findActualNodeFor(stMethod).getLeafNodes()) {
			if ((n.grammarElement instanceof RuleCallImpl) && (n.parent instanceof CompositeNodeWithSemanticElement) &&
				(n.grammarElement.eContainer.eContainer != ga.STVarDeclarationRule.alternatives)) {
				acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.METHODS_NAME_ID);
			}
		}
		return super.highlightElement(stMethod, acceptor, cancelIndicator);
	}

	// semantic highlighting for Name of Function  
	def protected dispatch boolean highlightElement(STFunction stFunction, IHighlightedPositionAcceptor acceptor,
		CancelIndicator cancelIndicator) {
		for (ILeafNode n : NodeModelUtils.findActualNodeFor(stFunction).getLeafNodes()) {
			if ((n.grammarElement instanceof RuleCallImpl) && (n.parent instanceof CompositeNodeWithSemanticElement) &&
				(n.grammarElement.eContainer.eContainer != ga.STVarDeclarationRule.alternatives)) {
				acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.FUNCTIONS_NAME_ID);
			}
		}
		return super.highlightElement(stFunction, acceptor, cancelIndicator);
	}

	// semantic highlighting for variables and some data types
	def protected dispatch boolean highlightElement(STVarDeclaration varDeclaration,
		IHighlightedPositionAcceptor acceptor, CancelIndicator cancelIndicator) {
		for (ILeafNode n : NodeModelUtils.findActualNodeFor(varDeclaration).getLeafNodes()) {
			if (n.grammarElement == ga.STVarDeclarationAccess.nameIDTerminalRuleCall_1_0) {
				acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.LOCAL_VARIABLE_ID);
			}
			if ((n.grammarElement?.eContainer == ga.STAnyTypeRule.alternatives)) {
				acceptor.addPosition(n.getOffset(), n.getLength(), STCoreHighlightingStyles.DATA_TYPE_ID);
			}
		}
		return super.highlightElement(varDeclaration, acceptor, cancelIndicator);
	}

	// semantic highlighting for calls of functions and methods and static variables
	def protected dispatch boolean highlightElement(STFeatureExpression featuresExpression,
		IHighlightedPositionAcceptor acceptor, CancelIndicator cancelIndicator) {
		val style = switch (feature : featuresExpression.feature) {
			VarDeclaration:
				STCoreHighlightingStyles.MEMBER_VARIABLE_ID
			FB:
				STCoreHighlightingStyles.CALL_FUNCTION_BLOCK_ID
			STMethod case (featuresExpression.call):
				STCoreHighlightingStyles.CALL_METHOD_ID
			STMethod case !featuresExpression.call:
				STCoreHighlightingStyles.RETURN_METHOD_ID
			STStandardFunction:
				STCoreHighlightingStyles.CALL_FUNCTION_ID
			STFunction case featuresExpression.call:
				STCoreHighlightingStyles.CALL_FUNCTION_ID
			STFunction case !featuresExpression.call:
				STCoreHighlightingStyles.RETURN_FUNCTION_ID
			STVarDeclaration case feature.eContainer instanceof STVarGlobalDeclarationBlock:
				STCoreHighlightingStyles.GLOBAL_CONST_ID
			STVarDeclaration:
				STCoreHighlightingStyles.LOCAL_VARIABLE_ID
		}

		for (ILeafNode n : NodeModelUtils.findActualNodeFor(featuresExpression).getLeafNodes()) {
			if (!n.hidden) {
				acceptor.addPosition(n.getOffset(), n.getLength(), style)
			}
		}
	}
}
