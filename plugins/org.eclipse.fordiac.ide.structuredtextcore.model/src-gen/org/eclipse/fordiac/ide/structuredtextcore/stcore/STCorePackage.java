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
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_SOURCE__COMMENTS = 0;

	/**
	 * The number of structural features of the '<em>ST Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_SOURCE_FEATURE_COUNT = 1;

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
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CORE_SOURCE__COMMENTS = ST_SOURCE__COMMENTS;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STImportImpl <em>ST Import</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STImportImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTImport()
	 * @generated
	 */
	int ST_IMPORT = 2;

	/**
	 * The feature id for the '<em><b>Imported Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_IMPORT__IMPORTED_NAMESPACE = LibraryElementPackage.IMPORT__IMPORTED_NAMESPACE;

	/**
	 * The number of structural features of the '<em>ST Import</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_IMPORT_FEATURE_COUNT = LibraryElementPackage.IMPORT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationBlockImpl <em>ST Var Declaration Block</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationBlockImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarDeclarationBlock()
	 * @generated
	 */
	int ST_VAR_DECLARATION_BLOCK = 3;

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
	int ST_VAR_PLAIN_DECLARATION_BLOCK = 4;

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
	int ST_VAR_INPUT_DECLARATION_BLOCK = 5;

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
	int ST_VAR_OUTPUT_DECLARATION_BLOCK = 6;

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
	int ST_VAR_IN_OUT_DECLARATION_BLOCK = 7;

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
	int ST_VAR_TEMP_DECLARATION_BLOCK = 8;

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
	int ST_INITIALIZER_EXPRESSION = 9;

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
	int ST_ELEMENTARY_INITIALIZER_EXPRESSION = 10;

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
	int ST_ARRAY_INITIALIZER_EXPRESSION = 11;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement <em>ST Array Init Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTArrayInitElement()
	 * @generated
	 */
	int ST_ARRAY_INIT_ELEMENT = 12;

	/**
	 * The number of structural features of the '<em>ST Array Init Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ARRAY_INIT_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STSingleArrayInitElementImpl <em>ST Single Array Init Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STSingleArrayInitElementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTSingleArrayInitElement()
	 * @generated
	 */
	int ST_SINGLE_ARRAY_INIT_ELEMENT = 13;

	/**
	 * The feature id for the '<em><b>Init Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_SINGLE_ARRAY_INIT_ELEMENT__INIT_EXPRESSION = ST_ARRAY_INIT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ST Single Array Init Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_SINGLE_ARRAY_INIT_ELEMENT_FEATURE_COUNT = ST_ARRAY_INIT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatArrayInitElementImpl <em>ST Repeat Array Init Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatArrayInitElementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTRepeatArrayInitElement()
	 * @generated
	 */
	int ST_REPEAT_ARRAY_INIT_ELEMENT = 14;

	/**
	 * The feature id for the '<em><b>Repetitions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_REPEAT_ARRAY_INIT_ELEMENT__REPETITIONS = ST_ARRAY_INIT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Init Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_REPEAT_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS = ST_ARRAY_INIT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Repeat Array Init Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_REPEAT_ARRAY_INIT_ELEMENT_FEATURE_COUNT = ST_ARRAY_INIT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STPragmaImpl <em>ST Pragma</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STPragmaImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTPragma()
	 * @generated
	 */
	int ST_PRAGMA = 15;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_PRAGMA__ATTRIBUTES = 0;

	/**
	 * The number of structural features of the '<em>ST Pragma</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_PRAGMA_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAttributeImpl <em>ST Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAttributeImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTAttribute()
	 * @generated
	 */
	int ST_ATTRIBUTE = 16;

	/**
	 * The feature id for the '<em><b>Declaration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ATTRIBUTE__DECLARATION = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ATTRIBUTE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>ST Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ATTRIBUTE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStatementImpl <em>ST Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStatement()
	 * @generated
	 */
	int ST_STATEMENT = 17;

	/**
	 * The number of structural features of the '<em>ST Statement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STATEMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallArgumentImpl <em>ST Call Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallArgumentImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallArgument()
	 * @generated
	 */
	int ST_CALL_ARGUMENT = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallUnnamedArgumentImpl <em>ST Call Unnamed Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallUnnamedArgumentImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallUnnamedArgument()
	 * @generated
	 */
	int ST_CALL_UNNAMED_ARGUMENT = 20;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedInputArgumentImpl <em>ST Call Named Input Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedInputArgumentImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallNamedInputArgument()
	 * @generated
	 */
	int ST_CALL_NAMED_INPUT_ARGUMENT = 21;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedOutputArgumentImpl <em>ST Call Named Output Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedOutputArgumentImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallNamedOutputArgument()
	 * @generated
	 */
	int ST_CALL_NAMED_OUTPUT_ARGUMENT = 22;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STIfStatementImpl <em>ST If Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STIfStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTIfStatement()
	 * @generated
	 */
	int ST_IF_STATEMENT = 23;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElseIfPartImpl <em>ST Else If Part</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElseIfPartImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTElseIfPart()
	 * @generated
	 */
	int ST_ELSE_IF_PART = 24;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseStatementImpl <em>ST Case Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCaseStatement()
	 * @generated
	 */
	int ST_CASE_STATEMENT = 25;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseCasesImpl <em>ST Case Cases</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCaseCasesImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCaseCases()
	 * @generated
	 */
	int ST_CASE_CASES = 26;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElsePartImpl <em>ST Else Part</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STElsePartImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTElsePart()
	 * @generated
	 */
	int ST_ELSE_PART = 27;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STForStatementImpl <em>ST For Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STForStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTForStatement()
	 * @generated
	 */
	int ST_FOR_STATEMENT = 28;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STWhileStatementImpl <em>ST While Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STWhileStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTWhileStatement()
	 * @generated
	 */
	int ST_WHILE_STATEMENT = 29;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatStatementImpl <em>ST Repeat Statement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatStatementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTRepeatStatement()
	 * @generated
	 */
	int ST_REPEAT_STATEMENT = 30;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExpressionImpl <em>ST Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTExpression()
	 * @generated
	 */
	int ST_EXPRESSION = 31;

	/**
	 * The number of structural features of the '<em>ST Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_EXPRESSION_FEATURE_COUNT = ST_STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAssignmentImpl <em>ST Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAssignmentImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTAssignment()
	 * @generated
	 */
	int ST_ASSIGNMENT = 18;

	/**
	 * The feature id for the '<em><b>Left</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ASSIGNMENT__LEFT = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Right</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ASSIGNMENT__RIGHT = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ASSIGNMENT_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Argument</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_ARGUMENT__ARGUMENT = 0;

	/**
	 * The number of structural features of the '<em>ST Call Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_ARGUMENT_FEATURE_COUNT = 1;

	/**
	 * The feature id for the '<em><b>Argument</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_UNNAMED_ARGUMENT__ARGUMENT = ST_CALL_ARGUMENT__ARGUMENT;

	/**
	 * The number of structural features of the '<em>ST Call Unnamed Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_UNNAMED_ARGUMENT_FEATURE_COUNT = ST_CALL_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Argument</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_INPUT_ARGUMENT__ARGUMENT = ST_CALL_ARGUMENT__ARGUMENT;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_INPUT_ARGUMENT__PARAMETER = ST_CALL_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ST Call Named Input Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_INPUT_ARGUMENT_FEATURE_COUNT = ST_CALL_ARGUMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Argument</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_OUTPUT_ARGUMENT__ARGUMENT = ST_CALL_ARGUMENT__ARGUMENT;

	/**
	 * The feature id for the '<em><b>Not</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_OUTPUT_ARGUMENT__NOT = ST_CALL_ARGUMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_OUTPUT_ARGUMENT__PARAMETER = ST_CALL_ARGUMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Call Named Output Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CALL_NAMED_OUTPUT_ARGUMENT_FEATURE_COUNT = ST_CALL_ARGUMENT_FEATURE_COUNT + 2;

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
	 * The feature id for the '<em><b>Statement</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CASE_CASES__STATEMENT = 2;

	/**
	 * The number of structural features of the '<em>ST Case Cases</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_CASE_CASES_FEATURE_COUNT = 3;

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
	 * The feature id for the '<em><b>Variable</b></em>' containment reference.
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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STNumericLiteralImpl <em>ST Numeric Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STNumericLiteralImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTNumericLiteral()
	 * @generated
	 */
	int ST_NUMERIC_LITERAL = 32;

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
	int ST_DATE_LITERAL = 33;

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
	int ST_TIME_LITERAL = 34;

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
	int ST_TIME_OF_DAY_LITERAL = 35;

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
	int ST_DATE_AND_TIME_LITERAL = 36;

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
	int ST_STRING_LITERAL = 37;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STEnumLiteralImpl <em>ST Enum Literal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STEnumLiteralImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTEnumLiteral()
	 * @generated
	 */
	int ST_ENUM_LITERAL = 38;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ENUM_LITERAL__VALUE = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ST Enum Literal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_ENUM_LITERAL_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationImpl <em>ST Var Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STVarDeclarationImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTVarDeclaration()
	 * @generated
	 */
	int ST_VAR_DECLARATION = 39;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__NAME = LibraryElementPackage.ITYPED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__COMMENT = LibraryElementPackage.ITYPED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Located At</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__LOCATED_AT = LibraryElementPackage.ITYPED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__ARRAY = LibraryElementPackage.ITYPED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__RANGES = LibraryElementPackage.ITYPED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__COUNT = LibraryElementPackage.ITYPED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__TYPE = LibraryElementPackage.ITYPED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__MAX_LENGTH = LibraryElementPackage.ITYPED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Default Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__DEFAULT_VALUE = LibraryElementPackage.ITYPED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Pragma</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION__PRAGMA = LibraryElementPackage.ITYPED_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>ST Var Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_VAR_DECLARATION_FEATURE_COUNT = LibraryElementPackage.ITYPED_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTypeDeclarationImpl <em>ST Type Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTypeDeclarationImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTypeDeclaration()
	 * @generated
	 */
	int ST_TYPE_DECLARATION = 40;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TYPE_DECLARATION__ARRAY = 0;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TYPE_DECLARATION__RANGES = 1;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TYPE_DECLARATION__COUNT = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TYPE_DECLARATION__TYPE = 3;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TYPE_DECLARATION__MAX_LENGTH = 4;

	/**
	 * The number of structural features of the '<em>ST Type Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_TYPE_DECLARATION_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STReturnImpl <em>ST Return</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STReturnImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTReturn()
	 * @generated
	 */
	int ST_RETURN = 41;

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
	int ST_CONTINUE = 42;

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
	int ST_EXIT = 43;

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
	int ST_NOP = 44;

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
	int ST_BINARY_EXPRESSION = 45;

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
	int ST_UNARY_EXPRESSION = 46;

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
	int ST_MEMBER_ACCESS_EXPRESSION = 47;

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
	int ST_ARRAY_ACCESS_EXPRESSION = 48;

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
	int ST_FEATURE_EXPRESSION = 49;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STBuiltinFeatureExpressionImpl <em>ST Builtin Feature Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STBuiltinFeatureExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTBuiltinFeatureExpression()
	 * @generated
	 */
	int ST_BUILTIN_FEATURE_EXPRESSION = 50;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_BUILTIN_FEATURE_EXPRESSION__FEATURE = ST_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Call</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_BUILTIN_FEATURE_EXPRESSION__CALL = ST_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_BUILTIN_FEATURE_EXPRESSION__PARAMETERS = ST_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>ST Builtin Feature Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_BUILTIN_FEATURE_EXPRESSION_FEATURE_COUNT = ST_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMultibitPartialExpressionImpl <em>ST Multibit Partial Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STMultibitPartialExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTMultibitPartialExpression()
	 * @generated
	 */
	int ST_MULTIBIT_PARTIAL_EXPRESSION = 51;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStandardFunctionImpl <em>ST Standard Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStandardFunctionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStandardFunction()
	 * @generated
	 */
	int ST_STANDARD_FUNCTION = 52;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__NAME = LibraryElementPackage.ICALLABLE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__COMMENT = LibraryElementPackage.ICALLABLE__COMMENT;

	/**
	 * The feature id for the '<em><b>Return Value Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__RETURN_VALUE_COMMENT = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Signature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__SIGNATURE = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Return Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__RETURN_TYPE = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Input Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__INPUT_PARAMETERS = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Output Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__OUTPUT_PARAMETERS = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>In Out Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__IN_OUT_PARAMETERS = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Varargs</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__VARARGS = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Only Supported By</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__ONLY_SUPPORTED_BY = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Java Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION__JAVA_METHOD = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>ST Standard Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STANDARD_FUNCTION_FEATURE_COUNT = LibraryElementPackage.ICALLABLE_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCommentImpl <em>ST Comment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCommentImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTComment()
	 * @generated
	 */
	int ST_COMMENT = 53;

	/**
	 * The feature id for the '<em><b>Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_COMMENT__CONTEXT = 0;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_COMMENT__TEXT = 1;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_COMMENT__POSITION = 2;

	/**
	 * The number of structural features of the '<em>ST Comment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_COMMENT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStructInitializerExpressionImpl <em>ST Struct Initializer Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStructInitializerExpressionImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStructInitializerExpression()
	 * @generated
	 */
	int ST_STRUCT_INITIALIZER_EXPRESSION = 54;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STRUCT_INITIALIZER_EXPRESSION__TYPE = ST_INITIALIZER_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STRUCT_INITIALIZER_EXPRESSION__VALUES = ST_INITIALIZER_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ST Struct Initializer Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STRUCT_INITIALIZER_EXPRESSION_FEATURE_COUNT = ST_INITIALIZER_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStructInitElementImpl <em>ST Struct Init Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStructInitElementImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStructInitElement()
	 * @generated
	 */
	int ST_STRUCT_INIT_ELEMENT = 55;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STRUCT_INIT_ELEMENT__VARIABLE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STRUCT_INIT_ELEMENT__VALUE = 1;

	/**
	 * The number of structural features of the '<em>ST Struct Init Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_STRUCT_INIT_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExpressionSourceImpl <em>ST Expression Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExpressionSourceImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTExpressionSource()
	 * @generated
	 */
	int ST_EXPRESSION_SOURCE = 56;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_EXPRESSION_SOURCE__COMMENTS = ST_SOURCE__COMMENTS;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_EXPRESSION_SOURCE__EXPRESSION = ST_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ST Expression Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_EXPRESSION_SOURCE_FEATURE_COUNT = ST_SOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STInitializerExpressionSourceImpl <em>ST Initializer Expression Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STInitializerExpressionSourceImpl
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTInitializerExpressionSource()
	 * @generated
	 */
	int ST_INITIALIZER_EXPRESSION_SOURCE = 57;

	/**
	 * The feature id for the '<em><b>Comments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_INITIALIZER_EXPRESSION_SOURCE__COMMENTS = ST_SOURCE__COMMENTS;

	/**
	 * The feature id for the '<em><b>Initializer Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION = ST_SOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ST Initializer Expression Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ST_INITIALIZER_EXPRESSION_SOURCE_FEATURE_COUNT = ST_SOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator <em>ST Binary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBinaryOperator
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTBinaryOperator()
	 * @generated
	 */
	int ST_BINARY_OPERATOR = 58;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator <em>ST Unary Operator</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STUnaryOperator
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTUnaryOperator()
	 * @generated
	 */
	int ST_UNARY_OPERATOR = 59;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier <em>ST Multi Bit Access Specifier</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STMultiBitAccessSpecifier
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTMultiBitAccessSpecifier()
	 * @generated
	 */
	int ST_MULTI_BIT_ACCESS_SPECIFIER = 60;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeature <em>ST Builtin Feature</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeature
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTBuiltinFeature()
	 * @generated
	 */
	int ST_BUILTIN_FEATURE = 61;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAccessSpecifier <em>ST Access Specifier</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAccessSpecifier
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTAccessSpecifier()
	 * @generated
	 */
	int ST_ACCESS_SPECIFIER = 62;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCommentPosition <em>ST Comment Position</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCommentPosition
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCommentPosition()
	 * @generated
	 */
	int ST_COMMENT_POSITION = 63;

	/**
	 * The meta object id for the '<em>ST Date</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.time.LocalDate
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTDate()
	 * @generated
	 */
	int ST_DATE = 64;

	/**
	 * The meta object id for the '<em>ST Time</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.time.Duration
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTime()
	 * @generated
	 */
	int ST_TIME = 65;

	/**
	 * The meta object id for the '<em>ST Time Of Day</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.time.LocalTime
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTimeOfDay()
	 * @generated
	 */
	int ST_TIME_OF_DAY = 66;

	/**
	 * The meta object id for the '<em>ST Date And Time</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.time.LocalDateTime
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTDateAndTime()
	 * @generated
	 */
	int ST_DATE_AND_TIME = 67;

	/**
	 * The meta object id for the '<em>ST String</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STString
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTString()
	 * @generated
	 */
	int ST_STRING = 68;

	/**
	 * The meta object id for the '<em>ST Java Method</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.reflect.Method
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTJavaMethod()
	 * @generated
	 */
	int ST_JAVA_METHOD = 69;

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
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource#getComments <em>Comments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Comments</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource#getComments()
	 * @see #getSTSource()
	 * @generated
	 */
	EReference getSTSource_Comments();

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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport <em>ST Import</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Import</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STImport
	 * @generated
	 */
	EClass getSTImport();

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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement <em>ST Single Array Init Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Single Array Init Element</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement
	 * @generated
	 */
	EClass getSTSingleArrayInitElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement#getInitExpression <em>Init Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Init Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STSingleArrayInitElement#getInitExpression()
	 * @see #getSTSingleArrayInitElement()
	 * @generated
	 */
	EReference getSTSingleArrayInitElement_InitExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement <em>ST Repeat Array Init Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Repeat Array Init Element</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement
	 * @generated
	 */
	EClass getSTRepeatArrayInitElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement#getRepetitions <em>Repetitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Repetitions</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement#getRepetitions()
	 * @see #getSTRepeatArrayInitElement()
	 * @generated
	 */
	EAttribute getSTRepeatArrayInitElement_Repetitions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement#getInitExpressions <em>Init Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Init Expressions</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STRepeatArrayInitElement#getInitExpressions()
	 * @see #getSTRepeatArrayInitElement()
	 * @generated
	 */
	EReference getSTRepeatArrayInitElement_InitExpressions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STPragma <em>ST Pragma</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Pragma</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STPragma
	 * @generated
	 */
	EClass getSTPragma();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STPragma#getAttributes <em>Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attributes</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STPragma#getAttributes()
	 * @see #getSTPragma()
	 * @generated
	 */
	EReference getSTPragma_Attributes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute <em>ST Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute
	 * @generated
	 */
	EClass getSTAttribute();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute#getDeclaration <em>Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute#getDeclaration()
	 * @see #getSTAttribute()
	 * @generated
	 */
	EReference getSTAttribute_Declaration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAttribute#getValue()
	 * @see #getSTAttribute()
	 * @generated
	 */
	EReference getSTAttribute_Value();

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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment <em>ST Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Assignment</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment
	 * @generated
	 */
	EClass getSTAssignment();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment#getLeft <em>Left</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Left</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment#getLeft()
	 * @see #getSTAssignment()
	 * @generated
	 */
	EReference getSTAssignment_Left();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment#getRight <em>Right</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Right</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAssignment#getRight()
	 * @see #getSTAssignment()
	 * @generated
	 */
	EReference getSTAssignment_Right();

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
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument#getArgument <em>Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Argument</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallArgument#getArgument()
	 * @see #getSTCallArgument()
	 * @generated
	 */
	EReference getSTCallArgument_Argument();

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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument <em>ST Call Named Input Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Call Named Input Argument</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument
	 * @generated
	 */
	EClass getSTCallNamedInputArgument();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedInputArgument#getParameter()
	 * @see #getSTCallNamedInputArgument()
	 * @generated
	 */
	EReference getSTCallNamedInputArgument_Parameter();

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
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCallNamedOutputArgument#getParameter()
	 * @see #getSTCallNamedOutputArgument()
	 * @generated
	 */
	EReference getSTCallNamedOutputArgument_Parameter();

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
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases#getStatement <em>Statement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Statement</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCaseCases#getStatement()
	 * @see #getSTCaseCases()
	 * @generated
	 */
	EReference getSTCaseCases_Statement();

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
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STForStatement#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Variable</em>'.
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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral <em>ST Enum Literal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Enum Literal</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral
	 * @generated
	 */
	EClass getSTEnumLiteral();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STEnumLiteral#getValue()
	 * @see #getSTEnumLiteral()
	 * @generated
	 */
	EReference getSTEnumLiteral_Value();

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
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getPragma <em>Pragma</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pragma</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getPragma()
	 * @see #getSTVarDeclaration()
	 * @generated
	 */
	EReference getSTVarDeclaration_Pragma();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration <em>ST Type Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Type Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration
	 * @generated
	 */
	EClass getSTTypeDeclaration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration#isArray <em>Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration#isArray()
	 * @see #getSTTypeDeclaration()
	 * @generated
	 */
	EAttribute getSTTypeDeclaration_Array();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ranges</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration#getRanges()
	 * @see #getSTTypeDeclaration()
	 * @generated
	 */
	EReference getSTTypeDeclaration_Ranges();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration#getCount <em>Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Count</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration#getCount()
	 * @see #getSTTypeDeclaration()
	 * @generated
	 */
	EAttribute getSTTypeDeclaration_Count();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration#getType()
	 * @see #getSTTypeDeclaration()
	 * @generated
	 */
	EReference getSTTypeDeclaration_Type();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration#getMaxLength <em>Max Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Length</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration#getMaxLength()
	 * @see #getSTTypeDeclaration()
	 * @generated
	 */
	EReference getSTTypeDeclaration_MaxLength();

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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression <em>ST Builtin Feature Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Builtin Feature Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression
	 * @generated
	 */
	EClass getSTBuiltinFeatureExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression#getFeature()
	 * @see #getSTBuiltinFeatureExpression()
	 * @generated
	 */
	EAttribute getSTBuiltinFeatureExpression_Feature();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression#isCall <em>Call</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Call</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression#isCall()
	 * @see #getSTBuiltinFeatureExpression()
	 * @generated
	 */
	EAttribute getSTBuiltinFeatureExpression_Call();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeatureExpression#getParameters()
	 * @see #getSTBuiltinFeatureExpression()
	 * @generated
	 */
	EReference getSTBuiltinFeatureExpression_Parameters();

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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction <em>ST Standard Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Standard Function</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction
	 * @generated
	 */
	EClass getSTStandardFunction();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getReturnValueComment <em>Return Value Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Return Value Comment</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getReturnValueComment()
	 * @see #getSTStandardFunction()
	 * @generated
	 */
	EAttribute getSTStandardFunction_ReturnValueComment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getSignature <em>Signature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Signature</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getSignature()
	 * @see #getSTStandardFunction()
	 * @generated
	 */
	EAttribute getSTStandardFunction_Signature();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getReturnType <em>Return Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Return Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getReturnType()
	 * @see #getSTStandardFunction()
	 * @generated
	 */
	EReference getSTStandardFunction_ReturnType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getInputParameters <em>Input Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getInputParameters()
	 * @see #getSTStandardFunction()
	 * @generated
	 */
	EReference getSTStandardFunction_InputParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getOutputParameters <em>Output Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getOutputParameters()
	 * @see #getSTStandardFunction()
	 * @generated
	 */
	EReference getSTStandardFunction_OutputParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getInOutParameters <em>In Out Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>In Out Parameters</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getInOutParameters()
	 * @see #getSTStandardFunction()
	 * @generated
	 */
	EReference getSTStandardFunction_InOutParameters();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#isVarargs <em>Varargs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Varargs</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#isVarargs()
	 * @see #getSTStandardFunction()
	 * @generated
	 */
	EAttribute getSTStandardFunction_Varargs();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getOnlySupportedBy <em>Only Supported By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Only Supported By</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getOnlySupportedBy()
	 * @see #getSTStandardFunction()
	 * @generated
	 */
	EAttribute getSTStandardFunction_OnlySupportedBy();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getJavaMethod <em>Java Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Java Method</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStandardFunction#getJavaMethod()
	 * @see #getSTStandardFunction()
	 * @generated
	 */
	EAttribute getSTStandardFunction_JavaMethod();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment <em>ST Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Comment</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment
	 * @generated
	 */
	EClass getSTComment();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Context</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment#getContext()
	 * @see #getSTComment()
	 * @generated
	 */
	EReference getSTComment_Context();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment#getText()
	 * @see #getSTComment()
	 * @generated
	 */
	EAttribute getSTComment_Text();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Position</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STComment#getPosition()
	 * @see #getSTComment()
	 * @generated
	 */
	EAttribute getSTComment_Position();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression <em>ST Struct Initializer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Struct Initializer Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression
	 * @generated
	 */
	EClass getSTStructInitializerExpression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression#getType()
	 * @see #getSTStructInitializerExpression()
	 * @generated
	 */
	EReference getSTStructInitializerExpression_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitializerExpression#getValues()
	 * @see #getSTStructInitializerExpression()
	 * @generated
	 */
	EReference getSTStructInitializerExpression_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement <em>ST Struct Init Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Struct Init Element</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement
	 * @generated
	 */
	EClass getSTStructInitElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement#getVariable()
	 * @see #getSTStructInitElement()
	 * @generated
	 */
	EReference getSTStructInitElement_Variable();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STStructInitElement#getValue()
	 * @see #getSTStructInitElement()
	 * @generated
	 */
	EReference getSTStructInitElement_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource <em>ST Expression Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Expression Source</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource
	 * @generated
	 */
	EClass getSTExpressionSource();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource#getExpression()
	 * @see #getSTExpressionSource()
	 * @generated
	 */
	EReference getSTExpressionSource_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource <em>ST Initializer Expression Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ST Initializer Expression Source</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource
	 * @generated
	 */
	EClass getSTInitializerExpressionSource();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource#getInitializerExpression <em>Initializer Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Initializer Expression</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource#getInitializerExpression()
	 * @see #getSTInitializerExpressionSource()
	 * @generated
	 */
	EReference getSTInitializerExpressionSource_InitializerExpression();

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
	 * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeature <em>ST Builtin Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ST Builtin Feature</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeature
	 * @generated
	 */
	EEnum getSTBuiltinFeature();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAccessSpecifier <em>ST Access Specifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ST Access Specifier</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAccessSpecifier
	 * @generated
	 */
	EEnum getSTAccessSpecifier();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCommentPosition <em>ST Comment Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ST Comment Position</em>'.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCommentPosition
	 * @generated
	 */
	EEnum getSTCommentPosition();

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
	 * Returns the meta object for data type '{@link java.lang.reflect.Method <em>ST Java Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>ST Java Method</em>'.
	 * @see java.lang.reflect.Method
	 * @model instanceClass="java.lang.reflect.Method" serializeable="false"
	 * @generated
	 */
	EDataType getSTJavaMethod();

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
		 * The meta object literal for the '<em><b>Comments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_SOURCE__COMMENTS = eINSTANCE.getSTSource_Comments();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STImportImpl <em>ST Import</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STImportImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTImport()
		 * @generated
		 */
		EClass ST_IMPORT = eINSTANCE.getSTImport();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement <em>ST Array Init Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STArrayInitElement
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTArrayInitElement()
		 * @generated
		 */
		EClass ST_ARRAY_INIT_ELEMENT = eINSTANCE.getSTArrayInitElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STSingleArrayInitElementImpl <em>ST Single Array Init Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STSingleArrayInitElementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTSingleArrayInitElement()
		 * @generated
		 */
		EClass ST_SINGLE_ARRAY_INIT_ELEMENT = eINSTANCE.getSTSingleArrayInitElement();

		/**
		 * The meta object literal for the '<em><b>Init Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_SINGLE_ARRAY_INIT_ELEMENT__INIT_EXPRESSION = eINSTANCE.getSTSingleArrayInitElement_InitExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatArrayInitElementImpl <em>ST Repeat Array Init Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STRepeatArrayInitElementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTRepeatArrayInitElement()
		 * @generated
		 */
		EClass ST_REPEAT_ARRAY_INIT_ELEMENT = eINSTANCE.getSTRepeatArrayInitElement();

		/**
		 * The meta object literal for the '<em><b>Repetitions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_REPEAT_ARRAY_INIT_ELEMENT__REPETITIONS = eINSTANCE.getSTRepeatArrayInitElement_Repetitions();

		/**
		 * The meta object literal for the '<em><b>Init Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_REPEAT_ARRAY_INIT_ELEMENT__INIT_EXPRESSIONS = eINSTANCE.getSTRepeatArrayInitElement_InitExpressions();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STPragmaImpl <em>ST Pragma</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STPragmaImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTPragma()
		 * @generated
		 */
		EClass ST_PRAGMA = eINSTANCE.getSTPragma();

		/**
		 * The meta object literal for the '<em><b>Attributes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_PRAGMA__ATTRIBUTES = eINSTANCE.getSTPragma_Attributes();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAttributeImpl <em>ST Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAttributeImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTAttribute()
		 * @generated
		 */
		EClass ST_ATTRIBUTE = eINSTANCE.getSTAttribute();

		/**
		 * The meta object literal for the '<em><b>Declaration</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ATTRIBUTE__DECLARATION = eINSTANCE.getSTAttribute_Declaration();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ATTRIBUTE__VALUE = eINSTANCE.getSTAttribute_Value();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAssignmentImpl <em>ST Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STAssignmentImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTAssignment()
		 * @generated
		 */
		EClass ST_ASSIGNMENT = eINSTANCE.getSTAssignment();

		/**
		 * The meta object literal for the '<em><b>Left</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ASSIGNMENT__LEFT = eINSTANCE.getSTAssignment_Left();

		/**
		 * The meta object literal for the '<em><b>Right</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ASSIGNMENT__RIGHT = eINSTANCE.getSTAssignment_Right();

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
		 * The meta object literal for the '<em><b>Argument</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CALL_ARGUMENT__ARGUMENT = eINSTANCE.getSTCallArgument_Argument();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedInputArgumentImpl <em>ST Call Named Input Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCallNamedInputArgumentImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCallNamedInputArgument()
		 * @generated
		 */
		EClass ST_CALL_NAMED_INPUT_ARGUMENT = eINSTANCE.getSTCallNamedInputArgument();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CALL_NAMED_INPUT_ARGUMENT__PARAMETER = eINSTANCE.getSTCallNamedInputArgument_Parameter();

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
		 * The meta object literal for the '<em><b>Parameter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CALL_NAMED_OUTPUT_ARGUMENT__PARAMETER = eINSTANCE.getSTCallNamedOutputArgument_Parameter();

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
		 * The meta object literal for the '<em><b>Statement</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_CASE_CASES__STATEMENT = eINSTANCE.getSTCaseCases_Statement();

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
		 * The meta object literal for the '<em><b>Variable</b></em>' containment reference feature.
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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STEnumLiteralImpl <em>ST Enum Literal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STEnumLiteralImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTEnumLiteral()
		 * @generated
		 */
		EClass ST_ENUM_LITERAL = eINSTANCE.getSTEnumLiteral();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_ENUM_LITERAL__VALUE = eINSTANCE.getSTEnumLiteral_Value();

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
		 * The meta object literal for the '<em><b>Pragma</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_VAR_DECLARATION__PRAGMA = eINSTANCE.getSTVarDeclaration_Pragma();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTypeDeclarationImpl <em>ST Type Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STTypeDeclarationImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTTypeDeclaration()
		 * @generated
		 */
		EClass ST_TYPE_DECLARATION = eINSTANCE.getSTTypeDeclaration();

		/**
		 * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_TYPE_DECLARATION__ARRAY = eINSTANCE.getSTTypeDeclaration_Array();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_TYPE_DECLARATION__RANGES = eINSTANCE.getSTTypeDeclaration_Ranges();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_TYPE_DECLARATION__COUNT = eINSTANCE.getSTTypeDeclaration_Count();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_TYPE_DECLARATION__TYPE = eINSTANCE.getSTTypeDeclaration_Type();

		/**
		 * The meta object literal for the '<em><b>Max Length</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_TYPE_DECLARATION__MAX_LENGTH = eINSTANCE.getSTTypeDeclaration_MaxLength();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STBuiltinFeatureExpressionImpl <em>ST Builtin Feature Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STBuiltinFeatureExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTBuiltinFeatureExpression()
		 * @generated
		 */
		EClass ST_BUILTIN_FEATURE_EXPRESSION = eINSTANCE.getSTBuiltinFeatureExpression();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_BUILTIN_FEATURE_EXPRESSION__FEATURE = eINSTANCE.getSTBuiltinFeatureExpression_Feature();

		/**
		 * The meta object literal for the '<em><b>Call</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_BUILTIN_FEATURE_EXPRESSION__CALL = eINSTANCE.getSTBuiltinFeatureExpression_Call();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_BUILTIN_FEATURE_EXPRESSION__PARAMETERS = eINSTANCE.getSTBuiltinFeatureExpression_Parameters();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStandardFunctionImpl <em>ST Standard Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStandardFunctionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStandardFunction()
		 * @generated
		 */
		EClass ST_STANDARD_FUNCTION = eINSTANCE.getSTStandardFunction();

		/**
		 * The meta object literal for the '<em><b>Return Value Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_STANDARD_FUNCTION__RETURN_VALUE_COMMENT = eINSTANCE.getSTStandardFunction_ReturnValueComment();

		/**
		 * The meta object literal for the '<em><b>Signature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_STANDARD_FUNCTION__SIGNATURE = eINSTANCE.getSTStandardFunction_Signature();

		/**
		 * The meta object literal for the '<em><b>Return Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_STANDARD_FUNCTION__RETURN_TYPE = eINSTANCE.getSTStandardFunction_ReturnType();

		/**
		 * The meta object literal for the '<em><b>Input Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_STANDARD_FUNCTION__INPUT_PARAMETERS = eINSTANCE.getSTStandardFunction_InputParameters();

		/**
		 * The meta object literal for the '<em><b>Output Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_STANDARD_FUNCTION__OUTPUT_PARAMETERS = eINSTANCE.getSTStandardFunction_OutputParameters();

		/**
		 * The meta object literal for the '<em><b>In Out Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_STANDARD_FUNCTION__IN_OUT_PARAMETERS = eINSTANCE.getSTStandardFunction_InOutParameters();

		/**
		 * The meta object literal for the '<em><b>Varargs</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_STANDARD_FUNCTION__VARARGS = eINSTANCE.getSTStandardFunction_Varargs();

		/**
		 * The meta object literal for the '<em><b>Only Supported By</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_STANDARD_FUNCTION__ONLY_SUPPORTED_BY = eINSTANCE.getSTStandardFunction_OnlySupportedBy();

		/**
		 * The meta object literal for the '<em><b>Java Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_STANDARD_FUNCTION__JAVA_METHOD = eINSTANCE.getSTStandardFunction_JavaMethod();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCommentImpl <em>ST Comment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCommentImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTComment()
		 * @generated
		 */
		EClass ST_COMMENT = eINSTANCE.getSTComment();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_COMMENT__CONTEXT = eINSTANCE.getSTComment_Context();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_COMMENT__TEXT = eINSTANCE.getSTComment_Text();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ST_COMMENT__POSITION = eINSTANCE.getSTComment_Position();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStructInitializerExpressionImpl <em>ST Struct Initializer Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStructInitializerExpressionImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStructInitializerExpression()
		 * @generated
		 */
		EClass ST_STRUCT_INITIALIZER_EXPRESSION = eINSTANCE.getSTStructInitializerExpression();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_STRUCT_INITIALIZER_EXPRESSION__TYPE = eINSTANCE.getSTStructInitializerExpression_Type();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_STRUCT_INITIALIZER_EXPRESSION__VALUES = eINSTANCE.getSTStructInitializerExpression_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStructInitElementImpl <em>ST Struct Init Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STStructInitElementImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTStructInitElement()
		 * @generated
		 */
		EClass ST_STRUCT_INIT_ELEMENT = eINSTANCE.getSTStructInitElement();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_STRUCT_INIT_ELEMENT__VARIABLE = eINSTANCE.getSTStructInitElement_Variable();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_STRUCT_INIT_ELEMENT__VALUE = eINSTANCE.getSTStructInitElement_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExpressionSourceImpl <em>ST Expression Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STExpressionSourceImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTExpressionSource()
		 * @generated
		 */
		EClass ST_EXPRESSION_SOURCE = eINSTANCE.getSTExpressionSource();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_EXPRESSION_SOURCE__EXPRESSION = eINSTANCE.getSTExpressionSource_Expression();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STInitializerExpressionSourceImpl <em>ST Initializer Expression Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STInitializerExpressionSourceImpl
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTInitializerExpressionSource()
		 * @generated
		 */
		EClass ST_INITIALIZER_EXPRESSION_SOURCE = eINSTANCE.getSTInitializerExpressionSource();

		/**
		 * The meta object literal for the '<em><b>Initializer Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ST_INITIALIZER_EXPRESSION_SOURCE__INITIALIZER_EXPRESSION = eINSTANCE.getSTInitializerExpressionSource_InitializerExpression();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeature <em>ST Builtin Feature</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STBuiltinFeature
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTBuiltinFeature()
		 * @generated
		 */
		EEnum ST_BUILTIN_FEATURE = eINSTANCE.getSTBuiltinFeature();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STAccessSpecifier <em>ST Access Specifier</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STAccessSpecifier
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTAccessSpecifier()
		 * @generated
		 */
		EEnum ST_ACCESS_SPECIFIER = eINSTANCE.getSTAccessSpecifier();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STCommentPosition <em>ST Comment Position</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCommentPosition
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTCommentPosition()
		 * @generated
		 */
		EEnum ST_COMMENT_POSITION = eINSTANCE.getSTCommentPosition();

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

		/**
		 * The meta object literal for the '<em>ST Java Method</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.reflect.Method
		 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.impl.STCorePackageImpl#getSTJavaMethod()
		 * @generated
		 */
		EDataType ST_JAVA_METHOD = eINSTANCE.getSTJavaMethod();

	}

} //STCorePackage
