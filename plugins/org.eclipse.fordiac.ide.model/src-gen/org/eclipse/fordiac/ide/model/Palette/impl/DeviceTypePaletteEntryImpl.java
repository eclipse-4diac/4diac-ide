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
package org.eclipse.fordiac.ide.model.Palette.impl;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.dataimport.TypeImporter;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Device
 * Type Palette Entry</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public class DeviceTypePaletteEntryImpl extends PaletteEntryImpl implements DeviceTypePaletteEntry {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected DeviceTypePaletteEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.DEVICE_TYPE_PALETTE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeviceType getDeviceType() {
		LibraryElement type = getType();
		if(type instanceof DeviceType){
		   return (DeviceType) type;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(final LibraryElement type) {
		if(type instanceof DeviceType){
			super.setType(type);
		}else{
			super.setType(null);
			if(null != type){
				Status exception = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "tried to set no DeviceType as type entry for DeviceTypePaletteEntry");  //$NON-NLS-1$
				Activator.getDefault().getLog().log(exception);
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TypeImporter getTypeImporter() {
		return new org.eclipse.fordiac.ide.model.dataimport.DEVImporter();
	}

} // DeviceTypePaletteEntryImpl
