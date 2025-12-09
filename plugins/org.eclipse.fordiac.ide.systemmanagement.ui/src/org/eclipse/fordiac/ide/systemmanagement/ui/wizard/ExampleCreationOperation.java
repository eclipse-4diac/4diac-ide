/*******************************************************************************
 * Copyright (c) 2006, 2025 IBM Corporation and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Alois Zoitl     - the content of this class is based on the
 *                       org.eclipse.gef.examples.ui.pde.internal.wizards.ProjectUnzipperNewWizard
 *                       from the Eclipse GEF Classic project *
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class ExampleCreationOperation extends WorkspaceModifyOperation {

	final File exampleZipFile;
	final String projectName;
	final IPath projectPath;

	public ExampleCreationOperation(final File exampleZipFile, final String projectName, final IPath projectPath) {
		this.exampleZipFile = exampleZipFile;
		this.projectName = projectName;
		this.projectPath = projectPath;
	}

	@Override
	protected void execute(final IProgressMonitor monitor)
			throws CoreException, InvocationTargetException, InterruptedException {
		try {

			final IWorkspace workspace = ResourcesPlugin.getWorkspace();
			final IProject project = workspace.getRoot().getProject(projectName);

			final SubMonitor subMonitor = SubMonitor.convert(monitor,
					MessageFormat.format(Messages.New4diacExampleWizard_CreatingExample,
							New4diacExampleProjectPage.getExampleName(exampleZipFile)),
					140);

			// If the project does not exist, we will create it and populate it.
			if (!project.exists()) {
				createProject(subMonitor, projectPath, project, exampleZipFile);
			}

			// Now, we ensure that the project is open.
			project.open(subMonitor.newChild(10));

			renameProject(project, projectName);
		} catch (final IOException e) {
			throw new CoreException(Status.error("IO issues in example project creation!", e)); //$NON-NLS-1$
		}
	}

	private static void createProject(final SubMonitor subMonitor, final IPath projectPath, final IProject project,
			final File exampleZipFile) throws IOException, InterruptedException, CoreException {
		final String projectFolder = projectPath.toOSString() + File.separator + project.getName();
		final File projectFolderFile = new File(projectFolder);
		projectFolderFile.mkdirs();
		subMonitor.worked(10);

		// Copy plug-in project code
		extractProject(projectFolderFile, exampleZipFile, subMonitor.newChild(100));

		if (subMonitor.isCanceled()) {
			throw new InterruptedException();
		}

		if (projectPath.equals(project.getWorkspace().getRoot().getLocation())) {
			project.create(subMonitor.newChild(10));
		} else {
			final IProjectDescription desc = project.getWorkspace().newProjectDescription(project.getName());
			desc.setLocation(new Path(projectFolder));

			project.create(desc, subMonitor.newChild(10));
		}
	}

	private static void extractProject(final File projectFolderFile, final File exampleZipFile,
			final IProgressMonitor monitor) throws IOException, InterruptedException {

		// Walk each element and unzip
		try (ZipFile zipFile = new ZipFile(exampleZipFile)) {
			// Allow for a hundred work units
			monitor.beginTask(Messages.New4diacExampleWizard_CopyingContentIntoNewProject, zipFile.size());
			unzip(zipFile, projectFolderFile, monitor);
		} finally {
			monitor.done();
		}
	}

	private static void unzip(final ZipFile zipFile, final File projectFolderFile, final IProgressMonitor monitor)
			throws IOException, InterruptedException {

		final Enumeration<? extends ZipEntry> e = zipFile.entries();

		while (e.hasMoreElements()) {
			final ZipEntry zipEntry = e.nextElement();
			final File file = new File(projectFolderFile, zipEntry.getName());

			if (!zipEntry.isDirectory()) {

				// Copy files (and make sure parent directory exist)
				final File parentFile = file.getParentFile();
				if (null != parentFile && !parentFile.exists()) {
					parentFile.mkdirs();
				}

				try (InputStream is = zipFile.getInputStream(zipEntry); OutputStream os = new FileOutputStream(file);) {
					is.transferTo(os);
				}
			}

			monitor.worked(1);

			if (monitor.isCanceled()) {
				throw new InterruptedException();
			}
		}
	}

	private static void renameProject(final IProject project, final String projectName) throws CoreException {
		final IProjectDescription description = project.getDescription();
		description.setName(projectName);
		project.move(description, IResource.FORCE | IResource.SHALLOW, null);
	}

}
