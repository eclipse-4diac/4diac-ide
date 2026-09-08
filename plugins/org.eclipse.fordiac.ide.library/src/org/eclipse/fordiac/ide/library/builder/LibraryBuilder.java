/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.builder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryValidation;
import org.eclipse.fordiac.ide.library.Messages;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class LibraryBuilder extends IncrementalProjectBuilder {
	private static final int MASK = IResourceDelta.ADDED | IResourceDelta.REMOVED | IResourceDelta.CHANGED;
	private final List<String> changedLibs = new LinkedList<>();

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, Messages.LibraryBuilder_ResolveProjectDependencies, 1);
		final IProject project = getProject();
		final Manifest manifest = ManifestHelper.getContainerManifest(project);

		if (manifest == null) {
			return new IProject[0];
		}

		if (!LibraryValidation.validate(manifest, getProject())) {
			throw new OperationCanceledException("Build aborted"); //$NON-NLS-1$
		}

		if (kind == FULL_BUILD) {
			fullBuild(project, manifest, progress.split(1));
		} else {
			final var delta = getDelta(project);
			changedLibs.clear();
			if (delta != null) {
				delta.accept(visitor, IContainer.INCLUDE_HIDDEN);
				if (projectManifestChanged(delta) || referencedManifestChanged() || !changedLibs.isEmpty()) {
					fullBuild(project, manifest, progress.split(1)); // no caching yet
				}
			}
		}
		return project.getReferencedProjects();
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, Messages.LibraryBuilder_CleaningLibrary, 1);

		// clean all library markers
		getProject().deleteMarkers(FordiacErrorMarker.LIBRARY_MARKER, true, IResource.DEPTH_INFINITE);
		progress.worked(1);

		SubMonitor.done(monitor);
	}

	@Override
	public ISchedulingRule getRule(final int kind, final Map<String, String> args) {
		return getProject();
	}

	private static void fullBuild(final IProject project, final Manifest manifest, final IProgressMonitor monitor)
			throws OperationCanceledException, CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, Messages.LibraryBuilder_LibraryBuild, 1);
		LibraryManager.INSTANCE.resolveDependencies(project, manifest, progress.split(1));
	}

	private final IResourceDeltaVisitor visitor = delta -> {
		switch (delta.getResource().getType()) {
		case IResource.PROJECT:
			// check if another project has been referenced or a reference has been removed
			// TODO project description also contains other cases, not only referenced
			// projects
			if (delta.getResource() instanceof final IProject project
					&& (delta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
				changedLibs.add(project.getName());
			}
			return true;
		case IResource.FILE:
			// check library manifest files
			// information on previous linked status is not available on delete
			if (delta.getResource() instanceof final IFile file && LibraryManager.MANIFEST.equals(file.getName())
					&& (file.isLinked() || delta.getKind() == IResourceDelta.REMOVED)
					&& (delta.getKind() & MASK) != 0) {
				changedLibs.add(file.getParent().getName());
			}
			return false;
		case IResource.FOLDER:
			if (delta.getResource() instanceof final IFolder folder) {
				// only search inside linked folders inside the Type Library
				return isLinkedLibraryFolder(folder)
						|| ((folder.isLinked() || delta.getKind() == IResourceDelta.REMOVED)
								&& isLinkedLibraryFolder(folder.getParent()));
			}
			break;
		default:
			break;
		}
		return true;
	};

	private boolean referencedManifestChanged() throws CoreException {
		return Stream.of(getProject().getReferencedProjects()).map(this::getDelta).filter(Objects::nonNull)
				.anyMatch(LibraryBuilder::projectManifestChanged);
	}

	private static boolean projectManifestChanged(final IResourceDelta delta) {
		final var md = delta.findMember(new Path(LibraryManager.MANIFEST));
		return md != null && (md.getKind() & (IResourceDelta.ADDED | IResourceDelta.CHANGED)) != 0;
	}

	private static boolean isLinkedLibraryFolder(final IContainer container) {
		return container instanceof IFolder && container.getParent() instanceof IProject
				&& (TypeLibraryTags.STANDARD_LIB_FOLDER_NAME.equals(container.getName())
						|| TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME.equals(container.getName()));
	}
}
