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

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.eclipse.core.runtime.IStatus;

public class GitIssueCreator {

	private static record IssueInfo(String title, String body, String[] labels) {
	}

	@SuppressWarnings("nls")
	private final static String[] LABELS = new String[] { "bug", "autoreport" };
	private final static String SESSION_ID = UUID.randomUUID().toString();

	public static int createIssue(final IStatus status) {
		final IssueInfo info = new IssueInfo(status.getMessage(), buildBody(status), LABELS);

		final PreferenceConstants.ReportDestination repDest = PreferenceConstants.getReportDestination();
		if (repDest == PreferenceConstants.ReportDestination.GITLAB) {
			return createGitLabIssue(info);
		}
		if (repDest == PreferenceConstants.ReportDestination.GITHUB) {
			return createGitHubIssue(info);
		}
		return -1;
	}

	private static String buildBody(final IStatus status) {
		final StringBuilder sb = new StringBuilder();
		sb.append("**4diac IDE version:** "); //$NON-NLS-1$

		sb.append(System.getProperty("org.eclipse.fordiac.ide.version")); //$NON-NLS-1$
		sb.append('.');
		sb.append(System.getProperty("org.eclipse.fordiac.ide.buildid")); //$NON-NLS-1$
		sb.append(System.lineSeparator()).append(System.lineSeparator());

		sb.append("**Session ID:** "); //$NON-NLS-1$
		sb.append(SESSION_ID);
		sb.append(System.lineSeparator()).append(System.lineSeparator());

		sb.append("### Stack trace"); //$NON-NLS-1$
		sb.append(System.lineSeparator()).append(System.lineSeparator());
		sb.append("```"); //$NON-NLS-1$
		sb.append(System.lineSeparator());
		sb.append(getStackTrace(status.getException()));
		sb.append("```"); //$NON-NLS-1$

		return sb.toString();
	}

	private static String getStackTrace(final Throwable exception) {
		final StringWriter writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

	private static int createGitLabIssue(final IssueInfo info) {
		if (info.body().length() > 1048575) { // ~max GitLab description length
			return -1;
		}

		final String baseURI = removeLeadingTrailingSlashes(PreferenceConstants.getReportGitLabURL());
		final String projectPath = removeLeadingTrailingSlashes(PreferenceConstants.getReportGitLabProjectPath());
		final String accessToken = PreferenceConstants.getReportGitLabToken();
		final String labels = String.join(",", info.labels()); //$NON-NLS-1$

		final String uri = baseURI + "/api/v4/projects/%s/issues?title=%s&description=%s&labels=%s"; //$NON-NLS-1$

		final String reportingURI = uri.formatted(URLEncoder.encode(projectPath, StandardCharsets.UTF_8),
				URLEncoder.encode(info.title(), StandardCharsets.UTF_8),
				URLEncoder.encode(info.body(), StandardCharsets.UTF_8),
				URLEncoder.encode(labels, StandardCharsets.UTF_8));

		final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(reportingURI))
				.header("PRIVATE-TOKEN", accessToken) //$NON-NLS-1$
				.POST(HttpRequest.BodyPublishers.noBody()).build();
		return makeRequest(request);
	}

	@SuppressWarnings("nls")
	private static int createGitHubIssue(final IssueInfo info) {
		final String baseURI = removeLeadingTrailingSlashes(PreferenceConstants.getReportGitHubURL());
		final String projectPath = removeLeadingTrailingSlashes(PreferenceConstants.getReportGitHubProjectPath());
		final String token = PreferenceConstants.getReportGitHubToken();

		final String jsonBody = """
				{"title":%s,"body":%s,"labels":%s}
				""".formatted(jsonQuote(info.title()), jsonQuote(info.body()), toJsonArray(info.labels()));

		final HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(baseURI + "/repos/" + projectPath + "/issues"))
				.header("Accept", "application/vnd.github+json").header("Authorization", "Bearer " + token)
				.header("X-GitHub-Api-Version", "2022-11-28").POST(HttpRequest.BodyPublishers.ofString(jsonBody))
				.build();
		return makeRequest(request);
	}

	private static int makeRequest(final HttpRequest request) {
		try {
			final HttpResponse<String> response = HttpClient.newHttpClient().send(request,
					HttpResponse.BodyHandlers.ofString());
			return response.statusCode();
		} catch (IOException | InterruptedException e) {
			return -1;
		}
	}

	private static String removeLeadingTrailingSlashes(final String s) {
		return s.replaceAll("^/+|/+$", ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static String toJsonArray(final String[] array) {
		final StringBuilder sb = new StringBuilder();
		sb.append('[');

		String prefix = ""; //$NON-NLS-1$
		for (final String elem : array) {
			sb.append(prefix);
			prefix = ","; //$NON-NLS-1$
			sb.append(jsonQuote(elem));
		}

		sb.append(']');
		return sb.toString();
	}

	@SuppressWarnings("boxing")
	private static String jsonQuote(final String input) {
		final StringBuilder sb = new StringBuilder("\""); //$NON-NLS-1$
		for (int i = 0; i < input.length(); i++) {
			final char ch = input.charAt(i);
			switch (ch) {
			case '"' -> sb.append("\\\""); //$NON-NLS-1$
			case '\\' -> sb.append("\\\\"); //$NON-NLS-1$
			case '\b' -> sb.append("\\b"); //$NON-NLS-1$
			case '\f' -> sb.append("\\f"); //$NON-NLS-1$
			case '\n' -> sb.append("\\n"); //$NON-NLS-1$
			case '\r' -> sb.append("\\r"); //$NON-NLS-1$
			case '\t' -> sb.append("\\t"); //$NON-NLS-1$
			default -> {
				if (ch < ' ' || ch == 0x7F) { // control chars
					sb.append(String.format("\\u%04x", (int) ch)); //$NON-NLS-1$
				} else {
					sb.append(ch);
				}
			}
			}
		}
		sb.append("\""); //$NON-NLS-1$
		return sb.toString();
	}
}
