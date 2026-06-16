/*******************************************************************************
 * Copyright (c) 2024, 2026 Primetals Technologies Austria GmbH and others
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import java.io.File;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
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
import org.eclipse.fordiac.ide.library.download.IArchiveDownloader;
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
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

public enum LibraryManager {

	INSTANCE;

	private static final String UPDATE_JOB_NAME = "Updating Software"; //$NON-NLS-1$

	public static final String LIB_TYPELIB_FOLDER_NAME = "typelib"; //$NON-NLS-1$
	public static final String PACKAGE_DOWNLOAD_DIRECTORY = ".download"; //$NON-NLS-1$
	public static final String EXTRACTED_LIB_DIRECTORY = ".lib"; //$NON-NLS-1$
	public static final String MANIFEST = "MANIFEST.MF"; //$NON-NLS-1$
	public static final String DOWNLOADER_EXTENSION = "org.eclipse.fordiac.ide.library.ArchiveDownloaderExtension"; //$NON-NLS-1$

	private final java.net.URI workspaceLibraryURI = java.net.URI.create("WORKSPACE_LOC/" + EXTRACTED_LIB_DIRECTORY); //$NON-NLS-1$

	private final Path workspacePath = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPath();
	private final Path libraryPath = workspacePath.resolve(EXTRACTED_LIB_DIRECTORY);
	private final Path archivePath = workspacePath.resolve(PACKAGE_DOWNLOAD_DIRECTORY);

	private final java.net.URI standardLibraryUri = java.net.URI.create("ECLIPSE_HOME/" + TypeLibraryTags.TYPE_LIBRARY); //$NON-NLS-1$
	private final Path standardLibraryPath = getStandardLibPath();

	public static final Set<String> LIBRARY_FOLDERS = Set.of(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME,
			TypeLibraryTags.STANDARD_LIB_FOLDER_NAME);

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

	private record LibraryManagerData(Map<String, DependencyNode> dependencyNodes,
			Map<String, ResolveNode> resolveNodes, Map<String, Version> preferred, Map<String, IFolder> linked,
			Map<String, List<Version>> referenced) {
		public static LibraryManagerData init() {
			return new LibraryManagerData(new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(),
					new HashMap<>());
		}
	}

	LibraryManager() {
		initLibraryMap(stdlibraries, standardLibraryPath, standardLibraryUri);
		if (!Files.exists(libraryPath)) {
			try {
				Files.createDirectory(libraryPath);
			} catch (final IOException e) {
				FordiacLogHelper.logError("Cannot create lib path!", e); //$NON-NLS-1$
			}
		}
		initLibraryMap(libraries, libraryPath, workspaceLibraryURI);
		try {
			watchService = FileSystems.getDefault().newWatchService();
			libraryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE);
		} catch (final IOException e) {
			FordiacLogHelper.logError("Cannot register watch watch service!", e); //$NON-NLS-1$
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
				if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
					addLibrary(libraries, libraryPath.resolve((Path) event.context()), workspaceLibraryURI);
				} else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
					removeLibrary(libraries, libraryPath.resolve((Path) event.context()));
				} else { // Overflow -> reinitialise libraries to ensure correct state
					initLibraryMap(libraries, libraryPath, workspaceLibraryURI);
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
					map.computeIfAbsent(manifest.getProduct().getSymbolicName(), s -> new ArrayList<>())
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
	 * Remove {@link LibraryRecord} from the given map based on the {@link Path}
	 *
	 * @param map  target map
	 * @param path path of the library folder
	 */
	private static void removeLibrary(final Map<String, List<LibraryRecord>> map, final Path path) {
		String folderName = path.getFileName().toString();
		final int pos = folderName.lastIndexOf('-');
		if (pos > 0) {
			folderName = folderName.substring(0, pos);
		}
		final List<LibraryRecord> records = map.get(folderName);
		if (records != null) {
			records.removeIf(r -> r.path().equals(path));
			if (records.isEmpty()) {
				map.remove(folderName);
			}
		} else {
			map.values().forEach(rl -> rl.removeIf(r -> r.path().equals(path)));
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
			// FIXME: AF: FileNotFoundException would be much more clear
			return null;
		}
		final Path real = path.toRealPath();
		if (!Files.isRegularFile(real)) {
			// FIXME: AF: FileNotFoundException would be much more clear
			return null;
		}
		FordiacLogHelper.logInfo("Extracting library at " + real); //$NON-NLS-1$
		final byte[] buffer = new byte[1024];
		String folderName;
		try (InputStream inputStream = Files.newInputStream(real);
				ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
			ZipEntry entry = zipInputStream.getNextEntry();
			folderName = ""; //$NON-NLS-1$
			if (entry != null) {
				folderName = entry.getName();
				deleteLibFolder(newPath(libraryPath, entry));
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

		FordiacLogHelper.logInfo("Importing library at " + uri + " into project " + project.getName() + " (update=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
			FordiacLogHelper.logError(MessageFormat.format(Messages.ImportFailedOnLinkCreation, e.getMessage()), e);
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
		return new HashMap<>(libraries);
	}

	/**
	 * Uses registered {@link IArchiveDownloader} to download specified library.
	 * Will download the latest version if versionRange is {@code null} or empty.
	 *
	 * <p>
	 * See {@link IArchiveDownloader#downloadLibrary} for more info
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
	 * @param progress     SubMonitor for progress report
	 * @return {@link java.net.URI} of the extracted library folder encapsulated in
	 *         a {@link DownloadResult}
	 */
	private DownloadResult<java.net.URI> libraryDownload(final String symbolicName, final VersionRange versionRange,
			final Version preferred, final IProject project, final boolean autoImport, final boolean resolve,
			final SubMonitor progress) throws OperationCanceledException {
		progress.setTaskName(MessageFormat.format(Messages.LibraryManager_LibraryDownload, symbolicName));
		FordiacLogHelper.logInfo("Attempting to download library " + symbolicName + " with version " + versionRange //$NON-NLS-1$ //$NON-NLS-2$
				+ " preferring " + preferred + " Project: " + project != null ? project.getName() : ""); //$NON-NLS-1$

		List<IArchiveDownloader> downloaders = TypeLibraryManager.listExtensions(DOWNLOADER_EXTENSION,
				IArchiveDownloader.class);

		if (downloaders.size() == 1 && downloaders.get(0).hasMultipleEndpoints()) {
			downloaders = downloaders.get(0).convertEndpointsToDownloader();
		}

		DownloadResult<Path> dlResult;
		final StringBuilder errors = new StringBuilder();
		final VersionRange range = (versionRange == null || versionRange.isEmpty()) ? ALL_RANGE : versionRange;
		final Version pref = (preferred != null && range.includes(preferred)) ? preferred : null;
		progress.setWorkRemaining(downloaders.size());
		for (final var downloader : downloaders) {
			if (!downloader.isActive()) {
				progress.worked(1);
				continue;
			}
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
				FordiacLogHelper.logError(e.getMessage(), e);
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
						true, SubMonitor.convert(monitor));
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

		final Queue<String> queue = new LinkedList<>(); // symbolicNames

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
					new DependencyNode(req.getSymbolicName(), "Project", //$NON-NLS-1$
							VersionComparator.parseVersionRange(req.getVersion())));
			queue.add(req.getSymbolicName());
		});

		buildDependencies(project, libManagerData, queue, progress.split(70));

		final List<ErrorMarkerBuilder> markerList = new LinkedList<>();

		// import valid nodes
		importDependencyNodes(project, libManagerData, projectManifest, markerList, progress.split(15));

		// remove still linked libraries
		cleanupLinks(libManagerData.linked(), progress.split(2));

		// check if imported library links are broken
		checkLinkedLibraries(project, progress.split(1));

		if (PreferenceProvider.getBoolean(LibraryPreferenceConstants.LIBRARY_PREFERENCES_ID,
				LibraryPreferenceConstants.FORCE_LOAD_DEPENDENCIES, false, project)) {
			// force load explicitly defined dependencies
			final List<Required> explicitDeps = projectManifest.getDependencies().getRequired().stream()
					.filter(r -> !r.getVersion().contains("-") //$NON-NLS-1$
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
						true, false, progress.split(1));
			}

		} else {
			progress.worked(3);
		}

		final int maxSeverity = markerList.stream().mapToInt(ErrorMarkerBuilder::getSeverity).max().orElse(-1);
		if (maxSeverity >= IMarker.SEVERITY_ERROR) {
			markerList.add(ErrorMarkerBuilder.createErrorMarkerBuilder(Messages.LibraryManager_UnresolvableDependencies)
					.setType(FordiacErrorMarker.LIBRARY_MARKER));
		}

		FordiacMarkerHelper.updateMarkers(project.getFile(MANIFEST), FordiacErrorMarker.LIBRARY_MARKER, markerList,
				true);

		TypeLibraryManager.INSTANCE.getTypeLibrary(project).refresh();

		if (maxSeverity >= IMarker.SEVERITY_ERROR) {
			throw new OperationCanceledException("Unresolvable dependencies"); //$NON-NLS-1$
		}
	}

	public Stream<Version> getAllAvailableVersions(final String symbolicName) {
		return Stream.concat(getAvailableVersions(getExtractedLibraries(), symbolicName),
				getAvailableVersions(getStandardLibraries(), symbolicName));
	}

	public static List<LibraryRecord> getLinkedLibraries(final IFolder root) {
		final List<LibraryRecord> libs = new ArrayList<>();
		try {
			root.accept(resource -> {
				if (resource.equals(root)) {
					return true;
				}
				if (resource instanceof final IFolder libFolder) {
					if (!libFolder.exists() || !libFolder.isLinked()) {
						return false;
					}
					final Manifest manifest = ManifestHelper.getContainerManifest(libFolder);
					if (manifest != null && manifest.getProduct() != null) {
						libs.add(new LibraryRecord(ManifestHelper.getSymbolicName(manifest, ""), //$NON-NLS-1$
								manifest.getProduct().getName(),
								ManifestHelper.getVersion(manifest, Version.emptyVersion),
								manifest.getProduct().getComment(), libFolder.getLocation().toPath(),
								libFolder.getLocationURI()));
					}
				}
				return false;
			});
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		return libs;
	}

	/**
	 * Checks if a given link inside the library folders is broken.
	 *
	 * <p>
	 * This method checks if the existing links are broken, creates error markers
	 * and will eventually abort the build.
	 *
	 * @param project selected project
	 */
	private static void checkLinkedLibraries(final IProject project, final SubMonitor progress) {
		progress.setTaskName(Messages.LibraryManager_CheckLinks);
		progress.setWorkRemaining(10);

		LIBRARY_FOLDERS.stream().map(project::getFolder).forEach(folder -> {
			try {
				folder.accept(resource -> {
					if (resource.equals(folder)) {
						return true;
					}
					if (resource instanceof final IFolder libFolder && libFolder.exists() && libFolder.isLinked()) {
						if (libFolder.getModificationStamp() == IResource.NULL_STAMP) {
							FordiacMarkerHelper.updateMarkers(resource, FordiacErrorMarker.LIBRARY_MARKER,
									List.of(LibraryMarkerFactory.createBrokenLinkMarker(libFolder)), true);
							throw new OperationCanceledException();
						}
						progress.worked(1);
					}
					return false;
				});
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		});
	}

	private void buildDependencies(final IProject project, final LibraryManagerData data, final Queue<String> queue,
			final SubMonitor progress) throws OperationCanceledException {
		progress.setTaskName(Messages.LibraryManager_BuildingDependencyGraph);

		while (!queue.isEmpty()) {
			progress.setWorkRemaining(Math.max(queue.size(), 10));
			final String symbolicName = queue.poll();

			final var dnode = data.dependencyNodes().get(symbolicName);

			if (!dnode.isChanged()) {
				continue;
			}

			if (!dnode.isValid()) {
				final var rnode = data.resolveNodes().get(symbolicName);
				if (rnode != null) {
					rnode.getDependencies().keySet().forEach(symb -> {
						final var dn = data.dependencyNodes().get(symb);
						if (dn != null) {
							dn.removeCause(symbolicName);
							if (dn.isChanged()) {
								queue.add(symb);
							}
						}
					});
				}
				continue;
			}

			// resolve dependency
			final var rnode = resolveDependency(project, symbolicName, dnode.getRange(),
					data.preferred().get(symbolicName), progress.split(1), data.referenced());

			if (data.resolveNodes().containsKey(symbolicName)) {
				final var oldRNode = data.resolveNodes().get(symbolicName);
				oldRNode.getDependencies().keySet().forEach(old -> {
					if (!rnode.getDependencies().containsKey(old)) {
						data.dependencyNodes().get(old).removeCause(symbolicName);
					}
				});
			}

			data.resolveNodes().put(symbolicName, rnode);

			// updated dependencies
			rnode.getDependencies().forEach((symb, val) -> {
				final var dn = data.dependencyNodes().computeIfAbsent(symb, s -> new DependencyNode(symb));
				dn.putCause(symbolicName, val);
				if (dn.isChanged()) {
					queue.add(symb);
				}
			});

			if (rnode.getError() != null) {
				break;
			}
		}
	}

	private void importDependencyNodes(final IProject project, final LibraryManagerData data,
			final Manifest projectManifest, final List<ErrorMarkerBuilder> markerList, final SubMonitor progress) {
		for (final var dnode : data.dependencyNodes().values()) {
			if (dnode.isValid()) {
				final var rnode = data.resolveNodes().get(dnode.getSymbolicName());

				if (rnode.isValid()) {
					if (rnode.requireImport(data.linked(), data.preferred())) {
						importLibrary(project, rnode.getUri(), false, false);
					}
					if (!rnode.isReferenced()) {
						data.linked().remove(rnode.getSymbolicName());
					}
				} else {
					markerList.add(LibraryMarkerFactory.createDependencyMarker(projectManifest, rnode, dnode));
				}
			} else if (dnode.isRangeEmpty()) {
				markerList.add(LibraryMarkerFactory.createDependencyMarker(projectManifest, dnode));
			}
		}
	}

	private static void cleanupLinks(final Map<String, IFolder> linked, final SubMonitor progress)
			throws CoreException {
		progress.setTaskName(Messages.LibraryManager_RemovingUnnecessaryLinks);
		progress.setWorkRemaining(linked.size());
		for (final IFolder folder : linked.values()) {
			folder.delete(true, progress.split(1));
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
	private static void findPreferred(final IProject project, final LibraryManagerData data,
			final SubMonitor progress) {
		final IFolder standardLibFolder = project.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME);
		final IFolder externalLibFolder = project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		if (!standardLibFolder.exists() || !externalLibFolder.exists()) {
			return;
		}
		progress.beginTask(Messages.LibraryManager_FindingPreferredLibraryVersion, 100);
		final IResourceVisitor visitor = res -> {
			if (res instanceof final IFolder libFolder) {
				progress.setWorkRemaining(100).worked(1);
				if (standardLibFolder.equals(libFolder) || externalLibFolder.equals(libFolder)) {
					return true;
				}
				if (!libFolder.exists() || !libFolder.isLinked()) {
					return false;
				}
				final Manifest libManifest = ManifestHelper.getContainerManifest(libFolder);
				if (libManifest != null) {
					data.linked().put(libFolder.getName(), libFolder);
					data.preferred().put(libFolder.getName(),
							new Version(libManifest.getProduct().getVersionInfo().getVersion()));
				} else {
					final Version version = parseLibraryVersion(libFolder);
					if (!version.equals(Version.emptyVersion)) {
						data.preferred().put(libFolder.getName(), version);
					}
				}
			}
			return false;
		};
		try {
			standardLibFolder.accept(visitor);
			externalLibFolder.accept(visitor);
		} catch (final CoreException e) {
			// empty
		}
	}

	/**
	 * Parses the Library Version of the folders raw location if possible
	 *
	 * @param the folder
	 * @return
	 */
	static Version parseLibraryVersion(final IFolder libraryFolder) {
		final IPath path = libraryFolder.getRawLocation();
		final String segment = (path != null && path.segmentCount() >= 2) ? path.segment(path.segmentCount() - 2) : ""; //$NON-NLS-1$
		final int index = segment.lastIndexOf('-');
		if (index > 0) {
			return new Version(segment.substring(index + 1));
		}
		return Version.emptyVersion;
	}

	/**
	 * Resolves the given dependency. Will download libraries as needed.
	 *
	 * @param symbolicName name of the library
	 * @param range        version range of the dependency
	 * @param prefVersion  preferred version, can be {@code null}
	 * @param progress     SubMonitor for progress report
	 * @return
	 */
	private ResolveNode resolveDependency(final IProject project, final String symbolicName, final VersionRange range,
			final Version prefVersion, final SubMonitor progress, final Map<String, List<Version>> referenced) {
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
				false, progress.split(95));

		if (dlResult.status() == DownloadResult.Status.OK) {
			rec = getLibraryRecord(libraries, symbolicName, dlResult.result());
			if (rec != null) {
				return new ResolveNode(rec);
			}
		} else {
			FordiacLogHelper.logWarning(dlResult.message());
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
			referenced.computeIfAbsent(symbolicName, name -> new ArrayList<>()).add(version);
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
		return libs.getOrDefault(symbolicName, Collections.emptyList()).stream()
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
		return libs.getOrDefault(symbolicName, Collections.emptyList()).stream()
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
		return libs.getOrDefault(symbolicName, Collections.emptyList()).stream().filter(l -> l.uri().equals(uri))
				.sorted((o1, o2) -> o2.version().compareTo(o1.version())).findFirst().orElse(null);
	}

	private static Path getStandardLibPath() {
		// go why a java file to handle any special characters in the installation
		// location URL
		final File installLocationFile = new File(Platform.getInstallLocation().getURL().getPath());
		final Path fordiacInstallPath = installLocationFile.toPath();
		return fordiacInstallPath.resolve(TypeLibraryTags.TYPE_LIBRARY);
	}

	private static Stream<Version> getAvailableVersions(final Map<String, List<LibraryRecord>> lib,
			final String symbolicName) {
		return lib.getOrDefault(symbolicName, Collections.emptyList()).stream().map(LibraryRecord::version);
	}

	private static boolean isUpdateProvisioningJob(final Job job) {
		return job instanceof IProfileChangeJob && UPDATE_JOB_NAME.equals(job.getName());
	}

}
