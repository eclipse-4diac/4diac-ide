/*******************************************************************************
 * Copyright (c) 2012 - 2017 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.dataexport.CommonElementExporter;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.controls.editors.EditorUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class FordiacResourceChangeListener implements IResourceChangeListener {
	
	private static final String XML_FILE_EXTENSION = "xml"; //$NON-NLS-1$

	/** The instance. */
	private SystemManager systemManager;

	public FordiacResourceChangeListener(SystemManager systemManager) {
		this.systemManager = systemManager;
	}

	// ! buffer containing systems scheduled for import
	private Set<String> systemImportWatingList = Collections
			.synchronizedSet(new HashSet<String>());

	@Override
	public void resourceChanged(final IResourceChangeEvent event) {
		if (event.getType() == IResourceChangeEvent.POST_CHANGE) {

			IResourceDelta rootDelta = event.getDelta();
			// get the delta, if any, for the documentation directory

			IResourceDeltaVisitor visitor = new IResourceDeltaVisitor() {
				public boolean visit(final IResourceDelta delta) {
					switch (delta.getKind()) {
					case IResourceDelta.CHANGED:
						if (IResourceDelta.OPEN == delta.getFlags()) {
							// project is opend oder closed
							if (0 != delta
									.getAffectedChildren(IResourceDelta.ADDED).length) {
								loadSystem(delta.getResource().getProject());
							} else if (0 != delta
									.getAffectedChildren(IResourceDelta.REMOVED).length) {
								handleProjectRemove(delta);
								return false;
							}
							return false;
						}
						break;
					case IResourceDelta.REMOVED:
						if (delta.getFlags() == IResourceDelta.MOVED_TO) {
							// we will handle movement only on the add side
							return false;
						}
						switch (delta.getResource().getType()) {
						case IResource.FILE:
							handleFileDelete(delta);
							break;
						case IResource.FOLDER:
							handleFolderDelete(delta);
							// folder delete does not require a check if its
							// children
							return false;
						case IResource.PROJECT:
							handleProjectRemove(delta);
							return false;
						}
						break;
					case IResourceDelta.ADDED:
						if (IResourceDelta.MOVED_FROM == (delta.getFlags() & IResourceDelta.MOVED_FROM)) {
							switch (delta.getResource().getType()) {
							case IResource.FILE:
								handleFileMove(delta);
								break;
							case IResource.FOLDER:
								handleFolderMove(delta);
								// if a folder has been moved we need to update
								// the IFile of the children
								return true;
							case IResource.PROJECT:
								handleProjectRename(delta);
								//we need to update the file names in the pallette as well
								return true;
							}
							return false;
						}

						final String projectName = delta.getResource()
								.getProject().getName();
						AutomationSystem system = systemManager
								.getSystemForName(projectName);
						if ((null == system)
								&& (!projectName
										.equals(TypeLibrary.TOOL_LIBRARY_PROJECT_NAME))) {
							if (!systemImportWatingList.contains(projectName)) {
								systemImportWatingList.add(projectName);
								WorkspaceJob job = new WorkspaceJob(
										"Load system: "
												+ delta.getResource()
														.getProject().getName()
												+ " after import") {
									public IStatus runInWorkspace(
											IProgressMonitor monitor) {
										// do the actual work in here
										loadSystem(delta.getResource()
												.getProject());
										// loading of the system has finished we
										// can remove it from the list
										systemImportWatingList
												.remove(projectName);
										return Status.OK_STATUS;
									}
								};
								job.setRule(delta.getResource().getProject());
								job.schedule();
							}

						}

						if ((null != system)
								|| (projectName.equals(TypeLibrary.TOOL_LIBRARY_PROJECT_NAME))) {
							switch (delta.getResource().getType()) {
							case IResource.FILE:
								handleFileCopy(delta);
								break;
							case IResource.FOLDER:
								handleFolderCopy(delta);
								// if a folder has been moved we need to update
								// the IFile of the children
								return true;
							}
							return false;
						}else{
							if(IResource.FILE == delta.getResource().getType()){
								handledCopiedProjectFiles(delta);
							}
						}
						break;
					}
					return true;
				}

				
			};
			try {
				rootDelta.accept(visitor);
			} catch (CoreException e) {
				// open error dialog with syncExec or print to plugin log file
			}
		}
	}

	protected void handleFolderDelete(IResourceDelta delta) {
		Palette palette = systemManager.getPalette(delta.getResource()
				.getProject());
		IFolder folder = ResourcesPlugin.getWorkspace().getRoot()
				.getFolder(delta.getResource().getFullPath());

		PaletteGroup group = TypeLibrary.getPaletteGroup(palette, folder);
		if (null != group) {
			removeGroup(group);
		}
	}

	private static void removeGroup(PaletteGroup group) {
		// first remove the children
		List<PaletteGroup> subGroups = new ArrayList<PaletteGroup>(
				group.getSubGroups());
		for (PaletteGroup runner : subGroups) {
			removeGroup(runner);
		}
		List<PaletteEntry> containedEntries = new ArrayList<PaletteEntry>(
				group.getEntries());
		for (PaletteEntry entry : containedEntries) {
			group.getEntries().remove(entry);
		}

		if ((null != group.getParentGroup())
				&& (null != group.getParentGroup().getSubGroups())) {
			group.getParentGroup().getSubGroups().remove(group);
		}
	}

	protected void handleFileDelete(IResourceDelta delta) {
		Palette palette = systemManager.getPalette(delta.getResource()
				.getProject());
		IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(delta.getResource().getFullPath());

		PaletteGroup group = TypeLibrary.getPaletteGroup(palette, file.getParent());
		
		if (null != group) {
			PaletteEntry entry = group.getEntry(TypeLibrary
					.getTypeNameFromFile(file));
			if (null != entry) {
				closeAllFBTypeEditor(entry);
				group.getEntries().remove(entry);
			}
		}
	}

	protected void handleFolderCopy(IResourceDelta delta) {
		Palette dstPalette = systemManager.getPalette(delta.getResource()
				.getProject());
		IFolder file = ResourcesPlugin.getWorkspace().getRoot()
				.getFolder(delta.getResource().getFullPath());

		PaletteGroup parent = TypeLibrary.getPaletteGroupWithAdd(dstPalette, file.getParent());
		if (null != parent) {
			if (null == parent.getGroup(file.getName())) {
				dstPalette.createGroup(parent, file.getName());
			}
		}
	}

	protected void handleFileCopy(IResourceDelta delta) {
		Palette dstPalette = systemManager.getPalette(delta.getResource()
				.getProject());
		IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(delta.getResource().getFullPath());
		
		PaletteGroup dstGroup = TypeLibrary.getPaletteGroupWithAdd(
				dstPalette, delta.getResource().getParent());
		if (null != dstGroup) {
			if (null == dstGroup.getEntry(TypeLibrary
					.getTypeNameFromFile(file))) {				
				PaletteEntry entry = TypeLibrary.createPaleteEntry(dstPalette, dstGroup, file); 
				if(null != entry){
					updatePaletteEntry(file, entry);
				}
			}
		}
	}
	
	protected void handledCopiedProjectFiles(IResourceDelta delta) {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getResource().getFullPath());
		
		if((null != file) && (null != file.getFileExtension()) && (XML_FILE_EXTENSION.equalsIgnoreCase(file.getFileExtension()))) {
			handleSystemFileCopy(file);
		}
	}

	protected void handleSystemFileCopy(final IFile file) {
		Scanner scanner;
		try {
			scanner = new Scanner(file.getContents());
			if(null != scanner.findWithinHorizon("<libraryElement:AutomationSystem", 0)){
				//it is an Automation system
				final IProject project = file.getProject();
				if(!file.getName().equals(file.getProject().getName() + ".xml")){ //$NON-NLS-1$
					WorkspaceJob job = new WorkspaceJob("Renaming system file") {
						public IStatus runInWorkspace(IProgressMonitor monitor) {
							// do the actual work in here
							
							IPath path = file.getProjectRelativePath();							
							path = path.removeLastSegments(1);
							path = path.append(project.getName() + SystemManager.SYSTEM_FILE_ENDING);
							try {
								file.move(path, true, null);
								//TODO model refactoring - should we remove the old system first?
								//a basic load should be sufficient to update the system configuration
								systemManager.loadProject(project);
							} catch (Exception e) {
								Activator.getDefault().logError(e.getMessage(), e);
							}
							return Status.OK_STATUS;
						}
					};
					job.setRule(project);
					job.schedule();
				}
			}
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	protected void handleFolderMove(IResourceDelta delta) {
		IContainer src = ResourcesPlugin.getWorkspace().getRoot().getFolder(delta.getMovedFromPath());
		Palette srcPalette = systemManager.getPalette(src.getProject());

		if (src.getParent().equals(delta.getResource().getParent())) {
			// rename folder
			PaletteGroup group = TypeLibrary.getPaletteGroup(srcPalette, src);
			if (null != group) {
				group.setLabel(delta.getResource().getName());
			}			
		} else {
			Palette dstPalette = systemManager.getPalette(delta.getResource()
					.getProject());

			movePaletteGroup(src, delta.getResource().getParent(), srcPalette, dstPalette);
		}

	}

	/**
	 * Handle folder move for palette groups
	 * 
	 * @param srcGroupFolder
	 *            the folder to be moved
	 * @param dstGroupFolder
	 *            the destination of the src folder (i.e., the new parent)
	 * @param srcPalette
	 * @param srcRootPaletteGroup
	 * @param dstPalette
	 * @param dstRootPaletteGroup
	 */
	private void movePaletteGroup(IContainer srcGroupFolder,
			IContainer dstGroupFolder, Palette srcPalette,
			Palette dstPalette) {
		PaletteGroup dstGroup = TypeLibrary.getPaletteGroup(dstPalette, dstGroupFolder);
		PaletteGroup srcGroup = TypeLibrary.getPaletteGroupWithAdd( srcPalette, srcGroupFolder);
		if ((null != dstGroup) && (null != srcGroup)) {
			srcGroup.getParentGroup().getSubGroups().remove(srcGroup);
			dstGroup.getSubGroups().add(srcGroup);
		}
	}

	private void handleFileMove(IResourceDelta delta) {
		IFile src = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(delta.getMovedFromPath());
		Palette srcPalette = systemManager.getPalette(src.getProject());

		if (src.getParent().equals(delta.getResource().getParent())) {
			handleFileRename(delta, src);
		} else {
			handleFileMovement(delta, src, srcPalette);
		}

		final AutomationSystem system = systemManager.getSystemForName(src
				.getProject().getName());
		if (null != system) {
			WorkspaceJob job = new WorkspaceJob("Save system: "
					+ system.getName() + " after type movement") {
				public IStatus runInWorkspace(IProgressMonitor monitor) {
					// do the actual work in here
					SystemManager.INSTANCE.saveSystem(system);
					return Status.OK_STATUS;
				}
			};
			job.setRule(src.getProject());
			job.schedule();

		}
	}

	private void handleFileMovement(IResourceDelta delta, IResource src,
			Palette srcPalette) {
		Palette dstPalette = systemManager.getPalette(delta.getResource()
				.getProject());
		PaletteGroup dstGroup = null;
		PaletteGroup srcGroup = null;

		srcGroup = TypeLibrary.getPaletteGroup(srcPalette, src.getParent());
		dstGroup = TypeLibrary.getPaletteGroupWithAdd(dstPalette, delta.getResource().getParent());

		PaletteEntry entry = null;
		if ((null != srcGroup) && (null != dstGroup)) {
			entry = srcGroup.getEntry(TypeLibrary.getTypeNameFromFileName(src
					.getName()));
			if (null != entry) {
				srcGroup.getEntries().remove(entry);
				dstGroup.addEntry(entry);
			}
		} else if (null == srcGroup) {
			// parent folder has been moved
			entry = dstGroup.getEntry(TypeLibrary.getTypeNameFromFileName(delta
					.getResource().getName()));
		}
		if (null != entry) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot()
					.getFile(delta.getResource().getFullPath());
			entry.setFile(file);
		}
	}

	private void handleFileRename(IResourceDelta delta, IFile src) {
		IFile file = ResourcesPlugin.getWorkspace().getRoot()
				.getFile(delta.getResource().getFullPath());
		Palette dstPalette = systemManager.getPalette(file.getProject());
		PaletteEntry entry = TypeLibrary.getPaletteEntry(dstPalette, src);
		updatePaletteEntry(file, entry);
	}

	private void updatePaletteEntry(final IFile newFile, final PaletteEntry entry) {
		if (null != entry) {
			String newTypeName = TypeLibrary.getTypeNameFromFile(newFile);
			entry.setLabel(newTypeName);
			entry.setFile(newFile);

			WorkspaceJob job = new WorkspaceJob("Save Renamed type: " + entry.getLabel()) {
				public IStatus runInWorkspace(IProgressMonitor monitor) {
					// do the actual work in here
					final LibraryElement type = entry.getType();
					if ((null != type) && // this means we couldn't load the type seems
							// like a problem in the type's XML file
							// TODO report on error
							(!newTypeName.equals(type.getName()))) {
						type.setName(newTypeName);
						CommonElementExporter.saveType(entry);
					}
					return Status.OK_STATUS;
				}
			};
			job.setRule(newFile.getProject());
			job.schedule();
		}
	}

	private void loadSystem(IProject project) {
		AutomationSystem system = systemManager.loadProject(project);
		if(null != system){
			if(!system.getName().equals(project.getName())){
				//we have been copied set the system name and correctly save it
				renameSystem(system, project);
			}
		}
		systemManager.notifyListeners();
	}
	
	private void handleProjectRename(final IResourceDelta delta) {
		IPath srcPath = delta.getMovedFromPath();
		
		if(null != srcPath){
		
			IResource src = ResourcesPlugin.getWorkspace().getRoot().getProject(srcPath.lastSegment());
			
			final AutomationSystem system = systemManager.getSystemForName(src.getName());
			IProject project = delta.getResource().getProject();
			if(null != system){
				renameSystem(system, project);
			}
		}
		
	}

	protected void renameSystem(final AutomationSystem system, final IProject project) {
		String newProjectName = project.getName();
		system.setName(newProjectName);
		
		IPath path = project.getLocation().append(system.getName() + SystemManager.SYSTEM_FILE_ENDING);
		URI uri = URI.createFileURI(path.toOSString());
		system.eResource().setURI(uri);
		system.setProject(project);     //update to the new project
		
		
		WorkspaceJob job = new WorkspaceJob("Save system configuration: " + newProjectName) {
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				// do the actual work in here
				SystemManager.INSTANCE.saveSystem(system);				
				return Status.OK_STATUS;
			}
		};
		job.setRule(project);
		job.schedule();
	}
	
	protected void handleProjectRemove(IResourceDelta delta) {
		IProject project = delta.getResource().getProject();
		AutomationSystem system = systemManager.getSystemForName(project.getName());
		if(null != system){
			closeAllEditors(system);			
			//remove system from system manager
			systemManager.removeSystem(system);
		}
	}
	
	private void closeAllEditors(final AutomationSystem refSystem) {
		//display related stuff needs to run in a display thread
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				EditorUtils.closeEditorsFiltered((IEditorPart editor) -> {
					return (editor instanceof ISystemEditor) && (refSystem.equals(((ISystemEditor)editor).getSystem()));
				});
			}
		});
		
	}
	private void closeAllFBTypeEditor(final PaletteEntry entry) {
		//display related stuff needs to run in a display thread
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				IFile file = entry.getFile();
				EditorUtils.closeEditorsFiltered( (IEditorPart editor) -> {
					IEditorInput input = editor.getEditorInput();
					return (input instanceof FileEditorInput) && (file.equals(((FileEditorInput) input).getFile()));							
				});				
			}
		});
		
	}
}
