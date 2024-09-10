/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *   Ernst Blecha - refactoring of base classes for ant tasks
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.export.ExportException;
import org.eclipse.fordiac.ide.export.ExportFilter;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public abstract class AbstractFBTask extends Task {
	private String projectNameString;
	private IProject fordiacProject;
	protected IWorkspace workspace;
	protected String exportDirectory = getExportDirectoryDefault();
	protected boolean preserveDirectoryStructure = false;
	protected ExportFilter filter = getExportFilter();

	protected abstract ExportFilter getExportFilter();

	protected abstract File getDirectory();

	protected abstract String getSingleFBName();

	protected abstract List<String> getExcludeSubfolder();

	protected abstract String getExportDirectoryDefault();

	public void setProjectName(final String value) {
		projectNameString = value;
	}

	public String getProjectNameString() {
		return projectNameString;
	}

	public void setExportDirectory(final String value) {
		exportDirectory = value;
	}

	public void setPreserveDirectoryStructure(final boolean preserveDirectoryStructure) {
		this.preserveDirectoryStructure = preserveDirectoryStructure;
	}

	@Override
	public final void execute() throws BuildException {
		if (getProjectNameString() == null) {
			throw new BuildException("Project name not specified!"); //$NON-NLS-1$
		}
		workspace = ResourcesPlugin.getWorkspace();
		setFordiacProject(workspace.getRoot().getProject(getProjectNameString()));
		checkFordiacProject();

		Import4diacProject.waitBuilderJobsComplete();

		final List<File> files = new ArrayList<>();

		getFBsFiles(files, getDirectory(), getSingleFBName(), getExcludeSubfolder());
		exportFiles(files);
	}

	protected void exportFiles(final List<File> files) {
		if (!files.isEmpty()) {
			final File folder = new File(exportDirectory);
			if (!folder.exists()) {
				folder.mkdir();
			}
			for (final var file : files) {
				final IPath location = Path.fromOSString(file.getAbsolutePath());
				final IFile ifile = workspace.getRoot().getFileForLocation(location);
				var directory = Path.fromOSString(folder.getAbsolutePath()).toFile().toPath();
				if (preserveDirectoryStructure) {
					directory = directory
							.resolve(ifile.getProjectRelativePath().removeLastSegments(1).toFile().toPath());
					try {
						Files.createDirectories(directory);
					} catch (final IOException e) {
						throw new BuildException("Can not create directory structure for export", e);//$NON-NLS-1$
					}
				}
				exportFile(directory.toFile(), ifile);
			}
		}
	}

	protected void exportFile(final File folder, final IFile file) {
		try {
			filter.export(file, folder.getPath(), true);
			log(file.toString());// print it in console for ANT Tasks
			if (!filter.getErrors().isEmpty()) {
				filter.getErrors().forEach(this::log);
				throw new BuildException("Could not export without errors"); //$NON-NLS-1$
			}
		} catch (final ExportException e) {
			throw new BuildException("Could not export: " + e.getMessage(), e);//$NON-NLS-1$
		}
	}

	protected void setFordiacProject(final IProject fordiacProject) {
		this.fordiacProject = fordiacProject;
	}

	protected IProject getFordiacProject() {
		return fordiacProject;
	}

	protected void checkFordiacProject() {
		if (fordiacProject == null) {
			throw new BuildException("Project named '" + projectNameString + "' not in workspace in Workspace");//$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	protected static boolean matchesFileExtension(final File file, final Stream<String> allowedFiletypes) {
		return allowedFiletypes.anyMatch(extension -> file.getName().toUpperCase().endsWith(extension));
	}

	@SuppressWarnings("static-method")
	protected boolean isFilteredFiletype(final File file) {
		final var allowedFiletypes = Stream.of(//
				TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT, //
				TypeLibraryTags.FC_TYPE_FILE_ENDING_WITH_DOT, //
				TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT, //
				TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING_WITH_DOT, //
				TypeLibraryTags.GLOBAL_CONST_FILE_ENDING_WITH_DOT, //
				TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING_WITH_DOT);
		return matchesFileExtension(file, allowedFiletypes);
	}

	// recursively call directories and save fbt Files
	protected List<File> getFBsFiles(final List<File> files, final File dir, final String singleFBName,
			final List<String> excludeSubfolder) {
		if (!dir.isDirectory() && isFilteredFiletype(dir)
				&& (singleFBName == null || singleFBName.equals(dir.getName()))) {
			files.add(dir);
			return files;
		}
		if (dir.listFiles() != null) {
			for (final File file : dir.listFiles()) {
				if (!excludeSubfolder.contains(file.getName())) {
					getFBsFiles(files, file, singleFBName, excludeSubfolder);
				}
			}
		}
		return files;
	}

	protected static File findFolderInProject(final File dir, final String dirName) {
		if (dir.isDirectory() && dir.getName().equals(dirName)) {
			return dir;
		}
		if (dir.listFiles() != null) {
			File foundFile = null;
			for (final File file : dir.listFiles()) {
				foundFile = findFolderInProject(file, dirName);
				if (foundFile != null) {
					return foundFile;
				}
			}
		}
		return null;
	}
}
