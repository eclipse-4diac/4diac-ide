/**
 * *******************************************************************************
 *  Copyright (c) 2023 Primetals Technologies Austria GmbH
 * 
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0 which is available at
 *  http://www.eclipse.org/legal/epl-2.0.
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *    Michael Oberlehner , Bianca Wiesmayr- initial API and implementation and/or initial documentation
 *  *******************************************************************************
 */
package org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root Level</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.RootLevel#getLevels <em>Levels</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage#getRootLevel()
 * @model
 * @generated
 */
public interface RootLevel extends EObject {
	/**
	 * Returns the value of the '<em><b>Levels</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Level}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Levels</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage#getRootLevel_Levels()
	 * @model containment="true"
	 * @generated
	 */
	EList<Level> getLevels();

} // RootLevel
