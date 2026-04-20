/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.library.messages"; //$NON-NLS-1$

	public static String Confirm;

	public static String ErrorMarkerLibNotAvailable;
	public static String ErrorMarkerStandardLibNotAvailable;
	public static String ErrorMarkerVersionRangeEmpty;

	public static String ImportFailed;
	public static String ImportFailedOnLinkCreation;
	public static String InstanceUpdate;

	public static String LibraryManager_BrokenLink;

	public static String LibraryBuilder_CleaningLibrary;

	public static String LibraryBuilder_LibraryBuild;

	public static String LibraryBuilder_ResolveProjectDependencies;

	public static String LibraryManager_BuildingDependencyGraph;

	public static String LibraryManager_ChekForLibraryChanges;

	public static String LibraryManager_CheckLinks;

	public static String LibraryManager_DownloadJobName;

	public static String LibraryManager_FindingPreferredLibraryVersion;

	public static String LibraryManager_LibraryDownload;

	public static String LibraryManager_RemovingUnnecessaryLinks;

	public static String LibraryManager_ResolvingDependency;

	public static String LibraryManager_ResolvingLibraryDependencies;

	public static String LibraryManager_SetStandardLibrariesReadOnly;

	public static String LibraryManager_UnresolvableDependencies;

	public static String LibraryManager_UpdateLibraryPackage;

	public static String PreferenceForceLoad;
	public static String PreferenceLoadingGroup;

	public static String OldTypeLibVersionCouldNotBeDeleted;

	public static String TypeLibrary_ProjectLoadingProblem;
	public static String TypeLibrary_LibraryLoadingProblem;

	public static String UpdatedInstances;

	public static String Warning;
	public static String WillBeImported;

	public static String DownloadNullResult;
	public static String DownloadUnexpectedError;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
