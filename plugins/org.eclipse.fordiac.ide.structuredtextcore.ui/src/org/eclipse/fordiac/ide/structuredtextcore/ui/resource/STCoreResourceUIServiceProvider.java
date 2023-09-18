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
package org.eclipse.fordiac.ide.structuredtextcore.ui.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.buildpath.Buildpath;
import org.eclipse.fordiac.ide.model.buildpath.util.BuildpathUtil;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.resource.DefaultResourceUIServiceProvider;

import com.google.inject.Inject;

public class STCoreResourceUIServiceProvider extends DefaultResourceUIServiceProvider {

	@Inject
	public STCoreResourceUIServiceProvider(final IResourceServiceProvider delegate) {
		super(delegate);
	}

	@Override
	public boolean canBuild(final URI uri, final IStorage storage) {
		return super.canBuild(uri, storage) && isInSourceFolder(storage);
	}

	public static boolean isInSourceFolder(final IStorage storage) {
		if (storage instanceof final IResource resource) {
			final IProject project = resource.getProject();
			if (project != null) {
				final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibrary(project);
				final Buildpath buildpath = typeLibrary.getBuildpath();
				if (buildpath != null) {
					return BuildpathUtil.findSourceFolder(buildpath, resource).isPresent();
				}
			}
		}
		return false;
	}
}
