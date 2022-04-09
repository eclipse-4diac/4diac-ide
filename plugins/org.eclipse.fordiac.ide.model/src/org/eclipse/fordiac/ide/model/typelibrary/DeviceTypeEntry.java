/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
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
 *    Alois Zoitl  - turned the Palette model into POJOs
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary;

import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/** <!-- begin-user-doc --> A representation of the model object '<em><b>Device Type Palette Entry</b></em>'. <!--
 * end-user-doc -->
 *
 *
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getDeviceTypePaletteEntry()
 * @model
 * @generated */
public interface DeviceTypeEntry extends TypeEntry {
	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" required="true"
	 * @generated */
	DeviceType getDeviceType();

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model
	 * @generated */
	@Override
	void setType(LibraryElement type);

	/** <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @model kind="operation" dataType="org.eclipse.fordiac.ide.model.Palette.CommonElementImporter"
	 * @generated */
	@Override
	CommonElementImporter getImporter();

} // DeviceTypePaletteEntry
