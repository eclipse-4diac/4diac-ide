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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.ConstraintDeclaration#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.ConstraintDeclaration#getConstraint <em>Constraint</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getConstraintDeclaration()
 * @model
 * @generated
 */
public interface ConstraintDeclaration extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getConstraintDeclaration_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.ConstraintDeclaration#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint</em>' containment reference.
	 * @see #setConstraint(Constraint)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getConstraintDeclaration_Constraint()
	 * @model containment="true"
	 * @generated
	 */
	Constraint getConstraint();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.ConstraintDeclaration#getConstraint <em>Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Constraint</em>' containment reference.
	 * @see #getConstraint()
	 * @generated
	 */
	void setConstraint(Constraint value);

} // ConstraintDeclaration
