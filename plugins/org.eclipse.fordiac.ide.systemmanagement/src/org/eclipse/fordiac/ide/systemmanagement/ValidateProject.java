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

import org.eclipse.core.resources.IFile;
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

public final class ValidateProject {

	public static void checkTypeLibraryInProjectsInWorkspaceJob() {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		final IProject[] projects = root.getProjects();

		final WorkspaceJob job = new WorkspaceJob(Messages.ValidateTypeLibrary) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				for (final IProject project : projects) {
					checkTypeLibraryInProjects(project);
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(null);
		job.schedule();
		try {
			job.join();
		} catch (final InterruptedException e) {
			FordiacLogHelper.logError("checkTypeLibraryInProjectsInWorkspaceJob interrupted", e); //$NON-NLS-1$
			Thread.currentThread().interrupt();
		}

	}

	public static void checkTypeLibraryInProjects(final IProject project) {

		if (isFordiacProject(project)) {

			final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);

			typeLibrary.getSubAppTypes().values().forEach(SubAppTypeEntry::getType);
			typeLibrary.getAdapterTypes().values().forEach(AdapterTypeEntry::getType);
			typeLibrary.getFbTypes().values().forEach(FBTypeEntry::getType);
			typeLibrary.getDataTypeLibrary().getDerivedDataTypes().values().forEach(DataTypeEntry::getType);
		}
	}

	public static void checkSTInProjects(final IProject project) {
		if (isFordiacProject(project)) {
			final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);

			typeLibrary.getFbTypes().values().stream().filter(f -> f.getType() instanceof BaseFBType)
			.forEach(f -> checkBaseFB((BaseFBType) f.getType()));
		}
	}

	public static void checkBaseFB(final BaseFBType baseFB) {
		final List<Issue> errors = StructuredTextParseUtil.validate(baseFB);
		final IFile file = baseFB.getTypeEntry().getFile();

		final MarkerCreator markerCreator = new MarkerCreator();
		synchronized (baseFB.getTypeEntry()) {
			for (final Issue issue : errors) {
				try {
					markerCreator.createMarker(issue, file, IMarker.PROBLEM);
					baseFB.getTypeEntry().setLastModificationTimestamp(file.getModificationStamp());
				} catch (final CoreException e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			}
		}
	}

	public static void clear(final IProject project) {
		if (isFordiacProject(project)) {
			try {
				project.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Could not delete error marker", e); //$NON-NLS-1$
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

	private ValidateProject() {
		throw new IllegalStateException("Utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
