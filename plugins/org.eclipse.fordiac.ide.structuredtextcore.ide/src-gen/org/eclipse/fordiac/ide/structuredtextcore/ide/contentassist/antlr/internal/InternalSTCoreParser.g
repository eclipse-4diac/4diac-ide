/*******************************************************************************
 * Copyright (c) 2021, 2023 Primetals Technologies GmbH, 
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
parser grammar InternalSTCoreParser;

options {
	tokenVocab=InternalSTCoreLexer;
	superClass=AbstractInternalContentAssistParser;
	backtrack=true;
}

@header {
package org.eclipse.fordiac.ide.structuredtextcore.ide.contentassist.antlr.internal;
import java.util.Map;
import java.util.HashMap;

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
import org.eclipse.fordiac.ide.structuredtextcore.services.STCoreGrammarAccess;

}
@members {
	private STCoreGrammarAccess grammarAccess;
	private final Map<String, String> tokenNameToValue = new HashMap<String, String>();
	
	{
		tokenNameToValue.put("NumberSign", "'#'");
		tokenNameToValue.put("Ampersand", "'&'");
		tokenNameToValue.put("LeftParenthesis", "'('");
		tokenNameToValue.put("RightParenthesis", "')'");
		tokenNameToValue.put("Asterisk", "'*'");
		tokenNameToValue.put("PlusSign", "'+'");
		tokenNameToValue.put("Comma", "','");
		tokenNameToValue.put("HyphenMinus", "'-'");
		tokenNameToValue.put("FullStop", "'.'");
		tokenNameToValue.put("Solidus", "'/'");
		tokenNameToValue.put("Colon", "':'");
		tokenNameToValue.put("Semicolon", "';'");
		tokenNameToValue.put("LessThanSign", "'<'");
		tokenNameToValue.put("EqualsSign", "'='");
		tokenNameToValue.put("GreaterThanSign", "'>'");
		tokenNameToValue.put("D", "'D'");
		tokenNameToValue.put("T", "'T'");
		tokenNameToValue.put("LeftSquareBracket", "'['");
		tokenNameToValue.put("RightSquareBracket", "']'");
		tokenNameToValue.put("LeftCurlyBracket", "'{'");
		tokenNameToValue.put("RightCurlyBracket", "'}'");
		tokenNameToValue.put("B", "'\%B'");
		tokenNameToValue.put("D_1", "'\%D'");
		tokenNameToValue.put("L", "'\%L'");
		tokenNameToValue.put("W", "'\%W'");
		tokenNameToValue.put("X", "'\%X'");
		tokenNameToValue.put("AsteriskAsterisk", "'**'");
		tokenNameToValue.put("FullStopFullStop", "'..'");
		tokenNameToValue.put("ColonColon", "'::'");
		tokenNameToValue.put("ColonEqualsSign", "':='");
		tokenNameToValue.put("LessThanSignEqualsSign", "'<='");
		tokenNameToValue.put("LessThanSignGreaterThanSign", "'<>'");
		tokenNameToValue.put("EqualsSignGreaterThanSign", "'=>'");
		tokenNameToValue.put("GreaterThanSignEqualsSign", "'>='");
		tokenNameToValue.put("AT", "'AT'");
		tokenNameToValue.put("BY", "'BY'");
		tokenNameToValue.put("DO", "'DO'");
		tokenNameToValue.put("DT", "'DT'");
		tokenNameToValue.put("IF", "'IF'");
		tokenNameToValue.put("LD", "'LD'");
		tokenNameToValue.put("LT", "'LT'");
		tokenNameToValue.put("OF", "'OF'");
		tokenNameToValue.put("ON", "'ON'");
		tokenNameToValue.put("OR", "'OR'");
		tokenNameToValue.put("TO", "'TO'");
		tokenNameToValue.put("ColonColonAsterisk", "'::*'");
		tokenNameToValue.put("AND", "'AND'");
		tokenNameToValue.put("FOR", "'FOR'");
		tokenNameToValue.put("INT", "'INT'");
		tokenNameToValue.put("LDT", "'LDT'");
		tokenNameToValue.put("MOD", "'MOD'");
		tokenNameToValue.put("NOT", "'NOT'");
		tokenNameToValue.put("REF", "'REF'");
		tokenNameToValue.put("TOD", "'TOD'");
		tokenNameToValue.put("VAR", "'VAR'");
		tokenNameToValue.put("XOR", "'XOR'");
		tokenNameToValue.put("BOOL", "'BOOL'");
		tokenNameToValue.put("BYTE", "'BYTE'");
		tokenNameToValue.put("CASE", "'CASE'");
		tokenNameToValue.put("CHAR", "'CHAR'");
		tokenNameToValue.put("DATE", "'DATE'");
		tokenNameToValue.put("DINT", "'DINT'");
		tokenNameToValue.put("ELSE", "'ELSE'");
		tokenNameToValue.put("EXIT", "'EXIT'");
		tokenNameToValue.put("FROM", "'FROM'");
		tokenNameToValue.put("LINT", "'LINT'");
		tokenNameToValue.put("LTOD", "'LTOD'");
		tokenNameToValue.put("NULL", "'NULL'");
		tokenNameToValue.put("REAL", "'REAL'");
		tokenNameToValue.put("SINT", "'SINT'");
		tokenNameToValue.put("STEP", "'STEP'");
		tokenNameToValue.put("TASK", "'TASK'");
		tokenNameToValue.put("THEN", "'THEN'");
		tokenNameToValue.put("THIS", "'THIS'");
		tokenNameToValue.put("TIME", "'TIME'");
		tokenNameToValue.put("TRUE", "'TRUE'");
		tokenNameToValue.put("TYPE", "'TYPE'");
		tokenNameToValue.put("UINT", "'UINT'");
		tokenNameToValue.put("WITH", "'WITH'");
		tokenNameToValue.put("WORD", "'WORD'");
		tokenNameToValue.put("ARRAY", "'ARRAY'");
		tokenNameToValue.put("CLASS", "'CLASS'");
		tokenNameToValue.put("DWORD", "'DWORD'");
		tokenNameToValue.put("ELSIF", "'ELSIF'");
		tokenNameToValue.put("FALSE", "'FALSE'");
		tokenNameToValue.put("FINAL", "'FINAL'");
		tokenNameToValue.put("LDATE", "'LDATE'");
		tokenNameToValue.put("LREAL", "'LREAL'");
		tokenNameToValue.put("LTIME", "'LTIME'");
		tokenNameToValue.put("LWORD", "'LWORD'");
		tokenNameToValue.put("SUPER", "'SUPER'");
		tokenNameToValue.put("UDINT", "'UDINT'");
		tokenNameToValue.put("ULINT", "'ULINT'");
		tokenNameToValue.put("UNTIL", "'UNTIL'");
		tokenNameToValue.put("USING", "'USING'");
		tokenNameToValue.put("USINT", "'USINT'");
		tokenNameToValue.put("WCHAR", "'WCHAR'");
		tokenNameToValue.put("WHILE", "'WHILE'");
		tokenNameToValue.put("ACTION", "'ACTION'");
		tokenNameToValue.put("END_IF", "'END_IF'");
		tokenNameToValue.put("IMPORT", "'IMPORT'");
		tokenNameToValue.put("METHOD", "'METHOD'");
		tokenNameToValue.put("PUBLIC", "'PUBLIC'");
		tokenNameToValue.put("REF_TO", "'REF_TO'");
		tokenNameToValue.put("REPEAT", "'REPEAT'");
		tokenNameToValue.put("RETAIN", "'RETAIN'");
		tokenNameToValue.put("RETURN", "'RETURN'");
		tokenNameToValue.put("SINGLE", "'SINGLE'");
		tokenNameToValue.put("STRING", "'STRING'");
		tokenNameToValue.put("STRUCT", "'STRUCT'");
		tokenNameToValue.put("END_FOR", "'END_FOR'");
		tokenNameToValue.put("END_VAR", "'END_VAR'");
		tokenNameToValue.put("EXTENDS", "'EXTENDS'");
		tokenNameToValue.put("INTERAL", "'INTERAL'");
		tokenNameToValue.put("OVERLAP", "'OVERLAP'");
		tokenNameToValue.put("PRIVATE", "'PRIVATE'");
		tokenNameToValue.put("PROGRAM", "'PROGRAM'");
		tokenNameToValue.put("WSTRING", "'WSTRING'");
		tokenNameToValue.put("ABSTRACT", "'ABSTRACT'");
		tokenNameToValue.put("CONSTANT", "'CONSTANT'");
		tokenNameToValue.put("CONTINUE", "'CONTINUE'");
		tokenNameToValue.put("END_CASE", "'END_CASE'");
		tokenNameToValue.put("END_STEP", "'END_STEP'");
		tokenNameToValue.put("END_TYPE", "'END_TYPE'");
		tokenNameToValue.put("FUNCTION", "'FUNCTION'");
		tokenNameToValue.put("INTERNAL", "'INTERNAL'");
		tokenNameToValue.put("INTERVAL", "'INTERVAL'");
		tokenNameToValue.put("OVERRIDE", "'OVERRIDE'");
		tokenNameToValue.put("PRIORITY", "'PRIORITY'");
		tokenNameToValue.put("RESOURCE", "'RESOURCE'");
		tokenNameToValue.put("VAR_TEMP", "'VAR_TEMP'");
		tokenNameToValue.put("END_CLASS", "'END_CLASS'");
		tokenNameToValue.put("END_WHILE", "'END_WHILE'");
		tokenNameToValue.put("INTERFACE", "'INTERFACE'");
		tokenNameToValue.put("NAMESPACE", "'NAMESPACE'");
		tokenNameToValue.put("PROTECTED", "'PROTECTED'");
		tokenNameToValue.put("READ_ONLY", "'READ_ONLY'");
		tokenNameToValue.put("VAR_INPUT", "'VAR_INPUT'");
		tokenNameToValue.put("END_ACTION", "'END_ACTION'");
		tokenNameToValue.put("END_METHOD", "'END_METHOD'");
		tokenNameToValue.put("END_REPEAT", "'END_REPEAT'");
		tokenNameToValue.put("END_STRUCT", "'END_STRUCT'");
		tokenNameToValue.put("IMPLEMENTS", "'IMPLEMENTS'");
		tokenNameToValue.put("NON_RETAIN", "'NON_RETAIN'");
		tokenNameToValue.put("READ_WRITE", "'READ_WRITE'");
		tokenNameToValue.put("TRANSITION", "'TRANSITION'");
		tokenNameToValue.put("VAR_ACCESS", "'VAR_ACCESS'");
		tokenNameToValue.put("VAR_CONFIG", "'VAR_CONFIG'");
		tokenNameToValue.put("VAR_GLOBAL", "'VAR_GLOBAL'");
		tokenNameToValue.put("VAR_IN_OUT", "'VAR_IN_OUT'");
		tokenNameToValue.put("VAR_OUTPUT", "'VAR_OUTPUT'");
		tokenNameToValue.put("END_PROGRAM", "'END_PROGRAM'");
		tokenNameToValue.put("TIME_OF_DAY", "'TIME_OF_DAY'");
		tokenNameToValue.put("END_FUNCTION", "'END_FUNCTION'");
		tokenNameToValue.put("END_RESOURCE", "'END_RESOURCE'");
		tokenNameToValue.put("INITIAL_STEP", "'INITIAL_STEP'");
		tokenNameToValue.put("LTIME_OF_DAY", "'LTIME_OF_DAY'");
		tokenNameToValue.put("VAR_EXTERNAL", "'VAR_EXTERNAL'");
		tokenNameToValue.put("CONFIGURATION", "'CONFIGURATION'");
		tokenNameToValue.put("DATE_AND_TIME", "'DATE_AND_TIME'");
		tokenNameToValue.put("END_INTERFACE", "'END_INTERFACE'");
		tokenNameToValue.put("END_NAMESPACE", "'END_NAMESPACE'");
		tokenNameToValue.put("END_TRANSITION", "'END_TRANSITION'");
		tokenNameToValue.put("FUNCTION_BLOCK", "'FUNCTION_BLOCK'");
		tokenNameToValue.put("LDATE_AND_TIME", "'LDATE_AND_TIME'");
		tokenNameToValue.put("END_CONFIGURATION", "'END_CONFIGURATION'");
		tokenNameToValue.put("END_FUNCTION_BLOCK", "'END_FUNCTION_BLOCK'");
	}

	public void setGrammarAccess(STCoreGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}

	@Override
	protected Grammar getGrammar() {
		return grammarAccess.getGrammar();
	}

	@Override
	protected String getValueForTokenName(String tokenName) {
		String result = tokenNameToValue.get(tokenName);
		if (result == null)
			result = tokenName;
		return result;
	}
}

// Entry rule entryRuleSTCoreSource
entryRuleSTCoreSource
:
{ before(grammarAccess.getSTCoreSourceRule()); }
	 ruleSTCoreSource
{ after(grammarAccess.getSTCoreSourceRule()); } 
	 EOF 
;

// Rule STCoreSource
ruleSTCoreSource 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTCoreSourceAccess().getGroup()); }
		(rule__STCoreSource__Group__0)
		{ after(grammarAccess.getSTCoreSourceAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTExpressionSource
entryRuleSTExpressionSource
:
{ before(grammarAccess.getSTExpressionSourceRule()); }
	 ruleSTExpressionSource
{ after(grammarAccess.getSTExpressionSourceRule()); } 
	 EOF 
;

// Rule STExpressionSource
ruleSTExpressionSource 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTExpressionSourceAccess().getGroup()); }
		(rule__STExpressionSource__Group__0)
		{ after(grammarAccess.getSTExpressionSourceAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTInitializerExpressionSource
entryRuleSTInitializerExpressionSource
:
{ before(grammarAccess.getSTInitializerExpressionSourceRule()); }
	 ruleSTInitializerExpressionSource
{ after(grammarAccess.getSTInitializerExpressionSourceRule()); } 
	 EOF 
;

// Rule STInitializerExpressionSource
ruleSTInitializerExpressionSource 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTInitializerExpressionSourceAccess().getGroup()); }
		(rule__STInitializerExpressionSource__Group__0)
		{ after(grammarAccess.getSTInitializerExpressionSourceAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTVarDeclaration
entryRuleSTVarDeclaration
:
{ before(grammarAccess.getSTVarDeclarationRule()); }
	 ruleSTVarDeclaration
{ after(grammarAccess.getSTVarDeclarationRule()); } 
	 EOF 
;

// Rule STVarDeclaration
ruleSTVarDeclaration 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getGroup()); }
		(rule__STVarDeclaration__Group__0)
		{ after(grammarAccess.getSTVarDeclarationAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTTypeDeclaration
entryRuleSTTypeDeclaration
:
{ before(grammarAccess.getSTTypeDeclarationRule()); }
	 ruleSTTypeDeclaration
{ after(grammarAccess.getSTTypeDeclarationRule()); } 
	 EOF 
;

// Rule STTypeDeclaration
ruleSTTypeDeclaration 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTTypeDeclarationAccess().getGroup()); }
		(rule__STTypeDeclaration__Group__0)
		{ after(grammarAccess.getSTTypeDeclarationAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTInitializerExpression
entryRuleSTInitializerExpression
:
{ before(grammarAccess.getSTInitializerExpressionRule()); }
	 ruleSTInitializerExpression
{ after(grammarAccess.getSTInitializerExpressionRule()); } 
	 EOF 
;

// Rule STInitializerExpression
ruleSTInitializerExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTInitializerExpressionAccess().getAlternatives()); }
		(rule__STInitializerExpression__Alternatives)
		{ after(grammarAccess.getSTInitializerExpressionAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTElementaryInitializerExpression
entryRuleSTElementaryInitializerExpression
:
{ before(grammarAccess.getSTElementaryInitializerExpressionRule()); }
	 ruleSTElementaryInitializerExpression
{ after(grammarAccess.getSTElementaryInitializerExpressionRule()); } 
	 EOF 
;

// Rule STElementaryInitializerExpression
ruleSTElementaryInitializerExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTElementaryInitializerExpressionAccess().getValueAssignment()); }
		(rule__STElementaryInitializerExpression__ValueAssignment)
		{ after(grammarAccess.getSTElementaryInitializerExpressionAccess().getValueAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTArrayInitializerExpression
entryRuleSTArrayInitializerExpression
:
{ before(grammarAccess.getSTArrayInitializerExpressionRule()); }
	 ruleSTArrayInitializerExpression
{ after(grammarAccess.getSTArrayInitializerExpressionRule()); } 
	 EOF 
;

// Rule STArrayInitializerExpression
ruleSTArrayInitializerExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTArrayInitializerExpressionAccess().getGroup()); }
		(rule__STArrayInitializerExpression__Group__0)
		{ after(grammarAccess.getSTArrayInitializerExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTArrayInitElement
entryRuleSTArrayInitElement
:
{ before(grammarAccess.getSTArrayInitElementRule()); }
	 ruleSTArrayInitElement
{ after(grammarAccess.getSTArrayInitElementRule()); } 
	 EOF 
;

// Rule STArrayInitElement
ruleSTArrayInitElement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTArrayInitElementAccess().getAlternatives()); }
		(rule__STArrayInitElement__Alternatives)
		{ after(grammarAccess.getSTArrayInitElementAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTSingleArrayInitElement
entryRuleSTSingleArrayInitElement
:
{ before(grammarAccess.getSTSingleArrayInitElementRule()); }
	 ruleSTSingleArrayInitElement
{ after(grammarAccess.getSTSingleArrayInitElementRule()); } 
	 EOF 
;

// Rule STSingleArrayInitElement
ruleSTSingleArrayInitElement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTSingleArrayInitElementAccess().getInitExpressionAssignment()); }
		(rule__STSingleArrayInitElement__InitExpressionAssignment)
		{ after(grammarAccess.getSTSingleArrayInitElementAccess().getInitExpressionAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTRepeatArrayInitElement
entryRuleSTRepeatArrayInitElement
:
{ before(grammarAccess.getSTRepeatArrayInitElementRule()); }
	 ruleSTRepeatArrayInitElement
{ after(grammarAccess.getSTRepeatArrayInitElementRule()); } 
	 EOF 
;

// Rule STRepeatArrayInitElement
ruleSTRepeatArrayInitElement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getGroup()); }
		(rule__STRepeatArrayInitElement__Group__0)
		{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTStructInitializerExpression
entryRuleSTStructInitializerExpression
:
{ before(grammarAccess.getSTStructInitializerExpressionRule()); }
	 ruleSTStructInitializerExpression
{ after(grammarAccess.getSTStructInitializerExpressionRule()); } 
	 EOF 
;

// Rule STStructInitializerExpression
ruleSTStructInitializerExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTStructInitializerExpressionAccess().getGroup()); }
		(rule__STStructInitializerExpression__Group__0)
		{ after(grammarAccess.getSTStructInitializerExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTStructInitElement
entryRuleSTStructInitElement
:
{ before(grammarAccess.getSTStructInitElementRule()); }
	 ruleSTStructInitElement
{ after(grammarAccess.getSTStructInitElementRule()); } 
	 EOF 
;

// Rule STStructInitElement
ruleSTStructInitElement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTStructInitElementAccess().getGroup()); }
		(rule__STStructInitElement__Group__0)
		{ after(grammarAccess.getSTStructInitElementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTPragma
entryRuleSTPragma
:
{ before(grammarAccess.getSTPragmaRule()); }
	 ruleSTPragma
{ after(grammarAccess.getSTPragmaRule()); } 
	 EOF 
;

// Rule STPragma
ruleSTPragma 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTPragmaAccess().getGroup()); }
		(rule__STPragma__Group__0)
		{ after(grammarAccess.getSTPragmaAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAttribute
entryRuleSTAttribute
:
{ before(grammarAccess.getSTAttributeRule()); }
	 ruleSTAttribute
{ after(grammarAccess.getSTAttributeRule()); } 
	 EOF 
;

// Rule STAttribute
ruleSTAttribute 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAttributeAccess().getGroup()); }
		(rule__STAttribute__Group__0)
		{ after(grammarAccess.getSTAttributeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAttributeName
entryRuleSTAttributeName
:
{ before(grammarAccess.getSTAttributeNameRule()); }
	 ruleSTAttributeName
{ after(grammarAccess.getSTAttributeNameRule()); } 
	 EOF 
;

// Rule STAttributeName
ruleSTAttributeName 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAttributeNameAccess().getQualifiedNameParserRuleCall()); }
		ruleQualifiedName
		{ after(grammarAccess.getSTAttributeNameAccess().getQualifiedNameParserRuleCall()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTStatement
entryRuleSTStatement
:
{ before(grammarAccess.getSTStatementRule()); }
	 ruleSTStatement
{ after(grammarAccess.getSTStatementRule()); } 
	 EOF 
;

// Rule STStatement
ruleSTStatement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTStatementAccess().getAlternatives()); }
		(rule__STStatement__Alternatives)
		{ after(grammarAccess.getSTStatementAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAssignment
entryRuleSTAssignment
:
{ before(grammarAccess.getSTAssignmentRule()); }
	 ruleSTAssignment
{ after(grammarAccess.getSTAssignmentRule()); } 
	 EOF 
;

// Rule STAssignment
ruleSTAssignment 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAssignmentAccess().getGroup()); }
		(rule__STAssignment__Group__0)
		{ after(grammarAccess.getSTAssignmentAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTCallArgument
entryRuleSTCallArgument
:
{ before(grammarAccess.getSTCallArgumentRule()); }
	 ruleSTCallArgument
{ after(grammarAccess.getSTCallArgumentRule()); } 
	 EOF 
;

// Rule STCallArgument
ruleSTCallArgument 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTCallArgumentAccess().getAlternatives()); }
		(rule__STCallArgument__Alternatives)
		{ after(grammarAccess.getSTCallArgumentAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTCallUnnamedArgument
entryRuleSTCallUnnamedArgument
:
{ before(grammarAccess.getSTCallUnnamedArgumentRule()); }
	 ruleSTCallUnnamedArgument
{ after(grammarAccess.getSTCallUnnamedArgumentRule()); } 
	 EOF 
;

// Rule STCallUnnamedArgument
ruleSTCallUnnamedArgument 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTCallUnnamedArgumentAccess().getArgumentAssignment()); }
		(rule__STCallUnnamedArgument__ArgumentAssignment)
		{ after(grammarAccess.getSTCallUnnamedArgumentAccess().getArgumentAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTCallNamedInputArgument
entryRuleSTCallNamedInputArgument
:
{ before(grammarAccess.getSTCallNamedInputArgumentRule()); }
	 ruleSTCallNamedInputArgument
{ after(grammarAccess.getSTCallNamedInputArgumentRule()); } 
	 EOF 
;

// Rule STCallNamedInputArgument
ruleSTCallNamedInputArgument 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTCallNamedInputArgumentAccess().getGroup()); }
		(rule__STCallNamedInputArgument__Group__0)
		{ after(grammarAccess.getSTCallNamedInputArgumentAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTCallNamedOutputArgument
entryRuleSTCallNamedOutputArgument
:
{ before(grammarAccess.getSTCallNamedOutputArgumentRule()); }
	 ruleSTCallNamedOutputArgument
{ after(grammarAccess.getSTCallNamedOutputArgumentRule()); } 
	 EOF 
;

// Rule STCallNamedOutputArgument
ruleSTCallNamedOutputArgument 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTCallNamedOutputArgumentAccess().getGroup()); }
		(rule__STCallNamedOutputArgument__Group__0)
		{ after(grammarAccess.getSTCallNamedOutputArgumentAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTIfStatement
entryRuleSTIfStatement
:
{ before(grammarAccess.getSTIfStatementRule()); }
	 ruleSTIfStatement
{ after(grammarAccess.getSTIfStatementRule()); } 
	 EOF 
;

// Rule STIfStatement
ruleSTIfStatement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTIfStatementAccess().getGroup()); }
		(rule__STIfStatement__Group__0)
		{ after(grammarAccess.getSTIfStatementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTElseIfPart
entryRuleSTElseIfPart
:
{ before(grammarAccess.getSTElseIfPartRule()); }
	 ruleSTElseIfPart
{ after(grammarAccess.getSTElseIfPartRule()); } 
	 EOF 
;

// Rule STElseIfPart
ruleSTElseIfPart 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTElseIfPartAccess().getGroup()); }
		(rule__STElseIfPart__Group__0)
		{ after(grammarAccess.getSTElseIfPartAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTCaseStatement
entryRuleSTCaseStatement
:
{ before(grammarAccess.getSTCaseStatementRule()); }
	 ruleSTCaseStatement
{ after(grammarAccess.getSTCaseStatementRule()); } 
	 EOF 
;

// Rule STCaseStatement
ruleSTCaseStatement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTCaseStatementAccess().getGroup()); }
		(rule__STCaseStatement__Group__0)
		{ after(grammarAccess.getSTCaseStatementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTCaseCases
entryRuleSTCaseCases
:
{ before(grammarAccess.getSTCaseCasesRule()); }
	 ruleSTCaseCases
{ after(grammarAccess.getSTCaseCasesRule()); } 
	 EOF 
;

// Rule STCaseCases
ruleSTCaseCases 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTCaseCasesAccess().getGroup()); }
		(rule__STCaseCases__Group__0)
		{ after(grammarAccess.getSTCaseCasesAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTElsePart
entryRuleSTElsePart
:
{ before(grammarAccess.getSTElsePartRule()); }
	 ruleSTElsePart
{ after(grammarAccess.getSTElsePartRule()); } 
	 EOF 
;

// Rule STElsePart
ruleSTElsePart 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTElsePartAccess().getGroup()); }
		(rule__STElsePart__Group__0)
		{ after(grammarAccess.getSTElsePartAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTForStatement
entryRuleSTForStatement
:
{ before(grammarAccess.getSTForStatementRule()); }
	 ruleSTForStatement
{ after(grammarAccess.getSTForStatementRule()); } 
	 EOF 
;

// Rule STForStatement
ruleSTForStatement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTForStatementAccess().getGroup()); }
		(rule__STForStatement__Group__0)
		{ after(grammarAccess.getSTForStatementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTWhileStatement
entryRuleSTWhileStatement
:
{ before(grammarAccess.getSTWhileStatementRule()); }
	 ruleSTWhileStatement
{ after(grammarAccess.getSTWhileStatementRule()); } 
	 EOF 
;

// Rule STWhileStatement
ruleSTWhileStatement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTWhileStatementAccess().getGroup()); }
		(rule__STWhileStatement__Group__0)
		{ after(grammarAccess.getSTWhileStatementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTRepeatStatement
entryRuleSTRepeatStatement
:
{ before(grammarAccess.getSTRepeatStatementRule()); }
	 ruleSTRepeatStatement
{ after(grammarAccess.getSTRepeatStatementRule()); } 
	 EOF 
;

// Rule STRepeatStatement
ruleSTRepeatStatement 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTRepeatStatementAccess().getGroup()); }
		(rule__STRepeatStatement__Group__0)
		{ after(grammarAccess.getSTRepeatStatementAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTExpression
entryRuleSTExpression
:
{ before(grammarAccess.getSTExpressionRule()); }
	 ruleSTExpression
{ after(grammarAccess.getSTExpressionRule()); } 
	 EOF 
;

// Rule STExpression
ruleSTExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTExpressionAccess().getSTSubrangeExpressionParserRuleCall()); }
		ruleSTSubrangeExpression
		{ after(grammarAccess.getSTExpressionAccess().getSTSubrangeExpressionParserRuleCall()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTSubrangeExpression
entryRuleSTSubrangeExpression
:
{ before(grammarAccess.getSTSubrangeExpressionRule()); }
	 ruleSTSubrangeExpression
{ after(grammarAccess.getSTSubrangeExpressionRule()); } 
	 EOF 
;

// Rule STSubrangeExpression
ruleSTSubrangeExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTSubrangeExpressionAccess().getGroup()); }
		(rule__STSubrangeExpression__Group__0)
		{ after(grammarAccess.getSTSubrangeExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTOrExpression
entryRuleSTOrExpression
:
{ before(grammarAccess.getSTOrExpressionRule()); }
	 ruleSTOrExpression
{ after(grammarAccess.getSTOrExpressionRule()); } 
	 EOF 
;

// Rule STOrExpression
ruleSTOrExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTOrExpressionAccess().getGroup()); }
		(rule__STOrExpression__Group__0)
		{ after(grammarAccess.getSTOrExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTXorExpression
entryRuleSTXorExpression
:
{ before(grammarAccess.getSTXorExpressionRule()); }
	 ruleSTXorExpression
{ after(grammarAccess.getSTXorExpressionRule()); } 
	 EOF 
;

// Rule STXorExpression
ruleSTXorExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTXorExpressionAccess().getGroup()); }
		(rule__STXorExpression__Group__0)
		{ after(grammarAccess.getSTXorExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAndExpression
entryRuleSTAndExpression
:
{ before(grammarAccess.getSTAndExpressionRule()); }
	 ruleSTAndExpression
{ after(grammarAccess.getSTAndExpressionRule()); } 
	 EOF 
;

// Rule STAndExpression
ruleSTAndExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAndExpressionAccess().getGroup()); }
		(rule__STAndExpression__Group__0)
		{ after(grammarAccess.getSTAndExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTEqualityExpression
entryRuleSTEqualityExpression
:
{ before(grammarAccess.getSTEqualityExpressionRule()); }
	 ruleSTEqualityExpression
{ after(grammarAccess.getSTEqualityExpressionRule()); } 
	 EOF 
;

// Rule STEqualityExpression
ruleSTEqualityExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTEqualityExpressionAccess().getGroup()); }
		(rule__STEqualityExpression__Group__0)
		{ after(grammarAccess.getSTEqualityExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTComparisonExpression
entryRuleSTComparisonExpression
:
{ before(grammarAccess.getSTComparisonExpressionRule()); }
	 ruleSTComparisonExpression
{ after(grammarAccess.getSTComparisonExpressionRule()); } 
	 EOF 
;

// Rule STComparisonExpression
ruleSTComparisonExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTComparisonExpressionAccess().getGroup()); }
		(rule__STComparisonExpression__Group__0)
		{ after(grammarAccess.getSTComparisonExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAddSubExpression
entryRuleSTAddSubExpression
:
{ before(grammarAccess.getSTAddSubExpressionRule()); }
	 ruleSTAddSubExpression
{ after(grammarAccess.getSTAddSubExpressionRule()); } 
	 EOF 
;

// Rule STAddSubExpression
ruleSTAddSubExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAddSubExpressionAccess().getGroup()); }
		(rule__STAddSubExpression__Group__0)
		{ after(grammarAccess.getSTAddSubExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTMulDivModExpression
entryRuleSTMulDivModExpression
:
{ before(grammarAccess.getSTMulDivModExpressionRule()); }
	 ruleSTMulDivModExpression
{ after(grammarAccess.getSTMulDivModExpressionRule()); } 
	 EOF 
;

// Rule STMulDivModExpression
ruleSTMulDivModExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTMulDivModExpressionAccess().getGroup()); }
		(rule__STMulDivModExpression__Group__0)
		{ after(grammarAccess.getSTMulDivModExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTPowerExpression
entryRuleSTPowerExpression
:
{ before(grammarAccess.getSTPowerExpressionRule()); }
	 ruleSTPowerExpression
{ after(grammarAccess.getSTPowerExpressionRule()); } 
	 EOF 
;

// Rule STPowerExpression
ruleSTPowerExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTPowerExpressionAccess().getGroup()); }
		(rule__STPowerExpression__Group__0)
		{ after(grammarAccess.getSTPowerExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTUnaryExpression
entryRuleSTUnaryExpression
:
{ before(grammarAccess.getSTUnaryExpressionRule()); }
	 ruleSTUnaryExpression
{ after(grammarAccess.getSTUnaryExpressionRule()); } 
	 EOF 
;

// Rule STUnaryExpression
ruleSTUnaryExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTUnaryExpressionAccess().getAlternatives()); }
		(rule__STUnaryExpression__Alternatives)
		{ after(grammarAccess.getSTUnaryExpressionAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAccessExpression
entryRuleSTAccessExpression
:
{ before(grammarAccess.getSTAccessExpressionRule()); }
	 ruleSTAccessExpression
{ after(grammarAccess.getSTAccessExpressionRule()); } 
	 EOF 
;

// Rule STAccessExpression
ruleSTAccessExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAccessExpressionAccess().getGroup()); }
		(rule__STAccessExpression__Group__0)
		{ after(grammarAccess.getSTAccessExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTPrimaryExpression
entryRuleSTPrimaryExpression
:
{ before(grammarAccess.getSTPrimaryExpressionRule()); }
	 ruleSTPrimaryExpression
{ after(grammarAccess.getSTPrimaryExpressionRule()); } 
	 EOF 
;

// Rule STPrimaryExpression
ruleSTPrimaryExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTPrimaryExpressionAccess().getAlternatives()); }
		(rule__STPrimaryExpression__Alternatives)
		{ after(grammarAccess.getSTPrimaryExpressionAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTFeatureExpression
entryRuleSTFeatureExpression
:
{ before(grammarAccess.getSTFeatureExpressionRule()); }
	 ruleSTFeatureExpression
{ after(grammarAccess.getSTFeatureExpressionRule()); } 
	 EOF 
;

// Rule STFeatureExpression
ruleSTFeatureExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTFeatureExpressionAccess().getGroup()); }
		(rule__STFeatureExpression__Group__0)
		{ after(grammarAccess.getSTFeatureExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTFeatureName
entryRuleSTFeatureName
:
{ before(grammarAccess.getSTFeatureNameRule()); }
	 ruleSTFeatureName
{ after(grammarAccess.getSTFeatureNameRule()); } 
	 EOF 
;

// Rule STFeatureName
ruleSTFeatureName 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTFeatureNameAccess().getAlternatives()); }
		(rule__STFeatureName__Alternatives)
		{ after(grammarAccess.getSTFeatureNameAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTBuiltinFeatureExpression
entryRuleSTBuiltinFeatureExpression
:
{ before(grammarAccess.getSTBuiltinFeatureExpressionRule()); }
	 ruleSTBuiltinFeatureExpression
{ after(grammarAccess.getSTBuiltinFeatureExpressionRule()); } 
	 EOF 
;

// Rule STBuiltinFeatureExpression
ruleSTBuiltinFeatureExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup()); }
		(rule__STBuiltinFeatureExpression__Group__0)
		{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTMultibitPartialExpression
entryRuleSTMultibitPartialExpression
:
{ before(grammarAccess.getSTMultibitPartialExpressionRule()); }
	 ruleSTMultibitPartialExpression
{ after(grammarAccess.getSTMultibitPartialExpressionRule()); } 
	 EOF 
;

// Rule STMultibitPartialExpression
ruleSTMultibitPartialExpression 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getGroup()); }
		(rule__STMultibitPartialExpression__Group__0)
		{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTLiteralExpressions
entryRuleSTLiteralExpressions
:
{ before(grammarAccess.getSTLiteralExpressionsRule()); }
	 ruleSTLiteralExpressions
{ after(grammarAccess.getSTLiteralExpressionsRule()); } 
	 EOF 
;

// Rule STLiteralExpressions
ruleSTLiteralExpressions 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTLiteralExpressionsAccess().getAlternatives()); }
		(rule__STLiteralExpressions__Alternatives)
		{ after(grammarAccess.getSTLiteralExpressionsAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTSignedLiteralExpressions
entryRuleSTSignedLiteralExpressions
:
{ before(grammarAccess.getSTSignedLiteralExpressionsRule()); }
	 ruleSTSignedLiteralExpressions
{ after(grammarAccess.getSTSignedLiteralExpressionsRule()); } 
	 EOF 
;

// Rule STSignedLiteralExpressions
ruleSTSignedLiteralExpressions 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTSignedLiteralExpressionsAccess().getSTSignedNumericLiteralParserRuleCall()); }
		ruleSTSignedNumericLiteral
		{ after(grammarAccess.getSTSignedLiteralExpressionsAccess().getSTSignedNumericLiteralParserRuleCall()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTNumericLiteralType
entryRuleSTNumericLiteralType
:
{ before(grammarAccess.getSTNumericLiteralTypeRule()); }
	 ruleSTNumericLiteralType
{ after(grammarAccess.getSTNumericLiteralTypeRule()); } 
	 EOF 
;

// Rule STNumericLiteralType
ruleSTNumericLiteralType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTNumericLiteralTypeAccess().getAlternatives()); }
		(rule__STNumericLiteralType__Alternatives)
		{ after(grammarAccess.getSTNumericLiteralTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTNumericLiteral
entryRuleSTNumericLiteral
:
{ before(grammarAccess.getSTNumericLiteralRule()); }
	 ruleSTNumericLiteral
{ after(grammarAccess.getSTNumericLiteralRule()); } 
	 EOF 
;

// Rule STNumericLiteral
ruleSTNumericLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTNumericLiteralAccess().getAlternatives()); }
		(rule__STNumericLiteral__Alternatives)
		{ after(grammarAccess.getSTNumericLiteralAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTSignedNumericLiteral
entryRuleSTSignedNumericLiteral
:
{ before(grammarAccess.getSTSignedNumericLiteralRule()); }
	 ruleSTSignedNumericLiteral
{ after(grammarAccess.getSTSignedNumericLiteralRule()); } 
	 EOF 
;

// Rule STSignedNumericLiteral
ruleSTSignedNumericLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTSignedNumericLiteralAccess().getValueAssignment()); }
		(rule__STSignedNumericLiteral__ValueAssignment)
		{ after(grammarAccess.getSTSignedNumericLiteralAccess().getValueAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTDateLiteralType
entryRuleSTDateLiteralType
:
{ before(grammarAccess.getSTDateLiteralTypeRule()); }
	 ruleSTDateLiteralType
{ after(grammarAccess.getSTDateLiteralTypeRule()); } 
	 EOF 
;

// Rule STDateLiteralType
ruleSTDateLiteralType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTDateLiteralTypeAccess().getAlternatives()); }
		(rule__STDateLiteralType__Alternatives)
		{ after(grammarAccess.getSTDateLiteralTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTDateLiteral
entryRuleSTDateLiteral
:
{ before(grammarAccess.getSTDateLiteralRule()); }
	 ruleSTDateLiteral
{ after(grammarAccess.getSTDateLiteralRule()); } 
	 EOF 
;

// Rule STDateLiteral
ruleSTDateLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTDateLiteralAccess().getGroup()); }
		(rule__STDateLiteral__Group__0)
		{ after(grammarAccess.getSTDateLiteralAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTTimeLiteralType
entryRuleSTTimeLiteralType
:
{ before(grammarAccess.getSTTimeLiteralTypeRule()); }
	 ruleSTTimeLiteralType
{ after(grammarAccess.getSTTimeLiteralTypeRule()); } 
	 EOF 
;

// Rule STTimeLiteralType
ruleSTTimeLiteralType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTTimeLiteralTypeAccess().getAlternatives()); }
		(rule__STTimeLiteralType__Alternatives)
		{ after(grammarAccess.getSTTimeLiteralTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTTimeLiteral
entryRuleSTTimeLiteral
:
{ before(grammarAccess.getSTTimeLiteralRule()); }
	 ruleSTTimeLiteral
{ after(grammarAccess.getSTTimeLiteralRule()); } 
	 EOF 
;

// Rule STTimeLiteral
ruleSTTimeLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTTimeLiteralAccess().getGroup()); }
		(rule__STTimeLiteral__Group__0)
		{ after(grammarAccess.getSTTimeLiteralAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTTimeOfDayLiteral
entryRuleSTTimeOfDayLiteral
:
{ before(grammarAccess.getSTTimeOfDayLiteralRule()); }
	 ruleSTTimeOfDayLiteral
{ after(grammarAccess.getSTTimeOfDayLiteralRule()); } 
	 EOF 
;

// Rule STTimeOfDayLiteral
ruleSTTimeOfDayLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTTimeOfDayLiteralAccess().getGroup()); }
		(rule__STTimeOfDayLiteral__Group__0)
		{ after(grammarAccess.getSTTimeOfDayLiteralAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTDateAndTimeLiteral
entryRuleSTDateAndTimeLiteral
:
{ before(grammarAccess.getSTDateAndTimeLiteralRule()); }
	 ruleSTDateAndTimeLiteral
{ after(grammarAccess.getSTDateAndTimeLiteralRule()); } 
	 EOF 
;

// Rule STDateAndTimeLiteral
ruleSTDateAndTimeLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTDateAndTimeLiteralAccess().getGroup()); }
		(rule__STDateAndTimeLiteral__Group__0)
		{ after(grammarAccess.getSTDateAndTimeLiteralAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTStringLiteral
entryRuleSTStringLiteral
:
{ before(grammarAccess.getSTStringLiteralRule()); }
	 ruleSTStringLiteral
{ after(grammarAccess.getSTStringLiteralRule()); } 
	 EOF 
;

// Rule STStringLiteral
ruleSTStringLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTStringLiteralAccess().getGroup()); }
		(rule__STStringLiteral__Group__0)
		{ after(grammarAccess.getSTStringLiteralAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTEnumLiteral
entryRuleSTEnumLiteral
:
{ before(grammarAccess.getSTEnumLiteralRule()); }
	 ruleSTEnumLiteral
{ after(grammarAccess.getSTEnumLiteralRule()); } 
	 EOF 
;

// Rule STEnumLiteral
ruleSTEnumLiteral 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTEnumLiteralAccess().getValueAssignment()); }
		(rule__STEnumLiteral__ValueAssignment)
		{ after(grammarAccess.getSTEnumLiteralAccess().getValueAssignment()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleEnumValue
entryRuleEnumValue
:
{ before(grammarAccess.getEnumValueRule()); }
	 ruleEnumValue
{ after(grammarAccess.getEnumValueRule()); } 
	 EOF 
;

// Rule EnumValue
ruleEnumValue 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getEnumValueAccess().getGroup()); }
		(rule__EnumValue__Group__0)
		{ after(grammarAccess.getEnumValueAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAnyType
entryRuleSTAnyType
:
{ before(grammarAccess.getSTAnyTypeRule()); }
	 ruleSTAnyType
{ after(grammarAccess.getSTAnyTypeRule()); } 
	 EOF 
;

// Rule STAnyType
ruleSTAnyType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAnyTypeAccess().getAlternatives()); }
		(rule__STAnyType__Alternatives)
		{ after(grammarAccess.getSTAnyTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAnyBuiltinType
entryRuleSTAnyBuiltinType
:
{ before(grammarAccess.getSTAnyBuiltinTypeRule()); }
	 ruleSTAnyBuiltinType
{ after(grammarAccess.getSTAnyBuiltinTypeRule()); } 
	 EOF 
;

// Rule STAnyBuiltinType
ruleSTAnyBuiltinType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAnyBuiltinTypeAccess().getAlternatives()); }
		(rule__STAnyBuiltinType__Alternatives)
		{ after(grammarAccess.getSTAnyBuiltinTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAnyBitType
entryRuleSTAnyBitType
:
{ before(grammarAccess.getSTAnyBitTypeRule()); }
	 ruleSTAnyBitType
{ after(grammarAccess.getSTAnyBitTypeRule()); } 
	 EOF 
;

// Rule STAnyBitType
ruleSTAnyBitType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAnyBitTypeAccess().getAlternatives()); }
		(rule__STAnyBitType__Alternatives)
		{ after(grammarAccess.getSTAnyBitTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAnyNumType
entryRuleSTAnyNumType
:
{ before(grammarAccess.getSTAnyNumTypeRule()); }
	 ruleSTAnyNumType
{ after(grammarAccess.getSTAnyNumTypeRule()); } 
	 EOF 
;

// Rule STAnyNumType
ruleSTAnyNumType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getAlternatives()); }
		(rule__STAnyNumType__Alternatives)
		{ after(grammarAccess.getSTAnyNumTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAnyDurationType
entryRuleSTAnyDurationType
:
{ before(grammarAccess.getSTAnyDurationTypeRule()); }
	 ruleSTAnyDurationType
{ after(grammarAccess.getSTAnyDurationTypeRule()); } 
	 EOF 
;

// Rule STAnyDurationType
ruleSTAnyDurationType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAnyDurationTypeAccess().getAlternatives()); }
		(rule__STAnyDurationType__Alternatives)
		{ after(grammarAccess.getSTAnyDurationTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAnyDateType
entryRuleSTAnyDateType
:
{ before(grammarAccess.getSTAnyDateTypeRule()); }
	 ruleSTAnyDateType
{ after(grammarAccess.getSTAnyDateTypeRule()); } 
	 EOF 
;

// Rule STAnyDateType
ruleSTAnyDateType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAnyDateTypeAccess().getAlternatives()); }
		(rule__STAnyDateType__Alternatives)
		{ after(grammarAccess.getSTAnyDateTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTDateType
entryRuleSTDateType
:
{ before(grammarAccess.getSTDateTypeRule()); }
	 ruleSTDateType
{ after(grammarAccess.getSTDateTypeRule()); } 
	 EOF 
;

// Rule STDateType
ruleSTDateType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTDateTypeAccess().getAlternatives()); }
		(rule__STDateType__Alternatives)
		{ after(grammarAccess.getSTDateTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTTimeOfDayType
entryRuleSTTimeOfDayType
:
{ before(grammarAccess.getSTTimeOfDayTypeRule()); }
	 ruleSTTimeOfDayType
{ after(grammarAccess.getSTTimeOfDayTypeRule()); } 
	 EOF 
;

// Rule STTimeOfDayType
ruleSTTimeOfDayType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTTimeOfDayTypeAccess().getAlternatives()); }
		(rule__STTimeOfDayType__Alternatives)
		{ after(grammarAccess.getSTTimeOfDayTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTDateAndTimeType
entryRuleSTDateAndTimeType
:
{ before(grammarAccess.getSTDateAndTimeTypeRule()); }
	 ruleSTDateAndTimeType
{ after(grammarAccess.getSTDateAndTimeTypeRule()); } 
	 EOF 
;

// Rule STDateAndTimeType
ruleSTDateAndTimeType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTDateAndTimeTypeAccess().getAlternatives()); }
		(rule__STDateAndTimeType__Alternatives)
		{ after(grammarAccess.getSTDateAndTimeTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSTAnyCharsType
entryRuleSTAnyCharsType
:
{ before(grammarAccess.getSTAnyCharsTypeRule()); }
	 ruleSTAnyCharsType
{ after(grammarAccess.getSTAnyCharsTypeRule()); } 
	 EOF 
;

// Rule STAnyCharsType
ruleSTAnyCharsType 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSTAnyCharsTypeAccess().getAlternatives()); }
		(rule__STAnyCharsType__Alternatives)
		{ after(grammarAccess.getSTAnyCharsTypeAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleQualifiedName
entryRuleQualifiedName
:
{ before(grammarAccess.getQualifiedNameRule()); }
	 ruleQualifiedName
{ after(grammarAccess.getQualifiedNameRule()); } 
	 EOF 
;

// Rule QualifiedName
ruleQualifiedName 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getQualifiedNameAccess().getGroup()); }
		(rule__QualifiedName__Group__0)
		{ after(grammarAccess.getQualifiedNameAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleQualifiedNameWithWildcard
entryRuleQualifiedNameWithWildcard
:
{ before(grammarAccess.getQualifiedNameWithWildcardRule()); }
	 ruleQualifiedNameWithWildcard
{ after(grammarAccess.getQualifiedNameWithWildcardRule()); } 
	 EOF 
;

// Rule QualifiedNameWithWildcard
ruleQualifiedNameWithWildcard 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup()); }
		(rule__QualifiedNameWithWildcard__Group__0)
		{ after(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleNumeric
entryRuleNumeric
:
{ before(grammarAccess.getNumericRule()); }
	 ruleNumeric
{ after(grammarAccess.getNumericRule()); } 
	 EOF 
;

// Rule Numeric
ruleNumeric 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getNumericAccess().getAlternatives()); }
		(rule__Numeric__Alternatives)
		{ after(grammarAccess.getNumericAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleNumber
entryRuleNumber
@init { 
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}
:
{ before(grammarAccess.getNumberRule()); }
	 ruleNumber
{ after(grammarAccess.getNumberRule()); } 
	 EOF 
;
finally {
	myHiddenTokenState.restore();
}

// Rule Number
ruleNumber 
	@init {
		HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getNumberAccess().getGroup()); }
		(rule__Number__Group__0)
		{ after(grammarAccess.getNumberAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
	myHiddenTokenState.restore();
}

// Entry rule entryRuleSignedNumeric
entryRuleSignedNumeric
:
{ before(grammarAccess.getSignedNumericRule()); }
	 ruleSignedNumeric
{ after(grammarAccess.getSignedNumericRule()); } 
	 EOF 
;

// Rule SignedNumeric
ruleSignedNumeric 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSignedNumericAccess().getSignedNumberParserRuleCall()); }
		ruleSignedNumber
		{ after(grammarAccess.getSignedNumericAccess().getSignedNumberParserRuleCall()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleSignedNumber
entryRuleSignedNumber
@init { 
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}
:
{ before(grammarAccess.getSignedNumberRule()); }
	 ruleSignedNumber
{ after(grammarAccess.getSignedNumberRule()); } 
	 EOF 
;
finally {
	myHiddenTokenState.restore();
}

// Rule SignedNumber
ruleSignedNumber 
	@init {
		HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getSignedNumberAccess().getGroup()); }
		(rule__SignedNumber__Group__0)
		{ after(grammarAccess.getSignedNumberAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
	myHiddenTokenState.restore();
}

// Entry rule entryRuleTime
entryRuleTime
@init { 
	HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
}
:
{ before(grammarAccess.getTimeRule()); }
	 ruleTime
{ after(grammarAccess.getTimeRule()); } 
	 EOF 
;
finally {
	myHiddenTokenState.restore();
}

// Rule Time
ruleTime 
	@init {
		HiddenTokens myHiddenTokenState = ((XtextTokenStream)input).setHiddenTokens();
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTimeAccess().getGroup()); }
		(rule__Time__Group__0)
		{ after(grammarAccess.getTimeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
	myHiddenTokenState.restore();
}

// Entry rule entryRuleDate
entryRuleDate
:
{ before(grammarAccess.getDateRule()); }
	 ruleDate
{ after(grammarAccess.getDateRule()); } 
	 EOF 
;

// Rule Date
ruleDate 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getDateAccess().getGroup()); }
		(rule__Date__Group__0)
		{ after(grammarAccess.getDateAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleDateAndTime
entryRuleDateAndTime
:
{ before(grammarAccess.getDateAndTimeRule()); }
	 ruleDateAndTime
{ after(grammarAccess.getDateAndTimeRule()); } 
	 EOF 
;

// Rule DateAndTime
ruleDateAndTime 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getDateAndTimeAccess().getGroup()); }
		(rule__DateAndTime__Group__0)
		{ after(grammarAccess.getDateAndTimeAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Entry rule entryRuleTimeOfDay
entryRuleTimeOfDay
:
{ before(grammarAccess.getTimeOfDayRule()); }
	 ruleTimeOfDay
{ after(grammarAccess.getTimeOfDayRule()); } 
	 EOF 
;

// Rule TimeOfDay
ruleTimeOfDay 
	@init {
		int stackSize = keepStackSize();
	}
	:
	(
		{ before(grammarAccess.getTimeOfDayAccess().getGroup()); }
		(rule__TimeOfDay__Group__0)
		{ after(grammarAccess.getTimeOfDayAccess().getGroup()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule SubrangeOperator
ruleSubrangeOperator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSubrangeOperatorAccess().getRangeEnumLiteralDeclaration()); }
		(FullStopFullStop)
		{ after(grammarAccess.getSubrangeOperatorAccess().getRangeEnumLiteralDeclaration()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule OrOperator
ruleOrOperator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getOrOperatorAccess().getOREnumLiteralDeclaration()); }
		(OR)
		{ after(grammarAccess.getOrOperatorAccess().getOREnumLiteralDeclaration()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule XorOperator
ruleXorOperator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getXorOperatorAccess().getXOREnumLiteralDeclaration()); }
		(XOR)
		{ after(grammarAccess.getXorOperatorAccess().getXOREnumLiteralDeclaration()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule AndOperator
ruleAndOperator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAndOperatorAccess().getAlternatives()); }
		(rule__AndOperator__Alternatives)
		{ after(grammarAccess.getAndOperatorAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule EqualityOperator
ruleEqualityOperator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEqualityOperatorAccess().getAlternatives()); }
		(rule__EqualityOperator__Alternatives)
		{ after(grammarAccess.getEqualityOperatorAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule CompareOperator
ruleCompareOperator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompareOperatorAccess().getAlternatives()); }
		(rule__CompareOperator__Alternatives)
		{ after(grammarAccess.getCompareOperatorAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule AddSubOperator
ruleAddSubOperator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAddSubOperatorAccess().getAlternatives()); }
		(rule__AddSubOperator__Alternatives)
		{ after(grammarAccess.getAddSubOperatorAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule MulDivModOperator
ruleMulDivModOperator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMulDivModOperatorAccess().getAlternatives()); }
		(rule__MulDivModOperator__Alternatives)
		{ after(grammarAccess.getMulDivModOperatorAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule PowerOperator
rulePowerOperator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getPowerOperatorAccess().getPOWEREnumLiteralDeclaration()); }
		(AsteriskAsterisk)
		{ after(grammarAccess.getPowerOperatorAccess().getPOWEREnumLiteralDeclaration()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule UnaryOperator
ruleUnaryOperator
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getUnaryOperatorAccess().getAlternatives()); }
		(rule__UnaryOperator__Alternatives)
		{ after(grammarAccess.getUnaryOperatorAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule STBuiltinFeature
ruleSTBuiltinFeature
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTBuiltinFeatureAccess().getTHISEnumLiteralDeclaration()); }
		(THIS)
		{ after(grammarAccess.getSTBuiltinFeatureAccess().getTHISEnumLiteralDeclaration()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

// Rule STMultiBitAccessSpecifier
ruleSTMultiBitAccessSpecifier
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTMultiBitAccessSpecifierAccess().getAlternatives()); }
		(rule__STMultiBitAccessSpecifier__Alternatives)
		{ after(grammarAccess.getSTMultiBitAccessSpecifierAccess().getAlternatives()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Alternatives_4_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_0()); }
		(rule__STVarDeclaration__Group_4_1_0__0)
		{ after(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_1()); }
		(rule__STVarDeclaration__Group_4_1_1__0)
		{ after(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Alternatives_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_0()); }
		(rule__STTypeDeclaration__Group_1_1_0__0)
		{ after(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_1()); }
		(rule__STTypeDeclaration__Group_1_1_1__0)
		{ after(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STInitializerExpression__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTInitializerExpressionAccess().getSTElementaryInitializerExpressionParserRuleCall_0()); }
		ruleSTElementaryInitializerExpression
		{ after(grammarAccess.getSTInitializerExpressionAccess().getSTElementaryInitializerExpressionParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTInitializerExpressionAccess().getSTArrayInitializerExpressionParserRuleCall_1()); }
		ruleSTArrayInitializerExpression
		{ after(grammarAccess.getSTInitializerExpressionAccess().getSTArrayInitializerExpressionParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTInitializerExpressionAccess().getSTStructInitializerExpressionParserRuleCall_2()); }
		ruleSTStructInitializerExpression
		{ after(grammarAccess.getSTInitializerExpressionAccess().getSTStructInitializerExpressionParserRuleCall_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitElement__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTArrayInitElementAccess().getSTSingleArrayInitElementParserRuleCall_0()); }
		ruleSTSingleArrayInitElement
		{ after(grammarAccess.getSTArrayInitElementAccess().getSTSingleArrayInitElementParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTArrayInitElementAccess().getSTRepeatArrayInitElementParserRuleCall_1()); }
		ruleSTRepeatArrayInitElement
		{ after(grammarAccess.getSTArrayInitElementAccess().getSTRepeatArrayInitElementParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTStatementAccess().getGroup_0()); }
		(rule__STStatement__Group_0__0)
		{ after(grammarAccess.getSTStatementAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTStatementAccess().getGroup_1()); }
		(rule__STStatement__Group_1__0)
		{ after(grammarAccess.getSTStatementAccess().getGroup_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Alternatives_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTStatementAccess().getSTIfStatementParserRuleCall_0_0_0()); }
		ruleSTIfStatement
		{ after(grammarAccess.getSTStatementAccess().getSTIfStatementParserRuleCall_0_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTStatementAccess().getSTCaseStatementParserRuleCall_0_0_1()); }
		ruleSTCaseStatement
		{ after(grammarAccess.getSTStatementAccess().getSTCaseStatementParserRuleCall_0_0_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTStatementAccess().getSTForStatementParserRuleCall_0_0_2()); }
		ruleSTForStatement
		{ after(grammarAccess.getSTStatementAccess().getSTForStatementParserRuleCall_0_0_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTStatementAccess().getSTWhileStatementParserRuleCall_0_0_3()); }
		ruleSTWhileStatement
		{ after(grammarAccess.getSTStatementAccess().getSTWhileStatementParserRuleCall_0_0_3()); }
	)
	|
	(
		{ before(grammarAccess.getSTStatementAccess().getSTRepeatStatementParserRuleCall_0_0_4()); }
		ruleSTRepeatStatement
		{ after(grammarAccess.getSTStatementAccess().getSTRepeatStatementParserRuleCall_0_0_4()); }
	)
	|
	(
		{ before(grammarAccess.getSTStatementAccess().getSTAssignmentParserRuleCall_0_0_5()); }
		ruleSTAssignment
		{ after(grammarAccess.getSTStatementAccess().getSTAssignmentParserRuleCall_0_0_5()); }
	)
	|
	(
		{ before(grammarAccess.getSTStatementAccess().getGroup_0_0_6()); }
		(rule__STStatement__Group_0_0_6__0)
		{ after(grammarAccess.getSTStatementAccess().getGroup_0_0_6()); }
	)
	|
	(
		{ before(grammarAccess.getSTStatementAccess().getGroup_0_0_7()); }
		(rule__STStatement__Group_0_0_7__0)
		{ after(grammarAccess.getSTStatementAccess().getGroup_0_0_7()); }
	)
	|
	(
		{ before(grammarAccess.getSTStatementAccess().getGroup_0_0_8()); }
		(rule__STStatement__Group_0_0_8__0)
		{ after(grammarAccess.getSTStatementAccess().getGroup_0_0_8()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallArgument__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCallArgumentAccess().getSTCallUnnamedArgumentParserRuleCall_0()); }
		ruleSTCallUnnamedArgument
		{ after(grammarAccess.getSTCallArgumentAccess().getSTCallUnnamedArgumentParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTCallArgumentAccess().getSTCallNamedInputArgumentParserRuleCall_1()); }
		ruleSTCallNamedInputArgument
		{ after(grammarAccess.getSTCallArgumentAccess().getSTCallNamedInputArgumentParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTCallArgumentAccess().getSTCallNamedOutputArgumentParserRuleCall_2()); }
		ruleSTCallNamedOutputArgument
		{ after(grammarAccess.getSTCallArgumentAccess().getSTCallNamedOutputArgumentParserRuleCall_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STUnaryExpression__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTUnaryExpressionAccess().getSTAccessExpressionParserRuleCall_0()); }
		ruleSTAccessExpression
		{ after(grammarAccess.getSTUnaryExpressionAccess().getSTAccessExpressionParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTUnaryExpressionAccess().getSTLiteralExpressionsParserRuleCall_1()); }
		ruleSTLiteralExpressions
		{ after(grammarAccess.getSTUnaryExpressionAccess().getSTLiteralExpressionsParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTUnaryExpressionAccess().getSTSignedLiteralExpressionsParserRuleCall_2()); }
		(ruleSTSignedLiteralExpressions)
		{ after(grammarAccess.getSTUnaryExpressionAccess().getSTSignedLiteralExpressionsParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTUnaryExpressionAccess().getGroup_3()); }
		(rule__STUnaryExpression__Group_3__0)
		{ after(grammarAccess.getSTUnaryExpressionAccess().getGroup_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Alternatives_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAccessExpressionAccess().getGroup_1_0()); }
		(rule__STAccessExpression__Group_1_0__0)
		{ after(grammarAccess.getSTAccessExpressionAccess().getGroup_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTAccessExpressionAccess().getGroup_1_1()); }
		(rule__STAccessExpression__Group_1_1__0)
		{ after(grammarAccess.getSTAccessExpressionAccess().getGroup_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__MemberAlternatives_1_0_2_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0()); }
		ruleSTFeatureExpression
		{ after(grammarAccess.getSTAccessExpressionAccess().getMemberSTFeatureExpressionParserRuleCall_1_0_2_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1()); }
		ruleSTMultibitPartialExpression
		{ after(grammarAccess.getSTAccessExpressionAccess().getMemberSTMultibitPartialExpressionParserRuleCall_1_0_2_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPrimaryExpression__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTPrimaryExpressionAccess().getGroup_0()); }
		(rule__STPrimaryExpression__Group_0__0)
		{ after(grammarAccess.getSTPrimaryExpressionAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTPrimaryExpressionAccess().getSTFeatureExpressionParserRuleCall_1()); }
		ruleSTFeatureExpression
		{ after(grammarAccess.getSTPrimaryExpressionAccess().getSTFeatureExpressionParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTPrimaryExpressionAccess().getSTBuiltinFeatureExpressionParserRuleCall_2()); }
		ruleSTBuiltinFeatureExpression
		{ after(grammarAccess.getSTPrimaryExpressionAccess().getSTBuiltinFeatureExpressionParserRuleCall_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureName__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTFeatureNameAccess().getQualifiedNameParserRuleCall_0()); }
		ruleQualifiedName
		{ after(grammarAccess.getSTFeatureNameAccess().getQualifiedNameParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTFeatureNameAccess().getLTKeyword_1()); }
		LT
		{ after(grammarAccess.getSTFeatureNameAccess().getLTKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTFeatureNameAccess().getANDKeyword_2()); }
		AND
		{ after(grammarAccess.getSTFeatureNameAccess().getANDKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTFeatureNameAccess().getORKeyword_3()); }
		OR
		{ after(grammarAccess.getSTFeatureNameAccess().getORKeyword_3()); }
	)
	|
	(
		{ before(grammarAccess.getSTFeatureNameAccess().getXORKeyword_4()); }
		XOR
		{ after(grammarAccess.getSTFeatureNameAccess().getXORKeyword_4()); }
	)
	|
	(
		{ before(grammarAccess.getSTFeatureNameAccess().getMODKeyword_5()); }
		MOD
		{ after(grammarAccess.getSTFeatureNameAccess().getMODKeyword_5()); }
	)
	|
	(
		{ before(grammarAccess.getSTFeatureNameAccess().getDKeyword_6()); }
		D
		{ after(grammarAccess.getSTFeatureNameAccess().getDKeyword_6()); }
	)
	|
	(
		{ before(grammarAccess.getSTFeatureNameAccess().getDTKeyword_7()); }
		DT
		{ after(grammarAccess.getSTFeatureNameAccess().getDTKeyword_7()); }
	)
	|
	(
		{ before(grammarAccess.getSTFeatureNameAccess().getLDKeyword_8()); }
		LD
		{ after(grammarAccess.getSTFeatureNameAccess().getLDKeyword_8()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Alternatives_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getIndexAssignment_2_0()); }
		(rule__STMultibitPartialExpression__IndexAssignment_2_0)
		{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getIndexAssignment_2_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getGroup_2_1()); }
		(rule__STMultibitPartialExpression__Group_2_1__0)
		{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getGroup_2_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STLiteralExpressions__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTLiteralExpressionsAccess().getSTNumericLiteralParserRuleCall_0()); }
		ruleSTNumericLiteral
		{ after(grammarAccess.getSTLiteralExpressionsAccess().getSTNumericLiteralParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTLiteralExpressionsAccess().getSTDateLiteralParserRuleCall_1()); }
		ruleSTDateLiteral
		{ after(grammarAccess.getSTLiteralExpressionsAccess().getSTDateLiteralParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTLiteralExpressionsAccess().getSTTimeLiteralParserRuleCall_2()); }
		ruleSTTimeLiteral
		{ after(grammarAccess.getSTLiteralExpressionsAccess().getSTTimeLiteralParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTLiteralExpressionsAccess().getSTTimeOfDayLiteralParserRuleCall_3()); }
		ruleSTTimeOfDayLiteral
		{ after(grammarAccess.getSTLiteralExpressionsAccess().getSTTimeOfDayLiteralParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getSTLiteralExpressionsAccess().getSTDateAndTimeLiteralParserRuleCall_4()); }
		ruleSTDateAndTimeLiteral
		{ after(grammarAccess.getSTLiteralExpressionsAccess().getSTDateAndTimeLiteralParserRuleCall_4()); }
	)
	|
	(
		{ before(grammarAccess.getSTLiteralExpressionsAccess().getSTStringLiteralParserRuleCall_5()); }
		ruleSTStringLiteral
		{ after(grammarAccess.getSTLiteralExpressionsAccess().getSTStringLiteralParserRuleCall_5()); }
	)
	|
	(
		{ before(grammarAccess.getSTLiteralExpressionsAccess().getSTEnumLiteralParserRuleCall_6()); }
		ruleSTEnumLiteral
		{ after(grammarAccess.getSTLiteralExpressionsAccess().getSTEnumLiteralParserRuleCall_6()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteralType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTNumericLiteralTypeAccess().getSTAnyBitTypeParserRuleCall_0()); }
		ruleSTAnyBitType
		{ after(grammarAccess.getSTNumericLiteralTypeAccess().getSTAnyBitTypeParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTNumericLiteralTypeAccess().getSTAnyNumTypeParserRuleCall_1()); }
		ruleSTAnyNumType
		{ after(grammarAccess.getSTNumericLiteralTypeAccess().getSTAnyNumTypeParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTNumericLiteralAccess().getGroup_0()); }
		(rule__STNumericLiteral__Group_0__0)
		{ after(grammarAccess.getSTNumericLiteralAccess().getGroup_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTNumericLiteralAccess().getGroup_1()); }
		(rule__STNumericLiteral__Group_1__0)
		{ after(grammarAccess.getSTNumericLiteralAccess().getGroup_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateLiteralType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTDateLiteralTypeAccess().getSTDateTypeParserRuleCall_0()); }
		ruleSTDateType
		{ after(grammarAccess.getSTDateLiteralTypeAccess().getSTDateTypeParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1()); }
		D
		{ after(grammarAccess.getSTDateLiteralTypeAccess().getDKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTDateLiteralTypeAccess().getLDKeyword_2()); }
		LD
		{ after(grammarAccess.getSTDateLiteralTypeAccess().getLDKeyword_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeLiteralType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTimeLiteralTypeAccess().getSTAnyDurationTypeParserRuleCall_0()); }
		ruleSTAnyDurationType
		{ after(grammarAccess.getSTTimeLiteralTypeAccess().getSTAnyDurationTypeParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1()); }
		T
		{ after(grammarAccess.getSTTimeLiteralTypeAccess().getTKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTTimeLiteralTypeAccess().getLTKeyword_2()); }
		LT
		{ after(grammarAccess.getSTTimeLiteralTypeAccess().getLTKeyword_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAnyType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAnyTypeAccess().getQualifiedNameParserRuleCall_0()); }
		ruleQualifiedName
		{ after(grammarAccess.getSTAnyTypeAccess().getQualifiedNameParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyTypeAccess().getSTAnyBuiltinTypeParserRuleCall_1()); }
		ruleSTAnyBuiltinType
		{ after(grammarAccess.getSTAnyTypeAccess().getSTAnyBuiltinTypeParserRuleCall_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAnyBuiltinType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyBitTypeParserRuleCall_0()); }
		ruleSTAnyBitType
		{ after(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyBitTypeParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyNumTypeParserRuleCall_1()); }
		ruleSTAnyNumType
		{ after(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyNumTypeParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyDurationTypeParserRuleCall_2()); }
		ruleSTAnyDurationType
		{ after(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyDurationTypeParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyDateTypeParserRuleCall_3()); }
		ruleSTAnyDateType
		{ after(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyDateTypeParserRuleCall_3()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyCharsTypeParserRuleCall_4()); }
		ruleSTAnyCharsType
		{ after(grammarAccess.getSTAnyBuiltinTypeAccess().getSTAnyCharsTypeParserRuleCall_4()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAnyBitType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0()); }
		BOOL
		{ after(grammarAccess.getSTAnyBitTypeAccess().getBOOLKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1()); }
		BYTE
		{ after(grammarAccess.getSTAnyBitTypeAccess().getBYTEKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2()); }
		WORD
		{ after(grammarAccess.getSTAnyBitTypeAccess().getWORDKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3()); }
		DWORD
		{ after(grammarAccess.getSTAnyBitTypeAccess().getDWORDKeyword_3()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyBitTypeAccess().getLWORDKeyword_4()); }
		LWORD
		{ after(grammarAccess.getSTAnyBitTypeAccess().getLWORDKeyword_4()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAnyNumType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0()); }
		SINT
		{ after(grammarAccess.getSTAnyNumTypeAccess().getSINTKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1()); }
		INT
		{ after(grammarAccess.getSTAnyNumTypeAccess().getINTKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2()); }
		DINT
		{ after(grammarAccess.getSTAnyNumTypeAccess().getDINTKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3()); }
		LINT
		{ after(grammarAccess.getSTAnyNumTypeAccess().getLINTKeyword_3()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4()); }
		USINT
		{ after(grammarAccess.getSTAnyNumTypeAccess().getUSINTKeyword_4()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5()); }
		UINT
		{ after(grammarAccess.getSTAnyNumTypeAccess().getUINTKeyword_5()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6()); }
		UDINT
		{ after(grammarAccess.getSTAnyNumTypeAccess().getUDINTKeyword_6()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7()); }
		ULINT
		{ after(grammarAccess.getSTAnyNumTypeAccess().getULINTKeyword_7()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8()); }
		REAL
		{ after(grammarAccess.getSTAnyNumTypeAccess().getREALKeyword_8()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyNumTypeAccess().getLREALKeyword_9()); }
		LREAL
		{ after(grammarAccess.getSTAnyNumTypeAccess().getLREALKeyword_9()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAnyDurationType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0()); }
		TIME
		{ after(grammarAccess.getSTAnyDurationTypeAccess().getTIMEKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyDurationTypeAccess().getLTIMEKeyword_1()); }
		LTIME
		{ after(grammarAccess.getSTAnyDurationTypeAccess().getLTIMEKeyword_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAnyDateType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAnyDateTypeAccess().getSTDateTypeParserRuleCall_0()); }
		ruleSTDateType
		{ after(grammarAccess.getSTAnyDateTypeAccess().getSTDateTypeParserRuleCall_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyDateTypeAccess().getSTTimeOfDayTypeParserRuleCall_1()); }
		ruleSTTimeOfDayType
		{ after(grammarAccess.getSTAnyDateTypeAccess().getSTTimeOfDayTypeParserRuleCall_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyDateTypeAccess().getSTDateAndTimeTypeParserRuleCall_2()); }
		ruleSTDateAndTimeType
		{ after(grammarAccess.getSTAnyDateTypeAccess().getSTDateAndTimeTypeParserRuleCall_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTDateTypeAccess().getDATEKeyword_0()); }
		DATE
		{ after(grammarAccess.getSTDateTypeAccess().getDATEKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTDateTypeAccess().getLDATEKeyword_1()); }
		LDATE
		{ after(grammarAccess.getSTDateTypeAccess().getLDATEKeyword_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeOfDayType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0()); }
		TIME_OF_DAY
		{ after(grammarAccess.getSTTimeOfDayTypeAccess().getTIME_OF_DAYKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1()); }
		LTIME_OF_DAY
		{ after(grammarAccess.getSTTimeOfDayTypeAccess().getLTIME_OF_DAYKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2()); }
		TOD
		{ after(grammarAccess.getSTTimeOfDayTypeAccess().getTODKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTTimeOfDayTypeAccess().getLTODKeyword_3()); }
		LTOD
		{ after(grammarAccess.getSTTimeOfDayTypeAccess().getLTODKeyword_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateAndTimeType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0()); }
		DATE_AND_TIME
		{ after(grammarAccess.getSTDateAndTimeTypeAccess().getDATE_AND_TIMEKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1()); }
		LDATE_AND_TIME
		{ after(grammarAccess.getSTDateAndTimeTypeAccess().getLDATE_AND_TIMEKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2()); }
		DT
		{ after(grammarAccess.getSTDateAndTimeTypeAccess().getDTKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTDateAndTimeTypeAccess().getLDTKeyword_3()); }
		LDT
		{ after(grammarAccess.getSTDateAndTimeTypeAccess().getLDTKeyword_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAnyCharsType__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0()); }
		STRING
		{ after(grammarAccess.getSTAnyCharsTypeAccess().getSTRINGKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1()); }
		WSTRING
		{ after(grammarAccess.getSTAnyCharsTypeAccess().getWSTRINGKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2()); }
		CHAR
		{ after(grammarAccess.getSTAnyCharsTypeAccess().getCHARKeyword_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTAnyCharsTypeAccess().getWCHARKeyword_3()); }
		WCHAR
		{ after(grammarAccess.getSTAnyCharsTypeAccess().getWCHARKeyword_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Numeric__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getNumericAccess().getTRUEKeyword_0()); }
		TRUE
		{ after(grammarAccess.getNumericAccess().getTRUEKeyword_0()); }
	)
	|
	(
		{ before(grammarAccess.getNumericAccess().getFALSEKeyword_1()); }
		FALSE
		{ after(grammarAccess.getNumericAccess().getFALSEKeyword_1()); }
	)
	|
	(
		{ before(grammarAccess.getNumericAccess().getNumberParserRuleCall_2()); }
		ruleNumber
		{ after(grammarAccess.getNumericAccess().getNumberParserRuleCall_2()); }
	)
	|
	(
		{ before(grammarAccess.getNumericAccess().getNON_DECIMALTerminalRuleCall_3()); }
		RULE_NON_DECIMAL
		{ after(grammarAccess.getNumericAccess().getNON_DECIMALTerminalRuleCall_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Number__Alternatives_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_1_0()); }
		RULE_INT
		{ after(grammarAccess.getNumberAccess().getINTTerminalRuleCall_1_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_1_1_1()); }
		RULE_DECIMAL
		{ after(grammarAccess.getNumberAccess().getDECIMALTerminalRuleCall_1_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SignedNumber__Alternatives_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSignedNumberAccess().getPlusSignKeyword_0_0()); }
		PlusSign
		{ after(grammarAccess.getSignedNumberAccess().getPlusSignKeyword_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getSignedNumberAccess().getHyphenMinusKeyword_0_1()); }
		HyphenMinus
		{ after(grammarAccess.getSignedNumberAccess().getHyphenMinusKeyword_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__SignedNumber__Alternatives_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSignedNumberAccess().getINTTerminalRuleCall_2_1_0()); }
		RULE_INT
		{ after(grammarAccess.getSignedNumberAccess().getINTTerminalRuleCall_2_1_0()); }
	)
	|
	(
		{ before(grammarAccess.getSignedNumberAccess().getDECIMALTerminalRuleCall_2_1_1()); }
		RULE_DECIMAL
		{ after(grammarAccess.getSignedNumberAccess().getDECIMALTerminalRuleCall_2_1_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__Time__Alternatives_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getTimeAccess().getPlusSignKeyword_0_0()); }
		PlusSign
		{ after(grammarAccess.getTimeAccess().getPlusSignKeyword_0_0()); }
	)
	|
	(
		{ before(grammarAccess.getTimeAccess().getHyphenMinusKeyword_0_1()); }
		HyphenMinus
		{ after(grammarAccess.getTimeAccess().getHyphenMinusKeyword_0_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__AndOperator__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAndOperatorAccess().getANDEnumLiteralDeclaration_0()); }
		(AND)
		{ after(grammarAccess.getAndOperatorAccess().getANDEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getAndOperatorAccess().getAMPERSANDEnumLiteralDeclaration_1()); }
		(Ampersand)
		{ after(grammarAccess.getAndOperatorAccess().getAMPERSANDEnumLiteralDeclaration_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__EqualityOperator__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getEqualityOperatorAccess().getEQEnumLiteralDeclaration_0()); }
		(EqualsSign)
		{ after(grammarAccess.getEqualityOperatorAccess().getEQEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getEqualityOperatorAccess().getNEEnumLiteralDeclaration_1()); }
		(LessThanSignGreaterThanSign)
		{ after(grammarAccess.getEqualityOperatorAccess().getNEEnumLiteralDeclaration_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__CompareOperator__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getCompareOperatorAccess().getLTEnumLiteralDeclaration_0()); }
		(LessThanSign)
		{ after(grammarAccess.getCompareOperatorAccess().getLTEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getCompareOperatorAccess().getLEEnumLiteralDeclaration_1()); }
		(LessThanSignEqualsSign)
		{ after(grammarAccess.getCompareOperatorAccess().getLEEnumLiteralDeclaration_1()); }
	)
	|
	(
		{ before(grammarAccess.getCompareOperatorAccess().getGTEnumLiteralDeclaration_2()); }
		(GreaterThanSign)
		{ after(grammarAccess.getCompareOperatorAccess().getGTEnumLiteralDeclaration_2()); }
	)
	|
	(
		{ before(grammarAccess.getCompareOperatorAccess().getGEEnumLiteralDeclaration_3()); }
		(GreaterThanSignEqualsSign)
		{ after(grammarAccess.getCompareOperatorAccess().getGEEnumLiteralDeclaration_3()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__AddSubOperator__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getAddSubOperatorAccess().getADDEnumLiteralDeclaration_0()); }
		(PlusSign)
		{ after(grammarAccess.getAddSubOperatorAccess().getADDEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getAddSubOperatorAccess().getSUBEnumLiteralDeclaration_1()); }
		(HyphenMinus)
		{ after(grammarAccess.getAddSubOperatorAccess().getSUBEnumLiteralDeclaration_1()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__MulDivModOperator__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getMulDivModOperatorAccess().getMULEnumLiteralDeclaration_0()); }
		(Asterisk)
		{ after(grammarAccess.getMulDivModOperatorAccess().getMULEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getMulDivModOperatorAccess().getDIVEnumLiteralDeclaration_1()); }
		(Solidus)
		{ after(grammarAccess.getMulDivModOperatorAccess().getDIVEnumLiteralDeclaration_1()); }
	)
	|
	(
		{ before(grammarAccess.getMulDivModOperatorAccess().getMODEnumLiteralDeclaration_2()); }
		(MOD)
		{ after(grammarAccess.getMulDivModOperatorAccess().getMODEnumLiteralDeclaration_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__UnaryOperator__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getUnaryOperatorAccess().getMINUSEnumLiteralDeclaration_0()); }
		(HyphenMinus)
		{ after(grammarAccess.getUnaryOperatorAccess().getMINUSEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getUnaryOperatorAccess().getPLUSEnumLiteralDeclaration_1()); }
		(PlusSign)
		{ after(grammarAccess.getUnaryOperatorAccess().getPLUSEnumLiteralDeclaration_1()); }
	)
	|
	(
		{ before(grammarAccess.getUnaryOperatorAccess().getNOTEnumLiteralDeclaration_2()); }
		(NOT)
		{ after(grammarAccess.getUnaryOperatorAccess().getNOTEnumLiteralDeclaration_2()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultiBitAccessSpecifier__Alternatives
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTMultiBitAccessSpecifierAccess().getLEnumLiteralDeclaration_0()); }
		(L)
		{ after(grammarAccess.getSTMultiBitAccessSpecifierAccess().getLEnumLiteralDeclaration_0()); }
	)
	|
	(
		{ before(grammarAccess.getSTMultiBitAccessSpecifierAccess().getDEnumLiteralDeclaration_1()); }
		(D_1)
		{ after(grammarAccess.getSTMultiBitAccessSpecifierAccess().getDEnumLiteralDeclaration_1()); }
	)
	|
	(
		{ before(grammarAccess.getSTMultiBitAccessSpecifierAccess().getWEnumLiteralDeclaration_2()); }
		(W)
		{ after(grammarAccess.getSTMultiBitAccessSpecifierAccess().getWEnumLiteralDeclaration_2()); }
	)
	|
	(
		{ before(grammarAccess.getSTMultiBitAccessSpecifierAccess().getBEnumLiteralDeclaration_3()); }
		(B)
		{ after(grammarAccess.getSTMultiBitAccessSpecifierAccess().getBEnumLiteralDeclaration_3()); }
	)
	|
	(
		{ before(grammarAccess.getSTMultiBitAccessSpecifierAccess().getXEnumLiteralDeclaration_4()); }
		(X)
		{ after(grammarAccess.getSTMultiBitAccessSpecifierAccess().getXEnumLiteralDeclaration_4()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCoreSource__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCoreSource__Group__0__Impl
	rule__STCoreSource__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STCoreSource__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCoreSourceAccess().getSTCoreSourceAction_0()); }
	()
	{ after(grammarAccess.getSTCoreSourceAccess().getSTCoreSourceAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCoreSource__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCoreSource__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STCoreSource__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCoreSourceAccess().getStatementsAssignment_1()); }
	(rule__STCoreSource__StatementsAssignment_1)*
	{ after(grammarAccess.getSTCoreSourceAccess().getStatementsAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STExpressionSource__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STExpressionSource__Group__0__Impl
	rule__STExpressionSource__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STExpressionSource__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTExpressionSourceAccess().getSTExpressionSourceAction_0()); }
	()
	{ after(grammarAccess.getSTExpressionSourceAccess().getSTExpressionSourceAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STExpressionSource__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STExpressionSource__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STExpressionSource__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTExpressionSourceAccess().getExpressionAssignment_1()); }
	(rule__STExpressionSource__ExpressionAssignment_1)?
	{ after(grammarAccess.getSTExpressionSourceAccess().getExpressionAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STInitializerExpressionSource__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STInitializerExpressionSource__Group__0__Impl
	rule__STInitializerExpressionSource__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STInitializerExpressionSource__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTInitializerExpressionSourceAccess().getSTInitializerExpressionSourceAction_0()); }
	()
	{ after(grammarAccess.getSTInitializerExpressionSourceAccess().getSTInitializerExpressionSourceAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STInitializerExpressionSource__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STInitializerExpressionSource__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STInitializerExpressionSource__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTInitializerExpressionSourceAccess().getInitializerExpressionAssignment_1()); }
	(rule__STInitializerExpressionSource__InitializerExpressionAssignment_1)?
	{ after(grammarAccess.getSTInitializerExpressionSourceAccess().getInitializerExpressionAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STVarDeclaration__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group__0__Impl
	rule__STVarDeclaration__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getSTVarDeclarationAction_0()); }
	()
	{ after(grammarAccess.getSTVarDeclarationAccess().getSTVarDeclarationAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group__1__Impl
	rule__STVarDeclaration__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getNameAssignment_1()); }
	(rule__STVarDeclaration__NameAssignment_1)
	{ after(grammarAccess.getSTVarDeclarationAccess().getNameAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group__2__Impl
	rule__STVarDeclaration__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getGroup_2()); }
	(rule__STVarDeclaration__Group_2__0)?
	{ after(grammarAccess.getSTVarDeclarationAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group__3__Impl
	rule__STVarDeclaration__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getColonKeyword_3()); }
	Colon
	{ after(grammarAccess.getSTVarDeclarationAccess().getColonKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group__4__Impl
	rule__STVarDeclaration__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getGroup_4()); }
	(rule__STVarDeclaration__Group_4__0)?
	{ after(grammarAccess.getSTVarDeclarationAccess().getGroup_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group__5__Impl
	rule__STVarDeclaration__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getTypeAssignment_5()); }
	(rule__STVarDeclaration__TypeAssignment_5)
	{ after(grammarAccess.getSTVarDeclarationAccess().getTypeAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group__6__Impl
	rule__STVarDeclaration__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getGroup_6()); }
	(rule__STVarDeclaration__Group_6__0)?
	{ after(grammarAccess.getSTVarDeclarationAccess().getGroup_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group__7__Impl
	rule__STVarDeclaration__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getGroup_7()); }
	(rule__STVarDeclaration__Group_7__0)?
	{ after(grammarAccess.getSTVarDeclarationAccess().getGroup_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group__8__Impl
	rule__STVarDeclaration__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getPragmaAssignment_8()); }
	(rule__STVarDeclaration__PragmaAssignment_8)?
	{ after(grammarAccess.getSTVarDeclarationAccess().getPragmaAssignment_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group__9__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getSemicolonKeyword_9()); }
	Semicolon
	{ after(grammarAccess.getSTVarDeclarationAccess().getSemicolonKeyword_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STVarDeclaration__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_2__0__Impl
	rule__STVarDeclaration__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getATKeyword_2_0()); }
	AT
	{ after(grammarAccess.getSTVarDeclarationAccess().getATKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getLocatedAtAssignment_2_1()); }
	(rule__STVarDeclaration__LocatedAtAssignment_2_1)
	{ after(grammarAccess.getSTVarDeclarationAccess().getLocatedAtAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STVarDeclaration__Group_4__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4__0__Impl
	rule__STVarDeclaration__Group_4__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getArrayAssignment_4_0()); }
	(rule__STVarDeclaration__ArrayAssignment_4_0)
	{ after(grammarAccess.getSTVarDeclarationAccess().getArrayAssignment_4_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4__1__Impl
	rule__STVarDeclaration__Group_4__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getAlternatives_4_1()); }
	(rule__STVarDeclaration__Alternatives_4_1)
	{ after(grammarAccess.getSTVarDeclarationAccess().getAlternatives_4_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getOFKeyword_4_2()); }
	OF
	{ after(grammarAccess.getSTVarDeclarationAccess().getOFKeyword_4_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STVarDeclaration__Group_4_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_0__0__Impl
	rule__STVarDeclaration__Group_4_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0()); }
	LeftSquareBracket
	{ after(grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_0__1__Impl
	rule__STVarDeclaration__Group_4_1_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getRangesAssignment_4_1_0_1()); }
	(rule__STVarDeclaration__RangesAssignment_4_1_0_1)
	{ after(grammarAccess.getSTVarDeclarationAccess().getRangesAssignment_4_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_0__2__Impl
	rule__STVarDeclaration__Group_4_1_0__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_0_2()); }
	(rule__STVarDeclaration__Group_4_1_0_2__0)*
	{ after(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_0__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_0__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_0__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3()); }
	RightSquareBracket
	{ after(grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_0_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STVarDeclaration__Group_4_1_0_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_0_2__0__Impl
	rule__STVarDeclaration__Group_4_1_0_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_0_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_0_2_0()); }
	Comma
	{ after(grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_0_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_0_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_0_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_0_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getRangesAssignment_4_1_0_2_1()); }
	(rule__STVarDeclaration__RangesAssignment_4_1_0_2_1)
	{ after(grammarAccess.getSTVarDeclarationAccess().getRangesAssignment_4_1_0_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STVarDeclaration__Group_4_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_1__0__Impl
	rule__STVarDeclaration__Group_4_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0()); }
	LeftSquareBracket
	{ after(grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_4_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_1__1__Impl
	rule__STVarDeclaration__Group_4_1_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getCountAssignment_4_1_1_1()); }
	(rule__STVarDeclaration__CountAssignment_4_1_1_1)
	{ after(grammarAccess.getSTVarDeclarationAccess().getCountAssignment_4_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_1__2__Impl
	rule__STVarDeclaration__Group_4_1_1__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_1_2()); }
	(rule__STVarDeclaration__Group_4_1_1_2__0)*
	{ after(grammarAccess.getSTVarDeclarationAccess().getGroup_4_1_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_1__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_1__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_1__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3()); }
	RightSquareBracket
	{ after(grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_4_1_1_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STVarDeclaration__Group_4_1_1_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_1_2__0__Impl
	rule__STVarDeclaration__Group_4_1_1_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_1_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_1_2_0()); }
	Comma
	{ after(grammarAccess.getSTVarDeclarationAccess().getCommaKeyword_4_1_1_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_1_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_4_1_1_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_4_1_1_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getCountAssignment_4_1_1_2_1()); }
	(rule__STVarDeclaration__CountAssignment_4_1_1_2_1)
	{ after(grammarAccess.getSTVarDeclarationAccess().getCountAssignment_4_1_1_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STVarDeclaration__Group_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_6__0__Impl
	rule__STVarDeclaration__Group_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_6_0()); }
	LeftSquareBracket
	{ after(grammarAccess.getSTVarDeclarationAccess().getLeftSquareBracketKeyword_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_6__1__Impl
	rule__STVarDeclaration__Group_6__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getMaxLengthAssignment_6_1()); }
	(rule__STVarDeclaration__MaxLengthAssignment_6_1)
	{ after(grammarAccess.getSTVarDeclarationAccess().getMaxLengthAssignment_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_6__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_6__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_6__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_6_2()); }
	RightSquareBracket
	{ after(grammarAccess.getSTVarDeclarationAccess().getRightSquareBracketKeyword_6_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STVarDeclaration__Group_7__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_7__0__Impl
	rule__STVarDeclaration__Group_7__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_7__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getColonEqualsSignKeyword_7_0()); }
	ColonEqualsSign
	{ after(grammarAccess.getSTVarDeclarationAccess().getColonEqualsSignKeyword_7_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_7__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STVarDeclaration__Group_7__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__Group_7__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTVarDeclarationAccess().getDefaultValueAssignment_7_1()); }
	(rule__STVarDeclaration__DefaultValueAssignment_7_1)
	{ after(grammarAccess.getSTVarDeclarationAccess().getDefaultValueAssignment_7_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STTypeDeclaration__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group__0__Impl
	rule__STTypeDeclaration__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getSTTypeDeclarationAction_0()); }
	()
	{ after(grammarAccess.getSTTypeDeclarationAccess().getSTTypeDeclarationAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group__1__Impl
	rule__STTypeDeclaration__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getGroup_1()); }
	(rule__STTypeDeclaration__Group_1__0)?
	{ after(grammarAccess.getSTTypeDeclarationAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group__2__Impl
	rule__STTypeDeclaration__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getTypeAssignment_2()); }
	(rule__STTypeDeclaration__TypeAssignment_2)
	{ after(grammarAccess.getSTTypeDeclarationAccess().getTypeAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getGroup_3()); }
	(rule__STTypeDeclaration__Group_3__0)?
	{ after(grammarAccess.getSTTypeDeclarationAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STTypeDeclaration__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1__0__Impl
	rule__STTypeDeclaration__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getArrayAssignment_1_0()); }
	(rule__STTypeDeclaration__ArrayAssignment_1_0)
	{ after(grammarAccess.getSTTypeDeclarationAccess().getArrayAssignment_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1__1__Impl
	rule__STTypeDeclaration__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getAlternatives_1_1()); }
	(rule__STTypeDeclaration__Alternatives_1_1)
	{ after(grammarAccess.getSTTypeDeclarationAccess().getAlternatives_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getOFKeyword_1_2()); }
	OF
	{ after(grammarAccess.getSTTypeDeclarationAccess().getOFKeyword_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STTypeDeclaration__Group_1_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_0__0__Impl
	rule__STTypeDeclaration__Group_1_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_0_0()); }
	LeftSquareBracket
	{ after(grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_0__1__Impl
	rule__STTypeDeclaration__Group_1_1_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getRangesAssignment_1_1_0_1()); }
	(rule__STTypeDeclaration__RangesAssignment_1_1_0_1)
	{ after(grammarAccess.getSTTypeDeclarationAccess().getRangesAssignment_1_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_0__2__Impl
	rule__STTypeDeclaration__Group_1_1_0__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_0_2()); }
	(rule__STTypeDeclaration__Group_1_1_0_2__0)*
	{ after(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_0__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_0__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_0__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_0_3()); }
	RightSquareBracket
	{ after(grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_0_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STTypeDeclaration__Group_1_1_0_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_0_2__0__Impl
	rule__STTypeDeclaration__Group_1_1_0_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_0_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_0_2_0()); }
	Comma
	{ after(grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_0_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_0_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_0_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_0_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getRangesAssignment_1_1_0_2_1()); }
	(rule__STTypeDeclaration__RangesAssignment_1_1_0_2_1)
	{ after(grammarAccess.getSTTypeDeclarationAccess().getRangesAssignment_1_1_0_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STTypeDeclaration__Group_1_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_1__0__Impl
	rule__STTypeDeclaration__Group_1_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_1_0()); }
	LeftSquareBracket
	{ after(grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_1_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_1__1__Impl
	rule__STTypeDeclaration__Group_1_1_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getCountAssignment_1_1_1_1()); }
	(rule__STTypeDeclaration__CountAssignment_1_1_1_1)
	{ after(grammarAccess.getSTTypeDeclarationAccess().getCountAssignment_1_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_1__2__Impl
	rule__STTypeDeclaration__Group_1_1_1__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_1_2()); }
	(rule__STTypeDeclaration__Group_1_1_1_2__0)*
	{ after(grammarAccess.getSTTypeDeclarationAccess().getGroup_1_1_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_1__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_1__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_1__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_1_3()); }
	RightSquareBracket
	{ after(grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_1_1_1_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STTypeDeclaration__Group_1_1_1_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_1_2__0__Impl
	rule__STTypeDeclaration__Group_1_1_1_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_1_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_1_2_0()); }
	Comma
	{ after(grammarAccess.getSTTypeDeclarationAccess().getCommaKeyword_1_1_1_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_1_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_1_1_1_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_1_1_1_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getCountAssignment_1_1_1_2_1()); }
	(rule__STTypeDeclaration__CountAssignment_1_1_1_2_1)
	{ after(grammarAccess.getSTTypeDeclarationAccess().getCountAssignment_1_1_1_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STTypeDeclaration__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_3__0__Impl
	rule__STTypeDeclaration__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_3_0()); }
	LeftSquareBracket
	{ after(grammarAccess.getSTTypeDeclarationAccess().getLeftSquareBracketKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_3__1__Impl
	rule__STTypeDeclaration__Group_3__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getMaxLengthAssignment_3_1()); }
	(rule__STTypeDeclaration__MaxLengthAssignment_3_1)
	{ after(grammarAccess.getSTTypeDeclarationAccess().getMaxLengthAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_3__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTypeDeclaration__Group_3__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__Group_3__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_3_2()); }
	RightSquareBracket
	{ after(grammarAccess.getSTTypeDeclarationAccess().getRightSquareBracketKeyword_3_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STArrayInitializerExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STArrayInitializerExpression__Group__0__Impl
	rule__STArrayInitializerExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0()); }
	LeftSquareBracket
	{ after(grammarAccess.getSTArrayInitializerExpressionAccess().getLeftSquareBracketKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STArrayInitializerExpression__Group__1__Impl
	rule__STArrayInitializerExpression__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesAssignment_1()); }
	(rule__STArrayInitializerExpression__ValuesAssignment_1)
	{ after(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STArrayInitializerExpression__Group__2__Impl
	rule__STArrayInitializerExpression__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTArrayInitializerExpressionAccess().getGroup_2()); }
	(rule__STArrayInitializerExpression__Group_2__0)*
	{ after(grammarAccess.getSTArrayInitializerExpressionAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STArrayInitializerExpression__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTArrayInitializerExpressionAccess().getRightSquareBracketKeyword_3()); }
	RightSquareBracket
	{ after(grammarAccess.getSTArrayInitializerExpressionAccess().getRightSquareBracketKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STArrayInitializerExpression__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STArrayInitializerExpression__Group_2__0__Impl
	rule__STArrayInitializerExpression__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0()); }
	Comma
	{ after(grammarAccess.getSTArrayInitializerExpressionAccess().getCommaKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STArrayInitializerExpression__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesAssignment_2_1()); }
	(rule__STArrayInitializerExpression__ValuesAssignment_2_1)
	{ after(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesAssignment_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STRepeatArrayInitElement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatArrayInitElement__Group__0__Impl
	rule__STRepeatArrayInitElement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getRepetitionsAssignment_0()); }
	(rule__STRepeatArrayInitElement__RepetitionsAssignment_0)
	{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getRepetitionsAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatArrayInitElement__Group__1__Impl
	rule__STRepeatArrayInitElement__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getLeftParenthesisKeyword_1()); }
	LeftParenthesis
	{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatArrayInitElement__Group__2__Impl
	rule__STRepeatArrayInitElement__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsAssignment_2()); }
	(rule__STRepeatArrayInitElement__InitExpressionsAssignment_2)
	{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatArrayInitElement__Group__3__Impl
	rule__STRepeatArrayInitElement__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getGroup_3()); }
	(rule__STRepeatArrayInitElement__Group_3__0)*
	{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatArrayInitElement__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getRightParenthesisKeyword_4()); }
	RightParenthesis
	{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getRightParenthesisKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STRepeatArrayInitElement__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatArrayInitElement__Group_3__0__Impl
	rule__STRepeatArrayInitElement__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getCommaKeyword_3_0()); }
	Comma
	{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getCommaKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatArrayInitElement__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsAssignment_3_1()); }
	(rule__STRepeatArrayInitElement__InitExpressionsAssignment_3_1)
	{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStructInitializerExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitializerExpression__Group__0__Impl
	rule__STStructInitializerExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitializerExpressionAccess().getGroup_0()); }
	(rule__STStructInitializerExpression__Group_0__0)?
	{ after(grammarAccess.getSTStructInitializerExpressionAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitializerExpression__Group__1__Impl
	rule__STStructInitializerExpression__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitializerExpressionAccess().getLeftParenthesisKeyword_1()); }
	LeftParenthesis
	{ after(grammarAccess.getSTStructInitializerExpressionAccess().getLeftParenthesisKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitializerExpression__Group__2__Impl
	rule__STStructInitializerExpression__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitializerExpressionAccess().getValuesAssignment_2()); }
	(rule__STStructInitializerExpression__ValuesAssignment_2)
	{ after(grammarAccess.getSTStructInitializerExpressionAccess().getValuesAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitializerExpression__Group__3__Impl
	rule__STStructInitializerExpression__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitializerExpressionAccess().getGroup_3()); }
	(rule__STStructInitializerExpression__Group_3__0)*
	{ after(grammarAccess.getSTStructInitializerExpressionAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitializerExpression__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitializerExpressionAccess().getRightParenthesisKeyword_4()); }
	RightParenthesis
	{ after(grammarAccess.getSTStructInitializerExpressionAccess().getRightParenthesisKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStructInitializerExpression__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitializerExpression__Group_0__0__Impl
	rule__STStructInitializerExpression__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitializerExpressionAccess().getTypeAssignment_0_0()); }
	(rule__STStructInitializerExpression__TypeAssignment_0_0)
	{ after(grammarAccess.getSTStructInitializerExpressionAccess().getTypeAssignment_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitializerExpression__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitializerExpressionAccess().getNumberSignKeyword_0_1()); }
	NumberSign
	{ after(grammarAccess.getSTStructInitializerExpressionAccess().getNumberSignKeyword_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStructInitializerExpression__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitializerExpression__Group_3__0__Impl
	rule__STStructInitializerExpression__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitializerExpressionAccess().getCommaKeyword_3_0()); }
	Comma
	{ after(grammarAccess.getSTStructInitializerExpressionAccess().getCommaKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitializerExpression__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitializerExpressionAccess().getValuesAssignment_3_1()); }
	(rule__STStructInitializerExpression__ValuesAssignment_3_1)
	{ after(grammarAccess.getSTStructInitializerExpressionAccess().getValuesAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStructInitElement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitElement__Group__0__Impl
	rule__STStructInitElement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitElement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitElementAccess().getVariableAssignment_0()); }
	(rule__STStructInitElement__VariableAssignment_0)
	{ after(grammarAccess.getSTStructInitElementAccess().getVariableAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitElement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitElement__Group__1__Impl
	rule__STStructInitElement__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitElement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitElementAccess().getColonEqualsSignKeyword_1()); }
	ColonEqualsSign
	{ after(grammarAccess.getSTStructInitElementAccess().getColonEqualsSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitElement__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStructInitElement__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitElement__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStructInitElementAccess().getValueAssignment_2()); }
	(rule__STStructInitElement__ValueAssignment_2)
	{ after(grammarAccess.getSTStructInitElementAccess().getValueAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STPragma__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPragma__Group__0__Impl
	rule__STPragma__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPragmaAccess().getSTPragmaAction_0()); }
	()
	{ after(grammarAccess.getSTPragmaAccess().getSTPragmaAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPragma__Group__1__Impl
	rule__STPragma__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPragmaAccess().getLeftCurlyBracketKeyword_1()); }
	LeftCurlyBracket
	{ after(grammarAccess.getSTPragmaAccess().getLeftCurlyBracketKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPragma__Group__2__Impl
	rule__STPragma__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPragmaAccess().getAttributesAssignment_2()); }
	(rule__STPragma__AttributesAssignment_2)
	{ after(grammarAccess.getSTPragmaAccess().getAttributesAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPragma__Group__3__Impl
	rule__STPragma__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPragmaAccess().getGroup_3()); }
	(rule__STPragma__Group_3__0)*
	{ after(grammarAccess.getSTPragmaAccess().getGroup_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPragma__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPragmaAccess().getRightCurlyBracketKeyword_4()); }
	RightCurlyBracket
	{ after(grammarAccess.getSTPragmaAccess().getRightCurlyBracketKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STPragma__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPragma__Group_3__0__Impl
	rule__STPragma__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPragmaAccess().getCommaKeyword_3_0()); }
	Comma
	{ after(grammarAccess.getSTPragmaAccess().getCommaKeyword_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPragma__Group_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPragmaAccess().getAttributesAssignment_3_1()); }
	(rule__STPragma__AttributesAssignment_3_1)
	{ after(grammarAccess.getSTPragmaAccess().getAttributesAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAttribute__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAttribute__Group__0__Impl
	rule__STAttribute__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAttribute__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAttributeAccess().getDeclarationAssignment_0()); }
	(rule__STAttribute__DeclarationAssignment_0)
	{ after(grammarAccess.getSTAttributeAccess().getDeclarationAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAttribute__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAttribute__Group__1__Impl
	rule__STAttribute__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STAttribute__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAttributeAccess().getColonEqualsSignKeyword_1()); }
	ColonEqualsSign
	{ after(grammarAccess.getSTAttributeAccess().getColonEqualsSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAttribute__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAttribute__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAttribute__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAttributeAccess().getValueAssignment_2()); }
	(rule__STAttribute__ValueAssignment_2)
	{ after(grammarAccess.getSTAttributeAccess().getValueAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStatement__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStatement__Group_0__0__Impl
	rule__STStatement__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStatementAccess().getAlternatives_0_0()); }
	(rule__STStatement__Alternatives_0_0)
	{ after(grammarAccess.getSTStatementAccess().getAlternatives_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStatement__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStatementAccess().getSemicolonKeyword_0_1()); }
	Semicolon
	{ after(grammarAccess.getSTStatementAccess().getSemicolonKeyword_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStatement__Group_0_0_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStatement__Group_0_0_6__0__Impl
	rule__STStatement__Group_0_0_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0_0_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStatementAccess().getSTReturnAction_0_0_6_0()); }
	()
	{ after(grammarAccess.getSTStatementAccess().getSTReturnAction_0_0_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0_0_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStatement__Group_0_0_6__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0_0_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStatementAccess().getRETURNKeyword_0_0_6_1()); }
	RETURN
	{ after(grammarAccess.getSTStatementAccess().getRETURNKeyword_0_0_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStatement__Group_0_0_7__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStatement__Group_0_0_7__0__Impl
	rule__STStatement__Group_0_0_7__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0_0_7__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStatementAccess().getSTContinueAction_0_0_7_0()); }
	()
	{ after(grammarAccess.getSTStatementAccess().getSTContinueAction_0_0_7_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0_0_7__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStatement__Group_0_0_7__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0_0_7__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStatementAccess().getCONTINUEKeyword_0_0_7_1()); }
	CONTINUE
	{ after(grammarAccess.getSTStatementAccess().getCONTINUEKeyword_0_0_7_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStatement__Group_0_0_8__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStatement__Group_0_0_8__0__Impl
	rule__STStatement__Group_0_0_8__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0_0_8__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStatementAccess().getSTExitAction_0_0_8_0()); }
	()
	{ after(grammarAccess.getSTStatementAccess().getSTExitAction_0_0_8_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0_0_8__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStatement__Group_0_0_8__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_0_0_8__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStatementAccess().getEXITKeyword_0_0_8_1()); }
	EXIT
	{ after(grammarAccess.getSTStatementAccess().getEXITKeyword_0_0_8_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStatement__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStatement__Group_1__0__Impl
	rule__STStatement__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStatementAccess().getSTNopAction_1_0()); }
	()
	{ after(grammarAccess.getSTStatementAccess().getSTNopAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStatement__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStatement__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStatementAccess().getSemicolonKeyword_1_1()); }
	Semicolon
	{ after(grammarAccess.getSTStatementAccess().getSemicolonKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAssignment__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAssignment__Group__0__Impl
	rule__STAssignment__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAssignment__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAssignmentAccess().getSTExpressionParserRuleCall_0()); }
	ruleSTExpression
	{ after(grammarAccess.getSTAssignmentAccess().getSTExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAssignment__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAssignment__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAssignment__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAssignmentAccess().getGroup_1()); }
	(rule__STAssignment__Group_1__0)?
	{ after(grammarAccess.getSTAssignmentAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAssignment__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAssignment__Group_1__0__Impl
	rule__STAssignment__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAssignment__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAssignmentAccess().getSTAssignmentLeftAction_1_0()); }
	()
	{ after(grammarAccess.getSTAssignmentAccess().getSTAssignmentLeftAction_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAssignment__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAssignment__Group_1__1__Impl
	rule__STAssignment__Group_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STAssignment__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAssignmentAccess().getColonEqualsSignKeyword_1_1()); }
	ColonEqualsSign
	{ after(grammarAccess.getSTAssignmentAccess().getColonEqualsSignKeyword_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAssignment__Group_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAssignment__Group_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAssignment__Group_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAssignmentAccess().getRightAssignment_1_2()); }
	(rule__STAssignment__RightAssignment_1_2)
	{ after(grammarAccess.getSTAssignmentAccess().getRightAssignment_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STCallNamedInputArgument__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCallNamedInputArgument__Group__0__Impl
	rule__STCallNamedInputArgument__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedInputArgument__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCallNamedInputArgumentAccess().getParameterAssignment_0()); }
	(rule__STCallNamedInputArgument__ParameterAssignment_0)
	{ after(grammarAccess.getSTCallNamedInputArgumentAccess().getParameterAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedInputArgument__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCallNamedInputArgument__Group__1__Impl
	rule__STCallNamedInputArgument__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedInputArgument__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCallNamedInputArgumentAccess().getColonEqualsSignKeyword_1()); }
	ColonEqualsSign
	{ after(grammarAccess.getSTCallNamedInputArgumentAccess().getColonEqualsSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedInputArgument__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCallNamedInputArgument__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedInputArgument__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCallNamedInputArgumentAccess().getArgumentAssignment_2()); }
	(rule__STCallNamedInputArgument__ArgumentAssignment_2)
	{ after(grammarAccess.getSTCallNamedInputArgumentAccess().getArgumentAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STCallNamedOutputArgument__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCallNamedOutputArgument__Group__0__Impl
	rule__STCallNamedOutputArgument__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedOutputArgument__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCallNamedOutputArgumentAccess().getNotAssignment_0()); }
	(rule__STCallNamedOutputArgument__NotAssignment_0)?
	{ after(grammarAccess.getSTCallNamedOutputArgumentAccess().getNotAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedOutputArgument__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCallNamedOutputArgument__Group__1__Impl
	rule__STCallNamedOutputArgument__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedOutputArgument__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterAssignment_1()); }
	(rule__STCallNamedOutputArgument__ParameterAssignment_1)
	{ after(grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedOutputArgument__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCallNamedOutputArgument__Group__2__Impl
	rule__STCallNamedOutputArgument__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedOutputArgument__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCallNamedOutputArgumentAccess().getEqualsSignGreaterThanSignKeyword_2()); }
	EqualsSignGreaterThanSign
	{ after(grammarAccess.getSTCallNamedOutputArgumentAccess().getEqualsSignGreaterThanSignKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedOutputArgument__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCallNamedOutputArgument__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedOutputArgument__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCallNamedOutputArgumentAccess().getArgumentAssignment_3()); }
	(rule__STCallNamedOutputArgument__ArgumentAssignment_3)
	{ after(grammarAccess.getSTCallNamedOutputArgumentAccess().getArgumentAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STIfStatement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STIfStatement__Group__0__Impl
	rule__STIfStatement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTIfStatementAccess().getIFKeyword_0()); }
	IF
	{ after(grammarAccess.getSTIfStatementAccess().getIFKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STIfStatement__Group__1__Impl
	rule__STIfStatement__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTIfStatementAccess().getConditionAssignment_1()); }
	(rule__STIfStatement__ConditionAssignment_1)
	{ after(grammarAccess.getSTIfStatementAccess().getConditionAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STIfStatement__Group__2__Impl
	rule__STIfStatement__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTIfStatementAccess().getTHENKeyword_2()); }
	THEN
	{ after(grammarAccess.getSTIfStatementAccess().getTHENKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STIfStatement__Group__3__Impl
	rule__STIfStatement__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTIfStatementAccess().getStatementsAssignment_3()); }
	(rule__STIfStatement__StatementsAssignment_3)*
	{ after(grammarAccess.getSTIfStatementAccess().getStatementsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STIfStatement__Group__4__Impl
	rule__STIfStatement__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTIfStatementAccess().getElseifsAssignment_4()); }
	(rule__STIfStatement__ElseifsAssignment_4)*
	{ after(grammarAccess.getSTIfStatementAccess().getElseifsAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STIfStatement__Group__5__Impl
	rule__STIfStatement__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTIfStatementAccess().getElseAssignment_5()); }
	(rule__STIfStatement__ElseAssignment_5)?
	{ after(grammarAccess.getSTIfStatementAccess().getElseAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STIfStatement__Group__6__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTIfStatementAccess().getEND_IFKeyword_6()); }
	END_IF
	{ after(grammarAccess.getSTIfStatementAccess().getEND_IFKeyword_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STElseIfPart__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STElseIfPart__Group__0__Impl
	rule__STElseIfPart__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STElseIfPart__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0()); }
	ELSIF
	{ after(grammarAccess.getSTElseIfPartAccess().getELSIFKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STElseIfPart__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STElseIfPart__Group__1__Impl
	rule__STElseIfPart__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STElseIfPart__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTElseIfPartAccess().getConditionAssignment_1()); }
	(rule__STElseIfPart__ConditionAssignment_1)
	{ after(grammarAccess.getSTElseIfPartAccess().getConditionAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STElseIfPart__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STElseIfPart__Group__2__Impl
	rule__STElseIfPart__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STElseIfPart__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2()); }
	THEN
	{ after(grammarAccess.getSTElseIfPartAccess().getTHENKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STElseIfPart__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STElseIfPart__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STElseIfPart__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTElseIfPartAccess().getStatementsAssignment_3()); }
	(rule__STElseIfPart__StatementsAssignment_3)*
	{ after(grammarAccess.getSTElseIfPartAccess().getStatementsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STCaseStatement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseStatement__Group__0__Impl
	rule__STCaseStatement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0()); }
	CASE
	{ after(grammarAccess.getSTCaseStatementAccess().getCASEKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseStatement__Group__1__Impl
	rule__STCaseStatement__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseStatementAccess().getSelectorAssignment_1()); }
	(rule__STCaseStatement__SelectorAssignment_1)
	{ after(grammarAccess.getSTCaseStatementAccess().getSelectorAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseStatement__Group__2__Impl
	rule__STCaseStatement__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseStatementAccess().getOFKeyword_2()); }
	OF
	{ after(grammarAccess.getSTCaseStatementAccess().getOFKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseStatement__Group__3__Impl
	rule__STCaseStatement__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	(
		{ before(grammarAccess.getSTCaseStatementAccess().getCasesAssignment_3()); }
		(rule__STCaseStatement__CasesAssignment_3)
		{ after(grammarAccess.getSTCaseStatementAccess().getCasesAssignment_3()); }
	)
	(
		{ before(grammarAccess.getSTCaseStatementAccess().getCasesAssignment_3()); }
		(rule__STCaseStatement__CasesAssignment_3)*
		{ after(grammarAccess.getSTCaseStatementAccess().getCasesAssignment_3()); }
	)
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseStatement__Group__4__Impl
	rule__STCaseStatement__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseStatementAccess().getElseAssignment_4()); }
	(rule__STCaseStatement__ElseAssignment_4)?
	{ after(grammarAccess.getSTCaseStatementAccess().getElseAssignment_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseStatement__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseStatementAccess().getEND_CASEKeyword_5()); }
	END_CASE
	{ after(grammarAccess.getSTCaseStatementAccess().getEND_CASEKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STCaseCases__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseCases__Group__0__Impl
	rule__STCaseCases__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseCasesAccess().getConditionsAssignment_0()); }
	(rule__STCaseCases__ConditionsAssignment_0)
	{ after(grammarAccess.getSTCaseCasesAccess().getConditionsAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseCases__Group__1__Impl
	rule__STCaseCases__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseCasesAccess().getGroup_1()); }
	(rule__STCaseCases__Group_1__0)*
	{ after(grammarAccess.getSTCaseCasesAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseCases__Group__2__Impl
	rule__STCaseCases__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseCasesAccess().getColonKeyword_2()); }
	Colon
	{ after(grammarAccess.getSTCaseCasesAccess().getColonKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseCases__Group__3__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseCasesAccess().getStatementsAssignment_3()); }
	(rule__STCaseCases__StatementsAssignment_3)*
	{ after(grammarAccess.getSTCaseCasesAccess().getStatementsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STCaseCases__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseCases__Group_1__0__Impl
	rule__STCaseCases__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0()); }
	Comma
	{ after(grammarAccess.getSTCaseCasesAccess().getCommaKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STCaseCases__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTCaseCasesAccess().getConditionsAssignment_1_1()); }
	(rule__STCaseCases__ConditionsAssignment_1_1)
	{ after(grammarAccess.getSTCaseCasesAccess().getConditionsAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STElsePart__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STElsePart__Group__0__Impl
	rule__STElsePart__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STElsePart__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTElsePartAccess().getSTElsePartAction_0()); }
	()
	{ after(grammarAccess.getSTElsePartAccess().getSTElsePartAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STElsePart__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STElsePart__Group__1__Impl
	rule__STElsePart__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STElsePart__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTElsePartAccess().getELSEKeyword_1()); }
	ELSE
	{ after(grammarAccess.getSTElsePartAccess().getELSEKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STElsePart__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STElsePart__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STElsePart__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTElsePartAccess().getStatementsAssignment_2()); }
	(rule__STElsePart__StatementsAssignment_2)*
	{ after(grammarAccess.getSTElsePartAccess().getStatementsAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STForStatement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group__0__Impl
	rule__STForStatement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getFORKeyword_0()); }
	FOR
	{ after(grammarAccess.getSTForStatementAccess().getFORKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group__1__Impl
	rule__STForStatement__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getVariableAssignment_1()); }
	(rule__STForStatement__VariableAssignment_1)
	{ after(grammarAccess.getSTForStatementAccess().getVariableAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group__2__Impl
	rule__STForStatement__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getColonEqualsSignKeyword_2()); }
	ColonEqualsSign
	{ after(grammarAccess.getSTForStatementAccess().getColonEqualsSignKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group__3__Impl
	rule__STForStatement__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getFromAssignment_3()); }
	(rule__STForStatement__FromAssignment_3)
	{ after(grammarAccess.getSTForStatementAccess().getFromAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group__4__Impl
	rule__STForStatement__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getTOKeyword_4()); }
	TO
	{ after(grammarAccess.getSTForStatementAccess().getTOKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group__5__Impl
	rule__STForStatement__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getToAssignment_5()); }
	(rule__STForStatement__ToAssignment_5)
	{ after(grammarAccess.getSTForStatementAccess().getToAssignment_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group__6__Impl
	rule__STForStatement__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getGroup_6()); }
	(rule__STForStatement__Group_6__0)?
	{ after(grammarAccess.getSTForStatementAccess().getGroup_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group__7__Impl
	rule__STForStatement__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getDOKeyword_7()); }
	DO
	{ after(grammarAccess.getSTForStatementAccess().getDOKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group__8__Impl
	rule__STForStatement__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getStatementsAssignment_8()); }
	(rule__STForStatement__StatementsAssignment_8)*
	{ after(grammarAccess.getSTForStatementAccess().getStatementsAssignment_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group__9__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getEND_FORKeyword_9()); }
	END_FOR
	{ after(grammarAccess.getSTForStatementAccess().getEND_FORKeyword_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STForStatement__Group_6__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group_6__0__Impl
	rule__STForStatement__Group_6__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group_6__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getBYKeyword_6_0()); }
	BY
	{ after(grammarAccess.getSTForStatementAccess().getBYKeyword_6_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group_6__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STForStatement__Group_6__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__Group_6__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTForStatementAccess().getByAssignment_6_1()); }
	(rule__STForStatement__ByAssignment_6_1)
	{ after(grammarAccess.getSTForStatementAccess().getByAssignment_6_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STWhileStatement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STWhileStatement__Group__0__Impl
	rule__STWhileStatement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0()); }
	WHILE
	{ after(grammarAccess.getSTWhileStatementAccess().getWHILEKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STWhileStatement__Group__1__Impl
	rule__STWhileStatement__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTWhileStatementAccess().getConditionAssignment_1()); }
	(rule__STWhileStatement__ConditionAssignment_1)
	{ after(grammarAccess.getSTWhileStatementAccess().getConditionAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STWhileStatement__Group__2__Impl
	rule__STWhileStatement__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTWhileStatementAccess().getDOKeyword_2()); }
	DO
	{ after(grammarAccess.getSTWhileStatementAccess().getDOKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STWhileStatement__Group__3__Impl
	rule__STWhileStatement__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTWhileStatementAccess().getStatementsAssignment_3()); }
	(rule__STWhileStatement__StatementsAssignment_3)*
	{ after(grammarAccess.getSTWhileStatementAccess().getStatementsAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STWhileStatement__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTWhileStatementAccess().getEND_WHILEKeyword_4()); }
	END_WHILE
	{ after(grammarAccess.getSTWhileStatementAccess().getEND_WHILEKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STRepeatStatement__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatStatement__Group__0__Impl
	rule__STRepeatStatement__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0()); }
	REPEAT
	{ after(grammarAccess.getSTRepeatStatementAccess().getREPEATKeyword_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatStatement__Group__1__Impl
	rule__STRepeatStatement__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatStatementAccess().getStatementsAssignment_1()); }
	(rule__STRepeatStatement__StatementsAssignment_1)*
	{ after(grammarAccess.getSTRepeatStatementAccess().getStatementsAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatStatement__Group__2__Impl
	rule__STRepeatStatement__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2()); }
	UNTIL
	{ after(grammarAccess.getSTRepeatStatementAccess().getUNTILKeyword_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatStatement__Group__3__Impl
	rule__STRepeatStatement__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatStatementAccess().getConditionAssignment_3()); }
	(rule__STRepeatStatement__ConditionAssignment_3)
	{ after(grammarAccess.getSTRepeatStatementAccess().getConditionAssignment_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STRepeatStatement__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTRepeatStatementAccess().getEND_REPEATKeyword_4()); }
	END_REPEAT
	{ after(grammarAccess.getSTRepeatStatementAccess().getEND_REPEATKeyword_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STSubrangeExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STSubrangeExpression__Group__0__Impl
	rule__STSubrangeExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0()); }
	ruleSTOrExpression
	{ after(grammarAccess.getSTSubrangeExpressionAccess().getSTOrExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STSubrangeExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTSubrangeExpressionAccess().getGroup_1()); }
	(rule__STSubrangeExpression__Group_1__0)*
	{ after(grammarAccess.getSTSubrangeExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STSubrangeExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STSubrangeExpression__Group_1__0__Impl
	rule__STSubrangeExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTSubrangeExpressionAccess().getGroup_1_0()); }
	(rule__STSubrangeExpression__Group_1_0__0)
	{ after(grammarAccess.getSTSubrangeExpressionAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STSubrangeExpression__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTSubrangeExpressionAccess().getRightAssignment_1_1()); }
	(rule__STSubrangeExpression__RightAssignment_1_1)
	{ after(grammarAccess.getSTSubrangeExpressionAccess().getRightAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STSubrangeExpression__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STSubrangeExpression__Group_1_0__0__Impl
	rule__STSubrangeExpression__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
	()
	{ after(grammarAccess.getSTSubrangeExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STSubrangeExpression__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTSubrangeExpressionAccess().getOpAssignment_1_0_1()); }
	(rule__STSubrangeExpression__OpAssignment_1_0_1)
	{ after(grammarAccess.getSTSubrangeExpressionAccess().getOpAssignment_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STOrExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STOrExpression__Group__0__Impl
	rule__STOrExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0()); }
	ruleSTXorExpression
	{ after(grammarAccess.getSTOrExpressionAccess().getSTXorExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STOrExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTOrExpressionAccess().getGroup_1()); }
	(rule__STOrExpression__Group_1__0)*
	{ after(grammarAccess.getSTOrExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STOrExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STOrExpression__Group_1__0__Impl
	rule__STOrExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTOrExpressionAccess().getGroup_1_0()); }
	(rule__STOrExpression__Group_1_0__0)
	{ after(grammarAccess.getSTOrExpressionAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STOrExpression__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTOrExpressionAccess().getRightAssignment_1_1()); }
	(rule__STOrExpression__RightAssignment_1_1)
	{ after(grammarAccess.getSTOrExpressionAccess().getRightAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STOrExpression__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STOrExpression__Group_1_0__0__Impl
	rule__STOrExpression__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
	()
	{ after(grammarAccess.getSTOrExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STOrExpression__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTOrExpressionAccess().getOpAssignment_1_0_1()); }
	(rule__STOrExpression__OpAssignment_1_0_1)
	{ after(grammarAccess.getSTOrExpressionAccess().getOpAssignment_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STXorExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STXorExpression__Group__0__Impl
	rule__STXorExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0()); }
	ruleSTAndExpression
	{ after(grammarAccess.getSTXorExpressionAccess().getSTAndExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STXorExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTXorExpressionAccess().getGroup_1()); }
	(rule__STXorExpression__Group_1__0)*
	{ after(grammarAccess.getSTXorExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STXorExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STXorExpression__Group_1__0__Impl
	rule__STXorExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTXorExpressionAccess().getGroup_1_0()); }
	(rule__STXorExpression__Group_1_0__0)
	{ after(grammarAccess.getSTXorExpressionAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STXorExpression__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTXorExpressionAccess().getRightAssignment_1_1()); }
	(rule__STXorExpression__RightAssignment_1_1)
	{ after(grammarAccess.getSTXorExpressionAccess().getRightAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STXorExpression__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STXorExpression__Group_1_0__0__Impl
	rule__STXorExpression__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
	()
	{ after(grammarAccess.getSTXorExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STXorExpression__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTXorExpressionAccess().getOpAssignment_1_0_1()); }
	(rule__STXorExpression__OpAssignment_1_0_1)
	{ after(grammarAccess.getSTXorExpressionAccess().getOpAssignment_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAndExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAndExpression__Group__0__Impl
	rule__STAndExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0()); }
	ruleSTEqualityExpression
	{ after(grammarAccess.getSTAndExpressionAccess().getSTEqualityExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAndExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAndExpressionAccess().getGroup_1()); }
	(rule__STAndExpression__Group_1__0)*
	{ after(grammarAccess.getSTAndExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAndExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAndExpression__Group_1__0__Impl
	rule__STAndExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAndExpressionAccess().getGroup_1_0()); }
	(rule__STAndExpression__Group_1_0__0)
	{ after(grammarAccess.getSTAndExpressionAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAndExpression__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAndExpressionAccess().getRightAssignment_1_1()); }
	(rule__STAndExpression__RightAssignment_1_1)
	{ after(grammarAccess.getSTAndExpressionAccess().getRightAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAndExpression__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAndExpression__Group_1_0__0__Impl
	rule__STAndExpression__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
	()
	{ after(grammarAccess.getSTAndExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAndExpression__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAndExpressionAccess().getOpAssignment_1_0_1()); }
	(rule__STAndExpression__OpAssignment_1_0_1)
	{ after(grammarAccess.getSTAndExpressionAccess().getOpAssignment_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STEqualityExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STEqualityExpression__Group__0__Impl
	rule__STEqualityExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0()); }
	ruleSTComparisonExpression
	{ after(grammarAccess.getSTEqualityExpressionAccess().getSTComparisonExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STEqualityExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTEqualityExpressionAccess().getGroup_1()); }
	(rule__STEqualityExpression__Group_1__0)*
	{ after(grammarAccess.getSTEqualityExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STEqualityExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STEqualityExpression__Group_1__0__Impl
	rule__STEqualityExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTEqualityExpressionAccess().getGroup_1_0()); }
	(rule__STEqualityExpression__Group_1_0__0)
	{ after(grammarAccess.getSTEqualityExpressionAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STEqualityExpression__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTEqualityExpressionAccess().getRightAssignment_1_1()); }
	(rule__STEqualityExpression__RightAssignment_1_1)
	{ after(grammarAccess.getSTEqualityExpressionAccess().getRightAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STEqualityExpression__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STEqualityExpression__Group_1_0__0__Impl
	rule__STEqualityExpression__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
	()
	{ after(grammarAccess.getSTEqualityExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STEqualityExpression__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTEqualityExpressionAccess().getOpAssignment_1_0_1()); }
	(rule__STEqualityExpression__OpAssignment_1_0_1)
	{ after(grammarAccess.getSTEqualityExpressionAccess().getOpAssignment_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STComparisonExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STComparisonExpression__Group__0__Impl
	rule__STComparisonExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0()); }
	ruleSTAddSubExpression
	{ after(grammarAccess.getSTComparisonExpressionAccess().getSTAddSubExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STComparisonExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTComparisonExpressionAccess().getGroup_1()); }
	(rule__STComparisonExpression__Group_1__0)*
	{ after(grammarAccess.getSTComparisonExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STComparisonExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STComparisonExpression__Group_1__0__Impl
	rule__STComparisonExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTComparisonExpressionAccess().getGroup_1_0()); }
	(rule__STComparisonExpression__Group_1_0__0)
	{ after(grammarAccess.getSTComparisonExpressionAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STComparisonExpression__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTComparisonExpressionAccess().getRightAssignment_1_1()); }
	(rule__STComparisonExpression__RightAssignment_1_1)
	{ after(grammarAccess.getSTComparisonExpressionAccess().getRightAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STComparisonExpression__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STComparisonExpression__Group_1_0__0__Impl
	rule__STComparisonExpression__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
	()
	{ after(grammarAccess.getSTComparisonExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STComparisonExpression__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTComparisonExpressionAccess().getOpAssignment_1_0_1()); }
	(rule__STComparisonExpression__OpAssignment_1_0_1)
	{ after(grammarAccess.getSTComparisonExpressionAccess().getOpAssignment_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAddSubExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAddSubExpression__Group__0__Impl
	rule__STAddSubExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0()); }
	ruleSTMulDivModExpression
	{ after(grammarAccess.getSTAddSubExpressionAccess().getSTMulDivModExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAddSubExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAddSubExpressionAccess().getGroup_1()); }
	(rule__STAddSubExpression__Group_1__0)*
	{ after(grammarAccess.getSTAddSubExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAddSubExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAddSubExpression__Group_1__0__Impl
	rule__STAddSubExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAddSubExpressionAccess().getGroup_1_0()); }
	(rule__STAddSubExpression__Group_1_0__0)
	{ after(grammarAccess.getSTAddSubExpressionAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAddSubExpression__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAddSubExpressionAccess().getRightAssignment_1_1()); }
	(rule__STAddSubExpression__RightAssignment_1_1)
	{ after(grammarAccess.getSTAddSubExpressionAccess().getRightAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAddSubExpression__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAddSubExpression__Group_1_0__0__Impl
	rule__STAddSubExpression__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
	()
	{ after(grammarAccess.getSTAddSubExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAddSubExpression__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAddSubExpressionAccess().getOpAssignment_1_0_1()); }
	(rule__STAddSubExpression__OpAssignment_1_0_1)
	{ after(grammarAccess.getSTAddSubExpressionAccess().getOpAssignment_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STMulDivModExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMulDivModExpression__Group__0__Impl
	rule__STMulDivModExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0()); }
	ruleSTPowerExpression
	{ after(grammarAccess.getSTMulDivModExpressionAccess().getSTPowerExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMulDivModExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMulDivModExpressionAccess().getGroup_1()); }
	(rule__STMulDivModExpression__Group_1__0)*
	{ after(grammarAccess.getSTMulDivModExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STMulDivModExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMulDivModExpression__Group_1__0__Impl
	rule__STMulDivModExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMulDivModExpressionAccess().getGroup_1_0()); }
	(rule__STMulDivModExpression__Group_1_0__0)
	{ after(grammarAccess.getSTMulDivModExpressionAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMulDivModExpression__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMulDivModExpressionAccess().getRightAssignment_1_1()); }
	(rule__STMulDivModExpression__RightAssignment_1_1)
	{ after(grammarAccess.getSTMulDivModExpressionAccess().getRightAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STMulDivModExpression__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMulDivModExpression__Group_1_0__0__Impl
	rule__STMulDivModExpression__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
	()
	{ after(grammarAccess.getSTMulDivModExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMulDivModExpression__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMulDivModExpressionAccess().getOpAssignment_1_0_1()); }
	(rule__STMulDivModExpression__OpAssignment_1_0_1)
	{ after(grammarAccess.getSTMulDivModExpressionAccess().getOpAssignment_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STPowerExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPowerExpression__Group__0__Impl
	rule__STPowerExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0()); }
	ruleSTUnaryExpression
	{ after(grammarAccess.getSTPowerExpressionAccess().getSTUnaryExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPowerExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPowerExpressionAccess().getGroup_1()); }
	(rule__STPowerExpression__Group_1__0)*
	{ after(grammarAccess.getSTPowerExpressionAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STPowerExpression__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPowerExpression__Group_1__0__Impl
	rule__STPowerExpression__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPowerExpressionAccess().getGroup_1_0()); }
	(rule__STPowerExpression__Group_1_0__0)
	{ after(grammarAccess.getSTPowerExpressionAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPowerExpression__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPowerExpressionAccess().getRightAssignment_1_1()); }
	(rule__STPowerExpression__RightAssignment_1_1)
	{ after(grammarAccess.getSTPowerExpressionAccess().getRightAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STPowerExpression__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPowerExpression__Group_1_0__0__Impl
	rule__STPowerExpression__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
	()
	{ after(grammarAccess.getSTPowerExpressionAccess().getSTBinaryExpressionLeftAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPowerExpression__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPowerExpressionAccess().getOpAssignment_1_0_1()); }
	(rule__STPowerExpression__OpAssignment_1_0_1)
	{ after(grammarAccess.getSTPowerExpressionAccess().getOpAssignment_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STUnaryExpression__Group_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STUnaryExpression__Group_3__0__Impl
	rule__STUnaryExpression__Group_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STUnaryExpression__Group_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_3_0()); }
	()
	{ after(grammarAccess.getSTUnaryExpressionAccess().getSTUnaryExpressionAction_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STUnaryExpression__Group_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STUnaryExpression__Group_3__1__Impl
	rule__STUnaryExpression__Group_3__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STUnaryExpression__Group_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTUnaryExpressionAccess().getOpAssignment_3_1()); }
	(rule__STUnaryExpression__OpAssignment_3_1)
	{ after(grammarAccess.getSTUnaryExpressionAccess().getOpAssignment_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STUnaryExpression__Group_3__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STUnaryExpression__Group_3__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STUnaryExpression__Group_3__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTUnaryExpressionAccess().getExpressionAssignment_3_2()); }
	(rule__STUnaryExpression__ExpressionAssignment_3_2)
	{ after(grammarAccess.getSTUnaryExpressionAccess().getExpressionAssignment_3_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAccessExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group__0__Impl
	rule__STAccessExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0()); }
	ruleSTPrimaryExpression
	{ after(grammarAccess.getSTAccessExpressionAccess().getSTPrimaryExpressionParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getAlternatives_1()); }
	(rule__STAccessExpression__Alternatives_1)*
	{ after(grammarAccess.getSTAccessExpressionAccess().getAlternatives_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAccessExpression__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group_1_0__0__Impl
	rule__STAccessExpression__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0()); }
	()
	{ after(grammarAccess.getSTAccessExpressionAccess().getSTMemberAccessExpressionReceiverAction_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group_1_0__1__Impl
	rule__STAccessExpression__Group_1_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1()); }
	FullStop
	{ after(grammarAccess.getSTAccessExpressionAccess().getFullStopKeyword_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group_1_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getMemberAssignment_1_0_2()); }
	(rule__STAccessExpression__MemberAssignment_1_0_2)
	{ after(grammarAccess.getSTAccessExpressionAccess().getMemberAssignment_1_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAccessExpression__Group_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group_1_1__0__Impl
	rule__STAccessExpression__Group_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getSTArrayAccessExpressionReceiverAction_1_1_0()); }
	()
	{ after(grammarAccess.getSTAccessExpressionAccess().getSTArrayAccessExpressionReceiverAction_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group_1_1__1__Impl
	rule__STAccessExpression__Group_1_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getLeftSquareBracketKeyword_1_1_1()); }
	LeftSquareBracket
	{ after(grammarAccess.getSTAccessExpressionAccess().getLeftSquareBracketKeyword_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group_1_1__2__Impl
	rule__STAccessExpression__Group_1_1__3
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getIndexAssignment_1_1_2()); }
	(rule__STAccessExpression__IndexAssignment_1_1_2)
	{ after(grammarAccess.getSTAccessExpressionAccess().getIndexAssignment_1_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group_1_1__3__Impl
	rule__STAccessExpression__Group_1_1__4
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getGroup_1_1_3()); }
	(rule__STAccessExpression__Group_1_1_3__0)*
	{ after(grammarAccess.getSTAccessExpressionAccess().getGroup_1_1_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group_1_1__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4()); }
	RightSquareBracket
	{ after(grammarAccess.getSTAccessExpressionAccess().getRightSquareBracketKeyword_1_1_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STAccessExpression__Group_1_1_3__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group_1_1_3__0__Impl
	rule__STAccessExpression__Group_1_1_3__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1_3__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0()); }
	Comma
	{ after(grammarAccess.getSTAccessExpressionAccess().getCommaKeyword_1_1_3_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1_3__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STAccessExpression__Group_1_1_3__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__Group_1_1_3__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTAccessExpressionAccess().getIndexAssignment_1_1_3_1()); }
	(rule__STAccessExpression__IndexAssignment_1_1_3_1)
	{ after(grammarAccess.getSTAccessExpressionAccess().getIndexAssignment_1_1_3_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STPrimaryExpression__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPrimaryExpression__Group_0__0__Impl
	rule__STPrimaryExpression__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STPrimaryExpression__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0()); }
	LeftParenthesis
	{ after(grammarAccess.getSTPrimaryExpressionAccess().getLeftParenthesisKeyword_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPrimaryExpression__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPrimaryExpression__Group_0__1__Impl
	rule__STPrimaryExpression__Group_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STPrimaryExpression__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1()); }
	ruleSTExpression
	{ after(grammarAccess.getSTPrimaryExpressionAccess().getSTExpressionParserRuleCall_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPrimaryExpression__Group_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STPrimaryExpression__Group_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STPrimaryExpression__Group_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTPrimaryExpressionAccess().getRightParenthesisKeyword_0_2()); }
	RightParenthesis
	{ after(grammarAccess.getSTPrimaryExpressionAccess().getRightParenthesisKeyword_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STFeatureExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STFeatureExpression__Group__0__Impl
	rule__STFeatureExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0()); }
	()
	{ after(grammarAccess.getSTFeatureExpressionAccess().getSTFeatureExpressionAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STFeatureExpression__Group__1__Impl
	rule__STFeatureExpression__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTFeatureExpressionAccess().getFeatureAssignment_1()); }
	(rule__STFeatureExpression__FeatureAssignment_1)
	{ after(grammarAccess.getSTFeatureExpressionAccess().getFeatureAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STFeatureExpression__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTFeatureExpressionAccess().getGroup_2()); }
	(rule__STFeatureExpression__Group_2__0)?
	{ after(grammarAccess.getSTFeatureExpressionAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STFeatureExpression__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STFeatureExpression__Group_2__0__Impl
	rule__STFeatureExpression__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTFeatureExpressionAccess().getCallAssignment_2_0()); }
	(rule__STFeatureExpression__CallAssignment_2_0)
	{ after(grammarAccess.getSTFeatureExpressionAccess().getCallAssignment_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STFeatureExpression__Group_2__1__Impl
	rule__STFeatureExpression__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTFeatureExpressionAccess().getGroup_2_1()); }
	(rule__STFeatureExpression__Group_2_1__0)?
	{ after(grammarAccess.getSTFeatureExpressionAccess().getGroup_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STFeatureExpression__Group_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTFeatureExpressionAccess().getRightParenthesisKeyword_2_2()); }
	RightParenthesis
	{ after(grammarAccess.getSTFeatureExpressionAccess().getRightParenthesisKeyword_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STFeatureExpression__Group_2_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STFeatureExpression__Group_2_1__0__Impl
	rule__STFeatureExpression__Group_2_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTFeatureExpressionAccess().getParametersAssignment_2_1_0()); }
	(rule__STFeatureExpression__ParametersAssignment_2_1_0)
	{ after(grammarAccess.getSTFeatureExpressionAccess().getParametersAssignment_2_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STFeatureExpression__Group_2_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTFeatureExpressionAccess().getGroup_2_1_1()); }
	(rule__STFeatureExpression__Group_2_1_1__0)*
	{ after(grammarAccess.getSTFeatureExpressionAccess().getGroup_2_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STFeatureExpression__Group_2_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STFeatureExpression__Group_2_1_1__0__Impl
	rule__STFeatureExpression__Group_2_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0()); }
	Comma
	{ after(grammarAccess.getSTFeatureExpressionAccess().getCommaKeyword_2_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STFeatureExpression__Group_2_1_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__Group_2_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTFeatureExpressionAccess().getParametersAssignment_2_1_1_1()); }
	(rule__STFeatureExpression__ParametersAssignment_2_1_1_1)
	{ after(grammarAccess.getSTFeatureExpressionAccess().getParametersAssignment_2_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STBuiltinFeatureExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STBuiltinFeatureExpression__Group__0__Impl
	rule__STBuiltinFeatureExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getSTBuiltinFeatureExpressionAction_0()); }
	()
	{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getSTBuiltinFeatureExpressionAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STBuiltinFeatureExpression__Group__1__Impl
	rule__STBuiltinFeatureExpression__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getFeatureAssignment_1()); }
	(rule__STBuiltinFeatureExpression__FeatureAssignment_1)
	{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getFeatureAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STBuiltinFeatureExpression__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup_2()); }
	(rule__STBuiltinFeatureExpression__Group_2__0)?
	{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STBuiltinFeatureExpression__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STBuiltinFeatureExpression__Group_2__0__Impl
	rule__STBuiltinFeatureExpression__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getCallAssignment_2_0()); }
	(rule__STBuiltinFeatureExpression__CallAssignment_2_0)
	{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getCallAssignment_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STBuiltinFeatureExpression__Group_2__1__Impl
	rule__STBuiltinFeatureExpression__Group_2__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup_2_1()); }
	(rule__STBuiltinFeatureExpression__Group_2_1__0)?
	{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STBuiltinFeatureExpression__Group_2__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getRightParenthesisKeyword_2_2()); }
	RightParenthesis
	{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getRightParenthesisKeyword_2_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STBuiltinFeatureExpression__Group_2_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STBuiltinFeatureExpression__Group_2_1__0__Impl
	rule__STBuiltinFeatureExpression__Group_2_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersAssignment_2_1_0()); }
	(rule__STBuiltinFeatureExpression__ParametersAssignment_2_1_0)
	{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersAssignment_2_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STBuiltinFeatureExpression__Group_2_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup_2_1_1()); }
	(rule__STBuiltinFeatureExpression__Group_2_1_1__0)*
	{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getGroup_2_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STBuiltinFeatureExpression__Group_2_1_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STBuiltinFeatureExpression__Group_2_1_1__0__Impl
	rule__STBuiltinFeatureExpression__Group_2_1_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2_1_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getCommaKeyword_2_1_1_0()); }
	Comma
	{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getCommaKeyword_2_1_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2_1_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STBuiltinFeatureExpression__Group_2_1_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__Group_2_1_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersAssignment_2_1_1_1()); }
	(rule__STBuiltinFeatureExpression__ParametersAssignment_2_1_1_1)
	{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersAssignment_2_1_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STMultibitPartialExpression__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMultibitPartialExpression__Group__0__Impl
	rule__STMultibitPartialExpression__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0()); }
	()
	{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getSTMultibitPartialExpressionAction_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMultibitPartialExpression__Group__1__Impl
	rule__STMultibitPartialExpression__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierAssignment_1()); }
	(rule__STMultibitPartialExpression__SpecifierAssignment_1)?
	{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMultibitPartialExpression__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getAlternatives_2()); }
	(rule__STMultibitPartialExpression__Alternatives_2)
	{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getAlternatives_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STMultibitPartialExpression__Group_2_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMultibitPartialExpression__Group_2_1__0__Impl
	rule__STMultibitPartialExpression__Group_2_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Group_2_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0()); }
	LeftParenthesis
	{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getLeftParenthesisKeyword_2_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Group_2_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMultibitPartialExpression__Group_2_1__1__Impl
	rule__STMultibitPartialExpression__Group_2_1__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Group_2_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionAssignment_2_1_1()); }
	(rule__STMultibitPartialExpression__ExpressionAssignment_2_1_1)
	{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionAssignment_2_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Group_2_1__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STMultibitPartialExpression__Group_2_1__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__Group_2_1__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getRightParenthesisKeyword_2_1_2()); }
	RightParenthesis
	{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getRightParenthesisKeyword_2_1_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STNumericLiteral__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STNumericLiteral__Group_0__0__Impl
	rule__STNumericLiteral__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTNumericLiteralAccess().getTypeAssignment_0_0()); }
	(rule__STNumericLiteral__TypeAssignment_0_0)
	{ after(grammarAccess.getSTNumericLiteralAccess().getTypeAssignment_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STNumericLiteral__Group_0__1__Impl
	rule__STNumericLiteral__Group_0__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1()); }
	NumberSign
	{ after(grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_0__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STNumericLiteral__Group_0__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_0__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTNumericLiteralAccess().getValueAssignment_0_2()); }
	(rule__STNumericLiteral__ValueAssignment_0_2)
	{ after(grammarAccess.getSTNumericLiteralAccess().getValueAssignment_0_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STNumericLiteral__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STNumericLiteral__Group_1__0__Impl
	rule__STNumericLiteral__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTNumericLiteralAccess().getGroup_1_0()); }
	(rule__STNumericLiteral__Group_1_0__0)?
	{ after(grammarAccess.getSTNumericLiteralAccess().getGroup_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STNumericLiteral__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTNumericLiteralAccess().getValueAssignment_1_1()); }
	(rule__STNumericLiteral__ValueAssignment_1_1)
	{ after(grammarAccess.getSTNumericLiteralAccess().getValueAssignment_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STNumericLiteral__Group_1_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STNumericLiteral__Group_1_0__0__Impl
	rule__STNumericLiteral__Group_1_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_1_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTNumericLiteralAccess().getTypeAssignment_1_0_0()); }
	(rule__STNumericLiteral__TypeAssignment_1_0_0)
	{ after(grammarAccess.getSTNumericLiteralAccess().getTypeAssignment_1_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_1_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STNumericLiteral__Group_1_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__Group_1_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_1_0_1()); }
	NumberSign
	{ after(grammarAccess.getSTNumericLiteralAccess().getNumberSignKeyword_1_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STDateLiteral__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STDateLiteral__Group__0__Impl
	rule__STDateLiteral__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateLiteral__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTDateLiteralAccess().getTypeAssignment_0()); }
	(rule__STDateLiteral__TypeAssignment_0)
	{ after(grammarAccess.getSTDateLiteralAccess().getTypeAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateLiteral__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STDateLiteral__Group__1__Impl
	rule__STDateLiteral__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateLiteral__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTDateLiteralAccess().getNumberSignKeyword_1()); }
	NumberSign
	{ after(grammarAccess.getSTDateLiteralAccess().getNumberSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateLiteral__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STDateLiteral__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateLiteral__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTDateLiteralAccess().getValueAssignment_2()); }
	(rule__STDateLiteral__ValueAssignment_2)
	{ after(grammarAccess.getSTDateLiteralAccess().getValueAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STTimeLiteral__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTimeLiteral__Group__0__Impl
	rule__STTimeLiteral__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeLiteral__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTimeLiteralAccess().getTypeAssignment_0()); }
	(rule__STTimeLiteral__TypeAssignment_0)
	{ after(grammarAccess.getSTTimeLiteralAccess().getTypeAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeLiteral__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTimeLiteral__Group__1__Impl
	rule__STTimeLiteral__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeLiteral__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTimeLiteralAccess().getNumberSignKeyword_1()); }
	NumberSign
	{ after(grammarAccess.getSTTimeLiteralAccess().getNumberSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeLiteral__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTimeLiteral__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeLiteral__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTimeLiteralAccess().getValueAssignment_2()); }
	(rule__STTimeLiteral__ValueAssignment_2)
	{ after(grammarAccess.getSTTimeLiteralAccess().getValueAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STTimeOfDayLiteral__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTimeOfDayLiteral__Group__0__Impl
	rule__STTimeOfDayLiteral__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeOfDayLiteral__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeAssignment_0()); }
	(rule__STTimeOfDayLiteral__TypeAssignment_0)
	{ after(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeOfDayLiteral__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTimeOfDayLiteral__Group__1__Impl
	rule__STTimeOfDayLiteral__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeOfDayLiteral__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTimeOfDayLiteralAccess().getNumberSignKeyword_1()); }
	NumberSign
	{ after(grammarAccess.getSTTimeOfDayLiteralAccess().getNumberSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeOfDayLiteral__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STTimeOfDayLiteral__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeOfDayLiteral__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTTimeOfDayLiteralAccess().getValueAssignment_2()); }
	(rule__STTimeOfDayLiteral__ValueAssignment_2)
	{ after(grammarAccess.getSTTimeOfDayLiteralAccess().getValueAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STDateAndTimeLiteral__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STDateAndTimeLiteral__Group__0__Impl
	rule__STDateAndTimeLiteral__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateAndTimeLiteral__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeAssignment_0()); }
	(rule__STDateAndTimeLiteral__TypeAssignment_0)
	{ after(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeAssignment_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateAndTimeLiteral__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STDateAndTimeLiteral__Group__1__Impl
	rule__STDateAndTimeLiteral__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateAndTimeLiteral__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTDateAndTimeLiteralAccess().getNumberSignKeyword_1()); }
	NumberSign
	{ after(grammarAccess.getSTDateAndTimeLiteralAccess().getNumberSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateAndTimeLiteral__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STDateAndTimeLiteral__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateAndTimeLiteral__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTDateAndTimeLiteralAccess().getValueAssignment_2()); }
	(rule__STDateAndTimeLiteral__ValueAssignment_2)
	{ after(grammarAccess.getSTDateAndTimeLiteralAccess().getValueAssignment_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStringLiteral__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStringLiteral__Group__0__Impl
	rule__STStringLiteral__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStringLiteral__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStringLiteralAccess().getGroup_0()); }
	(rule__STStringLiteral__Group_0__0)?
	{ after(grammarAccess.getSTStringLiteralAccess().getGroup_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStringLiteral__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStringLiteral__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStringLiteral__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStringLiteralAccess().getValueAssignment_1()); }
	(rule__STStringLiteral__ValueAssignment_1)
	{ after(grammarAccess.getSTStringLiteralAccess().getValueAssignment_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STStringLiteral__Group_0__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStringLiteral__Group_0__0__Impl
	rule__STStringLiteral__Group_0__1
;
finally {
	restoreStackSize(stackSize);
}

rule__STStringLiteral__Group_0__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStringLiteralAccess().getTypeAssignment_0_0()); }
	(rule__STStringLiteral__TypeAssignment_0_0)
	{ after(grammarAccess.getSTStringLiteralAccess().getTypeAssignment_0_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStringLiteral__Group_0__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__STStringLiteral__Group_0__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__STStringLiteral__Group_0__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSTStringLiteralAccess().getNumberSignKeyword_0_1()); }
	NumberSign
	{ after(grammarAccess.getSTStringLiteralAccess().getNumberSignKeyword_0_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__EnumValue__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EnumValue__Group__0__Impl
	rule__EnumValue__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumValue__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumValueAccess().getQualifiedNameParserRuleCall_0()); }
	ruleQualifiedName
	{ after(grammarAccess.getEnumValueAccess().getQualifiedNameParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumValue__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EnumValue__Group__1__Impl
	rule__EnumValue__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumValue__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumValueAccess().getNumberSignKeyword_1()); }
	NumberSign
	{ after(grammarAccess.getEnumValueAccess().getNumberSignKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumValue__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__EnumValue__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__EnumValue__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getEnumValueAccess().getIDTerminalRuleCall_2()); }
	RULE_ID
	{ after(grammarAccess.getEnumValueAccess().getIDTerminalRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__QualifiedName__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__QualifiedName__Group__0__Impl
	rule__QualifiedName__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifiedName__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); }
	RULE_ID
	{ after(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifiedName__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__QualifiedName__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifiedName__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifiedNameAccess().getGroup_1()); }
	(rule__QualifiedName__Group_1__0)*
	{ after(grammarAccess.getQualifiedNameAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__QualifiedName__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__QualifiedName__Group_1__0__Impl
	rule__QualifiedName__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifiedName__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifiedNameAccess().getColonColonKeyword_1_0()); }
	ColonColon
	{ after(grammarAccess.getQualifiedNameAccess().getColonColonKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifiedName__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__QualifiedName__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifiedName__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); }
	RULE_ID
	{ after(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__QualifiedNameWithWildcard__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__QualifiedNameWithWildcard__Group__0__Impl
	rule__QualifiedNameWithWildcard__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifiedNameWithWildcard__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0()); }
	ruleQualifiedName
	{ after(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifiedNameWithWildcard__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__QualifiedNameWithWildcard__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__QualifiedNameWithWildcard__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getQualifiedNameWithWildcardAccess().getColonColonAsteriskKeyword_1()); }
	(ColonColonAsterisk)?
	{ after(grammarAccess.getQualifiedNameWithWildcardAccess().getColonColonAsteriskKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Number__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Number__Group__0__Impl
	rule__Number__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Number__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNumberAccess().getINTTerminalRuleCall_0()); }
	RULE_INT
	{ after(grammarAccess.getNumberAccess().getINTTerminalRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Number__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Number__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Number__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNumberAccess().getGroup_1()); }
	(rule__Number__Group_1__0)?
	{ after(grammarAccess.getNumberAccess().getGroup_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Number__Group_1__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Number__Group_1__0__Impl
	rule__Number__Group_1__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Number__Group_1__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNumberAccess().getFullStopKeyword_1_0()); }
	FullStop
	{ after(grammarAccess.getNumberAccess().getFullStopKeyword_1_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Number__Group_1__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Number__Group_1__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Number__Group_1__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getNumberAccess().getAlternatives_1_1()); }
	(rule__Number__Alternatives_1_1)
	{ after(grammarAccess.getNumberAccess().getAlternatives_1_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SignedNumber__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SignedNumber__Group__0__Impl
	rule__SignedNumber__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SignedNumber__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSignedNumberAccess().getAlternatives_0()); }
	(rule__SignedNumber__Alternatives_0)
	{ after(grammarAccess.getSignedNumberAccess().getAlternatives_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SignedNumber__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SignedNumber__Group__1__Impl
	rule__SignedNumber__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__SignedNumber__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSignedNumberAccess().getINTTerminalRuleCall_1()); }
	RULE_INT
	{ after(grammarAccess.getSignedNumberAccess().getINTTerminalRuleCall_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SignedNumber__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SignedNumber__Group__2__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SignedNumber__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSignedNumberAccess().getGroup_2()); }
	(rule__SignedNumber__Group_2__0)?
	{ after(grammarAccess.getSignedNumberAccess().getGroup_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__SignedNumber__Group_2__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SignedNumber__Group_2__0__Impl
	rule__SignedNumber__Group_2__1
;
finally {
	restoreStackSize(stackSize);
}

rule__SignedNumber__Group_2__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSignedNumberAccess().getFullStopKeyword_2_0()); }
	FullStop
	{ after(grammarAccess.getSignedNumberAccess().getFullStopKeyword_2_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__SignedNumber__Group_2__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__SignedNumber__Group_2__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__SignedNumber__Group_2__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getSignedNumberAccess().getAlternatives_2_1()); }
	(rule__SignedNumber__Alternatives_2_1)
	{ after(grammarAccess.getSignedNumberAccess().getAlternatives_2_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Time__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Time__Group__0__Impl
	rule__Time__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Time__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeAccess().getAlternatives_0()); }
	(rule__Time__Alternatives_0)?
	{ after(grammarAccess.getTimeAccess().getAlternatives_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Time__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Time__Group__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Time__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeAccess().getTIME_VALUETerminalRuleCall_1()); }
	RULE_TIME_VALUE
	{ after(grammarAccess.getTimeAccess().getTIME_VALUETerminalRuleCall_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__Date__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Date__Group__0__Impl
	rule__Date__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__Date__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAccess().getINTTerminalRuleCall_0()); }
	RULE_INT
	{ after(grammarAccess.getDateAccess().getINTTerminalRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Date__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Date__Group__1__Impl
	rule__Date__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__Date__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAccess().getHyphenMinusKeyword_1()); }
	HyphenMinus
	{ after(grammarAccess.getDateAccess().getHyphenMinusKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Date__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Date__Group__2__Impl
	rule__Date__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__Date__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAccess().getINTTerminalRuleCall_2()); }
	RULE_INT
	{ after(grammarAccess.getDateAccess().getINTTerminalRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Date__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Date__Group__3__Impl
	rule__Date__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__Date__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAccess().getHyphenMinusKeyword_3()); }
	HyphenMinus
	{ after(grammarAccess.getDateAccess().getHyphenMinusKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__Date__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__Date__Group__4__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__Date__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAccess().getINTTerminalRuleCall_4()); }
	RULE_INT
	{ after(grammarAccess.getDateAccess().getINTTerminalRuleCall_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__DateAndTime__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__0__Impl
	rule__DateAndTime__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_0()); }
	RULE_INT
	{ after(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__1__Impl
	rule__DateAndTime__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_1()); }
	HyphenMinus
	{ after(grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__2__Impl
	rule__DateAndTime__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_2()); }
	RULE_INT
	{ after(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__3__Impl
	rule__DateAndTime__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_3()); }
	HyphenMinus
	{ after(grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__4__Impl
	rule__DateAndTime__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_4()); }
	RULE_INT
	{ after(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__5__Impl
	rule__DateAndTime__Group__6
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_5()); }
	HyphenMinus
	{ after(grammarAccess.getDateAndTimeAccess().getHyphenMinusKeyword_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__6
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__6__Impl
	rule__DateAndTime__Group__7
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__6__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_6()); }
	RULE_INT
	{ after(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_6()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__7
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__7__Impl
	rule__DateAndTime__Group__8
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__7__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getColonKeyword_7()); }
	Colon
	{ after(grammarAccess.getDateAndTimeAccess().getColonKeyword_7()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__8
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__8__Impl
	rule__DateAndTime__Group__9
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__8__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_8()); }
	RULE_INT
	{ after(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_8()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__9
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__9__Impl
	rule__DateAndTime__Group__10
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__9__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getColonKeyword_9()); }
	Colon
	{ after(grammarAccess.getDateAndTimeAccess().getColonKeyword_9()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__10
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__10__Impl
	rule__DateAndTime__Group__11
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__10__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10()); }
	RULE_INT
	{ after(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_10()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__11
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group__11__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group__11__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getGroup_11()); }
	(rule__DateAndTime__Group_11__0)?
	{ after(grammarAccess.getDateAndTimeAccess().getGroup_11()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__DateAndTime__Group_11__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group_11__0__Impl
	rule__DateAndTime__Group_11__1
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group_11__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getFullStopKeyword_11_0()); }
	FullStop
	{ after(grammarAccess.getDateAndTimeAccess().getFullStopKeyword_11_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group_11__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__DateAndTime__Group_11__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__DateAndTime__Group_11__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_11_1()); }
	RULE_INT
	{ after(grammarAccess.getDateAndTimeAccess().getINTTerminalRuleCall_11_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TimeOfDay__Group__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TimeOfDay__Group__0__Impl
	rule__TimeOfDay__Group__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0()); }
	RULE_INT
	{ after(grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TimeOfDay__Group__1__Impl
	rule__TimeOfDay__Group__2
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeOfDayAccess().getColonKeyword_1()); }
	Colon
	{ after(grammarAccess.getTimeOfDayAccess().getColonKeyword_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__2
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TimeOfDay__Group__2__Impl
	rule__TimeOfDay__Group__3
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__2__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_2()); }
	RULE_INT
	{ after(grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_2()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__3
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TimeOfDay__Group__3__Impl
	rule__TimeOfDay__Group__4
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__3__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeOfDayAccess().getColonKeyword_3()); }
	Colon
	{ after(grammarAccess.getTimeOfDayAccess().getColonKeyword_3()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__4
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TimeOfDay__Group__4__Impl
	rule__TimeOfDay__Group__5
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__4__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4()); }
	RULE_INT
	{ after(grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_4()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__5
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TimeOfDay__Group__5__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group__5__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeOfDayAccess().getGroup_5()); }
	(rule__TimeOfDay__Group_5__0)?
	{ after(grammarAccess.getTimeOfDayAccess().getGroup_5()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__TimeOfDay__Group_5__0
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TimeOfDay__Group_5__0__Impl
	rule__TimeOfDay__Group_5__1
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group_5__0__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeOfDayAccess().getFullStopKeyword_5_0()); }
	FullStop
	{ after(grammarAccess.getTimeOfDayAccess().getFullStopKeyword_5_0()); }
)
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group_5__1
	@init {
		int stackSize = keepStackSize();
	}
:
	rule__TimeOfDay__Group_5__1__Impl
;
finally {
	restoreStackSize(stackSize);
}

rule__TimeOfDay__Group_5__1__Impl
	@init {
		int stackSize = keepStackSize();
	}
:
(
	{ before(grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_5_1()); }
	RULE_INT
	{ after(grammarAccess.getTimeOfDayAccess().getINTTerminalRuleCall_5_1()); }
)
;
finally {
	restoreStackSize(stackSize);
}


rule__STCoreSource__StatementsAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCoreSourceAccess().getStatementsSTStatementParserRuleCall_1_0()); }
		ruleSTStatement
		{ after(grammarAccess.getSTCoreSourceAccess().getStatementsSTStatementParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STExpressionSource__ExpressionAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTExpressionSourceAccess().getExpressionSTExpressionParserRuleCall_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTExpressionSourceAccess().getExpressionSTExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STInitializerExpressionSource__InitializerExpressionAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTInitializerExpressionSourceAccess().getInitializerExpressionSTInitializerExpressionParserRuleCall_1_0()); }
		ruleSTInitializerExpression
		{ after(grammarAccess.getSTInitializerExpressionSourceAccess().getInitializerExpressionSTInitializerExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__NameAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getNameIDTerminalRuleCall_1_0()); }
		RULE_ID
		{ after(grammarAccess.getSTVarDeclarationAccess().getNameIDTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__LocatedAtAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0()); }
		(
			{ before(grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementIDTerminalRuleCall_2_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementIDTerminalRuleCall_2_1_0_1()); }
		)
		{ after(grammarAccess.getSTVarDeclarationAccess().getLocatedAtINamedElementCrossReference_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__ArrayAssignment_4_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getArrayARRAYKeyword_4_0_0()); }
		(
			{ before(grammarAccess.getSTVarDeclarationAccess().getArrayARRAYKeyword_4_0_0()); }
			ARRAY
			{ after(grammarAccess.getSTVarDeclarationAccess().getArrayARRAYKeyword_4_0_0()); }
		)
		{ after(grammarAccess.getSTVarDeclarationAccess().getArrayARRAYKeyword_4_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__RangesAssignment_4_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__RangesAssignment_4_1_0_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTVarDeclarationAccess().getRangesSTExpressionParserRuleCall_4_1_0_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__CountAssignment_4_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_1_0()); }
		(
			{ before(grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_1_0()); }
			Asterisk
			{ after(grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_1_0()); }
		)
		{ after(grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__CountAssignment_4_1_1_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_2_1_0()); }
		(
			{ before(grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_2_1_0()); }
			Asterisk
			{ after(grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_2_1_0()); }
		)
		{ after(grammarAccess.getSTVarDeclarationAccess().getCountAsteriskKeyword_4_1_1_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__TypeAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementCrossReference_5_0()); }
		(
			{ before(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementSTAnyTypeParserRuleCall_5_0_1()); }
			ruleSTAnyType
			{ after(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementSTAnyTypeParserRuleCall_5_0_1()); }
		)
		{ after(grammarAccess.getSTVarDeclarationAccess().getTypeINamedElementCrossReference_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__MaxLengthAssignment_6_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTVarDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_6_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__DefaultValueAssignment_7_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getDefaultValueSTInitializerExpressionParserRuleCall_7_1_0()); }
		ruleSTInitializerExpression
		{ after(grammarAccess.getSTVarDeclarationAccess().getDefaultValueSTInitializerExpressionParserRuleCall_7_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STVarDeclaration__PragmaAssignment_8
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTVarDeclarationAccess().getPragmaSTPragmaParserRuleCall_8_0()); }
		ruleSTPragma
		{ after(grammarAccess.getSTVarDeclarationAccess().getPragmaSTPragmaParserRuleCall_8_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__ArrayAssignment_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTypeDeclarationAccess().getArrayARRAYKeyword_1_0_0()); }
		(
			{ before(grammarAccess.getSTTypeDeclarationAccess().getArrayARRAYKeyword_1_0_0()); }
			ARRAY
			{ after(grammarAccess.getSTTypeDeclarationAccess().getArrayARRAYKeyword_1_0_0()); }
		)
		{ after(grammarAccess.getSTTypeDeclarationAccess().getArrayARRAYKeyword_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__RangesAssignment_1_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__RangesAssignment_1_1_0_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_2_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTTypeDeclarationAccess().getRangesSTExpressionParserRuleCall_1_1_0_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__CountAssignment_1_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_1_0()); }
		(
			{ before(grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_1_0()); }
			Asterisk
			{ after(grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_1_0()); }
		)
		{ after(grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__CountAssignment_1_1_1_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_2_1_0()); }
		(
			{ before(grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_2_1_0()); }
			Asterisk
			{ after(grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_2_1_0()); }
		)
		{ after(grammarAccess.getSTTypeDeclarationAccess().getCountAsteriskKeyword_1_1_1_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__TypeAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTypeDeclarationAccess().getTypeINamedElementCrossReference_2_0()); }
		(
			{ before(grammarAccess.getSTTypeDeclarationAccess().getTypeINamedElementSTAnyTypeParserRuleCall_2_0_1()); }
			ruleSTAnyType
			{ after(grammarAccess.getSTTypeDeclarationAccess().getTypeINamedElementSTAnyTypeParserRuleCall_2_0_1()); }
		)
		{ after(grammarAccess.getSTTypeDeclarationAccess().getTypeINamedElementCrossReference_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTypeDeclaration__MaxLengthAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTypeDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_3_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTTypeDeclarationAccess().getMaxLengthSTExpressionParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STElementaryInitializerExpression__ValueAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTElementaryInitializerExpressionAccess().getValueSTExpressionParserRuleCall_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTElementaryInitializerExpressionAccess().getValueSTExpressionParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__ValuesAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_1_0()); }
		ruleSTArrayInitElement
		{ after(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STArrayInitializerExpression__ValuesAssignment_2_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_2_1_0()); }
		ruleSTArrayInitElement
		{ after(grammarAccess.getSTArrayInitializerExpressionAccess().getValuesSTArrayInitElementParserRuleCall_2_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STSingleArrayInitElement__InitExpressionAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTSingleArrayInitElementAccess().getInitExpressionSTInitializerExpressionParserRuleCall_0()); }
		ruleSTInitializerExpression
		{ after(grammarAccess.getSTSingleArrayInitElementAccess().getInitExpressionSTInitializerExpressionParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__RepetitionsAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getRepetitionsINTTerminalRuleCall_0_0()); }
		RULE_INT
		{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getRepetitionsINTTerminalRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__InitExpressionsAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_2_0()); }
		ruleSTInitializerExpression
		{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatArrayInitElement__InitExpressionsAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_3_1_0()); }
		ruleSTInitializerExpression
		{ after(grammarAccess.getSTRepeatArrayInitElementAccess().getInitExpressionsSTInitializerExpressionParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__TypeAssignment_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTStructInitializerExpressionAccess().getTypeStructuredTypeCrossReference_0_0_0()); }
		(
			{ before(grammarAccess.getSTStructInitializerExpressionAccess().getTypeStructuredTypeQualifiedNameParserRuleCall_0_0_0_1()); }
			ruleQualifiedName
			{ after(grammarAccess.getSTStructInitializerExpressionAccess().getTypeStructuredTypeQualifiedNameParserRuleCall_0_0_0_1()); }
		)
		{ after(grammarAccess.getSTStructInitializerExpressionAccess().getTypeStructuredTypeCrossReference_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__ValuesAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_2_0()); }
		ruleSTStructInitElement
		{ after(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitializerExpression__ValuesAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_3_1_0()); }
		ruleSTStructInitElement
		{ after(grammarAccess.getSTStructInitializerExpressionAccess().getValuesSTStructInitElementParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitElement__VariableAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTStructInitElementAccess().getVariableINamedElementCrossReference_0_0()); }
		(
			{ before(grammarAccess.getSTStructInitElementAccess().getVariableINamedElementSTFeatureNameParserRuleCall_0_0_1()); }
			ruleSTFeatureName
			{ after(grammarAccess.getSTStructInitElementAccess().getVariableINamedElementSTFeatureNameParserRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getSTStructInitElementAccess().getVariableINamedElementCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStructInitElement__ValueAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTStructInitElementAccess().getValueSTInitializerExpressionParserRuleCall_2_0()); }
		ruleSTInitializerExpression
		{ after(grammarAccess.getSTStructInitElementAccess().getValueSTInitializerExpressionParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__AttributesAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTPragmaAccess().getAttributesSTAttributeParserRuleCall_2_0()); }
		ruleSTAttribute
		{ after(grammarAccess.getSTPragmaAccess().getAttributesSTAttributeParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPragma__AttributesAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTPragmaAccess().getAttributesSTAttributeParserRuleCall_3_1_0()); }
		ruleSTAttribute
		{ after(grammarAccess.getSTPragmaAccess().getAttributesSTAttributeParserRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAttribute__DeclarationAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAttributeAccess().getDeclarationAttributeDeclarationCrossReference_0_0()); }
		(
			{ before(grammarAccess.getSTAttributeAccess().getDeclarationAttributeDeclarationSTAttributeNameParserRuleCall_0_0_1()); }
			ruleSTAttributeName
			{ after(grammarAccess.getSTAttributeAccess().getDeclarationAttributeDeclarationSTAttributeNameParserRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getSTAttributeAccess().getDeclarationAttributeDeclarationCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAttribute__ValueAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAttributeAccess().getValueSTInitializerExpressionParserRuleCall_2_0()); }
		ruleSTInitializerExpression
		{ after(grammarAccess.getSTAttributeAccess().getValueSTInitializerExpressionParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAssignment__RightAssignment_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAssignmentAccess().getRightSTAssignmentParserRuleCall_1_2_0()); }
		ruleSTAssignment
		{ after(grammarAccess.getSTAssignmentAccess().getRightSTAssignmentParserRuleCall_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallUnnamedArgument__ArgumentAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCallUnnamedArgumentAccess().getArgumentSTExpressionParserRuleCall_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTCallUnnamedArgumentAccess().getArgumentSTExpressionParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedInputArgument__ParameterAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCallNamedInputArgumentAccess().getParameterINamedElementCrossReference_0_0()); }
		(
			{ before(grammarAccess.getSTCallNamedInputArgumentAccess().getParameterINamedElementIDTerminalRuleCall_0_0_1()); }
			RULE_ID
			{ after(grammarAccess.getSTCallNamedInputArgumentAccess().getParameterINamedElementIDTerminalRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getSTCallNamedInputArgumentAccess().getParameterINamedElementCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedInputArgument__ArgumentAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCallNamedInputArgumentAccess().getArgumentSTExpressionParserRuleCall_2_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTCallNamedInputArgumentAccess().getArgumentSTExpressionParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedOutputArgument__NotAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCallNamedOutputArgumentAccess().getNotNOTKeyword_0_0()); }
		(
			{ before(grammarAccess.getSTCallNamedOutputArgumentAccess().getNotNOTKeyword_0_0()); }
			NOT
			{ after(grammarAccess.getSTCallNamedOutputArgumentAccess().getNotNOTKeyword_0_0()); }
		)
		{ after(grammarAccess.getSTCallNamedOutputArgumentAccess().getNotNOTKeyword_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedOutputArgument__ParameterAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterINamedElementCrossReference_1_0()); }
		(
			{ before(grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterINamedElementIDTerminalRuleCall_1_0_1()); }
			RULE_ID
			{ after(grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterINamedElementIDTerminalRuleCall_1_0_1()); }
		)
		{ after(grammarAccess.getSTCallNamedOutputArgumentAccess().getParameterINamedElementCrossReference_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCallNamedOutputArgument__ArgumentAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCallNamedOutputArgumentAccess().getArgumentSTExpressionParserRuleCall_3_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTCallNamedOutputArgumentAccess().getArgumentSTExpressionParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__ConditionAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTIfStatementAccess().getConditionSTExpressionParserRuleCall_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTIfStatementAccess().getConditionSTExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__StatementsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTIfStatementAccess().getStatementsSTStatementParserRuleCall_3_0()); }
		ruleSTStatement
		{ after(grammarAccess.getSTIfStatementAccess().getStatementsSTStatementParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__ElseifsAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTIfStatementAccess().getElseifsSTElseIfPartParserRuleCall_4_0()); }
		ruleSTElseIfPart
		{ after(grammarAccess.getSTIfStatementAccess().getElseifsSTElseIfPartParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STIfStatement__ElseAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTIfStatementAccess().getElseSTElsePartParserRuleCall_5_0()); }
		ruleSTElsePart
		{ after(grammarAccess.getSTIfStatementAccess().getElseSTElsePartParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STElseIfPart__ConditionAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTElseIfPartAccess().getConditionSTExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STElseIfPart__StatementsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTElseIfPartAccess().getStatementsSTStatementParserRuleCall_3_0()); }
		ruleSTStatement
		{ after(grammarAccess.getSTElseIfPartAccess().getStatementsSTStatementParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__SelectorAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCaseStatementAccess().getSelectorSTExpressionParserRuleCall_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTCaseStatementAccess().getSelectorSTExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__CasesAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0()); }
		ruleSTCaseCases
		{ after(grammarAccess.getSTCaseStatementAccess().getCasesSTCaseCasesParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseStatement__ElseAssignment_4
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0()); }
		ruleSTElsePart
		{ after(grammarAccess.getSTCaseStatementAccess().getElseSTElsePartParserRuleCall_4_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__ConditionsAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__ConditionsAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTCaseCasesAccess().getConditionsSTExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STCaseCases__StatementsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTCaseCasesAccess().getStatementsSTStatementParserRuleCall_3_0()); }
		ruleSTStatement
		{ after(grammarAccess.getSTCaseCasesAccess().getStatementsSTStatementParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STElsePart__StatementsAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTElsePartAccess().getStatementsSTStatementParserRuleCall_2_0()); }
		ruleSTStatement
		{ after(grammarAccess.getSTElsePartAccess().getStatementsSTStatementParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__VariableAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTForStatementAccess().getVariableSTExpressionParserRuleCall_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTForStatementAccess().getVariableSTExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__FromAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTForStatementAccess().getFromSTExpressionParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__ToAssignment_5
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTForStatementAccess().getToSTExpressionParserRuleCall_5_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__ByAssignment_6_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTForStatementAccess().getBySTExpressionParserRuleCall_6_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STForStatement__StatementsAssignment_8
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0()); }
		ruleSTStatement
		{ after(grammarAccess.getSTForStatementAccess().getStatementsSTStatementParserRuleCall_8_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__ConditionAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTWhileStatementAccess().getConditionSTExpressionParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STWhileStatement__StatementsAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0()); }
		ruleSTStatement
		{ after(grammarAccess.getSTWhileStatementAccess().getStatementsSTStatementParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__StatementsAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0()); }
		ruleSTStatement
		{ after(grammarAccess.getSTRepeatStatementAccess().getStatementsSTStatementParserRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STRepeatStatement__ConditionAssignment_3
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTRepeatStatementAccess().getConditionSTExpressionParserRuleCall_3_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__OpAssignment_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTSubrangeExpressionAccess().getOpSubrangeOperatorEnumRuleCall_1_0_1_0()); }
		ruleSubrangeOperator
		{ after(grammarAccess.getSTSubrangeExpressionAccess().getOpSubrangeOperatorEnumRuleCall_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STSubrangeExpression__RightAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0()); }
		ruleSTOrExpression
		{ after(grammarAccess.getSTSubrangeExpressionAccess().getRightSTOrExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__OpAssignment_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTOrExpressionAccess().getOpOrOperatorEnumRuleCall_1_0_1_0()); }
		ruleOrOperator
		{ after(grammarAccess.getSTOrExpressionAccess().getOpOrOperatorEnumRuleCall_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STOrExpression__RightAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0()); }
		ruleSTXorExpression
		{ after(grammarAccess.getSTOrExpressionAccess().getRightSTXorExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__OpAssignment_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTXorExpressionAccess().getOpXorOperatorEnumRuleCall_1_0_1_0()); }
		ruleXorOperator
		{ after(grammarAccess.getSTXorExpressionAccess().getOpXorOperatorEnumRuleCall_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STXorExpression__RightAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0()); }
		ruleSTAndExpression
		{ after(grammarAccess.getSTXorExpressionAccess().getRightSTAndExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__OpAssignment_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAndExpressionAccess().getOpAndOperatorEnumRuleCall_1_0_1_0()); }
		ruleAndOperator
		{ after(grammarAccess.getSTAndExpressionAccess().getOpAndOperatorEnumRuleCall_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAndExpression__RightAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0()); }
		ruleSTEqualityExpression
		{ after(grammarAccess.getSTAndExpressionAccess().getRightSTEqualityExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__OpAssignment_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_1_0()); }
		ruleEqualityOperator
		{ after(grammarAccess.getSTEqualityExpressionAccess().getOpEqualityOperatorEnumRuleCall_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STEqualityExpression__RightAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0()); }
		ruleSTComparisonExpression
		{ after(grammarAccess.getSTEqualityExpressionAccess().getRightSTComparisonExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__OpAssignment_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTComparisonExpressionAccess().getOpCompareOperatorEnumRuleCall_1_0_1_0()); }
		ruleCompareOperator
		{ after(grammarAccess.getSTComparisonExpressionAccess().getOpCompareOperatorEnumRuleCall_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STComparisonExpression__RightAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0()); }
		ruleSTAddSubExpression
		{ after(grammarAccess.getSTComparisonExpressionAccess().getRightSTAddSubExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__OpAssignment_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAddSubExpressionAccess().getOpAddSubOperatorEnumRuleCall_1_0_1_0()); }
		ruleAddSubOperator
		{ after(grammarAccess.getSTAddSubExpressionAccess().getOpAddSubOperatorEnumRuleCall_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAddSubExpression__RightAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0()); }
		ruleSTMulDivModExpression
		{ after(grammarAccess.getSTAddSubExpressionAccess().getRightSTMulDivModExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__OpAssignment_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTMulDivModExpressionAccess().getOpMulDivModOperatorEnumRuleCall_1_0_1_0()); }
		ruleMulDivModOperator
		{ after(grammarAccess.getSTMulDivModExpressionAccess().getOpMulDivModOperatorEnumRuleCall_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMulDivModExpression__RightAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0()); }
		ruleSTPowerExpression
		{ after(grammarAccess.getSTMulDivModExpressionAccess().getRightSTPowerExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__OpAssignment_1_0_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTPowerExpressionAccess().getOpPowerOperatorEnumRuleCall_1_0_1_0()); }
		rulePowerOperator
		{ after(grammarAccess.getSTPowerExpressionAccess().getOpPowerOperatorEnumRuleCall_1_0_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STPowerExpression__RightAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0()); }
		ruleSTUnaryExpression
		{ after(grammarAccess.getSTPowerExpressionAccess().getRightSTUnaryExpressionParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STUnaryExpression__OpAssignment_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_3_1_0()); }
		ruleUnaryOperator
		{ after(grammarAccess.getSTUnaryExpressionAccess().getOpUnaryOperatorEnumRuleCall_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STUnaryExpression__ExpressionAssignment_3_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTUnaryExpressionAccess().getExpressionSTUnaryExpressionParserRuleCall_3_2_0()); }
		ruleSTUnaryExpression
		{ after(grammarAccess.getSTUnaryExpressionAccess().getExpressionSTUnaryExpressionParserRuleCall_3_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__MemberAssignment_1_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAccessExpressionAccess().getMemberAlternatives_1_0_2_0()); }
		(rule__STAccessExpression__MemberAlternatives_1_0_2_0)
		{ after(grammarAccess.getSTAccessExpressionAccess().getMemberAlternatives_1_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__IndexAssignment_1_1_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_2_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STAccessExpression__IndexAssignment_1_1_3_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_3_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTAccessExpressionAccess().getIndexSTExpressionParserRuleCall_1_1_3_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__FeatureAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0()); }
		(
			{ before(grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementSTFeatureNameParserRuleCall_1_0_1()); }
			ruleSTFeatureName
			{ after(grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementSTFeatureNameParserRuleCall_1_0_1()); }
		)
		{ after(grammarAccess.getSTFeatureExpressionAccess().getFeatureINamedElementCrossReference_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__CallAssignment_2_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0()); }
		(
			{ before(grammarAccess.getSTFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0()); }
			LeftParenthesis
			{ after(grammarAccess.getSTFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0()); }
		)
		{ after(grammarAccess.getSTFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__ParametersAssignment_2_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0()); }
		ruleSTCallArgument
		{ after(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STFeatureExpression__ParametersAssignment_2_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0()); }
		ruleSTCallArgument
		{ after(grammarAccess.getSTFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__FeatureAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getFeatureSTBuiltinFeatureEnumRuleCall_1_0()); }
		ruleSTBuiltinFeature
		{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getFeatureSTBuiltinFeatureEnumRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__CallAssignment_2_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0()); }
		(
			{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0()); }
			LeftParenthesis
			{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0()); }
		)
		{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getCallLeftParenthesisKeyword_2_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__ParametersAssignment_2_1_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0()); }
		ruleSTCallArgument
		{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STBuiltinFeatureExpression__ParametersAssignment_2_1_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0()); }
		ruleSTCallArgument
		{ after(grammarAccess.getSTBuiltinFeatureExpressionAccess().getParametersSTCallArgumentParserRuleCall_2_1_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__SpecifierAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0()); }
		ruleSTMultiBitAccessSpecifier
		{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getSpecifierSTMultiBitAccessSpecifierEnumRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__IndexAssignment_2_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getIndexINTTerminalRuleCall_2_0_0()); }
		RULE_INT
		{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getIndexINTTerminalRuleCall_2_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STMultibitPartialExpression__ExpressionAssignment_2_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionSTExpressionParserRuleCall_2_1_1_0()); }
		ruleSTExpression
		{ after(grammarAccess.getSTMultibitPartialExpressionAccess().getExpressionSTExpressionParserRuleCall_2_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__TypeAssignment_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0()); }
		(
			{ before(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeSTNumericLiteralTypeParserRuleCall_0_0_0_1()); }
			ruleSTNumericLiteralType
			{ after(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeSTNumericLiteralTypeParserRuleCall_0_0_0_1()); }
		)
		{ after(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__ValueAssignment_0_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTNumericLiteralAccess().getValueSignedNumericParserRuleCall_0_2_0()); }
		ruleSignedNumeric
		{ after(grammarAccess.getSTNumericLiteralAccess().getValueSignedNumericParserRuleCall_0_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__TypeAssignment_1_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_1_0_0_0()); }
		(
			{ before(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeSTNumericLiteralTypeParserRuleCall_1_0_0_0_1()); }
			ruleSTNumericLiteralType
			{ after(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeSTNumericLiteralTypeParserRuleCall_1_0_0_0_1()); }
		)
		{ after(grammarAccess.getSTNumericLiteralAccess().getTypeDataTypeCrossReference_1_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STNumericLiteral__ValueAssignment_1_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTNumericLiteralAccess().getValueNumericParserRuleCall_1_1_0()); }
		ruleNumeric
		{ after(grammarAccess.getSTNumericLiteralAccess().getValueNumericParserRuleCall_1_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STSignedNumericLiteral__ValueAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTSignedNumericLiteralAccess().getValueSignedNumericParserRuleCall_0()); }
		ruleSignedNumeric
		{ after(grammarAccess.getSTSignedNumericLiteralAccess().getValueSignedNumericParserRuleCall_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateLiteral__TypeAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0()); }
		(
			{ before(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeSTDateLiteralTypeParserRuleCall_0_0_1()); }
			ruleSTDateLiteralType
			{ after(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeSTDateLiteralTypeParserRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getSTDateLiteralAccess().getTypeDataTypeCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateLiteral__ValueAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTDateLiteralAccess().getValueDateParserRuleCall_2_0()); }
		ruleDate
		{ after(grammarAccess.getSTDateLiteralAccess().getValueDateParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeLiteral__TypeAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0()); }
		(
			{ before(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeSTTimeLiteralTypeParserRuleCall_0_0_1()); }
			ruleSTTimeLiteralType
			{ after(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeSTTimeLiteralTypeParserRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getSTTimeLiteralAccess().getTypeDataTypeCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeLiteral__ValueAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTimeLiteralAccess().getValueTimeParserRuleCall_2_0()); }
		ruleTime
		{ after(grammarAccess.getSTTimeLiteralAccess().getValueTimeParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeOfDayLiteral__TypeAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0()); }
		(
			{ before(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeSTTimeOfDayTypeParserRuleCall_0_0_1()); }
			ruleSTTimeOfDayType
			{ after(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeSTTimeOfDayTypeParserRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getSTTimeOfDayLiteralAccess().getTypeDataTypeCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STTimeOfDayLiteral__ValueAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTTimeOfDayLiteralAccess().getValueTimeOfDayParserRuleCall_2_0()); }
		ruleTimeOfDay
		{ after(grammarAccess.getSTTimeOfDayLiteralAccess().getValueTimeOfDayParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateAndTimeLiteral__TypeAssignment_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0()); }
		(
			{ before(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeSTDateAndTimeTypeParserRuleCall_0_0_1()); }
			ruleSTDateAndTimeType
			{ after(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeSTDateAndTimeTypeParserRuleCall_0_0_1()); }
		)
		{ after(grammarAccess.getSTDateAndTimeLiteralAccess().getTypeDataTypeCrossReference_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STDateAndTimeLiteral__ValueAssignment_2
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTDateAndTimeLiteralAccess().getValueDateAndTimeParserRuleCall_2_0()); }
		ruleDateAndTime
		{ after(grammarAccess.getSTDateAndTimeLiteralAccess().getValueDateAndTimeParserRuleCall_2_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStringLiteral__TypeAssignment_0_0
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0()); }
		(
			{ before(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeSTAnyCharsTypeParserRuleCall_0_0_0_1()); }
			ruleSTAnyCharsType
			{ after(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeSTAnyCharsTypeParserRuleCall_0_0_0_1()); }
		)
		{ after(grammarAccess.getSTStringLiteralAccess().getTypeDataTypeCrossReference_0_0_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STStringLiteral__ValueAssignment_1
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTStringLiteralAccess().getValueSTRINGTerminalRuleCall_1_0()); }
		RULE_STRING
		{ after(grammarAccess.getSTStringLiteralAccess().getValueSTRINGTerminalRuleCall_1_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}

rule__STEnumLiteral__ValueAssignment
	@init {
		int stackSize = keepStackSize();
	}
:
	(
		{ before(grammarAccess.getSTEnumLiteralAccess().getValueEnumeratedValueCrossReference_0()); }
		(
			{ before(grammarAccess.getSTEnumLiteralAccess().getValueEnumeratedValueEnumValueParserRuleCall_0_1()); }
			ruleEnumValue
			{ after(grammarAccess.getSTEnumLiteralAccess().getValueEnumeratedValueEnumValueParserRuleCall_0_1()); }
		)
		{ after(grammarAccess.getSTEnumLiteralAccess().getValueEnumeratedValueCrossReference_0()); }
	)
;
finally {
	restoreStackSize(stackSize);
}
