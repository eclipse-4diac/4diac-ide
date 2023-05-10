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
package org.eclipse.fordiac.ide.gitlab.treeviewer;

import org.gitlab4j.api.models.Package;
import org.gitlab4j.api.models.Project;

public class LeafNode {

	private Project project;
	private Package pack;
	private String version;
	
	public LeafNode(Project project, Package pack, String version) {
		this.project = project;
		this.pack = pack;
		this.version = version;
	}

	public Project getProject() {
		return project;
	}

	public Package getPackage() {
		return pack;
	}

	public String getVersion() {
		return version;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setPack(Package pack) {
		this.pack = pack;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
