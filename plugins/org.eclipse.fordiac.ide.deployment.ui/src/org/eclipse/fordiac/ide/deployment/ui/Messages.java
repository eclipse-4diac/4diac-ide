/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2015 Profactor GbmH, fortiss GmbH
 * 				 2020                   Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Andrea Zoitl
 *     - externalized all translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.deployment.ui.messages"; //$NON-NLS-1$

	public static String AbstractDeploymentCommand_ExtendedDeploymentErrorMessage;

	public static String AbstractDeploymentCommand_SimpleDeploymentErrorMessage;

	public static String AbstractDeviceDeploymentCommand_DeviceName;

	public static String CleanDeviceHandler_CleanDeviceError;

	public static String CreateBootfilesWizard_GeneratingBootFilesForTheSelectedResources;

	public static String CreateBootfilesWizard_IProgressMonitorMonitor;

	public static String CreateBootfilesWizard_BootFileCreationError;

	public static String CreateBootFilesWizardPage_Destination;

	public static String CreateBootFilesWizardPage_DestinationNotSelected;

	public static String CreateBootFilesWizardPage_NoSystemSelected;

	public static String CreateBootFilesWizardPage_NothingSelectedForBootFileGeneration;

	public static String DeleteResourceHandler_DeleteResourceError;

	public static String DeleteResourceHandler_Resource;

	public static String DownloadSelectionTree_Selection;

	public static String DownloadSelectionTree_MgrId;

	public static String DownloadSelectionTree_Properties;

	/** The Error annotation_ download error. */
	public static String ErrorAnnotation_DeploymentError;
	public static String WarningAnnotation_DeploymentWarning;

	/** Title of the create bootfile wizard */
	public static String FordiacCreateBootfilesWizard_LABEL_Window_Title;

	/** Page name of the create bootfile wizard page */
	public static String FordiacCreateBootfilesWizard_PageName;

	/** Description of the create bootfile page */
	public static String FordiacCreateBootfilesWizard_PageDESCRIPTION;

	/** Title of the create bootfile wizard page */
	public static String FordiacCreateBootfilesWizard_PageTITLE;

	public static String FullyCleanDeviceHandler_FullyCleanDeviceError;

	public static String FullyCleanDeviceHandler_DeleteResourcesFromDevice;

	public static String FullyCleanDeviceHandler_FullyCleanDevice;

	public static String FullyCleanDeviceHandler_Delete;

	public static String KillDeviceHandler_KillDeviceError;

	/** The Log listener_ malformed error. */
	public static String LogListener_MalformedError;

	/** The Log listener_ returned error. */
	public static String LogListener_ReturnedError;

	public static String LogListener_ErrorAnnotation;

	/** The Output_ clear action label. */
	public static String Output_ClearActionLabel;

	/** The Output_ clear description. */
	public static String Output_ClearDescription;

	/** The Output_ clear tooltip. */
	public static String Output_ClearTooltip;

	/** The Output_ comment. */
	public static String Output_Comment;

	public static String Output_FormattedXML;

	public static String XMLFormatter_ExceptionDuringXMLFormating;

	public static String DeploymentHandler_ErrorsInAutomationSystem;
	public static String DeploymentHandler_DeployLabel;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
