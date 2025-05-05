/*******************************************************************************
 * Copyright (c) 2012, 2025 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University Linz,
 *                          Primetals Technologies Austria GmbH,
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - New Project Explorer layout
 *               - Fixed handing of project renameing
 *   Martin Erich Jobst
 *     - fix handling of delta flags, file reload, and project open/close
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.changelistener;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IFileEditorInput;

public class FordiacResourceChangeListener implements IResourceChangeListener {

	private final SystemManager systemManager;

	public FordiacResourceChangeListener(final SystemManager systemManager) {
		this.systemManager = systemManager;
	}

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			// get the delta, if any, for the documentation directory
			final IResourceDelta rootDelta = event.getDelta();
			try {
				rootDelta.accept(visitor);
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Couldn't process resource delta", e); //$NON-NLS-1$
			}
		}
	}

	IResourceDeltaVisitor visitor = delta -> {
		switch (delta.getKind()) {
		case IResourceDelta.CHANGED:
			return handleResourceChanged(delta);
		case IResourceDelta.REMOVED:
			return handleResourceRemoved(delta);
		case IResourceDelta.ADDED:
			if (testFlags(delta, IResourceDelta.MOVED_FROM)) {
				return handleResourceMovedFrom(delta);
			}
			break;
		default:
			break;
		}
		return true;
	};

	private boolean handleResourceChanged(final IResourceDelta delta) {
		if (delta.getResource().getType() == IResource.PROJECT && testFlags(delta, IResourceDelta.OPEN)) {
			if (delta.getResource().isAccessible()) {
				// refresh type library when opening project
				TypeLibraryManager.INSTANCE.getTypeLibrary(delta.getResource().getProject()).refresh();
			} else {
				// this is the odd way of Eclipse Platform telling us a project was closed
				handleProjectRemove(delta);
			}
			return false;
		}
		if (testFlags(delta, IResourceDelta.MARKERS)) {
			systemManager.notifyListeners();
		}
		return true;
	}

	private static boolean handleResourceRemoved(final IResourceDelta delta) {
		final IProject project = delta.getResource().getProject();
		if (testFlags(delta, IResourceDelta.MOVED_TO) || !TypeLibraryManager.INSTANCE.hasTypeLibrary(project)) {
			// we will handle movement only on the add side
			return false;
		}
		switch (delta.getResource().getType()) {
		case IResource.FILE:
			handleFileDelete(delta);
			break;
		case IResource.PROJECT:
			handleProjectRemove(delta);
			return false;
		default:
			// we don't need to do anything in the other cases
			break;
		}
		return true;
	}

	private static boolean handleResourceMovedFrom(final IResourceDelta delta) {
		final IProject project = delta.getResource().getProject();
		if (!TypeLibraryManager.INSTANCE.hasTypeLibrary(project)) {
			return false;
		}

		if (delta.getResource().getType() == IResource.PROJECT) {
			handleProjectRename(delta);
		}
		return true;
	}

	private static void handleFileDelete(final IResourceDelta delta) {
		final IFile file = (IFile) delta.getResource();
		closeAllEditorsForFile(file);
	}

	public static String replaceLast(final String text, final String regex, final String replacement) {
		return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static void handleProjectRename(final IResourceDelta delta) {
		final IProject oldProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(delta.getMovedFromPath().lastSegment());
		final IProject newProject = delta.getResource().getProject();
		TypeLibraryManager.INSTANCE.renameProject(oldProject, newProject);
	}

	private static void handleProjectRemove(final IResourceDelta delta) {
		final IProject project = delta.getResource().getProject();
		closeAllProjectRelatedEditors(project);
		TypeLibraryManager.INSTANCE.removeProject(project);
	}

	private static void closeAllProjectRelatedEditors(final IProject project) {
		Display.getDefault()
				.asyncExec(() -> EditorUtils.closeEditorsFiltered(
						editor -> ((editor.getEditorInput() instanceof final IFileEditorInput fileEditorInput)
								&& (project.equals(fileEditorInput.getFile().getProject())))));
	}

	private static void closeAllEditorsForFile(final IFile file) {
		// display related stuff needs to run in a display thread
		Display.getDefault()
				.asyncExec(() -> EditorUtils.closeEditorsFiltered(
						editor -> ((editor.getEditorInput() instanceof final IFileEditorInput fileEditorInput)
								&& (file.equals(fileEditorInput.getFile())))));
	}

	private static boolean testFlags(final IResourceDelta delta, final int flags) {
		return (delta.getFlags() & flags) == flags;
	}
}
