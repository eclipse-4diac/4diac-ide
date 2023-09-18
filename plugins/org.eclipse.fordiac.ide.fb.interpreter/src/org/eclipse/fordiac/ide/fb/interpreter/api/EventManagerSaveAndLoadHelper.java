/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class EventManagerSaveAndLoadHelper {
	public static void saveEventManagerToFile(final FBType fbtype, final EventManager evntMngr) {
		final ResourceSet reset = new ResourceSetImpl();
		final IProject project = fbtype.getTypeEntry().getFile().getProject();
		final IFolder folder = project.getFolder("traces"); //$NON-NLS-1$
		if (!folder.exists()) {
			try {
				folder.create(false, false, null);
			} catch (final CoreException e) {
				FordiacLogHelper.logError(e.getMessage());
			}
		}
		final IFile file = folder.getFile(fbtype.getQualifiedName() + ".EvntMngr.opsem"); //$NON-NLS-1$
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		final Resource res = reset.createResource(uri);

		res.getContents().add(evntMngr);
		reset.getResources().add(fbtype.eResource());
		try {
			res.save(Collections.emptyMap());
		} catch (final IOException e) {
			FordiacLogHelper.logError(e.getMessage());
		}
	}
}
