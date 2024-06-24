/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gitlab.management;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.gitlab.Messages;
import org.eclipse.fordiac.ide.gitlab.Package;
import org.eclipse.fordiac.ide.gitlab.Project;
import org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class GitLabDownloadManager {

	private static final String PACKAGE_TYPE = "packageType"; //$NON-NLS-1$
	private static final String PACKAGE_VERSION = "packageVersion"; //$NON-NLS-1$
	private static final String PACKAGE_NAME = "packageName"; //$NON-NLS-1$
	private static final String PACKAGE_ID = "packageID"; //$NON-NLS-1$
	private static final String PROJECT_NAME = "projectName"; //$NON-NLS-1$
	private static final String PROJECT_ID = "projectID"; //$NON-NLS-1$
	private static final String PATH = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString();
	private static final String ROOT_DIRECTORY = ".download"; //$NON-NLS-1$
	private static final String API_VERSION = "api/v4/projects"; //$NON-NLS-1$
	private static final String PACKAGES = "/packages/"; //$NON-NLS-1$
	private static final String PACKAGE_FILES = "/package_files"; //$NON-NLS-1$
	private HashMap<Project, List<Package>> projectAndPackageMap;
	private HashMap<String, List<LeafNode>> packagesAndLeaves;
	private static final String URL_PARAMETERS = "?per_page=20"; //$NON-NLS-1$
	private static final String CUSTOM_PARAMETERS = "&membership=true"; //$NON-NLS-1$
	private static final String URL_PAGE_PARAMETER = "&page="; //$NON-NLS-1$
	private static final String NEXT_PAGE_HEADER = "X-Next-Page"; //$NON-NLS-1$

	private final String url;
	private final String token;

	public GitLabDownloadManager(final String url, final String token) {
		if (url.endsWith("/")) { //$NON-NLS-1$
			this.url = url;
		} else {
			this.url = url + "/"; //$NON-NLS-1$
		}
		this.token = token;
	}

	public Map<String, List<LeafNode>> getPackagesAndLeaves() {
		return packagesAndLeaves;
	}

	public Map<Project, List<Package>> getProjectsAndPackages() {
		return projectAndPackageMap;
	}

	public void fetchProjectsAndPackages() {
		projectAndPackageMap = new HashMap<>();
		packagesAndLeaves = new HashMap<>();
		try {
			getProjects();
			for (final Project project : projectAndPackageMap.keySet()) {
				getPackages(project);
			}
		} catch (final IOException e) {
			FordiacLogHelper.logError("Problem with GitLab import", e); //$NON-NLS-1$
		}
	}

	private static void createRootDir() throws IOException {
		final Path path = Paths.get(PATH, ROOT_DIRECTORY);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	private static void createPackageDir(final Package p) throws IOException {
		final Path path = Paths.get(PATH, ROOT_DIRECTORY, p.name() + "-" + p.version()); //$NON-NLS-1$
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	private HttpURLConnection createConnection(final String target) throws IOException {
		final URL url = new URL(target);
		final HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod(Messages.GET);
		httpConn.setRequestProperty(Messages.Private_Token, token);
		return httpConn;
	}

	public File packageDownloader(final Project project, final Package p) throws IOException {
		for (final String filename : findFilenamesInPackage(p, project)) {
			final HttpURLConnection httpConn = createConnection(buildDownloadURL(p, project, filename));
			try (InputStream responseStream = httpConn.getInputStream()) { // closed automatically
				createRootDir();
				createPackageDir(p);
				Files.copy(responseStream, Paths.get(PATH, ROOT_DIRECTORY, p.name() + "-" + p.version(), filename), //$NON-NLS-1$
						StandardCopyOption.REPLACE_EXISTING);
				httpConn.disconnect();
				return new File(Paths.get(PATH, ROOT_DIRECTORY, p.name() + "-" + p.version(), filename).toString()); //$NON-NLS-1$
			}
		}
		return null;
	}

	private List<String> findFilenamesInPackage(final Package pack, final Project project) throws IOException {
		final HttpURLConnection httpConn = createConnection(buildPackageFileURL(pack, project));
		try (InputStream responseStream = httpConn.getInputStream()) {
			final List<String> filenames = parseResponse(responseStream);
			httpConn.disconnect();
			return filenames;
		}
	}

	private String buildDownloadURL(final Package p, final Object project, final String filename) {
		return url + API_VERSION + "/" + ((Project) project).id() + PACKAGES + p.packageType() + "/" + p.name() + "/" //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
				+ p.version() + "/" + filename; //$NON-NLS-1$
	}

	private String buildPackageFileURL(final Package p, final Project project) {
		return url + API_VERSION + "/" + project.id() + PACKAGES + p.id() + PACKAGE_FILES; //$NON-NLS-1$
	}

	private String buildConnectionURL(final String page) {
		return url + API_VERSION + URL_PARAMETERS + CUSTOM_PARAMETERS + URL_PAGE_PARAMETER + page;
	}

	private String buildPackagesForProjectURL(final Project project, final String page) {
		return url + API_VERSION + "/" + project.id() + PACKAGES + URL_PARAMETERS + CUSTOM_PARAMETERS //$NON-NLS-1$
				+ URL_PAGE_PARAMETER + page;
	}

	private void getProjects() throws IOException {
		String page = "1"; //$NON-NLS-1$
		final String regex = "\\{\\\"id\\\"\\s*:\\s*(?<projectID>\\d+)\\s*,\\s*\\\"description\\\".*?,\\s*\\\"name\\\"\\s*:\\s*\\\"(?<projectName>[\\w\\s\\-\\.]+)\\\"\\s*,\\s*\\\"name_with_namespace\\\"[^\\}]*+\\}"; //$NON-NLS-1$
		final Pattern p = Pattern.compile(regex);

		while (page != null && !"".equals(page)) { //$NON-NLS-1$
			final HttpURLConnection httpConn = createConnection(buildConnectionURL(page));
			try (InputStream responseStream = httpConn.getInputStream()) {
				String response = ""; //$NON-NLS-1$
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream))) {
					response = reader.readLine();
				}
				page = httpConn.getHeaderField(NEXT_PAGE_HEADER);
				final Matcher m = p.matcher(response);
				Project project = null;
				while (m.find()) {
					project = new Project(Long.valueOf(m.group(PROJECT_ID)), m.group(PROJECT_NAME));
					projectAndPackageMap.put(project, new ArrayList<>());
				}
			}
		}

	}

	private void getPackages(final Project project) throws IOException {
		String page = "1"; //$NON-NLS-1$
		final String regex = "(?<packageID>\\d+),\\\"name\\\":\\\"(?<packageName>[\\w\\s\\-\\.]*)\\\",\\\"version\\\":\\\"(?<packageVersion>[\\w\\s\\-\\.]*)\\\",\\\"package_type\\\":\\\"(?<packageType>[\\w\\s\\-\\.]*)"; //$NON-NLS-1$
		final Pattern p = Pattern.compile(regex);
		while (page != null && !"".equals(page)) { //$NON-NLS-1$
			final HttpURLConnection httpConn = createConnection(buildPackagesForProjectURL(project, page));
			try (InputStream responseStream = httpConn.getInputStream()) {
				String response = ""; //$NON-NLS-1$
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream))) {
					response = reader.readLine();
				}
				page = httpConn.getHeaderField(NEXT_PAGE_HEADER);
				final Matcher m = p.matcher(response);
				Package pack;
				while (m.find()) {
					pack = new Package(Long.valueOf(m.group(PACKAGE_ID)), m.group(PACKAGE_NAME),
							m.group(PACKAGE_VERSION), m.group(PACKAGE_TYPE));
					projectAndPackageMap.get(project).add(pack);
					if (!packagesAndLeaves.containsKey(pack.name())) {
						packagesAndLeaves.put(pack.name(), new ArrayList<>());
					}
					packagesAndLeaves.get(pack.name()).add(new LeafNode(project, pack, pack.version()));
				}
			} catch (final IOException e) {
				httpConn.disconnect();
				return;
			}
			httpConn.disconnect();
		}
	}

	private static List<String> parseResponse(final InputStream responseStream) throws IOException {
		final List<String> filenames = new ArrayList<>();
		String response = ""; //$NON-NLS-1$
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream))) {
			response = reader.readLine();
		}

		final String regex = "\"file_name\":\"([^\"]*)\""; //$NON-NLS-1$
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(response);
		while (m.find()) {
			filenames.add(m.group(1));
		}
		return filenames;
	}
}
