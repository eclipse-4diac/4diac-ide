/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.refactoring;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.export.Messages;
import org.eclipse.fordiac.ide.export.utils.AdditionalSourceDirectories;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.osgi.service.prefs.BackingStoreException;

/**
 * A change that stores the additional source directories of a project.
 */
public class UpdateSourceDirectoriesChange extends Change {

	private final IProject project;
	private final UnaryOperator<List<IPath>> update;
	private final UnaryOperator<IPath> updateOutputDirectory;

	private UpdateSourceDirectoriesChange(final IProject project, final UnaryOperator<List<IPath>> update) {
		this(project, update, null);
	}

	private UpdateSourceDirectoriesChange(final IProject project, final UnaryOperator<List<IPath>> update,
			final UnaryOperator<IPath> updateOutputDirectory) {
		this.project = project;
		this.update = update;
		this.updateOutputDirectory = updateOutputDirectory;
	}

	/** Checks whether an additional source directory refers to the given folder. */
	public static boolean isReferenced(final IFolder folder) {
		final IPath folderPath = folder.getProjectRelativePath();
		return AdditionalSourceDirectories.getSourceDirectories(folder.getProject()).stream()
				.anyMatch(folderPath::isPrefixOf);
	}

	/**
	 * Creates a change relocating all additional source directories that refer to
	 * the given folder.
	 *
	 * The stored directories are updated when the change is performed, therefore
	 * the changes of several folders of the same project do not overwrite each
	 * other.
	 *
	 * @param folder   The folder that is refactored.
	 * @param relocate Maps a referring source directory to its new location or to
	 *                 {@code null} if it shall be removed.
	 */
	public static Change create(final IFolder folder, final UnaryOperator<IPath> relocate) {
		final IPath folderPath = folder.getProjectRelativePath();
		return new UpdateSourceDirectoriesChange(folder.getProject(), createUpdate(folderPath, relocate));
	}

	/**
	 * Creates a change relocating the additional source directories and the output
	 * directory that refer to the given folder.
	 *
	 * @param folder                  The folder that is refactored.
	 * @param relocate                Maps a referring source directory to its new
	 *                                location or to {@code null} if it shall be
	 *                                removed.
	 * @param relocateOutputDirectory Maps the referring output directory to its new
	 *                                location.
	 */
	public static Change create(final IFolder folder, final UnaryOperator<IPath> relocate,
			final UnaryOperator<IPath> relocateOutputDirectory) {
		final IPath folderPath = folder.getProjectRelativePath();
		return new UpdateSourceDirectoriesChange(folder.getProject(), createUpdate(folderPath, relocate),
				outputDirectory -> folderPath.isPrefixOf(outputDirectory)
						? relocateOutputDirectory.apply(outputDirectory)
						: outputDirectory);
	}

	private static UnaryOperator<List<IPath>> createUpdate(final IPath folderPath,
			final UnaryOperator<IPath> relocate) {
		return directories -> directories.stream()
				.map(directory -> folderPath.isPrefixOf(directory) ? relocate.apply(directory) : directory)
				.filter(Objects::nonNull).toList();
	}

	@Override
	public String getName() {
		return MessageFormat.format(Messages.Refactoring_UpdateSourceDirectories, project.getName());
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// nothing to validate here
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) {
		if (!project.isAccessible()) {
			return RefactoringStatus.createFatalErrorStatus(MessageFormat
					.format(Messages.Refactoring_UpdateSourceDirectoriesFailed, project.getName()));
		}
		return new RefactoringStatus();
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		final List<IPath> previousSourceDirectories = AdditionalSourceDirectories.getSourceDirectories(project);
		final List<IPath> updatedSourceDirectories = update.apply(previousSourceDirectories);
		final UnaryOperator<List<IPath>> restoreSourceDirectories = directories -> previousSourceDirectories;
		try {
			if (updateOutputDirectory != null) {
				final IPath previousOutputDirectory = AdditionalSourceDirectories.getOutputDirectory(project);
				final IPath updatedOutputDirectory = updateOutputDirectory.apply(previousOutputDirectory);
				if (!updatedOutputDirectory.equals(previousOutputDirectory)) {
					AdditionalSourceDirectories.setExportDirectories(project, updatedOutputDirectory,
							updatedSourceDirectories);
					return new UpdateSourceDirectoriesChange(project, restoreSourceDirectories,
							outputDirectory -> previousOutputDirectory);
				}
			}
			AdditionalSourceDirectories.setSourceDirectories(project, updatedSourceDirectories);
		} catch (final BackingStoreException e) {
			throw new CoreException(Status.error(
					MessageFormat.format(Messages.Refactoring_UpdateSourceDirectoriesFailed, project.getName()), e));
		}
		return new UpdateSourceDirectoriesChange(project, restoreSourceDirectories);
	}

	@Override
	public Object getModifiedElement() {
		return project;
	}
}
