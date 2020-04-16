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
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.dataimport.TypeImporter;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.ResourceType;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Resource Type Entry</b></em>'. <!-- end-user-doc -->
 *
 * @generated
 */
public class ResourceTypeEntryImpl extends PaletteEntryImpl implements ResourceTypeEntry {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceTypeEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.RESOURCE_TYPE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceType getResourceType() {
		LibraryElement type = getType();
		if(type instanceof ResourceType){
		   return (ResourceType) type;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(final LibraryElement type) {
		if(type instanceof ResourceType){
			super.setType(type);
		}else{
			super.setType(null);
			if(null != type){
				Status exception = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "tried to set no ResourceType as type entry for ResourceTypeEntry"); //$NON-NLS-1$
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
		return new org.eclipse.fordiac.ide.model.dataimport.RESImporter();
	}

} // ResourceTypeEntryImpl
