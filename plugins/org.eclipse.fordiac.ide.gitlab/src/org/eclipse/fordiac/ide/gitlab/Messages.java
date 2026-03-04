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
	public static String GitLab_Token_Title;
	public static String GitLab_Url_Title;
	public static String GitLab_Packages_And_Projects;

	public static String GitLabEndpointDialog_add;

	public static String GitLabEndpointDialog_configure;

	public static String GitLabEndpointDialog_edit;

	public static String GitLabEndpointDialog_name;

	public static String GitLabEndpointDialog_name_exists;

	public static String GitLabEndpointDialog_name_not_empty;

	public static String GitLabEndpointDialog_token;

	public static String GitLabEndpointDialog_token_note_empty;

	public static String GitLabEndpointDialog_url;

	public static String GitLabEndpointDialog_url_not_empty;
	public static String GitLabEndpointsPreferencePage_16;

	public static String GitLabEndpointsPreferencePage_20;

	public static String GitLabEndpointsPreferencePage_add;

	public static String GitLabEndpointsPreferencePage_all_endpoints;

	public static String GitLabEndpointsPreferencePage_configure;

	public static String GitLabEndpointsPreferencePage_connection_failed;

	public static String GitLabEndpointsPreferencePage_connnection_sucessful;

	public static String GitLabEndpointsPreferencePage_edit;

	public static String GitLabEndpointsPreferencePage_enpoints;

	public static String GitLabEndpointsPreferencePage_name;

	public static String GitLabEndpointsPreferencePage_name_exists;

	public static String GitLabEndpointsPreferencePage_name_not_empty;

	public static String GitLabEndpointsPreferencePage_new_name;

	public static String GitLabEndpointsPreferencePage_remove;

	public static String GitLabEndpointsPreferencePage_remove_ep;

	public static String GitLabEndpointsPreferencePage_remove_gl_ep;

	public static String GitLabEndpointsPreferencePage_rename;

	public static String GitLabEndpointsPreferencePage_rename_Ep;

	public static String GitLabEndpointsPreferencePage_test_con;

	public static String GitLabEndpointsPreferencePage_url;

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
