/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.library;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class LibraryChangeListener implements IResourceChangeListener {
	private static final int MASK = IResourceDelta.ADDED | IResourceDelta.REMOVED | IResourceDelta.CHANGED;

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			final IResourceDelta rootDelta = event.getDelta();
			try {
				rootDelta.accept(visitor, IContainer.INCLUDE_HIDDEN);
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Couldn't process resource delta", e); //$NON-NLS-1$
			}
		}

	}

	private final IResourceDeltaVisitor visitor = delta -> {
		switch (delta.getResource().getType()) {
		case IResource.PROJECT:
			// ignore closed or deleted projects
			return !(isProjectClosed(delta) || delta.getKind() == IResourceDelta.REMOVED);
		case IResource.FILE:
			// check manifest files
			// information on previous linked status is not available on delete
			if (delta.getResource() instanceof final IFile file && LibraryManager.MANIFEST.equals(file.getName())
			// project manifest:
					&& ((file.getParent() instanceof IProject && (delta.getKind() & IResourceDelta.ADDED) != 0)
							// library manifest:
							|| ((file.isLinked() || delta.getKind() == IResourceDelta.REMOVED)
									&& (delta.getKind() & MASK) != 0))) {
				final IProject project = file.getProject();
				LibraryManager.INSTANCE.startResolveJob(project);

			}
			return false;
		case IResource.FOLDER:
			if (delta.getResource() instanceof final IFolder folder) {
				// only search inside linked folders inside the Type Library
				return isTypeLibraryFolder(folder) || ((folder.isLinked() || delta.getKind() == IResourceDelta.REMOVED)
						&& isTypeLibraryFolder(folder.getParent()));
			}
			break;
		default:
			break;
		}
		return true;
	};

	private static boolean isProjectClosed(final IResourceDelta delta) {
		return (delta.getKind() != IResourceDelta.CHANGED
				&& (delta.getFlags() & IResourceDelta.OPEN) == IResourceDelta.OPEN
				&& !delta.getResource().isAccessible());
	}

	private static boolean isTypeLibraryFolder(final IContainer container) {
		return container instanceof IFolder && container.getParent() instanceof IProject
				&& (TypeLibraryTags.STANDARD_LIB_FOLDER_NAME.equals(container.getName())
						|| TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME.equals(container.getName()));
	}
}
