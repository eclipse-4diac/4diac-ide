/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.internal.resources.ProjectPathVariableManager;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
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
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.errormarker.FordiacErrorMarker;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.model.typelibrary.impl.TypeEntryFactory;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.changelistener.FordiacResourceChangeListener;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;
import org.osgi.service.event.EventHandler;

public enum LibraryManager {

	INSTANCE;

	public static final String TYPE_LIB_FOLDER_NAME = "Type Library"; //$NON-NLS-1$
	public static final String LIB_TYPELIB_FOLDER_NAME = "typelib"; //$NON-NLS-1$
	public static final String PACKAGE_DOWNLOAD_DIRECTORY = ".download"; //$NON-NLS-1$
	public static final String EXTRACTED_LIB_DIRECTORY = ".lib"; //$NON-NLS-1$
	public static final String MANIFEST = "MANIFEST.MF"; //$NON-NLS-1$

	private static final java.net.URI STANDARD_LIBRARY_URI = java.net.URI
			.create("ECLIPSE_HOME/" + TypeLibraryTags.TYPE_LIBRARY); //$NON-NLS-1$
	private static final java.net.URI WORKSPACE_DOWNLOAD_URI = java.net.URI
			.create("WORKSPACE_LOC/" + PACKAGE_DOWNLOAD_DIRECTORY); //$NON-NLS-1$
	private static final java.net.URI WORKSPACE_LIBRARY_URI = java.net.URI
			.create("WORKSPACE_LOC/" + EXTRACTED_LIB_DIRECTORY); //$NON-NLS-1$

	private static final Path WORKSPACE_PATH = ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toPath();
	private static final Path LIBRARY_PATH = WORKSPACE_PATH.resolve(EXTRACTED_LIB_DIRECTORY);
	private static final Path ARCHIVE_PATH = WORKSPACE_PATH.resolve(PACKAGE_DOWNLOAD_DIRECTORY);
	private Path standardLibraryPath;

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

	private final VersionComparator versionComparator = new VersionComparator();
	private static final VersionRange ALL_RANGE = new VersionRange(VersionRange.LEFT_CLOSED, Version.emptyVersion, null,
			VersionRange.RIGHT_CLOSED);

	private WatchService watchService;
	private final HashMap<String, List<LibraryRecord>> stdlibraries = new HashMap<>();
	private final HashMap<String, List<LibraryRecord>> libraries = new HashMap<>();

	private IEventBroker eventBroker;
	private boolean uninitialised = true;

