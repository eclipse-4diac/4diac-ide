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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage
 * @generated
 */
public class STCoreAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static STCorePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STCoreAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = STCorePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STCoreSwitch<Adapter> modelSwitch =
		new STCoreSwitch<Adapter>() {
			@Override
			public Adapter caseSTSource(STSource object) {
				return createSTSourceAdapter();
			}
			@Override
			public Adapter caseSTCoreSource(STCoreSource object) {
				return createSTCoreSourceAdapter();
			}
			@Override
			public Adapter caseSTImport(STImport object) {
				return createSTImportAdapter();
			}
			@Override
			public Adapter caseSTVarDeclarationBlock(STVarDeclarationBlock object) {
				return createSTVarDeclarationBlockAdapter();
			}
			@Override
			public Adapter caseSTVarPlainDeclarationBlock(STVarPlainDeclarationBlock object) {
				return createSTVarPlainDeclarationBlockAdapter();
			}
			@Override
			public Adapter caseSTVarInputDeclarationBlock(STVarInputDeclarationBlock object) {
				return createSTVarInputDeclarationBlockAdapter();
			}
			@Override
			public Adapter caseSTVarOutputDeclarationBlock(STVarOutputDeclarationBlock object) {
				return createSTVarOutputDeclarationBlockAdapter();
			}
			@Override
			public Adapter caseSTVarInOutDeclarationBlock(STVarInOutDeclarationBlock object) {
				return createSTVarInOutDeclarationBlockAdapter();
			}
			@Override
			public Adapter caseSTVarTempDeclarationBlock(STVarTempDeclarationBlock object) {
				return createSTVarTempDeclarationBlockAdapter();
			}
			@Override
			public Adapter caseSTInitializerExpression(STInitializerExpression object) {
				return createSTInitializerExpressionAdapter();
			}
			@Override
			public Adapter caseSTElementaryInitializerExpression(STElementaryInitializerExpression object) {
				return createSTElementaryInitializerExpressionAdapter();
			}
			@Override
			public Adapter caseSTArrayInitializerExpression(STArrayInitializerExpression object) {
				return createSTArrayInitializerExpressionAdapter();
			}
			@Override
			public Adapter caseSTArrayInitElement(STArrayInitElement object) {
				return createSTArrayInitElementAdapter();
			}
			@Override
			public Adapter caseSTSingleArrayInitElement(STSingleArrayInitElement object) {
				return createSTSingleArrayInitElementAdapter();
			}
			@Override
			public Adapter caseSTRepeatArrayInitElement(STRepeatArrayInitElement object) {
				return createSTRepeatArrayInitElementAdapter();
			}
			@Override
			public Adapter caseSTPragma(STPragma object) {
				return createSTPragmaAdapter();
			}
			@Override
			public Adapter caseSTAttribute(STAttribute object) {
				return createSTAttributeAdapter();
			}
			@Override
			public Adapter caseSTStatement(STStatement object) {
				return createSTStatementAdapter();
			}
			@Override
			public Adapter caseSTAssignment(STAssignment object) {
				return createSTAssignmentAdapter();
			}
			@Override
			public Adapter caseSTCallArgument(STCallArgument object) {
				return createSTCallArgumentAdapter();
			}
			@Override
			public Adapter caseSTCallUnnamedArgument(STCallUnnamedArgument object) {
				return createSTCallUnnamedArgumentAdapter();
			}
			@Override
			public Adapter caseSTCallNamedInputArgument(STCallNamedInputArgument object) {
				return createSTCallNamedInputArgumentAdapter();
			}
			@Override
			public Adapter caseSTCallNamedOutputArgument(STCallNamedOutputArgument object) {
				return createSTCallNamedOutputArgumentAdapter();
			}
			@Override
			public Adapter caseSTIfStatement(STIfStatement object) {
				return createSTIfStatementAdapter();
			}
			@Override
			public Adapter caseSTElseIfPart(STElseIfPart object) {
				return createSTElseIfPartAdapter();
			}
			@Override
			public Adapter caseSTCaseStatement(STCaseStatement object) {
				return createSTCaseStatementAdapter();
			}
			@Override
			public Adapter caseSTCaseCases(STCaseCases object) {
				return createSTCaseCasesAdapter();
			}
			@Override
			public Adapter caseSTElsePart(STElsePart object) {
				return createSTElsePartAdapter();
			}
			@Override
			public Adapter caseSTForStatement(STForStatement object) {
				return createSTForStatementAdapter();
			}
			@Override
			public Adapter caseSTWhileStatement(STWhileStatement object) {
				return createSTWhileStatementAdapter();
			}
			@Override
			public Adapter caseSTRepeatStatement(STRepeatStatement object) {
				return createSTRepeatStatementAdapter();
			}
			@Override
			public Adapter caseSTExpression(STExpression object) {
				return createSTExpressionAdapter();
			}
			@Override
			public Adapter caseSTNumericLiteral(STNumericLiteral object) {
				return createSTNumericLiteralAdapter();
			}
			@Override
			public Adapter caseSTDateLiteral(STDateLiteral object) {
				return createSTDateLiteralAdapter();
			}
			@Override
			public Adapter caseSTTimeLiteral(STTimeLiteral object) {
				return createSTTimeLiteralAdapter();
			}
			@Override
			public Adapter caseSTTimeOfDayLiteral(STTimeOfDayLiteral object) {
				return createSTTimeOfDayLiteralAdapter();
			}
			@Override
			public Adapter caseSTDateAndTimeLiteral(STDateAndTimeLiteral object) {
				return createSTDateAndTimeLiteralAdapter();
			}
			@Override
			public Adapter caseSTStringLiteral(STStringLiteral object) {
				return createSTStringLiteralAdapter();
			}
			@Override
			public Adapter caseSTEnumLiteral(STEnumLiteral object) {
				return createSTEnumLiteralAdapter();
			}
			@Override
			public Adapter caseSTVarDeclaration(STVarDeclaration object) {
				return createSTVarDeclarationAdapter();
			}
			@Override
			public Adapter caseSTTypeDeclaration(STTypeDeclaration object) {
				return createSTTypeDeclarationAdapter();
			}
			@Override
			public Adapter caseSTReturn(STReturn object) {
				return createSTReturnAdapter();
			}
			@Override
			public Adapter caseSTContinue(STContinue object) {
				return createSTContinueAdapter();
			}
			@Override
			public Adapter caseSTExit(STExit object) {
				return createSTExitAdapter();
			}
			@Override
			public Adapter caseSTNop(STNop object) {
				return createSTNopAdapter();
			}
			@Override
			public Adapter caseSTBinaryExpression(STBinaryExpression object) {
				return createSTBinaryExpressionAdapter();
			}
			@Override
			public Adapter caseSTUnaryExpression(STUnaryExpression object) {
				return createSTUnaryExpressionAdapter();
			}
			@Override
			public Adapter caseSTMemberAccessExpression(STMemberAccessExpression object) {
				return createSTMemberAccessExpressionAdapter();
			}
			@Override
			public Adapter caseSTArrayAccessExpression(STArrayAccessExpression object) {
				return createSTArrayAccessExpressionAdapter();
			}
			@Override
			public Adapter caseSTFeatureExpression(STFeatureExpression object) {
				return createSTFeatureExpressionAdapter();
			}
			@Override
			public Adapter caseSTBuiltinFeatureExpression(STBuiltinFeatureExpression object) {
				return createSTBuiltinFeatureExpressionAdapter();
			}
			@Override
			public Adapter caseSTMultibitPartialExpression(STMultibitPartialExpression object) {
				return createSTMultibitPartialExpressionAdapter();
			}
			@Override
			public Adapter caseSTStandardFunction(STStandardFunction object) {
				return createSTStandardFunctionAdapter();
			}
			@Override
			public Adapter caseSTComment(STComment object) {
				return createSTCommentAdapter();
			}
			@Override
			public Adapter caseSTStructInitializerExpression(STStructInitializerExpression object) {
				return createSTStructInitializerExpressionAdapter();
			}
			@Override
			public Adapter caseSTStructInitElement(STStructInitElement object) {
				return createSTStructInitElementAdapter();
			}
			@Override
			public Adapter caseSTExpressionSource(STExpressionSource object) {
				return createSTExpressionSourceAdapter();
			}
			@Override
			public Adapter caseSTInitializerExpressionSource(STInitializerExpressionSource object) {
				return createSTInitializerExpressionSourceAdapter();
			}
			@Override
			public Adapter caseImport(Import object) {
				return createImportAdapter();
			}
			@Override
			public Adapter caseINamedElement(INamedElement object) {
				return createINamedElementAdapter();
			}
			@Override
			public Adapter caseITypedElement(ITypedElement object) {
				return createITypedElementAdapter();
			}
			@Override
			public Adapter caseICallable(ICallable object) {
				return createICallableAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource <em>ST Source</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource
	 * @generated
	 */
	public Adapter createSTSourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource
	 * @generated
	 */
	public Adapter createSTCoreSourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport <em>ST Import</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport
	 * @generated
	 */
	public Adapter createSTImportAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock <em>ST Var Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
	 * @generated
	 */
	public Adapter createSTVarDeclarationBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock <em>ST Var Plain Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock
	 * @generated
	 */
	public Adapter createSTVarPlainDeclarationBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock <em>ST Var Input Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
	 * @generated
	 */
	public Adapter createSTVarInputDeclarationBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock <em>ST Var Output Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
	 * @generated
	 */
	public Adapter createSTVarOutputDeclarationBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock <em>ST Var In Out Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
	 * @generated
	 */
	public Adapter createSTVarInOutDeclarationBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock <em>ST Var Temp Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock
	 * @generated
	 */
	public Adapter createSTVarTempDeclarationBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression <em>ST Initializer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression
	 * @generated
	 */
	public Adapter createSTInitializerExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression <em>ST Elementary Initializer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression
	 * @generated
	 */
	public Adapter createSTElementaryInitializerExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression <em>ST Array Initializer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
	 * @generated
	 */
	public Adapter createSTArrayInitializerExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement <em>ST Array Init Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
	 * @generated
	 */
	public Adapter createSTArrayInitElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement <em>ST Single Array Init Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement
	 * @generated
	 */
	public Adapter createSTSingleArrayInitElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement <em>ST Repeat Array Init Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement
	 * @generated
	 */
	public Adapter createSTRepeatArrayInitElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STPragma <em>ST Pragma</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STPragma
	 * @generated
	 */
	public Adapter createSTPragmaAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute <em>ST Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute
	 * @generated
	 */
	public Adapter createSTAttributeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement <em>ST Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement
	 * @generated
	 */
	public Adapter createSTStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment <em>ST Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment
	 * @generated
	 */
	public Adapter createSTAssignmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument <em>ST Call Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument
	 * @generated
	 */
	public Adapter createSTCallArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument <em>ST Call Unnamed Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument
	 * @generated
	 */
	public Adapter createSTCallUnnamedArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument <em>ST Call Named Input Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
	 * @generated
	 */
	public Adapter createSTCallNamedInputArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument <em>ST Call Named Output Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument
	 * @generated
	 */
	public Adapter createSTCallNamedOutputArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement <em>ST If Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement
	 * @generated
	 */
	public Adapter createSTIfStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart <em>ST Else If Part</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart
	 * @generated
	 */
	public Adapter createSTElseIfPartAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement <em>ST Case Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement
	 * @generated
	 */
	public Adapter createSTCaseStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases <em>ST Case Cases</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases
	 * @generated
	 */
	public Adapter createSTCaseCasesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart <em>ST Else Part</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart
	 * @generated
	 */
	public Adapter createSTElsePartAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement <em>ST For Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement
	 * @generated
	 */
	public Adapter createSTForStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement <em>ST While Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement
	 * @generated
	 */
	public Adapter createSTWhileStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement <em>ST Repeat Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
	 * @generated
	 */
	public Adapter createSTRepeatStatementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression <em>ST Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
	 * @generated
	 */
	public Adapter createSTExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral <em>ST Numeric Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
	 * @generated
	 */
	public Adapter createSTNumericLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral <em>ST Date Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral
	 * @generated
	 */
	public Adapter createSTDateLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral <em>ST Time Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral
	 * @generated
	 */
	public Adapter createSTTimeLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral <em>ST Time Of Day Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral
	 * @generated
	 */
	public Adapter createSTTimeOfDayLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral <em>ST Date And Time Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral
	 * @generated
	 */
	public Adapter createSTDateAndTimeLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral <em>ST String Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
	 * @generated
	 */
	public Adapter createSTStringLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral <em>ST Enum Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral
	 * @generated
	 */
	public Adapter createSTEnumLiteralAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration <em>ST Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
	 * @generated
	 */
	public Adapter createSTVarDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration <em>ST Type Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration
	 * @generated
	 */
	public Adapter createSTTypeDeclarationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn <em>ST Return</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
	 * @generated
	 */
	public Adapter createSTReturnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue <em>ST Continue</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue
	 * @generated
	 */
	public Adapter createSTContinueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit <em>ST Exit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit
	 * @generated
	 */
	public Adapter createSTExitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop <em>ST Nop</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop
	 * @generated
	 */
	public Adapter createSTNopAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression <em>ST Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
	 * @generated
	 */
	public Adapter createSTBinaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression <em>ST Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
	 * @generated
	 */
	public Adapter createSTUnaryExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression <em>ST Member Access Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
	 * @generated
	 */
	public Adapter createSTMemberAccessExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression <em>ST Array Access Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
	 * @generated
	 */
	public Adapter createSTArrayAccessExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression <em>ST Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
	 * @generated
	 */
	public Adapter createSTFeatureExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression <em>ST Builtin Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression
	 * @generated
	 */
	public Adapter createSTBuiltinFeatureExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression <em>ST Multibit Partial Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
	 * @generated
	 */
	public Adapter createSTMultibitPartialExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction <em>ST Standard Function</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
	 * @generated
	 */
	public Adapter createSTStandardFunctionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment <em>ST Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment
	 * @generated
	 */
	public Adapter createSTCommentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression <em>ST Struct Initializer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression
	 * @generated
	 */
	public Adapter createSTStructInitializerExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement <em>ST Struct Init Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement
	 * @generated
	 */
	public Adapter createSTStructInitElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource <em>ST Expression Source</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource
	 * @generated
	 */
	public Adapter createSTExpressionSourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource <em>ST Initializer Expression Source</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource
	 * @generated
	 */
	public Adapter createSTInitializerExpressionSourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.Import <em>Import</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Import
	 * @generated
	 */
	public Adapter createImportAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.INamedElement <em>INamed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.INamedElement
	 * @generated
	 */
	public Adapter createINamedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.ITypedElement <em>ITyped Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ITypedElement
	 * @generated
	 */
	public Adapter createITypedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.fordiac.ide.model.libraryElement.ICallable <em>ICallable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ICallable
	 * @generated
	 */
	public Adapter createICallableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //STCoreAdapterFactory
