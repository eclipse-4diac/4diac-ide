/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.fordiac.ide.deployment.messages"; //$NON-NLS-1$

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

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
