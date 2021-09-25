/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GmbH, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment;

import org.eclipse.osgi.util.NLS;

/**
 * The Class Messages.
 */
@SuppressWarnings("squid:S3008")  // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.deployment.messages"; //$NON-NLS-1$

	public static String AbstractFileManagementHandler_CouldNotWriteFile;

	public static String AbstractFileManagementHandler_FileExists;

	public static String AbstractFileManagementHandler_CouldnotCreateFile;

	public static String AbstractFileManagementHandler_CouldnotCreateFileWithError;

	public static String AbstractMonitoringManager_ErrorInCreatingMonitoringManager;

	public static String AbstractMonitoringManager_NoMonitoringManagerProvided;

	public static String DeploymentCoordinator_DepoymentError;

	/** The DeploymentCoordinator_ERROR_Message. */
	public static String DeploymentCoordinator_ERROR_Message;

	/** The DeploymentCoordinator_LABEL_DownloadAborted. */
	public static String DeploymentCoordinator_LABEL_DownloadAborted;

	/** The DeploymentCoordinator_LABEL_PerformingDownload. */
	public static String DeploymentCoordinator_LABEL_PerformingDownload;

	/** The DeploymentCoordinator_MESSAGE_DefinedProfileNotSupported. */
	public static String DeploymentCoordinator_MESSAGE_DefinedProfileNotSupported;

	public static String DeploymentCoordinator_MESSAGE_ProfileNotSet;

	public static String DownloadRunnable_DownloadErrorDetails;

	public static String DownloadRunnable_MajorDownloadError;

	public static String DownloadRunnable_ResourceAlreadyExists;

	public static String DownloadRunnable_ResourceOverrideQuestion;

	public static String DownloadRunnable_Replace;

	public static String DownloadRunnable_ReplaceAll;

	public static String DownloadRunnable_ReassureOverideHeader;

	public static String DownloadRunnable_ReassureOveride;

	public static String DownloadRunnable_Warning;

	public static String DownloadRunnable_DeploymentErrorWarningMessage;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
