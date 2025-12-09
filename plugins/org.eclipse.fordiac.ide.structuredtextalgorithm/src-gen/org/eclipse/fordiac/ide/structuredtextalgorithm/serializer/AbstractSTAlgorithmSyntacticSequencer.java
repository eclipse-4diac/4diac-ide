/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public abstract class AbstractSTAlgorithmSyntacticSequencer extends AbstractSyntacticSequencer {

	protected STAlgorithmGrammarAccess grammarAccess;
	protected AbstractElementAlias match_STPrimaryExpression_LeftParenthesisKeyword_0_0_a;
	protected AbstractElementAlias match_STPrimaryExpression_LeftParenthesisKeyword_0_0_p;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (STAlgorithmGrammarAccess) access;
		match_STPrimaryExpression_LeftParenthesisKeyword_0_0_a = new TokenAlias(true, true, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
		match_STPrimaryExpression_LeftParenthesisKeyword_0_0_p = new TokenAlias(true, false, grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0());
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_STPrimaryExpression_LeftParenthesisKeyword_0_0_a.equals(syntax))
				emit_STPrimaryExpression_LeftParenthesisKeyword_0_0_a(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_STPrimaryExpression_LeftParenthesisKeyword_0_0_p.equals(syntax))
				emit_STPrimaryExpression_LeftParenthesisKeyword_0_0_p(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * <pre>
	 * Ambiguous syntax:
	 *     '('*
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) feature=STBuiltinFeature
	 *     (rule start) (ambiguity) feature=[INamedElement|STFeatureName]
	 *     (rule start) (ambiguity) op=UnaryOperator
	 *     (rule start) (ambiguity) type=[DataType|STAnyCharsType]
	 *     (rule start) (ambiguity) type=[DataType|STDateAndTimeType]
	 *     (rule start) (ambiguity) type=[DataType|STDateLiteralType]
	 *     (rule start) (ambiguity) type=[DataType|STNumericLiteralType]
	 *     (rule start) (ambiguity) type=[DataType|STTimeLiteralType]
	 *     (rule start) (ambiguity) type=[DataType|STTimeOfDayType]
	 *     (rule start) (ambiguity) value=Numeric
	 *     (rule start) (ambiguity) value=STRING
	 *     (rule start) (ambiguity) value=SignedNumeric
	 *     (rule start) (ambiguity) value=[EnumeratedValue|EnumValue]
	 *     (rule start) (ambiguity) {STArrayAccessExpression.receiver=}
	 *     (rule start) (ambiguity) {STBinaryExpression.left=}
	 *     (rule start) (ambiguity) {STMemberAccessExpression.receiver=}
	 
	 * </pre>
	 */
	protected void emit_STPrimaryExpression_LeftParenthesisKeyword_0_0_a(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * <pre>
	 * Ambiguous syntax:
	 *     '('+
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) op=UnaryOperator
	 *     (rule start) (ambiguity) type=[DataType|STAnyCharsType]
	 *     (rule start) (ambiguity) type=[DataType|STDateAndTimeType]
	 *     (rule start) (ambiguity) type=[DataType|STDateLiteralType]
	 *     (rule start) (ambiguity) type=[DataType|STNumericLiteralType]
	 *     (rule start) (ambiguity) type=[DataType|STTimeLiteralType]
	 *     (rule start) (ambiguity) type=[DataType|STTimeOfDayType]
	 *     (rule start) (ambiguity) value=Numeric
	 *     (rule start) (ambiguity) value=STRING
	 *     (rule start) (ambiguity) value=SignedNumeric
	 *     (rule start) (ambiguity) value=[EnumeratedValue|EnumValue]
	 *     (rule start) (ambiguity) {STArrayAccessExpression.receiver=}
	 *     (rule start) (ambiguity) {STBinaryExpression.left=}
	 *     (rule start) (ambiguity) {STMemberAccessExpression.receiver=}
	 
	 * </pre>
	 */
	protected void emit_STPrimaryExpression_LeftParenthesisKeyword_0_0_p(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
