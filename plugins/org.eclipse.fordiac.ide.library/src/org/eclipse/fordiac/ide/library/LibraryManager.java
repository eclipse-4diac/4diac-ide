/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH and others, Osoft e.U., MSR Software GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *   Alexander Fedorov (ArSysOp) - fix "Uncontrolled data used in path expression"
 *   Michael Oberlehner - use configured library package downloader providers
 *   Kaavya Manimathavan - remove stale library cache entries on polling file watchers
 *   Michael Oberlehner - look up the library sources once per dependency resolution
 *   Andrii Kozhushko - report libraries that depend on each other
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SequencedSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.equinox.p2.operations.IProfileChangeJob;
import org.eclipse.fordiac.ide.library.download.DownloadResult;
import org.eclipse.fordiac.ide.library.download.ILibraryPackageDownloader;
import org.eclipse.fordiac.ide.library.download.LibraryPackageDownloaderProvider;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.library.preferences.LibraryPreferenceConstants;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.preferences.PreferenceProvider;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public enum LibraryManager {

	INSTANCE;

	private static final String UPDATE_JOB_NAME = "Updating Software"; //$NON-NLS-1$

	public static final String LIB_TYPELIB_FOLDER_NAME = "typelib"; //$NON-NLS-1$
	public static final String PACKAGE_DOWNLOAD_DIRECTORY = ".download"; //$NON-NLS-1$
	public static final String EXTRACTED_LIB_DIRECTORY = ".lib"; //$NON-NLS-1$
	public static final String MANIFEST = "MANIFEST.MF"; //$NON-NLS-1$
	public static final String DOWNLOADER_EXTENSION = "org.eclipse.fordiac.ide.library.LibraryPackageDownloader"; //$NON-NLS-1$

	private final java.net.URI workspaceLibraryURI = java.net.URI.create("WORKSPACE_LOC/" + EXTRACTED_LIB_DIRECTORY); //$NON-NLS-1$

	private final Path workspacePath = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPath();
	private final Path libraryPath = workspacePath.resolve(EXTRACTED_LIB_DIRECTORY);
	private final Path archivePath = workspacePath.resolve(PACKAGE_DOWNLOAD_DIRECTORY);

	private final java.net.URI standardLibraryUri = java.net.URI.create("ECLIPSE_HOME/" + TypeLibraryTags.TYPE_LIBRARY); //$NON-NLS-1$
	private final Path standardLibraryPath = getStandardLibPath();

	public static final String ZIP_SUFFIX = ".zip"; //$NON-NLS-1$
	public static final Set<String> TYPE_ENDINGS = Set.of(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING,
			TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING, TypeLibraryTags.DATA_TYPE_FILE_ENDING,
			TypeLibraryTags.DEVICE_TYPE_FILE_ENDING, TypeLibraryTags.FB_TYPE_FILE_ENDING,
			TypeLibraryTags.FC_TYPE_FILE_ENDING, TypeLibraryTags.GLOBAL_CONST_FILE_ENDING,
			TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING, TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING,
			TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING, TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING);
	private static final Filter<Path> ARCHIVE_DIR_FILTER = entry -> (Files.isDirectory(entry)
			|| entry.getFileName().toString().endsWith(ZIP_SUFFIX));
	private static final Path[] EMPTY_PATH_ARRAY = new Path[0];

	public static final VersionRange ALL_RANGE = new VersionRange(VersionRange.LEFT_CLOSED, Version.emptyVersion, null,
			VersionRange.RIGHT_CLOSED);

	private WatchService watchService;
	private final AtomicBoolean standardLibraryResolutionEnabled = new AtomicBoolean(true);
	private final Map<String, List<LibraryRecord>> stdlibraries = new ConcurrentHashMap<>();
	private final HashMap<String, List<LibraryRecord>> libraries = new HashMap<>();

	public static final Object FAMILY_FORDIAC_LIBRARY = new Object();

	/** The cause of the libraries that the project requires. */
	private static final String PROJECT_CAUSE = "Project"; //$NON-NLS-1$

	/**
	 * @param blocked libraries that require themselves or each other, or whose versions cannot be resolved
	 *                consistently: they are not resolved and not linked
	 * @param reports the error messages about these libraries, with the library the marker refers to
	 * @param conflicts the dependency nodes of blocked libraries whose version range was empty, as they were then
	 */
	private record LibraryManagerData(Map<String, DependencyNode> dependencyNodes,
			Map<String, ResolveNode> resolveNodes, Map<String, LinkedLibrary> linked,
			Map<String, List<Version>> referenced, DownloadSources sources, Set<String> blocked,
			Map<String, String> reports, Map<String, DependencyNode> conflicts) {
		public static LibraryManagerData init() {
			return new LibraryManagerData(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
					new DownloadSources(), new HashSet<>(), new LinkedHashMap<>(), new HashMap<>());
		}
	}

	/**
	 * A library visited by the resolution: the libraries its resolved version requires and, if the range of
	 * versions was empty, the dependency node as it was then.
	 */
	private record Visit(String symbolicName, Set<String> requirements, DependencyNode conflict) {
	}

	/**
	 * The library sources of one operation, looked up on first use. Reusing the
	 * downloaders lets each of them load its catalogue once per operation instead
	 * of once per downloaded library.
	 */
	static final class DownloadSources {
		private LibraryPackageDownloaderProvider.Result result;

		LibraryPackageDownloaderProvider.Result get() {
			if (result == null) {
				result = new LibraryPackageDownloaderProvider().getActiveDownloaders();
				if (!result.status().isOK()) {
					ILog.get().log(result.status());
				}
			}
			return result;
		}
	}

	LibraryManager() {
		initLibraryMap(stdlibraries, standardLibraryPath, standardLibraryUri);
		if (!Files.exists(libraryPath)) {
			try {
				Files.createDirectory(libraryPath);
			} catch (final IOException e) {
				ILog.get().error("Cannot create lib path!", e); //$NON-NLS-1$
			}
		}
		initLibraryMap(libraries, libraryPath, workspaceLibraryURI);
		try {
			watchService = FileSystems.getDefault().newWatchService();
			libraryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE);
		} catch (final IOException e) {
			ILog.get().error("Cannot register watch watch service!", e); //$NON-NLS-1$
		}

		LibraryPermission.setLibReadOnly(standardLibraryPath);
		initP2UpdateListener();
	}

	private void initP2UpdateListener() {
		Job.getJobManager().addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void aboutToRun(final IJobChangeEvent event) {
				if (isUpdateProvisioningJob(event.getJob())) {
					standardLibraryResolutionEnabled.set(false);
				}
			}

			@Override
			public void done(final IJobChangeEvent event) {
				if (isUpdateProvisioningJob(event.getJob())) {
					standardLibraryResolutionEnabled.set(true);
				}
			}

		});
	}

	/**
	 * Poll the {@link WatchService} for changes in the library folder and react
	 * accordingly
	 */
	private void checkLibChanges(final SubMonitor progress) {
		progress.beginTask(Messages.LibraryManager_ChekForLibraryChanges, 10);
		if (watchService == null) {
			return;
		}
		final WatchKey watchKey = watchService.poll();
		if (watchKey == null) {
			return;
		}

		final var events = watchKey.pollEvents();
		progress.setWorkRemaining(events.size());
		events.forEach(event -> {
			try {
				if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE
						|| event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
					refreshLibrary(libraryPath.resolve((Path) event.context()));
				} else { // Overflow -> reinitialise libraries to ensure correct state
					synchronized (libraries) {
						initLibraryMap(libraries, libraryPath, workspaceLibraryURI);
					}
					return;
				}
				progress.worked(1);
			} catch (final IOException e) {
				// empty
			}
		});
		watchKey.reset();
	}

	/**
	 * Initialize map with all libraries contained in the folder specified
	 *
	 * @param map     map to initialize
	 * @param path    path to folder
	 * @param baseURI URI to use as base
	 */
	private static void initLibraryMap(final Map<String, List<LibraryRecord>> map, final Path path,
			final java.net.URI baseURI) {
		map.clear();
		try (var stream = Files.newDirectoryStream(path, (Filter<? super Path>) Files::isDirectory)) {
			for (final Path folder : stream) {
				addLibrary(map, folder, baseURI);
			}
		} catch (final IOException e) {
			// empty
		}
	}

	/**
	 * Add {@link LibraryRecord} to the given map based on the {@link Path}
	 *
	 * @param map     target map
	 * @param path    path of the library folder
	 * @param baseUri URI to use as base
	 * @throws IOException if an I/O error occurs
	 */
	private static void addLibrary(final Map<String, List<LibraryRecord>> map, final Path path,
			final java.net.URI baseUri) throws IOException {
		try (var folderStream = Files.newDirectoryStream(path, MANIFEST)) {
			final Iterator<Path> it = folderStream.iterator();
			if (it.hasNext()) {
				final Manifest manifest = ManifestHelper.getManifest(it.next());
				if (manifest != null && ManifestHelper.isLibrary(manifest) && manifest.getProduct() != null
						&& manifest.getProduct().getSymbolicName() != null) {
					map.computeIfAbsent(manifest.getProduct().getSymbolicName(), _ -> new ArrayList<>())
							.add(new LibraryRecord(manifest.getProduct().getSymbolicName(),
									manifest.getProduct().getName(),
									manifest.getProduct().getVersionInfo().getVersion(),
									manifest.getProduct().getComment(), path,
									URIUtil.append(baseUri, path.getFileName().toString())));
				}
			}
		}
	}

	/**
	 * Refresh the cached library at the given path from its current file system state.
	 *
	 * @param path path of the library folder
	 * @throws IOException if an I/O error occurs
	 */
	private void refreshLibrary(final Path path) throws IOException {
		synchronized (libraries) {
			libraries.values().forEach(records -> records.removeIf(record -> record.path().equals(path)));
			libraries.values().removeIf(List::isEmpty);
			if (Files.isDirectory(path)) {
				addLibrary(libraries, path, workspaceLibraryURI);
			}
		}
	}

	/**
	 * Extract library archive into the {@link #EXTRACTED_LIB_DIRECTORY} folder
	 *
	 * <p>
	 * See {@link #importLibrary} for automatic import into the given
	 * {@link IProject}
	 *
	 * @param path       path of the archive file to import (only .zip)
	 * @param project    project to import the library into after extracting
	 *                   (irrelevant if {@code autoImport} is false)
	 * @param autoImport if library should be automatically imported into project
	 * @param resolve    define if dependencies should get resolved on import
	 *                   (irrelevant if {@code autoImport} is false)
	 * @return {@link java.net.URI} of the extracted library folder
	 * @throws IOException if an I/O error occurs
	 */
	public java.net.URI extractLibrary(final Path path, final IProject project, final boolean autoImport,
			final boolean resolve) throws IOException {
		if (path == null) {
			throw new FileNotFoundException("Archive path not provided"); //$NON-NLS-1$
		}

		final Path archive = path.toRealPath();

		if (!Files.isRegularFile(archive)) {
			throw new FileNotFoundException("Archive file does not exist: " + archive); //$NON-NLS-1$
		}

		ILog.get().info("Extracting library at " + archive); //$NON-NLS-1$
		final byte[] buffer = new byte[1024];
		String folderName;
		Path extractedFolder = null;
		try (InputStream inputStream = Files.newInputStream(archive);
				ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
			ZipEntry entry = zipInputStream.getNextEntry();
			folderName = ""; //$NON-NLS-1$
			if (entry != null) {
				folderName = entry.getName();
				extractedFolder = newPath(libraryPath, entry);
				deleteLibFolder(extractedFolder);
			}
			while (entry != null) {
				final Path newFile = newPath(libraryPath, entry);
				if (entry.isDirectory()) {
					if (!Files.isDirectory(newFile)) {
						Files.createDirectories(newFile);
					}
				} else {
					final Path parent = newFile.getParent();
					if (!Files.isDirectory(parent)) {
						Files.createDirectories(parent);
					}
					try (OutputStream fileOutputStream = Files.newOutputStream(newFile)) {
						int len;
						while ((len = zipInputStream.read(buffer)) > 0) {
							fileOutputStream.write(buffer, 0, len);
						}
					}
					LibraryPermission.setPathReadOnly(newFile);
				}
				entry = zipInputStream.getNextEntry();
			}
		}
		checkLibChanges(SubMonitor.convert(null));
		if (extractedFolder != null) {
			// File system notifications may arrive after the dependency is resolved.
			refreshLibrary(extractedFolder);
		}

		// strip potential trailing slash
		if (folderName.endsWith("/")) { //$NON-NLS-1$
			folderName = folderName.substring(0, folderName.length() - 1);
		}

		final java.net.URI importURI = URIUtil.append(workspaceLibraryURI, folderName);

		if (autoImport && project != null) {
			// Parent's name because we want package-version name when importing
			importLibrary(project, importURI, true, resolve);
		}
		return importURI;
	}

	private static void deleteLibFolder(final Path folder) throws IOException {
		if (Files.exists(folder)) {
			Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
					LibraryPermission.setPathEditable(file);
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
					LibraryPermission.setPathEditable(dir);
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}

			});
		}
	}

	/**
	 * Create new {@link Path} for {@link ZipEntry} and ensure it stays in
	 * {@code destinationDir}
	 *
	 * @param destinationDir base directory to put the entry into
	 * @param zipEntry       zip entry to base the path on
	 * @return appropriate path
	 * @throws IOException if an I/O error occurs
	 */
	private static Path newPath(final Path destinationDir, final ZipEntry zipEntry) throws IOException {
		final Path normalizedDestinationDir = destinationDir.toAbsolutePath().normalize();
		final String entryName = zipEntry.getName().replace('\\', '/');
		final Path destPath = normalizedDestinationDir.resolve(entryName).normalize();

		if (!destPath.startsWith(normalizedDestinationDir)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName()); //$NON-NLS-1$
		}
		return destPath;
	}

	/**
	 * Import library into {@link IProject}
	 *
	 * @param project target project
	 * @param uri     URI of the library folder
	 * @param update  define if a dependency gets created/updated in the
	 *                {@link Manifest}
	 * @param resolve define if dependencies should get resolved on import
	 *
	 * @return {@code true}, if library was successfully imported
	 */
	public boolean importLibrary(final IProject project, final java.net.URI uri, final boolean update,
			final boolean resolve) {
		boolean imported = false;

		ILog.get().info("Importing library at " + uri + " into project " + project.getName() + " (update=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ update + ", resolve=" + resolve + ")"); //$NON-NLS-1$ //$NON-NLS-2$

		checkLibChanges(SubMonitor.convert(null));
		final IPathVariableManager varMan = project.getPathVariableManager();
		final java.net.URI resolvedUri = varMan.resolveURI(uri);
		final Path path = Paths.get(resolvedUri);
		if (!Files.isDirectory(path) || Files.notExists(path.resolve(LIB_TYPELIB_FOLDER_NAME))) {
			return false;
		}
		final Manifest libManifest = ManifestHelper.getFolderManifest(path);
		if (libManifest == null) {
			return false;
		}
		final Manifest projManifest = ManifestHelper.getOrCreateProjectManifest(project);
		if (projManifest == null) {
			return false;
		}
		final IFolder libDirectory = project
				.getFolder(uri.getPath().startsWith(standardLibraryUri.getPath())
						? TypeLibraryTags.STANDARD_LIB_FOLDER_NAME
						: TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME)
				.getFolder(libManifest.getProduct().getSymbolicName());

		final java.net.URI libUri = URIUtil.append(uri, LIB_TYPELIB_FOLDER_NAME);
		final java.net.URI manUri = URIUtil.append(uri, MANIFEST);
		try {
			libDirectory.createLink(libUri, IResource.REPLACE, null);
			final IFile man = libDirectory.getFile(MANIFEST);
			man.createLink(manUri, IResource.HIDDEN | IResource.REPLACE, null);
			if (update) {
				ManifestHelper.updateDependency(projManifest,
						ManifestHelper.createRequired(libManifest.getProduct().getSymbolicName(),
								libManifest.getProduct().getVersionInfo().getVersion()));
				projManifest.eResource().save(null);
			}
			imported = true;
		} catch (final CoreException | IOException e) {
			ILog.get().error(MessageFormat.format(Messages.ImportFailedOnLinkCreation, e.getMessage()), e);
		}

		return imported;
	}

	/**
	 * Import multiple libraries into {@link IProject}
	 *
	 * @param project target project
	 * @param uris    URIs of libraries
	 * @param resolve define if dependencies should get resolved on import
	 */
	public void importLibraries(final IProject project, final Collection<java.net.URI> uris, final boolean resolve) {
		for (final java.net.URI uri : uris) {
			importLibrary(project, uri, true, resolve);
		}
	}

	/**
	 * List the filtered contents of the standard archive folder
	 *
	 * @return array of paths
	 */
	public Path[] listDirectoriesContainingArchives() {
		return listArchiveFolders(archivePath);
	}

	/**
	 * Lists the filtered contents of the given folder - only directories and
	 * archives
	 *
	 * @param path folder path
	 * @return array of paths
	 */
	@SuppressWarnings("static-method")
	public Path[] listArchiveFolders(final Path path) {
		if (!Files.isDirectory(path)) {
			return EMPTY_PATH_ARRAY;
		}
		final List<Path> content = new LinkedList<>();
		try (var stream = Files.newDirectoryStream(path, ARCHIVE_DIR_FILTER)) {
			stream.forEach(content::add);
		} catch (final IOException e) {
			// empty
		}

		return content.toArray(EMPTY_PATH_ARRAY);
	}

	/**
	 * Returns a map of the standard libraries embedded in the distribution
	 *
	 * @return map of libraries
	 */
	public Map<String, List<LibraryRecord>> getStandardLibraries() {
		return new HashMap<>(stdlibraries);
	}

	/**
	 * Returns a map of the libraries in the library folder
	 *
	 * @return map of libraries
	 */
	public Map<String, List<LibraryRecord>> getExtractedLibraries() {
		checkLibChanges(SubMonitor.convert(null));
		synchronized (libraries) {
			// The polling WatchService (e.g. on macOS) can miss folders that are created and deleted
			// between two scans, so drop cached libraries whose folder no longer exists.
			libraries.values().forEach(records -> records.removeIf(r -> !Files.isDirectory(r.path())));
			libraries.values().removeIf(List::isEmpty);
			final Map<String, List<LibraryRecord>> snapshot = new HashMap<>();
			libraries.forEach((name, records) -> snapshot.put(name, new ArrayList<>(records)));
			return snapshot;
		}
	}

	/**
	 * Uses the active {@link ILibraryPackageDownloader downloaders} of all
	 * library sources (see {@link LibraryPackageDownloaderProvider}) to download specified
	 * library. Will download the latest version if versionRange is {@code null} or
	 * empty.
	 *
	 * <p>
	 * See {@link ILibraryPackageDownloader#downloadLibrary} for more info
	 *
	 * @param symbolicName symbolic name of library
	 * @param versionRange version range of library
	 * @param preferred    preferred version of library (ignored if {@code null} or
	 *                     outside version range)
	 * @param project      project to import the library into after extracting
	 *                     (irrelevant if {@code autoImport} is false)
	 * @param autoImport   if library should be automatically imported into project
	 * @param resolve      define if dependencies should get resolved on import
	 *                     (irrelevant if {@code autoImport} is false)
	 * @param downloadSources the library sources of the current operation
	 * @param progress     SubMonitor for progress report
	 * @return {@link java.net.URI} of the extracted library folder encapsulated in
	 *         a {@link DownloadResult}
	 */
	private DownloadResult<java.net.URI> libraryDownload(final String symbolicName, final VersionRange versionRange,
			final Version preferred, final IProject project, final boolean autoImport, final boolean resolve,
			final DownloadSources downloadSources, final SubMonitor progress) throws OperationCanceledException {
		progress.setTaskName(MessageFormat.format(Messages.LibraryManager_LibraryDownload, symbolicName));
		ILog.get().info("Attempting to download library " + symbolicName + " with version " + versionRange //$NON-NLS-1$ //$NON-NLS-2$
				+ " preferring " + preferred + " Project: " + project.getName()); //$NON-NLS-1$ //$NON-NLS-2$

		final var sources = downloadSources.get();
		final List<ILibraryPackageDownloader> downloaders = sources.downloaders();

		DownloadResult<Path> dlResult;
		final StringBuilder errors = new StringBuilder();
		for (final IStatus status : sources.status().getChildren()) {
			errors.append(" | ").append(status.getMessage()); //$NON-NLS-1$
		}
		final VersionRange range = (versionRange == null || versionRange.isEmpty()) ? ALL_RANGE : versionRange;
		final Version pref = (preferred != null && range.includes(preferred)) ? preferred : null;
		progress.setWorkRemaining(downloaders.size());
		for (final var downloader : downloaders) {
			try {
				dlResult = downloader.downloadLibrary(symbolicName, range, pref, progress.split(1));
				if (dlResult.status() == DownloadResult.Status.OK) {
					return new DownloadResult<>(extractLibrary(dlResult.result(), project, autoImport, resolve));
				}
				// ignore NO_CONFIG
				if (dlResult.status() == DownloadResult.Status.NOT_FOUND
						|| dlResult.status() == DownloadResult.Status.CONFIG_ERROR
						|| dlResult.status() == DownloadResult.Status.ERROR) {
					errors.append(" | "); //$NON-NLS-1$
					errors.append(downloader.getName());
					errors.append(": "); //$NON-NLS-1$
					errors.append(symbolicName);
					errors.append(" "); //$NON-NLS-1$
					errors.append(dlResult.message());
				}
			} catch (final IOException e) {
				ILog.get().error(e.getMessage(), e);
			}
		}
		return new DownloadResult<>(DownloadResult.Status.ERROR, errors.toString());
	}

	/**
	 * Starts a background job that downloads the most current version of a given
	 * library dependency
	 *
	 * @param project      selected project
	 * @param symbolicName symbolic name of library dependency
	 * @param versionRange version range of dependency
	 */
	public void updateLibrary(final IProject project, final String symbolicName, final String versionRange) {
		final WorkspaceJob job = new WorkspaceJob(
				MessageFormat.format(Messages.LibraryManager_UpdateLibraryPackage, symbolicName, versionRange)) {

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				libraryDownload(symbolicName, VersionComparator.parseVersionRange(versionRange), null, project, true,
						true, new DownloadSources(), SubMonitor.convert(monitor));
				return Status.OK_STATUS;
			}

			@Override
			public boolean belongsTo(final Object family) {
				return family == FAMILY_FORDIAC_LIBRARY;
			}
		};
		job.setRule(project);
		job.setPriority(Job.LONG);
		job.schedule();
	}

	/**
	 * Resolves transitive library dependencies of a {@link IProject project}
	 *
	 * <p>
	 * This method will remove already linked libraries if they cause conflicts or
	 * are no longer needed
	 *
	 * @param project  selected project
	 * @param manifest
	 * @throws CoreException
	 */
	public void resolveDependencies(final IProject project, final Manifest projectManifest,
			final IProgressMonitor monitor) throws OperationCanceledException, CoreException {

		if (!standardLibraryResolutionEnabled.get()) {
			return;
		}

		final LibraryManagerData libManagerData = LibraryManagerData.init();

		final SequencedSet<String> queue = new LinkedHashSet<>(); // symbolicNames, each library once

		final SubMonitor progress = SubMonitor.convert(monitor, Messages.LibraryManager_ResolvingLibraryDependencies,
				100);

		if (projectManifest == null) {
			return;
		}

		checkLibChanges(progress.split(4));

		if (projectManifest.getDependencies() == null) {
			return;
		}

		collectReferencedDependencies(project, libManagerData.referenced());

		findPreferred(project, libManagerData, progress.split(5));

		projectManifest.getDependencies().getRequired().forEach(req -> {
			libManagerData.dependencyNodes().put(req.getSymbolicName(),
					new DependencyNode(req.getSymbolicName(), PROJECT_CAUSE,
							VersionComparator.parseVersionRange(req.getVersion())));
			queue.add(req.getSymbolicName());
		});

		buildDependencies(project, libManagerData, queue, progress.split(70));

		final List<ErrorMarkerBuilder> markerList = new LinkedList<>();

		// import valid nodes
		importDependencyNodes(project, libManagerData, projectManifest, markerList, progress.split(15));

		// Preserve existing links when the dependency graph is incomplete.
		if (queue.isEmpty()) {
			cleanupLinks(libManagerData.linked(), progress.split(2));
		} else {
			progress.worked(2);
		}

		// check if imported library links are broken
		checkLinkedLibraries(project, progress.split(1));

		if (PreferenceProvider.getBoolean(LibraryPreferenceConstants.LIBRARY_PREFERENCES_ID,
				LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES, false, project)) {
			// force load explicitly defined dependencies
			final List<Required> explicitDeps = projectManifest.getDependencies().getRequired().stream()
					.filter(r -> !r.getVersion().contains("-") //$NON-NLS-1$
							&& !libManagerData.blocked().contains(r.getSymbolicName())
							&& !libManagerData.dependencyNodes().get(r.getSymbolicName()).isValid())
					.toList();
			progress.setWorkRemaining(explicitDeps.size());
			for (final Required req : explicitDeps) {
				libManagerData.linked().remove(req.getSymbolicName());
				final Version version = Version.parseVersion(req.getVersion());
				LibraryRecord lib = getLibraryRecord(stdlibraries, req.getSymbolicName(), version);
				// check if library is already downloaded
				if (lib != null) {
					importLibrary(project, lib.uri(), false, false);
					continue;
				}
				lib = getLibraryRecord(libraries, req.getSymbolicName(), version);
				if (lib != null) {
					importLibrary(project, lib.uri(), false, false);
					continue;
				}
				// download library
				libraryDownload(req.getSymbolicName(),
						new org.eclipse.osgi.service.resolver.VersionRange(version, true, version, true), null, project,
						true, false, libManagerData.sources(), progress.split(1));
			}

		} else {
			progress.worked(3);
		}

		final int maxSeverity = markerList.stream().mapToInt(ErrorMarkerBuilder::getSeverity).max().orElse(-1);
		if (maxSeverity >= IMarker.SEVERITY_ERROR) {
			FordiacMarkerHelper.updateMarkers(project, FordiacErrorMarker.LIBRARY_MARKER,
					List.of(ErrorMarkerBuilder
							.createErrorMarkerBuilder(Messages.LibraryManager_UnresolvableDependencies)
							.setType(FordiacErrorMarker.LIBRARY_MARKER)),
					true);
		}

		FordiacMarkerHelper.updateMarkers(project.getFile(MANIFEST), FordiacErrorMarker.LIBRARY_MARKER, markerList,
				true);

		TypeLibraryManager.INSTANCE.getTypeLibrary(project).refresh();
	}

	/**
	 * Checks if a given link inside the library folders is broken.
	 *
	 * <p>
	 * This method checks if the existing links are broken, creates error markers
	 * and will eventually abort the build.
	 *
	 * @param project selected project
	 * @throws CoreException
	 */
	private static void checkLinkedLibraries(final IProject project, final SubMonitor progress) throws CoreException {
		progress.setTaskName(Messages.LibraryManager_CheckLinks);
		progress.setWorkRemaining(10);
		LinkedLibrary.getAll(project, progress).filter(LinkedLibrary::hasBrokenLink).forEach(f -> {
			FordiacMarkerHelper.updateMarkers(f.getFolder(), FordiacErrorMarker.LIBRARY_MARKER,
					List.of(LibraryMarkerFactory.createBrokenLinkMarker(f)), true);
			throw new OperationCanceledException();
		});
	}

	/**
	 * Resolves the libraries of the queue and the libraries they require until the
	 * queue is empty. Libraries that require themselves or each other are reported
	 * and not resolved. The resolution stops if a library cannot be resolved, then
	 * the queue is not empty.
	 */
	private void buildDependencies(final IProject project, final LibraryManagerData data,
			final SequencedSet<String> queue, final SubMonitor progress) throws OperationCanceledException {
		progress.setTaskName(Messages.LibraryManager_BuildingDependencyGraph);

		// The state is only compared for repeated visits: there are at most as many first visits as libraries,
		// so skipping them delays the detection of a resolution that does not end, but does not miss it.
		final Set<String> visited = new HashSet<>();
		// the state in front of each repeated visit, with the number of visits before it
		final Map<String, Integer> states = new HashMap<>();
		final List<Visit> trace = new ArrayList<>();
		while (true) {
			while (!queue.isEmpty()) {
				if (progress.isCanceled()) {
					throw new OperationCanceledException();
				}
				progress.setWorkRemaining(Math.max(queue.size(), 10));
				final String symbolicName = queue.removeFirst();
				final var dnode = data.dependencyNodes().get(symbolicName);

				if (!dnode.isChanged()) {
					continue;
				}
				dnode.resetChanged();

				if (!visited.add(symbolicName)) {
					final Integer before = states.put(visitState(data, symbolicName, queue),
							Integer.valueOf(trace.size()));
					if (before != null) {
						// the resolution returns to a state it had before and does not end
						blockUnstable(data, queue, trace.subList(before.intValue(), trace.size()));
						states.clear();
						trace.clear();
					}
				}

				final DependencyNode conflict = dnode.isRangeEmpty() ? snapshot(dnode) : null;
				final boolean resolved = visit(project, data, queue, symbolicName, dnode, progress);
				final var rnode = data.resolveNodes().get(symbolicName);
				trace.add(new Visit(symbolicName, rnode == null ? Set.of() : rnode.getDependencies().keySet(),
						conflict));
				if (!resolved) {
					return;
				}
			}
			// the states are kept over the rounds of removeUnneeded: they are part of the resolution that does not end
			if (!removeUnneeded(data, queue)) {
				if (!blockCycles(data, queue)) {
					return;
				}
				// the blocked libraries are part of every state from now on, the states before cannot return
				states.clear();
				trace.clear();
			}
		}
	}

	/**
	 * Resolves a library and adds its requirements as causes to the libraries it
	 * requires. A library that requires itself keeps the requirement in its
	 * resolved node, it is no cause.
	 *
	 * @return {@code false} if the resolution has to stop, because the library
	 *         cannot be resolved
	 */
	private boolean visit(final IProject project, final LibraryManagerData data, final Set<String> queue,
			final String symbolicName, final DependencyNode dnode, final SubMonitor progress) {
		if (data.blocked().contains(symbolicName) || !dnode.isValid()) {
			release(data, queue, symbolicName);
			return true;
		}

		final Version prefVersion = Optional.ofNullable(data.linked().get(symbolicName)).map(LinkedLibrary::getVersion)
				.orElse(null);
		// resolve dependency
		final var rnode = resolveDependency(project, symbolicName, dnode.getRange(), prefVersion, progress.split(1),
				data.referenced(), data.sources());

		final var oldRNode = data.resolveNodes().put(symbolicName, rnode);
		if (oldRNode != null) {
			// a library that the new version does not require any more is visited again: if it is not needed
			// any more, its requirements are removed as well
			oldRNode.getDependencies().keySet().stream().filter(old -> !rnode.getDependencies().containsKey(old))
					.forEach(old -> dropCause(data, queue, old, symbolicName));
		}

		// updated dependencies
		rnode.getDependencies().forEach((symb, val) -> {
			if (!symb.equals(symbolicName)) {
				final var dn = data.dependencyNodes().computeIfAbsent(symb, _ -> new DependencyNode(symb));
				dn.putCause(symbolicName, val);
				if (dn.isChanged()) {
					queue.add(symb);
				}
			}
		});

		return rnode.getError() == null;
	}

	/** The library is not resolved: the libraries it required lose it as a cause. */
	private static void release(final LibraryManagerData data, final Set<String> queue, final String symbolicName) {
		final var rnode = data.resolveNodes().remove(symbolicName);
		if (rnode != null) {
			rnode.getDependencies().keySet().forEach(symb -> dropCause(data, queue, symb, symbolicName));
		}
	}

	private static void dropCause(final LibraryManagerData data, final Set<String> queue, final String dependency,
			final String requirer) {
		final var dn = data.dependencyNodes().get(dependency);
		// the cause of the project is not the cause of a library of the same name
		if (dn != null && !requirer.equals(PROJECT_CAUSE)) {
			dn.removeCause(requirer);
			if (dn.isChanged()) {
				queue.add(dependency);
			}
		}
	}

	private static DependencyNode snapshot(final DependencyNode dnode) {
		final var copy = new DependencyNode(dnode.getSymbolicName());
		dnode.getCauses().forEach(copy::putCause);
		return copy;
	}

	/**
	 * Libraries that require each other keep each other alive, also if nothing the
	 * project needs requires them any more (a new version of a library does not
	 * require them): their requirements narrow the ranges of libraries that are
	 * needed. The causes of such libraries are removed.
	 *
	 * @return {@code true} if causes were removed
	 */
	private static boolean removeUnneeded(final LibraryManagerData data, final Set<String> queue) {
		final Map<String, Set<String>> graph = requirementGraph(data, true);
		final Set<String> needed = new HashSet<>();
		data.dependencyNodes().values().stream().filter(dnode -> dnode.getCauses().containsKey(PROJECT_CAUSE))
				.forEach(dnode -> {
					needed.add(dnode.getSymbolicName());
					needed.addAll(reachable(graph, dnode.getSymbolicName()).keySet());
				});
		boolean removed = false;
		for (final var dnode : data.dependencyNodes().values()) {
			if (!needed.contains(dnode.getSymbolicName()) && !dnode.getCauses().isEmpty()) {
				new ArrayList<>(dnode.getCauses().keySet()).forEach(dnode::removeCause);
				queue.add(dnode.getSymbolicName());
				removed = true;
			}
		}
		return removed;
	}

	/**
	 * The requirements of the resolved libraries as they are now, without the
	 * libraries that are blocked.
	 *
	 * @param self whether a library that requires itself has an edge to itself
	 */
	private static Map<String, Set<String>> requirementGraph(final LibraryManagerData data, final boolean self) {
		final Map<String, Set<String>> graph = new TreeMap<>();
		data.resolveNodes().forEach((name, rnode) -> {
			if (!data.blocked().contains(name)) {
				rnode.getDependencies().keySet().stream()
						.filter(other -> (self || !other.equals(name)) && !data.blocked().contains(other))
						.forEach(other -> graph.computeIfAbsent(name, _ -> new TreeSet<>()).add(other));
			}
		});
		return graph;
	}

	/**
	 * The resolution converged: libraries that require themselves or each other
	 * are reported and blocked, in the versions that are resolved. Only cycles that
	 * cannot be reached from another cycle are blocked, a cycle behind them may be
	 * caused by their requirements and is looked for again afterwards.
	 *
	 * @return {@code true} if libraries were blocked
	 */
	private static boolean blockCycles(final LibraryManagerData data, final Set<String> queue) {
		final Map<String, Set<String>> graph = requirementGraph(data, true);
		final List<List<String>> cycles = upstreamCycles(graph, _ -> true);
		for (final List<String> cycle : cycles) {
			data.reports().put(cycleMessage(data, graph, cycle), cycle.get(0));
			block(data, queue, cycle);
		}
		return !cycles.isEmpty();
	}

	/**
	 * The resolution does not end, the same state is reached again after the
	 * visits of the window. The libraries that are on a cycle of the requirements
	 * of these visits and of the current requirements are blocked, if the cycle
	 * contains a library of the window. This does not mean that there is no
	 * solution, only that this resolution does not find it.
	 */
	private static void blockUnstable(final LibraryManagerData data, final Set<String> queue,
			final List<Visit> window) {
		final Map<String, Set<String>> graph = requirementGraph(data, false);
		window.forEach(visit -> visit.requirements().stream()
				.filter(name -> !name.equals(visit.symbolicName()) && !data.blocked().contains(name))
				.forEach(name -> graph.computeIfAbsent(visit.symbolicName(), _ -> new TreeSet<>()).add(name)));
		final Set<String> visited = window.stream().map(Visit::symbolicName).collect(Collectors.toSet());
		List<List<String>> groups = upstreamCycles(graph, cycle -> cycle.stream().anyMatch(visited::contains));
		if (groups.isEmpty()) {
			// not expected: a repeated state has a cycle of requirements
			groups = List.of(visited.stream().filter(name -> !data.blocked().contains(name)).sorted().toList());
		}
		for (final List<String> group : groups) {
			window.stream().filter(visit -> visit.conflict() != null && group.contains(visit.symbolicName()))
					.forEach(visit -> data.conflicts().put(visit.symbolicName(), visit.conflict()));
			data.reports().put(MessageFormat.format(Messages.ErrorMarkerUnstableDependency,
					String.join(", ", group)), group.get(0)); //$NON-NLS-1$
			block(data, queue, group);
		}
	}

	private static void block(final LibraryManagerData data, final Set<String> queue, final Collection<String> names) {
		data.blocked().addAll(names);
		names.forEach(name -> release(data, queue, name));
	}

	/**
	 * The libraries that are on a cycle: the strongly connected components that
	 * contain a cycle and cannot be reached from another one, each sorted by name,
	 * ordered by their first name.
	 */
	private static List<List<String>> upstreamCycles(final Map<String, Set<String>> graph,
			final Predicate<List<String>> relevant) {
		final Map<String, Set<String>> reach = new TreeMap<>();
		graph.keySet().forEach(name -> reach.put(name, reachable(graph, name).keySet()));
		final List<List<String>> cycles = new ArrayList<>();
		final Set<String> listed = new HashSet<>();
		reach.forEach((name, reached) -> {
			if ((reached.contains(name) || graph.get(name).contains(name)) && listed.add(name)) {
				final List<String> cycle = Stream
						.concat(Stream.of(name),
								reached.stream().filter(other -> reach.getOrDefault(other, Set.of()).contains(name)))
						.distinct().sorted().toList();
				listed.addAll(cycle);
				cycles.add(cycle);
			}
		});
		final List<List<String>> wanted = cycles.stream().filter(relevant).toList();
		return wanted.stream().filter(cycle -> wanted.stream()
				.noneMatch(other -> other != cycle && reach.get(other.get(0)).contains(cycle.get(0)))).toList();
	}

	/**
	 * The libraries that are reached over one or more requirements of other libraries, each with the
	 * library it was reached from (breadth first, so the way is the shortest).
	 */
	private static Map<String, String> reachable(final Map<String, Set<String>> graph, final String start) {
		final Map<String, String> from = new LinkedHashMap<>();
		final Deque<String> todo = new ArrayDeque<>(List.of(start));
		while (!todo.isEmpty()) {
			final String current = todo.poll();
			for (final String next : graph.getOrDefault(current, Set.of())) {
				if (!next.equals(current) && from.putIfAbsent(next, current) == null) {
					todo.add(next);
				}
			}
		}
		return from;
	}

	/** The message about libraries that depend on each other, or about a library that requires itself. */
	private static String cycleMessage(final LibraryManagerData data, final Map<String, Set<String>> graph,
			final List<String> cycle) {
		final String first = cycle.get(0);
		if (cycle.size() == 1) {
			final var rnode = data.resolveNodes().get(first);
			return MessageFormat.format(Messages.ErrorMarkerRequiresItself, first, rnode.getVersion(),
					VersionComparator.formatVersionRange(rnode.getDependencies().get(first)));
		}
		// the shortest cycle through the first library
		final Map<String, String> from = reachable(graph, first);
		final LinkedList<String> path = new LinkedList<>(List.of(first));
		for (String name = from.get(first); !name.equals(first); name = from.get(name)) {
			path.add(1, name);
		}
		final String text = Stream.concat(path.stream(), Stream.of(first))
				.map(name -> name + ' ' + data.resolveNodes().get(name).getVersion())
				.collect(Collectors.joining(" -> ")); //$NON-NLS-1$
		final List<String> others = cycle.stream().filter(name -> !path.contains(name)).toList();
		return others.isEmpty() ? MessageFormat.format(Messages.ErrorMarkerCyclicDependency, text)
				: MessageFormat.format(Messages.ErrorMarkerCyclicDependencyMembers, text,
						String.join(", ", others)); //$NON-NLS-1$
	}

	/**
	 * The state of the resolution when a library is visited: everything a visit
	 * reads (the library, the registry of the libraries, the blocked libraries, the
	 * causes and resolved versions of all libraries and the libraries that are
	 * still to be visited in their order). The same text means the same state, and
	 * from the same state the resolution goes the same way again.
	 */
	private String visitState(final LibraryManagerData data, final String symbolicName, final Set<String> queue) {
		final StringBuilder state = new StringBuilder(symbolicName).append('\n');
		synchronized (libraries) {
			state.append(libraries.values().stream().mapToInt(List::size).sum());
		}
		state.append('\n').append(new TreeSet<>(data.blocked())).append('\n');
		new TreeMap<>(data.dependencyNodes()).forEach((name, dnode) -> {
			state.append(name).append(dnode.isChanged() ? '*' : ' ');
			new TreeMap<>(dnode.getCauses())
					.forEach((cause, range) -> state.append(cause).append('=').append(range).append(' '));
			final var rnode = data.resolveNodes().get(name);
			state.append(rnode == null ? "" : rnode.getVersion()).append('\n'); //$NON-NLS-1$
		});
		return state.append(queue).toString();
	}

	private void importDependencyNodes(final IProject project, final LibraryManagerData data,
			final Manifest projectManifest, final List<ErrorMarkerBuilder> markerList, final SubMonitor progress) {
		Stream.concat(
				data.reports().entrySet().stream().map(report -> LibraryMarkerFactory
						.createCyclicDependencyMarker(projectManifest, report.getValue(), report.getKey())),
				data.conflicts().values().stream()
						.map(conflict -> LibraryMarkerFactory.createDependencyMarker(projectManifest, conflict)))
				.forEach(marker -> {
					markerList.add(marker);
					ILog.get().error(marker.getMessage());
				});
		for (final var dnode : data.dependencyNodes().values()) {
			if (data.blocked().contains(dnode.getSymbolicName())) {
				continue;
			}
			if (dnode.isValid()) {
				final var rnode = data.resolveNodes().get(dnode.getSymbolicName());

				if (rnode == null) {
					ILog.get().warn(MessageFormat.format(Messages.LibraryManager_UnresolvedDependency,
							dnode.getSymbolicName(), project.getName()));
					continue;
				}

				if (rnode.isValid()) {
					if (rnode.requireImport(data.linked())) {
						importLibrary(project, rnode.getUri(), false, false);
					}
					if (!rnode.isReferenced()) {
						data.linked().remove(rnode.getSymbolicName());
					}
				} else {
					final var marker = LibraryMarkerFactory.createDependencyMarker(projectManifest, rnode, dnode);
					markerList.add(marker);
					ILog.get().error(marker.getMessage());
				}
			} else if (dnode.isRangeEmpty()) {
				markerList.add(LibraryMarkerFactory.createDependencyMarker(projectManifest, dnode));
			}
		}
	}

	private static void cleanupLinks(final Map<String, LinkedLibrary> linked, final SubMonitor progress)
			throws CoreException {
		progress.setTaskName(Messages.LibraryManager_RemovingUnnecessaryLinks);
		progress.setWorkRemaining(linked.size());
		final List<IFolder> links = linked.values().stream().filter(LinkedLibrary::isValid)
				.map(LinkedLibrary::getFolder).toList();
		for (final IFolder link : links) {
			link.delete(true, progress.split(1));
		}
	}

	/**
	 * Find linked/preferred versions of libraries
	 *
	 * @param project   selected project
	 * @param preferred map to fill with preferred version
	 * @param linked    set to fill with symbolic names of linked libraries
	 * @param progress  SubMonitor for progress reporting
	 */
	private static void findPreferred(final IProject project, final LibraryManagerData data, final SubMonitor progress)
			throws CoreException {
		progress.beginTask(Messages.LibraryManager_FindingPreferredLibraryVersion, 100);
		LinkedLibrary.getAll(project, progress).forEach(folder -> data.linked().put(folder.getSymbolicName(), folder));
	}

	public java.net.URI getLibraryURI(final IProject project, final String symbolicName, final Version version,
			final IProgressMonitor progress) {
		return getLibraryURI(project, symbolicName, version, new DownloadSources(), progress);
	}

	java.net.URI getLibraryURI(final IProject project, final String symbolicName, final Version version,
			final DownloadSources sources, final IProgressMonitor progress) {
		final ResolveNode node = resolveDependency(project, symbolicName, ALL_RANGE, version,
				SubMonitor.convert(progress), Collections.emptyMap(), sources);
		if (node.isValid()) {
			return node.getUri();
		}
		return null;
	}

	/**
	 * Resolves the given dependency. Will download libraries as needed.
	 *
	 * @param symbolicName name of the library
	 * @param range        version range of the dependency
	 * @param prefVersion  preferred version, can be {@code null}
	 * @param progress     SubMonitor for progress report
	 * @param sources      the library sources of the current operation
	 * @return
	 */
	private ResolveNode resolveDependency(final IProject project, final String symbolicName, final VersionRange range,
			final Version prefVersion, final SubMonitor progress, final Map<String, List<Version>> referenced,
			final DownloadSources sources) {
		final boolean usePref = prefVersion != null && range.includes(prefVersion);
		LibraryRecord rec;

		progress.setTaskName(Messages.LibraryManager_ResolvingDependency + symbolicName);
		progress.setWorkRemaining(100);

		if (isProvidedByReference(referenced, symbolicName, range)) {
			return new ResolveNode(symbolicName, prefVersion, project);
		}

		if (stdlibraries.containsKey(symbolicName)) {
			if (usePref) {
				rec = getLibraryRecord(stdlibraries, symbolicName, prefVersion);
				if (rec != null) {
					return new ResolveNode(rec);
				}
			}
			rec = getLibraryRecord(stdlibraries, symbolicName, range);
			if (rec != null) {
				return new ResolveNode(rec);
			}
			return new ResolveNode(symbolicName, Messages.ErrorMarkerStandardLibNotAvailable);
		}
		if (usePref) {
			rec = getLibraryRecord(libraries, symbolicName, prefVersion);
			if (rec != null) {
				return new ResolveNode(rec);
			}
		} else {
			rec = getLibraryRecord(libraries, symbolicName, range);
			if (rec != null) {
				return new ResolveNode(rec);
			}
		}
		progress.worked(5);

		final DownloadResult<java.net.URI> dlResult = libraryDownload(symbolicName, range, prefVersion, project, false,
				false, sources, progress.split(95));

		if (dlResult.status() == DownloadResult.Status.OK) {
			rec = getLibraryRecord(libraries, symbolicName, dlResult.result());
			if (rec != null) {
				return new ResolveNode(rec);
			}
		} else {
			ILog.get().warn(dlResult.message());
		}

		if (usePref) {
			rec = getLibraryRecord(libraries, symbolicName, range);
			if (rec != null) {
				return new ResolveNode(rec);
			}
		}

		return new ResolveNode(symbolicName, Messages.ErrorMarkerLibNotAvailable + dlResult.message());
	}

	private static boolean isProvidedByReference(final Map<String, List<Version>> referenced, final String symbolicName,
			final VersionRange versionRange) {
		return referenced.getOrDefault(symbolicName, Collections.emptyList()).stream().anyMatch(versionRange::includes);

	}

	/**
	 * Collect provided libraries from referenced projects
	 *
	 */
	private static void collectReferencedDependencies(final IProject project,
			final Map<String, List<Version>> referenced) throws CoreException {
		final IProject[] projects = project.getReferencedProjects();

		for (final IProject refProject : projects) {
			if (!refProject.isAccessible()) {
				continue;
			}
			final Manifest manifest = ManifestHelper.getContainerManifest(refProject);
			final String symbolicName = ManifestHelper.getSymbolicName(manifest, refProject.getName());
			final Version version = ManifestHelper.getVersion(manifest, Version.emptyVersion);
			referenced.computeIfAbsent(symbolicName, _ -> new ArrayList<>()).add(version);
		}

	}

	private static List<LibraryRecord> getLibraryRecords(final Map<String, List<LibraryRecord>> libs,
			final String symbolicName) {
		synchronized (libs) {
			return List.copyOf(libs.getOrDefault(symbolicName, Collections.emptyList()));
		}
	}

	/**
	 * Search for existing library based on symbolic name and version
	 *
	 * @param libs         library map to search
	 * @param symbolicName symbolic name
	 * @param version      specific version
	 * @return library record if found, otherwise {@code null}
	 */
	private static LibraryRecord getLibraryRecord(final Map<String, List<LibraryRecord>> libs,
			final String symbolicName, final Version version) {
		return getLibraryRecords(libs, symbolicName).stream()
				.filter(l -> l.version().equals(version)).findFirst().orElse(null);
	}

	/**
	 * Search for existing library based on symbolic name and version range
	 *
	 * @param libs         library map to search
	 * @param symbolicName symbolic name
	 * @param range        version range
	 * @return library record if found, otherwise {@code null}
	 */
	private static LibraryRecord getLibraryRecord(final Map<String, List<LibraryRecord>> libs,
			final String symbolicName, final VersionRange range) {
		return getLibraryRecords(libs, symbolicName).stream()
				.filter(l -> range.includes(l.version())).sorted((o1, o2) -> o2.version().compareTo(o1.version()))
				.findFirst().orElse(null);
	}

	/**
	 * Search for existing library based on symbolic name and folder URI
	 *
	 * @param libs         library map to search
	 * @param symbolicName symbolic name
	 * @param uri          URI of the library folder
	 * @return library record if found, otherwise {@code null}
	 */
	private static LibraryRecord getLibraryRecord(final Map<String, List<LibraryRecord>> libs,
			final String symbolicName, final java.net.URI uri) {
		return getLibraryRecords(libs, symbolicName).stream().filter(l -> l.uri().equals(uri))
				.sorted((o1, o2) -> o2.version().compareTo(o1.version())).findFirst().orElse(null);
	}

	private static Path getStandardLibPath() {
		// go why a java file to handle any special characters in the installation
		// location URL
		final File installLocationFile = new File(Platform.getInstallLocation().getURL().getPath());
		final Path fordiacInstallPath = installLocationFile.toPath();
		return fordiacInstallPath.resolve(TypeLibraryTags.TYPE_LIBRARY);
	}

	private static boolean isUpdateProvisioningJob(final Job job) {
		return job instanceof IProfileChangeJob && UPDATE_JOB_NAME.equals(job.getName());
	}

}
