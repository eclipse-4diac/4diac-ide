/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022-2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.ecore.EObject;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Array Size</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ArraySize#getValue <em>Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.ArraySize#getVarDeclaration <em>Var Declaration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getArraySize()
 * @model
 * @generated
 */
public interface ArraySize extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getArraySize_Value()
	 * @model default=""
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ArraySize#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Var Declaration</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getArraySize <em>Array Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var Declaration</em>' container reference.
	 * @see #setVarDeclaration(VarDeclaration)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getArraySize_VarDeclaration()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getArraySize
	 * @model opposite="arraySize" transient="false"
	 * @generated
	 */
	VarDeclaration getVarDeclaration();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.ArraySize#getVarDeclaration <em>Var Declaration</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Var Declaration</em>' container reference.
	 * @see #getVarDeclaration()
	 * @generated
	 */
	void setVarDeclaration(VarDeclaration value);

} // ArraySize
