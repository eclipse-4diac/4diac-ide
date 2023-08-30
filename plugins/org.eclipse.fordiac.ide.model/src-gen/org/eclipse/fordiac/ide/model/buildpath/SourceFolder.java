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
package org.eclipse.fordiac.ide.model.buildpath;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getIncludes <em>Includes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getExcludes <em>Excludes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getBuildpath <em>Buildpath</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getSourceFolder()
 * @model extendedMetaData="name='sourceFolder' kind='elementOnly'"
 * @generated
 */
public interface SourceFolder extends EObject {
	/**
	 * Returns the value of the '<em><b>Includes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.buildpath.Pattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Includes</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getSourceFolder_Includes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='include'"
	 * @generated
	 */
	EList<Pattern> getIncludes();

	/**
	 * Returns the value of the '<em><b>Excludes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.buildpath.Pattern}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Excludes</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getSourceFolder_Excludes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='exclude'"
	 * @generated
	 */
	EList<Pattern> getExcludes();

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.buildpath.Attribute}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getSourceFolder_Attributes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='attribute'"
	 * @generated
	 */
	EList<Attribute> getAttributes();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getSourceFolder_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Buildpath</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.buildpath.Buildpath#getSourceFolders <em>Source Folders</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Buildpath</em>' container reference.
	 * @see #setBuildpath(Buildpath)
	 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getSourceFolder_Buildpath()
	 * @see org.eclipse.fordiac.ide.model.buildpath.Buildpath#getSourceFolders
	 * @model opposite="sourceFolders" transient="false"
	 * @generated
	 */
	Buildpath getBuildpath();

	/**
	 * Sets the value of the '{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getBuildpath <em>Buildpath</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Buildpath</em>' container reference.
	 * @see #getBuildpath()
	 * @generated
	 */
	void setBuildpath(Buildpath value);

} // SourceFolder
