/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee
 *       - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - Add quick fix for VAR_GLOBAL without CONSTANT
 *******************************************************************************/
package org.eclipse.fordiac.ide.globalconstantseditor.ui.quickfix;

import java.text.MessageFormat;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.globalconstantseditor.ui.Messages;
import org.eclipse.fordiac.ide.globalconstantseditor.validation.GlobalConstantsValidator;
import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.STCoreQuickfixProvider;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

/**
 * Custom quickfixes.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#quick-fixes
 */
public class GlobalConstantsQuickfixProvider extends STCoreQuickfixProvider {

	@Fix(GlobalConstantsValidator.GLOBAL_CONSTANTS_NAME_MISMATCH)
	public static void fixGlobalConstantsMatchesTypeName(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue,
				MessageFormat.format(Messages.GlobalConstQuickFix_RenameGlobalConstants, (Object[]) issue.getData()),
				MessageFormat.format(Messages.GlobalConstQuickFix_RenameGlobalConstants, (Object[]) issue.getData()),
				null, (IModification) context -> {
					final IXtextDocument xtextDocument = context.getXtextDocument();
					if (xtextDocument != null) {
						xtextDocument.replace(issue.getOffset().intValue(), issue.getLength().intValue(),
								issue.getData()[1]);
					}
				});
	}

	@Fix(GlobalConstantsValidator.GLOBAL_VARS_NOT_MARKED_CONSTANT)
	public static void addConstModifier(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, Messages.GlobalConstQuickFix_AddConstantKeyword,
				Messages.GlobalConstQuickFix_AddConstantKeyword, null,
				(final EObject object, final IModificationContext context) -> {
					if (object instanceof final STVarGlobalDeclarationBlock block) {
						block.setConstant(true);
					}
				});
	}
}
