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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
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

public final class ManifestHelper {
	private static final String MANIFEST_FILENAME = "MANIFEST.MF";
	private static final String SCOPE_PROJECT = "Project";
	private static final String SCOPE_LIBRARY = "Library";
	private static final String BASE_VERSION = "1.0.0";

	private static LibraryFactory factory = LibraryFactory.eINSTANCE;
	private static LibraryResourceFactoryImpl resourceFactory = new LibraryResourceFactoryImpl();

	public static Manifest getContainerManifest(final IContainer container) {
		if (container == null) {
			return null;
		}
		final IFile manifest = container.getFile(new Path(MANIFEST_FILENAME));
		if (!manifest.exists()) {
			return null;
		}
		return getManifest(URI.createURI(manifest.getLocationURI().toString()));
	}

	public static Manifest getFolderManifest(final File container) {
		if (container == null || !container.isDirectory()) {
			return null;
		}
		final File[] files = container.listFiles((dir, name) -> MANIFEST_FILENAME.equals(name));
		if (files.length == 0) {
			return null;
		}
		return getManifest(URI.createURI(files[0].toURI().toString()));
	}

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

	public static Manifest createManifest(final String scope) {
		final Manifest manifest = factory.createManifest();
		manifest.setScope(scope);

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		final VersionInfo versionInfo = factory.createVersionInfo();
		versionInfo.setAuthor("");
		versionInfo.setVersion(BASE_VERSION);
		versionInfo.setDate(formatter.format(LocalDate.now()));

		final Product product = factory.createProduct();
		product.setVersionInfo(versionInfo);
		manifest.setProduct(product);

		return manifest;
	}

	public static boolean isProject(final Manifest manifest) {
		return SCOPE_PROJECT.equals(manifest.getScope());
	}

	public static boolean isLibrary(final Manifest manifest) {
		return SCOPE_LIBRARY.equals(manifest.getScope());
	}

	public static void addDependency(final Manifest manifest, final Required dependency) {
		if (manifest.getDependencies() == null) {
			manifest.setDependencies(factory.createDependencies());
		}
		final EList<Required> reqList = manifest.getDependencies().getRequired();
		final Optional<Required> result = reqList.stream()
				.filter(r -> r.getSymbolicName().equals(dependency.getSymbolicName())).findAny();
		if (result.isPresent()) {
			reqList.remove(result.get());
		}
		reqList.add(dependency);
	}

	public static void removeDependency(final Manifest manifest, final Required dependency) {
		if (manifest.getDependencies() == null) {
			return;
		}
		final EList<Required> reqList = manifest.getDependencies().getRequired();
		reqList.remove(dependency);
	}

	public static void updateDependency(final Manifest manifest, final Required dependency) {
		if (manifest.getDependencies() == null) {
			manifest.setDependencies(factory.createDependencies());
		}
		final EList<Required> reqList = manifest.getDependencies().getRequired();
		final Optional<Required> result = reqList.stream()
				.filter(r -> r.getSymbolicName().equals(dependency.getSymbolicName())).findAny();
		if (result.isPresent()) {
			final VersionComparator comp = new VersionComparator();
			if (!comp.contains(result.get().getVersion(), dependency.getVersion())) { // replace dependency
				reqList.remove(result.get());
				reqList.add(dependency);
			}
		} else {
			reqList.add(dependency);
		}
	}

	public static Resource createResource(final URI uri) {
		final XMLResource resource = (XMLResource) resourceFactory.createResource(uri);
		resource.getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING, "UTF-8");
		resource.getDefaultLoadOptions().put(XMLResource.OPTION_ENCODING, "UTF-8");
		return resource;
	}

	public static Manifest getManifest(final URI uri) {
		final Resource resource = createResource(uri);
		try {
			resource.load(null);
		} catch (final IOException e) {
			FordiacLogHelper.logWarning("Could not load manifest", e); //$NON-NLS-1$
			return null;
		}
		return (Manifest) resource.getContents().get(0);
	}

	public static Required createRequired(final String symbolicName, final String version) {
		final Required required = factory.createRequired();
		required.setSymbolicName(symbolicName);
		required.setVersion(version);
		return required;
	}

	private ManifestHelper() {
	}
}
