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
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fordiac.ide.contractSpec.Age;
import org.eclipse.fordiac.ide.contractSpec.CausalAge;
import org.eclipse.fordiac.ide.contractSpec.CausalFuncDecl;
import org.eclipse.fordiac.ide.contractSpec.CausalReaction;
import org.eclipse.fordiac.ide.contractSpec.ClockDefinition;
import org.eclipse.fordiac.ide.contractSpec.ContractSpecPackage;
import org.eclipse.fordiac.ide.contractSpec.EventExpr;
import org.eclipse.fordiac.ide.contractSpec.EventList;
import org.eclipse.fordiac.ide.contractSpec.EventSpec;
import org.eclipse.fordiac.ide.contractSpec.Interval;
import org.eclipse.fordiac.ide.contractSpec.Jitter;
import org.eclipse.fordiac.ide.contractSpec.Model;
import org.eclipse.fordiac.ide.contractSpec.Offset;
import org.eclipse.fordiac.ide.contractSpec.Port;
import org.eclipse.fordiac.ide.contractSpec.Reaction;
import org.eclipse.fordiac.ide.contractSpec.Repetition;
import org.eclipse.fordiac.ide.contractSpec.RepetitionOptions;
import org.eclipse.fordiac.ide.contractSpec.SingleEvent;
import org.eclipse.fordiac.ide.contractSpec.TimeExpr;
import org.eclipse.fordiac.ide.contractSpec.Value;
import org.eclipse.fordiac.ide.services.ContractSpecGrammarAccess;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class ContractSpecSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private ContractSpecGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == ContractSpecPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case ContractSpecPackage.AGE:
				sequence_Age(context, (Age) semanticObject); 
				return; 
			case ContractSpecPackage.CAUSAL_AGE:
				sequence_CausalAge(context, (CausalAge) semanticObject); 
				return; 
			case ContractSpecPackage.CAUSAL_FUNC_DECL:
				sequence_CausalFuncDecl(context, (CausalFuncDecl) semanticObject); 
				return; 
			case ContractSpecPackage.CAUSAL_REACTION:
				sequence_CausalReaction(context, (CausalReaction) semanticObject); 
				return; 
			case ContractSpecPackage.CLOCK_DEFINITION:
				sequence_ClockDefinition(context, (ClockDefinition) semanticObject); 
				return; 
			case ContractSpecPackage.EVENT_EXPR:
				sequence_EventExpr(context, (EventExpr) semanticObject); 
				return; 
			case ContractSpecPackage.EVENT_LIST:
				sequence_EventList(context, (EventList) semanticObject); 
				return; 
			case ContractSpecPackage.EVENT_SPEC:
				sequence_EventSpec(context, (EventSpec) semanticObject); 
				return; 
			case ContractSpecPackage.INTERVAL:
				sequence_Interval(context, (Interval) semanticObject); 
				return; 
			case ContractSpecPackage.JITTER:
				sequence_Jitter(context, (Jitter) semanticObject); 
				return; 
			case ContractSpecPackage.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			case ContractSpecPackage.OFFSET:
				sequence_Offset(context, (Offset) semanticObject); 
				return; 
			case ContractSpecPackage.PORT:
				sequence_Port(context, (Port) semanticObject); 
				return; 
			case ContractSpecPackage.REACTION:
				sequence_Reaction(context, (Reaction) semanticObject); 
				return; 
			case ContractSpecPackage.REPETITION:
				sequence_Repetition(context, (Repetition) semanticObject); 
				return; 
			case ContractSpecPackage.REPETITION_OPTIONS:
				sequence_RepetitionOptions(context, (RepetitionOptions) semanticObject); 
				return; 
			case ContractSpecPackage.SINGLE_EVENT:
				sequence_SingleEvent(context, (SingleEvent) semanticObject); 
				return; 
			case ContractSpecPackage.TIME_EXPR:
				sequence_TimeExpr(context, (TimeExpr) semanticObject); 
				return; 
			case ContractSpecPackage.VALUE:
				sequence_Value(context, (Value) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     TimeSpec returns Age
	 *     Age returns Age
	 *
	 * Constraint:
	 *     (trigger=EventExpr reaction=EventExpr interval=Interval (n=INT outOf=INT)? clock=[ClockDefinition|ID]?)
	 * </pre>
	 */
	protected void sequence_Age(ISerializationContext context, Age semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     TimeSpec returns CausalAge
	 *     CausalAge returns CausalAge
	 *
	 * Constraint:
	 *     (e1=EventSpec e2=EventSpec interval=Interval clock=[ClockDefinition|ID]?)
	 * </pre>
	 */
	protected void sequence_CausalAge(ISerializationContext context, CausalAge semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     TimeSpec returns CausalFuncDecl
	 *     CausalFuncDecl returns CausalFuncDecl
	 *
	 * Constraint:
	 *     (funcName=CausalFuncName p1=[Port|ID] p2=[Port|ID] relation=CausalRelation)
	 * </pre>
	 */
	protected void sequence_CausalFuncDecl(ISerializationContext context, CausalFuncDecl semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__FUNC_NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__FUNC_NAME));
			if (transientValues.isValueTransient(semanticObject, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__P1) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__P1));
			if (transientValues.isValueTransient(semanticObject, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__P2) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__P2));
			if (transientValues.isValueTransient(semanticObject, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__RELATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__RELATION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getCausalFuncDeclAccess().getFuncNameCausalFuncNameParserRuleCall_0_0(), semanticObject.getFuncName());
		feeder.accept(grammarAccess.getCausalFuncDeclAccess().getP1PortIDTerminalRuleCall_2_0_1(), semanticObject.eGet(ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__P1, false));
		feeder.accept(grammarAccess.getCausalFuncDeclAccess().getP2PortIDTerminalRuleCall_4_0_1(), semanticObject.eGet(ContractSpecPackage.Literals.CAUSAL_FUNC_DECL__P2, false));
		feeder.accept(grammarAccess.getCausalFuncDeclAccess().getRelationCausalRelationParserRuleCall_7_0(), semanticObject.getRelation());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     TimeSpec returns CausalReaction
	 *     CausalReaction returns CausalReaction
	 *
	 * Constraint:
	 *     (e1=EventSpec e2=EventSpec interval=Interval clock=[ClockDefinition|ID]?)
	 * </pre>
	 */
	protected void sequence_CausalReaction(ISerializationContext context, CausalReaction semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     TimeSpec returns ClockDefinition
	 *     ClockDefinition returns ClockDefinition
	 *
	 * Constraint:
	 *     (name=ID resolution=TimeExpr? skew=TimeExpr? drift=Interval? maxdiff=TimeExpr?)
	 * </pre>
	 */
	protected void sequence_ClockDefinition(ISerializationContext context, ClockDefinition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     EventExpr returns EventExpr
	 *
	 * Constraint:
	 *     (event=EventSpec | events=EventList | events=EventList)
	 * </pre>
	 */
	protected void sequence_EventExpr(ISerializationContext context, EventExpr semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     EventList returns EventList
	 *
	 * Constraint:
	 *     (events+=EventSpec events+=EventSpec*)
	 * </pre>
	 */
	protected void sequence_EventList(ISerializationContext context, EventList semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     EventSpec returns EventSpec
	 *
	 * Constraint:
	 *     (port=[Port|ID] eventValue=ID?)
	 * </pre>
	 */
	protected void sequence_EventSpec(ISerializationContext context, EventSpec semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Interval returns Interval
	 *
	 * Constraint:
	 *     (time=TimeExpr | (b1=Boundary v1=Value v2=Value b2=Boundary unit=Unit))
	 * </pre>
	 */
	protected void sequence_Interval(ISerializationContext context, Interval semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Jitter returns Jitter
	 *
	 * Constraint:
	 *     time=TimeExpr
	 * </pre>
	 */
	protected void sequence_Jitter(ISerializationContext context, Jitter semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ContractSpecPackage.Literals.JITTER__TIME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ContractSpecPackage.Literals.JITTER__TIME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getJitterAccess().getTimeTimeExprParserRuleCall_1_0(), semanticObject.getTime());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     timeSpec+=TimeSpec+
	 * </pre>
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Offset returns Offset
	 *
	 * Constraint:
	 *     interval=Interval
	 * </pre>
	 */
	protected void sequence_Offset(ISerializationContext context, Offset semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ContractSpecPackage.Literals.OFFSET__INTERVAL) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ContractSpecPackage.Literals.OFFSET__INTERVAL));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getOffsetAccess().getIntervalIntervalParserRuleCall_1_0(), semanticObject.getInterval());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Port returns Port
	 *
	 * Constraint:
	 *     (name=ID isInput=INT)
	 * </pre>
	 */
	protected void sequence_Port(ISerializationContext context, Port semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ContractSpecPackage.Literals.PORT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ContractSpecPackage.Literals.PORT__NAME));
			if (transientValues.isValueTransient(semanticObject, ContractSpecPackage.Literals.PORT__IS_INPUT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ContractSpecPackage.Literals.PORT__IS_INPUT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getPortAccess().getNameIDTerminalRuleCall_0_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getPortAccess().getIsInputINTTerminalRuleCall_1_0(), semanticObject.getIsInput());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     TimeSpec returns Reaction
	 *     Reaction returns Reaction
	 *
	 * Constraint:
	 *     (trigger=EventExpr reaction=EventExpr interval=Interval (n=INT outOf=INT)? clock=[ClockDefinition|ID]?)
	 * </pre>
	 */
	protected void sequence_Reaction(ISerializationContext context, Reaction semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     RepetitionOptions returns RepetitionOptions
	 *
	 * Constraint:
	 *     ((jitter=Jitter offset=Offset?) | (offset=Offset jitter=Jitter?))
	 * </pre>
	 */
	protected void sequence_RepetitionOptions(ISerializationContext context, RepetitionOptions semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     TimeSpec returns Repetition
	 *     Repetition returns Repetition
	 *
	 * Constraint:
	 *     (events=EventList interval=Interval repetitionOptions=RepetitionOptions? clock=[ClockDefinition|ID]?)
	 * </pre>
	 */
	protected void sequence_Repetition(ISerializationContext context, Repetition semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     TimeSpec returns SingleEvent
	 *     SingleEvent returns SingleEvent
	 *
	 * Constraint:
	 *     (events=EventList interval=Interval clock=[ClockDefinition|ID]?)
	 * </pre>
	 */
	protected void sequence_SingleEvent(ISerializationContext context, SingleEvent semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     TimeExpr returns TimeExpr
	 *
	 * Constraint:
	 *     (value=Value unit=Unit)
	 * </pre>
	 */
	protected void sequence_TimeExpr(ISerializationContext context, TimeExpr semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, ContractSpecPackage.Literals.TIME_EXPR__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ContractSpecPackage.Literals.TIME_EXPR__VALUE));
			if (transientValues.isValueTransient(semanticObject, ContractSpecPackage.Literals.TIME_EXPR__UNIT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, ContractSpecPackage.Literals.TIME_EXPR__UNIT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getTimeExprAccess().getValueValueParserRuleCall_0_0(), semanticObject.getValue());
		feeder.accept(grammarAccess.getTimeExprAccess().getUnitUnitParserRuleCall_1_0(), semanticObject.getUnit());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Value returns Value
	 *
	 * Constraint:
	 *     (integer=INT fraction=INT?)
	 * </pre>
	 */
	protected void sequence_Value(ISerializationContext context, Value semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
