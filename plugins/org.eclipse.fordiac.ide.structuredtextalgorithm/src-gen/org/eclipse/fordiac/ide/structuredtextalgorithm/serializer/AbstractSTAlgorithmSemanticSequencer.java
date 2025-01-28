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
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.structuredtextalgorithm.services.STAlgorithmGrammarAccess;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmBody;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmPackage;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithmSource;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod;
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethodBody;
import org.eclipse.fordiac.ide.structuredtextcore.serializer.STCoreSemanticSequencer;
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
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public abstract class AbstractSTAlgorithmSemanticSequencer extends STCoreSemanticSequencer {

	@Inject
	private STAlgorithmGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == STAlgorithmPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case STAlgorithmPackage.ST_ALGORITHM:
				sequence_STAlgorithm(context, (STAlgorithm) semanticObject); 
				return; 
			case STAlgorithmPackage.ST_ALGORITHM_BODY:
				sequence_STAlgorithmBody(context, (STAlgorithmBody) semanticObject); 
				return; 
			case STAlgorithmPackage.ST_ALGORITHM_SOURCE:
				sequence_STAlgorithmSource(context, (STAlgorithmSource) semanticObject); 
				return; 
			case STAlgorithmPackage.ST_METHOD:
				sequence_STMethod(context, (STMethod) semanticObject); 
				return; 
			case STAlgorithmPackage.ST_METHOD_BODY:
				sequence_STMethodBody(context, (STMethodBody) semanticObject); 
				return; 
			}
		else if (epackage == STCorePackage.eINSTANCE)
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
	 *     STAlgorithmBody returns STAlgorithmBody
	 *
	 * Constraint:
	 *     (varTempDeclarations+=STVarTempDeclarationBlock* statements+=STStatement*)
	 * </pre>
	 */
	protected void sequence_STAlgorithmBody(ISerializationContext context, STAlgorithmBody semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STAlgorithmSource returns STAlgorithmSource
	 *
	 * Constraint:
	 *     elements+=STAlgorithmSourceElement*
	 * </pre>
	 */
	protected void sequence_STAlgorithmSource(ISerializationContext context, STAlgorithmSource semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STAlgorithmSourceElement returns STAlgorithm
	 *     STAlgorithm returns STAlgorithm
	 *
	 * Constraint:
	 *     (name=ID body=STAlgorithmBody)
	 * </pre>
	 */
	protected void sequence_STAlgorithm(ISerializationContext context, STAlgorithm semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, LibraryElementPackage.Literals.INAMED_ELEMENT__NAME));
			if (transientValues.isValueTransient(semanticObject, STAlgorithmPackage.Literals.ST_ALGORITHM__BODY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, STAlgorithmPackage.Literals.ST_ALGORITHM__BODY));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getSTAlgorithmAccess().getNameIDTerminalRuleCall_1_0(), semanticObject.getName());
		feeder.accept(grammarAccess.getSTAlgorithmAccess().getBodySTAlgorithmBodyParserRuleCall_2_0(), semanticObject.getBody());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STMethodBody returns STMethodBody
	 *
	 * Constraint:
	 *     (
	 *         (
	 *             varDeclarations+=STVarTempDeclarationBlock | 
	 *             varDeclarations+=STVarInputDeclarationBlock | 
	 *             varDeclarations+=STVarOutputDeclarationBlock | 
	 *             varDeclarations+=STVarInOutDeclarationBlock
	 *         )* 
	 *         statements+=STStatement*
	 *     )
	 * </pre>
	 */
	protected void sequence_STMethodBody(ISerializationContext context, STMethodBody semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     STAlgorithmSourceElement returns STMethod
	 *     STMethod returns STMethod
	 *
	 * Constraint:
	 *     (name=ID returnType=[DataType|STAnyType]? body=STMethodBody)
	 * </pre>
	 */
	protected void sequence_STMethod(ISerializationContext context, STMethod semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
