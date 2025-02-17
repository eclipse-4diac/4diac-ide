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
grammar InternalContractSpec;

options {
	superClass=AbstractInternalContentAssistParser;
}

@lexer::header {
package org.eclipse.fordiac.ide.ide.contentassist.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.Lexer;
}

@parser::header {
package org.eclipse.fordiac.ide.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import org.eclipse.fordiac.ide.services.ContractSpecGrammarAccess;

}
@parser::members {
	private ContractSpecGrammarAccess grammarAccess;

	public void setGrammarAccess(ContractSpecGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		return tokenName;
	}
}

// Entry rule entryRuleModel
entryRuleModel
:
{ before(grammarAccess.getModelRule()); }
	 ruleModel
{ after(grammarAccess.getModelRule()); } 
	 EOF 
;

// Rule Model
ruleModel 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getModelAccess().getTimeSpecAssignment()); }
		(rule__Model__TimeSpecAssignment)*
		{ after(grammarAccess.getModelAccess().getTimeSpecAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTimeSpec
entryRuleTimeSpec
:
{ before(grammarAccess.getTimeSpecRule()); }
	 ruleTimeSpec
{ after(grammarAccess.getTimeSpecRule()); } 
	 EOF 
;

// Rule TimeSpec
ruleTimeSpec 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTimeSpecAccess().getDummy_ruleKeyword()); }
		'dummy_rule'
		{ after(grammarAccess.getTimeSpecAccess().getDummy_ruleKeyword()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Model__TimeSpecAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getModelAccess().getTimeSpecTimeSpecParserRuleCall_0()); }
		ruleTimeSpec
		{ after(grammarAccess.getModelAccess().getTimeSpecTimeSpecParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_INT : ('0'..'9')+;

RULE_STRING : ('"' ('\\' .|~(('\\'|'"')))* '"'|'\'' ('\\' .|~(('\\'|'\'')))* '\'');

RULE_ML_COMMENT : '/*' ( options {greedy=false;} : . )*'*/';

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
