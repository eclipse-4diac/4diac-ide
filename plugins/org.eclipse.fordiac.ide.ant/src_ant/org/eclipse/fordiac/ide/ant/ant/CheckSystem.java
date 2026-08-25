/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.text.MessageFormat;

import org.apache.tools.ant.BuildException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public class CheckSystem extends AbstractCheckTask {

	private String systemPathString;

	public void setSystemPath(final String value) {
		systemPathString = value;
	}

	@Override
	public void execute() throws BuildException {
		if (systemPathString == null || systemPathString.isBlank()) {
			throw new BuildException("System path not specified!"); //$NON-NLS-1$
		}

		final IFile systemFile = getSystemFile();
		if (systemFile == null || !systemFile.exists() || !SystemManager.isSystemFile(systemFile)) {
			throw new BuildException(MessageFormat.format("System path {0} is not a valid system in workspace!", //$NON-NLS-1$
					systemPathString));
		}

		buildProject(systemFile.getProject());
		final var markers = findProblemMarkers(systemFile, IResource.DEPTH_ZERO);
		reportAndFail("checkSystem", systemFile.getProject(), systemFile.getFullPath().toPortableString(), markers); //$NON-NLS-1$
	}

	private IFile getSystemFile() {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		return workspace.getRoot().getFile(new Path(systemPathString));
	}

}
