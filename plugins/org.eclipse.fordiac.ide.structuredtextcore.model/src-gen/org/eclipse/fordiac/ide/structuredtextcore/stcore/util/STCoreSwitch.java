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
package org.eclipse.fordiac.ide.structuredtextcore.stcore.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
 * @generated
 */
public class STCoreSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static STCorePackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STCoreSwitch() {
		if (modelPackage == null) {
			modelPackage = STCorePackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case STCorePackage.ST_SOURCE: {
				STSource stSource = (STSource)theEObject;
				T result = caseSTSource(stSource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_CORE_SOURCE: {
				STCoreSource stCoreSource = (STCoreSource)theEObject;
				T result = caseSTCoreSource(stCoreSource);
				if (result == null) result = caseSTSource(stCoreSource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_IMPORT: {
				STImport stImport = (STImport)theEObject;
				T result = caseSTImport(stImport);
				if (result == null) result = caseImport(stImport);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_VAR_DECLARATION_BLOCK: {
				STVarDeclarationBlock stVarDeclarationBlock = (STVarDeclarationBlock)theEObject;
				T result = caseSTVarDeclarationBlock(stVarDeclarationBlock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_VAR_PLAIN_DECLARATION_BLOCK: {
				STVarPlainDeclarationBlock stVarPlainDeclarationBlock = (STVarPlainDeclarationBlock)theEObject;
				T result = caseSTVarPlainDeclarationBlock(stVarPlainDeclarationBlock);
				if (result == null) result = caseSTVarDeclarationBlock(stVarPlainDeclarationBlock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_VAR_INPUT_DECLARATION_BLOCK: {
				STVarInputDeclarationBlock stVarInputDeclarationBlock = (STVarInputDeclarationBlock)theEObject;
				T result = caseSTVarInputDeclarationBlock(stVarInputDeclarationBlock);
				if (result == null) result = caseSTVarDeclarationBlock(stVarInputDeclarationBlock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_VAR_OUTPUT_DECLARATION_BLOCK: {
				STVarOutputDeclarationBlock stVarOutputDeclarationBlock = (STVarOutputDeclarationBlock)theEObject;
				T result = caseSTVarOutputDeclarationBlock(stVarOutputDeclarationBlock);
				if (result == null) result = caseSTVarDeclarationBlock(stVarOutputDeclarationBlock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_VAR_IN_OUT_DECLARATION_BLOCK: {
				STVarInOutDeclarationBlock stVarInOutDeclarationBlock = (STVarInOutDeclarationBlock)theEObject;
				T result = caseSTVarInOutDeclarationBlock(stVarInOutDeclarationBlock);
				if (result == null) result = caseSTVarDeclarationBlock(stVarInOutDeclarationBlock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_VAR_TEMP_DECLARATION_BLOCK: {
				STVarTempDeclarationBlock stVarTempDeclarationBlock = (STVarTempDeclarationBlock)theEObject;
				T result = caseSTVarTempDeclarationBlock(stVarTempDeclarationBlock);
				if (result == null) result = caseSTVarDeclarationBlock(stVarTempDeclarationBlock);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_INITIALIZER_EXPRESSION: {
				STInitializerExpression stInitializerExpression = (STInitializerExpression)theEObject;
				T result = caseSTInitializerExpression(stInitializerExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_ELEMENTARY_INITIALIZER_EXPRESSION: {
				STElementaryInitializerExpression stElementaryInitializerExpression = (STElementaryInitializerExpression)theEObject;
				T result = caseSTElementaryInitializerExpression(stElementaryInitializerExpression);
				if (result == null) result = caseSTInitializerExpression(stElementaryInitializerExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_ARRAY_INITIALIZER_EXPRESSION: {
				STArrayInitializerExpression stArrayInitializerExpression = (STArrayInitializerExpression)theEObject;
				T result = caseSTArrayInitializerExpression(stArrayInitializerExpression);
				if (result == null) result = caseSTInitializerExpression(stArrayInitializerExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_ARRAY_INIT_ELEMENT: {
				STArrayInitElement stArrayInitElement = (STArrayInitElement)theEObject;
				T result = caseSTArrayInitElement(stArrayInitElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_SINGLE_ARRAY_INIT_ELEMENT: {
				STSingleArrayInitElement stSingleArrayInitElement = (STSingleArrayInitElement)theEObject;
				T result = caseSTSingleArrayInitElement(stSingleArrayInitElement);
				if (result == null) result = caseSTArrayInitElement(stSingleArrayInitElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_REPEAT_ARRAY_INIT_ELEMENT: {
				STRepeatArrayInitElement stRepeatArrayInitElement = (STRepeatArrayInitElement)theEObject;
				T result = caseSTRepeatArrayInitElement(stRepeatArrayInitElement);
				if (result == null) result = caseSTArrayInitElement(stRepeatArrayInitElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_PRAGMA: {
				STPragma stPragma = (STPragma)theEObject;
				T result = caseSTPragma(stPragma);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_ATTRIBUTE: {
				STAttribute stAttribute = (STAttribute)theEObject;
				T result = caseSTAttribute(stAttribute);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_STATEMENT: {
				STStatement stStatement = (STStatement)theEObject;
				T result = caseSTStatement(stStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_ASSIGNMENT: {
				STAssignment stAssignment = (STAssignment)theEObject;
				T result = caseSTAssignment(stAssignment);
				if (result == null) result = caseSTExpression(stAssignment);
				if (result == null) result = caseSTStatement(stAssignment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_CALL_ARGUMENT: {
				STCallArgument stCallArgument = (STCallArgument)theEObject;
				T result = caseSTCallArgument(stCallArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_CALL_UNNAMED_ARGUMENT: {
				STCallUnnamedArgument stCallUnnamedArgument = (STCallUnnamedArgument)theEObject;
				T result = caseSTCallUnnamedArgument(stCallUnnamedArgument);
				if (result == null) result = caseSTCallArgument(stCallUnnamedArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_CALL_NAMED_INPUT_ARGUMENT: {
				STCallNamedInputArgument stCallNamedInputArgument = (STCallNamedInputArgument)theEObject;
				T result = caseSTCallNamedInputArgument(stCallNamedInputArgument);
				if (result == null) result = caseSTCallArgument(stCallNamedInputArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_CALL_NAMED_OUTPUT_ARGUMENT: {
				STCallNamedOutputArgument stCallNamedOutputArgument = (STCallNamedOutputArgument)theEObject;
				T result = caseSTCallNamedOutputArgument(stCallNamedOutputArgument);
				if (result == null) result = caseSTCallArgument(stCallNamedOutputArgument);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_IF_STATEMENT: {
				STIfStatement stIfStatement = (STIfStatement)theEObject;
				T result = caseSTIfStatement(stIfStatement);
				if (result == null) result = caseSTStatement(stIfStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_ELSE_IF_PART: {
				STElseIfPart stElseIfPart = (STElseIfPart)theEObject;
				T result = caseSTElseIfPart(stElseIfPart);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_CASE_STATEMENT: {
				STCaseStatement stCaseStatement = (STCaseStatement)theEObject;
				T result = caseSTCaseStatement(stCaseStatement);
				if (result == null) result = caseSTStatement(stCaseStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_CASE_CASES: {
				STCaseCases stCaseCases = (STCaseCases)theEObject;
				T result = caseSTCaseCases(stCaseCases);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_ELSE_PART: {
				STElsePart stElsePart = (STElsePart)theEObject;
				T result = caseSTElsePart(stElsePart);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_FOR_STATEMENT: {
				STForStatement stForStatement = (STForStatement)theEObject;
				T result = caseSTForStatement(stForStatement);
				if (result == null) result = caseSTStatement(stForStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_WHILE_STATEMENT: {
				STWhileStatement stWhileStatement = (STWhileStatement)theEObject;
				T result = caseSTWhileStatement(stWhileStatement);
				if (result == null) result = caseSTStatement(stWhileStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_REPEAT_STATEMENT: {
				STRepeatStatement stRepeatStatement = (STRepeatStatement)theEObject;
				T result = caseSTRepeatStatement(stRepeatStatement);
				if (result == null) result = caseSTStatement(stRepeatStatement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_EXPRESSION: {
				STExpression stExpression = (STExpression)theEObject;
				T result = caseSTExpression(stExpression);
				if (result == null) result = caseSTStatement(stExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_NUMERIC_LITERAL: {
				STNumericLiteral stNumericLiteral = (STNumericLiteral)theEObject;
				T result = caseSTNumericLiteral(stNumericLiteral);
				if (result == null) result = caseSTExpression(stNumericLiteral);
				if (result == null) result = caseSTStatement(stNumericLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_DATE_LITERAL: {
				STDateLiteral stDateLiteral = (STDateLiteral)theEObject;
				T result = caseSTDateLiteral(stDateLiteral);
				if (result == null) result = caseSTExpression(stDateLiteral);
				if (result == null) result = caseSTStatement(stDateLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_TIME_LITERAL: {
				STTimeLiteral stTimeLiteral = (STTimeLiteral)theEObject;
				T result = caseSTTimeLiteral(stTimeLiteral);
				if (result == null) result = caseSTExpression(stTimeLiteral);
				if (result == null) result = caseSTStatement(stTimeLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_TIME_OF_DAY_LITERAL: {
				STTimeOfDayLiteral stTimeOfDayLiteral = (STTimeOfDayLiteral)theEObject;
				T result = caseSTTimeOfDayLiteral(stTimeOfDayLiteral);
				if (result == null) result = caseSTExpression(stTimeOfDayLiteral);
				if (result == null) result = caseSTStatement(stTimeOfDayLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_DATE_AND_TIME_LITERAL: {
				STDateAndTimeLiteral stDateAndTimeLiteral = (STDateAndTimeLiteral)theEObject;
				T result = caseSTDateAndTimeLiteral(stDateAndTimeLiteral);
				if (result == null) result = caseSTExpression(stDateAndTimeLiteral);
				if (result == null) result = caseSTStatement(stDateAndTimeLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_STRING_LITERAL: {
				STStringLiteral stStringLiteral = (STStringLiteral)theEObject;
				T result = caseSTStringLiteral(stStringLiteral);
				if (result == null) result = caseSTExpression(stStringLiteral);
				if (result == null) result = caseSTStatement(stStringLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_ENUM_LITERAL: {
				STEnumLiteral stEnumLiteral = (STEnumLiteral)theEObject;
				T result = caseSTEnumLiteral(stEnumLiteral);
				if (result == null) result = caseSTExpression(stEnumLiteral);
				if (result == null) result = caseSTStatement(stEnumLiteral);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_VAR_DECLARATION: {
				STVarDeclaration stVarDeclaration = (STVarDeclaration)theEObject;
				T result = caseSTVarDeclaration(stVarDeclaration);
				if (result == null) result = caseITypedElement(stVarDeclaration);
				if (result == null) result = caseINamedElement(stVarDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_TYPE_DECLARATION: {
				STTypeDeclaration stTypeDeclaration = (STTypeDeclaration)theEObject;
				T result = caseSTTypeDeclaration(stTypeDeclaration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_RETURN: {
				STReturn stReturn = (STReturn)theEObject;
				T result = caseSTReturn(stReturn);
				if (result == null) result = caseSTStatement(stReturn);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_CONTINUE: {
				STContinue stContinue = (STContinue)theEObject;
				T result = caseSTContinue(stContinue);
				if (result == null) result = caseSTStatement(stContinue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_EXIT: {
				STExit stExit = (STExit)theEObject;
				T result = caseSTExit(stExit);
				if (result == null) result = caseSTStatement(stExit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_NOP: {
				STNop stNop = (STNop)theEObject;
				T result = caseSTNop(stNop);
				if (result == null) result = caseSTStatement(stNop);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_BINARY_EXPRESSION: {
				STBinaryExpression stBinaryExpression = (STBinaryExpression)theEObject;
				T result = caseSTBinaryExpression(stBinaryExpression);
				if (result == null) result = caseSTExpression(stBinaryExpression);
				if (result == null) result = caseSTStatement(stBinaryExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_UNARY_EXPRESSION: {
				STUnaryExpression stUnaryExpression = (STUnaryExpression)theEObject;
				T result = caseSTUnaryExpression(stUnaryExpression);
				if (result == null) result = caseSTExpression(stUnaryExpression);
				if (result == null) result = caseSTStatement(stUnaryExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_MEMBER_ACCESS_EXPRESSION: {
				STMemberAccessExpression stMemberAccessExpression = (STMemberAccessExpression)theEObject;
				T result = caseSTMemberAccessExpression(stMemberAccessExpression);
				if (result == null) result = caseSTExpression(stMemberAccessExpression);
				if (result == null) result = caseSTStatement(stMemberAccessExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_ARRAY_ACCESS_EXPRESSION: {
				STArrayAccessExpression stArrayAccessExpression = (STArrayAccessExpression)theEObject;
				T result = caseSTArrayAccessExpression(stArrayAccessExpression);
				if (result == null) result = caseSTExpression(stArrayAccessExpression);
				if (result == null) result = caseSTStatement(stArrayAccessExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_FEATURE_EXPRESSION: {
				STFeatureExpression stFeatureExpression = (STFeatureExpression)theEObject;
				T result = caseSTFeatureExpression(stFeatureExpression);
				if (result == null) result = caseSTExpression(stFeatureExpression);
				if (result == null) result = caseSTStatement(stFeatureExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_BUILTIN_FEATURE_EXPRESSION: {
				STBuiltinFeatureExpression stBuiltinFeatureExpression = (STBuiltinFeatureExpression)theEObject;
				T result = caseSTBuiltinFeatureExpression(stBuiltinFeatureExpression);
				if (result == null) result = caseSTExpression(stBuiltinFeatureExpression);
				if (result == null) result = caseSTStatement(stBuiltinFeatureExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_MULTIBIT_PARTIAL_EXPRESSION: {
				STMultibitPartialExpression stMultibitPartialExpression = (STMultibitPartialExpression)theEObject;
				T result = caseSTMultibitPartialExpression(stMultibitPartialExpression);
				if (result == null) result = caseSTExpression(stMultibitPartialExpression);
				if (result == null) result = caseSTStatement(stMultibitPartialExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_STANDARD_FUNCTION: {
				STStandardFunction stStandardFunction = (STStandardFunction)theEObject;
				T result = caseSTStandardFunction(stStandardFunction);
				if (result == null) result = caseICallable(stStandardFunction);
				if (result == null) result = caseINamedElement(stStandardFunction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_COMMENT: {
				STComment stComment = (STComment)theEObject;
				T result = caseSTComment(stComment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_STRUCT_INITIALIZER_EXPRESSION: {
				STStructInitializerExpression stStructInitializerExpression = (STStructInitializerExpression)theEObject;
				T result = caseSTStructInitializerExpression(stStructInitializerExpression);
				if (result == null) result = caseSTInitializerExpression(stStructInitializerExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_STRUCT_INIT_ELEMENT: {
				STStructInitElement stStructInitElement = (STStructInitElement)theEObject;
				T result = caseSTStructInitElement(stStructInitElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_EXPRESSION_SOURCE: {
				STExpressionSource stExpressionSource = (STExpressionSource)theEObject;
				T result = caseSTExpressionSource(stExpressionSource);
				if (result == null) result = caseSTSource(stExpressionSource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case STCorePackage.ST_INITIALIZER_EXPRESSION_SOURCE: {
				STInitializerExpressionSource stInitializerExpressionSource = (STInitializerExpressionSource)theEObject;
				T result = caseSTInitializerExpressionSource(stInitializerExpressionSource);
				if (result == null) result = caseSTSource(stInitializerExpressionSource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTSource(STSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTCoreSource(STCoreSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Import</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Import</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTImport(STImport object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Var Declaration Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Var Declaration Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTVarDeclarationBlock(STVarDeclarationBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Var Plain Declaration Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Var Plain Declaration Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTVarPlainDeclarationBlock(STVarPlainDeclarationBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Var Input Declaration Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Var Input Declaration Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTVarInputDeclarationBlock(STVarInputDeclarationBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Var Output Declaration Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Var Output Declaration Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTVarOutputDeclarationBlock(STVarOutputDeclarationBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Var In Out Declaration Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Var In Out Declaration Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTVarInOutDeclarationBlock(STVarInOutDeclarationBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Var Temp Declaration Block</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Var Temp Declaration Block</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTVarTempDeclarationBlock(STVarTempDeclarationBlock object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Initializer Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Initializer Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTInitializerExpression(STInitializerExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Elementary Initializer Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Elementary Initializer Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTElementaryInitializerExpression(STElementaryInitializerExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Array Initializer Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Array Initializer Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTArrayInitializerExpression(STArrayInitializerExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Array Init Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Array Init Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTArrayInitElement(STArrayInitElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Single Array Init Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Single Array Init Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTSingleArrayInitElement(STSingleArrayInitElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Repeat Array Init Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Repeat Array Init Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTRepeatArrayInitElement(STRepeatArrayInitElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Pragma</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Pragma</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTPragma(STPragma object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Attribute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTAttribute(STAttribute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTStatement(STStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Assignment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTAssignment(STAssignment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Call Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Call Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTCallArgument(STCallArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Call Unnamed Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Call Unnamed Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTCallUnnamedArgument(STCallUnnamedArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Call Named Input Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Call Named Input Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTCallNamedInputArgument(STCallNamedInputArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Call Named Output Argument</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Call Named Output Argument</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTCallNamedOutputArgument(STCallNamedOutputArgument object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST If Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST If Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTIfStatement(STIfStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Else If Part</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Else If Part</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTElseIfPart(STElseIfPart object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Case Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Case Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTCaseStatement(STCaseStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Case Cases</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Case Cases</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTCaseCases(STCaseCases object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Else Part</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Else Part</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTElsePart(STElsePart object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST For Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST For Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTForStatement(STForStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST While Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST While Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTWhileStatement(STWhileStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Repeat Statement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Repeat Statement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTRepeatStatement(STRepeatStatement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTExpression(STExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Numeric Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Numeric Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTNumericLiteral(STNumericLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Date Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Date Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTDateLiteral(STDateLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Time Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Time Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTTimeLiteral(STTimeLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Time Of Day Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Time Of Day Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTTimeOfDayLiteral(STTimeOfDayLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Date And Time Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Date And Time Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTDateAndTimeLiteral(STDateAndTimeLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST String Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST String Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTStringLiteral(STStringLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Enum Literal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Enum Literal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTEnumLiteral(STEnumLiteral object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Var Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Var Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTVarDeclaration(STVarDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Type Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Type Declaration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTTypeDeclaration(STTypeDeclaration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Return</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Return</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTReturn(STReturn object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Continue</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Continue</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTContinue(STContinue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Exit</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Exit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTExit(STExit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Nop</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Nop</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTNop(STNop object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Binary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Binary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTBinaryExpression(STBinaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Unary Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTUnaryExpression(STUnaryExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Member Access Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Member Access Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTMemberAccessExpression(STMemberAccessExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Array Access Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Array Access Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTArrayAccessExpression(STArrayAccessExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Feature Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Feature Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTFeatureExpression(STFeatureExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Builtin Feature Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Builtin Feature Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTBuiltinFeatureExpression(STBuiltinFeatureExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Multibit Partial Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Multibit Partial Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTMultibitPartialExpression(STMultibitPartialExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Standard Function</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Standard Function</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTStandardFunction(STStandardFunction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Comment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Comment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTComment(STComment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Struct Initializer Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Struct Initializer Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTStructInitializerExpression(STStructInitializerExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Struct Init Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Struct Init Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTStructInitElement(STStructInitElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Expression Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Expression Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTExpressionSource(STExpressionSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ST Initializer Expression Source</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ST Initializer Expression Source</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSTInitializerExpressionSource(STInitializerExpressionSource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Import</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Import</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseImport(Import object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamedElement(INamedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ITyped Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ITyped Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseITypedElement(ITypedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ICallable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ICallable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseICallable(ICallable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //STCoreSwitch
