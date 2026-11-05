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
 * A representation of the model object '<em><b>Field Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isCaseSensitive <em>Case Sensitive</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isWholeWord <em>Whole Word</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isEntire <em>Entire</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isRegex <em>Regex</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getFieldConstraint()
 * @model
 * @generated
 */
public interface FieldConstraint extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getFieldConstraint_Value()
	 * @model default=""
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Case Sensitive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Case Sensitive</em>' attribute.
	 * @see #setCaseSensitive(boolean)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getFieldConstraint_CaseSensitive()
	 * @model
	 * @generated
	 */
	boolean isCaseSensitive();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isCaseSensitive <em>Case Sensitive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Case Sensitive</em>' attribute.
	 * @see #isCaseSensitive()
	 * @generated
	 */
	void setCaseSensitive(boolean value);

	/**
	 * Returns the value of the '<em><b>Whole Word</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Whole Word</em>' attribute.
	 * @see #setWholeWord(boolean)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getFieldConstraint_WholeWord()
	 * @model
	 * @generated
	 */
	boolean isWholeWord();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isWholeWord <em>Whole Word</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Whole Word</em>' attribute.
	 * @see #isWholeWord()
	 * @generated
	 */
	void setWholeWord(boolean value);

	/**
	 * Returns the value of the '<em><b>Entire</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entire</em>' attribute.
	 * @see #setEntire(boolean)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getFieldConstraint_Entire()
	 * @model
	 * @generated
	 */
	boolean isEntire();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isEntire <em>Entire</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entire</em>' attribute.
	 * @see #isEntire()
	 * @generated
	 */
	void setEntire(boolean value);

	/**
	 * Returns the value of the '<em><b>Regex</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Regex</em>' attribute.
	 * @see #setRegex(boolean)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getFieldConstraint_Regex()
	 * @model
	 * @generated
	 */
	boolean isRegex();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.FieldConstraint#isRegex <em>Regex</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Regex</em>' attribute.
	 * @see #isRegex()
	 * @generated
	 */
	void setRegex(boolean value);

} // FieldConstraint
