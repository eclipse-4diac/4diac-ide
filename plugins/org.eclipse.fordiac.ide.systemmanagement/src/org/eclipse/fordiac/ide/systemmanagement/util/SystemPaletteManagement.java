/*******************************************************************************
 * Copyright (c) 2008 - 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
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

/**
 * The Class SystemPaletteManagement.
 */
public final class SystemPaletteManagement {
	private static final String MANIFEST_FILE = "MANIFEST.MF"; //$NON-NLS-1$

	public static void linkToolTypeLibsToDestination(final Map<String, java.net.URI> libs, final IFolder destination) {
		try {
			final IProgressMonitor monitor = new NullProgressMonitor();

			if (!destination.exists()) {
				destination.create(true, true, monitor);
				destination.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			}

			libs.forEach((name, loc) -> {
				final IFolder link = destination.getFolder(new Path(name));
				final IFile man = link.getFile(MANIFEST_FILE);
				final java.net.URI typelib = URIUtil.append(loc, "typelib"); //$NON-NLS-1$
				final java.net.URI manifest = URIUtil.append(loc, MANIFEST_FILE);

				try {
					link.createLink(typelib, IResource.NONE, monitor);
					man.createLink(manifest, IResource.HIDDEN, monitor);
				} catch (final CoreException e) {
					FordiacLogHelper.logError(e.getMessage(), e);
				}
			});

		} catch (final Exception e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	public static Map<Manifest, java.net.URI> getStandardLibraries(final IProject project) {
		final Map<Manifest, java.net.URI> libraries = new HashMap<>();
		final FilenameFilter filter = (file, name) -> name.equals(MANIFEST_FILE);

		final java.net.URI typeLibURI = java.net.URI.create("ECLIPSE_HOME/" + TypeLibraryTags.TYPE_LIBRARY); //$NON-NLS-1$
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

	private SystemPaletteManagement() {
		throw new UnsupportedOperationException("SystemPaletteManagement utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
