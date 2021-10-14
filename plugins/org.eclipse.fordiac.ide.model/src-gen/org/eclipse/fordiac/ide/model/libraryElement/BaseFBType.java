/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement;

import org.eclipse.emf.common.util.EList;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Base FB Type</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getInternalVars <em>Internal Vars</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.BaseFBType#getInternalFbs <em>Internal Fbs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getBaseFBType()
 * @model
 * @generated */
public interface BaseFBType extends FBType {
	/** Returns the value of the '<em><b>Internal Vars</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Internal Vars</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Internal Vars</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getBaseFBType_InternalVars()
	 * @model containment="true" extendedMetaData="kind='element' name='VarDeclaration' namespace='##targetNamespace'"
	 * @generated */
	EList<VarDeclaration> getInternalVars();

	/** Returns the value of the '<em><b>Internal Fbs</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.fordiac.ide.model.libraryElement.FB}. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Internal Fbs</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getBaseFBType_InternalFbs()
	 * @model containment="true" extendedMetaData="kind='element' name='VarDeclaration' namespace='##targetNamespace'"
	 * @generated */
	EList<FB> getInternalFbs();

} // BaseFBType
