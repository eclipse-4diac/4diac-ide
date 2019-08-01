/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ECC</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getECState <em>EC State</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getECTransition <em>EC Transition</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getStart <em>Start</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getBasicFBType <em>Basic FB Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECC()
 * @model
 * @generated
 */
public interface ECC extends EObject {
	/**
	 * Returns the value of the '<em><b>EC State</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.ECState}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getECC <em>ECC</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EC State</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EC State</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECC_ECState()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECState#getECC
	 * @model opposite="eCC" containment="true" required="true"
	 *        extendedMetaData="kind='element' name='ECState' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ECState> getECState();

	/**
	 * Returns the value of the '<em><b>EC Transition</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getECC <em>ECC</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EC Transition</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EC Transition</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECC_ECTransition()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getECC
	 * @model opposite="eCC" containment="true" required="true"
	 *        extendedMetaData="kind='element' name='ECTransition' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ECTransition> getECTransition();

	/**
	 * Returns the value of the '<em><b>Start</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start</em>' reference.
	 * @see #setStart(ECState)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECC_Start()
	 * @model
	 * @generated
	 */
	ECState getStart();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getStart <em>Start</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start</em>' reference.
	 * @see #getStart()
	 * @generated
	 */
	void setStart(ECState value);

	/**
	 * Returns the value of the '<em><b>Basic FB Type</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.BasicFBType#getECC <em>ECC</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basic FB Type</em>' container reference.
	 * @see #setBasicFBType(BasicFBType)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECC_BasicFBType()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.BasicFBType#getECC
	 * @model opposite="eCC" required="true" transient="false"
	 * @generated
	 */
	BasicFBType getBasicFBType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getBasicFBType <em>Basic FB Type</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Basic FB Type</em>' container reference.
	 * @see #getBasicFBType()
	 * @generated
	 */
	void setBasicFBType(BasicFBType value);

} // ECC
