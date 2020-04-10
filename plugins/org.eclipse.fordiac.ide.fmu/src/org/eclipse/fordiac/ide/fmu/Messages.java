/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2020 Andrea Zoitl
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
 *   Andrea Zoitl
 *	   - Externalized translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.fmu.messages"; //$NON-NLS-1$

	public static String AnnotationMarkerAccess_LABEL_ErrorAnnotation;
	public static String AnnotationMarkerAccess_LABEL_WarningAnnotation;
	public static String CreateFMUWizard_FMUCreationError;
	public static String CreateFMUWizardPage_DestinationNotSelecte;
	public static String CreateFMUWizardPage_IncludeTheFollowingLibrariesInExportedFMU;
	public static String CreateFMUWizardPage_NoLibrariesSelectedToInclude;
	public static String CreateFMUWizardPage_NothingselectedForFMUGeneration;
	public static String CreateFMUWizardPage_SaveSelectedLibrariesForFutureFMUExports;
	public static String DownloadSelectionTreeView_COLLAPSE_ALL;
	public static String DownloadSelectionTreeView_EXPAND_ALL;
	public static String DownloadSelectionTreeView_LABEL_PopupMenu;
	public static String DownloadSelectionTreeView_Refresh;
	public static String ErrorAnnotation_DownloadError;
	public static String FMUDeviceManagementCommunicationHandler_BinaryDirectoryDoesNotExist;
	public static String FMUDeviceManagementCommunicationHandler_CouldNotCreateTheComponentsInsideTheTemporaryFolder;
	public static String FMUDeviceManagementCommunicationHandler_CouldNotCreateFolderInTheTemporaryFolder;
	public static String FMUDeviceManagementCommunicationHandler_CouldNotCreateTheTemporaryFolder;
	public static String FMUDeviceManagementCommunicationHandler_DoYouWantToRetry;
	public static String FMUDeviceManagementCommunicationHandler_GeneratingFMUsForDevice;
	public static String FMUDeviceManagementCommunicationHandler_InternalCopyingError;
	public static String FMUDeviceManagementCommunicationHandler_LibraryCouldNotBeFound;
	public static String FMUDeviceManagementCommunicationHandler_NoSelectedLibrariesWereFound;
	public static String FMUDeviceManagementCommunicationHandler_OutputFMUFileExistsOverwriteIt;
	public static String FMUDeviceManagementCommunicationHandler_OutputFolderDoesNotExistAndCouldNotBeCreated;
	public static String FMUDeviceManagementCommunicationHandler_TheDirectoryIsInvalid;
	public static String FMUDeviceManagementCommunicationHandler_UnableToCreateFolder;
	public static String FMUPreferencePage_BinariesLocation;
	public static String FMUPreferencePage_FMUPreferencesPage;
	public static String FMUPreferencePage_IncludeTheFollowingLibrariesInExportedFMU;
	public static String FMUPreferencePage_InsideTheSelectedPathTheFilesSearchedFor;
	public static String FordiacCreateFMUWizard_LABEL_Window_Title;
	public static String FordiacCreateFMUWizard_PageDESCRIPTION;
	public static String FordiacCreateFMUWizard_PageName;
	public static String FordiacCreateFMUWizard_PageTITLE;
	public static String LogListener_MalformedError;
	public static String LogListener_ReturnedError;
	public static String Mode_DownloadButtonLabel;
	public static String Output_ClearActionLabel;
	public static String Output_ClearDescription;
	public static String Output_ClearTooltip;
	public static String Output_Comment;
	public static String Output_DownloadError;
	public static String Output_DownloadWarning;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
