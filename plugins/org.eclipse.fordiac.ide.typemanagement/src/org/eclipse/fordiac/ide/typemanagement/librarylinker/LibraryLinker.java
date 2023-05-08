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
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;

public class LibraryLinker {
	
	private static final String WORKSPACE_ROOT = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString();
	private static final String EXTRACTED_LIB_DIRECTORY = ".lib";
	private static final String PACKAGE_DOWNLOAD_DIRECTORY = ".download"; 
	private static final String ZIP_SUFFIX = ".zip";
	private static final File[] EMPTY_ARRAY = new File[0];
	private static final String TYPE_LIB = "Type Library";

	public void extractLibrary(File file, StructuredSelection selection) throws IOException {
		byte[] buffer = new byte[1024];
		try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
			ZipEntry entry = zipInputStream.getNextEntry();
			while(entry != null) {
				File newFile = newFile(new File(Paths.get(WORKSPACE_ROOT, EXTRACTED_LIB_DIRECTORY).toString()), entry);
				if (entry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
			            throw new IOException("Failed to create directory " + newFile);
			        }
				} else {
					File parent = newFile.getParentFile();
			        if (!parent.isDirectory() && !parent.mkdirs()) {
			            throw new IOException("Failed to create directory " + parent);
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
		// Getting the parent's name because we want package-version name when importing into the Type Library
		importLibrary(file.getParentFile().getName(), getProjectName(selection));
	}
	
	public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
	    File destFile = new File(destinationDir, zipEntry.getName());

	    String destDirPath = destinationDir.getCanonicalPath();
	    String destFilePath = destFile.getCanonicalPath();

	    if (!destFilePath.startsWith(destDirPath + File.separator)) {
	        throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
	    }
	    return destFile;
	}
	
	private String getProjectName(StructuredSelection selection) {
		if (!selection.isEmpty()) {
			if (selection.getFirstElement() instanceof IProject project) {
				return project.getName();
			} else if (selection.getFirstElement() instanceof IFolder folder) {
				return folder.getParent().getName();
			}
		}
		return "";
	}
	
	private File[] listLibDirectories(String directory) {
		File libDir = new File(Paths.get(WORKSPACE_ROOT, directory).toString());
		if (libDir.exists()) {
			return libDir.listFiles();
		}
		return EMPTY_ARRAY;
	}
	
	public void importLibrary(String directory, String projectName) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot workspaceRoot = workspace.getRoot();
		// Make/find a folder inside of the Type Library
		IFolder directoryForExtraction = workspaceRoot.getProject(projectName).getFolder(TYPE_LIB).getFolder(directory);
		if (!directoryForExtraction.exists()) {
			java.nio.file.Path path = Paths.get(WORKSPACE_ROOT, EXTRACTED_LIB_DIRECTORY, directory, "typelib");
			IPath pathToActualData = new Path(path.toString());
			if (Files.exists(path)) {
				try {
					directoryForExtraction.createLink(pathToActualData, IResource.BACKGROUND_REFRESH, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		} else {
			MessageDialog.openWarning(null, Messages.warning, Messages.typeLibraryHasAlreadyBeenExtracted);
		}
	} 
	
	public File[] listDirectoriesContainingArchives() {
		File[] directory = listLibDirectories(PACKAGE_DOWNLOAD_DIRECTORY);
		return Stream.of(directory).filter(file -> file.isDirectory() && 
					!Stream.of(file.listFiles())
					.filter(child -> child.getName().endsWith(ZIP_SUFFIX))
					.toList().isEmpty())
				.toArray(File[]::new);
	}
	
	
	public File[] listExtractedFiles() {
		File[] extractedFiles = listLibDirectories(EXTRACTED_LIB_DIRECTORY);
		return EMPTY_ARRAY;
	}
}
