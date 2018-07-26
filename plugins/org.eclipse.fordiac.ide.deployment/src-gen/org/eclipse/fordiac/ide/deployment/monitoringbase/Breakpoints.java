/**
 * ******************************************************************************
 *  * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *  * 
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *  *     - initial API and implementation and/or initial documentation
 *  ******************************************************************************
 * 
 */
package org.eclipse.fordiac.ide.deployment.monitoringbase;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Breakpoints</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.deployment.monitoringbase.Breakpoints#getBreakpoints <em>Breakpoints</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getBreakpoints()
 * @model
 * @generated
 */
public interface Breakpoints extends EObject {
	/**
	 * Returns the value of the '<em><b>Breakpoints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Breakpoints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Breakpoints</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBasePackage#getBreakpoints_Breakpoints()
	 * @model containment="true"
	 * @generated
	 */
	EList<MonitoringBaseElement> getBreakpoints();

} // Breakpoints
