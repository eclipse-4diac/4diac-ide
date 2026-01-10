/*******************************************************************************
 * Copyright (c) 2025, 2026 Johannes Kepler University Linz
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.fordiac.ide.Activator;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;

import com.google.gson.Gson;

public class GitIssueCreator {

	private static final String FORDIAC_IDE_ISSUE_URL = "https://github.com/eclipse-4diac/4diac-ide/issues/new?title=%s&body=%s"; //$NON-NLS-1$

	private static record IssueInfo(String title, String body, String[] labels) {
	}

	private static record GitHubResponse(String html_url) {
	}

	private static record GitLabResponse(String web_url) {
	}

	@SuppressWarnings("nls")
	private static final String[] LABELS = new String[] { "bug", "autoreport" };
	private static final String SESSION_ID = UUID.randomUUID().toString();
	private static final String CODE_DELIMITER = "```"; //$NON-NLS-1$
	private static final int MAX_MANUAL_ISSUE_BODY_SIZE = 4000;

	public static Optional<String> createIssue(final IStatus status) {
		final IssueInfo info = new IssueInfo(status.getMessage(), buildBody(status), LABELS);
		return switch (PreferenceConstants.getReportDestination()) {
		case GITLAB -> createGitLabIssue(info);
		case GITHUB -> createGitHubIssue(info);
		case GITHUB_MANUAL -> createGitHubIssueManual(info);
		};
	}

	public static boolean openLinkInBrowser(final String url) {
		try {
			final IWorkbench wb = PlatformUI.getWorkbench();
			final IWebBrowser browser = wb.getBrowserSupport().createBrowser(Activator.PLUGIN_ID);
			browser.openURL(new URI(url).toURL());
			return true;
		} catch (final Exception e) {
			return false;
		}
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
		sb.append(CODE_DELIMITER);
		sb.append(System.lineSeparator());
		sb.append(getStackTrace(status.getException()));
		sb.append(CODE_DELIMITER);

		return sb.toString();
	}

	private static String getStackTrace(final Throwable exception) {
		final StringWriter writer = new StringWriter();
		exception.printStackTrace(new PrintWriter(writer));
		return writer.toString();
	}

	private static Optional<String> createGitHubIssueManual(final IssueInfo info) {
		String body = info.body();
		if (body.length() > MAX_MANUAL_ISSUE_BODY_SIZE) {
			body = body.substring(0, MAX_MANUAL_ISSUE_BODY_SIZE) + CODE_DELIMITER;
		}

		try {
			final String reportingURI = FORDIAC_IDE_ISSUE_URL.formatted(
					URLEncoder.encode(info.title(), StandardCharsets.UTF_8), // title
					URLEncoder.encode(body, StandardCharsets.UTF_8)); // body
			openLinkInBrowser(reportingURI);
		} catch (final Exception e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		}
		return Optional.empty(); // no issue created yet...
	}

	private static Optional<String> createGitLabIssue(final IssueInfo info) {
		if (info.body().length() > 1048575) { // ~max GitLab description length
			return Optional.empty();
		}

		final String baseURI = removeLeadingTrailingSlashes(PreferenceConstants.getReportGitLabURL());
		final String projectPath = removeLeadingTrailingSlashes(PreferenceConstants.getReportGitLabProjectPath());
		final String accessToken = PreferenceConstants.getReportGitLabToken();
		final String labels = String.join(",", info.labels()); //$NON-NLS-1$

		final String uri = "%s/api/v4/projects/%s/issues?title=%s&description=%s&labels=%s"; //$NON-NLS-1$
		final String reportingURI = uri.formatted(baseURI, URLEncoder.encode(projectPath, StandardCharsets.UTF_8),
				URLEncoder.encode(info.title(), StandardCharsets.UTF_8),
				URLEncoder.encode(info.body(), StandardCharsets.UTF_8),
				URLEncoder.encode(labels, StandardCharsets.UTF_8));
		final Gson gson = new Gson();

		try {
			final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(reportingURI))
					.header("PRIVATE-TOKEN", accessToken) //$NON-NLS-1$
					.POST(HttpRequest.BodyPublishers.noBody()).build();
			final Optional<String> body = makeRequest(request);
			return Optional.ofNullable(gson.fromJson(body.get(), GitLabResponse.class).web_url());
		} catch (final IllegalArgumentException | NoSuchElementException e) {
			return Optional.empty();
		}
	}

	@SuppressWarnings("nls")
	private static Optional<String> createGitHubIssue(final IssueInfo info) {
		final String baseURI = removeLeadingTrailingSlashes(PreferenceConstants.getReportGitHubURL());
		final String projectPath = removeLeadingTrailingSlashes(PreferenceConstants.getReportGitHubProjectPath());
		final String token = PreferenceConstants.getReportGitHubToken();

		final Gson gson = new Gson();
		final String jsonBody = gson.toJson(info);

		try {
			final HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("%s/repos/%s/issues".formatted(baseURI, projectPath)))
					.header("Accept", "application/vnd.github+json").header("Authorization", "Bearer " + token)
					.header("X-GitHub-Api-Version", "2022-11-28").POST(HttpRequest.BodyPublishers.ofString(jsonBody))
					.build();
			final Optional<String> body = makeRequest(request);
			return Optional.ofNullable(gson.fromJson(body.get(), GitHubResponse.class).html_url());
		} catch (final IllegalArgumentException | NoSuchElementException e) {
			return Optional.empty();
		}
	}

	private static Optional<String> makeRequest(final HttpRequest request) {
		try (HttpClient client = HttpClient.newHttpClient()) {
			final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			if (response.statusCode() == 201) { // 201 - Created
				return Optional.of(response.body());
			}
		} catch (final IOException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
		} catch (final InterruptedException e) {
			FordiacLogHelper.logWarning(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
		return Optional.empty();
	}

	private static String removeLeadingTrailingSlashes(final String s) {
		return s.replaceAll("^/+|/+$", ""); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
