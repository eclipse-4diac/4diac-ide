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
package org.eclipse.fordiac.ide.structuredtextcore.stcore;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
 * @generated
 */
public interface STCoreFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	STCoreFactory eINSTANCE = org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCoreFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>ST Source</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Source</em>'.
	 * @generated
	 */
	STSource createSTSource();

	/**
	 * Returns a new object of class '<em>Source</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Source</em>'.
	 * @generated
	 */
	STCoreSource createSTCoreSource();

	/**
	 * Returns a new object of class '<em>ST Var Declaration Block</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Var Declaration Block</em>'.
	 * @generated
	 */
	STVarDeclarationBlock createSTVarDeclarationBlock();

	/**
	 * Returns a new object of class '<em>ST Initializer Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Initializer Expression</em>'.
	 * @generated
	 */
	STInitializerExpression createSTInitializerExpression();

	/**
	 * Returns a new object of class '<em>ST Elementary Initializer Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Elementary Initializer Expression</em>'.
	 * @generated
	 */
	STElementaryInitializerExpression createSTElementaryInitializerExpression();

	/**
	 * Returns a new object of class '<em>ST Array Initializer Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Array Initializer Expression</em>'.
	 * @generated
	 */
	STArrayInitializerExpression createSTArrayInitializerExpression();

	/**
	 * Returns a new object of class '<em>ST Array Init Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Array Init Element</em>'.
	 * @generated
	 */
	STArrayInitElement createSTArrayInitElement();

	/**
	 * Returns a new object of class '<em>ST Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Statement</em>'.
	 * @generated
	 */
	STStatement createSTStatement();

	/**
	 * Returns a new object of class '<em>ST Assignment Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Assignment Statement</em>'.
	 * @generated
	 */
	STAssignmentStatement createSTAssignmentStatement();

	/**
	 * Returns a new object of class '<em>ST Call Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Call Statement</em>'.
	 * @generated
	 */
	STCallStatement createSTCallStatement();

	/**
	 * Returns a new object of class '<em>ST Call Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Call Argument</em>'.
	 * @generated
	 */
	STCallArgument createSTCallArgument();

	/**
	 * Returns a new object of class '<em>ST Call Unnamed Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Call Unnamed Argument</em>'.
	 * @generated
	 */
	STCallUnnamedArgument createSTCallUnnamedArgument();

	/**
	 * Returns a new object of class '<em>ST Call Named Input Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Call Named Input Argument</em>'.
	 * @generated
	 */
	STCallNamedInputArgument createSTCallNamedInputArgument();

	/**
	 * Returns a new object of class '<em>ST Call Named Output Argument</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Call Named Output Argument</em>'.
	 * @generated
	 */
	STCallNamedOutputArgument createSTCallNamedOutputArgument();

	/**
	 * Returns a new object of class '<em>ST If Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST If Statement</em>'.
	 * @generated
	 */
	STIfStatement createSTIfStatement();

	/**
	 * Returns a new object of class '<em>ST Else If Part</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Else If Part</em>'.
	 * @generated
	 */
	STElseIfPart createSTElseIfPart();

	/**
	 * Returns a new object of class '<em>ST Case Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Case Statement</em>'.
	 * @generated
	 */
	STCaseStatement createSTCaseStatement();

	/**
	 * Returns a new object of class '<em>ST Case Cases</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Case Cases</em>'.
	 * @generated
	 */
	STCaseCases createSTCaseCases();

	/**
	 * Returns a new object of class '<em>ST Else Part</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Else Part</em>'.
	 * @generated
	 */
	STElsePart createSTElsePart();

	/**
	 * Returns a new object of class '<em>ST For Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST For Statement</em>'.
	 * @generated
	 */
	STForStatement createSTForStatement();

	/**
	 * Returns a new object of class '<em>ST While Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST While Statement</em>'.
	 * @generated
	 */
	STWhileStatement createSTWhileStatement();

	/**
	 * Returns a new object of class '<em>ST Repeat Statement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Repeat Statement</em>'.
	 * @generated
	 */
	STRepeatStatement createSTRepeatStatement();

	/**
	 * Returns a new object of class '<em>ST Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Expression</em>'.
	 * @generated
	 */
	STExpression createSTExpression();

	/**
	 * Returns a new object of class '<em>ST Numeric Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Numeric Literal</em>'.
	 * @generated
	 */
	STNumericLiteral createSTNumericLiteral();

	/**
	 * Returns a new object of class '<em>ST Date Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Date Literal</em>'.
	 * @generated
	 */
	STDateLiteral createSTDateLiteral();

	/**
	 * Returns a new object of class '<em>ST Time Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Time Literal</em>'.
	 * @generated
	 */
	STTimeLiteral createSTTimeLiteral();

	/**
	 * Returns a new object of class '<em>ST Time Of Day Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Time Of Day Literal</em>'.
	 * @generated
	 */
	STTimeOfDayLiteral createSTTimeOfDayLiteral();

	/**
	 * Returns a new object of class '<em>ST Date And Time Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Date And Time Literal</em>'.
	 * @generated
	 */
	STDateAndTimeLiteral createSTDateAndTimeLiteral();

	/**
	 * Returns a new object of class '<em>ST String Literal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST String Literal</em>'.
	 * @generated
	 */
	STStringLiteral createSTStringLiteral();

	/**
	 * Returns a new object of class '<em>ST Var Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Var Declaration</em>'.
	 * @generated
	 */
	STVarDeclaration createSTVarDeclaration();

	/**
	 * Returns a new object of class '<em>ST Return</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Return</em>'.
	 * @generated
	 */
	STReturn createSTReturn();

	/**
	 * Returns a new object of class '<em>ST Continue</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Continue</em>'.
	 * @generated
	 */
	STContinue createSTContinue();

	/**
	 * Returns a new object of class '<em>ST Exit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Exit</em>'.
	 * @generated
	 */
	STExit createSTExit();

	/**
	 * Returns a new object of class '<em>ST Nop</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Nop</em>'.
	 * @generated
	 */
	STNop createSTNop();

	/**
	 * Returns a new object of class '<em>ST Binary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Binary Expression</em>'.
	 * @generated
	 */
	STBinaryExpression createSTBinaryExpression();

	/**
	 * Returns a new object of class '<em>ST Unary Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Unary Expression</em>'.
	 * @generated
	 */
	STUnaryExpression createSTUnaryExpression();

	/**
	 * Returns a new object of class '<em>ST Member Access Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Member Access Expression</em>'.
	 * @generated
	 */
	STMemberAccessExpression createSTMemberAccessExpression();

	/**
	 * Returns a new object of class '<em>ST Array Access Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Array Access Expression</em>'.
	 * @generated
	 */
	STArrayAccessExpression createSTArrayAccessExpression();

	/**
	 * Returns a new object of class '<em>ST Feature Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Feature Expression</em>'.
	 * @generated
	 */
	STFeatureExpression createSTFeatureExpression();

	/**
	 * Returns a new object of class '<em>ST Multibit Partial Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>ST Multibit Partial Expression</em>'.
	 * @generated
	 */
	STMultibitPartialExpression createSTMultibitPartialExpression();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	STCorePackage getSTCorePackage();

} //STCoreFactory
