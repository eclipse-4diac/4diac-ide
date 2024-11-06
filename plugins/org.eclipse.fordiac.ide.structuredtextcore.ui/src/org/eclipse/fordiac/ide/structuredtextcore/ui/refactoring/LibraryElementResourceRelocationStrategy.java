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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.resource.FordiacTypeResource;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.xtext.ide.refactoring.IResourceRelocationStrategy;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationChange;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationContext;
import org.eclipse.xtext.resource.FileExtensionProvider;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class LibraryElementResourceRelocationStrategy implements IResourceRelocationStrategy {

	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Override
	public void applyChange(final ResourceRelocationContext context) {
		if (!(context.getChangeSerializer() instanceof STCoreChangeSerializer)) {
			return; // do not execute for shared rename contribution
		}
		context.getChanges().stream().filter(this::isRelevant).forEach(change -> applyChange(context, change));
	}

	protected void applyChange(final ResourceRelocationContext context, final ResourceRelocationChange change) {
		context.addModification(change, resource -> modifyResource(resource, change));
	}

	protected void modifyResource(final Resource resource, final ResourceRelocationChange change) {
		if (resource instanceof final FordiacTypeResource typeResource && !typeResource.getContents().isEmpty()
				&& typeResource.getContents().getFirst() instanceof final LibraryElement libraryElement) {
			updateTypeName(libraryElement, change);
			updatePackageName(libraryElement, change);
		}
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected void updateTypeName(final LibraryElement libraryElement, final ResourceRelocationChange change) {
		final String oldName = libraryElement.getName();
		final String newName = TypeEntry.getTypeNameFromFileName(change.getToURI().lastSegment());
		if (!oldName.equals(newName)) {
			libraryElement.setName(newName);
		}
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected void updatePackageName(final LibraryElement libraryElement, final ResourceRelocationChange change) {
		final String oldPackageName = PackageNameHelper.getPackageName(libraryElement);
		final String newPackageName = PackageNameHelper.getPackageNameFromURI(change.getToURI());
		if (!oldPackageName.equals(newPackageName)) {
			PackageNameHelper.setPackageName(libraryElement, newPackageName);
		}
	}

	protected boolean isRelevant(final ResourceRelocationChange change) {
		final String fileExtension = change.getFromURI().fileExtension();
		return fileExtension != null && fileExtensionProvider.isValid(fileExtension.toLowerCase());
	}
}
