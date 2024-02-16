/*******************************************************************************
 * Copyright (c) 2022, 2024 Primetals Technologies Austria GmbH
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
 *       - refactor adding missing variables
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.quickfix;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.VarDeclarationKind;
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.STCoreQuickfixProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation.STFunctionValidator;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
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

	@Override
	@Fix(Diagnostic.LINKING_DIAGNOSTIC)
	public void createMissingVariable(final Issue issue, final IssueResolutionAcceptor acceptor) {
		super.createMissingVariable(issue, acceptor);
	}

	@Override
	protected void createMissingVariable(final ICallable callable, final String name, final INamedElement type,
			final VarDeclarationKind kind) {
		if (callable instanceof final STFunction function) {
			createSTVarDeclaration(function.getVarDeclarations(), name, type, kind);
		}
	}
}
