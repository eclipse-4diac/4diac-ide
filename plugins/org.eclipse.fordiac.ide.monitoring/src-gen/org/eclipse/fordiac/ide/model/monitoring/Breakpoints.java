/*******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.monitoring;

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
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.Breakpoints#getBreakpoints <em>Breakpoints</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getBreakpoints()
 * @model
 * @generated
 */
public interface Breakpoints extends EObject {
	/**
	 * Returns the value of the '<em><b>Breakpoints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.fordiac.ide.model.monitoring.MonitoringElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Breakpoints</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Breakpoints</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage#getBreakpoints_Breakpoints()
	 * @model containment="true"
	 * @generated
	 */
	EList<MonitoringElement> getBreakpoints();

} // Breakpoints
