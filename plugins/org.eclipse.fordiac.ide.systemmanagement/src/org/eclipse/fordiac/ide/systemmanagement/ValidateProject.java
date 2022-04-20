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

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.SubAppTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.xtext.ui.editor.validation.MarkerCreator;
import org.eclipse.xtext.validation.Issue;

public class ValidateProject {	
	
	public static void checkTypeLibraryInProjectsInWorkspaceJob() {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		final IProject[] projects = root.getProjects();

		final WorkspaceJob job = new WorkspaceJob(Messages.ValidateTypeLibrary) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {

				checkTypeLibraryInProjects(projects);
				return Status.OK_STATUS;
			}
		};
		job.setRule(root);
		job.schedule();
		
	}
	
	public static void checkTypeLibraryInProjects(IProject[] projects) {
		for (final IProject project : projects) {

			if (isFordiacProject(project)) {
				
				final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
										
				typeLibrary.getSubAppTypes().values()
				.forEach(SubAppTypeEntry::getType);
				typeLibrary.getAdapterTypes().values()
				.forEach(AdapterTypeEntry::getType);
				typeLibrary.getFbTypes().values().forEach(FBTypeEntry::getType);
				typeLibrary.getDataTypeLibrary().getDerivedDataTypes().values().forEach(DataTypeEntry::getType);
			}
		}
	}
	
	public static void checkSTInProjects(IProject[] projects) {
		for (final IProject project : projects) {
			if (isFordiacProject(project)) {
				final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
				
				typeLibrary.getFbTypes().values().forEach(f -> {
					if (f.getType() instanceof BaseFBType) {
						final List<Issue> errors = StructuredTextParseUtil.validate((BaseFBType) f.getType());
						
						final MarkerCreator markerCreator = new MarkerCreator();
						for (final Issue issue : errors) {
							try {
								markerCreator.createMarker(issue, f.getFile(), IMarker.PROBLEM);
								f.setLastModificationTimestamp(f.getFile().getModificationStamp());
							} catch (final CoreException e) {
								FordiacLogHelper.logError(e.getMessage(), e);
							}
						}
					}
				});
			}
		}
	}
	
	public static void clearInWorkspaceJob() {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final WorkspaceJob job = new WorkspaceJob("Remove all Markers") {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				clear(root.getProjects());
				return Status.OK_STATUS;
			}
		};
		job.setRule(root);
		job.schedule();
	}

	public static void clear(IProject[] projects) { 
		for (final IProject project : projects) {
			if (isFordiacProject(project)) {
				try {
					project.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
				} catch (CoreException e) {
					FordiacLogHelper.logError("Could not delete error marker", e); //$NON-NLS-1$
				}
			}
		}
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
