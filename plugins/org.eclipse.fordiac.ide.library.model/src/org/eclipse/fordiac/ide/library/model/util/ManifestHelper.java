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
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.fordiac.ide.library.model.library.Library;
import org.eclipse.fordiac.ide.library.model.library.LibraryElement;
import org.eclipse.fordiac.ide.library.model.library.LibraryFactory;
import org.eclipse.fordiac.ide.library.model.library.LibraryPackage;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.library.Product;
import org.eclipse.fordiac.ide.library.model.library.Required;
import org.eclipse.fordiac.ide.library.model.library.VersionInfo;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryResourceFactoryImpl;
import org.eclipse.fordiac.ide.library.model.library.util.LibraryResourceImpl;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class ManifestHelper {
	private static final String MANIFEST_FILENAME = "MANIFEST.MF"; //$NON-NLS-1$
	private static final String SCOPE_PROJECT = "Project"; //$NON-NLS-1$
	private static final String SCOPE_LIBRARY = "Library"; //$NON-NLS-1$
	private static final String BASE_VERSION = "1.0.0"; //$NON-NLS-1$
	private static final String UTF_8 = "UTF-8"; //$NON-NLS-1$

	private static final String EXCLUDES = "excludes"; //$NON-NLS-1$
	private static final String INCLUDES = "includes"; //$NON-NLS-1$
	private static final String LIBRARY = "library"; //$NON-NLS-1$
	private static final String LIBRARY_ELEMENT = "libraryElement"; //$NON-NLS-1$
	private static final String SYMBOLIC_NAME = "symbolicName"; //$NON-NLS-1$

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
			/*
			 * FordiacLogHelper.logWarning("Could not load manifest", e); //$NON-NLS-1$
			 * return null;
			 */
			return getAndConvertOldManifest(uri);
		}
		return (Manifest) resource.getContents().get(0);
	}

	/**
	 * This method loads Manifests the old way (non-capitalised Tags/Attributes) -
	 * it then converts it into the new Format and saves
	 *
	 * <p>
	 * TODO: remove when no longer needed
	 *
	 * @param uri specified URI
	 * @return the converted manifest, or {@code null} if it couldn't be loaded or
	 *         converted
	 */
	private static Manifest getAndConvertOldManifest(final URI uri) {
		final LibraryResourceImpl res = new LibraryResourceImpl(uri);
		try {
			res.load(null);
		} catch (final IOException e) {
			FordiacLogHelper.logWarning("Could not load manifest", e); //$NON-NLS-1$
			return null;
		}
		final Manifest manifest = (Manifest) res.getContents().get(0);
		final ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(
				new EPackageRegistryImpl(EPackage.Registry.INSTANCE));
		extendedMetaData.putPackage(null, LibraryPackage.eINSTANCE);
		res.getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING, UTF_8);
		res.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
		res.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		res.getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);

		try {
			if (manifest.getExports() != null && manifest.getExports().getLibrary() != null
					&& !manifest.getExports().getLibrary().isEmpty()) {
				final EList<Library> libraries = manifest.getExports().getLibrary();
				final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setFeature("http://xml.org/sax/features/external-general-entities", false); //$NON-NLS-1$
				factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false); //$NON-NLS-1$
				final DocumentBuilder builder = factory.newDocumentBuilder();
				final Document document = builder.parse(uri.toFileString());
				document.getDocumentElement().normalize();

				final NodeList libs = document.getElementsByTagName(LIBRARY);
				for (int i = 0; i < libs.getLength(); i++) {
					final Node libNode = libs.item(i);
					final String libSymbName = libNode.getAttributes().getNamedItem(SYMBOLIC_NAME).getNodeValue();
					final Library lib = libraries.stream().filter(l -> l.getSymbolicName().equals(libSymbName))
							.findAny().orElse(null);
					if (lib == null) {
						continue;
					}
					final NodeList children = libNode.getChildNodes();
					for (int j = 0; j < children.getLength(); j++) {
						final Node child = children.item(j);
						EList<LibraryElement> libElements;
						if (INCLUDES.equals(child.getNodeName())) {
							libElements = lib.getIncludes().getLibraryElement();
						} else if (EXCLUDES.equals(child.getNodeName())) {
							libElements = lib.getExcludes().getLibraryElement();
						} else {
							continue;
						}
						final NodeList libElemNodes = child.getChildNodes();
						int l = 0;
						for (int k = 0; k < libElemNodes.getLength() && l < libElements.size(); k++) {
							final Node node = libElemNodes.item(k);
							if (LIBRARY_ELEMENT.equals(node.getNodeName())) {
								libElements.get(l).setValue(node.getTextContent());
								l++;
							}
						}
					}

				}
			}

			manifest.eResource().save(null);
		} catch (final IOException | ParserConfigurationException | SAXException e) {
			FordiacLogHelper.logWarning("Could not convert manifest", e); //$NON-NLS-1$
			return null;
		}
		return getManifest(uri); // properly load the converted manifest
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
	 * Sorts dependencies and saves the {@link Manifest}
	 *
	 * @param manifest specified manifest
	 * @return {@code true} if it was saved successfully, else {@code false}
	 */
	public static boolean sortAndSaveManifest(final Manifest manifest) {
		// ensure dependencies are sorted (can't use EList.sort())
		final var dependencies = new LinkedList<>(manifest.getDependencies().getRequired());
		manifest.getDependencies().getRequired().clear();
		dependencies.forEach(d -> ManifestHelper.addDependency(manifest, d));
		return saveManifest(manifest);
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
