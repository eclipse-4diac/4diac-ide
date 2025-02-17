/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.data;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.eclipse.fordiac.ide.model.data.DataFactory
 * @model kind="package"
 * @generated
 */
public interface DataPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "data"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.model.datatype"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "data"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DataPackage eINSTANCE = org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.DataTypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__NAME = LibraryElementPackage.LIBRARY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__COMMENT = LibraryElementPackage.LIBRARY_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__ATTRIBUTES = LibraryElementPackage.LIBRARY_ELEMENT__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__VERSION_INFO = LibraryElementPackage.LIBRARY_ELEMENT__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__IDENTIFICATION = LibraryElementPackage.LIBRARY_ELEMENT__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__COMPILER_INFO = LibraryElementPackage.LIBRARY_ELEMENT__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__TYPE_ENTRY = LibraryElementPackage.LIBRARY_ELEMENT__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_FEATURE_COUNT = LibraryElementPackage.LIBRARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyTypeImpl <em>Any Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyType()
	 * @generated
	 */
	int ANY_TYPE = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__NAME = DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__COMMENT = DATA_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__ATTRIBUTES = DATA_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__VERSION_INFO = DATA_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__IDENTIFICATION = DATA_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__COMPILER_INFO = DATA_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE__TYPE_ENTRY = DATA_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyDerivedTypeImpl <em>Any Derived Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyDerivedTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyDerivedType()
	 * @generated
	 */
	int ANY_DERIVED_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DERIVED_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DERIVED_TYPE__COMMENT = ANY_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DERIVED_TYPE__ATTRIBUTES = ANY_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DERIVED_TYPE__VERSION_INFO = ANY_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DERIVED_TYPE__IDENTIFICATION = ANY_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DERIVED_TYPE__COMPILER_INFO = ANY_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DERIVED_TYPE__TYPE_ENTRY = ANY_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Derived Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DERIVED_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.ArrayTypeImpl <em>Array Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.ArrayTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getArrayType()
	 * @generated
	 */
	int ARRAY_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__NAME = ANY_DERIVED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__COMMENT = ANY_DERIVED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__ATTRIBUTES = ANY_DERIVED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__VERSION_INFO = ANY_DERIVED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__IDENTIFICATION = ANY_DERIVED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__COMPILER_INFO = ANY_DERIVED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__TYPE_ENTRY = ANY_DERIVED_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Subranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__SUBRANGES = ANY_DERIVED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial Values</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__INITIAL_VALUES = ANY_DERIVED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__BASE_TYPE = ANY_DERIVED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Array Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE_FEATURE_COUNT = ANY_DERIVED_TYPE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.ValueTypeImpl <em>Value Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.ValueTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getValueType()
	 * @generated
	 */
	int VALUE_TYPE = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.DerivedTypeImpl <em>Derived Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.DerivedTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDerivedType()
	 * @generated
	 */
	int DERIVED_TYPE = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.DirectlyDerivedTypeImpl <em>Directly Derived Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.DirectlyDerivedTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDirectlyDerivedType()
	 * @generated
	 */
	int DIRECTLY_DERIVED_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__NAME = ANY_DERIVED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__COMMENT = ANY_DERIVED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__ATTRIBUTES = ANY_DERIVED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__VERSION_INFO = ANY_DERIVED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__IDENTIFICATION = ANY_DERIVED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__COMPILER_INFO = ANY_DERIVED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__TYPE_ENTRY = ANY_DERIVED_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__BASE_TYPE = ANY_DERIVED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__INITIAL_VALUE = ANY_DERIVED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Directly Derived Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE_FEATURE_COUNT = ANY_DERIVED_TYPE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.EnumeratedTypeImpl <em>Enumerated Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.EnumeratedTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getEnumeratedType()
	 * @generated
	 */
	int ENUMERATED_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__NAME = ANY_DERIVED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__COMMENT = ANY_DERIVED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__ATTRIBUTES = ANY_DERIVED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__VERSION_INFO = ANY_DERIVED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__IDENTIFICATION = ANY_DERIVED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__COMPILER_INFO = ANY_DERIVED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__TYPE_ENTRY = ANY_DERIVED_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Enumerated Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__ENUMERATED_VALUES = ANY_DERIVED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enumerated Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE_FEATURE_COUNT = ANY_DERIVED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.EnumeratedValueImpl <em>Enumerated Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.EnumeratedValueImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getEnumeratedValue()
	 * @generated
	 */
	int ENUMERATED_VALUE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_VALUE__NAME = LibraryElementPackage.INAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_VALUE__COMMENT = LibraryElementPackage.INAMED_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_VALUE__TYPE = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enumerated Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_VALUE_FEATURE_COUNT = LibraryElementPackage.INAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.StructuredTypeImpl <em>Structured Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.StructuredTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getStructuredType()
	 * @generated
	 */
	int STRUCTURED_TYPE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__NAME = ANY_DERIVED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__COMMENT = ANY_DERIVED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__ATTRIBUTES = ANY_DERIVED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__VERSION_INFO = ANY_DERIVED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__IDENTIFICATION = ANY_DERIVED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__COMPILER_INFO = ANY_DERIVED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__TYPE_ENTRY = ANY_DERIVED_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Member Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__MEMBER_VARIABLES = ANY_DERIVED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Structured Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE_FEATURE_COUNT = ANY_DERIVED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.SubrangeImpl <em>Subrange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.SubrangeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getSubrange()
	 * @generated
	 */
	int SUBRANGE = 7;

	/**
	 * The feature id for the '<em><b>Lower Limit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE__LOWER_LIMIT = 0;

	/**
	 * The feature id for the '<em><b>Upper Limit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE__UPPER_LIMIT = 1;

	/**
	 * The number of structural features of the '<em>Subrange</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE__NAME = DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE__COMMENT = DATA_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE__ATTRIBUTES = DATA_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE__VERSION_INFO = DATA_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE__IDENTIFICATION = DATA_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE__COMPILER_INFO = DATA_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE__TYPE_ENTRY = DATA_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Value Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE__NAME = VALUE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE__COMMENT = VALUE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE__ATTRIBUTES = VALUE_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE__VERSION_INFO = VALUE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE__IDENTIFICATION = VALUE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE__COMPILER_INFO = VALUE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE__TYPE_ENTRY = VALUE_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE__BASE_TYPE = VALUE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Derived Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE_FEATURE_COUNT = VALUE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.SubrangeTypeImpl <em>Subrange Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.SubrangeTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getSubrangeType()
	 * @generated
	 */
	int SUBRANGE_TYPE = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__NAME = DERIVED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__COMMENT = DERIVED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__ATTRIBUTES = DERIVED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__VERSION_INFO = DERIVED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__IDENTIFICATION = DERIVED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__COMPILER_INFO = DERIVED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__TYPE_ENTRY = DERIVED_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__BASE_TYPE = DERIVED_TYPE__BASE_TYPE;

	/**
	 * The feature id for the '<em><b>Subrange</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__SUBRANGE = DERIVED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Subrange Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE_FEATURE_COUNT = DERIVED_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.EventTypeImpl <em>Event Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.EventTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getEventType()
	 * @generated
	 */
	int EVENT_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__NAME = DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__COMMENT = DATA_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__ATTRIBUTES = DATA_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__VERSION_INFO = DATA_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__IDENTIFICATION = DATA_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__COMPILER_INFO = DATA_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__TYPE_ENTRY = DATA_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Event Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyElementaryTypeImpl <em>Any Elementary Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyElementaryTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyElementaryType()
	 * @generated
	 */
	int ANY_ELEMENTARY_TYPE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ELEMENTARY_TYPE__NAME = ANY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ELEMENTARY_TYPE__COMMENT = ANY_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ELEMENTARY_TYPE__ATTRIBUTES = ANY_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ELEMENTARY_TYPE__VERSION_INFO = ANY_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ELEMENTARY_TYPE__IDENTIFICATION = ANY_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ELEMENTARY_TYPE__COMPILER_INFO = ANY_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ELEMENTARY_TYPE__TYPE_ENTRY = ANY_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Elementary Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_ELEMENTARY_TYPE_FEATURE_COUNT = ANY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyMagnitudeTypeImpl <em>Any Magnitude Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyMagnitudeTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyMagnitudeType()
	 * @generated
	 */
	int ANY_MAGNITUDE_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_MAGNITUDE_TYPE__NAME = ANY_ELEMENTARY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_MAGNITUDE_TYPE__COMMENT = ANY_ELEMENTARY_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_MAGNITUDE_TYPE__ATTRIBUTES = ANY_ELEMENTARY_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_MAGNITUDE_TYPE__VERSION_INFO = ANY_ELEMENTARY_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_MAGNITUDE_TYPE__IDENTIFICATION = ANY_ELEMENTARY_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_MAGNITUDE_TYPE__COMPILER_INFO = ANY_ELEMENTARY_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_MAGNITUDE_TYPE__TYPE_ENTRY = ANY_ELEMENTARY_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Magnitude Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_MAGNITUDE_TYPE_FEATURE_COUNT = ANY_ELEMENTARY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyNumTypeImpl <em>Any Num Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyNumTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyNumType()
	 * @generated
	 */
	int ANY_NUM_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_NUM_TYPE__NAME = ANY_MAGNITUDE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_NUM_TYPE__COMMENT = ANY_MAGNITUDE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_NUM_TYPE__ATTRIBUTES = ANY_MAGNITUDE_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_NUM_TYPE__VERSION_INFO = ANY_MAGNITUDE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_NUM_TYPE__IDENTIFICATION = ANY_MAGNITUDE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_NUM_TYPE__COMPILER_INFO = ANY_MAGNITUDE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_NUM_TYPE__TYPE_ENTRY = ANY_MAGNITUDE_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Num Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_NUM_TYPE_FEATURE_COUNT = ANY_MAGNITUDE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyRealTypeImpl <em>Any Real Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyRealTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyRealType()
	 * @generated
	 */
	int ANY_REAL_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_REAL_TYPE__NAME = ANY_NUM_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_REAL_TYPE__COMMENT = ANY_NUM_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_REAL_TYPE__ATTRIBUTES = ANY_NUM_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_REAL_TYPE__VERSION_INFO = ANY_NUM_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_REAL_TYPE__IDENTIFICATION = ANY_NUM_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_REAL_TYPE__COMPILER_INFO = ANY_NUM_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_REAL_TYPE__TYPE_ENTRY = ANY_NUM_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Real Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_REAL_TYPE_FEATURE_COUNT = ANY_NUM_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.RealTypeImpl <em>Real Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.RealTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getRealType()
	 * @generated
	 */
	int REAL_TYPE = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE__NAME = ANY_REAL_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE__COMMENT = ANY_REAL_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE__ATTRIBUTES = ANY_REAL_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE__VERSION_INFO = ANY_REAL_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE__IDENTIFICATION = ANY_REAL_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE__COMPILER_INFO = ANY_REAL_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE__TYPE_ENTRY = ANY_REAL_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Real Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REAL_TYPE_FEATURE_COUNT = ANY_REAL_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.LrealTypeImpl <em>Lreal Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.LrealTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLrealType()
	 * @generated
	 */
	int LREAL_TYPE = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LREAL_TYPE__NAME = ANY_REAL_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LREAL_TYPE__COMMENT = ANY_REAL_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LREAL_TYPE__ATTRIBUTES = ANY_REAL_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LREAL_TYPE__VERSION_INFO = ANY_REAL_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LREAL_TYPE__IDENTIFICATION = ANY_REAL_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LREAL_TYPE__COMPILER_INFO = ANY_REAL_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LREAL_TYPE__TYPE_ENTRY = ANY_REAL_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Lreal Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LREAL_TYPE_FEATURE_COUNT = ANY_REAL_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyIntTypeImpl <em>Any Int Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyIntTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyIntType()
	 * @generated
	 */
	int ANY_INT_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_INT_TYPE__NAME = ANY_NUM_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_INT_TYPE__COMMENT = ANY_NUM_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_INT_TYPE__ATTRIBUTES = ANY_NUM_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_INT_TYPE__VERSION_INFO = ANY_NUM_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_INT_TYPE__IDENTIFICATION = ANY_NUM_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_INT_TYPE__COMPILER_INFO = ANY_NUM_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_INT_TYPE__TYPE_ENTRY = ANY_NUM_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Int Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_INT_TYPE_FEATURE_COUNT = ANY_NUM_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyUnsignedTypeImpl <em>Any Unsigned Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyUnsignedTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyUnsignedType()
	 * @generated
	 */
	int ANY_UNSIGNED_TYPE = 20;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_UNSIGNED_TYPE__NAME = ANY_INT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_UNSIGNED_TYPE__COMMENT = ANY_INT_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_UNSIGNED_TYPE__ATTRIBUTES = ANY_INT_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_UNSIGNED_TYPE__VERSION_INFO = ANY_INT_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_UNSIGNED_TYPE__IDENTIFICATION = ANY_INT_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_UNSIGNED_TYPE__COMPILER_INFO = ANY_INT_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_UNSIGNED_TYPE__TYPE_ENTRY = ANY_INT_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Unsigned Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_UNSIGNED_TYPE_FEATURE_COUNT = ANY_INT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.UsintTypeImpl <em>Usint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.UsintTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getUsintType()
	 * @generated
	 */
	int USINT_TYPE = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USINT_TYPE__NAME = ANY_UNSIGNED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USINT_TYPE__COMMENT = ANY_UNSIGNED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USINT_TYPE__ATTRIBUTES = ANY_UNSIGNED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USINT_TYPE__VERSION_INFO = ANY_UNSIGNED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USINT_TYPE__IDENTIFICATION = ANY_UNSIGNED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USINT_TYPE__COMPILER_INFO = ANY_UNSIGNED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USINT_TYPE__TYPE_ENTRY = ANY_UNSIGNED_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Usint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USINT_TYPE_FEATURE_COUNT = ANY_UNSIGNED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.UintTypeImpl <em>Uint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.UintTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getUintType()
	 * @generated
	 */
	int UINT_TYPE = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINT_TYPE__NAME = ANY_UNSIGNED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINT_TYPE__COMMENT = ANY_UNSIGNED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINT_TYPE__ATTRIBUTES = ANY_UNSIGNED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINT_TYPE__VERSION_INFO = ANY_UNSIGNED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINT_TYPE__IDENTIFICATION = ANY_UNSIGNED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINT_TYPE__COMPILER_INFO = ANY_UNSIGNED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINT_TYPE__TYPE_ENTRY = ANY_UNSIGNED_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Uint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UINT_TYPE_FEATURE_COUNT = ANY_UNSIGNED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.UdintTypeImpl <em>Udint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.UdintTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getUdintType()
	 * @generated
	 */
	int UDINT_TYPE = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UDINT_TYPE__NAME = ANY_UNSIGNED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UDINT_TYPE__COMMENT = ANY_UNSIGNED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UDINT_TYPE__ATTRIBUTES = ANY_UNSIGNED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UDINT_TYPE__VERSION_INFO = ANY_UNSIGNED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UDINT_TYPE__IDENTIFICATION = ANY_UNSIGNED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UDINT_TYPE__COMPILER_INFO = ANY_UNSIGNED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UDINT_TYPE__TYPE_ENTRY = ANY_UNSIGNED_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Udint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UDINT_TYPE_FEATURE_COUNT = ANY_UNSIGNED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.UlintTypeImpl <em>Ulint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.UlintTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getUlintType()
	 * @generated
	 */
	int ULINT_TYPE = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ULINT_TYPE__NAME = ANY_UNSIGNED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ULINT_TYPE__COMMENT = ANY_UNSIGNED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ULINT_TYPE__ATTRIBUTES = ANY_UNSIGNED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ULINT_TYPE__VERSION_INFO = ANY_UNSIGNED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ULINT_TYPE__IDENTIFICATION = ANY_UNSIGNED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ULINT_TYPE__COMPILER_INFO = ANY_UNSIGNED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ULINT_TYPE__TYPE_ENTRY = ANY_UNSIGNED_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Ulint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ULINT_TYPE_FEATURE_COUNT = ANY_UNSIGNED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnySignedTypeImpl <em>Any Signed Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnySignedTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnySignedType()
	 * @generated
	 */
	int ANY_SIGNED_TYPE = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SIGNED_TYPE__NAME = ANY_INT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SIGNED_TYPE__COMMENT = ANY_INT_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SIGNED_TYPE__ATTRIBUTES = ANY_INT_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SIGNED_TYPE__VERSION_INFO = ANY_INT_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SIGNED_TYPE__IDENTIFICATION = ANY_INT_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SIGNED_TYPE__COMPILER_INFO = ANY_INT_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SIGNED_TYPE__TYPE_ENTRY = ANY_INT_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Signed Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SIGNED_TYPE_FEATURE_COUNT = ANY_INT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.SintTypeImpl <em>Sint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.SintTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getSintType()
	 * @generated
	 */
	int SINT_TYPE = 26;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINT_TYPE__NAME = ANY_SIGNED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINT_TYPE__COMMENT = ANY_SIGNED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINT_TYPE__ATTRIBUTES = ANY_SIGNED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINT_TYPE__VERSION_INFO = ANY_SIGNED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINT_TYPE__IDENTIFICATION = ANY_SIGNED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINT_TYPE__COMPILER_INFO = ANY_SIGNED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINT_TYPE__TYPE_ENTRY = ANY_SIGNED_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Sint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINT_TYPE_FEATURE_COUNT = ANY_SIGNED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.IntTypeImpl <em>Int Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.IntTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getIntType()
	 * @generated
	 */
	int INT_TYPE = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_TYPE__NAME = ANY_SIGNED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_TYPE__COMMENT = ANY_SIGNED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_TYPE__ATTRIBUTES = ANY_SIGNED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_TYPE__VERSION_INFO = ANY_SIGNED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_TYPE__IDENTIFICATION = ANY_SIGNED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_TYPE__COMPILER_INFO = ANY_SIGNED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_TYPE__TYPE_ENTRY = ANY_SIGNED_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Int Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_TYPE_FEATURE_COUNT = ANY_SIGNED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.DintTypeImpl <em>Dint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.DintTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDintType()
	 * @generated
	 */
	int DINT_TYPE = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DINT_TYPE__NAME = ANY_SIGNED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DINT_TYPE__COMMENT = ANY_SIGNED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DINT_TYPE__ATTRIBUTES = ANY_SIGNED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DINT_TYPE__VERSION_INFO = ANY_SIGNED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DINT_TYPE__IDENTIFICATION = ANY_SIGNED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DINT_TYPE__COMPILER_INFO = ANY_SIGNED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DINT_TYPE__TYPE_ENTRY = ANY_SIGNED_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Dint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DINT_TYPE_FEATURE_COUNT = ANY_SIGNED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.LintTypeImpl <em>Lint Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.LintTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLintType()
	 * @generated
	 */
	int LINT_TYPE = 29;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINT_TYPE__NAME = ANY_SIGNED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINT_TYPE__COMMENT = ANY_SIGNED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINT_TYPE__ATTRIBUTES = ANY_SIGNED_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINT_TYPE__VERSION_INFO = ANY_SIGNED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINT_TYPE__IDENTIFICATION = ANY_SIGNED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINT_TYPE__COMPILER_INFO = ANY_SIGNED_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINT_TYPE__TYPE_ENTRY = ANY_SIGNED_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Lint Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINT_TYPE_FEATURE_COUNT = ANY_SIGNED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyDurationTypeImpl <em>Any Duration Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyDurationTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyDurationType()
	 * @generated
	 */
	int ANY_DURATION_TYPE = 30;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DURATION_TYPE__NAME = ANY_MAGNITUDE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DURATION_TYPE__COMMENT = ANY_MAGNITUDE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DURATION_TYPE__ATTRIBUTES = ANY_MAGNITUDE_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DURATION_TYPE__VERSION_INFO = ANY_MAGNITUDE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DURATION_TYPE__IDENTIFICATION = ANY_MAGNITUDE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DURATION_TYPE__COMPILER_INFO = ANY_MAGNITUDE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DURATION_TYPE__TYPE_ENTRY = ANY_MAGNITUDE_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Duration Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DURATION_TYPE_FEATURE_COUNT = ANY_MAGNITUDE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.TimeTypeImpl <em>Time Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.TimeTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getTimeType()
	 * @generated
	 */
	int TIME_TYPE = 31;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_TYPE__NAME = ANY_DURATION_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_TYPE__COMMENT = ANY_DURATION_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_TYPE__ATTRIBUTES = ANY_DURATION_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_TYPE__VERSION_INFO = ANY_DURATION_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_TYPE__IDENTIFICATION = ANY_DURATION_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_TYPE__COMPILER_INFO = ANY_DURATION_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_TYPE__TYPE_ENTRY = ANY_DURATION_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Time Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_TYPE_FEATURE_COUNT = ANY_DURATION_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.LtimeTypeImpl <em>Ltime Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.LtimeTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLtimeType()
	 * @generated
	 */
	int LTIME_TYPE = 32;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTIME_TYPE__NAME = ANY_DURATION_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTIME_TYPE__COMMENT = ANY_DURATION_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTIME_TYPE__ATTRIBUTES = ANY_DURATION_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTIME_TYPE__VERSION_INFO = ANY_DURATION_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTIME_TYPE__IDENTIFICATION = ANY_DURATION_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTIME_TYPE__COMPILER_INFO = ANY_DURATION_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTIME_TYPE__TYPE_ENTRY = ANY_DURATION_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Ltime Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTIME_TYPE_FEATURE_COUNT = ANY_DURATION_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyBitTypeImpl <em>Any Bit Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyBitTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyBitType()
	 * @generated
	 */
	int ANY_BIT_TYPE = 33;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_BIT_TYPE__NAME = ANY_ELEMENTARY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_BIT_TYPE__COMMENT = ANY_ELEMENTARY_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_BIT_TYPE__ATTRIBUTES = ANY_ELEMENTARY_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_BIT_TYPE__VERSION_INFO = ANY_ELEMENTARY_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_BIT_TYPE__IDENTIFICATION = ANY_ELEMENTARY_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_BIT_TYPE__COMPILER_INFO = ANY_ELEMENTARY_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_BIT_TYPE__TYPE_ENTRY = ANY_ELEMENTARY_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Bit Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_BIT_TYPE_FEATURE_COUNT = ANY_ELEMENTARY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.BoolTypeImpl <em>Bool Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.BoolTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getBoolType()
	 * @generated
	 */
	int BOOL_TYPE = 34;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_TYPE__NAME = ANY_BIT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_TYPE__COMMENT = ANY_BIT_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_TYPE__ATTRIBUTES = ANY_BIT_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_TYPE__VERSION_INFO = ANY_BIT_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_TYPE__IDENTIFICATION = ANY_BIT_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_TYPE__COMPILER_INFO = ANY_BIT_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_TYPE__TYPE_ENTRY = ANY_BIT_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Bool Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOL_TYPE_FEATURE_COUNT = ANY_BIT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.ByteTypeImpl <em>Byte Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.ByteTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getByteType()
	 * @generated
	 */
	int BYTE_TYPE = 35;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BYTE_TYPE__NAME = ANY_BIT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BYTE_TYPE__COMMENT = ANY_BIT_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BYTE_TYPE__ATTRIBUTES = ANY_BIT_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BYTE_TYPE__VERSION_INFO = ANY_BIT_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BYTE_TYPE__IDENTIFICATION = ANY_BIT_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BYTE_TYPE__COMPILER_INFO = ANY_BIT_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BYTE_TYPE__TYPE_ENTRY = ANY_BIT_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Byte Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BYTE_TYPE_FEATURE_COUNT = ANY_BIT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.WordTypeImpl <em>Word Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.WordTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getWordType()
	 * @generated
	 */
	int WORD_TYPE = 36;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORD_TYPE__NAME = ANY_BIT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORD_TYPE__COMMENT = ANY_BIT_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORD_TYPE__ATTRIBUTES = ANY_BIT_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORD_TYPE__VERSION_INFO = ANY_BIT_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORD_TYPE__IDENTIFICATION = ANY_BIT_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORD_TYPE__COMPILER_INFO = ANY_BIT_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORD_TYPE__TYPE_ENTRY = ANY_BIT_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Word Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORD_TYPE_FEATURE_COUNT = ANY_BIT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.DwordTypeImpl <em>Dword Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.DwordTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDwordType()
	 * @generated
	 */
	int DWORD_TYPE = 37;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DWORD_TYPE__NAME = ANY_BIT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DWORD_TYPE__COMMENT = ANY_BIT_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DWORD_TYPE__ATTRIBUTES = ANY_BIT_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DWORD_TYPE__VERSION_INFO = ANY_BIT_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DWORD_TYPE__IDENTIFICATION = ANY_BIT_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DWORD_TYPE__COMPILER_INFO = ANY_BIT_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DWORD_TYPE__TYPE_ENTRY = ANY_BIT_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Dword Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DWORD_TYPE_FEATURE_COUNT = ANY_BIT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.LwordTypeImpl <em>Lword Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.LwordTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLwordType()
	 * @generated
	 */
	int LWORD_TYPE = 38;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LWORD_TYPE__NAME = ANY_BIT_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LWORD_TYPE__COMMENT = ANY_BIT_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LWORD_TYPE__ATTRIBUTES = ANY_BIT_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LWORD_TYPE__VERSION_INFO = ANY_BIT_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LWORD_TYPE__IDENTIFICATION = ANY_BIT_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LWORD_TYPE__COMPILER_INFO = ANY_BIT_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LWORD_TYPE__TYPE_ENTRY = ANY_BIT_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Lword Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LWORD_TYPE_FEATURE_COUNT = ANY_BIT_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyCharsTypeImpl <em>Any Chars Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyCharsTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyCharsType()
	 * @generated
	 */
	int ANY_CHARS_TYPE = 39;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHARS_TYPE__NAME = ANY_ELEMENTARY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHARS_TYPE__COMMENT = ANY_ELEMENTARY_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHARS_TYPE__ATTRIBUTES = ANY_ELEMENTARY_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHARS_TYPE__VERSION_INFO = ANY_ELEMENTARY_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHARS_TYPE__IDENTIFICATION = ANY_ELEMENTARY_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHARS_TYPE__COMPILER_INFO = ANY_ELEMENTARY_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHARS_TYPE__TYPE_ENTRY = ANY_ELEMENTARY_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Chars Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHARS_TYPE_FEATURE_COUNT = ANY_ELEMENTARY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnySCharsTypeImpl <em>Any SChars Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnySCharsTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnySCharsType()
	 * @generated
	 */
	int ANY_SCHARS_TYPE = 40;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SCHARS_TYPE__NAME = ANY_CHARS_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SCHARS_TYPE__COMMENT = ANY_CHARS_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SCHARS_TYPE__ATTRIBUTES = ANY_CHARS_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SCHARS_TYPE__VERSION_INFO = ANY_CHARS_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SCHARS_TYPE__IDENTIFICATION = ANY_CHARS_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SCHARS_TYPE__COMPILER_INFO = ANY_CHARS_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SCHARS_TYPE__TYPE_ENTRY = ANY_CHARS_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any SChars Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_SCHARS_TYPE_FEATURE_COUNT = ANY_CHARS_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyWCharsTypeImpl <em>Any WChars Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyWCharsTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyWCharsType()
	 * @generated
	 */
	int ANY_WCHARS_TYPE = 41;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_WCHARS_TYPE__NAME = ANY_CHARS_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_WCHARS_TYPE__COMMENT = ANY_CHARS_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_WCHARS_TYPE__ATTRIBUTES = ANY_CHARS_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_WCHARS_TYPE__VERSION_INFO = ANY_CHARS_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_WCHARS_TYPE__IDENTIFICATION = ANY_CHARS_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_WCHARS_TYPE__COMPILER_INFO = ANY_CHARS_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_WCHARS_TYPE__TYPE_ENTRY = ANY_CHARS_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any WChars Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_WCHARS_TYPE_FEATURE_COUNT = ANY_CHARS_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyStringTypeImpl <em>Any String Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyStringTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyStringType()
	 * @generated
	 */
	int ANY_STRING_TYPE = 42;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_STRING_TYPE__NAME = ANY_CHARS_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_STRING_TYPE__COMMENT = ANY_CHARS_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_STRING_TYPE__ATTRIBUTES = ANY_CHARS_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_STRING_TYPE__VERSION_INFO = ANY_CHARS_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_STRING_TYPE__IDENTIFICATION = ANY_CHARS_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_STRING_TYPE__COMPILER_INFO = ANY_CHARS_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_STRING_TYPE__TYPE_ENTRY = ANY_CHARS_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_STRING_TYPE__MAX_LENGTH = ANY_CHARS_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Any String Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_STRING_TYPE_FEATURE_COUNT = ANY_CHARS_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.StringTypeImpl <em>String Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.StringTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getStringType()
	 * @generated
	 */
	int STRING_TYPE = 43;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__NAME = ANY_STRING_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__COMMENT = ANY_STRING_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__ATTRIBUTES = ANY_STRING_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__VERSION_INFO = ANY_STRING_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__IDENTIFICATION = ANY_STRING_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__COMPILER_INFO = ANY_STRING_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__TYPE_ENTRY = ANY_STRING_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE__MAX_LENGTH = ANY_STRING_TYPE__MAX_LENGTH;

	/**
	 * The number of structural features of the '<em>String Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TYPE_FEATURE_COUNT = ANY_STRING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.WstringTypeImpl <em>Wstring Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.WstringTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getWstringType()
	 * @generated
	 */
	int WSTRING_TYPE = 44;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WSTRING_TYPE__NAME = ANY_STRING_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WSTRING_TYPE__COMMENT = ANY_STRING_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WSTRING_TYPE__ATTRIBUTES = ANY_STRING_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WSTRING_TYPE__VERSION_INFO = ANY_STRING_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WSTRING_TYPE__IDENTIFICATION = ANY_STRING_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WSTRING_TYPE__COMPILER_INFO = ANY_STRING_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WSTRING_TYPE__TYPE_ENTRY = ANY_STRING_TYPE__TYPE_ENTRY;

	/**
	 * The feature id for the '<em><b>Max Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WSTRING_TYPE__MAX_LENGTH = ANY_STRING_TYPE__MAX_LENGTH;

	/**
	 * The number of structural features of the '<em>Wstring Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WSTRING_TYPE_FEATURE_COUNT = ANY_STRING_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyCharTypeImpl <em>Any Char Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyCharTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyCharType()
	 * @generated
	 */
	int ANY_CHAR_TYPE = 45;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHAR_TYPE__NAME = ANY_CHARS_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHAR_TYPE__COMMENT = ANY_CHARS_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHAR_TYPE__ATTRIBUTES = ANY_CHARS_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHAR_TYPE__VERSION_INFO = ANY_CHARS_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHAR_TYPE__IDENTIFICATION = ANY_CHARS_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHAR_TYPE__COMPILER_INFO = ANY_CHARS_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHAR_TYPE__TYPE_ENTRY = ANY_CHARS_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Char Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_CHAR_TYPE_FEATURE_COUNT = ANY_CHARS_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.CharTypeImpl <em>Char Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.CharTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getCharType()
	 * @generated
	 */
	int CHAR_TYPE = 46;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TYPE__NAME = ANY_CHAR_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TYPE__COMMENT = ANY_CHAR_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TYPE__ATTRIBUTES = ANY_CHAR_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TYPE__VERSION_INFO = ANY_CHAR_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TYPE__IDENTIFICATION = ANY_CHAR_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TYPE__COMPILER_INFO = ANY_CHAR_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TYPE__TYPE_ENTRY = ANY_CHAR_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Char Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHAR_TYPE_FEATURE_COUNT = ANY_CHAR_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.WcharTypeImpl <em>Wchar Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.WcharTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getWcharType()
	 * @generated
	 */
	int WCHAR_TYPE = 47;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WCHAR_TYPE__NAME = ANY_CHAR_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WCHAR_TYPE__COMMENT = ANY_CHAR_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WCHAR_TYPE__ATTRIBUTES = ANY_CHAR_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WCHAR_TYPE__VERSION_INFO = ANY_CHAR_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WCHAR_TYPE__IDENTIFICATION = ANY_CHAR_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WCHAR_TYPE__COMPILER_INFO = ANY_CHAR_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WCHAR_TYPE__TYPE_ENTRY = ANY_CHAR_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Wchar Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WCHAR_TYPE_FEATURE_COUNT = ANY_CHAR_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyDateTypeImpl <em>Any Date Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.AnyDateTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyDateType()
	 * @generated
	 */
	int ANY_DATE_TYPE = 48;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DATE_TYPE__NAME = ANY_ELEMENTARY_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DATE_TYPE__COMMENT = ANY_ELEMENTARY_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DATE_TYPE__ATTRIBUTES = ANY_ELEMENTARY_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DATE_TYPE__VERSION_INFO = ANY_ELEMENTARY_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DATE_TYPE__IDENTIFICATION = ANY_ELEMENTARY_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DATE_TYPE__COMPILER_INFO = ANY_ELEMENTARY_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DATE_TYPE__TYPE_ENTRY = ANY_ELEMENTARY_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Any Date Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANY_DATE_TYPE_FEATURE_COUNT = ANY_ELEMENTARY_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.DateAndTimeTypeImpl <em>Date And Time Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.DateAndTimeTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDateAndTimeType()
	 * @generated
	 */
	int DATE_AND_TIME_TYPE = 49;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_AND_TIME_TYPE__NAME = ANY_DATE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_AND_TIME_TYPE__COMMENT = ANY_DATE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_AND_TIME_TYPE__ATTRIBUTES = ANY_DATE_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_AND_TIME_TYPE__VERSION_INFO = ANY_DATE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_AND_TIME_TYPE__IDENTIFICATION = ANY_DATE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_AND_TIME_TYPE__COMPILER_INFO = ANY_DATE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_AND_TIME_TYPE__TYPE_ENTRY = ANY_DATE_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Date And Time Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_AND_TIME_TYPE_FEATURE_COUNT = ANY_DATE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.LdtTypeImpl <em>Ldt Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.LdtTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLdtType()
	 * @generated
	 */
	int LDT_TYPE = 50;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDT_TYPE__NAME = ANY_DATE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDT_TYPE__COMMENT = ANY_DATE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDT_TYPE__ATTRIBUTES = ANY_DATE_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDT_TYPE__VERSION_INFO = ANY_DATE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDT_TYPE__IDENTIFICATION = ANY_DATE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDT_TYPE__COMPILER_INFO = ANY_DATE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDT_TYPE__TYPE_ENTRY = ANY_DATE_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Ldt Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDT_TYPE_FEATURE_COUNT = ANY_DATE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.DateTypeImpl <em>Date Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.DateTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDateType()
	 * @generated
	 */
	int DATE_TYPE = 51;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TYPE__NAME = ANY_DATE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TYPE__COMMENT = ANY_DATE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TYPE__ATTRIBUTES = ANY_DATE_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TYPE__VERSION_INFO = ANY_DATE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TYPE__IDENTIFICATION = ANY_DATE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TYPE__COMPILER_INFO = ANY_DATE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TYPE__TYPE_ENTRY = ANY_DATE_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Date Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TYPE_FEATURE_COUNT = ANY_DATE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.TimeOfDayTypeImpl <em>Time Of Day Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.TimeOfDayTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getTimeOfDayType()
	 * @generated
	 */
	int TIME_OF_DAY_TYPE = 52;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_OF_DAY_TYPE__NAME = ANY_DATE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_OF_DAY_TYPE__COMMENT = ANY_DATE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_OF_DAY_TYPE__ATTRIBUTES = ANY_DATE_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_OF_DAY_TYPE__VERSION_INFO = ANY_DATE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_OF_DAY_TYPE__IDENTIFICATION = ANY_DATE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_OF_DAY_TYPE__COMPILER_INFO = ANY_DATE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_OF_DAY_TYPE__TYPE_ENTRY = ANY_DATE_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Time Of Day Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_OF_DAY_TYPE_FEATURE_COUNT = ANY_DATE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.LtodTypeImpl <em>Ltod Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.LtodTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLtodType()
	 * @generated
	 */
	int LTOD_TYPE = 53;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTOD_TYPE__NAME = ANY_DATE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTOD_TYPE__COMMENT = ANY_DATE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTOD_TYPE__ATTRIBUTES = ANY_DATE_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTOD_TYPE__VERSION_INFO = ANY_DATE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTOD_TYPE__IDENTIFICATION = ANY_DATE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTOD_TYPE__COMPILER_INFO = ANY_DATE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTOD_TYPE__TYPE_ENTRY = ANY_DATE_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Ltod Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LTOD_TYPE_FEATURE_COUNT = ANY_DATE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.LdateTypeImpl <em>Ldate Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.LdateTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLdateType()
	 * @generated
	 */
	int LDATE_TYPE = 54;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDATE_TYPE__NAME = ANY_DATE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDATE_TYPE__COMMENT = ANY_DATE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDATE_TYPE__ATTRIBUTES = ANY_DATE_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDATE_TYPE__VERSION_INFO = ANY_DATE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDATE_TYPE__IDENTIFICATION = ANY_DATE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDATE_TYPE__COMPILER_INFO = ANY_DATE_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDATE_TYPE__TYPE_ENTRY = ANY_DATE_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Ldate Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LDATE_TYPE_FEATURE_COUNT = ANY_DATE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.InternalDataTypeImpl <em>Internal Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.InternalDataTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getInternalDataType()
	 * @generated
	 */
	int INTERNAL_DATA_TYPE = 55;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_DATA_TYPE__NAME = DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_DATA_TYPE__COMMENT = DATA_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Attributes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_DATA_TYPE__ATTRIBUTES = DATA_TYPE__ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_DATA_TYPE__VERSION_INFO = DATA_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_DATA_TYPE__IDENTIFICATION = DATA_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Compiler Info</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_DATA_TYPE__COMPILER_INFO = DATA_TYPE__COMPILER_INFO;

	/**
	 * The feature id for the '<em><b>Type Entry</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_DATA_TYPE__TYPE_ENTRY = DATA_TYPE__TYPE_ENTRY;

	/**
	 * The number of structural features of the '<em>Internal Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_DATA_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.BaseType1 <em>Base Type1</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.BaseType1
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getBaseType1()
	 * @generated
	 */
	int BASE_TYPE1 = 56;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyDerivedType <em>Any Derived Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Derived Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyDerivedType
	 * @generated
	 */
	EClass getAnyDerivedType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.ArrayType <em>Array Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Array Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.ArrayType
	 * @generated
	 */
	EClass getArrayType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.data.ArrayType#getSubranges <em>Subranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Subranges</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.ArrayType#getSubranges()
	 * @see #getArrayType()
	 * @generated
	 */
	EReference getArrayType_Subranges();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.data.ArrayType#getInitialValues <em>Initial Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Values</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.ArrayType#getInitialValues()
	 * @see #getArrayType()
	 * @generated
	 */
	EAttribute getArrayType_InitialValues();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.data.ArrayType#getBaseType <em>Base Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.ArrayType#getBaseType()
	 * @see #getArrayType()
	 * @generated
	 */
	EReference getArrayType_BaseType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.DataType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.DataType
	 * @generated
	 */
	EClass getDataType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.DirectlyDerivedType <em>Directly Derived Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Directly Derived Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.DirectlyDerivedType
	 * @generated
	 */
	EClass getDirectlyDerivedType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.data.DirectlyDerivedType#getBaseType <em>Base Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.DirectlyDerivedType#getBaseType()
	 * @see #getDirectlyDerivedType()
	 * @generated
	 */
	EReference getDirectlyDerivedType_BaseType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.data.DirectlyDerivedType#getInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.DirectlyDerivedType#getInitialValue()
	 * @see #getDirectlyDerivedType()
	 * @generated
	 */
	EAttribute getDirectlyDerivedType_InitialValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.EnumeratedType <em>Enumerated Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumerated Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedType
	 * @generated
	 */
	EClass getEnumeratedType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.data.EnumeratedType#getEnumeratedValues <em>Enumerated Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Enumerated Values</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedType#getEnumeratedValues()
	 * @see #getEnumeratedType()
	 * @generated
	 */
	EReference getEnumeratedType_EnumeratedValues();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.EnumeratedValue <em>Enumerated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumerated Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedValue
	 * @generated
	 */
	EClass getEnumeratedValue();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.fordiac.ide.model.data.EnumeratedValue#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedValue#getType()
	 * @see #getEnumeratedValue()
	 * @generated
	 */
	EReference getEnumeratedValue_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.StructuredType <em>Structured Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structured Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.StructuredType
	 * @generated
	 */
	EClass getStructuredType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.data.StructuredType#getMemberVariables <em>Member Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Member Variables</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.StructuredType#getMemberVariables()
	 * @see #getStructuredType()
	 * @generated
	 */
	EReference getStructuredType_MemberVariables();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.Subrange <em>Subrange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subrange</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.Subrange
	 * @generated
	 */
	EClass getSubrange();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.data.Subrange#getLowerLimit <em>Lower Limit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Limit</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.Subrange#getLowerLimit()
	 * @see #getSubrange()
	 * @generated
	 */
	EAttribute getSubrange_LowerLimit();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.data.Subrange#getUpperLimit <em>Upper Limit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Limit</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.Subrange#getUpperLimit()
	 * @see #getSubrange()
	 * @generated
	 */
	EAttribute getSubrange_UpperLimit();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.SubrangeType <em>Subrange Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subrange Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.SubrangeType
	 * @generated
	 */
	EClass getSubrangeType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.model.data.SubrangeType#getSubrange <em>Subrange</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subrange</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.SubrangeType#getSubrange()
	 * @see #getSubrangeType()
	 * @generated
	 */
	EReference getSubrangeType_Subrange();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.ValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.ValueType
	 * @generated
	 */
	EClass getValueType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.DerivedType <em>Derived Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Derived Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.DerivedType
	 * @generated
	 */
	EClass getDerivedType();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.fordiac.ide.model.data.DerivedType#getBaseType <em>Base Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.DerivedType#getBaseType()
	 * @see #getDerivedType()
	 * @generated
	 */
	EReference getDerivedType_BaseType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.EventType <em>Event Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.EventType
	 * @generated
	 */
	EClass getEventType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyType <em>Any Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyType
	 * @generated
	 */
	EClass getAnyType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyElementaryType <em>Any Elementary Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Elementary Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyElementaryType
	 * @generated
	 */
	EClass getAnyElementaryType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyMagnitudeType <em>Any Magnitude Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Magnitude Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyMagnitudeType
	 * @generated
	 */
	EClass getAnyMagnitudeType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyNumType <em>Any Num Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Num Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyNumType
	 * @generated
	 */
	EClass getAnyNumType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyRealType <em>Any Real Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Real Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyRealType
	 * @generated
	 */
	EClass getAnyRealType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.RealType <em>Real Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Real Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.RealType
	 * @generated
	 */
	EClass getRealType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.LrealType <em>Lreal Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lreal Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.LrealType
	 * @generated
	 */
	EClass getLrealType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyIntType <em>Any Int Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Int Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyIntType
	 * @generated
	 */
	EClass getAnyIntType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyUnsignedType <em>Any Unsigned Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Unsigned Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyUnsignedType
	 * @generated
	 */
	EClass getAnyUnsignedType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.UsintType <em>Usint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Usint Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.UsintType
	 * @generated
	 */
	EClass getUsintType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.UintType <em>Uint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Uint Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.UintType
	 * @generated
	 */
	EClass getUintType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.UdintType <em>Udint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Udint Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.UdintType
	 * @generated
	 */
	EClass getUdintType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.UlintType <em>Ulint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ulint Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.UlintType
	 * @generated
	 */
	EClass getUlintType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnySignedType <em>Any Signed Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Signed Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnySignedType
	 * @generated
	 */
	EClass getAnySignedType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.SintType <em>Sint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sint Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.SintType
	 * @generated
	 */
	EClass getSintType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.IntType <em>Int Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Int Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.IntType
	 * @generated
	 */
	EClass getIntType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.DintType <em>Dint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dint Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.DintType
	 * @generated
	 */
	EClass getDintType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.LintType <em>Lint Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lint Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.LintType
	 * @generated
	 */
	EClass getLintType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyDurationType <em>Any Duration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Duration Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyDurationType
	 * @generated
	 */
	EClass getAnyDurationType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.TimeType <em>Time Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.TimeType
	 * @generated
	 */
	EClass getTimeType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.LtimeType <em>Ltime Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ltime Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.LtimeType
	 * @generated
	 */
	EClass getLtimeType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyBitType <em>Any Bit Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Bit Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyBitType
	 * @generated
	 */
	EClass getAnyBitType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.BoolType <em>Bool Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bool Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.BoolType
	 * @generated
	 */
	EClass getBoolType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.ByteType <em>Byte Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Byte Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.ByteType
	 * @generated
	 */
	EClass getByteType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.WordType <em>Word Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Word Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.WordType
	 * @generated
	 */
	EClass getWordType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.DwordType <em>Dword Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dword Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.DwordType
	 * @generated
	 */
	EClass getDwordType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.LwordType <em>Lword Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Lword Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.LwordType
	 * @generated
	 */
	EClass getLwordType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyCharsType <em>Any Chars Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Chars Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyCharsType
	 * @generated
	 */
	EClass getAnyCharsType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnySCharsType <em>Any SChars Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any SChars Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnySCharsType
	 * @generated
	 */
	EClass getAnySCharsType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyWCharsType <em>Any WChars Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any WChars Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyWCharsType
	 * @generated
	 */
	EClass getAnyWCharsType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyStringType <em>Any String Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any String Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyStringType
	 * @generated
	 */
	EClass getAnyStringType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.data.AnyStringType#getMaxLength <em>Max Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Length</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyStringType#getMaxLength()
	 * @see #getAnyStringType()
	 * @generated
	 */
	EAttribute getAnyStringType_MaxLength();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.StringType <em>String Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.StringType
	 * @generated
	 */
	EClass getStringType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.WstringType <em>Wstring Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wstring Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.WstringType
	 * @generated
	 */
	EClass getWstringType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyCharType <em>Any Char Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Char Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyCharType
	 * @generated
	 */
	EClass getAnyCharType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.CharType <em>Char Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Char Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.CharType
	 * @generated
	 */
	EClass getCharType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.WcharType <em>Wchar Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Wchar Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.WcharType
	 * @generated
	 */
	EClass getWcharType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.AnyDateType <em>Any Date Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Any Date Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.AnyDateType
	 * @generated
	 */
	EClass getAnyDateType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.DateAndTimeType <em>Date And Time Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date And Time Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.DateAndTimeType
	 * @generated
	 */
	EClass getDateAndTimeType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.LdtType <em>Ldt Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ldt Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.LdtType
	 * @generated
	 */
	EClass getLdtType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.DateType <em>Date Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.DateType
	 * @generated
	 */
	EClass getDateType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.TimeOfDayType <em>Time Of Day Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Of Day Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.TimeOfDayType
	 * @generated
	 */
	EClass getTimeOfDayType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.LtodType <em>Ltod Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ltod Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.LtodType
	 * @generated
	 */
	EClass getLtodType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.LdateType <em>Ldate Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ldate Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.LdateType
	 * @generated
	 */
	EClass getLdateType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.InternalDataType <em>Internal Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Internal Data Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.InternalDataType
	 * @generated
	 */
	EClass getInternalDataType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.fordiac.ide.model.data.BaseType1 <em>Base Type1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Base Type1</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.BaseType1
	 * @generated
	 */
	EEnum getBaseType1();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DataFactory getDataFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyDerivedTypeImpl <em>Any Derived Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyDerivedTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyDerivedType()
		 * @generated
		 */
		EClass ANY_DERIVED_TYPE = eINSTANCE.getAnyDerivedType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.ArrayTypeImpl <em>Array Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.ArrayTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getArrayType()
		 * @generated
		 */
		EClass ARRAY_TYPE = eINSTANCE.getArrayType();

		/**
		 * The meta object literal for the '<em><b>Subranges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARRAY_TYPE__SUBRANGES = eINSTANCE.getArrayType_Subranges();

		/**
		 * The meta object literal for the '<em><b>Initial Values</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARRAY_TYPE__INITIAL_VALUES = eINSTANCE.getArrayType_InitialValues();

		/**
		 * The meta object literal for the '<em><b>Base Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARRAY_TYPE__BASE_TYPE = eINSTANCE.getArrayType_BaseType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.DataTypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDataType()
		 * @generated
		 */
		EClass DATA_TYPE = eINSTANCE.getDataType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.DirectlyDerivedTypeImpl <em>Directly Derived Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.DirectlyDerivedTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDirectlyDerivedType()
		 * @generated
		 */
		EClass DIRECTLY_DERIVED_TYPE = eINSTANCE.getDirectlyDerivedType();

		/**
		 * The meta object literal for the '<em><b>Base Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIRECTLY_DERIVED_TYPE__BASE_TYPE = eINSTANCE.getDirectlyDerivedType_BaseType();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIRECTLY_DERIVED_TYPE__INITIAL_VALUE = eINSTANCE.getDirectlyDerivedType_InitialValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.EnumeratedTypeImpl <em>Enumerated Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.EnumeratedTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getEnumeratedType()
		 * @generated
		 */
		EClass ENUMERATED_TYPE = eINSTANCE.getEnumeratedType();

		/**
		 * The meta object literal for the '<em><b>Enumerated Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATED_TYPE__ENUMERATED_VALUES = eINSTANCE.getEnumeratedType_EnumeratedValues();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.EnumeratedValueImpl <em>Enumerated Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.EnumeratedValueImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getEnumeratedValue()
		 * @generated
		 */
		EClass ENUMERATED_VALUE = eINSTANCE.getEnumeratedValue();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATED_VALUE__TYPE = eINSTANCE.getEnumeratedValue_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.StructuredTypeImpl <em>Structured Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.StructuredTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getStructuredType()
		 * @generated
		 */
		EClass STRUCTURED_TYPE = eINSTANCE.getStructuredType();

		/**
		 * The meta object literal for the '<em><b>Member Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURED_TYPE__MEMBER_VARIABLES = eINSTANCE.getStructuredType_MemberVariables();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.SubrangeImpl <em>Subrange</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.SubrangeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getSubrange()
		 * @generated
		 */
		EClass SUBRANGE = eINSTANCE.getSubrange();

		/**
		 * The meta object literal for the '<em><b>Lower Limit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBRANGE__LOWER_LIMIT = eINSTANCE.getSubrange_LowerLimit();

		/**
		 * The meta object literal for the '<em><b>Upper Limit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SUBRANGE__UPPER_LIMIT = eINSTANCE.getSubrange_UpperLimit();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.SubrangeTypeImpl <em>Subrange Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.SubrangeTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getSubrangeType()
		 * @generated
		 */
		EClass SUBRANGE_TYPE = eINSTANCE.getSubrangeType();

		/**
		 * The meta object literal for the '<em><b>Subrange</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SUBRANGE_TYPE__SUBRANGE = eINSTANCE.getSubrangeType_Subrange();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.ValueTypeImpl <em>Value Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.ValueTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getValueType()
		 * @generated
		 */
		EClass VALUE_TYPE = eINSTANCE.getValueType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.DerivedTypeImpl <em>Derived Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.DerivedTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDerivedType()
		 * @generated
		 */
		EClass DERIVED_TYPE = eINSTANCE.getDerivedType();

		/**
		 * The meta object literal for the '<em><b>Base Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DERIVED_TYPE__BASE_TYPE = eINSTANCE.getDerivedType_BaseType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.EventTypeImpl <em>Event Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.EventTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getEventType()
		 * @generated
		 */
		EClass EVENT_TYPE = eINSTANCE.getEventType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyTypeImpl <em>Any Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyType()
		 * @generated
		 */
		EClass ANY_TYPE = eINSTANCE.getAnyType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyElementaryTypeImpl <em>Any Elementary Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyElementaryTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyElementaryType()
		 * @generated
		 */
		EClass ANY_ELEMENTARY_TYPE = eINSTANCE.getAnyElementaryType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyMagnitudeTypeImpl <em>Any Magnitude Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyMagnitudeTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyMagnitudeType()
		 * @generated
		 */
		EClass ANY_MAGNITUDE_TYPE = eINSTANCE.getAnyMagnitudeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyNumTypeImpl <em>Any Num Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyNumTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyNumType()
		 * @generated
		 */
		EClass ANY_NUM_TYPE = eINSTANCE.getAnyNumType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyRealTypeImpl <em>Any Real Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyRealTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyRealType()
		 * @generated
		 */
		EClass ANY_REAL_TYPE = eINSTANCE.getAnyRealType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.RealTypeImpl <em>Real Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.RealTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getRealType()
		 * @generated
		 */
		EClass REAL_TYPE = eINSTANCE.getRealType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.LrealTypeImpl <em>Lreal Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.LrealTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLrealType()
		 * @generated
		 */
		EClass LREAL_TYPE = eINSTANCE.getLrealType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyIntTypeImpl <em>Any Int Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyIntTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyIntType()
		 * @generated
		 */
		EClass ANY_INT_TYPE = eINSTANCE.getAnyIntType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyUnsignedTypeImpl <em>Any Unsigned Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyUnsignedTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyUnsignedType()
		 * @generated
		 */
		EClass ANY_UNSIGNED_TYPE = eINSTANCE.getAnyUnsignedType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.UsintTypeImpl <em>Usint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.UsintTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getUsintType()
		 * @generated
		 */
		EClass USINT_TYPE = eINSTANCE.getUsintType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.UintTypeImpl <em>Uint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.UintTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getUintType()
		 * @generated
		 */
		EClass UINT_TYPE = eINSTANCE.getUintType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.UdintTypeImpl <em>Udint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.UdintTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getUdintType()
		 * @generated
		 */
		EClass UDINT_TYPE = eINSTANCE.getUdintType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.UlintTypeImpl <em>Ulint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.UlintTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getUlintType()
		 * @generated
		 */
		EClass ULINT_TYPE = eINSTANCE.getUlintType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnySignedTypeImpl <em>Any Signed Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnySignedTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnySignedType()
		 * @generated
		 */
		EClass ANY_SIGNED_TYPE = eINSTANCE.getAnySignedType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.SintTypeImpl <em>Sint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.SintTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getSintType()
		 * @generated
		 */
		EClass SINT_TYPE = eINSTANCE.getSintType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.IntTypeImpl <em>Int Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.IntTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getIntType()
		 * @generated
		 */
		EClass INT_TYPE = eINSTANCE.getIntType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.DintTypeImpl <em>Dint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.DintTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDintType()
		 * @generated
		 */
		EClass DINT_TYPE = eINSTANCE.getDintType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.LintTypeImpl <em>Lint Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.LintTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLintType()
		 * @generated
		 */
		EClass LINT_TYPE = eINSTANCE.getLintType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyDurationTypeImpl <em>Any Duration Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyDurationTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyDurationType()
		 * @generated
		 */
		EClass ANY_DURATION_TYPE = eINSTANCE.getAnyDurationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.TimeTypeImpl <em>Time Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.TimeTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getTimeType()
		 * @generated
		 */
		EClass TIME_TYPE = eINSTANCE.getTimeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.LtimeTypeImpl <em>Ltime Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.LtimeTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLtimeType()
		 * @generated
		 */
		EClass LTIME_TYPE = eINSTANCE.getLtimeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyBitTypeImpl <em>Any Bit Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyBitTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyBitType()
		 * @generated
		 */
		EClass ANY_BIT_TYPE = eINSTANCE.getAnyBitType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.BoolTypeImpl <em>Bool Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.BoolTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getBoolType()
		 * @generated
		 */
		EClass BOOL_TYPE = eINSTANCE.getBoolType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.ByteTypeImpl <em>Byte Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.ByteTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getByteType()
		 * @generated
		 */
		EClass BYTE_TYPE = eINSTANCE.getByteType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.WordTypeImpl <em>Word Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.WordTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getWordType()
		 * @generated
		 */
		EClass WORD_TYPE = eINSTANCE.getWordType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.DwordTypeImpl <em>Dword Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.DwordTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDwordType()
		 * @generated
		 */
		EClass DWORD_TYPE = eINSTANCE.getDwordType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.LwordTypeImpl <em>Lword Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.LwordTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLwordType()
		 * @generated
		 */
		EClass LWORD_TYPE = eINSTANCE.getLwordType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyCharsTypeImpl <em>Any Chars Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyCharsTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyCharsType()
		 * @generated
		 */
		EClass ANY_CHARS_TYPE = eINSTANCE.getAnyCharsType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnySCharsTypeImpl <em>Any SChars Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnySCharsTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnySCharsType()
		 * @generated
		 */
		EClass ANY_SCHARS_TYPE = eINSTANCE.getAnySCharsType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyWCharsTypeImpl <em>Any WChars Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyWCharsTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyWCharsType()
		 * @generated
		 */
		EClass ANY_WCHARS_TYPE = eINSTANCE.getAnyWCharsType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyStringTypeImpl <em>Any String Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyStringTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyStringType()
		 * @generated
		 */
		EClass ANY_STRING_TYPE = eINSTANCE.getAnyStringType();

		/**
		 * The meta object literal for the '<em><b>Max Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANY_STRING_TYPE__MAX_LENGTH = eINSTANCE.getAnyStringType_MaxLength();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.StringTypeImpl <em>String Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.StringTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getStringType()
		 * @generated
		 */
		EClass STRING_TYPE = eINSTANCE.getStringType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.WstringTypeImpl <em>Wstring Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.WstringTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getWstringType()
		 * @generated
		 */
		EClass WSTRING_TYPE = eINSTANCE.getWstringType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyCharTypeImpl <em>Any Char Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyCharTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyCharType()
		 * @generated
		 */
		EClass ANY_CHAR_TYPE = eINSTANCE.getAnyCharType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.CharTypeImpl <em>Char Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.CharTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getCharType()
		 * @generated
		 */
		EClass CHAR_TYPE = eINSTANCE.getCharType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.WcharTypeImpl <em>Wchar Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.WcharTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getWcharType()
		 * @generated
		 */
		EClass WCHAR_TYPE = eINSTANCE.getWcharType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.AnyDateTypeImpl <em>Any Date Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.AnyDateTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getAnyDateType()
		 * @generated
		 */
		EClass ANY_DATE_TYPE = eINSTANCE.getAnyDateType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.DateAndTimeTypeImpl <em>Date And Time Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.DateAndTimeTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDateAndTimeType()
		 * @generated
		 */
		EClass DATE_AND_TIME_TYPE = eINSTANCE.getDateAndTimeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.LdtTypeImpl <em>Ldt Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.LdtTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLdtType()
		 * @generated
		 */
		EClass LDT_TYPE = eINSTANCE.getLdtType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.DateTypeImpl <em>Date Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.DateTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDateType()
		 * @generated
		 */
		EClass DATE_TYPE = eINSTANCE.getDateType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.TimeOfDayTypeImpl <em>Time Of Day Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.TimeOfDayTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getTimeOfDayType()
		 * @generated
		 */
		EClass TIME_OF_DAY_TYPE = eINSTANCE.getTimeOfDayType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.LtodTypeImpl <em>Ltod Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.LtodTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLtodType()
		 * @generated
		 */
		EClass LTOD_TYPE = eINSTANCE.getLtodType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.LdateTypeImpl <em>Ldate Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.LdateTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getLdateType()
		 * @generated
		 */
		EClass LDATE_TYPE = eINSTANCE.getLdateType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.InternalDataTypeImpl <em>Internal Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.InternalDataTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getInternalDataType()
		 * @generated
		 */
		EClass INTERNAL_DATA_TYPE = eINSTANCE.getInternalDataType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.BaseType1 <em>Base Type1</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.BaseType1
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getBaseType1()
		 * @generated
		 */
		EEnum BASE_TYPE1 = eINSTANCE.getBaseType1();

	}

} //DataPackage
