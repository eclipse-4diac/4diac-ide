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
package org.eclipse.fordiac.ide.model.data;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Dword Type</b></em>'. <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.fordiac.ide.model.data.DataPackage#getDwordType()
 * @model
 * @generated */
public interface DwordType extends AnyBitType {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean" required="true" otherRequired="true"
	 * @generated */
	@Override
	boolean isCompatibleWith(DataType other);

} // DwordType
