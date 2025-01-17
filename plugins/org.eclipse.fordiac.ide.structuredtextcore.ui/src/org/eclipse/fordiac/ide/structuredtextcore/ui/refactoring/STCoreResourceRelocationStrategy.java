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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STSource;
import org.eclipse.xtext.ide.refactoring.IResourceRelocationStrategy;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationChange;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationContext;
import org.eclipse.xtext.ide.refactoring.ResourceRelocationContext.ChangeType;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreResourceRelocationStrategy implements IResourceRelocationStrategy {

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
		context.addModification(change, resource -> modifyResource(resource, change, context.getChangeType()));
	}

	protected void modifyResource(final Resource resource, final ResourceRelocationChange change,
			final ChangeType changeType) {
		if (resource instanceof final STCoreResource coreResource && coreResource.getInternalLibraryElement() != null) {
			updateTypeName(coreResource, change);
			if (changeType != ChangeType.RENAME) {
				updatePackageName(coreResource, change);
			}
		}
	}

	protected void updateTypeName(final STCoreResource resource, final ResourceRelocationChange change) {
		final String oldName = resource.getInternalLibraryElement().getName();
		final String newName = TypeEntry.getTypeNameFromFileName(change.getToURI().lastSegment());
		if (!oldName.equals(newName)) {
			updateTypeName(resource, oldName, newName);
		}
	}

	protected void updatePackageName(final STCoreResource resource, final ResourceRelocationChange change) {
		final String oldPackageName = PackageNameHelper.getPackageName(resource.getInternalLibraryElement());
		final String newPackageName = PackageNameHelper.getPackageNameFromURI(change.getToURI());
		if (!oldPackageName.equals(newPackageName)) {
			updatePackageName(resource, oldPackageName, newPackageName);
		}
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected void updateTypeName(final STCoreResource resource, final String oldName, final String newName) {
		resource.getInternalLibraryElement().setName(newName);
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected void updatePackageName(final STCoreResource resource, final String oldPackageName,
			final String newPackageName) {
		if (resource.getParseResult().getRootASTElement() instanceof final STSource source) {
			final EAttribute nameAttribute = SimpleAttributeResolver.NAME_RESOLVER.getAttribute(source);
			if (nameAttribute != null) {
				if (newPackageName == null || newPackageName.isEmpty()) {
					source.eUnset(nameAttribute);
				} else {
					source.eSet(nameAttribute, newPackageName);
				}
			}
		}
		PackageNameHelper.setPackageName(resource.getInternalLibraryElement(), newPackageName);
	}

	protected boolean isRelevant(final ResourceRelocationChange change) {
		final String fileExtension = change.getFromURI().fileExtension();
		return fileExtension != null && fileExtensionProvider.isValid(fileExtension.toLowerCase())
				&& !fileExtensionProvider.getPrimaryFileExtension().equalsIgnoreCase(fileExtension);
	}
}
