/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import org.apache.tools.ant.BuildException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

public class CheckProject extends AbstractCheckTask {

	private String projectNameString;

	public void setProjectName(final String value) {
		projectNameString = value;
	}

	@Override
	public void execute() throws BuildException {
		final IProject project = requireProject(projectNameString);
		buildProject(project);
		reportAndFail("checkProject", project, null, findProblemMarkers(project, IResource.DEPTH_INFINITE)); //$NON-NLS-1$
	}

}
