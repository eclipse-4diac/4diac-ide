package org.eclipse.fordiac.ide.model.structuredtext.ide.contentassist.antlr.internal;

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
import org.eclipse.fordiac.ide.model.structuredtext.services.StructuredTextGrammarAccess;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalStructuredTextParser extends AbstractInternalContentAssistParser {
	public static final String[] tokenNames = new String[] { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME",
			"DATE_AND_TIME", "LTIME_OF_DAY", "TIME_OF_DAY", "END_REPEAT", "END_WHILE", "CONSTANT", "CONTINUE",
			"END_CASE", "END_FOR", "END_VAR", "WSTRING", "END_IF", "REPEAT", "RETURN", "STRING", "DWORD", "ELSIF",
			"FALSE", "LDATE", "LREAL", "LTIME", "LWORD", "SUPER", "UDINT", "ULINT", "UNTIL", "USINT", "WCHAR", "WHILE",
			"BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "LINT", "LTOD", "REAL", "SINT", "THEN",
			"TIME", "TRUE", "UINT", "WORD", "B", "D", "W", "X", "AND", "FOR", "INT", "LDT", "MOD", "NOT", "TOD", "VAR",
			"XOR", "AsteriskAsterisk", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign",
			"EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LD", "LT", "OF",
			"OR", "TO", "Ms", "Ns", "Us", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk",
			"PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign",
			"EqualsSign", "GreaterThanSign", "E", "T", "LeftSquareBracket", "RightSquareBracket", "KW__", "D_1", "H",
			"M", "S", "RULE_LETTER", "RULE_DIGIT", "RULE_BIT", "RULE_OCTAL_DIGIT", "RULE_HEX_DIGIT", "RULE_ID",
			"RULE_BINARY_INT", "RULE_OCTAL_INT", "RULE_HEX_INT", "RULE_UNSIGNED_INT", "RULE_S_BYTE_CHAR_VALUE",
			"RULE_S_BYTE_CHAR_STR", "RULE_D_BYTE_CHAR_VALUE", "RULE_D_BYTE_CHAR_STR", "RULE_COMMON_CHAR_VALUE",
			"RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER" };
	public static final int LWORD = 26;
	public static final int LessThanSignGreaterThanSign = 67;
	public static final int EqualsSignGreaterThanSign = 68;
	public static final int VAR = 62;
	public static final int END_IF = 16;
	public static final int ULINT = 29;
	public static final int END_CASE = 12;
	public static final int LessThanSign = 95;
	public static final int RULE_BIT = 109;
	public static final int LeftParenthesis = 85;
	public static final int BYTE = 35;
	public static final int ELSE = 40;
	public static final int IF = 74;
	public static final int LINT = 42;
	public static final int GreaterThanSign = 97;
	public static final int WORD = 50;
	public static final int DATE_AND_TIME = 5;
	public static final int RULE_ID = 112;
	public static final int TOD = 61;
	public static final int RULE_DIGIT = 108;
	public static final int DINT = 39;
	public static final int UDINT = 28;
	public static final int CASE = 36;
	public static final int GreaterThanSignEqualsSign = 69;
	public static final int AT = 70;
	public static final int RULE_OCTAL_INT = 114;
	public static final int PlusSign = 88;
	public static final int END_FOR = 13;
	public static final int RULE_ML_COMMENT = 122;
	public static final int THEN = 46;
	public static final int XOR = 63;
	public static final int LeftSquareBracket = 100;
	public static final int EXIT = 41;
	public static final int TIME_OF_DAY = 7;
	public static final int B = 51;
	public static final int LDATE_AND_TIME = 4;
	public static final int REPEAT = 17;
	public static final int D = 52;
	public static final int E = 98;
	public static final int H = 104;
	public static final int CHAR = 37;
	public static final int RULE_D_BYTE_CHAR_STR = 120;
	public static final int RULE_UNSIGNED_INT = 116;
	public static final int M = 105;
	public static final int LTIME = 25;
	public static final int Comma = 89;
	public static final int HyphenMinus = 90;
	public static final int S = 106;
	public static final int T = 99;
	public static final int W = 53;
	public static final int BY = 71;
	public static final int X = 54;
	public static final int ELSIF = 21;
	public static final int END_REPEAT = 8;
	public static final int LessThanSignEqualsSign = 66;
	public static final int Solidus = 92;
	public static final int RULE_OCTAL_DIGIT = 110;
	public static final int RULE_HEX_INT = 115;
	public static final int FullStop = 91;
	public static final int CONSTANT = 10;
	public static final int KW__ = 102;
	public static final int CONTINUE = 11;
	public static final int Semicolon = 94;
	public static final int RULE_LETTER = 107;
	public static final int LD = 75;
	public static final int STRING = 19;
	public static final int RULE_HEX_DIGIT = 111;
	public static final int TO = 79;
	public static final int UINT = 49;
	public static final int LTOD = 43;
	public static final int LT = 76;
	public static final int DO = 72;
	public static final int WSTRING = 15;
	public static final int DT = 73;
	public static final int END_VAR = 14;
	public static final int Ampersand = 84;
	public static final int RightSquareBracket = 101;
	public static final int RULE_BINARY_INT = 113;
	public static final int USINT = 31;
	public static final int DWORD = 20;
	public static final int FOR = 56;
	public static final int RightParenthesis = 86;
	public static final int TRUE = 48;
	public static final int ColonEqualsSign = 65;
	public static final int END_WHILE = 9;
	public static final int DATE = 38;
	public static final int NOT = 60;
	public static final int LDATE = 23;
	public static final int AND = 55;
	public static final int NumberSign = 83;
	public static final int REAL = 44;
	public static final int AsteriskAsterisk = 64;
	public static final int SINT = 45;
	public static final int LTIME_OF_DAY = 6;
	public static final int Us = 82;
	public static final int LREAL = 24;
	public static final int WCHAR = 32;
	public static final int Ms = 80;
	public static final int INT = 57;
	public static final int RULE_SL_COMMENT = 123;
	public static final int RETURN = 18;
	public static final int EqualsSign = 96;
	public static final int RULE_COMMON_CHAR_VALUE = 121;
	public static final int OF = 77;
	public static final int Colon = 93;
	public static final int EOF = -1;
	public static final int LDT = 58;
	public static final int Asterisk = 87;
	public static final int SUPER = 27;
	public static final int MOD = 59;
	public static final int OR = 78;
	public static final int RULE_S_BYTE_CHAR_VALUE = 117;
	public static final int Ns = 81;
	public static final int RULE_WS = 124;
	public static final int TIME = 47;
	public static final int RULE_ANY_OTHER = 125;
	public static final int UNTIL = 30;
	public static final int RULE_S_BYTE_CHAR_STR = 118;
	public static final int BOOL = 34;
	public static final int D_1 = 103;
	public static final int FALSE = 22;
	public static final int WHILE = 33;
	public static final int RULE_D_BYTE_CHAR_VALUE = 119;

	// delegates
	// delegators

	public InternalStructuredTextParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}

	public InternalStructuredTextParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);

	}

	public String[] getTokenNames() {
		return InternalStructuredTextParser.tokenNames;
	}

	public String getGrammarFileName() {
		return "InternalStructuredTextParser.g";
	}

	private StructuredTextGrammarAccess grammarAccess;
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
		tokenNameToValue.put("D_1", "'D'");
		tokenNameToValue.put("E", "'E'");
		tokenNameToValue.put("T", "'T'");
		tokenNameToValue.put("LeftSquareBracket", "'['");
		tokenNameToValue.put("RightSquareBracket", "']'");
		tokenNameToValue.put("KW__", "'_'");
		tokenNameToValue.put("D_1", "'d'");
		tokenNameToValue.put("H", "'h'");
		tokenNameToValue.put("M", "'m'");
		tokenNameToValue.put("S", "'s'");
		tokenNameToValue.put("AsteriskAsterisk", "'**'");
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
		tokenNameToValue.put("OR", "'OR'");
		tokenNameToValue.put("TO", "'TO'");
		tokenNameToValue.put("Ms", "'ms'");
		tokenNameToValue.put("Ns", "'ns'");
		tokenNameToValue.put("Us", "'us'");
		tokenNameToValue.put("B", "'.%B'");
		tokenNameToValue.put("D", "'.%D'");
		tokenNameToValue.put("W", "'.%W'");
		tokenNameToValue.put("X", "'.%X'");
		tokenNameToValue.put("AND", "'AND'");
		tokenNameToValue.put("FOR", "'FOR'");
		tokenNameToValue.put("INT", "'INT'");
		tokenNameToValue.put("LDT", "'LDT'");
		tokenNameToValue.put("MOD", "'MOD'");
		tokenNameToValue.put("NOT", "'NOT'");
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
		tokenNameToValue.put("LINT", "'LINT'");
		tokenNameToValue.put("LTOD", "'LTOD'");
		tokenNameToValue.put("REAL", "'REAL'");
		tokenNameToValue.put("SINT", "'SINT'");
		tokenNameToValue.put("THEN", "'THEN'");
		tokenNameToValue.put("TIME", "'TIME'");
		tokenNameToValue.put("TRUE", "'TRUE'");
		tokenNameToValue.put("UINT", "'UINT'");
		tokenNameToValue.put("WORD", "'WORD'");
		tokenNameToValue.put("DWORD", "'DWORD'");
		tokenNameToValue.put("ELSIF", "'ELSIF'");
		tokenNameToValue.put("FALSE", "'FALSE'");
		tokenNameToValue.put("LDATE", "'LDATE'");
		tokenNameToValue.put("LREAL", "'LREAL'");
		tokenNameToValue.put("LTIME", "'LTIME'");
		tokenNameToValue.put("LWORD", "'LWORD'");
		tokenNameToValue.put("SUPER", "'SUPER'");
		tokenNameToValue.put("UDINT", "'UDINT'");
		tokenNameToValue.put("ULINT", "'ULINT'");
		tokenNameToValue.put("UNTIL", "'UNTIL'");
		tokenNameToValue.put("USINT", "'USINT'");
		tokenNameToValue.put("WCHAR", "'WCHAR'");
		tokenNameToValue.put("WHILE", "'WHILE'");
		tokenNameToValue.put("END_IF", "'END_IF'");
		tokenNameToValue.put("REPEAT", "'REPEAT'");
		tokenNameToValue.put("RETURN", "'RETURN'");
		tokenNameToValue.put("STRING", "'STRING'");
		tokenNameToValue.put("END_FOR", "'END_FOR'");
		tokenNameToValue.put("END_VAR", "'END_VAR'");
		tokenNameToValue.put("WSTRING", "'WSTRING'");
		tokenNameToValue.put("CONSTANT", "'CONSTANT'");
		tokenNameToValue.put("CONTINUE", "'CONTINUE'");
		tokenNameToValue.put("END_CASE", "'END_CASE'");
		tokenNameToValue.put("END_WHILE", "'END_WHILE'");
		tokenNameToValue.put("END_REPEAT", "'END_REPEAT'");
		tokenNameToValue.put("TIME_OF_DAY", "'TIME_OF_DAY'");
		tokenNameToValue.put("LTIME_OF_DAY", "'LTIME_OF_DAY'");
		tokenNameToValue.put("DATE_AND_TIME", "'DATE_AND_TIME'");
		tokenNameToValue.put("LDATE_AND_TIME", "'LDATE_AND_TIME'");
	}

	public void setGrammarAccess(StructuredTextGrammarAccess grammarAccess) {
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

	// $ANTLR start "entryRuleStructuredTextAlgorithm"
	// InternalStructuredTextParser.g:159:1: entryRuleStructuredTextAlgorithm :
	// ruleStructuredTextAlgorithm EOF ;
	public final void entryRuleStructuredTextAlgorithm() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:160:1: ( ruleStructuredTextAlgorithm EOF )
			// InternalStructuredTextParser.g:161:1: ruleStructuredTextAlgorithm EOF
			{
				before(grammarAccess.getStructuredTextAlgorithmRule());
				pushFollow(FOLLOW_1);
				ruleStructuredTextAlgorithm();

				state._fsp--;

				after(grammarAccess.getStructuredTextAlgorithmRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleStructuredTextAlgorithm"

	// $ANTLR start "ruleStructuredTextAlgorithm"
	// InternalStructuredTextParser.g:168:1: ruleStructuredTextAlgorithm : ( (
	// rule__StructuredTextAlgorithm__Group__0 ) ) ;
	public final void ruleStructuredTextAlgorithm() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:172:2: ( ( (
			// rule__StructuredTextAlgorithm__Group__0 ) ) )
			// InternalStructuredTextParser.g:173:2: ( (
			// rule__StructuredTextAlgorithm__Group__0 ) )
			{
				// InternalStructuredTextParser.g:173:2: ( (
				// rule__StructuredTextAlgorithm__Group__0 ) )
				// InternalStructuredTextParser.g:174:3: (
				// rule__StructuredTextAlgorithm__Group__0 )
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess().getGroup());
					// InternalStructuredTextParser.g:175:3: (
					// rule__StructuredTextAlgorithm__Group__0 )
					// InternalStructuredTextParser.g:175:4: rule__StructuredTextAlgorithm__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__StructuredTextAlgorithm__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getStructuredTextAlgorithmAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleStructuredTextAlgorithm"

	// $ANTLR start "entryRuleVar_Decl_Init"
	// InternalStructuredTextParser.g:184:1: entryRuleVar_Decl_Init :
	// ruleVar_Decl_Init EOF ;
	public final void entryRuleVar_Decl_Init() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:185:1: ( ruleVar_Decl_Init EOF )
			// InternalStructuredTextParser.g:186:1: ruleVar_Decl_Init EOF
			{
				before(grammarAccess.getVar_Decl_InitRule());
				pushFollow(FOLLOW_1);
				ruleVar_Decl_Init();

				state._fsp--;

				after(grammarAccess.getVar_Decl_InitRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleVar_Decl_Init"

	// $ANTLR start "ruleVar_Decl_Init"
	// InternalStructuredTextParser.g:193:1: ruleVar_Decl_Init : ( (
	// rule__Var_Decl_Init__Alternatives ) ) ;
	public final void ruleVar_Decl_Init() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:197:2: ( ( ( rule__Var_Decl_Init__Alternatives
			// ) ) )
			// InternalStructuredTextParser.g:198:2: ( ( rule__Var_Decl_Init__Alternatives )
			// )
			{
				// InternalStructuredTextParser.g:198:2: ( ( rule__Var_Decl_Init__Alternatives )
				// )
				// InternalStructuredTextParser.g:199:3: ( rule__Var_Decl_Init__Alternatives )
				{
					before(grammarAccess.getVar_Decl_InitAccess().getAlternatives());
					// InternalStructuredTextParser.g:200:3: ( rule__Var_Decl_Init__Alternatives )
					// InternalStructuredTextParser.g:200:4: rule__Var_Decl_Init__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Init__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_InitAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleVar_Decl_Init"

	// $ANTLR start "entryRuleVar_Decl_Local"
	// InternalStructuredTextParser.g:209:1: entryRuleVar_Decl_Local :
	// ruleVar_Decl_Local EOF ;
	public final void entryRuleVar_Decl_Local() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:210:1: ( ruleVar_Decl_Local EOF )
			// InternalStructuredTextParser.g:211:1: ruleVar_Decl_Local EOF
			{
				before(grammarAccess.getVar_Decl_LocalRule());
				pushFollow(FOLLOW_1);
				ruleVar_Decl_Local();

				state._fsp--;

				after(grammarAccess.getVar_Decl_LocalRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleVar_Decl_Local"

	// $ANTLR start "ruleVar_Decl_Local"
	// InternalStructuredTextParser.g:218:1: ruleVar_Decl_Local : ( (
	// rule__Var_Decl_Local__Group__0 ) ) ;
	public final void ruleVar_Decl_Local() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:222:2: ( ( ( rule__Var_Decl_Local__Group__0 )
			// ) )
			// InternalStructuredTextParser.g:223:2: ( ( rule__Var_Decl_Local__Group__0 ) )
			{
				// InternalStructuredTextParser.g:223:2: ( ( rule__Var_Decl_Local__Group__0 ) )
				// InternalStructuredTextParser.g:224:3: ( rule__Var_Decl_Local__Group__0 )
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getGroup());
					// InternalStructuredTextParser.g:225:3: ( rule__Var_Decl_Local__Group__0 )
					// InternalStructuredTextParser.g:225:4: rule__Var_Decl_Local__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Local__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleVar_Decl_Local"

	// $ANTLR start "entryRuleVar_Decl_Located"
	// InternalStructuredTextParser.g:234:1: entryRuleVar_Decl_Located :
	// ruleVar_Decl_Located EOF ;
	public final void entryRuleVar_Decl_Located() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:235:1: ( ruleVar_Decl_Located EOF )
			// InternalStructuredTextParser.g:236:1: ruleVar_Decl_Located EOF
			{
				before(grammarAccess.getVar_Decl_LocatedRule());
				pushFollow(FOLLOW_1);
				ruleVar_Decl_Located();

				state._fsp--;

				after(grammarAccess.getVar_Decl_LocatedRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleVar_Decl_Located"

	// $ANTLR start "ruleVar_Decl_Located"
	// InternalStructuredTextParser.g:243:1: ruleVar_Decl_Located : ( (
	// rule__Var_Decl_Located__Group__0 ) ) ;
	public final void ruleVar_Decl_Located() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:247:2: ( ( ( rule__Var_Decl_Located__Group__0
			// ) ) )
			// InternalStructuredTextParser.g:248:2: ( ( rule__Var_Decl_Located__Group__0 )
			// )
			{
				// InternalStructuredTextParser.g:248:2: ( ( rule__Var_Decl_Located__Group__0 )
				// )
				// InternalStructuredTextParser.g:249:3: ( rule__Var_Decl_Located__Group__0 )
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getGroup());
					// InternalStructuredTextParser.g:250:3: ( rule__Var_Decl_Located__Group__0 )
					// InternalStructuredTextParser.g:250:4: rule__Var_Decl_Located__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Located__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleVar_Decl_Located"

	// $ANTLR start "entryRuleStmt_List"
	// InternalStructuredTextParser.g:259:1: entryRuleStmt_List : ruleStmt_List EOF
	// ;
	public final void entryRuleStmt_List() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:260:1: ( ruleStmt_List EOF )
			// InternalStructuredTextParser.g:261:1: ruleStmt_List EOF
			{
				before(grammarAccess.getStmt_ListRule());
				pushFollow(FOLLOW_1);
				ruleStmt_List();

				state._fsp--;

				after(grammarAccess.getStmt_ListRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleStmt_List"

	// $ANTLR start "ruleStmt_List"
	// InternalStructuredTextParser.g:268:1: ruleStmt_List : ( (
	// rule__Stmt_List__Group__0 ) ) ;
	public final void ruleStmt_List() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:272:2: ( ( ( rule__Stmt_List__Group__0 ) ) )
			// InternalStructuredTextParser.g:273:2: ( ( rule__Stmt_List__Group__0 ) )
			{
				// InternalStructuredTextParser.g:273:2: ( ( rule__Stmt_List__Group__0 ) )
				// InternalStructuredTextParser.g:274:3: ( rule__Stmt_List__Group__0 )
				{
					before(grammarAccess.getStmt_ListAccess().getGroup());
					// InternalStructuredTextParser.g:275:3: ( rule__Stmt_List__Group__0 )
					// InternalStructuredTextParser.g:275:4: rule__Stmt_List__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Stmt_List__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getStmt_ListAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleStmt_List"

	// $ANTLR start "entryRuleStmt"
	// InternalStructuredTextParser.g:284:1: entryRuleStmt : ruleStmt EOF ;
	public final void entryRuleStmt() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:285:1: ( ruleStmt EOF )
			// InternalStructuredTextParser.g:286:1: ruleStmt EOF
			{
				before(grammarAccess.getStmtRule());
				pushFollow(FOLLOW_1);
				ruleStmt();

				state._fsp--;

				after(grammarAccess.getStmtRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleStmt"

	// $ANTLR start "ruleStmt"
	// InternalStructuredTextParser.g:293:1: ruleStmt : ( ( rule__Stmt__Alternatives
	// ) ) ;
	public final void ruleStmt() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:297:2: ( ( ( rule__Stmt__Alternatives ) ) )
			// InternalStructuredTextParser.g:298:2: ( ( rule__Stmt__Alternatives ) )
			{
				// InternalStructuredTextParser.g:298:2: ( ( rule__Stmt__Alternatives ) )
				// InternalStructuredTextParser.g:299:3: ( rule__Stmt__Alternatives )
				{
					before(grammarAccess.getStmtAccess().getAlternatives());
					// InternalStructuredTextParser.g:300:3: ( rule__Stmt__Alternatives )
					// InternalStructuredTextParser.g:300:4: rule__Stmt__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Stmt__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getStmtAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleStmt"

	// $ANTLR start "entryRuleAssign_Stmt"
	// InternalStructuredTextParser.g:309:1: entryRuleAssign_Stmt : ruleAssign_Stmt
	// EOF ;
	public final void entryRuleAssign_Stmt() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:310:1: ( ruleAssign_Stmt EOF )
			// InternalStructuredTextParser.g:311:1: ruleAssign_Stmt EOF
			{
				before(grammarAccess.getAssign_StmtRule());
				pushFollow(FOLLOW_1);
				ruleAssign_Stmt();

				state._fsp--;

				after(grammarAccess.getAssign_StmtRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleAssign_Stmt"

	// $ANTLR start "ruleAssign_Stmt"
	// InternalStructuredTextParser.g:318:1: ruleAssign_Stmt : ( (
	// rule__Assign_Stmt__Group__0 ) ) ;
	public final void ruleAssign_Stmt() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:322:2: ( ( ( rule__Assign_Stmt__Group__0 ) ) )
			// InternalStructuredTextParser.g:323:2: ( ( rule__Assign_Stmt__Group__0 ) )
			{
				// InternalStructuredTextParser.g:323:2: ( ( rule__Assign_Stmt__Group__0 ) )
				// InternalStructuredTextParser.g:324:3: ( rule__Assign_Stmt__Group__0 )
				{
					before(grammarAccess.getAssign_StmtAccess().getGroup());
					// InternalStructuredTextParser.g:325:3: ( rule__Assign_Stmt__Group__0 )
					// InternalStructuredTextParser.g:325:4: rule__Assign_Stmt__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Assign_Stmt__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getAssign_StmtAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleAssign_Stmt"

	// $ANTLR start "entryRuleSubprog_Ctrl_Stmt"
	// InternalStructuredTextParser.g:334:1: entryRuleSubprog_Ctrl_Stmt :
	// ruleSubprog_Ctrl_Stmt EOF ;
	public final void entryRuleSubprog_Ctrl_Stmt() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:335:1: ( ruleSubprog_Ctrl_Stmt EOF )
			// InternalStructuredTextParser.g:336:1: ruleSubprog_Ctrl_Stmt EOF
			{
				before(grammarAccess.getSubprog_Ctrl_StmtRule());
				pushFollow(FOLLOW_1);
				ruleSubprog_Ctrl_Stmt();

				state._fsp--;

				after(grammarAccess.getSubprog_Ctrl_StmtRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleSubprog_Ctrl_Stmt"

	// $ANTLR start "ruleSubprog_Ctrl_Stmt"
	// InternalStructuredTextParser.g:343:1: ruleSubprog_Ctrl_Stmt : ( (
	// rule__Subprog_Ctrl_Stmt__Alternatives ) ) ;
	public final void ruleSubprog_Ctrl_Stmt() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:347:2: ( ( (
			// rule__Subprog_Ctrl_Stmt__Alternatives ) ) )
			// InternalStructuredTextParser.g:348:2: ( (
			// rule__Subprog_Ctrl_Stmt__Alternatives ) )
			{
				// InternalStructuredTextParser.g:348:2: ( (
				// rule__Subprog_Ctrl_Stmt__Alternatives ) )
				// InternalStructuredTextParser.g:349:3: ( rule__Subprog_Ctrl_Stmt__Alternatives
				// )
				{
					before(grammarAccess.getSubprog_Ctrl_StmtAccess().getAlternatives());
					// InternalStructuredTextParser.g:350:3: ( rule__Subprog_Ctrl_Stmt__Alternatives
					// )
					// InternalStructuredTextParser.g:350:4: rule__Subprog_Ctrl_Stmt__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Subprog_Ctrl_Stmt__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getSubprog_Ctrl_StmtAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleSubprog_Ctrl_Stmt"

	// $ANTLR start "entryRuleSelection_Stmt"
	// InternalStructuredTextParser.g:359:1: entryRuleSelection_Stmt :
	// ruleSelection_Stmt EOF ;
	public final void entryRuleSelection_Stmt() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:360:1: ( ruleSelection_Stmt EOF )
			// InternalStructuredTextParser.g:361:1: ruleSelection_Stmt EOF
			{
				before(grammarAccess.getSelection_StmtRule());
				pushFollow(FOLLOW_1);
				ruleSelection_Stmt();

				state._fsp--;

				after(grammarAccess.getSelection_StmtRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleSelection_Stmt"

	// $ANTLR start "ruleSelection_Stmt"
	// InternalStructuredTextParser.g:368:1: ruleSelection_Stmt : ( (
	// rule__Selection_Stmt__Alternatives ) ) ;
	public final void ruleSelection_Stmt() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:372:2: ( ( (
			// rule__Selection_Stmt__Alternatives ) ) )
			// InternalStructuredTextParser.g:373:2: ( ( rule__Selection_Stmt__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:373:2: ( ( rule__Selection_Stmt__Alternatives
				// ) )
				// InternalStructuredTextParser.g:374:3: ( rule__Selection_Stmt__Alternatives )
				{
					before(grammarAccess.getSelection_StmtAccess().getAlternatives());
					// InternalStructuredTextParser.g:375:3: ( rule__Selection_Stmt__Alternatives )
					// InternalStructuredTextParser.g:375:4: rule__Selection_Stmt__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Selection_Stmt__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getSelection_StmtAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleSelection_Stmt"

	// $ANTLR start "entryRuleIF_Stmt"
	// InternalStructuredTextParser.g:384:1: entryRuleIF_Stmt : ruleIF_Stmt EOF ;
	public final void entryRuleIF_Stmt() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:385:1: ( ruleIF_Stmt EOF )
			// InternalStructuredTextParser.g:386:1: ruleIF_Stmt EOF
			{
				before(grammarAccess.getIF_StmtRule());
				pushFollow(FOLLOW_1);
				ruleIF_Stmt();

				state._fsp--;

				after(grammarAccess.getIF_StmtRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleIF_Stmt"

	// $ANTLR start "ruleIF_Stmt"
	// InternalStructuredTextParser.g:393:1: ruleIF_Stmt : ( (
	// rule__IF_Stmt__Group__0 ) ) ;
	public final void ruleIF_Stmt() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:397:2: ( ( ( rule__IF_Stmt__Group__0 ) ) )
			// InternalStructuredTextParser.g:398:2: ( ( rule__IF_Stmt__Group__0 ) )
			{
				// InternalStructuredTextParser.g:398:2: ( ( rule__IF_Stmt__Group__0 ) )
				// InternalStructuredTextParser.g:399:3: ( rule__IF_Stmt__Group__0 )
				{
					before(grammarAccess.getIF_StmtAccess().getGroup());
					// InternalStructuredTextParser.g:400:3: ( rule__IF_Stmt__Group__0 )
					// InternalStructuredTextParser.g:400:4: rule__IF_Stmt__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__IF_Stmt__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getIF_StmtAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleIF_Stmt"

	// $ANTLR start "entryRuleELSIF_Clause"
	// InternalStructuredTextParser.g:409:1: entryRuleELSIF_Clause :
	// ruleELSIF_Clause EOF ;
	public final void entryRuleELSIF_Clause() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:410:1: ( ruleELSIF_Clause EOF )
			// InternalStructuredTextParser.g:411:1: ruleELSIF_Clause EOF
			{
				before(grammarAccess.getELSIF_ClauseRule());
				pushFollow(FOLLOW_1);
				ruleELSIF_Clause();

				state._fsp--;

				after(grammarAccess.getELSIF_ClauseRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleELSIF_Clause"

	// $ANTLR start "ruleELSIF_Clause"
	// InternalStructuredTextParser.g:418:1: ruleELSIF_Clause : ( (
	// rule__ELSIF_Clause__Group__0 ) ) ;
	public final void ruleELSIF_Clause() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:422:2: ( ( ( rule__ELSIF_Clause__Group__0 ) )
			// )
			// InternalStructuredTextParser.g:423:2: ( ( rule__ELSIF_Clause__Group__0 ) )
			{
				// InternalStructuredTextParser.g:423:2: ( ( rule__ELSIF_Clause__Group__0 ) )
				// InternalStructuredTextParser.g:424:3: ( rule__ELSIF_Clause__Group__0 )
				{
					before(grammarAccess.getELSIF_ClauseAccess().getGroup());
					// InternalStructuredTextParser.g:425:3: ( rule__ELSIF_Clause__Group__0 )
					// InternalStructuredTextParser.g:425:4: rule__ELSIF_Clause__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__ELSIF_Clause__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getELSIF_ClauseAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleELSIF_Clause"

	// $ANTLR start "entryRuleELSE_Clause"
	// InternalStructuredTextParser.g:434:1: entryRuleELSE_Clause : ruleELSE_Clause
	// EOF ;
	public final void entryRuleELSE_Clause() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:435:1: ( ruleELSE_Clause EOF )
			// InternalStructuredTextParser.g:436:1: ruleELSE_Clause EOF
			{
				before(grammarAccess.getELSE_ClauseRule());
				pushFollow(FOLLOW_1);
				ruleELSE_Clause();

				state._fsp--;

				after(grammarAccess.getELSE_ClauseRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleELSE_Clause"

	// $ANTLR start "ruleELSE_Clause"
	// InternalStructuredTextParser.g:443:1: ruleELSE_Clause : ( (
	// rule__ELSE_Clause__Group__0 ) ) ;
	public final void ruleELSE_Clause() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:447:2: ( ( ( rule__ELSE_Clause__Group__0 ) ) )
			// InternalStructuredTextParser.g:448:2: ( ( rule__ELSE_Clause__Group__0 ) )
			{
				// InternalStructuredTextParser.g:448:2: ( ( rule__ELSE_Clause__Group__0 ) )
				// InternalStructuredTextParser.g:449:3: ( rule__ELSE_Clause__Group__0 )
				{
					before(grammarAccess.getELSE_ClauseAccess().getGroup());
					// InternalStructuredTextParser.g:450:3: ( rule__ELSE_Clause__Group__0 )
					// InternalStructuredTextParser.g:450:4: rule__ELSE_Clause__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__ELSE_Clause__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getELSE_ClauseAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleELSE_Clause"

	// $ANTLR start "entryRuleCase_Stmt"
	// InternalStructuredTextParser.g:459:1: entryRuleCase_Stmt : ruleCase_Stmt EOF
	// ;
	public final void entryRuleCase_Stmt() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:460:1: ( ruleCase_Stmt EOF )
			// InternalStructuredTextParser.g:461:1: ruleCase_Stmt EOF
			{
				before(grammarAccess.getCase_StmtRule());
				pushFollow(FOLLOW_1);
				ruleCase_Stmt();

				state._fsp--;

				after(grammarAccess.getCase_StmtRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleCase_Stmt"

	// $ANTLR start "ruleCase_Stmt"
	// InternalStructuredTextParser.g:468:1: ruleCase_Stmt : ( (
	// rule__Case_Stmt__Group__0 ) ) ;
	public final void ruleCase_Stmt() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:472:2: ( ( ( rule__Case_Stmt__Group__0 ) ) )
			// InternalStructuredTextParser.g:473:2: ( ( rule__Case_Stmt__Group__0 ) )
			{
				// InternalStructuredTextParser.g:473:2: ( ( rule__Case_Stmt__Group__0 ) )
				// InternalStructuredTextParser.g:474:3: ( rule__Case_Stmt__Group__0 )
				{
					before(grammarAccess.getCase_StmtAccess().getGroup());
					// InternalStructuredTextParser.g:475:3: ( rule__Case_Stmt__Group__0 )
					// InternalStructuredTextParser.g:475:4: rule__Case_Stmt__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Case_Stmt__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getCase_StmtAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleCase_Stmt"

	// $ANTLR start "entryRuleCase_Selection"
	// InternalStructuredTextParser.g:484:1: entryRuleCase_Selection :
	// ruleCase_Selection EOF ;
	public final void entryRuleCase_Selection() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:485:1: ( ruleCase_Selection EOF )
			// InternalStructuredTextParser.g:486:1: ruleCase_Selection EOF
			{
				before(grammarAccess.getCase_SelectionRule());
				pushFollow(FOLLOW_1);
				ruleCase_Selection();

				state._fsp--;

				after(grammarAccess.getCase_SelectionRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleCase_Selection"

	// $ANTLR start "ruleCase_Selection"
	// InternalStructuredTextParser.g:493:1: ruleCase_Selection : ( (
	// rule__Case_Selection__Group__0 ) ) ;
	public final void ruleCase_Selection() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:497:2: ( ( ( rule__Case_Selection__Group__0 )
			// ) )
			// InternalStructuredTextParser.g:498:2: ( ( rule__Case_Selection__Group__0 ) )
			{
				// InternalStructuredTextParser.g:498:2: ( ( rule__Case_Selection__Group__0 ) )
				// InternalStructuredTextParser.g:499:3: ( rule__Case_Selection__Group__0 )
				{
					before(grammarAccess.getCase_SelectionAccess().getGroup());
					// InternalStructuredTextParser.g:500:3: ( rule__Case_Selection__Group__0 )
					// InternalStructuredTextParser.g:500:4: rule__Case_Selection__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Case_Selection__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getCase_SelectionAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleCase_Selection"

	// $ANTLR start "entryRuleIteration_Stmt"
	// InternalStructuredTextParser.g:509:1: entryRuleIteration_Stmt :
	// ruleIteration_Stmt EOF ;
	public final void entryRuleIteration_Stmt() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:510:1: ( ruleIteration_Stmt EOF )
			// InternalStructuredTextParser.g:511:1: ruleIteration_Stmt EOF
			{
				before(grammarAccess.getIteration_StmtRule());
				pushFollow(FOLLOW_1);
				ruleIteration_Stmt();

				state._fsp--;

				after(grammarAccess.getIteration_StmtRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleIteration_Stmt"

	// $ANTLR start "ruleIteration_Stmt"
	// InternalStructuredTextParser.g:518:1: ruleIteration_Stmt : ( (
	// rule__Iteration_Stmt__Alternatives ) ) ;
	public final void ruleIteration_Stmt() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:522:2: ( ( (
			// rule__Iteration_Stmt__Alternatives ) ) )
			// InternalStructuredTextParser.g:523:2: ( ( rule__Iteration_Stmt__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:523:2: ( ( rule__Iteration_Stmt__Alternatives
				// ) )
				// InternalStructuredTextParser.g:524:3: ( rule__Iteration_Stmt__Alternatives )
				{
					before(grammarAccess.getIteration_StmtAccess().getAlternatives());
					// InternalStructuredTextParser.g:525:3: ( rule__Iteration_Stmt__Alternatives )
					// InternalStructuredTextParser.g:525:4: rule__Iteration_Stmt__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Iteration_Stmt__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getIteration_StmtAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleIteration_Stmt"

	// $ANTLR start "entryRuleFor_Stmt"
	// InternalStructuredTextParser.g:534:1: entryRuleFor_Stmt : ruleFor_Stmt EOF ;
	public final void entryRuleFor_Stmt() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:535:1: ( ruleFor_Stmt EOF )
			// InternalStructuredTextParser.g:536:1: ruleFor_Stmt EOF
			{
				before(grammarAccess.getFor_StmtRule());
				pushFollow(FOLLOW_1);
				ruleFor_Stmt();

				state._fsp--;

				after(grammarAccess.getFor_StmtRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleFor_Stmt"

	// $ANTLR start "ruleFor_Stmt"
	// InternalStructuredTextParser.g:543:1: ruleFor_Stmt : ( (
	// rule__For_Stmt__Group__0 ) ) ;
	public final void ruleFor_Stmt() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:547:2: ( ( ( rule__For_Stmt__Group__0 ) ) )
			// InternalStructuredTextParser.g:548:2: ( ( rule__For_Stmt__Group__0 ) )
			{
				// InternalStructuredTextParser.g:548:2: ( ( rule__For_Stmt__Group__0 ) )
				// InternalStructuredTextParser.g:549:3: ( rule__For_Stmt__Group__0 )
				{
					before(grammarAccess.getFor_StmtAccess().getGroup());
					// InternalStructuredTextParser.g:550:3: ( rule__For_Stmt__Group__0 )
					// InternalStructuredTextParser.g:550:4: rule__For_Stmt__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__For_Stmt__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getFor_StmtAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleFor_Stmt"

	// $ANTLR start "entryRuleWhile_Stmt"
	// InternalStructuredTextParser.g:559:1: entryRuleWhile_Stmt : ruleWhile_Stmt
	// EOF ;
	public final void entryRuleWhile_Stmt() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:560:1: ( ruleWhile_Stmt EOF )
			// InternalStructuredTextParser.g:561:1: ruleWhile_Stmt EOF
			{
				before(grammarAccess.getWhile_StmtRule());
				pushFollow(FOLLOW_1);
				ruleWhile_Stmt();

				state._fsp--;

				after(grammarAccess.getWhile_StmtRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleWhile_Stmt"

	// $ANTLR start "ruleWhile_Stmt"
	// InternalStructuredTextParser.g:568:1: ruleWhile_Stmt : ( (
	// rule__While_Stmt__Group__0 ) ) ;
	public final void ruleWhile_Stmt() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:572:2: ( ( ( rule__While_Stmt__Group__0 ) ) )
			// InternalStructuredTextParser.g:573:2: ( ( rule__While_Stmt__Group__0 ) )
			{
				// InternalStructuredTextParser.g:573:2: ( ( rule__While_Stmt__Group__0 ) )
				// InternalStructuredTextParser.g:574:3: ( rule__While_Stmt__Group__0 )
				{
					before(grammarAccess.getWhile_StmtAccess().getGroup());
					// InternalStructuredTextParser.g:575:3: ( rule__While_Stmt__Group__0 )
					// InternalStructuredTextParser.g:575:4: rule__While_Stmt__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__While_Stmt__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getWhile_StmtAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleWhile_Stmt"

	// $ANTLR start "entryRuleRepeat_Stmt"
	// InternalStructuredTextParser.g:584:1: entryRuleRepeat_Stmt : ruleRepeat_Stmt
	// EOF ;
	public final void entryRuleRepeat_Stmt() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:585:1: ( ruleRepeat_Stmt EOF )
			// InternalStructuredTextParser.g:586:1: ruleRepeat_Stmt EOF
			{
				before(grammarAccess.getRepeat_StmtRule());
				pushFollow(FOLLOW_1);
				ruleRepeat_Stmt();

				state._fsp--;

				after(grammarAccess.getRepeat_StmtRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleRepeat_Stmt"

	// $ANTLR start "ruleRepeat_Stmt"
	// InternalStructuredTextParser.g:593:1: ruleRepeat_Stmt : ( (
	// rule__Repeat_Stmt__Group__0 ) ) ;
	public final void ruleRepeat_Stmt() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:597:2: ( ( ( rule__Repeat_Stmt__Group__0 ) ) )
			// InternalStructuredTextParser.g:598:2: ( ( rule__Repeat_Stmt__Group__0 ) )
			{
				// InternalStructuredTextParser.g:598:2: ( ( rule__Repeat_Stmt__Group__0 ) )
				// InternalStructuredTextParser.g:599:3: ( rule__Repeat_Stmt__Group__0 )
				{
					before(grammarAccess.getRepeat_StmtAccess().getGroup());
					// InternalStructuredTextParser.g:600:3: ( rule__Repeat_Stmt__Group__0 )
					// InternalStructuredTextParser.g:600:4: rule__Repeat_Stmt__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Repeat_Stmt__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getRepeat_StmtAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleRepeat_Stmt"

	// $ANTLR start "entryRuleExpression"
	// InternalStructuredTextParser.g:609:1: entryRuleExpression : ruleExpression
	// EOF ;
	public final void entryRuleExpression() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:610:1: ( ruleExpression EOF )
			// InternalStructuredTextParser.g:611:1: ruleExpression EOF
			{
				before(grammarAccess.getExpressionRule());
				pushFollow(FOLLOW_1);
				ruleExpression();

				state._fsp--;

				after(grammarAccess.getExpressionRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleExpression"

	// $ANTLR start "ruleExpression"
	// InternalStructuredTextParser.g:618:1: ruleExpression : ( ruleOr_Expression )
	// ;
	public final void ruleExpression() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:622:2: ( ( ruleOr_Expression ) )
			// InternalStructuredTextParser.g:623:2: ( ruleOr_Expression )
			{
				// InternalStructuredTextParser.g:623:2: ( ruleOr_Expression )
				// InternalStructuredTextParser.g:624:3: ruleOr_Expression
				{
					before(grammarAccess.getExpressionAccess().getOr_ExpressionParserRuleCall());
					pushFollow(FOLLOW_2);
					ruleOr_Expression();

					state._fsp--;

					after(grammarAccess.getExpressionAccess().getOr_ExpressionParserRuleCall());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleExpression"

	// $ANTLR start "entryRuleOr_Expression"
	// InternalStructuredTextParser.g:634:1: entryRuleOr_Expression :
	// ruleOr_Expression EOF ;
	public final void entryRuleOr_Expression() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:635:1: ( ruleOr_Expression EOF )
			// InternalStructuredTextParser.g:636:1: ruleOr_Expression EOF
			{
				before(grammarAccess.getOr_ExpressionRule());
				pushFollow(FOLLOW_1);
				ruleOr_Expression();

				state._fsp--;

				after(grammarAccess.getOr_ExpressionRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleOr_Expression"

	// $ANTLR start "ruleOr_Expression"
	// InternalStructuredTextParser.g:643:1: ruleOr_Expression : ( (
	// rule__Or_Expression__Group__0 ) ) ;
	public final void ruleOr_Expression() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:647:2: ( ( ( rule__Or_Expression__Group__0 ) )
			// )
			// InternalStructuredTextParser.g:648:2: ( ( rule__Or_Expression__Group__0 ) )
			{
				// InternalStructuredTextParser.g:648:2: ( ( rule__Or_Expression__Group__0 ) )
				// InternalStructuredTextParser.g:649:3: ( rule__Or_Expression__Group__0 )
				{
					before(grammarAccess.getOr_ExpressionAccess().getGroup());
					// InternalStructuredTextParser.g:650:3: ( rule__Or_Expression__Group__0 )
					// InternalStructuredTextParser.g:650:4: rule__Or_Expression__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Or_Expression__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getOr_ExpressionAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleOr_Expression"

	// $ANTLR start "entryRuleXor_Expr"
	// InternalStructuredTextParser.g:659:1: entryRuleXor_Expr : ruleXor_Expr EOF ;
	public final void entryRuleXor_Expr() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:660:1: ( ruleXor_Expr EOF )
			// InternalStructuredTextParser.g:661:1: ruleXor_Expr EOF
			{
				before(grammarAccess.getXor_ExprRule());
				pushFollow(FOLLOW_1);
				ruleXor_Expr();

				state._fsp--;

				after(grammarAccess.getXor_ExprRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleXor_Expr"

	// $ANTLR start "ruleXor_Expr"
	// InternalStructuredTextParser.g:668:1: ruleXor_Expr : ( (
	// rule__Xor_Expr__Group__0 ) ) ;
	public final void ruleXor_Expr() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:672:2: ( ( ( rule__Xor_Expr__Group__0 ) ) )
			// InternalStructuredTextParser.g:673:2: ( ( rule__Xor_Expr__Group__0 ) )
			{
				// InternalStructuredTextParser.g:673:2: ( ( rule__Xor_Expr__Group__0 ) )
				// InternalStructuredTextParser.g:674:3: ( rule__Xor_Expr__Group__0 )
				{
					before(grammarAccess.getXor_ExprAccess().getGroup());
					// InternalStructuredTextParser.g:675:3: ( rule__Xor_Expr__Group__0 )
					// InternalStructuredTextParser.g:675:4: rule__Xor_Expr__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Xor_Expr__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getXor_ExprAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleXor_Expr"

	// $ANTLR start "entryRuleAnd_Expr"
	// InternalStructuredTextParser.g:684:1: entryRuleAnd_Expr : ruleAnd_Expr EOF ;
	public final void entryRuleAnd_Expr() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:685:1: ( ruleAnd_Expr EOF )
			// InternalStructuredTextParser.g:686:1: ruleAnd_Expr EOF
			{
				before(grammarAccess.getAnd_ExprRule());
				pushFollow(FOLLOW_1);
				ruleAnd_Expr();

				state._fsp--;

				after(grammarAccess.getAnd_ExprRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleAnd_Expr"

	// $ANTLR start "ruleAnd_Expr"
	// InternalStructuredTextParser.g:693:1: ruleAnd_Expr : ( (
	// rule__And_Expr__Group__0 ) ) ;
	public final void ruleAnd_Expr() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:697:2: ( ( ( rule__And_Expr__Group__0 ) ) )
			// InternalStructuredTextParser.g:698:2: ( ( rule__And_Expr__Group__0 ) )
			{
				// InternalStructuredTextParser.g:698:2: ( ( rule__And_Expr__Group__0 ) )
				// InternalStructuredTextParser.g:699:3: ( rule__And_Expr__Group__0 )
				{
					before(grammarAccess.getAnd_ExprAccess().getGroup());
					// InternalStructuredTextParser.g:700:3: ( rule__And_Expr__Group__0 )
					// InternalStructuredTextParser.g:700:4: rule__And_Expr__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__And_Expr__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getAnd_ExprAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleAnd_Expr"

	// $ANTLR start "entryRuleCompare_Expr"
	// InternalStructuredTextParser.g:709:1: entryRuleCompare_Expr :
	// ruleCompare_Expr EOF ;
	public final void entryRuleCompare_Expr() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:710:1: ( ruleCompare_Expr EOF )
			// InternalStructuredTextParser.g:711:1: ruleCompare_Expr EOF
			{
				before(grammarAccess.getCompare_ExprRule());
				pushFollow(FOLLOW_1);
				ruleCompare_Expr();

				state._fsp--;

				after(grammarAccess.getCompare_ExprRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleCompare_Expr"

	// $ANTLR start "ruleCompare_Expr"
	// InternalStructuredTextParser.g:718:1: ruleCompare_Expr : ( (
	// rule__Compare_Expr__Group__0 ) ) ;
	public final void ruleCompare_Expr() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:722:2: ( ( ( rule__Compare_Expr__Group__0 ) )
			// )
			// InternalStructuredTextParser.g:723:2: ( ( rule__Compare_Expr__Group__0 ) )
			{
				// InternalStructuredTextParser.g:723:2: ( ( rule__Compare_Expr__Group__0 ) )
				// InternalStructuredTextParser.g:724:3: ( rule__Compare_Expr__Group__0 )
				{
					before(grammarAccess.getCompare_ExprAccess().getGroup());
					// InternalStructuredTextParser.g:725:3: ( rule__Compare_Expr__Group__0 )
					// InternalStructuredTextParser.g:725:4: rule__Compare_Expr__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Compare_Expr__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getCompare_ExprAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleCompare_Expr"

	// $ANTLR start "entryRuleEqu_Expr"
	// InternalStructuredTextParser.g:734:1: entryRuleEqu_Expr : ruleEqu_Expr EOF ;
	public final void entryRuleEqu_Expr() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:735:1: ( ruleEqu_Expr EOF )
			// InternalStructuredTextParser.g:736:1: ruleEqu_Expr EOF
			{
				before(grammarAccess.getEqu_ExprRule());
				pushFollow(FOLLOW_1);
				ruleEqu_Expr();

				state._fsp--;

				after(grammarAccess.getEqu_ExprRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleEqu_Expr"

	// $ANTLR start "ruleEqu_Expr"
	// InternalStructuredTextParser.g:743:1: ruleEqu_Expr : ( (
	// rule__Equ_Expr__Group__0 ) ) ;
	public final void ruleEqu_Expr() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:747:2: ( ( ( rule__Equ_Expr__Group__0 ) ) )
			// InternalStructuredTextParser.g:748:2: ( ( rule__Equ_Expr__Group__0 ) )
			{
				// InternalStructuredTextParser.g:748:2: ( ( rule__Equ_Expr__Group__0 ) )
				// InternalStructuredTextParser.g:749:3: ( rule__Equ_Expr__Group__0 )
				{
					before(grammarAccess.getEqu_ExprAccess().getGroup());
					// InternalStructuredTextParser.g:750:3: ( rule__Equ_Expr__Group__0 )
					// InternalStructuredTextParser.g:750:4: rule__Equ_Expr__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Equ_Expr__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getEqu_ExprAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleEqu_Expr"

	// $ANTLR start "entryRuleAdd_Expr"
	// InternalStructuredTextParser.g:759:1: entryRuleAdd_Expr : ruleAdd_Expr EOF ;
	public final void entryRuleAdd_Expr() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:760:1: ( ruleAdd_Expr EOF )
			// InternalStructuredTextParser.g:761:1: ruleAdd_Expr EOF
			{
				before(grammarAccess.getAdd_ExprRule());
				pushFollow(FOLLOW_1);
				ruleAdd_Expr();

				state._fsp--;

				after(grammarAccess.getAdd_ExprRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleAdd_Expr"

	// $ANTLR start "ruleAdd_Expr"
	// InternalStructuredTextParser.g:768:1: ruleAdd_Expr : ( (
	// rule__Add_Expr__Group__0 ) ) ;
	public final void ruleAdd_Expr() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:772:2: ( ( ( rule__Add_Expr__Group__0 ) ) )
			// InternalStructuredTextParser.g:773:2: ( ( rule__Add_Expr__Group__0 ) )
			{
				// InternalStructuredTextParser.g:773:2: ( ( rule__Add_Expr__Group__0 ) )
				// InternalStructuredTextParser.g:774:3: ( rule__Add_Expr__Group__0 )
				{
					before(grammarAccess.getAdd_ExprAccess().getGroup());
					// InternalStructuredTextParser.g:775:3: ( rule__Add_Expr__Group__0 )
					// InternalStructuredTextParser.g:775:4: rule__Add_Expr__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Add_Expr__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getAdd_ExprAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleAdd_Expr"

	// $ANTLR start "entryRuleTerm"
	// InternalStructuredTextParser.g:784:1: entryRuleTerm : ruleTerm EOF ;
	public final void entryRuleTerm() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:785:1: ( ruleTerm EOF )
			// InternalStructuredTextParser.g:786:1: ruleTerm EOF
			{
				before(grammarAccess.getTermRule());
				pushFollow(FOLLOW_1);
				ruleTerm();

				state._fsp--;

				after(grammarAccess.getTermRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleTerm"

	// $ANTLR start "ruleTerm"
	// InternalStructuredTextParser.g:793:1: ruleTerm : ( ( rule__Term__Group__0 ) )
	// ;
	public final void ruleTerm() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:797:2: ( ( ( rule__Term__Group__0 ) ) )
			// InternalStructuredTextParser.g:798:2: ( ( rule__Term__Group__0 ) )
			{
				// InternalStructuredTextParser.g:798:2: ( ( rule__Term__Group__0 ) )
				// InternalStructuredTextParser.g:799:3: ( rule__Term__Group__0 )
				{
					before(grammarAccess.getTermAccess().getGroup());
					// InternalStructuredTextParser.g:800:3: ( rule__Term__Group__0 )
					// InternalStructuredTextParser.g:800:4: rule__Term__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Term__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getTermAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleTerm"

	// $ANTLR start "entryRulePower_Expr"
	// InternalStructuredTextParser.g:809:1: entryRulePower_Expr : rulePower_Expr
	// EOF ;
	public final void entryRulePower_Expr() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:810:1: ( rulePower_Expr EOF )
			// InternalStructuredTextParser.g:811:1: rulePower_Expr EOF
			{
				before(grammarAccess.getPower_ExprRule());
				pushFollow(FOLLOW_1);
				rulePower_Expr();

				state._fsp--;

				after(grammarAccess.getPower_ExprRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRulePower_Expr"

	// $ANTLR start "rulePower_Expr"
	// InternalStructuredTextParser.g:818:1: rulePower_Expr : ( (
	// rule__Power_Expr__Group__0 ) ) ;
	public final void rulePower_Expr() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:822:2: ( ( ( rule__Power_Expr__Group__0 ) ) )
			// InternalStructuredTextParser.g:823:2: ( ( rule__Power_Expr__Group__0 ) )
			{
				// InternalStructuredTextParser.g:823:2: ( ( rule__Power_Expr__Group__0 ) )
				// InternalStructuredTextParser.g:824:3: ( rule__Power_Expr__Group__0 )
				{
					before(grammarAccess.getPower_ExprAccess().getGroup());
					// InternalStructuredTextParser.g:825:3: ( rule__Power_Expr__Group__0 )
					// InternalStructuredTextParser.g:825:4: rule__Power_Expr__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Power_Expr__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getPower_ExprAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rulePower_Expr"

	// $ANTLR start "entryRuleUnary_Expr"
	// InternalStructuredTextParser.g:834:1: entryRuleUnary_Expr : ruleUnary_Expr
	// EOF ;
	public final void entryRuleUnary_Expr() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:835:1: ( ruleUnary_Expr EOF )
			// InternalStructuredTextParser.g:836:1: ruleUnary_Expr EOF
			{
				before(grammarAccess.getUnary_ExprRule());
				pushFollow(FOLLOW_1);
				ruleUnary_Expr();

				state._fsp--;

				after(grammarAccess.getUnary_ExprRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleUnary_Expr"

	// $ANTLR start "ruleUnary_Expr"
	// InternalStructuredTextParser.g:843:1: ruleUnary_Expr : ( (
	// rule__Unary_Expr__Alternatives ) ) ;
	public final void ruleUnary_Expr() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:847:2: ( ( ( rule__Unary_Expr__Alternatives )
			// ) )
			// InternalStructuredTextParser.g:848:2: ( ( rule__Unary_Expr__Alternatives ) )
			{
				// InternalStructuredTextParser.g:848:2: ( ( rule__Unary_Expr__Alternatives ) )
				// InternalStructuredTextParser.g:849:3: ( rule__Unary_Expr__Alternatives )
				{
					before(grammarAccess.getUnary_ExprAccess().getAlternatives());
					// InternalStructuredTextParser.g:850:3: ( rule__Unary_Expr__Alternatives )
					// InternalStructuredTextParser.g:850:4: rule__Unary_Expr__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Unary_Expr__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getUnary_ExprAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleUnary_Expr"

	// $ANTLR start "entryRulePrimary_Expr"
	// InternalStructuredTextParser.g:859:1: entryRulePrimary_Expr :
	// rulePrimary_Expr EOF ;
	public final void entryRulePrimary_Expr() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:860:1: ( rulePrimary_Expr EOF )
			// InternalStructuredTextParser.g:861:1: rulePrimary_Expr EOF
			{
				before(grammarAccess.getPrimary_ExprRule());
				pushFollow(FOLLOW_1);
				rulePrimary_Expr();

				state._fsp--;

				after(grammarAccess.getPrimary_ExprRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRulePrimary_Expr"

	// $ANTLR start "rulePrimary_Expr"
	// InternalStructuredTextParser.g:868:1: rulePrimary_Expr : ( (
	// rule__Primary_Expr__Alternatives ) ) ;
	public final void rulePrimary_Expr() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:872:2: ( ( ( rule__Primary_Expr__Alternatives
			// ) ) )
			// InternalStructuredTextParser.g:873:2: ( ( rule__Primary_Expr__Alternatives )
			// )
			{
				// InternalStructuredTextParser.g:873:2: ( ( rule__Primary_Expr__Alternatives )
				// )
				// InternalStructuredTextParser.g:874:3: ( rule__Primary_Expr__Alternatives )
				{
					before(grammarAccess.getPrimary_ExprAccess().getAlternatives());
					// InternalStructuredTextParser.g:875:3: ( rule__Primary_Expr__Alternatives )
					// InternalStructuredTextParser.g:875:4: rule__Primary_Expr__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Primary_Expr__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getPrimary_ExprAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rulePrimary_Expr"

	// $ANTLR start "entryRuleFunc_Call"
	// InternalStructuredTextParser.g:884:1: entryRuleFunc_Call : ruleFunc_Call EOF
	// ;
	public final void entryRuleFunc_Call() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:885:1: ( ruleFunc_Call EOF )
			// InternalStructuredTextParser.g:886:1: ruleFunc_Call EOF
			{
				before(grammarAccess.getFunc_CallRule());
				pushFollow(FOLLOW_1);
				ruleFunc_Call();

				state._fsp--;

				after(grammarAccess.getFunc_CallRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleFunc_Call"

	// $ANTLR start "ruleFunc_Call"
	// InternalStructuredTextParser.g:893:1: ruleFunc_Call : ( (
	// rule__Func_Call__Group__0 ) ) ;
	public final void ruleFunc_Call() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:897:2: ( ( ( rule__Func_Call__Group__0 ) ) )
			// InternalStructuredTextParser.g:898:2: ( ( rule__Func_Call__Group__0 ) )
			{
				// InternalStructuredTextParser.g:898:2: ( ( rule__Func_Call__Group__0 ) )
				// InternalStructuredTextParser.g:899:3: ( rule__Func_Call__Group__0 )
				{
					before(grammarAccess.getFunc_CallAccess().getGroup());
					// InternalStructuredTextParser.g:900:3: ( rule__Func_Call__Group__0 )
					// InternalStructuredTextParser.g:900:4: rule__Func_Call__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Func_Call__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getFunc_CallAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleFunc_Call"

	// $ANTLR start "entryRuleParam_Assign"
	// InternalStructuredTextParser.g:909:1: entryRuleParam_Assign :
	// ruleParam_Assign EOF ;
	public final void entryRuleParam_Assign() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:910:1: ( ruleParam_Assign EOF )
			// InternalStructuredTextParser.g:911:1: ruleParam_Assign EOF
			{
				before(grammarAccess.getParam_AssignRule());
				pushFollow(FOLLOW_1);
				ruleParam_Assign();

				state._fsp--;

				after(grammarAccess.getParam_AssignRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleParam_Assign"

	// $ANTLR start "ruleParam_Assign"
	// InternalStructuredTextParser.g:918:1: ruleParam_Assign : ( (
	// rule__Param_Assign__Alternatives ) ) ;
	public final void ruleParam_Assign() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:922:2: ( ( ( rule__Param_Assign__Alternatives
			// ) ) )
			// InternalStructuredTextParser.g:923:2: ( ( rule__Param_Assign__Alternatives )
			// )
			{
				// InternalStructuredTextParser.g:923:2: ( ( rule__Param_Assign__Alternatives )
				// )
				// InternalStructuredTextParser.g:924:3: ( rule__Param_Assign__Alternatives )
				{
					before(grammarAccess.getParam_AssignAccess().getAlternatives());
					// InternalStructuredTextParser.g:925:3: ( rule__Param_Assign__Alternatives )
					// InternalStructuredTextParser.g:925:4: rule__Param_Assign__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Param_Assign__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getParam_AssignAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleParam_Assign"

	// $ANTLR start "entryRuleParam_Assign_In"
	// InternalStructuredTextParser.g:934:1: entryRuleParam_Assign_In :
	// ruleParam_Assign_In EOF ;
	public final void entryRuleParam_Assign_In() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:935:1: ( ruleParam_Assign_In EOF )
			// InternalStructuredTextParser.g:936:1: ruleParam_Assign_In EOF
			{
				before(grammarAccess.getParam_Assign_InRule());
				pushFollow(FOLLOW_1);
				ruleParam_Assign_In();

				state._fsp--;

				after(grammarAccess.getParam_Assign_InRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleParam_Assign_In"

	// $ANTLR start "ruleParam_Assign_In"
	// InternalStructuredTextParser.g:943:1: ruleParam_Assign_In : ( (
	// rule__Param_Assign_In__Group__0 ) ) ;
	public final void ruleParam_Assign_In() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:947:2: ( ( ( rule__Param_Assign_In__Group__0 )
			// ) )
			// InternalStructuredTextParser.g:948:2: ( ( rule__Param_Assign_In__Group__0 ) )
			{
				// InternalStructuredTextParser.g:948:2: ( ( rule__Param_Assign_In__Group__0 ) )
				// InternalStructuredTextParser.g:949:3: ( rule__Param_Assign_In__Group__0 )
				{
					before(grammarAccess.getParam_Assign_InAccess().getGroup());
					// InternalStructuredTextParser.g:950:3: ( rule__Param_Assign_In__Group__0 )
					// InternalStructuredTextParser.g:950:4: rule__Param_Assign_In__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Param_Assign_In__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getParam_Assign_InAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleParam_Assign_In"

	// $ANTLR start "entryRuleParam_Assign_Out"
	// InternalStructuredTextParser.g:959:1: entryRuleParam_Assign_Out :
	// ruleParam_Assign_Out EOF ;
	public final void entryRuleParam_Assign_Out() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:960:1: ( ruleParam_Assign_Out EOF )
			// InternalStructuredTextParser.g:961:1: ruleParam_Assign_Out EOF
			{
				before(grammarAccess.getParam_Assign_OutRule());
				pushFollow(FOLLOW_1);
				ruleParam_Assign_Out();

				state._fsp--;

				after(grammarAccess.getParam_Assign_OutRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleParam_Assign_Out"

	// $ANTLR start "ruleParam_Assign_Out"
	// InternalStructuredTextParser.g:968:1: ruleParam_Assign_Out : ( (
	// rule__Param_Assign_Out__Group__0 ) ) ;
	public final void ruleParam_Assign_Out() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:972:2: ( ( ( rule__Param_Assign_Out__Group__0
			// ) ) )
			// InternalStructuredTextParser.g:973:2: ( ( rule__Param_Assign_Out__Group__0 )
			// )
			{
				// InternalStructuredTextParser.g:973:2: ( ( rule__Param_Assign_Out__Group__0 )
				// )
				// InternalStructuredTextParser.g:974:3: ( rule__Param_Assign_Out__Group__0 )
				{
					before(grammarAccess.getParam_Assign_OutAccess().getGroup());
					// InternalStructuredTextParser.g:975:3: ( rule__Param_Assign_Out__Group__0 )
					// InternalStructuredTextParser.g:975:4: rule__Param_Assign_Out__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Param_Assign_Out__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getParam_Assign_OutAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleParam_Assign_Out"

	// $ANTLR start "entryRuleVariable"
	// InternalStructuredTextParser.g:984:1: entryRuleVariable : ruleVariable EOF ;
	public final void entryRuleVariable() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:985:1: ( ruleVariable EOF )
			// InternalStructuredTextParser.g:986:1: ruleVariable EOF
			{
				before(grammarAccess.getVariableRule());
				pushFollow(FOLLOW_1);
				ruleVariable();

				state._fsp--;

				after(grammarAccess.getVariableRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleVariable"

	// $ANTLR start "ruleVariable"
	// InternalStructuredTextParser.g:993:1: ruleVariable : ( (
	// rule__Variable__Group__0 ) ) ;
	public final void ruleVariable() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:997:2: ( ( ( rule__Variable__Group__0 ) ) )
			// InternalStructuredTextParser.g:998:2: ( ( rule__Variable__Group__0 ) )
			{
				// InternalStructuredTextParser.g:998:2: ( ( rule__Variable__Group__0 ) )
				// InternalStructuredTextParser.g:999:3: ( rule__Variable__Group__0 )
				{
					before(grammarAccess.getVariableAccess().getGroup());
					// InternalStructuredTextParser.g:1000:3: ( rule__Variable__Group__0 )
					// InternalStructuredTextParser.g:1000:4: rule__Variable__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Variable__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getVariableAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleVariable"

	// $ANTLR start "entryRuleVariable_Subscript"
	// InternalStructuredTextParser.g:1009:1: entryRuleVariable_Subscript :
	// ruleVariable_Subscript EOF ;
	public final void entryRuleVariable_Subscript() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1010:1: ( ruleVariable_Subscript EOF )
			// InternalStructuredTextParser.g:1011:1: ruleVariable_Subscript EOF
			{
				before(grammarAccess.getVariable_SubscriptRule());
				pushFollow(FOLLOW_1);
				ruleVariable_Subscript();

				state._fsp--;

				after(grammarAccess.getVariable_SubscriptRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleVariable_Subscript"

	// $ANTLR start "ruleVariable_Subscript"
	// InternalStructuredTextParser.g:1018:1: ruleVariable_Subscript : ( (
	// rule__Variable_Subscript__Group__0 ) ) ;
	public final void ruleVariable_Subscript() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1022:2: ( ( (
			// rule__Variable_Subscript__Group__0 ) ) )
			// InternalStructuredTextParser.g:1023:2: ( ( rule__Variable_Subscript__Group__0
			// ) )
			{
				// InternalStructuredTextParser.g:1023:2: ( ( rule__Variable_Subscript__Group__0
				// ) )
				// InternalStructuredTextParser.g:1024:3: ( rule__Variable_Subscript__Group__0 )
				{
					before(grammarAccess.getVariable_SubscriptAccess().getGroup());
					// InternalStructuredTextParser.g:1025:3: ( rule__Variable_Subscript__Group__0 )
					// InternalStructuredTextParser.g:1025:4: rule__Variable_Subscript__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Variable_Subscript__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getVariable_SubscriptAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleVariable_Subscript"

	// $ANTLR start "entryRuleVariable_Adapter"
	// InternalStructuredTextParser.g:1034:1: entryRuleVariable_Adapter :
	// ruleVariable_Adapter EOF ;
	public final void entryRuleVariable_Adapter() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1035:1: ( ruleVariable_Adapter EOF )
			// InternalStructuredTextParser.g:1036:1: ruleVariable_Adapter EOF
			{
				before(grammarAccess.getVariable_AdapterRule());
				pushFollow(FOLLOW_1);
				ruleVariable_Adapter();

				state._fsp--;

				after(grammarAccess.getVariable_AdapterRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleVariable_Adapter"

	// $ANTLR start "ruleVariable_Adapter"
	// InternalStructuredTextParser.g:1043:1: ruleVariable_Adapter : ( (
	// rule__Variable_Adapter__Group__0 ) ) ;
	public final void ruleVariable_Adapter() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1047:2: ( ( ( rule__Variable_Adapter__Group__0
			// ) ) )
			// InternalStructuredTextParser.g:1048:2: ( ( rule__Variable_Adapter__Group__0 )
			// )
			{
				// InternalStructuredTextParser.g:1048:2: ( ( rule__Variable_Adapter__Group__0 )
				// )
				// InternalStructuredTextParser.g:1049:3: ( rule__Variable_Adapter__Group__0 )
				{
					before(grammarAccess.getVariable_AdapterAccess().getGroup());
					// InternalStructuredTextParser.g:1050:3: ( rule__Variable_Adapter__Group__0 )
					// InternalStructuredTextParser.g:1050:4: rule__Variable_Adapter__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Variable_Adapter__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getVariable_AdapterAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleVariable_Adapter"

	// $ANTLR start "entryRuleMultibit_Part_Access"
	// InternalStructuredTextParser.g:1059:1: entryRuleMultibit_Part_Access :
	// ruleMultibit_Part_Access EOF ;
	public final void entryRuleMultibit_Part_Access() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1060:1: ( ruleMultibit_Part_Access EOF )
			// InternalStructuredTextParser.g:1061:1: ruleMultibit_Part_Access EOF
			{
				before(grammarAccess.getMultibit_Part_AccessRule());
				pushFollow(FOLLOW_1);
				ruleMultibit_Part_Access();

				state._fsp--;

				after(grammarAccess.getMultibit_Part_AccessRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleMultibit_Part_Access"

	// $ANTLR start "ruleMultibit_Part_Access"
	// InternalStructuredTextParser.g:1068:1: ruleMultibit_Part_Access : ( (
	// rule__Multibit_Part_Access__Alternatives ) ) ;
	public final void ruleMultibit_Part_Access() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1072:2: ( ( (
			// rule__Multibit_Part_Access__Alternatives ) ) )
			// InternalStructuredTextParser.g:1073:2: ( (
			// rule__Multibit_Part_Access__Alternatives ) )
			{
				// InternalStructuredTextParser.g:1073:2: ( (
				// rule__Multibit_Part_Access__Alternatives ) )
				// InternalStructuredTextParser.g:1074:3: (
				// rule__Multibit_Part_Access__Alternatives )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getAlternatives());
					// InternalStructuredTextParser.g:1075:3: (
					// rule__Multibit_Part_Access__Alternatives )
					// InternalStructuredTextParser.g:1075:4:
					// rule__Multibit_Part_Access__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleMultibit_Part_Access"

	// $ANTLR start "entryRuleAdapter_Name"
	// InternalStructuredTextParser.g:1084:1: entryRuleAdapter_Name :
	// ruleAdapter_Name EOF ;
	public final void entryRuleAdapter_Name() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1085:1: ( ruleAdapter_Name EOF )
			// InternalStructuredTextParser.g:1086:1: ruleAdapter_Name EOF
			{
				before(grammarAccess.getAdapter_NameRule());
				pushFollow(FOLLOW_1);
				ruleAdapter_Name();

				state._fsp--;

				after(grammarAccess.getAdapter_NameRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleAdapter_Name"

	// $ANTLR start "ruleAdapter_Name"
	// InternalStructuredTextParser.g:1093:1: ruleAdapter_Name : ( (
	// rule__Adapter_Name__Alternatives ) ) ;
	public final void ruleAdapter_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1097:2: ( ( ( rule__Adapter_Name__Alternatives
			// ) ) )
			// InternalStructuredTextParser.g:1098:2: ( ( rule__Adapter_Name__Alternatives )
			// )
			{
				// InternalStructuredTextParser.g:1098:2: ( ( rule__Adapter_Name__Alternatives )
				// )
				// InternalStructuredTextParser.g:1099:3: ( rule__Adapter_Name__Alternatives )
				{
					before(grammarAccess.getAdapter_NameAccess().getAlternatives());
					// InternalStructuredTextParser.g:1100:3: ( rule__Adapter_Name__Alternatives )
					// InternalStructuredTextParser.g:1100:4: rule__Adapter_Name__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Adapter_Name__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getAdapter_NameAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleAdapter_Name"

	// $ANTLR start "entryRuleVariable_Primary"
	// InternalStructuredTextParser.g:1109:1: entryRuleVariable_Primary :
	// ruleVariable_Primary EOF ;
	public final void entryRuleVariable_Primary() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1110:1: ( ruleVariable_Primary EOF )
			// InternalStructuredTextParser.g:1111:1: ruleVariable_Primary EOF
			{
				before(grammarAccess.getVariable_PrimaryRule());
				pushFollow(FOLLOW_1);
				ruleVariable_Primary();

				state._fsp--;

				after(grammarAccess.getVariable_PrimaryRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleVariable_Primary"

	// $ANTLR start "ruleVariable_Primary"
	// InternalStructuredTextParser.g:1118:1: ruleVariable_Primary : ( (
	// rule__Variable_Primary__VarAssignment ) ) ;
	public final void ruleVariable_Primary() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1122:2: ( ( (
			// rule__Variable_Primary__VarAssignment ) ) )
			// InternalStructuredTextParser.g:1123:2: ( (
			// rule__Variable_Primary__VarAssignment ) )
			{
				// InternalStructuredTextParser.g:1123:2: ( (
				// rule__Variable_Primary__VarAssignment ) )
				// InternalStructuredTextParser.g:1124:3: (
				// rule__Variable_Primary__VarAssignment )
				{
					before(grammarAccess.getVariable_PrimaryAccess().getVarAssignment());
					// InternalStructuredTextParser.g:1125:3: (
					// rule__Variable_Primary__VarAssignment )
					// InternalStructuredTextParser.g:1125:4: rule__Variable_Primary__VarAssignment
					{
						pushFollow(FOLLOW_2);
						rule__Variable_Primary__VarAssignment();

						state._fsp--;

					}

					after(grammarAccess.getVariable_PrimaryAccess().getVarAssignment());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleVariable_Primary"

	// $ANTLR start "entryRuleVariable_Name"
	// InternalStructuredTextParser.g:1134:1: entryRuleVariable_Name :
	// ruleVariable_Name EOF ;
	public final void entryRuleVariable_Name() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1135:1: ( ruleVariable_Name EOF )
			// InternalStructuredTextParser.g:1136:1: ruleVariable_Name EOF
			{
				before(grammarAccess.getVariable_NameRule());
				pushFollow(FOLLOW_1);
				ruleVariable_Name();

				state._fsp--;

				after(grammarAccess.getVariable_NameRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleVariable_Name"

	// $ANTLR start "ruleVariable_Name"
	// InternalStructuredTextParser.g:1143:1: ruleVariable_Name : ( (
	// rule__Variable_Name__Alternatives ) ) ;
	public final void ruleVariable_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1147:2: ( ( (
			// rule__Variable_Name__Alternatives ) ) )
			// InternalStructuredTextParser.g:1148:2: ( ( rule__Variable_Name__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:1148:2: ( ( rule__Variable_Name__Alternatives
				// ) )
				// InternalStructuredTextParser.g:1149:3: ( rule__Variable_Name__Alternatives )
				{
					before(grammarAccess.getVariable_NameAccess().getAlternatives());
					// InternalStructuredTextParser.g:1150:3: ( rule__Variable_Name__Alternatives )
					// InternalStructuredTextParser.g:1150:4: rule__Variable_Name__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Variable_Name__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getVariable_NameAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleVariable_Name"

	// $ANTLR start "entryRuleConstant"
	// InternalStructuredTextParser.g:1159:1: entryRuleConstant : ruleConstant EOF ;
	public final void entryRuleConstant() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1160:1: ( ruleConstant EOF )
			// InternalStructuredTextParser.g:1161:1: ruleConstant EOF
			{
				before(grammarAccess.getConstantRule());
				pushFollow(FOLLOW_1);
				ruleConstant();

				state._fsp--;

				after(grammarAccess.getConstantRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleConstant"

	// $ANTLR start "ruleConstant"
	// InternalStructuredTextParser.g:1168:1: ruleConstant : ( (
	// rule__Constant__Alternatives ) ) ;
	public final void ruleConstant() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1172:2: ( ( ( rule__Constant__Alternatives ) )
			// )
			// InternalStructuredTextParser.g:1173:2: ( ( rule__Constant__Alternatives ) )
			{
				// InternalStructuredTextParser.g:1173:2: ( ( rule__Constant__Alternatives ) )
				// InternalStructuredTextParser.g:1174:3: ( rule__Constant__Alternatives )
				{
					before(grammarAccess.getConstantAccess().getAlternatives());
					// InternalStructuredTextParser.g:1175:3: ( rule__Constant__Alternatives )
					// InternalStructuredTextParser.g:1175:4: rule__Constant__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Constant__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getConstantAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleConstant"

	// $ANTLR start "entryRuleNumeric_Literal"
	// InternalStructuredTextParser.g:1184:1: entryRuleNumeric_Literal :
	// ruleNumeric_Literal EOF ;
	public final void entryRuleNumeric_Literal() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1185:1: ( ruleNumeric_Literal EOF )
			// InternalStructuredTextParser.g:1186:1: ruleNumeric_Literal EOF
			{
				before(grammarAccess.getNumeric_LiteralRule());
				pushFollow(FOLLOW_1);
				ruleNumeric_Literal();

				state._fsp--;

				after(grammarAccess.getNumeric_LiteralRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleNumeric_Literal"

	// $ANTLR start "ruleNumeric_Literal"
	// InternalStructuredTextParser.g:1193:1: ruleNumeric_Literal : ( (
	// rule__Numeric_Literal__Alternatives ) ) ;
	public final void ruleNumeric_Literal() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1197:2: ( ( (
			// rule__Numeric_Literal__Alternatives ) ) )
			// InternalStructuredTextParser.g:1198:2: ( (
			// rule__Numeric_Literal__Alternatives ) )
			{
				// InternalStructuredTextParser.g:1198:2: ( (
				// rule__Numeric_Literal__Alternatives ) )
				// InternalStructuredTextParser.g:1199:3: ( rule__Numeric_Literal__Alternatives
				// )
				{
					before(grammarAccess.getNumeric_LiteralAccess().getAlternatives());
					// InternalStructuredTextParser.g:1200:3: ( rule__Numeric_Literal__Alternatives
					// )
					// InternalStructuredTextParser.g:1200:4: rule__Numeric_Literal__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Numeric_Literal__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getNumeric_LiteralAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleNumeric_Literal"

	// $ANTLR start "entryRuleInt_Literal"
	// InternalStructuredTextParser.g:1209:1: entryRuleInt_Literal : ruleInt_Literal
	// EOF ;
	public final void entryRuleInt_Literal() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1210:1: ( ruleInt_Literal EOF )
			// InternalStructuredTextParser.g:1211:1: ruleInt_Literal EOF
			{
				before(grammarAccess.getInt_LiteralRule());
				pushFollow(FOLLOW_1);
				ruleInt_Literal();

				state._fsp--;

				after(grammarAccess.getInt_LiteralRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleInt_Literal"

	// $ANTLR start "ruleInt_Literal"
	// InternalStructuredTextParser.g:1218:1: ruleInt_Literal : ( (
	// rule__Int_Literal__Group__0 ) ) ;
	public final void ruleInt_Literal() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1222:2: ( ( ( rule__Int_Literal__Group__0 ) )
			// )
			// InternalStructuredTextParser.g:1223:2: ( ( rule__Int_Literal__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1223:2: ( ( rule__Int_Literal__Group__0 ) )
				// InternalStructuredTextParser.g:1224:3: ( rule__Int_Literal__Group__0 )
				{
					before(grammarAccess.getInt_LiteralAccess().getGroup());
					// InternalStructuredTextParser.g:1225:3: ( rule__Int_Literal__Group__0 )
					// InternalStructuredTextParser.g:1225:4: rule__Int_Literal__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Int_Literal__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getInt_LiteralAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleInt_Literal"

	// $ANTLR start "entryRuleSigned_Int"
	// InternalStructuredTextParser.g:1234:1: entryRuleSigned_Int : ruleSigned_Int
	// EOF ;
	public final void entryRuleSigned_Int() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1235:1: ( ruleSigned_Int EOF )
			// InternalStructuredTextParser.g:1236:1: ruleSigned_Int EOF
			{
				before(grammarAccess.getSigned_IntRule());
				pushFollow(FOLLOW_1);
				ruleSigned_Int();

				state._fsp--;

				after(grammarAccess.getSigned_IntRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleSigned_Int"

	// $ANTLR start "ruleSigned_Int"
	// InternalStructuredTextParser.g:1243:1: ruleSigned_Int : ( (
	// rule__Signed_Int__Group__0 ) ) ;
	public final void ruleSigned_Int() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1247:2: ( ( ( rule__Signed_Int__Group__0 ) ) )
			// InternalStructuredTextParser.g:1248:2: ( ( rule__Signed_Int__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1248:2: ( ( rule__Signed_Int__Group__0 ) )
				// InternalStructuredTextParser.g:1249:3: ( rule__Signed_Int__Group__0 )
				{
					before(grammarAccess.getSigned_IntAccess().getGroup());
					// InternalStructuredTextParser.g:1250:3: ( rule__Signed_Int__Group__0 )
					// InternalStructuredTextParser.g:1250:4: rule__Signed_Int__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Signed_Int__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getSigned_IntAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleSigned_Int"

	// $ANTLR start "entryRulePartial_Value"
	// InternalStructuredTextParser.g:1259:1: entryRulePartial_Value :
	// rulePartial_Value EOF ;
	public final void entryRulePartial_Value() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1260:1: ( rulePartial_Value EOF )
			// InternalStructuredTextParser.g:1261:1: rulePartial_Value EOF
			{
				before(grammarAccess.getPartial_ValueRule());
				pushFollow(FOLLOW_1);
				rulePartial_Value();

				state._fsp--;

				after(grammarAccess.getPartial_ValueRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRulePartial_Value"

	// $ANTLR start "rulePartial_Value"
	// InternalStructuredTextParser.g:1268:1: rulePartial_Value : (
	// RULE_UNSIGNED_INT ) ;
	public final void rulePartial_Value() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1272:2: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:1273:2: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:1273:2: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:1274:3: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getPartial_ValueAccess().getUNSIGNED_INTTerminalRuleCall());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getPartial_ValueAccess().getUNSIGNED_INTTerminalRuleCall());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rulePartial_Value"

	// $ANTLR start "entryRuleArray_Size"
	// InternalStructuredTextParser.g:1284:1: entryRuleArray_Size : ruleArray_Size
	// EOF ;
	public final void entryRuleArray_Size() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1285:1: ( ruleArray_Size EOF )
			// InternalStructuredTextParser.g:1286:1: ruleArray_Size EOF
			{
				before(grammarAccess.getArray_SizeRule());
				pushFollow(FOLLOW_1);
				ruleArray_Size();

				state._fsp--;

				after(grammarAccess.getArray_SizeRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleArray_Size"

	// $ANTLR start "ruleArray_Size"
	// InternalStructuredTextParser.g:1293:1: ruleArray_Size : ( RULE_UNSIGNED_INT )
	// ;
	public final void ruleArray_Size() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1297:2: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:1298:2: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:1298:2: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:1299:3: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getArray_SizeAccess().getUNSIGNED_INTTerminalRuleCall());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getArray_SizeAccess().getUNSIGNED_INTTerminalRuleCall());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleArray_Size"

	// $ANTLR start "entryRuleReal_Literal"
	// InternalStructuredTextParser.g:1309:1: entryRuleReal_Literal :
	// ruleReal_Literal EOF ;
	public final void entryRuleReal_Literal() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1310:1: ( ruleReal_Literal EOF )
			// InternalStructuredTextParser.g:1311:1: ruleReal_Literal EOF
			{
				before(grammarAccess.getReal_LiteralRule());
				pushFollow(FOLLOW_1);
				ruleReal_Literal();

				state._fsp--;

				after(grammarAccess.getReal_LiteralRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleReal_Literal"

	// $ANTLR start "ruleReal_Literal"
	// InternalStructuredTextParser.g:1318:1: ruleReal_Literal : ( (
	// rule__Real_Literal__Group__0 ) ) ;
	public final void ruleReal_Literal() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1322:2: ( ( ( rule__Real_Literal__Group__0 ) )
			// )
			// InternalStructuredTextParser.g:1323:2: ( ( rule__Real_Literal__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1323:2: ( ( rule__Real_Literal__Group__0 ) )
				// InternalStructuredTextParser.g:1324:3: ( rule__Real_Literal__Group__0 )
				{
					before(grammarAccess.getReal_LiteralAccess().getGroup());
					// InternalStructuredTextParser.g:1325:3: ( rule__Real_Literal__Group__0 )
					// InternalStructuredTextParser.g:1325:4: rule__Real_Literal__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Real_Literal__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getReal_LiteralAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleReal_Literal"

	// $ANTLR start "entryRuleReal_Value"
	// InternalStructuredTextParser.g:1334:1: entryRuleReal_Value : ruleReal_Value
	// EOF ;
	public final void entryRuleReal_Value() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1335:1: ( ruleReal_Value EOF )
			// InternalStructuredTextParser.g:1336:1: ruleReal_Value EOF
			{
				before(grammarAccess.getReal_ValueRule());
				pushFollow(FOLLOW_1);
				ruleReal_Value();

				state._fsp--;

				after(grammarAccess.getReal_ValueRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleReal_Value"

	// $ANTLR start "ruleReal_Value"
	// InternalStructuredTextParser.g:1343:1: ruleReal_Value : ( (
	// rule__Real_Value__Group__0 ) ) ;
	public final void ruleReal_Value() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1347:2: ( ( ( rule__Real_Value__Group__0 ) ) )
			// InternalStructuredTextParser.g:1348:2: ( ( rule__Real_Value__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1348:2: ( ( rule__Real_Value__Group__0 ) )
				// InternalStructuredTextParser.g:1349:3: ( rule__Real_Value__Group__0 )
				{
					before(grammarAccess.getReal_ValueAccess().getGroup());
					// InternalStructuredTextParser.g:1350:3: ( rule__Real_Value__Group__0 )
					// InternalStructuredTextParser.g:1350:4: rule__Real_Value__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Real_Value__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getReal_ValueAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleReal_Value"

	// $ANTLR start "entryRuleBool_Literal"
	// InternalStructuredTextParser.g:1359:1: entryRuleBool_Literal :
	// ruleBool_Literal EOF ;
	public final void entryRuleBool_Literal() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1360:1: ( ruleBool_Literal EOF )
			// InternalStructuredTextParser.g:1361:1: ruleBool_Literal EOF
			{
				before(grammarAccess.getBool_LiteralRule());
				pushFollow(FOLLOW_1);
				ruleBool_Literal();

				state._fsp--;

				after(grammarAccess.getBool_LiteralRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleBool_Literal"

	// $ANTLR start "ruleBool_Literal"
	// InternalStructuredTextParser.g:1368:1: ruleBool_Literal : ( (
	// rule__Bool_Literal__Group__0 ) ) ;
	public final void ruleBool_Literal() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1372:2: ( ( ( rule__Bool_Literal__Group__0 ) )
			// )
			// InternalStructuredTextParser.g:1373:2: ( ( rule__Bool_Literal__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1373:2: ( ( rule__Bool_Literal__Group__0 ) )
				// InternalStructuredTextParser.g:1374:3: ( rule__Bool_Literal__Group__0 )
				{
					before(grammarAccess.getBool_LiteralAccess().getGroup());
					// InternalStructuredTextParser.g:1375:3: ( rule__Bool_Literal__Group__0 )
					// InternalStructuredTextParser.g:1375:4: rule__Bool_Literal__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Bool_Literal__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getBool_LiteralAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleBool_Literal"

	// $ANTLR start "entryRuleBool_Value"
	// InternalStructuredTextParser.g:1384:1: entryRuleBool_Value : ruleBool_Value
	// EOF ;
	public final void entryRuleBool_Value() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1385:1: ( ruleBool_Value EOF )
			// InternalStructuredTextParser.g:1386:1: ruleBool_Value EOF
			{
				before(grammarAccess.getBool_ValueRule());
				pushFollow(FOLLOW_1);
				ruleBool_Value();

				state._fsp--;

				after(grammarAccess.getBool_ValueRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleBool_Value"

	// $ANTLR start "ruleBool_Value"
	// InternalStructuredTextParser.g:1393:1: ruleBool_Value : ( (
	// rule__Bool_Value__Alternatives ) ) ;
	public final void ruleBool_Value() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1397:2: ( ( ( rule__Bool_Value__Alternatives )
			// ) )
			// InternalStructuredTextParser.g:1398:2: ( ( rule__Bool_Value__Alternatives ) )
			{
				// InternalStructuredTextParser.g:1398:2: ( ( rule__Bool_Value__Alternatives ) )
				// InternalStructuredTextParser.g:1399:3: ( rule__Bool_Value__Alternatives )
				{
					before(grammarAccess.getBool_ValueAccess().getAlternatives());
					// InternalStructuredTextParser.g:1400:3: ( rule__Bool_Value__Alternatives )
					// InternalStructuredTextParser.g:1400:4: rule__Bool_Value__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Bool_Value__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getBool_ValueAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleBool_Value"

	// $ANTLR start "entryRuleChar_Literal"
	// InternalStructuredTextParser.g:1409:1: entryRuleChar_Literal :
	// ruleChar_Literal EOF ;
	public final void entryRuleChar_Literal() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1410:1: ( ruleChar_Literal EOF )
			// InternalStructuredTextParser.g:1411:1: ruleChar_Literal EOF
			{
				before(grammarAccess.getChar_LiteralRule());
				pushFollow(FOLLOW_1);
				ruleChar_Literal();

				state._fsp--;

				after(grammarAccess.getChar_LiteralRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleChar_Literal"

	// $ANTLR start "ruleChar_Literal"
	// InternalStructuredTextParser.g:1418:1: ruleChar_Literal : ( (
	// rule__Char_Literal__Group__0 ) ) ;
	public final void ruleChar_Literal() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1422:2: ( ( ( rule__Char_Literal__Group__0 ) )
			// )
			// InternalStructuredTextParser.g:1423:2: ( ( rule__Char_Literal__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1423:2: ( ( rule__Char_Literal__Group__0 ) )
				// InternalStructuredTextParser.g:1424:3: ( rule__Char_Literal__Group__0 )
				{
					before(grammarAccess.getChar_LiteralAccess().getGroup());
					// InternalStructuredTextParser.g:1425:3: ( rule__Char_Literal__Group__0 )
					// InternalStructuredTextParser.g:1425:4: rule__Char_Literal__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Char_Literal__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getChar_LiteralAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleChar_Literal"

	// $ANTLR start "entryRuleTime_Literal"
	// InternalStructuredTextParser.g:1434:1: entryRuleTime_Literal :
	// ruleTime_Literal EOF ;
	public final void entryRuleTime_Literal() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1435:1: ( ruleTime_Literal EOF )
			// InternalStructuredTextParser.g:1436:1: ruleTime_Literal EOF
			{
				before(grammarAccess.getTime_LiteralRule());
				pushFollow(FOLLOW_1);
				ruleTime_Literal();

				state._fsp--;

				after(grammarAccess.getTime_LiteralRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleTime_Literal"

	// $ANTLR start "ruleTime_Literal"
	// InternalStructuredTextParser.g:1443:1: ruleTime_Literal : ( (
	// rule__Time_Literal__Alternatives ) ) ;
	public final void ruleTime_Literal() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1447:2: ( ( ( rule__Time_Literal__Alternatives
			// ) ) )
			// InternalStructuredTextParser.g:1448:2: ( ( rule__Time_Literal__Alternatives )
			// )
			{
				// InternalStructuredTextParser.g:1448:2: ( ( rule__Time_Literal__Alternatives )
				// )
				// InternalStructuredTextParser.g:1449:3: ( rule__Time_Literal__Alternatives )
				{
					before(grammarAccess.getTime_LiteralAccess().getAlternatives());
					// InternalStructuredTextParser.g:1450:3: ( rule__Time_Literal__Alternatives )
					// InternalStructuredTextParser.g:1450:4: rule__Time_Literal__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Time_Literal__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getTime_LiteralAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleTime_Literal"

	// $ANTLR start "entryRuleDuration"
	// InternalStructuredTextParser.g:1459:1: entryRuleDuration : ruleDuration EOF ;
	public final void entryRuleDuration() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1460:1: ( ruleDuration EOF )
			// InternalStructuredTextParser.g:1461:1: ruleDuration EOF
			{
				before(grammarAccess.getDurationRule());
				pushFollow(FOLLOW_1);
				ruleDuration();

				state._fsp--;

				after(grammarAccess.getDurationRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDuration"

	// $ANTLR start "ruleDuration"
	// InternalStructuredTextParser.g:1468:1: ruleDuration : ( (
	// rule__Duration__Group__0 ) ) ;
	public final void ruleDuration() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1472:2: ( ( ( rule__Duration__Group__0 ) ) )
			// InternalStructuredTextParser.g:1473:2: ( ( rule__Duration__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1473:2: ( ( rule__Duration__Group__0 ) )
				// InternalStructuredTextParser.g:1474:3: ( rule__Duration__Group__0 )
				{
					before(grammarAccess.getDurationAccess().getGroup());
					// InternalStructuredTextParser.g:1475:3: ( rule__Duration__Group__0 )
					// InternalStructuredTextParser.g:1475:4: rule__Duration__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Duration__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getDurationAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDuration"

	// $ANTLR start "entryRuleDuration_Value"
	// InternalStructuredTextParser.g:1484:1: entryRuleDuration_Value :
	// ruleDuration_Value EOF ;
	public final void entryRuleDuration_Value() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1485:1: ( ruleDuration_Value EOF )
			// InternalStructuredTextParser.g:1486:1: ruleDuration_Value EOF
			{
				before(grammarAccess.getDuration_ValueRule());
				pushFollow(FOLLOW_1);
				ruleDuration_Value();

				state._fsp--;

				after(grammarAccess.getDuration_ValueRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDuration_Value"

	// $ANTLR start "ruleDuration_Value"
	// InternalStructuredTextParser.g:1493:1: ruleDuration_Value : ( (
	// rule__Duration_Value__Group__0 ) ) ;
	public final void ruleDuration_Value() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1497:2: ( ( ( rule__Duration_Value__Group__0 )
			// ) )
			// InternalStructuredTextParser.g:1498:2: ( ( rule__Duration_Value__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1498:2: ( ( rule__Duration_Value__Group__0 ) )
				// InternalStructuredTextParser.g:1499:3: ( rule__Duration_Value__Group__0 )
				{
					before(grammarAccess.getDuration_ValueAccess().getGroup());
					// InternalStructuredTextParser.g:1500:3: ( rule__Duration_Value__Group__0 )
					// InternalStructuredTextParser.g:1500:4: rule__Duration_Value__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Duration_Value__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getDuration_ValueAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDuration_Value"

	// $ANTLR start "entryRuleFix_Point"
	// InternalStructuredTextParser.g:1509:1: entryRuleFix_Point : ruleFix_Point EOF
	// ;
	public final void entryRuleFix_Point() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1510:1: ( ruleFix_Point EOF )
			// InternalStructuredTextParser.g:1511:1: ruleFix_Point EOF
			{
				before(grammarAccess.getFix_PointRule());
				pushFollow(FOLLOW_1);
				ruleFix_Point();

				state._fsp--;

				after(grammarAccess.getFix_PointRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleFix_Point"

	// $ANTLR start "ruleFix_Point"
	// InternalStructuredTextParser.g:1518:1: ruleFix_Point : ( (
	// rule__Fix_Point__Group__0 ) ) ;
	public final void ruleFix_Point() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1522:2: ( ( ( rule__Fix_Point__Group__0 ) ) )
			// InternalStructuredTextParser.g:1523:2: ( ( rule__Fix_Point__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1523:2: ( ( rule__Fix_Point__Group__0 ) )
				// InternalStructuredTextParser.g:1524:3: ( rule__Fix_Point__Group__0 )
				{
					before(grammarAccess.getFix_PointAccess().getGroup());
					// InternalStructuredTextParser.g:1525:3: ( rule__Fix_Point__Group__0 )
					// InternalStructuredTextParser.g:1525:4: rule__Fix_Point__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Fix_Point__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getFix_PointAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleFix_Point"

	// $ANTLR start "entryRuleTime_Of_Day"
	// InternalStructuredTextParser.g:1534:1: entryRuleTime_Of_Day : ruleTime_Of_Day
	// EOF ;
	public final void entryRuleTime_Of_Day() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1535:1: ( ruleTime_Of_Day EOF )
			// InternalStructuredTextParser.g:1536:1: ruleTime_Of_Day EOF
			{
				before(grammarAccess.getTime_Of_DayRule());
				pushFollow(FOLLOW_1);
				ruleTime_Of_Day();

				state._fsp--;

				after(grammarAccess.getTime_Of_DayRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleTime_Of_Day"

	// $ANTLR start "ruleTime_Of_Day"
	// InternalStructuredTextParser.g:1543:1: ruleTime_Of_Day : ( (
	// rule__Time_Of_Day__Group__0 ) ) ;
	public final void ruleTime_Of_Day() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1547:2: ( ( ( rule__Time_Of_Day__Group__0 ) )
			// )
			// InternalStructuredTextParser.g:1548:2: ( ( rule__Time_Of_Day__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1548:2: ( ( rule__Time_Of_Day__Group__0 ) )
				// InternalStructuredTextParser.g:1549:3: ( rule__Time_Of_Day__Group__0 )
				{
					before(grammarAccess.getTime_Of_DayAccess().getGroup());
					// InternalStructuredTextParser.g:1550:3: ( rule__Time_Of_Day__Group__0 )
					// InternalStructuredTextParser.g:1550:4: rule__Time_Of_Day__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Time_Of_Day__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getTime_Of_DayAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleTime_Of_Day"

	// $ANTLR start "entryRuleDaytime"
	// InternalStructuredTextParser.g:1559:1: entryRuleDaytime : ruleDaytime EOF ;
	public final void entryRuleDaytime() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1560:1: ( ruleDaytime EOF )
			// InternalStructuredTextParser.g:1561:1: ruleDaytime EOF
			{
				before(grammarAccess.getDaytimeRule());
				pushFollow(FOLLOW_1);
				ruleDaytime();

				state._fsp--;

				after(grammarAccess.getDaytimeRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDaytime"

	// $ANTLR start "ruleDaytime"
	// InternalStructuredTextParser.g:1568:1: ruleDaytime : ( (
	// rule__Daytime__Group__0 ) ) ;
	public final void ruleDaytime() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1572:2: ( ( ( rule__Daytime__Group__0 ) ) )
			// InternalStructuredTextParser.g:1573:2: ( ( rule__Daytime__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1573:2: ( ( rule__Daytime__Group__0 ) )
				// InternalStructuredTextParser.g:1574:3: ( rule__Daytime__Group__0 )
				{
					before(grammarAccess.getDaytimeAccess().getGroup());
					// InternalStructuredTextParser.g:1575:3: ( rule__Daytime__Group__0 )
					// InternalStructuredTextParser.g:1575:4: rule__Daytime__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Daytime__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getDaytimeAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDaytime"

	// $ANTLR start "entryRuleDay_Hour"
	// InternalStructuredTextParser.g:1584:1: entryRuleDay_Hour : ruleDay_Hour EOF ;
	public final void entryRuleDay_Hour() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1585:1: ( ruleDay_Hour EOF )
			// InternalStructuredTextParser.g:1586:1: ruleDay_Hour EOF
			{
				before(grammarAccess.getDay_HourRule());
				pushFollow(FOLLOW_1);
				ruleDay_Hour();

				state._fsp--;

				after(grammarAccess.getDay_HourRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDay_Hour"

	// $ANTLR start "ruleDay_Hour"
	// InternalStructuredTextParser.g:1593:1: ruleDay_Hour : ( RULE_UNSIGNED_INT ) ;
	public final void ruleDay_Hour() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1597:2: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:1598:2: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:1598:2: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:1599:3: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getDay_HourAccess().getUNSIGNED_INTTerminalRuleCall());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getDay_HourAccess().getUNSIGNED_INTTerminalRuleCall());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDay_Hour"

	// $ANTLR start "entryRuleDay_Minute"
	// InternalStructuredTextParser.g:1609:1: entryRuleDay_Minute : ruleDay_Minute
	// EOF ;
	public final void entryRuleDay_Minute() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1610:1: ( ruleDay_Minute EOF )
			// InternalStructuredTextParser.g:1611:1: ruleDay_Minute EOF
			{
				before(grammarAccess.getDay_MinuteRule());
				pushFollow(FOLLOW_1);
				ruleDay_Minute();

				state._fsp--;

				after(grammarAccess.getDay_MinuteRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDay_Minute"

	// $ANTLR start "ruleDay_Minute"
	// InternalStructuredTextParser.g:1618:1: ruleDay_Minute : ( RULE_UNSIGNED_INT )
	// ;
	public final void ruleDay_Minute() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1622:2: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:1623:2: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:1623:2: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:1624:3: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getDay_MinuteAccess().getUNSIGNED_INTTerminalRuleCall());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getDay_MinuteAccess().getUNSIGNED_INTTerminalRuleCall());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDay_Minute"

	// $ANTLR start "entryRuleDay_Second"
	// InternalStructuredTextParser.g:1634:1: entryRuleDay_Second : ruleDay_Second
	// EOF ;
	public final void entryRuleDay_Second() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1635:1: ( ruleDay_Second EOF )
			// InternalStructuredTextParser.g:1636:1: ruleDay_Second EOF
			{
				before(grammarAccess.getDay_SecondRule());
				pushFollow(FOLLOW_1);
				ruleDay_Second();

				state._fsp--;

				after(grammarAccess.getDay_SecondRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDay_Second"

	// $ANTLR start "ruleDay_Second"
	// InternalStructuredTextParser.g:1643:1: ruleDay_Second : ( ruleFix_Point ) ;
	public final void ruleDay_Second() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1647:2: ( ( ruleFix_Point ) )
			// InternalStructuredTextParser.g:1648:2: ( ruleFix_Point )
			{
				// InternalStructuredTextParser.g:1648:2: ( ruleFix_Point )
				// InternalStructuredTextParser.g:1649:3: ruleFix_Point
				{
					before(grammarAccess.getDay_SecondAccess().getFix_PointParserRuleCall());
					pushFollow(FOLLOW_2);
					ruleFix_Point();

					state._fsp--;

					after(grammarAccess.getDay_SecondAccess().getFix_PointParserRuleCall());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDay_Second"

	// $ANTLR start "entryRuleDate"
	// InternalStructuredTextParser.g:1659:1: entryRuleDate : ruleDate EOF ;
	public final void entryRuleDate() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1660:1: ( ruleDate EOF )
			// InternalStructuredTextParser.g:1661:1: ruleDate EOF
			{
				before(grammarAccess.getDateRule());
				pushFollow(FOLLOW_1);
				ruleDate();

				state._fsp--;

				after(grammarAccess.getDateRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDate"

	// $ANTLR start "ruleDate"
	// InternalStructuredTextParser.g:1668:1: ruleDate : ( ( rule__Date__Group__0 )
	// ) ;
	public final void ruleDate() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1672:2: ( ( ( rule__Date__Group__0 ) ) )
			// InternalStructuredTextParser.g:1673:2: ( ( rule__Date__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1673:2: ( ( rule__Date__Group__0 ) )
				// InternalStructuredTextParser.g:1674:3: ( rule__Date__Group__0 )
				{
					before(grammarAccess.getDateAccess().getGroup());
					// InternalStructuredTextParser.g:1675:3: ( rule__Date__Group__0 )
					// InternalStructuredTextParser.g:1675:4: rule__Date__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Date__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getDateAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDate"

	// $ANTLR start "entryRuleDate_Literal"
	// InternalStructuredTextParser.g:1684:1: entryRuleDate_Literal :
	// ruleDate_Literal EOF ;
	public final void entryRuleDate_Literal() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1685:1: ( ruleDate_Literal EOF )
			// InternalStructuredTextParser.g:1686:1: ruleDate_Literal EOF
			{
				before(grammarAccess.getDate_LiteralRule());
				pushFollow(FOLLOW_1);
				ruleDate_Literal();

				state._fsp--;

				after(grammarAccess.getDate_LiteralRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDate_Literal"

	// $ANTLR start "ruleDate_Literal"
	// InternalStructuredTextParser.g:1693:1: ruleDate_Literal : ( (
	// rule__Date_Literal__Group__0 ) ) ;
	public final void ruleDate_Literal() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1697:2: ( ( ( rule__Date_Literal__Group__0 ) )
			// )
			// InternalStructuredTextParser.g:1698:2: ( ( rule__Date_Literal__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1698:2: ( ( rule__Date_Literal__Group__0 ) )
				// InternalStructuredTextParser.g:1699:3: ( rule__Date_Literal__Group__0 )
				{
					before(grammarAccess.getDate_LiteralAccess().getGroup());
					// InternalStructuredTextParser.g:1700:3: ( rule__Date_Literal__Group__0 )
					// InternalStructuredTextParser.g:1700:4: rule__Date_Literal__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Date_Literal__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getDate_LiteralAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDate_Literal"

	// $ANTLR start "entryRuleYear"
	// InternalStructuredTextParser.g:1709:1: entryRuleYear : ruleYear EOF ;
	public final void entryRuleYear() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1710:1: ( ruleYear EOF )
			// InternalStructuredTextParser.g:1711:1: ruleYear EOF
			{
				before(grammarAccess.getYearRule());
				pushFollow(FOLLOW_1);
				ruleYear();

				state._fsp--;

				after(grammarAccess.getYearRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleYear"

	// $ANTLR start "ruleYear"
	// InternalStructuredTextParser.g:1718:1: ruleYear : ( RULE_UNSIGNED_INT ) ;
	public final void ruleYear() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1722:2: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:1723:2: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:1723:2: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:1724:3: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getYearAccess().getUNSIGNED_INTTerminalRuleCall());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getYearAccess().getUNSIGNED_INTTerminalRuleCall());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleYear"

	// $ANTLR start "entryRuleMonth"
	// InternalStructuredTextParser.g:1734:1: entryRuleMonth : ruleMonth EOF ;
	public final void entryRuleMonth() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1735:1: ( ruleMonth EOF )
			// InternalStructuredTextParser.g:1736:1: ruleMonth EOF
			{
				before(grammarAccess.getMonthRule());
				pushFollow(FOLLOW_1);
				ruleMonth();

				state._fsp--;

				after(grammarAccess.getMonthRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleMonth"

	// $ANTLR start "ruleMonth"
	// InternalStructuredTextParser.g:1743:1: ruleMonth : ( RULE_UNSIGNED_INT ) ;
	public final void ruleMonth() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1747:2: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:1748:2: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:1748:2: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:1749:3: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getMonthAccess().getUNSIGNED_INTTerminalRuleCall());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getMonthAccess().getUNSIGNED_INTTerminalRuleCall());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleMonth"

	// $ANTLR start "entryRuleDay"
	// InternalStructuredTextParser.g:1759:1: entryRuleDay : ruleDay EOF ;
	public final void entryRuleDay() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1760:1: ( ruleDay EOF )
			// InternalStructuredTextParser.g:1761:1: ruleDay EOF
			{
				before(grammarAccess.getDayRule());
				pushFollow(FOLLOW_1);
				ruleDay();

				state._fsp--;

				after(grammarAccess.getDayRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDay"

	// $ANTLR start "ruleDay"
	// InternalStructuredTextParser.g:1768:1: ruleDay : ( RULE_UNSIGNED_INT ) ;
	public final void ruleDay() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1772:2: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:1773:2: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:1773:2: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:1774:3: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getDayAccess().getUNSIGNED_INTTerminalRuleCall());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getDayAccess().getUNSIGNED_INTTerminalRuleCall());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDay"

	// $ANTLR start "entryRuleDate_And_Time"
	// InternalStructuredTextParser.g:1784:1: entryRuleDate_And_Time :
	// ruleDate_And_Time EOF ;
	public final void entryRuleDate_And_Time() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1785:1: ( ruleDate_And_Time EOF )
			// InternalStructuredTextParser.g:1786:1: ruleDate_And_Time EOF
			{
				before(grammarAccess.getDate_And_TimeRule());
				pushFollow(FOLLOW_1);
				ruleDate_And_Time();

				state._fsp--;

				after(grammarAccess.getDate_And_TimeRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDate_And_Time"

	// $ANTLR start "ruleDate_And_Time"
	// InternalStructuredTextParser.g:1793:1: ruleDate_And_Time : ( (
	// rule__Date_And_Time__Group__0 ) ) ;
	public final void ruleDate_And_Time() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1797:2: ( ( ( rule__Date_And_Time__Group__0 )
			// ) )
			// InternalStructuredTextParser.g:1798:2: ( ( rule__Date_And_Time__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1798:2: ( ( rule__Date_And_Time__Group__0 ) )
				// InternalStructuredTextParser.g:1799:3: ( rule__Date_And_Time__Group__0 )
				{
					before(grammarAccess.getDate_And_TimeAccess().getGroup());
					// InternalStructuredTextParser.g:1800:3: ( rule__Date_And_Time__Group__0 )
					// InternalStructuredTextParser.g:1800:4: rule__Date_And_Time__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Date_And_Time__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getDate_And_TimeAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDate_And_Time"

	// $ANTLR start "entryRuleDate_And_Time_Value"
	// InternalStructuredTextParser.g:1809:1: entryRuleDate_And_Time_Value :
	// ruleDate_And_Time_Value EOF ;
	public final void entryRuleDate_And_Time_Value() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1810:1: ( ruleDate_And_Time_Value EOF )
			// InternalStructuredTextParser.g:1811:1: ruleDate_And_Time_Value EOF
			{
				before(grammarAccess.getDate_And_Time_ValueRule());
				pushFollow(FOLLOW_1);
				ruleDate_And_Time_Value();

				state._fsp--;

				after(grammarAccess.getDate_And_Time_ValueRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleDate_And_Time_Value"

	// $ANTLR start "ruleDate_And_Time_Value"
	// InternalStructuredTextParser.g:1818:1: ruleDate_And_Time_Value : ( (
	// rule__Date_And_Time_Value__Group__0 ) ) ;
	public final void ruleDate_And_Time_Value() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1822:2: ( ( (
			// rule__Date_And_Time_Value__Group__0 ) ) )
			// InternalStructuredTextParser.g:1823:2: ( (
			// rule__Date_And_Time_Value__Group__0 ) )
			{
				// InternalStructuredTextParser.g:1823:2: ( (
				// rule__Date_And_Time_Value__Group__0 ) )
				// InternalStructuredTextParser.g:1824:3: ( rule__Date_And_Time_Value__Group__0
				// )
				{
					before(grammarAccess.getDate_And_Time_ValueAccess().getGroup());
					// InternalStructuredTextParser.g:1825:3: ( rule__Date_And_Time_Value__Group__0
					// )
					// InternalStructuredTextParser.g:1825:4: rule__Date_And_Time_Value__Group__0
					{
						pushFollow(FOLLOW_2);
						rule__Date_And_Time_Value__Group__0();

						state._fsp--;

					}

					after(grammarAccess.getDate_And_Time_ValueAccess().getGroup());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDate_And_Time_Value"

	// $ANTLR start "entryRuleType_Name"
	// InternalStructuredTextParser.g:1834:1: entryRuleType_Name : ruleType_Name EOF
	// ;
	public final void entryRuleType_Name() throws RecognitionException {
		try {
			// InternalStructuredTextParser.g:1835:1: ( ruleType_Name EOF )
			// InternalStructuredTextParser.g:1836:1: ruleType_Name EOF
			{
				before(grammarAccess.getType_NameRule());
				pushFollow(FOLLOW_1);
				ruleType_Name();

				state._fsp--;

				after(grammarAccess.getType_NameRule());
				match(input, EOF, FOLLOW_2);

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {
		}
		return;
	}
	// $ANTLR end "entryRuleType_Name"

	// $ANTLR start "ruleType_Name"
	// InternalStructuredTextParser.g:1843:1: ruleType_Name : ( (
	// rule__Type_Name__Alternatives ) ) ;
	public final void ruleType_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1847:2: ( ( ( rule__Type_Name__Alternatives )
			// ) )
			// InternalStructuredTextParser.g:1848:2: ( ( rule__Type_Name__Alternatives ) )
			{
				// InternalStructuredTextParser.g:1848:2: ( ( rule__Type_Name__Alternatives ) )
				// InternalStructuredTextParser.g:1849:3: ( rule__Type_Name__Alternatives )
				{
					before(grammarAccess.getType_NameAccess().getAlternatives());
					// InternalStructuredTextParser.g:1850:3: ( rule__Type_Name__Alternatives )
					// InternalStructuredTextParser.g:1850:4: rule__Type_Name__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Type_Name__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getType_NameAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleType_Name"

	// $ANTLR start "ruleOr_Operator"
	// InternalStructuredTextParser.g:1859:1: ruleOr_Operator : ( ( OR ) ) ;
	public final void ruleOr_Operator() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1863:1: ( ( ( OR ) ) )
			// InternalStructuredTextParser.g:1864:2: ( ( OR ) )
			{
				// InternalStructuredTextParser.g:1864:2: ( ( OR ) )
				// InternalStructuredTextParser.g:1865:3: ( OR )
				{
					before(grammarAccess.getOr_OperatorAccess().getOREnumLiteralDeclaration());
					// InternalStructuredTextParser.g:1866:3: ( OR )
					// InternalStructuredTextParser.g:1866:4: OR
					{
						match(input, OR, FOLLOW_2);

					}

					after(grammarAccess.getOr_OperatorAccess().getOREnumLiteralDeclaration());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleOr_Operator"

	// $ANTLR start "ruleXor_Operator"
	// InternalStructuredTextParser.g:1875:1: ruleXor_Operator : ( ( XOR ) ) ;
	public final void ruleXor_Operator() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1879:1: ( ( ( XOR ) ) )
			// InternalStructuredTextParser.g:1880:2: ( ( XOR ) )
			{
				// InternalStructuredTextParser.g:1880:2: ( ( XOR ) )
				// InternalStructuredTextParser.g:1881:3: ( XOR )
				{
					before(grammarAccess.getXor_OperatorAccess().getXOREnumLiteralDeclaration());
					// InternalStructuredTextParser.g:1882:3: ( XOR )
					// InternalStructuredTextParser.g:1882:4: XOR
					{
						match(input, XOR, FOLLOW_2);

					}

					after(grammarAccess.getXor_OperatorAccess().getXOREnumLiteralDeclaration());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleXor_Operator"

	// $ANTLR start "ruleAnd_Operator"
	// InternalStructuredTextParser.g:1891:1: ruleAnd_Operator : ( (
	// rule__And_Operator__Alternatives ) ) ;
	public final void ruleAnd_Operator() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1895:1: ( ( ( rule__And_Operator__Alternatives
			// ) ) )
			// InternalStructuredTextParser.g:1896:2: ( ( rule__And_Operator__Alternatives )
			// )
			{
				// InternalStructuredTextParser.g:1896:2: ( ( rule__And_Operator__Alternatives )
				// )
				// InternalStructuredTextParser.g:1897:3: ( rule__And_Operator__Alternatives )
				{
					before(grammarAccess.getAnd_OperatorAccess().getAlternatives());
					// InternalStructuredTextParser.g:1898:3: ( rule__And_Operator__Alternatives )
					// InternalStructuredTextParser.g:1898:4: rule__And_Operator__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__And_Operator__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getAnd_OperatorAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleAnd_Operator"

	// $ANTLR start "ruleCompare_Operator"
	// InternalStructuredTextParser.g:1907:1: ruleCompare_Operator : ( (
	// rule__Compare_Operator__Alternatives ) ) ;
	public final void ruleCompare_Operator() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1911:1: ( ( (
			// rule__Compare_Operator__Alternatives ) ) )
			// InternalStructuredTextParser.g:1912:2: ( (
			// rule__Compare_Operator__Alternatives ) )
			{
				// InternalStructuredTextParser.g:1912:2: ( (
				// rule__Compare_Operator__Alternatives ) )
				// InternalStructuredTextParser.g:1913:3: ( rule__Compare_Operator__Alternatives
				// )
				{
					before(grammarAccess.getCompare_OperatorAccess().getAlternatives());
					// InternalStructuredTextParser.g:1914:3: ( rule__Compare_Operator__Alternatives
					// )
					// InternalStructuredTextParser.g:1914:4: rule__Compare_Operator__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Compare_Operator__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getCompare_OperatorAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleCompare_Operator"

	// $ANTLR start "ruleEqu_Operator"
	// InternalStructuredTextParser.g:1923:1: ruleEqu_Operator : ( (
	// rule__Equ_Operator__Alternatives ) ) ;
	public final void ruleEqu_Operator() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1927:1: ( ( ( rule__Equ_Operator__Alternatives
			// ) ) )
			// InternalStructuredTextParser.g:1928:2: ( ( rule__Equ_Operator__Alternatives )
			// )
			{
				// InternalStructuredTextParser.g:1928:2: ( ( rule__Equ_Operator__Alternatives )
				// )
				// InternalStructuredTextParser.g:1929:3: ( rule__Equ_Operator__Alternatives )
				{
					before(grammarAccess.getEqu_OperatorAccess().getAlternatives());
					// InternalStructuredTextParser.g:1930:3: ( rule__Equ_Operator__Alternatives )
					// InternalStructuredTextParser.g:1930:4: rule__Equ_Operator__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Equ_Operator__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getEqu_OperatorAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleEqu_Operator"

	// $ANTLR start "ruleAdd_Operator"
	// InternalStructuredTextParser.g:1939:1: ruleAdd_Operator : ( (
	// rule__Add_Operator__Alternatives ) ) ;
	public final void ruleAdd_Operator() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1943:1: ( ( ( rule__Add_Operator__Alternatives
			// ) ) )
			// InternalStructuredTextParser.g:1944:2: ( ( rule__Add_Operator__Alternatives )
			// )
			{
				// InternalStructuredTextParser.g:1944:2: ( ( rule__Add_Operator__Alternatives )
				// )
				// InternalStructuredTextParser.g:1945:3: ( rule__Add_Operator__Alternatives )
				{
					before(grammarAccess.getAdd_OperatorAccess().getAlternatives());
					// InternalStructuredTextParser.g:1946:3: ( rule__Add_Operator__Alternatives )
					// InternalStructuredTextParser.g:1946:4: rule__Add_Operator__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Add_Operator__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getAdd_OperatorAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleAdd_Operator"

	// $ANTLR start "ruleTerm_Operator"
	// InternalStructuredTextParser.g:1955:1: ruleTerm_Operator : ( (
	// rule__Term_Operator__Alternatives ) ) ;
	public final void ruleTerm_Operator() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1959:1: ( ( (
			// rule__Term_Operator__Alternatives ) ) )
			// InternalStructuredTextParser.g:1960:2: ( ( rule__Term_Operator__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:1960:2: ( ( rule__Term_Operator__Alternatives
				// ) )
				// InternalStructuredTextParser.g:1961:3: ( rule__Term_Operator__Alternatives )
				{
					before(grammarAccess.getTerm_OperatorAccess().getAlternatives());
					// InternalStructuredTextParser.g:1962:3: ( rule__Term_Operator__Alternatives )
					// InternalStructuredTextParser.g:1962:4: rule__Term_Operator__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Term_Operator__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getTerm_OperatorAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleTerm_Operator"

	// $ANTLR start "rulePower_Operator"
	// InternalStructuredTextParser.g:1971:1: rulePower_Operator : ( (
	// AsteriskAsterisk ) ) ;
	public final void rulePower_Operator() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1975:1: ( ( ( AsteriskAsterisk ) ) )
			// InternalStructuredTextParser.g:1976:2: ( ( AsteriskAsterisk ) )
			{
				// InternalStructuredTextParser.g:1976:2: ( ( AsteriskAsterisk ) )
				// InternalStructuredTextParser.g:1977:3: ( AsteriskAsterisk )
				{
					before(grammarAccess.getPower_OperatorAccess().getPOWEREnumLiteralDeclaration());
					// InternalStructuredTextParser.g:1978:3: ( AsteriskAsterisk )
					// InternalStructuredTextParser.g:1978:4: AsteriskAsterisk
					{
						match(input, AsteriskAsterisk, FOLLOW_2);

					}

					after(grammarAccess.getPower_OperatorAccess().getPOWEREnumLiteralDeclaration());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rulePower_Operator"

	// $ANTLR start "ruleUnary_Operator"
	// InternalStructuredTextParser.g:1987:1: ruleUnary_Operator : ( (
	// rule__Unary_Operator__Alternatives ) ) ;
	public final void ruleUnary_Operator() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:1991:1: ( ( (
			// rule__Unary_Operator__Alternatives ) ) )
			// InternalStructuredTextParser.g:1992:2: ( ( rule__Unary_Operator__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:1992:2: ( ( rule__Unary_Operator__Alternatives
				// ) )
				// InternalStructuredTextParser.g:1993:3: ( rule__Unary_Operator__Alternatives )
				{
					before(grammarAccess.getUnary_OperatorAccess().getAlternatives());
					// InternalStructuredTextParser.g:1994:3: ( rule__Unary_Operator__Alternatives )
					// InternalStructuredTextParser.g:1994:4: rule__Unary_Operator__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Unary_Operator__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getUnary_OperatorAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleUnary_Operator"

	// $ANTLR start "ruleDuration_Unit"
	// InternalStructuredTextParser.g:2003:1: ruleDuration_Unit : ( (
	// rule__Duration_Unit__Alternatives ) ) ;
	public final void ruleDuration_Unit() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2007:1: ( ( (
			// rule__Duration_Unit__Alternatives ) ) )
			// InternalStructuredTextParser.g:2008:2: ( ( rule__Duration_Unit__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:2008:2: ( ( rule__Duration_Unit__Alternatives
				// ) )
				// InternalStructuredTextParser.g:2009:3: ( rule__Duration_Unit__Alternatives )
				{
					before(grammarAccess.getDuration_UnitAccess().getAlternatives());
					// InternalStructuredTextParser.g:2010:3: ( rule__Duration_Unit__Alternatives )
					// InternalStructuredTextParser.g:2010:4: rule__Duration_Unit__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Duration_Unit__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getDuration_UnitAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDuration_Unit"

	// $ANTLR start "ruleInt_Type_Name"
	// InternalStructuredTextParser.g:2019:1: ruleInt_Type_Name : ( (
	// rule__Int_Type_Name__Alternatives ) ) ;
	public final void ruleInt_Type_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2023:1: ( ( (
			// rule__Int_Type_Name__Alternatives ) ) )
			// InternalStructuredTextParser.g:2024:2: ( ( rule__Int_Type_Name__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:2024:2: ( ( rule__Int_Type_Name__Alternatives
				// ) )
				// InternalStructuredTextParser.g:2025:3: ( rule__Int_Type_Name__Alternatives )
				{
					before(grammarAccess.getInt_Type_NameAccess().getAlternatives());
					// InternalStructuredTextParser.g:2026:3: ( rule__Int_Type_Name__Alternatives )
					// InternalStructuredTextParser.g:2026:4: rule__Int_Type_Name__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Int_Type_Name__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getInt_Type_NameAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleInt_Type_Name"

	// $ANTLR start "ruleReal_Type_Name"
	// InternalStructuredTextParser.g:2035:1: ruleReal_Type_Name : ( (
	// rule__Real_Type_Name__Alternatives ) ) ;
	public final void ruleReal_Type_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2039:1: ( ( (
			// rule__Real_Type_Name__Alternatives ) ) )
			// InternalStructuredTextParser.g:2040:2: ( ( rule__Real_Type_Name__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:2040:2: ( ( rule__Real_Type_Name__Alternatives
				// ) )
				// InternalStructuredTextParser.g:2041:3: ( rule__Real_Type_Name__Alternatives )
				{
					before(grammarAccess.getReal_Type_NameAccess().getAlternatives());
					// InternalStructuredTextParser.g:2042:3: ( rule__Real_Type_Name__Alternatives )
					// InternalStructuredTextParser.g:2042:4: rule__Real_Type_Name__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Real_Type_Name__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getReal_Type_NameAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleReal_Type_Name"

	// $ANTLR start "ruleString_Type_Name"
	// InternalStructuredTextParser.g:2051:1: ruleString_Type_Name : ( (
	// rule__String_Type_Name__Alternatives ) ) ;
	public final void ruleString_Type_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2055:1: ( ( (
			// rule__String_Type_Name__Alternatives ) ) )
			// InternalStructuredTextParser.g:2056:2: ( (
			// rule__String_Type_Name__Alternatives ) )
			{
				// InternalStructuredTextParser.g:2056:2: ( (
				// rule__String_Type_Name__Alternatives ) )
				// InternalStructuredTextParser.g:2057:3: ( rule__String_Type_Name__Alternatives
				// )
				{
					before(grammarAccess.getString_Type_NameAccess().getAlternatives());
					// InternalStructuredTextParser.g:2058:3: ( rule__String_Type_Name__Alternatives
					// )
					// InternalStructuredTextParser.g:2058:4: rule__String_Type_Name__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__String_Type_Name__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getString_Type_NameAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleString_Type_Name"

	// $ANTLR start "ruleTime_Type_Name"
	// InternalStructuredTextParser.g:2067:1: ruleTime_Type_Name : ( (
	// rule__Time_Type_Name__Alternatives ) ) ;
	public final void ruleTime_Type_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2071:1: ( ( (
			// rule__Time_Type_Name__Alternatives ) ) )
			// InternalStructuredTextParser.g:2072:2: ( ( rule__Time_Type_Name__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:2072:2: ( ( rule__Time_Type_Name__Alternatives
				// ) )
				// InternalStructuredTextParser.g:2073:3: ( rule__Time_Type_Name__Alternatives )
				{
					before(grammarAccess.getTime_Type_NameAccess().getAlternatives());
					// InternalStructuredTextParser.g:2074:3: ( rule__Time_Type_Name__Alternatives )
					// InternalStructuredTextParser.g:2074:4: rule__Time_Type_Name__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Time_Type_Name__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getTime_Type_NameAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleTime_Type_Name"

	// $ANTLR start "ruleTod_Type_Name"
	// InternalStructuredTextParser.g:2083:1: ruleTod_Type_Name : ( (
	// rule__Tod_Type_Name__Alternatives ) ) ;
	public final void ruleTod_Type_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2087:1: ( ( (
			// rule__Tod_Type_Name__Alternatives ) ) )
			// InternalStructuredTextParser.g:2088:2: ( ( rule__Tod_Type_Name__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:2088:2: ( ( rule__Tod_Type_Name__Alternatives
				// ) )
				// InternalStructuredTextParser.g:2089:3: ( rule__Tod_Type_Name__Alternatives )
				{
					before(grammarAccess.getTod_Type_NameAccess().getAlternatives());
					// InternalStructuredTextParser.g:2090:3: ( rule__Tod_Type_Name__Alternatives )
					// InternalStructuredTextParser.g:2090:4: rule__Tod_Type_Name__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Tod_Type_Name__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getTod_Type_NameAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleTod_Type_Name"

	// $ANTLR start "ruleDate_Type_Name"
	// InternalStructuredTextParser.g:2099:1: ruleDate_Type_Name : ( (
	// rule__Date_Type_Name__Alternatives ) ) ;
	public final void ruleDate_Type_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2103:1: ( ( (
			// rule__Date_Type_Name__Alternatives ) ) )
			// InternalStructuredTextParser.g:2104:2: ( ( rule__Date_Type_Name__Alternatives
			// ) )
			{
				// InternalStructuredTextParser.g:2104:2: ( ( rule__Date_Type_Name__Alternatives
				// ) )
				// InternalStructuredTextParser.g:2105:3: ( rule__Date_Type_Name__Alternatives )
				{
					before(grammarAccess.getDate_Type_NameAccess().getAlternatives());
					// InternalStructuredTextParser.g:2106:3: ( rule__Date_Type_Name__Alternatives )
					// InternalStructuredTextParser.g:2106:4: rule__Date_Type_Name__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__Date_Type_Name__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getDate_Type_NameAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDate_Type_Name"

	// $ANTLR start "ruleDT_Type_Name"
	// InternalStructuredTextParser.g:2115:1: ruleDT_Type_Name : ( (
	// rule__DT_Type_Name__Alternatives ) ) ;
	public final void ruleDT_Type_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2119:1: ( ( ( rule__DT_Type_Name__Alternatives
			// ) ) )
			// InternalStructuredTextParser.g:2120:2: ( ( rule__DT_Type_Name__Alternatives )
			// )
			{
				// InternalStructuredTextParser.g:2120:2: ( ( rule__DT_Type_Name__Alternatives )
				// )
				// InternalStructuredTextParser.g:2121:3: ( rule__DT_Type_Name__Alternatives )
				{
					before(grammarAccess.getDT_Type_NameAccess().getAlternatives());
					// InternalStructuredTextParser.g:2122:3: ( rule__DT_Type_Name__Alternatives )
					// InternalStructuredTextParser.g:2122:4: rule__DT_Type_Name__Alternatives
					{
						pushFollow(FOLLOW_2);
						rule__DT_Type_Name__Alternatives();

						state._fsp--;

					}

					after(grammarAccess.getDT_Type_NameAccess().getAlternatives());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleDT_Type_Name"

	// $ANTLR start "ruleBool_Type_Name"
	// InternalStructuredTextParser.g:2131:1: ruleBool_Type_Name : ( ( BOOL ) ) ;
	public final void ruleBool_Type_Name() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2135:1: ( ( ( BOOL ) ) )
			// InternalStructuredTextParser.g:2136:2: ( ( BOOL ) )
			{
				// InternalStructuredTextParser.g:2136:2: ( ( BOOL ) )
				// InternalStructuredTextParser.g:2137:3: ( BOOL )
				{
					before(grammarAccess.getBool_Type_NameAccess().getBOOLEnumLiteralDeclaration());
					// InternalStructuredTextParser.g:2138:3: ( BOOL )
					// InternalStructuredTextParser.g:2138:4: BOOL
					{
						match(input, BOOL, FOLLOW_2);

					}

					after(grammarAccess.getBool_Type_NameAccess().getBOOLEnumLiteralDeclaration());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "ruleBool_Type_Name"

	// $ANTLR start "rule__Var_Decl_Init__Alternatives"
	// InternalStructuredTextParser.g:2146:1: rule__Var_Decl_Init__Alternatives : (
	// ( ruleVar_Decl_Local ) | ( ruleVar_Decl_Located ) );
	public final void rule__Var_Decl_Init__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2150:1: ( ( ruleVar_Decl_Local ) | (
			// ruleVar_Decl_Located ) )
			int alt1 = 2;
			int LA1_0 = input.LA(1);

			if ((LA1_0 == CONSTANT)) {
				alt1 = 1;
			} else if ((LA1_0 == RULE_ID)) {
				int LA1_2 = input.LA(2);

				if ((LA1_2 == AT)) {
					alt1 = 2;
				} else if ((LA1_2 == Colon)) {
					alt1 = 1;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 1, 2, input);

					throw nvae;
				}
			} else {
				NoViableAltException nvae = new NoViableAltException("", 1, 0, input);

				throw nvae;
			}
			switch (alt1) {
			case 1:
			// InternalStructuredTextParser.g:2151:2: ( ruleVar_Decl_Local )
			{
				// InternalStructuredTextParser.g:2151:2: ( ruleVar_Decl_Local )
				// InternalStructuredTextParser.g:2152:3: ruleVar_Decl_Local
				{
					before(grammarAccess.getVar_Decl_InitAccess().getVar_Decl_LocalParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleVar_Decl_Local();

					state._fsp--;

					after(grammarAccess.getVar_Decl_InitAccess().getVar_Decl_LocalParserRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2157:2: ( ruleVar_Decl_Located )
			{
				// InternalStructuredTextParser.g:2157:2: ( ruleVar_Decl_Located )
				// InternalStructuredTextParser.g:2158:3: ruleVar_Decl_Located
				{
					before(grammarAccess.getVar_Decl_InitAccess().getVar_Decl_LocatedParserRuleCall_1());
					pushFollow(FOLLOW_2);
					ruleVar_Decl_Located();

					state._fsp--;

					after(grammarAccess.getVar_Decl_InitAccess().getVar_Decl_LocatedParserRuleCall_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Init__Alternatives"

	// $ANTLR start "rule__Stmt__Alternatives"
	// InternalStructuredTextParser.g:2167:1: rule__Stmt__Alternatives : ( (
	// ruleAssign_Stmt ) | ( ruleSubprog_Ctrl_Stmt ) | ( ruleSelection_Stmt ) | (
	// ruleIteration_Stmt ) );
	public final void rule__Stmt__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2171:1: ( ( ruleAssign_Stmt ) | (
			// ruleSubprog_Ctrl_Stmt ) | ( ruleSelection_Stmt ) | ( ruleIteration_Stmt ) )
			int alt2 = 4;
			switch (input.LA(1)) {
			case RULE_ID: {
				int LA2_1 = input.LA(2);

				if ((LA2_1 == LeftParenthesis)) {
					alt2 = 2;
				} else if (((LA2_1 >= B && LA2_1 <= X) || LA2_1 == ColonEqualsSign || LA2_1 == FullStop
						|| LA2_1 == LeftSquareBracket)) {
					alt2 = 1;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 2, 1, input);

					throw nvae;
				}
			}
				break;
			case DT:
			case LT:
			case T: {
				alt2 = 1;
			}
				break;
			case RETURN:
			case SUPER:
			case TIME: {
				alt2 = 2;
			}
				break;
			case CASE:
			case IF: {
				alt2 = 3;
			}
				break;
			case CONTINUE:
			case REPEAT:
			case WHILE:
			case EXIT:
			case FOR: {
				alt2 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 2, 0, input);

				throw nvae;
			}

			switch (alt2) {
			case 1:
			// InternalStructuredTextParser.g:2172:2: ( ruleAssign_Stmt )
			{
				// InternalStructuredTextParser.g:2172:2: ( ruleAssign_Stmt )
				// InternalStructuredTextParser.g:2173:3: ruleAssign_Stmt
				{
					before(grammarAccess.getStmtAccess().getAssign_StmtParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleAssign_Stmt();

					state._fsp--;

					after(grammarAccess.getStmtAccess().getAssign_StmtParserRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2178:2: ( ruleSubprog_Ctrl_Stmt )
			{
				// InternalStructuredTextParser.g:2178:2: ( ruleSubprog_Ctrl_Stmt )
				// InternalStructuredTextParser.g:2179:3: ruleSubprog_Ctrl_Stmt
				{
					before(grammarAccess.getStmtAccess().getSubprog_Ctrl_StmtParserRuleCall_1());
					pushFollow(FOLLOW_2);
					ruleSubprog_Ctrl_Stmt();

					state._fsp--;

					after(grammarAccess.getStmtAccess().getSubprog_Ctrl_StmtParserRuleCall_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2184:2: ( ruleSelection_Stmt )
			{
				// InternalStructuredTextParser.g:2184:2: ( ruleSelection_Stmt )
				// InternalStructuredTextParser.g:2185:3: ruleSelection_Stmt
				{
					before(grammarAccess.getStmtAccess().getSelection_StmtParserRuleCall_2());
					pushFollow(FOLLOW_2);
					ruleSelection_Stmt();

					state._fsp--;

					after(grammarAccess.getStmtAccess().getSelection_StmtParserRuleCall_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:2190:2: ( ruleIteration_Stmt )
			{
				// InternalStructuredTextParser.g:2190:2: ( ruleIteration_Stmt )
				// InternalStructuredTextParser.g:2191:3: ruleIteration_Stmt
				{
					before(grammarAccess.getStmtAccess().getIteration_StmtParserRuleCall_3());
					pushFollow(FOLLOW_2);
					ruleIteration_Stmt();

					state._fsp--;

					after(grammarAccess.getStmtAccess().getIteration_StmtParserRuleCall_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Stmt__Alternatives"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Alternatives"
	// InternalStructuredTextParser.g:2200:1: rule__Subprog_Ctrl_Stmt__Alternatives
	// : ( ( ruleFunc_Call ) | ( ( rule__Subprog_Ctrl_Stmt__Group_1__0 ) ) | ( (
	// rule__Subprog_Ctrl_Stmt__Group_2__0 ) ) );
	public final void rule__Subprog_Ctrl_Stmt__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2204:1: ( ( ruleFunc_Call ) | ( (
			// rule__Subprog_Ctrl_Stmt__Group_1__0 ) ) | ( (
			// rule__Subprog_Ctrl_Stmt__Group_2__0 ) ) )
			int alt3 = 3;
			switch (input.LA(1)) {
			case TIME:
			case RULE_ID: {
				alt3 = 1;
			}
				break;
			case SUPER: {
				alt3 = 2;
			}
				break;
			case RETURN: {
				alt3 = 3;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 3, 0, input);

				throw nvae;
			}

			switch (alt3) {
			case 1:
			// InternalStructuredTextParser.g:2205:2: ( ruleFunc_Call )
			{
				// InternalStructuredTextParser.g:2205:2: ( ruleFunc_Call )
				// InternalStructuredTextParser.g:2206:3: ruleFunc_Call
				{
					before(grammarAccess.getSubprog_Ctrl_StmtAccess().getFunc_CallParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleFunc_Call();

					state._fsp--;

					after(grammarAccess.getSubprog_Ctrl_StmtAccess().getFunc_CallParserRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2211:2: ( (
			// rule__Subprog_Ctrl_Stmt__Group_1__0 ) )
			{
				// InternalStructuredTextParser.g:2211:2: ( (
				// rule__Subprog_Ctrl_Stmt__Group_1__0 ) )
				// InternalStructuredTextParser.g:2212:3: ( rule__Subprog_Ctrl_Stmt__Group_1__0
				// )
				{
					before(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_1());
					// InternalStructuredTextParser.g:2213:3: ( rule__Subprog_Ctrl_Stmt__Group_1__0
					// )
					// InternalStructuredTextParser.g:2213:4: rule__Subprog_Ctrl_Stmt__Group_1__0
					{
						pushFollow(FOLLOW_2);
						rule__Subprog_Ctrl_Stmt__Group_1__0();

						state._fsp--;

					}

					after(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2217:2: ( (
			// rule__Subprog_Ctrl_Stmt__Group_2__0 ) )
			{
				// InternalStructuredTextParser.g:2217:2: ( (
				// rule__Subprog_Ctrl_Stmt__Group_2__0 ) )
				// InternalStructuredTextParser.g:2218:3: ( rule__Subprog_Ctrl_Stmt__Group_2__0
				// )
				{
					before(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_2());
					// InternalStructuredTextParser.g:2219:3: ( rule__Subprog_Ctrl_Stmt__Group_2__0
					// )
					// InternalStructuredTextParser.g:2219:4: rule__Subprog_Ctrl_Stmt__Group_2__0
					{
						pushFollow(FOLLOW_2);
						rule__Subprog_Ctrl_Stmt__Group_2__0();

						state._fsp--;

					}

					after(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_2());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Alternatives"

	// $ANTLR start "rule__Selection_Stmt__Alternatives"
	// InternalStructuredTextParser.g:2227:1: rule__Selection_Stmt__Alternatives : (
	// ( ruleIF_Stmt ) | ( ruleCase_Stmt ) );
	public final void rule__Selection_Stmt__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2231:1: ( ( ruleIF_Stmt ) | ( ruleCase_Stmt )
			// )
			int alt4 = 2;
			int LA4_0 = input.LA(1);

			if ((LA4_0 == IF)) {
				alt4 = 1;
			} else if ((LA4_0 == CASE)) {
				alt4 = 2;
			} else {
				NoViableAltException nvae = new NoViableAltException("", 4, 0, input);

				throw nvae;
			}
			switch (alt4) {
			case 1:
			// InternalStructuredTextParser.g:2232:2: ( ruleIF_Stmt )
			{
				// InternalStructuredTextParser.g:2232:2: ( ruleIF_Stmt )
				// InternalStructuredTextParser.g:2233:3: ruleIF_Stmt
				{
					before(grammarAccess.getSelection_StmtAccess().getIF_StmtParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleIF_Stmt();

					state._fsp--;

					after(grammarAccess.getSelection_StmtAccess().getIF_StmtParserRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2238:2: ( ruleCase_Stmt )
			{
				// InternalStructuredTextParser.g:2238:2: ( ruleCase_Stmt )
				// InternalStructuredTextParser.g:2239:3: ruleCase_Stmt
				{
					before(grammarAccess.getSelection_StmtAccess().getCase_StmtParserRuleCall_1());
					pushFollow(FOLLOW_2);
					ruleCase_Stmt();

					state._fsp--;

					after(grammarAccess.getSelection_StmtAccess().getCase_StmtParserRuleCall_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Selection_Stmt__Alternatives"

	// $ANTLR start "rule__Iteration_Stmt__Alternatives"
	// InternalStructuredTextParser.g:2248:1: rule__Iteration_Stmt__Alternatives : (
	// ( ruleFor_Stmt ) | ( ruleWhile_Stmt ) | ( ruleRepeat_Stmt ) | ( (
	// rule__Iteration_Stmt__Group_3__0 ) ) | ( ( rule__Iteration_Stmt__Group_4__0 )
	// ) );
	public final void rule__Iteration_Stmt__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2252:1: ( ( ruleFor_Stmt ) | ( ruleWhile_Stmt
			// ) | ( ruleRepeat_Stmt ) | ( ( rule__Iteration_Stmt__Group_3__0 ) ) | ( (
			// rule__Iteration_Stmt__Group_4__0 ) ) )
			int alt5 = 5;
			switch (input.LA(1)) {
			case FOR: {
				alt5 = 1;
			}
				break;
			case WHILE: {
				alt5 = 2;
			}
				break;
			case REPEAT: {
				alt5 = 3;
			}
				break;
			case EXIT: {
				alt5 = 4;
			}
				break;
			case CONTINUE: {
				alt5 = 5;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 5, 0, input);

				throw nvae;
			}

			switch (alt5) {
			case 1:
			// InternalStructuredTextParser.g:2253:2: ( ruleFor_Stmt )
			{
				// InternalStructuredTextParser.g:2253:2: ( ruleFor_Stmt )
				// InternalStructuredTextParser.g:2254:3: ruleFor_Stmt
				{
					before(grammarAccess.getIteration_StmtAccess().getFor_StmtParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleFor_Stmt();

					state._fsp--;

					after(grammarAccess.getIteration_StmtAccess().getFor_StmtParserRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2259:2: ( ruleWhile_Stmt )
			{
				// InternalStructuredTextParser.g:2259:2: ( ruleWhile_Stmt )
				// InternalStructuredTextParser.g:2260:3: ruleWhile_Stmt
				{
					before(grammarAccess.getIteration_StmtAccess().getWhile_StmtParserRuleCall_1());
					pushFollow(FOLLOW_2);
					ruleWhile_Stmt();

					state._fsp--;

					after(grammarAccess.getIteration_StmtAccess().getWhile_StmtParserRuleCall_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2265:2: ( ruleRepeat_Stmt )
			{
				// InternalStructuredTextParser.g:2265:2: ( ruleRepeat_Stmt )
				// InternalStructuredTextParser.g:2266:3: ruleRepeat_Stmt
				{
					before(grammarAccess.getIteration_StmtAccess().getRepeat_StmtParserRuleCall_2());
					pushFollow(FOLLOW_2);
					ruleRepeat_Stmt();

					state._fsp--;

					after(grammarAccess.getIteration_StmtAccess().getRepeat_StmtParserRuleCall_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:2271:2: ( ( rule__Iteration_Stmt__Group_3__0 )
			// )
			{
				// InternalStructuredTextParser.g:2271:2: ( ( rule__Iteration_Stmt__Group_3__0 )
				// )
				// InternalStructuredTextParser.g:2272:3: ( rule__Iteration_Stmt__Group_3__0 )
				{
					before(grammarAccess.getIteration_StmtAccess().getGroup_3());
					// InternalStructuredTextParser.g:2273:3: ( rule__Iteration_Stmt__Group_3__0 )
					// InternalStructuredTextParser.g:2273:4: rule__Iteration_Stmt__Group_3__0
					{
						pushFollow(FOLLOW_2);
						rule__Iteration_Stmt__Group_3__0();

						state._fsp--;

					}

					after(grammarAccess.getIteration_StmtAccess().getGroup_3());

				}

			}
				break;
			case 5:
			// InternalStructuredTextParser.g:2277:2: ( ( rule__Iteration_Stmt__Group_4__0 )
			// )
			{
				// InternalStructuredTextParser.g:2277:2: ( ( rule__Iteration_Stmt__Group_4__0 )
				// )
				// InternalStructuredTextParser.g:2278:3: ( rule__Iteration_Stmt__Group_4__0 )
				{
					before(grammarAccess.getIteration_StmtAccess().getGroup_4());
					// InternalStructuredTextParser.g:2279:3: ( rule__Iteration_Stmt__Group_4__0 )
					// InternalStructuredTextParser.g:2279:4: rule__Iteration_Stmt__Group_4__0
					{
						pushFollow(FOLLOW_2);
						rule__Iteration_Stmt__Group_4__0();

						state._fsp--;

					}

					after(grammarAccess.getIteration_StmtAccess().getGroup_4());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Iteration_Stmt__Alternatives"

	// $ANTLR start "rule__Unary_Expr__Alternatives"
	// InternalStructuredTextParser.g:2287:1: rule__Unary_Expr__Alternatives : ( ( (
	// rule__Unary_Expr__Group_0__0 ) ) | ( rulePrimary_Expr ) | ( ruleConstant ) );
	public final void rule__Unary_Expr__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2291:1: ( ( ( rule__Unary_Expr__Group_0__0 ) )
			// | ( rulePrimary_Expr ) | ( ruleConstant ) )
			int alt6 = 3;
			alt6 = dfa6.predict(input);
			switch (alt6) {
			case 1:
			// InternalStructuredTextParser.g:2292:2: ( ( rule__Unary_Expr__Group_0__0 ) )
			{
				// InternalStructuredTextParser.g:2292:2: ( ( rule__Unary_Expr__Group_0__0 ) )
				// InternalStructuredTextParser.g:2293:3: ( rule__Unary_Expr__Group_0__0 )
				{
					before(grammarAccess.getUnary_ExprAccess().getGroup_0());
					// InternalStructuredTextParser.g:2294:3: ( rule__Unary_Expr__Group_0__0 )
					// InternalStructuredTextParser.g:2294:4: rule__Unary_Expr__Group_0__0
					{
						pushFollow(FOLLOW_2);
						rule__Unary_Expr__Group_0__0();

						state._fsp--;

					}

					after(grammarAccess.getUnary_ExprAccess().getGroup_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2298:2: ( rulePrimary_Expr )
			{
				// InternalStructuredTextParser.g:2298:2: ( rulePrimary_Expr )
				// InternalStructuredTextParser.g:2299:3: rulePrimary_Expr
				{
					before(grammarAccess.getUnary_ExprAccess().getPrimary_ExprParserRuleCall_1());
					pushFollow(FOLLOW_2);
					rulePrimary_Expr();

					state._fsp--;

					after(grammarAccess.getUnary_ExprAccess().getPrimary_ExprParserRuleCall_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2304:2: ( ruleConstant )
			{
				// InternalStructuredTextParser.g:2304:2: ( ruleConstant )
				// InternalStructuredTextParser.g:2305:3: ruleConstant
				{
					before(grammarAccess.getUnary_ExprAccess().getConstantParserRuleCall_2());
					pushFollow(FOLLOW_2);
					ruleConstant();

					state._fsp--;

					after(grammarAccess.getUnary_ExprAccess().getConstantParserRuleCall_2());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Unary_Expr__Alternatives"

	// $ANTLR start "rule__Primary_Expr__Alternatives"
	// InternalStructuredTextParser.g:2314:1: rule__Primary_Expr__Alternatives : ( (
	// ruleVariable ) | ( ruleFunc_Call ) | ( ( rule__Primary_Expr__Group_2__0 ) )
	// );
	public final void rule__Primary_Expr__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2318:1: ( ( ruleVariable ) | ( ruleFunc_Call )
			// | ( ( rule__Primary_Expr__Group_2__0 ) ) )
			int alt7 = 3;
			switch (input.LA(1)) {
			case RULE_ID: {
				int LA7_1 = input.LA(2);

				if ((LA7_1 == EOF || LA7_1 == END_REPEAT || LA7_1 == THEN || (LA7_1 >= B && LA7_1 <= AND)
						|| LA7_1 == MOD || (LA7_1 >= XOR && LA7_1 <= AsteriskAsterisk)
						|| (LA7_1 >= LessThanSignEqualsSign && LA7_1 <= LessThanSignGreaterThanSign)
						|| LA7_1 == GreaterThanSignEqualsSign || (LA7_1 >= BY && LA7_1 <= DO)
						|| (LA7_1 >= OF && LA7_1 <= TO) || LA7_1 == Ampersand
						|| (LA7_1 >= RightParenthesis && LA7_1 <= Solidus)
						|| (LA7_1 >= Semicolon && LA7_1 <= GreaterThanSign)
						|| (LA7_1 >= LeftSquareBracket && LA7_1 <= RightSquareBracket))) {
					alt7 = 1;
				} else if ((LA7_1 == LeftParenthesis)) {
					alt7 = 2;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 7, 1, input);

					throw nvae;
				}
			}
				break;
			case DT:
			case LT:
			case T: {
				alt7 = 1;
			}
				break;
			case TIME: {
				alt7 = 2;
			}
				break;
			case LeftParenthesis: {
				alt7 = 3;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 7, 0, input);

				throw nvae;
			}

			switch (alt7) {
			case 1:
			// InternalStructuredTextParser.g:2319:2: ( ruleVariable )
			{
				// InternalStructuredTextParser.g:2319:2: ( ruleVariable )
				// InternalStructuredTextParser.g:2320:3: ruleVariable
				{
					before(grammarAccess.getPrimary_ExprAccess().getVariableParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleVariable();

					state._fsp--;

					after(grammarAccess.getPrimary_ExprAccess().getVariableParserRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2325:2: ( ruleFunc_Call )
			{
				// InternalStructuredTextParser.g:2325:2: ( ruleFunc_Call )
				// InternalStructuredTextParser.g:2326:3: ruleFunc_Call
				{
					before(grammarAccess.getPrimary_ExprAccess().getFunc_CallParserRuleCall_1());
					pushFollow(FOLLOW_2);
					ruleFunc_Call();

					state._fsp--;

					after(grammarAccess.getPrimary_ExprAccess().getFunc_CallParserRuleCall_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2331:2: ( ( rule__Primary_Expr__Group_2__0 ) )
			{
				// InternalStructuredTextParser.g:2331:2: ( ( rule__Primary_Expr__Group_2__0 ) )
				// InternalStructuredTextParser.g:2332:3: ( rule__Primary_Expr__Group_2__0 )
				{
					before(grammarAccess.getPrimary_ExprAccess().getGroup_2());
					// InternalStructuredTextParser.g:2333:3: ( rule__Primary_Expr__Group_2__0 )
					// InternalStructuredTextParser.g:2333:4: rule__Primary_Expr__Group_2__0
					{
						pushFollow(FOLLOW_2);
						rule__Primary_Expr__Group_2__0();

						state._fsp--;

					}

					after(grammarAccess.getPrimary_ExprAccess().getGroup_2());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Primary_Expr__Alternatives"

	// $ANTLR start "rule__Func_Call__FuncAlternatives_0_0"
	// InternalStructuredTextParser.g:2341:1: rule__Func_Call__FuncAlternatives_0_0
	// : ( ( RULE_ID ) | ( TIME ) );
	public final void rule__Func_Call__FuncAlternatives_0_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2345:1: ( ( RULE_ID ) | ( TIME ) )
			int alt8 = 2;
			int LA8_0 = input.LA(1);

			if ((LA8_0 == RULE_ID)) {
				alt8 = 1;
			} else if ((LA8_0 == TIME)) {
				alt8 = 2;
			} else {
				NoViableAltException nvae = new NoViableAltException("", 8, 0, input);

				throw nvae;
			}
			switch (alt8) {
			case 1:
			// InternalStructuredTextParser.g:2346:2: ( RULE_ID )
			{
				// InternalStructuredTextParser.g:2346:2: ( RULE_ID )
				// InternalStructuredTextParser.g:2347:3: RULE_ID
				{
					before(grammarAccess.getFunc_CallAccess().getFuncIDTerminalRuleCall_0_0_0());
					match(input, RULE_ID, FOLLOW_2);
					after(grammarAccess.getFunc_CallAccess().getFuncIDTerminalRuleCall_0_0_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2352:2: ( TIME )
			{
				// InternalStructuredTextParser.g:2352:2: ( TIME )
				// InternalStructuredTextParser.g:2353:3: TIME
				{
					before(grammarAccess.getFunc_CallAccess().getFuncTIMEKeyword_0_0_1());
					match(input, TIME, FOLLOW_2);
					after(grammarAccess.getFunc_CallAccess().getFuncTIMEKeyword_0_0_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__FuncAlternatives_0_0"

	// $ANTLR start "rule__Param_Assign__Alternatives"
	// InternalStructuredTextParser.g:2362:1: rule__Param_Assign__Alternatives : ( (
	// ruleParam_Assign_In ) | ( ruleParam_Assign_Out ) );
	public final void rule__Param_Assign__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2366:1: ( ( ruleParam_Assign_In ) | (
			// ruleParam_Assign_Out ) )
			int alt9 = 2;
			switch (input.LA(1)) {
			case RULE_ID: {
				int LA9_1 = input.LA(2);

				if ((LA9_1 == EOF || (LA9_1 >= B && LA9_1 <= AND) || LA9_1 == MOD
						|| (LA9_1 >= XOR && LA9_1 <= LessThanSignGreaterThanSign) || LA9_1 == GreaterThanSignEqualsSign
						|| LA9_1 == OR || (LA9_1 >= Ampersand && LA9_1 <= Solidus)
						|| (LA9_1 >= LessThanSign && LA9_1 <= GreaterThanSign) || LA9_1 == LeftSquareBracket)) {
					alt9 = 1;
				} else if ((LA9_1 == EqualsSignGreaterThanSign)) {
					alt9 = 2;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 9, 1, input);

					throw nvae;
				}
			}
				break;
			case LDATE_AND_TIME:
			case DATE_AND_TIME:
			case LTIME_OF_DAY:
			case TIME_OF_DAY:
			case WSTRING:
			case STRING:
			case FALSE:
			case LDATE:
			case LREAL:
			case LTIME:
			case UDINT:
			case ULINT:
			case USINT:
			case WCHAR:
			case BOOL:
			case CHAR:
			case DATE:
			case DINT:
			case LINT:
			case LTOD:
			case REAL:
			case SINT:
			case TIME:
			case TRUE:
			case UINT:
			case INT:
			case LDT:
			case TOD:
			case DT:
			case LD:
			case LT:
			case LeftParenthesis:
			case PlusSign:
			case HyphenMinus:
			case T:
			case D_1:
			case RULE_BINARY_INT:
			case RULE_OCTAL_INT:
			case RULE_HEX_INT:
			case RULE_UNSIGNED_INT:
			case RULE_S_BYTE_CHAR_STR:
			case RULE_D_BYTE_CHAR_STR: {
				alt9 = 1;
			}
				break;
			case NOT: {
				int LA9_3 = input.LA(2);

				if ((LA9_3 == RULE_ID)) {
					int LA9_5 = input.LA(3);

					if ((LA9_5 == EOF || (LA9_5 >= B && LA9_5 <= AND) || LA9_5 == MOD
							|| (LA9_5 >= XOR && LA9_5 <= AsteriskAsterisk)
							|| (LA9_5 >= LessThanSignEqualsSign && LA9_5 <= LessThanSignGreaterThanSign)
							|| LA9_5 == GreaterThanSignEqualsSign || LA9_5 == OR
							|| (LA9_5 >= Ampersand && LA9_5 <= Solidus)
							|| (LA9_5 >= LessThanSign && LA9_5 <= GreaterThanSign) || LA9_5 == LeftSquareBracket)) {
						alt9 = 1;
					} else if ((LA9_5 == EqualsSignGreaterThanSign)) {
						alt9 = 2;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 9, 5, input);

						throw nvae;
					}
				} else if ((LA9_3 == TIME || LA9_3 == DT || LA9_3 == LT || LA9_3 == LeftParenthesis || LA9_3 == T)) {
					alt9 = 1;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 9, 3, input);

					throw nvae;
				}
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 9, 0, input);

				throw nvae;
			}

			switch (alt9) {
			case 1:
			// InternalStructuredTextParser.g:2367:2: ( ruleParam_Assign_In )
			{
				// InternalStructuredTextParser.g:2367:2: ( ruleParam_Assign_In )
				// InternalStructuredTextParser.g:2368:3: ruleParam_Assign_In
				{
					before(grammarAccess.getParam_AssignAccess().getParam_Assign_InParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleParam_Assign_In();

					state._fsp--;

					after(grammarAccess.getParam_AssignAccess().getParam_Assign_InParserRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2373:2: ( ruleParam_Assign_Out )
			{
				// InternalStructuredTextParser.g:2373:2: ( ruleParam_Assign_Out )
				// InternalStructuredTextParser.g:2374:3: ruleParam_Assign_Out
				{
					before(grammarAccess.getParam_AssignAccess().getParam_Assign_OutParserRuleCall_1());
					pushFollow(FOLLOW_2);
					ruleParam_Assign_Out();

					state._fsp--;

					after(grammarAccess.getParam_AssignAccess().getParam_Assign_OutParserRuleCall_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign__Alternatives"

	// $ANTLR start "rule__Variable_Subscript__Alternatives_0"
	// InternalStructuredTextParser.g:2383:1:
	// rule__Variable_Subscript__Alternatives_0 : ( ( ruleVariable_Primary ) | (
	// ruleVariable_Adapter ) );
	public final void rule__Variable_Subscript__Alternatives_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2387:1: ( ( ruleVariable_Primary ) | (
			// ruleVariable_Adapter ) )
			int alt10 = 2;
			switch (input.LA(1)) {
			case RULE_ID: {
				int LA10_1 = input.LA(2);

				if ((LA10_1 == EOF || LA10_1 == END_REPEAT || LA10_1 == THEN || (LA10_1 >= B && LA10_1 <= AND)
						|| LA10_1 == MOD || (LA10_1 >= XOR && LA10_1 <= LessThanSignGreaterThanSign)
						|| LA10_1 == GreaterThanSignEqualsSign || (LA10_1 >= BY && LA10_1 <= DO)
						|| (LA10_1 >= OF && LA10_1 <= TO) || LA10_1 == Ampersand
						|| (LA10_1 >= RightParenthesis && LA10_1 <= HyphenMinus)
						|| (LA10_1 >= Solidus && LA10_1 <= GreaterThanSign)
						|| (LA10_1 >= LeftSquareBracket && LA10_1 <= RightSquareBracket))) {
					alt10 = 1;
				} else if ((LA10_1 == FullStop)) {
					int LA10_6 = input.LA(3);

					if ((LA10_6 == RULE_UNSIGNED_INT)) {
						alt10 = 1;
					} else if ((LA10_6 == DT || LA10_6 == LT || LA10_6 == T || LA10_6 == RULE_ID)) {
						alt10 = 2;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 10, 6, input);

						throw nvae;
					}
				} else {
					NoViableAltException nvae = new NoViableAltException("", 10, 1, input);

					throw nvae;
				}
			}
				break;
			case T: {
				int LA10_2 = input.LA(2);

				if ((LA10_2 == FullStop)) {
					int LA10_6 = input.LA(3);

					if ((LA10_6 == RULE_UNSIGNED_INT)) {
						alt10 = 1;
					} else if ((LA10_6 == DT || LA10_6 == LT || LA10_6 == T || LA10_6 == RULE_ID)) {
						alt10 = 2;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 10, 6, input);

						throw nvae;
					}
				} else if ((LA10_2 == EOF || LA10_2 == END_REPEAT || LA10_2 == THEN || (LA10_2 >= B && LA10_2 <= AND)
						|| LA10_2 == MOD || (LA10_2 >= XOR && LA10_2 <= LessThanSignGreaterThanSign)
						|| LA10_2 == GreaterThanSignEqualsSign || (LA10_2 >= BY && LA10_2 <= DO)
						|| (LA10_2 >= OF && LA10_2 <= TO) || LA10_2 == Ampersand
						|| (LA10_2 >= RightParenthesis && LA10_2 <= HyphenMinus)
						|| (LA10_2 >= Solidus && LA10_2 <= GreaterThanSign)
						|| (LA10_2 >= LeftSquareBracket && LA10_2 <= RightSquareBracket))) {
					alt10 = 1;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 10, 2, input);

					throw nvae;
				}
			}
				break;
			case LT: {
				int LA10_3 = input.LA(2);

				if ((LA10_3 == EOF || LA10_3 == END_REPEAT || LA10_3 == THEN || (LA10_3 >= B && LA10_3 <= AND)
						|| LA10_3 == MOD || (LA10_3 >= XOR && LA10_3 <= LessThanSignGreaterThanSign)
						|| LA10_3 == GreaterThanSignEqualsSign || (LA10_3 >= BY && LA10_3 <= DO)
						|| (LA10_3 >= OF && LA10_3 <= TO) || LA10_3 == Ampersand
						|| (LA10_3 >= RightParenthesis && LA10_3 <= HyphenMinus)
						|| (LA10_3 >= Solidus && LA10_3 <= GreaterThanSign)
						|| (LA10_3 >= LeftSquareBracket && LA10_3 <= RightSquareBracket))) {
					alt10 = 1;
				} else if ((LA10_3 == FullStop)) {
					int LA10_6 = input.LA(3);

					if ((LA10_6 == RULE_UNSIGNED_INT)) {
						alt10 = 1;
					} else if ((LA10_6 == DT || LA10_6 == LT || LA10_6 == T || LA10_6 == RULE_ID)) {
						alt10 = 2;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 10, 6, input);

						throw nvae;
					}
				} else {
					NoViableAltException nvae = new NoViableAltException("", 10, 3, input);

					throw nvae;
				}
			}
				break;
			case DT: {
				int LA10_4 = input.LA(2);

				if ((LA10_4 == FullStop)) {
					int LA10_6 = input.LA(3);

					if ((LA10_6 == RULE_UNSIGNED_INT)) {
						alt10 = 1;
					} else if ((LA10_6 == DT || LA10_6 == LT || LA10_6 == T || LA10_6 == RULE_ID)) {
						alt10 = 2;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 10, 6, input);

						throw nvae;
					}
				} else if ((LA10_4 == EOF || LA10_4 == END_REPEAT || LA10_4 == THEN || (LA10_4 >= B && LA10_4 <= AND)
						|| LA10_4 == MOD || (LA10_4 >= XOR && LA10_4 <= LessThanSignGreaterThanSign)
						|| LA10_4 == GreaterThanSignEqualsSign || (LA10_4 >= BY && LA10_4 <= DO)
						|| (LA10_4 >= OF && LA10_4 <= TO) || LA10_4 == Ampersand
						|| (LA10_4 >= RightParenthesis && LA10_4 <= HyphenMinus)
						|| (LA10_4 >= Solidus && LA10_4 <= GreaterThanSign)
						|| (LA10_4 >= LeftSquareBracket && LA10_4 <= RightSquareBracket))) {
					alt10 = 1;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 10, 4, input);

					throw nvae;
				}
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 10, 0, input);

				throw nvae;
			}

			switch (alt10) {
			case 1:
			// InternalStructuredTextParser.g:2388:2: ( ruleVariable_Primary )
			{
				// InternalStructuredTextParser.g:2388:2: ( ruleVariable_Primary )
				// InternalStructuredTextParser.g:2389:3: ruleVariable_Primary
				{
					before(grammarAccess.getVariable_SubscriptAccess().getVariable_PrimaryParserRuleCall_0_0());
					pushFollow(FOLLOW_2);
					ruleVariable_Primary();

					state._fsp--;

					after(grammarAccess.getVariable_SubscriptAccess().getVariable_PrimaryParserRuleCall_0_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2394:2: ( ruleVariable_Adapter )
			{
				// InternalStructuredTextParser.g:2394:2: ( ruleVariable_Adapter )
				// InternalStructuredTextParser.g:2395:3: ruleVariable_Adapter
				{
					before(grammarAccess.getVariable_SubscriptAccess().getVariable_AdapterParserRuleCall_0_1());
					pushFollow(FOLLOW_2);
					ruleVariable_Adapter();

					state._fsp--;

					after(grammarAccess.getVariable_SubscriptAccess().getVariable_AdapterParserRuleCall_0_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Alternatives_0"

	// $ANTLR start "rule__Multibit_Part_Access__Alternatives"
	// InternalStructuredTextParser.g:2404:1:
	// rule__Multibit_Part_Access__Alternatives : ( ( (
	// rule__Multibit_Part_Access__Group_0__0 ) ) | ( (
	// rule__Multibit_Part_Access__Group_1__0 ) ) | ( (
	// rule__Multibit_Part_Access__Group_2__0 ) ) | ( (
	// rule__Multibit_Part_Access__Group_3__0 ) ) | ( (
	// rule__Multibit_Part_Access__Group_4__0 ) ) );
	public final void rule__Multibit_Part_Access__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2408:1: ( ( (
			// rule__Multibit_Part_Access__Group_0__0 ) ) | ( (
			// rule__Multibit_Part_Access__Group_1__0 ) ) | ( (
			// rule__Multibit_Part_Access__Group_2__0 ) ) | ( (
			// rule__Multibit_Part_Access__Group_3__0 ) ) | ( (
			// rule__Multibit_Part_Access__Group_4__0 ) ) )
			int alt11 = 5;
			switch (input.LA(1)) {
			case D: {
				alt11 = 1;
			}
				break;
			case W: {
				alt11 = 2;
			}
				break;
			case B: {
				alt11 = 3;
			}
				break;
			case X: {
				alt11 = 4;
			}
				break;
			case FullStop: {
				alt11 = 5;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 11, 0, input);

				throw nvae;
			}

			switch (alt11) {
			case 1:
			// InternalStructuredTextParser.g:2409:2: ( (
			// rule__Multibit_Part_Access__Group_0__0 ) )
			{
				// InternalStructuredTextParser.g:2409:2: ( (
				// rule__Multibit_Part_Access__Group_0__0 ) )
				// InternalStructuredTextParser.g:2410:3: (
				// rule__Multibit_Part_Access__Group_0__0 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_0());
					// InternalStructuredTextParser.g:2411:3: (
					// rule__Multibit_Part_Access__Group_0__0 )
					// InternalStructuredTextParser.g:2411:4: rule__Multibit_Part_Access__Group_0__0
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__Group_0__0();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getGroup_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2415:2: ( (
			// rule__Multibit_Part_Access__Group_1__0 ) )
			{
				// InternalStructuredTextParser.g:2415:2: ( (
				// rule__Multibit_Part_Access__Group_1__0 ) )
				// InternalStructuredTextParser.g:2416:3: (
				// rule__Multibit_Part_Access__Group_1__0 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_1());
					// InternalStructuredTextParser.g:2417:3: (
					// rule__Multibit_Part_Access__Group_1__0 )
					// InternalStructuredTextParser.g:2417:4: rule__Multibit_Part_Access__Group_1__0
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__Group_1__0();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getGroup_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2421:2: ( (
			// rule__Multibit_Part_Access__Group_2__0 ) )
			{
				// InternalStructuredTextParser.g:2421:2: ( (
				// rule__Multibit_Part_Access__Group_2__0 ) )
				// InternalStructuredTextParser.g:2422:3: (
				// rule__Multibit_Part_Access__Group_2__0 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_2());
					// InternalStructuredTextParser.g:2423:3: (
					// rule__Multibit_Part_Access__Group_2__0 )
					// InternalStructuredTextParser.g:2423:4: rule__Multibit_Part_Access__Group_2__0
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__Group_2__0();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getGroup_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:2427:2: ( (
			// rule__Multibit_Part_Access__Group_3__0 ) )
			{
				// InternalStructuredTextParser.g:2427:2: ( (
				// rule__Multibit_Part_Access__Group_3__0 ) )
				// InternalStructuredTextParser.g:2428:3: (
				// rule__Multibit_Part_Access__Group_3__0 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_3());
					// InternalStructuredTextParser.g:2429:3: (
					// rule__Multibit_Part_Access__Group_3__0 )
					// InternalStructuredTextParser.g:2429:4: rule__Multibit_Part_Access__Group_3__0
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__Group_3__0();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getGroup_3());

				}

			}
				break;
			case 5:
			// InternalStructuredTextParser.g:2433:2: ( (
			// rule__Multibit_Part_Access__Group_4__0 ) )
			{
				// InternalStructuredTextParser.g:2433:2: ( (
				// rule__Multibit_Part_Access__Group_4__0 ) )
				// InternalStructuredTextParser.g:2434:3: (
				// rule__Multibit_Part_Access__Group_4__0 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_4());
					// InternalStructuredTextParser.g:2435:3: (
					// rule__Multibit_Part_Access__Group_4__0 )
					// InternalStructuredTextParser.g:2435:4: rule__Multibit_Part_Access__Group_4__0
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__Group_4__0();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getGroup_4());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Alternatives"

	// $ANTLR start "rule__Adapter_Name__Alternatives"
	// InternalStructuredTextParser.g:2443:1: rule__Adapter_Name__Alternatives : ( (
	// RULE_ID ) | ( T ) | ( LT ) | ( DT ) );
	public final void rule__Adapter_Name__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2447:1: ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT
			// ) )
			int alt12 = 4;
			switch (input.LA(1)) {
			case RULE_ID: {
				alt12 = 1;
			}
				break;
			case T: {
				alt12 = 2;
			}
				break;
			case LT: {
				alt12 = 3;
			}
				break;
			case DT: {
				alt12 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 12, 0, input);

				throw nvae;
			}

			switch (alt12) {
			case 1:
			// InternalStructuredTextParser.g:2448:2: ( RULE_ID )
			{
				// InternalStructuredTextParser.g:2448:2: ( RULE_ID )
				// InternalStructuredTextParser.g:2449:3: RULE_ID
				{
					before(grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0());
					match(input, RULE_ID, FOLLOW_2);
					after(grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2454:2: ( T )
			{
				// InternalStructuredTextParser.g:2454:2: ( T )
				// InternalStructuredTextParser.g:2455:3: T
				{
					before(grammarAccess.getAdapter_NameAccess().getTKeyword_1());
					match(input, T, FOLLOW_2);
					after(grammarAccess.getAdapter_NameAccess().getTKeyword_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2460:2: ( LT )
			{
				// InternalStructuredTextParser.g:2460:2: ( LT )
				// InternalStructuredTextParser.g:2461:3: LT
				{
					before(grammarAccess.getAdapter_NameAccess().getLTKeyword_2());
					match(input, LT, FOLLOW_2);
					after(grammarAccess.getAdapter_NameAccess().getLTKeyword_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:2466:2: ( DT )
			{
				// InternalStructuredTextParser.g:2466:2: ( DT )
				// InternalStructuredTextParser.g:2467:3: DT
				{
					before(grammarAccess.getAdapter_NameAccess().getDTKeyword_3());
					match(input, DT, FOLLOW_2);
					after(grammarAccess.getAdapter_NameAccess().getDTKeyword_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Adapter_Name__Alternatives"

	// $ANTLR start "rule__Variable_Name__Alternatives"
	// InternalStructuredTextParser.g:2476:1: rule__Variable_Name__Alternatives : (
	// ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) );
	public final void rule__Variable_Name__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2480:1: ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT
			// ) )
			int alt13 = 4;
			switch (input.LA(1)) {
			case RULE_ID: {
				alt13 = 1;
			}
				break;
			case T: {
				alt13 = 2;
			}
				break;
			case LT: {
				alt13 = 3;
			}
				break;
			case DT: {
				alt13 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 13, 0, input);

				throw nvae;
			}

			switch (alt13) {
			case 1:
			// InternalStructuredTextParser.g:2481:2: ( RULE_ID )
			{
				// InternalStructuredTextParser.g:2481:2: ( RULE_ID )
				// InternalStructuredTextParser.g:2482:3: RULE_ID
				{
					before(grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0());
					match(input, RULE_ID, FOLLOW_2);
					after(grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2487:2: ( T )
			{
				// InternalStructuredTextParser.g:2487:2: ( T )
				// InternalStructuredTextParser.g:2488:3: T
				{
					before(grammarAccess.getVariable_NameAccess().getTKeyword_1());
					match(input, T, FOLLOW_2);
					after(grammarAccess.getVariable_NameAccess().getTKeyword_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2493:2: ( LT )
			{
				// InternalStructuredTextParser.g:2493:2: ( LT )
				// InternalStructuredTextParser.g:2494:3: LT
				{
					before(grammarAccess.getVariable_NameAccess().getLTKeyword_2());
					match(input, LT, FOLLOW_2);
					after(grammarAccess.getVariable_NameAccess().getLTKeyword_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:2499:2: ( DT )
			{
				// InternalStructuredTextParser.g:2499:2: ( DT )
				// InternalStructuredTextParser.g:2500:3: DT
				{
					before(grammarAccess.getVariable_NameAccess().getDTKeyword_3());
					match(input, DT, FOLLOW_2);
					after(grammarAccess.getVariable_NameAccess().getDTKeyword_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Name__Alternatives"

	// $ANTLR start "rule__Constant__Alternatives"
	// InternalStructuredTextParser.g:2509:1: rule__Constant__Alternatives : ( (
	// ruleNumeric_Literal ) | ( ruleChar_Literal ) | ( ruleTime_Literal ) | (
	// ruleBool_Literal ) );
	public final void rule__Constant__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2513:1: ( ( ruleNumeric_Literal ) | (
			// ruleChar_Literal ) | ( ruleTime_Literal ) | ( ruleBool_Literal ) )
			int alt14 = 4;
			switch (input.LA(1)) {
			case LREAL:
			case UDINT:
			case ULINT:
			case USINT:
			case DINT:
			case LINT:
			case REAL:
			case SINT:
			case UINT:
			case INT:
			case PlusSign:
			case HyphenMinus:
			case RULE_BINARY_INT:
			case RULE_OCTAL_INT:
			case RULE_HEX_INT:
			case RULE_UNSIGNED_INT: {
				alt14 = 1;
			}
				break;
			case WSTRING:
			case STRING:
			case WCHAR:
			case CHAR:
			case RULE_S_BYTE_CHAR_STR:
			case RULE_D_BYTE_CHAR_STR: {
				alt14 = 2;
			}
				break;
			case LDATE_AND_TIME:
			case DATE_AND_TIME:
			case LTIME_OF_DAY:
			case TIME_OF_DAY:
			case LDATE:
			case LTIME:
			case DATE:
			case LTOD:
			case TIME:
			case LDT:
			case TOD:
			case DT:
			case LD:
			case LT:
			case T:
			case D_1: {
				alt14 = 3;
			}
				break;
			case FALSE:
			case BOOL:
			case TRUE: {
				alt14 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 14, 0, input);

				throw nvae;
			}

			switch (alt14) {
			case 1:
			// InternalStructuredTextParser.g:2514:2: ( ruleNumeric_Literal )
			{
				// InternalStructuredTextParser.g:2514:2: ( ruleNumeric_Literal )
				// InternalStructuredTextParser.g:2515:3: ruleNumeric_Literal
				{
					before(grammarAccess.getConstantAccess().getNumeric_LiteralParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleNumeric_Literal();

					state._fsp--;

					after(grammarAccess.getConstantAccess().getNumeric_LiteralParserRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2520:2: ( ruleChar_Literal )
			{
				// InternalStructuredTextParser.g:2520:2: ( ruleChar_Literal )
				// InternalStructuredTextParser.g:2521:3: ruleChar_Literal
				{
					before(grammarAccess.getConstantAccess().getChar_LiteralParserRuleCall_1());
					pushFollow(FOLLOW_2);
					ruleChar_Literal();

					state._fsp--;

					after(grammarAccess.getConstantAccess().getChar_LiteralParserRuleCall_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2526:2: ( ruleTime_Literal )
			{
				// InternalStructuredTextParser.g:2526:2: ( ruleTime_Literal )
				// InternalStructuredTextParser.g:2527:3: ruleTime_Literal
				{
					before(grammarAccess.getConstantAccess().getTime_LiteralParserRuleCall_2());
					pushFollow(FOLLOW_2);
					ruleTime_Literal();

					state._fsp--;

					after(grammarAccess.getConstantAccess().getTime_LiteralParserRuleCall_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:2532:2: ( ruleBool_Literal )
			{
				// InternalStructuredTextParser.g:2532:2: ( ruleBool_Literal )
				// InternalStructuredTextParser.g:2533:3: ruleBool_Literal
				{
					before(grammarAccess.getConstantAccess().getBool_LiteralParserRuleCall_3());
					pushFollow(FOLLOW_2);
					ruleBool_Literal();

					state._fsp--;

					after(grammarAccess.getConstantAccess().getBool_LiteralParserRuleCall_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Constant__Alternatives"

	// $ANTLR start "rule__Numeric_Literal__Alternatives"
	// InternalStructuredTextParser.g:2542:1: rule__Numeric_Literal__Alternatives :
	// ( ( ruleInt_Literal ) | ( ruleReal_Literal ) );
	public final void rule__Numeric_Literal__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2546:1: ( ( ruleInt_Literal ) | (
			// ruleReal_Literal ) )
			int alt15 = 2;
			switch (input.LA(1)) {
			case UDINT:
			case ULINT:
			case USINT:
			case DINT:
			case LINT:
			case SINT:
			case UINT:
			case INT:
			case RULE_BINARY_INT:
			case RULE_OCTAL_INT:
			case RULE_HEX_INT: {
				alt15 = 1;
			}
				break;
			case PlusSign: {
				int LA15_2 = input.LA(2);

				if ((LA15_2 == RULE_UNSIGNED_INT)) {
					int LA15_4 = input.LA(3);

					if ((LA15_4 == FullStop)) {
						alt15 = 2;
					} else if ((LA15_4 == EOF || LA15_4 == END_REPEAT || LA15_4 == THEN || LA15_4 == AND
							|| LA15_4 == MOD || (LA15_4 >= XOR && LA15_4 <= AsteriskAsterisk)
							|| (LA15_4 >= LessThanSignEqualsSign && LA15_4 <= LessThanSignGreaterThanSign)
							|| LA15_4 == GreaterThanSignEqualsSign || (LA15_4 >= BY && LA15_4 <= DO)
							|| (LA15_4 >= OF && LA15_4 <= TO) || LA15_4 == Ampersand
							|| (LA15_4 >= RightParenthesis && LA15_4 <= HyphenMinus)
							|| (LA15_4 >= Solidus && LA15_4 <= GreaterThanSign) || LA15_4 == RightSquareBracket)) {
						alt15 = 1;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 15, 4, input);

						throw nvae;
					}
				} else {
					NoViableAltException nvae = new NoViableAltException("", 15, 2, input);

					throw nvae;
				}
			}
				break;
			case HyphenMinus: {
				int LA15_3 = input.LA(2);

				if ((LA15_3 == RULE_UNSIGNED_INT)) {
					int LA15_4 = input.LA(3);

					if ((LA15_4 == FullStop)) {
						alt15 = 2;
					} else if ((LA15_4 == EOF || LA15_4 == END_REPEAT || LA15_4 == THEN || LA15_4 == AND
							|| LA15_4 == MOD || (LA15_4 >= XOR && LA15_4 <= AsteriskAsterisk)
							|| (LA15_4 >= LessThanSignEqualsSign && LA15_4 <= LessThanSignGreaterThanSign)
							|| LA15_4 == GreaterThanSignEqualsSign || (LA15_4 >= BY && LA15_4 <= DO)
							|| (LA15_4 >= OF && LA15_4 <= TO) || LA15_4 == Ampersand
							|| (LA15_4 >= RightParenthesis && LA15_4 <= HyphenMinus)
							|| (LA15_4 >= Solidus && LA15_4 <= GreaterThanSign) || LA15_4 == RightSquareBracket)) {
						alt15 = 1;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 15, 4, input);

						throw nvae;
					}
				} else {
					NoViableAltException nvae = new NoViableAltException("", 15, 3, input);

					throw nvae;
				}
			}
				break;
			case RULE_UNSIGNED_INT: {
				int LA15_4 = input.LA(2);

				if ((LA15_4 == FullStop)) {
					alt15 = 2;
				} else if ((LA15_4 == EOF || LA15_4 == END_REPEAT || LA15_4 == THEN || LA15_4 == AND || LA15_4 == MOD
						|| (LA15_4 >= XOR && LA15_4 <= AsteriskAsterisk)
						|| (LA15_4 >= LessThanSignEqualsSign && LA15_4 <= LessThanSignGreaterThanSign)
						|| LA15_4 == GreaterThanSignEqualsSign || (LA15_4 >= BY && LA15_4 <= DO)
						|| (LA15_4 >= OF && LA15_4 <= TO) || LA15_4 == Ampersand
						|| (LA15_4 >= RightParenthesis && LA15_4 <= HyphenMinus)
						|| (LA15_4 >= Solidus && LA15_4 <= GreaterThanSign) || LA15_4 == RightSquareBracket)) {
					alt15 = 1;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 15, 4, input);

					throw nvae;
				}
			}
				break;
			case LREAL:
			case REAL: {
				alt15 = 2;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 15, 0, input);

				throw nvae;
			}

			switch (alt15) {
			case 1:
			// InternalStructuredTextParser.g:2547:2: ( ruleInt_Literal )
			{
				// InternalStructuredTextParser.g:2547:2: ( ruleInt_Literal )
				// InternalStructuredTextParser.g:2548:3: ruleInt_Literal
				{
					before(grammarAccess.getNumeric_LiteralAccess().getInt_LiteralParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleInt_Literal();

					state._fsp--;

					after(grammarAccess.getNumeric_LiteralAccess().getInt_LiteralParserRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2553:2: ( ruleReal_Literal )
			{
				// InternalStructuredTextParser.g:2553:2: ( ruleReal_Literal )
				// InternalStructuredTextParser.g:2554:3: ruleReal_Literal
				{
					before(grammarAccess.getNumeric_LiteralAccess().getReal_LiteralParserRuleCall_1());
					pushFollow(FOLLOW_2);
					ruleReal_Literal();

					state._fsp--;

					after(grammarAccess.getNumeric_LiteralAccess().getReal_LiteralParserRuleCall_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Numeric_Literal__Alternatives"

	// $ANTLR start "rule__Int_Literal__ValueAlternatives_1_0"
	// InternalStructuredTextParser.g:2563:1:
	// rule__Int_Literal__ValueAlternatives_1_0 : ( ( ruleSigned_Int ) | (
	// RULE_BINARY_INT ) | ( RULE_OCTAL_INT ) | ( RULE_HEX_INT ) );
	public final void rule__Int_Literal__ValueAlternatives_1_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2567:1: ( ( ruleSigned_Int ) | (
			// RULE_BINARY_INT ) | ( RULE_OCTAL_INT ) | ( RULE_HEX_INT ) )
			int alt16 = 4;
			switch (input.LA(1)) {
			case PlusSign:
			case HyphenMinus:
			case RULE_UNSIGNED_INT: {
				alt16 = 1;
			}
				break;
			case RULE_BINARY_INT: {
				alt16 = 2;
			}
				break;
			case RULE_OCTAL_INT: {
				alt16 = 3;
			}
				break;
			case RULE_HEX_INT: {
				alt16 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 16, 0, input);

				throw nvae;
			}

			switch (alt16) {
			case 1:
			// InternalStructuredTextParser.g:2568:2: ( ruleSigned_Int )
			{
				// InternalStructuredTextParser.g:2568:2: ( ruleSigned_Int )
				// InternalStructuredTextParser.g:2569:3: ruleSigned_Int
				{
					before(grammarAccess.getInt_LiteralAccess().getValueSigned_IntParserRuleCall_1_0_0());
					pushFollow(FOLLOW_2);
					ruleSigned_Int();

					state._fsp--;

					after(grammarAccess.getInt_LiteralAccess().getValueSigned_IntParserRuleCall_1_0_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2574:2: ( RULE_BINARY_INT )
			{
				// InternalStructuredTextParser.g:2574:2: ( RULE_BINARY_INT )
				// InternalStructuredTextParser.g:2575:3: RULE_BINARY_INT
				{
					before(grammarAccess.getInt_LiteralAccess().getValueBINARY_INTTerminalRuleCall_1_0_1());
					match(input, RULE_BINARY_INT, FOLLOW_2);
					after(grammarAccess.getInt_LiteralAccess().getValueBINARY_INTTerminalRuleCall_1_0_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2580:2: ( RULE_OCTAL_INT )
			{
				// InternalStructuredTextParser.g:2580:2: ( RULE_OCTAL_INT )
				// InternalStructuredTextParser.g:2581:3: RULE_OCTAL_INT
				{
					before(grammarAccess.getInt_LiteralAccess().getValueOCTAL_INTTerminalRuleCall_1_0_2());
					match(input, RULE_OCTAL_INT, FOLLOW_2);
					after(grammarAccess.getInt_LiteralAccess().getValueOCTAL_INTTerminalRuleCall_1_0_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:2586:2: ( RULE_HEX_INT )
			{
				// InternalStructuredTextParser.g:2586:2: ( RULE_HEX_INT )
				// InternalStructuredTextParser.g:2587:3: RULE_HEX_INT
				{
					before(grammarAccess.getInt_LiteralAccess().getValueHEX_INTTerminalRuleCall_1_0_3());
					match(input, RULE_HEX_INT, FOLLOW_2);
					after(grammarAccess.getInt_LiteralAccess().getValueHEX_INTTerminalRuleCall_1_0_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__ValueAlternatives_1_0"

	// $ANTLR start "rule__Signed_Int__Alternatives_0"
	// InternalStructuredTextParser.g:2596:1: rule__Signed_Int__Alternatives_0 : ( (
	// PlusSign ) | ( HyphenMinus ) );
	public final void rule__Signed_Int__Alternatives_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2600:1: ( ( PlusSign ) | ( HyphenMinus ) )
			int alt17 = 2;
			int LA17_0 = input.LA(1);

			if ((LA17_0 == PlusSign)) {
				alt17 = 1;
			} else if ((LA17_0 == HyphenMinus)) {
				alt17 = 2;
			} else {
				NoViableAltException nvae = new NoViableAltException("", 17, 0, input);

				throw nvae;
			}
			switch (alt17) {
			case 1:
			// InternalStructuredTextParser.g:2601:2: ( PlusSign )
			{
				// InternalStructuredTextParser.g:2601:2: ( PlusSign )
				// InternalStructuredTextParser.g:2602:3: PlusSign
				{
					before(grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0());
					match(input, PlusSign, FOLLOW_2);
					after(grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2607:2: ( HyphenMinus )
			{
				// InternalStructuredTextParser.g:2607:2: ( HyphenMinus )
				// InternalStructuredTextParser.g:2608:3: HyphenMinus
				{
					before(grammarAccess.getSigned_IntAccess().getHyphenMinusKeyword_0_1());
					match(input, HyphenMinus, FOLLOW_2);
					after(grammarAccess.getSigned_IntAccess().getHyphenMinusKeyword_0_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Signed_Int__Alternatives_0"

	// $ANTLR start "rule__Bool_Value__Alternatives"
	// InternalStructuredTextParser.g:2617:1: rule__Bool_Value__Alternatives : ( (
	// FALSE ) | ( TRUE ) );
	public final void rule__Bool_Value__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2621:1: ( ( FALSE ) | ( TRUE ) )
			int alt18 = 2;
			int LA18_0 = input.LA(1);

			if ((LA18_0 == FALSE)) {
				alt18 = 1;
			} else if ((LA18_0 == TRUE)) {
				alt18 = 2;
			} else {
				NoViableAltException nvae = new NoViableAltException("", 18, 0, input);

				throw nvae;
			}
			switch (alt18) {
			case 1:
			// InternalStructuredTextParser.g:2622:2: ( FALSE )
			{
				// InternalStructuredTextParser.g:2622:2: ( FALSE )
				// InternalStructuredTextParser.g:2623:3: FALSE
				{
					before(grammarAccess.getBool_ValueAccess().getFALSEKeyword_0());
					match(input, FALSE, FOLLOW_2);
					after(grammarAccess.getBool_ValueAccess().getFALSEKeyword_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2628:2: ( TRUE )
			{
				// InternalStructuredTextParser.g:2628:2: ( TRUE )
				// InternalStructuredTextParser.g:2629:3: TRUE
				{
					before(grammarAccess.getBool_ValueAccess().getTRUEKeyword_1());
					match(input, TRUE, FOLLOW_2);
					after(grammarAccess.getBool_ValueAccess().getTRUEKeyword_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Value__Alternatives"

	// $ANTLR start "rule__Char_Literal__ValueAlternatives_1_0"
	// InternalStructuredTextParser.g:2638:1:
	// rule__Char_Literal__ValueAlternatives_1_0 : ( ( RULE_S_BYTE_CHAR_STR ) | (
	// RULE_D_BYTE_CHAR_STR ) );
	public final void rule__Char_Literal__ValueAlternatives_1_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2642:1: ( ( RULE_S_BYTE_CHAR_STR ) | (
			// RULE_D_BYTE_CHAR_STR ) )
			int alt19 = 2;
			int LA19_0 = input.LA(1);

			if ((LA19_0 == RULE_S_BYTE_CHAR_STR)) {
				alt19 = 1;
			} else if ((LA19_0 == RULE_D_BYTE_CHAR_STR)) {
				alt19 = 2;
			} else {
				NoViableAltException nvae = new NoViableAltException("", 19, 0, input);

				throw nvae;
			}
			switch (alt19) {
			case 1:
			// InternalStructuredTextParser.g:2643:2: ( RULE_S_BYTE_CHAR_STR )
			{
				// InternalStructuredTextParser.g:2643:2: ( RULE_S_BYTE_CHAR_STR )
				// InternalStructuredTextParser.g:2644:3: RULE_S_BYTE_CHAR_STR
				{
					before(grammarAccess.getChar_LiteralAccess().getValueS_BYTE_CHAR_STRTerminalRuleCall_1_0_0());
					match(input, RULE_S_BYTE_CHAR_STR, FOLLOW_2);
					after(grammarAccess.getChar_LiteralAccess().getValueS_BYTE_CHAR_STRTerminalRuleCall_1_0_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2649:2: ( RULE_D_BYTE_CHAR_STR )
			{
				// InternalStructuredTextParser.g:2649:2: ( RULE_D_BYTE_CHAR_STR )
				// InternalStructuredTextParser.g:2650:3: RULE_D_BYTE_CHAR_STR
				{
					before(grammarAccess.getChar_LiteralAccess().getValueD_BYTE_CHAR_STRTerminalRuleCall_1_0_1());
					match(input, RULE_D_BYTE_CHAR_STR, FOLLOW_2);
					after(grammarAccess.getChar_LiteralAccess().getValueD_BYTE_CHAR_STRTerminalRuleCall_1_0_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__ValueAlternatives_1_0"

	// $ANTLR start "rule__Time_Literal__Alternatives"
	// InternalStructuredTextParser.g:2659:1: rule__Time_Literal__Alternatives : ( (
	// ruleDuration ) | ( ruleTime_Of_Day ) | ( ruleDate ) | ( ruleDate_And_Time )
	// );
	public final void rule__Time_Literal__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2663:1: ( ( ruleDuration ) | ( ruleTime_Of_Day
			// ) | ( ruleDate ) | ( ruleDate_And_Time ) )
			int alt20 = 4;
			switch (input.LA(1)) {
			case LTIME:
			case TIME:
			case LT:
			case T: {
				alt20 = 1;
			}
				break;
			case LTIME_OF_DAY:
			case TIME_OF_DAY:
			case LTOD:
			case TOD: {
				alt20 = 2;
			}
				break;
			case LDATE:
			case DATE:
			case LD:
			case D_1: {
				alt20 = 3;
			}
				break;
			case LDATE_AND_TIME:
			case DATE_AND_TIME:
			case LDT:
			case DT: {
				alt20 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 20, 0, input);

				throw nvae;
			}

			switch (alt20) {
			case 1:
			// InternalStructuredTextParser.g:2664:2: ( ruleDuration )
			{
				// InternalStructuredTextParser.g:2664:2: ( ruleDuration )
				// InternalStructuredTextParser.g:2665:3: ruleDuration
				{
					before(grammarAccess.getTime_LiteralAccess().getDurationParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleDuration();

					state._fsp--;

					after(grammarAccess.getTime_LiteralAccess().getDurationParserRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2670:2: ( ruleTime_Of_Day )
			{
				// InternalStructuredTextParser.g:2670:2: ( ruleTime_Of_Day )
				// InternalStructuredTextParser.g:2671:3: ruleTime_Of_Day
				{
					before(grammarAccess.getTime_LiteralAccess().getTime_Of_DayParserRuleCall_1());
					pushFollow(FOLLOW_2);
					ruleTime_Of_Day();

					state._fsp--;

					after(grammarAccess.getTime_LiteralAccess().getTime_Of_DayParserRuleCall_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2676:2: ( ruleDate )
			{
				// InternalStructuredTextParser.g:2676:2: ( ruleDate )
				// InternalStructuredTextParser.g:2677:3: ruleDate
				{
					before(grammarAccess.getTime_LiteralAccess().getDateParserRuleCall_2());
					pushFollow(FOLLOW_2);
					ruleDate();

					state._fsp--;

					after(grammarAccess.getTime_LiteralAccess().getDateParserRuleCall_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:2682:2: ( ruleDate_And_Time )
			{
				// InternalStructuredTextParser.g:2682:2: ( ruleDate_And_Time )
				// InternalStructuredTextParser.g:2683:3: ruleDate_And_Time
				{
					before(grammarAccess.getTime_LiteralAccess().getDate_And_TimeParserRuleCall_3());
					pushFollow(FOLLOW_2);
					ruleDate_And_Time();

					state._fsp--;

					after(grammarAccess.getTime_LiteralAccess().getDate_And_TimeParserRuleCall_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Time_Literal__Alternatives"

	// $ANTLR start "rule__Duration__Alternatives_2"
	// InternalStructuredTextParser.g:2692:1: rule__Duration__Alternatives_2 : ( (
	// PlusSign ) | ( ( rule__Duration__NegativeAssignment_2_1 ) ) );
	public final void rule__Duration__Alternatives_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2696:1: ( ( PlusSign ) | ( (
			// rule__Duration__NegativeAssignment_2_1 ) ) )
			int alt21 = 2;
			int LA21_0 = input.LA(1);

			if ((LA21_0 == PlusSign)) {
				alt21 = 1;
			} else if ((LA21_0 == HyphenMinus)) {
				alt21 = 2;
			} else {
				NoViableAltException nvae = new NoViableAltException("", 21, 0, input);

				throw nvae;
			}
			switch (alt21) {
			case 1:
			// InternalStructuredTextParser.g:2697:2: ( PlusSign )
			{
				// InternalStructuredTextParser.g:2697:2: ( PlusSign )
				// InternalStructuredTextParser.g:2698:3: PlusSign
				{
					before(grammarAccess.getDurationAccess().getPlusSignKeyword_2_0());
					match(input, PlusSign, FOLLOW_2);
					after(grammarAccess.getDurationAccess().getPlusSignKeyword_2_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2703:2: ( (
			// rule__Duration__NegativeAssignment_2_1 ) )
			{
				// InternalStructuredTextParser.g:2703:2: ( (
				// rule__Duration__NegativeAssignment_2_1 ) )
				// InternalStructuredTextParser.g:2704:3: (
				// rule__Duration__NegativeAssignment_2_1 )
				{
					before(grammarAccess.getDurationAccess().getNegativeAssignment_2_1());
					// InternalStructuredTextParser.g:2705:3: (
					// rule__Duration__NegativeAssignment_2_1 )
					// InternalStructuredTextParser.g:2705:4: rule__Duration__NegativeAssignment_2_1
					{
						pushFollow(FOLLOW_2);
						rule__Duration__NegativeAssignment_2_1();

						state._fsp--;

					}

					after(grammarAccess.getDurationAccess().getNegativeAssignment_2_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Alternatives_2"

	// $ANTLR start "rule__Type_Name__Alternatives"
	// InternalStructuredTextParser.g:2713:1: rule__Type_Name__Alternatives : ( (
	// RULE_ID ) | ( DINT ) | ( INT ) | ( SINT ) | ( LINT ) | ( UINT ) | ( USINT ) |
	// ( UDINT ) | ( ULINT ) | ( REAL ) | ( LREAL ) | ( STRING ) | ( WSTRING ) | (
	// CHAR ) | ( WCHAR ) | ( TIME ) | ( LTIME ) | ( TIME_OF_DAY ) | ( LTIME_OF_DAY
	// ) | ( TOD ) | ( LTOD ) | ( DATE ) | ( LDATE ) | ( DATE_AND_TIME ) | (
	// LDATE_AND_TIME ) | ( BOOL ) | ( LWORD ) | ( DWORD ) | ( WORD ) | ( BYTE ) );
	public final void rule__Type_Name__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2717:1: ( ( RULE_ID ) | ( DINT ) | ( INT ) | (
			// SINT ) | ( LINT ) | ( UINT ) | ( USINT ) | ( UDINT ) | ( ULINT ) | ( REAL ) |
			// ( LREAL ) | ( STRING ) | ( WSTRING ) | ( CHAR ) | ( WCHAR ) | ( TIME ) | (
			// LTIME ) | ( TIME_OF_DAY ) | ( LTIME_OF_DAY ) | ( TOD ) | ( LTOD ) | ( DATE )
			// | ( LDATE ) | ( DATE_AND_TIME ) | ( LDATE_AND_TIME ) | ( BOOL ) | ( LWORD ) |
			// ( DWORD ) | ( WORD ) | ( BYTE ) )
			int alt22 = 30;
			switch (input.LA(1)) {
			case RULE_ID: {
				alt22 = 1;
			}
				break;
			case DINT: {
				alt22 = 2;
			}
				break;
			case INT: {
				alt22 = 3;
			}
				break;
			case SINT: {
				alt22 = 4;
			}
				break;
			case LINT: {
				alt22 = 5;
			}
				break;
			case UINT: {
				alt22 = 6;
			}
				break;
			case USINT: {
				alt22 = 7;
			}
				break;
			case UDINT: {
				alt22 = 8;
			}
				break;
			case ULINT: {
				alt22 = 9;
			}
				break;
			case REAL: {
				alt22 = 10;
			}
				break;
			case LREAL: {
				alt22 = 11;
			}
				break;
			case STRING: {
				alt22 = 12;
			}
				break;
			case WSTRING: {
				alt22 = 13;
			}
				break;
			case CHAR: {
				alt22 = 14;
			}
				break;
			case WCHAR: {
				alt22 = 15;
			}
				break;
			case TIME: {
				alt22 = 16;
			}
				break;
			case LTIME: {
				alt22 = 17;
			}
				break;
			case TIME_OF_DAY: {
				alt22 = 18;
			}
				break;
			case LTIME_OF_DAY: {
				alt22 = 19;
			}
				break;
			case TOD: {
				alt22 = 20;
			}
				break;
			case LTOD: {
				alt22 = 21;
			}
				break;
			case DATE: {
				alt22 = 22;
			}
				break;
			case LDATE: {
				alt22 = 23;
			}
				break;
			case DATE_AND_TIME: {
				alt22 = 24;
			}
				break;
			case LDATE_AND_TIME: {
				alt22 = 25;
			}
				break;
			case BOOL: {
				alt22 = 26;
			}
				break;
			case LWORD: {
				alt22 = 27;
			}
				break;
			case DWORD: {
				alt22 = 28;
			}
				break;
			case WORD: {
				alt22 = 29;
			}
				break;
			case BYTE: {
				alt22 = 30;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 22, 0, input);

				throw nvae;
			}

			switch (alt22) {
			case 1:
			// InternalStructuredTextParser.g:2718:2: ( RULE_ID )
			{
				// InternalStructuredTextParser.g:2718:2: ( RULE_ID )
				// InternalStructuredTextParser.g:2719:3: RULE_ID
				{
					before(grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0());
					match(input, RULE_ID, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2724:2: ( DINT )
			{
				// InternalStructuredTextParser.g:2724:2: ( DINT )
				// InternalStructuredTextParser.g:2725:3: DINT
				{
					before(grammarAccess.getType_NameAccess().getDINTKeyword_1());
					match(input, DINT, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getDINTKeyword_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2730:2: ( INT )
			{
				// InternalStructuredTextParser.g:2730:2: ( INT )
				// InternalStructuredTextParser.g:2731:3: INT
				{
					before(grammarAccess.getType_NameAccess().getINTKeyword_2());
					match(input, INT, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getINTKeyword_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:2736:2: ( SINT )
			{
				// InternalStructuredTextParser.g:2736:2: ( SINT )
				// InternalStructuredTextParser.g:2737:3: SINT
				{
					before(grammarAccess.getType_NameAccess().getSINTKeyword_3());
					match(input, SINT, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getSINTKeyword_3());

				}

			}
				break;
			case 5:
			// InternalStructuredTextParser.g:2742:2: ( LINT )
			{
				// InternalStructuredTextParser.g:2742:2: ( LINT )
				// InternalStructuredTextParser.g:2743:3: LINT
				{
					before(grammarAccess.getType_NameAccess().getLINTKeyword_4());
					match(input, LINT, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getLINTKeyword_4());

				}

			}
				break;
			case 6:
			// InternalStructuredTextParser.g:2748:2: ( UINT )
			{
				// InternalStructuredTextParser.g:2748:2: ( UINT )
				// InternalStructuredTextParser.g:2749:3: UINT
				{
					before(grammarAccess.getType_NameAccess().getUINTKeyword_5());
					match(input, UINT, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getUINTKeyword_5());

				}

			}
				break;
			case 7:
			// InternalStructuredTextParser.g:2754:2: ( USINT )
			{
				// InternalStructuredTextParser.g:2754:2: ( USINT )
				// InternalStructuredTextParser.g:2755:3: USINT
				{
					before(grammarAccess.getType_NameAccess().getUSINTKeyword_6());
					match(input, USINT, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getUSINTKeyword_6());

				}

			}
				break;
			case 8:
			// InternalStructuredTextParser.g:2760:2: ( UDINT )
			{
				// InternalStructuredTextParser.g:2760:2: ( UDINT )
				// InternalStructuredTextParser.g:2761:3: UDINT
				{
					before(grammarAccess.getType_NameAccess().getUDINTKeyword_7());
					match(input, UDINT, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getUDINTKeyword_7());

				}

			}
				break;
			case 9:
			// InternalStructuredTextParser.g:2766:2: ( ULINT )
			{
				// InternalStructuredTextParser.g:2766:2: ( ULINT )
				// InternalStructuredTextParser.g:2767:3: ULINT
				{
					before(grammarAccess.getType_NameAccess().getULINTKeyword_8());
					match(input, ULINT, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getULINTKeyword_8());

				}

			}
				break;
			case 10:
			// InternalStructuredTextParser.g:2772:2: ( REAL )
			{
				// InternalStructuredTextParser.g:2772:2: ( REAL )
				// InternalStructuredTextParser.g:2773:3: REAL
				{
					before(grammarAccess.getType_NameAccess().getREALKeyword_9());
					match(input, REAL, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getREALKeyword_9());

				}

			}
				break;
			case 11:
			// InternalStructuredTextParser.g:2778:2: ( LREAL )
			{
				// InternalStructuredTextParser.g:2778:2: ( LREAL )
				// InternalStructuredTextParser.g:2779:3: LREAL
				{
					before(grammarAccess.getType_NameAccess().getLREALKeyword_10());
					match(input, LREAL, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getLREALKeyword_10());

				}

			}
				break;
			case 12:
			// InternalStructuredTextParser.g:2784:2: ( STRING )
			{
				// InternalStructuredTextParser.g:2784:2: ( STRING )
				// InternalStructuredTextParser.g:2785:3: STRING
				{
					before(grammarAccess.getType_NameAccess().getSTRINGKeyword_11());
					match(input, STRING, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getSTRINGKeyword_11());

				}

			}
				break;
			case 13:
			// InternalStructuredTextParser.g:2790:2: ( WSTRING )
			{
				// InternalStructuredTextParser.g:2790:2: ( WSTRING )
				// InternalStructuredTextParser.g:2791:3: WSTRING
				{
					before(grammarAccess.getType_NameAccess().getWSTRINGKeyword_12());
					match(input, WSTRING, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getWSTRINGKeyword_12());

				}

			}
				break;
			case 14:
			// InternalStructuredTextParser.g:2796:2: ( CHAR )
			{
				// InternalStructuredTextParser.g:2796:2: ( CHAR )
				// InternalStructuredTextParser.g:2797:3: CHAR
				{
					before(grammarAccess.getType_NameAccess().getCHARKeyword_13());
					match(input, CHAR, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getCHARKeyword_13());

				}

			}
				break;
			case 15:
			// InternalStructuredTextParser.g:2802:2: ( WCHAR )
			{
				// InternalStructuredTextParser.g:2802:2: ( WCHAR )
				// InternalStructuredTextParser.g:2803:3: WCHAR
				{
					before(grammarAccess.getType_NameAccess().getWCHARKeyword_14());
					match(input, WCHAR, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getWCHARKeyword_14());

				}

			}
				break;
			case 16:
			// InternalStructuredTextParser.g:2808:2: ( TIME )
			{
				// InternalStructuredTextParser.g:2808:2: ( TIME )
				// InternalStructuredTextParser.g:2809:3: TIME
				{
					before(grammarAccess.getType_NameAccess().getTIMEKeyword_15());
					match(input, TIME, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getTIMEKeyword_15());

				}

			}
				break;
			case 17:
			// InternalStructuredTextParser.g:2814:2: ( LTIME )
			{
				// InternalStructuredTextParser.g:2814:2: ( LTIME )
				// InternalStructuredTextParser.g:2815:3: LTIME
				{
					before(grammarAccess.getType_NameAccess().getLTIMEKeyword_16());
					match(input, LTIME, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getLTIMEKeyword_16());

				}

			}
				break;
			case 18:
			// InternalStructuredTextParser.g:2820:2: ( TIME_OF_DAY )
			{
				// InternalStructuredTextParser.g:2820:2: ( TIME_OF_DAY )
				// InternalStructuredTextParser.g:2821:3: TIME_OF_DAY
				{
					before(grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17());
					match(input, TIME_OF_DAY, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17());

				}

			}
				break;
			case 19:
			// InternalStructuredTextParser.g:2826:2: ( LTIME_OF_DAY )
			{
				// InternalStructuredTextParser.g:2826:2: ( LTIME_OF_DAY )
				// InternalStructuredTextParser.g:2827:3: LTIME_OF_DAY
				{
					before(grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18());
					match(input, LTIME_OF_DAY, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18());

				}

			}
				break;
			case 20:
			// InternalStructuredTextParser.g:2832:2: ( TOD )
			{
				// InternalStructuredTextParser.g:2832:2: ( TOD )
				// InternalStructuredTextParser.g:2833:3: TOD
				{
					before(grammarAccess.getType_NameAccess().getTODKeyword_19());
					match(input, TOD, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getTODKeyword_19());

				}

			}
				break;
			case 21:
			// InternalStructuredTextParser.g:2838:2: ( LTOD )
			{
				// InternalStructuredTextParser.g:2838:2: ( LTOD )
				// InternalStructuredTextParser.g:2839:3: LTOD
				{
					before(grammarAccess.getType_NameAccess().getLTODKeyword_20());
					match(input, LTOD, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getLTODKeyword_20());

				}

			}
				break;
			case 22:
			// InternalStructuredTextParser.g:2844:2: ( DATE )
			{
				// InternalStructuredTextParser.g:2844:2: ( DATE )
				// InternalStructuredTextParser.g:2845:3: DATE
				{
					before(grammarAccess.getType_NameAccess().getDATEKeyword_21());
					match(input, DATE, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getDATEKeyword_21());

				}

			}
				break;
			case 23:
			// InternalStructuredTextParser.g:2850:2: ( LDATE )
			{
				// InternalStructuredTextParser.g:2850:2: ( LDATE )
				// InternalStructuredTextParser.g:2851:3: LDATE
				{
					before(grammarAccess.getType_NameAccess().getLDATEKeyword_22());
					match(input, LDATE, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getLDATEKeyword_22());

				}

			}
				break;
			case 24:
			// InternalStructuredTextParser.g:2856:2: ( DATE_AND_TIME )
			{
				// InternalStructuredTextParser.g:2856:2: ( DATE_AND_TIME )
				// InternalStructuredTextParser.g:2857:3: DATE_AND_TIME
				{
					before(grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23());
					match(input, DATE_AND_TIME, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23());

				}

			}
				break;
			case 25:
			// InternalStructuredTextParser.g:2862:2: ( LDATE_AND_TIME )
			{
				// InternalStructuredTextParser.g:2862:2: ( LDATE_AND_TIME )
				// InternalStructuredTextParser.g:2863:3: LDATE_AND_TIME
				{
					before(grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24());
					match(input, LDATE_AND_TIME, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24());

				}

			}
				break;
			case 26:
			// InternalStructuredTextParser.g:2868:2: ( BOOL )
			{
				// InternalStructuredTextParser.g:2868:2: ( BOOL )
				// InternalStructuredTextParser.g:2869:3: BOOL
				{
					before(grammarAccess.getType_NameAccess().getBOOLKeyword_25());
					match(input, BOOL, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getBOOLKeyword_25());

				}

			}
				break;
			case 27:
			// InternalStructuredTextParser.g:2874:2: ( LWORD )
			{
				// InternalStructuredTextParser.g:2874:2: ( LWORD )
				// InternalStructuredTextParser.g:2875:3: LWORD
				{
					before(grammarAccess.getType_NameAccess().getLWORDKeyword_26());
					match(input, LWORD, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getLWORDKeyword_26());

				}

			}
				break;
			case 28:
			// InternalStructuredTextParser.g:2880:2: ( DWORD )
			{
				// InternalStructuredTextParser.g:2880:2: ( DWORD )
				// InternalStructuredTextParser.g:2881:3: DWORD
				{
					before(grammarAccess.getType_NameAccess().getDWORDKeyword_27());
					match(input, DWORD, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getDWORDKeyword_27());

				}

			}
				break;
			case 29:
			// InternalStructuredTextParser.g:2886:2: ( WORD )
			{
				// InternalStructuredTextParser.g:2886:2: ( WORD )
				// InternalStructuredTextParser.g:2887:3: WORD
				{
					before(grammarAccess.getType_NameAccess().getWORDKeyword_28());
					match(input, WORD, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getWORDKeyword_28());

				}

			}
				break;
			case 30:
			// InternalStructuredTextParser.g:2892:2: ( BYTE )
			{
				// InternalStructuredTextParser.g:2892:2: ( BYTE )
				// InternalStructuredTextParser.g:2893:3: BYTE
				{
					before(grammarAccess.getType_NameAccess().getBYTEKeyword_29());
					match(input, BYTE, FOLLOW_2);
					after(grammarAccess.getType_NameAccess().getBYTEKeyword_29());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Type_Name__Alternatives"

	// $ANTLR start "rule__And_Operator__Alternatives"
	// InternalStructuredTextParser.g:2902:1: rule__And_Operator__Alternatives : ( (
	// ( AND ) ) | ( ( Ampersand ) ) );
	public final void rule__And_Operator__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2906:1: ( ( ( AND ) ) | ( ( Ampersand ) ) )
			int alt23 = 2;
			int LA23_0 = input.LA(1);

			if ((LA23_0 == AND)) {
				alt23 = 1;
			} else if ((LA23_0 == Ampersand)) {
				alt23 = 2;
			} else {
				NoViableAltException nvae = new NoViableAltException("", 23, 0, input);

				throw nvae;
			}
			switch (alt23) {
			case 1:
			// InternalStructuredTextParser.g:2907:2: ( ( AND ) )
			{
				// InternalStructuredTextParser.g:2907:2: ( ( AND ) )
				// InternalStructuredTextParser.g:2908:3: ( AND )
				{
					before(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:2909:3: ( AND )
					// InternalStructuredTextParser.g:2909:4: AND
					{
						match(input, AND, FOLLOW_2);

					}

					after(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2913:2: ( ( Ampersand ) )
			{
				// InternalStructuredTextParser.g:2913:2: ( ( Ampersand ) )
				// InternalStructuredTextParser.g:2914:3: ( Ampersand )
				{
					before(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:2915:3: ( Ampersand )
					// InternalStructuredTextParser.g:2915:4: Ampersand
					{
						match(input, Ampersand, FOLLOW_2);

					}

					after(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Operator__Alternatives"

	// $ANTLR start "rule__Compare_Operator__Alternatives"
	// InternalStructuredTextParser.g:2923:1: rule__Compare_Operator__Alternatives :
	// ( ( ( EqualsSign ) ) | ( ( LessThanSignGreaterThanSign ) ) );
	public final void rule__Compare_Operator__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2927:1: ( ( ( EqualsSign ) ) | ( (
			// LessThanSignGreaterThanSign ) ) )
			int alt24 = 2;
			int LA24_0 = input.LA(1);

			if ((LA24_0 == EqualsSign)) {
				alt24 = 1;
			} else if ((LA24_0 == LessThanSignGreaterThanSign)) {
				alt24 = 2;
			} else {
				NoViableAltException nvae = new NoViableAltException("", 24, 0, input);

				throw nvae;
			}
			switch (alt24) {
			case 1:
			// InternalStructuredTextParser.g:2928:2: ( ( EqualsSign ) )
			{
				// InternalStructuredTextParser.g:2928:2: ( ( EqualsSign ) )
				// InternalStructuredTextParser.g:2929:3: ( EqualsSign )
				{
					before(grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:2930:3: ( EqualsSign )
					// InternalStructuredTextParser.g:2930:4: EqualsSign
					{
						match(input, EqualsSign, FOLLOW_2);

					}

					after(grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2934:2: ( ( LessThanSignGreaterThanSign ) )
			{
				// InternalStructuredTextParser.g:2934:2: ( ( LessThanSignGreaterThanSign ) )
				// InternalStructuredTextParser.g:2935:3: ( LessThanSignGreaterThanSign )
				{
					before(grammarAccess.getCompare_OperatorAccess().getNEEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:2936:3: ( LessThanSignGreaterThanSign )
					// InternalStructuredTextParser.g:2936:4: LessThanSignGreaterThanSign
					{
						match(input, LessThanSignGreaterThanSign, FOLLOW_2);

					}

					after(grammarAccess.getCompare_OperatorAccess().getNEEnumLiteralDeclaration_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Operator__Alternatives"

	// $ANTLR start "rule__Equ_Operator__Alternatives"
	// InternalStructuredTextParser.g:2944:1: rule__Equ_Operator__Alternatives : ( (
	// ( LessThanSign ) ) | ( ( LessThanSignEqualsSign ) ) | ( ( GreaterThanSign ) )
	// | ( ( GreaterThanSignEqualsSign ) ) );
	public final void rule__Equ_Operator__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2948:1: ( ( ( LessThanSign ) ) | ( (
			// LessThanSignEqualsSign ) ) | ( ( GreaterThanSign ) ) | ( (
			// GreaterThanSignEqualsSign ) ) )
			int alt25 = 4;
			switch (input.LA(1)) {
			case LessThanSign: {
				alt25 = 1;
			}
				break;
			case LessThanSignEqualsSign: {
				alt25 = 2;
			}
				break;
			case GreaterThanSign: {
				alt25 = 3;
			}
				break;
			case GreaterThanSignEqualsSign: {
				alt25 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 25, 0, input);

				throw nvae;
			}

			switch (alt25) {
			case 1:
			// InternalStructuredTextParser.g:2949:2: ( ( LessThanSign ) )
			{
				// InternalStructuredTextParser.g:2949:2: ( ( LessThanSign ) )
				// InternalStructuredTextParser.g:2950:3: ( LessThanSign )
				{
					before(grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:2951:3: ( LessThanSign )
					// InternalStructuredTextParser.g:2951:4: LessThanSign
					{
						match(input, LessThanSign, FOLLOW_2);

					}

					after(grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2955:2: ( ( LessThanSignEqualsSign ) )
			{
				// InternalStructuredTextParser.g:2955:2: ( ( LessThanSignEqualsSign ) )
				// InternalStructuredTextParser.g:2956:3: ( LessThanSignEqualsSign )
				{
					before(grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:2957:3: ( LessThanSignEqualsSign )
					// InternalStructuredTextParser.g:2957:4: LessThanSignEqualsSign
					{
						match(input, LessThanSignEqualsSign, FOLLOW_2);

					}

					after(grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:2961:2: ( ( GreaterThanSign ) )
			{
				// InternalStructuredTextParser.g:2961:2: ( ( GreaterThanSign ) )
				// InternalStructuredTextParser.g:2962:3: ( GreaterThanSign )
				{
					before(grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2());
					// InternalStructuredTextParser.g:2963:3: ( GreaterThanSign )
					// InternalStructuredTextParser.g:2963:4: GreaterThanSign
					{
						match(input, GreaterThanSign, FOLLOW_2);

					}

					after(grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:2967:2: ( ( GreaterThanSignEqualsSign ) )
			{
				// InternalStructuredTextParser.g:2967:2: ( ( GreaterThanSignEqualsSign ) )
				// InternalStructuredTextParser.g:2968:3: ( GreaterThanSignEqualsSign )
				{
					before(grammarAccess.getEqu_OperatorAccess().getGEEnumLiteralDeclaration_3());
					// InternalStructuredTextParser.g:2969:3: ( GreaterThanSignEqualsSign )
					// InternalStructuredTextParser.g:2969:4: GreaterThanSignEqualsSign
					{
						match(input, GreaterThanSignEqualsSign, FOLLOW_2);

					}

					after(grammarAccess.getEqu_OperatorAccess().getGEEnumLiteralDeclaration_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Operator__Alternatives"

	// $ANTLR start "rule__Add_Operator__Alternatives"
	// InternalStructuredTextParser.g:2977:1: rule__Add_Operator__Alternatives : ( (
	// ( PlusSign ) ) | ( ( HyphenMinus ) ) );
	public final void rule__Add_Operator__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:2981:1: ( ( ( PlusSign ) ) | ( ( HyphenMinus )
			// ) )
			int alt26 = 2;
			int LA26_0 = input.LA(1);

			if ((LA26_0 == PlusSign)) {
				alt26 = 1;
			} else if ((LA26_0 == HyphenMinus)) {
				alt26 = 2;
			} else {
				NoViableAltException nvae = new NoViableAltException("", 26, 0, input);

				throw nvae;
			}
			switch (alt26) {
			case 1:
			// InternalStructuredTextParser.g:2982:2: ( ( PlusSign ) )
			{
				// InternalStructuredTextParser.g:2982:2: ( ( PlusSign ) )
				// InternalStructuredTextParser.g:2983:3: ( PlusSign )
				{
					before(grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:2984:3: ( PlusSign )
					// InternalStructuredTextParser.g:2984:4: PlusSign
					{
						match(input, PlusSign, FOLLOW_2);

					}

					after(grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:2988:2: ( ( HyphenMinus ) )
			{
				// InternalStructuredTextParser.g:2988:2: ( ( HyphenMinus ) )
				// InternalStructuredTextParser.g:2989:3: ( HyphenMinus )
				{
					before(grammarAccess.getAdd_OperatorAccess().getSUBEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:2990:3: ( HyphenMinus )
					// InternalStructuredTextParser.g:2990:4: HyphenMinus
					{
						match(input, HyphenMinus, FOLLOW_2);

					}

					after(grammarAccess.getAdd_OperatorAccess().getSUBEnumLiteralDeclaration_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Operator__Alternatives"

	// $ANTLR start "rule__Term_Operator__Alternatives"
	// InternalStructuredTextParser.g:2998:1: rule__Term_Operator__Alternatives : (
	// ( ( Asterisk ) ) | ( ( Solidus ) ) | ( ( MOD ) ) );
	public final void rule__Term_Operator__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3002:1: ( ( ( Asterisk ) ) | ( ( Solidus ) ) |
			// ( ( MOD ) ) )
			int alt27 = 3;
			switch (input.LA(1)) {
			case Asterisk: {
				alt27 = 1;
			}
				break;
			case Solidus: {
				alt27 = 2;
			}
				break;
			case MOD: {
				alt27 = 3;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 27, 0, input);

				throw nvae;
			}

			switch (alt27) {
			case 1:
			// InternalStructuredTextParser.g:3003:2: ( ( Asterisk ) )
			{
				// InternalStructuredTextParser.g:3003:2: ( ( Asterisk ) )
				// InternalStructuredTextParser.g:3004:3: ( Asterisk )
				{
					before(grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:3005:3: ( Asterisk )
					// InternalStructuredTextParser.g:3005:4: Asterisk
					{
						match(input, Asterisk, FOLLOW_2);

					}

					after(grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:3009:2: ( ( Solidus ) )
			{
				// InternalStructuredTextParser.g:3009:2: ( ( Solidus ) )
				// InternalStructuredTextParser.g:3010:3: ( Solidus )
				{
					before(grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:3011:3: ( Solidus )
					// InternalStructuredTextParser.g:3011:4: Solidus
					{
						match(input, Solidus, FOLLOW_2);

					}

					after(grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:3015:2: ( ( MOD ) )
			{
				// InternalStructuredTextParser.g:3015:2: ( ( MOD ) )
				// InternalStructuredTextParser.g:3016:3: ( MOD )
				{
					before(grammarAccess.getTerm_OperatorAccess().getMODEnumLiteralDeclaration_2());
					// InternalStructuredTextParser.g:3017:3: ( MOD )
					// InternalStructuredTextParser.g:3017:4: MOD
					{
						match(input, MOD, FOLLOW_2);

					}

					after(grammarAccess.getTerm_OperatorAccess().getMODEnumLiteralDeclaration_2());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term_Operator__Alternatives"

	// $ANTLR start "rule__Unary_Operator__Alternatives"
	// InternalStructuredTextParser.g:3025:1: rule__Unary_Operator__Alternatives : (
	// ( ( HyphenMinus ) ) | ( ( PlusSign ) ) | ( ( NOT ) ) );
	public final void rule__Unary_Operator__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3029:1: ( ( ( HyphenMinus ) ) | ( ( PlusSign )
			// ) | ( ( NOT ) ) )
			int alt28 = 3;
			switch (input.LA(1)) {
			case HyphenMinus: {
				alt28 = 1;
			}
				break;
			case PlusSign: {
				alt28 = 2;
			}
				break;
			case NOT: {
				alt28 = 3;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 28, 0, input);

				throw nvae;
			}

			switch (alt28) {
			case 1:
			// InternalStructuredTextParser.g:3030:2: ( ( HyphenMinus ) )
			{
				// InternalStructuredTextParser.g:3030:2: ( ( HyphenMinus ) )
				// InternalStructuredTextParser.g:3031:3: ( HyphenMinus )
				{
					before(grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:3032:3: ( HyphenMinus )
					// InternalStructuredTextParser.g:3032:4: HyphenMinus
					{
						match(input, HyphenMinus, FOLLOW_2);

					}

					after(grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:3036:2: ( ( PlusSign ) )
			{
				// InternalStructuredTextParser.g:3036:2: ( ( PlusSign ) )
				// InternalStructuredTextParser.g:3037:3: ( PlusSign )
				{
					before(grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:3038:3: ( PlusSign )
					// InternalStructuredTextParser.g:3038:4: PlusSign
					{
						match(input, PlusSign, FOLLOW_2);

					}

					after(grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:3042:2: ( ( NOT ) )
			{
				// InternalStructuredTextParser.g:3042:2: ( ( NOT ) )
				// InternalStructuredTextParser.g:3043:3: ( NOT )
				{
					before(grammarAccess.getUnary_OperatorAccess().getNOTEnumLiteralDeclaration_2());
					// InternalStructuredTextParser.g:3044:3: ( NOT )
					// InternalStructuredTextParser.g:3044:4: NOT
					{
						match(input, NOT, FOLLOW_2);

					}

					after(grammarAccess.getUnary_OperatorAccess().getNOTEnumLiteralDeclaration_2());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Unary_Operator__Alternatives"

	// $ANTLR start "rule__Duration_Unit__Alternatives"
	// InternalStructuredTextParser.g:3052:1: rule__Duration_Unit__Alternatives : (
	// ( ( D_1 ) ) | ( ( H ) ) | ( ( M ) ) | ( ( S ) ) | ( ( Ms ) ) | ( ( Us ) ) | (
	// ( Ns ) ) );
	public final void rule__Duration_Unit__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3056:1: ( ( ( D_1 ) ) | ( ( H ) ) | ( ( M ) )
			// | ( ( S ) ) | ( ( Ms ) ) | ( ( Us ) ) | ( ( Ns ) ) )
			int alt29 = 7;
			switch (input.LA(1)) {
			case D_1: {
				alt29 = 1;
			}
				break;
			case H: {
				alt29 = 2;
			}
				break;
			case M: {
				alt29 = 3;
			}
				break;
			case S: {
				alt29 = 4;
			}
				break;
			case Ms: {
				alt29 = 5;
			}
				break;
			case Us: {
				alt29 = 6;
			}
				break;
			case Ns: {
				alt29 = 7;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 29, 0, input);

				throw nvae;
			}

			switch (alt29) {
			case 1:
			// InternalStructuredTextParser.g:3057:2: ( ( D_1 ) )
			{
				// InternalStructuredTextParser.g:3057:2: ( ( D_1 ) )
				// InternalStructuredTextParser.g:3058:3: ( D_1 )
				{
					before(grammarAccess.getDuration_UnitAccess().getDAYSEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:3059:3: ( D_1 )
					// InternalStructuredTextParser.g:3059:4: D_1
					{
						match(input, D_1, FOLLOW_2);

					}

					after(grammarAccess.getDuration_UnitAccess().getDAYSEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:3063:2: ( ( H ) )
			{
				// InternalStructuredTextParser.g:3063:2: ( ( H ) )
				// InternalStructuredTextParser.g:3064:3: ( H )
				{
					before(grammarAccess.getDuration_UnitAccess().getHOURSEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:3065:3: ( H )
					// InternalStructuredTextParser.g:3065:4: H
					{
						match(input, H, FOLLOW_2);

					}

					after(grammarAccess.getDuration_UnitAccess().getHOURSEnumLiteralDeclaration_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:3069:2: ( ( M ) )
			{
				// InternalStructuredTextParser.g:3069:2: ( ( M ) )
				// InternalStructuredTextParser.g:3070:3: ( M )
				{
					before(grammarAccess.getDuration_UnitAccess().getMINUTESEnumLiteralDeclaration_2());
					// InternalStructuredTextParser.g:3071:3: ( M )
					// InternalStructuredTextParser.g:3071:4: M
					{
						match(input, M, FOLLOW_2);

					}

					after(grammarAccess.getDuration_UnitAccess().getMINUTESEnumLiteralDeclaration_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:3075:2: ( ( S ) )
			{
				// InternalStructuredTextParser.g:3075:2: ( ( S ) )
				// InternalStructuredTextParser.g:3076:3: ( S )
				{
					before(grammarAccess.getDuration_UnitAccess().getSECONDSEnumLiteralDeclaration_3());
					// InternalStructuredTextParser.g:3077:3: ( S )
					// InternalStructuredTextParser.g:3077:4: S
					{
						match(input, S, FOLLOW_2);

					}

					after(grammarAccess.getDuration_UnitAccess().getSECONDSEnumLiteralDeclaration_3());

				}

			}
				break;
			case 5:
			// InternalStructuredTextParser.g:3081:2: ( ( Ms ) )
			{
				// InternalStructuredTextParser.g:3081:2: ( ( Ms ) )
				// InternalStructuredTextParser.g:3082:3: ( Ms )
				{
					before(grammarAccess.getDuration_UnitAccess().getMILLISEnumLiteralDeclaration_4());
					// InternalStructuredTextParser.g:3083:3: ( Ms )
					// InternalStructuredTextParser.g:3083:4: Ms
					{
						match(input, Ms, FOLLOW_2);

					}

					after(grammarAccess.getDuration_UnitAccess().getMILLISEnumLiteralDeclaration_4());

				}

			}
				break;
			case 6:
			// InternalStructuredTextParser.g:3087:2: ( ( Us ) )
			{
				// InternalStructuredTextParser.g:3087:2: ( ( Us ) )
				// InternalStructuredTextParser.g:3088:3: ( Us )
				{
					before(grammarAccess.getDuration_UnitAccess().getMICROSEnumLiteralDeclaration_5());
					// InternalStructuredTextParser.g:3089:3: ( Us )
					// InternalStructuredTextParser.g:3089:4: Us
					{
						match(input, Us, FOLLOW_2);

					}

					after(grammarAccess.getDuration_UnitAccess().getMICROSEnumLiteralDeclaration_5());

				}

			}
				break;
			case 7:
			// InternalStructuredTextParser.g:3093:2: ( ( Ns ) )
			{
				// InternalStructuredTextParser.g:3093:2: ( ( Ns ) )
				// InternalStructuredTextParser.g:3094:3: ( Ns )
				{
					before(grammarAccess.getDuration_UnitAccess().getNANOSEnumLiteralDeclaration_6());
					// InternalStructuredTextParser.g:3095:3: ( Ns )
					// InternalStructuredTextParser.g:3095:4: Ns
					{
						match(input, Ns, FOLLOW_2);

					}

					after(grammarAccess.getDuration_UnitAccess().getNANOSEnumLiteralDeclaration_6());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration_Unit__Alternatives"

	// $ANTLR start "rule__Int_Type_Name__Alternatives"
	// InternalStructuredTextParser.g:3103:1: rule__Int_Type_Name__Alternatives : (
	// ( ( DINT ) ) | ( ( INT ) ) | ( ( SINT ) ) | ( ( LINT ) ) | ( ( UINT ) ) | ( (
	// USINT ) ) | ( ( UDINT ) ) | ( ( ULINT ) ) );
	public final void rule__Int_Type_Name__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3107:1: ( ( ( DINT ) ) | ( ( INT ) ) | ( (
			// SINT ) ) | ( ( LINT ) ) | ( ( UINT ) ) | ( ( USINT ) ) | ( ( UDINT ) ) | ( (
			// ULINT ) ) )
			int alt30 = 8;
			switch (input.LA(1)) {
			case DINT: {
				alt30 = 1;
			}
				break;
			case INT: {
				alt30 = 2;
			}
				break;
			case SINT: {
				alt30 = 3;
			}
				break;
			case LINT: {
				alt30 = 4;
			}
				break;
			case UINT: {
				alt30 = 5;
			}
				break;
			case USINT: {
				alt30 = 6;
			}
				break;
			case UDINT: {
				alt30 = 7;
			}
				break;
			case ULINT: {
				alt30 = 8;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 30, 0, input);

				throw nvae;
			}

			switch (alt30) {
			case 1:
			// InternalStructuredTextParser.g:3108:2: ( ( DINT ) )
			{
				// InternalStructuredTextParser.g:3108:2: ( ( DINT ) )
				// InternalStructuredTextParser.g:3109:3: ( DINT )
				{
					before(grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:3110:3: ( DINT )
					// InternalStructuredTextParser.g:3110:4: DINT
					{
						match(input, DINT, FOLLOW_2);

					}

					after(grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:3114:2: ( ( INT ) )
			{
				// InternalStructuredTextParser.g:3114:2: ( ( INT ) )
				// InternalStructuredTextParser.g:3115:3: ( INT )
				{
					before(grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:3116:3: ( INT )
					// InternalStructuredTextParser.g:3116:4: INT
					{
						match(input, INT, FOLLOW_2);

					}

					after(grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:3120:2: ( ( SINT ) )
			{
				// InternalStructuredTextParser.g:3120:2: ( ( SINT ) )
				// InternalStructuredTextParser.g:3121:3: ( SINT )
				{
					before(grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2());
					// InternalStructuredTextParser.g:3122:3: ( SINT )
					// InternalStructuredTextParser.g:3122:4: SINT
					{
						match(input, SINT, FOLLOW_2);

					}

					after(grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:3126:2: ( ( LINT ) )
			{
				// InternalStructuredTextParser.g:3126:2: ( ( LINT ) )
				// InternalStructuredTextParser.g:3127:3: ( LINT )
				{
					before(grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3());
					// InternalStructuredTextParser.g:3128:3: ( LINT )
					// InternalStructuredTextParser.g:3128:4: LINT
					{
						match(input, LINT, FOLLOW_2);

					}

					after(grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3());

				}

			}
				break;
			case 5:
			// InternalStructuredTextParser.g:3132:2: ( ( UINT ) )
			{
				// InternalStructuredTextParser.g:3132:2: ( ( UINT ) )
				// InternalStructuredTextParser.g:3133:3: ( UINT )
				{
					before(grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4());
					// InternalStructuredTextParser.g:3134:3: ( UINT )
					// InternalStructuredTextParser.g:3134:4: UINT
					{
						match(input, UINT, FOLLOW_2);

					}

					after(grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4());

				}

			}
				break;
			case 6:
			// InternalStructuredTextParser.g:3138:2: ( ( USINT ) )
			{
				// InternalStructuredTextParser.g:3138:2: ( ( USINT ) )
				// InternalStructuredTextParser.g:3139:3: ( USINT )
				{
					before(grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5());
					// InternalStructuredTextParser.g:3140:3: ( USINT )
					// InternalStructuredTextParser.g:3140:4: USINT
					{
						match(input, USINT, FOLLOW_2);

					}

					after(grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5());

				}

			}
				break;
			case 7:
			// InternalStructuredTextParser.g:3144:2: ( ( UDINT ) )
			{
				// InternalStructuredTextParser.g:3144:2: ( ( UDINT ) )
				// InternalStructuredTextParser.g:3145:3: ( UDINT )
				{
					before(grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6());
					// InternalStructuredTextParser.g:3146:3: ( UDINT )
					// InternalStructuredTextParser.g:3146:4: UDINT
					{
						match(input, UDINT, FOLLOW_2);

					}

					after(grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6());

				}

			}
				break;
			case 8:
			// InternalStructuredTextParser.g:3150:2: ( ( ULINT ) )
			{
				// InternalStructuredTextParser.g:3150:2: ( ( ULINT ) )
				// InternalStructuredTextParser.g:3151:3: ( ULINT )
				{
					before(grammarAccess.getInt_Type_NameAccess().getULINTEnumLiteralDeclaration_7());
					// InternalStructuredTextParser.g:3152:3: ( ULINT )
					// InternalStructuredTextParser.g:3152:4: ULINT
					{
						match(input, ULINT, FOLLOW_2);

					}

					after(grammarAccess.getInt_Type_NameAccess().getULINTEnumLiteralDeclaration_7());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Type_Name__Alternatives"

	// $ANTLR start "rule__Real_Type_Name__Alternatives"
	// InternalStructuredTextParser.g:3160:1: rule__Real_Type_Name__Alternatives : (
	// ( ( REAL ) ) | ( ( LREAL ) ) );
	public final void rule__Real_Type_Name__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3164:1: ( ( ( REAL ) ) | ( ( LREAL ) ) )
			int alt31 = 2;
			int LA31_0 = input.LA(1);

			if ((LA31_0 == REAL)) {
				alt31 = 1;
			} else if ((LA31_0 == LREAL)) {
				alt31 = 2;
			} else {
				NoViableAltException nvae = new NoViableAltException("", 31, 0, input);

				throw nvae;
			}
			switch (alt31) {
			case 1:
			// InternalStructuredTextParser.g:3165:2: ( ( REAL ) )
			{
				// InternalStructuredTextParser.g:3165:2: ( ( REAL ) )
				// InternalStructuredTextParser.g:3166:3: ( REAL )
				{
					before(grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:3167:3: ( REAL )
					// InternalStructuredTextParser.g:3167:4: REAL
					{
						match(input, REAL, FOLLOW_2);

					}

					after(grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:3171:2: ( ( LREAL ) )
			{
				// InternalStructuredTextParser.g:3171:2: ( ( LREAL ) )
				// InternalStructuredTextParser.g:3172:3: ( LREAL )
				{
					before(grammarAccess.getReal_Type_NameAccess().getLREALEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:3173:3: ( LREAL )
					// InternalStructuredTextParser.g:3173:4: LREAL
					{
						match(input, LREAL, FOLLOW_2);

					}

					after(grammarAccess.getReal_Type_NameAccess().getLREALEnumLiteralDeclaration_1());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Type_Name__Alternatives"

	// $ANTLR start "rule__String_Type_Name__Alternatives"
	// InternalStructuredTextParser.g:3181:1: rule__String_Type_Name__Alternatives :
	// ( ( ( STRING ) ) | ( ( WSTRING ) ) | ( ( CHAR ) ) | ( ( WCHAR ) ) );
	public final void rule__String_Type_Name__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3185:1: ( ( ( STRING ) ) | ( ( WSTRING ) ) | (
			// ( CHAR ) ) | ( ( WCHAR ) ) )
			int alt32 = 4;
			switch (input.LA(1)) {
			case STRING: {
				alt32 = 1;
			}
				break;
			case WSTRING: {
				alt32 = 2;
			}
				break;
			case CHAR: {
				alt32 = 3;
			}
				break;
			case WCHAR: {
				alt32 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 32, 0, input);

				throw nvae;
			}

			switch (alt32) {
			case 1:
			// InternalStructuredTextParser.g:3186:2: ( ( STRING ) )
			{
				// InternalStructuredTextParser.g:3186:2: ( ( STRING ) )
				// InternalStructuredTextParser.g:3187:3: ( STRING )
				{
					before(grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:3188:3: ( STRING )
					// InternalStructuredTextParser.g:3188:4: STRING
					{
						match(input, STRING, FOLLOW_2);

					}

					after(grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:3192:2: ( ( WSTRING ) )
			{
				// InternalStructuredTextParser.g:3192:2: ( ( WSTRING ) )
				// InternalStructuredTextParser.g:3193:3: ( WSTRING )
				{
					before(grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:3194:3: ( WSTRING )
					// InternalStructuredTextParser.g:3194:4: WSTRING
					{
						match(input, WSTRING, FOLLOW_2);

					}

					after(grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:3198:2: ( ( CHAR ) )
			{
				// InternalStructuredTextParser.g:3198:2: ( ( CHAR ) )
				// InternalStructuredTextParser.g:3199:3: ( CHAR )
				{
					before(grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2());
					// InternalStructuredTextParser.g:3200:3: ( CHAR )
					// InternalStructuredTextParser.g:3200:4: CHAR
					{
						match(input, CHAR, FOLLOW_2);

					}

					after(grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:3204:2: ( ( WCHAR ) )
			{
				// InternalStructuredTextParser.g:3204:2: ( ( WCHAR ) )
				// InternalStructuredTextParser.g:3205:3: ( WCHAR )
				{
					before(grammarAccess.getString_Type_NameAccess().getWCHAREnumLiteralDeclaration_3());
					// InternalStructuredTextParser.g:3206:3: ( WCHAR )
					// InternalStructuredTextParser.g:3206:4: WCHAR
					{
						match(input, WCHAR, FOLLOW_2);

					}

					after(grammarAccess.getString_Type_NameAccess().getWCHAREnumLiteralDeclaration_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__String_Type_Name__Alternatives"

	// $ANTLR start "rule__Time_Type_Name__Alternatives"
	// InternalStructuredTextParser.g:3214:1: rule__Time_Type_Name__Alternatives : (
	// ( ( TIME ) ) | ( ( LTIME ) ) | ( ( T ) ) | ( ( LT ) ) );
	public final void rule__Time_Type_Name__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3218:1: ( ( ( TIME ) ) | ( ( LTIME ) ) | ( ( T
			// ) ) | ( ( LT ) ) )
			int alt33 = 4;
			switch (input.LA(1)) {
			case TIME: {
				alt33 = 1;
			}
				break;
			case LTIME: {
				alt33 = 2;
			}
				break;
			case T: {
				alt33 = 3;
			}
				break;
			case LT: {
				alt33 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 33, 0, input);

				throw nvae;
			}

			switch (alt33) {
			case 1:
			// InternalStructuredTextParser.g:3219:2: ( ( TIME ) )
			{
				// InternalStructuredTextParser.g:3219:2: ( ( TIME ) )
				// InternalStructuredTextParser.g:3220:3: ( TIME )
				{
					before(grammarAccess.getTime_Type_NameAccess().getTIMEEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:3221:3: ( TIME )
					// InternalStructuredTextParser.g:3221:4: TIME
					{
						match(input, TIME, FOLLOW_2);

					}

					after(grammarAccess.getTime_Type_NameAccess().getTIMEEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:3225:2: ( ( LTIME ) )
			{
				// InternalStructuredTextParser.g:3225:2: ( ( LTIME ) )
				// InternalStructuredTextParser.g:3226:3: ( LTIME )
				{
					before(grammarAccess.getTime_Type_NameAccess().getLTIMEEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:3227:3: ( LTIME )
					// InternalStructuredTextParser.g:3227:4: LTIME
					{
						match(input, LTIME, FOLLOW_2);

					}

					after(grammarAccess.getTime_Type_NameAccess().getLTIMEEnumLiteralDeclaration_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:3231:2: ( ( T ) )
			{
				// InternalStructuredTextParser.g:3231:2: ( ( T ) )
				// InternalStructuredTextParser.g:3232:3: ( T )
				{
					before(grammarAccess.getTime_Type_NameAccess().getTEnumLiteralDeclaration_2());
					// InternalStructuredTextParser.g:3233:3: ( T )
					// InternalStructuredTextParser.g:3233:4: T
					{
						match(input, T, FOLLOW_2);

					}

					after(grammarAccess.getTime_Type_NameAccess().getTEnumLiteralDeclaration_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:3237:2: ( ( LT ) )
			{
				// InternalStructuredTextParser.g:3237:2: ( ( LT ) )
				// InternalStructuredTextParser.g:3238:3: ( LT )
				{
					before(grammarAccess.getTime_Type_NameAccess().getLTEnumLiteralDeclaration_3());
					// InternalStructuredTextParser.g:3239:3: ( LT )
					// InternalStructuredTextParser.g:3239:4: LT
					{
						match(input, LT, FOLLOW_2);

					}

					after(grammarAccess.getTime_Type_NameAccess().getLTEnumLiteralDeclaration_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Time_Type_Name__Alternatives"

	// $ANTLR start "rule__Tod_Type_Name__Alternatives"
	// InternalStructuredTextParser.g:3247:1: rule__Tod_Type_Name__Alternatives : (
	// ( ( TIME_OF_DAY ) ) | ( ( LTIME_OF_DAY ) ) | ( ( TOD ) ) | ( ( LTOD ) ) );
	public final void rule__Tod_Type_Name__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3251:1: ( ( ( TIME_OF_DAY ) ) | ( (
			// LTIME_OF_DAY ) ) | ( ( TOD ) ) | ( ( LTOD ) ) )
			int alt34 = 4;
			switch (input.LA(1)) {
			case TIME_OF_DAY: {
				alt34 = 1;
			}
				break;
			case LTIME_OF_DAY: {
				alt34 = 2;
			}
				break;
			case TOD: {
				alt34 = 3;
			}
				break;
			case LTOD: {
				alt34 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 34, 0, input);

				throw nvae;
			}

			switch (alt34) {
			case 1:
			// InternalStructuredTextParser.g:3252:2: ( ( TIME_OF_DAY ) )
			{
				// InternalStructuredTextParser.g:3252:2: ( ( TIME_OF_DAY ) )
				// InternalStructuredTextParser.g:3253:3: ( TIME_OF_DAY )
				{
					before(grammarAccess.getTod_Type_NameAccess().getTIME_OF_DAYEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:3254:3: ( TIME_OF_DAY )
					// InternalStructuredTextParser.g:3254:4: TIME_OF_DAY
					{
						match(input, TIME_OF_DAY, FOLLOW_2);

					}

					after(grammarAccess.getTod_Type_NameAccess().getTIME_OF_DAYEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:3258:2: ( ( LTIME_OF_DAY ) )
			{
				// InternalStructuredTextParser.g:3258:2: ( ( LTIME_OF_DAY ) )
				// InternalStructuredTextParser.g:3259:3: ( LTIME_OF_DAY )
				{
					before(grammarAccess.getTod_Type_NameAccess().getLTIME_OF_DAYEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:3260:3: ( LTIME_OF_DAY )
					// InternalStructuredTextParser.g:3260:4: LTIME_OF_DAY
					{
						match(input, LTIME_OF_DAY, FOLLOW_2);

					}

					after(grammarAccess.getTod_Type_NameAccess().getLTIME_OF_DAYEnumLiteralDeclaration_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:3264:2: ( ( TOD ) )
			{
				// InternalStructuredTextParser.g:3264:2: ( ( TOD ) )
				// InternalStructuredTextParser.g:3265:3: ( TOD )
				{
					before(grammarAccess.getTod_Type_NameAccess().getTODEnumLiteralDeclaration_2());
					// InternalStructuredTextParser.g:3266:3: ( TOD )
					// InternalStructuredTextParser.g:3266:4: TOD
					{
						match(input, TOD, FOLLOW_2);

					}

					after(grammarAccess.getTod_Type_NameAccess().getTODEnumLiteralDeclaration_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:3270:2: ( ( LTOD ) )
			{
				// InternalStructuredTextParser.g:3270:2: ( ( LTOD ) )
				// InternalStructuredTextParser.g:3271:3: ( LTOD )
				{
					before(grammarAccess.getTod_Type_NameAccess().getLTODEnumLiteralDeclaration_3());
					// InternalStructuredTextParser.g:3272:3: ( LTOD )
					// InternalStructuredTextParser.g:3272:4: LTOD
					{
						match(input, LTOD, FOLLOW_2);

					}

					after(grammarAccess.getTod_Type_NameAccess().getLTODEnumLiteralDeclaration_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Tod_Type_Name__Alternatives"

	// $ANTLR start "rule__Date_Type_Name__Alternatives"
	// InternalStructuredTextParser.g:3280:1: rule__Date_Type_Name__Alternatives : (
	// ( ( DATE ) ) | ( ( LDATE ) ) | ( ( D_1 ) ) | ( ( LD ) ) );
	public final void rule__Date_Type_Name__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3284:1: ( ( ( DATE ) ) | ( ( LDATE ) ) | ( (
			// D_1 ) ) | ( ( LD ) ) )
			int alt35 = 4;
			switch (input.LA(1)) {
			case DATE: {
				alt35 = 1;
			}
				break;
			case LDATE: {
				alt35 = 2;
			}
				break;
			case D_1: {
				alt35 = 3;
			}
				break;
			case LD: {
				alt35 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 35, 0, input);

				throw nvae;
			}

			switch (alt35) {
			case 1:
			// InternalStructuredTextParser.g:3285:2: ( ( DATE ) )
			{
				// InternalStructuredTextParser.g:3285:2: ( ( DATE ) )
				// InternalStructuredTextParser.g:3286:3: ( DATE )
				{
					before(grammarAccess.getDate_Type_NameAccess().getDATEEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:3287:3: ( DATE )
					// InternalStructuredTextParser.g:3287:4: DATE
					{
						match(input, DATE, FOLLOW_2);

					}

					after(grammarAccess.getDate_Type_NameAccess().getDATEEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:3291:2: ( ( LDATE ) )
			{
				// InternalStructuredTextParser.g:3291:2: ( ( LDATE ) )
				// InternalStructuredTextParser.g:3292:3: ( LDATE )
				{
					before(grammarAccess.getDate_Type_NameAccess().getLDATEEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:3293:3: ( LDATE )
					// InternalStructuredTextParser.g:3293:4: LDATE
					{
						match(input, LDATE, FOLLOW_2);

					}

					after(grammarAccess.getDate_Type_NameAccess().getLDATEEnumLiteralDeclaration_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:3297:2: ( ( D_1 ) )
			{
				// InternalStructuredTextParser.g:3297:2: ( ( D_1 ) )
				// InternalStructuredTextParser.g:3298:3: ( D_1 )
				{
					before(grammarAccess.getDate_Type_NameAccess().getDEnumLiteralDeclaration_2());
					// InternalStructuredTextParser.g:3299:3: ( D_1 )
					// InternalStructuredTextParser.g:3299:4: D_1
					{
						match(input, D_1, FOLLOW_2);

					}

					after(grammarAccess.getDate_Type_NameAccess().getDEnumLiteralDeclaration_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:3303:2: ( ( LD ) )
			{
				// InternalStructuredTextParser.g:3303:2: ( ( LD ) )
				// InternalStructuredTextParser.g:3304:3: ( LD )
				{
					before(grammarAccess.getDate_Type_NameAccess().getLDEnumLiteralDeclaration_3());
					// InternalStructuredTextParser.g:3305:3: ( LD )
					// InternalStructuredTextParser.g:3305:4: LD
					{
						match(input, LD, FOLLOW_2);

					}

					after(grammarAccess.getDate_Type_NameAccess().getLDEnumLiteralDeclaration_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Type_Name__Alternatives"

	// $ANTLR start "rule__DT_Type_Name__Alternatives"
	// InternalStructuredTextParser.g:3313:1: rule__DT_Type_Name__Alternatives : ( (
	// ( DATE_AND_TIME ) ) | ( ( LDATE_AND_TIME ) ) | ( ( DT ) ) | ( ( LDT ) ) );
	public final void rule__DT_Type_Name__Alternatives() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3317:1: ( ( ( DATE_AND_TIME ) ) | ( (
			// LDATE_AND_TIME ) ) | ( ( DT ) ) | ( ( LDT ) ) )
			int alt36 = 4;
			switch (input.LA(1)) {
			case DATE_AND_TIME: {
				alt36 = 1;
			}
				break;
			case LDATE_AND_TIME: {
				alt36 = 2;
			}
				break;
			case DT: {
				alt36 = 3;
			}
				break;
			case LDT: {
				alt36 = 4;
			}
				break;
			default:
				NoViableAltException nvae = new NoViableAltException("", 36, 0, input);

				throw nvae;
			}

			switch (alt36) {
			case 1:
			// InternalStructuredTextParser.g:3318:2: ( ( DATE_AND_TIME ) )
			{
				// InternalStructuredTextParser.g:3318:2: ( ( DATE_AND_TIME ) )
				// InternalStructuredTextParser.g:3319:3: ( DATE_AND_TIME )
				{
					before(grammarAccess.getDT_Type_NameAccess().getDATE_AND_TIMEEnumLiteralDeclaration_0());
					// InternalStructuredTextParser.g:3320:3: ( DATE_AND_TIME )
					// InternalStructuredTextParser.g:3320:4: DATE_AND_TIME
					{
						match(input, DATE_AND_TIME, FOLLOW_2);

					}

					after(grammarAccess.getDT_Type_NameAccess().getDATE_AND_TIMEEnumLiteralDeclaration_0());

				}

			}
				break;
			case 2:
			// InternalStructuredTextParser.g:3324:2: ( ( LDATE_AND_TIME ) )
			{
				// InternalStructuredTextParser.g:3324:2: ( ( LDATE_AND_TIME ) )
				// InternalStructuredTextParser.g:3325:3: ( LDATE_AND_TIME )
				{
					before(grammarAccess.getDT_Type_NameAccess().getLDATE_AND_TIMEEnumLiteralDeclaration_1());
					// InternalStructuredTextParser.g:3326:3: ( LDATE_AND_TIME )
					// InternalStructuredTextParser.g:3326:4: LDATE_AND_TIME
					{
						match(input, LDATE_AND_TIME, FOLLOW_2);

					}

					after(grammarAccess.getDT_Type_NameAccess().getLDATE_AND_TIMEEnumLiteralDeclaration_1());

				}

			}
				break;
			case 3:
			// InternalStructuredTextParser.g:3330:2: ( ( DT ) )
			{
				// InternalStructuredTextParser.g:3330:2: ( ( DT ) )
				// InternalStructuredTextParser.g:3331:3: ( DT )
				{
					before(grammarAccess.getDT_Type_NameAccess().getDTEnumLiteralDeclaration_2());
					// InternalStructuredTextParser.g:3332:3: ( DT )
					// InternalStructuredTextParser.g:3332:4: DT
					{
						match(input, DT, FOLLOW_2);

					}

					after(grammarAccess.getDT_Type_NameAccess().getDTEnumLiteralDeclaration_2());

				}

			}
				break;
			case 4:
			// InternalStructuredTextParser.g:3336:2: ( ( LDT ) )
			{
				// InternalStructuredTextParser.g:3336:2: ( ( LDT ) )
				// InternalStructuredTextParser.g:3337:3: ( LDT )
				{
					before(grammarAccess.getDT_Type_NameAccess().getLDTEnumLiteralDeclaration_3());
					// InternalStructuredTextParser.g:3338:3: ( LDT )
					// InternalStructuredTextParser.g:3338:4: LDT
					{
						match(input, LDT, FOLLOW_2);

					}

					after(grammarAccess.getDT_Type_NameAccess().getLDTEnumLiteralDeclaration_3());

				}

			}
				break;

			}
		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__DT_Type_Name__Alternatives"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group__0"
	// InternalStructuredTextParser.g:3346:1:
	// rule__StructuredTextAlgorithm__Group__0 :
	// rule__StructuredTextAlgorithm__Group__0__Impl
	// rule__StructuredTextAlgorithm__Group__1 ;
	public final void rule__StructuredTextAlgorithm__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3350:1: (
			// rule__StructuredTextAlgorithm__Group__0__Impl
			// rule__StructuredTextAlgorithm__Group__1 )
			// InternalStructuredTextParser.g:3351:2:
			// rule__StructuredTextAlgorithm__Group__0__Impl
			// rule__StructuredTextAlgorithm__Group__1
			{
				pushFollow(FOLLOW_3);
				rule__StructuredTextAlgorithm__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__StructuredTextAlgorithm__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group__0"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group__0__Impl"
	// InternalStructuredTextParser.g:3358:1:
	// rule__StructuredTextAlgorithm__Group__0__Impl : ( () ) ;
	public final void rule__StructuredTextAlgorithm__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3362:1: ( ( () ) )
			// InternalStructuredTextParser.g:3363:1: ( () )
			{
				// InternalStructuredTextParser.g:3363:1: ( () )
				// InternalStructuredTextParser.g:3364:2: ()
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess().getStructuredTextAlgorithmAction_0());
					// InternalStructuredTextParser.g:3365:2: ()
					// InternalStructuredTextParser.g:3365:3:
					{
					}

					after(grammarAccess.getStructuredTextAlgorithmAccess().getStructuredTextAlgorithmAction_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group__0__Impl"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group__1"
	// InternalStructuredTextParser.g:3373:1:
	// rule__StructuredTextAlgorithm__Group__1 :
	// rule__StructuredTextAlgorithm__Group__1__Impl
	// rule__StructuredTextAlgorithm__Group__2 ;
	public final void rule__StructuredTextAlgorithm__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3377:1: (
			// rule__StructuredTextAlgorithm__Group__1__Impl
			// rule__StructuredTextAlgorithm__Group__2 )
			// InternalStructuredTextParser.g:3378:2:
			// rule__StructuredTextAlgorithm__Group__1__Impl
			// rule__StructuredTextAlgorithm__Group__2
			{
				pushFollow(FOLLOW_3);
				rule__StructuredTextAlgorithm__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__StructuredTextAlgorithm__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group__1"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group__1__Impl"
	// InternalStructuredTextParser.g:3385:1:
	// rule__StructuredTextAlgorithm__Group__1__Impl : ( (
	// rule__StructuredTextAlgorithm__Group_1__0 )? ) ;
	public final void rule__StructuredTextAlgorithm__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3389:1: ( ( (
			// rule__StructuredTextAlgorithm__Group_1__0 )? ) )
			// InternalStructuredTextParser.g:3390:1: ( (
			// rule__StructuredTextAlgorithm__Group_1__0 )? )
			{
				// InternalStructuredTextParser.g:3390:1: ( (
				// rule__StructuredTextAlgorithm__Group_1__0 )? )
				// InternalStructuredTextParser.g:3391:2: (
				// rule__StructuredTextAlgorithm__Group_1__0 )?
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1());
					// InternalStructuredTextParser.g:3392:2: (
					// rule__StructuredTextAlgorithm__Group_1__0 )?
					int alt37 = 2;
					int LA37_0 = input.LA(1);

					if ((LA37_0 == VAR)) {
						alt37 = 1;
					}
					switch (alt37) {
					case 1:
					// InternalStructuredTextParser.g:3392:3:
					// rule__StructuredTextAlgorithm__Group_1__0
					{
						pushFollow(FOLLOW_2);
						rule__StructuredTextAlgorithm__Group_1__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group__1__Impl"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group__2"
	// InternalStructuredTextParser.g:3400:1:
	// rule__StructuredTextAlgorithm__Group__2 :
	// rule__StructuredTextAlgorithm__Group__2__Impl ;
	public final void rule__StructuredTextAlgorithm__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3404:1: (
			// rule__StructuredTextAlgorithm__Group__2__Impl )
			// InternalStructuredTextParser.g:3405:2:
			// rule__StructuredTextAlgorithm__Group__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__StructuredTextAlgorithm__Group__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group__2"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group__2__Impl"
	// InternalStructuredTextParser.g:3411:1:
	// rule__StructuredTextAlgorithm__Group__2__Impl : ( (
	// rule__StructuredTextAlgorithm__StatementsAssignment_2 ) ) ;
	public final void rule__StructuredTextAlgorithm__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3415:1: ( ( (
			// rule__StructuredTextAlgorithm__StatementsAssignment_2 ) ) )
			// InternalStructuredTextParser.g:3416:1: ( (
			// rule__StructuredTextAlgorithm__StatementsAssignment_2 ) )
			{
				// InternalStructuredTextParser.g:3416:1: ( (
				// rule__StructuredTextAlgorithm__StatementsAssignment_2 ) )
				// InternalStructuredTextParser.g:3417:2: (
				// rule__StructuredTextAlgorithm__StatementsAssignment_2 )
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsAssignment_2());
					// InternalStructuredTextParser.g:3418:2: (
					// rule__StructuredTextAlgorithm__StatementsAssignment_2 )
					// InternalStructuredTextParser.g:3418:3:
					// rule__StructuredTextAlgorithm__StatementsAssignment_2
					{
						pushFollow(FOLLOW_2);
						rule__StructuredTextAlgorithm__StatementsAssignment_2();

						state._fsp--;

					}

					after(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsAssignment_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group__2__Impl"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group_1__0"
	// InternalStructuredTextParser.g:3427:1:
	// rule__StructuredTextAlgorithm__Group_1__0 :
	// rule__StructuredTextAlgorithm__Group_1__0__Impl
	// rule__StructuredTextAlgorithm__Group_1__1 ;
	public final void rule__StructuredTextAlgorithm__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3431:1: (
			// rule__StructuredTextAlgorithm__Group_1__0__Impl
			// rule__StructuredTextAlgorithm__Group_1__1 )
			// InternalStructuredTextParser.g:3432:2:
			// rule__StructuredTextAlgorithm__Group_1__0__Impl
			// rule__StructuredTextAlgorithm__Group_1__1
			{
				pushFollow(FOLLOW_4);
				rule__StructuredTextAlgorithm__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__StructuredTextAlgorithm__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group_1__0"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group_1__0__Impl"
	// InternalStructuredTextParser.g:3439:1:
	// rule__StructuredTextAlgorithm__Group_1__0__Impl : ( VAR ) ;
	public final void rule__StructuredTextAlgorithm__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3443:1: ( ( VAR ) )
			// InternalStructuredTextParser.g:3444:1: ( VAR )
			{
				// InternalStructuredTextParser.g:3444:1: ( VAR )
				// InternalStructuredTextParser.g:3445:2: VAR
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess().getVARKeyword_1_0());
					match(input, VAR, FOLLOW_2);
					after(grammarAccess.getStructuredTextAlgorithmAccess().getVARKeyword_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group_1__0__Impl"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group_1__1"
	// InternalStructuredTextParser.g:3454:1:
	// rule__StructuredTextAlgorithm__Group_1__1 :
	// rule__StructuredTextAlgorithm__Group_1__1__Impl
	// rule__StructuredTextAlgorithm__Group_1__2 ;
	public final void rule__StructuredTextAlgorithm__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3458:1: (
			// rule__StructuredTextAlgorithm__Group_1__1__Impl
			// rule__StructuredTextAlgorithm__Group_1__2 )
			// InternalStructuredTextParser.g:3459:2:
			// rule__StructuredTextAlgorithm__Group_1__1__Impl
			// rule__StructuredTextAlgorithm__Group_1__2
			{
				pushFollow(FOLLOW_4);
				rule__StructuredTextAlgorithm__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__StructuredTextAlgorithm__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group_1__1"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group_1__1__Impl"
	// InternalStructuredTextParser.g:3466:1:
	// rule__StructuredTextAlgorithm__Group_1__1__Impl : ( (
	// rule__StructuredTextAlgorithm__Group_1_1__0 )* ) ;
	public final void rule__StructuredTextAlgorithm__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3470:1: ( ( (
			// rule__StructuredTextAlgorithm__Group_1_1__0 )* ) )
			// InternalStructuredTextParser.g:3471:1: ( (
			// rule__StructuredTextAlgorithm__Group_1_1__0 )* )
			{
				// InternalStructuredTextParser.g:3471:1: ( (
				// rule__StructuredTextAlgorithm__Group_1_1__0 )* )
				// InternalStructuredTextParser.g:3472:2: (
				// rule__StructuredTextAlgorithm__Group_1_1__0 )*
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1_1());
					// InternalStructuredTextParser.g:3473:2: (
					// rule__StructuredTextAlgorithm__Group_1_1__0 )*
					loop38: do {
						int alt38 = 2;
						int LA38_0 = input.LA(1);

						if ((LA38_0 == CONSTANT || LA38_0 == RULE_ID)) {
							alt38 = 1;
						}

						switch (alt38) {
						case 1:
						// InternalStructuredTextParser.g:3473:3:
						// rule__StructuredTextAlgorithm__Group_1_1__0
						{
							pushFollow(FOLLOW_5);
							rule__StructuredTextAlgorithm__Group_1_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop38;
						}
					} while (true);

					after(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group_1__1__Impl"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group_1__2"
	// InternalStructuredTextParser.g:3481:1:
	// rule__StructuredTextAlgorithm__Group_1__2 :
	// rule__StructuredTextAlgorithm__Group_1__2__Impl ;
	public final void rule__StructuredTextAlgorithm__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3485:1: (
			// rule__StructuredTextAlgorithm__Group_1__2__Impl )
			// InternalStructuredTextParser.g:3486:2:
			// rule__StructuredTextAlgorithm__Group_1__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__StructuredTextAlgorithm__Group_1__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group_1__2"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group_1__2__Impl"
	// InternalStructuredTextParser.g:3492:1:
	// rule__StructuredTextAlgorithm__Group_1__2__Impl : ( END_VAR ) ;
	public final void rule__StructuredTextAlgorithm__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3496:1: ( ( END_VAR ) )
			// InternalStructuredTextParser.g:3497:1: ( END_VAR )
			{
				// InternalStructuredTextParser.g:3497:1: ( END_VAR )
				// InternalStructuredTextParser.g:3498:2: END_VAR
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess().getEND_VARKeyword_1_2());
					match(input, END_VAR, FOLLOW_2);
					after(grammarAccess.getStructuredTextAlgorithmAccess().getEND_VARKeyword_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group_1__2__Impl"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group_1_1__0"
	// InternalStructuredTextParser.g:3508:1:
	// rule__StructuredTextAlgorithm__Group_1_1__0 :
	// rule__StructuredTextAlgorithm__Group_1_1__0__Impl
	// rule__StructuredTextAlgorithm__Group_1_1__1 ;
	public final void rule__StructuredTextAlgorithm__Group_1_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3512:1: (
			// rule__StructuredTextAlgorithm__Group_1_1__0__Impl
			// rule__StructuredTextAlgorithm__Group_1_1__1 )
			// InternalStructuredTextParser.g:3513:2:
			// rule__StructuredTextAlgorithm__Group_1_1__0__Impl
			// rule__StructuredTextAlgorithm__Group_1_1__1
			{
				pushFollow(FOLLOW_6);
				rule__StructuredTextAlgorithm__Group_1_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__StructuredTextAlgorithm__Group_1_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group_1_1__0"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group_1_1__0__Impl"
	// InternalStructuredTextParser.g:3520:1:
	// rule__StructuredTextAlgorithm__Group_1_1__0__Impl : ( (
	// rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) ) ;
	public final void rule__StructuredTextAlgorithm__Group_1_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3524:1: ( ( (
			// rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) ) )
			// InternalStructuredTextParser.g:3525:1: ( (
			// rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) )
			{
				// InternalStructuredTextParser.g:3525:1: ( (
				// rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) )
				// InternalStructuredTextParser.g:3526:2: (
				// rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 )
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess().getLocalVariablesAssignment_1_1_0());
					// InternalStructuredTextParser.g:3527:2: (
					// rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 )
					// InternalStructuredTextParser.g:3527:3:
					// rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0
					{
						pushFollow(FOLLOW_2);
						rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0();

						state._fsp--;

					}

					after(grammarAccess.getStructuredTextAlgorithmAccess().getLocalVariablesAssignment_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group_1_1__0__Impl"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group_1_1__1"
	// InternalStructuredTextParser.g:3535:1:
	// rule__StructuredTextAlgorithm__Group_1_1__1 :
	// rule__StructuredTextAlgorithm__Group_1_1__1__Impl ;
	public final void rule__StructuredTextAlgorithm__Group_1_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3539:1: (
			// rule__StructuredTextAlgorithm__Group_1_1__1__Impl )
			// InternalStructuredTextParser.g:3540:2:
			// rule__StructuredTextAlgorithm__Group_1_1__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__StructuredTextAlgorithm__Group_1_1__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group_1_1__1"

	// $ANTLR start "rule__StructuredTextAlgorithm__Group_1_1__1__Impl"
	// InternalStructuredTextParser.g:3546:1:
	// rule__StructuredTextAlgorithm__Group_1_1__1__Impl : ( Semicolon ) ;
	public final void rule__StructuredTextAlgorithm__Group_1_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3550:1: ( ( Semicolon ) )
			// InternalStructuredTextParser.g:3551:1: ( Semicolon )
			{
				// InternalStructuredTextParser.g:3551:1: ( Semicolon )
				// InternalStructuredTextParser.g:3552:2: Semicolon
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess().getSemicolonKeyword_1_1_1());
					match(input, Semicolon, FOLLOW_2);
					after(grammarAccess.getStructuredTextAlgorithmAccess().getSemicolonKeyword_1_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__Group_1_1__1__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group__0"
	// InternalStructuredTextParser.g:3562:1: rule__Var_Decl_Local__Group__0 :
	// rule__Var_Decl_Local__Group__0__Impl rule__Var_Decl_Local__Group__1 ;
	public final void rule__Var_Decl_Local__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3566:1: ( rule__Var_Decl_Local__Group__0__Impl
			// rule__Var_Decl_Local__Group__1 )
			// InternalStructuredTextParser.g:3567:2: rule__Var_Decl_Local__Group__0__Impl
			// rule__Var_Decl_Local__Group__1
			{
				pushFollow(FOLLOW_7);
				rule__Var_Decl_Local__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__0"

	// $ANTLR start "rule__Var_Decl_Local__Group__0__Impl"
	// InternalStructuredTextParser.g:3574:1: rule__Var_Decl_Local__Group__0__Impl :
	// ( () ) ;
	public final void rule__Var_Decl_Local__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3578:1: ( ( () ) )
			// InternalStructuredTextParser.g:3579:1: ( () )
			{
				// InternalStructuredTextParser.g:3579:1: ( () )
				// InternalStructuredTextParser.g:3580:2: ()
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getLocalVariableAction_0());
					// InternalStructuredTextParser.g:3581:2: ()
					// InternalStructuredTextParser.g:3581:3:
					{
					}

					after(grammarAccess.getVar_Decl_LocalAccess().getLocalVariableAction_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__0__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group__1"
	// InternalStructuredTextParser.g:3589:1: rule__Var_Decl_Local__Group__1 :
	// rule__Var_Decl_Local__Group__1__Impl rule__Var_Decl_Local__Group__2 ;
	public final void rule__Var_Decl_Local__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3593:1: ( rule__Var_Decl_Local__Group__1__Impl
			// rule__Var_Decl_Local__Group__2 )
			// InternalStructuredTextParser.g:3594:2: rule__Var_Decl_Local__Group__1__Impl
			// rule__Var_Decl_Local__Group__2
			{
				pushFollow(FOLLOW_7);
				rule__Var_Decl_Local__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__1"

	// $ANTLR start "rule__Var_Decl_Local__Group__1__Impl"
	// InternalStructuredTextParser.g:3601:1: rule__Var_Decl_Local__Group__1__Impl :
	// ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? ) ;
	public final void rule__Var_Decl_Local__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3605:1: ( ( (
			// rule__Var_Decl_Local__ConstantAssignment_1 )? ) )
			// InternalStructuredTextParser.g:3606:1: ( (
			// rule__Var_Decl_Local__ConstantAssignment_1 )? )
			{
				// InternalStructuredTextParser.g:3606:1: ( (
				// rule__Var_Decl_Local__ConstantAssignment_1 )? )
				// InternalStructuredTextParser.g:3607:2: (
				// rule__Var_Decl_Local__ConstantAssignment_1 )?
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getConstantAssignment_1());
					// InternalStructuredTextParser.g:3608:2: (
					// rule__Var_Decl_Local__ConstantAssignment_1 )?
					int alt39 = 2;
					int LA39_0 = input.LA(1);

					if ((LA39_0 == CONSTANT)) {
						alt39 = 1;
					}
					switch (alt39) {
					case 1:
					// InternalStructuredTextParser.g:3608:3:
					// rule__Var_Decl_Local__ConstantAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Local__ConstantAssignment_1();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getConstantAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__1__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group__2"
	// InternalStructuredTextParser.g:3616:1: rule__Var_Decl_Local__Group__2 :
	// rule__Var_Decl_Local__Group__2__Impl rule__Var_Decl_Local__Group__3 ;
	public final void rule__Var_Decl_Local__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3620:1: ( rule__Var_Decl_Local__Group__2__Impl
			// rule__Var_Decl_Local__Group__3 )
			// InternalStructuredTextParser.g:3621:2: rule__Var_Decl_Local__Group__2__Impl
			// rule__Var_Decl_Local__Group__3
			{
				pushFollow(FOLLOW_8);
				rule__Var_Decl_Local__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__2"

	// $ANTLR start "rule__Var_Decl_Local__Group__2__Impl"
	// InternalStructuredTextParser.g:3628:1: rule__Var_Decl_Local__Group__2__Impl :
	// ( ( rule__Var_Decl_Local__NameAssignment_2 ) ) ;
	public final void rule__Var_Decl_Local__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3632:1: ( ( (
			// rule__Var_Decl_Local__NameAssignment_2 ) ) )
			// InternalStructuredTextParser.g:3633:1: ( (
			// rule__Var_Decl_Local__NameAssignment_2 ) )
			{
				// InternalStructuredTextParser.g:3633:1: ( (
				// rule__Var_Decl_Local__NameAssignment_2 ) )
				// InternalStructuredTextParser.g:3634:2: (
				// rule__Var_Decl_Local__NameAssignment_2 )
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getNameAssignment_2());
					// InternalStructuredTextParser.g:3635:2: (
					// rule__Var_Decl_Local__NameAssignment_2 )
					// InternalStructuredTextParser.g:3635:3: rule__Var_Decl_Local__NameAssignment_2
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Local__NameAssignment_2();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getNameAssignment_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__2__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group__3"
	// InternalStructuredTextParser.g:3643:1: rule__Var_Decl_Local__Group__3 :
	// rule__Var_Decl_Local__Group__3__Impl rule__Var_Decl_Local__Group__4 ;
	public final void rule__Var_Decl_Local__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3647:1: ( rule__Var_Decl_Local__Group__3__Impl
			// rule__Var_Decl_Local__Group__4 )
			// InternalStructuredTextParser.g:3648:2: rule__Var_Decl_Local__Group__3__Impl
			// rule__Var_Decl_Local__Group__4
			{
				pushFollow(FOLLOW_9);
				rule__Var_Decl_Local__Group__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__3"

	// $ANTLR start "rule__Var_Decl_Local__Group__3__Impl"
	// InternalStructuredTextParser.g:3655:1: rule__Var_Decl_Local__Group__3__Impl :
	// ( Colon ) ;
	public final void rule__Var_Decl_Local__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3659:1: ( ( Colon ) )
			// InternalStructuredTextParser.g:3660:1: ( Colon )
			{
				// InternalStructuredTextParser.g:3660:1: ( Colon )
				// InternalStructuredTextParser.g:3661:2: Colon
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getColonKeyword_3());
					match(input, Colon, FOLLOW_2);
					after(grammarAccess.getVar_Decl_LocalAccess().getColonKeyword_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__3__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group__4"
	// InternalStructuredTextParser.g:3670:1: rule__Var_Decl_Local__Group__4 :
	// rule__Var_Decl_Local__Group__4__Impl rule__Var_Decl_Local__Group__5 ;
	public final void rule__Var_Decl_Local__Group__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3674:1: ( rule__Var_Decl_Local__Group__4__Impl
			// rule__Var_Decl_Local__Group__5 )
			// InternalStructuredTextParser.g:3675:2: rule__Var_Decl_Local__Group__4__Impl
			// rule__Var_Decl_Local__Group__5
			{
				pushFollow(FOLLOW_10);
				rule__Var_Decl_Local__Group__4__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group__5();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__4"

	// $ANTLR start "rule__Var_Decl_Local__Group__4__Impl"
	// InternalStructuredTextParser.g:3682:1: rule__Var_Decl_Local__Group__4__Impl :
	// ( ( rule__Var_Decl_Local__TypeAssignment_4 ) ) ;
	public final void rule__Var_Decl_Local__Group__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3686:1: ( ( (
			// rule__Var_Decl_Local__TypeAssignment_4 ) ) )
			// InternalStructuredTextParser.g:3687:1: ( (
			// rule__Var_Decl_Local__TypeAssignment_4 ) )
			{
				// InternalStructuredTextParser.g:3687:1: ( (
				// rule__Var_Decl_Local__TypeAssignment_4 ) )
				// InternalStructuredTextParser.g:3688:2: (
				// rule__Var_Decl_Local__TypeAssignment_4 )
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getTypeAssignment_4());
					// InternalStructuredTextParser.g:3689:2: (
					// rule__Var_Decl_Local__TypeAssignment_4 )
					// InternalStructuredTextParser.g:3689:3: rule__Var_Decl_Local__TypeAssignment_4
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Local__TypeAssignment_4();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getTypeAssignment_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__4__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group__5"
	// InternalStructuredTextParser.g:3697:1: rule__Var_Decl_Local__Group__5 :
	// rule__Var_Decl_Local__Group__5__Impl rule__Var_Decl_Local__Group__6 ;
	public final void rule__Var_Decl_Local__Group__5() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3701:1: ( rule__Var_Decl_Local__Group__5__Impl
			// rule__Var_Decl_Local__Group__6 )
			// InternalStructuredTextParser.g:3702:2: rule__Var_Decl_Local__Group__5__Impl
			// rule__Var_Decl_Local__Group__6
			{
				pushFollow(FOLLOW_10);
				rule__Var_Decl_Local__Group__5__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group__6();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__5"

	// $ANTLR start "rule__Var_Decl_Local__Group__5__Impl"
	// InternalStructuredTextParser.g:3709:1: rule__Var_Decl_Local__Group__5__Impl :
	// ( ( rule__Var_Decl_Local__Group_5__0 )? ) ;
	public final void rule__Var_Decl_Local__Group__5__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3713:1: ( ( ( rule__Var_Decl_Local__Group_5__0
			// )? ) )
			// InternalStructuredTextParser.g:3714:1: ( ( rule__Var_Decl_Local__Group_5__0
			// )? )
			{
				// InternalStructuredTextParser.g:3714:1: ( ( rule__Var_Decl_Local__Group_5__0
				// )? )
				// InternalStructuredTextParser.g:3715:2: ( rule__Var_Decl_Local__Group_5__0 )?
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getGroup_5());
					// InternalStructuredTextParser.g:3716:2: ( rule__Var_Decl_Local__Group_5__0 )?
					int alt40 = 2;
					int LA40_0 = input.LA(1);

					if ((LA40_0 == LeftSquareBracket)) {
						alt40 = 1;
					}
					switch (alt40) {
					case 1:
					// InternalStructuredTextParser.g:3716:3: rule__Var_Decl_Local__Group_5__0
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Local__Group_5__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getGroup_5());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__5__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group__6"
	// InternalStructuredTextParser.g:3724:1: rule__Var_Decl_Local__Group__6 :
	// rule__Var_Decl_Local__Group__6__Impl ;
	public final void rule__Var_Decl_Local__Group__6() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3728:1: ( rule__Var_Decl_Local__Group__6__Impl
			// )
			// InternalStructuredTextParser.g:3729:2: rule__Var_Decl_Local__Group__6__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group__6__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__6"

	// $ANTLR start "rule__Var_Decl_Local__Group__6__Impl"
	// InternalStructuredTextParser.g:3735:1: rule__Var_Decl_Local__Group__6__Impl :
	// ( ( rule__Var_Decl_Local__Group_6__0 )? ) ;
	public final void rule__Var_Decl_Local__Group__6__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3739:1: ( ( ( rule__Var_Decl_Local__Group_6__0
			// )? ) )
			// InternalStructuredTextParser.g:3740:1: ( ( rule__Var_Decl_Local__Group_6__0
			// )? )
			{
				// InternalStructuredTextParser.g:3740:1: ( ( rule__Var_Decl_Local__Group_6__0
				// )? )
				// InternalStructuredTextParser.g:3741:2: ( rule__Var_Decl_Local__Group_6__0 )?
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getGroup_6());
					// InternalStructuredTextParser.g:3742:2: ( rule__Var_Decl_Local__Group_6__0 )?
					int alt41 = 2;
					int LA41_0 = input.LA(1);

					if ((LA41_0 == ColonEqualsSign)) {
						alt41 = 1;
					}
					switch (alt41) {
					case 1:
					// InternalStructuredTextParser.g:3742:3: rule__Var_Decl_Local__Group_6__0
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Local__Group_6__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getGroup_6());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group__6__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group_5__0"
	// InternalStructuredTextParser.g:3751:1: rule__Var_Decl_Local__Group_5__0 :
	// rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1 ;
	public final void rule__Var_Decl_Local__Group_5__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3755:1: (
			// rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1 )
			// InternalStructuredTextParser.g:3756:2: rule__Var_Decl_Local__Group_5__0__Impl
			// rule__Var_Decl_Local__Group_5__1
			{
				pushFollow(FOLLOW_11);
				rule__Var_Decl_Local__Group_5__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group_5__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group_5__0"

	// $ANTLR start "rule__Var_Decl_Local__Group_5__0__Impl"
	// InternalStructuredTextParser.g:3763:1: rule__Var_Decl_Local__Group_5__0__Impl
	// : ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) ) ;
	public final void rule__Var_Decl_Local__Group_5__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3767:1: ( ( (
			// rule__Var_Decl_Local__ArrayAssignment_5_0 ) ) )
			// InternalStructuredTextParser.g:3768:1: ( (
			// rule__Var_Decl_Local__ArrayAssignment_5_0 ) )
			{
				// InternalStructuredTextParser.g:3768:1: ( (
				// rule__Var_Decl_Local__ArrayAssignment_5_0 ) )
				// InternalStructuredTextParser.g:3769:2: (
				// rule__Var_Decl_Local__ArrayAssignment_5_0 )
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getArrayAssignment_5_0());
					// InternalStructuredTextParser.g:3770:2: (
					// rule__Var_Decl_Local__ArrayAssignment_5_0 )
					// InternalStructuredTextParser.g:3770:3:
					// rule__Var_Decl_Local__ArrayAssignment_5_0
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Local__ArrayAssignment_5_0();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getArrayAssignment_5_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group_5__0__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group_5__1"
	// InternalStructuredTextParser.g:3778:1: rule__Var_Decl_Local__Group_5__1 :
	// rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2 ;
	public final void rule__Var_Decl_Local__Group_5__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3782:1: (
			// rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2 )
			// InternalStructuredTextParser.g:3783:2: rule__Var_Decl_Local__Group_5__1__Impl
			// rule__Var_Decl_Local__Group_5__2
			{
				pushFollow(FOLLOW_12);
				rule__Var_Decl_Local__Group_5__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group_5__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group_5__1"

	// $ANTLR start "rule__Var_Decl_Local__Group_5__1__Impl"
	// InternalStructuredTextParser.g:3790:1: rule__Var_Decl_Local__Group_5__1__Impl
	// : ( ( rule__Var_Decl_Local__ArraySizeAssignment_5_1 ) ) ;
	public final void rule__Var_Decl_Local__Group_5__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3794:1: ( ( (
			// rule__Var_Decl_Local__ArraySizeAssignment_5_1 ) ) )
			// InternalStructuredTextParser.g:3795:1: ( (
			// rule__Var_Decl_Local__ArraySizeAssignment_5_1 ) )
			{
				// InternalStructuredTextParser.g:3795:1: ( (
				// rule__Var_Decl_Local__ArraySizeAssignment_5_1 ) )
				// InternalStructuredTextParser.g:3796:2: (
				// rule__Var_Decl_Local__ArraySizeAssignment_5_1 )
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getArraySizeAssignment_5_1());
					// InternalStructuredTextParser.g:3797:2: (
					// rule__Var_Decl_Local__ArraySizeAssignment_5_1 )
					// InternalStructuredTextParser.g:3797:3:
					// rule__Var_Decl_Local__ArraySizeAssignment_5_1
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Local__ArraySizeAssignment_5_1();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getArraySizeAssignment_5_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group_5__1__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group_5__2"
	// InternalStructuredTextParser.g:3805:1: rule__Var_Decl_Local__Group_5__2 :
	// rule__Var_Decl_Local__Group_5__2__Impl ;
	public final void rule__Var_Decl_Local__Group_5__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3809:1: (
			// rule__Var_Decl_Local__Group_5__2__Impl )
			// InternalStructuredTextParser.g:3810:2: rule__Var_Decl_Local__Group_5__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group_5__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group_5__2"

	// $ANTLR start "rule__Var_Decl_Local__Group_5__2__Impl"
	// InternalStructuredTextParser.g:3816:1: rule__Var_Decl_Local__Group_5__2__Impl
	// : ( RightSquareBracket ) ;
	public final void rule__Var_Decl_Local__Group_5__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3820:1: ( ( RightSquareBracket ) )
			// InternalStructuredTextParser.g:3821:1: ( RightSquareBracket )
			{
				// InternalStructuredTextParser.g:3821:1: ( RightSquareBracket )
				// InternalStructuredTextParser.g:3822:2: RightSquareBracket
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getRightSquareBracketKeyword_5_2());
					match(input, RightSquareBracket, FOLLOW_2);
					after(grammarAccess.getVar_Decl_LocalAccess().getRightSquareBracketKeyword_5_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group_5__2__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group_6__0"
	// InternalStructuredTextParser.g:3832:1: rule__Var_Decl_Local__Group_6__0 :
	// rule__Var_Decl_Local__Group_6__0__Impl rule__Var_Decl_Local__Group_6__1 ;
	public final void rule__Var_Decl_Local__Group_6__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3836:1: (
			// rule__Var_Decl_Local__Group_6__0__Impl rule__Var_Decl_Local__Group_6__1 )
			// InternalStructuredTextParser.g:3837:2: rule__Var_Decl_Local__Group_6__0__Impl
			// rule__Var_Decl_Local__Group_6__1
			{
				pushFollow(FOLLOW_13);
				rule__Var_Decl_Local__Group_6__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group_6__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group_6__0"

	// $ANTLR start "rule__Var_Decl_Local__Group_6__0__Impl"
	// InternalStructuredTextParser.g:3844:1: rule__Var_Decl_Local__Group_6__0__Impl
	// : ( ColonEqualsSign ) ;
	public final void rule__Var_Decl_Local__Group_6__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3848:1: ( ( ColonEqualsSign ) )
			// InternalStructuredTextParser.g:3849:1: ( ColonEqualsSign )
			{
				// InternalStructuredTextParser.g:3849:1: ( ColonEqualsSign )
				// InternalStructuredTextParser.g:3850:2: ColonEqualsSign
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getColonEqualsSignKeyword_6_0());
					match(input, ColonEqualsSign, FOLLOW_2);
					after(grammarAccess.getVar_Decl_LocalAccess().getColonEqualsSignKeyword_6_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group_6__0__Impl"

	// $ANTLR start "rule__Var_Decl_Local__Group_6__1"
	// InternalStructuredTextParser.g:3859:1: rule__Var_Decl_Local__Group_6__1 :
	// rule__Var_Decl_Local__Group_6__1__Impl ;
	public final void rule__Var_Decl_Local__Group_6__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3863:1: (
			// rule__Var_Decl_Local__Group_6__1__Impl )
			// InternalStructuredTextParser.g:3864:2: rule__Var_Decl_Local__Group_6__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Var_Decl_Local__Group_6__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group_6__1"

	// $ANTLR start "rule__Var_Decl_Local__Group_6__1__Impl"
	// InternalStructuredTextParser.g:3870:1: rule__Var_Decl_Local__Group_6__1__Impl
	// : ( ( rule__Var_Decl_Local__InitialValueAssignment_6_1 ) ) ;
	public final void rule__Var_Decl_Local__Group_6__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3874:1: ( ( (
			// rule__Var_Decl_Local__InitialValueAssignment_6_1 ) ) )
			// InternalStructuredTextParser.g:3875:1: ( (
			// rule__Var_Decl_Local__InitialValueAssignment_6_1 ) )
			{
				// InternalStructuredTextParser.g:3875:1: ( (
				// rule__Var_Decl_Local__InitialValueAssignment_6_1 ) )
				// InternalStructuredTextParser.g:3876:2: (
				// rule__Var_Decl_Local__InitialValueAssignment_6_1 )
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getInitialValueAssignment_6_1());
					// InternalStructuredTextParser.g:3877:2: (
					// rule__Var_Decl_Local__InitialValueAssignment_6_1 )
					// InternalStructuredTextParser.g:3877:3:
					// rule__Var_Decl_Local__InitialValueAssignment_6_1
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Local__InitialValueAssignment_6_1();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getInitialValueAssignment_6_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__Group_6__1__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group__0"
	// InternalStructuredTextParser.g:3886:1: rule__Var_Decl_Located__Group__0 :
	// rule__Var_Decl_Located__Group__0__Impl rule__Var_Decl_Located__Group__1 ;
	public final void rule__Var_Decl_Located__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3890:1: (
			// rule__Var_Decl_Located__Group__0__Impl rule__Var_Decl_Located__Group__1 )
			// InternalStructuredTextParser.g:3891:2: rule__Var_Decl_Located__Group__0__Impl
			// rule__Var_Decl_Located__Group__1
			{
				pushFollow(FOLLOW_7);
				rule__Var_Decl_Located__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__0"

	// $ANTLR start "rule__Var_Decl_Located__Group__0__Impl"
	// InternalStructuredTextParser.g:3898:1: rule__Var_Decl_Located__Group__0__Impl
	// : ( () ) ;
	public final void rule__Var_Decl_Located__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3902:1: ( ( () ) )
			// InternalStructuredTextParser.g:3903:1: ( () )
			{
				// InternalStructuredTextParser.g:3903:1: ( () )
				// InternalStructuredTextParser.g:3904:2: ()
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getLocatedVariableAction_0());
					// InternalStructuredTextParser.g:3905:2: ()
					// InternalStructuredTextParser.g:3905:3:
					{
					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getLocatedVariableAction_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__0__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group__1"
	// InternalStructuredTextParser.g:3913:1: rule__Var_Decl_Located__Group__1 :
	// rule__Var_Decl_Located__Group__1__Impl rule__Var_Decl_Located__Group__2 ;
	public final void rule__Var_Decl_Located__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3917:1: (
			// rule__Var_Decl_Located__Group__1__Impl rule__Var_Decl_Located__Group__2 )
			// InternalStructuredTextParser.g:3918:2: rule__Var_Decl_Located__Group__1__Impl
			// rule__Var_Decl_Located__Group__2
			{
				pushFollow(FOLLOW_14);
				rule__Var_Decl_Located__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__1"

	// $ANTLR start "rule__Var_Decl_Located__Group__1__Impl"
	// InternalStructuredTextParser.g:3925:1: rule__Var_Decl_Located__Group__1__Impl
	// : ( ( rule__Var_Decl_Located__NameAssignment_1 ) ) ;
	public final void rule__Var_Decl_Located__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3929:1: ( ( (
			// rule__Var_Decl_Located__NameAssignment_1 ) ) )
			// InternalStructuredTextParser.g:3930:1: ( (
			// rule__Var_Decl_Located__NameAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:3930:1: ( (
				// rule__Var_Decl_Located__NameAssignment_1 ) )
				// InternalStructuredTextParser.g:3931:2: (
				// rule__Var_Decl_Located__NameAssignment_1 )
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getNameAssignment_1());
					// InternalStructuredTextParser.g:3932:2: (
					// rule__Var_Decl_Located__NameAssignment_1 )
					// InternalStructuredTextParser.g:3932:3:
					// rule__Var_Decl_Located__NameAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Located__NameAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getNameAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__1__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group__2"
	// InternalStructuredTextParser.g:3940:1: rule__Var_Decl_Located__Group__2 :
	// rule__Var_Decl_Located__Group__2__Impl rule__Var_Decl_Located__Group__3 ;
	public final void rule__Var_Decl_Located__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3944:1: (
			// rule__Var_Decl_Located__Group__2__Impl rule__Var_Decl_Located__Group__3 )
			// InternalStructuredTextParser.g:3945:2: rule__Var_Decl_Located__Group__2__Impl
			// rule__Var_Decl_Located__Group__3
			{
				pushFollow(FOLLOW_8);
				rule__Var_Decl_Located__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__2"

	// $ANTLR start "rule__Var_Decl_Located__Group__2__Impl"
	// InternalStructuredTextParser.g:3952:1: rule__Var_Decl_Located__Group__2__Impl
	// : ( ( rule__Var_Decl_Located__Group_2__0 ) ) ;
	public final void rule__Var_Decl_Located__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3956:1: ( ( (
			// rule__Var_Decl_Located__Group_2__0 ) ) )
			// InternalStructuredTextParser.g:3957:1: ( ( rule__Var_Decl_Located__Group_2__0
			// ) )
			{
				// InternalStructuredTextParser.g:3957:1: ( ( rule__Var_Decl_Located__Group_2__0
				// ) )
				// InternalStructuredTextParser.g:3958:2: ( rule__Var_Decl_Located__Group_2__0 )
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getGroup_2());
					// InternalStructuredTextParser.g:3959:2: ( rule__Var_Decl_Located__Group_2__0 )
					// InternalStructuredTextParser.g:3959:3: rule__Var_Decl_Located__Group_2__0
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Located__Group_2__0();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getGroup_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__2__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group__3"
	// InternalStructuredTextParser.g:3967:1: rule__Var_Decl_Located__Group__3 :
	// rule__Var_Decl_Located__Group__3__Impl rule__Var_Decl_Located__Group__4 ;
	public final void rule__Var_Decl_Located__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3971:1: (
			// rule__Var_Decl_Located__Group__3__Impl rule__Var_Decl_Located__Group__4 )
			// InternalStructuredTextParser.g:3972:2: rule__Var_Decl_Located__Group__3__Impl
			// rule__Var_Decl_Located__Group__4
			{
				pushFollow(FOLLOW_9);
				rule__Var_Decl_Located__Group__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__3"

	// $ANTLR start "rule__Var_Decl_Located__Group__3__Impl"
	// InternalStructuredTextParser.g:3979:1: rule__Var_Decl_Located__Group__3__Impl
	// : ( Colon ) ;
	public final void rule__Var_Decl_Located__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3983:1: ( ( Colon ) )
			// InternalStructuredTextParser.g:3984:1: ( Colon )
			{
				// InternalStructuredTextParser.g:3984:1: ( Colon )
				// InternalStructuredTextParser.g:3985:2: Colon
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getColonKeyword_3());
					match(input, Colon, FOLLOW_2);
					after(grammarAccess.getVar_Decl_LocatedAccess().getColonKeyword_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__3__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group__4"
	// InternalStructuredTextParser.g:3994:1: rule__Var_Decl_Located__Group__4 :
	// rule__Var_Decl_Located__Group__4__Impl rule__Var_Decl_Located__Group__5 ;
	public final void rule__Var_Decl_Located__Group__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:3998:1: (
			// rule__Var_Decl_Located__Group__4__Impl rule__Var_Decl_Located__Group__5 )
			// InternalStructuredTextParser.g:3999:2: rule__Var_Decl_Located__Group__4__Impl
			// rule__Var_Decl_Located__Group__5
			{
				pushFollow(FOLLOW_15);
				rule__Var_Decl_Located__Group__4__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group__5();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__4"

	// $ANTLR start "rule__Var_Decl_Located__Group__4__Impl"
	// InternalStructuredTextParser.g:4006:1: rule__Var_Decl_Located__Group__4__Impl
	// : ( ( rule__Var_Decl_Located__TypeAssignment_4 ) ) ;
	public final void rule__Var_Decl_Located__Group__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4010:1: ( ( (
			// rule__Var_Decl_Located__TypeAssignment_4 ) ) )
			// InternalStructuredTextParser.g:4011:1: ( (
			// rule__Var_Decl_Located__TypeAssignment_4 ) )
			{
				// InternalStructuredTextParser.g:4011:1: ( (
				// rule__Var_Decl_Located__TypeAssignment_4 ) )
				// InternalStructuredTextParser.g:4012:2: (
				// rule__Var_Decl_Located__TypeAssignment_4 )
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getTypeAssignment_4());
					// InternalStructuredTextParser.g:4013:2: (
					// rule__Var_Decl_Located__TypeAssignment_4 )
					// InternalStructuredTextParser.g:4013:3:
					// rule__Var_Decl_Located__TypeAssignment_4
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Located__TypeAssignment_4();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getTypeAssignment_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__4__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group__5"
	// InternalStructuredTextParser.g:4021:1: rule__Var_Decl_Located__Group__5 :
	// rule__Var_Decl_Located__Group__5__Impl ;
	public final void rule__Var_Decl_Located__Group__5() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4025:1: (
			// rule__Var_Decl_Located__Group__5__Impl )
			// InternalStructuredTextParser.g:4026:2: rule__Var_Decl_Located__Group__5__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group__5__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__5"

	// $ANTLR start "rule__Var_Decl_Located__Group__5__Impl"
	// InternalStructuredTextParser.g:4032:1: rule__Var_Decl_Located__Group__5__Impl
	// : ( ( rule__Var_Decl_Located__Group_5__0 )? ) ;
	public final void rule__Var_Decl_Located__Group__5__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4036:1: ( ( (
			// rule__Var_Decl_Located__Group_5__0 )? ) )
			// InternalStructuredTextParser.g:4037:1: ( ( rule__Var_Decl_Located__Group_5__0
			// )? )
			{
				// InternalStructuredTextParser.g:4037:1: ( ( rule__Var_Decl_Located__Group_5__0
				// )? )
				// InternalStructuredTextParser.g:4038:2: ( rule__Var_Decl_Located__Group_5__0
				// )?
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getGroup_5());
					// InternalStructuredTextParser.g:4039:2: ( rule__Var_Decl_Located__Group_5__0
					// )?
					int alt42 = 2;
					int LA42_0 = input.LA(1);

					if ((LA42_0 == LeftSquareBracket)) {
						alt42 = 1;
					}
					switch (alt42) {
					case 1:
					// InternalStructuredTextParser.g:4039:3: rule__Var_Decl_Located__Group_5__0
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Located__Group_5__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getGroup_5());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group__5__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group_2__0"
	// InternalStructuredTextParser.g:4048:1: rule__Var_Decl_Located__Group_2__0 :
	// rule__Var_Decl_Located__Group_2__0__Impl rule__Var_Decl_Located__Group_2__1 ;
	public final void rule__Var_Decl_Located__Group_2__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4052:1: (
			// rule__Var_Decl_Located__Group_2__0__Impl rule__Var_Decl_Located__Group_2__1 )
			// InternalStructuredTextParser.g:4053:2:
			// rule__Var_Decl_Located__Group_2__0__Impl rule__Var_Decl_Located__Group_2__1
			{
				pushFollow(FOLLOW_16);
				rule__Var_Decl_Located__Group_2__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group_2__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group_2__0"

	// $ANTLR start "rule__Var_Decl_Located__Group_2__0__Impl"
	// InternalStructuredTextParser.g:4060:1:
	// rule__Var_Decl_Located__Group_2__0__Impl : ( AT ) ;
	public final void rule__Var_Decl_Located__Group_2__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4064:1: ( ( AT ) )
			// InternalStructuredTextParser.g:4065:1: ( AT )
			{
				// InternalStructuredTextParser.g:4065:1: ( AT )
				// InternalStructuredTextParser.g:4066:2: AT
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getATKeyword_2_0());
					match(input, AT, FOLLOW_2);
					after(grammarAccess.getVar_Decl_LocatedAccess().getATKeyword_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group_2__0__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group_2__1"
	// InternalStructuredTextParser.g:4075:1: rule__Var_Decl_Located__Group_2__1 :
	// rule__Var_Decl_Located__Group_2__1__Impl ;
	public final void rule__Var_Decl_Located__Group_2__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4079:1: (
			// rule__Var_Decl_Located__Group_2__1__Impl )
			// InternalStructuredTextParser.g:4080:2:
			// rule__Var_Decl_Located__Group_2__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group_2__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group_2__1"

	// $ANTLR start "rule__Var_Decl_Located__Group_2__1__Impl"
	// InternalStructuredTextParser.g:4086:1:
	// rule__Var_Decl_Located__Group_2__1__Impl : ( (
	// rule__Var_Decl_Located__LocationAssignment_2_1 ) ) ;
	public final void rule__Var_Decl_Located__Group_2__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4090:1: ( ( (
			// rule__Var_Decl_Located__LocationAssignment_2_1 ) ) )
			// InternalStructuredTextParser.g:4091:1: ( (
			// rule__Var_Decl_Located__LocationAssignment_2_1 ) )
			{
				// InternalStructuredTextParser.g:4091:1: ( (
				// rule__Var_Decl_Located__LocationAssignment_2_1 ) )
				// InternalStructuredTextParser.g:4092:2: (
				// rule__Var_Decl_Located__LocationAssignment_2_1 )
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getLocationAssignment_2_1());
					// InternalStructuredTextParser.g:4093:2: (
					// rule__Var_Decl_Located__LocationAssignment_2_1 )
					// InternalStructuredTextParser.g:4093:3:
					// rule__Var_Decl_Located__LocationAssignment_2_1
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Located__LocationAssignment_2_1();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getLocationAssignment_2_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group_2__1__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group_5__0"
	// InternalStructuredTextParser.g:4102:1: rule__Var_Decl_Located__Group_5__0 :
	// rule__Var_Decl_Located__Group_5__0__Impl rule__Var_Decl_Located__Group_5__1 ;
	public final void rule__Var_Decl_Located__Group_5__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4106:1: (
			// rule__Var_Decl_Located__Group_5__0__Impl rule__Var_Decl_Located__Group_5__1 )
			// InternalStructuredTextParser.g:4107:2:
			// rule__Var_Decl_Located__Group_5__0__Impl rule__Var_Decl_Located__Group_5__1
			{
				pushFollow(FOLLOW_11);
				rule__Var_Decl_Located__Group_5__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group_5__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group_5__0"

	// $ANTLR start "rule__Var_Decl_Located__Group_5__0__Impl"
	// InternalStructuredTextParser.g:4114:1:
	// rule__Var_Decl_Located__Group_5__0__Impl : ( (
	// rule__Var_Decl_Located__ArrayAssignment_5_0 ) ) ;
	public final void rule__Var_Decl_Located__Group_5__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4118:1: ( ( (
			// rule__Var_Decl_Located__ArrayAssignment_5_0 ) ) )
			// InternalStructuredTextParser.g:4119:1: ( (
			// rule__Var_Decl_Located__ArrayAssignment_5_0 ) )
			{
				// InternalStructuredTextParser.g:4119:1: ( (
				// rule__Var_Decl_Located__ArrayAssignment_5_0 ) )
				// InternalStructuredTextParser.g:4120:2: (
				// rule__Var_Decl_Located__ArrayAssignment_5_0 )
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getArrayAssignment_5_0());
					// InternalStructuredTextParser.g:4121:2: (
					// rule__Var_Decl_Located__ArrayAssignment_5_0 )
					// InternalStructuredTextParser.g:4121:3:
					// rule__Var_Decl_Located__ArrayAssignment_5_0
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Located__ArrayAssignment_5_0();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getArrayAssignment_5_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group_5__0__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group_5__1"
	// InternalStructuredTextParser.g:4129:1: rule__Var_Decl_Located__Group_5__1 :
	// rule__Var_Decl_Located__Group_5__1__Impl rule__Var_Decl_Located__Group_5__2 ;
	public final void rule__Var_Decl_Located__Group_5__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4133:1: (
			// rule__Var_Decl_Located__Group_5__1__Impl rule__Var_Decl_Located__Group_5__2 )
			// InternalStructuredTextParser.g:4134:2:
			// rule__Var_Decl_Located__Group_5__1__Impl rule__Var_Decl_Located__Group_5__2
			{
				pushFollow(FOLLOW_12);
				rule__Var_Decl_Located__Group_5__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group_5__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group_5__1"

	// $ANTLR start "rule__Var_Decl_Located__Group_5__1__Impl"
	// InternalStructuredTextParser.g:4141:1:
	// rule__Var_Decl_Located__Group_5__1__Impl : ( (
	// rule__Var_Decl_Located__ArraySizeAssignment_5_1 ) ) ;
	public final void rule__Var_Decl_Located__Group_5__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4145:1: ( ( (
			// rule__Var_Decl_Located__ArraySizeAssignment_5_1 ) ) )
			// InternalStructuredTextParser.g:4146:1: ( (
			// rule__Var_Decl_Located__ArraySizeAssignment_5_1 ) )
			{
				// InternalStructuredTextParser.g:4146:1: ( (
				// rule__Var_Decl_Located__ArraySizeAssignment_5_1 ) )
				// InternalStructuredTextParser.g:4147:2: (
				// rule__Var_Decl_Located__ArraySizeAssignment_5_1 )
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getArraySizeAssignment_5_1());
					// InternalStructuredTextParser.g:4148:2: (
					// rule__Var_Decl_Located__ArraySizeAssignment_5_1 )
					// InternalStructuredTextParser.g:4148:3:
					// rule__Var_Decl_Located__ArraySizeAssignment_5_1
					{
						pushFollow(FOLLOW_2);
						rule__Var_Decl_Located__ArraySizeAssignment_5_1();

						state._fsp--;

					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getArraySizeAssignment_5_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group_5__1__Impl"

	// $ANTLR start "rule__Var_Decl_Located__Group_5__2"
	// InternalStructuredTextParser.g:4156:1: rule__Var_Decl_Located__Group_5__2 :
	// rule__Var_Decl_Located__Group_5__2__Impl ;
	public final void rule__Var_Decl_Located__Group_5__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4160:1: (
			// rule__Var_Decl_Located__Group_5__2__Impl )
			// InternalStructuredTextParser.g:4161:2:
			// rule__Var_Decl_Located__Group_5__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Var_Decl_Located__Group_5__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group_5__2"

	// $ANTLR start "rule__Var_Decl_Located__Group_5__2__Impl"
	// InternalStructuredTextParser.g:4167:1:
	// rule__Var_Decl_Located__Group_5__2__Impl : ( RightSquareBracket ) ;
	public final void rule__Var_Decl_Located__Group_5__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4171:1: ( ( RightSquareBracket ) )
			// InternalStructuredTextParser.g:4172:1: ( RightSquareBracket )
			{
				// InternalStructuredTextParser.g:4172:1: ( RightSquareBracket )
				// InternalStructuredTextParser.g:4173:2: RightSquareBracket
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getRightSquareBracketKeyword_5_2());
					match(input, RightSquareBracket, FOLLOW_2);
					after(grammarAccess.getVar_Decl_LocatedAccess().getRightSquareBracketKeyword_5_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__Group_5__2__Impl"

	// $ANTLR start "rule__Stmt_List__Group__0"
	// InternalStructuredTextParser.g:4183:1: rule__Stmt_List__Group__0 :
	// rule__Stmt_List__Group__0__Impl rule__Stmt_List__Group__1 ;
	public final void rule__Stmt_List__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4187:1: ( rule__Stmt_List__Group__0__Impl
			// rule__Stmt_List__Group__1 )
			// InternalStructuredTextParser.g:4188:2: rule__Stmt_List__Group__0__Impl
			// rule__Stmt_List__Group__1
			{
				pushFollow(FOLLOW_3);
				rule__Stmt_List__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Stmt_List__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Stmt_List__Group__0"

	// $ANTLR start "rule__Stmt_List__Group__0__Impl"
	// InternalStructuredTextParser.g:4195:1: rule__Stmt_List__Group__0__Impl : ( ()
	// ) ;
	public final void rule__Stmt_List__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4199:1: ( ( () ) )
			// InternalStructuredTextParser.g:4200:1: ( () )
			{
				// InternalStructuredTextParser.g:4200:1: ( () )
				// InternalStructuredTextParser.g:4201:2: ()
				{
					before(grammarAccess.getStmt_ListAccess().getStatementListAction_0());
					// InternalStructuredTextParser.g:4202:2: ()
					// InternalStructuredTextParser.g:4202:3:
					{
					}

					after(grammarAccess.getStmt_ListAccess().getStatementListAction_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Stmt_List__Group__0__Impl"

	// $ANTLR start "rule__Stmt_List__Group__1"
	// InternalStructuredTextParser.g:4210:1: rule__Stmt_List__Group__1 :
	// rule__Stmt_List__Group__1__Impl ;
	public final void rule__Stmt_List__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4214:1: ( rule__Stmt_List__Group__1__Impl )
			// InternalStructuredTextParser.g:4215:2: rule__Stmt_List__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Stmt_List__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Stmt_List__Group__1"

	// $ANTLR start "rule__Stmt_List__Group__1__Impl"
	// InternalStructuredTextParser.g:4221:1: rule__Stmt_List__Group__1__Impl : ( (
	// rule__Stmt_List__Group_1__0 )* ) ;
	public final void rule__Stmt_List__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4225:1: ( ( ( rule__Stmt_List__Group_1__0 )* )
			// )
			// InternalStructuredTextParser.g:4226:1: ( ( rule__Stmt_List__Group_1__0 )* )
			{
				// InternalStructuredTextParser.g:4226:1: ( ( rule__Stmt_List__Group_1__0 )* )
				// InternalStructuredTextParser.g:4227:2: ( rule__Stmt_List__Group_1__0 )*
				{
					before(grammarAccess.getStmt_ListAccess().getGroup_1());
					// InternalStructuredTextParser.g:4228:2: ( rule__Stmt_List__Group_1__0 )*
					loop43: do {
						int alt43 = 2;
						switch (input.LA(1)) {
						case TIME: {
							int LA43_2 = input.LA(2);

							if ((LA43_2 == LeftParenthesis)) {
								alt43 = 1;
							}

						}
							break;
						case T: {
							int LA43_3 = input.LA(2);

							if (((LA43_3 >= B && LA43_3 <= X) || LA43_3 == ColonEqualsSign || LA43_3 == FullStop
									|| LA43_3 == LeftSquareBracket)) {
								alt43 = 1;
							}

						}
							break;
						case LT: {
							int LA43_4 = input.LA(2);

							if (((LA43_4 >= B && LA43_4 <= X) || LA43_4 == ColonEqualsSign || LA43_4 == FullStop
									|| LA43_4 == LeftSquareBracket)) {
								alt43 = 1;
							}

						}
							break;
						case DT: {
							int LA43_5 = input.LA(2);

							if (((LA43_5 >= B && LA43_5 <= X) || LA43_5 == ColonEqualsSign || LA43_5 == FullStop
									|| LA43_5 == LeftSquareBracket)) {
								alt43 = 1;
							}

						}
							break;
						case CONTINUE:
						case REPEAT:
						case RETURN:
						case SUPER:
						case WHILE:
						case CASE:
						case EXIT:
						case FOR:
						case IF:
						case Semicolon:
						case RULE_ID: {
							alt43 = 1;
						}
							break;

						}

						switch (alt43) {
						case 1:
						// InternalStructuredTextParser.g:4228:3: rule__Stmt_List__Group_1__0
						{
							pushFollow(FOLLOW_17);
							rule__Stmt_List__Group_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop43;
						}
					} while (true);

					after(grammarAccess.getStmt_ListAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Stmt_List__Group__1__Impl"

	// $ANTLR start "rule__Stmt_List__Group_1__0"
	// InternalStructuredTextParser.g:4237:1: rule__Stmt_List__Group_1__0 :
	// rule__Stmt_List__Group_1__0__Impl rule__Stmt_List__Group_1__1 ;
	public final void rule__Stmt_List__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4241:1: ( rule__Stmt_List__Group_1__0__Impl
			// rule__Stmt_List__Group_1__1 )
			// InternalStructuredTextParser.g:4242:2: rule__Stmt_List__Group_1__0__Impl
			// rule__Stmt_List__Group_1__1
			{
				pushFollow(FOLLOW_18);
				rule__Stmt_List__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Stmt_List__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Stmt_List__Group_1__0"

	// $ANTLR start "rule__Stmt_List__Group_1__0__Impl"
	// InternalStructuredTextParser.g:4249:1: rule__Stmt_List__Group_1__0__Impl : (
	// ( rule__Stmt_List__StatementsAssignment_1_0 )? ) ;
	public final void rule__Stmt_List__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4253:1: ( ( (
			// rule__Stmt_List__StatementsAssignment_1_0 )? ) )
			// InternalStructuredTextParser.g:4254:1: ( (
			// rule__Stmt_List__StatementsAssignment_1_0 )? )
			{
				// InternalStructuredTextParser.g:4254:1: ( (
				// rule__Stmt_List__StatementsAssignment_1_0 )? )
				// InternalStructuredTextParser.g:4255:2: (
				// rule__Stmt_List__StatementsAssignment_1_0 )?
				{
					before(grammarAccess.getStmt_ListAccess().getStatementsAssignment_1_0());
					// InternalStructuredTextParser.g:4256:2: (
					// rule__Stmt_List__StatementsAssignment_1_0 )?
					int alt44 = 2;
					int LA44_0 = input.LA(1);

					if ((LA44_0 == CONTINUE || (LA44_0 >= REPEAT && LA44_0 <= RETURN) || LA44_0 == SUPER
							|| LA44_0 == WHILE || LA44_0 == CASE || LA44_0 == EXIT || LA44_0 == TIME || LA44_0 == FOR
							|| (LA44_0 >= DT && LA44_0 <= IF) || LA44_0 == LT || LA44_0 == T || LA44_0 == RULE_ID)) {
						alt44 = 1;
					}
					switch (alt44) {
					case 1:
					// InternalStructuredTextParser.g:4256:3:
					// rule__Stmt_List__StatementsAssignment_1_0
					{
						pushFollow(FOLLOW_2);
						rule__Stmt_List__StatementsAssignment_1_0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getStmt_ListAccess().getStatementsAssignment_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Stmt_List__Group_1__0__Impl"

	// $ANTLR start "rule__Stmt_List__Group_1__1"
	// InternalStructuredTextParser.g:4264:1: rule__Stmt_List__Group_1__1 :
	// rule__Stmt_List__Group_1__1__Impl ;
	public final void rule__Stmt_List__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4268:1: ( rule__Stmt_List__Group_1__1__Impl )
			// InternalStructuredTextParser.g:4269:2: rule__Stmt_List__Group_1__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Stmt_List__Group_1__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Stmt_List__Group_1__1"

	// $ANTLR start "rule__Stmt_List__Group_1__1__Impl"
	// InternalStructuredTextParser.g:4275:1: rule__Stmt_List__Group_1__1__Impl : (
	// Semicolon ) ;
	public final void rule__Stmt_List__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4279:1: ( ( Semicolon ) )
			// InternalStructuredTextParser.g:4280:1: ( Semicolon )
			{
				// InternalStructuredTextParser.g:4280:1: ( Semicolon )
				// InternalStructuredTextParser.g:4281:2: Semicolon
				{
					before(grammarAccess.getStmt_ListAccess().getSemicolonKeyword_1_1());
					match(input, Semicolon, FOLLOW_2);
					after(grammarAccess.getStmt_ListAccess().getSemicolonKeyword_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Stmt_List__Group_1__1__Impl"

	// $ANTLR start "rule__Assign_Stmt__Group__0"
	// InternalStructuredTextParser.g:4291:1: rule__Assign_Stmt__Group__0 :
	// rule__Assign_Stmt__Group__0__Impl rule__Assign_Stmt__Group__1 ;
	public final void rule__Assign_Stmt__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4295:1: ( rule__Assign_Stmt__Group__0__Impl
			// rule__Assign_Stmt__Group__1 )
			// InternalStructuredTextParser.g:4296:2: rule__Assign_Stmt__Group__0__Impl
			// rule__Assign_Stmt__Group__1
			{
				pushFollow(FOLLOW_19);
				rule__Assign_Stmt__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Assign_Stmt__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Assign_Stmt__Group__0"

	// $ANTLR start "rule__Assign_Stmt__Group__0__Impl"
	// InternalStructuredTextParser.g:4303:1: rule__Assign_Stmt__Group__0__Impl : (
	// ( rule__Assign_Stmt__VariableAssignment_0 ) ) ;
	public final void rule__Assign_Stmt__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4307:1: ( ( (
			// rule__Assign_Stmt__VariableAssignment_0 ) ) )
			// InternalStructuredTextParser.g:4308:1: ( (
			// rule__Assign_Stmt__VariableAssignment_0 ) )
			{
				// InternalStructuredTextParser.g:4308:1: ( (
				// rule__Assign_Stmt__VariableAssignment_0 ) )
				// InternalStructuredTextParser.g:4309:2: (
				// rule__Assign_Stmt__VariableAssignment_0 )
				{
					before(grammarAccess.getAssign_StmtAccess().getVariableAssignment_0());
					// InternalStructuredTextParser.g:4310:2: (
					// rule__Assign_Stmt__VariableAssignment_0 )
					// InternalStructuredTextParser.g:4310:3:
					// rule__Assign_Stmt__VariableAssignment_0
					{
						pushFollow(FOLLOW_2);
						rule__Assign_Stmt__VariableAssignment_0();

						state._fsp--;

					}

					after(grammarAccess.getAssign_StmtAccess().getVariableAssignment_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Assign_Stmt__Group__0__Impl"

	// $ANTLR start "rule__Assign_Stmt__Group__1"
	// InternalStructuredTextParser.g:4318:1: rule__Assign_Stmt__Group__1 :
	// rule__Assign_Stmt__Group__1__Impl rule__Assign_Stmt__Group__2 ;
	public final void rule__Assign_Stmt__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4322:1: ( rule__Assign_Stmt__Group__1__Impl
			// rule__Assign_Stmt__Group__2 )
			// InternalStructuredTextParser.g:4323:2: rule__Assign_Stmt__Group__1__Impl
			// rule__Assign_Stmt__Group__2
			{
				pushFollow(FOLLOW_20);
				rule__Assign_Stmt__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Assign_Stmt__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Assign_Stmt__Group__1"

	// $ANTLR start "rule__Assign_Stmt__Group__1__Impl"
	// InternalStructuredTextParser.g:4330:1: rule__Assign_Stmt__Group__1__Impl : (
	// ColonEqualsSign ) ;
	public final void rule__Assign_Stmt__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4334:1: ( ( ColonEqualsSign ) )
			// InternalStructuredTextParser.g:4335:1: ( ColonEqualsSign )
			{
				// InternalStructuredTextParser.g:4335:1: ( ColonEqualsSign )
				// InternalStructuredTextParser.g:4336:2: ColonEqualsSign
				{
					before(grammarAccess.getAssign_StmtAccess().getColonEqualsSignKeyword_1());
					match(input, ColonEqualsSign, FOLLOW_2);
					after(grammarAccess.getAssign_StmtAccess().getColonEqualsSignKeyword_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Assign_Stmt__Group__1__Impl"

	// $ANTLR start "rule__Assign_Stmt__Group__2"
	// InternalStructuredTextParser.g:4345:1: rule__Assign_Stmt__Group__2 :
	// rule__Assign_Stmt__Group__2__Impl ;
	public final void rule__Assign_Stmt__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4349:1: ( rule__Assign_Stmt__Group__2__Impl )
			// InternalStructuredTextParser.g:4350:2: rule__Assign_Stmt__Group__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Assign_Stmt__Group__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Assign_Stmt__Group__2"

	// $ANTLR start "rule__Assign_Stmt__Group__2__Impl"
	// InternalStructuredTextParser.g:4356:1: rule__Assign_Stmt__Group__2__Impl : (
	// ( rule__Assign_Stmt__ExpressionAssignment_2 ) ) ;
	public final void rule__Assign_Stmt__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4360:1: ( ( (
			// rule__Assign_Stmt__ExpressionAssignment_2 ) ) )
			// InternalStructuredTextParser.g:4361:1: ( (
			// rule__Assign_Stmt__ExpressionAssignment_2 ) )
			{
				// InternalStructuredTextParser.g:4361:1: ( (
				// rule__Assign_Stmt__ExpressionAssignment_2 ) )
				// InternalStructuredTextParser.g:4362:2: (
				// rule__Assign_Stmt__ExpressionAssignment_2 )
				{
					before(grammarAccess.getAssign_StmtAccess().getExpressionAssignment_2());
					// InternalStructuredTextParser.g:4363:2: (
					// rule__Assign_Stmt__ExpressionAssignment_2 )
					// InternalStructuredTextParser.g:4363:3:
					// rule__Assign_Stmt__ExpressionAssignment_2
					{
						pushFollow(FOLLOW_2);
						rule__Assign_Stmt__ExpressionAssignment_2();

						state._fsp--;

					}

					after(grammarAccess.getAssign_StmtAccess().getExpressionAssignment_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Assign_Stmt__Group__2__Impl"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__0"
	// InternalStructuredTextParser.g:4372:1: rule__Subprog_Ctrl_Stmt__Group_1__0 :
	// rule__Subprog_Ctrl_Stmt__Group_1__0__Impl rule__Subprog_Ctrl_Stmt__Group_1__1
	// ;
	public final void rule__Subprog_Ctrl_Stmt__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4376:1: (
			// rule__Subprog_Ctrl_Stmt__Group_1__0__Impl rule__Subprog_Ctrl_Stmt__Group_1__1
			// )
			// InternalStructuredTextParser.g:4377:2:
			// rule__Subprog_Ctrl_Stmt__Group_1__0__Impl rule__Subprog_Ctrl_Stmt__Group_1__1
			{
				pushFollow(FOLLOW_21);
				rule__Subprog_Ctrl_Stmt__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Subprog_Ctrl_Stmt__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__0"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__0__Impl"
	// InternalStructuredTextParser.g:4384:1:
	// rule__Subprog_Ctrl_Stmt__Group_1__0__Impl : ( () ) ;
	public final void rule__Subprog_Ctrl_Stmt__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4388:1: ( ( () ) )
			// InternalStructuredTextParser.g:4389:1: ( () )
			{
				// InternalStructuredTextParser.g:4389:1: ( () )
				// InternalStructuredTextParser.g:4390:2: ()
				{
					before(grammarAccess.getSubprog_Ctrl_StmtAccess().getSuperStatementAction_1_0());
					// InternalStructuredTextParser.g:4391:2: ()
					// InternalStructuredTextParser.g:4391:3:
					{
					}

					after(grammarAccess.getSubprog_Ctrl_StmtAccess().getSuperStatementAction_1_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__0__Impl"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__1"
	// InternalStructuredTextParser.g:4399:1: rule__Subprog_Ctrl_Stmt__Group_1__1 :
	// rule__Subprog_Ctrl_Stmt__Group_1__1__Impl rule__Subprog_Ctrl_Stmt__Group_1__2
	// ;
	public final void rule__Subprog_Ctrl_Stmt__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4403:1: (
			// rule__Subprog_Ctrl_Stmt__Group_1__1__Impl rule__Subprog_Ctrl_Stmt__Group_1__2
			// )
			// InternalStructuredTextParser.g:4404:2:
			// rule__Subprog_Ctrl_Stmt__Group_1__1__Impl rule__Subprog_Ctrl_Stmt__Group_1__2
			{
				pushFollow(FOLLOW_22);
				rule__Subprog_Ctrl_Stmt__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Subprog_Ctrl_Stmt__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__1"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__1__Impl"
	// InternalStructuredTextParser.g:4411:1:
	// rule__Subprog_Ctrl_Stmt__Group_1__1__Impl : ( SUPER ) ;
	public final void rule__Subprog_Ctrl_Stmt__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4415:1: ( ( SUPER ) )
			// InternalStructuredTextParser.g:4416:1: ( SUPER )
			{
				// InternalStructuredTextParser.g:4416:1: ( SUPER )
				// InternalStructuredTextParser.g:4417:2: SUPER
				{
					before(grammarAccess.getSubprog_Ctrl_StmtAccess().getSUPERKeyword_1_1());
					match(input, SUPER, FOLLOW_2);
					after(grammarAccess.getSubprog_Ctrl_StmtAccess().getSUPERKeyword_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__1__Impl"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__2"
	// InternalStructuredTextParser.g:4426:1: rule__Subprog_Ctrl_Stmt__Group_1__2 :
	// rule__Subprog_Ctrl_Stmt__Group_1__2__Impl rule__Subprog_Ctrl_Stmt__Group_1__3
	// ;
	public final void rule__Subprog_Ctrl_Stmt__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4430:1: (
			// rule__Subprog_Ctrl_Stmt__Group_1__2__Impl rule__Subprog_Ctrl_Stmt__Group_1__3
			// )
			// InternalStructuredTextParser.g:4431:2:
			// rule__Subprog_Ctrl_Stmt__Group_1__2__Impl rule__Subprog_Ctrl_Stmt__Group_1__3
			{
				pushFollow(FOLLOW_23);
				rule__Subprog_Ctrl_Stmt__Group_1__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Subprog_Ctrl_Stmt__Group_1__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__2"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__2__Impl"
	// InternalStructuredTextParser.g:4438:1:
	// rule__Subprog_Ctrl_Stmt__Group_1__2__Impl : ( LeftParenthesis ) ;
	public final void rule__Subprog_Ctrl_Stmt__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4442:1: ( ( LeftParenthesis ) )
			// InternalStructuredTextParser.g:4443:1: ( LeftParenthesis )
			{
				// InternalStructuredTextParser.g:4443:1: ( LeftParenthesis )
				// InternalStructuredTextParser.g:4444:2: LeftParenthesis
				{
					before(grammarAccess.getSubprog_Ctrl_StmtAccess().getLeftParenthesisKeyword_1_2());
					match(input, LeftParenthesis, FOLLOW_2);
					after(grammarAccess.getSubprog_Ctrl_StmtAccess().getLeftParenthesisKeyword_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__2__Impl"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__3"
	// InternalStructuredTextParser.g:4453:1: rule__Subprog_Ctrl_Stmt__Group_1__3 :
	// rule__Subprog_Ctrl_Stmt__Group_1__3__Impl ;
	public final void rule__Subprog_Ctrl_Stmt__Group_1__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4457:1: (
			// rule__Subprog_Ctrl_Stmt__Group_1__3__Impl )
			// InternalStructuredTextParser.g:4458:2:
			// rule__Subprog_Ctrl_Stmt__Group_1__3__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Subprog_Ctrl_Stmt__Group_1__3__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__3"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__3__Impl"
	// InternalStructuredTextParser.g:4464:1:
	// rule__Subprog_Ctrl_Stmt__Group_1__3__Impl : ( RightParenthesis ) ;
	public final void rule__Subprog_Ctrl_Stmt__Group_1__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4468:1: ( ( RightParenthesis ) )
			// InternalStructuredTextParser.g:4469:1: ( RightParenthesis )
			{
				// InternalStructuredTextParser.g:4469:1: ( RightParenthesis )
				// InternalStructuredTextParser.g:4470:2: RightParenthesis
				{
					before(grammarAccess.getSubprog_Ctrl_StmtAccess().getRightParenthesisKeyword_1_3());
					match(input, RightParenthesis, FOLLOW_2);
					after(grammarAccess.getSubprog_Ctrl_StmtAccess().getRightParenthesisKeyword_1_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__3__Impl"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__0"
	// InternalStructuredTextParser.g:4480:1: rule__Subprog_Ctrl_Stmt__Group_2__0 :
	// rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1
	// ;
	public final void rule__Subprog_Ctrl_Stmt__Group_2__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4484:1: (
			// rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1
			// )
			// InternalStructuredTextParser.g:4485:2:
			// rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1
			{
				pushFollow(FOLLOW_24);
				rule__Subprog_Ctrl_Stmt__Group_2__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Subprog_Ctrl_Stmt__Group_2__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__0"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__0__Impl"
	// InternalStructuredTextParser.g:4492:1:
	// rule__Subprog_Ctrl_Stmt__Group_2__0__Impl : ( () ) ;
	public final void rule__Subprog_Ctrl_Stmt__Group_2__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4496:1: ( ( () ) )
			// InternalStructuredTextParser.g:4497:1: ( () )
			{
				// InternalStructuredTextParser.g:4497:1: ( () )
				// InternalStructuredTextParser.g:4498:2: ()
				{
					before(grammarAccess.getSubprog_Ctrl_StmtAccess().getReturnStatementAction_2_0());
					// InternalStructuredTextParser.g:4499:2: ()
					// InternalStructuredTextParser.g:4499:3:
					{
					}

					after(grammarAccess.getSubprog_Ctrl_StmtAccess().getReturnStatementAction_2_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__0__Impl"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__1"
	// InternalStructuredTextParser.g:4507:1: rule__Subprog_Ctrl_Stmt__Group_2__1 :
	// rule__Subprog_Ctrl_Stmt__Group_2__1__Impl ;
	public final void rule__Subprog_Ctrl_Stmt__Group_2__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4511:1: (
			// rule__Subprog_Ctrl_Stmt__Group_2__1__Impl )
			// InternalStructuredTextParser.g:4512:2:
			// rule__Subprog_Ctrl_Stmt__Group_2__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Subprog_Ctrl_Stmt__Group_2__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__1"

	// $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__1__Impl"
	// InternalStructuredTextParser.g:4518:1:
	// rule__Subprog_Ctrl_Stmt__Group_2__1__Impl : ( RETURN ) ;
	public final void rule__Subprog_Ctrl_Stmt__Group_2__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4522:1: ( ( RETURN ) )
			// InternalStructuredTextParser.g:4523:1: ( RETURN )
			{
				// InternalStructuredTextParser.g:4523:1: ( RETURN )
				// InternalStructuredTextParser.g:4524:2: RETURN
				{
					before(grammarAccess.getSubprog_Ctrl_StmtAccess().getRETURNKeyword_2_1());
					match(input, RETURN, FOLLOW_2);
					after(grammarAccess.getSubprog_Ctrl_StmtAccess().getRETURNKeyword_2_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__1__Impl"

	// $ANTLR start "rule__IF_Stmt__Group__0"
	// InternalStructuredTextParser.g:4534:1: rule__IF_Stmt__Group__0 :
	// rule__IF_Stmt__Group__0__Impl rule__IF_Stmt__Group__1 ;
	public final void rule__IF_Stmt__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4538:1: ( rule__IF_Stmt__Group__0__Impl
			// rule__IF_Stmt__Group__1 )
			// InternalStructuredTextParser.g:4539:2: rule__IF_Stmt__Group__0__Impl
			// rule__IF_Stmt__Group__1
			{
				pushFollow(FOLLOW_20);
				rule__IF_Stmt__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__IF_Stmt__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__0"

	// $ANTLR start "rule__IF_Stmt__Group__0__Impl"
	// InternalStructuredTextParser.g:4546:1: rule__IF_Stmt__Group__0__Impl : ( IF )
	// ;
	public final void rule__IF_Stmt__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4550:1: ( ( IF ) )
			// InternalStructuredTextParser.g:4551:1: ( IF )
			{
				// InternalStructuredTextParser.g:4551:1: ( IF )
				// InternalStructuredTextParser.g:4552:2: IF
				{
					before(grammarAccess.getIF_StmtAccess().getIFKeyword_0());
					match(input, IF, FOLLOW_2);
					after(grammarAccess.getIF_StmtAccess().getIFKeyword_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__0__Impl"

	// $ANTLR start "rule__IF_Stmt__Group__1"
	// InternalStructuredTextParser.g:4561:1: rule__IF_Stmt__Group__1 :
	// rule__IF_Stmt__Group__1__Impl rule__IF_Stmt__Group__2 ;
	public final void rule__IF_Stmt__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4565:1: ( rule__IF_Stmt__Group__1__Impl
			// rule__IF_Stmt__Group__2 )
			// InternalStructuredTextParser.g:4566:2: rule__IF_Stmt__Group__1__Impl
			// rule__IF_Stmt__Group__2
			{
				pushFollow(FOLLOW_25);
				rule__IF_Stmt__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__IF_Stmt__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__1"

	// $ANTLR start "rule__IF_Stmt__Group__1__Impl"
	// InternalStructuredTextParser.g:4573:1: rule__IF_Stmt__Group__1__Impl : ( (
	// rule__IF_Stmt__ExpressionAssignment_1 ) ) ;
	public final void rule__IF_Stmt__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4577:1: ( ( (
			// rule__IF_Stmt__ExpressionAssignment_1 ) ) )
			// InternalStructuredTextParser.g:4578:1: ( (
			// rule__IF_Stmt__ExpressionAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:4578:1: ( (
				// rule__IF_Stmt__ExpressionAssignment_1 ) )
				// InternalStructuredTextParser.g:4579:2: (
				// rule__IF_Stmt__ExpressionAssignment_1 )
				{
					before(grammarAccess.getIF_StmtAccess().getExpressionAssignment_1());
					// InternalStructuredTextParser.g:4580:2: (
					// rule__IF_Stmt__ExpressionAssignment_1 )
					// InternalStructuredTextParser.g:4580:3: rule__IF_Stmt__ExpressionAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__IF_Stmt__ExpressionAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getIF_StmtAccess().getExpressionAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__1__Impl"

	// $ANTLR start "rule__IF_Stmt__Group__2"
	// InternalStructuredTextParser.g:4588:1: rule__IF_Stmt__Group__2 :
	// rule__IF_Stmt__Group__2__Impl rule__IF_Stmt__Group__3 ;
	public final void rule__IF_Stmt__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4592:1: ( rule__IF_Stmt__Group__2__Impl
			// rule__IF_Stmt__Group__3 )
			// InternalStructuredTextParser.g:4593:2: rule__IF_Stmt__Group__2__Impl
			// rule__IF_Stmt__Group__3
			{
				pushFollow(FOLLOW_3);
				rule__IF_Stmt__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__IF_Stmt__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__2"

	// $ANTLR start "rule__IF_Stmt__Group__2__Impl"
	// InternalStructuredTextParser.g:4600:1: rule__IF_Stmt__Group__2__Impl : ( THEN
	// ) ;
	public final void rule__IF_Stmt__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4604:1: ( ( THEN ) )
			// InternalStructuredTextParser.g:4605:1: ( THEN )
			{
				// InternalStructuredTextParser.g:4605:1: ( THEN )
				// InternalStructuredTextParser.g:4606:2: THEN
				{
					before(grammarAccess.getIF_StmtAccess().getTHENKeyword_2());
					match(input, THEN, FOLLOW_2);
					after(grammarAccess.getIF_StmtAccess().getTHENKeyword_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__2__Impl"

	// $ANTLR start "rule__IF_Stmt__Group__3"
	// InternalStructuredTextParser.g:4615:1: rule__IF_Stmt__Group__3 :
	// rule__IF_Stmt__Group__3__Impl rule__IF_Stmt__Group__4 ;
	public final void rule__IF_Stmt__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4619:1: ( rule__IF_Stmt__Group__3__Impl
			// rule__IF_Stmt__Group__4 )
			// InternalStructuredTextParser.g:4620:2: rule__IF_Stmt__Group__3__Impl
			// rule__IF_Stmt__Group__4
			{
				pushFollow(FOLLOW_26);
				rule__IF_Stmt__Group__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__IF_Stmt__Group__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__3"

	// $ANTLR start "rule__IF_Stmt__Group__3__Impl"
	// InternalStructuredTextParser.g:4627:1: rule__IF_Stmt__Group__3__Impl : ( (
	// rule__IF_Stmt__StatmentsAssignment_3 ) ) ;
	public final void rule__IF_Stmt__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4631:1: ( ( (
			// rule__IF_Stmt__StatmentsAssignment_3 ) ) )
			// InternalStructuredTextParser.g:4632:1: ( (
			// rule__IF_Stmt__StatmentsAssignment_3 ) )
			{
				// InternalStructuredTextParser.g:4632:1: ( (
				// rule__IF_Stmt__StatmentsAssignment_3 ) )
				// InternalStructuredTextParser.g:4633:2: ( rule__IF_Stmt__StatmentsAssignment_3
				// )
				{
					before(grammarAccess.getIF_StmtAccess().getStatmentsAssignment_3());
					// InternalStructuredTextParser.g:4634:2: ( rule__IF_Stmt__StatmentsAssignment_3
					// )
					// InternalStructuredTextParser.g:4634:3: rule__IF_Stmt__StatmentsAssignment_3
					{
						pushFollow(FOLLOW_2);
						rule__IF_Stmt__StatmentsAssignment_3();

						state._fsp--;

					}

					after(grammarAccess.getIF_StmtAccess().getStatmentsAssignment_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__3__Impl"

	// $ANTLR start "rule__IF_Stmt__Group__4"
	// InternalStructuredTextParser.g:4642:1: rule__IF_Stmt__Group__4 :
	// rule__IF_Stmt__Group__4__Impl rule__IF_Stmt__Group__5 ;
	public final void rule__IF_Stmt__Group__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4646:1: ( rule__IF_Stmt__Group__4__Impl
			// rule__IF_Stmt__Group__5 )
			// InternalStructuredTextParser.g:4647:2: rule__IF_Stmt__Group__4__Impl
			// rule__IF_Stmt__Group__5
			{
				pushFollow(FOLLOW_26);
				rule__IF_Stmt__Group__4__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__IF_Stmt__Group__5();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__4"

	// $ANTLR start "rule__IF_Stmt__Group__4__Impl"
	// InternalStructuredTextParser.g:4654:1: rule__IF_Stmt__Group__4__Impl : ( (
	// rule__IF_Stmt__ElseifAssignment_4 )* ) ;
	public final void rule__IF_Stmt__Group__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4658:1: ( ( (
			// rule__IF_Stmt__ElseifAssignment_4 )* ) )
			// InternalStructuredTextParser.g:4659:1: ( ( rule__IF_Stmt__ElseifAssignment_4
			// )* )
			{
				// InternalStructuredTextParser.g:4659:1: ( ( rule__IF_Stmt__ElseifAssignment_4
				// )* )
				// InternalStructuredTextParser.g:4660:2: ( rule__IF_Stmt__ElseifAssignment_4 )*
				{
					before(grammarAccess.getIF_StmtAccess().getElseifAssignment_4());
					// InternalStructuredTextParser.g:4661:2: ( rule__IF_Stmt__ElseifAssignment_4 )*
					loop45: do {
						int alt45 = 2;
						int LA45_0 = input.LA(1);

						if ((LA45_0 == ELSIF)) {
							alt45 = 1;
						}

						switch (alt45) {
						case 1:
						// InternalStructuredTextParser.g:4661:3: rule__IF_Stmt__ElseifAssignment_4
						{
							pushFollow(FOLLOW_27);
							rule__IF_Stmt__ElseifAssignment_4();

							state._fsp--;

						}
							break;

						default:
							break loop45;
						}
					} while (true);

					after(grammarAccess.getIF_StmtAccess().getElseifAssignment_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__4__Impl"

	// $ANTLR start "rule__IF_Stmt__Group__5"
	// InternalStructuredTextParser.g:4669:1: rule__IF_Stmt__Group__5 :
	// rule__IF_Stmt__Group__5__Impl rule__IF_Stmt__Group__6 ;
	public final void rule__IF_Stmt__Group__5() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4673:1: ( rule__IF_Stmt__Group__5__Impl
			// rule__IF_Stmt__Group__6 )
			// InternalStructuredTextParser.g:4674:2: rule__IF_Stmt__Group__5__Impl
			// rule__IF_Stmt__Group__6
			{
				pushFollow(FOLLOW_26);
				rule__IF_Stmt__Group__5__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__IF_Stmt__Group__6();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__5"

	// $ANTLR start "rule__IF_Stmt__Group__5__Impl"
	// InternalStructuredTextParser.g:4681:1: rule__IF_Stmt__Group__5__Impl : ( (
	// rule__IF_Stmt__ElseAssignment_5 )? ) ;
	public final void rule__IF_Stmt__Group__5__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4685:1: ( ( ( rule__IF_Stmt__ElseAssignment_5
			// )? ) )
			// InternalStructuredTextParser.g:4686:1: ( ( rule__IF_Stmt__ElseAssignment_5 )?
			// )
			{
				// InternalStructuredTextParser.g:4686:1: ( ( rule__IF_Stmt__ElseAssignment_5 )?
				// )
				// InternalStructuredTextParser.g:4687:2: ( rule__IF_Stmt__ElseAssignment_5 )?
				{
					before(grammarAccess.getIF_StmtAccess().getElseAssignment_5());
					// InternalStructuredTextParser.g:4688:2: ( rule__IF_Stmt__ElseAssignment_5 )?
					int alt46 = 2;
					int LA46_0 = input.LA(1);

					if ((LA46_0 == ELSE)) {
						alt46 = 1;
					}
					switch (alt46) {
					case 1:
					// InternalStructuredTextParser.g:4688:3: rule__IF_Stmt__ElseAssignment_5
					{
						pushFollow(FOLLOW_2);
						rule__IF_Stmt__ElseAssignment_5();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getIF_StmtAccess().getElseAssignment_5());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__5__Impl"

	// $ANTLR start "rule__IF_Stmt__Group__6"
	// InternalStructuredTextParser.g:4696:1: rule__IF_Stmt__Group__6 :
	// rule__IF_Stmt__Group__6__Impl ;
	public final void rule__IF_Stmt__Group__6() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4700:1: ( rule__IF_Stmt__Group__6__Impl )
			// InternalStructuredTextParser.g:4701:2: rule__IF_Stmt__Group__6__Impl
			{
				pushFollow(FOLLOW_2);
				rule__IF_Stmt__Group__6__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__6"

	// $ANTLR start "rule__IF_Stmt__Group__6__Impl"
	// InternalStructuredTextParser.g:4707:1: rule__IF_Stmt__Group__6__Impl : (
	// END_IF ) ;
	public final void rule__IF_Stmt__Group__6__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4711:1: ( ( END_IF ) )
			// InternalStructuredTextParser.g:4712:1: ( END_IF )
			{
				// InternalStructuredTextParser.g:4712:1: ( END_IF )
				// InternalStructuredTextParser.g:4713:2: END_IF
				{
					before(grammarAccess.getIF_StmtAccess().getEND_IFKeyword_6());
					match(input, END_IF, FOLLOW_2);
					after(grammarAccess.getIF_StmtAccess().getEND_IFKeyword_6());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__Group__6__Impl"

	// $ANTLR start "rule__ELSIF_Clause__Group__0"
	// InternalStructuredTextParser.g:4723:1: rule__ELSIF_Clause__Group__0 :
	// rule__ELSIF_Clause__Group__0__Impl rule__ELSIF_Clause__Group__1 ;
	public final void rule__ELSIF_Clause__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4727:1: ( rule__ELSIF_Clause__Group__0__Impl
			// rule__ELSIF_Clause__Group__1 )
			// InternalStructuredTextParser.g:4728:2: rule__ELSIF_Clause__Group__0__Impl
			// rule__ELSIF_Clause__Group__1
			{
				pushFollow(FOLLOW_20);
				rule__ELSIF_Clause__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__ELSIF_Clause__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSIF_Clause__Group__0"

	// $ANTLR start "rule__ELSIF_Clause__Group__0__Impl"
	// InternalStructuredTextParser.g:4735:1: rule__ELSIF_Clause__Group__0__Impl : (
	// ELSIF ) ;
	public final void rule__ELSIF_Clause__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4739:1: ( ( ELSIF ) )
			// InternalStructuredTextParser.g:4740:1: ( ELSIF )
			{
				// InternalStructuredTextParser.g:4740:1: ( ELSIF )
				// InternalStructuredTextParser.g:4741:2: ELSIF
				{
					before(grammarAccess.getELSIF_ClauseAccess().getELSIFKeyword_0());
					match(input, ELSIF, FOLLOW_2);
					after(grammarAccess.getELSIF_ClauseAccess().getELSIFKeyword_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSIF_Clause__Group__0__Impl"

	// $ANTLR start "rule__ELSIF_Clause__Group__1"
	// InternalStructuredTextParser.g:4750:1: rule__ELSIF_Clause__Group__1 :
	// rule__ELSIF_Clause__Group__1__Impl rule__ELSIF_Clause__Group__2 ;
	public final void rule__ELSIF_Clause__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4754:1: ( rule__ELSIF_Clause__Group__1__Impl
			// rule__ELSIF_Clause__Group__2 )
			// InternalStructuredTextParser.g:4755:2: rule__ELSIF_Clause__Group__1__Impl
			// rule__ELSIF_Clause__Group__2
			{
				pushFollow(FOLLOW_25);
				rule__ELSIF_Clause__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__ELSIF_Clause__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSIF_Clause__Group__1"

	// $ANTLR start "rule__ELSIF_Clause__Group__1__Impl"
	// InternalStructuredTextParser.g:4762:1: rule__ELSIF_Clause__Group__1__Impl : (
	// ( rule__ELSIF_Clause__ExpressionAssignment_1 ) ) ;
	public final void rule__ELSIF_Clause__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4766:1: ( ( (
			// rule__ELSIF_Clause__ExpressionAssignment_1 ) ) )
			// InternalStructuredTextParser.g:4767:1: ( (
			// rule__ELSIF_Clause__ExpressionAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:4767:1: ( (
				// rule__ELSIF_Clause__ExpressionAssignment_1 ) )
				// InternalStructuredTextParser.g:4768:2: (
				// rule__ELSIF_Clause__ExpressionAssignment_1 )
				{
					before(grammarAccess.getELSIF_ClauseAccess().getExpressionAssignment_1());
					// InternalStructuredTextParser.g:4769:2: (
					// rule__ELSIF_Clause__ExpressionAssignment_1 )
					// InternalStructuredTextParser.g:4769:3:
					// rule__ELSIF_Clause__ExpressionAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__ELSIF_Clause__ExpressionAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getELSIF_ClauseAccess().getExpressionAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSIF_Clause__Group__1__Impl"

	// $ANTLR start "rule__ELSIF_Clause__Group__2"
	// InternalStructuredTextParser.g:4777:1: rule__ELSIF_Clause__Group__2 :
	// rule__ELSIF_Clause__Group__2__Impl rule__ELSIF_Clause__Group__3 ;
	public final void rule__ELSIF_Clause__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4781:1: ( rule__ELSIF_Clause__Group__2__Impl
			// rule__ELSIF_Clause__Group__3 )
			// InternalStructuredTextParser.g:4782:2: rule__ELSIF_Clause__Group__2__Impl
			// rule__ELSIF_Clause__Group__3
			{
				pushFollow(FOLLOW_3);
				rule__ELSIF_Clause__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__ELSIF_Clause__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSIF_Clause__Group__2"

	// $ANTLR start "rule__ELSIF_Clause__Group__2__Impl"
	// InternalStructuredTextParser.g:4789:1: rule__ELSIF_Clause__Group__2__Impl : (
	// THEN ) ;
	public final void rule__ELSIF_Clause__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4793:1: ( ( THEN ) )
			// InternalStructuredTextParser.g:4794:1: ( THEN )
			{
				// InternalStructuredTextParser.g:4794:1: ( THEN )
				// InternalStructuredTextParser.g:4795:2: THEN
				{
					before(grammarAccess.getELSIF_ClauseAccess().getTHENKeyword_2());
					match(input, THEN, FOLLOW_2);
					after(grammarAccess.getELSIF_ClauseAccess().getTHENKeyword_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSIF_Clause__Group__2__Impl"

	// $ANTLR start "rule__ELSIF_Clause__Group__3"
	// InternalStructuredTextParser.g:4804:1: rule__ELSIF_Clause__Group__3 :
	// rule__ELSIF_Clause__Group__3__Impl ;
	public final void rule__ELSIF_Clause__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4808:1: ( rule__ELSIF_Clause__Group__3__Impl )
			// InternalStructuredTextParser.g:4809:2: rule__ELSIF_Clause__Group__3__Impl
			{
				pushFollow(FOLLOW_2);
				rule__ELSIF_Clause__Group__3__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSIF_Clause__Group__3"

	// $ANTLR start "rule__ELSIF_Clause__Group__3__Impl"
	// InternalStructuredTextParser.g:4815:1: rule__ELSIF_Clause__Group__3__Impl : (
	// ( rule__ELSIF_Clause__StatementsAssignment_3 ) ) ;
	public final void rule__ELSIF_Clause__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4819:1: ( ( (
			// rule__ELSIF_Clause__StatementsAssignment_3 ) ) )
			// InternalStructuredTextParser.g:4820:1: ( (
			// rule__ELSIF_Clause__StatementsAssignment_3 ) )
			{
				// InternalStructuredTextParser.g:4820:1: ( (
				// rule__ELSIF_Clause__StatementsAssignment_3 ) )
				// InternalStructuredTextParser.g:4821:2: (
				// rule__ELSIF_Clause__StatementsAssignment_3 )
				{
					before(grammarAccess.getELSIF_ClauseAccess().getStatementsAssignment_3());
					// InternalStructuredTextParser.g:4822:2: (
					// rule__ELSIF_Clause__StatementsAssignment_3 )
					// InternalStructuredTextParser.g:4822:3:
					// rule__ELSIF_Clause__StatementsAssignment_3
					{
						pushFollow(FOLLOW_2);
						rule__ELSIF_Clause__StatementsAssignment_3();

						state._fsp--;

					}

					after(grammarAccess.getELSIF_ClauseAccess().getStatementsAssignment_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSIF_Clause__Group__3__Impl"

	// $ANTLR start "rule__ELSE_Clause__Group__0"
	// InternalStructuredTextParser.g:4831:1: rule__ELSE_Clause__Group__0 :
	// rule__ELSE_Clause__Group__0__Impl rule__ELSE_Clause__Group__1 ;
	public final void rule__ELSE_Clause__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4835:1: ( rule__ELSE_Clause__Group__0__Impl
			// rule__ELSE_Clause__Group__1 )
			// InternalStructuredTextParser.g:4836:2: rule__ELSE_Clause__Group__0__Impl
			// rule__ELSE_Clause__Group__1
			{
				pushFollow(FOLLOW_3);
				rule__ELSE_Clause__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__ELSE_Clause__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSE_Clause__Group__0"

	// $ANTLR start "rule__ELSE_Clause__Group__0__Impl"
	// InternalStructuredTextParser.g:4843:1: rule__ELSE_Clause__Group__0__Impl : (
	// ELSE ) ;
	public final void rule__ELSE_Clause__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4847:1: ( ( ELSE ) )
			// InternalStructuredTextParser.g:4848:1: ( ELSE )
			{
				// InternalStructuredTextParser.g:4848:1: ( ELSE )
				// InternalStructuredTextParser.g:4849:2: ELSE
				{
					before(grammarAccess.getELSE_ClauseAccess().getELSEKeyword_0());
					match(input, ELSE, FOLLOW_2);
					after(grammarAccess.getELSE_ClauseAccess().getELSEKeyword_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSE_Clause__Group__0__Impl"

	// $ANTLR start "rule__ELSE_Clause__Group__1"
	// InternalStructuredTextParser.g:4858:1: rule__ELSE_Clause__Group__1 :
	// rule__ELSE_Clause__Group__1__Impl ;
	public final void rule__ELSE_Clause__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4862:1: ( rule__ELSE_Clause__Group__1__Impl )
			// InternalStructuredTextParser.g:4863:2: rule__ELSE_Clause__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__ELSE_Clause__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSE_Clause__Group__1"

	// $ANTLR start "rule__ELSE_Clause__Group__1__Impl"
	// InternalStructuredTextParser.g:4869:1: rule__ELSE_Clause__Group__1__Impl : (
	// ( rule__ELSE_Clause__StatementsAssignment_1 ) ) ;
	public final void rule__ELSE_Clause__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4873:1: ( ( (
			// rule__ELSE_Clause__StatementsAssignment_1 ) ) )
			// InternalStructuredTextParser.g:4874:1: ( (
			// rule__ELSE_Clause__StatementsAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:4874:1: ( (
				// rule__ELSE_Clause__StatementsAssignment_1 ) )
				// InternalStructuredTextParser.g:4875:2: (
				// rule__ELSE_Clause__StatementsAssignment_1 )
				{
					before(grammarAccess.getELSE_ClauseAccess().getStatementsAssignment_1());
					// InternalStructuredTextParser.g:4876:2: (
					// rule__ELSE_Clause__StatementsAssignment_1 )
					// InternalStructuredTextParser.g:4876:3:
					// rule__ELSE_Clause__StatementsAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__ELSE_Clause__StatementsAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getELSE_ClauseAccess().getStatementsAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSE_Clause__Group__1__Impl"

	// $ANTLR start "rule__Case_Stmt__Group__0"
	// InternalStructuredTextParser.g:4885:1: rule__Case_Stmt__Group__0 :
	// rule__Case_Stmt__Group__0__Impl rule__Case_Stmt__Group__1 ;
	public final void rule__Case_Stmt__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4889:1: ( rule__Case_Stmt__Group__0__Impl
			// rule__Case_Stmt__Group__1 )
			// InternalStructuredTextParser.g:4890:2: rule__Case_Stmt__Group__0__Impl
			// rule__Case_Stmt__Group__1
			{
				pushFollow(FOLLOW_20);
				rule__Case_Stmt__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Case_Stmt__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__0"

	// $ANTLR start "rule__Case_Stmt__Group__0__Impl"
	// InternalStructuredTextParser.g:4897:1: rule__Case_Stmt__Group__0__Impl : (
	// CASE ) ;
	public final void rule__Case_Stmt__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4901:1: ( ( CASE ) )
			// InternalStructuredTextParser.g:4902:1: ( CASE )
			{
				// InternalStructuredTextParser.g:4902:1: ( CASE )
				// InternalStructuredTextParser.g:4903:2: CASE
				{
					before(grammarAccess.getCase_StmtAccess().getCASEKeyword_0());
					match(input, CASE, FOLLOW_2);
					after(grammarAccess.getCase_StmtAccess().getCASEKeyword_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__0__Impl"

	// $ANTLR start "rule__Case_Stmt__Group__1"
	// InternalStructuredTextParser.g:4912:1: rule__Case_Stmt__Group__1 :
	// rule__Case_Stmt__Group__1__Impl rule__Case_Stmt__Group__2 ;
	public final void rule__Case_Stmt__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4916:1: ( rule__Case_Stmt__Group__1__Impl
			// rule__Case_Stmt__Group__2 )
			// InternalStructuredTextParser.g:4917:2: rule__Case_Stmt__Group__1__Impl
			// rule__Case_Stmt__Group__2
			{
				pushFollow(FOLLOW_28);
				rule__Case_Stmt__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Case_Stmt__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__1"

	// $ANTLR start "rule__Case_Stmt__Group__1__Impl"
	// InternalStructuredTextParser.g:4924:1: rule__Case_Stmt__Group__1__Impl : ( (
	// rule__Case_Stmt__ExpressionAssignment_1 ) ) ;
	public final void rule__Case_Stmt__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4928:1: ( ( (
			// rule__Case_Stmt__ExpressionAssignment_1 ) ) )
			// InternalStructuredTextParser.g:4929:1: ( (
			// rule__Case_Stmt__ExpressionAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:4929:1: ( (
				// rule__Case_Stmt__ExpressionAssignment_1 ) )
				// InternalStructuredTextParser.g:4930:2: (
				// rule__Case_Stmt__ExpressionAssignment_1 )
				{
					before(grammarAccess.getCase_StmtAccess().getExpressionAssignment_1());
					// InternalStructuredTextParser.g:4931:2: (
					// rule__Case_Stmt__ExpressionAssignment_1 )
					// InternalStructuredTextParser.g:4931:3:
					// rule__Case_Stmt__ExpressionAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Case_Stmt__ExpressionAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getCase_StmtAccess().getExpressionAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__1__Impl"

	// $ANTLR start "rule__Case_Stmt__Group__2"
	// InternalStructuredTextParser.g:4939:1: rule__Case_Stmt__Group__2 :
	// rule__Case_Stmt__Group__2__Impl rule__Case_Stmt__Group__3 ;
	public final void rule__Case_Stmt__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4943:1: ( rule__Case_Stmt__Group__2__Impl
			// rule__Case_Stmt__Group__3 )
			// InternalStructuredTextParser.g:4944:2: rule__Case_Stmt__Group__2__Impl
			// rule__Case_Stmt__Group__3
			{
				pushFollow(FOLLOW_13);
				rule__Case_Stmt__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Case_Stmt__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__2"

	// $ANTLR start "rule__Case_Stmt__Group__2__Impl"
	// InternalStructuredTextParser.g:4951:1: rule__Case_Stmt__Group__2__Impl : ( OF
	// ) ;
	public final void rule__Case_Stmt__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4955:1: ( ( OF ) )
			// InternalStructuredTextParser.g:4956:1: ( OF )
			{
				// InternalStructuredTextParser.g:4956:1: ( OF )
				// InternalStructuredTextParser.g:4957:2: OF
				{
					before(grammarAccess.getCase_StmtAccess().getOFKeyword_2());
					match(input, OF, FOLLOW_2);
					after(grammarAccess.getCase_StmtAccess().getOFKeyword_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__2__Impl"

	// $ANTLR start "rule__Case_Stmt__Group__3"
	// InternalStructuredTextParser.g:4966:1: rule__Case_Stmt__Group__3 :
	// rule__Case_Stmt__Group__3__Impl rule__Case_Stmt__Group__4 ;
	public final void rule__Case_Stmt__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4970:1: ( rule__Case_Stmt__Group__3__Impl
			// rule__Case_Stmt__Group__4 )
			// InternalStructuredTextParser.g:4971:2: rule__Case_Stmt__Group__3__Impl
			// rule__Case_Stmt__Group__4
			{
				pushFollow(FOLLOW_29);
				rule__Case_Stmt__Group__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Case_Stmt__Group__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__3"

	// $ANTLR start "rule__Case_Stmt__Group__3__Impl"
	// InternalStructuredTextParser.g:4978:1: rule__Case_Stmt__Group__3__Impl : ( (
	// ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3
	// )* ) ) ;
	public final void rule__Case_Stmt__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:4982:1: ( ( ( (
			// rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3
			// )* ) ) )
			// InternalStructuredTextParser.g:4983:1: ( ( (
			// rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3
			// )* ) )
			{
				// InternalStructuredTextParser.g:4983:1: ( ( (
				// rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3
				// )* ) )
				// InternalStructuredTextParser.g:4984:2: ( ( rule__Case_Stmt__CaseAssignment_3
				// ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* )
				{
					// InternalStructuredTextParser.g:4984:2: ( ( rule__Case_Stmt__CaseAssignment_3
					// ) )
					// InternalStructuredTextParser.g:4985:3: ( rule__Case_Stmt__CaseAssignment_3 )
					{
						before(grammarAccess.getCase_StmtAccess().getCaseAssignment_3());
						// InternalStructuredTextParser.g:4986:3: ( rule__Case_Stmt__CaseAssignment_3 )
						// InternalStructuredTextParser.g:4986:4: rule__Case_Stmt__CaseAssignment_3
						{
							pushFollow(FOLLOW_30);
							rule__Case_Stmt__CaseAssignment_3();

							state._fsp--;

						}

						after(grammarAccess.getCase_StmtAccess().getCaseAssignment_3());

					}

					// InternalStructuredTextParser.g:4989:2: ( ( rule__Case_Stmt__CaseAssignment_3
					// )* )
					// InternalStructuredTextParser.g:4990:3: ( rule__Case_Stmt__CaseAssignment_3 )*
					{
						before(grammarAccess.getCase_StmtAccess().getCaseAssignment_3());
						// InternalStructuredTextParser.g:4991:3: ( rule__Case_Stmt__CaseAssignment_3 )*
						loop47: do {
							int alt47 = 2;
							int LA47_0 = input.LA(1);

							if (((LA47_0 >= LDATE_AND_TIME && LA47_0 <= TIME_OF_DAY) || LA47_0 == WSTRING
									|| LA47_0 == STRING || (LA47_0 >= FALSE && LA47_0 <= LTIME)
									|| (LA47_0 >= UDINT && LA47_0 <= ULINT) || (LA47_0 >= USINT && LA47_0 <= WCHAR)
									|| LA47_0 == BOOL || (LA47_0 >= CHAR && LA47_0 <= DINT)
									|| (LA47_0 >= LINT && LA47_0 <= SINT) || (LA47_0 >= TIME && LA47_0 <= UINT)
									|| (LA47_0 >= INT && LA47_0 <= LDT) || LA47_0 == TOD || LA47_0 == DT
									|| (LA47_0 >= LD && LA47_0 <= LT) || LA47_0 == PlusSign || LA47_0 == HyphenMinus
									|| LA47_0 == T || LA47_0 == D_1
									|| (LA47_0 >= RULE_BINARY_INT && LA47_0 <= RULE_UNSIGNED_INT)
									|| LA47_0 == RULE_S_BYTE_CHAR_STR || LA47_0 == RULE_D_BYTE_CHAR_STR)) {
								alt47 = 1;
							}

							switch (alt47) {
							case 1:
							// InternalStructuredTextParser.g:4991:4: rule__Case_Stmt__CaseAssignment_3
							{
								pushFollow(FOLLOW_30);
								rule__Case_Stmt__CaseAssignment_3();

								state._fsp--;

							}
								break;

							default:
								break loop47;
							}
						} while (true);

						after(grammarAccess.getCase_StmtAccess().getCaseAssignment_3());

					}

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__3__Impl"

	// $ANTLR start "rule__Case_Stmt__Group__4"
	// InternalStructuredTextParser.g:5000:1: rule__Case_Stmt__Group__4 :
	// rule__Case_Stmt__Group__4__Impl rule__Case_Stmt__Group__5 ;
	public final void rule__Case_Stmt__Group__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5004:1: ( rule__Case_Stmt__Group__4__Impl
			// rule__Case_Stmt__Group__5 )
			// InternalStructuredTextParser.g:5005:2: rule__Case_Stmt__Group__4__Impl
			// rule__Case_Stmt__Group__5
			{
				pushFollow(FOLLOW_29);
				rule__Case_Stmt__Group__4__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Case_Stmt__Group__5();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__4"

	// $ANTLR start "rule__Case_Stmt__Group__4__Impl"
	// InternalStructuredTextParser.g:5012:1: rule__Case_Stmt__Group__4__Impl : ( (
	// rule__Case_Stmt__ElseAssignment_4 )? ) ;
	public final void rule__Case_Stmt__Group__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5016:1: ( ( (
			// rule__Case_Stmt__ElseAssignment_4 )? ) )
			// InternalStructuredTextParser.g:5017:1: ( ( rule__Case_Stmt__ElseAssignment_4
			// )? )
			{
				// InternalStructuredTextParser.g:5017:1: ( ( rule__Case_Stmt__ElseAssignment_4
				// )? )
				// InternalStructuredTextParser.g:5018:2: ( rule__Case_Stmt__ElseAssignment_4 )?
				{
					before(grammarAccess.getCase_StmtAccess().getElseAssignment_4());
					// InternalStructuredTextParser.g:5019:2: ( rule__Case_Stmt__ElseAssignment_4 )?
					int alt48 = 2;
					int LA48_0 = input.LA(1);

					if ((LA48_0 == ELSE)) {
						alt48 = 1;
					}
					switch (alt48) {
					case 1:
					// InternalStructuredTextParser.g:5019:3: rule__Case_Stmt__ElseAssignment_4
					{
						pushFollow(FOLLOW_2);
						rule__Case_Stmt__ElseAssignment_4();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getCase_StmtAccess().getElseAssignment_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__4__Impl"

	// $ANTLR start "rule__Case_Stmt__Group__5"
	// InternalStructuredTextParser.g:5027:1: rule__Case_Stmt__Group__5 :
	// rule__Case_Stmt__Group__5__Impl ;
	public final void rule__Case_Stmt__Group__5() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5031:1: ( rule__Case_Stmt__Group__5__Impl )
			// InternalStructuredTextParser.g:5032:2: rule__Case_Stmt__Group__5__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Case_Stmt__Group__5__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__5"

	// $ANTLR start "rule__Case_Stmt__Group__5__Impl"
	// InternalStructuredTextParser.g:5038:1: rule__Case_Stmt__Group__5__Impl : (
	// END_CASE ) ;
	public final void rule__Case_Stmt__Group__5__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5042:1: ( ( END_CASE ) )
			// InternalStructuredTextParser.g:5043:1: ( END_CASE )
			{
				// InternalStructuredTextParser.g:5043:1: ( END_CASE )
				// InternalStructuredTextParser.g:5044:2: END_CASE
				{
					before(grammarAccess.getCase_StmtAccess().getEND_CASEKeyword_5());
					match(input, END_CASE, FOLLOW_2);
					after(grammarAccess.getCase_StmtAccess().getEND_CASEKeyword_5());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__Group__5__Impl"

	// $ANTLR start "rule__Case_Selection__Group__0"
	// InternalStructuredTextParser.g:5054:1: rule__Case_Selection__Group__0 :
	// rule__Case_Selection__Group__0__Impl rule__Case_Selection__Group__1 ;
	public final void rule__Case_Selection__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5058:1: ( rule__Case_Selection__Group__0__Impl
			// rule__Case_Selection__Group__1 )
			// InternalStructuredTextParser.g:5059:2: rule__Case_Selection__Group__0__Impl
			// rule__Case_Selection__Group__1
			{
				pushFollow(FOLLOW_31);
				rule__Case_Selection__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Case_Selection__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group__0"

	// $ANTLR start "rule__Case_Selection__Group__0__Impl"
	// InternalStructuredTextParser.g:5066:1: rule__Case_Selection__Group__0__Impl :
	// ( ( rule__Case_Selection__CaseAssignment_0 ) ) ;
	public final void rule__Case_Selection__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5070:1: ( ( (
			// rule__Case_Selection__CaseAssignment_0 ) ) )
			// InternalStructuredTextParser.g:5071:1: ( (
			// rule__Case_Selection__CaseAssignment_0 ) )
			{
				// InternalStructuredTextParser.g:5071:1: ( (
				// rule__Case_Selection__CaseAssignment_0 ) )
				// InternalStructuredTextParser.g:5072:2: (
				// rule__Case_Selection__CaseAssignment_0 )
				{
					before(grammarAccess.getCase_SelectionAccess().getCaseAssignment_0());
					// InternalStructuredTextParser.g:5073:2: (
					// rule__Case_Selection__CaseAssignment_0 )
					// InternalStructuredTextParser.g:5073:3: rule__Case_Selection__CaseAssignment_0
					{
						pushFollow(FOLLOW_2);
						rule__Case_Selection__CaseAssignment_0();

						state._fsp--;

					}

					after(grammarAccess.getCase_SelectionAccess().getCaseAssignment_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group__0__Impl"

	// $ANTLR start "rule__Case_Selection__Group__1"
	// InternalStructuredTextParser.g:5081:1: rule__Case_Selection__Group__1 :
	// rule__Case_Selection__Group__1__Impl rule__Case_Selection__Group__2 ;
	public final void rule__Case_Selection__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5085:1: ( rule__Case_Selection__Group__1__Impl
			// rule__Case_Selection__Group__2 )
			// InternalStructuredTextParser.g:5086:2: rule__Case_Selection__Group__1__Impl
			// rule__Case_Selection__Group__2
			{
				pushFollow(FOLLOW_31);
				rule__Case_Selection__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Case_Selection__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group__1"

	// $ANTLR start "rule__Case_Selection__Group__1__Impl"
	// InternalStructuredTextParser.g:5093:1: rule__Case_Selection__Group__1__Impl :
	// ( ( rule__Case_Selection__Group_1__0 )* ) ;
	public final void rule__Case_Selection__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5097:1: ( ( ( rule__Case_Selection__Group_1__0
			// )* ) )
			// InternalStructuredTextParser.g:5098:1: ( ( rule__Case_Selection__Group_1__0
			// )* )
			{
				// InternalStructuredTextParser.g:5098:1: ( ( rule__Case_Selection__Group_1__0
				// )* )
				// InternalStructuredTextParser.g:5099:2: ( rule__Case_Selection__Group_1__0 )*
				{
					before(grammarAccess.getCase_SelectionAccess().getGroup_1());
					// InternalStructuredTextParser.g:5100:2: ( rule__Case_Selection__Group_1__0 )*
					loop49: do {
						int alt49 = 2;
						int LA49_0 = input.LA(1);

						if ((LA49_0 == Comma)) {
							alt49 = 1;
						}

						switch (alt49) {
						case 1:
						// InternalStructuredTextParser.g:5100:3: rule__Case_Selection__Group_1__0
						{
							pushFollow(FOLLOW_32);
							rule__Case_Selection__Group_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop49;
						}
					} while (true);

					after(grammarAccess.getCase_SelectionAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group__1__Impl"

	// $ANTLR start "rule__Case_Selection__Group__2"
	// InternalStructuredTextParser.g:5108:1: rule__Case_Selection__Group__2 :
	// rule__Case_Selection__Group__2__Impl rule__Case_Selection__Group__3 ;
	public final void rule__Case_Selection__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5112:1: ( rule__Case_Selection__Group__2__Impl
			// rule__Case_Selection__Group__3 )
			// InternalStructuredTextParser.g:5113:2: rule__Case_Selection__Group__2__Impl
			// rule__Case_Selection__Group__3
			{
				pushFollow(FOLLOW_3);
				rule__Case_Selection__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Case_Selection__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group__2"

	// $ANTLR start "rule__Case_Selection__Group__2__Impl"
	// InternalStructuredTextParser.g:5120:1: rule__Case_Selection__Group__2__Impl :
	// ( Colon ) ;
	public final void rule__Case_Selection__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5124:1: ( ( Colon ) )
			// InternalStructuredTextParser.g:5125:1: ( Colon )
			{
				// InternalStructuredTextParser.g:5125:1: ( Colon )
				// InternalStructuredTextParser.g:5126:2: Colon
				{
					before(grammarAccess.getCase_SelectionAccess().getColonKeyword_2());
					match(input, Colon, FOLLOW_2);
					after(grammarAccess.getCase_SelectionAccess().getColonKeyword_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group__2__Impl"

	// $ANTLR start "rule__Case_Selection__Group__3"
	// InternalStructuredTextParser.g:5135:1: rule__Case_Selection__Group__3 :
	// rule__Case_Selection__Group__3__Impl ;
	public final void rule__Case_Selection__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5139:1: ( rule__Case_Selection__Group__3__Impl
			// )
			// InternalStructuredTextParser.g:5140:2: rule__Case_Selection__Group__3__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Case_Selection__Group__3__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group__3"

	// $ANTLR start "rule__Case_Selection__Group__3__Impl"
	// InternalStructuredTextParser.g:5146:1: rule__Case_Selection__Group__3__Impl :
	// ( ( rule__Case_Selection__StatementsAssignment_3 ) ) ;
	public final void rule__Case_Selection__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5150:1: ( ( (
			// rule__Case_Selection__StatementsAssignment_3 ) ) )
			// InternalStructuredTextParser.g:5151:1: ( (
			// rule__Case_Selection__StatementsAssignment_3 ) )
			{
				// InternalStructuredTextParser.g:5151:1: ( (
				// rule__Case_Selection__StatementsAssignment_3 ) )
				// InternalStructuredTextParser.g:5152:2: (
				// rule__Case_Selection__StatementsAssignment_3 )
				{
					before(grammarAccess.getCase_SelectionAccess().getStatementsAssignment_3());
					// InternalStructuredTextParser.g:5153:2: (
					// rule__Case_Selection__StatementsAssignment_3 )
					// InternalStructuredTextParser.g:5153:3:
					// rule__Case_Selection__StatementsAssignment_3
					{
						pushFollow(FOLLOW_2);
						rule__Case_Selection__StatementsAssignment_3();

						state._fsp--;

					}

					after(grammarAccess.getCase_SelectionAccess().getStatementsAssignment_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group__3__Impl"

	// $ANTLR start "rule__Case_Selection__Group_1__0"
	// InternalStructuredTextParser.g:5162:1: rule__Case_Selection__Group_1__0 :
	// rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1 ;
	public final void rule__Case_Selection__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5166:1: (
			// rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1 )
			// InternalStructuredTextParser.g:5167:2: rule__Case_Selection__Group_1__0__Impl
			// rule__Case_Selection__Group_1__1
			{
				pushFollow(FOLLOW_13);
				rule__Case_Selection__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Case_Selection__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group_1__0"

	// $ANTLR start "rule__Case_Selection__Group_1__0__Impl"
	// InternalStructuredTextParser.g:5174:1: rule__Case_Selection__Group_1__0__Impl
	// : ( Comma ) ;
	public final void rule__Case_Selection__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5178:1: ( ( Comma ) )
			// InternalStructuredTextParser.g:5179:1: ( Comma )
			{
				// InternalStructuredTextParser.g:5179:1: ( Comma )
				// InternalStructuredTextParser.g:5180:2: Comma
				{
					before(grammarAccess.getCase_SelectionAccess().getCommaKeyword_1_0());
					match(input, Comma, FOLLOW_2);
					after(grammarAccess.getCase_SelectionAccess().getCommaKeyword_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group_1__0__Impl"

	// $ANTLR start "rule__Case_Selection__Group_1__1"
	// InternalStructuredTextParser.g:5189:1: rule__Case_Selection__Group_1__1 :
	// rule__Case_Selection__Group_1__1__Impl ;
	public final void rule__Case_Selection__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5193:1: (
			// rule__Case_Selection__Group_1__1__Impl )
			// InternalStructuredTextParser.g:5194:2: rule__Case_Selection__Group_1__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Case_Selection__Group_1__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group_1__1"

	// $ANTLR start "rule__Case_Selection__Group_1__1__Impl"
	// InternalStructuredTextParser.g:5200:1: rule__Case_Selection__Group_1__1__Impl
	// : ( ( rule__Case_Selection__CaseAssignment_1_1 ) ) ;
	public final void rule__Case_Selection__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5204:1: ( ( (
			// rule__Case_Selection__CaseAssignment_1_1 ) ) )
			// InternalStructuredTextParser.g:5205:1: ( (
			// rule__Case_Selection__CaseAssignment_1_1 ) )
			{
				// InternalStructuredTextParser.g:5205:1: ( (
				// rule__Case_Selection__CaseAssignment_1_1 ) )
				// InternalStructuredTextParser.g:5206:2: (
				// rule__Case_Selection__CaseAssignment_1_1 )
				{
					before(grammarAccess.getCase_SelectionAccess().getCaseAssignment_1_1());
					// InternalStructuredTextParser.g:5207:2: (
					// rule__Case_Selection__CaseAssignment_1_1 )
					// InternalStructuredTextParser.g:5207:3:
					// rule__Case_Selection__CaseAssignment_1_1
					{
						pushFollow(FOLLOW_2);
						rule__Case_Selection__CaseAssignment_1_1();

						state._fsp--;

					}

					after(grammarAccess.getCase_SelectionAccess().getCaseAssignment_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__Group_1__1__Impl"

	// $ANTLR start "rule__Iteration_Stmt__Group_3__0"
	// InternalStructuredTextParser.g:5216:1: rule__Iteration_Stmt__Group_3__0 :
	// rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1 ;
	public final void rule__Iteration_Stmt__Group_3__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5220:1: (
			// rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1 )
			// InternalStructuredTextParser.g:5221:2: rule__Iteration_Stmt__Group_3__0__Impl
			// rule__Iteration_Stmt__Group_3__1
			{
				pushFollow(FOLLOW_33);
				rule__Iteration_Stmt__Group_3__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Iteration_Stmt__Group_3__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Iteration_Stmt__Group_3__0"

	// $ANTLR start "rule__Iteration_Stmt__Group_3__0__Impl"
	// InternalStructuredTextParser.g:5228:1: rule__Iteration_Stmt__Group_3__0__Impl
	// : ( () ) ;
	public final void rule__Iteration_Stmt__Group_3__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5232:1: ( ( () ) )
			// InternalStructuredTextParser.g:5233:1: ( () )
			{
				// InternalStructuredTextParser.g:5233:1: ( () )
				// InternalStructuredTextParser.g:5234:2: ()
				{
					before(grammarAccess.getIteration_StmtAccess().getExitStatementAction_3_0());
					// InternalStructuredTextParser.g:5235:2: ()
					// InternalStructuredTextParser.g:5235:3:
					{
					}

					after(grammarAccess.getIteration_StmtAccess().getExitStatementAction_3_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Iteration_Stmt__Group_3__0__Impl"

	// $ANTLR start "rule__Iteration_Stmt__Group_3__1"
	// InternalStructuredTextParser.g:5243:1: rule__Iteration_Stmt__Group_3__1 :
	// rule__Iteration_Stmt__Group_3__1__Impl ;
	public final void rule__Iteration_Stmt__Group_3__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5247:1: (
			// rule__Iteration_Stmt__Group_3__1__Impl )
			// InternalStructuredTextParser.g:5248:2: rule__Iteration_Stmt__Group_3__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Iteration_Stmt__Group_3__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Iteration_Stmt__Group_3__1"

	// $ANTLR start "rule__Iteration_Stmt__Group_3__1__Impl"
	// InternalStructuredTextParser.g:5254:1: rule__Iteration_Stmt__Group_3__1__Impl
	// : ( EXIT ) ;
	public final void rule__Iteration_Stmt__Group_3__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5258:1: ( ( EXIT ) )
			// InternalStructuredTextParser.g:5259:1: ( EXIT )
			{
				// InternalStructuredTextParser.g:5259:1: ( EXIT )
				// InternalStructuredTextParser.g:5260:2: EXIT
				{
					before(grammarAccess.getIteration_StmtAccess().getEXITKeyword_3_1());
					match(input, EXIT, FOLLOW_2);
					after(grammarAccess.getIteration_StmtAccess().getEXITKeyword_3_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Iteration_Stmt__Group_3__1__Impl"

	// $ANTLR start "rule__Iteration_Stmt__Group_4__0"
	// InternalStructuredTextParser.g:5270:1: rule__Iteration_Stmt__Group_4__0 :
	// rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1 ;
	public final void rule__Iteration_Stmt__Group_4__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5274:1: (
			// rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1 )
			// InternalStructuredTextParser.g:5275:2: rule__Iteration_Stmt__Group_4__0__Impl
			// rule__Iteration_Stmt__Group_4__1
			{
				pushFollow(FOLLOW_34);
				rule__Iteration_Stmt__Group_4__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Iteration_Stmt__Group_4__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Iteration_Stmt__Group_4__0"

	// $ANTLR start "rule__Iteration_Stmt__Group_4__0__Impl"
	// InternalStructuredTextParser.g:5282:1: rule__Iteration_Stmt__Group_4__0__Impl
	// : ( () ) ;
	public final void rule__Iteration_Stmt__Group_4__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5286:1: ( ( () ) )
			// InternalStructuredTextParser.g:5287:1: ( () )
			{
				// InternalStructuredTextParser.g:5287:1: ( () )
				// InternalStructuredTextParser.g:5288:2: ()
				{
					before(grammarAccess.getIteration_StmtAccess().getContinueStatementAction_4_0());
					// InternalStructuredTextParser.g:5289:2: ()
					// InternalStructuredTextParser.g:5289:3:
					{
					}

					after(grammarAccess.getIteration_StmtAccess().getContinueStatementAction_4_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Iteration_Stmt__Group_4__0__Impl"

	// $ANTLR start "rule__Iteration_Stmt__Group_4__1"
	// InternalStructuredTextParser.g:5297:1: rule__Iteration_Stmt__Group_4__1 :
	// rule__Iteration_Stmt__Group_4__1__Impl ;
	public final void rule__Iteration_Stmt__Group_4__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5301:1: (
			// rule__Iteration_Stmt__Group_4__1__Impl )
			// InternalStructuredTextParser.g:5302:2: rule__Iteration_Stmt__Group_4__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Iteration_Stmt__Group_4__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Iteration_Stmt__Group_4__1"

	// $ANTLR start "rule__Iteration_Stmt__Group_4__1__Impl"
	// InternalStructuredTextParser.g:5308:1: rule__Iteration_Stmt__Group_4__1__Impl
	// : ( CONTINUE ) ;
	public final void rule__Iteration_Stmt__Group_4__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5312:1: ( ( CONTINUE ) )
			// InternalStructuredTextParser.g:5313:1: ( CONTINUE )
			{
				// InternalStructuredTextParser.g:5313:1: ( CONTINUE )
				// InternalStructuredTextParser.g:5314:2: CONTINUE
				{
					before(grammarAccess.getIteration_StmtAccess().getCONTINUEKeyword_4_1());
					match(input, CONTINUE, FOLLOW_2);
					after(grammarAccess.getIteration_StmtAccess().getCONTINUEKeyword_4_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Iteration_Stmt__Group_4__1__Impl"

	// $ANTLR start "rule__For_Stmt__Group__0"
	// InternalStructuredTextParser.g:5324:1: rule__For_Stmt__Group__0 :
	// rule__For_Stmt__Group__0__Impl rule__For_Stmt__Group__1 ;
	public final void rule__For_Stmt__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5328:1: ( rule__For_Stmt__Group__0__Impl
			// rule__For_Stmt__Group__1 )
			// InternalStructuredTextParser.g:5329:2: rule__For_Stmt__Group__0__Impl
			// rule__For_Stmt__Group__1
			{
				pushFollow(FOLLOW_16);
				rule__For_Stmt__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__0"

	// $ANTLR start "rule__For_Stmt__Group__0__Impl"
	// InternalStructuredTextParser.g:5336:1: rule__For_Stmt__Group__0__Impl : ( FOR
	// ) ;
	public final void rule__For_Stmt__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5340:1: ( ( FOR ) )
			// InternalStructuredTextParser.g:5341:1: ( FOR )
			{
				// InternalStructuredTextParser.g:5341:1: ( FOR )
				// InternalStructuredTextParser.g:5342:2: FOR
				{
					before(grammarAccess.getFor_StmtAccess().getFORKeyword_0());
					match(input, FOR, FOLLOW_2);
					after(grammarAccess.getFor_StmtAccess().getFORKeyword_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__0__Impl"

	// $ANTLR start "rule__For_Stmt__Group__1"
	// InternalStructuredTextParser.g:5351:1: rule__For_Stmt__Group__1 :
	// rule__For_Stmt__Group__1__Impl rule__For_Stmt__Group__2 ;
	public final void rule__For_Stmt__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5355:1: ( rule__For_Stmt__Group__1__Impl
			// rule__For_Stmt__Group__2 )
			// InternalStructuredTextParser.g:5356:2: rule__For_Stmt__Group__1__Impl
			// rule__For_Stmt__Group__2
			{
				pushFollow(FOLLOW_19);
				rule__For_Stmt__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__1"

	// $ANTLR start "rule__For_Stmt__Group__1__Impl"
	// InternalStructuredTextParser.g:5363:1: rule__For_Stmt__Group__1__Impl : ( (
	// rule__For_Stmt__VariableAssignment_1 ) ) ;
	public final void rule__For_Stmt__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5367:1: ( ( (
			// rule__For_Stmt__VariableAssignment_1 ) ) )
			// InternalStructuredTextParser.g:5368:1: ( (
			// rule__For_Stmt__VariableAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:5368:1: ( (
				// rule__For_Stmt__VariableAssignment_1 ) )
				// InternalStructuredTextParser.g:5369:2: ( rule__For_Stmt__VariableAssignment_1
				// )
				{
					before(grammarAccess.getFor_StmtAccess().getVariableAssignment_1());
					// InternalStructuredTextParser.g:5370:2: ( rule__For_Stmt__VariableAssignment_1
					// )
					// InternalStructuredTextParser.g:5370:3: rule__For_Stmt__VariableAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__For_Stmt__VariableAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getFor_StmtAccess().getVariableAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__1__Impl"

	// $ANTLR start "rule__For_Stmt__Group__2"
	// InternalStructuredTextParser.g:5378:1: rule__For_Stmt__Group__2 :
	// rule__For_Stmt__Group__2__Impl rule__For_Stmt__Group__3 ;
	public final void rule__For_Stmt__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5382:1: ( rule__For_Stmt__Group__2__Impl
			// rule__For_Stmt__Group__3 )
			// InternalStructuredTextParser.g:5383:2: rule__For_Stmt__Group__2__Impl
			// rule__For_Stmt__Group__3
			{
				pushFollow(FOLLOW_20);
				rule__For_Stmt__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__2"

	// $ANTLR start "rule__For_Stmt__Group__2__Impl"
	// InternalStructuredTextParser.g:5390:1: rule__For_Stmt__Group__2__Impl : (
	// ColonEqualsSign ) ;
	public final void rule__For_Stmt__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5394:1: ( ( ColonEqualsSign ) )
			// InternalStructuredTextParser.g:5395:1: ( ColonEqualsSign )
			{
				// InternalStructuredTextParser.g:5395:1: ( ColonEqualsSign )
				// InternalStructuredTextParser.g:5396:2: ColonEqualsSign
				{
					before(grammarAccess.getFor_StmtAccess().getColonEqualsSignKeyword_2());
					match(input, ColonEqualsSign, FOLLOW_2);
					after(grammarAccess.getFor_StmtAccess().getColonEqualsSignKeyword_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__2__Impl"

	// $ANTLR start "rule__For_Stmt__Group__3"
	// InternalStructuredTextParser.g:5405:1: rule__For_Stmt__Group__3 :
	// rule__For_Stmt__Group__3__Impl rule__For_Stmt__Group__4 ;
	public final void rule__For_Stmt__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5409:1: ( rule__For_Stmt__Group__3__Impl
			// rule__For_Stmt__Group__4 )
			// InternalStructuredTextParser.g:5410:2: rule__For_Stmt__Group__3__Impl
			// rule__For_Stmt__Group__4
			{
				pushFollow(FOLLOW_35);
				rule__For_Stmt__Group__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__3"

	// $ANTLR start "rule__For_Stmt__Group__3__Impl"
	// InternalStructuredTextParser.g:5417:1: rule__For_Stmt__Group__3__Impl : ( (
	// rule__For_Stmt__FromAssignment_3 ) ) ;
	public final void rule__For_Stmt__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5421:1: ( ( ( rule__For_Stmt__FromAssignment_3
			// ) ) )
			// InternalStructuredTextParser.g:5422:1: ( ( rule__For_Stmt__FromAssignment_3 )
			// )
			{
				// InternalStructuredTextParser.g:5422:1: ( ( rule__For_Stmt__FromAssignment_3 )
				// )
				// InternalStructuredTextParser.g:5423:2: ( rule__For_Stmt__FromAssignment_3 )
				{
					before(grammarAccess.getFor_StmtAccess().getFromAssignment_3());
					// InternalStructuredTextParser.g:5424:2: ( rule__For_Stmt__FromAssignment_3 )
					// InternalStructuredTextParser.g:5424:3: rule__For_Stmt__FromAssignment_3
					{
						pushFollow(FOLLOW_2);
						rule__For_Stmt__FromAssignment_3();

						state._fsp--;

					}

					after(grammarAccess.getFor_StmtAccess().getFromAssignment_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__3__Impl"

	// $ANTLR start "rule__For_Stmt__Group__4"
	// InternalStructuredTextParser.g:5432:1: rule__For_Stmt__Group__4 :
	// rule__For_Stmt__Group__4__Impl rule__For_Stmt__Group__5 ;
	public final void rule__For_Stmt__Group__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5436:1: ( rule__For_Stmt__Group__4__Impl
			// rule__For_Stmt__Group__5 )
			// InternalStructuredTextParser.g:5437:2: rule__For_Stmt__Group__4__Impl
			// rule__For_Stmt__Group__5
			{
				pushFollow(FOLLOW_20);
				rule__For_Stmt__Group__4__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group__5();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__4"

	// $ANTLR start "rule__For_Stmt__Group__4__Impl"
	// InternalStructuredTextParser.g:5444:1: rule__For_Stmt__Group__4__Impl : ( TO
	// ) ;
	public final void rule__For_Stmt__Group__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5448:1: ( ( TO ) )
			// InternalStructuredTextParser.g:5449:1: ( TO )
			{
				// InternalStructuredTextParser.g:5449:1: ( TO )
				// InternalStructuredTextParser.g:5450:2: TO
				{
					before(grammarAccess.getFor_StmtAccess().getTOKeyword_4());
					match(input, TO, FOLLOW_2);
					after(grammarAccess.getFor_StmtAccess().getTOKeyword_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__4__Impl"

	// $ANTLR start "rule__For_Stmt__Group__5"
	// InternalStructuredTextParser.g:5459:1: rule__For_Stmt__Group__5 :
	// rule__For_Stmt__Group__5__Impl rule__For_Stmt__Group__6 ;
	public final void rule__For_Stmt__Group__5() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5463:1: ( rule__For_Stmt__Group__5__Impl
			// rule__For_Stmt__Group__6 )
			// InternalStructuredTextParser.g:5464:2: rule__For_Stmt__Group__5__Impl
			// rule__For_Stmt__Group__6
			{
				pushFollow(FOLLOW_36);
				rule__For_Stmt__Group__5__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group__6();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__5"

	// $ANTLR start "rule__For_Stmt__Group__5__Impl"
	// InternalStructuredTextParser.g:5471:1: rule__For_Stmt__Group__5__Impl : ( (
	// rule__For_Stmt__ToAssignment_5 ) ) ;
	public final void rule__For_Stmt__Group__5__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5475:1: ( ( ( rule__For_Stmt__ToAssignment_5 )
			// ) )
			// InternalStructuredTextParser.g:5476:1: ( ( rule__For_Stmt__ToAssignment_5 ) )
			{
				// InternalStructuredTextParser.g:5476:1: ( ( rule__For_Stmt__ToAssignment_5 ) )
				// InternalStructuredTextParser.g:5477:2: ( rule__For_Stmt__ToAssignment_5 )
				{
					before(grammarAccess.getFor_StmtAccess().getToAssignment_5());
					// InternalStructuredTextParser.g:5478:2: ( rule__For_Stmt__ToAssignment_5 )
					// InternalStructuredTextParser.g:5478:3: rule__For_Stmt__ToAssignment_5
					{
						pushFollow(FOLLOW_2);
						rule__For_Stmt__ToAssignment_5();

						state._fsp--;

					}

					after(grammarAccess.getFor_StmtAccess().getToAssignment_5());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__5__Impl"

	// $ANTLR start "rule__For_Stmt__Group__6"
	// InternalStructuredTextParser.g:5486:1: rule__For_Stmt__Group__6 :
	// rule__For_Stmt__Group__6__Impl rule__For_Stmt__Group__7 ;
	public final void rule__For_Stmt__Group__6() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5490:1: ( rule__For_Stmt__Group__6__Impl
			// rule__For_Stmt__Group__7 )
			// InternalStructuredTextParser.g:5491:2: rule__For_Stmt__Group__6__Impl
			// rule__For_Stmt__Group__7
			{
				pushFollow(FOLLOW_36);
				rule__For_Stmt__Group__6__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group__7();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__6"

	// $ANTLR start "rule__For_Stmt__Group__6__Impl"
	// InternalStructuredTextParser.g:5498:1: rule__For_Stmt__Group__6__Impl : ( (
	// rule__For_Stmt__Group_6__0 )? ) ;
	public final void rule__For_Stmt__Group__6__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5502:1: ( ( ( rule__For_Stmt__Group_6__0 )? )
			// )
			// InternalStructuredTextParser.g:5503:1: ( ( rule__For_Stmt__Group_6__0 )? )
			{
				// InternalStructuredTextParser.g:5503:1: ( ( rule__For_Stmt__Group_6__0 )? )
				// InternalStructuredTextParser.g:5504:2: ( rule__For_Stmt__Group_6__0 )?
				{
					before(grammarAccess.getFor_StmtAccess().getGroup_6());
					// InternalStructuredTextParser.g:5505:2: ( rule__For_Stmt__Group_6__0 )?
					int alt50 = 2;
					int LA50_0 = input.LA(1);

					if ((LA50_0 == BY)) {
						alt50 = 1;
					}
					switch (alt50) {
					case 1:
					// InternalStructuredTextParser.g:5505:3: rule__For_Stmt__Group_6__0
					{
						pushFollow(FOLLOW_2);
						rule__For_Stmt__Group_6__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getFor_StmtAccess().getGroup_6());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__6__Impl"

	// $ANTLR start "rule__For_Stmt__Group__7"
	// InternalStructuredTextParser.g:5513:1: rule__For_Stmt__Group__7 :
	// rule__For_Stmt__Group__7__Impl rule__For_Stmt__Group__8 ;
	public final void rule__For_Stmt__Group__7() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5517:1: ( rule__For_Stmt__Group__7__Impl
			// rule__For_Stmt__Group__8 )
			// InternalStructuredTextParser.g:5518:2: rule__For_Stmt__Group__7__Impl
			// rule__For_Stmt__Group__8
			{
				pushFollow(FOLLOW_3);
				rule__For_Stmt__Group__7__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group__8();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__7"

	// $ANTLR start "rule__For_Stmt__Group__7__Impl"
	// InternalStructuredTextParser.g:5525:1: rule__For_Stmt__Group__7__Impl : ( DO
	// ) ;
	public final void rule__For_Stmt__Group__7__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5529:1: ( ( DO ) )
			// InternalStructuredTextParser.g:5530:1: ( DO )
			{
				// InternalStructuredTextParser.g:5530:1: ( DO )
				// InternalStructuredTextParser.g:5531:2: DO
				{
					before(grammarAccess.getFor_StmtAccess().getDOKeyword_7());
					match(input, DO, FOLLOW_2);
					after(grammarAccess.getFor_StmtAccess().getDOKeyword_7());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__7__Impl"

	// $ANTLR start "rule__For_Stmt__Group__8"
	// InternalStructuredTextParser.g:5540:1: rule__For_Stmt__Group__8 :
	// rule__For_Stmt__Group__8__Impl rule__For_Stmt__Group__9 ;
	public final void rule__For_Stmt__Group__8() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5544:1: ( rule__For_Stmt__Group__8__Impl
			// rule__For_Stmt__Group__9 )
			// InternalStructuredTextParser.g:5545:2: rule__For_Stmt__Group__8__Impl
			// rule__For_Stmt__Group__9
			{
				pushFollow(FOLLOW_37);
				rule__For_Stmt__Group__8__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group__9();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__8"

	// $ANTLR start "rule__For_Stmt__Group__8__Impl"
	// InternalStructuredTextParser.g:5552:1: rule__For_Stmt__Group__8__Impl : ( (
	// rule__For_Stmt__StatementsAssignment_8 ) ) ;
	public final void rule__For_Stmt__Group__8__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5556:1: ( ( (
			// rule__For_Stmt__StatementsAssignment_8 ) ) )
			// InternalStructuredTextParser.g:5557:1: ( (
			// rule__For_Stmt__StatementsAssignment_8 ) )
			{
				// InternalStructuredTextParser.g:5557:1: ( (
				// rule__For_Stmt__StatementsAssignment_8 ) )
				// InternalStructuredTextParser.g:5558:2: (
				// rule__For_Stmt__StatementsAssignment_8 )
				{
					before(grammarAccess.getFor_StmtAccess().getStatementsAssignment_8());
					// InternalStructuredTextParser.g:5559:2: (
					// rule__For_Stmt__StatementsAssignment_8 )
					// InternalStructuredTextParser.g:5559:3: rule__For_Stmt__StatementsAssignment_8
					{
						pushFollow(FOLLOW_2);
						rule__For_Stmt__StatementsAssignment_8();

						state._fsp--;

					}

					after(grammarAccess.getFor_StmtAccess().getStatementsAssignment_8());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__8__Impl"

	// $ANTLR start "rule__For_Stmt__Group__9"
	// InternalStructuredTextParser.g:5567:1: rule__For_Stmt__Group__9 :
	// rule__For_Stmt__Group__9__Impl ;
	public final void rule__For_Stmt__Group__9() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5571:1: ( rule__For_Stmt__Group__9__Impl )
			// InternalStructuredTextParser.g:5572:2: rule__For_Stmt__Group__9__Impl
			{
				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group__9__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__9"

	// $ANTLR start "rule__For_Stmt__Group__9__Impl"
	// InternalStructuredTextParser.g:5578:1: rule__For_Stmt__Group__9__Impl : (
	// END_FOR ) ;
	public final void rule__For_Stmt__Group__9__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5582:1: ( ( END_FOR ) )
			// InternalStructuredTextParser.g:5583:1: ( END_FOR )
			{
				// InternalStructuredTextParser.g:5583:1: ( END_FOR )
				// InternalStructuredTextParser.g:5584:2: END_FOR
				{
					before(grammarAccess.getFor_StmtAccess().getEND_FORKeyword_9());
					match(input, END_FOR, FOLLOW_2);
					after(grammarAccess.getFor_StmtAccess().getEND_FORKeyword_9());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group__9__Impl"

	// $ANTLR start "rule__For_Stmt__Group_6__0"
	// InternalStructuredTextParser.g:5594:1: rule__For_Stmt__Group_6__0 :
	// rule__For_Stmt__Group_6__0__Impl rule__For_Stmt__Group_6__1 ;
	public final void rule__For_Stmt__Group_6__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5598:1: ( rule__For_Stmt__Group_6__0__Impl
			// rule__For_Stmt__Group_6__1 )
			// InternalStructuredTextParser.g:5599:2: rule__For_Stmt__Group_6__0__Impl
			// rule__For_Stmt__Group_6__1
			{
				pushFollow(FOLLOW_20);
				rule__For_Stmt__Group_6__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group_6__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group_6__0"

	// $ANTLR start "rule__For_Stmt__Group_6__0__Impl"
	// InternalStructuredTextParser.g:5606:1: rule__For_Stmt__Group_6__0__Impl : (
	// BY ) ;
	public final void rule__For_Stmt__Group_6__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5610:1: ( ( BY ) )
			// InternalStructuredTextParser.g:5611:1: ( BY )
			{
				// InternalStructuredTextParser.g:5611:1: ( BY )
				// InternalStructuredTextParser.g:5612:2: BY
				{
					before(grammarAccess.getFor_StmtAccess().getBYKeyword_6_0());
					match(input, BY, FOLLOW_2);
					after(grammarAccess.getFor_StmtAccess().getBYKeyword_6_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group_6__0__Impl"

	// $ANTLR start "rule__For_Stmt__Group_6__1"
	// InternalStructuredTextParser.g:5621:1: rule__For_Stmt__Group_6__1 :
	// rule__For_Stmt__Group_6__1__Impl ;
	public final void rule__For_Stmt__Group_6__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5625:1: ( rule__For_Stmt__Group_6__1__Impl )
			// InternalStructuredTextParser.g:5626:2: rule__For_Stmt__Group_6__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__For_Stmt__Group_6__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group_6__1"

	// $ANTLR start "rule__For_Stmt__Group_6__1__Impl"
	// InternalStructuredTextParser.g:5632:1: rule__For_Stmt__Group_6__1__Impl : ( (
	// rule__For_Stmt__ByAssignment_6_1 ) ) ;
	public final void rule__For_Stmt__Group_6__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5636:1: ( ( ( rule__For_Stmt__ByAssignment_6_1
			// ) ) )
			// InternalStructuredTextParser.g:5637:1: ( ( rule__For_Stmt__ByAssignment_6_1 )
			// )
			{
				// InternalStructuredTextParser.g:5637:1: ( ( rule__For_Stmt__ByAssignment_6_1 )
				// )
				// InternalStructuredTextParser.g:5638:2: ( rule__For_Stmt__ByAssignment_6_1 )
				{
					before(grammarAccess.getFor_StmtAccess().getByAssignment_6_1());
					// InternalStructuredTextParser.g:5639:2: ( rule__For_Stmt__ByAssignment_6_1 )
					// InternalStructuredTextParser.g:5639:3: rule__For_Stmt__ByAssignment_6_1
					{
						pushFollow(FOLLOW_2);
						rule__For_Stmt__ByAssignment_6_1();

						state._fsp--;

					}

					after(grammarAccess.getFor_StmtAccess().getByAssignment_6_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__Group_6__1__Impl"

	// $ANTLR start "rule__While_Stmt__Group__0"
	// InternalStructuredTextParser.g:5648:1: rule__While_Stmt__Group__0 :
	// rule__While_Stmt__Group__0__Impl rule__While_Stmt__Group__1 ;
	public final void rule__While_Stmt__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5652:1: ( rule__While_Stmt__Group__0__Impl
			// rule__While_Stmt__Group__1 )
			// InternalStructuredTextParser.g:5653:2: rule__While_Stmt__Group__0__Impl
			// rule__While_Stmt__Group__1
			{
				pushFollow(FOLLOW_20);
				rule__While_Stmt__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__While_Stmt__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__Group__0"

	// $ANTLR start "rule__While_Stmt__Group__0__Impl"
	// InternalStructuredTextParser.g:5660:1: rule__While_Stmt__Group__0__Impl : (
	// WHILE ) ;
	public final void rule__While_Stmt__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5664:1: ( ( WHILE ) )
			// InternalStructuredTextParser.g:5665:1: ( WHILE )
			{
				// InternalStructuredTextParser.g:5665:1: ( WHILE )
				// InternalStructuredTextParser.g:5666:2: WHILE
				{
					before(grammarAccess.getWhile_StmtAccess().getWHILEKeyword_0());
					match(input, WHILE, FOLLOW_2);
					after(grammarAccess.getWhile_StmtAccess().getWHILEKeyword_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__Group__0__Impl"

	// $ANTLR start "rule__While_Stmt__Group__1"
	// InternalStructuredTextParser.g:5675:1: rule__While_Stmt__Group__1 :
	// rule__While_Stmt__Group__1__Impl rule__While_Stmt__Group__2 ;
	public final void rule__While_Stmt__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5679:1: ( rule__While_Stmt__Group__1__Impl
			// rule__While_Stmt__Group__2 )
			// InternalStructuredTextParser.g:5680:2: rule__While_Stmt__Group__1__Impl
			// rule__While_Stmt__Group__2
			{
				pushFollow(FOLLOW_38);
				rule__While_Stmt__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__While_Stmt__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__Group__1"

	// $ANTLR start "rule__While_Stmt__Group__1__Impl"
	// InternalStructuredTextParser.g:5687:1: rule__While_Stmt__Group__1__Impl : ( (
	// rule__While_Stmt__ExpressionAssignment_1 ) ) ;
	public final void rule__While_Stmt__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5691:1: ( ( (
			// rule__While_Stmt__ExpressionAssignment_1 ) ) )
			// InternalStructuredTextParser.g:5692:1: ( (
			// rule__While_Stmt__ExpressionAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:5692:1: ( (
				// rule__While_Stmt__ExpressionAssignment_1 ) )
				// InternalStructuredTextParser.g:5693:2: (
				// rule__While_Stmt__ExpressionAssignment_1 )
				{
					before(grammarAccess.getWhile_StmtAccess().getExpressionAssignment_1());
					// InternalStructuredTextParser.g:5694:2: (
					// rule__While_Stmt__ExpressionAssignment_1 )
					// InternalStructuredTextParser.g:5694:3:
					// rule__While_Stmt__ExpressionAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__While_Stmt__ExpressionAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getWhile_StmtAccess().getExpressionAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__Group__1__Impl"

	// $ANTLR start "rule__While_Stmt__Group__2"
	// InternalStructuredTextParser.g:5702:1: rule__While_Stmt__Group__2 :
	// rule__While_Stmt__Group__2__Impl rule__While_Stmt__Group__3 ;
	public final void rule__While_Stmt__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5706:1: ( rule__While_Stmt__Group__2__Impl
			// rule__While_Stmt__Group__3 )
			// InternalStructuredTextParser.g:5707:2: rule__While_Stmt__Group__2__Impl
			// rule__While_Stmt__Group__3
			{
				pushFollow(FOLLOW_3);
				rule__While_Stmt__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__While_Stmt__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__Group__2"

	// $ANTLR start "rule__While_Stmt__Group__2__Impl"
	// InternalStructuredTextParser.g:5714:1: rule__While_Stmt__Group__2__Impl : (
	// DO ) ;
	public final void rule__While_Stmt__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5718:1: ( ( DO ) )
			// InternalStructuredTextParser.g:5719:1: ( DO )
			{
				// InternalStructuredTextParser.g:5719:1: ( DO )
				// InternalStructuredTextParser.g:5720:2: DO
				{
					before(grammarAccess.getWhile_StmtAccess().getDOKeyword_2());
					match(input, DO, FOLLOW_2);
					after(grammarAccess.getWhile_StmtAccess().getDOKeyword_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__Group__2__Impl"

	// $ANTLR start "rule__While_Stmt__Group__3"
	// InternalStructuredTextParser.g:5729:1: rule__While_Stmt__Group__3 :
	// rule__While_Stmt__Group__3__Impl rule__While_Stmt__Group__4 ;
	public final void rule__While_Stmt__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5733:1: ( rule__While_Stmt__Group__3__Impl
			// rule__While_Stmt__Group__4 )
			// InternalStructuredTextParser.g:5734:2: rule__While_Stmt__Group__3__Impl
			// rule__While_Stmt__Group__4
			{
				pushFollow(FOLLOW_39);
				rule__While_Stmt__Group__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__While_Stmt__Group__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__Group__3"

	// $ANTLR start "rule__While_Stmt__Group__3__Impl"
	// InternalStructuredTextParser.g:5741:1: rule__While_Stmt__Group__3__Impl : ( (
	// rule__While_Stmt__StatementsAssignment_3 ) ) ;
	public final void rule__While_Stmt__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5745:1: ( ( (
			// rule__While_Stmt__StatementsAssignment_3 ) ) )
			// InternalStructuredTextParser.g:5746:1: ( (
			// rule__While_Stmt__StatementsAssignment_3 ) )
			{
				// InternalStructuredTextParser.g:5746:1: ( (
				// rule__While_Stmt__StatementsAssignment_3 ) )
				// InternalStructuredTextParser.g:5747:2: (
				// rule__While_Stmt__StatementsAssignment_3 )
				{
					before(grammarAccess.getWhile_StmtAccess().getStatementsAssignment_3());
					// InternalStructuredTextParser.g:5748:2: (
					// rule__While_Stmt__StatementsAssignment_3 )
					// InternalStructuredTextParser.g:5748:3:
					// rule__While_Stmt__StatementsAssignment_3
					{
						pushFollow(FOLLOW_2);
						rule__While_Stmt__StatementsAssignment_3();

						state._fsp--;

					}

					after(grammarAccess.getWhile_StmtAccess().getStatementsAssignment_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__Group__3__Impl"

	// $ANTLR start "rule__While_Stmt__Group__4"
	// InternalStructuredTextParser.g:5756:1: rule__While_Stmt__Group__4 :
	// rule__While_Stmt__Group__4__Impl ;
	public final void rule__While_Stmt__Group__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5760:1: ( rule__While_Stmt__Group__4__Impl )
			// InternalStructuredTextParser.g:5761:2: rule__While_Stmt__Group__4__Impl
			{
				pushFollow(FOLLOW_2);
				rule__While_Stmt__Group__4__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__Group__4"

	// $ANTLR start "rule__While_Stmt__Group__4__Impl"
	// InternalStructuredTextParser.g:5767:1: rule__While_Stmt__Group__4__Impl : (
	// END_WHILE ) ;
	public final void rule__While_Stmt__Group__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5771:1: ( ( END_WHILE ) )
			// InternalStructuredTextParser.g:5772:1: ( END_WHILE )
			{
				// InternalStructuredTextParser.g:5772:1: ( END_WHILE )
				// InternalStructuredTextParser.g:5773:2: END_WHILE
				{
					before(grammarAccess.getWhile_StmtAccess().getEND_WHILEKeyword_4());
					match(input, END_WHILE, FOLLOW_2);
					after(grammarAccess.getWhile_StmtAccess().getEND_WHILEKeyword_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__Group__4__Impl"

	// $ANTLR start "rule__Repeat_Stmt__Group__0"
	// InternalStructuredTextParser.g:5783:1: rule__Repeat_Stmt__Group__0 :
	// rule__Repeat_Stmt__Group__0__Impl rule__Repeat_Stmt__Group__1 ;
	public final void rule__Repeat_Stmt__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5787:1: ( rule__Repeat_Stmt__Group__0__Impl
			// rule__Repeat_Stmt__Group__1 )
			// InternalStructuredTextParser.g:5788:2: rule__Repeat_Stmt__Group__0__Impl
			// rule__Repeat_Stmt__Group__1
			{
				pushFollow(FOLLOW_3);
				rule__Repeat_Stmt__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Repeat_Stmt__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__Group__0"

	// $ANTLR start "rule__Repeat_Stmt__Group__0__Impl"
	// InternalStructuredTextParser.g:5795:1: rule__Repeat_Stmt__Group__0__Impl : (
	// REPEAT ) ;
	public final void rule__Repeat_Stmt__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5799:1: ( ( REPEAT ) )
			// InternalStructuredTextParser.g:5800:1: ( REPEAT )
			{
				// InternalStructuredTextParser.g:5800:1: ( REPEAT )
				// InternalStructuredTextParser.g:5801:2: REPEAT
				{
					before(grammarAccess.getRepeat_StmtAccess().getREPEATKeyword_0());
					match(input, REPEAT, FOLLOW_2);
					after(grammarAccess.getRepeat_StmtAccess().getREPEATKeyword_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__Group__0__Impl"

	// $ANTLR start "rule__Repeat_Stmt__Group__1"
	// InternalStructuredTextParser.g:5810:1: rule__Repeat_Stmt__Group__1 :
	// rule__Repeat_Stmt__Group__1__Impl rule__Repeat_Stmt__Group__2 ;
	public final void rule__Repeat_Stmt__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5814:1: ( rule__Repeat_Stmt__Group__1__Impl
			// rule__Repeat_Stmt__Group__2 )
			// InternalStructuredTextParser.g:5815:2: rule__Repeat_Stmt__Group__1__Impl
			// rule__Repeat_Stmt__Group__2
			{
				pushFollow(FOLLOW_40);
				rule__Repeat_Stmt__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Repeat_Stmt__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__Group__1"

	// $ANTLR start "rule__Repeat_Stmt__Group__1__Impl"
	// InternalStructuredTextParser.g:5822:1: rule__Repeat_Stmt__Group__1__Impl : (
	// ( rule__Repeat_Stmt__StatementsAssignment_1 ) ) ;
	public final void rule__Repeat_Stmt__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5826:1: ( ( (
			// rule__Repeat_Stmt__StatementsAssignment_1 ) ) )
			// InternalStructuredTextParser.g:5827:1: ( (
			// rule__Repeat_Stmt__StatementsAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:5827:1: ( (
				// rule__Repeat_Stmt__StatementsAssignment_1 ) )
				// InternalStructuredTextParser.g:5828:2: (
				// rule__Repeat_Stmt__StatementsAssignment_1 )
				{
					before(grammarAccess.getRepeat_StmtAccess().getStatementsAssignment_1());
					// InternalStructuredTextParser.g:5829:2: (
					// rule__Repeat_Stmt__StatementsAssignment_1 )
					// InternalStructuredTextParser.g:5829:3:
					// rule__Repeat_Stmt__StatementsAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Repeat_Stmt__StatementsAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getRepeat_StmtAccess().getStatementsAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__Group__1__Impl"

	// $ANTLR start "rule__Repeat_Stmt__Group__2"
	// InternalStructuredTextParser.g:5837:1: rule__Repeat_Stmt__Group__2 :
	// rule__Repeat_Stmt__Group__2__Impl rule__Repeat_Stmt__Group__3 ;
	public final void rule__Repeat_Stmt__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5841:1: ( rule__Repeat_Stmt__Group__2__Impl
			// rule__Repeat_Stmt__Group__3 )
			// InternalStructuredTextParser.g:5842:2: rule__Repeat_Stmt__Group__2__Impl
			// rule__Repeat_Stmt__Group__3
			{
				pushFollow(FOLLOW_20);
				rule__Repeat_Stmt__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Repeat_Stmt__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__Group__2"

	// $ANTLR start "rule__Repeat_Stmt__Group__2__Impl"
	// InternalStructuredTextParser.g:5849:1: rule__Repeat_Stmt__Group__2__Impl : (
	// UNTIL ) ;
	public final void rule__Repeat_Stmt__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5853:1: ( ( UNTIL ) )
			// InternalStructuredTextParser.g:5854:1: ( UNTIL )
			{
				// InternalStructuredTextParser.g:5854:1: ( UNTIL )
				// InternalStructuredTextParser.g:5855:2: UNTIL
				{
					before(grammarAccess.getRepeat_StmtAccess().getUNTILKeyword_2());
					match(input, UNTIL, FOLLOW_2);
					after(grammarAccess.getRepeat_StmtAccess().getUNTILKeyword_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__Group__2__Impl"

	// $ANTLR start "rule__Repeat_Stmt__Group__3"
	// InternalStructuredTextParser.g:5864:1: rule__Repeat_Stmt__Group__3 :
	// rule__Repeat_Stmt__Group__3__Impl rule__Repeat_Stmt__Group__4 ;
	public final void rule__Repeat_Stmt__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5868:1: ( rule__Repeat_Stmt__Group__3__Impl
			// rule__Repeat_Stmt__Group__4 )
			// InternalStructuredTextParser.g:5869:2: rule__Repeat_Stmt__Group__3__Impl
			// rule__Repeat_Stmt__Group__4
			{
				pushFollow(FOLLOW_41);
				rule__Repeat_Stmt__Group__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Repeat_Stmt__Group__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__Group__3"

	// $ANTLR start "rule__Repeat_Stmt__Group__3__Impl"
	// InternalStructuredTextParser.g:5876:1: rule__Repeat_Stmt__Group__3__Impl : (
	// ( rule__Repeat_Stmt__ExpressionAssignment_3 ) ) ;
	public final void rule__Repeat_Stmt__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5880:1: ( ( (
			// rule__Repeat_Stmt__ExpressionAssignment_3 ) ) )
			// InternalStructuredTextParser.g:5881:1: ( (
			// rule__Repeat_Stmt__ExpressionAssignment_3 ) )
			{
				// InternalStructuredTextParser.g:5881:1: ( (
				// rule__Repeat_Stmt__ExpressionAssignment_3 ) )
				// InternalStructuredTextParser.g:5882:2: (
				// rule__Repeat_Stmt__ExpressionAssignment_3 )
				{
					before(grammarAccess.getRepeat_StmtAccess().getExpressionAssignment_3());
					// InternalStructuredTextParser.g:5883:2: (
					// rule__Repeat_Stmt__ExpressionAssignment_3 )
					// InternalStructuredTextParser.g:5883:3:
					// rule__Repeat_Stmt__ExpressionAssignment_3
					{
						pushFollow(FOLLOW_2);
						rule__Repeat_Stmt__ExpressionAssignment_3();

						state._fsp--;

					}

					after(grammarAccess.getRepeat_StmtAccess().getExpressionAssignment_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__Group__3__Impl"

	// $ANTLR start "rule__Repeat_Stmt__Group__4"
	// InternalStructuredTextParser.g:5891:1: rule__Repeat_Stmt__Group__4 :
	// rule__Repeat_Stmt__Group__4__Impl ;
	public final void rule__Repeat_Stmt__Group__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5895:1: ( rule__Repeat_Stmt__Group__4__Impl )
			// InternalStructuredTextParser.g:5896:2: rule__Repeat_Stmt__Group__4__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Repeat_Stmt__Group__4__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__Group__4"

	// $ANTLR start "rule__Repeat_Stmt__Group__4__Impl"
	// InternalStructuredTextParser.g:5902:1: rule__Repeat_Stmt__Group__4__Impl : (
	// END_REPEAT ) ;
	public final void rule__Repeat_Stmt__Group__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5906:1: ( ( END_REPEAT ) )
			// InternalStructuredTextParser.g:5907:1: ( END_REPEAT )
			{
				// InternalStructuredTextParser.g:5907:1: ( END_REPEAT )
				// InternalStructuredTextParser.g:5908:2: END_REPEAT
				{
					before(grammarAccess.getRepeat_StmtAccess().getEND_REPEATKeyword_4());
					match(input, END_REPEAT, FOLLOW_2);
					after(grammarAccess.getRepeat_StmtAccess().getEND_REPEATKeyword_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__Group__4__Impl"

	// $ANTLR start "rule__Or_Expression__Group__0"
	// InternalStructuredTextParser.g:5918:1: rule__Or_Expression__Group__0 :
	// rule__Or_Expression__Group__0__Impl rule__Or_Expression__Group__1 ;
	public final void rule__Or_Expression__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5922:1: ( rule__Or_Expression__Group__0__Impl
			// rule__Or_Expression__Group__1 )
			// InternalStructuredTextParser.g:5923:2: rule__Or_Expression__Group__0__Impl
			// rule__Or_Expression__Group__1
			{
				pushFollow(FOLLOW_42);
				rule__Or_Expression__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Or_Expression__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__Group__0"

	// $ANTLR start "rule__Or_Expression__Group__0__Impl"
	// InternalStructuredTextParser.g:5930:1: rule__Or_Expression__Group__0__Impl :
	// ( ruleXor_Expr ) ;
	public final void rule__Or_Expression__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5934:1: ( ( ruleXor_Expr ) )
			// InternalStructuredTextParser.g:5935:1: ( ruleXor_Expr )
			{
				// InternalStructuredTextParser.g:5935:1: ( ruleXor_Expr )
				// InternalStructuredTextParser.g:5936:2: ruleXor_Expr
				{
					before(grammarAccess.getOr_ExpressionAccess().getXor_ExprParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleXor_Expr();

					state._fsp--;

					after(grammarAccess.getOr_ExpressionAccess().getXor_ExprParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__Group__0__Impl"

	// $ANTLR start "rule__Or_Expression__Group__1"
	// InternalStructuredTextParser.g:5945:1: rule__Or_Expression__Group__1 :
	// rule__Or_Expression__Group__1__Impl ;
	public final void rule__Or_Expression__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5949:1: ( rule__Or_Expression__Group__1__Impl
			// )
			// InternalStructuredTextParser.g:5950:2: rule__Or_Expression__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Or_Expression__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__Group__1"

	// $ANTLR start "rule__Or_Expression__Group__1__Impl"
	// InternalStructuredTextParser.g:5956:1: rule__Or_Expression__Group__1__Impl :
	// ( ( rule__Or_Expression__Group_1__0 )* ) ;
	public final void rule__Or_Expression__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5960:1: ( ( ( rule__Or_Expression__Group_1__0
			// )* ) )
			// InternalStructuredTextParser.g:5961:1: ( ( rule__Or_Expression__Group_1__0 )*
			// )
			{
				// InternalStructuredTextParser.g:5961:1: ( ( rule__Or_Expression__Group_1__0 )*
				// )
				// InternalStructuredTextParser.g:5962:2: ( rule__Or_Expression__Group_1__0 )*
				{
					before(grammarAccess.getOr_ExpressionAccess().getGroup_1());
					// InternalStructuredTextParser.g:5963:2: ( rule__Or_Expression__Group_1__0 )*
					loop51: do {
						int alt51 = 2;
						int LA51_0 = input.LA(1);

						if ((LA51_0 == OR)) {
							alt51 = 1;
						}

						switch (alt51) {
						case 1:
						// InternalStructuredTextParser.g:5963:3: rule__Or_Expression__Group_1__0
						{
							pushFollow(FOLLOW_43);
							rule__Or_Expression__Group_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop51;
						}
					} while (true);

					after(grammarAccess.getOr_ExpressionAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__Group__1__Impl"

	// $ANTLR start "rule__Or_Expression__Group_1__0"
	// InternalStructuredTextParser.g:5972:1: rule__Or_Expression__Group_1__0 :
	// rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1 ;
	public final void rule__Or_Expression__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5976:1: (
			// rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1 )
			// InternalStructuredTextParser.g:5977:2: rule__Or_Expression__Group_1__0__Impl
			// rule__Or_Expression__Group_1__1
			{
				pushFollow(FOLLOW_42);
				rule__Or_Expression__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Or_Expression__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__Group_1__0"

	// $ANTLR start "rule__Or_Expression__Group_1__0__Impl"
	// InternalStructuredTextParser.g:5984:1: rule__Or_Expression__Group_1__0__Impl
	// : ( () ) ;
	public final void rule__Or_Expression__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:5988:1: ( ( () ) )
			// InternalStructuredTextParser.g:5989:1: ( () )
			{
				// InternalStructuredTextParser.g:5989:1: ( () )
				// InternalStructuredTextParser.g:5990:2: ()
				{
					before(grammarAccess.getOr_ExpressionAccess().getBinaryExpressionLeftAction_1_0());
					// InternalStructuredTextParser.g:5991:2: ()
					// InternalStructuredTextParser.g:5991:3:
					{
					}

					after(grammarAccess.getOr_ExpressionAccess().getBinaryExpressionLeftAction_1_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__Group_1__0__Impl"

	// $ANTLR start "rule__Or_Expression__Group_1__1"
	// InternalStructuredTextParser.g:5999:1: rule__Or_Expression__Group_1__1 :
	// rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2 ;
	public final void rule__Or_Expression__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6003:1: (
			// rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2 )
			// InternalStructuredTextParser.g:6004:2: rule__Or_Expression__Group_1__1__Impl
			// rule__Or_Expression__Group_1__2
			{
				pushFollow(FOLLOW_20);
				rule__Or_Expression__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Or_Expression__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__Group_1__1"

	// $ANTLR start "rule__Or_Expression__Group_1__1__Impl"
	// InternalStructuredTextParser.g:6011:1: rule__Or_Expression__Group_1__1__Impl
	// : ( ( rule__Or_Expression__OperatorAssignment_1_1 ) ) ;
	public final void rule__Or_Expression__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6015:1: ( ( (
			// rule__Or_Expression__OperatorAssignment_1_1 ) ) )
			// InternalStructuredTextParser.g:6016:1: ( (
			// rule__Or_Expression__OperatorAssignment_1_1 ) )
			{
				// InternalStructuredTextParser.g:6016:1: ( (
				// rule__Or_Expression__OperatorAssignment_1_1 ) )
				// InternalStructuredTextParser.g:6017:2: (
				// rule__Or_Expression__OperatorAssignment_1_1 )
				{
					before(grammarAccess.getOr_ExpressionAccess().getOperatorAssignment_1_1());
					// InternalStructuredTextParser.g:6018:2: (
					// rule__Or_Expression__OperatorAssignment_1_1 )
					// InternalStructuredTextParser.g:6018:3:
					// rule__Or_Expression__OperatorAssignment_1_1
					{
						pushFollow(FOLLOW_2);
						rule__Or_Expression__OperatorAssignment_1_1();

						state._fsp--;

					}

					after(grammarAccess.getOr_ExpressionAccess().getOperatorAssignment_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__Group_1__1__Impl"

	// $ANTLR start "rule__Or_Expression__Group_1__2"
	// InternalStructuredTextParser.g:6026:1: rule__Or_Expression__Group_1__2 :
	// rule__Or_Expression__Group_1__2__Impl ;
	public final void rule__Or_Expression__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6030:1: (
			// rule__Or_Expression__Group_1__2__Impl )
			// InternalStructuredTextParser.g:6031:2: rule__Or_Expression__Group_1__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Or_Expression__Group_1__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__Group_1__2"

	// $ANTLR start "rule__Or_Expression__Group_1__2__Impl"
	// InternalStructuredTextParser.g:6037:1: rule__Or_Expression__Group_1__2__Impl
	// : ( ( rule__Or_Expression__RightAssignment_1_2 ) ) ;
	public final void rule__Or_Expression__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6041:1: ( ( (
			// rule__Or_Expression__RightAssignment_1_2 ) ) )
			// InternalStructuredTextParser.g:6042:1: ( (
			// rule__Or_Expression__RightAssignment_1_2 ) )
			{
				// InternalStructuredTextParser.g:6042:1: ( (
				// rule__Or_Expression__RightAssignment_1_2 ) )
				// InternalStructuredTextParser.g:6043:2: (
				// rule__Or_Expression__RightAssignment_1_2 )
				{
					before(grammarAccess.getOr_ExpressionAccess().getRightAssignment_1_2());
					// InternalStructuredTextParser.g:6044:2: (
					// rule__Or_Expression__RightAssignment_1_2 )
					// InternalStructuredTextParser.g:6044:3:
					// rule__Or_Expression__RightAssignment_1_2
					{
						pushFollow(FOLLOW_2);
						rule__Or_Expression__RightAssignment_1_2();

						state._fsp--;

					}

					after(grammarAccess.getOr_ExpressionAccess().getRightAssignment_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__Group_1__2__Impl"

	// $ANTLR start "rule__Xor_Expr__Group__0"
	// InternalStructuredTextParser.g:6053:1: rule__Xor_Expr__Group__0 :
	// rule__Xor_Expr__Group__0__Impl rule__Xor_Expr__Group__1 ;
	public final void rule__Xor_Expr__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6057:1: ( rule__Xor_Expr__Group__0__Impl
			// rule__Xor_Expr__Group__1 )
			// InternalStructuredTextParser.g:6058:2: rule__Xor_Expr__Group__0__Impl
			// rule__Xor_Expr__Group__1
			{
				pushFollow(FOLLOW_44);
				rule__Xor_Expr__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Xor_Expr__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__Group__0"

	// $ANTLR start "rule__Xor_Expr__Group__0__Impl"
	// InternalStructuredTextParser.g:6065:1: rule__Xor_Expr__Group__0__Impl : (
	// ruleAnd_Expr ) ;
	public final void rule__Xor_Expr__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6069:1: ( ( ruleAnd_Expr ) )
			// InternalStructuredTextParser.g:6070:1: ( ruleAnd_Expr )
			{
				// InternalStructuredTextParser.g:6070:1: ( ruleAnd_Expr )
				// InternalStructuredTextParser.g:6071:2: ruleAnd_Expr
				{
					before(grammarAccess.getXor_ExprAccess().getAnd_ExprParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleAnd_Expr();

					state._fsp--;

					after(grammarAccess.getXor_ExprAccess().getAnd_ExprParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__Group__0__Impl"

	// $ANTLR start "rule__Xor_Expr__Group__1"
	// InternalStructuredTextParser.g:6080:1: rule__Xor_Expr__Group__1 :
	// rule__Xor_Expr__Group__1__Impl ;
	public final void rule__Xor_Expr__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6084:1: ( rule__Xor_Expr__Group__1__Impl )
			// InternalStructuredTextParser.g:6085:2: rule__Xor_Expr__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Xor_Expr__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__Group__1"

	// $ANTLR start "rule__Xor_Expr__Group__1__Impl"
	// InternalStructuredTextParser.g:6091:1: rule__Xor_Expr__Group__1__Impl : ( (
	// rule__Xor_Expr__Group_1__0 )* ) ;
	public final void rule__Xor_Expr__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6095:1: ( ( ( rule__Xor_Expr__Group_1__0 )* )
			// )
			// InternalStructuredTextParser.g:6096:1: ( ( rule__Xor_Expr__Group_1__0 )* )
			{
				// InternalStructuredTextParser.g:6096:1: ( ( rule__Xor_Expr__Group_1__0 )* )
				// InternalStructuredTextParser.g:6097:2: ( rule__Xor_Expr__Group_1__0 )*
				{
					before(grammarAccess.getXor_ExprAccess().getGroup_1());
					// InternalStructuredTextParser.g:6098:2: ( rule__Xor_Expr__Group_1__0 )*
					loop52: do {
						int alt52 = 2;
						int LA52_0 = input.LA(1);

						if ((LA52_0 == XOR)) {
							alt52 = 1;
						}

						switch (alt52) {
						case 1:
						// InternalStructuredTextParser.g:6098:3: rule__Xor_Expr__Group_1__0
						{
							pushFollow(FOLLOW_45);
							rule__Xor_Expr__Group_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop52;
						}
					} while (true);

					after(grammarAccess.getXor_ExprAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__Group__1__Impl"

	// $ANTLR start "rule__Xor_Expr__Group_1__0"
	// InternalStructuredTextParser.g:6107:1: rule__Xor_Expr__Group_1__0 :
	// rule__Xor_Expr__Group_1__0__Impl rule__Xor_Expr__Group_1__1 ;
	public final void rule__Xor_Expr__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6111:1: ( rule__Xor_Expr__Group_1__0__Impl
			// rule__Xor_Expr__Group_1__1 )
			// InternalStructuredTextParser.g:6112:2: rule__Xor_Expr__Group_1__0__Impl
			// rule__Xor_Expr__Group_1__1
			{
				pushFollow(FOLLOW_44);
				rule__Xor_Expr__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Xor_Expr__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__Group_1__0"

	// $ANTLR start "rule__Xor_Expr__Group_1__0__Impl"
	// InternalStructuredTextParser.g:6119:1: rule__Xor_Expr__Group_1__0__Impl : (
	// () ) ;
	public final void rule__Xor_Expr__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6123:1: ( ( () ) )
			// InternalStructuredTextParser.g:6124:1: ( () )
			{
				// InternalStructuredTextParser.g:6124:1: ( () )
				// InternalStructuredTextParser.g:6125:2: ()
				{
					before(grammarAccess.getXor_ExprAccess().getBinaryExpressionLeftAction_1_0());
					// InternalStructuredTextParser.g:6126:2: ()
					// InternalStructuredTextParser.g:6126:3:
					{
					}

					after(grammarAccess.getXor_ExprAccess().getBinaryExpressionLeftAction_1_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__Group_1__0__Impl"

	// $ANTLR start "rule__Xor_Expr__Group_1__1"
	// InternalStructuredTextParser.g:6134:1: rule__Xor_Expr__Group_1__1 :
	// rule__Xor_Expr__Group_1__1__Impl rule__Xor_Expr__Group_1__2 ;
	public final void rule__Xor_Expr__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6138:1: ( rule__Xor_Expr__Group_1__1__Impl
			// rule__Xor_Expr__Group_1__2 )
			// InternalStructuredTextParser.g:6139:2: rule__Xor_Expr__Group_1__1__Impl
			// rule__Xor_Expr__Group_1__2
			{
				pushFollow(FOLLOW_20);
				rule__Xor_Expr__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Xor_Expr__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__Group_1__1"

	// $ANTLR start "rule__Xor_Expr__Group_1__1__Impl"
	// InternalStructuredTextParser.g:6146:1: rule__Xor_Expr__Group_1__1__Impl : ( (
	// rule__Xor_Expr__OperatorAssignment_1_1 ) ) ;
	public final void rule__Xor_Expr__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6150:1: ( ( (
			// rule__Xor_Expr__OperatorAssignment_1_1 ) ) )
			// InternalStructuredTextParser.g:6151:1: ( (
			// rule__Xor_Expr__OperatorAssignment_1_1 ) )
			{
				// InternalStructuredTextParser.g:6151:1: ( (
				// rule__Xor_Expr__OperatorAssignment_1_1 ) )
				// InternalStructuredTextParser.g:6152:2: (
				// rule__Xor_Expr__OperatorAssignment_1_1 )
				{
					before(grammarAccess.getXor_ExprAccess().getOperatorAssignment_1_1());
					// InternalStructuredTextParser.g:6153:2: (
					// rule__Xor_Expr__OperatorAssignment_1_1 )
					// InternalStructuredTextParser.g:6153:3: rule__Xor_Expr__OperatorAssignment_1_1
					{
						pushFollow(FOLLOW_2);
						rule__Xor_Expr__OperatorAssignment_1_1();

						state._fsp--;

					}

					after(grammarAccess.getXor_ExprAccess().getOperatorAssignment_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__Group_1__1__Impl"

	// $ANTLR start "rule__Xor_Expr__Group_1__2"
	// InternalStructuredTextParser.g:6161:1: rule__Xor_Expr__Group_1__2 :
	// rule__Xor_Expr__Group_1__2__Impl ;
	public final void rule__Xor_Expr__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6165:1: ( rule__Xor_Expr__Group_1__2__Impl )
			// InternalStructuredTextParser.g:6166:2: rule__Xor_Expr__Group_1__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Xor_Expr__Group_1__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__Group_1__2"

	// $ANTLR start "rule__Xor_Expr__Group_1__2__Impl"
	// InternalStructuredTextParser.g:6172:1: rule__Xor_Expr__Group_1__2__Impl : ( (
	// rule__Xor_Expr__RightAssignment_1_2 ) ) ;
	public final void rule__Xor_Expr__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6176:1: ( ( (
			// rule__Xor_Expr__RightAssignment_1_2 ) ) )
			// InternalStructuredTextParser.g:6177:1: ( (
			// rule__Xor_Expr__RightAssignment_1_2 ) )
			{
				// InternalStructuredTextParser.g:6177:1: ( (
				// rule__Xor_Expr__RightAssignment_1_2 ) )
				// InternalStructuredTextParser.g:6178:2: ( rule__Xor_Expr__RightAssignment_1_2
				// )
				{
					before(grammarAccess.getXor_ExprAccess().getRightAssignment_1_2());
					// InternalStructuredTextParser.g:6179:2: ( rule__Xor_Expr__RightAssignment_1_2
					// )
					// InternalStructuredTextParser.g:6179:3: rule__Xor_Expr__RightAssignment_1_2
					{
						pushFollow(FOLLOW_2);
						rule__Xor_Expr__RightAssignment_1_2();

						state._fsp--;

					}

					after(grammarAccess.getXor_ExprAccess().getRightAssignment_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__Group_1__2__Impl"

	// $ANTLR start "rule__And_Expr__Group__0"
	// InternalStructuredTextParser.g:6188:1: rule__And_Expr__Group__0 :
	// rule__And_Expr__Group__0__Impl rule__And_Expr__Group__1 ;
	public final void rule__And_Expr__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6192:1: ( rule__And_Expr__Group__0__Impl
			// rule__And_Expr__Group__1 )
			// InternalStructuredTextParser.g:6193:2: rule__And_Expr__Group__0__Impl
			// rule__And_Expr__Group__1
			{
				pushFollow(FOLLOW_46);
				rule__And_Expr__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__And_Expr__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__Group__0"

	// $ANTLR start "rule__And_Expr__Group__0__Impl"
	// InternalStructuredTextParser.g:6200:1: rule__And_Expr__Group__0__Impl : (
	// ruleCompare_Expr ) ;
	public final void rule__And_Expr__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6204:1: ( ( ruleCompare_Expr ) )
			// InternalStructuredTextParser.g:6205:1: ( ruleCompare_Expr )
			{
				// InternalStructuredTextParser.g:6205:1: ( ruleCompare_Expr )
				// InternalStructuredTextParser.g:6206:2: ruleCompare_Expr
				{
					before(grammarAccess.getAnd_ExprAccess().getCompare_ExprParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleCompare_Expr();

					state._fsp--;

					after(grammarAccess.getAnd_ExprAccess().getCompare_ExprParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__Group__0__Impl"

	// $ANTLR start "rule__And_Expr__Group__1"
	// InternalStructuredTextParser.g:6215:1: rule__And_Expr__Group__1 :
	// rule__And_Expr__Group__1__Impl ;
	public final void rule__And_Expr__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6219:1: ( rule__And_Expr__Group__1__Impl )
			// InternalStructuredTextParser.g:6220:2: rule__And_Expr__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__And_Expr__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__Group__1"

	// $ANTLR start "rule__And_Expr__Group__1__Impl"
	// InternalStructuredTextParser.g:6226:1: rule__And_Expr__Group__1__Impl : ( (
	// rule__And_Expr__Group_1__0 )* ) ;
	public final void rule__And_Expr__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6230:1: ( ( ( rule__And_Expr__Group_1__0 )* )
			// )
			// InternalStructuredTextParser.g:6231:1: ( ( rule__And_Expr__Group_1__0 )* )
			{
				// InternalStructuredTextParser.g:6231:1: ( ( rule__And_Expr__Group_1__0 )* )
				// InternalStructuredTextParser.g:6232:2: ( rule__And_Expr__Group_1__0 )*
				{
					before(grammarAccess.getAnd_ExprAccess().getGroup_1());
					// InternalStructuredTextParser.g:6233:2: ( rule__And_Expr__Group_1__0 )*
					loop53: do {
						int alt53 = 2;
						int LA53_0 = input.LA(1);

						if ((LA53_0 == AND || LA53_0 == Ampersand)) {
							alt53 = 1;
						}

						switch (alt53) {
						case 1:
						// InternalStructuredTextParser.g:6233:3: rule__And_Expr__Group_1__0
						{
							pushFollow(FOLLOW_47);
							rule__And_Expr__Group_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop53;
						}
					} while (true);

					after(grammarAccess.getAnd_ExprAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__Group__1__Impl"

	// $ANTLR start "rule__And_Expr__Group_1__0"
	// InternalStructuredTextParser.g:6242:1: rule__And_Expr__Group_1__0 :
	// rule__And_Expr__Group_1__0__Impl rule__And_Expr__Group_1__1 ;
	public final void rule__And_Expr__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6246:1: ( rule__And_Expr__Group_1__0__Impl
			// rule__And_Expr__Group_1__1 )
			// InternalStructuredTextParser.g:6247:2: rule__And_Expr__Group_1__0__Impl
			// rule__And_Expr__Group_1__1
			{
				pushFollow(FOLLOW_46);
				rule__And_Expr__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__And_Expr__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__Group_1__0"

	// $ANTLR start "rule__And_Expr__Group_1__0__Impl"
	// InternalStructuredTextParser.g:6254:1: rule__And_Expr__Group_1__0__Impl : (
	// () ) ;
	public final void rule__And_Expr__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6258:1: ( ( () ) )
			// InternalStructuredTextParser.g:6259:1: ( () )
			{
				// InternalStructuredTextParser.g:6259:1: ( () )
				// InternalStructuredTextParser.g:6260:2: ()
				{
					before(grammarAccess.getAnd_ExprAccess().getBinaryExpressionLeftAction_1_0());
					// InternalStructuredTextParser.g:6261:2: ()
					// InternalStructuredTextParser.g:6261:3:
					{
					}

					after(grammarAccess.getAnd_ExprAccess().getBinaryExpressionLeftAction_1_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__Group_1__0__Impl"

	// $ANTLR start "rule__And_Expr__Group_1__1"
	// InternalStructuredTextParser.g:6269:1: rule__And_Expr__Group_1__1 :
	// rule__And_Expr__Group_1__1__Impl rule__And_Expr__Group_1__2 ;
	public final void rule__And_Expr__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6273:1: ( rule__And_Expr__Group_1__1__Impl
			// rule__And_Expr__Group_1__2 )
			// InternalStructuredTextParser.g:6274:2: rule__And_Expr__Group_1__1__Impl
			// rule__And_Expr__Group_1__2
			{
				pushFollow(FOLLOW_20);
				rule__And_Expr__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__And_Expr__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__Group_1__1"

	// $ANTLR start "rule__And_Expr__Group_1__1__Impl"
	// InternalStructuredTextParser.g:6281:1: rule__And_Expr__Group_1__1__Impl : ( (
	// rule__And_Expr__OperatorAssignment_1_1 ) ) ;
	public final void rule__And_Expr__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6285:1: ( ( (
			// rule__And_Expr__OperatorAssignment_1_1 ) ) )
			// InternalStructuredTextParser.g:6286:1: ( (
			// rule__And_Expr__OperatorAssignment_1_1 ) )
			{
				// InternalStructuredTextParser.g:6286:1: ( (
				// rule__And_Expr__OperatorAssignment_1_1 ) )
				// InternalStructuredTextParser.g:6287:2: (
				// rule__And_Expr__OperatorAssignment_1_1 )
				{
					before(grammarAccess.getAnd_ExprAccess().getOperatorAssignment_1_1());
					// InternalStructuredTextParser.g:6288:2: (
					// rule__And_Expr__OperatorAssignment_1_1 )
					// InternalStructuredTextParser.g:6288:3: rule__And_Expr__OperatorAssignment_1_1
					{
						pushFollow(FOLLOW_2);
						rule__And_Expr__OperatorAssignment_1_1();

						state._fsp--;

					}

					after(grammarAccess.getAnd_ExprAccess().getOperatorAssignment_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__Group_1__1__Impl"

	// $ANTLR start "rule__And_Expr__Group_1__2"
	// InternalStructuredTextParser.g:6296:1: rule__And_Expr__Group_1__2 :
	// rule__And_Expr__Group_1__2__Impl ;
	public final void rule__And_Expr__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6300:1: ( rule__And_Expr__Group_1__2__Impl )
			// InternalStructuredTextParser.g:6301:2: rule__And_Expr__Group_1__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__And_Expr__Group_1__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__Group_1__2"

	// $ANTLR start "rule__And_Expr__Group_1__2__Impl"
	// InternalStructuredTextParser.g:6307:1: rule__And_Expr__Group_1__2__Impl : ( (
	// rule__And_Expr__RightAssignment_1_2 ) ) ;
	public final void rule__And_Expr__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6311:1: ( ( (
			// rule__And_Expr__RightAssignment_1_2 ) ) )
			// InternalStructuredTextParser.g:6312:1: ( (
			// rule__And_Expr__RightAssignment_1_2 ) )
			{
				// InternalStructuredTextParser.g:6312:1: ( (
				// rule__And_Expr__RightAssignment_1_2 ) )
				// InternalStructuredTextParser.g:6313:2: ( rule__And_Expr__RightAssignment_1_2
				// )
				{
					before(grammarAccess.getAnd_ExprAccess().getRightAssignment_1_2());
					// InternalStructuredTextParser.g:6314:2: ( rule__And_Expr__RightAssignment_1_2
					// )
					// InternalStructuredTextParser.g:6314:3: rule__And_Expr__RightAssignment_1_2
					{
						pushFollow(FOLLOW_2);
						rule__And_Expr__RightAssignment_1_2();

						state._fsp--;

					}

					after(grammarAccess.getAnd_ExprAccess().getRightAssignment_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__Group_1__2__Impl"

	// $ANTLR start "rule__Compare_Expr__Group__0"
	// InternalStructuredTextParser.g:6323:1: rule__Compare_Expr__Group__0 :
	// rule__Compare_Expr__Group__0__Impl rule__Compare_Expr__Group__1 ;
	public final void rule__Compare_Expr__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6327:1: ( rule__Compare_Expr__Group__0__Impl
			// rule__Compare_Expr__Group__1 )
			// InternalStructuredTextParser.g:6328:2: rule__Compare_Expr__Group__0__Impl
			// rule__Compare_Expr__Group__1
			{
				pushFollow(FOLLOW_48);
				rule__Compare_Expr__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Compare_Expr__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__Group__0"

	// $ANTLR start "rule__Compare_Expr__Group__0__Impl"
	// InternalStructuredTextParser.g:6335:1: rule__Compare_Expr__Group__0__Impl : (
	// ruleEqu_Expr ) ;
	public final void rule__Compare_Expr__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6339:1: ( ( ruleEqu_Expr ) )
			// InternalStructuredTextParser.g:6340:1: ( ruleEqu_Expr )
			{
				// InternalStructuredTextParser.g:6340:1: ( ruleEqu_Expr )
				// InternalStructuredTextParser.g:6341:2: ruleEqu_Expr
				{
					before(grammarAccess.getCompare_ExprAccess().getEqu_ExprParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleEqu_Expr();

					state._fsp--;

					after(grammarAccess.getCompare_ExprAccess().getEqu_ExprParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__Group__0__Impl"

	// $ANTLR start "rule__Compare_Expr__Group__1"
	// InternalStructuredTextParser.g:6350:1: rule__Compare_Expr__Group__1 :
	// rule__Compare_Expr__Group__1__Impl ;
	public final void rule__Compare_Expr__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6354:1: ( rule__Compare_Expr__Group__1__Impl )
			// InternalStructuredTextParser.g:6355:2: rule__Compare_Expr__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Compare_Expr__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__Group__1"

	// $ANTLR start "rule__Compare_Expr__Group__1__Impl"
	// InternalStructuredTextParser.g:6361:1: rule__Compare_Expr__Group__1__Impl : (
	// ( rule__Compare_Expr__Group_1__0 )* ) ;
	public final void rule__Compare_Expr__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6365:1: ( ( ( rule__Compare_Expr__Group_1__0
			// )* ) )
			// InternalStructuredTextParser.g:6366:1: ( ( rule__Compare_Expr__Group_1__0 )*
			// )
			{
				// InternalStructuredTextParser.g:6366:1: ( ( rule__Compare_Expr__Group_1__0 )*
				// )
				// InternalStructuredTextParser.g:6367:2: ( rule__Compare_Expr__Group_1__0 )*
				{
					before(grammarAccess.getCompare_ExprAccess().getGroup_1());
					// InternalStructuredTextParser.g:6368:2: ( rule__Compare_Expr__Group_1__0 )*
					loop54: do {
						int alt54 = 2;
						int LA54_0 = input.LA(1);

						if ((LA54_0 == LessThanSignGreaterThanSign || LA54_0 == EqualsSign)) {
							alt54 = 1;
						}

						switch (alt54) {
						case 1:
						// InternalStructuredTextParser.g:6368:3: rule__Compare_Expr__Group_1__0
						{
							pushFollow(FOLLOW_49);
							rule__Compare_Expr__Group_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop54;
						}
					} while (true);

					after(grammarAccess.getCompare_ExprAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__Group__1__Impl"

	// $ANTLR start "rule__Compare_Expr__Group_1__0"
	// InternalStructuredTextParser.g:6377:1: rule__Compare_Expr__Group_1__0 :
	// rule__Compare_Expr__Group_1__0__Impl rule__Compare_Expr__Group_1__1 ;
	public final void rule__Compare_Expr__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6381:1: ( rule__Compare_Expr__Group_1__0__Impl
			// rule__Compare_Expr__Group_1__1 )
			// InternalStructuredTextParser.g:6382:2: rule__Compare_Expr__Group_1__0__Impl
			// rule__Compare_Expr__Group_1__1
			{
				pushFollow(FOLLOW_48);
				rule__Compare_Expr__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Compare_Expr__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__Group_1__0"

	// $ANTLR start "rule__Compare_Expr__Group_1__0__Impl"
	// InternalStructuredTextParser.g:6389:1: rule__Compare_Expr__Group_1__0__Impl :
	// ( () ) ;
	public final void rule__Compare_Expr__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6393:1: ( ( () ) )
			// InternalStructuredTextParser.g:6394:1: ( () )
			{
				// InternalStructuredTextParser.g:6394:1: ( () )
				// InternalStructuredTextParser.g:6395:2: ()
				{
					before(grammarAccess.getCompare_ExprAccess().getBinaryExpressionLeftAction_1_0());
					// InternalStructuredTextParser.g:6396:2: ()
					// InternalStructuredTextParser.g:6396:3:
					{
					}

					after(grammarAccess.getCompare_ExprAccess().getBinaryExpressionLeftAction_1_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__Group_1__0__Impl"

	// $ANTLR start "rule__Compare_Expr__Group_1__1"
	// InternalStructuredTextParser.g:6404:1: rule__Compare_Expr__Group_1__1 :
	// rule__Compare_Expr__Group_1__1__Impl rule__Compare_Expr__Group_1__2 ;
	public final void rule__Compare_Expr__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6408:1: ( rule__Compare_Expr__Group_1__1__Impl
			// rule__Compare_Expr__Group_1__2 )
			// InternalStructuredTextParser.g:6409:2: rule__Compare_Expr__Group_1__1__Impl
			// rule__Compare_Expr__Group_1__2
			{
				pushFollow(FOLLOW_20);
				rule__Compare_Expr__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Compare_Expr__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__Group_1__1"

	// $ANTLR start "rule__Compare_Expr__Group_1__1__Impl"
	// InternalStructuredTextParser.g:6416:1: rule__Compare_Expr__Group_1__1__Impl :
	// ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) ) ;
	public final void rule__Compare_Expr__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6420:1: ( ( (
			// rule__Compare_Expr__OperatorAssignment_1_1 ) ) )
			// InternalStructuredTextParser.g:6421:1: ( (
			// rule__Compare_Expr__OperatorAssignment_1_1 ) )
			{
				// InternalStructuredTextParser.g:6421:1: ( (
				// rule__Compare_Expr__OperatorAssignment_1_1 ) )
				// InternalStructuredTextParser.g:6422:2: (
				// rule__Compare_Expr__OperatorAssignment_1_1 )
				{
					before(grammarAccess.getCompare_ExprAccess().getOperatorAssignment_1_1());
					// InternalStructuredTextParser.g:6423:2: (
					// rule__Compare_Expr__OperatorAssignment_1_1 )
					// InternalStructuredTextParser.g:6423:3:
					// rule__Compare_Expr__OperatorAssignment_1_1
					{
						pushFollow(FOLLOW_2);
						rule__Compare_Expr__OperatorAssignment_1_1();

						state._fsp--;

					}

					after(grammarAccess.getCompare_ExprAccess().getOperatorAssignment_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__Group_1__1__Impl"

	// $ANTLR start "rule__Compare_Expr__Group_1__2"
	// InternalStructuredTextParser.g:6431:1: rule__Compare_Expr__Group_1__2 :
	// rule__Compare_Expr__Group_1__2__Impl ;
	public final void rule__Compare_Expr__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6435:1: ( rule__Compare_Expr__Group_1__2__Impl
			// )
			// InternalStructuredTextParser.g:6436:2: rule__Compare_Expr__Group_1__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Compare_Expr__Group_1__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__Group_1__2"

	// $ANTLR start "rule__Compare_Expr__Group_1__2__Impl"
	// InternalStructuredTextParser.g:6442:1: rule__Compare_Expr__Group_1__2__Impl :
	// ( ( rule__Compare_Expr__RightAssignment_1_2 ) ) ;
	public final void rule__Compare_Expr__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6446:1: ( ( (
			// rule__Compare_Expr__RightAssignment_1_2 ) ) )
			// InternalStructuredTextParser.g:6447:1: ( (
			// rule__Compare_Expr__RightAssignment_1_2 ) )
			{
				// InternalStructuredTextParser.g:6447:1: ( (
				// rule__Compare_Expr__RightAssignment_1_2 ) )
				// InternalStructuredTextParser.g:6448:2: (
				// rule__Compare_Expr__RightAssignment_1_2 )
				{
					before(grammarAccess.getCompare_ExprAccess().getRightAssignment_1_2());
					// InternalStructuredTextParser.g:6449:2: (
					// rule__Compare_Expr__RightAssignment_1_2 )
					// InternalStructuredTextParser.g:6449:3:
					// rule__Compare_Expr__RightAssignment_1_2
					{
						pushFollow(FOLLOW_2);
						rule__Compare_Expr__RightAssignment_1_2();

						state._fsp--;

					}

					after(grammarAccess.getCompare_ExprAccess().getRightAssignment_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__Group_1__2__Impl"

	// $ANTLR start "rule__Equ_Expr__Group__0"
	// InternalStructuredTextParser.g:6458:1: rule__Equ_Expr__Group__0 :
	// rule__Equ_Expr__Group__0__Impl rule__Equ_Expr__Group__1 ;
	public final void rule__Equ_Expr__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6462:1: ( rule__Equ_Expr__Group__0__Impl
			// rule__Equ_Expr__Group__1 )
			// InternalStructuredTextParser.g:6463:2: rule__Equ_Expr__Group__0__Impl
			// rule__Equ_Expr__Group__1
			{
				pushFollow(FOLLOW_50);
				rule__Equ_Expr__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Equ_Expr__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__Group__0"

	// $ANTLR start "rule__Equ_Expr__Group__0__Impl"
	// InternalStructuredTextParser.g:6470:1: rule__Equ_Expr__Group__0__Impl : (
	// ruleAdd_Expr ) ;
	public final void rule__Equ_Expr__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6474:1: ( ( ruleAdd_Expr ) )
			// InternalStructuredTextParser.g:6475:1: ( ruleAdd_Expr )
			{
				// InternalStructuredTextParser.g:6475:1: ( ruleAdd_Expr )
				// InternalStructuredTextParser.g:6476:2: ruleAdd_Expr
				{
					before(grammarAccess.getEqu_ExprAccess().getAdd_ExprParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleAdd_Expr();

					state._fsp--;

					after(grammarAccess.getEqu_ExprAccess().getAdd_ExprParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__Group__0__Impl"

	// $ANTLR start "rule__Equ_Expr__Group__1"
	// InternalStructuredTextParser.g:6485:1: rule__Equ_Expr__Group__1 :
	// rule__Equ_Expr__Group__1__Impl ;
	public final void rule__Equ_Expr__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6489:1: ( rule__Equ_Expr__Group__1__Impl )
			// InternalStructuredTextParser.g:6490:2: rule__Equ_Expr__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Equ_Expr__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__Group__1"

	// $ANTLR start "rule__Equ_Expr__Group__1__Impl"
	// InternalStructuredTextParser.g:6496:1: rule__Equ_Expr__Group__1__Impl : ( (
	// rule__Equ_Expr__Group_1__0 )* ) ;
	public final void rule__Equ_Expr__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6500:1: ( ( ( rule__Equ_Expr__Group_1__0 )* )
			// )
			// InternalStructuredTextParser.g:6501:1: ( ( rule__Equ_Expr__Group_1__0 )* )
			{
				// InternalStructuredTextParser.g:6501:1: ( ( rule__Equ_Expr__Group_1__0 )* )
				// InternalStructuredTextParser.g:6502:2: ( rule__Equ_Expr__Group_1__0 )*
				{
					before(grammarAccess.getEqu_ExprAccess().getGroup_1());
					// InternalStructuredTextParser.g:6503:2: ( rule__Equ_Expr__Group_1__0 )*
					loop55: do {
						int alt55 = 2;
						int LA55_0 = input.LA(1);

						if ((LA55_0 == LessThanSignEqualsSign || LA55_0 == GreaterThanSignEqualsSign
								|| LA55_0 == LessThanSign || LA55_0 == GreaterThanSign)) {
							alt55 = 1;
						}

						switch (alt55) {
						case 1:
						// InternalStructuredTextParser.g:6503:3: rule__Equ_Expr__Group_1__0
						{
							pushFollow(FOLLOW_51);
							rule__Equ_Expr__Group_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop55;
						}
					} while (true);

					after(grammarAccess.getEqu_ExprAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__Group__1__Impl"

	// $ANTLR start "rule__Equ_Expr__Group_1__0"
	// InternalStructuredTextParser.g:6512:1: rule__Equ_Expr__Group_1__0 :
	// rule__Equ_Expr__Group_1__0__Impl rule__Equ_Expr__Group_1__1 ;
	public final void rule__Equ_Expr__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6516:1: ( rule__Equ_Expr__Group_1__0__Impl
			// rule__Equ_Expr__Group_1__1 )
			// InternalStructuredTextParser.g:6517:2: rule__Equ_Expr__Group_1__0__Impl
			// rule__Equ_Expr__Group_1__1
			{
				pushFollow(FOLLOW_50);
				rule__Equ_Expr__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Equ_Expr__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__Group_1__0"

	// $ANTLR start "rule__Equ_Expr__Group_1__0__Impl"
	// InternalStructuredTextParser.g:6524:1: rule__Equ_Expr__Group_1__0__Impl : (
	// () ) ;
	public final void rule__Equ_Expr__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6528:1: ( ( () ) )
			// InternalStructuredTextParser.g:6529:1: ( () )
			{
				// InternalStructuredTextParser.g:6529:1: ( () )
				// InternalStructuredTextParser.g:6530:2: ()
				{
					before(grammarAccess.getEqu_ExprAccess().getBinaryExpressionLeftAction_1_0());
					// InternalStructuredTextParser.g:6531:2: ()
					// InternalStructuredTextParser.g:6531:3:
					{
					}

					after(grammarAccess.getEqu_ExprAccess().getBinaryExpressionLeftAction_1_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__Group_1__0__Impl"

	// $ANTLR start "rule__Equ_Expr__Group_1__1"
	// InternalStructuredTextParser.g:6539:1: rule__Equ_Expr__Group_1__1 :
	// rule__Equ_Expr__Group_1__1__Impl rule__Equ_Expr__Group_1__2 ;
	public final void rule__Equ_Expr__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6543:1: ( rule__Equ_Expr__Group_1__1__Impl
			// rule__Equ_Expr__Group_1__2 )
			// InternalStructuredTextParser.g:6544:2: rule__Equ_Expr__Group_1__1__Impl
			// rule__Equ_Expr__Group_1__2
			{
				pushFollow(FOLLOW_20);
				rule__Equ_Expr__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Equ_Expr__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__Group_1__1"

	// $ANTLR start "rule__Equ_Expr__Group_1__1__Impl"
	// InternalStructuredTextParser.g:6551:1: rule__Equ_Expr__Group_1__1__Impl : ( (
	// rule__Equ_Expr__OperatorAssignment_1_1 ) ) ;
	public final void rule__Equ_Expr__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6555:1: ( ( (
			// rule__Equ_Expr__OperatorAssignment_1_1 ) ) )
			// InternalStructuredTextParser.g:6556:1: ( (
			// rule__Equ_Expr__OperatorAssignment_1_1 ) )
			{
				// InternalStructuredTextParser.g:6556:1: ( (
				// rule__Equ_Expr__OperatorAssignment_1_1 ) )
				// InternalStructuredTextParser.g:6557:2: (
				// rule__Equ_Expr__OperatorAssignment_1_1 )
				{
					before(grammarAccess.getEqu_ExprAccess().getOperatorAssignment_1_1());
					// InternalStructuredTextParser.g:6558:2: (
					// rule__Equ_Expr__OperatorAssignment_1_1 )
					// InternalStructuredTextParser.g:6558:3: rule__Equ_Expr__OperatorAssignment_1_1
					{
						pushFollow(FOLLOW_2);
						rule__Equ_Expr__OperatorAssignment_1_1();

						state._fsp--;

					}

					after(grammarAccess.getEqu_ExprAccess().getOperatorAssignment_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__Group_1__1__Impl"

	// $ANTLR start "rule__Equ_Expr__Group_1__2"
	// InternalStructuredTextParser.g:6566:1: rule__Equ_Expr__Group_1__2 :
	// rule__Equ_Expr__Group_1__2__Impl ;
	public final void rule__Equ_Expr__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6570:1: ( rule__Equ_Expr__Group_1__2__Impl )
			// InternalStructuredTextParser.g:6571:2: rule__Equ_Expr__Group_1__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Equ_Expr__Group_1__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__Group_1__2"

	// $ANTLR start "rule__Equ_Expr__Group_1__2__Impl"
	// InternalStructuredTextParser.g:6577:1: rule__Equ_Expr__Group_1__2__Impl : ( (
	// rule__Equ_Expr__RightAssignment_1_2 ) ) ;
	public final void rule__Equ_Expr__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6581:1: ( ( (
			// rule__Equ_Expr__RightAssignment_1_2 ) ) )
			// InternalStructuredTextParser.g:6582:1: ( (
			// rule__Equ_Expr__RightAssignment_1_2 ) )
			{
				// InternalStructuredTextParser.g:6582:1: ( (
				// rule__Equ_Expr__RightAssignment_1_2 ) )
				// InternalStructuredTextParser.g:6583:2: ( rule__Equ_Expr__RightAssignment_1_2
				// )
				{
					before(grammarAccess.getEqu_ExprAccess().getRightAssignment_1_2());
					// InternalStructuredTextParser.g:6584:2: ( rule__Equ_Expr__RightAssignment_1_2
					// )
					// InternalStructuredTextParser.g:6584:3: rule__Equ_Expr__RightAssignment_1_2
					{
						pushFollow(FOLLOW_2);
						rule__Equ_Expr__RightAssignment_1_2();

						state._fsp--;

					}

					after(grammarAccess.getEqu_ExprAccess().getRightAssignment_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__Group_1__2__Impl"

	// $ANTLR start "rule__Add_Expr__Group__0"
	// InternalStructuredTextParser.g:6593:1: rule__Add_Expr__Group__0 :
	// rule__Add_Expr__Group__0__Impl rule__Add_Expr__Group__1 ;
	public final void rule__Add_Expr__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6597:1: ( rule__Add_Expr__Group__0__Impl
			// rule__Add_Expr__Group__1 )
			// InternalStructuredTextParser.g:6598:2: rule__Add_Expr__Group__0__Impl
			// rule__Add_Expr__Group__1
			{
				pushFollow(FOLLOW_52);
				rule__Add_Expr__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Add_Expr__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__Group__0"

	// $ANTLR start "rule__Add_Expr__Group__0__Impl"
	// InternalStructuredTextParser.g:6605:1: rule__Add_Expr__Group__0__Impl : (
	// ruleTerm ) ;
	public final void rule__Add_Expr__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6609:1: ( ( ruleTerm ) )
			// InternalStructuredTextParser.g:6610:1: ( ruleTerm )
			{
				// InternalStructuredTextParser.g:6610:1: ( ruleTerm )
				// InternalStructuredTextParser.g:6611:2: ruleTerm
				{
					before(grammarAccess.getAdd_ExprAccess().getTermParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleTerm();

					state._fsp--;

					after(grammarAccess.getAdd_ExprAccess().getTermParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__Group__0__Impl"

	// $ANTLR start "rule__Add_Expr__Group__1"
	// InternalStructuredTextParser.g:6620:1: rule__Add_Expr__Group__1 :
	// rule__Add_Expr__Group__1__Impl ;
	public final void rule__Add_Expr__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6624:1: ( rule__Add_Expr__Group__1__Impl )
			// InternalStructuredTextParser.g:6625:2: rule__Add_Expr__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Add_Expr__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__Group__1"

	// $ANTLR start "rule__Add_Expr__Group__1__Impl"
	// InternalStructuredTextParser.g:6631:1: rule__Add_Expr__Group__1__Impl : ( (
	// rule__Add_Expr__Group_1__0 )* ) ;
	public final void rule__Add_Expr__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6635:1: ( ( ( rule__Add_Expr__Group_1__0 )* )
			// )
			// InternalStructuredTextParser.g:6636:1: ( ( rule__Add_Expr__Group_1__0 )* )
			{
				// InternalStructuredTextParser.g:6636:1: ( ( rule__Add_Expr__Group_1__0 )* )
				// InternalStructuredTextParser.g:6637:2: ( rule__Add_Expr__Group_1__0 )*
				{
					before(grammarAccess.getAdd_ExprAccess().getGroup_1());
					// InternalStructuredTextParser.g:6638:2: ( rule__Add_Expr__Group_1__0 )*
					loop56: do {
						int alt56 = 2;
						int LA56_0 = input.LA(1);

						if ((LA56_0 == PlusSign || LA56_0 == HyphenMinus)) {
							alt56 = 1;
						}

						switch (alt56) {
						case 1:
						// InternalStructuredTextParser.g:6638:3: rule__Add_Expr__Group_1__0
						{
							pushFollow(FOLLOW_53);
							rule__Add_Expr__Group_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop56;
						}
					} while (true);

					after(grammarAccess.getAdd_ExprAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__Group__1__Impl"

	// $ANTLR start "rule__Add_Expr__Group_1__0"
	// InternalStructuredTextParser.g:6647:1: rule__Add_Expr__Group_1__0 :
	// rule__Add_Expr__Group_1__0__Impl rule__Add_Expr__Group_1__1 ;
	public final void rule__Add_Expr__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6651:1: ( rule__Add_Expr__Group_1__0__Impl
			// rule__Add_Expr__Group_1__1 )
			// InternalStructuredTextParser.g:6652:2: rule__Add_Expr__Group_1__0__Impl
			// rule__Add_Expr__Group_1__1
			{
				pushFollow(FOLLOW_52);
				rule__Add_Expr__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Add_Expr__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__Group_1__0"

	// $ANTLR start "rule__Add_Expr__Group_1__0__Impl"
	// InternalStructuredTextParser.g:6659:1: rule__Add_Expr__Group_1__0__Impl : (
	// () ) ;
	public final void rule__Add_Expr__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6663:1: ( ( () ) )
			// InternalStructuredTextParser.g:6664:1: ( () )
			{
				// InternalStructuredTextParser.g:6664:1: ( () )
				// InternalStructuredTextParser.g:6665:2: ()
				{
					before(grammarAccess.getAdd_ExprAccess().getBinaryExpressionLeftAction_1_0());
					// InternalStructuredTextParser.g:6666:2: ()
					// InternalStructuredTextParser.g:6666:3:
					{
					}

					after(grammarAccess.getAdd_ExprAccess().getBinaryExpressionLeftAction_1_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__Group_1__0__Impl"

	// $ANTLR start "rule__Add_Expr__Group_1__1"
	// InternalStructuredTextParser.g:6674:1: rule__Add_Expr__Group_1__1 :
	// rule__Add_Expr__Group_1__1__Impl rule__Add_Expr__Group_1__2 ;
	public final void rule__Add_Expr__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6678:1: ( rule__Add_Expr__Group_1__1__Impl
			// rule__Add_Expr__Group_1__2 )
			// InternalStructuredTextParser.g:6679:2: rule__Add_Expr__Group_1__1__Impl
			// rule__Add_Expr__Group_1__2
			{
				pushFollow(FOLLOW_20);
				rule__Add_Expr__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Add_Expr__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__Group_1__1"

	// $ANTLR start "rule__Add_Expr__Group_1__1__Impl"
	// InternalStructuredTextParser.g:6686:1: rule__Add_Expr__Group_1__1__Impl : ( (
	// rule__Add_Expr__OperatorAssignment_1_1 ) ) ;
	public final void rule__Add_Expr__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6690:1: ( ( (
			// rule__Add_Expr__OperatorAssignment_1_1 ) ) )
			// InternalStructuredTextParser.g:6691:1: ( (
			// rule__Add_Expr__OperatorAssignment_1_1 ) )
			{
				// InternalStructuredTextParser.g:6691:1: ( (
				// rule__Add_Expr__OperatorAssignment_1_1 ) )
				// InternalStructuredTextParser.g:6692:2: (
				// rule__Add_Expr__OperatorAssignment_1_1 )
				{
					before(grammarAccess.getAdd_ExprAccess().getOperatorAssignment_1_1());
					// InternalStructuredTextParser.g:6693:2: (
					// rule__Add_Expr__OperatorAssignment_1_1 )
					// InternalStructuredTextParser.g:6693:3: rule__Add_Expr__OperatorAssignment_1_1
					{
						pushFollow(FOLLOW_2);
						rule__Add_Expr__OperatorAssignment_1_1();

						state._fsp--;

					}

					after(grammarAccess.getAdd_ExprAccess().getOperatorAssignment_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__Group_1__1__Impl"

	// $ANTLR start "rule__Add_Expr__Group_1__2"
	// InternalStructuredTextParser.g:6701:1: rule__Add_Expr__Group_1__2 :
	// rule__Add_Expr__Group_1__2__Impl ;
	public final void rule__Add_Expr__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6705:1: ( rule__Add_Expr__Group_1__2__Impl )
			// InternalStructuredTextParser.g:6706:2: rule__Add_Expr__Group_1__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Add_Expr__Group_1__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__Group_1__2"

	// $ANTLR start "rule__Add_Expr__Group_1__2__Impl"
	// InternalStructuredTextParser.g:6712:1: rule__Add_Expr__Group_1__2__Impl : ( (
	// rule__Add_Expr__RightAssignment_1_2 ) ) ;
	public final void rule__Add_Expr__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6716:1: ( ( (
			// rule__Add_Expr__RightAssignment_1_2 ) ) )
			// InternalStructuredTextParser.g:6717:1: ( (
			// rule__Add_Expr__RightAssignment_1_2 ) )
			{
				// InternalStructuredTextParser.g:6717:1: ( (
				// rule__Add_Expr__RightAssignment_1_2 ) )
				// InternalStructuredTextParser.g:6718:2: ( rule__Add_Expr__RightAssignment_1_2
				// )
				{
					before(grammarAccess.getAdd_ExprAccess().getRightAssignment_1_2());
					// InternalStructuredTextParser.g:6719:2: ( rule__Add_Expr__RightAssignment_1_2
					// )
					// InternalStructuredTextParser.g:6719:3: rule__Add_Expr__RightAssignment_1_2
					{
						pushFollow(FOLLOW_2);
						rule__Add_Expr__RightAssignment_1_2();

						state._fsp--;

					}

					after(grammarAccess.getAdd_ExprAccess().getRightAssignment_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__Group_1__2__Impl"

	// $ANTLR start "rule__Term__Group__0"
	// InternalStructuredTextParser.g:6728:1: rule__Term__Group__0 :
	// rule__Term__Group__0__Impl rule__Term__Group__1 ;
	public final void rule__Term__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6732:1: ( rule__Term__Group__0__Impl
			// rule__Term__Group__1 )
			// InternalStructuredTextParser.g:6733:2: rule__Term__Group__0__Impl
			// rule__Term__Group__1
			{
				pushFollow(FOLLOW_54);
				rule__Term__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Term__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__Group__0"

	// $ANTLR start "rule__Term__Group__0__Impl"
	// InternalStructuredTextParser.g:6740:1: rule__Term__Group__0__Impl : (
	// rulePower_Expr ) ;
	public final void rule__Term__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6744:1: ( ( rulePower_Expr ) )
			// InternalStructuredTextParser.g:6745:1: ( rulePower_Expr )
			{
				// InternalStructuredTextParser.g:6745:1: ( rulePower_Expr )
				// InternalStructuredTextParser.g:6746:2: rulePower_Expr
				{
					before(grammarAccess.getTermAccess().getPower_ExprParserRuleCall_0());
					pushFollow(FOLLOW_2);
					rulePower_Expr();

					state._fsp--;

					after(grammarAccess.getTermAccess().getPower_ExprParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__Group__0__Impl"

	// $ANTLR start "rule__Term__Group__1"
	// InternalStructuredTextParser.g:6755:1: rule__Term__Group__1 :
	// rule__Term__Group__1__Impl ;
	public final void rule__Term__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6759:1: ( rule__Term__Group__1__Impl )
			// InternalStructuredTextParser.g:6760:2: rule__Term__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Term__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__Group__1"

	// $ANTLR start "rule__Term__Group__1__Impl"
	// InternalStructuredTextParser.g:6766:1: rule__Term__Group__1__Impl : ( (
	// rule__Term__Group_1__0 )* ) ;
	public final void rule__Term__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6770:1: ( ( ( rule__Term__Group_1__0 )* ) )
			// InternalStructuredTextParser.g:6771:1: ( ( rule__Term__Group_1__0 )* )
			{
				// InternalStructuredTextParser.g:6771:1: ( ( rule__Term__Group_1__0 )* )
				// InternalStructuredTextParser.g:6772:2: ( rule__Term__Group_1__0 )*
				{
					before(grammarAccess.getTermAccess().getGroup_1());
					// InternalStructuredTextParser.g:6773:2: ( rule__Term__Group_1__0 )*
					loop57: do {
						int alt57 = 2;
						int LA57_0 = input.LA(1);

						if ((LA57_0 == MOD || LA57_0 == Asterisk || LA57_0 == Solidus)) {
							alt57 = 1;
						}

						switch (alt57) {
						case 1:
						// InternalStructuredTextParser.g:6773:3: rule__Term__Group_1__0
						{
							pushFollow(FOLLOW_55);
							rule__Term__Group_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop57;
						}
					} while (true);

					after(grammarAccess.getTermAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__Group__1__Impl"

	// $ANTLR start "rule__Term__Group_1__0"
	// InternalStructuredTextParser.g:6782:1: rule__Term__Group_1__0 :
	// rule__Term__Group_1__0__Impl rule__Term__Group_1__1 ;
	public final void rule__Term__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6786:1: ( rule__Term__Group_1__0__Impl
			// rule__Term__Group_1__1 )
			// InternalStructuredTextParser.g:6787:2: rule__Term__Group_1__0__Impl
			// rule__Term__Group_1__1
			{
				pushFollow(FOLLOW_54);
				rule__Term__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Term__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__Group_1__0"

	// $ANTLR start "rule__Term__Group_1__0__Impl"
	// InternalStructuredTextParser.g:6794:1: rule__Term__Group_1__0__Impl : ( () )
	// ;
	public final void rule__Term__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6798:1: ( ( () ) )
			// InternalStructuredTextParser.g:6799:1: ( () )
			{
				// InternalStructuredTextParser.g:6799:1: ( () )
				// InternalStructuredTextParser.g:6800:2: ()
				{
					before(grammarAccess.getTermAccess().getBinaryExpressionLeftAction_1_0());
					// InternalStructuredTextParser.g:6801:2: ()
					// InternalStructuredTextParser.g:6801:3:
					{
					}

					after(grammarAccess.getTermAccess().getBinaryExpressionLeftAction_1_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__Group_1__0__Impl"

	// $ANTLR start "rule__Term__Group_1__1"
	// InternalStructuredTextParser.g:6809:1: rule__Term__Group_1__1 :
	// rule__Term__Group_1__1__Impl rule__Term__Group_1__2 ;
	public final void rule__Term__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6813:1: ( rule__Term__Group_1__1__Impl
			// rule__Term__Group_1__2 )
			// InternalStructuredTextParser.g:6814:2: rule__Term__Group_1__1__Impl
			// rule__Term__Group_1__2
			{
				pushFollow(FOLLOW_20);
				rule__Term__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Term__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__Group_1__1"

	// $ANTLR start "rule__Term__Group_1__1__Impl"
	// InternalStructuredTextParser.g:6821:1: rule__Term__Group_1__1__Impl : ( (
	// rule__Term__OperatorAssignment_1_1 ) ) ;
	public final void rule__Term__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6825:1: ( ( (
			// rule__Term__OperatorAssignment_1_1 ) ) )
			// InternalStructuredTextParser.g:6826:1: ( ( rule__Term__OperatorAssignment_1_1
			// ) )
			{
				// InternalStructuredTextParser.g:6826:1: ( ( rule__Term__OperatorAssignment_1_1
				// ) )
				// InternalStructuredTextParser.g:6827:2: ( rule__Term__OperatorAssignment_1_1 )
				{
					before(grammarAccess.getTermAccess().getOperatorAssignment_1_1());
					// InternalStructuredTextParser.g:6828:2: ( rule__Term__OperatorAssignment_1_1 )
					// InternalStructuredTextParser.g:6828:3: rule__Term__OperatorAssignment_1_1
					{
						pushFollow(FOLLOW_2);
						rule__Term__OperatorAssignment_1_1();

						state._fsp--;

					}

					after(grammarAccess.getTermAccess().getOperatorAssignment_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__Group_1__1__Impl"

	// $ANTLR start "rule__Term__Group_1__2"
	// InternalStructuredTextParser.g:6836:1: rule__Term__Group_1__2 :
	// rule__Term__Group_1__2__Impl ;
	public final void rule__Term__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6840:1: ( rule__Term__Group_1__2__Impl )
			// InternalStructuredTextParser.g:6841:2: rule__Term__Group_1__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Term__Group_1__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__Group_1__2"

	// $ANTLR start "rule__Term__Group_1__2__Impl"
	// InternalStructuredTextParser.g:6847:1: rule__Term__Group_1__2__Impl : ( (
	// rule__Term__RightAssignment_1_2 ) ) ;
	public final void rule__Term__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6851:1: ( ( ( rule__Term__RightAssignment_1_2
			// ) ) )
			// InternalStructuredTextParser.g:6852:1: ( ( rule__Term__RightAssignment_1_2 )
			// )
			{
				// InternalStructuredTextParser.g:6852:1: ( ( rule__Term__RightAssignment_1_2 )
				// )
				// InternalStructuredTextParser.g:6853:2: ( rule__Term__RightAssignment_1_2 )
				{
					before(grammarAccess.getTermAccess().getRightAssignment_1_2());
					// InternalStructuredTextParser.g:6854:2: ( rule__Term__RightAssignment_1_2 )
					// InternalStructuredTextParser.g:6854:3: rule__Term__RightAssignment_1_2
					{
						pushFollow(FOLLOW_2);
						rule__Term__RightAssignment_1_2();

						state._fsp--;

					}

					after(grammarAccess.getTermAccess().getRightAssignment_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__Group_1__2__Impl"

	// $ANTLR start "rule__Power_Expr__Group__0"
	// InternalStructuredTextParser.g:6863:1: rule__Power_Expr__Group__0 :
	// rule__Power_Expr__Group__0__Impl rule__Power_Expr__Group__1 ;
	public final void rule__Power_Expr__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6867:1: ( rule__Power_Expr__Group__0__Impl
			// rule__Power_Expr__Group__1 )
			// InternalStructuredTextParser.g:6868:2: rule__Power_Expr__Group__0__Impl
			// rule__Power_Expr__Group__1
			{
				pushFollow(FOLLOW_56);
				rule__Power_Expr__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Power_Expr__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__Group__0"

	// $ANTLR start "rule__Power_Expr__Group__0__Impl"
	// InternalStructuredTextParser.g:6875:1: rule__Power_Expr__Group__0__Impl : (
	// ruleUnary_Expr ) ;
	public final void rule__Power_Expr__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6879:1: ( ( ruleUnary_Expr ) )
			// InternalStructuredTextParser.g:6880:1: ( ruleUnary_Expr )
			{
				// InternalStructuredTextParser.g:6880:1: ( ruleUnary_Expr )
				// InternalStructuredTextParser.g:6881:2: ruleUnary_Expr
				{
					before(grammarAccess.getPower_ExprAccess().getUnary_ExprParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleUnary_Expr();

					state._fsp--;

					after(grammarAccess.getPower_ExprAccess().getUnary_ExprParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__Group__0__Impl"

	// $ANTLR start "rule__Power_Expr__Group__1"
	// InternalStructuredTextParser.g:6890:1: rule__Power_Expr__Group__1 :
	// rule__Power_Expr__Group__1__Impl ;
	public final void rule__Power_Expr__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6894:1: ( rule__Power_Expr__Group__1__Impl )
			// InternalStructuredTextParser.g:6895:2: rule__Power_Expr__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Power_Expr__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__Group__1"

	// $ANTLR start "rule__Power_Expr__Group__1__Impl"
	// InternalStructuredTextParser.g:6901:1: rule__Power_Expr__Group__1__Impl : ( (
	// rule__Power_Expr__Group_1__0 )* ) ;
	public final void rule__Power_Expr__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6905:1: ( ( ( rule__Power_Expr__Group_1__0 )*
			// ) )
			// InternalStructuredTextParser.g:6906:1: ( ( rule__Power_Expr__Group_1__0 )* )
			{
				// InternalStructuredTextParser.g:6906:1: ( ( rule__Power_Expr__Group_1__0 )* )
				// InternalStructuredTextParser.g:6907:2: ( rule__Power_Expr__Group_1__0 )*
				{
					before(grammarAccess.getPower_ExprAccess().getGroup_1());
					// InternalStructuredTextParser.g:6908:2: ( rule__Power_Expr__Group_1__0 )*
					loop58: do {
						int alt58 = 2;
						int LA58_0 = input.LA(1);

						if ((LA58_0 == AsteriskAsterisk)) {
							alt58 = 1;
						}

						switch (alt58) {
						case 1:
						// InternalStructuredTextParser.g:6908:3: rule__Power_Expr__Group_1__0
						{
							pushFollow(FOLLOW_57);
							rule__Power_Expr__Group_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop58;
						}
					} while (true);

					after(grammarAccess.getPower_ExprAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__Group__1__Impl"

	// $ANTLR start "rule__Power_Expr__Group_1__0"
	// InternalStructuredTextParser.g:6917:1: rule__Power_Expr__Group_1__0 :
	// rule__Power_Expr__Group_1__0__Impl rule__Power_Expr__Group_1__1 ;
	public final void rule__Power_Expr__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6921:1: ( rule__Power_Expr__Group_1__0__Impl
			// rule__Power_Expr__Group_1__1 )
			// InternalStructuredTextParser.g:6922:2: rule__Power_Expr__Group_1__0__Impl
			// rule__Power_Expr__Group_1__1
			{
				pushFollow(FOLLOW_56);
				rule__Power_Expr__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Power_Expr__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__Group_1__0"

	// $ANTLR start "rule__Power_Expr__Group_1__0__Impl"
	// InternalStructuredTextParser.g:6929:1: rule__Power_Expr__Group_1__0__Impl : (
	// () ) ;
	public final void rule__Power_Expr__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6933:1: ( ( () ) )
			// InternalStructuredTextParser.g:6934:1: ( () )
			{
				// InternalStructuredTextParser.g:6934:1: ( () )
				// InternalStructuredTextParser.g:6935:2: ()
				{
					before(grammarAccess.getPower_ExprAccess().getBinaryExpressionLeftAction_1_0());
					// InternalStructuredTextParser.g:6936:2: ()
					// InternalStructuredTextParser.g:6936:3:
					{
					}

					after(grammarAccess.getPower_ExprAccess().getBinaryExpressionLeftAction_1_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__Group_1__0__Impl"

	// $ANTLR start "rule__Power_Expr__Group_1__1"
	// InternalStructuredTextParser.g:6944:1: rule__Power_Expr__Group_1__1 :
	// rule__Power_Expr__Group_1__1__Impl rule__Power_Expr__Group_1__2 ;
	public final void rule__Power_Expr__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6948:1: ( rule__Power_Expr__Group_1__1__Impl
			// rule__Power_Expr__Group_1__2 )
			// InternalStructuredTextParser.g:6949:2: rule__Power_Expr__Group_1__1__Impl
			// rule__Power_Expr__Group_1__2
			{
				pushFollow(FOLLOW_20);
				rule__Power_Expr__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Power_Expr__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__Group_1__1"

	// $ANTLR start "rule__Power_Expr__Group_1__1__Impl"
	// InternalStructuredTextParser.g:6956:1: rule__Power_Expr__Group_1__1__Impl : (
	// ( rule__Power_Expr__OperatorAssignment_1_1 ) ) ;
	public final void rule__Power_Expr__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6960:1: ( ( (
			// rule__Power_Expr__OperatorAssignment_1_1 ) ) )
			// InternalStructuredTextParser.g:6961:1: ( (
			// rule__Power_Expr__OperatorAssignment_1_1 ) )
			{
				// InternalStructuredTextParser.g:6961:1: ( (
				// rule__Power_Expr__OperatorAssignment_1_1 ) )
				// InternalStructuredTextParser.g:6962:2: (
				// rule__Power_Expr__OperatorAssignment_1_1 )
				{
					before(grammarAccess.getPower_ExprAccess().getOperatorAssignment_1_1());
					// InternalStructuredTextParser.g:6963:2: (
					// rule__Power_Expr__OperatorAssignment_1_1 )
					// InternalStructuredTextParser.g:6963:3:
					// rule__Power_Expr__OperatorAssignment_1_1
					{
						pushFollow(FOLLOW_2);
						rule__Power_Expr__OperatorAssignment_1_1();

						state._fsp--;

					}

					after(grammarAccess.getPower_ExprAccess().getOperatorAssignment_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__Group_1__1__Impl"

	// $ANTLR start "rule__Power_Expr__Group_1__2"
	// InternalStructuredTextParser.g:6971:1: rule__Power_Expr__Group_1__2 :
	// rule__Power_Expr__Group_1__2__Impl ;
	public final void rule__Power_Expr__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6975:1: ( rule__Power_Expr__Group_1__2__Impl )
			// InternalStructuredTextParser.g:6976:2: rule__Power_Expr__Group_1__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Power_Expr__Group_1__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__Group_1__2"

	// $ANTLR start "rule__Power_Expr__Group_1__2__Impl"
	// InternalStructuredTextParser.g:6982:1: rule__Power_Expr__Group_1__2__Impl : (
	// ( rule__Power_Expr__RightAssignment_1_2 ) ) ;
	public final void rule__Power_Expr__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:6986:1: ( ( (
			// rule__Power_Expr__RightAssignment_1_2 ) ) )
			// InternalStructuredTextParser.g:6987:1: ( (
			// rule__Power_Expr__RightAssignment_1_2 ) )
			{
				// InternalStructuredTextParser.g:6987:1: ( (
				// rule__Power_Expr__RightAssignment_1_2 ) )
				// InternalStructuredTextParser.g:6988:2: (
				// rule__Power_Expr__RightAssignment_1_2 )
				{
					before(grammarAccess.getPower_ExprAccess().getRightAssignment_1_2());
					// InternalStructuredTextParser.g:6989:2: (
					// rule__Power_Expr__RightAssignment_1_2 )
					// InternalStructuredTextParser.g:6989:3: rule__Power_Expr__RightAssignment_1_2
					{
						pushFollow(FOLLOW_2);
						rule__Power_Expr__RightAssignment_1_2();

						state._fsp--;

					}

					after(grammarAccess.getPower_ExprAccess().getRightAssignment_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__Group_1__2__Impl"

	// $ANTLR start "rule__Unary_Expr__Group_0__0"
	// InternalStructuredTextParser.g:6998:1: rule__Unary_Expr__Group_0__0 :
	// rule__Unary_Expr__Group_0__0__Impl rule__Unary_Expr__Group_0__1 ;
	public final void rule__Unary_Expr__Group_0__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7002:1: ( rule__Unary_Expr__Group_0__0__Impl
			// rule__Unary_Expr__Group_0__1 )
			// InternalStructuredTextParser.g:7003:2: rule__Unary_Expr__Group_0__0__Impl
			// rule__Unary_Expr__Group_0__1
			{
				pushFollow(FOLLOW_58);
				rule__Unary_Expr__Group_0__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Unary_Expr__Group_0__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Unary_Expr__Group_0__0"

	// $ANTLR start "rule__Unary_Expr__Group_0__0__Impl"
	// InternalStructuredTextParser.g:7010:1: rule__Unary_Expr__Group_0__0__Impl : (
	// () ) ;
	public final void rule__Unary_Expr__Group_0__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7014:1: ( ( () ) )
			// InternalStructuredTextParser.g:7015:1: ( () )
			{
				// InternalStructuredTextParser.g:7015:1: ( () )
				// InternalStructuredTextParser.g:7016:2: ()
				{
					before(grammarAccess.getUnary_ExprAccess().getUnaryExpressionAction_0_0());
					// InternalStructuredTextParser.g:7017:2: ()
					// InternalStructuredTextParser.g:7017:3:
					{
					}

					after(grammarAccess.getUnary_ExprAccess().getUnaryExpressionAction_0_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Unary_Expr__Group_0__0__Impl"

	// $ANTLR start "rule__Unary_Expr__Group_0__1"
	// InternalStructuredTextParser.g:7025:1: rule__Unary_Expr__Group_0__1 :
	// rule__Unary_Expr__Group_0__1__Impl rule__Unary_Expr__Group_0__2 ;
	public final void rule__Unary_Expr__Group_0__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7029:1: ( rule__Unary_Expr__Group_0__1__Impl
			// rule__Unary_Expr__Group_0__2 )
			// InternalStructuredTextParser.g:7030:2: rule__Unary_Expr__Group_0__1__Impl
			// rule__Unary_Expr__Group_0__2
			{
				pushFollow(FOLLOW_59);
				rule__Unary_Expr__Group_0__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Unary_Expr__Group_0__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Unary_Expr__Group_0__1"

	// $ANTLR start "rule__Unary_Expr__Group_0__1__Impl"
	// InternalStructuredTextParser.g:7037:1: rule__Unary_Expr__Group_0__1__Impl : (
	// ( rule__Unary_Expr__OperatorAssignment_0_1 ) ) ;
	public final void rule__Unary_Expr__Group_0__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7041:1: ( ( (
			// rule__Unary_Expr__OperatorAssignment_0_1 ) ) )
			// InternalStructuredTextParser.g:7042:1: ( (
			// rule__Unary_Expr__OperatorAssignment_0_1 ) )
			{
				// InternalStructuredTextParser.g:7042:1: ( (
				// rule__Unary_Expr__OperatorAssignment_0_1 ) )
				// InternalStructuredTextParser.g:7043:2: (
				// rule__Unary_Expr__OperatorAssignment_0_1 )
				{
					before(grammarAccess.getUnary_ExprAccess().getOperatorAssignment_0_1());
					// InternalStructuredTextParser.g:7044:2: (
					// rule__Unary_Expr__OperatorAssignment_0_1 )
					// InternalStructuredTextParser.g:7044:3:
					// rule__Unary_Expr__OperatorAssignment_0_1
					{
						pushFollow(FOLLOW_2);
						rule__Unary_Expr__OperatorAssignment_0_1();

						state._fsp--;

					}

					after(grammarAccess.getUnary_ExprAccess().getOperatorAssignment_0_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Unary_Expr__Group_0__1__Impl"

	// $ANTLR start "rule__Unary_Expr__Group_0__2"
	// InternalStructuredTextParser.g:7052:1: rule__Unary_Expr__Group_0__2 :
	// rule__Unary_Expr__Group_0__2__Impl ;
	public final void rule__Unary_Expr__Group_0__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7056:1: ( rule__Unary_Expr__Group_0__2__Impl )
			// InternalStructuredTextParser.g:7057:2: rule__Unary_Expr__Group_0__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Unary_Expr__Group_0__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Unary_Expr__Group_0__2"

	// $ANTLR start "rule__Unary_Expr__Group_0__2__Impl"
	// InternalStructuredTextParser.g:7063:1: rule__Unary_Expr__Group_0__2__Impl : (
	// ( rule__Unary_Expr__ExpressionAssignment_0_2 ) ) ;
	public final void rule__Unary_Expr__Group_0__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7067:1: ( ( (
			// rule__Unary_Expr__ExpressionAssignment_0_2 ) ) )
			// InternalStructuredTextParser.g:7068:1: ( (
			// rule__Unary_Expr__ExpressionAssignment_0_2 ) )
			{
				// InternalStructuredTextParser.g:7068:1: ( (
				// rule__Unary_Expr__ExpressionAssignment_0_2 ) )
				// InternalStructuredTextParser.g:7069:2: (
				// rule__Unary_Expr__ExpressionAssignment_0_2 )
				{
					before(grammarAccess.getUnary_ExprAccess().getExpressionAssignment_0_2());
					// InternalStructuredTextParser.g:7070:2: (
					// rule__Unary_Expr__ExpressionAssignment_0_2 )
					// InternalStructuredTextParser.g:7070:3:
					// rule__Unary_Expr__ExpressionAssignment_0_2
					{
						pushFollow(FOLLOW_2);
						rule__Unary_Expr__ExpressionAssignment_0_2();

						state._fsp--;

					}

					after(grammarAccess.getUnary_ExprAccess().getExpressionAssignment_0_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Unary_Expr__Group_0__2__Impl"

	// $ANTLR start "rule__Primary_Expr__Group_2__0"
	// InternalStructuredTextParser.g:7079:1: rule__Primary_Expr__Group_2__0 :
	// rule__Primary_Expr__Group_2__0__Impl rule__Primary_Expr__Group_2__1 ;
	public final void rule__Primary_Expr__Group_2__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7083:1: ( rule__Primary_Expr__Group_2__0__Impl
			// rule__Primary_Expr__Group_2__1 )
			// InternalStructuredTextParser.g:7084:2: rule__Primary_Expr__Group_2__0__Impl
			// rule__Primary_Expr__Group_2__1
			{
				pushFollow(FOLLOW_20);
				rule__Primary_Expr__Group_2__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Primary_Expr__Group_2__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Primary_Expr__Group_2__0"

	// $ANTLR start "rule__Primary_Expr__Group_2__0__Impl"
	// InternalStructuredTextParser.g:7091:1: rule__Primary_Expr__Group_2__0__Impl :
	// ( LeftParenthesis ) ;
	public final void rule__Primary_Expr__Group_2__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7095:1: ( ( LeftParenthesis ) )
			// InternalStructuredTextParser.g:7096:1: ( LeftParenthesis )
			{
				// InternalStructuredTextParser.g:7096:1: ( LeftParenthesis )
				// InternalStructuredTextParser.g:7097:2: LeftParenthesis
				{
					before(grammarAccess.getPrimary_ExprAccess().getLeftParenthesisKeyword_2_0());
					match(input, LeftParenthesis, FOLLOW_2);
					after(grammarAccess.getPrimary_ExprAccess().getLeftParenthesisKeyword_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Primary_Expr__Group_2__0__Impl"

	// $ANTLR start "rule__Primary_Expr__Group_2__1"
	// InternalStructuredTextParser.g:7106:1: rule__Primary_Expr__Group_2__1 :
	// rule__Primary_Expr__Group_2__1__Impl rule__Primary_Expr__Group_2__2 ;
	public final void rule__Primary_Expr__Group_2__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7110:1: ( rule__Primary_Expr__Group_2__1__Impl
			// rule__Primary_Expr__Group_2__2 )
			// InternalStructuredTextParser.g:7111:2: rule__Primary_Expr__Group_2__1__Impl
			// rule__Primary_Expr__Group_2__2
			{
				pushFollow(FOLLOW_23);
				rule__Primary_Expr__Group_2__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Primary_Expr__Group_2__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Primary_Expr__Group_2__1"

	// $ANTLR start "rule__Primary_Expr__Group_2__1__Impl"
	// InternalStructuredTextParser.g:7118:1: rule__Primary_Expr__Group_2__1__Impl :
	// ( ruleExpression ) ;
	public final void rule__Primary_Expr__Group_2__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7122:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:7123:1: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:7123:1: ( ruleExpression )
				// InternalStructuredTextParser.g:7124:2: ruleExpression
				{
					before(grammarAccess.getPrimary_ExprAccess().getExpressionParserRuleCall_2_1());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getPrimary_ExprAccess().getExpressionParserRuleCall_2_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Primary_Expr__Group_2__1__Impl"

	// $ANTLR start "rule__Primary_Expr__Group_2__2"
	// InternalStructuredTextParser.g:7133:1: rule__Primary_Expr__Group_2__2 :
	// rule__Primary_Expr__Group_2__2__Impl ;
	public final void rule__Primary_Expr__Group_2__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7137:1: ( rule__Primary_Expr__Group_2__2__Impl
			// )
			// InternalStructuredTextParser.g:7138:2: rule__Primary_Expr__Group_2__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Primary_Expr__Group_2__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Primary_Expr__Group_2__2"

	// $ANTLR start "rule__Primary_Expr__Group_2__2__Impl"
	// InternalStructuredTextParser.g:7144:1: rule__Primary_Expr__Group_2__2__Impl :
	// ( RightParenthesis ) ;
	public final void rule__Primary_Expr__Group_2__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7148:1: ( ( RightParenthesis ) )
			// InternalStructuredTextParser.g:7149:1: ( RightParenthesis )
			{
				// InternalStructuredTextParser.g:7149:1: ( RightParenthesis )
				// InternalStructuredTextParser.g:7150:2: RightParenthesis
				{
					before(grammarAccess.getPrimary_ExprAccess().getRightParenthesisKeyword_2_2());
					match(input, RightParenthesis, FOLLOW_2);
					after(grammarAccess.getPrimary_ExprAccess().getRightParenthesisKeyword_2_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Primary_Expr__Group_2__2__Impl"

	// $ANTLR start "rule__Func_Call__Group__0"
	// InternalStructuredTextParser.g:7160:1: rule__Func_Call__Group__0 :
	// rule__Func_Call__Group__0__Impl rule__Func_Call__Group__1 ;
	public final void rule__Func_Call__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7164:1: ( rule__Func_Call__Group__0__Impl
			// rule__Func_Call__Group__1 )
			// InternalStructuredTextParser.g:7165:2: rule__Func_Call__Group__0__Impl
			// rule__Func_Call__Group__1
			{
				pushFollow(FOLLOW_22);
				rule__Func_Call__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Func_Call__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group__0"

	// $ANTLR start "rule__Func_Call__Group__0__Impl"
	// InternalStructuredTextParser.g:7172:1: rule__Func_Call__Group__0__Impl : ( (
	// rule__Func_Call__FuncAssignment_0 ) ) ;
	public final void rule__Func_Call__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7176:1: ( ( (
			// rule__Func_Call__FuncAssignment_0 ) ) )
			// InternalStructuredTextParser.g:7177:1: ( ( rule__Func_Call__FuncAssignment_0
			// ) )
			{
				// InternalStructuredTextParser.g:7177:1: ( ( rule__Func_Call__FuncAssignment_0
				// ) )
				// InternalStructuredTextParser.g:7178:2: ( rule__Func_Call__FuncAssignment_0 )
				{
					before(grammarAccess.getFunc_CallAccess().getFuncAssignment_0());
					// InternalStructuredTextParser.g:7179:2: ( rule__Func_Call__FuncAssignment_0 )
					// InternalStructuredTextParser.g:7179:3: rule__Func_Call__FuncAssignment_0
					{
						pushFollow(FOLLOW_2);
						rule__Func_Call__FuncAssignment_0();

						state._fsp--;

					}

					after(grammarAccess.getFunc_CallAccess().getFuncAssignment_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group__0__Impl"

	// $ANTLR start "rule__Func_Call__Group__1"
	// InternalStructuredTextParser.g:7187:1: rule__Func_Call__Group__1 :
	// rule__Func_Call__Group__1__Impl rule__Func_Call__Group__2 ;
	public final void rule__Func_Call__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7191:1: ( rule__Func_Call__Group__1__Impl
			// rule__Func_Call__Group__2 )
			// InternalStructuredTextParser.g:7192:2: rule__Func_Call__Group__1__Impl
			// rule__Func_Call__Group__2
			{
				pushFollow(FOLLOW_60);
				rule__Func_Call__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Func_Call__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group__1"

	// $ANTLR start "rule__Func_Call__Group__1__Impl"
	// InternalStructuredTextParser.g:7199:1: rule__Func_Call__Group__1__Impl : (
	// LeftParenthesis ) ;
	public final void rule__Func_Call__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7203:1: ( ( LeftParenthesis ) )
			// InternalStructuredTextParser.g:7204:1: ( LeftParenthesis )
			{
				// InternalStructuredTextParser.g:7204:1: ( LeftParenthesis )
				// InternalStructuredTextParser.g:7205:2: LeftParenthesis
				{
					before(grammarAccess.getFunc_CallAccess().getLeftParenthesisKeyword_1());
					match(input, LeftParenthesis, FOLLOW_2);
					after(grammarAccess.getFunc_CallAccess().getLeftParenthesisKeyword_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group__1__Impl"

	// $ANTLR start "rule__Func_Call__Group__2"
	// InternalStructuredTextParser.g:7214:1: rule__Func_Call__Group__2 :
	// rule__Func_Call__Group__2__Impl rule__Func_Call__Group__3 ;
	public final void rule__Func_Call__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7218:1: ( rule__Func_Call__Group__2__Impl
			// rule__Func_Call__Group__3 )
			// InternalStructuredTextParser.g:7219:2: rule__Func_Call__Group__2__Impl
			// rule__Func_Call__Group__3
			{
				pushFollow(FOLLOW_60);
				rule__Func_Call__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Func_Call__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group__2"

	// $ANTLR start "rule__Func_Call__Group__2__Impl"
	// InternalStructuredTextParser.g:7226:1: rule__Func_Call__Group__2__Impl : ( (
	// rule__Func_Call__Group_2__0 )? ) ;
	public final void rule__Func_Call__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7230:1: ( ( ( rule__Func_Call__Group_2__0 )? )
			// )
			// InternalStructuredTextParser.g:7231:1: ( ( rule__Func_Call__Group_2__0 )? )
			{
				// InternalStructuredTextParser.g:7231:1: ( ( rule__Func_Call__Group_2__0 )? )
				// InternalStructuredTextParser.g:7232:2: ( rule__Func_Call__Group_2__0 )?
				{
					before(grammarAccess.getFunc_CallAccess().getGroup_2());
					// InternalStructuredTextParser.g:7233:2: ( rule__Func_Call__Group_2__0 )?
					int alt59 = 2;
					int LA59_0 = input.LA(1);

					if (((LA59_0 >= LDATE_AND_TIME && LA59_0 <= TIME_OF_DAY) || LA59_0 == WSTRING || LA59_0 == STRING
							|| (LA59_0 >= FALSE && LA59_0 <= LTIME) || (LA59_0 >= UDINT && LA59_0 <= ULINT)
							|| (LA59_0 >= USINT && LA59_0 <= WCHAR) || LA59_0 == BOOL
							|| (LA59_0 >= CHAR && LA59_0 <= DINT) || (LA59_0 >= LINT && LA59_0 <= SINT)
							|| (LA59_0 >= TIME && LA59_0 <= UINT) || (LA59_0 >= INT && LA59_0 <= LDT)
							|| (LA59_0 >= NOT && LA59_0 <= TOD) || LA59_0 == DT || (LA59_0 >= LD && LA59_0 <= LT)
							|| LA59_0 == LeftParenthesis || LA59_0 == PlusSign || LA59_0 == HyphenMinus || LA59_0 == T
							|| LA59_0 == D_1 || (LA59_0 >= RULE_ID && LA59_0 <= RULE_UNSIGNED_INT)
							|| LA59_0 == RULE_S_BYTE_CHAR_STR || LA59_0 == RULE_D_BYTE_CHAR_STR)) {
						alt59 = 1;
					}
					switch (alt59) {
					case 1:
					// InternalStructuredTextParser.g:7233:3: rule__Func_Call__Group_2__0
					{
						pushFollow(FOLLOW_2);
						rule__Func_Call__Group_2__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getFunc_CallAccess().getGroup_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group__2__Impl"

	// $ANTLR start "rule__Func_Call__Group__3"
	// InternalStructuredTextParser.g:7241:1: rule__Func_Call__Group__3 :
	// rule__Func_Call__Group__3__Impl ;
	public final void rule__Func_Call__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7245:1: ( rule__Func_Call__Group__3__Impl )
			// InternalStructuredTextParser.g:7246:2: rule__Func_Call__Group__3__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Func_Call__Group__3__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group__3"

	// $ANTLR start "rule__Func_Call__Group__3__Impl"
	// InternalStructuredTextParser.g:7252:1: rule__Func_Call__Group__3__Impl : (
	// RightParenthesis ) ;
	public final void rule__Func_Call__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7256:1: ( ( RightParenthesis ) )
			// InternalStructuredTextParser.g:7257:1: ( RightParenthesis )
			{
				// InternalStructuredTextParser.g:7257:1: ( RightParenthesis )
				// InternalStructuredTextParser.g:7258:2: RightParenthesis
				{
					before(grammarAccess.getFunc_CallAccess().getRightParenthesisKeyword_3());
					match(input, RightParenthesis, FOLLOW_2);
					after(grammarAccess.getFunc_CallAccess().getRightParenthesisKeyword_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group__3__Impl"

	// $ANTLR start "rule__Func_Call__Group_2__0"
	// InternalStructuredTextParser.g:7268:1: rule__Func_Call__Group_2__0 :
	// rule__Func_Call__Group_2__0__Impl rule__Func_Call__Group_2__1 ;
	public final void rule__Func_Call__Group_2__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7272:1: ( rule__Func_Call__Group_2__0__Impl
			// rule__Func_Call__Group_2__1 )
			// InternalStructuredTextParser.g:7273:2: rule__Func_Call__Group_2__0__Impl
			// rule__Func_Call__Group_2__1
			{
				pushFollow(FOLLOW_61);
				rule__Func_Call__Group_2__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Func_Call__Group_2__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group_2__0"

	// $ANTLR start "rule__Func_Call__Group_2__0__Impl"
	// InternalStructuredTextParser.g:7280:1: rule__Func_Call__Group_2__0__Impl : (
	// ( rule__Func_Call__ArgsAssignment_2_0 ) ) ;
	public final void rule__Func_Call__Group_2__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7284:1: ( ( (
			// rule__Func_Call__ArgsAssignment_2_0 ) ) )
			// InternalStructuredTextParser.g:7285:1: ( (
			// rule__Func_Call__ArgsAssignment_2_0 ) )
			{
				// InternalStructuredTextParser.g:7285:1: ( (
				// rule__Func_Call__ArgsAssignment_2_0 ) )
				// InternalStructuredTextParser.g:7286:2: ( rule__Func_Call__ArgsAssignment_2_0
				// )
				{
					before(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_0());
					// InternalStructuredTextParser.g:7287:2: ( rule__Func_Call__ArgsAssignment_2_0
					// )
					// InternalStructuredTextParser.g:7287:3: rule__Func_Call__ArgsAssignment_2_0
					{
						pushFollow(FOLLOW_2);
						rule__Func_Call__ArgsAssignment_2_0();

						state._fsp--;

					}

					after(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group_2__0__Impl"

	// $ANTLR start "rule__Func_Call__Group_2__1"
	// InternalStructuredTextParser.g:7295:1: rule__Func_Call__Group_2__1 :
	// rule__Func_Call__Group_2__1__Impl ;
	public final void rule__Func_Call__Group_2__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7299:1: ( rule__Func_Call__Group_2__1__Impl )
			// InternalStructuredTextParser.g:7300:2: rule__Func_Call__Group_2__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Func_Call__Group_2__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group_2__1"

	// $ANTLR start "rule__Func_Call__Group_2__1__Impl"
	// InternalStructuredTextParser.g:7306:1: rule__Func_Call__Group_2__1__Impl : (
	// ( rule__Func_Call__Group_2_1__0 )* ) ;
	public final void rule__Func_Call__Group_2__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7310:1: ( ( ( rule__Func_Call__Group_2_1__0 )*
			// ) )
			// InternalStructuredTextParser.g:7311:1: ( ( rule__Func_Call__Group_2_1__0 )* )
			{
				// InternalStructuredTextParser.g:7311:1: ( ( rule__Func_Call__Group_2_1__0 )* )
				// InternalStructuredTextParser.g:7312:2: ( rule__Func_Call__Group_2_1__0 )*
				{
					before(grammarAccess.getFunc_CallAccess().getGroup_2_1());
					// InternalStructuredTextParser.g:7313:2: ( rule__Func_Call__Group_2_1__0 )*
					loop60: do {
						int alt60 = 2;
						int LA60_0 = input.LA(1);

						if ((LA60_0 == Comma)) {
							alt60 = 1;
						}

						switch (alt60) {
						case 1:
						// InternalStructuredTextParser.g:7313:3: rule__Func_Call__Group_2_1__0
						{
							pushFollow(FOLLOW_32);
							rule__Func_Call__Group_2_1__0();

							state._fsp--;

						}
							break;

						default:
							break loop60;
						}
					} while (true);

					after(grammarAccess.getFunc_CallAccess().getGroup_2_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group_2__1__Impl"

	// $ANTLR start "rule__Func_Call__Group_2_1__0"
	// InternalStructuredTextParser.g:7322:1: rule__Func_Call__Group_2_1__0 :
	// rule__Func_Call__Group_2_1__0__Impl rule__Func_Call__Group_2_1__1 ;
	public final void rule__Func_Call__Group_2_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7326:1: ( rule__Func_Call__Group_2_1__0__Impl
			// rule__Func_Call__Group_2_1__1 )
			// InternalStructuredTextParser.g:7327:2: rule__Func_Call__Group_2_1__0__Impl
			// rule__Func_Call__Group_2_1__1
			{
				pushFollow(FOLLOW_20);
				rule__Func_Call__Group_2_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Func_Call__Group_2_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group_2_1__0"

	// $ANTLR start "rule__Func_Call__Group_2_1__0__Impl"
	// InternalStructuredTextParser.g:7334:1: rule__Func_Call__Group_2_1__0__Impl :
	// ( Comma ) ;
	public final void rule__Func_Call__Group_2_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7338:1: ( ( Comma ) )
			// InternalStructuredTextParser.g:7339:1: ( Comma )
			{
				// InternalStructuredTextParser.g:7339:1: ( Comma )
				// InternalStructuredTextParser.g:7340:2: Comma
				{
					before(grammarAccess.getFunc_CallAccess().getCommaKeyword_2_1_0());
					match(input, Comma, FOLLOW_2);
					after(grammarAccess.getFunc_CallAccess().getCommaKeyword_2_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group_2_1__0__Impl"

	// $ANTLR start "rule__Func_Call__Group_2_1__1"
	// InternalStructuredTextParser.g:7349:1: rule__Func_Call__Group_2_1__1 :
	// rule__Func_Call__Group_2_1__1__Impl ;
	public final void rule__Func_Call__Group_2_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7353:1: ( rule__Func_Call__Group_2_1__1__Impl
			// )
			// InternalStructuredTextParser.g:7354:2: rule__Func_Call__Group_2_1__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Func_Call__Group_2_1__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group_2_1__1"

	// $ANTLR start "rule__Func_Call__Group_2_1__1__Impl"
	// InternalStructuredTextParser.g:7360:1: rule__Func_Call__Group_2_1__1__Impl :
	// ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) ) ;
	public final void rule__Func_Call__Group_2_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7364:1: ( ( (
			// rule__Func_Call__ArgsAssignment_2_1_1 ) ) )
			// InternalStructuredTextParser.g:7365:1: ( (
			// rule__Func_Call__ArgsAssignment_2_1_1 ) )
			{
				// InternalStructuredTextParser.g:7365:1: ( (
				// rule__Func_Call__ArgsAssignment_2_1_1 ) )
				// InternalStructuredTextParser.g:7366:2: (
				// rule__Func_Call__ArgsAssignment_2_1_1 )
				{
					before(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_1_1());
					// InternalStructuredTextParser.g:7367:2: (
					// rule__Func_Call__ArgsAssignment_2_1_1 )
					// InternalStructuredTextParser.g:7367:3: rule__Func_Call__ArgsAssignment_2_1_1
					{
						pushFollow(FOLLOW_2);
						rule__Func_Call__ArgsAssignment_2_1_1();

						state._fsp--;

					}

					after(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__Group_2_1__1__Impl"

	// $ANTLR start "rule__Param_Assign_In__Group__0"
	// InternalStructuredTextParser.g:7376:1: rule__Param_Assign_In__Group__0 :
	// rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1 ;
	public final void rule__Param_Assign_In__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7380:1: (
			// rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1 )
			// InternalStructuredTextParser.g:7381:2: rule__Param_Assign_In__Group__0__Impl
			// rule__Param_Assign_In__Group__1
			{
				pushFollow(FOLLOW_20);
				rule__Param_Assign_In__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Param_Assign_In__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_In__Group__0"

	// $ANTLR start "rule__Param_Assign_In__Group__0__Impl"
	// InternalStructuredTextParser.g:7388:1: rule__Param_Assign_In__Group__0__Impl
	// : ( ( rule__Param_Assign_In__Group_0__0 )? ) ;
	public final void rule__Param_Assign_In__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7392:1: ( ( (
			// rule__Param_Assign_In__Group_0__0 )? ) )
			// InternalStructuredTextParser.g:7393:1: ( ( rule__Param_Assign_In__Group_0__0
			// )? )
			{
				// InternalStructuredTextParser.g:7393:1: ( ( rule__Param_Assign_In__Group_0__0
				// )? )
				// InternalStructuredTextParser.g:7394:2: ( rule__Param_Assign_In__Group_0__0 )?
				{
					before(grammarAccess.getParam_Assign_InAccess().getGroup_0());
					// InternalStructuredTextParser.g:7395:2: ( rule__Param_Assign_In__Group_0__0 )?
					int alt61 = 2;
					int LA61_0 = input.LA(1);

					if ((LA61_0 == RULE_ID)) {
						int LA61_1 = input.LA(2);

						if ((LA61_1 == ColonEqualsSign)) {
							alt61 = 1;
						}
					}
					switch (alt61) {
					case 1:
					// InternalStructuredTextParser.g:7395:3: rule__Param_Assign_In__Group_0__0
					{
						pushFollow(FOLLOW_2);
						rule__Param_Assign_In__Group_0__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getParam_Assign_InAccess().getGroup_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_In__Group__0__Impl"

	// $ANTLR start "rule__Param_Assign_In__Group__1"
	// InternalStructuredTextParser.g:7403:1: rule__Param_Assign_In__Group__1 :
	// rule__Param_Assign_In__Group__1__Impl ;
	public final void rule__Param_Assign_In__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7407:1: (
			// rule__Param_Assign_In__Group__1__Impl )
			// InternalStructuredTextParser.g:7408:2: rule__Param_Assign_In__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Param_Assign_In__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_In__Group__1"

	// $ANTLR start "rule__Param_Assign_In__Group__1__Impl"
	// InternalStructuredTextParser.g:7414:1: rule__Param_Assign_In__Group__1__Impl
	// : ( ( rule__Param_Assign_In__ExprAssignment_1 ) ) ;
	public final void rule__Param_Assign_In__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7418:1: ( ( (
			// rule__Param_Assign_In__ExprAssignment_1 ) ) )
			// InternalStructuredTextParser.g:7419:1: ( (
			// rule__Param_Assign_In__ExprAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:7419:1: ( (
				// rule__Param_Assign_In__ExprAssignment_1 ) )
				// InternalStructuredTextParser.g:7420:2: (
				// rule__Param_Assign_In__ExprAssignment_1 )
				{
					before(grammarAccess.getParam_Assign_InAccess().getExprAssignment_1());
					// InternalStructuredTextParser.g:7421:2: (
					// rule__Param_Assign_In__ExprAssignment_1 )
					// InternalStructuredTextParser.g:7421:3:
					// rule__Param_Assign_In__ExprAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Param_Assign_In__ExprAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getParam_Assign_InAccess().getExprAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_In__Group__1__Impl"

	// $ANTLR start "rule__Param_Assign_In__Group_0__0"
	// InternalStructuredTextParser.g:7430:1: rule__Param_Assign_In__Group_0__0 :
	// rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1 ;
	public final void rule__Param_Assign_In__Group_0__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7434:1: (
			// rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1 )
			// InternalStructuredTextParser.g:7435:2:
			// rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1
			{
				pushFollow(FOLLOW_19);
				rule__Param_Assign_In__Group_0__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Param_Assign_In__Group_0__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_In__Group_0__0"

	// $ANTLR start "rule__Param_Assign_In__Group_0__0__Impl"
	// InternalStructuredTextParser.g:7442:1:
	// rule__Param_Assign_In__Group_0__0__Impl : ( (
	// rule__Param_Assign_In__VarAssignment_0_0 ) ) ;
	public final void rule__Param_Assign_In__Group_0__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7446:1: ( ( (
			// rule__Param_Assign_In__VarAssignment_0_0 ) ) )
			// InternalStructuredTextParser.g:7447:1: ( (
			// rule__Param_Assign_In__VarAssignment_0_0 ) )
			{
				// InternalStructuredTextParser.g:7447:1: ( (
				// rule__Param_Assign_In__VarAssignment_0_0 ) )
				// InternalStructuredTextParser.g:7448:2: (
				// rule__Param_Assign_In__VarAssignment_0_0 )
				{
					before(grammarAccess.getParam_Assign_InAccess().getVarAssignment_0_0());
					// InternalStructuredTextParser.g:7449:2: (
					// rule__Param_Assign_In__VarAssignment_0_0 )
					// InternalStructuredTextParser.g:7449:3:
					// rule__Param_Assign_In__VarAssignment_0_0
					{
						pushFollow(FOLLOW_2);
						rule__Param_Assign_In__VarAssignment_0_0();

						state._fsp--;

					}

					after(grammarAccess.getParam_Assign_InAccess().getVarAssignment_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_In__Group_0__0__Impl"

	// $ANTLR start "rule__Param_Assign_In__Group_0__1"
	// InternalStructuredTextParser.g:7457:1: rule__Param_Assign_In__Group_0__1 :
	// rule__Param_Assign_In__Group_0__1__Impl ;
	public final void rule__Param_Assign_In__Group_0__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7461:1: (
			// rule__Param_Assign_In__Group_0__1__Impl )
			// InternalStructuredTextParser.g:7462:2:
			// rule__Param_Assign_In__Group_0__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Param_Assign_In__Group_0__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_In__Group_0__1"

	// $ANTLR start "rule__Param_Assign_In__Group_0__1__Impl"
	// InternalStructuredTextParser.g:7468:1:
	// rule__Param_Assign_In__Group_0__1__Impl : ( ColonEqualsSign ) ;
	public final void rule__Param_Assign_In__Group_0__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7472:1: ( ( ColonEqualsSign ) )
			// InternalStructuredTextParser.g:7473:1: ( ColonEqualsSign )
			{
				// InternalStructuredTextParser.g:7473:1: ( ColonEqualsSign )
				// InternalStructuredTextParser.g:7474:2: ColonEqualsSign
				{
					before(grammarAccess.getParam_Assign_InAccess().getColonEqualsSignKeyword_0_1());
					match(input, ColonEqualsSign, FOLLOW_2);
					after(grammarAccess.getParam_Assign_InAccess().getColonEqualsSignKeyword_0_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_In__Group_0__1__Impl"

	// $ANTLR start "rule__Param_Assign_Out__Group__0"
	// InternalStructuredTextParser.g:7484:1: rule__Param_Assign_Out__Group__0 :
	// rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1 ;
	public final void rule__Param_Assign_Out__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7488:1: (
			// rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1 )
			// InternalStructuredTextParser.g:7489:2: rule__Param_Assign_Out__Group__0__Impl
			// rule__Param_Assign_Out__Group__1
			{
				pushFollow(FOLLOW_20);
				rule__Param_Assign_Out__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Param_Assign_Out__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__Group__0"

	// $ANTLR start "rule__Param_Assign_Out__Group__0__Impl"
	// InternalStructuredTextParser.g:7496:1: rule__Param_Assign_Out__Group__0__Impl
	// : ( ( rule__Param_Assign_Out__NotAssignment_0 )? ) ;
	public final void rule__Param_Assign_Out__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7500:1: ( ( (
			// rule__Param_Assign_Out__NotAssignment_0 )? ) )
			// InternalStructuredTextParser.g:7501:1: ( (
			// rule__Param_Assign_Out__NotAssignment_0 )? )
			{
				// InternalStructuredTextParser.g:7501:1: ( (
				// rule__Param_Assign_Out__NotAssignment_0 )? )
				// InternalStructuredTextParser.g:7502:2: (
				// rule__Param_Assign_Out__NotAssignment_0 )?
				{
					before(grammarAccess.getParam_Assign_OutAccess().getNotAssignment_0());
					// InternalStructuredTextParser.g:7503:2: (
					// rule__Param_Assign_Out__NotAssignment_0 )?
					int alt62 = 2;
					int LA62_0 = input.LA(1);

					if ((LA62_0 == NOT)) {
						alt62 = 1;
					}
					switch (alt62) {
					case 1:
					// InternalStructuredTextParser.g:7503:3:
					// rule__Param_Assign_Out__NotAssignment_0
					{
						pushFollow(FOLLOW_2);
						rule__Param_Assign_Out__NotAssignment_0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getParam_Assign_OutAccess().getNotAssignment_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__Group__0__Impl"

	// $ANTLR start "rule__Param_Assign_Out__Group__1"
	// InternalStructuredTextParser.g:7511:1: rule__Param_Assign_Out__Group__1 :
	// rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2 ;
	public final void rule__Param_Assign_Out__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7515:1: (
			// rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2 )
			// InternalStructuredTextParser.g:7516:2: rule__Param_Assign_Out__Group__1__Impl
			// rule__Param_Assign_Out__Group__2
			{
				pushFollow(FOLLOW_62);
				rule__Param_Assign_Out__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Param_Assign_Out__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__Group__1"

	// $ANTLR start "rule__Param_Assign_Out__Group__1__Impl"
	// InternalStructuredTextParser.g:7523:1: rule__Param_Assign_Out__Group__1__Impl
	// : ( ( rule__Param_Assign_Out__VarAssignment_1 ) ) ;
	public final void rule__Param_Assign_Out__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7527:1: ( ( (
			// rule__Param_Assign_Out__VarAssignment_1 ) ) )
			// InternalStructuredTextParser.g:7528:1: ( (
			// rule__Param_Assign_Out__VarAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:7528:1: ( (
				// rule__Param_Assign_Out__VarAssignment_1 ) )
				// InternalStructuredTextParser.g:7529:2: (
				// rule__Param_Assign_Out__VarAssignment_1 )
				{
					before(grammarAccess.getParam_Assign_OutAccess().getVarAssignment_1());
					// InternalStructuredTextParser.g:7530:2: (
					// rule__Param_Assign_Out__VarAssignment_1 )
					// InternalStructuredTextParser.g:7530:3:
					// rule__Param_Assign_Out__VarAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Param_Assign_Out__VarAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getParam_Assign_OutAccess().getVarAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__Group__1__Impl"

	// $ANTLR start "rule__Param_Assign_Out__Group__2"
	// InternalStructuredTextParser.g:7538:1: rule__Param_Assign_Out__Group__2 :
	// rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3 ;
	public final void rule__Param_Assign_Out__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7542:1: (
			// rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3 )
			// InternalStructuredTextParser.g:7543:2: rule__Param_Assign_Out__Group__2__Impl
			// rule__Param_Assign_Out__Group__3
			{
				pushFollow(FOLLOW_16);
				rule__Param_Assign_Out__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Param_Assign_Out__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__Group__2"

	// $ANTLR start "rule__Param_Assign_Out__Group__2__Impl"
	// InternalStructuredTextParser.g:7550:1: rule__Param_Assign_Out__Group__2__Impl
	// : ( EqualsSignGreaterThanSign ) ;
	public final void rule__Param_Assign_Out__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7554:1: ( ( EqualsSignGreaterThanSign ) )
			// InternalStructuredTextParser.g:7555:1: ( EqualsSignGreaterThanSign )
			{
				// InternalStructuredTextParser.g:7555:1: ( EqualsSignGreaterThanSign )
				// InternalStructuredTextParser.g:7556:2: EqualsSignGreaterThanSign
				{
					before(grammarAccess.getParam_Assign_OutAccess().getEqualsSignGreaterThanSignKeyword_2());
					match(input, EqualsSignGreaterThanSign, FOLLOW_2);
					after(grammarAccess.getParam_Assign_OutAccess().getEqualsSignGreaterThanSignKeyword_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__Group__2__Impl"

	// $ANTLR start "rule__Param_Assign_Out__Group__3"
	// InternalStructuredTextParser.g:7565:1: rule__Param_Assign_Out__Group__3 :
	// rule__Param_Assign_Out__Group__3__Impl ;
	public final void rule__Param_Assign_Out__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7569:1: (
			// rule__Param_Assign_Out__Group__3__Impl )
			// InternalStructuredTextParser.g:7570:2: rule__Param_Assign_Out__Group__3__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Param_Assign_Out__Group__3__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__Group__3"

	// $ANTLR start "rule__Param_Assign_Out__Group__3__Impl"
	// InternalStructuredTextParser.g:7576:1: rule__Param_Assign_Out__Group__3__Impl
	// : ( ( rule__Param_Assign_Out__ExprAssignment_3 ) ) ;
	public final void rule__Param_Assign_Out__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7580:1: ( ( (
			// rule__Param_Assign_Out__ExprAssignment_3 ) ) )
			// InternalStructuredTextParser.g:7581:1: ( (
			// rule__Param_Assign_Out__ExprAssignment_3 ) )
			{
				// InternalStructuredTextParser.g:7581:1: ( (
				// rule__Param_Assign_Out__ExprAssignment_3 ) )
				// InternalStructuredTextParser.g:7582:2: (
				// rule__Param_Assign_Out__ExprAssignment_3 )
				{
					before(grammarAccess.getParam_Assign_OutAccess().getExprAssignment_3());
					// InternalStructuredTextParser.g:7583:2: (
					// rule__Param_Assign_Out__ExprAssignment_3 )
					// InternalStructuredTextParser.g:7583:3:
					// rule__Param_Assign_Out__ExprAssignment_3
					{
						pushFollow(FOLLOW_2);
						rule__Param_Assign_Out__ExprAssignment_3();

						state._fsp--;

					}

					after(grammarAccess.getParam_Assign_OutAccess().getExprAssignment_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__Group__3__Impl"

	// $ANTLR start "rule__Variable__Group__0"
	// InternalStructuredTextParser.g:7592:1: rule__Variable__Group__0 :
	// rule__Variable__Group__0__Impl rule__Variable__Group__1 ;
	public final void rule__Variable__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7596:1: ( rule__Variable__Group__0__Impl
			// rule__Variable__Group__1 )
			// InternalStructuredTextParser.g:7597:2: rule__Variable__Group__0__Impl
			// rule__Variable__Group__1
			{
				pushFollow(FOLLOW_63);
				rule__Variable__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Variable__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable__Group__0"

	// $ANTLR start "rule__Variable__Group__0__Impl"
	// InternalStructuredTextParser.g:7604:1: rule__Variable__Group__0__Impl : (
	// ruleVariable_Subscript ) ;
	public final void rule__Variable__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7608:1: ( ( ruleVariable_Subscript ) )
			// InternalStructuredTextParser.g:7609:1: ( ruleVariable_Subscript )
			{
				// InternalStructuredTextParser.g:7609:1: ( ruleVariable_Subscript )
				// InternalStructuredTextParser.g:7610:2: ruleVariable_Subscript
				{
					before(grammarAccess.getVariableAccess().getVariable_SubscriptParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleVariable_Subscript();

					state._fsp--;

					after(grammarAccess.getVariableAccess().getVariable_SubscriptParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable__Group__0__Impl"

	// $ANTLR start "rule__Variable__Group__1"
	// InternalStructuredTextParser.g:7619:1: rule__Variable__Group__1 :
	// rule__Variable__Group__1__Impl ;
	public final void rule__Variable__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7623:1: ( rule__Variable__Group__1__Impl )
			// InternalStructuredTextParser.g:7624:2: rule__Variable__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Variable__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable__Group__1"

	// $ANTLR start "rule__Variable__Group__1__Impl"
	// InternalStructuredTextParser.g:7630:1: rule__Variable__Group__1__Impl : ( (
	// rule__Variable__PartAssignment_1 )? ) ;
	public final void rule__Variable__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7634:1: ( ( ( rule__Variable__PartAssignment_1
			// )? ) )
			// InternalStructuredTextParser.g:7635:1: ( ( rule__Variable__PartAssignment_1
			// )? )
			{
				// InternalStructuredTextParser.g:7635:1: ( ( rule__Variable__PartAssignment_1
				// )? )
				// InternalStructuredTextParser.g:7636:2: ( rule__Variable__PartAssignment_1 )?
				{
					before(grammarAccess.getVariableAccess().getPartAssignment_1());
					// InternalStructuredTextParser.g:7637:2: ( rule__Variable__PartAssignment_1 )?
					int alt63 = 2;
					int LA63_0 = input.LA(1);

					if (((LA63_0 >= B && LA63_0 <= X) || LA63_0 == FullStop)) {
						alt63 = 1;
					}
					switch (alt63) {
					case 1:
					// InternalStructuredTextParser.g:7637:3: rule__Variable__PartAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Variable__PartAssignment_1();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getVariableAccess().getPartAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable__Group__1__Impl"

	// $ANTLR start "rule__Variable_Subscript__Group__0"
	// InternalStructuredTextParser.g:7646:1: rule__Variable_Subscript__Group__0 :
	// rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1 ;
	public final void rule__Variable_Subscript__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7650:1: (
			// rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1 )
			// InternalStructuredTextParser.g:7651:2:
			// rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1
			{
				pushFollow(FOLLOW_15);
				rule__Variable_Subscript__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Variable_Subscript__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group__0"

	// $ANTLR start "rule__Variable_Subscript__Group__0__Impl"
	// InternalStructuredTextParser.g:7658:1:
	// rule__Variable_Subscript__Group__0__Impl : ( (
	// rule__Variable_Subscript__Alternatives_0 ) ) ;
	public final void rule__Variable_Subscript__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7662:1: ( ( (
			// rule__Variable_Subscript__Alternatives_0 ) ) )
			// InternalStructuredTextParser.g:7663:1: ( (
			// rule__Variable_Subscript__Alternatives_0 ) )
			{
				// InternalStructuredTextParser.g:7663:1: ( (
				// rule__Variable_Subscript__Alternatives_0 ) )
				// InternalStructuredTextParser.g:7664:2: (
				// rule__Variable_Subscript__Alternatives_0 )
				{
					before(grammarAccess.getVariable_SubscriptAccess().getAlternatives_0());
					// InternalStructuredTextParser.g:7665:2: (
					// rule__Variable_Subscript__Alternatives_0 )
					// InternalStructuredTextParser.g:7665:3:
					// rule__Variable_Subscript__Alternatives_0
					{
						pushFollow(FOLLOW_2);
						rule__Variable_Subscript__Alternatives_0();

						state._fsp--;

					}

					after(grammarAccess.getVariable_SubscriptAccess().getAlternatives_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group__0__Impl"

	// $ANTLR start "rule__Variable_Subscript__Group__1"
	// InternalStructuredTextParser.g:7673:1: rule__Variable_Subscript__Group__1 :
	// rule__Variable_Subscript__Group__1__Impl ;
	public final void rule__Variable_Subscript__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7677:1: (
			// rule__Variable_Subscript__Group__1__Impl )
			// InternalStructuredTextParser.g:7678:2:
			// rule__Variable_Subscript__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Variable_Subscript__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group__1"

	// $ANTLR start "rule__Variable_Subscript__Group__1__Impl"
	// InternalStructuredTextParser.g:7684:1:
	// rule__Variable_Subscript__Group__1__Impl : ( (
	// rule__Variable_Subscript__Group_1__0 )? ) ;
	public final void rule__Variable_Subscript__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7688:1: ( ( (
			// rule__Variable_Subscript__Group_1__0 )? ) )
			// InternalStructuredTextParser.g:7689:1: ( (
			// rule__Variable_Subscript__Group_1__0 )? )
			{
				// InternalStructuredTextParser.g:7689:1: ( (
				// rule__Variable_Subscript__Group_1__0 )? )
				// InternalStructuredTextParser.g:7690:2: ( rule__Variable_Subscript__Group_1__0
				// )?
				{
					before(grammarAccess.getVariable_SubscriptAccess().getGroup_1());
					// InternalStructuredTextParser.g:7691:2: ( rule__Variable_Subscript__Group_1__0
					// )?
					int alt64 = 2;
					int LA64_0 = input.LA(1);

					if ((LA64_0 == LeftSquareBracket)) {
						alt64 = 1;
					}
					switch (alt64) {
					case 1:
					// InternalStructuredTextParser.g:7691:3: rule__Variable_Subscript__Group_1__0
					{
						pushFollow(FOLLOW_2);
						rule__Variable_Subscript__Group_1__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getVariable_SubscriptAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group__1__Impl"

	// $ANTLR start "rule__Variable_Subscript__Group_1__0"
	// InternalStructuredTextParser.g:7700:1: rule__Variable_Subscript__Group_1__0 :
	// rule__Variable_Subscript__Group_1__0__Impl
	// rule__Variable_Subscript__Group_1__1 ;
	public final void rule__Variable_Subscript__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7704:1: (
			// rule__Variable_Subscript__Group_1__0__Impl
			// rule__Variable_Subscript__Group_1__1 )
			// InternalStructuredTextParser.g:7705:2:
			// rule__Variable_Subscript__Group_1__0__Impl
			// rule__Variable_Subscript__Group_1__1
			{
				pushFollow(FOLLOW_15);
				rule__Variable_Subscript__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Variable_Subscript__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1__0"

	// $ANTLR start "rule__Variable_Subscript__Group_1__0__Impl"
	// InternalStructuredTextParser.g:7712:1:
	// rule__Variable_Subscript__Group_1__0__Impl : ( () ) ;
	public final void rule__Variable_Subscript__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7716:1: ( ( () ) )
			// InternalStructuredTextParser.g:7717:1: ( () )
			{
				// InternalStructuredTextParser.g:7717:1: ( () )
				// InternalStructuredTextParser.g:7718:2: ()
				{
					before(grammarAccess.getVariable_SubscriptAccess().getArrayVariableArrayAction_1_0());
					// InternalStructuredTextParser.g:7719:2: ()
					// InternalStructuredTextParser.g:7719:3:
					{
					}

					after(grammarAccess.getVariable_SubscriptAccess().getArrayVariableArrayAction_1_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1__0__Impl"

	// $ANTLR start "rule__Variable_Subscript__Group_1__1"
	// InternalStructuredTextParser.g:7727:1: rule__Variable_Subscript__Group_1__1 :
	// rule__Variable_Subscript__Group_1__1__Impl
	// rule__Variable_Subscript__Group_1__2 ;
	public final void rule__Variable_Subscript__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7731:1: (
			// rule__Variable_Subscript__Group_1__1__Impl
			// rule__Variable_Subscript__Group_1__2 )
			// InternalStructuredTextParser.g:7732:2:
			// rule__Variable_Subscript__Group_1__1__Impl
			// rule__Variable_Subscript__Group_1__2
			{
				pushFollow(FOLLOW_20);
				rule__Variable_Subscript__Group_1__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Variable_Subscript__Group_1__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1__1"

	// $ANTLR start "rule__Variable_Subscript__Group_1__1__Impl"
	// InternalStructuredTextParser.g:7739:1:
	// rule__Variable_Subscript__Group_1__1__Impl : ( LeftSquareBracket ) ;
	public final void rule__Variable_Subscript__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7743:1: ( ( LeftSquareBracket ) )
			// InternalStructuredTextParser.g:7744:1: ( LeftSquareBracket )
			{
				// InternalStructuredTextParser.g:7744:1: ( LeftSquareBracket )
				// InternalStructuredTextParser.g:7745:2: LeftSquareBracket
				{
					before(grammarAccess.getVariable_SubscriptAccess().getLeftSquareBracketKeyword_1_1());
					match(input, LeftSquareBracket, FOLLOW_2);
					after(grammarAccess.getVariable_SubscriptAccess().getLeftSquareBracketKeyword_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1__1__Impl"

	// $ANTLR start "rule__Variable_Subscript__Group_1__2"
	// InternalStructuredTextParser.g:7754:1: rule__Variable_Subscript__Group_1__2 :
	// rule__Variable_Subscript__Group_1__2__Impl
	// rule__Variable_Subscript__Group_1__3 ;
	public final void rule__Variable_Subscript__Group_1__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7758:1: (
			// rule__Variable_Subscript__Group_1__2__Impl
			// rule__Variable_Subscript__Group_1__3 )
			// InternalStructuredTextParser.g:7759:2:
			// rule__Variable_Subscript__Group_1__2__Impl
			// rule__Variable_Subscript__Group_1__3
			{
				pushFollow(FOLLOW_64);
				rule__Variable_Subscript__Group_1__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Variable_Subscript__Group_1__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1__2"

	// $ANTLR start "rule__Variable_Subscript__Group_1__2__Impl"
	// InternalStructuredTextParser.g:7766:1:
	// rule__Variable_Subscript__Group_1__2__Impl : ( (
	// rule__Variable_Subscript__IndexAssignment_1_2 ) ) ;
	public final void rule__Variable_Subscript__Group_1__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7770:1: ( ( (
			// rule__Variable_Subscript__IndexAssignment_1_2 ) ) )
			// InternalStructuredTextParser.g:7771:1: ( (
			// rule__Variable_Subscript__IndexAssignment_1_2 ) )
			{
				// InternalStructuredTextParser.g:7771:1: ( (
				// rule__Variable_Subscript__IndexAssignment_1_2 ) )
				// InternalStructuredTextParser.g:7772:2: (
				// rule__Variable_Subscript__IndexAssignment_1_2 )
				{
					before(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_2());
					// InternalStructuredTextParser.g:7773:2: (
					// rule__Variable_Subscript__IndexAssignment_1_2 )
					// InternalStructuredTextParser.g:7773:3:
					// rule__Variable_Subscript__IndexAssignment_1_2
					{
						pushFollow(FOLLOW_2);
						rule__Variable_Subscript__IndexAssignment_1_2();

						state._fsp--;

					}

					after(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1__2__Impl"

	// $ANTLR start "rule__Variable_Subscript__Group_1__3"
	// InternalStructuredTextParser.g:7781:1: rule__Variable_Subscript__Group_1__3 :
	// rule__Variable_Subscript__Group_1__3__Impl
	// rule__Variable_Subscript__Group_1__4 ;
	public final void rule__Variable_Subscript__Group_1__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7785:1: (
			// rule__Variable_Subscript__Group_1__3__Impl
			// rule__Variable_Subscript__Group_1__4 )
			// InternalStructuredTextParser.g:7786:2:
			// rule__Variable_Subscript__Group_1__3__Impl
			// rule__Variable_Subscript__Group_1__4
			{
				pushFollow(FOLLOW_64);
				rule__Variable_Subscript__Group_1__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Variable_Subscript__Group_1__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1__3"

	// $ANTLR start "rule__Variable_Subscript__Group_1__3__Impl"
	// InternalStructuredTextParser.g:7793:1:
	// rule__Variable_Subscript__Group_1__3__Impl : ( (
	// rule__Variable_Subscript__Group_1_3__0 )* ) ;
	public final void rule__Variable_Subscript__Group_1__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7797:1: ( ( (
			// rule__Variable_Subscript__Group_1_3__0 )* ) )
			// InternalStructuredTextParser.g:7798:1: ( (
			// rule__Variable_Subscript__Group_1_3__0 )* )
			{
				// InternalStructuredTextParser.g:7798:1: ( (
				// rule__Variable_Subscript__Group_1_3__0 )* )
				// InternalStructuredTextParser.g:7799:2: (
				// rule__Variable_Subscript__Group_1_3__0 )*
				{
					before(grammarAccess.getVariable_SubscriptAccess().getGroup_1_3());
					// InternalStructuredTextParser.g:7800:2: (
					// rule__Variable_Subscript__Group_1_3__0 )*
					loop65: do {
						int alt65 = 2;
						int LA65_0 = input.LA(1);

						if ((LA65_0 == Comma)) {
							alt65 = 1;
						}

						switch (alt65) {
						case 1:
						// InternalStructuredTextParser.g:7800:3: rule__Variable_Subscript__Group_1_3__0
						{
							pushFollow(FOLLOW_32);
							rule__Variable_Subscript__Group_1_3__0();

							state._fsp--;

						}
							break;

						default:
							break loop65;
						}
					} while (true);

					after(grammarAccess.getVariable_SubscriptAccess().getGroup_1_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1__3__Impl"

	// $ANTLR start "rule__Variable_Subscript__Group_1__4"
	// InternalStructuredTextParser.g:7808:1: rule__Variable_Subscript__Group_1__4 :
	// rule__Variable_Subscript__Group_1__4__Impl ;
	public final void rule__Variable_Subscript__Group_1__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7812:1: (
			// rule__Variable_Subscript__Group_1__4__Impl )
			// InternalStructuredTextParser.g:7813:2:
			// rule__Variable_Subscript__Group_1__4__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Variable_Subscript__Group_1__4__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1__4"

	// $ANTLR start "rule__Variable_Subscript__Group_1__4__Impl"
	// InternalStructuredTextParser.g:7819:1:
	// rule__Variable_Subscript__Group_1__4__Impl : ( RightSquareBracket ) ;
	public final void rule__Variable_Subscript__Group_1__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7823:1: ( ( RightSquareBracket ) )
			// InternalStructuredTextParser.g:7824:1: ( RightSquareBracket )
			{
				// InternalStructuredTextParser.g:7824:1: ( RightSquareBracket )
				// InternalStructuredTextParser.g:7825:2: RightSquareBracket
				{
					before(grammarAccess.getVariable_SubscriptAccess().getRightSquareBracketKeyword_1_4());
					match(input, RightSquareBracket, FOLLOW_2);
					after(grammarAccess.getVariable_SubscriptAccess().getRightSquareBracketKeyword_1_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1__4__Impl"

	// $ANTLR start "rule__Variable_Subscript__Group_1_3__0"
	// InternalStructuredTextParser.g:7835:1: rule__Variable_Subscript__Group_1_3__0
	// : rule__Variable_Subscript__Group_1_3__0__Impl
	// rule__Variable_Subscript__Group_1_3__1 ;
	public final void rule__Variable_Subscript__Group_1_3__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7839:1: (
			// rule__Variable_Subscript__Group_1_3__0__Impl
			// rule__Variable_Subscript__Group_1_3__1 )
			// InternalStructuredTextParser.g:7840:2:
			// rule__Variable_Subscript__Group_1_3__0__Impl
			// rule__Variable_Subscript__Group_1_3__1
			{
				pushFollow(FOLLOW_20);
				rule__Variable_Subscript__Group_1_3__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Variable_Subscript__Group_1_3__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1_3__0"

	// $ANTLR start "rule__Variable_Subscript__Group_1_3__0__Impl"
	// InternalStructuredTextParser.g:7847:1:
	// rule__Variable_Subscript__Group_1_3__0__Impl : ( Comma ) ;
	public final void rule__Variable_Subscript__Group_1_3__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7851:1: ( ( Comma ) )
			// InternalStructuredTextParser.g:7852:1: ( Comma )
			{
				// InternalStructuredTextParser.g:7852:1: ( Comma )
				// InternalStructuredTextParser.g:7853:2: Comma
				{
					before(grammarAccess.getVariable_SubscriptAccess().getCommaKeyword_1_3_0());
					match(input, Comma, FOLLOW_2);
					after(grammarAccess.getVariable_SubscriptAccess().getCommaKeyword_1_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1_3__0__Impl"

	// $ANTLR start "rule__Variable_Subscript__Group_1_3__1"
	// InternalStructuredTextParser.g:7862:1: rule__Variable_Subscript__Group_1_3__1
	// : rule__Variable_Subscript__Group_1_3__1__Impl ;
	public final void rule__Variable_Subscript__Group_1_3__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7866:1: (
			// rule__Variable_Subscript__Group_1_3__1__Impl )
			// InternalStructuredTextParser.g:7867:2:
			// rule__Variable_Subscript__Group_1_3__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Variable_Subscript__Group_1_3__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1_3__1"

	// $ANTLR start "rule__Variable_Subscript__Group_1_3__1__Impl"
	// InternalStructuredTextParser.g:7873:1:
	// rule__Variable_Subscript__Group_1_3__1__Impl : ( (
	// rule__Variable_Subscript__IndexAssignment_1_3_1 ) ) ;
	public final void rule__Variable_Subscript__Group_1_3__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7877:1: ( ( (
			// rule__Variable_Subscript__IndexAssignment_1_3_1 ) ) )
			// InternalStructuredTextParser.g:7878:1: ( (
			// rule__Variable_Subscript__IndexAssignment_1_3_1 ) )
			{
				// InternalStructuredTextParser.g:7878:1: ( (
				// rule__Variable_Subscript__IndexAssignment_1_3_1 ) )
				// InternalStructuredTextParser.g:7879:2: (
				// rule__Variable_Subscript__IndexAssignment_1_3_1 )
				{
					before(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_3_1());
					// InternalStructuredTextParser.g:7880:2: (
					// rule__Variable_Subscript__IndexAssignment_1_3_1 )
					// InternalStructuredTextParser.g:7880:3:
					// rule__Variable_Subscript__IndexAssignment_1_3_1
					{
						pushFollow(FOLLOW_2);
						rule__Variable_Subscript__IndexAssignment_1_3_1();

						state._fsp--;

					}

					after(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_3_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__Group_1_3__1__Impl"

	// $ANTLR start "rule__Variable_Adapter__Group__0"
	// InternalStructuredTextParser.g:7889:1: rule__Variable_Adapter__Group__0 :
	// rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1 ;
	public final void rule__Variable_Adapter__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7893:1: (
			// rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1 )
			// InternalStructuredTextParser.g:7894:2: rule__Variable_Adapter__Group__0__Impl
			// rule__Variable_Adapter__Group__1
			{
				pushFollow(FOLLOW_16);
				rule__Variable_Adapter__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Variable_Adapter__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Adapter__Group__0"

	// $ANTLR start "rule__Variable_Adapter__Group__0__Impl"
	// InternalStructuredTextParser.g:7901:1: rule__Variable_Adapter__Group__0__Impl
	// : ( () ) ;
	public final void rule__Variable_Adapter__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7905:1: ( ( () ) )
			// InternalStructuredTextParser.g:7906:1: ( () )
			{
				// InternalStructuredTextParser.g:7906:1: ( () )
				// InternalStructuredTextParser.g:7907:2: ()
				{
					before(grammarAccess.getVariable_AdapterAccess().getAdapterVariableAction_0());
					// InternalStructuredTextParser.g:7908:2: ()
					// InternalStructuredTextParser.g:7908:3:
					{
					}

					after(grammarAccess.getVariable_AdapterAccess().getAdapterVariableAction_0());

				}

			}

		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Adapter__Group__0__Impl"

	// $ANTLR start "rule__Variable_Adapter__Group__1"
	// InternalStructuredTextParser.g:7916:1: rule__Variable_Adapter__Group__1 :
	// rule__Variable_Adapter__Group__1__Impl rule__Variable_Adapter__Group__2 ;
	public final void rule__Variable_Adapter__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7920:1: (
			// rule__Variable_Adapter__Group__1__Impl rule__Variable_Adapter__Group__2 )
			// InternalStructuredTextParser.g:7921:2: rule__Variable_Adapter__Group__1__Impl
			// rule__Variable_Adapter__Group__2
			{
				pushFollow(FOLLOW_65);
				rule__Variable_Adapter__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Variable_Adapter__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Adapter__Group__1"

	// $ANTLR start "rule__Variable_Adapter__Group__1__Impl"
	// InternalStructuredTextParser.g:7928:1: rule__Variable_Adapter__Group__1__Impl
	// : ( ( rule__Variable_Adapter__AdapterAssignment_1 ) ) ;
	public final void rule__Variable_Adapter__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7932:1: ( ( (
			// rule__Variable_Adapter__AdapterAssignment_1 ) ) )
			// InternalStructuredTextParser.g:7933:1: ( (
			// rule__Variable_Adapter__AdapterAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:7933:1: ( (
				// rule__Variable_Adapter__AdapterAssignment_1 ) )
				// InternalStructuredTextParser.g:7934:2: (
				// rule__Variable_Adapter__AdapterAssignment_1 )
				{
					before(grammarAccess.getVariable_AdapterAccess().getAdapterAssignment_1());
					// InternalStructuredTextParser.g:7935:2: (
					// rule__Variable_Adapter__AdapterAssignment_1 )
					// InternalStructuredTextParser.g:7935:3:
					// rule__Variable_Adapter__AdapterAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Variable_Adapter__AdapterAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getVariable_AdapterAccess().getAdapterAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Adapter__Group__1__Impl"

	// $ANTLR start "rule__Variable_Adapter__Group__2"
	// InternalStructuredTextParser.g:7943:1: rule__Variable_Adapter__Group__2 :
	// rule__Variable_Adapter__Group__2__Impl rule__Variable_Adapter__Group__3 ;
	public final void rule__Variable_Adapter__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7947:1: (
			// rule__Variable_Adapter__Group__2__Impl rule__Variable_Adapter__Group__3 )
			// InternalStructuredTextParser.g:7948:2: rule__Variable_Adapter__Group__2__Impl
			// rule__Variable_Adapter__Group__3
			{
				pushFollow(FOLLOW_16);
				rule__Variable_Adapter__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Variable_Adapter__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Adapter__Group__2"

	// $ANTLR start "rule__Variable_Adapter__Group__2__Impl"
	// InternalStructuredTextParser.g:7955:1: rule__Variable_Adapter__Group__2__Impl
	// : ( FullStop ) ;
	public final void rule__Variable_Adapter__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7959:1: ( ( FullStop ) )
			// InternalStructuredTextParser.g:7960:1: ( FullStop )
			{
				// InternalStructuredTextParser.g:7960:1: ( FullStop )
				// InternalStructuredTextParser.g:7961:2: FullStop
				{
					before(grammarAccess.getVariable_AdapterAccess().getFullStopKeyword_2());
					match(input, FullStop, FOLLOW_2);
					after(grammarAccess.getVariable_AdapterAccess().getFullStopKeyword_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Adapter__Group__2__Impl"

	// $ANTLR start "rule__Variable_Adapter__Group__3"
	// InternalStructuredTextParser.g:7970:1: rule__Variable_Adapter__Group__3 :
	// rule__Variable_Adapter__Group__3__Impl ;
	public final void rule__Variable_Adapter__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7974:1: (
			// rule__Variable_Adapter__Group__3__Impl )
			// InternalStructuredTextParser.g:7975:2: rule__Variable_Adapter__Group__3__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Variable_Adapter__Group__3__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Adapter__Group__3"

	// $ANTLR start "rule__Variable_Adapter__Group__3__Impl"
	// InternalStructuredTextParser.g:7981:1: rule__Variable_Adapter__Group__3__Impl
	// : ( ( rule__Variable_Adapter__VarAssignment_3 ) ) ;
	public final void rule__Variable_Adapter__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:7985:1: ( ( (
			// rule__Variable_Adapter__VarAssignment_3 ) ) )
			// InternalStructuredTextParser.g:7986:1: ( (
			// rule__Variable_Adapter__VarAssignment_3 ) )
			{
				// InternalStructuredTextParser.g:7986:1: ( (
				// rule__Variable_Adapter__VarAssignment_3 ) )
				// InternalStructuredTextParser.g:7987:2: (
				// rule__Variable_Adapter__VarAssignment_3 )
				{
					before(grammarAccess.getVariable_AdapterAccess().getVarAssignment_3());
					// InternalStructuredTextParser.g:7988:2: (
					// rule__Variable_Adapter__VarAssignment_3 )
					// InternalStructuredTextParser.g:7988:3:
					// rule__Variable_Adapter__VarAssignment_3
					{
						pushFollow(FOLLOW_2);
						rule__Variable_Adapter__VarAssignment_3();

						state._fsp--;

					}

					after(grammarAccess.getVariable_AdapterAccess().getVarAssignment_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Adapter__Group__3__Impl"

	// $ANTLR start "rule__Multibit_Part_Access__Group_0__0"
	// InternalStructuredTextParser.g:7997:1: rule__Multibit_Part_Access__Group_0__0
	// : rule__Multibit_Part_Access__Group_0__0__Impl
	// rule__Multibit_Part_Access__Group_0__1 ;
	public final void rule__Multibit_Part_Access__Group_0__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8001:1: (
			// rule__Multibit_Part_Access__Group_0__0__Impl
			// rule__Multibit_Part_Access__Group_0__1 )
			// InternalStructuredTextParser.g:8002:2:
			// rule__Multibit_Part_Access__Group_0__0__Impl
			// rule__Multibit_Part_Access__Group_0__1
			{
				pushFollow(FOLLOW_11);
				rule__Multibit_Part_Access__Group_0__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Multibit_Part_Access__Group_0__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_0__0"

	// $ANTLR start "rule__Multibit_Part_Access__Group_0__0__Impl"
	// InternalStructuredTextParser.g:8009:1:
	// rule__Multibit_Part_Access__Group_0__0__Impl : ( (
	// rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) ) ;
	public final void rule__Multibit_Part_Access__Group_0__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8013:1: ( ( (
			// rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) ) )
			// InternalStructuredTextParser.g:8014:1: ( (
			// rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) )
			{
				// InternalStructuredTextParser.g:8014:1: ( (
				// rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) )
				// InternalStructuredTextParser.g:8015:2: (
				// rule__Multibit_Part_Access__DwordaccessAssignment_0_0 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessAssignment_0_0());
					// InternalStructuredTextParser.g:8016:2: (
					// rule__Multibit_Part_Access__DwordaccessAssignment_0_0 )
					// InternalStructuredTextParser.g:8016:3:
					// rule__Multibit_Part_Access__DwordaccessAssignment_0_0
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__DwordaccessAssignment_0_0();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessAssignment_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_0__0__Impl"

	// $ANTLR start "rule__Multibit_Part_Access__Group_0__1"
	// InternalStructuredTextParser.g:8024:1: rule__Multibit_Part_Access__Group_0__1
	// : rule__Multibit_Part_Access__Group_0__1__Impl ;
	public final void rule__Multibit_Part_Access__Group_0__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8028:1: (
			// rule__Multibit_Part_Access__Group_0__1__Impl )
			// InternalStructuredTextParser.g:8029:2:
			// rule__Multibit_Part_Access__Group_0__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Multibit_Part_Access__Group_0__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_0__1"

	// $ANTLR start "rule__Multibit_Part_Access__Group_0__1__Impl"
	// InternalStructuredTextParser.g:8035:1:
	// rule__Multibit_Part_Access__Group_0__1__Impl : ( (
	// rule__Multibit_Part_Access__IndexAssignment_0_1 ) ) ;
	public final void rule__Multibit_Part_Access__Group_0__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8039:1: ( ( (
			// rule__Multibit_Part_Access__IndexAssignment_0_1 ) ) )
			// InternalStructuredTextParser.g:8040:1: ( (
			// rule__Multibit_Part_Access__IndexAssignment_0_1 ) )
			{
				// InternalStructuredTextParser.g:8040:1: ( (
				// rule__Multibit_Part_Access__IndexAssignment_0_1 ) )
				// InternalStructuredTextParser.g:8041:2: (
				// rule__Multibit_Part_Access__IndexAssignment_0_1 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_0_1());
					// InternalStructuredTextParser.g:8042:2: (
					// rule__Multibit_Part_Access__IndexAssignment_0_1 )
					// InternalStructuredTextParser.g:8042:3:
					// rule__Multibit_Part_Access__IndexAssignment_0_1
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__IndexAssignment_0_1();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_0_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_0__1__Impl"

	// $ANTLR start "rule__Multibit_Part_Access__Group_1__0"
	// InternalStructuredTextParser.g:8051:1: rule__Multibit_Part_Access__Group_1__0
	// : rule__Multibit_Part_Access__Group_1__0__Impl
	// rule__Multibit_Part_Access__Group_1__1 ;
	public final void rule__Multibit_Part_Access__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8055:1: (
			// rule__Multibit_Part_Access__Group_1__0__Impl
			// rule__Multibit_Part_Access__Group_1__1 )
			// InternalStructuredTextParser.g:8056:2:
			// rule__Multibit_Part_Access__Group_1__0__Impl
			// rule__Multibit_Part_Access__Group_1__1
			{
				pushFollow(FOLLOW_11);
				rule__Multibit_Part_Access__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Multibit_Part_Access__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_1__0"

	// $ANTLR start "rule__Multibit_Part_Access__Group_1__0__Impl"
	// InternalStructuredTextParser.g:8063:1:
	// rule__Multibit_Part_Access__Group_1__0__Impl : ( (
	// rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) ) ;
	public final void rule__Multibit_Part_Access__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8067:1: ( ( (
			// rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) ) )
			// InternalStructuredTextParser.g:8068:1: ( (
			// rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) )
			{
				// InternalStructuredTextParser.g:8068:1: ( (
				// rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) )
				// InternalStructuredTextParser.g:8069:2: (
				// rule__Multibit_Part_Access__WordaccessAssignment_1_0 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessAssignment_1_0());
					// InternalStructuredTextParser.g:8070:2: (
					// rule__Multibit_Part_Access__WordaccessAssignment_1_0 )
					// InternalStructuredTextParser.g:8070:3:
					// rule__Multibit_Part_Access__WordaccessAssignment_1_0
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__WordaccessAssignment_1_0();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessAssignment_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_1__0__Impl"

	// $ANTLR start "rule__Multibit_Part_Access__Group_1__1"
	// InternalStructuredTextParser.g:8078:1: rule__Multibit_Part_Access__Group_1__1
	// : rule__Multibit_Part_Access__Group_1__1__Impl ;
	public final void rule__Multibit_Part_Access__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8082:1: (
			// rule__Multibit_Part_Access__Group_1__1__Impl )
			// InternalStructuredTextParser.g:8083:2:
			// rule__Multibit_Part_Access__Group_1__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Multibit_Part_Access__Group_1__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_1__1"

	// $ANTLR start "rule__Multibit_Part_Access__Group_1__1__Impl"
	// InternalStructuredTextParser.g:8089:1:
	// rule__Multibit_Part_Access__Group_1__1__Impl : ( (
	// rule__Multibit_Part_Access__IndexAssignment_1_1 ) ) ;
	public final void rule__Multibit_Part_Access__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8093:1: ( ( (
			// rule__Multibit_Part_Access__IndexAssignment_1_1 ) ) )
			// InternalStructuredTextParser.g:8094:1: ( (
			// rule__Multibit_Part_Access__IndexAssignment_1_1 ) )
			{
				// InternalStructuredTextParser.g:8094:1: ( (
				// rule__Multibit_Part_Access__IndexAssignment_1_1 ) )
				// InternalStructuredTextParser.g:8095:2: (
				// rule__Multibit_Part_Access__IndexAssignment_1_1 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_1_1());
					// InternalStructuredTextParser.g:8096:2: (
					// rule__Multibit_Part_Access__IndexAssignment_1_1 )
					// InternalStructuredTextParser.g:8096:3:
					// rule__Multibit_Part_Access__IndexAssignment_1_1
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__IndexAssignment_1_1();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_1__1__Impl"

	// $ANTLR start "rule__Multibit_Part_Access__Group_2__0"
	// InternalStructuredTextParser.g:8105:1: rule__Multibit_Part_Access__Group_2__0
	// : rule__Multibit_Part_Access__Group_2__0__Impl
	// rule__Multibit_Part_Access__Group_2__1 ;
	public final void rule__Multibit_Part_Access__Group_2__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8109:1: (
			// rule__Multibit_Part_Access__Group_2__0__Impl
			// rule__Multibit_Part_Access__Group_2__1 )
			// InternalStructuredTextParser.g:8110:2:
			// rule__Multibit_Part_Access__Group_2__0__Impl
			// rule__Multibit_Part_Access__Group_2__1
			{
				pushFollow(FOLLOW_11);
				rule__Multibit_Part_Access__Group_2__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Multibit_Part_Access__Group_2__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_2__0"

	// $ANTLR start "rule__Multibit_Part_Access__Group_2__0__Impl"
	// InternalStructuredTextParser.g:8117:1:
	// rule__Multibit_Part_Access__Group_2__0__Impl : ( (
	// rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) ) ;
	public final void rule__Multibit_Part_Access__Group_2__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8121:1: ( ( (
			// rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) ) )
			// InternalStructuredTextParser.g:8122:1: ( (
			// rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) )
			{
				// InternalStructuredTextParser.g:8122:1: ( (
				// rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) )
				// InternalStructuredTextParser.g:8123:2: (
				// rule__Multibit_Part_Access__ByteaccessAssignment_2_0 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessAssignment_2_0());
					// InternalStructuredTextParser.g:8124:2: (
					// rule__Multibit_Part_Access__ByteaccessAssignment_2_0 )
					// InternalStructuredTextParser.g:8124:3:
					// rule__Multibit_Part_Access__ByteaccessAssignment_2_0
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__ByteaccessAssignment_2_0();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessAssignment_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_2__0__Impl"

	// $ANTLR start "rule__Multibit_Part_Access__Group_2__1"
	// InternalStructuredTextParser.g:8132:1: rule__Multibit_Part_Access__Group_2__1
	// : rule__Multibit_Part_Access__Group_2__1__Impl ;
	public final void rule__Multibit_Part_Access__Group_2__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8136:1: (
			// rule__Multibit_Part_Access__Group_2__1__Impl )
			// InternalStructuredTextParser.g:8137:2:
			// rule__Multibit_Part_Access__Group_2__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Multibit_Part_Access__Group_2__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_2__1"

	// $ANTLR start "rule__Multibit_Part_Access__Group_2__1__Impl"
	// InternalStructuredTextParser.g:8143:1:
	// rule__Multibit_Part_Access__Group_2__1__Impl : ( (
	// rule__Multibit_Part_Access__IndexAssignment_2_1 ) ) ;
	public final void rule__Multibit_Part_Access__Group_2__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8147:1: ( ( (
			// rule__Multibit_Part_Access__IndexAssignment_2_1 ) ) )
			// InternalStructuredTextParser.g:8148:1: ( (
			// rule__Multibit_Part_Access__IndexAssignment_2_1 ) )
			{
				// InternalStructuredTextParser.g:8148:1: ( (
				// rule__Multibit_Part_Access__IndexAssignment_2_1 ) )
				// InternalStructuredTextParser.g:8149:2: (
				// rule__Multibit_Part_Access__IndexAssignment_2_1 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_2_1());
					// InternalStructuredTextParser.g:8150:2: (
					// rule__Multibit_Part_Access__IndexAssignment_2_1 )
					// InternalStructuredTextParser.g:8150:3:
					// rule__Multibit_Part_Access__IndexAssignment_2_1
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__IndexAssignment_2_1();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_2_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_2__1__Impl"

	// $ANTLR start "rule__Multibit_Part_Access__Group_3__0"
	// InternalStructuredTextParser.g:8159:1: rule__Multibit_Part_Access__Group_3__0
	// : rule__Multibit_Part_Access__Group_3__0__Impl
	// rule__Multibit_Part_Access__Group_3__1 ;
	public final void rule__Multibit_Part_Access__Group_3__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8163:1: (
			// rule__Multibit_Part_Access__Group_3__0__Impl
			// rule__Multibit_Part_Access__Group_3__1 )
			// InternalStructuredTextParser.g:8164:2:
			// rule__Multibit_Part_Access__Group_3__0__Impl
			// rule__Multibit_Part_Access__Group_3__1
			{
				pushFollow(FOLLOW_11);
				rule__Multibit_Part_Access__Group_3__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Multibit_Part_Access__Group_3__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_3__0"

	// $ANTLR start "rule__Multibit_Part_Access__Group_3__0__Impl"
	// InternalStructuredTextParser.g:8171:1:
	// rule__Multibit_Part_Access__Group_3__0__Impl : ( (
	// rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) ) ;
	public final void rule__Multibit_Part_Access__Group_3__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8175:1: ( ( (
			// rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) ) )
			// InternalStructuredTextParser.g:8176:1: ( (
			// rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) )
			{
				// InternalStructuredTextParser.g:8176:1: ( (
				// rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) )
				// InternalStructuredTextParser.g:8177:2: (
				// rule__Multibit_Part_Access__BitaccessAssignment_3_0 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_3_0());
					// InternalStructuredTextParser.g:8178:2: (
					// rule__Multibit_Part_Access__BitaccessAssignment_3_0 )
					// InternalStructuredTextParser.g:8178:3:
					// rule__Multibit_Part_Access__BitaccessAssignment_3_0
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__BitaccessAssignment_3_0();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_3__0__Impl"

	// $ANTLR start "rule__Multibit_Part_Access__Group_3__1"
	// InternalStructuredTextParser.g:8186:1: rule__Multibit_Part_Access__Group_3__1
	// : rule__Multibit_Part_Access__Group_3__1__Impl ;
	public final void rule__Multibit_Part_Access__Group_3__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8190:1: (
			// rule__Multibit_Part_Access__Group_3__1__Impl )
			// InternalStructuredTextParser.g:8191:2:
			// rule__Multibit_Part_Access__Group_3__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Multibit_Part_Access__Group_3__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_3__1"

	// $ANTLR start "rule__Multibit_Part_Access__Group_3__1__Impl"
	// InternalStructuredTextParser.g:8197:1:
	// rule__Multibit_Part_Access__Group_3__1__Impl : ( (
	// rule__Multibit_Part_Access__IndexAssignment_3_1 ) ) ;
	public final void rule__Multibit_Part_Access__Group_3__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8201:1: ( ( (
			// rule__Multibit_Part_Access__IndexAssignment_3_1 ) ) )
			// InternalStructuredTextParser.g:8202:1: ( (
			// rule__Multibit_Part_Access__IndexAssignment_3_1 ) )
			{
				// InternalStructuredTextParser.g:8202:1: ( (
				// rule__Multibit_Part_Access__IndexAssignment_3_1 ) )
				// InternalStructuredTextParser.g:8203:2: (
				// rule__Multibit_Part_Access__IndexAssignment_3_1 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_3_1());
					// InternalStructuredTextParser.g:8204:2: (
					// rule__Multibit_Part_Access__IndexAssignment_3_1 )
					// InternalStructuredTextParser.g:8204:3:
					// rule__Multibit_Part_Access__IndexAssignment_3_1
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__IndexAssignment_3_1();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_3_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_3__1__Impl"

	// $ANTLR start "rule__Multibit_Part_Access__Group_4__0"
	// InternalStructuredTextParser.g:8213:1: rule__Multibit_Part_Access__Group_4__0
	// : rule__Multibit_Part_Access__Group_4__0__Impl
	// rule__Multibit_Part_Access__Group_4__1 ;
	public final void rule__Multibit_Part_Access__Group_4__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8217:1: (
			// rule__Multibit_Part_Access__Group_4__0__Impl
			// rule__Multibit_Part_Access__Group_4__1 )
			// InternalStructuredTextParser.g:8218:2:
			// rule__Multibit_Part_Access__Group_4__0__Impl
			// rule__Multibit_Part_Access__Group_4__1
			{
				pushFollow(FOLLOW_11);
				rule__Multibit_Part_Access__Group_4__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Multibit_Part_Access__Group_4__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_4__0"

	// $ANTLR start "rule__Multibit_Part_Access__Group_4__0__Impl"
	// InternalStructuredTextParser.g:8225:1:
	// rule__Multibit_Part_Access__Group_4__0__Impl : ( (
	// rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) ) ;
	public final void rule__Multibit_Part_Access__Group_4__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8229:1: ( ( (
			// rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) ) )
			// InternalStructuredTextParser.g:8230:1: ( (
			// rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) )
			{
				// InternalStructuredTextParser.g:8230:1: ( (
				// rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) )
				// InternalStructuredTextParser.g:8231:2: (
				// rule__Multibit_Part_Access__BitaccessAssignment_4_0 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_4_0());
					// InternalStructuredTextParser.g:8232:2: (
					// rule__Multibit_Part_Access__BitaccessAssignment_4_0 )
					// InternalStructuredTextParser.g:8232:3:
					// rule__Multibit_Part_Access__BitaccessAssignment_4_0
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__BitaccessAssignment_4_0();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_4_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_4__0__Impl"

	// $ANTLR start "rule__Multibit_Part_Access__Group_4__1"
	// InternalStructuredTextParser.g:8240:1: rule__Multibit_Part_Access__Group_4__1
	// : rule__Multibit_Part_Access__Group_4__1__Impl ;
	public final void rule__Multibit_Part_Access__Group_4__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8244:1: (
			// rule__Multibit_Part_Access__Group_4__1__Impl )
			// InternalStructuredTextParser.g:8245:2:
			// rule__Multibit_Part_Access__Group_4__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Multibit_Part_Access__Group_4__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_4__1"

	// $ANTLR start "rule__Multibit_Part_Access__Group_4__1__Impl"
	// InternalStructuredTextParser.g:8251:1:
	// rule__Multibit_Part_Access__Group_4__1__Impl : ( (
	// rule__Multibit_Part_Access__IndexAssignment_4_1 ) ) ;
	public final void rule__Multibit_Part_Access__Group_4__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8255:1: ( ( (
			// rule__Multibit_Part_Access__IndexAssignment_4_1 ) ) )
			// InternalStructuredTextParser.g:8256:1: ( (
			// rule__Multibit_Part_Access__IndexAssignment_4_1 ) )
			{
				// InternalStructuredTextParser.g:8256:1: ( (
				// rule__Multibit_Part_Access__IndexAssignment_4_1 ) )
				// InternalStructuredTextParser.g:8257:2: (
				// rule__Multibit_Part_Access__IndexAssignment_4_1 )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_4_1());
					// InternalStructuredTextParser.g:8258:2: (
					// rule__Multibit_Part_Access__IndexAssignment_4_1 )
					// InternalStructuredTextParser.g:8258:3:
					// rule__Multibit_Part_Access__IndexAssignment_4_1
					{
						pushFollow(FOLLOW_2);
						rule__Multibit_Part_Access__IndexAssignment_4_1();

						state._fsp--;

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_4_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__Group_4__1__Impl"

	// $ANTLR start "rule__Int_Literal__Group__0"
	// InternalStructuredTextParser.g:8267:1: rule__Int_Literal__Group__0 :
	// rule__Int_Literal__Group__0__Impl rule__Int_Literal__Group__1 ;
	public final void rule__Int_Literal__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8271:1: ( rule__Int_Literal__Group__0__Impl
			// rule__Int_Literal__Group__1 )
			// InternalStructuredTextParser.g:8272:2: rule__Int_Literal__Group__0__Impl
			// rule__Int_Literal__Group__1
			{
				pushFollow(FOLLOW_66);
				rule__Int_Literal__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Int_Literal__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__Group__0"

	// $ANTLR start "rule__Int_Literal__Group__0__Impl"
	// InternalStructuredTextParser.g:8279:1: rule__Int_Literal__Group__0__Impl : (
	// ( rule__Int_Literal__Group_0__0 )? ) ;
	public final void rule__Int_Literal__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8283:1: ( ( ( rule__Int_Literal__Group_0__0 )?
			// ) )
			// InternalStructuredTextParser.g:8284:1: ( ( rule__Int_Literal__Group_0__0 )? )
			{
				// InternalStructuredTextParser.g:8284:1: ( ( rule__Int_Literal__Group_0__0 )? )
				// InternalStructuredTextParser.g:8285:2: ( rule__Int_Literal__Group_0__0 )?
				{
					before(grammarAccess.getInt_LiteralAccess().getGroup_0());
					// InternalStructuredTextParser.g:8286:2: ( rule__Int_Literal__Group_0__0 )?
					int alt66 = 2;
					int LA66_0 = input.LA(1);

					if (((LA66_0 >= UDINT && LA66_0 <= ULINT) || LA66_0 == USINT || LA66_0 == DINT || LA66_0 == LINT
							|| LA66_0 == SINT || LA66_0 == UINT || LA66_0 == INT)) {
						alt66 = 1;
					}
					switch (alt66) {
					case 1:
					// InternalStructuredTextParser.g:8286:3: rule__Int_Literal__Group_0__0
					{
						pushFollow(FOLLOW_2);
						rule__Int_Literal__Group_0__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getInt_LiteralAccess().getGroup_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__Group__0__Impl"

	// $ANTLR start "rule__Int_Literal__Group__1"
	// InternalStructuredTextParser.g:8294:1: rule__Int_Literal__Group__1 :
	// rule__Int_Literal__Group__1__Impl ;
	public final void rule__Int_Literal__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8298:1: ( rule__Int_Literal__Group__1__Impl )
			// InternalStructuredTextParser.g:8299:2: rule__Int_Literal__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Int_Literal__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__Group__1"

	// $ANTLR start "rule__Int_Literal__Group__1__Impl"
	// InternalStructuredTextParser.g:8305:1: rule__Int_Literal__Group__1__Impl : (
	// ( rule__Int_Literal__ValueAssignment_1 ) ) ;
	public final void rule__Int_Literal__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8309:1: ( ( (
			// rule__Int_Literal__ValueAssignment_1 ) ) )
			// InternalStructuredTextParser.g:8310:1: ( (
			// rule__Int_Literal__ValueAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:8310:1: ( (
				// rule__Int_Literal__ValueAssignment_1 ) )
				// InternalStructuredTextParser.g:8311:2: ( rule__Int_Literal__ValueAssignment_1
				// )
				{
					before(grammarAccess.getInt_LiteralAccess().getValueAssignment_1());
					// InternalStructuredTextParser.g:8312:2: ( rule__Int_Literal__ValueAssignment_1
					// )
					// InternalStructuredTextParser.g:8312:3: rule__Int_Literal__ValueAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Int_Literal__ValueAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getInt_LiteralAccess().getValueAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__Group__1__Impl"

	// $ANTLR start "rule__Int_Literal__Group_0__0"
	// InternalStructuredTextParser.g:8321:1: rule__Int_Literal__Group_0__0 :
	// rule__Int_Literal__Group_0__0__Impl rule__Int_Literal__Group_0__1 ;
	public final void rule__Int_Literal__Group_0__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8325:1: ( rule__Int_Literal__Group_0__0__Impl
			// rule__Int_Literal__Group_0__1 )
			// InternalStructuredTextParser.g:8326:2: rule__Int_Literal__Group_0__0__Impl
			// rule__Int_Literal__Group_0__1
			{
				pushFollow(FOLLOW_67);
				rule__Int_Literal__Group_0__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Int_Literal__Group_0__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__Group_0__0"

	// $ANTLR start "rule__Int_Literal__Group_0__0__Impl"
	// InternalStructuredTextParser.g:8333:1: rule__Int_Literal__Group_0__0__Impl :
	// ( ( rule__Int_Literal__TypeAssignment_0_0 ) ) ;
	public final void rule__Int_Literal__Group_0__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8337:1: ( ( (
			// rule__Int_Literal__TypeAssignment_0_0 ) ) )
			// InternalStructuredTextParser.g:8338:1: ( (
			// rule__Int_Literal__TypeAssignment_0_0 ) )
			{
				// InternalStructuredTextParser.g:8338:1: ( (
				// rule__Int_Literal__TypeAssignment_0_0 ) )
				// InternalStructuredTextParser.g:8339:2: (
				// rule__Int_Literal__TypeAssignment_0_0 )
				{
					before(grammarAccess.getInt_LiteralAccess().getTypeAssignment_0_0());
					// InternalStructuredTextParser.g:8340:2: (
					// rule__Int_Literal__TypeAssignment_0_0 )
					// InternalStructuredTextParser.g:8340:3: rule__Int_Literal__TypeAssignment_0_0
					{
						pushFollow(FOLLOW_2);
						rule__Int_Literal__TypeAssignment_0_0();

						state._fsp--;

					}

					after(grammarAccess.getInt_LiteralAccess().getTypeAssignment_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__Group_0__0__Impl"

	// $ANTLR start "rule__Int_Literal__Group_0__1"
	// InternalStructuredTextParser.g:8348:1: rule__Int_Literal__Group_0__1 :
	// rule__Int_Literal__Group_0__1__Impl ;
	public final void rule__Int_Literal__Group_0__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8352:1: ( rule__Int_Literal__Group_0__1__Impl
			// )
			// InternalStructuredTextParser.g:8353:2: rule__Int_Literal__Group_0__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Int_Literal__Group_0__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__Group_0__1"

	// $ANTLR start "rule__Int_Literal__Group_0__1__Impl"
	// InternalStructuredTextParser.g:8359:1: rule__Int_Literal__Group_0__1__Impl :
	// ( NumberSign ) ;
	public final void rule__Int_Literal__Group_0__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8363:1: ( ( NumberSign ) )
			// InternalStructuredTextParser.g:8364:1: ( NumberSign )
			{
				// InternalStructuredTextParser.g:8364:1: ( NumberSign )
				// InternalStructuredTextParser.g:8365:2: NumberSign
				{
					before(grammarAccess.getInt_LiteralAccess().getNumberSignKeyword_0_1());
					match(input, NumberSign, FOLLOW_2);
					after(grammarAccess.getInt_LiteralAccess().getNumberSignKeyword_0_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__Group_0__1__Impl"

	// $ANTLR start "rule__Signed_Int__Group__0"
	// InternalStructuredTextParser.g:8375:1: rule__Signed_Int__Group__0 :
	// rule__Signed_Int__Group__0__Impl rule__Signed_Int__Group__1 ;
	public final void rule__Signed_Int__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8379:1: ( rule__Signed_Int__Group__0__Impl
			// rule__Signed_Int__Group__1 )
			// InternalStructuredTextParser.g:8380:2: rule__Signed_Int__Group__0__Impl
			// rule__Signed_Int__Group__1
			{
				pushFollow(FOLLOW_68);
				rule__Signed_Int__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Signed_Int__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Signed_Int__Group__0"

	// $ANTLR start "rule__Signed_Int__Group__0__Impl"
	// InternalStructuredTextParser.g:8387:1: rule__Signed_Int__Group__0__Impl : ( (
	// rule__Signed_Int__Alternatives_0 )? ) ;
	public final void rule__Signed_Int__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8391:1: ( ( ( rule__Signed_Int__Alternatives_0
			// )? ) )
			// InternalStructuredTextParser.g:8392:1: ( ( rule__Signed_Int__Alternatives_0
			// )? )
			{
				// InternalStructuredTextParser.g:8392:1: ( ( rule__Signed_Int__Alternatives_0
				// )? )
				// InternalStructuredTextParser.g:8393:2: ( rule__Signed_Int__Alternatives_0 )?
				{
					before(grammarAccess.getSigned_IntAccess().getAlternatives_0());
					// InternalStructuredTextParser.g:8394:2: ( rule__Signed_Int__Alternatives_0 )?
					int alt67 = 2;
					int LA67_0 = input.LA(1);

					if ((LA67_0 == PlusSign || LA67_0 == HyphenMinus)) {
						alt67 = 1;
					}
					switch (alt67) {
					case 1:
					// InternalStructuredTextParser.g:8394:3: rule__Signed_Int__Alternatives_0
					{
						pushFollow(FOLLOW_2);
						rule__Signed_Int__Alternatives_0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getSigned_IntAccess().getAlternatives_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Signed_Int__Group__0__Impl"

	// $ANTLR start "rule__Signed_Int__Group__1"
	// InternalStructuredTextParser.g:8402:1: rule__Signed_Int__Group__1 :
	// rule__Signed_Int__Group__1__Impl ;
	public final void rule__Signed_Int__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8406:1: ( rule__Signed_Int__Group__1__Impl )
			// InternalStructuredTextParser.g:8407:2: rule__Signed_Int__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Signed_Int__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Signed_Int__Group__1"

	// $ANTLR start "rule__Signed_Int__Group__1__Impl"
	// InternalStructuredTextParser.g:8413:1: rule__Signed_Int__Group__1__Impl : (
	// RULE_UNSIGNED_INT ) ;
	public final void rule__Signed_Int__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8417:1: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:8418:1: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:8418:1: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:8419:2: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getSigned_IntAccess().getUNSIGNED_INTTerminalRuleCall_1());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getSigned_IntAccess().getUNSIGNED_INTTerminalRuleCall_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Signed_Int__Group__1__Impl"

	// $ANTLR start "rule__Real_Literal__Group__0"
	// InternalStructuredTextParser.g:8429:1: rule__Real_Literal__Group__0 :
	// rule__Real_Literal__Group__0__Impl rule__Real_Literal__Group__1 ;
	public final void rule__Real_Literal__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8433:1: ( rule__Real_Literal__Group__0__Impl
			// rule__Real_Literal__Group__1 )
			// InternalStructuredTextParser.g:8434:2: rule__Real_Literal__Group__0__Impl
			// rule__Real_Literal__Group__1
			{
				pushFollow(FOLLOW_69);
				rule__Real_Literal__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Real_Literal__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Literal__Group__0"

	// $ANTLR start "rule__Real_Literal__Group__0__Impl"
	// InternalStructuredTextParser.g:8441:1: rule__Real_Literal__Group__0__Impl : (
	// ( rule__Real_Literal__Group_0__0 )? ) ;
	public final void rule__Real_Literal__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8445:1: ( ( ( rule__Real_Literal__Group_0__0
			// )? ) )
			// InternalStructuredTextParser.g:8446:1: ( ( rule__Real_Literal__Group_0__0 )?
			// )
			{
				// InternalStructuredTextParser.g:8446:1: ( ( rule__Real_Literal__Group_0__0 )?
				// )
				// InternalStructuredTextParser.g:8447:2: ( rule__Real_Literal__Group_0__0 )?
				{
					before(grammarAccess.getReal_LiteralAccess().getGroup_0());
					// InternalStructuredTextParser.g:8448:2: ( rule__Real_Literal__Group_0__0 )?
					int alt68 = 2;
					int LA68_0 = input.LA(1);

					if ((LA68_0 == LREAL || LA68_0 == REAL)) {
						alt68 = 1;
					}
					switch (alt68) {
					case 1:
					// InternalStructuredTextParser.g:8448:3: rule__Real_Literal__Group_0__0
					{
						pushFollow(FOLLOW_2);
						rule__Real_Literal__Group_0__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getReal_LiteralAccess().getGroup_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Literal__Group__0__Impl"

	// $ANTLR start "rule__Real_Literal__Group__1"
	// InternalStructuredTextParser.g:8456:1: rule__Real_Literal__Group__1 :
	// rule__Real_Literal__Group__1__Impl ;
	public final void rule__Real_Literal__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8460:1: ( rule__Real_Literal__Group__1__Impl )
			// InternalStructuredTextParser.g:8461:2: rule__Real_Literal__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Real_Literal__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Literal__Group__1"

	// $ANTLR start "rule__Real_Literal__Group__1__Impl"
	// InternalStructuredTextParser.g:8467:1: rule__Real_Literal__Group__1__Impl : (
	// ( rule__Real_Literal__ValueAssignment_1 ) ) ;
	public final void rule__Real_Literal__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8471:1: ( ( (
			// rule__Real_Literal__ValueAssignment_1 ) ) )
			// InternalStructuredTextParser.g:8472:1: ( (
			// rule__Real_Literal__ValueAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:8472:1: ( (
				// rule__Real_Literal__ValueAssignment_1 ) )
				// InternalStructuredTextParser.g:8473:2: (
				// rule__Real_Literal__ValueAssignment_1 )
				{
					before(grammarAccess.getReal_LiteralAccess().getValueAssignment_1());
					// InternalStructuredTextParser.g:8474:2: (
					// rule__Real_Literal__ValueAssignment_1 )
					// InternalStructuredTextParser.g:8474:3: rule__Real_Literal__ValueAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Real_Literal__ValueAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getReal_LiteralAccess().getValueAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Literal__Group__1__Impl"

	// $ANTLR start "rule__Real_Literal__Group_0__0"
	// InternalStructuredTextParser.g:8483:1: rule__Real_Literal__Group_0__0 :
	// rule__Real_Literal__Group_0__0__Impl rule__Real_Literal__Group_0__1 ;
	public final void rule__Real_Literal__Group_0__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8487:1: ( rule__Real_Literal__Group_0__0__Impl
			// rule__Real_Literal__Group_0__1 )
			// InternalStructuredTextParser.g:8488:2: rule__Real_Literal__Group_0__0__Impl
			// rule__Real_Literal__Group_0__1
			{
				pushFollow(FOLLOW_67);
				rule__Real_Literal__Group_0__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Real_Literal__Group_0__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Literal__Group_0__0"

	// $ANTLR start "rule__Real_Literal__Group_0__0__Impl"
	// InternalStructuredTextParser.g:8495:1: rule__Real_Literal__Group_0__0__Impl :
	// ( ( rule__Real_Literal__TypeAssignment_0_0 ) ) ;
	public final void rule__Real_Literal__Group_0__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8499:1: ( ( (
			// rule__Real_Literal__TypeAssignment_0_0 ) ) )
			// InternalStructuredTextParser.g:8500:1: ( (
			// rule__Real_Literal__TypeAssignment_0_0 ) )
			{
				// InternalStructuredTextParser.g:8500:1: ( (
				// rule__Real_Literal__TypeAssignment_0_0 ) )
				// InternalStructuredTextParser.g:8501:2: (
				// rule__Real_Literal__TypeAssignment_0_0 )
				{
					before(grammarAccess.getReal_LiteralAccess().getTypeAssignment_0_0());
					// InternalStructuredTextParser.g:8502:2: (
					// rule__Real_Literal__TypeAssignment_0_0 )
					// InternalStructuredTextParser.g:8502:3: rule__Real_Literal__TypeAssignment_0_0
					{
						pushFollow(FOLLOW_2);
						rule__Real_Literal__TypeAssignment_0_0();

						state._fsp--;

					}

					after(grammarAccess.getReal_LiteralAccess().getTypeAssignment_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Literal__Group_0__0__Impl"

	// $ANTLR start "rule__Real_Literal__Group_0__1"
	// InternalStructuredTextParser.g:8510:1: rule__Real_Literal__Group_0__1 :
	// rule__Real_Literal__Group_0__1__Impl ;
	public final void rule__Real_Literal__Group_0__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8514:1: ( rule__Real_Literal__Group_0__1__Impl
			// )
			// InternalStructuredTextParser.g:8515:2: rule__Real_Literal__Group_0__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Real_Literal__Group_0__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Literal__Group_0__1"

	// $ANTLR start "rule__Real_Literal__Group_0__1__Impl"
	// InternalStructuredTextParser.g:8521:1: rule__Real_Literal__Group_0__1__Impl :
	// ( NumberSign ) ;
	public final void rule__Real_Literal__Group_0__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8525:1: ( ( NumberSign ) )
			// InternalStructuredTextParser.g:8526:1: ( NumberSign )
			{
				// InternalStructuredTextParser.g:8526:1: ( NumberSign )
				// InternalStructuredTextParser.g:8527:2: NumberSign
				{
					before(grammarAccess.getReal_LiteralAccess().getNumberSignKeyword_0_1());
					match(input, NumberSign, FOLLOW_2);
					after(grammarAccess.getReal_LiteralAccess().getNumberSignKeyword_0_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Literal__Group_0__1__Impl"

	// $ANTLR start "rule__Real_Value__Group__0"
	// InternalStructuredTextParser.g:8537:1: rule__Real_Value__Group__0 :
	// rule__Real_Value__Group__0__Impl rule__Real_Value__Group__1 ;
	public final void rule__Real_Value__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8541:1: ( rule__Real_Value__Group__0__Impl
			// rule__Real_Value__Group__1 )
			// InternalStructuredTextParser.g:8542:2: rule__Real_Value__Group__0__Impl
			// rule__Real_Value__Group__1
			{
				pushFollow(FOLLOW_65);
				rule__Real_Value__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Real_Value__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group__0"

	// $ANTLR start "rule__Real_Value__Group__0__Impl"
	// InternalStructuredTextParser.g:8549:1: rule__Real_Value__Group__0__Impl : (
	// ruleSigned_Int ) ;
	public final void rule__Real_Value__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8553:1: ( ( ruleSigned_Int ) )
			// InternalStructuredTextParser.g:8554:1: ( ruleSigned_Int )
			{
				// InternalStructuredTextParser.g:8554:1: ( ruleSigned_Int )
				// InternalStructuredTextParser.g:8555:2: ruleSigned_Int
				{
					before(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleSigned_Int();

					state._fsp--;

					after(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group__0__Impl"

	// $ANTLR start "rule__Real_Value__Group__1"
	// InternalStructuredTextParser.g:8564:1: rule__Real_Value__Group__1 :
	// rule__Real_Value__Group__1__Impl rule__Real_Value__Group__2 ;
	public final void rule__Real_Value__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8568:1: ( rule__Real_Value__Group__1__Impl
			// rule__Real_Value__Group__2 )
			// InternalStructuredTextParser.g:8569:2: rule__Real_Value__Group__1__Impl
			// rule__Real_Value__Group__2
			{
				pushFollow(FOLLOW_11);
				rule__Real_Value__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Real_Value__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group__1"

	// $ANTLR start "rule__Real_Value__Group__1__Impl"
	// InternalStructuredTextParser.g:8576:1: rule__Real_Value__Group__1__Impl : (
	// FullStop ) ;
	public final void rule__Real_Value__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8580:1: ( ( FullStop ) )
			// InternalStructuredTextParser.g:8581:1: ( FullStop )
			{
				// InternalStructuredTextParser.g:8581:1: ( FullStop )
				// InternalStructuredTextParser.g:8582:2: FullStop
				{
					before(grammarAccess.getReal_ValueAccess().getFullStopKeyword_1());
					match(input, FullStop, FOLLOW_2);
					after(grammarAccess.getReal_ValueAccess().getFullStopKeyword_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group__1__Impl"

	// $ANTLR start "rule__Real_Value__Group__2"
	// InternalStructuredTextParser.g:8591:1: rule__Real_Value__Group__2 :
	// rule__Real_Value__Group__2__Impl rule__Real_Value__Group__3 ;
	public final void rule__Real_Value__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8595:1: ( rule__Real_Value__Group__2__Impl
			// rule__Real_Value__Group__3 )
			// InternalStructuredTextParser.g:8596:2: rule__Real_Value__Group__2__Impl
			// rule__Real_Value__Group__3
			{
				pushFollow(FOLLOW_70);
				rule__Real_Value__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Real_Value__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group__2"

	// $ANTLR start "rule__Real_Value__Group__2__Impl"
	// InternalStructuredTextParser.g:8603:1: rule__Real_Value__Group__2__Impl : (
	// RULE_UNSIGNED_INT ) ;
	public final void rule__Real_Value__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8607:1: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:8608:1: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:8608:1: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:8609:2: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getReal_ValueAccess().getUNSIGNED_INTTerminalRuleCall_2());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getReal_ValueAccess().getUNSIGNED_INTTerminalRuleCall_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group__2__Impl"

	// $ANTLR start "rule__Real_Value__Group__3"
	// InternalStructuredTextParser.g:8618:1: rule__Real_Value__Group__3 :
	// rule__Real_Value__Group__3__Impl ;
	public final void rule__Real_Value__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8622:1: ( rule__Real_Value__Group__3__Impl )
			// InternalStructuredTextParser.g:8623:2: rule__Real_Value__Group__3__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Real_Value__Group__3__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group__3"

	// $ANTLR start "rule__Real_Value__Group__3__Impl"
	// InternalStructuredTextParser.g:8629:1: rule__Real_Value__Group__3__Impl : ( (
	// rule__Real_Value__Group_3__0 )? ) ;
	public final void rule__Real_Value__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8633:1: ( ( ( rule__Real_Value__Group_3__0 )?
			// ) )
			// InternalStructuredTextParser.g:8634:1: ( ( rule__Real_Value__Group_3__0 )? )
			{
				// InternalStructuredTextParser.g:8634:1: ( ( rule__Real_Value__Group_3__0 )? )
				// InternalStructuredTextParser.g:8635:2: ( rule__Real_Value__Group_3__0 )?
				{
					before(grammarAccess.getReal_ValueAccess().getGroup_3());
					// InternalStructuredTextParser.g:8636:2: ( rule__Real_Value__Group_3__0 )?
					int alt69 = 2;
					int LA69_0 = input.LA(1);

					if ((LA69_0 == E)) {
						alt69 = 1;
					}
					switch (alt69) {
					case 1:
					// InternalStructuredTextParser.g:8636:3: rule__Real_Value__Group_3__0
					{
						pushFollow(FOLLOW_2);
						rule__Real_Value__Group_3__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getReal_ValueAccess().getGroup_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group__3__Impl"

	// $ANTLR start "rule__Real_Value__Group_3__0"
	// InternalStructuredTextParser.g:8645:1: rule__Real_Value__Group_3__0 :
	// rule__Real_Value__Group_3__0__Impl rule__Real_Value__Group_3__1 ;
	public final void rule__Real_Value__Group_3__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8649:1: ( rule__Real_Value__Group_3__0__Impl
			// rule__Real_Value__Group_3__1 )
			// InternalStructuredTextParser.g:8650:2: rule__Real_Value__Group_3__0__Impl
			// rule__Real_Value__Group_3__1
			{
				pushFollow(FOLLOW_68);
				rule__Real_Value__Group_3__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Real_Value__Group_3__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group_3__0"

	// $ANTLR start "rule__Real_Value__Group_3__0__Impl"
	// InternalStructuredTextParser.g:8657:1: rule__Real_Value__Group_3__0__Impl : (
	// E ) ;
	public final void rule__Real_Value__Group_3__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8661:1: ( ( E ) )
			// InternalStructuredTextParser.g:8662:1: ( E )
			{
				// InternalStructuredTextParser.g:8662:1: ( E )
				// InternalStructuredTextParser.g:8663:2: E
				{
					before(grammarAccess.getReal_ValueAccess().getEKeyword_3_0());
					match(input, E, FOLLOW_2);
					after(grammarAccess.getReal_ValueAccess().getEKeyword_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group_3__0__Impl"

	// $ANTLR start "rule__Real_Value__Group_3__1"
	// InternalStructuredTextParser.g:8672:1: rule__Real_Value__Group_3__1 :
	// rule__Real_Value__Group_3__1__Impl ;
	public final void rule__Real_Value__Group_3__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8676:1: ( rule__Real_Value__Group_3__1__Impl )
			// InternalStructuredTextParser.g:8677:2: rule__Real_Value__Group_3__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Real_Value__Group_3__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group_3__1"

	// $ANTLR start "rule__Real_Value__Group_3__1__Impl"
	// InternalStructuredTextParser.g:8683:1: rule__Real_Value__Group_3__1__Impl : (
	// ruleSigned_Int ) ;
	public final void rule__Real_Value__Group_3__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8687:1: ( ( ruleSigned_Int ) )
			// InternalStructuredTextParser.g:8688:1: ( ruleSigned_Int )
			{
				// InternalStructuredTextParser.g:8688:1: ( ruleSigned_Int )
				// InternalStructuredTextParser.g:8689:2: ruleSigned_Int
				{
					before(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_3_1());
					pushFollow(FOLLOW_2);
					ruleSigned_Int();

					state._fsp--;

					after(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_3_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Value__Group_3__1__Impl"

	// $ANTLR start "rule__Bool_Literal__Group__0"
	// InternalStructuredTextParser.g:8699:1: rule__Bool_Literal__Group__0 :
	// rule__Bool_Literal__Group__0__Impl rule__Bool_Literal__Group__1 ;
	public final void rule__Bool_Literal__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8703:1: ( rule__Bool_Literal__Group__0__Impl
			// rule__Bool_Literal__Group__1 )
			// InternalStructuredTextParser.g:8704:2: rule__Bool_Literal__Group__0__Impl
			// rule__Bool_Literal__Group__1
			{
				pushFollow(FOLLOW_13);
				rule__Bool_Literal__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Bool_Literal__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Literal__Group__0"

	// $ANTLR start "rule__Bool_Literal__Group__0__Impl"
	// InternalStructuredTextParser.g:8711:1: rule__Bool_Literal__Group__0__Impl : (
	// ( rule__Bool_Literal__Group_0__0 )? ) ;
	public final void rule__Bool_Literal__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8715:1: ( ( ( rule__Bool_Literal__Group_0__0
			// )? ) )
			// InternalStructuredTextParser.g:8716:1: ( ( rule__Bool_Literal__Group_0__0 )?
			// )
			{
				// InternalStructuredTextParser.g:8716:1: ( ( rule__Bool_Literal__Group_0__0 )?
				// )
				// InternalStructuredTextParser.g:8717:2: ( rule__Bool_Literal__Group_0__0 )?
				{
					before(grammarAccess.getBool_LiteralAccess().getGroup_0());
					// InternalStructuredTextParser.g:8718:2: ( rule__Bool_Literal__Group_0__0 )?
					int alt70 = 2;
					int LA70_0 = input.LA(1);

					if ((LA70_0 == BOOL)) {
						alt70 = 1;
					}
					switch (alt70) {
					case 1:
					// InternalStructuredTextParser.g:8718:3: rule__Bool_Literal__Group_0__0
					{
						pushFollow(FOLLOW_2);
						rule__Bool_Literal__Group_0__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getBool_LiteralAccess().getGroup_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Literal__Group__0__Impl"

	// $ANTLR start "rule__Bool_Literal__Group__1"
	// InternalStructuredTextParser.g:8726:1: rule__Bool_Literal__Group__1 :
	// rule__Bool_Literal__Group__1__Impl ;
	public final void rule__Bool_Literal__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8730:1: ( rule__Bool_Literal__Group__1__Impl )
			// InternalStructuredTextParser.g:8731:2: rule__Bool_Literal__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Bool_Literal__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Literal__Group__1"

	// $ANTLR start "rule__Bool_Literal__Group__1__Impl"
	// InternalStructuredTextParser.g:8737:1: rule__Bool_Literal__Group__1__Impl : (
	// ( rule__Bool_Literal__ValueAssignment_1 ) ) ;
	public final void rule__Bool_Literal__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8741:1: ( ( (
			// rule__Bool_Literal__ValueAssignment_1 ) ) )
			// InternalStructuredTextParser.g:8742:1: ( (
			// rule__Bool_Literal__ValueAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:8742:1: ( (
				// rule__Bool_Literal__ValueAssignment_1 ) )
				// InternalStructuredTextParser.g:8743:2: (
				// rule__Bool_Literal__ValueAssignment_1 )
				{
					before(grammarAccess.getBool_LiteralAccess().getValueAssignment_1());
					// InternalStructuredTextParser.g:8744:2: (
					// rule__Bool_Literal__ValueAssignment_1 )
					// InternalStructuredTextParser.g:8744:3: rule__Bool_Literal__ValueAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Bool_Literal__ValueAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getBool_LiteralAccess().getValueAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Literal__Group__1__Impl"

	// $ANTLR start "rule__Bool_Literal__Group_0__0"
	// InternalStructuredTextParser.g:8753:1: rule__Bool_Literal__Group_0__0 :
	// rule__Bool_Literal__Group_0__0__Impl rule__Bool_Literal__Group_0__1 ;
	public final void rule__Bool_Literal__Group_0__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8757:1: ( rule__Bool_Literal__Group_0__0__Impl
			// rule__Bool_Literal__Group_0__1 )
			// InternalStructuredTextParser.g:8758:2: rule__Bool_Literal__Group_0__0__Impl
			// rule__Bool_Literal__Group_0__1
			{
				pushFollow(FOLLOW_67);
				rule__Bool_Literal__Group_0__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Bool_Literal__Group_0__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Literal__Group_0__0"

	// $ANTLR start "rule__Bool_Literal__Group_0__0__Impl"
	// InternalStructuredTextParser.g:8765:1: rule__Bool_Literal__Group_0__0__Impl :
	// ( ( rule__Bool_Literal__TypeAssignment_0_0 ) ) ;
	public final void rule__Bool_Literal__Group_0__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8769:1: ( ( (
			// rule__Bool_Literal__TypeAssignment_0_0 ) ) )
			// InternalStructuredTextParser.g:8770:1: ( (
			// rule__Bool_Literal__TypeAssignment_0_0 ) )
			{
				// InternalStructuredTextParser.g:8770:1: ( (
				// rule__Bool_Literal__TypeAssignment_0_0 ) )
				// InternalStructuredTextParser.g:8771:2: (
				// rule__Bool_Literal__TypeAssignment_0_0 )
				{
					before(grammarAccess.getBool_LiteralAccess().getTypeAssignment_0_0());
					// InternalStructuredTextParser.g:8772:2: (
					// rule__Bool_Literal__TypeAssignment_0_0 )
					// InternalStructuredTextParser.g:8772:3: rule__Bool_Literal__TypeAssignment_0_0
					{
						pushFollow(FOLLOW_2);
						rule__Bool_Literal__TypeAssignment_0_0();

						state._fsp--;

					}

					after(grammarAccess.getBool_LiteralAccess().getTypeAssignment_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Literal__Group_0__0__Impl"

	// $ANTLR start "rule__Bool_Literal__Group_0__1"
	// InternalStructuredTextParser.g:8780:1: rule__Bool_Literal__Group_0__1 :
	// rule__Bool_Literal__Group_0__1__Impl ;
	public final void rule__Bool_Literal__Group_0__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8784:1: ( rule__Bool_Literal__Group_0__1__Impl
			// )
			// InternalStructuredTextParser.g:8785:2: rule__Bool_Literal__Group_0__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Bool_Literal__Group_0__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Literal__Group_0__1"

	// $ANTLR start "rule__Bool_Literal__Group_0__1__Impl"
	// InternalStructuredTextParser.g:8791:1: rule__Bool_Literal__Group_0__1__Impl :
	// ( NumberSign ) ;
	public final void rule__Bool_Literal__Group_0__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8795:1: ( ( NumberSign ) )
			// InternalStructuredTextParser.g:8796:1: ( NumberSign )
			{
				// InternalStructuredTextParser.g:8796:1: ( NumberSign )
				// InternalStructuredTextParser.g:8797:2: NumberSign
				{
					before(grammarAccess.getBool_LiteralAccess().getNumberSignKeyword_0_1());
					match(input, NumberSign, FOLLOW_2);
					after(grammarAccess.getBool_LiteralAccess().getNumberSignKeyword_0_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Literal__Group_0__1__Impl"

	// $ANTLR start "rule__Char_Literal__Group__0"
	// InternalStructuredTextParser.g:8807:1: rule__Char_Literal__Group__0 :
	// rule__Char_Literal__Group__0__Impl rule__Char_Literal__Group__1 ;
	public final void rule__Char_Literal__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8811:1: ( rule__Char_Literal__Group__0__Impl
			// rule__Char_Literal__Group__1 )
			// InternalStructuredTextParser.g:8812:2: rule__Char_Literal__Group__0__Impl
			// rule__Char_Literal__Group__1
			{
				pushFollow(FOLLOW_71);
				rule__Char_Literal__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Char_Literal__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__Group__0"

	// $ANTLR start "rule__Char_Literal__Group__0__Impl"
	// InternalStructuredTextParser.g:8819:1: rule__Char_Literal__Group__0__Impl : (
	// ( rule__Char_Literal__Group_0__0 )? ) ;
	public final void rule__Char_Literal__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8823:1: ( ( ( rule__Char_Literal__Group_0__0
			// )? ) )
			// InternalStructuredTextParser.g:8824:1: ( ( rule__Char_Literal__Group_0__0 )?
			// )
			{
				// InternalStructuredTextParser.g:8824:1: ( ( rule__Char_Literal__Group_0__0 )?
				// )
				// InternalStructuredTextParser.g:8825:2: ( rule__Char_Literal__Group_0__0 )?
				{
					before(grammarAccess.getChar_LiteralAccess().getGroup_0());
					// InternalStructuredTextParser.g:8826:2: ( rule__Char_Literal__Group_0__0 )?
					int alt71 = 2;
					int LA71_0 = input.LA(1);

					if ((LA71_0 == WSTRING || LA71_0 == STRING || LA71_0 == WCHAR || LA71_0 == CHAR)) {
						alt71 = 1;
					}
					switch (alt71) {
					case 1:
					// InternalStructuredTextParser.g:8826:3: rule__Char_Literal__Group_0__0
					{
						pushFollow(FOLLOW_2);
						rule__Char_Literal__Group_0__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getChar_LiteralAccess().getGroup_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__Group__0__Impl"

	// $ANTLR start "rule__Char_Literal__Group__1"
	// InternalStructuredTextParser.g:8834:1: rule__Char_Literal__Group__1 :
	// rule__Char_Literal__Group__1__Impl ;
	public final void rule__Char_Literal__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8838:1: ( rule__Char_Literal__Group__1__Impl )
			// InternalStructuredTextParser.g:8839:2: rule__Char_Literal__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Char_Literal__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__Group__1"

	// $ANTLR start "rule__Char_Literal__Group__1__Impl"
	// InternalStructuredTextParser.g:8845:1: rule__Char_Literal__Group__1__Impl : (
	// ( rule__Char_Literal__ValueAssignment_1 ) ) ;
	public final void rule__Char_Literal__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8849:1: ( ( (
			// rule__Char_Literal__ValueAssignment_1 ) ) )
			// InternalStructuredTextParser.g:8850:1: ( (
			// rule__Char_Literal__ValueAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:8850:1: ( (
				// rule__Char_Literal__ValueAssignment_1 ) )
				// InternalStructuredTextParser.g:8851:2: (
				// rule__Char_Literal__ValueAssignment_1 )
				{
					before(grammarAccess.getChar_LiteralAccess().getValueAssignment_1());
					// InternalStructuredTextParser.g:8852:2: (
					// rule__Char_Literal__ValueAssignment_1 )
					// InternalStructuredTextParser.g:8852:3: rule__Char_Literal__ValueAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Char_Literal__ValueAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getChar_LiteralAccess().getValueAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__Group__1__Impl"

	// $ANTLR start "rule__Char_Literal__Group_0__0"
	// InternalStructuredTextParser.g:8861:1: rule__Char_Literal__Group_0__0 :
	// rule__Char_Literal__Group_0__0__Impl rule__Char_Literal__Group_0__1 ;
	public final void rule__Char_Literal__Group_0__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8865:1: ( rule__Char_Literal__Group_0__0__Impl
			// rule__Char_Literal__Group_0__1 )
			// InternalStructuredTextParser.g:8866:2: rule__Char_Literal__Group_0__0__Impl
			// rule__Char_Literal__Group_0__1
			{
				pushFollow(FOLLOW_72);
				rule__Char_Literal__Group_0__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Char_Literal__Group_0__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__Group_0__0"

	// $ANTLR start "rule__Char_Literal__Group_0__0__Impl"
	// InternalStructuredTextParser.g:8873:1: rule__Char_Literal__Group_0__0__Impl :
	// ( ( rule__Char_Literal__TypeAssignment_0_0 ) ) ;
	public final void rule__Char_Literal__Group_0__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8877:1: ( ( (
			// rule__Char_Literal__TypeAssignment_0_0 ) ) )
			// InternalStructuredTextParser.g:8878:1: ( (
			// rule__Char_Literal__TypeAssignment_0_0 ) )
			{
				// InternalStructuredTextParser.g:8878:1: ( (
				// rule__Char_Literal__TypeAssignment_0_0 ) )
				// InternalStructuredTextParser.g:8879:2: (
				// rule__Char_Literal__TypeAssignment_0_0 )
				{
					before(grammarAccess.getChar_LiteralAccess().getTypeAssignment_0_0());
					// InternalStructuredTextParser.g:8880:2: (
					// rule__Char_Literal__TypeAssignment_0_0 )
					// InternalStructuredTextParser.g:8880:3: rule__Char_Literal__TypeAssignment_0_0
					{
						pushFollow(FOLLOW_2);
						rule__Char_Literal__TypeAssignment_0_0();

						state._fsp--;

					}

					after(grammarAccess.getChar_LiteralAccess().getTypeAssignment_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__Group_0__0__Impl"

	// $ANTLR start "rule__Char_Literal__Group_0__1"
	// InternalStructuredTextParser.g:8888:1: rule__Char_Literal__Group_0__1 :
	// rule__Char_Literal__Group_0__1__Impl rule__Char_Literal__Group_0__2 ;
	public final void rule__Char_Literal__Group_0__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8892:1: ( rule__Char_Literal__Group_0__1__Impl
			// rule__Char_Literal__Group_0__2 )
			// InternalStructuredTextParser.g:8893:2: rule__Char_Literal__Group_0__1__Impl
			// rule__Char_Literal__Group_0__2
			{
				pushFollow(FOLLOW_72);
				rule__Char_Literal__Group_0__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Char_Literal__Group_0__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__Group_0__1"

	// $ANTLR start "rule__Char_Literal__Group_0__1__Impl"
	// InternalStructuredTextParser.g:8900:1: rule__Char_Literal__Group_0__1__Impl :
	// ( ( rule__Char_Literal__LengthAssignment_0_1 )? ) ;
	public final void rule__Char_Literal__Group_0__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8904:1: ( ( (
			// rule__Char_Literal__LengthAssignment_0_1 )? ) )
			// InternalStructuredTextParser.g:8905:1: ( (
			// rule__Char_Literal__LengthAssignment_0_1 )? )
			{
				// InternalStructuredTextParser.g:8905:1: ( (
				// rule__Char_Literal__LengthAssignment_0_1 )? )
				// InternalStructuredTextParser.g:8906:2: (
				// rule__Char_Literal__LengthAssignment_0_1 )?
				{
					before(grammarAccess.getChar_LiteralAccess().getLengthAssignment_0_1());
					// InternalStructuredTextParser.g:8907:2: (
					// rule__Char_Literal__LengthAssignment_0_1 )?
					int alt72 = 2;
					int LA72_0 = input.LA(1);

					if ((LA72_0 == RULE_UNSIGNED_INT)) {
						alt72 = 1;
					}
					switch (alt72) {
					case 1:
					// InternalStructuredTextParser.g:8907:3:
					// rule__Char_Literal__LengthAssignment_0_1
					{
						pushFollow(FOLLOW_2);
						rule__Char_Literal__LengthAssignment_0_1();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getChar_LiteralAccess().getLengthAssignment_0_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__Group_0__1__Impl"

	// $ANTLR start "rule__Char_Literal__Group_0__2"
	// InternalStructuredTextParser.g:8915:1: rule__Char_Literal__Group_0__2 :
	// rule__Char_Literal__Group_0__2__Impl ;
	public final void rule__Char_Literal__Group_0__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8919:1: ( rule__Char_Literal__Group_0__2__Impl
			// )
			// InternalStructuredTextParser.g:8920:2: rule__Char_Literal__Group_0__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Char_Literal__Group_0__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__Group_0__2"

	// $ANTLR start "rule__Char_Literal__Group_0__2__Impl"
	// InternalStructuredTextParser.g:8926:1: rule__Char_Literal__Group_0__2__Impl :
	// ( NumberSign ) ;
	public final void rule__Char_Literal__Group_0__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8930:1: ( ( NumberSign ) )
			// InternalStructuredTextParser.g:8931:1: ( NumberSign )
			{
				// InternalStructuredTextParser.g:8931:1: ( NumberSign )
				// InternalStructuredTextParser.g:8932:2: NumberSign
				{
					before(grammarAccess.getChar_LiteralAccess().getNumberSignKeyword_0_2());
					match(input, NumberSign, FOLLOW_2);
					after(grammarAccess.getChar_LiteralAccess().getNumberSignKeyword_0_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__Group_0__2__Impl"

	// $ANTLR start "rule__Duration__Group__0"
	// InternalStructuredTextParser.g:8942:1: rule__Duration__Group__0 :
	// rule__Duration__Group__0__Impl rule__Duration__Group__1 ;
	public final void rule__Duration__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8946:1: ( rule__Duration__Group__0__Impl
			// rule__Duration__Group__1 )
			// InternalStructuredTextParser.g:8947:2: rule__Duration__Group__0__Impl
			// rule__Duration__Group__1
			{
				pushFollow(FOLLOW_67);
				rule__Duration__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Duration__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group__0"

	// $ANTLR start "rule__Duration__Group__0__Impl"
	// InternalStructuredTextParser.g:8954:1: rule__Duration__Group__0__Impl : ( (
	// rule__Duration__TypeAssignment_0 ) ) ;
	public final void rule__Duration__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8958:1: ( ( ( rule__Duration__TypeAssignment_0
			// ) ) )
			// InternalStructuredTextParser.g:8959:1: ( ( rule__Duration__TypeAssignment_0 )
			// )
			{
				// InternalStructuredTextParser.g:8959:1: ( ( rule__Duration__TypeAssignment_0 )
				// )
				// InternalStructuredTextParser.g:8960:2: ( rule__Duration__TypeAssignment_0 )
				{
					before(grammarAccess.getDurationAccess().getTypeAssignment_0());
					// InternalStructuredTextParser.g:8961:2: ( rule__Duration__TypeAssignment_0 )
					// InternalStructuredTextParser.g:8961:3: rule__Duration__TypeAssignment_0
					{
						pushFollow(FOLLOW_2);
						rule__Duration__TypeAssignment_0();

						state._fsp--;

					}

					after(grammarAccess.getDurationAccess().getTypeAssignment_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group__0__Impl"

	// $ANTLR start "rule__Duration__Group__1"
	// InternalStructuredTextParser.g:8969:1: rule__Duration__Group__1 :
	// rule__Duration__Group__1__Impl rule__Duration__Group__2 ;
	public final void rule__Duration__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8973:1: ( rule__Duration__Group__1__Impl
			// rule__Duration__Group__2 )
			// InternalStructuredTextParser.g:8974:2: rule__Duration__Group__1__Impl
			// rule__Duration__Group__2
			{
				pushFollow(FOLLOW_68);
				rule__Duration__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Duration__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group__1"

	// $ANTLR start "rule__Duration__Group__1__Impl"
	// InternalStructuredTextParser.g:8981:1: rule__Duration__Group__1__Impl : (
	// NumberSign ) ;
	public final void rule__Duration__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:8985:1: ( ( NumberSign ) )
			// InternalStructuredTextParser.g:8986:1: ( NumberSign )
			{
				// InternalStructuredTextParser.g:8986:1: ( NumberSign )
				// InternalStructuredTextParser.g:8987:2: NumberSign
				{
					before(grammarAccess.getDurationAccess().getNumberSignKeyword_1());
					match(input, NumberSign, FOLLOW_2);
					after(grammarAccess.getDurationAccess().getNumberSignKeyword_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group__1__Impl"

	// $ANTLR start "rule__Duration__Group__2"
	// InternalStructuredTextParser.g:8996:1: rule__Duration__Group__2 :
	// rule__Duration__Group__2__Impl rule__Duration__Group__3 ;
	public final void rule__Duration__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9000:1: ( rule__Duration__Group__2__Impl
			// rule__Duration__Group__3 )
			// InternalStructuredTextParser.g:9001:2: rule__Duration__Group__2__Impl
			// rule__Duration__Group__3
			{
				pushFollow(FOLLOW_68);
				rule__Duration__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Duration__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group__2"

	// $ANTLR start "rule__Duration__Group__2__Impl"
	// InternalStructuredTextParser.g:9008:1: rule__Duration__Group__2__Impl : ( (
	// rule__Duration__Alternatives_2 )? ) ;
	public final void rule__Duration__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9012:1: ( ( ( rule__Duration__Alternatives_2
			// )? ) )
			// InternalStructuredTextParser.g:9013:1: ( ( rule__Duration__Alternatives_2 )?
			// )
			{
				// InternalStructuredTextParser.g:9013:1: ( ( rule__Duration__Alternatives_2 )?
				// )
				// InternalStructuredTextParser.g:9014:2: ( rule__Duration__Alternatives_2 )?
				{
					before(grammarAccess.getDurationAccess().getAlternatives_2());
					// InternalStructuredTextParser.g:9015:2: ( rule__Duration__Alternatives_2 )?
					int alt73 = 2;
					int LA73_0 = input.LA(1);

					if ((LA73_0 == PlusSign || LA73_0 == HyphenMinus)) {
						alt73 = 1;
					}
					switch (alt73) {
					case 1:
					// InternalStructuredTextParser.g:9015:3: rule__Duration__Alternatives_2
					{
						pushFollow(FOLLOW_2);
						rule__Duration__Alternatives_2();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getDurationAccess().getAlternatives_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group__2__Impl"

	// $ANTLR start "rule__Duration__Group__3"
	// InternalStructuredTextParser.g:9023:1: rule__Duration__Group__3 :
	// rule__Duration__Group__3__Impl rule__Duration__Group__4 ;
	public final void rule__Duration__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9027:1: ( rule__Duration__Group__3__Impl
			// rule__Duration__Group__4 )
			// InternalStructuredTextParser.g:9028:2: rule__Duration__Group__3__Impl
			// rule__Duration__Group__4
			{
				pushFollow(FOLLOW_73);
				rule__Duration__Group__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Duration__Group__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group__3"

	// $ANTLR start "rule__Duration__Group__3__Impl"
	// InternalStructuredTextParser.g:9035:1: rule__Duration__Group__3__Impl : ( (
	// rule__Duration__ValueAssignment_3 ) ) ;
	public final void rule__Duration__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9039:1: ( ( (
			// rule__Duration__ValueAssignment_3 ) ) )
			// InternalStructuredTextParser.g:9040:1: ( ( rule__Duration__ValueAssignment_3
			// ) )
			{
				// InternalStructuredTextParser.g:9040:1: ( ( rule__Duration__ValueAssignment_3
				// ) )
				// InternalStructuredTextParser.g:9041:2: ( rule__Duration__ValueAssignment_3 )
				{
					before(grammarAccess.getDurationAccess().getValueAssignment_3());
					// InternalStructuredTextParser.g:9042:2: ( rule__Duration__ValueAssignment_3 )
					// InternalStructuredTextParser.g:9042:3: rule__Duration__ValueAssignment_3
					{
						pushFollow(FOLLOW_2);
						rule__Duration__ValueAssignment_3();

						state._fsp--;

					}

					after(grammarAccess.getDurationAccess().getValueAssignment_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group__3__Impl"

	// $ANTLR start "rule__Duration__Group__4"
	// InternalStructuredTextParser.g:9050:1: rule__Duration__Group__4 :
	// rule__Duration__Group__4__Impl ;
	public final void rule__Duration__Group__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9054:1: ( rule__Duration__Group__4__Impl )
			// InternalStructuredTextParser.g:9055:2: rule__Duration__Group__4__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Duration__Group__4__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group__4"

	// $ANTLR start "rule__Duration__Group__4__Impl"
	// InternalStructuredTextParser.g:9061:1: rule__Duration__Group__4__Impl : ( (
	// rule__Duration__Group_4__0 )* ) ;
	public final void rule__Duration__Group__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9065:1: ( ( ( rule__Duration__Group_4__0 )* )
			// )
			// InternalStructuredTextParser.g:9066:1: ( ( rule__Duration__Group_4__0 )* )
			{
				// InternalStructuredTextParser.g:9066:1: ( ( rule__Duration__Group_4__0 )* )
				// InternalStructuredTextParser.g:9067:2: ( rule__Duration__Group_4__0 )*
				{
					before(grammarAccess.getDurationAccess().getGroup_4());
					// InternalStructuredTextParser.g:9068:2: ( rule__Duration__Group_4__0 )*
					loop74: do {
						int alt74 = 2;
						int LA74_0 = input.LA(1);

						if ((LA74_0 == KW__)) {
							alt74 = 1;
						}

						switch (alt74) {
						case 1:
						// InternalStructuredTextParser.g:9068:3: rule__Duration__Group_4__0
						{
							pushFollow(FOLLOW_74);
							rule__Duration__Group_4__0();

							state._fsp--;

						}
							break;

						default:
							break loop74;
						}
					} while (true);

					after(grammarAccess.getDurationAccess().getGroup_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group__4__Impl"

	// $ANTLR start "rule__Duration__Group_4__0"
	// InternalStructuredTextParser.g:9077:1: rule__Duration__Group_4__0 :
	// rule__Duration__Group_4__0__Impl rule__Duration__Group_4__1 ;
	public final void rule__Duration__Group_4__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9081:1: ( rule__Duration__Group_4__0__Impl
			// rule__Duration__Group_4__1 )
			// InternalStructuredTextParser.g:9082:2: rule__Duration__Group_4__0__Impl
			// rule__Duration__Group_4__1
			{
				pushFollow(FOLLOW_68);
				rule__Duration__Group_4__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Duration__Group_4__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group_4__0"

	// $ANTLR start "rule__Duration__Group_4__0__Impl"
	// InternalStructuredTextParser.g:9089:1: rule__Duration__Group_4__0__Impl : (
	// KW__ ) ;
	public final void rule__Duration__Group_4__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9093:1: ( ( KW__ ) )
			// InternalStructuredTextParser.g:9094:1: ( KW__ )
			{
				// InternalStructuredTextParser.g:9094:1: ( KW__ )
				// InternalStructuredTextParser.g:9095:2: KW__
				{
					before(grammarAccess.getDurationAccess().get_Keyword_4_0());
					match(input, KW__, FOLLOW_2);
					after(grammarAccess.getDurationAccess().get_Keyword_4_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group_4__0__Impl"

	// $ANTLR start "rule__Duration__Group_4__1"
	// InternalStructuredTextParser.g:9104:1: rule__Duration__Group_4__1 :
	// rule__Duration__Group_4__1__Impl ;
	public final void rule__Duration__Group_4__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9108:1: ( rule__Duration__Group_4__1__Impl )
			// InternalStructuredTextParser.g:9109:2: rule__Duration__Group_4__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Duration__Group_4__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group_4__1"

	// $ANTLR start "rule__Duration__Group_4__1__Impl"
	// InternalStructuredTextParser.g:9115:1: rule__Duration__Group_4__1__Impl : ( (
	// rule__Duration__ValueAssignment_4_1 ) ) ;
	public final void rule__Duration__Group_4__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9119:1: ( ( (
			// rule__Duration__ValueAssignment_4_1 ) ) )
			// InternalStructuredTextParser.g:9120:1: ( (
			// rule__Duration__ValueAssignment_4_1 ) )
			{
				// InternalStructuredTextParser.g:9120:1: ( (
				// rule__Duration__ValueAssignment_4_1 ) )
				// InternalStructuredTextParser.g:9121:2: ( rule__Duration__ValueAssignment_4_1
				// )
				{
					before(grammarAccess.getDurationAccess().getValueAssignment_4_1());
					// InternalStructuredTextParser.g:9122:2: ( rule__Duration__ValueAssignment_4_1
					// )
					// InternalStructuredTextParser.g:9122:3: rule__Duration__ValueAssignment_4_1
					{
						pushFollow(FOLLOW_2);
						rule__Duration__ValueAssignment_4_1();

						state._fsp--;

					}

					after(grammarAccess.getDurationAccess().getValueAssignment_4_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__Group_4__1__Impl"

	// $ANTLR start "rule__Duration_Value__Group__0"
	// InternalStructuredTextParser.g:9131:1: rule__Duration_Value__Group__0 :
	// rule__Duration_Value__Group__0__Impl rule__Duration_Value__Group__1 ;
	public final void rule__Duration_Value__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9135:1: ( rule__Duration_Value__Group__0__Impl
			// rule__Duration_Value__Group__1 )
			// InternalStructuredTextParser.g:9136:2: rule__Duration_Value__Group__0__Impl
			// rule__Duration_Value__Group__1
			{
				pushFollow(FOLLOW_75);
				rule__Duration_Value__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Duration_Value__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration_Value__Group__0"

	// $ANTLR start "rule__Duration_Value__Group__0__Impl"
	// InternalStructuredTextParser.g:9143:1: rule__Duration_Value__Group__0__Impl :
	// ( ( rule__Duration_Value__ValueAssignment_0 ) ) ;
	public final void rule__Duration_Value__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9147:1: ( ( (
			// rule__Duration_Value__ValueAssignment_0 ) ) )
			// InternalStructuredTextParser.g:9148:1: ( (
			// rule__Duration_Value__ValueAssignment_0 ) )
			{
				// InternalStructuredTextParser.g:9148:1: ( (
				// rule__Duration_Value__ValueAssignment_0 ) )
				// InternalStructuredTextParser.g:9149:2: (
				// rule__Duration_Value__ValueAssignment_0 )
				{
					before(grammarAccess.getDuration_ValueAccess().getValueAssignment_0());
					// InternalStructuredTextParser.g:9150:2: (
					// rule__Duration_Value__ValueAssignment_0 )
					// InternalStructuredTextParser.g:9150:3:
					// rule__Duration_Value__ValueAssignment_0
					{
						pushFollow(FOLLOW_2);
						rule__Duration_Value__ValueAssignment_0();

						state._fsp--;

					}

					after(grammarAccess.getDuration_ValueAccess().getValueAssignment_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration_Value__Group__0__Impl"

	// $ANTLR start "rule__Duration_Value__Group__1"
	// InternalStructuredTextParser.g:9158:1: rule__Duration_Value__Group__1 :
	// rule__Duration_Value__Group__1__Impl ;
	public final void rule__Duration_Value__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9162:1: ( rule__Duration_Value__Group__1__Impl
			// )
			// InternalStructuredTextParser.g:9163:2: rule__Duration_Value__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Duration_Value__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration_Value__Group__1"

	// $ANTLR start "rule__Duration_Value__Group__1__Impl"
	// InternalStructuredTextParser.g:9169:1: rule__Duration_Value__Group__1__Impl :
	// ( ( rule__Duration_Value__UnitAssignment_1 ) ) ;
	public final void rule__Duration_Value__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9173:1: ( ( (
			// rule__Duration_Value__UnitAssignment_1 ) ) )
			// InternalStructuredTextParser.g:9174:1: ( (
			// rule__Duration_Value__UnitAssignment_1 ) )
			{
				// InternalStructuredTextParser.g:9174:1: ( (
				// rule__Duration_Value__UnitAssignment_1 ) )
				// InternalStructuredTextParser.g:9175:2: (
				// rule__Duration_Value__UnitAssignment_1 )
				{
					before(grammarAccess.getDuration_ValueAccess().getUnitAssignment_1());
					// InternalStructuredTextParser.g:9176:2: (
					// rule__Duration_Value__UnitAssignment_1 )
					// InternalStructuredTextParser.g:9176:3: rule__Duration_Value__UnitAssignment_1
					{
						pushFollow(FOLLOW_2);
						rule__Duration_Value__UnitAssignment_1();

						state._fsp--;

					}

					after(grammarAccess.getDuration_ValueAccess().getUnitAssignment_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration_Value__Group__1__Impl"

	// $ANTLR start "rule__Fix_Point__Group__0"
	// InternalStructuredTextParser.g:9185:1: rule__Fix_Point__Group__0 :
	// rule__Fix_Point__Group__0__Impl rule__Fix_Point__Group__1 ;
	public final void rule__Fix_Point__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9189:1: ( rule__Fix_Point__Group__0__Impl
			// rule__Fix_Point__Group__1 )
			// InternalStructuredTextParser.g:9190:2: rule__Fix_Point__Group__0__Impl
			// rule__Fix_Point__Group__1
			{
				pushFollow(FOLLOW_65);
				rule__Fix_Point__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Fix_Point__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Fix_Point__Group__0"

	// $ANTLR start "rule__Fix_Point__Group__0__Impl"
	// InternalStructuredTextParser.g:9197:1: rule__Fix_Point__Group__0__Impl : (
	// RULE_UNSIGNED_INT ) ;
	public final void rule__Fix_Point__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9201:1: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:9202:1: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:9202:1: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:9203:2: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getFix_PointAccess().getUNSIGNED_INTTerminalRuleCall_0());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getFix_PointAccess().getUNSIGNED_INTTerminalRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Fix_Point__Group__0__Impl"

	// $ANTLR start "rule__Fix_Point__Group__1"
	// InternalStructuredTextParser.g:9212:1: rule__Fix_Point__Group__1 :
	// rule__Fix_Point__Group__1__Impl ;
	public final void rule__Fix_Point__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9216:1: ( rule__Fix_Point__Group__1__Impl )
			// InternalStructuredTextParser.g:9217:2: rule__Fix_Point__Group__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Fix_Point__Group__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Fix_Point__Group__1"

	// $ANTLR start "rule__Fix_Point__Group__1__Impl"
	// InternalStructuredTextParser.g:9223:1: rule__Fix_Point__Group__1__Impl : ( (
	// rule__Fix_Point__Group_1__0 )? ) ;
	public final void rule__Fix_Point__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9227:1: ( ( ( rule__Fix_Point__Group_1__0 )? )
			// )
			// InternalStructuredTextParser.g:9228:1: ( ( rule__Fix_Point__Group_1__0 )? )
			{
				// InternalStructuredTextParser.g:9228:1: ( ( rule__Fix_Point__Group_1__0 )? )
				// InternalStructuredTextParser.g:9229:2: ( rule__Fix_Point__Group_1__0 )?
				{
					before(grammarAccess.getFix_PointAccess().getGroup_1());
					// InternalStructuredTextParser.g:9230:2: ( rule__Fix_Point__Group_1__0 )?
					int alt75 = 2;
					int LA75_0 = input.LA(1);

					if ((LA75_0 == FullStop)) {
						alt75 = 1;
					}
					switch (alt75) {
					case 1:
					// InternalStructuredTextParser.g:9230:3: rule__Fix_Point__Group_1__0
					{
						pushFollow(FOLLOW_2);
						rule__Fix_Point__Group_1__0();

						state._fsp--;

					}
						break;

					}

					after(grammarAccess.getFix_PointAccess().getGroup_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Fix_Point__Group__1__Impl"

	// $ANTLR start "rule__Fix_Point__Group_1__0"
	// InternalStructuredTextParser.g:9239:1: rule__Fix_Point__Group_1__0 :
	// rule__Fix_Point__Group_1__0__Impl rule__Fix_Point__Group_1__1 ;
	public final void rule__Fix_Point__Group_1__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9243:1: ( rule__Fix_Point__Group_1__0__Impl
			// rule__Fix_Point__Group_1__1 )
			// InternalStructuredTextParser.g:9244:2: rule__Fix_Point__Group_1__0__Impl
			// rule__Fix_Point__Group_1__1
			{
				pushFollow(FOLLOW_11);
				rule__Fix_Point__Group_1__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Fix_Point__Group_1__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Fix_Point__Group_1__0"

	// $ANTLR start "rule__Fix_Point__Group_1__0__Impl"
	// InternalStructuredTextParser.g:9251:1: rule__Fix_Point__Group_1__0__Impl : (
	// FullStop ) ;
	public final void rule__Fix_Point__Group_1__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9255:1: ( ( FullStop ) )
			// InternalStructuredTextParser.g:9256:1: ( FullStop )
			{
				// InternalStructuredTextParser.g:9256:1: ( FullStop )
				// InternalStructuredTextParser.g:9257:2: FullStop
				{
					before(grammarAccess.getFix_PointAccess().getFullStopKeyword_1_0());
					match(input, FullStop, FOLLOW_2);
					after(grammarAccess.getFix_PointAccess().getFullStopKeyword_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Fix_Point__Group_1__0__Impl"

	// $ANTLR start "rule__Fix_Point__Group_1__1"
	// InternalStructuredTextParser.g:9266:1: rule__Fix_Point__Group_1__1 :
	// rule__Fix_Point__Group_1__1__Impl ;
	public final void rule__Fix_Point__Group_1__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9270:1: ( rule__Fix_Point__Group_1__1__Impl )
			// InternalStructuredTextParser.g:9271:2: rule__Fix_Point__Group_1__1__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Fix_Point__Group_1__1__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Fix_Point__Group_1__1"

	// $ANTLR start "rule__Fix_Point__Group_1__1__Impl"
	// InternalStructuredTextParser.g:9277:1: rule__Fix_Point__Group_1__1__Impl : (
	// RULE_UNSIGNED_INT ) ;
	public final void rule__Fix_Point__Group_1__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9281:1: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:9282:1: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:9282:1: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:9283:2: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getFix_PointAccess().getUNSIGNED_INTTerminalRuleCall_1_1());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getFix_PointAccess().getUNSIGNED_INTTerminalRuleCall_1_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Fix_Point__Group_1__1__Impl"

	// $ANTLR start "rule__Time_Of_Day__Group__0"
	// InternalStructuredTextParser.g:9293:1: rule__Time_Of_Day__Group__0 :
	// rule__Time_Of_Day__Group__0__Impl rule__Time_Of_Day__Group__1 ;
	public final void rule__Time_Of_Day__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9297:1: ( rule__Time_Of_Day__Group__0__Impl
			// rule__Time_Of_Day__Group__1 )
			// InternalStructuredTextParser.g:9298:2: rule__Time_Of_Day__Group__0__Impl
			// rule__Time_Of_Day__Group__1
			{
				pushFollow(FOLLOW_67);
				rule__Time_Of_Day__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Time_Of_Day__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Time_Of_Day__Group__0"

	// $ANTLR start "rule__Time_Of_Day__Group__0__Impl"
	// InternalStructuredTextParser.g:9305:1: rule__Time_Of_Day__Group__0__Impl : (
	// ( rule__Time_Of_Day__TypeAssignment_0 ) ) ;
	public final void rule__Time_Of_Day__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9309:1: ( ( (
			// rule__Time_Of_Day__TypeAssignment_0 ) ) )
			// InternalStructuredTextParser.g:9310:1: ( (
			// rule__Time_Of_Day__TypeAssignment_0 ) )
			{
				// InternalStructuredTextParser.g:9310:1: ( (
				// rule__Time_Of_Day__TypeAssignment_0 ) )
				// InternalStructuredTextParser.g:9311:2: ( rule__Time_Of_Day__TypeAssignment_0
				// )
				{
					before(grammarAccess.getTime_Of_DayAccess().getTypeAssignment_0());
					// InternalStructuredTextParser.g:9312:2: ( rule__Time_Of_Day__TypeAssignment_0
					// )
					// InternalStructuredTextParser.g:9312:3: rule__Time_Of_Day__TypeAssignment_0
					{
						pushFollow(FOLLOW_2);
						rule__Time_Of_Day__TypeAssignment_0();

						state._fsp--;

					}

					after(grammarAccess.getTime_Of_DayAccess().getTypeAssignment_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Time_Of_Day__Group__0__Impl"

	// $ANTLR start "rule__Time_Of_Day__Group__1"
	// InternalStructuredTextParser.g:9320:1: rule__Time_Of_Day__Group__1 :
	// rule__Time_Of_Day__Group__1__Impl rule__Time_Of_Day__Group__2 ;
	public final void rule__Time_Of_Day__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9324:1: ( rule__Time_Of_Day__Group__1__Impl
			// rule__Time_Of_Day__Group__2 )
			// InternalStructuredTextParser.g:9325:2: rule__Time_Of_Day__Group__1__Impl
			// rule__Time_Of_Day__Group__2
			{
				pushFollow(FOLLOW_11);
				rule__Time_Of_Day__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Time_Of_Day__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Time_Of_Day__Group__1"

	// $ANTLR start "rule__Time_Of_Day__Group__1__Impl"
	// InternalStructuredTextParser.g:9332:1: rule__Time_Of_Day__Group__1__Impl : (
	// NumberSign ) ;
	public final void rule__Time_Of_Day__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9336:1: ( ( NumberSign ) )
			// InternalStructuredTextParser.g:9337:1: ( NumberSign )
			{
				// InternalStructuredTextParser.g:9337:1: ( NumberSign )
				// InternalStructuredTextParser.g:9338:2: NumberSign
				{
					before(grammarAccess.getTime_Of_DayAccess().getNumberSignKeyword_1());
					match(input, NumberSign, FOLLOW_2);
					after(grammarAccess.getTime_Of_DayAccess().getNumberSignKeyword_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Time_Of_Day__Group__1__Impl"

	// $ANTLR start "rule__Time_Of_Day__Group__2"
	// InternalStructuredTextParser.g:9347:1: rule__Time_Of_Day__Group__2 :
	// rule__Time_Of_Day__Group__2__Impl ;
	public final void rule__Time_Of_Day__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9351:1: ( rule__Time_Of_Day__Group__2__Impl )
			// InternalStructuredTextParser.g:9352:2: rule__Time_Of_Day__Group__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Time_Of_Day__Group__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Time_Of_Day__Group__2"

	// $ANTLR start "rule__Time_Of_Day__Group__2__Impl"
	// InternalStructuredTextParser.g:9358:1: rule__Time_Of_Day__Group__2__Impl : (
	// ( rule__Time_Of_Day__ValueAssignment_2 ) ) ;
	public final void rule__Time_Of_Day__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9362:1: ( ( (
			// rule__Time_Of_Day__ValueAssignment_2 ) ) )
			// InternalStructuredTextParser.g:9363:1: ( (
			// rule__Time_Of_Day__ValueAssignment_2 ) )
			{
				// InternalStructuredTextParser.g:9363:1: ( (
				// rule__Time_Of_Day__ValueAssignment_2 ) )
				// InternalStructuredTextParser.g:9364:2: ( rule__Time_Of_Day__ValueAssignment_2
				// )
				{
					before(grammarAccess.getTime_Of_DayAccess().getValueAssignment_2());
					// InternalStructuredTextParser.g:9365:2: ( rule__Time_Of_Day__ValueAssignment_2
					// )
					// InternalStructuredTextParser.g:9365:3: rule__Time_Of_Day__ValueAssignment_2
					{
						pushFollow(FOLLOW_2);
						rule__Time_Of_Day__ValueAssignment_2();

						state._fsp--;

					}

					after(grammarAccess.getTime_Of_DayAccess().getValueAssignment_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Time_Of_Day__Group__2__Impl"

	// $ANTLR start "rule__Daytime__Group__0"
	// InternalStructuredTextParser.g:9374:1: rule__Daytime__Group__0 :
	// rule__Daytime__Group__0__Impl rule__Daytime__Group__1 ;
	public final void rule__Daytime__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9378:1: ( rule__Daytime__Group__0__Impl
			// rule__Daytime__Group__1 )
			// InternalStructuredTextParser.g:9379:2: rule__Daytime__Group__0__Impl
			// rule__Daytime__Group__1
			{
				pushFollow(FOLLOW_8);
				rule__Daytime__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Daytime__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Daytime__Group__0"

	// $ANTLR start "rule__Daytime__Group__0__Impl"
	// InternalStructuredTextParser.g:9386:1: rule__Daytime__Group__0__Impl : (
	// ruleDay_Hour ) ;
	public final void rule__Daytime__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9390:1: ( ( ruleDay_Hour ) )
			// InternalStructuredTextParser.g:9391:1: ( ruleDay_Hour )
			{
				// InternalStructuredTextParser.g:9391:1: ( ruleDay_Hour )
				// InternalStructuredTextParser.g:9392:2: ruleDay_Hour
				{
					before(grammarAccess.getDaytimeAccess().getDay_HourParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleDay_Hour();

					state._fsp--;

					after(grammarAccess.getDaytimeAccess().getDay_HourParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Daytime__Group__0__Impl"

	// $ANTLR start "rule__Daytime__Group__1"
	// InternalStructuredTextParser.g:9401:1: rule__Daytime__Group__1 :
	// rule__Daytime__Group__1__Impl rule__Daytime__Group__2 ;
	public final void rule__Daytime__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9405:1: ( rule__Daytime__Group__1__Impl
			// rule__Daytime__Group__2 )
			// InternalStructuredTextParser.g:9406:2: rule__Daytime__Group__1__Impl
			// rule__Daytime__Group__2
			{
				pushFollow(FOLLOW_11);
				rule__Daytime__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Daytime__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Daytime__Group__1"

	// $ANTLR start "rule__Daytime__Group__1__Impl"
	// InternalStructuredTextParser.g:9413:1: rule__Daytime__Group__1__Impl : (
	// Colon ) ;
	public final void rule__Daytime__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9417:1: ( ( Colon ) )
			// InternalStructuredTextParser.g:9418:1: ( Colon )
			{
				// InternalStructuredTextParser.g:9418:1: ( Colon )
				// InternalStructuredTextParser.g:9419:2: Colon
				{
					before(grammarAccess.getDaytimeAccess().getColonKeyword_1());
					match(input, Colon, FOLLOW_2);
					after(grammarAccess.getDaytimeAccess().getColonKeyword_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Daytime__Group__1__Impl"

	// $ANTLR start "rule__Daytime__Group__2"
	// InternalStructuredTextParser.g:9428:1: rule__Daytime__Group__2 :
	// rule__Daytime__Group__2__Impl rule__Daytime__Group__3 ;
	public final void rule__Daytime__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9432:1: ( rule__Daytime__Group__2__Impl
			// rule__Daytime__Group__3 )
			// InternalStructuredTextParser.g:9433:2: rule__Daytime__Group__2__Impl
			// rule__Daytime__Group__3
			{
				pushFollow(FOLLOW_8);
				rule__Daytime__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Daytime__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Daytime__Group__2"

	// $ANTLR start "rule__Daytime__Group__2__Impl"
	// InternalStructuredTextParser.g:9440:1: rule__Daytime__Group__2__Impl : (
	// ruleDay_Minute ) ;
	public final void rule__Daytime__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9444:1: ( ( ruleDay_Minute ) )
			// InternalStructuredTextParser.g:9445:1: ( ruleDay_Minute )
			{
				// InternalStructuredTextParser.g:9445:1: ( ruleDay_Minute )
				// InternalStructuredTextParser.g:9446:2: ruleDay_Minute
				{
					before(grammarAccess.getDaytimeAccess().getDay_MinuteParserRuleCall_2());
					pushFollow(FOLLOW_2);
					ruleDay_Minute();

					state._fsp--;

					after(grammarAccess.getDaytimeAccess().getDay_MinuteParserRuleCall_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Daytime__Group__2__Impl"

	// $ANTLR start "rule__Daytime__Group__3"
	// InternalStructuredTextParser.g:9455:1: rule__Daytime__Group__3 :
	// rule__Daytime__Group__3__Impl rule__Daytime__Group__4 ;
	public final void rule__Daytime__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9459:1: ( rule__Daytime__Group__3__Impl
			// rule__Daytime__Group__4 )
			// InternalStructuredTextParser.g:9460:2: rule__Daytime__Group__3__Impl
			// rule__Daytime__Group__4
			{
				pushFollow(FOLLOW_68);
				rule__Daytime__Group__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Daytime__Group__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Daytime__Group__3"

	// $ANTLR start "rule__Daytime__Group__3__Impl"
	// InternalStructuredTextParser.g:9467:1: rule__Daytime__Group__3__Impl : (
	// Colon ) ;
	public final void rule__Daytime__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9471:1: ( ( Colon ) )
			// InternalStructuredTextParser.g:9472:1: ( Colon )
			{
				// InternalStructuredTextParser.g:9472:1: ( Colon )
				// InternalStructuredTextParser.g:9473:2: Colon
				{
					before(grammarAccess.getDaytimeAccess().getColonKeyword_3());
					match(input, Colon, FOLLOW_2);
					after(grammarAccess.getDaytimeAccess().getColonKeyword_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Daytime__Group__3__Impl"

	// $ANTLR start "rule__Daytime__Group__4"
	// InternalStructuredTextParser.g:9482:1: rule__Daytime__Group__4 :
	// rule__Daytime__Group__4__Impl ;
	public final void rule__Daytime__Group__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9486:1: ( rule__Daytime__Group__4__Impl )
			// InternalStructuredTextParser.g:9487:2: rule__Daytime__Group__4__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Daytime__Group__4__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Daytime__Group__4"

	// $ANTLR start "rule__Daytime__Group__4__Impl"
	// InternalStructuredTextParser.g:9493:1: rule__Daytime__Group__4__Impl : (
	// ruleDay_Second ) ;
	public final void rule__Daytime__Group__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9497:1: ( ( ruleDay_Second ) )
			// InternalStructuredTextParser.g:9498:1: ( ruleDay_Second )
			{
				// InternalStructuredTextParser.g:9498:1: ( ruleDay_Second )
				// InternalStructuredTextParser.g:9499:2: ruleDay_Second
				{
					before(grammarAccess.getDaytimeAccess().getDay_SecondParserRuleCall_4());
					pushFollow(FOLLOW_2);
					ruleDay_Second();

					state._fsp--;

					after(grammarAccess.getDaytimeAccess().getDay_SecondParserRuleCall_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Daytime__Group__4__Impl"

	// $ANTLR start "rule__Date__Group__0"
	// InternalStructuredTextParser.g:9509:1: rule__Date__Group__0 :
	// rule__Date__Group__0__Impl rule__Date__Group__1 ;
	public final void rule__Date__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9513:1: ( rule__Date__Group__0__Impl
			// rule__Date__Group__1 )
			// InternalStructuredTextParser.g:9514:2: rule__Date__Group__0__Impl
			// rule__Date__Group__1
			{
				pushFollow(FOLLOW_67);
				rule__Date__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Date__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date__Group__0"

	// $ANTLR start "rule__Date__Group__0__Impl"
	// InternalStructuredTextParser.g:9521:1: rule__Date__Group__0__Impl : ( (
	// rule__Date__TypeAssignment_0 ) ) ;
	public final void rule__Date__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9525:1: ( ( ( rule__Date__TypeAssignment_0 ) )
			// )
			// InternalStructuredTextParser.g:9526:1: ( ( rule__Date__TypeAssignment_0 ) )
			{
				// InternalStructuredTextParser.g:9526:1: ( ( rule__Date__TypeAssignment_0 ) )
				// InternalStructuredTextParser.g:9527:2: ( rule__Date__TypeAssignment_0 )
				{
					before(grammarAccess.getDateAccess().getTypeAssignment_0());
					// InternalStructuredTextParser.g:9528:2: ( rule__Date__TypeAssignment_0 )
					// InternalStructuredTextParser.g:9528:3: rule__Date__TypeAssignment_0
					{
						pushFollow(FOLLOW_2);
						rule__Date__TypeAssignment_0();

						state._fsp--;

					}

					after(grammarAccess.getDateAccess().getTypeAssignment_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date__Group__0__Impl"

	// $ANTLR start "rule__Date__Group__1"
	// InternalStructuredTextParser.g:9536:1: rule__Date__Group__1 :
	// rule__Date__Group__1__Impl rule__Date__Group__2 ;
	public final void rule__Date__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9540:1: ( rule__Date__Group__1__Impl
			// rule__Date__Group__2 )
			// InternalStructuredTextParser.g:9541:2: rule__Date__Group__1__Impl
			// rule__Date__Group__2
			{
				pushFollow(FOLLOW_11);
				rule__Date__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Date__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date__Group__1"

	// $ANTLR start "rule__Date__Group__1__Impl"
	// InternalStructuredTextParser.g:9548:1: rule__Date__Group__1__Impl : (
	// NumberSign ) ;
	public final void rule__Date__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9552:1: ( ( NumberSign ) )
			// InternalStructuredTextParser.g:9553:1: ( NumberSign )
			{
				// InternalStructuredTextParser.g:9553:1: ( NumberSign )
				// InternalStructuredTextParser.g:9554:2: NumberSign
				{
					before(grammarAccess.getDateAccess().getNumberSignKeyword_1());
					match(input, NumberSign, FOLLOW_2);
					after(grammarAccess.getDateAccess().getNumberSignKeyword_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date__Group__1__Impl"

	// $ANTLR start "rule__Date__Group__2"
	// InternalStructuredTextParser.g:9563:1: rule__Date__Group__2 :
	// rule__Date__Group__2__Impl ;
	public final void rule__Date__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9567:1: ( rule__Date__Group__2__Impl )
			// InternalStructuredTextParser.g:9568:2: rule__Date__Group__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Date__Group__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date__Group__2"

	// $ANTLR start "rule__Date__Group__2__Impl"
	// InternalStructuredTextParser.g:9574:1: rule__Date__Group__2__Impl : ( (
	// rule__Date__ValueAssignment_2 ) ) ;
	public final void rule__Date__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9578:1: ( ( ( rule__Date__ValueAssignment_2 )
			// ) )
			// InternalStructuredTextParser.g:9579:1: ( ( rule__Date__ValueAssignment_2 ) )
			{
				// InternalStructuredTextParser.g:9579:1: ( ( rule__Date__ValueAssignment_2 ) )
				// InternalStructuredTextParser.g:9580:2: ( rule__Date__ValueAssignment_2 )
				{
					before(grammarAccess.getDateAccess().getValueAssignment_2());
					// InternalStructuredTextParser.g:9581:2: ( rule__Date__ValueAssignment_2 )
					// InternalStructuredTextParser.g:9581:3: rule__Date__ValueAssignment_2
					{
						pushFollow(FOLLOW_2);
						rule__Date__ValueAssignment_2();

						state._fsp--;

					}

					after(grammarAccess.getDateAccess().getValueAssignment_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date__Group__2__Impl"

	// $ANTLR start "rule__Date_Literal__Group__0"
	// InternalStructuredTextParser.g:9590:1: rule__Date_Literal__Group__0 :
	// rule__Date_Literal__Group__0__Impl rule__Date_Literal__Group__1 ;
	public final void rule__Date_Literal__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9594:1: ( rule__Date_Literal__Group__0__Impl
			// rule__Date_Literal__Group__1 )
			// InternalStructuredTextParser.g:9595:2: rule__Date_Literal__Group__0__Impl
			// rule__Date_Literal__Group__1
			{
				pushFollow(FOLLOW_76);
				rule__Date_Literal__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Date_Literal__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Literal__Group__0"

	// $ANTLR start "rule__Date_Literal__Group__0__Impl"
	// InternalStructuredTextParser.g:9602:1: rule__Date_Literal__Group__0__Impl : (
	// ruleYear ) ;
	public final void rule__Date_Literal__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9606:1: ( ( ruleYear ) )
			// InternalStructuredTextParser.g:9607:1: ( ruleYear )
			{
				// InternalStructuredTextParser.g:9607:1: ( ruleYear )
				// InternalStructuredTextParser.g:9608:2: ruleYear
				{
					before(grammarAccess.getDate_LiteralAccess().getYearParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleYear();

					state._fsp--;

					after(grammarAccess.getDate_LiteralAccess().getYearParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Literal__Group__0__Impl"

	// $ANTLR start "rule__Date_Literal__Group__1"
	// InternalStructuredTextParser.g:9617:1: rule__Date_Literal__Group__1 :
	// rule__Date_Literal__Group__1__Impl rule__Date_Literal__Group__2 ;
	public final void rule__Date_Literal__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9621:1: ( rule__Date_Literal__Group__1__Impl
			// rule__Date_Literal__Group__2 )
			// InternalStructuredTextParser.g:9622:2: rule__Date_Literal__Group__1__Impl
			// rule__Date_Literal__Group__2
			{
				pushFollow(FOLLOW_11);
				rule__Date_Literal__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Date_Literal__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Literal__Group__1"

	// $ANTLR start "rule__Date_Literal__Group__1__Impl"
	// InternalStructuredTextParser.g:9629:1: rule__Date_Literal__Group__1__Impl : (
	// HyphenMinus ) ;
	public final void rule__Date_Literal__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9633:1: ( ( HyphenMinus ) )
			// InternalStructuredTextParser.g:9634:1: ( HyphenMinus )
			{
				// InternalStructuredTextParser.g:9634:1: ( HyphenMinus )
				// InternalStructuredTextParser.g:9635:2: HyphenMinus
				{
					before(grammarAccess.getDate_LiteralAccess().getHyphenMinusKeyword_1());
					match(input, HyphenMinus, FOLLOW_2);
					after(grammarAccess.getDate_LiteralAccess().getHyphenMinusKeyword_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Literal__Group__1__Impl"

	// $ANTLR start "rule__Date_Literal__Group__2"
	// InternalStructuredTextParser.g:9644:1: rule__Date_Literal__Group__2 :
	// rule__Date_Literal__Group__2__Impl rule__Date_Literal__Group__3 ;
	public final void rule__Date_Literal__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9648:1: ( rule__Date_Literal__Group__2__Impl
			// rule__Date_Literal__Group__3 )
			// InternalStructuredTextParser.g:9649:2: rule__Date_Literal__Group__2__Impl
			// rule__Date_Literal__Group__3
			{
				pushFollow(FOLLOW_76);
				rule__Date_Literal__Group__2__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Date_Literal__Group__3();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Literal__Group__2"

	// $ANTLR start "rule__Date_Literal__Group__2__Impl"
	// InternalStructuredTextParser.g:9656:1: rule__Date_Literal__Group__2__Impl : (
	// ruleMonth ) ;
	public final void rule__Date_Literal__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9660:1: ( ( ruleMonth ) )
			// InternalStructuredTextParser.g:9661:1: ( ruleMonth )
			{
				// InternalStructuredTextParser.g:9661:1: ( ruleMonth )
				// InternalStructuredTextParser.g:9662:2: ruleMonth
				{
					before(grammarAccess.getDate_LiteralAccess().getMonthParserRuleCall_2());
					pushFollow(FOLLOW_2);
					ruleMonth();

					state._fsp--;

					after(grammarAccess.getDate_LiteralAccess().getMonthParserRuleCall_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Literal__Group__2__Impl"

	// $ANTLR start "rule__Date_Literal__Group__3"
	// InternalStructuredTextParser.g:9671:1: rule__Date_Literal__Group__3 :
	// rule__Date_Literal__Group__3__Impl rule__Date_Literal__Group__4 ;
	public final void rule__Date_Literal__Group__3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9675:1: ( rule__Date_Literal__Group__3__Impl
			// rule__Date_Literal__Group__4 )
			// InternalStructuredTextParser.g:9676:2: rule__Date_Literal__Group__3__Impl
			// rule__Date_Literal__Group__4
			{
				pushFollow(FOLLOW_11);
				rule__Date_Literal__Group__3__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Date_Literal__Group__4();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Literal__Group__3"

	// $ANTLR start "rule__Date_Literal__Group__3__Impl"
	// InternalStructuredTextParser.g:9683:1: rule__Date_Literal__Group__3__Impl : (
	// HyphenMinus ) ;
	public final void rule__Date_Literal__Group__3__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9687:1: ( ( HyphenMinus ) )
			// InternalStructuredTextParser.g:9688:1: ( HyphenMinus )
			{
				// InternalStructuredTextParser.g:9688:1: ( HyphenMinus )
				// InternalStructuredTextParser.g:9689:2: HyphenMinus
				{
					before(grammarAccess.getDate_LiteralAccess().getHyphenMinusKeyword_3());
					match(input, HyphenMinus, FOLLOW_2);
					after(grammarAccess.getDate_LiteralAccess().getHyphenMinusKeyword_3());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Literal__Group__3__Impl"

	// $ANTLR start "rule__Date_Literal__Group__4"
	// InternalStructuredTextParser.g:9698:1: rule__Date_Literal__Group__4 :
	// rule__Date_Literal__Group__4__Impl ;
	public final void rule__Date_Literal__Group__4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9702:1: ( rule__Date_Literal__Group__4__Impl )
			// InternalStructuredTextParser.g:9703:2: rule__Date_Literal__Group__4__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Date_Literal__Group__4__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Literal__Group__4"

	// $ANTLR start "rule__Date_Literal__Group__4__Impl"
	// InternalStructuredTextParser.g:9709:1: rule__Date_Literal__Group__4__Impl : (
	// ruleDay ) ;
	public final void rule__Date_Literal__Group__4__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9713:1: ( ( ruleDay ) )
			// InternalStructuredTextParser.g:9714:1: ( ruleDay )
			{
				// InternalStructuredTextParser.g:9714:1: ( ruleDay )
				// InternalStructuredTextParser.g:9715:2: ruleDay
				{
					before(grammarAccess.getDate_LiteralAccess().getDayParserRuleCall_4());
					pushFollow(FOLLOW_2);
					ruleDay();

					state._fsp--;

					after(grammarAccess.getDate_LiteralAccess().getDayParserRuleCall_4());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_Literal__Group__4__Impl"

	// $ANTLR start "rule__Date_And_Time__Group__0"
	// InternalStructuredTextParser.g:9725:1: rule__Date_And_Time__Group__0 :
	// rule__Date_And_Time__Group__0__Impl rule__Date_And_Time__Group__1 ;
	public final void rule__Date_And_Time__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9729:1: ( rule__Date_And_Time__Group__0__Impl
			// rule__Date_And_Time__Group__1 )
			// InternalStructuredTextParser.g:9730:2: rule__Date_And_Time__Group__0__Impl
			// rule__Date_And_Time__Group__1
			{
				pushFollow(FOLLOW_67);
				rule__Date_And_Time__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Date_And_Time__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time__Group__0"

	// $ANTLR start "rule__Date_And_Time__Group__0__Impl"
	// InternalStructuredTextParser.g:9737:1: rule__Date_And_Time__Group__0__Impl :
	// ( ( rule__Date_And_Time__TypeAssignment_0 ) ) ;
	public final void rule__Date_And_Time__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9741:1: ( ( (
			// rule__Date_And_Time__TypeAssignment_0 ) ) )
			// InternalStructuredTextParser.g:9742:1: ( (
			// rule__Date_And_Time__TypeAssignment_0 ) )
			{
				// InternalStructuredTextParser.g:9742:1: ( (
				// rule__Date_And_Time__TypeAssignment_0 ) )
				// InternalStructuredTextParser.g:9743:2: (
				// rule__Date_And_Time__TypeAssignment_0 )
				{
					before(grammarAccess.getDate_And_TimeAccess().getTypeAssignment_0());
					// InternalStructuredTextParser.g:9744:2: (
					// rule__Date_And_Time__TypeAssignment_0 )
					// InternalStructuredTextParser.g:9744:3: rule__Date_And_Time__TypeAssignment_0
					{
						pushFollow(FOLLOW_2);
						rule__Date_And_Time__TypeAssignment_0();

						state._fsp--;

					}

					after(grammarAccess.getDate_And_TimeAccess().getTypeAssignment_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time__Group__0__Impl"

	// $ANTLR start "rule__Date_And_Time__Group__1"
	// InternalStructuredTextParser.g:9752:1: rule__Date_And_Time__Group__1 :
	// rule__Date_And_Time__Group__1__Impl rule__Date_And_Time__Group__2 ;
	public final void rule__Date_And_Time__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9756:1: ( rule__Date_And_Time__Group__1__Impl
			// rule__Date_And_Time__Group__2 )
			// InternalStructuredTextParser.g:9757:2: rule__Date_And_Time__Group__1__Impl
			// rule__Date_And_Time__Group__2
			{
				pushFollow(FOLLOW_11);
				rule__Date_And_Time__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Date_And_Time__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time__Group__1"

	// $ANTLR start "rule__Date_And_Time__Group__1__Impl"
	// InternalStructuredTextParser.g:9764:1: rule__Date_And_Time__Group__1__Impl :
	// ( NumberSign ) ;
	public final void rule__Date_And_Time__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9768:1: ( ( NumberSign ) )
			// InternalStructuredTextParser.g:9769:1: ( NumberSign )
			{
				// InternalStructuredTextParser.g:9769:1: ( NumberSign )
				// InternalStructuredTextParser.g:9770:2: NumberSign
				{
					before(grammarAccess.getDate_And_TimeAccess().getNumberSignKeyword_1());
					match(input, NumberSign, FOLLOW_2);
					after(grammarAccess.getDate_And_TimeAccess().getNumberSignKeyword_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time__Group__1__Impl"

	// $ANTLR start "rule__Date_And_Time__Group__2"
	// InternalStructuredTextParser.g:9779:1: rule__Date_And_Time__Group__2 :
	// rule__Date_And_Time__Group__2__Impl ;
	public final void rule__Date_And_Time__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9783:1: ( rule__Date_And_Time__Group__2__Impl
			// )
			// InternalStructuredTextParser.g:9784:2: rule__Date_And_Time__Group__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Date_And_Time__Group__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time__Group__2"

	// $ANTLR start "rule__Date_And_Time__Group__2__Impl"
	// InternalStructuredTextParser.g:9790:1: rule__Date_And_Time__Group__2__Impl :
	// ( ( rule__Date_And_Time__ValueAssignment_2 ) ) ;
	public final void rule__Date_And_Time__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9794:1: ( ( (
			// rule__Date_And_Time__ValueAssignment_2 ) ) )
			// InternalStructuredTextParser.g:9795:1: ( (
			// rule__Date_And_Time__ValueAssignment_2 ) )
			{
				// InternalStructuredTextParser.g:9795:1: ( (
				// rule__Date_And_Time__ValueAssignment_2 ) )
				// InternalStructuredTextParser.g:9796:2: (
				// rule__Date_And_Time__ValueAssignment_2 )
				{
					before(grammarAccess.getDate_And_TimeAccess().getValueAssignment_2());
					// InternalStructuredTextParser.g:9797:2: (
					// rule__Date_And_Time__ValueAssignment_2 )
					// InternalStructuredTextParser.g:9797:3: rule__Date_And_Time__ValueAssignment_2
					{
						pushFollow(FOLLOW_2);
						rule__Date_And_Time__ValueAssignment_2();

						state._fsp--;

					}

					after(grammarAccess.getDate_And_TimeAccess().getValueAssignment_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time__Group__2__Impl"

	// $ANTLR start "rule__Date_And_Time_Value__Group__0"
	// InternalStructuredTextParser.g:9806:1: rule__Date_And_Time_Value__Group__0 :
	// rule__Date_And_Time_Value__Group__0__Impl rule__Date_And_Time_Value__Group__1
	// ;
	public final void rule__Date_And_Time_Value__Group__0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9810:1: (
			// rule__Date_And_Time_Value__Group__0__Impl rule__Date_And_Time_Value__Group__1
			// )
			// InternalStructuredTextParser.g:9811:2:
			// rule__Date_And_Time_Value__Group__0__Impl rule__Date_And_Time_Value__Group__1
			{
				pushFollow(FOLLOW_76);
				rule__Date_And_Time_Value__Group__0__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Date_And_Time_Value__Group__1();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time_Value__Group__0"

	// $ANTLR start "rule__Date_And_Time_Value__Group__0__Impl"
	// InternalStructuredTextParser.g:9818:1:
	// rule__Date_And_Time_Value__Group__0__Impl : ( ruleDate_Literal ) ;
	public final void rule__Date_And_Time_Value__Group__0__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9822:1: ( ( ruleDate_Literal ) )
			// InternalStructuredTextParser.g:9823:1: ( ruleDate_Literal )
			{
				// InternalStructuredTextParser.g:9823:1: ( ruleDate_Literal )
				// InternalStructuredTextParser.g:9824:2: ruleDate_Literal
				{
					before(grammarAccess.getDate_And_Time_ValueAccess().getDate_LiteralParserRuleCall_0());
					pushFollow(FOLLOW_2);
					ruleDate_Literal();

					state._fsp--;

					after(grammarAccess.getDate_And_Time_ValueAccess().getDate_LiteralParserRuleCall_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time_Value__Group__0__Impl"

	// $ANTLR start "rule__Date_And_Time_Value__Group__1"
	// InternalStructuredTextParser.g:9833:1: rule__Date_And_Time_Value__Group__1 :
	// rule__Date_And_Time_Value__Group__1__Impl rule__Date_And_Time_Value__Group__2
	// ;
	public final void rule__Date_And_Time_Value__Group__1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9837:1: (
			// rule__Date_And_Time_Value__Group__1__Impl rule__Date_And_Time_Value__Group__2
			// )
			// InternalStructuredTextParser.g:9838:2:
			// rule__Date_And_Time_Value__Group__1__Impl rule__Date_And_Time_Value__Group__2
			{
				pushFollow(FOLLOW_11);
				rule__Date_And_Time_Value__Group__1__Impl();

				state._fsp--;

				pushFollow(FOLLOW_2);
				rule__Date_And_Time_Value__Group__2();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time_Value__Group__1"

	// $ANTLR start "rule__Date_And_Time_Value__Group__1__Impl"
	// InternalStructuredTextParser.g:9845:1:
	// rule__Date_And_Time_Value__Group__1__Impl : ( HyphenMinus ) ;
	public final void rule__Date_And_Time_Value__Group__1__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9849:1: ( ( HyphenMinus ) )
			// InternalStructuredTextParser.g:9850:1: ( HyphenMinus )
			{
				// InternalStructuredTextParser.g:9850:1: ( HyphenMinus )
				// InternalStructuredTextParser.g:9851:2: HyphenMinus
				{
					before(grammarAccess.getDate_And_Time_ValueAccess().getHyphenMinusKeyword_1());
					match(input, HyphenMinus, FOLLOW_2);
					after(grammarAccess.getDate_And_Time_ValueAccess().getHyphenMinusKeyword_1());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time_Value__Group__1__Impl"

	// $ANTLR start "rule__Date_And_Time_Value__Group__2"
	// InternalStructuredTextParser.g:9860:1: rule__Date_And_Time_Value__Group__2 :
	// rule__Date_And_Time_Value__Group__2__Impl ;
	public final void rule__Date_And_Time_Value__Group__2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9864:1: (
			// rule__Date_And_Time_Value__Group__2__Impl )
			// InternalStructuredTextParser.g:9865:2:
			// rule__Date_And_Time_Value__Group__2__Impl
			{
				pushFollow(FOLLOW_2);
				rule__Date_And_Time_Value__Group__2__Impl();

				state._fsp--;

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time_Value__Group__2"

	// $ANTLR start "rule__Date_And_Time_Value__Group__2__Impl"
	// InternalStructuredTextParser.g:9871:1:
	// rule__Date_And_Time_Value__Group__2__Impl : ( ruleDaytime ) ;
	public final void rule__Date_And_Time_Value__Group__2__Impl() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9875:1: ( ( ruleDaytime ) )
			// InternalStructuredTextParser.g:9876:1: ( ruleDaytime )
			{
				// InternalStructuredTextParser.g:9876:1: ( ruleDaytime )
				// InternalStructuredTextParser.g:9877:2: ruleDaytime
				{
					before(grammarAccess.getDate_And_Time_ValueAccess().getDaytimeParserRuleCall_2());
					pushFollow(FOLLOW_2);
					ruleDaytime();

					state._fsp--;

					after(grammarAccess.getDate_And_Time_ValueAccess().getDaytimeParserRuleCall_2());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time_Value__Group__2__Impl"

	// $ANTLR start "rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0"
	// InternalStructuredTextParser.g:9887:1:
	// rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 : (
	// ruleVar_Decl_Init ) ;
	public final void rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9891:1: ( ( ruleVar_Decl_Init ) )
			// InternalStructuredTextParser.g:9892:2: ( ruleVar_Decl_Init )
			{
				// InternalStructuredTextParser.g:9892:2: ( ruleVar_Decl_Init )
				// InternalStructuredTextParser.g:9893:3: ruleVar_Decl_Init
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess()
							.getLocalVariablesVar_Decl_InitParserRuleCall_1_1_0_0());
					pushFollow(FOLLOW_2);
					ruleVar_Decl_Init();

					state._fsp--;

					after(grammarAccess.getStructuredTextAlgorithmAccess()
							.getLocalVariablesVar_Decl_InitParserRuleCall_1_1_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0"

	// $ANTLR start "rule__StructuredTextAlgorithm__StatementsAssignment_2"
	// InternalStructuredTextParser.g:9902:1:
	// rule__StructuredTextAlgorithm__StatementsAssignment_2 : ( ruleStmt_List ) ;
	public final void rule__StructuredTextAlgorithm__StatementsAssignment_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9906:1: ( ( ruleStmt_List ) )
			// InternalStructuredTextParser.g:9907:2: ( ruleStmt_List )
			{
				// InternalStructuredTextParser.g:9907:2: ( ruleStmt_List )
				// InternalStructuredTextParser.g:9908:3: ruleStmt_List
				{
					before(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsStmt_ListParserRuleCall_2_0());
					pushFollow(FOLLOW_2);
					ruleStmt_List();

					state._fsp--;

					after(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsStmt_ListParserRuleCall_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__StructuredTextAlgorithm__StatementsAssignment_2"

	// $ANTLR start "rule__Var_Decl_Local__ConstantAssignment_1"
	// InternalStructuredTextParser.g:9917:1:
	// rule__Var_Decl_Local__ConstantAssignment_1 : ( ( CONSTANT ) ) ;
	public final void rule__Var_Decl_Local__ConstantAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9921:1: ( ( ( CONSTANT ) ) )
			// InternalStructuredTextParser.g:9922:2: ( ( CONSTANT ) )
			{
				// InternalStructuredTextParser.g:9922:2: ( ( CONSTANT ) )
				// InternalStructuredTextParser.g:9923:3: ( CONSTANT )
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0());
					// InternalStructuredTextParser.g:9924:3: ( CONSTANT )
					// InternalStructuredTextParser.g:9925:4: CONSTANT
					{
						before(grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0());
						match(input, CONSTANT, FOLLOW_2);
						after(grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0());

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__ConstantAssignment_1"

	// $ANTLR start "rule__Var_Decl_Local__NameAssignment_2"
	// InternalStructuredTextParser.g:9936:1: rule__Var_Decl_Local__NameAssignment_2
	// : ( RULE_ID ) ;
	public final void rule__Var_Decl_Local__NameAssignment_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9940:1: ( ( RULE_ID ) )
			// InternalStructuredTextParser.g:9941:2: ( RULE_ID )
			{
				// InternalStructuredTextParser.g:9941:2: ( RULE_ID )
				// InternalStructuredTextParser.g:9942:3: RULE_ID
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getNameIDTerminalRuleCall_2_0());
					match(input, RULE_ID, FOLLOW_2);
					after(grammarAccess.getVar_Decl_LocalAccess().getNameIDTerminalRuleCall_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__NameAssignment_2"

	// $ANTLR start "rule__Var_Decl_Local__TypeAssignment_4"
	// InternalStructuredTextParser.g:9951:1: rule__Var_Decl_Local__TypeAssignment_4
	// : ( ( ruleType_Name ) ) ;
	public final void rule__Var_Decl_Local__TypeAssignment_4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9955:1: ( ( ( ruleType_Name ) ) )
			// InternalStructuredTextParser.g:9956:2: ( ( ruleType_Name ) )
			{
				// InternalStructuredTextParser.g:9956:2: ( ( ruleType_Name ) )
				// InternalStructuredTextParser.g:9957:3: ( ruleType_Name )
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeCrossReference_4_0());
					// InternalStructuredTextParser.g:9958:3: ( ruleType_Name )
					// InternalStructuredTextParser.g:9959:4: ruleType_Name
					{
						before(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeType_NameParserRuleCall_4_0_1());
						pushFollow(FOLLOW_2);
						ruleType_Name();

						state._fsp--;

						after(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeType_NameParserRuleCall_4_0_1());

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeCrossReference_4_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__TypeAssignment_4"

	// $ANTLR start "rule__Var_Decl_Local__ArrayAssignment_5_0"
	// InternalStructuredTextParser.g:9970:1:
	// rule__Var_Decl_Local__ArrayAssignment_5_0 : ( ( LeftSquareBracket ) ) ;
	public final void rule__Var_Decl_Local__ArrayAssignment_5_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9974:1: ( ( ( LeftSquareBracket ) ) )
			// InternalStructuredTextParser.g:9975:2: ( ( LeftSquareBracket ) )
			{
				// InternalStructuredTextParser.g:9975:2: ( ( LeftSquareBracket ) )
				// InternalStructuredTextParser.g:9976:3: ( LeftSquareBracket )
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getArrayLeftSquareBracketKeyword_5_0_0());
					// InternalStructuredTextParser.g:9977:3: ( LeftSquareBracket )
					// InternalStructuredTextParser.g:9978:4: LeftSquareBracket
					{
						before(grammarAccess.getVar_Decl_LocalAccess().getArrayLeftSquareBracketKeyword_5_0_0());
						match(input, LeftSquareBracket, FOLLOW_2);
						after(grammarAccess.getVar_Decl_LocalAccess().getArrayLeftSquareBracketKeyword_5_0_0());

					}

					after(grammarAccess.getVar_Decl_LocalAccess().getArrayLeftSquareBracketKeyword_5_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__ArrayAssignment_5_0"

	// $ANTLR start "rule__Var_Decl_Local__ArraySizeAssignment_5_1"
	// InternalStructuredTextParser.g:9989:1:
	// rule__Var_Decl_Local__ArraySizeAssignment_5_1 : ( ruleArray_Size ) ;
	public final void rule__Var_Decl_Local__ArraySizeAssignment_5_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:9993:1: ( ( ruleArray_Size ) )
			// InternalStructuredTextParser.g:9994:2: ( ruleArray_Size )
			{
				// InternalStructuredTextParser.g:9994:2: ( ruleArray_Size )
				// InternalStructuredTextParser.g:9995:3: ruleArray_Size
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getArraySizeArray_SizeParserRuleCall_5_1_0());
					pushFollow(FOLLOW_2);
					ruleArray_Size();

					state._fsp--;

					after(grammarAccess.getVar_Decl_LocalAccess().getArraySizeArray_SizeParserRuleCall_5_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__ArraySizeAssignment_5_1"

	// $ANTLR start "rule__Var_Decl_Local__InitialValueAssignment_6_1"
	// InternalStructuredTextParser.g:10004:1:
	// rule__Var_Decl_Local__InitialValueAssignment_6_1 : ( ruleConstant ) ;
	public final void rule__Var_Decl_Local__InitialValueAssignment_6_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10008:1: ( ( ruleConstant ) )
			// InternalStructuredTextParser.g:10009:2: ( ruleConstant )
			{
				// InternalStructuredTextParser.g:10009:2: ( ruleConstant )
				// InternalStructuredTextParser.g:10010:3: ruleConstant
				{
					before(grammarAccess.getVar_Decl_LocalAccess().getInitialValueConstantParserRuleCall_6_1_0());
					pushFollow(FOLLOW_2);
					ruleConstant();

					state._fsp--;

					after(grammarAccess.getVar_Decl_LocalAccess().getInitialValueConstantParserRuleCall_6_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Local__InitialValueAssignment_6_1"

	// $ANTLR start "rule__Var_Decl_Located__NameAssignment_1"
	// InternalStructuredTextParser.g:10019:1:
	// rule__Var_Decl_Located__NameAssignment_1 : ( RULE_ID ) ;
	public final void rule__Var_Decl_Located__NameAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10023:1: ( ( RULE_ID ) )
			// InternalStructuredTextParser.g:10024:2: ( RULE_ID )
			{
				// InternalStructuredTextParser.g:10024:2: ( RULE_ID )
				// InternalStructuredTextParser.g:10025:3: RULE_ID
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getNameIDTerminalRuleCall_1_0());
					match(input, RULE_ID, FOLLOW_2);
					after(grammarAccess.getVar_Decl_LocatedAccess().getNameIDTerminalRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__NameAssignment_1"

	// $ANTLR start "rule__Var_Decl_Located__LocationAssignment_2_1"
	// InternalStructuredTextParser.g:10034:1:
	// rule__Var_Decl_Located__LocationAssignment_2_1 : ( ruleVariable ) ;
	public final void rule__Var_Decl_Located__LocationAssignment_2_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10038:1: ( ( ruleVariable ) )
			// InternalStructuredTextParser.g:10039:2: ( ruleVariable )
			{
				// InternalStructuredTextParser.g:10039:2: ( ruleVariable )
				// InternalStructuredTextParser.g:10040:3: ruleVariable
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getLocationVariableParserRuleCall_2_1_0());
					pushFollow(FOLLOW_2);
					ruleVariable();

					state._fsp--;

					after(grammarAccess.getVar_Decl_LocatedAccess().getLocationVariableParserRuleCall_2_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__LocationAssignment_2_1"

	// $ANTLR start "rule__Var_Decl_Located__TypeAssignment_4"
	// InternalStructuredTextParser.g:10049:1:
	// rule__Var_Decl_Located__TypeAssignment_4 : ( ( ruleType_Name ) ) ;
	public final void rule__Var_Decl_Located__TypeAssignment_4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10053:1: ( ( ( ruleType_Name ) ) )
			// InternalStructuredTextParser.g:10054:2: ( ( ruleType_Name ) )
			{
				// InternalStructuredTextParser.g:10054:2: ( ( ruleType_Name ) )
				// InternalStructuredTextParser.g:10055:3: ( ruleType_Name )
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getTypeDataTypeCrossReference_4_0());
					// InternalStructuredTextParser.g:10056:3: ( ruleType_Name )
					// InternalStructuredTextParser.g:10057:4: ruleType_Name
					{
						before(grammarAccess.getVar_Decl_LocatedAccess()
								.getTypeDataTypeType_NameParserRuleCall_4_0_1());
						pushFollow(FOLLOW_2);
						ruleType_Name();

						state._fsp--;

						after(grammarAccess.getVar_Decl_LocatedAccess().getTypeDataTypeType_NameParserRuleCall_4_0_1());

					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getTypeDataTypeCrossReference_4_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__TypeAssignment_4"

	// $ANTLR start "rule__Var_Decl_Located__ArrayAssignment_5_0"
	// InternalStructuredTextParser.g:10068:1:
	// rule__Var_Decl_Located__ArrayAssignment_5_0 : ( ( LeftSquareBracket ) ) ;
	public final void rule__Var_Decl_Located__ArrayAssignment_5_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10072:1: ( ( ( LeftSquareBracket ) ) )
			// InternalStructuredTextParser.g:10073:2: ( ( LeftSquareBracket ) )
			{
				// InternalStructuredTextParser.g:10073:2: ( ( LeftSquareBracket ) )
				// InternalStructuredTextParser.g:10074:3: ( LeftSquareBracket )
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getArrayLeftSquareBracketKeyword_5_0_0());
					// InternalStructuredTextParser.g:10075:3: ( LeftSquareBracket )
					// InternalStructuredTextParser.g:10076:4: LeftSquareBracket
					{
						before(grammarAccess.getVar_Decl_LocatedAccess().getArrayLeftSquareBracketKeyword_5_0_0());
						match(input, LeftSquareBracket, FOLLOW_2);
						after(grammarAccess.getVar_Decl_LocatedAccess().getArrayLeftSquareBracketKeyword_5_0_0());

					}

					after(grammarAccess.getVar_Decl_LocatedAccess().getArrayLeftSquareBracketKeyword_5_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__ArrayAssignment_5_0"

	// $ANTLR start "rule__Var_Decl_Located__ArraySizeAssignment_5_1"
	// InternalStructuredTextParser.g:10087:1:
	// rule__Var_Decl_Located__ArraySizeAssignment_5_1 : ( ruleArray_Size ) ;
	public final void rule__Var_Decl_Located__ArraySizeAssignment_5_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10091:1: ( ( ruleArray_Size ) )
			// InternalStructuredTextParser.g:10092:2: ( ruleArray_Size )
			{
				// InternalStructuredTextParser.g:10092:2: ( ruleArray_Size )
				// InternalStructuredTextParser.g:10093:3: ruleArray_Size
				{
					before(grammarAccess.getVar_Decl_LocatedAccess().getArraySizeArray_SizeParserRuleCall_5_1_0());
					pushFollow(FOLLOW_2);
					ruleArray_Size();

					state._fsp--;

					after(grammarAccess.getVar_Decl_LocatedAccess().getArraySizeArray_SizeParserRuleCall_5_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Var_Decl_Located__ArraySizeAssignment_5_1"

	// $ANTLR start "rule__Stmt_List__StatementsAssignment_1_0"
	// InternalStructuredTextParser.g:10102:1:
	// rule__Stmt_List__StatementsAssignment_1_0 : ( ruleStmt ) ;
	public final void rule__Stmt_List__StatementsAssignment_1_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10106:1: ( ( ruleStmt ) )
			// InternalStructuredTextParser.g:10107:2: ( ruleStmt )
			{
				// InternalStructuredTextParser.g:10107:2: ( ruleStmt )
				// InternalStructuredTextParser.g:10108:3: ruleStmt
				{
					before(grammarAccess.getStmt_ListAccess().getStatementsStmtParserRuleCall_1_0_0());
					pushFollow(FOLLOW_2);
					ruleStmt();

					state._fsp--;

					after(grammarAccess.getStmt_ListAccess().getStatementsStmtParserRuleCall_1_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Stmt_List__StatementsAssignment_1_0"

	// $ANTLR start "rule__Assign_Stmt__VariableAssignment_0"
	// InternalStructuredTextParser.g:10117:1:
	// rule__Assign_Stmt__VariableAssignment_0 : ( ruleVariable ) ;
	public final void rule__Assign_Stmt__VariableAssignment_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10121:1: ( ( ruleVariable ) )
			// InternalStructuredTextParser.g:10122:2: ( ruleVariable )
			{
				// InternalStructuredTextParser.g:10122:2: ( ruleVariable )
				// InternalStructuredTextParser.g:10123:3: ruleVariable
				{
					before(grammarAccess.getAssign_StmtAccess().getVariableVariableParserRuleCall_0_0());
					pushFollow(FOLLOW_2);
					ruleVariable();

					state._fsp--;

					after(grammarAccess.getAssign_StmtAccess().getVariableVariableParserRuleCall_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Assign_Stmt__VariableAssignment_0"

	// $ANTLR start "rule__Assign_Stmt__ExpressionAssignment_2"
	// InternalStructuredTextParser.g:10132:1:
	// rule__Assign_Stmt__ExpressionAssignment_2 : ( ruleExpression ) ;
	public final void rule__Assign_Stmt__ExpressionAssignment_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10136:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10137:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10137:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10138:3: ruleExpression
				{
					before(grammarAccess.getAssign_StmtAccess().getExpressionExpressionParserRuleCall_2_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getAssign_StmtAccess().getExpressionExpressionParserRuleCall_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Assign_Stmt__ExpressionAssignment_2"

	// $ANTLR start "rule__IF_Stmt__ExpressionAssignment_1"
	// InternalStructuredTextParser.g:10147:1: rule__IF_Stmt__ExpressionAssignment_1
	// : ( ruleExpression ) ;
	public final void rule__IF_Stmt__ExpressionAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10151:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10152:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10152:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10153:3: ruleExpression
				{
					before(grammarAccess.getIF_StmtAccess().getExpressionExpressionParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getIF_StmtAccess().getExpressionExpressionParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__ExpressionAssignment_1"

	// $ANTLR start "rule__IF_Stmt__StatmentsAssignment_3"
	// InternalStructuredTextParser.g:10162:1: rule__IF_Stmt__StatmentsAssignment_3
	// : ( ruleStmt_List ) ;
	public final void rule__IF_Stmt__StatmentsAssignment_3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10166:1: ( ( ruleStmt_List ) )
			// InternalStructuredTextParser.g:10167:2: ( ruleStmt_List )
			{
				// InternalStructuredTextParser.g:10167:2: ( ruleStmt_List )
				// InternalStructuredTextParser.g:10168:3: ruleStmt_List
				{
					before(grammarAccess.getIF_StmtAccess().getStatmentsStmt_ListParserRuleCall_3_0());
					pushFollow(FOLLOW_2);
					ruleStmt_List();

					state._fsp--;

					after(grammarAccess.getIF_StmtAccess().getStatmentsStmt_ListParserRuleCall_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__StatmentsAssignment_3"

	// $ANTLR start "rule__IF_Stmt__ElseifAssignment_4"
	// InternalStructuredTextParser.g:10177:1: rule__IF_Stmt__ElseifAssignment_4 : (
	// ruleELSIF_Clause ) ;
	public final void rule__IF_Stmt__ElseifAssignment_4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10181:1: ( ( ruleELSIF_Clause ) )
			// InternalStructuredTextParser.g:10182:2: ( ruleELSIF_Clause )
			{
				// InternalStructuredTextParser.g:10182:2: ( ruleELSIF_Clause )
				// InternalStructuredTextParser.g:10183:3: ruleELSIF_Clause
				{
					before(grammarAccess.getIF_StmtAccess().getElseifELSIF_ClauseParserRuleCall_4_0());
					pushFollow(FOLLOW_2);
					ruleELSIF_Clause();

					state._fsp--;

					after(grammarAccess.getIF_StmtAccess().getElseifELSIF_ClauseParserRuleCall_4_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__ElseifAssignment_4"

	// $ANTLR start "rule__IF_Stmt__ElseAssignment_5"
	// InternalStructuredTextParser.g:10192:1: rule__IF_Stmt__ElseAssignment_5 : (
	// ruleELSE_Clause ) ;
	public final void rule__IF_Stmt__ElseAssignment_5() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10196:1: ( ( ruleELSE_Clause ) )
			// InternalStructuredTextParser.g:10197:2: ( ruleELSE_Clause )
			{
				// InternalStructuredTextParser.g:10197:2: ( ruleELSE_Clause )
				// InternalStructuredTextParser.g:10198:3: ruleELSE_Clause
				{
					before(grammarAccess.getIF_StmtAccess().getElseELSE_ClauseParserRuleCall_5_0());
					pushFollow(FOLLOW_2);
					ruleELSE_Clause();

					state._fsp--;

					after(grammarAccess.getIF_StmtAccess().getElseELSE_ClauseParserRuleCall_5_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__IF_Stmt__ElseAssignment_5"

	// $ANTLR start "rule__ELSIF_Clause__ExpressionAssignment_1"
	// InternalStructuredTextParser.g:10207:1:
	// rule__ELSIF_Clause__ExpressionAssignment_1 : ( ruleExpression ) ;
	public final void rule__ELSIF_Clause__ExpressionAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10211:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10212:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10212:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10213:3: ruleExpression
				{
					before(grammarAccess.getELSIF_ClauseAccess().getExpressionExpressionParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getELSIF_ClauseAccess().getExpressionExpressionParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSIF_Clause__ExpressionAssignment_1"

	// $ANTLR start "rule__ELSIF_Clause__StatementsAssignment_3"
	// InternalStructuredTextParser.g:10222:1:
	// rule__ELSIF_Clause__StatementsAssignment_3 : ( ruleStmt_List ) ;
	public final void rule__ELSIF_Clause__StatementsAssignment_3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10226:1: ( ( ruleStmt_List ) )
			// InternalStructuredTextParser.g:10227:2: ( ruleStmt_List )
			{
				// InternalStructuredTextParser.g:10227:2: ( ruleStmt_List )
				// InternalStructuredTextParser.g:10228:3: ruleStmt_List
				{
					before(grammarAccess.getELSIF_ClauseAccess().getStatementsStmt_ListParserRuleCall_3_0());
					pushFollow(FOLLOW_2);
					ruleStmt_List();

					state._fsp--;

					after(grammarAccess.getELSIF_ClauseAccess().getStatementsStmt_ListParserRuleCall_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSIF_Clause__StatementsAssignment_3"

	// $ANTLR start "rule__ELSE_Clause__StatementsAssignment_1"
	// InternalStructuredTextParser.g:10237:1:
	// rule__ELSE_Clause__StatementsAssignment_1 : ( ruleStmt_List ) ;
	public final void rule__ELSE_Clause__StatementsAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10241:1: ( ( ruleStmt_List ) )
			// InternalStructuredTextParser.g:10242:2: ( ruleStmt_List )
			{
				// InternalStructuredTextParser.g:10242:2: ( ruleStmt_List )
				// InternalStructuredTextParser.g:10243:3: ruleStmt_List
				{
					before(grammarAccess.getELSE_ClauseAccess().getStatementsStmt_ListParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleStmt_List();

					state._fsp--;

					after(grammarAccess.getELSE_ClauseAccess().getStatementsStmt_ListParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__ELSE_Clause__StatementsAssignment_1"

	// $ANTLR start "rule__Case_Stmt__ExpressionAssignment_1"
	// InternalStructuredTextParser.g:10252:1:
	// rule__Case_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
	public final void rule__Case_Stmt__ExpressionAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10256:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10257:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10257:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10258:3: ruleExpression
				{
					before(grammarAccess.getCase_StmtAccess().getExpressionExpressionParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getCase_StmtAccess().getExpressionExpressionParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__ExpressionAssignment_1"

	// $ANTLR start "rule__Case_Stmt__CaseAssignment_3"
	// InternalStructuredTextParser.g:10267:1: rule__Case_Stmt__CaseAssignment_3 : (
	// ruleCase_Selection ) ;
	public final void rule__Case_Stmt__CaseAssignment_3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10271:1: ( ( ruleCase_Selection ) )
			// InternalStructuredTextParser.g:10272:2: ( ruleCase_Selection )
			{
				// InternalStructuredTextParser.g:10272:2: ( ruleCase_Selection )
				// InternalStructuredTextParser.g:10273:3: ruleCase_Selection
				{
					before(grammarAccess.getCase_StmtAccess().getCaseCase_SelectionParserRuleCall_3_0());
					pushFollow(FOLLOW_2);
					ruleCase_Selection();

					state._fsp--;

					after(grammarAccess.getCase_StmtAccess().getCaseCase_SelectionParserRuleCall_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__CaseAssignment_3"

	// $ANTLR start "rule__Case_Stmt__ElseAssignment_4"
	// InternalStructuredTextParser.g:10282:1: rule__Case_Stmt__ElseAssignment_4 : (
	// ruleELSE_Clause ) ;
	public final void rule__Case_Stmt__ElseAssignment_4() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10286:1: ( ( ruleELSE_Clause ) )
			// InternalStructuredTextParser.g:10287:2: ( ruleELSE_Clause )
			{
				// InternalStructuredTextParser.g:10287:2: ( ruleELSE_Clause )
				// InternalStructuredTextParser.g:10288:3: ruleELSE_Clause
				{
					before(grammarAccess.getCase_StmtAccess().getElseELSE_ClauseParserRuleCall_4_0());
					pushFollow(FOLLOW_2);
					ruleELSE_Clause();

					state._fsp--;

					after(grammarAccess.getCase_StmtAccess().getElseELSE_ClauseParserRuleCall_4_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Stmt__ElseAssignment_4"

	// $ANTLR start "rule__Case_Selection__CaseAssignment_0"
	// InternalStructuredTextParser.g:10297:1:
	// rule__Case_Selection__CaseAssignment_0 : ( ruleConstant ) ;
	public final void rule__Case_Selection__CaseAssignment_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10301:1: ( ( ruleConstant ) )
			// InternalStructuredTextParser.g:10302:2: ( ruleConstant )
			{
				// InternalStructuredTextParser.g:10302:2: ( ruleConstant )
				// InternalStructuredTextParser.g:10303:3: ruleConstant
				{
					before(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_0_0());
					pushFollow(FOLLOW_2);
					ruleConstant();

					state._fsp--;

					after(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__CaseAssignment_0"

	// $ANTLR start "rule__Case_Selection__CaseAssignment_1_1"
	// InternalStructuredTextParser.g:10312:1:
	// rule__Case_Selection__CaseAssignment_1_1 : ( ruleConstant ) ;
	public final void rule__Case_Selection__CaseAssignment_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10316:1: ( ( ruleConstant ) )
			// InternalStructuredTextParser.g:10317:2: ( ruleConstant )
			{
				// InternalStructuredTextParser.g:10317:2: ( ruleConstant )
				// InternalStructuredTextParser.g:10318:3: ruleConstant
				{
					before(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_1_1_0());
					pushFollow(FOLLOW_2);
					ruleConstant();

					state._fsp--;

					after(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__CaseAssignment_1_1"

	// $ANTLR start "rule__Case_Selection__StatementsAssignment_3"
	// InternalStructuredTextParser.g:10327:1:
	// rule__Case_Selection__StatementsAssignment_3 : ( ruleStmt_List ) ;
	public final void rule__Case_Selection__StatementsAssignment_3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10331:1: ( ( ruleStmt_List ) )
			// InternalStructuredTextParser.g:10332:2: ( ruleStmt_List )
			{
				// InternalStructuredTextParser.g:10332:2: ( ruleStmt_List )
				// InternalStructuredTextParser.g:10333:3: ruleStmt_List
				{
					before(grammarAccess.getCase_SelectionAccess().getStatementsStmt_ListParserRuleCall_3_0());
					pushFollow(FOLLOW_2);
					ruleStmt_List();

					state._fsp--;

					after(grammarAccess.getCase_SelectionAccess().getStatementsStmt_ListParserRuleCall_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Case_Selection__StatementsAssignment_3"

	// $ANTLR start "rule__For_Stmt__VariableAssignment_1"
	// InternalStructuredTextParser.g:10342:1: rule__For_Stmt__VariableAssignment_1
	// : ( ruleVariable_Primary ) ;
	public final void rule__For_Stmt__VariableAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10346:1: ( ( ruleVariable_Primary ) )
			// InternalStructuredTextParser.g:10347:2: ( ruleVariable_Primary )
			{
				// InternalStructuredTextParser.g:10347:2: ( ruleVariable_Primary )
				// InternalStructuredTextParser.g:10348:3: ruleVariable_Primary
				{
					before(grammarAccess.getFor_StmtAccess().getVariableVariable_PrimaryParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleVariable_Primary();

					state._fsp--;

					after(grammarAccess.getFor_StmtAccess().getVariableVariable_PrimaryParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__VariableAssignment_1"

	// $ANTLR start "rule__For_Stmt__FromAssignment_3"
	// InternalStructuredTextParser.g:10357:1: rule__For_Stmt__FromAssignment_3 : (
	// ruleExpression ) ;
	public final void rule__For_Stmt__FromAssignment_3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10361:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10362:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10362:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10363:3: ruleExpression
				{
					before(grammarAccess.getFor_StmtAccess().getFromExpressionParserRuleCall_3_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getFor_StmtAccess().getFromExpressionParserRuleCall_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__FromAssignment_3"

	// $ANTLR start "rule__For_Stmt__ToAssignment_5"
	// InternalStructuredTextParser.g:10372:1: rule__For_Stmt__ToAssignment_5 : (
	// ruleExpression ) ;
	public final void rule__For_Stmt__ToAssignment_5() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10376:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10377:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10377:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10378:3: ruleExpression
				{
					before(grammarAccess.getFor_StmtAccess().getToExpressionParserRuleCall_5_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getFor_StmtAccess().getToExpressionParserRuleCall_5_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__ToAssignment_5"

	// $ANTLR start "rule__For_Stmt__ByAssignment_6_1"
	// InternalStructuredTextParser.g:10387:1: rule__For_Stmt__ByAssignment_6_1 : (
	// ruleExpression ) ;
	public final void rule__For_Stmt__ByAssignment_6_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10391:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10392:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10392:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10393:3: ruleExpression
				{
					before(grammarAccess.getFor_StmtAccess().getByExpressionParserRuleCall_6_1_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getFor_StmtAccess().getByExpressionParserRuleCall_6_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__ByAssignment_6_1"

	// $ANTLR start "rule__For_Stmt__StatementsAssignment_8"
	// InternalStructuredTextParser.g:10402:1:
	// rule__For_Stmt__StatementsAssignment_8 : ( ruleStmt_List ) ;
	public final void rule__For_Stmt__StatementsAssignment_8() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10406:1: ( ( ruleStmt_List ) )
			// InternalStructuredTextParser.g:10407:2: ( ruleStmt_List )
			{
				// InternalStructuredTextParser.g:10407:2: ( ruleStmt_List )
				// InternalStructuredTextParser.g:10408:3: ruleStmt_List
				{
					before(grammarAccess.getFor_StmtAccess().getStatementsStmt_ListParserRuleCall_8_0());
					pushFollow(FOLLOW_2);
					ruleStmt_List();

					state._fsp--;

					after(grammarAccess.getFor_StmtAccess().getStatementsStmt_ListParserRuleCall_8_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__For_Stmt__StatementsAssignment_8"

	// $ANTLR start "rule__While_Stmt__ExpressionAssignment_1"
	// InternalStructuredTextParser.g:10417:1:
	// rule__While_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
	public final void rule__While_Stmt__ExpressionAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10421:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10422:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10422:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10423:3: ruleExpression
				{
					before(grammarAccess.getWhile_StmtAccess().getExpressionExpressionParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getWhile_StmtAccess().getExpressionExpressionParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__ExpressionAssignment_1"

	// $ANTLR start "rule__While_Stmt__StatementsAssignment_3"
	// InternalStructuredTextParser.g:10432:1:
	// rule__While_Stmt__StatementsAssignment_3 : ( ruleStmt_List ) ;
	public final void rule__While_Stmt__StatementsAssignment_3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10436:1: ( ( ruleStmt_List ) )
			// InternalStructuredTextParser.g:10437:2: ( ruleStmt_List )
			{
				// InternalStructuredTextParser.g:10437:2: ( ruleStmt_List )
				// InternalStructuredTextParser.g:10438:3: ruleStmt_List
				{
					before(grammarAccess.getWhile_StmtAccess().getStatementsStmt_ListParserRuleCall_3_0());
					pushFollow(FOLLOW_2);
					ruleStmt_List();

					state._fsp--;

					after(grammarAccess.getWhile_StmtAccess().getStatementsStmt_ListParserRuleCall_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__While_Stmt__StatementsAssignment_3"

	// $ANTLR start "rule__Repeat_Stmt__StatementsAssignment_1"
	// InternalStructuredTextParser.g:10447:1:
	// rule__Repeat_Stmt__StatementsAssignment_1 : ( ruleStmt_List ) ;
	public final void rule__Repeat_Stmt__StatementsAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10451:1: ( ( ruleStmt_List ) )
			// InternalStructuredTextParser.g:10452:2: ( ruleStmt_List )
			{
				// InternalStructuredTextParser.g:10452:2: ( ruleStmt_List )
				// InternalStructuredTextParser.g:10453:3: ruleStmt_List
				{
					before(grammarAccess.getRepeat_StmtAccess().getStatementsStmt_ListParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleStmt_List();

					state._fsp--;

					after(grammarAccess.getRepeat_StmtAccess().getStatementsStmt_ListParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__StatementsAssignment_1"

	// $ANTLR start "rule__Repeat_Stmt__ExpressionAssignment_3"
	// InternalStructuredTextParser.g:10462:1:
	// rule__Repeat_Stmt__ExpressionAssignment_3 : ( ruleExpression ) ;
	public final void rule__Repeat_Stmt__ExpressionAssignment_3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10466:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10467:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10467:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10468:3: ruleExpression
				{
					before(grammarAccess.getRepeat_StmtAccess().getExpressionExpressionParserRuleCall_3_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getRepeat_StmtAccess().getExpressionExpressionParserRuleCall_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Repeat_Stmt__ExpressionAssignment_3"

	// $ANTLR start "rule__Or_Expression__OperatorAssignment_1_1"
	// InternalStructuredTextParser.g:10477:1:
	// rule__Or_Expression__OperatorAssignment_1_1 : ( ruleOr_Operator ) ;
	public final void rule__Or_Expression__OperatorAssignment_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10481:1: ( ( ruleOr_Operator ) )
			// InternalStructuredTextParser.g:10482:2: ( ruleOr_Operator )
			{
				// InternalStructuredTextParser.g:10482:2: ( ruleOr_Operator )
				// InternalStructuredTextParser.g:10483:3: ruleOr_Operator
				{
					before(grammarAccess.getOr_ExpressionAccess().getOperatorOr_OperatorEnumRuleCall_1_1_0());
					pushFollow(FOLLOW_2);
					ruleOr_Operator();

					state._fsp--;

					after(grammarAccess.getOr_ExpressionAccess().getOperatorOr_OperatorEnumRuleCall_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__OperatorAssignment_1_1"

	// $ANTLR start "rule__Or_Expression__RightAssignment_1_2"
	// InternalStructuredTextParser.g:10492:1:
	// rule__Or_Expression__RightAssignment_1_2 : ( ruleXor_Expr ) ;
	public final void rule__Or_Expression__RightAssignment_1_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10496:1: ( ( ruleXor_Expr ) )
			// InternalStructuredTextParser.g:10497:2: ( ruleXor_Expr )
			{
				// InternalStructuredTextParser.g:10497:2: ( ruleXor_Expr )
				// InternalStructuredTextParser.g:10498:3: ruleXor_Expr
				{
					before(grammarAccess.getOr_ExpressionAccess().getRightXor_ExprParserRuleCall_1_2_0());
					pushFollow(FOLLOW_2);
					ruleXor_Expr();

					state._fsp--;

					after(grammarAccess.getOr_ExpressionAccess().getRightXor_ExprParserRuleCall_1_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Or_Expression__RightAssignment_1_2"

	// $ANTLR start "rule__Xor_Expr__OperatorAssignment_1_1"
	// InternalStructuredTextParser.g:10507:1:
	// rule__Xor_Expr__OperatorAssignment_1_1 : ( ruleXor_Operator ) ;
	public final void rule__Xor_Expr__OperatorAssignment_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10511:1: ( ( ruleXor_Operator ) )
			// InternalStructuredTextParser.g:10512:2: ( ruleXor_Operator )
			{
				// InternalStructuredTextParser.g:10512:2: ( ruleXor_Operator )
				// InternalStructuredTextParser.g:10513:3: ruleXor_Operator
				{
					before(grammarAccess.getXor_ExprAccess().getOperatorXor_OperatorEnumRuleCall_1_1_0());
					pushFollow(FOLLOW_2);
					ruleXor_Operator();

					state._fsp--;

					after(grammarAccess.getXor_ExprAccess().getOperatorXor_OperatorEnumRuleCall_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__OperatorAssignment_1_1"

	// $ANTLR start "rule__Xor_Expr__RightAssignment_1_2"
	// InternalStructuredTextParser.g:10522:1: rule__Xor_Expr__RightAssignment_1_2 :
	// ( ruleAnd_Expr ) ;
	public final void rule__Xor_Expr__RightAssignment_1_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10526:1: ( ( ruleAnd_Expr ) )
			// InternalStructuredTextParser.g:10527:2: ( ruleAnd_Expr )
			{
				// InternalStructuredTextParser.g:10527:2: ( ruleAnd_Expr )
				// InternalStructuredTextParser.g:10528:3: ruleAnd_Expr
				{
					before(grammarAccess.getXor_ExprAccess().getRightAnd_ExprParserRuleCall_1_2_0());
					pushFollow(FOLLOW_2);
					ruleAnd_Expr();

					state._fsp--;

					after(grammarAccess.getXor_ExprAccess().getRightAnd_ExprParserRuleCall_1_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Xor_Expr__RightAssignment_1_2"

	// $ANTLR start "rule__And_Expr__OperatorAssignment_1_1"
	// InternalStructuredTextParser.g:10537:1:
	// rule__And_Expr__OperatorAssignment_1_1 : ( ruleAnd_Operator ) ;
	public final void rule__And_Expr__OperatorAssignment_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10541:1: ( ( ruleAnd_Operator ) )
			// InternalStructuredTextParser.g:10542:2: ( ruleAnd_Operator )
			{
				// InternalStructuredTextParser.g:10542:2: ( ruleAnd_Operator )
				// InternalStructuredTextParser.g:10543:3: ruleAnd_Operator
				{
					before(grammarAccess.getAnd_ExprAccess().getOperatorAnd_OperatorEnumRuleCall_1_1_0());
					pushFollow(FOLLOW_2);
					ruleAnd_Operator();

					state._fsp--;

					after(grammarAccess.getAnd_ExprAccess().getOperatorAnd_OperatorEnumRuleCall_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__OperatorAssignment_1_1"

	// $ANTLR start "rule__And_Expr__RightAssignment_1_2"
	// InternalStructuredTextParser.g:10552:1: rule__And_Expr__RightAssignment_1_2 :
	// ( ruleCompare_Expr ) ;
	public final void rule__And_Expr__RightAssignment_1_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10556:1: ( ( ruleCompare_Expr ) )
			// InternalStructuredTextParser.g:10557:2: ( ruleCompare_Expr )
			{
				// InternalStructuredTextParser.g:10557:2: ( ruleCompare_Expr )
				// InternalStructuredTextParser.g:10558:3: ruleCompare_Expr
				{
					before(grammarAccess.getAnd_ExprAccess().getRightCompare_ExprParserRuleCall_1_2_0());
					pushFollow(FOLLOW_2);
					ruleCompare_Expr();

					state._fsp--;

					after(grammarAccess.getAnd_ExprAccess().getRightCompare_ExprParserRuleCall_1_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__And_Expr__RightAssignment_1_2"

	// $ANTLR start "rule__Compare_Expr__OperatorAssignment_1_1"
	// InternalStructuredTextParser.g:10567:1:
	// rule__Compare_Expr__OperatorAssignment_1_1 : ( ruleCompare_Operator ) ;
	public final void rule__Compare_Expr__OperatorAssignment_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10571:1: ( ( ruleCompare_Operator ) )
			// InternalStructuredTextParser.g:10572:2: ( ruleCompare_Operator )
			{
				// InternalStructuredTextParser.g:10572:2: ( ruleCompare_Operator )
				// InternalStructuredTextParser.g:10573:3: ruleCompare_Operator
				{
					before(grammarAccess.getCompare_ExprAccess().getOperatorCompare_OperatorEnumRuleCall_1_1_0());
					pushFollow(FOLLOW_2);
					ruleCompare_Operator();

					state._fsp--;

					after(grammarAccess.getCompare_ExprAccess().getOperatorCompare_OperatorEnumRuleCall_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__OperatorAssignment_1_1"

	// $ANTLR start "rule__Compare_Expr__RightAssignment_1_2"
	// InternalStructuredTextParser.g:10582:1:
	// rule__Compare_Expr__RightAssignment_1_2 : ( ruleEqu_Expr ) ;
	public final void rule__Compare_Expr__RightAssignment_1_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10586:1: ( ( ruleEqu_Expr ) )
			// InternalStructuredTextParser.g:10587:2: ( ruleEqu_Expr )
			{
				// InternalStructuredTextParser.g:10587:2: ( ruleEqu_Expr )
				// InternalStructuredTextParser.g:10588:3: ruleEqu_Expr
				{
					before(grammarAccess.getCompare_ExprAccess().getRightEqu_ExprParserRuleCall_1_2_0());
					pushFollow(FOLLOW_2);
					ruleEqu_Expr();

					state._fsp--;

					after(grammarAccess.getCompare_ExprAccess().getRightEqu_ExprParserRuleCall_1_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Compare_Expr__RightAssignment_1_2"

	// $ANTLR start "rule__Equ_Expr__OperatorAssignment_1_1"
	// InternalStructuredTextParser.g:10597:1:
	// rule__Equ_Expr__OperatorAssignment_1_1 : ( ruleEqu_Operator ) ;
	public final void rule__Equ_Expr__OperatorAssignment_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10601:1: ( ( ruleEqu_Operator ) )
			// InternalStructuredTextParser.g:10602:2: ( ruleEqu_Operator )
			{
				// InternalStructuredTextParser.g:10602:2: ( ruleEqu_Operator )
				// InternalStructuredTextParser.g:10603:3: ruleEqu_Operator
				{
					before(grammarAccess.getEqu_ExprAccess().getOperatorEqu_OperatorEnumRuleCall_1_1_0());
					pushFollow(FOLLOW_2);
					ruleEqu_Operator();

					state._fsp--;

					after(grammarAccess.getEqu_ExprAccess().getOperatorEqu_OperatorEnumRuleCall_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__OperatorAssignment_1_1"

	// $ANTLR start "rule__Equ_Expr__RightAssignment_1_2"
	// InternalStructuredTextParser.g:10612:1: rule__Equ_Expr__RightAssignment_1_2 :
	// ( ruleAdd_Expr ) ;
	public final void rule__Equ_Expr__RightAssignment_1_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10616:1: ( ( ruleAdd_Expr ) )
			// InternalStructuredTextParser.g:10617:2: ( ruleAdd_Expr )
			{
				// InternalStructuredTextParser.g:10617:2: ( ruleAdd_Expr )
				// InternalStructuredTextParser.g:10618:3: ruleAdd_Expr
				{
					before(grammarAccess.getEqu_ExprAccess().getRightAdd_ExprParserRuleCall_1_2_0());
					pushFollow(FOLLOW_2);
					ruleAdd_Expr();

					state._fsp--;

					after(grammarAccess.getEqu_ExprAccess().getRightAdd_ExprParserRuleCall_1_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Equ_Expr__RightAssignment_1_2"

	// $ANTLR start "rule__Add_Expr__OperatorAssignment_1_1"
	// InternalStructuredTextParser.g:10627:1:
	// rule__Add_Expr__OperatorAssignment_1_1 : ( ruleAdd_Operator ) ;
	public final void rule__Add_Expr__OperatorAssignment_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10631:1: ( ( ruleAdd_Operator ) )
			// InternalStructuredTextParser.g:10632:2: ( ruleAdd_Operator )
			{
				// InternalStructuredTextParser.g:10632:2: ( ruleAdd_Operator )
				// InternalStructuredTextParser.g:10633:3: ruleAdd_Operator
				{
					before(grammarAccess.getAdd_ExprAccess().getOperatorAdd_OperatorEnumRuleCall_1_1_0());
					pushFollow(FOLLOW_2);
					ruleAdd_Operator();

					state._fsp--;

					after(grammarAccess.getAdd_ExprAccess().getOperatorAdd_OperatorEnumRuleCall_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__OperatorAssignment_1_1"

	// $ANTLR start "rule__Add_Expr__RightAssignment_1_2"
	// InternalStructuredTextParser.g:10642:1: rule__Add_Expr__RightAssignment_1_2 :
	// ( ruleTerm ) ;
	public final void rule__Add_Expr__RightAssignment_1_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10646:1: ( ( ruleTerm ) )
			// InternalStructuredTextParser.g:10647:2: ( ruleTerm )
			{
				// InternalStructuredTextParser.g:10647:2: ( ruleTerm )
				// InternalStructuredTextParser.g:10648:3: ruleTerm
				{
					before(grammarAccess.getAdd_ExprAccess().getRightTermParserRuleCall_1_2_0());
					pushFollow(FOLLOW_2);
					ruleTerm();

					state._fsp--;

					after(grammarAccess.getAdd_ExprAccess().getRightTermParserRuleCall_1_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Add_Expr__RightAssignment_1_2"

	// $ANTLR start "rule__Term__OperatorAssignment_1_1"
	// InternalStructuredTextParser.g:10657:1: rule__Term__OperatorAssignment_1_1 :
	// ( ruleTerm_Operator ) ;
	public final void rule__Term__OperatorAssignment_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10661:1: ( ( ruleTerm_Operator ) )
			// InternalStructuredTextParser.g:10662:2: ( ruleTerm_Operator )
			{
				// InternalStructuredTextParser.g:10662:2: ( ruleTerm_Operator )
				// InternalStructuredTextParser.g:10663:3: ruleTerm_Operator
				{
					before(grammarAccess.getTermAccess().getOperatorTerm_OperatorEnumRuleCall_1_1_0());
					pushFollow(FOLLOW_2);
					ruleTerm_Operator();

					state._fsp--;

					after(grammarAccess.getTermAccess().getOperatorTerm_OperatorEnumRuleCall_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__OperatorAssignment_1_1"

	// $ANTLR start "rule__Term__RightAssignment_1_2"
	// InternalStructuredTextParser.g:10672:1: rule__Term__RightAssignment_1_2 : (
	// rulePower_Expr ) ;
	public final void rule__Term__RightAssignment_1_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10676:1: ( ( rulePower_Expr ) )
			// InternalStructuredTextParser.g:10677:2: ( rulePower_Expr )
			{
				// InternalStructuredTextParser.g:10677:2: ( rulePower_Expr )
				// InternalStructuredTextParser.g:10678:3: rulePower_Expr
				{
					before(grammarAccess.getTermAccess().getRightPower_ExprParserRuleCall_1_2_0());
					pushFollow(FOLLOW_2);
					rulePower_Expr();

					state._fsp--;

					after(grammarAccess.getTermAccess().getRightPower_ExprParserRuleCall_1_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Term__RightAssignment_1_2"

	// $ANTLR start "rule__Power_Expr__OperatorAssignment_1_1"
	// InternalStructuredTextParser.g:10687:1:
	// rule__Power_Expr__OperatorAssignment_1_1 : ( rulePower_Operator ) ;
	public final void rule__Power_Expr__OperatorAssignment_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10691:1: ( ( rulePower_Operator ) )
			// InternalStructuredTextParser.g:10692:2: ( rulePower_Operator )
			{
				// InternalStructuredTextParser.g:10692:2: ( rulePower_Operator )
				// InternalStructuredTextParser.g:10693:3: rulePower_Operator
				{
					before(grammarAccess.getPower_ExprAccess().getOperatorPower_OperatorEnumRuleCall_1_1_0());
					pushFollow(FOLLOW_2);
					rulePower_Operator();

					state._fsp--;

					after(grammarAccess.getPower_ExprAccess().getOperatorPower_OperatorEnumRuleCall_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__OperatorAssignment_1_1"

	// $ANTLR start "rule__Power_Expr__RightAssignment_1_2"
	// InternalStructuredTextParser.g:10702:1: rule__Power_Expr__RightAssignment_1_2
	// : ( ruleUnary_Expr ) ;
	public final void rule__Power_Expr__RightAssignment_1_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10706:1: ( ( ruleUnary_Expr ) )
			// InternalStructuredTextParser.g:10707:2: ( ruleUnary_Expr )
			{
				// InternalStructuredTextParser.g:10707:2: ( ruleUnary_Expr )
				// InternalStructuredTextParser.g:10708:3: ruleUnary_Expr
				{
					before(grammarAccess.getPower_ExprAccess().getRightUnary_ExprParserRuleCall_1_2_0());
					pushFollow(FOLLOW_2);
					ruleUnary_Expr();

					state._fsp--;

					after(grammarAccess.getPower_ExprAccess().getRightUnary_ExprParserRuleCall_1_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Power_Expr__RightAssignment_1_2"

	// $ANTLR start "rule__Unary_Expr__OperatorAssignment_0_1"
	// InternalStructuredTextParser.g:10717:1:
	// rule__Unary_Expr__OperatorAssignment_0_1 : ( ruleUnary_Operator ) ;
	public final void rule__Unary_Expr__OperatorAssignment_0_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10721:1: ( ( ruleUnary_Operator ) )
			// InternalStructuredTextParser.g:10722:2: ( ruleUnary_Operator )
			{
				// InternalStructuredTextParser.g:10722:2: ( ruleUnary_Operator )
				// InternalStructuredTextParser.g:10723:3: ruleUnary_Operator
				{
					before(grammarAccess.getUnary_ExprAccess().getOperatorUnary_OperatorEnumRuleCall_0_1_0());
					pushFollow(FOLLOW_2);
					ruleUnary_Operator();

					state._fsp--;

					after(grammarAccess.getUnary_ExprAccess().getOperatorUnary_OperatorEnumRuleCall_0_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Unary_Expr__OperatorAssignment_0_1"

	// $ANTLR start "rule__Unary_Expr__ExpressionAssignment_0_2"
	// InternalStructuredTextParser.g:10732:1:
	// rule__Unary_Expr__ExpressionAssignment_0_2 : ( rulePrimary_Expr ) ;
	public final void rule__Unary_Expr__ExpressionAssignment_0_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10736:1: ( ( rulePrimary_Expr ) )
			// InternalStructuredTextParser.g:10737:2: ( rulePrimary_Expr )
			{
				// InternalStructuredTextParser.g:10737:2: ( rulePrimary_Expr )
				// InternalStructuredTextParser.g:10738:3: rulePrimary_Expr
				{
					before(grammarAccess.getUnary_ExprAccess().getExpressionPrimary_ExprParserRuleCall_0_2_0());
					pushFollow(FOLLOW_2);
					rulePrimary_Expr();

					state._fsp--;

					after(grammarAccess.getUnary_ExprAccess().getExpressionPrimary_ExprParserRuleCall_0_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Unary_Expr__ExpressionAssignment_0_2"

	// $ANTLR start "rule__Func_Call__FuncAssignment_0"
	// InternalStructuredTextParser.g:10747:1: rule__Func_Call__FuncAssignment_0 : (
	// ( rule__Func_Call__FuncAlternatives_0_0 ) ) ;
	public final void rule__Func_Call__FuncAssignment_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10751:1: ( ( (
			// rule__Func_Call__FuncAlternatives_0_0 ) ) )
			// InternalStructuredTextParser.g:10752:2: ( (
			// rule__Func_Call__FuncAlternatives_0_0 ) )
			{
				// InternalStructuredTextParser.g:10752:2: ( (
				// rule__Func_Call__FuncAlternatives_0_0 ) )
				// InternalStructuredTextParser.g:10753:3: (
				// rule__Func_Call__FuncAlternatives_0_0 )
				{
					before(grammarAccess.getFunc_CallAccess().getFuncAlternatives_0_0());
					// InternalStructuredTextParser.g:10754:3: (
					// rule__Func_Call__FuncAlternatives_0_0 )
					// InternalStructuredTextParser.g:10754:4: rule__Func_Call__FuncAlternatives_0_0
					{
						pushFollow(FOLLOW_2);
						rule__Func_Call__FuncAlternatives_0_0();

						state._fsp--;

					}

					after(grammarAccess.getFunc_CallAccess().getFuncAlternatives_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__FuncAssignment_0"

	// $ANTLR start "rule__Func_Call__ArgsAssignment_2_0"
	// InternalStructuredTextParser.g:10762:1: rule__Func_Call__ArgsAssignment_2_0 :
	// ( ruleParam_Assign ) ;
	public final void rule__Func_Call__ArgsAssignment_2_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10766:1: ( ( ruleParam_Assign ) )
			// InternalStructuredTextParser.g:10767:2: ( ruleParam_Assign )
			{
				// InternalStructuredTextParser.g:10767:2: ( ruleParam_Assign )
				// InternalStructuredTextParser.g:10768:3: ruleParam_Assign
				{
					before(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_0_0());
					pushFollow(FOLLOW_2);
					ruleParam_Assign();

					state._fsp--;

					after(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__ArgsAssignment_2_0"

	// $ANTLR start "rule__Func_Call__ArgsAssignment_2_1_1"
	// InternalStructuredTextParser.g:10777:1: rule__Func_Call__ArgsAssignment_2_1_1
	// : ( ruleParam_Assign ) ;
	public final void rule__Func_Call__ArgsAssignment_2_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10781:1: ( ( ruleParam_Assign ) )
			// InternalStructuredTextParser.g:10782:2: ( ruleParam_Assign )
			{
				// InternalStructuredTextParser.g:10782:2: ( ruleParam_Assign )
				// InternalStructuredTextParser.g:10783:3: ruleParam_Assign
				{
					before(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_1_1_0());
					pushFollow(FOLLOW_2);
					ruleParam_Assign();

					state._fsp--;

					after(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Func_Call__ArgsAssignment_2_1_1"

	// $ANTLR start "rule__Param_Assign_In__VarAssignment_0_0"
	// InternalStructuredTextParser.g:10792:1:
	// rule__Param_Assign_In__VarAssignment_0_0 : ( RULE_ID ) ;
	public final void rule__Param_Assign_In__VarAssignment_0_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10796:1: ( ( RULE_ID ) )
			// InternalStructuredTextParser.g:10797:2: ( RULE_ID )
			{
				// InternalStructuredTextParser.g:10797:2: ( RULE_ID )
				// InternalStructuredTextParser.g:10798:3: RULE_ID
				{
					before(grammarAccess.getParam_Assign_InAccess().getVarIDTerminalRuleCall_0_0_0());
					match(input, RULE_ID, FOLLOW_2);
					after(grammarAccess.getParam_Assign_InAccess().getVarIDTerminalRuleCall_0_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_In__VarAssignment_0_0"

	// $ANTLR start "rule__Param_Assign_In__ExprAssignment_1"
	// InternalStructuredTextParser.g:10807:1:
	// rule__Param_Assign_In__ExprAssignment_1 : ( ruleExpression ) ;
	public final void rule__Param_Assign_In__ExprAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10811:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10812:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10812:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10813:3: ruleExpression
				{
					before(grammarAccess.getParam_Assign_InAccess().getExprExpressionParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getParam_Assign_InAccess().getExprExpressionParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_In__ExprAssignment_1"

	// $ANTLR start "rule__Param_Assign_Out__NotAssignment_0"
	// InternalStructuredTextParser.g:10822:1:
	// rule__Param_Assign_Out__NotAssignment_0 : ( ( NOT ) ) ;
	public final void rule__Param_Assign_Out__NotAssignment_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10826:1: ( ( ( NOT ) ) )
			// InternalStructuredTextParser.g:10827:2: ( ( NOT ) )
			{
				// InternalStructuredTextParser.g:10827:2: ( ( NOT ) )
				// InternalStructuredTextParser.g:10828:3: ( NOT )
				{
					before(grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0());
					// InternalStructuredTextParser.g:10829:3: ( NOT )
					// InternalStructuredTextParser.g:10830:4: NOT
					{
						before(grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0());
						match(input, NOT, FOLLOW_2);
						after(grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0());

					}

					after(grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__NotAssignment_0"

	// $ANTLR start "rule__Param_Assign_Out__VarAssignment_1"
	// InternalStructuredTextParser.g:10841:1:
	// rule__Param_Assign_Out__VarAssignment_1 : ( RULE_ID ) ;
	public final void rule__Param_Assign_Out__VarAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10845:1: ( ( RULE_ID ) )
			// InternalStructuredTextParser.g:10846:2: ( RULE_ID )
			{
				// InternalStructuredTextParser.g:10846:2: ( RULE_ID )
				// InternalStructuredTextParser.g:10847:3: RULE_ID
				{
					before(grammarAccess.getParam_Assign_OutAccess().getVarIDTerminalRuleCall_1_0());
					match(input, RULE_ID, FOLLOW_2);
					after(grammarAccess.getParam_Assign_OutAccess().getVarIDTerminalRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__VarAssignment_1"

	// $ANTLR start "rule__Param_Assign_Out__ExprAssignment_3"
	// InternalStructuredTextParser.g:10856:1:
	// rule__Param_Assign_Out__ExprAssignment_3 : ( ruleVariable ) ;
	public final void rule__Param_Assign_Out__ExprAssignment_3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10860:1: ( ( ruleVariable ) )
			// InternalStructuredTextParser.g:10861:2: ( ruleVariable )
			{
				// InternalStructuredTextParser.g:10861:2: ( ruleVariable )
				// InternalStructuredTextParser.g:10862:3: ruleVariable
				{
					before(grammarAccess.getParam_Assign_OutAccess().getExprVariableParserRuleCall_3_0());
					pushFollow(FOLLOW_2);
					ruleVariable();

					state._fsp--;

					after(grammarAccess.getParam_Assign_OutAccess().getExprVariableParserRuleCall_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Param_Assign_Out__ExprAssignment_3"

	// $ANTLR start "rule__Variable__PartAssignment_1"
	// InternalStructuredTextParser.g:10871:1: rule__Variable__PartAssignment_1 : (
	// ruleMultibit_Part_Access ) ;
	public final void rule__Variable__PartAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10875:1: ( ( ruleMultibit_Part_Access ) )
			// InternalStructuredTextParser.g:10876:2: ( ruleMultibit_Part_Access )
			{
				// InternalStructuredTextParser.g:10876:2: ( ruleMultibit_Part_Access )
				// InternalStructuredTextParser.g:10877:3: ruleMultibit_Part_Access
				{
					before(grammarAccess.getVariableAccess().getPartMultibit_Part_AccessParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleMultibit_Part_Access();

					state._fsp--;

					after(grammarAccess.getVariableAccess().getPartMultibit_Part_AccessParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable__PartAssignment_1"

	// $ANTLR start "rule__Variable_Subscript__IndexAssignment_1_2"
	// InternalStructuredTextParser.g:10886:1:
	// rule__Variable_Subscript__IndexAssignment_1_2 : ( ruleExpression ) ;
	public final void rule__Variable_Subscript__IndexAssignment_1_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10890:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10891:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10891:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10892:3: ruleExpression
				{
					before(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_2_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__IndexAssignment_1_2"

	// $ANTLR start "rule__Variable_Subscript__IndexAssignment_1_3_1"
	// InternalStructuredTextParser.g:10901:1:
	// rule__Variable_Subscript__IndexAssignment_1_3_1 : ( ruleExpression ) ;
	public final void rule__Variable_Subscript__IndexAssignment_1_3_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10905:1: ( ( ruleExpression ) )
			// InternalStructuredTextParser.g:10906:2: ( ruleExpression )
			{
				// InternalStructuredTextParser.g:10906:2: ( ruleExpression )
				// InternalStructuredTextParser.g:10907:3: ruleExpression
				{
					before(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_3_1_0());
					pushFollow(FOLLOW_2);
					ruleExpression();

					state._fsp--;

					after(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_3_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Subscript__IndexAssignment_1_3_1"

	// $ANTLR start "rule__Variable_Adapter__AdapterAssignment_1"
	// InternalStructuredTextParser.g:10916:1:
	// rule__Variable_Adapter__AdapterAssignment_1 : ( ( ruleAdapter_Name ) ) ;
	public final void rule__Variable_Adapter__AdapterAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10920:1: ( ( ( ruleAdapter_Name ) ) )
			// InternalStructuredTextParser.g:10921:2: ( ( ruleAdapter_Name ) )
			{
				// InternalStructuredTextParser.g:10921:2: ( ( ruleAdapter_Name ) )
				// InternalStructuredTextParser.g:10922:3: ( ruleAdapter_Name )
				{
					before(grammarAccess.getVariable_AdapterAccess().getAdapterAdapterDeclarationCrossReference_1_0());
					// InternalStructuredTextParser.g:10923:3: ( ruleAdapter_Name )
					// InternalStructuredTextParser.g:10924:4: ruleAdapter_Name
					{
						before(grammarAccess.getVariable_AdapterAccess()
								.getAdapterAdapterDeclarationAdapter_NameParserRuleCall_1_0_1());
						pushFollow(FOLLOW_2);
						ruleAdapter_Name();

						state._fsp--;

						after(grammarAccess.getVariable_AdapterAccess()
								.getAdapterAdapterDeclarationAdapter_NameParserRuleCall_1_0_1());

					}

					after(grammarAccess.getVariable_AdapterAccess().getAdapterAdapterDeclarationCrossReference_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Adapter__AdapterAssignment_1"

	// $ANTLR start "rule__Variable_Adapter__VarAssignment_3"
	// InternalStructuredTextParser.g:10935:1:
	// rule__Variable_Adapter__VarAssignment_3 : ( ( ruleVariable_Name ) ) ;
	public final void rule__Variable_Adapter__VarAssignment_3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10939:1: ( ( ( ruleVariable_Name ) ) )
			// InternalStructuredTextParser.g:10940:2: ( ( ruleVariable_Name ) )
			{
				// InternalStructuredTextParser.g:10940:2: ( ( ruleVariable_Name ) )
				// InternalStructuredTextParser.g:10941:3: ( ruleVariable_Name )
				{
					before(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationCrossReference_3_0());
					// InternalStructuredTextParser.g:10942:3: ( ruleVariable_Name )
					// InternalStructuredTextParser.g:10943:4: ruleVariable_Name
					{
						before(grammarAccess.getVariable_AdapterAccess()
								.getVarVarDeclarationVariable_NameParserRuleCall_3_0_1());
						pushFollow(FOLLOW_2);
						ruleVariable_Name();

						state._fsp--;

						after(grammarAccess.getVariable_AdapterAccess()
								.getVarVarDeclarationVariable_NameParserRuleCall_3_0_1());

					}

					after(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationCrossReference_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Adapter__VarAssignment_3"

	// $ANTLR start "rule__Multibit_Part_Access__DwordaccessAssignment_0_0"
	// InternalStructuredTextParser.g:10954:1:
	// rule__Multibit_Part_Access__DwordaccessAssignment_0_0 : ( ( D ) ) ;
	public final void rule__Multibit_Part_Access__DwordaccessAssignment_0_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10958:1: ( ( ( D ) ) )
			// InternalStructuredTextParser.g:10959:2: ( ( D ) )
			{
				// InternalStructuredTextParser.g:10959:2: ( ( D ) )
				// InternalStructuredTextParser.g:10960:3: ( D )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0());
					// InternalStructuredTextParser.g:10961:3: ( D )
					// InternalStructuredTextParser.g:10962:4: D
					{
						before(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0());
						match(input, D, FOLLOW_2);
						after(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0());

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__DwordaccessAssignment_0_0"

	// $ANTLR start "rule__Multibit_Part_Access__IndexAssignment_0_1"
	// InternalStructuredTextParser.g:10973:1:
	// rule__Multibit_Part_Access__IndexAssignment_0_1 : ( rulePartial_Value ) ;
	public final void rule__Multibit_Part_Access__IndexAssignment_0_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10977:1: ( ( rulePartial_Value ) )
			// InternalStructuredTextParser.g:10978:2: ( rulePartial_Value )
			{
				// InternalStructuredTextParser.g:10978:2: ( rulePartial_Value )
				// InternalStructuredTextParser.g:10979:3: rulePartial_Value
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_0_1_0());
					pushFollow(FOLLOW_2);
					rulePartial_Value();

					state._fsp--;

					after(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_0_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__IndexAssignment_0_1"

	// $ANTLR start "rule__Multibit_Part_Access__WordaccessAssignment_1_0"
	// InternalStructuredTextParser.g:10988:1:
	// rule__Multibit_Part_Access__WordaccessAssignment_1_0 : ( ( W ) ) ;
	public final void rule__Multibit_Part_Access__WordaccessAssignment_1_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:10992:1: ( ( ( W ) ) )
			// InternalStructuredTextParser.g:10993:2: ( ( W ) )
			{
				// InternalStructuredTextParser.g:10993:2: ( ( W ) )
				// InternalStructuredTextParser.g:10994:3: ( W )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0());
					// InternalStructuredTextParser.g:10995:3: ( W )
					// InternalStructuredTextParser.g:10996:4: W
					{
						before(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0());
						match(input, W, FOLLOW_2);
						after(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0());

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__WordaccessAssignment_1_0"

	// $ANTLR start "rule__Multibit_Part_Access__IndexAssignment_1_1"
	// InternalStructuredTextParser.g:11007:1:
	// rule__Multibit_Part_Access__IndexAssignment_1_1 : ( rulePartial_Value ) ;
	public final void rule__Multibit_Part_Access__IndexAssignment_1_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11011:1: ( ( rulePartial_Value ) )
			// InternalStructuredTextParser.g:11012:2: ( rulePartial_Value )
			{
				// InternalStructuredTextParser.g:11012:2: ( rulePartial_Value )
				// InternalStructuredTextParser.g:11013:3: rulePartial_Value
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_1_1_0());
					pushFollow(FOLLOW_2);
					rulePartial_Value();

					state._fsp--;

					after(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_1_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__IndexAssignment_1_1"

	// $ANTLR start "rule__Multibit_Part_Access__ByteaccessAssignment_2_0"
	// InternalStructuredTextParser.g:11022:1:
	// rule__Multibit_Part_Access__ByteaccessAssignment_2_0 : ( ( B ) ) ;
	public final void rule__Multibit_Part_Access__ByteaccessAssignment_2_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11026:1: ( ( ( B ) ) )
			// InternalStructuredTextParser.g:11027:2: ( ( B ) )
			{
				// InternalStructuredTextParser.g:11027:2: ( ( B ) )
				// InternalStructuredTextParser.g:11028:3: ( B )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0());
					// InternalStructuredTextParser.g:11029:3: ( B )
					// InternalStructuredTextParser.g:11030:4: B
					{
						before(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0());
						match(input, B, FOLLOW_2);
						after(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0());

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__ByteaccessAssignment_2_0"

	// $ANTLR start "rule__Multibit_Part_Access__IndexAssignment_2_1"
	// InternalStructuredTextParser.g:11041:1:
	// rule__Multibit_Part_Access__IndexAssignment_2_1 : ( rulePartial_Value ) ;
	public final void rule__Multibit_Part_Access__IndexAssignment_2_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11045:1: ( ( rulePartial_Value ) )
			// InternalStructuredTextParser.g:11046:2: ( rulePartial_Value )
			{
				// InternalStructuredTextParser.g:11046:2: ( rulePartial_Value )
				// InternalStructuredTextParser.g:11047:3: rulePartial_Value
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_2_1_0());
					pushFollow(FOLLOW_2);
					rulePartial_Value();

					state._fsp--;

					after(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_2_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__IndexAssignment_2_1"

	// $ANTLR start "rule__Multibit_Part_Access__BitaccessAssignment_3_0"
	// InternalStructuredTextParser.g:11056:1:
	// rule__Multibit_Part_Access__BitaccessAssignment_3_0 : ( ( X ) ) ;
	public final void rule__Multibit_Part_Access__BitaccessAssignment_3_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11060:1: ( ( ( X ) ) )
			// InternalStructuredTextParser.g:11061:2: ( ( X ) )
			{
				// InternalStructuredTextParser.g:11061:2: ( ( X ) )
				// InternalStructuredTextParser.g:11062:3: ( X )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0());
					// InternalStructuredTextParser.g:11063:3: ( X )
					// InternalStructuredTextParser.g:11064:4: X
					{
						before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0());
						match(input, X, FOLLOW_2);
						after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0());

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__BitaccessAssignment_3_0"

	// $ANTLR start "rule__Multibit_Part_Access__IndexAssignment_3_1"
	// InternalStructuredTextParser.g:11075:1:
	// rule__Multibit_Part_Access__IndexAssignment_3_1 : ( rulePartial_Value ) ;
	public final void rule__Multibit_Part_Access__IndexAssignment_3_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11079:1: ( ( rulePartial_Value ) )
			// InternalStructuredTextParser.g:11080:2: ( rulePartial_Value )
			{
				// InternalStructuredTextParser.g:11080:2: ( rulePartial_Value )
				// InternalStructuredTextParser.g:11081:3: rulePartial_Value
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_3_1_0());
					pushFollow(FOLLOW_2);
					rulePartial_Value();

					state._fsp--;

					after(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_3_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__IndexAssignment_3_1"

	// $ANTLR start "rule__Multibit_Part_Access__BitaccessAssignment_4_0"
	// InternalStructuredTextParser.g:11090:1:
	// rule__Multibit_Part_Access__BitaccessAssignment_4_0 : ( ( FullStop ) ) ;
	public final void rule__Multibit_Part_Access__BitaccessAssignment_4_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11094:1: ( ( ( FullStop ) ) )
			// InternalStructuredTextParser.g:11095:2: ( ( FullStop ) )
			{
				// InternalStructuredTextParser.g:11095:2: ( ( FullStop ) )
				// InternalStructuredTextParser.g:11096:3: ( FullStop )
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0());
					// InternalStructuredTextParser.g:11097:3: ( FullStop )
					// InternalStructuredTextParser.g:11098:4: FullStop
					{
						before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0());
						match(input, FullStop, FOLLOW_2);
						after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0());

					}

					after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__BitaccessAssignment_4_0"

	// $ANTLR start "rule__Multibit_Part_Access__IndexAssignment_4_1"
	// InternalStructuredTextParser.g:11109:1:
	// rule__Multibit_Part_Access__IndexAssignment_4_1 : ( rulePartial_Value ) ;
	public final void rule__Multibit_Part_Access__IndexAssignment_4_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11113:1: ( ( rulePartial_Value ) )
			// InternalStructuredTextParser.g:11114:2: ( rulePartial_Value )
			{
				// InternalStructuredTextParser.g:11114:2: ( rulePartial_Value )
				// InternalStructuredTextParser.g:11115:3: rulePartial_Value
				{
					before(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_4_1_0());
					pushFollow(FOLLOW_2);
					rulePartial_Value();

					state._fsp--;

					after(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_4_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Multibit_Part_Access__IndexAssignment_4_1"

	// $ANTLR start "rule__Variable_Primary__VarAssignment"
	// InternalStructuredTextParser.g:11124:1: rule__Variable_Primary__VarAssignment
	// : ( ( ruleVariable_Name ) ) ;
	public final void rule__Variable_Primary__VarAssignment() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11128:1: ( ( ( ruleVariable_Name ) ) )
			// InternalStructuredTextParser.g:11129:2: ( ( ruleVariable_Name ) )
			{
				// InternalStructuredTextParser.g:11129:2: ( ( ruleVariable_Name ) )
				// InternalStructuredTextParser.g:11130:3: ( ruleVariable_Name )
				{
					before(grammarAccess.getVariable_PrimaryAccess().getVarVarDeclarationCrossReference_0());
					// InternalStructuredTextParser.g:11131:3: ( ruleVariable_Name )
					// InternalStructuredTextParser.g:11132:4: ruleVariable_Name
					{
						before(grammarAccess.getVariable_PrimaryAccess()
								.getVarVarDeclarationVariable_NameParserRuleCall_0_1());
						pushFollow(FOLLOW_2);
						ruleVariable_Name();

						state._fsp--;

						after(grammarAccess.getVariable_PrimaryAccess()
								.getVarVarDeclarationVariable_NameParserRuleCall_0_1());

					}

					after(grammarAccess.getVariable_PrimaryAccess().getVarVarDeclarationCrossReference_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Variable_Primary__VarAssignment"

	// $ANTLR start "rule__Int_Literal__TypeAssignment_0_0"
	// InternalStructuredTextParser.g:11143:1: rule__Int_Literal__TypeAssignment_0_0
	// : ( ruleInt_Type_Name ) ;
	public final void rule__Int_Literal__TypeAssignment_0_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11147:1: ( ( ruleInt_Type_Name ) )
			// InternalStructuredTextParser.g:11148:2: ( ruleInt_Type_Name )
			{
				// InternalStructuredTextParser.g:11148:2: ( ruleInt_Type_Name )
				// InternalStructuredTextParser.g:11149:3: ruleInt_Type_Name
				{
					before(grammarAccess.getInt_LiteralAccess().getTypeInt_Type_NameEnumRuleCall_0_0_0());
					pushFollow(FOLLOW_2);
					ruleInt_Type_Name();

					state._fsp--;

					after(grammarAccess.getInt_LiteralAccess().getTypeInt_Type_NameEnumRuleCall_0_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__TypeAssignment_0_0"

	// $ANTLR start "rule__Int_Literal__ValueAssignment_1"
	// InternalStructuredTextParser.g:11158:1: rule__Int_Literal__ValueAssignment_1
	// : ( ( rule__Int_Literal__ValueAlternatives_1_0 ) ) ;
	public final void rule__Int_Literal__ValueAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11162:1: ( ( (
			// rule__Int_Literal__ValueAlternatives_1_0 ) ) )
			// InternalStructuredTextParser.g:11163:2: ( (
			// rule__Int_Literal__ValueAlternatives_1_0 ) )
			{
				// InternalStructuredTextParser.g:11163:2: ( (
				// rule__Int_Literal__ValueAlternatives_1_0 ) )
				// InternalStructuredTextParser.g:11164:3: (
				// rule__Int_Literal__ValueAlternatives_1_0 )
				{
					before(grammarAccess.getInt_LiteralAccess().getValueAlternatives_1_0());
					// InternalStructuredTextParser.g:11165:3: (
					// rule__Int_Literal__ValueAlternatives_1_0 )
					// InternalStructuredTextParser.g:11165:4:
					// rule__Int_Literal__ValueAlternatives_1_0
					{
						pushFollow(FOLLOW_2);
						rule__Int_Literal__ValueAlternatives_1_0();

						state._fsp--;

					}

					after(grammarAccess.getInt_LiteralAccess().getValueAlternatives_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Int_Literal__ValueAssignment_1"

	// $ANTLR start "rule__Real_Literal__TypeAssignment_0_0"
	// InternalStructuredTextParser.g:11173:1:
	// rule__Real_Literal__TypeAssignment_0_0 : ( ruleReal_Type_Name ) ;
	public final void rule__Real_Literal__TypeAssignment_0_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11177:1: ( ( ruleReal_Type_Name ) )
			// InternalStructuredTextParser.g:11178:2: ( ruleReal_Type_Name )
			{
				// InternalStructuredTextParser.g:11178:2: ( ruleReal_Type_Name )
				// InternalStructuredTextParser.g:11179:3: ruleReal_Type_Name
				{
					before(grammarAccess.getReal_LiteralAccess().getTypeReal_Type_NameEnumRuleCall_0_0_0());
					pushFollow(FOLLOW_2);
					ruleReal_Type_Name();

					state._fsp--;

					after(grammarAccess.getReal_LiteralAccess().getTypeReal_Type_NameEnumRuleCall_0_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Literal__TypeAssignment_0_0"

	// $ANTLR start "rule__Real_Literal__ValueAssignment_1"
	// InternalStructuredTextParser.g:11188:1: rule__Real_Literal__ValueAssignment_1
	// : ( ruleReal_Value ) ;
	public final void rule__Real_Literal__ValueAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11192:1: ( ( ruleReal_Value ) )
			// InternalStructuredTextParser.g:11193:2: ( ruleReal_Value )
			{
				// InternalStructuredTextParser.g:11193:2: ( ruleReal_Value )
				// InternalStructuredTextParser.g:11194:3: ruleReal_Value
				{
					before(grammarAccess.getReal_LiteralAccess().getValueReal_ValueParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleReal_Value();

					state._fsp--;

					after(grammarAccess.getReal_LiteralAccess().getValueReal_ValueParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Real_Literal__ValueAssignment_1"

	// $ANTLR start "rule__Bool_Literal__TypeAssignment_0_0"
	// InternalStructuredTextParser.g:11203:1:
	// rule__Bool_Literal__TypeAssignment_0_0 : ( ruleBool_Type_Name ) ;
	public final void rule__Bool_Literal__TypeAssignment_0_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11207:1: ( ( ruleBool_Type_Name ) )
			// InternalStructuredTextParser.g:11208:2: ( ruleBool_Type_Name )
			{
				// InternalStructuredTextParser.g:11208:2: ( ruleBool_Type_Name )
				// InternalStructuredTextParser.g:11209:3: ruleBool_Type_Name
				{
					before(grammarAccess.getBool_LiteralAccess().getTypeBool_Type_NameEnumRuleCall_0_0_0());
					pushFollow(FOLLOW_2);
					ruleBool_Type_Name();

					state._fsp--;

					after(grammarAccess.getBool_LiteralAccess().getTypeBool_Type_NameEnumRuleCall_0_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Literal__TypeAssignment_0_0"

	// $ANTLR start "rule__Bool_Literal__ValueAssignment_1"
	// InternalStructuredTextParser.g:11218:1: rule__Bool_Literal__ValueAssignment_1
	// : ( ruleBool_Value ) ;
	public final void rule__Bool_Literal__ValueAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11222:1: ( ( ruleBool_Value ) )
			// InternalStructuredTextParser.g:11223:2: ( ruleBool_Value )
			{
				// InternalStructuredTextParser.g:11223:2: ( ruleBool_Value )
				// InternalStructuredTextParser.g:11224:3: ruleBool_Value
				{
					before(grammarAccess.getBool_LiteralAccess().getValueBool_ValueParserRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleBool_Value();

					state._fsp--;

					after(grammarAccess.getBool_LiteralAccess().getValueBool_ValueParserRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Bool_Literal__ValueAssignment_1"

	// $ANTLR start "rule__Char_Literal__TypeAssignment_0_0"
	// InternalStructuredTextParser.g:11233:1:
	// rule__Char_Literal__TypeAssignment_0_0 : ( ruleString_Type_Name ) ;
	public final void rule__Char_Literal__TypeAssignment_0_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11237:1: ( ( ruleString_Type_Name ) )
			// InternalStructuredTextParser.g:11238:2: ( ruleString_Type_Name )
			{
				// InternalStructuredTextParser.g:11238:2: ( ruleString_Type_Name )
				// InternalStructuredTextParser.g:11239:3: ruleString_Type_Name
				{
					before(grammarAccess.getChar_LiteralAccess().getTypeString_Type_NameEnumRuleCall_0_0_0());
					pushFollow(FOLLOW_2);
					ruleString_Type_Name();

					state._fsp--;

					after(grammarAccess.getChar_LiteralAccess().getTypeString_Type_NameEnumRuleCall_0_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__TypeAssignment_0_0"

	// $ANTLR start "rule__Char_Literal__LengthAssignment_0_1"
	// InternalStructuredTextParser.g:11248:1:
	// rule__Char_Literal__LengthAssignment_0_1 : ( RULE_UNSIGNED_INT ) ;
	public final void rule__Char_Literal__LengthAssignment_0_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11252:1: ( ( RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:11253:2: ( RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:11253:2: ( RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:11254:3: RULE_UNSIGNED_INT
				{
					before(grammarAccess.getChar_LiteralAccess().getLengthUNSIGNED_INTTerminalRuleCall_0_1_0());
					match(input, RULE_UNSIGNED_INT, FOLLOW_2);
					after(grammarAccess.getChar_LiteralAccess().getLengthUNSIGNED_INTTerminalRuleCall_0_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__LengthAssignment_0_1"

	// $ANTLR start "rule__Char_Literal__ValueAssignment_1"
	// InternalStructuredTextParser.g:11263:1: rule__Char_Literal__ValueAssignment_1
	// : ( ( rule__Char_Literal__ValueAlternatives_1_0 ) ) ;
	public final void rule__Char_Literal__ValueAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11267:1: ( ( (
			// rule__Char_Literal__ValueAlternatives_1_0 ) ) )
			// InternalStructuredTextParser.g:11268:2: ( (
			// rule__Char_Literal__ValueAlternatives_1_0 ) )
			{
				// InternalStructuredTextParser.g:11268:2: ( (
				// rule__Char_Literal__ValueAlternatives_1_0 ) )
				// InternalStructuredTextParser.g:11269:3: (
				// rule__Char_Literal__ValueAlternatives_1_0 )
				{
					before(grammarAccess.getChar_LiteralAccess().getValueAlternatives_1_0());
					// InternalStructuredTextParser.g:11270:3: (
					// rule__Char_Literal__ValueAlternatives_1_0 )
					// InternalStructuredTextParser.g:11270:4:
					// rule__Char_Literal__ValueAlternatives_1_0
					{
						pushFollow(FOLLOW_2);
						rule__Char_Literal__ValueAlternatives_1_0();

						state._fsp--;

					}

					after(grammarAccess.getChar_LiteralAccess().getValueAlternatives_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Char_Literal__ValueAssignment_1"

	// $ANTLR start "rule__Duration__TypeAssignment_0"
	// InternalStructuredTextParser.g:11278:1: rule__Duration__TypeAssignment_0 : (
	// ruleTime_Type_Name ) ;
	public final void rule__Duration__TypeAssignment_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11282:1: ( ( ruleTime_Type_Name ) )
			// InternalStructuredTextParser.g:11283:2: ( ruleTime_Type_Name )
			{
				// InternalStructuredTextParser.g:11283:2: ( ruleTime_Type_Name )
				// InternalStructuredTextParser.g:11284:3: ruleTime_Type_Name
				{
					before(grammarAccess.getDurationAccess().getTypeTime_Type_NameEnumRuleCall_0_0());
					pushFollow(FOLLOW_2);
					ruleTime_Type_Name();

					state._fsp--;

					after(grammarAccess.getDurationAccess().getTypeTime_Type_NameEnumRuleCall_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__TypeAssignment_0"

	// $ANTLR start "rule__Duration__NegativeAssignment_2_1"
	// InternalStructuredTextParser.g:11293:1:
	// rule__Duration__NegativeAssignment_2_1 : ( ( HyphenMinus ) ) ;
	public final void rule__Duration__NegativeAssignment_2_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11297:1: ( ( ( HyphenMinus ) ) )
			// InternalStructuredTextParser.g:11298:2: ( ( HyphenMinus ) )
			{
				// InternalStructuredTextParser.g:11298:2: ( ( HyphenMinus ) )
				// InternalStructuredTextParser.g:11299:3: ( HyphenMinus )
				{
					before(grammarAccess.getDurationAccess().getNegativeHyphenMinusKeyword_2_1_0());
					// InternalStructuredTextParser.g:11300:3: ( HyphenMinus )
					// InternalStructuredTextParser.g:11301:4: HyphenMinus
					{
						before(grammarAccess.getDurationAccess().getNegativeHyphenMinusKeyword_2_1_0());
						match(input, HyphenMinus, FOLLOW_2);
						after(grammarAccess.getDurationAccess().getNegativeHyphenMinusKeyword_2_1_0());

					}

					after(grammarAccess.getDurationAccess().getNegativeHyphenMinusKeyword_2_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__NegativeAssignment_2_1"

	// $ANTLR start "rule__Duration__ValueAssignment_3"
	// InternalStructuredTextParser.g:11312:1: rule__Duration__ValueAssignment_3 : (
	// ruleDuration_Value ) ;
	public final void rule__Duration__ValueAssignment_3() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11316:1: ( ( ruleDuration_Value ) )
			// InternalStructuredTextParser.g:11317:2: ( ruleDuration_Value )
			{
				// InternalStructuredTextParser.g:11317:2: ( ruleDuration_Value )
				// InternalStructuredTextParser.g:11318:3: ruleDuration_Value
				{
					before(grammarAccess.getDurationAccess().getValueDuration_ValueParserRuleCall_3_0());
					pushFollow(FOLLOW_2);
					ruleDuration_Value();

					state._fsp--;

					after(grammarAccess.getDurationAccess().getValueDuration_ValueParserRuleCall_3_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__ValueAssignment_3"

	// $ANTLR start "rule__Duration__ValueAssignment_4_1"
	// InternalStructuredTextParser.g:11327:1: rule__Duration__ValueAssignment_4_1 :
	// ( ruleDuration_Value ) ;
	public final void rule__Duration__ValueAssignment_4_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11331:1: ( ( ruleDuration_Value ) )
			// InternalStructuredTextParser.g:11332:2: ( ruleDuration_Value )
			{
				// InternalStructuredTextParser.g:11332:2: ( ruleDuration_Value )
				// InternalStructuredTextParser.g:11333:3: ruleDuration_Value
				{
					before(grammarAccess.getDurationAccess().getValueDuration_ValueParserRuleCall_4_1_0());
					pushFollow(FOLLOW_2);
					ruleDuration_Value();

					state._fsp--;

					after(grammarAccess.getDurationAccess().getValueDuration_ValueParserRuleCall_4_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration__ValueAssignment_4_1"

	// $ANTLR start "rule__Duration_Value__ValueAssignment_0"
	// InternalStructuredTextParser.g:11342:1:
	// rule__Duration_Value__ValueAssignment_0 : ( ruleFix_Point ) ;
	public final void rule__Duration_Value__ValueAssignment_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11346:1: ( ( ruleFix_Point ) )
			// InternalStructuredTextParser.g:11347:2: ( ruleFix_Point )
			{
				// InternalStructuredTextParser.g:11347:2: ( ruleFix_Point )
				// InternalStructuredTextParser.g:11348:3: ruleFix_Point
				{
					before(grammarAccess.getDuration_ValueAccess().getValueFix_PointParserRuleCall_0_0());
					pushFollow(FOLLOW_2);
					ruleFix_Point();

					state._fsp--;

					after(grammarAccess.getDuration_ValueAccess().getValueFix_PointParserRuleCall_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration_Value__ValueAssignment_0"

	// $ANTLR start "rule__Duration_Value__UnitAssignment_1"
	// InternalStructuredTextParser.g:11357:1:
	// rule__Duration_Value__UnitAssignment_1 : ( ruleDuration_Unit ) ;
	public final void rule__Duration_Value__UnitAssignment_1() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11361:1: ( ( ruleDuration_Unit ) )
			// InternalStructuredTextParser.g:11362:2: ( ruleDuration_Unit )
			{
				// InternalStructuredTextParser.g:11362:2: ( ruleDuration_Unit )
				// InternalStructuredTextParser.g:11363:3: ruleDuration_Unit
				{
					before(grammarAccess.getDuration_ValueAccess().getUnitDuration_UnitEnumRuleCall_1_0());
					pushFollow(FOLLOW_2);
					ruleDuration_Unit();

					state._fsp--;

					after(grammarAccess.getDuration_ValueAccess().getUnitDuration_UnitEnumRuleCall_1_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Duration_Value__UnitAssignment_1"

	// $ANTLR start "rule__Time_Of_Day__TypeAssignment_0"
	// InternalStructuredTextParser.g:11372:1: rule__Time_Of_Day__TypeAssignment_0 :
	// ( ruleTod_Type_Name ) ;
	public final void rule__Time_Of_Day__TypeAssignment_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11376:1: ( ( ruleTod_Type_Name ) )
			// InternalStructuredTextParser.g:11377:2: ( ruleTod_Type_Name )
			{
				// InternalStructuredTextParser.g:11377:2: ( ruleTod_Type_Name )
				// InternalStructuredTextParser.g:11378:3: ruleTod_Type_Name
				{
					before(grammarAccess.getTime_Of_DayAccess().getTypeTod_Type_NameEnumRuleCall_0_0());
					pushFollow(FOLLOW_2);
					ruleTod_Type_Name();

					state._fsp--;

					after(grammarAccess.getTime_Of_DayAccess().getTypeTod_Type_NameEnumRuleCall_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Time_Of_Day__TypeAssignment_0"

	// $ANTLR start "rule__Time_Of_Day__ValueAssignment_2"
	// InternalStructuredTextParser.g:11387:1: rule__Time_Of_Day__ValueAssignment_2
	// : ( ruleDaytime ) ;
	public final void rule__Time_Of_Day__ValueAssignment_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11391:1: ( ( ruleDaytime ) )
			// InternalStructuredTextParser.g:11392:2: ( ruleDaytime )
			{
				// InternalStructuredTextParser.g:11392:2: ( ruleDaytime )
				// InternalStructuredTextParser.g:11393:3: ruleDaytime
				{
					before(grammarAccess.getTime_Of_DayAccess().getValueDaytimeParserRuleCall_2_0());
					pushFollow(FOLLOW_2);
					ruleDaytime();

					state._fsp--;

					after(grammarAccess.getTime_Of_DayAccess().getValueDaytimeParserRuleCall_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Time_Of_Day__ValueAssignment_2"

	// $ANTLR start "rule__Date__TypeAssignment_0"
	// InternalStructuredTextParser.g:11402:1: rule__Date__TypeAssignment_0 : (
	// ruleDate_Type_Name ) ;
	public final void rule__Date__TypeAssignment_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11406:1: ( ( ruleDate_Type_Name ) )
			// InternalStructuredTextParser.g:11407:2: ( ruleDate_Type_Name )
			{
				// InternalStructuredTextParser.g:11407:2: ( ruleDate_Type_Name )
				// InternalStructuredTextParser.g:11408:3: ruleDate_Type_Name
				{
					before(grammarAccess.getDateAccess().getTypeDate_Type_NameEnumRuleCall_0_0());
					pushFollow(FOLLOW_2);
					ruleDate_Type_Name();

					state._fsp--;

					after(grammarAccess.getDateAccess().getTypeDate_Type_NameEnumRuleCall_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date__TypeAssignment_0"

	// $ANTLR start "rule__Date__ValueAssignment_2"
	// InternalStructuredTextParser.g:11417:1: rule__Date__ValueAssignment_2 : (
	// ruleDate_Literal ) ;
	public final void rule__Date__ValueAssignment_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11421:1: ( ( ruleDate_Literal ) )
			// InternalStructuredTextParser.g:11422:2: ( ruleDate_Literal )
			{
				// InternalStructuredTextParser.g:11422:2: ( ruleDate_Literal )
				// InternalStructuredTextParser.g:11423:3: ruleDate_Literal
				{
					before(grammarAccess.getDateAccess().getValueDate_LiteralParserRuleCall_2_0());
					pushFollow(FOLLOW_2);
					ruleDate_Literal();

					state._fsp--;

					after(grammarAccess.getDateAccess().getValueDate_LiteralParserRuleCall_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date__ValueAssignment_2"

	// $ANTLR start "rule__Date_And_Time__TypeAssignment_0"
	// InternalStructuredTextParser.g:11432:1: rule__Date_And_Time__TypeAssignment_0
	// : ( ruleDT_Type_Name ) ;
	public final void rule__Date_And_Time__TypeAssignment_0() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11436:1: ( ( ruleDT_Type_Name ) )
			// InternalStructuredTextParser.g:11437:2: ( ruleDT_Type_Name )
			{
				// InternalStructuredTextParser.g:11437:2: ( ruleDT_Type_Name )
				// InternalStructuredTextParser.g:11438:3: ruleDT_Type_Name
				{
					before(grammarAccess.getDate_And_TimeAccess().getTypeDT_Type_NameEnumRuleCall_0_0());
					pushFollow(FOLLOW_2);
					ruleDT_Type_Name();

					state._fsp--;

					after(grammarAccess.getDate_And_TimeAccess().getTypeDT_Type_NameEnumRuleCall_0_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time__TypeAssignment_0"

	// $ANTLR start "rule__Date_And_Time__ValueAssignment_2"
	// InternalStructuredTextParser.g:11447:1:
	// rule__Date_And_Time__ValueAssignment_2 : ( ruleDate_And_Time_Value ) ;
	public final void rule__Date_And_Time__ValueAssignment_2() throws RecognitionException {

		int stackSize = keepStackSize();

		try {
			// InternalStructuredTextParser.g:11451:1: ( ( ruleDate_And_Time_Value ) )
			// InternalStructuredTextParser.g:11452:2: ( ruleDate_And_Time_Value )
			{
				// InternalStructuredTextParser.g:11452:2: ( ruleDate_And_Time_Value )
				// InternalStructuredTextParser.g:11453:3: ruleDate_And_Time_Value
				{
					before(grammarAccess.getDate_And_TimeAccess().getValueDate_And_Time_ValueParserRuleCall_2_0());
					pushFollow(FOLLOW_2);
					ruleDate_And_Time_Value();

					state._fsp--;

					after(grammarAccess.getDate_And_TimeAccess().getValueDate_And_Time_ValueParserRuleCall_2_0());

				}

			}

		} catch (RecognitionException re) {
			reportError(re);
			recover(input, re);
		} finally {

			restoreStackSize(stackSize);

		}
		return;
	}
	// $ANTLR end "rule__Date_And_Time__ValueAssignment_2"

	// Delegated rules

	protected DFA6 dfa6 = new DFA6(this);
	static final String dfa_1s = "\12\uffff";
	static final String dfa_2s = "\5\uffff\3\4\2\uffff";
	static final String dfa_3s = "\1\4\2\57\2\uffff\3\10\1\123\1\uffff";
	static final String dfa_4s = "\1\170\2\164\2\uffff\3\145\1\125\1\uffff";
	static final String dfa_5s = "\3\uffff\1\1\1\2\4\uffff\1\3";
	static final String dfa_6s = "\12\uffff}>";
	static final String[] dfa_7s = {
			"\4\11\7\uffff\1\11\3\uffff\1\11\2\uffff\4\11\2\uffff\2\11\1\uffff\2\11\1\uffff\1\11\2\uffff\3\11\2\uffff\4\11\1\uffff\1\10\2\11\7\uffff\2\11\1\uffff\1\3\1\11\13\uffff\1\7\1\uffff\1\11\1\6\10\uffff\1\4\2\uffff\1\2\1\uffff\1\1\10\uffff\1\5\3\uffff\1\11\10\uffff\1\4\4\11\1\uffff\1\11\1\uffff\1\11",
			"\1\3\31\uffff\1\3\2\uffff\1\3\10\uffff\1\3\15\uffff\1\3\14\uffff\1\3\3\uffff\1\11",
			"\1\3\31\uffff\1\3\2\uffff\1\3\10\uffff\1\3\15\uffff\1\3\14\uffff\1\3\3\uffff\1\11", "", "",
			"\1\4\45\uffff\1\4\4\uffff\5\4\3\uffff\1\4\3\uffff\2\4\1\uffff\2\4\1\uffff\1\4\1\uffff\2\4\4\uffff\3\4\3\uffff\1\11\1\4\1\uffff\7\4\1\uffff\4\4\2\uffff\2\4",
			"\1\4\45\uffff\1\4\4\uffff\5\4\3\uffff\1\4\3\uffff\2\4\1\uffff\2\4\1\uffff\1\4\1\uffff\2\4\4\uffff\3\4\3\uffff\1\11\1\4\1\uffff\7\4\1\uffff\4\4\2\uffff\2\4",
			"\1\4\45\uffff\1\4\4\uffff\5\4\3\uffff\1\4\3\uffff\2\4\1\uffff\2\4\1\uffff\1\4\1\uffff\2\4\4\uffff\3\4\3\uffff\1\11\1\4\1\uffff\7\4\1\uffff\4\4\2\uffff\2\4",
			"\1\11\1\uffff\1\4", "" };

	static final short[] dfa_1 = DFA.unpackEncodedString(dfa_1s);
	static final short[] dfa_2 = DFA.unpackEncodedString(dfa_2s);
	static final char[] dfa_3 = DFA.unpackEncodedStringToUnsignedChars(dfa_3s);
	static final char[] dfa_4 = DFA.unpackEncodedStringToUnsignedChars(dfa_4s);
	static final short[] dfa_5 = DFA.unpackEncodedString(dfa_5s);
	static final short[] dfa_6 = DFA.unpackEncodedString(dfa_6s);
	static final short[][] dfa_7 = unpackEncodedStringArray(dfa_7s);

	class DFA6 extends DFA {

		public DFA6(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 6;
			this.eot = dfa_1;
			this.eof = dfa_2;
			this.min = dfa_3;
			this.max = dfa_4;
			this.accept = dfa_5;
			this.special = dfa_6;
			this.transition = dfa_7;
		}

		public String getDescription() {
			return "2287:1: rule__Unary_Expr__Alternatives : ( ( ( rule__Unary_Expr__Group_0__0 ) ) | ( rulePrimary_Expr ) | ( ruleConstant ) );";
		}
	}

	public static final BitSet FOLLOW_1 = new BitSet(new long[] { 0x0000000000000000L });
	public static final BitSet FOLLOW_2 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_3 = new BitSet(new long[] { 0x4100821208060800L, 0x0001000840001600L });
	public static final BitSet FOLLOW_4 = new BitSet(new long[] { 0x0000000000004400L, 0x0001000000000000L });
	public static final BitSet FOLLOW_5 = new BitSet(new long[] { 0x0000000000000402L, 0x0001000000000000L });
	public static final BitSet FOLLOW_6 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000040000000L });
	public static final BitSet FOLLOW_7 = new BitSet(new long[] { 0x0000000000000400L, 0x0001000000000000L });
	public static final BitSet FOLLOW_8 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000020000000L });
	public static final BitSet FOLLOW_9 = new BitSet(new long[] { 0x2206BCEDB79880F0L, 0x0001000000000000L });
	public static final BitSet FOLLOW_10 = new BitSet(new long[] { 0x0000000000000000L, 0x0000001000000002L });
	public static final BitSet FOLLOW_11 = new BitSet(new long[] { 0x0000000000000000L, 0x0010000000000000L });
	public static final BitSet FOLLOW_12 = new BitSet(new long[] { 0x0000000000000000L, 0x0000002000000000L });
	public static final BitSet FOLLOW_13 = new BitSet(new long[] { 0x2603BCE5B3C880F0L, 0x015E008805001A00L });
	public static final BitSet FOLLOW_14 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000040L });
	public static final BitSet FOLLOW_15 = new BitSet(new long[] { 0x0000000000000000L, 0x0000001000000000L });
	public static final BitSet FOLLOW_16 = new BitSet(new long[] { 0x0000000000000000L, 0x0001000800001200L });
	public static final BitSet FOLLOW_17 = new BitSet(new long[] { 0x0100821208060802L, 0x0001000840001600L });
	public static final BitSet FOLLOW_18 = new BitSet(new long[] { 0x0100821208060800L, 0x0001000840001600L });
	public static final BitSet FOLLOW_19 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000002L });
	public static final BitSet FOLLOW_20 = new BitSet(new long[] { 0x3603BCE5B3C880F0L, 0x015F008805201A00L });
	public static final BitSet FOLLOW_21 = new BitSet(new long[] { 0x0000000008000000L });
	public static final BitSet FOLLOW_22 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000200000L });
	public static final BitSet FOLLOW_23 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000400000L });
	public static final BitSet FOLLOW_24 = new BitSet(new long[] { 0x0000800008040000L, 0x0001000000000000L });
	public static final BitSet FOLLOW_25 = new BitSet(new long[] { 0x0000400000000000L });
	public static final BitSet FOLLOW_26 = new BitSet(new long[] { 0x0000010000210000L });
	public static final BitSet FOLLOW_27 = new BitSet(new long[] { 0x0000000000200002L });
	public static final BitSet FOLLOW_28 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000002000L });
	public static final BitSet FOLLOW_29 = new BitSet(new long[] { 0x0000010000001000L });
	public static final BitSet FOLLOW_30 = new BitSet(new long[] { 0x2603BCE5B3C880F2L, 0x015E008805001A00L });
	public static final BitSet FOLLOW_31 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000022000000L });
	public static final BitSet FOLLOW_32 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000002000000L });
	public static final BitSet FOLLOW_33 = new BitSet(new long[] { 0x0000020000000000L });
	public static final BitSet FOLLOW_34 = new BitSet(new long[] { 0x0100821208060800L, 0x0001000800001600L });
	public static final BitSet FOLLOW_35 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000008000L });
	public static final BitSet FOLLOW_36 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000180L });
	public static final BitSet FOLLOW_37 = new BitSet(new long[] { 0x0000000000002000L });
	public static final BitSet FOLLOW_38 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000100L });
	public static final BitSet FOLLOW_39 = new BitSet(new long[] { 0x0000000000000200L });
	public static final BitSet FOLLOW_40 = new BitSet(new long[] { 0x0000000040000000L });
	public static final BitSet FOLLOW_41 = new BitSet(new long[] { 0x0000000000000100L });
	public static final BitSet FOLLOW_42 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000004000L });
	public static final BitSet FOLLOW_43 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000000004000L });
	public static final BitSet FOLLOW_44 = new BitSet(new long[] { 0x8000000000000000L });
	public static final BitSet FOLLOW_45 = new BitSet(new long[] { 0x8000000000000002L });
	public static final BitSet FOLLOW_46 = new BitSet(new long[] { 0x0080000000000000L, 0x0000000000100000L });
	public static final BitSet FOLLOW_47 = new BitSet(new long[] { 0x0080000000000002L, 0x0000000000100000L });
	public static final BitSet FOLLOW_48 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000100000008L });
	public static final BitSet FOLLOW_49 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000100000008L });
	public static final BitSet FOLLOW_50 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000280000024L });
	public static final BitSet FOLLOW_51 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000280000024L });
	public static final BitSet FOLLOW_52 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000005000000L });
	public static final BitSet FOLLOW_53 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000005000000L });
	public static final BitSet FOLLOW_54 = new BitSet(new long[] { 0x0800000000000000L, 0x0000000010800000L });
	public static final BitSet FOLLOW_55 = new BitSet(new long[] { 0x0800000000000002L, 0x0000000010800000L });
	public static final BitSet FOLLOW_56 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000001L });
	public static final BitSet FOLLOW_57 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000000000001L });
	public static final BitSet FOLLOW_58 = new BitSet(new long[] { 0x1000000000000000L, 0x0000000005000000L });
	public static final BitSet FOLLOW_59 = new BitSet(new long[] { 0x0000800000000000L, 0x0001000800201200L });
	public static final BitSet FOLLOW_60 = new BitSet(new long[] { 0x3603BCE5B3C880F0L, 0x015F008805601A00L });
	public static final BitSet FOLLOW_61 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000002000000L });
	public static final BitSet FOLLOW_62 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000010L });
	public static final BitSet FOLLOW_63 = new BitSet(new long[] { 0x0078000000000000L, 0x0000000008000000L });
	public static final BitSet FOLLOW_64 = new BitSet(new long[] { 0x0000000000000000L, 0x0000002002000000L });
	public static final BitSet FOLLOW_65 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000008000000L });
	public static final BitSet FOLLOW_66 = new BitSet(new long[] { 0x02022480B0000000L, 0x001E000005000000L });
	public static final BitSet FOLLOW_67 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000080000L });
	public static final BitSet FOLLOW_68 = new BitSet(new long[] { 0x0000000000000000L, 0x0010000005000000L });
	public static final BitSet FOLLOW_69 = new BitSet(new long[] { 0x02023480B1000000L, 0x001E000005000000L });
	public static final BitSet FOLLOW_70 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000400000000L });
	public static final BitSet FOLLOW_71 = new BitSet(new long[] { 0x0000002100088000L, 0x0140000000000000L });
	public static final BitSet FOLLOW_72 = new BitSet(new long[] { 0x0000000000000000L, 0x0010000000080000L });
	public static final BitSet FOLLOW_73 = new BitSet(new long[] { 0x0000000000000000L, 0x0000004000000000L });
	public static final BitSet FOLLOW_74 = new BitSet(new long[] { 0x0000000000000002L, 0x0000004000000000L });
	public static final BitSet FOLLOW_75 = new BitSet(new long[] { 0x0000000000000000L, 0x0000078000070000L });
	public static final BitSet FOLLOW_76 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000004000000L });

}