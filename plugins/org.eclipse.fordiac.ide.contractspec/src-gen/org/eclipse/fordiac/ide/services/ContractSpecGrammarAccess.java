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
		private final Assignment cTriggerAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTriggerEventExprParserRuleCall_1_0 = (RuleCall)cTriggerAssignment_1.eContents().get(0);
		private final Keyword cOccursKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cThenKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cReactionAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cReactionEventExprParserRuleCall_4_0 = (RuleCall)cReactionAssignment_4.eContents().get(0);
		private final Keyword cOccursKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Keyword cWithinKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Assignment cIntervalAssignment_7 = (Assignment)cGroup.eContents().get(7);
		private final RuleCall cIntervalIntervalParserRuleCall_7_0 = (RuleCall)cIntervalAssignment_7.eContents().get(0);
		private final Keyword cOnceKeyword_8 = (Keyword)cGroup.eContents().get(8);
		private final Group cGroup_9 = (Group)cGroup.eContents().get(9);
		private final Assignment cNAssignment_9_0 = (Assignment)cGroup_9.eContents().get(0);
		private final RuleCall cNINTTerminalRuleCall_9_0_0 = (RuleCall)cNAssignment_9_0.eContents().get(0);
		private final Keyword cOutKeyword_9_1 = (Keyword)cGroup_9.eContents().get(1);
		private final Keyword cOfKeyword_9_2 = (Keyword)cGroup_9.eContents().get(2);
		private final Assignment cOutOfAssignment_9_3 = (Assignment)cGroup_9.eContents().get(3);
		private final RuleCall cOutOfINTTerminalRuleCall_9_3_0 = (RuleCall)cOutOfAssignment_9_3.eContents().get(0);
		private final Keyword cTimesKeyword_9_4 = (Keyword)cGroup_9.eContents().get(4);
		private final Group cGroup_10 = (Group)cGroup.eContents().get(10);
		private final Keyword cUsingKeyword_10_0 = (Keyword)cGroup_10.eContents().get(0);
		private final Keyword cClockKeyword_10_1 = (Keyword)cGroup_10.eContents().get(1);
		private final Assignment cClockAssignment_10_2 = (Assignment)cGroup_10.eContents().get(2);
		private final CrossReference cClockClockDefinitionCrossReference_10_2_0 = (CrossReference)cClockAssignment_10_2.eContents().get(0);
		private final RuleCall cClockClockDefinitionIDTerminalRuleCall_10_2_0_1 = (RuleCall)cClockClockDefinitionCrossReference_10_2_0.eContents().get(1);
		
		//Reaction:
		//    "whenever" trigger=EventExpr "occurs" "then" reaction=EventExpr "occurs" "within" interval=Interval
		//    ("once")? (n=INT "out" "of" outOf=INT "times")?
		//    ("using" "clock" clock=[ClockDefinition])?;
		@Override public ParserRule getRule() { return rule; }
		
		//"whenever" trigger=EventExpr "occurs" "then" reaction=EventExpr "occurs" "within" interval=Interval
		//("once")? (n=INT "out" "of" outOf=INT "times")?
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup() { return cGroup; }
		
		//"whenever"
		public Keyword getWheneverKeyword_0() { return cWheneverKeyword_0; }
		
		//trigger=EventExpr
		public Assignment getTriggerAssignment_1() { return cTriggerAssignment_1; }
		
		//EventExpr
		public RuleCall getTriggerEventExprParserRuleCall_1_0() { return cTriggerEventExprParserRuleCall_1_0; }
		
		//"occurs"
		public Keyword getOccursKeyword_2() { return cOccursKeyword_2; }
		
		//"then"
		public Keyword getThenKeyword_3() { return cThenKeyword_3; }
		
		//reaction=EventExpr
		public Assignment getReactionAssignment_4() { return cReactionAssignment_4; }
		
		//EventExpr
		public RuleCall getReactionEventExprParserRuleCall_4_0() { return cReactionEventExprParserRuleCall_4_0; }
		
		//"occurs"
		public Keyword getOccursKeyword_5() { return cOccursKeyword_5; }
		
		//"within"
		public Keyword getWithinKeyword_6() { return cWithinKeyword_6; }
		
		//interval=Interval
		public Assignment getIntervalAssignment_7() { return cIntervalAssignment_7; }
		
		//Interval
		public RuleCall getIntervalIntervalParserRuleCall_7_0() { return cIntervalIntervalParserRuleCall_7_0; }
		
		//("once")?
		public Keyword getOnceKeyword_8() { return cOnceKeyword_8; }
		
		//(n=INT "out" "of" outOf=INT "times")?
		public Group getGroup_9() { return cGroup_9; }
		
		//n=INT
		public Assignment getNAssignment_9_0() { return cNAssignment_9_0; }
		
		//INT
		public RuleCall getNINTTerminalRuleCall_9_0_0() { return cNINTTerminalRuleCall_9_0_0; }
		
		//"out"
		public Keyword getOutKeyword_9_1() { return cOutKeyword_9_1; }
		
		//"of"
		public Keyword getOfKeyword_9_2() { return cOfKeyword_9_2; }
		
		//outOf=INT
		public Assignment getOutOfAssignment_9_3() { return cOutOfAssignment_9_3; }
		
		//INT
		public RuleCall getOutOfINTTerminalRuleCall_9_3_0() { return cOutOfINTTerminalRuleCall_9_3_0; }
		
		//"times"
		public Keyword getTimesKeyword_9_4() { return cTimesKeyword_9_4; }
		
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
	public class AgeElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Age");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cWheneverKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cTriggerAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cTriggerEventExprParserRuleCall_1_0 = (RuleCall)cTriggerAssignment_1.eContents().get(0);
		private final Keyword cOccursKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Keyword cThenKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cReactionAssignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cReactionEventExprParserRuleCall_4_0 = (RuleCall)cReactionAssignment_4.eContents().get(0);
		private final Keyword cHasKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Keyword cOccurredKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Keyword cWithinKeyword_7 = (Keyword)cGroup.eContents().get(7);
		private final Assignment cIntervalAssignment_8 = (Assignment)cGroup.eContents().get(8);
		private final RuleCall cIntervalIntervalParserRuleCall_8_0 = (RuleCall)cIntervalAssignment_8.eContents().get(0);
		private final Keyword cOnceKeyword_9 = (Keyword)cGroup.eContents().get(9);
		private final Group cGroup_10 = (Group)cGroup.eContents().get(10);
		private final Assignment cNAssignment_10_0 = (Assignment)cGroup_10.eContents().get(0);
		private final RuleCall cNINTTerminalRuleCall_10_0_0 = (RuleCall)cNAssignment_10_0.eContents().get(0);
		private final Keyword cOutKeyword_10_1 = (Keyword)cGroup_10.eContents().get(1);
		private final Keyword cOfKeyword_10_2 = (Keyword)cGroup_10.eContents().get(2);
		private final Assignment cOutOfAssignment_10_3 = (Assignment)cGroup_10.eContents().get(3);
		private final RuleCall cOutOfINTTerminalRuleCall_10_3_0 = (RuleCall)cOutOfAssignment_10_3.eContents().get(0);
		private final Keyword cTimesKeyword_10_4 = (Keyword)cGroup_10.eContents().get(4);
		private final Group cGroup_11 = (Group)cGroup.eContents().get(11);
		private final Keyword cUsingKeyword_11_0 = (Keyword)cGroup_11.eContents().get(0);
		private final Keyword cClockKeyword_11_1 = (Keyword)cGroup_11.eContents().get(1);
		private final Assignment cClockAssignment_11_2 = (Assignment)cGroup_11.eContents().get(2);
		private final CrossReference cClockClockDefinitionCrossReference_11_2_0 = (CrossReference)cClockAssignment_11_2.eContents().get(0);
		private final RuleCall cClockClockDefinitionIDTerminalRuleCall_11_2_0_1 = (RuleCall)cClockClockDefinitionCrossReference_11_2_0.eContents().get(1);
		
		//Age:
		//    "whenever" trigger=EventExpr "occurs" "then" reaction=EventExpr "has" "occurred" "within" interval=Interval
		//    ("once")? (n=INT "out" "of" outOf=INT "times")?
		//    ("using" "clock" clock=[ClockDefinition])?;
		@Override public ParserRule getRule() { return rule; }
		
		//"whenever" trigger=EventExpr "occurs" "then" reaction=EventExpr "has" "occurred" "within" interval=Interval
		//("once")? (n=INT "out" "of" outOf=INT "times")?
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup() { return cGroup; }
		
		//"whenever"
		public Keyword getWheneverKeyword_0() { return cWheneverKeyword_0; }
		
		//trigger=EventExpr
		public Assignment getTriggerAssignment_1() { return cTriggerAssignment_1; }
		
		//EventExpr
		public RuleCall getTriggerEventExprParserRuleCall_1_0() { return cTriggerEventExprParserRuleCall_1_0; }
		
		//"occurs"
		public Keyword getOccursKeyword_2() { return cOccursKeyword_2; }
		
		//"then"
		public Keyword getThenKeyword_3() { return cThenKeyword_3; }
		
		//reaction=EventExpr
		public Assignment getReactionAssignment_4() { return cReactionAssignment_4; }
		
		//EventExpr
		public RuleCall getReactionEventExprParserRuleCall_4_0() { return cReactionEventExprParserRuleCall_4_0; }
		
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
		
		//("once")?
		public Keyword getOnceKeyword_9() { return cOnceKeyword_9; }
		
		//(n=INT "out" "of" outOf=INT "times")?
		public Group getGroup_10() { return cGroup_10; }
		
		//n=INT
		public Assignment getNAssignment_10_0() { return cNAssignment_10_0; }
		
		//INT
		public RuleCall getNINTTerminalRuleCall_10_0_0() { return cNINTTerminalRuleCall_10_0_0; }
		
		//"out"
		public Keyword getOutKeyword_10_1() { return cOutKeyword_10_1; }
		
		//"of"
		public Keyword getOfKeyword_10_2() { return cOfKeyword_10_2; }
		
		//outOf=INT
		public Assignment getOutOfAssignment_10_3() { return cOutOfAssignment_10_3; }
		
		//INT
		public RuleCall getOutOfINTTerminalRuleCall_10_3_0() { return cOutOfINTTerminalRuleCall_10_3_0; }
		
		//"times"
		public Keyword getTimesKeyword_10_4() { return cTimesKeyword_10_4; }
		
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup_11() { return cGroup_11; }
		
		//"using"
		public Keyword getUsingKeyword_11_0() { return cUsingKeyword_11_0; }
		
		//"clock"
		public Keyword getClockKeyword_11_1() { return cClockKeyword_11_1; }
		
		//clock=[ClockDefinition]
		public Assignment getClockAssignment_11_2() { return cClockAssignment_11_2; }
		
		//[ClockDefinition]
		public CrossReference getClockClockDefinitionCrossReference_11_2_0() { return cClockClockDefinitionCrossReference_11_2_0; }
		
		//ID
		public RuleCall getClockClockDefinitionIDTerminalRuleCall_11_2_0_1() { return cClockClockDefinitionIDTerminalRuleCall_11_2_0_1; }
	}
	public class CausalReactionElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.CausalReaction");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cReactionKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cE1Assignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cE1EventSpecParserRuleCall_2_0 = (RuleCall)cE1Assignment_2.eContents().get(0);
		private final Keyword cCommaKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cE2Assignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cE2EventSpecParserRuleCall_4_0 = (RuleCall)cE2Assignment_4.eContents().get(0);
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
		//    "Reaction" "(" e1=EventSpec "," e2=EventSpec ")" "within" interval=Interval
		//    ("using" "clock" clock=[ClockDefinition])?;
		@Override public ParserRule getRule() { return rule; }
		
		//"Reaction" "(" e1=EventSpec "," e2=EventSpec ")" "within" interval=Interval
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup() { return cGroup; }
		
		//"Reaction"
		public Keyword getReactionKeyword_0() { return cReactionKeyword_0; }
		
		//"("
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//e1=EventSpec
		public Assignment getE1Assignment_2() { return cE1Assignment_2; }
		
		//EventSpec
		public RuleCall getE1EventSpecParserRuleCall_2_0() { return cE1EventSpecParserRuleCall_2_0; }
		
		//","
		public Keyword getCommaKeyword_3() { return cCommaKeyword_3; }
		
		//e2=EventSpec
		public Assignment getE2Assignment_4() { return cE2Assignment_4; }
		
		//EventSpec
		public RuleCall getE2EventSpecParserRuleCall_4_0() { return cE2EventSpecParserRuleCall_4_0; }
		
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
		private final Assignment cE1Assignment_2 = (Assignment)cGroup.eContents().get(2);
		private final RuleCall cE1EventSpecParserRuleCall_2_0 = (RuleCall)cE1Assignment_2.eContents().get(0);
		private final Keyword cCommaKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cE2Assignment_4 = (Assignment)cGroup.eContents().get(4);
		private final RuleCall cE2EventSpecParserRuleCall_4_0 = (RuleCall)cE2Assignment_4.eContents().get(0);
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
		//    "Age" "(" e1=EventSpec "," e2=EventSpec ")" "within" interval=Interval
		//    ("using" "clock" clock=[ClockDefinition])?;
		@Override public ParserRule getRule() { return rule; }
		
		//"Age" "(" e1=EventSpec "," e2=EventSpec ")" "within" interval=Interval
		//("using" "clock" clock=[ClockDefinition])?
		public Group getGroup() { return cGroup; }
		
		//"Age"
		public Keyword getAgeKeyword_0() { return cAgeKeyword_0; }
		
		//"("
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//e1=EventSpec
		public Assignment getE1Assignment_2() { return cE1Assignment_2; }
		
		//EventSpec
		public RuleCall getE1EventSpecParserRuleCall_2_0() { return cE1EventSpecParserRuleCall_2_0; }
		
		//","
		public Keyword getCommaKeyword_3() { return cCommaKeyword_3; }
		
		//e2=EventSpec
		public Assignment getE2Assignment_4() { return cE2Assignment_4; }
		
		//EventSpec
		public RuleCall getE2EventSpecParserRuleCall_4_0() { return cE2EventSpecParserRuleCall_4_0; }
		
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
		private final Keyword cLeftParenthesisKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
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
		//    ("(" events=EventList ")") |
		//    ("{" events=EventList "}");
		@Override public ParserRule getRule() { return rule; }
		
		//event=EventSpec |
		//("(" events=EventList ")") |
		//("{" events=EventList "}")
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//event=EventSpec
		public Assignment getEventAssignment_0() { return cEventAssignment_0; }
		
		//EventSpec
		public RuleCall getEventEventSpecParserRuleCall_0_0() { return cEventEventSpecParserRuleCall_0_0; }
		
		//("(" events=EventList ")")
		public Group getGroup_1() { return cGroup_1; }
		
		//"("
		public Keyword getLeftParenthesisKeyword_1_0() { return cLeftParenthesisKeyword_1_0; }
		
		//events=EventList
		public Assignment getEventsAssignment_1_1() { return cEventsAssignment_1_1; }
		
		//EventList
		public RuleCall getEventsEventListParserRuleCall_1_1_0() { return cEventsEventListParserRuleCall_1_1_0; }
		
		//")"
		public Keyword getRightParenthesisKeyword_1_2() { return cRightParenthesisKeyword_1_2; }
		
		//("{" events=EventList "}")
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
		private final Assignment cB1Assignment_1_0 = (Assignment)cGroup_1.eContents().get(0);
		private final RuleCall cB1BoundaryParserRuleCall_1_0_0 = (RuleCall)cB1Assignment_1_0.eContents().get(0);
		private final Assignment cV1Assignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cV1ValueParserRuleCall_1_1_0 = (RuleCall)cV1Assignment_1_1.eContents().get(0);
		private final Keyword cCommaKeyword_1_2 = (Keyword)cGroup_1.eContents().get(2);
		private final Assignment cV2Assignment_1_3 = (Assignment)cGroup_1.eContents().get(3);
		private final RuleCall cV2ValueParserRuleCall_1_3_0 = (RuleCall)cV2Assignment_1_3.eContents().get(0);
		private final Assignment cB2Assignment_1_4 = (Assignment)cGroup_1.eContents().get(4);
		private final RuleCall cB2BoundaryParserRuleCall_1_4_0 = (RuleCall)cB2Assignment_1_4.eContents().get(0);
		private final Assignment cUnitAssignment_1_5 = (Assignment)cGroup_1.eContents().get(5);
		private final RuleCall cUnitUnitParserRuleCall_1_5_0 = (RuleCall)cUnitAssignment_1_5.eContents().get(0);
		
		// // component name implicit, isInput used as boolean
		//Interval:
		//    time=TimeExpr |
		//    (b1=Boundary v1=Value "," v2=Value b2=Boundary unit=Unit);
		@Override public ParserRule getRule() { return rule; }
		
		//time=TimeExpr |
		//(b1=Boundary v1=Value "," v2=Value b2=Boundary unit=Unit)
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//time=TimeExpr
		public Assignment getTimeAssignment_0() { return cTimeAssignment_0; }
		
		//TimeExpr
		public RuleCall getTimeTimeExprParserRuleCall_0_0() { return cTimeTimeExprParserRuleCall_0_0; }
		
		//(b1=Boundary v1=Value "," v2=Value b2=Boundary unit=Unit)
		public Group getGroup_1() { return cGroup_1; }
		
		//b1=Boundary
		public Assignment getB1Assignment_1_0() { return cB1Assignment_1_0; }
		
		//Boundary
		public RuleCall getB1BoundaryParserRuleCall_1_0_0() { return cB1BoundaryParserRuleCall_1_0_0; }
		
		//v1=Value
		public Assignment getV1Assignment_1_1() { return cV1Assignment_1_1; }
		
		//Value
		public RuleCall getV1ValueParserRuleCall_1_1_0() { return cV1ValueParserRuleCall_1_1_0; }
		
		//","
		public Keyword getCommaKeyword_1_2() { return cCommaKeyword_1_2; }
		
		//v2=Value
		public Assignment getV2Assignment_1_3() { return cV2Assignment_1_3; }
		
		//Value
		public RuleCall getV2ValueParserRuleCall_1_3_0() { return cV2ValueParserRuleCall_1_3_0; }
		
		//b2=Boundary
		public Assignment getB2Assignment_1_4() { return cB2Assignment_1_4; }
		
		//Boundary
		public RuleCall getB2BoundaryParserRuleCall_1_4_0() { return cB2BoundaryParserRuleCall_1_4_0; }
		
		//unit=Unit
		public Assignment getUnitAssignment_1_5() { return cUnitAssignment_1_5; }
		
		//Unit
		public RuleCall getUnitUnitParserRuleCall_1_5_0() { return cUnitUnitParserRuleCall_1_5_0; }
	}
	public class TimeExprElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.TimeExpr");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cValueAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cValueValueParserRuleCall_0_0 = (RuleCall)cValueAssignment_0.eContents().get(0);
		private final Assignment cUnitAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cUnitUnitParserRuleCall_1_0 = (RuleCall)cUnitAssignment_1.eContents().get(0);
		
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
		public RuleCall getUnitUnitParserRuleCall_1_0() { return cUnitUnitParserRuleCall_1_0; }
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
		private final Assignment cIntegerAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cIntegerINTTerminalRuleCall_0_0 = (RuleCall)cIntegerAssignment_0.eContents().get(0);
		private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
		private final Keyword cFullStopKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
		private final Assignment cFractionAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
		private final RuleCall cFractionINTTerminalRuleCall_1_1_0 = (RuleCall)cFractionAssignment_1_1.eContents().get(0);
		
		//Value:
		//    integer=INT ("." fraction=INT)?;
		@Override public ParserRule getRule() { return rule; }
		
		//integer=INT ("." fraction=INT)?
		public Group getGroup() { return cGroup; }
		
		//integer=INT
		public Assignment getIntegerAssignment_0() { return cIntegerAssignment_0; }
		
		//INT
		public RuleCall getIntegerINTTerminalRuleCall_0_0() { return cIntegerINTTerminalRuleCall_0_0; }
		
		//("." fraction=INT)?
		public Group getGroup_1() { return cGroup_1; }
		
		//"."
		public Keyword getFullStopKeyword_1_0() { return cFullStopKeyword_1_0; }
		
		//fraction=INT
		public Assignment getFractionAssignment_1_1() { return cFractionAssignment_1_1; }
		
		//INT
		public RuleCall getFractionINTTerminalRuleCall_1_1_0() { return cFractionINTTerminalRuleCall_1_1_0; }
	}
	public class UnitElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.Unit");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cSKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cMsKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cUsKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		private final Keyword cNsKeyword_3 = (Keyword)cAlternatives.eContents().get(3);
		
		//Unit:
		//    "s" | "ms" | "us" | "ns";
		@Override public ParserRule getRule() { return rule; }
		
		//"s" | "ms" | "us" | "ns"
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//"s"
		public Keyword getSKeyword_0() { return cSKeyword_0; }
		
		//"ms"
		public Keyword getMsKeyword_1() { return cMsKeyword_1; }
		
		//"us"
		public Keyword getUsKeyword_2() { return cUsKeyword_2; }
		
		//"ns"
		public Keyword getNsKeyword_3() { return cNsKeyword_3; }
	}
	public class CausalFuncDeclElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.CausalFuncDecl");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cFuncNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final RuleCall cFuncNameCausalFuncNameParserRuleCall_0_0 = (RuleCall)cFuncNameAssignment_0.eContents().get(0);
		private final Keyword cLeftParenthesisKeyword_1 = (Keyword)cGroup.eContents().get(1);
		private final Assignment cP1Assignment_2 = (Assignment)cGroup.eContents().get(2);
		private final CrossReference cP1PortCrossReference_2_0 = (CrossReference)cP1Assignment_2.eContents().get(0);
		private final RuleCall cP1PortIDTerminalRuleCall_2_0_1 = (RuleCall)cP1PortCrossReference_2_0.eContents().get(1);
		private final Keyword cCommaKeyword_3 = (Keyword)cGroup.eContents().get(3);
		private final Assignment cP2Assignment_4 = (Assignment)cGroup.eContents().get(4);
		private final CrossReference cP2PortCrossReference_4_0 = (CrossReference)cP2Assignment_4.eContents().get(0);
		private final RuleCall cP2PortIDTerminalRuleCall_4_0_1 = (RuleCall)cP2PortCrossReference_4_0.eContents().get(1);
		private final Keyword cRightParenthesisKeyword_5 = (Keyword)cGroup.eContents().get(5);
		private final Keyword cColonEqualsSignKeyword_6 = (Keyword)cGroup.eContents().get(6);
		private final Assignment cRelationAssignment_7 = (Assignment)cGroup.eContents().get(7);
		private final RuleCall cRelationCausalRelationParserRuleCall_7_0 = (RuleCall)cRelationAssignment_7.eContents().get(0);
		
		//CausalFuncDecl:
		//    funcName=CausalFuncName "(" p1=[Port] "," p2=[Port] ")" ":=" relation=CausalRelation;
		@Override public ParserRule getRule() { return rule; }
		
		//funcName=CausalFuncName "(" p1=[Port] "," p2=[Port] ")" ":=" relation=CausalRelation
		public Group getGroup() { return cGroup; }
		
		//funcName=CausalFuncName
		public Assignment getFuncNameAssignment_0() { return cFuncNameAssignment_0; }
		
		//CausalFuncName
		public RuleCall getFuncNameCausalFuncNameParserRuleCall_0_0() { return cFuncNameCausalFuncNameParserRuleCall_0_0; }
		
		//"("
		public Keyword getLeftParenthesisKeyword_1() { return cLeftParenthesisKeyword_1; }
		
		//p1=[Port]
		public Assignment getP1Assignment_2() { return cP1Assignment_2; }
		
		//[Port]
		public CrossReference getP1PortCrossReference_2_0() { return cP1PortCrossReference_2_0; }
		
		//ID
		public RuleCall getP1PortIDTerminalRuleCall_2_0_1() { return cP1PortIDTerminalRuleCall_2_0_1; }
		
		//","
		public Keyword getCommaKeyword_3() { return cCommaKeyword_3; }
		
		//p2=[Port]
		public Assignment getP2Assignment_4() { return cP2Assignment_4; }
		
		//[Port]
		public CrossReference getP2PortCrossReference_4_0() { return cP2PortCrossReference_4_0; }
		
		//ID
		public RuleCall getP2PortIDTerminalRuleCall_4_0_1() { return cP2PortIDTerminalRuleCall_4_0_1; }
		
		//")"
		public Keyword getRightParenthesisKeyword_5() { return cRightParenthesisKeyword_5; }
		
		//":="
		public Keyword getColonEqualsSignKeyword_6() { return cColonEqualsSignKeyword_6; }
		
		//relation=CausalRelation
		public Assignment getRelationAssignment_7() { return cRelationAssignment_7; }
		
		//CausalRelation
		public RuleCall getRelationCausalRelationParserRuleCall_7_0() { return cRelationCausalRelationParserRuleCall_7_0; }
	}
	public class CausalFuncNameElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.CausalFuncName");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cVerticalLineGreaterThanSignKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cLessThanSignVerticalLineKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		
		//CausalFuncName:
		//    "|>" | "<|";
		@Override public ParserRule getRule() { return rule; }
		
		//"|>" | "<|"
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//"|>"
		public Keyword getVerticalLineGreaterThanSignKeyword_0() { return cVerticalLineGreaterThanSignKeyword_0; }
		
		//"<|"
		public Keyword getLessThanSignVerticalLineKeyword_1() { return cLessThanSignVerticalLineKeyword_1; }
	}
	public class CausalRelationElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.fordiac.ide.ContractSpec.CausalRelation");
		private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
		private final Keyword cFIFOKeyword_0 = (Keyword)cAlternatives.eContents().get(0);
		private final Keyword cLIFOKeyword_1 = (Keyword)cAlternatives.eContents().get(1);
		private final Keyword cIDKeyword_2 = (Keyword)cAlternatives.eContents().get(2);
		
		//CausalRelation:
		//    "FIFO" | "LIFO" | "ID";
		@Override public ParserRule getRule() { return rule; }
		
		//"FIFO" | "LIFO" | "ID"
		public Alternatives getAlternatives() { return cAlternatives; }
		
		//"FIFO"
		public Keyword getFIFOKeyword_0() { return cFIFOKeyword_0; }
		
		//"LIFO"
		public Keyword getLIFOKeyword_1() { return cLIFOKeyword_1; }
		
		//"ID"
		public Keyword getIDKeyword_2() { return cIDKeyword_2; }
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
	private final UnitElements pUnit;
	private final CausalFuncDeclElements pCausalFuncDecl;
	private final CausalFuncNameElements pCausalFuncName;
	private final CausalRelationElements pCausalRelation;
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
		this.pUnit = new UnitElements();
		this.pCausalFuncDecl = new CausalFuncDeclElements();
		this.pCausalFuncName = new CausalFuncNameElements();
		this.pCausalRelation = new CausalRelationElements();
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
	//    "whenever" trigger=EventExpr "occurs" "then" reaction=EventExpr "occurs" "within" interval=Interval
	//    ("once")? (n=INT "out" "of" outOf=INT "times")?
	//    ("using" "clock" clock=[ClockDefinition])?;
	public ReactionElements getReactionAccess() {
		return pReaction;
	}
	
	public ParserRule getReactionRule() {
		return getReactionAccess().getRule();
	}
	
	//Age:
	//    "whenever" trigger=EventExpr "occurs" "then" reaction=EventExpr "has" "occurred" "within" interval=Interval
	//    ("once")? (n=INT "out" "of" outOf=INT "times")?
	//    ("using" "clock" clock=[ClockDefinition])?;
	public AgeElements getAgeAccess() {
		return pAge;
	}
	
	public ParserRule getAgeRule() {
		return getAgeAccess().getRule();
	}
	
	//CausalReaction:
	//    "Reaction" "(" e1=EventSpec "," e2=EventSpec ")" "within" interval=Interval
	//    ("using" "clock" clock=[ClockDefinition])?;
	public CausalReactionElements getCausalReactionAccess() {
		return pCausalReaction;
	}
	
	public ParserRule getCausalReactionRule() {
		return getCausalReactionAccess().getRule();
	}
	
	//CausalAge:
	//    "Age" "(" e1=EventSpec "," e2=EventSpec ")" "within" interval=Interval
	//    ("using" "clock" clock=[ClockDefinition])?;
	public CausalAgeElements getCausalAgeAccess() {
		return pCausalAge;
	}
	
	public ParserRule getCausalAgeRule() {
		return getCausalAgeAccess().getRule();
	}
	
	//EventExpr:
	//    event=EventSpec |
	//    ("(" events=EventList ")") |
	//    ("{" events=EventList "}");
	public EventExprElements getEventExprAccess() {
		return pEventExpr;
	}
	
	public ParserRule getEventExprRule() {
		return getEventExprAccess().getRule();
	}
	
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
	//    (b1=Boundary v1=Value "," v2=Value b2=Boundary unit=Unit);
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
	
	//Value:
	//    integer=INT ("." fraction=INT)?;
	public ValueElements getValueAccess() {
		return pValue;
	}
	
	public ParserRule getValueRule() {
		return getValueAccess().getRule();
	}
	
	//Unit:
	//    "s" | "ms" | "us" | "ns";
	public UnitElements getUnitAccess() {
		return pUnit;
	}
	
	public ParserRule getUnitRule() {
		return getUnitAccess().getRule();
	}
	
	//CausalFuncDecl:
	//    funcName=CausalFuncName "(" p1=[Port] "," p2=[Port] ")" ":=" relation=CausalRelation;
	public CausalFuncDeclElements getCausalFuncDeclAccess() {
		return pCausalFuncDecl;
	}
	
	public ParserRule getCausalFuncDeclRule() {
		return getCausalFuncDeclAccess().getRule();
	}
	
	//CausalFuncName:
	//    "|>" | "<|";
	public CausalFuncNameElements getCausalFuncNameAccess() {
		return pCausalFuncName;
	}
	
	public ParserRule getCausalFuncNameRule() {
		return getCausalFuncNameAccess().getRule();
	}
	
	//CausalRelation:
	//    "FIFO" | "LIFO" | "ID";
	public CausalRelationElements getCausalRelationAccess() {
		return pCausalRelation;
	}
	
	public ParserRule getCausalRelationRule() {
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
