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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Version
 * Info</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getDate <em>Date</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getOrganization <em>Organization</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getRemarks <em>Remarks</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getVersion <em>Version</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVersionInfo()
 * @model
 * @generated
 */
public interface VersionInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Author</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Author</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVersionInfo_Author()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Author'"
	 * @generated
	 */
	String getAuthor();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
	void setAuthor(String value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVersionInfo_Date()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Date'"
	 * @generated
	 */
	String getDate();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(String value);

	/**
	 * Returns the value of the '<em><b>Organization</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Organization</em>' attribute.
	 * @see #setOrganization(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVersionInfo_Organization()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Organization'"
	 * @generated
	 */
	String getOrganization();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getOrganization
	 * <em>Organization</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Organization</em>' attribute.
	 * @see #getOrganization()
	 * @generated
	 */
	void setOrganization(String value);

	/**
	 * Returns the value of the '<em><b>Remarks</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remarks</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Remarks</em>' attribute.
	 * @see #setRemarks(String)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVersionInfo_Remarks()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='Remarks'"
	 * @generated
	 */
	String getRemarks();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getRemarks <em>Remarks</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remarks</em>' attribute.
	 * @see #getRemarks()
	 * @generated
	 */
	void setRemarks(String value);

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
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getVersionInfo_Version()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='Version'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.VersionInfo#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // VersionInfo
