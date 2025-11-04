/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid - initial API and implementation and/or initial documentation
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
		try {
			return ReportMode.valueOf(getPrefString(P_BUG_REPORT_MODE));
		} catch (final Exception e) {
			return ReportMode.NO_REPORT;
		}
	}

	public static ReportDestination getReportDestination() {
		try {
			return ReportDestination.valueOf(getPrefString(P_BUG_REPORT_DESTINATION));
		} catch (final Exception e) {
			return ReportDestination.GITLAB;
		}
	}

	public static String getReportGitLabURL() {
		return getPrefString(P_BUG_REPORT_GITLAB_URL);
	}

	public static String getReportGitLabProjectPath() {
		return getPrefString(P_BUG_REPORT_GITLAB_PROJECT_PATH);
	}

	public static String getReportGitLabToken() {
		return getPrefString(P_BUG_REPORT_GITLAB_TOKEN);
	}

	public static String getReportGitHubURL() {
		return getPrefString(P_BUG_REPORT_GITHUB_URL, "https://api.github.com/"); //$NON-NLS-1$
	}

	public static String getReportGitHubProjectPath() {
		return getPrefString(P_BUG_REPORT_GITHUB_PROJECT_PATH);
	}

	public static String getReportGitHubToken() {
		return getPrefString(P_BUG_REPORT_GITHUB_TOKEN);
	}

	private static String getPrefString(final String key) {
		return getPrefString(key, ""); //$NON-NLS-1$
	}

	private static String getPrefString(final String key, final String defaultValue) {
		return Platform.getPreferencesService().getString(P_BUG_REPORT_PREFERENCE_ID, key, defaultValue,
				new IScopeContext[] { InstanceScope.INSTANCE, DefaultScope.INSTANCE });
	}
}
