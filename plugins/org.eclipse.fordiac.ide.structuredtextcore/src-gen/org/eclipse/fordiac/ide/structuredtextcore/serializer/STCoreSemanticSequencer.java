/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies GmbH, 
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STPragma;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class STCoreSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private STCoreGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == STCorePackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case STCorePackage.ST_ARRAY_ACCESS_EXPRESSION:
				sequence_STAccessExpression(context, (STArrayAccessExpression) semanticObject); 
				return; 
			case STCorePackage.ST_ARRAY_INITIALIZER_EXPRESSION:
				sequence_STArrayInitializerExpression(context, (STArrayInitializerExpression) semanticObject); 
				return; 
			case STCorePackage.ST_ASSIGNMENT:
				sequence_STAssignment(context, (STAssignment) semanticObject); 
				return; 
			case STCorePackage.ST_ATTRIBUTE:
				sequence_STAttribute(context, (STAttribute) semanticObject); 
				return; 
			case STCorePackage.ST_BINARY_EXPRESSION:
				sequence_STAddSubExpression_STAndExpression_STComparisonExpression_STEqualityExpression_STMulDivModExpression_STOrExpression_STPowerExpression_STSubrangeExpression_STXorExpression(context, (STBinaryExpression) semanticObject); 
				return; 
			case STCorePackage.ST_BUILTIN_FEATURE_EXPRESSION:
				sequence_STBuiltinFeatureExpression(context, (STBuiltinFeatureExpression) semanticObject); 
				return; 
			case STCorePackage.ST_CALL_NAMED_INPUT_ARGUMENT:
				sequence_STCallNamedInputArgument(context, (STCallNamedInputArgument) semanticObject); 
				return; 
			case STCorePackage.ST_CALL_NAMED_OUTPUT_ARGUMENT:
				sequence_STCallNamedOutputArgument(context, (STCallNamedOutputArgument) semanticObject); 
				return; 
			case STCorePackage.ST_CALL_UNNAMED_ARGUMENT:
				sequence_STCallUnnamedArgument(context, (STCallUnnamedArgument) semanticObject); 
				return; 
			case STCorePackage.ST_CASE_CASES:
				sequence_STCaseCases(context, (STCaseCases) semanticObject); 
				return; 
			case STCorePackage.ST_CASE_STATEMENT:
				sequence_STCaseStatement(context, (STCaseStatement) semanticObject); 
				return; 
			case STCorePackage.ST_CONTINUE:
				sequence_STStatement(context, (STContinue) semanticObject); 
				return; 
			case STCorePackage.ST_CORE_SOURCE:
				sequence_STCoreSource(context, (STCoreSource) semanticObject); 
				return; 
			case STCorePackage.ST_DATE_AND_TIME_LITERAL:
				sequence_STDateAndTimeLiteral(context, (STDateAndTimeLiteral) semanticObject); 
				return; 
			case STCorePackage.ST_DATE_LITERAL:
				sequence_STDateLiteral(context, (STDateLiteral) semanticObject); 
				return; 
			case STCorePackage.ST_ELEMENTARY_INITIALIZER_EXPRESSION:
				sequence_STElementaryInitializerExpression(context, (STElementaryInitializerExpression) semanticObject); 
				return; 
			case STCorePackage.ST_ELSE_IF_PART:
				sequence_STElseIfPart(context, (STElseIfPart) semanticObject); 
				return; 
			case STCorePackage.ST_ELSE_PART:
				sequence_STElsePart(context, (STElsePart) semanticObject); 
				return; 
			case STCorePackage.ST_ENUM_LITERAL:
				sequence_STEnumLiteral(context, (STEnumLiteral) semanticObject); 
				return; 
			case STCorePackage.ST_EXIT:
				sequence_STStatement(context, (STExit) semanticObject); 
				return; 
			case STCorePackage.ST_EXPRESSION_SOURCE:
				sequence_STExpressionSource(context, (STExpressionSource) semanticObject); 
				return; 
			case STCorePackage.ST_FEATURE_EXPRESSION:
				sequence_STFeatureExpression(context, (STFeatureExpression) semanticObject); 
				return; 
			case STCorePackage.ST_FOR_STATEMENT:
				sequence_STForStatement(context, (STForStatement) semanticObject); 
				return; 
			case STCorePackage.ST_IF_STATEMENT:
				sequence_STIfStatement(context, (STIfStatement) semanticObject); 
				return; 
			case STCorePackage.ST_IMPORT:
				sequence_STImport(context, (STImport) semanticObject); 
				return; 
			case STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE:
				sequence_STInitializerExpressionSource(context, (STInitializerExpressionSource) semanticObject); 
				return; 
			case STCorePackage.ST_MEMBER_ACCESS_EXPRESSION:
				sequence_STAccessExpression(context, (STMemberAccessExpression) semanticObject); 
				return; 
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION:
				sequence_STMultibitPartialExpression(context, (STMultibitPartialExpression) semanticObject); 
				return; 
			case STCorePackage.ST_NOP:
				sequence_STStatement(context, (STNop) semanticObject); 
				return; 
			case STCorePackage.ST_NUMERIC_LITERAL:
				if (rule == grammarAccess.getSTLiteralExpressionsRule()
						|| rule == grammarAccess.getSTNumericLiteralRule()) {
					sequence_STNumericLiteral(context, (STNumericLiteral) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getSTStatementRule()
						|| rule == grammarAccess.getSTAssignmentRule()
						|| action == grammarAccess.getSTAssignmentAccess().getSTAssignmentLeftAction_1_0()
						|| rule == grammarAccess.getSTExpressionRule()
						|| rule == grammarAccess.getSTSubrangeExpressionRule()
						|| action == grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()
						|| rule == grammarAccess.getSTOrExpressionRule()
						|| action == grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()
						|| rule == grammarAccess.getSTXorExpressionRule()
						|| action == grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()
						|| rule == grammarAccess.getSTAndExpressionRule()
						|| action == grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()
						|| rule == grammarAccess.getSTEqualityExpressionRule()
						|| action == grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()
						|| rule == grammarAccess.getSTComparisonExpressionRule()
						|| action == grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()
						|| rule == grammarAccess.getSTAddSubExpressionRule()
						|| action == grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()
						|| rule == grammarAccess.getSTMulDivModExpressionRule()
						|| action == grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()
						|| rule == grammarAccess.getSTPowerExpressionRule()
						|| action == grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()
						|| rule == grammarAccess.getSTUnaryExpressionRule()
						|| rule == grammarAccess.getSTAccessExpressionRule()
						|| action == grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0()
						|| action == grammarAccess.getSTAccessExpressionAccess().getSTArrayAccessExpressionReceiverAction_1_1_0()
						|| rule == grammarAccess.getSTPrimaryExpressionRule()) {
					sequence_STNumericLiteral_STSignedNumericLiteral(context, (STNumericLiteral) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getSTSignedLiteralExpressionsRule()
						|| rule == grammarAccess.getSTSignedNumericLiteralRule()) {
					sequence_STSignedNumericLiteral(context, (STNumericLiteral) semanticObject); 
					return; 
				}
				else break;
			case STCorePackage.ST_PRAGMA:
				sequence_STPragma(context, (STPragma) semanticObject); 
				return; 
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT:
				sequence_STRepeatArrayInitElement(context, (STRepeatArrayInitElement) semanticObject); 
				return; 
			case STCorePackage.ST_REPEAT_STATEMENT:
				sequence_STRepeatStatement(context, (STRepeatStatement) semanticObject); 
				return; 
			case STCorePackage.ST_RETURN:
				sequence_STStatement(context, (STReturn) semanticObject); 
				return; 
			case STCorePackage.ST_SINGLE_ARRAY_INIT_ELEMENT:
				sequence_STSingleArrayInitElement(context, (STSingleArrayInitElement) semanticObject); 
				return; 
			case STCorePackage.ST_STRING_LITERAL:
				sequence_STStringLiteral(context, (STStringLiteral) semanticObject); 
				return; 
			case STCorePackage.ST_STRUCT_INIT_ELEMENT:
				sequence_STStructInitElement(context, (STStructInitElement) semanticObject); 
				return; 
			case STCorePackage.ST_STRUCT_INITIALIZER_EXPRESSION:
				sequence_STStructInitializerExpression(context, (STStructInitializerExpression) semanticObject); 
				return; 
			case STCorePackage.ST_TIME_LITERAL:
				sequence_STTimeLiteral(context, (STTimeLiteral) semanticObject); 
				return; 
			case STCorePackage.ST_TIME_OF_DAY_LITERAL:
				sequence_STTimeOfDayLiteral(context, (STTimeOfDayLiteral) semanticObject); 
				return; 
			case STCorePackage.ST_TYPE_DECLARATION:
				sequence_STTypeDeclaration(context, (STTypeDeclaration) semanticObject); 
				return; 
			case STCorePackage.ST_UNARY_EXPRESSION:
				sequence_STUnaryExpression(context, (STUnaryExpression) semanticObject); 
				return; 
			case STCorePackage.ST_VAR_DECLARATION:
				sequence_STVarDeclaration(context, (STVarDeclaration) semanticObject); 
				return; 
			case STCorePackage.ST_VAR_IN_OUT_DECLARATION_BLOCK:
				sequence_STVarInOutDeclarationBlock(context, (STVarInOutDeclarationBlock) semanticObject); 
				return; 
			case STCorePackage.ST_VAR_INPUT_DECLARATION_BLOCK:
				sequence_STVarInputDeclarationBlock(context, (STVarInputDeclarationBlock) semanticObject); 
				return; 
			case STCorePackage.ST_VAR_OUTPUT_DECLARATION_BLOCK:
				sequence_STVarOutputDeclarationBlock(context, (STVarOutputDeclarationBlock) semanticObject); 
				return; 
			case STCorePackage.ST_VAR_PLAIN_DECLARATION_BLOCK:
				sequence_STVarDeclarationBlock(context, (STVarPlainDeclarationBlock) semanticObject); 
				return; 
			case STCorePackage.ST_VAR_TEMP_DECLARATION_BLOCK:
				sequence_STVarTempDeclarationBlock(context, (STVarTempDeclarationBlock) semanticObject); 
				return; 
			case STCorePackage.ST_WHILE_STATEMENT:
				sequence_STWhileStatement(context, (STWhileStatement) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STArrayAccessExpression
	 *     STAssignment returns STArrayAccessExpression
	 *     STAssignment.STAssignment_1_0 returns STArrayAccessExpression
	 *     STExpression returns STArrayAccessExpression
	 *     STSubrangeExpression returns STArrayAccessExpression
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STArrayAccessExpression
	 *     STOrExpression returns STArrayAccessExpression
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STArrayAccessExpression
	 *     STXorExpression returns STArrayAccessExpression
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STArrayAccessExpression
	 *     STAndExpression returns STArrayAccessExpression
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STArrayAccessExpression
	 *     STEqualityExpression returns STArrayAccessExpression
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STArrayAccessExpression
	 *     STComparisonExpression returns STArrayAccessExpression
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STArrayAccessExpression
	 *     STAddSubExpression returns STArrayAccessExpression
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STArrayAccessExpression
	 *     STMulDivModExpression returns STArrayAccessExpression
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STArrayAccessExpression
	 *     STPowerExpression returns STArrayAccessExpression
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STArrayAccessExpression
	 *     STUnaryExpression returns STArrayAccessExpression
	 *     STAccessExpression returns STArrayAccessExpression
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STArrayAccessExpression
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STArrayAccessExpression
	 *     STPrimaryExpression returns STArrayAccessExpression
	 *
	 * Constraint:
	 *     (receiver=STAccessExpression_STArrayAccessExpression_1_1_0 index+=STExpression index+=STExpression*)
	 * </pre>
	 */
	protected void sequence_STAccessExpression(ISerializationContext context, STArrayAccessExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STMemberAccessExpression
	 *     STAssignment returns STMemberAccessExpression
	 *     STAssignment.STAssignment_1_0 returns STMemberAccessExpression
	 *     STExpression returns STMemberAccessExpression
	 *     STSubrangeExpression returns STMemberAccessExpression
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STMemberAccessExpression
	 *     STOrExpression returns STMemberAccessExpression
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STMemberAccessExpression
	 *     STXorExpression returns STMemberAccessExpression
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STMemberAccessExpression
	 *     STAndExpression returns STMemberAccessExpression
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STMemberAccessExpression
	 *     STEqualityExpression returns STMemberAccessExpression
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STMemberAccessExpression
	 *     STComparisonExpression returns STMemberAccessExpression
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STMemberAccessExpression
	 *     STAddSubExpression returns STMemberAccessExpression
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STMemberAccessExpression
	 *     STMulDivModExpression returns STMemberAccessExpression
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STMemberAccessExpression
	 *     STPowerExpression returns STMemberAccessExpression
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STMemberAccessExpression
	 *     STUnaryExpression returns STMemberAccessExpression
	 *     STAccessExpression returns STMemberAccessExpression
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STMemberAccessExpression
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STMemberAccessExpression
	 *     STPrimaryExpression returns STMemberAccessExpression
	 *
	 * Constraint:
	 *     (receiver=STAccessExpression_STMemberAccessExpression_1_0_0 (member=STFeatureExpression | member=STMultibitPartialExpression))
	 * </pre>
	 */
	protected void sequence_STAccessExpression(ISerializationContext context, STMemberAccessExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STBinaryExpression
	 *     STAssignment returns STBinaryExpression
	 *     STAssignment.STAssignment_1_0 returns STBinaryExpression
	 *     STExpression returns STBinaryExpression
	 *     STSubrangeExpression returns STBinaryExpression
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STBinaryExpression
	 *     STOrExpression returns STBinaryExpression
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STBinaryExpression
	 *     STXorExpression returns STBinaryExpression
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STBinaryExpression
	 *     STAndExpression returns STBinaryExpression
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STBinaryExpression
	 *     STEqualityExpression returns STBinaryExpression
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STBinaryExpression
	 *     STComparisonExpression returns STBinaryExpression
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STBinaryExpression
	 *     STAddSubExpression returns STBinaryExpression
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STBinaryExpression
	 *     STMulDivModExpression returns STBinaryExpression
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STBinaryExpression
	 *     STPowerExpression returns STBinaryExpression
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STBinaryExpression
	 *     STUnaryExpression returns STBinaryExpression
	 *     STAccessExpression returns STBinaryExpression
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STBinaryExpression
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STBinaryExpression
	 *     STPrimaryExpression returns STBinaryExpression
	 *
	 * Constraint:
	 *     (
	 *         (left=STSubrangeExpression_STBinaryExpression_1_0_0 op=SubrangeOperator right=STOrExpression) | 
	 *         (left=STOrExpression_STBinaryExpression_1_0_0 op=OrOperator right=STXorExpression) | 
	 *         (left=STXorExpression_STBinaryExpression_1_0_0 op=XorOperator right=STAndExpression) | 
	 *         (left=STAndExpression_STBinaryExpression_1_0_0 op=AndOperator right=STEqualityExpression) | 
	 *         (left=STEqualityExpression_STBinaryExpression_1_0_0 op=EqualityOperator right=STComparisonExpression) | 
	 *         (left=STComparisonExpression_STBinaryExpression_1_0_0 op=CompareOperator right=STAddSubExpression) | 
	 *         (left=STAddSubExpression_STBinaryExpression_1_0_0 op=AddSubOperator right=STMulDivModExpression) | 
	 *         (left=STMulDivModExpression_STBinaryExpression_1_0_0 op=MulDivModOperator right=STPowerExpression) | 
	 *         (left=STPowerExpression_STBinaryExpression_1_0_0 op=PowerOperator right=STUnaryExpression)
	 *     )
	 * </pre>
	 */
	protected void sequence_STAddSubExpression_STAndExpression_STComparisonExpression_STEqualityExpression_STMulDivModExpression_STOrExpression_STPowerExpression_STSubrangeExpression_STXorExpression(ISerializationContext context, STBinaryExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STInitializerExpression returns STArrayInitializerExpression
	 *     STArrayInitializerExpression returns STArrayInitializerExpression
	 *
	 * Constraint:
	 *     (values+=STArrayInitElement values+=STArrayInitElement*)
	 * </pre>
	 */
	protected void sequence_STArrayInitializerExpression(ISerializationContext context, STArrayInitializerExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STAssignment
	 *     STAssignment returns STAssignment
	 *
	 * Constraint:
	 *     (left=STAssignment_STAssignment_1_0 right=STAssignment)
	 * </pre>
	 */
	protected void sequence_STAssignment(ISerializationContext context, STAssignment semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_ASSIGNMENT__LEFT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_ASSIGNMENT__LEFT));
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_ASSIGNMENT__RIGHT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_ASSIGNMENT__RIGHT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTAssignmentAccess().getSTAssignmentLeftAction_1_0(), semanticObject.getLeft());
		feeder.accept(grammarAccess.getSTAssignmentAccess().getRightSTAssignmentParserRuleCall_1_2_0(), semanticObject.getRight());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STAttribute returns STAttribute
	 *
	 * Constraint:
	 *     (declaration=[AttributeDeclaration|STAttributeName] value=STInitializerExpression)
	 * </pre>
	 */
	protected void sequence_STAttribute(ISerializationContext context, STAttribute semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_ATTRIBUTE__DECLARATION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_ATTRIBUTE__DECLARATION));
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_ATTRIBUTE__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_ATTRIBUTE__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTAttributeAccess().getDeclarationAttributeDeclarationSTAttributeNameParserRuleCall_0_0_1(), semanticObject.eGet(STCorePackage.Literals.ST_ATTRIBUTE__DECLARATION, false));
		feeder.accept(grammarAccess.getSTAttributeAccess().getValueSTInitializerExpressionParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STBuiltinFeatureExpression
	 *     STAssignment returns STBuiltinFeatureExpression
	 *     STAssignment.STAssignment_1_0 returns STBuiltinFeatureExpression
	 *     STExpression returns STBuiltinFeatureExpression
	 *     STSubrangeExpression returns STBuiltinFeatureExpression
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STBuiltinFeatureExpression
	 *     STOrExpression returns STBuiltinFeatureExpression
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STBuiltinFeatureExpression
	 *     STXorExpression returns STBuiltinFeatureExpression
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STBuiltinFeatureExpression
	 *     STAndExpression returns STBuiltinFeatureExpression
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STBuiltinFeatureExpression
	 *     STEqualityExpression returns STBuiltinFeatureExpression
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STBuiltinFeatureExpression
	 *     STComparisonExpression returns STBuiltinFeatureExpression
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STBuiltinFeatureExpression
	 *     STAddSubExpression returns STBuiltinFeatureExpression
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STBuiltinFeatureExpression
	 *     STMulDivModExpression returns STBuiltinFeatureExpression
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STBuiltinFeatureExpression
	 *     STPowerExpression returns STBuiltinFeatureExpression
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STBuiltinFeatureExpression
	 *     STUnaryExpression returns STBuiltinFeatureExpression
	 *     STAccessExpression returns STBuiltinFeatureExpression
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STBuiltinFeatureExpression
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STBuiltinFeatureExpression
	 *     STPrimaryExpression returns STBuiltinFeatureExpression
	 *     STBuiltinFeatureExpression returns STBuiltinFeatureExpression
	 *
	 * Constraint:
	 *     (feature=STBuiltinFeature (call?='(' (parameters+=STCallArgument parameters+=STCallArgument*)?)?)
	 * </pre>
	 */
	protected void sequence_STBuiltinFeatureExpression(ISerializationContext context, STBuiltinFeatureExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STCallArgument returns STCallNamedInputArgument
	 *     STCallNamedInputArgument returns STCallNamedInputArgument
	 *
	 * Constraint:
	 *     (parameter=[INamedElement|ID] argument=STExpression)
	 * </pre>
	 */
	protected void sequence_STCallNamedInputArgument(ISerializationContext context, STCallNamedInputArgument semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_CALL_NAMED_INPUT_ARGUMENT__PARAMETER) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_CALL_NAMED_INPUT_ARGUMENT__PARAMETER));
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_CALL_ARGUMENT__ARGUMENT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_CALL_ARGUMENT__ARGUMENT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTCallNamedInputArgumentAccess().getParameterINamedElementIDTerminalRuleCall_0_0_1(), semanticObject.eGet(STCorePackage.Literals.ST_CALL_NAMED_INPUT_ARGUMENT__PARAMETER, false));
		feeder.accept(grammarAccess.getSTCallNamedInputArgumentAccess().getArgumentSTExpressionParserRuleCall_2_0(), semanticObject.getArgument());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STCallArgument returns STCallNamedOutputArgument
	 *     STCallNamedOutputArgument returns STCallNamedOutputArgument
	 *
	 * Constraint:
	 *     (not?='NOT'? parameter=[INamedElement|ID] argument=STExpression)
	 * </pre>
	 */
	protected void sequence_STCallNamedOutputArgument(ISerializationContext context, STCallNamedOutputArgument semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STCallArgument returns STCallUnnamedArgument
	 *     STCallUnnamedArgument returns STCallUnnamedArgument
	 *
	 * Constraint:
	 *     argument=STExpression
	 * </pre>
	 */
	protected void sequence_STCallUnnamedArgument(ISerializationContext context, STCallUnnamedArgument semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_CALL_ARGUMENT__ARGUMENT) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_CALL_ARGUMENT__ARGUMENT));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTCallUnnamedArgumentAccess().getArgumentSTExpressionParserRuleCall_0(), semanticObject.getArgument());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STCaseCases returns STCaseCases
	 *
	 * Constraint:
	 *     (conditions+=STExpression conditions+=STExpression* statements+=STStatement*)
	 * </pre>
	 */
	protected void sequence_STCaseCases(ISerializationContext context, STCaseCases semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STCaseStatement
	 *     STCaseStatement returns STCaseStatement
	 *
	 * Constraint:
	 *     (selector=STExpression cases+=STCaseCases+ else=STElsePart?)
	 * </pre>
	 */
	protected void sequence_STCaseStatement(ISerializationContext context, STCaseStatement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STCoreSource returns STCoreSource
	 *
	 * Constraint:
	 *     statements+=STStatement*
	 * </pre>
	 */
	protected void sequence_STCoreSource(ISerializationContext context, STCoreSource semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STDateAndTimeLiteral
	 *     STAssignment returns STDateAndTimeLiteral
	 *     STAssignment.STAssignment_1_0 returns STDateAndTimeLiteral
	 *     STExpression returns STDateAndTimeLiteral
	 *     STSubrangeExpression returns STDateAndTimeLiteral
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STDateAndTimeLiteral
	 *     STOrExpression returns STDateAndTimeLiteral
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STDateAndTimeLiteral
	 *     STXorExpression returns STDateAndTimeLiteral
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STDateAndTimeLiteral
	 *     STAndExpression returns STDateAndTimeLiteral
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STDateAndTimeLiteral
	 *     STEqualityExpression returns STDateAndTimeLiteral
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STDateAndTimeLiteral
	 *     STComparisonExpression returns STDateAndTimeLiteral
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STDateAndTimeLiteral
	 *     STAddSubExpression returns STDateAndTimeLiteral
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STDateAndTimeLiteral
	 *     STMulDivModExpression returns STDateAndTimeLiteral
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STDateAndTimeLiteral
	 *     STPowerExpression returns STDateAndTimeLiteral
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STDateAndTimeLiteral
	 *     STUnaryExpression returns STDateAndTimeLiteral
	 *     STAccessExpression returns STDateAndTimeLiteral
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STDateAndTimeLiteral
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STDateAndTimeLiteral
	 *     STPrimaryExpression returns STDateAndTimeLiteral
	 *     STLiteralExpressions returns STDateAndTimeLiteral
	 *     STDateAndTimeLiteral returns STDateAndTimeLiteral
	 *
	 * Constraint:
	 *     (type=[DataType|STDateAndTimeType] value=DateAndTime)
	 * </pre>
	 */
	protected void sequence_STDateAndTimeLiteral(ISerializationContext context, STDateAndTimeLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_DATE_AND_TIME_LITERAL__TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_DATE_AND_TIME_LITERAL__TYPE));
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_DATE_AND_TIME_LITERAL__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_DATE_AND_TIME_LITERAL__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeSTDateAndTimeTypeParserRuleCall_0_0_1(), semanticObject.eGet(STCorePackage.Literals.ST_DATE_AND_TIME_LITERAL__TYPE, false));
		feeder.accept(grammarAccess.getSTDateAndTimeLiteralAccess().getValueDateAndTimeParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STDateLiteral
	 *     STAssignment returns STDateLiteral
	 *     STAssignment.STAssignment_1_0 returns STDateLiteral
	 *     STExpression returns STDateLiteral
	 *     STSubrangeExpression returns STDateLiteral
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STDateLiteral
	 *     STOrExpression returns STDateLiteral
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STDateLiteral
	 *     STXorExpression returns STDateLiteral
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STDateLiteral
	 *     STAndExpression returns STDateLiteral
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STDateLiteral
	 *     STEqualityExpression returns STDateLiteral
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STDateLiteral
	 *     STComparisonExpression returns STDateLiteral
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STDateLiteral
	 *     STAddSubExpression returns STDateLiteral
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STDateLiteral
	 *     STMulDivModExpression returns STDateLiteral
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STDateLiteral
	 *     STPowerExpression returns STDateLiteral
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STDateLiteral
	 *     STUnaryExpression returns STDateLiteral
	 *     STAccessExpression returns STDateLiteral
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STDateLiteral
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STDateLiteral
	 *     STPrimaryExpression returns STDateLiteral
	 *     STLiteralExpressions returns STDateLiteral
	 *     STDateLiteral returns STDateLiteral
	 *
	 * Constraint:
	 *     (type=[DataType|STDateLiteralType] value=Date)
	 * </pre>
	 */
	protected void sequence_STDateLiteral(ISerializationContext context, STDateLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_DATE_LITERAL__TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_DATE_LITERAL__TYPE));
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_DATE_LITERAL__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_DATE_LITERAL__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeSTDateLiteralTypeParserRuleCall_0_0_1(), semanticObject.eGet(STCorePackage.Literals.ST_DATE_LITERAL__TYPE, false));
		feeder.accept(grammarAccess.getSTDateLiteralAccess().getValueDateParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STInitializerExpression returns STElementaryInitializerExpression
	 *     STElementaryInitializerExpression returns STElementaryInitializerExpression
	 *
	 * Constraint:
	 *     value=STExpression
	 * </pre>
	 */
	protected void sequence_STElementaryInitializerExpression(ISerializationContext context, STElementaryInitializerExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_ELEMENTARY_INITIALIZER_EXPRESSION__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_ELEMENTARY_INITIALIZER_EXPRESSION__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTElementaryInitializerExpressionAccess().getValueSTExpressionParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STElseIfPart returns STElseIfPart
	 *
	 * Constraint:
	 *     (condition=STExpression statements+=STStatement*)
	 * </pre>
	 */
	protected void sequence_STElseIfPart(ISerializationContext context, STElseIfPart semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STElsePart returns STElsePart
	 *
	 * Constraint:
	 *     statements+=STStatement*
	 * </pre>
	 */
	protected void sequence_STElsePart(ISerializationContext context, STElsePart semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STEnumLiteral
	 *     STAssignment returns STEnumLiteral
	 *     STAssignment.STAssignment_1_0 returns STEnumLiteral
	 *     STExpression returns STEnumLiteral
	 *     STSubrangeExpression returns STEnumLiteral
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STEnumLiteral
	 *     STOrExpression returns STEnumLiteral
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STEnumLiteral
	 *     STXorExpression returns STEnumLiteral
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STEnumLiteral
	 *     STAndExpression returns STEnumLiteral
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STEnumLiteral
	 *     STEqualityExpression returns STEnumLiteral
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STEnumLiteral
	 *     STComparisonExpression returns STEnumLiteral
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STEnumLiteral
	 *     STAddSubExpression returns STEnumLiteral
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STEnumLiteral
	 *     STMulDivModExpression returns STEnumLiteral
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STEnumLiteral
	 *     STPowerExpression returns STEnumLiteral
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STEnumLiteral
	 *     STUnaryExpression returns STEnumLiteral
	 *     STAccessExpression returns STEnumLiteral
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STEnumLiteral
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STEnumLiteral
	 *     STPrimaryExpression returns STEnumLiteral
	 *     STLiteralExpressions returns STEnumLiteral
	 *     STEnumLiteral returns STEnumLiteral
	 *
	 * Constraint:
	 *     value=[EnumeratedValue|EnumValue]
	 * </pre>
	 */
	protected void sequence_STEnumLiteral(ISerializationContext context, STEnumLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_ENUM_LITERAL__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_ENUM_LITERAL__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTEnumLiteralAccess().getValueEnumeratedValueEnumValueParserRuleCall_0_1(), semanticObject.eGet(STCorePackage.Literals.ST_ENUM_LITERAL__VALUE, false));
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STExpressionSource returns STExpressionSource
	 *     STExpressionSource0 returns STExpressionSource
	 *
	 * Constraint:
	 *     expression=STExpression?
	 * </pre>
	 */
	protected void sequence_STExpressionSource(ISerializationContext context, STExpressionSource semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STFeatureExpression
	 *     STAssignment returns STFeatureExpression
	 *     STAssignment.STAssignment_1_0 returns STFeatureExpression
	 *     STExpression returns STFeatureExpression
	 *     STSubrangeExpression returns STFeatureExpression
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STFeatureExpression
	 *     STOrExpression returns STFeatureExpression
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STFeatureExpression
	 *     STXorExpression returns STFeatureExpression
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STFeatureExpression
	 *     STAndExpression returns STFeatureExpression
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STFeatureExpression
	 *     STEqualityExpression returns STFeatureExpression
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STFeatureExpression
	 *     STComparisonExpression returns STFeatureExpression
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STFeatureExpression
	 *     STAddSubExpression returns STFeatureExpression
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STFeatureExpression
	 *     STMulDivModExpression returns STFeatureExpression
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STFeatureExpression
	 *     STPowerExpression returns STFeatureExpression
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STFeatureExpression
	 *     STUnaryExpression returns STFeatureExpression
	 *     STAccessExpression returns STFeatureExpression
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STFeatureExpression
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STFeatureExpression
	 *     STPrimaryExpression returns STFeatureExpression
	 *     STFeatureExpression returns STFeatureExpression
	 *
	 * Constraint:
	 *     (feature=[INamedElement|STFeatureName] (call?='(' (parameters+=STCallArgument parameters+=STCallArgument*)?)?)
	 * </pre>
	 */
	protected void sequence_STFeatureExpression(ISerializationContext context, STFeatureExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STForStatement
	 *     STForStatement returns STForStatement
	 *
	 * Constraint:
	 *     (variable=STExpression from=STExpression to=STExpression by=STExpression? statements+=STStatement*)
	 * </pre>
	 */
	protected void sequence_STForStatement(ISerializationContext context, STForStatement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STIfStatement
	 *     STIfStatement returns STIfStatement
	 *
	 * Constraint:
	 *     (condition=STExpression statements+=STStatement* elseifs+=STElseIfPart* else=STElsePart?)
	 * </pre>
	 */
	protected void sequence_STIfStatement(ISerializationContext context, STIfStatement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STImport returns STImport
	 *
	 * Constraint:
	 *     importedNamespace=QualifiedNameWithWildcard
	 * </pre>
	 */
	protected void sequence_STImport(ISerializationContext context, STImport semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, LibraryElementPackage.Literals.IMPORT__IMPORTED_NAMESPACE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, LibraryElementPackage.Literals.IMPORT__IMPORTED_NAMESPACE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTImportAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0(), semanticObject.getImportedNamespace());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STInitializerExpressionSource returns STInitializerExpressionSource
	 *     STInitializerExpressionSource0 returns STInitializerExpressionSource
	 *
	 * Constraint:
	 *     initializerExpression=STInitializerExpression?
	 * </pre>
	 */
	protected void sequence_STInitializerExpressionSource(ISerializationContext context, STInitializerExpressionSource semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STMultibitPartialExpression returns STMultibitPartialExpression
	 *
	 * Constraint:
	 *     (specifier=STMultiBitAccessSpecifier? (index=INT | expression=STExpression))
	 * </pre>
	 */
	protected void sequence_STMultibitPartialExpression(ISerializationContext context, STMultibitPartialExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STLiteralExpressions returns STNumericLiteral
	 *     STNumericLiteral returns STNumericLiteral
	 *
	 * Constraint:
	 *     ((type=[DataType|STNumericLiteralType] value=SignedNumeric) | (type=[DataType|STNumericLiteralType]? value=Numeric))
	 * </pre>
	 */
	protected void sequence_STNumericLiteral(ISerializationContext context, STNumericLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STNumericLiteral
	 *     STAssignment returns STNumericLiteral
	 *     STAssignment.STAssignment_1_0 returns STNumericLiteral
	 *     STExpression returns STNumericLiteral
	 *     STSubrangeExpression returns STNumericLiteral
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STNumericLiteral
	 *     STOrExpression returns STNumericLiteral
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STNumericLiteral
	 *     STXorExpression returns STNumericLiteral
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STNumericLiteral
	 *     STAndExpression returns STNumericLiteral
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STNumericLiteral
	 *     STEqualityExpression returns STNumericLiteral
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STNumericLiteral
	 *     STComparisonExpression returns STNumericLiteral
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STNumericLiteral
	 *     STAddSubExpression returns STNumericLiteral
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STNumericLiteral
	 *     STMulDivModExpression returns STNumericLiteral
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STNumericLiteral
	 *     STPowerExpression returns STNumericLiteral
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STNumericLiteral
	 *     STUnaryExpression returns STNumericLiteral
	 *     STAccessExpression returns STNumericLiteral
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STNumericLiteral
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STNumericLiteral
	 *     STPrimaryExpression returns STNumericLiteral
	 *
	 * Constraint:
	 *     ((type=[DataType|STNumericLiteralType] value=SignedNumeric) | (type=[DataType|STNumericLiteralType]? value=Numeric) | value=SignedNumeric)
	 * </pre>
	 */
	protected void sequence_STNumericLiteral_STSignedNumericLiteral(ISerializationContext context, STNumericLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STPragma returns STPragma
	 *
	 * Constraint:
	 *     (attributes+=STAttribute attributes+=STAttribute*)
	 * </pre>
	 */
	protected void sequence_STPragma(ISerializationContext context, STPragma semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STArrayInitElement returns STRepeatArrayInitElement
	 *     STRepeatArrayInitElement returns STRepeatArrayInitElement
	 *
	 * Constraint:
	 *     (repetitions=INT initExpressions+=STInitializerExpression initExpressions+=STInitializerExpression*)
	 * </pre>
	 */
	protected void sequence_STRepeatArrayInitElement(ISerializationContext context, STRepeatArrayInitElement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STRepeatStatement
	 *     STRepeatStatement returns STRepeatStatement
	 *
	 * Constraint:
	 *     (statements+=STStatement* condition=STExpression)
	 * </pre>
	 */
	protected void sequence_STRepeatStatement(ISerializationContext context, STRepeatStatement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STSignedLiteralExpressions returns STNumericLiteral
	 *     STSignedNumericLiteral returns STNumericLiteral
	 *
	 * Constraint:
	 *     value=SignedNumeric
	 * </pre>
	 */
	protected void sequence_STSignedNumericLiteral(ISerializationContext context, STNumericLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_NUMERIC_LITERAL__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_NUMERIC_LITERAL__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTSignedNumericLiteralAccess().getValueSignedNumericParserRuleCall_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STArrayInitElement returns STSingleArrayInitElement
	 *     STSingleArrayInitElement returns STSingleArrayInitElement
	 *
	 * Constraint:
	 *     initExpression=STInitializerExpression
	 * </pre>
	 */
	protected void sequence_STSingleArrayInitElement(ISerializationContext context, STSingleArrayInitElement semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_SINGLE_ARRAY_INIT_ELEMENT__INIT_EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_SINGLE_ARRAY_INIT_ELEMENT__INIT_EXPRESSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTSingleArrayInitElementAccess().getInitExpressionSTInitializerExpressionParserRuleCall_0(), semanticObject.getInitExpression());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STContinue
	 *
	 * Constraint:
	 *     {STContinue}
	 * </pre>
	 */
	protected void sequence_STStatement(ISerializationContext context, STContinue semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STExit
	 *
	 * Constraint:
	 *     {STExit}
	 * </pre>
	 */
	protected void sequence_STStatement(ISerializationContext context, STExit semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STNop
	 *
	 * Constraint:
	 *     {STNop}
	 * </pre>
	 */
	protected void sequence_STStatement(ISerializationContext context, STNop semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STReturn
	 *
	 * Constraint:
	 *     {STReturn}
	 * </pre>
	 */
	protected void sequence_STStatement(ISerializationContext context, STReturn semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STStringLiteral
	 *     STAssignment returns STStringLiteral
	 *     STAssignment.STAssignment_1_0 returns STStringLiteral
	 *     STExpression returns STStringLiteral
	 *     STSubrangeExpression returns STStringLiteral
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STStringLiteral
	 *     STOrExpression returns STStringLiteral
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STStringLiteral
	 *     STXorExpression returns STStringLiteral
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STStringLiteral
	 *     STAndExpression returns STStringLiteral
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STStringLiteral
	 *     STEqualityExpression returns STStringLiteral
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STStringLiteral
	 *     STComparisonExpression returns STStringLiteral
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STStringLiteral
	 *     STAddSubExpression returns STStringLiteral
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STStringLiteral
	 *     STMulDivModExpression returns STStringLiteral
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STStringLiteral
	 *     STPowerExpression returns STStringLiteral
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STStringLiteral
	 *     STUnaryExpression returns STStringLiteral
	 *     STAccessExpression returns STStringLiteral
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STStringLiteral
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STStringLiteral
	 *     STPrimaryExpression returns STStringLiteral
	 *     STLiteralExpressions returns STStringLiteral
	 *     STStringLiteral returns STStringLiteral
	 *
	 * Constraint:
	 *     (type=[DataType|STAnyCharsType]? value=STRING)
	 * </pre>
	 */
	protected void sequence_STStringLiteral(ISerializationContext context, STStringLiteral semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStructInitElement returns STStructInitElement
	 *
	 * Constraint:
	 *     (variable=[INamedElement|STFeatureName] value=STInitializerExpression)
	 * </pre>
	 */
	protected void sequence_STStructInitElement(ISerializationContext context, STStructInitElement semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_STRUCT_INIT_ELEMENT__VARIABLE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_STRUCT_INIT_ELEMENT__VARIABLE));
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_STRUCT_INIT_ELEMENT__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_STRUCT_INIT_ELEMENT__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTStructInitElementAccess().getVariableINamedElementSTFeatureNameParserRuleCall_0_0_1(), semanticObject.eGet(STCorePackage.Literals.ST_STRUCT_INIT_ELEMENT__VARIABLE, false));
		feeder.accept(grammarAccess.getSTStructInitElementAccess().getValueSTInitializerExpressionParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STInitializerExpression returns STStructInitializerExpression
	 *     STStructInitializerExpression returns STStructInitializerExpression
	 *
	 * Constraint:
	 *     (type=[StructuredType|QualifiedName]? values+=STStructInitElement values+=STStructInitElement*)
	 * </pre>
	 */
	protected void sequence_STStructInitializerExpression(ISerializationContext context, STStructInitializerExpression semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STTimeLiteral
	 *     STAssignment returns STTimeLiteral
	 *     STAssignment.STAssignment_1_0 returns STTimeLiteral
	 *     STExpression returns STTimeLiteral
	 *     STSubrangeExpression returns STTimeLiteral
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STTimeLiteral
	 *     STOrExpression returns STTimeLiteral
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STTimeLiteral
	 *     STXorExpression returns STTimeLiteral
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STTimeLiteral
	 *     STAndExpression returns STTimeLiteral
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STTimeLiteral
	 *     STEqualityExpression returns STTimeLiteral
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STTimeLiteral
	 *     STComparisonExpression returns STTimeLiteral
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STTimeLiteral
	 *     STAddSubExpression returns STTimeLiteral
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STTimeLiteral
	 *     STMulDivModExpression returns STTimeLiteral
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STTimeLiteral
	 *     STPowerExpression returns STTimeLiteral
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STTimeLiteral
	 *     STUnaryExpression returns STTimeLiteral
	 *     STAccessExpression returns STTimeLiteral
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STTimeLiteral
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STTimeLiteral
	 *     STPrimaryExpression returns STTimeLiteral
	 *     STLiteralExpressions returns STTimeLiteral
	 *     STTimeLiteral returns STTimeLiteral
	 *
	 * Constraint:
	 *     (type=[DataType|STTimeLiteralType] value=Time)
	 * </pre>
	 */
	protected void sequence_STTimeLiteral(ISerializationContext context, STTimeLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_TIME_LITERAL__TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_TIME_LITERAL__TYPE));
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_TIME_LITERAL__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_TIME_LITERAL__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeSTTimeLiteralTypeParserRuleCall_0_0_1(), semanticObject.eGet(STCorePackage.Literals.ST_TIME_LITERAL__TYPE, false));
		feeder.accept(grammarAccess.getSTTimeLiteralAccess().getValueTimeParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STTimeOfDayLiteral
	 *     STAssignment returns STTimeOfDayLiteral
	 *     STAssignment.STAssignment_1_0 returns STTimeOfDayLiteral
	 *     STExpression returns STTimeOfDayLiteral
	 *     STSubrangeExpression returns STTimeOfDayLiteral
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STTimeOfDayLiteral
	 *     STOrExpression returns STTimeOfDayLiteral
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STTimeOfDayLiteral
	 *     STXorExpression returns STTimeOfDayLiteral
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STTimeOfDayLiteral
	 *     STAndExpression returns STTimeOfDayLiteral
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STTimeOfDayLiteral
	 *     STEqualityExpression returns STTimeOfDayLiteral
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STTimeOfDayLiteral
	 *     STComparisonExpression returns STTimeOfDayLiteral
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STTimeOfDayLiteral
	 *     STAddSubExpression returns STTimeOfDayLiteral
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STTimeOfDayLiteral
	 *     STMulDivModExpression returns STTimeOfDayLiteral
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STTimeOfDayLiteral
	 *     STPowerExpression returns STTimeOfDayLiteral
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STTimeOfDayLiteral
	 *     STUnaryExpression returns STTimeOfDayLiteral
	 *     STAccessExpression returns STTimeOfDayLiteral
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STTimeOfDayLiteral
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STTimeOfDayLiteral
	 *     STPrimaryExpression returns STTimeOfDayLiteral
	 *     STLiteralExpressions returns STTimeOfDayLiteral
	 *     STTimeOfDayLiteral returns STTimeOfDayLiteral
	 *
	 * Constraint:
	 *     (type=[DataType|STTimeOfDayType] value=TimeOfDay)
	 * </pre>
	 */
	protected void sequence_STTimeOfDayLiteral(ISerializationContext context, STTimeOfDayLiteral semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_TIME_OF_DAY_LITERAL__TYPE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_TIME_OF_DAY_LITERAL__TYPE));
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_TIME_OF_DAY_LITERAL__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_TIME_OF_DAY_LITERAL__VALUE));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeSTTimeOfDayTypeParserRuleCall_0_0_1(), semanticObject.eGet(STCorePackage.Literals.ST_TIME_OF_DAY_LITERAL__TYPE, false));
		feeder.accept(grammarAccess.getSTTimeOfDayLiteralAccess().getValueTimeOfDayParserRuleCall_2_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STTypeDeclaration returns STTypeDeclaration
	 *     STTypeDeclaration0 returns STTypeDeclaration
	 *
	 * Constraint:
	 *     (
	 *         (array?='ARRAY' ((ranges+=STExpression ranges+=STExpression*) | (count+='*' count+='*'*)))? 
	 *         type=[INamedElement|STAnyType] 
	 *         maxLength=STExpression?
	 *     )
	 * </pre>
	 */
	protected void sequence_STTypeDeclaration(ISerializationContext context, STTypeDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STUnaryExpression
	 *     STAssignment returns STUnaryExpression
	 *     STAssignment.STAssignment_1_0 returns STUnaryExpression
	 *     STExpression returns STUnaryExpression
	 *     STSubrangeExpression returns STUnaryExpression
	 *     STSubrangeExpression.STBinaryExpression_1_0_0 returns STUnaryExpression
	 *     STOrExpression returns STUnaryExpression
	 *     STOrExpression.STBinaryExpression_1_0_0 returns STUnaryExpression
	 *     STXorExpression returns STUnaryExpression
	 *     STXorExpression.STBinaryExpression_1_0_0 returns STUnaryExpression
	 *     STAndExpression returns STUnaryExpression
	 *     STAndExpression.STBinaryExpression_1_0_0 returns STUnaryExpression
	 *     STEqualityExpression returns STUnaryExpression
	 *     STEqualityExpression.STBinaryExpression_1_0_0 returns STUnaryExpression
	 *     STComparisonExpression returns STUnaryExpression
	 *     STComparisonExpression.STBinaryExpression_1_0_0 returns STUnaryExpression
	 *     STAddSubExpression returns STUnaryExpression
	 *     STAddSubExpression.STBinaryExpression_1_0_0 returns STUnaryExpression
	 *     STMulDivModExpression returns STUnaryExpression
	 *     STMulDivModExpression.STBinaryExpression_1_0_0 returns STUnaryExpression
	 *     STPowerExpression returns STUnaryExpression
	 *     STPowerExpression.STBinaryExpression_1_0_0 returns STUnaryExpression
	 *     STUnaryExpression returns STUnaryExpression
	 *     STAccessExpression returns STUnaryExpression
	 *     STAccessExpression.STMemberAccessExpression_1_0_0 returns STUnaryExpression
	 *     STAccessExpression.STArrayAccessExpression_1_1_0 returns STUnaryExpression
	 *     STPrimaryExpression returns STUnaryExpression
	 *
	 * Constraint:
	 *     (op=UnaryOperator expression=STUnaryExpression)
	 * </pre>
	 */
	protected void sequence_STUnaryExpression(ISerializationContext context, STUnaryExpression semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_UNARY_EXPRESSION__OP) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_UNARY_EXPRESSION__OP));
			if (transientValues.isValueTransient(semanticObject, STCorePackage.Literals.ST_UNARY_EXPRESSION__EXPRESSION) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STCorePackage.Literals.ST_UNARY_EXPRESSION__EXPRESSION));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_3_1_0(), semanticObject.getOp());
		feeder.accept(grammarAccess.getSTUnaryExpressionAccess().getExpressionSTUnaryExpressionParserRuleCall_3_2_0(), semanticObject.getExpression());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STVarDeclarationBlock returns STVarPlainDeclarationBlock
	 *
	 * Constraint:
	 *     (constant?='CONSTANT'? varDeclarations+=STVarDeclaration*)
	 * </pre>
	 */
	protected void sequence_STVarDeclarationBlock(ISerializationContext context, STVarPlainDeclarationBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STVarDeclaration returns STVarDeclaration
	 *
	 * Constraint:
	 *     (
	 *         name=ID 
	 *         locatedAt=[INamedElement|ID]? 
	 *         (array?='ARRAY' ((ranges+=STExpression ranges+=STExpression*) | (count+='*' count+='*'*)))? 
	 *         type=[INamedElement|STAnyType] 
	 *         maxLength=STExpression? 
	 *         defaultValue=STInitializerExpression? 
	 *         pragma=STPragma?
	 *     )
	 * </pre>
	 */
	protected void sequence_STVarDeclaration(ISerializationContext context, STVarDeclaration semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STVarInOutDeclarationBlock returns STVarInOutDeclarationBlock
	 *
	 * Constraint:
	 *     (constant?='CONSTANT'? varDeclarations+=STVarDeclaration*)
	 * </pre>
	 */
	protected void sequence_STVarInOutDeclarationBlock(ISerializationContext context, STVarInOutDeclarationBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STVarInputDeclarationBlock returns STVarInputDeclarationBlock
	 *
	 * Constraint:
	 *     (constant?='CONSTANT'? varDeclarations+=STVarDeclaration*)
	 * </pre>
	 */
	protected void sequence_STVarInputDeclarationBlock(ISerializationContext context, STVarInputDeclarationBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STVarOutputDeclarationBlock returns STVarOutputDeclarationBlock
	 *
	 * Constraint:
	 *     (constant?='CONSTANT'? varDeclarations+=STVarDeclaration*)
	 * </pre>
	 */
	protected void sequence_STVarOutputDeclarationBlock(ISerializationContext context, STVarOutputDeclarationBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STVarTempDeclarationBlock returns STVarTempDeclarationBlock
	 *
	 * Constraint:
	 *     (constant?='CONSTANT'? varDeclarations+=STVarDeclaration*)
	 * </pre>
	 */
	protected void sequence_STVarTempDeclarationBlock(ISerializationContext context, STVarTempDeclarationBlock semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STStatement returns STWhileStatement
	 *     STWhileStatement returns STWhileStatement
	 *
	 * Constraint:
	 *     (condition=STExpression statements+=STStatement*)
	 * </pre>
	 */
	protected void sequence_STWhileStatement(ISerializationContext context, STWhileStatement semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
