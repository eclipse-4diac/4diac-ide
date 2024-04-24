/*******************************************************************************
 * Copyright (c) 2023 - 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin, Fabio Gandolfi -
 *   	initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.librarylinker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.fordiac.ide.gitlab.management.GitLabDownloadManager;
import org.eclipse.fordiac.ide.gitlab.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.eclipse.fordiac.ide.model.typelibrary.ILibraryLinker;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.util.FBUpdater;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class LibraryLinker implements ILibraryLinker {
	private static final String WORKSPACE_ROOT = ResourcesPlugin.getWorkspace().getRoot().getRawLocation()
			.toPortableString();
	private static final String ZIP_SUFFIX = ".zip"; //$NON-NLS-1$
	private static final File[] EMPTY_ARRAY = new File[0];
	private static final java.net.URI WORKSPACE_URI = java.net.URI.create("WORKSPACE_LOC"); //$NON-NLS-1$
	private static final VersionComparator versionComparator = new VersionComparator();

	private IProject selectedProject;
	private Set<TypeEntry> cachedTypes;
	private TypeLibrary typeLibrary;

	@Override
	public void extractLibrary(final File file, final IProject project) throws IOException {
		final byte[] buffer = new byte[1024];
		if (project != null) {
			setSelectedProject(project);
		}
		final java.net.URI baseUri = URIUtil.append(WORKSPACE_URI, LibraryUtil.EXTRACTED_LIB_DIRECTORY);
		final IPathVariableManager varMan = selectedProject.getPathVariableManager();
		final java.net.URI resolvedUri = varMan.resolveURI(baseUri);
		final File destinationDir = new File(resolvedUri);
		try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
			ZipEntry entry = zipInputStream.getNextEntry();
			while (entry != null) {
				final File newFile = newFile(destinationDir, entry);
				if (entry.isDirectory()) {
					if (!newFile.isDirectory() && !newFile.mkdirs()) {
						throw new IOException("Failed to create directory " + newFile); //$NON-NLS-1$
					}
				} else {
					final File parent = newFile.getParentFile();
					if (!parent.isDirectory() && !parent.mkdirs()) {
						throw new IOException("Failed to create directory " + parent); //$NON-NLS-1$
					}
					try (FileOutputStream fileOutputStream = new FileOutputStream(newFile)) {
						int len;
						while ((len = zipInputStream.read(buffer)) > 0) {
							fileOutputStream.write(buffer, 0, len);
						}
					}
				}
				entry = zipInputStream.getNextEntry();
			}
		}

		// Parent's name because we want package-version name when importing
		importLibrary(URIUtil.append(baseUri, file.getParentFile().getName()));

	}

	@Override
	public File newFile(final File destinationDir, final ZipEntry zipEntry) throws IOException {
		final File destFile = new File(destinationDir, zipEntry.getName());

		final String destDirPath = destinationDir.getCanonicalPath();
		final String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName()); //$NON-NLS-1$
		}
		return destFile;
	}

	@Override
	public void setSelectedProject(final IProject project) {
		selectedProject = project;
		if (project != null) {
			typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(selectedProject);
		}
	}

	@Override
	public void setSelectedProjectWithTypeLib(final IProject project, final TypeLibrary typeLib) {
		selectedProject = project;
		typeLibrary = typeLib;
	}

	@Override
	public File[] listLibDirectories(final String directory) {
		final File libDir = new File(Paths.get(WORKSPACE_ROOT, directory).toString());
		if (libDir.exists()) {
			return libDir.listFiles();
		}
		return EMPTY_ARRAY;
	}

	@Override
	public void importLibrary(final java.net.URI uri) {
		SystemManager.INSTANCE.removeFordiacChangeListener();
		final IPathVariableManager varMan = selectedProject.getPathVariableManager();
		final java.net.URI resolvedUri = varMan.resolveURI(uri);
		final File directory = new File(resolvedUri);
		final Manifest libManifest = ManifestHelper.getFolderManifest(directory);
		final Manifest projManifest = ManifestHelper.getOrCreateProjectManifest(selectedProject);
		if (libManifest == null) {
			return;
		}
		final IFolder libDirectory = selectedProject.getFolder(LibraryUtil.TYPE_LIB_FOLDER_NAME)
				.getFolder(libManifest.getProduct().getSymbolicName());
		final java.nio.file.Path libPath = Paths.get(resolvedUri);
		if (directory.exists() && Files.exists(libPath.resolve(LibraryUtil.LIB_TYPELIB_FOLDER_NAME))
				&& Files.exists(libPath.resolve(LibraryUtil.MANIFEST))) {
			final boolean isNewVersion = removeOldLibVersion(libDirectory);
			final java.net.URI libUri = URIUtil.append(uri, LibraryUtil.LIB_TYPELIB_FOLDER_NAME);
			final java.net.URI manUri = URIUtil.append(uri, LibraryUtil.MANIFEST);
			try {
				libDirectory.createLink(libUri, IResource.NONE, null);
				final IFile man = libDirectory.getFile(LibraryUtil.MANIFEST);
				man.createLink(manUri, IResource.HIDDEN, null);
				ManifestHelper.updateDependency(projManifest,
						ManifestHelper.createRequired(libManifest.getProduct().getSymbolicName(),
								libManifest.getProduct().getVersionInfo().getVersion()));
				projManifest.eResource().save(null);
				createTypeEntriesManually(libDirectory);
				if (isNewVersion) {
					// We can only update if there was a version before
					updateFBInstancesWithNewTypeVersion();
				}
			} catch (final CoreException | IOException e) {
				Display.getDefault().syncExec(() -> MessageDialog.openWarning(null, Messages.Warning,
						MessageFormat.format(Messages.ImportFailedOnLinkCreation, e.getMessage())));
			}
		}
		SystemManager.INSTANCE.addFordiacChangeListener();
	}

	@Override
	public void importLibraries(final Collection<java.net.URI> uris) {
		for (final java.net.URI uri : uris) {
			importLibrary(uri);
		}
	}

	@Override
	public boolean removeOldLibVersion(final IFolder libDirectory) {
		boolean isNewVersion = false;
		if (libDirectory.exists()) {
			isNewVersion = true;
			try {
				cachedTypes = cacheOldTypes(libDirectory.getName());
				// Remove the link but keep the resource on disk
				libDirectory.delete(true, null);
				// Remove the entries manually
				cachedTypes.forEach(typeEntry -> TypeLibraryManager.INSTANCE.getTypeLibrary(selectedProject)
						.removeTypeEntry(typeEntry));
			} catch (final CoreException e) {
				Display.getDefault().syncExec(() -> MessageDialog.openWarning(null, Messages.Warning,
						Messages.OldTypeLibVersionCouldNotBeDeleted));
			}
		}
		return isNewVersion;
	}

	@Override
	public void createTypeEntriesManually(final IContainer container) {
		try {
			final IResource[] members = container.members();
			for (final IResource resource : members) {
				if (resource instanceof final IFolder folder) {
					createTypeEntriesManually(folder);
				}
				if (resource instanceof final IFile file) {
					typeLibrary.createTypeEntry(file);
				}
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}

	}

	@Override
	public void updateFBInstancesWithNewTypeVersion() {
		Display.getDefault().syncExec(() -> FBUpdater.updateAllInstances(selectedProject, cachedTypes, typeLibrary));
		final InstanceUpdateDialog updateDialog = new InstanceUpdateDialog(null, Messages.InstanceUpdate, null,
				Messages.UpdatedInstances, MessageDialog.NONE, new String[] { Messages.Confirm }, 0,
				FBUpdater.getUpdatedElements());
		Display.getDefault().syncExec(updateDialog::open);
	}

	@Override
	public Set<TypeEntry> cacheOldTypes(final String oldVersion) {
		try (final Stream<java.nio.file.Path> stream = Files
				.walk(Paths.get(WORKSPACE_ROOT, LibraryUtil.EXTRACTED_LIB_DIRECTORY, oldVersion))) {

			final Predicate<java.nio.file.Path> fbt = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.FB_TYPE_FILE_ENDING_WITH_DOT);
			final Predicate<java.nio.file.Path> adp = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.ADAPTER_TYPE_FILE_ENDING_WITH_DOT);
			final Predicate<java.nio.file.Path> atp = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.ATTRIBUTE_TYPE_FILE_ENDING_WITH_DOT);
			final Predicate<java.nio.file.Path> dev = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.DEVICE_TYPE_FILE_ENDING_WITH_DOT);
			final Predicate<java.nio.file.Path> dtp = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.DATA_TYPE_FILE_ENDING_WITH_DOT);
			final Predicate<java.nio.file.Path> res = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.RESOURCE_TYPE_FILE_ENDING_WITH_DOT);
			final Predicate<java.nio.file.Path> seg = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.SEGMENT_TYPE_FILE_ENDING_WITH_DOT);
			final Predicate<java.nio.file.Path> sub = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.SUBAPP_TYPE_FILE_ENDING_WITH_DOT);
			final Predicate<java.nio.file.Path> sys = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.SYSTEM_TYPE_FILE_ENDING_WITH_DOT);
			final Predicate<java.nio.file.Path> fct = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.FC_TYPE_FILE_ENDING_WITH_DOT);
			final Predicate<java.nio.file.Path> gcf = i -> i.getFileName().toString().toUpperCase()
					.endsWith(TypeLibraryTags.GLOBAL_CONST_FILE_ENDING_WITH_DOT);

			return stream.filter(Files::isRegularFile)
					.filter(fbt.or(adp).or(atp).or(dev).or(dtp).or(res).or(seg).or(sub).or(sys).or(fct).or(gcf))
					.map(path -> TypeLibraryManager.INSTANCE.getTypeFromLinkedFile(selectedProject,
							path.getFileName().toString()))
					.filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return Collections.emptySet();
	}

	@Override
	public List<String> findLinkedLibs() {
		if (selectedProject == null) {
			return Collections.emptyList();
		}
		final LinkedList<IResource> resources = new LinkedList<>();
		try {
			selectedProject.accept(resource -> {
				if (resource.isLinked() && !resource.isVirtual()) {
					resources.add(resource);
				}
				return true;
			});
		} catch (final CoreException e) {
			e.printStackTrace();
		}
		return resources.stream().map(IResource::getName).toList();
	}

	@Override
	public File[] listDirectoriesContainingArchives() {
		final File[] directory = listLibDirectories(LibraryUtil.PACKAGE_DOWNLOAD_DIRECTORY);
		return Stream.of(directory).filter(file -> file.isDirectory() && !Stream.of(file.listFiles())
				.filter(child -> child.getName().endsWith(ZIP_SUFFIX)).toList().isEmpty()).toArray(File[]::new);
	}

	@Override
	public File[] listExtractedFiles() {
		return listLibDirectories(LibraryUtil.EXTRACTED_LIB_DIRECTORY);
	}

	@Override
	public File[] listStandardLibraries() {
		final IPathVariableManager varMan = selectedProject.getPathVariableManager();
		final java.net.URI typeLibURIResolved = varMan.resolveURI(LibraryUtil.STANDARD_LIBRARY_URI);
		final File libDir = new File(typeLibURIResolved);
		if (libDir.exists()) {
			return libDir.listFiles();
		}
		return EMPTY_ARRAY;
	}

	@Override
	public void checkManifestFile(final IProject project, final TypeLibrary typeLibrary) {
		try {
			final Manifest manifest = ManifestHelper.getContainerManifest(project);
			if (manifest != null) {
				Map<String, List<String>> projectLibs;

				final IResource typeLib = project.findMember("Type Library"); //$NON-NLS-1$
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
		setSelectedProjectWithTypeLib(project, typeLibrary);
		WorkspaceJob job;
		// check already linked lib
		if (projectLibs.containsKey(lib.getSymbolicName()) && (projectLibs.get(lib.getSymbolicName()).stream()
				.filter(p -> versionComparator.contains(lib.getVersion(), p)).count() > 0)) {
			return;
		}
		// check standard libs
		List<File> libDir = Arrays.asList(listStandardLibraries());
		java.net.URI baseUri = LibraryUtil.STANDARD_LIBRARY_URI;
		Map<String, List<LibInfo>> localLibs = parseLocalLibraryNameAndVersion(libDir);

		if (localLibs.containsKey(lib.getSymbolicName()) && localLibs.get(lib.getSymbolicName()).stream()
				.anyMatch(l -> versionComparator.contains(lib.getVersion(), l.version()))) {

			startLocalLinkJob(lib, project, baseUri, localLibs);
			return;
		}
		// check local libs
		libDir = Arrays.asList(listExtractedFiles());
		baseUri = LibraryUtil.WORKSPACE_LIBRARY_URI;
		localLibs = parseLocalLibraryNameAndVersion(libDir);

		if (localLibs.containsKey(lib.getSymbolicName()) && localLibs.get(lib.getSymbolicName()).stream()
				.anyMatch(l -> versionComparator.contains(lib.getVersion(), l.version()))) {

			startLocalLinkJob(lib, project, baseUri, localLibs);
			return;
		}
		// download if checks were unsuccessful
		job = new WorkspaceJob("Download Gitlab package: " + lib.getSymbolicName() + " - " + lib.getVersion()) { //$NON-NLS-1$//$NON-NLS-2$

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				gitlabLibraryImport(lib.getSymbolicName(), lib.getVersion());
				return Status.OK_STATUS;
			}
		};
		job.setRule(project);
		job.setPriority(Job.LONG);
		job.schedule();
	}

	private void startLocalLinkJob(final Required lib, final IProject project, final java.net.URI baseUri,
			final Map<String, List<LibInfo>> localLibs) {
		WorkspaceJob job;
		job = new WorkspaceJob("Link library: " + lib.getSymbolicName() + " - " + lib.getVersion()) { //$NON-NLS-1$//$NON-NLS-2$

			@Override
			public IStatus runInWorkspace(final IProgressMonitor monitor) throws CoreException {
				importLibrary(URIUtil.append(baseUri,
						localLibs.get(lib.getSymbolicName()).stream()
								.filter(l -> versionComparator.contains(lib.getVersion(), l.version()))
								.sorted((o1, o2) -> -versionComparator.compare(o1.version(), o2.version())).findFirst()
								.get().folderName()));
				return Status.OK_STATUS;
			}
		};
		job.setRule(project);
		job.setPriority(Job.SHORT);
		job.schedule();
	}

	private void gitlabLibraryImport(final String libSymbolicName, final String libVersion) {
		final String url = PreferenceConstants.getURL();
		final String token = PreferenceConstants.getToken();
		if (url != null && !url.isBlank() && token != null && !token.isBlank()) {
			final GitLabDownloadManager downloadManager = new GitLabDownloadManager(url, token);
			downloadManager.fetchProjectsAndPackages();
			if (downloadManager.getPackagesAndLeaves().containsKey(libSymbolicName)) {
				downloadManager.getPackagesAndLeaves().get(libSymbolicName).stream()
						.filter(l -> versionComparator.contains(libVersion, l.getVersion())).sorted(Comparator
								.comparing(LeafNode::getVersion, (o1, o2) -> -versionComparator.compare(o1, o2)))
						.findFirst().ifPresent(l -> {
							try {
								final File file = downloadManager.packageDownloader(l.getProject(), l.getPackage());
								if (file != null) {
									extractLibrary(file, null);
								}
							} catch (final IOException e) {
								FordiacLogHelper.logError(e.getMessage(), e);
							}
						});
			}
		}
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

	private static Map<String, List<LibInfo>> parseLocalLibraryNameAndVersion(final List<File> libs) {
		final Map<String, List<LibInfo>> nameVersionMap = new HashMap<>();
		for (final File lib : libs) {
			final Manifest manifest = ManifestHelper.getFolderManifest(lib);
			if (manifest == null || !ManifestHelper.isLibrary(manifest)) {
				continue;
			}

			final String name = manifest.getProduct().getSymbolicName();
			final String version = manifest.getProduct().getVersionInfo().getVersion();

			if (nameVersionMap.containsKey(name)) {
				nameVersionMap.get(name).add(new LibInfo(name, version, lib.getName()));
			} else {
				nameVersionMap.put(name, new ArrayList<>(Arrays.asList(new LibInfo(name, version, lib.getName()))));
			}
		}

		return nameVersionMap;
	}

	private record LibInfo(String symbolicName, String version, String folderName) {

	}
}
