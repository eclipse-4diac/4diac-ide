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
 * A representation of the model object '<em><b>Constraint Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.ConstraintInstance#getDeclaration <em>Declaration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getConstraintInstance()
 * @model
 * @generated
 */
public interface ConstraintInstance extends Constraint {
	/**
	 * Returns the value of the '<em><b>Declaration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declaration</em>' reference.
	 * @see #setDeclaration(ConstraintDeclaration)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getConstraintInstance_Declaration()
	 * @model resolveProxies="false" required="true"
	 * @generated
	 */
	ConstraintDeclaration getDeclaration();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.ConstraintInstance#getDeclaration <em>Declaration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declaration</em>' reference.
	 * @see #getDeclaration()
	 * @generated
	 */
	void setDeclaration(ConstraintDeclaration value);

} // ConstraintInstance
