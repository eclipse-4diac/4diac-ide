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
 *******************************************************************************/
package org.eclipse.foridac.ide.structuredtextfunctioneditor.ui.quickfix;

import org.eclipse.fordiac.ide.structuredtextcore.ui.quickfix.STCoreQuickfixProvider;

/**
 * Custom quickfixes.
 *
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#quick-fixes
 */
public class STFunctionQuickfixProvider extends STCoreQuickfixProvider {

	//	@Fix(STFunctionValidator.INVALID_NAME)
	//	public void capitalizeName(final Issue issue, IssueResolutionAcceptor acceptor) {
	//		acceptor.accept(issue, "Capitalize name", "Capitalize the name.", "upcase.png", new IModification() {
	//			public void apply(IModificationContext context) throws BadLocationException {
	//				IXtextDocument xtextDocument = context.getXtextDocument();
	//				String firstLetter = xtextDocument.get(issue.getOffset(), 1);
	//				xtextDocument.replace(issue.getOffset(), 1, firstLetter.toUpperCase());
	//			}
	//		});
	//	}

}
