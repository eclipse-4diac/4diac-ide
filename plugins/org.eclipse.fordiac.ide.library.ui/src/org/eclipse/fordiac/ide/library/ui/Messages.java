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
package org.eclipse.fordiac.ide.library.ui;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "plugin"; //$NON-NLS-1$

	public static String AddLibraryDependency_Change_Title;

	public static String DeleteLibraryParticipant_Name;
	public static String DeleteLibraryParticipant_Change_Title;
	public static String DeleteLibraryParticipant_Block_Delete;
	public static String DirsWithArchives;
	public static String DirsWithUnzippedTypeLibs;

	public static String ExtractedLibraryImportWizard;

	public static String ImportExtractedFiles;

	public static String LibraryPage_Comment;
	public static String LibraryPage_Name;
	public static String LibraryPage_SymbolicName;
	public static String LibraryPage_Version;
	public static String LibraryPage_Sorting;
	public static String LibraryPage_Path;
	public static String LibraryPage_Columns;

	public static String LibrarySourceBuilder_gitlab_package_version;

	public static String LibrarySourceBuilder_project;

	public static String LibrarySourceBuilder_pkg;

	public static String LibrarySourceBuilder_v;

	public static String LibrarySourceBuilder_gitlab_project;

	public static String LibrarySourceBuilder_comment;

	public static String LibrarySourceBuilder_dir;

	public static String LibrarySourceBuilder_file;

	public static String LibrarySourceBuilder_file_system;

	public static String LibrarySourceBuilder_gitlab_package;

	public static String LibrarySourceBuilder_lib;

	public static String LibrarySourceBuilder_name;

	public static String LibrarySourceBuilder_path;

	public static String LibrarySourceBuilder_sym_name;

	public static String LibrarySourceBuilder_uri;

	public static String LibrarySourceBuilder_version;

	public static String PreferenceForceLoad;
	public static String PreferenceLoadingGroup;

	public static String UnifiedLibraryImportWizardPage_Available_Libraries;

	public static String UnifiedLibraryImportWizardPage_brows;

	public static String UnifiedLibraryImportWizardPage_config;

	public static String UnifiedLibraryImportWizardPage_failed;

	public static String UnifiedLibraryImportWizardPage_feiled_load;

	public static String UnifiedLibraryImportWizardPage_hide_non_valid;

	public static String UnifiedLibraryImportWizardPage_ImportIntoProject;

	public static String UnifiedLibraryImportWizardPage_LibraryImport;

	public static String UnifiedLibraryImportWizardPage_loading;

	public static String UnifiedLibraryImportWizardPage_loading_from;

	public static String UnifiedLibraryImportWizardPage_manage;

	public static String UnifiedLibraryImportWizardPage_no_lib;

	public static String UnifiedLibraryImportWizardPage_no_lib_Available;

	public static String UnifiedLibraryImportWizardPage_no_lib_src;

	public static String UnifiedLibraryImportWizardPage_no_src;

	public static String UnifiedLibraryImportWizardPage_no_project;

	public static String UnifiedLibraryImportWizardPage_op_cancled;

	public static String UnifiedLibraryImportWizardPage_refresh;

	public static String UnifiedLibraryImportWizardPage_sel_lib;

	public static String UnifiedLibraryImportWizardPage_select_to_see_details;

	public static String UnifiedLibraryImportWizardPage_show_latest;

	public static String UnifiedLibraryImportWizardPage_source;

	public static String UnifiedLibraryImportWizardPage_work_with;

	// Manage Library Wizard
	public static String ManageLibraryWizard_Label;
	public static String ManageLibraryWizard_Description;

	public static String ManageLibraryWizard_CurrentVersion;
	public static String ManageLibraryWizard_Change;
	public static String ManageLibraryWizard_LoadRemoteVersions;
	public static String ManageLibraryWizard_SymbolicName;
	public static String ManageLibraryWizard_PlannigPage_Titel;
	public static String ManageLibraryWizard_PreviewPage_Titel;
	public static String ManageLibraryWizard_PreviewPage_Description;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
