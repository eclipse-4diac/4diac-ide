/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Prankur Agarwal - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab;

import org.eclipse.osgi.util.NLS;

/** The Class Messages. */
@SuppressWarnings("squid:S3008") // tell sonar the java naming convention does not make sense for this class
public final class Messages extends NLS {
	private static String BUNDLE_NAME = "org.eclipse.fordiac.ide.gitlab.messages"; //$NON-NLS-1$

	public static String Configuration_Incomplete;
	public static String Connect;
	public static String Download_Error;
	public static String GET;
	public static String GitLab_Available_Packages;
	public static String GitLab_Import;
	public static String GitLab_Token;
	public static String GitLab_Token_Title;
	public static String GitLab_Url;
	public static String GitLab_Url_Title;
	public static String GitLab_Packages_And_Projects;
	public static String GitLab_PreferenceId;
	public static String Library_Not_Found;
	public static String No_Config;
	public static String Private_Token;
	public static String Token;
	public static String URL;
	public static String Version_Not_Found;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		// empty private constructor
	}
}
