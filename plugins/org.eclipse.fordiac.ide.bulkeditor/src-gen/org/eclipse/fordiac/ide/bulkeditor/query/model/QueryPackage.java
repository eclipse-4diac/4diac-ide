/**
 * *******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.bulkeditor.query.model;

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
 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryFactory
 * @model kind="package"
 * @generated
 */
public interface QueryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.fordiac.ide.bulkeditor.query"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "query"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	QueryPackage eINSTANCE = org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConstraintImpl <em>Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConstraintImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getConstraint()
	 * @generated
	 */
	int CONSTRAINT = 0;

	/**
	 * The feature id for the '<em><b>Negate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__NEGATE = 0;

	/**
	 * The feature id for the '<em><b>Or Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__OR_CONSTRAINT = 1;

	/**
	 * The feature id for the '<em><b>And Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__AND_CONSTRAINT = 2;

	/**
	 * The number of structural features of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PropertyConstraintImpl <em>Property Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PropertyConstraintImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getPropertyConstraint()
	 * @generated
	 */
	int PROPERTY_CONSTRAINT = 1;

	/**
	 * The feature id for the '<em><b>Negate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONSTRAINT__NEGATE = CONSTRAINT__NEGATE;

	/**
	 * The feature id for the '<em><b>Or Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONSTRAINT__OR_CONSTRAINT = CONSTRAINT__OR_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>And Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONSTRAINT__AND_CONSTRAINT = CONSTRAINT__AND_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONSTRAINT__NAME = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONSTRAINT__TYPE = CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONSTRAINT__COMMENT = CONSTRAINT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONSTRAINT__VALUE = CONSTRAINT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Property Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_CONSTRAINT_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FieldConstraintImpl <em>Field Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FieldConstraintImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getFieldConstraint()
	 * @generated
	 */
	int FIELD_CONSTRAINT = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_CONSTRAINT__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Case Sensitive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_CONSTRAINT__CASE_SENSITIVE = 1;

	/**
	 * The feature id for the '<em><b>Whole Word</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_CONSTRAINT__WHOLE_WORD = 2;

	/**
	 * The feature id for the '<em><b>Entire</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_CONSTRAINT__ENTIRE = 3;

	/**
	 * The feature id for the '<em><b>Regex</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_CONSTRAINT__REGEX = 4;

	/**
	 * The number of structural features of the '<em>Field Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FIELD_CONSTRAINT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeConstraintImpl <em>Attribute Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeConstraintImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getAttributeConstraint()
	 * @generated
	 */
	int ATTRIBUTE_CONSTRAINT = 3;

	/**
	 * The feature id for the '<em><b>Negate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__NEGATE = PROPERTY_CONSTRAINT__NEGATE;

	/**
	 * The feature id for the '<em><b>Or Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__OR_CONSTRAINT = PROPERTY_CONSTRAINT__OR_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>And Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__AND_CONSTRAINT = PROPERTY_CONSTRAINT__AND_CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__NAME = PROPERTY_CONSTRAINT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__TYPE = PROPERTY_CONSTRAINT__TYPE;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__COMMENT = PROPERTY_CONSTRAINT__COMMENT;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT__VALUE = PROPERTY_CONSTRAINT__VALUE;

	/**
	 * The number of structural features of the '<em>Attribute Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_CONSTRAINT_FEATURE_COUNT = PROPERTY_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.OccurrenceImpl <em>Occurrence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.OccurrenceImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getOccurrence()
	 * @generated
	 */
	int OCCURRENCE = 4;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCCURRENCE__CONSTRAINT = 0;

	/**
	 * The number of structural features of the '<em>Occurrence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCCURRENCE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ApplicationOccurrenceImpl <em>Application Occurrence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ApplicationOccurrenceImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getApplicationOccurrence()
	 * @generated
	 */
	int APPLICATION_OCCURRENCE = 5;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_OCCURRENCE__CONSTRAINT = OCCURRENCE__CONSTRAINT;

	/**
	 * The number of structural features of the '<em>Application Occurrence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_OCCURRENCE_FEATURE_COUNT = OCCURRENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeFBOccurrenceImpl <em>Composite FB Occurrence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeFBOccurrenceImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getCompositeFBOccurrence()
	 * @generated
	 */
	int COMPOSITE_FB_OCCURRENCE = 6;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_OCCURRENCE__CONSTRAINT = OCCURRENCE__CONSTRAINT;

	/**
	 * The number of structural features of the '<em>Composite FB Occurrence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_OCCURRENCE_FEATURE_COUNT = OCCURRENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypedSubappOccurrenceImpl <em>Typed Subapp Occurrence</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypedSubappOccurrenceImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getTypedSubappOccurrence()
	 * @generated
	 */
	int TYPED_SUBAPP_OCCURRENCE = 7;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUBAPP_OCCURRENCE__CONSTRAINT = OCCURRENCE__CONSTRAINT;

	/**
	 * The number of structural features of the '<em>Typed Subapp Occurrence</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUBAPP_OCCURRENCE_FEATURE_COUNT = OCCURRENCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryImpl <em>Query</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getQuery()
	 * @generated
	 */
	int QUERY = 8;

	/**
	 * The feature id for the '<em><b>Placeholder</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY__PLACEHOLDER = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Place</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY__PLACE = 2;

	/**
	 * The number of structural features of the '<em>Query</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceholderImpl <em>Placeholder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceholderImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getPlaceholder()
	 * @generated
	 */
	int PLACEHOLDER = 9;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACEHOLDER__KEY = 0;

	/**
	 * The feature id for the '<em><b>Val</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACEHOLDER__VAL = 1;

	/**
	 * The number of structural features of the '<em>Placeholder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACEHOLDER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TargetImpl <em>Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TargetImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getTarget()
	 * @generated
	 */
	int TARGET = 10;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__TARGET = 0;

	/**
	 * The number of structural features of the '<em>Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TargetOptionImpl <em>Target Option</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TargetOptionImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getTargetOption()
	 * @generated
	 */
	int TARGET_OPTION = 11;

	/**
	 * The number of structural features of the '<em>Target Option</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_OPTION_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl <em>Place</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getPlace()
	 * @generated
	 */
	int PLACE = 12;

	/**
	 * The feature id for the '<em><b>Ignore Linked Libraries</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__IGNORE_LINKED_LIBRARIES = 0;

	/**
	 * The feature id for the '<em><b>Subapp Hierarchy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__SUBAPP_HIERARCHY = 1;

	/**
	 * The feature id for the '<em><b>Subapp Hierarchy Elements</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__SUBAPP_HIERARCHY_ELEMENTS = 2;

	/**
	 * The feature id for the '<em><b>Simple FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__SIMPLE_FB = 3;

	/**
	 * The feature id for the '<em><b>Basic FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__BASIC_FB = 4;

	/**
	 * The feature id for the '<em><b>Composite FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__COMPOSITE_FB = 5;

	/**
	 * The feature id for the '<em><b>Service Interface FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__SERVICE_INTERFACE_FB = 6;

	/**
	 * The feature id for the '<em><b>Function FB</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__FUNCTION_FB = 7;

	/**
	 * The feature id for the '<em><b>Typed Subapp</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__TYPED_SUBAPP = 8;

	/**
	 * The feature id for the '<em><b>Untyped Subapp</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__UNTYPED_SUBAPP = 9;

	/**
	 * The feature id for the '<em><b>Connection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__CONNECTION = 10;

	/**
	 * The feature id for the '<em><b>Simple Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__SIMPLE_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Basic Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__BASIC_TYPE = 12;

	/**
	 * The feature id for the '<em><b>Composite Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__COMPOSITE_TYPE = 13;

	/**
	 * The feature id for the '<em><b>Service Interface Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__SERVICE_INTERFACE_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Function Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__FUNCTION_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Subapp Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__SUBAPP_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Struct Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__STRUCT_TYPE = 17;

	/**
	 * The feature id for the '<em><b>Attribute Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE__ATTRIBUTE_TYPE = 18;

	/**
	 * The number of structural features of the '<em>Place</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLACE_FEATURE_COUNT = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SearchTargetImpl <em>Search Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SearchTargetImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getSearchTarget()
	 * @generated
	 */
	int SEARCH_TARGET = 13;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_TARGET__CONSTRAINT = 0;

	/**
	 * The number of structural features of the '<em>Search Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_TARGET_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypeImpl <em>Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypeImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getType()
	 * @generated
	 */
	int TYPE = 14;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__CONSTRAINT = SEARCH_TARGET__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE__PIN = SEARCH_TARGET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_FEATURE_COUNT = SEARCH_TARGET_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SimpleTypeImpl <em>Simple Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SimpleTypeImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getSimpleType()
	 * @generated
	 */
	int SIMPLE_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE__CONSTRAINT = TYPE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE__PIN = TYPE__PIN;

	/**
	 * The number of structural features of the '<em>Simple Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.BasicTypeImpl <em>Basic Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.BasicTypeImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getBasicType()
	 * @generated
	 */
	int BASIC_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_TYPE__CONSTRAINT = TYPE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_TYPE__PIN = TYPE__PIN;

	/**
	 * The number of structural features of the '<em>Basic Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeTypeImpl <em>Composite Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeTypeImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getCompositeType()
	 * @generated
	 */
	int COMPOSITE_TYPE = 17;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_TYPE__CONSTRAINT = TYPE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_TYPE__PIN = TYPE__PIN;

	/**
	 * The number of structural features of the '<em>Composite Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ServiceInterfaceTypeImpl <em>Service Interface Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ServiceInterfaceTypeImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getServiceInterfaceType()
	 * @generated
	 */
	int SERVICE_INTERFACE_TYPE = 18;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_TYPE__CONSTRAINT = TYPE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_TYPE__PIN = TYPE__PIN;

	/**
	 * The number of structural features of the '<em>Service Interface Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FunctionTypeImpl <em>Function Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FunctionTypeImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getFunctionType()
	 * @generated
	 */
	int FUNCTION_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__CONSTRAINT = TYPE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE__PIN = TYPE__PIN;

	/**
	 * The number of structural features of the '<em>Function Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SubappTypeImpl <em>Subapp Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SubappTypeImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getSubappType()
	 * @generated
	 */
	int SUBAPP_TYPE = 20;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_TYPE__CONSTRAINT = TYPE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_TYPE__PIN = TYPE__PIN;

	/**
	 * The number of structural features of the '<em>Subapp Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUBAPP_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.StructTypeImpl <em>Struct Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.StructTypeImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getStructType()
	 * @generated
	 */
	int STRUCT_TYPE = 21;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_TYPE__CONSTRAINT = TYPE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_TYPE__PIN = TYPE__PIN;

	/**
	 * The number of structural features of the '<em>Struct Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCT_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeTypeImpl <em>Attribute Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeTypeImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getAttributeType()
	 * @generated
	 */
	int ATTRIBUTE_TYPE = 22;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__CONSTRAINT = TYPE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE__PIN = TYPE__PIN;

	/**
	 * The number of structural features of the '<em>Attribute Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_TYPE_FEATURE_COUNT = TYPE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.NetworkElementImpl <em>Network Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.NetworkElementImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getNetworkElement()
	 * @generated
	 */
	int NETWORK_ELEMENT = 23;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK_ELEMENT__CONSTRAINT = SEARCH_TARGET__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK_ELEMENT__APPLICATION_OCCURRENCE = SEARCH_TARGET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE = SEARCH_TARGET_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE = SEARCH_TARGET_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Network Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NETWORK_ELEMENT_FEATURE_COUNT = SEARCH_TARGET_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.InstanceImpl <em>Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.InstanceImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getInstance()
	 * @generated
	 */
	int INSTANCE = 24;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__CONSTRAINT = NETWORK_ELEMENT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__APPLICATION_OCCURRENCE = NETWORK_ELEMENT__APPLICATION_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__COMPOSITE_FB_OCCURRENCE = NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__TYPED_SUBAPP_OCCURRENCE = NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE__PIN = NETWORK_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_FEATURE_COUNT = NETWORK_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SimpleFBImpl <em>Simple FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SimpleFBImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getSimpleFB()
	 * @generated
	 */
	int SIMPLE_FB = 25;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB__CONSTRAINT = INSTANCE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB__APPLICATION_OCCURRENCE = INSTANCE__APPLICATION_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB__COMPOSITE_FB_OCCURRENCE = INSTANCE__COMPOSITE_FB_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB__TYPED_SUBAPP_OCCURRENCE = INSTANCE__TYPED_SUBAPP_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB__PIN = INSTANCE__PIN;

	/**
	 * The number of structural features of the '<em>Simple FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_FB_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.BasicFBImpl <em>Basic FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.BasicFBImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getBasicFB()
	 * @generated
	 */
	int BASIC_FB = 26;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB__CONSTRAINT = INSTANCE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB__APPLICATION_OCCURRENCE = INSTANCE__APPLICATION_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB__COMPOSITE_FB_OCCURRENCE = INSTANCE__COMPOSITE_FB_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB__TYPED_SUBAPP_OCCURRENCE = INSTANCE__TYPED_SUBAPP_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB__PIN = INSTANCE__PIN;

	/**
	 * The number of structural features of the '<em>Basic FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASIC_FB_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeFBImpl <em>Composite FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeFBImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getCompositeFB()
	 * @generated
	 */
	int COMPOSITE_FB = 27;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB__CONSTRAINT = INSTANCE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB__APPLICATION_OCCURRENCE = INSTANCE__APPLICATION_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB__COMPOSITE_FB_OCCURRENCE = INSTANCE__COMPOSITE_FB_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB__TYPED_SUBAPP_OCCURRENCE = INSTANCE__TYPED_SUBAPP_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB__PIN = INSTANCE__PIN;

	/**
	 * The number of structural features of the '<em>Composite FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_FB_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ServiceInterfaceFBImpl <em>Service Interface FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ServiceInterfaceFBImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getServiceInterfaceFB()
	 * @generated
	 */
	int SERVICE_INTERFACE_FB = 28;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB__CONSTRAINT = INSTANCE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB__APPLICATION_OCCURRENCE = INSTANCE__APPLICATION_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB__COMPOSITE_FB_OCCURRENCE = INSTANCE__COMPOSITE_FB_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB__TYPED_SUBAPP_OCCURRENCE = INSTANCE__TYPED_SUBAPP_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB__PIN = INSTANCE__PIN;

	/**
	 * The number of structural features of the '<em>Service Interface FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_INTERFACE_FB_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FunctionFBImpl <em>Function FB</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FunctionFBImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getFunctionFB()
	 * @generated
	 */
	int FUNCTION_FB = 29;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB__CONSTRAINT = INSTANCE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB__APPLICATION_OCCURRENCE = INSTANCE__APPLICATION_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB__COMPOSITE_FB_OCCURRENCE = INSTANCE__COMPOSITE_FB_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB__TYPED_SUBAPP_OCCURRENCE = INSTANCE__TYPED_SUBAPP_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB__PIN = INSTANCE__PIN;

	/**
	 * The number of structural features of the '<em>Function FB</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FB_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypedSubappImpl <em>Typed Subapp</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypedSubappImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getTypedSubapp()
	 * @generated
	 */
	int TYPED_SUBAPP = 30;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUBAPP__CONSTRAINT = INSTANCE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUBAPP__APPLICATION_OCCURRENCE = INSTANCE__APPLICATION_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUBAPP__COMPOSITE_FB_OCCURRENCE = INSTANCE__COMPOSITE_FB_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUBAPP__TYPED_SUBAPP_OCCURRENCE = INSTANCE__TYPED_SUBAPP_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUBAPP__PIN = INSTANCE__PIN;

	/**
	 * The number of structural features of the '<em>Typed Subapp</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPED_SUBAPP_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.UntypedSubappImpl <em>Untyped Subapp</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.UntypedSubappImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getUntypedSubapp()
	 * @generated
	 */
	int UNTYPED_SUBAPP = 31;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUBAPP__CONSTRAINT = INSTANCE__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUBAPP__APPLICATION_OCCURRENCE = INSTANCE__APPLICATION_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUBAPP__COMPOSITE_FB_OCCURRENCE = INSTANCE__COMPOSITE_FB_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUBAPP__TYPED_SUBAPP_OCCURRENCE = INSTANCE__TYPED_SUBAPP_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Pin</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUBAPP__PIN = INSTANCE__PIN;

	/**
	 * The number of structural features of the '<em>Untyped Subapp</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTYPED_SUBAPP_FEATURE_COUNT = INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConnectionImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 32;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__CONSTRAINT = NETWORK_ELEMENT__CONSTRAINT;

	/**
	 * The feature id for the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__APPLICATION_OCCURRENCE = NETWORK_ELEMENT__APPLICATION_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__COMPOSITE_FB_OCCURRENCE = NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE;

	/**
	 * The feature id for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TYPED_SUBAPP_OCCURRENCE = NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = NETWORK_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PINImpl <em>PIN</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PINImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getPIN()
	 * @generated
	 */
	int PIN = 33;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIN__CONSTRAINT = SEARCH_TARGET__CONSTRAINT;

	/**
	 * The number of structural features of the '<em>PIN</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIN_FEATURE_COUNT = SEARCH_TARGET_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PinTargetImpl <em>Pin Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PinTargetImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getPinTarget()
	 * @generated
	 */
	int PIN_TARGET = 34;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIN_TARGET__CONSTRAINT = TARGET_OPTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Pin Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PIN_TARGET_FEATURE_COUNT = TARGET_OPTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeImpl <em>Attribute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getAttribute()
	 * @generated
	 */
	int ATTRIBUTE = 35;

	/**
	 * The feature id for the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE__CONSTRAINT = TARGET_OPTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Attribute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_FEATURE_COUNT = TARGET_OPTION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeDeclarationImpl <em>Attribute Declaration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeDeclarationImpl
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getAttributeDeclaration()
	 * @generated
	 */
	int ATTRIBUTE_DECLARATION = 36;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION__NAME = TARGET_OPTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Attribute Declaration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATTRIBUTE_DECLARATION_FEATURE_COUNT = TARGET_OPTION_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint
	 * @generated
	 */
	EClass getConstraint();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#isNegate <em>Negate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Negate</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#isNegate()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Negate();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#getOrConstraint <em>Or Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Or Constraint</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#getOrConstraint()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_OrConstraint();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#getAndConstraint <em>And Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>And Constraint</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#getAndConstraint()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_AndConstraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint <em>Property Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Constraint</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint
	 * @generated
	 */
	EClass getPropertyConstraint();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getName()
	 * @see #getPropertyConstraint()
	 * @generated
	 */
	EReference getPropertyConstraint_Name();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getType()
	 * @see #getPropertyConstraint()
	 * @generated
	 */
	EReference getPropertyConstraint_Type();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Comment</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getComment()
	 * @see #getPropertyConstraint()
	 * @generated
	 */
	EReference getPropertyConstraint_Comment();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getValue()
	 * @see #getPropertyConstraint()
	 * @generated
	 */
	EReference getPropertyConstraint_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint <em>Field Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Field Constraint</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint
	 * @generated
	 */
	EClass getFieldConstraint();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#getValue()
	 * @see #getFieldConstraint()
	 * @generated
	 */
	EAttribute getFieldConstraint_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isCaseSensitive <em>Case Sensitive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Case Sensitive</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isCaseSensitive()
	 * @see #getFieldConstraint()
	 * @generated
	 */
	EAttribute getFieldConstraint_CaseSensitive();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isWholeWord <em>Whole Word</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Whole Word</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isWholeWord()
	 * @see #getFieldConstraint()
	 * @generated
	 */
	EAttribute getFieldConstraint_WholeWord();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isEntire <em>Entire</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entire</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isEntire()
	 * @see #getFieldConstraint()
	 * @generated
	 */
	EAttribute getFieldConstraint_Entire();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isRegex <em>Regex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Regex</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isRegex()
	 * @see #getFieldConstraint()
	 * @generated
	 */
	EAttribute getFieldConstraint_Regex();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeConstraint <em>Attribute Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Constraint</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeConstraint
	 * @generated
	 */
	EClass getAttributeConstraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Occurrence <em>Occurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Occurrence
	 * @generated
	 */
	EClass getOccurrence();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Occurrence#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constraint</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Occurrence#getConstraint()
	 * @see #getOccurrence()
	 * @generated
	 */
	EReference getOccurrence_Constraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.ApplicationOccurrence <em>Application Occurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.ApplicationOccurrence
	 * @generated
	 */
	EClass getApplicationOccurrence();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.CompositeFBOccurrence <em>Composite FB Occurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite FB Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.CompositeFBOccurrence
	 * @generated
	 */
	EClass getCompositeFBOccurrence();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.TypedSubappOccurrence <em>Typed Subapp Occurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Subapp Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.TypedSubappOccurrence
	 * @generated
	 */
	EClass getTypedSubappOccurrence();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Query <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Query</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Query
	 * @generated
	 */
	EClass getQuery();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Query#getPlaceholder <em>Placeholder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Placeholder</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Query#getPlaceholder()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Placeholder();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Query#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Query#getTarget()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Target();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Query#getPlace <em>Place</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Place</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Query#getPlace()
	 * @see #getQuery()
	 * @generated
	 */
	EReference getQuery_Place();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Placeholder <em>Placeholder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Placeholder</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Placeholder
	 * @generated
	 */
	EClass getPlaceholder();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Placeholder#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Placeholder#getKey()
	 * @see #getPlaceholder()
	 * @generated
	 */
	EAttribute getPlaceholder_Key();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Placeholder#getVal <em>Val</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Val</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Placeholder#getVal()
	 * @see #getPlaceholder()
	 * @generated
	 */
	EAttribute getPlaceholder_Val();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Target <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Target
	 * @generated
	 */
	EClass getTarget();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Target#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Target</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Target#getTarget()
	 * @see #getTarget()
	 * @generated
	 */
	EReference getTarget_Target();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.TargetOption <em>Target Option</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target Option</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.TargetOption
	 * @generated
	 */
	EClass getTargetOption();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place <em>Place</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Place</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place
	 * @generated
	 */
	EClass getPlace();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#isIgnoreLinkedLibraries <em>Ignore Linked Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ignore Linked Libraries</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#isIgnoreLinkedLibraries()
	 * @see #getPlace()
	 * @generated
	 */
	EAttribute getPlace_IgnoreLinkedLibraries();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#isSubappHierarchy <em>Subapp Hierarchy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Subapp Hierarchy</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#isSubappHierarchy()
	 * @see #getPlace()
	 * @generated
	 */
	EAttribute getPlace_SubappHierarchy();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSubappHierarchyElements <em>Subapp Hierarchy Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Subapp Hierarchy Elements</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSubappHierarchyElements()
	 * @see #getPlace()
	 * @generated
	 */
	EAttribute getPlace_SubappHierarchyElements();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSimpleFB <em>Simple FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Simple FB</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSimpleFB()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_SimpleFB();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getBasicFB <em>Basic FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Basic FB</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getBasicFB()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_BasicFB();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getCompositeFB <em>Composite FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Composite FB</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getCompositeFB()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_CompositeFB();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getServiceInterfaceFB <em>Service Interface FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Service Interface FB</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getServiceInterfaceFB()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_ServiceInterfaceFB();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getFunctionFB <em>Function FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Function FB</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getFunctionFB()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_FunctionFB();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getTypedSubapp <em>Typed Subapp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Typed Subapp</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getTypedSubapp()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_TypedSubapp();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getUntypedSubapp <em>Untyped Subapp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Untyped Subapp</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getUntypedSubapp()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_UntypedSubapp();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getConnection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Connection</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getConnection()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_Connection();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSimpleType <em>Simple Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Simple Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSimpleType()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_SimpleType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getBasicType <em>Basic Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Basic Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getBasicType()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_BasicType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getCompositeType <em>Composite Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Composite Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getCompositeType()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_CompositeType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getServiceInterfaceType <em>Service Interface Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Service Interface Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getServiceInterfaceType()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_ServiceInterfaceType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getFunctionType <em>Function Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Function Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getFunctionType()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_FunctionType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSubappType <em>Subapp Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Subapp Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getSubappType()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_SubappType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getStructType <em>Struct Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Struct Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getStructType()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_StructType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getAttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Attribute Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Place#getAttributeType()
	 * @see #getPlace()
	 * @generated
	 */
	EReference getPlace_AttributeType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.SearchTarget <em>Search Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Search Target</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.SearchTarget
	 * @generated
	 */
	EClass getSearchTarget();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.SearchTarget#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constraint</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.SearchTarget#getConstraint()
	 * @see #getSearchTarget()
	 * @generated
	 */
	EReference getSearchTarget_Constraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Type <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Type
	 * @generated
	 */
	EClass getType();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Type#getPin <em>Pin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pin</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Type#getPin()
	 * @see #getType()
	 * @generated
	 */
	EReference getType_Pin();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.SimpleType <em>Simple Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.SimpleType
	 * @generated
	 */
	EClass getSimpleType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.BasicType <em>Basic Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.BasicType
	 * @generated
	 */
	EClass getBasicType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.CompositeType <em>Composite Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.CompositeType
	 * @generated
	 */
	EClass getCompositeType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.ServiceInterfaceType <em>Service Interface Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Interface Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.ServiceInterfaceType
	 * @generated
	 */
	EClass getServiceInterfaceType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FunctionType <em>Function Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.FunctionType
	 * @generated
	 */
	EClass getFunctionType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.SubappType <em>Subapp Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Subapp Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.SubappType
	 * @generated
	 */
	EClass getSubappType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.StructType <em>Struct Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Struct Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.StructType
	 * @generated
	 */
	EClass getStructType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeType <em>Attribute Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Type</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeType
	 * @generated
	 */
	EClass getAttributeType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement <em>Network Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Network Element</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement
	 * @generated
	 */
	EClass getNetworkElement();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getApplicationOccurrence <em>Application Occurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Application Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getApplicationOccurrence()
	 * @see #getNetworkElement()
	 * @generated
	 */
	EReference getNetworkElement_ApplicationOccurrence();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getCompositeFBOccurrence <em>Composite FB Occurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Composite FB Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getCompositeFBOccurrence()
	 * @see #getNetworkElement()
	 * @generated
	 */
	EReference getNetworkElement_CompositeFBOccurrence();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getTypedSubappOccurrence <em>Typed Subapp Occurrence</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Typed Subapp Occurrence</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getTypedSubappOccurrence()
	 * @see #getNetworkElement()
	 * @generated
	 */
	EReference getNetworkElement_TypedSubappOccurrence();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Instance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Instance
	 * @generated
	 */
	EClass getInstance();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Instance#getPin <em>Pin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pin</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Instance#getPin()
	 * @see #getInstance()
	 * @generated
	 */
	EReference getInstance_Pin();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.SimpleFB <em>Simple FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple FB</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.SimpleFB
	 * @generated
	 */
	EClass getSimpleFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.BasicFB <em>Basic FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Basic FB</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.BasicFB
	 * @generated
	 */
	EClass getBasicFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.CompositeFB <em>Composite FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite FB</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.CompositeFB
	 * @generated
	 */
	EClass getCompositeFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.ServiceInterfaceFB <em>Service Interface FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Interface FB</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.ServiceInterfaceFB
	 * @generated
	 */
	EClass getServiceInterfaceFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FunctionFB <em>Function FB</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function FB</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.FunctionFB
	 * @generated
	 */
	EClass getFunctionFB();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.TypedSubapp <em>Typed Subapp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Typed Subapp</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.TypedSubapp
	 * @generated
	 */
	EClass getTypedSubapp();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.UntypedSubapp <em>Untyped Subapp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Untyped Subapp</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.UntypedSubapp
	 * @generated
	 */
	EClass getUntypedSubapp();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PIN <em>PIN</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>PIN</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.PIN
	 * @generated
	 */
	EClass getPIN();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PinTarget <em>Pin Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pin Target</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.PinTarget
	 * @generated
	 */
	EClass getPinTarget();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PinTarget#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constraint</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.PinTarget#getConstraint()
	 * @see #getPinTarget()
	 * @generated
	 */
	EReference getPinTarget_Constraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Attribute <em>Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Attribute
	 * @generated
	 */
	EClass getAttribute();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Attribute#getConstraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Constraint</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.Attribute#getConstraint()
	 * @see #getAttribute()
	 * @generated
	 */
	EReference getAttribute_Constraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeDeclaration <em>Attribute Declaration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Attribute Declaration</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeDeclaration
	 * @generated
	 */
	EClass getAttributeDeclaration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeDeclaration#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.AttributeDeclaration#getName()
	 * @see #getAttributeDeclaration()
	 * @generated
	 */
	EAttribute getAttributeDeclaration_Name();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	QueryFactory getQueryFactory();

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
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConstraintImpl <em>Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConstraintImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getConstraint()
		 * @generated
		 */
		EClass CONSTRAINT = eINSTANCE.getConstraint();

		/**
		 * The meta object literal for the '<em><b>Negate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__NEGATE = eINSTANCE.getConstraint_Negate();

		/**
		 * The meta object literal for the '<em><b>Or Constraint</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT__OR_CONSTRAINT = eINSTANCE.getConstraint_OrConstraint();

		/**
		 * The meta object literal for the '<em><b>And Constraint</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT__AND_CONSTRAINT = eINSTANCE.getConstraint_AndConstraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PropertyConstraintImpl <em>Property Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PropertyConstraintImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getPropertyConstraint()
		 * @generated
		 */
		EClass PROPERTY_CONSTRAINT = eINSTANCE.getPropertyConstraint();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_CONSTRAINT__NAME = eINSTANCE.getPropertyConstraint_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_CONSTRAINT__TYPE = eINSTANCE.getPropertyConstraint_Type();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_CONSTRAINT__COMMENT = eINSTANCE.getPropertyConstraint_Comment();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_CONSTRAINT__VALUE = eINSTANCE.getPropertyConstraint_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FieldConstraintImpl <em>Field Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FieldConstraintImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getFieldConstraint()
		 * @generated
		 */
		EClass FIELD_CONSTRAINT = eINSTANCE.getFieldConstraint();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_CONSTRAINT__VALUE = eINSTANCE.getFieldConstraint_Value();

		/**
		 * The meta object literal for the '<em><b>Case Sensitive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_CONSTRAINT__CASE_SENSITIVE = eINSTANCE.getFieldConstraint_CaseSensitive();

		/**
		 * The meta object literal for the '<em><b>Whole Word</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_CONSTRAINT__WHOLE_WORD = eINSTANCE.getFieldConstraint_WholeWord();

		/**
		 * The meta object literal for the '<em><b>Entire</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_CONSTRAINT__ENTIRE = eINSTANCE.getFieldConstraint_Entire();

		/**
		 * The meta object literal for the '<em><b>Regex</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FIELD_CONSTRAINT__REGEX = eINSTANCE.getFieldConstraint_Regex();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeConstraintImpl <em>Attribute Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeConstraintImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getAttributeConstraint()
		 * @generated
		 */
		EClass ATTRIBUTE_CONSTRAINT = eINSTANCE.getAttributeConstraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.OccurrenceImpl <em>Occurrence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.OccurrenceImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getOccurrence()
		 * @generated
		 */
		EClass OCCURRENCE = eINSTANCE.getOccurrence();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OCCURRENCE__CONSTRAINT = eINSTANCE.getOccurrence_Constraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ApplicationOccurrenceImpl <em>Application Occurrence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ApplicationOccurrenceImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getApplicationOccurrence()
		 * @generated
		 */
		EClass APPLICATION_OCCURRENCE = eINSTANCE.getApplicationOccurrence();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeFBOccurrenceImpl <em>Composite FB Occurrence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeFBOccurrenceImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getCompositeFBOccurrence()
		 * @generated
		 */
		EClass COMPOSITE_FB_OCCURRENCE = eINSTANCE.getCompositeFBOccurrence();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypedSubappOccurrenceImpl <em>Typed Subapp Occurrence</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypedSubappOccurrenceImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getTypedSubappOccurrence()
		 * @generated
		 */
		EClass TYPED_SUBAPP_OCCURRENCE = eINSTANCE.getTypedSubappOccurrence();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryImpl <em>Query</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getQuery()
		 * @generated
		 */
		EClass QUERY = eINSTANCE.getQuery();

		/**
		 * The meta object literal for the '<em><b>Placeholder</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUERY__PLACEHOLDER = eINSTANCE.getQuery_Placeholder();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUERY__TARGET = eINSTANCE.getQuery_Target();

		/**
		 * The meta object literal for the '<em><b>Place</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUERY__PLACE = eINSTANCE.getQuery_Place();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceholderImpl <em>Placeholder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceholderImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getPlaceholder()
		 * @generated
		 */
		EClass PLACEHOLDER = eINSTANCE.getPlaceholder();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLACEHOLDER__KEY = eINSTANCE.getPlaceholder_Key();

		/**
		 * The meta object literal for the '<em><b>Val</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLACEHOLDER__VAL = eINSTANCE.getPlaceholder_Val();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TargetImpl <em>Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TargetImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getTarget()
		 * @generated
		 */
		EClass TARGET = eINSTANCE.getTarget();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET__TARGET = eINSTANCE.getTarget_Target();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TargetOptionImpl <em>Target Option</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TargetOptionImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getTargetOption()
		 * @generated
		 */
		EClass TARGET_OPTION = eINSTANCE.getTargetOption();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl <em>Place</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PlaceImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getPlace()
		 * @generated
		 */
		EClass PLACE = eINSTANCE.getPlace();

		/**
		 * The meta object literal for the '<em><b>Ignore Linked Libraries</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLACE__IGNORE_LINKED_LIBRARIES = eINSTANCE.getPlace_IgnoreLinkedLibraries();

		/**
		 * The meta object literal for the '<em><b>Subapp Hierarchy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLACE__SUBAPP_HIERARCHY = eINSTANCE.getPlace_SubappHierarchy();

		/**
		 * The meta object literal for the '<em><b>Subapp Hierarchy Elements</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLACE__SUBAPP_HIERARCHY_ELEMENTS = eINSTANCE.getPlace_SubappHierarchyElements();

		/**
		 * The meta object literal for the '<em><b>Simple FB</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__SIMPLE_FB = eINSTANCE.getPlace_SimpleFB();

		/**
		 * The meta object literal for the '<em><b>Basic FB</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__BASIC_FB = eINSTANCE.getPlace_BasicFB();

		/**
		 * The meta object literal for the '<em><b>Composite FB</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__COMPOSITE_FB = eINSTANCE.getPlace_CompositeFB();

		/**
		 * The meta object literal for the '<em><b>Service Interface FB</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__SERVICE_INTERFACE_FB = eINSTANCE.getPlace_ServiceInterfaceFB();

		/**
		 * The meta object literal for the '<em><b>Function FB</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__FUNCTION_FB = eINSTANCE.getPlace_FunctionFB();

		/**
		 * The meta object literal for the '<em><b>Typed Subapp</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__TYPED_SUBAPP = eINSTANCE.getPlace_TypedSubapp();

		/**
		 * The meta object literal for the '<em><b>Untyped Subapp</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__UNTYPED_SUBAPP = eINSTANCE.getPlace_UntypedSubapp();

		/**
		 * The meta object literal for the '<em><b>Connection</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__CONNECTION = eINSTANCE.getPlace_Connection();

		/**
		 * The meta object literal for the '<em><b>Simple Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__SIMPLE_TYPE = eINSTANCE.getPlace_SimpleType();

		/**
		 * The meta object literal for the '<em><b>Basic Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__BASIC_TYPE = eINSTANCE.getPlace_BasicType();

		/**
		 * The meta object literal for the '<em><b>Composite Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__COMPOSITE_TYPE = eINSTANCE.getPlace_CompositeType();

		/**
		 * The meta object literal for the '<em><b>Service Interface Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__SERVICE_INTERFACE_TYPE = eINSTANCE.getPlace_ServiceInterfaceType();

		/**
		 * The meta object literal for the '<em><b>Function Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__FUNCTION_TYPE = eINSTANCE.getPlace_FunctionType();

		/**
		 * The meta object literal for the '<em><b>Subapp Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__SUBAPP_TYPE = eINSTANCE.getPlace_SubappType();

		/**
		 * The meta object literal for the '<em><b>Struct Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__STRUCT_TYPE = eINSTANCE.getPlace_StructType();

		/**
		 * The meta object literal for the '<em><b>Attribute Type</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLACE__ATTRIBUTE_TYPE = eINSTANCE.getPlace_AttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SearchTargetImpl <em>Search Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SearchTargetImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getSearchTarget()
		 * @generated
		 */
		EClass SEARCH_TARGET = eINSTANCE.getSearchTarget();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEARCH_TARGET__CONSTRAINT = eINSTANCE.getSearchTarget_Constraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypeImpl <em>Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypeImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getType()
		 * @generated
		 */
		EClass TYPE = eINSTANCE.getType();

		/**
		 * The meta object literal for the '<em><b>Pin</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE__PIN = eINSTANCE.getType_Pin();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SimpleTypeImpl <em>Simple Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SimpleTypeImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getSimpleType()
		 * @generated
		 */
		EClass SIMPLE_TYPE = eINSTANCE.getSimpleType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.BasicTypeImpl <em>Basic Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.BasicTypeImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getBasicType()
		 * @generated
		 */
		EClass BASIC_TYPE = eINSTANCE.getBasicType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeTypeImpl <em>Composite Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeTypeImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getCompositeType()
		 * @generated
		 */
		EClass COMPOSITE_TYPE = eINSTANCE.getCompositeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ServiceInterfaceTypeImpl <em>Service Interface Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ServiceInterfaceTypeImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getServiceInterfaceType()
		 * @generated
		 */
		EClass SERVICE_INTERFACE_TYPE = eINSTANCE.getServiceInterfaceType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FunctionTypeImpl <em>Function Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FunctionTypeImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getFunctionType()
		 * @generated
		 */
		EClass FUNCTION_TYPE = eINSTANCE.getFunctionType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SubappTypeImpl <em>Subapp Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SubappTypeImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getSubappType()
		 * @generated
		 */
		EClass SUBAPP_TYPE = eINSTANCE.getSubappType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.StructTypeImpl <em>Struct Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.StructTypeImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getStructType()
		 * @generated
		 */
		EClass STRUCT_TYPE = eINSTANCE.getStructType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeTypeImpl <em>Attribute Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeTypeImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getAttributeType()
		 * @generated
		 */
		EClass ATTRIBUTE_TYPE = eINSTANCE.getAttributeType();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.NetworkElementImpl <em>Network Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.NetworkElementImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getNetworkElement()
		 * @generated
		 */
		EClass NETWORK_ELEMENT = eINSTANCE.getNetworkElement();

		/**
		 * The meta object literal for the '<em><b>Application Occurrence</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK_ELEMENT__APPLICATION_OCCURRENCE = eINSTANCE.getNetworkElement_ApplicationOccurrence();

		/**
		 * The meta object literal for the '<em><b>Composite FB Occurrence</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK_ELEMENT__COMPOSITE_FB_OCCURRENCE = eINSTANCE.getNetworkElement_CompositeFBOccurrence();

		/**
		 * The meta object literal for the '<em><b>Typed Subapp Occurrence</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NETWORK_ELEMENT__TYPED_SUBAPP_OCCURRENCE = eINSTANCE.getNetworkElement_TypedSubappOccurrence();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.InstanceImpl <em>Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.InstanceImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getInstance()
		 * @generated
		 */
		EClass INSTANCE = eINSTANCE.getInstance();

		/**
		 * The meta object literal for the '<em><b>Pin</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE__PIN = eINSTANCE.getInstance_Pin();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SimpleFBImpl <em>Simple FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.SimpleFBImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getSimpleFB()
		 * @generated
		 */
		EClass SIMPLE_FB = eINSTANCE.getSimpleFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.BasicFBImpl <em>Basic FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.BasicFBImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getBasicFB()
		 * @generated
		 */
		EClass BASIC_FB = eINSTANCE.getBasicFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeFBImpl <em>Composite FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.CompositeFBImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getCompositeFB()
		 * @generated
		 */
		EClass COMPOSITE_FB = eINSTANCE.getCompositeFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ServiceInterfaceFBImpl <em>Service Interface FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ServiceInterfaceFBImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getServiceInterfaceFB()
		 * @generated
		 */
		EClass SERVICE_INTERFACE_FB = eINSTANCE.getServiceInterfaceFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FunctionFBImpl <em>Function FB</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.FunctionFBImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getFunctionFB()
		 * @generated
		 */
		EClass FUNCTION_FB = eINSTANCE.getFunctionFB();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypedSubappImpl <em>Typed Subapp</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.TypedSubappImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getTypedSubapp()
		 * @generated
		 */
		EClass TYPED_SUBAPP = eINSTANCE.getTypedSubapp();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.UntypedSubappImpl <em>Untyped Subapp</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.UntypedSubappImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getUntypedSubapp()
		 * @generated
		 */
		EClass UNTYPED_SUBAPP = eINSTANCE.getUntypedSubapp();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.ConnectionImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PINImpl <em>PIN</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PINImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getPIN()
		 * @generated
		 */
		EClass PIN = eINSTANCE.getPIN();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PinTargetImpl <em>Pin Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.PinTargetImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getPinTarget()
		 * @generated
		 */
		EClass PIN_TARGET = eINSTANCE.getPinTarget();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PIN_TARGET__CONSTRAINT = eINSTANCE.getPinTarget_Constraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeImpl <em>Attribute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getAttribute()
		 * @generated
		 */
		EClass ATTRIBUTE = eINSTANCE.getAttribute();

		/**
		 * The meta object literal for the '<em><b>Constraint</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATTRIBUTE__CONSTRAINT = eINSTANCE.getAttribute_Constraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeDeclarationImpl <em>Attribute Declaration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.AttributeDeclarationImpl
		 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryPackageImpl#getAttributeDeclaration()
		 * @generated
		 */
		EClass ATTRIBUTE_DECLARATION = eINSTANCE.getAttributeDeclaration();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATTRIBUTE_DECLARATION__NAME = eINSTANCE.getAttributeDeclaration_Name();

	}

} //QueryPackage
