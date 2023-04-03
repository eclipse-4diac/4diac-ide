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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.fordiac.ide.gitlab.Messages;
import org.eclipse.fordiac.ide.gitlab.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.gitlab.wizard.GitLabImportWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Package;
import org.gitlab4j.api.models.PackageType;
import org.gitlab4j.api.models.Project;

public class GitLabDownloadManager {
	
	private static final String PATH = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString();
	private static final String DIRECTORY = ".fblib";
	private GitLabApi gitLabApi;
	private HashMap<Project, List<Package>> projectAndPackageMap;
	private GitLabImportWizardPage gitLabImportPage;
	
	public GitLabDownloadManager(GitLabImportWizardPage gitLabImportPage) {
		this.gitLabImportPage = gitLabImportPage;
	}
	
	public GitLabApi getGitLabApi() {
		return gitLabApi;
	}
	
	public void connectToGitLab(String url, String personalToken) {
		gitLabApi = new GitLabApi(url, personalToken);
		filterData();
	}	
	
	private void filterData() {
		try {
			projectAndPackageMap = new HashMap<>();
			for(Project p: gitLabApi.getProjectApi().getProjects()) {
				projectAndPackageMap.put(p, gitLabApi.getPackagesApi().getPackages(p.getId()));
			}
		} catch (GitLabApiException e) {
			e.printStackTrace();
		}
		
	}
	
	public Map<Project, List<Package>> getMap() {
		return projectAndPackageMap;
	}

	private void createDir() throws IOException {
		Path path = Paths.get(PATH, DIRECTORY);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
	}
	
	public void packageDownloader(Package p, Object project) throws IOException {
		if (project instanceof Project) {
			String filename = findFilenameForPackage(p, ((Project)project));
		
			URL url = new URL(buildDownloadURL(p, project, filename));
		
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod(Messages.GET);
			httpConn.setRequestProperty(Messages.Private_Token, gitLabImportPage.getToken());

			InputStream responseStream = httpConn.getInputStream();

			createDir();
			Files.copy(responseStream, Paths.get(PATH, DIRECTORY, filename), StandardCopyOption.REPLACE_EXISTING);
		
			responseStream.close();
			httpConn.disconnect();
		}
	}
	
	private String findFilenameForPackage(Package pack, Project project) throws IOException {
		URL url = new URL(buildPackageFileURL(pack, project));
		
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod(Messages.GET);
		httpConn.setRequestProperty(Messages.Private_Token, gitLabImportPage.getToken());

		InputStream responseStream = httpConn.getInputStream();
		String filenameInPackage = parseResponse(responseStream);
		
		responseStream.close();
		httpConn.disconnect();
		return filenameInPackage;
	}
	
	private String buildDownloadURL(Package p, Object project, String filename) {
		return gitLabImportPage.getUrl() + "api/v4/projects/" + ((Project)project).getId() + 
				"/packages/" + p.getPackageType() + "/" + p.getName() + "/" + p.getVersion() + "/" + filename;
	}
	
	private String buildPackageFileURL(Package p, Project project) {
		return gitLabImportPage.getUrl() + "api/v4/projects/" + project.getId() +  
				"/packages/" + p.getId() +"/package_files";
	}
	
	private String parseResponse(InputStream responseStream) throws IOException {
		String filename = "";
		String response = "";
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream))) {
	        response = reader.readLine();
	    }
		
		String[] responseSplit = response.split(",");
		for (int i = 0; i < responseSplit.length; i++) {
			if (responseSplit[i].contains("file_name")) {
				String[] filenameParsing = responseSplit[i].split(":");
				filename = filenameParsing[1].replace("\"", ""); // Removing unnecessary quotes
			}
		}
		return filename;
	}
}
