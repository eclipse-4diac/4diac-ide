/*******************************************************************************
 * Copyright (c) 2018 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.preferences;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.typemanagement.Activator;

public final class TypeManagementPreferencesHelper {
	
	private TypeManagementPreferencesHelper() {
		// do nothing
	}
	
	public static void setupVersionInfo(LibraryElement type) {
		VersionInfo versionInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();

		// version
		setupVersion(versionInfo);

		// Organization
		setupOrganization(versionInfo);

		// Author
		setupAuthor(versionInfo);
		
		//Date 
		setupDate(versionInfo);
		
		//Remarks
		setupRemarks(versionInfo);

		if (type instanceof AdapterType) {
			type = ((AdapterType) type).getAdapterFBType();
		}
		type.getVersionInfo().clear();
		type.getVersionInfo().add(versionInfo);
	}
	
	public static void setupVersion(VersionInfo versionInfo) {
		versionInfo.setVersion(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_VERSION));
	}
	
	public static void setupOrganization(VersionInfo versionInfo) {
		versionInfo.setOrganization(
				Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_ORGANIZATION));
	}
	
	public static void setupAuthor(VersionInfo versionInfo) {
		versionInfo.setAuthor(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_AUTHOR));
	}
	
	public static void setupDate(VersionInfo versionInfo) {
		versionInfo.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()))); //$NON-NLS-1$
	}
	
	public static void setupRemarks(VersionInfo versionInfo) {
		versionInfo.setRemarks(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_REMARKS));
	}
	
	public static void setupIdentification(LibraryElement type) {

		Identification identification = type.getIdentification();
		if (type instanceof AdapterType) {
			type = ((AdapterType) type).getAdapterFBType();
			identification = type.getIdentification();
		}
		
		// Standard.
		setupStandard(identification);

		// Classification
		setupClassification(identification);

		// Application Domain
		setupTypeDomain(identification);
		
		// Function
		setupFunction(identification);
		
		// Type
		setupType(identification);
		
		// Description
		setupDescription(identification);
		
		type.setIdentification(identification);
	}
	
	public static void setupStandard(Identification identification) {
		// If the standard is defined and the preference is empty, don't load it
		if(!(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_STANDARD).isEmpty())) { 
			identification.setStandard(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_STANDARD));
		}
	}
	
	public static void setupClassification(Identification identification) {
		identification.setClassification(
				Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_CLASSIFICATION));
	}
	
	public static void setupTypeDomain(Identification identification) {
		identification.setApplicationDomain(
				Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_APPLICATION_DOMAIN));
	}
	
	public static void setupFunction(Identification identification) {
		identification
		.setFunction(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_FUNCTION));
	}	
	
	public static void setupType(Identification identification) {
		identification.setType(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_TYPE));
	}
	
	
	public static void setupDescription(Identification identification) {
		identification.setDescription(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_DESCRIPTION));
	}

}
