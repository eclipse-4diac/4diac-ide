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
 *   Fabio Gandolfi - initial implementation and/or documentation
 *   Ernst Blecha - refactoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import org.apache.tools.ant.BuildException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;

public class CheckTypeLibrary extends AbstractCheckTask {

	private String projectNameString;

	public void setProjectName(final String value) {
		projectNameString = value;
	}

	@Override
	public void execute() throws BuildException {
		final IProject project = requireProject(projectNameString);
		buildProject(project);
		final var markers = findProblemMarkers(project, IResource.DEPTH_INFINITE).stream()
				.filter(marker -> !(marker.getResource() instanceof final IFile file
						&& SystemManager.isSystemFile(file)))
				.toList();
		reportAndFail("checkTypeLibrary", project, null, markers); //$NON-NLS-1$
	}

}
