/*******************************************************************************
+ * Copyright (c) 2022 Primetals Technologies Austria GmbH
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


public abstract class AbstractExportFBs extends AbstractFBTask {

	@Override
	public final void execute() throws BuildException {
		if (getProjectNameString() == null) {
			throw new BuildException("Project name not specified!"); //$NON-NLS-1$
		}

		workspace = ResourcesPlugin.getWorkspace();
		setFordiacProject(workspace.getRoot().getProject(getProjectNameString()));
		checkFordiacProject();

		CheckTypeLibrary.waitBuilderJobsComplete();

		final List<File> files = new ArrayList<>();

		getFBsFiles(files, getDirectory(), getSingleFBName(), getExcludeSubfolder());
		exportFiles(files);

	}

	protected abstract void exportFiles(final List<File> files);

}
