/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.systemconfiguration.segment.communication;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Default Configuration</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.systemconfiguration.segment.communication.DefaultConfiguration#getTarget
 * <em>Target</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.communication.CommunicationPackage#getDefaultConfiguration()
 * @model
 * @generated */
public interface DefaultConfiguration extends CommunicationConfiguration {
	/** Returns the value of the '<em><b>Target</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(CommunicationMappingTarget)
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.communication.CommunicationPackage#getDefaultConfiguration_Target()
	 * @model containment="true" resolveProxies="true" required="true"
	 * @generated */
	CommunicationMappingTarget getTarget();

	/** Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.communication.DefaultConfiguration#getTarget
	 * <em>Target</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated */
	void setTarget(CommunicationMappingTarget value);

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated */
	String getId();

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation"
	 * @generated */
	@Override
	EList<CommunicationMappingTarget> getMappingTargets();

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation"
	 * @generated */
	@Override
	EList<VarDeclaration> getParameters();

} // DefaultConfiguration
