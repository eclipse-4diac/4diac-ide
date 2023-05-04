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
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.ResourcesPlugin;

public class LibraryLinker {
	
	private static final String PATH = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPortableString();
	private static final String EXTRACTED_LIB_DIRECTORY = ".lib";
	private static final String FBLIB_DIRECTORY = ".fblib";
	private static final String ZIP_SUFFIX = ".zip";
	private static final File[] EMPTY_ARRAY = new File[0];

	public void extractLibrary(File file) throws IOException {
		byte[] buffer = new byte[1024];
		try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
			ZipEntry entry = zipInputStream.getNextEntry();
			while(entry != null) {
				File newFile = newFile(new File(Paths.get(PATH, EXTRACTED_LIB_DIRECTORY).toString()), entry);
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
	
	private File[] listLibDirectories(String directory) {
		File libDir = new File(Paths.get(PATH, directory).toString());
		if (libDir.exists()) {
			return libDir.listFiles();
		}
		return EMPTY_ARRAY;
	}
	
	public void importLibrary() {
		// For connecting Eclipse and a type library programmatically 
	} 
	
	public File[] listDirectoriesContainingArchives() {
		File[] directory = listLibDirectories(FBLIB_DIRECTORY);
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
