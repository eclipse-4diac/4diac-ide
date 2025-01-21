/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.buildpath.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.fordiac.ide.model.buildpath.Attribute;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathAttributes;
import org.eclipse.fordiac.ide.model.buildpath.BuildpathFactory;
import org.eclipse.fordiac.ide.model.buildpath.DocumentRoot;
import org.eclipse.fordiac.ide.model.buildpath.Pattern;
import org.eclipse.fordiac.ide.model.buildpath.SourceFolder;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class BuildpathUtil {

	/** The build path file name */
	public static final String BUILDPATH_FILE_NAME = ".buildpath"; //$NON-NLS-1$

	private static final List<String> DEFAULT_SOURCE_FOLDERS = List.of("Type Library"); //$NON-NLS-1$
	private static final List<String> EXTERNAL_SOURCE_FOLDERS = List.of("Standard Libraries", "External Libraries"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Create a default build path configuration for project
	 *
	 * @param project The project
	 * @return The default build path configuration
	 */
	public static Buildpath createDefaultBuildpath(final IProject project) {
		Objects.requireNonNull(project);
		final Buildpath buildpath = BuildpathFactory.eINSTANCE.createBuildpath();
		final SourceFolder projectFolder = createSourceFolder(""); //$NON-NLS-1$
		buildpath.getSourceFolders().add(projectFolder);
		DEFAULT_SOURCE_FOLDERS.stream().map(BuildpathUtil::createSourceFolder)
				.forEachOrdered(buildpath.getSourceFolders()::add);
		EXTERNAL_SOURCE_FOLDERS.stream().map(BuildpathUtil::createExternalSourceFolder)
				.forEachOrdered(buildpath.getSourceFolders()::add);
		Stream.of(DEFAULT_SOURCE_FOLDERS, EXTERNAL_SOURCE_FOLDERS).flatMap(List::stream)
				.map(BuildpathUtil::createPattern).forEachOrdered(projectFolder.getExcludes()::add);
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.createResource(getBuildpathURI(project)).getContents().add(buildpath);
		return buildpath;
	}

	/**
	 * Load build path configuration for project
	 *
	 * @param project The project
	 * @return The build path configuration, either loaded from disk or using
	 *         default values
	 */
	public static Buildpath loadBuildpath(final IProject project) {
		Objects.requireNonNull(project);
		if (project.isAccessible() && getBuildpathFile(project).exists()) {
			try {
				final ResourceSet resourceSet = new ResourceSetImpl();
				final Resource resource = resourceSet.getResource(getBuildpathURI(project), true);
				if (!resource.getContents().isEmpty()
						&& resource.getContents().get(0) instanceof final DocumentRoot documentRoot) {
					return documentRoot.getBuildpath();
				}
			} catch (final Exception e) {
				FordiacLogHelper.logWarning(
						"Exception loading buildpath file " + getBuildpathFile(project).getFullPath().toString(), e); //$NON-NLS-1$
			}
		}
		return createDefaultBuildpath(project);
	}

	/**
	 * Save build path configuration
	 *
	 * @param buildpath The build path configuration
	 * @throws IOException if an error occurrs during save
	 */
	public static void saveBuildpath(final Buildpath buildpath) throws IOException {
		Objects.requireNonNull(buildpath);
		final Resource resource = buildpath.eResource();
		if (resource == null) {
			throw new IOException("The buildpath argument must be contained in a resource"); //$NON-NLS-1$
		}
		resource.save(Collections.emptyMap());
	}

	/**
	 * Test if source folders are nested
	 *
	 * @param sourceFolder The source folder
	 * @param other        The other source folder
	 * @return {@code true} if {@code sourceFolder} is nested in {@code other},
	 *         {@code false} otherwise
	 */
	public static boolean isNestedIn(final SourceFolder sourceFolder, final SourceFolder other) {
		Objects.requireNonNull(sourceFolder);
		Objects.requireNonNull(other);
		final IPath path = getRelativePath(sourceFolder);
		final IPath otherPath = getRelativePath(other);
		return otherPath.isPrefixOf(path);
	}

	/**
	 * Visit the set of matching resources for a build path configuration in a
	 * project
	 *
	 * @param buildpath The build path configuration
	 * @param project   The project
	 * @param visitor   The resource visitor
	 * @throws CoreException if there was a problem visiting the resources
	 * @apiNote May visit the same file multiple times if matching in multiple
	 *          source folders
	 */
	public static void acceptMatches(final Buildpath buildpath, final IProject project, final IResourceVisitor visitor)
			throws CoreException {
		Objects.requireNonNull(buildpath);
		Objects.requireNonNull(project);
		Objects.requireNonNull(visitor);
		for (final SourceFolder sourceFolder : buildpath.getSourceFolders()) {
			acceptMatches(sourceFolder, project, visitor);
		}
	}

	/**
	 * Visit the set of matching resources for a source folder in a project
	 *
	 * @param sourceFolder The source folder
	 * @param project      The project
	 * @param visitor      The resource visitor
	 * @throws CoreException if there was a problem visiting the resources
	 */
	public static void acceptMatches(final SourceFolder sourceFolder, final IProject project,
			final IResourceVisitor visitor) throws CoreException {
		Objects.requireNonNull(sourceFolder);
		Objects.requireNonNull(project);
		Objects.requireNonNull(visitor);
		final IContainer container = findContainer(sourceFolder, project);
		if (container == null) {
			return;
		}
		final List<PathMatcher> includes = getPathMatchers(sourceFolder.getIncludes());
		final List<PathMatcher> excludes = getPathMatchers(sourceFolder.getExcludes());
		container.accept(resource -> {
			final Path path = Paths.get(resource.getFullPath().makeRelativeTo(container.getFullPath()).toString());
			if ((includes.isEmpty() // empty includes means all files included by default
					|| includes.stream().anyMatch(matcher -> matcher.matches(path)))
					&& excludes.stream().noneMatch(matcher -> matcher.matches(path))) {
				return visitor.visit(resource);
			}
			return true;
		});
	}

	/**
	 * Test if a resource matches the set of resources for the source folder
	 *
	 * @param buildpath The build path configuration
	 * @param resource  The resource, may be {@code null}
	 * @return The source folder matching the resource
	 */
	public static Optional<SourceFolder> findSourceFolder(final Buildpath buildpath, final IResource resource) {
		Objects.requireNonNull(buildpath);
		if (resource == null) {
			return Optional.empty();
		}
		return buildpath.getSourceFolders().stream().filter(candidate -> matches(candidate, resource)).findFirst();
	}

	/**
	 * Find a path relative to a source folder
	 *
	 * @param buildpath The build path configuration
	 * @param resource  The resource, may be {@code null}
	 * @return A path relative to a source folder
	 */
	public static Optional<IPath> findRelativePath(final Buildpath buildpath, final IResource resource) {
		Objects.requireNonNull(buildpath);
		if (resource == null) {
			return Optional.empty();
		}
		return buildpath.getSourceFolders().stream()
				.map(candidate -> getRelativePath(candidate, resource).filter(path -> matches(candidate, path)))
				.flatMap(Optional::stream).findFirst();
	}

	/**
	 * Test if a resource matches the set of resources for the source folder
	 *
	 * @param sourceFolder The source folder
	 * @param resource     The resource, may be {@code null}
	 * @return {@code true} if the resource matches, {@code false} otherwise
	 */
	public static boolean matches(final SourceFolder sourceFolder, final IResource resource) {
		return getRelativePath(sourceFolder, resource).filter(path -> matches(sourceFolder, path)).isPresent();
	}

	/**
	 * Test if a relative path matches the set of resources for the source folder
	 *
	 * @param sourceFolder The source folder
	 * @param relativePath The relative path, may be {@code null}
	 * @return {@code true} if the resource matches, {@code false} otherwise
	 */
	public static boolean matches(final SourceFolder sourceFolder, final IPath relativePath) {
		Objects.requireNonNull(sourceFolder);
		if (relativePath == null) {
			return false;
		}
		final Path path = Paths.get(relativePath.toString());
		final List<PathMatcher> includes = getPathMatchers(sourceFolder.getIncludes());
		final List<PathMatcher> excludes = getPathMatchers(sourceFolder.getExcludes());
		return (includes.isEmpty() // empty includes means all files included by default
				|| includes.stream().anyMatch(matcher -> matcher.matches(path)))
				&& excludes.stream().noneMatch(matcher -> matcher.matches(path));
	}

	/**
	 * Get the path relative to the source folder
	 *
	 * @param sourceFolder The source folder
	 * @param resource     The resource, may be {@code null}
	 * @return A path relative to the source folder
	 */
	public static Optional<IPath> getRelativePath(final SourceFolder sourceFolder, final IResource resource) {
		Objects.requireNonNull(sourceFolder);
		if (resource == null) {
			return Optional.empty();
		}
		final IContainer container = findContainer(sourceFolder, resource.getProject());
		if (container == null || !container.getFullPath().isPrefixOf(resource.getFullPath())) {
			return Optional.empty();
		}
		return Optional.of(resource.getFullPath().makeRelativeTo(container.getFullPath()));
	}

	private static IContainer findContainer(final SourceFolder sourceFolder, final IProject project) {
		return project.findMember(sourceFolder.getName()) instanceof final IContainer container ? container : null;
	}

	private static IPath getRelativePath(final SourceFolder sourceFolder) {
		return new org.eclipse.core.runtime.Path(sourceFolder.getName());
	}

	private static List<PathMatcher> getPathMatchers(final List<Pattern> patterns) {
		return patterns.stream().map(BuildpathUtil::getPathMatcher).toList();
	}

	private static PathMatcher getPathMatcher(final Pattern pattern) {
		return switch (pattern.getSyntax()) {
		case GLOB ->
			FileSystems.getDefault().getPathMatcher(pattern.getSyntax() + ":" + convertGlobPattern(pattern.getValue())); //$NON-NLS-1$
		default -> FileSystems.getDefault().getPathMatcher(pattern.getSyntax() + ":" + pattern.getValue()); //$NON-NLS-1$
		};
	}

	private static String convertGlobPattern(String pattern) {
		// patterns starting with '/' match only in the top-level folder (anchor) (e.g.,
		// "/.git")
		if (pattern.startsWith("/")) { //$NON-NLS-1$
			pattern = pattern.substring(1);
		} else { // otherwise, match in any sub-folder (e.g., "*.fbt")
			pattern = "{**/,}" + pattern; //$NON-NLS-1$
		}
		// patterns ending with '/' match only folders (e.g., "bin/")
		if (pattern.endsWith("/")) { //$NON-NLS-1$
			pattern = pattern + "**"; //$NON-NLS-1$
		} else { // otherwise, match any suffix (e.g., "*.fbt")
			pattern = pattern + "{,/**}"; //$NON-NLS-1$
		}
		return pattern;
	}

	private static URI getBuildpathURI(final IProject project) {
		return URI.createPlatformResourceURI(getBuildpathFile(project).getFullPath().toString(), true);
	}

	private static IFile getBuildpathFile(final IProject project) {
		return project.getFile(BUILDPATH_FILE_NAME);
	}

	private static SourceFolder createSourceFolder(final String name) {
		final SourceFolder sourceFolder = BuildpathFactory.eINSTANCE.createSourceFolder();
		sourceFolder.setName(name);
		return sourceFolder;
	}

	private static SourceFolder createExternalSourceFolder(final String name) {
		final SourceFolder sourceFolder = createSourceFolder(name);
		sourceFolder.getAttributes().add(createAttribute(BuildpathAttributes.IGNORE_WARNINGS, String.valueOf(true)));
		return sourceFolder;
	}

	private static Attribute createAttribute(final String name, final String value) {
		final Attribute attribute = BuildpathAttributes.createAttribute(name);
		attribute.setValue(value);
		return attribute;
	}

	private static Pattern createPattern(final String value) {
		final Pattern pattern = BuildpathFactory.eINSTANCE.createPattern();
		pattern.setValue(value);
		return pattern;
	}

	private BuildpathUtil() {
		throw new UnsupportedOperationException();
	}
}
