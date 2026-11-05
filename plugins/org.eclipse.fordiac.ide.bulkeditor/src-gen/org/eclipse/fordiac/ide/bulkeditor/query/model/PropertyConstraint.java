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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getComment <em>Comment</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPropertyConstraint()
 * @model
 * @generated
 */
public interface PropertyConstraint extends Constraint {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' containment reference.
	 * @see #setName(FieldConstraint)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPropertyConstraint_Name()
	 * @model containment="true"
	 * @generated
	 */
	FieldConstraint getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getName <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' containment reference.
	 * @see #getName()
	 * @generated
	 */
	void setName(FieldConstraint value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(FieldConstraint)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPropertyConstraint_Type()
	 * @model containment="true"
	 * @generated
	 */
	FieldConstraint getType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(FieldConstraint value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' containment reference.
	 * @see #setComment(FieldConstraint)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPropertyConstraint_Comment()
	 * @model containment="true"
	 * @generated
	 */
	FieldConstraint getComment();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getComment <em>Comment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' containment reference.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(FieldConstraint value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(FieldConstraint)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getPropertyConstraint_Value()
	 * @model containment="true"
	 * @generated
	 */
	FieldConstraint getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.PropertyConstraint#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(FieldConstraint value);

} // PropertyConstraint
