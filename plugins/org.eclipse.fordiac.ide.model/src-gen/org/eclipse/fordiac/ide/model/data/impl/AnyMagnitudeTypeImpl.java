/**
 * *******************************************************************************
 * Copyright (c) 2008 - 2018 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.model.data.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType;
import org.eclipse.fordiac.ide.model.data.DataPackage;
import org.eclipse.fordiac.ide.model.data.DataType;

/** <!-- begin-user-doc --> An implementation of the model object '<em><b>Any Magnitude Type</b></em>'. <!--
 * end-user-doc -->
 *
 * @generated */
public class AnyMagnitudeTypeImpl extends AnyElementaryTypeImpl implements AnyMagnitudeType {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	protected AnyMagnitudeTypeImpl() {
		super();
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	protected EClass eStaticClass() {
		return DataPackage.Literals.ANY_MAGNITUDE_TYPE;
	}

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated */
	@Override
	public boolean isCompatibleWith(final DataType other) {
		return org.eclipse.fordiac.ide.model.datatype.helper.GenericDataTypeCompatibility
				.isAnyMagnitudeCompatibleWith(other);
	}

} // AnyMagnitudeTypeImpl
