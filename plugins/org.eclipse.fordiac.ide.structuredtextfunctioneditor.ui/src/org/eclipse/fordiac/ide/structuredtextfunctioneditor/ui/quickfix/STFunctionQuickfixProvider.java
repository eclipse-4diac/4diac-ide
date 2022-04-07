/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextfunctioneditor.ui.quickfix;

import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.STCoreQuickfixProvider;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.validation.STFunctionValidator;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

@SuppressWarnings("nls")
public class STFunctionQuickfixProvider extends STCoreQuickfixProvider {

	@SuppressWarnings("boxing")
	@Fix(STFunctionValidator.WRONG_NAME_CASE)
	public static void fixVariableNameCasing(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Change variable name case as declared",
				"Changes " + issue.getData()[0] + "to " + issue.getData()[1], "upcase.png", (IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					xtextDocument.replace(issue.getOffset(), issue.getLength(), issue.getData()[1]);
				});
	}

	@Fix(STCoreValidator.TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR)
	public static void fixTrailingUnderscore(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Remove trailing '_' from identifier",
				"Remove trailing underscore from name: " + issue.getData()[0], "upcase.png",
				(IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					xtextDocument.replace(issue.getOffset(), issue.getLength(),
							issue.getData()[0].substring(0, issue.getData()[0].length() - 1));
				});
	}

	@Fix(STCoreValidator.CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR)
	public static void fixConsecutiveUnderscore(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Replace consecutive undersocres with one underscore from identifier",
				"Remove consecutive underscore from name: " + issue.getData()[0],
				"upcase.png", (IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					xtextDocument.replace(issue.getOffset(), issue.getLength(),
							issue.getData()[0].replaceAll("_(_)+", "_"));
				});
	}

}
