/**
 * Copyright (c) 2021 Primetals Technologies GmbH
 *               2022 Martin Erich Jobst
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
 *   Martin Jobst
 *       - adapt non-compatible type quickfix
 */
package org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix;

import java.util.stream.StreamSupport;

import org.eclipse.fordiac.ide.structuredtextcore.scoping.STStandardFunctionProvider;
import org.eclipse.fordiac.ide.structuredtextcore.validation.STCoreValidator;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;

public class STCoreQuickfixProvider extends DefaultQuickfixProvider {

	@Inject
	private STStandardFunctionProvider standardFunctionProvider;

	@Fix(STCoreValidator.TRAILING_UNDERSCORE_IN_IDENTIFIER_ERROR)
	public static void fixTrailingUnderscore(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Remove trailing '_' from identifier",
				"Remove trailing underscore from name: " + issue.getData()[0], null, (IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					xtextDocument.replace(issue.getOffset(), issue.getLength(),
							issue.getData()[0].substring(0, issue.getData()[0].length() - 1));
				});
	}

	@Fix(STCoreValidator.CONSECUTIVE_UNDERSCORE_IN_IDENTIFIER_ERROR)
	public static void fixConsecutiveUnderscore(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Replace consecutive undersocres with one underscore from identifier",
				"Remove consecutive underscore from name: " + issue.getData()[0], null, (IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					xtextDocument.replace(issue.getOffset(), issue.getLength(),
							issue.getData()[0].replaceAll("_(_)+", "_"));
				});
	}

	@Fix(STCoreValidator.NON_COMPATIBLE_TYPES)
	public void fixNonCompatibleTypes(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final String castName = issue.getData()[0] + "_TO_" + issue.getData()[1]; //$NON-NLS-1$
		final boolean castPossible = StreamSupport.stream(standardFunctionProvider.get().spliterator(), true)
				.anyMatch(func -> func.getName().equals(castName));
		if (castPossible) {
			acceptor.accept(issue, "Add explicit typecast",
					"Add typecast from " + issue.getData()[0] + " to " + issue.getData()[1], null,
					(IModification) context -> {
						final IXtextDocument xtextDocument = context.getXtextDocument();
						final String original = xtextDocument.get(issue.getOffset().intValue(),
								issue.getLength().intValue());
						final String replacement = issue.getData()[0] + "_TO_" + issue.getData()[1] + "(" + original //$NON-NLS-1$ //$NON-NLS-2$
								+ ")";  //$NON-NLS-1$
						xtextDocument.replace(issue.getOffset().intValue(), issue.getLength().intValue(), replacement);
					});
		}
	}

	@Fix(STCoreValidator.WRONG_NAME_CASE)
	public static void fixVariableNameCasing(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, "Change variable name case as declared",
				"Changes " + issue.getData()[0] + "to " + issue.getData()[1], null, (IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					xtextDocument.replace(issue.getOffset(), issue.getLength(), issue.getData()[1]);
				});
	}
}
