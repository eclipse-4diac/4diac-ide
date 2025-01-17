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
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.xtext.ide.serializer.IEmfResourceChange;
import org.eclipse.xtext.ide.serializer.impl.EObjectDescriptionDeltaProvider.Deltas;
import org.eclipse.xtext.ide.serializer.impl.RelatedXtextResourceUpdater;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.IAcceptor;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreRelatedXtextResourceUpdater extends RelatedXtextResourceUpdater {

	@Inject
	private STCoreImportUpdater importUpdater;

	@Override
	public void applyChange(final Deltas deltas, final IAcceptor<IEmfResourceChange> changeAcceptor) {
		final Resource resource = getResourceSet().getResource(getResource().getUri(), true);
		if (resource instanceof final LibraryElementXtextResource libResource) {
			importUpdater.updateImports(deltas, libResource.getInternalLibraryElement(),
					(imp, value) -> changeAcceptor.accept(new ImportedNamespaceChange(imp, value)));
		}
		if (resource instanceof final XtextResource xtextResource && hasContents(xtextResource)) {
			super.applyChange(deltas, changeAcceptor);
		}
	}

	protected static boolean hasContents(final XtextResource xtextResource) {
		final IParseResult parseResult = xtextResource.getParseResult();
		return parseResult != null && !parseResult.getRootNode().getText().isBlank();
	}
}
