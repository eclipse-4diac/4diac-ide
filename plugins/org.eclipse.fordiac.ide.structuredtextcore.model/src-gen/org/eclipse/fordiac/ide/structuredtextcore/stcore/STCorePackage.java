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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreFactory
 * @model kind="package"
 * @generated
 */
public interface STCorePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "stcore"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/fordiac/ide/structuredtextcore/STCore"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "stcore"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	STCorePackage eINSTANCE = org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STSourceImpl <em>ST Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STSourceImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTSource()
	 * @generated
	 */
	int ST_SOURCE = 0;

	/**
	 * The number of structural features of the '<em>ST Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_SOURCE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCoreSourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCoreSourceImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCoreSource()
	 * @generated
	 */
	int ST_CORE_SOURCE = 1;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CORE_SOURCE__STATEMENTS = ST_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CORE_SOURCE_FEATURE_COUNT = ST_SOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationBlockImpl <em>ST Var Declaration Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationBlockImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarDeclarationBlock()
	 * @generated
	 */
	int ST_VAR_DECLARATION_BLOCK = 2;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION_BLOCK__CONSTANT = 0;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS = 1;

	/**
	 * The number of structural features of the '<em>ST Var Declaration Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION_BLOCK_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarPlainDeclarationBlockImpl <em>ST Var Plain Declaration Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarPlainDeclarationBlockImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarPlainDeclarationBlock()
	 * @generated
	 */
	int ST_VAR_PLAIN_DECLARATION_BLOCK = 3;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_PLAIN_DECLARATION_BLOCK__CONSTANT = ST_VAR_DECLARATION_BLOCK__CONSTANT;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_PLAIN_DECLARATION_BLOCK__VAR_DECLARATIONS = ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS;

	/**
	 * The number of structural features of the '<em>ST Var Plain Declaration Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_PLAIN_DECLARATION_BLOCK_FEATURE_COUNT = ST_VAR_DECLARATION_BLOCK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarInputDeclarationBlockImpl <em>ST Var Input Declaration Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarInputDeclarationBlockImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarInputDeclarationBlock()
	 * @generated
	 */
	int ST_VAR_INPUT_DECLARATION_BLOCK = 4;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_INPUT_DECLARATION_BLOCK__CONSTANT = ST_VAR_DECLARATION_BLOCK__CONSTANT;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_INPUT_DECLARATION_BLOCK__VAR_DECLARATIONS = ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS;

	/**
	 * The number of structural features of the '<em>ST Var Input Declaration Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_INPUT_DECLARATION_BLOCK_FEATURE_COUNT = ST_VAR_DECLARATION_BLOCK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarOutputDeclarationBlockImpl <em>ST Var Output Declaration Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarOutputDeclarationBlockImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarOutputDeclarationBlock()
	 * @generated
	 */
	int ST_VAR_OUTPUT_DECLARATION_BLOCK = 5;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_OUTPUT_DECLARATION_BLOCK__CONSTANT = ST_VAR_DECLARATION_BLOCK__CONSTANT;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_OUTPUT_DECLARATION_BLOCK__VAR_DECLARATIONS = ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS;

	/**
	 * The number of structural features of the '<em>ST Var Output Declaration Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_OUTPUT_DECLARATION_BLOCK_FEATURE_COUNT = ST_VAR_DECLARATION_BLOCK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarInOutDeclarationBlockImpl <em>ST Var In Out Declaration Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarInOutDeclarationBlockImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarInOutDeclarationBlock()
	 * @generated
	 */
	int ST_VAR_IN_OUT_DECLARATION_BLOCK = 6;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_IN_OUT_DECLARATION_BLOCK__CONSTANT = ST_VAR_DECLARATION_BLOCK__CONSTANT;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_IN_OUT_DECLARATION_BLOCK__VAR_DECLARATIONS = ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS;

	/**
	 * The number of structural features of the '<em>ST Var In Out Declaration Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_IN_OUT_DECLARATION_BLOCK_FEATURE_COUNT = ST_VAR_DECLARATION_BLOCK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarTempDeclarationBlockImpl <em>ST Var Temp Declaration Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarTempDeclarationBlockImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarTempDeclarationBlock()
	 * @generated
	 */
	int ST_VAR_TEMP_DECLARATION_BLOCK = 7;

	/**
	 * The feature id for the '<em><b>Constant</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_TEMP_DECLARATION_BLOCK__CONSTANT = ST_VAR_DECLARATION_BLOCK__CONSTANT;

	/**
	 * The feature id for the '<em><b>Var Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_TEMP_DECLARATION_BLOCK__VAR_DECLARATIONS = ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS;

	/**
	 * The number of structural features of the '<em>ST Var Temp Declaration Block</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_TEMP_DECLARATION_BLOCK_FEATURE_COUNT = ST_VAR_DECLARATION_BLOCK_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STInitializerExpressionImpl <em>ST Initializer Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STInitializerExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTInitializerExpression()
	 * @generated
	 */
	int ST_INITIALIZER_EXPRESSION = 8;

	/**
	 * The number of structural features of the '<em>ST Initializer Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_INITIALIZER_EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElementaryInitializerExpressionImpl <em>ST Elementary Initializer Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElementaryInitializerExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTElementaryInitializerExpression()
	 * @generated
	 */
	int ST_ELEMENTARY_INITIALIZER_EXPRESSION = 9;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ELEMENTARY_INITIALIZER_EXPRESSION__VALUE = ST_INITIALIZER_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ST Elementary Initializer Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ELEMENTARY_INITIALIZER_EXPRESSION_FEATURE_COUNT = ST_INITIALIZER_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayInitializerExpressionImpl <em>ST Array Initializer Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayInitializerExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTArrayInitializerExpression()
	 * @generated
	 */
	int ST_ARRAY_INITIALIZER_EXPRESSION = 10;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ARRAY_INITIALIZER_EXPRESSION__VALUES = ST_INITIALIZER_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ST Array Initializer Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ARRAY_INITIALIZER_EXPRESSION_FEATURE_COUNT = ST_INITIALIZER_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayInitElementImpl <em>ST Array Init Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayInitElementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTArrayInitElement()
	 * @generated
	 */
	int ST_ARRAY_INIT_ELEMENT = 11;

	/**
	 * The feature id for the '<em><b>Index Or Init Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ARRAY_INIT_ELEMENT__INDEX_OR_INIT_EXPRESSION = 0;

	/**
	 * The feature id for the '<em><b>Init Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS = 1;

	/**
	 * The number of structural features of the '<em>ST Array Init Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ARRAY_INIT_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStatementImpl <em>ST Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStatement()
	 * @generated
	 */
	int ST_STATEMENT = 12;

	/**
	 * The number of structural features of the '<em>ST Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STATEMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAssignmentStatementImpl <em>ST Assignment Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAssignmentStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTAssignmentStatement()
	 * @generated
	 */
	int ST_ASSIGNMENT_STATEMENT = 13;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ASSIGNMENT_STATEMENT__LEFT = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ASSIGNMENT_STATEMENT__RIGHT = ST_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Assignment Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ASSIGNMENT_STATEMENT_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallStatementImpl <em>ST Call Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallStatement()
	 * @generated
	 */
	int ST_CALL_STATEMENT = 14;

	/**
	 * The feature id for the '<em><b>Call</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_STATEMENT__CALL = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ST Call Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_STATEMENT_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallArgumentImpl <em>ST Call Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallArgumentImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallArgument()
	 * @generated
	 */
	int ST_CALL_ARGUMENT = 15;

	/**
	 * The number of structural features of the '<em>ST Call Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_ARGUMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallUnnamedArgumentImpl <em>ST Call Unnamed Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallUnnamedArgumentImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallUnnamedArgument()
	 * @generated
	 */
	int ST_CALL_UNNAMED_ARGUMENT = 16;

	/**
	 * The feature id for the '<em><b>Arg</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_UNNAMED_ARGUMENT__ARG = ST_CALL_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ST Call Unnamed Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_UNNAMED_ARGUMENT_FEATURE_COUNT = ST_CALL_ARGUMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedInputArgumentImpl <em>ST Call Named Input Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedInputArgumentImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallNamedInputArgument()
	 * @generated
	 */
	int ST_CALL_NAMED_INPUT_ARGUMENT = 17;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_INPUT_ARGUMENT__TARGET = ST_CALL_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_INPUT_ARGUMENT__SOURCE = ST_CALL_ARGUMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Call Named Input Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_INPUT_ARGUMENT_FEATURE_COUNT = ST_CALL_ARGUMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedOutputArgumentImpl <em>ST Call Named Output Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedOutputArgumentImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallNamedOutputArgument()
	 * @generated
	 */
	int ST_CALL_NAMED_OUTPUT_ARGUMENT = 18;

	/**
	 * The feature id for the '<em><b>Not</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_OUTPUT_ARGUMENT__NOT = ST_CALL_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_OUTPUT_ARGUMENT__SOURCE = ST_CALL_ARGUMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_OUTPUT_ARGUMENT__TARGET = ST_CALL_ARGUMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>ST Call Named Output Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_OUTPUT_ARGUMENT_FEATURE_COUNT = ST_CALL_ARGUMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STIfStatementImpl <em>ST If Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STIfStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTIfStatement()
	 * @generated
	 */
	int ST_IF_STATEMENT = 19;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_IF_STATEMENT__CONDITION = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_IF_STATEMENT__STATEMENTS = ST_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Elseifs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_IF_STATEMENT__ELSEIFS = ST_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Else</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_IF_STATEMENT__ELSE = ST_STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>ST If Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_IF_STATEMENT_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElseIfPartImpl <em>ST Else If Part</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElseIfPartImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTElseIfPart()
	 * @generated
	 */
	int ST_ELSE_IF_PART = 20;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ELSE_IF_PART__CONDITION = 0;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ELSE_IF_PART__STATEMENTS = 1;

	/**
	 * The number of structural features of the '<em>ST Else If Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ELSE_IF_PART_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseStatementImpl <em>ST Case Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCaseStatement()
	 * @generated
	 */
	int ST_CASE_STATEMENT = 21;

	/**
	 * The feature id for the '<em><b>Selector</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CASE_STATEMENT__SELECTOR = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cases</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CASE_STATEMENT__CASES = ST_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CASE_STATEMENT__ELSE = ST_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>ST Case Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CASE_STATEMENT_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseCasesImpl <em>ST Case Cases</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseCasesImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCaseCases()
	 * @generated
	 */
	int ST_CASE_CASES = 22;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CASE_CASES__CONDITIONS = 0;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CASE_CASES__STATEMENTS = 1;

	/**
	 * The number of structural features of the '<em>ST Case Cases</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CASE_CASES_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElsePartImpl <em>ST Else Part</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElsePartImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTElsePart()
	 * @generated
	 */
	int ST_ELSE_PART = 23;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ELSE_PART__STATEMENTS = 0;

	/**
	 * The number of structural features of the '<em>ST Else Part</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ELSE_PART_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STForStatementImpl <em>ST For Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STForStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTForStatement()
	 * @generated
	 */
	int ST_FOR_STATEMENT = 24;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FOR_STATEMENT__VARIABLE = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FOR_STATEMENT__FROM = ST_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FOR_STATEMENT__TO = ST_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>By</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FOR_STATEMENT__BY = ST_STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FOR_STATEMENT__STATEMENTS = ST_STATEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>ST For Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FOR_STATEMENT_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STWhileStatementImpl <em>ST While Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STWhileStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTWhileStatement()
	 * @generated
	 */
	int ST_WHILE_STATEMENT = 25;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_WHILE_STATEMENT__CONDITION = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_WHILE_STATEMENT__STATEMENTS = ST_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST While Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_WHILE_STATEMENT_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatStatementImpl <em>ST Repeat Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTRepeatStatement()
	 * @generated
	 */
	int ST_REPEAT_STATEMENT = 26;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_REPEAT_STATEMENT__STATEMENTS = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_REPEAT_STATEMENT__CONDITION = ST_STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Repeat Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_REPEAT_STATEMENT_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExpressionImpl <em>ST Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTExpression()
	 * @generated
	 */
	int ST_EXPRESSION = 27;

	/**
	 * The number of structural features of the '<em>ST Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_EXPRESSION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STNumericLiteralImpl <em>ST Numeric Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STNumericLiteralImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTNumericLiteral()
	 * @generated
	 */
	int ST_NUMERIC_LITERAL = 28;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_NUMERIC_LITERAL__TYPE = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_NUMERIC_LITERAL__VALUE = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Numeric Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_NUMERIC_LITERAL_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateLiteralImpl <em>ST Date Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateLiteralImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTDateLiteral()
	 * @generated
	 */
	int ST_DATE_LITERAL = 29;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_DATE_LITERAL__TYPE = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_DATE_LITERAL__VALUE = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Date Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_DATE_LITERAL_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTimeLiteralImpl <em>ST Time Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTimeLiteralImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTimeLiteral()
	 * @generated
	 */
	int ST_TIME_LITERAL = 30;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TIME_LITERAL__TYPE = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TIME_LITERAL__VALUE = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Time Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TIME_LITERAL_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTimeOfDayLiteralImpl <em>ST Time Of Day Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTimeOfDayLiteralImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTimeOfDayLiteral()
	 * @generated
	 */
	int ST_TIME_OF_DAY_LITERAL = 31;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TIME_OF_DAY_LITERAL__TYPE = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TIME_OF_DAY_LITERAL__VALUE = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Time Of Day Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TIME_OF_DAY_LITERAL_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateAndTimeLiteralImpl <em>ST Date And Time Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateAndTimeLiteralImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTDateAndTimeLiteral()
	 * @generated
	 */
	int ST_DATE_AND_TIME_LITERAL = 32;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_DATE_AND_TIME_LITERAL__TYPE = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_DATE_AND_TIME_LITERAL__VALUE = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Date And Time Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_DATE_AND_TIME_LITERAL_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStringLiteralImpl <em>ST String Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStringLiteralImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStringLiteral()
	 * @generated
	 */
	int ST_STRING_LITERAL = 33;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STRING_LITERAL__TYPE = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STRING_LITERAL__VALUE = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST String Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STRING_LITERAL_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationImpl <em>ST Var Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarDeclaration()
	 * @generated
	 */
	int ST_VAR_DECLARATION = 34;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__NAME = LibraryElementPackage.INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__COMMENT = LibraryElementPackage.INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Located At</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__LOCATED_AT = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__ARRAY = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__RANGES = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__COUNT = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__TYPE = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__MAX_LENGTH = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__DEFAULT_VALUE = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>ST Var Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION_FEATURE_COUNT = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STReturnImpl <em>ST Return</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STReturnImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTReturn()
	 * @generated
	 */
	int ST_RETURN = 35;

	/**
	 * The number of structural features of the '<em>ST Return</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_RETURN_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STContinueImpl <em>ST Continue</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STContinueImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTContinue()
	 * @generated
	 */
	int ST_CONTINUE = 36;

	/**
	 * The number of structural features of the '<em>ST Continue</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CONTINUE_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExitImpl <em>ST Exit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExitImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTExit()
	 * @generated
	 */
	int ST_EXIT = 37;

	/**
	 * The number of structural features of the '<em>ST Exit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_EXIT_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STNopImpl <em>ST Nop</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STNopImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTNop()
	 * @generated
	 */
	int ST_NOP = 38;

	/**
	 * The number of structural features of the '<em>ST Nop</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_NOP_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STBinaryExpressionImpl <em>ST Binary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STBinaryExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTBinaryExpression()
	 * @generated
	 */
	int ST_BINARY_EXPRESSION = 39;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_BINARY_EXPRESSION__LEFT = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_BINARY_EXPRESSION__OP = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_BINARY_EXPRESSION__RIGHT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>ST Binary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_BINARY_EXPRESSION_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STUnaryExpressionImpl <em>ST Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STUnaryExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTUnaryExpression()
	 * @generated
	 */
	int ST_UNARY_EXPRESSION = 40;

	/**
	 * The feature id for the '<em><b>Op</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_UNARY_EXPRESSION__OP = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_UNARY_EXPRESSION__EXPRESSION = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_UNARY_EXPRESSION_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMemberAccessExpressionImpl <em>ST Member Access Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMemberAccessExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTMemberAccessExpression()
	 * @generated
	 */
	int ST_MEMBER_ACCESS_EXPRESSION = 41;

	/**
	 * The feature id for the '<em><b>Receiver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_MEMBER_ACCESS_EXPRESSION__RECEIVER = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Member</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_MEMBER_ACCESS_EXPRESSION__MEMBER = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Member Access Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_MEMBER_ACCESS_EXPRESSION_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayAccessExpressionImpl <em>ST Array Access Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayAccessExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTArrayAccessExpression()
	 * @generated
	 */
	int ST_ARRAY_ACCESS_EXPRESSION = 42;

	/**
	 * The feature id for the '<em><b>Receiver</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ARRAY_ACCESS_EXPRESSION__RECEIVER = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Index</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ARRAY_ACCESS_EXPRESSION__INDEX = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Array Access Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ARRAY_ACCESS_EXPRESSION_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STFeatureExpressionImpl <em>ST Feature Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STFeatureExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTFeatureExpression()
	 * @generated
	 */
	int ST_FEATURE_EXPRESSION = 43;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FEATURE_EXPRESSION__FEATURE = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Call</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FEATURE_EXPRESSION__CALL = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FEATURE_EXPRESSION__PARAMETERS = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>ST Feature Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_FEATURE_EXPRESSION_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMultibitPartialExpressionImpl <em>ST Multibit Partial Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMultibitPartialExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTMultibitPartialExpression()
	 * @generated
	 */
	int ST_MULTIBIT_PARTIAL_EXPRESSION = 44;

	/**
	 * The feature id for the '<em><b>Specifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_MULTIBIT_PARTIAL_EXPRESSION__SPECIFIER = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_MULTIBIT_PARTIAL_EXPRESSION__INDEX = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_MULTIBIT_PARTIAL_EXPRESSION__EXPRESSION = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>ST Multibit Partial Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_MULTIBIT_PARTIAL_EXPRESSION_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator <em>ST Binary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTBinaryOperator()
	 * @generated
	 */
	int ST_BINARY_OPERATOR = 45;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator <em>ST Unary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTUnaryOperator()
	 * @generated
	 */
	int ST_UNARY_OPERATOR = 46;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier <em>ST Multi Bit Access Specifier</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTMultiBitAccessSpecifier()
	 * @generated
	 */
	int ST_MULTI_BIT_ACCESS_SPECIFIER = 47;

	/**
	 * The meta object id for the '<em>ST Date</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.time.LocalDate
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTDate()
	 * @generated
	 */
	int ST_DATE = 48;

	/**
	 * The meta object id for the '<em>ST Time</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.time.Duration
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTime()
	 * @generated
	 */
	int ST_TIME = 49;

	/**
	 * The meta object id for the '<em>ST Time Of Day</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.time.LocalTime
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTimeOfDay()
	 * @generated
	 */
	int ST_TIME_OF_DAY = 50;

	/**
	 * The meta object id for the '<em>ST Date And Time</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.time.LocalDateTime
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTDateAndTime()
	 * @generated
	 */
	int ST_DATE_AND_TIME = 51;

	/**
	 * The meta object id for the '<em>ST String</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STString
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTString()
	 * @generated
	 */
	int ST_STRING = 52;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource <em>ST Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Source</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource
	 * @generated
	 */
	EClass getSTSource();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource
	 * @generated
	 */
	EClass getSTCoreSource();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCoreSource#getStatements()
	 * @see #getSTCoreSource()
	 * @generated
	 */
	EReference getSTCoreSource_Statements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock <em>ST Var Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Var Declaration Block</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
	 * @generated
	 */
	EClass getSTVarDeclarationBlock();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock#isConstant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Constant</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock#isConstant()
	 * @see #getSTVarDeclarationBlock()
	 * @generated
	 */
	EAttribute getSTVarDeclarationBlock_Constant();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock#getVarDeclarations <em>Var Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declarations</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock#getVarDeclarations()
	 * @see #getSTVarDeclarationBlock()
	 * @generated
	 */
	EReference getSTVarDeclarationBlock_VarDeclarations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock <em>ST Var Plain Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Var Plain Declaration Block</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarPlainDeclarationBlock
	 * @generated
	 */
	EClass getSTVarPlainDeclarationBlock();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock <em>ST Var Input Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Var Input Declaration Block</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
	 * @generated
	 */
	EClass getSTVarInputDeclarationBlock();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock <em>ST Var Output Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Var Output Declaration Block</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarOutputDeclarationBlock
	 * @generated
	 */
	EClass getSTVarOutputDeclarationBlock();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock <em>ST Var In Out Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Var In Out Declaration Block</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
	 * @generated
	 */
	EClass getSTVarInOutDeclarationBlock();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock <em>ST Var Temp Declaration Block</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Var Temp Declaration Block</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarTempDeclarationBlock
	 * @generated
	 */
	EClass getSTVarTempDeclarationBlock();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression <em>ST Initializer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Initializer Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpression
	 * @generated
	 */
	EClass getSTInitializerExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression <em>ST Elementary Initializer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Elementary Initializer Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression
	 * @generated
	 */
	EClass getSTElementaryInitializerExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STElementaryInitializerExpression#getValue()
	 * @see #getSTElementaryInitializerExpression()
	 * @generated
	 */
	EReference getSTElementaryInitializerExpression_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression <em>ST Array Initializer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Array Initializer Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression
	 * @generated
	 */
	EClass getSTArrayInitializerExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitializerExpression#getValues()
	 * @see #getSTArrayInitializerExpression()
	 * @generated
	 */
	EReference getSTArrayInitializerExpression_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement <em>ST Array Init Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Array Init Element</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
	 * @generated
	 */
	EClass getSTArrayInitElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement#getIndexOrInitExpression <em>Index Or Init Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Index Or Init Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement#getIndexOrInitExpression()
	 * @see #getSTArrayInitElement()
	 * @generated
	 */
	EReference getSTArrayInitElement_IndexOrInitExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement#getInitExpressions <em>Init Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Init Expressions</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement#getInitExpressions()
	 * @see #getSTArrayInitElement()
	 * @generated
	 */
	EReference getSTArrayInitElement_InitExpressions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement <em>ST Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Statement</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStatement
	 * @generated
	 */
	EClass getSTStatement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement <em>ST Assignment Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Assignment Statement</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement
	 * @generated
	 */
	EClass getSTAssignmentStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement#getLeft()
	 * @see #getSTAssignmentStatement()
	 * @generated
	 */
	EReference getSTAssignmentStatement_Left();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignmentStatement#getRight()
	 * @see #getSTAssignmentStatement()
	 * @generated
	 */
	EReference getSTAssignmentStatement_Right();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement <em>ST Call Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Call Statement</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement
	 * @generated
	 */
	EClass getSTCallStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement#getCall <em>Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Call</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallStatement#getCall()
	 * @see #getSTCallStatement()
	 * @generated
	 */
	EReference getSTCallStatement_Call();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument <em>ST Call Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Call Argument</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument
	 * @generated
	 */
	EClass getSTCallArgument();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument <em>ST Call Unnamed Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Call Unnamed Argument</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument
	 * @generated
	 */
	EClass getSTCallUnnamedArgument();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument#getArg <em>Arg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Arg</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallUnnamedArgument#getArg()
	 * @see #getSTCallUnnamedArgument()
	 * @generated
	 */
	EReference getSTCallUnnamedArgument_Arg();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument <em>ST Call Named Input Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Call Named Input Argument</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
	 * @generated
	 */
	EClass getSTCallNamedInputArgument();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument#getTarget()
	 * @see #getSTCallNamedInputArgument()
	 * @generated
	 */
	EReference getSTCallNamedInputArgument_Target();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument#getSource()
	 * @see #getSTCallNamedInputArgument()
	 * @generated
	 */
	EReference getSTCallNamedInputArgument_Source();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument <em>ST Call Named Output Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Call Named Output Argument</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument
	 * @generated
	 */
	EClass getSTCallNamedOutputArgument();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument#isNot <em>Not</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Not</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument#isNot()
	 * @see #getSTCallNamedOutputArgument()
	 * @generated
	 */
	EAttribute getSTCallNamedOutputArgument_Not();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument#getSource()
	 * @see #getSTCallNamedOutputArgument()
	 * @generated
	 */
	EReference getSTCallNamedOutputArgument_Source();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument#getTarget()
	 * @see #getSTCallNamedOutputArgument()
	 * @generated
	 */
	EReference getSTCallNamedOutputArgument_Target();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement <em>ST If Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST If Statement</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement
	 * @generated
	 */
	EClass getSTIfStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement#getCondition()
	 * @see #getSTIfStatement()
	 * @generated
	 */
	EReference getSTIfStatement_Condition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement#getStatements()
	 * @see #getSTIfStatement()
	 * @generated
	 */
	EReference getSTIfStatement_Statements();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement#getElseifs <em>Elseifs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elseifs</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement#getElseifs()
	 * @see #getSTIfStatement()
	 * @generated
	 */
	EReference getSTIfStatement_Elseifs();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement#getElse <em>Else</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Else</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STIfStatement#getElse()
	 * @see #getSTIfStatement()
	 * @generated
	 */
	EReference getSTIfStatement_Else();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart <em>ST Else If Part</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Else If Part</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart
	 * @generated
	 */
	EClass getSTElseIfPart();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart#getCondition()
	 * @see #getSTElseIfPart()
	 * @generated
	 */
	EReference getSTElseIfPart_Condition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STElseIfPart#getStatements()
	 * @see #getSTElseIfPart()
	 * @generated
	 */
	EReference getSTElseIfPart_Statements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement <em>ST Case Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Case Statement</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement
	 * @generated
	 */
	EClass getSTCaseStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement#getSelector <em>Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Selector</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement#getSelector()
	 * @see #getSTCaseStatement()
	 * @generated
	 */
	EReference getSTCaseStatement_Selector();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement#getCases <em>Cases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cases</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement#getCases()
	 * @see #getSTCaseStatement()
	 * @generated
	 */
	EReference getSTCaseStatement_Cases();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement#getElse <em>Else</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Else</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseStatement#getElse()
	 * @see #getSTCaseStatement()
	 * @generated
	 */
	EReference getSTCaseStatement_Else();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases <em>ST Case Cases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Case Cases</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases
	 * @generated
	 */
	EClass getSTCaseCases();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conditions</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases#getConditions()
	 * @see #getSTCaseCases()
	 * @generated
	 */
	EReference getSTCaseCases_Conditions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases#getStatements()
	 * @see #getSTCaseCases()
	 * @generated
	 */
	EReference getSTCaseCases_Statements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart <em>ST Else Part</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Else Part</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart
	 * @generated
	 */
	EClass getSTElsePart();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STElsePart#getStatements()
	 * @see #getSTElsePart()
	 * @generated
	 */
	EReference getSTElsePart_Statements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement <em>ST For Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST For Statement</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement
	 * @generated
	 */
	EClass getSTForStatement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getVariable()
	 * @see #getSTForStatement()
	 * @generated
	 */
	EReference getSTForStatement_Variable();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>From</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getFrom()
	 * @see #getSTForStatement()
	 * @generated
	 */
	EReference getSTForStatement_From();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>To</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getTo()
	 * @see #getSTForStatement()
	 * @generated
	 */
	EReference getSTForStatement_To();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getBy <em>By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>By</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getBy()
	 * @see #getSTForStatement()
	 * @generated
	 */
	EReference getSTForStatement_By();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getStatements()
	 * @see #getSTForStatement()
	 * @generated
	 */
	EReference getSTForStatement_Statements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement <em>ST While Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST While Statement</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement
	 * @generated
	 */
	EClass getSTWhileStatement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement#getCondition()
	 * @see #getSTWhileStatement()
	 * @generated
	 */
	EReference getSTWhileStatement_Condition();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STWhileStatement#getStatements()
	 * @see #getSTWhileStatement()
	 * @generated
	 */
	EReference getSTWhileStatement_Statements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement <em>ST Repeat Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Repeat Statement</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement
	 * @generated
	 */
	EClass getSTRepeatStatement();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement#getStatements()
	 * @see #getSTRepeatStatement()
	 * @generated
	 */
	EReference getSTRepeatStatement_Statements();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatStatement#getCondition()
	 * @see #getSTRepeatStatement()
	 * @generated
	 */
	EReference getSTRepeatStatement_Condition();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression <em>ST Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
	 * @generated
	 */
	EClass getSTExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral <em>ST Numeric Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Numeric Literal</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral
	 * @generated
	 */
	EClass getSTNumericLiteral();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral#getType()
	 * @see #getSTNumericLiteral()
	 * @generated
	 */
	EReference getSTNumericLiteral_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STNumericLiteral#getValue()
	 * @see #getSTNumericLiteral()
	 * @generated
	 */
	EAttribute getSTNumericLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral <em>ST Date Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Date Literal</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral
	 * @generated
	 */
	EClass getSTDateLiteral();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral#getType()
	 * @see #getSTDateLiteral()
	 * @generated
	 */
	EReference getSTDateLiteral_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateLiteral#getValue()
	 * @see #getSTDateLiteral()
	 * @generated
	 */
	EAttribute getSTDateLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral <em>ST Time Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Time Literal</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral
	 * @generated
	 */
	EClass getSTTimeLiteral();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral#getType()
	 * @see #getSTTimeLiteral()
	 * @generated
	 */
	EReference getSTTimeLiteral_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeLiteral#getValue()
	 * @see #getSTTimeLiteral()
	 * @generated
	 */
	EAttribute getSTTimeLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral <em>ST Time Of Day Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Time Of Day Literal</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral
	 * @generated
	 */
	EClass getSTTimeOfDayLiteral();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral#getType()
	 * @see #getSTTimeOfDayLiteral()
	 * @generated
	 */
	EReference getSTTimeOfDayLiteral_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTimeOfDayLiteral#getValue()
	 * @see #getSTTimeOfDayLiteral()
	 * @generated
	 */
	EAttribute getSTTimeOfDayLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral <em>ST Date And Time Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Date And Time Literal</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral
	 * @generated
	 */
	EClass getSTDateAndTimeLiteral();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral#getType()
	 * @see #getSTDateAndTimeLiteral()
	 * @generated
	 */
	EReference getSTDateAndTimeLiteral_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STDateAndTimeLiteral#getValue()
	 * @see #getSTDateAndTimeLiteral()
	 * @generated
	 */
	EAttribute getSTDateAndTimeLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral <em>ST String Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST String Literal</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral
	 * @generated
	 */
	EClass getSTStringLiteral();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral#getType()
	 * @see #getSTStringLiteral()
	 * @generated
	 */
	EReference getSTStringLiteral_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStringLiteral#getValue()
	 * @see #getSTStringLiteral()
	 * @generated
	 */
	EAttribute getSTStringLiteral_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration <em>ST Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration
	 * @generated
	 */
	EClass getSTVarDeclaration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getLocatedAt <em>Located At</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Located At</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getLocatedAt()
	 * @see #getSTVarDeclaration()
	 * @generated
	 */
	EReference getSTVarDeclaration_LocatedAt();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#isArray <em>Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#isArray()
	 * @see #getSTVarDeclaration()
	 * @generated
	 */
	EAttribute getSTVarDeclaration_Array();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ranges</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getRanges()
	 * @see #getSTVarDeclaration()
	 * @generated
	 */
	EReference getSTVarDeclaration_Ranges();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getCount <em>Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Count</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getCount()
	 * @see #getSTVarDeclaration()
	 * @generated
	 */
	EAttribute getSTVarDeclaration_Count();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getType()
	 * @see #getSTVarDeclaration()
	 * @generated
	 */
	EReference getSTVarDeclaration_Type();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getMaxLength <em>Max Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Length</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getMaxLength()
	 * @see #getSTVarDeclaration()
	 * @generated
	 */
	EReference getSTVarDeclaration_MaxLength();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getDefaultValue <em>Default Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Default Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getDefaultValue()
	 * @see #getSTVarDeclaration()
	 * @generated
	 */
	EReference getSTVarDeclaration_DefaultValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn <em>ST Return</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Return</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STReturn
	 * @generated
	 */
	EClass getSTReturn();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue <em>ST Continue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Continue</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STContinue
	 * @generated
	 */
	EClass getSTContinue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit <em>ST Exit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Exit</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STExit
	 * @generated
	 */
	EClass getSTExit();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop <em>ST Nop</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Nop</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STNop
	 * @generated
	 */
	EClass getSTNop();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression <em>ST Binary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Binary Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression
	 * @generated
	 */
	EClass getSTBinaryExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression#getLeft()
	 * @see #getSTBinaryExpression()
	 * @generated
	 */
	EReference getSTBinaryExpression_Left();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression#getOp()
	 * @see #getSTBinaryExpression()
	 * @generated
	 */
	EAttribute getSTBinaryExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryExpression#getRight()
	 * @see #getSTBinaryExpression()
	 * @generated
	 */
	EReference getSTBinaryExpression_Right();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression <em>ST Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Unary Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression
	 * @generated
	 */
	EClass getSTUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression#getOp <em>Op</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Op</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression#getOp()
	 * @see #getSTUnaryExpression()
	 * @generated
	 */
	EAttribute getSTUnaryExpression_Op();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryExpression#getExpression()
	 * @see #getSTUnaryExpression()
	 * @generated
	 */
	EReference getSTUnaryExpression_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression <em>ST Member Access Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Member Access Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression
	 * @generated
	 */
	EClass getSTMemberAccessExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression#getReceiver <em>Receiver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Receiver</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression#getReceiver()
	 * @see #getSTMemberAccessExpression()
	 * @generated
	 */
	EReference getSTMemberAccessExpression_Receiver();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Member</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMemberAccessExpression#getMember()
	 * @see #getSTMemberAccessExpression()
	 * @generated
	 */
	EReference getSTMemberAccessExpression_Member();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression <em>ST Array Access Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Array Access Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression
	 * @generated
	 */
	EClass getSTArrayAccessExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression#getReceiver <em>Receiver</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Receiver</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression#getReceiver()
	 * @see #getSTArrayAccessExpression()
	 * @generated
	 */
	EReference getSTArrayAccessExpression_Receiver();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Index</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayAccessExpression#getIndex()
	 * @see #getSTArrayAccessExpression()
	 * @generated
	 */
	EReference getSTArrayAccessExpression_Index();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression <em>ST Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Feature Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression
	 * @generated
	 */
	EClass getSTFeatureExpression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression#getFeature()
	 * @see #getSTFeatureExpression()
	 * @generated
	 */
	EReference getSTFeatureExpression_Feature();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression#isCall <em>Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Call</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression#isCall()
	 * @see #getSTFeatureExpression()
	 * @generated
	 */
	EAttribute getSTFeatureExpression_Call();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STFeatureExpression#getParameters()
	 * @see #getSTFeatureExpression()
	 * @generated
	 */
	EReference getSTFeatureExpression_Parameters();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression <em>ST Multibit Partial Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Multibit Partial Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression
	 * @generated
	 */
	EClass getSTMultibitPartialExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression#getSpecifier <em>Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Specifier</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression#getSpecifier()
	 * @see #getSTMultibitPartialExpression()
	 * @generated
	 */
	EAttribute getSTMultibitPartialExpression_Specifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression#getIndex()
	 * @see #getSTMultibitPartialExpression()
	 * @generated
	 */
	EAttribute getSTMultibitPartialExpression_Index();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultibitPartialExpression#getExpression()
	 * @see #getSTMultibitPartialExpression()
	 * @generated
	 */
	EReference getSTMultibitPartialExpression_Expression();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator <em>ST Binary Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ST Binary Operator</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
	 * @generated
	 */
	EEnum getSTBinaryOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator <em>ST Unary Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ST Unary Operator</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
	 * @generated
	 */
	EEnum getSTUnaryOperator();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier <em>ST Multi Bit Access Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ST Multi Bit Access Specifier</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier
	 * @generated
	 */
	EEnum getSTMultiBitAccessSpecifier();

	/**
	 * Returns the meta object for data type '{@link java.time.LocalDate <em>ST Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ST Date</em>'.
	 * @see java.time.LocalDate
	 * @model instanceClass="java.time.LocalDate"
	 * @generated
	 */
	EDataType getSTDate();

	/**
	 * Returns the meta object for data type '{@link java.time.Duration <em>ST Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ST Time</em>'.
	 * @see java.time.Duration
	 * @model instanceClass="java.time.Duration"
	 * @generated
	 */
	EDataType getSTTime();

	/**
	 * Returns the meta object for data type '{@link java.time.LocalTime <em>ST Time Of Day</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ST Time Of Day</em>'.
	 * @see java.time.LocalTime
	 * @model instanceClass="java.time.LocalTime"
	 * @generated
	 */
	EDataType getSTTimeOfDay();

	/**
	 * Returns the meta object for data type '{@link java.time.LocalDateTime <em>ST Date And Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ST Date And Time</em>'.
	 * @see java.time.LocalDateTime
	 * @model instanceClass="java.time.LocalDateTime"
	 * @generated
	 */
	EDataType getSTDateAndTime();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STString <em>ST String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ST String</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STString
	 * @model instanceClass="org.eclipse.fordiac.ide.structuredtextcore.stcore.STString"
	 * @generated
	 */
	EDataType getSTString();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	STCoreFactory getSTCoreFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STSourceImpl <em>ST Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STSourceImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTSource()
		 * @generated
		 */
		EClass ST_SOURCE = eINSTANCE.getSTSource();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCoreSourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCoreSourceImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCoreSource()
		 * @generated
		 */
		EClass ST_CORE_SOURCE = eINSTANCE.getSTCoreSource();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CORE_SOURCE__STATEMENTS = eINSTANCE.getSTCoreSource_Statements();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationBlockImpl <em>ST Var Declaration Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationBlockImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarDeclarationBlock()
		 * @generated
		 */
		EClass ST_VAR_DECLARATION_BLOCK = eINSTANCE.getSTVarDeclarationBlock();

		/**
		 * The meta object literal for the '<em><b>Constant</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_VAR_DECLARATION_BLOCK__CONSTANT = eINSTANCE.getSTVarDeclarationBlock_Constant();

		/**
		 * The meta object literal for the '<em><b>Var Declarations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_VAR_DECLARATION_BLOCK__VAR_DECLARATIONS = eINSTANCE.getSTVarDeclarationBlock_VarDeclarations();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarPlainDeclarationBlockImpl <em>ST Var Plain Declaration Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarPlainDeclarationBlockImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarPlainDeclarationBlock()
		 * @generated
		 */
		EClass ST_VAR_PLAIN_DECLARATION_BLOCK = eINSTANCE.getSTVarPlainDeclarationBlock();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarInputDeclarationBlockImpl <em>ST Var Input Declaration Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarInputDeclarationBlockImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarInputDeclarationBlock()
		 * @generated
		 */
		EClass ST_VAR_INPUT_DECLARATION_BLOCK = eINSTANCE.getSTVarInputDeclarationBlock();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarOutputDeclarationBlockImpl <em>ST Var Output Declaration Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarOutputDeclarationBlockImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarOutputDeclarationBlock()
		 * @generated
		 */
		EClass ST_VAR_OUTPUT_DECLARATION_BLOCK = eINSTANCE.getSTVarOutputDeclarationBlock();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarInOutDeclarationBlockImpl <em>ST Var In Out Declaration Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarInOutDeclarationBlockImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarInOutDeclarationBlock()
		 * @generated
		 */
		EClass ST_VAR_IN_OUT_DECLARATION_BLOCK = eINSTANCE.getSTVarInOutDeclarationBlock();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarTempDeclarationBlockImpl <em>ST Var Temp Declaration Block</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarTempDeclarationBlockImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarTempDeclarationBlock()
		 * @generated
		 */
		EClass ST_VAR_TEMP_DECLARATION_BLOCK = eINSTANCE.getSTVarTempDeclarationBlock();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STInitializerExpressionImpl <em>ST Initializer Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STInitializerExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTInitializerExpression()
		 * @generated
		 */
		EClass ST_INITIALIZER_EXPRESSION = eINSTANCE.getSTInitializerExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElementaryInitializerExpressionImpl <em>ST Elementary Initializer Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElementaryInitializerExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTElementaryInitializerExpression()
		 * @generated
		 */
		EClass ST_ELEMENTARY_INITIALIZER_EXPRESSION = eINSTANCE.getSTElementaryInitializerExpression();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ELEMENTARY_INITIALIZER_EXPRESSION__VALUE = eINSTANCE.getSTElementaryInitializerExpression_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayInitializerExpressionImpl <em>ST Array Initializer Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayInitializerExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTArrayInitializerExpression()
		 * @generated
		 */
		EClass ST_ARRAY_INITIALIZER_EXPRESSION = eINSTANCE.getSTArrayInitializerExpression();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ARRAY_INITIALIZER_EXPRESSION__VALUES = eINSTANCE.getSTArrayInitializerExpression_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayInitElementImpl <em>ST Array Init Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayInitElementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTArrayInitElement()
		 * @generated
		 */
		EClass ST_ARRAY_INIT_ELEMENT = eINSTANCE.getSTArrayInitElement();

		/**
		 * The meta object literal for the '<em><b>Index Or Init Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ARRAY_INIT_ELEMENT__INDEX_OR_INIT_EXPRESSION = eINSTANCE.getSTArrayInitElement_IndexOrInitExpression();

		/**
		 * The meta object literal for the '<em><b>Init Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS = eINSTANCE.getSTArrayInitElement_InitExpressions();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStatementImpl <em>ST Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStatementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStatement()
		 * @generated
		 */
		EClass ST_STATEMENT = eINSTANCE.getSTStatement();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAssignmentStatementImpl <em>ST Assignment Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAssignmentStatementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTAssignmentStatement()
		 * @generated
		 */
		EClass ST_ASSIGNMENT_STATEMENT = eINSTANCE.getSTAssignmentStatement();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ASSIGNMENT_STATEMENT__LEFT = eINSTANCE.getSTAssignmentStatement_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ASSIGNMENT_STATEMENT__RIGHT = eINSTANCE.getSTAssignmentStatement_Right();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallStatementImpl <em>ST Call Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallStatementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallStatement()
		 * @generated
		 */
		EClass ST_CALL_STATEMENT = eINSTANCE.getSTCallStatement();

		/**
		 * The meta object literal for the '<em><b>Call</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CALL_STATEMENT__CALL = eINSTANCE.getSTCallStatement_Call();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallArgumentImpl <em>ST Call Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallArgumentImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallArgument()
		 * @generated
		 */
		EClass ST_CALL_ARGUMENT = eINSTANCE.getSTCallArgument();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallUnnamedArgumentImpl <em>ST Call Unnamed Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallUnnamedArgumentImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallUnnamedArgument()
		 * @generated
		 */
		EClass ST_CALL_UNNAMED_ARGUMENT = eINSTANCE.getSTCallUnnamedArgument();

		/**
		 * The meta object literal for the '<em><b>Arg</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CALL_UNNAMED_ARGUMENT__ARG = eINSTANCE.getSTCallUnnamedArgument_Arg();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedInputArgumentImpl <em>ST Call Named Input Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedInputArgumentImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallNamedInputArgument()
		 * @generated
		 */
		EClass ST_CALL_NAMED_INPUT_ARGUMENT = eINSTANCE.getSTCallNamedInputArgument();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CALL_NAMED_INPUT_ARGUMENT__TARGET = eINSTANCE.getSTCallNamedInputArgument_Target();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CALL_NAMED_INPUT_ARGUMENT__SOURCE = eINSTANCE.getSTCallNamedInputArgument_Source();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedOutputArgumentImpl <em>ST Call Named Output Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedOutputArgumentImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallNamedOutputArgument()
		 * @generated
		 */
		EClass ST_CALL_NAMED_OUTPUT_ARGUMENT = eINSTANCE.getSTCallNamedOutputArgument();

		/**
		 * The meta object literal for the '<em><b>Not</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_CALL_NAMED_OUTPUT_ARGUMENT__NOT = eINSTANCE.getSTCallNamedOutputArgument_Not();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CALL_NAMED_OUTPUT_ARGUMENT__SOURCE = eINSTANCE.getSTCallNamedOutputArgument_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CALL_NAMED_OUTPUT_ARGUMENT__TARGET = eINSTANCE.getSTCallNamedOutputArgument_Target();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STIfStatementImpl <em>ST If Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STIfStatementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTIfStatement()
		 * @generated
		 */
		EClass ST_IF_STATEMENT = eINSTANCE.getSTIfStatement();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_IF_STATEMENT__CONDITION = eINSTANCE.getSTIfStatement_Condition();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_IF_STATEMENT__STATEMENTS = eINSTANCE.getSTIfStatement_Statements();

		/**
		 * The meta object literal for the '<em><b>Elseifs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_IF_STATEMENT__ELSEIFS = eINSTANCE.getSTIfStatement_Elseifs();

		/**
		 * The meta object literal for the '<em><b>Else</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_IF_STATEMENT__ELSE = eINSTANCE.getSTIfStatement_Else();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElseIfPartImpl <em>ST Else If Part</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElseIfPartImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTElseIfPart()
		 * @generated
		 */
		EClass ST_ELSE_IF_PART = eINSTANCE.getSTElseIfPart();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ELSE_IF_PART__CONDITION = eINSTANCE.getSTElseIfPart_Condition();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ELSE_IF_PART__STATEMENTS = eINSTANCE.getSTElseIfPart_Statements();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseStatementImpl <em>ST Case Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseStatementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCaseStatement()
		 * @generated
		 */
		EClass ST_CASE_STATEMENT = eINSTANCE.getSTCaseStatement();

		/**
		 * The meta object literal for the '<em><b>Selector</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CASE_STATEMENT__SELECTOR = eINSTANCE.getSTCaseStatement_Selector();

		/**
		 * The meta object literal for the '<em><b>Cases</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CASE_STATEMENT__CASES = eINSTANCE.getSTCaseStatement_Cases();

		/**
		 * The meta object literal for the '<em><b>Else</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CASE_STATEMENT__ELSE = eINSTANCE.getSTCaseStatement_Else();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseCasesImpl <em>ST Case Cases</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseCasesImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCaseCases()
		 * @generated
		 */
		EClass ST_CASE_CASES = eINSTANCE.getSTCaseCases();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CASE_CASES__CONDITIONS = eINSTANCE.getSTCaseCases_Conditions();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CASE_CASES__STATEMENTS = eINSTANCE.getSTCaseCases_Statements();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElsePartImpl <em>ST Else Part</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElsePartImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTElsePart()
		 * @generated
		 */
		EClass ST_ELSE_PART = eINSTANCE.getSTElsePart();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ELSE_PART__STATEMENTS = eINSTANCE.getSTElsePart_Statements();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STForStatementImpl <em>ST For Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STForStatementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTForStatement()
		 * @generated
		 */
		EClass ST_FOR_STATEMENT = eINSTANCE.getSTForStatement();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FOR_STATEMENT__VARIABLE = eINSTANCE.getSTForStatement_Variable();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FOR_STATEMENT__FROM = eINSTANCE.getSTForStatement_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FOR_STATEMENT__TO = eINSTANCE.getSTForStatement_To();

		/**
		 * The meta object literal for the '<em><b>By</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FOR_STATEMENT__BY = eINSTANCE.getSTForStatement_By();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FOR_STATEMENT__STATEMENTS = eINSTANCE.getSTForStatement_Statements();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STWhileStatementImpl <em>ST While Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STWhileStatementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTWhileStatement()
		 * @generated
		 */
		EClass ST_WHILE_STATEMENT = eINSTANCE.getSTWhileStatement();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_WHILE_STATEMENT__CONDITION = eINSTANCE.getSTWhileStatement_Condition();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_WHILE_STATEMENT__STATEMENTS = eINSTANCE.getSTWhileStatement_Statements();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatStatementImpl <em>ST Repeat Statement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatStatementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTRepeatStatement()
		 * @generated
		 */
		EClass ST_REPEAT_STATEMENT = eINSTANCE.getSTRepeatStatement();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_REPEAT_STATEMENT__STATEMENTS = eINSTANCE.getSTRepeatStatement_Statements();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_REPEAT_STATEMENT__CONDITION = eINSTANCE.getSTRepeatStatement_Condition();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExpressionImpl <em>ST Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTExpression()
		 * @generated
		 */
		EClass ST_EXPRESSION = eINSTANCE.getSTExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STNumericLiteralImpl <em>ST Numeric Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STNumericLiteralImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTNumericLiteral()
		 * @generated
		 */
		EClass ST_NUMERIC_LITERAL = eINSTANCE.getSTNumericLiteral();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_NUMERIC_LITERAL__TYPE = eINSTANCE.getSTNumericLiteral_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_NUMERIC_LITERAL__VALUE = eINSTANCE.getSTNumericLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateLiteralImpl <em>ST Date Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateLiteralImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTDateLiteral()
		 * @generated
		 */
		EClass ST_DATE_LITERAL = eINSTANCE.getSTDateLiteral();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_DATE_LITERAL__TYPE = eINSTANCE.getSTDateLiteral_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_DATE_LITERAL__VALUE = eINSTANCE.getSTDateLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTimeLiteralImpl <em>ST Time Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTimeLiteralImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTimeLiteral()
		 * @generated
		 */
		EClass ST_TIME_LITERAL = eINSTANCE.getSTTimeLiteral();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_TIME_LITERAL__TYPE = eINSTANCE.getSTTimeLiteral_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_TIME_LITERAL__VALUE = eINSTANCE.getSTTimeLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTimeOfDayLiteralImpl <em>ST Time Of Day Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTimeOfDayLiteralImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTimeOfDayLiteral()
		 * @generated
		 */
		EClass ST_TIME_OF_DAY_LITERAL = eINSTANCE.getSTTimeOfDayLiteral();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_TIME_OF_DAY_LITERAL__TYPE = eINSTANCE.getSTTimeOfDayLiteral_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_TIME_OF_DAY_LITERAL__VALUE = eINSTANCE.getSTTimeOfDayLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateAndTimeLiteralImpl <em>ST Date And Time Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STDateAndTimeLiteralImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTDateAndTimeLiteral()
		 * @generated
		 */
		EClass ST_DATE_AND_TIME_LITERAL = eINSTANCE.getSTDateAndTimeLiteral();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_DATE_AND_TIME_LITERAL__TYPE = eINSTANCE.getSTDateAndTimeLiteral_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_DATE_AND_TIME_LITERAL__VALUE = eINSTANCE.getSTDateAndTimeLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStringLiteralImpl <em>ST String Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStringLiteralImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStringLiteral()
		 * @generated
		 */
		EClass ST_STRING_LITERAL = eINSTANCE.getSTStringLiteral();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_STRING_LITERAL__TYPE = eINSTANCE.getSTStringLiteral_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_STRING_LITERAL__VALUE = eINSTANCE.getSTStringLiteral_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationImpl <em>ST Var Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarDeclaration()
		 * @generated
		 */
		EClass ST_VAR_DECLARATION = eINSTANCE.getSTVarDeclaration();

		/**
		 * The meta object literal for the '<em><b>Located At</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_VAR_DECLARATION__LOCATED_AT = eINSTANCE.getSTVarDeclaration_LocatedAt();

		/**
		 * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_VAR_DECLARATION__ARRAY = eINSTANCE.getSTVarDeclaration_Array();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_VAR_DECLARATION__RANGES = eINSTANCE.getSTVarDeclaration_Ranges();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_VAR_DECLARATION__COUNT = eINSTANCE.getSTVarDeclaration_Count();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_VAR_DECLARATION__TYPE = eINSTANCE.getSTVarDeclaration_Type();

		/**
		 * The meta object literal for the '<em><b>Max Length</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_VAR_DECLARATION__MAX_LENGTH = eINSTANCE.getSTVarDeclaration_MaxLength();

		/**
		 * The meta object literal for the '<em><b>Default Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_VAR_DECLARATION__DEFAULT_VALUE = eINSTANCE.getSTVarDeclaration_DefaultValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STReturnImpl <em>ST Return</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STReturnImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTReturn()
		 * @generated
		 */
		EClass ST_RETURN = eINSTANCE.getSTReturn();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STContinueImpl <em>ST Continue</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STContinueImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTContinue()
		 * @generated
		 */
		EClass ST_CONTINUE = eINSTANCE.getSTContinue();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExitImpl <em>ST Exit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExitImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTExit()
		 * @generated
		 */
		EClass ST_EXIT = eINSTANCE.getSTExit();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STNopImpl <em>ST Nop</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STNopImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTNop()
		 * @generated
		 */
		EClass ST_NOP = eINSTANCE.getSTNop();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STBinaryExpressionImpl <em>ST Binary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STBinaryExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTBinaryExpression()
		 * @generated
		 */
		EClass ST_BINARY_EXPRESSION = eINSTANCE.getSTBinaryExpression();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_BINARY_EXPRESSION__LEFT = eINSTANCE.getSTBinaryExpression_Left();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_BINARY_EXPRESSION__OP = eINSTANCE.getSTBinaryExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_BINARY_EXPRESSION__RIGHT = eINSTANCE.getSTBinaryExpression_Right();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STUnaryExpressionImpl <em>ST Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STUnaryExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTUnaryExpression()
		 * @generated
		 */
		EClass ST_UNARY_EXPRESSION = eINSTANCE.getSTUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Op</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_UNARY_EXPRESSION__OP = eINSTANCE.getSTUnaryExpression_Op();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_UNARY_EXPRESSION__EXPRESSION = eINSTANCE.getSTUnaryExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMemberAccessExpressionImpl <em>ST Member Access Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMemberAccessExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTMemberAccessExpression()
		 * @generated
		 */
		EClass ST_MEMBER_ACCESS_EXPRESSION = eINSTANCE.getSTMemberAccessExpression();

		/**
		 * The meta object literal for the '<em><b>Receiver</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_MEMBER_ACCESS_EXPRESSION__RECEIVER = eINSTANCE.getSTMemberAccessExpression_Receiver();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_MEMBER_ACCESS_EXPRESSION__MEMBER = eINSTANCE.getSTMemberAccessExpression_Member();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayAccessExpressionImpl <em>ST Array Access Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STArrayAccessExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTArrayAccessExpression()
		 * @generated
		 */
		EClass ST_ARRAY_ACCESS_EXPRESSION = eINSTANCE.getSTArrayAccessExpression();

		/**
		 * The meta object literal for the '<em><b>Receiver</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ARRAY_ACCESS_EXPRESSION__RECEIVER = eINSTANCE.getSTArrayAccessExpression_Receiver();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ARRAY_ACCESS_EXPRESSION__INDEX = eINSTANCE.getSTArrayAccessExpression_Index();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STFeatureExpressionImpl <em>ST Feature Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STFeatureExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTFeatureExpression()
		 * @generated
		 */
		EClass ST_FEATURE_EXPRESSION = eINSTANCE.getSTFeatureExpression();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FEATURE_EXPRESSION__FEATURE = eINSTANCE.getSTFeatureExpression_Feature();

		/**
		 * The meta object literal for the '<em><b>Call</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_FEATURE_EXPRESSION__CALL = eINSTANCE.getSTFeatureExpression_Call();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_FEATURE_EXPRESSION__PARAMETERS = eINSTANCE.getSTFeatureExpression_Parameters();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMultibitPartialExpressionImpl <em>ST Multibit Partial Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMultibitPartialExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTMultibitPartialExpression()
		 * @generated
		 */
		EClass ST_MULTIBIT_PARTIAL_EXPRESSION = eINSTANCE.getSTMultibitPartialExpression();

		/**
		 * The meta object literal for the '<em><b>Specifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_MULTIBIT_PARTIAL_EXPRESSION__SPECIFIER = eINSTANCE.getSTMultibitPartialExpression_Specifier();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_MULTIBIT_PARTIAL_EXPRESSION__INDEX = eINSTANCE.getSTMultibitPartialExpression_Index();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_MULTIBIT_PARTIAL_EXPRESSION__EXPRESSION = eINSTANCE.getSTMultibitPartialExpression_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator <em>ST Binary Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTBinaryOperator()
		 * @generated
		 */
		EEnum ST_BINARY_OPERATOR = eINSTANCE.getSTBinaryOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator <em>ST Unary Operator</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTUnaryOperator()
		 * @generated
		 */
		EEnum ST_UNARY_OPERATOR = eINSTANCE.getSTUnaryOperator();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier <em>ST Multi Bit Access Specifier</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTMultiBitAccessSpecifier()
		 * @generated
		 */
		EEnum ST_MULTI_BIT_ACCESS_SPECIFIER = eINSTANCE.getSTMultiBitAccessSpecifier();

		/**
		 * The meta object literal for the '<em>ST Date</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.time.LocalDate
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTDate()
		 * @generated
		 */
		EDataType ST_DATE = eINSTANCE.getSTDate();

		/**
		 * The meta object literal for the '<em>ST Time</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.time.Duration
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTime()
		 * @generated
		 */
		EDataType ST_TIME = eINSTANCE.getSTTime();

		/**
		 * The meta object literal for the '<em>ST Time Of Day</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.time.LocalTime
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTimeOfDay()
		 * @generated
		 */
		EDataType ST_TIME_OF_DAY = eINSTANCE.getSTTimeOfDay();

		/**
		 * The meta object literal for the '<em>ST Date And Time</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.time.LocalDateTime
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTDateAndTime()
		 * @generated
		 */
		EDataType ST_DATE_AND_TIME = eINSTANCE.getSTDateAndTime();

		/**
		 * The meta object literal for the '<em>ST String</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STString
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTString()
		 * @generated
		 */
		EDataType ST_STRING = eINSTANCE.getSTString();

	}

} //STCorePackage
