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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public class LibraryBuilder extends IncrementalProjectBuilder {
	private static final int MASK = IResourceDelta.ADDED | IResourceDelta.REMOVED | IResourceDelta.CHANGED;
	private final List<String> changedLibs = new LinkedList<>();

	@Override
	protected IProject[] build(final int kind, final Map<String, String> args, final IProgressMonitor monitor)
			throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, "Resolve Project Dependencies", 1); //$NON-NLS-1$
		final IProject project = getProject();
		final Manifest manifest = ManifestHelper.getContainerManifest(project);
		if (manifest != null) {
			if (kind == FULL_BUILD) {
				fullBuild(project, manifest, progress.split(1));
			} else {
				boolean projectManifestChanged = false;
				changedLibs.clear();
				final var delta = getDelta(project);
				final var md = delta.findMember(new Path(LibraryManager.MANIFEST));
				if (md != null && (md.getKind() & (IResourceDelta.ADDED | IResourceDelta.CHANGED)) != 0) {
					projectManifestChanged = true;
				}
				delta.accept(visitor, IContainer.INCLUDE_HIDDEN);
				if (projectManifestChanged || !changedLibs.isEmpty()) {
					fullBuild(project, manifest, progress.split(1)); // no caching yet
				}
			}
		}

		return new IProject[0];
	}

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, "Cleaning Library", 1); //$NON-NLS-1$
		FordiacMarkerHelper.updateMarkers(getProject().getFile(LibraryManager.MANIFEST),
				FordiacErrorMarker.LIBRARY_MARKER, Collections.emptyList(), true);
		progress.worked(1);

		SubMonitor.done(monitor);
	}

	@Override
	public ISchedulingRule getRule(final int kind, final Map<String, String> args) {
		return getProject();
	}

	private static void fullBuild(final IProject project, final Manifest manifest, final IProgressMonitor monitor) {
		final SubMonitor progress = SubMonitor.convert(monitor, "Library build", 1); //$NON-NLS-1$
		LibraryManager.INSTANCE.resolveDependencies(project, progress.split(1));
	}

	private final IResourceDeltaVisitor visitor = delta -> {
		switch (delta.getResource().getType()) {
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

	private static boolean isLinkedLibraryFolder(final IContainer container) {
		return container instanceof IFolder && container.getParent() instanceof IProject
				&& (TypeLibraryTags.STANDARD_LIB_FOLDER_NAME.equals(container.getName())
						|| TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME.equals(container.getName()));
	}
}
