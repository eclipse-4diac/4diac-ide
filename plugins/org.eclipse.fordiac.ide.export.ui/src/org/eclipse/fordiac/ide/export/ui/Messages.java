/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 *               2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *   Michael Oberlehner - add support for additional source directories during type export
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.ui;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "plugin"; //$NON-NLS-1$

	public static String ExportStatusMessageDialog_4diacIDETypeExportErrors;

	public static String ExportStatusMessageDialog_DuringTypeExportTheFollowingIssuesHaveBeenIdentified;

	public static String ExportStatusMessageDialog_ErrorsNotEmpty;

	public static String ExportStatusMessageDialog_ExportStatusMessageDialog;

	public static String ExportStatusMessageDialog_InfosNotEmpty;

	public static String ExportStatusMessageDialog_WarningsNotEmpty;

	public static String FordiacExportWizard_DESCRIPTION_WizardPage;

	public static String FordiacExportWizard_LABEL_Window_Title;

	public static String FordiacExportWizard_TITLE_WizardPage;

	public static String FordiacExportWizard_WizardPage;

	public static String FORTEExportPreferences_CompareEditorForMerging;

	public static String FORTEExportPreferences_DefaultCompareEditorOpener;

	public static String SelectFBTypesWizardPage_Browse;

	public static String SelectFBTypesWizardPage_ExportDestination;

	public static String SelectFBTypesWizardPage_Exporter;

	public static String SelectFBTypesWizardPage_ExportToDirectory;

	public static String SelectFBTypesWizardPage_ExportfilterNeedsToBeSelected;

	public static String SelectFBTypesWizardPage_DestinationDirectoryNeedsToBeChosen;

	public static String SelectFBTypesWizardPage_NoTypeSelected;

	public static String SelectFBTypesWizardPage_OverwriteWithoutWarning;

	public static String SelectFBTypesWizardPage_SelectADirectoryToExportTo;

	public static String TypeExport;

	public static String TypeExport_AddExistingSourceDirectory;

	public static String TypeExport_AdditionalSourceDirectories;

	public static String TypeExport_AdditionalSourceDirectoriesDescription;

	public static String TypeExport_Enable;

	public static String TypeExport_Exporter;

	public static String TypeExport_OutputFolder;

	public static String TypeExport_Settings;

	public static String TypeExport_FileDialogMessage;

	public static String TypeExport_GeneratedSourceDirectory;

	public static String TypeExport_InvalidPath;

	public static String TypeExport_InvalidSourceDirectories;

	public static String TypeExport_HiddenSourceDirectory;

	public static String TypeExport_NewSourceDirectory;

	public static String TypeExport_NewSourceDirectoryMessage;

	public static String TypeExport_OutputFolderMissing;

	public static String TypeExport_RemoveSourceDirectory;

	public static String TypeExport_SelectSourceDirectories;

	public static String TypeExport_SelectSourceDirectoriesMessage;

	public static String TypeExport_SourceDirectoryExists;

	public static String TypeExport_UpdateExportedFiles;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
