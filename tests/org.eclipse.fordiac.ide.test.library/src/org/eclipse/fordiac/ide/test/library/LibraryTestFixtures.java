/*******************************************************************************
 * Copyright (c) 2026 Osoft e.U.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.library;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.LibraryPermission;
import org.eclipse.fordiac.ide.library.LibraryRecord;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * Handles the library fixtures and the global state touched by the library
 * tests, so that every test class can restore what it changed.
 */
public final class LibraryTestFixtures {
	/** Archives in the {@code data} folder, named {@code <symbolic name>-<version>}. */
	@SuppressWarnings("nls")
	private static final List<String> ARCHIVES = List.of("test01-1.0.0", "test01-1.1.0", "test01-1.5.0",
			"test01-2.0.0", "test02-1.0.0", "test02-1.1.0", "test03-1.0.0", "test03-1.1.0", "test04-1.0.0",
			"test04-1.1.0", "test05-1.0.0", "test06-1.0.0", "test07-1.0.0");

	private static final String BUNDLE_ID = "org.eclipse.fordiac.ide.test.library"; //$NON-NLS-1$
	private static final String DATA_FOLDER = "data/"; //$NON-NLS-1$
	private static final long REGISTRY_TIMEOUT_MILLIS = 5000;
	private static final long REGISTRY_POLL_MILLIS = 50;

	private LibraryTestFixtures() {
	}

	/**
	 * Extracts a test archive into the workspace library folder.
	 *
	 * @param archive archive name without folder and file extension
	 * @return the URI of the extracted library
	 */
	public static URI extract(final String archive) throws IOException, URISyntaxException {
		return LibraryManager.INSTANCE.extractLibrary(getArchivePath(archive), null, false, false);
	}

	/** Extracts all test archives into the workspace library folder. */
	public static void extractAll() throws IOException, URISyntaxException {
		for (final String archive : ARCHIVES) {
			extract(archive);
		}
	}

	/**
	 * Removes all extracted test archives from the workspace library folder and
	 * waits until the library manager no longer lists them.
	 */
	public static void removeFixtureLibraries() throws IOException {
		final Path libraryFolder = getWorkspacePath().resolve(LibraryManager.EXTRACTED_LIB_DIRECTORY);
		for (final String archive : ARCHIVES) {
			deleteReadOnlyTree(libraryFolder.resolve(archive));
		}
		awaitExtractedLibraries(libraries -> ARCHIVES.stream().noneMatch(archive -> isListed(libraries, archive)),
				"removed fixture libraries are no longer listed"); //$NON-NLS-1$
	}

	/**
	 * Waits until the extracted libraries listed by the library manager fulfil the
	 * given condition. The library manager learns about changes of the library
	 * folder asynchronously through a file system watch service.
	 */
	private static void awaitExtractedLibraries(final Predicate<Map<String, List<LibraryRecord>>> condition,
			final String description) {
		final long deadline = System.currentTimeMillis() + REGISTRY_TIMEOUT_MILLIS;
		while (!condition.test(LibraryManager.INSTANCE.getExtractedLibraries())) {
			if (System.currentTimeMillis() > deadline) {
				fail("Timed out waiting until " + description); //$NON-NLS-1$
			}
			try {
				Thread.sleep(REGISTRY_POLL_MILLIS);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
				fail("Interrupted while waiting until " + description); //$NON-NLS-1$
			}
		}
	}

	public static List<Version> getExtractedVersions(final String symbolicName) {
		return LibraryManager.INSTANCE.getExtractedLibraries().getOrDefault(symbolicName, List.of()).stream()
				.map(LibraryRecord::version).toList();
	}

	public static boolean hasFixtureLibraries() {
		final Map<String, List<LibraryRecord>> libraries = LibraryManager.INSTANCE.getExtractedLibraries();
		return ARCHIVES.stream().anyMatch(archive -> isListed(libraries, archive));
	}

	public static Path getDownloadFolder() {
		return getWorkspacePath().resolve(LibraryManager.PACKAGE_DOWNLOAD_DIRECTORY);
	}

