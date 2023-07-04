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
import org.eclipse.fordiac.ide.gitlab.wizard.GitLabImportWizardPage;

public class GitLabDownloadManager {

	private static final String PATH = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString();
	private static final String ROOT_DIRECTORY = ".download";
	private static final String API_VERSION = "api/v4/projects/";
	private static final String PACKAGES = "/packages/";
	private static final String PACKAGE_FILES = "/package_files";
	private HashMap<Project, List<Package>> projectAndPackageMap;
	private HashMap<String, List<LeafNode>> packagesAndLeaves;

	private final GitLabImportWizardPage gitLabImportPage;

	public GitLabDownloadManager(final GitLabImportWizardPage gitLabImportPage) {
		this.gitLabImportPage = gitLabImportPage;
	}

	public Map<String, List<LeafNode>> getPackagesAndLeaves() {
		return packagesAndLeaves;
	}

	public Map<Project, List<Package>> getProjectsAndPackages() {
		return projectAndPackageMap;
	}

	public void connectToGitLab() {
		filterData();
	}

	private void filterData() {
		projectAndPackageMap = new HashMap<>();
		packagesAndLeaves = new HashMap<>();
		try {
			final URL url = new URL(buildConnectionURL());

			final HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod(Messages.GET);
			httpConn.setRequestProperty(Messages.Private_Token, gitLabImportPage.getToken());

			try (InputStream responseStream = httpConn.getInputStream()) {
				getProjects(responseStream);
			}
			httpConn.disconnect();
			for (final Project project : projectAndPackageMap.keySet()) {
				getPackages(project);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private static void createRootDir() throws IOException {
		final Path path = Paths.get(PATH, ROOT_DIRECTORY);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	private static void createPackageDir(final Package p) throws IOException {
		final Path path = Paths.get(PATH, ROOT_DIRECTORY, p.getName() + "-" + p.getVersion());
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}

	public void packageDownloader(final Project project, final Package p) throws IOException {
		for (final String filename : findFilenamesInPackage(p, project)) {
			final URL url = new URL(buildDownloadURL(p, project, filename));

			final HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod(Messages.GET);
			httpConn.setRequestProperty(Messages.Private_Token, gitLabImportPage.getToken());

			try (InputStream responseStream = httpConn.getInputStream()) {
				createRootDir();
				createPackageDir(p);
				Files.copy(responseStream,
						Paths.get(PATH, ROOT_DIRECTORY, p.getName() + "-" + p.getVersion(), filename),
						StandardCopyOption.REPLACE_EXISTING);
			}

			httpConn.disconnect();
		}
	}

	private List<String> findFilenamesInPackage(final Package pack, final Project project) throws IOException {
		final URL url = new URL(buildPackageFileURL(pack, project));

		final HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod(Messages.GET);
		httpConn.setRequestProperty(Messages.Private_Token, gitLabImportPage.getToken());

		try (InputStream responseStream = httpConn.getInputStream()) {
			final List<String> filenames = parseResponse(responseStream);

			httpConn.disconnect();
			return filenames;
		}
	}

	private String buildDownloadURL(final Package p, final Object project, final String filename) {
		return gitLabImportPage.getUrl() + API_VERSION + ((Project) project).getId() + PACKAGES + p.getPackageType()
				+ "/" + p.getName() + "/" + p.getVersion() + "/" + filename;
	}

	private String buildPackageFileURL(final Package p, final Project project) {
		return gitLabImportPage.getUrl() + API_VERSION + project.getId() + PACKAGES + p.getId() + PACKAGE_FILES;
	}

	private String buildConnectionURL() {
		return gitLabImportPage.getUrl() + API_VERSION;
	}

	private String buildPackagesForProjectURL(final Project project) {
		return gitLabImportPage.getUrl() + API_VERSION + project.getId() + PACKAGES;
	}

	private void getProjects(final InputStream responseStream) throws IOException {
		String response = "";
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream))) {
			response = reader.readLine();
		}
		final String regex = "(?<projectID>[\\w\\s-]*),(?:\\\"|\\')?(?:description)(?:\\\"|\\')?(?:\\:\\s*)(?:[\\w\\s-]*|\\\"\\\"|\\\"[\\w\\s-]*\\\"),(?:\\\"|\\')?(?:name)(?:\\\"|\\')(?:\\:\\s*)(?:\\\"|\\')?(?<projectName>[\\w\\s-]*)(?:\\\"|\\')?(?:,\\\"name_with_namespace\\\")";
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(response);
		Project project = null;
		while (m.find()) {
			project = new Project(Long.valueOf(m.group("projectID")), m.group("projectName"));
			projectAndPackageMap.put(project, new ArrayList<>());
		}
	}

	private void getPackages(final Project project) throws IOException {
		final URL url = new URL(buildPackagesForProjectURL(project));

		final HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod(Messages.GET);
		httpConn.setRequestProperty(Messages.Private_Token, gitLabImportPage.getToken());
		String response = "";
		InputStream responseStream;
		try {
			responseStream = httpConn.getInputStream();
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream))) {
				response = reader.readLine();
			}
		} catch (final IOException e) {
			httpConn.disconnect();
			return;
		}
		final String regex = "(?<packageID>[\\w\\s-]*),(?:\\\"|\\')(?:name)(?:\\\"|\\')?:(?:\\\"|\\')(?<packageName>[\\w\\s-]*)\",\"version\":\"(?<packageVersion>[\\w\\s-.]*)\",\"package_type\":\"(?<packageType>[\\w\\s-]*)";
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(response);
		Package pack;
		while (m.find()) {
			pack = new Package(Long.valueOf(m.group("packageID")), m.group("packageName"), m.group("packageVersion"),
					m.group("packageType"));
			projectAndPackageMap.get(project).add(pack);
			if (!packagesAndLeaves.containsKey(pack.getName())) {
				packagesAndLeaves.put(pack.getName(), new ArrayList<>());
			}
			packagesAndLeaves.get(pack.getName()).add(new LeafNode(project, pack, pack.getVersion()));
		}
		responseStream.close();
		httpConn.disconnect();

	}

	private static List<String> parseResponse(final InputStream responseStream) throws IOException {
		final List<String> filenames = new ArrayList<>();
		String response = "";
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream))) {
			response = reader.readLine();
		}

		final String regex = "\"file_name\":\"(\\w+\\.[a-zA-Z0-9]*)";
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(response);
		while (m.find()) {
			filenames.add(m.group(1));
		}

		return filenames;
	}
}
