/********************************************************************************
 * Copyright (c) 2008, 2010 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.dataimport.DEVImporter;
import org.eclipse.fordiac.ide.model.dataimport.IDeviceTypeImporter;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.DEVTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Device Type Palette Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class DeviceTypePaletteEntryImpl extends PaletteEntryImpl implements DeviceTypePaletteEntry {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeviceTypePaletteEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.DEVICE_TYPE_PALETTE_ENTRY;
	}
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeviceType getDeviceType() {
		LibraryElement type = getType();
		if((null !=type) && (type instanceof DeviceType)){
		   return (DeviceType) type;
		}
		return null;
	}

/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(final LibraryElement type) {
		if((null != type) && (type instanceof DeviceType)){
			super.setType(type);
		}else{
			super.setType(null);
			if(null != type){
				Status exception = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "tried to set no DeviceType as type entry for DeviceTypePaletteEntry");
				Activator.getDefault().getLog().log(exception);
			}
		}
	}

	@Override
	protected LibraryElement loadType() {
		DeviceType type = null;
		Palette palette = getGroup().getPallete();
		if (TypeLibraryTags.DEVICE_TYPE_FILE_ENDING.equalsIgnoreCase(getFile().getFileExtension())) {
			type = DEVImporter.importDEVType(getFile(), palette);
		} else {
			IDeviceTypeImporter importer = DEVTypeLibrary.getInstance().getDeviceTypeImporter(getFile().getFileExtension());
			type = importer.importDEVType(getFile());
		}
		return type;
	}
	
} //DeviceTypePaletteEntryImpl
