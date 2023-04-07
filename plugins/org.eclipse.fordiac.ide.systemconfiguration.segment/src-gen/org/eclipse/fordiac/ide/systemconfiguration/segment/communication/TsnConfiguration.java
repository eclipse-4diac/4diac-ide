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

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Tsn Configuration</b></em>'. <!-- end-user-doc
 * -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnConfiguration#getCycleTime <em>Cycle
 * Time</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnConfiguration#getWindows
 * <em>Windows</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.communication.CommunicationPackage#getTsnConfiguration()
 * @model
 * @generated */
public interface TsnConfiguration extends CommunicationConfiguration {
	/** Returns the value of the '<em><b>Cycle Time</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Cycle Time</em>' attribute.
	 * @see #setCycleTime(int)
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.communication.CommunicationPackage#getTsnConfiguration_CycleTime()
	 * @model required="true"
	 * @generated */
	int getCycleTime();

	/** Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnConfiguration#getCycleTime <em>Cycle
	 * Time</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Cycle Time</em>' attribute.
	 * @see #getCycleTime()
	 * @generated */
	void setCycleTime(int value);

	/** Returns the value of the '<em><b>Windows</b></em>' containment reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnWindow}. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @return the value of the '<em>Windows</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.communication.CommunicationPackage#getTsnConfiguration_Windows()
	 * @model containment="true" resolveProxies="true" required="true" upper="8"
	 * @generated */
	EList<TsnWindow> getWindows();

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

} // TsnConfiguration
