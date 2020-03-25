/********************************************************************************
 * Copyright (c) 2008, 2010, 2012 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Martin Jobst
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.data;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Structured Type</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.data.StructuredType#getMemberVariables <em>Member Variables</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getStructuredType()
 * @model
 * @generated
 */
public interface StructuredType extends AnyDerivedType {
	/**
	 * Returns the value of the '<em><b>Member Variables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Member Variables</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getStructuredType_MemberVariables()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='VarDeclaration' namespace='http://www.fordiac.org/61499/lib'"
	 * @generated
	 */
	EList<VarDeclaration> getMemberVariables();

} // StructuredType
