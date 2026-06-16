/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.LibraryElementResource;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeEntry;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.xtext.ide.serializer.IChangeSerializer;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.ui.refactoring.ui.SyncUtil;
import org.eclipse.xtext.ui.refactoring2.ChangeConverter;
import org.eclipse.xtext.ui.refactoring2.LtkIssueAcceptor;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;
import org.eclipse.xtext.ui.resource.LiveScopeResourceSetInitializer;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.inject.Inject;
import com.google.inject.Provider;

@SuppressWarnings("restriction")
public class STCorePackageNameChangeParticipant extends RenameParticipant {

	@Inject
	private SyncUtil syncUtil;

	@Inject
	private Provider<IChangeSerializer> changeSerializerProvider;

	@Inject
	private IResourceSetProvider resourceSetProvider;

	@Inject
	private LiveScopeResourceSetInitializer liveScopeResourceSetInitializer;

	@Inject
	private ChangeConverter.Factory changeConverterFactory;

	@Inject
	private LtkIssueAcceptor issues;

	private DataTypeEntry dataTypeEntry;

	private Change change;

	@Override
	protected boolean initialize(final Object element) {
		try {
			syncUtil.totalSync(true, true, false);
		} catch (final InvocationTargetException e) {
			return false;
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		}
		if (element instanceof final DataTypeEntry entry && entry.getFile() != null) {
			dataTypeEntry = entry;
			return true;
		}
		return false;
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		change = createPackageNameChange(pm);
		return issues.getRefactoringStatus();
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return change;
	}

	private Change createPackageNameChange(final IProgressMonitor pm) {
		final String newPackageName = Objects.requireNonNullElse(getArguments().getNewName(), ""); //$NON-NLS-1$
		if (Objects.equals(dataTypeEntry.getPackageName(), newPackageName)) {
			return null;
		}

		final ResourceSet resourceSet = resourceSetProvider.get(dataTypeEntry.getFile().getProject());
		liveScopeResourceSetInitializer.initialize(resourceSet);
		final URI sourceURI = dataTypeEntry.getURI();
		if (sourceURI == null) {
			return null;
		}
		final Resource resource = resourceSet.getResource(sourceURI, true);
		final IChangeSerializer changeSerializer = changeSerializerProvider.get();
		changeSerializer.setUpdateCrossReferences(true);
		changeSerializer.setUpdateRelatedFiles(true);
		changeSerializer.setProgressMonitor(pm);
		changeSerializer.addModification(resource, changedResource -> setPackageName(changedResource, newPackageName));
		final ChangeConverter changeConverter = changeConverterFactory.create(getName(), it -> true, issues);
		changeSerializer.applyModifications(changeConverter);
		return changeConverter.getChange();
	}

	private static void setPackageName(final Resource resource, final String newPackageName) {
		if (resource instanceof final LibraryElementXtextResource libResource) {
			setPackageName(libResource, newPackageName);
		} else if (resource instanceof final LibraryElementResource libResource) {
			setPackageName(libResource.getLibraryElement(), newPackageName);
		}
	}

	private static void setPackageName(final LibraryElementXtextResource resource, final String newPackageName) {
		final IParseResult parseResult = resource.getParseResult();
		if (parseResult != null && parseResult.getRootASTElement() instanceof final STSource source) {
			setPackageName(source, newPackageName);
		}
		setPackageName(resource.getInternalLibraryElement(), newPackageName);
	}

	private static void setPackageName(final STSource source, final String newPackageName) {
		final EAttribute nameAttribute = SimpleAttributeResolver.NAME_RESOLVER.getAttribute(source);
		if (nameAttribute != null) {
			if (newPackageName == null || newPackageName.isEmpty()) {
				source.eUnset(nameAttribute);
			} else {
				source.eSet(nameAttribute, newPackageName);
			}
		}
	}

	private static void setPackageName(final LibraryElement libraryElement, final String newPackageName) {
		if (libraryElement != null) {
			PackageNameHelper.setPackageName(libraryElement, newPackageName);
		}
	}

	@Override
	public String getName() {
		return Messages.STCorePackageNameChangeParticipant_Name;
	}
}
