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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage
 * @generated
 */
public interface QueryFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	QueryFactory eINSTANCE = org.eclipse.fordiac.ide.bulkeditor.query.model.impl.QueryFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Property Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Property Constraint</em>'.
	 * @generated
	 */
	PropertyConstraint createPropertyConstraint();

	/**
	 * Returns a new object of class '<em>Field Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Field Constraint</em>'.
	 * @generated
	 */
	FieldConstraint createFieldConstraint();

	/**
	 * Returns a new object of class '<em>Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Constraint</em>'.
	 * @generated
	 */
	AttributeConstraint createAttributeConstraint();

	/**
	 * Returns a new object of class '<em>Application Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application Occurrence</em>'.
	 * @generated
	 */
	ApplicationOccurrence createApplicationOccurrence();

	/**
	 * Returns a new object of class '<em>Composite FB Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composite FB Occurrence</em>'.
	 * @generated
	 */
	CompositeFBOccurrence createCompositeFBOccurrence();

	/**
	 * Returns a new object of class '<em>Typed Subapp Occurrence</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Typed Subapp Occurrence</em>'.
	 * @generated
	 */
	TypedSubappOccurrence createTypedSubappOccurrence();

	/**
	 * Returns a new object of class '<em>Query</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Query</em>'.
	 * @generated
	 */
	Query createQuery();

	/**
	 * Returns a new object of class '<em>Placeholder</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Placeholder</em>'.
	 * @generated
	 */
	Placeholder createPlaceholder();

	/**
	 * Returns a new object of class '<em>Target</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Target</em>'.
	 * @generated
	 */
	Target createTarget();

	/**
	 * Returns a new object of class '<em>Place</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Place</em>'.
	 * @generated
	 */
	Place createPlace();

	/**
	 * Returns a new object of class '<em>Simple Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple Type</em>'.
	 * @generated
	 */
	SimpleType createSimpleType();

	/**
	 * Returns a new object of class '<em>Basic Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Basic Type</em>'.
	 * @generated
	 */
	BasicType createBasicType();

	/**
	 * Returns a new object of class '<em>Composite Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composite Type</em>'.
	 * @generated
	 */
	CompositeType createCompositeType();

	/**
	 * Returns a new object of class '<em>Service Interface Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service Interface Type</em>'.
	 * @generated
	 */
	ServiceInterfaceType createServiceInterfaceType();

	/**
	 * Returns a new object of class '<em>Function Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function Type</em>'.
	 * @generated
	 */
	FunctionType createFunctionType();

	/**
	 * Returns a new object of class '<em>Subapp Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Subapp Type</em>'.
	 * @generated
	 */
	SubappType createSubappType();

	/**
	 * Returns a new object of class '<em>Struct Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Struct Type</em>'.
	 * @generated
	 */
	StructType createStructType();

	/**
	 * Returns a new object of class '<em>Attribute Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Type</em>'.
	 * @generated
	 */
	AttributeType createAttributeType();

	/**
	 * Returns a new object of class '<em>Simple FB</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Simple FB</em>'.
	 * @generated
	 */
	SimpleFB createSimpleFB();

	/**
	 * Returns a new object of class '<em>Basic FB</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Basic FB</em>'.
	 * @generated
	 */
	BasicFB createBasicFB();

	/**
	 * Returns a new object of class '<em>Composite FB</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composite FB</em>'.
	 * @generated
	 */
	CompositeFB createCompositeFB();

	/**
	 * Returns a new object of class '<em>Service Interface FB</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service Interface FB</em>'.
	 * @generated
	 */
	ServiceInterfaceFB createServiceInterfaceFB();

	/**
	 * Returns a new object of class '<em>Function FB</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Function FB</em>'.
	 * @generated
	 */
	FunctionFB createFunctionFB();

	/**
	 * Returns a new object of class '<em>Typed Subapp</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Typed Subapp</em>'.
	 * @generated
	 */
	TypedSubapp createTypedSubapp();

	/**
	 * Returns a new object of class '<em>Untyped Subapp</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Untyped Subapp</em>'.
	 * @generated
	 */
	UntypedSubapp createUntypedSubapp();

	/**
	 * Returns a new object of class '<em>Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connection</em>'.
	 * @generated
	 */
	Connection createConnection();

	/**
	 * Returns a new object of class '<em>PIN</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>PIN</em>'.
	 * @generated
	 */
	PIN createPIN();

	/**
	 * Returns a new object of class '<em>Pin Target</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pin Target</em>'.
	 * @generated
	 */
	PinTarget createPinTarget();

	/**
	 * Returns a new object of class '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute</em>'.
	 * @generated
	 */
	Attribute createAttribute();

	/**
	 * Returns a new object of class '<em>Attribute Declaration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Declaration</em>'.
	 * @generated
	 */
	AttributeDeclaration createAttributeDeclaration();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	QueryPackage getQueryPackage();

} //QueryFactory
