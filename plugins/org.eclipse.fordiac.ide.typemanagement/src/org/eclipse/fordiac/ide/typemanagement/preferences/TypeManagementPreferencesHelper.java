/*******************************************************************************
 * Copyright (c) 2018, 2024 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.typemanagement.preferences;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.model.libraryElement.Identification;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public final class TypeManagementPreferencesHelper {

	private TypeManagementPreferencesHelper() {
		// do nothing
	}

	public static void setupVersionInfo(final LibraryElement type) {
		final VersionInfo versionInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();
		final IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				TypeManagementPreferenceConstants.TYPE_MANAGEMENT_PREFERENCES_ID);

		// version
		setupVersion(versionInfo, store);

		// Organization
		setupOrganization(versionInfo, store);

		// Author
		setupAuthor(versionInfo, store);

		// Date
		setupDate(versionInfo);

		// Remarks
		setupRemarks(versionInfo, store);

		type.getVersionInfo().clear();
		type.getVersionInfo().add(versionInfo);
	}

	private static void setupVersion(final VersionInfo versionInfo, final IPreferenceStore store) {
		versionInfo.setVersion(store.getString(TypeManagementPreferenceConstants.P_VERSION));
	}

	private static void setupOrganization(final VersionInfo versionInfo, final IPreferenceStore store) {
		versionInfo.setOrganization(store.getString(TypeManagementPreferenceConstants.P_ORGANIZATION));
	}

	private static void setupAuthor(final VersionInfo versionInfo, final IPreferenceStore store) {
		versionInfo.setAuthor(store.getString(TypeManagementPreferenceConstants.P_AUTHOR));
	}

	private static void setupDate(final VersionInfo versionInfo) {
		versionInfo.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()))); //$NON-NLS-1$
	}

	private static void setupRemarks(final VersionInfo versionInfo, final IPreferenceStore store) {
		versionInfo.setRemarks(store.getString(TypeManagementPreferenceConstants.P_REMARKS));
	}

	public static void setupIdentification(final LibraryElement type) {

		Identification identification = type.getIdentification();

		if (type.getIdentification() == null) {
			identification = LibraryElementFactory.eINSTANCE.createIdentification();
		}

		final IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				TypeManagementPreferenceConstants.TYPE_MANAGEMENT_PREFERENCES_ID);

		// Standard.
		setupStandard(identification, store);

		// Classification
		setupClassification(identification, store);

		// Application Domain
		setupTypeDomain(identification, store);

		// Function
		setupFunction(identification, store);

		// Type
		setupType(identification, store);

		// Description
		setupDescription(identification, store);

		type.setIdentification(identification);
	}

	public static void setupStandard(final Identification identification, final IPreferenceStore store) {
		// If the standard is defined and the preference is empty, don't load it
		if (!(store.getString(TypeManagementPreferenceConstants.P_STANDARD).isEmpty())) {
			identification.setStandard(store.getString(TypeManagementPreferenceConstants.P_STANDARD));
		}
	}

	public static void setupClassification(final Identification identification, final IPreferenceStore store) {
		identification.setClassification(store.getString(TypeManagementPreferenceConstants.P_CLASSIFICATION));
	}

	public static void setupTypeDomain(final Identification identification, final IPreferenceStore store) {
		identification.setApplicationDomain(store.getString(TypeManagementPreferenceConstants.P_APPLICATION_DOMAIN));
	}

	public static void setupFunction(final Identification identification, final IPreferenceStore store) {
		identification.setFunction(store.getString(TypeManagementPreferenceConstants.P_FUNCTION));
	}

	public static void setupType(final Identification identification, final IPreferenceStore store) {
		identification.setType(store.getString(TypeManagementPreferenceConstants.P_TYPE));
	}

	public static void setupDescription(final Identification identification, final IPreferenceStore store) {
		identification.setDescription(store.getString(TypeManagementPreferenceConstants.P_DESCRIPTION));
	}

}
