/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *       - initial API and implementation and/or initial documentation
 *   Ulzii Jargalsaikhan
 *		 - quickfix for WRONG_NAME_CASE warnings
 *		 - add quick fixes for suggesting similar variables
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.quickfix;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.STCoreQuickfixProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.ui.editor.quickfix.ReplaceModification;
import org.eclipse.xtext.validation.Issue;

public class STFunctionQuickfixProvider extends STCoreQuickfixProvider {

	@Fix(Diagnostic.LINKING_DIAGNOSTIC)
	public void suggestSimilarVariable(final Issue issue, final IssueResolutionAcceptor acceptor)
			throws BadLocationException {
		final IModificationContext modificationContext = getModificationContextFactory()
				.createModificationContext(issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		if (xtextDocument != null) {
			final var resolvedElement = xtextDocument.readOnly((final XtextResource resource) -> (offsetHelper
					.resolveContainedElementAt(resource, issue.getOffset())));
			final EObject varContainer = EcoreUtil2.getContainerOfType(resolvedElement, STFunction.class);
			if (varContainer != null) {
				final String issueString = xtextDocument.get(issue.getOffset(), issue.getLength());
				final List<STVarDeclaration> similarSTVarDeclarations = new LinkedList<>();
				similarSTVarDeclarations.addAll(EcoreUtil2.getAllContentsOfType(varContainer, STVarDeclaration.class));
				similarSTVarDeclarations
				.removeIf(declaration -> !getSimilarityMatcher().isSimilar(issueString, declaration.getName()));
				for (final STVarDeclaration stVarDeclaration : similarSTVarDeclarations) {
					final String name = stVarDeclaration.getName();
					acceptor.accept(issue, "Change to existing variable " + "\'" + name + "\'",
							"Change to existing variable" + "\'" + name + "\'", null,
							new ReplaceModification(issue, stVarDeclaration.getName()));
				}
			}
		}
	}
}
