/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid, Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid - initial API and implementation and/or initial documentation
 *   Martin Erich Jobst - add preference qualifier parameter
 *******************************************************************************/
package org.eclipse.fordiac.ide.issuereport;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

public final class PreferenceConstants {

	public enum ReportMode {
		NO_REPORT, PROMPT_REPORT, AUTO_REPORT
	}

	public enum ReportDestination {
		GITLAB, GITHUB, GITHUB_MANUAL
	}

	private PreferenceConstants() {
		// Shall not be instantiated
	}

	public static final String P_BUG_REPORT_PREFERENCE_ID = "org.eclipse.fordiac.ide.issuereport"; //$NON-NLS-1$

	public static final String P_BUG_REPORT_MODE = "bugReportingMode"; //$NON-NLS-1$

	public static final String P_BUG_REPORT_DESTINATION = "bugReportingDestination"; //$NON-NLS-1$

	public static final String P_BUG_REPORT_GITLAB_URL = "bugReportingGitLabURL"; //$NON-NLS-1$

	public static final String P_BUG_REPORT_GITLAB_PROJECT_PATH = "bugReportingGitLabProjectPath"; //$NON-NLS-1$

	public static final String P_BUG_REPORT_GITLAB_TOKEN = "bugReportingGitLabToken"; //$NON-NLS-1$

	public static final String P_BUG_REPORT_GITHUB_URL = "bugReportingGitHubURL"; //$NON-NLS-1$

	public static final String P_BUG_REPORT_GITHUB_PROJECT_PATH = "bugReportingGitHubProjectPath"; //$NON-NLS-1$

	public static final String P_BUG_REPORT_GITHUB_TOKEN = "bugReportingGitHubToken"; //$NON-NLS-1$

	public static ReportMode getReportMode() {
		return getReportMode(P_BUG_REPORT_PREFERENCE_ID);
	}

	public static ReportMode getReportMode(final String qualifier) {
		try {
			return ReportMode.valueOf(getPrefString(qualifier, P_BUG_REPORT_MODE));
		} catch (final Exception e) {
			return ReportMode.PROMPT_REPORT;
		}
	}

	public static ReportDestination getReportDestination() {
		return getReportDestination(P_BUG_REPORT_PREFERENCE_ID);
	}

	public static ReportDestination getReportDestination(final String qualifier) {
		try {
			return ReportDestination.valueOf(getPrefString(qualifier, P_BUG_REPORT_DESTINATION));
		} catch (final Exception e) {
			return ReportDestination.GITHUB_MANUAL;
		}
	}

	public static String getReportGitLabURL() {
		return getReportGitLabURL(P_BUG_REPORT_PREFERENCE_ID);
	}

	public static String getReportGitLabURL(final String qualifier) {
		return getPrefString(qualifier, P_BUG_REPORT_GITLAB_URL);
	}

	public static String getReportGitLabProjectPath() {
		return getReportGitLabProjectPath(P_BUG_REPORT_PREFERENCE_ID);
	}

	public static String getReportGitLabProjectPath(final String qualifier) {
		return getPrefString(qualifier, P_BUG_REPORT_GITLAB_PROJECT_PATH);
	}

	public static String getReportGitLabToken() {
		return getReportGitLabToken(P_BUG_REPORT_PREFERENCE_ID);
	}

	public static String getReportGitLabToken(final String qualifier) {
		return getPrefString(qualifier, P_BUG_REPORT_GITLAB_TOKEN);
	}

	public static String getReportGitHubURL() {
		return getReportGitHubURL(P_BUG_REPORT_PREFERENCE_ID);
	}

	public static String getReportGitHubURL(final String qualifier) {
		return getPrefString(qualifier, P_BUG_REPORT_GITHUB_URL, "https://api.github.com/"); //$NON-NLS-1$
	}

	public static String getReportGitHubProjectPath() {
		return getReportGitHubProjectPath(P_BUG_REPORT_PREFERENCE_ID);
	}

	public static String getReportGitHubProjectPath(final String qualifier) {
		return getPrefString(qualifier, P_BUG_REPORT_GITHUB_PROJECT_PATH);
	}

	public static String getReportGitHubToken() {
		return getReportGitHubToken(P_BUG_REPORT_PREFERENCE_ID);
	}

	public static String getReportGitHubToken(final String qualifier) {
		return getPrefString(qualifier, P_BUG_REPORT_GITHUB_TOKEN);
	}

	private static String getPrefString(final String qualifier, final String key) {
		return getPrefString(qualifier, key, ""); //$NON-NLS-1$
	}

	private static String getPrefString(final String qualifier, final String key, final String defaultValue) {
		return Platform.getPreferencesService().getString(qualifier, key, defaultValue,
				new IScopeContext[] { InstanceScope.INSTANCE, DefaultScope.INSTANCE });
	}
}
