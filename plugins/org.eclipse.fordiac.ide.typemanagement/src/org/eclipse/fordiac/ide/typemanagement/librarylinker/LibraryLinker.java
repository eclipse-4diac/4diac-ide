/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.librarylinker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.util.FBUpdater;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

public class LibraryLinker {

	private static final SystemManager SYSTEM_MANAGER = SystemManager.INSTANCE;
	private static final String LOGIC = "Logic"; //$NON-NLS-1$
	private static final String LIB_TYPELIB_FOLDER_NAME = "typelib"; //$NON-NLS-1$
	private static final String WORKSPACE_ROOT = ResourcesPlugin.getWorkspace().getRoot().getRawLocation()
			.toPortableString();
	private static final String EXTRACTED_LIB_DIRECTORY = ".lib"; //$NON-NLS-1$
	private static final String PACKAGE_DOWNLOAD_DIRECTORY = ".download"; //$NON-NLS-1$
	private static final String ZIP_SUFFIX = ".zip"; //$NON-NLS-1$
	private static final File[] EMPTY_ARRAY = new File[0];
	private static final String TYPE_LIB = "Type Library"; //$NON-NLS-1$
	private static final IPath WORKSPACE_REL_EXTRACTED_LIB_DIR = new Path("WORKSPACE_LOC") //$NON-NLS-1$
			.append(EXTRACTED_LIB_DIRECTORY);

	private IProject selectedProject;
	private List<TypeEntry> cashedTypes;
	private TypeLibrary typeLibrary;
	private boolean isNewVersion;

