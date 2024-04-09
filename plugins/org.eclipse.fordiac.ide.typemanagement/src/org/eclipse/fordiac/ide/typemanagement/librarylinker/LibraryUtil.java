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
package org.eclipse.fordiac.ide.typemanagement.librarylinker;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IPathVariableManager;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.URIUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.library.model.library.Manifest;
import org.eclipse.fordiac.ide.library.model.util.ManifestHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class LibraryUtil {
	public static final String TYPE_LIB_FOLDER_NAME = "Type Library"; //$NON-NLS-1$
	public static final String LIB_TYPELIB_FOLDER_NAME = "typelib"; //$NON-NLS-1$
	public static final String PACKAGE_DOWNLOAD_DIRECTORY = ".download"; //$NON-NLS-1$
	public static final String EXTRACTED_LIB_DIRECTORY = ".lib"; //$NON-NLS-1$
	public static final String MANIFEST = "MANIFEST.MF"; //$NON-NLS-1$
	public static final java.net.URI STANDARD_LIBRARY_URI = java.net.URI
			.create("ECLIPSE_HOME/" + TypeLibraryTags.TYPE_LIBRARY); //$NON-NLS-1$
	public static final java.net.URI WORKSPACE_DOWNLOAD_URI = java.net.URI
			.create("WORKSPACE_LOC/" + PACKAGE_DOWNLOAD_DIRECTORY); //$NON-NLS-1$
	public static final java.net.URI WORKSPACE_LIBRARY_URI = java.net.URI
			.create("WORKSPACE_LOC/" + EXTRACTED_LIB_DIRECTORY); //$NON-NLS-1$

	public static Map<Manifest, java.net.URI> getLibraries(final IProject project, final java.net.URI typeLibURI) {
		final Map<Manifest, java.net.URI> libraries = new HashMap<>();
		final FilenameFilter filter = (file, name) -> name.equals(MANIFEST);

		final IPathVariableManager varMan = project.getPathVariableManager();
		final java.net.URI typeLibURIResolved = varMan.resolveURI(typeLibURI);
		final File typeLib = new File(typeLibURIResolved);

		if (typeLib.isDirectory()) {
			for (final File file : typeLib.listFiles()) {
				if (!file.getName().startsWith(".") && file.isDirectory()) { //$NON-NLS-1$
					final File[] manifestArr = file.listFiles(filter);
					if (manifestArr.length > 0) {
						final Manifest manifest = ManifestHelper
								.getManifest(URI.createURI(manifestArr[0].toURI().toString()));
						if (manifest != null && ManifestHelper.isLibrary(manifest) && manifest.getProduct() != null
								&& manifest.getProduct().getSymbolicName() != null) {
							libraries.put(manifest, URIUtil.append(typeLibURI, file.getName()));
						}
					}
				}
			}
		}

		return libraries;
	}

	public static Map<Manifest, java.net.URI> getStandardLibraries(final IProject project) {
		return getLibraries(project, STANDARD_LIBRARY_URI);
	}

	public static Map<Manifest, java.net.URI> getWorkspaceLibraries(final IProject project) {
		return getLibraries(project, WORKSPACE_LIBRARY_URI);
	}

	public static void linkLibrariesToDestination(final Map<String, java.net.URI> libs, final IFolder destination) {
		try {
			final IProgressMonitor monitor = new NullProgressMonitor();

			if (!destination.exists()) {
				destination.create(true, true, monitor);
				destination.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			}

			libs.forEach((name, loc) -> {
				final IFolder link = destination.getFolder(new Path(name));

				try {
					link.createLink(loc, IResource.NONE, monitor);
				} catch (final CoreException e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			});

		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	private LibraryUtil() {

	}
}
