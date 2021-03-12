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

/** <!-- begin-user-doc --> A representation of the model object '<em><b>With</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.libraryElement.With#getVariables <em>Variables</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getWith()
 * @model
 * @generated */
public interface With extends EObject {
	/** Returns the value of the '<em><b>Variables</b></em>' reference. It is bidirectional and its opposite is
	 * '{@link org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getWiths <em>Withs</em>}'. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Variables</em>' reference.
	 * @see #setVariables(VarDeclaration)
	 * @see org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage#getWith_Variables()
	 * @see org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration#getWiths
	 * @model opposite="withs" required="true"
	 * @generated */
	VarDeclaration getVariables();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.libraryElement.With#getVariables
	 * <em>Variables</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Variables</em>' reference.
	 * @see #getVariables()
	 * @generated */
	void setVariables(VarDeclaration value);

} // With
