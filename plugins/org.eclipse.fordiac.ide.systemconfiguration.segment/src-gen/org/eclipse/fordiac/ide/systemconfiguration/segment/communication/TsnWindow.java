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

import org.eclipse.fordiac.ide.model.libraryElement.CommunicationMappingTarget;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Tsn Window</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnWindow#getDuration
 * <em>Duration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.communication.CommunicationPackage#getTsnWindow()
 * @model
 * @generated */
public interface TsnWindow extends CommunicationMappingTarget {
	/** Returns the value of the '<em><b>Duration</b></em>' attribute. The default value is <code>"0"</code>. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Duration</em>' attribute.
	 * @see #setDuration(int)
	 * @see org.eclipse.fordiac.ide.systemconfiguration.segment.communication.CommunicationPackage#getTsnWindow_Duration()
	 * @model default="0" required="true"
	 * @generated */
	int getDuration();

	/** Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.systemconfiguration.segment.communication.TsnWindow#getDuration
	 * <em>Duration</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Duration</em>' attribute.
	 * @see #getDuration()
	 * @generated */
	void setDuration(int value);

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 * @generated */
	@Override
	String getName();

} // TsnWindow
