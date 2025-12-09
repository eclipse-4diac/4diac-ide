/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.builder;

import java.util.Set;
import java.util.stream.StreamSupport;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.datatype.helper.InternalAttributeDeclarations;
import org.eclipse.fordiac.ide.model.datatype.helper.TypeDeclarationParser;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.structuredtextalgorithm.resource.STAlgorithmResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil;
import org.eclipse.fordiac.ide.structuredtextcore.ui.validation.ValidationUtil;
import org.eclipse.xtext.builder.builderState.IBuilderState;
import org.eclipse.xtext.builder.impl.IToBeBuiltComputerContribution;
import org.eclipse.xtext.builder.impl.ToBeBuilt;
import org.eclipse.xtext.resource.IResourceDescription;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STAlgorithmToBeBuiltComputerContribution implements IToBeBuiltComputerContribution {

	@Inject
	private IBuilderState builderState;

	@Override
	public void removeProject(final ToBeBuilt toBeBuilt, final IProject project, final IProgressMonitor monitor) {
		// do nothing
	}

	@Override
	public void updateProject(final ToBeBuilt toBeBuilt, final IProject project, final IProgressMonitor monitor)
			throws CoreException {
		// do nothing
	}

	@Override
	public boolean removeStorage(final ToBeBuilt toBeBuilt, final IStorage storage, final IProgressMonitor monitor) {
		if (storage instanceof final IFile file && STAlgorithmResource.isValidFileExtension(file.getFileExtension())) {
			return removeTypeEntry(toBeBuilt, file);
		}
		return false;
	}

	protected boolean removeTypeEntry(final ToBeBuilt toBeBuilt, final IFile file) {
		final URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		toBeBuilt.getToBeDeleted().add(uri);
		collectRelatedURIs(uri, toBeBuilt.getToBeDeleted());
		return true;
	}

	@Override
	public boolean updateStorage(final ToBeBuilt toBeBuilt, final IStorage storage, final IProgressMonitor monitor) {
		if (storage instanceof final IFile file && STAlgorithmResource.isValidFileExtension(file.getFileExtension())) {
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
			if (typeEntry != null) {
				return updateTypeEntry(toBeBuilt, typeEntry);
			}
		}
		return false;
	}

	protected boolean updateTypeEntry(final ToBeBuilt toBeBuilt, final TypeEntry typeEntry) {
		final URI uri = typeEntry.getURI();
		toBeBuilt.getToBeUpdated().add(uri);
		collectRelatedURIs(uri, toBeBuilt.getToBeDeleted());
		final LibraryElement type = typeEntry.getType();
		if (type != null) {
			final TreeIterator<EObject> contents = type.eAllContents();
			while (contents.hasNext()) {
				final EObject next = contents.next();
				if (next instanceof final Attribute attribute) {
					updateAttribute(toBeBuilt, attribute);
				} else if (next instanceof final VarDeclaration varDeclaration) {
					updateVarDeclaration(toBeBuilt, varDeclaration);
				}
			}
		}
		return true;
	}

	protected static void updateAttribute(final ToBeBuilt toBeBuilt, final Attribute attribute) {
		if (attribute.getType() instanceof AnyType
				&& !InternalAttributeDeclarations.isInternalAttribute(attribute.getAttributeDeclaration())
				&& !STCoreUtil.isSimpleAttributeValue(attribute, false)) {
			updateElement(toBeBuilt, attribute);
		}
	}

	protected static void updateVarDeclaration(final ToBeBuilt toBeBuilt, final VarDeclaration varDeclaration) {
		if (varDeclaration.isArray() && !ValidationUtil.isContainedInTypedInstance(varDeclaration)
				&& !TypeDeclarationParser.isSimpleTypeDeclaration(varDeclaration.getArraySize().getValue())) {
			updateElement(toBeBuilt, varDeclaration.getArraySize());
		}
		if (!STCoreUtil.isSimpleInitialValue(varDeclaration, false)) {
			updateElement(toBeBuilt, varDeclaration.getValue());
		}
	}

	protected static void updateElement(final ToBeBuilt toBeBuilt, final EObject element) {
		final Resource resource = element.eResource();
		final URI uri = resource.getURI().appendQuery(resource.getURIFragment(element));
		toBeBuilt.getToBeUpdated().add(uri);
		toBeBuilt.getToBeDeleted().remove(uri);
	}

	protected void collectRelatedURIs(final URI uri, final Set<URI> result) {
		StreamSupport.stream(builderState.getAllResourceDescriptions().spliterator(), false)
				.map(IResourceDescription::getURI)
				.filter(candidate -> candidate.hasQuery() && candidate.trimQuery().equals(uri)).forEach(result::add);
	}

	@Override
	public boolean isPossiblyHandled(final IStorage storage) {
		return storage instanceof final IFile file && TypeLibraryManager.INSTANCE.getTypeEntryForFile(file) != null;
	}

	@Override
	public boolean isRejected(final IFolder folder) {
		return false;
	}
}
