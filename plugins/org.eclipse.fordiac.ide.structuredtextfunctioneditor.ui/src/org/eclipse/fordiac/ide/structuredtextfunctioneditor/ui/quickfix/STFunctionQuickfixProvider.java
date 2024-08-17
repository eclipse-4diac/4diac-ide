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
 *       - refactor quickfixes
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.quickfix;

import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.util.VarDeclarationKind;
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.STCoreQuickfixProvider;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.Messages;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation.STFunctionValidator;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

@SuppressWarnings("static-method")
public class STFunctionQuickfixProvider extends STCoreQuickfixProvider {

	@Fix(STFunctionValidator.FUNCTION_NAME_MISMATCH)
	public void fixFunctionNameMatchesTypeName(final Issue issue, final IssueResolutionAcceptor acceptor) {
		// multi resolutions need to have identical label, description, and image
		acceptor.acceptMulti(issue, Messages.STFunctionQuickfixProvider_RenameFunction,
				Messages.STFunctionQuickfixProvider_RenameFunction, null, (element, context) -> {
					context.setUpdateCrossReferences(true);
					context.setUpdateRelatedFiles(true);
					context.addModification(element,
							(final STFunction function) -> function.setName(issue.getData()[1]));
				});
	}

	@Override
	@Fix(Diagnostic.LINKING_DIAGNOSTIC)
	public void createMissingVariable(final Issue issue, final IssueResolutionAcceptor acceptor) {
		if (!hasSyntaxErrors(issue)) {
			super.createMissingVariable(issue, acceptor);
		}
	}

	@Override
	protected void createMissingVariable(final ICallable callable, final String name, final INamedElement type,
			final VarDeclarationKind kind) {
		if (callable instanceof final STFunction function) {
			createSTVarDeclaration(function.getVarDeclarations(), name, type, kind);
		}
	}
}
