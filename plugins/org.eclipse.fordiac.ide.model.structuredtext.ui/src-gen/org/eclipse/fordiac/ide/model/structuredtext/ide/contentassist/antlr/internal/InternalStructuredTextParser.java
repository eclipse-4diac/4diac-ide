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
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "LDATE_AND_TIME", "DATE_AND_TIME", "LTIME_OF_DAY", "TIME_OF_DAY", "END_REPEAT", "END_WHILE", "CONSTANT", "CONTINUE", "END_CASE", "END_FOR", "END_VAR", "WSTRING", "END_IF", "REPEAT", "RETURN", "STRING", "ARRAY", "DWORD", "ELSIF", "FALSE", "LDATE", "LREAL", "LTIME", "LWORD", "SUPER", "UDINT", "ULINT", "UNTIL", "USINT", "WCHAR", "WHILE", "BOOL", "BYTE", "CASE", "CHAR", "DATE", "DINT", "ELSE", "EXIT", "LINT", "LTOD", "REAL", "SINT", "THEN", "TIME", "TRUE", "UINT", "WORD", "B", "D", "W", "X", "AND", "FOR", "INT", "MOD", "NOT", "TOD", "VAR", "XOR", "AsteriskAsterisk", "FullStopFullStop", "ColonEqualsSign", "LessThanSignEqualsSign", "LessThanSignGreaterThanSign", "EqualsSignGreaterThanSign", "GreaterThanSignEqualsSign", "AT", "BY", "DO", "DT", "IF", "LT", "OF", "OR", "TO", "NumberSign", "Ampersand", "LeftParenthesis", "RightParenthesis", "Asterisk", "PlusSign", "Comma", "HyphenMinus", "FullStop", "Solidus", "Colon", "Semicolon", "LessThanSign", "EqualsSign", "GreaterThanSign", "E", "T", "LeftSquareBracket", "RightSquareBracket", "RULE_LETTER", "RULE_DIGIT", "RULE_BIT", "RULE_OCTAL_DIGIT", "RULE_HEX_DIGIT", "RULE_UNSIGNED_INT", "RULE_TIMEOFDAY", "RULE_TIME", "RULE_DATETIME", "RULE_DATE", "RULE_ID", "RULE_BINARY_INT", "RULE_OCTAL_INT", "RULE_HEX_INT", "RULE_S_BYTE_CHAR_VALUE", "RULE_S_BYTE_CHAR_STR", "RULE_D_BYTE_CHAR_VALUE", "RULE_D_BYTE_CHAR_STR", "RULE_COMMON_CHAR_VALUE", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER"
    };
    public static final int LWORD=27;
    public static final int LessThanSignGreaterThanSign=68;
    public static final int EqualsSignGreaterThanSign=69;
    public static final int VAR=62;
    public static final int END_IF=16;
    public static final int ULINT=30;
    public static final int END_CASE=12;
    public static final int LessThanSign=92;
    public static final int RULE_BIT=101;
    public static final int LeftParenthesis=82;
    public static final int BYTE=36;
    public static final int ELSE=41;
    public static final int RULE_TIME=106;
    public static final int IF=75;
    public static final int LINT=43;
    public static final int GreaterThanSign=94;
    public static final int WORD=51;
    public static final int DATE_AND_TIME=5;
    public static final int RULE_ID=109;
    public static final int TOD=61;
    public static final int RULE_DIGIT=100;
    public static final int DINT=40;
    public static final int UDINT=29;
    public static final int CASE=37;
    public static final int GreaterThanSignEqualsSign=70;
    public static final int AT=71;
    public static final int RULE_DATE=108;
    public static final int RULE_OCTAL_INT=111;
    public static final int PlusSign=85;
    public static final int END_FOR=13;
    public static final int RULE_ML_COMMENT=118;
    public static final int THEN=47;
    public static final int XOR=63;
    public static final int LeftSquareBracket=97;
    public static final int EXIT=42;
    public static final int TIME_OF_DAY=7;
    public static final int B=52;
    public static final int LDATE_AND_TIME=4;
    public static final int REPEAT=17;
    public static final int D=53;
    public static final int E=95;
    public static final int RULE_TIMEOFDAY=105;
    public static final int CHAR=38;
    public static final int RULE_D_BYTE_CHAR_STR=116;
    public static final int RULE_UNSIGNED_INT=104;
    public static final int LTIME=26;
    public static final int Comma=86;
    public static final int HyphenMinus=87;
    public static final int T=96;
    public static final int W=54;
    public static final int BY=72;
    public static final int X=55;
    public static final int ELSIF=22;
    public static final int END_REPEAT=8;
    public static final int LessThanSignEqualsSign=67;
    public static final int Solidus=89;
    public static final int RULE_OCTAL_DIGIT=102;
    public static final int RULE_HEX_INT=112;
    public static final int FullStop=88;
    public static final int CONSTANT=10;
    public static final int CONTINUE=11;
    public static final int Semicolon=91;
    public static final int RULE_LETTER=99;
    public static final int STRING=19;
    public static final int RULE_HEX_DIGIT=103;
    public static final int TO=79;
    public static final int UINT=50;
    public static final int LTOD=44;
    public static final int ARRAY=20;
    public static final int LT=76;
    public static final int DO=73;
    public static final int WSTRING=15;
    public static final int DT=74;
    public static final int END_VAR=14;
    public static final int FullStopFullStop=65;
    public static final int Ampersand=81;
    public static final int RightSquareBracket=98;
    public static final int RULE_BINARY_INT=110;
    public static final int USINT=32;
    public static final int DWORD=21;
    public static final int FOR=57;
    public static final int RightParenthesis=83;
    public static final int TRUE=49;
    public static final int ColonEqualsSign=66;
    public static final int END_WHILE=9;
    public static final int DATE=39;
    public static final int NOT=60;
    public static final int LDATE=24;
    public static final int AND=56;
    public static final int NumberSign=80;
    public static final int REAL=45;
    public static final int AsteriskAsterisk=64;
    public static final int SINT=46;
    public static final int LTIME_OF_DAY=6;
    public static final int LREAL=25;
    public static final int WCHAR=33;
    public static final int RULE_DATETIME=107;
    public static final int INT=58;
    public static final int RULE_SL_COMMENT=119;
    public static final int RETURN=18;
    public static final int EqualsSign=93;
    public static final int RULE_COMMON_CHAR_VALUE=117;
    public static final int OF=77;
    public static final int Colon=90;
    public static final int EOF=-1;
    public static final int Asterisk=84;
    public static final int SUPER=28;
    public static final int MOD=59;
    public static final int OR=78;
    public static final int RULE_S_BYTE_CHAR_VALUE=113;
    public static final int RULE_WS=120;
    public static final int TIME=48;
    public static final int RULE_ANY_OTHER=121;
    public static final int UNTIL=31;
    public static final int RULE_S_BYTE_CHAR_STR=114;
    public static final int BOOL=35;
    public static final int FALSE=23;
    public static final int WHILE=34;
    public static final int RULE_D_BYTE_CHAR_VALUE=115;

    // delegates
    // delegators


        public InternalStructuredTextParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalStructuredTextParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalStructuredTextParser.tokenNames; }
    public String getGrammarFileName() { return "InternalStructuredTextParser.g"; }


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
    		tokenNameToValue.put("E", "'E'");
    		tokenNameToValue.put("T", "'T'");
    		tokenNameToValue.put("LeftSquareBracket", "'['");
    		tokenNameToValue.put("RightSquareBracket", "']'");
    		tokenNameToValue.put("AsteriskAsterisk", "'**'");
    		tokenNameToValue.put("FullStopFullStop", "'..'");
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
    		tokenNameToValue.put("LT", "'LT'");
    		tokenNameToValue.put("OF", "'OF'");
    		tokenNameToValue.put("OR", "'OR'");
    		tokenNameToValue.put("TO", "'TO'");
    		tokenNameToValue.put("B", "'.%B'");
    		tokenNameToValue.put("D", "'.%D'");
    		tokenNameToValue.put("W", "'.%W'");
    		tokenNameToValue.put("X", "'.%X'");
    		tokenNameToValue.put("AND", "'AND'");
    		tokenNameToValue.put("FOR", "'FOR'");
    		tokenNameToValue.put("INT", "'INT'");
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
    		tokenNameToValue.put("ARRAY", "'ARRAY'");
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
    // InternalStructuredTextParser.g:150:1: entryRuleStructuredTextAlgorithm : ruleStructuredTextAlgorithm EOF ;
    public final void entryRuleStructuredTextAlgorithm() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:151:1: ( ruleStructuredTextAlgorithm EOF )
            // InternalStructuredTextParser.g:152:1: ruleStructuredTextAlgorithm EOF
            {
             before(grammarAccess.getStructuredTextAlgorithmRule()); 
            pushFollow(FOLLOW_1);
            ruleStructuredTextAlgorithm();

            state._fsp--;

             after(grammarAccess.getStructuredTextAlgorithmRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStructuredTextAlgorithm"


    // $ANTLR start "ruleStructuredTextAlgorithm"
    // InternalStructuredTextParser.g:159:1: ruleStructuredTextAlgorithm : ( ( rule__StructuredTextAlgorithm__Group__0 ) ) ;
    public final void ruleStructuredTextAlgorithm() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:163:2: ( ( ( rule__StructuredTextAlgorithm__Group__0 ) ) )
            // InternalStructuredTextParser.g:164:2: ( ( rule__StructuredTextAlgorithm__Group__0 ) )
            {
            // InternalStructuredTextParser.g:164:2: ( ( rule__StructuredTextAlgorithm__Group__0 ) )
            // InternalStructuredTextParser.g:165:3: ( rule__StructuredTextAlgorithm__Group__0 )
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getGroup()); 
            // InternalStructuredTextParser.g:166:3: ( rule__StructuredTextAlgorithm__Group__0 )
            // InternalStructuredTextParser.g:166:4: rule__StructuredTextAlgorithm__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getStructuredTextAlgorithmAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStructuredTextAlgorithm"


    // $ANTLR start "entryRuleVar_Decl_Init"
    // InternalStructuredTextParser.g:175:1: entryRuleVar_Decl_Init : ruleVar_Decl_Init EOF ;
    public final void entryRuleVar_Decl_Init() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:176:1: ( ruleVar_Decl_Init EOF )
            // InternalStructuredTextParser.g:177:1: ruleVar_Decl_Init EOF
            {
             before(grammarAccess.getVar_Decl_InitRule()); 
            pushFollow(FOLLOW_1);
            ruleVar_Decl_Init();

            state._fsp--;

             after(grammarAccess.getVar_Decl_InitRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVar_Decl_Init"


    // $ANTLR start "ruleVar_Decl_Init"
    // InternalStructuredTextParser.g:184:1: ruleVar_Decl_Init : ( ruleVar_Decl_Local ) ;
    public final void ruleVar_Decl_Init() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:188:2: ( ( ruleVar_Decl_Local ) )
            // InternalStructuredTextParser.g:189:2: ( ruleVar_Decl_Local )
            {
            // InternalStructuredTextParser.g:189:2: ( ruleVar_Decl_Local )
            // InternalStructuredTextParser.g:190:3: ruleVar_Decl_Local
            {
             before(grammarAccess.getVar_Decl_InitAccess().getVar_Decl_LocalParserRuleCall()); 
            pushFollow(FOLLOW_2);
            ruleVar_Decl_Local();

            state._fsp--;

             after(grammarAccess.getVar_Decl_InitAccess().getVar_Decl_LocalParserRuleCall()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVar_Decl_Init"


    // $ANTLR start "entryRuleVar_Decl_Local"
    // InternalStructuredTextParser.g:200:1: entryRuleVar_Decl_Local : ruleVar_Decl_Local EOF ;
    public final void entryRuleVar_Decl_Local() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:201:1: ( ruleVar_Decl_Local EOF )
            // InternalStructuredTextParser.g:202:1: ruleVar_Decl_Local EOF
            {
             before(grammarAccess.getVar_Decl_LocalRule()); 
            pushFollow(FOLLOW_1);
            ruleVar_Decl_Local();

            state._fsp--;

             after(grammarAccess.getVar_Decl_LocalRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVar_Decl_Local"


    // $ANTLR start "ruleVar_Decl_Local"
    // InternalStructuredTextParser.g:209:1: ruleVar_Decl_Local : ( ( rule__Var_Decl_Local__Group__0 ) ) ;
    public final void ruleVar_Decl_Local() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:213:2: ( ( ( rule__Var_Decl_Local__Group__0 ) ) )
            // InternalStructuredTextParser.g:214:2: ( ( rule__Var_Decl_Local__Group__0 ) )
            {
            // InternalStructuredTextParser.g:214:2: ( ( rule__Var_Decl_Local__Group__0 ) )
            // InternalStructuredTextParser.g:215:3: ( rule__Var_Decl_Local__Group__0 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getGroup()); 
            // InternalStructuredTextParser.g:216:3: ( rule__Var_Decl_Local__Group__0 )
            // InternalStructuredTextParser.g:216:4: rule__Var_Decl_Local__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getVar_Decl_LocalAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVar_Decl_Local"


    // $ANTLR start "entryRuleStmt_List"
    // InternalStructuredTextParser.g:225:1: entryRuleStmt_List : ruleStmt_List EOF ;
    public final void entryRuleStmt_List() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:226:1: ( ruleStmt_List EOF )
            // InternalStructuredTextParser.g:227:1: ruleStmt_List EOF
            {
             before(grammarAccess.getStmt_ListRule()); 
            pushFollow(FOLLOW_1);
            ruleStmt_List();

            state._fsp--;

             after(grammarAccess.getStmt_ListRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStmt_List"


    // $ANTLR start "ruleStmt_List"
    // InternalStructuredTextParser.g:234:1: ruleStmt_List : ( ( rule__Stmt_List__Group__0 ) ) ;
    public final void ruleStmt_List() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:238:2: ( ( ( rule__Stmt_List__Group__0 ) ) )
            // InternalStructuredTextParser.g:239:2: ( ( rule__Stmt_List__Group__0 ) )
            {
            // InternalStructuredTextParser.g:239:2: ( ( rule__Stmt_List__Group__0 ) )
            // InternalStructuredTextParser.g:240:3: ( rule__Stmt_List__Group__0 )
            {
             before(grammarAccess.getStmt_ListAccess().getGroup()); 
            // InternalStructuredTextParser.g:241:3: ( rule__Stmt_List__Group__0 )
            // InternalStructuredTextParser.g:241:4: rule__Stmt_List__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Stmt_List__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getStmt_ListAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStmt_List"


    // $ANTLR start "entryRuleStmt"
    // InternalStructuredTextParser.g:250:1: entryRuleStmt : ruleStmt EOF ;
    public final void entryRuleStmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:251:1: ( ruleStmt EOF )
            // InternalStructuredTextParser.g:252:1: ruleStmt EOF
            {
             before(grammarAccess.getStmtRule()); 
            pushFollow(FOLLOW_1);
            ruleStmt();

            state._fsp--;

             after(grammarAccess.getStmtRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleStmt"


    // $ANTLR start "ruleStmt"
    // InternalStructuredTextParser.g:259:1: ruleStmt : ( ( rule__Stmt__Alternatives ) ) ;
    public final void ruleStmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:263:2: ( ( ( rule__Stmt__Alternatives ) ) )
            // InternalStructuredTextParser.g:264:2: ( ( rule__Stmt__Alternatives ) )
            {
            // InternalStructuredTextParser.g:264:2: ( ( rule__Stmt__Alternatives ) )
            // InternalStructuredTextParser.g:265:3: ( rule__Stmt__Alternatives )
            {
             before(grammarAccess.getStmtAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:266:3: ( rule__Stmt__Alternatives )
            // InternalStructuredTextParser.g:266:4: rule__Stmt__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Stmt__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getStmtAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleStmt"


    // $ANTLR start "entryRuleAssign_Stmt"
    // InternalStructuredTextParser.g:275:1: entryRuleAssign_Stmt : ruleAssign_Stmt EOF ;
    public final void entryRuleAssign_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:276:1: ( ruleAssign_Stmt EOF )
            // InternalStructuredTextParser.g:277:1: ruleAssign_Stmt EOF
            {
             before(grammarAccess.getAssign_StmtRule()); 
            pushFollow(FOLLOW_1);
            ruleAssign_Stmt();

            state._fsp--;

             after(grammarAccess.getAssign_StmtRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAssign_Stmt"


    // $ANTLR start "ruleAssign_Stmt"
    // InternalStructuredTextParser.g:284:1: ruleAssign_Stmt : ( ( rule__Assign_Stmt__Group__0 ) ) ;
    public final void ruleAssign_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:288:2: ( ( ( rule__Assign_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:289:2: ( ( rule__Assign_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:289:2: ( ( rule__Assign_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:290:3: ( rule__Assign_Stmt__Group__0 )
            {
             before(grammarAccess.getAssign_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:291:3: ( rule__Assign_Stmt__Group__0 )
            // InternalStructuredTextParser.g:291:4: rule__Assign_Stmt__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Assign_Stmt__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAssign_StmtAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAssign_Stmt"


    // $ANTLR start "entryRuleSubprog_Ctrl_Stmt"
    // InternalStructuredTextParser.g:300:1: entryRuleSubprog_Ctrl_Stmt : ruleSubprog_Ctrl_Stmt EOF ;
    public final void entryRuleSubprog_Ctrl_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:301:1: ( ruleSubprog_Ctrl_Stmt EOF )
            // InternalStructuredTextParser.g:302:1: ruleSubprog_Ctrl_Stmt EOF
            {
             before(grammarAccess.getSubprog_Ctrl_StmtRule()); 
            pushFollow(FOLLOW_1);
            ruleSubprog_Ctrl_Stmt();

            state._fsp--;

             after(grammarAccess.getSubprog_Ctrl_StmtRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSubprog_Ctrl_Stmt"


    // $ANTLR start "ruleSubprog_Ctrl_Stmt"
    // InternalStructuredTextParser.g:309:1: ruleSubprog_Ctrl_Stmt : ( ( rule__Subprog_Ctrl_Stmt__Alternatives ) ) ;
    public final void ruleSubprog_Ctrl_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:313:2: ( ( ( rule__Subprog_Ctrl_Stmt__Alternatives ) ) )
            // InternalStructuredTextParser.g:314:2: ( ( rule__Subprog_Ctrl_Stmt__Alternatives ) )
            {
            // InternalStructuredTextParser.g:314:2: ( ( rule__Subprog_Ctrl_Stmt__Alternatives ) )
            // InternalStructuredTextParser.g:315:3: ( rule__Subprog_Ctrl_Stmt__Alternatives )
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:316:3: ( rule__Subprog_Ctrl_Stmt__Alternatives )
            // InternalStructuredTextParser.g:316:4: rule__Subprog_Ctrl_Stmt__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSubprog_Ctrl_Stmt"


    // $ANTLR start "entryRuleSelection_Stmt"
    // InternalStructuredTextParser.g:325:1: entryRuleSelection_Stmt : ruleSelection_Stmt EOF ;
    public final void entryRuleSelection_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:326:1: ( ruleSelection_Stmt EOF )
            // InternalStructuredTextParser.g:327:1: ruleSelection_Stmt EOF
            {
             before(grammarAccess.getSelection_StmtRule()); 
            pushFollow(FOLLOW_1);
            ruleSelection_Stmt();

            state._fsp--;

             after(grammarAccess.getSelection_StmtRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSelection_Stmt"


    // $ANTLR start "ruleSelection_Stmt"
    // InternalStructuredTextParser.g:334:1: ruleSelection_Stmt : ( ( rule__Selection_Stmt__Alternatives ) ) ;
    public final void ruleSelection_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:338:2: ( ( ( rule__Selection_Stmt__Alternatives ) ) )
            // InternalStructuredTextParser.g:339:2: ( ( rule__Selection_Stmt__Alternatives ) )
            {
            // InternalStructuredTextParser.g:339:2: ( ( rule__Selection_Stmt__Alternatives ) )
            // InternalStructuredTextParser.g:340:3: ( rule__Selection_Stmt__Alternatives )
            {
             before(grammarAccess.getSelection_StmtAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:341:3: ( rule__Selection_Stmt__Alternatives )
            // InternalStructuredTextParser.g:341:4: rule__Selection_Stmt__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Selection_Stmt__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getSelection_StmtAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSelection_Stmt"


    // $ANTLR start "entryRuleIF_Stmt"
    // InternalStructuredTextParser.g:350:1: entryRuleIF_Stmt : ruleIF_Stmt EOF ;
    public final void entryRuleIF_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:351:1: ( ruleIF_Stmt EOF )
            // InternalStructuredTextParser.g:352:1: ruleIF_Stmt EOF
            {
             before(grammarAccess.getIF_StmtRule()); 
            pushFollow(FOLLOW_1);
            ruleIF_Stmt();

            state._fsp--;

             after(grammarAccess.getIF_StmtRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleIF_Stmt"


    // $ANTLR start "ruleIF_Stmt"
    // InternalStructuredTextParser.g:359:1: ruleIF_Stmt : ( ( rule__IF_Stmt__Group__0 ) ) ;
    public final void ruleIF_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:363:2: ( ( ( rule__IF_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:364:2: ( ( rule__IF_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:364:2: ( ( rule__IF_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:365:3: ( rule__IF_Stmt__Group__0 )
            {
             before(grammarAccess.getIF_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:366:3: ( rule__IF_Stmt__Group__0 )
            // InternalStructuredTextParser.g:366:4: rule__IF_Stmt__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__IF_Stmt__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getIF_StmtAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleIF_Stmt"


    // $ANTLR start "entryRuleELSIF_Clause"
    // InternalStructuredTextParser.g:375:1: entryRuleELSIF_Clause : ruleELSIF_Clause EOF ;
    public final void entryRuleELSIF_Clause() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:376:1: ( ruleELSIF_Clause EOF )
            // InternalStructuredTextParser.g:377:1: ruleELSIF_Clause EOF
            {
             before(grammarAccess.getELSIF_ClauseRule()); 
            pushFollow(FOLLOW_1);
            ruleELSIF_Clause();

            state._fsp--;

             after(grammarAccess.getELSIF_ClauseRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleELSIF_Clause"


    // $ANTLR start "ruleELSIF_Clause"
    // InternalStructuredTextParser.g:384:1: ruleELSIF_Clause : ( ( rule__ELSIF_Clause__Group__0 ) ) ;
    public final void ruleELSIF_Clause() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:388:2: ( ( ( rule__ELSIF_Clause__Group__0 ) ) )
            // InternalStructuredTextParser.g:389:2: ( ( rule__ELSIF_Clause__Group__0 ) )
            {
            // InternalStructuredTextParser.g:389:2: ( ( rule__ELSIF_Clause__Group__0 ) )
            // InternalStructuredTextParser.g:390:3: ( rule__ELSIF_Clause__Group__0 )
            {
             before(grammarAccess.getELSIF_ClauseAccess().getGroup()); 
            // InternalStructuredTextParser.g:391:3: ( rule__ELSIF_Clause__Group__0 )
            // InternalStructuredTextParser.g:391:4: rule__ELSIF_Clause__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ELSIF_Clause__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getELSIF_ClauseAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleELSIF_Clause"


    // $ANTLR start "entryRuleELSE_Clause"
    // InternalStructuredTextParser.g:400:1: entryRuleELSE_Clause : ruleELSE_Clause EOF ;
    public final void entryRuleELSE_Clause() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:401:1: ( ruleELSE_Clause EOF )
            // InternalStructuredTextParser.g:402:1: ruleELSE_Clause EOF
            {
             before(grammarAccess.getELSE_ClauseRule()); 
            pushFollow(FOLLOW_1);
            ruleELSE_Clause();

            state._fsp--;

             after(grammarAccess.getELSE_ClauseRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleELSE_Clause"


    // $ANTLR start "ruleELSE_Clause"
    // InternalStructuredTextParser.g:409:1: ruleELSE_Clause : ( ( rule__ELSE_Clause__Group__0 ) ) ;
    public final void ruleELSE_Clause() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:413:2: ( ( ( rule__ELSE_Clause__Group__0 ) ) )
            // InternalStructuredTextParser.g:414:2: ( ( rule__ELSE_Clause__Group__0 ) )
            {
            // InternalStructuredTextParser.g:414:2: ( ( rule__ELSE_Clause__Group__0 ) )
            // InternalStructuredTextParser.g:415:3: ( rule__ELSE_Clause__Group__0 )
            {
             before(grammarAccess.getELSE_ClauseAccess().getGroup()); 
            // InternalStructuredTextParser.g:416:3: ( rule__ELSE_Clause__Group__0 )
            // InternalStructuredTextParser.g:416:4: rule__ELSE_Clause__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__ELSE_Clause__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getELSE_ClauseAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleELSE_Clause"


    // $ANTLR start "entryRuleCase_Stmt"
    // InternalStructuredTextParser.g:425:1: entryRuleCase_Stmt : ruleCase_Stmt EOF ;
    public final void entryRuleCase_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:426:1: ( ruleCase_Stmt EOF )
            // InternalStructuredTextParser.g:427:1: ruleCase_Stmt EOF
            {
             before(grammarAccess.getCase_StmtRule()); 
            pushFollow(FOLLOW_1);
            ruleCase_Stmt();

            state._fsp--;

             after(grammarAccess.getCase_StmtRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCase_Stmt"


    // $ANTLR start "ruleCase_Stmt"
    // InternalStructuredTextParser.g:434:1: ruleCase_Stmt : ( ( rule__Case_Stmt__Group__0 ) ) ;
    public final void ruleCase_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:438:2: ( ( ( rule__Case_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:439:2: ( ( rule__Case_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:439:2: ( ( rule__Case_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:440:3: ( rule__Case_Stmt__Group__0 )
            {
             before(grammarAccess.getCase_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:441:3: ( rule__Case_Stmt__Group__0 )
            // InternalStructuredTextParser.g:441:4: rule__Case_Stmt__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Case_Stmt__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getCase_StmtAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCase_Stmt"


    // $ANTLR start "entryRuleCase_Selection"
    // InternalStructuredTextParser.g:450:1: entryRuleCase_Selection : ruleCase_Selection EOF ;
    public final void entryRuleCase_Selection() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:451:1: ( ruleCase_Selection EOF )
            // InternalStructuredTextParser.g:452:1: ruleCase_Selection EOF
            {
             before(grammarAccess.getCase_SelectionRule()); 
            pushFollow(FOLLOW_1);
            ruleCase_Selection();

            state._fsp--;

             after(grammarAccess.getCase_SelectionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCase_Selection"


    // $ANTLR start "ruleCase_Selection"
    // InternalStructuredTextParser.g:459:1: ruleCase_Selection : ( ( rule__Case_Selection__Group__0 ) ) ;
    public final void ruleCase_Selection() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:463:2: ( ( ( rule__Case_Selection__Group__0 ) ) )
            // InternalStructuredTextParser.g:464:2: ( ( rule__Case_Selection__Group__0 ) )
            {
            // InternalStructuredTextParser.g:464:2: ( ( rule__Case_Selection__Group__0 ) )
            // InternalStructuredTextParser.g:465:3: ( rule__Case_Selection__Group__0 )
            {
             before(grammarAccess.getCase_SelectionAccess().getGroup()); 
            // InternalStructuredTextParser.g:466:3: ( rule__Case_Selection__Group__0 )
            // InternalStructuredTextParser.g:466:4: rule__Case_Selection__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Case_Selection__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getCase_SelectionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCase_Selection"


    // $ANTLR start "entryRuleIteration_Stmt"
    // InternalStructuredTextParser.g:475:1: entryRuleIteration_Stmt : ruleIteration_Stmt EOF ;
    public final void entryRuleIteration_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:476:1: ( ruleIteration_Stmt EOF )
            // InternalStructuredTextParser.g:477:1: ruleIteration_Stmt EOF
            {
             before(grammarAccess.getIteration_StmtRule()); 
            pushFollow(FOLLOW_1);
            ruleIteration_Stmt();

            state._fsp--;

             after(grammarAccess.getIteration_StmtRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleIteration_Stmt"


    // $ANTLR start "ruleIteration_Stmt"
    // InternalStructuredTextParser.g:484:1: ruleIteration_Stmt : ( ( rule__Iteration_Stmt__Alternatives ) ) ;
    public final void ruleIteration_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:488:2: ( ( ( rule__Iteration_Stmt__Alternatives ) ) )
            // InternalStructuredTextParser.g:489:2: ( ( rule__Iteration_Stmt__Alternatives ) )
            {
            // InternalStructuredTextParser.g:489:2: ( ( rule__Iteration_Stmt__Alternatives ) )
            // InternalStructuredTextParser.g:490:3: ( rule__Iteration_Stmt__Alternatives )
            {
             before(grammarAccess.getIteration_StmtAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:491:3: ( rule__Iteration_Stmt__Alternatives )
            // InternalStructuredTextParser.g:491:4: rule__Iteration_Stmt__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Iteration_Stmt__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getIteration_StmtAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleIteration_Stmt"


    // $ANTLR start "entryRuleFor_Stmt"
    // InternalStructuredTextParser.g:500:1: entryRuleFor_Stmt : ruleFor_Stmt EOF ;
    public final void entryRuleFor_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:501:1: ( ruleFor_Stmt EOF )
            // InternalStructuredTextParser.g:502:1: ruleFor_Stmt EOF
            {
             before(grammarAccess.getFor_StmtRule()); 
            pushFollow(FOLLOW_1);
            ruleFor_Stmt();

            state._fsp--;

             after(grammarAccess.getFor_StmtRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleFor_Stmt"


    // $ANTLR start "ruleFor_Stmt"
    // InternalStructuredTextParser.g:509:1: ruleFor_Stmt : ( ( rule__For_Stmt__Group__0 ) ) ;
    public final void ruleFor_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:513:2: ( ( ( rule__For_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:514:2: ( ( rule__For_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:514:2: ( ( rule__For_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:515:3: ( rule__For_Stmt__Group__0 )
            {
             before(grammarAccess.getFor_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:516:3: ( rule__For_Stmt__Group__0 )
            // InternalStructuredTextParser.g:516:4: rule__For_Stmt__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getFor_StmtAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFor_Stmt"


    // $ANTLR start "entryRuleWhile_Stmt"
    // InternalStructuredTextParser.g:525:1: entryRuleWhile_Stmt : ruleWhile_Stmt EOF ;
    public final void entryRuleWhile_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:526:1: ( ruleWhile_Stmt EOF )
            // InternalStructuredTextParser.g:527:1: ruleWhile_Stmt EOF
            {
             before(grammarAccess.getWhile_StmtRule()); 
            pushFollow(FOLLOW_1);
            ruleWhile_Stmt();

            state._fsp--;

             after(grammarAccess.getWhile_StmtRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleWhile_Stmt"


    // $ANTLR start "ruleWhile_Stmt"
    // InternalStructuredTextParser.g:534:1: ruleWhile_Stmt : ( ( rule__While_Stmt__Group__0 ) ) ;
    public final void ruleWhile_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:538:2: ( ( ( rule__While_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:539:2: ( ( rule__While_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:539:2: ( ( rule__While_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:540:3: ( rule__While_Stmt__Group__0 )
            {
             before(grammarAccess.getWhile_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:541:3: ( rule__While_Stmt__Group__0 )
            // InternalStructuredTextParser.g:541:4: rule__While_Stmt__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__While_Stmt__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getWhile_StmtAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleWhile_Stmt"


    // $ANTLR start "entryRuleRepeat_Stmt"
    // InternalStructuredTextParser.g:550:1: entryRuleRepeat_Stmt : ruleRepeat_Stmt EOF ;
    public final void entryRuleRepeat_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:551:1: ( ruleRepeat_Stmt EOF )
            // InternalStructuredTextParser.g:552:1: ruleRepeat_Stmt EOF
            {
             before(grammarAccess.getRepeat_StmtRule()); 
            pushFollow(FOLLOW_1);
            ruleRepeat_Stmt();

            state._fsp--;

             after(grammarAccess.getRepeat_StmtRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleRepeat_Stmt"


    // $ANTLR start "ruleRepeat_Stmt"
    // InternalStructuredTextParser.g:559:1: ruleRepeat_Stmt : ( ( rule__Repeat_Stmt__Group__0 ) ) ;
    public final void ruleRepeat_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:563:2: ( ( ( rule__Repeat_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:564:2: ( ( rule__Repeat_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:564:2: ( ( rule__Repeat_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:565:3: ( rule__Repeat_Stmt__Group__0 )
            {
             before(grammarAccess.getRepeat_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:566:3: ( rule__Repeat_Stmt__Group__0 )
            // InternalStructuredTextParser.g:566:4: rule__Repeat_Stmt__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Repeat_Stmt__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getRepeat_StmtAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleRepeat_Stmt"


    // $ANTLR start "entryRuleExpression"
    // InternalStructuredTextParser.g:575:1: entryRuleExpression : ruleExpression EOF ;
    public final void entryRuleExpression() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:576:1: ( ruleExpression EOF )
            // InternalStructuredTextParser.g:577:1: ruleExpression EOF
            {
             before(grammarAccess.getExpressionRule()); 
            pushFollow(FOLLOW_1);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getExpressionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleExpression"


    // $ANTLR start "ruleExpression"
    // InternalStructuredTextParser.g:584:1: ruleExpression : ( ruleOr_Expression ) ;
    public final void ruleExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:588:2: ( ( ruleOr_Expression ) )
            // InternalStructuredTextParser.g:589:2: ( ruleOr_Expression )
            {
            // InternalStructuredTextParser.g:589:2: ( ruleOr_Expression )
            // InternalStructuredTextParser.g:590:3: ruleOr_Expression
            {
             before(grammarAccess.getExpressionAccess().getOr_ExpressionParserRuleCall()); 
            pushFollow(FOLLOW_2);
            ruleOr_Expression();

            state._fsp--;

             after(grammarAccess.getExpressionAccess().getOr_ExpressionParserRuleCall()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleExpression"


    // $ANTLR start "entryRuleOr_Expression"
    // InternalStructuredTextParser.g:600:1: entryRuleOr_Expression : ruleOr_Expression EOF ;
    public final void entryRuleOr_Expression() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:601:1: ( ruleOr_Expression EOF )
            // InternalStructuredTextParser.g:602:1: ruleOr_Expression EOF
            {
             before(grammarAccess.getOr_ExpressionRule()); 
            pushFollow(FOLLOW_1);
            ruleOr_Expression();

            state._fsp--;

             after(grammarAccess.getOr_ExpressionRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleOr_Expression"


    // $ANTLR start "ruleOr_Expression"
    // InternalStructuredTextParser.g:609:1: ruleOr_Expression : ( ( rule__Or_Expression__Group__0 ) ) ;
    public final void ruleOr_Expression() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:613:2: ( ( ( rule__Or_Expression__Group__0 ) ) )
            // InternalStructuredTextParser.g:614:2: ( ( rule__Or_Expression__Group__0 ) )
            {
            // InternalStructuredTextParser.g:614:2: ( ( rule__Or_Expression__Group__0 ) )
            // InternalStructuredTextParser.g:615:3: ( rule__Or_Expression__Group__0 )
            {
             before(grammarAccess.getOr_ExpressionAccess().getGroup()); 
            // InternalStructuredTextParser.g:616:3: ( rule__Or_Expression__Group__0 )
            // InternalStructuredTextParser.g:616:4: rule__Or_Expression__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Or_Expression__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getOr_ExpressionAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleOr_Expression"


    // $ANTLR start "entryRuleXor_Expr"
    // InternalStructuredTextParser.g:625:1: entryRuleXor_Expr : ruleXor_Expr EOF ;
    public final void entryRuleXor_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:626:1: ( ruleXor_Expr EOF )
            // InternalStructuredTextParser.g:627:1: ruleXor_Expr EOF
            {
             before(grammarAccess.getXor_ExprRule()); 
            pushFollow(FOLLOW_1);
            ruleXor_Expr();

            state._fsp--;

             after(grammarAccess.getXor_ExprRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleXor_Expr"


    // $ANTLR start "ruleXor_Expr"
    // InternalStructuredTextParser.g:634:1: ruleXor_Expr : ( ( rule__Xor_Expr__Group__0 ) ) ;
    public final void ruleXor_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:638:2: ( ( ( rule__Xor_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:639:2: ( ( rule__Xor_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:639:2: ( ( rule__Xor_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:640:3: ( rule__Xor_Expr__Group__0 )
            {
             before(grammarAccess.getXor_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:641:3: ( rule__Xor_Expr__Group__0 )
            // InternalStructuredTextParser.g:641:4: rule__Xor_Expr__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Xor_Expr__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getXor_ExprAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleXor_Expr"


    // $ANTLR start "entryRuleAnd_Expr"
    // InternalStructuredTextParser.g:650:1: entryRuleAnd_Expr : ruleAnd_Expr EOF ;
    public final void entryRuleAnd_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:651:1: ( ruleAnd_Expr EOF )
            // InternalStructuredTextParser.g:652:1: ruleAnd_Expr EOF
            {
             before(grammarAccess.getAnd_ExprRule()); 
            pushFollow(FOLLOW_1);
            ruleAnd_Expr();

            state._fsp--;

             after(grammarAccess.getAnd_ExprRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAnd_Expr"


    // $ANTLR start "ruleAnd_Expr"
    // InternalStructuredTextParser.g:659:1: ruleAnd_Expr : ( ( rule__And_Expr__Group__0 ) ) ;
    public final void ruleAnd_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:663:2: ( ( ( rule__And_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:664:2: ( ( rule__And_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:664:2: ( ( rule__And_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:665:3: ( rule__And_Expr__Group__0 )
            {
             before(grammarAccess.getAnd_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:666:3: ( rule__And_Expr__Group__0 )
            // InternalStructuredTextParser.g:666:4: rule__And_Expr__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__And_Expr__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAnd_ExprAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAnd_Expr"


    // $ANTLR start "entryRuleCompare_Expr"
    // InternalStructuredTextParser.g:675:1: entryRuleCompare_Expr : ruleCompare_Expr EOF ;
    public final void entryRuleCompare_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:676:1: ( ruleCompare_Expr EOF )
            // InternalStructuredTextParser.g:677:1: ruleCompare_Expr EOF
            {
             before(grammarAccess.getCompare_ExprRule()); 
            pushFollow(FOLLOW_1);
            ruleCompare_Expr();

            state._fsp--;

             after(grammarAccess.getCompare_ExprRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleCompare_Expr"


    // $ANTLR start "ruleCompare_Expr"
    // InternalStructuredTextParser.g:684:1: ruleCompare_Expr : ( ( rule__Compare_Expr__Group__0 ) ) ;
    public final void ruleCompare_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:688:2: ( ( ( rule__Compare_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:689:2: ( ( rule__Compare_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:689:2: ( ( rule__Compare_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:690:3: ( rule__Compare_Expr__Group__0 )
            {
             before(grammarAccess.getCompare_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:691:3: ( rule__Compare_Expr__Group__0 )
            // InternalStructuredTextParser.g:691:4: rule__Compare_Expr__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Compare_Expr__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getCompare_ExprAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCompare_Expr"


    // $ANTLR start "entryRuleEqu_Expr"
    // InternalStructuredTextParser.g:700:1: entryRuleEqu_Expr : ruleEqu_Expr EOF ;
    public final void entryRuleEqu_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:701:1: ( ruleEqu_Expr EOF )
            // InternalStructuredTextParser.g:702:1: ruleEqu_Expr EOF
            {
             before(grammarAccess.getEqu_ExprRule()); 
            pushFollow(FOLLOW_1);
            ruleEqu_Expr();

            state._fsp--;

             after(grammarAccess.getEqu_ExprRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleEqu_Expr"


    // $ANTLR start "ruleEqu_Expr"
    // InternalStructuredTextParser.g:709:1: ruleEqu_Expr : ( ( rule__Equ_Expr__Group__0 ) ) ;
    public final void ruleEqu_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:713:2: ( ( ( rule__Equ_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:714:2: ( ( rule__Equ_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:714:2: ( ( rule__Equ_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:715:3: ( rule__Equ_Expr__Group__0 )
            {
             before(grammarAccess.getEqu_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:716:3: ( rule__Equ_Expr__Group__0 )
            // InternalStructuredTextParser.g:716:4: rule__Equ_Expr__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Equ_Expr__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getEqu_ExprAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEqu_Expr"


    // $ANTLR start "entryRuleAdd_Expr"
    // InternalStructuredTextParser.g:725:1: entryRuleAdd_Expr : ruleAdd_Expr EOF ;
    public final void entryRuleAdd_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:726:1: ( ruleAdd_Expr EOF )
            // InternalStructuredTextParser.g:727:1: ruleAdd_Expr EOF
            {
             before(grammarAccess.getAdd_ExprRule()); 
            pushFollow(FOLLOW_1);
            ruleAdd_Expr();

            state._fsp--;

             after(grammarAccess.getAdd_ExprRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAdd_Expr"


    // $ANTLR start "ruleAdd_Expr"
    // InternalStructuredTextParser.g:734:1: ruleAdd_Expr : ( ( rule__Add_Expr__Group__0 ) ) ;
    public final void ruleAdd_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:738:2: ( ( ( rule__Add_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:739:2: ( ( rule__Add_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:739:2: ( ( rule__Add_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:740:3: ( rule__Add_Expr__Group__0 )
            {
             before(grammarAccess.getAdd_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:741:3: ( rule__Add_Expr__Group__0 )
            // InternalStructuredTextParser.g:741:4: rule__Add_Expr__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Add_Expr__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAdd_ExprAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAdd_Expr"


    // $ANTLR start "entryRuleTerm"
    // InternalStructuredTextParser.g:750:1: entryRuleTerm : ruleTerm EOF ;
    public final void entryRuleTerm() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:751:1: ( ruleTerm EOF )
            // InternalStructuredTextParser.g:752:1: ruleTerm EOF
            {
             before(grammarAccess.getTermRule()); 
            pushFollow(FOLLOW_1);
            ruleTerm();

            state._fsp--;

             after(grammarAccess.getTermRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTerm"


    // $ANTLR start "ruleTerm"
    // InternalStructuredTextParser.g:759:1: ruleTerm : ( ( rule__Term__Group__0 ) ) ;
    public final void ruleTerm() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:763:2: ( ( ( rule__Term__Group__0 ) ) )
            // InternalStructuredTextParser.g:764:2: ( ( rule__Term__Group__0 ) )
            {
            // InternalStructuredTextParser.g:764:2: ( ( rule__Term__Group__0 ) )
            // InternalStructuredTextParser.g:765:3: ( rule__Term__Group__0 )
            {
             before(grammarAccess.getTermAccess().getGroup()); 
            // InternalStructuredTextParser.g:766:3: ( rule__Term__Group__0 )
            // InternalStructuredTextParser.g:766:4: rule__Term__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Term__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getTermAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTerm"


    // $ANTLR start "entryRulePower_Expr"
    // InternalStructuredTextParser.g:775:1: entryRulePower_Expr : rulePower_Expr EOF ;
    public final void entryRulePower_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:776:1: ( rulePower_Expr EOF )
            // InternalStructuredTextParser.g:777:1: rulePower_Expr EOF
            {
             before(grammarAccess.getPower_ExprRule()); 
            pushFollow(FOLLOW_1);
            rulePower_Expr();

            state._fsp--;

             after(grammarAccess.getPower_ExprRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePower_Expr"


    // $ANTLR start "rulePower_Expr"
    // InternalStructuredTextParser.g:784:1: rulePower_Expr : ( ( rule__Power_Expr__Group__0 ) ) ;
    public final void rulePower_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:788:2: ( ( ( rule__Power_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:789:2: ( ( rule__Power_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:789:2: ( ( rule__Power_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:790:3: ( rule__Power_Expr__Group__0 )
            {
             before(grammarAccess.getPower_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:791:3: ( rule__Power_Expr__Group__0 )
            // InternalStructuredTextParser.g:791:4: rule__Power_Expr__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Power_Expr__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getPower_ExprAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePower_Expr"


    // $ANTLR start "entryRuleUnary_Expr"
    // InternalStructuredTextParser.g:800:1: entryRuleUnary_Expr : ruleUnary_Expr EOF ;
    public final void entryRuleUnary_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:801:1: ( ruleUnary_Expr EOF )
            // InternalStructuredTextParser.g:802:1: ruleUnary_Expr EOF
            {
             before(grammarAccess.getUnary_ExprRule()); 
            pushFollow(FOLLOW_1);
            ruleUnary_Expr();

            state._fsp--;

             after(grammarAccess.getUnary_ExprRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleUnary_Expr"


    // $ANTLR start "ruleUnary_Expr"
    // InternalStructuredTextParser.g:809:1: ruleUnary_Expr : ( ( rule__Unary_Expr__Alternatives ) ) ;
    public final void ruleUnary_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:813:2: ( ( ( rule__Unary_Expr__Alternatives ) ) )
            // InternalStructuredTextParser.g:814:2: ( ( rule__Unary_Expr__Alternatives ) )
            {
            // InternalStructuredTextParser.g:814:2: ( ( rule__Unary_Expr__Alternatives ) )
            // InternalStructuredTextParser.g:815:3: ( rule__Unary_Expr__Alternatives )
            {
             before(grammarAccess.getUnary_ExprAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:816:3: ( rule__Unary_Expr__Alternatives )
            // InternalStructuredTextParser.g:816:4: rule__Unary_Expr__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Unary_Expr__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getUnary_ExprAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleUnary_Expr"


    // $ANTLR start "entryRulePrimary_Expr"
    // InternalStructuredTextParser.g:825:1: entryRulePrimary_Expr : rulePrimary_Expr EOF ;
    public final void entryRulePrimary_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:826:1: ( rulePrimary_Expr EOF )
            // InternalStructuredTextParser.g:827:1: rulePrimary_Expr EOF
            {
             before(grammarAccess.getPrimary_ExprRule()); 
            pushFollow(FOLLOW_1);
            rulePrimary_Expr();

            state._fsp--;

             after(grammarAccess.getPrimary_ExprRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePrimary_Expr"


    // $ANTLR start "rulePrimary_Expr"
    // InternalStructuredTextParser.g:834:1: rulePrimary_Expr : ( ( rule__Primary_Expr__Alternatives ) ) ;
    public final void rulePrimary_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:838:2: ( ( ( rule__Primary_Expr__Alternatives ) ) )
            // InternalStructuredTextParser.g:839:2: ( ( rule__Primary_Expr__Alternatives ) )
            {
            // InternalStructuredTextParser.g:839:2: ( ( rule__Primary_Expr__Alternatives ) )
            // InternalStructuredTextParser.g:840:3: ( rule__Primary_Expr__Alternatives )
            {
             before(grammarAccess.getPrimary_ExprAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:841:3: ( rule__Primary_Expr__Alternatives )
            // InternalStructuredTextParser.g:841:4: rule__Primary_Expr__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Primary_Expr__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getPrimary_ExprAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePrimary_Expr"


    // $ANTLR start "entryRuleFunc_Call"
    // InternalStructuredTextParser.g:850:1: entryRuleFunc_Call : ruleFunc_Call EOF ;
    public final void entryRuleFunc_Call() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:851:1: ( ruleFunc_Call EOF )
            // InternalStructuredTextParser.g:852:1: ruleFunc_Call EOF
            {
             before(grammarAccess.getFunc_CallRule()); 
            pushFollow(FOLLOW_1);
            ruleFunc_Call();

            state._fsp--;

             after(grammarAccess.getFunc_CallRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleFunc_Call"


    // $ANTLR start "ruleFunc_Call"
    // InternalStructuredTextParser.g:859:1: ruleFunc_Call : ( ( rule__Func_Call__Group__0 ) ) ;
    public final void ruleFunc_Call() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:863:2: ( ( ( rule__Func_Call__Group__0 ) ) )
            // InternalStructuredTextParser.g:864:2: ( ( rule__Func_Call__Group__0 ) )
            {
            // InternalStructuredTextParser.g:864:2: ( ( rule__Func_Call__Group__0 ) )
            // InternalStructuredTextParser.g:865:3: ( rule__Func_Call__Group__0 )
            {
             before(grammarAccess.getFunc_CallAccess().getGroup()); 
            // InternalStructuredTextParser.g:866:3: ( rule__Func_Call__Group__0 )
            // InternalStructuredTextParser.g:866:4: rule__Func_Call__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Func_Call__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getFunc_CallAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFunc_Call"


    // $ANTLR start "entryRuleParam_Assign"
    // InternalStructuredTextParser.g:875:1: entryRuleParam_Assign : ruleParam_Assign EOF ;
    public final void entryRuleParam_Assign() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:876:1: ( ruleParam_Assign EOF )
            // InternalStructuredTextParser.g:877:1: ruleParam_Assign EOF
            {
             before(grammarAccess.getParam_AssignRule()); 
            pushFollow(FOLLOW_1);
            ruleParam_Assign();

            state._fsp--;

             after(grammarAccess.getParam_AssignRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleParam_Assign"


    // $ANTLR start "ruleParam_Assign"
    // InternalStructuredTextParser.g:884:1: ruleParam_Assign : ( ( rule__Param_Assign__Alternatives ) ) ;
    public final void ruleParam_Assign() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:888:2: ( ( ( rule__Param_Assign__Alternatives ) ) )
            // InternalStructuredTextParser.g:889:2: ( ( rule__Param_Assign__Alternatives ) )
            {
            // InternalStructuredTextParser.g:889:2: ( ( rule__Param_Assign__Alternatives ) )
            // InternalStructuredTextParser.g:890:3: ( rule__Param_Assign__Alternatives )
            {
             before(grammarAccess.getParam_AssignAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:891:3: ( rule__Param_Assign__Alternatives )
            // InternalStructuredTextParser.g:891:4: rule__Param_Assign__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Param_Assign__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getParam_AssignAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleParam_Assign"


    // $ANTLR start "entryRuleParam_Assign_In"
    // InternalStructuredTextParser.g:900:1: entryRuleParam_Assign_In : ruleParam_Assign_In EOF ;
    public final void entryRuleParam_Assign_In() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:901:1: ( ruleParam_Assign_In EOF )
            // InternalStructuredTextParser.g:902:1: ruleParam_Assign_In EOF
            {
             before(grammarAccess.getParam_Assign_InRule()); 
            pushFollow(FOLLOW_1);
            ruleParam_Assign_In();

            state._fsp--;

             after(grammarAccess.getParam_Assign_InRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleParam_Assign_In"


    // $ANTLR start "ruleParam_Assign_In"
    // InternalStructuredTextParser.g:909:1: ruleParam_Assign_In : ( ( rule__Param_Assign_In__Group__0 ) ) ;
    public final void ruleParam_Assign_In() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:913:2: ( ( ( rule__Param_Assign_In__Group__0 ) ) )
            // InternalStructuredTextParser.g:914:2: ( ( rule__Param_Assign_In__Group__0 ) )
            {
            // InternalStructuredTextParser.g:914:2: ( ( rule__Param_Assign_In__Group__0 ) )
            // InternalStructuredTextParser.g:915:3: ( rule__Param_Assign_In__Group__0 )
            {
             before(grammarAccess.getParam_Assign_InAccess().getGroup()); 
            // InternalStructuredTextParser.g:916:3: ( rule__Param_Assign_In__Group__0 )
            // InternalStructuredTextParser.g:916:4: rule__Param_Assign_In__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Param_Assign_In__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getParam_Assign_InAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleParam_Assign_In"


    // $ANTLR start "entryRuleParam_Assign_Out"
    // InternalStructuredTextParser.g:925:1: entryRuleParam_Assign_Out : ruleParam_Assign_Out EOF ;
    public final void entryRuleParam_Assign_Out() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:926:1: ( ruleParam_Assign_Out EOF )
            // InternalStructuredTextParser.g:927:1: ruleParam_Assign_Out EOF
            {
             before(grammarAccess.getParam_Assign_OutRule()); 
            pushFollow(FOLLOW_1);
            ruleParam_Assign_Out();

            state._fsp--;

             after(grammarAccess.getParam_Assign_OutRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleParam_Assign_Out"


    // $ANTLR start "ruleParam_Assign_Out"
    // InternalStructuredTextParser.g:934:1: ruleParam_Assign_Out : ( ( rule__Param_Assign_Out__Group__0 ) ) ;
    public final void ruleParam_Assign_Out() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:938:2: ( ( ( rule__Param_Assign_Out__Group__0 ) ) )
            // InternalStructuredTextParser.g:939:2: ( ( rule__Param_Assign_Out__Group__0 ) )
            {
            // InternalStructuredTextParser.g:939:2: ( ( rule__Param_Assign_Out__Group__0 ) )
            // InternalStructuredTextParser.g:940:3: ( rule__Param_Assign_Out__Group__0 )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getGroup()); 
            // InternalStructuredTextParser.g:941:3: ( rule__Param_Assign_Out__Group__0 )
            // InternalStructuredTextParser.g:941:4: rule__Param_Assign_Out__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Param_Assign_Out__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getParam_Assign_OutAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleParam_Assign_Out"


    // $ANTLR start "entryRuleVariable"
    // InternalStructuredTextParser.g:950:1: entryRuleVariable : ruleVariable EOF ;
    public final void entryRuleVariable() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:951:1: ( ruleVariable EOF )
            // InternalStructuredTextParser.g:952:1: ruleVariable EOF
            {
             before(grammarAccess.getVariableRule()); 
            pushFollow(FOLLOW_1);
            ruleVariable();

            state._fsp--;

             after(grammarAccess.getVariableRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVariable"


    // $ANTLR start "ruleVariable"
    // InternalStructuredTextParser.g:959:1: ruleVariable : ( ( rule__Variable__Group__0 ) ) ;
    public final void ruleVariable() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:963:2: ( ( ( rule__Variable__Group__0 ) ) )
            // InternalStructuredTextParser.g:964:2: ( ( rule__Variable__Group__0 ) )
            {
            // InternalStructuredTextParser.g:964:2: ( ( rule__Variable__Group__0 ) )
            // InternalStructuredTextParser.g:965:3: ( rule__Variable__Group__0 )
            {
             before(grammarAccess.getVariableAccess().getGroup()); 
            // InternalStructuredTextParser.g:966:3: ( rule__Variable__Group__0 )
            // InternalStructuredTextParser.g:966:4: rule__Variable__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Variable__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getVariableAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVariable"


    // $ANTLR start "entryRuleVariable_Subscript"
    // InternalStructuredTextParser.g:975:1: entryRuleVariable_Subscript : ruleVariable_Subscript EOF ;
    public final void entryRuleVariable_Subscript() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:976:1: ( ruleVariable_Subscript EOF )
            // InternalStructuredTextParser.g:977:1: ruleVariable_Subscript EOF
            {
             before(grammarAccess.getVariable_SubscriptRule()); 
            pushFollow(FOLLOW_1);
            ruleVariable_Subscript();

            state._fsp--;

             after(grammarAccess.getVariable_SubscriptRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVariable_Subscript"


    // $ANTLR start "ruleVariable_Subscript"
    // InternalStructuredTextParser.g:984:1: ruleVariable_Subscript : ( ( rule__Variable_Subscript__Group__0 ) ) ;
    public final void ruleVariable_Subscript() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:988:2: ( ( ( rule__Variable_Subscript__Group__0 ) ) )
            // InternalStructuredTextParser.g:989:2: ( ( rule__Variable_Subscript__Group__0 ) )
            {
            // InternalStructuredTextParser.g:989:2: ( ( rule__Variable_Subscript__Group__0 ) )
            // InternalStructuredTextParser.g:990:3: ( rule__Variable_Subscript__Group__0 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getGroup()); 
            // InternalStructuredTextParser.g:991:3: ( rule__Variable_Subscript__Group__0 )
            // InternalStructuredTextParser.g:991:4: rule__Variable_Subscript__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getVariable_SubscriptAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVariable_Subscript"


    // $ANTLR start "entryRuleVariable_Adapter"
    // InternalStructuredTextParser.g:1000:1: entryRuleVariable_Adapter : ruleVariable_Adapter EOF ;
    public final void entryRuleVariable_Adapter() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1001:1: ( ruleVariable_Adapter EOF )
            // InternalStructuredTextParser.g:1002:1: ruleVariable_Adapter EOF
            {
             before(grammarAccess.getVariable_AdapterRule()); 
            pushFollow(FOLLOW_1);
            ruleVariable_Adapter();

            state._fsp--;

             after(grammarAccess.getVariable_AdapterRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVariable_Adapter"


    // $ANTLR start "ruleVariable_Adapter"
    // InternalStructuredTextParser.g:1009:1: ruleVariable_Adapter : ( ( rule__Variable_Adapter__Group__0 ) ) ;
    public final void ruleVariable_Adapter() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1013:2: ( ( ( rule__Variable_Adapter__Group__0 ) ) )
            // InternalStructuredTextParser.g:1014:2: ( ( rule__Variable_Adapter__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1014:2: ( ( rule__Variable_Adapter__Group__0 ) )
            // InternalStructuredTextParser.g:1015:3: ( rule__Variable_Adapter__Group__0 )
            {
             before(grammarAccess.getVariable_AdapterAccess().getGroup()); 
            // InternalStructuredTextParser.g:1016:3: ( rule__Variable_Adapter__Group__0 )
            // InternalStructuredTextParser.g:1016:4: rule__Variable_Adapter__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getVariable_AdapterAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVariable_Adapter"


    // $ANTLR start "entryRuleAdapterRoot"
    // InternalStructuredTextParser.g:1025:1: entryRuleAdapterRoot : ruleAdapterRoot EOF ;
    public final void entryRuleAdapterRoot() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1026:1: ( ruleAdapterRoot EOF )
            // InternalStructuredTextParser.g:1027:1: ruleAdapterRoot EOF
            {
             before(grammarAccess.getAdapterRootRule()); 
            pushFollow(FOLLOW_1);
            ruleAdapterRoot();

            state._fsp--;

             after(grammarAccess.getAdapterRootRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAdapterRoot"


    // $ANTLR start "ruleAdapterRoot"
    // InternalStructuredTextParser.g:1034:1: ruleAdapterRoot : ( ( rule__AdapterRoot__Group__0 ) ) ;
    public final void ruleAdapterRoot() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1038:2: ( ( ( rule__AdapterRoot__Group__0 ) ) )
            // InternalStructuredTextParser.g:1039:2: ( ( rule__AdapterRoot__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1039:2: ( ( rule__AdapterRoot__Group__0 ) )
            // InternalStructuredTextParser.g:1040:3: ( rule__AdapterRoot__Group__0 )
            {
             before(grammarAccess.getAdapterRootAccess().getGroup()); 
            // InternalStructuredTextParser.g:1041:3: ( rule__AdapterRoot__Group__0 )
            // InternalStructuredTextParser.g:1041:4: rule__AdapterRoot__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__AdapterRoot__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getAdapterRootAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAdapterRoot"


    // $ANTLR start "entryRuleMultibit_Part_Access"
    // InternalStructuredTextParser.g:1050:1: entryRuleMultibit_Part_Access : ruleMultibit_Part_Access EOF ;
    public final void entryRuleMultibit_Part_Access() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1051:1: ( ruleMultibit_Part_Access EOF )
            // InternalStructuredTextParser.g:1052:1: ruleMultibit_Part_Access EOF
            {
             before(grammarAccess.getMultibit_Part_AccessRule()); 
            pushFollow(FOLLOW_1);
            ruleMultibit_Part_Access();

            state._fsp--;

             after(grammarAccess.getMultibit_Part_AccessRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleMultibit_Part_Access"


    // $ANTLR start "ruleMultibit_Part_Access"
    // InternalStructuredTextParser.g:1059:1: ruleMultibit_Part_Access : ( ( rule__Multibit_Part_Access__Alternatives ) ) ;
    public final void ruleMultibit_Part_Access() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1063:2: ( ( ( rule__Multibit_Part_Access__Alternatives ) ) )
            // InternalStructuredTextParser.g:1064:2: ( ( rule__Multibit_Part_Access__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1064:2: ( ( rule__Multibit_Part_Access__Alternatives ) )
            // InternalStructuredTextParser.g:1065:3: ( rule__Multibit_Part_Access__Alternatives )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1066:3: ( rule__Multibit_Part_Access__Alternatives )
            // InternalStructuredTextParser.g:1066:4: rule__Multibit_Part_Access__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleMultibit_Part_Access"


    // $ANTLR start "entryRuleAdapter_Name"
    // InternalStructuredTextParser.g:1075:1: entryRuleAdapter_Name : ruleAdapter_Name EOF ;
    public final void entryRuleAdapter_Name() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1076:1: ( ruleAdapter_Name EOF )
            // InternalStructuredTextParser.g:1077:1: ruleAdapter_Name EOF
            {
             before(grammarAccess.getAdapter_NameRule()); 
            pushFollow(FOLLOW_1);
            ruleAdapter_Name();

            state._fsp--;

             after(grammarAccess.getAdapter_NameRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleAdapter_Name"


    // $ANTLR start "ruleAdapter_Name"
    // InternalStructuredTextParser.g:1084:1: ruleAdapter_Name : ( ( rule__Adapter_Name__Alternatives ) ) ;
    public final void ruleAdapter_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1088:2: ( ( ( rule__Adapter_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1089:2: ( ( rule__Adapter_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1089:2: ( ( rule__Adapter_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1090:3: ( rule__Adapter_Name__Alternatives )
            {
             before(grammarAccess.getAdapter_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1091:3: ( rule__Adapter_Name__Alternatives )
            // InternalStructuredTextParser.g:1091:4: rule__Adapter_Name__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Adapter_Name__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getAdapter_NameAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAdapter_Name"


    // $ANTLR start "entryRuleVariable_Primary"
    // InternalStructuredTextParser.g:1100:1: entryRuleVariable_Primary : ruleVariable_Primary EOF ;
    public final void entryRuleVariable_Primary() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1101:1: ( ruleVariable_Primary EOF )
            // InternalStructuredTextParser.g:1102:1: ruleVariable_Primary EOF
            {
             before(grammarAccess.getVariable_PrimaryRule()); 
            pushFollow(FOLLOW_1);
            ruleVariable_Primary();

            state._fsp--;

             after(grammarAccess.getVariable_PrimaryRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVariable_Primary"


    // $ANTLR start "ruleVariable_Primary"
    // InternalStructuredTextParser.g:1109:1: ruleVariable_Primary : ( ( rule__Variable_Primary__VarAssignment ) ) ;
    public final void ruleVariable_Primary() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1113:2: ( ( ( rule__Variable_Primary__VarAssignment ) ) )
            // InternalStructuredTextParser.g:1114:2: ( ( rule__Variable_Primary__VarAssignment ) )
            {
            // InternalStructuredTextParser.g:1114:2: ( ( rule__Variable_Primary__VarAssignment ) )
            // InternalStructuredTextParser.g:1115:3: ( rule__Variable_Primary__VarAssignment )
            {
             before(grammarAccess.getVariable_PrimaryAccess().getVarAssignment()); 
            // InternalStructuredTextParser.g:1116:3: ( rule__Variable_Primary__VarAssignment )
            // InternalStructuredTextParser.g:1116:4: rule__Variable_Primary__VarAssignment
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Primary__VarAssignment();

            state._fsp--;


            }

             after(grammarAccess.getVariable_PrimaryAccess().getVarAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVariable_Primary"


    // $ANTLR start "entryRuleVariable_Name"
    // InternalStructuredTextParser.g:1125:1: entryRuleVariable_Name : ruleVariable_Name EOF ;
    public final void entryRuleVariable_Name() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1126:1: ( ruleVariable_Name EOF )
            // InternalStructuredTextParser.g:1127:1: ruleVariable_Name EOF
            {
             before(grammarAccess.getVariable_NameRule()); 
            pushFollow(FOLLOW_1);
            ruleVariable_Name();

            state._fsp--;

             after(grammarAccess.getVariable_NameRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleVariable_Name"


    // $ANTLR start "ruleVariable_Name"
    // InternalStructuredTextParser.g:1134:1: ruleVariable_Name : ( ( rule__Variable_Name__Alternatives ) ) ;
    public final void ruleVariable_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1138:2: ( ( ( rule__Variable_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1139:2: ( ( rule__Variable_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1139:2: ( ( rule__Variable_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1140:3: ( rule__Variable_Name__Alternatives )
            {
             before(grammarAccess.getVariable_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1141:3: ( rule__Variable_Name__Alternatives )
            // InternalStructuredTextParser.g:1141:4: rule__Variable_Name__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Name__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getVariable_NameAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleVariable_Name"


    // $ANTLR start "entryRuleConstant"
    // InternalStructuredTextParser.g:1150:1: entryRuleConstant : ruleConstant EOF ;
    public final void entryRuleConstant() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1151:1: ( ruleConstant EOF )
            // InternalStructuredTextParser.g:1152:1: ruleConstant EOF
            {
             before(grammarAccess.getConstantRule()); 
            pushFollow(FOLLOW_1);
            ruleConstant();

            state._fsp--;

             after(grammarAccess.getConstantRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleConstant"


    // $ANTLR start "ruleConstant"
    // InternalStructuredTextParser.g:1159:1: ruleConstant : ( ( rule__Constant__Alternatives ) ) ;
    public final void ruleConstant() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1163:2: ( ( ( rule__Constant__Alternatives ) ) )
            // InternalStructuredTextParser.g:1164:2: ( ( rule__Constant__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1164:2: ( ( rule__Constant__Alternatives ) )
            // InternalStructuredTextParser.g:1165:3: ( rule__Constant__Alternatives )
            {
             before(grammarAccess.getConstantAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1166:3: ( rule__Constant__Alternatives )
            // InternalStructuredTextParser.g:1166:4: rule__Constant__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Constant__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getConstantAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleConstant"


    // $ANTLR start "entryRuleNumeric_Literal"
    // InternalStructuredTextParser.g:1175:1: entryRuleNumeric_Literal : ruleNumeric_Literal EOF ;
    public final void entryRuleNumeric_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1176:1: ( ruleNumeric_Literal EOF )
            // InternalStructuredTextParser.g:1177:1: ruleNumeric_Literal EOF
            {
             before(grammarAccess.getNumeric_LiteralRule()); 
            pushFollow(FOLLOW_1);
            ruleNumeric_Literal();

            state._fsp--;

             after(grammarAccess.getNumeric_LiteralRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleNumeric_Literal"


    // $ANTLR start "ruleNumeric_Literal"
    // InternalStructuredTextParser.g:1184:1: ruleNumeric_Literal : ( ( rule__Numeric_Literal__Alternatives ) ) ;
    public final void ruleNumeric_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1188:2: ( ( ( rule__Numeric_Literal__Alternatives ) ) )
            // InternalStructuredTextParser.g:1189:2: ( ( rule__Numeric_Literal__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1189:2: ( ( rule__Numeric_Literal__Alternatives ) )
            // InternalStructuredTextParser.g:1190:3: ( rule__Numeric_Literal__Alternatives )
            {
             before(grammarAccess.getNumeric_LiteralAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1191:3: ( rule__Numeric_Literal__Alternatives )
            // InternalStructuredTextParser.g:1191:4: rule__Numeric_Literal__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Numeric_Literal__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getNumeric_LiteralAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleNumeric_Literal"


    // $ANTLR start "entryRuleInt_Literal"
    // InternalStructuredTextParser.g:1200:1: entryRuleInt_Literal : ruleInt_Literal EOF ;
    public final void entryRuleInt_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1201:1: ( ruleInt_Literal EOF )
            // InternalStructuredTextParser.g:1202:1: ruleInt_Literal EOF
            {
             before(grammarAccess.getInt_LiteralRule()); 
            pushFollow(FOLLOW_1);
            ruleInt_Literal();

            state._fsp--;

             after(grammarAccess.getInt_LiteralRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleInt_Literal"


    // $ANTLR start "ruleInt_Literal"
    // InternalStructuredTextParser.g:1209:1: ruleInt_Literal : ( ( rule__Int_Literal__Group__0 ) ) ;
    public final void ruleInt_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1213:2: ( ( ( rule__Int_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1214:2: ( ( rule__Int_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1214:2: ( ( rule__Int_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1215:3: ( rule__Int_Literal__Group__0 )
            {
             before(grammarAccess.getInt_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1216:3: ( rule__Int_Literal__Group__0 )
            // InternalStructuredTextParser.g:1216:4: rule__Int_Literal__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Int_Literal__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getInt_LiteralAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleInt_Literal"


    // $ANTLR start "entryRuleSigned_Int"
    // InternalStructuredTextParser.g:1225:1: entryRuleSigned_Int : ruleSigned_Int EOF ;
    public final void entryRuleSigned_Int() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1226:1: ( ruleSigned_Int EOF )
            // InternalStructuredTextParser.g:1227:1: ruleSigned_Int EOF
            {
             before(grammarAccess.getSigned_IntRule()); 
            pushFollow(FOLLOW_1);
            ruleSigned_Int();

            state._fsp--;

             after(grammarAccess.getSigned_IntRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleSigned_Int"


    // $ANTLR start "ruleSigned_Int"
    // InternalStructuredTextParser.g:1234:1: ruleSigned_Int : ( ( rule__Signed_Int__Group__0 ) ) ;
    public final void ruleSigned_Int() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1238:2: ( ( ( rule__Signed_Int__Group__0 ) ) )
            // InternalStructuredTextParser.g:1239:2: ( ( rule__Signed_Int__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1239:2: ( ( rule__Signed_Int__Group__0 ) )
            // InternalStructuredTextParser.g:1240:3: ( rule__Signed_Int__Group__0 )
            {
             before(grammarAccess.getSigned_IntAccess().getGroup()); 
            // InternalStructuredTextParser.g:1241:3: ( rule__Signed_Int__Group__0 )
            // InternalStructuredTextParser.g:1241:4: rule__Signed_Int__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Signed_Int__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getSigned_IntAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleSigned_Int"


    // $ANTLR start "entryRulePartial_Value"
    // InternalStructuredTextParser.g:1250:1: entryRulePartial_Value : rulePartial_Value EOF ;
    public final void entryRulePartial_Value() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1251:1: ( rulePartial_Value EOF )
            // InternalStructuredTextParser.g:1252:1: rulePartial_Value EOF
            {
             before(grammarAccess.getPartial_ValueRule()); 
            pushFollow(FOLLOW_1);
            rulePartial_Value();

            state._fsp--;

             after(grammarAccess.getPartial_ValueRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRulePartial_Value"


    // $ANTLR start "rulePartial_Value"
    // InternalStructuredTextParser.g:1259:1: rulePartial_Value : ( RULE_UNSIGNED_INT ) ;
    public final void rulePartial_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1263:2: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:1264:2: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:1264:2: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:1265:3: RULE_UNSIGNED_INT
            {
             before(grammarAccess.getPartial_ValueAccess().getUNSIGNED_INTTerminalRuleCall()); 
            match(input,RULE_UNSIGNED_INT,FOLLOW_2); 
             after(grammarAccess.getPartial_ValueAccess().getUNSIGNED_INTTerminalRuleCall()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePartial_Value"


    // $ANTLR start "entryRuleArray_Size"
    // InternalStructuredTextParser.g:1275:1: entryRuleArray_Size : ruleArray_Size EOF ;
    public final void entryRuleArray_Size() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1276:1: ( ruleArray_Size EOF )
            // InternalStructuredTextParser.g:1277:1: ruleArray_Size EOF
            {
             before(grammarAccess.getArray_SizeRule()); 
            pushFollow(FOLLOW_1);
            ruleArray_Size();

            state._fsp--;

             after(grammarAccess.getArray_SizeRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleArray_Size"


    // $ANTLR start "ruleArray_Size"
    // InternalStructuredTextParser.g:1284:1: ruleArray_Size : ( RULE_UNSIGNED_INT ) ;
    public final void ruleArray_Size() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1288:2: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:1289:2: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:1289:2: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:1290:3: RULE_UNSIGNED_INT
            {
             before(grammarAccess.getArray_SizeAccess().getUNSIGNED_INTTerminalRuleCall()); 
            match(input,RULE_UNSIGNED_INT,FOLLOW_2); 
             after(grammarAccess.getArray_SizeAccess().getUNSIGNED_INTTerminalRuleCall()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleArray_Size"


    // $ANTLR start "entryRuleReal_Literal"
    // InternalStructuredTextParser.g:1300:1: entryRuleReal_Literal : ruleReal_Literal EOF ;
    public final void entryRuleReal_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1301:1: ( ruleReal_Literal EOF )
            // InternalStructuredTextParser.g:1302:1: ruleReal_Literal EOF
            {
             before(grammarAccess.getReal_LiteralRule()); 
            pushFollow(FOLLOW_1);
            ruleReal_Literal();

            state._fsp--;

             after(grammarAccess.getReal_LiteralRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleReal_Literal"


    // $ANTLR start "ruleReal_Literal"
    // InternalStructuredTextParser.g:1309:1: ruleReal_Literal : ( ( rule__Real_Literal__Group__0 ) ) ;
    public final void ruleReal_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1313:2: ( ( ( rule__Real_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1314:2: ( ( rule__Real_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1314:2: ( ( rule__Real_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1315:3: ( rule__Real_Literal__Group__0 )
            {
             before(grammarAccess.getReal_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1316:3: ( rule__Real_Literal__Group__0 )
            // InternalStructuredTextParser.g:1316:4: rule__Real_Literal__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Real_Literal__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getReal_LiteralAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleReal_Literal"


    // $ANTLR start "entryRuleReal_Value"
    // InternalStructuredTextParser.g:1325:1: entryRuleReal_Value : ruleReal_Value EOF ;
    public final void entryRuleReal_Value() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1326:1: ( ruleReal_Value EOF )
            // InternalStructuredTextParser.g:1327:1: ruleReal_Value EOF
            {
             before(grammarAccess.getReal_ValueRule()); 
            pushFollow(FOLLOW_1);
            ruleReal_Value();

            state._fsp--;

             after(grammarAccess.getReal_ValueRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleReal_Value"


    // $ANTLR start "ruleReal_Value"
    // InternalStructuredTextParser.g:1334:1: ruleReal_Value : ( ( rule__Real_Value__Group__0 ) ) ;
    public final void ruleReal_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1338:2: ( ( ( rule__Real_Value__Group__0 ) ) )
            // InternalStructuredTextParser.g:1339:2: ( ( rule__Real_Value__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1339:2: ( ( rule__Real_Value__Group__0 ) )
            // InternalStructuredTextParser.g:1340:3: ( rule__Real_Value__Group__0 )
            {
             before(grammarAccess.getReal_ValueAccess().getGroup()); 
            // InternalStructuredTextParser.g:1341:3: ( rule__Real_Value__Group__0 )
            // InternalStructuredTextParser.g:1341:4: rule__Real_Value__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Real_Value__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getReal_ValueAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleReal_Value"


    // $ANTLR start "entryRuleBool_Literal"
    // InternalStructuredTextParser.g:1350:1: entryRuleBool_Literal : ruleBool_Literal EOF ;
    public final void entryRuleBool_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1351:1: ( ruleBool_Literal EOF )
            // InternalStructuredTextParser.g:1352:1: ruleBool_Literal EOF
            {
             before(grammarAccess.getBool_LiteralRule()); 
            pushFollow(FOLLOW_1);
            ruleBool_Literal();

            state._fsp--;

             after(grammarAccess.getBool_LiteralRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBool_Literal"


    // $ANTLR start "ruleBool_Literal"
    // InternalStructuredTextParser.g:1359:1: ruleBool_Literal : ( ( rule__Bool_Literal__Group__0 ) ) ;
    public final void ruleBool_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1363:2: ( ( ( rule__Bool_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1364:2: ( ( rule__Bool_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1364:2: ( ( rule__Bool_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1365:3: ( rule__Bool_Literal__Group__0 )
            {
             before(grammarAccess.getBool_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1366:3: ( rule__Bool_Literal__Group__0 )
            // InternalStructuredTextParser.g:1366:4: rule__Bool_Literal__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Bool_Literal__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getBool_LiteralAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBool_Literal"


    // $ANTLR start "entryRuleBool_Value"
    // InternalStructuredTextParser.g:1375:1: entryRuleBool_Value : ruleBool_Value EOF ;
    public final void entryRuleBool_Value() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1376:1: ( ruleBool_Value EOF )
            // InternalStructuredTextParser.g:1377:1: ruleBool_Value EOF
            {
             before(grammarAccess.getBool_ValueRule()); 
            pushFollow(FOLLOW_1);
            ruleBool_Value();

            state._fsp--;

             after(grammarAccess.getBool_ValueRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleBool_Value"


    // $ANTLR start "ruleBool_Value"
    // InternalStructuredTextParser.g:1384:1: ruleBool_Value : ( ( rule__Bool_Value__Alternatives ) ) ;
    public final void ruleBool_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1388:2: ( ( ( rule__Bool_Value__Alternatives ) ) )
            // InternalStructuredTextParser.g:1389:2: ( ( rule__Bool_Value__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1389:2: ( ( rule__Bool_Value__Alternatives ) )
            // InternalStructuredTextParser.g:1390:3: ( rule__Bool_Value__Alternatives )
            {
             before(grammarAccess.getBool_ValueAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1391:3: ( rule__Bool_Value__Alternatives )
            // InternalStructuredTextParser.g:1391:4: rule__Bool_Value__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Bool_Value__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getBool_ValueAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBool_Value"


    // $ANTLR start "entryRuleChar_Literal"
    // InternalStructuredTextParser.g:1400:1: entryRuleChar_Literal : ruleChar_Literal EOF ;
    public final void entryRuleChar_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1401:1: ( ruleChar_Literal EOF )
            // InternalStructuredTextParser.g:1402:1: ruleChar_Literal EOF
            {
             before(grammarAccess.getChar_LiteralRule()); 
            pushFollow(FOLLOW_1);
            ruleChar_Literal();

            state._fsp--;

             after(grammarAccess.getChar_LiteralRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleChar_Literal"


    // $ANTLR start "ruleChar_Literal"
    // InternalStructuredTextParser.g:1409:1: ruleChar_Literal : ( ( rule__Char_Literal__Group__0 ) ) ;
    public final void ruleChar_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1413:2: ( ( ( rule__Char_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1414:2: ( ( rule__Char_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1414:2: ( ( rule__Char_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1415:3: ( rule__Char_Literal__Group__0 )
            {
             before(grammarAccess.getChar_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1416:3: ( rule__Char_Literal__Group__0 )
            // InternalStructuredTextParser.g:1416:4: rule__Char_Literal__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__Char_Literal__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getChar_LiteralAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleChar_Literal"


    // $ANTLR start "entryRuleTime_Literal"
    // InternalStructuredTextParser.g:1425:1: entryRuleTime_Literal : ruleTime_Literal EOF ;
    public final void entryRuleTime_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1426:1: ( ruleTime_Literal EOF )
            // InternalStructuredTextParser.g:1427:1: ruleTime_Literal EOF
            {
             before(grammarAccess.getTime_LiteralRule()); 
            pushFollow(FOLLOW_1);
            ruleTime_Literal();

            state._fsp--;

             after(grammarAccess.getTime_LiteralRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleTime_Literal"


    // $ANTLR start "ruleTime_Literal"
    // InternalStructuredTextParser.g:1434:1: ruleTime_Literal : ( ( rule__Time_Literal__LiteralAssignment ) ) ;
    public final void ruleTime_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1438:2: ( ( ( rule__Time_Literal__LiteralAssignment ) ) )
            // InternalStructuredTextParser.g:1439:2: ( ( rule__Time_Literal__LiteralAssignment ) )
            {
            // InternalStructuredTextParser.g:1439:2: ( ( rule__Time_Literal__LiteralAssignment ) )
            // InternalStructuredTextParser.g:1440:3: ( rule__Time_Literal__LiteralAssignment )
            {
             before(grammarAccess.getTime_LiteralAccess().getLiteralAssignment()); 
            // InternalStructuredTextParser.g:1441:3: ( rule__Time_Literal__LiteralAssignment )
            // InternalStructuredTextParser.g:1441:4: rule__Time_Literal__LiteralAssignment
            {
            pushFollow(FOLLOW_2);
            rule__Time_Literal__LiteralAssignment();

            state._fsp--;


            }

             after(grammarAccess.getTime_LiteralAccess().getLiteralAssignment()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTime_Literal"


    // $ANTLR start "entryRuleType_Name"
    // InternalStructuredTextParser.g:1450:1: entryRuleType_Name : ruleType_Name EOF ;
    public final void entryRuleType_Name() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1451:1: ( ruleType_Name EOF )
            // InternalStructuredTextParser.g:1452:1: ruleType_Name EOF
            {
             before(grammarAccess.getType_NameRule()); 
            pushFollow(FOLLOW_1);
            ruleType_Name();

            state._fsp--;

             after(grammarAccess.getType_NameRule()); 
            match(input,EOF,FOLLOW_2); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {
        }
        return ;
    }
    // $ANTLR end "entryRuleType_Name"


    // $ANTLR start "ruleType_Name"
    // InternalStructuredTextParser.g:1459:1: ruleType_Name : ( ( rule__Type_Name__Alternatives ) ) ;
    public final void ruleType_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1463:2: ( ( ( rule__Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1464:2: ( ( rule__Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1464:2: ( ( rule__Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1465:3: ( rule__Type_Name__Alternatives )
            {
             before(grammarAccess.getType_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1466:3: ( rule__Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1466:4: rule__Type_Name__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Type_Name__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getType_NameAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleType_Name"


    // $ANTLR start "ruleOr_Operator"
    // InternalStructuredTextParser.g:1475:1: ruleOr_Operator : ( ( OR ) ) ;
    public final void ruleOr_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1479:1: ( ( ( OR ) ) )
            // InternalStructuredTextParser.g:1480:2: ( ( OR ) )
            {
            // InternalStructuredTextParser.g:1480:2: ( ( OR ) )
            // InternalStructuredTextParser.g:1481:3: ( OR )
            {
             before(grammarAccess.getOr_OperatorAccess().getOREnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1482:3: ( OR )
            // InternalStructuredTextParser.g:1482:4: OR
            {
            match(input,OR,FOLLOW_2); 

            }

             after(grammarAccess.getOr_OperatorAccess().getOREnumLiteralDeclaration()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleOr_Operator"


    // $ANTLR start "ruleXor_Operator"
    // InternalStructuredTextParser.g:1491:1: ruleXor_Operator : ( ( XOR ) ) ;
    public final void ruleXor_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1495:1: ( ( ( XOR ) ) )
            // InternalStructuredTextParser.g:1496:2: ( ( XOR ) )
            {
            // InternalStructuredTextParser.g:1496:2: ( ( XOR ) )
            // InternalStructuredTextParser.g:1497:3: ( XOR )
            {
             before(grammarAccess.getXor_OperatorAccess().getXOREnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1498:3: ( XOR )
            // InternalStructuredTextParser.g:1498:4: XOR
            {
            match(input,XOR,FOLLOW_2); 

            }

             after(grammarAccess.getXor_OperatorAccess().getXOREnumLiteralDeclaration()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleXor_Operator"


    // $ANTLR start "ruleAnd_Operator"
    // InternalStructuredTextParser.g:1507:1: ruleAnd_Operator : ( ( rule__And_Operator__Alternatives ) ) ;
    public final void ruleAnd_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1511:1: ( ( ( rule__And_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1512:2: ( ( rule__And_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1512:2: ( ( rule__And_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1513:3: ( rule__And_Operator__Alternatives )
            {
             before(grammarAccess.getAnd_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1514:3: ( rule__And_Operator__Alternatives )
            // InternalStructuredTextParser.g:1514:4: rule__And_Operator__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__And_Operator__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getAnd_OperatorAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAnd_Operator"


    // $ANTLR start "ruleCompare_Operator"
    // InternalStructuredTextParser.g:1523:1: ruleCompare_Operator : ( ( rule__Compare_Operator__Alternatives ) ) ;
    public final void ruleCompare_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1527:1: ( ( ( rule__Compare_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1528:2: ( ( rule__Compare_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1528:2: ( ( rule__Compare_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1529:3: ( rule__Compare_Operator__Alternatives )
            {
             before(grammarAccess.getCompare_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1530:3: ( rule__Compare_Operator__Alternatives )
            // InternalStructuredTextParser.g:1530:4: rule__Compare_Operator__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Compare_Operator__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getCompare_OperatorAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleCompare_Operator"


    // $ANTLR start "ruleEqu_Operator"
    // InternalStructuredTextParser.g:1539:1: ruleEqu_Operator : ( ( rule__Equ_Operator__Alternatives ) ) ;
    public final void ruleEqu_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1543:1: ( ( ( rule__Equ_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1544:2: ( ( rule__Equ_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1544:2: ( ( rule__Equ_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1545:3: ( rule__Equ_Operator__Alternatives )
            {
             before(grammarAccess.getEqu_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1546:3: ( rule__Equ_Operator__Alternatives )
            // InternalStructuredTextParser.g:1546:4: rule__Equ_Operator__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Equ_Operator__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getEqu_OperatorAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleEqu_Operator"


    // $ANTLR start "ruleAdd_Operator"
    // InternalStructuredTextParser.g:1555:1: ruleAdd_Operator : ( ( rule__Add_Operator__Alternatives ) ) ;
    public final void ruleAdd_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1559:1: ( ( ( rule__Add_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1560:2: ( ( rule__Add_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1560:2: ( ( rule__Add_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1561:3: ( rule__Add_Operator__Alternatives )
            {
             before(grammarAccess.getAdd_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1562:3: ( rule__Add_Operator__Alternatives )
            // InternalStructuredTextParser.g:1562:4: rule__Add_Operator__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Add_Operator__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getAdd_OperatorAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleAdd_Operator"


    // $ANTLR start "ruleTerm_Operator"
    // InternalStructuredTextParser.g:1571:1: ruleTerm_Operator : ( ( rule__Term_Operator__Alternatives ) ) ;
    public final void ruleTerm_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1575:1: ( ( ( rule__Term_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1576:2: ( ( rule__Term_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1576:2: ( ( rule__Term_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1577:3: ( rule__Term_Operator__Alternatives )
            {
             before(grammarAccess.getTerm_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1578:3: ( rule__Term_Operator__Alternatives )
            // InternalStructuredTextParser.g:1578:4: rule__Term_Operator__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Term_Operator__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getTerm_OperatorAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleTerm_Operator"


    // $ANTLR start "rulePower_Operator"
    // InternalStructuredTextParser.g:1587:1: rulePower_Operator : ( ( AsteriskAsterisk ) ) ;
    public final void rulePower_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1591:1: ( ( ( AsteriskAsterisk ) ) )
            // InternalStructuredTextParser.g:1592:2: ( ( AsteriskAsterisk ) )
            {
            // InternalStructuredTextParser.g:1592:2: ( ( AsteriskAsterisk ) )
            // InternalStructuredTextParser.g:1593:3: ( AsteriskAsterisk )
            {
             before(grammarAccess.getPower_OperatorAccess().getPOWEREnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1594:3: ( AsteriskAsterisk )
            // InternalStructuredTextParser.g:1594:4: AsteriskAsterisk
            {
            match(input,AsteriskAsterisk,FOLLOW_2); 

            }

             after(grammarAccess.getPower_OperatorAccess().getPOWEREnumLiteralDeclaration()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rulePower_Operator"


    // $ANTLR start "ruleUnary_Operator"
    // InternalStructuredTextParser.g:1603:1: ruleUnary_Operator : ( ( rule__Unary_Operator__Alternatives ) ) ;
    public final void ruleUnary_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1607:1: ( ( ( rule__Unary_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1608:2: ( ( rule__Unary_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1608:2: ( ( rule__Unary_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1609:3: ( rule__Unary_Operator__Alternatives )
            {
             before(grammarAccess.getUnary_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1610:3: ( rule__Unary_Operator__Alternatives )
            // InternalStructuredTextParser.g:1610:4: rule__Unary_Operator__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Unary_Operator__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getUnary_OperatorAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleUnary_Operator"


    // $ANTLR start "ruleInt_Type_Name"
    // InternalStructuredTextParser.g:1619:1: ruleInt_Type_Name : ( ( rule__Int_Type_Name__Alternatives ) ) ;
    public final void ruleInt_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1623:1: ( ( ( rule__Int_Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1624:2: ( ( rule__Int_Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1624:2: ( ( rule__Int_Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1625:3: ( rule__Int_Type_Name__Alternatives )
            {
             before(grammarAccess.getInt_Type_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1626:3: ( rule__Int_Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1626:4: rule__Int_Type_Name__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Int_Type_Name__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getInt_Type_NameAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleInt_Type_Name"


    // $ANTLR start "ruleReal_Type_Name"
    // InternalStructuredTextParser.g:1635:1: ruleReal_Type_Name : ( ( rule__Real_Type_Name__Alternatives ) ) ;
    public final void ruleReal_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1639:1: ( ( ( rule__Real_Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1640:2: ( ( rule__Real_Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1640:2: ( ( rule__Real_Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1641:3: ( rule__Real_Type_Name__Alternatives )
            {
             before(grammarAccess.getReal_Type_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1642:3: ( rule__Real_Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1642:4: rule__Real_Type_Name__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__Real_Type_Name__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getReal_Type_NameAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleReal_Type_Name"


    // $ANTLR start "ruleString_Type_Name"
    // InternalStructuredTextParser.g:1651:1: ruleString_Type_Name : ( ( rule__String_Type_Name__Alternatives ) ) ;
    public final void ruleString_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1655:1: ( ( ( rule__String_Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1656:2: ( ( rule__String_Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1656:2: ( ( rule__String_Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1657:3: ( rule__String_Type_Name__Alternatives )
            {
             before(grammarAccess.getString_Type_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1658:3: ( rule__String_Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1658:4: rule__String_Type_Name__Alternatives
            {
            pushFollow(FOLLOW_2);
            rule__String_Type_Name__Alternatives();

            state._fsp--;


            }

             after(grammarAccess.getString_Type_NameAccess().getAlternatives()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleString_Type_Name"


    // $ANTLR start "ruleBool_Type_Name"
    // InternalStructuredTextParser.g:1667:1: ruleBool_Type_Name : ( ( BOOL ) ) ;
    public final void ruleBool_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1671:1: ( ( ( BOOL ) ) )
            // InternalStructuredTextParser.g:1672:2: ( ( BOOL ) )
            {
            // InternalStructuredTextParser.g:1672:2: ( ( BOOL ) )
            // InternalStructuredTextParser.g:1673:3: ( BOOL )
            {
             before(grammarAccess.getBool_Type_NameAccess().getBOOLEnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1674:3: ( BOOL )
            // InternalStructuredTextParser.g:1674:4: BOOL
            {
            match(input,BOOL,FOLLOW_2); 

            }

             after(grammarAccess.getBool_Type_NameAccess().getBOOLEnumLiteralDeclaration()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleBool_Type_Name"


    // $ANTLR start "rule__Stmt__Alternatives"
    // InternalStructuredTextParser.g:1682:1: rule__Stmt__Alternatives : ( ( ruleAssign_Stmt ) | ( ruleSubprog_Ctrl_Stmt ) | ( ruleSelection_Stmt ) | ( ruleIteration_Stmt ) );
    public final void rule__Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1686:1: ( ( ruleAssign_Stmt ) | ( ruleSubprog_Ctrl_Stmt ) | ( ruleSelection_Stmt ) | ( ruleIteration_Stmt ) )
            int alt1=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA1_1 = input.LA(2);

                if ( ((LA1_1>=B && LA1_1<=X)||LA1_1==ColonEqualsSign||LA1_1==FullStop||LA1_1==LeftSquareBracket) ) {
                    alt1=1;
                }
                else if ( (LA1_1==LeftParenthesis) ) {
                    alt1=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 1, 1, input);

                    throw nvae;
                }
                }
                break;
            case DT:
            case LT:
            case T:
                {
                alt1=1;
                }
                break;
            case RETURN:
            case SUPER:
            case TIME:
                {
                alt1=2;
                }
                break;
            case CASE:
            case IF:
                {
                alt1=3;
                }
                break;
            case CONTINUE:
            case REPEAT:
            case WHILE:
            case EXIT:
            case FOR:
                {
                alt1=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // InternalStructuredTextParser.g:1687:2: ( ruleAssign_Stmt )
                    {
                    // InternalStructuredTextParser.g:1687:2: ( ruleAssign_Stmt )
                    // InternalStructuredTextParser.g:1688:3: ruleAssign_Stmt
                    {
                     before(grammarAccess.getStmtAccess().getAssign_StmtParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleAssign_Stmt();

                    state._fsp--;

                     after(grammarAccess.getStmtAccess().getAssign_StmtParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1693:2: ( ruleSubprog_Ctrl_Stmt )
                    {
                    // InternalStructuredTextParser.g:1693:2: ( ruleSubprog_Ctrl_Stmt )
                    // InternalStructuredTextParser.g:1694:3: ruleSubprog_Ctrl_Stmt
                    {
                     before(grammarAccess.getStmtAccess().getSubprog_Ctrl_StmtParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleSubprog_Ctrl_Stmt();

                    state._fsp--;

                     after(grammarAccess.getStmtAccess().getSubprog_Ctrl_StmtParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1699:2: ( ruleSelection_Stmt )
                    {
                    // InternalStructuredTextParser.g:1699:2: ( ruleSelection_Stmt )
                    // InternalStructuredTextParser.g:1700:3: ruleSelection_Stmt
                    {
                     before(grammarAccess.getStmtAccess().getSelection_StmtParserRuleCall_2()); 
                    pushFollow(FOLLOW_2);
                    ruleSelection_Stmt();

                    state._fsp--;

                     after(grammarAccess.getStmtAccess().getSelection_StmtParserRuleCall_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:1705:2: ( ruleIteration_Stmt )
                    {
                    // InternalStructuredTextParser.g:1705:2: ( ruleIteration_Stmt )
                    // InternalStructuredTextParser.g:1706:3: ruleIteration_Stmt
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmt__Alternatives"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Alternatives"
    // InternalStructuredTextParser.g:1715:1: rule__Subprog_Ctrl_Stmt__Alternatives : ( ( ruleFunc_Call ) | ( ( rule__Subprog_Ctrl_Stmt__Group_1__0 ) ) | ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) ) );
    public final void rule__Subprog_Ctrl_Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1719:1: ( ( ruleFunc_Call ) | ( ( rule__Subprog_Ctrl_Stmt__Group_1__0 ) ) | ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) ) )
            int alt2=3;
            switch ( input.LA(1) ) {
            case TIME:
            case RULE_ID:
                {
                alt2=1;
                }
                break;
            case SUPER:
                {
                alt2=2;
                }
                break;
            case RETURN:
                {
                alt2=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // InternalStructuredTextParser.g:1720:2: ( ruleFunc_Call )
                    {
                    // InternalStructuredTextParser.g:1720:2: ( ruleFunc_Call )
                    // InternalStructuredTextParser.g:1721:3: ruleFunc_Call
                    {
                     before(grammarAccess.getSubprog_Ctrl_StmtAccess().getFunc_CallParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleFunc_Call();

                    state._fsp--;

                     after(grammarAccess.getSubprog_Ctrl_StmtAccess().getFunc_CallParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1726:2: ( ( rule__Subprog_Ctrl_Stmt__Group_1__0 ) )
                    {
                    // InternalStructuredTextParser.g:1726:2: ( ( rule__Subprog_Ctrl_Stmt__Group_1__0 ) )
                    // InternalStructuredTextParser.g:1727:3: ( rule__Subprog_Ctrl_Stmt__Group_1__0 )
                    {
                     before(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_1()); 
                    // InternalStructuredTextParser.g:1728:3: ( rule__Subprog_Ctrl_Stmt__Group_1__0 )
                    // InternalStructuredTextParser.g:1728:4: rule__Subprog_Ctrl_Stmt__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Subprog_Ctrl_Stmt__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1732:2: ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) )
                    {
                    // InternalStructuredTextParser.g:1732:2: ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) )
                    // InternalStructuredTextParser.g:1733:3: ( rule__Subprog_Ctrl_Stmt__Group_2__0 )
                    {
                     before(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_2()); 
                    // InternalStructuredTextParser.g:1734:3: ( rule__Subprog_Ctrl_Stmt__Group_2__0 )
                    // InternalStructuredTextParser.g:1734:4: rule__Subprog_Ctrl_Stmt__Group_2__0
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Alternatives"


    // $ANTLR start "rule__Selection_Stmt__Alternatives"
    // InternalStructuredTextParser.g:1742:1: rule__Selection_Stmt__Alternatives : ( ( ruleIF_Stmt ) | ( ruleCase_Stmt ) );
    public final void rule__Selection_Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1746:1: ( ( ruleIF_Stmt ) | ( ruleCase_Stmt ) )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==IF) ) {
                alt3=1;
            }
            else if ( (LA3_0==CASE) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalStructuredTextParser.g:1747:2: ( ruleIF_Stmt )
                    {
                    // InternalStructuredTextParser.g:1747:2: ( ruleIF_Stmt )
                    // InternalStructuredTextParser.g:1748:3: ruleIF_Stmt
                    {
                     before(grammarAccess.getSelection_StmtAccess().getIF_StmtParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleIF_Stmt();

                    state._fsp--;

                     after(grammarAccess.getSelection_StmtAccess().getIF_StmtParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1753:2: ( ruleCase_Stmt )
                    {
                    // InternalStructuredTextParser.g:1753:2: ( ruleCase_Stmt )
                    // InternalStructuredTextParser.g:1754:3: ruleCase_Stmt
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Selection_Stmt__Alternatives"


    // $ANTLR start "rule__Iteration_Stmt__Alternatives"
    // InternalStructuredTextParser.g:1763:1: rule__Iteration_Stmt__Alternatives : ( ( ruleFor_Stmt ) | ( ruleWhile_Stmt ) | ( ruleRepeat_Stmt ) | ( ( rule__Iteration_Stmt__Group_3__0 ) ) | ( ( rule__Iteration_Stmt__Group_4__0 ) ) );
    public final void rule__Iteration_Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1767:1: ( ( ruleFor_Stmt ) | ( ruleWhile_Stmt ) | ( ruleRepeat_Stmt ) | ( ( rule__Iteration_Stmt__Group_3__0 ) ) | ( ( rule__Iteration_Stmt__Group_4__0 ) ) )
            int alt4=5;
            switch ( input.LA(1) ) {
            case FOR:
                {
                alt4=1;
                }
                break;
            case WHILE:
                {
                alt4=2;
                }
                break;
            case REPEAT:
                {
                alt4=3;
                }
                break;
            case EXIT:
                {
                alt4=4;
                }
                break;
            case CONTINUE:
                {
                alt4=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // InternalStructuredTextParser.g:1768:2: ( ruleFor_Stmt )
                    {
                    // InternalStructuredTextParser.g:1768:2: ( ruleFor_Stmt )
                    // InternalStructuredTextParser.g:1769:3: ruleFor_Stmt
                    {
                     before(grammarAccess.getIteration_StmtAccess().getFor_StmtParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleFor_Stmt();

                    state._fsp--;

                     after(grammarAccess.getIteration_StmtAccess().getFor_StmtParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1774:2: ( ruleWhile_Stmt )
                    {
                    // InternalStructuredTextParser.g:1774:2: ( ruleWhile_Stmt )
                    // InternalStructuredTextParser.g:1775:3: ruleWhile_Stmt
                    {
                     before(grammarAccess.getIteration_StmtAccess().getWhile_StmtParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleWhile_Stmt();

                    state._fsp--;

                     after(grammarAccess.getIteration_StmtAccess().getWhile_StmtParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1780:2: ( ruleRepeat_Stmt )
                    {
                    // InternalStructuredTextParser.g:1780:2: ( ruleRepeat_Stmt )
                    // InternalStructuredTextParser.g:1781:3: ruleRepeat_Stmt
                    {
                     before(grammarAccess.getIteration_StmtAccess().getRepeat_StmtParserRuleCall_2()); 
                    pushFollow(FOLLOW_2);
                    ruleRepeat_Stmt();

                    state._fsp--;

                     after(grammarAccess.getIteration_StmtAccess().getRepeat_StmtParserRuleCall_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:1786:2: ( ( rule__Iteration_Stmt__Group_3__0 ) )
                    {
                    // InternalStructuredTextParser.g:1786:2: ( ( rule__Iteration_Stmt__Group_3__0 ) )
                    // InternalStructuredTextParser.g:1787:3: ( rule__Iteration_Stmt__Group_3__0 )
                    {
                     before(grammarAccess.getIteration_StmtAccess().getGroup_3()); 
                    // InternalStructuredTextParser.g:1788:3: ( rule__Iteration_Stmt__Group_3__0 )
                    // InternalStructuredTextParser.g:1788:4: rule__Iteration_Stmt__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Iteration_Stmt__Group_3__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getIteration_StmtAccess().getGroup_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:1792:2: ( ( rule__Iteration_Stmt__Group_4__0 ) )
                    {
                    // InternalStructuredTextParser.g:1792:2: ( ( rule__Iteration_Stmt__Group_4__0 ) )
                    // InternalStructuredTextParser.g:1793:3: ( rule__Iteration_Stmt__Group_4__0 )
                    {
                     before(grammarAccess.getIteration_StmtAccess().getGroup_4()); 
                    // InternalStructuredTextParser.g:1794:3: ( rule__Iteration_Stmt__Group_4__0 )
                    // InternalStructuredTextParser.g:1794:4: rule__Iteration_Stmt__Group_4__0
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Iteration_Stmt__Alternatives"


    // $ANTLR start "rule__Unary_Expr__Alternatives"
    // InternalStructuredTextParser.g:1802:1: rule__Unary_Expr__Alternatives : ( ( ( rule__Unary_Expr__Group_0__0 ) ) | ( rulePrimary_Expr ) | ( ruleConstant ) );
    public final void rule__Unary_Expr__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1806:1: ( ( ( rule__Unary_Expr__Group_0__0 ) ) | ( rulePrimary_Expr ) | ( ruleConstant ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==TIME||LA5_1==DT||LA5_1==LT||LA5_1==LeftParenthesis||LA5_1==T||LA5_1==RULE_ID) ) {
                    alt5=1;
                }
                else if ( (LA5_1==RULE_UNSIGNED_INT) ) {
                    alt5=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
                }
                break;
            case PlusSign:
                {
                int LA5_2 = input.LA(2);

                if ( (LA5_2==RULE_UNSIGNED_INT) ) {
                    alt5=3;
                }
                else if ( (LA5_2==TIME||LA5_2==DT||LA5_2==LT||LA5_2==LeftParenthesis||LA5_2==T||LA5_2==RULE_ID) ) {
                    alt5=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 2, input);

                    throw nvae;
                }
                }
                break;
            case NOT:
                {
                alt5=1;
                }
                break;
            case TIME:
            case DT:
            case LT:
            case LeftParenthesis:
            case T:
            case RULE_ID:
                {
                alt5=2;
                }
                break;
            case WSTRING:
            case STRING:
            case FALSE:
            case LREAL:
            case UDINT:
            case ULINT:
            case USINT:
            case WCHAR:
            case BOOL:
            case CHAR:
            case DINT:
            case LINT:
            case REAL:
            case SINT:
            case TRUE:
            case UINT:
            case INT:
            case RULE_UNSIGNED_INT:
            case RULE_TIMEOFDAY:
            case RULE_TIME:
            case RULE_DATETIME:
            case RULE_DATE:
            case RULE_BINARY_INT:
            case RULE_OCTAL_INT:
            case RULE_HEX_INT:
            case RULE_S_BYTE_CHAR_STR:
            case RULE_D_BYTE_CHAR_STR:
                {
                alt5=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // InternalStructuredTextParser.g:1807:2: ( ( rule__Unary_Expr__Group_0__0 ) )
                    {
                    // InternalStructuredTextParser.g:1807:2: ( ( rule__Unary_Expr__Group_0__0 ) )
                    // InternalStructuredTextParser.g:1808:3: ( rule__Unary_Expr__Group_0__0 )
                    {
                     before(grammarAccess.getUnary_ExprAccess().getGroup_0()); 
                    // InternalStructuredTextParser.g:1809:3: ( rule__Unary_Expr__Group_0__0 )
                    // InternalStructuredTextParser.g:1809:4: rule__Unary_Expr__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Unary_Expr__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getUnary_ExprAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1813:2: ( rulePrimary_Expr )
                    {
                    // InternalStructuredTextParser.g:1813:2: ( rulePrimary_Expr )
                    // InternalStructuredTextParser.g:1814:3: rulePrimary_Expr
                    {
                     before(grammarAccess.getUnary_ExprAccess().getPrimary_ExprParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    rulePrimary_Expr();

                    state._fsp--;

                     after(grammarAccess.getUnary_ExprAccess().getPrimary_ExprParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1819:2: ( ruleConstant )
                    {
                    // InternalStructuredTextParser.g:1819:2: ( ruleConstant )
                    // InternalStructuredTextParser.g:1820:3: ruleConstant
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Unary_Expr__Alternatives"


    // $ANTLR start "rule__Primary_Expr__Alternatives"
    // InternalStructuredTextParser.g:1829:1: rule__Primary_Expr__Alternatives : ( ( ruleVariable ) | ( ruleFunc_Call ) | ( ( rule__Primary_Expr__Group_2__0 ) ) );
    public final void rule__Primary_Expr__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1833:1: ( ( ruleVariable ) | ( ruleFunc_Call ) | ( ( rule__Primary_Expr__Group_2__0 ) ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==EOF||LA6_1==END_REPEAT||LA6_1==THEN||(LA6_1>=B && LA6_1<=AND)||LA6_1==MOD||(LA6_1>=XOR && LA6_1<=AsteriskAsterisk)||(LA6_1>=LessThanSignEqualsSign && LA6_1<=LessThanSignGreaterThanSign)||LA6_1==GreaterThanSignEqualsSign||(LA6_1>=BY && LA6_1<=DO)||(LA6_1>=OF && LA6_1<=TO)||LA6_1==Ampersand||(LA6_1>=RightParenthesis && LA6_1<=Solidus)||(LA6_1>=Semicolon && LA6_1<=GreaterThanSign)||(LA6_1>=LeftSquareBracket && LA6_1<=RightSquareBracket)) ) {
                    alt6=1;
                }
                else if ( (LA6_1==LeftParenthesis) ) {
                    alt6=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 6, 1, input);

                    throw nvae;
                }
                }
                break;
            case DT:
            case LT:
            case T:
                {
                alt6=1;
                }
                break;
            case TIME:
                {
                alt6=2;
                }
                break;
            case LeftParenthesis:
                {
                alt6=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // InternalStructuredTextParser.g:1834:2: ( ruleVariable )
                    {
                    // InternalStructuredTextParser.g:1834:2: ( ruleVariable )
                    // InternalStructuredTextParser.g:1835:3: ruleVariable
                    {
                     before(grammarAccess.getPrimary_ExprAccess().getVariableParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleVariable();

                    state._fsp--;

                     after(grammarAccess.getPrimary_ExprAccess().getVariableParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1840:2: ( ruleFunc_Call )
                    {
                    // InternalStructuredTextParser.g:1840:2: ( ruleFunc_Call )
                    // InternalStructuredTextParser.g:1841:3: ruleFunc_Call
                    {
                     before(grammarAccess.getPrimary_ExprAccess().getFunc_CallParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleFunc_Call();

                    state._fsp--;

                     after(grammarAccess.getPrimary_ExprAccess().getFunc_CallParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1846:2: ( ( rule__Primary_Expr__Group_2__0 ) )
                    {
                    // InternalStructuredTextParser.g:1846:2: ( ( rule__Primary_Expr__Group_2__0 ) )
                    // InternalStructuredTextParser.g:1847:3: ( rule__Primary_Expr__Group_2__0 )
                    {
                     before(grammarAccess.getPrimary_ExprAccess().getGroup_2()); 
                    // InternalStructuredTextParser.g:1848:3: ( rule__Primary_Expr__Group_2__0 )
                    // InternalStructuredTextParser.g:1848:4: rule__Primary_Expr__Group_2__0
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary_Expr__Alternatives"


    // $ANTLR start "rule__Func_Call__FuncAlternatives_0_0"
    // InternalStructuredTextParser.g:1856:1: rule__Func_Call__FuncAlternatives_0_0 : ( ( RULE_ID ) | ( TIME ) );
    public final void rule__Func_Call__FuncAlternatives_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1860:1: ( ( RULE_ID ) | ( TIME ) )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==RULE_ID) ) {
                alt7=1;
            }
            else if ( (LA7_0==TIME) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // InternalStructuredTextParser.g:1861:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:1861:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:1862:3: RULE_ID
                    {
                     before(grammarAccess.getFunc_CallAccess().getFuncIDTerminalRuleCall_0_0_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getFunc_CallAccess().getFuncIDTerminalRuleCall_0_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1867:2: ( TIME )
                    {
                    // InternalStructuredTextParser.g:1867:2: ( TIME )
                    // InternalStructuredTextParser.g:1868:3: TIME
                    {
                     before(grammarAccess.getFunc_CallAccess().getFuncTIMEKeyword_0_0_1()); 
                    match(input,TIME,FOLLOW_2); 
                     after(grammarAccess.getFunc_CallAccess().getFuncTIMEKeyword_0_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__FuncAlternatives_0_0"


    // $ANTLR start "rule__Param_Assign__Alternatives"
    // InternalStructuredTextParser.g:1877:1: rule__Param_Assign__Alternatives : ( ( ruleParam_Assign_In ) | ( ruleParam_Assign_Out ) );
    public final void rule__Param_Assign__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1881:1: ( ( ruleParam_Assign_In ) | ( ruleParam_Assign_Out ) )
            int alt8=2;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==EOF||(LA8_1>=B && LA8_1<=AND)||LA8_1==MOD||(LA8_1>=XOR && LA8_1<=AsteriskAsterisk)||(LA8_1>=ColonEqualsSign && LA8_1<=LessThanSignGreaterThanSign)||LA8_1==GreaterThanSignEqualsSign||LA8_1==OR||(LA8_1>=Ampersand && LA8_1<=Solidus)||(LA8_1>=LessThanSign && LA8_1<=GreaterThanSign)||LA8_1==LeftSquareBracket) ) {
                    alt8=1;
                }
                else if ( (LA8_1==EqualsSignGreaterThanSign) ) {
                    alt8=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
                }
                break;
            case WSTRING:
            case STRING:
            case FALSE:
            case LREAL:
            case UDINT:
            case ULINT:
            case USINT:
            case WCHAR:
            case BOOL:
            case CHAR:
            case DINT:
            case LINT:
            case REAL:
            case SINT:
            case TIME:
            case TRUE:
            case UINT:
            case INT:
            case DT:
            case LT:
            case LeftParenthesis:
            case PlusSign:
            case HyphenMinus:
            case T:
            case RULE_UNSIGNED_INT:
            case RULE_TIMEOFDAY:
            case RULE_TIME:
            case RULE_DATETIME:
            case RULE_DATE:
            case RULE_BINARY_INT:
            case RULE_OCTAL_INT:
            case RULE_HEX_INT:
            case RULE_S_BYTE_CHAR_STR:
            case RULE_D_BYTE_CHAR_STR:
                {
                alt8=1;
                }
                break;
            case NOT:
                {
                int LA8_3 = input.LA(2);

                if ( (LA8_3==RULE_ID) ) {
                    int LA8_5 = input.LA(3);

                    if ( (LA8_5==EOF||(LA8_5>=B && LA8_5<=AND)||LA8_5==MOD||(LA8_5>=XOR && LA8_5<=AsteriskAsterisk)||(LA8_5>=LessThanSignEqualsSign && LA8_5<=LessThanSignGreaterThanSign)||LA8_5==GreaterThanSignEqualsSign||LA8_5==OR||(LA8_5>=Ampersand && LA8_5<=Solidus)||(LA8_5>=LessThanSign && LA8_5<=GreaterThanSign)||LA8_5==LeftSquareBracket) ) {
                        alt8=1;
                    }
                    else if ( (LA8_5==EqualsSignGreaterThanSign) ) {
                        alt8=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 8, 5, input);

                        throw nvae;
                    }
                }
                else if ( (LA8_3==TIME||LA8_3==DT||LA8_3==LT||LA8_3==LeftParenthesis||LA8_3==T) ) {
                    alt8=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 3, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // InternalStructuredTextParser.g:1882:2: ( ruleParam_Assign_In )
                    {
                    // InternalStructuredTextParser.g:1882:2: ( ruleParam_Assign_In )
                    // InternalStructuredTextParser.g:1883:3: ruleParam_Assign_In
                    {
                     before(grammarAccess.getParam_AssignAccess().getParam_Assign_InParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleParam_Assign_In();

                    state._fsp--;

                     after(grammarAccess.getParam_AssignAccess().getParam_Assign_InParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1888:2: ( ruleParam_Assign_Out )
                    {
                    // InternalStructuredTextParser.g:1888:2: ( ruleParam_Assign_Out )
                    // InternalStructuredTextParser.g:1889:3: ruleParam_Assign_Out
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign__Alternatives"


    // $ANTLR start "rule__Variable_Subscript__Alternatives_0"
    // InternalStructuredTextParser.g:1898:1: rule__Variable_Subscript__Alternatives_0 : ( ( ruleVariable_Primary ) | ( ruleVariable_Adapter ) );
    public final void rule__Variable_Subscript__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1902:1: ( ( ruleVariable_Primary ) | ( ruleVariable_Adapter ) )
            int alt9=2;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA9_1 = input.LA(2);

                if ( (LA9_1==FullStop) ) {
                    int LA9_5 = input.LA(3);

                    if ( (LA9_5==RULE_UNSIGNED_INT) ) {
                        alt9=1;
                    }
                    else if ( (LA9_5==DT||LA9_5==LT||LA9_5==T||LA9_5==RULE_ID) ) {
                        alt9=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 5, input);

                        throw nvae;
                    }
                }
                else if ( (LA9_1==EOF||LA9_1==END_REPEAT||LA9_1==THEN||(LA9_1>=B && LA9_1<=AND)||LA9_1==MOD||(LA9_1>=XOR && LA9_1<=AsteriskAsterisk)||(LA9_1>=ColonEqualsSign && LA9_1<=LessThanSignGreaterThanSign)||LA9_1==GreaterThanSignEqualsSign||(LA9_1>=BY && LA9_1<=DO)||(LA9_1>=OF && LA9_1<=TO)||LA9_1==Ampersand||(LA9_1>=RightParenthesis && LA9_1<=HyphenMinus)||(LA9_1>=Solidus && LA9_1<=GreaterThanSign)||(LA9_1>=LeftSquareBracket && LA9_1<=RightSquareBracket)) ) {
                    alt9=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;
                }
                }
                break;
            case T:
                {
                int LA9_2 = input.LA(2);

                if ( (LA9_2==FullStop) ) {
                    int LA9_5 = input.LA(3);

                    if ( (LA9_5==RULE_UNSIGNED_INT) ) {
                        alt9=1;
                    }
                    else if ( (LA9_5==DT||LA9_5==LT||LA9_5==T||LA9_5==RULE_ID) ) {
                        alt9=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 5, input);

                        throw nvae;
                    }
                }
                else if ( (LA9_2==EOF||LA9_2==END_REPEAT||LA9_2==THEN||(LA9_2>=B && LA9_2<=AND)||LA9_2==MOD||(LA9_2>=XOR && LA9_2<=AsteriskAsterisk)||(LA9_2>=ColonEqualsSign && LA9_2<=LessThanSignGreaterThanSign)||LA9_2==GreaterThanSignEqualsSign||(LA9_2>=BY && LA9_2<=DO)||(LA9_2>=OF && LA9_2<=TO)||LA9_2==Ampersand||(LA9_2>=RightParenthesis && LA9_2<=HyphenMinus)||(LA9_2>=Solidus && LA9_2<=GreaterThanSign)||(LA9_2>=LeftSquareBracket && LA9_2<=RightSquareBracket)) ) {
                    alt9=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 2, input);

                    throw nvae;
                }
                }
                break;
            case LT:
                {
                int LA9_3 = input.LA(2);

                if ( (LA9_3==FullStop) ) {
                    int LA9_5 = input.LA(3);

                    if ( (LA9_5==RULE_UNSIGNED_INT) ) {
                        alt9=1;
                    }
                    else if ( (LA9_5==DT||LA9_5==LT||LA9_5==T||LA9_5==RULE_ID) ) {
                        alt9=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 5, input);

                        throw nvae;
                    }
                }
                else if ( (LA9_3==EOF||LA9_3==END_REPEAT||LA9_3==THEN||(LA9_3>=B && LA9_3<=AND)||LA9_3==MOD||(LA9_3>=XOR && LA9_3<=AsteriskAsterisk)||(LA9_3>=ColonEqualsSign && LA9_3<=LessThanSignGreaterThanSign)||LA9_3==GreaterThanSignEqualsSign||(LA9_3>=BY && LA9_3<=DO)||(LA9_3>=OF && LA9_3<=TO)||LA9_3==Ampersand||(LA9_3>=RightParenthesis && LA9_3<=HyphenMinus)||(LA9_3>=Solidus && LA9_3<=GreaterThanSign)||(LA9_3>=LeftSquareBracket && LA9_3<=RightSquareBracket)) ) {
                    alt9=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 3, input);

                    throw nvae;
                }
                }
                break;
            case DT:
                {
                int LA9_4 = input.LA(2);

                if ( (LA9_4==EOF||LA9_4==END_REPEAT||LA9_4==THEN||(LA9_4>=B && LA9_4<=AND)||LA9_4==MOD||(LA9_4>=XOR && LA9_4<=AsteriskAsterisk)||(LA9_4>=ColonEqualsSign && LA9_4<=LessThanSignGreaterThanSign)||LA9_4==GreaterThanSignEqualsSign||(LA9_4>=BY && LA9_4<=DO)||(LA9_4>=OF && LA9_4<=TO)||LA9_4==Ampersand||(LA9_4>=RightParenthesis && LA9_4<=HyphenMinus)||(LA9_4>=Solidus && LA9_4<=GreaterThanSign)||(LA9_4>=LeftSquareBracket && LA9_4<=RightSquareBracket)) ) {
                    alt9=1;
                }
                else if ( (LA9_4==FullStop) ) {
                    int LA9_5 = input.LA(3);

                    if ( (LA9_5==RULE_UNSIGNED_INT) ) {
                        alt9=1;
                    }
                    else if ( (LA9_5==DT||LA9_5==LT||LA9_5==T||LA9_5==RULE_ID) ) {
                        alt9=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 5, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 4, input);

                    throw nvae;
                }
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalStructuredTextParser.g:1903:2: ( ruleVariable_Primary )
                    {
                    // InternalStructuredTextParser.g:1903:2: ( ruleVariable_Primary )
                    // InternalStructuredTextParser.g:1904:3: ruleVariable_Primary
                    {
                     before(grammarAccess.getVariable_SubscriptAccess().getVariable_PrimaryParserRuleCall_0_0()); 
                    pushFollow(FOLLOW_2);
                    ruleVariable_Primary();

                    state._fsp--;

                     after(grammarAccess.getVariable_SubscriptAccess().getVariable_PrimaryParserRuleCall_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1909:2: ( ruleVariable_Adapter )
                    {
                    // InternalStructuredTextParser.g:1909:2: ( ruleVariable_Adapter )
                    // InternalStructuredTextParser.g:1910:3: ruleVariable_Adapter
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Alternatives_0"


    // $ANTLR start "rule__Multibit_Part_Access__Alternatives"
    // InternalStructuredTextParser.g:1919:1: rule__Multibit_Part_Access__Alternatives : ( ( ( rule__Multibit_Part_Access__Group_0__0 ) ) | ( ( rule__Multibit_Part_Access__Group_1__0 ) ) | ( ( rule__Multibit_Part_Access__Group_2__0 ) ) | ( ( rule__Multibit_Part_Access__Group_3__0 ) ) | ( ( rule__Multibit_Part_Access__Group_4__0 ) ) );
    public final void rule__Multibit_Part_Access__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1923:1: ( ( ( rule__Multibit_Part_Access__Group_0__0 ) ) | ( ( rule__Multibit_Part_Access__Group_1__0 ) ) | ( ( rule__Multibit_Part_Access__Group_2__0 ) ) | ( ( rule__Multibit_Part_Access__Group_3__0 ) ) | ( ( rule__Multibit_Part_Access__Group_4__0 ) ) )
            int alt10=5;
            switch ( input.LA(1) ) {
            case D:
                {
                alt10=1;
                }
                break;
            case W:
                {
                alt10=2;
                }
                break;
            case B:
                {
                alt10=3;
                }
                break;
            case X:
                {
                alt10=4;
                }
                break;
            case FullStop:
                {
                alt10=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalStructuredTextParser.g:1924:2: ( ( rule__Multibit_Part_Access__Group_0__0 ) )
                    {
                    // InternalStructuredTextParser.g:1924:2: ( ( rule__Multibit_Part_Access__Group_0__0 ) )
                    // InternalStructuredTextParser.g:1925:3: ( rule__Multibit_Part_Access__Group_0__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_0()); 
                    // InternalStructuredTextParser.g:1926:3: ( rule__Multibit_Part_Access__Group_0__0 )
                    // InternalStructuredTextParser.g:1926:4: rule__Multibit_Part_Access__Group_0__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Multibit_Part_Access__Group_0__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMultibit_Part_AccessAccess().getGroup_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1930:2: ( ( rule__Multibit_Part_Access__Group_1__0 ) )
                    {
                    // InternalStructuredTextParser.g:1930:2: ( ( rule__Multibit_Part_Access__Group_1__0 ) )
                    // InternalStructuredTextParser.g:1931:3: ( rule__Multibit_Part_Access__Group_1__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_1()); 
                    // InternalStructuredTextParser.g:1932:3: ( rule__Multibit_Part_Access__Group_1__0 )
                    // InternalStructuredTextParser.g:1932:4: rule__Multibit_Part_Access__Group_1__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Multibit_Part_Access__Group_1__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMultibit_Part_AccessAccess().getGroup_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1936:2: ( ( rule__Multibit_Part_Access__Group_2__0 ) )
                    {
                    // InternalStructuredTextParser.g:1936:2: ( ( rule__Multibit_Part_Access__Group_2__0 ) )
                    // InternalStructuredTextParser.g:1937:3: ( rule__Multibit_Part_Access__Group_2__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_2()); 
                    // InternalStructuredTextParser.g:1938:3: ( rule__Multibit_Part_Access__Group_2__0 )
                    // InternalStructuredTextParser.g:1938:4: rule__Multibit_Part_Access__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Multibit_Part_Access__Group_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMultibit_Part_AccessAccess().getGroup_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:1942:2: ( ( rule__Multibit_Part_Access__Group_3__0 ) )
                    {
                    // InternalStructuredTextParser.g:1942:2: ( ( rule__Multibit_Part_Access__Group_3__0 ) )
                    // InternalStructuredTextParser.g:1943:3: ( rule__Multibit_Part_Access__Group_3__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_3()); 
                    // InternalStructuredTextParser.g:1944:3: ( rule__Multibit_Part_Access__Group_3__0 )
                    // InternalStructuredTextParser.g:1944:4: rule__Multibit_Part_Access__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Multibit_Part_Access__Group_3__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getMultibit_Part_AccessAccess().getGroup_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:1948:2: ( ( rule__Multibit_Part_Access__Group_4__0 ) )
                    {
                    // InternalStructuredTextParser.g:1948:2: ( ( rule__Multibit_Part_Access__Group_4__0 ) )
                    // InternalStructuredTextParser.g:1949:3: ( rule__Multibit_Part_Access__Group_4__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_4()); 
                    // InternalStructuredTextParser.g:1950:3: ( rule__Multibit_Part_Access__Group_4__0 )
                    // InternalStructuredTextParser.g:1950:4: rule__Multibit_Part_Access__Group_4__0
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Alternatives"


    // $ANTLR start "rule__Adapter_Name__Alternatives"
    // InternalStructuredTextParser.g:1958:1: rule__Adapter_Name__Alternatives : ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) );
    public final void rule__Adapter_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1962:1: ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) )
            int alt11=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt11=1;
                }
                break;
            case T:
                {
                alt11=2;
                }
                break;
            case LT:
                {
                alt11=3;
                }
                break;
            case DT:
                {
                alt11=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // InternalStructuredTextParser.g:1963:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:1963:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:1964:3: RULE_ID
                    {
                     before(grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1969:2: ( T )
                    {
                    // InternalStructuredTextParser.g:1969:2: ( T )
                    // InternalStructuredTextParser.g:1970:3: T
                    {
                     before(grammarAccess.getAdapter_NameAccess().getTKeyword_1()); 
                    match(input,T,FOLLOW_2); 
                     after(grammarAccess.getAdapter_NameAccess().getTKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1975:2: ( LT )
                    {
                    // InternalStructuredTextParser.g:1975:2: ( LT )
                    // InternalStructuredTextParser.g:1976:3: LT
                    {
                     before(grammarAccess.getAdapter_NameAccess().getLTKeyword_2()); 
                    match(input,LT,FOLLOW_2); 
                     after(grammarAccess.getAdapter_NameAccess().getLTKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:1981:2: ( DT )
                    {
                    // InternalStructuredTextParser.g:1981:2: ( DT )
                    // InternalStructuredTextParser.g:1982:3: DT
                    {
                     before(grammarAccess.getAdapter_NameAccess().getDTKeyword_3()); 
                    match(input,DT,FOLLOW_2); 
                     after(grammarAccess.getAdapter_NameAccess().getDTKeyword_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Adapter_Name__Alternatives"


    // $ANTLR start "rule__Variable_Name__Alternatives"
    // InternalStructuredTextParser.g:1991:1: rule__Variable_Name__Alternatives : ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) );
    public final void rule__Variable_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1995:1: ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) )
            int alt12=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt12=1;
                }
                break;
            case T:
                {
                alt12=2;
                }
                break;
            case LT:
                {
                alt12=3;
                }
                break;
            case DT:
                {
                alt12=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // InternalStructuredTextParser.g:1996:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:1996:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:1997:3: RULE_ID
                    {
                     before(grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2002:2: ( T )
                    {
                    // InternalStructuredTextParser.g:2002:2: ( T )
                    // InternalStructuredTextParser.g:2003:3: T
                    {
                     before(grammarAccess.getVariable_NameAccess().getTKeyword_1()); 
                    match(input,T,FOLLOW_2); 
                     after(grammarAccess.getVariable_NameAccess().getTKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2008:2: ( LT )
                    {
                    // InternalStructuredTextParser.g:2008:2: ( LT )
                    // InternalStructuredTextParser.g:2009:3: LT
                    {
                     before(grammarAccess.getVariable_NameAccess().getLTKeyword_2()); 
                    match(input,LT,FOLLOW_2); 
                     after(grammarAccess.getVariable_NameAccess().getLTKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2014:2: ( DT )
                    {
                    // InternalStructuredTextParser.g:2014:2: ( DT )
                    // InternalStructuredTextParser.g:2015:3: DT
                    {
                     before(grammarAccess.getVariable_NameAccess().getDTKeyword_3()); 
                    match(input,DT,FOLLOW_2); 
                     after(grammarAccess.getVariable_NameAccess().getDTKeyword_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Name__Alternatives"


    // $ANTLR start "rule__Constant__Alternatives"
    // InternalStructuredTextParser.g:2024:1: rule__Constant__Alternatives : ( ( ruleNumeric_Literal ) | ( ruleChar_Literal ) | ( ruleTime_Literal ) | ( ruleBool_Literal ) );
    public final void rule__Constant__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2028:1: ( ( ruleNumeric_Literal ) | ( ruleChar_Literal ) | ( ruleTime_Literal ) | ( ruleBool_Literal ) )
            int alt13=4;
            switch ( input.LA(1) ) {
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
            case RULE_UNSIGNED_INT:
            case RULE_BINARY_INT:
            case RULE_OCTAL_INT:
            case RULE_HEX_INT:
                {
                alt13=1;
                }
                break;
            case WSTRING:
            case STRING:
            case WCHAR:
            case CHAR:
            case RULE_S_BYTE_CHAR_STR:
            case RULE_D_BYTE_CHAR_STR:
                {
                alt13=2;
                }
                break;
            case RULE_TIMEOFDAY:
            case RULE_TIME:
            case RULE_DATETIME:
            case RULE_DATE:
                {
                alt13=3;
                }
                break;
            case FALSE:
            case BOOL:
            case TRUE:
                {
                alt13=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // InternalStructuredTextParser.g:2029:2: ( ruleNumeric_Literal )
                    {
                    // InternalStructuredTextParser.g:2029:2: ( ruleNumeric_Literal )
                    // InternalStructuredTextParser.g:2030:3: ruleNumeric_Literal
                    {
                     before(grammarAccess.getConstantAccess().getNumeric_LiteralParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleNumeric_Literal();

                    state._fsp--;

                     after(grammarAccess.getConstantAccess().getNumeric_LiteralParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2035:2: ( ruleChar_Literal )
                    {
                    // InternalStructuredTextParser.g:2035:2: ( ruleChar_Literal )
                    // InternalStructuredTextParser.g:2036:3: ruleChar_Literal
                    {
                     before(grammarAccess.getConstantAccess().getChar_LiteralParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleChar_Literal();

                    state._fsp--;

                     after(grammarAccess.getConstantAccess().getChar_LiteralParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2041:2: ( ruleTime_Literal )
                    {
                    // InternalStructuredTextParser.g:2041:2: ( ruleTime_Literal )
                    // InternalStructuredTextParser.g:2042:3: ruleTime_Literal
                    {
                     before(grammarAccess.getConstantAccess().getTime_LiteralParserRuleCall_2()); 
                    pushFollow(FOLLOW_2);
                    ruleTime_Literal();

                    state._fsp--;

                     after(grammarAccess.getConstantAccess().getTime_LiteralParserRuleCall_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2047:2: ( ruleBool_Literal )
                    {
                    // InternalStructuredTextParser.g:2047:2: ( ruleBool_Literal )
                    // InternalStructuredTextParser.g:2048:3: ruleBool_Literal
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Constant__Alternatives"


    // $ANTLR start "rule__Numeric_Literal__Alternatives"
    // InternalStructuredTextParser.g:2057:1: rule__Numeric_Literal__Alternatives : ( ( ruleInt_Literal ) | ( ruleReal_Literal ) );
    public final void rule__Numeric_Literal__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2061:1: ( ( ruleInt_Literal ) | ( ruleReal_Literal ) )
            int alt14=2;
            switch ( input.LA(1) ) {
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
            case RULE_HEX_INT:
                {
                alt14=1;
                }
                break;
            case PlusSign:
                {
                int LA14_2 = input.LA(2);

                if ( (LA14_2==RULE_UNSIGNED_INT) ) {
                    int LA14_4 = input.LA(3);

                    if ( (LA14_4==EOF||LA14_4==END_REPEAT||LA14_4==THEN||LA14_4==AND||LA14_4==MOD||(LA14_4>=XOR && LA14_4<=AsteriskAsterisk)||(LA14_4>=LessThanSignEqualsSign && LA14_4<=LessThanSignGreaterThanSign)||LA14_4==GreaterThanSignEqualsSign||(LA14_4>=BY && LA14_4<=DO)||(LA14_4>=OF && LA14_4<=TO)||LA14_4==Ampersand||(LA14_4>=RightParenthesis && LA14_4<=HyphenMinus)||(LA14_4>=Solidus && LA14_4<=GreaterThanSign)||LA14_4==RightSquareBracket) ) {
                        alt14=1;
                    }
                    else if ( (LA14_4==FullStop) ) {
                        alt14=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 14, 4, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 2, input);

                    throw nvae;
                }
                }
                break;
            case HyphenMinus:
                {
                int LA14_3 = input.LA(2);

                if ( (LA14_3==RULE_UNSIGNED_INT) ) {
                    int LA14_4 = input.LA(3);

                    if ( (LA14_4==EOF||LA14_4==END_REPEAT||LA14_4==THEN||LA14_4==AND||LA14_4==MOD||(LA14_4>=XOR && LA14_4<=AsteriskAsterisk)||(LA14_4>=LessThanSignEqualsSign && LA14_4<=LessThanSignGreaterThanSign)||LA14_4==GreaterThanSignEqualsSign||(LA14_4>=BY && LA14_4<=DO)||(LA14_4>=OF && LA14_4<=TO)||LA14_4==Ampersand||(LA14_4>=RightParenthesis && LA14_4<=HyphenMinus)||(LA14_4>=Solidus && LA14_4<=GreaterThanSign)||LA14_4==RightSquareBracket) ) {
                        alt14=1;
                    }
                    else if ( (LA14_4==FullStop) ) {
                        alt14=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 14, 4, input);

                        throw nvae;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 3, input);

                    throw nvae;
                }
                }
                break;
            case RULE_UNSIGNED_INT:
                {
                int LA14_4 = input.LA(2);

                if ( (LA14_4==EOF||LA14_4==END_REPEAT||LA14_4==THEN||LA14_4==AND||LA14_4==MOD||(LA14_4>=XOR && LA14_4<=AsteriskAsterisk)||(LA14_4>=LessThanSignEqualsSign && LA14_4<=LessThanSignGreaterThanSign)||LA14_4==GreaterThanSignEqualsSign||(LA14_4>=BY && LA14_4<=DO)||(LA14_4>=OF && LA14_4<=TO)||LA14_4==Ampersand||(LA14_4>=RightParenthesis && LA14_4<=HyphenMinus)||(LA14_4>=Solidus && LA14_4<=GreaterThanSign)||LA14_4==RightSquareBracket) ) {
                    alt14=1;
                }
                else if ( (LA14_4==FullStop) ) {
                    alt14=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 14, 4, input);

                    throw nvae;
                }
                }
                break;
            case LREAL:
            case REAL:
                {
                alt14=2;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }

            switch (alt14) {
                case 1 :
                    // InternalStructuredTextParser.g:2062:2: ( ruleInt_Literal )
                    {
                    // InternalStructuredTextParser.g:2062:2: ( ruleInt_Literal )
                    // InternalStructuredTextParser.g:2063:3: ruleInt_Literal
                    {
                     before(grammarAccess.getNumeric_LiteralAccess().getInt_LiteralParserRuleCall_0()); 
                    pushFollow(FOLLOW_2);
                    ruleInt_Literal();

                    state._fsp--;

                     after(grammarAccess.getNumeric_LiteralAccess().getInt_LiteralParserRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2068:2: ( ruleReal_Literal )
                    {
                    // InternalStructuredTextParser.g:2068:2: ( ruleReal_Literal )
                    // InternalStructuredTextParser.g:2069:3: ruleReal_Literal
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
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Numeric_Literal__Alternatives"


    // $ANTLR start "rule__Int_Literal__ValueAlternatives_1_0"
    // InternalStructuredTextParser.g:2078:1: rule__Int_Literal__ValueAlternatives_1_0 : ( ( ruleSigned_Int ) | ( RULE_BINARY_INT ) | ( RULE_OCTAL_INT ) | ( RULE_HEX_INT ) );
    public final void rule__Int_Literal__ValueAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2082:1: ( ( ruleSigned_Int ) | ( RULE_BINARY_INT ) | ( RULE_OCTAL_INT ) | ( RULE_HEX_INT ) )
            int alt15=4;
            switch ( input.LA(1) ) {
            case PlusSign:
            case HyphenMinus:
            case RULE_UNSIGNED_INT:
                {
                alt15=1;
                }
                break;
            case RULE_BINARY_INT:
                {
                alt15=2;
                }
                break;
            case RULE_OCTAL_INT:
                {
                alt15=3;
                }
                break;
            case RULE_HEX_INT:
                {
                alt15=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // InternalStructuredTextParser.g:2083:2: ( ruleSigned_Int )
                    {
                    // InternalStructuredTextParser.g:2083:2: ( ruleSigned_Int )
                    // InternalStructuredTextParser.g:2084:3: ruleSigned_Int
                    {
                     before(grammarAccess.getInt_LiteralAccess().getValueSigned_IntParserRuleCall_1_0_0()); 
                    pushFollow(FOLLOW_2);
                    ruleSigned_Int();

                    state._fsp--;

                     after(grammarAccess.getInt_LiteralAccess().getValueSigned_IntParserRuleCall_1_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2089:2: ( RULE_BINARY_INT )
                    {
                    // InternalStructuredTextParser.g:2089:2: ( RULE_BINARY_INT )
                    // InternalStructuredTextParser.g:2090:3: RULE_BINARY_INT
                    {
                     before(grammarAccess.getInt_LiteralAccess().getValueBINARY_INTTerminalRuleCall_1_0_1()); 
                    match(input,RULE_BINARY_INT,FOLLOW_2); 
                     after(grammarAccess.getInt_LiteralAccess().getValueBINARY_INTTerminalRuleCall_1_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2095:2: ( RULE_OCTAL_INT )
                    {
                    // InternalStructuredTextParser.g:2095:2: ( RULE_OCTAL_INT )
                    // InternalStructuredTextParser.g:2096:3: RULE_OCTAL_INT
                    {
                     before(grammarAccess.getInt_LiteralAccess().getValueOCTAL_INTTerminalRuleCall_1_0_2()); 
                    match(input,RULE_OCTAL_INT,FOLLOW_2); 
                     after(grammarAccess.getInt_LiteralAccess().getValueOCTAL_INTTerminalRuleCall_1_0_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2101:2: ( RULE_HEX_INT )
                    {
                    // InternalStructuredTextParser.g:2101:2: ( RULE_HEX_INT )
                    // InternalStructuredTextParser.g:2102:3: RULE_HEX_INT
                    {
                     before(grammarAccess.getInt_LiteralAccess().getValueHEX_INTTerminalRuleCall_1_0_3()); 
                    match(input,RULE_HEX_INT,FOLLOW_2); 
                     after(grammarAccess.getInt_LiteralAccess().getValueHEX_INTTerminalRuleCall_1_0_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__ValueAlternatives_1_0"


    // $ANTLR start "rule__Signed_Int__Alternatives_0"
    // InternalStructuredTextParser.g:2111:1: rule__Signed_Int__Alternatives_0 : ( ( PlusSign ) | ( HyphenMinus ) );
    public final void rule__Signed_Int__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2115:1: ( ( PlusSign ) | ( HyphenMinus ) )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==PlusSign) ) {
                alt16=1;
            }
            else if ( (LA16_0==HyphenMinus) ) {
                alt16=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // InternalStructuredTextParser.g:2116:2: ( PlusSign )
                    {
                    // InternalStructuredTextParser.g:2116:2: ( PlusSign )
                    // InternalStructuredTextParser.g:2117:3: PlusSign
                    {
                     before(grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0()); 
                    match(input,PlusSign,FOLLOW_2); 
                     after(grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2122:2: ( HyphenMinus )
                    {
                    // InternalStructuredTextParser.g:2122:2: ( HyphenMinus )
                    // InternalStructuredTextParser.g:2123:3: HyphenMinus
                    {
                     before(grammarAccess.getSigned_IntAccess().getHyphenMinusKeyword_0_1()); 
                    match(input,HyphenMinus,FOLLOW_2); 
                     after(grammarAccess.getSigned_IntAccess().getHyphenMinusKeyword_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Signed_Int__Alternatives_0"


    // $ANTLR start "rule__Bool_Value__Alternatives"
    // InternalStructuredTextParser.g:2132:1: rule__Bool_Value__Alternatives : ( ( FALSE ) | ( TRUE ) );
    public final void rule__Bool_Value__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2136:1: ( ( FALSE ) | ( TRUE ) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==FALSE) ) {
                alt17=1;
            }
            else if ( (LA17_0==TRUE) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // InternalStructuredTextParser.g:2137:2: ( FALSE )
                    {
                    // InternalStructuredTextParser.g:2137:2: ( FALSE )
                    // InternalStructuredTextParser.g:2138:3: FALSE
                    {
                     before(grammarAccess.getBool_ValueAccess().getFALSEKeyword_0()); 
                    match(input,FALSE,FOLLOW_2); 
                     after(grammarAccess.getBool_ValueAccess().getFALSEKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2143:2: ( TRUE )
                    {
                    // InternalStructuredTextParser.g:2143:2: ( TRUE )
                    // InternalStructuredTextParser.g:2144:3: TRUE
                    {
                     before(grammarAccess.getBool_ValueAccess().getTRUEKeyword_1()); 
                    match(input,TRUE,FOLLOW_2); 
                     after(grammarAccess.getBool_ValueAccess().getTRUEKeyword_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Value__Alternatives"


    // $ANTLR start "rule__Char_Literal__ValueAlternatives_1_0"
    // InternalStructuredTextParser.g:2153:1: rule__Char_Literal__ValueAlternatives_1_0 : ( ( RULE_S_BYTE_CHAR_STR ) | ( RULE_D_BYTE_CHAR_STR ) );
    public final void rule__Char_Literal__ValueAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2157:1: ( ( RULE_S_BYTE_CHAR_STR ) | ( RULE_D_BYTE_CHAR_STR ) )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==RULE_S_BYTE_CHAR_STR) ) {
                alt18=1;
            }
            else if ( (LA18_0==RULE_D_BYTE_CHAR_STR) ) {
                alt18=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // InternalStructuredTextParser.g:2158:2: ( RULE_S_BYTE_CHAR_STR )
                    {
                    // InternalStructuredTextParser.g:2158:2: ( RULE_S_BYTE_CHAR_STR )
                    // InternalStructuredTextParser.g:2159:3: RULE_S_BYTE_CHAR_STR
                    {
                     before(grammarAccess.getChar_LiteralAccess().getValueS_BYTE_CHAR_STRTerminalRuleCall_1_0_0()); 
                    match(input,RULE_S_BYTE_CHAR_STR,FOLLOW_2); 
                     after(grammarAccess.getChar_LiteralAccess().getValueS_BYTE_CHAR_STRTerminalRuleCall_1_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2164:2: ( RULE_D_BYTE_CHAR_STR )
                    {
                    // InternalStructuredTextParser.g:2164:2: ( RULE_D_BYTE_CHAR_STR )
                    // InternalStructuredTextParser.g:2165:3: RULE_D_BYTE_CHAR_STR
                    {
                     before(grammarAccess.getChar_LiteralAccess().getValueD_BYTE_CHAR_STRTerminalRuleCall_1_0_1()); 
                    match(input,RULE_D_BYTE_CHAR_STR,FOLLOW_2); 
                     after(grammarAccess.getChar_LiteralAccess().getValueD_BYTE_CHAR_STRTerminalRuleCall_1_0_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__ValueAlternatives_1_0"


    // $ANTLR start "rule__Time_Literal__LiteralAlternatives_0"
    // InternalStructuredTextParser.g:2174:1: rule__Time_Literal__LiteralAlternatives_0 : ( ( RULE_TIME ) | ( RULE_DATE ) | ( RULE_TIMEOFDAY ) | ( RULE_DATETIME ) );
    public final void rule__Time_Literal__LiteralAlternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2178:1: ( ( RULE_TIME ) | ( RULE_DATE ) | ( RULE_TIMEOFDAY ) | ( RULE_DATETIME ) )
            int alt19=4;
            switch ( input.LA(1) ) {
            case RULE_TIME:
                {
                alt19=1;
                }
                break;
            case RULE_DATE:
                {
                alt19=2;
                }
                break;
            case RULE_TIMEOFDAY:
                {
                alt19=3;
                }
                break;
            case RULE_DATETIME:
                {
                alt19=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // InternalStructuredTextParser.g:2179:2: ( RULE_TIME )
                    {
                    // InternalStructuredTextParser.g:2179:2: ( RULE_TIME )
                    // InternalStructuredTextParser.g:2180:3: RULE_TIME
                    {
                     before(grammarAccess.getTime_LiteralAccess().getLiteralTIMETerminalRuleCall_0_0()); 
                    match(input,RULE_TIME,FOLLOW_2); 
                     after(grammarAccess.getTime_LiteralAccess().getLiteralTIMETerminalRuleCall_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2185:2: ( RULE_DATE )
                    {
                    // InternalStructuredTextParser.g:2185:2: ( RULE_DATE )
                    // InternalStructuredTextParser.g:2186:3: RULE_DATE
                    {
                     before(grammarAccess.getTime_LiteralAccess().getLiteralDATETerminalRuleCall_0_1()); 
                    match(input,RULE_DATE,FOLLOW_2); 
                     after(grammarAccess.getTime_LiteralAccess().getLiteralDATETerminalRuleCall_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2191:2: ( RULE_TIMEOFDAY )
                    {
                    // InternalStructuredTextParser.g:2191:2: ( RULE_TIMEOFDAY )
                    // InternalStructuredTextParser.g:2192:3: RULE_TIMEOFDAY
                    {
                     before(grammarAccess.getTime_LiteralAccess().getLiteralTIMEOFDAYTerminalRuleCall_0_2()); 
                    match(input,RULE_TIMEOFDAY,FOLLOW_2); 
                     after(grammarAccess.getTime_LiteralAccess().getLiteralTIMEOFDAYTerminalRuleCall_0_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2197:2: ( RULE_DATETIME )
                    {
                    // InternalStructuredTextParser.g:2197:2: ( RULE_DATETIME )
                    // InternalStructuredTextParser.g:2198:3: RULE_DATETIME
                    {
                     before(grammarAccess.getTime_LiteralAccess().getLiteralDATETIMETerminalRuleCall_0_3()); 
                    match(input,RULE_DATETIME,FOLLOW_2); 
                     after(grammarAccess.getTime_LiteralAccess().getLiteralDATETIMETerminalRuleCall_0_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Time_Literal__LiteralAlternatives_0"


    // $ANTLR start "rule__Type_Name__Alternatives"
    // InternalStructuredTextParser.g:2207:1: rule__Type_Name__Alternatives : ( ( RULE_ID ) | ( DINT ) | ( INT ) | ( SINT ) | ( LINT ) | ( UINT ) | ( USINT ) | ( UDINT ) | ( ULINT ) | ( REAL ) | ( LREAL ) | ( STRING ) | ( WSTRING ) | ( CHAR ) | ( WCHAR ) | ( TIME ) | ( LTIME ) | ( TIME_OF_DAY ) | ( LTIME_OF_DAY ) | ( TOD ) | ( LTOD ) | ( DATE ) | ( LDATE ) | ( DATE_AND_TIME ) | ( LDATE_AND_TIME ) | ( BOOL ) | ( LWORD ) | ( DWORD ) | ( WORD ) | ( BYTE ) );
    public final void rule__Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2211:1: ( ( RULE_ID ) | ( DINT ) | ( INT ) | ( SINT ) | ( LINT ) | ( UINT ) | ( USINT ) | ( UDINT ) | ( ULINT ) | ( REAL ) | ( LREAL ) | ( STRING ) | ( WSTRING ) | ( CHAR ) | ( WCHAR ) | ( TIME ) | ( LTIME ) | ( TIME_OF_DAY ) | ( LTIME_OF_DAY ) | ( TOD ) | ( LTOD ) | ( DATE ) | ( LDATE ) | ( DATE_AND_TIME ) | ( LDATE_AND_TIME ) | ( BOOL ) | ( LWORD ) | ( DWORD ) | ( WORD ) | ( BYTE ) )
            int alt20=30;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                alt20=1;
                }
                break;
            case DINT:
                {
                alt20=2;
                }
                break;
            case INT:
                {
                alt20=3;
                }
                break;
            case SINT:
                {
                alt20=4;
                }
                break;
            case LINT:
                {
                alt20=5;
                }
                break;
            case UINT:
                {
                alt20=6;
                }
                break;
            case USINT:
                {
                alt20=7;
                }
                break;
            case UDINT:
                {
                alt20=8;
                }
                break;
            case ULINT:
                {
                alt20=9;
                }
                break;
            case REAL:
                {
                alt20=10;
                }
                break;
            case LREAL:
                {
                alt20=11;
                }
                break;
            case STRING:
                {
                alt20=12;
                }
                break;
            case WSTRING:
                {
                alt20=13;
                }
                break;
            case CHAR:
                {
                alt20=14;
                }
                break;
            case WCHAR:
                {
                alt20=15;
                }
                break;
            case TIME:
                {
                alt20=16;
                }
                break;
            case LTIME:
                {
                alt20=17;
                }
                break;
            case TIME_OF_DAY:
                {
                alt20=18;
                }
                break;
            case LTIME_OF_DAY:
                {
                alt20=19;
                }
                break;
            case TOD:
                {
                alt20=20;
                }
                break;
            case LTOD:
                {
                alt20=21;
                }
                break;
            case DATE:
                {
                alt20=22;
                }
                break;
            case LDATE:
                {
                alt20=23;
                }
                break;
            case DATE_AND_TIME:
                {
                alt20=24;
                }
                break;
            case LDATE_AND_TIME:
                {
                alt20=25;
                }
                break;
            case BOOL:
                {
                alt20=26;
                }
                break;
            case LWORD:
                {
                alt20=27;
                }
                break;
            case DWORD:
                {
                alt20=28;
                }
                break;
            case WORD:
                {
                alt20=29;
                }
                break;
            case BYTE:
                {
                alt20=30;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // InternalStructuredTextParser.g:2212:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:2212:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:2213:3: RULE_ID
                    {
                     before(grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2218:2: ( DINT )
                    {
                    // InternalStructuredTextParser.g:2218:2: ( DINT )
                    // InternalStructuredTextParser.g:2219:3: DINT
                    {
                     before(grammarAccess.getType_NameAccess().getDINTKeyword_1()); 
                    match(input,DINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDINTKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2224:2: ( INT )
                    {
                    // InternalStructuredTextParser.g:2224:2: ( INT )
                    // InternalStructuredTextParser.g:2225:3: INT
                    {
                     before(grammarAccess.getType_NameAccess().getINTKeyword_2()); 
                    match(input,INT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getINTKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2230:2: ( SINT )
                    {
                    // InternalStructuredTextParser.g:2230:2: ( SINT )
                    // InternalStructuredTextParser.g:2231:3: SINT
                    {
                     before(grammarAccess.getType_NameAccess().getSINTKeyword_3()); 
                    match(input,SINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getSINTKeyword_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:2236:2: ( LINT )
                    {
                    // InternalStructuredTextParser.g:2236:2: ( LINT )
                    // InternalStructuredTextParser.g:2237:3: LINT
                    {
                     before(grammarAccess.getType_NameAccess().getLINTKeyword_4()); 
                    match(input,LINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLINTKeyword_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:2242:2: ( UINT )
                    {
                    // InternalStructuredTextParser.g:2242:2: ( UINT )
                    // InternalStructuredTextParser.g:2243:3: UINT
                    {
                     before(grammarAccess.getType_NameAccess().getUINTKeyword_5()); 
                    match(input,UINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getUINTKeyword_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:2248:2: ( USINT )
                    {
                    // InternalStructuredTextParser.g:2248:2: ( USINT )
                    // InternalStructuredTextParser.g:2249:3: USINT
                    {
                     before(grammarAccess.getType_NameAccess().getUSINTKeyword_6()); 
                    match(input,USINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getUSINTKeyword_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalStructuredTextParser.g:2254:2: ( UDINT )
                    {
                    // InternalStructuredTextParser.g:2254:2: ( UDINT )
                    // InternalStructuredTextParser.g:2255:3: UDINT
                    {
                     before(grammarAccess.getType_NameAccess().getUDINTKeyword_7()); 
                    match(input,UDINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getUDINTKeyword_7()); 

                    }


                    }
                    break;
                case 9 :
                    // InternalStructuredTextParser.g:2260:2: ( ULINT )
                    {
                    // InternalStructuredTextParser.g:2260:2: ( ULINT )
                    // InternalStructuredTextParser.g:2261:3: ULINT
                    {
                     before(grammarAccess.getType_NameAccess().getULINTKeyword_8()); 
                    match(input,ULINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getULINTKeyword_8()); 

                    }


                    }
                    break;
                case 10 :
                    // InternalStructuredTextParser.g:2266:2: ( REAL )
                    {
                    // InternalStructuredTextParser.g:2266:2: ( REAL )
                    // InternalStructuredTextParser.g:2267:3: REAL
                    {
                     before(grammarAccess.getType_NameAccess().getREALKeyword_9()); 
                    match(input,REAL,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getREALKeyword_9()); 

                    }


                    }
                    break;
                case 11 :
                    // InternalStructuredTextParser.g:2272:2: ( LREAL )
                    {
                    // InternalStructuredTextParser.g:2272:2: ( LREAL )
                    // InternalStructuredTextParser.g:2273:3: LREAL
                    {
                     before(grammarAccess.getType_NameAccess().getLREALKeyword_10()); 
                    match(input,LREAL,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLREALKeyword_10()); 

                    }


                    }
                    break;
                case 12 :
                    // InternalStructuredTextParser.g:2278:2: ( STRING )
                    {
                    // InternalStructuredTextParser.g:2278:2: ( STRING )
                    // InternalStructuredTextParser.g:2279:3: STRING
                    {
                     before(grammarAccess.getType_NameAccess().getSTRINGKeyword_11()); 
                    match(input,STRING,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getSTRINGKeyword_11()); 

                    }


                    }
                    break;
                case 13 :
                    // InternalStructuredTextParser.g:2284:2: ( WSTRING )
                    {
                    // InternalStructuredTextParser.g:2284:2: ( WSTRING )
                    // InternalStructuredTextParser.g:2285:3: WSTRING
                    {
                     before(grammarAccess.getType_NameAccess().getWSTRINGKeyword_12()); 
                    match(input,WSTRING,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getWSTRINGKeyword_12()); 

                    }


                    }
                    break;
                case 14 :
                    // InternalStructuredTextParser.g:2290:2: ( CHAR )
                    {
                    // InternalStructuredTextParser.g:2290:2: ( CHAR )
                    // InternalStructuredTextParser.g:2291:3: CHAR
                    {
                     before(grammarAccess.getType_NameAccess().getCHARKeyword_13()); 
                    match(input,CHAR,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getCHARKeyword_13()); 

                    }


                    }
                    break;
                case 15 :
                    // InternalStructuredTextParser.g:2296:2: ( WCHAR )
                    {
                    // InternalStructuredTextParser.g:2296:2: ( WCHAR )
                    // InternalStructuredTextParser.g:2297:3: WCHAR
                    {
                     before(grammarAccess.getType_NameAccess().getWCHARKeyword_14()); 
                    match(input,WCHAR,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getWCHARKeyword_14()); 

                    }


                    }
                    break;
                case 16 :
                    // InternalStructuredTextParser.g:2302:2: ( TIME )
                    {
                    // InternalStructuredTextParser.g:2302:2: ( TIME )
                    // InternalStructuredTextParser.g:2303:3: TIME
                    {
                     before(grammarAccess.getType_NameAccess().getTIMEKeyword_15()); 
                    match(input,TIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getTIMEKeyword_15()); 

                    }


                    }
                    break;
                case 17 :
                    // InternalStructuredTextParser.g:2308:2: ( LTIME )
                    {
                    // InternalStructuredTextParser.g:2308:2: ( LTIME )
                    // InternalStructuredTextParser.g:2309:3: LTIME
                    {
                     before(grammarAccess.getType_NameAccess().getLTIMEKeyword_16()); 
                    match(input,LTIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLTIMEKeyword_16()); 

                    }


                    }
                    break;
                case 18 :
                    // InternalStructuredTextParser.g:2314:2: ( TIME_OF_DAY )
                    {
                    // InternalStructuredTextParser.g:2314:2: ( TIME_OF_DAY )
                    // InternalStructuredTextParser.g:2315:3: TIME_OF_DAY
                    {
                     before(grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17()); 
                    match(input,TIME_OF_DAY,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17()); 

                    }


                    }
                    break;
                case 19 :
                    // InternalStructuredTextParser.g:2320:2: ( LTIME_OF_DAY )
                    {
                    // InternalStructuredTextParser.g:2320:2: ( LTIME_OF_DAY )
                    // InternalStructuredTextParser.g:2321:3: LTIME_OF_DAY
                    {
                     before(grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18()); 
                    match(input,LTIME_OF_DAY,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18()); 

                    }


                    }
                    break;
                case 20 :
                    // InternalStructuredTextParser.g:2326:2: ( TOD )
                    {
                    // InternalStructuredTextParser.g:2326:2: ( TOD )
                    // InternalStructuredTextParser.g:2327:3: TOD
                    {
                     before(grammarAccess.getType_NameAccess().getTODKeyword_19()); 
                    match(input,TOD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getTODKeyword_19()); 

                    }


                    }
                    break;
                case 21 :
                    // InternalStructuredTextParser.g:2332:2: ( LTOD )
                    {
                    // InternalStructuredTextParser.g:2332:2: ( LTOD )
                    // InternalStructuredTextParser.g:2333:3: LTOD
                    {
                     before(grammarAccess.getType_NameAccess().getLTODKeyword_20()); 
                    match(input,LTOD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLTODKeyword_20()); 

                    }


                    }
                    break;
                case 22 :
                    // InternalStructuredTextParser.g:2338:2: ( DATE )
                    {
                    // InternalStructuredTextParser.g:2338:2: ( DATE )
                    // InternalStructuredTextParser.g:2339:3: DATE
                    {
                     before(grammarAccess.getType_NameAccess().getDATEKeyword_21()); 
                    match(input,DATE,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDATEKeyword_21()); 

                    }


                    }
                    break;
                case 23 :
                    // InternalStructuredTextParser.g:2344:2: ( LDATE )
                    {
                    // InternalStructuredTextParser.g:2344:2: ( LDATE )
                    // InternalStructuredTextParser.g:2345:3: LDATE
                    {
                     before(grammarAccess.getType_NameAccess().getLDATEKeyword_22()); 
                    match(input,LDATE,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLDATEKeyword_22()); 

                    }


                    }
                    break;
                case 24 :
                    // InternalStructuredTextParser.g:2350:2: ( DATE_AND_TIME )
                    {
                    // InternalStructuredTextParser.g:2350:2: ( DATE_AND_TIME )
                    // InternalStructuredTextParser.g:2351:3: DATE_AND_TIME
                    {
                     before(grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23()); 
                    match(input,DATE_AND_TIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23()); 

                    }


                    }
                    break;
                case 25 :
                    // InternalStructuredTextParser.g:2356:2: ( LDATE_AND_TIME )
                    {
                    // InternalStructuredTextParser.g:2356:2: ( LDATE_AND_TIME )
                    // InternalStructuredTextParser.g:2357:3: LDATE_AND_TIME
                    {
                     before(grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24()); 
                    match(input,LDATE_AND_TIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24()); 

                    }


                    }
                    break;
                case 26 :
                    // InternalStructuredTextParser.g:2362:2: ( BOOL )
                    {
                    // InternalStructuredTextParser.g:2362:2: ( BOOL )
                    // InternalStructuredTextParser.g:2363:3: BOOL
                    {
                     before(grammarAccess.getType_NameAccess().getBOOLKeyword_25()); 
                    match(input,BOOL,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getBOOLKeyword_25()); 

                    }


                    }
                    break;
                case 27 :
                    // InternalStructuredTextParser.g:2368:2: ( LWORD )
                    {
                    // InternalStructuredTextParser.g:2368:2: ( LWORD )
                    // InternalStructuredTextParser.g:2369:3: LWORD
                    {
                     before(grammarAccess.getType_NameAccess().getLWORDKeyword_26()); 
                    match(input,LWORD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLWORDKeyword_26()); 

                    }


                    }
                    break;
                case 28 :
                    // InternalStructuredTextParser.g:2374:2: ( DWORD )
                    {
                    // InternalStructuredTextParser.g:2374:2: ( DWORD )
                    // InternalStructuredTextParser.g:2375:3: DWORD
                    {
                     before(grammarAccess.getType_NameAccess().getDWORDKeyword_27()); 
                    match(input,DWORD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDWORDKeyword_27()); 

                    }


                    }
                    break;
                case 29 :
                    // InternalStructuredTextParser.g:2380:2: ( WORD )
                    {
                    // InternalStructuredTextParser.g:2380:2: ( WORD )
                    // InternalStructuredTextParser.g:2381:3: WORD
                    {
                     before(grammarAccess.getType_NameAccess().getWORDKeyword_28()); 
                    match(input,WORD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getWORDKeyword_28()); 

                    }


                    }
                    break;
                case 30 :
                    // InternalStructuredTextParser.g:2386:2: ( BYTE )
                    {
                    // InternalStructuredTextParser.g:2386:2: ( BYTE )
                    // InternalStructuredTextParser.g:2387:3: BYTE
                    {
                     before(grammarAccess.getType_NameAccess().getBYTEKeyword_29()); 
                    match(input,BYTE,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getBYTEKeyword_29()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Type_Name__Alternatives"


    // $ANTLR start "rule__And_Operator__Alternatives"
    // InternalStructuredTextParser.g:2396:1: rule__And_Operator__Alternatives : ( ( ( AND ) ) | ( ( Ampersand ) ) );
    public final void rule__And_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2400:1: ( ( ( AND ) ) | ( ( Ampersand ) ) )
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==AND) ) {
                alt21=1;
            }
            else if ( (LA21_0==Ampersand) ) {
                alt21=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }
            switch (alt21) {
                case 1 :
                    // InternalStructuredTextParser.g:2401:2: ( ( AND ) )
                    {
                    // InternalStructuredTextParser.g:2401:2: ( ( AND ) )
                    // InternalStructuredTextParser.g:2402:3: ( AND )
                    {
                     before(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2403:3: ( AND )
                    // InternalStructuredTextParser.g:2403:4: AND
                    {
                    match(input,AND,FOLLOW_2); 

                    }

                     after(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2407:2: ( ( Ampersand ) )
                    {
                    // InternalStructuredTextParser.g:2407:2: ( ( Ampersand ) )
                    // InternalStructuredTextParser.g:2408:3: ( Ampersand )
                    {
                     before(grammarAccess.getAnd_OperatorAccess().getAMPERSANDEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2409:3: ( Ampersand )
                    // InternalStructuredTextParser.g:2409:4: Ampersand
                    {
                    match(input,Ampersand,FOLLOW_2); 

                    }

                     after(grammarAccess.getAnd_OperatorAccess().getAMPERSANDEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Operator__Alternatives"


    // $ANTLR start "rule__Compare_Operator__Alternatives"
    // InternalStructuredTextParser.g:2417:1: rule__Compare_Operator__Alternatives : ( ( ( EqualsSign ) ) | ( ( LessThanSignGreaterThanSign ) ) );
    public final void rule__Compare_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2421:1: ( ( ( EqualsSign ) ) | ( ( LessThanSignGreaterThanSign ) ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==EqualsSign) ) {
                alt22=1;
            }
            else if ( (LA22_0==LessThanSignGreaterThanSign) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // InternalStructuredTextParser.g:2422:2: ( ( EqualsSign ) )
                    {
                    // InternalStructuredTextParser.g:2422:2: ( ( EqualsSign ) )
                    // InternalStructuredTextParser.g:2423:3: ( EqualsSign )
                    {
                     before(grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2424:3: ( EqualsSign )
                    // InternalStructuredTextParser.g:2424:4: EqualsSign
                    {
                    match(input,EqualsSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2428:2: ( ( LessThanSignGreaterThanSign ) )
                    {
                    // InternalStructuredTextParser.g:2428:2: ( ( LessThanSignGreaterThanSign ) )
                    // InternalStructuredTextParser.g:2429:3: ( LessThanSignGreaterThanSign )
                    {
                     before(grammarAccess.getCompare_OperatorAccess().getNEEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2430:3: ( LessThanSignGreaterThanSign )
                    // InternalStructuredTextParser.g:2430:4: LessThanSignGreaterThanSign
                    {
                    match(input,LessThanSignGreaterThanSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getCompare_OperatorAccess().getNEEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Operator__Alternatives"


    // $ANTLR start "rule__Equ_Operator__Alternatives"
    // InternalStructuredTextParser.g:2438:1: rule__Equ_Operator__Alternatives : ( ( ( LessThanSign ) ) | ( ( LessThanSignEqualsSign ) ) | ( ( GreaterThanSign ) ) | ( ( GreaterThanSignEqualsSign ) ) );
    public final void rule__Equ_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2442:1: ( ( ( LessThanSign ) ) | ( ( LessThanSignEqualsSign ) ) | ( ( GreaterThanSign ) ) | ( ( GreaterThanSignEqualsSign ) ) )
            int alt23=4;
            switch ( input.LA(1) ) {
            case LessThanSign:
                {
                alt23=1;
                }
                break;
            case LessThanSignEqualsSign:
                {
                alt23=2;
                }
                break;
            case GreaterThanSign:
                {
                alt23=3;
                }
                break;
            case GreaterThanSignEqualsSign:
                {
                alt23=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // InternalStructuredTextParser.g:2443:2: ( ( LessThanSign ) )
                    {
                    // InternalStructuredTextParser.g:2443:2: ( ( LessThanSign ) )
                    // InternalStructuredTextParser.g:2444:3: ( LessThanSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2445:3: ( LessThanSign )
                    // InternalStructuredTextParser.g:2445:4: LessThanSign
                    {
                    match(input,LessThanSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2449:2: ( ( LessThanSignEqualsSign ) )
                    {
                    // InternalStructuredTextParser.g:2449:2: ( ( LessThanSignEqualsSign ) )
                    // InternalStructuredTextParser.g:2450:3: ( LessThanSignEqualsSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2451:3: ( LessThanSignEqualsSign )
                    // InternalStructuredTextParser.g:2451:4: LessThanSignEqualsSign
                    {
                    match(input,LessThanSignEqualsSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2455:2: ( ( GreaterThanSign ) )
                    {
                    // InternalStructuredTextParser.g:2455:2: ( ( GreaterThanSign ) )
                    // InternalStructuredTextParser.g:2456:3: ( GreaterThanSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2457:3: ( GreaterThanSign )
                    // InternalStructuredTextParser.g:2457:4: GreaterThanSign
                    {
                    match(input,GreaterThanSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2461:2: ( ( GreaterThanSignEqualsSign ) )
                    {
                    // InternalStructuredTextParser.g:2461:2: ( ( GreaterThanSignEqualsSign ) )
                    // InternalStructuredTextParser.g:2462:3: ( GreaterThanSignEqualsSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getGEEnumLiteralDeclaration_3()); 
                    // InternalStructuredTextParser.g:2463:3: ( GreaterThanSignEqualsSign )
                    // InternalStructuredTextParser.g:2463:4: GreaterThanSignEqualsSign
                    {
                    match(input,GreaterThanSignEqualsSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getEqu_OperatorAccess().getGEEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Operator__Alternatives"


    // $ANTLR start "rule__Add_Operator__Alternatives"
    // InternalStructuredTextParser.g:2471:1: rule__Add_Operator__Alternatives : ( ( ( PlusSign ) ) | ( ( HyphenMinus ) ) );
    public final void rule__Add_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2475:1: ( ( ( PlusSign ) ) | ( ( HyphenMinus ) ) )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==PlusSign) ) {
                alt24=1;
            }
            else if ( (LA24_0==HyphenMinus) ) {
                alt24=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // InternalStructuredTextParser.g:2476:2: ( ( PlusSign ) )
                    {
                    // InternalStructuredTextParser.g:2476:2: ( ( PlusSign ) )
                    // InternalStructuredTextParser.g:2477:3: ( PlusSign )
                    {
                     before(grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2478:3: ( PlusSign )
                    // InternalStructuredTextParser.g:2478:4: PlusSign
                    {
                    match(input,PlusSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2482:2: ( ( HyphenMinus ) )
                    {
                    // InternalStructuredTextParser.g:2482:2: ( ( HyphenMinus ) )
                    // InternalStructuredTextParser.g:2483:3: ( HyphenMinus )
                    {
                     before(grammarAccess.getAdd_OperatorAccess().getSUBEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2484:3: ( HyphenMinus )
                    // InternalStructuredTextParser.g:2484:4: HyphenMinus
                    {
                    match(input,HyphenMinus,FOLLOW_2); 

                    }

                     after(grammarAccess.getAdd_OperatorAccess().getSUBEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Operator__Alternatives"


    // $ANTLR start "rule__Term_Operator__Alternatives"
    // InternalStructuredTextParser.g:2492:1: rule__Term_Operator__Alternatives : ( ( ( Asterisk ) ) | ( ( Solidus ) ) | ( ( MOD ) ) );
    public final void rule__Term_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2496:1: ( ( ( Asterisk ) ) | ( ( Solidus ) ) | ( ( MOD ) ) )
            int alt25=3;
            switch ( input.LA(1) ) {
            case Asterisk:
                {
                alt25=1;
                }
                break;
            case Solidus:
                {
                alt25=2;
                }
                break;
            case MOD:
                {
                alt25=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // InternalStructuredTextParser.g:2497:2: ( ( Asterisk ) )
                    {
                    // InternalStructuredTextParser.g:2497:2: ( ( Asterisk ) )
                    // InternalStructuredTextParser.g:2498:3: ( Asterisk )
                    {
                     before(grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2499:3: ( Asterisk )
                    // InternalStructuredTextParser.g:2499:4: Asterisk
                    {
                    match(input,Asterisk,FOLLOW_2); 

                    }

                     after(grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2503:2: ( ( Solidus ) )
                    {
                    // InternalStructuredTextParser.g:2503:2: ( ( Solidus ) )
                    // InternalStructuredTextParser.g:2504:3: ( Solidus )
                    {
                     before(grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2505:3: ( Solidus )
                    // InternalStructuredTextParser.g:2505:4: Solidus
                    {
                    match(input,Solidus,FOLLOW_2); 

                    }

                     after(grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2509:2: ( ( MOD ) )
                    {
                    // InternalStructuredTextParser.g:2509:2: ( ( MOD ) )
                    // InternalStructuredTextParser.g:2510:3: ( MOD )
                    {
                     before(grammarAccess.getTerm_OperatorAccess().getMODEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2511:3: ( MOD )
                    // InternalStructuredTextParser.g:2511:4: MOD
                    {
                    match(input,MOD,FOLLOW_2); 

                    }

                     after(grammarAccess.getTerm_OperatorAccess().getMODEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term_Operator__Alternatives"


    // $ANTLR start "rule__Unary_Operator__Alternatives"
    // InternalStructuredTextParser.g:2519:1: rule__Unary_Operator__Alternatives : ( ( ( HyphenMinus ) ) | ( ( PlusSign ) ) | ( ( NOT ) ) );
    public final void rule__Unary_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2523:1: ( ( ( HyphenMinus ) ) | ( ( PlusSign ) ) | ( ( NOT ) ) )
            int alt26=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                alt26=1;
                }
                break;
            case PlusSign:
                {
                alt26=2;
                }
                break;
            case NOT:
                {
                alt26=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }

            switch (alt26) {
                case 1 :
                    // InternalStructuredTextParser.g:2524:2: ( ( HyphenMinus ) )
                    {
                    // InternalStructuredTextParser.g:2524:2: ( ( HyphenMinus ) )
                    // InternalStructuredTextParser.g:2525:3: ( HyphenMinus )
                    {
                     before(grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2526:3: ( HyphenMinus )
                    // InternalStructuredTextParser.g:2526:4: HyphenMinus
                    {
                    match(input,HyphenMinus,FOLLOW_2); 

                    }

                     after(grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2530:2: ( ( PlusSign ) )
                    {
                    // InternalStructuredTextParser.g:2530:2: ( ( PlusSign ) )
                    // InternalStructuredTextParser.g:2531:3: ( PlusSign )
                    {
                     before(grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2532:3: ( PlusSign )
                    // InternalStructuredTextParser.g:2532:4: PlusSign
                    {
                    match(input,PlusSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2536:2: ( ( NOT ) )
                    {
                    // InternalStructuredTextParser.g:2536:2: ( ( NOT ) )
                    // InternalStructuredTextParser.g:2537:3: ( NOT )
                    {
                     before(grammarAccess.getUnary_OperatorAccess().getNOTEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2538:3: ( NOT )
                    // InternalStructuredTextParser.g:2538:4: NOT
                    {
                    match(input,NOT,FOLLOW_2); 

                    }

                     after(grammarAccess.getUnary_OperatorAccess().getNOTEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Unary_Operator__Alternatives"


    // $ANTLR start "rule__Int_Type_Name__Alternatives"
    // InternalStructuredTextParser.g:2546:1: rule__Int_Type_Name__Alternatives : ( ( ( DINT ) ) | ( ( INT ) ) | ( ( SINT ) ) | ( ( LINT ) ) | ( ( UINT ) ) | ( ( USINT ) ) | ( ( UDINT ) ) | ( ( ULINT ) ) );
    public final void rule__Int_Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2550:1: ( ( ( DINT ) ) | ( ( INT ) ) | ( ( SINT ) ) | ( ( LINT ) ) | ( ( UINT ) ) | ( ( USINT ) ) | ( ( UDINT ) ) | ( ( ULINT ) ) )
            int alt27=8;
            switch ( input.LA(1) ) {
            case DINT:
                {
                alt27=1;
                }
                break;
            case INT:
                {
                alt27=2;
                }
                break;
            case SINT:
                {
                alt27=3;
                }
                break;
            case LINT:
                {
                alt27=4;
                }
                break;
            case UINT:
                {
                alt27=5;
                }
                break;
            case USINT:
                {
                alt27=6;
                }
                break;
            case UDINT:
                {
                alt27=7;
                }
                break;
            case ULINT:
                {
                alt27=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }

            switch (alt27) {
                case 1 :
                    // InternalStructuredTextParser.g:2551:2: ( ( DINT ) )
                    {
                    // InternalStructuredTextParser.g:2551:2: ( ( DINT ) )
                    // InternalStructuredTextParser.g:2552:3: ( DINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2553:3: ( DINT )
                    // InternalStructuredTextParser.g:2553:4: DINT
                    {
                    match(input,DINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2557:2: ( ( INT ) )
                    {
                    // InternalStructuredTextParser.g:2557:2: ( ( INT ) )
                    // InternalStructuredTextParser.g:2558:3: ( INT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2559:3: ( INT )
                    // InternalStructuredTextParser.g:2559:4: INT
                    {
                    match(input,INT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2563:2: ( ( SINT ) )
                    {
                    // InternalStructuredTextParser.g:2563:2: ( ( SINT ) )
                    // InternalStructuredTextParser.g:2564:3: ( SINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2565:3: ( SINT )
                    // InternalStructuredTextParser.g:2565:4: SINT
                    {
                    match(input,SINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2569:2: ( ( LINT ) )
                    {
                    // InternalStructuredTextParser.g:2569:2: ( ( LINT ) )
                    // InternalStructuredTextParser.g:2570:3: ( LINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3()); 
                    // InternalStructuredTextParser.g:2571:3: ( LINT )
                    // InternalStructuredTextParser.g:2571:4: LINT
                    {
                    match(input,LINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:2575:2: ( ( UINT ) )
                    {
                    // InternalStructuredTextParser.g:2575:2: ( ( UINT ) )
                    // InternalStructuredTextParser.g:2576:3: ( UINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4()); 
                    // InternalStructuredTextParser.g:2577:3: ( UINT )
                    // InternalStructuredTextParser.g:2577:4: UINT
                    {
                    match(input,UINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:2581:2: ( ( USINT ) )
                    {
                    // InternalStructuredTextParser.g:2581:2: ( ( USINT ) )
                    // InternalStructuredTextParser.g:2582:3: ( USINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5()); 
                    // InternalStructuredTextParser.g:2583:3: ( USINT )
                    // InternalStructuredTextParser.g:2583:4: USINT
                    {
                    match(input,USINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:2587:2: ( ( UDINT ) )
                    {
                    // InternalStructuredTextParser.g:2587:2: ( ( UDINT ) )
                    // InternalStructuredTextParser.g:2588:3: ( UDINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6()); 
                    // InternalStructuredTextParser.g:2589:3: ( UDINT )
                    // InternalStructuredTextParser.g:2589:4: UDINT
                    {
                    match(input,UDINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalStructuredTextParser.g:2593:2: ( ( ULINT ) )
                    {
                    // InternalStructuredTextParser.g:2593:2: ( ( ULINT ) )
                    // InternalStructuredTextParser.g:2594:3: ( ULINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getULINTEnumLiteralDeclaration_7()); 
                    // InternalStructuredTextParser.g:2595:3: ( ULINT )
                    // InternalStructuredTextParser.g:2595:4: ULINT
                    {
                    match(input,ULINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getULINTEnumLiteralDeclaration_7()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Type_Name__Alternatives"


    // $ANTLR start "rule__Real_Type_Name__Alternatives"
    // InternalStructuredTextParser.g:2603:1: rule__Real_Type_Name__Alternatives : ( ( ( REAL ) ) | ( ( LREAL ) ) );
    public final void rule__Real_Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2607:1: ( ( ( REAL ) ) | ( ( LREAL ) ) )
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==REAL) ) {
                alt28=1;
            }
            else if ( (LA28_0==LREAL) ) {
                alt28=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 28, 0, input);

                throw nvae;
            }
            switch (alt28) {
                case 1 :
                    // InternalStructuredTextParser.g:2608:2: ( ( REAL ) )
                    {
                    // InternalStructuredTextParser.g:2608:2: ( ( REAL ) )
                    // InternalStructuredTextParser.g:2609:3: ( REAL )
                    {
                     before(grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2610:3: ( REAL )
                    // InternalStructuredTextParser.g:2610:4: REAL
                    {
                    match(input,REAL,FOLLOW_2); 

                    }

                     after(grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2614:2: ( ( LREAL ) )
                    {
                    // InternalStructuredTextParser.g:2614:2: ( ( LREAL ) )
                    // InternalStructuredTextParser.g:2615:3: ( LREAL )
                    {
                     before(grammarAccess.getReal_Type_NameAccess().getLREALEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2616:3: ( LREAL )
                    // InternalStructuredTextParser.g:2616:4: LREAL
                    {
                    match(input,LREAL,FOLLOW_2); 

                    }

                     after(grammarAccess.getReal_Type_NameAccess().getLREALEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Type_Name__Alternatives"


    // $ANTLR start "rule__String_Type_Name__Alternatives"
    // InternalStructuredTextParser.g:2624:1: rule__String_Type_Name__Alternatives : ( ( ( STRING ) ) | ( ( WSTRING ) ) | ( ( CHAR ) ) | ( ( WCHAR ) ) );
    public final void rule__String_Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2628:1: ( ( ( STRING ) ) | ( ( WSTRING ) ) | ( ( CHAR ) ) | ( ( WCHAR ) ) )
            int alt29=4;
            switch ( input.LA(1) ) {
            case STRING:
                {
                alt29=1;
                }
                break;
            case WSTRING:
                {
                alt29=2;
                }
                break;
            case CHAR:
                {
                alt29=3;
                }
                break;
            case WCHAR:
                {
                alt29=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 29, 0, input);

                throw nvae;
            }

            switch (alt29) {
                case 1 :
                    // InternalStructuredTextParser.g:2629:2: ( ( STRING ) )
                    {
                    // InternalStructuredTextParser.g:2629:2: ( ( STRING ) )
                    // InternalStructuredTextParser.g:2630:3: ( STRING )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2631:3: ( STRING )
                    // InternalStructuredTextParser.g:2631:4: STRING
                    {
                    match(input,STRING,FOLLOW_2); 

                    }

                     after(grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2635:2: ( ( WSTRING ) )
                    {
                    // InternalStructuredTextParser.g:2635:2: ( ( WSTRING ) )
                    // InternalStructuredTextParser.g:2636:3: ( WSTRING )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2637:3: ( WSTRING )
                    // InternalStructuredTextParser.g:2637:4: WSTRING
                    {
                    match(input,WSTRING,FOLLOW_2); 

                    }

                     after(grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2641:2: ( ( CHAR ) )
                    {
                    // InternalStructuredTextParser.g:2641:2: ( ( CHAR ) )
                    // InternalStructuredTextParser.g:2642:3: ( CHAR )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2643:3: ( CHAR )
                    // InternalStructuredTextParser.g:2643:4: CHAR
                    {
                    match(input,CHAR,FOLLOW_2); 

                    }

                     after(grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2647:2: ( ( WCHAR ) )
                    {
                    // InternalStructuredTextParser.g:2647:2: ( ( WCHAR ) )
                    // InternalStructuredTextParser.g:2648:3: ( WCHAR )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getWCHAREnumLiteralDeclaration_3()); 
                    // InternalStructuredTextParser.g:2649:3: ( WCHAR )
                    // InternalStructuredTextParser.g:2649:4: WCHAR
                    {
                    match(input,WCHAR,FOLLOW_2); 

                    }

                     after(grammarAccess.getString_Type_NameAccess().getWCHAREnumLiteralDeclaration_3()); 

                    }


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__String_Type_Name__Alternatives"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group__0"
    // InternalStructuredTextParser.g:2657:1: rule__StructuredTextAlgorithm__Group__0 : rule__StructuredTextAlgorithm__Group__0__Impl rule__StructuredTextAlgorithm__Group__1 ;
    public final void rule__StructuredTextAlgorithm__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2661:1: ( rule__StructuredTextAlgorithm__Group__0__Impl rule__StructuredTextAlgorithm__Group__1 )
            // InternalStructuredTextParser.g:2662:2: rule__StructuredTextAlgorithm__Group__0__Impl rule__StructuredTextAlgorithm__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__StructuredTextAlgorithm__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group__0"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group__0__Impl"
    // InternalStructuredTextParser.g:2669:1: rule__StructuredTextAlgorithm__Group__0__Impl : ( () ) ;
    public final void rule__StructuredTextAlgorithm__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2673:1: ( ( () ) )
            // InternalStructuredTextParser.g:2674:1: ( () )
            {
            // InternalStructuredTextParser.g:2674:1: ( () )
            // InternalStructuredTextParser.g:2675:2: ()
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getStructuredTextAlgorithmAction_0()); 
            // InternalStructuredTextParser.g:2676:2: ()
            // InternalStructuredTextParser.g:2676:3: 
            {
            }

             after(grammarAccess.getStructuredTextAlgorithmAccess().getStructuredTextAlgorithmAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group__0__Impl"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group__1"
    // InternalStructuredTextParser.g:2684:1: rule__StructuredTextAlgorithm__Group__1 : rule__StructuredTextAlgorithm__Group__1__Impl rule__StructuredTextAlgorithm__Group__2 ;
    public final void rule__StructuredTextAlgorithm__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2688:1: ( rule__StructuredTextAlgorithm__Group__1__Impl rule__StructuredTextAlgorithm__Group__2 )
            // InternalStructuredTextParser.g:2689:2: rule__StructuredTextAlgorithm__Group__1__Impl rule__StructuredTextAlgorithm__Group__2
            {
            pushFollow(FOLLOW_3);
            rule__StructuredTextAlgorithm__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group__1"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group__1__Impl"
    // InternalStructuredTextParser.g:2696:1: rule__StructuredTextAlgorithm__Group__1__Impl : ( ( rule__StructuredTextAlgorithm__Group_1__0 )? ) ;
    public final void rule__StructuredTextAlgorithm__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2700:1: ( ( ( rule__StructuredTextAlgorithm__Group_1__0 )? ) )
            // InternalStructuredTextParser.g:2701:1: ( ( rule__StructuredTextAlgorithm__Group_1__0 )? )
            {
            // InternalStructuredTextParser.g:2701:1: ( ( rule__StructuredTextAlgorithm__Group_1__0 )? )
            // InternalStructuredTextParser.g:2702:2: ( rule__StructuredTextAlgorithm__Group_1__0 )?
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:2703:2: ( rule__StructuredTextAlgorithm__Group_1__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==VAR) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalStructuredTextParser.g:2703:3: rule__StructuredTextAlgorithm__Group_1__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group__1__Impl"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group__2"
    // InternalStructuredTextParser.g:2711:1: rule__StructuredTextAlgorithm__Group__2 : rule__StructuredTextAlgorithm__Group__2__Impl ;
    public final void rule__StructuredTextAlgorithm__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2715:1: ( rule__StructuredTextAlgorithm__Group__2__Impl )
            // InternalStructuredTextParser.g:2716:2: rule__StructuredTextAlgorithm__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group__2"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group__2__Impl"
    // InternalStructuredTextParser.g:2722:1: rule__StructuredTextAlgorithm__Group__2__Impl : ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) ) ;
    public final void rule__StructuredTextAlgorithm__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2726:1: ( ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) ) )
            // InternalStructuredTextParser.g:2727:1: ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) )
            {
            // InternalStructuredTextParser.g:2727:1: ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) )
            // InternalStructuredTextParser.g:2728:2: ( rule__StructuredTextAlgorithm__StatementsAssignment_2 )
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsAssignment_2()); 
            // InternalStructuredTextParser.g:2729:2: ( rule__StructuredTextAlgorithm__StatementsAssignment_2 )
            // InternalStructuredTextParser.g:2729:3: rule__StructuredTextAlgorithm__StatementsAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__StatementsAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group__2__Impl"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group_1__0"
    // InternalStructuredTextParser.g:2738:1: rule__StructuredTextAlgorithm__Group_1__0 : rule__StructuredTextAlgorithm__Group_1__0__Impl rule__StructuredTextAlgorithm__Group_1__1 ;
    public final void rule__StructuredTextAlgorithm__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2742:1: ( rule__StructuredTextAlgorithm__Group_1__0__Impl rule__StructuredTextAlgorithm__Group_1__1 )
            // InternalStructuredTextParser.g:2743:2: rule__StructuredTextAlgorithm__Group_1__0__Impl rule__StructuredTextAlgorithm__Group_1__1
            {
            pushFollow(FOLLOW_4);
            rule__StructuredTextAlgorithm__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group_1__0"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group_1__0__Impl"
    // InternalStructuredTextParser.g:2750:1: rule__StructuredTextAlgorithm__Group_1__0__Impl : ( VAR ) ;
    public final void rule__StructuredTextAlgorithm__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2754:1: ( ( VAR ) )
            // InternalStructuredTextParser.g:2755:1: ( VAR )
            {
            // InternalStructuredTextParser.g:2755:1: ( VAR )
            // InternalStructuredTextParser.g:2756:2: VAR
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getVARKeyword_1_0()); 
            match(input,VAR,FOLLOW_2); 
             after(grammarAccess.getStructuredTextAlgorithmAccess().getVARKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group_1__0__Impl"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group_1__1"
    // InternalStructuredTextParser.g:2765:1: rule__StructuredTextAlgorithm__Group_1__1 : rule__StructuredTextAlgorithm__Group_1__1__Impl rule__StructuredTextAlgorithm__Group_1__2 ;
    public final void rule__StructuredTextAlgorithm__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2769:1: ( rule__StructuredTextAlgorithm__Group_1__1__Impl rule__StructuredTextAlgorithm__Group_1__2 )
            // InternalStructuredTextParser.g:2770:2: rule__StructuredTextAlgorithm__Group_1__1__Impl rule__StructuredTextAlgorithm__Group_1__2
            {
            pushFollow(FOLLOW_4);
            rule__StructuredTextAlgorithm__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group_1__1"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group_1__1__Impl"
    // InternalStructuredTextParser.g:2777:1: rule__StructuredTextAlgorithm__Group_1__1__Impl : ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* ) ;
    public final void rule__StructuredTextAlgorithm__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2781:1: ( ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* ) )
            // InternalStructuredTextParser.g:2782:1: ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* )
            {
            // InternalStructuredTextParser.g:2782:1: ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* )
            // InternalStructuredTextParser.g:2783:2: ( rule__StructuredTextAlgorithm__Group_1_1__0 )*
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1_1()); 
            // InternalStructuredTextParser.g:2784:2: ( rule__StructuredTextAlgorithm__Group_1_1__0 )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==CONSTANT||LA31_0==RULE_ID) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalStructuredTextParser.g:2784:3: rule__StructuredTextAlgorithm__Group_1_1__0
            	    {
            	    pushFollow(FOLLOW_5);
            	    rule__StructuredTextAlgorithm__Group_1_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop31;
                }
            } while (true);

             after(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group_1__1__Impl"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group_1__2"
    // InternalStructuredTextParser.g:2792:1: rule__StructuredTextAlgorithm__Group_1__2 : rule__StructuredTextAlgorithm__Group_1__2__Impl ;
    public final void rule__StructuredTextAlgorithm__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2796:1: ( rule__StructuredTextAlgorithm__Group_1__2__Impl )
            // InternalStructuredTextParser.g:2797:2: rule__StructuredTextAlgorithm__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group_1__2"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group_1__2__Impl"
    // InternalStructuredTextParser.g:2803:1: rule__StructuredTextAlgorithm__Group_1__2__Impl : ( END_VAR ) ;
    public final void rule__StructuredTextAlgorithm__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2807:1: ( ( END_VAR ) )
            // InternalStructuredTextParser.g:2808:1: ( END_VAR )
            {
            // InternalStructuredTextParser.g:2808:1: ( END_VAR )
            // InternalStructuredTextParser.g:2809:2: END_VAR
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getEND_VARKeyword_1_2()); 
            match(input,END_VAR,FOLLOW_2); 
             after(grammarAccess.getStructuredTextAlgorithmAccess().getEND_VARKeyword_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group_1__2__Impl"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group_1_1__0"
    // InternalStructuredTextParser.g:2819:1: rule__StructuredTextAlgorithm__Group_1_1__0 : rule__StructuredTextAlgorithm__Group_1_1__0__Impl rule__StructuredTextAlgorithm__Group_1_1__1 ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2823:1: ( rule__StructuredTextAlgorithm__Group_1_1__0__Impl rule__StructuredTextAlgorithm__Group_1_1__1 )
            // InternalStructuredTextParser.g:2824:2: rule__StructuredTextAlgorithm__Group_1_1__0__Impl rule__StructuredTextAlgorithm__Group_1_1__1
            {
            pushFollow(FOLLOW_6);
            rule__StructuredTextAlgorithm__Group_1_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__Group_1_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group_1_1__0"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group_1_1__0__Impl"
    // InternalStructuredTextParser.g:2831:1: rule__StructuredTextAlgorithm__Group_1_1__0__Impl : ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) ) ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2835:1: ( ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) ) )
            // InternalStructuredTextParser.g:2836:1: ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) )
            {
            // InternalStructuredTextParser.g:2836:1: ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) )
            // InternalStructuredTextParser.g:2837:2: ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 )
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getLocalVariablesAssignment_1_1_0()); 
            // InternalStructuredTextParser.g:2838:2: ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 )
            // InternalStructuredTextParser.g:2838:3: rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0
            {
            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0();

            state._fsp--;


            }

             after(grammarAccess.getStructuredTextAlgorithmAccess().getLocalVariablesAssignment_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group_1_1__0__Impl"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group_1_1__1"
    // InternalStructuredTextParser.g:2846:1: rule__StructuredTextAlgorithm__Group_1_1__1 : rule__StructuredTextAlgorithm__Group_1_1__1__Impl ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2850:1: ( rule__StructuredTextAlgorithm__Group_1_1__1__Impl )
            // InternalStructuredTextParser.g:2851:2: rule__StructuredTextAlgorithm__Group_1_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__StructuredTextAlgorithm__Group_1_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group_1_1__1"


    // $ANTLR start "rule__StructuredTextAlgorithm__Group_1_1__1__Impl"
    // InternalStructuredTextParser.g:2857:1: rule__StructuredTextAlgorithm__Group_1_1__1__Impl : ( Semicolon ) ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2861:1: ( ( Semicolon ) )
            // InternalStructuredTextParser.g:2862:1: ( Semicolon )
            {
            // InternalStructuredTextParser.g:2862:1: ( Semicolon )
            // InternalStructuredTextParser.g:2863:2: Semicolon
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getSemicolonKeyword_1_1_1()); 
            match(input,Semicolon,FOLLOW_2); 
             after(grammarAccess.getStructuredTextAlgorithmAccess().getSemicolonKeyword_1_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__Group_1_1__1__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group__0"
    // InternalStructuredTextParser.g:2873:1: rule__Var_Decl_Local__Group__0 : rule__Var_Decl_Local__Group__0__Impl rule__Var_Decl_Local__Group__1 ;
    public final void rule__Var_Decl_Local__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2877:1: ( rule__Var_Decl_Local__Group__0__Impl rule__Var_Decl_Local__Group__1 )
            // InternalStructuredTextParser.g:2878:2: rule__Var_Decl_Local__Group__0__Impl rule__Var_Decl_Local__Group__1
            {
            pushFollow(FOLLOW_7);
            rule__Var_Decl_Local__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__0"


    // $ANTLR start "rule__Var_Decl_Local__Group__0__Impl"
    // InternalStructuredTextParser.g:2885:1: rule__Var_Decl_Local__Group__0__Impl : ( () ) ;
    public final void rule__Var_Decl_Local__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2889:1: ( ( () ) )
            // InternalStructuredTextParser.g:2890:1: ( () )
            {
            // InternalStructuredTextParser.g:2890:1: ( () )
            // InternalStructuredTextParser.g:2891:2: ()
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocalVariableAction_0()); 
            // InternalStructuredTextParser.g:2892:2: ()
            // InternalStructuredTextParser.g:2892:3: 
            {
            }

             after(grammarAccess.getVar_Decl_LocalAccess().getLocalVariableAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__0__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group__1"
    // InternalStructuredTextParser.g:2900:1: rule__Var_Decl_Local__Group__1 : rule__Var_Decl_Local__Group__1__Impl rule__Var_Decl_Local__Group__2 ;
    public final void rule__Var_Decl_Local__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2904:1: ( rule__Var_Decl_Local__Group__1__Impl rule__Var_Decl_Local__Group__2 )
            // InternalStructuredTextParser.g:2905:2: rule__Var_Decl_Local__Group__1__Impl rule__Var_Decl_Local__Group__2
            {
            pushFollow(FOLLOW_7);
            rule__Var_Decl_Local__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__1"


    // $ANTLR start "rule__Var_Decl_Local__Group__1__Impl"
    // InternalStructuredTextParser.g:2912:1: rule__Var_Decl_Local__Group__1__Impl : ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? ) ;
    public final void rule__Var_Decl_Local__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2916:1: ( ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? ) )
            // InternalStructuredTextParser.g:2917:1: ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? )
            {
            // InternalStructuredTextParser.g:2917:1: ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? )
            // InternalStructuredTextParser.g:2918:2: ( rule__Var_Decl_Local__ConstantAssignment_1 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getConstantAssignment_1()); 
            // InternalStructuredTextParser.g:2919:2: ( rule__Var_Decl_Local__ConstantAssignment_1 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==CONSTANT) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalStructuredTextParser.g:2919:3: rule__Var_Decl_Local__ConstantAssignment_1
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__1__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group__2"
    // InternalStructuredTextParser.g:2927:1: rule__Var_Decl_Local__Group__2 : rule__Var_Decl_Local__Group__2__Impl rule__Var_Decl_Local__Group__3 ;
    public final void rule__Var_Decl_Local__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2931:1: ( rule__Var_Decl_Local__Group__2__Impl rule__Var_Decl_Local__Group__3 )
            // InternalStructuredTextParser.g:2932:2: rule__Var_Decl_Local__Group__2__Impl rule__Var_Decl_Local__Group__3
            {
            pushFollow(FOLLOW_8);
            rule__Var_Decl_Local__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__2"


    // $ANTLR start "rule__Var_Decl_Local__Group__2__Impl"
    // InternalStructuredTextParser.g:2939:1: rule__Var_Decl_Local__Group__2__Impl : ( ( rule__Var_Decl_Local__NameAssignment_2 ) ) ;
    public final void rule__Var_Decl_Local__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2943:1: ( ( ( rule__Var_Decl_Local__NameAssignment_2 ) ) )
            // InternalStructuredTextParser.g:2944:1: ( ( rule__Var_Decl_Local__NameAssignment_2 ) )
            {
            // InternalStructuredTextParser.g:2944:1: ( ( rule__Var_Decl_Local__NameAssignment_2 ) )
            // InternalStructuredTextParser.g:2945:2: ( rule__Var_Decl_Local__NameAssignment_2 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getNameAssignment_2()); 
            // InternalStructuredTextParser.g:2946:2: ( rule__Var_Decl_Local__NameAssignment_2 )
            // InternalStructuredTextParser.g:2946:3: rule__Var_Decl_Local__NameAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__NameAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getVar_Decl_LocalAccess().getNameAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__2__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group__3"
    // InternalStructuredTextParser.g:2954:1: rule__Var_Decl_Local__Group__3 : rule__Var_Decl_Local__Group__3__Impl rule__Var_Decl_Local__Group__4 ;
    public final void rule__Var_Decl_Local__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2958:1: ( rule__Var_Decl_Local__Group__3__Impl rule__Var_Decl_Local__Group__4 )
            // InternalStructuredTextParser.g:2959:2: rule__Var_Decl_Local__Group__3__Impl rule__Var_Decl_Local__Group__4
            {
            pushFollow(FOLLOW_8);
            rule__Var_Decl_Local__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__3"


    // $ANTLR start "rule__Var_Decl_Local__Group__3__Impl"
    // InternalStructuredTextParser.g:2966:1: rule__Var_Decl_Local__Group__3__Impl : ( ( rule__Var_Decl_Local__Group_3__0 )? ) ;
    public final void rule__Var_Decl_Local__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2970:1: ( ( ( rule__Var_Decl_Local__Group_3__0 )? ) )
            // InternalStructuredTextParser.g:2971:1: ( ( rule__Var_Decl_Local__Group_3__0 )? )
            {
            // InternalStructuredTextParser.g:2971:1: ( ( rule__Var_Decl_Local__Group_3__0 )? )
            // InternalStructuredTextParser.g:2972:2: ( rule__Var_Decl_Local__Group_3__0 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getGroup_3()); 
            // InternalStructuredTextParser.g:2973:2: ( rule__Var_Decl_Local__Group_3__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==AT) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalStructuredTextParser.g:2973:3: rule__Var_Decl_Local__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Var_Decl_Local__Group_3__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getVar_Decl_LocalAccess().getGroup_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__3__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group__4"
    // InternalStructuredTextParser.g:2981:1: rule__Var_Decl_Local__Group__4 : rule__Var_Decl_Local__Group__4__Impl rule__Var_Decl_Local__Group__5 ;
    public final void rule__Var_Decl_Local__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2985:1: ( rule__Var_Decl_Local__Group__4__Impl rule__Var_Decl_Local__Group__5 )
            // InternalStructuredTextParser.g:2986:2: rule__Var_Decl_Local__Group__4__Impl rule__Var_Decl_Local__Group__5
            {
            pushFollow(FOLLOW_9);
            rule__Var_Decl_Local__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__4"


    // $ANTLR start "rule__Var_Decl_Local__Group__4__Impl"
    // InternalStructuredTextParser.g:2993:1: rule__Var_Decl_Local__Group__4__Impl : ( Colon ) ;
    public final void rule__Var_Decl_Local__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2997:1: ( ( Colon ) )
            // InternalStructuredTextParser.g:2998:1: ( Colon )
            {
            // InternalStructuredTextParser.g:2998:1: ( Colon )
            // InternalStructuredTextParser.g:2999:2: Colon
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getColonKeyword_4()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getVar_Decl_LocalAccess().getColonKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__4__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group__5"
    // InternalStructuredTextParser.g:3008:1: rule__Var_Decl_Local__Group__5 : rule__Var_Decl_Local__Group__5__Impl rule__Var_Decl_Local__Group__6 ;
    public final void rule__Var_Decl_Local__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3012:1: ( rule__Var_Decl_Local__Group__5__Impl rule__Var_Decl_Local__Group__6 )
            // InternalStructuredTextParser.g:3013:2: rule__Var_Decl_Local__Group__5__Impl rule__Var_Decl_Local__Group__6
            {
            pushFollow(FOLLOW_9);
            rule__Var_Decl_Local__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__5"


    // $ANTLR start "rule__Var_Decl_Local__Group__5__Impl"
    // InternalStructuredTextParser.g:3020:1: rule__Var_Decl_Local__Group__5__Impl : ( ( rule__Var_Decl_Local__Group_5__0 )? ) ;
    public final void rule__Var_Decl_Local__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3024:1: ( ( ( rule__Var_Decl_Local__Group_5__0 )? ) )
            // InternalStructuredTextParser.g:3025:1: ( ( rule__Var_Decl_Local__Group_5__0 )? )
            {
            // InternalStructuredTextParser.g:3025:1: ( ( rule__Var_Decl_Local__Group_5__0 )? )
            // InternalStructuredTextParser.g:3026:2: ( rule__Var_Decl_Local__Group_5__0 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getGroup_5()); 
            // InternalStructuredTextParser.g:3027:2: ( rule__Var_Decl_Local__Group_5__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==ARRAY) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalStructuredTextParser.g:3027:3: rule__Var_Decl_Local__Group_5__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__5__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group__6"
    // InternalStructuredTextParser.g:3035:1: rule__Var_Decl_Local__Group__6 : rule__Var_Decl_Local__Group__6__Impl rule__Var_Decl_Local__Group__7 ;
    public final void rule__Var_Decl_Local__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3039:1: ( rule__Var_Decl_Local__Group__6__Impl rule__Var_Decl_Local__Group__7 )
            // InternalStructuredTextParser.g:3040:2: rule__Var_Decl_Local__Group__6__Impl rule__Var_Decl_Local__Group__7
            {
            pushFollow(FOLLOW_10);
            rule__Var_Decl_Local__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__6"


    // $ANTLR start "rule__Var_Decl_Local__Group__6__Impl"
    // InternalStructuredTextParser.g:3047:1: rule__Var_Decl_Local__Group__6__Impl : ( ( rule__Var_Decl_Local__TypeAssignment_6 ) ) ;
    public final void rule__Var_Decl_Local__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3051:1: ( ( ( rule__Var_Decl_Local__TypeAssignment_6 ) ) )
            // InternalStructuredTextParser.g:3052:1: ( ( rule__Var_Decl_Local__TypeAssignment_6 ) )
            {
            // InternalStructuredTextParser.g:3052:1: ( ( rule__Var_Decl_Local__TypeAssignment_6 ) )
            // InternalStructuredTextParser.g:3053:2: ( rule__Var_Decl_Local__TypeAssignment_6 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getTypeAssignment_6()); 
            // InternalStructuredTextParser.g:3054:2: ( rule__Var_Decl_Local__TypeAssignment_6 )
            // InternalStructuredTextParser.g:3054:3: rule__Var_Decl_Local__TypeAssignment_6
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__TypeAssignment_6();

            state._fsp--;


            }

             after(grammarAccess.getVar_Decl_LocalAccess().getTypeAssignment_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__6__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group__7"
    // InternalStructuredTextParser.g:3062:1: rule__Var_Decl_Local__Group__7 : rule__Var_Decl_Local__Group__7__Impl ;
    public final void rule__Var_Decl_Local__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3066:1: ( rule__Var_Decl_Local__Group__7__Impl )
            // InternalStructuredTextParser.g:3067:2: rule__Var_Decl_Local__Group__7__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group__7__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__7"


    // $ANTLR start "rule__Var_Decl_Local__Group__7__Impl"
    // InternalStructuredTextParser.g:3073:1: rule__Var_Decl_Local__Group__7__Impl : ( ( rule__Var_Decl_Local__Group_7__0 )? ) ;
    public final void rule__Var_Decl_Local__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3077:1: ( ( ( rule__Var_Decl_Local__Group_7__0 )? ) )
            // InternalStructuredTextParser.g:3078:1: ( ( rule__Var_Decl_Local__Group_7__0 )? )
            {
            // InternalStructuredTextParser.g:3078:1: ( ( rule__Var_Decl_Local__Group_7__0 )? )
            // InternalStructuredTextParser.g:3079:2: ( rule__Var_Decl_Local__Group_7__0 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getGroup_7()); 
            // InternalStructuredTextParser.g:3080:2: ( rule__Var_Decl_Local__Group_7__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==ColonEqualsSign) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalStructuredTextParser.g:3080:3: rule__Var_Decl_Local__Group_7__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Var_Decl_Local__Group_7__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getVar_Decl_LocalAccess().getGroup_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group__7__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_3__0"
    // InternalStructuredTextParser.g:3089:1: rule__Var_Decl_Local__Group_3__0 : rule__Var_Decl_Local__Group_3__0__Impl rule__Var_Decl_Local__Group_3__1 ;
    public final void rule__Var_Decl_Local__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3093:1: ( rule__Var_Decl_Local__Group_3__0__Impl rule__Var_Decl_Local__Group_3__1 )
            // InternalStructuredTextParser.g:3094:2: rule__Var_Decl_Local__Group_3__0__Impl rule__Var_Decl_Local__Group_3__1
            {
            pushFollow(FOLLOW_11);
            rule__Var_Decl_Local__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_3__0"


    // $ANTLR start "rule__Var_Decl_Local__Group_3__0__Impl"
    // InternalStructuredTextParser.g:3101:1: rule__Var_Decl_Local__Group_3__0__Impl : ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) ) ;
    public final void rule__Var_Decl_Local__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3105:1: ( ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) ) )
            // InternalStructuredTextParser.g:3106:1: ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) )
            {
            // InternalStructuredTextParser.g:3106:1: ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) )
            // InternalStructuredTextParser.g:3107:2: ( rule__Var_Decl_Local__LocatedAssignment_3_0 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocatedAssignment_3_0()); 
            // InternalStructuredTextParser.g:3108:2: ( rule__Var_Decl_Local__LocatedAssignment_3_0 )
            // InternalStructuredTextParser.g:3108:3: rule__Var_Decl_Local__LocatedAssignment_3_0
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__LocatedAssignment_3_0();

            state._fsp--;


            }

             after(grammarAccess.getVar_Decl_LocalAccess().getLocatedAssignment_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_3__0__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_3__1"
    // InternalStructuredTextParser.g:3116:1: rule__Var_Decl_Local__Group_3__1 : rule__Var_Decl_Local__Group_3__1__Impl ;
    public final void rule__Var_Decl_Local__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3120:1: ( rule__Var_Decl_Local__Group_3__1__Impl )
            // InternalStructuredTextParser.g:3121:2: rule__Var_Decl_Local__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_3__1"


    // $ANTLR start "rule__Var_Decl_Local__Group_3__1__Impl"
    // InternalStructuredTextParser.g:3127:1: rule__Var_Decl_Local__Group_3__1__Impl : ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) ) ;
    public final void rule__Var_Decl_Local__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3131:1: ( ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) ) )
            // InternalStructuredTextParser.g:3132:1: ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) )
            {
            // InternalStructuredTextParser.g:3132:1: ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) )
            // InternalStructuredTextParser.g:3133:2: ( rule__Var_Decl_Local__LocationAssignment_3_1 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocationAssignment_3_1()); 
            // InternalStructuredTextParser.g:3134:2: ( rule__Var_Decl_Local__LocationAssignment_3_1 )
            // InternalStructuredTextParser.g:3134:3: rule__Var_Decl_Local__LocationAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__LocationAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getVar_Decl_LocalAccess().getLocationAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_3__1__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__0"
    // InternalStructuredTextParser.g:3143:1: rule__Var_Decl_Local__Group_5__0 : rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1 ;
    public final void rule__Var_Decl_Local__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3147:1: ( rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1 )
            // InternalStructuredTextParser.g:3148:2: rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1
            {
            pushFollow(FOLLOW_12);
            rule__Var_Decl_Local__Group_5__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_5__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__0"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__0__Impl"
    // InternalStructuredTextParser.g:3155:1: rule__Var_Decl_Local__Group_5__0__Impl : ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) ) ;
    public final void rule__Var_Decl_Local__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3159:1: ( ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) ) )
            // InternalStructuredTextParser.g:3160:1: ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) )
            {
            // InternalStructuredTextParser.g:3160:1: ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) )
            // InternalStructuredTextParser.g:3161:2: ( rule__Var_Decl_Local__ArrayAssignment_5_0 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayAssignment_5_0()); 
            // InternalStructuredTextParser.g:3162:2: ( rule__Var_Decl_Local__ArrayAssignment_5_0 )
            // InternalStructuredTextParser.g:3162:3: rule__Var_Decl_Local__ArrayAssignment_5_0
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__ArrayAssignment_5_0();

            state._fsp--;


            }

             after(grammarAccess.getVar_Decl_LocalAccess().getArrayAssignment_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__0__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__1"
    // InternalStructuredTextParser.g:3170:1: rule__Var_Decl_Local__Group_5__1 : rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2 ;
    public final void rule__Var_Decl_Local__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3174:1: ( rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2 )
            // InternalStructuredTextParser.g:3175:2: rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2
            {
            pushFollow(FOLLOW_13);
            rule__Var_Decl_Local__Group_5__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_5__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__1"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__1__Impl"
    // InternalStructuredTextParser.g:3182:1: rule__Var_Decl_Local__Group_5__1__Impl : ( LeftSquareBracket ) ;
    public final void rule__Var_Decl_Local__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3186:1: ( ( LeftSquareBracket ) )
            // InternalStructuredTextParser.g:3187:1: ( LeftSquareBracket )
            {
            // InternalStructuredTextParser.g:3187:1: ( LeftSquareBracket )
            // InternalStructuredTextParser.g:3188:2: LeftSquareBracket
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLeftSquareBracketKeyword_5_1()); 
            match(input,LeftSquareBracket,FOLLOW_2); 
             after(grammarAccess.getVar_Decl_LocalAccess().getLeftSquareBracketKeyword_5_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__1__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__2"
    // InternalStructuredTextParser.g:3197:1: rule__Var_Decl_Local__Group_5__2 : rule__Var_Decl_Local__Group_5__2__Impl rule__Var_Decl_Local__Group_5__3 ;
    public final void rule__Var_Decl_Local__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3201:1: ( rule__Var_Decl_Local__Group_5__2__Impl rule__Var_Decl_Local__Group_5__3 )
            // InternalStructuredTextParser.g:3202:2: rule__Var_Decl_Local__Group_5__2__Impl rule__Var_Decl_Local__Group_5__3
            {
            pushFollow(FOLLOW_14);
            rule__Var_Decl_Local__Group_5__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_5__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__2"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__2__Impl"
    // InternalStructuredTextParser.g:3209:1: rule__Var_Decl_Local__Group_5__2__Impl : ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) ) ;
    public final void rule__Var_Decl_Local__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3213:1: ( ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) ) )
            // InternalStructuredTextParser.g:3214:1: ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) )
            {
            // InternalStructuredTextParser.g:3214:1: ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) )
            // InternalStructuredTextParser.g:3215:2: ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayStartAssignment_5_2()); 
            // InternalStructuredTextParser.g:3216:2: ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 )
            // InternalStructuredTextParser.g:3216:3: rule__Var_Decl_Local__ArrayStartAssignment_5_2
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__ArrayStartAssignment_5_2();

            state._fsp--;


            }

             after(grammarAccess.getVar_Decl_LocalAccess().getArrayStartAssignment_5_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__2__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__3"
    // InternalStructuredTextParser.g:3224:1: rule__Var_Decl_Local__Group_5__3 : rule__Var_Decl_Local__Group_5__3__Impl rule__Var_Decl_Local__Group_5__4 ;
    public final void rule__Var_Decl_Local__Group_5__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3228:1: ( rule__Var_Decl_Local__Group_5__3__Impl rule__Var_Decl_Local__Group_5__4 )
            // InternalStructuredTextParser.g:3229:2: rule__Var_Decl_Local__Group_5__3__Impl rule__Var_Decl_Local__Group_5__4
            {
            pushFollow(FOLLOW_13);
            rule__Var_Decl_Local__Group_5__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_5__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__3"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__3__Impl"
    // InternalStructuredTextParser.g:3236:1: rule__Var_Decl_Local__Group_5__3__Impl : ( FullStopFullStop ) ;
    public final void rule__Var_Decl_Local__Group_5__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3240:1: ( ( FullStopFullStop ) )
            // InternalStructuredTextParser.g:3241:1: ( FullStopFullStop )
            {
            // InternalStructuredTextParser.g:3241:1: ( FullStopFullStop )
            // InternalStructuredTextParser.g:3242:2: FullStopFullStop
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getFullStopFullStopKeyword_5_3()); 
            match(input,FullStopFullStop,FOLLOW_2); 
             after(grammarAccess.getVar_Decl_LocalAccess().getFullStopFullStopKeyword_5_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__3__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__4"
    // InternalStructuredTextParser.g:3251:1: rule__Var_Decl_Local__Group_5__4 : rule__Var_Decl_Local__Group_5__4__Impl rule__Var_Decl_Local__Group_5__5 ;
    public final void rule__Var_Decl_Local__Group_5__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3255:1: ( rule__Var_Decl_Local__Group_5__4__Impl rule__Var_Decl_Local__Group_5__5 )
            // InternalStructuredTextParser.g:3256:2: rule__Var_Decl_Local__Group_5__4__Impl rule__Var_Decl_Local__Group_5__5
            {
            pushFollow(FOLLOW_15);
            rule__Var_Decl_Local__Group_5__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_5__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__4"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__4__Impl"
    // InternalStructuredTextParser.g:3263:1: rule__Var_Decl_Local__Group_5__4__Impl : ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) ) ;
    public final void rule__Var_Decl_Local__Group_5__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3267:1: ( ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) ) )
            // InternalStructuredTextParser.g:3268:1: ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) )
            {
            // InternalStructuredTextParser.g:3268:1: ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) )
            // InternalStructuredTextParser.g:3269:2: ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayStopAssignment_5_4()); 
            // InternalStructuredTextParser.g:3270:2: ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 )
            // InternalStructuredTextParser.g:3270:3: rule__Var_Decl_Local__ArrayStopAssignment_5_4
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__ArrayStopAssignment_5_4();

            state._fsp--;


            }

             after(grammarAccess.getVar_Decl_LocalAccess().getArrayStopAssignment_5_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__4__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__5"
    // InternalStructuredTextParser.g:3278:1: rule__Var_Decl_Local__Group_5__5 : rule__Var_Decl_Local__Group_5__5__Impl rule__Var_Decl_Local__Group_5__6 ;
    public final void rule__Var_Decl_Local__Group_5__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3282:1: ( rule__Var_Decl_Local__Group_5__5__Impl rule__Var_Decl_Local__Group_5__6 )
            // InternalStructuredTextParser.g:3283:2: rule__Var_Decl_Local__Group_5__5__Impl rule__Var_Decl_Local__Group_5__6
            {
            pushFollow(FOLLOW_16);
            rule__Var_Decl_Local__Group_5__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_5__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__5"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__5__Impl"
    // InternalStructuredTextParser.g:3290:1: rule__Var_Decl_Local__Group_5__5__Impl : ( RightSquareBracket ) ;
    public final void rule__Var_Decl_Local__Group_5__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3294:1: ( ( RightSquareBracket ) )
            // InternalStructuredTextParser.g:3295:1: ( RightSquareBracket )
            {
            // InternalStructuredTextParser.g:3295:1: ( RightSquareBracket )
            // InternalStructuredTextParser.g:3296:2: RightSquareBracket
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getRightSquareBracketKeyword_5_5()); 
            match(input,RightSquareBracket,FOLLOW_2); 
             after(grammarAccess.getVar_Decl_LocalAccess().getRightSquareBracketKeyword_5_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__5__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__6"
    // InternalStructuredTextParser.g:3305:1: rule__Var_Decl_Local__Group_5__6 : rule__Var_Decl_Local__Group_5__6__Impl ;
    public final void rule__Var_Decl_Local__Group_5__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3309:1: ( rule__Var_Decl_Local__Group_5__6__Impl )
            // InternalStructuredTextParser.g:3310:2: rule__Var_Decl_Local__Group_5__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_5__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__6"


    // $ANTLR start "rule__Var_Decl_Local__Group_5__6__Impl"
    // InternalStructuredTextParser.g:3316:1: rule__Var_Decl_Local__Group_5__6__Impl : ( OF ) ;
    public final void rule__Var_Decl_Local__Group_5__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3320:1: ( ( OF ) )
            // InternalStructuredTextParser.g:3321:1: ( OF )
            {
            // InternalStructuredTextParser.g:3321:1: ( OF )
            // InternalStructuredTextParser.g:3322:2: OF
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getOFKeyword_5_6()); 
            match(input,OF,FOLLOW_2); 
             after(grammarAccess.getVar_Decl_LocalAccess().getOFKeyword_5_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_5__6__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_7__0"
    // InternalStructuredTextParser.g:3332:1: rule__Var_Decl_Local__Group_7__0 : rule__Var_Decl_Local__Group_7__0__Impl rule__Var_Decl_Local__Group_7__1 ;
    public final void rule__Var_Decl_Local__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3336:1: ( rule__Var_Decl_Local__Group_7__0__Impl rule__Var_Decl_Local__Group_7__1 )
            // InternalStructuredTextParser.g:3337:2: rule__Var_Decl_Local__Group_7__0__Impl rule__Var_Decl_Local__Group_7__1
            {
            pushFollow(FOLLOW_17);
            rule__Var_Decl_Local__Group_7__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_7__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_7__0"


    // $ANTLR start "rule__Var_Decl_Local__Group_7__0__Impl"
    // InternalStructuredTextParser.g:3344:1: rule__Var_Decl_Local__Group_7__0__Impl : ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) ) ;
    public final void rule__Var_Decl_Local__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3348:1: ( ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) ) )
            // InternalStructuredTextParser.g:3349:1: ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) )
            {
            // InternalStructuredTextParser.g:3349:1: ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) )
            // InternalStructuredTextParser.g:3350:2: ( rule__Var_Decl_Local__InitalizedAssignment_7_0 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitalizedAssignment_7_0()); 
            // InternalStructuredTextParser.g:3351:2: ( rule__Var_Decl_Local__InitalizedAssignment_7_0 )
            // InternalStructuredTextParser.g:3351:3: rule__Var_Decl_Local__InitalizedAssignment_7_0
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__InitalizedAssignment_7_0();

            state._fsp--;


            }

             after(grammarAccess.getVar_Decl_LocalAccess().getInitalizedAssignment_7_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_7__0__Impl"


    // $ANTLR start "rule__Var_Decl_Local__Group_7__1"
    // InternalStructuredTextParser.g:3359:1: rule__Var_Decl_Local__Group_7__1 : rule__Var_Decl_Local__Group_7__1__Impl ;
    public final void rule__Var_Decl_Local__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3363:1: ( rule__Var_Decl_Local__Group_7__1__Impl )
            // InternalStructuredTextParser.g:3364:2: rule__Var_Decl_Local__Group_7__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__Group_7__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_7__1"


    // $ANTLR start "rule__Var_Decl_Local__Group_7__1__Impl"
    // InternalStructuredTextParser.g:3370:1: rule__Var_Decl_Local__Group_7__1__Impl : ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) ) ;
    public final void rule__Var_Decl_Local__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3374:1: ( ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) ) )
            // InternalStructuredTextParser.g:3375:1: ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) )
            {
            // InternalStructuredTextParser.g:3375:1: ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) )
            // InternalStructuredTextParser.g:3376:2: ( rule__Var_Decl_Local__InitialValueAssignment_7_1 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitialValueAssignment_7_1()); 
            // InternalStructuredTextParser.g:3377:2: ( rule__Var_Decl_Local__InitialValueAssignment_7_1 )
            // InternalStructuredTextParser.g:3377:3: rule__Var_Decl_Local__InitialValueAssignment_7_1
            {
            pushFollow(FOLLOW_2);
            rule__Var_Decl_Local__InitialValueAssignment_7_1();

            state._fsp--;


            }

             after(grammarAccess.getVar_Decl_LocalAccess().getInitialValueAssignment_7_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__Group_7__1__Impl"


    // $ANTLR start "rule__Stmt_List__Group__0"
    // InternalStructuredTextParser.g:3386:1: rule__Stmt_List__Group__0 : rule__Stmt_List__Group__0__Impl rule__Stmt_List__Group__1 ;
    public final void rule__Stmt_List__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3390:1: ( rule__Stmt_List__Group__0__Impl rule__Stmt_List__Group__1 )
            // InternalStructuredTextParser.g:3391:2: rule__Stmt_List__Group__0__Impl rule__Stmt_List__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Stmt_List__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Stmt_List__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmt_List__Group__0"


    // $ANTLR start "rule__Stmt_List__Group__0__Impl"
    // InternalStructuredTextParser.g:3398:1: rule__Stmt_List__Group__0__Impl : ( () ) ;
    public final void rule__Stmt_List__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3402:1: ( ( () ) )
            // InternalStructuredTextParser.g:3403:1: ( () )
            {
            // InternalStructuredTextParser.g:3403:1: ( () )
            // InternalStructuredTextParser.g:3404:2: ()
            {
             before(grammarAccess.getStmt_ListAccess().getStatementListAction_0()); 
            // InternalStructuredTextParser.g:3405:2: ()
            // InternalStructuredTextParser.g:3405:3: 
            {
            }

             after(grammarAccess.getStmt_ListAccess().getStatementListAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmt_List__Group__0__Impl"


    // $ANTLR start "rule__Stmt_List__Group__1"
    // InternalStructuredTextParser.g:3413:1: rule__Stmt_List__Group__1 : rule__Stmt_List__Group__1__Impl ;
    public final void rule__Stmt_List__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3417:1: ( rule__Stmt_List__Group__1__Impl )
            // InternalStructuredTextParser.g:3418:2: rule__Stmt_List__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Stmt_List__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmt_List__Group__1"


    // $ANTLR start "rule__Stmt_List__Group__1__Impl"
    // InternalStructuredTextParser.g:3424:1: rule__Stmt_List__Group__1__Impl : ( ( rule__Stmt_List__Group_1__0 )* ) ;
    public final void rule__Stmt_List__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3428:1: ( ( ( rule__Stmt_List__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:3429:1: ( ( rule__Stmt_List__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:3429:1: ( ( rule__Stmt_List__Group_1__0 )* )
            // InternalStructuredTextParser.g:3430:2: ( rule__Stmt_List__Group_1__0 )*
            {
             before(grammarAccess.getStmt_ListAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:3431:2: ( rule__Stmt_List__Group_1__0 )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==CONTINUE||(LA36_0>=REPEAT && LA36_0<=RETURN)||LA36_0==SUPER||LA36_0==WHILE||LA36_0==CASE||LA36_0==EXIT||LA36_0==TIME||LA36_0==FOR||(LA36_0>=DT && LA36_0<=LT)||LA36_0==Semicolon||LA36_0==T||LA36_0==RULE_ID) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalStructuredTextParser.g:3431:3: rule__Stmt_List__Group_1__0
            	    {
            	    pushFollow(FOLLOW_18);
            	    rule__Stmt_List__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop36;
                }
            } while (true);

             after(grammarAccess.getStmt_ListAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmt_List__Group__1__Impl"


    // $ANTLR start "rule__Stmt_List__Group_1__0"
    // InternalStructuredTextParser.g:3440:1: rule__Stmt_List__Group_1__0 : rule__Stmt_List__Group_1__0__Impl rule__Stmt_List__Group_1__1 ;
    public final void rule__Stmt_List__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3444:1: ( rule__Stmt_List__Group_1__0__Impl rule__Stmt_List__Group_1__1 )
            // InternalStructuredTextParser.g:3445:2: rule__Stmt_List__Group_1__0__Impl rule__Stmt_List__Group_1__1
            {
            pushFollow(FOLLOW_19);
            rule__Stmt_List__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Stmt_List__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmt_List__Group_1__0"


    // $ANTLR start "rule__Stmt_List__Group_1__0__Impl"
    // InternalStructuredTextParser.g:3452:1: rule__Stmt_List__Group_1__0__Impl : ( ( rule__Stmt_List__StatementsAssignment_1_0 )? ) ;
    public final void rule__Stmt_List__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3456:1: ( ( ( rule__Stmt_List__StatementsAssignment_1_0 )? ) )
            // InternalStructuredTextParser.g:3457:1: ( ( rule__Stmt_List__StatementsAssignment_1_0 )? )
            {
            // InternalStructuredTextParser.g:3457:1: ( ( rule__Stmt_List__StatementsAssignment_1_0 )? )
            // InternalStructuredTextParser.g:3458:2: ( rule__Stmt_List__StatementsAssignment_1_0 )?
            {
             before(grammarAccess.getStmt_ListAccess().getStatementsAssignment_1_0()); 
            // InternalStructuredTextParser.g:3459:2: ( rule__Stmt_List__StatementsAssignment_1_0 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==CONTINUE||(LA37_0>=REPEAT && LA37_0<=RETURN)||LA37_0==SUPER||LA37_0==WHILE||LA37_0==CASE||LA37_0==EXIT||LA37_0==TIME||LA37_0==FOR||(LA37_0>=DT && LA37_0<=LT)||LA37_0==T||LA37_0==RULE_ID) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalStructuredTextParser.g:3459:3: rule__Stmt_List__StatementsAssignment_1_0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmt_List__Group_1__0__Impl"


    // $ANTLR start "rule__Stmt_List__Group_1__1"
    // InternalStructuredTextParser.g:3467:1: rule__Stmt_List__Group_1__1 : rule__Stmt_List__Group_1__1__Impl ;
    public final void rule__Stmt_List__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3471:1: ( rule__Stmt_List__Group_1__1__Impl )
            // InternalStructuredTextParser.g:3472:2: rule__Stmt_List__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Stmt_List__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmt_List__Group_1__1"


    // $ANTLR start "rule__Stmt_List__Group_1__1__Impl"
    // InternalStructuredTextParser.g:3478:1: rule__Stmt_List__Group_1__1__Impl : ( Semicolon ) ;
    public final void rule__Stmt_List__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3482:1: ( ( Semicolon ) )
            // InternalStructuredTextParser.g:3483:1: ( Semicolon )
            {
            // InternalStructuredTextParser.g:3483:1: ( Semicolon )
            // InternalStructuredTextParser.g:3484:2: Semicolon
            {
             before(grammarAccess.getStmt_ListAccess().getSemicolonKeyword_1_1()); 
            match(input,Semicolon,FOLLOW_2); 
             after(grammarAccess.getStmt_ListAccess().getSemicolonKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmt_List__Group_1__1__Impl"


    // $ANTLR start "rule__Assign_Stmt__Group__0"
    // InternalStructuredTextParser.g:3494:1: rule__Assign_Stmt__Group__0 : rule__Assign_Stmt__Group__0__Impl rule__Assign_Stmt__Group__1 ;
    public final void rule__Assign_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3498:1: ( rule__Assign_Stmt__Group__0__Impl rule__Assign_Stmt__Group__1 )
            // InternalStructuredTextParser.g:3499:2: rule__Assign_Stmt__Group__0__Impl rule__Assign_Stmt__Group__1
            {
            pushFollow(FOLLOW_10);
            rule__Assign_Stmt__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Assign_Stmt__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assign_Stmt__Group__0"


    // $ANTLR start "rule__Assign_Stmt__Group__0__Impl"
    // InternalStructuredTextParser.g:3506:1: rule__Assign_Stmt__Group__0__Impl : ( ( rule__Assign_Stmt__VariableAssignment_0 ) ) ;
    public final void rule__Assign_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3510:1: ( ( ( rule__Assign_Stmt__VariableAssignment_0 ) ) )
            // InternalStructuredTextParser.g:3511:1: ( ( rule__Assign_Stmt__VariableAssignment_0 ) )
            {
            // InternalStructuredTextParser.g:3511:1: ( ( rule__Assign_Stmt__VariableAssignment_0 ) )
            // InternalStructuredTextParser.g:3512:2: ( rule__Assign_Stmt__VariableAssignment_0 )
            {
             before(grammarAccess.getAssign_StmtAccess().getVariableAssignment_0()); 
            // InternalStructuredTextParser.g:3513:2: ( rule__Assign_Stmt__VariableAssignment_0 )
            // InternalStructuredTextParser.g:3513:3: rule__Assign_Stmt__VariableAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Assign_Stmt__VariableAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getAssign_StmtAccess().getVariableAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assign_Stmt__Group__0__Impl"


    // $ANTLR start "rule__Assign_Stmt__Group__1"
    // InternalStructuredTextParser.g:3521:1: rule__Assign_Stmt__Group__1 : rule__Assign_Stmt__Group__1__Impl rule__Assign_Stmt__Group__2 ;
    public final void rule__Assign_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3525:1: ( rule__Assign_Stmt__Group__1__Impl rule__Assign_Stmt__Group__2 )
            // InternalStructuredTextParser.g:3526:2: rule__Assign_Stmt__Group__1__Impl rule__Assign_Stmt__Group__2
            {
            pushFollow(FOLLOW_20);
            rule__Assign_Stmt__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Assign_Stmt__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assign_Stmt__Group__1"


    // $ANTLR start "rule__Assign_Stmt__Group__1__Impl"
    // InternalStructuredTextParser.g:3533:1: rule__Assign_Stmt__Group__1__Impl : ( ColonEqualsSign ) ;
    public final void rule__Assign_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3537:1: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:3538:1: ( ColonEqualsSign )
            {
            // InternalStructuredTextParser.g:3538:1: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:3539:2: ColonEqualsSign
            {
             before(grammarAccess.getAssign_StmtAccess().getColonEqualsSignKeyword_1()); 
            match(input,ColonEqualsSign,FOLLOW_2); 
             after(grammarAccess.getAssign_StmtAccess().getColonEqualsSignKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assign_Stmt__Group__1__Impl"


    // $ANTLR start "rule__Assign_Stmt__Group__2"
    // InternalStructuredTextParser.g:3548:1: rule__Assign_Stmt__Group__2 : rule__Assign_Stmt__Group__2__Impl ;
    public final void rule__Assign_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3552:1: ( rule__Assign_Stmt__Group__2__Impl )
            // InternalStructuredTextParser.g:3553:2: rule__Assign_Stmt__Group__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Assign_Stmt__Group__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assign_Stmt__Group__2"


    // $ANTLR start "rule__Assign_Stmt__Group__2__Impl"
    // InternalStructuredTextParser.g:3559:1: rule__Assign_Stmt__Group__2__Impl : ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) ) ;
    public final void rule__Assign_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3563:1: ( ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) ) )
            // InternalStructuredTextParser.g:3564:1: ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) )
            {
            // InternalStructuredTextParser.g:3564:1: ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) )
            // InternalStructuredTextParser.g:3565:2: ( rule__Assign_Stmt__ExpressionAssignment_2 )
            {
             before(grammarAccess.getAssign_StmtAccess().getExpressionAssignment_2()); 
            // InternalStructuredTextParser.g:3566:2: ( rule__Assign_Stmt__ExpressionAssignment_2 )
            // InternalStructuredTextParser.g:3566:3: rule__Assign_Stmt__ExpressionAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__Assign_Stmt__ExpressionAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getAssign_StmtAccess().getExpressionAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assign_Stmt__Group__2__Impl"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__0"
    // InternalStructuredTextParser.g:3575:1: rule__Subprog_Ctrl_Stmt__Group_1__0 : rule__Subprog_Ctrl_Stmt__Group_1__0__Impl rule__Subprog_Ctrl_Stmt__Group_1__1 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3579:1: ( rule__Subprog_Ctrl_Stmt__Group_1__0__Impl rule__Subprog_Ctrl_Stmt__Group_1__1 )
            // InternalStructuredTextParser.g:3580:2: rule__Subprog_Ctrl_Stmt__Group_1__0__Impl rule__Subprog_Ctrl_Stmt__Group_1__1
            {
            pushFollow(FOLLOW_21);
            rule__Subprog_Ctrl_Stmt__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__0"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__0__Impl"
    // InternalStructuredTextParser.g:3587:1: rule__Subprog_Ctrl_Stmt__Group_1__0__Impl : ( () ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3591:1: ( ( () ) )
            // InternalStructuredTextParser.g:3592:1: ( () )
            {
            // InternalStructuredTextParser.g:3592:1: ( () )
            // InternalStructuredTextParser.g:3593:2: ()
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getSuperStatementAction_1_0()); 
            // InternalStructuredTextParser.g:3594:2: ()
            // InternalStructuredTextParser.g:3594:3: 
            {
            }

             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getSuperStatementAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__0__Impl"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__1"
    // InternalStructuredTextParser.g:3602:1: rule__Subprog_Ctrl_Stmt__Group_1__1 : rule__Subprog_Ctrl_Stmt__Group_1__1__Impl rule__Subprog_Ctrl_Stmt__Group_1__2 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3606:1: ( rule__Subprog_Ctrl_Stmt__Group_1__1__Impl rule__Subprog_Ctrl_Stmt__Group_1__2 )
            // InternalStructuredTextParser.g:3607:2: rule__Subprog_Ctrl_Stmt__Group_1__1__Impl rule__Subprog_Ctrl_Stmt__Group_1__2
            {
            pushFollow(FOLLOW_22);
            rule__Subprog_Ctrl_Stmt__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__1"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__1__Impl"
    // InternalStructuredTextParser.g:3614:1: rule__Subprog_Ctrl_Stmt__Group_1__1__Impl : ( SUPER ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3618:1: ( ( SUPER ) )
            // InternalStructuredTextParser.g:3619:1: ( SUPER )
            {
            // InternalStructuredTextParser.g:3619:1: ( SUPER )
            // InternalStructuredTextParser.g:3620:2: SUPER
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getSUPERKeyword_1_1()); 
            match(input,SUPER,FOLLOW_2); 
             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getSUPERKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__1__Impl"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__2"
    // InternalStructuredTextParser.g:3629:1: rule__Subprog_Ctrl_Stmt__Group_1__2 : rule__Subprog_Ctrl_Stmt__Group_1__2__Impl rule__Subprog_Ctrl_Stmt__Group_1__3 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3633:1: ( rule__Subprog_Ctrl_Stmt__Group_1__2__Impl rule__Subprog_Ctrl_Stmt__Group_1__3 )
            // InternalStructuredTextParser.g:3634:2: rule__Subprog_Ctrl_Stmt__Group_1__2__Impl rule__Subprog_Ctrl_Stmt__Group_1__3
            {
            pushFollow(FOLLOW_23);
            rule__Subprog_Ctrl_Stmt__Group_1__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_1__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__2"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__2__Impl"
    // InternalStructuredTextParser.g:3641:1: rule__Subprog_Ctrl_Stmt__Group_1__2__Impl : ( LeftParenthesis ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3645:1: ( ( LeftParenthesis ) )
            // InternalStructuredTextParser.g:3646:1: ( LeftParenthesis )
            {
            // InternalStructuredTextParser.g:3646:1: ( LeftParenthesis )
            // InternalStructuredTextParser.g:3647:2: LeftParenthesis
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getLeftParenthesisKeyword_1_2()); 
            match(input,LeftParenthesis,FOLLOW_2); 
             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getLeftParenthesisKeyword_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__2__Impl"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__3"
    // InternalStructuredTextParser.g:3656:1: rule__Subprog_Ctrl_Stmt__Group_1__3 : rule__Subprog_Ctrl_Stmt__Group_1__3__Impl ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3660:1: ( rule__Subprog_Ctrl_Stmt__Group_1__3__Impl )
            // InternalStructuredTextParser.g:3661:2: rule__Subprog_Ctrl_Stmt__Group_1__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_1__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__3"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_1__3__Impl"
    // InternalStructuredTextParser.g:3667:1: rule__Subprog_Ctrl_Stmt__Group_1__3__Impl : ( RightParenthesis ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3671:1: ( ( RightParenthesis ) )
            // InternalStructuredTextParser.g:3672:1: ( RightParenthesis )
            {
            // InternalStructuredTextParser.g:3672:1: ( RightParenthesis )
            // InternalStructuredTextParser.g:3673:2: RightParenthesis
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getRightParenthesisKeyword_1_3()); 
            match(input,RightParenthesis,FOLLOW_2); 
             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getRightParenthesisKeyword_1_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_1__3__Impl"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__0"
    // InternalStructuredTextParser.g:3683:1: rule__Subprog_Ctrl_Stmt__Group_2__0 : rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3687:1: ( rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1 )
            // InternalStructuredTextParser.g:3688:2: rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1
            {
            pushFollow(FOLLOW_24);
            rule__Subprog_Ctrl_Stmt__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__0"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__0__Impl"
    // InternalStructuredTextParser.g:3695:1: rule__Subprog_Ctrl_Stmt__Group_2__0__Impl : ( () ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3699:1: ( ( () ) )
            // InternalStructuredTextParser.g:3700:1: ( () )
            {
            // InternalStructuredTextParser.g:3700:1: ( () )
            // InternalStructuredTextParser.g:3701:2: ()
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getReturnStatementAction_2_0()); 
            // InternalStructuredTextParser.g:3702:2: ()
            // InternalStructuredTextParser.g:3702:3: 
            {
            }

             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getReturnStatementAction_2_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__0__Impl"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__1"
    // InternalStructuredTextParser.g:3710:1: rule__Subprog_Ctrl_Stmt__Group_2__1 : rule__Subprog_Ctrl_Stmt__Group_2__1__Impl ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3714:1: ( rule__Subprog_Ctrl_Stmt__Group_2__1__Impl )
            // InternalStructuredTextParser.g:3715:2: rule__Subprog_Ctrl_Stmt__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__1"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__1__Impl"
    // InternalStructuredTextParser.g:3721:1: rule__Subprog_Ctrl_Stmt__Group_2__1__Impl : ( RETURN ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3725:1: ( ( RETURN ) )
            // InternalStructuredTextParser.g:3726:1: ( RETURN )
            {
            // InternalStructuredTextParser.g:3726:1: ( RETURN )
            // InternalStructuredTextParser.g:3727:2: RETURN
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getRETURNKeyword_2_1()); 
            match(input,RETURN,FOLLOW_2); 
             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getRETURNKeyword_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__1__Impl"


    // $ANTLR start "rule__IF_Stmt__Group__0"
    // InternalStructuredTextParser.g:3737:1: rule__IF_Stmt__Group__0 : rule__IF_Stmt__Group__0__Impl rule__IF_Stmt__Group__1 ;
    public final void rule__IF_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3741:1: ( rule__IF_Stmt__Group__0__Impl rule__IF_Stmt__Group__1 )
            // InternalStructuredTextParser.g:3742:2: rule__IF_Stmt__Group__0__Impl rule__IF_Stmt__Group__1
            {
            pushFollow(FOLLOW_20);
            rule__IF_Stmt__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IF_Stmt__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__0"


    // $ANTLR start "rule__IF_Stmt__Group__0__Impl"
    // InternalStructuredTextParser.g:3749:1: rule__IF_Stmt__Group__0__Impl : ( IF ) ;
    public final void rule__IF_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3753:1: ( ( IF ) )
            // InternalStructuredTextParser.g:3754:1: ( IF )
            {
            // InternalStructuredTextParser.g:3754:1: ( IF )
            // InternalStructuredTextParser.g:3755:2: IF
            {
             before(grammarAccess.getIF_StmtAccess().getIFKeyword_0()); 
            match(input,IF,FOLLOW_2); 
             after(grammarAccess.getIF_StmtAccess().getIFKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__0__Impl"


    // $ANTLR start "rule__IF_Stmt__Group__1"
    // InternalStructuredTextParser.g:3764:1: rule__IF_Stmt__Group__1 : rule__IF_Stmt__Group__1__Impl rule__IF_Stmt__Group__2 ;
    public final void rule__IF_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3768:1: ( rule__IF_Stmt__Group__1__Impl rule__IF_Stmt__Group__2 )
            // InternalStructuredTextParser.g:3769:2: rule__IF_Stmt__Group__1__Impl rule__IF_Stmt__Group__2
            {
            pushFollow(FOLLOW_25);
            rule__IF_Stmt__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IF_Stmt__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__1"


    // $ANTLR start "rule__IF_Stmt__Group__1__Impl"
    // InternalStructuredTextParser.g:3776:1: rule__IF_Stmt__Group__1__Impl : ( ( rule__IF_Stmt__ExpressionAssignment_1 ) ) ;
    public final void rule__IF_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3780:1: ( ( ( rule__IF_Stmt__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:3781:1: ( ( rule__IF_Stmt__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:3781:1: ( ( rule__IF_Stmt__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:3782:2: ( rule__IF_Stmt__ExpressionAssignment_1 )
            {
             before(grammarAccess.getIF_StmtAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:3783:2: ( rule__IF_Stmt__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:3783:3: rule__IF_Stmt__ExpressionAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__IF_Stmt__ExpressionAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getIF_StmtAccess().getExpressionAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__1__Impl"


    // $ANTLR start "rule__IF_Stmt__Group__2"
    // InternalStructuredTextParser.g:3791:1: rule__IF_Stmt__Group__2 : rule__IF_Stmt__Group__2__Impl rule__IF_Stmt__Group__3 ;
    public final void rule__IF_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3795:1: ( rule__IF_Stmt__Group__2__Impl rule__IF_Stmt__Group__3 )
            // InternalStructuredTextParser.g:3796:2: rule__IF_Stmt__Group__2__Impl rule__IF_Stmt__Group__3
            {
            pushFollow(FOLLOW_3);
            rule__IF_Stmt__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IF_Stmt__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__2"


    // $ANTLR start "rule__IF_Stmt__Group__2__Impl"
    // InternalStructuredTextParser.g:3803:1: rule__IF_Stmt__Group__2__Impl : ( THEN ) ;
    public final void rule__IF_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3807:1: ( ( THEN ) )
            // InternalStructuredTextParser.g:3808:1: ( THEN )
            {
            // InternalStructuredTextParser.g:3808:1: ( THEN )
            // InternalStructuredTextParser.g:3809:2: THEN
            {
             before(grammarAccess.getIF_StmtAccess().getTHENKeyword_2()); 
            match(input,THEN,FOLLOW_2); 
             after(grammarAccess.getIF_StmtAccess().getTHENKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__2__Impl"


    // $ANTLR start "rule__IF_Stmt__Group__3"
    // InternalStructuredTextParser.g:3818:1: rule__IF_Stmt__Group__3 : rule__IF_Stmt__Group__3__Impl rule__IF_Stmt__Group__4 ;
    public final void rule__IF_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3822:1: ( rule__IF_Stmt__Group__3__Impl rule__IF_Stmt__Group__4 )
            // InternalStructuredTextParser.g:3823:2: rule__IF_Stmt__Group__3__Impl rule__IF_Stmt__Group__4
            {
            pushFollow(FOLLOW_26);
            rule__IF_Stmt__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IF_Stmt__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__3"


    // $ANTLR start "rule__IF_Stmt__Group__3__Impl"
    // InternalStructuredTextParser.g:3830:1: rule__IF_Stmt__Group__3__Impl : ( ( rule__IF_Stmt__StatmentsAssignment_3 ) ) ;
    public final void rule__IF_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3834:1: ( ( ( rule__IF_Stmt__StatmentsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:3835:1: ( ( rule__IF_Stmt__StatmentsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:3835:1: ( ( rule__IF_Stmt__StatmentsAssignment_3 ) )
            // InternalStructuredTextParser.g:3836:2: ( rule__IF_Stmt__StatmentsAssignment_3 )
            {
             before(grammarAccess.getIF_StmtAccess().getStatmentsAssignment_3()); 
            // InternalStructuredTextParser.g:3837:2: ( rule__IF_Stmt__StatmentsAssignment_3 )
            // InternalStructuredTextParser.g:3837:3: rule__IF_Stmt__StatmentsAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__IF_Stmt__StatmentsAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getIF_StmtAccess().getStatmentsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__3__Impl"


    // $ANTLR start "rule__IF_Stmt__Group__4"
    // InternalStructuredTextParser.g:3845:1: rule__IF_Stmt__Group__4 : rule__IF_Stmt__Group__4__Impl rule__IF_Stmt__Group__5 ;
    public final void rule__IF_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3849:1: ( rule__IF_Stmt__Group__4__Impl rule__IF_Stmt__Group__5 )
            // InternalStructuredTextParser.g:3850:2: rule__IF_Stmt__Group__4__Impl rule__IF_Stmt__Group__5
            {
            pushFollow(FOLLOW_26);
            rule__IF_Stmt__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IF_Stmt__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__4"


    // $ANTLR start "rule__IF_Stmt__Group__4__Impl"
    // InternalStructuredTextParser.g:3857:1: rule__IF_Stmt__Group__4__Impl : ( ( rule__IF_Stmt__ElseifAssignment_4 )* ) ;
    public final void rule__IF_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3861:1: ( ( ( rule__IF_Stmt__ElseifAssignment_4 )* ) )
            // InternalStructuredTextParser.g:3862:1: ( ( rule__IF_Stmt__ElseifAssignment_4 )* )
            {
            // InternalStructuredTextParser.g:3862:1: ( ( rule__IF_Stmt__ElseifAssignment_4 )* )
            // InternalStructuredTextParser.g:3863:2: ( rule__IF_Stmt__ElseifAssignment_4 )*
            {
             before(grammarAccess.getIF_StmtAccess().getElseifAssignment_4()); 
            // InternalStructuredTextParser.g:3864:2: ( rule__IF_Stmt__ElseifAssignment_4 )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==ELSIF) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalStructuredTextParser.g:3864:3: rule__IF_Stmt__ElseifAssignment_4
            	    {
            	    pushFollow(FOLLOW_27);
            	    rule__IF_Stmt__ElseifAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop38;
                }
            } while (true);

             after(grammarAccess.getIF_StmtAccess().getElseifAssignment_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__4__Impl"


    // $ANTLR start "rule__IF_Stmt__Group__5"
    // InternalStructuredTextParser.g:3872:1: rule__IF_Stmt__Group__5 : rule__IF_Stmt__Group__5__Impl rule__IF_Stmt__Group__6 ;
    public final void rule__IF_Stmt__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3876:1: ( rule__IF_Stmt__Group__5__Impl rule__IF_Stmt__Group__6 )
            // InternalStructuredTextParser.g:3877:2: rule__IF_Stmt__Group__5__Impl rule__IF_Stmt__Group__6
            {
            pushFollow(FOLLOW_26);
            rule__IF_Stmt__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__IF_Stmt__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__5"


    // $ANTLR start "rule__IF_Stmt__Group__5__Impl"
    // InternalStructuredTextParser.g:3884:1: rule__IF_Stmt__Group__5__Impl : ( ( rule__IF_Stmt__ElseAssignment_5 )? ) ;
    public final void rule__IF_Stmt__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3888:1: ( ( ( rule__IF_Stmt__ElseAssignment_5 )? ) )
            // InternalStructuredTextParser.g:3889:1: ( ( rule__IF_Stmt__ElseAssignment_5 )? )
            {
            // InternalStructuredTextParser.g:3889:1: ( ( rule__IF_Stmt__ElseAssignment_5 )? )
            // InternalStructuredTextParser.g:3890:2: ( rule__IF_Stmt__ElseAssignment_5 )?
            {
             before(grammarAccess.getIF_StmtAccess().getElseAssignment_5()); 
            // InternalStructuredTextParser.g:3891:2: ( rule__IF_Stmt__ElseAssignment_5 )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==ELSE) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalStructuredTextParser.g:3891:3: rule__IF_Stmt__ElseAssignment_5
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__5__Impl"


    // $ANTLR start "rule__IF_Stmt__Group__6"
    // InternalStructuredTextParser.g:3899:1: rule__IF_Stmt__Group__6 : rule__IF_Stmt__Group__6__Impl ;
    public final void rule__IF_Stmt__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3903:1: ( rule__IF_Stmt__Group__6__Impl )
            // InternalStructuredTextParser.g:3904:2: rule__IF_Stmt__Group__6__Impl
            {
            pushFollow(FOLLOW_2);
            rule__IF_Stmt__Group__6__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__6"


    // $ANTLR start "rule__IF_Stmt__Group__6__Impl"
    // InternalStructuredTextParser.g:3910:1: rule__IF_Stmt__Group__6__Impl : ( END_IF ) ;
    public final void rule__IF_Stmt__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3914:1: ( ( END_IF ) )
            // InternalStructuredTextParser.g:3915:1: ( END_IF )
            {
            // InternalStructuredTextParser.g:3915:1: ( END_IF )
            // InternalStructuredTextParser.g:3916:2: END_IF
            {
             before(grammarAccess.getIF_StmtAccess().getEND_IFKeyword_6()); 
            match(input,END_IF,FOLLOW_2); 
             after(grammarAccess.getIF_StmtAccess().getEND_IFKeyword_6()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__Group__6__Impl"


    // $ANTLR start "rule__ELSIF_Clause__Group__0"
    // InternalStructuredTextParser.g:3926:1: rule__ELSIF_Clause__Group__0 : rule__ELSIF_Clause__Group__0__Impl rule__ELSIF_Clause__Group__1 ;
    public final void rule__ELSIF_Clause__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3930:1: ( rule__ELSIF_Clause__Group__0__Impl rule__ELSIF_Clause__Group__1 )
            // InternalStructuredTextParser.g:3931:2: rule__ELSIF_Clause__Group__0__Impl rule__ELSIF_Clause__Group__1
            {
            pushFollow(FOLLOW_20);
            rule__ELSIF_Clause__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ELSIF_Clause__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSIF_Clause__Group__0"


    // $ANTLR start "rule__ELSIF_Clause__Group__0__Impl"
    // InternalStructuredTextParser.g:3938:1: rule__ELSIF_Clause__Group__0__Impl : ( ELSIF ) ;
    public final void rule__ELSIF_Clause__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3942:1: ( ( ELSIF ) )
            // InternalStructuredTextParser.g:3943:1: ( ELSIF )
            {
            // InternalStructuredTextParser.g:3943:1: ( ELSIF )
            // InternalStructuredTextParser.g:3944:2: ELSIF
            {
             before(grammarAccess.getELSIF_ClauseAccess().getELSIFKeyword_0()); 
            match(input,ELSIF,FOLLOW_2); 
             after(grammarAccess.getELSIF_ClauseAccess().getELSIFKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSIF_Clause__Group__0__Impl"


    // $ANTLR start "rule__ELSIF_Clause__Group__1"
    // InternalStructuredTextParser.g:3953:1: rule__ELSIF_Clause__Group__1 : rule__ELSIF_Clause__Group__1__Impl rule__ELSIF_Clause__Group__2 ;
    public final void rule__ELSIF_Clause__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3957:1: ( rule__ELSIF_Clause__Group__1__Impl rule__ELSIF_Clause__Group__2 )
            // InternalStructuredTextParser.g:3958:2: rule__ELSIF_Clause__Group__1__Impl rule__ELSIF_Clause__Group__2
            {
            pushFollow(FOLLOW_25);
            rule__ELSIF_Clause__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ELSIF_Clause__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSIF_Clause__Group__1"


    // $ANTLR start "rule__ELSIF_Clause__Group__1__Impl"
    // InternalStructuredTextParser.g:3965:1: rule__ELSIF_Clause__Group__1__Impl : ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) ) ;
    public final void rule__ELSIF_Clause__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3969:1: ( ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:3970:1: ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:3970:1: ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:3971:2: ( rule__ELSIF_Clause__ExpressionAssignment_1 )
            {
             before(grammarAccess.getELSIF_ClauseAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:3972:2: ( rule__ELSIF_Clause__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:3972:3: rule__ELSIF_Clause__ExpressionAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__ELSIF_Clause__ExpressionAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getELSIF_ClauseAccess().getExpressionAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSIF_Clause__Group__1__Impl"


    // $ANTLR start "rule__ELSIF_Clause__Group__2"
    // InternalStructuredTextParser.g:3980:1: rule__ELSIF_Clause__Group__2 : rule__ELSIF_Clause__Group__2__Impl rule__ELSIF_Clause__Group__3 ;
    public final void rule__ELSIF_Clause__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3984:1: ( rule__ELSIF_Clause__Group__2__Impl rule__ELSIF_Clause__Group__3 )
            // InternalStructuredTextParser.g:3985:2: rule__ELSIF_Clause__Group__2__Impl rule__ELSIF_Clause__Group__3
            {
            pushFollow(FOLLOW_3);
            rule__ELSIF_Clause__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ELSIF_Clause__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSIF_Clause__Group__2"


    // $ANTLR start "rule__ELSIF_Clause__Group__2__Impl"
    // InternalStructuredTextParser.g:3992:1: rule__ELSIF_Clause__Group__2__Impl : ( THEN ) ;
    public final void rule__ELSIF_Clause__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3996:1: ( ( THEN ) )
            // InternalStructuredTextParser.g:3997:1: ( THEN )
            {
            // InternalStructuredTextParser.g:3997:1: ( THEN )
            // InternalStructuredTextParser.g:3998:2: THEN
            {
             before(grammarAccess.getELSIF_ClauseAccess().getTHENKeyword_2()); 
            match(input,THEN,FOLLOW_2); 
             after(grammarAccess.getELSIF_ClauseAccess().getTHENKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSIF_Clause__Group__2__Impl"


    // $ANTLR start "rule__ELSIF_Clause__Group__3"
    // InternalStructuredTextParser.g:4007:1: rule__ELSIF_Clause__Group__3 : rule__ELSIF_Clause__Group__3__Impl ;
    public final void rule__ELSIF_Clause__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4011:1: ( rule__ELSIF_Clause__Group__3__Impl )
            // InternalStructuredTextParser.g:4012:2: rule__ELSIF_Clause__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ELSIF_Clause__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSIF_Clause__Group__3"


    // $ANTLR start "rule__ELSIF_Clause__Group__3__Impl"
    // InternalStructuredTextParser.g:4018:1: rule__ELSIF_Clause__Group__3__Impl : ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) ) ;
    public final void rule__ELSIF_Clause__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4022:1: ( ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4023:1: ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4023:1: ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) )
            // InternalStructuredTextParser.g:4024:2: ( rule__ELSIF_Clause__StatementsAssignment_3 )
            {
             before(grammarAccess.getELSIF_ClauseAccess().getStatementsAssignment_3()); 
            // InternalStructuredTextParser.g:4025:2: ( rule__ELSIF_Clause__StatementsAssignment_3 )
            // InternalStructuredTextParser.g:4025:3: rule__ELSIF_Clause__StatementsAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__ELSIF_Clause__StatementsAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getELSIF_ClauseAccess().getStatementsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSIF_Clause__Group__3__Impl"


    // $ANTLR start "rule__ELSE_Clause__Group__0"
    // InternalStructuredTextParser.g:4034:1: rule__ELSE_Clause__Group__0 : rule__ELSE_Clause__Group__0__Impl rule__ELSE_Clause__Group__1 ;
    public final void rule__ELSE_Clause__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4038:1: ( rule__ELSE_Clause__Group__0__Impl rule__ELSE_Clause__Group__1 )
            // InternalStructuredTextParser.g:4039:2: rule__ELSE_Clause__Group__0__Impl rule__ELSE_Clause__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__ELSE_Clause__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__ELSE_Clause__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSE_Clause__Group__0"


    // $ANTLR start "rule__ELSE_Clause__Group__0__Impl"
    // InternalStructuredTextParser.g:4046:1: rule__ELSE_Clause__Group__0__Impl : ( ELSE ) ;
    public final void rule__ELSE_Clause__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4050:1: ( ( ELSE ) )
            // InternalStructuredTextParser.g:4051:1: ( ELSE )
            {
            // InternalStructuredTextParser.g:4051:1: ( ELSE )
            // InternalStructuredTextParser.g:4052:2: ELSE
            {
             before(grammarAccess.getELSE_ClauseAccess().getELSEKeyword_0()); 
            match(input,ELSE,FOLLOW_2); 
             after(grammarAccess.getELSE_ClauseAccess().getELSEKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSE_Clause__Group__0__Impl"


    // $ANTLR start "rule__ELSE_Clause__Group__1"
    // InternalStructuredTextParser.g:4061:1: rule__ELSE_Clause__Group__1 : rule__ELSE_Clause__Group__1__Impl ;
    public final void rule__ELSE_Clause__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4065:1: ( rule__ELSE_Clause__Group__1__Impl )
            // InternalStructuredTextParser.g:4066:2: rule__ELSE_Clause__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__ELSE_Clause__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSE_Clause__Group__1"


    // $ANTLR start "rule__ELSE_Clause__Group__1__Impl"
    // InternalStructuredTextParser.g:4072:1: rule__ELSE_Clause__Group__1__Impl : ( ( rule__ELSE_Clause__StatementsAssignment_1 ) ) ;
    public final void rule__ELSE_Clause__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4076:1: ( ( ( rule__ELSE_Clause__StatementsAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4077:1: ( ( rule__ELSE_Clause__StatementsAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4077:1: ( ( rule__ELSE_Clause__StatementsAssignment_1 ) )
            // InternalStructuredTextParser.g:4078:2: ( rule__ELSE_Clause__StatementsAssignment_1 )
            {
             before(grammarAccess.getELSE_ClauseAccess().getStatementsAssignment_1()); 
            // InternalStructuredTextParser.g:4079:2: ( rule__ELSE_Clause__StatementsAssignment_1 )
            // InternalStructuredTextParser.g:4079:3: rule__ELSE_Clause__StatementsAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__ELSE_Clause__StatementsAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getELSE_ClauseAccess().getStatementsAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSE_Clause__Group__1__Impl"


    // $ANTLR start "rule__Case_Stmt__Group__0"
    // InternalStructuredTextParser.g:4088:1: rule__Case_Stmt__Group__0 : rule__Case_Stmt__Group__0__Impl rule__Case_Stmt__Group__1 ;
    public final void rule__Case_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4092:1: ( rule__Case_Stmt__Group__0__Impl rule__Case_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4093:2: rule__Case_Stmt__Group__0__Impl rule__Case_Stmt__Group__1
            {
            pushFollow(FOLLOW_20);
            rule__Case_Stmt__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Case_Stmt__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__0"


    // $ANTLR start "rule__Case_Stmt__Group__0__Impl"
    // InternalStructuredTextParser.g:4100:1: rule__Case_Stmt__Group__0__Impl : ( CASE ) ;
    public final void rule__Case_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4104:1: ( ( CASE ) )
            // InternalStructuredTextParser.g:4105:1: ( CASE )
            {
            // InternalStructuredTextParser.g:4105:1: ( CASE )
            // InternalStructuredTextParser.g:4106:2: CASE
            {
             before(grammarAccess.getCase_StmtAccess().getCASEKeyword_0()); 
            match(input,CASE,FOLLOW_2); 
             after(grammarAccess.getCase_StmtAccess().getCASEKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__0__Impl"


    // $ANTLR start "rule__Case_Stmt__Group__1"
    // InternalStructuredTextParser.g:4115:1: rule__Case_Stmt__Group__1 : rule__Case_Stmt__Group__1__Impl rule__Case_Stmt__Group__2 ;
    public final void rule__Case_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4119:1: ( rule__Case_Stmt__Group__1__Impl rule__Case_Stmt__Group__2 )
            // InternalStructuredTextParser.g:4120:2: rule__Case_Stmt__Group__1__Impl rule__Case_Stmt__Group__2
            {
            pushFollow(FOLLOW_16);
            rule__Case_Stmt__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Case_Stmt__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__1"


    // $ANTLR start "rule__Case_Stmt__Group__1__Impl"
    // InternalStructuredTextParser.g:4127:1: rule__Case_Stmt__Group__1__Impl : ( ( rule__Case_Stmt__ExpressionAssignment_1 ) ) ;
    public final void rule__Case_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4131:1: ( ( ( rule__Case_Stmt__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4132:1: ( ( rule__Case_Stmt__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4132:1: ( ( rule__Case_Stmt__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:4133:2: ( rule__Case_Stmt__ExpressionAssignment_1 )
            {
             before(grammarAccess.getCase_StmtAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:4134:2: ( rule__Case_Stmt__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:4134:3: rule__Case_Stmt__ExpressionAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Case_Stmt__ExpressionAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getCase_StmtAccess().getExpressionAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__1__Impl"


    // $ANTLR start "rule__Case_Stmt__Group__2"
    // InternalStructuredTextParser.g:4142:1: rule__Case_Stmt__Group__2 : rule__Case_Stmt__Group__2__Impl rule__Case_Stmt__Group__3 ;
    public final void rule__Case_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4146:1: ( rule__Case_Stmt__Group__2__Impl rule__Case_Stmt__Group__3 )
            // InternalStructuredTextParser.g:4147:2: rule__Case_Stmt__Group__2__Impl rule__Case_Stmt__Group__3
            {
            pushFollow(FOLLOW_17);
            rule__Case_Stmt__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Case_Stmt__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__2"


    // $ANTLR start "rule__Case_Stmt__Group__2__Impl"
    // InternalStructuredTextParser.g:4154:1: rule__Case_Stmt__Group__2__Impl : ( OF ) ;
    public final void rule__Case_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4158:1: ( ( OF ) )
            // InternalStructuredTextParser.g:4159:1: ( OF )
            {
            // InternalStructuredTextParser.g:4159:1: ( OF )
            // InternalStructuredTextParser.g:4160:2: OF
            {
             before(grammarAccess.getCase_StmtAccess().getOFKeyword_2()); 
            match(input,OF,FOLLOW_2); 
             after(grammarAccess.getCase_StmtAccess().getOFKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__2__Impl"


    // $ANTLR start "rule__Case_Stmt__Group__3"
    // InternalStructuredTextParser.g:4169:1: rule__Case_Stmt__Group__3 : rule__Case_Stmt__Group__3__Impl rule__Case_Stmt__Group__4 ;
    public final void rule__Case_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4173:1: ( rule__Case_Stmt__Group__3__Impl rule__Case_Stmt__Group__4 )
            // InternalStructuredTextParser.g:4174:2: rule__Case_Stmt__Group__3__Impl rule__Case_Stmt__Group__4
            {
            pushFollow(FOLLOW_28);
            rule__Case_Stmt__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Case_Stmt__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__3"


    // $ANTLR start "rule__Case_Stmt__Group__3__Impl"
    // InternalStructuredTextParser.g:4181:1: rule__Case_Stmt__Group__3__Impl : ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) ) ;
    public final void rule__Case_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4185:1: ( ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) ) )
            // InternalStructuredTextParser.g:4186:1: ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) )
            {
            // InternalStructuredTextParser.g:4186:1: ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) )
            // InternalStructuredTextParser.g:4187:2: ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* )
            {
            // InternalStructuredTextParser.g:4187:2: ( ( rule__Case_Stmt__CaseAssignment_3 ) )
            // InternalStructuredTextParser.g:4188:3: ( rule__Case_Stmt__CaseAssignment_3 )
            {
             before(grammarAccess.getCase_StmtAccess().getCaseAssignment_3()); 
            // InternalStructuredTextParser.g:4189:3: ( rule__Case_Stmt__CaseAssignment_3 )
            // InternalStructuredTextParser.g:4189:4: rule__Case_Stmt__CaseAssignment_3
            {
            pushFollow(FOLLOW_29);
            rule__Case_Stmt__CaseAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getCase_StmtAccess().getCaseAssignment_3()); 

            }

            // InternalStructuredTextParser.g:4192:2: ( ( rule__Case_Stmt__CaseAssignment_3 )* )
            // InternalStructuredTextParser.g:4193:3: ( rule__Case_Stmt__CaseAssignment_3 )*
            {
             before(grammarAccess.getCase_StmtAccess().getCaseAssignment_3()); 
            // InternalStructuredTextParser.g:4194:3: ( rule__Case_Stmt__CaseAssignment_3 )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==WSTRING||LA40_0==STRING||LA40_0==FALSE||LA40_0==LREAL||(LA40_0>=UDINT && LA40_0<=ULINT)||(LA40_0>=USINT && LA40_0<=WCHAR)||LA40_0==BOOL||LA40_0==CHAR||LA40_0==DINT||LA40_0==LINT||(LA40_0>=REAL && LA40_0<=SINT)||(LA40_0>=TRUE && LA40_0<=UINT)||LA40_0==INT||LA40_0==PlusSign||LA40_0==HyphenMinus||(LA40_0>=RULE_UNSIGNED_INT && LA40_0<=RULE_DATE)||(LA40_0>=RULE_BINARY_INT && LA40_0<=RULE_HEX_INT)||LA40_0==RULE_S_BYTE_CHAR_STR||LA40_0==RULE_D_BYTE_CHAR_STR) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalStructuredTextParser.g:4194:4: rule__Case_Stmt__CaseAssignment_3
            	    {
            	    pushFollow(FOLLOW_29);
            	    rule__Case_Stmt__CaseAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop40;
                }
            } while (true);

             after(grammarAccess.getCase_StmtAccess().getCaseAssignment_3()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__3__Impl"


    // $ANTLR start "rule__Case_Stmt__Group__4"
    // InternalStructuredTextParser.g:4203:1: rule__Case_Stmt__Group__4 : rule__Case_Stmt__Group__4__Impl rule__Case_Stmt__Group__5 ;
    public final void rule__Case_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4207:1: ( rule__Case_Stmt__Group__4__Impl rule__Case_Stmt__Group__5 )
            // InternalStructuredTextParser.g:4208:2: rule__Case_Stmt__Group__4__Impl rule__Case_Stmt__Group__5
            {
            pushFollow(FOLLOW_28);
            rule__Case_Stmt__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Case_Stmt__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__4"


    // $ANTLR start "rule__Case_Stmt__Group__4__Impl"
    // InternalStructuredTextParser.g:4215:1: rule__Case_Stmt__Group__4__Impl : ( ( rule__Case_Stmt__ElseAssignment_4 )? ) ;
    public final void rule__Case_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4219:1: ( ( ( rule__Case_Stmt__ElseAssignment_4 )? ) )
            // InternalStructuredTextParser.g:4220:1: ( ( rule__Case_Stmt__ElseAssignment_4 )? )
            {
            // InternalStructuredTextParser.g:4220:1: ( ( rule__Case_Stmt__ElseAssignment_4 )? )
            // InternalStructuredTextParser.g:4221:2: ( rule__Case_Stmt__ElseAssignment_4 )?
            {
             before(grammarAccess.getCase_StmtAccess().getElseAssignment_4()); 
            // InternalStructuredTextParser.g:4222:2: ( rule__Case_Stmt__ElseAssignment_4 )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==ELSE) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalStructuredTextParser.g:4222:3: rule__Case_Stmt__ElseAssignment_4
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__4__Impl"


    // $ANTLR start "rule__Case_Stmt__Group__5"
    // InternalStructuredTextParser.g:4230:1: rule__Case_Stmt__Group__5 : rule__Case_Stmt__Group__5__Impl ;
    public final void rule__Case_Stmt__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4234:1: ( rule__Case_Stmt__Group__5__Impl )
            // InternalStructuredTextParser.g:4235:2: rule__Case_Stmt__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Case_Stmt__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__5"


    // $ANTLR start "rule__Case_Stmt__Group__5__Impl"
    // InternalStructuredTextParser.g:4241:1: rule__Case_Stmt__Group__5__Impl : ( END_CASE ) ;
    public final void rule__Case_Stmt__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4245:1: ( ( END_CASE ) )
            // InternalStructuredTextParser.g:4246:1: ( END_CASE )
            {
            // InternalStructuredTextParser.g:4246:1: ( END_CASE )
            // InternalStructuredTextParser.g:4247:2: END_CASE
            {
             before(grammarAccess.getCase_StmtAccess().getEND_CASEKeyword_5()); 
            match(input,END_CASE,FOLLOW_2); 
             after(grammarAccess.getCase_StmtAccess().getEND_CASEKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__Group__5__Impl"


    // $ANTLR start "rule__Case_Selection__Group__0"
    // InternalStructuredTextParser.g:4257:1: rule__Case_Selection__Group__0 : rule__Case_Selection__Group__0__Impl rule__Case_Selection__Group__1 ;
    public final void rule__Case_Selection__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4261:1: ( rule__Case_Selection__Group__0__Impl rule__Case_Selection__Group__1 )
            // InternalStructuredTextParser.g:4262:2: rule__Case_Selection__Group__0__Impl rule__Case_Selection__Group__1
            {
            pushFollow(FOLLOW_30);
            rule__Case_Selection__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Case_Selection__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group__0"


    // $ANTLR start "rule__Case_Selection__Group__0__Impl"
    // InternalStructuredTextParser.g:4269:1: rule__Case_Selection__Group__0__Impl : ( ( rule__Case_Selection__CaseAssignment_0 ) ) ;
    public final void rule__Case_Selection__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4273:1: ( ( ( rule__Case_Selection__CaseAssignment_0 ) ) )
            // InternalStructuredTextParser.g:4274:1: ( ( rule__Case_Selection__CaseAssignment_0 ) )
            {
            // InternalStructuredTextParser.g:4274:1: ( ( rule__Case_Selection__CaseAssignment_0 ) )
            // InternalStructuredTextParser.g:4275:2: ( rule__Case_Selection__CaseAssignment_0 )
            {
             before(grammarAccess.getCase_SelectionAccess().getCaseAssignment_0()); 
            // InternalStructuredTextParser.g:4276:2: ( rule__Case_Selection__CaseAssignment_0 )
            // InternalStructuredTextParser.g:4276:3: rule__Case_Selection__CaseAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Case_Selection__CaseAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getCase_SelectionAccess().getCaseAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group__0__Impl"


    // $ANTLR start "rule__Case_Selection__Group__1"
    // InternalStructuredTextParser.g:4284:1: rule__Case_Selection__Group__1 : rule__Case_Selection__Group__1__Impl rule__Case_Selection__Group__2 ;
    public final void rule__Case_Selection__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4288:1: ( rule__Case_Selection__Group__1__Impl rule__Case_Selection__Group__2 )
            // InternalStructuredTextParser.g:4289:2: rule__Case_Selection__Group__1__Impl rule__Case_Selection__Group__2
            {
            pushFollow(FOLLOW_30);
            rule__Case_Selection__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Case_Selection__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group__1"


    // $ANTLR start "rule__Case_Selection__Group__1__Impl"
    // InternalStructuredTextParser.g:4296:1: rule__Case_Selection__Group__1__Impl : ( ( rule__Case_Selection__Group_1__0 )* ) ;
    public final void rule__Case_Selection__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4300:1: ( ( ( rule__Case_Selection__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:4301:1: ( ( rule__Case_Selection__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:4301:1: ( ( rule__Case_Selection__Group_1__0 )* )
            // InternalStructuredTextParser.g:4302:2: ( rule__Case_Selection__Group_1__0 )*
            {
             before(grammarAccess.getCase_SelectionAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:4303:2: ( rule__Case_Selection__Group_1__0 )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==Comma) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalStructuredTextParser.g:4303:3: rule__Case_Selection__Group_1__0
            	    {
            	    pushFollow(FOLLOW_31);
            	    rule__Case_Selection__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);

             after(grammarAccess.getCase_SelectionAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group__1__Impl"


    // $ANTLR start "rule__Case_Selection__Group__2"
    // InternalStructuredTextParser.g:4311:1: rule__Case_Selection__Group__2 : rule__Case_Selection__Group__2__Impl rule__Case_Selection__Group__3 ;
    public final void rule__Case_Selection__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4315:1: ( rule__Case_Selection__Group__2__Impl rule__Case_Selection__Group__3 )
            // InternalStructuredTextParser.g:4316:2: rule__Case_Selection__Group__2__Impl rule__Case_Selection__Group__3
            {
            pushFollow(FOLLOW_3);
            rule__Case_Selection__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Case_Selection__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group__2"


    // $ANTLR start "rule__Case_Selection__Group__2__Impl"
    // InternalStructuredTextParser.g:4323:1: rule__Case_Selection__Group__2__Impl : ( Colon ) ;
    public final void rule__Case_Selection__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4327:1: ( ( Colon ) )
            // InternalStructuredTextParser.g:4328:1: ( Colon )
            {
            // InternalStructuredTextParser.g:4328:1: ( Colon )
            // InternalStructuredTextParser.g:4329:2: Colon
            {
             before(grammarAccess.getCase_SelectionAccess().getColonKeyword_2()); 
            match(input,Colon,FOLLOW_2); 
             after(grammarAccess.getCase_SelectionAccess().getColonKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group__2__Impl"


    // $ANTLR start "rule__Case_Selection__Group__3"
    // InternalStructuredTextParser.g:4338:1: rule__Case_Selection__Group__3 : rule__Case_Selection__Group__3__Impl ;
    public final void rule__Case_Selection__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4342:1: ( rule__Case_Selection__Group__3__Impl )
            // InternalStructuredTextParser.g:4343:2: rule__Case_Selection__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Case_Selection__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group__3"


    // $ANTLR start "rule__Case_Selection__Group__3__Impl"
    // InternalStructuredTextParser.g:4349:1: rule__Case_Selection__Group__3__Impl : ( ( rule__Case_Selection__StatementsAssignment_3 ) ) ;
    public final void rule__Case_Selection__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4353:1: ( ( ( rule__Case_Selection__StatementsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4354:1: ( ( rule__Case_Selection__StatementsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4354:1: ( ( rule__Case_Selection__StatementsAssignment_3 ) )
            // InternalStructuredTextParser.g:4355:2: ( rule__Case_Selection__StatementsAssignment_3 )
            {
             before(grammarAccess.getCase_SelectionAccess().getStatementsAssignment_3()); 
            // InternalStructuredTextParser.g:4356:2: ( rule__Case_Selection__StatementsAssignment_3 )
            // InternalStructuredTextParser.g:4356:3: rule__Case_Selection__StatementsAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Case_Selection__StatementsAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getCase_SelectionAccess().getStatementsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group__3__Impl"


    // $ANTLR start "rule__Case_Selection__Group_1__0"
    // InternalStructuredTextParser.g:4365:1: rule__Case_Selection__Group_1__0 : rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1 ;
    public final void rule__Case_Selection__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4369:1: ( rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1 )
            // InternalStructuredTextParser.g:4370:2: rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1
            {
            pushFollow(FOLLOW_17);
            rule__Case_Selection__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Case_Selection__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group_1__0"


    // $ANTLR start "rule__Case_Selection__Group_1__0__Impl"
    // InternalStructuredTextParser.g:4377:1: rule__Case_Selection__Group_1__0__Impl : ( Comma ) ;
    public final void rule__Case_Selection__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4381:1: ( ( Comma ) )
            // InternalStructuredTextParser.g:4382:1: ( Comma )
            {
            // InternalStructuredTextParser.g:4382:1: ( Comma )
            // InternalStructuredTextParser.g:4383:2: Comma
            {
             before(grammarAccess.getCase_SelectionAccess().getCommaKeyword_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getCase_SelectionAccess().getCommaKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group_1__0__Impl"


    // $ANTLR start "rule__Case_Selection__Group_1__1"
    // InternalStructuredTextParser.g:4392:1: rule__Case_Selection__Group_1__1 : rule__Case_Selection__Group_1__1__Impl ;
    public final void rule__Case_Selection__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4396:1: ( rule__Case_Selection__Group_1__1__Impl )
            // InternalStructuredTextParser.g:4397:2: rule__Case_Selection__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Case_Selection__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group_1__1"


    // $ANTLR start "rule__Case_Selection__Group_1__1__Impl"
    // InternalStructuredTextParser.g:4403:1: rule__Case_Selection__Group_1__1__Impl : ( ( rule__Case_Selection__CaseAssignment_1_1 ) ) ;
    public final void rule__Case_Selection__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4407:1: ( ( ( rule__Case_Selection__CaseAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:4408:1: ( ( rule__Case_Selection__CaseAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:4408:1: ( ( rule__Case_Selection__CaseAssignment_1_1 ) )
            // InternalStructuredTextParser.g:4409:2: ( rule__Case_Selection__CaseAssignment_1_1 )
            {
             before(grammarAccess.getCase_SelectionAccess().getCaseAssignment_1_1()); 
            // InternalStructuredTextParser.g:4410:2: ( rule__Case_Selection__CaseAssignment_1_1 )
            // InternalStructuredTextParser.g:4410:3: rule__Case_Selection__CaseAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Case_Selection__CaseAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getCase_SelectionAccess().getCaseAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__Group_1__1__Impl"


    // $ANTLR start "rule__Iteration_Stmt__Group_3__0"
    // InternalStructuredTextParser.g:4419:1: rule__Iteration_Stmt__Group_3__0 : rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1 ;
    public final void rule__Iteration_Stmt__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4423:1: ( rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1 )
            // InternalStructuredTextParser.g:4424:2: rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1
            {
            pushFollow(FOLLOW_32);
            rule__Iteration_Stmt__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Iteration_Stmt__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Iteration_Stmt__Group_3__0"


    // $ANTLR start "rule__Iteration_Stmt__Group_3__0__Impl"
    // InternalStructuredTextParser.g:4431:1: rule__Iteration_Stmt__Group_3__0__Impl : ( () ) ;
    public final void rule__Iteration_Stmt__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4435:1: ( ( () ) )
            // InternalStructuredTextParser.g:4436:1: ( () )
            {
            // InternalStructuredTextParser.g:4436:1: ( () )
            // InternalStructuredTextParser.g:4437:2: ()
            {
             before(grammarAccess.getIteration_StmtAccess().getExitStatementAction_3_0()); 
            // InternalStructuredTextParser.g:4438:2: ()
            // InternalStructuredTextParser.g:4438:3: 
            {
            }

             after(grammarAccess.getIteration_StmtAccess().getExitStatementAction_3_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Iteration_Stmt__Group_3__0__Impl"


    // $ANTLR start "rule__Iteration_Stmt__Group_3__1"
    // InternalStructuredTextParser.g:4446:1: rule__Iteration_Stmt__Group_3__1 : rule__Iteration_Stmt__Group_3__1__Impl ;
    public final void rule__Iteration_Stmt__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4450:1: ( rule__Iteration_Stmt__Group_3__1__Impl )
            // InternalStructuredTextParser.g:4451:2: rule__Iteration_Stmt__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Iteration_Stmt__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Iteration_Stmt__Group_3__1"


    // $ANTLR start "rule__Iteration_Stmt__Group_3__1__Impl"
    // InternalStructuredTextParser.g:4457:1: rule__Iteration_Stmt__Group_3__1__Impl : ( EXIT ) ;
    public final void rule__Iteration_Stmt__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4461:1: ( ( EXIT ) )
            // InternalStructuredTextParser.g:4462:1: ( EXIT )
            {
            // InternalStructuredTextParser.g:4462:1: ( EXIT )
            // InternalStructuredTextParser.g:4463:2: EXIT
            {
             before(grammarAccess.getIteration_StmtAccess().getEXITKeyword_3_1()); 
            match(input,EXIT,FOLLOW_2); 
             after(grammarAccess.getIteration_StmtAccess().getEXITKeyword_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Iteration_Stmt__Group_3__1__Impl"


    // $ANTLR start "rule__Iteration_Stmt__Group_4__0"
    // InternalStructuredTextParser.g:4473:1: rule__Iteration_Stmt__Group_4__0 : rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1 ;
    public final void rule__Iteration_Stmt__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4477:1: ( rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1 )
            // InternalStructuredTextParser.g:4478:2: rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1
            {
            pushFollow(FOLLOW_33);
            rule__Iteration_Stmt__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Iteration_Stmt__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Iteration_Stmt__Group_4__0"


    // $ANTLR start "rule__Iteration_Stmt__Group_4__0__Impl"
    // InternalStructuredTextParser.g:4485:1: rule__Iteration_Stmt__Group_4__0__Impl : ( () ) ;
    public final void rule__Iteration_Stmt__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4489:1: ( ( () ) )
            // InternalStructuredTextParser.g:4490:1: ( () )
            {
            // InternalStructuredTextParser.g:4490:1: ( () )
            // InternalStructuredTextParser.g:4491:2: ()
            {
             before(grammarAccess.getIteration_StmtAccess().getContinueStatementAction_4_0()); 
            // InternalStructuredTextParser.g:4492:2: ()
            // InternalStructuredTextParser.g:4492:3: 
            {
            }

             after(grammarAccess.getIteration_StmtAccess().getContinueStatementAction_4_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Iteration_Stmt__Group_4__0__Impl"


    // $ANTLR start "rule__Iteration_Stmt__Group_4__1"
    // InternalStructuredTextParser.g:4500:1: rule__Iteration_Stmt__Group_4__1 : rule__Iteration_Stmt__Group_4__1__Impl ;
    public final void rule__Iteration_Stmt__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4504:1: ( rule__Iteration_Stmt__Group_4__1__Impl )
            // InternalStructuredTextParser.g:4505:2: rule__Iteration_Stmt__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Iteration_Stmt__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Iteration_Stmt__Group_4__1"


    // $ANTLR start "rule__Iteration_Stmt__Group_4__1__Impl"
    // InternalStructuredTextParser.g:4511:1: rule__Iteration_Stmt__Group_4__1__Impl : ( CONTINUE ) ;
    public final void rule__Iteration_Stmt__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4515:1: ( ( CONTINUE ) )
            // InternalStructuredTextParser.g:4516:1: ( CONTINUE )
            {
            // InternalStructuredTextParser.g:4516:1: ( CONTINUE )
            // InternalStructuredTextParser.g:4517:2: CONTINUE
            {
             before(grammarAccess.getIteration_StmtAccess().getCONTINUEKeyword_4_1()); 
            match(input,CONTINUE,FOLLOW_2); 
             after(grammarAccess.getIteration_StmtAccess().getCONTINUEKeyword_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Iteration_Stmt__Group_4__1__Impl"


    // $ANTLR start "rule__For_Stmt__Group__0"
    // InternalStructuredTextParser.g:4527:1: rule__For_Stmt__Group__0 : rule__For_Stmt__Group__0__Impl rule__For_Stmt__Group__1 ;
    public final void rule__For_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4531:1: ( rule__For_Stmt__Group__0__Impl rule__For_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4532:2: rule__For_Stmt__Group__0__Impl rule__For_Stmt__Group__1
            {
            pushFollow(FOLLOW_11);
            rule__For_Stmt__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__0"


    // $ANTLR start "rule__For_Stmt__Group__0__Impl"
    // InternalStructuredTextParser.g:4539:1: rule__For_Stmt__Group__0__Impl : ( FOR ) ;
    public final void rule__For_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4543:1: ( ( FOR ) )
            // InternalStructuredTextParser.g:4544:1: ( FOR )
            {
            // InternalStructuredTextParser.g:4544:1: ( FOR )
            // InternalStructuredTextParser.g:4545:2: FOR
            {
             before(grammarAccess.getFor_StmtAccess().getFORKeyword_0()); 
            match(input,FOR,FOLLOW_2); 
             after(grammarAccess.getFor_StmtAccess().getFORKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__0__Impl"


    // $ANTLR start "rule__For_Stmt__Group__1"
    // InternalStructuredTextParser.g:4554:1: rule__For_Stmt__Group__1 : rule__For_Stmt__Group__1__Impl rule__For_Stmt__Group__2 ;
    public final void rule__For_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4558:1: ( rule__For_Stmt__Group__1__Impl rule__For_Stmt__Group__2 )
            // InternalStructuredTextParser.g:4559:2: rule__For_Stmt__Group__1__Impl rule__For_Stmt__Group__2
            {
            pushFollow(FOLLOW_10);
            rule__For_Stmt__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__1"


    // $ANTLR start "rule__For_Stmt__Group__1__Impl"
    // InternalStructuredTextParser.g:4566:1: rule__For_Stmt__Group__1__Impl : ( ( rule__For_Stmt__VariableAssignment_1 ) ) ;
    public final void rule__For_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4570:1: ( ( ( rule__For_Stmt__VariableAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4571:1: ( ( rule__For_Stmt__VariableAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4571:1: ( ( rule__For_Stmt__VariableAssignment_1 ) )
            // InternalStructuredTextParser.g:4572:2: ( rule__For_Stmt__VariableAssignment_1 )
            {
             before(grammarAccess.getFor_StmtAccess().getVariableAssignment_1()); 
            // InternalStructuredTextParser.g:4573:2: ( rule__For_Stmt__VariableAssignment_1 )
            // InternalStructuredTextParser.g:4573:3: rule__For_Stmt__VariableAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__For_Stmt__VariableAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getFor_StmtAccess().getVariableAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__1__Impl"


    // $ANTLR start "rule__For_Stmt__Group__2"
    // InternalStructuredTextParser.g:4581:1: rule__For_Stmt__Group__2 : rule__For_Stmt__Group__2__Impl rule__For_Stmt__Group__3 ;
    public final void rule__For_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4585:1: ( rule__For_Stmt__Group__2__Impl rule__For_Stmt__Group__3 )
            // InternalStructuredTextParser.g:4586:2: rule__For_Stmt__Group__2__Impl rule__For_Stmt__Group__3
            {
            pushFollow(FOLLOW_20);
            rule__For_Stmt__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__2"


    // $ANTLR start "rule__For_Stmt__Group__2__Impl"
    // InternalStructuredTextParser.g:4593:1: rule__For_Stmt__Group__2__Impl : ( ColonEqualsSign ) ;
    public final void rule__For_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4597:1: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:4598:1: ( ColonEqualsSign )
            {
            // InternalStructuredTextParser.g:4598:1: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:4599:2: ColonEqualsSign
            {
             before(grammarAccess.getFor_StmtAccess().getColonEqualsSignKeyword_2()); 
            match(input,ColonEqualsSign,FOLLOW_2); 
             after(grammarAccess.getFor_StmtAccess().getColonEqualsSignKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__2__Impl"


    // $ANTLR start "rule__For_Stmt__Group__3"
    // InternalStructuredTextParser.g:4608:1: rule__For_Stmt__Group__3 : rule__For_Stmt__Group__3__Impl rule__For_Stmt__Group__4 ;
    public final void rule__For_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4612:1: ( rule__For_Stmt__Group__3__Impl rule__For_Stmt__Group__4 )
            // InternalStructuredTextParser.g:4613:2: rule__For_Stmt__Group__3__Impl rule__For_Stmt__Group__4
            {
            pushFollow(FOLLOW_34);
            rule__For_Stmt__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__3"


    // $ANTLR start "rule__For_Stmt__Group__3__Impl"
    // InternalStructuredTextParser.g:4620:1: rule__For_Stmt__Group__3__Impl : ( ( rule__For_Stmt__FromAssignment_3 ) ) ;
    public final void rule__For_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4624:1: ( ( ( rule__For_Stmt__FromAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4625:1: ( ( rule__For_Stmt__FromAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4625:1: ( ( rule__For_Stmt__FromAssignment_3 ) )
            // InternalStructuredTextParser.g:4626:2: ( rule__For_Stmt__FromAssignment_3 )
            {
             before(grammarAccess.getFor_StmtAccess().getFromAssignment_3()); 
            // InternalStructuredTextParser.g:4627:2: ( rule__For_Stmt__FromAssignment_3 )
            // InternalStructuredTextParser.g:4627:3: rule__For_Stmt__FromAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__For_Stmt__FromAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getFor_StmtAccess().getFromAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__3__Impl"


    // $ANTLR start "rule__For_Stmt__Group__4"
    // InternalStructuredTextParser.g:4635:1: rule__For_Stmt__Group__4 : rule__For_Stmt__Group__4__Impl rule__For_Stmt__Group__5 ;
    public final void rule__For_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4639:1: ( rule__For_Stmt__Group__4__Impl rule__For_Stmt__Group__5 )
            // InternalStructuredTextParser.g:4640:2: rule__For_Stmt__Group__4__Impl rule__For_Stmt__Group__5
            {
            pushFollow(FOLLOW_20);
            rule__For_Stmt__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__4"


    // $ANTLR start "rule__For_Stmt__Group__4__Impl"
    // InternalStructuredTextParser.g:4647:1: rule__For_Stmt__Group__4__Impl : ( TO ) ;
    public final void rule__For_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4651:1: ( ( TO ) )
            // InternalStructuredTextParser.g:4652:1: ( TO )
            {
            // InternalStructuredTextParser.g:4652:1: ( TO )
            // InternalStructuredTextParser.g:4653:2: TO
            {
             before(grammarAccess.getFor_StmtAccess().getTOKeyword_4()); 
            match(input,TO,FOLLOW_2); 
             after(grammarAccess.getFor_StmtAccess().getTOKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__4__Impl"


    // $ANTLR start "rule__For_Stmt__Group__5"
    // InternalStructuredTextParser.g:4662:1: rule__For_Stmt__Group__5 : rule__For_Stmt__Group__5__Impl rule__For_Stmt__Group__6 ;
    public final void rule__For_Stmt__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4666:1: ( rule__For_Stmt__Group__5__Impl rule__For_Stmt__Group__6 )
            // InternalStructuredTextParser.g:4667:2: rule__For_Stmt__Group__5__Impl rule__For_Stmt__Group__6
            {
            pushFollow(FOLLOW_35);
            rule__For_Stmt__Group__5__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__6();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__5"


    // $ANTLR start "rule__For_Stmt__Group__5__Impl"
    // InternalStructuredTextParser.g:4674:1: rule__For_Stmt__Group__5__Impl : ( ( rule__For_Stmt__ToAssignment_5 ) ) ;
    public final void rule__For_Stmt__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4678:1: ( ( ( rule__For_Stmt__ToAssignment_5 ) ) )
            // InternalStructuredTextParser.g:4679:1: ( ( rule__For_Stmt__ToAssignment_5 ) )
            {
            // InternalStructuredTextParser.g:4679:1: ( ( rule__For_Stmt__ToAssignment_5 ) )
            // InternalStructuredTextParser.g:4680:2: ( rule__For_Stmt__ToAssignment_5 )
            {
             before(grammarAccess.getFor_StmtAccess().getToAssignment_5()); 
            // InternalStructuredTextParser.g:4681:2: ( rule__For_Stmt__ToAssignment_5 )
            // InternalStructuredTextParser.g:4681:3: rule__For_Stmt__ToAssignment_5
            {
            pushFollow(FOLLOW_2);
            rule__For_Stmt__ToAssignment_5();

            state._fsp--;


            }

             after(grammarAccess.getFor_StmtAccess().getToAssignment_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__5__Impl"


    // $ANTLR start "rule__For_Stmt__Group__6"
    // InternalStructuredTextParser.g:4689:1: rule__For_Stmt__Group__6 : rule__For_Stmt__Group__6__Impl rule__For_Stmt__Group__7 ;
    public final void rule__For_Stmt__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4693:1: ( rule__For_Stmt__Group__6__Impl rule__For_Stmt__Group__7 )
            // InternalStructuredTextParser.g:4694:2: rule__For_Stmt__Group__6__Impl rule__For_Stmt__Group__7
            {
            pushFollow(FOLLOW_35);
            rule__For_Stmt__Group__6__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__7();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__6"


    // $ANTLR start "rule__For_Stmt__Group__6__Impl"
    // InternalStructuredTextParser.g:4701:1: rule__For_Stmt__Group__6__Impl : ( ( rule__For_Stmt__Group_6__0 )? ) ;
    public final void rule__For_Stmt__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4705:1: ( ( ( rule__For_Stmt__Group_6__0 )? ) )
            // InternalStructuredTextParser.g:4706:1: ( ( rule__For_Stmt__Group_6__0 )? )
            {
            // InternalStructuredTextParser.g:4706:1: ( ( rule__For_Stmt__Group_6__0 )? )
            // InternalStructuredTextParser.g:4707:2: ( rule__For_Stmt__Group_6__0 )?
            {
             before(grammarAccess.getFor_StmtAccess().getGroup_6()); 
            // InternalStructuredTextParser.g:4708:2: ( rule__For_Stmt__Group_6__0 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==BY) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalStructuredTextParser.g:4708:3: rule__For_Stmt__Group_6__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__6__Impl"


    // $ANTLR start "rule__For_Stmt__Group__7"
    // InternalStructuredTextParser.g:4716:1: rule__For_Stmt__Group__7 : rule__For_Stmt__Group__7__Impl rule__For_Stmt__Group__8 ;
    public final void rule__For_Stmt__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4720:1: ( rule__For_Stmt__Group__7__Impl rule__For_Stmt__Group__8 )
            // InternalStructuredTextParser.g:4721:2: rule__For_Stmt__Group__7__Impl rule__For_Stmt__Group__8
            {
            pushFollow(FOLLOW_3);
            rule__For_Stmt__Group__7__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__8();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__7"


    // $ANTLR start "rule__For_Stmt__Group__7__Impl"
    // InternalStructuredTextParser.g:4728:1: rule__For_Stmt__Group__7__Impl : ( DO ) ;
    public final void rule__For_Stmt__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4732:1: ( ( DO ) )
            // InternalStructuredTextParser.g:4733:1: ( DO )
            {
            // InternalStructuredTextParser.g:4733:1: ( DO )
            // InternalStructuredTextParser.g:4734:2: DO
            {
             before(grammarAccess.getFor_StmtAccess().getDOKeyword_7()); 
            match(input,DO,FOLLOW_2); 
             after(grammarAccess.getFor_StmtAccess().getDOKeyword_7()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__7__Impl"


    // $ANTLR start "rule__For_Stmt__Group__8"
    // InternalStructuredTextParser.g:4743:1: rule__For_Stmt__Group__8 : rule__For_Stmt__Group__8__Impl rule__For_Stmt__Group__9 ;
    public final void rule__For_Stmt__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4747:1: ( rule__For_Stmt__Group__8__Impl rule__For_Stmt__Group__9 )
            // InternalStructuredTextParser.g:4748:2: rule__For_Stmt__Group__8__Impl rule__For_Stmt__Group__9
            {
            pushFollow(FOLLOW_36);
            rule__For_Stmt__Group__8__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__9();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__8"


    // $ANTLR start "rule__For_Stmt__Group__8__Impl"
    // InternalStructuredTextParser.g:4755:1: rule__For_Stmt__Group__8__Impl : ( ( rule__For_Stmt__StatementsAssignment_8 ) ) ;
    public final void rule__For_Stmt__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4759:1: ( ( ( rule__For_Stmt__StatementsAssignment_8 ) ) )
            // InternalStructuredTextParser.g:4760:1: ( ( rule__For_Stmt__StatementsAssignment_8 ) )
            {
            // InternalStructuredTextParser.g:4760:1: ( ( rule__For_Stmt__StatementsAssignment_8 ) )
            // InternalStructuredTextParser.g:4761:2: ( rule__For_Stmt__StatementsAssignment_8 )
            {
             before(grammarAccess.getFor_StmtAccess().getStatementsAssignment_8()); 
            // InternalStructuredTextParser.g:4762:2: ( rule__For_Stmt__StatementsAssignment_8 )
            // InternalStructuredTextParser.g:4762:3: rule__For_Stmt__StatementsAssignment_8
            {
            pushFollow(FOLLOW_2);
            rule__For_Stmt__StatementsAssignment_8();

            state._fsp--;


            }

             after(grammarAccess.getFor_StmtAccess().getStatementsAssignment_8()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__8__Impl"


    // $ANTLR start "rule__For_Stmt__Group__9"
    // InternalStructuredTextParser.g:4770:1: rule__For_Stmt__Group__9 : rule__For_Stmt__Group__9__Impl ;
    public final void rule__For_Stmt__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4774:1: ( rule__For_Stmt__Group__9__Impl )
            // InternalStructuredTextParser.g:4775:2: rule__For_Stmt__Group__9__Impl
            {
            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group__9__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__9"


    // $ANTLR start "rule__For_Stmt__Group__9__Impl"
    // InternalStructuredTextParser.g:4781:1: rule__For_Stmt__Group__9__Impl : ( END_FOR ) ;
    public final void rule__For_Stmt__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4785:1: ( ( END_FOR ) )
            // InternalStructuredTextParser.g:4786:1: ( END_FOR )
            {
            // InternalStructuredTextParser.g:4786:1: ( END_FOR )
            // InternalStructuredTextParser.g:4787:2: END_FOR
            {
             before(grammarAccess.getFor_StmtAccess().getEND_FORKeyword_9()); 
            match(input,END_FOR,FOLLOW_2); 
             after(grammarAccess.getFor_StmtAccess().getEND_FORKeyword_9()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group__9__Impl"


    // $ANTLR start "rule__For_Stmt__Group_6__0"
    // InternalStructuredTextParser.g:4797:1: rule__For_Stmt__Group_6__0 : rule__For_Stmt__Group_6__0__Impl rule__For_Stmt__Group_6__1 ;
    public final void rule__For_Stmt__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4801:1: ( rule__For_Stmt__Group_6__0__Impl rule__For_Stmt__Group_6__1 )
            // InternalStructuredTextParser.g:4802:2: rule__For_Stmt__Group_6__0__Impl rule__For_Stmt__Group_6__1
            {
            pushFollow(FOLLOW_20);
            rule__For_Stmt__Group_6__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group_6__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group_6__0"


    // $ANTLR start "rule__For_Stmt__Group_6__0__Impl"
    // InternalStructuredTextParser.g:4809:1: rule__For_Stmt__Group_6__0__Impl : ( BY ) ;
    public final void rule__For_Stmt__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4813:1: ( ( BY ) )
            // InternalStructuredTextParser.g:4814:1: ( BY )
            {
            // InternalStructuredTextParser.g:4814:1: ( BY )
            // InternalStructuredTextParser.g:4815:2: BY
            {
             before(grammarAccess.getFor_StmtAccess().getBYKeyword_6_0()); 
            match(input,BY,FOLLOW_2); 
             after(grammarAccess.getFor_StmtAccess().getBYKeyword_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group_6__0__Impl"


    // $ANTLR start "rule__For_Stmt__Group_6__1"
    // InternalStructuredTextParser.g:4824:1: rule__For_Stmt__Group_6__1 : rule__For_Stmt__Group_6__1__Impl ;
    public final void rule__For_Stmt__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4828:1: ( rule__For_Stmt__Group_6__1__Impl )
            // InternalStructuredTextParser.g:4829:2: rule__For_Stmt__Group_6__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__For_Stmt__Group_6__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group_6__1"


    // $ANTLR start "rule__For_Stmt__Group_6__1__Impl"
    // InternalStructuredTextParser.g:4835:1: rule__For_Stmt__Group_6__1__Impl : ( ( rule__For_Stmt__ByAssignment_6_1 ) ) ;
    public final void rule__For_Stmt__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4839:1: ( ( ( rule__For_Stmt__ByAssignment_6_1 ) ) )
            // InternalStructuredTextParser.g:4840:1: ( ( rule__For_Stmt__ByAssignment_6_1 ) )
            {
            // InternalStructuredTextParser.g:4840:1: ( ( rule__For_Stmt__ByAssignment_6_1 ) )
            // InternalStructuredTextParser.g:4841:2: ( rule__For_Stmt__ByAssignment_6_1 )
            {
             before(grammarAccess.getFor_StmtAccess().getByAssignment_6_1()); 
            // InternalStructuredTextParser.g:4842:2: ( rule__For_Stmt__ByAssignment_6_1 )
            // InternalStructuredTextParser.g:4842:3: rule__For_Stmt__ByAssignment_6_1
            {
            pushFollow(FOLLOW_2);
            rule__For_Stmt__ByAssignment_6_1();

            state._fsp--;


            }

             after(grammarAccess.getFor_StmtAccess().getByAssignment_6_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__Group_6__1__Impl"


    // $ANTLR start "rule__While_Stmt__Group__0"
    // InternalStructuredTextParser.g:4851:1: rule__While_Stmt__Group__0 : rule__While_Stmt__Group__0__Impl rule__While_Stmt__Group__1 ;
    public final void rule__While_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4855:1: ( rule__While_Stmt__Group__0__Impl rule__While_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4856:2: rule__While_Stmt__Group__0__Impl rule__While_Stmt__Group__1
            {
            pushFollow(FOLLOW_20);
            rule__While_Stmt__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__While_Stmt__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__Group__0"


    // $ANTLR start "rule__While_Stmt__Group__0__Impl"
    // InternalStructuredTextParser.g:4863:1: rule__While_Stmt__Group__0__Impl : ( WHILE ) ;
    public final void rule__While_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4867:1: ( ( WHILE ) )
            // InternalStructuredTextParser.g:4868:1: ( WHILE )
            {
            // InternalStructuredTextParser.g:4868:1: ( WHILE )
            // InternalStructuredTextParser.g:4869:2: WHILE
            {
             before(grammarAccess.getWhile_StmtAccess().getWHILEKeyword_0()); 
            match(input,WHILE,FOLLOW_2); 
             after(grammarAccess.getWhile_StmtAccess().getWHILEKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__Group__0__Impl"


    // $ANTLR start "rule__While_Stmt__Group__1"
    // InternalStructuredTextParser.g:4878:1: rule__While_Stmt__Group__1 : rule__While_Stmt__Group__1__Impl rule__While_Stmt__Group__2 ;
    public final void rule__While_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4882:1: ( rule__While_Stmt__Group__1__Impl rule__While_Stmt__Group__2 )
            // InternalStructuredTextParser.g:4883:2: rule__While_Stmt__Group__1__Impl rule__While_Stmt__Group__2
            {
            pushFollow(FOLLOW_37);
            rule__While_Stmt__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__While_Stmt__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__Group__1"


    // $ANTLR start "rule__While_Stmt__Group__1__Impl"
    // InternalStructuredTextParser.g:4890:1: rule__While_Stmt__Group__1__Impl : ( ( rule__While_Stmt__ExpressionAssignment_1 ) ) ;
    public final void rule__While_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4894:1: ( ( ( rule__While_Stmt__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4895:1: ( ( rule__While_Stmt__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4895:1: ( ( rule__While_Stmt__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:4896:2: ( rule__While_Stmt__ExpressionAssignment_1 )
            {
             before(grammarAccess.getWhile_StmtAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:4897:2: ( rule__While_Stmt__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:4897:3: rule__While_Stmt__ExpressionAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__While_Stmt__ExpressionAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getWhile_StmtAccess().getExpressionAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__Group__1__Impl"


    // $ANTLR start "rule__While_Stmt__Group__2"
    // InternalStructuredTextParser.g:4905:1: rule__While_Stmt__Group__2 : rule__While_Stmt__Group__2__Impl rule__While_Stmt__Group__3 ;
    public final void rule__While_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4909:1: ( rule__While_Stmt__Group__2__Impl rule__While_Stmt__Group__3 )
            // InternalStructuredTextParser.g:4910:2: rule__While_Stmt__Group__2__Impl rule__While_Stmt__Group__3
            {
            pushFollow(FOLLOW_3);
            rule__While_Stmt__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__While_Stmt__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__Group__2"


    // $ANTLR start "rule__While_Stmt__Group__2__Impl"
    // InternalStructuredTextParser.g:4917:1: rule__While_Stmt__Group__2__Impl : ( DO ) ;
    public final void rule__While_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4921:1: ( ( DO ) )
            // InternalStructuredTextParser.g:4922:1: ( DO )
            {
            // InternalStructuredTextParser.g:4922:1: ( DO )
            // InternalStructuredTextParser.g:4923:2: DO
            {
             before(grammarAccess.getWhile_StmtAccess().getDOKeyword_2()); 
            match(input,DO,FOLLOW_2); 
             after(grammarAccess.getWhile_StmtAccess().getDOKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__Group__2__Impl"


    // $ANTLR start "rule__While_Stmt__Group__3"
    // InternalStructuredTextParser.g:4932:1: rule__While_Stmt__Group__3 : rule__While_Stmt__Group__3__Impl rule__While_Stmt__Group__4 ;
    public final void rule__While_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4936:1: ( rule__While_Stmt__Group__3__Impl rule__While_Stmt__Group__4 )
            // InternalStructuredTextParser.g:4937:2: rule__While_Stmt__Group__3__Impl rule__While_Stmt__Group__4
            {
            pushFollow(FOLLOW_38);
            rule__While_Stmt__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__While_Stmt__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__Group__3"


    // $ANTLR start "rule__While_Stmt__Group__3__Impl"
    // InternalStructuredTextParser.g:4944:1: rule__While_Stmt__Group__3__Impl : ( ( rule__While_Stmt__StatementsAssignment_3 ) ) ;
    public final void rule__While_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4948:1: ( ( ( rule__While_Stmt__StatementsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4949:1: ( ( rule__While_Stmt__StatementsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4949:1: ( ( rule__While_Stmt__StatementsAssignment_3 ) )
            // InternalStructuredTextParser.g:4950:2: ( rule__While_Stmt__StatementsAssignment_3 )
            {
             before(grammarAccess.getWhile_StmtAccess().getStatementsAssignment_3()); 
            // InternalStructuredTextParser.g:4951:2: ( rule__While_Stmt__StatementsAssignment_3 )
            // InternalStructuredTextParser.g:4951:3: rule__While_Stmt__StatementsAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__While_Stmt__StatementsAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getWhile_StmtAccess().getStatementsAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__Group__3__Impl"


    // $ANTLR start "rule__While_Stmt__Group__4"
    // InternalStructuredTextParser.g:4959:1: rule__While_Stmt__Group__4 : rule__While_Stmt__Group__4__Impl ;
    public final void rule__While_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4963:1: ( rule__While_Stmt__Group__4__Impl )
            // InternalStructuredTextParser.g:4964:2: rule__While_Stmt__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__While_Stmt__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__Group__4"


    // $ANTLR start "rule__While_Stmt__Group__4__Impl"
    // InternalStructuredTextParser.g:4970:1: rule__While_Stmt__Group__4__Impl : ( END_WHILE ) ;
    public final void rule__While_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4974:1: ( ( END_WHILE ) )
            // InternalStructuredTextParser.g:4975:1: ( END_WHILE )
            {
            // InternalStructuredTextParser.g:4975:1: ( END_WHILE )
            // InternalStructuredTextParser.g:4976:2: END_WHILE
            {
             before(grammarAccess.getWhile_StmtAccess().getEND_WHILEKeyword_4()); 
            match(input,END_WHILE,FOLLOW_2); 
             after(grammarAccess.getWhile_StmtAccess().getEND_WHILEKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__Group__4__Impl"


    // $ANTLR start "rule__Repeat_Stmt__Group__0"
    // InternalStructuredTextParser.g:4986:1: rule__Repeat_Stmt__Group__0 : rule__Repeat_Stmt__Group__0__Impl rule__Repeat_Stmt__Group__1 ;
    public final void rule__Repeat_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4990:1: ( rule__Repeat_Stmt__Group__0__Impl rule__Repeat_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4991:2: rule__Repeat_Stmt__Group__0__Impl rule__Repeat_Stmt__Group__1
            {
            pushFollow(FOLLOW_3);
            rule__Repeat_Stmt__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Repeat_Stmt__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__Group__0"


    // $ANTLR start "rule__Repeat_Stmt__Group__0__Impl"
    // InternalStructuredTextParser.g:4998:1: rule__Repeat_Stmt__Group__0__Impl : ( REPEAT ) ;
    public final void rule__Repeat_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5002:1: ( ( REPEAT ) )
            // InternalStructuredTextParser.g:5003:1: ( REPEAT )
            {
            // InternalStructuredTextParser.g:5003:1: ( REPEAT )
            // InternalStructuredTextParser.g:5004:2: REPEAT
            {
             before(grammarAccess.getRepeat_StmtAccess().getREPEATKeyword_0()); 
            match(input,REPEAT,FOLLOW_2); 
             after(grammarAccess.getRepeat_StmtAccess().getREPEATKeyword_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__Group__0__Impl"


    // $ANTLR start "rule__Repeat_Stmt__Group__1"
    // InternalStructuredTextParser.g:5013:1: rule__Repeat_Stmt__Group__1 : rule__Repeat_Stmt__Group__1__Impl rule__Repeat_Stmt__Group__2 ;
    public final void rule__Repeat_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5017:1: ( rule__Repeat_Stmt__Group__1__Impl rule__Repeat_Stmt__Group__2 )
            // InternalStructuredTextParser.g:5018:2: rule__Repeat_Stmt__Group__1__Impl rule__Repeat_Stmt__Group__2
            {
            pushFollow(FOLLOW_39);
            rule__Repeat_Stmt__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Repeat_Stmt__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__Group__1"


    // $ANTLR start "rule__Repeat_Stmt__Group__1__Impl"
    // InternalStructuredTextParser.g:5025:1: rule__Repeat_Stmt__Group__1__Impl : ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) ) ;
    public final void rule__Repeat_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5029:1: ( ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) ) )
            // InternalStructuredTextParser.g:5030:1: ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:5030:1: ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) )
            // InternalStructuredTextParser.g:5031:2: ( rule__Repeat_Stmt__StatementsAssignment_1 )
            {
             before(grammarAccess.getRepeat_StmtAccess().getStatementsAssignment_1()); 
            // InternalStructuredTextParser.g:5032:2: ( rule__Repeat_Stmt__StatementsAssignment_1 )
            // InternalStructuredTextParser.g:5032:3: rule__Repeat_Stmt__StatementsAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Repeat_Stmt__StatementsAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getRepeat_StmtAccess().getStatementsAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__Group__1__Impl"


    // $ANTLR start "rule__Repeat_Stmt__Group__2"
    // InternalStructuredTextParser.g:5040:1: rule__Repeat_Stmt__Group__2 : rule__Repeat_Stmt__Group__2__Impl rule__Repeat_Stmt__Group__3 ;
    public final void rule__Repeat_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5044:1: ( rule__Repeat_Stmt__Group__2__Impl rule__Repeat_Stmt__Group__3 )
            // InternalStructuredTextParser.g:5045:2: rule__Repeat_Stmt__Group__2__Impl rule__Repeat_Stmt__Group__3
            {
            pushFollow(FOLLOW_20);
            rule__Repeat_Stmt__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Repeat_Stmt__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__Group__2"


    // $ANTLR start "rule__Repeat_Stmt__Group__2__Impl"
    // InternalStructuredTextParser.g:5052:1: rule__Repeat_Stmt__Group__2__Impl : ( UNTIL ) ;
    public final void rule__Repeat_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5056:1: ( ( UNTIL ) )
            // InternalStructuredTextParser.g:5057:1: ( UNTIL )
            {
            // InternalStructuredTextParser.g:5057:1: ( UNTIL )
            // InternalStructuredTextParser.g:5058:2: UNTIL
            {
             before(grammarAccess.getRepeat_StmtAccess().getUNTILKeyword_2()); 
            match(input,UNTIL,FOLLOW_2); 
             after(grammarAccess.getRepeat_StmtAccess().getUNTILKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__Group__2__Impl"


    // $ANTLR start "rule__Repeat_Stmt__Group__3"
    // InternalStructuredTextParser.g:5067:1: rule__Repeat_Stmt__Group__3 : rule__Repeat_Stmt__Group__3__Impl rule__Repeat_Stmt__Group__4 ;
    public final void rule__Repeat_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5071:1: ( rule__Repeat_Stmt__Group__3__Impl rule__Repeat_Stmt__Group__4 )
            // InternalStructuredTextParser.g:5072:2: rule__Repeat_Stmt__Group__3__Impl rule__Repeat_Stmt__Group__4
            {
            pushFollow(FOLLOW_40);
            rule__Repeat_Stmt__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Repeat_Stmt__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__Group__3"


    // $ANTLR start "rule__Repeat_Stmt__Group__3__Impl"
    // InternalStructuredTextParser.g:5079:1: rule__Repeat_Stmt__Group__3__Impl : ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) ) ;
    public final void rule__Repeat_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5083:1: ( ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) ) )
            // InternalStructuredTextParser.g:5084:1: ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:5084:1: ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) )
            // InternalStructuredTextParser.g:5085:2: ( rule__Repeat_Stmt__ExpressionAssignment_3 )
            {
             before(grammarAccess.getRepeat_StmtAccess().getExpressionAssignment_3()); 
            // InternalStructuredTextParser.g:5086:2: ( rule__Repeat_Stmt__ExpressionAssignment_3 )
            // InternalStructuredTextParser.g:5086:3: rule__Repeat_Stmt__ExpressionAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Repeat_Stmt__ExpressionAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getRepeat_StmtAccess().getExpressionAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__Group__3__Impl"


    // $ANTLR start "rule__Repeat_Stmt__Group__4"
    // InternalStructuredTextParser.g:5094:1: rule__Repeat_Stmt__Group__4 : rule__Repeat_Stmt__Group__4__Impl ;
    public final void rule__Repeat_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5098:1: ( rule__Repeat_Stmt__Group__4__Impl )
            // InternalStructuredTextParser.g:5099:2: rule__Repeat_Stmt__Group__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Repeat_Stmt__Group__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__Group__4"


    // $ANTLR start "rule__Repeat_Stmt__Group__4__Impl"
    // InternalStructuredTextParser.g:5105:1: rule__Repeat_Stmt__Group__4__Impl : ( END_REPEAT ) ;
    public final void rule__Repeat_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5109:1: ( ( END_REPEAT ) )
            // InternalStructuredTextParser.g:5110:1: ( END_REPEAT )
            {
            // InternalStructuredTextParser.g:5110:1: ( END_REPEAT )
            // InternalStructuredTextParser.g:5111:2: END_REPEAT
            {
             before(grammarAccess.getRepeat_StmtAccess().getEND_REPEATKeyword_4()); 
            match(input,END_REPEAT,FOLLOW_2); 
             after(grammarAccess.getRepeat_StmtAccess().getEND_REPEATKeyword_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__Group__4__Impl"


    // $ANTLR start "rule__Or_Expression__Group__0"
    // InternalStructuredTextParser.g:5121:1: rule__Or_Expression__Group__0 : rule__Or_Expression__Group__0__Impl rule__Or_Expression__Group__1 ;
    public final void rule__Or_Expression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5125:1: ( rule__Or_Expression__Group__0__Impl rule__Or_Expression__Group__1 )
            // InternalStructuredTextParser.g:5126:2: rule__Or_Expression__Group__0__Impl rule__Or_Expression__Group__1
            {
            pushFollow(FOLLOW_41);
            rule__Or_Expression__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Or_Expression__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__Group__0"


    // $ANTLR start "rule__Or_Expression__Group__0__Impl"
    // InternalStructuredTextParser.g:5133:1: rule__Or_Expression__Group__0__Impl : ( ruleXor_Expr ) ;
    public final void rule__Or_Expression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5137:1: ( ( ruleXor_Expr ) )
            // InternalStructuredTextParser.g:5138:1: ( ruleXor_Expr )
            {
            // InternalStructuredTextParser.g:5138:1: ( ruleXor_Expr )
            // InternalStructuredTextParser.g:5139:2: ruleXor_Expr
            {
             before(grammarAccess.getOr_ExpressionAccess().getXor_ExprParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleXor_Expr();

            state._fsp--;

             after(grammarAccess.getOr_ExpressionAccess().getXor_ExprParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__Group__0__Impl"


    // $ANTLR start "rule__Or_Expression__Group__1"
    // InternalStructuredTextParser.g:5148:1: rule__Or_Expression__Group__1 : rule__Or_Expression__Group__1__Impl ;
    public final void rule__Or_Expression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5152:1: ( rule__Or_Expression__Group__1__Impl )
            // InternalStructuredTextParser.g:5153:2: rule__Or_Expression__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Or_Expression__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__Group__1"


    // $ANTLR start "rule__Or_Expression__Group__1__Impl"
    // InternalStructuredTextParser.g:5159:1: rule__Or_Expression__Group__1__Impl : ( ( rule__Or_Expression__Group_1__0 )* ) ;
    public final void rule__Or_Expression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5163:1: ( ( ( rule__Or_Expression__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5164:1: ( ( rule__Or_Expression__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5164:1: ( ( rule__Or_Expression__Group_1__0 )* )
            // InternalStructuredTextParser.g:5165:2: ( rule__Or_Expression__Group_1__0 )*
            {
             before(grammarAccess.getOr_ExpressionAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5166:2: ( rule__Or_Expression__Group_1__0 )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==OR) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5166:3: rule__Or_Expression__Group_1__0
            	    {
            	    pushFollow(FOLLOW_42);
            	    rule__Or_Expression__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

             after(grammarAccess.getOr_ExpressionAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__Group__1__Impl"


    // $ANTLR start "rule__Or_Expression__Group_1__0"
    // InternalStructuredTextParser.g:5175:1: rule__Or_Expression__Group_1__0 : rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1 ;
    public final void rule__Or_Expression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5179:1: ( rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1 )
            // InternalStructuredTextParser.g:5180:2: rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1
            {
            pushFollow(FOLLOW_41);
            rule__Or_Expression__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Or_Expression__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__Group_1__0"


    // $ANTLR start "rule__Or_Expression__Group_1__0__Impl"
    // InternalStructuredTextParser.g:5187:1: rule__Or_Expression__Group_1__0__Impl : ( () ) ;
    public final void rule__Or_Expression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5191:1: ( ( () ) )
            // InternalStructuredTextParser.g:5192:1: ( () )
            {
            // InternalStructuredTextParser.g:5192:1: ( () )
            // InternalStructuredTextParser.g:5193:2: ()
            {
             before(grammarAccess.getOr_ExpressionAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5194:2: ()
            // InternalStructuredTextParser.g:5194:3: 
            {
            }

             after(grammarAccess.getOr_ExpressionAccess().getBinaryExpressionLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__Group_1__0__Impl"


    // $ANTLR start "rule__Or_Expression__Group_1__1"
    // InternalStructuredTextParser.g:5202:1: rule__Or_Expression__Group_1__1 : rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2 ;
    public final void rule__Or_Expression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5206:1: ( rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2 )
            // InternalStructuredTextParser.g:5207:2: rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2
            {
            pushFollow(FOLLOW_20);
            rule__Or_Expression__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Or_Expression__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__Group_1__1"


    // $ANTLR start "rule__Or_Expression__Group_1__1__Impl"
    // InternalStructuredTextParser.g:5214:1: rule__Or_Expression__Group_1__1__Impl : ( ( rule__Or_Expression__OperatorAssignment_1_1 ) ) ;
    public final void rule__Or_Expression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5218:1: ( ( ( rule__Or_Expression__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5219:1: ( ( rule__Or_Expression__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5219:1: ( ( rule__Or_Expression__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5220:2: ( rule__Or_Expression__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getOr_ExpressionAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5221:2: ( rule__Or_Expression__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5221:3: rule__Or_Expression__OperatorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Or_Expression__OperatorAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getOr_ExpressionAccess().getOperatorAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__Group_1__1__Impl"


    // $ANTLR start "rule__Or_Expression__Group_1__2"
    // InternalStructuredTextParser.g:5229:1: rule__Or_Expression__Group_1__2 : rule__Or_Expression__Group_1__2__Impl ;
    public final void rule__Or_Expression__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5233:1: ( rule__Or_Expression__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5234:2: rule__Or_Expression__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Or_Expression__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__Group_1__2"


    // $ANTLR start "rule__Or_Expression__Group_1__2__Impl"
    // InternalStructuredTextParser.g:5240:1: rule__Or_Expression__Group_1__2__Impl : ( ( rule__Or_Expression__RightAssignment_1_2 ) ) ;
    public final void rule__Or_Expression__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5244:1: ( ( ( rule__Or_Expression__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5245:1: ( ( rule__Or_Expression__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5245:1: ( ( rule__Or_Expression__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5246:2: ( rule__Or_Expression__RightAssignment_1_2 )
            {
             before(grammarAccess.getOr_ExpressionAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5247:2: ( rule__Or_Expression__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5247:3: rule__Or_Expression__RightAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Or_Expression__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getOr_ExpressionAccess().getRightAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__Group_1__2__Impl"


    // $ANTLR start "rule__Xor_Expr__Group__0"
    // InternalStructuredTextParser.g:5256:1: rule__Xor_Expr__Group__0 : rule__Xor_Expr__Group__0__Impl rule__Xor_Expr__Group__1 ;
    public final void rule__Xor_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5260:1: ( rule__Xor_Expr__Group__0__Impl rule__Xor_Expr__Group__1 )
            // InternalStructuredTextParser.g:5261:2: rule__Xor_Expr__Group__0__Impl rule__Xor_Expr__Group__1
            {
            pushFollow(FOLLOW_43);
            rule__Xor_Expr__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Xor_Expr__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__Group__0"


    // $ANTLR start "rule__Xor_Expr__Group__0__Impl"
    // InternalStructuredTextParser.g:5268:1: rule__Xor_Expr__Group__0__Impl : ( ruleAnd_Expr ) ;
    public final void rule__Xor_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5272:1: ( ( ruleAnd_Expr ) )
            // InternalStructuredTextParser.g:5273:1: ( ruleAnd_Expr )
            {
            // InternalStructuredTextParser.g:5273:1: ( ruleAnd_Expr )
            // InternalStructuredTextParser.g:5274:2: ruleAnd_Expr
            {
             before(grammarAccess.getXor_ExprAccess().getAnd_ExprParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleAnd_Expr();

            state._fsp--;

             after(grammarAccess.getXor_ExprAccess().getAnd_ExprParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__Group__0__Impl"


    // $ANTLR start "rule__Xor_Expr__Group__1"
    // InternalStructuredTextParser.g:5283:1: rule__Xor_Expr__Group__1 : rule__Xor_Expr__Group__1__Impl ;
    public final void rule__Xor_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5287:1: ( rule__Xor_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5288:2: rule__Xor_Expr__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Xor_Expr__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__Group__1"


    // $ANTLR start "rule__Xor_Expr__Group__1__Impl"
    // InternalStructuredTextParser.g:5294:1: rule__Xor_Expr__Group__1__Impl : ( ( rule__Xor_Expr__Group_1__0 )* ) ;
    public final void rule__Xor_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5298:1: ( ( ( rule__Xor_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5299:1: ( ( rule__Xor_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5299:1: ( ( rule__Xor_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5300:2: ( rule__Xor_Expr__Group_1__0 )*
            {
             before(grammarAccess.getXor_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5301:2: ( rule__Xor_Expr__Group_1__0 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==XOR) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5301:3: rule__Xor_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_44);
            	    rule__Xor_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop45;
                }
            } while (true);

             after(grammarAccess.getXor_ExprAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__Group__1__Impl"


    // $ANTLR start "rule__Xor_Expr__Group_1__0"
    // InternalStructuredTextParser.g:5310:1: rule__Xor_Expr__Group_1__0 : rule__Xor_Expr__Group_1__0__Impl rule__Xor_Expr__Group_1__1 ;
    public final void rule__Xor_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5314:1: ( rule__Xor_Expr__Group_1__0__Impl rule__Xor_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5315:2: rule__Xor_Expr__Group_1__0__Impl rule__Xor_Expr__Group_1__1
            {
            pushFollow(FOLLOW_43);
            rule__Xor_Expr__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Xor_Expr__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__Group_1__0"


    // $ANTLR start "rule__Xor_Expr__Group_1__0__Impl"
    // InternalStructuredTextParser.g:5322:1: rule__Xor_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Xor_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5326:1: ( ( () ) )
            // InternalStructuredTextParser.g:5327:1: ( () )
            {
            // InternalStructuredTextParser.g:5327:1: ( () )
            // InternalStructuredTextParser.g:5328:2: ()
            {
             before(grammarAccess.getXor_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5329:2: ()
            // InternalStructuredTextParser.g:5329:3: 
            {
            }

             after(grammarAccess.getXor_ExprAccess().getBinaryExpressionLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__Group_1__0__Impl"


    // $ANTLR start "rule__Xor_Expr__Group_1__1"
    // InternalStructuredTextParser.g:5337:1: rule__Xor_Expr__Group_1__1 : rule__Xor_Expr__Group_1__1__Impl rule__Xor_Expr__Group_1__2 ;
    public final void rule__Xor_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5341:1: ( rule__Xor_Expr__Group_1__1__Impl rule__Xor_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5342:2: rule__Xor_Expr__Group_1__1__Impl rule__Xor_Expr__Group_1__2
            {
            pushFollow(FOLLOW_20);
            rule__Xor_Expr__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Xor_Expr__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__Group_1__1"


    // $ANTLR start "rule__Xor_Expr__Group_1__1__Impl"
    // InternalStructuredTextParser.g:5349:1: rule__Xor_Expr__Group_1__1__Impl : ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Xor_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5353:1: ( ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5354:1: ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5354:1: ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5355:2: ( rule__Xor_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getXor_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5356:2: ( rule__Xor_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5356:3: rule__Xor_Expr__OperatorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Xor_Expr__OperatorAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getXor_ExprAccess().getOperatorAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__Group_1__1__Impl"


    // $ANTLR start "rule__Xor_Expr__Group_1__2"
    // InternalStructuredTextParser.g:5364:1: rule__Xor_Expr__Group_1__2 : rule__Xor_Expr__Group_1__2__Impl ;
    public final void rule__Xor_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5368:1: ( rule__Xor_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5369:2: rule__Xor_Expr__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Xor_Expr__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__Group_1__2"


    // $ANTLR start "rule__Xor_Expr__Group_1__2__Impl"
    // InternalStructuredTextParser.g:5375:1: rule__Xor_Expr__Group_1__2__Impl : ( ( rule__Xor_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Xor_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5379:1: ( ( ( rule__Xor_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5380:1: ( ( rule__Xor_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5380:1: ( ( rule__Xor_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5381:2: ( rule__Xor_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getXor_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5382:2: ( rule__Xor_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5382:3: rule__Xor_Expr__RightAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Xor_Expr__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getXor_ExprAccess().getRightAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__Group_1__2__Impl"


    // $ANTLR start "rule__And_Expr__Group__0"
    // InternalStructuredTextParser.g:5391:1: rule__And_Expr__Group__0 : rule__And_Expr__Group__0__Impl rule__And_Expr__Group__1 ;
    public final void rule__And_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5395:1: ( rule__And_Expr__Group__0__Impl rule__And_Expr__Group__1 )
            // InternalStructuredTextParser.g:5396:2: rule__And_Expr__Group__0__Impl rule__And_Expr__Group__1
            {
            pushFollow(FOLLOW_45);
            rule__And_Expr__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__And_Expr__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__Group__0"


    // $ANTLR start "rule__And_Expr__Group__0__Impl"
    // InternalStructuredTextParser.g:5403:1: rule__And_Expr__Group__0__Impl : ( ruleCompare_Expr ) ;
    public final void rule__And_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5407:1: ( ( ruleCompare_Expr ) )
            // InternalStructuredTextParser.g:5408:1: ( ruleCompare_Expr )
            {
            // InternalStructuredTextParser.g:5408:1: ( ruleCompare_Expr )
            // InternalStructuredTextParser.g:5409:2: ruleCompare_Expr
            {
             before(grammarAccess.getAnd_ExprAccess().getCompare_ExprParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleCompare_Expr();

            state._fsp--;

             after(grammarAccess.getAnd_ExprAccess().getCompare_ExprParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__Group__0__Impl"


    // $ANTLR start "rule__And_Expr__Group__1"
    // InternalStructuredTextParser.g:5418:1: rule__And_Expr__Group__1 : rule__And_Expr__Group__1__Impl ;
    public final void rule__And_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5422:1: ( rule__And_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5423:2: rule__And_Expr__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__And_Expr__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__Group__1"


    // $ANTLR start "rule__And_Expr__Group__1__Impl"
    // InternalStructuredTextParser.g:5429:1: rule__And_Expr__Group__1__Impl : ( ( rule__And_Expr__Group_1__0 )* ) ;
    public final void rule__And_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5433:1: ( ( ( rule__And_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5434:1: ( ( rule__And_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5434:1: ( ( rule__And_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5435:2: ( rule__And_Expr__Group_1__0 )*
            {
             before(grammarAccess.getAnd_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5436:2: ( rule__And_Expr__Group_1__0 )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==AND||LA46_0==Ampersand) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5436:3: rule__And_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_46);
            	    rule__And_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);

             after(grammarAccess.getAnd_ExprAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__Group__1__Impl"


    // $ANTLR start "rule__And_Expr__Group_1__0"
    // InternalStructuredTextParser.g:5445:1: rule__And_Expr__Group_1__0 : rule__And_Expr__Group_1__0__Impl rule__And_Expr__Group_1__1 ;
    public final void rule__And_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5449:1: ( rule__And_Expr__Group_1__0__Impl rule__And_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5450:2: rule__And_Expr__Group_1__0__Impl rule__And_Expr__Group_1__1
            {
            pushFollow(FOLLOW_45);
            rule__And_Expr__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__And_Expr__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__Group_1__0"


    // $ANTLR start "rule__And_Expr__Group_1__0__Impl"
    // InternalStructuredTextParser.g:5457:1: rule__And_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__And_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5461:1: ( ( () ) )
            // InternalStructuredTextParser.g:5462:1: ( () )
            {
            // InternalStructuredTextParser.g:5462:1: ( () )
            // InternalStructuredTextParser.g:5463:2: ()
            {
             before(grammarAccess.getAnd_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5464:2: ()
            // InternalStructuredTextParser.g:5464:3: 
            {
            }

             after(grammarAccess.getAnd_ExprAccess().getBinaryExpressionLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__Group_1__0__Impl"


    // $ANTLR start "rule__And_Expr__Group_1__1"
    // InternalStructuredTextParser.g:5472:1: rule__And_Expr__Group_1__1 : rule__And_Expr__Group_1__1__Impl rule__And_Expr__Group_1__2 ;
    public final void rule__And_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5476:1: ( rule__And_Expr__Group_1__1__Impl rule__And_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5477:2: rule__And_Expr__Group_1__1__Impl rule__And_Expr__Group_1__2
            {
            pushFollow(FOLLOW_20);
            rule__And_Expr__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__And_Expr__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__Group_1__1"


    // $ANTLR start "rule__And_Expr__Group_1__1__Impl"
    // InternalStructuredTextParser.g:5484:1: rule__And_Expr__Group_1__1__Impl : ( ( rule__And_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__And_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5488:1: ( ( ( rule__And_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5489:1: ( ( rule__And_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5489:1: ( ( rule__And_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5490:2: ( rule__And_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getAnd_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5491:2: ( rule__And_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5491:3: rule__And_Expr__OperatorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__And_Expr__OperatorAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getAnd_ExprAccess().getOperatorAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__Group_1__1__Impl"


    // $ANTLR start "rule__And_Expr__Group_1__2"
    // InternalStructuredTextParser.g:5499:1: rule__And_Expr__Group_1__2 : rule__And_Expr__Group_1__2__Impl ;
    public final void rule__And_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5503:1: ( rule__And_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5504:2: rule__And_Expr__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__And_Expr__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__Group_1__2"


    // $ANTLR start "rule__And_Expr__Group_1__2__Impl"
    // InternalStructuredTextParser.g:5510:1: rule__And_Expr__Group_1__2__Impl : ( ( rule__And_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__And_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5514:1: ( ( ( rule__And_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5515:1: ( ( rule__And_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5515:1: ( ( rule__And_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5516:2: ( rule__And_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getAnd_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5517:2: ( rule__And_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5517:3: rule__And_Expr__RightAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__And_Expr__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getAnd_ExprAccess().getRightAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__Group_1__2__Impl"


    // $ANTLR start "rule__Compare_Expr__Group__0"
    // InternalStructuredTextParser.g:5526:1: rule__Compare_Expr__Group__0 : rule__Compare_Expr__Group__0__Impl rule__Compare_Expr__Group__1 ;
    public final void rule__Compare_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5530:1: ( rule__Compare_Expr__Group__0__Impl rule__Compare_Expr__Group__1 )
            // InternalStructuredTextParser.g:5531:2: rule__Compare_Expr__Group__0__Impl rule__Compare_Expr__Group__1
            {
            pushFollow(FOLLOW_47);
            rule__Compare_Expr__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Compare_Expr__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__Group__0"


    // $ANTLR start "rule__Compare_Expr__Group__0__Impl"
    // InternalStructuredTextParser.g:5538:1: rule__Compare_Expr__Group__0__Impl : ( ruleEqu_Expr ) ;
    public final void rule__Compare_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5542:1: ( ( ruleEqu_Expr ) )
            // InternalStructuredTextParser.g:5543:1: ( ruleEqu_Expr )
            {
            // InternalStructuredTextParser.g:5543:1: ( ruleEqu_Expr )
            // InternalStructuredTextParser.g:5544:2: ruleEqu_Expr
            {
             before(grammarAccess.getCompare_ExprAccess().getEqu_ExprParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleEqu_Expr();

            state._fsp--;

             after(grammarAccess.getCompare_ExprAccess().getEqu_ExprParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__Group__0__Impl"


    // $ANTLR start "rule__Compare_Expr__Group__1"
    // InternalStructuredTextParser.g:5553:1: rule__Compare_Expr__Group__1 : rule__Compare_Expr__Group__1__Impl ;
    public final void rule__Compare_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5557:1: ( rule__Compare_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5558:2: rule__Compare_Expr__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Compare_Expr__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__Group__1"


    // $ANTLR start "rule__Compare_Expr__Group__1__Impl"
    // InternalStructuredTextParser.g:5564:1: rule__Compare_Expr__Group__1__Impl : ( ( rule__Compare_Expr__Group_1__0 )* ) ;
    public final void rule__Compare_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5568:1: ( ( ( rule__Compare_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5569:1: ( ( rule__Compare_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5569:1: ( ( rule__Compare_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5570:2: ( rule__Compare_Expr__Group_1__0 )*
            {
             before(grammarAccess.getCompare_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5571:2: ( rule__Compare_Expr__Group_1__0 )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==LessThanSignGreaterThanSign||LA47_0==EqualsSign) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5571:3: rule__Compare_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_48);
            	    rule__Compare_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop47;
                }
            } while (true);

             after(grammarAccess.getCompare_ExprAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__Group__1__Impl"


    // $ANTLR start "rule__Compare_Expr__Group_1__0"
    // InternalStructuredTextParser.g:5580:1: rule__Compare_Expr__Group_1__0 : rule__Compare_Expr__Group_1__0__Impl rule__Compare_Expr__Group_1__1 ;
    public final void rule__Compare_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5584:1: ( rule__Compare_Expr__Group_1__0__Impl rule__Compare_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5585:2: rule__Compare_Expr__Group_1__0__Impl rule__Compare_Expr__Group_1__1
            {
            pushFollow(FOLLOW_47);
            rule__Compare_Expr__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Compare_Expr__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__Group_1__0"


    // $ANTLR start "rule__Compare_Expr__Group_1__0__Impl"
    // InternalStructuredTextParser.g:5592:1: rule__Compare_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Compare_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5596:1: ( ( () ) )
            // InternalStructuredTextParser.g:5597:1: ( () )
            {
            // InternalStructuredTextParser.g:5597:1: ( () )
            // InternalStructuredTextParser.g:5598:2: ()
            {
             before(grammarAccess.getCompare_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5599:2: ()
            // InternalStructuredTextParser.g:5599:3: 
            {
            }

             after(grammarAccess.getCompare_ExprAccess().getBinaryExpressionLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__Group_1__0__Impl"


    // $ANTLR start "rule__Compare_Expr__Group_1__1"
    // InternalStructuredTextParser.g:5607:1: rule__Compare_Expr__Group_1__1 : rule__Compare_Expr__Group_1__1__Impl rule__Compare_Expr__Group_1__2 ;
    public final void rule__Compare_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5611:1: ( rule__Compare_Expr__Group_1__1__Impl rule__Compare_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5612:2: rule__Compare_Expr__Group_1__1__Impl rule__Compare_Expr__Group_1__2
            {
            pushFollow(FOLLOW_20);
            rule__Compare_Expr__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Compare_Expr__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__Group_1__1"


    // $ANTLR start "rule__Compare_Expr__Group_1__1__Impl"
    // InternalStructuredTextParser.g:5619:1: rule__Compare_Expr__Group_1__1__Impl : ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Compare_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5623:1: ( ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5624:1: ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5624:1: ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5625:2: ( rule__Compare_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getCompare_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5626:2: ( rule__Compare_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5626:3: rule__Compare_Expr__OperatorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Compare_Expr__OperatorAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getCompare_ExprAccess().getOperatorAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__Group_1__1__Impl"


    // $ANTLR start "rule__Compare_Expr__Group_1__2"
    // InternalStructuredTextParser.g:5634:1: rule__Compare_Expr__Group_1__2 : rule__Compare_Expr__Group_1__2__Impl ;
    public final void rule__Compare_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5638:1: ( rule__Compare_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5639:2: rule__Compare_Expr__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Compare_Expr__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__Group_1__2"


    // $ANTLR start "rule__Compare_Expr__Group_1__2__Impl"
    // InternalStructuredTextParser.g:5645:1: rule__Compare_Expr__Group_1__2__Impl : ( ( rule__Compare_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Compare_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5649:1: ( ( ( rule__Compare_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5650:1: ( ( rule__Compare_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5650:1: ( ( rule__Compare_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5651:2: ( rule__Compare_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getCompare_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5652:2: ( rule__Compare_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5652:3: rule__Compare_Expr__RightAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Compare_Expr__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getCompare_ExprAccess().getRightAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__Group_1__2__Impl"


    // $ANTLR start "rule__Equ_Expr__Group__0"
    // InternalStructuredTextParser.g:5661:1: rule__Equ_Expr__Group__0 : rule__Equ_Expr__Group__0__Impl rule__Equ_Expr__Group__1 ;
    public final void rule__Equ_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5665:1: ( rule__Equ_Expr__Group__0__Impl rule__Equ_Expr__Group__1 )
            // InternalStructuredTextParser.g:5666:2: rule__Equ_Expr__Group__0__Impl rule__Equ_Expr__Group__1
            {
            pushFollow(FOLLOW_49);
            rule__Equ_Expr__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Equ_Expr__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__Group__0"


    // $ANTLR start "rule__Equ_Expr__Group__0__Impl"
    // InternalStructuredTextParser.g:5673:1: rule__Equ_Expr__Group__0__Impl : ( ruleAdd_Expr ) ;
    public final void rule__Equ_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5677:1: ( ( ruleAdd_Expr ) )
            // InternalStructuredTextParser.g:5678:1: ( ruleAdd_Expr )
            {
            // InternalStructuredTextParser.g:5678:1: ( ruleAdd_Expr )
            // InternalStructuredTextParser.g:5679:2: ruleAdd_Expr
            {
             before(grammarAccess.getEqu_ExprAccess().getAdd_ExprParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleAdd_Expr();

            state._fsp--;

             after(grammarAccess.getEqu_ExprAccess().getAdd_ExprParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__Group__0__Impl"


    // $ANTLR start "rule__Equ_Expr__Group__1"
    // InternalStructuredTextParser.g:5688:1: rule__Equ_Expr__Group__1 : rule__Equ_Expr__Group__1__Impl ;
    public final void rule__Equ_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5692:1: ( rule__Equ_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5693:2: rule__Equ_Expr__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Equ_Expr__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__Group__1"


    // $ANTLR start "rule__Equ_Expr__Group__1__Impl"
    // InternalStructuredTextParser.g:5699:1: rule__Equ_Expr__Group__1__Impl : ( ( rule__Equ_Expr__Group_1__0 )* ) ;
    public final void rule__Equ_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5703:1: ( ( ( rule__Equ_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5704:1: ( ( rule__Equ_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5704:1: ( ( rule__Equ_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5705:2: ( rule__Equ_Expr__Group_1__0 )*
            {
             before(grammarAccess.getEqu_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5706:2: ( rule__Equ_Expr__Group_1__0 )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==LessThanSignEqualsSign||LA48_0==GreaterThanSignEqualsSign||LA48_0==LessThanSign||LA48_0==GreaterThanSign) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5706:3: rule__Equ_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_50);
            	    rule__Equ_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

             after(grammarAccess.getEqu_ExprAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__Group__1__Impl"


    // $ANTLR start "rule__Equ_Expr__Group_1__0"
    // InternalStructuredTextParser.g:5715:1: rule__Equ_Expr__Group_1__0 : rule__Equ_Expr__Group_1__0__Impl rule__Equ_Expr__Group_1__1 ;
    public final void rule__Equ_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5719:1: ( rule__Equ_Expr__Group_1__0__Impl rule__Equ_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5720:2: rule__Equ_Expr__Group_1__0__Impl rule__Equ_Expr__Group_1__1
            {
            pushFollow(FOLLOW_49);
            rule__Equ_Expr__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Equ_Expr__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__Group_1__0"


    // $ANTLR start "rule__Equ_Expr__Group_1__0__Impl"
    // InternalStructuredTextParser.g:5727:1: rule__Equ_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Equ_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5731:1: ( ( () ) )
            // InternalStructuredTextParser.g:5732:1: ( () )
            {
            // InternalStructuredTextParser.g:5732:1: ( () )
            // InternalStructuredTextParser.g:5733:2: ()
            {
             before(grammarAccess.getEqu_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5734:2: ()
            // InternalStructuredTextParser.g:5734:3: 
            {
            }

             after(grammarAccess.getEqu_ExprAccess().getBinaryExpressionLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__Group_1__0__Impl"


    // $ANTLR start "rule__Equ_Expr__Group_1__1"
    // InternalStructuredTextParser.g:5742:1: rule__Equ_Expr__Group_1__1 : rule__Equ_Expr__Group_1__1__Impl rule__Equ_Expr__Group_1__2 ;
    public final void rule__Equ_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5746:1: ( rule__Equ_Expr__Group_1__1__Impl rule__Equ_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5747:2: rule__Equ_Expr__Group_1__1__Impl rule__Equ_Expr__Group_1__2
            {
            pushFollow(FOLLOW_20);
            rule__Equ_Expr__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Equ_Expr__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__Group_1__1"


    // $ANTLR start "rule__Equ_Expr__Group_1__1__Impl"
    // InternalStructuredTextParser.g:5754:1: rule__Equ_Expr__Group_1__1__Impl : ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Equ_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5758:1: ( ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5759:1: ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5759:1: ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5760:2: ( rule__Equ_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getEqu_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5761:2: ( rule__Equ_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5761:3: rule__Equ_Expr__OperatorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Equ_Expr__OperatorAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getEqu_ExprAccess().getOperatorAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__Group_1__1__Impl"


    // $ANTLR start "rule__Equ_Expr__Group_1__2"
    // InternalStructuredTextParser.g:5769:1: rule__Equ_Expr__Group_1__2 : rule__Equ_Expr__Group_1__2__Impl ;
    public final void rule__Equ_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5773:1: ( rule__Equ_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5774:2: rule__Equ_Expr__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Equ_Expr__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__Group_1__2"


    // $ANTLR start "rule__Equ_Expr__Group_1__2__Impl"
    // InternalStructuredTextParser.g:5780:1: rule__Equ_Expr__Group_1__2__Impl : ( ( rule__Equ_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Equ_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5784:1: ( ( ( rule__Equ_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5785:1: ( ( rule__Equ_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5785:1: ( ( rule__Equ_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5786:2: ( rule__Equ_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getEqu_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5787:2: ( rule__Equ_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5787:3: rule__Equ_Expr__RightAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Equ_Expr__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getEqu_ExprAccess().getRightAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__Group_1__2__Impl"


    // $ANTLR start "rule__Add_Expr__Group__0"
    // InternalStructuredTextParser.g:5796:1: rule__Add_Expr__Group__0 : rule__Add_Expr__Group__0__Impl rule__Add_Expr__Group__1 ;
    public final void rule__Add_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5800:1: ( rule__Add_Expr__Group__0__Impl rule__Add_Expr__Group__1 )
            // InternalStructuredTextParser.g:5801:2: rule__Add_Expr__Group__0__Impl rule__Add_Expr__Group__1
            {
            pushFollow(FOLLOW_51);
            rule__Add_Expr__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Add_Expr__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__Group__0"


    // $ANTLR start "rule__Add_Expr__Group__0__Impl"
    // InternalStructuredTextParser.g:5808:1: rule__Add_Expr__Group__0__Impl : ( ruleTerm ) ;
    public final void rule__Add_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5812:1: ( ( ruleTerm ) )
            // InternalStructuredTextParser.g:5813:1: ( ruleTerm )
            {
            // InternalStructuredTextParser.g:5813:1: ( ruleTerm )
            // InternalStructuredTextParser.g:5814:2: ruleTerm
            {
             before(grammarAccess.getAdd_ExprAccess().getTermParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleTerm();

            state._fsp--;

             after(grammarAccess.getAdd_ExprAccess().getTermParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__Group__0__Impl"


    // $ANTLR start "rule__Add_Expr__Group__1"
    // InternalStructuredTextParser.g:5823:1: rule__Add_Expr__Group__1 : rule__Add_Expr__Group__1__Impl ;
    public final void rule__Add_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5827:1: ( rule__Add_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5828:2: rule__Add_Expr__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Add_Expr__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__Group__1"


    // $ANTLR start "rule__Add_Expr__Group__1__Impl"
    // InternalStructuredTextParser.g:5834:1: rule__Add_Expr__Group__1__Impl : ( ( rule__Add_Expr__Group_1__0 )* ) ;
    public final void rule__Add_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5838:1: ( ( ( rule__Add_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5839:1: ( ( rule__Add_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5839:1: ( ( rule__Add_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5840:2: ( rule__Add_Expr__Group_1__0 )*
            {
             before(grammarAccess.getAdd_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5841:2: ( rule__Add_Expr__Group_1__0 )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==PlusSign||LA49_0==HyphenMinus) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5841:3: rule__Add_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_52);
            	    rule__Add_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop49;
                }
            } while (true);

             after(grammarAccess.getAdd_ExprAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__Group__1__Impl"


    // $ANTLR start "rule__Add_Expr__Group_1__0"
    // InternalStructuredTextParser.g:5850:1: rule__Add_Expr__Group_1__0 : rule__Add_Expr__Group_1__0__Impl rule__Add_Expr__Group_1__1 ;
    public final void rule__Add_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5854:1: ( rule__Add_Expr__Group_1__0__Impl rule__Add_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5855:2: rule__Add_Expr__Group_1__0__Impl rule__Add_Expr__Group_1__1
            {
            pushFollow(FOLLOW_51);
            rule__Add_Expr__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Add_Expr__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__Group_1__0"


    // $ANTLR start "rule__Add_Expr__Group_1__0__Impl"
    // InternalStructuredTextParser.g:5862:1: rule__Add_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Add_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5866:1: ( ( () ) )
            // InternalStructuredTextParser.g:5867:1: ( () )
            {
            // InternalStructuredTextParser.g:5867:1: ( () )
            // InternalStructuredTextParser.g:5868:2: ()
            {
             before(grammarAccess.getAdd_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5869:2: ()
            // InternalStructuredTextParser.g:5869:3: 
            {
            }

             after(grammarAccess.getAdd_ExprAccess().getBinaryExpressionLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__Group_1__0__Impl"


    // $ANTLR start "rule__Add_Expr__Group_1__1"
    // InternalStructuredTextParser.g:5877:1: rule__Add_Expr__Group_1__1 : rule__Add_Expr__Group_1__1__Impl rule__Add_Expr__Group_1__2 ;
    public final void rule__Add_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5881:1: ( rule__Add_Expr__Group_1__1__Impl rule__Add_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5882:2: rule__Add_Expr__Group_1__1__Impl rule__Add_Expr__Group_1__2
            {
            pushFollow(FOLLOW_20);
            rule__Add_Expr__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Add_Expr__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__Group_1__1"


    // $ANTLR start "rule__Add_Expr__Group_1__1__Impl"
    // InternalStructuredTextParser.g:5889:1: rule__Add_Expr__Group_1__1__Impl : ( ( rule__Add_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Add_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5893:1: ( ( ( rule__Add_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5894:1: ( ( rule__Add_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5894:1: ( ( rule__Add_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5895:2: ( rule__Add_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getAdd_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5896:2: ( rule__Add_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5896:3: rule__Add_Expr__OperatorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Add_Expr__OperatorAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getAdd_ExprAccess().getOperatorAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__Group_1__1__Impl"


    // $ANTLR start "rule__Add_Expr__Group_1__2"
    // InternalStructuredTextParser.g:5904:1: rule__Add_Expr__Group_1__2 : rule__Add_Expr__Group_1__2__Impl ;
    public final void rule__Add_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5908:1: ( rule__Add_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5909:2: rule__Add_Expr__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Add_Expr__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__Group_1__2"


    // $ANTLR start "rule__Add_Expr__Group_1__2__Impl"
    // InternalStructuredTextParser.g:5915:1: rule__Add_Expr__Group_1__2__Impl : ( ( rule__Add_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Add_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5919:1: ( ( ( rule__Add_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5920:1: ( ( rule__Add_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5920:1: ( ( rule__Add_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5921:2: ( rule__Add_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getAdd_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5922:2: ( rule__Add_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5922:3: rule__Add_Expr__RightAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Add_Expr__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getAdd_ExprAccess().getRightAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__Group_1__2__Impl"


    // $ANTLR start "rule__Term__Group__0"
    // InternalStructuredTextParser.g:5931:1: rule__Term__Group__0 : rule__Term__Group__0__Impl rule__Term__Group__1 ;
    public final void rule__Term__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5935:1: ( rule__Term__Group__0__Impl rule__Term__Group__1 )
            // InternalStructuredTextParser.g:5936:2: rule__Term__Group__0__Impl rule__Term__Group__1
            {
            pushFollow(FOLLOW_53);
            rule__Term__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Term__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group__0"


    // $ANTLR start "rule__Term__Group__0__Impl"
    // InternalStructuredTextParser.g:5943:1: rule__Term__Group__0__Impl : ( rulePower_Expr ) ;
    public final void rule__Term__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5947:1: ( ( rulePower_Expr ) )
            // InternalStructuredTextParser.g:5948:1: ( rulePower_Expr )
            {
            // InternalStructuredTextParser.g:5948:1: ( rulePower_Expr )
            // InternalStructuredTextParser.g:5949:2: rulePower_Expr
            {
             before(grammarAccess.getTermAccess().getPower_ExprParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            rulePower_Expr();

            state._fsp--;

             after(grammarAccess.getTermAccess().getPower_ExprParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group__0__Impl"


    // $ANTLR start "rule__Term__Group__1"
    // InternalStructuredTextParser.g:5958:1: rule__Term__Group__1 : rule__Term__Group__1__Impl ;
    public final void rule__Term__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5962:1: ( rule__Term__Group__1__Impl )
            // InternalStructuredTextParser.g:5963:2: rule__Term__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Term__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group__1"


    // $ANTLR start "rule__Term__Group__1__Impl"
    // InternalStructuredTextParser.g:5969:1: rule__Term__Group__1__Impl : ( ( rule__Term__Group_1__0 )* ) ;
    public final void rule__Term__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5973:1: ( ( ( rule__Term__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5974:1: ( ( rule__Term__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5974:1: ( ( rule__Term__Group_1__0 )* )
            // InternalStructuredTextParser.g:5975:2: ( rule__Term__Group_1__0 )*
            {
             before(grammarAccess.getTermAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5976:2: ( rule__Term__Group_1__0 )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==MOD||LA50_0==Asterisk||LA50_0==Solidus) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5976:3: rule__Term__Group_1__0
            	    {
            	    pushFollow(FOLLOW_54);
            	    rule__Term__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);

             after(grammarAccess.getTermAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group__1__Impl"


    // $ANTLR start "rule__Term__Group_1__0"
    // InternalStructuredTextParser.g:5985:1: rule__Term__Group_1__0 : rule__Term__Group_1__0__Impl rule__Term__Group_1__1 ;
    public final void rule__Term__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5989:1: ( rule__Term__Group_1__0__Impl rule__Term__Group_1__1 )
            // InternalStructuredTextParser.g:5990:2: rule__Term__Group_1__0__Impl rule__Term__Group_1__1
            {
            pushFollow(FOLLOW_53);
            rule__Term__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Term__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group_1__0"


    // $ANTLR start "rule__Term__Group_1__0__Impl"
    // InternalStructuredTextParser.g:5997:1: rule__Term__Group_1__0__Impl : ( () ) ;
    public final void rule__Term__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6001:1: ( ( () ) )
            // InternalStructuredTextParser.g:6002:1: ( () )
            {
            // InternalStructuredTextParser.g:6002:1: ( () )
            // InternalStructuredTextParser.g:6003:2: ()
            {
             before(grammarAccess.getTermAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:6004:2: ()
            // InternalStructuredTextParser.g:6004:3: 
            {
            }

             after(grammarAccess.getTermAccess().getBinaryExpressionLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group_1__0__Impl"


    // $ANTLR start "rule__Term__Group_1__1"
    // InternalStructuredTextParser.g:6012:1: rule__Term__Group_1__1 : rule__Term__Group_1__1__Impl rule__Term__Group_1__2 ;
    public final void rule__Term__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6016:1: ( rule__Term__Group_1__1__Impl rule__Term__Group_1__2 )
            // InternalStructuredTextParser.g:6017:2: rule__Term__Group_1__1__Impl rule__Term__Group_1__2
            {
            pushFollow(FOLLOW_20);
            rule__Term__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Term__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group_1__1"


    // $ANTLR start "rule__Term__Group_1__1__Impl"
    // InternalStructuredTextParser.g:6024:1: rule__Term__Group_1__1__Impl : ( ( rule__Term__OperatorAssignment_1_1 ) ) ;
    public final void rule__Term__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6028:1: ( ( ( rule__Term__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:6029:1: ( ( rule__Term__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:6029:1: ( ( rule__Term__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:6030:2: ( rule__Term__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getTermAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:6031:2: ( rule__Term__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:6031:3: rule__Term__OperatorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Term__OperatorAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getTermAccess().getOperatorAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group_1__1__Impl"


    // $ANTLR start "rule__Term__Group_1__2"
    // InternalStructuredTextParser.g:6039:1: rule__Term__Group_1__2 : rule__Term__Group_1__2__Impl ;
    public final void rule__Term__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6043:1: ( rule__Term__Group_1__2__Impl )
            // InternalStructuredTextParser.g:6044:2: rule__Term__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Term__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group_1__2"


    // $ANTLR start "rule__Term__Group_1__2__Impl"
    // InternalStructuredTextParser.g:6050:1: rule__Term__Group_1__2__Impl : ( ( rule__Term__RightAssignment_1_2 ) ) ;
    public final void rule__Term__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6054:1: ( ( ( rule__Term__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:6055:1: ( ( rule__Term__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:6055:1: ( ( rule__Term__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:6056:2: ( rule__Term__RightAssignment_1_2 )
            {
             before(grammarAccess.getTermAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:6057:2: ( rule__Term__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:6057:3: rule__Term__RightAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Term__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getTermAccess().getRightAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__Group_1__2__Impl"


    // $ANTLR start "rule__Power_Expr__Group__0"
    // InternalStructuredTextParser.g:6066:1: rule__Power_Expr__Group__0 : rule__Power_Expr__Group__0__Impl rule__Power_Expr__Group__1 ;
    public final void rule__Power_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6070:1: ( rule__Power_Expr__Group__0__Impl rule__Power_Expr__Group__1 )
            // InternalStructuredTextParser.g:6071:2: rule__Power_Expr__Group__0__Impl rule__Power_Expr__Group__1
            {
            pushFollow(FOLLOW_55);
            rule__Power_Expr__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Power_Expr__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__Group__0"


    // $ANTLR start "rule__Power_Expr__Group__0__Impl"
    // InternalStructuredTextParser.g:6078:1: rule__Power_Expr__Group__0__Impl : ( ruleUnary_Expr ) ;
    public final void rule__Power_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6082:1: ( ( ruleUnary_Expr ) )
            // InternalStructuredTextParser.g:6083:1: ( ruleUnary_Expr )
            {
            // InternalStructuredTextParser.g:6083:1: ( ruleUnary_Expr )
            // InternalStructuredTextParser.g:6084:2: ruleUnary_Expr
            {
             before(grammarAccess.getPower_ExprAccess().getUnary_ExprParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleUnary_Expr();

            state._fsp--;

             after(grammarAccess.getPower_ExprAccess().getUnary_ExprParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__Group__0__Impl"


    // $ANTLR start "rule__Power_Expr__Group__1"
    // InternalStructuredTextParser.g:6093:1: rule__Power_Expr__Group__1 : rule__Power_Expr__Group__1__Impl ;
    public final void rule__Power_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6097:1: ( rule__Power_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:6098:2: rule__Power_Expr__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Power_Expr__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__Group__1"


    // $ANTLR start "rule__Power_Expr__Group__1__Impl"
    // InternalStructuredTextParser.g:6104:1: rule__Power_Expr__Group__1__Impl : ( ( rule__Power_Expr__Group_1__0 )* ) ;
    public final void rule__Power_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6108:1: ( ( ( rule__Power_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:6109:1: ( ( rule__Power_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:6109:1: ( ( rule__Power_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:6110:2: ( rule__Power_Expr__Group_1__0 )*
            {
             before(grammarAccess.getPower_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:6111:2: ( rule__Power_Expr__Group_1__0 )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==AsteriskAsterisk) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalStructuredTextParser.g:6111:3: rule__Power_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_56);
            	    rule__Power_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop51;
                }
            } while (true);

             after(grammarAccess.getPower_ExprAccess().getGroup_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__Group__1__Impl"


    // $ANTLR start "rule__Power_Expr__Group_1__0"
    // InternalStructuredTextParser.g:6120:1: rule__Power_Expr__Group_1__0 : rule__Power_Expr__Group_1__0__Impl rule__Power_Expr__Group_1__1 ;
    public final void rule__Power_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6124:1: ( rule__Power_Expr__Group_1__0__Impl rule__Power_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:6125:2: rule__Power_Expr__Group_1__0__Impl rule__Power_Expr__Group_1__1
            {
            pushFollow(FOLLOW_55);
            rule__Power_Expr__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Power_Expr__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__Group_1__0"


    // $ANTLR start "rule__Power_Expr__Group_1__0__Impl"
    // InternalStructuredTextParser.g:6132:1: rule__Power_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Power_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6136:1: ( ( () ) )
            // InternalStructuredTextParser.g:6137:1: ( () )
            {
            // InternalStructuredTextParser.g:6137:1: ( () )
            // InternalStructuredTextParser.g:6138:2: ()
            {
             before(grammarAccess.getPower_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:6139:2: ()
            // InternalStructuredTextParser.g:6139:3: 
            {
            }

             after(grammarAccess.getPower_ExprAccess().getBinaryExpressionLeftAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__Group_1__0__Impl"


    // $ANTLR start "rule__Power_Expr__Group_1__1"
    // InternalStructuredTextParser.g:6147:1: rule__Power_Expr__Group_1__1 : rule__Power_Expr__Group_1__1__Impl rule__Power_Expr__Group_1__2 ;
    public final void rule__Power_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6151:1: ( rule__Power_Expr__Group_1__1__Impl rule__Power_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:6152:2: rule__Power_Expr__Group_1__1__Impl rule__Power_Expr__Group_1__2
            {
            pushFollow(FOLLOW_20);
            rule__Power_Expr__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Power_Expr__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__Group_1__1"


    // $ANTLR start "rule__Power_Expr__Group_1__1__Impl"
    // InternalStructuredTextParser.g:6159:1: rule__Power_Expr__Group_1__1__Impl : ( ( rule__Power_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Power_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6163:1: ( ( ( rule__Power_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:6164:1: ( ( rule__Power_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:6164:1: ( ( rule__Power_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:6165:2: ( rule__Power_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getPower_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:6166:2: ( rule__Power_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:6166:3: rule__Power_Expr__OperatorAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Power_Expr__OperatorAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getPower_ExprAccess().getOperatorAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__Group_1__1__Impl"


    // $ANTLR start "rule__Power_Expr__Group_1__2"
    // InternalStructuredTextParser.g:6174:1: rule__Power_Expr__Group_1__2 : rule__Power_Expr__Group_1__2__Impl ;
    public final void rule__Power_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6178:1: ( rule__Power_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:6179:2: rule__Power_Expr__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Power_Expr__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__Group_1__2"


    // $ANTLR start "rule__Power_Expr__Group_1__2__Impl"
    // InternalStructuredTextParser.g:6185:1: rule__Power_Expr__Group_1__2__Impl : ( ( rule__Power_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Power_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6189:1: ( ( ( rule__Power_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:6190:1: ( ( rule__Power_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:6190:1: ( ( rule__Power_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:6191:2: ( rule__Power_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getPower_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:6192:2: ( rule__Power_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:6192:3: rule__Power_Expr__RightAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Power_Expr__RightAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getPower_ExprAccess().getRightAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__Group_1__2__Impl"


    // $ANTLR start "rule__Unary_Expr__Group_0__0"
    // InternalStructuredTextParser.g:6201:1: rule__Unary_Expr__Group_0__0 : rule__Unary_Expr__Group_0__0__Impl rule__Unary_Expr__Group_0__1 ;
    public final void rule__Unary_Expr__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6205:1: ( rule__Unary_Expr__Group_0__0__Impl rule__Unary_Expr__Group_0__1 )
            // InternalStructuredTextParser.g:6206:2: rule__Unary_Expr__Group_0__0__Impl rule__Unary_Expr__Group_0__1
            {
            pushFollow(FOLLOW_57);
            rule__Unary_Expr__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Unary_Expr__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Unary_Expr__Group_0__0"


    // $ANTLR start "rule__Unary_Expr__Group_0__0__Impl"
    // InternalStructuredTextParser.g:6213:1: rule__Unary_Expr__Group_0__0__Impl : ( () ) ;
    public final void rule__Unary_Expr__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6217:1: ( ( () ) )
            // InternalStructuredTextParser.g:6218:1: ( () )
            {
            // InternalStructuredTextParser.g:6218:1: ( () )
            // InternalStructuredTextParser.g:6219:2: ()
            {
             before(grammarAccess.getUnary_ExprAccess().getUnaryExpressionAction_0_0()); 
            // InternalStructuredTextParser.g:6220:2: ()
            // InternalStructuredTextParser.g:6220:3: 
            {
            }

             after(grammarAccess.getUnary_ExprAccess().getUnaryExpressionAction_0_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Unary_Expr__Group_0__0__Impl"


    // $ANTLR start "rule__Unary_Expr__Group_0__1"
    // InternalStructuredTextParser.g:6228:1: rule__Unary_Expr__Group_0__1 : rule__Unary_Expr__Group_0__1__Impl rule__Unary_Expr__Group_0__2 ;
    public final void rule__Unary_Expr__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6232:1: ( rule__Unary_Expr__Group_0__1__Impl rule__Unary_Expr__Group_0__2 )
            // InternalStructuredTextParser.g:6233:2: rule__Unary_Expr__Group_0__1__Impl rule__Unary_Expr__Group_0__2
            {
            pushFollow(FOLLOW_58);
            rule__Unary_Expr__Group_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Unary_Expr__Group_0__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Unary_Expr__Group_0__1"


    // $ANTLR start "rule__Unary_Expr__Group_0__1__Impl"
    // InternalStructuredTextParser.g:6240:1: rule__Unary_Expr__Group_0__1__Impl : ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) ) ;
    public final void rule__Unary_Expr__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6244:1: ( ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) ) )
            // InternalStructuredTextParser.g:6245:1: ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) )
            {
            // InternalStructuredTextParser.g:6245:1: ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) )
            // InternalStructuredTextParser.g:6246:2: ( rule__Unary_Expr__OperatorAssignment_0_1 )
            {
             before(grammarAccess.getUnary_ExprAccess().getOperatorAssignment_0_1()); 
            // InternalStructuredTextParser.g:6247:2: ( rule__Unary_Expr__OperatorAssignment_0_1 )
            // InternalStructuredTextParser.g:6247:3: rule__Unary_Expr__OperatorAssignment_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Unary_Expr__OperatorAssignment_0_1();

            state._fsp--;


            }

             after(grammarAccess.getUnary_ExprAccess().getOperatorAssignment_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Unary_Expr__Group_0__1__Impl"


    // $ANTLR start "rule__Unary_Expr__Group_0__2"
    // InternalStructuredTextParser.g:6255:1: rule__Unary_Expr__Group_0__2 : rule__Unary_Expr__Group_0__2__Impl ;
    public final void rule__Unary_Expr__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6259:1: ( rule__Unary_Expr__Group_0__2__Impl )
            // InternalStructuredTextParser.g:6260:2: rule__Unary_Expr__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Unary_Expr__Group_0__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Unary_Expr__Group_0__2"


    // $ANTLR start "rule__Unary_Expr__Group_0__2__Impl"
    // InternalStructuredTextParser.g:6266:1: rule__Unary_Expr__Group_0__2__Impl : ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) ) ;
    public final void rule__Unary_Expr__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6270:1: ( ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) ) )
            // InternalStructuredTextParser.g:6271:1: ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) )
            {
            // InternalStructuredTextParser.g:6271:1: ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) )
            // InternalStructuredTextParser.g:6272:2: ( rule__Unary_Expr__ExpressionAssignment_0_2 )
            {
             before(grammarAccess.getUnary_ExprAccess().getExpressionAssignment_0_2()); 
            // InternalStructuredTextParser.g:6273:2: ( rule__Unary_Expr__ExpressionAssignment_0_2 )
            // InternalStructuredTextParser.g:6273:3: rule__Unary_Expr__ExpressionAssignment_0_2
            {
            pushFollow(FOLLOW_2);
            rule__Unary_Expr__ExpressionAssignment_0_2();

            state._fsp--;


            }

             after(grammarAccess.getUnary_ExprAccess().getExpressionAssignment_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Unary_Expr__Group_0__2__Impl"


    // $ANTLR start "rule__Primary_Expr__Group_2__0"
    // InternalStructuredTextParser.g:6282:1: rule__Primary_Expr__Group_2__0 : rule__Primary_Expr__Group_2__0__Impl rule__Primary_Expr__Group_2__1 ;
    public final void rule__Primary_Expr__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6286:1: ( rule__Primary_Expr__Group_2__0__Impl rule__Primary_Expr__Group_2__1 )
            // InternalStructuredTextParser.g:6287:2: rule__Primary_Expr__Group_2__0__Impl rule__Primary_Expr__Group_2__1
            {
            pushFollow(FOLLOW_20);
            rule__Primary_Expr__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Primary_Expr__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary_Expr__Group_2__0"


    // $ANTLR start "rule__Primary_Expr__Group_2__0__Impl"
    // InternalStructuredTextParser.g:6294:1: rule__Primary_Expr__Group_2__0__Impl : ( LeftParenthesis ) ;
    public final void rule__Primary_Expr__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6298:1: ( ( LeftParenthesis ) )
            // InternalStructuredTextParser.g:6299:1: ( LeftParenthesis )
            {
            // InternalStructuredTextParser.g:6299:1: ( LeftParenthesis )
            // InternalStructuredTextParser.g:6300:2: LeftParenthesis
            {
             before(grammarAccess.getPrimary_ExprAccess().getLeftParenthesisKeyword_2_0()); 
            match(input,LeftParenthesis,FOLLOW_2); 
             after(grammarAccess.getPrimary_ExprAccess().getLeftParenthesisKeyword_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary_Expr__Group_2__0__Impl"


    // $ANTLR start "rule__Primary_Expr__Group_2__1"
    // InternalStructuredTextParser.g:6309:1: rule__Primary_Expr__Group_2__1 : rule__Primary_Expr__Group_2__1__Impl rule__Primary_Expr__Group_2__2 ;
    public final void rule__Primary_Expr__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6313:1: ( rule__Primary_Expr__Group_2__1__Impl rule__Primary_Expr__Group_2__2 )
            // InternalStructuredTextParser.g:6314:2: rule__Primary_Expr__Group_2__1__Impl rule__Primary_Expr__Group_2__2
            {
            pushFollow(FOLLOW_23);
            rule__Primary_Expr__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Primary_Expr__Group_2__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary_Expr__Group_2__1"


    // $ANTLR start "rule__Primary_Expr__Group_2__1__Impl"
    // InternalStructuredTextParser.g:6321:1: rule__Primary_Expr__Group_2__1__Impl : ( ruleExpression ) ;
    public final void rule__Primary_Expr__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6325:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:6326:1: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:6326:1: ( ruleExpression )
            // InternalStructuredTextParser.g:6327:2: ruleExpression
            {
             before(grammarAccess.getPrimary_ExprAccess().getExpressionParserRuleCall_2_1()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getPrimary_ExprAccess().getExpressionParserRuleCall_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary_Expr__Group_2__1__Impl"


    // $ANTLR start "rule__Primary_Expr__Group_2__2"
    // InternalStructuredTextParser.g:6336:1: rule__Primary_Expr__Group_2__2 : rule__Primary_Expr__Group_2__2__Impl ;
    public final void rule__Primary_Expr__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6340:1: ( rule__Primary_Expr__Group_2__2__Impl )
            // InternalStructuredTextParser.g:6341:2: rule__Primary_Expr__Group_2__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Primary_Expr__Group_2__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary_Expr__Group_2__2"


    // $ANTLR start "rule__Primary_Expr__Group_2__2__Impl"
    // InternalStructuredTextParser.g:6347:1: rule__Primary_Expr__Group_2__2__Impl : ( RightParenthesis ) ;
    public final void rule__Primary_Expr__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6351:1: ( ( RightParenthesis ) )
            // InternalStructuredTextParser.g:6352:1: ( RightParenthesis )
            {
            // InternalStructuredTextParser.g:6352:1: ( RightParenthesis )
            // InternalStructuredTextParser.g:6353:2: RightParenthesis
            {
             before(grammarAccess.getPrimary_ExprAccess().getRightParenthesisKeyword_2_2()); 
            match(input,RightParenthesis,FOLLOW_2); 
             after(grammarAccess.getPrimary_ExprAccess().getRightParenthesisKeyword_2_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Primary_Expr__Group_2__2__Impl"


    // $ANTLR start "rule__Func_Call__Group__0"
    // InternalStructuredTextParser.g:6363:1: rule__Func_Call__Group__0 : rule__Func_Call__Group__0__Impl rule__Func_Call__Group__1 ;
    public final void rule__Func_Call__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6367:1: ( rule__Func_Call__Group__0__Impl rule__Func_Call__Group__1 )
            // InternalStructuredTextParser.g:6368:2: rule__Func_Call__Group__0__Impl rule__Func_Call__Group__1
            {
            pushFollow(FOLLOW_22);
            rule__Func_Call__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Func_Call__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group__0"


    // $ANTLR start "rule__Func_Call__Group__0__Impl"
    // InternalStructuredTextParser.g:6375:1: rule__Func_Call__Group__0__Impl : ( ( rule__Func_Call__FuncAssignment_0 ) ) ;
    public final void rule__Func_Call__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6379:1: ( ( ( rule__Func_Call__FuncAssignment_0 ) ) )
            // InternalStructuredTextParser.g:6380:1: ( ( rule__Func_Call__FuncAssignment_0 ) )
            {
            // InternalStructuredTextParser.g:6380:1: ( ( rule__Func_Call__FuncAssignment_0 ) )
            // InternalStructuredTextParser.g:6381:2: ( rule__Func_Call__FuncAssignment_0 )
            {
             before(grammarAccess.getFunc_CallAccess().getFuncAssignment_0()); 
            // InternalStructuredTextParser.g:6382:2: ( rule__Func_Call__FuncAssignment_0 )
            // InternalStructuredTextParser.g:6382:3: rule__Func_Call__FuncAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__Func_Call__FuncAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getFunc_CallAccess().getFuncAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group__0__Impl"


    // $ANTLR start "rule__Func_Call__Group__1"
    // InternalStructuredTextParser.g:6390:1: rule__Func_Call__Group__1 : rule__Func_Call__Group__1__Impl rule__Func_Call__Group__2 ;
    public final void rule__Func_Call__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6394:1: ( rule__Func_Call__Group__1__Impl rule__Func_Call__Group__2 )
            // InternalStructuredTextParser.g:6395:2: rule__Func_Call__Group__1__Impl rule__Func_Call__Group__2
            {
            pushFollow(FOLLOW_59);
            rule__Func_Call__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Func_Call__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group__1"


    // $ANTLR start "rule__Func_Call__Group__1__Impl"
    // InternalStructuredTextParser.g:6402:1: rule__Func_Call__Group__1__Impl : ( LeftParenthesis ) ;
    public final void rule__Func_Call__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6406:1: ( ( LeftParenthesis ) )
            // InternalStructuredTextParser.g:6407:1: ( LeftParenthesis )
            {
            // InternalStructuredTextParser.g:6407:1: ( LeftParenthesis )
            // InternalStructuredTextParser.g:6408:2: LeftParenthesis
            {
             before(grammarAccess.getFunc_CallAccess().getLeftParenthesisKeyword_1()); 
            match(input,LeftParenthesis,FOLLOW_2); 
             after(grammarAccess.getFunc_CallAccess().getLeftParenthesisKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group__1__Impl"


    // $ANTLR start "rule__Func_Call__Group__2"
    // InternalStructuredTextParser.g:6417:1: rule__Func_Call__Group__2 : rule__Func_Call__Group__2__Impl rule__Func_Call__Group__3 ;
    public final void rule__Func_Call__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6421:1: ( rule__Func_Call__Group__2__Impl rule__Func_Call__Group__3 )
            // InternalStructuredTextParser.g:6422:2: rule__Func_Call__Group__2__Impl rule__Func_Call__Group__3
            {
            pushFollow(FOLLOW_59);
            rule__Func_Call__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Func_Call__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group__2"


    // $ANTLR start "rule__Func_Call__Group__2__Impl"
    // InternalStructuredTextParser.g:6429:1: rule__Func_Call__Group__2__Impl : ( ( rule__Func_Call__Group_2__0 )? ) ;
    public final void rule__Func_Call__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6433:1: ( ( ( rule__Func_Call__Group_2__0 )? ) )
            // InternalStructuredTextParser.g:6434:1: ( ( rule__Func_Call__Group_2__0 )? )
            {
            // InternalStructuredTextParser.g:6434:1: ( ( rule__Func_Call__Group_2__0 )? )
            // InternalStructuredTextParser.g:6435:2: ( rule__Func_Call__Group_2__0 )?
            {
             before(grammarAccess.getFunc_CallAccess().getGroup_2()); 
            // InternalStructuredTextParser.g:6436:2: ( rule__Func_Call__Group_2__0 )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==WSTRING||LA52_0==STRING||LA52_0==FALSE||LA52_0==LREAL||(LA52_0>=UDINT && LA52_0<=ULINT)||(LA52_0>=USINT && LA52_0<=WCHAR)||LA52_0==BOOL||LA52_0==CHAR||LA52_0==DINT||LA52_0==LINT||(LA52_0>=REAL && LA52_0<=SINT)||(LA52_0>=TIME && LA52_0<=UINT)||LA52_0==INT||LA52_0==NOT||LA52_0==DT||LA52_0==LT||LA52_0==LeftParenthesis||LA52_0==PlusSign||LA52_0==HyphenMinus||LA52_0==T||(LA52_0>=RULE_UNSIGNED_INT && LA52_0<=RULE_HEX_INT)||LA52_0==RULE_S_BYTE_CHAR_STR||LA52_0==RULE_D_BYTE_CHAR_STR) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalStructuredTextParser.g:6436:3: rule__Func_Call__Group_2__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group__2__Impl"


    // $ANTLR start "rule__Func_Call__Group__3"
    // InternalStructuredTextParser.g:6444:1: rule__Func_Call__Group__3 : rule__Func_Call__Group__3__Impl ;
    public final void rule__Func_Call__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6448:1: ( rule__Func_Call__Group__3__Impl )
            // InternalStructuredTextParser.g:6449:2: rule__Func_Call__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Func_Call__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group__3"


    // $ANTLR start "rule__Func_Call__Group__3__Impl"
    // InternalStructuredTextParser.g:6455:1: rule__Func_Call__Group__3__Impl : ( RightParenthesis ) ;
    public final void rule__Func_Call__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6459:1: ( ( RightParenthesis ) )
            // InternalStructuredTextParser.g:6460:1: ( RightParenthesis )
            {
            // InternalStructuredTextParser.g:6460:1: ( RightParenthesis )
            // InternalStructuredTextParser.g:6461:2: RightParenthesis
            {
             before(grammarAccess.getFunc_CallAccess().getRightParenthesisKeyword_3()); 
            match(input,RightParenthesis,FOLLOW_2); 
             after(grammarAccess.getFunc_CallAccess().getRightParenthesisKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group__3__Impl"


    // $ANTLR start "rule__Func_Call__Group_2__0"
    // InternalStructuredTextParser.g:6471:1: rule__Func_Call__Group_2__0 : rule__Func_Call__Group_2__0__Impl rule__Func_Call__Group_2__1 ;
    public final void rule__Func_Call__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6475:1: ( rule__Func_Call__Group_2__0__Impl rule__Func_Call__Group_2__1 )
            // InternalStructuredTextParser.g:6476:2: rule__Func_Call__Group_2__0__Impl rule__Func_Call__Group_2__1
            {
            pushFollow(FOLLOW_60);
            rule__Func_Call__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Func_Call__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group_2__0"


    // $ANTLR start "rule__Func_Call__Group_2__0__Impl"
    // InternalStructuredTextParser.g:6483:1: rule__Func_Call__Group_2__0__Impl : ( ( rule__Func_Call__ArgsAssignment_2_0 ) ) ;
    public final void rule__Func_Call__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6487:1: ( ( ( rule__Func_Call__ArgsAssignment_2_0 ) ) )
            // InternalStructuredTextParser.g:6488:1: ( ( rule__Func_Call__ArgsAssignment_2_0 ) )
            {
            // InternalStructuredTextParser.g:6488:1: ( ( rule__Func_Call__ArgsAssignment_2_0 ) )
            // InternalStructuredTextParser.g:6489:2: ( rule__Func_Call__ArgsAssignment_2_0 )
            {
             before(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_0()); 
            // InternalStructuredTextParser.g:6490:2: ( rule__Func_Call__ArgsAssignment_2_0 )
            // InternalStructuredTextParser.g:6490:3: rule__Func_Call__ArgsAssignment_2_0
            {
            pushFollow(FOLLOW_2);
            rule__Func_Call__ArgsAssignment_2_0();

            state._fsp--;


            }

             after(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group_2__0__Impl"


    // $ANTLR start "rule__Func_Call__Group_2__1"
    // InternalStructuredTextParser.g:6498:1: rule__Func_Call__Group_2__1 : rule__Func_Call__Group_2__1__Impl ;
    public final void rule__Func_Call__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6502:1: ( rule__Func_Call__Group_2__1__Impl )
            // InternalStructuredTextParser.g:6503:2: rule__Func_Call__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Func_Call__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group_2__1"


    // $ANTLR start "rule__Func_Call__Group_2__1__Impl"
    // InternalStructuredTextParser.g:6509:1: rule__Func_Call__Group_2__1__Impl : ( ( rule__Func_Call__Group_2_1__0 )* ) ;
    public final void rule__Func_Call__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6513:1: ( ( ( rule__Func_Call__Group_2_1__0 )* ) )
            // InternalStructuredTextParser.g:6514:1: ( ( rule__Func_Call__Group_2_1__0 )* )
            {
            // InternalStructuredTextParser.g:6514:1: ( ( rule__Func_Call__Group_2_1__0 )* )
            // InternalStructuredTextParser.g:6515:2: ( rule__Func_Call__Group_2_1__0 )*
            {
             before(grammarAccess.getFunc_CallAccess().getGroup_2_1()); 
            // InternalStructuredTextParser.g:6516:2: ( rule__Func_Call__Group_2_1__0 )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==Comma) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalStructuredTextParser.g:6516:3: rule__Func_Call__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_31);
            	    rule__Func_Call__Group_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop53;
                }
            } while (true);

             after(grammarAccess.getFunc_CallAccess().getGroup_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group_2__1__Impl"


    // $ANTLR start "rule__Func_Call__Group_2_1__0"
    // InternalStructuredTextParser.g:6525:1: rule__Func_Call__Group_2_1__0 : rule__Func_Call__Group_2_1__0__Impl rule__Func_Call__Group_2_1__1 ;
    public final void rule__Func_Call__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6529:1: ( rule__Func_Call__Group_2_1__0__Impl rule__Func_Call__Group_2_1__1 )
            // InternalStructuredTextParser.g:6530:2: rule__Func_Call__Group_2_1__0__Impl rule__Func_Call__Group_2_1__1
            {
            pushFollow(FOLLOW_20);
            rule__Func_Call__Group_2_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Func_Call__Group_2_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group_2_1__0"


    // $ANTLR start "rule__Func_Call__Group_2_1__0__Impl"
    // InternalStructuredTextParser.g:6537:1: rule__Func_Call__Group_2_1__0__Impl : ( Comma ) ;
    public final void rule__Func_Call__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6541:1: ( ( Comma ) )
            // InternalStructuredTextParser.g:6542:1: ( Comma )
            {
            // InternalStructuredTextParser.g:6542:1: ( Comma )
            // InternalStructuredTextParser.g:6543:2: Comma
            {
             before(grammarAccess.getFunc_CallAccess().getCommaKeyword_2_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getFunc_CallAccess().getCommaKeyword_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group_2_1__0__Impl"


    // $ANTLR start "rule__Func_Call__Group_2_1__1"
    // InternalStructuredTextParser.g:6552:1: rule__Func_Call__Group_2_1__1 : rule__Func_Call__Group_2_1__1__Impl ;
    public final void rule__Func_Call__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6556:1: ( rule__Func_Call__Group_2_1__1__Impl )
            // InternalStructuredTextParser.g:6557:2: rule__Func_Call__Group_2_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Func_Call__Group_2_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group_2_1__1"


    // $ANTLR start "rule__Func_Call__Group_2_1__1__Impl"
    // InternalStructuredTextParser.g:6563:1: rule__Func_Call__Group_2_1__1__Impl : ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) ) ;
    public final void rule__Func_Call__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6567:1: ( ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) ) )
            // InternalStructuredTextParser.g:6568:1: ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) )
            {
            // InternalStructuredTextParser.g:6568:1: ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) )
            // InternalStructuredTextParser.g:6569:2: ( rule__Func_Call__ArgsAssignment_2_1_1 )
            {
             before(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_1_1()); 
            // InternalStructuredTextParser.g:6570:2: ( rule__Func_Call__ArgsAssignment_2_1_1 )
            // InternalStructuredTextParser.g:6570:3: rule__Func_Call__ArgsAssignment_2_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Func_Call__ArgsAssignment_2_1_1();

            state._fsp--;


            }

             after(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__Group_2_1__1__Impl"


    // $ANTLR start "rule__Param_Assign_In__Group__0"
    // InternalStructuredTextParser.g:6579:1: rule__Param_Assign_In__Group__0 : rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1 ;
    public final void rule__Param_Assign_In__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6583:1: ( rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1 )
            // InternalStructuredTextParser.g:6584:2: rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1
            {
            pushFollow(FOLLOW_20);
            rule__Param_Assign_In__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Param_Assign_In__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_In__Group__0"


    // $ANTLR start "rule__Param_Assign_In__Group__0__Impl"
    // InternalStructuredTextParser.g:6591:1: rule__Param_Assign_In__Group__0__Impl : ( ( rule__Param_Assign_In__Group_0__0 )? ) ;
    public final void rule__Param_Assign_In__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6595:1: ( ( ( rule__Param_Assign_In__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:6596:1: ( ( rule__Param_Assign_In__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:6596:1: ( ( rule__Param_Assign_In__Group_0__0 )? )
            // InternalStructuredTextParser.g:6597:2: ( rule__Param_Assign_In__Group_0__0 )?
            {
             before(grammarAccess.getParam_Assign_InAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:6598:2: ( rule__Param_Assign_In__Group_0__0 )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==RULE_ID) ) {
                int LA54_1 = input.LA(2);

                if ( (LA54_1==ColonEqualsSign) ) {
                    alt54=1;
                }
            }
            switch (alt54) {
                case 1 :
                    // InternalStructuredTextParser.g:6598:3: rule__Param_Assign_In__Group_0__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_In__Group__0__Impl"


    // $ANTLR start "rule__Param_Assign_In__Group__1"
    // InternalStructuredTextParser.g:6606:1: rule__Param_Assign_In__Group__1 : rule__Param_Assign_In__Group__1__Impl ;
    public final void rule__Param_Assign_In__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6610:1: ( rule__Param_Assign_In__Group__1__Impl )
            // InternalStructuredTextParser.g:6611:2: rule__Param_Assign_In__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Param_Assign_In__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_In__Group__1"


    // $ANTLR start "rule__Param_Assign_In__Group__1__Impl"
    // InternalStructuredTextParser.g:6617:1: rule__Param_Assign_In__Group__1__Impl : ( ( rule__Param_Assign_In__ExprAssignment_1 ) ) ;
    public final void rule__Param_Assign_In__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6621:1: ( ( ( rule__Param_Assign_In__ExprAssignment_1 ) ) )
            // InternalStructuredTextParser.g:6622:1: ( ( rule__Param_Assign_In__ExprAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:6622:1: ( ( rule__Param_Assign_In__ExprAssignment_1 ) )
            // InternalStructuredTextParser.g:6623:2: ( rule__Param_Assign_In__ExprAssignment_1 )
            {
             before(grammarAccess.getParam_Assign_InAccess().getExprAssignment_1()); 
            // InternalStructuredTextParser.g:6624:2: ( rule__Param_Assign_In__ExprAssignment_1 )
            // InternalStructuredTextParser.g:6624:3: rule__Param_Assign_In__ExprAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Param_Assign_In__ExprAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getParam_Assign_InAccess().getExprAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_In__Group__1__Impl"


    // $ANTLR start "rule__Param_Assign_In__Group_0__0"
    // InternalStructuredTextParser.g:6633:1: rule__Param_Assign_In__Group_0__0 : rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1 ;
    public final void rule__Param_Assign_In__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6637:1: ( rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1 )
            // InternalStructuredTextParser.g:6638:2: rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1
            {
            pushFollow(FOLLOW_10);
            rule__Param_Assign_In__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Param_Assign_In__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_In__Group_0__0"


    // $ANTLR start "rule__Param_Assign_In__Group_0__0__Impl"
    // InternalStructuredTextParser.g:6645:1: rule__Param_Assign_In__Group_0__0__Impl : ( ( rule__Param_Assign_In__VarAssignment_0_0 ) ) ;
    public final void rule__Param_Assign_In__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6649:1: ( ( ( rule__Param_Assign_In__VarAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:6650:1: ( ( rule__Param_Assign_In__VarAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:6650:1: ( ( rule__Param_Assign_In__VarAssignment_0_0 ) )
            // InternalStructuredTextParser.g:6651:2: ( rule__Param_Assign_In__VarAssignment_0_0 )
            {
             before(grammarAccess.getParam_Assign_InAccess().getVarAssignment_0_0()); 
            // InternalStructuredTextParser.g:6652:2: ( rule__Param_Assign_In__VarAssignment_0_0 )
            // InternalStructuredTextParser.g:6652:3: rule__Param_Assign_In__VarAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Param_Assign_In__VarAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getParam_Assign_InAccess().getVarAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_In__Group_0__0__Impl"


    // $ANTLR start "rule__Param_Assign_In__Group_0__1"
    // InternalStructuredTextParser.g:6660:1: rule__Param_Assign_In__Group_0__1 : rule__Param_Assign_In__Group_0__1__Impl ;
    public final void rule__Param_Assign_In__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6664:1: ( rule__Param_Assign_In__Group_0__1__Impl )
            // InternalStructuredTextParser.g:6665:2: rule__Param_Assign_In__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Param_Assign_In__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_In__Group_0__1"


    // $ANTLR start "rule__Param_Assign_In__Group_0__1__Impl"
    // InternalStructuredTextParser.g:6671:1: rule__Param_Assign_In__Group_0__1__Impl : ( ColonEqualsSign ) ;
    public final void rule__Param_Assign_In__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6675:1: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:6676:1: ( ColonEqualsSign )
            {
            // InternalStructuredTextParser.g:6676:1: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:6677:2: ColonEqualsSign
            {
             before(grammarAccess.getParam_Assign_InAccess().getColonEqualsSignKeyword_0_1()); 
            match(input,ColonEqualsSign,FOLLOW_2); 
             after(grammarAccess.getParam_Assign_InAccess().getColonEqualsSignKeyword_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_In__Group_0__1__Impl"


    // $ANTLR start "rule__Param_Assign_Out__Group__0"
    // InternalStructuredTextParser.g:6687:1: rule__Param_Assign_Out__Group__0 : rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1 ;
    public final void rule__Param_Assign_Out__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6691:1: ( rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1 )
            // InternalStructuredTextParser.g:6692:2: rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1
            {
            pushFollow(FOLLOW_20);
            rule__Param_Assign_Out__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Param_Assign_Out__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__Group__0"


    // $ANTLR start "rule__Param_Assign_Out__Group__0__Impl"
    // InternalStructuredTextParser.g:6699:1: rule__Param_Assign_Out__Group__0__Impl : ( ( rule__Param_Assign_Out__NotAssignment_0 )? ) ;
    public final void rule__Param_Assign_Out__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6703:1: ( ( ( rule__Param_Assign_Out__NotAssignment_0 )? ) )
            // InternalStructuredTextParser.g:6704:1: ( ( rule__Param_Assign_Out__NotAssignment_0 )? )
            {
            // InternalStructuredTextParser.g:6704:1: ( ( rule__Param_Assign_Out__NotAssignment_0 )? )
            // InternalStructuredTextParser.g:6705:2: ( rule__Param_Assign_Out__NotAssignment_0 )?
            {
             before(grammarAccess.getParam_Assign_OutAccess().getNotAssignment_0()); 
            // InternalStructuredTextParser.g:6706:2: ( rule__Param_Assign_Out__NotAssignment_0 )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==NOT) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalStructuredTextParser.g:6706:3: rule__Param_Assign_Out__NotAssignment_0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__Group__0__Impl"


    // $ANTLR start "rule__Param_Assign_Out__Group__1"
    // InternalStructuredTextParser.g:6714:1: rule__Param_Assign_Out__Group__1 : rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2 ;
    public final void rule__Param_Assign_Out__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6718:1: ( rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2 )
            // InternalStructuredTextParser.g:6719:2: rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2
            {
            pushFollow(FOLLOW_61);
            rule__Param_Assign_Out__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Param_Assign_Out__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__Group__1"


    // $ANTLR start "rule__Param_Assign_Out__Group__1__Impl"
    // InternalStructuredTextParser.g:6726:1: rule__Param_Assign_Out__Group__1__Impl : ( ( rule__Param_Assign_Out__VarAssignment_1 ) ) ;
    public final void rule__Param_Assign_Out__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6730:1: ( ( ( rule__Param_Assign_Out__VarAssignment_1 ) ) )
            // InternalStructuredTextParser.g:6731:1: ( ( rule__Param_Assign_Out__VarAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:6731:1: ( ( rule__Param_Assign_Out__VarAssignment_1 ) )
            // InternalStructuredTextParser.g:6732:2: ( rule__Param_Assign_Out__VarAssignment_1 )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getVarAssignment_1()); 
            // InternalStructuredTextParser.g:6733:2: ( rule__Param_Assign_Out__VarAssignment_1 )
            // InternalStructuredTextParser.g:6733:3: rule__Param_Assign_Out__VarAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Param_Assign_Out__VarAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getParam_Assign_OutAccess().getVarAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__Group__1__Impl"


    // $ANTLR start "rule__Param_Assign_Out__Group__2"
    // InternalStructuredTextParser.g:6741:1: rule__Param_Assign_Out__Group__2 : rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3 ;
    public final void rule__Param_Assign_Out__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6745:1: ( rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3 )
            // InternalStructuredTextParser.g:6746:2: rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3
            {
            pushFollow(FOLLOW_11);
            rule__Param_Assign_Out__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Param_Assign_Out__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__Group__2"


    // $ANTLR start "rule__Param_Assign_Out__Group__2__Impl"
    // InternalStructuredTextParser.g:6753:1: rule__Param_Assign_Out__Group__2__Impl : ( EqualsSignGreaterThanSign ) ;
    public final void rule__Param_Assign_Out__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6757:1: ( ( EqualsSignGreaterThanSign ) )
            // InternalStructuredTextParser.g:6758:1: ( EqualsSignGreaterThanSign )
            {
            // InternalStructuredTextParser.g:6758:1: ( EqualsSignGreaterThanSign )
            // InternalStructuredTextParser.g:6759:2: EqualsSignGreaterThanSign
            {
             before(grammarAccess.getParam_Assign_OutAccess().getEqualsSignGreaterThanSignKeyword_2()); 
            match(input,EqualsSignGreaterThanSign,FOLLOW_2); 
             after(grammarAccess.getParam_Assign_OutAccess().getEqualsSignGreaterThanSignKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__Group__2__Impl"


    // $ANTLR start "rule__Param_Assign_Out__Group__3"
    // InternalStructuredTextParser.g:6768:1: rule__Param_Assign_Out__Group__3 : rule__Param_Assign_Out__Group__3__Impl ;
    public final void rule__Param_Assign_Out__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6772:1: ( rule__Param_Assign_Out__Group__3__Impl )
            // InternalStructuredTextParser.g:6773:2: rule__Param_Assign_Out__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Param_Assign_Out__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__Group__3"


    // $ANTLR start "rule__Param_Assign_Out__Group__3__Impl"
    // InternalStructuredTextParser.g:6779:1: rule__Param_Assign_Out__Group__3__Impl : ( ( rule__Param_Assign_Out__ExprAssignment_3 ) ) ;
    public final void rule__Param_Assign_Out__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6783:1: ( ( ( rule__Param_Assign_Out__ExprAssignment_3 ) ) )
            // InternalStructuredTextParser.g:6784:1: ( ( rule__Param_Assign_Out__ExprAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:6784:1: ( ( rule__Param_Assign_Out__ExprAssignment_3 ) )
            // InternalStructuredTextParser.g:6785:2: ( rule__Param_Assign_Out__ExprAssignment_3 )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getExprAssignment_3()); 
            // InternalStructuredTextParser.g:6786:2: ( rule__Param_Assign_Out__ExprAssignment_3 )
            // InternalStructuredTextParser.g:6786:3: rule__Param_Assign_Out__ExprAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Param_Assign_Out__ExprAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getParam_Assign_OutAccess().getExprAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__Group__3__Impl"


    // $ANTLR start "rule__Variable__Group__0"
    // InternalStructuredTextParser.g:6795:1: rule__Variable__Group__0 : rule__Variable__Group__0__Impl rule__Variable__Group__1 ;
    public final void rule__Variable__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6799:1: ( rule__Variable__Group__0__Impl rule__Variable__Group__1 )
            // InternalStructuredTextParser.g:6800:2: rule__Variable__Group__0__Impl rule__Variable__Group__1
            {
            pushFollow(FOLLOW_62);
            rule__Variable__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable__Group__0"


    // $ANTLR start "rule__Variable__Group__0__Impl"
    // InternalStructuredTextParser.g:6807:1: rule__Variable__Group__0__Impl : ( ruleVariable_Subscript ) ;
    public final void rule__Variable__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6811:1: ( ( ruleVariable_Subscript ) )
            // InternalStructuredTextParser.g:6812:1: ( ruleVariable_Subscript )
            {
            // InternalStructuredTextParser.g:6812:1: ( ruleVariable_Subscript )
            // InternalStructuredTextParser.g:6813:2: ruleVariable_Subscript
            {
             before(grammarAccess.getVariableAccess().getVariable_SubscriptParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleVariable_Subscript();

            state._fsp--;

             after(grammarAccess.getVariableAccess().getVariable_SubscriptParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable__Group__0__Impl"


    // $ANTLR start "rule__Variable__Group__1"
    // InternalStructuredTextParser.g:6822:1: rule__Variable__Group__1 : rule__Variable__Group__1__Impl ;
    public final void rule__Variable__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6826:1: ( rule__Variable__Group__1__Impl )
            // InternalStructuredTextParser.g:6827:2: rule__Variable__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Variable__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable__Group__1"


    // $ANTLR start "rule__Variable__Group__1__Impl"
    // InternalStructuredTextParser.g:6833:1: rule__Variable__Group__1__Impl : ( ( rule__Variable__PartAssignment_1 )? ) ;
    public final void rule__Variable__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6837:1: ( ( ( rule__Variable__PartAssignment_1 )? ) )
            // InternalStructuredTextParser.g:6838:1: ( ( rule__Variable__PartAssignment_1 )? )
            {
            // InternalStructuredTextParser.g:6838:1: ( ( rule__Variable__PartAssignment_1 )? )
            // InternalStructuredTextParser.g:6839:2: ( rule__Variable__PartAssignment_1 )?
            {
             before(grammarAccess.getVariableAccess().getPartAssignment_1()); 
            // InternalStructuredTextParser.g:6840:2: ( rule__Variable__PartAssignment_1 )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( ((LA56_0>=B && LA56_0<=X)||LA56_0==FullStop) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // InternalStructuredTextParser.g:6840:3: rule__Variable__PartAssignment_1
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable__Group__1__Impl"


    // $ANTLR start "rule__Variable_Subscript__Group__0"
    // InternalStructuredTextParser.g:6849:1: rule__Variable_Subscript__Group__0 : rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1 ;
    public final void rule__Variable_Subscript__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6853:1: ( rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1 )
            // InternalStructuredTextParser.g:6854:2: rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1
            {
            pushFollow(FOLLOW_12);
            rule__Variable_Subscript__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group__0"


    // $ANTLR start "rule__Variable_Subscript__Group__0__Impl"
    // InternalStructuredTextParser.g:6861:1: rule__Variable_Subscript__Group__0__Impl : ( ( rule__Variable_Subscript__Alternatives_0 ) ) ;
    public final void rule__Variable_Subscript__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6865:1: ( ( ( rule__Variable_Subscript__Alternatives_0 ) ) )
            // InternalStructuredTextParser.g:6866:1: ( ( rule__Variable_Subscript__Alternatives_0 ) )
            {
            // InternalStructuredTextParser.g:6866:1: ( ( rule__Variable_Subscript__Alternatives_0 ) )
            // InternalStructuredTextParser.g:6867:2: ( rule__Variable_Subscript__Alternatives_0 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getAlternatives_0()); 
            // InternalStructuredTextParser.g:6868:2: ( rule__Variable_Subscript__Alternatives_0 )
            // InternalStructuredTextParser.g:6868:3: rule__Variable_Subscript__Alternatives_0
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Alternatives_0();

            state._fsp--;


            }

             after(grammarAccess.getVariable_SubscriptAccess().getAlternatives_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group__0__Impl"


    // $ANTLR start "rule__Variable_Subscript__Group__1"
    // InternalStructuredTextParser.g:6876:1: rule__Variable_Subscript__Group__1 : rule__Variable_Subscript__Group__1__Impl ;
    public final void rule__Variable_Subscript__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6880:1: ( rule__Variable_Subscript__Group__1__Impl )
            // InternalStructuredTextParser.g:6881:2: rule__Variable_Subscript__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group__1"


    // $ANTLR start "rule__Variable_Subscript__Group__1__Impl"
    // InternalStructuredTextParser.g:6887:1: rule__Variable_Subscript__Group__1__Impl : ( ( rule__Variable_Subscript__Group_1__0 )? ) ;
    public final void rule__Variable_Subscript__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6891:1: ( ( ( rule__Variable_Subscript__Group_1__0 )? ) )
            // InternalStructuredTextParser.g:6892:1: ( ( rule__Variable_Subscript__Group_1__0 )? )
            {
            // InternalStructuredTextParser.g:6892:1: ( ( rule__Variable_Subscript__Group_1__0 )? )
            // InternalStructuredTextParser.g:6893:2: ( rule__Variable_Subscript__Group_1__0 )?
            {
             before(grammarAccess.getVariable_SubscriptAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:6894:2: ( rule__Variable_Subscript__Group_1__0 )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==LeftSquareBracket) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalStructuredTextParser.g:6894:3: rule__Variable_Subscript__Group_1__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group__1__Impl"


    // $ANTLR start "rule__Variable_Subscript__Group_1__0"
    // InternalStructuredTextParser.g:6903:1: rule__Variable_Subscript__Group_1__0 : rule__Variable_Subscript__Group_1__0__Impl rule__Variable_Subscript__Group_1__1 ;
    public final void rule__Variable_Subscript__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6907:1: ( rule__Variable_Subscript__Group_1__0__Impl rule__Variable_Subscript__Group_1__1 )
            // InternalStructuredTextParser.g:6908:2: rule__Variable_Subscript__Group_1__0__Impl rule__Variable_Subscript__Group_1__1
            {
            pushFollow(FOLLOW_12);
            rule__Variable_Subscript__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1__0"


    // $ANTLR start "rule__Variable_Subscript__Group_1__0__Impl"
    // InternalStructuredTextParser.g:6915:1: rule__Variable_Subscript__Group_1__0__Impl : ( () ) ;
    public final void rule__Variable_Subscript__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6919:1: ( ( () ) )
            // InternalStructuredTextParser.g:6920:1: ( () )
            {
            // InternalStructuredTextParser.g:6920:1: ( () )
            // InternalStructuredTextParser.g:6921:2: ()
            {
             before(grammarAccess.getVariable_SubscriptAccess().getArrayVariableArrayAction_1_0()); 
            // InternalStructuredTextParser.g:6922:2: ()
            // InternalStructuredTextParser.g:6922:3: 
            {
            }

             after(grammarAccess.getVariable_SubscriptAccess().getArrayVariableArrayAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1__0__Impl"


    // $ANTLR start "rule__Variable_Subscript__Group_1__1"
    // InternalStructuredTextParser.g:6930:1: rule__Variable_Subscript__Group_1__1 : rule__Variable_Subscript__Group_1__1__Impl rule__Variable_Subscript__Group_1__2 ;
    public final void rule__Variable_Subscript__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6934:1: ( rule__Variable_Subscript__Group_1__1__Impl rule__Variable_Subscript__Group_1__2 )
            // InternalStructuredTextParser.g:6935:2: rule__Variable_Subscript__Group_1__1__Impl rule__Variable_Subscript__Group_1__2
            {
            pushFollow(FOLLOW_20);
            rule__Variable_Subscript__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1__1"


    // $ANTLR start "rule__Variable_Subscript__Group_1__1__Impl"
    // InternalStructuredTextParser.g:6942:1: rule__Variable_Subscript__Group_1__1__Impl : ( LeftSquareBracket ) ;
    public final void rule__Variable_Subscript__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6946:1: ( ( LeftSquareBracket ) )
            // InternalStructuredTextParser.g:6947:1: ( LeftSquareBracket )
            {
            // InternalStructuredTextParser.g:6947:1: ( LeftSquareBracket )
            // InternalStructuredTextParser.g:6948:2: LeftSquareBracket
            {
             before(grammarAccess.getVariable_SubscriptAccess().getLeftSquareBracketKeyword_1_1()); 
            match(input,LeftSquareBracket,FOLLOW_2); 
             after(grammarAccess.getVariable_SubscriptAccess().getLeftSquareBracketKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1__1__Impl"


    // $ANTLR start "rule__Variable_Subscript__Group_1__2"
    // InternalStructuredTextParser.g:6957:1: rule__Variable_Subscript__Group_1__2 : rule__Variable_Subscript__Group_1__2__Impl rule__Variable_Subscript__Group_1__3 ;
    public final void rule__Variable_Subscript__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6961:1: ( rule__Variable_Subscript__Group_1__2__Impl rule__Variable_Subscript__Group_1__3 )
            // InternalStructuredTextParser.g:6962:2: rule__Variable_Subscript__Group_1__2__Impl rule__Variable_Subscript__Group_1__3
            {
            pushFollow(FOLLOW_63);
            rule__Variable_Subscript__Group_1__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Group_1__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1__2"


    // $ANTLR start "rule__Variable_Subscript__Group_1__2__Impl"
    // InternalStructuredTextParser.g:6969:1: rule__Variable_Subscript__Group_1__2__Impl : ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) ) ;
    public final void rule__Variable_Subscript__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6973:1: ( ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:6974:1: ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:6974:1: ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) )
            // InternalStructuredTextParser.g:6975:2: ( rule__Variable_Subscript__IndexAssignment_1_2 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_2()); 
            // InternalStructuredTextParser.g:6976:2: ( rule__Variable_Subscript__IndexAssignment_1_2 )
            // InternalStructuredTextParser.g:6976:3: rule__Variable_Subscript__IndexAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__IndexAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1__2__Impl"


    // $ANTLR start "rule__Variable_Subscript__Group_1__3"
    // InternalStructuredTextParser.g:6984:1: rule__Variable_Subscript__Group_1__3 : rule__Variable_Subscript__Group_1__3__Impl rule__Variable_Subscript__Group_1__4 ;
    public final void rule__Variable_Subscript__Group_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6988:1: ( rule__Variable_Subscript__Group_1__3__Impl rule__Variable_Subscript__Group_1__4 )
            // InternalStructuredTextParser.g:6989:2: rule__Variable_Subscript__Group_1__3__Impl rule__Variable_Subscript__Group_1__4
            {
            pushFollow(FOLLOW_63);
            rule__Variable_Subscript__Group_1__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Group_1__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1__3"


    // $ANTLR start "rule__Variable_Subscript__Group_1__3__Impl"
    // InternalStructuredTextParser.g:6996:1: rule__Variable_Subscript__Group_1__3__Impl : ( ( rule__Variable_Subscript__Group_1_3__0 )* ) ;
    public final void rule__Variable_Subscript__Group_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7000:1: ( ( ( rule__Variable_Subscript__Group_1_3__0 )* ) )
            // InternalStructuredTextParser.g:7001:1: ( ( rule__Variable_Subscript__Group_1_3__0 )* )
            {
            // InternalStructuredTextParser.g:7001:1: ( ( rule__Variable_Subscript__Group_1_3__0 )* )
            // InternalStructuredTextParser.g:7002:2: ( rule__Variable_Subscript__Group_1_3__0 )*
            {
             before(grammarAccess.getVariable_SubscriptAccess().getGroup_1_3()); 
            // InternalStructuredTextParser.g:7003:2: ( rule__Variable_Subscript__Group_1_3__0 )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==Comma) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalStructuredTextParser.g:7003:3: rule__Variable_Subscript__Group_1_3__0
            	    {
            	    pushFollow(FOLLOW_31);
            	    rule__Variable_Subscript__Group_1_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);

             after(grammarAccess.getVariable_SubscriptAccess().getGroup_1_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1__3__Impl"


    // $ANTLR start "rule__Variable_Subscript__Group_1__4"
    // InternalStructuredTextParser.g:7011:1: rule__Variable_Subscript__Group_1__4 : rule__Variable_Subscript__Group_1__4__Impl ;
    public final void rule__Variable_Subscript__Group_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7015:1: ( rule__Variable_Subscript__Group_1__4__Impl )
            // InternalStructuredTextParser.g:7016:2: rule__Variable_Subscript__Group_1__4__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Group_1__4__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1__4"


    // $ANTLR start "rule__Variable_Subscript__Group_1__4__Impl"
    // InternalStructuredTextParser.g:7022:1: rule__Variable_Subscript__Group_1__4__Impl : ( RightSquareBracket ) ;
    public final void rule__Variable_Subscript__Group_1__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7026:1: ( ( RightSquareBracket ) )
            // InternalStructuredTextParser.g:7027:1: ( RightSquareBracket )
            {
            // InternalStructuredTextParser.g:7027:1: ( RightSquareBracket )
            // InternalStructuredTextParser.g:7028:2: RightSquareBracket
            {
             before(grammarAccess.getVariable_SubscriptAccess().getRightSquareBracketKeyword_1_4()); 
            match(input,RightSquareBracket,FOLLOW_2); 
             after(grammarAccess.getVariable_SubscriptAccess().getRightSquareBracketKeyword_1_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1__4__Impl"


    // $ANTLR start "rule__Variable_Subscript__Group_1_3__0"
    // InternalStructuredTextParser.g:7038:1: rule__Variable_Subscript__Group_1_3__0 : rule__Variable_Subscript__Group_1_3__0__Impl rule__Variable_Subscript__Group_1_3__1 ;
    public final void rule__Variable_Subscript__Group_1_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7042:1: ( rule__Variable_Subscript__Group_1_3__0__Impl rule__Variable_Subscript__Group_1_3__1 )
            // InternalStructuredTextParser.g:7043:2: rule__Variable_Subscript__Group_1_3__0__Impl rule__Variable_Subscript__Group_1_3__1
            {
            pushFollow(FOLLOW_20);
            rule__Variable_Subscript__Group_1_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Group_1_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1_3__0"


    // $ANTLR start "rule__Variable_Subscript__Group_1_3__0__Impl"
    // InternalStructuredTextParser.g:7050:1: rule__Variable_Subscript__Group_1_3__0__Impl : ( Comma ) ;
    public final void rule__Variable_Subscript__Group_1_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7054:1: ( ( Comma ) )
            // InternalStructuredTextParser.g:7055:1: ( Comma )
            {
            // InternalStructuredTextParser.g:7055:1: ( Comma )
            // InternalStructuredTextParser.g:7056:2: Comma
            {
             before(grammarAccess.getVariable_SubscriptAccess().getCommaKeyword_1_3_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getVariable_SubscriptAccess().getCommaKeyword_1_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1_3__0__Impl"


    // $ANTLR start "rule__Variable_Subscript__Group_1_3__1"
    // InternalStructuredTextParser.g:7065:1: rule__Variable_Subscript__Group_1_3__1 : rule__Variable_Subscript__Group_1_3__1__Impl ;
    public final void rule__Variable_Subscript__Group_1_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7069:1: ( rule__Variable_Subscript__Group_1_3__1__Impl )
            // InternalStructuredTextParser.g:7070:2: rule__Variable_Subscript__Group_1_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__Group_1_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1_3__1"


    // $ANTLR start "rule__Variable_Subscript__Group_1_3__1__Impl"
    // InternalStructuredTextParser.g:7076:1: rule__Variable_Subscript__Group_1_3__1__Impl : ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) ) ;
    public final void rule__Variable_Subscript__Group_1_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7080:1: ( ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) ) )
            // InternalStructuredTextParser.g:7081:1: ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) )
            {
            // InternalStructuredTextParser.g:7081:1: ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) )
            // InternalStructuredTextParser.g:7082:2: ( rule__Variable_Subscript__IndexAssignment_1_3_1 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_3_1()); 
            // InternalStructuredTextParser.g:7083:2: ( rule__Variable_Subscript__IndexAssignment_1_3_1 )
            // InternalStructuredTextParser.g:7083:3: rule__Variable_Subscript__IndexAssignment_1_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Subscript__IndexAssignment_1_3_1();

            state._fsp--;


            }

             after(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__Group_1_3__1__Impl"


    // $ANTLR start "rule__Variable_Adapter__Group__0"
    // InternalStructuredTextParser.g:7092:1: rule__Variable_Adapter__Group__0 : rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1 ;
    public final void rule__Variable_Adapter__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7096:1: ( rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1 )
            // InternalStructuredTextParser.g:7097:2: rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1
            {
            pushFollow(FOLLOW_64);
            rule__Variable_Adapter__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group__0"


    // $ANTLR start "rule__Variable_Adapter__Group__0__Impl"
    // InternalStructuredTextParser.g:7104:1: rule__Variable_Adapter__Group__0__Impl : ( ruleAdapterRoot ) ;
    public final void rule__Variable_Adapter__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7108:1: ( ( ruleAdapterRoot ) )
            // InternalStructuredTextParser.g:7109:1: ( ruleAdapterRoot )
            {
            // InternalStructuredTextParser.g:7109:1: ( ruleAdapterRoot )
            // InternalStructuredTextParser.g:7110:2: ruleAdapterRoot
            {
             before(grammarAccess.getVariable_AdapterAccess().getAdapterRootParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleAdapterRoot();

            state._fsp--;

             after(grammarAccess.getVariable_AdapterAccess().getAdapterRootParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group__0__Impl"


    // $ANTLR start "rule__Variable_Adapter__Group__1"
    // InternalStructuredTextParser.g:7119:1: rule__Variable_Adapter__Group__1 : rule__Variable_Adapter__Group__1__Impl ;
    public final void rule__Variable_Adapter__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7123:1: ( rule__Variable_Adapter__Group__1__Impl )
            // InternalStructuredTextParser.g:7124:2: rule__Variable_Adapter__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group__1"


    // $ANTLR start "rule__Variable_Adapter__Group__1__Impl"
    // InternalStructuredTextParser.g:7130:1: rule__Variable_Adapter__Group__1__Impl : ( ( ( rule__Variable_Adapter__Group_1__0 ) ) ( ( rule__Variable_Adapter__Group_1__0 )* ) ) ;
    public final void rule__Variable_Adapter__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7134:1: ( ( ( ( rule__Variable_Adapter__Group_1__0 ) ) ( ( rule__Variable_Adapter__Group_1__0 )* ) ) )
            // InternalStructuredTextParser.g:7135:1: ( ( ( rule__Variable_Adapter__Group_1__0 ) ) ( ( rule__Variable_Adapter__Group_1__0 )* ) )
            {
            // InternalStructuredTextParser.g:7135:1: ( ( ( rule__Variable_Adapter__Group_1__0 ) ) ( ( rule__Variable_Adapter__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:7136:2: ( ( rule__Variable_Adapter__Group_1__0 ) ) ( ( rule__Variable_Adapter__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:7136:2: ( ( rule__Variable_Adapter__Group_1__0 ) )
            // InternalStructuredTextParser.g:7137:3: ( rule__Variable_Adapter__Group_1__0 )
            {
             before(grammarAccess.getVariable_AdapterAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:7138:3: ( rule__Variable_Adapter__Group_1__0 )
            // InternalStructuredTextParser.g:7138:4: rule__Variable_Adapter__Group_1__0
            {
            pushFollow(FOLLOW_65);
            rule__Variable_Adapter__Group_1__0();

            state._fsp--;


            }

             after(grammarAccess.getVariable_AdapterAccess().getGroup_1()); 

            }

            // InternalStructuredTextParser.g:7141:2: ( ( rule__Variable_Adapter__Group_1__0 )* )
            // InternalStructuredTextParser.g:7142:3: ( rule__Variable_Adapter__Group_1__0 )*
            {
             before(grammarAccess.getVariable_AdapterAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:7143:3: ( rule__Variable_Adapter__Group_1__0 )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==FullStop) ) {
                    int LA59_2 = input.LA(2);

                    if ( (LA59_2==DT||LA59_2==LT||LA59_2==T||LA59_2==RULE_ID) ) {
                        alt59=1;
                    }


                }


                switch (alt59) {
            	case 1 :
            	    // InternalStructuredTextParser.g:7143:4: rule__Variable_Adapter__Group_1__0
            	    {
            	    pushFollow(FOLLOW_65);
            	    rule__Variable_Adapter__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);

             after(grammarAccess.getVariable_AdapterAccess().getGroup_1()); 

            }


            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group__1__Impl"


    // $ANTLR start "rule__Variable_Adapter__Group_1__0"
    // InternalStructuredTextParser.g:7153:1: rule__Variable_Adapter__Group_1__0 : rule__Variable_Adapter__Group_1__0__Impl rule__Variable_Adapter__Group_1__1 ;
    public final void rule__Variable_Adapter__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7157:1: ( rule__Variable_Adapter__Group_1__0__Impl rule__Variable_Adapter__Group_1__1 )
            // InternalStructuredTextParser.g:7158:2: rule__Variable_Adapter__Group_1__0__Impl rule__Variable_Adapter__Group_1__1
            {
            pushFollow(FOLLOW_64);
            rule__Variable_Adapter__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group_1__0"


    // $ANTLR start "rule__Variable_Adapter__Group_1__0__Impl"
    // InternalStructuredTextParser.g:7165:1: rule__Variable_Adapter__Group_1__0__Impl : ( () ) ;
    public final void rule__Variable_Adapter__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7169:1: ( ( () ) )
            // InternalStructuredTextParser.g:7170:1: ( () )
            {
            // InternalStructuredTextParser.g:7170:1: ( () )
            // InternalStructuredTextParser.g:7171:2: ()
            {
             before(grammarAccess.getVariable_AdapterAccess().getAdapterVariableCurrAction_1_0()); 
            // InternalStructuredTextParser.g:7172:2: ()
            // InternalStructuredTextParser.g:7172:3: 
            {
            }

             after(grammarAccess.getVariable_AdapterAccess().getAdapterVariableCurrAction_1_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group_1__0__Impl"


    // $ANTLR start "rule__Variable_Adapter__Group_1__1"
    // InternalStructuredTextParser.g:7180:1: rule__Variable_Adapter__Group_1__1 : rule__Variable_Adapter__Group_1__1__Impl rule__Variable_Adapter__Group_1__2 ;
    public final void rule__Variable_Adapter__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7184:1: ( rule__Variable_Adapter__Group_1__1__Impl rule__Variable_Adapter__Group_1__2 )
            // InternalStructuredTextParser.g:7185:2: rule__Variable_Adapter__Group_1__1__Impl rule__Variable_Adapter__Group_1__2
            {
            pushFollow(FOLLOW_11);
            rule__Variable_Adapter__Group_1__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__Group_1__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group_1__1"


    // $ANTLR start "rule__Variable_Adapter__Group_1__1__Impl"
    // InternalStructuredTextParser.g:7192:1: rule__Variable_Adapter__Group_1__1__Impl : ( FullStop ) ;
    public final void rule__Variable_Adapter__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7196:1: ( ( FullStop ) )
            // InternalStructuredTextParser.g:7197:1: ( FullStop )
            {
            // InternalStructuredTextParser.g:7197:1: ( FullStop )
            // InternalStructuredTextParser.g:7198:2: FullStop
            {
             before(grammarAccess.getVariable_AdapterAccess().getFullStopKeyword_1_1()); 
            match(input,FullStop,FOLLOW_2); 
             after(grammarAccess.getVariable_AdapterAccess().getFullStopKeyword_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group_1__1__Impl"


    // $ANTLR start "rule__Variable_Adapter__Group_1__2"
    // InternalStructuredTextParser.g:7207:1: rule__Variable_Adapter__Group_1__2 : rule__Variable_Adapter__Group_1__2__Impl ;
    public final void rule__Variable_Adapter__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7211:1: ( rule__Variable_Adapter__Group_1__2__Impl )
            // InternalStructuredTextParser.g:7212:2: rule__Variable_Adapter__Group_1__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__Group_1__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group_1__2"


    // $ANTLR start "rule__Variable_Adapter__Group_1__2__Impl"
    // InternalStructuredTextParser.g:7218:1: rule__Variable_Adapter__Group_1__2__Impl : ( ( rule__Variable_Adapter__VarAssignment_1_2 ) ) ;
    public final void rule__Variable_Adapter__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7222:1: ( ( ( rule__Variable_Adapter__VarAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:7223:1: ( ( rule__Variable_Adapter__VarAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:7223:1: ( ( rule__Variable_Adapter__VarAssignment_1_2 ) )
            // InternalStructuredTextParser.g:7224:2: ( rule__Variable_Adapter__VarAssignment_1_2 )
            {
             before(grammarAccess.getVariable_AdapterAccess().getVarAssignment_1_2()); 
            // InternalStructuredTextParser.g:7225:2: ( rule__Variable_Adapter__VarAssignment_1_2 )
            // InternalStructuredTextParser.g:7225:3: rule__Variable_Adapter__VarAssignment_1_2
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__VarAssignment_1_2();

            state._fsp--;


            }

             after(grammarAccess.getVariable_AdapterAccess().getVarAssignment_1_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group_1__2__Impl"


    // $ANTLR start "rule__AdapterRoot__Group__0"
    // InternalStructuredTextParser.g:7234:1: rule__AdapterRoot__Group__0 : rule__AdapterRoot__Group__0__Impl rule__AdapterRoot__Group__1 ;
    public final void rule__AdapterRoot__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7238:1: ( rule__AdapterRoot__Group__0__Impl rule__AdapterRoot__Group__1 )
            // InternalStructuredTextParser.g:7239:2: rule__AdapterRoot__Group__0__Impl rule__AdapterRoot__Group__1
            {
            pushFollow(FOLLOW_11);
            rule__AdapterRoot__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__AdapterRoot__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdapterRoot__Group__0"


    // $ANTLR start "rule__AdapterRoot__Group__0__Impl"
    // InternalStructuredTextParser.g:7246:1: rule__AdapterRoot__Group__0__Impl : ( () ) ;
    public final void rule__AdapterRoot__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7250:1: ( ( () ) )
            // InternalStructuredTextParser.g:7251:1: ( () )
            {
            // InternalStructuredTextParser.g:7251:1: ( () )
            // InternalStructuredTextParser.g:7252:2: ()
            {
             before(grammarAccess.getAdapterRootAccess().getAdapterRootAction_0()); 
            // InternalStructuredTextParser.g:7253:2: ()
            // InternalStructuredTextParser.g:7253:3: 
            {
            }

             after(grammarAccess.getAdapterRootAccess().getAdapterRootAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdapterRoot__Group__0__Impl"


    // $ANTLR start "rule__AdapterRoot__Group__1"
    // InternalStructuredTextParser.g:7261:1: rule__AdapterRoot__Group__1 : rule__AdapterRoot__Group__1__Impl ;
    public final void rule__AdapterRoot__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7265:1: ( rule__AdapterRoot__Group__1__Impl )
            // InternalStructuredTextParser.g:7266:2: rule__AdapterRoot__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__AdapterRoot__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdapterRoot__Group__1"


    // $ANTLR start "rule__AdapterRoot__Group__1__Impl"
    // InternalStructuredTextParser.g:7272:1: rule__AdapterRoot__Group__1__Impl : ( ( rule__AdapterRoot__AdapterAssignment_1 ) ) ;
    public final void rule__AdapterRoot__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7276:1: ( ( ( rule__AdapterRoot__AdapterAssignment_1 ) ) )
            // InternalStructuredTextParser.g:7277:1: ( ( rule__AdapterRoot__AdapterAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:7277:1: ( ( rule__AdapterRoot__AdapterAssignment_1 ) )
            // InternalStructuredTextParser.g:7278:2: ( rule__AdapterRoot__AdapterAssignment_1 )
            {
             before(grammarAccess.getAdapterRootAccess().getAdapterAssignment_1()); 
            // InternalStructuredTextParser.g:7279:2: ( rule__AdapterRoot__AdapterAssignment_1 )
            // InternalStructuredTextParser.g:7279:3: rule__AdapterRoot__AdapterAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__AdapterRoot__AdapterAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getAdapterRootAccess().getAdapterAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdapterRoot__Group__1__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_0__0"
    // InternalStructuredTextParser.g:7288:1: rule__Multibit_Part_Access__Group_0__0 : rule__Multibit_Part_Access__Group_0__0__Impl rule__Multibit_Part_Access__Group_0__1 ;
    public final void rule__Multibit_Part_Access__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7292:1: ( rule__Multibit_Part_Access__Group_0__0__Impl rule__Multibit_Part_Access__Group_0__1 )
            // InternalStructuredTextParser.g:7293:2: rule__Multibit_Part_Access__Group_0__0__Impl rule__Multibit_Part_Access__Group_0__1
            {
            pushFollow(FOLLOW_13);
            rule__Multibit_Part_Access__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_0__0"


    // $ANTLR start "rule__Multibit_Part_Access__Group_0__0__Impl"
    // InternalStructuredTextParser.g:7300:1: rule__Multibit_Part_Access__Group_0__0__Impl : ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7304:1: ( ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:7305:1: ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:7305:1: ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) )
            // InternalStructuredTextParser.g:7306:2: ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessAssignment_0_0()); 
            // InternalStructuredTextParser.g:7307:2: ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 )
            // InternalStructuredTextParser.g:7307:3: rule__Multibit_Part_Access__DwordaccessAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__DwordaccessAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_0__0__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_0__1"
    // InternalStructuredTextParser.g:7315:1: rule__Multibit_Part_Access__Group_0__1 : rule__Multibit_Part_Access__Group_0__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7319:1: ( rule__Multibit_Part_Access__Group_0__1__Impl )
            // InternalStructuredTextParser.g:7320:2: rule__Multibit_Part_Access__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_0__1"


    // $ANTLR start "rule__Multibit_Part_Access__Group_0__1__Impl"
    // InternalStructuredTextParser.g:7326:1: rule__Multibit_Part_Access__Group_0__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7330:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) ) )
            // InternalStructuredTextParser.g:7331:1: ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) )
            {
            // InternalStructuredTextParser.g:7331:1: ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) )
            // InternalStructuredTextParser.g:7332:2: ( rule__Multibit_Part_Access__IndexAssignment_0_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_0_1()); 
            // InternalStructuredTextParser.g:7333:2: ( rule__Multibit_Part_Access__IndexAssignment_0_1 )
            // InternalStructuredTextParser.g:7333:3: rule__Multibit_Part_Access__IndexAssignment_0_1
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__IndexAssignment_0_1();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_0__1__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_1__0"
    // InternalStructuredTextParser.g:7342:1: rule__Multibit_Part_Access__Group_1__0 : rule__Multibit_Part_Access__Group_1__0__Impl rule__Multibit_Part_Access__Group_1__1 ;
    public final void rule__Multibit_Part_Access__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7346:1: ( rule__Multibit_Part_Access__Group_1__0__Impl rule__Multibit_Part_Access__Group_1__1 )
            // InternalStructuredTextParser.g:7347:2: rule__Multibit_Part_Access__Group_1__0__Impl rule__Multibit_Part_Access__Group_1__1
            {
            pushFollow(FOLLOW_13);
            rule__Multibit_Part_Access__Group_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Group_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_1__0"


    // $ANTLR start "rule__Multibit_Part_Access__Group_1__0__Impl"
    // InternalStructuredTextParser.g:7354:1: rule__Multibit_Part_Access__Group_1__0__Impl : ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7358:1: ( ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) ) )
            // InternalStructuredTextParser.g:7359:1: ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) )
            {
            // InternalStructuredTextParser.g:7359:1: ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) )
            // InternalStructuredTextParser.g:7360:2: ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessAssignment_1_0()); 
            // InternalStructuredTextParser.g:7361:2: ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 )
            // InternalStructuredTextParser.g:7361:3: rule__Multibit_Part_Access__WordaccessAssignment_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__WordaccessAssignment_1_0();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessAssignment_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_1__0__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_1__1"
    // InternalStructuredTextParser.g:7369:1: rule__Multibit_Part_Access__Group_1__1 : rule__Multibit_Part_Access__Group_1__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7373:1: ( rule__Multibit_Part_Access__Group_1__1__Impl )
            // InternalStructuredTextParser.g:7374:2: rule__Multibit_Part_Access__Group_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Group_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_1__1"


    // $ANTLR start "rule__Multibit_Part_Access__Group_1__1__Impl"
    // InternalStructuredTextParser.g:7380:1: rule__Multibit_Part_Access__Group_1__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7384:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:7385:1: ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:7385:1: ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) )
            // InternalStructuredTextParser.g:7386:2: ( rule__Multibit_Part_Access__IndexAssignment_1_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_1_1()); 
            // InternalStructuredTextParser.g:7387:2: ( rule__Multibit_Part_Access__IndexAssignment_1_1 )
            // InternalStructuredTextParser.g:7387:3: rule__Multibit_Part_Access__IndexAssignment_1_1
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__IndexAssignment_1_1();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_1__1__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_2__0"
    // InternalStructuredTextParser.g:7396:1: rule__Multibit_Part_Access__Group_2__0 : rule__Multibit_Part_Access__Group_2__0__Impl rule__Multibit_Part_Access__Group_2__1 ;
    public final void rule__Multibit_Part_Access__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7400:1: ( rule__Multibit_Part_Access__Group_2__0__Impl rule__Multibit_Part_Access__Group_2__1 )
            // InternalStructuredTextParser.g:7401:2: rule__Multibit_Part_Access__Group_2__0__Impl rule__Multibit_Part_Access__Group_2__1
            {
            pushFollow(FOLLOW_13);
            rule__Multibit_Part_Access__Group_2__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Group_2__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_2__0"


    // $ANTLR start "rule__Multibit_Part_Access__Group_2__0__Impl"
    // InternalStructuredTextParser.g:7408:1: rule__Multibit_Part_Access__Group_2__0__Impl : ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7412:1: ( ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) ) )
            // InternalStructuredTextParser.g:7413:1: ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) )
            {
            // InternalStructuredTextParser.g:7413:1: ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) )
            // InternalStructuredTextParser.g:7414:2: ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessAssignment_2_0()); 
            // InternalStructuredTextParser.g:7415:2: ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 )
            // InternalStructuredTextParser.g:7415:3: rule__Multibit_Part_Access__ByteaccessAssignment_2_0
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__ByteaccessAssignment_2_0();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessAssignment_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_2__0__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_2__1"
    // InternalStructuredTextParser.g:7423:1: rule__Multibit_Part_Access__Group_2__1 : rule__Multibit_Part_Access__Group_2__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7427:1: ( rule__Multibit_Part_Access__Group_2__1__Impl )
            // InternalStructuredTextParser.g:7428:2: rule__Multibit_Part_Access__Group_2__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Group_2__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_2__1"


    // $ANTLR start "rule__Multibit_Part_Access__Group_2__1__Impl"
    // InternalStructuredTextParser.g:7434:1: rule__Multibit_Part_Access__Group_2__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7438:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) ) )
            // InternalStructuredTextParser.g:7439:1: ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) )
            {
            // InternalStructuredTextParser.g:7439:1: ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) )
            // InternalStructuredTextParser.g:7440:2: ( rule__Multibit_Part_Access__IndexAssignment_2_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_2_1()); 
            // InternalStructuredTextParser.g:7441:2: ( rule__Multibit_Part_Access__IndexAssignment_2_1 )
            // InternalStructuredTextParser.g:7441:3: rule__Multibit_Part_Access__IndexAssignment_2_1
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__IndexAssignment_2_1();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_2_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_2__1__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_3__0"
    // InternalStructuredTextParser.g:7450:1: rule__Multibit_Part_Access__Group_3__0 : rule__Multibit_Part_Access__Group_3__0__Impl rule__Multibit_Part_Access__Group_3__1 ;
    public final void rule__Multibit_Part_Access__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7454:1: ( rule__Multibit_Part_Access__Group_3__0__Impl rule__Multibit_Part_Access__Group_3__1 )
            // InternalStructuredTextParser.g:7455:2: rule__Multibit_Part_Access__Group_3__0__Impl rule__Multibit_Part_Access__Group_3__1
            {
            pushFollow(FOLLOW_13);
            rule__Multibit_Part_Access__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_3__0"


    // $ANTLR start "rule__Multibit_Part_Access__Group_3__0__Impl"
    // InternalStructuredTextParser.g:7462:1: rule__Multibit_Part_Access__Group_3__0__Impl : ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7466:1: ( ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) ) )
            // InternalStructuredTextParser.g:7467:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) )
            {
            // InternalStructuredTextParser.g:7467:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) )
            // InternalStructuredTextParser.g:7468:2: ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_3_0()); 
            // InternalStructuredTextParser.g:7469:2: ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 )
            // InternalStructuredTextParser.g:7469:3: rule__Multibit_Part_Access__BitaccessAssignment_3_0
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__BitaccessAssignment_3_0();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_3__0__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_3__1"
    // InternalStructuredTextParser.g:7477:1: rule__Multibit_Part_Access__Group_3__1 : rule__Multibit_Part_Access__Group_3__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7481:1: ( rule__Multibit_Part_Access__Group_3__1__Impl )
            // InternalStructuredTextParser.g:7482:2: rule__Multibit_Part_Access__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_3__1"


    // $ANTLR start "rule__Multibit_Part_Access__Group_3__1__Impl"
    // InternalStructuredTextParser.g:7488:1: rule__Multibit_Part_Access__Group_3__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7492:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) ) )
            // InternalStructuredTextParser.g:7493:1: ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) )
            {
            // InternalStructuredTextParser.g:7493:1: ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) )
            // InternalStructuredTextParser.g:7494:2: ( rule__Multibit_Part_Access__IndexAssignment_3_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_3_1()); 
            // InternalStructuredTextParser.g:7495:2: ( rule__Multibit_Part_Access__IndexAssignment_3_1 )
            // InternalStructuredTextParser.g:7495:3: rule__Multibit_Part_Access__IndexAssignment_3_1
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__IndexAssignment_3_1();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_3__1__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_4__0"
    // InternalStructuredTextParser.g:7504:1: rule__Multibit_Part_Access__Group_4__0 : rule__Multibit_Part_Access__Group_4__0__Impl rule__Multibit_Part_Access__Group_4__1 ;
    public final void rule__Multibit_Part_Access__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7508:1: ( rule__Multibit_Part_Access__Group_4__0__Impl rule__Multibit_Part_Access__Group_4__1 )
            // InternalStructuredTextParser.g:7509:2: rule__Multibit_Part_Access__Group_4__0__Impl rule__Multibit_Part_Access__Group_4__1
            {
            pushFollow(FOLLOW_13);
            rule__Multibit_Part_Access__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_4__0"


    // $ANTLR start "rule__Multibit_Part_Access__Group_4__0__Impl"
    // InternalStructuredTextParser.g:7516:1: rule__Multibit_Part_Access__Group_4__0__Impl : ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7520:1: ( ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) ) )
            // InternalStructuredTextParser.g:7521:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) )
            {
            // InternalStructuredTextParser.g:7521:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) )
            // InternalStructuredTextParser.g:7522:2: ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_4_0()); 
            // InternalStructuredTextParser.g:7523:2: ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 )
            // InternalStructuredTextParser.g:7523:3: rule__Multibit_Part_Access__BitaccessAssignment_4_0
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__BitaccessAssignment_4_0();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_4__0__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_4__1"
    // InternalStructuredTextParser.g:7531:1: rule__Multibit_Part_Access__Group_4__1 : rule__Multibit_Part_Access__Group_4__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7535:1: ( rule__Multibit_Part_Access__Group_4__1__Impl )
            // InternalStructuredTextParser.g:7536:2: rule__Multibit_Part_Access__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_4__1"


    // $ANTLR start "rule__Multibit_Part_Access__Group_4__1__Impl"
    // InternalStructuredTextParser.g:7542:1: rule__Multibit_Part_Access__Group_4__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7546:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) ) )
            // InternalStructuredTextParser.g:7547:1: ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) )
            {
            // InternalStructuredTextParser.g:7547:1: ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) )
            // InternalStructuredTextParser.g:7548:2: ( rule__Multibit_Part_Access__IndexAssignment_4_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_4_1()); 
            // InternalStructuredTextParser.g:7549:2: ( rule__Multibit_Part_Access__IndexAssignment_4_1 )
            // InternalStructuredTextParser.g:7549:3: rule__Multibit_Part_Access__IndexAssignment_4_1
            {
            pushFollow(FOLLOW_2);
            rule__Multibit_Part_Access__IndexAssignment_4_1();

            state._fsp--;


            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__Group_4__1__Impl"


    // $ANTLR start "rule__Int_Literal__Group__0"
    // InternalStructuredTextParser.g:7558:1: rule__Int_Literal__Group__0 : rule__Int_Literal__Group__0__Impl rule__Int_Literal__Group__1 ;
    public final void rule__Int_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7562:1: ( rule__Int_Literal__Group__0__Impl rule__Int_Literal__Group__1 )
            // InternalStructuredTextParser.g:7563:2: rule__Int_Literal__Group__0__Impl rule__Int_Literal__Group__1
            {
            pushFollow(FOLLOW_66);
            rule__Int_Literal__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Int_Literal__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__Group__0"


    // $ANTLR start "rule__Int_Literal__Group__0__Impl"
    // InternalStructuredTextParser.g:7570:1: rule__Int_Literal__Group__0__Impl : ( ( rule__Int_Literal__Group_0__0 )? ) ;
    public final void rule__Int_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7574:1: ( ( ( rule__Int_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:7575:1: ( ( rule__Int_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:7575:1: ( ( rule__Int_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:7576:2: ( rule__Int_Literal__Group_0__0 )?
            {
             before(grammarAccess.getInt_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:7577:2: ( rule__Int_Literal__Group_0__0 )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( ((LA60_0>=UDINT && LA60_0<=ULINT)||LA60_0==USINT||LA60_0==DINT||LA60_0==LINT||LA60_0==SINT||LA60_0==UINT||LA60_0==INT) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalStructuredTextParser.g:7577:3: rule__Int_Literal__Group_0__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__Group__0__Impl"


    // $ANTLR start "rule__Int_Literal__Group__1"
    // InternalStructuredTextParser.g:7585:1: rule__Int_Literal__Group__1 : rule__Int_Literal__Group__1__Impl ;
    public final void rule__Int_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7589:1: ( rule__Int_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:7590:2: rule__Int_Literal__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Int_Literal__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__Group__1"


    // $ANTLR start "rule__Int_Literal__Group__1__Impl"
    // InternalStructuredTextParser.g:7596:1: rule__Int_Literal__Group__1__Impl : ( ( rule__Int_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Int_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7600:1: ( ( ( rule__Int_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:7601:1: ( ( rule__Int_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:7601:1: ( ( rule__Int_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:7602:2: ( rule__Int_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getInt_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:7603:2: ( rule__Int_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:7603:3: rule__Int_Literal__ValueAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Int_Literal__ValueAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getInt_LiteralAccess().getValueAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__Group__1__Impl"


    // $ANTLR start "rule__Int_Literal__Group_0__0"
    // InternalStructuredTextParser.g:7612:1: rule__Int_Literal__Group_0__0 : rule__Int_Literal__Group_0__0__Impl rule__Int_Literal__Group_0__1 ;
    public final void rule__Int_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7616:1: ( rule__Int_Literal__Group_0__0__Impl rule__Int_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:7617:2: rule__Int_Literal__Group_0__0__Impl rule__Int_Literal__Group_0__1
            {
            pushFollow(FOLLOW_67);
            rule__Int_Literal__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Int_Literal__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__Group_0__0"


    // $ANTLR start "rule__Int_Literal__Group_0__0__Impl"
    // InternalStructuredTextParser.g:7624:1: rule__Int_Literal__Group_0__0__Impl : ( ( rule__Int_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Int_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7628:1: ( ( ( rule__Int_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:7629:1: ( ( rule__Int_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:7629:1: ( ( rule__Int_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:7630:2: ( rule__Int_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getInt_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:7631:2: ( rule__Int_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:7631:3: rule__Int_Literal__TypeAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Int_Literal__TypeAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getInt_LiteralAccess().getTypeAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__Group_0__0__Impl"


    // $ANTLR start "rule__Int_Literal__Group_0__1"
    // InternalStructuredTextParser.g:7639:1: rule__Int_Literal__Group_0__1 : rule__Int_Literal__Group_0__1__Impl ;
    public final void rule__Int_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7643:1: ( rule__Int_Literal__Group_0__1__Impl )
            // InternalStructuredTextParser.g:7644:2: rule__Int_Literal__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Int_Literal__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__Group_0__1"


    // $ANTLR start "rule__Int_Literal__Group_0__1__Impl"
    // InternalStructuredTextParser.g:7650:1: rule__Int_Literal__Group_0__1__Impl : ( NumberSign ) ;
    public final void rule__Int_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7654:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:7655:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:7655:1: ( NumberSign )
            // InternalStructuredTextParser.g:7656:2: NumberSign
            {
             before(grammarAccess.getInt_LiteralAccess().getNumberSignKeyword_0_1()); 
            match(input,NumberSign,FOLLOW_2); 
             after(grammarAccess.getInt_LiteralAccess().getNumberSignKeyword_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__Group_0__1__Impl"


    // $ANTLR start "rule__Signed_Int__Group__0"
    // InternalStructuredTextParser.g:7666:1: rule__Signed_Int__Group__0 : rule__Signed_Int__Group__0__Impl rule__Signed_Int__Group__1 ;
    public final void rule__Signed_Int__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7670:1: ( rule__Signed_Int__Group__0__Impl rule__Signed_Int__Group__1 )
            // InternalStructuredTextParser.g:7671:2: rule__Signed_Int__Group__0__Impl rule__Signed_Int__Group__1
            {
            pushFollow(FOLLOW_68);
            rule__Signed_Int__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Signed_Int__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Signed_Int__Group__0"


    // $ANTLR start "rule__Signed_Int__Group__0__Impl"
    // InternalStructuredTextParser.g:7678:1: rule__Signed_Int__Group__0__Impl : ( ( rule__Signed_Int__Alternatives_0 )? ) ;
    public final void rule__Signed_Int__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7682:1: ( ( ( rule__Signed_Int__Alternatives_0 )? ) )
            // InternalStructuredTextParser.g:7683:1: ( ( rule__Signed_Int__Alternatives_0 )? )
            {
            // InternalStructuredTextParser.g:7683:1: ( ( rule__Signed_Int__Alternatives_0 )? )
            // InternalStructuredTextParser.g:7684:2: ( rule__Signed_Int__Alternatives_0 )?
            {
             before(grammarAccess.getSigned_IntAccess().getAlternatives_0()); 
            // InternalStructuredTextParser.g:7685:2: ( rule__Signed_Int__Alternatives_0 )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==PlusSign||LA61_0==HyphenMinus) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalStructuredTextParser.g:7685:3: rule__Signed_Int__Alternatives_0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Signed_Int__Group__0__Impl"


    // $ANTLR start "rule__Signed_Int__Group__1"
    // InternalStructuredTextParser.g:7693:1: rule__Signed_Int__Group__1 : rule__Signed_Int__Group__1__Impl ;
    public final void rule__Signed_Int__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7697:1: ( rule__Signed_Int__Group__1__Impl )
            // InternalStructuredTextParser.g:7698:2: rule__Signed_Int__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Signed_Int__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Signed_Int__Group__1"


    // $ANTLR start "rule__Signed_Int__Group__1__Impl"
    // InternalStructuredTextParser.g:7704:1: rule__Signed_Int__Group__1__Impl : ( RULE_UNSIGNED_INT ) ;
    public final void rule__Signed_Int__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7708:1: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:7709:1: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:7709:1: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:7710:2: RULE_UNSIGNED_INT
            {
             before(grammarAccess.getSigned_IntAccess().getUNSIGNED_INTTerminalRuleCall_1()); 
            match(input,RULE_UNSIGNED_INT,FOLLOW_2); 
             after(grammarAccess.getSigned_IntAccess().getUNSIGNED_INTTerminalRuleCall_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Signed_Int__Group__1__Impl"


    // $ANTLR start "rule__Real_Literal__Group__0"
    // InternalStructuredTextParser.g:7720:1: rule__Real_Literal__Group__0 : rule__Real_Literal__Group__0__Impl rule__Real_Literal__Group__1 ;
    public final void rule__Real_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7724:1: ( rule__Real_Literal__Group__0__Impl rule__Real_Literal__Group__1 )
            // InternalStructuredTextParser.g:7725:2: rule__Real_Literal__Group__0__Impl rule__Real_Literal__Group__1
            {
            pushFollow(FOLLOW_69);
            rule__Real_Literal__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Real_Literal__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Literal__Group__0"


    // $ANTLR start "rule__Real_Literal__Group__0__Impl"
    // InternalStructuredTextParser.g:7732:1: rule__Real_Literal__Group__0__Impl : ( ( rule__Real_Literal__Group_0__0 )? ) ;
    public final void rule__Real_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7736:1: ( ( ( rule__Real_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:7737:1: ( ( rule__Real_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:7737:1: ( ( rule__Real_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:7738:2: ( rule__Real_Literal__Group_0__0 )?
            {
             before(grammarAccess.getReal_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:7739:2: ( rule__Real_Literal__Group_0__0 )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==LREAL||LA62_0==REAL) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalStructuredTextParser.g:7739:3: rule__Real_Literal__Group_0__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Literal__Group__0__Impl"


    // $ANTLR start "rule__Real_Literal__Group__1"
    // InternalStructuredTextParser.g:7747:1: rule__Real_Literal__Group__1 : rule__Real_Literal__Group__1__Impl ;
    public final void rule__Real_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7751:1: ( rule__Real_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:7752:2: rule__Real_Literal__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Real_Literal__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Literal__Group__1"


    // $ANTLR start "rule__Real_Literal__Group__1__Impl"
    // InternalStructuredTextParser.g:7758:1: rule__Real_Literal__Group__1__Impl : ( ( rule__Real_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Real_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7762:1: ( ( ( rule__Real_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:7763:1: ( ( rule__Real_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:7763:1: ( ( rule__Real_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:7764:2: ( rule__Real_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getReal_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:7765:2: ( rule__Real_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:7765:3: rule__Real_Literal__ValueAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Real_Literal__ValueAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getReal_LiteralAccess().getValueAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Literal__Group__1__Impl"


    // $ANTLR start "rule__Real_Literal__Group_0__0"
    // InternalStructuredTextParser.g:7774:1: rule__Real_Literal__Group_0__0 : rule__Real_Literal__Group_0__0__Impl rule__Real_Literal__Group_0__1 ;
    public final void rule__Real_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7778:1: ( rule__Real_Literal__Group_0__0__Impl rule__Real_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:7779:2: rule__Real_Literal__Group_0__0__Impl rule__Real_Literal__Group_0__1
            {
            pushFollow(FOLLOW_67);
            rule__Real_Literal__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Real_Literal__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Literal__Group_0__0"


    // $ANTLR start "rule__Real_Literal__Group_0__0__Impl"
    // InternalStructuredTextParser.g:7786:1: rule__Real_Literal__Group_0__0__Impl : ( ( rule__Real_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Real_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7790:1: ( ( ( rule__Real_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:7791:1: ( ( rule__Real_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:7791:1: ( ( rule__Real_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:7792:2: ( rule__Real_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getReal_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:7793:2: ( rule__Real_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:7793:3: rule__Real_Literal__TypeAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Real_Literal__TypeAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getReal_LiteralAccess().getTypeAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Literal__Group_0__0__Impl"


    // $ANTLR start "rule__Real_Literal__Group_0__1"
    // InternalStructuredTextParser.g:7801:1: rule__Real_Literal__Group_0__1 : rule__Real_Literal__Group_0__1__Impl ;
    public final void rule__Real_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7805:1: ( rule__Real_Literal__Group_0__1__Impl )
            // InternalStructuredTextParser.g:7806:2: rule__Real_Literal__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Real_Literal__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Literal__Group_0__1"


    // $ANTLR start "rule__Real_Literal__Group_0__1__Impl"
    // InternalStructuredTextParser.g:7812:1: rule__Real_Literal__Group_0__1__Impl : ( NumberSign ) ;
    public final void rule__Real_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7816:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:7817:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:7817:1: ( NumberSign )
            // InternalStructuredTextParser.g:7818:2: NumberSign
            {
             before(grammarAccess.getReal_LiteralAccess().getNumberSignKeyword_0_1()); 
            match(input,NumberSign,FOLLOW_2); 
             after(grammarAccess.getReal_LiteralAccess().getNumberSignKeyword_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Literal__Group_0__1__Impl"


    // $ANTLR start "rule__Real_Value__Group__0"
    // InternalStructuredTextParser.g:7828:1: rule__Real_Value__Group__0 : rule__Real_Value__Group__0__Impl rule__Real_Value__Group__1 ;
    public final void rule__Real_Value__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7832:1: ( rule__Real_Value__Group__0__Impl rule__Real_Value__Group__1 )
            // InternalStructuredTextParser.g:7833:2: rule__Real_Value__Group__0__Impl rule__Real_Value__Group__1
            {
            pushFollow(FOLLOW_64);
            rule__Real_Value__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Real_Value__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group__0"


    // $ANTLR start "rule__Real_Value__Group__0__Impl"
    // InternalStructuredTextParser.g:7840:1: rule__Real_Value__Group__0__Impl : ( ruleSigned_Int ) ;
    public final void rule__Real_Value__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7844:1: ( ( ruleSigned_Int ) )
            // InternalStructuredTextParser.g:7845:1: ( ruleSigned_Int )
            {
            // InternalStructuredTextParser.g:7845:1: ( ruleSigned_Int )
            // InternalStructuredTextParser.g:7846:2: ruleSigned_Int
            {
             before(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_0()); 
            pushFollow(FOLLOW_2);
            ruleSigned_Int();

            state._fsp--;

             after(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group__0__Impl"


    // $ANTLR start "rule__Real_Value__Group__1"
    // InternalStructuredTextParser.g:7855:1: rule__Real_Value__Group__1 : rule__Real_Value__Group__1__Impl rule__Real_Value__Group__2 ;
    public final void rule__Real_Value__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7859:1: ( rule__Real_Value__Group__1__Impl rule__Real_Value__Group__2 )
            // InternalStructuredTextParser.g:7860:2: rule__Real_Value__Group__1__Impl rule__Real_Value__Group__2
            {
            pushFollow(FOLLOW_13);
            rule__Real_Value__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Real_Value__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group__1"


    // $ANTLR start "rule__Real_Value__Group__1__Impl"
    // InternalStructuredTextParser.g:7867:1: rule__Real_Value__Group__1__Impl : ( FullStop ) ;
    public final void rule__Real_Value__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7871:1: ( ( FullStop ) )
            // InternalStructuredTextParser.g:7872:1: ( FullStop )
            {
            // InternalStructuredTextParser.g:7872:1: ( FullStop )
            // InternalStructuredTextParser.g:7873:2: FullStop
            {
             before(grammarAccess.getReal_ValueAccess().getFullStopKeyword_1()); 
            match(input,FullStop,FOLLOW_2); 
             after(grammarAccess.getReal_ValueAccess().getFullStopKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group__1__Impl"


    // $ANTLR start "rule__Real_Value__Group__2"
    // InternalStructuredTextParser.g:7882:1: rule__Real_Value__Group__2 : rule__Real_Value__Group__2__Impl rule__Real_Value__Group__3 ;
    public final void rule__Real_Value__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7886:1: ( rule__Real_Value__Group__2__Impl rule__Real_Value__Group__3 )
            // InternalStructuredTextParser.g:7887:2: rule__Real_Value__Group__2__Impl rule__Real_Value__Group__3
            {
            pushFollow(FOLLOW_70);
            rule__Real_Value__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Real_Value__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group__2"


    // $ANTLR start "rule__Real_Value__Group__2__Impl"
    // InternalStructuredTextParser.g:7894:1: rule__Real_Value__Group__2__Impl : ( RULE_UNSIGNED_INT ) ;
    public final void rule__Real_Value__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7898:1: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:7899:1: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:7899:1: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:7900:2: RULE_UNSIGNED_INT
            {
             before(grammarAccess.getReal_ValueAccess().getUNSIGNED_INTTerminalRuleCall_2()); 
            match(input,RULE_UNSIGNED_INT,FOLLOW_2); 
             after(grammarAccess.getReal_ValueAccess().getUNSIGNED_INTTerminalRuleCall_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group__2__Impl"


    // $ANTLR start "rule__Real_Value__Group__3"
    // InternalStructuredTextParser.g:7909:1: rule__Real_Value__Group__3 : rule__Real_Value__Group__3__Impl ;
    public final void rule__Real_Value__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7913:1: ( rule__Real_Value__Group__3__Impl )
            // InternalStructuredTextParser.g:7914:2: rule__Real_Value__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Real_Value__Group__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group__3"


    // $ANTLR start "rule__Real_Value__Group__3__Impl"
    // InternalStructuredTextParser.g:7920:1: rule__Real_Value__Group__3__Impl : ( ( rule__Real_Value__Group_3__0 )? ) ;
    public final void rule__Real_Value__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7924:1: ( ( ( rule__Real_Value__Group_3__0 )? ) )
            // InternalStructuredTextParser.g:7925:1: ( ( rule__Real_Value__Group_3__0 )? )
            {
            // InternalStructuredTextParser.g:7925:1: ( ( rule__Real_Value__Group_3__0 )? )
            // InternalStructuredTextParser.g:7926:2: ( rule__Real_Value__Group_3__0 )?
            {
             before(grammarAccess.getReal_ValueAccess().getGroup_3()); 
            // InternalStructuredTextParser.g:7927:2: ( rule__Real_Value__Group_3__0 )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==E) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalStructuredTextParser.g:7927:3: rule__Real_Value__Group_3__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group__3__Impl"


    // $ANTLR start "rule__Real_Value__Group_3__0"
    // InternalStructuredTextParser.g:7936:1: rule__Real_Value__Group_3__0 : rule__Real_Value__Group_3__0__Impl rule__Real_Value__Group_3__1 ;
    public final void rule__Real_Value__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7940:1: ( rule__Real_Value__Group_3__0__Impl rule__Real_Value__Group_3__1 )
            // InternalStructuredTextParser.g:7941:2: rule__Real_Value__Group_3__0__Impl rule__Real_Value__Group_3__1
            {
            pushFollow(FOLLOW_68);
            rule__Real_Value__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Real_Value__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group_3__0"


    // $ANTLR start "rule__Real_Value__Group_3__0__Impl"
    // InternalStructuredTextParser.g:7948:1: rule__Real_Value__Group_3__0__Impl : ( E ) ;
    public final void rule__Real_Value__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7952:1: ( ( E ) )
            // InternalStructuredTextParser.g:7953:1: ( E )
            {
            // InternalStructuredTextParser.g:7953:1: ( E )
            // InternalStructuredTextParser.g:7954:2: E
            {
             before(grammarAccess.getReal_ValueAccess().getEKeyword_3_0()); 
            match(input,E,FOLLOW_2); 
             after(grammarAccess.getReal_ValueAccess().getEKeyword_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group_3__0__Impl"


    // $ANTLR start "rule__Real_Value__Group_3__1"
    // InternalStructuredTextParser.g:7963:1: rule__Real_Value__Group_3__1 : rule__Real_Value__Group_3__1__Impl ;
    public final void rule__Real_Value__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7967:1: ( rule__Real_Value__Group_3__1__Impl )
            // InternalStructuredTextParser.g:7968:2: rule__Real_Value__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Real_Value__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group_3__1"


    // $ANTLR start "rule__Real_Value__Group_3__1__Impl"
    // InternalStructuredTextParser.g:7974:1: rule__Real_Value__Group_3__1__Impl : ( ruleSigned_Int ) ;
    public final void rule__Real_Value__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7978:1: ( ( ruleSigned_Int ) )
            // InternalStructuredTextParser.g:7979:1: ( ruleSigned_Int )
            {
            // InternalStructuredTextParser.g:7979:1: ( ruleSigned_Int )
            // InternalStructuredTextParser.g:7980:2: ruleSigned_Int
            {
             before(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_3_1()); 
            pushFollow(FOLLOW_2);
            ruleSigned_Int();

            state._fsp--;

             after(grammarAccess.getReal_ValueAccess().getSigned_IntParserRuleCall_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Value__Group_3__1__Impl"


    // $ANTLR start "rule__Bool_Literal__Group__0"
    // InternalStructuredTextParser.g:7990:1: rule__Bool_Literal__Group__0 : rule__Bool_Literal__Group__0__Impl rule__Bool_Literal__Group__1 ;
    public final void rule__Bool_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7994:1: ( rule__Bool_Literal__Group__0__Impl rule__Bool_Literal__Group__1 )
            // InternalStructuredTextParser.g:7995:2: rule__Bool_Literal__Group__0__Impl rule__Bool_Literal__Group__1
            {
            pushFollow(FOLLOW_17);
            rule__Bool_Literal__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Bool_Literal__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Literal__Group__0"


    // $ANTLR start "rule__Bool_Literal__Group__0__Impl"
    // InternalStructuredTextParser.g:8002:1: rule__Bool_Literal__Group__0__Impl : ( ( rule__Bool_Literal__Group_0__0 )? ) ;
    public final void rule__Bool_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8006:1: ( ( ( rule__Bool_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:8007:1: ( ( rule__Bool_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:8007:1: ( ( rule__Bool_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:8008:2: ( rule__Bool_Literal__Group_0__0 )?
            {
             before(grammarAccess.getBool_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:8009:2: ( rule__Bool_Literal__Group_0__0 )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==BOOL) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalStructuredTextParser.g:8009:3: rule__Bool_Literal__Group_0__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Literal__Group__0__Impl"


    // $ANTLR start "rule__Bool_Literal__Group__1"
    // InternalStructuredTextParser.g:8017:1: rule__Bool_Literal__Group__1 : rule__Bool_Literal__Group__1__Impl ;
    public final void rule__Bool_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8021:1: ( rule__Bool_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:8022:2: rule__Bool_Literal__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Bool_Literal__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Literal__Group__1"


    // $ANTLR start "rule__Bool_Literal__Group__1__Impl"
    // InternalStructuredTextParser.g:8028:1: rule__Bool_Literal__Group__1__Impl : ( ( rule__Bool_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Bool_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8032:1: ( ( ( rule__Bool_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:8033:1: ( ( rule__Bool_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:8033:1: ( ( rule__Bool_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:8034:2: ( rule__Bool_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getBool_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:8035:2: ( rule__Bool_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:8035:3: rule__Bool_Literal__ValueAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Bool_Literal__ValueAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getBool_LiteralAccess().getValueAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Literal__Group__1__Impl"


    // $ANTLR start "rule__Bool_Literal__Group_0__0"
    // InternalStructuredTextParser.g:8044:1: rule__Bool_Literal__Group_0__0 : rule__Bool_Literal__Group_0__0__Impl rule__Bool_Literal__Group_0__1 ;
    public final void rule__Bool_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8048:1: ( rule__Bool_Literal__Group_0__0__Impl rule__Bool_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:8049:2: rule__Bool_Literal__Group_0__0__Impl rule__Bool_Literal__Group_0__1
            {
            pushFollow(FOLLOW_67);
            rule__Bool_Literal__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Bool_Literal__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Literal__Group_0__0"


    // $ANTLR start "rule__Bool_Literal__Group_0__0__Impl"
    // InternalStructuredTextParser.g:8056:1: rule__Bool_Literal__Group_0__0__Impl : ( ( rule__Bool_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Bool_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8060:1: ( ( ( rule__Bool_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:8061:1: ( ( rule__Bool_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:8061:1: ( ( rule__Bool_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:8062:2: ( rule__Bool_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getBool_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:8063:2: ( rule__Bool_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:8063:3: rule__Bool_Literal__TypeAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Bool_Literal__TypeAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getBool_LiteralAccess().getTypeAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Literal__Group_0__0__Impl"


    // $ANTLR start "rule__Bool_Literal__Group_0__1"
    // InternalStructuredTextParser.g:8071:1: rule__Bool_Literal__Group_0__1 : rule__Bool_Literal__Group_0__1__Impl ;
    public final void rule__Bool_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8075:1: ( rule__Bool_Literal__Group_0__1__Impl )
            // InternalStructuredTextParser.g:8076:2: rule__Bool_Literal__Group_0__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Bool_Literal__Group_0__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Literal__Group_0__1"


    // $ANTLR start "rule__Bool_Literal__Group_0__1__Impl"
    // InternalStructuredTextParser.g:8082:1: rule__Bool_Literal__Group_0__1__Impl : ( NumberSign ) ;
    public final void rule__Bool_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8086:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:8087:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:8087:1: ( NumberSign )
            // InternalStructuredTextParser.g:8088:2: NumberSign
            {
             before(grammarAccess.getBool_LiteralAccess().getNumberSignKeyword_0_1()); 
            match(input,NumberSign,FOLLOW_2); 
             after(grammarAccess.getBool_LiteralAccess().getNumberSignKeyword_0_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Literal__Group_0__1__Impl"


    // $ANTLR start "rule__Char_Literal__Group__0"
    // InternalStructuredTextParser.g:8098:1: rule__Char_Literal__Group__0 : rule__Char_Literal__Group__0__Impl rule__Char_Literal__Group__1 ;
    public final void rule__Char_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8102:1: ( rule__Char_Literal__Group__0__Impl rule__Char_Literal__Group__1 )
            // InternalStructuredTextParser.g:8103:2: rule__Char_Literal__Group__0__Impl rule__Char_Literal__Group__1
            {
            pushFollow(FOLLOW_71);
            rule__Char_Literal__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Char_Literal__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__Group__0"


    // $ANTLR start "rule__Char_Literal__Group__0__Impl"
    // InternalStructuredTextParser.g:8110:1: rule__Char_Literal__Group__0__Impl : ( ( rule__Char_Literal__Group_0__0 )? ) ;
    public final void rule__Char_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8114:1: ( ( ( rule__Char_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:8115:1: ( ( rule__Char_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:8115:1: ( ( rule__Char_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:8116:2: ( rule__Char_Literal__Group_0__0 )?
            {
             before(grammarAccess.getChar_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:8117:2: ( rule__Char_Literal__Group_0__0 )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==WSTRING||LA65_0==STRING||LA65_0==WCHAR||LA65_0==CHAR) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalStructuredTextParser.g:8117:3: rule__Char_Literal__Group_0__0
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__Group__0__Impl"


    // $ANTLR start "rule__Char_Literal__Group__1"
    // InternalStructuredTextParser.g:8125:1: rule__Char_Literal__Group__1 : rule__Char_Literal__Group__1__Impl ;
    public final void rule__Char_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8129:1: ( rule__Char_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:8130:2: rule__Char_Literal__Group__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Char_Literal__Group__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__Group__1"


    // $ANTLR start "rule__Char_Literal__Group__1__Impl"
    // InternalStructuredTextParser.g:8136:1: rule__Char_Literal__Group__1__Impl : ( ( rule__Char_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Char_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8140:1: ( ( ( rule__Char_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:8141:1: ( ( rule__Char_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:8141:1: ( ( rule__Char_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:8142:2: ( rule__Char_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getChar_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:8143:2: ( rule__Char_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:8143:3: rule__Char_Literal__ValueAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Char_Literal__ValueAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getChar_LiteralAccess().getValueAssignment_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__Group__1__Impl"


    // $ANTLR start "rule__Char_Literal__Group_0__0"
    // InternalStructuredTextParser.g:8152:1: rule__Char_Literal__Group_0__0 : rule__Char_Literal__Group_0__0__Impl rule__Char_Literal__Group_0__1 ;
    public final void rule__Char_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8156:1: ( rule__Char_Literal__Group_0__0__Impl rule__Char_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:8157:2: rule__Char_Literal__Group_0__0__Impl rule__Char_Literal__Group_0__1
            {
            pushFollow(FOLLOW_72);
            rule__Char_Literal__Group_0__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Char_Literal__Group_0__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__Group_0__0"


    // $ANTLR start "rule__Char_Literal__Group_0__0__Impl"
    // InternalStructuredTextParser.g:8164:1: rule__Char_Literal__Group_0__0__Impl : ( ( rule__Char_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Char_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8168:1: ( ( ( rule__Char_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:8169:1: ( ( rule__Char_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:8169:1: ( ( rule__Char_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:8170:2: ( rule__Char_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getChar_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:8171:2: ( rule__Char_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:8171:3: rule__Char_Literal__TypeAssignment_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Char_Literal__TypeAssignment_0_0();

            state._fsp--;


            }

             after(grammarAccess.getChar_LiteralAccess().getTypeAssignment_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__Group_0__0__Impl"


    // $ANTLR start "rule__Char_Literal__Group_0__1"
    // InternalStructuredTextParser.g:8179:1: rule__Char_Literal__Group_0__1 : rule__Char_Literal__Group_0__1__Impl rule__Char_Literal__Group_0__2 ;
    public final void rule__Char_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8183:1: ( rule__Char_Literal__Group_0__1__Impl rule__Char_Literal__Group_0__2 )
            // InternalStructuredTextParser.g:8184:2: rule__Char_Literal__Group_0__1__Impl rule__Char_Literal__Group_0__2
            {
            pushFollow(FOLLOW_72);
            rule__Char_Literal__Group_0__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Char_Literal__Group_0__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__Group_0__1"


    // $ANTLR start "rule__Char_Literal__Group_0__1__Impl"
    // InternalStructuredTextParser.g:8191:1: rule__Char_Literal__Group_0__1__Impl : ( ( rule__Char_Literal__LengthAssignment_0_1 )? ) ;
    public final void rule__Char_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8195:1: ( ( ( rule__Char_Literal__LengthAssignment_0_1 )? ) )
            // InternalStructuredTextParser.g:8196:1: ( ( rule__Char_Literal__LengthAssignment_0_1 )? )
            {
            // InternalStructuredTextParser.g:8196:1: ( ( rule__Char_Literal__LengthAssignment_0_1 )? )
            // InternalStructuredTextParser.g:8197:2: ( rule__Char_Literal__LengthAssignment_0_1 )?
            {
             before(grammarAccess.getChar_LiteralAccess().getLengthAssignment_0_1()); 
            // InternalStructuredTextParser.g:8198:2: ( rule__Char_Literal__LengthAssignment_0_1 )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==RULE_UNSIGNED_INT) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalStructuredTextParser.g:8198:3: rule__Char_Literal__LengthAssignment_0_1
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

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__Group_0__1__Impl"


    // $ANTLR start "rule__Char_Literal__Group_0__2"
    // InternalStructuredTextParser.g:8206:1: rule__Char_Literal__Group_0__2 : rule__Char_Literal__Group_0__2__Impl ;
    public final void rule__Char_Literal__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8210:1: ( rule__Char_Literal__Group_0__2__Impl )
            // InternalStructuredTextParser.g:8211:2: rule__Char_Literal__Group_0__2__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Char_Literal__Group_0__2__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__Group_0__2"


    // $ANTLR start "rule__Char_Literal__Group_0__2__Impl"
    // InternalStructuredTextParser.g:8217:1: rule__Char_Literal__Group_0__2__Impl : ( NumberSign ) ;
    public final void rule__Char_Literal__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8221:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:8222:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:8222:1: ( NumberSign )
            // InternalStructuredTextParser.g:8223:2: NumberSign
            {
             before(grammarAccess.getChar_LiteralAccess().getNumberSignKeyword_0_2()); 
            match(input,NumberSign,FOLLOW_2); 
             after(grammarAccess.getChar_LiteralAccess().getNumberSignKeyword_0_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__Group_0__2__Impl"


    // $ANTLR start "rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0"
    // InternalStructuredTextParser.g:8233:1: rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 : ( ruleVar_Decl_Init ) ;
    public final void rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8237:1: ( ( ruleVar_Decl_Init ) )
            // InternalStructuredTextParser.g:8238:2: ( ruleVar_Decl_Init )
            {
            // InternalStructuredTextParser.g:8238:2: ( ruleVar_Decl_Init )
            // InternalStructuredTextParser.g:8239:3: ruleVar_Decl_Init
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getLocalVariablesVar_Decl_InitParserRuleCall_1_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleVar_Decl_Init();

            state._fsp--;

             after(grammarAccess.getStructuredTextAlgorithmAccess().getLocalVariablesVar_Decl_InitParserRuleCall_1_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0"


    // $ANTLR start "rule__StructuredTextAlgorithm__StatementsAssignment_2"
    // InternalStructuredTextParser.g:8248:1: rule__StructuredTextAlgorithm__StatementsAssignment_2 : ( ruleStmt_List ) ;
    public final void rule__StructuredTextAlgorithm__StatementsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8252:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8253:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8253:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8254:3: ruleStmt_List
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsStmt_ListParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleStmt_List();

            state._fsp--;

             after(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsStmt_ListParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__StructuredTextAlgorithm__StatementsAssignment_2"


    // $ANTLR start "rule__Var_Decl_Local__ConstantAssignment_1"
    // InternalStructuredTextParser.g:8263:1: rule__Var_Decl_Local__ConstantAssignment_1 : ( ( CONSTANT ) ) ;
    public final void rule__Var_Decl_Local__ConstantAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8267:1: ( ( ( CONSTANT ) ) )
            // InternalStructuredTextParser.g:8268:2: ( ( CONSTANT ) )
            {
            // InternalStructuredTextParser.g:8268:2: ( ( CONSTANT ) )
            // InternalStructuredTextParser.g:8269:3: ( CONSTANT )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0()); 
            // InternalStructuredTextParser.g:8270:3: ( CONSTANT )
            // InternalStructuredTextParser.g:8271:4: CONSTANT
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0()); 
            match(input,CONSTANT,FOLLOW_2); 
             after(grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0()); 

            }

             after(grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__ConstantAssignment_1"


    // $ANTLR start "rule__Var_Decl_Local__NameAssignment_2"
    // InternalStructuredTextParser.g:8282:1: rule__Var_Decl_Local__NameAssignment_2 : ( RULE_ID ) ;
    public final void rule__Var_Decl_Local__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8286:1: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:8287:2: ( RULE_ID )
            {
            // InternalStructuredTextParser.g:8287:2: ( RULE_ID )
            // InternalStructuredTextParser.g:8288:3: RULE_ID
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getNameIDTerminalRuleCall_2_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getVar_Decl_LocalAccess().getNameIDTerminalRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__NameAssignment_2"


    // $ANTLR start "rule__Var_Decl_Local__LocatedAssignment_3_0"
    // InternalStructuredTextParser.g:8297:1: rule__Var_Decl_Local__LocatedAssignment_3_0 : ( ( AT ) ) ;
    public final void rule__Var_Decl_Local__LocatedAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8301:1: ( ( ( AT ) ) )
            // InternalStructuredTextParser.g:8302:2: ( ( AT ) )
            {
            // InternalStructuredTextParser.g:8302:2: ( ( AT ) )
            // InternalStructuredTextParser.g:8303:3: ( AT )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocatedATKeyword_3_0_0()); 
            // InternalStructuredTextParser.g:8304:3: ( AT )
            // InternalStructuredTextParser.g:8305:4: AT
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocatedATKeyword_3_0_0()); 
            match(input,AT,FOLLOW_2); 
             after(grammarAccess.getVar_Decl_LocalAccess().getLocatedATKeyword_3_0_0()); 

            }

             after(grammarAccess.getVar_Decl_LocalAccess().getLocatedATKeyword_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__LocatedAssignment_3_0"


    // $ANTLR start "rule__Var_Decl_Local__LocationAssignment_3_1"
    // InternalStructuredTextParser.g:8316:1: rule__Var_Decl_Local__LocationAssignment_3_1 : ( ruleVariable ) ;
    public final void rule__Var_Decl_Local__LocationAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8320:1: ( ( ruleVariable ) )
            // InternalStructuredTextParser.g:8321:2: ( ruleVariable )
            {
            // InternalStructuredTextParser.g:8321:2: ( ruleVariable )
            // InternalStructuredTextParser.g:8322:3: ruleVariable
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocationVariableParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVariable();

            state._fsp--;

             after(grammarAccess.getVar_Decl_LocalAccess().getLocationVariableParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__LocationAssignment_3_1"


    // $ANTLR start "rule__Var_Decl_Local__ArrayAssignment_5_0"
    // InternalStructuredTextParser.g:8331:1: rule__Var_Decl_Local__ArrayAssignment_5_0 : ( ( ARRAY ) ) ;
    public final void rule__Var_Decl_Local__ArrayAssignment_5_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8335:1: ( ( ( ARRAY ) ) )
            // InternalStructuredTextParser.g:8336:2: ( ( ARRAY ) )
            {
            // InternalStructuredTextParser.g:8336:2: ( ( ARRAY ) )
            // InternalStructuredTextParser.g:8337:3: ( ARRAY )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayARRAYKeyword_5_0_0()); 
            // InternalStructuredTextParser.g:8338:3: ( ARRAY )
            // InternalStructuredTextParser.g:8339:4: ARRAY
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayARRAYKeyword_5_0_0()); 
            match(input,ARRAY,FOLLOW_2); 
             after(grammarAccess.getVar_Decl_LocalAccess().getArrayARRAYKeyword_5_0_0()); 

            }

             after(grammarAccess.getVar_Decl_LocalAccess().getArrayARRAYKeyword_5_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__ArrayAssignment_5_0"


    // $ANTLR start "rule__Var_Decl_Local__ArrayStartAssignment_5_2"
    // InternalStructuredTextParser.g:8350:1: rule__Var_Decl_Local__ArrayStartAssignment_5_2 : ( ruleArray_Size ) ;
    public final void rule__Var_Decl_Local__ArrayStartAssignment_5_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8354:1: ( ( ruleArray_Size ) )
            // InternalStructuredTextParser.g:8355:2: ( ruleArray_Size )
            {
            // InternalStructuredTextParser.g:8355:2: ( ruleArray_Size )
            // InternalStructuredTextParser.g:8356:3: ruleArray_Size
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayStartArray_SizeParserRuleCall_5_2_0()); 
            pushFollow(FOLLOW_2);
            ruleArray_Size();

            state._fsp--;

             after(grammarAccess.getVar_Decl_LocalAccess().getArrayStartArray_SizeParserRuleCall_5_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__ArrayStartAssignment_5_2"


    // $ANTLR start "rule__Var_Decl_Local__ArrayStopAssignment_5_4"
    // InternalStructuredTextParser.g:8365:1: rule__Var_Decl_Local__ArrayStopAssignment_5_4 : ( ruleArray_Size ) ;
    public final void rule__Var_Decl_Local__ArrayStopAssignment_5_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8369:1: ( ( ruleArray_Size ) )
            // InternalStructuredTextParser.g:8370:2: ( ruleArray_Size )
            {
            // InternalStructuredTextParser.g:8370:2: ( ruleArray_Size )
            // InternalStructuredTextParser.g:8371:3: ruleArray_Size
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayStopArray_SizeParserRuleCall_5_4_0()); 
            pushFollow(FOLLOW_2);
            ruleArray_Size();

            state._fsp--;

             after(grammarAccess.getVar_Decl_LocalAccess().getArrayStopArray_SizeParserRuleCall_5_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__ArrayStopAssignment_5_4"


    // $ANTLR start "rule__Var_Decl_Local__TypeAssignment_6"
    // InternalStructuredTextParser.g:8380:1: rule__Var_Decl_Local__TypeAssignment_6 : ( ( ruleType_Name ) ) ;
    public final void rule__Var_Decl_Local__TypeAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8384:1: ( ( ( ruleType_Name ) ) )
            // InternalStructuredTextParser.g:8385:2: ( ( ruleType_Name ) )
            {
            // InternalStructuredTextParser.g:8385:2: ( ( ruleType_Name ) )
            // InternalStructuredTextParser.g:8386:3: ( ruleType_Name )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeCrossReference_6_0()); 
            // InternalStructuredTextParser.g:8387:3: ( ruleType_Name )
            // InternalStructuredTextParser.g:8388:4: ruleType_Name
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeType_NameParserRuleCall_6_0_1()); 
            pushFollow(FOLLOW_2);
            ruleType_Name();

            state._fsp--;

             after(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeType_NameParserRuleCall_6_0_1()); 

            }

             after(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeCrossReference_6_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__TypeAssignment_6"


    // $ANTLR start "rule__Var_Decl_Local__InitalizedAssignment_7_0"
    // InternalStructuredTextParser.g:8399:1: rule__Var_Decl_Local__InitalizedAssignment_7_0 : ( ( ColonEqualsSign ) ) ;
    public final void rule__Var_Decl_Local__InitalizedAssignment_7_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8403:1: ( ( ( ColonEqualsSign ) ) )
            // InternalStructuredTextParser.g:8404:2: ( ( ColonEqualsSign ) )
            {
            // InternalStructuredTextParser.g:8404:2: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:8405:3: ( ColonEqualsSign )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitalizedColonEqualsSignKeyword_7_0_0()); 
            // InternalStructuredTextParser.g:8406:3: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:8407:4: ColonEqualsSign
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitalizedColonEqualsSignKeyword_7_0_0()); 
            match(input,ColonEqualsSign,FOLLOW_2); 
             after(grammarAccess.getVar_Decl_LocalAccess().getInitalizedColonEqualsSignKeyword_7_0_0()); 

            }

             after(grammarAccess.getVar_Decl_LocalAccess().getInitalizedColonEqualsSignKeyword_7_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__InitalizedAssignment_7_0"


    // $ANTLR start "rule__Var_Decl_Local__InitialValueAssignment_7_1"
    // InternalStructuredTextParser.g:8418:1: rule__Var_Decl_Local__InitialValueAssignment_7_1 : ( ruleConstant ) ;
    public final void rule__Var_Decl_Local__InitialValueAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8422:1: ( ( ruleConstant ) )
            // InternalStructuredTextParser.g:8423:2: ( ruleConstant )
            {
            // InternalStructuredTextParser.g:8423:2: ( ruleConstant )
            // InternalStructuredTextParser.g:8424:3: ruleConstant
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitialValueConstantParserRuleCall_7_1_0()); 
            pushFollow(FOLLOW_2);
            ruleConstant();

            state._fsp--;

             after(grammarAccess.getVar_Decl_LocalAccess().getInitialValueConstantParserRuleCall_7_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Var_Decl_Local__InitialValueAssignment_7_1"


    // $ANTLR start "rule__Stmt_List__StatementsAssignment_1_0"
    // InternalStructuredTextParser.g:8433:1: rule__Stmt_List__StatementsAssignment_1_0 : ( ruleStmt ) ;
    public final void rule__Stmt_List__StatementsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8437:1: ( ( ruleStmt ) )
            // InternalStructuredTextParser.g:8438:2: ( ruleStmt )
            {
            // InternalStructuredTextParser.g:8438:2: ( ruleStmt )
            // InternalStructuredTextParser.g:8439:3: ruleStmt
            {
             before(grammarAccess.getStmt_ListAccess().getStatementsStmtParserRuleCall_1_0_0()); 
            pushFollow(FOLLOW_2);
            ruleStmt();

            state._fsp--;

             after(grammarAccess.getStmt_ListAccess().getStatementsStmtParserRuleCall_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Stmt_List__StatementsAssignment_1_0"


    // $ANTLR start "rule__Assign_Stmt__VariableAssignment_0"
    // InternalStructuredTextParser.g:8448:1: rule__Assign_Stmt__VariableAssignment_0 : ( ruleVariable ) ;
    public final void rule__Assign_Stmt__VariableAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8452:1: ( ( ruleVariable ) )
            // InternalStructuredTextParser.g:8453:2: ( ruleVariable )
            {
            // InternalStructuredTextParser.g:8453:2: ( ruleVariable )
            // InternalStructuredTextParser.g:8454:3: ruleVariable
            {
             before(grammarAccess.getAssign_StmtAccess().getVariableVariableParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleVariable();

            state._fsp--;

             after(grammarAccess.getAssign_StmtAccess().getVariableVariableParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assign_Stmt__VariableAssignment_0"


    // $ANTLR start "rule__Assign_Stmt__ExpressionAssignment_2"
    // InternalStructuredTextParser.g:8463:1: rule__Assign_Stmt__ExpressionAssignment_2 : ( ruleExpression ) ;
    public final void rule__Assign_Stmt__ExpressionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8467:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8468:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8468:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8469:3: ruleExpression
            {
             before(grammarAccess.getAssign_StmtAccess().getExpressionExpressionParserRuleCall_2_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getAssign_StmtAccess().getExpressionExpressionParserRuleCall_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Assign_Stmt__ExpressionAssignment_2"


    // $ANTLR start "rule__IF_Stmt__ExpressionAssignment_1"
    // InternalStructuredTextParser.g:8478:1: rule__IF_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__IF_Stmt__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8482:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8483:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8483:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8484:3: ruleExpression
            {
             before(grammarAccess.getIF_StmtAccess().getExpressionExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getIF_StmtAccess().getExpressionExpressionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__ExpressionAssignment_1"


    // $ANTLR start "rule__IF_Stmt__StatmentsAssignment_3"
    // InternalStructuredTextParser.g:8493:1: rule__IF_Stmt__StatmentsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__IF_Stmt__StatmentsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8497:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8498:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8498:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8499:3: ruleStmt_List
            {
             before(grammarAccess.getIF_StmtAccess().getStatmentsStmt_ListParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleStmt_List();

            state._fsp--;

             after(grammarAccess.getIF_StmtAccess().getStatmentsStmt_ListParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__StatmentsAssignment_3"


    // $ANTLR start "rule__IF_Stmt__ElseifAssignment_4"
    // InternalStructuredTextParser.g:8508:1: rule__IF_Stmt__ElseifAssignment_4 : ( ruleELSIF_Clause ) ;
    public final void rule__IF_Stmt__ElseifAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8512:1: ( ( ruleELSIF_Clause ) )
            // InternalStructuredTextParser.g:8513:2: ( ruleELSIF_Clause )
            {
            // InternalStructuredTextParser.g:8513:2: ( ruleELSIF_Clause )
            // InternalStructuredTextParser.g:8514:3: ruleELSIF_Clause
            {
             before(grammarAccess.getIF_StmtAccess().getElseifELSIF_ClauseParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleELSIF_Clause();

            state._fsp--;

             after(grammarAccess.getIF_StmtAccess().getElseifELSIF_ClauseParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__ElseifAssignment_4"


    // $ANTLR start "rule__IF_Stmt__ElseAssignment_5"
    // InternalStructuredTextParser.g:8523:1: rule__IF_Stmt__ElseAssignment_5 : ( ruleELSE_Clause ) ;
    public final void rule__IF_Stmt__ElseAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8527:1: ( ( ruleELSE_Clause ) )
            // InternalStructuredTextParser.g:8528:2: ( ruleELSE_Clause )
            {
            // InternalStructuredTextParser.g:8528:2: ( ruleELSE_Clause )
            // InternalStructuredTextParser.g:8529:3: ruleELSE_Clause
            {
             before(grammarAccess.getIF_StmtAccess().getElseELSE_ClauseParserRuleCall_5_0()); 
            pushFollow(FOLLOW_2);
            ruleELSE_Clause();

            state._fsp--;

             after(grammarAccess.getIF_StmtAccess().getElseELSE_ClauseParserRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__IF_Stmt__ElseAssignment_5"


    // $ANTLR start "rule__ELSIF_Clause__ExpressionAssignment_1"
    // InternalStructuredTextParser.g:8538:1: rule__ELSIF_Clause__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__ELSIF_Clause__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8542:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8543:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8543:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8544:3: ruleExpression
            {
             before(grammarAccess.getELSIF_ClauseAccess().getExpressionExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getELSIF_ClauseAccess().getExpressionExpressionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSIF_Clause__ExpressionAssignment_1"


    // $ANTLR start "rule__ELSIF_Clause__StatementsAssignment_3"
    // InternalStructuredTextParser.g:8553:1: rule__ELSIF_Clause__StatementsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__ELSIF_Clause__StatementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8557:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8558:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8558:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8559:3: ruleStmt_List
            {
             before(grammarAccess.getELSIF_ClauseAccess().getStatementsStmt_ListParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleStmt_List();

            state._fsp--;

             after(grammarAccess.getELSIF_ClauseAccess().getStatementsStmt_ListParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSIF_Clause__StatementsAssignment_3"


    // $ANTLR start "rule__ELSE_Clause__StatementsAssignment_1"
    // InternalStructuredTextParser.g:8568:1: rule__ELSE_Clause__StatementsAssignment_1 : ( ruleStmt_List ) ;
    public final void rule__ELSE_Clause__StatementsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8572:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8573:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8573:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8574:3: ruleStmt_List
            {
             before(grammarAccess.getELSE_ClauseAccess().getStatementsStmt_ListParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleStmt_List();

            state._fsp--;

             after(grammarAccess.getELSE_ClauseAccess().getStatementsStmt_ListParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__ELSE_Clause__StatementsAssignment_1"


    // $ANTLR start "rule__Case_Stmt__ExpressionAssignment_1"
    // InternalStructuredTextParser.g:8583:1: rule__Case_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__Case_Stmt__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8587:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8588:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8588:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8589:3: ruleExpression
            {
             before(grammarAccess.getCase_StmtAccess().getExpressionExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getCase_StmtAccess().getExpressionExpressionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__ExpressionAssignment_1"


    // $ANTLR start "rule__Case_Stmt__CaseAssignment_3"
    // InternalStructuredTextParser.g:8598:1: rule__Case_Stmt__CaseAssignment_3 : ( ruleCase_Selection ) ;
    public final void rule__Case_Stmt__CaseAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8602:1: ( ( ruleCase_Selection ) )
            // InternalStructuredTextParser.g:8603:2: ( ruleCase_Selection )
            {
            // InternalStructuredTextParser.g:8603:2: ( ruleCase_Selection )
            // InternalStructuredTextParser.g:8604:3: ruleCase_Selection
            {
             before(grammarAccess.getCase_StmtAccess().getCaseCase_SelectionParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleCase_Selection();

            state._fsp--;

             after(grammarAccess.getCase_StmtAccess().getCaseCase_SelectionParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__CaseAssignment_3"


    // $ANTLR start "rule__Case_Stmt__ElseAssignment_4"
    // InternalStructuredTextParser.g:8613:1: rule__Case_Stmt__ElseAssignment_4 : ( ruleELSE_Clause ) ;
    public final void rule__Case_Stmt__ElseAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8617:1: ( ( ruleELSE_Clause ) )
            // InternalStructuredTextParser.g:8618:2: ( ruleELSE_Clause )
            {
            // InternalStructuredTextParser.g:8618:2: ( ruleELSE_Clause )
            // InternalStructuredTextParser.g:8619:3: ruleELSE_Clause
            {
             before(grammarAccess.getCase_StmtAccess().getElseELSE_ClauseParserRuleCall_4_0()); 
            pushFollow(FOLLOW_2);
            ruleELSE_Clause();

            state._fsp--;

             after(grammarAccess.getCase_StmtAccess().getElseELSE_ClauseParserRuleCall_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Stmt__ElseAssignment_4"


    // $ANTLR start "rule__Case_Selection__CaseAssignment_0"
    // InternalStructuredTextParser.g:8628:1: rule__Case_Selection__CaseAssignment_0 : ( ruleConstant ) ;
    public final void rule__Case_Selection__CaseAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8632:1: ( ( ruleConstant ) )
            // InternalStructuredTextParser.g:8633:2: ( ruleConstant )
            {
            // InternalStructuredTextParser.g:8633:2: ( ruleConstant )
            // InternalStructuredTextParser.g:8634:3: ruleConstant
            {
             before(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_0_0()); 
            pushFollow(FOLLOW_2);
            ruleConstant();

            state._fsp--;

             after(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__CaseAssignment_0"


    // $ANTLR start "rule__Case_Selection__CaseAssignment_1_1"
    // InternalStructuredTextParser.g:8643:1: rule__Case_Selection__CaseAssignment_1_1 : ( ruleConstant ) ;
    public final void rule__Case_Selection__CaseAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8647:1: ( ( ruleConstant ) )
            // InternalStructuredTextParser.g:8648:2: ( ruleConstant )
            {
            // InternalStructuredTextParser.g:8648:2: ( ruleConstant )
            // InternalStructuredTextParser.g:8649:3: ruleConstant
            {
             before(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleConstant();

            state._fsp--;

             after(grammarAccess.getCase_SelectionAccess().getCaseConstantParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__CaseAssignment_1_1"


    // $ANTLR start "rule__Case_Selection__StatementsAssignment_3"
    // InternalStructuredTextParser.g:8658:1: rule__Case_Selection__StatementsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__Case_Selection__StatementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8662:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8663:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8663:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8664:3: ruleStmt_List
            {
             before(grammarAccess.getCase_SelectionAccess().getStatementsStmt_ListParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleStmt_List();

            state._fsp--;

             after(grammarAccess.getCase_SelectionAccess().getStatementsStmt_ListParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Case_Selection__StatementsAssignment_3"


    // $ANTLR start "rule__For_Stmt__VariableAssignment_1"
    // InternalStructuredTextParser.g:8673:1: rule__For_Stmt__VariableAssignment_1 : ( ruleVariable_Primary ) ;
    public final void rule__For_Stmt__VariableAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8677:1: ( ( ruleVariable_Primary ) )
            // InternalStructuredTextParser.g:8678:2: ( ruleVariable_Primary )
            {
            // InternalStructuredTextParser.g:8678:2: ( ruleVariable_Primary )
            // InternalStructuredTextParser.g:8679:3: ruleVariable_Primary
            {
             before(grammarAccess.getFor_StmtAccess().getVariableVariable_PrimaryParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleVariable_Primary();

            state._fsp--;

             after(grammarAccess.getFor_StmtAccess().getVariableVariable_PrimaryParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__VariableAssignment_1"


    // $ANTLR start "rule__For_Stmt__FromAssignment_3"
    // InternalStructuredTextParser.g:8688:1: rule__For_Stmt__FromAssignment_3 : ( ruleExpression ) ;
    public final void rule__For_Stmt__FromAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8692:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8693:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8693:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8694:3: ruleExpression
            {
             before(grammarAccess.getFor_StmtAccess().getFromExpressionParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getFor_StmtAccess().getFromExpressionParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__FromAssignment_3"


    // $ANTLR start "rule__For_Stmt__ToAssignment_5"
    // InternalStructuredTextParser.g:8703:1: rule__For_Stmt__ToAssignment_5 : ( ruleExpression ) ;
    public final void rule__For_Stmt__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8707:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8708:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8708:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8709:3: ruleExpression
            {
             before(grammarAccess.getFor_StmtAccess().getToExpressionParserRuleCall_5_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getFor_StmtAccess().getToExpressionParserRuleCall_5_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__ToAssignment_5"


    // $ANTLR start "rule__For_Stmt__ByAssignment_6_1"
    // InternalStructuredTextParser.g:8718:1: rule__For_Stmt__ByAssignment_6_1 : ( ruleExpression ) ;
    public final void rule__For_Stmt__ByAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8722:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8723:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8723:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8724:3: ruleExpression
            {
             before(grammarAccess.getFor_StmtAccess().getByExpressionParserRuleCall_6_1_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getFor_StmtAccess().getByExpressionParserRuleCall_6_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__ByAssignment_6_1"


    // $ANTLR start "rule__For_Stmt__StatementsAssignment_8"
    // InternalStructuredTextParser.g:8733:1: rule__For_Stmt__StatementsAssignment_8 : ( ruleStmt_List ) ;
    public final void rule__For_Stmt__StatementsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8737:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8738:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8738:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8739:3: ruleStmt_List
            {
             before(grammarAccess.getFor_StmtAccess().getStatementsStmt_ListParserRuleCall_8_0()); 
            pushFollow(FOLLOW_2);
            ruleStmt_List();

            state._fsp--;

             after(grammarAccess.getFor_StmtAccess().getStatementsStmt_ListParserRuleCall_8_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__For_Stmt__StatementsAssignment_8"


    // $ANTLR start "rule__While_Stmt__ExpressionAssignment_1"
    // InternalStructuredTextParser.g:8748:1: rule__While_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__While_Stmt__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8752:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8753:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8753:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8754:3: ruleExpression
            {
             before(grammarAccess.getWhile_StmtAccess().getExpressionExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getWhile_StmtAccess().getExpressionExpressionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__ExpressionAssignment_1"


    // $ANTLR start "rule__While_Stmt__StatementsAssignment_3"
    // InternalStructuredTextParser.g:8763:1: rule__While_Stmt__StatementsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__While_Stmt__StatementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8767:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8768:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8768:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8769:3: ruleStmt_List
            {
             before(grammarAccess.getWhile_StmtAccess().getStatementsStmt_ListParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleStmt_List();

            state._fsp--;

             after(grammarAccess.getWhile_StmtAccess().getStatementsStmt_ListParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__While_Stmt__StatementsAssignment_3"


    // $ANTLR start "rule__Repeat_Stmt__StatementsAssignment_1"
    // InternalStructuredTextParser.g:8778:1: rule__Repeat_Stmt__StatementsAssignment_1 : ( ruleStmt_List ) ;
    public final void rule__Repeat_Stmt__StatementsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8782:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8783:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8783:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8784:3: ruleStmt_List
            {
             before(grammarAccess.getRepeat_StmtAccess().getStatementsStmt_ListParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleStmt_List();

            state._fsp--;

             after(grammarAccess.getRepeat_StmtAccess().getStatementsStmt_ListParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__StatementsAssignment_1"


    // $ANTLR start "rule__Repeat_Stmt__ExpressionAssignment_3"
    // InternalStructuredTextParser.g:8793:1: rule__Repeat_Stmt__ExpressionAssignment_3 : ( ruleExpression ) ;
    public final void rule__Repeat_Stmt__ExpressionAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8797:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8798:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8798:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8799:3: ruleExpression
            {
             before(grammarAccess.getRepeat_StmtAccess().getExpressionExpressionParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getRepeat_StmtAccess().getExpressionExpressionParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Repeat_Stmt__ExpressionAssignment_3"


    // $ANTLR start "rule__Or_Expression__OperatorAssignment_1_1"
    // InternalStructuredTextParser.g:8808:1: rule__Or_Expression__OperatorAssignment_1_1 : ( ruleOr_Operator ) ;
    public final void rule__Or_Expression__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8812:1: ( ( ruleOr_Operator ) )
            // InternalStructuredTextParser.g:8813:2: ( ruleOr_Operator )
            {
            // InternalStructuredTextParser.g:8813:2: ( ruleOr_Operator )
            // InternalStructuredTextParser.g:8814:3: ruleOr_Operator
            {
             before(grammarAccess.getOr_ExpressionAccess().getOperatorOr_OperatorEnumRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleOr_Operator();

            state._fsp--;

             after(grammarAccess.getOr_ExpressionAccess().getOperatorOr_OperatorEnumRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__OperatorAssignment_1_1"


    // $ANTLR start "rule__Or_Expression__RightAssignment_1_2"
    // InternalStructuredTextParser.g:8823:1: rule__Or_Expression__RightAssignment_1_2 : ( ruleXor_Expr ) ;
    public final void rule__Or_Expression__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8827:1: ( ( ruleXor_Expr ) )
            // InternalStructuredTextParser.g:8828:2: ( ruleXor_Expr )
            {
            // InternalStructuredTextParser.g:8828:2: ( ruleXor_Expr )
            // InternalStructuredTextParser.g:8829:3: ruleXor_Expr
            {
             before(grammarAccess.getOr_ExpressionAccess().getRightXor_ExprParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_2);
            ruleXor_Expr();

            state._fsp--;

             after(grammarAccess.getOr_ExpressionAccess().getRightXor_ExprParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Or_Expression__RightAssignment_1_2"


    // $ANTLR start "rule__Xor_Expr__OperatorAssignment_1_1"
    // InternalStructuredTextParser.g:8838:1: rule__Xor_Expr__OperatorAssignment_1_1 : ( ruleXor_Operator ) ;
    public final void rule__Xor_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8842:1: ( ( ruleXor_Operator ) )
            // InternalStructuredTextParser.g:8843:2: ( ruleXor_Operator )
            {
            // InternalStructuredTextParser.g:8843:2: ( ruleXor_Operator )
            // InternalStructuredTextParser.g:8844:3: ruleXor_Operator
            {
             before(grammarAccess.getXor_ExprAccess().getOperatorXor_OperatorEnumRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleXor_Operator();

            state._fsp--;

             after(grammarAccess.getXor_ExprAccess().getOperatorXor_OperatorEnumRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__OperatorAssignment_1_1"


    // $ANTLR start "rule__Xor_Expr__RightAssignment_1_2"
    // InternalStructuredTextParser.g:8853:1: rule__Xor_Expr__RightAssignment_1_2 : ( ruleAnd_Expr ) ;
    public final void rule__Xor_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8857:1: ( ( ruleAnd_Expr ) )
            // InternalStructuredTextParser.g:8858:2: ( ruleAnd_Expr )
            {
            // InternalStructuredTextParser.g:8858:2: ( ruleAnd_Expr )
            // InternalStructuredTextParser.g:8859:3: ruleAnd_Expr
            {
             before(grammarAccess.getXor_ExprAccess().getRightAnd_ExprParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_2);
            ruleAnd_Expr();

            state._fsp--;

             after(grammarAccess.getXor_ExprAccess().getRightAnd_ExprParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Xor_Expr__RightAssignment_1_2"


    // $ANTLR start "rule__And_Expr__OperatorAssignment_1_1"
    // InternalStructuredTextParser.g:8868:1: rule__And_Expr__OperatorAssignment_1_1 : ( ruleAnd_Operator ) ;
    public final void rule__And_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8872:1: ( ( ruleAnd_Operator ) )
            // InternalStructuredTextParser.g:8873:2: ( ruleAnd_Operator )
            {
            // InternalStructuredTextParser.g:8873:2: ( ruleAnd_Operator )
            // InternalStructuredTextParser.g:8874:3: ruleAnd_Operator
            {
             before(grammarAccess.getAnd_ExprAccess().getOperatorAnd_OperatorEnumRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleAnd_Operator();

            state._fsp--;

             after(grammarAccess.getAnd_ExprAccess().getOperatorAnd_OperatorEnumRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__OperatorAssignment_1_1"


    // $ANTLR start "rule__And_Expr__RightAssignment_1_2"
    // InternalStructuredTextParser.g:8883:1: rule__And_Expr__RightAssignment_1_2 : ( ruleCompare_Expr ) ;
    public final void rule__And_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8887:1: ( ( ruleCompare_Expr ) )
            // InternalStructuredTextParser.g:8888:2: ( ruleCompare_Expr )
            {
            // InternalStructuredTextParser.g:8888:2: ( ruleCompare_Expr )
            // InternalStructuredTextParser.g:8889:3: ruleCompare_Expr
            {
             before(grammarAccess.getAnd_ExprAccess().getRightCompare_ExprParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_2);
            ruleCompare_Expr();

            state._fsp--;

             after(grammarAccess.getAnd_ExprAccess().getRightCompare_ExprParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__And_Expr__RightAssignment_1_2"


    // $ANTLR start "rule__Compare_Expr__OperatorAssignment_1_1"
    // InternalStructuredTextParser.g:8898:1: rule__Compare_Expr__OperatorAssignment_1_1 : ( ruleCompare_Operator ) ;
    public final void rule__Compare_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8902:1: ( ( ruleCompare_Operator ) )
            // InternalStructuredTextParser.g:8903:2: ( ruleCompare_Operator )
            {
            // InternalStructuredTextParser.g:8903:2: ( ruleCompare_Operator )
            // InternalStructuredTextParser.g:8904:3: ruleCompare_Operator
            {
             before(grammarAccess.getCompare_ExprAccess().getOperatorCompare_OperatorEnumRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleCompare_Operator();

            state._fsp--;

             after(grammarAccess.getCompare_ExprAccess().getOperatorCompare_OperatorEnumRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__OperatorAssignment_1_1"


    // $ANTLR start "rule__Compare_Expr__RightAssignment_1_2"
    // InternalStructuredTextParser.g:8913:1: rule__Compare_Expr__RightAssignment_1_2 : ( ruleEqu_Expr ) ;
    public final void rule__Compare_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8917:1: ( ( ruleEqu_Expr ) )
            // InternalStructuredTextParser.g:8918:2: ( ruleEqu_Expr )
            {
            // InternalStructuredTextParser.g:8918:2: ( ruleEqu_Expr )
            // InternalStructuredTextParser.g:8919:3: ruleEqu_Expr
            {
             before(grammarAccess.getCompare_ExprAccess().getRightEqu_ExprParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_2);
            ruleEqu_Expr();

            state._fsp--;

             after(grammarAccess.getCompare_ExprAccess().getRightEqu_ExprParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Compare_Expr__RightAssignment_1_2"


    // $ANTLR start "rule__Equ_Expr__OperatorAssignment_1_1"
    // InternalStructuredTextParser.g:8928:1: rule__Equ_Expr__OperatorAssignment_1_1 : ( ruleEqu_Operator ) ;
    public final void rule__Equ_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8932:1: ( ( ruleEqu_Operator ) )
            // InternalStructuredTextParser.g:8933:2: ( ruleEqu_Operator )
            {
            // InternalStructuredTextParser.g:8933:2: ( ruleEqu_Operator )
            // InternalStructuredTextParser.g:8934:3: ruleEqu_Operator
            {
             before(grammarAccess.getEqu_ExprAccess().getOperatorEqu_OperatorEnumRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleEqu_Operator();

            state._fsp--;

             after(grammarAccess.getEqu_ExprAccess().getOperatorEqu_OperatorEnumRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__OperatorAssignment_1_1"


    // $ANTLR start "rule__Equ_Expr__RightAssignment_1_2"
    // InternalStructuredTextParser.g:8943:1: rule__Equ_Expr__RightAssignment_1_2 : ( ruleAdd_Expr ) ;
    public final void rule__Equ_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8947:1: ( ( ruleAdd_Expr ) )
            // InternalStructuredTextParser.g:8948:2: ( ruleAdd_Expr )
            {
            // InternalStructuredTextParser.g:8948:2: ( ruleAdd_Expr )
            // InternalStructuredTextParser.g:8949:3: ruleAdd_Expr
            {
             before(grammarAccess.getEqu_ExprAccess().getRightAdd_ExprParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_2);
            ruleAdd_Expr();

            state._fsp--;

             after(grammarAccess.getEqu_ExprAccess().getRightAdd_ExprParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Equ_Expr__RightAssignment_1_2"


    // $ANTLR start "rule__Add_Expr__OperatorAssignment_1_1"
    // InternalStructuredTextParser.g:8958:1: rule__Add_Expr__OperatorAssignment_1_1 : ( ruleAdd_Operator ) ;
    public final void rule__Add_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8962:1: ( ( ruleAdd_Operator ) )
            // InternalStructuredTextParser.g:8963:2: ( ruleAdd_Operator )
            {
            // InternalStructuredTextParser.g:8963:2: ( ruleAdd_Operator )
            // InternalStructuredTextParser.g:8964:3: ruleAdd_Operator
            {
             before(grammarAccess.getAdd_ExprAccess().getOperatorAdd_OperatorEnumRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleAdd_Operator();

            state._fsp--;

             after(grammarAccess.getAdd_ExprAccess().getOperatorAdd_OperatorEnumRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__OperatorAssignment_1_1"


    // $ANTLR start "rule__Add_Expr__RightAssignment_1_2"
    // InternalStructuredTextParser.g:8973:1: rule__Add_Expr__RightAssignment_1_2 : ( ruleTerm ) ;
    public final void rule__Add_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8977:1: ( ( ruleTerm ) )
            // InternalStructuredTextParser.g:8978:2: ( ruleTerm )
            {
            // InternalStructuredTextParser.g:8978:2: ( ruleTerm )
            // InternalStructuredTextParser.g:8979:3: ruleTerm
            {
             before(grammarAccess.getAdd_ExprAccess().getRightTermParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_2);
            ruleTerm();

            state._fsp--;

             after(grammarAccess.getAdd_ExprAccess().getRightTermParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Add_Expr__RightAssignment_1_2"


    // $ANTLR start "rule__Term__OperatorAssignment_1_1"
    // InternalStructuredTextParser.g:8988:1: rule__Term__OperatorAssignment_1_1 : ( ruleTerm_Operator ) ;
    public final void rule__Term__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8992:1: ( ( ruleTerm_Operator ) )
            // InternalStructuredTextParser.g:8993:2: ( ruleTerm_Operator )
            {
            // InternalStructuredTextParser.g:8993:2: ( ruleTerm_Operator )
            // InternalStructuredTextParser.g:8994:3: ruleTerm_Operator
            {
             before(grammarAccess.getTermAccess().getOperatorTerm_OperatorEnumRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleTerm_Operator();

            state._fsp--;

             after(grammarAccess.getTermAccess().getOperatorTerm_OperatorEnumRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__OperatorAssignment_1_1"


    // $ANTLR start "rule__Term__RightAssignment_1_2"
    // InternalStructuredTextParser.g:9003:1: rule__Term__RightAssignment_1_2 : ( rulePower_Expr ) ;
    public final void rule__Term__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9007:1: ( ( rulePower_Expr ) )
            // InternalStructuredTextParser.g:9008:2: ( rulePower_Expr )
            {
            // InternalStructuredTextParser.g:9008:2: ( rulePower_Expr )
            // InternalStructuredTextParser.g:9009:3: rulePower_Expr
            {
             before(grammarAccess.getTermAccess().getRightPower_ExprParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_2);
            rulePower_Expr();

            state._fsp--;

             after(grammarAccess.getTermAccess().getRightPower_ExprParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Term__RightAssignment_1_2"


    // $ANTLR start "rule__Power_Expr__OperatorAssignment_1_1"
    // InternalStructuredTextParser.g:9018:1: rule__Power_Expr__OperatorAssignment_1_1 : ( rulePower_Operator ) ;
    public final void rule__Power_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9022:1: ( ( rulePower_Operator ) )
            // InternalStructuredTextParser.g:9023:2: ( rulePower_Operator )
            {
            // InternalStructuredTextParser.g:9023:2: ( rulePower_Operator )
            // InternalStructuredTextParser.g:9024:3: rulePower_Operator
            {
             before(grammarAccess.getPower_ExprAccess().getOperatorPower_OperatorEnumRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            rulePower_Operator();

            state._fsp--;

             after(grammarAccess.getPower_ExprAccess().getOperatorPower_OperatorEnumRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__OperatorAssignment_1_1"


    // $ANTLR start "rule__Power_Expr__RightAssignment_1_2"
    // InternalStructuredTextParser.g:9033:1: rule__Power_Expr__RightAssignment_1_2 : ( ruleUnary_Expr ) ;
    public final void rule__Power_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9037:1: ( ( ruleUnary_Expr ) )
            // InternalStructuredTextParser.g:9038:2: ( ruleUnary_Expr )
            {
            // InternalStructuredTextParser.g:9038:2: ( ruleUnary_Expr )
            // InternalStructuredTextParser.g:9039:3: ruleUnary_Expr
            {
             before(grammarAccess.getPower_ExprAccess().getRightUnary_ExprParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_2);
            ruleUnary_Expr();

            state._fsp--;

             after(grammarAccess.getPower_ExprAccess().getRightUnary_ExprParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Power_Expr__RightAssignment_1_2"


    // $ANTLR start "rule__Unary_Expr__OperatorAssignment_0_1"
    // InternalStructuredTextParser.g:9048:1: rule__Unary_Expr__OperatorAssignment_0_1 : ( ruleUnary_Operator ) ;
    public final void rule__Unary_Expr__OperatorAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9052:1: ( ( ruleUnary_Operator ) )
            // InternalStructuredTextParser.g:9053:2: ( ruleUnary_Operator )
            {
            // InternalStructuredTextParser.g:9053:2: ( ruleUnary_Operator )
            // InternalStructuredTextParser.g:9054:3: ruleUnary_Operator
            {
             before(grammarAccess.getUnary_ExprAccess().getOperatorUnary_OperatorEnumRuleCall_0_1_0()); 
            pushFollow(FOLLOW_2);
            ruleUnary_Operator();

            state._fsp--;

             after(grammarAccess.getUnary_ExprAccess().getOperatorUnary_OperatorEnumRuleCall_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Unary_Expr__OperatorAssignment_0_1"


    // $ANTLR start "rule__Unary_Expr__ExpressionAssignment_0_2"
    // InternalStructuredTextParser.g:9063:1: rule__Unary_Expr__ExpressionAssignment_0_2 : ( rulePrimary_Expr ) ;
    public final void rule__Unary_Expr__ExpressionAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9067:1: ( ( rulePrimary_Expr ) )
            // InternalStructuredTextParser.g:9068:2: ( rulePrimary_Expr )
            {
            // InternalStructuredTextParser.g:9068:2: ( rulePrimary_Expr )
            // InternalStructuredTextParser.g:9069:3: rulePrimary_Expr
            {
             before(grammarAccess.getUnary_ExprAccess().getExpressionPrimary_ExprParserRuleCall_0_2_0()); 
            pushFollow(FOLLOW_2);
            rulePrimary_Expr();

            state._fsp--;

             after(grammarAccess.getUnary_ExprAccess().getExpressionPrimary_ExprParserRuleCall_0_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Unary_Expr__ExpressionAssignment_0_2"


    // $ANTLR start "rule__Func_Call__FuncAssignment_0"
    // InternalStructuredTextParser.g:9078:1: rule__Func_Call__FuncAssignment_0 : ( ( rule__Func_Call__FuncAlternatives_0_0 ) ) ;
    public final void rule__Func_Call__FuncAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9082:1: ( ( ( rule__Func_Call__FuncAlternatives_0_0 ) ) )
            // InternalStructuredTextParser.g:9083:2: ( ( rule__Func_Call__FuncAlternatives_0_0 ) )
            {
            // InternalStructuredTextParser.g:9083:2: ( ( rule__Func_Call__FuncAlternatives_0_0 ) )
            // InternalStructuredTextParser.g:9084:3: ( rule__Func_Call__FuncAlternatives_0_0 )
            {
             before(grammarAccess.getFunc_CallAccess().getFuncAlternatives_0_0()); 
            // InternalStructuredTextParser.g:9085:3: ( rule__Func_Call__FuncAlternatives_0_0 )
            // InternalStructuredTextParser.g:9085:4: rule__Func_Call__FuncAlternatives_0_0
            {
            pushFollow(FOLLOW_2);
            rule__Func_Call__FuncAlternatives_0_0();

            state._fsp--;


            }

             after(grammarAccess.getFunc_CallAccess().getFuncAlternatives_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__FuncAssignment_0"


    // $ANTLR start "rule__Func_Call__ArgsAssignment_2_0"
    // InternalStructuredTextParser.g:9093:1: rule__Func_Call__ArgsAssignment_2_0 : ( ruleParam_Assign ) ;
    public final void rule__Func_Call__ArgsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9097:1: ( ( ruleParam_Assign ) )
            // InternalStructuredTextParser.g:9098:2: ( ruleParam_Assign )
            {
            // InternalStructuredTextParser.g:9098:2: ( ruleParam_Assign )
            // InternalStructuredTextParser.g:9099:3: ruleParam_Assign
            {
             before(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_0_0()); 
            pushFollow(FOLLOW_2);
            ruleParam_Assign();

            state._fsp--;

             after(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__ArgsAssignment_2_0"


    // $ANTLR start "rule__Func_Call__ArgsAssignment_2_1_1"
    // InternalStructuredTextParser.g:9108:1: rule__Func_Call__ArgsAssignment_2_1_1 : ( ruleParam_Assign ) ;
    public final void rule__Func_Call__ArgsAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9112:1: ( ( ruleParam_Assign ) )
            // InternalStructuredTextParser.g:9113:2: ( ruleParam_Assign )
            {
            // InternalStructuredTextParser.g:9113:2: ( ruleParam_Assign )
            // InternalStructuredTextParser.g:9114:3: ruleParam_Assign
            {
             before(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleParam_Assign();

            state._fsp--;

             after(grammarAccess.getFunc_CallAccess().getArgsParam_AssignParserRuleCall_2_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Func_Call__ArgsAssignment_2_1_1"


    // $ANTLR start "rule__Param_Assign_In__VarAssignment_0_0"
    // InternalStructuredTextParser.g:9123:1: rule__Param_Assign_In__VarAssignment_0_0 : ( RULE_ID ) ;
    public final void rule__Param_Assign_In__VarAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9127:1: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:9128:2: ( RULE_ID )
            {
            // InternalStructuredTextParser.g:9128:2: ( RULE_ID )
            // InternalStructuredTextParser.g:9129:3: RULE_ID
            {
             before(grammarAccess.getParam_Assign_InAccess().getVarIDTerminalRuleCall_0_0_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getParam_Assign_InAccess().getVarIDTerminalRuleCall_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_In__VarAssignment_0_0"


    // $ANTLR start "rule__Param_Assign_In__ExprAssignment_1"
    // InternalStructuredTextParser.g:9138:1: rule__Param_Assign_In__ExprAssignment_1 : ( ruleExpression ) ;
    public final void rule__Param_Assign_In__ExprAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9142:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9143:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9143:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9144:3: ruleExpression
            {
             before(grammarAccess.getParam_Assign_InAccess().getExprExpressionParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getParam_Assign_InAccess().getExprExpressionParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_In__ExprAssignment_1"


    // $ANTLR start "rule__Param_Assign_Out__NotAssignment_0"
    // InternalStructuredTextParser.g:9153:1: rule__Param_Assign_Out__NotAssignment_0 : ( ( NOT ) ) ;
    public final void rule__Param_Assign_Out__NotAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9157:1: ( ( ( NOT ) ) )
            // InternalStructuredTextParser.g:9158:2: ( ( NOT ) )
            {
            // InternalStructuredTextParser.g:9158:2: ( ( NOT ) )
            // InternalStructuredTextParser.g:9159:3: ( NOT )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0()); 
            // InternalStructuredTextParser.g:9160:3: ( NOT )
            // InternalStructuredTextParser.g:9161:4: NOT
            {
             before(grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0()); 
            match(input,NOT,FOLLOW_2); 
             after(grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0()); 

            }

             after(grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__NotAssignment_0"


    // $ANTLR start "rule__Param_Assign_Out__VarAssignment_1"
    // InternalStructuredTextParser.g:9172:1: rule__Param_Assign_Out__VarAssignment_1 : ( RULE_ID ) ;
    public final void rule__Param_Assign_Out__VarAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9176:1: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:9177:2: ( RULE_ID )
            {
            // InternalStructuredTextParser.g:9177:2: ( RULE_ID )
            // InternalStructuredTextParser.g:9178:3: RULE_ID
            {
             before(grammarAccess.getParam_Assign_OutAccess().getVarIDTerminalRuleCall_1_0()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getParam_Assign_OutAccess().getVarIDTerminalRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__VarAssignment_1"


    // $ANTLR start "rule__Param_Assign_Out__ExprAssignment_3"
    // InternalStructuredTextParser.g:9187:1: rule__Param_Assign_Out__ExprAssignment_3 : ( ruleVariable ) ;
    public final void rule__Param_Assign_Out__ExprAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9191:1: ( ( ruleVariable ) )
            // InternalStructuredTextParser.g:9192:2: ( ruleVariable )
            {
            // InternalStructuredTextParser.g:9192:2: ( ruleVariable )
            // InternalStructuredTextParser.g:9193:3: ruleVariable
            {
             before(grammarAccess.getParam_Assign_OutAccess().getExprVariableParserRuleCall_3_0()); 
            pushFollow(FOLLOW_2);
            ruleVariable();

            state._fsp--;

             after(grammarAccess.getParam_Assign_OutAccess().getExprVariableParserRuleCall_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Param_Assign_Out__ExprAssignment_3"


    // $ANTLR start "rule__Variable__PartAssignment_1"
    // InternalStructuredTextParser.g:9202:1: rule__Variable__PartAssignment_1 : ( ruleMultibit_Part_Access ) ;
    public final void rule__Variable__PartAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9206:1: ( ( ruleMultibit_Part_Access ) )
            // InternalStructuredTextParser.g:9207:2: ( ruleMultibit_Part_Access )
            {
            // InternalStructuredTextParser.g:9207:2: ( ruleMultibit_Part_Access )
            // InternalStructuredTextParser.g:9208:3: ruleMultibit_Part_Access
            {
             before(grammarAccess.getVariableAccess().getPartMultibit_Part_AccessParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleMultibit_Part_Access();

            state._fsp--;

             after(grammarAccess.getVariableAccess().getPartMultibit_Part_AccessParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable__PartAssignment_1"


    // $ANTLR start "rule__Variable_Subscript__IndexAssignment_1_2"
    // InternalStructuredTextParser.g:9217:1: rule__Variable_Subscript__IndexAssignment_1_2 : ( ruleExpression ) ;
    public final void rule__Variable_Subscript__IndexAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9221:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9222:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9222:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9223:3: ruleExpression
            {
             before(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_2_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__IndexAssignment_1_2"


    // $ANTLR start "rule__Variable_Subscript__IndexAssignment_1_3_1"
    // InternalStructuredTextParser.g:9232:1: rule__Variable_Subscript__IndexAssignment_1_3_1 : ( ruleExpression ) ;
    public final void rule__Variable_Subscript__IndexAssignment_1_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9236:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9237:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9237:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9238:3: ruleExpression
            {
             before(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_3_1_0()); 
            pushFollow(FOLLOW_2);
            ruleExpression();

            state._fsp--;

             after(grammarAccess.getVariable_SubscriptAccess().getIndexExpressionParserRuleCall_1_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Subscript__IndexAssignment_1_3_1"


    // $ANTLR start "rule__Variable_Adapter__VarAssignment_1_2"
    // InternalStructuredTextParser.g:9247:1: rule__Variable_Adapter__VarAssignment_1_2 : ( ( ruleVariable_Name ) ) ;
    public final void rule__Variable_Adapter__VarAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9251:1: ( ( ( ruleVariable_Name ) ) )
            // InternalStructuredTextParser.g:9252:2: ( ( ruleVariable_Name ) )
            {
            // InternalStructuredTextParser.g:9252:2: ( ( ruleVariable_Name ) )
            // InternalStructuredTextParser.g:9253:3: ( ruleVariable_Name )
            {
             before(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationCrossReference_1_2_0()); 
            // InternalStructuredTextParser.g:9254:3: ( ruleVariable_Name )
            // InternalStructuredTextParser.g:9255:4: ruleVariable_Name
            {
             before(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationVariable_NameParserRuleCall_1_2_0_1()); 
            pushFollow(FOLLOW_2);
            ruleVariable_Name();

            state._fsp--;

             after(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationVariable_NameParserRuleCall_1_2_0_1()); 

            }

             after(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationCrossReference_1_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__VarAssignment_1_2"


    // $ANTLR start "rule__AdapterRoot__AdapterAssignment_1"
    // InternalStructuredTextParser.g:9266:1: rule__AdapterRoot__AdapterAssignment_1 : ( ( ruleAdapter_Name ) ) ;
    public final void rule__AdapterRoot__AdapterAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9270:1: ( ( ( ruleAdapter_Name ) ) )
            // InternalStructuredTextParser.g:9271:2: ( ( ruleAdapter_Name ) )
            {
            // InternalStructuredTextParser.g:9271:2: ( ( ruleAdapter_Name ) )
            // InternalStructuredTextParser.g:9272:3: ( ruleAdapter_Name )
            {
             before(grammarAccess.getAdapterRootAccess().getAdapterVarDeclarationCrossReference_1_0()); 
            // InternalStructuredTextParser.g:9273:3: ( ruleAdapter_Name )
            // InternalStructuredTextParser.g:9274:4: ruleAdapter_Name
            {
             before(grammarAccess.getAdapterRootAccess().getAdapterVarDeclarationAdapter_NameParserRuleCall_1_0_1()); 
            pushFollow(FOLLOW_2);
            ruleAdapter_Name();

            state._fsp--;

             after(grammarAccess.getAdapterRootAccess().getAdapterVarDeclarationAdapter_NameParserRuleCall_1_0_1()); 

            }

             after(grammarAccess.getAdapterRootAccess().getAdapterVarDeclarationCrossReference_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__AdapterRoot__AdapterAssignment_1"


    // $ANTLR start "rule__Multibit_Part_Access__DwordaccessAssignment_0_0"
    // InternalStructuredTextParser.g:9285:1: rule__Multibit_Part_Access__DwordaccessAssignment_0_0 : ( ( D ) ) ;
    public final void rule__Multibit_Part_Access__DwordaccessAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9289:1: ( ( ( D ) ) )
            // InternalStructuredTextParser.g:9290:2: ( ( D ) )
            {
            // InternalStructuredTextParser.g:9290:2: ( ( D ) )
            // InternalStructuredTextParser.g:9291:3: ( D )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0()); 
            // InternalStructuredTextParser.g:9292:3: ( D )
            // InternalStructuredTextParser.g:9293:4: D
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0()); 
            match(input,D,FOLLOW_2); 
             after(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0()); 

            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__DwordaccessAssignment_0_0"


    // $ANTLR start "rule__Multibit_Part_Access__IndexAssignment_0_1"
    // InternalStructuredTextParser.g:9304:1: rule__Multibit_Part_Access__IndexAssignment_0_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9308:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9309:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9309:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9310:3: rulePartial_Value
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_0_1_0()); 
            pushFollow(FOLLOW_2);
            rulePartial_Value();

            state._fsp--;

             after(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__IndexAssignment_0_1"


    // $ANTLR start "rule__Multibit_Part_Access__WordaccessAssignment_1_0"
    // InternalStructuredTextParser.g:9319:1: rule__Multibit_Part_Access__WordaccessAssignment_1_0 : ( ( W ) ) ;
    public final void rule__Multibit_Part_Access__WordaccessAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9323:1: ( ( ( W ) ) )
            // InternalStructuredTextParser.g:9324:2: ( ( W ) )
            {
            // InternalStructuredTextParser.g:9324:2: ( ( W ) )
            // InternalStructuredTextParser.g:9325:3: ( W )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0()); 
            // InternalStructuredTextParser.g:9326:3: ( W )
            // InternalStructuredTextParser.g:9327:4: W
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0()); 
            match(input,W,FOLLOW_2); 
             after(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0()); 

            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__WordaccessAssignment_1_0"


    // $ANTLR start "rule__Multibit_Part_Access__IndexAssignment_1_1"
    // InternalStructuredTextParser.g:9338:1: rule__Multibit_Part_Access__IndexAssignment_1_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9342:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9343:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9343:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9344:3: rulePartial_Value
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_1_1_0()); 
            pushFollow(FOLLOW_2);
            rulePartial_Value();

            state._fsp--;

             after(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__IndexAssignment_1_1"


    // $ANTLR start "rule__Multibit_Part_Access__ByteaccessAssignment_2_0"
    // InternalStructuredTextParser.g:9353:1: rule__Multibit_Part_Access__ByteaccessAssignment_2_0 : ( ( B ) ) ;
    public final void rule__Multibit_Part_Access__ByteaccessAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9357:1: ( ( ( B ) ) )
            // InternalStructuredTextParser.g:9358:2: ( ( B ) )
            {
            // InternalStructuredTextParser.g:9358:2: ( ( B ) )
            // InternalStructuredTextParser.g:9359:3: ( B )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0()); 
            // InternalStructuredTextParser.g:9360:3: ( B )
            // InternalStructuredTextParser.g:9361:4: B
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0()); 
            match(input,B,FOLLOW_2); 
             after(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0()); 

            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__ByteaccessAssignment_2_0"


    // $ANTLR start "rule__Multibit_Part_Access__IndexAssignment_2_1"
    // InternalStructuredTextParser.g:9372:1: rule__Multibit_Part_Access__IndexAssignment_2_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9376:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9377:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9377:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9378:3: rulePartial_Value
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_2_1_0()); 
            pushFollow(FOLLOW_2);
            rulePartial_Value();

            state._fsp--;

             after(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_2_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__IndexAssignment_2_1"


    // $ANTLR start "rule__Multibit_Part_Access__BitaccessAssignment_3_0"
    // InternalStructuredTextParser.g:9387:1: rule__Multibit_Part_Access__BitaccessAssignment_3_0 : ( ( X ) ) ;
    public final void rule__Multibit_Part_Access__BitaccessAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9391:1: ( ( ( X ) ) )
            // InternalStructuredTextParser.g:9392:2: ( ( X ) )
            {
            // InternalStructuredTextParser.g:9392:2: ( ( X ) )
            // InternalStructuredTextParser.g:9393:3: ( X )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0()); 
            // InternalStructuredTextParser.g:9394:3: ( X )
            // InternalStructuredTextParser.g:9395:4: X
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0()); 
            match(input,X,FOLLOW_2); 
             after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0()); 

            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__BitaccessAssignment_3_0"


    // $ANTLR start "rule__Multibit_Part_Access__IndexAssignment_3_1"
    // InternalStructuredTextParser.g:9406:1: rule__Multibit_Part_Access__IndexAssignment_3_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9410:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9411:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9411:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9412:3: rulePartial_Value
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_3_1_0()); 
            pushFollow(FOLLOW_2);
            rulePartial_Value();

            state._fsp--;

             after(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_3_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__IndexAssignment_3_1"


    // $ANTLR start "rule__Multibit_Part_Access__BitaccessAssignment_4_0"
    // InternalStructuredTextParser.g:9421:1: rule__Multibit_Part_Access__BitaccessAssignment_4_0 : ( ( FullStop ) ) ;
    public final void rule__Multibit_Part_Access__BitaccessAssignment_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9425:1: ( ( ( FullStop ) ) )
            // InternalStructuredTextParser.g:9426:2: ( ( FullStop ) )
            {
            // InternalStructuredTextParser.g:9426:2: ( ( FullStop ) )
            // InternalStructuredTextParser.g:9427:3: ( FullStop )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0()); 
            // InternalStructuredTextParser.g:9428:3: ( FullStop )
            // InternalStructuredTextParser.g:9429:4: FullStop
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0()); 
            match(input,FullStop,FOLLOW_2); 
             after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0()); 

            }

             after(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__BitaccessAssignment_4_0"


    // $ANTLR start "rule__Multibit_Part_Access__IndexAssignment_4_1"
    // InternalStructuredTextParser.g:9440:1: rule__Multibit_Part_Access__IndexAssignment_4_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9444:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9445:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9445:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9446:3: rulePartial_Value
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_4_1_0()); 
            pushFollow(FOLLOW_2);
            rulePartial_Value();

            state._fsp--;

             after(grammarAccess.getMultibit_Part_AccessAccess().getIndexPartial_ValueParserRuleCall_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Multibit_Part_Access__IndexAssignment_4_1"


    // $ANTLR start "rule__Variable_Primary__VarAssignment"
    // InternalStructuredTextParser.g:9455:1: rule__Variable_Primary__VarAssignment : ( ( ruleVariable_Name ) ) ;
    public final void rule__Variable_Primary__VarAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9459:1: ( ( ( ruleVariable_Name ) ) )
            // InternalStructuredTextParser.g:9460:2: ( ( ruleVariable_Name ) )
            {
            // InternalStructuredTextParser.g:9460:2: ( ( ruleVariable_Name ) )
            // InternalStructuredTextParser.g:9461:3: ( ruleVariable_Name )
            {
             before(grammarAccess.getVariable_PrimaryAccess().getVarVarDeclarationCrossReference_0()); 
            // InternalStructuredTextParser.g:9462:3: ( ruleVariable_Name )
            // InternalStructuredTextParser.g:9463:4: ruleVariable_Name
            {
             before(grammarAccess.getVariable_PrimaryAccess().getVarVarDeclarationVariable_NameParserRuleCall_0_1()); 
            pushFollow(FOLLOW_2);
            ruleVariable_Name();

            state._fsp--;

             after(grammarAccess.getVariable_PrimaryAccess().getVarVarDeclarationVariable_NameParserRuleCall_0_1()); 

            }

             after(grammarAccess.getVariable_PrimaryAccess().getVarVarDeclarationCrossReference_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Primary__VarAssignment"


    // $ANTLR start "rule__Int_Literal__TypeAssignment_0_0"
    // InternalStructuredTextParser.g:9474:1: rule__Int_Literal__TypeAssignment_0_0 : ( ruleInt_Type_Name ) ;
    public final void rule__Int_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9478:1: ( ( ruleInt_Type_Name ) )
            // InternalStructuredTextParser.g:9479:2: ( ruleInt_Type_Name )
            {
            // InternalStructuredTextParser.g:9479:2: ( ruleInt_Type_Name )
            // InternalStructuredTextParser.g:9480:3: ruleInt_Type_Name
            {
             before(grammarAccess.getInt_LiteralAccess().getTypeInt_Type_NameEnumRuleCall_0_0_0()); 
            pushFollow(FOLLOW_2);
            ruleInt_Type_Name();

            state._fsp--;

             after(grammarAccess.getInt_LiteralAccess().getTypeInt_Type_NameEnumRuleCall_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__TypeAssignment_0_0"


    // $ANTLR start "rule__Int_Literal__ValueAssignment_1"
    // InternalStructuredTextParser.g:9489:1: rule__Int_Literal__ValueAssignment_1 : ( ( rule__Int_Literal__ValueAlternatives_1_0 ) ) ;
    public final void rule__Int_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9493:1: ( ( ( rule__Int_Literal__ValueAlternatives_1_0 ) ) )
            // InternalStructuredTextParser.g:9494:2: ( ( rule__Int_Literal__ValueAlternatives_1_0 ) )
            {
            // InternalStructuredTextParser.g:9494:2: ( ( rule__Int_Literal__ValueAlternatives_1_0 ) )
            // InternalStructuredTextParser.g:9495:3: ( rule__Int_Literal__ValueAlternatives_1_0 )
            {
             before(grammarAccess.getInt_LiteralAccess().getValueAlternatives_1_0()); 
            // InternalStructuredTextParser.g:9496:3: ( rule__Int_Literal__ValueAlternatives_1_0 )
            // InternalStructuredTextParser.g:9496:4: rule__Int_Literal__ValueAlternatives_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Int_Literal__ValueAlternatives_1_0();

            state._fsp--;


            }

             after(grammarAccess.getInt_LiteralAccess().getValueAlternatives_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Int_Literal__ValueAssignment_1"


    // $ANTLR start "rule__Real_Literal__TypeAssignment_0_0"
    // InternalStructuredTextParser.g:9504:1: rule__Real_Literal__TypeAssignment_0_0 : ( ruleReal_Type_Name ) ;
    public final void rule__Real_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9508:1: ( ( ruleReal_Type_Name ) )
            // InternalStructuredTextParser.g:9509:2: ( ruleReal_Type_Name )
            {
            // InternalStructuredTextParser.g:9509:2: ( ruleReal_Type_Name )
            // InternalStructuredTextParser.g:9510:3: ruleReal_Type_Name
            {
             before(grammarAccess.getReal_LiteralAccess().getTypeReal_Type_NameEnumRuleCall_0_0_0()); 
            pushFollow(FOLLOW_2);
            ruleReal_Type_Name();

            state._fsp--;

             after(grammarAccess.getReal_LiteralAccess().getTypeReal_Type_NameEnumRuleCall_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Literal__TypeAssignment_0_0"


    // $ANTLR start "rule__Real_Literal__ValueAssignment_1"
    // InternalStructuredTextParser.g:9519:1: rule__Real_Literal__ValueAssignment_1 : ( ruleReal_Value ) ;
    public final void rule__Real_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9523:1: ( ( ruleReal_Value ) )
            // InternalStructuredTextParser.g:9524:2: ( ruleReal_Value )
            {
            // InternalStructuredTextParser.g:9524:2: ( ruleReal_Value )
            // InternalStructuredTextParser.g:9525:3: ruleReal_Value
            {
             before(grammarAccess.getReal_LiteralAccess().getValueReal_ValueParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleReal_Value();

            state._fsp--;

             after(grammarAccess.getReal_LiteralAccess().getValueReal_ValueParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Real_Literal__ValueAssignment_1"


    // $ANTLR start "rule__Bool_Literal__TypeAssignment_0_0"
    // InternalStructuredTextParser.g:9534:1: rule__Bool_Literal__TypeAssignment_0_0 : ( ruleBool_Type_Name ) ;
    public final void rule__Bool_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9538:1: ( ( ruleBool_Type_Name ) )
            // InternalStructuredTextParser.g:9539:2: ( ruleBool_Type_Name )
            {
            // InternalStructuredTextParser.g:9539:2: ( ruleBool_Type_Name )
            // InternalStructuredTextParser.g:9540:3: ruleBool_Type_Name
            {
             before(grammarAccess.getBool_LiteralAccess().getTypeBool_Type_NameEnumRuleCall_0_0_0()); 
            pushFollow(FOLLOW_2);
            ruleBool_Type_Name();

            state._fsp--;

             after(grammarAccess.getBool_LiteralAccess().getTypeBool_Type_NameEnumRuleCall_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Literal__TypeAssignment_0_0"


    // $ANTLR start "rule__Bool_Literal__ValueAssignment_1"
    // InternalStructuredTextParser.g:9549:1: rule__Bool_Literal__ValueAssignment_1 : ( ruleBool_Value ) ;
    public final void rule__Bool_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9553:1: ( ( ruleBool_Value ) )
            // InternalStructuredTextParser.g:9554:2: ( ruleBool_Value )
            {
            // InternalStructuredTextParser.g:9554:2: ( ruleBool_Value )
            // InternalStructuredTextParser.g:9555:3: ruleBool_Value
            {
             before(grammarAccess.getBool_LiteralAccess().getValueBool_ValueParserRuleCall_1_0()); 
            pushFollow(FOLLOW_2);
            ruleBool_Value();

            state._fsp--;

             after(grammarAccess.getBool_LiteralAccess().getValueBool_ValueParserRuleCall_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Bool_Literal__ValueAssignment_1"


    // $ANTLR start "rule__Char_Literal__TypeAssignment_0_0"
    // InternalStructuredTextParser.g:9564:1: rule__Char_Literal__TypeAssignment_0_0 : ( ruleString_Type_Name ) ;
    public final void rule__Char_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9568:1: ( ( ruleString_Type_Name ) )
            // InternalStructuredTextParser.g:9569:2: ( ruleString_Type_Name )
            {
            // InternalStructuredTextParser.g:9569:2: ( ruleString_Type_Name )
            // InternalStructuredTextParser.g:9570:3: ruleString_Type_Name
            {
             before(grammarAccess.getChar_LiteralAccess().getTypeString_Type_NameEnumRuleCall_0_0_0()); 
            pushFollow(FOLLOW_2);
            ruleString_Type_Name();

            state._fsp--;

             after(grammarAccess.getChar_LiteralAccess().getTypeString_Type_NameEnumRuleCall_0_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__TypeAssignment_0_0"


    // $ANTLR start "rule__Char_Literal__LengthAssignment_0_1"
    // InternalStructuredTextParser.g:9579:1: rule__Char_Literal__LengthAssignment_0_1 : ( RULE_UNSIGNED_INT ) ;
    public final void rule__Char_Literal__LengthAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9583:1: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:9584:2: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:9584:2: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:9585:3: RULE_UNSIGNED_INT
            {
             before(grammarAccess.getChar_LiteralAccess().getLengthUNSIGNED_INTTerminalRuleCall_0_1_0()); 
            match(input,RULE_UNSIGNED_INT,FOLLOW_2); 
             after(grammarAccess.getChar_LiteralAccess().getLengthUNSIGNED_INTTerminalRuleCall_0_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__LengthAssignment_0_1"


    // $ANTLR start "rule__Char_Literal__ValueAssignment_1"
    // InternalStructuredTextParser.g:9594:1: rule__Char_Literal__ValueAssignment_1 : ( ( rule__Char_Literal__ValueAlternatives_1_0 ) ) ;
    public final void rule__Char_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9598:1: ( ( ( rule__Char_Literal__ValueAlternatives_1_0 ) ) )
            // InternalStructuredTextParser.g:9599:2: ( ( rule__Char_Literal__ValueAlternatives_1_0 ) )
            {
            // InternalStructuredTextParser.g:9599:2: ( ( rule__Char_Literal__ValueAlternatives_1_0 ) )
            // InternalStructuredTextParser.g:9600:3: ( rule__Char_Literal__ValueAlternatives_1_0 )
            {
             before(grammarAccess.getChar_LiteralAccess().getValueAlternatives_1_0()); 
            // InternalStructuredTextParser.g:9601:3: ( rule__Char_Literal__ValueAlternatives_1_0 )
            // InternalStructuredTextParser.g:9601:4: rule__Char_Literal__ValueAlternatives_1_0
            {
            pushFollow(FOLLOW_2);
            rule__Char_Literal__ValueAlternatives_1_0();

            state._fsp--;


            }

             after(grammarAccess.getChar_LiteralAccess().getValueAlternatives_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Char_Literal__ValueAssignment_1"


    // $ANTLR start "rule__Time_Literal__LiteralAssignment"
    // InternalStructuredTextParser.g:9609:1: rule__Time_Literal__LiteralAssignment : ( ( rule__Time_Literal__LiteralAlternatives_0 ) ) ;
    public final void rule__Time_Literal__LiteralAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9613:1: ( ( ( rule__Time_Literal__LiteralAlternatives_0 ) ) )
            // InternalStructuredTextParser.g:9614:2: ( ( rule__Time_Literal__LiteralAlternatives_0 ) )
            {
            // InternalStructuredTextParser.g:9614:2: ( ( rule__Time_Literal__LiteralAlternatives_0 ) )
            // InternalStructuredTextParser.g:9615:3: ( rule__Time_Literal__LiteralAlternatives_0 )
            {
             before(grammarAccess.getTime_LiteralAccess().getLiteralAlternatives_0()); 
            // InternalStructuredTextParser.g:9616:3: ( rule__Time_Literal__LiteralAlternatives_0 )
            // InternalStructuredTextParser.g:9616:4: rule__Time_Literal__LiteralAlternatives_0
            {
            pushFollow(FOLLOW_2);
            rule__Time_Literal__LiteralAlternatives_0();

            state._fsp--;


            }

             after(grammarAccess.getTime_LiteralAccess().getLiteralAlternatives_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Time_Literal__LiteralAssignment"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x4201042410060800L,0x0000200108001C00L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000004400L,0x0000200000000000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000000402L,0x0000200000000000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000400L,0x0000200000000000L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000080L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x240D79DB6F3880F0L,0x0000200000000000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000000000L,0x0000200100001400L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0406694B62888000L,0x0015DF0000A00000L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0201042410060802L,0x0000200108001C00L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0201042410060800L,0x0000200108001C00L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x1407694B62888000L,0x0015FF0100A41400L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0001000010040000L,0x0000200000000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000020000410000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000020000001000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0406694B62888002L,0x0015DF0000A00000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000000000000000L,0x0000000004400000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0201042410060800L,0x0000200100001C00L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000300L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0100000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0100000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000010L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000010L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0000000000000000L,0x0000000050000048L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0000000000000002L,0x0000000050000048L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A00000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000002L,0x0000000000A00000L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0800000000000000L,0x0000000002100000L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0800000000000002L,0x0000000002100000L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x1000000000000000L,0x0000000000A00000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0001000000000000L,0x0000200100041400L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x1407694B62888000L,0x0015FF0100AC1400L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x00F0000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000400400000L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0404490160000000L,0x0001C10000A00000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000010000A00000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0404690162000000L,0x0001C10000A00000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000004200088000L,0x0014000000000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000000L,0x0000010000010000L});

}