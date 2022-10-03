/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.eclipse.core.resources.ResourcesPlugin;

public abstract class AbstractExportLibElements extends AbstractFBTask {

	@Override
	public void execute() throws BuildException {

		if (getProjectNameString() == null) {
			throw new BuildException("Project name not specified!"); //$NON-NLS-1$
		}

		workspace = ResourcesPlugin.getWorkspace();
		setFordiacProject(workspace.getRoot().getProject(getProjectNameString()));
		checkFordiacProject();

		checkValidDestination();

		Import4diacProject.waitBuilderJobsComplete();

		final List<File> files = new ArrayList<>();

		getFBsFiles(files, getDirectory(), getSingleFBName(), getExcludeSubfolder());

		convertFiles(files);

	}

	protected abstract void convertFiles(final List<File> files)
			throws BuildException;

	private void checkValidDestination() {

		// check that folder outside of the project
		final File folder = new File(exportDirectory);

		if (folder.getAbsolutePath().replace("\\", "/") //$NON-NLS-1$ //$NON-NLS-2$
				.startsWith(getFordiacProject().getLocation().toString().replace("\\", "/"))) { //$NON-NLS-1$ //$NON-NLS-2$
			throw new BuildException("Export Directory needs to be outsite of the project"); //$NON-NLS-1$
		}

		if (!folder.exists()) {
			folder.mkdir();
		}

	}
}
