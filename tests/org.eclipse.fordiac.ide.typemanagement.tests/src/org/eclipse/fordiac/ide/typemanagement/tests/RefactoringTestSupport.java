/*******************************************************************************
 * Copyright (c) 2026 Dimitrios Kalligaridis
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dimitrios Kalligaridis - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.tests;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.typemanagement.refactoring.rename.RenameElementRefactoringProcessor;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CheckConditionsOperation;
import org.eclipse.ltk.core.refactoring.CreateChangeOperation;
import org.eclipse.ltk.core.refactoring.IValidationCheckResultQuery;
import org.eclipse.ltk.core.refactoring.PerformChangeOperation;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;
import org.eclipse.ltk.core.refactoring.resource.DeleteResourcesDescriptor;
import org.eclipse.ltk.core.refactoring.resource.MoveResourcesDescriptor;
import org.eclipse.ltk.core.refactoring.resource.RenameResourceDescriptor;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public final class RefactoringTestSupport {

	/**
	 * Copy a bundle-relative project template into a fresh, writable project in the
	 * test workspace.
	 *
	 * The fixture data shipped with the test bundle is read-only template data;
	 * refactorings rename and rewrite type files, so each test must operate on its
	 * own writable copy instead of mutating the committed fixture in place.
	 */
	public static IProject importProjectIntoWorkspace(final String projectName, final String bundleRelativePath)
			throws CoreException, IOException {
		final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		final IProject project = root.getProject(projectName);
		deleteProject(project);

		final java.nio.file.Path source = resolveBundleDirectory(bundleRelativePath);
		final java.nio.file.Path destination = root.getLocation().append(projectName).toFile().toPath();
		deleteRecursively(destination);
		copyRecursively(source, destination);

		final IPath descriptionPath = Path.fromOSString(destination.resolve(".project").toString()); //$NON-NLS-1$
		final IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(descriptionPath);
		// destination is the default location (workspaceRoot/projectName), so no
		// explicit location needs to be set on the description
		project.create(description, new NullProgressMonitor());
		project.open(new NullProgressMonitor());
		project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		return project;
	}

	/**
	 * Link the given standard libraries from the source tree so types they declare
	 * resolve at test runtime instead of being copied into the fixture.
	 */
	public static void linkStandardLibraries(final IProject project, final String... libraryNames)
			throws CoreException, IOException {
		ensureLibraryFolders(project);
		final java.nio.file.Path standardLibraries = TestRepositoryPaths.resolve().standardLibraries();
		for (final String libraryName : libraryNames) {
			LibraryManager.INSTANCE.importLibrary(project, standardLibraries.resolve(libraryName).toUri(), true, false);
		}
		// refresh() only reconciles added or deleted files; drop the cached
		// TypeLibrary so .sys entries re-parse with the linked libraries in scope.
		TypeLibraryManager.INSTANCE.removeProject(project);
		TypeLibraryManager.INSTANCE.getTypeLibrary(project).refresh();
	}

	private static void ensureLibraryFolders(final IProject project) throws CoreException {
		final NullProgressMonitor monitor = new NullProgressMonitor();
		final IFolder standardLibs = project.getFolder(TypeLibraryTags.STANDARD_LIB_FOLDER_NAME);
		if (!standardLibs.exists()) {
			standardLibs.create(IResource.VIRTUAL | IResource.FORCE, true, monitor);
		}
		final IFolder externalLibs = project.getFolder(TypeLibraryTags.EXTERNAL_LIB_FOLDER_NAME);
		if (!externalLibs.exists()) {
			externalLibs.create(IResource.VIRTUAL | IResource.FORCE, true, monitor);
		}
	}

	/** Remove a project from the workspace and drop its cached type library. */
	public static void deleteProject(final IProject project) throws CoreException {
		if (project.exists()) {
			TypeLibraryManager.INSTANCE.removeProject(project);
			project.delete(true, true, new NullProgressMonitor());
		}
	}

	public static Change performRename(final IFile file, final String newName) throws CoreException {
		final RenameResourceDescriptor descriptor = new RenameResourceDescriptor();
		descriptor.setResourcePath(file.getFullPath());
		descriptor.setNewName(newName);
		return performRefactoring(descriptor.createRefactoring(new RefactoringStatus()));
	}

	public static Change performFolderRename(final IFolder folder, final String newName) throws CoreException {
		final RenameResourceDescriptor descriptor = new RenameResourceDescriptor();
		descriptor.setResourcePath(folder.getFullPath());
		descriptor.setNewName(newName);
		return performRefactoring(descriptor.createRefactoring(new RefactoringStatus()));
	}

	public static Change performDelete(final IFile file) throws CoreException {
		final DeleteResourcesDescriptor descriptor = new DeleteResourcesDescriptor();
		descriptor.setResourcePaths(new IPath[] { file.getFullPath() });
		return performRefactoring(descriptor.createRefactoring(new RefactoringStatus()));
	}

	public static Change performMove(final IFile file, final IContainer destination) throws CoreException {
		final MoveResourcesDescriptor descriptor = new MoveResourcesDescriptor();
		descriptor.setResourcesToMove(new IResource[] { file });
		descriptor.setDestination(destination);
		return performRefactoring(descriptor.createRefactoring(new RefactoringStatus()));
	}

	/**
	 * Rename a model element identified by its URI, e.g. a struct member, through
	 * the same processor the Rename Element command uses, but without the wizard
	 * and without saving and building the project first.
	 */
	public static Change performElementRename(final URI elementURI, final String newName) throws CoreException {
		return performRefactoring(new RenameRefactoring(new RenameElementRefactoringProcessor(elementURI, newName)));
	}

	/**
	 * Run only the rename condition check and return its status. performRename
	 * cannot be used for the negative cases because CreateChangeOperation drops a
	 * fatal status instead of surfacing it.
	 */
	public static RefactoringStatus checkRenameConditions(final IFile file, final String newName) throws CoreException {
		final RenameResourceDescriptor descriptor = new RenameResourceDescriptor();
		descriptor.setResourcePath(file.getFullPath());
		descriptor.setNewName(newName);
		final CheckConditionsOperation check = new CheckConditionsOperation(
				descriptor.createRefactoring(new RefactoringStatus()), CheckConditionsOperation.ALL_CONDITIONS);
		check.run(new NullProgressMonitor());
		return check.getStatus();
	}

	private static Change performRefactoring(final Refactoring refactoring) throws CoreException {
		final CreateChangeOperation create = new CreateChangeOperation(
				new CheckConditionsOperation(refactoring, CheckConditionsOperation.ALL_CONDITIONS),
				RefactoringStatus.FATAL);
		final PerformChangeOperation perform = new PerformChangeOperation(create);
		// Register with the shared undo manager so undoLastRefactoring and
		// redoLastRefactoring can drive this change.
		perform.setUndoManager(RefactoringCore.getUndoManager(), refactoring.getName());
		ResourcesPlugin.getWorkspace().run(perform, new NullProgressMonitor());
		return perform.getUndoChange();
	}

	public static void undoLastRefactoring() throws CoreException {
		RefactoringCore.getUndoManager().performUndo(PROCEED_QUERY, new NullProgressMonitor());
	}

	public static void redoLastRefactoring() throws CoreException {
		RefactoringCore.getUndoManager().performRedo(PROCEED_QUERY, new NullProgressMonitor());
	}

	/**
	 * Clear the shared undo history so undo and redo do not leak between tests.
	 * The undo manager is a process-wide singleton, so this assumes the suite runs
	 * sequentially.
	 */
	public static void flushUndoHistory() {
		RefactoringCore.getUndoManager().flush();
	}

	/** Walk from the application network through the named subapps to an FB instance. */
	public static FBNetworkElement findInstance(final AutomationSystem system, final String applicationName,
			final String... namePath) {
		FBNetwork network = system.getApplicationNamed(applicationName).getFBNetwork();
		for (int i = 0; i < namePath.length - 1; i++) {
			final String subAppName = namePath[i];
			final UntypedSubApp subApp = (UntypedSubApp) network.getNetworkElements().stream()
					.filter(element -> subAppName.equals(element.getName())).findFirst().orElseThrow();
			network = subApp.getSubAppNetwork();
		}
		final String instanceName = namePath[namePath.length - 1];
		return network.getNetworkElements().stream().filter(element -> instanceName.equals(element.getName()))
				.findFirst().orElseThrow();
	}

	// The undo manager is the global RefactoringCore singleton; a permissive query
	// keeps undo and redo non-interactive during headless tests.
	private static final IValidationCheckResultQuery PROCEED_QUERY = new IValidationCheckResultQuery() {
		@Override
		public boolean proceed(final RefactoringStatus status) {
			return true;
		}

		@Override
		public void stopped(final RefactoringStatus status) {
			// nothing to do in a headless test
		}
	};

	private static java.nio.file.Path resolveBundleDirectory(final String bundleRelativePath) throws IOException {
		final Bundle bundle = FrameworkUtil.getBundle(RefactoringTestSupport.class);
		final var url = FileLocator.toFileURL(FileLocator.find(bundle, new Path(bundleRelativePath)));
		return Paths.get(url.getPath());
	}

	private static void copyRecursively(final java.nio.file.Path source, final java.nio.file.Path destination)
			throws IOException {
		try (var paths = Files.walk(source)) {
			for (final var path : (Iterable<java.nio.file.Path>) paths::iterator) {
				final java.nio.file.Path target = destination.resolve(source.relativize(path).toString());
				if (Files.isDirectory(path)) {
					Files.createDirectories(target);
				} else {
					Files.createDirectories(target.getParent());
					Files.copy(path, target, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}
	}

	private static void deleteRecursively(final java.nio.file.Path directory) throws IOException {
		if (!Files.exists(directory)) {
			return;
		}
		try (var paths = Files.walk(directory)) {
			paths.sorted(Comparator.reverseOrder()).forEach(path -> {
				try {
					Files.delete(path);
				} catch (final IOException e) {
					throw new UncheckedIOException(e);
				}
			});
		}
	}

	private RefactoringTestSupport() {
		throw new UnsupportedOperationException();
	}
}
