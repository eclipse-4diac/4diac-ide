/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.IAllContainersState;

public class TypeLibraryAllContainersState implements IAllContainersState {

	@Override
	public boolean isEmpty(final String containerHandle) {
		final TypeLibrary typeLibrary = getTypeLibrary(containerHandle);
		return typeLibrary == null || typeLibrary.getAllTypes().isEmpty();
	}

	@Override
	public List<String> getVisibleContainerHandles(final String handle) {
		return List.of(handle);
	}

	@Override
	public Collection<URI> getContainedURIs(final String containerHandle) {
		final TypeLibrary typeLibrary = getTypeLibrary(containerHandle);
		if (typeLibrary == null) {
			return Collections.emptySet();
		}
		return typeLibrary.getAllTypes().stream().map(TypeEntry::getURI).collect(Collectors.toSet());
	}

	@Override
	public String getContainerHandle(final URI uri) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromURI(uri);
		if (typeLibrary == null) {
			return null;
		}
		final IProject project = typeLibrary.getProject();
		if (project == null) {
			return null;
		}
		return project.getName();
	}

	protected static TypeLibrary getTypeLibrary(final String handle) {
		if (handle == null) {
			return null;
		}
		final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(handle);
		if (project == null) {
			return null;
		}
		return TypeLibraryManager.INSTANCE.getTypeLibrary(project);
	}

	public static class ProviderImpl implements Provider {

		@Override
		public IAllContainersState get(final IResourceDescriptions context) {
			return new TypeLibraryAllContainersState();
		}
	}
}
