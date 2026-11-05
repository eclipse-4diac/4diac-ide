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
 * A representation of the model object '<em><b>Network Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getApplicationOccurrence <em>Application Occurrence</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getCompositeFBOccurrence <em>Composite FB Occurrence</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getTypedSubappOccurrence <em>Typed Subapp Occurrence</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getNetworkElement()
 * @model abstract="true"
 * @generated
 */
public interface NetworkElement extends SearchTarget {
	/**
	 * Returns the value of the '<em><b>Application Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application Occurrence</em>' containment reference.
	 * @see #setApplicationOccurrence(ApplicationOccurrence)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getNetworkElement_ApplicationOccurrence()
	 * @model containment="true"
	 * @generated
	 */
	ApplicationOccurrence getApplicationOccurrence();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getApplicationOccurrence <em>Application Occurrence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Application Occurrence</em>' containment reference.
	 * @see #getApplicationOccurrence()
	 * @generated
	 */
	void setApplicationOccurrence(ApplicationOccurrence value);

	/**
	 * Returns the value of the '<em><b>Composite FB Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite FB Occurrence</em>' containment reference.
	 * @see #setCompositeFBOccurrence(CompositeFBOccurrence)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getNetworkElement_CompositeFBOccurrence()
	 * @model containment="true"
	 * @generated
	 */
	CompositeFBOccurrence getCompositeFBOccurrence();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getCompositeFBOccurrence <em>Composite FB Occurrence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Composite FB Occurrence</em>' containment reference.
	 * @see #getCompositeFBOccurrence()
	 * @generated
	 */
	void setCompositeFBOccurrence(CompositeFBOccurrence value);

	/**
	 * Returns the value of the '<em><b>Typed Subapp Occurrence</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Typed Subapp Occurrence</em>' containment reference.
	 * @see #setTypedSubappOccurrence(TypedSubappOccurrence)
	 * @see org.eclipse.fordiac.ide.bulkeditor.query.model.QueryPackage#getNetworkElement_TypedSubappOccurrence()
	 * @model containment="true"
	 * @generated
	 */
	TypedSubappOccurrence getTypedSubappOccurrence();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.bulkeditor.query.model.NetworkElement#getTypedSubappOccurrence <em>Typed Subapp Occurrence</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Typed Subapp Occurrence</em>' containment reference.
	 * @see #getTypedSubappOccurrence()
	 * @generated
	 */
	void setTypedSubappOccurrence(TypedSubappOccurrence value);

} // NetworkElement
