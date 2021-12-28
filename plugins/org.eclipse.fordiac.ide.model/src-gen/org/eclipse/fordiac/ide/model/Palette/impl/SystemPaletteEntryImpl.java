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
package org.eclipse.fordiac.ide.model.Palette.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.Palette.SystemPaletteEntry;

import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>System Palette Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class SystemPaletteEntryImpl extends PaletteEntryImpl implements SystemPaletteEntry {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SystemPaletteEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.SYSTEM_PALETTE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommonElementImporter getImporter() {
		return new org.eclipse.fordiac.ide.model.dataimport.SystemImporter(getFile());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AutomationSystem getSystem() {
		LibraryElement type = getType();
		if(type instanceof AutomationSystem){
		   return (AutomationSystem)type;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSystem(final LibraryElement system) {
		if(system instanceof AutomationSystem){
			super.setType(system);
		}else{
			super.setType(null);
			if(null != type){
				FordiacLogHelper .logError("tried to set no AutomationSystem as type entry for SystemPaletteEntry");//$NON-NLS-1$
			}
		}
	}

} //SystemPaletteEntryImpl
