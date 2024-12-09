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
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
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
			return delta.getKind() != IResourceDelta.REMOVED; // ignore deleted projects
		case IResource.FILE:
			// information on previous linked status is not available on delete
			if (delta.getResource() instanceof final IFile file
					&& (file.isLinked() || delta.getKind() == IResourceDelta.REMOVED)
					&& LibraryManager.MANIFEST.equals(file.getName()) && (delta.getKind() & MASK) != 0) {
				final IProject project = file.getProject();
				LibraryManager.INSTANCE.startResolveJob(project, TypeLibraryManager.INSTANCE.getTypeLibrary(project));

			}
			return false;
		case IResource.FOLDER:
			if (delta.getResource() instanceof final IFolder folder) {
				if (delta.getKind() == IResourceDelta.ADDED
						&& TypeLibraryTags.STANDARD_LIB_FOLDER_NAME.equals(folder.getName())) {
					LibraryManager.INSTANCE.startResolveJob(folder.getProject(),
							TypeLibraryManager.INSTANCE.getTypeLibrary(folder.getProject()));
				}
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

	private static boolean isTypeLibraryFolder(final IContainer container) {
		return container instanceof IFolder && container.getParent() instanceof IProject
				&& (TypeLibraryTags.STANDARD_LIB_FOLDER_NAME.equals(container.getName())
						|| TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME.equals(container.getName()));
	}
}
