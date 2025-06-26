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
package org.eclipse.fordiac.ide.ide.contentassist.antlr;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import org.eclipse.fordiac.ide.ide.contentassist.antlr.internal.InternalContractSpecParser;
import org.eclipse.fordiac.ide.services.ContractSpecGrammarAccess;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class ContractSpecParser extends AbstractContentAssistParser {

	@Singleton
	public static final class NameMappings {
		
		private final Map<AbstractElement, String> mappings;
		
		@Inject
		public NameMappings(ContractSpecGrammarAccess grammarAccess) {
			ImmutableMap.Builder<AbstractElement, String> builder = ImmutableMap.builder();
			init(builder, grammarAccess);
			this.mappings = builder.build();
		}
		
		public String getRuleName(AbstractElement element) {
			return mappings.get(element);
		}
		
		private static void init(ImmutableMap.Builder<AbstractElement, String> builder, ContractSpecGrammarAccess grammarAccess) {
			builder.put(grammarAccess.getTimeSpecAccess().getAlternatives(), "rule__TimeSpec__Alternatives");
			builder.put(grammarAccess.getRepetitionOptionsAccess().getAlternatives(), "rule__RepetitionOptions__Alternatives");
			builder.put(grammarAccess.getReactionAccess().getAlternatives_8(), "rule__Reaction__Alternatives_8");
			builder.put(grammarAccess.getAgeAccess().getAlternatives_9(), "rule__Age__Alternatives_9");
			builder.put(grammarAccess.getEventExprAccess().getAlternatives(), "rule__EventExpr__Alternatives");
			builder.put(grammarAccess.getIntervalAccess().getAlternatives(), "rule__Interval__Alternatives");
			builder.put(grammarAccess.getBoundaryAccess().getAlternatives(), "rule__Boundary__Alternatives");
			builder.put(grammarAccess.getUnitAccess().getAlternatives(), "rule__Unit__Alternatives");
			builder.put(grammarAccess.getCausalFuncNameAccess().getAlternatives(), "rule__CausalFuncName__Alternatives");
			builder.put(grammarAccess.getCausalRelationAccess().getAlternatives(), "rule__CausalRelation__Alternatives");
			builder.put(grammarAccess.getSingleEventAccess().getGroup(), "rule__SingleEvent__Group__0");
			builder.put(grammarAccess.getSingleEventAccess().getGroup_4(), "rule__SingleEvent__Group_4__0");
			builder.put(grammarAccess.getRepetitionAccess().getGroup(), "rule__Repetition__Group__0");
			builder.put(grammarAccess.getRepetitionAccess().getGroup_4(), "rule__Repetition__Group_4__0");
			builder.put(grammarAccess.getRepetitionAccess().getGroup_5(), "rule__Repetition__Group_5__0");
			builder.put(grammarAccess.getRepetitionOptionsAccess().getGroup_0(), "rule__RepetitionOptions__Group_0__0");
			builder.put(grammarAccess.getRepetitionOptionsAccess().getGroup_0_1(), "rule__RepetitionOptions__Group_0_1__0");
			builder.put(grammarAccess.getRepetitionOptionsAccess().getGroup_1(), "rule__RepetitionOptions__Group_1__0");
			builder.put(grammarAccess.getRepetitionOptionsAccess().getGroup_1_1(), "rule__RepetitionOptions__Group_1_1__0");
			builder.put(grammarAccess.getJitterAccess().getGroup(), "rule__Jitter__Group__0");
			builder.put(grammarAccess.getOffsetAccess().getGroup(), "rule__Offset__Group__0");
			builder.put(grammarAccess.getReactionAccess().getGroup(), "rule__Reaction__Group__0");
			builder.put(grammarAccess.getReactionAccess().getGroup_8_1(), "rule__Reaction__Group_8_1__0");
			builder.put(grammarAccess.getReactionAccess().getGroup_9(), "rule__Reaction__Group_9__0");
			builder.put(grammarAccess.getAgeAccess().getGroup(), "rule__Age__Group__0");
			builder.put(grammarAccess.getAgeAccess().getGroup_9_1(), "rule__Age__Group_9_1__0");
			builder.put(grammarAccess.getAgeAccess().getGroup_10(), "rule__Age__Group_10__0");
			builder.put(grammarAccess.getCausalReactionAccess().getGroup(), "rule__CausalReaction__Group__0");
			builder.put(grammarAccess.getCausalReactionAccess().getGroup_8(), "rule__CausalReaction__Group_8__0");
			builder.put(grammarAccess.getCausalAgeAccess().getGroup(), "rule__CausalAge__Group__0");
			builder.put(grammarAccess.getCausalAgeAccess().getGroup_8(), "rule__CausalAge__Group_8__0");
			builder.put(grammarAccess.getEventExprAccess().getGroup_1(), "rule__EventExpr__Group_1__0");
			builder.put(grammarAccess.getEventExprAccess().getGroup_2(), "rule__EventExpr__Group_2__0");
			builder.put(grammarAccess.getEventListAccess().getGroup(), "rule__EventList__Group__0");
			builder.put(grammarAccess.getEventListAccess().getGroup_1(), "rule__EventList__Group_1__0");
			builder.put(grammarAccess.getEventSpecAccess().getGroup(), "rule__EventSpec__Group__0");
			builder.put(grammarAccess.getEventSpecAccess().getGroup_1(), "rule__EventSpec__Group_1__0");
			builder.put(grammarAccess.getPortAccess().getGroup(), "rule__Port__Group__0");
			builder.put(grammarAccess.getIntervalAccess().getGroup_1(), "rule__Interval__Group_1__0");
			builder.put(grammarAccess.getTimeExprAccess().getGroup(), "rule__TimeExpr__Group__0");
			builder.put(grammarAccess.getValueAccess().getGroup(), "rule__Value__Group__0");
			builder.put(grammarAccess.getValueAccess().getGroup_1(), "rule__Value__Group_1__0");
			builder.put(grammarAccess.getCausalFuncDeclAccess().getGroup(), "rule__CausalFuncDecl__Group__0");
			builder.put(grammarAccess.getClockDefinitionAccess().getGroup(), "rule__ClockDefinition__Group__0");
			builder.put(grammarAccess.getClockDefinitionAccess().getGroup_3(), "rule__ClockDefinition__Group_3__0");
			builder.put(grammarAccess.getClockDefinitionAccess().getGroup_4(), "rule__ClockDefinition__Group_4__0");
			builder.put(grammarAccess.getClockDefinitionAccess().getGroup_5(), "rule__ClockDefinition__Group_5__0");
			builder.put(grammarAccess.getClockDefinitionAccess().getGroup_6(), "rule__ClockDefinition__Group_6__0");
			builder.put(grammarAccess.getModelAccess().getTimeSpecAssignment(), "rule__Model__TimeSpecAssignment");
			builder.put(grammarAccess.getSingleEventAccess().getEventsAssignment_0(), "rule__SingleEvent__EventsAssignment_0");
			builder.put(grammarAccess.getSingleEventAccess().getIntervalAssignment_3(), "rule__SingleEvent__IntervalAssignment_3");
			builder.put(grammarAccess.getSingleEventAccess().getClockAssignment_4_2(), "rule__SingleEvent__ClockAssignment_4_2");
			builder.put(grammarAccess.getRepetitionAccess().getEventsAssignment_0(), "rule__Repetition__EventsAssignment_0");
			builder.put(grammarAccess.getRepetitionAccess().getIntervalAssignment_3(), "rule__Repetition__IntervalAssignment_3");
			builder.put(grammarAccess.getRepetitionAccess().getRepetitionOptionsAssignment_4_1(), "rule__Repetition__RepetitionOptionsAssignment_4_1");
			builder.put(grammarAccess.getRepetitionAccess().getClockAssignment_5_2(), "rule__Repetition__ClockAssignment_5_2");
			builder.put(grammarAccess.getRepetitionOptionsAccess().getJitterAssignment_0_0(), "rule__RepetitionOptions__JitterAssignment_0_0");
			builder.put(grammarAccess.getRepetitionOptionsAccess().getOffsetAssignment_0_1_1(), "rule__RepetitionOptions__OffsetAssignment_0_1_1");
			builder.put(grammarAccess.getRepetitionOptionsAccess().getOffsetAssignment_1_0(), "rule__RepetitionOptions__OffsetAssignment_1_0");
			builder.put(grammarAccess.getRepetitionOptionsAccess().getJitterAssignment_1_1_1(), "rule__RepetitionOptions__JitterAssignment_1_1_1");
			builder.put(grammarAccess.getJitterAccess().getTimeAssignment_1(), "rule__Jitter__TimeAssignment_1");
			builder.put(grammarAccess.getOffsetAccess().getIntervalAssignment_1(), "rule__Offset__IntervalAssignment_1");
			builder.put(grammarAccess.getReactionAccess().getInputAssignment_1(), "rule__Reaction__InputAssignment_1");
			builder.put(grammarAccess.getReactionAccess().getOutputAssignment_4(), "rule__Reaction__OutputAssignment_4");
			builder.put(grammarAccess.getReactionAccess().getIntervalAssignment_7(), "rule__Reaction__IntervalAssignment_7");
			builder.put(grammarAccess.getReactionAccess().getOnceAssignment_8_0(), "rule__Reaction__OnceAssignment_8_0");
			builder.put(grammarAccess.getReactionAccess().getNAssignment_8_1_0(), "rule__Reaction__NAssignment_8_1_0");
			builder.put(grammarAccess.getReactionAccess().getOutOfAssignment_8_1_3(), "rule__Reaction__OutOfAssignment_8_1_3");
			builder.put(grammarAccess.getReactionAccess().getClockAssignment_9_2(), "rule__Reaction__ClockAssignment_9_2");
			builder.put(grammarAccess.getAgeAccess().getOutputAssignment_1(), "rule__Age__OutputAssignment_1");
			builder.put(grammarAccess.getAgeAccess().getInputAssignment_4(), "rule__Age__InputAssignment_4");
			builder.put(grammarAccess.getAgeAccess().getIntervalAssignment_8(), "rule__Age__IntervalAssignment_8");
			builder.put(grammarAccess.getAgeAccess().getOnceAssignment_9_0(), "rule__Age__OnceAssignment_9_0");
			builder.put(grammarAccess.getAgeAccess().getNAssignment_9_1_0(), "rule__Age__NAssignment_9_1_0");
			builder.put(grammarAccess.getAgeAccess().getOutOfAssignment_9_1_3(), "rule__Age__OutOfAssignment_9_1_3");
			builder.put(grammarAccess.getAgeAccess().getClockAssignment_10_2(), "rule__Age__ClockAssignment_10_2");
			builder.put(grammarAccess.getCausalReactionAccess().getInputAssignment_2(), "rule__CausalReaction__InputAssignment_2");
			builder.put(grammarAccess.getCausalReactionAccess().getOutputAssignment_4(), "rule__CausalReaction__OutputAssignment_4");
			builder.put(grammarAccess.getCausalReactionAccess().getIntervalAssignment_7(), "rule__CausalReaction__IntervalAssignment_7");
			builder.put(grammarAccess.getCausalReactionAccess().getClockAssignment_8_2(), "rule__CausalReaction__ClockAssignment_8_2");
			builder.put(grammarAccess.getCausalAgeAccess().getOutputAssignment_2(), "rule__CausalAge__OutputAssignment_2");
			builder.put(grammarAccess.getCausalAgeAccess().getInputAssignment_4(), "rule__CausalAge__InputAssignment_4");
			builder.put(grammarAccess.getCausalAgeAccess().getIntervalAssignment_7(), "rule__CausalAge__IntervalAssignment_7");
			builder.put(grammarAccess.getCausalAgeAccess().getClockAssignment_8_2(), "rule__CausalAge__ClockAssignment_8_2");
			builder.put(grammarAccess.getEventExprAccess().getEventAssignment_0(), "rule__EventExpr__EventAssignment_0");
			builder.put(grammarAccess.getEventExprAccess().getSequenceAssignment_1_0(), "rule__EventExpr__SequenceAssignment_1_0");
			builder.put(grammarAccess.getEventExprAccess().getEventsAssignment_1_1(), "rule__EventExpr__EventsAssignment_1_1");
			builder.put(grammarAccess.getEventExprAccess().getEventsAssignment_2_1(), "rule__EventExpr__EventsAssignment_2_1");
			builder.put(grammarAccess.getEventListAccess().getEventsAssignment_0(), "rule__EventList__EventsAssignment_0");
			builder.put(grammarAccess.getEventListAccess().getEventsAssignment_1_1(), "rule__EventList__EventsAssignment_1_1");
			builder.put(grammarAccess.getEventSpecAccess().getPortAssignment_0(), "rule__EventSpec__PortAssignment_0");
			builder.put(grammarAccess.getEventSpecAccess().getEventValueAssignment_1_1(), "rule__EventSpec__EventValueAssignment_1_1");
			builder.put(grammarAccess.getPortAccess().getNameAssignment_0(), "rule__Port__NameAssignment_0");
			builder.put(grammarAccess.getPortAccess().getIsInputAssignment_1(), "rule__Port__IsInputAssignment_1");
			builder.put(grammarAccess.getIntervalAccess().getTimeAssignment_0(), "rule__Interval__TimeAssignment_0");
			builder.put(grammarAccess.getIntervalAccess().getLBoundAssignment_1_0(), "rule__Interval__LBoundAssignment_1_0");
			builder.put(grammarAccess.getIntervalAccess().getLbValueAssignment_1_1(), "rule__Interval__LbValueAssignment_1_1");
			builder.put(grammarAccess.getIntervalAccess().getUbValueAssignment_1_3(), "rule__Interval__UbValueAssignment_1_3");
			builder.put(grammarAccess.getIntervalAccess().getUBoundAssignment_1_4(), "rule__Interval__UBoundAssignment_1_4");
			builder.put(grammarAccess.getIntervalAccess().getUnitAssignment_1_5(), "rule__Interval__UnitAssignment_1_5");
			builder.put(grammarAccess.getTimeExprAccess().getValueAssignment_0(), "rule__TimeExpr__ValueAssignment_0");
			builder.put(grammarAccess.getTimeExprAccess().getUnitAssignment_1(), "rule__TimeExpr__UnitAssignment_1");
			builder.put(grammarAccess.getCausalFuncDeclAccess().getFuncNameAssignment_0(), "rule__CausalFuncDecl__FuncNameAssignment_0");
			builder.put(grammarAccess.getCausalFuncDeclAccess().getPort1Assignment_2(), "rule__CausalFuncDecl__Port1Assignment_2");
			builder.put(grammarAccess.getCausalFuncDeclAccess().getPort2Assignment_4(), "rule__CausalFuncDecl__Port2Assignment_4");
			builder.put(grammarAccess.getCausalFuncDeclAccess().getRelationAssignment_7(), "rule__CausalFuncDecl__RelationAssignment_7");
			builder.put(grammarAccess.getClockDefinitionAccess().getNameAssignment_1(), "rule__ClockDefinition__NameAssignment_1");
			builder.put(grammarAccess.getClockDefinitionAccess().getResolutionAssignment_3_1(), "rule__ClockDefinition__ResolutionAssignment_3_1");
			builder.put(grammarAccess.getClockDefinitionAccess().getSkewAssignment_4_1(), "rule__ClockDefinition__SkewAssignment_4_1");
			builder.put(grammarAccess.getClockDefinitionAccess().getDriftAssignment_5_1(), "rule__ClockDefinition__DriftAssignment_5_1");
			builder.put(grammarAccess.getClockDefinitionAccess().getMaxdiffAssignment_6_1(), "rule__ClockDefinition__MaxdiffAssignment_6_1");
		}
	}
	
	@Inject
	private NameMappings nameMappings;

	@Inject
	private ContractSpecGrammarAccess grammarAccess;

	@Override
	protected InternalContractSpecParser createParser() {
		InternalContractSpecParser result = new InternalContractSpecParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		return nameMappings.getRuleName(element);
	}

	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}

	public ContractSpecGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(ContractSpecGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
	public NameMappings getNameMappings() {
		return nameMappings;
	}
	
	public void setNameMappings(NameMappings nameMappings) {
		this.nameMappings = nameMappings;
	}
}
