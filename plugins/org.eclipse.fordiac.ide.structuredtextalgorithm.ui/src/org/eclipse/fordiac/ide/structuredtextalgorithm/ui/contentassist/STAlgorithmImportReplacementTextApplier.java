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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.contentassist;

import org.eclipse.fordiac.ide.model.commands.create.AddNewImportCommand;
import org.eclipse.fordiac.ide.model.helpers.ImportHelper;
import org.eclipse.fordiac.ide.structuredtextalgorithm.ui.editor.STAlgorithmEditorUtils;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.ui.contentassist.STCoreImportReplacementTextApplier;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.xtext.formatting.IWhitespaceInformationProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;

public class STAlgorithmImportReplacementTextApplier extends STCoreImportReplacementTextApplier {

	public STAlgorithmImportReplacementTextApplier(final XtextResource resource, final String importedNamespace,
			final IWhitespaceInformationProvider whitespaceInformationProvider) {
		super(resource, importedNamespace, whitespaceInformationProvider);
	}

	@Override
	protected void applyImport(final IDocument document, final ConfigurableCompletionProposal proposal)
			throws BadLocationException {
		if (getResource() instanceof final LibraryElementXtextResource libraryElementResource
				&& !ImportHelper.matchesImports(getImportedNamespace(), libraryElementResource.getLibraryElement())) {
			STAlgorithmEditorUtils.executeCommand(getResource().getURI(),
					new AddNewImportCommand(libraryElementResource.getLibraryElement(), getImportedNamespace()));
		}
	}
}
