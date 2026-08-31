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
package org.eclipse.fordiac.ide.export.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;

public final class AdditionalSourceDirectories {

	private static final String SERIALIZED_PATH_SEPARATOR = "\u0000"; //$NON-NLS-1$

	/** directory names that need no quoting when written to a generated build file */
	private static final Pattern VALID_SEGMENT = Pattern.compile("[A-Za-z0-9_.+-]+"); //$NON-NLS-1$

	public static List<IPath> parsePaths(final String value) {
		if (value == null || value.isEmpty()) {
			return List.of();
		}
		return Arrays.stream(value.split(SERIALIZED_PATH_SEPARATOR, -1)).filter(path -> !path.isEmpty())
				.map(path -> (IPath) new Path(path)).toList();
	}

	public static String formatPaths(final Collection<IPath> paths) {
		return String.join(SERIALIZED_PATH_SEPARATOR, paths.stream().map(IPath::toPortableString).toList());
	}

	public static boolean validatePaths(final IProject project, final IPath outputDirectory,
			final List<IPath> additionalSourceDirectories, final boolean requireExistingDirectories) {
		if (!isValidProjectRelativePath(outputDirectory)) {
			return false;
		}

		for (final IPath sourceDirectory : additionalSourceDirectories) {
			if (sourceDirectory.equals(outputDirectory) || !outputDirectory.isPrefixOf(sourceDirectory)
					|| !isValidRelativeDirectory(java.nio.file.Path
							.of(sourceDirectory.makeRelativeTo(outputDirectory).toPortableString()))) {
				return false;
			}

			final IFolder folder = project.getFolder(sourceDirectory);
			final boolean exists = folder.exists();
			if ((requireExistingDirectories && !exists) || (exists && (folder.isLinked(IResource.CHECK_ANCESTORS)
					|| folder.isVirtual() || folder.getLocation() == null))) {
				return false;
			}
		}

		return true;
	}

	public static void cleanOutputDirectory(final IFolder outputDirectory,
			final List<IPath> additionalSourceDirectories, final IProgressMonitor monitor) throws CoreException {
		final SubMonitor progress = SubMonitor.convert(monitor, IProgressMonitor.UNKNOWN);
		if (additionalSourceDirectories.isEmpty()) {
			if (outputDirectory.exists()) {
				outputDirectory.delete(IResource.FORCE, progress);
			}
			return;
		}

		outputDirectory.refreshLocal(IResource.DEPTH_INFINITE, progress);
		if (!outputDirectory.exists()) {
			return;
		}

		for (final IResource member : outputDirectory.members()) {
			cleanResource(member, additionalSourceDirectories, progress);
		}
	}

	private static void cleanResource(final IResource resource, final List<IPath> additionalSourceDirectories,
			final SubMonitor monitor) throws CoreException {
		checkCanceled(monitor);
		final IPath resourcePath = resource.getProjectRelativePath();
		if (additionalSourceDirectories.contains(resourcePath)) {
			return;
		}
		if (resource instanceof final IFolder folder
				&& additionalSourceDirectories.stream().anyMatch(source -> resourcePath.isPrefixOf(source))) {
			for (final IResource member : folder.members()) {
				cleanResource(member, additionalSourceDirectories, monitor);
			}
			return;
		}
		resource.delete(IResource.FORCE, monitor);
	}

	/**
	 * Checks whether the directory is a non-empty relative path without traversal
	 * segments and with names that need no quoting in generated build files.
	 *
	 * This is the single definition of a valid additional source directory and is
	 * also used by export filters working on {@link java.nio.file.Path}.
	 */
	public static boolean isValidRelativeDirectory(final java.nio.file.Path directory) {
		return directory != null && !directory.isAbsolute() && directory.getNameCount() > 0
				&& StreamSupport.stream(directory.spliterator(), false).map(Object::toString)
						.allMatch(segment -> isValidSegment(segment) && VALID_SEGMENT.matcher(segment).matches());
	}

	/** the output directory itself is not written to generated build files */
	private static boolean isValidProjectRelativePath(final IPath path) {
		return path != null && !path.isAbsolute() && path.getDevice() == null && path.segmentCount() > 0
				&& Arrays.stream(path.segments()).allMatch(AdditionalSourceDirectories::isValidSegment);
	}

	private static boolean isValidSegment(final String segment) {
		return !segment.isEmpty() && !".".equals(segment) && !"..".equals(segment); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static void checkCanceled(final IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	private AdditionalSourceDirectories() {
		throw new UnsupportedOperationException();
	}
}
