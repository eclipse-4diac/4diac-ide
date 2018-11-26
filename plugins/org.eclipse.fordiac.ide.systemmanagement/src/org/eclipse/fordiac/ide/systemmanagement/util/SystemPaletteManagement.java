/*******************************************************************************
 * Copyright (c) 2008 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.core.resources.IProject;
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
public class SystemPaletteManagement {

	/**
	 * Copy tool type lib to project.
	 * 
	 * @param project the project
	 */
	public static void copyToolTypeLibToProject(final IProject project) {		
		try {
			copyDirectory(TypeLibrary.getToolLibFolder(), project);
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}
	
	private static void copyDirectory(final IContainer sourceLocation,
			final IContainer targetLocation) throws IOException, CoreException {

		IProgressMonitor monitor = new NullProgressMonitor();

		if (!targetLocation.exists()) {
			((IFolder) targetLocation).create(true, true, monitor);
			targetLocation.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		}

		for (IResource resource : sourceLocation.members()) {
			if (!resource.getName().startsWith(".")) { //$NON-NLS-1$
				if (resource instanceof IFolder) {
					IFolder target = targetLocation.getFolder(new Path(resource
							.getName()));
					copyDirectory((IFolder) resource, target);
				} else if (resource instanceof IFile) {
					IFile file = targetLocation.getFile(new Path(resource
							.getName()));
					File in = ((IFile) resource).getLocation().toFile();
					File out = file.getLocation().toFile();
					ImportUtils.copyFile(in, out);
				}
			}
		}
		targetLocation.refreshLocal(IResource.DEPTH_INFINITE, monitor);
	}

}
