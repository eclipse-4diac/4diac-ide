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

import org.eclipse.emf.ecore.EObject;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Var Initialization</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.model.data.VarInitialization#getInitialValue <em>Initial Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getVarInitialization()
 * @model
 * @generated */
public interface VarInitialization extends EObject {
	/** Returns the value of the '<em><b>Initial Value</b></em>' attribute. The default value is <code>""</code>. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Initial Value</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Initial Value</em>' attribute.
	 * @see #setInitialValue(String)
	 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getVarInitialization_InitialValue()
	 * @model default=""
	 * @generated */
	String getInitialValue();

	/** Sets the value of the '{@link org.eclipse.fordiac.ide.model.data.VarInitialization#getInitialValue <em>Initial
	 * Value</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Initial Value</em>' attribute.
	 * @see #getInitialValue()
	 * @generated */
	void setInitialValue(String value);

} // VarInitialization
