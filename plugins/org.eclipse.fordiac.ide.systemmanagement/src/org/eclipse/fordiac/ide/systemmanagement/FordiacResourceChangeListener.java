/*******************************************************************************
 * Copyright (c) 2012 - 2017 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.dataexport.AbstractBlockTypeExporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class FordiacResourceChangeListener implements IResourceChangeListener {

	/** The instance. */
	private SystemManager systemManager;

	public FordiacResourceChangeListener(SystemManager systemManager) {
		this.systemManager = systemManager;
	}

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
			// get the delta, if any, for the documentation directory
			IResourceDelta rootDelta = event.getDelta();
			try {
				rootDelta.accept(visitor);
			} catch (CoreException e) {
				Activator.getDefault().logError("Couldn't process resource delta", e); //$NON-NLS-1$
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
			if (IResourceDelta.MOVED_FROM == (delta.getFlags() & IResourceDelta.MOVED_FROM)) {
				return handleResourceMovedFrom(delta);
			}
			return handleResourceCopy(delta);
		default:
			break;
		}
		return true;
	};

	private static boolean isSystemFile(IFile file) {
		return SystemManager.SYSTEM_FILE_ENDING.equalsIgnoreCase(file.getFileExtension());
	}

	private boolean handleResourceChanged(IResourceDelta delta) {
		if (IResourceDelta.OPEN == delta.getFlags()) {
			// project is opened oder closed
			if (0 != delta.getAffectedChildren(IResourceDelta.ADDED).length) {
				systemManager.notifyListeners();
			} else if (0 != delta.getAffectedChildren(IResourceDelta.REMOVED).length) {
				handleProjectRemove(delta);
			}
			return false;
		}
		return true;
	}

	private boolean handleResourceRemoved(IResourceDelta delta) {
		if (delta.getFlags() == IResourceDelta.MOVED_TO) {
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

	private boolean handleResourceMovedFrom(IResourceDelta delta) {
		if (IResource.FILE == delta.getResource().getType()) {
			handleFileMove(delta);
			return false;
		}
		return true;
	}

	private boolean handleResourceCopy(IResourceDelta delta) {
		switch (delta.getResource().getType()) {
		case IResource.FILE:
			handleFileCopy(delta);
			break;
		case IResource.FOLDER:
			// if a folder has been moved we need to update the IFile of the children
			return true;
		default:
			break;
		}
		return false;
	}

	private void handleFileDelete(IResourceDelta delta) {
		TypeLibrary typeLib = TypeLibrary.getTypeLibrary(delta.getResource().getProject());
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getResource().getFullPath());

		if (isSystemFile(file)) {
			systemManager.removeSystem(file);
		} else {
			PaletteEntry entry = TypeLibrary.getPaletteEntryForFile(file);
			if (null != entry) {
				closeAllEditorsForFile(file);
				typeLib.removePaletteEntry(entry);
			}
		}
	}

	private void handleFileCopy(IResourceDelta delta) {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getResource().getFullPath());

		if (isSystemFile(file)) {
			// in case of a copied system file we just need to fix the name in the root XML
			// node
			renameSystemFile(file);
		} else {
			TypeLibrary typeLib = TypeLibrary.getTypeLibrary(delta.getResource().getProject());
			if (!typeLib.containsType(file)) {
				PaletteEntry entry = typeLib.createPaletteEntry(file);
				if (null != entry) {
					updatePaletteEntry(file, entry);
				}
			}
		}
	}

	private void handleFileMove(IResourceDelta delta) {
		IFile src = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getMovedFromPath());

		if (src.getParent().equals(delta.getResource().getParent())) {
			handleFileRename(delta, src);
		}
	}

	private void handleFileRename(IResourceDelta delta, IFile src) {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getResource().getFullPath());

		if (isSystemFile(file)) {
			renameSystemFile(file);
		} else {
			PaletteEntry entry = TypeLibrary.getPaletteEntryForFile(src);
			updatePaletteEntry(file, entry);
		}
	}

	private void renameSystemFile(final IFile file) {
		WorkspaceJob job = new WorkspaceJob("Save renamed system: " + file.getName()) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				AutomationSystem system = systemManager.getSystem(file);
				system.setName(TypeLibrary.getTypeNameFromFile(file));
				SystemManager.saveSystem(system);
				return Status.OK_STATUS;
			}
		};
		job.setRule(file.getProject());
		job.schedule();
	}

	private static void updatePaletteEntry(final IFile newFile, final PaletteEntry entry) {
		if (null != entry) {
			String newTypeName = TypeLibrary.getTypeNameFromFile(newFile);
			entry.getTypeLibrary().removePaletteEntry(entry);
			entry.setLabel(newTypeName);
			entry.setFile(newFile);
			entry.getTypeLibrary().addPaletteEntry(entry);

			WorkspaceJob job = new WorkspaceJob("Save Renamed type: " + entry.getLabel()) {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) {
					// do the actual work in here
					final LibraryElement type = entry.getType();
					if ((null != type) && // this means we couldn't load the type seems
					// like a problem in the type's XML file
					// TODO report on error
					(!newTypeName.equals(type.getName()))) {
						type.setName(newTypeName);
						AbstractBlockTypeExporter.saveType(entry);
					}
					return Status.OK_STATUS;
				}
			};
			job.setRule(newFile.getProject());
			job.schedule();
		}
	}

	private void handleProjectRemove(IResourceDelta delta) {
		IProject project = delta.getResource().getProject();
		closeAllProjectRelatedEditors(project);
		systemManager.removeProject(project);
		TypeLibrary.removeProject(project);
	}

	private static void closeAllProjectRelatedEditors(final IProject project) {
		Display.getDefault().asyncExec(() -> EditorUtils.closeEditorsFiltered((IEditorPart editor) -> {
			IEditorInput input = editor.getEditorInput();
			if ((input instanceof FileEditorInput)
					&& (project.equals(((FileEditorInput) input).getFile().getProject()))) {
				return true;
			}
			if (editor instanceof ISystemEditor) {
				AutomationSystem system = ((ISystemEditor) editor).getSystem();
				return project.equals(system.getSystemFile().getProject());
			}
			return false;
		}));
	}

	private static void closeAllEditorsForFile(final IFile file) {
		// display related stuff needs to run in a display thread
		Display.getDefault().asyncExec(() -> EditorUtils.closeEditorsFiltered((IEditorPart editor) -> {
			IEditorInput input = editor.getEditorInput();
			return (input instanceof FileEditorInput) && (file.equals(((FileEditorInput) input).getFile()));
		}));
	}
}