	/**
	 * Initialise library maps and start the {@link WatchService}
	 *
	 * <p>
	 * A project is needed to obtain a {@link ProjectPathVariableManager} which is
	 * needed to properly resolve URIs that use environment variables such as
	 * {@link #STANDARD_LIBRARY_URI}
	 *
	 * @param project project used to obtain the appropriate
	 *                {@link IPathVariableManager}
	 */
	@SuppressWarnings("restriction")
	private void init(final IProject project) {
		final IPathVariableManager varMan = project.getPathVariableManager();
		standardLibraryPath = Paths.get(varMan.resolveURI(STANDARD_LIBRARY_URI));
		initLibraryMap(stdlibraries, standardLibraryPath, STANDARD_LIBRARY_URI);
		initLibraryMap(libraries, LIBRARY_PATH, WORKSPACE_LIBRARY_URI);
		try {
			watchService = FileSystems.getDefault().newWatchService();
			LIBRARY_PATH.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_DELETE);
		} catch (final IOException e) {
			// empty
		}
		uninitialised = false;
	}

	/**
	 * Poll the {@link WatchService} for changes in the library folder and react
	 * accordingly
	 */
	private void checkLibChanges() {
		if (watchService == null) {
			return;
		}
		final WatchKey watchKey = watchService.poll();
		if (watchKey == null) {
			return;
		}

		watchKey.pollEvents().forEach(event -> {
			try {
				if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
					addLibrary(libraries, LIBRARY_PATH.resolve((Path) event.context()), WORKSPACE_LIBRARY_URI);
				} else if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
					removeLibrary(libraries, LIBRARY_PATH.resolve((Path) event.context()));
				} else { // Overflow -> reinitialise libraries to ensure correct state
					initLibraryMap(libraries, LIBRARY_PATH, WORKSPACE_LIBRARY_URI);
					return;
				}
			} catch (final IOException e) {
				// empty
			}
		});
		watchKey.reset();
	}

	/**
	 * Initialise map with all libraries contained in the folder specified
	 *
	 * @param map     map to initialise
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
		if (path == null || Files.notExists(path)) {
			return null;
		}
		final byte[] buffer = new byte[1024];
		String folderName;
		try (InputStream inputStream = Files.newInputStream(path);
				ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
			ZipEntry entry = zipInputStream.getNextEntry();
			folderName = entry != null ? entry.getName() : ""; //$NON-NLS-1$
			while (entry != null) {
				final Path newFile = newPath(LIBRARY_PATH, entry);
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
				}
				entry = zipInputStream.getNextEntry();
			}
		}
		checkLibChanges();

		final java.net.URI importURI = URIUtil.append(WORKSPACE_LIBRARY_URI, folderName);

		if (autoImport && project != null) {
			// Parent's name because we want package-version name when importing
			importLibrary(project, null, importURI, true, resolve);
		}
		return importURI;
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
		final Path destPath = destinationDir.resolve(zipEntry.getName());

		if (!destPath.startsWith(destinationDir)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName()); //$NON-NLS-1$
		}
		return destPath;
	}

	/**
	 * Import library into {@link IProject}
	 *
	 * @param project     target project
	 * @param typeLibrary {@link TypeLibrary} to use, will be retrieved through
	 *                    {@link TypeLibraryManager} if {@code null}
	 * @param uri         URI of the library folder
	 * @param update      define if a dependency gets created/updated in the
	 *                    {@link Manifest}
	 * @param resolve     define if dependencies should get resolved on import
	 */
	public void importLibrary(final IProject project, final TypeLibrary typeLibrary, final java.net.URI uri,
			final boolean update, final boolean resolve) {
		boolean imported = false;
		if (uninitialised) {
			init(project);
		}
		final TypeLibrary typeLib = typeLibrary != null ? typeLibrary
				: TypeLibraryManager.INSTANCE.getTypeLibrary(project);
		checkLibChanges();
		final IPathVariableManager varMan = project.getPathVariableManager();
		final java.net.URI resolvedUri = varMan.resolveURI(uri);
		final Path path = Paths.get(resolvedUri);
		if (!Files.isDirectory(path) || Files.notExists(path.resolve(LIB_TYPELIB_FOLDER_NAME))) {
			return;
		}
		final Manifest libManifest = ManifestHelper.getFolderManifest(path);
		if (libManifest == null) {
			return;
		}
		final Manifest projManifest = ManifestHelper.getOrCreateProjectManifest(project);
		final IFolder libDirectory = project.getFolder(TYPE_LIB_FOLDER_NAME)
				.getFolder(libManifest.getProduct().getSymbolicName());

		SystemManager.INSTANCE.removeFordiacChangeListener();
		final Map<String, TypeEntry> cachedTypes = removeOldLibVersion(libDirectory, typeLib);
		final java.net.URI libUri = URIUtil.append(uri, LIB_TYPELIB_FOLDER_NAME);
		final java.net.URI manUri = URIUtil.append(uri, MANIFEST);
		try {
			libDirectory.createLink(libUri, IResource.NONE, null);
			final IFile man = libDirectory.getFile(MANIFEST);
			man.createLink(manUri, IResource.HIDDEN, null);
			if (update) {
				ManifestHelper.updateDependency(projManifest,
						ManifestHelper.createRequired(libManifest.getProduct().getSymbolicName(),
								libManifest.getProduct().getVersionInfo().getVersion()));
				projManifest.eResource().save(null);
			}
			createTypeEntriesManually(libDirectory, cachedTypes, typeLib);
			if (!cachedTypes.isEmpty()) {
				final List<TypeEntry> affectedTypes = new ArrayList<>(cachedTypes.values());
				// clean up if there was a version before
				cleanupOldLibraryVersion(cachedTypes, typeLib);
				// show affected elements
				showUpdatedElements(affectedTypes);
			}
			imported = true;
		} catch (final CoreException | IOException e) {
			Display.getDefault().syncExec(() -> MessageDialog.openWarning(null, Messages.Warning,
					MessageFormat.format(Messages.ImportFailedOnLinkCreation, e.getMessage())));
		}

		SystemManager.INSTANCE.addFordiacChangeListener();
		// dependency resolution
		if (resolve && imported) {
			final WorkspaceJob job = new WorkspaceJob(
					"Resolve dependencies for: " + libManifest.getProduct().getSymbolicName()) { //$NON-NLS-1$
				@Override
				public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
					resolveDependencies(project, typeLib);
					return Status.OK_STATUS;
				}
			};
			job.setRule(project);
			job.setPriority(Job.LONG);
			job.schedule();
		}
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
			importLibrary(project, null, uri, true, resolve);
		}
	}

	/**
	 * Remove old library version
	 *
	 * @param libDirectory library directory in project
	 * @param typeLibrary  {@link TypeLibrary} to use
	 * @return Map containing all {@link TypeEntry} of the removed library
	 */
	private static Map<String, TypeEntry> removeOldLibVersion(final IFolder libDirectory,
			final TypeLibrary typeLibrary) {
		if (libDirectory.exists()) {
			try {
				final Map<String, TypeEntry> existingTypes = findExistingTypes(libDirectory, typeLibrary);
				// Remove the link but keep the resource on disk
				libDirectory.delete(true, null);
				return existingTypes;
			} catch (final CoreException e) {
				Display.getDefault().syncExec(() -> MessageDialog.openWarning(null, Messages.Warning,
						Messages.OldTypeLibVersionCouldNotBeDeleted));
			}
		}
		return Collections.emptyMap();
	}

	/**
	 * Find all existing types contained in a project directory
	 *
	 * @param folder      directory in project
	 * @param typeLibrary {@link TypeLibrary} to use
	 * @return Map containing all {@link TypeEntry} of the library
	 */
	private static Map<String, TypeEntry> findExistingTypes(final IFolder folder, final TypeLibrary typeLibrary) {
		final Map<String, TypeEntry> existingTypes = new HashMap<>();

		final IResourceVisitor visitor = resource -> (switch (resource) {
		case final IFolder f -> true;
		case final IFile file when TYPE_ENDINGS.contains(file.getFileExtension().toUpperCase()) -> {
			final TypeEntry entry = typeLibrary.getTypeEntry(file);
			existingTypes.put(entry.getFullTypeName(), entry);
			yield false;
		}
		default -> false;
		});
		try {
			folder.accept(visitor);
			return existingTypes;
		} catch (final CoreException e) {
			// do nothing
		}

		return Collections.emptyMap();
	}

	/**
	 * Create {@link TypeEntry} contained in project folder
	 *
	 * @param folder      directory in project
	 * @param cachedTypes old types that need to be replaced
	 * @param typeLibrary {@link TypeLibrary} to use
	 */
	private static void createTypeEntriesManually(final IFolder folder, final Map<String, TypeEntry> cachedTypes,
			final TypeLibrary typeLibrary) {
		final IResourceVisitor visitor = resource -> (switch (resource) {
		case final IFolder f -> true;
		case final IFile file -> {
			final TypeEntry entry = TypeEntryFactory.INSTANCE.createTypeEntry(file);
			final TypeEntry oldEntry = cachedTypes.get(entry.getFullTypeName());
			if (oldEntry != null) {
				FordiacResourceChangeListener.updateTypeEntry(file, oldEntry);
				cachedTypes.remove(entry.getFullTypeName());
			} else {
				typeLibrary.createTypeEntry(file);
			}
			yield false;
		}
		default -> false;
		});
		try {
			folder.accept(visitor);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	/**
	 * Removes {@link TypeEntry} from library
	 *
	 * @param cachedTypes old types that need to be removed
	 * @param typeLibrary {@link TypeLibrary} to use
	 */
	private static void cleanupOldLibraryVersion(final Map<String, TypeEntry> cachedTypes,
			final TypeLibrary typeLibrary) {
		// remove obsolete types
		cachedTypes.values().forEach(typeLibrary::removeTypeEntry);
	}

	/**
	 * Show dialog listing {@link TypeEntry} that were affected by other operations
	 *
	 * @param affectedTypes list of affected types
	 */
	private static void showUpdatedElements(final List<TypeEntry> affectedTypes) {
		if (affectedTypes.isEmpty()) {
			return;
		}
		final BlockTypeInstanceSearch search = new BlockTypeInstanceSearch(affectedTypes);
		final List<FBNetworkElement> elements = search.performSearch().stream()
				.filter(FBNetworkElement.class::isInstance).map(FBNetworkElement.class::cast).toList();

		Display.getDefault().syncExec(() -> {
			final InstanceUpdateDialog updateDialog = new InstanceUpdateDialog(null, Messages.InstanceUpdate, null,
					Messages.UpdatedInstances, MessageDialog.NONE, new String[] { Messages.Confirm }, 0, elements);
			updateDialog.open();

		});
	}

	/**
	 * List the filtered contents of the standard archive folder
	 *
	 * @return array of paths
	 */
	public Path[] listDirectoriesContainingArchives() {
		return listArchiveFolders(ARCHIVE_PATH);
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
		checkLibChanges();
		return new HashMap<>(libraries);
	}

	/**
	 * Check {@link Manifest} of {@link IProject} and ensure project is set up
	 * properly
	 *
	 * <p>
	 * Will download/import libraries as needed through background jobs
	 *
	 * @param project     selected project
	 * @param typeLibrary {@link TypeLibrary} to use
	 */
	public void checkManifestFile(final IProject project, final TypeLibrary typeLibrary) {
		if (uninitialised) {
			init(project);
		}
		final Manifest manifest = ManifestHelper.getOrCreateProjectManifest(project);
		if (manifest == null || !ManifestHelper.isProject(manifest) || manifest.getDependencies() == null) {
			return;
		}
		for (final Required req : manifest.getDependencies().getRequired()) {
			checkDependency(req, project, typeLibrary);
		}
		startLocalResolveJob(project, typeLibrary);

	}

	/**
	 * Check if library dependency is present in the {@link IProject} and
	 * download/import as needed if not
	 *
	 * @param req         library dependency
	 * @param project     selected project
	 * @param typeLibrary {@link TypeLibrary} to use
	 */
	private void checkDependency(final Required req, final IProject project, final TypeLibrary typeLibrary) {
		checkLibChanges();
		String preferred = null;

		// check already linked lib
		final IFolder libFolder = project.getFolder(TYPE_LIB_FOLDER_NAME).getFolder(req.getSymbolicName());
		if (libFolder.exists()) {
			final Manifest libManifest = ManifestHelper.getContainerManifest(libFolder);
			if (libManifest != null) {
				if (VersionComparator.contains(req.getVersion(),
						libManifest.getProduct().getVersionInfo().getVersion())) {
					return;
				}
			} else {
				final IPath path = libFolder.getLocation();
				final String segment = (path.segmentCount() >= 2) ? path.segment(path.segmentCount() - 2) : ""; //$NON-NLS-1$
				final int index = segment.lastIndexOf('-');
				if (index > 0) {
					preferred = segment.substring(index + 1);
					if (!VersionComparator.contains(req.getVersion(), preferred)) {
						preferred = null;
					}
				}
			}
		}

		if (preferred != null) {
			final Required prefReq = ManifestHelper.createRequired(req.getSymbolicName(), preferred);
			// check standard libs
			if (checkLibraries(prefReq, project, typeLibrary, stdlibraries)) {
				return;
			}

			// check local libs
			if (checkLibraries(prefReq, project, typeLibrary, libraries)) {
				return;
			}
		} else {
			// check standard libs
			if (checkLibraries(req, project, typeLibrary, stdlibraries)) {
				return;
			}

			// check local libs
			if (checkLibraries(req, project, typeLibrary, libraries)) {
				return;
			}
		}

		final Version pref = (preferred != null) ? new Version(preferred) : null;

		// download if checks were unsuccessful
		final WorkspaceJob job = new WorkspaceJob(
				"Download Library package: " + req.getSymbolicName() + " - " + req.getVersion()) { //$NON-NLS-1$//$NON-NLS-2$

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				libraryDownload(req.getSymbolicName(), VersionComparator.parseVersionRange(req.getVersion()), pref,
						project, true, false);
				return Status.OK_STATUS;
			}
		};
		job.setRule(project);
		job.setPriority(Job.LONG);
		job.schedule();
	}

	/**
	 * Import the library dependency into the {@link IProject} if it is contained in
	 * the given library map
	 *
	 * @param req         library dependency
	 * @param project     selected project
	 * @param typeLibrary {@link TypeLibrary} to use
	 * @param libs        library map
	 * @return {@code true} if the dependency was found in the library map, else
	 *         {@code false}
	 */
	private boolean checkLibraries(final Required req, final IProject project, final TypeLibrary typeLibrary,
			final Map<String, List<LibraryRecord>> libs) {
		if (libs.containsKey(req.getSymbolicName()) && libs.get(req.getSymbolicName()).stream()
				.anyMatch(l -> VersionComparator.parseVersionRange(req.getVersion()).includes(l.version()))) {
			startLocalLinkJob(req, project, libs, typeLibrary);
			return true;
		}
		return false;
	}

	/**
	 * Start background job that links library into {@link IProject}
	 *
	 * @param lib         library dependency
	 * @param project     selected project
	 * @param libs        map of available libraries
	 * @param typeLibrary {@link TypeLibrary} to use
	 */
	private void startLocalLinkJob(final Required lib, final IProject project,
			final Map<String, List<LibraryRecord>> libs, final TypeLibrary typeLibrary) {
		final WorkspaceJob job = new WorkspaceJob("Link library: " + lib.getSymbolicName() + " - " + lib.getVersion()) { //$NON-NLS-1$//$NON-NLS-2$

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				importLibrary(project, typeLibrary, libs.get(lib.getSymbolicName()).stream()
						.filter(l -> VersionComparator.parseVersionRange(lib.getVersion()).includes(l.version()))
						.sorted((o1, o2) -> o2.version().compareTo(o1.version())).findFirst().get().uri(), true, true);
				return Status.OK_STATUS;
			}
		};
		job.setRule(project);
		job.setPriority(Job.SHORT);
		job.schedule();
	}

	/**
	 * Start background job that resolves transitive library dependencies of
	 * {@link IProject}
	 *
	 * @param project     selected project
	 * @param typeLibrary {@link TypeLibrary} to use
	 */
	private void startLocalResolveJob(final IProject project, final TypeLibrary typeLibrary) {
		final WorkspaceJob job = new WorkspaceJob("Resolve dependencies: " + project.getName()) { //$NON-NLS-1$

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				resolveDependencies(project, typeLibrary);
				return Status.OK_STATUS;
			}
		};
		job.setRule(project);
		job.setPriority(Job.LONG);
		job.schedule();
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
	 * @param preferred    preferred version of library (ignored if {@code null})
	 * @param project      project to import the library into after extracting
	 *                     (irrelevant if {@code autoImport} is false)
	 * @param autoImport   if library should be automatically imported into project
	 * @param resolve      define if dependencies should get resolved on import
	 *                     (irrelevant if {@code autoImport} is false)
	 * @return {@link java.net.URI} of the extracted library folder
	 */
	private java.net.URI libraryDownload(final String symbolicName, final VersionRange versionRange,
			final Version preferred, final IProject project, final boolean autoImport, final boolean resolve) {
		final List<IArchiveDownloader> downloaders = TypeLibraryManager
				.listExtensions("org.eclipse.fordiac.ide.library.ArchiveDownloaderExtension", IArchiveDownloader.class); //$NON-NLS-1$
		Path archivePath;
		final VersionRange range = (versionRange == null || versionRange.isEmpty()) ? ALL_RANGE : versionRange;
		for (final var downloader : downloaders) {
			try {
				archivePath = downloader.downloadLibrary(symbolicName, range, preferred);
				if (archivePath != null) {
					return extractLibrary(archivePath, project, autoImport, resolve);
				}
			} catch (final IOException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}
		return null;
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
		final WorkspaceJob job = new WorkspaceJob("Update Library package: " + symbolicName + " = " + versionRange) { //$NON-NLS-1$//$NON-NLS-2$

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				libraryDownload(symbolicName, VersionComparator.parseVersionRange(versionRange), null, project, true,
						true);
				return Status.OK_STATUS;
			}
		};
		job.setRule(project);
		job.setPriority(Job.LONG);
		job.schedule();
	}

	/**
	 * Resolves transitive library dependencies of an {@link IProject}
	 *
	 * @param project     selected project
	 * @param typeLibrary {@link TypeLibrary} to use
	 */
	public void resolveDependencies(final IProject project, final TypeLibrary typeLibrary) {
		final Map<String, VersionRange> requirements = new HashMap<>();
		final Set<String> resolved = new HashSet<>();

		final Manifest projectManifest = ManifestHelper.getOrCreateProjectManifest(project);
		if (projectManifest.getDependencies() == null) {
			return;
		}

		final List<ErrorMarkerBuilder> markerList = new LinkedList<>();

		projectManifest.getDependencies().getRequired().forEach(
				req -> requirements.put(req.getSymbolicName(), VersionComparator.parseVersionRange(req.getVersion())));
		resolved.addAll(requirements.keySet());

		final IFolder typelib = project.getFolder(TYPE_LIB_FOLDER_NAME);
		try {
			for (final var member : typelib.members()) {
				if (member instanceof final IContainer container) {
					final Manifest libManifest = ManifestHelper.getContainerManifest(container);
					if (libManifest != null && resolved.contains(libManifest.getProduct().getSymbolicName())
							&& libManifest.getDependencies() != null) {
						libManifest.getDependencies().getRequired()
								.forEach(req -> handleRequired(requirements, projectManifest, markerList, req));
					}
				}
			}
		} catch (final CoreException e) {
			// empty
		}
		final List<String> queue = new LinkedList<>();
		requirements.keySet().stream().filter(k -> !resolved.contains(k)).forEach(queue::add);

		while (!queue.isEmpty()) {
			final String symb = queue.removeFirst();
			final VersionRange range = requirements.get(symb);
			LibraryRecord libRec = null;
			java.net.URI uri = null;
			if (stdlibraries.containsKey(symb)) {
				libRec = stdlibraries.get(symb).stream().filter(lr -> range.includes(lr.version()))
						.sorted((o1, o2) -> o2.version().compareTo(o1.version())).findFirst().orElse(null);
				if (libRec == null) {
					markerList.add(ErrorMarkerBuilder
							.createErrorMarkerBuilder(MessageFormat.format(Messages.ErrorMarkerStandardLibNotAvailable,
									symb, VersionComparator.formatVersionRange(range)))
							.setType(FordiacErrorMarker.LIBRARY_MARKER).setTarget(projectManifest.getDependencies()));
					continue;
				}
			}
			if (libraries.containsKey(symb)) {
				libRec = libraries.get(symb).stream().filter(lr -> range.includes(lr.version()))
						.sorted((o1, o2) -> o2.version().compareTo(o1.version())).findFirst().orElse(null);
			}
			if (libRec == null) {
				uri = libraryDownload(symb, range, null, project, false, false);
			} else {
				uri = libRec.uri();
			}
			if (uri != null) {
				importLibrary(project, typeLibrary, uri, false, false);
				resolved.add(symb);
				final Manifest libManifest = ManifestHelper
						.getContainerManifest(project.getFolder(TYPE_LIB_FOLDER_NAME).getFolder(symb));
				if (libManifest != null && libManifest.getDependencies() != null) {
					libManifest.getDependencies().getRequired().forEach(req -> {
						handleRequired(requirements, projectManifest, markerList, req);
						if (!resolved.contains(req.getSymbolicName())) {
							queue.add(req.getSymbolicName());
						}
					});
				}
			} else {
				markerList.add(ErrorMarkerBuilder
						.createErrorMarkerBuilder(MessageFormat.format(Messages.ErrorMarkerLibNotAvailable, symb,
								VersionComparator.formatVersionRange(range)))
						.setType(FordiacErrorMarker.LIBRARY_MARKER).setTarget(projectManifest.getDependencies()));
			}
		}
		FordiacMarkerHelper.updateMarkers(project.getFile(MANIFEST), FordiacErrorMarker.LIBRARY_MARKER, markerList,
				true);
	}

	/**
	 * Checks {@link Required} and merges it with already known dependencies
	 *
	 * @param requirements    already known dependencies
	 * @param projectManifest project {@link Manifest}
	 * @param markerList      list to add error markers to
	 * @param req             library dependency to check
	 */
	private static void handleRequired(final Map<String, VersionRange> requirements, final Manifest projectManifest,
			final List<ErrorMarkerBuilder> markerList, final Required req) {
		final VersionRange oldRange = requirements.get(req.getSymbolicName());
		final VersionRange newRange = VersionComparator.parseVersionRange(req.getVersion());
		requirements.merge(req.getSymbolicName(), newRange, VersionRange::intersection);
		if (oldRange != null && requirements.get(req.getSymbolicName()).isEmpty()) {
			markerList.add(ErrorMarkerBuilder
					.createErrorMarkerBuilder(MessageFormat.format(Messages.ErrorMarkerVersionRangeEmpty,
							req.getSymbolicName(), VersionComparator.formatVersionRange(oldRange),
							VersionComparator.formatVersionRange(newRange)))
					.setType(FordiacErrorMarker.LIBRARY_MARKER).setTarget(projectManifest.getDependencies()));
		}
	}

	/**
	 * Starts event broker for the creation event of {@link TypeLibrary}
	 *
	 * @param context available context
	 */
	public void startEventBroker(final BundleContext context) {
		eventBroker = EclipseContextFactory.getServiceContext(context).get(IEventBroker.class);
		eventBroker.subscribe(TypeLibraryTags.TYPE_LIBRARY_CREATION_TOPIC, null, handler, true);
	}

	/**
	 * Stops the event broker
	 */
	void stopEventBroker() {
		eventBroker.unsubscribe(handler);
	}

	private final EventHandler handler = event -> {
		final Object data = event.getProperty(IEventBroker.DATA);
		if (data instanceof final TypeLibrary typeLibrary) {
			checkManifestFile(typeLibrary.getProject(), typeLibrary);
		}
	};
}
