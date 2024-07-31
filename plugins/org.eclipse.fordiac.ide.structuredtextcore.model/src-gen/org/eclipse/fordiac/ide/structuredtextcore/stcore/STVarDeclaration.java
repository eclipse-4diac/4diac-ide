/**
 * *******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH,
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Martin Jobst, Martin Melik Merkumians
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.structuredtextcore.stcore;

import org.eclipse.emf.common.util.EList;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.ITypedElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST Var Declaration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getLocatedAt <em>Located At</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#isArray <em>Array</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getRanges <em>Ranges</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getCount <em>Count</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getMaxLength <em>Max Length</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getPragma <em>Pragma</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTVarDeclaration()
 * @model
 * @generated
 */
public interface STVarDeclaration extends ITypedElement {
	/**
	 * Returns the value of the '<em><b>Located At</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Located At</em>' reference.
	 * @see #setLocatedAt(INamedElement)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTVarDeclaration_LocatedAt()
	 * @model
	 * @generated
	 */
	INamedElement getLocatedAt();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getLocatedAt <em>Located At</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Located At</em>' reference.
	 * @see #getLocatedAt()
	 * @generated
	 */
	void setLocatedAt(INamedElement value);

	/**
	 * Returns the value of the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array</em>' attribute.
	 * @see #setArray(boolean)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTVarDeclaration_Array()
	 * @model
	 * @generated
	 */
	boolean isArray();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#isArray <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array</em>' attribute.
	 * @see #isArray()
	 * @generated
	 */
	void setArray(boolean value);

	/**
	 * Returns the value of the '<em><b>Ranges</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ranges</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTVarDeclaration_Ranges()
	 * @model containment="true"
	 * @generated
	 */
	EList<STExpression> getRanges();

	/**
	 * Returns the value of the '<em><b>Count</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Count</em>' attribute list.
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTVarDeclaration_Count()
	 * @model unique="false"
	 * @generated
	 */
	EList<String> getCount();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(INamedElement)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTVarDeclaration_Type()
	 * @model
	 * @generated
	 */
	INamedElement getType();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(INamedElement value);

	/**
	 * Returns the value of the '<em><b>Max Length</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Length</em>' containment reference.
	 * @see #setMaxLength(STExpression)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTVarDeclaration_MaxLength()
	 * @model containment="true"
	 * @generated
	 */
	STExpression getMaxLength();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getMaxLength <em>Max Length</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Length</em>' containment reference.
	 * @see #getMaxLength()
	 * @generated
	 */
	void setMaxLength(STExpression value);

	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value</em>' containment reference.
	 * @see #setDefaultValue(STInitializerExpression)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTVarDeclaration_DefaultValue()
	 * @model containment="true"
	 * @generated
	 */
	STInitializerExpression getDefaultValue();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getDefaultValue <em>Default Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' containment reference.
	 * @see #getDefaultValue()
	 * @generated
	 */
	void setDefaultValue(STInitializerExpression value);

	/**
	 * Returns the value of the '<em><b>Pragma</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pragma</em>' containment reference.
	 * @see #setPragma(STPragma)
	 * @see org.eclipse.fordiac.ide.structuredtextcore.stcore.STCorePackage#getSTVarDeclaration_Pragma()
	 * @model containment="true"
	 * @generated
	 */
	STPragma getPragma();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration#getPragma <em>Pragma</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pragma</em>' containment reference.
	 * @see #getPragma()
	 * @generated
	 */
	void setPragma(STPragma value);

} // STVarDeclaration
