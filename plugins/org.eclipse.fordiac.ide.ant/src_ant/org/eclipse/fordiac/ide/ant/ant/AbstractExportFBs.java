/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.ant.ant;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;

public abstract class AbstractExportFBs extends Task {

	protected String projectNameString;
	protected IWorkspace workspace;
	private IProject fordiacProject;
	protected String exportDirectory = getExportDirectoryDefault();

	public void setProjectName(final String value) {
		projectNameString = value;
	}

	protected void setFordiacProject(final IProject fordiacProject) {
		this.fordiacProject = fordiacProject;
	}

	protected IProject getFordiacProject() {
		return fordiacProject;
	}

	public void setExportDirectory(final String value) {
		exportDirectory = value;
	}

	@Override
	public final void execute() throws BuildException {
		if (projectNameString == null) {
			throw new BuildException("Project name not specified!"); //$NON-NLS-1$
		}

		workspace = ResourcesPlugin.getWorkspace();
		setFordiacProject(workspace.getRoot().getProject(projectNameString));
		checkFordiacProject();

		waitBuilderJobsComplete();

		final List<File> files = new ArrayList<>();

		getFBsFiles(files, getDirectory(), getSingleFBName(), getExcludeSubfolder());
		exportFiles(files);

	}

	protected void checkFordiacProject() {
		if (fordiacProject == null) {
			throw new BuildException("Project named '" + projectNameString + "' not in workspace in Workspace");//$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	protected abstract String getExportDirectoryDefault();

	protected abstract void exportFiles(final List<File> files);

	protected File getDirectory() {
		throw new BuildException("Implement getDirectory in subclass");
	}

	protected String getSingleFBName() {
		throw new BuildException("Implement getSingleFBName in subclass");
	}

	protected List<String> getExcludeSubfolder() {
		throw new BuildException("Implement getExcludeSubfolder in subclass");
	}

	// recursively call directories and save fbt Files
	public List<File> getFBsFiles(final List<File> files, final File dir, final String singleFBName,
			final List<String> excludeSubfolder) {
		if (!dir.isDirectory()
				&& (dir.getName().toUpperCase().endsWith(TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT)
						|| dir.getName().toUpperCase().endsWith(TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT)
						|| dir.getName().toUpperCase().endsWith(TypeLibraryTags.FC_TYPE_FILE_ENDING_WITH_DOT))
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

	public static File findFolderInProject(final File dir, final String dirName) {
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

	public static void waitBuilderJobsComplete() {
		Job[] jobs = Job.getJobManager().find(null); // get all current scheduled jobs

		while (buildJobExists(jobs)) {
			try {
				Thread.sleep(50);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			jobs = Job.getJobManager().find(null); // update the job list
		}
	}

	private static boolean buildJobExists(final Job[] jobs) {
		final Optional<Job> findAny = Arrays.stream(jobs)
				.filter(j -> (j.getState() != Job.NONE && j.getName().startsWith("Building"))) //$NON-NLS-1$
				.findAny();
		return findAny.isPresent();
	}
}
