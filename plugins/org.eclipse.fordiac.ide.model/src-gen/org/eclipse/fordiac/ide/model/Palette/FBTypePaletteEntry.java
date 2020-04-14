/********************************************************************************
 * Copyright (c) 2008, 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.Palette;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.dataimport.TypeImporter;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>FB Type
 * Palette Entry</b></em>'. <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.fordiac.ide.model.Palette.PalettePackage#getFBTypePaletteEntry()
 * @model
 * @generated
 */
public interface FBTypePaletteEntry extends PaletteEntry {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model kind="operation" required="true"
	 * @generated
	 */
	FBType getFBType();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	@Override
	void setType(LibraryElement type);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model dataType="org.eclipse.fordiac.ide.model.Palette.TypeImporter"
	 * @generated
	 */
	@Override
	TypeImporter getTypeImporter(Palette palette) throws CoreException, XMLStreamException;

} // FBTypePaletteEntry
