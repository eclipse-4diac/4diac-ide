/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

public class AddLibraryDependencyChange extends CompositeChange {
	private final IProject project;
	private final Required dependency;

	public AddLibraryDependencyChange(final IProject project, final Required dependency) {
		super(Messages.AddLibraryDependency_Change_Title);
		this.project = project;
		this.dependency = dependency;
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		final Manifest projectManifest = ManifestHelper.getContainerManifest(project);
		if (projectManifest == null || projectManifest.getDependencies() == null) {
			return null;
		}

		if (dependency != null && ManifestHelper.addDependency(projectManifest, dependency)
				&& ManifestHelper.saveManifest(projectManifest)) {
			return new DeleteLibraryDependencyChange(project, dependency.getSymbolicName());
		}
		return null;
	}
}
