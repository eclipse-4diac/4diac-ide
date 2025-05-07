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
package org.eclipse.fordiac.ide.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.common.services.TerminalsGrammarAccess;
import org.eclipse.xtext.service.AbstractElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class ContractSpecGrammarAccess extends AbstractElementFinder.AbstractGrammarElementFinder {
	
	public class ModelElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Model");
		private final Assignment cTimeSpecAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cTimeSpecTimeSpecParserRuleCall_0 = (RuleCall)cTimeSpecAssignment.eContents().get(0);
		
		//Model:
		//    timeSpec+=TimeSpec*;
		@Override public ParserRule getRule() { return rule; }
		
		//timeSpec+=TimeSpec*
		public Assignment getTimeSpecAssignment() { return cTimeSpecAssignment; }
		
		//TimeSpec
		public RuleCall getTimeSpecTimeSpecParserRuleCall_0() { return cTimeSpecTimeSpecParserRuleCall_0; }
	}
	public class TimeSpecElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.TimeSpec");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final RuleCall cSingleEventParserRuleCall_0 = (RuleCall)cAlternatives.eContents().get(0);
		private final RuleCall cRepetitionParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
		private final RuleCall cReactionParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
		private final RuleCall cAgeParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
		private final RuleCall cCausalReactionParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
		private final RuleCall cCausalAgeParserRuleCall_5 = (RuleCall)cAlternatives.eContents().get(5);
		private final RuleCall cCausalFuncDeclParserRuleCall_6 = (RuleCall)cAlternatives.eContents().get(6);
		private final RuleCall cClockDefinitionParserRuleCall_7 = (RuleCall)cAlternatives.eContents().get(7);
		
		//TimeSpec:
		//    SingleEvent | Repetition | Reaction | Age |
		//    CausalReaction | CausalAge | CausalFuncDecl | ClockDefinition;
		@Override public ParserRule getRule() { return rule; }
		
		//SingleEvent | Repetition | Reaction | Age |
		//CausalReaction | CausalAge | CausalFuncDecl | ClockDefinition
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//SingleEvent
		public RuleCall getSingleEventParserRuleCall_0() { return cSingleEventParserRuleCall_0; }
		
		//Repetition
		public RuleCall getRepetitionParserRuleCall_1() { return cRepetitionParserRuleCall_1; }
		
		//Reaction
		public RuleCall getReactionParserRuleCall_2() { return cReactionParserRuleCall_2; }
		
		//Age
		public RuleCall getAgeParserRuleCall_3() { return cAgeParserRuleCall_3; }
		
		//CausalReaction
		public RuleCall getCausalReactionParserRuleCall_4() { return cCausalReactionParserRuleCall_4; }
		
		//CausalAge
		public RuleCall getCausalAgeParserRuleCall_5() { return cCausalAgeParserRuleCall_5; }
		
		//CausalFuncDecl
		public RuleCall getCausalFuncDeclParserRuleCall_6() { return cCausalFuncDeclParserRuleCall_6; }
		
		//ClockDefinition
		public RuleCall getClockDefinitionParserRuleCall_7() { return cClockDefinitionParserRuleCall_7; }
	}
	public class SingleEventElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.SingleEvent");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cEventsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cEventsEventListParserRuleCall_0_0 = (RuleCall)cEventsAssignment_0.eContents().get(0);
		private final Keyword cOccursKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cWithinKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cIntervalAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cIntervalIntervalParserRuleCall_3_0 = (RuleCall)cIntervalAssignment_3.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cUsingKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Keyword cClockKeyword_4_1 = (Keyword)cGroup_4.eContents().get(1);
		private final Assignment cClockAssignment_4_2 = (Assignment)cGroup_4.eContents().get(2);
		private final CrossReference cClockClockDefinitionCrossReference_4_2_0 = (CrossReference)cClockAssignment_4_2.eContents().get(0);
		private final RuleCall cClockClockDefinitionIDTerminalRuleCall_4_2_0_1 = (RuleCall)cClockClockDefinitionCrossReference_4_2_0.eContents().get(1);
		
		//SingleEvent:
		//    events=EventList "occurs" "within" interval=Interval
		//    ("using" "clock" clock=[ClockDefinition])?;
		@Override public ParserRule getRule() { return rule; }
		
		//events=EventList "occurs" "within" interval=Interval
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup() { return cGroup; }
		
		//events=EventList
		public Assignment getEventsAssignment_0() { return cEventsAssignment_0; }
		
		//EventList
		public RuleCall getEventsEventListParserRuleCall_0_0() { return cEventsEventListParserRuleCall_0_0; }
		
		//"occurs"
		public Keyword getOccursKeyword_1() { return cOccursKeyword_1; }
		
		//"within"
		public Keyword getWithinKeyword_2() { return cWithinKeyword_2; }
		
		//interval=Interval
		public Assignment getIntervalAssignment_3() { return cIntervalAssignment_3; }
		
		//Interval
		public RuleCall getIntervalIntervalParserRuleCall_3_0() { return cIntervalIntervalParserRuleCall_3_0; }
		
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup_4() { return cGroup_4; }
		
		//"using"
		public Keyword getUsingKeyword_4_0() { return cUsingKeyword_4_0; }
		
		//"clock"
		public Keyword getClockKeyword_4_1() { return cClockKeyword_4_1; }
		
		//clock=[ClockDefinition]
		public Assignment getClockAssignment_4_2() { return cClockAssignment_4_2; }
		
		//[ClockDefinition]
		public CrossReference getClockClockDefinitionCrossReference_4_2_0() { return cClockClockDefinitionCrossReference_4_2_0; }
		
		//ID
		public RuleCall getClockClockDefinitionIDTerminalRuleCall_4_2_0_1() { return cClockClockDefinitionIDTerminalRuleCall_4_2_0_1; }
	}
	public class RepetitionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Repetition");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cEventsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cEventsEventListParserRuleCall_0_0 = (RuleCall)cEventsAssignment_0.eContents().get(0);
		private final Keyword cOccursKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Keyword cEveryKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cIntervalAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cIntervalIntervalParserRuleCall_3_0 = (RuleCall)cIntervalAssignment_3.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cWithKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cRepetitionOptionsAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cRepetitionOptionsRepetitionOptionsParserRuleCall_4_1_0 = (RuleCall)cRepetitionOptionsAssignment_4_1.eContents().get(0);
		private final Group cGroup_5 = (Group)cGroup.eContents().get(5);
		private final Keyword cUsingKeyword_5_0 = (Keyword)cGroup_5.eContents().get(0);
		private final Keyword cClockKeyword_5_1 = (Keyword)cGroup_5.eContents().get(1);
		private final Assignment cClockAssignment_5_2 = (Assignment)cGroup_5.eContents().get(2);
		private final CrossReference cClockClockDefinitionCrossReference_5_2_0 = (CrossReference)cClockAssignment_5_2.eContents().get(0);
		private final RuleCall cClockClockDefinitionIDTerminalRuleCall_5_2_0_1 = (RuleCall)cClockClockDefinitionCrossReference_5_2_0.eContents().get(1);
		
		//Repetition:
		//    events=EventList "occurs" "every" interval=Interval
		//    ("with" repetitionOptions=RepetitionOptions)?
		//    ("using" "clock" clock=[ClockDefinition])?;
		@Override public ParserRule getRule() { return rule; }
		
		//events=EventList "occurs" "every" interval=Interval
		//("with" repetitionOptions=RepetitionOptions)?
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup() { return cGroup; }
		
		//events=EventList
		public Assignment getEventsAssignment_0() { return cEventsAssignment_0; }
		
		//EventList
		public RuleCall getEventsEventListParserRuleCall_0_0() { return cEventsEventListParserRuleCall_0_0; }
		
		//"occurs"
		public Keyword getOccursKeyword_1() { return cOccursKeyword_1; }
		
		//"every"
		public Keyword getEveryKeyword_2() { return cEveryKeyword_2; }
		
		//interval=Interval
		public Assignment getIntervalAssignment_3() { return cIntervalAssignment_3; }
		
		//Interval
		public RuleCall getIntervalIntervalParserRuleCall_3_0() { return cIntervalIntervalParserRuleCall_3_0; }
		
		//("with" repetitionOptions=RepetitionOptions)?
		public Group getGroup_4() { return cGroup_4; }
		
		//"with"
		public Keyword getWithKeyword_4_0() { return cWithKeyword_4_0; }
		
		//repetitionOptions=RepetitionOptions
		public Assignment getRepetitionOptionsAssignment_4_1() { return cRepetitionOptionsAssignment_4_1; }
		
		//RepetitionOptions
		public RuleCall getRepetitionOptionsRepetitionOptionsParserRuleCall_4_1_0() { return cRepetitionOptionsRepetitionOptionsParserRuleCall_4_1_0; }
		
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup_5() { return cGroup_5; }
		
		//"using"
		public Keyword getUsingKeyword_5_0() { return cUsingKeyword_5_0; }
		
		//"clock"
		public Keyword getClockKeyword_5_1() { return cClockKeyword_5_1; }
		
		//clock=[ClockDefinition]
		public Assignment getClockAssignment_5_2() { return cClockAssignment_5_2; }
		
		//[ClockDefinition]
		public CrossReference getClockClockDefinitionCrossReference_5_2_0() { return cClockClockDefinitionCrossReference_5_2_0; }
		
		//ID
		public RuleCall getClockClockDefinitionIDTerminalRuleCall_5_2_0_1() { return cClockClockDefinitionIDTerminalRuleCall_5_2_0_1; }
	}
	public class RepetitionOptionsElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.RepetitionOptions");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
		private final Assignment cJitterAssignment_0_0 = (Assignment)cGroup_0.eContents().get(0);
		private final RuleCall cJitterJitterParserRuleCall_0_0_0 = (RuleCall)cJitterAssignment_0_0.eContents().get(0);
		private final Group cGroup_0_1 = (Group)cGroup_0.eContents().get(1);
		private final Keyword cAndKeyword_0_1_0 = (Keyword)cGroup_0_1.eContents().get(0);
		private final Assignment cOffsetAssignment_0_1_1 = (Assignment)cGroup_0_1.eContents().get(1);
		private final RuleCall cOffsetOffsetParserRuleCall_0_1_1_0 = (RuleCall)cOffsetAssignment_0_1_1.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Assignment cOffsetAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cOffsetOffsetParserRuleCall_1_0_0 = (RuleCall)cOffsetAssignment_1_0.eContents().get(0);
		private final Group cGroup_1_1 = (Group)cGroup_1.eContents().get(1);
		private final Keyword cAndKeyword_1_1_0 = (Keyword)cGroup_1_1.eContents().get(0);
		private final Assignment cJitterAssignment_1_1_1 = (Assignment)cGroup_1_1.eContents().get(1);
		private final RuleCall cJitterJitterParserRuleCall_1_1_1_0 = (RuleCall)cJitterAssignment_1_1_1.eContents().get(0);
		
		//RepetitionOptions:
		//    (jitter=Jitter ("and" offset=Offset)?) |
		//    (offset=Offset ("and" jitter=Jitter)?);
		@Override public ParserRule getRule() { return rule; }
		
		//(jitter=Jitter ("and" offset=Offset)?) |
		//(offset=Offset ("and" jitter=Jitter)?)
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//(jitter=Jitter ("and" offset=Offset)?)
		public Group getGroup_0() { return cGroup_0; }
		
		//jitter=Jitter
		public Assignment getJitterAssignment_0_0() { return cJitterAssignment_0_0; }
		
		//Jitter
		public RuleCall getJitterJitterParserRuleCall_0_0_0() { return cJitterJitterParserRuleCall_0_0_0; }
		
		//("and" offset=Offset)?
		public Group getGroup_0_1() { return cGroup_0_1; }
		
		//"and"
		public Keyword getAndKeyword_0_1_0() { return cAndKeyword_0_1_0; }
		
		//offset=Offset
		public Assignment getOffsetAssignment_0_1_1() { return cOffsetAssignment_0_1_1; }
		
		//Offset
		public RuleCall getOffsetOffsetParserRuleCall_0_1_1_0() { return cOffsetOffsetParserRuleCall_0_1_1_0; }
		
		//(offset=Offset ("and" jitter=Jitter)?)
		public Group getGroup_1() { return cGroup_1; }
		
		//offset=Offset
		public Assignment getOffsetAssignment_1_0() { return cOffsetAssignment_1_0; }
		
		//Offset
		public RuleCall getOffsetOffsetParserRuleCall_1_0_0() { return cOffsetOffsetParserRuleCall_1_0_0; }
		
		//("and" jitter=Jitter)?
		public Group getGroup_1_1() { return cGroup_1_1; }
		
		//"and"
		public Keyword getAndKeyword_1_1_0() { return cAndKeyword_1_1_0; }
		
		//jitter=Jitter
		public Assignment getJitterAssignment_1_1_1() { return cJitterAssignment_1_1_1; }
		
		//Jitter
		public RuleCall getJitterJitterParserRuleCall_1_1_1_0() { return cJitterJitterParserRuleCall_1_1_1_0; }
	}
	public class JitterElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Jitter");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cJitterKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cTimeAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTimeTimeExprParserRuleCall_1_0 = (RuleCall)cTimeAssignment_1.eContents().get(0);
		
		//Jitter:
		//    "jitter" time=TimeExpr;
		@Override public ParserRule getRule() { return rule; }
		
		//"jitter" time=TimeExpr
		public Group getGroup() { return cGroup; }
		
		//"jitter"
		public Keyword getJitterKeyword_0() { return cJitterKeyword_0; }
		
		//time=TimeExpr
		public Assignment getTimeAssignment_1() { return cTimeAssignment_1; }
		
		//TimeExpr
		public RuleCall getTimeTimeExprParserRuleCall_1_0() { return cTimeTimeExprParserRuleCall_1_0; }
	}
	public class OffsetElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Offset");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cOffsetKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cIntervalAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cIntervalIntervalParserRuleCall_1_0 = (RuleCall)cIntervalAssignment_1.eContents().get(0);
		
		//Offset:
		//    "offset" interval=Interval;
		@Override public ParserRule getRule() { return rule; }
		
		//"offset" interval=Interval
		public Group getGroup() { return cGroup; }
		
		//"offset"
		public Keyword getOffsetKeyword_0() { return cOffsetKeyword_0; }
		
		//interval=Interval
		public Assignment getIntervalAssignment_1() { return cIntervalAssignment_1; }
		
		//Interval
		public RuleCall getIntervalIntervalParserRuleCall_1_0() { return cIntervalIntervalParserRuleCall_1_0; }
	}
	public class ReactionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Reaction");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cWheneverKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cInputAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cInputEventExprParserRuleCall_1_0 = (RuleCall)cInputAssignment_1.eContents().get(0);
		private final Keyword cOccursKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cThenKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cOutputAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cOutputEventExprParserRuleCall_4_0 = (RuleCall)cOutputAssignment_4.eContents().get(0);
		private final Keyword cOccursKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Keyword cWithinKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Assignment cIntervalAssignment_7 = (Assignment)cGroup.eContents().get(7);
		private final RuleCall cIntervalIntervalParserRuleCall_7_0 = (RuleCall)cIntervalAssignment_7.eContents().get(0);
		private final Alternatives cAlternatives_8 = (Alternatives)cGroup.eContents().get(8);
		private final Assignment cOnceAssignment_8_0 = (Assignment)cAlternatives_8.eContents().get(0);
		private final Keyword cOnceOnceKeyword_8_0_0 = (Keyword)cOnceAssignment_8_0.eContents().get(0);
		private final Group cGroup_8_1 = (Group)cAlternatives_8.eContents().get(1);
		private final Assignment cNAssignment_8_1_0 = (Assignment)cGroup_8_1.eContents().get(0);
		private final RuleCall cNINTTerminalRuleCall_8_1_0_0 = (RuleCall)cNAssignment_8_1_0.eContents().get(0);
		private final Keyword cOutKeyword_8_1_1 = (Keyword)cGroup_8_1.eContents().get(1);
		private final Keyword cOfKeyword_8_1_2 = (Keyword)cGroup_8_1.eContents().get(2);
		private final Assignment cOutOfAssignment_8_1_3 = (Assignment)cGroup_8_1.eContents().get(3);
		private final RuleCall cOutOfINTTerminalRuleCall_8_1_3_0 = (RuleCall)cOutOfAssignment_8_1_3.eContents().get(0);
		private final Keyword cTimesKeyword_8_1_4 = (Keyword)cGroup_8_1.eContents().get(4);
		private final Group cGroup_9 = (Group)cGroup.eContents().get(9);
		private final Keyword cUsingKeyword_9_0 = (Keyword)cGroup_9.eContents().get(0);
		private final Keyword cClockKeyword_9_1 = (Keyword)cGroup_9.eContents().get(1);
		private final Assignment cClockAssignment_9_2 = (Assignment)cGroup_9.eContents().get(2);
		private final CrossReference cClockClockDefinitionCrossReference_9_2_0 = (CrossReference)cClockAssignment_9_2.eContents().get(0);
		private final RuleCall cClockClockDefinitionIDTerminalRuleCall_9_2_0_1 = (RuleCall)cClockClockDefinitionCrossReference_9_2_0.eContents().get(1);
		
		//Reaction:
		//    "whenever" input=EventExpr "occurs" "then" output=EventExpr "occurs" "within" interval=Interval
		//    (once?="once" | n=INT "out" "of" outOf=INT "times")?
		//    ("using" "clock" clock=[ClockDefinition])?;
		@Override public ParserRule getRule() { return rule; }
		
		//"whenever" input=EventExpr "occurs" "then" output=EventExpr "occurs" "within" interval=Interval
		//(once?="once" | n=INT "out" "of" outOf=INT "times")?
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup() { return cGroup; }
		
		//"whenever"
		public Keyword getWheneverKeyword_0() { return cWheneverKeyword_0; }
		
		//input=EventExpr
		public Assignment getInputAssignment_1() { return cInputAssignment_1; }
		
		//EventExpr
		public RuleCall getInputEventExprParserRuleCall_1_0() { return cInputEventExprParserRuleCall_1_0; }
		
		//"occurs"
		public Keyword getOccursKeyword_2() { return cOccursKeyword_2; }
		
		//"then"
		public Keyword getThenKeyword_3() { return cThenKeyword_3; }
		
		//output=EventExpr
		public Assignment getOutputAssignment_4() { return cOutputAssignment_4; }
		
		//EventExpr
		public RuleCall getOutputEventExprParserRuleCall_4_0() { return cOutputEventExprParserRuleCall_4_0; }
		
		//"occurs"
		public Keyword getOccursKeyword_5() { return cOccursKeyword_5; }
		
		//"within"
		public Keyword getWithinKeyword_6() { return cWithinKeyword_6; }
		
		//interval=Interval
		public Assignment getIntervalAssignment_7() { return cIntervalAssignment_7; }
		
		//Interval
		public RuleCall getIntervalIntervalParserRuleCall_7_0() { return cIntervalIntervalParserRuleCall_7_0; }
		
		//(once?="once" | n=INT "out" "of" outOf=INT "times")?
		public Alternatives getAlternatives_8() { return cAlternatives_8; }
		
		//once?="once"
		public Assignment getOnceAssignment_8_0() { return cOnceAssignment_8_0; }
		
		//"once"
		public Keyword getOnceOnceKeyword_8_0_0() { return cOnceOnceKeyword_8_0_0; }
		
		//n=INT "out" "of" outOf=INT "times"
		public Group getGroup_8_1() { return cGroup_8_1; }
		
		//n=INT
		public Assignment getNAssignment_8_1_0() { return cNAssignment_8_1_0; }
		
		//INT
		public RuleCall getNINTTerminalRuleCall_8_1_0_0() { return cNINTTerminalRuleCall_8_1_0_0; }
		
		//"out"
		public Keyword getOutKeyword_8_1_1() { return cOutKeyword_8_1_1; }
		
		//"of"
		public Keyword getOfKeyword_8_1_2() { return cOfKeyword_8_1_2; }
		
		//outOf=INT
		public Assignment getOutOfAssignment_8_1_3() { return cOutOfAssignment_8_1_3; }
		
		//INT
		public RuleCall getOutOfINTTerminalRuleCall_8_1_3_0() { return cOutOfINTTerminalRuleCall_8_1_3_0; }
		
		//"times"
		public Keyword getTimesKeyword_8_1_4() { return cTimesKeyword_8_1_4; }
		
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup_9() { return cGroup_9; }
		
		//"using"
		public Keyword getUsingKeyword_9_0() { return cUsingKeyword_9_0; }
		
		//"clock"
		public Keyword getClockKeyword_9_1() { return cClockKeyword_9_1; }
		
		//clock=[ClockDefinition]
		public Assignment getClockAssignment_9_2() { return cClockAssignment_9_2; }
		
		//[ClockDefinition]
		public CrossReference getClockClockDefinitionCrossReference_9_2_0() { return cClockClockDefinitionCrossReference_9_2_0; }
		
		//ID
		public RuleCall getClockClockDefinitionIDTerminalRuleCall_9_2_0_1() { return cClockClockDefinitionIDTerminalRuleCall_9_2_0_1; }
	}
	public class AgeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Age");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cWheneverKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cOutputAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cOutputEventExprParserRuleCall_1_0 = (RuleCall)cOutputAssignment_1.eContents().get(0);
		private final Keyword cOccursKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cThenKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cInputAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cInputEventExprParserRuleCall_4_0 = (RuleCall)cInputAssignment_4.eContents().get(0);
		private final Keyword cHasKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Keyword cOccurredKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Keyword cWithinKeyword_7 = (Keyword)cGroup.eContents().get(7);
		private final Assignment cIntervalAssignment_8 = (Assignment)cGroup.eContents().get(8);
		private final RuleCall cIntervalIntervalParserRuleCall_8_0 = (RuleCall)cIntervalAssignment_8.eContents().get(0);
		private final Alternatives cAlternatives_9 = (Alternatives)cGroup.eContents().get(9);
		private final Assignment cOnceAssignment_9_0 = (Assignment)cAlternatives_9.eContents().get(0);
		private final Keyword cOnceOnceKeyword_9_0_0 = (Keyword)cOnceAssignment_9_0.eContents().get(0);
		private final Group cGroup_9_1 = (Group)cAlternatives_9.eContents().get(1);
		private final Assignment cNAssignment_9_1_0 = (Assignment)cGroup_9_1.eContents().get(0);
		private final RuleCall cNINTTerminalRuleCall_9_1_0_0 = (RuleCall)cNAssignment_9_1_0.eContents().get(0);
		private final Keyword cOutKeyword_9_1_1 = (Keyword)cGroup_9_1.eContents().get(1);
		private final Keyword cOfKeyword_9_1_2 = (Keyword)cGroup_9_1.eContents().get(2);
		private final Assignment cOutOfAssignment_9_1_3 = (Assignment)cGroup_9_1.eContents().get(3);
		private final RuleCall cOutOfINTTerminalRuleCall_9_1_3_0 = (RuleCall)cOutOfAssignment_9_1_3.eContents().get(0);
		private final Keyword cTimesKeyword_9_1_4 = (Keyword)cGroup_9_1.eContents().get(4);
		private final Group cGroup_10 = (Group)cGroup.eContents().get(10);
		private final Keyword cUsingKeyword_10_0 = (Keyword)cGroup_10.eContents().get(0);
		private final Keyword cClockKeyword_10_1 = (Keyword)cGroup_10.eContents().get(1);
		private final Assignment cClockAssignment_10_2 = (Assignment)cGroup_10.eContents().get(2);
		private final CrossReference cClockClockDefinitionCrossReference_10_2_0 = (CrossReference)cClockAssignment_10_2.eContents().get(0);
		private final RuleCall cClockClockDefinitionIDTerminalRuleCall_10_2_0_1 = (RuleCall)cClockClockDefinitionCrossReference_10_2_0.eContents().get(1);
		
		//Age:
		//    "whenever" output=EventExpr "occurs" "then" input=EventExpr "has" "occurred" "within" interval=Interval
		//    (once?="once" | n=INT "out" "of" outOf=INT "times")?
		//    ("using" "clock" clock=[ClockDefinition])?;
		@Override public ParserRule getRule() { return rule; }
		
		//"whenever" output=EventExpr "occurs" "then" input=EventExpr "has" "occurred" "within" interval=Interval
		//(once?="once" | n=INT "out" "of" outOf=INT "times")?
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup() { return cGroup; }
		
		//"whenever"
		public Keyword getWheneverKeyword_0() { return cWheneverKeyword_0; }
		
		//output=EventExpr
		public Assignment getOutputAssignment_1() { return cOutputAssignment_1; }
		
		//EventExpr
		public RuleCall getOutputEventExprParserRuleCall_1_0() { return cOutputEventExprParserRuleCall_1_0; }
		
		//"occurs"
		public Keyword getOccursKeyword_2() { return cOccursKeyword_2; }
		
		//"then"
		public Keyword getThenKeyword_3() { return cThenKeyword_3; }
		
		//input=EventExpr
		public Assignment getInputAssignment_4() { return cInputAssignment_4; }
		
		//EventExpr
		public RuleCall getInputEventExprParserRuleCall_4_0() { return cInputEventExprParserRuleCall_4_0; }
		
		//"has"
		public Keyword getHasKeyword_5() { return cHasKeyword_5; }
		
		//"occurred"
		public Keyword getOccurredKeyword_6() { return cOccurredKeyword_6; }
		
		//"within"
		public Keyword getWithinKeyword_7() { return cWithinKeyword_7; }
		
		//interval=Interval
		public Assignment getIntervalAssignment_8() { return cIntervalAssignment_8; }
		
		//Interval
		public RuleCall getIntervalIntervalParserRuleCall_8_0() { return cIntervalIntervalParserRuleCall_8_0; }
		
		//(once?="once" | n=INT "out" "of" outOf=INT "times")?
		public Alternatives getAlternatives_9() { return cAlternatives_9; }
		
		//once?="once"
		public Assignment getOnceAssignment_9_0() { return cOnceAssignment_9_0; }
		
		//"once"
		public Keyword getOnceOnceKeyword_9_0_0() { return cOnceOnceKeyword_9_0_0; }
		
		//n=INT "out" "of" outOf=INT "times"
		public Group getGroup_9_1() { return cGroup_9_1; }
		
		//n=INT
		public Assignment getNAssignment_9_1_0() { return cNAssignment_9_1_0; }
		
		//INT
		public RuleCall getNINTTerminalRuleCall_9_1_0_0() { return cNINTTerminalRuleCall_9_1_0_0; }
		
		//"out"
		public Keyword getOutKeyword_9_1_1() { return cOutKeyword_9_1_1; }
		
		//"of"
		public Keyword getOfKeyword_9_1_2() { return cOfKeyword_9_1_2; }
		
		//outOf=INT
		public Assignment getOutOfAssignment_9_1_3() { return cOutOfAssignment_9_1_3; }
		
		//INT
		public RuleCall getOutOfINTTerminalRuleCall_9_1_3_0() { return cOutOfINTTerminalRuleCall_9_1_3_0; }
		
		//"times"
		public Keyword getTimesKeyword_9_1_4() { return cTimesKeyword_9_1_4; }
		
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup_10() { return cGroup_10; }
		
		//"using"
		public Keyword getUsingKeyword_10_0() { return cUsingKeyword_10_0; }
		
		//"clock"
		public Keyword getClockKeyword_10_1() { return cClockKeyword_10_1; }
		
		//clock=[ClockDefinition]
		public Assignment getClockAssignment_10_2() { return cClockAssignment_10_2; }
		
		//[ClockDefinition]
		public CrossReference getClockClockDefinitionCrossReference_10_2_0() { return cClockClockDefinitionCrossReference_10_2_0; }
		
		//ID
		public RuleCall getClockClockDefinitionIDTerminalRuleCall_10_2_0_1() { return cClockClockDefinitionIDTerminalRuleCall_10_2_0_1; }
	}
	public class CausalReactionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.CausalReaction");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cReactionKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cInputAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cInputEventSpecParserRuleCall_2_0 = (RuleCall)cInputAssignment_2.eContents().get(0);
		private final Keyword cCommaKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cOutputAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cOutputEventSpecParserRuleCall_4_0 = (RuleCall)cOutputAssignment_4.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Keyword cWithinKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Assignment cIntervalAssignment_7 = (Assignment)cGroup.eContents().get(7);
		private final RuleCall cIntervalIntervalParserRuleCall_7_0 = (RuleCall)cIntervalAssignment_7.eContents().get(0);
		private final Group cGroup_8 = (Group)cGroup.eContents().get(8);
		private final Keyword cUsingKeyword_8_0 = (Keyword)cGroup_8.eContents().get(0);
		private final Keyword cClockKeyword_8_1 = (Keyword)cGroup_8.eContents().get(1);
		private final Assignment cClockAssignment_8_2 = (Assignment)cGroup_8.eContents().get(2);
		private final CrossReference cClockClockDefinitionCrossReference_8_2_0 = (CrossReference)cClockAssignment_8_2.eContents().get(0);
		private final RuleCall cClockClockDefinitionIDTerminalRuleCall_8_2_0_1 = (RuleCall)cClockClockDefinitionCrossReference_8_2_0.eContents().get(1);
		
		//CausalReaction:
		//    "Reaction" "(" input=EventSpec "," output=EventSpec ")" "within" interval=Interval
		//    ("using" "clock" clock=[ClockDefinition])?;
		@Override public ParserRule getRule() { return rule; }
		
		//"Reaction" "(" input=EventSpec "," output=EventSpec ")" "within" interval=Interval
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup() { return cGroup; }
		
		//"Reaction"
		public Keyword getReactionKeyword_0() { return cReactionKeyword_0; }
		
		//"("
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//input=EventSpec
		public Assignment getInputAssignment_2() { return cInputAssignment_2; }
		
		//EventSpec
		public RuleCall getInputEventSpecParserRuleCall_2_0() { return cInputEventSpecParserRuleCall_2_0; }
		
		//","
		public Keyword getCommaKeyword_3() { return cCommaKeyword_3; }
		
		//output=EventSpec
		public Assignment getOutputAssignment_4() { return cOutputAssignment_4; }
		
		//EventSpec
		public RuleCall getOutputEventSpecParserRuleCall_4_0() { return cOutputEventSpecParserRuleCall_4_0; }
		
		//")"
		public Keyword getRightParenthesisKeyword_5() { return cRightParenthesisKeyword_5; }
		
		//"within"
		public Keyword getWithinKeyword_6() { return cWithinKeyword_6; }
		
		//interval=Interval
		public Assignment getIntervalAssignment_7() { return cIntervalAssignment_7; }
		
		//Interval
		public RuleCall getIntervalIntervalParserRuleCall_7_0() { return cIntervalIntervalParserRuleCall_7_0; }
		
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup_8() { return cGroup_8; }
		
		//"using"
		public Keyword getUsingKeyword_8_0() { return cUsingKeyword_8_0; }
		
		//"clock"
		public Keyword getClockKeyword_8_1() { return cClockKeyword_8_1; }
		
		//clock=[ClockDefinition]
		public Assignment getClockAssignment_8_2() { return cClockAssignment_8_2; }
		
		//[ClockDefinition]
		public CrossReference getClockClockDefinitionCrossReference_8_2_0() { return cClockClockDefinitionCrossReference_8_2_0; }
		
		//ID
		public RuleCall getClockClockDefinitionIDTerminalRuleCall_8_2_0_1() { return cClockClockDefinitionIDTerminalRuleCall_8_2_0_1; }
	}
	public class CausalAgeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.CausalAge");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cAgeKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cOutputAssignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cOutputEventSpecParserRuleCall_2_0 = (RuleCall)cOutputAssignment_2.eContents().get(0);
		private final Keyword cCommaKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cInputAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cInputEventSpecParserRuleCall_4_0 = (RuleCall)cInputAssignment_4.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Keyword cWithinKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Assignment cIntervalAssignment_7 = (Assignment)cGroup.eContents().get(7);
		private final RuleCall cIntervalIntervalParserRuleCall_7_0 = (RuleCall)cIntervalAssignment_7.eContents().get(0);
		private final Group cGroup_8 = (Group)cGroup.eContents().get(8);
		private final Keyword cUsingKeyword_8_0 = (Keyword)cGroup_8.eContents().get(0);
		private final Keyword cClockKeyword_8_1 = (Keyword)cGroup_8.eContents().get(1);
		private final Assignment cClockAssignment_8_2 = (Assignment)cGroup_8.eContents().get(2);
		private final CrossReference cClockClockDefinitionCrossReference_8_2_0 = (CrossReference)cClockAssignment_8_2.eContents().get(0);
		private final RuleCall cClockClockDefinitionIDTerminalRuleCall_8_2_0_1 = (RuleCall)cClockClockDefinitionCrossReference_8_2_0.eContents().get(1);
		
		//CausalAge:
		//    "Age" "(" output=EventSpec "," input=EventSpec ")" "within" interval=Interval
		//    ("using" "clock" clock=[ClockDefinition])?;
		@Override public ParserRule getRule() { return rule; }
		
		//"Age" "(" output=EventSpec "," input=EventSpec ")" "within" interval=Interval
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup() { return cGroup; }
		
		//"Age"
		public Keyword getAgeKeyword_0() { return cAgeKeyword_0; }
		
		//"("
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//output=EventSpec
		public Assignment getOutputAssignment_2() { return cOutputAssignment_2; }
		
		//EventSpec
		public RuleCall getOutputEventSpecParserRuleCall_2_0() { return cOutputEventSpecParserRuleCall_2_0; }
		
		//","
		public Keyword getCommaKeyword_3() { return cCommaKeyword_3; }
		
		//input=EventSpec
		public Assignment getInputAssignment_4() { return cInputAssignment_4; }
		
		//EventSpec
		public RuleCall getInputEventSpecParserRuleCall_4_0() { return cInputEventSpecParserRuleCall_4_0; }
		
		//")"
		public Keyword getRightParenthesisKeyword_5() { return cRightParenthesisKeyword_5; }
		
		//"within"
		public Keyword getWithinKeyword_6() { return cWithinKeyword_6; }
		
		//interval=Interval
		public Assignment getIntervalAssignment_7() { return cIntervalAssignment_7; }
		
		//Interval
		public RuleCall getIntervalIntervalParserRuleCall_7_0() { return cIntervalIntervalParserRuleCall_7_0; }
		
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup_8() { return cGroup_8; }
		
		//"using"
		public Keyword getUsingKeyword_8_0() { return cUsingKeyword_8_0; }
		
		//"clock"
		public Keyword getClockKeyword_8_1() { return cClockKeyword_8_1; }
		
		//clock=[ClockDefinition]
		public Assignment getClockAssignment_8_2() { return cClockAssignment_8_2; }
		
		//[ClockDefinition]
		public CrossReference getClockClockDefinitionCrossReference_8_2_0() { return cClockClockDefinitionCrossReference_8_2_0; }
		
		//ID
		public RuleCall getClockClockDefinitionIDTerminalRuleCall_8_2_0_1() { return cClockClockDefinitionIDTerminalRuleCall_8_2_0_1; }
	}
	public class EventExprElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.EventExpr");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Assignment cEventAssignment_0 = (Assignment)cAlternatives.eContents().get(0);
		private final RuleCall cEventEventSpecParserRuleCall_0_0 = (RuleCall)cEventAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Assignment cSequenceAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final Keyword cSequenceLeftParenthesisKeyword_1_0_0 = (Keyword)cSequenceAssignment_1_0.eContents().get(0);
		private final Assignment cEventsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cEventsEventListParserRuleCall_1_1_0 = (RuleCall)cEventsAssignment_1_1.eContents().get(0);
		private final Keyword cRightParenthesisKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		private final Group cGroup_2 = (Group)cAlternatives.eContents().get(2);
		private final Keyword cLeftCurlyBracketKeyword_2_0 = (Keyword)cGroup_2.eContents().get(0);
		private final Assignment cEventsAssignment_2_1 = (Assignment)cGroup_2.eContents().get(1);
		private final RuleCall cEventsEventListParserRuleCall_2_1_0 = (RuleCall)cEventsAssignment_2_1.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_2_2 = (Keyword)cGroup_2.eContents().get(2);
		
		//EventExpr:
		//    event=EventSpec |
		//    (sequence?="(" events=EventList ")") | // event sequence
		//    ("{" events=EventList "}");
		@Override public ParserRule getRule() { return rule; }
		
		//event=EventSpec |
		//(sequence?="(" events=EventList ")") | // event sequence
		//("{" events=EventList "}")
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//event=EventSpec
		public Assignment getEventAssignment_0() { return cEventAssignment_0; }
		
		//EventSpec
		public RuleCall getEventEventSpecParserRuleCall_0_0() { return cEventEventSpecParserRuleCall_0_0; }
		
		//(sequence?="(" events=EventList ")")
		public Group getGroup_1() { return cGroup_1; }
		
		//sequence?="("
		public Assignment getSequenceAssignment_1_0() { return cSequenceAssignment_1_0; }
		
		//"("
		public Keyword getSequenceLeftParenthesisKeyword_1_0_0() { return cSequenceLeftParenthesisKeyword_1_0_0; }
		
		//events=EventList
		public Assignment getEventsAssignment_1_1() { return cEventsAssignment_1_1; }
		
		//EventList
		public RuleCall getEventsEventListParserRuleCall_1_1_0() { return cEventsEventListParserRuleCall_1_1_0; }
		
		//")"
		public Keyword getRightParenthesisKeyword_1_2() { return cRightParenthesisKeyword_1_2; }
		
		//// event sequence
		//   ("{" events=EventList "}")
		public Group getGroup_2() { return cGroup_2; }
		
		//"{"
		public Keyword getLeftCurlyBracketKeyword_2_0() { return cLeftCurlyBracketKeyword_2_0; }
		
		//events=EventList
		public Assignment getEventsAssignment_2_1() { return cEventsAssignment_2_1; }
		
		//EventList
		public RuleCall getEventsEventListParserRuleCall_2_1_0() { return cEventsEventListParserRuleCall_2_1_0; }
		
		//"}"
		public Keyword getRightCurlyBracketKeyword_2_2() { return cRightCurlyBracketKeyword_2_2; }
	}
	public class EventListElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.EventList");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cEventsAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cEventsEventSpecParserRuleCall_0_0 = (RuleCall)cEventsAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cCommaKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cEventsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cEventsEventSpecParserRuleCall_1_1_0 = (RuleCall)cEventsAssignment_1_1.eContents().get(0);
		
		//            // event set
		//EventList:
		//    events+=EventSpec ("," events+=EventSpec)*;
		@Override public ParserRule getRule() { return rule; }
		
		//events+=EventSpec ("," events+=EventSpec)*
		public Group getGroup() { return cGroup; }
		
		//events+=EventSpec
		public Assignment getEventsAssignment_0() { return cEventsAssignment_0; }
		
		//EventSpec
		public RuleCall getEventsEventSpecParserRuleCall_0_0() { return cEventsEventSpecParserRuleCall_0_0; }
		
		//("," events+=EventSpec)*
		public Group getGroup_1() { return cGroup_1; }
		
		//","
		public Keyword getCommaKeyword_1_0() { return cCommaKeyword_1_0; }
		
		//events+=EventSpec
		public Assignment getEventsAssignment_1_1() { return cEventsAssignment_1_1; }
		
		//EventSpec
		public RuleCall getEventsEventSpecParserRuleCall_1_1_0() { return cEventsEventSpecParserRuleCall_1_1_0; }
	}
	public class EventSpecElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.EventSpec");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cPortAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final CrossReference cPortPortCrossReference_0_0 = (CrossReference)cPortAssignment_0.eContents().get(0);
		private final RuleCall cPortPortIDTerminalRuleCall_0_0_1 = (RuleCall)cPortPortCrossReference_0_0.eContents().get(1);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cFullStopKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cEventValueAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cEventValueIDTerminalRuleCall_1_1_0 = (RuleCall)cEventValueAssignment_1_1.eContents().get(0);
		
		//EventSpec:
		//    port=[Port] ("." eventValue=ID)?;
		@Override public ParserRule getRule() { return rule; }
		
		//port=[Port] ("." eventValue=ID)?
		public Group getGroup() { return cGroup; }
		
		//port=[Port]
		public Assignment getPortAssignment_0() { return cPortAssignment_0; }
		
		//[Port]
		public CrossReference getPortPortCrossReference_0_0() { return cPortPortCrossReference_0_0; }
		
		//ID
		public RuleCall getPortPortIDTerminalRuleCall_0_0_1() { return cPortPortIDTerminalRuleCall_0_0_1; }
		
		//("." eventValue=ID)?
		public Group getGroup_1() { return cGroup_1; }
		
		//"."
		public Keyword getFullStopKeyword_1_0() { return cFullStopKeyword_1_0; }
		
		//eventValue=ID
		public Assignment getEventValueAssignment_1_1() { return cEventValueAssignment_1_1; }
		
		//ID
		public RuleCall getEventValueIDTerminalRuleCall_1_1_0() { return cEventValueIDTerminalRuleCall_1_1_0; }
	}
	public class PortElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Port");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cNameIDTerminalRuleCall_0_0 = (RuleCall)cNameAssignment_0.eContents().get(0);
		private final Assignment cIsInputAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cIsInputINTTerminalRuleCall_1_0 = (RuleCall)cIsInputAssignment_1.eContents().get(0);
		
		//Port:
		//    name=ID isInput=INT;
		@Override public ParserRule getRule() { return rule; }
		
		//name=ID isInput=INT
		public Group getGroup() { return cGroup; }
		
		//name=ID
		public Assignment getNameAssignment_0() { return cNameAssignment_0; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_0_0() { return cNameIDTerminalRuleCall_0_0; }
		
		//isInput=INT
		public Assignment getIsInputAssignment_1() { return cIsInputAssignment_1; }
		
		//INT
		public RuleCall getIsInputINTTerminalRuleCall_1_0() { return cIsInputINTTerminalRuleCall_1_0; }
	}
	public class IntervalElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Interval");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Assignment cTimeAssignment_0 = (Assignment)cAlternatives.eContents().get(0);
		private final RuleCall cTimeTimeExprParserRuleCall_0_0 = (RuleCall)cTimeAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cAlternatives.eContents().get(1);
		private final Assignment cLBoundAssignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cLBoundBoundaryParserRuleCall_1_0_0 = (RuleCall)cLBoundAssignment_1_0.eContents().get(0);
		private final Assignment cLbValueAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cLbValueValueParserRuleCall_1_1_0 = (RuleCall)cLbValueAssignment_1_1.eContents().get(0);
		private final Keyword cCommaKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		private final Assignment cUbValueAssignment_1_3 = (Assignment)cGroup_1.eContents().get(3);
		private final RuleCall cUbValueValueParserRuleCall_1_3_0 = (RuleCall)cUbValueAssignment_1_3.eContents().get(0);
		private final Assignment cUBoundAssignment_1_4 = (Assignment)cGroup_1.eContents().get(4);
		private final RuleCall cUBoundBoundaryParserRuleCall_1_4_0 = (RuleCall)cUBoundAssignment_1_4.eContents().get(0);
		private final Assignment cUnitAssignment_1_5 = (Assignment)cGroup_1.eContents().get(5);
		private final RuleCall cUnitUnitEnumRuleCall_1_5_0 = (RuleCall)cUnitAssignment_1_5.eContents().get(0);
		
		// // component name implicit, isInput used as boolean
		//Interval:
		//    time=TimeExpr |
		//    (lBound=Boundary lbValue=Value "," ubValue=Value uBound=Boundary unit=Unit);
		@Override public ParserRule getRule() { return rule; }
		
		//time=TimeExpr |
		//(lBound=Boundary lbValue=Value "," ubValue=Value uBound=Boundary unit=Unit)
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//time=TimeExpr
		public Assignment getTimeAssignment_0() { return cTimeAssignment_0; }
		
		//TimeExpr
		public RuleCall getTimeTimeExprParserRuleCall_0_0() { return cTimeTimeExprParserRuleCall_0_0; }
		
		//(lBound=Boundary lbValue=Value "," ubValue=Value uBound=Boundary unit=Unit)
		public Group getGroup_1() { return cGroup_1; }
		
		//lBound=Boundary
		public Assignment getLBoundAssignment_1_0() { return cLBoundAssignment_1_0; }
		
		//Boundary
		public RuleCall getLBoundBoundaryParserRuleCall_1_0_0() { return cLBoundBoundaryParserRuleCall_1_0_0; }
		
		//lbValue=Value
		public Assignment getLbValueAssignment_1_1() { return cLbValueAssignment_1_1; }
		
		//Value
		public RuleCall getLbValueValueParserRuleCall_1_1_0() { return cLbValueValueParserRuleCall_1_1_0; }
		
		//","
		public Keyword getCommaKeyword_1_2() { return cCommaKeyword_1_2; }
		
		//ubValue=Value
		public Assignment getUbValueAssignment_1_3() { return cUbValueAssignment_1_3; }
		
		//Value
		public RuleCall getUbValueValueParserRuleCall_1_3_0() { return cUbValueValueParserRuleCall_1_3_0; }
		
		//uBound=Boundary
		public Assignment getUBoundAssignment_1_4() { return cUBoundAssignment_1_4; }
		
		//Boundary
		public RuleCall getUBoundBoundaryParserRuleCall_1_4_0() { return cUBoundBoundaryParserRuleCall_1_4_0; }
		
		//unit=Unit
		public Assignment getUnitAssignment_1_5() { return cUnitAssignment_1_5; }
		
		//Unit
		public RuleCall getUnitUnitEnumRuleCall_1_5_0() { return cUnitUnitEnumRuleCall_1_5_0; }
	}
	public class TimeExprElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.TimeExpr");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cValueAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cValueValueParserRuleCall_0_0 = (RuleCall)cValueAssignment_0.eContents().get(0);
		private final Assignment cUnitAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cUnitUnitEnumRuleCall_1_0 = (RuleCall)cUnitAssignment_1.eContents().get(0);
		
		//TimeExpr:
		//    value=Value unit=Unit;
		@Override public ParserRule getRule() { return rule; }
		
		//value=Value unit=Unit
		public Group getGroup() { return cGroup; }
		
		//value=Value
		public Assignment getValueAssignment_0() { return cValueAssignment_0; }
		
		//Value
		public RuleCall getValueValueParserRuleCall_0_0() { return cValueValueParserRuleCall_0_0; }
		
		//unit=Unit
		public Assignment getUnitAssignment_1() { return cUnitAssignment_1; }
		
		//Unit
		public RuleCall getUnitUnitEnumRuleCall_1_0() { return cUnitUnitEnumRuleCall_1_0; }
	}
	public class BoundaryElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Boundary");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cLeftSquareBracketKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cRightSquareBracketKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		
		//Boundary:
		//    "[" | "]";
		@Override public ParserRule getRule() { return rule; }
		
		//"[" | "]"
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//"["
		public Keyword getLeftSquareBracketKeyword_0() { return cLeftSquareBracketKeyword_0; }
		
		//"]"
		public Keyword getRightSquareBracketKeyword_1() { return cRightSquareBracketKeyword_1; }
	}
	public class ValueElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Value");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final RuleCall cINTTerminalRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cFullStopKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final RuleCall cINTTerminalRuleCall_1_1 = (RuleCall)cGroup_1.eContents().get(1);
		
		//Value returns ecore::EDouble:
		//    INT ("." INT)?;
		@Override public ParserRule getRule() { return rule; }
		
		//INT ("." INT)?
		public Group getGroup() { return cGroup; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_0() { return cINTTerminalRuleCall_0; }
		
		//("." INT)?
		public Group getGroup_1() { return cGroup_1; }
		
		//"."
		public Keyword getFullStopKeyword_1_0() { return cFullStopKeyword_1_0; }
		
		//INT
		public RuleCall getINTTerminalRuleCall_1_1() { return cINTTerminalRuleCall_1_1; }
	}
	public class CausalFuncDeclElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.CausalFuncDecl");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cFuncNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cFuncNameCausalFuncNameEnumRuleCall_0_0 = (RuleCall)cFuncNameAssignment_0.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cPort1Assignment_2 = (Assignment)cGroup.eContents().get(2);
		private final CrossReference cPort1PortCrossReference_2_0 = (CrossReference)cPort1Assignment_2.eContents().get(0);
		private final RuleCall cPort1PortIDTerminalRuleCall_2_0_1 = (RuleCall)cPort1PortCrossReference_2_0.eContents().get(1);
		private final Keyword cCommaKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cPort2Assignment_4 = (Assignment)cGroup.eContents().get(4);
		private final CrossReference cPort2PortCrossReference_4_0 = (CrossReference)cPort2Assignment_4.eContents().get(0);
		private final RuleCall cPort2PortIDTerminalRuleCall_4_0_1 = (RuleCall)cPort2PortCrossReference_4_0.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Keyword cColonEqualsSignKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Assignment cRelationAssignment_7 = (Assignment)cGroup.eContents().get(7);
		private final RuleCall cRelationCausalRelationEnumRuleCall_7_0 = (RuleCall)cRelationAssignment_7.eContents().get(0);
		
		//CausalFuncDecl:
		//    funcName=CausalFuncName "(" port1=[Port] "," port2=[Port] ")" ":=" relation=CausalRelation;
		@Override public ParserRule getRule() { return rule; }
		
		//funcName=CausalFuncName "(" port1=[Port] "," port2=[Port] ")" ":=" relation=CausalRelation
		public Group getGroup() { return cGroup; }
		
		//funcName=CausalFuncName
		public Assignment getFuncNameAssignment_0() { return cFuncNameAssignment_0; }
		
		//CausalFuncName
		public RuleCall getFuncNameCausalFuncNameEnumRuleCall_0_0() { return cFuncNameCausalFuncNameEnumRuleCall_0_0; }
		
		//"("
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//port1=[Port]
		public Assignment getPort1Assignment_2() { return cPort1Assignment_2; }
		
		//[Port]
		public CrossReference getPort1PortCrossReference_2_0() { return cPort1PortCrossReference_2_0; }
		
		//ID
		public RuleCall getPort1PortIDTerminalRuleCall_2_0_1() { return cPort1PortIDTerminalRuleCall_2_0_1; }
		
		//","
		public Keyword getCommaKeyword_3() { return cCommaKeyword_3; }
		
		//port2=[Port]
		public Assignment getPort2Assignment_4() { return cPort2Assignment_4; }
		
		//[Port]
		public CrossReference getPort2PortCrossReference_4_0() { return cPort2PortCrossReference_4_0; }
		
		//ID
		public RuleCall getPort2PortIDTerminalRuleCall_4_0_1() { return cPort2PortIDTerminalRuleCall_4_0_1; }
		
		//")"
		public Keyword getRightParenthesisKeyword_5() { return cRightParenthesisKeyword_5; }
		
		//":="
		public Keyword getColonEqualsSignKeyword_6() { return cColonEqualsSignKeyword_6; }
		
		//relation=CausalRelation
		public Assignment getRelationAssignment_7() { return cRelationAssignment_7; }
		
		//CausalRelation
		public RuleCall getRelationCausalRelationEnumRuleCall_7_0() { return cRelationCausalRelationEnumRuleCall_7_0; }
	}
	public class ClockDefinitionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.ClockDefinition");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cClockKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cHasKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
		private final Keyword cResolutionKeyword_3_0 = (Keyword)cGroup_3.eContents().get(0);
		private final Assignment cResolutionAssignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
		private final RuleCall cResolutionTimeExprParserRuleCall_3_1_0 = (RuleCall)cResolutionAssignment_3_1.eContents().get(0);
		private final Group cGroup_4 = (Group)cGroup.eContents().get(4);
		private final Keyword cSkewKeyword_4_0 = (Keyword)cGroup_4.eContents().get(0);
		private final Assignment cSkewAssignment_4_1 = (Assignment)cGroup_4.eContents().get(1);
		private final RuleCall cSkewTimeExprParserRuleCall_4_1_0 = (RuleCall)cSkewAssignment_4_1.eContents().get(0);
		private final Group cGroup_5 = (Group)cGroup.eContents().get(5);
		private final Keyword cDriftKeyword_5_0 = (Keyword)cGroup_5.eContents().get(0);
		private final Assignment cDriftAssignment_5_1 = (Assignment)cGroup_5.eContents().get(1);
		private final RuleCall cDriftIntervalParserRuleCall_5_1_0 = (RuleCall)cDriftAssignment_5_1.eContents().get(0);
		private final Group cGroup_6 = (Group)cGroup.eContents().get(6);
		private final Keyword cMaxdiffKeyword_6_0 = (Keyword)cGroup_6.eContents().get(0);
		private final Assignment cMaxdiffAssignment_6_1 = (Assignment)cGroup_6.eContents().get(1);
		private final RuleCall cMaxdiffTimeExprParserRuleCall_6_1_0 = (RuleCall)cMaxdiffAssignment_6_1.eContents().get(0);
		
		//ClockDefinition:
		//    "Clock" name=ID "has"
		//    ("resolution" resolution=TimeExpr)?
		//    ("skew" skew=TimeExpr)?
		//    ("drift" drift=Interval)?
		//    ("maxdiff" maxdiff=TimeExpr)?;
		@Override public ParserRule getRule() { return rule; }
		
		//"Clock" name=ID "has"
		//("resolution" resolution=TimeExpr)?
		//("skew" skew=TimeExpr)?
		//("drift" drift=Interval)?
		//("maxdiff" maxdiff=TimeExpr)?
		public Group getGroup() { return cGroup; }
		
		//"Clock"
		public Keyword getClockKeyword_0() { return cClockKeyword_0; }
		
		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }
		
		//"has"
		public Keyword getHasKeyword_2() { return cHasKeyword_2; }
		
		//("resolution" resolution=TimeExpr)?
		public Group getGroup_3() { return cGroup_3; }
		
		//"resolution"
		public Keyword getResolutionKeyword_3_0() { return cResolutionKeyword_3_0; }
		
		//resolution=TimeExpr
		public Assignment getResolutionAssignment_3_1() { return cResolutionAssignment_3_1; }
		
		//TimeExpr
		public RuleCall getResolutionTimeExprParserRuleCall_3_1_0() { return cResolutionTimeExprParserRuleCall_3_1_0; }
		
		//("skew" skew=TimeExpr)?
		public Group getGroup_4() { return cGroup_4; }
		
		//"skew"
		public Keyword getSkewKeyword_4_0() { return cSkewKeyword_4_0; }
		
		//skew=TimeExpr
		public Assignment getSkewAssignment_4_1() { return cSkewAssignment_4_1; }
		
		//TimeExpr
		public RuleCall getSkewTimeExprParserRuleCall_4_1_0() { return cSkewTimeExprParserRuleCall_4_1_0; }
		
		//("drift" drift=Interval)?
		public Group getGroup_5() { return cGroup_5; }
		
		//"drift"
		public Keyword getDriftKeyword_5_0() { return cDriftKeyword_5_0; }
		
		//drift=Interval
		public Assignment getDriftAssignment_5_1() { return cDriftAssignment_5_1; }
		
		//Interval
		public RuleCall getDriftIntervalParserRuleCall_5_1_0() { return cDriftIntervalParserRuleCall_5_1_0; }
		
		//("maxdiff" maxdiff=TimeExpr)?
		public Group getGroup_6() { return cGroup_6; }
		
		//"maxdiff"
		public Keyword getMaxdiffKeyword_6_0() { return cMaxdiffKeyword_6_0; }
		
		//maxdiff=TimeExpr
		public Assignment getMaxdiffAssignment_6_1() { return cMaxdiffAssignment_6_1; }
		
		//TimeExpr
		public RuleCall getMaxdiffTimeExprParserRuleCall_6_1_0() { return cMaxdiffTimeExprParserRuleCall_6_1_0; }
	}
	
	public class UnitElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Unit");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cSEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cSSKeyword_0_0 = (Keyword)cSEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cMSEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cMSMsKeyword_1_0 = (Keyword)cMSEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cUSEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cUSUsKeyword_2_0 = (Keyword)cUSEnumLiteralDeclaration_2.eContents().get(0);
		private final EnumLiteralDeclaration cNSEnumLiteralDeclaration_3 = (EnumLiteralDeclaration)cAlternatives.eContents().get(3);
		private final Keyword cNSNsKeyword_3_0 = (Keyword)cNSEnumLiteralDeclaration_3.eContents().get(0);
		
		//enum Unit:
		//    S="s" | MS="ms" | US="us" | NS="ns";
		public EnumRule getRule() { return rule; }
		
		//S="s" | MS="ms" | US="us" | NS="ns"
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//S="s"
		public EnumLiteralDeclaration getSEnumLiteralDeclaration_0() { return cSEnumLiteralDeclaration_0; }
		
		//"s"
		public Keyword getSSKeyword_0_0() { return cSSKeyword_0_0; }
		
		//MS="ms"
		public EnumLiteralDeclaration getMSEnumLiteralDeclaration_1() { return cMSEnumLiteralDeclaration_1; }
		
		//"ms"
		public Keyword getMSMsKeyword_1_0() { return cMSMsKeyword_1_0; }
		
		//US="us"
		public EnumLiteralDeclaration getUSEnumLiteralDeclaration_2() { return cUSEnumLiteralDeclaration_2; }
		
		//"us"
		public Keyword getUSUsKeyword_2_0() { return cUSUsKeyword_2_0; }
		
		//NS="ns"
		public EnumLiteralDeclaration getNSEnumLiteralDeclaration_3() { return cNSEnumLiteralDeclaration_3; }
		
		//"ns"
		public Keyword getNSNsKeyword_3_0() { return cNSNsKeyword_3_0; }
	}
	public class CausalFuncNameElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.CausalFuncName");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cREACTIONEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cREACTIONVerticalLineGreaterThanSignKeyword_0_0 = (Keyword)cREACTIONEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cAGEEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cAGELessThanSignVerticalLineKeyword_1_0 = (Keyword)cAGEEnumLiteralDeclaration_1.eContents().get(0);
		
		//enum CausalFuncName:
		//    REACTION="|>" | AGE="<|";
		public EnumRule getRule() { return rule; }
		
		//REACTION="|>" | AGE="<|"
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//REACTION="|>"
		public EnumLiteralDeclaration getREACTIONEnumLiteralDeclaration_0() { return cREACTIONEnumLiteralDeclaration_0; }
		
		//"|>"
		public Keyword getREACTIONVerticalLineGreaterThanSignKeyword_0_0() { return cREACTIONVerticalLineGreaterThanSignKeyword_0_0; }
		
		//AGE="<|"
		public EnumLiteralDeclaration getAGEEnumLiteralDeclaration_1() { return cAGEEnumLiteralDeclaration_1; }
		
		//"<|"
		public Keyword getAGELessThanSignVerticalLineKeyword_1_0() { return cAGELessThanSignVerticalLineKeyword_1_0; }
	}
	public class CausalRelationElements extends AbstractElementFinder.AbstractEnumRuleElementFinder {
		private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.CausalRelation");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final EnumLiteralDeclaration cFIFOEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
		private final Keyword cFIFOFIFOKeyword_0_0 = (Keyword)cFIFOEnumLiteralDeclaration_0.eContents().get(0);
		private final EnumLiteralDeclaration cLIFOEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
		private final Keyword cLIFOLIFOKeyword_1_0 = (Keyword)cLIFOEnumLiteralDeclaration_1.eContents().get(0);
		private final EnumLiteralDeclaration cIDEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
		private final Keyword cIDIDKeyword_2_0 = (Keyword)cIDEnumLiteralDeclaration_2.eContents().get(0);
		
		//enum CausalRelation:
		//    FIFO="FIFO" | LIFO="LIFO" | ID="ID";
		public EnumRule getRule() { return rule; }
		
		//FIFO="FIFO" | LIFO="LIFO" | ID="ID"
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//FIFO="FIFO"
		public EnumLiteralDeclaration getFIFOEnumLiteralDeclaration_0() { return cFIFOEnumLiteralDeclaration_0; }
		
		//"FIFO"
		public Keyword getFIFOFIFOKeyword_0_0() { return cFIFOFIFOKeyword_0_0; }
		
		//LIFO="LIFO"
		public EnumLiteralDeclaration getLIFOEnumLiteralDeclaration_1() { return cLIFOEnumLiteralDeclaration_1; }
		
		//"LIFO"
		public Keyword getLIFOLIFOKeyword_1_0() { return cLIFOLIFOKeyword_1_0; }
		
		//ID="ID"
		public EnumLiteralDeclaration getIDEnumLiteralDeclaration_2() { return cIDEnumLiteralDeclaration_2; }
		
		//"ID"
		public Keyword getIDIDKeyword_2_0() { return cIDIDKeyword_2_0; }
	}
	
	private final ModelElements pModel;
	private final TimeSpecElements pTimeSpec;
	private final SingleEventElements pSingleEvent;
	private final RepetitionElements pRepetition;
	private final RepetitionOptionsElements pRepetitionOptions;
	private final JitterElements pJitter;
	private final OffsetElements pOffset;
	private final ReactionElements pReaction;
	private final AgeElements pAge;
	private final CausalReactionElements pCausalReaction;
	private final CausalAgeElements pCausalAge;
	private final EventExprElements pEventExpr;
	private final EventListElements pEventList;
	private final EventSpecElements pEventSpec;
	private final PortElements pPort;
	private final IntervalElements pInterval;
	private final TimeExprElements pTimeExpr;
	private final BoundaryElements pBoundary;
	private final ValueElements pValue;
	private final UnitElements eUnit;
	private final CausalFuncDeclElements pCausalFuncDecl;
	private final CausalFuncNameElements eCausalFuncName;
	private final CausalRelationElements eCausalRelation;
	private final ClockDefinitionElements pClockDefinition;
	
	private final Grammar grammar;
	
	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public ContractSpecGrammarAccess(GrammarProvider grammarProvider,
			TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
		this.pModel = new ModelElements();
		this.pTimeSpec = new TimeSpecElements();
		this.pSingleEvent = new SingleEventElements();
		this.pRepetition = new RepetitionElements();
		this.pRepetitionOptions = new RepetitionOptionsElements();
		this.pJitter = new JitterElements();
		this.pOffset = new OffsetElements();
		this.pReaction = new ReactionElements();
		this.pAge = new AgeElements();
		this.pCausalReaction = new CausalReactionElements();
		this.pCausalAge = new CausalAgeElements();
		this.pEventExpr = new EventExprElements();
		this.pEventList = new EventListElements();
		this.pEventSpec = new EventSpecElements();
		this.pPort = new PortElements();
		this.pInterval = new IntervalElements();
		this.pTimeExpr = new TimeExprElements();
		this.pBoundary = new BoundaryElements();
		this.pValue = new ValueElements();
		this.eUnit = new UnitElements();
		this.pCausalFuncDecl = new CausalFuncDeclElements();
		this.eCausalFuncName = new CausalFuncNameElements();
		this.eCausalRelation = new CausalRelationElements();
		this.pClockDefinition = new ClockDefinitionElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.eclipse.fordiac.ide.ContractSpec".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//Model:
	//    timeSpec+=TimeSpec*;
	public ModelElements getModelAccess() {
		return pModel;
	}
	
	public ParserRule getModelRule() {
		return getModelAccess().getRule();
	}
	
	//TimeSpec:
	//    SingleEvent | Repetition | Reaction | Age |
	//    CausalReaction | CausalAge | CausalFuncDecl | ClockDefinition;
	public TimeSpecElements getTimeSpecAccess() {
		return pTimeSpec;
	}
	
	public ParserRule getTimeSpecRule() {
		return getTimeSpecAccess().getRule();
	}
	
	//SingleEvent:
	//    events=EventList "occurs" "within" interval=Interval
	//    ("using" "clock" clock=[ClockDefinition])?;
	public SingleEventElements getSingleEventAccess() {
		return pSingleEvent;
	}
	
	public ParserRule getSingleEventRule() {
		return getSingleEventAccess().getRule();
	}
	
	//Repetition:
	//    events=EventList "occurs" "every" interval=Interval
	//    ("with" repetitionOptions=RepetitionOptions)?
	//    ("using" "clock" clock=[ClockDefinition])?;
	public RepetitionElements getRepetitionAccess() {
		return pRepetition;
	}
	
	public ParserRule getRepetitionRule() {
		return getRepetitionAccess().getRule();
	}
	
	//RepetitionOptions:
	//    (jitter=Jitter ("and" offset=Offset)?) |
	//    (offset=Offset ("and" jitter=Jitter)?);
	public RepetitionOptionsElements getRepetitionOptionsAccess() {
		return pRepetitionOptions;
	}
	
	public ParserRule getRepetitionOptionsRule() {
		return getRepetitionOptionsAccess().getRule();
	}
	
	//Jitter:
	//    "jitter" time=TimeExpr;
	public JitterElements getJitterAccess() {
		return pJitter;
	}
	
	public ParserRule getJitterRule() {
		return getJitterAccess().getRule();
	}
	
	//Offset:
	//    "offset" interval=Interval;
	public OffsetElements getOffsetAccess() {
		return pOffset;
	}
	
	public ParserRule getOffsetRule() {
		return getOffsetAccess().getRule();
	}
	
	//Reaction:
	//    "whenever" input=EventExpr "occurs" "then" output=EventExpr "occurs" "within" interval=Interval
	//    (once?="once" | n=INT "out" "of" outOf=INT "times")?
	//    ("using" "clock" clock=[ClockDefinition])?;
	public ReactionElements getReactionAccess() {
		return pReaction;
	}
	
	public ParserRule getReactionRule() {
		return getReactionAccess().getRule();
	}
	
	//Age:
	//    "whenever" output=EventExpr "occurs" "then" input=EventExpr "has" "occurred" "within" interval=Interval
	//    (once?="once" | n=INT "out" "of" outOf=INT "times")?
	//    ("using" "clock" clock=[ClockDefinition])?;
	public AgeElements getAgeAccess() {
		return pAge;
	}
	
	public ParserRule getAgeRule() {
		return getAgeAccess().getRule();
	}
	
	//CausalReaction:
	//    "Reaction" "(" input=EventSpec "," output=EventSpec ")" "within" interval=Interval
	//    ("using" "clock" clock=[ClockDefinition])?;
	public CausalReactionElements getCausalReactionAccess() {
		return pCausalReaction;
	}
	
	public ParserRule getCausalReactionRule() {
		return getCausalReactionAccess().getRule();
	}
	
	//CausalAge:
	//    "Age" "(" output=EventSpec "," input=EventSpec ")" "within" interval=Interval
	//    ("using" "clock" clock=[ClockDefinition])?;
	public CausalAgeElements getCausalAgeAccess() {
		return pCausalAge;
	}
	
	public ParserRule getCausalAgeRule() {
		return getCausalAgeAccess().getRule();
	}
	
	//EventExpr:
	//    event=EventSpec |
	//    (sequence?="(" events=EventList ")") | // event sequence
	//    ("{" events=EventList "}");
	public EventExprElements getEventExprAccess() {
		return pEventExpr;
	}
	
	public ParserRule getEventExprRule() {
		return getEventExprAccess().getRule();
	}
	
	//            // event set
	//EventList:
	//    events+=EventSpec ("," events+=EventSpec)*;
	public EventListElements getEventListAccess() {
		return pEventList;
	}
	
	public ParserRule getEventListRule() {
		return getEventListAccess().getRule();
	}
	
	//EventSpec:
	//    port=[Port] ("." eventValue=ID)?;
	public EventSpecElements getEventSpecAccess() {
		return pEventSpec;
	}
	
	public ParserRule getEventSpecRule() {
		return getEventSpecAccess().getRule();
	}
	
	//Port:
	//    name=ID isInput=INT;
	public PortElements getPortAccess() {
		return pPort;
	}
	
	public ParserRule getPortRule() {
		return getPortAccess().getRule();
	}
	
	// // component name implicit, isInput used as boolean
	//Interval:
	//    time=TimeExpr |
	//    (lBound=Boundary lbValue=Value "," ubValue=Value uBound=Boundary unit=Unit);
	public IntervalElements getIntervalAccess() {
		return pInterval;
	}
	
	public ParserRule getIntervalRule() {
		return getIntervalAccess().getRule();
	}
	
	//TimeExpr:
	//    value=Value unit=Unit;
	public TimeExprElements getTimeExprAccess() {
		return pTimeExpr;
	}
	
	public ParserRule getTimeExprRule() {
		return getTimeExprAccess().getRule();
	}
	
	//Boundary:
	//    "[" | "]";
	public BoundaryElements getBoundaryAccess() {
		return pBoundary;
	}
	
	public ParserRule getBoundaryRule() {
		return getBoundaryAccess().getRule();
	}
	
	//Value returns ecore::EDouble:
	//    INT ("." INT)?;
	public ValueElements getValueAccess() {
		return pValue;
	}
	
	public ParserRule getValueRule() {
		return getValueAccess().getRule();
	}
	
	//enum Unit:
	//    S="s" | MS="ms" | US="us" | NS="ns";
	public UnitElements getUnitAccess() {
		return eUnit;
	}
	
	public EnumRule getUnitRule() {
		return getUnitAccess().getRule();
	}
	
	//CausalFuncDecl:
	//    funcName=CausalFuncName "(" port1=[Port] "," port2=[Port] ")" ":=" relation=CausalRelation;
	public CausalFuncDeclElements getCausalFuncDeclAccess() {
		return pCausalFuncDecl;
	}
	
	public ParserRule getCausalFuncDeclRule() {
		return getCausalFuncDeclAccess().getRule();
	}
	
	//enum CausalFuncName:
	//    REACTION="|>" | AGE="<|";
	public CausalFuncNameElements getCausalFuncNameAccess() {
		return eCausalFuncName;
	}
	
	public EnumRule getCausalFuncNameRule() {
		return getCausalFuncNameAccess().getRule();
	}
	
	//enum CausalRelation:
	//    FIFO="FIFO" | LIFO="LIFO" | ID="ID";
	public CausalRelationElements getCausalRelationAccess() {
		return eCausalRelation;
	}
	
	public EnumRule getCausalRelationRule() {
		return getCausalRelationAccess().getRule();
	}
	
	//ClockDefinition:
	//    "Clock" name=ID "has"
	//    ("resolution" resolution=TimeExpr)?
	//    ("skew" skew=TimeExpr)?
	//    ("drift" drift=Interval)?
	//    ("maxdiff" maxdiff=TimeExpr)?;
	public ClockDefinitionElements getClockDefinitionAccess() {
		return pClockDefinition;
	}
	
	public ParserRule getClockDefinitionRule() {
		return getClockDefinitionAccess().getRule();
	}
	
	//terminal ID: '^'?('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	}
	
	//terminal INT returns ecore::EInt: ('0'..'9')+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	}
	
	//terminal STRING:
	//            '"' ( '\\' . /* 'b'|'t'|'n'|'f'|'r'|'u'|'"'|"'"|'\\' */ | !('\\'|'"') )* '"' |
	//            "'" ( '\\' . /* 'b'|'t'|'n'|'f'|'r'|'u'|'"'|"'"|'\\' */ | !('\\'|"'") )* "'"
	//        ;
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	}
	
	//terminal ML_COMMENT : '/*' -> '*/';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT : '//' !('\n'|'\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	}
	
	//terminal WS         : (' '|'\t'|'\r'|'\n')+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	}
	
	//terminal ANY_OTHER: .;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	}
}
