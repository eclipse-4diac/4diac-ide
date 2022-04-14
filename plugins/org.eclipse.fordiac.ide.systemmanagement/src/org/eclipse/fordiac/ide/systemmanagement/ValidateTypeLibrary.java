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
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class ValidateTypeLibrary {

	public static void validate() {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		final IProject[] projects = root.getProjects();

		final WorkspaceJob job = new WorkspaceJob(Messages.ValidateTypeLibrary) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {

				for (final IProject project : projects) {

					if (isFordiacProject(project)) {
						final TypeLibrary typeLibrary = TypeLibrary.getTypeLibrary(project);
						typeLibrary.getBlockTypeLib().getSubAppTypes().values()
						.forEach(SubApplicationTypePaletteEntry::getType);
						typeLibrary.getBlockTypeLib().getAdapterTypes().values()
						.forEach(AdapterTypePaletteEntry::getType);
						typeLibrary.getBlockTypeLib().getFbTypes().values().forEach(FBTypePaletteEntry::getType);
						typeLibrary.getDataTypeLibrary().getStructuredTypes().forEach(s -> {
							if (s.getPaletteEntry() != null) {
								s.getPaletteEntry().getType();
							}
						});
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(root);
		job.schedule();
	}


	private static boolean isFordiacProject(final IProject project) {
		try {
			return project.isOpen() && project.hasNature(SystemManager.FORDIAC_PROJECT_NATURE_ID);
		} catch (final CoreException e) {
			FordiacLogHelper.logError("Could not read project nature", e); //$NON-NLS-1$
		}
		return false;
	}

}
