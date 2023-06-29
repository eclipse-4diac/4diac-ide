/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
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
 *   Martin Jobst
 *       - add quick fixes for function FB types
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.quickfix;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.STCoreQuickfixProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation.STFunctionValidator;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.ui.editor.quickfix.ReplaceModification;
import org.eclipse.xtext.validation.Issue;

@SuppressWarnings("static-method")
public class STFunctionQuickfixProvider extends STCoreQuickfixProvider {

	@Fix(STFunctionValidator.FUNCTION_NAME_MISMATCH)
	public void fixFunctionNameMatchesTypeName(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue,
				MessageFormat.format(Messages.STFunctionQuickfixProvider_RenameFunction, (Object[]) issue.getData()),
				MessageFormat.format(Messages.STFunctionQuickfixProvider_RenameFunction, (Object[]) issue.getData()),
				null, (IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					if (xtextDocument != null) {
						xtextDocument.replace(issue.getOffset().intValue(), issue.getLength().intValue(),
								issue.getData()[1]);
					}
				});
	}

	@Fix(Diagnostic.LINKING_DIAGNOSTIC)
	public void suggestSimilarVariable(final Issue issue, final IssueResolutionAcceptor acceptor)
			throws BadLocationException {
		final IModificationContext modificationContext = getModificationContextFactory()
				.createModificationContext(issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		if (xtextDocument != null) {
			final var resolvedElement = xtextDocument.readOnly((final XtextResource resource) -> (offsetHelper
					.resolveContainedElementAt(resource, issue.getOffset().intValue())));
			final EObject varContainer = EcoreUtil2.getContainerOfType(resolvedElement, STFunction.class);
			if (varContainer != null) {
				final String issueString = xtextDocument.get(issue.getOffset().intValue(),
						issue.getLength().intValue());
				final List<STVarDeclaration> similarSTVarDeclarations = new LinkedList<>();
				similarSTVarDeclarations.addAll(EcoreUtil2.getAllContentsOfType(varContainer, STVarDeclaration.class));
				similarSTVarDeclarations
				.removeIf(declaration -> !getSimilarityMatcher().isSimilar(issueString, declaration.getName()));
				for (final STVarDeclaration stVarDeclaration : similarSTVarDeclarations) {
					final String name = stVarDeclaration.getName();
					acceptor.accept(issue,
							MessageFormat.format(Messages.STFunctionQuickfixProvider_ChangeToExistingVariable, name),
							MessageFormat.format(Messages.STFunctionQuickfixProvider_ChangeToExistingVariable, name),
							null, new ReplaceModification(issue, stVarDeclaration.getName()));
				}
			}
		}
	}
}
