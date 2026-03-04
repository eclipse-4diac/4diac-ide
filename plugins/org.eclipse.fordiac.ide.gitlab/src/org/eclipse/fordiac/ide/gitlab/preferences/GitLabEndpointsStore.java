/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab.preferences;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Loads and saves configured GitLab endpoints.
 *
 * Plain preferences store ONLY:
 *
 * <pre>
 *   name|url\n
 *   name2|url2
 * </pre>
 *
 * Tokens are stored encrypted in Eclipse Secure Storage (Equinox Secure
 * Preferences).
 */
public final class GitLabEndpointsStore {
	private GitLabEndpointsStore() {
	}

	private static final String SECURE_ROOT_NODE = PreferenceConstants.P_GITLAB_PREFERENCE_ID;
	private static final String SECURE_ENDPOINTS_NODE = "endpoints"; //$NON-NLS-1$
	private static final String SECURE_TOKEN_KEY = "token"; //$NON-NLS-1$

	public static List<GitLabEndpoint> loadEndpoints() {
		final IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PreferenceConstants.P_GITLAB_PREFERENCE_ID);
		final String encoded = prefs.get(PreferenceConstants.P_GITLAB_ENDPOINTS, ""); //$NON-NLS-1$

		// Only migrate the legacy single endpoint (gitLabURL/gitLabToken)
		if (encoded == null || encoded.isBlank()) {
			return migrateOldEndpoints(prefs);
		}

