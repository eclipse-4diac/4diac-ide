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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>EC State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getECAction <em>EC Action</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getOutTransitions <em>Out Transitions</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getInTransitions <em>In Transitions</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getECC <em>ECC</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECState()
 * @model
 * @generated
 */
public interface ECState extends INamedElement, PositionableElement {
	/**
	 * Returns the value of the '<em><b>EC Action</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.ECAction}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.ECAction#getECState <em>EC State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EC Action</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EC Action</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECState_ECAction()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECAction#getECState
	 * @model opposite="eCState" containment="true"
	 *        extendedMetaData="kind='element' name='ECAction' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ECAction> getECAction();

	/**
	 * Returns the value of the '<em><b>Out Transitions</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Out Transitions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Transitions</em>' reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECState_OutTransitions()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<ECTransition> getOutTransitions();

	/**
	 * Returns the value of the '<em><b>In Transitions</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Transitions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Transitions</em>' reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECState_InTransitions()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECTransition#getDestination
	 * @model opposite="destination"
	 * @generated
	 */
	EList<ECTransition> getInTransitions();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true"
	 * @generated
	 */
	boolean isStartState();

	/**
	 * Returns the value of the '<em><b>ECC</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.ECC#getECState <em>EC State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ECC</em>' container reference.
	 * @see #setECC(ECC)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getECState_ECC()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.ECC#getECState
	 * @model opposite="eCState" transient="false"
	 * @generated
	 */
	ECC getECC();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ECState#getECC <em>ECC</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ECC</em>' container reference.
	 * @see #getECC()
	 * @generated
	 */
	void setECC(ECC value);

} // ECState
