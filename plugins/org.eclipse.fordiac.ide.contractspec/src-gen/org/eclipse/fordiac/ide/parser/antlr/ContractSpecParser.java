/*******************************************************************************
 * Copyright (c) 2024 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.parser.antlr;

import com.google.inject.Inject;
import org.eclipse.fordiac.ide.parser.antlr.internal.InternalContractSpecParser;
import org.eclipse.fordiac.ide.services.ContractSpecGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class ContractSpecParser extends AbstractAntlrParser {

	@Inject
	private ContractSpecGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalContractSpecParser createParser(XtextTokenStream stream) {
		return new InternalContractSpecParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "Model";
	}

	public ContractSpecGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(ContractSpecGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
