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
import org.eclipse.fordiac.ide.model.data.VarInitialization;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Var Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getArraySize <em>Array Size</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getVarInitialization <em>Var Initialization</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getWiths <em>Withs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVarDeclaration()
 * @model
 * @generated
 */
public interface VarDeclaration extends IInterfaceElement {
	/**
	 * Returns the value of the '<em><b>Array Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Size</em>' attribute.
	 * @see #setArraySize(int)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVarDeclaration_ArraySize()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='attribute' name='ArraySize'"
	 * @generated
	 */
	int getArraySize();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getArraySize <em>Array Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array Size</em>' attribute.
	 * @see #getArraySize()
	 * @generated
	 */
	void setArraySize(int value);

	/**
	 * Returns the value of the '<em><b>Var Initialization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Var Initialization</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var Initialization</em>' containment reference.
	 * @see #setVarInitialization(VarInitialization)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVarDeclaration_VarInitialization()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	VarInitialization getVarInitialization();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getVarInitialization <em>Var Initialization</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Var Initialization</em>' containment reference.
	 * @see #getVarInitialization()
	 * @generated
	 */
	void setVarInitialization(VarInitialization value);

	/**
	 * Returns the value of the '<em><b>Withs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.With}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.With#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Withs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Withs</em>' reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVarDeclaration_Withs()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.With#getVariables
	 * @model opposite="variables"
	 * @generated
	 */
	EList<With> getWiths();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return org.eclipse.fordiac.ide.model.Annotations.isArray(this);'"
	 * @generated
	 */
	boolean isArray();

} // VarDeclaration
