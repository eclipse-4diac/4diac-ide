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

import java.util.Scanner;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;

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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.Palette.DataTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.data.AnyDerivedType;
import org.eclipse.fordiac.ide.model.dataexport.AbstractBlockTypeExporter;
import org.eclipse.fordiac.ide.model.dataexport.DataTypeExporter;
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

	private static boolean isSystemFile(final IFile file) {
		return SystemManager.SYSTEM_FILE_ENDING.equalsIgnoreCase(file.getFileExtension());
	}

	private boolean handleResourceChanged(final IResourceDelta delta) {
		if (isExternalSysFileChange(delta)) {
			systemManager.notifyAutmationSystemListeners((IFile) delta.getResource());
		} else if (IResourceDelta.OPEN == delta.getFlags()) {
			// project is opened oder closed
			if (0 != delta.getAffectedChildren(IResourceDelta.ADDED).length) {
				systemManager.notifyListeners();
			} else if (0 != delta.getAffectedChildren(IResourceDelta.REMOVED).length) {
				handleProjectRemove(delta);
				return false;
			}
		}
		return true;
	}

	private static boolean isExternalSysFileChange(final IResourceDelta delta) {
		return delta.getResource().getType() == IResource.FILE && isSystemFile((IFile) delta.getResource())
				&& delta.getFlags() != IResourceDelta.MARKERS;
	}

	private boolean handleResourceRemoved(final IResourceDelta delta) {
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

	private boolean handleResourceMovedFrom(final IResourceDelta delta) {
		if (IResource.FILE == delta.getResource().getType()) {
			handleFileMove(delta);
			// return false;
		}
		return true;
	}

	private boolean handleResourceCopy(final IResourceDelta delta) {
		switch (delta.getResource().getType()) {
		case IResource.FILE:
			handleFileCopy(delta);
			break;
		case IResource.FOLDER:
			// if a folder has been moved we need to update the IFile of the children
			return true;
		case IResource.PROJECT:
			return true;
		default:
			break;
		}
		return false;
	}

	private void handleFileDelete(final IResourceDelta delta) {
		final TypeLibrary typeLib = TypeLibrary.getTypeLibrary(delta.getResource().getProject());
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getResource().getFullPath());

		if (isSystemFile(file)) {
			systemManager.removeSystem(file);
		} else {
			final PaletteEntry entry = TypeLibrary.getPaletteEntryForFile(file);
			if (null != entry) {
				closeAllEditorsForFile(file);
				typeLib.removePaletteEntry(entry);
			}
		}
	}

	private void handleFileCopy(final IResourceDelta delta) {
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getResource().getFullPath());
		if (file.getProject().isOpen() && delta.getFlags() != IResourceDelta.MARKERS) {

			if (isSystemFile(file)) {
				// in case of a copied system file we just need to fix the name in the root XML
				// node
				renameSystemFileCopy(file);
			} else {
				final TypeLibrary typeLib = TypeLibrary.getTypeLibrary(delta.getResource().getProject());
				final PaletteEntry paletteEntryForFile = TypeLibrary.getPaletteEntryForFile(file);
				if (paletteEntryForFile == null) {
					final PaletteEntry entry = typeLib.createPaletteEntry(file);
					if (null != entry) {
						updatePaletteEntry(file, entry);
					}
				} else
					if (!file.equals(paletteEntryForFile.getFile())) {
						// After a file has been copied and the copied file is not the same as the founded palette entry
						// the file and the resulting type must be renamed with a unique name
						autoRenameExistingType(file, paletteEntryForFile);
					}

			}
		}
	}

	protected static void autoRenameExistingType(final IFile file, final PaletteEntry paletteEntryForFile) {

		final WorkspaceJob job = new WorkspaceJob("Check copied type file: " + file.getName()) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				final LibraryElement type = paletteEntryForFile.getType();
				final String oldName = type.getName();
				final String newName = NameRepository.createUniqueTypeName(type);
				if (newName == null || newName.equals(oldName)) {
					return Status.CANCEL_STATUS;
				}
				final String oldPath = file.getFullPath().toOSString();
				final String newPath = replaceLast(oldPath, oldName, newName);

				try {// this will trigger handleFileMove() and further handleFileRename(). file.move() is a workaround
					// to rename a file
					file.move(new Path(newPath), true, new NullProgressMonitor());
				} catch (final CoreException e) {
					e.printStackTrace();
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(file.getProject());
		job.schedule();
	}

	public static String replaceLast(final String text, final String regex, final String replacement) {
		return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement); //$NON-NLS-1$ //$NON-NLS-2$
	}

	static final Pattern systemNamePattern = Pattern
			.compile("\\<System\\p{javaWhitespace}+(Comment=\".*\"\\p{javaWhitespace}+)?Name=\"([^\"]*)"); //$NON-NLS-1$

	private void renameSystemFileCopy(final IFile file) {
		final WorkspaceJob job = new WorkspaceJob("Check copied system file: " + file.getName()) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				boolean wrongName = false;
				final String newTypeName = TypeLibrary.getTypeNameFromFile(file);
				try (Scanner scanner = new Scanner(file.getContents())) {
					final String name = scanner.findWithinHorizon(systemNamePattern, 0);
					wrongName = (null != name) && (!name.endsWith("\"" + newTypeName));
				} catch (final Exception e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
				if (wrongName) {
					final AutomationSystem system = systemManager.getSystem(file);
					if ((null != system) && (!newTypeName.equals(system.getName()))) {
						system.setName(TypeLibrary.getTypeNameFromFile(file));
						SystemManager.saveSystem(system);
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(file.getProject());
		job.schedule();
	}

	private void handleFileMove(final IResourceDelta delta) {
		final IFile src = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getMovedFromPath());

		if (src.getParent().equals(delta.getResource().getParent())) {
			handleFileRename(delta, src);
		}
	}

	private void handleFileRename(final IResourceDelta delta, final IFile src) {
		final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getResource().getFullPath()); // targetFile
		if (isSystemFile(file)) {
			renameSystemFile(file);
		} else {
			handleTypeRename(delta, src, file);
		}
	}

	private static void handleTypeRename(final IResourceDelta delta, final IFile src, final IFile file) {
		final TypeLibrary typeLibrary = TypeLibrary.getTypeLibrary(delta.getResource().getProject());
		final PaletteEntry entry = TypeLibrary.getPaletteEntryForFile(src);
		if (src.equals(entry.getFile())) {
			updatePaletteEntry(file, entry);
		} else {
			updatePaletteEntry(file, typeLibrary.createPaletteEntry(file));
		}
	}

	private void renameSystemFile(final IFile file) {
		final WorkspaceJob job = new WorkspaceJob("Save renamed system: " + file.getName()) {
			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) {
				final AutomationSystem system = systemManager.getSystem(file);
				if (null != system) {
					final String newTypeName = TypeLibrary.getTypeNameFromFile(file);
					if (!newTypeName.equals(system.getName())) {
						system.setName(TypeLibrary.getTypeNameFromFile(file));
						SystemManager.saveSystem(system);
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setRule(file.getProject());
		job.schedule();
	}

	private static void updatePaletteEntry(final IFile newFile, final PaletteEntry entry) {
		if (null != entry) {
			final String newTypeName = TypeLibrary.getTypeNameFromFile(newFile);
			entry.getTypeLibrary().removePaletteEntry(entry);
			entry.setLabel(newTypeName);
			entry.setFile(newFile);
			entry.getTypeLibrary().addPaletteEntry(entry);

			final WorkspaceJob job = new WorkspaceJob("Save Renamed type: " + entry.getLabel()) {
				@Override
				public IStatus runInWorkspace(final IProgressMonitor monitor) {
					// do the actual work in here
					final LibraryElement type = entry.getType();
					if ((null != type) && // this means we couldn't load the type seems
							// like a problem in the type's XML file
							// TODO report on error
							(!newTypeName.equals(type.getName()))) {
						type.setName(newTypeName);
						saveType(entry);
					}
					return Status.OK_STATUS;
				}

				private void saveType(final PaletteEntry entry) {
					if (entry instanceof DataTypePaletteEntry) {
						final DataTypeExporter exporter = new DataTypeExporter((AnyDerivedType) entry.getType());
						try {
							exporter.saveType(entry.getFile());
						} catch (final XMLStreamException e) {
							Activator.getDefault().logError(e.getMessage(), e);
						}
					} else {
						AbstractBlockTypeExporter.saveType(entry);
					}
				}
			};
			job.setRule(newFile.getProject());
			job.schedule();
		}
	}

	private void handleProjectRemove(final IResourceDelta delta) {
		final IProject project = delta.getResource().getProject();
		closeAllProjectRelatedEditors(project);
		systemManager.removeProject(project);
		TypeLibrary.removeProject(project);
	}

	private static void closeAllProjectRelatedEditors(final IProject project) {
		Display.getDefault().asyncExec(() -> EditorUtils.closeEditorsFiltered((final IEditorPart editor) -> {
			final IEditorInput input = editor.getEditorInput();
			if ((input instanceof FileEditorInput)
					&& (project.equals(((FileEditorInput) input).getFile().getProject()))) {
				return true;
			}
			if (editor instanceof ISystemEditor) {
				final AutomationSystem system = ((ISystemEditor) editor).getSystem();
				return project.equals(system.getSystemFile().getProject());
			}
			return false;
		}));
	}

	private static void closeAllEditorsForFile(final IFile file) {
		// display related stuff needs to run in a display thread
		Display.getDefault().asyncExec(() -> EditorUtils.closeEditorsFiltered((final IEditorPart editor) -> {
			final IEditorInput input = editor.getEditorInput();
			return (input instanceof FileEditorInput) && (file.equals(((FileEditorInput) input).getFile()));
		}));
	}
}
