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
 * A representation of the model object '<em><b>Buildpath</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.buildpath.Buildpath#getSourceFolders <em>Source Folders</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getBuildpath()
 * @model extendedMetaData="name='buildpath' kind='elementOnly'"
 * @generated
 */
public interface Buildpath extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Folders</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getBuildpath <em>Buildpath</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Folders</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.buildpath.BuildpathPackage#getBuildpath_SourceFolders()
	 * @see org.eclipse.fordiac.ide.model.buildpath.SourceFolder#getBuildpath
	 * @model opposite="buildpath" containment="true"
	 *        extendedMetaData="kind='element' name='sourceFolder'"
	 * @generated
	 */
	EList<SourceFolder> getSourceFolders();

} // Buildpath
