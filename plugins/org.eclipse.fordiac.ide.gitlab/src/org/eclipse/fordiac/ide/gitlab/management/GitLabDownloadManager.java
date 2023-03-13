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

import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Package;

public class GitLabDownloadManager {
	
	private GitLabApi gitLabApi;
	private List<Package> packages;
	
	public List<Package> getPackages() {
		return packages;
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
			// 371 is the Project ID we have on GitLab
			packages = gitLabApi.getPackagesApi().getPackages(Long.valueOf(371)); 
		} catch (GitLabApiException e) {
			e.printStackTrace();
		}
		
	}
}