	/**
	 * Removes the dependencies, library markers and library links of the project,
	 * as left behind by a test.
	 */
	public static void resetProject(final IProject project) throws Exception {
		waitForBuild();
		final Manifest manifest = ManifestHelper.getContainerManifest(project);
		if (manifest != null && manifest.getDependencies() != null) {
			manifest.getDependencies().getRequired().clear();
			ManifestHelper.saveManifest(manifest);
		}
		final IFile manifestFile = project.getFile(LibraryManager.MANIFEST);
		if (manifestFile.exists()) {
			manifestFile.deleteMarkers(FordiacErrorMarker.LIBRARY_MARKER, false, IResource.DEPTH_ZERO);
		}
		deleteLinkedLibraries(project.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME));
		deleteLinkedLibraries(project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME));
		waitForBuild();
	}

	public static void deleteProject(final String name) throws CoreException {
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
		if (project.exists()) {
			// removes the library links, not the linked library folders
			project.delete(true, true, null);
		}
	}

	/**
	 * Sets the shared activation state of the {@link MockDownloader}.
	 *
	 * @return the previous state
	 */
	public static boolean setMockDownloaderActive(final boolean active) {
		final MockDownloader downloader = new MockDownloader();
		final boolean previous = downloader.isActive();
		downloader.setActive(active);
		return previous;
	}

	public static boolean isMockDownloaderActive() {
		return new MockDownloader().isActive();
	}

	/**
	 * Enables or disables the automatic workspace build.
	 *
	 * @return the previous state
	 */
	public static boolean setAutoBuilding(final boolean enabled) throws CoreException {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IWorkspaceDescription description = workspace.getDescription();
		final boolean previous = description.isAutoBuilding();
		if (previous != enabled) {
			description.setAutoBuilding(enabled);
			workspace.setDescription(description);
		}
		return previous;
	}

	public static boolean isAutoBuilding() {
		return ResourcesPlugin.getWorkspace().isAutoBuilding();
	}

	/** Waits for running builds and for the marker jobs scheduled by them. */
	public static void waitForBuild() throws InterruptedException {
		final IJobManager manager = Job.getJobManager();
		manager.join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
		manager.join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
		manager.join(FordiacMarkerHelper.FAMILY_FORDIAC_MARKER, null);
	}

	private static Path getArchivePath(final String archive) throws IOException, URISyntaxException {
		final Bundle bundle = Platform.getBundle(BUNDLE_ID);
		final URL url = FileLocator.find(bundle,
				new org.eclipse.core.runtime.Path(DATA_FOLDER + archive + LibraryManager.ZIP_SUFFIX));
		if (url == null) {
			throw new IOException("Test archive not found: " + archive); //$NON-NLS-1$
		}
		return Paths.get(URIUtil.toURI(FileLocator.toFileURL(url)));
	}

	private static Path getWorkspacePath() {
		return ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPath();
	}

	private static boolean isListed(final Map<String, List<LibraryRecord>> libraries, final String archive) {
		final int separator = archive.lastIndexOf('-');
		final Version version = new Version(archive.substring(separator + 1));
		return libraries.getOrDefault(archive.substring(0, separator), List.of()).stream()
				.anyMatch(library -> version.equals(library.version()));
	}

	private static void deleteLinkedLibraries(final IFolder libraryFolder) throws CoreException {
		if (!libraryFolder.exists()) {
			return;
		}
		for (final IResource member : libraryFolder.members()) {
			if (member instanceof final IFolder folder) {
				folder.delete(false, null);
			}
		}
	}

	private static void deleteReadOnlyTree(final Path root) throws IOException {
		if (!Files.exists(root)) {
			return;
		}
		// extracted library files are read only
		try (Stream<Path> paths = Files.walk(root)) {
			for (final Path path : paths.sorted(Comparator.reverseOrder()).toList()) {
				LibraryPermission.setPathEditable(path);
				Files.delete(path);
			}
		}
	}
}
