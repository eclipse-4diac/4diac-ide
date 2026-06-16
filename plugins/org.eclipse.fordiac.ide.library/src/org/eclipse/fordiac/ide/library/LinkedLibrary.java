/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.osgi.framework.Version;

/**
 * Represents a linked library folder within a project's library directory.
 */
public class LinkedLibrary {

	/**
	 * Returns all linked standard libraries of the given project.
	 *
	 * @param project  the project to inspect
	 * @param progress the progress monitor
	 * @return a stream of linked standard libraries
	 * @throws CoreException if the project contents cannot be accessed
	 */
	public static Stream<LinkedLibrary> getStandard(final IProject project, final IProgressMonitor progress)
			throws CoreException {
		return collectLinkedFolders(project, TypeLibraryTags.STANDARD_LIB_FOLDER_NAME, SubMonitor.convert(progress))
				.stream();
	}

	/**
	 * Returns all linked external libraries of the given project.
	 *
	 * @param project  the project to inspect
	 * @param progress the progress monitor
	 * @return a stream of linked external libraries
	 * @throws CoreException if the project contents cannot be accessed
	 */
	public static Stream<LinkedLibrary> getExternal(final IProject project, final IProgressMonitor progress)
			throws CoreException {
		return collectLinkedFolders(project, TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME, SubMonitor.convert(progress))
				.stream();
	}

	/**
	 * Returns all linked libraries of the given project.
	 *
	 * @param project  the project to inspect
	 * @param progress the progress monitor
	 * @return a stream containing all linked libraries
	 * @throws CoreException if the project contents cannot be accessed
	 */
	public static Stream<LinkedLibrary> getAll(final IProject project, final IProgressMonitor progress)
			throws CoreException {
		final SubMonitor monitor = SubMonitor.convert(progress, 2);
		return Stream.concat(getExternal(project, monitor.split(1)), getStandard(project, monitor.split(1)));
	}

	private static List<LinkedLibrary> collectLinkedFolders(final IProject project, final String name,
			final SubMonitor progress) throws CoreException {
		final IFolder root = project.getFolder(name);

		if (!root.exists()) {
			return Collections.emptyList();
		}

		final List<LinkedLibrary> libFolders = new ArrayList<>();
		final IResource[] members = root.members();

		progress.setWorkRemaining(members.length);

		for (final IResource resource : members) {
			if (resource instanceof final IFolder folder && folder.isLinked()) {
				libFolders.add(new LinkedLibrary(folder));
			}
			progress.split(1);
		}

		return libFolders;
	}

	/*
	 * Wrapper for linked library folders
	 */

	private final IFolder folder;
	private Manifest manifest;

	private LinkedLibrary(final IFolder folder) {
		this.folder = Objects.requireNonNull(folder);
	}

	/**
	 * Checks whether the linked library points to a missing location.
	 *
	 * @return {@code true} if the link is invalid or its target no longer exists,
	 *         {@code false} otherwise
	 */
	public boolean hasBrokenLink() {
		if (!isValid()) {
			return true;
		}
		final IPath location = folder.getLocation();
		return location == null || !location.toFile().exists();
	}

	/**
	 * Checks whether this library represents an existing linked folder.
	 *
	 * @return {@code true} if the folder exists and is linked, {@code false}
	 *         otherwise
	 */
	public boolean isValid() {
		return folder.exists() && folder.isLinked();
	}

	/**
	 * Returns the linked folder represented by this library.
	 *
	 * @return the linked folder
	 */
	public IFolder getFolder() {
		return folder;
	}

	/**
	 * Returns the library manifest.
	 * <p>
	 *
	 * @return the library manifest, or {@code null} if none could be found
	 */
	public Manifest getLibraryManifest() {
		if (manifest == null) {
			manifest = ManifestHelper.getContainerManifest(folder);
		}
		return manifest;
	}

	/**
	 * Returns the symbolic name of this library.
	 * <p>
	 * If the manifest does not define a symbolic name, the folder name is used.
	 *
	 * @return the symbolic name of the library
	 */
	public String getSymbolicName() {
		return ManifestHelper.getSymbolicName(getLibraryManifest(), folder.getName());
	}

	/**
	 * Returns the version of this library.
	 * <p>
	 * The version is taken from the manifest if available. Otherwise, an attempt is
	 * made to infer it from the library's directory structure. If neither source
	 * provides a valid version, {@link Version#emptyVersion} is returned.
	 *
	 * @return the library version
	 */
	public Version getVersion() {
		return ManifestHelper.getVersion(getLibraryManifest(), parseLibraryVersion(folder));
	}

	private static Version parseLibraryVersion(final IFolder libraryFolder) {
		final IPath path = libraryFolder.getRawLocation();
		final String segment = (path != null && path.segmentCount() >= 2) ? path.segment(path.segmentCount() - 2) : ""; //$NON-NLS-1$
		final int index = segment.lastIndexOf('-');
		if (index > 0) {
			final String versionString = segment.substring(index + 1);
			if (VersionComparator.isValidRange(versionString)) {
				return new Version(versionString);
			}
		}
		return Version.emptyVersion;
	}
}
