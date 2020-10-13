/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
import java.io.IOException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.dataimport.ImportUtils;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemmanagement.Activator;

/**
 * The Class SystemPaletteManagement.
 */
public final class SystemPaletteManagement {

	/**
	 * Copy tool type lib to project.
	 *
	 * @param destination the project
	 */
	public static void copyToolTypeLibToDestination(final IContainer destination) {
		try {
			copyDirectory(TypeLibrary.getToolLibFolder(), destination);
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	private static void copyDirectory(final IContainer sourceLocation, final IContainer targetLocation)
			throws IOException, CoreException {

		IProgressMonitor monitor = new NullProgressMonitor();

		if (!targetLocation.exists()) {
			((IFolder) targetLocation).create(true, true, monitor);
			targetLocation.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		}

		for (IResource resource : sourceLocation.members()) {
			if (!resource.getName().startsWith(".")) { //$NON-NLS-1$
				if (resource instanceof IFolder) {
					IFolder target = targetLocation.getFolder(new Path(resource.getName()));
					copyDirectory((IFolder) resource, target);
				} else if (resource instanceof IFile) {
					IFile file = targetLocation.getFile(new Path(resource.getName()));
					File in = ((IFile) resource).getLocation().toFile();
					File out = file.getLocation().toFile();
					ImportUtils.copyFile(in, out);
				}
			}
		}
		targetLocation.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

	private SystemPaletteManagement() {
		throw new UnsupportedOperationException("SystemPaletteManagement utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
