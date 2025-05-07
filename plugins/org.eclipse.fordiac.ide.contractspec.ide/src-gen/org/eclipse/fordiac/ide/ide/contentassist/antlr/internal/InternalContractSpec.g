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
grammar InternalContractSpec;

options {
	superClass=AbstractInternalContentAssistParser;
}

@lexer::header {
package org.eclipse.fordiac.ide.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
}

@parser::header {
package org.eclipse.fordiac.ide.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.eclipse.fordiac.ide.services.ContractSpecGrammarAccess;

}
@parser::members {
	private ContractSpecGrammarAccess grammarAccess;

	public void setGrammarAccess(ContractSpecGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		return tokenName;
	}
}

// Entry rule entryRuleModel
entryRuleModel
:
{ before(grammarAccess.getModelRule()); }
	 ruleModel
{ after(grammarAccess.getModelRule()); } 
	 EOF 
;

// Rule Model
ruleModel 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getModelAccess().getTimeSpecAssignment()); }
		(rule__Model__TimeSpecAssignment)*
		{ after(grammarAccess.getModelAccess().getTimeSpecAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTimeSpec
entryRuleTimeSpec
:
{ before(grammarAccess.getTimeSpecRule()); }
	 ruleTimeSpec
{ after(grammarAccess.getTimeSpecRule()); } 
	 EOF 
;

// Rule TimeSpec
ruleTimeSpec 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTimeSpecAccess().getAlternatives()); }
		(rule__TimeSpec__Alternatives)
		{ after(grammarAccess.getTimeSpecAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSingleEvent
entryRuleSingleEvent
:
{ before(grammarAccess.getSingleEventRule()); }
	 ruleSingleEvent
{ after(grammarAccess.getSingleEventRule()); } 
	 EOF 
;

// Rule SingleEvent
ruleSingleEvent 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSingleEventAccess().getGroup()); }
		(rule__SingleEvent__Group__0)
		{ after(grammarAccess.getSingleEventAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleRepetition
entryRuleRepetition
:
{ before(grammarAccess.getRepetitionRule()); }
	 ruleRepetition
{ after(grammarAccess.getRepetitionRule()); } 
	 EOF 
;

// Rule Repetition
ruleRepetition 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getRepetitionAccess().getGroup()); }
		(rule__Repetition__Group__0)
		{ after(grammarAccess.getRepetitionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleRepetitionOptions
entryRuleRepetitionOptions
:
{ before(grammarAccess.getRepetitionOptionsRule()); }
	 ruleRepetitionOptions
{ after(grammarAccess.getRepetitionOptionsRule()); } 
	 EOF 
;

// Rule RepetitionOptions
ruleRepetitionOptions 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getRepetitionOptionsAccess().getAlternatives()); }
		(rule__RepetitionOptions__Alternatives)
		{ after(grammarAccess.getRepetitionOptionsAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleJitter
entryRuleJitter
:
{ before(grammarAccess.getJitterRule()); }
	 ruleJitter
{ after(grammarAccess.getJitterRule()); } 
	 EOF 
;

// Rule Jitter
ruleJitter 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getJitterAccess().getGroup()); }
		(rule__Jitter__Group__0)
		{ after(grammarAccess.getJitterAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleOffset
entryRuleOffset
:
{ before(grammarAccess.getOffsetRule()); }
	 ruleOffset
{ after(grammarAccess.getOffsetRule()); } 
	 EOF 
;

// Rule Offset
ruleOffset 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getOffsetAccess().getGroup()); }
		(rule__Offset__Group__0)
		{ after(grammarAccess.getOffsetAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleReaction
entryRuleReaction
:
{ before(grammarAccess.getReactionRule()); }
	 ruleReaction
{ after(grammarAccess.getReactionRule()); } 
	 EOF 
;

// Rule Reaction
ruleReaction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getReactionAccess().getGroup()); }
		(rule__Reaction__Group__0)
		{ after(grammarAccess.getReactionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleAge
entryRuleAge
:
{ before(grammarAccess.getAgeRule()); }
	 ruleAge
{ after(grammarAccess.getAgeRule()); } 
	 EOF 
;

// Rule Age
ruleAge 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getAgeAccess().getGroup()); }
		(rule__Age__Group__0)
		{ after(grammarAccess.getAgeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCausalReaction
entryRuleCausalReaction
:
{ before(grammarAccess.getCausalReactionRule()); }
	 ruleCausalReaction
{ after(grammarAccess.getCausalReactionRule()); } 
	 EOF 
;

// Rule CausalReaction
ruleCausalReaction 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCausalReactionAccess().getGroup()); }
		(rule__CausalReaction__Group__0)
		{ after(grammarAccess.getCausalReactionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCausalAge
entryRuleCausalAge
:
{ before(grammarAccess.getCausalAgeRule()); }
	 ruleCausalAge
{ after(grammarAccess.getCausalAgeRule()); } 
	 EOF 
;

// Rule CausalAge
ruleCausalAge 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCausalAgeAccess().getGroup()); }
		(rule__CausalAge__Group__0)
		{ after(grammarAccess.getCausalAgeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEventExpr
entryRuleEventExpr
:
{ before(grammarAccess.getEventExprRule()); }
	 ruleEventExpr
{ after(grammarAccess.getEventExprRule()); } 
	 EOF 
;

// Rule EventExpr
ruleEventExpr 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEventExprAccess().getAlternatives()); }
		(rule__EventExpr__Alternatives)
		{ after(grammarAccess.getEventExprAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEventList
entryRuleEventList
:
{ before(grammarAccess.getEventListRule()); }
	 ruleEventList
{ after(grammarAccess.getEventListRule()); } 
	 EOF 
;

// Rule EventList
ruleEventList 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEventListAccess().getGroup()); }
		(rule__EventList__Group__0)
		{ after(grammarAccess.getEventListAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEventSpec
entryRuleEventSpec
:
{ before(grammarAccess.getEventSpecRule()); }
	 ruleEventSpec
{ after(grammarAccess.getEventSpecRule()); } 
	 EOF 
;

// Rule EventSpec
ruleEventSpec 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEventSpecAccess().getGroup()); }
		(rule__EventSpec__Group__0)
		{ after(grammarAccess.getEventSpecAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleInterval
entryRuleInterval
:
{ before(grammarAccess.getIntervalRule()); }
	 ruleInterval
{ after(grammarAccess.getIntervalRule()); } 
	 EOF 
;

// Rule Interval
ruleInterval 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getIntervalAccess().getAlternatives()); }
		(rule__Interval__Alternatives)
		{ after(grammarAccess.getIntervalAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTimeExpr
entryRuleTimeExpr
:
{ before(grammarAccess.getTimeExprRule()); }
	 ruleTimeExpr
{ after(grammarAccess.getTimeExprRule()); } 
	 EOF 
;

// Rule TimeExpr
ruleTimeExpr 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTimeExprAccess().getGroup()); }
		(rule__TimeExpr__Group__0)
		{ after(grammarAccess.getTimeExprAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleBoundary
entryRuleBoundary
:
{ before(grammarAccess.getBoundaryRule()); }
	 ruleBoundary
{ after(grammarAccess.getBoundaryRule()); } 
	 EOF 
;

// Rule Boundary
ruleBoundary 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getBoundaryAccess().getAlternatives()); }
		(rule__Boundary__Alternatives)
		{ after(grammarAccess.getBoundaryAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleValue
entryRuleValue
:
{ before(grammarAccess.getValueRule()); }
	 ruleValue
{ after(grammarAccess.getValueRule()); } 
	 EOF 
;

// Rule Value
ruleValue 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getValueAccess().getGroup()); }
		(rule__Value__Group__0)
		{ after(grammarAccess.getValueAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleUnit
entryRuleUnit
:
{ before(grammarAccess.getUnitRule()); }
	 ruleUnit
{ after(grammarAccess.getUnitRule()); } 
	 EOF 
;

// Rule Unit
ruleUnit 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getUnitAccess().getAlternatives()); }
		(rule__Unit__Alternatives)
		{ after(grammarAccess.getUnitAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCausalFuncDecl
entryRuleCausalFuncDecl
:
{ before(grammarAccess.getCausalFuncDeclRule()); }
	 ruleCausalFuncDecl
{ after(grammarAccess.getCausalFuncDeclRule()); } 
	 EOF 
;

// Rule CausalFuncDecl
ruleCausalFuncDecl 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCausalFuncDeclAccess().getGroup()); }
		(rule__CausalFuncDecl__Group__0)
		{ after(grammarAccess.getCausalFuncDeclAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCausalFuncName
entryRuleCausalFuncName
:
{ before(grammarAccess.getCausalFuncNameRule()); }
	 ruleCausalFuncName
{ after(grammarAccess.getCausalFuncNameRule()); } 
	 EOF 
;

// Rule CausalFuncName
ruleCausalFuncName 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCausalFuncNameAccess().getAlternatives()); }
		(rule__CausalFuncName__Alternatives)
		{ after(grammarAccess.getCausalFuncNameAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleCausalRelation
entryRuleCausalRelation
:
{ before(grammarAccess.getCausalRelationRule()); }
	 ruleCausalRelation
{ after(grammarAccess.getCausalRelationRule()); } 
	 EOF 
;

// Rule CausalRelation
ruleCausalRelation 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getCausalRelationAccess().getAlternatives()); }
		(rule__CausalRelation__Alternatives)
		{ after(grammarAccess.getCausalRelationAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleClockDefinition
entryRuleClockDefinition
:
{ before(grammarAccess.getClockDefinitionRule()); }
	 ruleClockDefinition
{ after(grammarAccess.getClockDefinitionRule()); } 
	 EOF 
;

// Rule ClockDefinition
ruleClockDefinition 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getClockDefinitionAccess().getGroup()); }
		(rule__ClockDefinition__Group__0)
		{ after(grammarAccess.getClockDefinitionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeSpec__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTimeSpecAccess().getSingleEventParserRuleCall_0()); }
		ruleSingleEvent
		{ after(grammarAccess.getTimeSpecAccess().getSingleEventParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getTimeSpecAccess().getRepetitionParserRuleCall_1()); }
		ruleRepetition
		{ after(grammarAccess.getTimeSpecAccess().getRepetitionParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getTimeSpecAccess().getReactionParserRuleCall_2()); }
		ruleReaction
		{ after(grammarAccess.getTimeSpecAccess().getReactionParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getTimeSpecAccess().getAgeParserRuleCall_3()); }
		ruleAge
		{ after(grammarAccess.getTimeSpecAccess().getAgeParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getTimeSpecAccess().getCausalReactionParserRuleCall_4()); }
		ruleCausalReaction
		{ after(grammarAccess.getTimeSpecAccess().getCausalReactionParserRuleCall_4()); }
	)
	|
	(
		{ before(grammarAccess.getTimeSpecAccess().getCausalAgeParserRuleCall_5()); }
		ruleCausalAge
		{ after(grammarAccess.getTimeSpecAccess().getCausalAgeParserRuleCall_5()); }
	)
	|
	(
		{ before(grammarAccess.getTimeSpecAccess().getCausalFuncDeclParserRuleCall_6()); }
		ruleCausalFuncDecl
		{ after(grammarAccess.getTimeSpecAccess().getCausalFuncDeclParserRuleCall_6()); }
	)
	|
	(
		{ before(grammarAccess.getTimeSpecAccess().getClockDefinitionParserRuleCall_7()); }
		ruleClockDefinition
		{ after(grammarAccess.getTimeSpecAccess().getClockDefinitionParserRuleCall_7()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRepetitionOptionsAccess().getGroup_0()); }
		(rule__RepetitionOptions__Group_0__0)
		{ after(grammarAccess.getRepetitionOptionsAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getRepetitionOptionsAccess().getGroup_1()); }
		(rule__RepetitionOptions__Group_1__0)
		{ after(grammarAccess.getRepetitionOptionsAccess().getGroup_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventExprAccess().getEventAssignment_0()); }
		(rule__EventExpr__EventAssignment_0)
		{ after(grammarAccess.getEventExprAccess().getEventAssignment_0()); }
	)
	|
	(
		{ before(grammarAccess.getEventExprAccess().getGroup_1()); }
		(rule__EventExpr__Group_1__0)
		{ after(grammarAccess.getEventExprAccess().getGroup_1()); }
	)
	|
	(
		{ before(grammarAccess.getEventExprAccess().getGroup_2()); }
		(rule__EventExpr__Group_2__0)
		{ after(grammarAccess.getEventExprAccess().getGroup_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIntervalAccess().getTimeAssignment_0()); }
		(rule__Interval__TimeAssignment_0)
		{ after(grammarAccess.getIntervalAccess().getTimeAssignment_0()); }
	)
	|
	(
		{ before(grammarAccess.getIntervalAccess().getGroup_1()); }
		(rule__Interval__Group_1__0)
		{ after(grammarAccess.getIntervalAccess().getGroup_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Boundary__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getBoundaryAccess().getLeftSquareBracketKeyword_0()); }
		'['
		{ after(grammarAccess.getBoundaryAccess().getLeftSquareBracketKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getBoundaryAccess().getRightSquareBracketKeyword_1()); }
		']'
		{ after(grammarAccess.getBoundaryAccess().getRightSquareBracketKeyword_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Unit__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getUnitAccess().getSKeyword_0()); }
		's'
		{ after(grammarAccess.getUnitAccess().getSKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getUnitAccess().getMsKeyword_1()); }
		'ms'
		{ after(grammarAccess.getUnitAccess().getMsKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getUnitAccess().getUsKeyword_2()); }
		'us'
		{ after(grammarAccess.getUnitAccess().getUsKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getUnitAccess().getNsKeyword_3()); }
		'ns'
		{ after(grammarAccess.getUnitAccess().getNsKeyword_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncName__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalFuncNameAccess().getVerticalLineGreaterThanSignKeyword_0()); }
		'|>'
		{ after(grammarAccess.getCausalFuncNameAccess().getVerticalLineGreaterThanSignKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getCausalFuncNameAccess().getLessThanSignVerticalLineKeyword_1()); }
		'<|'
		{ after(grammarAccess.getCausalFuncNameAccess().getLessThanSignVerticalLineKeyword_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalRelation__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalRelationAccess().getFIFOKeyword_0()); }
		'FIFO'
		{ after(grammarAccess.getCausalRelationAccess().getFIFOKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getCausalRelationAccess().getLIFOKeyword_1()); }
		'LIFO'
		{ after(grammarAccess.getCausalRelationAccess().getLIFOKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getCausalRelationAccess().getIDKeyword_2()); }
		'ID'
		{ after(grammarAccess.getCausalRelationAccess().getIDKeyword_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SingleEvent__Group__0__Impl
	rule__SingleEvent__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSingleEventAccess().getEventsAssignment_0()); }
	(rule__SingleEvent__EventsAssignment_0)
	{ after(grammarAccess.getSingleEventAccess().getEventsAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SingleEvent__Group__1__Impl
	rule__SingleEvent__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSingleEventAccess().getOccursKeyword_1()); }
	'occurs'
	{ after(grammarAccess.getSingleEventAccess().getOccursKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SingleEvent__Group__2__Impl
	rule__SingleEvent__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSingleEventAccess().getWithinKeyword_2()); }
	'within'
	{ after(grammarAccess.getSingleEventAccess().getWithinKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SingleEvent__Group__3__Impl
	rule__SingleEvent__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSingleEventAccess().getIntervalAssignment_3()); }
	(rule__SingleEvent__IntervalAssignment_3)
	{ after(grammarAccess.getSingleEventAccess().getIntervalAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SingleEvent__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSingleEventAccess().getGroup_4()); }
	(rule__SingleEvent__Group_4__0)?
	{ after(grammarAccess.getSingleEventAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SingleEvent__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SingleEvent__Group_4__0__Impl
	rule__SingleEvent__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSingleEventAccess().getUsingKeyword_4_0()); }
	'using'
	{ after(grammarAccess.getSingleEventAccess().getUsingKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SingleEvent__Group_4__1__Impl
	rule__SingleEvent__Group_4__2
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSingleEventAccess().getClockKeyword_4_1()); }
	'clock'
	{ after(grammarAccess.getSingleEventAccess().getClockKeyword_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group_4__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SingleEvent__Group_4__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__Group_4__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSingleEventAccess().getClockAssignment_4_2()); }
	(rule__SingleEvent__ClockAssignment_4_2)
	{ after(grammarAccess.getSingleEventAccess().getClockAssignment_4_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Repetition__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group__0__Impl
	rule__Repetition__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getEventsAssignment_0()); }
	(rule__Repetition__EventsAssignment_0)
	{ after(grammarAccess.getRepetitionAccess().getEventsAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group__1__Impl
	rule__Repetition__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getOccursKeyword_1()); }
	'occurs'
	{ after(grammarAccess.getRepetitionAccess().getOccursKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group__2__Impl
	rule__Repetition__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getEveryKeyword_2()); }
	'every'
	{ after(grammarAccess.getRepetitionAccess().getEveryKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group__3__Impl
	rule__Repetition__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getIntervalAssignment_3()); }
	(rule__Repetition__IntervalAssignment_3)
	{ after(grammarAccess.getRepetitionAccess().getIntervalAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group__4__Impl
	rule__Repetition__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getGroup_4()); }
	(rule__Repetition__Group_4__0)?
	{ after(grammarAccess.getRepetitionAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getGroup_5()); }
	(rule__Repetition__Group_5__0)?
	{ after(grammarAccess.getRepetitionAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Repetition__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group_4__0__Impl
	rule__Repetition__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getWithKeyword_4_0()); }
	'with'
	{ after(grammarAccess.getRepetitionAccess().getWithKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group_4__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getRepetitionOptionsAssignment_4_1()); }
	(rule__Repetition__RepetitionOptionsAssignment_4_1)
	{ after(grammarAccess.getRepetitionAccess().getRepetitionOptionsAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Repetition__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group_5__0__Impl
	rule__Repetition__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getUsingKeyword_5_0()); }
	'using'
	{ after(grammarAccess.getRepetitionAccess().getUsingKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group_5__1__Impl
	rule__Repetition__Group_5__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getClockKeyword_5_1()); }
	'clock'
	{ after(grammarAccess.getRepetitionAccess().getClockKeyword_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group_5__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Repetition__Group_5__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__Group_5__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionAccess().getClockAssignment_5_2()); }
	(rule__Repetition__ClockAssignment_5_2)
	{ after(grammarAccess.getRepetitionAccess().getClockAssignment_5_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RepetitionOptions__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RepetitionOptions__Group_0__0__Impl
	rule__RepetitionOptions__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionOptionsAccess().getJitterAssignment_0_0()); }
	(rule__RepetitionOptions__JitterAssignment_0_0)
	{ after(grammarAccess.getRepetitionOptionsAccess().getJitterAssignment_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RepetitionOptions__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionOptionsAccess().getGroup_0_1()); }
	(rule__RepetitionOptions__Group_0_1__0)?
	{ after(grammarAccess.getRepetitionOptionsAccess().getGroup_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RepetitionOptions__Group_0_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RepetitionOptions__Group_0_1__0__Impl
	rule__RepetitionOptions__Group_0_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_0_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionOptionsAccess().getAndKeyword_0_1_0()); }
	'and'
	{ after(grammarAccess.getRepetitionOptionsAccess().getAndKeyword_0_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_0_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RepetitionOptions__Group_0_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_0_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionOptionsAccess().getOffsetAssignment_0_1_1()); }
	(rule__RepetitionOptions__OffsetAssignment_0_1_1)
	{ after(grammarAccess.getRepetitionOptionsAccess().getOffsetAssignment_0_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RepetitionOptions__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RepetitionOptions__Group_1__0__Impl
	rule__RepetitionOptions__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionOptionsAccess().getOffsetAssignment_1_0()); }
	(rule__RepetitionOptions__OffsetAssignment_1_0)
	{ after(grammarAccess.getRepetitionOptionsAccess().getOffsetAssignment_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RepetitionOptions__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionOptionsAccess().getGroup_1_1()); }
	(rule__RepetitionOptions__Group_1_1__0)?
	{ after(grammarAccess.getRepetitionOptionsAccess().getGroup_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__RepetitionOptions__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RepetitionOptions__Group_1_1__0__Impl
	rule__RepetitionOptions__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionOptionsAccess().getAndKeyword_1_1_0()); }
	'and'
	{ after(grammarAccess.getRepetitionOptionsAccess().getAndKeyword_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__RepetitionOptions__Group_1_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getRepetitionOptionsAccess().getJitterAssignment_1_1_1()); }
	(rule__RepetitionOptions__JitterAssignment_1_1_1)
	{ after(grammarAccess.getRepetitionOptionsAccess().getJitterAssignment_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Jitter__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Jitter__Group__0__Impl
	rule__Jitter__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Jitter__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getJitterAccess().getJitterKeyword_0()); }
	'jitter'
	{ after(grammarAccess.getJitterAccess().getJitterKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Jitter__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Jitter__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Jitter__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getJitterAccess().getTimeAssignment_1()); }
	(rule__Jitter__TimeAssignment_1)
	{ after(grammarAccess.getJitterAccess().getTimeAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Offset__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Offset__Group__0__Impl
	rule__Offset__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Offset__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getOffsetAccess().getOffsetKeyword_0()); }
	'offset'
	{ after(grammarAccess.getOffsetAccess().getOffsetKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Offset__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Offset__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Offset__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getOffsetAccess().getIntervalAssignment_1()); }
	(rule__Offset__IntervalAssignment_1)
	{ after(grammarAccess.getOffsetAccess().getIntervalAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Reaction__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__0__Impl
	rule__Reaction__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getWheneverKeyword_0()); }
	'whenever'
	{ after(grammarAccess.getReactionAccess().getWheneverKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__1__Impl
	rule__Reaction__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getTriggerAssignment_1()); }
	(rule__Reaction__TriggerAssignment_1)
	{ after(grammarAccess.getReactionAccess().getTriggerAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__2__Impl
	rule__Reaction__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getOccursKeyword_2()); }
	'occurs'
	{ after(grammarAccess.getReactionAccess().getOccursKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__3__Impl
	rule__Reaction__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getThenKeyword_3()); }
	'then'
	{ after(grammarAccess.getReactionAccess().getThenKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__4__Impl
	rule__Reaction__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getReactionAssignment_4()); }
	(rule__Reaction__ReactionAssignment_4)
	{ after(grammarAccess.getReactionAccess().getReactionAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__5__Impl
	rule__Reaction__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getOccursKeyword_5()); }
	'occurs'
	{ after(grammarAccess.getReactionAccess().getOccursKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__6__Impl
	rule__Reaction__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getWithinKeyword_6()); }
	'within'
	{ after(grammarAccess.getReactionAccess().getWithinKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__7__Impl
	rule__Reaction__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getIntervalAssignment_7()); }
	(rule__Reaction__IntervalAssignment_7)
	{ after(grammarAccess.getReactionAccess().getIntervalAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__8__Impl
	rule__Reaction__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getOnceKeyword_8()); }
	('once')?
	{ after(grammarAccess.getReactionAccess().getOnceKeyword_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__9__Impl
	rule__Reaction__Group__10
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getGroup_9()); }
	(rule__Reaction__Group_9__0)?
	{ after(grammarAccess.getReactionAccess().getGroup_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__10
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group__10__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group__10__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getGroup_10()); }
	(rule__Reaction__Group_10__0)?
	{ after(grammarAccess.getReactionAccess().getGroup_10()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Reaction__Group_9__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group_9__0__Impl
	rule__Reaction__Group_9__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_9__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getNAssignment_9_0()); }
	(rule__Reaction__NAssignment_9_0)
	{ after(grammarAccess.getReactionAccess().getNAssignment_9_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_9__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group_9__1__Impl
	rule__Reaction__Group_9__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_9__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getOutKeyword_9_1()); }
	'out'
	{ after(grammarAccess.getReactionAccess().getOutKeyword_9_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_9__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group_9__2__Impl
	rule__Reaction__Group_9__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_9__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getOfKeyword_9_2()); }
	'of'
	{ after(grammarAccess.getReactionAccess().getOfKeyword_9_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_9__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group_9__3__Impl
	rule__Reaction__Group_9__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_9__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getOutOfAssignment_9_3()); }
	(rule__Reaction__OutOfAssignment_9_3)
	{ after(grammarAccess.getReactionAccess().getOutOfAssignment_9_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_9__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group_9__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_9__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getTimesKeyword_9_4()); }
	'times'
	{ after(grammarAccess.getReactionAccess().getTimesKeyword_9_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Reaction__Group_10__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group_10__0__Impl
	rule__Reaction__Group_10__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_10__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getUsingKeyword_10_0()); }
	'using'
	{ after(grammarAccess.getReactionAccess().getUsingKeyword_10_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_10__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group_10__1__Impl
	rule__Reaction__Group_10__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_10__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getClockKeyword_10_1()); }
	'clock'
	{ after(grammarAccess.getReactionAccess().getClockKeyword_10_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_10__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Reaction__Group_10__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__Group_10__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getReactionAccess().getClockAssignment_10_2()); }
	(rule__Reaction__ClockAssignment_10_2)
	{ after(grammarAccess.getReactionAccess().getClockAssignment_10_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Age__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__0__Impl
	rule__Age__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getWheneverKeyword_0()); }
	'whenever'
	{ after(grammarAccess.getAgeAccess().getWheneverKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__1__Impl
	rule__Age__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getTriggerAssignment_1()); }
	(rule__Age__TriggerAssignment_1)
	{ after(grammarAccess.getAgeAccess().getTriggerAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__2__Impl
	rule__Age__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getOccursKeyword_2()); }
	'occurs'
	{ after(grammarAccess.getAgeAccess().getOccursKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__3__Impl
	rule__Age__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getThenKeyword_3()); }
	'then'
	{ after(grammarAccess.getAgeAccess().getThenKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__4__Impl
	rule__Age__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getReactionAssignment_4()); }
	(rule__Age__ReactionAssignment_4)
	{ after(grammarAccess.getAgeAccess().getReactionAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__5__Impl
	rule__Age__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getHasKeyword_5()); }
	'has'
	{ after(grammarAccess.getAgeAccess().getHasKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__6__Impl
	rule__Age__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getOccurredKeyword_6()); }
	'occurred'
	{ after(grammarAccess.getAgeAccess().getOccurredKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__7__Impl
	rule__Age__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getWithinKeyword_7()); }
	'within'
	{ after(grammarAccess.getAgeAccess().getWithinKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__8__Impl
	rule__Age__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getIntervalAssignment_8()); }
	(rule__Age__IntervalAssignment_8)
	{ after(grammarAccess.getAgeAccess().getIntervalAssignment_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__9__Impl
	rule__Age__Group__10
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getOnceKeyword_9()); }
	('once')?
	{ after(grammarAccess.getAgeAccess().getOnceKeyword_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__10
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__10__Impl
	rule__Age__Group__11
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__10__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getGroup_10()); }
	(rule__Age__Group_10__0)?
	{ after(grammarAccess.getAgeAccess().getGroup_10()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__11
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group__11__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group__11__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getGroup_11()); }
	(rule__Age__Group_11__0)?
	{ after(grammarAccess.getAgeAccess().getGroup_11()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Age__Group_10__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group_10__0__Impl
	rule__Age__Group_10__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_10__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getNAssignment_10_0()); }
	(rule__Age__NAssignment_10_0)
	{ after(grammarAccess.getAgeAccess().getNAssignment_10_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_10__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group_10__1__Impl
	rule__Age__Group_10__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_10__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getOutKeyword_10_1()); }
	'out'
	{ after(grammarAccess.getAgeAccess().getOutKeyword_10_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_10__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group_10__2__Impl
	rule__Age__Group_10__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_10__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getOfKeyword_10_2()); }
	'of'
	{ after(grammarAccess.getAgeAccess().getOfKeyword_10_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_10__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group_10__3__Impl
	rule__Age__Group_10__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_10__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getOutOfAssignment_10_3()); }
	(rule__Age__OutOfAssignment_10_3)
	{ after(grammarAccess.getAgeAccess().getOutOfAssignment_10_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_10__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group_10__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_10__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getTimesKeyword_10_4()); }
	'times'
	{ after(grammarAccess.getAgeAccess().getTimesKeyword_10_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Age__Group_11__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group_11__0__Impl
	rule__Age__Group_11__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_11__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getUsingKeyword_11_0()); }
	'using'
	{ after(grammarAccess.getAgeAccess().getUsingKeyword_11_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_11__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group_11__1__Impl
	rule__Age__Group_11__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_11__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getClockKeyword_11_1()); }
	'clock'
	{ after(grammarAccess.getAgeAccess().getClockKeyword_11_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_11__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Age__Group_11__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__Group_11__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getAgeAccess().getClockAssignment_11_2()); }
	(rule__Age__ClockAssignment_11_2)
	{ after(grammarAccess.getAgeAccess().getClockAssignment_11_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CausalReaction__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group__0__Impl
	rule__CausalReaction__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getReactionKeyword_0()); }
	'Reaction'
	{ after(grammarAccess.getCausalReactionAccess().getReactionKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group__1__Impl
	rule__CausalReaction__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getLeftParenthesisKeyword_1()); }
	'('
	{ after(grammarAccess.getCausalReactionAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group__2__Impl
	rule__CausalReaction__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getE1Assignment_2()); }
	(rule__CausalReaction__E1Assignment_2)
	{ after(grammarAccess.getCausalReactionAccess().getE1Assignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group__3__Impl
	rule__CausalReaction__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getCommaKeyword_3()); }
	','
	{ after(grammarAccess.getCausalReactionAccess().getCommaKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group__4__Impl
	rule__CausalReaction__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getE2Assignment_4()); }
	(rule__CausalReaction__E2Assignment_4)
	{ after(grammarAccess.getCausalReactionAccess().getE2Assignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group__5__Impl
	rule__CausalReaction__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getRightParenthesisKeyword_5()); }
	')'
	{ after(grammarAccess.getCausalReactionAccess().getRightParenthesisKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group__6__Impl
	rule__CausalReaction__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getWithinKeyword_6()); }
	'within'
	{ after(grammarAccess.getCausalReactionAccess().getWithinKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group__7__Impl
	rule__CausalReaction__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getIntervalAssignment_7()); }
	(rule__CausalReaction__IntervalAssignment_7)
	{ after(grammarAccess.getCausalReactionAccess().getIntervalAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group__8__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getGroup_8()); }
	(rule__CausalReaction__Group_8__0)?
	{ after(grammarAccess.getCausalReactionAccess().getGroup_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CausalReaction__Group_8__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group_8__0__Impl
	rule__CausalReaction__Group_8__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group_8__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getUsingKeyword_8_0()); }
	'using'
	{ after(grammarAccess.getCausalReactionAccess().getUsingKeyword_8_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group_8__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group_8__1__Impl
	rule__CausalReaction__Group_8__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group_8__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getClockKeyword_8_1()); }
	'clock'
	{ after(grammarAccess.getCausalReactionAccess().getClockKeyword_8_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group_8__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalReaction__Group_8__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__Group_8__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalReactionAccess().getClockAssignment_8_2()); }
	(rule__CausalReaction__ClockAssignment_8_2)
	{ after(grammarAccess.getCausalReactionAccess().getClockAssignment_8_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CausalAge__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group__0__Impl
	rule__CausalAge__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getAgeKeyword_0()); }
	'Age'
	{ after(grammarAccess.getCausalAgeAccess().getAgeKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group__1__Impl
	rule__CausalAge__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getLeftParenthesisKeyword_1()); }
	'('
	{ after(grammarAccess.getCausalAgeAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group__2__Impl
	rule__CausalAge__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getE1Assignment_2()); }
	(rule__CausalAge__E1Assignment_2)
	{ after(grammarAccess.getCausalAgeAccess().getE1Assignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group__3__Impl
	rule__CausalAge__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getCommaKeyword_3()); }
	','
	{ after(grammarAccess.getCausalAgeAccess().getCommaKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group__4__Impl
	rule__CausalAge__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getE2Assignment_4()); }
	(rule__CausalAge__E2Assignment_4)
	{ after(grammarAccess.getCausalAgeAccess().getE2Assignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group__5__Impl
	rule__CausalAge__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getRightParenthesisKeyword_5()); }
	')'
	{ after(grammarAccess.getCausalAgeAccess().getRightParenthesisKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group__6__Impl
	rule__CausalAge__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getWithinKeyword_6()); }
	'within'
	{ after(grammarAccess.getCausalAgeAccess().getWithinKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group__7__Impl
	rule__CausalAge__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getIntervalAssignment_7()); }
	(rule__CausalAge__IntervalAssignment_7)
	{ after(grammarAccess.getCausalAgeAccess().getIntervalAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group__8__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getGroup_8()); }
	(rule__CausalAge__Group_8__0)?
	{ after(grammarAccess.getCausalAgeAccess().getGroup_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CausalAge__Group_8__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group_8__0__Impl
	rule__CausalAge__Group_8__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group_8__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getUsingKeyword_8_0()); }
	'using'
	{ after(grammarAccess.getCausalAgeAccess().getUsingKeyword_8_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group_8__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group_8__1__Impl
	rule__CausalAge__Group_8__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group_8__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getClockKeyword_8_1()); }
	'clock'
	{ after(grammarAccess.getCausalAgeAccess().getClockKeyword_8_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group_8__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalAge__Group_8__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__Group_8__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalAgeAccess().getClockAssignment_8_2()); }
	(rule__CausalAge__ClockAssignment_8_2)
	{ after(grammarAccess.getCausalAgeAccess().getClockAssignment_8_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EventExpr__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventExpr__Group_1__0__Impl
	rule__EventExpr__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventExprAccess().getLeftParenthesisKeyword_1_0()); }
	'('
	{ after(grammarAccess.getEventExprAccess().getLeftParenthesisKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventExpr__Group_1__1__Impl
	rule__EventExpr__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventExprAccess().getEventsAssignment_1_1()); }
	(rule__EventExpr__EventsAssignment_1_1)
	{ after(grammarAccess.getEventExprAccess().getEventsAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventExpr__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventExprAccess().getRightParenthesisKeyword_1_2()); }
	')'
	{ after(grammarAccess.getEventExprAccess().getRightParenthesisKeyword_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EventExpr__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventExpr__Group_2__0__Impl
	rule__EventExpr__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventExprAccess().getLeftCurlyBracketKeyword_2_0()); }
	'{'
	{ after(grammarAccess.getEventExprAccess().getLeftCurlyBracketKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventExpr__Group_2__1__Impl
	rule__EventExpr__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventExprAccess().getEventsAssignment_2_1()); }
	(rule__EventExpr__EventsAssignment_2_1)
	{ after(grammarAccess.getEventExprAccess().getEventsAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventExpr__Group_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventExprAccess().getRightCurlyBracketKeyword_2_2()); }
	'}'
	{ after(grammarAccess.getEventExprAccess().getRightCurlyBracketKeyword_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EventList__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventList__Group__0__Impl
	rule__EventList__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EventList__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventListAccess().getEventsAssignment_0()); }
	(rule__EventList__EventsAssignment_0)
	{ after(grammarAccess.getEventListAccess().getEventsAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventList__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventList__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EventList__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventListAccess().getGroup_1()); }
	(rule__EventList__Group_1__0)*
	{ after(grammarAccess.getEventListAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EventList__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventList__Group_1__0__Impl
	rule__EventList__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EventList__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventListAccess().getCommaKeyword_1_0()); }
	','
	{ after(grammarAccess.getEventListAccess().getCommaKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventList__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventList__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EventList__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventListAccess().getEventsAssignment_1_1()); }
	(rule__EventList__EventsAssignment_1_1)
	{ after(grammarAccess.getEventListAccess().getEventsAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EventSpec__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventSpec__Group__0__Impl
	rule__EventSpec__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EventSpec__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventSpecAccess().getPortAssignment_0()); }
	(rule__EventSpec__PortAssignment_0)
	{ after(grammarAccess.getEventSpecAccess().getPortAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventSpec__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventSpec__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EventSpec__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventSpecAccess().getGroup_1()); }
	(rule__EventSpec__Group_1__0)?
	{ after(grammarAccess.getEventSpecAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EventSpec__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventSpec__Group_1__0__Impl
	rule__EventSpec__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EventSpec__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventSpecAccess().getFullStopKeyword_1_0()); }
	'.'
	{ after(grammarAccess.getEventSpecAccess().getFullStopKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventSpec__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EventSpec__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EventSpec__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEventSpecAccess().getEventValueAssignment_1_1()); }
	(rule__EventSpec__EventValueAssignment_1_1)
	{ after(grammarAccess.getEventSpecAccess().getEventValueAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Interval__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Interval__Group_1__0__Impl
	rule__Interval__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntervalAccess().getB1Assignment_1_0()); }
	(rule__Interval__B1Assignment_1_0)
	{ after(grammarAccess.getIntervalAccess().getB1Assignment_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Interval__Group_1__1__Impl
	rule__Interval__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntervalAccess().getV1Assignment_1_1()); }
	(rule__Interval__V1Assignment_1_1)
	{ after(grammarAccess.getIntervalAccess().getV1Assignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Interval__Group_1__2__Impl
	rule__Interval__Group_1__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntervalAccess().getCommaKeyword_1_2()); }
	','
	{ after(grammarAccess.getIntervalAccess().getCommaKeyword_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Interval__Group_1__3__Impl
	rule__Interval__Group_1__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntervalAccess().getV2Assignment_1_3()); }
	(rule__Interval__V2Assignment_1_3)
	{ after(grammarAccess.getIntervalAccess().getV2Assignment_1_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Interval__Group_1__4__Impl
	rule__Interval__Group_1__5
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntervalAccess().getB2Assignment_1_4()); }
	(rule__Interval__B2Assignment_1_4)
	{ after(grammarAccess.getIntervalAccess().getB2Assignment_1_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Interval__Group_1__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__Group_1__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getIntervalAccess().getUnitAssignment_1_5()); }
	(rule__Interval__UnitAssignment_1_5)
	{ after(grammarAccess.getIntervalAccess().getUnitAssignment_1_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TimeExpr__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TimeExpr__Group__0__Impl
	rule__TimeExpr__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeExpr__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeExprAccess().getValueAssignment_0()); }
	(rule__TimeExpr__ValueAssignment_0)
	{ after(grammarAccess.getTimeExprAccess().getValueAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeExpr__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TimeExpr__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeExpr__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeExprAccess().getUnitAssignment_1()); }
	(rule__TimeExpr__UnitAssignment_1)
	{ after(grammarAccess.getTimeExprAccess().getUnitAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Value__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Value__Group__0__Impl
	rule__Value__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Value__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getValueAccess().getIntegerAssignment_0()); }
	(rule__Value__IntegerAssignment_0)
	{ after(grammarAccess.getValueAccess().getIntegerAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Value__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Value__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Value__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getValueAccess().getGroup_1()); }
	(rule__Value__Group_1__0)?
	{ after(grammarAccess.getValueAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Value__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Value__Group_1__0__Impl
	rule__Value__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Value__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getValueAccess().getFullStopKeyword_1_0()); }
	'.'
	{ after(grammarAccess.getValueAccess().getFullStopKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Value__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Value__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Value__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getValueAccess().getFractionAssignment_1_1()); }
	(rule__Value__FractionAssignment_1_1)
	{ after(grammarAccess.getValueAccess().getFractionAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__CausalFuncDecl__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalFuncDecl__Group__0__Impl
	rule__CausalFuncDecl__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalFuncDeclAccess().getFuncNameAssignment_0()); }
	(rule__CausalFuncDecl__FuncNameAssignment_0)
	{ after(grammarAccess.getCausalFuncDeclAccess().getFuncNameAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalFuncDecl__Group__1__Impl
	rule__CausalFuncDecl__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalFuncDeclAccess().getLeftParenthesisKeyword_1()); }
	'('
	{ after(grammarAccess.getCausalFuncDeclAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalFuncDecl__Group__2__Impl
	rule__CausalFuncDecl__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalFuncDeclAccess().getP1Assignment_2()); }
	(rule__CausalFuncDecl__P1Assignment_2)
	{ after(grammarAccess.getCausalFuncDeclAccess().getP1Assignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalFuncDecl__Group__3__Impl
	rule__CausalFuncDecl__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalFuncDeclAccess().getCommaKeyword_3()); }
	','
	{ after(grammarAccess.getCausalFuncDeclAccess().getCommaKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalFuncDecl__Group__4__Impl
	rule__CausalFuncDecl__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalFuncDeclAccess().getP2Assignment_4()); }
	(rule__CausalFuncDecl__P2Assignment_4)
	{ after(grammarAccess.getCausalFuncDeclAccess().getP2Assignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalFuncDecl__Group__5__Impl
	rule__CausalFuncDecl__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalFuncDeclAccess().getRightParenthesisKeyword_5()); }
	')'
	{ after(grammarAccess.getCausalFuncDeclAccess().getRightParenthesisKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalFuncDecl__Group__6__Impl
	rule__CausalFuncDecl__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalFuncDeclAccess().getColonEqualsSignKeyword_6()); }
	':='
	{ after(grammarAccess.getCausalFuncDeclAccess().getColonEqualsSignKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__CausalFuncDecl__Group__7__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getCausalFuncDeclAccess().getRelationAssignment_7()); }
	(rule__CausalFuncDecl__RelationAssignment_7)
	{ after(grammarAccess.getCausalFuncDeclAccess().getRelationAssignment_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ClockDefinition__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group__0__Impl
	rule__ClockDefinition__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getClockKeyword_0()); }
	'Clock'
	{ after(grammarAccess.getClockDefinitionAccess().getClockKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group__1__Impl
	rule__ClockDefinition__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getNameAssignment_1()); }
	(rule__ClockDefinition__NameAssignment_1)
	{ after(grammarAccess.getClockDefinitionAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group__2__Impl
	rule__ClockDefinition__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getHasKeyword_2()); }
	'has'
	{ after(grammarAccess.getClockDefinitionAccess().getHasKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group__3__Impl
	rule__ClockDefinition__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getGroup_3()); }
	(rule__ClockDefinition__Group_3__0)?
	{ after(grammarAccess.getClockDefinitionAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group__4__Impl
	rule__ClockDefinition__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getGroup_4()); }
	(rule__ClockDefinition__Group_4__0)?
	{ after(grammarAccess.getClockDefinitionAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group__5__Impl
	rule__ClockDefinition__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getGroup_5()); }
	(rule__ClockDefinition__Group_5__0)?
	{ after(grammarAccess.getClockDefinitionAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getGroup_6()); }
	(rule__ClockDefinition__Group_6__0)?
	{ after(grammarAccess.getClockDefinitionAccess().getGroup_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ClockDefinition__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group_3__0__Impl
	rule__ClockDefinition__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getResolutionKeyword_3_0()); }
	'resolution'
	{ after(grammarAccess.getClockDefinitionAccess().getResolutionKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getResolutionAssignment_3_1()); }
	(rule__ClockDefinition__ResolutionAssignment_3_1)
	{ after(grammarAccess.getClockDefinitionAccess().getResolutionAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ClockDefinition__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group_4__0__Impl
	rule__ClockDefinition__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getSkewKeyword_4_0()); }
	'skew'
	{ after(grammarAccess.getClockDefinitionAccess().getSkewKeyword_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group_4__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getSkewAssignment_4_1()); }
	(rule__ClockDefinition__SkewAssignment_4_1)
	{ after(grammarAccess.getClockDefinitionAccess().getSkewAssignment_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ClockDefinition__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group_5__0__Impl
	rule__ClockDefinition__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getDriftKeyword_5_0()); }
	'drift'
	{ after(grammarAccess.getClockDefinitionAccess().getDriftKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getDriftAssignment_5_1()); }
	(rule__ClockDefinition__DriftAssignment_5_1)
	{ after(grammarAccess.getClockDefinitionAccess().getDriftAssignment_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__ClockDefinition__Group_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group_6__0__Impl
	rule__ClockDefinition__Group_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getMaxdiffKeyword_6_0()); }
	'maxdiff'
	{ after(grammarAccess.getClockDefinitionAccess().getMaxdiffKeyword_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__ClockDefinition__Group_6__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__Group_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getClockDefinitionAccess().getMaxdiffAssignment_6_1()); }
	(rule__ClockDefinition__MaxdiffAssignment_6_1)
	{ after(grammarAccess.getClockDefinitionAccess().getMaxdiffAssignment_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Model__TimeSpecAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModelAccess().getTimeSpecTimeSpecParserRuleCall_0()); }
		ruleTimeSpec
		{ after(grammarAccess.getModelAccess().getTimeSpecTimeSpecParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__EventsAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSingleEventAccess().getEventsEventListParserRuleCall_0_0()); }
		ruleEventList
		{ after(grammarAccess.getSingleEventAccess().getEventsEventListParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__IntervalAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSingleEventAccess().getIntervalIntervalParserRuleCall_3_0()); }
		ruleInterval
		{ after(grammarAccess.getSingleEventAccess().getIntervalIntervalParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SingleEvent__ClockAssignment_4_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSingleEventAccess().getClockClockDefinitionCrossReference_4_2_0()); }
		(
			{ before(grammarAccess.getSingleEventAccess().getClockClockDefinitionIDTerminalRuleCall_4_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getSingleEventAccess().getClockClockDefinitionIDTerminalRuleCall_4_2_0_1()); }
		)
		{ after(grammarAccess.getSingleEventAccess().getClockClockDefinitionCrossReference_4_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__EventsAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRepetitionAccess().getEventsEventListParserRuleCall_0_0()); }
		ruleEventList
		{ after(grammarAccess.getRepetitionAccess().getEventsEventListParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__IntervalAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRepetitionAccess().getIntervalIntervalParserRuleCall_3_0()); }
		ruleInterval
		{ after(grammarAccess.getRepetitionAccess().getIntervalIntervalParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__RepetitionOptionsAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRepetitionAccess().getRepetitionOptionsRepetitionOptionsParserRuleCall_4_1_0()); }
		ruleRepetitionOptions
		{ after(grammarAccess.getRepetitionAccess().getRepetitionOptionsRepetitionOptionsParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Repetition__ClockAssignment_5_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRepetitionAccess().getClockClockDefinitionCrossReference_5_2_0()); }
		(
			{ before(grammarAccess.getRepetitionAccess().getClockClockDefinitionIDTerminalRuleCall_5_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getRepetitionAccess().getClockClockDefinitionIDTerminalRuleCall_5_2_0_1()); }
		)
		{ after(grammarAccess.getRepetitionAccess().getClockClockDefinitionCrossReference_5_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__JitterAssignment_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRepetitionOptionsAccess().getJitterJitterParserRuleCall_0_0_0()); }
		ruleJitter
		{ after(grammarAccess.getRepetitionOptionsAccess().getJitterJitterParserRuleCall_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__OffsetAssignment_0_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRepetitionOptionsAccess().getOffsetOffsetParserRuleCall_0_1_1_0()); }
		ruleOffset
		{ after(grammarAccess.getRepetitionOptionsAccess().getOffsetOffsetParserRuleCall_0_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__OffsetAssignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRepetitionOptionsAccess().getOffsetOffsetParserRuleCall_1_0_0()); }
		ruleOffset
		{ after(grammarAccess.getRepetitionOptionsAccess().getOffsetOffsetParserRuleCall_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__RepetitionOptions__JitterAssignment_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getRepetitionOptionsAccess().getJitterJitterParserRuleCall_1_1_1_0()); }
		ruleJitter
		{ after(grammarAccess.getRepetitionOptionsAccess().getJitterJitterParserRuleCall_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Jitter__TimeAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getJitterAccess().getTimeTimeExprParserRuleCall_1_0()); }
		ruleTimeExpr
		{ after(grammarAccess.getJitterAccess().getTimeTimeExprParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Offset__IntervalAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getOffsetAccess().getIntervalIntervalParserRuleCall_1_0()); }
		ruleInterval
		{ after(grammarAccess.getOffsetAccess().getIntervalIntervalParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__TriggerAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReactionAccess().getTriggerEventExprParserRuleCall_1_0()); }
		ruleEventExpr
		{ after(grammarAccess.getReactionAccess().getTriggerEventExprParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__ReactionAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReactionAccess().getReactionEventExprParserRuleCall_4_0()); }
		ruleEventExpr
		{ after(grammarAccess.getReactionAccess().getReactionEventExprParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__IntervalAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReactionAccess().getIntervalIntervalParserRuleCall_7_0()); }
		ruleInterval
		{ after(grammarAccess.getReactionAccess().getIntervalIntervalParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__NAssignment_9_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReactionAccess().getNINTTerminalRuleCall_9_0_0()); }
		RULE_INT
		{ after(grammarAccess.getReactionAccess().getNINTTerminalRuleCall_9_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__OutOfAssignment_9_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReactionAccess().getOutOfINTTerminalRuleCall_9_3_0()); }
		RULE_INT
		{ after(grammarAccess.getReactionAccess().getOutOfINTTerminalRuleCall_9_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Reaction__ClockAssignment_10_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getReactionAccess().getClockClockDefinitionCrossReference_10_2_0()); }
		(
			{ before(grammarAccess.getReactionAccess().getClockClockDefinitionIDTerminalRuleCall_10_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getReactionAccess().getClockClockDefinitionIDTerminalRuleCall_10_2_0_1()); }
		)
		{ after(grammarAccess.getReactionAccess().getClockClockDefinitionCrossReference_10_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__TriggerAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAgeAccess().getTriggerEventExprParserRuleCall_1_0()); }
		ruleEventExpr
		{ after(grammarAccess.getAgeAccess().getTriggerEventExprParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__ReactionAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAgeAccess().getReactionEventExprParserRuleCall_4_0()); }
		ruleEventExpr
		{ after(grammarAccess.getAgeAccess().getReactionEventExprParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__IntervalAssignment_8
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAgeAccess().getIntervalIntervalParserRuleCall_8_0()); }
		ruleInterval
		{ after(grammarAccess.getAgeAccess().getIntervalIntervalParserRuleCall_8_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__NAssignment_10_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAgeAccess().getNINTTerminalRuleCall_10_0_0()); }
		RULE_INT
		{ after(grammarAccess.getAgeAccess().getNINTTerminalRuleCall_10_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__OutOfAssignment_10_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAgeAccess().getOutOfINTTerminalRuleCall_10_3_0()); }
		RULE_INT
		{ after(grammarAccess.getAgeAccess().getOutOfINTTerminalRuleCall_10_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Age__ClockAssignment_11_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAgeAccess().getClockClockDefinitionCrossReference_11_2_0()); }
		(
			{ before(grammarAccess.getAgeAccess().getClockClockDefinitionIDTerminalRuleCall_11_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getAgeAccess().getClockClockDefinitionIDTerminalRuleCall_11_2_0_1()); }
		)
		{ after(grammarAccess.getAgeAccess().getClockClockDefinitionCrossReference_11_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__E1Assignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalReactionAccess().getE1EventSpecParserRuleCall_2_0()); }
		ruleEventSpec
		{ after(grammarAccess.getCausalReactionAccess().getE1EventSpecParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__E2Assignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalReactionAccess().getE2EventSpecParserRuleCall_4_0()); }
		ruleEventSpec
		{ after(grammarAccess.getCausalReactionAccess().getE2EventSpecParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__IntervalAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalReactionAccess().getIntervalIntervalParserRuleCall_7_0()); }
		ruleInterval
		{ after(grammarAccess.getCausalReactionAccess().getIntervalIntervalParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalReaction__ClockAssignment_8_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalReactionAccess().getClockClockDefinitionCrossReference_8_2_0()); }
		(
			{ before(grammarAccess.getCausalReactionAccess().getClockClockDefinitionIDTerminalRuleCall_8_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getCausalReactionAccess().getClockClockDefinitionIDTerminalRuleCall_8_2_0_1()); }
		)
		{ after(grammarAccess.getCausalReactionAccess().getClockClockDefinitionCrossReference_8_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__E1Assignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalAgeAccess().getE1EventSpecParserRuleCall_2_0()); }
		ruleEventSpec
		{ after(grammarAccess.getCausalAgeAccess().getE1EventSpecParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__E2Assignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalAgeAccess().getE2EventSpecParserRuleCall_4_0()); }
		ruleEventSpec
		{ after(grammarAccess.getCausalAgeAccess().getE2EventSpecParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__IntervalAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalAgeAccess().getIntervalIntervalParserRuleCall_7_0()); }
		ruleInterval
		{ after(grammarAccess.getCausalAgeAccess().getIntervalIntervalParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalAge__ClockAssignment_8_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalAgeAccess().getClockClockDefinitionCrossReference_8_2_0()); }
		(
			{ before(grammarAccess.getCausalAgeAccess().getClockClockDefinitionIDTerminalRuleCall_8_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getCausalAgeAccess().getClockClockDefinitionIDTerminalRuleCall_8_2_0_1()); }
		)
		{ after(grammarAccess.getCausalAgeAccess().getClockClockDefinitionCrossReference_8_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__EventAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventExprAccess().getEventEventSpecParserRuleCall_0_0()); }
		ruleEventSpec
		{ after(grammarAccess.getEventExprAccess().getEventEventSpecParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__EventsAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventExprAccess().getEventsEventListParserRuleCall_1_1_0()); }
		ruleEventList
		{ after(grammarAccess.getEventExprAccess().getEventsEventListParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventExpr__EventsAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventExprAccess().getEventsEventListParserRuleCall_2_1_0()); }
		ruleEventList
		{ after(grammarAccess.getEventExprAccess().getEventsEventListParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventList__EventsAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventListAccess().getEventsEventSpecParserRuleCall_0_0()); }
		ruleEventSpec
		{ after(grammarAccess.getEventListAccess().getEventsEventSpecParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventList__EventsAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventListAccess().getEventsEventSpecParserRuleCall_1_1_0()); }
		ruleEventSpec
		{ after(grammarAccess.getEventListAccess().getEventsEventSpecParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventSpec__PortAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventSpecAccess().getPortPortCrossReference_0_0()); }
		(
			{ before(grammarAccess.getEventSpecAccess().getPortPortIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getEventSpecAccess().getPortPortIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getEventSpecAccess().getPortPortCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EventSpec__EventValueAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEventSpecAccess().getEventValueIDTerminalRuleCall_1_1_0()); }
		RULE_ID
		{ after(grammarAccess.getEventSpecAccess().getEventValueIDTerminalRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__TimeAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIntervalAccess().getTimeTimeExprParserRuleCall_0_0()); }
		ruleTimeExpr
		{ after(grammarAccess.getIntervalAccess().getTimeTimeExprParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__B1Assignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIntervalAccess().getB1BoundaryParserRuleCall_1_0_0()); }
		ruleBoundary
		{ after(grammarAccess.getIntervalAccess().getB1BoundaryParserRuleCall_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__V1Assignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIntervalAccess().getV1ValueParserRuleCall_1_1_0()); }
		ruleValue
		{ after(grammarAccess.getIntervalAccess().getV1ValueParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__V2Assignment_1_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIntervalAccess().getV2ValueParserRuleCall_1_3_0()); }
		ruleValue
		{ after(grammarAccess.getIntervalAccess().getV2ValueParserRuleCall_1_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__B2Assignment_1_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIntervalAccess().getB2BoundaryParserRuleCall_1_4_0()); }
		ruleBoundary
		{ after(grammarAccess.getIntervalAccess().getB2BoundaryParserRuleCall_1_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Interval__UnitAssignment_1_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getIntervalAccess().getUnitUnitParserRuleCall_1_5_0()); }
		ruleUnit
		{ after(grammarAccess.getIntervalAccess().getUnitUnitParserRuleCall_1_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeExpr__ValueAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTimeExprAccess().getValueValueParserRuleCall_0_0()); }
		ruleValue
		{ after(grammarAccess.getTimeExprAccess().getValueValueParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeExpr__UnitAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTimeExprAccess().getUnitUnitParserRuleCall_1_0()); }
		ruleUnit
		{ after(grammarAccess.getTimeExprAccess().getUnitUnitParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Value__IntegerAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getValueAccess().getIntegerINTTerminalRuleCall_0_0()); }
		RULE_INT
		{ after(grammarAccess.getValueAccess().getIntegerINTTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Value__FractionAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getValueAccess().getFractionINTTerminalRuleCall_1_1_0()); }
		RULE_INT
		{ after(grammarAccess.getValueAccess().getFractionINTTerminalRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__FuncNameAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalFuncDeclAccess().getFuncNameCausalFuncNameParserRuleCall_0_0()); }
		ruleCausalFuncName
		{ after(grammarAccess.getCausalFuncDeclAccess().getFuncNameCausalFuncNameParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__P1Assignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalFuncDeclAccess().getP1PortCrossReference_2_0()); }
		(
			{ before(grammarAccess.getCausalFuncDeclAccess().getP1PortIDTerminalRuleCall_2_0_1()); }
			RULE_ID
			{ after(grammarAccess.getCausalFuncDeclAccess().getP1PortIDTerminalRuleCall_2_0_1()); }
		)
		{ after(grammarAccess.getCausalFuncDeclAccess().getP1PortCrossReference_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__P2Assignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalFuncDeclAccess().getP2PortCrossReference_4_0()); }
		(
			{ before(grammarAccess.getCausalFuncDeclAccess().getP2PortIDTerminalRuleCall_4_0_1()); }
			RULE_ID
			{ after(grammarAccess.getCausalFuncDeclAccess().getP2PortIDTerminalRuleCall_4_0_1()); }
		)
		{ after(grammarAccess.getCausalFuncDeclAccess().getP2PortCrossReference_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CausalFuncDecl__RelationAssignment_7
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCausalFuncDeclAccess().getRelationCausalRelationParserRuleCall_7_0()); }
		ruleCausalRelation
		{ after(grammarAccess.getCausalFuncDeclAccess().getRelationCausalRelationParserRuleCall_7_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getClockDefinitionAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getClockDefinitionAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__ResolutionAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getClockDefinitionAccess().getResolutionTimeExprParserRuleCall_3_1_0()); }
		ruleTimeExpr
		{ after(grammarAccess.getClockDefinitionAccess().getResolutionTimeExprParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__SkewAssignment_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getClockDefinitionAccess().getSkewTimeExprParserRuleCall_4_1_0()); }
		ruleTimeExpr
		{ after(grammarAccess.getClockDefinitionAccess().getSkewTimeExprParserRuleCall_4_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__DriftAssignment_5_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getClockDefinitionAccess().getDriftIntervalParserRuleCall_5_1_0()); }
		ruleInterval
		{ after(grammarAccess.getClockDefinitionAccess().getDriftIntervalParserRuleCall_5_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__ClockDefinition__MaxdiffAssignment_6_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getClockDefinitionAccess().getMaxdiffTimeExprParserRuleCall_6_1_0()); }
		ruleTimeExpr
		{ after(grammarAccess.getClockDefinitionAccess().getMaxdiffTimeExprParserRuleCall_6_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
