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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ResourcesPlugin;
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
	

	public GitLabApi getGitLabApi() {
		return gitLabApi;
	}
	
	public void connectToGitLab(String url, String personalToken) {
		gitLabApi = new GitLabApi(url, personalToken);
		filterData();
//		try {
//			packageDownloader();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}	
	
	private void filterData() {
		try {
			projectAndPackageMap = new HashMap<>();
			for(Project p: gitLabApi.getProjectApi().getProjects()) {
				if (p.getId() == 371) {
					projectAndPackageMap.put(p, gitLabApi.getPackagesApi().getPackages(p.getId()));
				}
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
	
	public void packageDownloader(Package p, String token) throws IOException {
		URL url = new URL("https://sourcery.im.jku.at/api/v4/projects/371/packages/generic/last_but_not_least/0.0.1/proba2.txt");
		
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("GET");
		httpConn.setRequestProperty("PRIVATE-TOKEN", token);

		InputStream responseStream = httpConn.getInputStream();

		createDir();
		Files.copy(responseStream, Paths.get(PATH, DIRECTORY, "proba2.txt"), StandardCopyOption.REPLACE_EXISTING);
		
		responseStream.close();
		httpConn.disconnect();
	}
	
	
	
}
