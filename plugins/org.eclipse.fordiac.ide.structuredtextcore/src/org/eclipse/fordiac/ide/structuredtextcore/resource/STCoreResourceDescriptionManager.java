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
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager;

import com.google.inject.Inject;

public class STCoreResourceDescriptionManager extends DefaultResourceDescriptionManager {

	@Inject
	private IQualifiedNameConverter nameConverter;

	@Override
	protected IResourceDescription internalGetResourceDescription(final Resource resource,
			final IDefaultResourceDescriptionStrategy strategy) {
		return new STCoreResourceDescription(resource, strategy, getCache(), nameConverter);
	}

	@Override
	public boolean isAffected(final Collection<Delta> deltas, final IResourceDescription candidate,
			final IResourceDescriptions context) throws IllegalArgumentException {
		final Collection<Delta> visibleDeltas = getVisibleDeltas(deltas, candidate);
		return !visibleDeltas.isEmpty() && super.isAffected(visibleDeltas, candidate, context);
	}

	private static Collection<IResourceDescription.Delta> getVisibleDeltas(final Collection<Delta> deltas,
			final IResourceDescription candidate) {
		final Set<IProject> visibleProjects = getVisibleProjects(candidate.getURI());
		if (visibleProjects.isEmpty()) {
			return deltas;
		}

		return deltas.stream().filter(delta -> isVisible(delta, visibleProjects)).toList();
	}

	private static Set<IProject> getVisibleProjects(final URI uri) {
		final TypeLibrary typeLibrary = TypeLibraryManager.INSTANCE.getTypeLibraryFromURI(uri);
		if (typeLibrary == null) {
			return Set.of();
		}
		final IProject project = typeLibrary.getProject();
		if (project == null) {
			return Set.of();
		}
		final Set<IProject> visibleProjects = new HashSet<>(typeLibrary.getReferencedProjects());
		visibleProjects.add(project);
		return visibleProjects;
	}

	private static boolean isVisible(final Delta delta, final Set<IProject> visibleProjects) {
		final IProject project = getProject(delta.getUri());
		return project == null || visibleProjects.contains(project);
	}

	private static IProject getProject(final URI uri) {
		if (uri.isPlatformResource() && uri.segmentCount() >= 2) {
			return ResourcesPlugin.getWorkspace().getRoot().getProject(URI.decode(uri.segment(1)));
		}
		return null;
	}

	public IQualifiedNameConverter getNameConverter() {
		return nameConverter;
	}
}
