/********************************************************************************
 * Copyright (c) 2008 - 2018  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Kepler University, Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Martijn Rooker,Gerhard Ebenhofer, Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *   Alois Zoitl - moved coordinate system resolution conversion to dedicated class,
 *   			   moved connection value parsing to fbnetwork importer where it
 *                 belongs
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.dataimport;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class ImportUtils {

	private ImportUtils() {
		// empty private constructor so that this utility class can not be insantiated
	}

	public static AdapterEvent createAdapterEvent(Event event, AdapterDeclaration a) {
		AdapterEvent ae = LibraryElementFactory.eINSTANCE.createAdapterEvent();
		ae.setName(event.getName());
		ae.setComment(event.getComment());
		ae.setAdapterDeclaration(a);

		return ae;
	}

	/**
	 * A helper method to get a platform independent regex for the split method.
	 *
	 * @return a "file.separator" regex
	 */
	public static String getSeperatorRegex() {
		String regex = File.separator;
		if (regex.equals("\\")) { //$NON-NLS-1$
			regex = "\\\\"; //$NON-NLS-1$
		}
		return regex;
	}

	/**
	 * copies a file from in to out.
	 *
	 * @param in  source File
	 * @param out destination File
	 *
	 * @throws Exception *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copyFile(final File in, final File out) throws IOException {
		Files.copy(in.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	public static void copyFile(final File in, final org.eclipse.core.resources.IFile out)
			throws IOException, CoreException {
		if (!out.getParent().exists()) {
			// create folder if does not exist
			((IFolder) out.getParent()).create(true, true, null);
			out.getParent().refreshLocal(IResource.DEPTH_ZERO, null);
		}

		Files.copy(in.toPath(), out.getLocation().toFile().toPath(), StandardCopyOption.REPLACE_EXISTING);

		try {
			out.getParent().refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

}
