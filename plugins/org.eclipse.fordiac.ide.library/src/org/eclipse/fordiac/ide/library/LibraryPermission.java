/*******************************************************************************
 * Copyright (c) 2025, 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *   Mario Kastner - extracted code from LibraryManager
 *******************************************************************************/

package org.eclipse.fordiac.ide.library;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class LibraryPermission {

	private static final Set<PosixFilePermission> PERMISSIONS = Set.of(PosixFilePermission.OWNER_WRITE,
			PosixFilePermission.GROUP_WRITE, PosixFilePermission.OTHERS_WRITE);

	/**
	 * Sets a specific path read only
	 *
	 * @param path Path to set read only
	 */
	public static void setPathReadOnly(final Path path) {
		setPathEditable(path, false);
	}

	/**
	 * Sets a specific path editable
	 *
	 * @param path Path to set editable
	 */
	public static void setPathEditable(final Path path) {
		setPathEditable(path, true);
	}

	public static void setLibReadOnly(final Path libraryPath) {
		final WorkspaceJob job = new WorkspaceJob(Messages.LibraryManager_SetStandardLibrariesReadOnly) {

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {

				try {
					Files.walkFileTree(libraryPath, new FileVisitor<Path>() {

						@Override
						public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs)
								throws IOException {
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs)
								throws IOException {
							setPathReadOnly(file);
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFileFailed(final Path file, final IOException exc)
								throws IOException {
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult postVisitDirectory(final Path dir, final IOException exc)
								throws IOException {
							return FileVisitResult.CONTINUE;
						}
					});
				} catch (final IOException e) {
					// empty
				}
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(final Object family) {
				return family == LibraryManager.FAMILY_FORDIAC_LIBRARY;
			}
		};
		job.setRule(null);
		job.setPriority(Job.DECORATE);
		job.schedule();
	}

	private static void setPathEditable(final Path path, final boolean editable) {
		final DosFileAttributeView dosView = Files.getFileAttributeView(path, DosFileAttributeView.class);
		if (dosView != null) {
			try {
				dosView.setReadOnly(!editable);
			} catch (final IOException e) {
				// empty
			}
		}
		final PosixFileAttributeView posixView = Files.getFileAttributeView(path, PosixFileAttributeView.class);
		if (posixView != null) {
			try {
				final Set<PosixFilePermission> permissions = posixView.readAttributes().permissions();
				if (editable) {
					permissions.addAll(PERMISSIONS);
				} else {
					permissions.removeAll(PERMISSIONS);
				}
				posixView.setPermissions(permissions);
			} catch (final IOException e) {
				// empty
			}
		}
	}

	private LibraryPermission() {
		throw new UnsupportedOperationException();
	}

}
