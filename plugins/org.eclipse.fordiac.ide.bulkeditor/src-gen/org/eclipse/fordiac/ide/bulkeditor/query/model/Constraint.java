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
 * A representation of the model object '<em><b>Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#isNegate <em>Negate</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#getOrConstraint <em>Or Constraint</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#getAndConstraint <em>And Constraint</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getConstraint()
 * @model abstract="true"
 * @generated
 */
public interface Constraint extends EObject {
	/**
	 * Returns the value of the '<em><b>Negate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Negate</em>' attribute.
	 * @see #setNegate(boolean)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getConstraint_Negate()
	 * @model
	 * @generated
	 */
	boolean isNegate();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#isNegate <em>Negate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Negate</em>' attribute.
	 * @see #isNegate()
	 * @generated
	 */
	void setNegate(boolean value);

	/**
	 * Returns the value of the '<em><b>Or Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Or Constraint</em>' containment reference.
	 * @see #setOrConstraint(Constraint)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getConstraint_OrConstraint()
	 * @model containment="true"
	 * @generated
	 */
	Constraint getOrConstraint();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#getOrConstraint <em>Or Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Or Constraint</em>' containment reference.
	 * @see #getOrConstraint()
	 * @generated
	 */
	void setOrConstraint(Constraint value);

	/**
	 * Returns the value of the '<em><b>And Constraint</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>And Constraint</em>' containment reference.
	 * @see #setAndConstraint(Constraint)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getConstraint_AndConstraint()
	 * @model containment="true"
	 * @generated
	 */
	Constraint getAndConstraint();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.Constraint#getAndConstraint <em>And Constraint</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>And Constraint</em>' containment reference.
	 * @see #getAndConstraint()
	 * @generated
	 */
	void setAndConstraint(Constraint value);

} // Constraint
