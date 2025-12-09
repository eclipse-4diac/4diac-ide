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
package org.eclipse.fordiac.ide.hierarchymanager.build;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.HierarchyPackage;
import org.eclipse.fordiac.ide.hierarchymanager.model.hierarchy.Leaf;
import org.eclipse.fordiac.ide.model.errormarker.ErrorMarkerBuilder;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;

import com.google.inject.Inject;

public class HierarchyBuilderParticipant implements IXtextBuilderParticipant {

	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Inject
	private IQualifiedNameProvider qualifiedNameProvider;

	@Override
	public void build(final IBuildContext context, final IProgressMonitor monitor) throws CoreException {
		final List<IResourceDescription.Delta> deltas = getRelevantDeltas(context);
		for (final var delta : deltas) {
			if (monitor.isCanceled()) {
				throw new OperationCanceledException();
			}
			if (delta.getOld() != null) {
				doClean(delta);
			}
			if (delta.getNew() != null) {
				doBuild(delta, context, monitor);
			}
		}
	}

	protected void doBuild(final IResourceDescription.Delta delta, final IBuildContext context,
			final IProgressMonitor monitor) throws CoreException {
		try {
			final Set<URI> references = new HashSet<>();
			final Resource resource = context.getResourceSet().getResource(delta.getUri(), true);
			final TreeIterator<EObject> allContents = EcoreUtil.getAllContents(resource, true);
			while (allContents.hasNext()) {
				if (monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
				final EObject target = allContents.next();
				if (target instanceof final Leaf leaf) {
					validateLeaf(leaf, references, delta, context);
				}
			}
		} catch (final OperationCanceledException e) {
			throw e;
		} catch (final RuntimeException e) {
			throw new CoreException(Status.error("Exception processing build delta " + delta.getUri().toString(), e)); //$NON-NLS-1$
		}
	}

	protected static void doClean(final IResourceDescription.Delta delta) throws CoreException {
		final IFile file = getFile(delta.getOld().getURI());
		if (file != null && file.exists()) {
			file.deleteMarkers(HierarchyLanguageConstants.HIERARCHY_PROBLEM, true, IResource.DEPTH_INFINITE);
		}
	}

	private void validateLeaf(final Leaf leaf, final Set<URI> references, final Delta delta,
			final IBuildContext context) throws CoreException {
		final Resource resource = loadContainerFile(leaf, delta, context);
		if (resource == null) {
			return;
		}
		final Optional<LibraryElement> libraryElement = resource.getContents().stream()
				.filter(LibraryElement.class::isInstance).map(LibraryElement.class::cast).findFirst();
		if (libraryElement.isEmpty()) {
			createMarker(
					MessageFormat.format(Messages.HierarchyBuilderParticipant_NoSuchLibraryElement,
							leaf.getContainerFileName()),
					leaf, HierarchyPackage.Literals.LEAF__CONTAINER_FILE_NAME, delta.getUri());
			return;
		}
		final Optional<INamedElement> referencedElement = libraryElement.get().findByQualifiedName(leaf.getRef())
				.findAny();
		if (referencedElement.isEmpty()) {
			createMarker(MessageFormat.format(Messages.HierarchyBuilderParticipant_NoSuchReferencedElement,
					leaf.getRef(), leaf.getContainerFileName()), leaf, HierarchyPackage.Literals.LEAF__REF,
					delta.getUri());
		} else if (!references.add(EcoreUtil.getURI(referencedElement.get()))) {
			createMarker(MessageFormat.format(Messages.HierarchyBuilderParticipant_DuplicateReferencedElement,
					leaf.getRef(), leaf.getContainerFileName()), leaf, HierarchyPackage.Literals.LEAF__REF,
					delta.getUri());
		}
	}

	protected Resource loadContainerFile(final Leaf leaf, final Delta delta, final IBuildContext context)
			throws CoreException {
		final IFile containerFile = context.getBuiltProject().getFile(leaf.getContainerFileName());
		if (!containerFile.exists()) {
			createMarker(
					MessageFormat.format(Messages.HierarchyBuilderParticipant_NoSuchContainerFile,
							leaf.getContainerFileName()),
					leaf, HierarchyPackage.Literals.LEAF__CONTAINER_FILE_NAME, delta.getUri());
			return null;
		}
		final URI containerURI = URI.createPlatformResourceURI(containerFile.getFullPath().toString(), true);
		try {
			return context.getResourceSet().getResource(containerURI, true);
		} catch (final Exception e) {
			createMarker(
					MessageFormat.format(Messages.HierarchyBuilderParticipant_CannotLoadContainerFile,
							leaf.getContainerFileName(), e.getLocalizedMessage()),
					leaf, HierarchyPackage.Literals.LEAF__CONTAINER_FILE_NAME, delta.getUri());
			return null;
		}
	}

	protected void createMarker(final String message, final EObject target, final EStructuralFeature feature,
			final URI uri) throws CoreException {
		final IFile file = getFile(uri);
		final QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(target.eContainer());
		if (file != null && file.exists() && qualifiedName != null) {
			ErrorMarkerBuilder.createErrorMarkerBuilder(message).setType(HierarchyLanguageConstants.HIERARCHY_PROBLEM)
					.setLocation(qualifiedName.toString("/")).setTarget(target).setFeature(feature).createMarker(file); //$NON-NLS-1$
		}
	}

	protected List<IResourceDescription.Delta> getRelevantDeltas(final IBuildContext context) {
		return context.getDeltas().stream().filter(this::isRelevantDelta).toList();
	}

	protected boolean isRelevantDelta(final IResourceDescription.Delta delta) {
		return fileExtensionProvider.isValid(delta.getUri().fileExtension());
	}

	protected static IFile getFile(final URI uri) {
		if (uri.isPlatformResource()) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
		}
		return null;
	}
}
