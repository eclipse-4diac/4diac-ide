/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.services.ContractSpecGrammarAccess;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class ContractSpecSyntacticSequencer extends AbstractSyntacticSequencer {

	protected ContractSpecGrammarAccess grammarAccess;
	protected AbstractElementAlias match_Age_OnceKeyword_9_q;
	protected AbstractElementAlias match_Reaction_OnceKeyword_8_q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (ContractSpecGrammarAccess) access;
		match_Age_OnceKeyword_9_q = new TokenAlias(false, true, grammarAccess.getAgeAccess().getOnceKeyword_9());
		match_Reaction_OnceKeyword_8_q = new TokenAlias(false, true, grammarAccess.getReactionAccess().getOnceKeyword_8());
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
			if (match_Age_OnceKeyword_9_q.equals(syntax))
				emit_Age_OnceKeyword_9_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if (match_Reaction_OnceKeyword_8_q.equals(syntax))
				emit_Reaction_OnceKeyword_8_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * <pre>
	 * Ambiguous syntax:
	 *     'once'?
	 *
	 * This ambiguous syntax occurs at:
	 *     interval=Interval (ambiguity) 'using' 'clock' clock=[ClockDefinition|ID]
	 *     interval=Interval (ambiguity) (rule end)
	 *     interval=Interval (ambiguity) n=INT
	 
	 * </pre>
	 */
	protected void emit_Age_OnceKeyword_9_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * <pre>
	 * Ambiguous syntax:
	 *     'once'?
	 *
	 * This ambiguous syntax occurs at:
	 *     interval=Interval (ambiguity) 'using' 'clock' clock=[ClockDefinition|ID]
	 *     interval=Interval (ambiguity) (rule end)
	 *     interval=Interval (ambiguity) n=INT
	 
	 * </pre>
	 */
	protected void emit_Reaction_OnceKeyword_8_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
