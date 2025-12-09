/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *               2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.model.libraryElement.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.fordiac.ide.model.libraryElement.CommunicationConfiguration;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementPackage;
import org.eclipse.fordiac.ide.model.libraryElement.Segment;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Communication Configuration</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated */
public abstract class CommunicationConfigurationImpl extends EObjectImpl implements CommunicationConfiguration {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	protected CommunicationConfigurationImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return LibraryElementPackage.Literals.COMMUNICATION_CONFIGURATION;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated */
	@Override
	public Segment getSegment() {
		return (Segment) eContainer();
	}

} // CommunicationConfigurationImpl