	public void extractLibrary(final File file, final StructuredSelection selection) throws IOException {
		final byte[] buffer = new byte[1024];
		try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
			ZipEntry entry = zipInputStream.getNextEntry();
			while (entry != null) {
				final File newFile = newFile(new File(Paths.get(WORKSPACE_ROOT, EXTRACTED_LIB_DIRECTORY).toString()),
						entry);
				if (entry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
						throw new IOException("Failed to create directory " + newFile); //$NON-NLS-1$
					}
				} else {
					final File parent = newFile.getParentFile();
					if (!parent.isDirectory() && !parent.mkdirs()) {
						throw new IOException("Failed to create directory " + parent); //$NON-NLS-1$
					}
					try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
						int len;
						while ((len = zipInputStream.read(buffer)) > 0) {
							fileOutputStream.write(buffer, 0, len);
						}
					}
				}
				entry = zipInputStream.getNextEntry();
			}
		}
		setSelectedProject(selection);

		// Parent's name because we want package-version name when importing
		importLibrary(file.getParentFile().getName());

	}

	public static File newFile(final File destinationDir, final ZipEntry zipEntry) throws IOException {
		final File destFile = new File(destinationDir, zipEntry.getName());

		final String destDirPath = destinationDir.getCanonicalPath();
		final String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName()); //$NON-NLS-1$
		}
		return destFile;
	}

	public void setSelectedProject(final StructuredSelection selection) {
		if (!selection.isEmpty()) {
			if (selection.getFirstElement() instanceof final IProject project) {
				selectedProject = project;
				typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(selectedProject);
			}
			if ((selection.getFirstElement() instanceof final IFolder folder)
					&& (folder.getParent() instanceof final IProject project)) {
				selectedProject = project;
				typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(selectedProject);
			}
		}
	}

	private static File[] listLibDirectories(final String directory) {
		final File libDir = new File(Paths.get(WORKSPACE_ROOT, directory).toString());
		if (libDir.exists()) {
			return libDir.listFiles();
		}
		return EMPTY_ARRAY;
	}

	public void importLibrary(final String directory) {
		SYSTEM_MANAGER.removeFordiacChangeListener();
		// Make a folder inside of the Type Library
		final IFolder directoryForNewLibVersion = selectedProject.getFolder(TYPE_LIB).getFolder(directory);
		if (!directoryForNewLibVersion.exists()) {
			final java.nio.file.Path path = Paths.get(WORKSPACE_ROOT, EXTRACTED_LIB_DIRECTORY, directory,
					LIB_TYPELIB_FOLDER_NAME);
			if (Files.exists(path)) {
				try {
					final IPath libPath = WORKSPACE_REL_EXTRACTED_LIB_DIR.append(directory)
							.append(LIB_TYPELIB_FOLDER_NAME);
					removeOldLibVersion(findLinkedLibs(), directory);
					directoryForNewLibVersion.createLink(libPath, IResource.NONE, null);
					createTypeEntriesManually(directoryForNewLibVersion);
					if (isNewVersion) {
						// We can only update if there was a version before
						updateFBInstancesWithNewTypeVersion();
					}
					SYSTEM_MANAGER.addFordiacChangeListener();
				} catch (final CoreException e) {
					MessageDialog.openWarning(null, Messages.Warning,
							MessageFormat.format(Messages.ImportFailedOnLinkCreation, e.getMessage()));
				}
			} else {
				MessageDialog.openWarning(null, Messages.Warning, Messages.ImportFailed);
			}
		} else {
			MessageDialog.openWarning(null, Messages.Warning, Messages.typeLibraryHasAlreadyBeenExtracted);
		}
	}

	private void removeOldLibVersion(final List<String> importedLibs, final String newDirectoryName) {
		for (final String nameOfImportedLib : importedLibs) {
			final String[] nameAndVersionSplit = nameOfImportedLib.split("-"); //$NON-NLS-1$
			if (newDirectoryName.contains(nameAndVersionSplit[0])) {
				isNewVersion = true;
				MessageDialog.openWarning(null, Messages.Warning,
						Messages.NewVersionOf + nameAndVersionSplit[0] + " " + Messages.WillBeImported); //$NON-NLS-1$
				final IFolder oldFolder = selectedProject.getFolder(TYPE_LIB).getFolder(nameOfImportedLib);
				try {
					cashedTypes = cashOldTypes(nameOfImportedLib);
					// Should only remove the link but keep the resource on disk
					oldFolder.delete(true, null);

					// Remove the entries manually
					cashedTypes.forEach(typeEntry -> TypeLibraryManager.INSTANCE.getTypeLibrary(selectedProject)
							.removeTypeEntry(typeEntry));
					SYSTEM_MANAGER.notifyListeners();

				} catch (final CoreException e) {
					MessageDialog.openWarning(null, Messages.Warning, Messages.OldTypeLibVersionCouldNotBeDeleted);
				}
			}
		}
	}

	private void createTypeEntriesManually(final IContainer container) {
		try {
			final IResource[] members = container.members();
			for (final IResource resource : members) {
				if (resource instanceof final IFolder folder) {
					createTypeEntriesManually(folder);
				}
				if (resource instanceof final IFile file) {
					typeLibrary.createTypeEntry(file);
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

	}

	private void updateFBInstancesWithNewTypeVersion() {
		Display.getDefault().syncExec(() -> {
			SYSTEM_MANAGER.getProjectSystems(selectedProject)
					.forEach(autoSys -> autoSys.getApplication().forEach(app -> {
						final Command cmd = FBUpdater.updateAllInstances(app.getFBNetwork(), cashedTypes, typeLibrary);
						final IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
								.getActiveEditor();
						if (editor == null) {
							cmd.execute();
						} else {
							editor.getAdapter(CommandStack.class).execute(cmd);
						}
					}));
		});
	}

	private List<TypeEntry> cashOldTypes(final String oldVersion) {
		try (final Stream<java.nio.file.Path> stream = Files
				.list(Paths.get(WORKSPACE_ROOT, EXTRACTED_LIB_DIRECTORY, oldVersion, LIB_TYPELIB_FOLDER_NAME, LOGIC))) {
			return stream.map(path -> TypeLibraryManager.INSTANCE.getTypeFromLinkedFile(selectedProject,
					path.getFileName().toString())).filter(Optional::isPresent).map(Optional::get).toList();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	private List<String> findLinkedLibs() {
		if (selectedProject == null) {
			return Collections.emptyList();
		}
		final LinkedList<IResource> resources = new LinkedList<>();
		try {
			selectedProject.accept(resource -> {
				if (resource.isLinked() && !resource.isVirtual()) {
					resources.add(resource);
				}
				return true;
			});
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		return resources.stream().map(IResource::getName).toList();
	}

	public static File[] listDirectoriesContainingArchives() {
		final File[] directory = listLibDirectories(PACKAGE_DOWNLOAD_DIRECTORY);
		return Stream.of(directory).filter(file -> file.isDirectory() && !Stream.of(file.listFiles())
				.filter(child -> child.getName().endsWith(ZIP_SUFFIX)).toList().isEmpty()).toArray(File[]::new);
	}

	public static File[] listExtractedFiles() {
		return listLibDirectories(EXTRACTED_LIB_DIRECTORY);
	}
}
