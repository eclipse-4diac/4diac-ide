/********************************************************************************
 * Copyright (c) 2008, 2010, 2012 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Martin Jobst
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
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
	String eNAME = "data";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.model.datatype";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "data";

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
	int DATA_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__ANNOTATIONS = LibraryElementPackage.LIBRARY_ELEMENT__ANNOTATIONS;

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
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE__PALETTE_ENTRY = LibraryElementPackage.LIBRARY_ELEMENT__PALETTE_ENTRY;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_TYPE_FEATURE_COUNT = LibraryElementPackage.LIBRARY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.ArrayTypeImpl <em>Array Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.ArrayTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getArrayType()
	 * @generated
	 */
	int ARRAY_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__ANNOTATIONS = DATA_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__NAME = DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__COMMENT = DATA_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__VERSION_INFO = DATA_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__IDENTIFICATION = DATA_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__PALETTE_ENTRY = DATA_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Subranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__SUBRANGES = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Initial Values</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__INITIAL_VALUES = DATA_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE__BASE_TYPE = DATA_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Array Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARRAY_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 3;

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
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE__ANNOTATIONS = DATA_TYPE__ANNOTATIONS;

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
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE__PALETTE_ENTRY = DATA_TYPE__PALETTE_ENTRY;

	/**
	 * The number of structural features of the '<em>Value Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.DerivedTypeImpl <em>Derived Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.DerivedTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDerivedType()
	 * @generated
	 */
	int DERIVED_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE__ANNOTATIONS = VALUE_TYPE__ANNOTATIONS;

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
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_TYPE__PALETTE_ENTRY = VALUE_TYPE__PALETTE_ENTRY;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.DirectlyDerivedTypeImpl <em>Directly Derived Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.DirectlyDerivedTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getDirectlyDerivedType()
	 * @generated
	 */
	int DIRECTLY_DERIVED_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__ANNOTATIONS = DERIVED_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__NAME = DERIVED_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__COMMENT = DERIVED_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__VERSION_INFO = DERIVED_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__IDENTIFICATION = DERIVED_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__PALETTE_ENTRY = DERIVED_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Base Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE__BASE_TYPE = DERIVED_TYPE__BASE_TYPE;

	/**
	 * The number of structural features of the '<em>Directly Derived Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIRECTLY_DERIVED_TYPE_FEATURE_COUNT = DERIVED_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.EnumeratedTypeImpl <em>Enumerated Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.EnumeratedTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getEnumeratedType()
	 * @generated
	 */
	int ENUMERATED_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__ANNOTATIONS = VALUE_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__NAME = VALUE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__COMMENT = VALUE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__VERSION_INFO = VALUE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__IDENTIFICATION = VALUE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__PALETTE_ENTRY = VALUE_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Enumerated Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE__ENUMERATED_VALUE = VALUE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enumerated Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_TYPE_FEATURE_COUNT = VALUE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.EnumeratedValueImpl <em>Enumerated Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.EnumeratedValueImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getEnumeratedValue()
	 * @generated
	 */
	int ENUMERATED_VALUE = 4;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_VALUE__COMMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_VALUE__NAME = 1;

	/**
	 * The number of structural features of the '<em>Enumerated Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATED_VALUE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.StructuredTypeImpl <em>Structured Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.StructuredTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getStructuredType()
	 * @generated
	 */
	int STRUCTURED_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__ANNOTATIONS = DATA_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__NAME = DATA_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__COMMENT = DATA_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__VERSION_INFO = DATA_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__IDENTIFICATION = DATA_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__PALETTE_ENTRY = DATA_TYPE__PALETTE_ENTRY;

	/**
	 * The feature id for the '<em><b>Var Declaration</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE__VAR_DECLARATION = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Structured Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.SubrangeImpl <em>Subrange</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.SubrangeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getSubrange()
	 * @generated
	 */
	int SUBRANGE = 6;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.SubrangeTypeImpl <em>Subrange Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.SubrangeTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getSubrangeType()
	 * @generated
	 */
	int SUBRANGE_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__ANNOTATIONS = DERIVED_TYPE__ANNOTATIONS;

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
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBRANGE_TYPE__PALETTE_ENTRY = DERIVED_TYPE__PALETTE_ENTRY;

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
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.VarInitializationImpl <em>Var Initialization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.VarInitializationImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getVarInitialization()
	 * @generated
	 */
	int VAR_INITIALIZATION = 8;

	/**
	 * The feature id for the '<em><b>Initial Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_INITIALIZATION__INITIAL_VALUE = 0;

	/**
	 * The number of structural features of the '<em>Var Initialization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAR_INITIALIZATION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.ElementaryTypeImpl <em>Elementary Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.ElementaryTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getElementaryType()
	 * @generated
	 */
	int ELEMENTARY_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENTARY_TYPE__ANNOTATIONS = VALUE_TYPE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENTARY_TYPE__NAME = VALUE_TYPE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENTARY_TYPE__COMMENT = VALUE_TYPE__COMMENT;

	/**
	 * The feature id for the '<em><b>Version Info</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENTARY_TYPE__VERSION_INFO = VALUE_TYPE__VERSION_INFO;

	/**
	 * The feature id for the '<em><b>Identification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENTARY_TYPE__IDENTIFICATION = VALUE_TYPE__IDENTIFICATION;

	/**
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENTARY_TYPE__PALETTE_ENTRY = VALUE_TYPE__PALETTE_ENTRY;

	/**
	 * The number of structural features of the '<em>Elementary Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENTARY_TYPE_FEATURE_COUNT = VALUE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.impl.EventTypeImpl <em>Event Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.impl.EventTypeImpl
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getEventType()
	 * @generated
	 */
	int EVENT_TYPE = 12;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__ANNOTATIONS = DATA_TYPE__ANNOTATIONS;

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
	 * The feature id for the '<em><b>Palette Entry</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE__PALETTE_ENTRY = DATA_TYPE__PALETTE_ENTRY;

	/**
	 * The number of structural features of the '<em>Event Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_TYPE_FEATURE_COUNT = DATA_TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.model.data.BaseType1 <em>Base Type1</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.model.data.BaseType1
	 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getBaseType1()
	 * @generated
	 */
	int BASE_TYPE1 = 13;


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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.EnumeratedType <em>Enumerated Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumerated Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedType
	 * @generated
	 */
	EClass getEnumeratedType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.data.EnumeratedType#getEnumeratedValue <em>Enumerated Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Enumerated Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedType#getEnumeratedValue()
	 * @see #getEnumeratedType()
	 * @generated
	 */
	EReference getEnumeratedType_EnumeratedValue();

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
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.data.EnumeratedValue#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedValue#getComment()
	 * @see #getEnumeratedValue()
	 * @generated
	 */
	EAttribute getEnumeratedValue_Comment();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.data.EnumeratedValue#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.EnumeratedValue#getName()
	 * @see #getEnumeratedValue()
	 * @generated
	 */
	EAttribute getEnumeratedValue_Name();

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
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.model.data.StructuredType#getVarDeclaration <em>Var Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Var Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.StructuredType#getVarDeclaration()
	 * @see #getStructuredType()
	 * @generated
	 */
	EReference getStructuredType_VarDeclaration();

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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.VarInitialization <em>Var Initialization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Var Initialization</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.VarInitialization
	 * @generated
	 */
	EClass getVarInitialization();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.model.data.VarInitialization#getInitialValue <em>Initial Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Initial Value</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.VarInitialization#getInitialValue()
	 * @see #getVarInitialization()
	 * @generated
	 */
	EAttribute getVarInitialization_InitialValue();

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
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.model.data.ElementaryType <em>Elementary Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Elementary Type</em>'.
	 * @see org.eclipse.fordiac.ide.model.data.ElementaryType
	 * @generated
	 */
	EClass getElementaryType();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.EnumeratedTypeImpl <em>Enumerated Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.EnumeratedTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getEnumeratedType()
		 * @generated
		 */
		EClass ENUMERATED_TYPE = eINSTANCE.getEnumeratedType();

		/**
		 * The meta object literal for the '<em><b>Enumerated Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATED_TYPE__ENUMERATED_VALUE = eINSTANCE.getEnumeratedType_EnumeratedValue();

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
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUMERATED_VALUE__COMMENT = eINSTANCE.getEnumeratedValue_Comment();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUMERATED_VALUE__NAME = eINSTANCE.getEnumeratedValue_Name();

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
		 * The meta object literal for the '<em><b>Var Declaration</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STRUCTURED_TYPE__VAR_DECLARATION = eINSTANCE.getStructuredType_VarDeclaration();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.VarInitializationImpl <em>Var Initialization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.VarInitializationImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getVarInitialization()
		 * @generated
		 */
		EClass VAR_INITIALIZATION = eINSTANCE.getVarInitialization();

		/**
		 * The meta object literal for the '<em><b>Initial Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAR_INITIALIZATION__INITIAL_VALUE = eINSTANCE.getVarInitialization_InitialValue();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.model.data.impl.ElementaryTypeImpl <em>Elementary Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.model.data.impl.ElementaryTypeImpl
		 * @see org.eclipse.fordiac.ide.model.data.impl.DataPackageImpl#getElementaryType()
		 * @generated
		 */
		EClass ELEMENTARY_TYPE = eINSTANCE.getElementaryType();

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
