/**
 * ******************************************************************************
 * * Copyright (c) 2012, 2013, 2018 Profactor GmbH, fortiss GmbH, Johannes Kepler University
 * * 
 * * All rights reserved. This program and the accompanying materials
 * * are made available under the terms of the Eclipse Public License v1.0
 * * which accompanies this distribution, and is available at
 * * http://www.eclipse.org/legal/epl-v10.html
 * *
 * * Contributors:
 * *   Gerhard Ebenhofer, Alois Zoitl
 * *     - initial API and implementation and/or initial documentation
 * *   Alois Zoitl - moved to deployment and reworked it to a device response model
 * ******************************************************************************
 * 
 */
package org.eclipse.fordiac.ide.deployment.devResponse;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>FB List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.devResponse.FBList#getFbs <em>Fbs</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage#getFBList()
 * @model
 * @generated
 */
public interface FBList extends EObject {
	/**
	 * Returns the value of the '<em><b>Fbs</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.deployment.devResponse.FB}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fbs</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fbs</em>' reference list.
	 * @see org.eclipse.fordiac.ide.deployment.devResponse.DevResponsePackage#getFBList_Fbs()
	 * @model
	 * @generated
	 */
	EList<FB> getFbs();

} // FBList