		final List<GitLabEndpoint> result = new ArrayList<>();
		for (final String line : encoded.split("\\n")) { //$NON-NLS-1$
			if (line == null || line.isBlank()) {
				continue;
			}
			final List<String> parts = splitEscaped(line, '|');
			if (parts.size() < 2) {
				continue;
			}

			final String name = unescape(parts.get(0));
			final String url = unescape(parts.get(1));
			final String token = getTokenSecure(name);

			final GitLabEndpoint ep = new GitLabEndpoint(name, url, token);
			if (!name.isBlank() && !url.isBlank()) {
				result.add(ep);
			}
		}
		return result;
	}

	/**
	 * Saves endpoints: stores "name|url" in plain preferences and token encrypted
	 * in secure storage.
	 */
	public static void saveEndpoints(final List<GitLabEndpoint> endpoints) {
		final IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(PreferenceConstants.P_GITLAB_PREFERENCE_ID);

		// store tokens securely first (best-effort)
		if (endpoints != null) {
			for (final GitLabEndpoint ep : endpoints) {
				if (ep == null) {
					continue;
				}
				final String name = Objects.toString(ep.name(), "").trim(); //$NON-NLS-1$
				final String token = Objects.toString(ep.token(), "").trim(); //$NON-NLS-1$
				if (!name.isEmpty() && !token.isEmpty()) {
					putTokenSecure(name, token);
				}
			}
		}

		prefs.put(PreferenceConstants.P_GITLAB_ENDPOINTS, encodeNameUrlOnly(endpoints));
		try {
			prefs.flush();
		} catch (final BackingStoreException e) {
			FordiacLogHelper.logWarning("Secure Token failed", e); //$NON-NLS-1$
		}
	}

	public static void removeToken(final String endpointName) {
		if (endpointName == null || endpointName.isBlank()) {
			return;
		}
		try {
			final ISecurePreferences root = SecurePreferencesFactory.getDefault();
			final ISecurePreferences node = root.node(SECURE_ROOT_NODE).node(SECURE_ENDPOINTS_NODE)
					.node(endpointNodeId(endpointName));
			node.remove(SECURE_TOKEN_KEY);
			root.flush();
		} catch (final Exception e) {
			FordiacLogHelper.logWarning("Reomving Secure Token failed", e); //$NON-NLS-1$
		}
	}

	/**
	 * LEGACY migration: migrate old single URL/token preferences into: - one
	 * endpoint entry in plain prefs ("Default|<url>") - token stored in secure
	 * storage
	 */
	protected static List<GitLabEndpoint> migrateOldEndpoints(final IEclipsePreferences prefs) {
		final String legacyUrl = prefs.get(PreferenceConstants.P_GITLAB_URL, ""); //$NON-NLS-1$
		final String legacyToken = prefs.get(PreferenceConstants.P_GITLAB_TOKEN, ""); //$NON-NLS-1$
		if (legacyUrl != null && !legacyUrl.isBlank() && legacyToken != null && !legacyToken.isBlank()) {
			final GitLabEndpoint migrated = new GitLabEndpoint("Default", legacyUrl, legacyToken); //$NON-NLS-1$
			saveEndpoints(List.of(migrated));
			return List.of(new GitLabEndpoint("Default", legacyUrl, getTokenSecure("Default"))); //$NON-NLS-1$
		}
		return Collections.emptyList();
	}

	private static void putTokenSecure(final String endpointName, final String token) {
		try {
			final ISecurePreferences root = SecurePreferencesFactory.getDefault();
			final ISecurePreferences node = root.node(SECURE_ROOT_NODE).node(SECURE_ENDPOINTS_NODE)
					.node(endpointNodeId(endpointName));
			node.put(SECURE_TOKEN_KEY, token, true);
			root.flush();
		} catch (final StorageException | IOException | RuntimeException e) {
			FordiacLogHelper.logWarning("Secure Token failed", e); //$NON-NLS-1$
		}
	}

	private static String getTokenSecure(final String endpointName) {
		try {
			final ISecurePreferences root = SecurePreferencesFactory.getDefault();
			final ISecurePreferences node = root.node(SECURE_ROOT_NODE).node(SECURE_ENDPOINTS_NODE)
					.node(endpointNodeId(endpointName));
			return node.get(SECURE_TOKEN_KEY, "");
		} catch (final Exception e) {
			return "";
		}
	}

	/**
	 * Secure storage node names are path components. Encode name to avoid '/'
	 * issues.
	 */
	private static String endpointNodeId(final String endpointName) {
		final byte[] bytes = Objects.toString(endpointName, "").getBytes(StandardCharsets.UTF_8); //$NON-NLS-1$
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	private static String encodeNameUrlOnly(final List<GitLabEndpoint> endpoints) {
		if (endpoints == null || endpoints.isEmpty()) {
			return ""; //$NON-NLS-1$
		}
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (final GitLabEndpoint ep : endpoints) {
			if (ep == null) {
				continue;
			}
			final String name = Objects.toString(ep.name(), "").trim(); //$NON-NLS-1$
			final String url = Objects.toString(ep.url(), "").trim(); //$NON-NLS-1$
			if (name.isEmpty() || url.isEmpty()) {
				continue;
			}
			if (!first) {
				sb.append('\n');
			}
			first = false;
			sb.append(escape(name)).append('|').append(escape(url));
		}
		return sb.toString();
	}

	private static String escape(final String s) {
		final String in = Objects.toString(s, ""); //$NON-NLS-1$
		final StringBuilder out = new StringBuilder(in.length());
		for (int i = 0; i < in.length(); i++) {
			final char c = in.charAt(i);
			switch (c) {
			case '\\' -> out.append("\\\\"); //$NON-NLS-1$
			case '|' -> out.append("\\|"); //$NON-NLS-1$
			case '\n' -> out.append("\\n"); //$NON-NLS-1$
			case '\r' -> out.append("\\r"); //$NON-NLS-1$
			default -> out.append(c);
			}
		}
		return out.toString();
	}

	private static String unescape(final String s) {
		if (s == null || s.isEmpty()) {
			return ""; //$NON-NLS-1$
		}
		final StringBuilder out = new StringBuilder(s.length());
		boolean esc = false;
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			if (!esc) {
				if (c == '\\') {
					esc = true;
				} else {
					out.append(c);
				}
				continue;
			}
			esc = false;
			switch (c) {
			case 'n' -> out.append('\n');
			case 'r' -> out.append('\r');
			case '|' -> out.append('|');
			case '\\' -> out.append('\\');
			default -> out.append(c);
			}
		}
		if (esc) {
			out.append('\\');
		}
		return out.toString();
	}

	private static List<String> splitEscaped(final String line, final char sep) {
		final List<String> parts = new ArrayList<>();
		final StringBuilder cur = new StringBuilder();
		boolean esc = false;
		for (int i = 0; i < line.length(); i++) {
			final char c = line.charAt(i);
			if (!esc) {
				if (c == '\\') {
					esc = true;
					cur.append(c);
					continue;
				}
				if (c == sep) {
					parts.add(cur.toString());
					cur.setLength(0);
					continue;
				}
				cur.append(c);
				continue;
			}
			esc = false;
			cur.append(c);
		}
		parts.add(cur.toString());
		return parts;
	}
}