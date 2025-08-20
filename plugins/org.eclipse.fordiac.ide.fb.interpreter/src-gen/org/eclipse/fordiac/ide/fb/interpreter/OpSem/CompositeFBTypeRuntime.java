/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem;

import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Composite FB Type Runtime</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime#getCompositeFBType
 * <em>Composite FB Type</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime#getFbElement
 * <em>Fb Element</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime#getNetworkRuntime
 * <em>Network Runtime</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getCompositeFBTypeRuntime()
 * @model
 * @generated
 */
public interface CompositeFBTypeRuntime extends FBRuntimeAbstract {
	/**
	 * Returns the value of the '<em><b>Composite FB Type</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Composite FB Type</em>' containment reference.
	 * @see #setCompositeFBType(CompositeFBType)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getCompositeFBTypeRuntime_CompositeFBType()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated
	 */
	CompositeFBType getCompositeFBType();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime#getCompositeFBType
	 * <em>Composite FB Type</em>}' containment reference. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Composite FB Type</em>' containment
	 *              reference.
	 * @see #getCompositeFBType()
	 * @generated
	 */
	void setCompositeFBType(CompositeFBType value);

	/**
	 * Returns the value of the '<em><b>Fb Element</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Fb Element</em>' reference.
	 * @see #setFbElement(FBNetworkElement)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getCompositeFBTypeRuntime_FbElement()
	 * @model required="true"
	 * @generated
	 */
	FBNetworkElement getFbElement();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime#getFbElement
	 * <em>Fb Element</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @param value the new value of the '<em>Fb Element</em>' reference.
	 * @see #getFbElement()
	 * @generated
	 */
	void setFbElement(FBNetworkElement value);

	/**
	 * Returns the value of the '<em><b>Network Runtime</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Network Runtime</em>' reference.
	 * @see #setNetworkRuntime(FBNetworkRuntime)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getCompositeFBTypeRuntime_NetworkRuntime()
	 * @model required="true"
	 * @generated
	 */
	FBNetworkRuntime getNetworkRuntime();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.CompositeFBTypeRuntime#getNetworkRuntime
	 * <em>Network Runtime</em>}' reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value the new value of the '<em>Network Runtime</em>' reference.
	 * @see #getNetworkRuntime()
	 * @generated
	 */
	void setNetworkRuntime(FBNetworkRuntime value);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" required="true"
	 * @generated
	 */
	@Override
	CompositeFBType getModel();

} // CompositeFBTypeRuntime
