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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
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

	private WatchService watchService;
	private final HashMap<String, List<LibraryRecord>> stdlibraries = new HashMap<>();
	private final HashMap<String, List<LibraryRecord>> libraries = new HashMap<>();

	private IEventBroker eventBroker;
	private boolean uninitialised = true;

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

	private static void initLibraryMap(final Map<String, List<LibraryRecord>> map, final Path path,
			final java.net.URI baseUri) {
		map.clear();
		try (var stream = Files.newDirectoryStream(path, (Filter<? super Path>) Files::isDirectory)) {
			for (final Path folder : stream) {
				addLibrary(map, folder, baseUri);
			}
		} catch (final IOException e) {
			// empty
		}
	}

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

	public void extractLibrary(final Path file, final IProject project, final boolean autoimport) throws IOException {
		if (file == null || Files.notExists(file)) {
			return;
		}
		final byte[] buffer = new byte[1024];
		String folderName;
		try (InputStream inputStream = Files.newInputStream(file);
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

		if (autoimport || project != null) {
			// Parent's name because we want package-version name when importing
			importLibrary(project, null, URIUtil.append(WORKSPACE_LIBRARY_URI, folderName));
		}
	}

	private static Path newPath(final Path destinationDir, final ZipEntry zipEntry) throws IOException {
		final Path destPath = destinationDir.resolve(zipEntry.getName());

		if (!destPath.startsWith(destinationDir)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName()); //$NON-NLS-1$
		}
		return destPath;
	}

	// TODO: dependency resolution
	public void importLibrary(final IProject project, final TypeLibrary typeLibrary, final java.net.URI uri) {
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
			ManifestHelper.updateDependency(projManifest,
					ManifestHelper.createRequired(libManifest.getProduct().getSymbolicName(),
							libManifest.getProduct().getVersionInfo().getVersion()));
			projManifest.eResource().save(null);
			createTypeEntriesManually(libDirectory, cachedTypes, typeLib);
			if (!cachedTypes.isEmpty()) {
				final List<TypeEntry> affectedTypes = new ArrayList<>(cachedTypes.values());
				// clean up if there was a version before
				cleanupOldLibraryVersion(cachedTypes, typeLib);
				// show affected elements
				showUpdatedElements(affectedTypes);
			}
		} catch (final CoreException | IOException e) {
			Display.getDefault().syncExec(() -> MessageDialog.openWarning(null, Messages.Warning,
					MessageFormat.format(Messages.ImportFailedOnLinkCreation, e.getMessage())));
		}

		SystemManager.INSTANCE.addFordiacChangeListener();
	}

	public void importLibraries(final IProject project, final Collection<java.net.URI> uris) {
		for (final java.net.URI uri : uris) {
			importLibrary(project, null, uri);
		}
	}

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

	private static void cleanupOldLibraryVersion(final Map<String, TypeEntry> cachedTypes,
			final TypeLibrary typeLibrary) {
		// remove obsolete types
		cachedTypes.values().forEach(typeLibrary::removeTypeEntry);
	}

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

	public Path[] listDirectoriesContainingArchives() {
		return listArchiveFolders(ARCHIVE_PATH);
	}

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

	public Map<String, List<LibraryRecord>> getStandardLibraries() {
		checkLibChanges();
		return new HashMap<>(stdlibraries);
	}

	public Map<String, List<LibraryRecord>> getExtractedLibraries() {
		return new HashMap<>(libraries);
	}

	public void checkManifestFile(final IProject project, final TypeLibrary typeLibrary) {
		if (uninitialised) {
			init(project);
		}
		try {
			final Manifest manifest = ManifestHelper.getContainerManifest(project);
			if (manifest != null) {
				Map<String, List<String>> projectLibs;

				final IResource typeLib = project.findMember(TYPE_LIB_FOLDER_NAME);
				if (typeLib != null && typeLib instanceof final IFolder typeLibFolder) {
					projectLibs = parseLibraryNameAndVersion(Arrays.asList(typeLibFolder.members()).stream()
							.filter(fol -> fol.isLinked() && fol instanceof IFolder).map(IFolder.class::cast).toList());
				} else {
					projectLibs = new HashMap<>();
				}

				if (ManifestHelper.isProject(manifest) && manifest.getDependencies() != null) {
					for (final Required req : manifest.getDependencies().getRequired()) {
						checkLibrary(req, projectLibs, project, typeLibrary);
					}
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(Messages.TypeLibrary_ProjectLoadingProblem, e);
		}
	}

	private void checkLibrary(final Required lib, final Map<String, List<String>> projectLibs, final IProject project,
			final TypeLibrary typeLibrary) {
		checkLibChanges();
		final WorkspaceJob job;
		// check already linked lib
		if (projectLibs.containsKey(lib.getSymbolicName()) && (projectLibs.get(lib.getSymbolicName()).stream()
				.filter(p -> versionComparator.contains(lib.getVersion(), p)).count() > 0)) {
			return;
		}

		// check standard libs
		if (stdlibraries.containsKey(lib.getSymbolicName()) && stdlibraries.get(lib.getSymbolicName()).stream()
				.anyMatch(l -> versionComparator.contains(lib.getVersion(), l.version()))) {

			startLocalLinkJob(lib, project, stdlibraries, typeLibrary);
			return;
		}

		// check local libs
		if (libraries.containsKey(lib.getSymbolicName()) && libraries.get(lib.getSymbolicName()).stream()
				.anyMatch(l -> versionComparator.contains(lib.getVersion(), l.version()))) {

			startLocalLinkJob(lib, project, libraries, typeLibrary);
			return;
		}

		// download if checks were unsuccessful
		job = new WorkspaceJob("Download Library package: " + lib.getSymbolicName() + " - " + lib.getVersion()) { //$NON-NLS-1$//$NON-NLS-2$

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				libraryDownload(lib.getSymbolicName(), lib.getVersion(), null, project, true);
				return Status.OK_STATUS;
			}
		};
		job.setRule(project);
		job.setPriority(Job.LONG);
		job.schedule();
	}

	private void startLocalLinkJob(final Required lib, final IProject project,
			final Map<String, List<LibraryRecord>> libs, final TypeLibrary typeLibrary) {
		WorkspaceJob job;
		job = new WorkspaceJob("Link library: " + lib.getSymbolicName() + " - " + lib.getVersion()) { //$NON-NLS-1$//$NON-NLS-2$

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				importLibrary(project, typeLibrary,
						libs.get(lib.getSymbolicName()).stream()
								.filter(l -> versionComparator.contains(lib.getVersion(), l.version()))
								.sorted((o1, o2) -> -versionComparator.compare(o1.version(), o2.version())).findFirst()
								.get().uri());
				return Status.OK_STATUS;
			}
		};
		job.setRule(project);
		job.setPriority(Job.SHORT);
		job.schedule();
	}

	private static Map<String, List<String>> parseLibraryNameAndVersion(final List<IFolder> libs) {
		final Map<String, List<String>> nameVersionMap = new HashMap<>();
		for (final IFolder lib : libs) {
			final Manifest manifest = ManifestHelper.getContainerManifest(lib);
			if (manifest == null || !ManifestHelper.isLibrary(manifest)) {
				continue;
			}

			final String name = manifest.getProduct().getSymbolicName();
			final String version = manifest.getProduct().getVersionInfo().getVersion();

			if (nameVersionMap.containsKey(name)) {
				nameVersionMap.get(name).add(version);
			} else {
				nameVersionMap.put(name, new ArrayList<>(Arrays.asList(version)));
			}
		}

		return nameVersionMap;
	}

	private void libraryDownload(final String symbolicName, final String version, final String preferred,
			final IProject project, final boolean autoimport) {
		final List<IArchiveDownloader> downloaders = TypeLibraryManager
				.listExtensions("org.eclipse.fordiac.ide.library.ArchiveDownloaderExtension", IArchiveDownloader.class); //$NON-NLS-1$
		Path archivePath;
		for (final var downloader : downloaders) {
			try {
				archivePath = downloader.downloadLibrary(symbolicName, version, preferred);
				if (archivePath != null) {
					extractLibrary(archivePath, project, autoimport);
					break;
				}
			} catch (final IOException e) {
				FordiacLogHelper.logError(e.getMessage(), e);
			}
		}

	}

	public void updateLibrary(final IProject project, final String symbolicName, final String version) {
		final WorkspaceJob job = new WorkspaceJob("Update Library package: " + symbolicName + " = " + version) { //$NON-NLS-1$//$NON-NLS-2$

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				libraryDownload(symbolicName, version, null, project, true);
				return Status.OK_STATUS;
			}
		};
		job.setRule(project);
		job.setPriority(Job.LONG);
		job.schedule();
	}

	void startEventBroker(final BundleContext context) {
		eventBroker = EclipseContextFactory.getServiceContext(context).get(IEventBroker.class);
		eventBroker.subscribe(TypeLibraryTags.TYPE_LIBRARY_CREATION_TOPIC, null, handler, true);
	}

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
