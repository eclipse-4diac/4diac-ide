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
 *   Michael Oberlehner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.fordiac.ide.gitlab.preferences.GitLabEndpoint;
import org.eclipse.fordiac.ide.gitlab.preferences.GitLabEndpointsStore;

public class SetGitLabEndpoints extends Task {

	private final List<Endpoint> endpoints = new ArrayList<>();
	private final Endpoint directEndpoint = new Endpoint();
	private boolean append;

	public void setName(final String name) {
		directEndpoint.setName(name);
	}

	public void setUrl(final String url) {
		directEndpoint.setUrl(url);
	}

	public void setToken(final String token) {
		directEndpoint.setToken(token);
	}

	public void setAppend(final boolean append) {
		this.append = append;
	}

	public Endpoint createEndpoint() {
		final Endpoint endpoint = new Endpoint();
		endpoints.add(endpoint);
		return endpoint;
	}

	@Override
	public void execute() throws BuildException {
		final List<GitLabEndpoint> configuredEndpoints = validateUniqueNames(collectConfiguredEndpoints());
		if (configuredEndpoints.isEmpty()) {
			throw new BuildException("No GitLab endpoints configured"); //$NON-NLS-1$
		}

		final List<GitLabEndpoint> endpointsToSave = append ? mergeWithStoredEndpoints(configuredEndpoints)
				: configuredEndpoints;
		GitLabEndpointsStore.saveEndpoints(endpointsToSave);
		verifyStoredEndpoints(configuredEndpoints);
		log(MessageFormat.format("Configured {0} GitLab endpoint(s)", //$NON-NLS-1$
				Integer.valueOf(configuredEndpoints.size())));
	}

	private List<GitLabEndpoint> collectConfiguredEndpoints() {
		final List<GitLabEndpoint> configuredEndpoints = new ArrayList<>();
		if (directEndpoint.hasValues()) {
			configuredEndpoints.add(directEndpoint.toGitLabEndpoint("GitLab endpoint")); //$NON-NLS-1$
		}
		for (final Endpoint endpoint : endpoints) {
			configuredEndpoints.add(endpoint.toGitLabEndpoint("GitLab endpoint")); //$NON-NLS-1$
		}
		return configuredEndpoints;
	}

	private static List<GitLabEndpoint> validateUniqueNames(final List<GitLabEndpoint> endpoints) {
		final Map<String, GitLabEndpoint> uniqueEndpoints = new LinkedHashMap<>();
		for (final GitLabEndpoint endpoint : endpoints) {
			if (uniqueEndpoints.put(endpoint.name(), endpoint) != null) {
				throw new BuildException(MessageFormat.format("Duplicate GitLab endpoint name: {0}", endpoint.name())); //$NON-NLS-1$
			}
		}
		return List.copyOf(uniqueEndpoints.values());
	}

	private static List<GitLabEndpoint> mergeWithStoredEndpoints(final List<GitLabEndpoint> configuredEndpoints) {
		final Map<String, GitLabEndpoint> mergedEndpoints = new LinkedHashMap<>();
		GitLabEndpointsStore.loadEndpoints().forEach(endpoint -> mergedEndpoints.put(endpoint.name(), endpoint));
		configuredEndpoints.forEach(endpoint -> mergedEndpoints.put(endpoint.name(), endpoint));
		return List.copyOf(mergedEndpoints.values());
	}

	private static void verifyStoredEndpoints(final List<GitLabEndpoint> configuredEndpoints) {
		final Map<String, GitLabEndpoint> storedEndpoints = new LinkedHashMap<>();
		GitLabEndpointsStore.loadEndpoints().forEach(endpoint -> storedEndpoints.put(endpoint.name(), endpoint));
		for (final GitLabEndpoint endpoint : configuredEndpoints) {
			if (!endpoint.equals(storedEndpoints.get(endpoint.name()))) {
				throw new BuildException(
						MessageFormat.format("GitLab endpoint could not be stored: {0}", endpoint.name())); //$NON-NLS-1$
			}
		}
	}

	public static final class Endpoint {

		private String name;
		private String url;
		private String token;

		public void setName(final String name) {
			this.name = name;
		}

		public void setUrl(final String url) {
			this.url = url;
		}

		public void setToken(final String token) {
			this.token = token;
		}

		private boolean hasValues() {
			return !Objects.toString(name, "").isBlank() || !Objects.toString(url, "").isBlank() //$NON-NLS-1$ //$NON-NLS-2$
					|| !Objects.toString(token, "").isBlank(); //$NON-NLS-1$
		}

		private GitLabEndpoint toGitLabEndpoint(final String description) {
			final GitLabEndpoint endpoint = new GitLabEndpoint(name, url, token);
			if (!GitLabEndpoint.isValidName(endpoint.name())) {
				throw new BuildException(
						MessageFormat.format("{0} name is invalid: {1}", description, endpoint.name())); //$NON-NLS-1$
			}
			if (endpoint.url().isBlank()) {
				throw new BuildException(MessageFormat.format("{0} URL is missing", description)); //$NON-NLS-1$
			}
			if (endpoint.token().isBlank()) {
				throw new BuildException(MessageFormat.format("{0} token is missing", description)); //$NON-NLS-1$
			}
			return endpoint;
		}
	}
}
