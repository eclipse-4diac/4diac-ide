/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compiler Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getCompiler <em>Compiler</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getClassdef <em>Classdef</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getHeader <em>Header</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getImports <em>Imports</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompilerInfo()
 * @model
 * @generated
 */
public interface CompilerInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Compiler</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.Compiler}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compiler</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompilerInfo_Compiler()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Compiler' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<org.eclipse.fordiac.ide.model.libraryElement.Compiler> getCompiler();

	/**
	 * Returns the value of the '<em><b>Classdef</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Classdef</em>' attribute.
	 * @see #setClassdef(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompilerInfo_Classdef()
	 * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='classdef'"
	 * @generated
	 */
	String getClassdef();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getClassdef <em>Classdef</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Classdef</em>' attribute.
	 * @see #getClassdef()
	 * @generated
	 */
	void setClassdef(String value);

	/**
	 * Returns the value of the '<em><b>Header</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Header</em>' attribute.
	 * @see #setHeader(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompilerInfo_Header()
	 * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='header'"
	 * @generated
	 */
	String getHeader();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getHeader <em>Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Header</em>' attribute.
	 * @see #getHeader()
	 * @generated
	 */
	void setHeader(String value);

	/**
	 * Returns the value of the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Name</em>' attribute.
	 * @see #setPackageName(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompilerInfo_PackageName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='packageName'"
	 * @generated
	 */
	String getPackageName();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.CompilerInfo#getPackageName <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Name</em>' attribute.
	 * @see #getPackageName()
	 * @generated
	 */
	void setPackageName(String value);

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.Import}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imports</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompilerInfo_Imports()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<Import> getImports();

} // CompilerInfo
