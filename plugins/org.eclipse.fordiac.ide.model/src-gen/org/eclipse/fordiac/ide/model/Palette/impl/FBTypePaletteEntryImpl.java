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
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.dataimport.FBTImporter;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>FB Type Palette Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class FBTypePaletteEntryImpl extends PaletteEntryImpl implements FBTypePaletteEntry {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FBTypePaletteEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.FB_TYPE_PALETTE_ENTRY;
	}

	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public FBType getFBType() {
		LibraryElement type = getType();
		if((null !=type) && (type instanceof FBType)){
		   return (FBType) type;
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
		if((null != type) && (type instanceof FBType)){
			super.setType(type);
		}else{
			super.setType(null);
			if(null != type){
				Status exception = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "tried to set no FBType as type entry for FBTypePaletteEntry");
				Activator.getDefault().getLog().log(exception);
			}
		}
	}

	@Override
	protected LibraryElement loadType() {
		LibraryElement retval = null;
		Palette palette = getGroup().getPallete();
		
		retval = new FBTImporter().importType(getFile(), palette);
		
		if (retval == null) {
			Activator.getDefault().logError("Error loading type: " + getFile().getName());
		}
		return retval;
	}

} //FBTypePaletteEntryImpl
