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
 *       - do not resolve features during highlighting
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.syntaxcoloring;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.xtext.ide.editor.syntaxcoloring.DefaultSemanticHighlightingCalculator;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.impl.RuleCallImpl;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.impl.CompositeNodeWithSemanticElement;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;

public class STCoreSemanticHighlightingCalculator extends DefaultSemanticHighlightingCalculator {
	@Inject
	private STCoreGrammarAccess grammarAccess;

	protected boolean highlightElement(final STMethod stMethod, final IHighlightedPositionAcceptor acceptor,
			final CancelIndicator cancelIndicator) {
		final Iterable<ILeafNode> leafNodes = NodeModelUtils.findActualNodeFor(stMethod).getLeafNodes();
		for (final ILeafNode node : leafNodes) {
			if (node.getGrammarElement() instanceof RuleCallImpl
					&& node.getParent() instanceof CompositeNodeWithSemanticElement
					&& !Objects.equals(node.getGrammarElement().eContainer().eContainer(),
							grammarAccess.getSTVarDeclarationRule().getAlternatives())) {
				acceptor.addPosition(node.getOffset(), node.getLength(), STCoreHighlightingStyles.METHODS_NAME_ID);
			}
		}
		return super.highlightElement(stMethod, acceptor, cancelIndicator);
	}

	protected boolean highlightElement(final STFunction stFunction, final IHighlightedPositionAcceptor acceptor,
			final CancelIndicator cancelIndicator) {
		final Iterable<ILeafNode> leafNodes = NodeModelUtils.findActualNodeFor(stFunction).getLeafNodes();
		for (final ILeafNode node : leafNodes) {
			if (node.getGrammarElement() instanceof RuleCallImpl
					&& node.getParent() instanceof CompositeNodeWithSemanticElement
					&& !Objects.equals(node.getGrammarElement().eContainer().eContainer(),
							grammarAccess.getSTVarDeclarationRule().getAlternatives())) {
				acceptor.addPosition(node.getOffset(), node.getLength(), STCoreHighlightingStyles.FUNCTIONS_NAME_ID);
			}
		}
		return super.highlightElement(stFunction, acceptor, cancelIndicator);
	}

	protected boolean highlightElement(final STVarDeclaration varDeclaration,
			final IHighlightedPositionAcceptor acceptor, final CancelIndicator cancelIndicator) {
		final Iterable<ILeafNode> leafNodes = NodeModelUtils.findActualNodeFor(varDeclaration).getLeafNodes();
		for (final ILeafNode node : leafNodes) {
			if (Objects.equals(node.getGrammarElement(),
					grammarAccess.getSTVarDeclarationAccess().getNameIDTerminalRuleCall_1_0())) {
				acceptor.addPosition(node.getOffset(), node.getLength(), STCoreHighlightingStyles.LOCAL_VARIABLE_ID);
			}
			if (node.getGrammarElement() != null && Objects.equals(node.getGrammarElement().eContainer(),
					grammarAccess.getSTAnyTypeRule().getAlternatives())) {
				acceptor.addPosition(node.getOffset(), node.getLength(), STCoreHighlightingStyles.DATA_TYPE_ID);
			}
		}
		return super.highlightElement(varDeclaration, acceptor, cancelIndicator);
	}

	protected boolean highlightElement(final STFeatureExpression expression,
			final IHighlightedPositionAcceptor acceptor, final CancelIndicator cancelIndicator) {
		final String style = switch (expression.getFeature()) {
		case final VarDeclaration unused -> STCoreHighlightingStyles.MEMBER_VARIABLE_ID;
		case final FB unused -> STCoreHighlightingStyles.CALL_FUNCTION_BLOCK_ID;
		case final STMethod method when expression.isCall() -> STCoreHighlightingStyles.CALL_METHOD_ID;
		case final STMethod unused -> STCoreHighlightingStyles.RETURN_METHOD_ID;
		case final STStandardFunction unused -> STCoreHighlightingStyles.CALL_FUNCTION_ID;
		case final STFunction unused when expression.isCall() -> STCoreHighlightingStyles.CALL_FUNCTION_ID;
		case final STFunction unused -> STCoreHighlightingStyles.RETURN_FUNCTION_ID;
		case final STVarDeclaration varDeclaration when varDeclaration
				.eContainer() instanceof STVarGlobalDeclarationBlock ->
			STCoreHighlightingStyles.GLOBAL_CONST_ID;
		case final STVarDeclaration unused -> STCoreHighlightingStyles.LOCAL_VARIABLE_ID;
		case null, default -> null;
		};
		final Iterable<ILeafNode> leafNodes = NodeModelUtils.findActualNodeFor(expression).getLeafNodes();
		for (final ILeafNode node : leafNodes) {
			if (!node.isHidden()) {
				acceptor.addPosition(node.getOffset(), node.getLength(), style);
			}
		}
		return super.highlightElement(expression, acceptor, cancelIndicator);
	}

	@Override
	protected boolean highlightElement(final EObject object, final IHighlightedPositionAcceptor acceptor,
			final CancelIndicator cancelIndicator) {
		return switch (object) {
		case final STMethod element -> highlightElement(element, acceptor, cancelIndicator);
		case final STFeatureExpression element -> highlightElement(element, acceptor, cancelIndicator);
		case final STVarDeclaration element -> highlightElement(element, acceptor, cancelIndicator);
		case final STFunction element -> highlightElement(element, acceptor, cancelIndicator);
		case null, default -> super.highlightElement(object, acceptor, cancelIndicator);
		};
	}
}
