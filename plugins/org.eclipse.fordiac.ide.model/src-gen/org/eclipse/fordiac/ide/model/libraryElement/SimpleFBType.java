/**
 * *******************************************************************************
 *  * Copyright (c) 2008 - 2017 4DIAC - consortium.
 *  *
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *  *     - initial API and implementation and/or initial documentation
 *  *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple FB Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType#getAlgorithm <em>Algorithm</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType#getInternalVars <em>Internal Vars</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleFBType()
 * @model
 * @generated
 */
public interface SimpleFBType extends FBType {
	/**
	 * Returns the value of the '<em><b>Algorithm</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Algorithm</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Algorithm</em>' containment reference.
	 * @see #setAlgorithm(Algorithm)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleFBType_Algorithm()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='Algorithm' namespace='##targetNamespace'"
	 * @generated
	 */
	Algorithm getAlgorithm();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType#getAlgorithm <em>Algorithm</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Algorithm</em>' containment reference.
	 * @see #getAlgorithm()
	 * @generated
	 */
	void setAlgorithm(Algorithm value);

	/**
	 * Returns the value of the '<em><b>Internal Vars</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Internal Vars</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Internal Vars</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getSimpleFBType_InternalVars()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='VarDeclaration' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<VarDeclaration> getInternalVars();

} // SimpleFBType
