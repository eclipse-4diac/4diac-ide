/*******************************************************************************
 * Copyright (c) 2022, 2023 Primetals Technologies Austria GmbH
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Hesam Rezaee, Martin Jobst
 *       - initial API and implementation and/or initial documentation
 *******************************************************************************/
lexer grammar InternalGlobalConstantsLexer;

@header {
package org.eclipse.fordiac.ide.globalconstantseditor.parser.antlr.lexer;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

END_GLOBALCONSTANTS : ('E'|'e')('N'|'n')('D'|'d')'_'('G'|'g')('L'|'l')('O'|'o')('B'|'b')('A'|'a')('L'|'l')('C'|'c')('O'|'o')('N'|'n')('S'|'s')('T'|'t')('A'|'a')('N'|'n')('T'|'t')('S'|'s');

END_FUNCTION_BLOCK : ('E'|'e')('N'|'n')('D'|'d')'_'('F'|'f')('U'|'u')('N'|'n')('C'|'c')('T'|'t')('I'|'i')('O'|'o')('N'|'n')'_'('B'|'b')('L'|'l')('O'|'o')('C'|'c')('K'|'k');

END_CONFIGURATION : ('E'|'e')('N'|'n')('D'|'d')'_'('C'|'c')('O'|'o')('N'|'n')('F'|'f')('I'|'i')('G'|'g')('U'|'u')('R'|'r')('A'|'a')('T'|'t')('I'|'i')('O'|'o')('N'|'n');

GLOBALCONSTANTS : ('G'|'g')('L'|'l')('O'|'o')('B'|'b')('A'|'a')('L'|'l')('C'|'c')('O'|'o')('N'|'n')('S'|'s')('T'|'t')('A'|'a')('N'|'n')('T'|'t')('S'|'s');

END_TRANSITION : ('E'|'e')('N'|'n')('D'|'d')'_'('T'|'t')('R'|'r')('A'|'a')('N'|'n')('S'|'s')('I'|'i')('T'|'t')('I'|'i')('O'|'o')('N'|'n');

FUNCTION_BLOCK : ('F'|'f')('U'|'u')('N'|'n')('C'|'c')('T'|'t')('I'|'i')('O'|'o')('N'|'n')'_'('B'|'b')('L'|'l')('O'|'o')('C'|'c')('K'|'k');

LDATE_AND_TIME : ('L'|'l')('D'|'d')('A'|'a')('T'|'t')('E'|'e')'_'('A'|'a')('N'|'n')('D'|'d')'_'('T'|'t')('I'|'i')('M'|'m')('E'|'e');

CONFIGURATION : ('C'|'c')('O'|'o')('N'|'n')('F'|'f')('I'|'i')('G'|'g')('U'|'u')('R'|'r')('A'|'a')('T'|'t')('I'|'i')('O'|'o')('N'|'n');

DATE_AND_TIME : ('D'|'d')('A'|'a')('T'|'t')('E'|'e')'_'('A'|'a')('N'|'n')('D'|'d')'_'('T'|'t')('I'|'i')('M'|'m')('E'|'e');

END_INTERFACE : ('E'|'e')('N'|'n')('D'|'d')'_'('I'|'i')('N'|'n')('T'|'t')('E'|'e')('R'|'r')('F'|'f')('A'|'a')('C'|'c')('E'|'e');

END_NAMESPACE : ('E'|'e')('N'|'n')('D'|'d')'_'('N'|'n')('A'|'a')('M'|'m')('E'|'e')('S'|'s')('P'|'p')('A'|'a')('C'|'c')('E'|'e');

END_FUNCTION : ('E'|'e')('N'|'n')('D'|'d')'_'('F'|'f')('U'|'u')('N'|'n')('C'|'c')('T'|'t')('I'|'i')('O'|'o')('N'|'n');

END_RESOURCE : ('E'|'e')('N'|'n')('D'|'d')'_'('R'|'r')('E'|'e')('S'|'s')('O'|'o')('U'|'u')('R'|'r')('C'|'c')('E'|'e');

INITIAL_STEP : ('I'|'i')('N'|'n')('I'|'i')('T'|'t')('I'|'i')('A'|'a')('L'|'l')'_'('S'|'s')('T'|'t')('E'|'e')('P'|'p');

LTIME_OF_DAY : ('L'|'l')('T'|'t')('I'|'i')('M'|'m')('E'|'e')'_'('O'|'o')('F'|'f')'_'('D'|'d')('A'|'a')('Y'|'y');

VAR_EXTERNAL : ('V'|'v')('A'|'a')('R'|'r')'_'('E'|'e')('X'|'x')('T'|'t')('E'|'e')('R'|'r')('N'|'n')('A'|'a')('L'|'l');

END_PROGRAM : ('E'|'e')('N'|'n')('D'|'d')'_'('P'|'p')('R'|'r')('O'|'o')('G'|'g')('R'|'r')('A'|'a')('M'|'m');

TIME_OF_DAY : ('T'|'t')('I'|'i')('M'|'m')('E'|'e')'_'('O'|'o')('F'|'f')'_'('D'|'d')('A'|'a')('Y'|'y');

END_ACTION : ('E'|'e')('N'|'n')('D'|'d')'_'('A'|'a')('C'|'c')('T'|'t')('I'|'i')('O'|'o')('N'|'n');

END_METHOD : ('E'|'e')('N'|'n')('D'|'d')'_'('M'|'m')('E'|'e')('T'|'t')('H'|'h')('O'|'o')('D'|'d');

END_REPEAT : ('E'|'e')('N'|'n')('D'|'d')'_'('R'|'r')('E'|'e')('P'|'p')('E'|'e')('A'|'a')('T'|'t');

END_STRUCT : ('E'|'e')('N'|'n')('D'|'d')'_'('S'|'s')('T'|'t')('R'|'r')('U'|'u')('C'|'c')('T'|'t');

IMPLEMENTS : ('I'|'i')('M'|'m')('P'|'p')('L'|'l')('E'|'e')('M'|'m')('E'|'e')('N'|'n')('T'|'t')('S'|'s');

NON_RETAIN : ('N'|'n')('O'|'o')('N'|'n')'_'('R'|'r')('E'|'e')('T'|'t')('A'|'a')('I'|'i')('N'|'n');

READ_WRITE : ('R'|'r')('E'|'e')('A'|'a')('D'|'d')'_'('W'|'w')('R'|'r')('I'|'i')('T'|'t')('E'|'e');

TRANSITION : ('T'|'t')('R'|'r')('A'|'a')('N'|'n')('S'|'s')('I'|'i')('T'|'t')('I'|'i')('O'|'o')('N'|'n');

VAR_ACCESS : ('V'|'v')('A'|'a')('R'|'r')'_'('A'|'a')('C'|'c')('C'|'c')('E'|'e')('S'|'s')('S'|'s');

VAR_CONFIG : ('V'|'v')('A'|'a')('R'|'r')'_'('C'|'c')('O'|'o')('N'|'n')('F'|'f')('I'|'i')('G'|'g');

VAR_GLOBAL : ('V'|'v')('A'|'a')('R'|'r')'_'('G'|'g')('L'|'l')('O'|'o')('B'|'b')('A'|'a')('L'|'l');

VAR_IN_OUT : ('V'|'v')('A'|'a')('R'|'r')'_'('I'|'i')('N'|'n')'_'('O'|'o')('U'|'u')('T'|'t');

VAR_OUTPUT : ('V'|'v')('A'|'a')('R'|'r')'_'('O'|'o')('U'|'u')('T'|'t')('P'|'p')('U'|'u')('T'|'t');

END_CLASS : ('E'|'e')('N'|'n')('D'|'d')'_'('C'|'c')('L'|'l')('A'|'a')('S'|'s')('S'|'s');

END_WHILE : ('E'|'e')('N'|'n')('D'|'d')'_'('W'|'w')('H'|'h')('I'|'i')('L'|'l')('E'|'e');

INTERFACE : ('I'|'i')('N'|'n')('T'|'t')('E'|'e')('R'|'r')('F'|'f')('A'|'a')('C'|'c')('E'|'e');

NAMESPACE : ('N'|'n')('A'|'a')('M'|'m')('E'|'e')('S'|'s')('P'|'p')('A'|'a')('C'|'c')('E'|'e');

PROTECTED : ('P'|'p')('R'|'r')('O'|'o')('T'|'t')('E'|'e')('C'|'c')('T'|'t')('E'|'e')('D'|'d');

READ_ONLY : ('R'|'r')('E'|'e')('A'|'a')('D'|'d')'_'('O'|'o')('N'|'n')('L'|'l')('Y'|'y');

VAR_INPUT : ('V'|'v')('A'|'a')('R'|'r')'_'('I'|'i')('N'|'n')('P'|'p')('U'|'u')('T'|'t');

ABSTRACT : ('A'|'a')('B'|'b')('S'|'s')('T'|'t')('R'|'r')('A'|'a')('C'|'c')('T'|'t');

CONSTANT : ('C'|'c')('O'|'o')('N'|'n')('S'|'s')('T'|'t')('A'|'a')('N'|'n')('T'|'t');

CONTINUE : ('C'|'c')('O'|'o')('N'|'n')('T'|'t')('I'|'i')('N'|'n')('U'|'u')('E'|'e');

END_CASE : ('E'|'e')('N'|'n')('D'|'d')'_'('C'|'c')('A'|'a')('S'|'s')('E'|'e');

END_STEP : ('E'|'e')('N'|'n')('D'|'d')'_'('S'|'s')('T'|'t')('E'|'e')('P'|'p');

END_TYPE : ('E'|'e')('N'|'n')('D'|'d')'_'('T'|'t')('Y'|'y')('P'|'p')('E'|'e');

FUNCTION : ('F'|'f')('U'|'u')('N'|'n')('C'|'c')('T'|'t')('I'|'i')('O'|'o')('N'|'n');

INTERNAL : ('I'|'i')('N'|'n')('T'|'t')('E'|'e')('R'|'r')('N'|'n')('A'|'a')('L'|'l');

INTERVAL : ('I'|'i')('N'|'n')('T'|'t')('E'|'e')('R'|'r')('V'|'v')('A'|'a')('L'|'l');

OVERRIDE : ('O'|'o')('V'|'v')('E'|'e')('R'|'r')('R'|'r')('I'|'i')('D'|'d')('E'|'e');

PRIORITY : ('P'|'p')('R'|'r')('I'|'i')('O'|'o')('R'|'r')('I'|'i')('T'|'t')('Y'|'y');

RESOURCE : ('R'|'r')('E'|'e')('S'|'s')('O'|'o')('U'|'u')('R'|'r')('C'|'c')('E'|'e');

VAR_TEMP : ('V'|'v')('A'|'a')('R'|'r')'_'('T'|'t')('E'|'e')('M'|'m')('P'|'p');

END_FOR : ('E'|'e')('N'|'n')('D'|'d')'_'('F'|'f')('O'|'o')('R'|'r');

END_VAR : ('E'|'e')('N'|'n')('D'|'d')'_'('V'|'v')('A'|'a')('R'|'r');

EXTENDS : ('E'|'e')('X'|'x')('T'|'t')('E'|'e')('N'|'n')('D'|'d')('S'|'s');

INTERAL : ('I'|'i')('N'|'n')('T'|'t')('E'|'e')('R'|'r')('A'|'a')('L'|'l');

OVERLAP : ('O'|'o')('V'|'v')('E'|'e')('R'|'r')('L'|'l')('A'|'a')('P'|'p');

PACKAGE : ('P'|'p')('A'|'a')('C'|'c')('K'|'k')('A'|'a')('G'|'g')('E'|'e');

PRIVATE : ('P'|'p')('R'|'r')('I'|'i')('V'|'v')('A'|'a')('T'|'t')('E'|'e');

PROGRAM : ('P'|'p')('R'|'r')('O'|'o')('G'|'g')('R'|'r')('A'|'a')('M'|'m');

WSTRING : ('W'|'w')('S'|'s')('T'|'t')('R'|'r')('I'|'i')('N'|'n')('G'|'g');

ACTION : ('A'|'a')('C'|'c')('T'|'t')('I'|'i')('O'|'o')('N'|'n');

END_IF : ('E'|'e')('N'|'n')('D'|'d')'_'('I'|'i')('F'|'f');

IMPORT : ('I'|'i')('M'|'m')('P'|'p')('O'|'o')('R'|'r')('T'|'t');

METHOD : ('M'|'m')('E'|'e')('T'|'t')('H'|'h')('O'|'o')('D'|'d');

PUBLIC : ('P'|'p')('U'|'u')('B'|'b')('L'|'l')('I'|'i')('C'|'c');

REF_TO : ('R'|'r')('E'|'e')('F'|'f')'_'('T'|'t')('O'|'o');

REPEAT : ('R'|'r')('E'|'e')('P'|'p')('E'|'e')('A'|'a')('T'|'t');

RETAIN : ('R'|'r')('E'|'e')('T'|'t')('A'|'a')('I'|'i')('N'|'n');

RETURN : ('R'|'r')('E'|'e')('T'|'t')('U'|'u')('R'|'r')('N'|'n');

SINGLE : ('S'|'s')('I'|'i')('N'|'n')('G'|'g')('L'|'l')('E'|'e');

STRING : ('S'|'s')('T'|'t')('R'|'r')('I'|'i')('N'|'n')('G'|'g');

STRUCT : ('S'|'s')('T'|'t')('R'|'r')('U'|'u')('C'|'c')('T'|'t');

ARRAY : ('A'|'a')('R'|'r')('R'|'r')('A'|'a')('Y'|'y');

CLASS : ('C'|'c')('L'|'l')('A'|'a')('S'|'s')('S'|'s');

DWORD : ('D'|'d')('W'|'w')('O'|'o')('R'|'r')('D'|'d');

ELSIF : ('E'|'e')('L'|'l')('S'|'s')('I'|'i')('F'|'f');

FALSE : ('F'|'f')('A'|'a')('L'|'l')('S'|'s')('E'|'e');

FINAL : ('F'|'f')('I'|'i')('N'|'n')('A'|'a')('L'|'l');

LDATE : ('L'|'l')('D'|'d')('A'|'a')('T'|'t')('E'|'e');

LREAL : ('L'|'l')('R'|'r')('E'|'e')('A'|'a')('L'|'l');

LTIME : ('L'|'l')('T'|'t')('I'|'i')('M'|'m')('E'|'e');

LWORD : ('L'|'l')('W'|'w')('O'|'o')('R'|'r')('D'|'d');

SUPER : ('S'|'s')('U'|'u')('P'|'p')('E'|'e')('R'|'r');

UDINT : ('U'|'u')('D'|'d')('I'|'i')('N'|'n')('T'|'t');

ULINT : ('U'|'u')('L'|'l')('I'|'i')('N'|'n')('T'|'t');

UNTIL : ('U'|'u')('N'|'n')('T'|'t')('I'|'i')('L'|'l');

USING : ('U'|'u')('S'|'s')('I'|'i')('N'|'n')('G'|'g');

USINT : ('U'|'u')('S'|'s')('I'|'i')('N'|'n')('T'|'t');

WCHAR : ('W'|'w')('C'|'c')('H'|'h')('A'|'a')('R'|'r');

WHILE : ('W'|'w')('H'|'h')('I'|'i')('L'|'l')('E'|'e');

BOOL : ('B'|'b')('O'|'o')('O'|'o')('L'|'l');

BYTE : ('B'|'b')('Y'|'y')('T'|'t')('E'|'e');

CASE : ('C'|'c')('A'|'a')('S'|'s')('E'|'e');

CHAR : ('C'|'c')('H'|'h')('A'|'a')('R'|'r');

DATE : ('D'|'d')('A'|'a')('T'|'t')('E'|'e');

DINT : ('D'|'d')('I'|'i')('N'|'n')('T'|'t');

ELSE : ('E'|'e')('L'|'l')('S'|'s')('E'|'e');

EXIT : ('E'|'e')('X'|'x')('I'|'i')('T'|'t');

FROM : ('F'|'f')('R'|'r')('O'|'o')('M'|'m');

LINT : ('L'|'l')('I'|'i')('N'|'n')('T'|'t');

LTOD : ('L'|'l')('T'|'t')('O'|'o')('D'|'d');

NULL : ('N'|'n')('U'|'u')('L'|'l')('L'|'l');

REAL : ('R'|'r')('E'|'e')('A'|'a')('L'|'l');

SINT : ('S'|'s')('I'|'i')('N'|'n')('T'|'t');

STEP : ('S'|'s')('T'|'t')('E'|'e')('P'|'p');

TASK : ('T'|'t')('A'|'a')('S'|'s')('K'|'k');

THEN : ('T'|'t')('H'|'h')('E'|'e')('N'|'n');

THIS : ('T'|'t')('H'|'h')('I'|'i')('S'|'s');

TIME : ('T'|'t')('I'|'i')('M'|'m')('E'|'e');

TRUE : ('T'|'t')('R'|'r')('U'|'u')('E'|'e');

TYPE : ('T'|'t')('Y'|'y')('P'|'p')('E'|'e');

UINT : ('U'|'u')('I'|'i')('N'|'n')('T'|'t');

WITH : ('W'|'w')('I'|'i')('T'|'t')('H'|'h');

WORD : ('W'|'w')('O'|'o')('R'|'r')('D'|'d');

ColonColonAsterisk : ':'':''*';

AND : ('A'|'a')('N'|'n')('D'|'d');

FOR : ('F'|'f')('O'|'o')('R'|'r');

INT : ('I'|'i')('N'|'n')('T'|'t');

LDT : ('L'|'l')('D'|'d')('T'|'t');

MOD : ('M'|'m')('O'|'o')('D'|'d');

NOT : ('N'|'n')('O'|'o')('T'|'t');

REF : ('R'|'r')('E'|'e')('F'|'f');

TOD : ('T'|'t')('O'|'o')('D'|'d');

VAR : ('V'|'v')('A'|'a')('R'|'r');

XOR : ('X'|'x')('O'|'o')('R'|'r');

B : '%'('B'|'b');

D_1 : '%'('D'|'d');

L : '%'('L'|'l');

W : '%'('W'|'w');

X : '%'('X'|'x');

AsteriskAsterisk : '*''*';

FullStopFullStop : '.''.';

ColonColon : ':'':';

ColonEqualsSign : ':''=';

LessThanSignEqualsSign : '<''=';

LessThanSignGreaterThanSign : '<''>';

EqualsSignGreaterThanSign : '=''>';

GreaterThanSignEqualsSign : '>''=';

AT : ('A'|'a')('T'|'t');

BY : ('B'|'b')('Y'|'y');

DO : ('D'|'d')('O'|'o');

DT : ('D'|'d')('T'|'t');

IF : ('I'|'i')('F'|'f');

LD : ('L'|'l')('D'|'d');

LT : ('L'|'l')('T'|'t');

OF : ('O'|'o')('F'|'f');

ON : ('O'|'o')('N'|'n');

OR : ('O'|'o')('R'|'r');

TO : ('T'|'t')('O'|'o');

NumberSign : '#';

Ampersand : '&';

LeftParenthesis : '(';

RightParenthesis : ')';

Asterisk : '*';

PlusSign : '+';

Comma : ',';

HyphenMinus : '-';

FullStop : '.';

Solidus : '/';

Colon : ':';

Semicolon : ';';

LessThanSign : '<';

EqualsSign : '=';

GreaterThanSign : '>';

D : ('D'|'d');

T : ('T'|'t');

LeftSquareBracket : '[';

RightSquareBracket : ']';

LeftCurlyBracket : '{';

RightCurlyBracket : '}';

fragment RULE_HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F'|'_');

RULE_NON_DECIMAL : ('2#'|'8#'|'16#') RULE_HEX_DIGIT+;

RULE_INT : '0'..'9' ('0'..'9'|'_')*;

RULE_DECIMAL : RULE_INT (('e'|'E') ('+'|'-')? RULE_INT)?;

RULE_TIME_VALUE : (RULE_TIME_PART '_'?)+;

fragment RULE_TIME_PART : RULE_INT (RULE_TIME_DAYS|RULE_TIME_HOURS|RULE_TIME_MINUTES|RULE_TIME_SECONDS|RULE_TIME_MILLIS|RULE_TIME_MICROS|RULE_TIME_NANOS);

fragment RULE_TIME_DAYS : ('D'|'d');

fragment RULE_TIME_HOURS : ('H'|'h');

fragment RULE_TIME_MINUTES : ('M'|'m');

fragment RULE_TIME_SECONDS : ('S'|'s');

fragment RULE_TIME_MILLIS : ('M'|'m') ('S'|'s');

fragment RULE_TIME_MICROS : ('U'|'u') ('S'|'s');

fragment RULE_TIME_NANOS : ('N'|'n') ('S'|'s');

RULE_ID : '^'? ('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

RULE_STRING : ('"' ('$' .|~(('$'|'"')))* '"'|'\'' ('$' .|~(('$'|'\'')))* '\'');

RULE_ML_COMMENT : ('/*' ( options {greedy=false;} : . )*'*/'|'(*' ( options {greedy=false;} : . )*'*)');

RULE_SL_COMMENT : '//' ~(('\n'|'\r'))* ('\r'? '\n')?;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;
