package org.eclipse.fordiac.ide.model.structuredtext.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.eclipse.fordiac.ide.model.structuredtext.services.StructuredTextGrammarAccess;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalStructuredTextParser extends AbstractInternalAntlrParser {
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

	public InternalStructuredTextParser(TokenStream input, StructuredTextGrammarAccess grammarAccess) {
		this(input);
		this.grammarAccess = grammarAccess;
		registerRules(grammarAccess.getGrammar());
	}

	@Override
	protected String getFirstRuleName() {
		return "StructuredTextAlgorithm";
	}

	@Override
	protected StructuredTextGrammarAccess getGrammarAccess() {
		return grammarAccess;
	}

	// $ANTLR start "entryRuleStructuredTextAlgorithm"
	// InternalStructuredTextParser.g:58:1: entryRuleStructuredTextAlgorithm returns
	// [EObject current=null] : iv_ruleStructuredTextAlgorithm=
	// ruleStructuredTextAlgorithm EOF ;
	public final EObject entryRuleStructuredTextAlgorithm() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleStructuredTextAlgorithm = null;

		try {
			// InternalStructuredTextParser.g:58:64: (iv_ruleStructuredTextAlgorithm=
			// ruleStructuredTextAlgorithm EOF )
			// InternalStructuredTextParser.g:59:2: iv_ruleStructuredTextAlgorithm=
			// ruleStructuredTextAlgorithm EOF
			{
				newCompositeNode(grammarAccess.getStructuredTextAlgorithmRule());
				pushFollow(FOLLOW_1);
				iv_ruleStructuredTextAlgorithm = ruleStructuredTextAlgorithm();

				state._fsp--;

				current = iv_ruleStructuredTextAlgorithm;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleStructuredTextAlgorithm"

	// $ANTLR start "ruleStructuredTextAlgorithm"
	// InternalStructuredTextParser.g:65:1: ruleStructuredTextAlgorithm returns
	// [EObject current=null] : ( () (otherlv_1= VAR ( ( (lv_localVariables_2_0=
	// ruleVar_Decl_Init ) ) otherlv_3= Semicolon )* otherlv_4= END_VAR )? (
	// (lv_statements_5_0= ruleStmt_List ) ) ) ;
	public final EObject ruleStructuredTextAlgorithm() throws RecognitionException {
		EObject current = null;

		Token otherlv_1 = null;
		Token otherlv_3 = null;
		Token otherlv_4 = null;
		EObject lv_localVariables_2_0 = null;

		EObject lv_statements_5_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:71:2: ( ( () (otherlv_1= VAR ( (
			// (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )*
			// otherlv_4= END_VAR )? ( (lv_statements_5_0= ruleStmt_List ) ) ) )
			// InternalStructuredTextParser.g:72:2: ( () (otherlv_1= VAR ( (
			// (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )*
			// otherlv_4= END_VAR )? ( (lv_statements_5_0= ruleStmt_List ) ) )
			{
				// InternalStructuredTextParser.g:72:2: ( () (otherlv_1= VAR ( (
				// (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )*
				// otherlv_4= END_VAR )? ( (lv_statements_5_0= ruleStmt_List ) ) )
				// InternalStructuredTextParser.g:73:3: () (otherlv_1= VAR ( (
				// (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )*
				// otherlv_4= END_VAR )? ( (lv_statements_5_0= ruleStmt_List ) )
				{
					// InternalStructuredTextParser.g:73:3: ()
					// InternalStructuredTextParser.g:74:4:
					{

						current = forceCreateModelElement(
								grammarAccess.getStructuredTextAlgorithmAccess().getStructuredTextAlgorithmAction_0(),
								current);

					}

					// InternalStructuredTextParser.g:80:3: (otherlv_1= VAR ( (
					// (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )*
					// otherlv_4= END_VAR )?
					int alt2 = 2;
					int LA2_0 = input.LA(1);

					if ((LA2_0 == VAR)) {
						alt2 = 1;
					}
					switch (alt2) {
					case 1:
					// InternalStructuredTextParser.g:81:4: otherlv_1= VAR ( (
					// (lv_localVariables_2_0= ruleVar_Decl_Init ) ) otherlv_3= Semicolon )*
					// otherlv_4= END_VAR
					{
						otherlv_1 = (Token) match(input, VAR, FOLLOW_3);

						newLeafNode(otherlv_1, grammarAccess.getStructuredTextAlgorithmAccess().getVARKeyword_1_0());

						// InternalStructuredTextParser.g:85:4: ( ( (lv_localVariables_2_0=
						// ruleVar_Decl_Init ) ) otherlv_3= Semicolon )*
						loop1: do {
							int alt1 = 2;
							int LA1_0 = input.LA(1);

							if ((LA1_0 == CONSTANT || LA1_0 == RULE_ID)) {
								alt1 = 1;
							}

							switch (alt1) {
							case 1:
							// InternalStructuredTextParser.g:86:5: ( (lv_localVariables_2_0=
							// ruleVar_Decl_Init ) ) otherlv_3= Semicolon
							{
								// InternalStructuredTextParser.g:86:5: ( (lv_localVariables_2_0=
								// ruleVar_Decl_Init ) )
								// InternalStructuredTextParser.g:87:6: (lv_localVariables_2_0=
								// ruleVar_Decl_Init )
								{
									// InternalStructuredTextParser.g:87:6: (lv_localVariables_2_0=
									// ruleVar_Decl_Init )
									// InternalStructuredTextParser.g:88:7: lv_localVariables_2_0= ruleVar_Decl_Init
									{

										newCompositeNode(grammarAccess.getStructuredTextAlgorithmAccess()
												.getLocalVariablesVar_Decl_InitParserRuleCall_1_1_0_0());

										pushFollow(FOLLOW_4);
										lv_localVariables_2_0 = ruleVar_Decl_Init();

										state._fsp--;

										if (current == null) {
											current = createModelElementForParent(
													grammarAccess.getStructuredTextAlgorithmRule());
										}
										add(current, "localVariables", lv_localVariables_2_0,
												"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Var_Decl_Init");
										afterParserOrEnumRuleCall();

									}

								}

								otherlv_3 = (Token) match(input, Semicolon, FOLLOW_3);

								newLeafNode(otherlv_3,
										grammarAccess.getStructuredTextAlgorithmAccess().getSemicolonKeyword_1_1_1());

							}
								break;

							default:
								break loop1;
							}
						} while (true);

						otherlv_4 = (Token) match(input, END_VAR, FOLLOW_5);

						newLeafNode(otherlv_4,
								grammarAccess.getStructuredTextAlgorithmAccess().getEND_VARKeyword_1_2());

					}
						break;

					}

					// InternalStructuredTextParser.g:115:3: ( (lv_statements_5_0= ruleStmt_List ) )
					// InternalStructuredTextParser.g:116:4: (lv_statements_5_0= ruleStmt_List )
					{
						// InternalStructuredTextParser.g:116:4: (lv_statements_5_0= ruleStmt_List )
						// InternalStructuredTextParser.g:117:5: lv_statements_5_0= ruleStmt_List
						{

							newCompositeNode(grammarAccess.getStructuredTextAlgorithmAccess()
									.getStatementsStmt_ListParserRuleCall_2_0());

							pushFollow(FOLLOW_2);
							lv_statements_5_0 = ruleStmt_List();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getStructuredTextAlgorithmRule());
							}
							set(current, "statements", lv_statements_5_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleStructuredTextAlgorithm"

	// $ANTLR start "entryRuleVar_Decl_Init"
	// InternalStructuredTextParser.g:138:1: entryRuleVar_Decl_Init returns [EObject
	// current=null] : iv_ruleVar_Decl_Init= ruleVar_Decl_Init EOF ;
	public final EObject entryRuleVar_Decl_Init() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleVar_Decl_Init = null;

		try {
			// InternalStructuredTextParser.g:138:54: (iv_ruleVar_Decl_Init=
			// ruleVar_Decl_Init EOF )
			// InternalStructuredTextParser.g:139:2: iv_ruleVar_Decl_Init= ruleVar_Decl_Init
			// EOF
			{
				newCompositeNode(grammarAccess.getVar_Decl_InitRule());
				pushFollow(FOLLOW_1);
				iv_ruleVar_Decl_Init = ruleVar_Decl_Init();

				state._fsp--;

				current = iv_ruleVar_Decl_Init;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleVar_Decl_Init"

	// $ANTLR start "ruleVar_Decl_Init"
	// InternalStructuredTextParser.g:145:1: ruleVar_Decl_Init returns [EObject
	// current=null] : (this_Var_Decl_Local_0= ruleVar_Decl_Local |
	// this_Var_Decl_Located_1= ruleVar_Decl_Located ) ;
	public final EObject ruleVar_Decl_Init() throws RecognitionException {
		EObject current = null;

		EObject this_Var_Decl_Local_0 = null;

		EObject this_Var_Decl_Located_1 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:151:2: ( (this_Var_Decl_Local_0=
			// ruleVar_Decl_Local | this_Var_Decl_Located_1= ruleVar_Decl_Located ) )
			// InternalStructuredTextParser.g:152:2: (this_Var_Decl_Local_0=
			// ruleVar_Decl_Local | this_Var_Decl_Located_1= ruleVar_Decl_Located )
			{
				// InternalStructuredTextParser.g:152:2: (this_Var_Decl_Local_0=
				// ruleVar_Decl_Local | this_Var_Decl_Located_1= ruleVar_Decl_Located )
				int alt3 = 2;
				int LA3_0 = input.LA(1);

				if ((LA3_0 == CONSTANT)) {
					alt3 = 1;
				} else if ((LA3_0 == RULE_ID)) {
					int LA3_2 = input.LA(2);

					if ((LA3_2 == Colon)) {
						alt3 = 1;
					} else if ((LA3_2 == AT)) {
						alt3 = 2;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 3, 2, input);

						throw nvae;
					}
				} else {
					NoViableAltException nvae = new NoViableAltException("", 3, 0, input);

					throw nvae;
				}
				switch (alt3) {
				case 1:
				// InternalStructuredTextParser.g:153:3: this_Var_Decl_Local_0=
				// ruleVar_Decl_Local
				{

					newCompositeNode(grammarAccess.getVar_Decl_InitAccess().getVar_Decl_LocalParserRuleCall_0());

					pushFollow(FOLLOW_2);
					this_Var_Decl_Local_0 = ruleVar_Decl_Local();

					state._fsp--;

					current = this_Var_Decl_Local_0;
					afterParserOrEnumRuleCall();

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:162:3: this_Var_Decl_Located_1=
				// ruleVar_Decl_Located
				{

					newCompositeNode(grammarAccess.getVar_Decl_InitAccess().getVar_Decl_LocatedParserRuleCall_1());

					pushFollow(FOLLOW_2);
					this_Var_Decl_Located_1 = ruleVar_Decl_Located();

					state._fsp--;

					current = this_Var_Decl_Located_1;
					afterParserOrEnumRuleCall();

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleVar_Decl_Init"

	// $ANTLR start "entryRuleVar_Decl_Local"
	// InternalStructuredTextParser.g:174:1: entryRuleVar_Decl_Local returns
	// [EObject current=null] : iv_ruleVar_Decl_Local= ruleVar_Decl_Local EOF ;
	public final EObject entryRuleVar_Decl_Local() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleVar_Decl_Local = null;

		try {
			// InternalStructuredTextParser.g:174:55: (iv_ruleVar_Decl_Local=
			// ruleVar_Decl_Local EOF )
			// InternalStructuredTextParser.g:175:2: iv_ruleVar_Decl_Local=
			// ruleVar_Decl_Local EOF
			{
				newCompositeNode(grammarAccess.getVar_Decl_LocalRule());
				pushFollow(FOLLOW_1);
				iv_ruleVar_Decl_Local = ruleVar_Decl_Local();

				state._fsp--;

				current = iv_ruleVar_Decl_Local;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleVar_Decl_Local"

	// $ANTLR start "ruleVar_Decl_Local"
	// InternalStructuredTextParser.g:181:1: ruleVar_Decl_Local returns [EObject
	// current=null] : ( () ( (lv_constant_1_0= CONSTANT ) )? ( (lv_name_2_0=
	// RULE_ID ) ) otherlv_3= Colon ( ( ruleType_Name ) ) ( ( (lv_array_5_0=
	// LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) ) otherlv_7=
	// RightSquareBracket )? (otherlv_8= ColonEqualsSign ( (lv_initialValue_9_0=
	// ruleConstant ) ) )? ) ;
	public final EObject ruleVar_Decl_Local() throws RecognitionException {
		EObject current = null;

		Token lv_constant_1_0 = null;
		Token lv_name_2_0 = null;
		Token otherlv_3 = null;
		Token lv_array_5_0 = null;
		Token otherlv_7 = null;
		Token otherlv_8 = null;
		AntlrDatatypeRuleToken lv_arraySize_6_0 = null;

		EObject lv_initialValue_9_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:187:2: ( ( () ( (lv_constant_1_0= CONSTANT )
			// )? ( (lv_name_2_0= RULE_ID ) ) otherlv_3= Colon ( ( ruleType_Name ) ) ( (
			// (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) )
			// otherlv_7= RightSquareBracket )? (otherlv_8= ColonEqualsSign (
			// (lv_initialValue_9_0= ruleConstant ) ) )? ) )
			// InternalStructuredTextParser.g:188:2: ( () ( (lv_constant_1_0= CONSTANT ) )?
			// ( (lv_name_2_0= RULE_ID ) ) otherlv_3= Colon ( ( ruleType_Name ) ) ( (
			// (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) )
			// otherlv_7= RightSquareBracket )? (otherlv_8= ColonEqualsSign (
			// (lv_initialValue_9_0= ruleConstant ) ) )? )
			{
				// InternalStructuredTextParser.g:188:2: ( () ( (lv_constant_1_0= CONSTANT ) )?
				// ( (lv_name_2_0= RULE_ID ) ) otherlv_3= Colon ( ( ruleType_Name ) ) ( (
				// (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) )
				// otherlv_7= RightSquareBracket )? (otherlv_8= ColonEqualsSign (
				// (lv_initialValue_9_0= ruleConstant ) ) )? )
				// InternalStructuredTextParser.g:189:3: () ( (lv_constant_1_0= CONSTANT ) )? (
				// (lv_name_2_0= RULE_ID ) ) otherlv_3= Colon ( ( ruleType_Name ) ) ( (
				// (lv_array_5_0= LeftSquareBracket ) ) ( (lv_arraySize_6_0= ruleArray_Size ) )
				// otherlv_7= RightSquareBracket )? (otherlv_8= ColonEqualsSign (
				// (lv_initialValue_9_0= ruleConstant ) ) )?
				{
					// InternalStructuredTextParser.g:189:3: ()
					// InternalStructuredTextParser.g:190:4:
					{

						current = forceCreateModelElement(
								grammarAccess.getVar_Decl_LocalAccess().getLocalVariableAction_0(), current);

					}

					// InternalStructuredTextParser.g:196:3: ( (lv_constant_1_0= CONSTANT ) )?
					int alt4 = 2;
					int LA4_0 = input.LA(1);

					if ((LA4_0 == CONSTANT)) {
						alt4 = 1;
					}
					switch (alt4) {
					case 1:
					// InternalStructuredTextParser.g:197:4: (lv_constant_1_0= CONSTANT )
					{
						// InternalStructuredTextParser.g:197:4: (lv_constant_1_0= CONSTANT )
						// InternalStructuredTextParser.g:198:5: lv_constant_1_0= CONSTANT
						{
							lv_constant_1_0 = (Token) match(input, CONSTANT, FOLLOW_6);

							newLeafNode(lv_constant_1_0,
									grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0());

							if (current == null) {
								current = createModelElement(grammarAccess.getVar_Decl_LocalRule());
							}
							setWithLastConsumed(current, "constant", true, "CONSTANT");

						}

					}
						break;

					}

					// InternalStructuredTextParser.g:210:3: ( (lv_name_2_0= RULE_ID ) )
					// InternalStructuredTextParser.g:211:4: (lv_name_2_0= RULE_ID )
					{
						// InternalStructuredTextParser.g:211:4: (lv_name_2_0= RULE_ID )
						// InternalStructuredTextParser.g:212:5: lv_name_2_0= RULE_ID
						{
							lv_name_2_0 = (Token) match(input, RULE_ID, FOLLOW_7);

							newLeafNode(lv_name_2_0,
									grammarAccess.getVar_Decl_LocalAccess().getNameIDTerminalRuleCall_2_0());

							if (current == null) {
								current = createModelElement(grammarAccess.getVar_Decl_LocalRule());
							}
							setWithLastConsumed(current, "name", lv_name_2_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ID");

						}

					}

					otherlv_3 = (Token) match(input, Colon, FOLLOW_8);

					newLeafNode(otherlv_3, grammarAccess.getVar_Decl_LocalAccess().getColonKeyword_3());

					// InternalStructuredTextParser.g:232:3: ( ( ruleType_Name ) )
					// InternalStructuredTextParser.g:233:4: ( ruleType_Name )
					{
						// InternalStructuredTextParser.g:233:4: ( ruleType_Name )
						// InternalStructuredTextParser.g:234:5: ruleType_Name
						{

							if (current == null) {
								current = createModelElement(grammarAccess.getVar_Decl_LocalRule());
							}

							newCompositeNode(
									grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeCrossReference_4_0());

							pushFollow(FOLLOW_9);
							ruleType_Name();

							state._fsp--;

							afterParserOrEnumRuleCall();

						}

					}

					// InternalStructuredTextParser.g:248:3: ( ( (lv_array_5_0= LeftSquareBracket )
					// ) ( (lv_arraySize_6_0= ruleArray_Size ) ) otherlv_7= RightSquareBracket )?
					int alt5 = 2;
					int LA5_0 = input.LA(1);

					if ((LA5_0 == LeftSquareBracket)) {
						alt5 = 1;
					}
					switch (alt5) {
					case 1:
					// InternalStructuredTextParser.g:249:4: ( (lv_array_5_0= LeftSquareBracket ) )
					// ( (lv_arraySize_6_0= ruleArray_Size ) ) otherlv_7= RightSquareBracket
					{
						// InternalStructuredTextParser.g:249:4: ( (lv_array_5_0= LeftSquareBracket ) )
						// InternalStructuredTextParser.g:250:5: (lv_array_5_0= LeftSquareBracket )
						{
							// InternalStructuredTextParser.g:250:5: (lv_array_5_0= LeftSquareBracket )
							// InternalStructuredTextParser.g:251:6: lv_array_5_0= LeftSquareBracket
							{
								lv_array_5_0 = (Token) match(input, LeftSquareBracket, FOLLOW_10);

								newLeafNode(lv_array_5_0, grammarAccess.getVar_Decl_LocalAccess()
										.getArrayLeftSquareBracketKeyword_5_0_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getVar_Decl_LocalRule());
								}
								setWithLastConsumed(current, "array", true, "[");

							}

						}

						// InternalStructuredTextParser.g:263:4: ( (lv_arraySize_6_0= ruleArray_Size ) )
						// InternalStructuredTextParser.g:264:5: (lv_arraySize_6_0= ruleArray_Size )
						{
							// InternalStructuredTextParser.g:264:5: (lv_arraySize_6_0= ruleArray_Size )
							// InternalStructuredTextParser.g:265:6: lv_arraySize_6_0= ruleArray_Size
							{

								newCompositeNode(grammarAccess.getVar_Decl_LocalAccess()
										.getArraySizeArray_SizeParserRuleCall_5_1_0());

								pushFollow(FOLLOW_11);
								lv_arraySize_6_0 = ruleArray_Size();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getVar_Decl_LocalRule());
								}
								set(current, "arraySize", lv_arraySize_6_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Array_Size");
								afterParserOrEnumRuleCall();

							}

						}

						otherlv_7 = (Token) match(input, RightSquareBracket, FOLLOW_12);

						newLeafNode(otherlv_7,
								grammarAccess.getVar_Decl_LocalAccess().getRightSquareBracketKeyword_5_2());

					}
						break;

					}

					// InternalStructuredTextParser.g:287:3: (otherlv_8= ColonEqualsSign (
					// (lv_initialValue_9_0= ruleConstant ) ) )?
					int alt6 = 2;
					int LA6_0 = input.LA(1);

					if ((LA6_0 == ColonEqualsSign)) {
						alt6 = 1;
					}
					switch (alt6) {
					case 1:
					// InternalStructuredTextParser.g:288:4: otherlv_8= ColonEqualsSign (
					// (lv_initialValue_9_0= ruleConstant ) )
					{
						otherlv_8 = (Token) match(input, ColonEqualsSign, FOLLOW_13);

						newLeafNode(otherlv_8, grammarAccess.getVar_Decl_LocalAccess().getColonEqualsSignKeyword_6_0());

						// InternalStructuredTextParser.g:292:4: ( (lv_initialValue_9_0= ruleConstant )
						// )
						// InternalStructuredTextParser.g:293:5: (lv_initialValue_9_0= ruleConstant )
						{
							// InternalStructuredTextParser.g:293:5: (lv_initialValue_9_0= ruleConstant )
							// InternalStructuredTextParser.g:294:6: lv_initialValue_9_0= ruleConstant
							{

								newCompositeNode(grammarAccess.getVar_Decl_LocalAccess()
										.getInitialValueConstantParserRuleCall_6_1_0());

								pushFollow(FOLLOW_2);
								lv_initialValue_9_0 = ruleConstant();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getVar_Decl_LocalRule());
								}
								set(current, "initialValue", lv_initialValue_9_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Constant");
								afterParserOrEnumRuleCall();

							}

						}

					}
						break;

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleVar_Decl_Local"

	// $ANTLR start "entryRuleVar_Decl_Located"
	// InternalStructuredTextParser.g:316:1: entryRuleVar_Decl_Located returns
	// [EObject current=null] : iv_ruleVar_Decl_Located= ruleVar_Decl_Located EOF ;
	public final EObject entryRuleVar_Decl_Located() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleVar_Decl_Located = null;

		try {
			// InternalStructuredTextParser.g:316:57: (iv_ruleVar_Decl_Located=
			// ruleVar_Decl_Located EOF )
			// InternalStructuredTextParser.g:317:2: iv_ruleVar_Decl_Located=
			// ruleVar_Decl_Located EOF
			{
				newCompositeNode(grammarAccess.getVar_Decl_LocatedRule());
				pushFollow(FOLLOW_1);
				iv_ruleVar_Decl_Located = ruleVar_Decl_Located();

				state._fsp--;

				current = iv_ruleVar_Decl_Located;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleVar_Decl_Located"

	// $ANTLR start "ruleVar_Decl_Located"
	// InternalStructuredTextParser.g:323:1: ruleVar_Decl_Located returns [EObject
	// current=null] : ( () ( (lv_name_1_0= RULE_ID ) ) (otherlv_2= AT (
	// (lv_location_3_0= ruleVariable ) ) ) otherlv_4= Colon ( ( ruleType_Name ) ) (
	// ( (lv_array_6_0= LeftSquareBracket ) ) ( (lv_arraySize_7_0= ruleArray_Size )
	// ) otherlv_8= RightSquareBracket )? ) ;
	public final EObject ruleVar_Decl_Located() throws RecognitionException {
		EObject current = null;

		Token lv_name_1_0 = null;
		Token otherlv_2 = null;
		Token otherlv_4 = null;
		Token lv_array_6_0 = null;
		Token otherlv_8 = null;
		EObject lv_location_3_0 = null;

		AntlrDatatypeRuleToken lv_arraySize_7_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:329:2: ( ( () ( (lv_name_1_0= RULE_ID ) )
			// (otherlv_2= AT ( (lv_location_3_0= ruleVariable ) ) ) otherlv_4= Colon ( (
			// ruleType_Name ) ) ( ( (lv_array_6_0= LeftSquareBracket ) ) (
			// (lv_arraySize_7_0= ruleArray_Size ) ) otherlv_8= RightSquareBracket )? ) )
			// InternalStructuredTextParser.g:330:2: ( () ( (lv_name_1_0= RULE_ID ) )
			// (otherlv_2= AT ( (lv_location_3_0= ruleVariable ) ) ) otherlv_4= Colon ( (
			// ruleType_Name ) ) ( ( (lv_array_6_0= LeftSquareBracket ) ) (
			// (lv_arraySize_7_0= ruleArray_Size ) ) otherlv_8= RightSquareBracket )? )
			{
				// InternalStructuredTextParser.g:330:2: ( () ( (lv_name_1_0= RULE_ID ) )
				// (otherlv_2= AT ( (lv_location_3_0= ruleVariable ) ) ) otherlv_4= Colon ( (
				// ruleType_Name ) ) ( ( (lv_array_6_0= LeftSquareBracket ) ) (
				// (lv_arraySize_7_0= ruleArray_Size ) ) otherlv_8= RightSquareBracket )? )
				// InternalStructuredTextParser.g:331:3: () ( (lv_name_1_0= RULE_ID ) )
				// (otherlv_2= AT ( (lv_location_3_0= ruleVariable ) ) ) otherlv_4= Colon ( (
				// ruleType_Name ) ) ( ( (lv_array_6_0= LeftSquareBracket ) ) (
				// (lv_arraySize_7_0= ruleArray_Size ) ) otherlv_8= RightSquareBracket )?
				{
					// InternalStructuredTextParser.g:331:3: ()
					// InternalStructuredTextParser.g:332:4:
					{

						current = forceCreateModelElement(
								grammarAccess.getVar_Decl_LocatedAccess().getLocatedVariableAction_0(), current);

					}

					// InternalStructuredTextParser.g:338:3: ( (lv_name_1_0= RULE_ID ) )
					// InternalStructuredTextParser.g:339:4: (lv_name_1_0= RULE_ID )
					{
						// InternalStructuredTextParser.g:339:4: (lv_name_1_0= RULE_ID )
						// InternalStructuredTextParser.g:340:5: lv_name_1_0= RULE_ID
						{
							lv_name_1_0 = (Token) match(input, RULE_ID, FOLLOW_14);

							newLeafNode(lv_name_1_0,
									grammarAccess.getVar_Decl_LocatedAccess().getNameIDTerminalRuleCall_1_0());

							if (current == null) {
								current = createModelElement(grammarAccess.getVar_Decl_LocatedRule());
							}
							setWithLastConsumed(current, "name", lv_name_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ID");

						}

					}

					// InternalStructuredTextParser.g:356:3: (otherlv_2= AT ( (lv_location_3_0=
					// ruleVariable ) ) )
					// InternalStructuredTextParser.g:357:4: otherlv_2= AT ( (lv_location_3_0=
					// ruleVariable ) )
					{
						otherlv_2 = (Token) match(input, AT, FOLLOW_15);

						newLeafNode(otherlv_2, grammarAccess.getVar_Decl_LocatedAccess().getATKeyword_2_0());

						// InternalStructuredTextParser.g:361:4: ( (lv_location_3_0= ruleVariable ) )
						// InternalStructuredTextParser.g:362:5: (lv_location_3_0= ruleVariable )
						{
							// InternalStructuredTextParser.g:362:5: (lv_location_3_0= ruleVariable )
							// InternalStructuredTextParser.g:363:6: lv_location_3_0= ruleVariable
							{

								newCompositeNode(grammarAccess.getVar_Decl_LocatedAccess()
										.getLocationVariableParserRuleCall_2_1_0());

								pushFollow(FOLLOW_7);
								lv_location_3_0 = ruleVariable();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getVar_Decl_LocatedRule());
								}
								set(current, "location", lv_location_3_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Variable");
								afterParserOrEnumRuleCall();

							}

						}

					}

					otherlv_4 = (Token) match(input, Colon, FOLLOW_8);

					newLeafNode(otherlv_4, grammarAccess.getVar_Decl_LocatedAccess().getColonKeyword_3());

					// InternalStructuredTextParser.g:385:3: ( ( ruleType_Name ) )
					// InternalStructuredTextParser.g:386:4: ( ruleType_Name )
					{
						// InternalStructuredTextParser.g:386:4: ( ruleType_Name )
						// InternalStructuredTextParser.g:387:5: ruleType_Name
						{

							if (current == null) {
								current = createModelElement(grammarAccess.getVar_Decl_LocatedRule());
							}

							newCompositeNode(
									grammarAccess.getVar_Decl_LocatedAccess().getTypeDataTypeCrossReference_4_0());

							pushFollow(FOLLOW_16);
							ruleType_Name();

							state._fsp--;

							afterParserOrEnumRuleCall();

						}

					}

					// InternalStructuredTextParser.g:401:3: ( ( (lv_array_6_0= LeftSquareBracket )
					// ) ( (lv_arraySize_7_0= ruleArray_Size ) ) otherlv_8= RightSquareBracket )?
					int alt7 = 2;
					int LA7_0 = input.LA(1);

					if ((LA7_0 == LeftSquareBracket)) {
						alt7 = 1;
					}
					switch (alt7) {
					case 1:
					// InternalStructuredTextParser.g:402:4: ( (lv_array_6_0= LeftSquareBracket ) )
					// ( (lv_arraySize_7_0= ruleArray_Size ) ) otherlv_8= RightSquareBracket
					{
						// InternalStructuredTextParser.g:402:4: ( (lv_array_6_0= LeftSquareBracket ) )
						// InternalStructuredTextParser.g:403:5: (lv_array_6_0= LeftSquareBracket )
						{
							// InternalStructuredTextParser.g:403:5: (lv_array_6_0= LeftSquareBracket )
							// InternalStructuredTextParser.g:404:6: lv_array_6_0= LeftSquareBracket
							{
								lv_array_6_0 = (Token) match(input, LeftSquareBracket, FOLLOW_10);

								newLeafNode(lv_array_6_0, grammarAccess.getVar_Decl_LocatedAccess()
										.getArrayLeftSquareBracketKeyword_5_0_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getVar_Decl_LocatedRule());
								}
								setWithLastConsumed(current, "array", true, "[");

							}

						}

						// InternalStructuredTextParser.g:416:4: ( (lv_arraySize_7_0= ruleArray_Size ) )
						// InternalStructuredTextParser.g:417:5: (lv_arraySize_7_0= ruleArray_Size )
						{
							// InternalStructuredTextParser.g:417:5: (lv_arraySize_7_0= ruleArray_Size )
							// InternalStructuredTextParser.g:418:6: lv_arraySize_7_0= ruleArray_Size
							{

								newCompositeNode(grammarAccess.getVar_Decl_LocatedAccess()
										.getArraySizeArray_SizeParserRuleCall_5_1_0());

								pushFollow(FOLLOW_11);
								lv_arraySize_7_0 = ruleArray_Size();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getVar_Decl_LocatedRule());
								}
								set(current, "arraySize", lv_arraySize_7_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Array_Size");
								afterParserOrEnumRuleCall();

							}

						}

						otherlv_8 = (Token) match(input, RightSquareBracket, FOLLOW_2);

						newLeafNode(otherlv_8,
								grammarAccess.getVar_Decl_LocatedAccess().getRightSquareBracketKeyword_5_2());

					}
						break;

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleVar_Decl_Located"

	// $ANTLR start "entryRuleStmt_List"
	// InternalStructuredTextParser.g:444:1: entryRuleStmt_List returns [EObject
	// current=null] : iv_ruleStmt_List= ruleStmt_List EOF ;
	public final EObject entryRuleStmt_List() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleStmt_List = null;

		try {
			// InternalStructuredTextParser.g:444:50: (iv_ruleStmt_List= ruleStmt_List EOF )
			// InternalStructuredTextParser.g:445:2: iv_ruleStmt_List= ruleStmt_List EOF
			{
				newCompositeNode(grammarAccess.getStmt_ListRule());
				pushFollow(FOLLOW_1);
				iv_ruleStmt_List = ruleStmt_List();

				state._fsp--;

				current = iv_ruleStmt_List;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleStmt_List"

	// $ANTLR start "ruleStmt_List"
	// InternalStructuredTextParser.g:451:1: ruleStmt_List returns [EObject
	// current=null] : ( () ( ( (lv_statements_1_0= ruleStmt ) )? otherlv_2=
	// Semicolon )* ) ;
	public final EObject ruleStmt_List() throws RecognitionException {
		EObject current = null;

		Token otherlv_2 = null;
		EObject lv_statements_1_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:457:2: ( ( () ( ( (lv_statements_1_0= ruleStmt
			// ) )? otherlv_2= Semicolon )* ) )
			// InternalStructuredTextParser.g:458:2: ( () ( ( (lv_statements_1_0= ruleStmt )
			// )? otherlv_2= Semicolon )* )
			{
				// InternalStructuredTextParser.g:458:2: ( () ( ( (lv_statements_1_0= ruleStmt )
				// )? otherlv_2= Semicolon )* )
				// InternalStructuredTextParser.g:459:3: () ( ( (lv_statements_1_0= ruleStmt )
				// )? otherlv_2= Semicolon )*
				{
					// InternalStructuredTextParser.g:459:3: ()
					// InternalStructuredTextParser.g:460:4:
					{

						current = forceCreateModelElement(grammarAccess.getStmt_ListAccess().getStatementListAction_0(),
								current);

					}

					// InternalStructuredTextParser.g:466:3: ( ( (lv_statements_1_0= ruleStmt ) )?
					// otherlv_2= Semicolon )*
					loop9: do {
						int alt9 = 2;
						switch (input.LA(1)) {
						case TIME: {
							int LA9_2 = input.LA(2);

							if ((LA9_2 == LeftParenthesis)) {
								alt9 = 1;
							}

						}
							break;
						case T: {
							int LA9_3 = input.LA(2);

							if (((LA9_3 >= B && LA9_3 <= X) || LA9_3 == ColonEqualsSign || LA9_3 == FullStop
									|| LA9_3 == LeftSquareBracket)) {
								alt9 = 1;
							}

						}
							break;
						case LT: {
							int LA9_4 = input.LA(2);

							if (((LA9_4 >= B && LA9_4 <= X) || LA9_4 == ColonEqualsSign || LA9_4 == FullStop
									|| LA9_4 == LeftSquareBracket)) {
								alt9 = 1;
							}

						}
							break;
						case DT: {
							int LA9_5 = input.LA(2);

							if (((LA9_5 >= B && LA9_5 <= X) || LA9_5 == ColonEqualsSign || LA9_5 == FullStop
									|| LA9_5 == LeftSquareBracket)) {
								alt9 = 1;
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
							alt9 = 1;
						}
							break;

						}

						switch (alt9) {
						case 1:
						// InternalStructuredTextParser.g:467:4: ( (lv_statements_1_0= ruleStmt ) )?
						// otherlv_2= Semicolon
						{
							// InternalStructuredTextParser.g:467:4: ( (lv_statements_1_0= ruleStmt ) )?
							int alt8 = 2;
							int LA8_0 = input.LA(1);

							if ((LA8_0 == CONTINUE || (LA8_0 >= REPEAT && LA8_0 <= RETURN) || LA8_0 == SUPER
									|| LA8_0 == WHILE || LA8_0 == CASE || LA8_0 == EXIT || LA8_0 == TIME || LA8_0 == FOR
									|| (LA8_0 >= DT && LA8_0 <= IF) || LA8_0 == LT || LA8_0 == T || LA8_0 == RULE_ID)) {
								alt8 = 1;
							}
							switch (alt8) {
							case 1:
							// InternalStructuredTextParser.g:468:5: (lv_statements_1_0= ruleStmt )
							{
								// InternalStructuredTextParser.g:468:5: (lv_statements_1_0= ruleStmt )
								// InternalStructuredTextParser.g:469:6: lv_statements_1_0= ruleStmt
								{

									newCompositeNode(
											grammarAccess.getStmt_ListAccess().getStatementsStmtParserRuleCall_1_0_0());

									pushFollow(FOLLOW_4);
									lv_statements_1_0 = ruleStmt();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getStmt_ListRule());
									}
									add(current, "statements", lv_statements_1_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt");
									afterParserOrEnumRuleCall();

								}

							}
								break;

							}

							otherlv_2 = (Token) match(input, Semicolon, FOLLOW_17);

							newLeafNode(otherlv_2, grammarAccess.getStmt_ListAccess().getSemicolonKeyword_1_1());

						}
							break;

						default:
							break loop9;
						}
					} while (true);

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleStmt_List"

	// $ANTLR start "entryRuleStmt"
	// InternalStructuredTextParser.g:495:1: entryRuleStmt returns [EObject
	// current=null] : iv_ruleStmt= ruleStmt EOF ;
	public final EObject entryRuleStmt() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleStmt = null;

		try {
			// InternalStructuredTextParser.g:495:45: (iv_ruleStmt= ruleStmt EOF )
			// InternalStructuredTextParser.g:496:2: iv_ruleStmt= ruleStmt EOF
			{
				newCompositeNode(grammarAccess.getStmtRule());
				pushFollow(FOLLOW_1);
				iv_ruleStmt = ruleStmt();

				state._fsp--;

				current = iv_ruleStmt;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleStmt"

	// $ANTLR start "ruleStmt"
	// InternalStructuredTextParser.g:502:1: ruleStmt returns [EObject current=null]
	// : (this_Assign_Stmt_0= ruleAssign_Stmt | this_Subprog_Ctrl_Stmt_1=
	// ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2= ruleSelection_Stmt |
	// this_Iteration_Stmt_3= ruleIteration_Stmt ) ;
	public final EObject ruleStmt() throws RecognitionException {
		EObject current = null;

		EObject this_Assign_Stmt_0 = null;

		EObject this_Subprog_Ctrl_Stmt_1 = null;

		EObject this_Selection_Stmt_2 = null;

		EObject this_Iteration_Stmt_3 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:508:2: ( (this_Assign_Stmt_0= ruleAssign_Stmt
			// | this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2=
			// ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt ) )
			// InternalStructuredTextParser.g:509:2: (this_Assign_Stmt_0= ruleAssign_Stmt |
			// this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2=
			// ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt )
			{
				// InternalStructuredTextParser.g:509:2: (this_Assign_Stmt_0= ruleAssign_Stmt |
				// this_Subprog_Ctrl_Stmt_1= ruleSubprog_Ctrl_Stmt | this_Selection_Stmt_2=
				// ruleSelection_Stmt | this_Iteration_Stmt_3= ruleIteration_Stmt )
				int alt10 = 4;
				switch (input.LA(1)) {
				case RULE_ID: {
					int LA10_1 = input.LA(2);

					if ((LA10_1 == LeftParenthesis)) {
						alt10 = 2;
					} else if (((LA10_1 >= B && LA10_1 <= X) || LA10_1 == ColonEqualsSign || LA10_1 == FullStop
							|| LA10_1 == LeftSquareBracket)) {
						alt10 = 1;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 10, 1, input);

						throw nvae;
					}
				}
					break;
				case DT:
				case LT:
				case T: {
					alt10 = 1;
				}
					break;
				case RETURN:
				case SUPER:
				case TIME: {
					alt10 = 2;
				}
					break;
				case CASE:
				case IF: {
					alt10 = 3;
				}
					break;
				case CONTINUE:
				case REPEAT:
				case WHILE:
				case EXIT:
				case FOR: {
					alt10 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 10, 0, input);

					throw nvae;
				}

				switch (alt10) {
				case 1:
				// InternalStructuredTextParser.g:510:3: this_Assign_Stmt_0= ruleAssign_Stmt
				{

					newCompositeNode(grammarAccess.getStmtAccess().getAssign_StmtParserRuleCall_0());

					pushFollow(FOLLOW_2);
					this_Assign_Stmt_0 = ruleAssign_Stmt();

					state._fsp--;

					current = this_Assign_Stmt_0;
					afterParserOrEnumRuleCall();

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:519:3: this_Subprog_Ctrl_Stmt_1=
				// ruleSubprog_Ctrl_Stmt
				{

					newCompositeNode(grammarAccess.getStmtAccess().getSubprog_Ctrl_StmtParserRuleCall_1());

					pushFollow(FOLLOW_2);
					this_Subprog_Ctrl_Stmt_1 = ruleSubprog_Ctrl_Stmt();

					state._fsp--;

					current = this_Subprog_Ctrl_Stmt_1;
					afterParserOrEnumRuleCall();

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:528:3: this_Selection_Stmt_2=
				// ruleSelection_Stmt
				{

					newCompositeNode(grammarAccess.getStmtAccess().getSelection_StmtParserRuleCall_2());

					pushFollow(FOLLOW_2);
					this_Selection_Stmt_2 = ruleSelection_Stmt();

					state._fsp--;

					current = this_Selection_Stmt_2;
					afterParserOrEnumRuleCall();

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:537:3: this_Iteration_Stmt_3=
				// ruleIteration_Stmt
				{

					newCompositeNode(grammarAccess.getStmtAccess().getIteration_StmtParserRuleCall_3());

					pushFollow(FOLLOW_2);
					this_Iteration_Stmt_3 = ruleIteration_Stmt();

					state._fsp--;

					current = this_Iteration_Stmt_3;
					afterParserOrEnumRuleCall();

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleStmt"

	// $ANTLR start "entryRuleAssign_Stmt"
	// InternalStructuredTextParser.g:549:1: entryRuleAssign_Stmt returns [EObject
	// current=null] : iv_ruleAssign_Stmt= ruleAssign_Stmt EOF ;
	public final EObject entryRuleAssign_Stmt() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleAssign_Stmt = null;

		try {
			// InternalStructuredTextParser.g:549:52: (iv_ruleAssign_Stmt= ruleAssign_Stmt
			// EOF )
			// InternalStructuredTextParser.g:550:2: iv_ruleAssign_Stmt= ruleAssign_Stmt EOF
			{
				newCompositeNode(grammarAccess.getAssign_StmtRule());
				pushFollow(FOLLOW_1);
				iv_ruleAssign_Stmt = ruleAssign_Stmt();

				state._fsp--;

				current = iv_ruleAssign_Stmt;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleAssign_Stmt"

	// $ANTLR start "ruleAssign_Stmt"
	// InternalStructuredTextParser.g:556:1: ruleAssign_Stmt returns [EObject
	// current=null] : ( ( (lv_variable_0_0= ruleVariable ) ) otherlv_1=
	// ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) ) ;
	public final EObject ruleAssign_Stmt() throws RecognitionException {
		EObject current = null;

		Token otherlv_1 = null;
		EObject lv_variable_0_0 = null;

		EObject lv_expression_2_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:562:2: ( ( ( (lv_variable_0_0= ruleVariable )
			// ) otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) ) )
			// InternalStructuredTextParser.g:563:2: ( ( (lv_variable_0_0= ruleVariable ) )
			// otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) )
			{
				// InternalStructuredTextParser.g:563:2: ( ( (lv_variable_0_0= ruleVariable ) )
				// otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) ) )
				// InternalStructuredTextParser.g:564:3: ( (lv_variable_0_0= ruleVariable ) )
				// otherlv_1= ColonEqualsSign ( (lv_expression_2_0= ruleExpression ) )
				{
					// InternalStructuredTextParser.g:564:3: ( (lv_variable_0_0= ruleVariable ) )
					// InternalStructuredTextParser.g:565:4: (lv_variable_0_0= ruleVariable )
					{
						// InternalStructuredTextParser.g:565:4: (lv_variable_0_0= ruleVariable )
						// InternalStructuredTextParser.g:566:5: lv_variable_0_0= ruleVariable
						{

							newCompositeNode(
									grammarAccess.getAssign_StmtAccess().getVariableVariableParserRuleCall_0_0());

							pushFollow(FOLLOW_18);
							lv_variable_0_0 = ruleVariable();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getAssign_StmtRule());
							}
							set(current, "variable", lv_variable_0_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Variable");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_1 = (Token) match(input, ColonEqualsSign, FOLLOW_19);

					newLeafNode(otherlv_1, grammarAccess.getAssign_StmtAccess().getColonEqualsSignKeyword_1());

					// InternalStructuredTextParser.g:587:3: ( (lv_expression_2_0= ruleExpression )
					// )
					// InternalStructuredTextParser.g:588:4: (lv_expression_2_0= ruleExpression )
					{
						// InternalStructuredTextParser.g:588:4: (lv_expression_2_0= ruleExpression )
						// InternalStructuredTextParser.g:589:5: lv_expression_2_0= ruleExpression
						{

							newCompositeNode(
									grammarAccess.getAssign_StmtAccess().getExpressionExpressionParserRuleCall_2_0());

							pushFollow(FOLLOW_2);
							lv_expression_2_0 = ruleExpression();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getAssign_StmtRule());
							}
							set(current, "expression", lv_expression_2_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleAssign_Stmt"

	// $ANTLR start "entryRuleSubprog_Ctrl_Stmt"
	// InternalStructuredTextParser.g:610:1: entryRuleSubprog_Ctrl_Stmt returns
	// [EObject current=null] : iv_ruleSubprog_Ctrl_Stmt= ruleSubprog_Ctrl_Stmt EOF
	// ;
	public final EObject entryRuleSubprog_Ctrl_Stmt() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleSubprog_Ctrl_Stmt = null;

		try {
			// InternalStructuredTextParser.g:610:58: (iv_ruleSubprog_Ctrl_Stmt=
			// ruleSubprog_Ctrl_Stmt EOF )
			// InternalStructuredTextParser.g:611:2: iv_ruleSubprog_Ctrl_Stmt=
			// ruleSubprog_Ctrl_Stmt EOF
			{
				newCompositeNode(grammarAccess.getSubprog_Ctrl_StmtRule());
				pushFollow(FOLLOW_1);
				iv_ruleSubprog_Ctrl_Stmt = ruleSubprog_Ctrl_Stmt();

				state._fsp--;

				current = iv_ruleSubprog_Ctrl_Stmt;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleSubprog_Ctrl_Stmt"

	// $ANTLR start "ruleSubprog_Ctrl_Stmt"
	// InternalStructuredTextParser.g:617:1: ruleSubprog_Ctrl_Stmt returns [EObject
	// current=null] : (this_Func_Call_0= ruleFunc_Call | ( () otherlv_2= SUPER
	// otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | ( () otherlv_6=
	// RETURN ) ) ;
	public final EObject ruleSubprog_Ctrl_Stmt() throws RecognitionException {
		EObject current = null;

		Token otherlv_2 = null;
		Token otherlv_3 = null;
		Token otherlv_4 = null;
		Token otherlv_6 = null;
		EObject this_Func_Call_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:623:2: ( (this_Func_Call_0= ruleFunc_Call | (
			// () otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis )
			// | ( () otherlv_6= RETURN ) ) )
			// InternalStructuredTextParser.g:624:2: (this_Func_Call_0= ruleFunc_Call | ( ()
			// otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | (
			// () otherlv_6= RETURN ) )
			{
				// InternalStructuredTextParser.g:624:2: (this_Func_Call_0= ruleFunc_Call | ( ()
				// otherlv_2= SUPER otherlv_3= LeftParenthesis otherlv_4= RightParenthesis ) | (
				// () otherlv_6= RETURN ) )
				int alt11 = 3;
				switch (input.LA(1)) {
				case TIME:
				case RULE_ID: {
					alt11 = 1;
				}
					break;
				case SUPER: {
					alt11 = 2;
				}
					break;
				case RETURN: {
					alt11 = 3;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 11, 0, input);

					throw nvae;
				}

				switch (alt11) {
				case 1:
				// InternalStructuredTextParser.g:625:3: this_Func_Call_0= ruleFunc_Call
				{

					newCompositeNode(grammarAccess.getSubprog_Ctrl_StmtAccess().getFunc_CallParserRuleCall_0());

					pushFollow(FOLLOW_2);
					this_Func_Call_0 = ruleFunc_Call();

					state._fsp--;

					current = this_Func_Call_0;
					afterParserOrEnumRuleCall();

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:634:3: ( () otherlv_2= SUPER otherlv_3=
				// LeftParenthesis otherlv_4= RightParenthesis )
				{
					// InternalStructuredTextParser.g:634:3: ( () otherlv_2= SUPER otherlv_3=
					// LeftParenthesis otherlv_4= RightParenthesis )
					// InternalStructuredTextParser.g:635:4: () otherlv_2= SUPER otherlv_3=
					// LeftParenthesis otherlv_4= RightParenthesis
					{
						// InternalStructuredTextParser.g:635:4: ()
						// InternalStructuredTextParser.g:636:5:
						{

							current = forceCreateModelElement(
									grammarAccess.getSubprog_Ctrl_StmtAccess().getSuperStatementAction_1_0(), current);

						}

						otherlv_2 = (Token) match(input, SUPER, FOLLOW_20);

						newLeafNode(otherlv_2, grammarAccess.getSubprog_Ctrl_StmtAccess().getSUPERKeyword_1_1());

						otherlv_3 = (Token) match(input, LeftParenthesis, FOLLOW_21);

						newLeafNode(otherlv_3,
								grammarAccess.getSubprog_Ctrl_StmtAccess().getLeftParenthesisKeyword_1_2());

						otherlv_4 = (Token) match(input, RightParenthesis, FOLLOW_2);

						newLeafNode(otherlv_4,
								grammarAccess.getSubprog_Ctrl_StmtAccess().getRightParenthesisKeyword_1_3());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:656:3: ( () otherlv_6= RETURN )
				{
					// InternalStructuredTextParser.g:656:3: ( () otherlv_6= RETURN )
					// InternalStructuredTextParser.g:657:4: () otherlv_6= RETURN
					{
						// InternalStructuredTextParser.g:657:4: ()
						// InternalStructuredTextParser.g:658:5:
						{

							current = forceCreateModelElement(
									grammarAccess.getSubprog_Ctrl_StmtAccess().getReturnStatementAction_2_0(), current);

						}

						otherlv_6 = (Token) match(input, RETURN, FOLLOW_2);

						newLeafNode(otherlv_6, grammarAccess.getSubprog_Ctrl_StmtAccess().getRETURNKeyword_2_1());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleSubprog_Ctrl_Stmt"

	// $ANTLR start "entryRuleSelection_Stmt"
	// InternalStructuredTextParser.g:673:1: entryRuleSelection_Stmt returns
	// [EObject current=null] : iv_ruleSelection_Stmt= ruleSelection_Stmt EOF ;
	public final EObject entryRuleSelection_Stmt() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleSelection_Stmt = null;

		try {
			// InternalStructuredTextParser.g:673:55: (iv_ruleSelection_Stmt=
			// ruleSelection_Stmt EOF )
			// InternalStructuredTextParser.g:674:2: iv_ruleSelection_Stmt=
			// ruleSelection_Stmt EOF
			{
				newCompositeNode(grammarAccess.getSelection_StmtRule());
				pushFollow(FOLLOW_1);
				iv_ruleSelection_Stmt = ruleSelection_Stmt();

				state._fsp--;

				current = iv_ruleSelection_Stmt;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleSelection_Stmt"

	// $ANTLR start "ruleSelection_Stmt"
	// InternalStructuredTextParser.g:680:1: ruleSelection_Stmt returns [EObject
	// current=null] : (this_IF_Stmt_0= ruleIF_Stmt | this_Case_Stmt_1=
	// ruleCase_Stmt ) ;
	public final EObject ruleSelection_Stmt() throws RecognitionException {
		EObject current = null;

		EObject this_IF_Stmt_0 = null;

		EObject this_Case_Stmt_1 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:686:2: ( (this_IF_Stmt_0= ruleIF_Stmt |
			// this_Case_Stmt_1= ruleCase_Stmt ) )
			// InternalStructuredTextParser.g:687:2: (this_IF_Stmt_0= ruleIF_Stmt |
			// this_Case_Stmt_1= ruleCase_Stmt )
			{
				// InternalStructuredTextParser.g:687:2: (this_IF_Stmt_0= ruleIF_Stmt |
				// this_Case_Stmt_1= ruleCase_Stmt )
				int alt12 = 2;
				int LA12_0 = input.LA(1);

				if ((LA12_0 == IF)) {
					alt12 = 1;
				} else if ((LA12_0 == CASE)) {
					alt12 = 2;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 12, 0, input);

					throw nvae;
				}
				switch (alt12) {
				case 1:
				// InternalStructuredTextParser.g:688:3: this_IF_Stmt_0= ruleIF_Stmt
				{

					newCompositeNode(grammarAccess.getSelection_StmtAccess().getIF_StmtParserRuleCall_0());

					pushFollow(FOLLOW_2);
					this_IF_Stmt_0 = ruleIF_Stmt();

					state._fsp--;

					current = this_IF_Stmt_0;
					afterParserOrEnumRuleCall();

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:697:3: this_Case_Stmt_1= ruleCase_Stmt
				{

					newCompositeNode(grammarAccess.getSelection_StmtAccess().getCase_StmtParserRuleCall_1());

					pushFollow(FOLLOW_2);
					this_Case_Stmt_1 = ruleCase_Stmt();

					state._fsp--;

					current = this_Case_Stmt_1;
					afterParserOrEnumRuleCall();

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleSelection_Stmt"

	// $ANTLR start "entryRuleIF_Stmt"
	// InternalStructuredTextParser.g:709:1: entryRuleIF_Stmt returns [EObject
	// current=null] : iv_ruleIF_Stmt= ruleIF_Stmt EOF ;
	public final EObject entryRuleIF_Stmt() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleIF_Stmt = null;

		try {
			// InternalStructuredTextParser.g:709:48: (iv_ruleIF_Stmt= ruleIF_Stmt EOF )
			// InternalStructuredTextParser.g:710:2: iv_ruleIF_Stmt= ruleIF_Stmt EOF
			{
				newCompositeNode(grammarAccess.getIF_StmtRule());
				pushFollow(FOLLOW_1);
				iv_ruleIF_Stmt = ruleIF_Stmt();

				state._fsp--;

				current = iv_ruleIF_Stmt;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleIF_Stmt"

	// $ANTLR start "ruleIF_Stmt"
	// InternalStructuredTextParser.g:716:1: ruleIF_Stmt returns [EObject
	// current=null] : (otherlv_0= IF ( (lv_expression_1_0= ruleExpression ) )
	// otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) ( (lv_elseif_4_0=
	// ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )? otherlv_6= END_IF
	// ) ;
	public final EObject ruleIF_Stmt() throws RecognitionException {
		EObject current = null;

		Token otherlv_0 = null;
		Token otherlv_2 = null;
		Token otherlv_6 = null;
		EObject lv_expression_1_0 = null;

		EObject lv_statments_3_0 = null;

		EObject lv_elseif_4_0 = null;

		EObject lv_else_5_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:722:2: ( (otherlv_0= IF ( (lv_expression_1_0=
			// ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) (
			// (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )?
			// otherlv_6= END_IF ) )
			// InternalStructuredTextParser.g:723:2: (otherlv_0= IF ( (lv_expression_1_0=
			// ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) (
			// (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )?
			// otherlv_6= END_IF )
			{
				// InternalStructuredTextParser.g:723:2: (otherlv_0= IF ( (lv_expression_1_0=
				// ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) (
				// (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )?
				// otherlv_6= END_IF )
				// InternalStructuredTextParser.g:724:3: otherlv_0= IF ( (lv_expression_1_0=
				// ruleExpression ) ) otherlv_2= THEN ( (lv_statments_3_0= ruleStmt_List ) ) (
				// (lv_elseif_4_0= ruleELSIF_Clause ) )* ( (lv_else_5_0= ruleELSE_Clause ) )?
				// otherlv_6= END_IF
				{
					otherlv_0 = (Token) match(input, IF, FOLLOW_19);

					newLeafNode(otherlv_0, grammarAccess.getIF_StmtAccess().getIFKeyword_0());

					// InternalStructuredTextParser.g:728:3: ( (lv_expression_1_0= ruleExpression )
					// )
					// InternalStructuredTextParser.g:729:4: (lv_expression_1_0= ruleExpression )
					{
						// InternalStructuredTextParser.g:729:4: (lv_expression_1_0= ruleExpression )
						// InternalStructuredTextParser.g:730:5: lv_expression_1_0= ruleExpression
						{

							newCompositeNode(
									grammarAccess.getIF_StmtAccess().getExpressionExpressionParserRuleCall_1_0());

							pushFollow(FOLLOW_22);
							lv_expression_1_0 = ruleExpression();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getIF_StmtRule());
							}
							set(current, "expression", lv_expression_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_2 = (Token) match(input, THEN, FOLLOW_23);

					newLeafNode(otherlv_2, grammarAccess.getIF_StmtAccess().getTHENKeyword_2());

					// InternalStructuredTextParser.g:751:3: ( (lv_statments_3_0= ruleStmt_List ) )
					// InternalStructuredTextParser.g:752:4: (lv_statments_3_0= ruleStmt_List )
					{
						// InternalStructuredTextParser.g:752:4: (lv_statments_3_0= ruleStmt_List )
						// InternalStructuredTextParser.g:753:5: lv_statments_3_0= ruleStmt_List
						{

							newCompositeNode(
									grammarAccess.getIF_StmtAccess().getStatmentsStmt_ListParserRuleCall_3_0());

							pushFollow(FOLLOW_24);
							lv_statments_3_0 = ruleStmt_List();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getIF_StmtRule());
							}
							set(current, "statments", lv_statments_3_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
							afterParserOrEnumRuleCall();

						}

					}

					// InternalStructuredTextParser.g:770:3: ( (lv_elseif_4_0= ruleELSIF_Clause ) )*
					loop13: do {
						int alt13 = 2;
						int LA13_0 = input.LA(1);

						if ((LA13_0 == ELSIF)) {
							alt13 = 1;
						}

						switch (alt13) {
						case 1:
						// InternalStructuredTextParser.g:771:4: (lv_elseif_4_0= ruleELSIF_Clause )
						{
							// InternalStructuredTextParser.g:771:4: (lv_elseif_4_0= ruleELSIF_Clause )
							// InternalStructuredTextParser.g:772:5: lv_elseif_4_0= ruleELSIF_Clause
							{

								newCompositeNode(
										grammarAccess.getIF_StmtAccess().getElseifELSIF_ClauseParserRuleCall_4_0());

								pushFollow(FOLLOW_24);
								lv_elseif_4_0 = ruleELSIF_Clause();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getIF_StmtRule());
								}
								add(current, "elseif", lv_elseif_4_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ELSIF_Clause");
								afterParserOrEnumRuleCall();

							}

						}
							break;

						default:
							break loop13;
						}
					} while (true);

					// InternalStructuredTextParser.g:789:3: ( (lv_else_5_0= ruleELSE_Clause ) )?
					int alt14 = 2;
					int LA14_0 = input.LA(1);

					if ((LA14_0 == ELSE)) {
						alt14 = 1;
					}
					switch (alt14) {
					case 1:
					// InternalStructuredTextParser.g:790:4: (lv_else_5_0= ruleELSE_Clause )
					{
						// InternalStructuredTextParser.g:790:4: (lv_else_5_0= ruleELSE_Clause )
						// InternalStructuredTextParser.g:791:5: lv_else_5_0= ruleELSE_Clause
						{

							newCompositeNode(grammarAccess.getIF_StmtAccess().getElseELSE_ClauseParserRuleCall_5_0());

							pushFollow(FOLLOW_25);
							lv_else_5_0 = ruleELSE_Clause();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getIF_StmtRule());
							}
							set(current, "else", lv_else_5_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ELSE_Clause");
							afterParserOrEnumRuleCall();

						}

					}
						break;

					}

					otherlv_6 = (Token) match(input, END_IF, FOLLOW_2);

					newLeafNode(otherlv_6, grammarAccess.getIF_StmtAccess().getEND_IFKeyword_6());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleIF_Stmt"

	// $ANTLR start "entryRuleELSIF_Clause"
	// InternalStructuredTextParser.g:816:1: entryRuleELSIF_Clause returns [EObject
	// current=null] : iv_ruleELSIF_Clause= ruleELSIF_Clause EOF ;
	public final EObject entryRuleELSIF_Clause() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleELSIF_Clause = null;

		try {
			// InternalStructuredTextParser.g:816:53: (iv_ruleELSIF_Clause= ruleELSIF_Clause
			// EOF )
			// InternalStructuredTextParser.g:817:2: iv_ruleELSIF_Clause= ruleELSIF_Clause
			// EOF
			{
				newCompositeNode(grammarAccess.getELSIF_ClauseRule());
				pushFollow(FOLLOW_1);
				iv_ruleELSIF_Clause = ruleELSIF_Clause();

				state._fsp--;

				current = iv_ruleELSIF_Clause;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleELSIF_Clause"

	// $ANTLR start "ruleELSIF_Clause"
	// InternalStructuredTextParser.g:823:1: ruleELSIF_Clause returns [EObject
	// current=null] : (otherlv_0= ELSIF ( (lv_expression_1_0= ruleExpression ) )
	// otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) ) ;
	public final EObject ruleELSIF_Clause() throws RecognitionException {
		EObject current = null;

		Token otherlv_0 = null;
		Token otherlv_2 = null;
		EObject lv_expression_1_0 = null;

		EObject lv_statements_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:829:2: ( (otherlv_0= ELSIF (
			// (lv_expression_1_0= ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0=
			// ruleStmt_List ) ) ) )
			// InternalStructuredTextParser.g:830:2: (otherlv_0= ELSIF ( (lv_expression_1_0=
			// ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) )
			{
				// InternalStructuredTextParser.g:830:2: (otherlv_0= ELSIF ( (lv_expression_1_0=
				// ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) ) )
				// InternalStructuredTextParser.g:831:3: otherlv_0= ELSIF ( (lv_expression_1_0=
				// ruleExpression ) ) otherlv_2= THEN ( (lv_statements_3_0= ruleStmt_List ) )
				{
					otherlv_0 = (Token) match(input, ELSIF, FOLLOW_19);

					newLeafNode(otherlv_0, grammarAccess.getELSIF_ClauseAccess().getELSIFKeyword_0());

					// InternalStructuredTextParser.g:835:3: ( (lv_expression_1_0= ruleExpression )
					// )
					// InternalStructuredTextParser.g:836:4: (lv_expression_1_0= ruleExpression )
					{
						// InternalStructuredTextParser.g:836:4: (lv_expression_1_0= ruleExpression )
						// InternalStructuredTextParser.g:837:5: lv_expression_1_0= ruleExpression
						{

							newCompositeNode(
									grammarAccess.getELSIF_ClauseAccess().getExpressionExpressionParserRuleCall_1_0());

							pushFollow(FOLLOW_22);
							lv_expression_1_0 = ruleExpression();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getELSIF_ClauseRule());
							}
							set(current, "expression", lv_expression_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_2 = (Token) match(input, THEN, FOLLOW_5);

					newLeafNode(otherlv_2, grammarAccess.getELSIF_ClauseAccess().getTHENKeyword_2());

					// InternalStructuredTextParser.g:858:3: ( (lv_statements_3_0= ruleStmt_List ) )
					// InternalStructuredTextParser.g:859:4: (lv_statements_3_0= ruleStmt_List )
					{
						// InternalStructuredTextParser.g:859:4: (lv_statements_3_0= ruleStmt_List )
						// InternalStructuredTextParser.g:860:5: lv_statements_3_0= ruleStmt_List
						{

							newCompositeNode(
									grammarAccess.getELSIF_ClauseAccess().getStatementsStmt_ListParserRuleCall_3_0());

							pushFollow(FOLLOW_2);
							lv_statements_3_0 = ruleStmt_List();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getELSIF_ClauseRule());
							}
							set(current, "statements", lv_statements_3_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleELSIF_Clause"

	// $ANTLR start "entryRuleELSE_Clause"
	// InternalStructuredTextParser.g:881:1: entryRuleELSE_Clause returns [EObject
	// current=null] : iv_ruleELSE_Clause= ruleELSE_Clause EOF ;
	public final EObject entryRuleELSE_Clause() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleELSE_Clause = null;

		try {
			// InternalStructuredTextParser.g:881:52: (iv_ruleELSE_Clause= ruleELSE_Clause
			// EOF )
			// InternalStructuredTextParser.g:882:2: iv_ruleELSE_Clause= ruleELSE_Clause EOF
			{
				newCompositeNode(grammarAccess.getELSE_ClauseRule());
				pushFollow(FOLLOW_1);
				iv_ruleELSE_Clause = ruleELSE_Clause();

				state._fsp--;

				current = iv_ruleELSE_Clause;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleELSE_Clause"

	// $ANTLR start "ruleELSE_Clause"
	// InternalStructuredTextParser.g:888:1: ruleELSE_Clause returns [EObject
	// current=null] : (otherlv_0= ELSE ( (lv_statements_1_0= ruleStmt_List ) ) ) ;
	public final EObject ruleELSE_Clause() throws RecognitionException {
		EObject current = null;

		Token otherlv_0 = null;
		EObject lv_statements_1_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:894:2: ( (otherlv_0= ELSE (
			// (lv_statements_1_0= ruleStmt_List ) ) ) )
			// InternalStructuredTextParser.g:895:2: (otherlv_0= ELSE ( (lv_statements_1_0=
			// ruleStmt_List ) ) )
			{
				// InternalStructuredTextParser.g:895:2: (otherlv_0= ELSE ( (lv_statements_1_0=
				// ruleStmt_List ) ) )
				// InternalStructuredTextParser.g:896:3: otherlv_0= ELSE ( (lv_statements_1_0=
				// ruleStmt_List ) )
				{
					otherlv_0 = (Token) match(input, ELSE, FOLLOW_5);

					newLeafNode(otherlv_0, grammarAccess.getELSE_ClauseAccess().getELSEKeyword_0());

					// InternalStructuredTextParser.g:900:3: ( (lv_statements_1_0= ruleStmt_List ) )
					// InternalStructuredTextParser.g:901:4: (lv_statements_1_0= ruleStmt_List )
					{
						// InternalStructuredTextParser.g:901:4: (lv_statements_1_0= ruleStmt_List )
						// InternalStructuredTextParser.g:902:5: lv_statements_1_0= ruleStmt_List
						{

							newCompositeNode(
									grammarAccess.getELSE_ClauseAccess().getStatementsStmt_ListParserRuleCall_1_0());

							pushFollow(FOLLOW_2);
							lv_statements_1_0 = ruleStmt_List();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getELSE_ClauseRule());
							}
							set(current, "statements", lv_statements_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleELSE_Clause"

	// $ANTLR start "entryRuleCase_Stmt"
	// InternalStructuredTextParser.g:923:1: entryRuleCase_Stmt returns [EObject
	// current=null] : iv_ruleCase_Stmt= ruleCase_Stmt EOF ;
	public final EObject entryRuleCase_Stmt() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleCase_Stmt = null;

		try {
			// InternalStructuredTextParser.g:923:50: (iv_ruleCase_Stmt= ruleCase_Stmt EOF )
			// InternalStructuredTextParser.g:924:2: iv_ruleCase_Stmt= ruleCase_Stmt EOF
			{
				newCompositeNode(grammarAccess.getCase_StmtRule());
				pushFollow(FOLLOW_1);
				iv_ruleCase_Stmt = ruleCase_Stmt();

				state._fsp--;

				current = iv_ruleCase_Stmt;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleCase_Stmt"

	// $ANTLR start "ruleCase_Stmt"
	// InternalStructuredTextParser.g:930:1: ruleCase_Stmt returns [EObject
	// current=null] : (otherlv_0= CASE ( (lv_expression_1_0= ruleExpression ) )
	// otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ ( (lv_else_4_0=
	// ruleELSE_Clause ) )? otherlv_5= END_CASE ) ;
	public final EObject ruleCase_Stmt() throws RecognitionException {
		EObject current = null;

		Token otherlv_0 = null;
		Token otherlv_2 = null;
		Token otherlv_5 = null;
		EObject lv_expression_1_0 = null;

		EObject lv_case_3_0 = null;

		EObject lv_else_4_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:936:2: ( (otherlv_0= CASE (
			// (lv_expression_1_0= ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0=
			// ruleCase_Selection ) )+ ( (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5=
			// END_CASE ) )
			// InternalStructuredTextParser.g:937:2: (otherlv_0= CASE ( (lv_expression_1_0=
			// ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ (
			// (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE )
			{
				// InternalStructuredTextParser.g:937:2: (otherlv_0= CASE ( (lv_expression_1_0=
				// ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ (
				// (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE )
				// InternalStructuredTextParser.g:938:3: otherlv_0= CASE ( (lv_expression_1_0=
				// ruleExpression ) ) otherlv_2= OF ( (lv_case_3_0= ruleCase_Selection ) )+ (
				// (lv_else_4_0= ruleELSE_Clause ) )? otherlv_5= END_CASE
				{
					otherlv_0 = (Token) match(input, CASE, FOLLOW_19);

					newLeafNode(otherlv_0, grammarAccess.getCase_StmtAccess().getCASEKeyword_0());

					// InternalStructuredTextParser.g:942:3: ( (lv_expression_1_0= ruleExpression )
					// )
					// InternalStructuredTextParser.g:943:4: (lv_expression_1_0= ruleExpression )
					{
						// InternalStructuredTextParser.g:943:4: (lv_expression_1_0= ruleExpression )
						// InternalStructuredTextParser.g:944:5: lv_expression_1_0= ruleExpression
						{

							newCompositeNode(
									grammarAccess.getCase_StmtAccess().getExpressionExpressionParserRuleCall_1_0());

							pushFollow(FOLLOW_26);
							lv_expression_1_0 = ruleExpression();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getCase_StmtRule());
							}
							set(current, "expression", lv_expression_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_2 = (Token) match(input, OF, FOLLOW_13);

					newLeafNode(otherlv_2, grammarAccess.getCase_StmtAccess().getOFKeyword_2());

					// InternalStructuredTextParser.g:965:3: ( (lv_case_3_0= ruleCase_Selection ) )+
					int cnt15 = 0;
					loop15: do {
						int alt15 = 2;
						int LA15_0 = input.LA(1);

						if (((LA15_0 >= LDATE_AND_TIME && LA15_0 <= TIME_OF_DAY) || LA15_0 == WSTRING
								|| LA15_0 == STRING || (LA15_0 >= FALSE && LA15_0 <= LTIME)
								|| (LA15_0 >= UDINT && LA15_0 <= ULINT) || (LA15_0 >= USINT && LA15_0 <= WCHAR)
								|| LA15_0 == BOOL || (LA15_0 >= CHAR && LA15_0 <= DINT)
								|| (LA15_0 >= LINT && LA15_0 <= SINT) || (LA15_0 >= TIME && LA15_0 <= UINT)
								|| (LA15_0 >= INT && LA15_0 <= LDT) || LA15_0 == TOD || LA15_0 == DT
								|| (LA15_0 >= LD && LA15_0 <= LT) || LA15_0 == PlusSign || LA15_0 == HyphenMinus
								|| LA15_0 == T || LA15_0 == D_1
								|| (LA15_0 >= RULE_BINARY_INT && LA15_0 <= RULE_UNSIGNED_INT)
								|| LA15_0 == RULE_S_BYTE_CHAR_STR || LA15_0 == RULE_D_BYTE_CHAR_STR)) {
							alt15 = 1;
						}

						switch (alt15) {
						case 1:
						// InternalStructuredTextParser.g:966:4: (lv_case_3_0= ruleCase_Selection )
						{
							// InternalStructuredTextParser.g:966:4: (lv_case_3_0= ruleCase_Selection )
							// InternalStructuredTextParser.g:967:5: lv_case_3_0= ruleCase_Selection
							{

								newCompositeNode(
										grammarAccess.getCase_StmtAccess().getCaseCase_SelectionParserRuleCall_3_0());

								pushFollow(FOLLOW_27);
								lv_case_3_0 = ruleCase_Selection();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getCase_StmtRule());
								}
								add(current, "case", lv_case_3_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Case_Selection");
								afterParserOrEnumRuleCall();

							}

						}
							break;

						default:
							if (cnt15 >= 1)
								break loop15;
							EarlyExitException eee = new EarlyExitException(15, input);
							throw eee;
						}
						cnt15++;
					} while (true);

					// InternalStructuredTextParser.g:984:3: ( (lv_else_4_0= ruleELSE_Clause ) )?
					int alt16 = 2;
					int LA16_0 = input.LA(1);

					if ((LA16_0 == ELSE)) {
						alt16 = 1;
					}
					switch (alt16) {
					case 1:
					// InternalStructuredTextParser.g:985:4: (lv_else_4_0= ruleELSE_Clause )
					{
						// InternalStructuredTextParser.g:985:4: (lv_else_4_0= ruleELSE_Clause )
						// InternalStructuredTextParser.g:986:5: lv_else_4_0= ruleELSE_Clause
						{

							newCompositeNode(grammarAccess.getCase_StmtAccess().getElseELSE_ClauseParserRuleCall_4_0());

							pushFollow(FOLLOW_28);
							lv_else_4_0 = ruleELSE_Clause();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getCase_StmtRule());
							}
							set(current, "else", lv_else_4_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ELSE_Clause");
							afterParserOrEnumRuleCall();

						}

					}
						break;

					}

					otherlv_5 = (Token) match(input, END_CASE, FOLLOW_2);

					newLeafNode(otherlv_5, grammarAccess.getCase_StmtAccess().getEND_CASEKeyword_5());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleCase_Stmt"

	// $ANTLR start "entryRuleCase_Selection"
	// InternalStructuredTextParser.g:1011:1: entryRuleCase_Selection returns
	// [EObject current=null] : iv_ruleCase_Selection= ruleCase_Selection EOF ;
	public final EObject entryRuleCase_Selection() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleCase_Selection = null;

		try {
			// InternalStructuredTextParser.g:1011:55: (iv_ruleCase_Selection=
			// ruleCase_Selection EOF )
			// InternalStructuredTextParser.g:1012:2: iv_ruleCase_Selection=
			// ruleCase_Selection EOF
			{
				newCompositeNode(grammarAccess.getCase_SelectionRule());
				pushFollow(FOLLOW_1);
				iv_ruleCase_Selection = ruleCase_Selection();

				state._fsp--;

				current = iv_ruleCase_Selection;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleCase_Selection"

	// $ANTLR start "ruleCase_Selection"
	// InternalStructuredTextParser.g:1018:1: ruleCase_Selection returns [EObject
	// current=null] : ( ( (lv_case_0_0= ruleConstant ) ) (otherlv_1= Comma (
	// (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon ( (lv_statements_4_0=
	// ruleStmt_List ) ) ) ;
	public final EObject ruleCase_Selection() throws RecognitionException {
		EObject current = null;

		Token otherlv_1 = null;
		Token otherlv_3 = null;
		EObject lv_case_0_0 = null;

		EObject lv_case_2_0 = null;

		EObject lv_statements_4_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1024:2: ( ( ( (lv_case_0_0= ruleConstant ) )
			// (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon (
			// (lv_statements_4_0= ruleStmt_List ) ) ) )
			// InternalStructuredTextParser.g:1025:2: ( ( (lv_case_0_0= ruleConstant ) )
			// (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon (
			// (lv_statements_4_0= ruleStmt_List ) ) )
			{
				// InternalStructuredTextParser.g:1025:2: ( ( (lv_case_0_0= ruleConstant ) )
				// (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon (
				// (lv_statements_4_0= ruleStmt_List ) ) )
				// InternalStructuredTextParser.g:1026:3: ( (lv_case_0_0= ruleConstant ) )
				// (otherlv_1= Comma ( (lv_case_2_0= ruleConstant ) ) )* otherlv_3= Colon (
				// (lv_statements_4_0= ruleStmt_List ) )
				{
					// InternalStructuredTextParser.g:1026:3: ( (lv_case_0_0= ruleConstant ) )
					// InternalStructuredTextParser.g:1027:4: (lv_case_0_0= ruleConstant )
					{
						// InternalStructuredTextParser.g:1027:4: (lv_case_0_0= ruleConstant )
						// InternalStructuredTextParser.g:1028:5: lv_case_0_0= ruleConstant
						{

							newCompositeNode(
									grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_0_0());

							pushFollow(FOLLOW_29);
							lv_case_0_0 = ruleConstant();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getCase_SelectionRule());
							}
							add(current, "case", lv_case_0_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Constant");
							afterParserOrEnumRuleCall();

						}

					}

					// InternalStructuredTextParser.g:1045:3: (otherlv_1= Comma ( (lv_case_2_0=
					// ruleConstant ) ) )*
					loop17: do {
						int alt17 = 2;
						int LA17_0 = input.LA(1);

						if ((LA17_0 == Comma)) {
							alt17 = 1;
						}

						switch (alt17) {
						case 1:
						// InternalStructuredTextParser.g:1046:4: otherlv_1= Comma ( (lv_case_2_0=
						// ruleConstant ) )
						{
							otherlv_1 = (Token) match(input, Comma, FOLLOW_13);

							newLeafNode(otherlv_1, grammarAccess.getCase_SelectionAccess().getCommaKeyword_1_0());

							// InternalStructuredTextParser.g:1050:4: ( (lv_case_2_0= ruleConstant ) )
							// InternalStructuredTextParser.g:1051:5: (lv_case_2_0= ruleConstant )
							{
								// InternalStructuredTextParser.g:1051:5: (lv_case_2_0= ruleConstant )
								// InternalStructuredTextParser.g:1052:6: lv_case_2_0= ruleConstant
								{

									newCompositeNode(grammarAccess.getCase_SelectionAccess()
											.getCaseConstantParserRuleCall_1_1_0());

									pushFollow(FOLLOW_29);
									lv_case_2_0 = ruleConstant();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getCase_SelectionRule());
									}
									add(current, "case", lv_case_2_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Constant");
									afterParserOrEnumRuleCall();

								}

							}

						}
							break;

						default:
							break loop17;
						}
					} while (true);

					otherlv_3 = (Token) match(input, Colon, FOLLOW_5);

					newLeafNode(otherlv_3, grammarAccess.getCase_SelectionAccess().getColonKeyword_2());

					// InternalStructuredTextParser.g:1074:3: ( (lv_statements_4_0= ruleStmt_List )
					// )
					// InternalStructuredTextParser.g:1075:4: (lv_statements_4_0= ruleStmt_List )
					{
						// InternalStructuredTextParser.g:1075:4: (lv_statements_4_0= ruleStmt_List )
						// InternalStructuredTextParser.g:1076:5: lv_statements_4_0= ruleStmt_List
						{

							newCompositeNode(
									grammarAccess.getCase_SelectionAccess().getStatementsStmt_ListParserRuleCall_3_0());

							pushFollow(FOLLOW_2);
							lv_statements_4_0 = ruleStmt_List();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getCase_SelectionRule());
							}
							set(current, "statements", lv_statements_4_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleCase_Selection"

	// $ANTLR start "entryRuleIteration_Stmt"
	// InternalStructuredTextParser.g:1097:1: entryRuleIteration_Stmt returns
	// [EObject current=null] : iv_ruleIteration_Stmt= ruleIteration_Stmt EOF ;
	public final EObject entryRuleIteration_Stmt() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleIteration_Stmt = null;

		try {
			// InternalStructuredTextParser.g:1097:55: (iv_ruleIteration_Stmt=
			// ruleIteration_Stmt EOF )
			// InternalStructuredTextParser.g:1098:2: iv_ruleIteration_Stmt=
			// ruleIteration_Stmt EOF
			{
				newCompositeNode(grammarAccess.getIteration_StmtRule());
				pushFollow(FOLLOW_1);
				iv_ruleIteration_Stmt = ruleIteration_Stmt();

				state._fsp--;

				current = iv_ruleIteration_Stmt;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleIteration_Stmt"

	// $ANTLR start "ruleIteration_Stmt"
	// InternalStructuredTextParser.g:1104:1: ruleIteration_Stmt returns [EObject
	// current=null] : (this_For_Stmt_0= ruleFor_Stmt | this_While_Stmt_1=
	// ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | ( () otherlv_4= EXIT )
	// | ( () otherlv_6= CONTINUE ) ) ;
	public final EObject ruleIteration_Stmt() throws RecognitionException {
		EObject current = null;

		Token otherlv_4 = null;
		Token otherlv_6 = null;
		EObject this_For_Stmt_0 = null;

		EObject this_While_Stmt_1 = null;

		EObject this_Repeat_Stmt_2 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1110:2: ( (this_For_Stmt_0= ruleFor_Stmt |
			// this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | (
			// () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) ) )
			// InternalStructuredTextParser.g:1111:2: (this_For_Stmt_0= ruleFor_Stmt |
			// this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | (
			// () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) )
			{
				// InternalStructuredTextParser.g:1111:2: (this_For_Stmt_0= ruleFor_Stmt |
				// this_While_Stmt_1= ruleWhile_Stmt | this_Repeat_Stmt_2= ruleRepeat_Stmt | (
				// () otherlv_4= EXIT ) | ( () otherlv_6= CONTINUE ) )
				int alt18 = 5;
				switch (input.LA(1)) {
				case FOR: {
					alt18 = 1;
				}
					break;
				case WHILE: {
					alt18 = 2;
				}
					break;
				case REPEAT: {
					alt18 = 3;
				}
					break;
				case EXIT: {
					alt18 = 4;
				}
					break;
				case CONTINUE: {
					alt18 = 5;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 18, 0, input);

					throw nvae;
				}

				switch (alt18) {
				case 1:
				// InternalStructuredTextParser.g:1112:3: this_For_Stmt_0= ruleFor_Stmt
				{

					newCompositeNode(grammarAccess.getIteration_StmtAccess().getFor_StmtParserRuleCall_0());

					pushFollow(FOLLOW_2);
					this_For_Stmt_0 = ruleFor_Stmt();

					state._fsp--;

					current = this_For_Stmt_0;
					afterParserOrEnumRuleCall();

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:1121:3: this_While_Stmt_1= ruleWhile_Stmt
				{

					newCompositeNode(grammarAccess.getIteration_StmtAccess().getWhile_StmtParserRuleCall_1());

					pushFollow(FOLLOW_2);
					this_While_Stmt_1 = ruleWhile_Stmt();

					state._fsp--;

					current = this_While_Stmt_1;
					afterParserOrEnumRuleCall();

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:1130:3: this_Repeat_Stmt_2= ruleRepeat_Stmt
				{

					newCompositeNode(grammarAccess.getIteration_StmtAccess().getRepeat_StmtParserRuleCall_2());

					pushFollow(FOLLOW_2);
					this_Repeat_Stmt_2 = ruleRepeat_Stmt();

					state._fsp--;

					current = this_Repeat_Stmt_2;
					afterParserOrEnumRuleCall();

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:1139:3: ( () otherlv_4= EXIT )
				{
					// InternalStructuredTextParser.g:1139:3: ( () otherlv_4= EXIT )
					// InternalStructuredTextParser.g:1140:4: () otherlv_4= EXIT
					{
						// InternalStructuredTextParser.g:1140:4: ()
						// InternalStructuredTextParser.g:1141:5:
						{

							current = forceCreateModelElement(
									grammarAccess.getIteration_StmtAccess().getExitStatementAction_3_0(), current);

						}

						otherlv_4 = (Token) match(input, EXIT, FOLLOW_2);

						newLeafNode(otherlv_4, grammarAccess.getIteration_StmtAccess().getEXITKeyword_3_1());

					}

				}
					break;
				case 5:
				// InternalStructuredTextParser.g:1153:3: ( () otherlv_6= CONTINUE )
				{
					// InternalStructuredTextParser.g:1153:3: ( () otherlv_6= CONTINUE )
					// InternalStructuredTextParser.g:1154:4: () otherlv_6= CONTINUE
					{
						// InternalStructuredTextParser.g:1154:4: ()
						// InternalStructuredTextParser.g:1155:5:
						{

							current = forceCreateModelElement(
									grammarAccess.getIteration_StmtAccess().getContinueStatementAction_4_0(), current);

						}

						otherlv_6 = (Token) match(input, CONTINUE, FOLLOW_2);

						newLeafNode(otherlv_6, grammarAccess.getIteration_StmtAccess().getCONTINUEKeyword_4_1());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleIteration_Stmt"

	// $ANTLR start "entryRuleFor_Stmt"
	// InternalStructuredTextParser.g:1170:1: entryRuleFor_Stmt returns [EObject
	// current=null] : iv_ruleFor_Stmt= ruleFor_Stmt EOF ;
	public final EObject entryRuleFor_Stmt() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleFor_Stmt = null;

		try {
			// InternalStructuredTextParser.g:1170:49: (iv_ruleFor_Stmt= ruleFor_Stmt EOF )
			// InternalStructuredTextParser.g:1171:2: iv_ruleFor_Stmt= ruleFor_Stmt EOF
			{
				newCompositeNode(grammarAccess.getFor_StmtRule());
				pushFollow(FOLLOW_1);
				iv_ruleFor_Stmt = ruleFor_Stmt();

				state._fsp--;

				current = iv_ruleFor_Stmt;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleFor_Stmt"

	// $ANTLR start "ruleFor_Stmt"
	// InternalStructuredTextParser.g:1177:1: ruleFor_Stmt returns [EObject
	// current=null] : (otherlv_0= FOR ( (lv_variable_1_0= ruleVariable_Primary ) )
	// otherlv_2= ColonEqualsSign ( (lv_from_3_0= ruleExpression ) ) otherlv_4= TO (
	// (lv_to_5_0= ruleExpression ) ) (otherlv_6= BY ( (lv_by_7_0= ruleExpression )
	// ) )? otherlv_8= DO ( (lv_statements_9_0= ruleStmt_List ) ) otherlv_10=
	// END_FOR ) ;
	public final EObject ruleFor_Stmt() throws RecognitionException {
		EObject current = null;

		Token otherlv_0 = null;
		Token otherlv_2 = null;
		Token otherlv_4 = null;
		Token otherlv_6 = null;
		Token otherlv_8 = null;
		Token otherlv_10 = null;
		EObject lv_variable_1_0 = null;

		EObject lv_from_3_0 = null;

		EObject lv_to_5_0 = null;

		EObject lv_by_7_0 = null;

		EObject lv_statements_9_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1183:2: ( (otherlv_0= FOR ( (lv_variable_1_0=
			// ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0=
			// ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6=
			// BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0=
			// ruleStmt_List ) ) otherlv_10= END_FOR ) )
			// InternalStructuredTextParser.g:1184:2: (otherlv_0= FOR ( (lv_variable_1_0=
			// ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0=
			// ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6=
			// BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0=
			// ruleStmt_List ) ) otherlv_10= END_FOR )
			{
				// InternalStructuredTextParser.g:1184:2: (otherlv_0= FOR ( (lv_variable_1_0=
				// ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0=
				// ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6=
				// BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0=
				// ruleStmt_List ) ) otherlv_10= END_FOR )
				// InternalStructuredTextParser.g:1185:3: otherlv_0= FOR ( (lv_variable_1_0=
				// ruleVariable_Primary ) ) otherlv_2= ColonEqualsSign ( (lv_from_3_0=
				// ruleExpression ) ) otherlv_4= TO ( (lv_to_5_0= ruleExpression ) ) (otherlv_6=
				// BY ( (lv_by_7_0= ruleExpression ) ) )? otherlv_8= DO ( (lv_statements_9_0=
				// ruleStmt_List ) ) otherlv_10= END_FOR
				{
					otherlv_0 = (Token) match(input, FOR, FOLLOW_15);

					newLeafNode(otherlv_0, grammarAccess.getFor_StmtAccess().getFORKeyword_0());

					// InternalStructuredTextParser.g:1189:3: ( (lv_variable_1_0=
					// ruleVariable_Primary ) )
					// InternalStructuredTextParser.g:1190:4: (lv_variable_1_0= ruleVariable_Primary
					// )
					{
						// InternalStructuredTextParser.g:1190:4: (lv_variable_1_0= ruleVariable_Primary
						// )
						// InternalStructuredTextParser.g:1191:5: lv_variable_1_0= ruleVariable_Primary
						{

							newCompositeNode(
									grammarAccess.getFor_StmtAccess().getVariableVariable_PrimaryParserRuleCall_1_0());

							pushFollow(FOLLOW_18);
							lv_variable_1_0 = ruleVariable_Primary();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getFor_StmtRule());
							}
							set(current, "variable", lv_variable_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Variable_Primary");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_2 = (Token) match(input, ColonEqualsSign, FOLLOW_19);

					newLeafNode(otherlv_2, grammarAccess.getFor_StmtAccess().getColonEqualsSignKeyword_2());

					// InternalStructuredTextParser.g:1212:3: ( (lv_from_3_0= ruleExpression ) )
					// InternalStructuredTextParser.g:1213:4: (lv_from_3_0= ruleExpression )
					{
						// InternalStructuredTextParser.g:1213:4: (lv_from_3_0= ruleExpression )
						// InternalStructuredTextParser.g:1214:5: lv_from_3_0= ruleExpression
						{

							newCompositeNode(grammarAccess.getFor_StmtAccess().getFromExpressionParserRuleCall_3_0());

							pushFollow(FOLLOW_30);
							lv_from_3_0 = ruleExpression();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getFor_StmtRule());
							}
							set(current, "from", lv_from_3_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_4 = (Token) match(input, TO, FOLLOW_19);

					newLeafNode(otherlv_4, grammarAccess.getFor_StmtAccess().getTOKeyword_4());

					// InternalStructuredTextParser.g:1235:3: ( (lv_to_5_0= ruleExpression ) )
					// InternalStructuredTextParser.g:1236:4: (lv_to_5_0= ruleExpression )
					{
						// InternalStructuredTextParser.g:1236:4: (lv_to_5_0= ruleExpression )
						// InternalStructuredTextParser.g:1237:5: lv_to_5_0= ruleExpression
						{

							newCompositeNode(grammarAccess.getFor_StmtAccess().getToExpressionParserRuleCall_5_0());

							pushFollow(FOLLOW_31);
							lv_to_5_0 = ruleExpression();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getFor_StmtRule());
							}
							set(current, "to", lv_to_5_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
							afterParserOrEnumRuleCall();

						}

					}

					// InternalStructuredTextParser.g:1254:3: (otherlv_6= BY ( (lv_by_7_0=
					// ruleExpression ) ) )?
					int alt19 = 2;
					int LA19_0 = input.LA(1);

					if ((LA19_0 == BY)) {
						alt19 = 1;
					}
					switch (alt19) {
					case 1:
					// InternalStructuredTextParser.g:1255:4: otherlv_6= BY ( (lv_by_7_0=
					// ruleExpression ) )
					{
						otherlv_6 = (Token) match(input, BY, FOLLOW_19);

						newLeafNode(otherlv_6, grammarAccess.getFor_StmtAccess().getBYKeyword_6_0());

						// InternalStructuredTextParser.g:1259:4: ( (lv_by_7_0= ruleExpression ) )
						// InternalStructuredTextParser.g:1260:5: (lv_by_7_0= ruleExpression )
						{
							// InternalStructuredTextParser.g:1260:5: (lv_by_7_0= ruleExpression )
							// InternalStructuredTextParser.g:1261:6: lv_by_7_0= ruleExpression
							{

								newCompositeNode(
										grammarAccess.getFor_StmtAccess().getByExpressionParserRuleCall_6_1_0());

								pushFollow(FOLLOW_32);
								lv_by_7_0 = ruleExpression();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getFor_StmtRule());
								}
								set(current, "by", lv_by_7_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
								afterParserOrEnumRuleCall();

							}

						}

					}
						break;

					}

					otherlv_8 = (Token) match(input, DO, FOLLOW_33);

					newLeafNode(otherlv_8, grammarAccess.getFor_StmtAccess().getDOKeyword_7());

					// InternalStructuredTextParser.g:1283:3: ( (lv_statements_9_0= ruleStmt_List )
					// )
					// InternalStructuredTextParser.g:1284:4: (lv_statements_9_0= ruleStmt_List )
					{
						// InternalStructuredTextParser.g:1284:4: (lv_statements_9_0= ruleStmt_List )
						// InternalStructuredTextParser.g:1285:5: lv_statements_9_0= ruleStmt_List
						{

							newCompositeNode(
									grammarAccess.getFor_StmtAccess().getStatementsStmt_ListParserRuleCall_8_0());

							pushFollow(FOLLOW_34);
							lv_statements_9_0 = ruleStmt_List();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getFor_StmtRule());
							}
							set(current, "statements", lv_statements_9_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_10 = (Token) match(input, END_FOR, FOLLOW_2);

					newLeafNode(otherlv_10, grammarAccess.getFor_StmtAccess().getEND_FORKeyword_9());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleFor_Stmt"

	// $ANTLR start "entryRuleWhile_Stmt"
	// InternalStructuredTextParser.g:1310:1: entryRuleWhile_Stmt returns [EObject
	// current=null] : iv_ruleWhile_Stmt= ruleWhile_Stmt EOF ;
	public final EObject entryRuleWhile_Stmt() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleWhile_Stmt = null;

		try {
			// InternalStructuredTextParser.g:1310:51: (iv_ruleWhile_Stmt= ruleWhile_Stmt
			// EOF )
			// InternalStructuredTextParser.g:1311:2: iv_ruleWhile_Stmt= ruleWhile_Stmt EOF
			{
				newCompositeNode(grammarAccess.getWhile_StmtRule());
				pushFollow(FOLLOW_1);
				iv_ruleWhile_Stmt = ruleWhile_Stmt();

				state._fsp--;

				current = iv_ruleWhile_Stmt;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleWhile_Stmt"

	// $ANTLR start "ruleWhile_Stmt"
	// InternalStructuredTextParser.g:1317:1: ruleWhile_Stmt returns [EObject
	// current=null] : (otherlv_0= WHILE ( (lv_expression_1_0= ruleExpression ) )
	// otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) ) otherlv_4= END_WHILE )
	// ;
	public final EObject ruleWhile_Stmt() throws RecognitionException {
		EObject current = null;

		Token otherlv_0 = null;
		Token otherlv_2 = null;
		Token otherlv_4 = null;
		EObject lv_expression_1_0 = null;

		EObject lv_statements_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1323:2: ( (otherlv_0= WHILE (
			// (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0=
			// ruleStmt_List ) ) otherlv_4= END_WHILE ) )
			// InternalStructuredTextParser.g:1324:2: (otherlv_0= WHILE (
			// (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0=
			// ruleStmt_List ) ) otherlv_4= END_WHILE )
			{
				// InternalStructuredTextParser.g:1324:2: (otherlv_0= WHILE (
				// (lv_expression_1_0= ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0=
				// ruleStmt_List ) ) otherlv_4= END_WHILE )
				// InternalStructuredTextParser.g:1325:3: otherlv_0= WHILE ( (lv_expression_1_0=
				// ruleExpression ) ) otherlv_2= DO ( (lv_statements_3_0= ruleStmt_List ) )
				// otherlv_4= END_WHILE
				{
					otherlv_0 = (Token) match(input, WHILE, FOLLOW_19);

					newLeafNode(otherlv_0, grammarAccess.getWhile_StmtAccess().getWHILEKeyword_0());

					// InternalStructuredTextParser.g:1329:3: ( (lv_expression_1_0= ruleExpression )
					// )
					// InternalStructuredTextParser.g:1330:4: (lv_expression_1_0= ruleExpression )
					{
						// InternalStructuredTextParser.g:1330:4: (lv_expression_1_0= ruleExpression )
						// InternalStructuredTextParser.g:1331:5: lv_expression_1_0= ruleExpression
						{

							newCompositeNode(
									grammarAccess.getWhile_StmtAccess().getExpressionExpressionParserRuleCall_1_0());

							pushFollow(FOLLOW_32);
							lv_expression_1_0 = ruleExpression();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getWhile_StmtRule());
							}
							set(current, "expression", lv_expression_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_2 = (Token) match(input, DO, FOLLOW_35);

					newLeafNode(otherlv_2, grammarAccess.getWhile_StmtAccess().getDOKeyword_2());

					// InternalStructuredTextParser.g:1352:3: ( (lv_statements_3_0= ruleStmt_List )
					// )
					// InternalStructuredTextParser.g:1353:4: (lv_statements_3_0= ruleStmt_List )
					{
						// InternalStructuredTextParser.g:1353:4: (lv_statements_3_0= ruleStmt_List )
						// InternalStructuredTextParser.g:1354:5: lv_statements_3_0= ruleStmt_List
						{

							newCompositeNode(
									grammarAccess.getWhile_StmtAccess().getStatementsStmt_ListParserRuleCall_3_0());

							pushFollow(FOLLOW_36);
							lv_statements_3_0 = ruleStmt_List();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getWhile_StmtRule());
							}
							set(current, "statements", lv_statements_3_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_4 = (Token) match(input, END_WHILE, FOLLOW_2);

					newLeafNode(otherlv_4, grammarAccess.getWhile_StmtAccess().getEND_WHILEKeyword_4());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleWhile_Stmt"

	// $ANTLR start "entryRuleRepeat_Stmt"
	// InternalStructuredTextParser.g:1379:1: entryRuleRepeat_Stmt returns [EObject
	// current=null] : iv_ruleRepeat_Stmt= ruleRepeat_Stmt EOF ;
	public final EObject entryRuleRepeat_Stmt() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleRepeat_Stmt = null;

		try {
			// InternalStructuredTextParser.g:1379:52: (iv_ruleRepeat_Stmt= ruleRepeat_Stmt
			// EOF )
			// InternalStructuredTextParser.g:1380:2: iv_ruleRepeat_Stmt= ruleRepeat_Stmt
			// EOF
			{
				newCompositeNode(grammarAccess.getRepeat_StmtRule());
				pushFollow(FOLLOW_1);
				iv_ruleRepeat_Stmt = ruleRepeat_Stmt();

				state._fsp--;

				current = iv_ruleRepeat_Stmt;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleRepeat_Stmt"

	// $ANTLR start "ruleRepeat_Stmt"
	// InternalStructuredTextParser.g:1386:1: ruleRepeat_Stmt returns [EObject
	// current=null] : (otherlv_0= REPEAT ( (lv_statements_1_0= ruleStmt_List ) )
	// otherlv_2= UNTIL ( (lv_expression_3_0= ruleExpression ) ) otherlv_4=
	// END_REPEAT ) ;
	public final EObject ruleRepeat_Stmt() throws RecognitionException {
		EObject current = null;

		Token otherlv_0 = null;
		Token otherlv_2 = null;
		Token otherlv_4 = null;
		EObject lv_statements_1_0 = null;

		EObject lv_expression_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1392:2: ( (otherlv_0= REPEAT (
			// (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0=
			// ruleExpression ) ) otherlv_4= END_REPEAT ) )
			// InternalStructuredTextParser.g:1393:2: (otherlv_0= REPEAT (
			// (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0=
			// ruleExpression ) ) otherlv_4= END_REPEAT )
			{
				// InternalStructuredTextParser.g:1393:2: (otherlv_0= REPEAT (
				// (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0=
				// ruleExpression ) ) otherlv_4= END_REPEAT )
				// InternalStructuredTextParser.g:1394:3: otherlv_0= REPEAT (
				// (lv_statements_1_0= ruleStmt_List ) ) otherlv_2= UNTIL ( (lv_expression_3_0=
				// ruleExpression ) ) otherlv_4= END_REPEAT
				{
					otherlv_0 = (Token) match(input, REPEAT, FOLLOW_37);

					newLeafNode(otherlv_0, grammarAccess.getRepeat_StmtAccess().getREPEATKeyword_0());

					// InternalStructuredTextParser.g:1398:3: ( (lv_statements_1_0= ruleStmt_List )
					// )
					// InternalStructuredTextParser.g:1399:4: (lv_statements_1_0= ruleStmt_List )
					{
						// InternalStructuredTextParser.g:1399:4: (lv_statements_1_0= ruleStmt_List )
						// InternalStructuredTextParser.g:1400:5: lv_statements_1_0= ruleStmt_List
						{

							newCompositeNode(
									grammarAccess.getRepeat_StmtAccess().getStatementsStmt_ListParserRuleCall_1_0());

							pushFollow(FOLLOW_38);
							lv_statements_1_0 = ruleStmt_List();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getRepeat_StmtRule());
							}
							set(current, "statements", lv_statements_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Stmt_List");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_2 = (Token) match(input, UNTIL, FOLLOW_19);

					newLeafNode(otherlv_2, grammarAccess.getRepeat_StmtAccess().getUNTILKeyword_2());

					// InternalStructuredTextParser.g:1421:3: ( (lv_expression_3_0= ruleExpression )
					// )
					// InternalStructuredTextParser.g:1422:4: (lv_expression_3_0= ruleExpression )
					{
						// InternalStructuredTextParser.g:1422:4: (lv_expression_3_0= ruleExpression )
						// InternalStructuredTextParser.g:1423:5: lv_expression_3_0= ruleExpression
						{

							newCompositeNode(
									grammarAccess.getRepeat_StmtAccess().getExpressionExpressionParserRuleCall_3_0());

							pushFollow(FOLLOW_39);
							lv_expression_3_0 = ruleExpression();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getRepeat_StmtRule());
							}
							set(current, "expression", lv_expression_3_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_4 = (Token) match(input, END_REPEAT, FOLLOW_2);

					newLeafNode(otherlv_4, grammarAccess.getRepeat_StmtAccess().getEND_REPEATKeyword_4());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleRepeat_Stmt"

	// $ANTLR start "entryRuleExpression"
	// InternalStructuredTextParser.g:1448:1: entryRuleExpression returns [EObject
	// current=null] : iv_ruleExpression= ruleExpression EOF ;
	public final EObject entryRuleExpression() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleExpression = null;

		try {
			// InternalStructuredTextParser.g:1448:51: (iv_ruleExpression= ruleExpression
			// EOF )
			// InternalStructuredTextParser.g:1449:2: iv_ruleExpression= ruleExpression EOF
			{
				newCompositeNode(grammarAccess.getExpressionRule());
				pushFollow(FOLLOW_1);
				iv_ruleExpression = ruleExpression();

				state._fsp--;

				current = iv_ruleExpression;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleExpression"

	// $ANTLR start "ruleExpression"
	// InternalStructuredTextParser.g:1455:1: ruleExpression returns [EObject
	// current=null] : this_Or_Expression_0= ruleOr_Expression ;
	public final EObject ruleExpression() throws RecognitionException {
		EObject current = null;

		EObject this_Or_Expression_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1461:2: (this_Or_Expression_0=
			// ruleOr_Expression )
			// InternalStructuredTextParser.g:1462:2: this_Or_Expression_0=
			// ruleOr_Expression
			{

				newCompositeNode(grammarAccess.getExpressionAccess().getOr_ExpressionParserRuleCall());

				pushFollow(FOLLOW_2);
				this_Or_Expression_0 = ruleOr_Expression();

				state._fsp--;

				current = this_Or_Expression_0;
				afterParserOrEnumRuleCall();

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleExpression"

	// $ANTLR start "entryRuleOr_Expression"
	// InternalStructuredTextParser.g:1473:1: entryRuleOr_Expression returns
	// [EObject current=null] : iv_ruleOr_Expression= ruleOr_Expression EOF ;
	public final EObject entryRuleOr_Expression() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleOr_Expression = null;

		try {
			// InternalStructuredTextParser.g:1473:54: (iv_ruleOr_Expression=
			// ruleOr_Expression EOF )
			// InternalStructuredTextParser.g:1474:2: iv_ruleOr_Expression=
			// ruleOr_Expression EOF
			{
				newCompositeNode(grammarAccess.getOr_ExpressionRule());
				pushFollow(FOLLOW_1);
				iv_ruleOr_Expression = ruleOr_Expression();

				state._fsp--;

				current = iv_ruleOr_Expression;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleOr_Expression"

	// $ANTLR start "ruleOr_Expression"
	// InternalStructuredTextParser.g:1480:1: ruleOr_Expression returns [EObject
	// current=null] : (this_Xor_Expr_0= ruleXor_Expr ( () ( (lv_operator_2_0=
	// ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* ) ;
	public final EObject ruleOr_Expression() throws RecognitionException {
		EObject current = null;

		EObject this_Xor_Expr_0 = null;

		Enumerator lv_operator_2_0 = null;

		EObject lv_right_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1486:2: ( (this_Xor_Expr_0= ruleXor_Expr ( ()
			// ( (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )*
			// ) )
			// InternalStructuredTextParser.g:1487:2: (this_Xor_Expr_0= ruleXor_Expr ( () (
			// (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* )
			{
				// InternalStructuredTextParser.g:1487:2: (this_Xor_Expr_0= ruleXor_Expr ( () (
				// (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )* )
				// InternalStructuredTextParser.g:1488:3: this_Xor_Expr_0= ruleXor_Expr ( () (
				// (lv_operator_2_0= ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )*
				{

					newCompositeNode(grammarAccess.getOr_ExpressionAccess().getXor_ExprParserRuleCall_0());

					pushFollow(FOLLOW_40);
					this_Xor_Expr_0 = ruleXor_Expr();

					state._fsp--;

					current = this_Xor_Expr_0;
					afterParserOrEnumRuleCall();

					// InternalStructuredTextParser.g:1496:3: ( () ( (lv_operator_2_0=
					// ruleOr_Operator ) ) ( (lv_right_3_0= ruleXor_Expr ) ) )*
					loop20: do {
						int alt20 = 2;
						int LA20_0 = input.LA(1);

						if ((LA20_0 == OR)) {
							alt20 = 1;
						}

						switch (alt20) {
						case 1:
						// InternalStructuredTextParser.g:1497:4: () ( (lv_operator_2_0= ruleOr_Operator
						// ) ) ( (lv_right_3_0= ruleXor_Expr ) )
						{
							// InternalStructuredTextParser.g:1497:4: ()
							// InternalStructuredTextParser.g:1498:5:
							{

								current = forceCreateModelElementAndSet(
										grammarAccess.getOr_ExpressionAccess().getBinaryExpressionLeftAction_1_0(),
										current);

							}

							// InternalStructuredTextParser.g:1504:4: ( (lv_operator_2_0= ruleOr_Operator )
							// )
							// InternalStructuredTextParser.g:1505:5: (lv_operator_2_0= ruleOr_Operator )
							{
								// InternalStructuredTextParser.g:1505:5: (lv_operator_2_0= ruleOr_Operator )
								// InternalStructuredTextParser.g:1506:6: lv_operator_2_0= ruleOr_Operator
								{

									newCompositeNode(grammarAccess.getOr_ExpressionAccess()
											.getOperatorOr_OperatorEnumRuleCall_1_1_0());

									pushFollow(FOLLOW_19);
									lv_operator_2_0 = ruleOr_Operator();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getOr_ExpressionRule());
									}
									set(current, "operator", lv_operator_2_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Or_Operator");
									afterParserOrEnumRuleCall();

								}

							}

							// InternalStructuredTextParser.g:1523:4: ( (lv_right_3_0= ruleXor_Expr ) )
							// InternalStructuredTextParser.g:1524:5: (lv_right_3_0= ruleXor_Expr )
							{
								// InternalStructuredTextParser.g:1524:5: (lv_right_3_0= ruleXor_Expr )
								// InternalStructuredTextParser.g:1525:6: lv_right_3_0= ruleXor_Expr
								{

									newCompositeNode(grammarAccess.getOr_ExpressionAccess()
											.getRightXor_ExprParserRuleCall_1_2_0());

									pushFollow(FOLLOW_40);
									lv_right_3_0 = ruleXor_Expr();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getOr_ExpressionRule());
									}
									set(current, "right", lv_right_3_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Xor_Expr");
									afterParserOrEnumRuleCall();

								}

							}

						}
							break;

						default:
							break loop20;
						}
					} while (true);

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleOr_Expression"

	// $ANTLR start "entryRuleXor_Expr"
	// InternalStructuredTextParser.g:1547:1: entryRuleXor_Expr returns [EObject
	// current=null] : iv_ruleXor_Expr= ruleXor_Expr EOF ;
	public final EObject entryRuleXor_Expr() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleXor_Expr = null;

		try {
			// InternalStructuredTextParser.g:1547:49: (iv_ruleXor_Expr= ruleXor_Expr EOF )
			// InternalStructuredTextParser.g:1548:2: iv_ruleXor_Expr= ruleXor_Expr EOF
			{
				newCompositeNode(grammarAccess.getXor_ExprRule());
				pushFollow(FOLLOW_1);
				iv_ruleXor_Expr = ruleXor_Expr();

				state._fsp--;

				current = iv_ruleXor_Expr;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleXor_Expr"

	// $ANTLR start "ruleXor_Expr"
	// InternalStructuredTextParser.g:1554:1: ruleXor_Expr returns [EObject
	// current=null] : (this_And_Expr_0= ruleAnd_Expr ( () ( (lv_operator_2_0=
	// ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* ) ;
	public final EObject ruleXor_Expr() throws RecognitionException {
		EObject current = null;

		EObject this_And_Expr_0 = null;

		Enumerator lv_operator_2_0 = null;

		EObject lv_right_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1560:2: ( (this_And_Expr_0= ruleAnd_Expr ( ()
			// ( (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )*
			// ) )
			// InternalStructuredTextParser.g:1561:2: (this_And_Expr_0= ruleAnd_Expr ( () (
			// (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* )
			{
				// InternalStructuredTextParser.g:1561:2: (this_And_Expr_0= ruleAnd_Expr ( () (
				// (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )* )
				// InternalStructuredTextParser.g:1562:3: this_And_Expr_0= ruleAnd_Expr ( () (
				// (lv_operator_2_0= ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )*
				{

					newCompositeNode(grammarAccess.getXor_ExprAccess().getAnd_ExprParserRuleCall_0());

					pushFollow(FOLLOW_41);
					this_And_Expr_0 = ruleAnd_Expr();

					state._fsp--;

					current = this_And_Expr_0;
					afterParserOrEnumRuleCall();

					// InternalStructuredTextParser.g:1570:3: ( () ( (lv_operator_2_0=
					// ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) ) )*
					loop21: do {
						int alt21 = 2;
						int LA21_0 = input.LA(1);

						if ((LA21_0 == XOR)) {
							alt21 = 1;
						}

						switch (alt21) {
						case 1:
						// InternalStructuredTextParser.g:1571:4: () ( (lv_operator_2_0=
						// ruleXor_Operator ) ) ( (lv_right_3_0= ruleAnd_Expr ) )
						{
							// InternalStructuredTextParser.g:1571:4: ()
							// InternalStructuredTextParser.g:1572:5:
							{

								current = forceCreateModelElementAndSet(
										grammarAccess.getXor_ExprAccess().getBinaryExpressionLeftAction_1_0(), current);

							}

							// InternalStructuredTextParser.g:1578:4: ( (lv_operator_2_0= ruleXor_Operator )
							// )
							// InternalStructuredTextParser.g:1579:5: (lv_operator_2_0= ruleXor_Operator )
							{
								// InternalStructuredTextParser.g:1579:5: (lv_operator_2_0= ruleXor_Operator )
								// InternalStructuredTextParser.g:1580:6: lv_operator_2_0= ruleXor_Operator
								{

									newCompositeNode(grammarAccess.getXor_ExprAccess()
											.getOperatorXor_OperatorEnumRuleCall_1_1_0());

									pushFollow(FOLLOW_19);
									lv_operator_2_0 = ruleXor_Operator();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getXor_ExprRule());
									}
									set(current, "operator", lv_operator_2_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Xor_Operator");
									afterParserOrEnumRuleCall();

								}

							}

							// InternalStructuredTextParser.g:1597:4: ( (lv_right_3_0= ruleAnd_Expr ) )
							// InternalStructuredTextParser.g:1598:5: (lv_right_3_0= ruleAnd_Expr )
							{
								// InternalStructuredTextParser.g:1598:5: (lv_right_3_0= ruleAnd_Expr )
								// InternalStructuredTextParser.g:1599:6: lv_right_3_0= ruleAnd_Expr
								{

									newCompositeNode(
											grammarAccess.getXor_ExprAccess().getRightAnd_ExprParserRuleCall_1_2_0());

									pushFollow(FOLLOW_41);
									lv_right_3_0 = ruleAnd_Expr();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getXor_ExprRule());
									}
									set(current, "right", lv_right_3_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.And_Expr");
									afterParserOrEnumRuleCall();

								}

							}

						}
							break;

						default:
							break loop21;
						}
					} while (true);

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleXor_Expr"

	// $ANTLR start "entryRuleAnd_Expr"
	// InternalStructuredTextParser.g:1621:1: entryRuleAnd_Expr returns [EObject
	// current=null] : iv_ruleAnd_Expr= ruleAnd_Expr EOF ;
	public final EObject entryRuleAnd_Expr() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleAnd_Expr = null;

		try {
			// InternalStructuredTextParser.g:1621:49: (iv_ruleAnd_Expr= ruleAnd_Expr EOF )
			// InternalStructuredTextParser.g:1622:2: iv_ruleAnd_Expr= ruleAnd_Expr EOF
			{
				newCompositeNode(grammarAccess.getAnd_ExprRule());
				pushFollow(FOLLOW_1);
				iv_ruleAnd_Expr = ruleAnd_Expr();

				state._fsp--;

				current = iv_ruleAnd_Expr;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleAnd_Expr"

	// $ANTLR start "ruleAnd_Expr"
	// InternalStructuredTextParser.g:1628:1: ruleAnd_Expr returns [EObject
	// current=null] : (this_Compare_Expr_0= ruleCompare_Expr ( () (
	// (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) )
	// )* ) ;
	public final EObject ruleAnd_Expr() throws RecognitionException {
		EObject current = null;

		EObject this_Compare_Expr_0 = null;

		Enumerator lv_operator_2_0 = null;

		EObject lv_right_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1634:2: ( (this_Compare_Expr_0=
			// ruleCompare_Expr ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) (
			// (lv_right_3_0= ruleCompare_Expr ) ) )* ) )
			// InternalStructuredTextParser.g:1635:2: (this_Compare_Expr_0= ruleCompare_Expr
			// ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0=
			// ruleCompare_Expr ) ) )* )
			{
				// InternalStructuredTextParser.g:1635:2: (this_Compare_Expr_0= ruleCompare_Expr
				// ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0=
				// ruleCompare_Expr ) ) )* )
				// InternalStructuredTextParser.g:1636:3: this_Compare_Expr_0= ruleCompare_Expr
				// ( () ( (lv_operator_2_0= ruleAnd_Operator ) ) ( (lv_right_3_0=
				// ruleCompare_Expr ) ) )*
				{

					newCompositeNode(grammarAccess.getAnd_ExprAccess().getCompare_ExprParserRuleCall_0());

					pushFollow(FOLLOW_42);
					this_Compare_Expr_0 = ruleCompare_Expr();

					state._fsp--;

					current = this_Compare_Expr_0;
					afterParserOrEnumRuleCall();

					// InternalStructuredTextParser.g:1644:3: ( () ( (lv_operator_2_0=
					// ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) ) )*
					loop22: do {
						int alt22 = 2;
						int LA22_0 = input.LA(1);

						if ((LA22_0 == AND || LA22_0 == Ampersand)) {
							alt22 = 1;
						}

						switch (alt22) {
						case 1:
						// InternalStructuredTextParser.g:1645:4: () ( (lv_operator_2_0=
						// ruleAnd_Operator ) ) ( (lv_right_3_0= ruleCompare_Expr ) )
						{
							// InternalStructuredTextParser.g:1645:4: ()
							// InternalStructuredTextParser.g:1646:5:
							{

								current = forceCreateModelElementAndSet(
										grammarAccess.getAnd_ExprAccess().getBinaryExpressionLeftAction_1_0(), current);

							}

							// InternalStructuredTextParser.g:1652:4: ( (lv_operator_2_0= ruleAnd_Operator )
							// )
							// InternalStructuredTextParser.g:1653:5: (lv_operator_2_0= ruleAnd_Operator )
							{
								// InternalStructuredTextParser.g:1653:5: (lv_operator_2_0= ruleAnd_Operator )
								// InternalStructuredTextParser.g:1654:6: lv_operator_2_0= ruleAnd_Operator
								{

									newCompositeNode(grammarAccess.getAnd_ExprAccess()
											.getOperatorAnd_OperatorEnumRuleCall_1_1_0());

									pushFollow(FOLLOW_19);
									lv_operator_2_0 = ruleAnd_Operator();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getAnd_ExprRule());
									}
									set(current, "operator", lv_operator_2_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.And_Operator");
									afterParserOrEnumRuleCall();

								}

							}

							// InternalStructuredTextParser.g:1671:4: ( (lv_right_3_0= ruleCompare_Expr ) )
							// InternalStructuredTextParser.g:1672:5: (lv_right_3_0= ruleCompare_Expr )
							{
								// InternalStructuredTextParser.g:1672:5: (lv_right_3_0= ruleCompare_Expr )
								// InternalStructuredTextParser.g:1673:6: lv_right_3_0= ruleCompare_Expr
								{

									newCompositeNode(grammarAccess.getAnd_ExprAccess()
											.getRightCompare_ExprParserRuleCall_1_2_0());

									pushFollow(FOLLOW_42);
									lv_right_3_0 = ruleCompare_Expr();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getAnd_ExprRule());
									}
									set(current, "right", lv_right_3_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Compare_Expr");
									afterParserOrEnumRuleCall();

								}

							}

						}
							break;

						default:
							break loop22;
						}
					} while (true);

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleAnd_Expr"

	// $ANTLR start "entryRuleCompare_Expr"
	// InternalStructuredTextParser.g:1695:1: entryRuleCompare_Expr returns [EObject
	// current=null] : iv_ruleCompare_Expr= ruleCompare_Expr EOF ;
	public final EObject entryRuleCompare_Expr() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleCompare_Expr = null;

		try {
			// InternalStructuredTextParser.g:1695:53: (iv_ruleCompare_Expr=
			// ruleCompare_Expr EOF )
			// InternalStructuredTextParser.g:1696:2: iv_ruleCompare_Expr= ruleCompare_Expr
			// EOF
			{
				newCompositeNode(grammarAccess.getCompare_ExprRule());
				pushFollow(FOLLOW_1);
				iv_ruleCompare_Expr = ruleCompare_Expr();

				state._fsp--;

				current = iv_ruleCompare_Expr;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleCompare_Expr"

	// $ANTLR start "ruleCompare_Expr"
	// InternalStructuredTextParser.g:1702:1: ruleCompare_Expr returns [EObject
	// current=null] : (this_Equ_Expr_0= ruleEqu_Expr ( () ( (lv_operator_2_0=
	// ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )* ) ;
	public final EObject ruleCompare_Expr() throws RecognitionException {
		EObject current = null;

		EObject this_Equ_Expr_0 = null;

		Enumerator lv_operator_2_0 = null;

		EObject lv_right_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1708:2: ( (this_Equ_Expr_0= ruleEqu_Expr ( ()
			// ( (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr )
			// ) )* ) )
			// InternalStructuredTextParser.g:1709:2: (this_Equ_Expr_0= ruleEqu_Expr ( () (
			// (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) )
			// )* )
			{
				// InternalStructuredTextParser.g:1709:2: (this_Equ_Expr_0= ruleEqu_Expr ( () (
				// (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) )
				// )* )
				// InternalStructuredTextParser.g:1710:3: this_Equ_Expr_0= ruleEqu_Expr ( () (
				// (lv_operator_2_0= ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) )
				// )*
				{

					newCompositeNode(grammarAccess.getCompare_ExprAccess().getEqu_ExprParserRuleCall_0());

					pushFollow(FOLLOW_43);
					this_Equ_Expr_0 = ruleEqu_Expr();

					state._fsp--;

					current = this_Equ_Expr_0;
					afterParserOrEnumRuleCall();

					// InternalStructuredTextParser.g:1718:3: ( () ( (lv_operator_2_0=
					// ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) ) )*
					loop23: do {
						int alt23 = 2;
						int LA23_0 = input.LA(1);

						if ((LA23_0 == LessThanSignGreaterThanSign || LA23_0 == EqualsSign)) {
							alt23 = 1;
						}

						switch (alt23) {
						case 1:
						// InternalStructuredTextParser.g:1719:4: () ( (lv_operator_2_0=
						// ruleCompare_Operator ) ) ( (lv_right_3_0= ruleEqu_Expr ) )
						{
							// InternalStructuredTextParser.g:1719:4: ()
							// InternalStructuredTextParser.g:1720:5:
							{

								current = forceCreateModelElementAndSet(
										grammarAccess.getCompare_ExprAccess().getBinaryExpressionLeftAction_1_0(),
										current);

							}

							// InternalStructuredTextParser.g:1726:4: ( (lv_operator_2_0=
							// ruleCompare_Operator ) )
							// InternalStructuredTextParser.g:1727:5: (lv_operator_2_0= ruleCompare_Operator
							// )
							{
								// InternalStructuredTextParser.g:1727:5: (lv_operator_2_0= ruleCompare_Operator
								// )
								// InternalStructuredTextParser.g:1728:6: lv_operator_2_0= ruleCompare_Operator
								{

									newCompositeNode(grammarAccess.getCompare_ExprAccess()
											.getOperatorCompare_OperatorEnumRuleCall_1_1_0());

									pushFollow(FOLLOW_19);
									lv_operator_2_0 = ruleCompare_Operator();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getCompare_ExprRule());
									}
									set(current, "operator", lv_operator_2_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Compare_Operator");
									afterParserOrEnumRuleCall();

								}

							}

							// InternalStructuredTextParser.g:1745:4: ( (lv_right_3_0= ruleEqu_Expr ) )
							// InternalStructuredTextParser.g:1746:5: (lv_right_3_0= ruleEqu_Expr )
							{
								// InternalStructuredTextParser.g:1746:5: (lv_right_3_0= ruleEqu_Expr )
								// InternalStructuredTextParser.g:1747:6: lv_right_3_0= ruleEqu_Expr
								{

									newCompositeNode(grammarAccess.getCompare_ExprAccess()
											.getRightEqu_ExprParserRuleCall_1_2_0());

									pushFollow(FOLLOW_43);
									lv_right_3_0 = ruleEqu_Expr();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getCompare_ExprRule());
									}
									set(current, "right", lv_right_3_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Equ_Expr");
									afterParserOrEnumRuleCall();

								}

							}

						}
							break;

						default:
							break loop23;
						}
					} while (true);

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleCompare_Expr"

	// $ANTLR start "entryRuleEqu_Expr"
	// InternalStructuredTextParser.g:1769:1: entryRuleEqu_Expr returns [EObject
	// current=null] : iv_ruleEqu_Expr= ruleEqu_Expr EOF ;
	public final EObject entryRuleEqu_Expr() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleEqu_Expr = null;

		try {
			// InternalStructuredTextParser.g:1769:49: (iv_ruleEqu_Expr= ruleEqu_Expr EOF )
			// InternalStructuredTextParser.g:1770:2: iv_ruleEqu_Expr= ruleEqu_Expr EOF
			{
				newCompositeNode(grammarAccess.getEqu_ExprRule());
				pushFollow(FOLLOW_1);
				iv_ruleEqu_Expr = ruleEqu_Expr();

				state._fsp--;

				current = iv_ruleEqu_Expr;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleEqu_Expr"

	// $ANTLR start "ruleEqu_Expr"
	// InternalStructuredTextParser.g:1776:1: ruleEqu_Expr returns [EObject
	// current=null] : (this_Add_Expr_0= ruleAdd_Expr ( () ( (lv_operator_2_0=
	// ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* ) ;
	public final EObject ruleEqu_Expr() throws RecognitionException {
		EObject current = null;

		EObject this_Add_Expr_0 = null;

		Enumerator lv_operator_2_0 = null;

		EObject lv_right_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1782:2: ( (this_Add_Expr_0= ruleAdd_Expr ( ()
			// ( (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )*
			// ) )
			// InternalStructuredTextParser.g:1783:2: (this_Add_Expr_0= ruleAdd_Expr ( () (
			// (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* )
			{
				// InternalStructuredTextParser.g:1783:2: (this_Add_Expr_0= ruleAdd_Expr ( () (
				// (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )* )
				// InternalStructuredTextParser.g:1784:3: this_Add_Expr_0= ruleAdd_Expr ( () (
				// (lv_operator_2_0= ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )*
				{

					newCompositeNode(grammarAccess.getEqu_ExprAccess().getAdd_ExprParserRuleCall_0());

					pushFollow(FOLLOW_44);
					this_Add_Expr_0 = ruleAdd_Expr();

					state._fsp--;

					current = this_Add_Expr_0;
					afterParserOrEnumRuleCall();

					// InternalStructuredTextParser.g:1792:3: ( () ( (lv_operator_2_0=
					// ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) ) )*
					loop24: do {
						int alt24 = 2;
						int LA24_0 = input.LA(1);

						if ((LA24_0 == LessThanSignEqualsSign || LA24_0 == GreaterThanSignEqualsSign
								|| LA24_0 == LessThanSign || LA24_0 == GreaterThanSign)) {
							alt24 = 1;
						}

						switch (alt24) {
						case 1:
						// InternalStructuredTextParser.g:1793:4: () ( (lv_operator_2_0=
						// ruleEqu_Operator ) ) ( (lv_right_3_0= ruleAdd_Expr ) )
						{
							// InternalStructuredTextParser.g:1793:4: ()
							// InternalStructuredTextParser.g:1794:5:
							{

								current = forceCreateModelElementAndSet(
										grammarAccess.getEqu_ExprAccess().getBinaryExpressionLeftAction_1_0(), current);

							}

							// InternalStructuredTextParser.g:1800:4: ( (lv_operator_2_0= ruleEqu_Operator )
							// )
							// InternalStructuredTextParser.g:1801:5: (lv_operator_2_0= ruleEqu_Operator )
							{
								// InternalStructuredTextParser.g:1801:5: (lv_operator_2_0= ruleEqu_Operator )
								// InternalStructuredTextParser.g:1802:6: lv_operator_2_0= ruleEqu_Operator
								{

									newCompositeNode(grammarAccess.getEqu_ExprAccess()
											.getOperatorEqu_OperatorEnumRuleCall_1_1_0());

									pushFollow(FOLLOW_19);
									lv_operator_2_0 = ruleEqu_Operator();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getEqu_ExprRule());
									}
									set(current, "operator", lv_operator_2_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Equ_Operator");
									afterParserOrEnumRuleCall();

								}

							}

							// InternalStructuredTextParser.g:1819:4: ( (lv_right_3_0= ruleAdd_Expr ) )
							// InternalStructuredTextParser.g:1820:5: (lv_right_3_0= ruleAdd_Expr )
							{
								// InternalStructuredTextParser.g:1820:5: (lv_right_3_0= ruleAdd_Expr )
								// InternalStructuredTextParser.g:1821:6: lv_right_3_0= ruleAdd_Expr
								{

									newCompositeNode(
											grammarAccess.getEqu_ExprAccess().getRightAdd_ExprParserRuleCall_1_2_0());

									pushFollow(FOLLOW_44);
									lv_right_3_0 = ruleAdd_Expr();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getEqu_ExprRule());
									}
									set(current, "right", lv_right_3_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Add_Expr");
									afterParserOrEnumRuleCall();

								}

							}

						}
							break;

						default:
							break loop24;
						}
					} while (true);

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleEqu_Expr"

	// $ANTLR start "entryRuleAdd_Expr"
	// InternalStructuredTextParser.g:1843:1: entryRuleAdd_Expr returns [EObject
	// current=null] : iv_ruleAdd_Expr= ruleAdd_Expr EOF ;
	public final EObject entryRuleAdd_Expr() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleAdd_Expr = null;

		try {
			// InternalStructuredTextParser.g:1843:49: (iv_ruleAdd_Expr= ruleAdd_Expr EOF )
			// InternalStructuredTextParser.g:1844:2: iv_ruleAdd_Expr= ruleAdd_Expr EOF
			{
				newCompositeNode(grammarAccess.getAdd_ExprRule());
				pushFollow(FOLLOW_1);
				iv_ruleAdd_Expr = ruleAdd_Expr();

				state._fsp--;

				current = iv_ruleAdd_Expr;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleAdd_Expr"

	// $ANTLR start "ruleAdd_Expr"
	// InternalStructuredTextParser.g:1850:1: ruleAdd_Expr returns [EObject
	// current=null] : (this_Term_0= ruleTerm ( () ( (lv_operator_2_0=
	// ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* ) ;
	public final EObject ruleAdd_Expr() throws RecognitionException {
		EObject current = null;

		EObject this_Term_0 = null;

		Enumerator lv_operator_2_0 = null;

		EObject lv_right_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1856:2: ( (this_Term_0= ruleTerm ( () (
			// (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* ) )
			// InternalStructuredTextParser.g:1857:2: (this_Term_0= ruleTerm ( () (
			// (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* )
			{
				// InternalStructuredTextParser.g:1857:2: (this_Term_0= ruleTerm ( () (
				// (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )* )
				// InternalStructuredTextParser.g:1858:3: this_Term_0= ruleTerm ( () (
				// (lv_operator_2_0= ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )*
				{

					newCompositeNode(grammarAccess.getAdd_ExprAccess().getTermParserRuleCall_0());

					pushFollow(FOLLOW_45);
					this_Term_0 = ruleTerm();

					state._fsp--;

					current = this_Term_0;
					afterParserOrEnumRuleCall();

					// InternalStructuredTextParser.g:1866:3: ( () ( (lv_operator_2_0=
					// ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) ) )*
					loop25: do {
						int alt25 = 2;
						int LA25_0 = input.LA(1);

						if ((LA25_0 == PlusSign || LA25_0 == HyphenMinus)) {
							alt25 = 1;
						}

						switch (alt25) {
						case 1:
						// InternalStructuredTextParser.g:1867:4: () ( (lv_operator_2_0=
						// ruleAdd_Operator ) ) ( (lv_right_3_0= ruleTerm ) )
						{
							// InternalStructuredTextParser.g:1867:4: ()
							// InternalStructuredTextParser.g:1868:5:
							{

								current = forceCreateModelElementAndSet(
										grammarAccess.getAdd_ExprAccess().getBinaryExpressionLeftAction_1_0(), current);

							}

							// InternalStructuredTextParser.g:1874:4: ( (lv_operator_2_0= ruleAdd_Operator )
							// )
							// InternalStructuredTextParser.g:1875:5: (lv_operator_2_0= ruleAdd_Operator )
							{
								// InternalStructuredTextParser.g:1875:5: (lv_operator_2_0= ruleAdd_Operator )
								// InternalStructuredTextParser.g:1876:6: lv_operator_2_0= ruleAdd_Operator
								{

									newCompositeNode(grammarAccess.getAdd_ExprAccess()
											.getOperatorAdd_OperatorEnumRuleCall_1_1_0());

									pushFollow(FOLLOW_19);
									lv_operator_2_0 = ruleAdd_Operator();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getAdd_ExprRule());
									}
									set(current, "operator", lv_operator_2_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Add_Operator");
									afterParserOrEnumRuleCall();

								}

							}

							// InternalStructuredTextParser.g:1893:4: ( (lv_right_3_0= ruleTerm ) )
							// InternalStructuredTextParser.g:1894:5: (lv_right_3_0= ruleTerm )
							{
								// InternalStructuredTextParser.g:1894:5: (lv_right_3_0= ruleTerm )
								// InternalStructuredTextParser.g:1895:6: lv_right_3_0= ruleTerm
								{

									newCompositeNode(
											grammarAccess.getAdd_ExprAccess().getRightTermParserRuleCall_1_2_0());

									pushFollow(FOLLOW_45);
									lv_right_3_0 = ruleTerm();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getAdd_ExprRule());
									}
									set(current, "right", lv_right_3_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Term");
									afterParserOrEnumRuleCall();

								}

							}

						}
							break;

						default:
							break loop25;
						}
					} while (true);

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleAdd_Expr"

	// $ANTLR start "entryRuleTerm"
	// InternalStructuredTextParser.g:1917:1: entryRuleTerm returns [EObject
	// current=null] : iv_ruleTerm= ruleTerm EOF ;
	public final EObject entryRuleTerm() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleTerm = null;

		try {
			// InternalStructuredTextParser.g:1917:45: (iv_ruleTerm= ruleTerm EOF )
			// InternalStructuredTextParser.g:1918:2: iv_ruleTerm= ruleTerm EOF
			{
				newCompositeNode(grammarAccess.getTermRule());
				pushFollow(FOLLOW_1);
				iv_ruleTerm = ruleTerm();

				state._fsp--;

				current = iv_ruleTerm;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleTerm"

	// $ANTLR start "ruleTerm"
	// InternalStructuredTextParser.g:1924:1: ruleTerm returns [EObject
	// current=null] : (this_Power_Expr_0= rulePower_Expr ( () ( (lv_operator_2_0=
	// ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )* ) ;
	public final EObject ruleTerm() throws RecognitionException {
		EObject current = null;

		EObject this_Power_Expr_0 = null;

		Enumerator lv_operator_2_0 = null;

		EObject lv_right_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:1930:2: ( (this_Power_Expr_0= rulePower_Expr (
			// () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr
			// ) ) )* ) )
			// InternalStructuredTextParser.g:1931:2: (this_Power_Expr_0= rulePower_Expr (
			// () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr
			// ) ) )* )
			{
				// InternalStructuredTextParser.g:1931:2: (this_Power_Expr_0= rulePower_Expr (
				// () ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr
				// ) ) )* )
				// InternalStructuredTextParser.g:1932:3: this_Power_Expr_0= rulePower_Expr ( ()
				// ( (lv_operator_2_0= ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) )
				// )*
				{

					newCompositeNode(grammarAccess.getTermAccess().getPower_ExprParserRuleCall_0());

					pushFollow(FOLLOW_46);
					this_Power_Expr_0 = rulePower_Expr();

					state._fsp--;

					current = this_Power_Expr_0;
					afterParserOrEnumRuleCall();

					// InternalStructuredTextParser.g:1940:3: ( () ( (lv_operator_2_0=
					// ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) ) )*
					loop26: do {
						int alt26 = 2;
						int LA26_0 = input.LA(1);

						if ((LA26_0 == MOD || LA26_0 == Asterisk || LA26_0 == Solidus)) {
							alt26 = 1;
						}

						switch (alt26) {
						case 1:
						// InternalStructuredTextParser.g:1941:4: () ( (lv_operator_2_0=
						// ruleTerm_Operator ) ) ( (lv_right_3_0= rulePower_Expr ) )
						{
							// InternalStructuredTextParser.g:1941:4: ()
							// InternalStructuredTextParser.g:1942:5:
							{

								current = forceCreateModelElementAndSet(
										grammarAccess.getTermAccess().getBinaryExpressionLeftAction_1_0(), current);

							}

							// InternalStructuredTextParser.g:1948:4: ( (lv_operator_2_0= ruleTerm_Operator
							// ) )
							// InternalStructuredTextParser.g:1949:5: (lv_operator_2_0= ruleTerm_Operator )
							{
								// InternalStructuredTextParser.g:1949:5: (lv_operator_2_0= ruleTerm_Operator )
								// InternalStructuredTextParser.g:1950:6: lv_operator_2_0= ruleTerm_Operator
								{

									newCompositeNode(
											grammarAccess.getTermAccess().getOperatorTerm_OperatorEnumRuleCall_1_1_0());

									pushFollow(FOLLOW_19);
									lv_operator_2_0 = ruleTerm_Operator();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getTermRule());
									}
									set(current, "operator", lv_operator_2_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Term_Operator");
									afterParserOrEnumRuleCall();

								}

							}

							// InternalStructuredTextParser.g:1967:4: ( (lv_right_3_0= rulePower_Expr ) )
							// InternalStructuredTextParser.g:1968:5: (lv_right_3_0= rulePower_Expr )
							{
								// InternalStructuredTextParser.g:1968:5: (lv_right_3_0= rulePower_Expr )
								// InternalStructuredTextParser.g:1969:6: lv_right_3_0= rulePower_Expr
								{

									newCompositeNode(
											grammarAccess.getTermAccess().getRightPower_ExprParserRuleCall_1_2_0());

									pushFollow(FOLLOW_46);
									lv_right_3_0 = rulePower_Expr();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getTermRule());
									}
									set(current, "right", lv_right_3_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Power_Expr");
									afterParserOrEnumRuleCall();

								}

							}

						}
							break;

						default:
							break loop26;
						}
					} while (true);

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleTerm"

	// $ANTLR start "entryRulePower_Expr"
	// InternalStructuredTextParser.g:1991:1: entryRulePower_Expr returns [EObject
	// current=null] : iv_rulePower_Expr= rulePower_Expr EOF ;
	public final EObject entryRulePower_Expr() throws RecognitionException {
		EObject current = null;

		EObject iv_rulePower_Expr = null;

		try {
			// InternalStructuredTextParser.g:1991:51: (iv_rulePower_Expr= rulePower_Expr
			// EOF )
			// InternalStructuredTextParser.g:1992:2: iv_rulePower_Expr= rulePower_Expr EOF
			{
				newCompositeNode(grammarAccess.getPower_ExprRule());
				pushFollow(FOLLOW_1);
				iv_rulePower_Expr = rulePower_Expr();

				state._fsp--;

				current = iv_rulePower_Expr;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRulePower_Expr"

	// $ANTLR start "rulePower_Expr"
	// InternalStructuredTextParser.g:1998:1: rulePower_Expr returns [EObject
	// current=null] : (this_Unary_Expr_0= ruleUnary_Expr ( () ( (lv_operator_2_0=
	// rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )* ) ;
	public final EObject rulePower_Expr() throws RecognitionException {
		EObject current = null;

		EObject this_Unary_Expr_0 = null;

		Enumerator lv_operator_2_0 = null;

		EObject lv_right_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2004:2: ( (this_Unary_Expr_0= ruleUnary_Expr (
			// () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr
			// ) ) )* ) )
			// InternalStructuredTextParser.g:2005:2: (this_Unary_Expr_0= ruleUnary_Expr (
			// () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr
			// ) ) )* )
			{
				// InternalStructuredTextParser.g:2005:2: (this_Unary_Expr_0= ruleUnary_Expr (
				// () ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr
				// ) ) )* )
				// InternalStructuredTextParser.g:2006:3: this_Unary_Expr_0= ruleUnary_Expr ( ()
				// ( (lv_operator_2_0= rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr )
				// ) )*
				{

					newCompositeNode(grammarAccess.getPower_ExprAccess().getUnary_ExprParserRuleCall_0());

					pushFollow(FOLLOW_47);
					this_Unary_Expr_0 = ruleUnary_Expr();

					state._fsp--;

					current = this_Unary_Expr_0;
					afterParserOrEnumRuleCall();

					// InternalStructuredTextParser.g:2014:3: ( () ( (lv_operator_2_0=
					// rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) ) )*
					loop27: do {
						int alt27 = 2;
						int LA27_0 = input.LA(1);

						if ((LA27_0 == AsteriskAsterisk)) {
							alt27 = 1;
						}

						switch (alt27) {
						case 1:
						// InternalStructuredTextParser.g:2015:4: () ( (lv_operator_2_0=
						// rulePower_Operator ) ) ( (lv_right_3_0= ruleUnary_Expr ) )
						{
							// InternalStructuredTextParser.g:2015:4: ()
							// InternalStructuredTextParser.g:2016:5:
							{

								current = forceCreateModelElementAndSet(
										grammarAccess.getPower_ExprAccess().getBinaryExpressionLeftAction_1_0(),
										current);

							}

							// InternalStructuredTextParser.g:2022:4: ( (lv_operator_2_0= rulePower_Operator
							// ) )
							// InternalStructuredTextParser.g:2023:5: (lv_operator_2_0= rulePower_Operator )
							{
								// InternalStructuredTextParser.g:2023:5: (lv_operator_2_0= rulePower_Operator )
								// InternalStructuredTextParser.g:2024:6: lv_operator_2_0= rulePower_Operator
								{

									newCompositeNode(grammarAccess.getPower_ExprAccess()
											.getOperatorPower_OperatorEnumRuleCall_1_1_0());

									pushFollow(FOLLOW_19);
									lv_operator_2_0 = rulePower_Operator();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getPower_ExprRule());
									}
									set(current, "operator", lv_operator_2_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Power_Operator");
									afterParserOrEnumRuleCall();

								}

							}

							// InternalStructuredTextParser.g:2041:4: ( (lv_right_3_0= ruleUnary_Expr ) )
							// InternalStructuredTextParser.g:2042:5: (lv_right_3_0= ruleUnary_Expr )
							{
								// InternalStructuredTextParser.g:2042:5: (lv_right_3_0= ruleUnary_Expr )
								// InternalStructuredTextParser.g:2043:6: lv_right_3_0= ruleUnary_Expr
								{

									newCompositeNode(grammarAccess.getPower_ExprAccess()
											.getRightUnary_ExprParserRuleCall_1_2_0());

									pushFollow(FOLLOW_47);
									lv_right_3_0 = ruleUnary_Expr();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getPower_ExprRule());
									}
									set(current, "right", lv_right_3_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Unary_Expr");
									afterParserOrEnumRuleCall();

								}

							}

						}
							break;

						default:
							break loop27;
						}
					} while (true);

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "rulePower_Expr"

	// $ANTLR start "entryRuleUnary_Expr"
	// InternalStructuredTextParser.g:2065:1: entryRuleUnary_Expr returns [EObject
	// current=null] : iv_ruleUnary_Expr= ruleUnary_Expr EOF ;
	public final EObject entryRuleUnary_Expr() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleUnary_Expr = null;

		try {
			// InternalStructuredTextParser.g:2065:51: (iv_ruleUnary_Expr= ruleUnary_Expr
			// EOF )
			// InternalStructuredTextParser.g:2066:2: iv_ruleUnary_Expr= ruleUnary_Expr EOF
			{
				newCompositeNode(grammarAccess.getUnary_ExprRule());
				pushFollow(FOLLOW_1);
				iv_ruleUnary_Expr = ruleUnary_Expr();

				state._fsp--;

				current = iv_ruleUnary_Expr;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleUnary_Expr"

	// $ANTLR start "ruleUnary_Expr"
	// InternalStructuredTextParser.g:2072:1: ruleUnary_Expr returns [EObject
	// current=null] : ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) (
	// (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3=
	// rulePrimary_Expr | this_Constant_4= ruleConstant ) ;
	public final EObject ruleUnary_Expr() throws RecognitionException {
		EObject current = null;

		Enumerator lv_operator_1_0 = null;

		EObject lv_expression_2_0 = null;

		EObject this_Primary_Expr_3 = null;

		EObject this_Constant_4 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2078:2: ( ( ( () ( (lv_operator_1_0=
			// ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) |
			// this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant ) )
			// InternalStructuredTextParser.g:2079:2: ( ( () ( (lv_operator_1_0=
			// ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) |
			// this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant )
			{
				// InternalStructuredTextParser.g:2079:2: ( ( () ( (lv_operator_1_0=
				// ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) |
				// this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant )
				int alt28 = 3;
				alt28 = dfa28.predict(input);
				switch (alt28) {
				case 1:
				// InternalStructuredTextParser.g:2080:3: ( () ( (lv_operator_1_0=
				// ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) )
				{
					// InternalStructuredTextParser.g:2080:3: ( () ( (lv_operator_1_0=
					// ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) )
					// InternalStructuredTextParser.g:2081:4: () ( (lv_operator_1_0=
					// ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) )
					{
						// InternalStructuredTextParser.g:2081:4: ()
						// InternalStructuredTextParser.g:2082:5:
						{

							current = forceCreateModelElement(
									grammarAccess.getUnary_ExprAccess().getUnaryExpressionAction_0_0(), current);

						}

						// InternalStructuredTextParser.g:2088:4: ( (lv_operator_1_0= ruleUnary_Operator
						// ) )
						// InternalStructuredTextParser.g:2089:5: (lv_operator_1_0= ruleUnary_Operator )
						{
							// InternalStructuredTextParser.g:2089:5: (lv_operator_1_0= ruleUnary_Operator )
							// InternalStructuredTextParser.g:2090:6: lv_operator_1_0= ruleUnary_Operator
							{

								newCompositeNode(grammarAccess.getUnary_ExprAccess()
										.getOperatorUnary_OperatorEnumRuleCall_0_1_0());

								pushFollow(FOLLOW_48);
								lv_operator_1_0 = ruleUnary_Operator();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getUnary_ExprRule());
								}
								set(current, "operator", lv_operator_1_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Unary_Operator");
								afterParserOrEnumRuleCall();

							}

						}

						// InternalStructuredTextParser.g:2107:4: ( (lv_expression_2_0= rulePrimary_Expr
						// ) )
						// InternalStructuredTextParser.g:2108:5: (lv_expression_2_0= rulePrimary_Expr )
						{
							// InternalStructuredTextParser.g:2108:5: (lv_expression_2_0= rulePrimary_Expr )
							// InternalStructuredTextParser.g:2109:6: lv_expression_2_0= rulePrimary_Expr
							{

								newCompositeNode(grammarAccess.getUnary_ExprAccess()
										.getExpressionPrimary_ExprParserRuleCall_0_2_0());

								pushFollow(FOLLOW_2);
								lv_expression_2_0 = rulePrimary_Expr();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getUnary_ExprRule());
								}
								set(current, "expression", lv_expression_2_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Primary_Expr");
								afterParserOrEnumRuleCall();

							}

						}

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:2128:3: this_Primary_Expr_3= rulePrimary_Expr
				{

					newCompositeNode(grammarAccess.getUnary_ExprAccess().getPrimary_ExprParserRuleCall_1());

					pushFollow(FOLLOW_2);
					this_Primary_Expr_3 = rulePrimary_Expr();

					state._fsp--;

					current = this_Primary_Expr_3;
					afterParserOrEnumRuleCall();

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:2137:3: this_Constant_4= ruleConstant
				{

					newCompositeNode(grammarAccess.getUnary_ExprAccess().getConstantParserRuleCall_2());

					pushFollow(FOLLOW_2);
					this_Constant_4 = ruleConstant();

					state._fsp--;

					current = this_Constant_4;
					afterParserOrEnumRuleCall();

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleUnary_Expr"

	// $ANTLR start "entryRulePrimary_Expr"
	// InternalStructuredTextParser.g:2149:1: entryRulePrimary_Expr returns [EObject
	// current=null] : iv_rulePrimary_Expr= rulePrimary_Expr EOF ;
	public final EObject entryRulePrimary_Expr() throws RecognitionException {
		EObject current = null;

		EObject iv_rulePrimary_Expr = null;

		try {
			// InternalStructuredTextParser.g:2149:53: (iv_rulePrimary_Expr=
			// rulePrimary_Expr EOF )
			// InternalStructuredTextParser.g:2150:2: iv_rulePrimary_Expr= rulePrimary_Expr
			// EOF
			{
				newCompositeNode(grammarAccess.getPrimary_ExprRule());
				pushFollow(FOLLOW_1);
				iv_rulePrimary_Expr = rulePrimary_Expr();

				state._fsp--;

				current = iv_rulePrimary_Expr;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRulePrimary_Expr"

	// $ANTLR start "rulePrimary_Expr"
	// InternalStructuredTextParser.g:2156:1: rulePrimary_Expr returns [EObject
	// current=null] : (this_Variable_0= ruleVariable | this_Func_Call_1=
	// ruleFunc_Call | (otherlv_2= LeftParenthesis this_Expression_3= ruleExpression
	// otherlv_4= RightParenthesis ) ) ;
	public final EObject rulePrimary_Expr() throws RecognitionException {
		EObject current = null;

		Token otherlv_2 = null;
		Token otherlv_4 = null;
		EObject this_Variable_0 = null;

		EObject this_Func_Call_1 = null;

		EObject this_Expression_3 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2162:2: ( (this_Variable_0= ruleVariable |
			// this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis
			// this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) ) )
			// InternalStructuredTextParser.g:2163:2: (this_Variable_0= ruleVariable |
			// this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis
			// this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) )
			{
				// InternalStructuredTextParser.g:2163:2: (this_Variable_0= ruleVariable |
				// this_Func_Call_1= ruleFunc_Call | (otherlv_2= LeftParenthesis
				// this_Expression_3= ruleExpression otherlv_4= RightParenthesis ) )
				int alt29 = 3;
				switch (input.LA(1)) {
				case RULE_ID: {
					int LA29_1 = input.LA(2);

					if ((LA29_1 == LeftParenthesis)) {
						alt29 = 2;
					} else if ((LA29_1 == EOF || LA29_1 == END_REPEAT || LA29_1 == THEN
							|| (LA29_1 >= B && LA29_1 <= AND) || LA29_1 == MOD
							|| (LA29_1 >= XOR && LA29_1 <= AsteriskAsterisk)
							|| (LA29_1 >= LessThanSignEqualsSign && LA29_1 <= LessThanSignGreaterThanSign)
							|| LA29_1 == GreaterThanSignEqualsSign || (LA29_1 >= BY && LA29_1 <= DO)
							|| (LA29_1 >= OF && LA29_1 <= TO) || LA29_1 == Ampersand
							|| (LA29_1 >= RightParenthesis && LA29_1 <= Solidus)
							|| (LA29_1 >= Semicolon && LA29_1 <= GreaterThanSign)
							|| (LA29_1 >= LeftSquareBracket && LA29_1 <= RightSquareBracket))) {
						alt29 = 1;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 29, 1, input);

						throw nvae;
					}
				}
					break;
				case DT:
				case LT:
				case T: {
					alt29 = 1;
				}
					break;
				case TIME: {
					alt29 = 2;
				}
					break;
				case LeftParenthesis: {
					alt29 = 3;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 29, 0, input);

					throw nvae;
				}

				switch (alt29) {
				case 1:
				// InternalStructuredTextParser.g:2164:3: this_Variable_0= ruleVariable
				{

					newCompositeNode(grammarAccess.getPrimary_ExprAccess().getVariableParserRuleCall_0());

					pushFollow(FOLLOW_2);
					this_Variable_0 = ruleVariable();

					state._fsp--;

					current = this_Variable_0;
					afterParserOrEnumRuleCall();

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:2173:3: this_Func_Call_1= ruleFunc_Call
				{

					newCompositeNode(grammarAccess.getPrimary_ExprAccess().getFunc_CallParserRuleCall_1());

					pushFollow(FOLLOW_2);
					this_Func_Call_1 = ruleFunc_Call();

					state._fsp--;

					current = this_Func_Call_1;
					afterParserOrEnumRuleCall();

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:2182:3: (otherlv_2= LeftParenthesis
				// this_Expression_3= ruleExpression otherlv_4= RightParenthesis )
				{
					// InternalStructuredTextParser.g:2182:3: (otherlv_2= LeftParenthesis
					// this_Expression_3= ruleExpression otherlv_4= RightParenthesis )
					// InternalStructuredTextParser.g:2183:4: otherlv_2= LeftParenthesis
					// this_Expression_3= ruleExpression otherlv_4= RightParenthesis
					{
						otherlv_2 = (Token) match(input, LeftParenthesis, FOLLOW_19);

						newLeafNode(otherlv_2, grammarAccess.getPrimary_ExprAccess().getLeftParenthesisKeyword_2_0());

						newCompositeNode(grammarAccess.getPrimary_ExprAccess().getExpressionParserRuleCall_2_1());

						pushFollow(FOLLOW_21);
						this_Expression_3 = ruleExpression();

						state._fsp--;

						current = this_Expression_3;
						afterParserOrEnumRuleCall();

						otherlv_4 = (Token) match(input, RightParenthesis, FOLLOW_2);

						newLeafNode(otherlv_4, grammarAccess.getPrimary_ExprAccess().getRightParenthesisKeyword_2_2());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "rulePrimary_Expr"

	// $ANTLR start "entryRuleFunc_Call"
	// InternalStructuredTextParser.g:2204:1: entryRuleFunc_Call returns [EObject
	// current=null] : iv_ruleFunc_Call= ruleFunc_Call EOF ;
	public final EObject entryRuleFunc_Call() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleFunc_Call = null;

		try {
			// InternalStructuredTextParser.g:2204:50: (iv_ruleFunc_Call= ruleFunc_Call EOF
			// )
			// InternalStructuredTextParser.g:2205:2: iv_ruleFunc_Call= ruleFunc_Call EOF
			{
				newCompositeNode(grammarAccess.getFunc_CallRule());
				pushFollow(FOLLOW_1);
				iv_ruleFunc_Call = ruleFunc_Call();

				state._fsp--;

				current = iv_ruleFunc_Call;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleFunc_Call"

	// $ANTLR start "ruleFunc_Call"
	// InternalStructuredTextParser.g:2211:1: ruleFunc_Call returns [EObject
	// current=null] : ( ( ( (lv_func_0_1= RULE_ID | lv_func_0_2= TIME ) ) )
	// otherlv_1= LeftParenthesis ( ( (lv_args_2_0= ruleParam_Assign ) ) (otherlv_3=
	// Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )? otherlv_5= RightParenthesis
	// ) ;
	public final EObject ruleFunc_Call() throws RecognitionException {
		EObject current = null;

		Token lv_func_0_1 = null;
		Token lv_func_0_2 = null;
		Token otherlv_1 = null;
		Token otherlv_3 = null;
		Token otherlv_5 = null;
		EObject lv_args_2_0 = null;

		EObject lv_args_4_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2217:2: ( ( ( ( (lv_func_0_1= RULE_ID |
			// lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0=
			// ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) )
			// )* )? otherlv_5= RightParenthesis ) )
			// InternalStructuredTextParser.g:2218:2: ( ( ( (lv_func_0_1= RULE_ID |
			// lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0=
			// ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) )
			// )* )? otherlv_5= RightParenthesis )
			{
				// InternalStructuredTextParser.g:2218:2: ( ( ( (lv_func_0_1= RULE_ID |
				// lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0=
				// ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) )
				// )* )? otherlv_5= RightParenthesis )
				// InternalStructuredTextParser.g:2219:3: ( ( (lv_func_0_1= RULE_ID |
				// lv_func_0_2= TIME ) ) ) otherlv_1= LeftParenthesis ( ( (lv_args_2_0=
				// ruleParam_Assign ) ) (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) )
				// )* )? otherlv_5= RightParenthesis
				{
					// InternalStructuredTextParser.g:2219:3: ( ( (lv_func_0_1= RULE_ID |
					// lv_func_0_2= TIME ) ) )
					// InternalStructuredTextParser.g:2220:4: ( (lv_func_0_1= RULE_ID | lv_func_0_2=
					// TIME ) )
					{
						// InternalStructuredTextParser.g:2220:4: ( (lv_func_0_1= RULE_ID | lv_func_0_2=
						// TIME ) )
						// InternalStructuredTextParser.g:2221:5: (lv_func_0_1= RULE_ID | lv_func_0_2=
						// TIME )
						{
							// InternalStructuredTextParser.g:2221:5: (lv_func_0_1= RULE_ID | lv_func_0_2=
							// TIME )
							int alt30 = 2;
							int LA30_0 = input.LA(1);

							if ((LA30_0 == RULE_ID)) {
								alt30 = 1;
							} else if ((LA30_0 == TIME)) {
								alt30 = 2;
							} else {
								NoViableAltException nvae = new NoViableAltException("", 30, 0, input);

								throw nvae;
							}
							switch (alt30) {
							case 1:
							// InternalStructuredTextParser.g:2222:6: lv_func_0_1= RULE_ID
							{
								lv_func_0_1 = (Token) match(input, RULE_ID, FOLLOW_20);

								newLeafNode(lv_func_0_1,
										grammarAccess.getFunc_CallAccess().getFuncIDTerminalRuleCall_0_0_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getFunc_CallRule());
								}
								setWithLastConsumed(current, "func", lv_func_0_1,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ID");

							}
								break;
							case 2:
							// InternalStructuredTextParser.g:2237:6: lv_func_0_2= TIME
							{
								lv_func_0_2 = (Token) match(input, TIME, FOLLOW_20);

								newLeafNode(lv_func_0_2, grammarAccess.getFunc_CallAccess().getFuncTIMEKeyword_0_0_1());

								if (current == null) {
									current = createModelElement(grammarAccess.getFunc_CallRule());
								}
								setWithLastConsumed(current, "func", lv_func_0_2, null);

							}
								break;

							}

						}

					}

					otherlv_1 = (Token) match(input, LeftParenthesis, FOLLOW_49);

					newLeafNode(otherlv_1, grammarAccess.getFunc_CallAccess().getLeftParenthesisKeyword_1());

					// InternalStructuredTextParser.g:2254:3: ( ( (lv_args_2_0= ruleParam_Assign ) )
					// (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )* )?
					int alt32 = 2;
					int LA32_0 = input.LA(1);

					if (((LA32_0 >= LDATE_AND_TIME && LA32_0 <= TIME_OF_DAY) || LA32_0 == WSTRING || LA32_0 == STRING
							|| (LA32_0 >= FALSE && LA32_0 <= LTIME) || (LA32_0 >= UDINT && LA32_0 <= ULINT)
							|| (LA32_0 >= USINT && LA32_0 <= WCHAR) || LA32_0 == BOOL
							|| (LA32_0 >= CHAR && LA32_0 <= DINT) || (LA32_0 >= LINT && LA32_0 <= SINT)
							|| (LA32_0 >= TIME && LA32_0 <= UINT) || (LA32_0 >= INT && LA32_0 <= LDT)
							|| (LA32_0 >= NOT && LA32_0 <= TOD) || LA32_0 == DT || (LA32_0 >= LD && LA32_0 <= LT)
							|| LA32_0 == LeftParenthesis || LA32_0 == PlusSign || LA32_0 == HyphenMinus || LA32_0 == T
							|| LA32_0 == D_1 || (LA32_0 >= RULE_ID && LA32_0 <= RULE_UNSIGNED_INT)
							|| LA32_0 == RULE_S_BYTE_CHAR_STR || LA32_0 == RULE_D_BYTE_CHAR_STR)) {
						alt32 = 1;
					}
					switch (alt32) {
					case 1:
					// InternalStructuredTextParser.g:2255:4: ( (lv_args_2_0= ruleParam_Assign ) )
					// (otherlv_3= Comma ( (lv_args_4_0= ruleParam_Assign ) ) )*
					{
						// InternalStructuredTextParser.g:2255:4: ( (lv_args_2_0= ruleParam_Assign ) )
						// InternalStructuredTextParser.g:2256:5: (lv_args_2_0= ruleParam_Assign )
						{
							// InternalStructuredTextParser.g:2256:5: (lv_args_2_0= ruleParam_Assign )
							// InternalStructuredTextParser.g:2257:6: lv_args_2_0= ruleParam_Assign
							{

								newCompositeNode(
										grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_0_0());

								pushFollow(FOLLOW_50);
								lv_args_2_0 = ruleParam_Assign();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getFunc_CallRule());
								}
								add(current, "args", lv_args_2_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Param_Assign");
								afterParserOrEnumRuleCall();

							}

						}

						// InternalStructuredTextParser.g:2274:4: (otherlv_3= Comma ( (lv_args_4_0=
						// ruleParam_Assign ) ) )*
						loop31: do {
							int alt31 = 2;
							int LA31_0 = input.LA(1);

							if ((LA31_0 == Comma)) {
								alt31 = 1;
							}

							switch (alt31) {
							case 1:
							// InternalStructuredTextParser.g:2275:5: otherlv_3= Comma ( (lv_args_4_0=
							// ruleParam_Assign ) )
							{
								otherlv_3 = (Token) match(input, Comma, FOLLOW_19);

								newLeafNode(otherlv_3, grammarAccess.getFunc_CallAccess().getCommaKeyword_2_1_0());

								// InternalStructuredTextParser.g:2279:5: ( (lv_args_4_0= ruleParam_Assign ) )
								// InternalStructuredTextParser.g:2280:6: (lv_args_4_0= ruleParam_Assign )
								{
									// InternalStructuredTextParser.g:2280:6: (lv_args_4_0= ruleParam_Assign )
									// InternalStructuredTextParser.g:2281:7: lv_args_4_0= ruleParam_Assign
									{

										newCompositeNode(grammarAccess.getFunc_CallAccess()
												.getArgsParam_AssignParserRuleCall_2_1_1_0());

										pushFollow(FOLLOW_50);
										lv_args_4_0 = ruleParam_Assign();

										state._fsp--;

										if (current == null) {
											current = createModelElementForParent(grammarAccess.getFunc_CallRule());
										}
										add(current, "args", lv_args_4_0,
												"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Param_Assign");
										afterParserOrEnumRuleCall();

									}

								}

							}
								break;

							default:
								break loop31;
							}
						} while (true);

					}
						break;

					}

					otherlv_5 = (Token) match(input, RightParenthesis, FOLLOW_2);

					newLeafNode(otherlv_5, grammarAccess.getFunc_CallAccess().getRightParenthesisKeyword_3());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleFunc_Call"

	// $ANTLR start "entryRuleParam_Assign"
	// InternalStructuredTextParser.g:2308:1: entryRuleParam_Assign returns [EObject
	// current=null] : iv_ruleParam_Assign= ruleParam_Assign EOF ;
	public final EObject entryRuleParam_Assign() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleParam_Assign = null;

		try {
			// InternalStructuredTextParser.g:2308:53: (iv_ruleParam_Assign=
			// ruleParam_Assign EOF )
			// InternalStructuredTextParser.g:2309:2: iv_ruleParam_Assign= ruleParam_Assign
			// EOF
			{
				newCompositeNode(grammarAccess.getParam_AssignRule());
				pushFollow(FOLLOW_1);
				iv_ruleParam_Assign = ruleParam_Assign();

				state._fsp--;

				current = iv_ruleParam_Assign;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleParam_Assign"

	// $ANTLR start "ruleParam_Assign"
	// InternalStructuredTextParser.g:2315:1: ruleParam_Assign returns [EObject
	// current=null] : (this_Param_Assign_In_0= ruleParam_Assign_In |
	// this_Param_Assign_Out_1= ruleParam_Assign_Out ) ;
	public final EObject ruleParam_Assign() throws RecognitionException {
		EObject current = null;

		EObject this_Param_Assign_In_0 = null;

		EObject this_Param_Assign_Out_1 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2321:2: ( (this_Param_Assign_In_0=
			// ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out ) )
			// InternalStructuredTextParser.g:2322:2: (this_Param_Assign_In_0=
			// ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out )
			{
				// InternalStructuredTextParser.g:2322:2: (this_Param_Assign_In_0=
				// ruleParam_Assign_In | this_Param_Assign_Out_1= ruleParam_Assign_Out )
				int alt33 = 2;
				switch (input.LA(1)) {
				case RULE_ID: {
					int LA33_1 = input.LA(2);

					if ((LA33_1 == EOF || (LA33_1 >= B && LA33_1 <= AND) || LA33_1 == MOD
							|| (LA33_1 >= XOR && LA33_1 <= LessThanSignGreaterThanSign)
							|| LA33_1 == GreaterThanSignEqualsSign || LA33_1 == OR
							|| (LA33_1 >= Ampersand && LA33_1 <= Solidus)
							|| (LA33_1 >= LessThanSign && LA33_1 <= GreaterThanSign) || LA33_1 == LeftSquareBracket)) {
						alt33 = 1;
					} else if ((LA33_1 == EqualsSignGreaterThanSign)) {
						alt33 = 2;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 33, 1, input);

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
					alt33 = 1;
				}
					break;
				case NOT: {
					int LA33_3 = input.LA(2);

					if ((LA33_3 == RULE_ID)) {
						int LA33_5 = input.LA(3);

						if ((LA33_5 == EOF || (LA33_5 >= B && LA33_5 <= AND) || LA33_5 == MOD
								|| (LA33_5 >= XOR && LA33_5 <= AsteriskAsterisk)
								|| (LA33_5 >= LessThanSignEqualsSign && LA33_5 <= LessThanSignGreaterThanSign)
								|| LA33_5 == GreaterThanSignEqualsSign || LA33_5 == OR
								|| (LA33_5 >= Ampersand && LA33_5 <= Solidus)
								|| (LA33_5 >= LessThanSign && LA33_5 <= GreaterThanSign)
								|| LA33_5 == LeftSquareBracket)) {
							alt33 = 1;
						} else if ((LA33_5 == EqualsSignGreaterThanSign)) {
							alt33 = 2;
						} else {
							NoViableAltException nvae = new NoViableAltException("", 33, 5, input);

							throw nvae;
						}
					} else if ((LA33_3 == TIME || LA33_3 == DT || LA33_3 == LT || LA33_3 == LeftParenthesis
							|| LA33_3 == T)) {
						alt33 = 1;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 33, 3, input);

						throw nvae;
					}
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 33, 0, input);

					throw nvae;
				}

				switch (alt33) {
				case 1:
				// InternalStructuredTextParser.g:2323:3: this_Param_Assign_In_0=
				// ruleParam_Assign_In
				{

					newCompositeNode(grammarAccess.getParam_AssignAccess().getParam_Assign_InParserRuleCall_0());

					pushFollow(FOLLOW_2);
					this_Param_Assign_In_0 = ruleParam_Assign_In();

					state._fsp--;

					current = this_Param_Assign_In_0;
					afterParserOrEnumRuleCall();

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:2332:3: this_Param_Assign_Out_1=
				// ruleParam_Assign_Out
				{

					newCompositeNode(grammarAccess.getParam_AssignAccess().getParam_Assign_OutParserRuleCall_1());

					pushFollow(FOLLOW_2);
					this_Param_Assign_Out_1 = ruleParam_Assign_Out();

					state._fsp--;

					current = this_Param_Assign_Out_1;
					afterParserOrEnumRuleCall();

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleParam_Assign"

	// $ANTLR start "entryRuleParam_Assign_In"
	// InternalStructuredTextParser.g:2344:1: entryRuleParam_Assign_In returns
	// [EObject current=null] : iv_ruleParam_Assign_In= ruleParam_Assign_In EOF ;
	public final EObject entryRuleParam_Assign_In() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleParam_Assign_In = null;

		try {
			// InternalStructuredTextParser.g:2344:56: (iv_ruleParam_Assign_In=
			// ruleParam_Assign_In EOF )
			// InternalStructuredTextParser.g:2345:2: iv_ruleParam_Assign_In=
			// ruleParam_Assign_In EOF
			{
				newCompositeNode(grammarAccess.getParam_Assign_InRule());
				pushFollow(FOLLOW_1);
				iv_ruleParam_Assign_In = ruleParam_Assign_In();

				state._fsp--;

				current = iv_ruleParam_Assign_In;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleParam_Assign_In"

	// $ANTLR start "ruleParam_Assign_In"
	// InternalStructuredTextParser.g:2351:1: ruleParam_Assign_In returns [EObject
	// current=null] : ( ( ( (lv_var_0_0= RULE_ID ) ) otherlv_1= ColonEqualsSign )?
	// ( (lv_expr_2_0= ruleExpression ) ) ) ;
	public final EObject ruleParam_Assign_In() throws RecognitionException {
		EObject current = null;

		Token lv_var_0_0 = null;
		Token otherlv_1 = null;
		EObject lv_expr_2_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2357:2: ( ( ( ( (lv_var_0_0= RULE_ID ) )
			// otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) ) )
			// InternalStructuredTextParser.g:2358:2: ( ( ( (lv_var_0_0= RULE_ID ) )
			// otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) )
			{
				// InternalStructuredTextParser.g:2358:2: ( ( ( (lv_var_0_0= RULE_ID ) )
				// otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) ) )
				// InternalStructuredTextParser.g:2359:3: ( ( (lv_var_0_0= RULE_ID ) )
				// otherlv_1= ColonEqualsSign )? ( (lv_expr_2_0= ruleExpression ) )
				{
					// InternalStructuredTextParser.g:2359:3: ( ( (lv_var_0_0= RULE_ID ) )
					// otherlv_1= ColonEqualsSign )?
					int alt34 = 2;
					int LA34_0 = input.LA(1);

					if ((LA34_0 == RULE_ID)) {
						int LA34_1 = input.LA(2);

						if ((LA34_1 == ColonEqualsSign)) {
							alt34 = 1;
						}
					}
					switch (alt34) {
					case 1:
					// InternalStructuredTextParser.g:2360:4: ( (lv_var_0_0= RULE_ID ) ) otherlv_1=
					// ColonEqualsSign
					{
						// InternalStructuredTextParser.g:2360:4: ( (lv_var_0_0= RULE_ID ) )
						// InternalStructuredTextParser.g:2361:5: (lv_var_0_0= RULE_ID )
						{
							// InternalStructuredTextParser.g:2361:5: (lv_var_0_0= RULE_ID )
							// InternalStructuredTextParser.g:2362:6: lv_var_0_0= RULE_ID
							{
								lv_var_0_0 = (Token) match(input, RULE_ID, FOLLOW_18);

								newLeafNode(lv_var_0_0,
										grammarAccess.getParam_Assign_InAccess().getVarIDTerminalRuleCall_0_0_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getParam_Assign_InRule());
								}
								setWithLastConsumed(current, "var", lv_var_0_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ID");

							}

						}

						otherlv_1 = (Token) match(input, ColonEqualsSign, FOLLOW_19);

						newLeafNode(otherlv_1,
								grammarAccess.getParam_Assign_InAccess().getColonEqualsSignKeyword_0_1());

					}
						break;

					}

					// InternalStructuredTextParser.g:2383:3: ( (lv_expr_2_0= ruleExpression ) )
					// InternalStructuredTextParser.g:2384:4: (lv_expr_2_0= ruleExpression )
					{
						// InternalStructuredTextParser.g:2384:4: (lv_expr_2_0= ruleExpression )
						// InternalStructuredTextParser.g:2385:5: lv_expr_2_0= ruleExpression
						{

							newCompositeNode(
									grammarAccess.getParam_Assign_InAccess().getExprExpressionParserRuleCall_1_0());

							pushFollow(FOLLOW_2);
							lv_expr_2_0 = ruleExpression();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getParam_Assign_InRule());
							}
							set(current, "expr", lv_expr_2_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleParam_Assign_In"

	// $ANTLR start "entryRuleParam_Assign_Out"
	// InternalStructuredTextParser.g:2406:1: entryRuleParam_Assign_Out returns
	// [EObject current=null] : iv_ruleParam_Assign_Out= ruleParam_Assign_Out EOF ;
	public final EObject entryRuleParam_Assign_Out() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleParam_Assign_Out = null;

		try {
			// InternalStructuredTextParser.g:2406:57: (iv_ruleParam_Assign_Out=
			// ruleParam_Assign_Out EOF )
			// InternalStructuredTextParser.g:2407:2: iv_ruleParam_Assign_Out=
			// ruleParam_Assign_Out EOF
			{
				newCompositeNode(grammarAccess.getParam_Assign_OutRule());
				pushFollow(FOLLOW_1);
				iv_ruleParam_Assign_Out = ruleParam_Assign_Out();

				state._fsp--;

				current = iv_ruleParam_Assign_Out;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleParam_Assign_Out"

	// $ANTLR start "ruleParam_Assign_Out"
	// InternalStructuredTextParser.g:2413:1: ruleParam_Assign_Out returns [EObject
	// current=null] : ( ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0= RULE_ID ) )
	// otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable ) ) ) ;
	public final EObject ruleParam_Assign_Out() throws RecognitionException {
		EObject current = null;

		Token lv_not_0_0 = null;
		Token lv_var_1_0 = null;
		Token otherlv_2 = null;
		EObject lv_expr_3_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2419:2: ( ( ( (lv_not_0_0= NOT ) )? (
			// (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0=
			// ruleVariable ) ) ) )
			// InternalStructuredTextParser.g:2420:2: ( ( (lv_not_0_0= NOT ) )? (
			// (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0=
			// ruleVariable ) ) )
			{
				// InternalStructuredTextParser.g:2420:2: ( ( (lv_not_0_0= NOT ) )? (
				// (lv_var_1_0= RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0=
				// ruleVariable ) ) )
				// InternalStructuredTextParser.g:2421:3: ( (lv_not_0_0= NOT ) )? ( (lv_var_1_0=
				// RULE_ID ) ) otherlv_2= EqualsSignGreaterThanSign ( (lv_expr_3_0= ruleVariable
				// ) )
				{
					// InternalStructuredTextParser.g:2421:3: ( (lv_not_0_0= NOT ) )?
					int alt35 = 2;
					int LA35_0 = input.LA(1);

					if ((LA35_0 == NOT)) {
						alt35 = 1;
					}
					switch (alt35) {
					case 1:
					// InternalStructuredTextParser.g:2422:4: (lv_not_0_0= NOT )
					{
						// InternalStructuredTextParser.g:2422:4: (lv_not_0_0= NOT )
						// InternalStructuredTextParser.g:2423:5: lv_not_0_0= NOT
						{
							lv_not_0_0 = (Token) match(input, NOT, FOLLOW_6);

							newLeafNode(lv_not_0_0, grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0());

							if (current == null) {
								current = createModelElement(grammarAccess.getParam_Assign_OutRule());
							}
							setWithLastConsumed(current, "not", true, "NOT");

						}

					}
						break;

					}

					// InternalStructuredTextParser.g:2435:3: ( (lv_var_1_0= RULE_ID ) )
					// InternalStructuredTextParser.g:2436:4: (lv_var_1_0= RULE_ID )
					{
						// InternalStructuredTextParser.g:2436:4: (lv_var_1_0= RULE_ID )
						// InternalStructuredTextParser.g:2437:5: lv_var_1_0= RULE_ID
						{
							lv_var_1_0 = (Token) match(input, RULE_ID, FOLLOW_51);

							newLeafNode(lv_var_1_0,
									grammarAccess.getParam_Assign_OutAccess().getVarIDTerminalRuleCall_1_0());

							if (current == null) {
								current = createModelElement(grammarAccess.getParam_Assign_OutRule());
							}
							setWithLastConsumed(current, "var", lv_var_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.ID");

						}

					}

					otherlv_2 = (Token) match(input, EqualsSignGreaterThanSign, FOLLOW_15);

					newLeafNode(otherlv_2,
							grammarAccess.getParam_Assign_OutAccess().getEqualsSignGreaterThanSignKeyword_2());

					// InternalStructuredTextParser.g:2457:3: ( (lv_expr_3_0= ruleVariable ) )
					// InternalStructuredTextParser.g:2458:4: (lv_expr_3_0= ruleVariable )
					{
						// InternalStructuredTextParser.g:2458:4: (lv_expr_3_0= ruleVariable )
						// InternalStructuredTextParser.g:2459:5: lv_expr_3_0= ruleVariable
						{

							newCompositeNode(
									grammarAccess.getParam_Assign_OutAccess().getExprVariableParserRuleCall_3_0());

							pushFollow(FOLLOW_2);
							lv_expr_3_0 = ruleVariable();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getParam_Assign_OutRule());
							}
							set(current, "expr", lv_expr_3_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Variable");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleParam_Assign_Out"

	// $ANTLR start "entryRuleVariable"
	// InternalStructuredTextParser.g:2480:1: entryRuleVariable returns [EObject
	// current=null] : iv_ruleVariable= ruleVariable EOF ;
	public final EObject entryRuleVariable() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleVariable = null;

		try {
			// InternalStructuredTextParser.g:2480:49: (iv_ruleVariable= ruleVariable EOF )
			// InternalStructuredTextParser.g:2481:2: iv_ruleVariable= ruleVariable EOF
			{
				newCompositeNode(grammarAccess.getVariableRule());
				pushFollow(FOLLOW_1);
				iv_ruleVariable = ruleVariable();

				state._fsp--;

				current = iv_ruleVariable;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleVariable"

	// $ANTLR start "ruleVariable"
	// InternalStructuredTextParser.g:2487:1: ruleVariable returns [EObject
	// current=null] : (this_Variable_Subscript_0= ruleVariable_Subscript (
	// (lv_part_1_0= ruleMultibit_Part_Access ) )? ) ;
	public final EObject ruleVariable() throws RecognitionException {
		EObject current = null;

		EObject this_Variable_Subscript_0 = null;

		EObject lv_part_1_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2493:2: ( (this_Variable_Subscript_0=
			// ruleVariable_Subscript ( (lv_part_1_0= ruleMultibit_Part_Access ) )? ) )
			// InternalStructuredTextParser.g:2494:2: (this_Variable_Subscript_0=
			// ruleVariable_Subscript ( (lv_part_1_0= ruleMultibit_Part_Access ) )? )
			{
				// InternalStructuredTextParser.g:2494:2: (this_Variable_Subscript_0=
				// ruleVariable_Subscript ( (lv_part_1_0= ruleMultibit_Part_Access ) )? )
				// InternalStructuredTextParser.g:2495:3: this_Variable_Subscript_0=
				// ruleVariable_Subscript ( (lv_part_1_0= ruleMultibit_Part_Access ) )?
				{

					newCompositeNode(grammarAccess.getVariableAccess().getVariable_SubscriptParserRuleCall_0());

					pushFollow(FOLLOW_52);
					this_Variable_Subscript_0 = ruleVariable_Subscript();

					state._fsp--;

					current = this_Variable_Subscript_0;
					afterParserOrEnumRuleCall();

					// InternalStructuredTextParser.g:2503:3: ( (lv_part_1_0=
					// ruleMultibit_Part_Access ) )?
					int alt36 = 2;
					int LA36_0 = input.LA(1);

					if (((LA36_0 >= B && LA36_0 <= X) || LA36_0 == FullStop)) {
						alt36 = 1;
					}
					switch (alt36) {
					case 1:
					// InternalStructuredTextParser.g:2504:4: (lv_part_1_0= ruleMultibit_Part_Access
					// )
					{
						// InternalStructuredTextParser.g:2504:4: (lv_part_1_0= ruleMultibit_Part_Access
						// )
						// InternalStructuredTextParser.g:2505:5: lv_part_1_0= ruleMultibit_Part_Access
						{

							newCompositeNode(
									grammarAccess.getVariableAccess().getPartMultibit_Part_AccessParserRuleCall_1_0());

							pushFollow(FOLLOW_2);
							lv_part_1_0 = ruleMultibit_Part_Access();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getVariableRule());
							}
							set(current, "part", lv_part_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Multibit_Part_Access");
							afterParserOrEnumRuleCall();

						}

					}
						break;

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleVariable"

	// $ANTLR start "entryRuleVariable_Subscript"
	// InternalStructuredTextParser.g:2526:1: entryRuleVariable_Subscript returns
	// [EObject current=null] : iv_ruleVariable_Subscript= ruleVariable_Subscript
	// EOF ;
	public final EObject entryRuleVariable_Subscript() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleVariable_Subscript = null;

		try {
			// InternalStructuredTextParser.g:2526:59: (iv_ruleVariable_Subscript=
			// ruleVariable_Subscript EOF )
			// InternalStructuredTextParser.g:2527:2: iv_ruleVariable_Subscript=
			// ruleVariable_Subscript EOF
			{
				newCompositeNode(grammarAccess.getVariable_SubscriptRule());
				pushFollow(FOLLOW_1);
				iv_ruleVariable_Subscript = ruleVariable_Subscript();

				state._fsp--;

				current = iv_ruleVariable_Subscript;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleVariable_Subscript"

	// $ANTLR start "ruleVariable_Subscript"
	// InternalStructuredTextParser.g:2533:1: ruleVariable_Subscript returns
	// [EObject current=null] : ( (this_Variable_Primary_0= ruleVariable_Primary |
	// this_Variable_Adapter_1= ruleVariable_Adapter ) ( () otherlv_3=
	// LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma (
	// (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )? ) ;
	public final EObject ruleVariable_Subscript() throws RecognitionException {
		EObject current = null;

		Token otherlv_3 = null;
		Token otherlv_5 = null;
		Token otherlv_7 = null;
		EObject this_Variable_Primary_0 = null;

		EObject this_Variable_Adapter_1 = null;

		EObject lv_index_4_0 = null;

		EObject lv_index_6_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2539:2: ( ( (this_Variable_Primary_0=
			// ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( ()
			// otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5=
			// Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )?
			// ) )
			// InternalStructuredTextParser.g:2540:2: ( (this_Variable_Primary_0=
			// ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( ()
			// otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5=
			// Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )?
			// )
			{
				// InternalStructuredTextParser.g:2540:2: ( (this_Variable_Primary_0=
				// ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( ()
				// otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5=
				// Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )?
				// )
				// InternalStructuredTextParser.g:2541:3: (this_Variable_Primary_0=
				// ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter ) ( ()
				// otherlv_3= LeftSquareBracket ( (lv_index_4_0= ruleExpression ) ) (otherlv_5=
				// Comma ( (lv_index_6_0= ruleExpression ) ) )* otherlv_7= RightSquareBracket )?
				{
					// InternalStructuredTextParser.g:2541:3: (this_Variable_Primary_0=
					// ruleVariable_Primary | this_Variable_Adapter_1= ruleVariable_Adapter )
					int alt37 = 2;
					switch (input.LA(1)) {
					case RULE_ID: {
						int LA37_1 = input.LA(2);

						if ((LA37_1 == EOF || LA37_1 == END_REPEAT || LA37_1 == THEN || (LA37_1 >= B && LA37_1 <= AND)
								|| LA37_1 == MOD || (LA37_1 >= XOR && LA37_1 <= LessThanSignGreaterThanSign)
								|| LA37_1 == GreaterThanSignEqualsSign || (LA37_1 >= BY && LA37_1 <= DO)
								|| (LA37_1 >= OF && LA37_1 <= TO) || LA37_1 == Ampersand
								|| (LA37_1 >= RightParenthesis && LA37_1 <= HyphenMinus)
								|| (LA37_1 >= Solidus && LA37_1 <= GreaterThanSign)
								|| (LA37_1 >= LeftSquareBracket && LA37_1 <= RightSquareBracket))) {
							alt37 = 1;
						} else if ((LA37_1 == FullStop)) {
							int LA37_6 = input.LA(3);

							if ((LA37_6 == DT || LA37_6 == LT || LA37_6 == T || LA37_6 == RULE_ID)) {
								alt37 = 2;
							} else if ((LA37_6 == RULE_UNSIGNED_INT)) {
								alt37 = 1;
							} else {
								NoViableAltException nvae = new NoViableAltException("", 37, 6, input);

								throw nvae;
							}
						} else {
							NoViableAltException nvae = new NoViableAltException("", 37, 1, input);

							throw nvae;
						}
					}
						break;
					case T: {
						int LA37_2 = input.LA(2);

						if ((LA37_2 == EOF || LA37_2 == END_REPEAT || LA37_2 == THEN || (LA37_2 >= B && LA37_2 <= AND)
								|| LA37_2 == MOD || (LA37_2 >= XOR && LA37_2 <= LessThanSignGreaterThanSign)
								|| LA37_2 == GreaterThanSignEqualsSign || (LA37_2 >= BY && LA37_2 <= DO)
								|| (LA37_2 >= OF && LA37_2 <= TO) || LA37_2 == Ampersand
								|| (LA37_2 >= RightParenthesis && LA37_2 <= HyphenMinus)
								|| (LA37_2 >= Solidus && LA37_2 <= GreaterThanSign)
								|| (LA37_2 >= LeftSquareBracket && LA37_2 <= RightSquareBracket))) {
							alt37 = 1;
						} else if ((LA37_2 == FullStop)) {
							int LA37_6 = input.LA(3);

							if ((LA37_6 == DT || LA37_6 == LT || LA37_6 == T || LA37_6 == RULE_ID)) {
								alt37 = 2;
							} else if ((LA37_6 == RULE_UNSIGNED_INT)) {
								alt37 = 1;
							} else {
								NoViableAltException nvae = new NoViableAltException("", 37, 6, input);

								throw nvae;
							}
						} else {
							NoViableAltException nvae = new NoViableAltException("", 37, 2, input);

							throw nvae;
						}
					}
						break;
					case LT: {
						int LA37_3 = input.LA(2);

						if ((LA37_3 == EOF || LA37_3 == END_REPEAT || LA37_3 == THEN || (LA37_3 >= B && LA37_3 <= AND)
								|| LA37_3 == MOD || (LA37_3 >= XOR && LA37_3 <= LessThanSignGreaterThanSign)
								|| LA37_3 == GreaterThanSignEqualsSign || (LA37_3 >= BY && LA37_3 <= DO)
								|| (LA37_3 >= OF && LA37_3 <= TO) || LA37_3 == Ampersand
								|| (LA37_3 >= RightParenthesis && LA37_3 <= HyphenMinus)
								|| (LA37_3 >= Solidus && LA37_3 <= GreaterThanSign)
								|| (LA37_3 >= LeftSquareBracket && LA37_3 <= RightSquareBracket))) {
							alt37 = 1;
						} else if ((LA37_3 == FullStop)) {
							int LA37_6 = input.LA(3);

							if ((LA37_6 == DT || LA37_6 == LT || LA37_6 == T || LA37_6 == RULE_ID)) {
								alt37 = 2;
							} else if ((LA37_6 == RULE_UNSIGNED_INT)) {
								alt37 = 1;
							} else {
								NoViableAltException nvae = new NoViableAltException("", 37, 6, input);

								throw nvae;
							}
						} else {
							NoViableAltException nvae = new NoViableAltException("", 37, 3, input);

							throw nvae;
						}
					}
						break;
					case DT: {
						int LA37_4 = input.LA(2);

						if ((LA37_4 == EOF || LA37_4 == END_REPEAT || LA37_4 == THEN || (LA37_4 >= B && LA37_4 <= AND)
								|| LA37_4 == MOD || (LA37_4 >= XOR && LA37_4 <= LessThanSignGreaterThanSign)
								|| LA37_4 == GreaterThanSignEqualsSign || (LA37_4 >= BY && LA37_4 <= DO)
								|| (LA37_4 >= OF && LA37_4 <= TO) || LA37_4 == Ampersand
								|| (LA37_4 >= RightParenthesis && LA37_4 <= HyphenMinus)
								|| (LA37_4 >= Solidus && LA37_4 <= GreaterThanSign)
								|| (LA37_4 >= LeftSquareBracket && LA37_4 <= RightSquareBracket))) {
							alt37 = 1;
						} else if ((LA37_4 == FullStop)) {
							int LA37_6 = input.LA(3);

							if ((LA37_6 == DT || LA37_6 == LT || LA37_6 == T || LA37_6 == RULE_ID)) {
								alt37 = 2;
							} else if ((LA37_6 == RULE_UNSIGNED_INT)) {
								alt37 = 1;
							} else {
								NoViableAltException nvae = new NoViableAltException("", 37, 6, input);

								throw nvae;
							}
						} else {
							NoViableAltException nvae = new NoViableAltException("", 37, 4, input);

							throw nvae;
						}
					}
						break;
					default:
						NoViableAltException nvae = new NoViableAltException("", 37, 0, input);

						throw nvae;
					}

					switch (alt37) {
					case 1:
					// InternalStructuredTextParser.g:2542:4: this_Variable_Primary_0=
					// ruleVariable_Primary
					{

						newCompositeNode(
								grammarAccess.getVariable_SubscriptAccess().getVariable_PrimaryParserRuleCall_0_0());

						pushFollow(FOLLOW_16);
						this_Variable_Primary_0 = ruleVariable_Primary();

						state._fsp--;

						current = this_Variable_Primary_0;
						afterParserOrEnumRuleCall();

					}
						break;
					case 2:
					// InternalStructuredTextParser.g:2551:4: this_Variable_Adapter_1=
					// ruleVariable_Adapter
					{

						newCompositeNode(
								grammarAccess.getVariable_SubscriptAccess().getVariable_AdapterParserRuleCall_0_1());

						pushFollow(FOLLOW_16);
						this_Variable_Adapter_1 = ruleVariable_Adapter();

						state._fsp--;

						current = this_Variable_Adapter_1;
						afterParserOrEnumRuleCall();

					}
						break;

					}

					// InternalStructuredTextParser.g:2560:3: ( () otherlv_3= LeftSquareBracket (
					// (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0=
					// ruleExpression ) ) )* otherlv_7= RightSquareBracket )?
					int alt39 = 2;
					int LA39_0 = input.LA(1);

					if ((LA39_0 == LeftSquareBracket)) {
						alt39 = 1;
					}
					switch (alt39) {
					case 1:
					// InternalStructuredTextParser.g:2561:4: () otherlv_3= LeftSquareBracket (
					// (lv_index_4_0= ruleExpression ) ) (otherlv_5= Comma ( (lv_index_6_0=
					// ruleExpression ) ) )* otherlv_7= RightSquareBracket
					{
						// InternalStructuredTextParser.g:2561:4: ()
						// InternalStructuredTextParser.g:2562:5:
						{

							current = forceCreateModelElementAndSet(
									grammarAccess.getVariable_SubscriptAccess().getArrayVariableArrayAction_1_0(),
									current);

						}

						otherlv_3 = (Token) match(input, LeftSquareBracket, FOLLOW_19);

						newLeafNode(otherlv_3,
								grammarAccess.getVariable_SubscriptAccess().getLeftSquareBracketKeyword_1_1());

						// InternalStructuredTextParser.g:2572:4: ( (lv_index_4_0= ruleExpression ) )
						// InternalStructuredTextParser.g:2573:5: (lv_index_4_0= ruleExpression )
						{
							// InternalStructuredTextParser.g:2573:5: (lv_index_4_0= ruleExpression )
							// InternalStructuredTextParser.g:2574:6: lv_index_4_0= ruleExpression
							{

								newCompositeNode(grammarAccess.getVariable_SubscriptAccess()
										.getIndexExpressionParserRuleCall_1_2_0());

								pushFollow(FOLLOW_53);
								lv_index_4_0 = ruleExpression();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getVariable_SubscriptRule());
								}
								add(current, "index", lv_index_4_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
								afterParserOrEnumRuleCall();

							}

						}

						// InternalStructuredTextParser.g:2591:4: (otherlv_5= Comma ( (lv_index_6_0=
						// ruleExpression ) ) )*
						loop38: do {
							int alt38 = 2;
							int LA38_0 = input.LA(1);

							if ((LA38_0 == Comma)) {
								alt38 = 1;
							}

							switch (alt38) {
							case 1:
							// InternalStructuredTextParser.g:2592:5: otherlv_5= Comma ( (lv_index_6_0=
							// ruleExpression ) )
							{
								otherlv_5 = (Token) match(input, Comma, FOLLOW_19);

								newLeafNode(otherlv_5,
										grammarAccess.getVariable_SubscriptAccess().getCommaKeyword_1_3_0());

								// InternalStructuredTextParser.g:2596:5: ( (lv_index_6_0= ruleExpression ) )
								// InternalStructuredTextParser.g:2597:6: (lv_index_6_0= ruleExpression )
								{
									// InternalStructuredTextParser.g:2597:6: (lv_index_6_0= ruleExpression )
									// InternalStructuredTextParser.g:2598:7: lv_index_6_0= ruleExpression
									{

										newCompositeNode(grammarAccess.getVariable_SubscriptAccess()
												.getIndexExpressionParserRuleCall_1_3_1_0());

										pushFollow(FOLLOW_53);
										lv_index_6_0 = ruleExpression();

										state._fsp--;

										if (current == null) {
											current = createModelElementForParent(
													grammarAccess.getVariable_SubscriptRule());
										}
										add(current, "index", lv_index_6_0,
												"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Expression");
										afterParserOrEnumRuleCall();

									}

								}

							}
								break;

							default:
								break loop38;
							}
						} while (true);

						otherlv_7 = (Token) match(input, RightSquareBracket, FOLLOW_2);

						newLeafNode(otherlv_7,
								grammarAccess.getVariable_SubscriptAccess().getRightSquareBracketKeyword_1_4());

					}
						break;

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleVariable_Subscript"

	// $ANTLR start "entryRuleVariable_Adapter"
	// InternalStructuredTextParser.g:2625:1: entryRuleVariable_Adapter returns
	// [EObject current=null] : iv_ruleVariable_Adapter= ruleVariable_Adapter EOF ;
	public final EObject entryRuleVariable_Adapter() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleVariable_Adapter = null;

		try {
			// InternalStructuredTextParser.g:2625:57: (iv_ruleVariable_Adapter=
			// ruleVariable_Adapter EOF )
			// InternalStructuredTextParser.g:2626:2: iv_ruleVariable_Adapter=
			// ruleVariable_Adapter EOF
			{
				newCompositeNode(grammarAccess.getVariable_AdapterRule());
				pushFollow(FOLLOW_1);
				iv_ruleVariable_Adapter = ruleVariable_Adapter();

				state._fsp--;

				current = iv_ruleVariable_Adapter;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleVariable_Adapter"

	// $ANTLR start "ruleVariable_Adapter"
	// InternalStructuredTextParser.g:2632:1: ruleVariable_Adapter returns [EObject
	// current=null] : ( () ( ( ruleAdapter_Name ) ) otherlv_2= FullStop ( (
	// ruleVariable_Name ) ) ) ;
	public final EObject ruleVariable_Adapter() throws RecognitionException {
		EObject current = null;

		Token otherlv_2 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2638:2: ( ( () ( ( ruleAdapter_Name ) )
			// otherlv_2= FullStop ( ( ruleVariable_Name ) ) ) )
			// InternalStructuredTextParser.g:2639:2: ( () ( ( ruleAdapter_Name ) )
			// otherlv_2= FullStop ( ( ruleVariable_Name ) ) )
			{
				// InternalStructuredTextParser.g:2639:2: ( () ( ( ruleAdapter_Name ) )
				// otherlv_2= FullStop ( ( ruleVariable_Name ) ) )
				// InternalStructuredTextParser.g:2640:3: () ( ( ruleAdapter_Name ) ) otherlv_2=
				// FullStop ( ( ruleVariable_Name ) )
				{
					// InternalStructuredTextParser.g:2640:3: ()
					// InternalStructuredTextParser.g:2641:4:
					{

						current = forceCreateModelElement(
								grammarAccess.getVariable_AdapterAccess().getAdapterVariableAction_0(), current);

					}

					// InternalStructuredTextParser.g:2647:3: ( ( ruleAdapter_Name ) )
					// InternalStructuredTextParser.g:2648:4: ( ruleAdapter_Name )
					{
						// InternalStructuredTextParser.g:2648:4: ( ruleAdapter_Name )
						// InternalStructuredTextParser.g:2649:5: ruleAdapter_Name
						{

							if (current == null) {
								current = createModelElement(grammarAccess.getVariable_AdapterRule());
							}

							newCompositeNode(grammarAccess.getVariable_AdapterAccess()
									.getAdapterAdapterDeclarationCrossReference_1_0());

							pushFollow(FOLLOW_54);
							ruleAdapter_Name();

							state._fsp--;

							afterParserOrEnumRuleCall();

						}

					}

					otherlv_2 = (Token) match(input, FullStop, FOLLOW_15);

					newLeafNode(otherlv_2, grammarAccess.getVariable_AdapterAccess().getFullStopKeyword_2());

					// InternalStructuredTextParser.g:2667:3: ( ( ruleVariable_Name ) )
					// InternalStructuredTextParser.g:2668:4: ( ruleVariable_Name )
					{
						// InternalStructuredTextParser.g:2668:4: ( ruleVariable_Name )
						// InternalStructuredTextParser.g:2669:5: ruleVariable_Name
						{

							if (current == null) {
								current = createModelElement(grammarAccess.getVariable_AdapterRule());
							}

							newCompositeNode(
									grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationCrossReference_3_0());

							pushFollow(FOLLOW_2);
							ruleVariable_Name();

							state._fsp--;

							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleVariable_Adapter"

	// $ANTLR start "entryRuleMultibit_Part_Access"
	// InternalStructuredTextParser.g:2687:1: entryRuleMultibit_Part_Access returns
	// [EObject current=null] : iv_ruleMultibit_Part_Access=
	// ruleMultibit_Part_Access EOF ;
	public final EObject entryRuleMultibit_Part_Access() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleMultibit_Part_Access = null;

		try {
			// InternalStructuredTextParser.g:2687:61: (iv_ruleMultibit_Part_Access=
			// ruleMultibit_Part_Access EOF )
			// InternalStructuredTextParser.g:2688:2: iv_ruleMultibit_Part_Access=
			// ruleMultibit_Part_Access EOF
			{
				newCompositeNode(grammarAccess.getMultibit_Part_AccessRule());
				pushFollow(FOLLOW_1);
				iv_ruleMultibit_Part_Access = ruleMultibit_Part_Access();

				state._fsp--;

				current = iv_ruleMultibit_Part_Access;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleMultibit_Part_Access"

	// $ANTLR start "ruleMultibit_Part_Access"
	// InternalStructuredTextParser.g:2694:1: ruleMultibit_Part_Access returns
	// [EObject current=null] : ( ( ( (lv_dwordaccess_0_0= D ) ) ( (lv_index_1_0=
	// rulePartial_Value ) ) ) | ( ( (lv_wordaccess_2_0= W ) ) ( (lv_index_3_0=
	// rulePartial_Value ) ) ) | ( ( (lv_byteaccess_4_0= B ) ) ( (lv_index_5_0=
	// rulePartial_Value ) ) ) | ( ( (lv_bitaccess_6_0= X ) ) ( (lv_index_7_0=
	// rulePartial_Value ) ) ) | ( ( (lv_bitaccess_8_0= FullStop ) ) (
	// (lv_index_9_0= rulePartial_Value ) ) ) ) ;
	public final EObject ruleMultibit_Part_Access() throws RecognitionException {
		EObject current = null;

		Token lv_dwordaccess_0_0 = null;
		Token lv_wordaccess_2_0 = null;
		Token lv_byteaccess_4_0 = null;
		Token lv_bitaccess_6_0 = null;
		Token lv_bitaccess_8_0 = null;
		AntlrDatatypeRuleToken lv_index_1_0 = null;

		AntlrDatatypeRuleToken lv_index_3_0 = null;

		AntlrDatatypeRuleToken lv_index_5_0 = null;

		AntlrDatatypeRuleToken lv_index_7_0 = null;

		AntlrDatatypeRuleToken lv_index_9_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2700:2: ( ( ( ( (lv_dwordaccess_0_0= D ) ) (
			// (lv_index_1_0= rulePartial_Value ) ) ) | ( ( (lv_wordaccess_2_0= W ) ) (
			// (lv_index_3_0= rulePartial_Value ) ) ) | ( ( (lv_byteaccess_4_0= B ) ) (
			// (lv_index_5_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_6_0= X ) ) (
			// (lv_index_7_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_8_0= FullStop ) )
			// ( (lv_index_9_0= rulePartial_Value ) ) ) ) )
			// InternalStructuredTextParser.g:2701:2: ( ( ( (lv_dwordaccess_0_0= D ) ) (
			// (lv_index_1_0= rulePartial_Value ) ) ) | ( ( (lv_wordaccess_2_0= W ) ) (
			// (lv_index_3_0= rulePartial_Value ) ) ) | ( ( (lv_byteaccess_4_0= B ) ) (
			// (lv_index_5_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_6_0= X ) ) (
			// (lv_index_7_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_8_0= FullStop ) )
			// ( (lv_index_9_0= rulePartial_Value ) ) ) )
			{
				// InternalStructuredTextParser.g:2701:2: ( ( ( (lv_dwordaccess_0_0= D ) ) (
				// (lv_index_1_0= rulePartial_Value ) ) ) | ( ( (lv_wordaccess_2_0= W ) ) (
				// (lv_index_3_0= rulePartial_Value ) ) ) | ( ( (lv_byteaccess_4_0= B ) ) (
				// (lv_index_5_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_6_0= X ) ) (
				// (lv_index_7_0= rulePartial_Value ) ) ) | ( ( (lv_bitaccess_8_0= FullStop ) )
				// ( (lv_index_9_0= rulePartial_Value ) ) ) )
				int alt40 = 5;
				switch (input.LA(1)) {
				case D: {
					alt40 = 1;
				}
					break;
				case W: {
					alt40 = 2;
				}
					break;
				case B: {
					alt40 = 3;
				}
					break;
				case X: {
					alt40 = 4;
				}
					break;
				case FullStop: {
					alt40 = 5;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 40, 0, input);

					throw nvae;
				}

				switch (alt40) {
				case 1:
				// InternalStructuredTextParser.g:2702:3: ( ( (lv_dwordaccess_0_0= D ) ) (
				// (lv_index_1_0= rulePartial_Value ) ) )
				{
					// InternalStructuredTextParser.g:2702:3: ( ( (lv_dwordaccess_0_0= D ) ) (
					// (lv_index_1_0= rulePartial_Value ) ) )
					// InternalStructuredTextParser.g:2703:4: ( (lv_dwordaccess_0_0= D ) ) (
					// (lv_index_1_0= rulePartial_Value ) )
					{
						// InternalStructuredTextParser.g:2703:4: ( (lv_dwordaccess_0_0= D ) )
						// InternalStructuredTextParser.g:2704:5: (lv_dwordaccess_0_0= D )
						{
							// InternalStructuredTextParser.g:2704:5: (lv_dwordaccess_0_0= D )
							// InternalStructuredTextParser.g:2705:6: lv_dwordaccess_0_0= D
							{
								lv_dwordaccess_0_0 = (Token) match(input, D, FOLLOW_10);

								newLeafNode(lv_dwordaccess_0_0,
										grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getMultibit_Part_AccessRule());
								}
								setWithLastConsumed(current, "dwordaccess", true, ".%D");

							}

						}

						// InternalStructuredTextParser.g:2717:4: ( (lv_index_1_0= rulePartial_Value ) )
						// InternalStructuredTextParser.g:2718:5: (lv_index_1_0= rulePartial_Value )
						{
							// InternalStructuredTextParser.g:2718:5: (lv_index_1_0= rulePartial_Value )
							// InternalStructuredTextParser.g:2719:6: lv_index_1_0= rulePartial_Value
							{

								newCompositeNode(grammarAccess.getMultibit_Part_AccessAccess()
										.getIndexPartial_ValueParserRuleCall_0_1_0());

								pushFollow(FOLLOW_2);
								lv_index_1_0 = rulePartial_Value();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getMultibit_Part_AccessRule());
								}
								set(current, "index", lv_index_1_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Partial_Value");
								afterParserOrEnumRuleCall();

							}

						}

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:2738:3: ( ( (lv_wordaccess_2_0= W ) ) (
				// (lv_index_3_0= rulePartial_Value ) ) )
				{
					// InternalStructuredTextParser.g:2738:3: ( ( (lv_wordaccess_2_0= W ) ) (
					// (lv_index_3_0= rulePartial_Value ) ) )
					// InternalStructuredTextParser.g:2739:4: ( (lv_wordaccess_2_0= W ) ) (
					// (lv_index_3_0= rulePartial_Value ) )
					{
						// InternalStructuredTextParser.g:2739:4: ( (lv_wordaccess_2_0= W ) )
						// InternalStructuredTextParser.g:2740:5: (lv_wordaccess_2_0= W )
						{
							// InternalStructuredTextParser.g:2740:5: (lv_wordaccess_2_0= W )
							// InternalStructuredTextParser.g:2741:6: lv_wordaccess_2_0= W
							{
								lv_wordaccess_2_0 = (Token) match(input, W, FOLLOW_10);

								newLeafNode(lv_wordaccess_2_0,
										grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getMultibit_Part_AccessRule());
								}
								setWithLastConsumed(current, "wordaccess", true, ".%W");

							}

						}

						// InternalStructuredTextParser.g:2753:4: ( (lv_index_3_0= rulePartial_Value ) )
						// InternalStructuredTextParser.g:2754:5: (lv_index_3_0= rulePartial_Value )
						{
							// InternalStructuredTextParser.g:2754:5: (lv_index_3_0= rulePartial_Value )
							// InternalStructuredTextParser.g:2755:6: lv_index_3_0= rulePartial_Value
							{

								newCompositeNode(grammarAccess.getMultibit_Part_AccessAccess()
										.getIndexPartial_ValueParserRuleCall_1_1_0());

								pushFollow(FOLLOW_2);
								lv_index_3_0 = rulePartial_Value();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getMultibit_Part_AccessRule());
								}
								set(current, "index", lv_index_3_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Partial_Value");
								afterParserOrEnumRuleCall();

							}

						}

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:2774:3: ( ( (lv_byteaccess_4_0= B ) ) (
				// (lv_index_5_0= rulePartial_Value ) ) )
				{
					// InternalStructuredTextParser.g:2774:3: ( ( (lv_byteaccess_4_0= B ) ) (
					// (lv_index_5_0= rulePartial_Value ) ) )
					// InternalStructuredTextParser.g:2775:4: ( (lv_byteaccess_4_0= B ) ) (
					// (lv_index_5_0= rulePartial_Value ) )
					{
						// InternalStructuredTextParser.g:2775:4: ( (lv_byteaccess_4_0= B ) )
						// InternalStructuredTextParser.g:2776:5: (lv_byteaccess_4_0= B )
						{
							// InternalStructuredTextParser.g:2776:5: (lv_byteaccess_4_0= B )
							// InternalStructuredTextParser.g:2777:6: lv_byteaccess_4_0= B
							{
								lv_byteaccess_4_0 = (Token) match(input, B, FOLLOW_10);

								newLeafNode(lv_byteaccess_4_0,
										grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getMultibit_Part_AccessRule());
								}
								setWithLastConsumed(current, "byteaccess", true, ".%B");

							}

						}

						// InternalStructuredTextParser.g:2789:4: ( (lv_index_5_0= rulePartial_Value ) )
						// InternalStructuredTextParser.g:2790:5: (lv_index_5_0= rulePartial_Value )
						{
							// InternalStructuredTextParser.g:2790:5: (lv_index_5_0= rulePartial_Value )
							// InternalStructuredTextParser.g:2791:6: lv_index_5_0= rulePartial_Value
							{

								newCompositeNode(grammarAccess.getMultibit_Part_AccessAccess()
										.getIndexPartial_ValueParserRuleCall_2_1_0());

								pushFollow(FOLLOW_2);
								lv_index_5_0 = rulePartial_Value();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getMultibit_Part_AccessRule());
								}
								set(current, "index", lv_index_5_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Partial_Value");
								afterParserOrEnumRuleCall();

							}

						}

					}

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:2810:3: ( ( (lv_bitaccess_6_0= X ) ) (
				// (lv_index_7_0= rulePartial_Value ) ) )
				{
					// InternalStructuredTextParser.g:2810:3: ( ( (lv_bitaccess_6_0= X ) ) (
					// (lv_index_7_0= rulePartial_Value ) ) )
					// InternalStructuredTextParser.g:2811:4: ( (lv_bitaccess_6_0= X ) ) (
					// (lv_index_7_0= rulePartial_Value ) )
					{
						// InternalStructuredTextParser.g:2811:4: ( (lv_bitaccess_6_0= X ) )
						// InternalStructuredTextParser.g:2812:5: (lv_bitaccess_6_0= X )
						{
							// InternalStructuredTextParser.g:2812:5: (lv_bitaccess_6_0= X )
							// InternalStructuredTextParser.g:2813:6: lv_bitaccess_6_0= X
							{
								lv_bitaccess_6_0 = (Token) match(input, X, FOLLOW_10);

								newLeafNode(lv_bitaccess_6_0,
										grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getMultibit_Part_AccessRule());
								}
								setWithLastConsumed(current, "bitaccess", true, ".%X");

							}

						}

						// InternalStructuredTextParser.g:2825:4: ( (lv_index_7_0= rulePartial_Value ) )
						// InternalStructuredTextParser.g:2826:5: (lv_index_7_0= rulePartial_Value )
						{
							// InternalStructuredTextParser.g:2826:5: (lv_index_7_0= rulePartial_Value )
							// InternalStructuredTextParser.g:2827:6: lv_index_7_0= rulePartial_Value
							{

								newCompositeNode(grammarAccess.getMultibit_Part_AccessAccess()
										.getIndexPartial_ValueParserRuleCall_3_1_0());

								pushFollow(FOLLOW_2);
								lv_index_7_0 = rulePartial_Value();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getMultibit_Part_AccessRule());
								}
								set(current, "index", lv_index_7_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Partial_Value");
								afterParserOrEnumRuleCall();

							}

						}

					}

				}
					break;
				case 5:
				// InternalStructuredTextParser.g:2846:3: ( ( (lv_bitaccess_8_0= FullStop ) ) (
				// (lv_index_9_0= rulePartial_Value ) ) )
				{
					// InternalStructuredTextParser.g:2846:3: ( ( (lv_bitaccess_8_0= FullStop ) ) (
					// (lv_index_9_0= rulePartial_Value ) ) )
					// InternalStructuredTextParser.g:2847:4: ( (lv_bitaccess_8_0= FullStop ) ) (
					// (lv_index_9_0= rulePartial_Value ) )
					{
						// InternalStructuredTextParser.g:2847:4: ( (lv_bitaccess_8_0= FullStop ) )
						// InternalStructuredTextParser.g:2848:5: (lv_bitaccess_8_0= FullStop )
						{
							// InternalStructuredTextParser.g:2848:5: (lv_bitaccess_8_0= FullStop )
							// InternalStructuredTextParser.g:2849:6: lv_bitaccess_8_0= FullStop
							{
								lv_bitaccess_8_0 = (Token) match(input, FullStop, FOLLOW_10);

								newLeafNode(lv_bitaccess_8_0, grammarAccess.getMultibit_Part_AccessAccess()
										.getBitaccessFullStopKeyword_4_0_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getMultibit_Part_AccessRule());
								}
								setWithLastConsumed(current, "bitaccess", true, ".");

							}

						}

						// InternalStructuredTextParser.g:2861:4: ( (lv_index_9_0= rulePartial_Value ) )
						// InternalStructuredTextParser.g:2862:5: (lv_index_9_0= rulePartial_Value )
						{
							// InternalStructuredTextParser.g:2862:5: (lv_index_9_0= rulePartial_Value )
							// InternalStructuredTextParser.g:2863:6: lv_index_9_0= rulePartial_Value
							{

								newCompositeNode(grammarAccess.getMultibit_Part_AccessAccess()
										.getIndexPartial_ValueParserRuleCall_4_1_0());

								pushFollow(FOLLOW_2);
								lv_index_9_0 = rulePartial_Value();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getMultibit_Part_AccessRule());
								}
								set(current, "index", lv_index_9_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Partial_Value");
								afterParserOrEnumRuleCall();

							}

						}

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleMultibit_Part_Access"

	// $ANTLR start "entryRuleAdapter_Name"
	// InternalStructuredTextParser.g:2885:1: entryRuleAdapter_Name returns [String
	// current=null] : iv_ruleAdapter_Name= ruleAdapter_Name EOF ;
	public final String entryRuleAdapter_Name() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleAdapter_Name = null;

		try {
			// InternalStructuredTextParser.g:2885:52: (iv_ruleAdapter_Name=
			// ruleAdapter_Name EOF )
			// InternalStructuredTextParser.g:2886:2: iv_ruleAdapter_Name= ruleAdapter_Name
			// EOF
			{
				newCompositeNode(grammarAccess.getAdapter_NameRule());
				pushFollow(FOLLOW_1);
				iv_ruleAdapter_Name = ruleAdapter_Name();

				state._fsp--;

				current = iv_ruleAdapter_Name.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleAdapter_Name"

	// $ANTLR start "ruleAdapter_Name"
	// InternalStructuredTextParser.g:2892:1: ruleAdapter_Name returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0=
	// RULE_ID | kw= T | kw= LT | kw= DT ) ;
	public final AntlrDatatypeRuleToken ruleAdapter_Name() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_ID_0 = null;
		Token kw = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2898:2: ( (this_ID_0= RULE_ID | kw= T | kw= LT
			// | kw= DT ) )
			// InternalStructuredTextParser.g:2899:2: (this_ID_0= RULE_ID | kw= T | kw= LT |
			// kw= DT )
			{
				// InternalStructuredTextParser.g:2899:2: (this_ID_0= RULE_ID | kw= T | kw= LT |
				// kw= DT )
				int alt41 = 4;
				switch (input.LA(1)) {
				case RULE_ID: {
					alt41 = 1;
				}
					break;
				case T: {
					alt41 = 2;
				}
					break;
				case LT: {
					alt41 = 3;
				}
					break;
				case DT: {
					alt41 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 41, 0, input);

					throw nvae;
				}

				switch (alt41) {
				case 1:
				// InternalStructuredTextParser.g:2900:3: this_ID_0= RULE_ID
				{
					this_ID_0 = (Token) match(input, RULE_ID, FOLLOW_2);

					current.merge(this_ID_0);

					newLeafNode(this_ID_0, grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0());

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:2908:3: kw= T
				{
					kw = (Token) match(input, T, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getAdapter_NameAccess().getTKeyword_1());

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:2914:3: kw= LT
				{
					kw = (Token) match(input, LT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getAdapter_NameAccess().getLTKeyword_2());

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:2920:3: kw= DT
				{
					kw = (Token) match(input, DT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getAdapter_NameAccess().getDTKeyword_3());

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleAdapter_Name"

	// $ANTLR start "entryRuleVariable_Primary"
	// InternalStructuredTextParser.g:2929:1: entryRuleVariable_Primary returns
	// [EObject current=null] : iv_ruleVariable_Primary= ruleVariable_Primary EOF ;
	public final EObject entryRuleVariable_Primary() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleVariable_Primary = null;

		try {
			// InternalStructuredTextParser.g:2929:57: (iv_ruleVariable_Primary=
			// ruleVariable_Primary EOF )
			// InternalStructuredTextParser.g:2930:2: iv_ruleVariable_Primary=
			// ruleVariable_Primary EOF
			{
				newCompositeNode(grammarAccess.getVariable_PrimaryRule());
				pushFollow(FOLLOW_1);
				iv_ruleVariable_Primary = ruleVariable_Primary();

				state._fsp--;

				current = iv_ruleVariable_Primary;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleVariable_Primary"

	// $ANTLR start "ruleVariable_Primary"
	// InternalStructuredTextParser.g:2936:1: ruleVariable_Primary returns [EObject
	// current=null] : ( ( ruleVariable_Name ) ) ;
	public final EObject ruleVariable_Primary() throws RecognitionException {
		EObject current = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2942:2: ( ( ( ruleVariable_Name ) ) )
			// InternalStructuredTextParser.g:2943:2: ( ( ruleVariable_Name ) )
			{
				// InternalStructuredTextParser.g:2943:2: ( ( ruleVariable_Name ) )
				// InternalStructuredTextParser.g:2944:3: ( ruleVariable_Name )
				{
					// InternalStructuredTextParser.g:2944:3: ( ruleVariable_Name )
					// InternalStructuredTextParser.g:2945:4: ruleVariable_Name
					{

						if (current == null) {
							current = createModelElement(grammarAccess.getVariable_PrimaryRule());
						}

						newCompositeNode(
								grammarAccess.getVariable_PrimaryAccess().getVarVarDeclarationCrossReference_0());

						pushFollow(FOLLOW_2);
						ruleVariable_Name();

						state._fsp--;

						afterParserOrEnumRuleCall();

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleVariable_Primary"

	// $ANTLR start "entryRuleVariable_Name"
	// InternalStructuredTextParser.g:2962:1: entryRuleVariable_Name returns [String
	// current=null] : iv_ruleVariable_Name= ruleVariable_Name EOF ;
	public final String entryRuleVariable_Name() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleVariable_Name = null;

		try {
			// InternalStructuredTextParser.g:2962:53: (iv_ruleVariable_Name=
			// ruleVariable_Name EOF )
			// InternalStructuredTextParser.g:2963:2: iv_ruleVariable_Name=
			// ruleVariable_Name EOF
			{
				newCompositeNode(grammarAccess.getVariable_NameRule());
				pushFollow(FOLLOW_1);
				iv_ruleVariable_Name = ruleVariable_Name();

				state._fsp--;

				current = iv_ruleVariable_Name.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleVariable_Name"

	// $ANTLR start "ruleVariable_Name"
	// InternalStructuredTextParser.g:2969:1: ruleVariable_Name returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0=
	// RULE_ID | kw= T | kw= LT | kw= DT ) ;
	public final AntlrDatatypeRuleToken ruleVariable_Name() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_ID_0 = null;
		Token kw = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:2975:2: ( (this_ID_0= RULE_ID | kw= T | kw= LT
			// | kw= DT ) )
			// InternalStructuredTextParser.g:2976:2: (this_ID_0= RULE_ID | kw= T | kw= LT |
			// kw= DT )
			{
				// InternalStructuredTextParser.g:2976:2: (this_ID_0= RULE_ID | kw= T | kw= LT |
				// kw= DT )
				int alt42 = 4;
				switch (input.LA(1)) {
				case RULE_ID: {
					alt42 = 1;
				}
					break;
				case T: {
					alt42 = 2;
				}
					break;
				case LT: {
					alt42 = 3;
				}
					break;
				case DT: {
					alt42 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 42, 0, input);

					throw nvae;
				}

				switch (alt42) {
				case 1:
				// InternalStructuredTextParser.g:2977:3: this_ID_0= RULE_ID
				{
					this_ID_0 = (Token) match(input, RULE_ID, FOLLOW_2);

					current.merge(this_ID_0);

					newLeafNode(this_ID_0, grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0());

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:2985:3: kw= T
				{
					kw = (Token) match(input, T, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getVariable_NameAccess().getTKeyword_1());

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:2991:3: kw= LT
				{
					kw = (Token) match(input, LT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getVariable_NameAccess().getLTKeyword_2());

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:2997:3: kw= DT
				{
					kw = (Token) match(input, DT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getVariable_NameAccess().getDTKeyword_3());

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleVariable_Name"

	// $ANTLR start "entryRuleConstant"
	// InternalStructuredTextParser.g:3006:1: entryRuleConstant returns [EObject
	// current=null] : iv_ruleConstant= ruleConstant EOF ;
	public final EObject entryRuleConstant() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleConstant = null;

		try {
			// InternalStructuredTextParser.g:3006:49: (iv_ruleConstant= ruleConstant EOF )
			// InternalStructuredTextParser.g:3007:2: iv_ruleConstant= ruleConstant EOF
			{
				newCompositeNode(grammarAccess.getConstantRule());
				pushFollow(FOLLOW_1);
				iv_ruleConstant = ruleConstant();

				state._fsp--;

				current = iv_ruleConstant;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleConstant"

	// $ANTLR start "ruleConstant"
	// InternalStructuredTextParser.g:3013:1: ruleConstant returns [EObject
	// current=null] : (this_Numeric_Literal_0= ruleNumeric_Literal |
	// this_Char_Literal_1= ruleChar_Literal | this_Time_Literal_2= ruleTime_Literal
	// | this_Bool_Literal_3= ruleBool_Literal ) ;
	public final EObject ruleConstant() throws RecognitionException {
		EObject current = null;

		EObject this_Numeric_Literal_0 = null;

		EObject this_Char_Literal_1 = null;

		EObject this_Time_Literal_2 = null;

		EObject this_Bool_Literal_3 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3019:2: ( (this_Numeric_Literal_0=
			// ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal |
			// this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal
			// ) )
			// InternalStructuredTextParser.g:3020:2: (this_Numeric_Literal_0=
			// ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal |
			// this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal
			// )
			{
				// InternalStructuredTextParser.g:3020:2: (this_Numeric_Literal_0=
				// ruleNumeric_Literal | this_Char_Literal_1= ruleChar_Literal |
				// this_Time_Literal_2= ruleTime_Literal | this_Bool_Literal_3= ruleBool_Literal
				// )
				int alt43 = 4;
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
					alt43 = 1;
				}
					break;
				case WSTRING:
				case STRING:
				case WCHAR:
				case CHAR:
				case RULE_S_BYTE_CHAR_STR:
				case RULE_D_BYTE_CHAR_STR: {
					alt43 = 2;
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
					alt43 = 3;
				}
					break;
				case FALSE:
				case BOOL:
				case TRUE: {
					alt43 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 43, 0, input);

					throw nvae;
				}

				switch (alt43) {
				case 1:
				// InternalStructuredTextParser.g:3021:3: this_Numeric_Literal_0=
				// ruleNumeric_Literal
				{

					newCompositeNode(grammarAccess.getConstantAccess().getNumeric_LiteralParserRuleCall_0());

					pushFollow(FOLLOW_2);
					this_Numeric_Literal_0 = ruleNumeric_Literal();

					state._fsp--;

					current = this_Numeric_Literal_0;
					afterParserOrEnumRuleCall();

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:3030:3: this_Char_Literal_1= ruleChar_Literal
				{

					newCompositeNode(grammarAccess.getConstantAccess().getChar_LiteralParserRuleCall_1());

					pushFollow(FOLLOW_2);
					this_Char_Literal_1 = ruleChar_Literal();

					state._fsp--;

					current = this_Char_Literal_1;
					afterParserOrEnumRuleCall();

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:3039:3: this_Time_Literal_2= ruleTime_Literal
				{

					newCompositeNode(grammarAccess.getConstantAccess().getTime_LiteralParserRuleCall_2());

					pushFollow(FOLLOW_2);
					this_Time_Literal_2 = ruleTime_Literal();

					state._fsp--;

					current = this_Time_Literal_2;
					afterParserOrEnumRuleCall();

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:3048:3: this_Bool_Literal_3= ruleBool_Literal
				{

					newCompositeNode(grammarAccess.getConstantAccess().getBool_LiteralParserRuleCall_3());

					pushFollow(FOLLOW_2);
					this_Bool_Literal_3 = ruleBool_Literal();

					state._fsp--;

					current = this_Bool_Literal_3;
					afterParserOrEnumRuleCall();

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleConstant"

	// $ANTLR start "entryRuleNumeric_Literal"
	// InternalStructuredTextParser.g:3060:1: entryRuleNumeric_Literal returns
	// [EObject current=null] : iv_ruleNumeric_Literal= ruleNumeric_Literal EOF ;
	public final EObject entryRuleNumeric_Literal() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleNumeric_Literal = null;

		try {
			// InternalStructuredTextParser.g:3060:56: (iv_ruleNumeric_Literal=
			// ruleNumeric_Literal EOF )
			// InternalStructuredTextParser.g:3061:2: iv_ruleNumeric_Literal=
			// ruleNumeric_Literal EOF
			{
				newCompositeNode(grammarAccess.getNumeric_LiteralRule());
				pushFollow(FOLLOW_1);
				iv_ruleNumeric_Literal = ruleNumeric_Literal();

				state._fsp--;

				current = iv_ruleNumeric_Literal;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleNumeric_Literal"

	// $ANTLR start "ruleNumeric_Literal"
	// InternalStructuredTextParser.g:3067:1: ruleNumeric_Literal returns [EObject
	// current=null] : (this_Int_Literal_0= ruleInt_Literal | this_Real_Literal_1=
	// ruleReal_Literal ) ;
	public final EObject ruleNumeric_Literal() throws RecognitionException {
		EObject current = null;

		EObject this_Int_Literal_0 = null;

		EObject this_Real_Literal_1 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3073:2: ( (this_Int_Literal_0= ruleInt_Literal
			// | this_Real_Literal_1= ruleReal_Literal ) )
			// InternalStructuredTextParser.g:3074:2: (this_Int_Literal_0= ruleInt_Literal |
			// this_Real_Literal_1= ruleReal_Literal )
			{
				// InternalStructuredTextParser.g:3074:2: (this_Int_Literal_0= ruleInt_Literal |
				// this_Real_Literal_1= ruleReal_Literal )
				int alt44 = 2;
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
					alt44 = 1;
				}
					break;
				case PlusSign: {
					int LA44_2 = input.LA(2);

					if ((LA44_2 == RULE_UNSIGNED_INT)) {
						int LA44_4 = input.LA(3);

						if ((LA44_4 == EOF || LA44_4 == END_REPEAT || LA44_4 == THEN || LA44_4 == AND || LA44_4 == MOD
								|| (LA44_4 >= XOR && LA44_4 <= AsteriskAsterisk)
								|| (LA44_4 >= LessThanSignEqualsSign && LA44_4 <= LessThanSignGreaterThanSign)
								|| LA44_4 == GreaterThanSignEqualsSign || (LA44_4 >= BY && LA44_4 <= DO)
								|| (LA44_4 >= OF && LA44_4 <= TO) || LA44_4 == Ampersand
								|| (LA44_4 >= RightParenthesis && LA44_4 <= HyphenMinus)
								|| (LA44_4 >= Solidus && LA44_4 <= GreaterThanSign) || LA44_4 == RightSquareBracket)) {
							alt44 = 1;
						} else if ((LA44_4 == FullStop)) {
							alt44 = 2;
						} else {
							NoViableAltException nvae = new NoViableAltException("", 44, 4, input);

							throw nvae;
						}
					} else {
						NoViableAltException nvae = new NoViableAltException("", 44, 2, input);

						throw nvae;
					}
				}
					break;
				case HyphenMinus: {
					int LA44_3 = input.LA(2);

					if ((LA44_3 == RULE_UNSIGNED_INT)) {
						int LA44_4 = input.LA(3);

						if ((LA44_4 == EOF || LA44_4 == END_REPEAT || LA44_4 == THEN || LA44_4 == AND || LA44_4 == MOD
								|| (LA44_4 >= XOR && LA44_4 <= AsteriskAsterisk)
								|| (LA44_4 >= LessThanSignEqualsSign && LA44_4 <= LessThanSignGreaterThanSign)
								|| LA44_4 == GreaterThanSignEqualsSign || (LA44_4 >= BY && LA44_4 <= DO)
								|| (LA44_4 >= OF && LA44_4 <= TO) || LA44_4 == Ampersand
								|| (LA44_4 >= RightParenthesis && LA44_4 <= HyphenMinus)
								|| (LA44_4 >= Solidus && LA44_4 <= GreaterThanSign) || LA44_4 == RightSquareBracket)) {
							alt44 = 1;
						} else if ((LA44_4 == FullStop)) {
							alt44 = 2;
						} else {
							NoViableAltException nvae = new NoViableAltException("", 44, 4, input);

							throw nvae;
						}
					} else {
						NoViableAltException nvae = new NoViableAltException("", 44, 3, input);

						throw nvae;
					}
				}
					break;
				case RULE_UNSIGNED_INT: {
					int LA44_4 = input.LA(2);

					if ((LA44_4 == EOF || LA44_4 == END_REPEAT || LA44_4 == THEN || LA44_4 == AND || LA44_4 == MOD
							|| (LA44_4 >= XOR && LA44_4 <= AsteriskAsterisk)
							|| (LA44_4 >= LessThanSignEqualsSign && LA44_4 <= LessThanSignGreaterThanSign)
							|| LA44_4 == GreaterThanSignEqualsSign || (LA44_4 >= BY && LA44_4 <= DO)
							|| (LA44_4 >= OF && LA44_4 <= TO) || LA44_4 == Ampersand
							|| (LA44_4 >= RightParenthesis && LA44_4 <= HyphenMinus)
							|| (LA44_4 >= Solidus && LA44_4 <= GreaterThanSign) || LA44_4 == RightSquareBracket)) {
						alt44 = 1;
					} else if ((LA44_4 == FullStop)) {
						alt44 = 2;
					} else {
						NoViableAltException nvae = new NoViableAltException("", 44, 4, input);

						throw nvae;
					}
				}
					break;
				case LREAL:
				case REAL: {
					alt44 = 2;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 44, 0, input);

					throw nvae;
				}

				switch (alt44) {
				case 1:
				// InternalStructuredTextParser.g:3075:3: this_Int_Literal_0= ruleInt_Literal
				{

					newCompositeNode(grammarAccess.getNumeric_LiteralAccess().getInt_LiteralParserRuleCall_0());

					pushFollow(FOLLOW_2);
					this_Int_Literal_0 = ruleInt_Literal();

					state._fsp--;

					current = this_Int_Literal_0;
					afterParserOrEnumRuleCall();

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:3084:3: this_Real_Literal_1= ruleReal_Literal
				{

					newCompositeNode(grammarAccess.getNumeric_LiteralAccess().getReal_LiteralParserRuleCall_1());

					pushFollow(FOLLOW_2);
					this_Real_Literal_1 = ruleReal_Literal();

					state._fsp--;

					current = this_Real_Literal_1;
					afterParserOrEnumRuleCall();

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleNumeric_Literal"

	// $ANTLR start "entryRuleInt_Literal"
	// InternalStructuredTextParser.g:3096:1: entryRuleInt_Literal returns [EObject
	// current=null] : iv_ruleInt_Literal= ruleInt_Literal EOF ;
	public final EObject entryRuleInt_Literal() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleInt_Literal = null;

		try {
			// InternalStructuredTextParser.g:3096:52: (iv_ruleInt_Literal= ruleInt_Literal
			// EOF )
			// InternalStructuredTextParser.g:3097:2: iv_ruleInt_Literal= ruleInt_Literal
			// EOF
			{
				newCompositeNode(grammarAccess.getInt_LiteralRule());
				pushFollow(FOLLOW_1);
				iv_ruleInt_Literal = ruleInt_Literal();

				state._fsp--;

				current = iv_ruleInt_Literal;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleInt_Literal"

	// $ANTLR start "ruleInt_Literal"
	// InternalStructuredTextParser.g:3103:1: ruleInt_Literal returns [EObject
	// current=null] : ( ( ( (lv_type_0_0= ruleInt_Type_Name ) ) otherlv_1=
	// NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2=
	// RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT )
	// ) ) ) ;
	public final EObject ruleInt_Literal() throws RecognitionException {
		EObject current = null;

		Token otherlv_1 = null;
		Token lv_value_2_2 = null;
		Token lv_value_2_3 = null;
		Token lv_value_2_4 = null;
		Enumerator lv_type_0_0 = null;

		AntlrDatatypeRuleToken lv_value_2_1 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3109:2: ( ( ( ( (lv_type_0_0=
			// ruleInt_Type_Name ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1=
			// ruleSigned_Int | lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT
			// | lv_value_2_4= RULE_HEX_INT ) ) ) ) )
			// InternalStructuredTextParser.g:3110:2: ( ( ( (lv_type_0_0= ruleInt_Type_Name
			// ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int |
			// lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4=
			// RULE_HEX_INT ) ) ) )
			{
				// InternalStructuredTextParser.g:3110:2: ( ( ( (lv_type_0_0= ruleInt_Type_Name
				// ) ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int |
				// lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4=
				// RULE_HEX_INT ) ) ) )
				// InternalStructuredTextParser.g:3111:3: ( ( (lv_type_0_0= ruleInt_Type_Name )
				// ) otherlv_1= NumberSign )? ( ( (lv_value_2_1= ruleSigned_Int | lv_value_2_2=
				// RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4= RULE_HEX_INT )
				// ) )
				{
					// InternalStructuredTextParser.g:3111:3: ( ( (lv_type_0_0= ruleInt_Type_Name )
					// ) otherlv_1= NumberSign )?
					int alt45 = 2;
					int LA45_0 = input.LA(1);

					if (((LA45_0 >= UDINT && LA45_0 <= ULINT) || LA45_0 == USINT || LA45_0 == DINT || LA45_0 == LINT
							|| LA45_0 == SINT || LA45_0 == UINT || LA45_0 == INT)) {
						alt45 = 1;
					}
					switch (alt45) {
					case 1:
					// InternalStructuredTextParser.g:3112:4: ( (lv_type_0_0= ruleInt_Type_Name ) )
					// otherlv_1= NumberSign
					{
						// InternalStructuredTextParser.g:3112:4: ( (lv_type_0_0= ruleInt_Type_Name ) )
						// InternalStructuredTextParser.g:3113:5: (lv_type_0_0= ruleInt_Type_Name )
						{
							// InternalStructuredTextParser.g:3113:5: (lv_type_0_0= ruleInt_Type_Name )
							// InternalStructuredTextParser.g:3114:6: lv_type_0_0= ruleInt_Type_Name
							{

								newCompositeNode(
										grammarAccess.getInt_LiteralAccess().getTypeInt_Type_NameEnumRuleCall_0_0_0());

								pushFollow(FOLLOW_55);
								lv_type_0_0 = ruleInt_Type_Name();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getInt_LiteralRule());
								}
								set(current, "type", lv_type_0_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Int_Type_Name");
								afterParserOrEnumRuleCall();

							}

						}

						otherlv_1 = (Token) match(input, NumberSign, FOLLOW_56);

						newLeafNode(otherlv_1, grammarAccess.getInt_LiteralAccess().getNumberSignKeyword_0_1());

					}
						break;

					}

					// InternalStructuredTextParser.g:3136:3: ( ( (lv_value_2_1= ruleSigned_Int |
					// lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4=
					// RULE_HEX_INT ) ) )
					// InternalStructuredTextParser.g:3137:4: ( (lv_value_2_1= ruleSigned_Int |
					// lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4=
					// RULE_HEX_INT ) )
					{
						// InternalStructuredTextParser.g:3137:4: ( (lv_value_2_1= ruleSigned_Int |
						// lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4=
						// RULE_HEX_INT ) )
						// InternalStructuredTextParser.g:3138:5: (lv_value_2_1= ruleSigned_Int |
						// lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4=
						// RULE_HEX_INT )
						{
							// InternalStructuredTextParser.g:3138:5: (lv_value_2_1= ruleSigned_Int |
							// lv_value_2_2= RULE_BINARY_INT | lv_value_2_3= RULE_OCTAL_INT | lv_value_2_4=
							// RULE_HEX_INT )
							int alt46 = 4;
							switch (input.LA(1)) {
							case PlusSign:
							case HyphenMinus:
							case RULE_UNSIGNED_INT: {
								alt46 = 1;
							}
								break;
							case RULE_BINARY_INT: {
								alt46 = 2;
							}
								break;
							case RULE_OCTAL_INT: {
								alt46 = 3;
							}
								break;
							case RULE_HEX_INT: {
								alt46 = 4;
							}
								break;
							default:
								NoViableAltException nvae = new NoViableAltException("", 46, 0, input);

								throw nvae;
							}

							switch (alt46) {
							case 1:
							// InternalStructuredTextParser.g:3139:6: lv_value_2_1= ruleSigned_Int
							{

								newCompositeNode(
										grammarAccess.getInt_LiteralAccess().getValueSigned_IntParserRuleCall_1_0_0());

								pushFollow(FOLLOW_2);
								lv_value_2_1 = ruleSigned_Int();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getInt_LiteralRule());
								}
								set(current, "value", lv_value_2_1,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Signed_Int");
								afterParserOrEnumRuleCall();

							}
								break;
							case 2:
							// InternalStructuredTextParser.g:3155:6: lv_value_2_2= RULE_BINARY_INT
							{
								lv_value_2_2 = (Token) match(input, RULE_BINARY_INT, FOLLOW_2);

								newLeafNode(lv_value_2_2, grammarAccess.getInt_LiteralAccess()
										.getValueBINARY_INTTerminalRuleCall_1_0_1());

								if (current == null) {
									current = createModelElement(grammarAccess.getInt_LiteralRule());
								}
								setWithLastConsumed(current, "value", lv_value_2_2,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.BINARY_INT");

							}
								break;
							case 3:
							// InternalStructuredTextParser.g:3170:6: lv_value_2_3= RULE_OCTAL_INT
							{
								lv_value_2_3 = (Token) match(input, RULE_OCTAL_INT, FOLLOW_2);

								newLeafNode(lv_value_2_3,
										grammarAccess.getInt_LiteralAccess().getValueOCTAL_INTTerminalRuleCall_1_0_2());

								if (current == null) {
									current = createModelElement(grammarAccess.getInt_LiteralRule());
								}
								setWithLastConsumed(current, "value", lv_value_2_3,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.OCTAL_INT");

							}
								break;
							case 4:
							// InternalStructuredTextParser.g:3185:6: lv_value_2_4= RULE_HEX_INT
							{
								lv_value_2_4 = (Token) match(input, RULE_HEX_INT, FOLLOW_2);

								newLeafNode(lv_value_2_4,
										grammarAccess.getInt_LiteralAccess().getValueHEX_INTTerminalRuleCall_1_0_3());

								if (current == null) {
									current = createModelElement(grammarAccess.getInt_LiteralRule());
								}
								setWithLastConsumed(current, "value", lv_value_2_4,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.HEX_INT");

							}
								break;

							}

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleInt_Literal"

	// $ANTLR start "entryRuleSigned_Int"
	// InternalStructuredTextParser.g:3206:1: entryRuleSigned_Int returns [String
	// current=null] : iv_ruleSigned_Int= ruleSigned_Int EOF ;
	public final String entryRuleSigned_Int() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleSigned_Int = null;

		try {
			// InternalStructuredTextParser.g:3206:50: (iv_ruleSigned_Int= ruleSigned_Int
			// EOF )
			// InternalStructuredTextParser.g:3207:2: iv_ruleSigned_Int= ruleSigned_Int EOF
			{
				newCompositeNode(grammarAccess.getSigned_IntRule());
				pushFollow(FOLLOW_1);
				iv_ruleSigned_Int = ruleSigned_Int();

				state._fsp--;

				current = iv_ruleSigned_Int.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleSigned_Int"

	// $ANTLR start "ruleSigned_Int"
	// InternalStructuredTextParser.g:3213:1: ruleSigned_Int returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw=
	// PlusSign | kw= HyphenMinus )? this_UNSIGNED_INT_2= RULE_UNSIGNED_INT ) ;
	public final AntlrDatatypeRuleToken ruleSigned_Int() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token kw = null;
		Token this_UNSIGNED_INT_2 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3219:2: ( ( (kw= PlusSign | kw= HyphenMinus )?
			// this_UNSIGNED_INT_2= RULE_UNSIGNED_INT ) )
			// InternalStructuredTextParser.g:3220:2: ( (kw= PlusSign | kw= HyphenMinus )?
			// this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )
			{
				// InternalStructuredTextParser.g:3220:2: ( (kw= PlusSign | kw= HyphenMinus )?
				// this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )
				// InternalStructuredTextParser.g:3221:3: (kw= PlusSign | kw= HyphenMinus )?
				// this_UNSIGNED_INT_2= RULE_UNSIGNED_INT
				{
					// InternalStructuredTextParser.g:3221:3: (kw= PlusSign | kw= HyphenMinus )?
					int alt47 = 3;
					int LA47_0 = input.LA(1);

					if ((LA47_0 == PlusSign)) {
						alt47 = 1;
					} else if ((LA47_0 == HyphenMinus)) {
						alt47 = 2;
					}
					switch (alt47) {
					case 1:
					// InternalStructuredTextParser.g:3222:4: kw= PlusSign
					{
						kw = (Token) match(input, PlusSign, FOLLOW_10);

						current.merge(kw);
						newLeafNode(kw, grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0());

					}
						break;
					case 2:
					// InternalStructuredTextParser.g:3228:4: kw= HyphenMinus
					{
						kw = (Token) match(input, HyphenMinus, FOLLOW_10);

						current.merge(kw);
						newLeafNode(kw, grammarAccess.getSigned_IntAccess().getHyphenMinusKeyword_0_1());

					}
						break;

					}

					this_UNSIGNED_INT_2 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_2);

					current.merge(this_UNSIGNED_INT_2);

					newLeafNode(this_UNSIGNED_INT_2,
							grammarAccess.getSigned_IntAccess().getUNSIGNED_INTTerminalRuleCall_1());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleSigned_Int"

	// $ANTLR start "entryRulePartial_Value"
	// InternalStructuredTextParser.g:3245:1: entryRulePartial_Value returns [String
	// current=null] : iv_rulePartial_Value= rulePartial_Value EOF ;
	public final String entryRulePartial_Value() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_rulePartial_Value = null;

		try {
			// InternalStructuredTextParser.g:3245:53: (iv_rulePartial_Value=
			// rulePartial_Value EOF )
			// InternalStructuredTextParser.g:3246:2: iv_rulePartial_Value=
			// rulePartial_Value EOF
			{
				newCompositeNode(grammarAccess.getPartial_ValueRule());
				pushFollow(FOLLOW_1);
				iv_rulePartial_Value = rulePartial_Value();

				state._fsp--;

				current = iv_rulePartial_Value.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRulePartial_Value"

	// $ANTLR start "rulePartial_Value"
	// InternalStructuredTextParser.g:3252:1: rulePartial_Value returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
	public final AntlrDatatypeRuleToken rulePartial_Value() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_UNSIGNED_INT_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3258:2: (this_UNSIGNED_INT_0=
			// RULE_UNSIGNED_INT )
			// InternalStructuredTextParser.g:3259:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
			{
				this_UNSIGNED_INT_0 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_2);

				current.merge(this_UNSIGNED_INT_0);

				newLeafNode(this_UNSIGNED_INT_0,
						grammarAccess.getPartial_ValueAccess().getUNSIGNED_INTTerminalRuleCall());

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "rulePartial_Value"

	// $ANTLR start "entryRuleArray_Size"
	// InternalStructuredTextParser.g:3269:1: entryRuleArray_Size returns [String
	// current=null] : iv_ruleArray_Size= ruleArray_Size EOF ;
	public final String entryRuleArray_Size() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleArray_Size = null;

		try {
			// InternalStructuredTextParser.g:3269:50: (iv_ruleArray_Size= ruleArray_Size
			// EOF )
			// InternalStructuredTextParser.g:3270:2: iv_ruleArray_Size= ruleArray_Size EOF
			{
				newCompositeNode(grammarAccess.getArray_SizeRule());
				pushFollow(FOLLOW_1);
				iv_ruleArray_Size = ruleArray_Size();

				state._fsp--;

				current = iv_ruleArray_Size.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleArray_Size"

	// $ANTLR start "ruleArray_Size"
	// InternalStructuredTextParser.g:3276:1: ruleArray_Size returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
	public final AntlrDatatypeRuleToken ruleArray_Size() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_UNSIGNED_INT_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3282:2: (this_UNSIGNED_INT_0=
			// RULE_UNSIGNED_INT )
			// InternalStructuredTextParser.g:3283:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
			{
				this_UNSIGNED_INT_0 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_2);

				current.merge(this_UNSIGNED_INT_0);

				newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getArray_SizeAccess().getUNSIGNED_INTTerminalRuleCall());

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleArray_Size"

	// $ANTLR start "entryRuleReal_Literal"
	// InternalStructuredTextParser.g:3293:1: entryRuleReal_Literal returns [EObject
	// current=null] : iv_ruleReal_Literal= ruleReal_Literal EOF ;
	public final EObject entryRuleReal_Literal() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleReal_Literal = null;

		try {
			// InternalStructuredTextParser.g:3293:53: (iv_ruleReal_Literal=
			// ruleReal_Literal EOF )
			// InternalStructuredTextParser.g:3294:2: iv_ruleReal_Literal= ruleReal_Literal
			// EOF
			{
				newCompositeNode(grammarAccess.getReal_LiteralRule());
				pushFollow(FOLLOW_1);
				iv_ruleReal_Literal = ruleReal_Literal();

				state._fsp--;

				current = iv_ruleReal_Literal;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleReal_Literal"

	// $ANTLR start "ruleReal_Literal"
	// InternalStructuredTextParser.g:3300:1: ruleReal_Literal returns [EObject
	// current=null] : ( ( ( (lv_type_0_0= ruleReal_Type_Name ) ) otherlv_1=
	// NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) ) ;
	public final EObject ruleReal_Literal() throws RecognitionException {
		EObject current = null;

		Token otherlv_1 = null;
		Enumerator lv_type_0_0 = null;

		AntlrDatatypeRuleToken lv_value_2_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3306:2: ( ( ( ( (lv_type_0_0=
			// ruleReal_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0=
			// ruleReal_Value ) ) ) )
			// InternalStructuredTextParser.g:3307:2: ( ( ( (lv_type_0_0= ruleReal_Type_Name
			// ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) )
			{
				// InternalStructuredTextParser.g:3307:2: ( ( ( (lv_type_0_0= ruleReal_Type_Name
				// ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) ) )
				// InternalStructuredTextParser.g:3308:3: ( ( (lv_type_0_0= ruleReal_Type_Name )
				// ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleReal_Value ) )
				{
					// InternalStructuredTextParser.g:3308:3: ( ( (lv_type_0_0= ruleReal_Type_Name )
					// ) otherlv_1= NumberSign )?
					int alt48 = 2;
					int LA48_0 = input.LA(1);

					if ((LA48_0 == LREAL || LA48_0 == REAL)) {
						alt48 = 1;
					}
					switch (alt48) {
					case 1:
					// InternalStructuredTextParser.g:3309:4: ( (lv_type_0_0= ruleReal_Type_Name ) )
					// otherlv_1= NumberSign
					{
						// InternalStructuredTextParser.g:3309:4: ( (lv_type_0_0= ruleReal_Type_Name ) )
						// InternalStructuredTextParser.g:3310:5: (lv_type_0_0= ruleReal_Type_Name )
						{
							// InternalStructuredTextParser.g:3310:5: (lv_type_0_0= ruleReal_Type_Name )
							// InternalStructuredTextParser.g:3311:6: lv_type_0_0= ruleReal_Type_Name
							{

								newCompositeNode(grammarAccess.getReal_LiteralAccess()
										.getTypeReal_Type_NameEnumRuleCall_0_0_0());

								pushFollow(FOLLOW_55);
								lv_type_0_0 = ruleReal_Type_Name();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getReal_LiteralRule());
								}
								set(current, "type", lv_type_0_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Real_Type_Name");
								afterParserOrEnumRuleCall();

							}

						}

						otherlv_1 = (Token) match(input, NumberSign, FOLLOW_57);

						newLeafNode(otherlv_1, grammarAccess.getReal_LiteralAccess().getNumberSignKeyword_0_1());

					}
						break;

					}

					// InternalStructuredTextParser.g:3333:3: ( (lv_value_2_0= ruleReal_Value ) )
					// InternalStructuredTextParser.g:3334:4: (lv_value_2_0= ruleReal_Value )
					{
						// InternalStructuredTextParser.g:3334:4: (lv_value_2_0= ruleReal_Value )
						// InternalStructuredTextParser.g:3335:5: lv_value_2_0= ruleReal_Value
						{

							newCompositeNode(
									grammarAccess.getReal_LiteralAccess().getValueReal_ValueParserRuleCall_1_0());

							pushFollow(FOLLOW_2);
							lv_value_2_0 = ruleReal_Value();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getReal_LiteralRule());
							}
							set(current, "value", lv_value_2_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Real_Value");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleReal_Literal"

	// $ANTLR start "entryRuleReal_Value"
	// InternalStructuredTextParser.g:3356:1: entryRuleReal_Value returns [String
	// current=null] : iv_ruleReal_Value= ruleReal_Value EOF ;
	public final String entryRuleReal_Value() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleReal_Value = null;

		try {
			// InternalStructuredTextParser.g:3356:50: (iv_ruleReal_Value= ruleReal_Value
			// EOF )
			// InternalStructuredTextParser.g:3357:2: iv_ruleReal_Value= ruleReal_Value EOF
			{
				newCompositeNode(grammarAccess.getReal_ValueRule());
				pushFollow(FOLLOW_1);
				iv_ruleReal_Value = ruleReal_Value();

				state._fsp--;

				current = iv_ruleReal_Value.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleReal_Value"

	// $ANTLR start "ruleReal_Value"
	// InternalStructuredTextParser.g:3363:1: ruleReal_Value returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// (this_Signed_Int_0= ruleSigned_Int kw= FullStop this_UNSIGNED_INT_2=
	// RULE_UNSIGNED_INT (kw= E this_Signed_Int_4= ruleSigned_Int )? ) ;
	public final AntlrDatatypeRuleToken ruleReal_Value() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token kw = null;
		Token this_UNSIGNED_INT_2 = null;
		AntlrDatatypeRuleToken this_Signed_Int_0 = null;

		AntlrDatatypeRuleToken this_Signed_Int_4 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3369:2: ( (this_Signed_Int_0= ruleSigned_Int
			// kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4=
			// ruleSigned_Int )? ) )
			// InternalStructuredTextParser.g:3370:2: (this_Signed_Int_0= ruleSigned_Int kw=
			// FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4=
			// ruleSigned_Int )? )
			{
				// InternalStructuredTextParser.g:3370:2: (this_Signed_Int_0= ruleSigned_Int kw=
				// FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4=
				// ruleSigned_Int )? )
				// InternalStructuredTextParser.g:3371:3: this_Signed_Int_0= ruleSigned_Int kw=
				// FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT (kw= E this_Signed_Int_4=
				// ruleSigned_Int )?
				{

					newCompositeNode(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_0());

					pushFollow(FOLLOW_54);
					this_Signed_Int_0 = ruleSigned_Int();

					state._fsp--;

					current.merge(this_Signed_Int_0);

					afterParserOrEnumRuleCall();

					kw = (Token) match(input, FullStop, FOLLOW_10);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getReal_ValueAccess().getFullStopKeyword_1());

					this_UNSIGNED_INT_2 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_58);

					current.merge(this_UNSIGNED_INT_2);

					newLeafNode(this_UNSIGNED_INT_2,
							grammarAccess.getReal_ValueAccess().getUNSIGNED_INTTerminalRuleCall_2());

					// InternalStructuredTextParser.g:3393:3: (kw= E this_Signed_Int_4=
					// ruleSigned_Int )?
					int alt49 = 2;
					int LA49_0 = input.LA(1);

					if ((LA49_0 == E)) {
						alt49 = 1;
					}
					switch (alt49) {
					case 1:
					// InternalStructuredTextParser.g:3394:4: kw= E this_Signed_Int_4=
					// ruleSigned_Int
					{
						kw = (Token) match(input, E, FOLLOW_59);

						current.merge(kw);
						newLeafNode(kw, grammarAccess.getReal_ValueAccess().getEKeyword_3_0());

						newCompositeNode(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_3_1());

						pushFollow(FOLLOW_2);
						this_Signed_Int_4 = ruleSigned_Int();

						state._fsp--;

						current.merge(this_Signed_Int_4);

						afterParserOrEnumRuleCall();

					}
						break;

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleReal_Value"

	// $ANTLR start "entryRuleBool_Literal"
	// InternalStructuredTextParser.g:3414:1: entryRuleBool_Literal returns [EObject
	// current=null] : iv_ruleBool_Literal= ruleBool_Literal EOF ;
	public final EObject entryRuleBool_Literal() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleBool_Literal = null;

		try {
			// InternalStructuredTextParser.g:3414:53: (iv_ruleBool_Literal=
			// ruleBool_Literal EOF )
			// InternalStructuredTextParser.g:3415:2: iv_ruleBool_Literal= ruleBool_Literal
			// EOF
			{
				newCompositeNode(grammarAccess.getBool_LiteralRule());
				pushFollow(FOLLOW_1);
				iv_ruleBool_Literal = ruleBool_Literal();

				state._fsp--;

				current = iv_ruleBool_Literal;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleBool_Literal"

	// $ANTLR start "ruleBool_Literal"
	// InternalStructuredTextParser.g:3421:1: ruleBool_Literal returns [EObject
	// current=null] : ( ( ( (lv_type_0_0= ruleBool_Type_Name ) ) otherlv_1=
	// NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) ) ;
	public final EObject ruleBool_Literal() throws RecognitionException {
		EObject current = null;

		Token otherlv_1 = null;
		Enumerator lv_type_0_0 = null;

		AntlrDatatypeRuleToken lv_value_2_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3427:2: ( ( ( ( (lv_type_0_0=
			// ruleBool_Type_Name ) ) otherlv_1= NumberSign )? ( (lv_value_2_0=
			// ruleBool_Value ) ) ) )
			// InternalStructuredTextParser.g:3428:2: ( ( ( (lv_type_0_0= ruleBool_Type_Name
			// ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) )
			{
				// InternalStructuredTextParser.g:3428:2: ( ( ( (lv_type_0_0= ruleBool_Type_Name
				// ) ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) ) )
				// InternalStructuredTextParser.g:3429:3: ( ( (lv_type_0_0= ruleBool_Type_Name )
				// ) otherlv_1= NumberSign )? ( (lv_value_2_0= ruleBool_Value ) )
				{
					// InternalStructuredTextParser.g:3429:3: ( ( (lv_type_0_0= ruleBool_Type_Name )
					// ) otherlv_1= NumberSign )?
					int alt50 = 2;
					int LA50_0 = input.LA(1);

					if ((LA50_0 == BOOL)) {
						alt50 = 1;
					}
					switch (alt50) {
					case 1:
					// InternalStructuredTextParser.g:3430:4: ( (lv_type_0_0= ruleBool_Type_Name ) )
					// otherlv_1= NumberSign
					{
						// InternalStructuredTextParser.g:3430:4: ( (lv_type_0_0= ruleBool_Type_Name ) )
						// InternalStructuredTextParser.g:3431:5: (lv_type_0_0= ruleBool_Type_Name )
						{
							// InternalStructuredTextParser.g:3431:5: (lv_type_0_0= ruleBool_Type_Name )
							// InternalStructuredTextParser.g:3432:6: lv_type_0_0= ruleBool_Type_Name
							{

								newCompositeNode(grammarAccess.getBool_LiteralAccess()
										.getTypeBool_Type_NameEnumRuleCall_0_0_0());

								pushFollow(FOLLOW_55);
								lv_type_0_0 = ruleBool_Type_Name();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getBool_LiteralRule());
								}
								set(current, "type", lv_type_0_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Bool_Type_Name");
								afterParserOrEnumRuleCall();

							}

						}

						otherlv_1 = (Token) match(input, NumberSign, FOLLOW_13);

						newLeafNode(otherlv_1, grammarAccess.getBool_LiteralAccess().getNumberSignKeyword_0_1());

					}
						break;

					}

					// InternalStructuredTextParser.g:3454:3: ( (lv_value_2_0= ruleBool_Value ) )
					// InternalStructuredTextParser.g:3455:4: (lv_value_2_0= ruleBool_Value )
					{
						// InternalStructuredTextParser.g:3455:4: (lv_value_2_0= ruleBool_Value )
						// InternalStructuredTextParser.g:3456:5: lv_value_2_0= ruleBool_Value
						{

							newCompositeNode(
									grammarAccess.getBool_LiteralAccess().getValueBool_ValueParserRuleCall_1_0());

							pushFollow(FOLLOW_2);
							lv_value_2_0 = ruleBool_Value();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getBool_LiteralRule());
							}
							set(current, "value", lv_value_2_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Bool_Value");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleBool_Literal"

	// $ANTLR start "entryRuleBool_Value"
	// InternalStructuredTextParser.g:3477:1: entryRuleBool_Value returns [String
	// current=null] : iv_ruleBool_Value= ruleBool_Value EOF ;
	public final String entryRuleBool_Value() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleBool_Value = null;

		try {
			// InternalStructuredTextParser.g:3477:50: (iv_ruleBool_Value= ruleBool_Value
			// EOF )
			// InternalStructuredTextParser.g:3478:2: iv_ruleBool_Value= ruleBool_Value EOF
			{
				newCompositeNode(grammarAccess.getBool_ValueRule());
				pushFollow(FOLLOW_1);
				iv_ruleBool_Value = ruleBool_Value();

				state._fsp--;

				current = iv_ruleBool_Value.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleBool_Value"

	// $ANTLR start "ruleBool_Value"
	// InternalStructuredTextParser.g:3484:1: ruleBool_Value returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= FALSE |
	// kw= TRUE ) ;
	public final AntlrDatatypeRuleToken ruleBool_Value() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token kw = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3490:2: ( (kw= FALSE | kw= TRUE ) )
			// InternalStructuredTextParser.g:3491:2: (kw= FALSE | kw= TRUE )
			{
				// InternalStructuredTextParser.g:3491:2: (kw= FALSE | kw= TRUE )
				int alt51 = 2;
				int LA51_0 = input.LA(1);

				if ((LA51_0 == FALSE)) {
					alt51 = 1;
				} else if ((LA51_0 == TRUE)) {
					alt51 = 2;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 51, 0, input);

					throw nvae;
				}
				switch (alt51) {
				case 1:
				// InternalStructuredTextParser.g:3492:3: kw= FALSE
				{
					kw = (Token) match(input, FALSE, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getBool_ValueAccess().getFALSEKeyword_0());

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:3498:3: kw= TRUE
				{
					kw = (Token) match(input, TRUE, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getBool_ValueAccess().getTRUEKeyword_1());

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleBool_Value"

	// $ANTLR start "entryRuleChar_Literal"
	// InternalStructuredTextParser.g:3507:1: entryRuleChar_Literal returns [EObject
	// current=null] : iv_ruleChar_Literal= ruleChar_Literal EOF ;
	public final EObject entryRuleChar_Literal() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleChar_Literal = null;

		try {
			// InternalStructuredTextParser.g:3507:53: (iv_ruleChar_Literal=
			// ruleChar_Literal EOF )
			// InternalStructuredTextParser.g:3508:2: iv_ruleChar_Literal= ruleChar_Literal
			// EOF
			{
				newCompositeNode(grammarAccess.getChar_LiteralRule());
				pushFollow(FOLLOW_1);
				iv_ruleChar_Literal = ruleChar_Literal();

				state._fsp--;

				current = iv_ruleChar_Literal;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleChar_Literal"

	// $ANTLR start "ruleChar_Literal"
	// InternalStructuredTextParser.g:3514:1: ruleChar_Literal returns [EObject
	// current=null] : ( ( ( (lv_type_0_0= ruleString_Type_Name ) ) (
	// (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( (
	// (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) )
	// ) ) ;
	public final EObject ruleChar_Literal() throws RecognitionException {
		EObject current = null;

		Token lv_length_1_0 = null;
		Token otherlv_2 = null;
		Token lv_value_3_1 = null;
		Token lv_value_3_2 = null;
		Enumerator lv_type_0_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3520:2: ( ( ( ( (lv_type_0_0=
			// ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2=
			// NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2=
			// RULE_D_BYTE_CHAR_STR ) ) ) ) )
			// InternalStructuredTextParser.g:3521:2: ( ( ( (lv_type_0_0=
			// ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2=
			// NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2=
			// RULE_D_BYTE_CHAR_STR ) ) ) )
			{
				// InternalStructuredTextParser.g:3521:2: ( ( ( (lv_type_0_0=
				// ruleString_Type_Name ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2=
				// NumberSign )? ( ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2=
				// RULE_D_BYTE_CHAR_STR ) ) ) )
				// InternalStructuredTextParser.g:3522:3: ( ( (lv_type_0_0= ruleString_Type_Name
				// ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )? ( (
				// (lv_value_3_1= RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) )
				// )
				{
					// InternalStructuredTextParser.g:3522:3: ( ( (lv_type_0_0= ruleString_Type_Name
					// ) ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign )?
					int alt53 = 2;
					int LA53_0 = input.LA(1);

					if ((LA53_0 == WSTRING || LA53_0 == STRING || LA53_0 == WCHAR || LA53_0 == CHAR)) {
						alt53 = 1;
					}
					switch (alt53) {
					case 1:
					// InternalStructuredTextParser.g:3523:4: ( (lv_type_0_0= ruleString_Type_Name )
					// ) ( (lv_length_1_0= RULE_UNSIGNED_INT ) )? otherlv_2= NumberSign
					{
						// InternalStructuredTextParser.g:3523:4: ( (lv_type_0_0= ruleString_Type_Name )
						// )
						// InternalStructuredTextParser.g:3524:5: (lv_type_0_0= ruleString_Type_Name )
						{
							// InternalStructuredTextParser.g:3524:5: (lv_type_0_0= ruleString_Type_Name )
							// InternalStructuredTextParser.g:3525:6: lv_type_0_0= ruleString_Type_Name
							{

								newCompositeNode(grammarAccess.getChar_LiteralAccess()
										.getTypeString_Type_NameEnumRuleCall_0_0_0());

								pushFollow(FOLLOW_60);
								lv_type_0_0 = ruleString_Type_Name();

								state._fsp--;

								if (current == null) {
									current = createModelElementForParent(grammarAccess.getChar_LiteralRule());
								}
								set(current, "type", lv_type_0_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.String_Type_Name");
								afterParserOrEnumRuleCall();

							}

						}

						// InternalStructuredTextParser.g:3542:4: ( (lv_length_1_0= RULE_UNSIGNED_INT )
						// )?
						int alt52 = 2;
						int LA52_0 = input.LA(1);

						if ((LA52_0 == RULE_UNSIGNED_INT)) {
							alt52 = 1;
						}
						switch (alt52) {
						case 1:
						// InternalStructuredTextParser.g:3543:5: (lv_length_1_0= RULE_UNSIGNED_INT )
						{
							// InternalStructuredTextParser.g:3543:5: (lv_length_1_0= RULE_UNSIGNED_INT )
							// InternalStructuredTextParser.g:3544:6: lv_length_1_0= RULE_UNSIGNED_INT
							{
								lv_length_1_0 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_55);

								newLeafNode(lv_length_1_0, grammarAccess.getChar_LiteralAccess()
										.getLengthUNSIGNED_INTTerminalRuleCall_0_1_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getChar_LiteralRule());
								}
								setWithLastConsumed(current, "length", lv_length_1_0,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.UNSIGNED_INT");

							}

						}
							break;

						}

						otherlv_2 = (Token) match(input, NumberSign, FOLLOW_61);

						newLeafNode(otherlv_2, grammarAccess.getChar_LiteralAccess().getNumberSignKeyword_0_2());

					}
						break;

					}

					// InternalStructuredTextParser.g:3565:3: ( ( (lv_value_3_1=
					// RULE_S_BYTE_CHAR_STR | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) ) )
					// InternalStructuredTextParser.g:3566:4: ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR
					// | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) )
					{
						// InternalStructuredTextParser.g:3566:4: ( (lv_value_3_1= RULE_S_BYTE_CHAR_STR
						// | lv_value_3_2= RULE_D_BYTE_CHAR_STR ) )
						// InternalStructuredTextParser.g:3567:5: (lv_value_3_1= RULE_S_BYTE_CHAR_STR |
						// lv_value_3_2= RULE_D_BYTE_CHAR_STR )
						{
							// InternalStructuredTextParser.g:3567:5: (lv_value_3_1= RULE_S_BYTE_CHAR_STR |
							// lv_value_3_2= RULE_D_BYTE_CHAR_STR )
							int alt54 = 2;
							int LA54_0 = input.LA(1);

							if ((LA54_0 == RULE_S_BYTE_CHAR_STR)) {
								alt54 = 1;
							} else if ((LA54_0 == RULE_D_BYTE_CHAR_STR)) {
								alt54 = 2;
							} else {
								NoViableAltException nvae = new NoViableAltException("", 54, 0, input);

								throw nvae;
							}
							switch (alt54) {
							case 1:
							// InternalStructuredTextParser.g:3568:6: lv_value_3_1= RULE_S_BYTE_CHAR_STR
							{
								lv_value_3_1 = (Token) match(input, RULE_S_BYTE_CHAR_STR, FOLLOW_2);

								newLeafNode(lv_value_3_1, grammarAccess.getChar_LiteralAccess()
										.getValueS_BYTE_CHAR_STRTerminalRuleCall_1_0_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getChar_LiteralRule());
								}
								setWithLastConsumed(current, "value", lv_value_3_1,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.S_BYTE_CHAR_STR");

							}
								break;
							case 2:
							// InternalStructuredTextParser.g:3583:6: lv_value_3_2= RULE_D_BYTE_CHAR_STR
							{
								lv_value_3_2 = (Token) match(input, RULE_D_BYTE_CHAR_STR, FOLLOW_2);

								newLeafNode(lv_value_3_2, grammarAccess.getChar_LiteralAccess()
										.getValueD_BYTE_CHAR_STRTerminalRuleCall_1_0_1());

								if (current == null) {
									current = createModelElement(grammarAccess.getChar_LiteralRule());
								}
								setWithLastConsumed(current, "value", lv_value_3_2,
										"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.D_BYTE_CHAR_STR");

							}
								break;

							}

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleChar_Literal"

	// $ANTLR start "entryRuleTime_Literal"
	// InternalStructuredTextParser.g:3604:1: entryRuleTime_Literal returns [EObject
	// current=null] : iv_ruleTime_Literal= ruleTime_Literal EOF ;
	public final EObject entryRuleTime_Literal() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleTime_Literal = null;

		try {
			// InternalStructuredTextParser.g:3604:53: (iv_ruleTime_Literal=
			// ruleTime_Literal EOF )
			// InternalStructuredTextParser.g:3605:2: iv_ruleTime_Literal= ruleTime_Literal
			// EOF
			{
				newCompositeNode(grammarAccess.getTime_LiteralRule());
				pushFollow(FOLLOW_1);
				iv_ruleTime_Literal = ruleTime_Literal();

				state._fsp--;

				current = iv_ruleTime_Literal;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleTime_Literal"

	// $ANTLR start "ruleTime_Literal"
	// InternalStructuredTextParser.g:3611:1: ruleTime_Literal returns [EObject
	// current=null] : (this_Duration_0= ruleDuration | this_Time_Of_Day_1=
	// ruleTime_Of_Day | this_Date_2= ruleDate | this_Date_And_Time_3=
	// ruleDate_And_Time ) ;
	public final EObject ruleTime_Literal() throws RecognitionException {
		EObject current = null;

		EObject this_Duration_0 = null;

		EObject this_Time_Of_Day_1 = null;

		EObject this_Date_2 = null;

		EObject this_Date_And_Time_3 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3617:2: ( (this_Duration_0= ruleDuration |
			// this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate |
			// this_Date_And_Time_3= ruleDate_And_Time ) )
			// InternalStructuredTextParser.g:3618:2: (this_Duration_0= ruleDuration |
			// this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate |
			// this_Date_And_Time_3= ruleDate_And_Time )
			{
				// InternalStructuredTextParser.g:3618:2: (this_Duration_0= ruleDuration |
				// this_Time_Of_Day_1= ruleTime_Of_Day | this_Date_2= ruleDate |
				// this_Date_And_Time_3= ruleDate_And_Time )
				int alt55 = 4;
				switch (input.LA(1)) {
				case LTIME:
				case TIME:
				case LT:
				case T: {
					alt55 = 1;
				}
					break;
				case LTIME_OF_DAY:
				case TIME_OF_DAY:
				case LTOD:
				case TOD: {
					alt55 = 2;
				}
					break;
				case LDATE:
				case DATE:
				case LD:
				case D_1: {
					alt55 = 3;
				}
					break;
				case LDATE_AND_TIME:
				case DATE_AND_TIME:
				case LDT:
				case DT: {
					alt55 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 55, 0, input);

					throw nvae;
				}

				switch (alt55) {
				case 1:
				// InternalStructuredTextParser.g:3619:3: this_Duration_0= ruleDuration
				{

					newCompositeNode(grammarAccess.getTime_LiteralAccess().getDurationParserRuleCall_0());

					pushFollow(FOLLOW_2);
					this_Duration_0 = ruleDuration();

					state._fsp--;

					current = this_Duration_0;
					afterParserOrEnumRuleCall();

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:3628:3: this_Time_Of_Day_1= ruleTime_Of_Day
				{

					newCompositeNode(grammarAccess.getTime_LiteralAccess().getTime_Of_DayParserRuleCall_1());

					pushFollow(FOLLOW_2);
					this_Time_Of_Day_1 = ruleTime_Of_Day();

					state._fsp--;

					current = this_Time_Of_Day_1;
					afterParserOrEnumRuleCall();

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:3637:3: this_Date_2= ruleDate
				{

					newCompositeNode(grammarAccess.getTime_LiteralAccess().getDateParserRuleCall_2());

					pushFollow(FOLLOW_2);
					this_Date_2 = ruleDate();

					state._fsp--;

					current = this_Date_2;
					afterParserOrEnumRuleCall();

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:3646:3: this_Date_And_Time_3=
				// ruleDate_And_Time
				{

					newCompositeNode(grammarAccess.getTime_LiteralAccess().getDate_And_TimeParserRuleCall_3());

					pushFollow(FOLLOW_2);
					this_Date_And_Time_3 = ruleDate_And_Time();

					state._fsp--;

					current = this_Date_And_Time_3;
					afterParserOrEnumRuleCall();

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleTime_Literal"

	// $ANTLR start "entryRuleDuration"
	// InternalStructuredTextParser.g:3658:1: entryRuleDuration returns [EObject
	// current=null] : iv_ruleDuration= ruleDuration EOF ;
	public final EObject entryRuleDuration() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleDuration = null;

		try {
			// InternalStructuredTextParser.g:3658:49: (iv_ruleDuration= ruleDuration EOF )
			// InternalStructuredTextParser.g:3659:2: iv_ruleDuration= ruleDuration EOF
			{
				newCompositeNode(grammarAccess.getDurationRule());
				pushFollow(FOLLOW_1);
				iv_ruleDuration = ruleDuration();

				state._fsp--;

				current = iv_ruleDuration;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDuration"

	// $ANTLR start "ruleDuration"
	// InternalStructuredTextParser.g:3665:1: ruleDuration returns [EObject
	// current=null] : ( ( (lv_type_0_0= ruleTime_Type_Name ) ) otherlv_1=
	// NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus ) ) )? (
	// (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ ( (lv_value_6_0=
	// ruleDuration_Value ) ) )* ) ;
	public final EObject ruleDuration() throws RecognitionException {
		EObject current = null;

		Token otherlv_1 = null;
		Token otherlv_2 = null;
		Token lv_negative_3_0 = null;
		Token otherlv_5 = null;
		Enumerator lv_type_0_0 = null;

		EObject lv_value_4_0 = null;

		EObject lv_value_6_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3671:2: ( ( ( (lv_type_0_0= ruleTime_Type_Name
			// ) ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0=
			// HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ (
			// (lv_value_6_0= ruleDuration_Value ) ) )* ) )
			// InternalStructuredTextParser.g:3672:2: ( ( (lv_type_0_0= ruleTime_Type_Name )
			// ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0=
			// HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ (
			// (lv_value_6_0= ruleDuration_Value ) ) )* )
			{
				// InternalStructuredTextParser.g:3672:2: ( ( (lv_type_0_0= ruleTime_Type_Name )
				// ) otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0=
				// HyphenMinus ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ (
				// (lv_value_6_0= ruleDuration_Value ) ) )* )
				// InternalStructuredTextParser.g:3673:3: ( (lv_type_0_0= ruleTime_Type_Name ) )
				// otherlv_1= NumberSign (otherlv_2= PlusSign | ( (lv_negative_3_0= HyphenMinus
				// ) ) )? ( (lv_value_4_0= ruleDuration_Value ) ) (otherlv_5= KW__ (
				// (lv_value_6_0= ruleDuration_Value ) ) )*
				{
					// InternalStructuredTextParser.g:3673:3: ( (lv_type_0_0= ruleTime_Type_Name ) )
					// InternalStructuredTextParser.g:3674:4: (lv_type_0_0= ruleTime_Type_Name )
					{
						// InternalStructuredTextParser.g:3674:4: (lv_type_0_0= ruleTime_Type_Name )
						// InternalStructuredTextParser.g:3675:5: lv_type_0_0= ruleTime_Type_Name
						{

							newCompositeNode(grammarAccess.getDurationAccess().getTypeTime_Type_NameEnumRuleCall_0_0());

							pushFollow(FOLLOW_55);
							lv_type_0_0 = ruleTime_Type_Name();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getDurationRule());
							}
							set(current, "type", lv_type_0_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Time_Type_Name");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_1 = (Token) match(input, NumberSign, FOLLOW_59);

					newLeafNode(otherlv_1, grammarAccess.getDurationAccess().getNumberSignKeyword_1());

					// InternalStructuredTextParser.g:3696:3: (otherlv_2= PlusSign | (
					// (lv_negative_3_0= HyphenMinus ) ) )?
					int alt56 = 3;
					int LA56_0 = input.LA(1);

					if ((LA56_0 == PlusSign)) {
						alt56 = 1;
					} else if ((LA56_0 == HyphenMinus)) {
						alt56 = 2;
					}
					switch (alt56) {
					case 1:
					// InternalStructuredTextParser.g:3697:4: otherlv_2= PlusSign
					{
						otherlv_2 = (Token) match(input, PlusSign, FOLLOW_59);

						newLeafNode(otherlv_2, grammarAccess.getDurationAccess().getPlusSignKeyword_2_0());

					}
						break;
					case 2:
					// InternalStructuredTextParser.g:3702:4: ( (lv_negative_3_0= HyphenMinus ) )
					{
						// InternalStructuredTextParser.g:3702:4: ( (lv_negative_3_0= HyphenMinus ) )
						// InternalStructuredTextParser.g:3703:5: (lv_negative_3_0= HyphenMinus )
						{
							// InternalStructuredTextParser.g:3703:5: (lv_negative_3_0= HyphenMinus )
							// InternalStructuredTextParser.g:3704:6: lv_negative_3_0= HyphenMinus
							{
								lv_negative_3_0 = (Token) match(input, HyphenMinus, FOLLOW_59);

								newLeafNode(lv_negative_3_0,
										grammarAccess.getDurationAccess().getNegativeHyphenMinusKeyword_2_1_0());

								if (current == null) {
									current = createModelElement(grammarAccess.getDurationRule());
								}
								setWithLastConsumed(current, "negative", true, "-");

							}

						}

					}
						break;

					}

					// InternalStructuredTextParser.g:3717:3: ( (lv_value_4_0= ruleDuration_Value )
					// )
					// InternalStructuredTextParser.g:3718:4: (lv_value_4_0= ruleDuration_Value )
					{
						// InternalStructuredTextParser.g:3718:4: (lv_value_4_0= ruleDuration_Value )
						// InternalStructuredTextParser.g:3719:5: lv_value_4_0= ruleDuration_Value
						{

							newCompositeNode(
									grammarAccess.getDurationAccess().getValueDuration_ValueParserRuleCall_3_0());

							pushFollow(FOLLOW_62);
							lv_value_4_0 = ruleDuration_Value();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getDurationRule());
							}
							add(current, "value", lv_value_4_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Duration_Value");
							afterParserOrEnumRuleCall();

						}

					}

					// InternalStructuredTextParser.g:3736:3: (otherlv_5= KW__ ( (lv_value_6_0=
					// ruleDuration_Value ) ) )*
					loop57: do {
						int alt57 = 2;
						int LA57_0 = input.LA(1);

						if ((LA57_0 == KW__)) {
							alt57 = 1;
						}

						switch (alt57) {
						case 1:
						// InternalStructuredTextParser.g:3737:4: otherlv_5= KW__ ( (lv_value_6_0=
						// ruleDuration_Value ) )
						{
							otherlv_5 = (Token) match(input, KW__, FOLLOW_59);

							newLeafNode(otherlv_5, grammarAccess.getDurationAccess().get_Keyword_4_0());

							// InternalStructuredTextParser.g:3741:4: ( (lv_value_6_0= ruleDuration_Value )
							// )
							// InternalStructuredTextParser.g:3742:5: (lv_value_6_0= ruleDuration_Value )
							{
								// InternalStructuredTextParser.g:3742:5: (lv_value_6_0= ruleDuration_Value )
								// InternalStructuredTextParser.g:3743:6: lv_value_6_0= ruleDuration_Value
								{

									newCompositeNode(grammarAccess.getDurationAccess()
											.getValueDuration_ValueParserRuleCall_4_1_0());

									pushFollow(FOLLOW_62);
									lv_value_6_0 = ruleDuration_Value();

									state._fsp--;

									if (current == null) {
										current = createModelElementForParent(grammarAccess.getDurationRule());
									}
									add(current, "value", lv_value_6_0,
											"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Duration_Value");
									afterParserOrEnumRuleCall();

								}

							}

						}
							break;

						default:
							break loop57;
						}
					} while (true);

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDuration"

	// $ANTLR start "entryRuleDuration_Value"
	// InternalStructuredTextParser.g:3765:1: entryRuleDuration_Value returns
	// [EObject current=null] : iv_ruleDuration_Value= ruleDuration_Value EOF ;
	public final EObject entryRuleDuration_Value() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleDuration_Value = null;

		try {
			// InternalStructuredTextParser.g:3765:55: (iv_ruleDuration_Value=
			// ruleDuration_Value EOF )
			// InternalStructuredTextParser.g:3766:2: iv_ruleDuration_Value=
			// ruleDuration_Value EOF
			{
				newCompositeNode(grammarAccess.getDuration_ValueRule());
				pushFollow(FOLLOW_1);
				iv_ruleDuration_Value = ruleDuration_Value();

				state._fsp--;

				current = iv_ruleDuration_Value;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDuration_Value"

	// $ANTLR start "ruleDuration_Value"
	// InternalStructuredTextParser.g:3772:1: ruleDuration_Value returns [EObject
	// current=null] : ( ( (lv_value_0_0= ruleFix_Point ) ) ( (lv_unit_1_0=
	// ruleDuration_Unit ) ) ) ;
	public final EObject ruleDuration_Value() throws RecognitionException {
		EObject current = null;

		AntlrDatatypeRuleToken lv_value_0_0 = null;

		Enumerator lv_unit_1_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3778:2: ( ( ( (lv_value_0_0= ruleFix_Point ) )
			// ( (lv_unit_1_0= ruleDuration_Unit ) ) ) )
			// InternalStructuredTextParser.g:3779:2: ( ( (lv_value_0_0= ruleFix_Point ) ) (
			// (lv_unit_1_0= ruleDuration_Unit ) ) )
			{
				// InternalStructuredTextParser.g:3779:2: ( ( (lv_value_0_0= ruleFix_Point ) ) (
				// (lv_unit_1_0= ruleDuration_Unit ) ) )
				// InternalStructuredTextParser.g:3780:3: ( (lv_value_0_0= ruleFix_Point ) ) (
				// (lv_unit_1_0= ruleDuration_Unit ) )
				{
					// InternalStructuredTextParser.g:3780:3: ( (lv_value_0_0= ruleFix_Point ) )
					// InternalStructuredTextParser.g:3781:4: (lv_value_0_0= ruleFix_Point )
					{
						// InternalStructuredTextParser.g:3781:4: (lv_value_0_0= ruleFix_Point )
						// InternalStructuredTextParser.g:3782:5: lv_value_0_0= ruleFix_Point
						{

							newCompositeNode(
									grammarAccess.getDuration_ValueAccess().getValueFix_PointParserRuleCall_0_0());

							pushFollow(FOLLOW_63);
							lv_value_0_0 = ruleFix_Point();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getDuration_ValueRule());
							}
							set(current, "value", lv_value_0_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Fix_Point");
							afterParserOrEnumRuleCall();

						}

					}

					// InternalStructuredTextParser.g:3799:3: ( (lv_unit_1_0= ruleDuration_Unit ) )
					// InternalStructuredTextParser.g:3800:4: (lv_unit_1_0= ruleDuration_Unit )
					{
						// InternalStructuredTextParser.g:3800:4: (lv_unit_1_0= ruleDuration_Unit )
						// InternalStructuredTextParser.g:3801:5: lv_unit_1_0= ruleDuration_Unit
						{

							newCompositeNode(
									grammarAccess.getDuration_ValueAccess().getUnitDuration_UnitEnumRuleCall_1_0());

							pushFollow(FOLLOW_2);
							lv_unit_1_0 = ruleDuration_Unit();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getDuration_ValueRule());
							}
							set(current, "unit", lv_unit_1_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Duration_Unit");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDuration_Value"

	// $ANTLR start "entryRuleFix_Point"
	// InternalStructuredTextParser.g:3822:1: entryRuleFix_Point returns [String
	// current=null] : iv_ruleFix_Point= ruleFix_Point EOF ;
	public final String entryRuleFix_Point() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleFix_Point = null;

		try {
			// InternalStructuredTextParser.g:3822:49: (iv_ruleFix_Point= ruleFix_Point EOF
			// )
			// InternalStructuredTextParser.g:3823:2: iv_ruleFix_Point= ruleFix_Point EOF
			{
				newCompositeNode(grammarAccess.getFix_PointRule());
				pushFollow(FOLLOW_1);
				iv_ruleFix_Point = ruleFix_Point();

				state._fsp--;

				current = iv_ruleFix_Point.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleFix_Point"

	// $ANTLR start "ruleFix_Point"
	// InternalStructuredTextParser.g:3829:1: ruleFix_Point returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// (this_UNSIGNED_INT_0= RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2=
	// RULE_UNSIGNED_INT )? ) ;
	public final AntlrDatatypeRuleToken ruleFix_Point() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_UNSIGNED_INT_0 = null;
		Token kw = null;
		Token this_UNSIGNED_INT_2 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3835:2: ( (this_UNSIGNED_INT_0=
			// RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? ) )
			// InternalStructuredTextParser.g:3836:2: (this_UNSIGNED_INT_0=
			// RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? )
			{
				// InternalStructuredTextParser.g:3836:2: (this_UNSIGNED_INT_0=
				// RULE_UNSIGNED_INT (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )? )
				// InternalStructuredTextParser.g:3837:3: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
				// (kw= FullStop this_UNSIGNED_INT_2= RULE_UNSIGNED_INT )?
				{
					this_UNSIGNED_INT_0 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_64);

					current.merge(this_UNSIGNED_INT_0);

					newLeafNode(this_UNSIGNED_INT_0,
							grammarAccess.getFix_PointAccess().getUNSIGNED_INTTerminalRuleCall_0());

					// InternalStructuredTextParser.g:3844:3: (kw= FullStop this_UNSIGNED_INT_2=
					// RULE_UNSIGNED_INT )?
					int alt58 = 2;
					int LA58_0 = input.LA(1);

					if ((LA58_0 == FullStop)) {
						alt58 = 1;
					}
					switch (alt58) {
					case 1:
					// InternalStructuredTextParser.g:3845:4: kw= FullStop this_UNSIGNED_INT_2=
					// RULE_UNSIGNED_INT
					{
						kw = (Token) match(input, FullStop, FOLLOW_10);

						current.merge(kw);
						newLeafNode(kw, grammarAccess.getFix_PointAccess().getFullStopKeyword_1_0());

						this_UNSIGNED_INT_2 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_2);

						current.merge(this_UNSIGNED_INT_2);

						newLeafNode(this_UNSIGNED_INT_2,
								grammarAccess.getFix_PointAccess().getUNSIGNED_INTTerminalRuleCall_1_1());

					}
						break;

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleFix_Point"

	// $ANTLR start "entryRuleTime_Of_Day"
	// InternalStructuredTextParser.g:3862:1: entryRuleTime_Of_Day returns [EObject
	// current=null] : iv_ruleTime_Of_Day= ruleTime_Of_Day EOF ;
	public final EObject entryRuleTime_Of_Day() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleTime_Of_Day = null;

		try {
			// InternalStructuredTextParser.g:3862:52: (iv_ruleTime_Of_Day= ruleTime_Of_Day
			// EOF )
			// InternalStructuredTextParser.g:3863:2: iv_ruleTime_Of_Day= ruleTime_Of_Day
			// EOF
			{
				newCompositeNode(grammarAccess.getTime_Of_DayRule());
				pushFollow(FOLLOW_1);
				iv_ruleTime_Of_Day = ruleTime_Of_Day();

				state._fsp--;

				current = iv_ruleTime_Of_Day;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleTime_Of_Day"

	// $ANTLR start "ruleTime_Of_Day"
	// InternalStructuredTextParser.g:3869:1: ruleTime_Of_Day returns [EObject
	// current=null] : ( ( (lv_type_0_0= ruleTod_Type_Name ) ) otherlv_1= NumberSign
	// ( (lv_value_2_0= ruleDaytime ) ) ) ;
	public final EObject ruleTime_Of_Day() throws RecognitionException {
		EObject current = null;

		Token otherlv_1 = null;
		Enumerator lv_type_0_0 = null;

		AntlrDatatypeRuleToken lv_value_2_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3875:2: ( ( ( (lv_type_0_0= ruleTod_Type_Name
			// ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) ) )
			// InternalStructuredTextParser.g:3876:2: ( ( (lv_type_0_0= ruleTod_Type_Name )
			// ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) )
			{
				// InternalStructuredTextParser.g:3876:2: ( ( (lv_type_0_0= ruleTod_Type_Name )
				// ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) ) )
				// InternalStructuredTextParser.g:3877:3: ( (lv_type_0_0= ruleTod_Type_Name ) )
				// otherlv_1= NumberSign ( (lv_value_2_0= ruleDaytime ) )
				{
					// InternalStructuredTextParser.g:3877:3: ( (lv_type_0_0= ruleTod_Type_Name ) )
					// InternalStructuredTextParser.g:3878:4: (lv_type_0_0= ruleTod_Type_Name )
					{
						// InternalStructuredTextParser.g:3878:4: (lv_type_0_0= ruleTod_Type_Name )
						// InternalStructuredTextParser.g:3879:5: lv_type_0_0= ruleTod_Type_Name
						{

							newCompositeNode(
									grammarAccess.getTime_Of_DayAccess().getTypeTod_Type_NameEnumRuleCall_0_0());

							pushFollow(FOLLOW_55);
							lv_type_0_0 = ruleTod_Type_Name();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getTime_Of_DayRule());
							}
							set(current, "type", lv_type_0_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Tod_Type_Name");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_1 = (Token) match(input, NumberSign, FOLLOW_10);

					newLeafNode(otherlv_1, grammarAccess.getTime_Of_DayAccess().getNumberSignKeyword_1());

					// InternalStructuredTextParser.g:3900:3: ( (lv_value_2_0= ruleDaytime ) )
					// InternalStructuredTextParser.g:3901:4: (lv_value_2_0= ruleDaytime )
					{
						// InternalStructuredTextParser.g:3901:4: (lv_value_2_0= ruleDaytime )
						// InternalStructuredTextParser.g:3902:5: lv_value_2_0= ruleDaytime
						{

							newCompositeNode(grammarAccess.getTime_Of_DayAccess().getValueDaytimeParserRuleCall_2_0());

							pushFollow(FOLLOW_2);
							lv_value_2_0 = ruleDaytime();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getTime_Of_DayRule());
							}
							set(current, "value", lv_value_2_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Daytime");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleTime_Of_Day"

	// $ANTLR start "entryRuleDaytime"
	// InternalStructuredTextParser.g:3923:1: entryRuleDaytime returns [String
	// current=null] : iv_ruleDaytime= ruleDaytime EOF ;
	public final String entryRuleDaytime() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleDaytime = null;

		try {
			// InternalStructuredTextParser.g:3923:47: (iv_ruleDaytime= ruleDaytime EOF )
			// InternalStructuredTextParser.g:3924:2: iv_ruleDaytime= ruleDaytime EOF
			{
				newCompositeNode(grammarAccess.getDaytimeRule());
				pushFollow(FOLLOW_1);
				iv_ruleDaytime = ruleDaytime();

				state._fsp--;

				current = iv_ruleDaytime.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDaytime"

	// $ANTLR start "ruleDaytime"
	// InternalStructuredTextParser.g:3930:1: ruleDaytime returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// (this_Day_Hour_0= ruleDay_Hour kw= Colon this_Day_Minute_2= ruleDay_Minute
	// kw= Colon this_Day_Second_4= ruleDay_Second ) ;
	public final AntlrDatatypeRuleToken ruleDaytime() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token kw = null;
		AntlrDatatypeRuleToken this_Day_Hour_0 = null;

		AntlrDatatypeRuleToken this_Day_Minute_2 = null;

		AntlrDatatypeRuleToken this_Day_Second_4 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3936:2: ( (this_Day_Hour_0= ruleDay_Hour kw=
			// Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4=
			// ruleDay_Second ) )
			// InternalStructuredTextParser.g:3937:2: (this_Day_Hour_0= ruleDay_Hour kw=
			// Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4=
			// ruleDay_Second )
			{
				// InternalStructuredTextParser.g:3937:2: (this_Day_Hour_0= ruleDay_Hour kw=
				// Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4=
				// ruleDay_Second )
				// InternalStructuredTextParser.g:3938:3: this_Day_Hour_0= ruleDay_Hour kw=
				// Colon this_Day_Minute_2= ruleDay_Minute kw= Colon this_Day_Second_4=
				// ruleDay_Second
				{

					newCompositeNode(grammarAccess.getDaytimeAccess().getDay_HourParserRuleCall_0());

					pushFollow(FOLLOW_7);
					this_Day_Hour_0 = ruleDay_Hour();

					state._fsp--;

					current.merge(this_Day_Hour_0);

					afterParserOrEnumRuleCall();

					kw = (Token) match(input, Colon, FOLLOW_10);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getDaytimeAccess().getColonKeyword_1());

					newCompositeNode(grammarAccess.getDaytimeAccess().getDay_MinuteParserRuleCall_2());

					pushFollow(FOLLOW_7);
					this_Day_Minute_2 = ruleDay_Minute();

					state._fsp--;

					current.merge(this_Day_Minute_2);

					afterParserOrEnumRuleCall();

					kw = (Token) match(input, Colon, FOLLOW_59);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getDaytimeAccess().getColonKeyword_3());

					newCompositeNode(grammarAccess.getDaytimeAccess().getDay_SecondParserRuleCall_4());

					pushFollow(FOLLOW_2);
					this_Day_Second_4 = ruleDay_Second();

					state._fsp--;

					current.merge(this_Day_Second_4);

					afterParserOrEnumRuleCall();

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDaytime"

	// $ANTLR start "entryRuleDay_Hour"
	// InternalStructuredTextParser.g:3982:1: entryRuleDay_Hour returns [String
	// current=null] : iv_ruleDay_Hour= ruleDay_Hour EOF ;
	public final String entryRuleDay_Hour() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleDay_Hour = null;

		try {
			// InternalStructuredTextParser.g:3982:48: (iv_ruleDay_Hour= ruleDay_Hour EOF )
			// InternalStructuredTextParser.g:3983:2: iv_ruleDay_Hour= ruleDay_Hour EOF
			{
				newCompositeNode(grammarAccess.getDay_HourRule());
				pushFollow(FOLLOW_1);
				iv_ruleDay_Hour = ruleDay_Hour();

				state._fsp--;

				current = iv_ruleDay_Hour.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDay_Hour"

	// $ANTLR start "ruleDay_Hour"
	// InternalStructuredTextParser.g:3989:1: ruleDay_Hour returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
	public final AntlrDatatypeRuleToken ruleDay_Hour() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_UNSIGNED_INT_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:3995:2: (this_UNSIGNED_INT_0=
			// RULE_UNSIGNED_INT )
			// InternalStructuredTextParser.g:3996:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
			{
				this_UNSIGNED_INT_0 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_2);

				current.merge(this_UNSIGNED_INT_0);

				newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getDay_HourAccess().getUNSIGNED_INTTerminalRuleCall());

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDay_Hour"

	// $ANTLR start "entryRuleDay_Minute"
	// InternalStructuredTextParser.g:4006:1: entryRuleDay_Minute returns [String
	// current=null] : iv_ruleDay_Minute= ruleDay_Minute EOF ;
	public final String entryRuleDay_Minute() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleDay_Minute = null;

		try {
			// InternalStructuredTextParser.g:4006:50: (iv_ruleDay_Minute= ruleDay_Minute
			// EOF )
			// InternalStructuredTextParser.g:4007:2: iv_ruleDay_Minute= ruleDay_Minute EOF
			{
				newCompositeNode(grammarAccess.getDay_MinuteRule());
				pushFollow(FOLLOW_1);
				iv_ruleDay_Minute = ruleDay_Minute();

				state._fsp--;

				current = iv_ruleDay_Minute.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDay_Minute"

	// $ANTLR start "ruleDay_Minute"
	// InternalStructuredTextParser.g:4013:1: ruleDay_Minute returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
	public final AntlrDatatypeRuleToken ruleDay_Minute() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_UNSIGNED_INT_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4019:2: (this_UNSIGNED_INT_0=
			// RULE_UNSIGNED_INT )
			// InternalStructuredTextParser.g:4020:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
			{
				this_UNSIGNED_INT_0 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_2);

				current.merge(this_UNSIGNED_INT_0);

				newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getDay_MinuteAccess().getUNSIGNED_INTTerminalRuleCall());

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDay_Minute"

	// $ANTLR start "entryRuleDay_Second"
	// InternalStructuredTextParser.g:4030:1: entryRuleDay_Second returns [String
	// current=null] : iv_ruleDay_Second= ruleDay_Second EOF ;
	public final String entryRuleDay_Second() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleDay_Second = null;

		try {
			// InternalStructuredTextParser.g:4030:50: (iv_ruleDay_Second= ruleDay_Second
			// EOF )
			// InternalStructuredTextParser.g:4031:2: iv_ruleDay_Second= ruleDay_Second EOF
			{
				newCompositeNode(grammarAccess.getDay_SecondRule());
				pushFollow(FOLLOW_1);
				iv_ruleDay_Second = ruleDay_Second();

				state._fsp--;

				current = iv_ruleDay_Second.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDay_Second"

	// $ANTLR start "ruleDay_Second"
	// InternalStructuredTextParser.g:4037:1: ruleDay_Second returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// this_Fix_Point_0= ruleFix_Point ;
	public final AntlrDatatypeRuleToken ruleDay_Second() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		AntlrDatatypeRuleToken this_Fix_Point_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4043:2: (this_Fix_Point_0= ruleFix_Point )
			// InternalStructuredTextParser.g:4044:2: this_Fix_Point_0= ruleFix_Point
			{

				newCompositeNode(grammarAccess.getDay_SecondAccess().getFix_PointParserRuleCall());

				pushFollow(FOLLOW_2);
				this_Fix_Point_0 = ruleFix_Point();

				state._fsp--;

				current.merge(this_Fix_Point_0);

				afterParserOrEnumRuleCall();

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDay_Second"

	// $ANTLR start "entryRuleDate"
	// InternalStructuredTextParser.g:4057:1: entryRuleDate returns [EObject
	// current=null] : iv_ruleDate= ruleDate EOF ;
	public final EObject entryRuleDate() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleDate = null;

		try {
			// InternalStructuredTextParser.g:4057:45: (iv_ruleDate= ruleDate EOF )
			// InternalStructuredTextParser.g:4058:2: iv_ruleDate= ruleDate EOF
			{
				newCompositeNode(grammarAccess.getDateRule());
				pushFollow(FOLLOW_1);
				iv_ruleDate = ruleDate();

				state._fsp--;

				current = iv_ruleDate;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDate"

	// $ANTLR start "ruleDate"
	// InternalStructuredTextParser.g:4064:1: ruleDate returns [EObject
	// current=null] : ( ( (lv_type_0_0= ruleDate_Type_Name ) ) otherlv_1=
	// NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) ) ;
	public final EObject ruleDate() throws RecognitionException {
		EObject current = null;

		Token otherlv_1 = null;
		Enumerator lv_type_0_0 = null;

		AntlrDatatypeRuleToken lv_value_2_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4070:2: ( ( ( (lv_type_0_0= ruleDate_Type_Name
			// ) ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) ) )
			// InternalStructuredTextParser.g:4071:2: ( ( (lv_type_0_0= ruleDate_Type_Name )
			// ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) )
			{
				// InternalStructuredTextParser.g:4071:2: ( ( (lv_type_0_0= ruleDate_Type_Name )
				// ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) ) )
				// InternalStructuredTextParser.g:4072:3: ( (lv_type_0_0= ruleDate_Type_Name ) )
				// otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_Literal ) )
				{
					// InternalStructuredTextParser.g:4072:3: ( (lv_type_0_0= ruleDate_Type_Name ) )
					// InternalStructuredTextParser.g:4073:4: (lv_type_0_0= ruleDate_Type_Name )
					{
						// InternalStructuredTextParser.g:4073:4: (lv_type_0_0= ruleDate_Type_Name )
						// InternalStructuredTextParser.g:4074:5: lv_type_0_0= ruleDate_Type_Name
						{

							newCompositeNode(grammarAccess.getDateAccess().getTypeDate_Type_NameEnumRuleCall_0_0());

							pushFollow(FOLLOW_55);
							lv_type_0_0 = ruleDate_Type_Name();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getDateRule());
							}
							set(current, "type", lv_type_0_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Date_Type_Name");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_1 = (Token) match(input, NumberSign, FOLLOW_10);

					newLeafNode(otherlv_1, grammarAccess.getDateAccess().getNumberSignKeyword_1());

					// InternalStructuredTextParser.g:4095:3: ( (lv_value_2_0= ruleDate_Literal ) )
					// InternalStructuredTextParser.g:4096:4: (lv_value_2_0= ruleDate_Literal )
					{
						// InternalStructuredTextParser.g:4096:4: (lv_value_2_0= ruleDate_Literal )
						// InternalStructuredTextParser.g:4097:5: lv_value_2_0= ruleDate_Literal
						{

							newCompositeNode(grammarAccess.getDateAccess().getValueDate_LiteralParserRuleCall_2_0());

							pushFollow(FOLLOW_2);
							lv_value_2_0 = ruleDate_Literal();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getDateRule());
							}
							set(current, "value", lv_value_2_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Date_Literal");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDate"

	// $ANTLR start "entryRuleDate_Literal"
	// InternalStructuredTextParser.g:4118:1: entryRuleDate_Literal returns [String
	// current=null] : iv_ruleDate_Literal= ruleDate_Literal EOF ;
	public final String entryRuleDate_Literal() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleDate_Literal = null;

		try {
			// InternalStructuredTextParser.g:4118:52: (iv_ruleDate_Literal=
			// ruleDate_Literal EOF )
			// InternalStructuredTextParser.g:4119:2: iv_ruleDate_Literal= ruleDate_Literal
			// EOF
			{
				newCompositeNode(grammarAccess.getDate_LiteralRule());
				pushFollow(FOLLOW_1);
				iv_ruleDate_Literal = ruleDate_Literal();

				state._fsp--;

				current = iv_ruleDate_Literal.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDate_Literal"

	// $ANTLR start "ruleDate_Literal"
	// InternalStructuredTextParser.g:4125:1: ruleDate_Literal returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_Year_0=
	// ruleYear kw= HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4=
	// ruleDay ) ;
	public final AntlrDatatypeRuleToken ruleDate_Literal() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token kw = null;
		AntlrDatatypeRuleToken this_Year_0 = null;

		AntlrDatatypeRuleToken this_Month_2 = null;

		AntlrDatatypeRuleToken this_Day_4 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4131:2: ( (this_Year_0= ruleYear kw=
			// HyphenMinus this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay ) )
			// InternalStructuredTextParser.g:4132:2: (this_Year_0= ruleYear kw= HyphenMinus
			// this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay )
			{
				// InternalStructuredTextParser.g:4132:2: (this_Year_0= ruleYear kw= HyphenMinus
				// this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay )
				// InternalStructuredTextParser.g:4133:3: this_Year_0= ruleYear kw= HyphenMinus
				// this_Month_2= ruleMonth kw= HyphenMinus this_Day_4= ruleDay
				{

					newCompositeNode(grammarAccess.getDate_LiteralAccess().getYearParserRuleCall_0());

					pushFollow(FOLLOW_65);
					this_Year_0 = ruleYear();

					state._fsp--;

					current.merge(this_Year_0);

					afterParserOrEnumRuleCall();

					kw = (Token) match(input, HyphenMinus, FOLLOW_10);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getDate_LiteralAccess().getHyphenMinusKeyword_1());

					newCompositeNode(grammarAccess.getDate_LiteralAccess().getMonthParserRuleCall_2());

					pushFollow(FOLLOW_65);
					this_Month_2 = ruleMonth();

					state._fsp--;

					current.merge(this_Month_2);

					afterParserOrEnumRuleCall();

					kw = (Token) match(input, HyphenMinus, FOLLOW_10);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getDate_LiteralAccess().getHyphenMinusKeyword_3());

					newCompositeNode(grammarAccess.getDate_LiteralAccess().getDayParserRuleCall_4());

					pushFollow(FOLLOW_2);
					this_Day_4 = ruleDay();

					state._fsp--;

					current.merge(this_Day_4);

					afterParserOrEnumRuleCall();

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDate_Literal"

	// $ANTLR start "entryRuleYear"
	// InternalStructuredTextParser.g:4177:1: entryRuleYear returns [String
	// current=null] : iv_ruleYear= ruleYear EOF ;
	public final String entryRuleYear() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleYear = null;

		try {
			// InternalStructuredTextParser.g:4177:44: (iv_ruleYear= ruleYear EOF )
			// InternalStructuredTextParser.g:4178:2: iv_ruleYear= ruleYear EOF
			{
				newCompositeNode(grammarAccess.getYearRule());
				pushFollow(FOLLOW_1);
				iv_ruleYear = ruleYear();

				state._fsp--;

				current = iv_ruleYear.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleYear"

	// $ANTLR start "ruleYear"
	// InternalStructuredTextParser.g:4184:1: ruleYear returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
	public final AntlrDatatypeRuleToken ruleYear() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_UNSIGNED_INT_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4190:2: (this_UNSIGNED_INT_0=
			// RULE_UNSIGNED_INT )
			// InternalStructuredTextParser.g:4191:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
			{
				this_UNSIGNED_INT_0 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_2);

				current.merge(this_UNSIGNED_INT_0);

				newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getYearAccess().getUNSIGNED_INTTerminalRuleCall());

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleYear"

	// $ANTLR start "entryRuleMonth"
	// InternalStructuredTextParser.g:4201:1: entryRuleMonth returns [String
	// current=null] : iv_ruleMonth= ruleMonth EOF ;
	public final String entryRuleMonth() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleMonth = null;

		try {
			// InternalStructuredTextParser.g:4201:45: (iv_ruleMonth= ruleMonth EOF )
			// InternalStructuredTextParser.g:4202:2: iv_ruleMonth= ruleMonth EOF
			{
				newCompositeNode(grammarAccess.getMonthRule());
				pushFollow(FOLLOW_1);
				iv_ruleMonth = ruleMonth();

				state._fsp--;

				current = iv_ruleMonth.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleMonth"

	// $ANTLR start "ruleMonth"
	// InternalStructuredTextParser.g:4208:1: ruleMonth returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
	public final AntlrDatatypeRuleToken ruleMonth() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_UNSIGNED_INT_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4214:2: (this_UNSIGNED_INT_0=
			// RULE_UNSIGNED_INT )
			// InternalStructuredTextParser.g:4215:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
			{
				this_UNSIGNED_INT_0 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_2);

				current.merge(this_UNSIGNED_INT_0);

				newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getMonthAccess().getUNSIGNED_INTTerminalRuleCall());

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleMonth"

	// $ANTLR start "entryRuleDay"
	// InternalStructuredTextParser.g:4225:1: entryRuleDay returns [String
	// current=null] : iv_ruleDay= ruleDay EOF ;
	public final String entryRuleDay() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleDay = null;

		try {
			// InternalStructuredTextParser.g:4225:43: (iv_ruleDay= ruleDay EOF )
			// InternalStructuredTextParser.g:4226:2: iv_ruleDay= ruleDay EOF
			{
				newCompositeNode(grammarAccess.getDayRule());
				pushFollow(FOLLOW_1);
				iv_ruleDay = ruleDay();

				state._fsp--;

				current = iv_ruleDay.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDay"

	// $ANTLR start "ruleDay"
	// InternalStructuredTextParser.g:4232:1: ruleDay returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// this_UNSIGNED_INT_0= RULE_UNSIGNED_INT ;
	public final AntlrDatatypeRuleToken ruleDay() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_UNSIGNED_INT_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4238:2: (this_UNSIGNED_INT_0=
			// RULE_UNSIGNED_INT )
			// InternalStructuredTextParser.g:4239:2: this_UNSIGNED_INT_0= RULE_UNSIGNED_INT
			{
				this_UNSIGNED_INT_0 = (Token) match(input, RULE_UNSIGNED_INT, FOLLOW_2);

				current.merge(this_UNSIGNED_INT_0);

				newLeafNode(this_UNSIGNED_INT_0, grammarAccess.getDayAccess().getUNSIGNED_INTTerminalRuleCall());

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDay"

	// $ANTLR start "entryRuleDate_And_Time"
	// InternalStructuredTextParser.g:4249:1: entryRuleDate_And_Time returns
	// [EObject current=null] : iv_ruleDate_And_Time= ruleDate_And_Time EOF ;
	public final EObject entryRuleDate_And_Time() throws RecognitionException {
		EObject current = null;

		EObject iv_ruleDate_And_Time = null;

		try {
			// InternalStructuredTextParser.g:4249:54: (iv_ruleDate_And_Time=
			// ruleDate_And_Time EOF )
			// InternalStructuredTextParser.g:4250:2: iv_ruleDate_And_Time=
			// ruleDate_And_Time EOF
			{
				newCompositeNode(grammarAccess.getDate_And_TimeRule());
				pushFollow(FOLLOW_1);
				iv_ruleDate_And_Time = ruleDate_And_Time();

				state._fsp--;

				current = iv_ruleDate_And_Time;
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDate_And_Time"

	// $ANTLR start "ruleDate_And_Time"
	// InternalStructuredTextParser.g:4256:1: ruleDate_And_Time returns [EObject
	// current=null] : ( ( (lv_type_0_0= ruleDT_Type_Name ) ) otherlv_1= NumberSign
	// ( (lv_value_2_0= ruleDate_And_Time_Value ) ) ) ;
	public final EObject ruleDate_And_Time() throws RecognitionException {
		EObject current = null;

		Token otherlv_1 = null;
		Enumerator lv_type_0_0 = null;

		AntlrDatatypeRuleToken lv_value_2_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4262:2: ( ( ( (lv_type_0_0= ruleDT_Type_Name )
			// ) otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) ) )
			// InternalStructuredTextParser.g:4263:2: ( ( (lv_type_0_0= ruleDT_Type_Name ) )
			// otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) )
			{
				// InternalStructuredTextParser.g:4263:2: ( ( (lv_type_0_0= ruleDT_Type_Name ) )
				// otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) ) )
				// InternalStructuredTextParser.g:4264:3: ( (lv_type_0_0= ruleDT_Type_Name ) )
				// otherlv_1= NumberSign ( (lv_value_2_0= ruleDate_And_Time_Value ) )
				{
					// InternalStructuredTextParser.g:4264:3: ( (lv_type_0_0= ruleDT_Type_Name ) )
					// InternalStructuredTextParser.g:4265:4: (lv_type_0_0= ruleDT_Type_Name )
					{
						// InternalStructuredTextParser.g:4265:4: (lv_type_0_0= ruleDT_Type_Name )
						// InternalStructuredTextParser.g:4266:5: lv_type_0_0= ruleDT_Type_Name
						{

							newCompositeNode(
									grammarAccess.getDate_And_TimeAccess().getTypeDT_Type_NameEnumRuleCall_0_0());

							pushFollow(FOLLOW_55);
							lv_type_0_0 = ruleDT_Type_Name();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getDate_And_TimeRule());
							}
							set(current, "type", lv_type_0_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.DT_Type_Name");
							afterParserOrEnumRuleCall();

						}

					}

					otherlv_1 = (Token) match(input, NumberSign, FOLLOW_10);

					newLeafNode(otherlv_1, grammarAccess.getDate_And_TimeAccess().getNumberSignKeyword_1());

					// InternalStructuredTextParser.g:4287:3: ( (lv_value_2_0=
					// ruleDate_And_Time_Value ) )
					// InternalStructuredTextParser.g:4288:4: (lv_value_2_0= ruleDate_And_Time_Value
					// )
					{
						// InternalStructuredTextParser.g:4288:4: (lv_value_2_0= ruleDate_And_Time_Value
						// )
						// InternalStructuredTextParser.g:4289:5: lv_value_2_0= ruleDate_And_Time_Value
						{

							newCompositeNode(grammarAccess.getDate_And_TimeAccess()
									.getValueDate_And_Time_ValueParserRuleCall_2_0());

							pushFollow(FOLLOW_2);
							lv_value_2_0 = ruleDate_And_Time_Value();

							state._fsp--;

							if (current == null) {
								current = createModelElementForParent(grammarAccess.getDate_And_TimeRule());
							}
							set(current, "value", lv_value_2_0,
									"org.eclipse.fordiac.ide.model.structuredtext.StructuredText.Date_And_Time_Value");
							afterParserOrEnumRuleCall();

						}

					}

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDate_And_Time"

	// $ANTLR start "entryRuleDate_And_Time_Value"
	// InternalStructuredTextParser.g:4310:1: entryRuleDate_And_Time_Value returns
	// [String current=null] : iv_ruleDate_And_Time_Value= ruleDate_And_Time_Value
	// EOF ;
	public final String entryRuleDate_And_Time_Value() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleDate_And_Time_Value = null;

		try {
			// InternalStructuredTextParser.g:4310:59: (iv_ruleDate_And_Time_Value=
			// ruleDate_And_Time_Value EOF )
			// InternalStructuredTextParser.g:4311:2: iv_ruleDate_And_Time_Value=
			// ruleDate_And_Time_Value EOF
			{
				newCompositeNode(grammarAccess.getDate_And_Time_ValueRule());
				pushFollow(FOLLOW_1);
				iv_ruleDate_And_Time_Value = ruleDate_And_Time_Value();

				state._fsp--;

				current = iv_ruleDate_And_Time_Value.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleDate_And_Time_Value"

	// $ANTLR start "ruleDate_And_Time_Value"
	// InternalStructuredTextParser.g:4317:1: ruleDate_And_Time_Value returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] :
	// (this_Date_Literal_0= ruleDate_Literal kw= HyphenMinus this_Daytime_2=
	// ruleDaytime ) ;
	public final AntlrDatatypeRuleToken ruleDate_And_Time_Value() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token kw = null;
		AntlrDatatypeRuleToken this_Date_Literal_0 = null;

		AntlrDatatypeRuleToken this_Daytime_2 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4323:2: ( (this_Date_Literal_0=
			// ruleDate_Literal kw= HyphenMinus this_Daytime_2= ruleDaytime ) )
			// InternalStructuredTextParser.g:4324:2: (this_Date_Literal_0= ruleDate_Literal
			// kw= HyphenMinus this_Daytime_2= ruleDaytime )
			{
				// InternalStructuredTextParser.g:4324:2: (this_Date_Literal_0= ruleDate_Literal
				// kw= HyphenMinus this_Daytime_2= ruleDaytime )
				// InternalStructuredTextParser.g:4325:3: this_Date_Literal_0= ruleDate_Literal
				// kw= HyphenMinus this_Daytime_2= ruleDaytime
				{

					newCompositeNode(grammarAccess.getDate_And_Time_ValueAccess().getDate_LiteralParserRuleCall_0());

					pushFollow(FOLLOW_65);
					this_Date_Literal_0 = ruleDate_Literal();

					state._fsp--;

					current.merge(this_Date_Literal_0);

					afterParserOrEnumRuleCall();

					kw = (Token) match(input, HyphenMinus, FOLLOW_10);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getDate_And_Time_ValueAccess().getHyphenMinusKeyword_1());

					newCompositeNode(grammarAccess.getDate_And_Time_ValueAccess().getDaytimeParserRuleCall_2());

					pushFollow(FOLLOW_2);
					this_Daytime_2 = ruleDaytime();

					state._fsp--;

					current.merge(this_Daytime_2);

					afterParserOrEnumRuleCall();

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDate_And_Time_Value"

	// $ANTLR start "entryRuleType_Name"
	// InternalStructuredTextParser.g:4354:1: entryRuleType_Name returns [String
	// current=null] : iv_ruleType_Name= ruleType_Name EOF ;
	public final String entryRuleType_Name() throws RecognitionException {
		String current = null;

		AntlrDatatypeRuleToken iv_ruleType_Name = null;

		try {
			// InternalStructuredTextParser.g:4354:49: (iv_ruleType_Name= ruleType_Name EOF
			// )
			// InternalStructuredTextParser.g:4355:2: iv_ruleType_Name= ruleType_Name EOF
			{
				newCompositeNode(grammarAccess.getType_NameRule());
				pushFollow(FOLLOW_1);
				iv_ruleType_Name = ruleType_Name();

				state._fsp--;

				current = iv_ruleType_Name.getText();
				match(input, EOF, FOLLOW_2);

			}

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "entryRuleType_Name"

	// $ANTLR start "ruleType_Name"
	// InternalStructuredTextParser.g:4361:1: ruleType_Name returns
	// [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0=
	// RULE_ID | kw= DINT | kw= INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT |
	// kw= UDINT | kw= ULINT | kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw=
	// CHAR | kw= WCHAR | kw= TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY
	// | kw= TOD | kw= LTOD | kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw=
	// LDATE_AND_TIME | kw= BOOL | kw= LWORD | kw= DWORD | kw= WORD | kw= BYTE ) ;
	public final AntlrDatatypeRuleToken ruleType_Name() throws RecognitionException {
		AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

		Token this_ID_0 = null;
		Token kw = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4367:2: ( (this_ID_0= RULE_ID | kw= DINT | kw=
			// INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT |
			// kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw=
			// TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD |
			// kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL |
			// kw= LWORD | kw= DWORD | kw= WORD | kw= BYTE ) )
			// InternalStructuredTextParser.g:4368:2: (this_ID_0= RULE_ID | kw= DINT | kw=
			// INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT |
			// kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw=
			// TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD |
			// kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL |
			// kw= LWORD | kw= DWORD | kw= WORD | kw= BYTE )
			{
				// InternalStructuredTextParser.g:4368:2: (this_ID_0= RULE_ID | kw= DINT | kw=
				// INT | kw= SINT | kw= LINT | kw= UINT | kw= USINT | kw= UDINT | kw= ULINT |
				// kw= REAL | kw= LREAL | kw= STRING | kw= WSTRING | kw= CHAR | kw= WCHAR | kw=
				// TIME | kw= LTIME | kw= TIME_OF_DAY | kw= LTIME_OF_DAY | kw= TOD | kw= LTOD |
				// kw= DATE | kw= LDATE | kw= DATE_AND_TIME | kw= LDATE_AND_TIME | kw= BOOL |
				// kw= LWORD | kw= DWORD | kw= WORD | kw= BYTE )
				int alt59 = 30;
				switch (input.LA(1)) {
				case RULE_ID: {
					alt59 = 1;
				}
					break;
				case DINT: {
					alt59 = 2;
				}
					break;
				case INT: {
					alt59 = 3;
				}
					break;
				case SINT: {
					alt59 = 4;
				}
					break;
				case LINT: {
					alt59 = 5;
				}
					break;
				case UINT: {
					alt59 = 6;
				}
					break;
				case USINT: {
					alt59 = 7;
				}
					break;
				case UDINT: {
					alt59 = 8;
				}
					break;
				case ULINT: {
					alt59 = 9;
				}
					break;
				case REAL: {
					alt59 = 10;
				}
					break;
				case LREAL: {
					alt59 = 11;
				}
					break;
				case STRING: {
					alt59 = 12;
				}
					break;
				case WSTRING: {
					alt59 = 13;
				}
					break;
				case CHAR: {
					alt59 = 14;
				}
					break;
				case WCHAR: {
					alt59 = 15;
				}
					break;
				case TIME: {
					alt59 = 16;
				}
					break;
				case LTIME: {
					alt59 = 17;
				}
					break;
				case TIME_OF_DAY: {
					alt59 = 18;
				}
					break;
				case LTIME_OF_DAY: {
					alt59 = 19;
				}
					break;
				case TOD: {
					alt59 = 20;
				}
					break;
				case LTOD: {
					alt59 = 21;
				}
					break;
				case DATE: {
					alt59 = 22;
				}
					break;
				case LDATE: {
					alt59 = 23;
				}
					break;
				case DATE_AND_TIME: {
					alt59 = 24;
				}
					break;
				case LDATE_AND_TIME: {
					alt59 = 25;
				}
					break;
				case BOOL: {
					alt59 = 26;
				}
					break;
				case LWORD: {
					alt59 = 27;
				}
					break;
				case DWORD: {
					alt59 = 28;
				}
					break;
				case WORD: {
					alt59 = 29;
				}
					break;
				case BYTE: {
					alt59 = 30;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 59, 0, input);

					throw nvae;
				}

				switch (alt59) {
				case 1:
				// InternalStructuredTextParser.g:4369:3: this_ID_0= RULE_ID
				{
					this_ID_0 = (Token) match(input, RULE_ID, FOLLOW_2);

					current.merge(this_ID_0);

					newLeafNode(this_ID_0, grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0());

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4377:3: kw= DINT
				{
					kw = (Token) match(input, DINT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getDINTKeyword_1());

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:4383:3: kw= INT
				{
					kw = (Token) match(input, INT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getINTKeyword_2());

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:4389:3: kw= SINT
				{
					kw = (Token) match(input, SINT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getSINTKeyword_3());

				}
					break;
				case 5:
				// InternalStructuredTextParser.g:4395:3: kw= LINT
				{
					kw = (Token) match(input, LINT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getLINTKeyword_4());

				}
					break;
				case 6:
				// InternalStructuredTextParser.g:4401:3: kw= UINT
				{
					kw = (Token) match(input, UINT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getUINTKeyword_5());

				}
					break;
				case 7:
				// InternalStructuredTextParser.g:4407:3: kw= USINT
				{
					kw = (Token) match(input, USINT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getUSINTKeyword_6());

				}
					break;
				case 8:
				// InternalStructuredTextParser.g:4413:3: kw= UDINT
				{
					kw = (Token) match(input, UDINT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getUDINTKeyword_7());

				}
					break;
				case 9:
				// InternalStructuredTextParser.g:4419:3: kw= ULINT
				{
					kw = (Token) match(input, ULINT, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getULINTKeyword_8());

				}
					break;
				case 10:
				// InternalStructuredTextParser.g:4425:3: kw= REAL
				{
					kw = (Token) match(input, REAL, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getREALKeyword_9());

				}
					break;
				case 11:
				// InternalStructuredTextParser.g:4431:3: kw= LREAL
				{
					kw = (Token) match(input, LREAL, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getLREALKeyword_10());

				}
					break;
				case 12:
				// InternalStructuredTextParser.g:4437:3: kw= STRING
				{
					kw = (Token) match(input, STRING, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getSTRINGKeyword_11());

				}
					break;
				case 13:
				// InternalStructuredTextParser.g:4443:3: kw= WSTRING
				{
					kw = (Token) match(input, WSTRING, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getWSTRINGKeyword_12());

				}
					break;
				case 14:
				// InternalStructuredTextParser.g:4449:3: kw= CHAR
				{
					kw = (Token) match(input, CHAR, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getCHARKeyword_13());

				}
					break;
				case 15:
				// InternalStructuredTextParser.g:4455:3: kw= WCHAR
				{
					kw = (Token) match(input, WCHAR, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getWCHARKeyword_14());

				}
					break;
				case 16:
				// InternalStructuredTextParser.g:4461:3: kw= TIME
				{
					kw = (Token) match(input, TIME, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getTIMEKeyword_15());

				}
					break;
				case 17:
				// InternalStructuredTextParser.g:4467:3: kw= LTIME
				{
					kw = (Token) match(input, LTIME, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getLTIMEKeyword_16());

				}
					break;
				case 18:
				// InternalStructuredTextParser.g:4473:3: kw= TIME_OF_DAY
				{
					kw = (Token) match(input, TIME_OF_DAY, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17());

				}
					break;
				case 19:
				// InternalStructuredTextParser.g:4479:3: kw= LTIME_OF_DAY
				{
					kw = (Token) match(input, LTIME_OF_DAY, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18());

				}
					break;
				case 20:
				// InternalStructuredTextParser.g:4485:3: kw= TOD
				{
					kw = (Token) match(input, TOD, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getTODKeyword_19());

				}
					break;
				case 21:
				// InternalStructuredTextParser.g:4491:3: kw= LTOD
				{
					kw = (Token) match(input, LTOD, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getLTODKeyword_20());

				}
					break;
				case 22:
				// InternalStructuredTextParser.g:4497:3: kw= DATE
				{
					kw = (Token) match(input, DATE, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getDATEKeyword_21());

				}
					break;
				case 23:
				// InternalStructuredTextParser.g:4503:3: kw= LDATE
				{
					kw = (Token) match(input, LDATE, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getLDATEKeyword_22());

				}
					break;
				case 24:
				// InternalStructuredTextParser.g:4509:3: kw= DATE_AND_TIME
				{
					kw = (Token) match(input, DATE_AND_TIME, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23());

				}
					break;
				case 25:
				// InternalStructuredTextParser.g:4515:3: kw= LDATE_AND_TIME
				{
					kw = (Token) match(input, LDATE_AND_TIME, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24());

				}
					break;
				case 26:
				// InternalStructuredTextParser.g:4521:3: kw= BOOL
				{
					kw = (Token) match(input, BOOL, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getBOOLKeyword_25());

				}
					break;
				case 27:
				// InternalStructuredTextParser.g:4527:3: kw= LWORD
				{
					kw = (Token) match(input, LWORD, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getLWORDKeyword_26());

				}
					break;
				case 28:
				// InternalStructuredTextParser.g:4533:3: kw= DWORD
				{
					kw = (Token) match(input, DWORD, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getDWORDKeyword_27());

				}
					break;
				case 29:
				// InternalStructuredTextParser.g:4539:3: kw= WORD
				{
					kw = (Token) match(input, WORD, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getWORDKeyword_28());

				}
					break;
				case 30:
				// InternalStructuredTextParser.g:4545:3: kw= BYTE
				{
					kw = (Token) match(input, BYTE, FOLLOW_2);

					current.merge(kw);
					newLeafNode(kw, grammarAccess.getType_NameAccess().getBYTEKeyword_29());

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleType_Name"

	// $ANTLR start "ruleOr_Operator"
	// InternalStructuredTextParser.g:4554:1: ruleOr_Operator returns [Enumerator
	// current=null] : (enumLiteral_0= OR ) ;
	public final Enumerator ruleOr_Operator() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4560:2: ( (enumLiteral_0= OR ) )
			// InternalStructuredTextParser.g:4561:2: (enumLiteral_0= OR )
			{
				// InternalStructuredTextParser.g:4561:2: (enumLiteral_0= OR )
				// InternalStructuredTextParser.g:4562:3: enumLiteral_0= OR
				{
					enumLiteral_0 = (Token) match(input, OR, FOLLOW_2);

					current = grammarAccess.getOr_OperatorAccess().getOREnumLiteralDeclaration().getEnumLiteral()
							.getInstance();
					newLeafNode(enumLiteral_0, grammarAccess.getOr_OperatorAccess().getOREnumLiteralDeclaration());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleOr_Operator"

	// $ANTLR start "ruleXor_Operator"
	// InternalStructuredTextParser.g:4571:1: ruleXor_Operator returns [Enumerator
	// current=null] : (enumLiteral_0= XOR ) ;
	public final Enumerator ruleXor_Operator() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4577:2: ( (enumLiteral_0= XOR ) )
			// InternalStructuredTextParser.g:4578:2: (enumLiteral_0= XOR )
			{
				// InternalStructuredTextParser.g:4578:2: (enumLiteral_0= XOR )
				// InternalStructuredTextParser.g:4579:3: enumLiteral_0= XOR
				{
					enumLiteral_0 = (Token) match(input, XOR, FOLLOW_2);

					current = grammarAccess.getXor_OperatorAccess().getXOREnumLiteralDeclaration().getEnumLiteral()
							.getInstance();
					newLeafNode(enumLiteral_0, grammarAccess.getXor_OperatorAccess().getXOREnumLiteralDeclaration());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleXor_Operator"

	// $ANTLR start "ruleAnd_Operator"
	// InternalStructuredTextParser.g:4588:1: ruleAnd_Operator returns [Enumerator
	// current=null] : ( (enumLiteral_0= AND ) | (enumLiteral_1= Ampersand ) ) ;
	public final Enumerator ruleAnd_Operator() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4594:2: ( ( (enumLiteral_0= AND ) |
			// (enumLiteral_1= Ampersand ) ) )
			// InternalStructuredTextParser.g:4595:2: ( (enumLiteral_0= AND ) |
			// (enumLiteral_1= Ampersand ) )
			{
				// InternalStructuredTextParser.g:4595:2: ( (enumLiteral_0= AND ) |
				// (enumLiteral_1= Ampersand ) )
				int alt60 = 2;
				int LA60_0 = input.LA(1);

				if ((LA60_0 == AND)) {
					alt60 = 1;
				} else if ((LA60_0 == Ampersand)) {
					alt60 = 2;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 60, 0, input);

					throw nvae;
				}
				switch (alt60) {
				case 1:
				// InternalStructuredTextParser.g:4596:3: (enumLiteral_0= AND )
				{
					// InternalStructuredTextParser.g:4596:3: (enumLiteral_0= AND )
					// InternalStructuredTextParser.g:4597:4: enumLiteral_0= AND
					{
						enumLiteral_0 = (Token) match(input, AND, FOLLOW_2);

						current = grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4604:3: (enumLiteral_1= Ampersand )
				{
					// InternalStructuredTextParser.g:4604:3: (enumLiteral_1= Ampersand )
					// InternalStructuredTextParser.g:4605:4: enumLiteral_1= Ampersand
					{
						enumLiteral_1 = (Token) match(input, Ampersand, FOLLOW_2);

						current = grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_1());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleAnd_Operator"

	// $ANTLR start "ruleCompare_Operator"
	// InternalStructuredTextParser.g:4615:1: ruleCompare_Operator returns
	// [Enumerator current=null] : ( (enumLiteral_0= EqualsSign ) | (enumLiteral_1=
	// LessThanSignGreaterThanSign ) ) ;
	public final Enumerator ruleCompare_Operator() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4621:2: ( ( (enumLiteral_0= EqualsSign ) |
			// (enumLiteral_1= LessThanSignGreaterThanSign ) ) )
			// InternalStructuredTextParser.g:4622:2: ( (enumLiteral_0= EqualsSign ) |
			// (enumLiteral_1= LessThanSignGreaterThanSign ) )
			{
				// InternalStructuredTextParser.g:4622:2: ( (enumLiteral_0= EqualsSign ) |
				// (enumLiteral_1= LessThanSignGreaterThanSign ) )
				int alt61 = 2;
				int LA61_0 = input.LA(1);

				if ((LA61_0 == EqualsSign)) {
					alt61 = 1;
				} else if ((LA61_0 == LessThanSignGreaterThanSign)) {
					alt61 = 2;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 61, 0, input);

					throw nvae;
				}
				switch (alt61) {
				case 1:
				// InternalStructuredTextParser.g:4623:3: (enumLiteral_0= EqualsSign )
				{
					// InternalStructuredTextParser.g:4623:3: (enumLiteral_0= EqualsSign )
					// InternalStructuredTextParser.g:4624:4: enumLiteral_0= EqualsSign
					{
						enumLiteral_0 = (Token) match(input, EqualsSign, FOLLOW_2);

						current = grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4631:3: (enumLiteral_1=
				// LessThanSignGreaterThanSign )
				{
					// InternalStructuredTextParser.g:4631:3: (enumLiteral_1=
					// LessThanSignGreaterThanSign )
					// InternalStructuredTextParser.g:4632:4: enumLiteral_1=
					// LessThanSignGreaterThanSign
					{
						enumLiteral_1 = (Token) match(input, LessThanSignGreaterThanSign, FOLLOW_2);

						current = grammarAccess.getCompare_OperatorAccess().getNEEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getCompare_OperatorAccess().getNEEnumLiteralDeclaration_1());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleCompare_Operator"

	// $ANTLR start "ruleEqu_Operator"
	// InternalStructuredTextParser.g:4642:1: ruleEqu_Operator returns [Enumerator
	// current=null] : ( (enumLiteral_0= LessThanSign ) | (enumLiteral_1=
	// LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign ) |
	// (enumLiteral_3= GreaterThanSignEqualsSign ) ) ;
	public final Enumerator ruleEqu_Operator() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;
		Token enumLiteral_2 = null;
		Token enumLiteral_3 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4648:2: ( ( (enumLiteral_0= LessThanSign ) |
			// (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign )
			// | (enumLiteral_3= GreaterThanSignEqualsSign ) ) )
			// InternalStructuredTextParser.g:4649:2: ( (enumLiteral_0= LessThanSign ) |
			// (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign )
			// | (enumLiteral_3= GreaterThanSignEqualsSign ) )
			{
				// InternalStructuredTextParser.g:4649:2: ( (enumLiteral_0= LessThanSign ) |
				// (enumLiteral_1= LessThanSignEqualsSign ) | (enumLiteral_2= GreaterThanSign )
				// | (enumLiteral_3= GreaterThanSignEqualsSign ) )
				int alt62 = 4;
				switch (input.LA(1)) {
				case LessThanSign: {
					alt62 = 1;
				}
					break;
				case LessThanSignEqualsSign: {
					alt62 = 2;
				}
					break;
				case GreaterThanSign: {
					alt62 = 3;
				}
					break;
				case GreaterThanSignEqualsSign: {
					alt62 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 62, 0, input);

					throw nvae;
				}

				switch (alt62) {
				case 1:
				// InternalStructuredTextParser.g:4650:3: (enumLiteral_0= LessThanSign )
				{
					// InternalStructuredTextParser.g:4650:3: (enumLiteral_0= LessThanSign )
					// InternalStructuredTextParser.g:4651:4: enumLiteral_0= LessThanSign
					{
						enumLiteral_0 = (Token) match(input, LessThanSign, FOLLOW_2);

						current = grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0().getEnumLiteral()
								.getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4658:3: (enumLiteral_1= LessThanSignEqualsSign
				// )
				{
					// InternalStructuredTextParser.g:4658:3: (enumLiteral_1= LessThanSignEqualsSign
					// )
					// InternalStructuredTextParser.g:4659:4: enumLiteral_1= LessThanSignEqualsSign
					{
						enumLiteral_1 = (Token) match(input, LessThanSignEqualsSign, FOLLOW_2);

						current = grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1().getEnumLiteral()
								.getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:4666:3: (enumLiteral_2= GreaterThanSign )
				{
					// InternalStructuredTextParser.g:4666:3: (enumLiteral_2= GreaterThanSign )
					// InternalStructuredTextParser.g:4667:4: enumLiteral_2= GreaterThanSign
					{
						enumLiteral_2 = (Token) match(input, GreaterThanSign, FOLLOW_2);

						current = grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2().getEnumLiteral()
								.getInstance();
						newLeafNode(enumLiteral_2,
								grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2());

					}

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:4674:3: (enumLiteral_3=
				// GreaterThanSignEqualsSign )
				{
					// InternalStructuredTextParser.g:4674:3: (enumLiteral_3=
					// GreaterThanSignEqualsSign )
					// InternalStructuredTextParser.g:4675:4: enumLiteral_3=
					// GreaterThanSignEqualsSign
					{
						enumLiteral_3 = (Token) match(input, GreaterThanSignEqualsSign, FOLLOW_2);

						current = grammarAccess.getEqu_OperatorAccess().getGEEnumLiteralDeclaration_3().getEnumLiteral()
								.getInstance();
						newLeafNode(enumLiteral_3,
								grammarAccess.getEqu_OperatorAccess().getGEEnumLiteralDeclaration_3());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleEqu_Operator"

	// $ANTLR start "ruleAdd_Operator"
	// InternalStructuredTextParser.g:4685:1: ruleAdd_Operator returns [Enumerator
	// current=null] : ( (enumLiteral_0= PlusSign ) | (enumLiteral_1= HyphenMinus )
	// ) ;
	public final Enumerator ruleAdd_Operator() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4691:2: ( ( (enumLiteral_0= PlusSign ) |
			// (enumLiteral_1= HyphenMinus ) ) )
			// InternalStructuredTextParser.g:4692:2: ( (enumLiteral_0= PlusSign ) |
			// (enumLiteral_1= HyphenMinus ) )
			{
				// InternalStructuredTextParser.g:4692:2: ( (enumLiteral_0= PlusSign ) |
				// (enumLiteral_1= HyphenMinus ) )
				int alt63 = 2;
				int LA63_0 = input.LA(1);

				if ((LA63_0 == PlusSign)) {
					alt63 = 1;
				} else if ((LA63_0 == HyphenMinus)) {
					alt63 = 2;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 63, 0, input);

					throw nvae;
				}
				switch (alt63) {
				case 1:
				// InternalStructuredTextParser.g:4693:3: (enumLiteral_0= PlusSign )
				{
					// InternalStructuredTextParser.g:4693:3: (enumLiteral_0= PlusSign )
					// InternalStructuredTextParser.g:4694:4: enumLiteral_0= PlusSign
					{
						enumLiteral_0 = (Token) match(input, PlusSign, FOLLOW_2);

						current = grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4701:3: (enumLiteral_1= HyphenMinus )
				{
					// InternalStructuredTextParser.g:4701:3: (enumLiteral_1= HyphenMinus )
					// InternalStructuredTextParser.g:4702:4: enumLiteral_1= HyphenMinus
					{
						enumLiteral_1 = (Token) match(input, HyphenMinus, FOLLOW_2);

						current = grammarAccess.getAdd_OperatorAccess().getSUBEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getAdd_OperatorAccess().getSUBEnumLiteralDeclaration_1());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleAdd_Operator"

	// $ANTLR start "ruleTerm_Operator"
	// InternalStructuredTextParser.g:4712:1: ruleTerm_Operator returns [Enumerator
	// current=null] : ( (enumLiteral_0= Asterisk ) | (enumLiteral_1= Solidus ) |
	// (enumLiteral_2= MOD ) ) ;
	public final Enumerator ruleTerm_Operator() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;
		Token enumLiteral_2 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4718:2: ( ( (enumLiteral_0= Asterisk ) |
			// (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) ) )
			// InternalStructuredTextParser.g:4719:2: ( (enumLiteral_0= Asterisk ) |
			// (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
			{
				// InternalStructuredTextParser.g:4719:2: ( (enumLiteral_0= Asterisk ) |
				// (enumLiteral_1= Solidus ) | (enumLiteral_2= MOD ) )
				int alt64 = 3;
				switch (input.LA(1)) {
				case Asterisk: {
					alt64 = 1;
				}
					break;
				case Solidus: {
					alt64 = 2;
				}
					break;
				case MOD: {
					alt64 = 3;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 64, 0, input);

					throw nvae;
				}

				switch (alt64) {
				case 1:
				// InternalStructuredTextParser.g:4720:3: (enumLiteral_0= Asterisk )
				{
					// InternalStructuredTextParser.g:4720:3: (enumLiteral_0= Asterisk )
					// InternalStructuredTextParser.g:4721:4: enumLiteral_0= Asterisk
					{
						enumLiteral_0 = (Token) match(input, Asterisk, FOLLOW_2);

						current = grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4728:3: (enumLiteral_1= Solidus )
				{
					// InternalStructuredTextParser.g:4728:3: (enumLiteral_1= Solidus )
					// InternalStructuredTextParser.g:4729:4: enumLiteral_1= Solidus
					{
						enumLiteral_1 = (Token) match(input, Solidus, FOLLOW_2);

						current = grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:4736:3: (enumLiteral_2= MOD )
				{
					// InternalStructuredTextParser.g:4736:3: (enumLiteral_2= MOD )
					// InternalStructuredTextParser.g:4737:4: enumLiteral_2= MOD
					{
						enumLiteral_2 = (Token) match(input, MOD, FOLLOW_2);

						current = grammarAccess.getTerm_OperatorAccess().getMODEnumLiteralDeclaration_2()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_2,
								grammarAccess.getTerm_OperatorAccess().getMODEnumLiteralDeclaration_2());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleTerm_Operator"

	// $ANTLR start "rulePower_Operator"
	// InternalStructuredTextParser.g:4747:1: rulePower_Operator returns [Enumerator
	// current=null] : (enumLiteral_0= AsteriskAsterisk ) ;
	public final Enumerator rulePower_Operator() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4753:2: ( (enumLiteral_0= AsteriskAsterisk ) )
			// InternalStructuredTextParser.g:4754:2: (enumLiteral_0= AsteriskAsterisk )
			{
				// InternalStructuredTextParser.g:4754:2: (enumLiteral_0= AsteriskAsterisk )
				// InternalStructuredTextParser.g:4755:3: enumLiteral_0= AsteriskAsterisk
				{
					enumLiteral_0 = (Token) match(input, AsteriskAsterisk, FOLLOW_2);

					current = grammarAccess.getPower_OperatorAccess().getPOWEREnumLiteralDeclaration().getEnumLiteral()
							.getInstance();
					newLeafNode(enumLiteral_0,
							grammarAccess.getPower_OperatorAccess().getPOWEREnumLiteralDeclaration());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "rulePower_Operator"

	// $ANTLR start "ruleUnary_Operator"
	// InternalStructuredTextParser.g:4764:1: ruleUnary_Operator returns [Enumerator
	// current=null] : ( (enumLiteral_0= HyphenMinus ) | (enumLiteral_1= PlusSign )
	// | (enumLiteral_2= NOT ) ) ;
	public final Enumerator ruleUnary_Operator() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;
		Token enumLiteral_2 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4770:2: ( ( (enumLiteral_0= HyphenMinus ) |
			// (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) ) )
			// InternalStructuredTextParser.g:4771:2: ( (enumLiteral_0= HyphenMinus ) |
			// (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
			{
				// InternalStructuredTextParser.g:4771:2: ( (enumLiteral_0= HyphenMinus ) |
				// (enumLiteral_1= PlusSign ) | (enumLiteral_2= NOT ) )
				int alt65 = 3;
				switch (input.LA(1)) {
				case HyphenMinus: {
					alt65 = 1;
				}
					break;
				case PlusSign: {
					alt65 = 2;
				}
					break;
				case NOT: {
					alt65 = 3;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 65, 0, input);

					throw nvae;
				}

				switch (alt65) {
				case 1:
				// InternalStructuredTextParser.g:4772:3: (enumLiteral_0= HyphenMinus )
				{
					// InternalStructuredTextParser.g:4772:3: (enumLiteral_0= HyphenMinus )
					// InternalStructuredTextParser.g:4773:4: enumLiteral_0= HyphenMinus
					{
						enumLiteral_0 = (Token) match(input, HyphenMinus, FOLLOW_2);

						current = grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4780:3: (enumLiteral_1= PlusSign )
				{
					// InternalStructuredTextParser.g:4780:3: (enumLiteral_1= PlusSign )
					// InternalStructuredTextParser.g:4781:4: enumLiteral_1= PlusSign
					{
						enumLiteral_1 = (Token) match(input, PlusSign, FOLLOW_2);

						current = grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:4788:3: (enumLiteral_2= NOT )
				{
					// InternalStructuredTextParser.g:4788:3: (enumLiteral_2= NOT )
					// InternalStructuredTextParser.g:4789:4: enumLiteral_2= NOT
					{
						enumLiteral_2 = (Token) match(input, NOT, FOLLOW_2);

						current = grammarAccess.getUnary_OperatorAccess().getNOTEnumLiteralDeclaration_2()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_2,
								grammarAccess.getUnary_OperatorAccess().getNOTEnumLiteralDeclaration_2());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleUnary_Operator"

	// $ANTLR start "ruleDuration_Unit"
	// InternalStructuredTextParser.g:4799:1: ruleDuration_Unit returns [Enumerator
	// current=null] : ( (enumLiteral_0= D_1 ) | (enumLiteral_1= H ) |
	// (enumLiteral_2= M ) | (enumLiteral_3= S ) | (enumLiteral_4= Ms ) |
	// (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) ) ;
	public final Enumerator ruleDuration_Unit() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;
		Token enumLiteral_2 = null;
		Token enumLiteral_3 = null;
		Token enumLiteral_4 = null;
		Token enumLiteral_5 = null;
		Token enumLiteral_6 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4805:2: ( ( (enumLiteral_0= D_1 ) |
			// (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) |
			// (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) ) )
			// InternalStructuredTextParser.g:4806:2: ( (enumLiteral_0= D_1 ) |
			// (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) |
			// (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) )
			{
				// InternalStructuredTextParser.g:4806:2: ( (enumLiteral_0= D_1 ) |
				// (enumLiteral_1= H ) | (enumLiteral_2= M ) | (enumLiteral_3= S ) |
				// (enumLiteral_4= Ms ) | (enumLiteral_5= Us ) | (enumLiteral_6= Ns ) )
				int alt66 = 7;
				switch (input.LA(1)) {
				case D_1: {
					alt66 = 1;
				}
					break;
				case H: {
					alt66 = 2;
				}
					break;
				case M: {
					alt66 = 3;
				}
					break;
				case S: {
					alt66 = 4;
				}
					break;
				case Ms: {
					alt66 = 5;
				}
					break;
				case Us: {
					alt66 = 6;
				}
					break;
				case Ns: {
					alt66 = 7;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 66, 0, input);

					throw nvae;
				}

				switch (alt66) {
				case 1:
				// InternalStructuredTextParser.g:4807:3: (enumLiteral_0= D_1 )
				{
					// InternalStructuredTextParser.g:4807:3: (enumLiteral_0= D_1 )
					// InternalStructuredTextParser.g:4808:4: enumLiteral_0= D_1
					{
						enumLiteral_0 = (Token) match(input, D_1, FOLLOW_2);

						current = grammarAccess.getDuration_UnitAccess().getDAYSEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getDuration_UnitAccess().getDAYSEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4815:3: (enumLiteral_1= H )
				{
					// InternalStructuredTextParser.g:4815:3: (enumLiteral_1= H )
					// InternalStructuredTextParser.g:4816:4: enumLiteral_1= H
					{
						enumLiteral_1 = (Token) match(input, H, FOLLOW_2);

						current = grammarAccess.getDuration_UnitAccess().getHOURSEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getDuration_UnitAccess().getHOURSEnumLiteralDeclaration_1());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:4823:3: (enumLiteral_2= M )
				{
					// InternalStructuredTextParser.g:4823:3: (enumLiteral_2= M )
					// InternalStructuredTextParser.g:4824:4: enumLiteral_2= M
					{
						enumLiteral_2 = (Token) match(input, M, FOLLOW_2);

						current = grammarAccess.getDuration_UnitAccess().getMINUTESEnumLiteralDeclaration_2()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_2,
								grammarAccess.getDuration_UnitAccess().getMINUTESEnumLiteralDeclaration_2());

					}

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:4831:3: (enumLiteral_3= S )
				{
					// InternalStructuredTextParser.g:4831:3: (enumLiteral_3= S )
					// InternalStructuredTextParser.g:4832:4: enumLiteral_3= S
					{
						enumLiteral_3 = (Token) match(input, S, FOLLOW_2);

						current = grammarAccess.getDuration_UnitAccess().getSECONDSEnumLiteralDeclaration_3()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_3,
								grammarAccess.getDuration_UnitAccess().getSECONDSEnumLiteralDeclaration_3());

					}

				}
					break;
				case 5:
				// InternalStructuredTextParser.g:4839:3: (enumLiteral_4= Ms )
				{
					// InternalStructuredTextParser.g:4839:3: (enumLiteral_4= Ms )
					// InternalStructuredTextParser.g:4840:4: enumLiteral_4= Ms
					{
						enumLiteral_4 = (Token) match(input, Ms, FOLLOW_2);

						current = grammarAccess.getDuration_UnitAccess().getMILLISEnumLiteralDeclaration_4()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_4,
								grammarAccess.getDuration_UnitAccess().getMILLISEnumLiteralDeclaration_4());

					}

				}
					break;
				case 6:
				// InternalStructuredTextParser.g:4847:3: (enumLiteral_5= Us )
				{
					// InternalStructuredTextParser.g:4847:3: (enumLiteral_5= Us )
					// InternalStructuredTextParser.g:4848:4: enumLiteral_5= Us
					{
						enumLiteral_5 = (Token) match(input, Us, FOLLOW_2);

						current = grammarAccess.getDuration_UnitAccess().getMICROSEnumLiteralDeclaration_5()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_5,
								grammarAccess.getDuration_UnitAccess().getMICROSEnumLiteralDeclaration_5());

					}

				}
					break;
				case 7:
				// InternalStructuredTextParser.g:4855:3: (enumLiteral_6= Ns )
				{
					// InternalStructuredTextParser.g:4855:3: (enumLiteral_6= Ns )
					// InternalStructuredTextParser.g:4856:4: enumLiteral_6= Ns
					{
						enumLiteral_6 = (Token) match(input, Ns, FOLLOW_2);

						current = grammarAccess.getDuration_UnitAccess().getNANOSEnumLiteralDeclaration_6()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_6,
								grammarAccess.getDuration_UnitAccess().getNANOSEnumLiteralDeclaration_6());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDuration_Unit"

	// $ANTLR start "ruleInt_Type_Name"
	// InternalStructuredTextParser.g:4866:1: ruleInt_Type_Name returns [Enumerator
	// current=null] : ( (enumLiteral_0= DINT ) | (enumLiteral_1= INT ) |
	// (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) | (enumLiteral_4= UINT ) |
	// (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) | (enumLiteral_7= ULINT ) )
	// ;
	public final Enumerator ruleInt_Type_Name() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;
		Token enumLiteral_2 = null;
		Token enumLiteral_3 = null;
		Token enumLiteral_4 = null;
		Token enumLiteral_5 = null;
		Token enumLiteral_6 = null;
		Token enumLiteral_7 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4872:2: ( ( (enumLiteral_0= DINT ) |
			// (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) |
			// (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) |
			// (enumLiteral_7= ULINT ) ) )
			// InternalStructuredTextParser.g:4873:2: ( (enumLiteral_0= DINT ) |
			// (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) |
			// (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) |
			// (enumLiteral_7= ULINT ) )
			{
				// InternalStructuredTextParser.g:4873:2: ( (enumLiteral_0= DINT ) |
				// (enumLiteral_1= INT ) | (enumLiteral_2= SINT ) | (enumLiteral_3= LINT ) |
				// (enumLiteral_4= UINT ) | (enumLiteral_5= USINT ) | (enumLiteral_6= UDINT ) |
				// (enumLiteral_7= ULINT ) )
				int alt67 = 8;
				switch (input.LA(1)) {
				case DINT: {
					alt67 = 1;
				}
					break;
				case INT: {
					alt67 = 2;
				}
					break;
				case SINT: {
					alt67 = 3;
				}
					break;
				case LINT: {
					alt67 = 4;
				}
					break;
				case UINT: {
					alt67 = 5;
				}
					break;
				case USINT: {
					alt67 = 6;
				}
					break;
				case UDINT: {
					alt67 = 7;
				}
					break;
				case ULINT: {
					alt67 = 8;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 67, 0, input);

					throw nvae;
				}

				switch (alt67) {
				case 1:
				// InternalStructuredTextParser.g:4874:3: (enumLiteral_0= DINT )
				{
					// InternalStructuredTextParser.g:4874:3: (enumLiteral_0= DINT )
					// InternalStructuredTextParser.g:4875:4: enumLiteral_0= DINT
					{
						enumLiteral_0 = (Token) match(input, DINT, FOLLOW_2);

						current = grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4882:3: (enumLiteral_1= INT )
				{
					// InternalStructuredTextParser.g:4882:3: (enumLiteral_1= INT )
					// InternalStructuredTextParser.g:4883:4: enumLiteral_1= INT
					{
						enumLiteral_1 = (Token) match(input, INT, FOLLOW_2);

						current = grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:4890:3: (enumLiteral_2= SINT )
				{
					// InternalStructuredTextParser.g:4890:3: (enumLiteral_2= SINT )
					// InternalStructuredTextParser.g:4891:4: enumLiteral_2= SINT
					{
						enumLiteral_2 = (Token) match(input, SINT, FOLLOW_2);

						current = grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_2,
								grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2());

					}

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:4898:3: (enumLiteral_3= LINT )
				{
					// InternalStructuredTextParser.g:4898:3: (enumLiteral_3= LINT )
					// InternalStructuredTextParser.g:4899:4: enumLiteral_3= LINT
					{
						enumLiteral_3 = (Token) match(input, LINT, FOLLOW_2);

						current = grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_3,
								grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3());

					}

				}
					break;
				case 5:
				// InternalStructuredTextParser.g:4906:3: (enumLiteral_4= UINT )
				{
					// InternalStructuredTextParser.g:4906:3: (enumLiteral_4= UINT )
					// InternalStructuredTextParser.g:4907:4: enumLiteral_4= UINT
					{
						enumLiteral_4 = (Token) match(input, UINT, FOLLOW_2);

						current = grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_4,
								grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4());

					}

				}
					break;
				case 6:
				// InternalStructuredTextParser.g:4914:3: (enumLiteral_5= USINT )
				{
					// InternalStructuredTextParser.g:4914:3: (enumLiteral_5= USINT )
					// InternalStructuredTextParser.g:4915:4: enumLiteral_5= USINT
					{
						enumLiteral_5 = (Token) match(input, USINT, FOLLOW_2);

						current = grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_5,
								grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5());

					}

				}
					break;
				case 7:
				// InternalStructuredTextParser.g:4922:3: (enumLiteral_6= UDINT )
				{
					// InternalStructuredTextParser.g:4922:3: (enumLiteral_6= UDINT )
					// InternalStructuredTextParser.g:4923:4: enumLiteral_6= UDINT
					{
						enumLiteral_6 = (Token) match(input, UDINT, FOLLOW_2);

						current = grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_6,
								grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6());

					}

				}
					break;
				case 8:
				// InternalStructuredTextParser.g:4930:3: (enumLiteral_7= ULINT )
				{
					// InternalStructuredTextParser.g:4930:3: (enumLiteral_7= ULINT )
					// InternalStructuredTextParser.g:4931:4: enumLiteral_7= ULINT
					{
						enumLiteral_7 = (Token) match(input, ULINT, FOLLOW_2);

						current = grammarAccess.getInt_Type_NameAccess().getULINTEnumLiteralDeclaration_7()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_7,
								grammarAccess.getInt_Type_NameAccess().getULINTEnumLiteralDeclaration_7());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleInt_Type_Name"

	// $ANTLR start "ruleReal_Type_Name"
	// InternalStructuredTextParser.g:4941:1: ruleReal_Type_Name returns [Enumerator
	// current=null] : ( (enumLiteral_0= REAL ) | (enumLiteral_1= LREAL ) ) ;
	public final Enumerator ruleReal_Type_Name() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4947:2: ( ( (enumLiteral_0= REAL ) |
			// (enumLiteral_1= LREAL ) ) )
			// InternalStructuredTextParser.g:4948:2: ( (enumLiteral_0= REAL ) |
			// (enumLiteral_1= LREAL ) )
			{
				// InternalStructuredTextParser.g:4948:2: ( (enumLiteral_0= REAL ) |
				// (enumLiteral_1= LREAL ) )
				int alt68 = 2;
				int LA68_0 = input.LA(1);

				if ((LA68_0 == REAL)) {
					alt68 = 1;
				} else if ((LA68_0 == LREAL)) {
					alt68 = 2;
				} else {
					NoViableAltException nvae = new NoViableAltException("", 68, 0, input);

					throw nvae;
				}
				switch (alt68) {
				case 1:
				// InternalStructuredTextParser.g:4949:3: (enumLiteral_0= REAL )
				{
					// InternalStructuredTextParser.g:4949:3: (enumLiteral_0= REAL )
					// InternalStructuredTextParser.g:4950:4: enumLiteral_0= REAL
					{
						enumLiteral_0 = (Token) match(input, REAL, FOLLOW_2);

						current = grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4957:3: (enumLiteral_1= LREAL )
				{
					// InternalStructuredTextParser.g:4957:3: (enumLiteral_1= LREAL )
					// InternalStructuredTextParser.g:4958:4: enumLiteral_1= LREAL
					{
						enumLiteral_1 = (Token) match(input, LREAL, FOLLOW_2);

						current = grammarAccess.getReal_Type_NameAccess().getLREALEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getReal_Type_NameAccess().getLREALEnumLiteralDeclaration_1());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleReal_Type_Name"

	// $ANTLR start "ruleString_Type_Name"
	// InternalStructuredTextParser.g:4968:1: ruleString_Type_Name returns
	// [Enumerator current=null] : ( (enumLiteral_0= STRING ) | (enumLiteral_1=
	// WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR ) ) ;
	public final Enumerator ruleString_Type_Name() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;
		Token enumLiteral_2 = null;
		Token enumLiteral_3 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:4974:2: ( ( (enumLiteral_0= STRING ) |
			// (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR )
			// ) )
			// InternalStructuredTextParser.g:4975:2: ( (enumLiteral_0= STRING ) |
			// (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR )
			// )
			{
				// InternalStructuredTextParser.g:4975:2: ( (enumLiteral_0= STRING ) |
				// (enumLiteral_1= WSTRING ) | (enumLiteral_2= CHAR ) | (enumLiteral_3= WCHAR )
				// )
				int alt69 = 4;
				switch (input.LA(1)) {
				case STRING: {
					alt69 = 1;
				}
					break;
				case WSTRING: {
					alt69 = 2;
				}
					break;
				case CHAR: {
					alt69 = 3;
				}
					break;
				case WCHAR: {
					alt69 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 69, 0, input);

					throw nvae;
				}

				switch (alt69) {
				case 1:
				// InternalStructuredTextParser.g:4976:3: (enumLiteral_0= STRING )
				{
					// InternalStructuredTextParser.g:4976:3: (enumLiteral_0= STRING )
					// InternalStructuredTextParser.g:4977:4: enumLiteral_0= STRING
					{
						enumLiteral_0 = (Token) match(input, STRING, FOLLOW_2);

						current = grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:4984:3: (enumLiteral_1= WSTRING )
				{
					// InternalStructuredTextParser.g:4984:3: (enumLiteral_1= WSTRING )
					// InternalStructuredTextParser.g:4985:4: enumLiteral_1= WSTRING
					{
						enumLiteral_1 = (Token) match(input, WSTRING, FOLLOW_2);

						current = grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:4992:3: (enumLiteral_2= CHAR )
				{
					// InternalStructuredTextParser.g:4992:3: (enumLiteral_2= CHAR )
					// InternalStructuredTextParser.g:4993:4: enumLiteral_2= CHAR
					{
						enumLiteral_2 = (Token) match(input, CHAR, FOLLOW_2);

						current = grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_2,
								grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2());

					}

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:5000:3: (enumLiteral_3= WCHAR )
				{
					// InternalStructuredTextParser.g:5000:3: (enumLiteral_3= WCHAR )
					// InternalStructuredTextParser.g:5001:4: enumLiteral_3= WCHAR
					{
						enumLiteral_3 = (Token) match(input, WCHAR, FOLLOW_2);

						current = grammarAccess.getString_Type_NameAccess().getWCHAREnumLiteralDeclaration_3()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_3,
								grammarAccess.getString_Type_NameAccess().getWCHAREnumLiteralDeclaration_3());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleString_Type_Name"

	// $ANTLR start "ruleTime_Type_Name"
	// InternalStructuredTextParser.g:5011:1: ruleTime_Type_Name returns [Enumerator
	// current=null] : ( (enumLiteral_0= TIME ) | (enumLiteral_1= LTIME ) |
	// (enumLiteral_2= T ) | (enumLiteral_3= LT ) ) ;
	public final Enumerator ruleTime_Type_Name() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;
		Token enumLiteral_2 = null;
		Token enumLiteral_3 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:5017:2: ( ( (enumLiteral_0= TIME ) |
			// (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) ) )
			// InternalStructuredTextParser.g:5018:2: ( (enumLiteral_0= TIME ) |
			// (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) )
			{
				// InternalStructuredTextParser.g:5018:2: ( (enumLiteral_0= TIME ) |
				// (enumLiteral_1= LTIME ) | (enumLiteral_2= T ) | (enumLiteral_3= LT ) )
				int alt70 = 4;
				switch (input.LA(1)) {
				case TIME: {
					alt70 = 1;
				}
					break;
				case LTIME: {
					alt70 = 2;
				}
					break;
				case T: {
					alt70 = 3;
				}
					break;
				case LT: {
					alt70 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 70, 0, input);

					throw nvae;
				}

				switch (alt70) {
				case 1:
				// InternalStructuredTextParser.g:5019:3: (enumLiteral_0= TIME )
				{
					// InternalStructuredTextParser.g:5019:3: (enumLiteral_0= TIME )
					// InternalStructuredTextParser.g:5020:4: enumLiteral_0= TIME
					{
						enumLiteral_0 = (Token) match(input, TIME, FOLLOW_2);

						current = grammarAccess.getTime_Type_NameAccess().getTIMEEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getTime_Type_NameAccess().getTIMEEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:5027:3: (enumLiteral_1= LTIME )
				{
					// InternalStructuredTextParser.g:5027:3: (enumLiteral_1= LTIME )
					// InternalStructuredTextParser.g:5028:4: enumLiteral_1= LTIME
					{
						enumLiteral_1 = (Token) match(input, LTIME, FOLLOW_2);

						current = grammarAccess.getTime_Type_NameAccess().getLTIMEEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getTime_Type_NameAccess().getLTIMEEnumLiteralDeclaration_1());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:5035:3: (enumLiteral_2= T )
				{
					// InternalStructuredTextParser.g:5035:3: (enumLiteral_2= T )
					// InternalStructuredTextParser.g:5036:4: enumLiteral_2= T
					{
						enumLiteral_2 = (Token) match(input, T, FOLLOW_2);

						current = grammarAccess.getTime_Type_NameAccess().getTEnumLiteralDeclaration_2()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_2,
								grammarAccess.getTime_Type_NameAccess().getTEnumLiteralDeclaration_2());

					}

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:5043:3: (enumLiteral_3= LT )
				{
					// InternalStructuredTextParser.g:5043:3: (enumLiteral_3= LT )
					// InternalStructuredTextParser.g:5044:4: enumLiteral_3= LT
					{
						enumLiteral_3 = (Token) match(input, LT, FOLLOW_2);

						current = grammarAccess.getTime_Type_NameAccess().getLTEnumLiteralDeclaration_3()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_3,
								grammarAccess.getTime_Type_NameAccess().getLTEnumLiteralDeclaration_3());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleTime_Type_Name"

	// $ANTLR start "ruleTod_Type_Name"
	// InternalStructuredTextParser.g:5054:1: ruleTod_Type_Name returns [Enumerator
	// current=null] : ( (enumLiteral_0= TIME_OF_DAY ) | (enumLiteral_1=
	// LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD ) ) ;
	public final Enumerator ruleTod_Type_Name() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;
		Token enumLiteral_2 = null;
		Token enumLiteral_3 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:5060:2: ( ( (enumLiteral_0= TIME_OF_DAY ) |
			// (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD
			// ) ) )
			// InternalStructuredTextParser.g:5061:2: ( (enumLiteral_0= TIME_OF_DAY ) |
			// (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD
			// ) )
			{
				// InternalStructuredTextParser.g:5061:2: ( (enumLiteral_0= TIME_OF_DAY ) |
				// (enumLiteral_1= LTIME_OF_DAY ) | (enumLiteral_2= TOD ) | (enumLiteral_3= LTOD
				// ) )
				int alt71 = 4;
				switch (input.LA(1)) {
				case TIME_OF_DAY: {
					alt71 = 1;
				}
					break;
				case LTIME_OF_DAY: {
					alt71 = 2;
				}
					break;
				case TOD: {
					alt71 = 3;
				}
					break;
				case LTOD: {
					alt71 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 71, 0, input);

					throw nvae;
				}

				switch (alt71) {
				case 1:
				// InternalStructuredTextParser.g:5062:3: (enumLiteral_0= TIME_OF_DAY )
				{
					// InternalStructuredTextParser.g:5062:3: (enumLiteral_0= TIME_OF_DAY )
					// InternalStructuredTextParser.g:5063:4: enumLiteral_0= TIME_OF_DAY
					{
						enumLiteral_0 = (Token) match(input, TIME_OF_DAY, FOLLOW_2);

						current = grammarAccess.getTod_Type_NameAccess().getTIME_OF_DAYEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getTod_Type_NameAccess().getTIME_OF_DAYEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:5070:3: (enumLiteral_1= LTIME_OF_DAY )
				{
					// InternalStructuredTextParser.g:5070:3: (enumLiteral_1= LTIME_OF_DAY )
					// InternalStructuredTextParser.g:5071:4: enumLiteral_1= LTIME_OF_DAY
					{
						enumLiteral_1 = (Token) match(input, LTIME_OF_DAY, FOLLOW_2);

						current = grammarAccess.getTod_Type_NameAccess().getLTIME_OF_DAYEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getTod_Type_NameAccess().getLTIME_OF_DAYEnumLiteralDeclaration_1());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:5078:3: (enumLiteral_2= TOD )
				{
					// InternalStructuredTextParser.g:5078:3: (enumLiteral_2= TOD )
					// InternalStructuredTextParser.g:5079:4: enumLiteral_2= TOD
					{
						enumLiteral_2 = (Token) match(input, TOD, FOLLOW_2);

						current = grammarAccess.getTod_Type_NameAccess().getTODEnumLiteralDeclaration_2()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_2,
								grammarAccess.getTod_Type_NameAccess().getTODEnumLiteralDeclaration_2());

					}

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:5086:3: (enumLiteral_3= LTOD )
				{
					// InternalStructuredTextParser.g:5086:3: (enumLiteral_3= LTOD )
					// InternalStructuredTextParser.g:5087:4: enumLiteral_3= LTOD
					{
						enumLiteral_3 = (Token) match(input, LTOD, FOLLOW_2);

						current = grammarAccess.getTod_Type_NameAccess().getLTODEnumLiteralDeclaration_3()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_3,
								grammarAccess.getTod_Type_NameAccess().getLTODEnumLiteralDeclaration_3());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleTod_Type_Name"

	// $ANTLR start "ruleDate_Type_Name"
	// InternalStructuredTextParser.g:5097:1: ruleDate_Type_Name returns [Enumerator
	// current=null] : ( (enumLiteral_0= DATE ) | (enumLiteral_1= LDATE ) |
	// (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) ) ;
	public final Enumerator ruleDate_Type_Name() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;
		Token enumLiteral_2 = null;
		Token enumLiteral_3 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:5103:2: ( ( (enumLiteral_0= DATE ) |
			// (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) ) )
			// InternalStructuredTextParser.g:5104:2: ( (enumLiteral_0= DATE ) |
			// (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) )
			{
				// InternalStructuredTextParser.g:5104:2: ( (enumLiteral_0= DATE ) |
				// (enumLiteral_1= LDATE ) | (enumLiteral_2= D_1 ) | (enumLiteral_3= LD ) )
				int alt72 = 4;
				switch (input.LA(1)) {
				case DATE: {
					alt72 = 1;
				}
					break;
				case LDATE: {
					alt72 = 2;
				}
					break;
				case D_1: {
					alt72 = 3;
				}
					break;
				case LD: {
					alt72 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 72, 0, input);

					throw nvae;
				}

				switch (alt72) {
				case 1:
				// InternalStructuredTextParser.g:5105:3: (enumLiteral_0= DATE )
				{
					// InternalStructuredTextParser.g:5105:3: (enumLiteral_0= DATE )
					// InternalStructuredTextParser.g:5106:4: enumLiteral_0= DATE
					{
						enumLiteral_0 = (Token) match(input, DATE, FOLLOW_2);

						current = grammarAccess.getDate_Type_NameAccess().getDATEEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getDate_Type_NameAccess().getDATEEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:5113:3: (enumLiteral_1= LDATE )
				{
					// InternalStructuredTextParser.g:5113:3: (enumLiteral_1= LDATE )
					// InternalStructuredTextParser.g:5114:4: enumLiteral_1= LDATE
					{
						enumLiteral_1 = (Token) match(input, LDATE, FOLLOW_2);

						current = grammarAccess.getDate_Type_NameAccess().getLDATEEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getDate_Type_NameAccess().getLDATEEnumLiteralDeclaration_1());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:5121:3: (enumLiteral_2= D_1 )
				{
					// InternalStructuredTextParser.g:5121:3: (enumLiteral_2= D_1 )
					// InternalStructuredTextParser.g:5122:4: enumLiteral_2= D_1
					{
						enumLiteral_2 = (Token) match(input, D_1, FOLLOW_2);

						current = grammarAccess.getDate_Type_NameAccess().getDEnumLiteralDeclaration_2()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_2,
								grammarAccess.getDate_Type_NameAccess().getDEnumLiteralDeclaration_2());

					}

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:5129:3: (enumLiteral_3= LD )
				{
					// InternalStructuredTextParser.g:5129:3: (enumLiteral_3= LD )
					// InternalStructuredTextParser.g:5130:4: enumLiteral_3= LD
					{
						enumLiteral_3 = (Token) match(input, LD, FOLLOW_2);

						current = grammarAccess.getDate_Type_NameAccess().getLDEnumLiteralDeclaration_3()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_3,
								grammarAccess.getDate_Type_NameAccess().getLDEnumLiteralDeclaration_3());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDate_Type_Name"

	// $ANTLR start "ruleDT_Type_Name"
	// InternalStructuredTextParser.g:5140:1: ruleDT_Type_Name returns [Enumerator
	// current=null] : ( (enumLiteral_0= DATE_AND_TIME ) | (enumLiteral_1=
	// LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT ) ) ;
	public final Enumerator ruleDT_Type_Name() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;
		Token enumLiteral_1 = null;
		Token enumLiteral_2 = null;
		Token enumLiteral_3 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:5146:2: ( ( (enumLiteral_0= DATE_AND_TIME ) |
			// (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT
			// ) ) )
			// InternalStructuredTextParser.g:5147:2: ( (enumLiteral_0= DATE_AND_TIME ) |
			// (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT
			// ) )
			{
				// InternalStructuredTextParser.g:5147:2: ( (enumLiteral_0= DATE_AND_TIME ) |
				// (enumLiteral_1= LDATE_AND_TIME ) | (enumLiteral_2= DT ) | (enumLiteral_3= LDT
				// ) )
				int alt73 = 4;
				switch (input.LA(1)) {
				case DATE_AND_TIME: {
					alt73 = 1;
				}
					break;
				case LDATE_AND_TIME: {
					alt73 = 2;
				}
					break;
				case DT: {
					alt73 = 3;
				}
					break;
				case LDT: {
					alt73 = 4;
				}
					break;
				default:
					NoViableAltException nvae = new NoViableAltException("", 73, 0, input);

					throw nvae;
				}

				switch (alt73) {
				case 1:
				// InternalStructuredTextParser.g:5148:3: (enumLiteral_0= DATE_AND_TIME )
				{
					// InternalStructuredTextParser.g:5148:3: (enumLiteral_0= DATE_AND_TIME )
					// InternalStructuredTextParser.g:5149:4: enumLiteral_0= DATE_AND_TIME
					{
						enumLiteral_0 = (Token) match(input, DATE_AND_TIME, FOLLOW_2);

						current = grammarAccess.getDT_Type_NameAccess().getDATE_AND_TIMEEnumLiteralDeclaration_0()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_0,
								grammarAccess.getDT_Type_NameAccess().getDATE_AND_TIMEEnumLiteralDeclaration_0());

					}

				}
					break;
				case 2:
				// InternalStructuredTextParser.g:5156:3: (enumLiteral_1= LDATE_AND_TIME )
				{
					// InternalStructuredTextParser.g:5156:3: (enumLiteral_1= LDATE_AND_TIME )
					// InternalStructuredTextParser.g:5157:4: enumLiteral_1= LDATE_AND_TIME
					{
						enumLiteral_1 = (Token) match(input, LDATE_AND_TIME, FOLLOW_2);

						current = grammarAccess.getDT_Type_NameAccess().getLDATE_AND_TIMEEnumLiteralDeclaration_1()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_1,
								grammarAccess.getDT_Type_NameAccess().getLDATE_AND_TIMEEnumLiteralDeclaration_1());

					}

				}
					break;
				case 3:
				// InternalStructuredTextParser.g:5164:3: (enumLiteral_2= DT )
				{
					// InternalStructuredTextParser.g:5164:3: (enumLiteral_2= DT )
					// InternalStructuredTextParser.g:5165:4: enumLiteral_2= DT
					{
						enumLiteral_2 = (Token) match(input, DT, FOLLOW_2);

						current = grammarAccess.getDT_Type_NameAccess().getDTEnumLiteralDeclaration_2().getEnumLiteral()
								.getInstance();
						newLeafNode(enumLiteral_2,
								grammarAccess.getDT_Type_NameAccess().getDTEnumLiteralDeclaration_2());

					}

				}
					break;
				case 4:
				// InternalStructuredTextParser.g:5172:3: (enumLiteral_3= LDT )
				{
					// InternalStructuredTextParser.g:5172:3: (enumLiteral_3= LDT )
					// InternalStructuredTextParser.g:5173:4: enumLiteral_3= LDT
					{
						enumLiteral_3 = (Token) match(input, LDT, FOLLOW_2);

						current = grammarAccess.getDT_Type_NameAccess().getLDTEnumLiteralDeclaration_3()
								.getEnumLiteral().getInstance();
						newLeafNode(enumLiteral_3,
								grammarAccess.getDT_Type_NameAccess().getLDTEnumLiteralDeclaration_3());

					}

				}
					break;

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleDT_Type_Name"

	// $ANTLR start "ruleBool_Type_Name"
	// InternalStructuredTextParser.g:5183:1: ruleBool_Type_Name returns [Enumerator
	// current=null] : (enumLiteral_0= BOOL ) ;
	public final Enumerator ruleBool_Type_Name() throws RecognitionException {
		Enumerator current = null;

		Token enumLiteral_0 = null;

		enterRule();

		try {
			// InternalStructuredTextParser.g:5189:2: ( (enumLiteral_0= BOOL ) )
			// InternalStructuredTextParser.g:5190:2: (enumLiteral_0= BOOL )
			{
				// InternalStructuredTextParser.g:5190:2: (enumLiteral_0= BOOL )
				// InternalStructuredTextParser.g:5191:3: enumLiteral_0= BOOL
				{
					enumLiteral_0 = (Token) match(input, BOOL, FOLLOW_2);

					current = grammarAccess.getBool_Type_NameAccess().getBOOLEnumLiteralDeclaration().getEnumLiteral()
							.getInstance();
					newLeafNode(enumLiteral_0, grammarAccess.getBool_Type_NameAccess().getBOOLEnumLiteralDeclaration());

				}

			}

			leaveRule();

		}

		catch (RecognitionException re) {
			recover(input, re);
			appendSkippedTokens();
		} finally {
		}
		return current;
	}
	// $ANTLR end "ruleBool_Type_Name"

	// Delegated rules

	protected DFA28 dfa28 = new DFA28(this);
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

	class DFA28 extends DFA {

		public DFA28(BaseRecognizer recognizer) {
			this.recognizer = recognizer;
			this.decisionNumber = 28;
			this.eot = dfa_1;
			this.eof = dfa_2;
			this.min = dfa_3;
			this.max = dfa_4;
			this.accept = dfa_5;
			this.special = dfa_6;
			this.transition = dfa_7;
		}

		public String getDescription() {
			return "2079:2: ( ( () ( (lv_operator_1_0= ruleUnary_Operator ) ) ( (lv_expression_2_0= rulePrimary_Expr ) ) ) | this_Primary_Expr_3= rulePrimary_Expr | this_Constant_4= ruleConstant )";
		}
	}

	public static final BitSet FOLLOW_1 = new BitSet(new long[] { 0x0000000000000000L });
	public static final BitSet FOLLOW_2 = new BitSet(new long[] { 0x0000000000000002L });
	public static final BitSet FOLLOW_3 = new BitSet(new long[] { 0x0000000000004400L, 0x0001000000000000L });
	public static final BitSet FOLLOW_4 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000040000000L });
	public static final BitSet FOLLOW_5 = new BitSet(new long[] { 0x0100821208060800L, 0x0001000840001600L });
	public static final BitSet FOLLOW_6 = new BitSet(new long[] { 0x0000000000000000L, 0x0001000000000000L });
	public static final BitSet FOLLOW_7 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000020000000L });
	public static final BitSet FOLLOW_8 = new BitSet(new long[] { 0x2206BCEDB79880F0L, 0x0001000000000000L });
	public static final BitSet FOLLOW_9 = new BitSet(new long[] { 0x0000000000000002L, 0x0000001000000002L });
	public static final BitSet FOLLOW_10 = new BitSet(new long[] { 0x0000000000000000L, 0x0010000000000000L });
	public static final BitSet FOLLOW_11 = new BitSet(new long[] { 0x0000000000000000L, 0x0000002000000000L });
	public static final BitSet FOLLOW_12 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000000000002L });
	public static final BitSet FOLLOW_13 = new BitSet(new long[] { 0x2603BCE5B3C880F0L, 0x015E008805001A00L });
	public static final BitSet FOLLOW_14 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000040L });
	public static final BitSet FOLLOW_15 = new BitSet(new long[] { 0x0000000000000000L, 0x0001000800001200L });
	public static final BitSet FOLLOW_16 = new BitSet(new long[] { 0x0000000000000002L, 0x0000001000000000L });
	public static final BitSet FOLLOW_17 = new BitSet(new long[] { 0x0100821208060802L, 0x0001000840001600L });
	public static final BitSet FOLLOW_18 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000002L });
	public static final BitSet FOLLOW_19 = new BitSet(new long[] { 0x3603BCE5B3C880F0L, 0x015F008805201A00L });
	public static final BitSet FOLLOW_20 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000200000L });
	public static final BitSet FOLLOW_21 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000400000L });
	public static final BitSet FOLLOW_22 = new BitSet(new long[] { 0x0000400000000000L });
	public static final BitSet FOLLOW_23 = new BitSet(new long[] { 0x0100831208270800L, 0x0001000840001600L });
	public static final BitSet FOLLOW_24 = new BitSet(new long[] { 0x0000010000210000L });
	public static final BitSet FOLLOW_25 = new BitSet(new long[] { 0x0000000000010000L });
	public static final BitSet FOLLOW_26 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000002000L });
	public static final BitSet FOLLOW_27 = new BitSet(new long[] { 0x2603BDE5B3C890F0L, 0x015E008805001A00L });
	public static final BitSet FOLLOW_28 = new BitSet(new long[] { 0x0000000000001000L });
	public static final BitSet FOLLOW_29 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000022000000L });
	public static final BitSet FOLLOW_30 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000008000L });
	public static final BitSet FOLLOW_31 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000180L });
	public static final BitSet FOLLOW_32 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000100L });
	public static final BitSet FOLLOW_33 = new BitSet(new long[] { 0x0100821208062800L, 0x0001000840001600L });
	public static final BitSet FOLLOW_34 = new BitSet(new long[] { 0x0000000000002000L });
	public static final BitSet FOLLOW_35 = new BitSet(new long[] { 0x0100821208060A00L, 0x0001000840001600L });
	public static final BitSet FOLLOW_36 = new BitSet(new long[] { 0x0000000000000200L });
	public static final BitSet FOLLOW_37 = new BitSet(new long[] { 0x0100821248060800L, 0x0001000840001600L });
	public static final BitSet FOLLOW_38 = new BitSet(new long[] { 0x0000000040000000L });
	public static final BitSet FOLLOW_39 = new BitSet(new long[] { 0x0000000000000100L });
	public static final BitSet FOLLOW_40 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000000004000L });
	public static final BitSet FOLLOW_41 = new BitSet(new long[] { 0x8000000000000002L });
	public static final BitSet FOLLOW_42 = new BitSet(new long[] { 0x0080000000000002L, 0x0000000000100000L });
	public static final BitSet FOLLOW_43 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000100000008L });
	public static final BitSet FOLLOW_44 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000280000024L });
	public static final BitSet FOLLOW_45 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000005000000L });
	public static final BitSet FOLLOW_46 = new BitSet(new long[] { 0x0800000000000002L, 0x0000000010800000L });
	public static final BitSet FOLLOW_47 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000000000001L });
	public static final BitSet FOLLOW_48 = new BitSet(new long[] { 0x0000800000000000L, 0x0001000800201200L });
	public static final BitSet FOLLOW_49 = new BitSet(new long[] { 0x3603BCE5B3C880F0L, 0x015F008805601A00L });
	public static final BitSet FOLLOW_50 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000002400000L });
	public static final BitSet FOLLOW_51 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000000010L });
	public static final BitSet FOLLOW_52 = new BitSet(new long[] { 0x0078000000000002L, 0x0000000008000000L });
	public static final BitSet FOLLOW_53 = new BitSet(new long[] { 0x0000000000000000L, 0x0000002002000000L });
	public static final BitSet FOLLOW_54 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000008000000L });
	public static final BitSet FOLLOW_55 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000000080000L });
	public static final BitSet FOLLOW_56 = new BitSet(new long[] { 0x0000000000000000L, 0x001E000005000000L });
	public static final BitSet FOLLOW_57 = new BitSet(new long[] { 0x02023480B1000000L, 0x001E000005000000L });
	public static final BitSet FOLLOW_58 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000400000000L });
	public static final BitSet FOLLOW_59 = new BitSet(new long[] { 0x0000000000000000L, 0x0010000005000000L });
	public static final BitSet FOLLOW_60 = new BitSet(new long[] { 0x0000000000000000L, 0x0010000000080000L });
	public static final BitSet FOLLOW_61 = new BitSet(new long[] { 0x0000000000000000L, 0x0140000000000000L });
	public static final BitSet FOLLOW_62 = new BitSet(new long[] { 0x0000000000000002L, 0x0000004000000000L });
	public static final BitSet FOLLOW_63 = new BitSet(new long[] { 0x0000000000000000L, 0x0000078000070000L });
	public static final BitSet FOLLOW_64 = new BitSet(new long[] { 0x0000000000000002L, 0x0000000008000000L });
	public static final BitSet FOLLOW_65 = new BitSet(new long[] { 0x0000000000000000L, 0x0000000004000000L });

}