/**
 * *******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.xmiexport.xmiexport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getVariable <em>Variable</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getTypeDeclaration <em>Type Declaration</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getResultType <em>Result Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportTypeDeclaration()
 * @model
 * @generated
 */
public interface XMIExportTypeDeclaration extends EObject {
	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference.
	 * @see #setVariable(VarDeclaration)
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportTypeDeclaration_Variable()
	 * @model
	 * @generated
	 */
	VarDeclaration getVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getVariable <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(VarDeclaration value);

	/**
	 * Returns the value of the '<em><b>Type Declaration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Declaration</em>' containment reference.
	 * @see #setTypeDeclaration(STTypeDeclaration)
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportTypeDeclaration_TypeDeclaration()
	 * @model containment="true"
	 * @generated
	 */
	STTypeDeclaration getTypeDeclaration();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getTypeDeclaration <em>Type Declaration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Declaration</em>' containment reference.
	 * @see #getTypeDeclaration()
	 * @generated
	 */
	void setTypeDeclaration(STTypeDeclaration value);

	/**
	 * Returns the value of the '<em><b>Result Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result Type</em>' containment reference.
	 * @see #setResultType(INamedElement)
	 * @see org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportPackage#getXMIExportTypeDeclaration_ResultType()
	 * @model containment="true"
	 * @generated
	 */
	INamedElement getResultType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.xmiexport.xmiexport.XMIExportTypeDeclaration#getResultType <em>Result Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result Type</em>' containment reference.
	 * @see #getResultType()
	 * @generated
	 */
	void setResultType(INamedElement value);

} // XMIExportTypeDeclaration
