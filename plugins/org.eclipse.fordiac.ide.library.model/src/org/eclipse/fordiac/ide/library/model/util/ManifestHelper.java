/*******************************************************************************
 * Copyright (c) 2024  Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.model.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.fordiac.ide.library.model.library.LibraryFactory;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Product;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.library.VersionInfo;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryResourceFactoryImpl;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.osgi.framework.Version;

public final class ManifestHelper {
	private static final String MANIFEST_FILENAME = "MANIFEST.MF"; //$NON-NLS-1$
	private static final String SCOPE_PROJECT = "Project"; //$NON-NLS-1$
	private static final String SCOPE_LIBRARY = "Library"; //$NON-NLS-1$
	private static final String BASE_VERSION = "1.0.0"; //$NON-NLS-1$
	private static final String UTF_8 = "UTF-8"; //$NON-NLS-1$

	// duplicated from SystemManager to avoid dependencies
	private static final String FORDIAC_PROJECT_NATURE_ID = "org.eclipse.fordiac.ide.systemmanagement.FordiacNature"; //$NON-NLS-1$

	private static LibraryFactory factory = LibraryFactory.eINSTANCE;
	private static LibraryResourceFactoryImpl resourceFactory = new LibraryResourceFactoryImpl();

	/**
	 * Returns (and if necessary creates) a {@link Manifest} for the specified
	 * {@link IProject}
	 *
	 * @param project specified project
	 * @return project manifest or {@code null} if project is missing the Fordiac
	 *         nature
	 */
	public static Manifest getOrCreateProjectManifest(final IProject project) {
		if (!isFordiacProject(project)) {
			return null;
		}
		Manifest manifest = getContainerManifest(project);
		if (manifest == null) {
			manifest = createProjectManifest(project, null);
		}
		return manifest;
	}

	/**
	 * Returns the {@link Manifest} contained in the specified {@link IContainer}
	 *
	 * @param container specified container
	 * @return the manifest, or {@code null} if it couldn't be loaded
	 */
	public static Manifest getContainerManifest(final IContainer container) {
		if (container == null || (container instanceof final IProject project && !isFordiacProject(project))) {
			return null;
		}
		final IFile manifest = container.getFile(new Path(MANIFEST_FILENAME));
		return getManifest(manifest);
	}

	/**
	 * Returns the {@link Manifest} contained in the specified folder {@link File}
	 *
	 * @param container specified folder
	 * @return the manifest, or {@code null} if it couldn't be loaded
	 */
	public static Manifest getFolderManifest(final File container) {
		if (container == null || !container.isDirectory()) {
			return null;
		}
		final File[] files = container.listFiles((dir, name) -> MANIFEST_FILENAME.equals(name));
		if (files.length == 0) {
			return null;
		}
		return getManifest(files[0]);
	}

	/**
	 * Returns the {@link Manifest} contained in the specified
	 * {@link java.nio.file.Path}
	 *
	 * @param path specified path
	 * @return the manifest, or {@code null} if it couldn't be loaded
	 */
	public static Manifest getFolderManifest(final java.nio.file.Path path) {
		if (path == null || !Files.isDirectory(path)) {
			return null;
		}
		try (var stream = Files.newDirectoryStream(path, MANIFEST_FILENAME)) {
			final Iterator<java.nio.file.Path> it = stream.iterator();
			if (it.hasNext()) {
				return getManifest(it.next());
			}
		} catch (final IOException e) {
			// empty
		}
		return null;
	}

	/**
	 * Returns the {@link Manifest} represented by the specified {@link IFile}
	 *
	 * @param manifest specified IFile
	 * @return the manifest, or {@code null} if it couldn't be loaded
	 */
	public static Manifest getManifest(final IFile manifest) {
		if (manifest == null || !manifest.exists()) {
			return null;
		}
		return getManifest(URI.createURI(manifest.getLocationURI().toString()));
	}

	/**
	 * Returns the {@link Manifest} represented by the specified {@link File}
	 *
	 * @param manifest specified file
	 * @return the manifest, or {@code null} if it couldn't be loaded
	 */
	public static Manifest getManifest(final File manifest) {
		if (manifest == null || !manifest.exists()) {
			return null;
		}
		return getManifest(URI.createURI(manifest.toURI().toString()));
	}

	/**
	 * Returns the {@link Manifest} represented by the specified
	 * {@link java.nio.file.Path}
	 *
	 * @param manifest specified path
	 * @return the manifest, or {@code null} if it couldn't be loaded
	 */
	public static Manifest getManifest(final java.nio.file.Path manifest) {
		if (manifest == null || Files.notExists(manifest)) {
			return null;
		}
		return getManifest(URI.createURI(manifest.toUri().toString()));
	}

	public static Manifest getReferencedManifest(final IProject project, final String symbolicName) {
		try {
			final IProject[] projects = project.getReferencedProjects();
			for (final IProject refProject : projects) {
				if (!refProject.isAccessible()) {
					continue;
				}
				final Manifest manifest = getContainerManifest(refProject);
				if (Objects.equals(symbolicName, getSymbolicName(manifest, refProject.getName()))) {
					return manifest;
				}
			}
		} catch (final CoreException e) {
			// do nothing
		}
		return null;
	}

	/**
	 * Get the symbolic name from a project
	 *
	 * @param project The project
	 * @return The symbolic name, project name, or {@code null}
	 */
	public static String getSymbolicName(final IProject project) {
		if (project == null) {
			return null;
		}
		final Manifest manifest = getContainerManifest(project);
		return getSymbolicName(manifest, project.getName());
	}

	/**
	 * Get the symbolic name from a manifest
	 *
	 * @param manifest     The manifest (may be null)
	 * @param defaultValue The default value
	 * @return The symbolic name or default value if no symbolic name is available
	 */
	public static String getSymbolicName(final Manifest manifest, final String defaultValue) {
		if (manifest == null || manifest.getProduct() == null || manifest.getProduct().getSymbolicName() == null) {
			return defaultValue;
		}
		return manifest.getProduct().getSymbolicName();
	}

	/**
	 * Get the product version from a manifest
	 *
	 * @param manifest     The manifest (may be null)
	 * @param defaultValue The default value
	 * @return The version or default value if no valid version is available
	 */
	public static Version getVersion(final Manifest manifest, final Version defaultValue) {
		if (manifest == null || manifest.getProduct() == null || manifest.getProduct().getVersionInfo() == null
				|| manifest.getProduct().getVersionInfo().getVersion() == null) {
			return defaultValue;
		}
		try {
			return new Version(manifest.getProduct().getVersionInfo().getVersion());
		} catch (final IllegalArgumentException e) {
			return defaultValue;
		}
	}

	/**
	 * Creates a new {@link Manifest} for the specified {@link IProject}
	 *
	 * @param project      specified project
	 * @param dependencies collection of {@link Required} to add to the manifest
	 * @return the created manifest
	 */
	public static Manifest createProjectManifest(final IProject project, final Collection<Required> dependencies) {
		final Manifest manifest = createManifest(SCOPE_PROJECT);
		final IFile manifestFile = project.getFile(MANIFEST_FILENAME);
		final Resource resource = createResource(URI.createURI(manifestFile.getLocationURI().toString()));

		if (dependencies != null) {
			for (final Required req : dependencies) {
				addDependency(manifest, req);
			}
		}

		resource.getContents().add(manifest);
		try {
			resource.save(null);
		} catch (final IOException e) {
			FordiacLogHelper.logWarning("Could not create project manifest", e); //$NON-NLS-1$
		}
		return manifest;
	}

	/**
	 * Creates a new {@link Manifest} with a specific scope
	 *
	 * @param scope manifest scope, either {@link #SCOPE_PROJECT} or
	 *              {@link #SCOPE_LIBRARY}
	 * @return the created manifest
	 */
	public static Manifest createManifest(final String scope) {
		final Manifest manifest = factory.createManifest();
		manifest.setScope(scope);

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //$NON-NLS-1$
		final VersionInfo versionInfo = factory.createVersionInfo();
		versionInfo.setAuthor(""); //$NON-NLS-1$
		versionInfo.setVersion(BASE_VERSION);
		versionInfo.setDate(formatter.format(LocalDate.now()));

		final Product product = factory.createProduct();
		product.setVersionInfo(versionInfo);
		manifest.setProduct(product);

		return manifest;
	}

	/**
	 * Check if {@link Manifest} has a project scope
	 *
	 * @param manifest specified manifest
	 * @return {@code true} if scope is {@link #SCOPE_PROJECT}, else false
	 */
	public static boolean isProject(final Manifest manifest) {
		return SCOPE_PROJECT.equals(manifest.getScope());
	}

	/**
	 * Check if {@link Manifest} has a library scope
	 *
	 * @param manifest specified manifest
	 * @return {@code true} if scope is {@link #SCOPE_LIBRARY}, else false
	 */
	public static boolean isLibrary(final Manifest manifest) {
		return SCOPE_LIBRARY.equals(manifest.getScope());
	}

	/**
	 * Add {@link Required} to the {@link Manifest} and remove an old one with the
	 * same {@code symbolicName} if necessary
	 *
	 * @param manifest   specified manifest
	 * @param dependency dependency to add
	 * @return result of {@link List#add}
	 */
	public static boolean addDependency(final Manifest manifest, final Required dependency) {
		if (manifest.getDependencies() == null) {
			manifest.setDependencies(factory.createDependencies());
		}
		final EList<Required> reqList = manifest.getDependencies().getRequired();
		int index = 0;
		while (index < reqList.size()) {
			final int comp = reqList.get(index).getSymbolicName().compareTo(dependency.getSymbolicName());
			if (comp == 0) {
				reqList.set(index, dependency);
				return true;
			}
			if (comp > 0) {
				reqList.add(index, dependency);
				return true;
			}
			index++;
		}
		reqList.add(index, dependency);
		return true;
	}

	/**
	 * Remove {@link Required} from the {@link Manifest}
	 *
	 * @param manifest   specified manifest
	 * @param dependency dependency to remove
	 * @return result of {@link List#remove}
	 */
	public static boolean removeDependency(final Manifest manifest, final Required dependency) {
		if (manifest.getDependencies() == null) {
			return false;
		}
		return manifest.getDependencies().getRequired().remove(dependency);
	}

	/**
	 * Update {@link Required} in the {@link Manifest}
	 *
	 * <p>
	 * Will only replace an existing dependency if the new one is not contained in
	 * it
	 *
	 * @param manifest   specified manifest
	 * @param dependency dependency to update
	 */
	public static void updateDependency(final Manifest manifest, final Required dependency) {
		if (manifest.getDependencies() == null) {
			manifest.setDependencies(factory.createDependencies());
		}
		sortManifestDependencies(manifest);
		final EList<Required> reqList = manifest.getDependencies().getRequired();
		int index = 0;
		while (index < reqList.size()) {
			final int comp = reqList.get(index).getSymbolicName().compareTo(dependency.getSymbolicName());
			if (comp == 0) {
				// replace dependency
				if (!VersionComparator.contains(reqList.get(index).getVersion(), dependency.getVersion())) {
					reqList.set(index, dependency);
				}
				return;
			}
			if (comp > 0) {
				reqList.add(index, dependency);
				return;
			}
			index++;
		}
		reqList.add(index, dependency);
	}

	/**
	 * Creates an {@link XMLResource} with the given {@link URI}
	 *
	 * @param uri specified URI
	 * @return created {@code XMLResource}
	 */
	public static XMLResource createResource(final URI uri) {
		final XMLResource resource = (XMLResource) resourceFactory.createResource(uri);
		resource.getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING, UTF_8);
		resource.getDefaultLoadOptions().put(XMLResource.OPTION_ENCODING, UTF_8);
		return resource;
	}

	/**
	 * Returns the {@link Manifest} represented by the specified {@link URI}
	 *
	 * @param uri specified URI
	 * @return the manifest, or {@code null} if it couldn't be loaded
	 */
	public static Manifest getManifest(final URI uri) {
		final Resource resource = createResource(uri);
		try {
			resource.load(null);
		} catch (final IOException e) {
			FordiacLogHelper.logWarning("Could not load manifest for URI " + uri, e); //$NON-NLS-1$
			return null;
		}
		return (Manifest) resource.getContents().get(0);
	}

	/**
	 * Saves the {@link Manifest}
	 *
	 * @param manifest specified manifest
	 * @return {@code true} if it was saved successfully, else {@code false}
	 */
	public static boolean saveManifest(final Manifest manifest) {
		try {
			manifest.eResource().save(null);
		} catch (final IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * Sorts dependencies of the {@link Manifest} <br>
	 * Note: Does not save the manifest.
	 *
	 * @param manifest specified manifest
	 * @return {@code true} if sorting was necessary, else {@code false}
	 */
	public static boolean sortManifestDependencies(final Manifest manifest) {
		if (manifest.getDependencies() == null) {
			return false; // nothing to do
		}
		final var dependencies = new BasicEList<>(manifest.getDependencies().getRequired());
		ECollections.sort(manifest.getDependencies().getRequired(),
				(o1, o2) -> o1.getSymbolicName().compareTo(o2.getSymbolicName()));

		return !dependencies.equals(manifest.getDependencies().getRequired());
	}

	/**
	 * Creates {@link Required} with given {@code symbolicName} and {@code version}
	 *
	 * @param symbolicName symbolic name of the dependency
	 * @param version      version (range) of the dependency
	 * @return
	 */
	public static Required createRequired(final String symbolicName, final String version) {
		final Required required = factory.createRequired();
		required.setSymbolicName(symbolicName);
		required.setVersion(version);
		return required;
	}

	/**
	 * Checks if given project has the Fordiac project nature
	 *
	 * @param project
	 * @return {@code true} if project has the Fordiac nature, else {@code false}
	 */
	private static boolean isFordiacProject(final IProject project) {
		try {
			return project.getNature(FORDIAC_PROJECT_NATURE_ID) != null;
		} catch (final CoreException e) {
			// empty
		}
		return false;
	}

	private ManifestHelper() {
	}

}
