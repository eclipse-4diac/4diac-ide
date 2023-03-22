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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.globalconstantseditor.globalConstants.STVarGlobalDeclarationBlock;
import org.eclipse.fordiac.ide.globalconstantseditor.ui.Messages;
import org.eclipse.fordiac.ide.globalconstantseditor.validation.GlobalConstantsValidator;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

/**
 * Custom quickfixes.
 *
 * See
 * https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#quick-fixes
 */
public class GlobalConstantsQuickfixProvider extends DefaultQuickfixProvider {

	@Fix(GlobalConstantsValidator.GLOBAL_VARS_NOT_MARKED_CONSTANT)
	public void addConstModifier(final Issue issue, final IssueResolutionAcceptor acceptor) {
		acceptor.accept(issue, Messages.GlobalConstQuickFix_AddConstantKeyword,
				Messages.GlobalConstQuickFix_AddConstantKeyword, null,
				(final EObject object, final IModificationContext context) -> {
					if (object instanceof STVarGlobalDeclarationBlock) {
						final var block = (STVarGlobalDeclarationBlock) object;
						block.setConstant(true);
					}
				});
	}
}
