/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextcore.stcore.impl;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class STCoreFactoryImpl extends EFactoryImpl implements STCoreFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static STCoreFactory init() {
		try {
			STCoreFactory theSTCoreFactory = (STCoreFactory)EPackage.Registry.INSTANCE.getEFactory(STCorePackage.eNS_URI);
			if (theSTCoreFactory != null) {
				return theSTCoreFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new STCoreFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STCoreFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case STCorePackage.ST_SOURCE: return createSTSource();
			case STCorePackage.ST_CORE_SOURCE: return createSTCoreSource();
			case STCorePackage.ST_IMPORT: return createSTImport();
			case STCorePackage.ST_VAR_DECLARATION_BLOCK: return createSTVarDeclarationBlock();
			case STCorePackage.ST_VAR_PLAIN_DECLARATION_BLOCK: return createSTVarPlainDeclarationBlock();
			case STCorePackage.ST_VAR_INPUT_DECLARATION_BLOCK: return createSTVarInputDeclarationBlock();
			case STCorePackage.ST_VAR_OUTPUT_DECLARATION_BLOCK: return createSTVarOutputDeclarationBlock();
			case STCorePackage.ST_VAR_IN_OUT_DECLARATION_BLOCK: return createSTVarInOutDeclarationBlock();
			case STCorePackage.ST_VAR_TEMP_DECLARATION_BLOCK: return createSTVarTempDeclarationBlock();
			case STCorePackage.ST_INITIALIZER_EXPRESSION: return createSTInitializerExpression();
			case STCorePackage.ST_ELEMENTARY_INITIALIZER_EXPRESSION: return createSTElementaryInitializerExpression();
			case STCorePackage.ST_ARRAY_INITIALIZER_EXPRESSION: return createSTArrayInitializerExpression();
			case STCorePackage.ST_SINGLE_ARRAY_INIT_ELEMENT: return createSTSingleArrayInitElement();
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT: return createSTRepeatArrayInitElement();
			case STCorePackage.ST_PRAGMA: return createSTPragma();
			case STCorePackage.ST_ATTRIBUTE: return createSTAttribute();
			case STCorePackage.ST_STATEMENT: return createSTStatement();
			case STCorePackage.ST_ASSIGNMENT: return createSTAssignment();
			case STCorePackage.ST_CALL_ARGUMENT: return createSTCallArgument();
			case STCorePackage.ST_CALL_UNNAMED_ARGUMENT: return createSTCallUnnamedArgument();
			case STCorePackage.ST_CALL_NAMED_INPUT_ARGUMENT: return createSTCallNamedInputArgument();
			case STCorePackage.ST_CALL_NAMED_OUTPUT_ARGUMENT: return createSTCallNamedOutputArgument();
			case STCorePackage.ST_IF_STATEMENT: return createSTIfStatement();
			case STCorePackage.ST_ELSE_IF_PART: return createSTElseIfPart();
			case STCorePackage.ST_CASE_STATEMENT: return createSTCaseStatement();
			case STCorePackage.ST_CASE_CASES: return createSTCaseCases();
			case STCorePackage.ST_ELSE_PART: return createSTElsePart();
			case STCorePackage.ST_FOR_STATEMENT: return createSTForStatement();
			case STCorePackage.ST_WHILE_STATEMENT: return createSTWhileStatement();
			case STCorePackage.ST_REPEAT_STATEMENT: return createSTRepeatStatement();
			case STCorePackage.ST_EXPRESSION: return createSTExpression();
			case STCorePackage.ST_NUMERIC_LITERAL: return createSTNumericLiteral();
			case STCorePackage.ST_DATE_LITERAL: return createSTDateLiteral();
			case STCorePackage.ST_TIME_LITERAL: return createSTTimeLiteral();
			case STCorePackage.ST_TIME_OF_DAY_LITERAL: return createSTTimeOfDayLiteral();
			case STCorePackage.ST_DATE_AND_TIME_LITERAL: return createSTDateAndTimeLiteral();
			case STCorePackage.ST_STRING_LITERAL: return createSTStringLiteral();
			case STCorePackage.ST_ENUM_LITERAL: return createSTEnumLiteral();
			case STCorePackage.ST_VAR_DECLARATION: return createSTVarDeclaration();
			case STCorePackage.ST_TYPE_DECLARATION: return createSTTypeDeclaration();
			case STCorePackage.ST_RETURN: return createSTReturn();
			case STCorePackage.ST_CONTINUE: return createSTContinue();
			case STCorePackage.ST_EXIT: return createSTExit();
			case STCorePackage.ST_NOP: return createSTNop();
			case STCorePackage.ST_BINARY_EXPRESSION: return createSTBinaryExpression();
			case STCorePackage.ST_UNARY_EXPRESSION: return createSTUnaryExpression();
			case STCorePackage.ST_MEMBER_ACCESS_EXPRESSION: return createSTMemberAccessExpression();
			case STCorePackage.ST_ARRAY_ACCESS_EXPRESSION: return createSTArrayAccessExpression();
			case STCorePackage.ST_FEATURE_EXPRESSION: return createSTFeatureExpression();
			case STCorePackage.ST_BUILTIN_FEATURE_EXPRESSION: return createSTBuiltinFeatureExpression();
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION: return createSTMultibitPartialExpression();
			case STCorePackage.ST_STANDARD_FUNCTION: return createSTStandardFunction();
			case STCorePackage.ST_COMMENT: return createSTComment();
			case STCorePackage.ST_STRUCT_INITIALIZER_EXPRESSION: return createSTStructInitializerExpression();
			case STCorePackage.ST_STRUCT_INIT_ELEMENT: return createSTStructInitElement();
			case STCorePackage.ST_EXPRESSION_SOURCE: return createSTExpressionSource();
			case STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE: return createSTInitializerExpressionSource();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case STCorePackage.ST_BINARY_OPERATOR:
				return createSTBinaryOperatorFromString(eDataType, initialValue);
			case STCorePackage.ST_UNARY_OPERATOR:
				return createSTUnaryOperatorFromString(eDataType, initialValue);
			case STCorePackage.ST_MULTI_BIT_ACCESS_SPECIFIER:
				return createSTMultiBitAccessSpecifierFromString(eDataType, initialValue);
			case STCorePackage.ST_BUILTIN_FEATURE:
				return createSTBuiltinFeatureFromString(eDataType, initialValue);
			case STCorePackage.ST_ACCESS_SPECIFIER:
				return createSTAccessSpecifierFromString(eDataType, initialValue);
			case STCorePackage.ST_COMMENT_POSITION:
				return createSTCommentPositionFromString(eDataType, initialValue);
			case STCorePackage.ST_DATE:
				return createSTDateFromString(eDataType, initialValue);
			case STCorePackage.ST_TIME:
				return createSTTimeFromString(eDataType, initialValue);
			case STCorePackage.ST_TIME_OF_DAY:
				return createSTTimeOfDayFromString(eDataType, initialValue);
			case STCorePackage.ST_DATE_AND_TIME:
				return createSTDateAndTimeFromString(eDataType, initialValue);
			case STCorePackage.ST_STRING:
				return createSTStringFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case STCorePackage.ST_BINARY_OPERATOR:
				return convertSTBinaryOperatorToString(eDataType, instanceValue);
			case STCorePackage.ST_UNARY_OPERATOR:
				return convertSTUnaryOperatorToString(eDataType, instanceValue);
			case STCorePackage.ST_MULTI_BIT_ACCESS_SPECIFIER:
				return convertSTMultiBitAccessSpecifierToString(eDataType, instanceValue);
			case STCorePackage.ST_BUILTIN_FEATURE:
				return convertSTBuiltinFeatureToString(eDataType, instanceValue);
			case STCorePackage.ST_ACCESS_SPECIFIER:
				return convertSTAccessSpecifierToString(eDataType, instanceValue);
			case STCorePackage.ST_COMMENT_POSITION:
				return convertSTCommentPositionToString(eDataType, instanceValue);
			case STCorePackage.ST_DATE:
				return convertSTDateToString(eDataType, instanceValue);
			case STCorePackage.ST_TIME:
				return convertSTTimeToString(eDataType, instanceValue);
			case STCorePackage.ST_TIME_OF_DAY:
				return convertSTTimeOfDayToString(eDataType, instanceValue);
			case STCorePackage.ST_DATE_AND_TIME:
				return convertSTDateAndTimeToString(eDataType, instanceValue);
			case STCorePackage.ST_STRING:
				return convertSTStringToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STSource createSTSource() {
		STSourceImpl stSource = new STSourceImpl();
		return stSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STCoreSource createSTCoreSource() {
		STCoreSourceImpl stCoreSource = new STCoreSourceImpl();
		return stCoreSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STImport createSTImport() {
		STImportImpl stImport = new STImportImpl();
		return stImport;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STVarDeclarationBlock createSTVarDeclarationBlock() {
		STVarDeclarationBlockImpl stVarDeclarationBlock = new STVarDeclarationBlockImpl();
		return stVarDeclarationBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STVarPlainDeclarationBlock createSTVarPlainDeclarationBlock() {
		STVarPlainDeclarationBlockImpl stVarPlainDeclarationBlock = new STVarPlainDeclarationBlockImpl();
		return stVarPlainDeclarationBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STVarInputDeclarationBlock createSTVarInputDeclarationBlock() {
		STVarInputDeclarationBlockImpl stVarInputDeclarationBlock = new STVarInputDeclarationBlockImpl();
		return stVarInputDeclarationBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STVarOutputDeclarationBlock createSTVarOutputDeclarationBlock() {
		STVarOutputDeclarationBlockImpl stVarOutputDeclarationBlock = new STVarOutputDeclarationBlockImpl();
		return stVarOutputDeclarationBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STVarInOutDeclarationBlock createSTVarInOutDeclarationBlock() {
		STVarInOutDeclarationBlockImpl stVarInOutDeclarationBlock = new STVarInOutDeclarationBlockImpl();
		return stVarInOutDeclarationBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STVarTempDeclarationBlock createSTVarTempDeclarationBlock() {
		STVarTempDeclarationBlockImpl stVarTempDeclarationBlock = new STVarTempDeclarationBlockImpl();
		return stVarTempDeclarationBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STInitializerExpression createSTInitializerExpression() {
		STInitializerExpressionImpl stInitializerExpression = new STInitializerExpressionImpl();
		return stInitializerExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STElementaryInitializerExpression createSTElementaryInitializerExpression() {
		STElementaryInitializerExpressionImpl stElementaryInitializerExpression = new STElementaryInitializerExpressionImpl();
		return stElementaryInitializerExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STArrayInitializerExpression createSTArrayInitializerExpression() {
		STArrayInitializerExpressionImpl stArrayInitializerExpression = new STArrayInitializerExpressionImpl();
		return stArrayInitializerExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STSingleArrayInitElement createSTSingleArrayInitElement() {
		STSingleArrayInitElementImpl stSingleArrayInitElement = new STSingleArrayInitElementImpl();
		return stSingleArrayInitElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STRepeatArrayInitElement createSTRepeatArrayInitElement() {
		STRepeatArrayInitElementImpl stRepeatArrayInitElement = new STRepeatArrayInitElementImpl();
		return stRepeatArrayInitElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STPragma createSTPragma() {
		STPragmaImpl stPragma = new STPragmaImpl();
		return stPragma;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STAttribute createSTAttribute() {
		STAttributeImpl stAttribute = new STAttributeImpl();
		return stAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STStatement createSTStatement() {
		STStatementImpl stStatement = new STStatementImpl();
		return stStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STAssignment createSTAssignment() {
		STAssignmentImpl stAssignment = new STAssignmentImpl();
		return stAssignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STCallArgument createSTCallArgument() {
		STCallArgumentImpl stCallArgument = new STCallArgumentImpl();
		return stCallArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STCallUnnamedArgument createSTCallUnnamedArgument() {
		STCallUnnamedArgumentImpl stCallUnnamedArgument = new STCallUnnamedArgumentImpl();
		return stCallUnnamedArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STCallNamedInputArgument createSTCallNamedInputArgument() {
		STCallNamedInputArgumentImpl stCallNamedInputArgument = new STCallNamedInputArgumentImpl();
		return stCallNamedInputArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STCallNamedOutputArgument createSTCallNamedOutputArgument() {
		STCallNamedOutputArgumentImpl stCallNamedOutputArgument = new STCallNamedOutputArgumentImpl();
		return stCallNamedOutputArgument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STIfStatement createSTIfStatement() {
		STIfStatementImpl stIfStatement = new STIfStatementImpl();
		return stIfStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STElseIfPart createSTElseIfPart() {
		STElseIfPartImpl stElseIfPart = new STElseIfPartImpl();
		return stElseIfPart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STCaseStatement createSTCaseStatement() {
		STCaseStatementImpl stCaseStatement = new STCaseStatementImpl();
		return stCaseStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STCaseCases createSTCaseCases() {
		STCaseCasesImpl stCaseCases = new STCaseCasesImpl();
		return stCaseCases;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STElsePart createSTElsePart() {
		STElsePartImpl stElsePart = new STElsePartImpl();
		return stElsePart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STForStatement createSTForStatement() {
		STForStatementImpl stForStatement = new STForStatementImpl();
		return stForStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STWhileStatement createSTWhileStatement() {
		STWhileStatementImpl stWhileStatement = new STWhileStatementImpl();
		return stWhileStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STRepeatStatement createSTRepeatStatement() {
		STRepeatStatementImpl stRepeatStatement = new STRepeatStatementImpl();
		return stRepeatStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STExpression createSTExpression() {
		STExpressionImpl stExpression = new STExpressionImpl();
		return stExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STNumericLiteral createSTNumericLiteral() {
		STNumericLiteralImpl stNumericLiteral = new STNumericLiteralImpl();
		return stNumericLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STDateLiteral createSTDateLiteral() {
		STDateLiteralImpl stDateLiteral = new STDateLiteralImpl();
		return stDateLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STTimeLiteral createSTTimeLiteral() {
		STTimeLiteralImpl stTimeLiteral = new STTimeLiteralImpl();
		return stTimeLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STTimeOfDayLiteral createSTTimeOfDayLiteral() {
		STTimeOfDayLiteralImpl stTimeOfDayLiteral = new STTimeOfDayLiteralImpl();
		return stTimeOfDayLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STDateAndTimeLiteral createSTDateAndTimeLiteral() {
		STDateAndTimeLiteralImpl stDateAndTimeLiteral = new STDateAndTimeLiteralImpl();
		return stDateAndTimeLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STStringLiteral createSTStringLiteral() {
		STStringLiteralImpl stStringLiteral = new STStringLiteralImpl();
		return stStringLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STEnumLiteral createSTEnumLiteral() {
		STEnumLiteralImpl stEnumLiteral = new STEnumLiteralImpl();
		return stEnumLiteral;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STVarDeclaration createSTVarDeclaration() {
		STVarDeclarationImpl stVarDeclaration = new STVarDeclarationImpl();
		return stVarDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STTypeDeclaration createSTTypeDeclaration() {
		STTypeDeclarationImpl stTypeDeclaration = new STTypeDeclarationImpl();
		return stTypeDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STReturn createSTReturn() {
		STReturnImpl stReturn = new STReturnImpl();
		return stReturn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STContinue createSTContinue() {
		STContinueImpl stContinue = new STContinueImpl();
		return stContinue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STExit createSTExit() {
		STExitImpl stExit = new STExitImpl();
		return stExit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STNop createSTNop() {
		STNopImpl stNop = new STNopImpl();
		return stNop;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STBinaryExpression createSTBinaryExpression() {
		STBinaryExpressionImpl stBinaryExpression = new STBinaryExpressionImpl();
		return stBinaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STUnaryExpression createSTUnaryExpression() {
		STUnaryExpressionImpl stUnaryExpression = new STUnaryExpressionImpl();
		return stUnaryExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STMemberAccessExpression createSTMemberAccessExpression() {
		STMemberAccessExpressionImpl stMemberAccessExpression = new STMemberAccessExpressionImpl();
		return stMemberAccessExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STArrayAccessExpression createSTArrayAccessExpression() {
		STArrayAccessExpressionImpl stArrayAccessExpression = new STArrayAccessExpressionImpl();
		return stArrayAccessExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STFeatureExpression createSTFeatureExpression() {
		STFeatureExpressionImpl stFeatureExpression = new STFeatureExpressionImpl();
		return stFeatureExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STBuiltinFeatureExpression createSTBuiltinFeatureExpression() {
		STBuiltinFeatureExpressionImpl stBuiltinFeatureExpression = new STBuiltinFeatureExpressionImpl();
		return stBuiltinFeatureExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STMultibitPartialExpression createSTMultibitPartialExpression() {
		STMultibitPartialExpressionImpl stMultibitPartialExpression = new STMultibitPartialExpressionImpl();
		return stMultibitPartialExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STStandardFunction createSTStandardFunction() {
		STStandardFunctionImpl stStandardFunction = new STStandardFunctionImpl();
		return stStandardFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STComment createSTComment() {
		STCommentImpl stComment = new STCommentImpl();
		return stComment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STStructInitializerExpression createSTStructInitializerExpression() {
		STStructInitializerExpressionImpl stStructInitializerExpression = new STStructInitializerExpressionImpl();
		return stStructInitializerExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STStructInitElement createSTStructInitElement() {
		STStructInitElementImpl stStructInitElement = new STStructInitElementImpl();
		return stStructInitElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STExpressionSource createSTExpressionSource() {
		STExpressionSourceImpl stExpressionSource = new STExpressionSourceImpl();
		return stExpressionSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STInitializerExpressionSource createSTInitializerExpressionSource() {
		STInitializerExpressionSourceImpl stInitializerExpressionSource = new STInitializerExpressionSourceImpl();
		return stInitializerExpressionSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STBinaryOperator createSTBinaryOperatorFromString(EDataType eDataType, String initialValue) {
		STBinaryOperator result = STBinaryOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTBinaryOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STUnaryOperator createSTUnaryOperatorFromString(EDataType eDataType, String initialValue) {
		STUnaryOperator result = STUnaryOperator.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTUnaryOperatorToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STMultiBitAccessSpecifier createSTMultiBitAccessSpecifierFromString(EDataType eDataType, String initialValue) {
		STMultiBitAccessSpecifier result = STMultiBitAccessSpecifier.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTMultiBitAccessSpecifierToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STBuiltinFeature createSTBuiltinFeatureFromString(EDataType eDataType, String initialValue) {
		STBuiltinFeature result = STBuiltinFeature.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTBuiltinFeatureToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STAccessSpecifier createSTAccessSpecifierFromString(EDataType eDataType, String initialValue) {
		STAccessSpecifier result = STAccessSpecifier.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTAccessSpecifierToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STCommentPosition createSTCommentPositionFromString(EDataType eDataType, String initialValue) {
		STCommentPosition result = STCommentPosition.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTCommentPositionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalDate createSTDateFromString(EDataType eDataType, String initialValue) {
		return (LocalDate)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTDateToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Duration createSTTimeFromString(EDataType eDataType, String initialValue) {
		return (Duration)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTTimeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalTime createSTTimeOfDayFromString(EDataType eDataType, String initialValue) {
		return (LocalTime)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTTimeOfDayToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalDateTime createSTDateAndTimeFromString(EDataType eDataType, String initialValue) {
		return (LocalDateTime)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTDateAndTimeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STString createSTString(final String it) {
		return org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStringAnnotations.createSTString(it);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STString createSTStringFromString(EDataType eDataType, String initialValue) {
		return createSTString(initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTString(final STString it) {
		return org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStringAnnotations.convertSTString(it);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSTStringToString(EDataType eDataType, Object instanceValue) {
		return convertSTString((STString)instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public STCorePackage getSTCorePackage() {
		return (STCorePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static STCorePackage getPackage() {
		return STCorePackage.eINSTANCE;
	}

} //STCoreFactoryImpl
