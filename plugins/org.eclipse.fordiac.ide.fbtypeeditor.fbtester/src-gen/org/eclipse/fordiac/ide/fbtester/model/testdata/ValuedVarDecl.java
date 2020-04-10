/*******************************************************************************
 * Copyright (c) 2012 Profactor GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtester.model.testdata;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Valued
 * Var Decl</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl#getVarDeclaration
 * <em>Var Declaration</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl#getValue
 * <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getValuedVarDecl()
 * @model
 * @generated
 */
public interface ValuedVarDecl extends EObject {
	/**
	 * Returns the value of the '<em><b>Var Declaration</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Var Declaration</em>' reference isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Var Declaration</em>' reference.
	 * @see #setVarDeclaration(VarDeclaration)
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getValuedVarDecl_VarDeclaration()
	 * @model
	 * @generated
	 */
	VarDeclaration getVarDeclaration();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl#getVarDeclaration
	 * <em>Var Declaration</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Var Declaration</em>' reference.
	 * @see #getVarDeclaration()
	 * @generated
	 */
	void setVarDeclaration(VarDeclaration value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.eclipse.fordiac.ide.fbtester.model.testdata.TestdataPackage#getValuedVarDecl_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fbtester.model.testdata.ValuedVarDecl#getValue
	 * <em>Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // ValuedVarDecl
