/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.xmiexport.xmiexport;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportFactory
 * @model kind="package"
 * @generated
 */
public interface XMIExportPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "xmiexport"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/fordiac/ide/xmiexport/XMIExport"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "xmiexport"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	XMIExportPackage eINSTANCE = org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportAttributeValuesImpl <em>Attribute Values</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportAttributeValuesImpl
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportAttributeValues()
	 * @generated
	 */
	int XMI_EXPORT_ATTRIBUTE_VALUES = 0;

	/**
	 * The feature id for the '<em><b>Attribute Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_ATTRIBUTE_VALUES__ATTRIBUTE_VALUES = 0;

	/**
	 * The number of structural features of the '<em>Attribute Values</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_ATTRIBUTE_VALUES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportAttributeValueImpl <em>Attribute Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportAttributeValueImpl
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportAttributeValue()
	 * @generated
	 */
	int XMI_EXPORT_ATTRIBUTE_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Attribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_ATTRIBUTE_VALUE__ATTRIBUTE = 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_ATTRIBUTE_VALUE__EXPRESSION = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_ATTRIBUTE_VALUE__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Attribute Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_ATTRIBUTE_VALUE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportInitialValuesImpl <em>Initial Values</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportInitialValuesImpl
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportInitialValues()
	 * @generated
	 */
	int XMI_EXPORT_INITIAL_VALUES = 2;

	/**
	 * The feature id for the '<em><b>Initial Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_INITIAL_VALUES__INITIAL_VALUES = 0;

	/**
	 * The number of structural features of the '<em>Initial Values</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_INITIAL_VALUES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportInitialValueImpl <em>Initial Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportInitialValueImpl
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportInitialValue()
	 * @generated
	 */
	int XMI_EXPORT_INITIAL_VALUE = 3;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_INITIAL_VALUE__VARIABLE = 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_INITIAL_VALUE__EXPRESSION = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_INITIAL_VALUE__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Initial Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_INITIAL_VALUE_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationsImpl <em>Type Declarations</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationsImpl
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportTypeDeclarations()
	 * @generated
	 */
	int XMI_EXPORT_TYPE_DECLARATIONS = 4;

	/**
	 * The feature id for the '<em><b>Type Declarations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_TYPE_DECLARATIONS__TYPE_DECLARATIONS = 0;

	/**
	 * The number of structural features of the '<em>Type Declarations</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_TYPE_DECLARATIONS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationImpl <em>Type Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationImpl
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportTypeDeclaration()
	 * @generated
	 */
	int XMI_EXPORT_TYPE_DECLARATION = 5;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_TYPE_DECLARATION__VARIABLE = 0;

	/**
	 * The feature id for the '<em><b>Type Declaration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION = 1;

	/**
	 * The feature id for the '<em><b>Result Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE = 2;

	/**
	 * The number of structural features of the '<em>Type Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int XMI_EXPORT_TYPE_DECLARATION_FEATURE_COUNT = 3;

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValues <em>Attribute Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Values</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValues
	 * @generated
	 */
	EClass getXMIExportAttributeValues();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValues#getAttributeValues <em>Attribute Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Attribute Values</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValues#getAttributeValues()
	 * @see #getXMIExportAttributeValues()
	 * @generated
	 */
	EReference getXMIExportAttributeValues_AttributeValues();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue <em>Attribute Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Value</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue
	 * @generated
	 */
	EClass getXMIExportAttributeValue();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getAttribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getAttribute()
	 * @see #getXMIExportAttributeValue()
	 * @generated
	 */
	EReference getXMIExportAttributeValue_Attribute();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getExpression()
	 * @see #getXMIExportAttributeValue()
	 * @generated
	 */
	EReference getXMIExportAttributeValue_Expression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportAttributeValue#getValue()
	 * @see #getXMIExportAttributeValue()
	 * @generated
	 */
	EAttribute getXMIExportAttributeValue_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValues <em>Initial Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initial Values</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValues
	 * @generated
	 */
	EClass getXMIExportInitialValues();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValues#getInitialValues <em>Initial Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Initial Values</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValues#getInitialValues()
	 * @see #getXMIExportInitialValues()
	 * @generated
	 */
	EReference getXMIExportInitialValues_InitialValues();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Initial Value</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue
	 * @generated
	 */
	EClass getXMIExportInitialValue();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue#getVariable()
	 * @see #getXMIExportInitialValue()
	 * @generated
	 */
	EReference getXMIExportInitialValue_Variable();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue#getExpression()
	 * @see #getXMIExportInitialValue()
	 * @generated
	 */
	EReference getXMIExportInitialValue_Expression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportInitialValue#getValue()
	 * @see #getXMIExportInitialValue()
	 * @generated
	 */
	EAttribute getXMIExportInitialValue_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclarations <em>Type Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Declarations</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclarations
	 * @generated
	 */
	EClass getXMIExportTypeDeclarations();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclarations#getTypeDeclarations <em>Type Declarations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Type Declarations</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclarations#getTypeDeclarations()
	 * @see #getXMIExportTypeDeclarations()
	 * @generated
	 */
	EReference getXMIExportTypeDeclarations_TypeDeclarations();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration <em>Type Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration
	 * @generated
	 */
	EClass getXMIExportTypeDeclaration();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getVariable()
	 * @see #getXMIExportTypeDeclaration()
	 * @generated
	 */
	EReference getXMIExportTypeDeclaration_Variable();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getTypeDeclaration <em>Type Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getTypeDeclaration()
	 * @see #getXMIExportTypeDeclaration()
	 * @generated
	 */
	EReference getXMIExportTypeDeclaration_TypeDeclaration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getResultType <em>Result Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Result Type</em>'.
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getResultType()
	 * @see #getXMIExportTypeDeclaration()
	 * @generated
	 */
	EReference getXMIExportTypeDeclaration_ResultType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	XMIExportFactory getXMIExportFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportAttributeValuesImpl <em>Attribute Values</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportAttributeValuesImpl
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportAttributeValues()
		 * @generated
		 */
		EClass XMI_EXPORT_ATTRIBUTE_VALUES = eINSTANCE.getXMIExportAttributeValues();

		/**
		 * The meta object literal for the '<em><b>Attribute Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XMI_EXPORT_ATTRIBUTE_VALUES__ATTRIBUTE_VALUES = eINSTANCE.getXMIExportAttributeValues_AttributeValues();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportAttributeValueImpl <em>Attribute Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportAttributeValueImpl
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportAttributeValue()
		 * @generated
		 */
		EClass XMI_EXPORT_ATTRIBUTE_VALUE = eINSTANCE.getXMIExportAttributeValue();

		/**
		 * The meta object literal for the '<em><b>Attribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XMI_EXPORT_ATTRIBUTE_VALUE__ATTRIBUTE = eINSTANCE.getXMIExportAttributeValue_Attribute();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XMI_EXPORT_ATTRIBUTE_VALUE__EXPRESSION = eINSTANCE.getXMIExportAttributeValue_Expression();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XMI_EXPORT_ATTRIBUTE_VALUE__VALUE = eINSTANCE.getXMIExportAttributeValue_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportInitialValuesImpl <em>Initial Values</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportInitialValuesImpl
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportInitialValues()
		 * @generated
		 */
		EClass XMI_EXPORT_INITIAL_VALUES = eINSTANCE.getXMIExportInitialValues();

		/**
		 * The meta object literal for the '<em><b>Initial Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XMI_EXPORT_INITIAL_VALUES__INITIAL_VALUES = eINSTANCE.getXMIExportInitialValues_InitialValues();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportInitialValueImpl <em>Initial Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportInitialValueImpl
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportInitialValue()
		 * @generated
		 */
		EClass XMI_EXPORT_INITIAL_VALUE = eINSTANCE.getXMIExportInitialValue();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XMI_EXPORT_INITIAL_VALUE__VARIABLE = eINSTANCE.getXMIExportInitialValue_Variable();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XMI_EXPORT_INITIAL_VALUE__EXPRESSION = eINSTANCE.getXMIExportInitialValue_Expression();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute XMI_EXPORT_INITIAL_VALUE__VALUE = eINSTANCE.getXMIExportInitialValue_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationsImpl <em>Type Declarations</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationsImpl
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportTypeDeclarations()
		 * @generated
		 */
		EClass XMI_EXPORT_TYPE_DECLARATIONS = eINSTANCE.getXMIExportTypeDeclarations();

		/**
		 * The meta object literal for the '<em><b>Type Declarations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XMI_EXPORT_TYPE_DECLARATIONS__TYPE_DECLARATIONS = eINSTANCE.getXMIExportTypeDeclarations_TypeDeclarations();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationImpl <em>Type Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportTypeDeclarationImpl
		 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.impl.XMIExportPackageImpl#getXMIExportTypeDeclaration()
		 * @generated
		 */
		EClass XMI_EXPORT_TYPE_DECLARATION = eINSTANCE.getXMIExportTypeDeclaration();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XMI_EXPORT_TYPE_DECLARATION__VARIABLE = eINSTANCE.getXMIExportTypeDeclaration_Variable();

		/**
		 * The meta object literal for the '<em><b>Type Declaration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XMI_EXPORT_TYPE_DECLARATION__TYPE_DECLARATION = eINSTANCE.getXMIExportTypeDeclaration_TypeDeclaration();

		/**
		 * The meta object literal for the '<em><b>Result Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference XMI_EXPORT_TYPE_DECLARATION__RESULT_TYPE = eINSTANCE.getXMIExportTypeDeclaration_ResultType();

	}

} //XMIExportPackage
