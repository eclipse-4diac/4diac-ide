/********************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Compiler</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getProduct <em>Product</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVendor <em>Vendor</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVersion <em>Version</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompiler()
 * @model
 * @generated
 */
public interface Compiler extends EObject {
	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute. The default
	 * value is <code>"C"</code>. The literals are from the enumeration
	 * {@link org.eclipse.fordiac.ide.model.libraryElement.Language}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Language</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Language
	 * @see #isSetLanguage()
	 * @see #unsetLanguage()
	 * @see #setLanguage(Language)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompiler_Language()
	 * @model default="C" unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='Language'"
	 * @generated
	 */
	Language getLanguage();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.Language
	 * @see #isSetLanguage()
	 * @see #unsetLanguage()
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(Language value);

	/**
	 * Unsets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isSetLanguage()
	 * @see #getLanguage()
	 * @see #setLanguage(Language)
	 * @generated
	 */
	void unsetLanguage();

	/**
	 * Returns whether the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getLanguage <em>Language</em>}' attribute is set.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return whether the value of the '<em>Language</em>' attribute is set.
	 * @see #unsetLanguage()
	 * @see #getLanguage()
	 * @see #setLanguage(Language)
	 * @generated
	 */
	boolean isSetLanguage();

	/**
	 * Returns the value of the '<em><b>Product</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Product</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Product</em>' attribute.
	 * @see #setProduct(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompiler_Product()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Product'"
	 * @generated
	 */
	String getProduct();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getProduct <em>Product</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Product</em>' attribute.
	 * @see #getProduct()
	 * @generated
	 */
	void setProduct(String value);

	/**
	 * Returns the value of the '<em><b>Vendor</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vendor</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Vendor</em>' attribute.
	 * @see #setVendor(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompiler_Vendor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Vendor'"
	 * @generated
	 */
	String getVendor();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVendor <em>Vendor</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vendor</em>' attribute.
	 * @see #getVendor()
	 * @generated
	 */
	void setVendor(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getCompiler_Version()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Version'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.Compiler#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // Compiler
