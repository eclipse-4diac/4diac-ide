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


    // $ANTLR start "entryRuleMultibit_Part_Access"
    // InternalStructuredTextParser.g:1025:1: entryRuleMultibit_Part_Access : ruleMultibit_Part_Access EOF ;
    public final void entryRuleMultibit_Part_Access() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1026:1: ( ruleMultibit_Part_Access EOF )
            // InternalStructuredTextParser.g:1027:1: ruleMultibit_Part_Access EOF
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
    // InternalStructuredTextParser.g:1034:1: ruleMultibit_Part_Access : ( ( rule__Multibit_Part_Access__Alternatives ) ) ;
    public final void ruleMultibit_Part_Access() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1038:2: ( ( ( rule__Multibit_Part_Access__Alternatives ) ) )
            // InternalStructuredTextParser.g:1039:2: ( ( rule__Multibit_Part_Access__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1039:2: ( ( rule__Multibit_Part_Access__Alternatives ) )
            // InternalStructuredTextParser.g:1040:3: ( rule__Multibit_Part_Access__Alternatives )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1041:3: ( rule__Multibit_Part_Access__Alternatives )
            // InternalStructuredTextParser.g:1041:4: rule__Multibit_Part_Access__Alternatives
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
    // InternalStructuredTextParser.g:1050:1: entryRuleAdapter_Name : ruleAdapter_Name EOF ;
    public final void entryRuleAdapter_Name() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1051:1: ( ruleAdapter_Name EOF )
            // InternalStructuredTextParser.g:1052:1: ruleAdapter_Name EOF
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
    // InternalStructuredTextParser.g:1059:1: ruleAdapter_Name : ( ( rule__Adapter_Name__Alternatives ) ) ;
    public final void ruleAdapter_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1063:2: ( ( ( rule__Adapter_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1064:2: ( ( rule__Adapter_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1064:2: ( ( rule__Adapter_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1065:3: ( rule__Adapter_Name__Alternatives )
            {
             before(grammarAccess.getAdapter_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1066:3: ( rule__Adapter_Name__Alternatives )
            // InternalStructuredTextParser.g:1066:4: rule__Adapter_Name__Alternatives
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
    // InternalStructuredTextParser.g:1075:1: entryRuleVariable_Primary : ruleVariable_Primary EOF ;
    public final void entryRuleVariable_Primary() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1076:1: ( ruleVariable_Primary EOF )
            // InternalStructuredTextParser.g:1077:1: ruleVariable_Primary EOF
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
    // InternalStructuredTextParser.g:1084:1: ruleVariable_Primary : ( ( rule__Variable_Primary__VarAssignment ) ) ;
    public final void ruleVariable_Primary() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1088:2: ( ( ( rule__Variable_Primary__VarAssignment ) ) )
            // InternalStructuredTextParser.g:1089:2: ( ( rule__Variable_Primary__VarAssignment ) )
            {
            // InternalStructuredTextParser.g:1089:2: ( ( rule__Variable_Primary__VarAssignment ) )
            // InternalStructuredTextParser.g:1090:3: ( rule__Variable_Primary__VarAssignment )
            {
             before(grammarAccess.getVariable_PrimaryAccess().getVarAssignment()); 
            // InternalStructuredTextParser.g:1091:3: ( rule__Variable_Primary__VarAssignment )
            // InternalStructuredTextParser.g:1091:4: rule__Variable_Primary__VarAssignment
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
    // InternalStructuredTextParser.g:1100:1: entryRuleVariable_Name : ruleVariable_Name EOF ;
    public final void entryRuleVariable_Name() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1101:1: ( ruleVariable_Name EOF )
            // InternalStructuredTextParser.g:1102:1: ruleVariable_Name EOF
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
    // InternalStructuredTextParser.g:1109:1: ruleVariable_Name : ( ( rule__Variable_Name__Alternatives ) ) ;
    public final void ruleVariable_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1113:2: ( ( ( rule__Variable_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1114:2: ( ( rule__Variable_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1114:2: ( ( rule__Variable_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1115:3: ( rule__Variable_Name__Alternatives )
            {
             before(grammarAccess.getVariable_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1116:3: ( rule__Variable_Name__Alternatives )
            // InternalStructuredTextParser.g:1116:4: rule__Variable_Name__Alternatives
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
    // InternalStructuredTextParser.g:1125:1: entryRuleConstant : ruleConstant EOF ;
    public final void entryRuleConstant() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1126:1: ( ruleConstant EOF )
            // InternalStructuredTextParser.g:1127:1: ruleConstant EOF
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
    // InternalStructuredTextParser.g:1134:1: ruleConstant : ( ( rule__Constant__Alternatives ) ) ;
    public final void ruleConstant() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1138:2: ( ( ( rule__Constant__Alternatives ) ) )
            // InternalStructuredTextParser.g:1139:2: ( ( rule__Constant__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1139:2: ( ( rule__Constant__Alternatives ) )
            // InternalStructuredTextParser.g:1140:3: ( rule__Constant__Alternatives )
            {
             before(grammarAccess.getConstantAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1141:3: ( rule__Constant__Alternatives )
            // InternalStructuredTextParser.g:1141:4: rule__Constant__Alternatives
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
    // InternalStructuredTextParser.g:1150:1: entryRuleNumeric_Literal : ruleNumeric_Literal EOF ;
    public final void entryRuleNumeric_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1151:1: ( ruleNumeric_Literal EOF )
            // InternalStructuredTextParser.g:1152:1: ruleNumeric_Literal EOF
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
    // InternalStructuredTextParser.g:1159:1: ruleNumeric_Literal : ( ( rule__Numeric_Literal__Alternatives ) ) ;
    public final void ruleNumeric_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1163:2: ( ( ( rule__Numeric_Literal__Alternatives ) ) )
            // InternalStructuredTextParser.g:1164:2: ( ( rule__Numeric_Literal__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1164:2: ( ( rule__Numeric_Literal__Alternatives ) )
            // InternalStructuredTextParser.g:1165:3: ( rule__Numeric_Literal__Alternatives )
            {
             before(grammarAccess.getNumeric_LiteralAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1166:3: ( rule__Numeric_Literal__Alternatives )
            // InternalStructuredTextParser.g:1166:4: rule__Numeric_Literal__Alternatives
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
    // InternalStructuredTextParser.g:1175:1: entryRuleInt_Literal : ruleInt_Literal EOF ;
    public final void entryRuleInt_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1176:1: ( ruleInt_Literal EOF )
            // InternalStructuredTextParser.g:1177:1: ruleInt_Literal EOF
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
    // InternalStructuredTextParser.g:1184:1: ruleInt_Literal : ( ( rule__Int_Literal__Group__0 ) ) ;
    public final void ruleInt_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1188:2: ( ( ( rule__Int_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1189:2: ( ( rule__Int_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1189:2: ( ( rule__Int_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1190:3: ( rule__Int_Literal__Group__0 )
            {
             before(grammarAccess.getInt_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1191:3: ( rule__Int_Literal__Group__0 )
            // InternalStructuredTextParser.g:1191:4: rule__Int_Literal__Group__0
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
    // InternalStructuredTextParser.g:1200:1: entryRuleSigned_Int : ruleSigned_Int EOF ;
    public final void entryRuleSigned_Int() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1201:1: ( ruleSigned_Int EOF )
            // InternalStructuredTextParser.g:1202:1: ruleSigned_Int EOF
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
    // InternalStructuredTextParser.g:1209:1: ruleSigned_Int : ( ( rule__Signed_Int__Group__0 ) ) ;
    public final void ruleSigned_Int() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1213:2: ( ( ( rule__Signed_Int__Group__0 ) ) )
            // InternalStructuredTextParser.g:1214:2: ( ( rule__Signed_Int__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1214:2: ( ( rule__Signed_Int__Group__0 ) )
            // InternalStructuredTextParser.g:1215:3: ( rule__Signed_Int__Group__0 )
            {
             before(grammarAccess.getSigned_IntAccess().getGroup()); 
            // InternalStructuredTextParser.g:1216:3: ( rule__Signed_Int__Group__0 )
            // InternalStructuredTextParser.g:1216:4: rule__Signed_Int__Group__0
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
    // InternalStructuredTextParser.g:1225:1: entryRulePartial_Value : rulePartial_Value EOF ;
    public final void entryRulePartial_Value() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1226:1: ( rulePartial_Value EOF )
            // InternalStructuredTextParser.g:1227:1: rulePartial_Value EOF
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
    // InternalStructuredTextParser.g:1234:1: rulePartial_Value : ( RULE_UNSIGNED_INT ) ;
    public final void rulePartial_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1238:2: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:1239:2: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:1239:2: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:1240:3: RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:1250:1: entryRuleArray_Size : ruleArray_Size EOF ;
    public final void entryRuleArray_Size() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1251:1: ( ruleArray_Size EOF )
            // InternalStructuredTextParser.g:1252:1: ruleArray_Size EOF
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
    // InternalStructuredTextParser.g:1259:1: ruleArray_Size : ( RULE_UNSIGNED_INT ) ;
    public final void ruleArray_Size() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1263:2: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:1264:2: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:1264:2: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:1265:3: RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:1275:1: entryRuleReal_Literal : ruleReal_Literal EOF ;
    public final void entryRuleReal_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1276:1: ( ruleReal_Literal EOF )
            // InternalStructuredTextParser.g:1277:1: ruleReal_Literal EOF
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
    // InternalStructuredTextParser.g:1284:1: ruleReal_Literal : ( ( rule__Real_Literal__Group__0 ) ) ;
    public final void ruleReal_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1288:2: ( ( ( rule__Real_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1289:2: ( ( rule__Real_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1289:2: ( ( rule__Real_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1290:3: ( rule__Real_Literal__Group__0 )
            {
             before(grammarAccess.getReal_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1291:3: ( rule__Real_Literal__Group__0 )
            // InternalStructuredTextParser.g:1291:4: rule__Real_Literal__Group__0
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
    // InternalStructuredTextParser.g:1300:1: entryRuleReal_Value : ruleReal_Value EOF ;
    public final void entryRuleReal_Value() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1301:1: ( ruleReal_Value EOF )
            // InternalStructuredTextParser.g:1302:1: ruleReal_Value EOF
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
    // InternalStructuredTextParser.g:1309:1: ruleReal_Value : ( ( rule__Real_Value__Group__0 ) ) ;
    public final void ruleReal_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1313:2: ( ( ( rule__Real_Value__Group__0 ) ) )
            // InternalStructuredTextParser.g:1314:2: ( ( rule__Real_Value__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1314:2: ( ( rule__Real_Value__Group__0 ) )
            // InternalStructuredTextParser.g:1315:3: ( rule__Real_Value__Group__0 )
            {
             before(grammarAccess.getReal_ValueAccess().getGroup()); 
            // InternalStructuredTextParser.g:1316:3: ( rule__Real_Value__Group__0 )
            // InternalStructuredTextParser.g:1316:4: rule__Real_Value__Group__0
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
    // InternalStructuredTextParser.g:1325:1: entryRuleBool_Literal : ruleBool_Literal EOF ;
    public final void entryRuleBool_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1326:1: ( ruleBool_Literal EOF )
            // InternalStructuredTextParser.g:1327:1: ruleBool_Literal EOF
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
    // InternalStructuredTextParser.g:1334:1: ruleBool_Literal : ( ( rule__Bool_Literal__Group__0 ) ) ;
    public final void ruleBool_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1338:2: ( ( ( rule__Bool_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1339:2: ( ( rule__Bool_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1339:2: ( ( rule__Bool_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1340:3: ( rule__Bool_Literal__Group__0 )
            {
             before(grammarAccess.getBool_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1341:3: ( rule__Bool_Literal__Group__0 )
            // InternalStructuredTextParser.g:1341:4: rule__Bool_Literal__Group__0
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
    // InternalStructuredTextParser.g:1350:1: entryRuleBool_Value : ruleBool_Value EOF ;
    public final void entryRuleBool_Value() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1351:1: ( ruleBool_Value EOF )
            // InternalStructuredTextParser.g:1352:1: ruleBool_Value EOF
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
    // InternalStructuredTextParser.g:1359:1: ruleBool_Value : ( ( rule__Bool_Value__Alternatives ) ) ;
    public final void ruleBool_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1363:2: ( ( ( rule__Bool_Value__Alternatives ) ) )
            // InternalStructuredTextParser.g:1364:2: ( ( rule__Bool_Value__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1364:2: ( ( rule__Bool_Value__Alternatives ) )
            // InternalStructuredTextParser.g:1365:3: ( rule__Bool_Value__Alternatives )
            {
             before(grammarAccess.getBool_ValueAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1366:3: ( rule__Bool_Value__Alternatives )
            // InternalStructuredTextParser.g:1366:4: rule__Bool_Value__Alternatives
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
    // InternalStructuredTextParser.g:1375:1: entryRuleChar_Literal : ruleChar_Literal EOF ;
    public final void entryRuleChar_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1376:1: ( ruleChar_Literal EOF )
            // InternalStructuredTextParser.g:1377:1: ruleChar_Literal EOF
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
    // InternalStructuredTextParser.g:1384:1: ruleChar_Literal : ( ( rule__Char_Literal__Group__0 ) ) ;
    public final void ruleChar_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1388:2: ( ( ( rule__Char_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1389:2: ( ( rule__Char_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1389:2: ( ( rule__Char_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1390:3: ( rule__Char_Literal__Group__0 )
            {
             before(grammarAccess.getChar_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1391:3: ( rule__Char_Literal__Group__0 )
            // InternalStructuredTextParser.g:1391:4: rule__Char_Literal__Group__0
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
    // InternalStructuredTextParser.g:1400:1: entryRuleTime_Literal : ruleTime_Literal EOF ;
    public final void entryRuleTime_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1401:1: ( ruleTime_Literal EOF )
            // InternalStructuredTextParser.g:1402:1: ruleTime_Literal EOF
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
    // InternalStructuredTextParser.g:1409:1: ruleTime_Literal : ( ( rule__Time_Literal__LiteralAssignment ) ) ;
    public final void ruleTime_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1413:2: ( ( ( rule__Time_Literal__LiteralAssignment ) ) )
            // InternalStructuredTextParser.g:1414:2: ( ( rule__Time_Literal__LiteralAssignment ) )
            {
            // InternalStructuredTextParser.g:1414:2: ( ( rule__Time_Literal__LiteralAssignment ) )
            // InternalStructuredTextParser.g:1415:3: ( rule__Time_Literal__LiteralAssignment )
            {
             before(grammarAccess.getTime_LiteralAccess().getLiteralAssignment()); 
            // InternalStructuredTextParser.g:1416:3: ( rule__Time_Literal__LiteralAssignment )
            // InternalStructuredTextParser.g:1416:4: rule__Time_Literal__LiteralAssignment
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
    // InternalStructuredTextParser.g:1425:1: entryRuleType_Name : ruleType_Name EOF ;
    public final void entryRuleType_Name() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1426:1: ( ruleType_Name EOF )
            // InternalStructuredTextParser.g:1427:1: ruleType_Name EOF
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
    // InternalStructuredTextParser.g:1434:1: ruleType_Name : ( ( rule__Type_Name__Alternatives ) ) ;
    public final void ruleType_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1438:2: ( ( ( rule__Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1439:2: ( ( rule__Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1439:2: ( ( rule__Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1440:3: ( rule__Type_Name__Alternatives )
            {
             before(grammarAccess.getType_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1441:3: ( rule__Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1441:4: rule__Type_Name__Alternatives
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
    // InternalStructuredTextParser.g:1450:1: ruleOr_Operator : ( ( OR ) ) ;
    public final void ruleOr_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1454:1: ( ( ( OR ) ) )
            // InternalStructuredTextParser.g:1455:2: ( ( OR ) )
            {
            // InternalStructuredTextParser.g:1455:2: ( ( OR ) )
            // InternalStructuredTextParser.g:1456:3: ( OR )
            {
             before(grammarAccess.getOr_OperatorAccess().getOREnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1457:3: ( OR )
            // InternalStructuredTextParser.g:1457:4: OR
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
    // InternalStructuredTextParser.g:1466:1: ruleXor_Operator : ( ( XOR ) ) ;
    public final void ruleXor_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1470:1: ( ( ( XOR ) ) )
            // InternalStructuredTextParser.g:1471:2: ( ( XOR ) )
            {
            // InternalStructuredTextParser.g:1471:2: ( ( XOR ) )
            // InternalStructuredTextParser.g:1472:3: ( XOR )
            {
             before(grammarAccess.getXor_OperatorAccess().getXOREnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1473:3: ( XOR )
            // InternalStructuredTextParser.g:1473:4: XOR
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
    // InternalStructuredTextParser.g:1482:1: ruleAnd_Operator : ( ( rule__And_Operator__Alternatives ) ) ;
    public final void ruleAnd_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1486:1: ( ( ( rule__And_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1487:2: ( ( rule__And_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1487:2: ( ( rule__And_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1488:3: ( rule__And_Operator__Alternatives )
            {
             before(grammarAccess.getAnd_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1489:3: ( rule__And_Operator__Alternatives )
            // InternalStructuredTextParser.g:1489:4: rule__And_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1498:1: ruleCompare_Operator : ( ( rule__Compare_Operator__Alternatives ) ) ;
    public final void ruleCompare_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1502:1: ( ( ( rule__Compare_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1503:2: ( ( rule__Compare_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1503:2: ( ( rule__Compare_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1504:3: ( rule__Compare_Operator__Alternatives )
            {
             before(grammarAccess.getCompare_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1505:3: ( rule__Compare_Operator__Alternatives )
            // InternalStructuredTextParser.g:1505:4: rule__Compare_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1514:1: ruleEqu_Operator : ( ( rule__Equ_Operator__Alternatives ) ) ;
    public final void ruleEqu_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1518:1: ( ( ( rule__Equ_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1519:2: ( ( rule__Equ_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1519:2: ( ( rule__Equ_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1520:3: ( rule__Equ_Operator__Alternatives )
            {
             before(grammarAccess.getEqu_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1521:3: ( rule__Equ_Operator__Alternatives )
            // InternalStructuredTextParser.g:1521:4: rule__Equ_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1530:1: ruleAdd_Operator : ( ( rule__Add_Operator__Alternatives ) ) ;
    public final void ruleAdd_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1534:1: ( ( ( rule__Add_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1535:2: ( ( rule__Add_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1535:2: ( ( rule__Add_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1536:3: ( rule__Add_Operator__Alternatives )
            {
             before(grammarAccess.getAdd_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1537:3: ( rule__Add_Operator__Alternatives )
            // InternalStructuredTextParser.g:1537:4: rule__Add_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1546:1: ruleTerm_Operator : ( ( rule__Term_Operator__Alternatives ) ) ;
    public final void ruleTerm_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1550:1: ( ( ( rule__Term_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1551:2: ( ( rule__Term_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1551:2: ( ( rule__Term_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1552:3: ( rule__Term_Operator__Alternatives )
            {
             before(grammarAccess.getTerm_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1553:3: ( rule__Term_Operator__Alternatives )
            // InternalStructuredTextParser.g:1553:4: rule__Term_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1562:1: rulePower_Operator : ( ( AsteriskAsterisk ) ) ;
    public final void rulePower_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1566:1: ( ( ( AsteriskAsterisk ) ) )
            // InternalStructuredTextParser.g:1567:2: ( ( AsteriskAsterisk ) )
            {
            // InternalStructuredTextParser.g:1567:2: ( ( AsteriskAsterisk ) )
            // InternalStructuredTextParser.g:1568:3: ( AsteriskAsterisk )
            {
             before(grammarAccess.getPower_OperatorAccess().getPOWEREnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1569:3: ( AsteriskAsterisk )
            // InternalStructuredTextParser.g:1569:4: AsteriskAsterisk
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
    // InternalStructuredTextParser.g:1578:1: ruleUnary_Operator : ( ( rule__Unary_Operator__Alternatives ) ) ;
    public final void ruleUnary_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1582:1: ( ( ( rule__Unary_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1583:2: ( ( rule__Unary_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1583:2: ( ( rule__Unary_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1584:3: ( rule__Unary_Operator__Alternatives )
            {
             before(grammarAccess.getUnary_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1585:3: ( rule__Unary_Operator__Alternatives )
            // InternalStructuredTextParser.g:1585:4: rule__Unary_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1594:1: ruleInt_Type_Name : ( ( rule__Int_Type_Name__Alternatives ) ) ;
    public final void ruleInt_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1598:1: ( ( ( rule__Int_Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1599:2: ( ( rule__Int_Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1599:2: ( ( rule__Int_Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1600:3: ( rule__Int_Type_Name__Alternatives )
            {
             before(grammarAccess.getInt_Type_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1601:3: ( rule__Int_Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1601:4: rule__Int_Type_Name__Alternatives
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
    // InternalStructuredTextParser.g:1610:1: ruleReal_Type_Name : ( ( rule__Real_Type_Name__Alternatives ) ) ;
    public final void ruleReal_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1614:1: ( ( ( rule__Real_Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1615:2: ( ( rule__Real_Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1615:2: ( ( rule__Real_Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1616:3: ( rule__Real_Type_Name__Alternatives )
            {
             before(grammarAccess.getReal_Type_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1617:3: ( rule__Real_Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1617:4: rule__Real_Type_Name__Alternatives
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
    // InternalStructuredTextParser.g:1626:1: ruleString_Type_Name : ( ( rule__String_Type_Name__Alternatives ) ) ;
    public final void ruleString_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1630:1: ( ( ( rule__String_Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1631:2: ( ( rule__String_Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1631:2: ( ( rule__String_Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1632:3: ( rule__String_Type_Name__Alternatives )
            {
             before(grammarAccess.getString_Type_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1633:3: ( rule__String_Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1633:4: rule__String_Type_Name__Alternatives
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
    // InternalStructuredTextParser.g:1642:1: ruleBool_Type_Name : ( ( BOOL ) ) ;
    public final void ruleBool_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1646:1: ( ( ( BOOL ) ) )
            // InternalStructuredTextParser.g:1647:2: ( ( BOOL ) )
            {
            // InternalStructuredTextParser.g:1647:2: ( ( BOOL ) )
            // InternalStructuredTextParser.g:1648:3: ( BOOL )
            {
             before(grammarAccess.getBool_Type_NameAccess().getBOOLEnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1649:3: ( BOOL )
            // InternalStructuredTextParser.g:1649:4: BOOL
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
    // InternalStructuredTextParser.g:1657:1: rule__Stmt__Alternatives : ( ( ruleAssign_Stmt ) | ( ruleSubprog_Ctrl_Stmt ) | ( ruleSelection_Stmt ) | ( ruleIteration_Stmt ) );
    public final void rule__Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1661:1: ( ( ruleAssign_Stmt ) | ( ruleSubprog_Ctrl_Stmt ) | ( ruleSelection_Stmt ) | ( ruleIteration_Stmt ) )
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
                    // InternalStructuredTextParser.g:1662:2: ( ruleAssign_Stmt )
                    {
                    // InternalStructuredTextParser.g:1662:2: ( ruleAssign_Stmt )
                    // InternalStructuredTextParser.g:1663:3: ruleAssign_Stmt
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
                    // InternalStructuredTextParser.g:1668:2: ( ruleSubprog_Ctrl_Stmt )
                    {
                    // InternalStructuredTextParser.g:1668:2: ( ruleSubprog_Ctrl_Stmt )
                    // InternalStructuredTextParser.g:1669:3: ruleSubprog_Ctrl_Stmt
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
                    // InternalStructuredTextParser.g:1674:2: ( ruleSelection_Stmt )
                    {
                    // InternalStructuredTextParser.g:1674:2: ( ruleSelection_Stmt )
                    // InternalStructuredTextParser.g:1675:3: ruleSelection_Stmt
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
                    // InternalStructuredTextParser.g:1680:2: ( ruleIteration_Stmt )
                    {
                    // InternalStructuredTextParser.g:1680:2: ( ruleIteration_Stmt )
                    // InternalStructuredTextParser.g:1681:3: ruleIteration_Stmt
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
    // InternalStructuredTextParser.g:1690:1: rule__Subprog_Ctrl_Stmt__Alternatives : ( ( ruleFunc_Call ) | ( ( rule__Subprog_Ctrl_Stmt__Group_1__0 ) ) | ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) ) );
    public final void rule__Subprog_Ctrl_Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1694:1: ( ( ruleFunc_Call ) | ( ( rule__Subprog_Ctrl_Stmt__Group_1__0 ) ) | ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) ) )
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
                    // InternalStructuredTextParser.g:1695:2: ( ruleFunc_Call )
                    {
                    // InternalStructuredTextParser.g:1695:2: ( ruleFunc_Call )
                    // InternalStructuredTextParser.g:1696:3: ruleFunc_Call
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
                    // InternalStructuredTextParser.g:1701:2: ( ( rule__Subprog_Ctrl_Stmt__Group_1__0 ) )
                    {
                    // InternalStructuredTextParser.g:1701:2: ( ( rule__Subprog_Ctrl_Stmt__Group_1__0 ) )
                    // InternalStructuredTextParser.g:1702:3: ( rule__Subprog_Ctrl_Stmt__Group_1__0 )
                    {
                     before(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_1()); 
                    // InternalStructuredTextParser.g:1703:3: ( rule__Subprog_Ctrl_Stmt__Group_1__0 )
                    // InternalStructuredTextParser.g:1703:4: rule__Subprog_Ctrl_Stmt__Group_1__0
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
                    // InternalStructuredTextParser.g:1707:2: ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) )
                    {
                    // InternalStructuredTextParser.g:1707:2: ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) )
                    // InternalStructuredTextParser.g:1708:3: ( rule__Subprog_Ctrl_Stmt__Group_2__0 )
                    {
                     before(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_2()); 
                    // InternalStructuredTextParser.g:1709:3: ( rule__Subprog_Ctrl_Stmt__Group_2__0 )
                    // InternalStructuredTextParser.g:1709:4: rule__Subprog_Ctrl_Stmt__Group_2__0
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
    // InternalStructuredTextParser.g:1717:1: rule__Selection_Stmt__Alternatives : ( ( ruleIF_Stmt ) | ( ruleCase_Stmt ) );
    public final void rule__Selection_Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1721:1: ( ( ruleIF_Stmt ) | ( ruleCase_Stmt ) )
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
                    // InternalStructuredTextParser.g:1722:2: ( ruleIF_Stmt )
                    {
                    // InternalStructuredTextParser.g:1722:2: ( ruleIF_Stmt )
                    // InternalStructuredTextParser.g:1723:3: ruleIF_Stmt
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
                    // InternalStructuredTextParser.g:1728:2: ( ruleCase_Stmt )
                    {
                    // InternalStructuredTextParser.g:1728:2: ( ruleCase_Stmt )
                    // InternalStructuredTextParser.g:1729:3: ruleCase_Stmt
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
    // InternalStructuredTextParser.g:1738:1: rule__Iteration_Stmt__Alternatives : ( ( ruleFor_Stmt ) | ( ruleWhile_Stmt ) | ( ruleRepeat_Stmt ) | ( ( rule__Iteration_Stmt__Group_3__0 ) ) | ( ( rule__Iteration_Stmt__Group_4__0 ) ) );
    public final void rule__Iteration_Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1742:1: ( ( ruleFor_Stmt ) | ( ruleWhile_Stmt ) | ( ruleRepeat_Stmt ) | ( ( rule__Iteration_Stmt__Group_3__0 ) ) | ( ( rule__Iteration_Stmt__Group_4__0 ) ) )
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
                    // InternalStructuredTextParser.g:1743:2: ( ruleFor_Stmt )
                    {
                    // InternalStructuredTextParser.g:1743:2: ( ruleFor_Stmt )
                    // InternalStructuredTextParser.g:1744:3: ruleFor_Stmt
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
                    // InternalStructuredTextParser.g:1749:2: ( ruleWhile_Stmt )
                    {
                    // InternalStructuredTextParser.g:1749:2: ( ruleWhile_Stmt )
                    // InternalStructuredTextParser.g:1750:3: ruleWhile_Stmt
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
                    // InternalStructuredTextParser.g:1755:2: ( ruleRepeat_Stmt )
                    {
                    // InternalStructuredTextParser.g:1755:2: ( ruleRepeat_Stmt )
                    // InternalStructuredTextParser.g:1756:3: ruleRepeat_Stmt
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
                    // InternalStructuredTextParser.g:1761:2: ( ( rule__Iteration_Stmt__Group_3__0 ) )
                    {
                    // InternalStructuredTextParser.g:1761:2: ( ( rule__Iteration_Stmt__Group_3__0 ) )
                    // InternalStructuredTextParser.g:1762:3: ( rule__Iteration_Stmt__Group_3__0 )
                    {
                     before(grammarAccess.getIteration_StmtAccess().getGroup_3()); 
                    // InternalStructuredTextParser.g:1763:3: ( rule__Iteration_Stmt__Group_3__0 )
                    // InternalStructuredTextParser.g:1763:4: rule__Iteration_Stmt__Group_3__0
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
                    // InternalStructuredTextParser.g:1767:2: ( ( rule__Iteration_Stmt__Group_4__0 ) )
                    {
                    // InternalStructuredTextParser.g:1767:2: ( ( rule__Iteration_Stmt__Group_4__0 ) )
                    // InternalStructuredTextParser.g:1768:3: ( rule__Iteration_Stmt__Group_4__0 )
                    {
                     before(grammarAccess.getIteration_StmtAccess().getGroup_4()); 
                    // InternalStructuredTextParser.g:1769:3: ( rule__Iteration_Stmt__Group_4__0 )
                    // InternalStructuredTextParser.g:1769:4: rule__Iteration_Stmt__Group_4__0
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
    // InternalStructuredTextParser.g:1777:1: rule__Unary_Expr__Alternatives : ( ( ( rule__Unary_Expr__Group_0__0 ) ) | ( rulePrimary_Expr ) | ( ruleConstant ) );
    public final void rule__Unary_Expr__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1781:1: ( ( ( rule__Unary_Expr__Group_0__0 ) ) | ( rulePrimary_Expr ) | ( ruleConstant ) )
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
                    // InternalStructuredTextParser.g:1782:2: ( ( rule__Unary_Expr__Group_0__0 ) )
                    {
                    // InternalStructuredTextParser.g:1782:2: ( ( rule__Unary_Expr__Group_0__0 ) )
                    // InternalStructuredTextParser.g:1783:3: ( rule__Unary_Expr__Group_0__0 )
                    {
                     before(grammarAccess.getUnary_ExprAccess().getGroup_0()); 
                    // InternalStructuredTextParser.g:1784:3: ( rule__Unary_Expr__Group_0__0 )
                    // InternalStructuredTextParser.g:1784:4: rule__Unary_Expr__Group_0__0
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
                    // InternalStructuredTextParser.g:1788:2: ( rulePrimary_Expr )
                    {
                    // InternalStructuredTextParser.g:1788:2: ( rulePrimary_Expr )
                    // InternalStructuredTextParser.g:1789:3: rulePrimary_Expr
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
                    // InternalStructuredTextParser.g:1794:2: ( ruleConstant )
                    {
                    // InternalStructuredTextParser.g:1794:2: ( ruleConstant )
                    // InternalStructuredTextParser.g:1795:3: ruleConstant
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
    // InternalStructuredTextParser.g:1804:1: rule__Primary_Expr__Alternatives : ( ( ruleVariable ) | ( ruleFunc_Call ) | ( ( rule__Primary_Expr__Group_2__0 ) ) );
    public final void rule__Primary_Expr__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1808:1: ( ( ruleVariable ) | ( ruleFunc_Call ) | ( ( rule__Primary_Expr__Group_2__0 ) ) )
            int alt6=3;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA6_1 = input.LA(2);

                if ( (LA6_1==LeftParenthesis) ) {
                    alt6=2;
                }
                else if ( (LA6_1==EOF||LA6_1==END_REPEAT||LA6_1==THEN||(LA6_1>=B && LA6_1<=AND)||LA6_1==MOD||(LA6_1>=XOR && LA6_1<=AsteriskAsterisk)||(LA6_1>=LessThanSignEqualsSign && LA6_1<=LessThanSignGreaterThanSign)||LA6_1==GreaterThanSignEqualsSign||(LA6_1>=BY && LA6_1<=DO)||(LA6_1>=OF && LA6_1<=TO)||LA6_1==Ampersand||(LA6_1>=RightParenthesis && LA6_1<=Solidus)||(LA6_1>=Semicolon && LA6_1<=GreaterThanSign)||(LA6_1>=LeftSquareBracket && LA6_1<=RightSquareBracket)) ) {
                    alt6=1;
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
                    // InternalStructuredTextParser.g:1809:2: ( ruleVariable )
                    {
                    // InternalStructuredTextParser.g:1809:2: ( ruleVariable )
                    // InternalStructuredTextParser.g:1810:3: ruleVariable
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
                    // InternalStructuredTextParser.g:1815:2: ( ruleFunc_Call )
                    {
                    // InternalStructuredTextParser.g:1815:2: ( ruleFunc_Call )
                    // InternalStructuredTextParser.g:1816:3: ruleFunc_Call
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
                    // InternalStructuredTextParser.g:1821:2: ( ( rule__Primary_Expr__Group_2__0 ) )
                    {
                    // InternalStructuredTextParser.g:1821:2: ( ( rule__Primary_Expr__Group_2__0 ) )
                    // InternalStructuredTextParser.g:1822:3: ( rule__Primary_Expr__Group_2__0 )
                    {
                     before(grammarAccess.getPrimary_ExprAccess().getGroup_2()); 
                    // InternalStructuredTextParser.g:1823:3: ( rule__Primary_Expr__Group_2__0 )
                    // InternalStructuredTextParser.g:1823:4: rule__Primary_Expr__Group_2__0
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
    // InternalStructuredTextParser.g:1831:1: rule__Func_Call__FuncAlternatives_0_0 : ( ( RULE_ID ) | ( TIME ) );
    public final void rule__Func_Call__FuncAlternatives_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1835:1: ( ( RULE_ID ) | ( TIME ) )
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
                    // InternalStructuredTextParser.g:1836:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:1836:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:1837:3: RULE_ID
                    {
                     before(grammarAccess.getFunc_CallAccess().getFuncIDTerminalRuleCall_0_0_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getFunc_CallAccess().getFuncIDTerminalRuleCall_0_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1842:2: ( TIME )
                    {
                    // InternalStructuredTextParser.g:1842:2: ( TIME )
                    // InternalStructuredTextParser.g:1843:3: TIME
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
    // InternalStructuredTextParser.g:1852:1: rule__Param_Assign__Alternatives : ( ( ruleParam_Assign_In ) | ( ruleParam_Assign_Out ) );
    public final void rule__Param_Assign__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1856:1: ( ( ruleParam_Assign_In ) | ( ruleParam_Assign_Out ) )
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

                    if ( (LA8_5==EqualsSignGreaterThanSign) ) {
                        alt8=2;
                    }
                    else if ( (LA8_5==EOF||(LA8_5>=B && LA8_5<=AND)||LA8_5==MOD||(LA8_5>=XOR && LA8_5<=AsteriskAsterisk)||(LA8_5>=LessThanSignEqualsSign && LA8_5<=LessThanSignGreaterThanSign)||LA8_5==GreaterThanSignEqualsSign||LA8_5==OR||(LA8_5>=Ampersand && LA8_5<=Solidus)||(LA8_5>=LessThanSign && LA8_5<=GreaterThanSign)||LA8_5==LeftSquareBracket) ) {
                        alt8=1;
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
                    // InternalStructuredTextParser.g:1857:2: ( ruleParam_Assign_In )
                    {
                    // InternalStructuredTextParser.g:1857:2: ( ruleParam_Assign_In )
                    // InternalStructuredTextParser.g:1858:3: ruleParam_Assign_In
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
                    // InternalStructuredTextParser.g:1863:2: ( ruleParam_Assign_Out )
                    {
                    // InternalStructuredTextParser.g:1863:2: ( ruleParam_Assign_Out )
                    // InternalStructuredTextParser.g:1864:3: ruleParam_Assign_Out
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
    // InternalStructuredTextParser.g:1873:1: rule__Variable_Subscript__Alternatives_0 : ( ( ruleVariable_Primary ) | ( ruleVariable_Adapter ) );
    public final void rule__Variable_Subscript__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1877:1: ( ( ruleVariable_Primary ) | ( ruleVariable_Adapter ) )
            int alt9=2;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA9_1 = input.LA(2);

                if ( (LA9_1==EOF||LA9_1==END_REPEAT||LA9_1==THEN||(LA9_1>=B && LA9_1<=AND)||LA9_1==MOD||(LA9_1>=XOR && LA9_1<=AsteriskAsterisk)||(LA9_1>=ColonEqualsSign && LA9_1<=LessThanSignGreaterThanSign)||LA9_1==GreaterThanSignEqualsSign||(LA9_1>=BY && LA9_1<=DO)||(LA9_1>=OF && LA9_1<=TO)||LA9_1==Ampersand||(LA9_1>=RightParenthesis && LA9_1<=HyphenMinus)||(LA9_1>=Solidus && LA9_1<=GreaterThanSign)||(LA9_1>=LeftSquareBracket && LA9_1<=RightSquareBracket)) ) {
                    alt9=1;
                }
                else if ( (LA9_1==FullStop) ) {
                    int LA9_6 = input.LA(3);

                    if ( (LA9_6==RULE_UNSIGNED_INT) ) {
                        alt9=1;
                    }
                    else if ( (LA9_6==DT||LA9_6==LT||LA9_6==T||LA9_6==RULE_ID) ) {
                        alt9=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 6, input);

                        throw nvae;
                    }
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
                    int LA9_6 = input.LA(3);

                    if ( (LA9_6==RULE_UNSIGNED_INT) ) {
                        alt9=1;
                    }
                    else if ( (LA9_6==DT||LA9_6==LT||LA9_6==T||LA9_6==RULE_ID) ) {
                        alt9=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 6, input);

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

                if ( (LA9_3==EOF||LA9_3==END_REPEAT||LA9_3==THEN||(LA9_3>=B && LA9_3<=AND)||LA9_3==MOD||(LA9_3>=XOR && LA9_3<=AsteriskAsterisk)||(LA9_3>=ColonEqualsSign && LA9_3<=LessThanSignGreaterThanSign)||LA9_3==GreaterThanSignEqualsSign||(LA9_3>=BY && LA9_3<=DO)||(LA9_3>=OF && LA9_3<=TO)||LA9_3==Ampersand||(LA9_3>=RightParenthesis && LA9_3<=HyphenMinus)||(LA9_3>=Solidus && LA9_3<=GreaterThanSign)||(LA9_3>=LeftSquareBracket && LA9_3<=RightSquareBracket)) ) {
                    alt9=1;
                }
                else if ( (LA9_3==FullStop) ) {
                    int LA9_6 = input.LA(3);

                    if ( (LA9_6==RULE_UNSIGNED_INT) ) {
                        alt9=1;
                    }
                    else if ( (LA9_6==DT||LA9_6==LT||LA9_6==T||LA9_6==RULE_ID) ) {
                        alt9=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 6, input);

                        throw nvae;
                    }
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

                if ( (LA9_4==FullStop) ) {
                    int LA9_6 = input.LA(3);

                    if ( (LA9_6==RULE_UNSIGNED_INT) ) {
                        alt9=1;
                    }
                    else if ( (LA9_6==DT||LA9_6==LT||LA9_6==T||LA9_6==RULE_ID) ) {
                        alt9=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 6, input);

                        throw nvae;
                    }
                }
                else if ( (LA9_4==EOF||LA9_4==END_REPEAT||LA9_4==THEN||(LA9_4>=B && LA9_4<=AND)||LA9_4==MOD||(LA9_4>=XOR && LA9_4<=AsteriskAsterisk)||(LA9_4>=ColonEqualsSign && LA9_4<=LessThanSignGreaterThanSign)||LA9_4==GreaterThanSignEqualsSign||(LA9_4>=BY && LA9_4<=DO)||(LA9_4>=OF && LA9_4<=TO)||LA9_4==Ampersand||(LA9_4>=RightParenthesis && LA9_4<=HyphenMinus)||(LA9_4>=Solidus && LA9_4<=GreaterThanSign)||(LA9_4>=LeftSquareBracket && LA9_4<=RightSquareBracket)) ) {
                    alt9=1;
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
                    // InternalStructuredTextParser.g:1878:2: ( ruleVariable_Primary )
                    {
                    // InternalStructuredTextParser.g:1878:2: ( ruleVariable_Primary )
                    // InternalStructuredTextParser.g:1879:3: ruleVariable_Primary
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
                    // InternalStructuredTextParser.g:1884:2: ( ruleVariable_Adapter )
                    {
                    // InternalStructuredTextParser.g:1884:2: ( ruleVariable_Adapter )
                    // InternalStructuredTextParser.g:1885:3: ruleVariable_Adapter
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
    // InternalStructuredTextParser.g:1894:1: rule__Multibit_Part_Access__Alternatives : ( ( ( rule__Multibit_Part_Access__Group_0__0 ) ) | ( ( rule__Multibit_Part_Access__Group_1__0 ) ) | ( ( rule__Multibit_Part_Access__Group_2__0 ) ) | ( ( rule__Multibit_Part_Access__Group_3__0 ) ) | ( ( rule__Multibit_Part_Access__Group_4__0 ) ) );
    public final void rule__Multibit_Part_Access__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1898:1: ( ( ( rule__Multibit_Part_Access__Group_0__0 ) ) | ( ( rule__Multibit_Part_Access__Group_1__0 ) ) | ( ( rule__Multibit_Part_Access__Group_2__0 ) ) | ( ( rule__Multibit_Part_Access__Group_3__0 ) ) | ( ( rule__Multibit_Part_Access__Group_4__0 ) ) )
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
                    // InternalStructuredTextParser.g:1899:2: ( ( rule__Multibit_Part_Access__Group_0__0 ) )
                    {
                    // InternalStructuredTextParser.g:1899:2: ( ( rule__Multibit_Part_Access__Group_0__0 ) )
                    // InternalStructuredTextParser.g:1900:3: ( rule__Multibit_Part_Access__Group_0__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_0()); 
                    // InternalStructuredTextParser.g:1901:3: ( rule__Multibit_Part_Access__Group_0__0 )
                    // InternalStructuredTextParser.g:1901:4: rule__Multibit_Part_Access__Group_0__0
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
                    // InternalStructuredTextParser.g:1905:2: ( ( rule__Multibit_Part_Access__Group_1__0 ) )
                    {
                    // InternalStructuredTextParser.g:1905:2: ( ( rule__Multibit_Part_Access__Group_1__0 ) )
                    // InternalStructuredTextParser.g:1906:3: ( rule__Multibit_Part_Access__Group_1__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_1()); 
                    // InternalStructuredTextParser.g:1907:3: ( rule__Multibit_Part_Access__Group_1__0 )
                    // InternalStructuredTextParser.g:1907:4: rule__Multibit_Part_Access__Group_1__0
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
                    // InternalStructuredTextParser.g:1911:2: ( ( rule__Multibit_Part_Access__Group_2__0 ) )
                    {
                    // InternalStructuredTextParser.g:1911:2: ( ( rule__Multibit_Part_Access__Group_2__0 ) )
                    // InternalStructuredTextParser.g:1912:3: ( rule__Multibit_Part_Access__Group_2__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_2()); 
                    // InternalStructuredTextParser.g:1913:3: ( rule__Multibit_Part_Access__Group_2__0 )
                    // InternalStructuredTextParser.g:1913:4: rule__Multibit_Part_Access__Group_2__0
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
                    // InternalStructuredTextParser.g:1917:2: ( ( rule__Multibit_Part_Access__Group_3__0 ) )
                    {
                    // InternalStructuredTextParser.g:1917:2: ( ( rule__Multibit_Part_Access__Group_3__0 ) )
                    // InternalStructuredTextParser.g:1918:3: ( rule__Multibit_Part_Access__Group_3__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_3()); 
                    // InternalStructuredTextParser.g:1919:3: ( rule__Multibit_Part_Access__Group_3__0 )
                    // InternalStructuredTextParser.g:1919:4: rule__Multibit_Part_Access__Group_3__0
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
                    // InternalStructuredTextParser.g:1923:2: ( ( rule__Multibit_Part_Access__Group_4__0 ) )
                    {
                    // InternalStructuredTextParser.g:1923:2: ( ( rule__Multibit_Part_Access__Group_4__0 ) )
                    // InternalStructuredTextParser.g:1924:3: ( rule__Multibit_Part_Access__Group_4__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_4()); 
                    // InternalStructuredTextParser.g:1925:3: ( rule__Multibit_Part_Access__Group_4__0 )
                    // InternalStructuredTextParser.g:1925:4: rule__Multibit_Part_Access__Group_4__0
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
    // InternalStructuredTextParser.g:1933:1: rule__Adapter_Name__Alternatives : ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) );
    public final void rule__Adapter_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1937:1: ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) )
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
                    // InternalStructuredTextParser.g:1938:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:1938:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:1939:3: RULE_ID
                    {
                     before(grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1944:2: ( T )
                    {
                    // InternalStructuredTextParser.g:1944:2: ( T )
                    // InternalStructuredTextParser.g:1945:3: T
                    {
                     before(grammarAccess.getAdapter_NameAccess().getTKeyword_1()); 
                    match(input,T,FOLLOW_2); 
                     after(grammarAccess.getAdapter_NameAccess().getTKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1950:2: ( LT )
                    {
                    // InternalStructuredTextParser.g:1950:2: ( LT )
                    // InternalStructuredTextParser.g:1951:3: LT
                    {
                     before(grammarAccess.getAdapter_NameAccess().getLTKeyword_2()); 
                    match(input,LT,FOLLOW_2); 
                     after(grammarAccess.getAdapter_NameAccess().getLTKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:1956:2: ( DT )
                    {
                    // InternalStructuredTextParser.g:1956:2: ( DT )
                    // InternalStructuredTextParser.g:1957:3: DT
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
    // InternalStructuredTextParser.g:1966:1: rule__Variable_Name__Alternatives : ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) );
    public final void rule__Variable_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1970:1: ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) )
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
                    // InternalStructuredTextParser.g:1971:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:1971:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:1972:3: RULE_ID
                    {
                     before(grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1977:2: ( T )
                    {
                    // InternalStructuredTextParser.g:1977:2: ( T )
                    // InternalStructuredTextParser.g:1978:3: T
                    {
                     before(grammarAccess.getVariable_NameAccess().getTKeyword_1()); 
                    match(input,T,FOLLOW_2); 
                     after(grammarAccess.getVariable_NameAccess().getTKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1983:2: ( LT )
                    {
                    // InternalStructuredTextParser.g:1983:2: ( LT )
                    // InternalStructuredTextParser.g:1984:3: LT
                    {
                     before(grammarAccess.getVariable_NameAccess().getLTKeyword_2()); 
                    match(input,LT,FOLLOW_2); 
                     after(grammarAccess.getVariable_NameAccess().getLTKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:1989:2: ( DT )
                    {
                    // InternalStructuredTextParser.g:1989:2: ( DT )
                    // InternalStructuredTextParser.g:1990:3: DT
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
    // InternalStructuredTextParser.g:1999:1: rule__Constant__Alternatives : ( ( ruleNumeric_Literal ) | ( ruleChar_Literal ) | ( ruleTime_Literal ) | ( ruleBool_Literal ) );
    public final void rule__Constant__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2003:1: ( ( ruleNumeric_Literal ) | ( ruleChar_Literal ) | ( ruleTime_Literal ) | ( ruleBool_Literal ) )
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
                    // InternalStructuredTextParser.g:2004:2: ( ruleNumeric_Literal )
                    {
                    // InternalStructuredTextParser.g:2004:2: ( ruleNumeric_Literal )
                    // InternalStructuredTextParser.g:2005:3: ruleNumeric_Literal
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
                    // InternalStructuredTextParser.g:2010:2: ( ruleChar_Literal )
                    {
                    // InternalStructuredTextParser.g:2010:2: ( ruleChar_Literal )
                    // InternalStructuredTextParser.g:2011:3: ruleChar_Literal
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
                    // InternalStructuredTextParser.g:2016:2: ( ruleTime_Literal )
                    {
                    // InternalStructuredTextParser.g:2016:2: ( ruleTime_Literal )
                    // InternalStructuredTextParser.g:2017:3: ruleTime_Literal
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
                    // InternalStructuredTextParser.g:2022:2: ( ruleBool_Literal )
                    {
                    // InternalStructuredTextParser.g:2022:2: ( ruleBool_Literal )
                    // InternalStructuredTextParser.g:2023:3: ruleBool_Literal
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
    // InternalStructuredTextParser.g:2032:1: rule__Numeric_Literal__Alternatives : ( ( ruleInt_Literal ) | ( ruleReal_Literal ) );
    public final void rule__Numeric_Literal__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2036:1: ( ( ruleInt_Literal ) | ( ruleReal_Literal ) )
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

                    if ( (LA14_4==FullStop) ) {
                        alt14=2;
                    }
                    else if ( (LA14_4==EOF||LA14_4==END_REPEAT||LA14_4==THEN||LA14_4==AND||LA14_4==MOD||(LA14_4>=XOR && LA14_4<=AsteriskAsterisk)||(LA14_4>=LessThanSignEqualsSign && LA14_4<=LessThanSignGreaterThanSign)||LA14_4==GreaterThanSignEqualsSign||(LA14_4>=BY && LA14_4<=DO)||(LA14_4>=OF && LA14_4<=TO)||LA14_4==Ampersand||(LA14_4>=RightParenthesis && LA14_4<=HyphenMinus)||(LA14_4>=Solidus && LA14_4<=GreaterThanSign)||LA14_4==RightSquareBracket) ) {
                        alt14=1;
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

                    if ( (LA14_4==FullStop) ) {
                        alt14=2;
                    }
                    else if ( (LA14_4==EOF||LA14_4==END_REPEAT||LA14_4==THEN||LA14_4==AND||LA14_4==MOD||(LA14_4>=XOR && LA14_4<=AsteriskAsterisk)||(LA14_4>=LessThanSignEqualsSign && LA14_4<=LessThanSignGreaterThanSign)||LA14_4==GreaterThanSignEqualsSign||(LA14_4>=BY && LA14_4<=DO)||(LA14_4>=OF && LA14_4<=TO)||LA14_4==Ampersand||(LA14_4>=RightParenthesis && LA14_4<=HyphenMinus)||(LA14_4>=Solidus && LA14_4<=GreaterThanSign)||LA14_4==RightSquareBracket) ) {
                        alt14=1;
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

                if ( (LA14_4==FullStop) ) {
                    alt14=2;
                }
                else if ( (LA14_4==EOF||LA14_4==END_REPEAT||LA14_4==THEN||LA14_4==AND||LA14_4==MOD||(LA14_4>=XOR && LA14_4<=AsteriskAsterisk)||(LA14_4>=LessThanSignEqualsSign && LA14_4<=LessThanSignGreaterThanSign)||LA14_4==GreaterThanSignEqualsSign||(LA14_4>=BY && LA14_4<=DO)||(LA14_4>=OF && LA14_4<=TO)||LA14_4==Ampersand||(LA14_4>=RightParenthesis && LA14_4<=HyphenMinus)||(LA14_4>=Solidus && LA14_4<=GreaterThanSign)||LA14_4==RightSquareBracket) ) {
                    alt14=1;
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
                    // InternalStructuredTextParser.g:2037:2: ( ruleInt_Literal )
                    {
                    // InternalStructuredTextParser.g:2037:2: ( ruleInt_Literal )
                    // InternalStructuredTextParser.g:2038:3: ruleInt_Literal
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
                    // InternalStructuredTextParser.g:2043:2: ( ruleReal_Literal )
                    {
                    // InternalStructuredTextParser.g:2043:2: ( ruleReal_Literal )
                    // InternalStructuredTextParser.g:2044:3: ruleReal_Literal
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
    // InternalStructuredTextParser.g:2053:1: rule__Int_Literal__ValueAlternatives_1_0 : ( ( ruleSigned_Int ) | ( RULE_BINARY_INT ) | ( RULE_OCTAL_INT ) | ( RULE_HEX_INT ) );
    public final void rule__Int_Literal__ValueAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2057:1: ( ( ruleSigned_Int ) | ( RULE_BINARY_INT ) | ( RULE_OCTAL_INT ) | ( RULE_HEX_INT ) )
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
                    // InternalStructuredTextParser.g:2058:2: ( ruleSigned_Int )
                    {
                    // InternalStructuredTextParser.g:2058:2: ( ruleSigned_Int )
                    // InternalStructuredTextParser.g:2059:3: ruleSigned_Int
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
                    // InternalStructuredTextParser.g:2064:2: ( RULE_BINARY_INT )
                    {
                    // InternalStructuredTextParser.g:2064:2: ( RULE_BINARY_INT )
                    // InternalStructuredTextParser.g:2065:3: RULE_BINARY_INT
                    {
                     before(grammarAccess.getInt_LiteralAccess().getValueBINARY_INTTerminalRuleCall_1_0_1()); 
                    match(input,RULE_BINARY_INT,FOLLOW_2); 
                     after(grammarAccess.getInt_LiteralAccess().getValueBINARY_INTTerminalRuleCall_1_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2070:2: ( RULE_OCTAL_INT )
                    {
                    // InternalStructuredTextParser.g:2070:2: ( RULE_OCTAL_INT )
                    // InternalStructuredTextParser.g:2071:3: RULE_OCTAL_INT
                    {
                     before(grammarAccess.getInt_LiteralAccess().getValueOCTAL_INTTerminalRuleCall_1_0_2()); 
                    match(input,RULE_OCTAL_INT,FOLLOW_2); 
                     after(grammarAccess.getInt_LiteralAccess().getValueOCTAL_INTTerminalRuleCall_1_0_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2076:2: ( RULE_HEX_INT )
                    {
                    // InternalStructuredTextParser.g:2076:2: ( RULE_HEX_INT )
                    // InternalStructuredTextParser.g:2077:3: RULE_HEX_INT
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
    // InternalStructuredTextParser.g:2086:1: rule__Signed_Int__Alternatives_0 : ( ( PlusSign ) | ( HyphenMinus ) );
    public final void rule__Signed_Int__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2090:1: ( ( PlusSign ) | ( HyphenMinus ) )
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
                    // InternalStructuredTextParser.g:2091:2: ( PlusSign )
                    {
                    // InternalStructuredTextParser.g:2091:2: ( PlusSign )
                    // InternalStructuredTextParser.g:2092:3: PlusSign
                    {
                     before(grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0()); 
                    match(input,PlusSign,FOLLOW_2); 
                     after(grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2097:2: ( HyphenMinus )
                    {
                    // InternalStructuredTextParser.g:2097:2: ( HyphenMinus )
                    // InternalStructuredTextParser.g:2098:3: HyphenMinus
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
    // InternalStructuredTextParser.g:2107:1: rule__Bool_Value__Alternatives : ( ( FALSE ) | ( TRUE ) );
    public final void rule__Bool_Value__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2111:1: ( ( FALSE ) | ( TRUE ) )
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
                    // InternalStructuredTextParser.g:2112:2: ( FALSE )
                    {
                    // InternalStructuredTextParser.g:2112:2: ( FALSE )
                    // InternalStructuredTextParser.g:2113:3: FALSE
                    {
                     before(grammarAccess.getBool_ValueAccess().getFALSEKeyword_0()); 
                    match(input,FALSE,FOLLOW_2); 
                     after(grammarAccess.getBool_ValueAccess().getFALSEKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2118:2: ( TRUE )
                    {
                    // InternalStructuredTextParser.g:2118:2: ( TRUE )
                    // InternalStructuredTextParser.g:2119:3: TRUE
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
    // InternalStructuredTextParser.g:2128:1: rule__Char_Literal__ValueAlternatives_1_0 : ( ( RULE_S_BYTE_CHAR_STR ) | ( RULE_D_BYTE_CHAR_STR ) );
    public final void rule__Char_Literal__ValueAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2132:1: ( ( RULE_S_BYTE_CHAR_STR ) | ( RULE_D_BYTE_CHAR_STR ) )
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
                    // InternalStructuredTextParser.g:2133:2: ( RULE_S_BYTE_CHAR_STR )
                    {
                    // InternalStructuredTextParser.g:2133:2: ( RULE_S_BYTE_CHAR_STR )
                    // InternalStructuredTextParser.g:2134:3: RULE_S_BYTE_CHAR_STR
                    {
                     before(grammarAccess.getChar_LiteralAccess().getValueS_BYTE_CHAR_STRTerminalRuleCall_1_0_0()); 
                    match(input,RULE_S_BYTE_CHAR_STR,FOLLOW_2); 
                     after(grammarAccess.getChar_LiteralAccess().getValueS_BYTE_CHAR_STRTerminalRuleCall_1_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2139:2: ( RULE_D_BYTE_CHAR_STR )
                    {
                    // InternalStructuredTextParser.g:2139:2: ( RULE_D_BYTE_CHAR_STR )
                    // InternalStructuredTextParser.g:2140:3: RULE_D_BYTE_CHAR_STR
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
    // InternalStructuredTextParser.g:2149:1: rule__Time_Literal__LiteralAlternatives_0 : ( ( RULE_TIME ) | ( RULE_DATE ) | ( RULE_TIMEOFDAY ) | ( RULE_DATETIME ) );
    public final void rule__Time_Literal__LiteralAlternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2153:1: ( ( RULE_TIME ) | ( RULE_DATE ) | ( RULE_TIMEOFDAY ) | ( RULE_DATETIME ) )
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
                    // InternalStructuredTextParser.g:2154:2: ( RULE_TIME )
                    {
                    // InternalStructuredTextParser.g:2154:2: ( RULE_TIME )
                    // InternalStructuredTextParser.g:2155:3: RULE_TIME
                    {
                     before(grammarAccess.getTime_LiteralAccess().getLiteralTIMETerminalRuleCall_0_0()); 
                    match(input,RULE_TIME,FOLLOW_2); 
                     after(grammarAccess.getTime_LiteralAccess().getLiteralTIMETerminalRuleCall_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2160:2: ( RULE_DATE )
                    {
                    // InternalStructuredTextParser.g:2160:2: ( RULE_DATE )
                    // InternalStructuredTextParser.g:2161:3: RULE_DATE
                    {
                     before(grammarAccess.getTime_LiteralAccess().getLiteralDATETerminalRuleCall_0_1()); 
                    match(input,RULE_DATE,FOLLOW_2); 
                     after(grammarAccess.getTime_LiteralAccess().getLiteralDATETerminalRuleCall_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2166:2: ( RULE_TIMEOFDAY )
                    {
                    // InternalStructuredTextParser.g:2166:2: ( RULE_TIMEOFDAY )
                    // InternalStructuredTextParser.g:2167:3: RULE_TIMEOFDAY
                    {
                     before(grammarAccess.getTime_LiteralAccess().getLiteralTIMEOFDAYTerminalRuleCall_0_2()); 
                    match(input,RULE_TIMEOFDAY,FOLLOW_2); 
                     after(grammarAccess.getTime_LiteralAccess().getLiteralTIMEOFDAYTerminalRuleCall_0_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2172:2: ( RULE_DATETIME )
                    {
                    // InternalStructuredTextParser.g:2172:2: ( RULE_DATETIME )
                    // InternalStructuredTextParser.g:2173:3: RULE_DATETIME
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
    // InternalStructuredTextParser.g:2182:1: rule__Type_Name__Alternatives : ( ( RULE_ID ) | ( DINT ) | ( INT ) | ( SINT ) | ( LINT ) | ( UINT ) | ( USINT ) | ( UDINT ) | ( ULINT ) | ( REAL ) | ( LREAL ) | ( STRING ) | ( WSTRING ) | ( CHAR ) | ( WCHAR ) | ( TIME ) | ( LTIME ) | ( TIME_OF_DAY ) | ( LTIME_OF_DAY ) | ( TOD ) | ( LTOD ) | ( DATE ) | ( LDATE ) | ( DATE_AND_TIME ) | ( LDATE_AND_TIME ) | ( BOOL ) | ( LWORD ) | ( DWORD ) | ( WORD ) | ( BYTE ) );
    public final void rule__Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2186:1: ( ( RULE_ID ) | ( DINT ) | ( INT ) | ( SINT ) | ( LINT ) | ( UINT ) | ( USINT ) | ( UDINT ) | ( ULINT ) | ( REAL ) | ( LREAL ) | ( STRING ) | ( WSTRING ) | ( CHAR ) | ( WCHAR ) | ( TIME ) | ( LTIME ) | ( TIME_OF_DAY ) | ( LTIME_OF_DAY ) | ( TOD ) | ( LTOD ) | ( DATE ) | ( LDATE ) | ( DATE_AND_TIME ) | ( LDATE_AND_TIME ) | ( BOOL ) | ( LWORD ) | ( DWORD ) | ( WORD ) | ( BYTE ) )
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
                    // InternalStructuredTextParser.g:2187:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:2187:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:2188:3: RULE_ID
                    {
                     before(grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2193:2: ( DINT )
                    {
                    // InternalStructuredTextParser.g:2193:2: ( DINT )
                    // InternalStructuredTextParser.g:2194:3: DINT
                    {
                     before(grammarAccess.getType_NameAccess().getDINTKeyword_1()); 
                    match(input,DINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDINTKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2199:2: ( INT )
                    {
                    // InternalStructuredTextParser.g:2199:2: ( INT )
                    // InternalStructuredTextParser.g:2200:3: INT
                    {
                     before(grammarAccess.getType_NameAccess().getINTKeyword_2()); 
                    match(input,INT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getINTKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2205:2: ( SINT )
                    {
                    // InternalStructuredTextParser.g:2205:2: ( SINT )
                    // InternalStructuredTextParser.g:2206:3: SINT
                    {
                     before(grammarAccess.getType_NameAccess().getSINTKeyword_3()); 
                    match(input,SINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getSINTKeyword_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:2211:2: ( LINT )
                    {
                    // InternalStructuredTextParser.g:2211:2: ( LINT )
                    // InternalStructuredTextParser.g:2212:3: LINT
                    {
                     before(grammarAccess.getType_NameAccess().getLINTKeyword_4()); 
                    match(input,LINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLINTKeyword_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:2217:2: ( UINT )
                    {
                    // InternalStructuredTextParser.g:2217:2: ( UINT )
                    // InternalStructuredTextParser.g:2218:3: UINT
                    {
                     before(grammarAccess.getType_NameAccess().getUINTKeyword_5()); 
                    match(input,UINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getUINTKeyword_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:2223:2: ( USINT )
                    {
                    // InternalStructuredTextParser.g:2223:2: ( USINT )
                    // InternalStructuredTextParser.g:2224:3: USINT
                    {
                     before(grammarAccess.getType_NameAccess().getUSINTKeyword_6()); 
                    match(input,USINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getUSINTKeyword_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalStructuredTextParser.g:2229:2: ( UDINT )
                    {
                    // InternalStructuredTextParser.g:2229:2: ( UDINT )
                    // InternalStructuredTextParser.g:2230:3: UDINT
                    {
                     before(grammarAccess.getType_NameAccess().getUDINTKeyword_7()); 
                    match(input,UDINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getUDINTKeyword_7()); 

                    }


                    }
                    break;
                case 9 :
                    // InternalStructuredTextParser.g:2235:2: ( ULINT )
                    {
                    // InternalStructuredTextParser.g:2235:2: ( ULINT )
                    // InternalStructuredTextParser.g:2236:3: ULINT
                    {
                     before(grammarAccess.getType_NameAccess().getULINTKeyword_8()); 
                    match(input,ULINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getULINTKeyword_8()); 

                    }


                    }
                    break;
                case 10 :
                    // InternalStructuredTextParser.g:2241:2: ( REAL )
                    {
                    // InternalStructuredTextParser.g:2241:2: ( REAL )
                    // InternalStructuredTextParser.g:2242:3: REAL
                    {
                     before(grammarAccess.getType_NameAccess().getREALKeyword_9()); 
                    match(input,REAL,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getREALKeyword_9()); 

                    }


                    }
                    break;
                case 11 :
                    // InternalStructuredTextParser.g:2247:2: ( LREAL )
                    {
                    // InternalStructuredTextParser.g:2247:2: ( LREAL )
                    // InternalStructuredTextParser.g:2248:3: LREAL
                    {
                     before(grammarAccess.getType_NameAccess().getLREALKeyword_10()); 
                    match(input,LREAL,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLREALKeyword_10()); 

                    }


                    }
                    break;
                case 12 :
                    // InternalStructuredTextParser.g:2253:2: ( STRING )
                    {
                    // InternalStructuredTextParser.g:2253:2: ( STRING )
                    // InternalStructuredTextParser.g:2254:3: STRING
                    {
                     before(grammarAccess.getType_NameAccess().getSTRINGKeyword_11()); 
                    match(input,STRING,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getSTRINGKeyword_11()); 

                    }


                    }
                    break;
                case 13 :
                    // InternalStructuredTextParser.g:2259:2: ( WSTRING )
                    {
                    // InternalStructuredTextParser.g:2259:2: ( WSTRING )
                    // InternalStructuredTextParser.g:2260:3: WSTRING
                    {
                     before(grammarAccess.getType_NameAccess().getWSTRINGKeyword_12()); 
                    match(input,WSTRING,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getWSTRINGKeyword_12()); 

                    }


                    }
                    break;
                case 14 :
                    // InternalStructuredTextParser.g:2265:2: ( CHAR )
                    {
                    // InternalStructuredTextParser.g:2265:2: ( CHAR )
                    // InternalStructuredTextParser.g:2266:3: CHAR
                    {
                     before(grammarAccess.getType_NameAccess().getCHARKeyword_13()); 
                    match(input,CHAR,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getCHARKeyword_13()); 

                    }


                    }
                    break;
                case 15 :
                    // InternalStructuredTextParser.g:2271:2: ( WCHAR )
                    {
                    // InternalStructuredTextParser.g:2271:2: ( WCHAR )
                    // InternalStructuredTextParser.g:2272:3: WCHAR
                    {
                     before(grammarAccess.getType_NameAccess().getWCHARKeyword_14()); 
                    match(input,WCHAR,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getWCHARKeyword_14()); 

                    }


                    }
                    break;
                case 16 :
                    // InternalStructuredTextParser.g:2277:2: ( TIME )
                    {
                    // InternalStructuredTextParser.g:2277:2: ( TIME )
                    // InternalStructuredTextParser.g:2278:3: TIME
                    {
                     before(grammarAccess.getType_NameAccess().getTIMEKeyword_15()); 
                    match(input,TIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getTIMEKeyword_15()); 

                    }


                    }
                    break;
                case 17 :
                    // InternalStructuredTextParser.g:2283:2: ( LTIME )
                    {
                    // InternalStructuredTextParser.g:2283:2: ( LTIME )
                    // InternalStructuredTextParser.g:2284:3: LTIME
                    {
                     before(grammarAccess.getType_NameAccess().getLTIMEKeyword_16()); 
                    match(input,LTIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLTIMEKeyword_16()); 

                    }


                    }
                    break;
                case 18 :
                    // InternalStructuredTextParser.g:2289:2: ( TIME_OF_DAY )
                    {
                    // InternalStructuredTextParser.g:2289:2: ( TIME_OF_DAY )
                    // InternalStructuredTextParser.g:2290:3: TIME_OF_DAY
                    {
                     before(grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17()); 
                    match(input,TIME_OF_DAY,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17()); 

                    }


                    }
                    break;
                case 19 :
                    // InternalStructuredTextParser.g:2295:2: ( LTIME_OF_DAY )
                    {
                    // InternalStructuredTextParser.g:2295:2: ( LTIME_OF_DAY )
                    // InternalStructuredTextParser.g:2296:3: LTIME_OF_DAY
                    {
                     before(grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18()); 
                    match(input,LTIME_OF_DAY,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18()); 

                    }


                    }
                    break;
                case 20 :
                    // InternalStructuredTextParser.g:2301:2: ( TOD )
                    {
                    // InternalStructuredTextParser.g:2301:2: ( TOD )
                    // InternalStructuredTextParser.g:2302:3: TOD
                    {
                     before(grammarAccess.getType_NameAccess().getTODKeyword_19()); 
                    match(input,TOD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getTODKeyword_19()); 

                    }


                    }
                    break;
                case 21 :
                    // InternalStructuredTextParser.g:2307:2: ( LTOD )
                    {
                    // InternalStructuredTextParser.g:2307:2: ( LTOD )
                    // InternalStructuredTextParser.g:2308:3: LTOD
                    {
                     before(grammarAccess.getType_NameAccess().getLTODKeyword_20()); 
                    match(input,LTOD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLTODKeyword_20()); 

                    }


                    }
                    break;
                case 22 :
                    // InternalStructuredTextParser.g:2313:2: ( DATE )
                    {
                    // InternalStructuredTextParser.g:2313:2: ( DATE )
                    // InternalStructuredTextParser.g:2314:3: DATE
                    {
                     before(grammarAccess.getType_NameAccess().getDATEKeyword_21()); 
                    match(input,DATE,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDATEKeyword_21()); 

                    }


                    }
                    break;
                case 23 :
                    // InternalStructuredTextParser.g:2319:2: ( LDATE )
                    {
                    // InternalStructuredTextParser.g:2319:2: ( LDATE )
                    // InternalStructuredTextParser.g:2320:3: LDATE
                    {
                     before(grammarAccess.getType_NameAccess().getLDATEKeyword_22()); 
                    match(input,LDATE,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLDATEKeyword_22()); 

                    }


                    }
                    break;
                case 24 :
                    // InternalStructuredTextParser.g:2325:2: ( DATE_AND_TIME )
                    {
                    // InternalStructuredTextParser.g:2325:2: ( DATE_AND_TIME )
                    // InternalStructuredTextParser.g:2326:3: DATE_AND_TIME
                    {
                     before(grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23()); 
                    match(input,DATE_AND_TIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23()); 

                    }


                    }
                    break;
                case 25 :
                    // InternalStructuredTextParser.g:2331:2: ( LDATE_AND_TIME )
                    {
                    // InternalStructuredTextParser.g:2331:2: ( LDATE_AND_TIME )
                    // InternalStructuredTextParser.g:2332:3: LDATE_AND_TIME
                    {
                     before(grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24()); 
                    match(input,LDATE_AND_TIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24()); 

                    }


                    }
                    break;
                case 26 :
                    // InternalStructuredTextParser.g:2337:2: ( BOOL )
                    {
                    // InternalStructuredTextParser.g:2337:2: ( BOOL )
                    // InternalStructuredTextParser.g:2338:3: BOOL
                    {
                     before(grammarAccess.getType_NameAccess().getBOOLKeyword_25()); 
                    match(input,BOOL,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getBOOLKeyword_25()); 

                    }


                    }
                    break;
                case 27 :
                    // InternalStructuredTextParser.g:2343:2: ( LWORD )
                    {
                    // InternalStructuredTextParser.g:2343:2: ( LWORD )
                    // InternalStructuredTextParser.g:2344:3: LWORD
                    {
                     before(grammarAccess.getType_NameAccess().getLWORDKeyword_26()); 
                    match(input,LWORD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLWORDKeyword_26()); 

                    }


                    }
                    break;
                case 28 :
                    // InternalStructuredTextParser.g:2349:2: ( DWORD )
                    {
                    // InternalStructuredTextParser.g:2349:2: ( DWORD )
                    // InternalStructuredTextParser.g:2350:3: DWORD
                    {
                     before(grammarAccess.getType_NameAccess().getDWORDKeyword_27()); 
                    match(input,DWORD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDWORDKeyword_27()); 

                    }


                    }
                    break;
                case 29 :
                    // InternalStructuredTextParser.g:2355:2: ( WORD )
                    {
                    // InternalStructuredTextParser.g:2355:2: ( WORD )
                    // InternalStructuredTextParser.g:2356:3: WORD
                    {
                     before(grammarAccess.getType_NameAccess().getWORDKeyword_28()); 
                    match(input,WORD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getWORDKeyword_28()); 

                    }


                    }
                    break;
                case 30 :
                    // InternalStructuredTextParser.g:2361:2: ( BYTE )
                    {
                    // InternalStructuredTextParser.g:2361:2: ( BYTE )
                    // InternalStructuredTextParser.g:2362:3: BYTE
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
    // InternalStructuredTextParser.g:2371:1: rule__And_Operator__Alternatives : ( ( ( AND ) ) | ( ( Ampersand ) ) );
    public final void rule__And_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2375:1: ( ( ( AND ) ) | ( ( Ampersand ) ) )
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
                    // InternalStructuredTextParser.g:2376:2: ( ( AND ) )
                    {
                    // InternalStructuredTextParser.g:2376:2: ( ( AND ) )
                    // InternalStructuredTextParser.g:2377:3: ( AND )
                    {
                     before(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2378:3: ( AND )
                    // InternalStructuredTextParser.g:2378:4: AND
                    {
                    match(input,AND,FOLLOW_2); 

                    }

                     after(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2382:2: ( ( Ampersand ) )
                    {
                    // InternalStructuredTextParser.g:2382:2: ( ( Ampersand ) )
                    // InternalStructuredTextParser.g:2383:3: ( Ampersand )
                    {
                     before(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2384:3: ( Ampersand )
                    // InternalStructuredTextParser.g:2384:4: Ampersand
                    {
                    match(input,Ampersand,FOLLOW_2); 

                    }

                     after(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_1()); 

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
    // InternalStructuredTextParser.g:2392:1: rule__Compare_Operator__Alternatives : ( ( ( EqualsSign ) ) | ( ( LessThanSignGreaterThanSign ) ) );
    public final void rule__Compare_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2396:1: ( ( ( EqualsSign ) ) | ( ( LessThanSignGreaterThanSign ) ) )
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
                    // InternalStructuredTextParser.g:2397:2: ( ( EqualsSign ) )
                    {
                    // InternalStructuredTextParser.g:2397:2: ( ( EqualsSign ) )
                    // InternalStructuredTextParser.g:2398:3: ( EqualsSign )
                    {
                     before(grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2399:3: ( EqualsSign )
                    // InternalStructuredTextParser.g:2399:4: EqualsSign
                    {
                    match(input,EqualsSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2403:2: ( ( LessThanSignGreaterThanSign ) )
                    {
                    // InternalStructuredTextParser.g:2403:2: ( ( LessThanSignGreaterThanSign ) )
                    // InternalStructuredTextParser.g:2404:3: ( LessThanSignGreaterThanSign )
                    {
                     before(grammarAccess.getCompare_OperatorAccess().getNEEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2405:3: ( LessThanSignGreaterThanSign )
                    // InternalStructuredTextParser.g:2405:4: LessThanSignGreaterThanSign
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
    // InternalStructuredTextParser.g:2413:1: rule__Equ_Operator__Alternatives : ( ( ( LessThanSign ) ) | ( ( LessThanSignEqualsSign ) ) | ( ( GreaterThanSign ) ) | ( ( GreaterThanSignEqualsSign ) ) );
    public final void rule__Equ_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2417:1: ( ( ( LessThanSign ) ) | ( ( LessThanSignEqualsSign ) ) | ( ( GreaterThanSign ) ) | ( ( GreaterThanSignEqualsSign ) ) )
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
                    // InternalStructuredTextParser.g:2418:2: ( ( LessThanSign ) )
                    {
                    // InternalStructuredTextParser.g:2418:2: ( ( LessThanSign ) )
                    // InternalStructuredTextParser.g:2419:3: ( LessThanSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2420:3: ( LessThanSign )
                    // InternalStructuredTextParser.g:2420:4: LessThanSign
                    {
                    match(input,LessThanSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2424:2: ( ( LessThanSignEqualsSign ) )
                    {
                    // InternalStructuredTextParser.g:2424:2: ( ( LessThanSignEqualsSign ) )
                    // InternalStructuredTextParser.g:2425:3: ( LessThanSignEqualsSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2426:3: ( LessThanSignEqualsSign )
                    // InternalStructuredTextParser.g:2426:4: LessThanSignEqualsSign
                    {
                    match(input,LessThanSignEqualsSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2430:2: ( ( GreaterThanSign ) )
                    {
                    // InternalStructuredTextParser.g:2430:2: ( ( GreaterThanSign ) )
                    // InternalStructuredTextParser.g:2431:3: ( GreaterThanSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2432:3: ( GreaterThanSign )
                    // InternalStructuredTextParser.g:2432:4: GreaterThanSign
                    {
                    match(input,GreaterThanSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2436:2: ( ( GreaterThanSignEqualsSign ) )
                    {
                    // InternalStructuredTextParser.g:2436:2: ( ( GreaterThanSignEqualsSign ) )
                    // InternalStructuredTextParser.g:2437:3: ( GreaterThanSignEqualsSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getGEEnumLiteralDeclaration_3()); 
                    // InternalStructuredTextParser.g:2438:3: ( GreaterThanSignEqualsSign )
                    // InternalStructuredTextParser.g:2438:4: GreaterThanSignEqualsSign
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
    // InternalStructuredTextParser.g:2446:1: rule__Add_Operator__Alternatives : ( ( ( PlusSign ) ) | ( ( HyphenMinus ) ) );
    public final void rule__Add_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2450:1: ( ( ( PlusSign ) ) | ( ( HyphenMinus ) ) )
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
                    // InternalStructuredTextParser.g:2451:2: ( ( PlusSign ) )
                    {
                    // InternalStructuredTextParser.g:2451:2: ( ( PlusSign ) )
                    // InternalStructuredTextParser.g:2452:3: ( PlusSign )
                    {
                     before(grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2453:3: ( PlusSign )
                    // InternalStructuredTextParser.g:2453:4: PlusSign
                    {
                    match(input,PlusSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2457:2: ( ( HyphenMinus ) )
                    {
                    // InternalStructuredTextParser.g:2457:2: ( ( HyphenMinus ) )
                    // InternalStructuredTextParser.g:2458:3: ( HyphenMinus )
                    {
                     before(grammarAccess.getAdd_OperatorAccess().getSUBEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2459:3: ( HyphenMinus )
                    // InternalStructuredTextParser.g:2459:4: HyphenMinus
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
    // InternalStructuredTextParser.g:2467:1: rule__Term_Operator__Alternatives : ( ( ( Asterisk ) ) | ( ( Solidus ) ) | ( ( MOD ) ) );
    public final void rule__Term_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2471:1: ( ( ( Asterisk ) ) | ( ( Solidus ) ) | ( ( MOD ) ) )
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
                    // InternalStructuredTextParser.g:2472:2: ( ( Asterisk ) )
                    {
                    // InternalStructuredTextParser.g:2472:2: ( ( Asterisk ) )
                    // InternalStructuredTextParser.g:2473:3: ( Asterisk )
                    {
                     before(grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2474:3: ( Asterisk )
                    // InternalStructuredTextParser.g:2474:4: Asterisk
                    {
                    match(input,Asterisk,FOLLOW_2); 

                    }

                     after(grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2478:2: ( ( Solidus ) )
                    {
                    // InternalStructuredTextParser.g:2478:2: ( ( Solidus ) )
                    // InternalStructuredTextParser.g:2479:3: ( Solidus )
                    {
                     before(grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2480:3: ( Solidus )
                    // InternalStructuredTextParser.g:2480:4: Solidus
                    {
                    match(input,Solidus,FOLLOW_2); 

                    }

                     after(grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2484:2: ( ( MOD ) )
                    {
                    // InternalStructuredTextParser.g:2484:2: ( ( MOD ) )
                    // InternalStructuredTextParser.g:2485:3: ( MOD )
                    {
                     before(grammarAccess.getTerm_OperatorAccess().getMODEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2486:3: ( MOD )
                    // InternalStructuredTextParser.g:2486:4: MOD
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
    // InternalStructuredTextParser.g:2494:1: rule__Unary_Operator__Alternatives : ( ( ( HyphenMinus ) ) | ( ( PlusSign ) ) | ( ( NOT ) ) );
    public final void rule__Unary_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2498:1: ( ( ( HyphenMinus ) ) | ( ( PlusSign ) ) | ( ( NOT ) ) )
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
                    // InternalStructuredTextParser.g:2499:2: ( ( HyphenMinus ) )
                    {
                    // InternalStructuredTextParser.g:2499:2: ( ( HyphenMinus ) )
                    // InternalStructuredTextParser.g:2500:3: ( HyphenMinus )
                    {
                     before(grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2501:3: ( HyphenMinus )
                    // InternalStructuredTextParser.g:2501:4: HyphenMinus
                    {
                    match(input,HyphenMinus,FOLLOW_2); 

                    }

                     after(grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2505:2: ( ( PlusSign ) )
                    {
                    // InternalStructuredTextParser.g:2505:2: ( ( PlusSign ) )
                    // InternalStructuredTextParser.g:2506:3: ( PlusSign )
                    {
                     before(grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2507:3: ( PlusSign )
                    // InternalStructuredTextParser.g:2507:4: PlusSign
                    {
                    match(input,PlusSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2511:2: ( ( NOT ) )
                    {
                    // InternalStructuredTextParser.g:2511:2: ( ( NOT ) )
                    // InternalStructuredTextParser.g:2512:3: ( NOT )
                    {
                     before(grammarAccess.getUnary_OperatorAccess().getNOTEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2513:3: ( NOT )
                    // InternalStructuredTextParser.g:2513:4: NOT
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
    // InternalStructuredTextParser.g:2521:1: rule__Int_Type_Name__Alternatives : ( ( ( DINT ) ) | ( ( INT ) ) | ( ( SINT ) ) | ( ( LINT ) ) | ( ( UINT ) ) | ( ( USINT ) ) | ( ( UDINT ) ) | ( ( ULINT ) ) );
    public final void rule__Int_Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2525:1: ( ( ( DINT ) ) | ( ( INT ) ) | ( ( SINT ) ) | ( ( LINT ) ) | ( ( UINT ) ) | ( ( USINT ) ) | ( ( UDINT ) ) | ( ( ULINT ) ) )
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
                    // InternalStructuredTextParser.g:2526:2: ( ( DINT ) )
                    {
                    // InternalStructuredTextParser.g:2526:2: ( ( DINT ) )
                    // InternalStructuredTextParser.g:2527:3: ( DINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2528:3: ( DINT )
                    // InternalStructuredTextParser.g:2528:4: DINT
                    {
                    match(input,DINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2532:2: ( ( INT ) )
                    {
                    // InternalStructuredTextParser.g:2532:2: ( ( INT ) )
                    // InternalStructuredTextParser.g:2533:3: ( INT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2534:3: ( INT )
                    // InternalStructuredTextParser.g:2534:4: INT
                    {
                    match(input,INT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2538:2: ( ( SINT ) )
                    {
                    // InternalStructuredTextParser.g:2538:2: ( ( SINT ) )
                    // InternalStructuredTextParser.g:2539:3: ( SINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2540:3: ( SINT )
                    // InternalStructuredTextParser.g:2540:4: SINT
                    {
                    match(input,SINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2544:2: ( ( LINT ) )
                    {
                    // InternalStructuredTextParser.g:2544:2: ( ( LINT ) )
                    // InternalStructuredTextParser.g:2545:3: ( LINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3()); 
                    // InternalStructuredTextParser.g:2546:3: ( LINT )
                    // InternalStructuredTextParser.g:2546:4: LINT
                    {
                    match(input,LINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:2550:2: ( ( UINT ) )
                    {
                    // InternalStructuredTextParser.g:2550:2: ( ( UINT ) )
                    // InternalStructuredTextParser.g:2551:3: ( UINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4()); 
                    // InternalStructuredTextParser.g:2552:3: ( UINT )
                    // InternalStructuredTextParser.g:2552:4: UINT
                    {
                    match(input,UINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:2556:2: ( ( USINT ) )
                    {
                    // InternalStructuredTextParser.g:2556:2: ( ( USINT ) )
                    // InternalStructuredTextParser.g:2557:3: ( USINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5()); 
                    // InternalStructuredTextParser.g:2558:3: ( USINT )
                    // InternalStructuredTextParser.g:2558:4: USINT
                    {
                    match(input,USINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:2562:2: ( ( UDINT ) )
                    {
                    // InternalStructuredTextParser.g:2562:2: ( ( UDINT ) )
                    // InternalStructuredTextParser.g:2563:3: ( UDINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6()); 
                    // InternalStructuredTextParser.g:2564:3: ( UDINT )
                    // InternalStructuredTextParser.g:2564:4: UDINT
                    {
                    match(input,UDINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalStructuredTextParser.g:2568:2: ( ( ULINT ) )
                    {
                    // InternalStructuredTextParser.g:2568:2: ( ( ULINT ) )
                    // InternalStructuredTextParser.g:2569:3: ( ULINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getULINTEnumLiteralDeclaration_7()); 
                    // InternalStructuredTextParser.g:2570:3: ( ULINT )
                    // InternalStructuredTextParser.g:2570:4: ULINT
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
    // InternalStructuredTextParser.g:2578:1: rule__Real_Type_Name__Alternatives : ( ( ( REAL ) ) | ( ( LREAL ) ) );
    public final void rule__Real_Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2582:1: ( ( ( REAL ) ) | ( ( LREAL ) ) )
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
                    // InternalStructuredTextParser.g:2583:2: ( ( REAL ) )
                    {
                    // InternalStructuredTextParser.g:2583:2: ( ( REAL ) )
                    // InternalStructuredTextParser.g:2584:3: ( REAL )
                    {
                     before(grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2585:3: ( REAL )
                    // InternalStructuredTextParser.g:2585:4: REAL
                    {
                    match(input,REAL,FOLLOW_2); 

                    }

                     after(grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2589:2: ( ( LREAL ) )
                    {
                    // InternalStructuredTextParser.g:2589:2: ( ( LREAL ) )
                    // InternalStructuredTextParser.g:2590:3: ( LREAL )
                    {
                     before(grammarAccess.getReal_Type_NameAccess().getLREALEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2591:3: ( LREAL )
                    // InternalStructuredTextParser.g:2591:4: LREAL
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
    // InternalStructuredTextParser.g:2599:1: rule__String_Type_Name__Alternatives : ( ( ( STRING ) ) | ( ( WSTRING ) ) | ( ( CHAR ) ) | ( ( WCHAR ) ) );
    public final void rule__String_Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2603:1: ( ( ( STRING ) ) | ( ( WSTRING ) ) | ( ( CHAR ) ) | ( ( WCHAR ) ) )
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
                    // InternalStructuredTextParser.g:2604:2: ( ( STRING ) )
                    {
                    // InternalStructuredTextParser.g:2604:2: ( ( STRING ) )
                    // InternalStructuredTextParser.g:2605:3: ( STRING )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2606:3: ( STRING )
                    // InternalStructuredTextParser.g:2606:4: STRING
                    {
                    match(input,STRING,FOLLOW_2); 

                    }

                     after(grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2610:2: ( ( WSTRING ) )
                    {
                    // InternalStructuredTextParser.g:2610:2: ( ( WSTRING ) )
                    // InternalStructuredTextParser.g:2611:3: ( WSTRING )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2612:3: ( WSTRING )
                    // InternalStructuredTextParser.g:2612:4: WSTRING
                    {
                    match(input,WSTRING,FOLLOW_2); 

                    }

                     after(grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2616:2: ( ( CHAR ) )
                    {
                    // InternalStructuredTextParser.g:2616:2: ( ( CHAR ) )
                    // InternalStructuredTextParser.g:2617:3: ( CHAR )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2618:3: ( CHAR )
                    // InternalStructuredTextParser.g:2618:4: CHAR
                    {
                    match(input,CHAR,FOLLOW_2); 

                    }

                     after(grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2622:2: ( ( WCHAR ) )
                    {
                    // InternalStructuredTextParser.g:2622:2: ( ( WCHAR ) )
                    // InternalStructuredTextParser.g:2623:3: ( WCHAR )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getWCHAREnumLiteralDeclaration_3()); 
                    // InternalStructuredTextParser.g:2624:3: ( WCHAR )
                    // InternalStructuredTextParser.g:2624:4: WCHAR
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
    // InternalStructuredTextParser.g:2632:1: rule__StructuredTextAlgorithm__Group__0 : rule__StructuredTextAlgorithm__Group__0__Impl rule__StructuredTextAlgorithm__Group__1 ;
    public final void rule__StructuredTextAlgorithm__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2636:1: ( rule__StructuredTextAlgorithm__Group__0__Impl rule__StructuredTextAlgorithm__Group__1 )
            // InternalStructuredTextParser.g:2637:2: rule__StructuredTextAlgorithm__Group__0__Impl rule__StructuredTextAlgorithm__Group__1
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
    // InternalStructuredTextParser.g:2644:1: rule__StructuredTextAlgorithm__Group__0__Impl : ( () ) ;
    public final void rule__StructuredTextAlgorithm__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2648:1: ( ( () ) )
            // InternalStructuredTextParser.g:2649:1: ( () )
            {
            // InternalStructuredTextParser.g:2649:1: ( () )
            // InternalStructuredTextParser.g:2650:2: ()
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getStructuredTextAlgorithmAction_0()); 
            // InternalStructuredTextParser.g:2651:2: ()
            // InternalStructuredTextParser.g:2651:3: 
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
    // InternalStructuredTextParser.g:2659:1: rule__StructuredTextAlgorithm__Group__1 : rule__StructuredTextAlgorithm__Group__1__Impl rule__StructuredTextAlgorithm__Group__2 ;
    public final void rule__StructuredTextAlgorithm__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2663:1: ( rule__StructuredTextAlgorithm__Group__1__Impl rule__StructuredTextAlgorithm__Group__2 )
            // InternalStructuredTextParser.g:2664:2: rule__StructuredTextAlgorithm__Group__1__Impl rule__StructuredTextAlgorithm__Group__2
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
    // InternalStructuredTextParser.g:2671:1: rule__StructuredTextAlgorithm__Group__1__Impl : ( ( rule__StructuredTextAlgorithm__Group_1__0 )? ) ;
    public final void rule__StructuredTextAlgorithm__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2675:1: ( ( ( rule__StructuredTextAlgorithm__Group_1__0 )? ) )
            // InternalStructuredTextParser.g:2676:1: ( ( rule__StructuredTextAlgorithm__Group_1__0 )? )
            {
            // InternalStructuredTextParser.g:2676:1: ( ( rule__StructuredTextAlgorithm__Group_1__0 )? )
            // InternalStructuredTextParser.g:2677:2: ( rule__StructuredTextAlgorithm__Group_1__0 )?
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:2678:2: ( rule__StructuredTextAlgorithm__Group_1__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==VAR) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalStructuredTextParser.g:2678:3: rule__StructuredTextAlgorithm__Group_1__0
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
    // InternalStructuredTextParser.g:2686:1: rule__StructuredTextAlgorithm__Group__2 : rule__StructuredTextAlgorithm__Group__2__Impl ;
    public final void rule__StructuredTextAlgorithm__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2690:1: ( rule__StructuredTextAlgorithm__Group__2__Impl )
            // InternalStructuredTextParser.g:2691:2: rule__StructuredTextAlgorithm__Group__2__Impl
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
    // InternalStructuredTextParser.g:2697:1: rule__StructuredTextAlgorithm__Group__2__Impl : ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) ) ;
    public final void rule__StructuredTextAlgorithm__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2701:1: ( ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) ) )
            // InternalStructuredTextParser.g:2702:1: ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) )
            {
            // InternalStructuredTextParser.g:2702:1: ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) )
            // InternalStructuredTextParser.g:2703:2: ( rule__StructuredTextAlgorithm__StatementsAssignment_2 )
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsAssignment_2()); 
            // InternalStructuredTextParser.g:2704:2: ( rule__StructuredTextAlgorithm__StatementsAssignment_2 )
            // InternalStructuredTextParser.g:2704:3: rule__StructuredTextAlgorithm__StatementsAssignment_2
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
    // InternalStructuredTextParser.g:2713:1: rule__StructuredTextAlgorithm__Group_1__0 : rule__StructuredTextAlgorithm__Group_1__0__Impl rule__StructuredTextAlgorithm__Group_1__1 ;
    public final void rule__StructuredTextAlgorithm__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2717:1: ( rule__StructuredTextAlgorithm__Group_1__0__Impl rule__StructuredTextAlgorithm__Group_1__1 )
            // InternalStructuredTextParser.g:2718:2: rule__StructuredTextAlgorithm__Group_1__0__Impl rule__StructuredTextAlgorithm__Group_1__1
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
    // InternalStructuredTextParser.g:2725:1: rule__StructuredTextAlgorithm__Group_1__0__Impl : ( VAR ) ;
    public final void rule__StructuredTextAlgorithm__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2729:1: ( ( VAR ) )
            // InternalStructuredTextParser.g:2730:1: ( VAR )
            {
            // InternalStructuredTextParser.g:2730:1: ( VAR )
            // InternalStructuredTextParser.g:2731:2: VAR
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
    // InternalStructuredTextParser.g:2740:1: rule__StructuredTextAlgorithm__Group_1__1 : rule__StructuredTextAlgorithm__Group_1__1__Impl rule__StructuredTextAlgorithm__Group_1__2 ;
    public final void rule__StructuredTextAlgorithm__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2744:1: ( rule__StructuredTextAlgorithm__Group_1__1__Impl rule__StructuredTextAlgorithm__Group_1__2 )
            // InternalStructuredTextParser.g:2745:2: rule__StructuredTextAlgorithm__Group_1__1__Impl rule__StructuredTextAlgorithm__Group_1__2
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
    // InternalStructuredTextParser.g:2752:1: rule__StructuredTextAlgorithm__Group_1__1__Impl : ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* ) ;
    public final void rule__StructuredTextAlgorithm__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2756:1: ( ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* ) )
            // InternalStructuredTextParser.g:2757:1: ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* )
            {
            // InternalStructuredTextParser.g:2757:1: ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* )
            // InternalStructuredTextParser.g:2758:2: ( rule__StructuredTextAlgorithm__Group_1_1__0 )*
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1_1()); 
            // InternalStructuredTextParser.g:2759:2: ( rule__StructuredTextAlgorithm__Group_1_1__0 )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==CONSTANT||LA31_0==RULE_ID) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalStructuredTextParser.g:2759:3: rule__StructuredTextAlgorithm__Group_1_1__0
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
    // InternalStructuredTextParser.g:2767:1: rule__StructuredTextAlgorithm__Group_1__2 : rule__StructuredTextAlgorithm__Group_1__2__Impl ;
    public final void rule__StructuredTextAlgorithm__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2771:1: ( rule__StructuredTextAlgorithm__Group_1__2__Impl )
            // InternalStructuredTextParser.g:2772:2: rule__StructuredTextAlgorithm__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:2778:1: rule__StructuredTextAlgorithm__Group_1__2__Impl : ( END_VAR ) ;
    public final void rule__StructuredTextAlgorithm__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2782:1: ( ( END_VAR ) )
            // InternalStructuredTextParser.g:2783:1: ( END_VAR )
            {
            // InternalStructuredTextParser.g:2783:1: ( END_VAR )
            // InternalStructuredTextParser.g:2784:2: END_VAR
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
    // InternalStructuredTextParser.g:2794:1: rule__StructuredTextAlgorithm__Group_1_1__0 : rule__StructuredTextAlgorithm__Group_1_1__0__Impl rule__StructuredTextAlgorithm__Group_1_1__1 ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2798:1: ( rule__StructuredTextAlgorithm__Group_1_1__0__Impl rule__StructuredTextAlgorithm__Group_1_1__1 )
            // InternalStructuredTextParser.g:2799:2: rule__StructuredTextAlgorithm__Group_1_1__0__Impl rule__StructuredTextAlgorithm__Group_1_1__1
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
    // InternalStructuredTextParser.g:2806:1: rule__StructuredTextAlgorithm__Group_1_1__0__Impl : ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) ) ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2810:1: ( ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) ) )
            // InternalStructuredTextParser.g:2811:1: ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) )
            {
            // InternalStructuredTextParser.g:2811:1: ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) )
            // InternalStructuredTextParser.g:2812:2: ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 )
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getLocalVariablesAssignment_1_1_0()); 
            // InternalStructuredTextParser.g:2813:2: ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 )
            // InternalStructuredTextParser.g:2813:3: rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0
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
    // InternalStructuredTextParser.g:2821:1: rule__StructuredTextAlgorithm__Group_1_1__1 : rule__StructuredTextAlgorithm__Group_1_1__1__Impl ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2825:1: ( rule__StructuredTextAlgorithm__Group_1_1__1__Impl )
            // InternalStructuredTextParser.g:2826:2: rule__StructuredTextAlgorithm__Group_1_1__1__Impl
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
    // InternalStructuredTextParser.g:2832:1: rule__StructuredTextAlgorithm__Group_1_1__1__Impl : ( Semicolon ) ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2836:1: ( ( Semicolon ) )
            // InternalStructuredTextParser.g:2837:1: ( Semicolon )
            {
            // InternalStructuredTextParser.g:2837:1: ( Semicolon )
            // InternalStructuredTextParser.g:2838:2: Semicolon
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
    // InternalStructuredTextParser.g:2848:1: rule__Var_Decl_Local__Group__0 : rule__Var_Decl_Local__Group__0__Impl rule__Var_Decl_Local__Group__1 ;
    public final void rule__Var_Decl_Local__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2852:1: ( rule__Var_Decl_Local__Group__0__Impl rule__Var_Decl_Local__Group__1 )
            // InternalStructuredTextParser.g:2853:2: rule__Var_Decl_Local__Group__0__Impl rule__Var_Decl_Local__Group__1
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
    // InternalStructuredTextParser.g:2860:1: rule__Var_Decl_Local__Group__0__Impl : ( () ) ;
    public final void rule__Var_Decl_Local__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2864:1: ( ( () ) )
            // InternalStructuredTextParser.g:2865:1: ( () )
            {
            // InternalStructuredTextParser.g:2865:1: ( () )
            // InternalStructuredTextParser.g:2866:2: ()
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocalVariableAction_0()); 
            // InternalStructuredTextParser.g:2867:2: ()
            // InternalStructuredTextParser.g:2867:3: 
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
    // InternalStructuredTextParser.g:2875:1: rule__Var_Decl_Local__Group__1 : rule__Var_Decl_Local__Group__1__Impl rule__Var_Decl_Local__Group__2 ;
    public final void rule__Var_Decl_Local__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2879:1: ( rule__Var_Decl_Local__Group__1__Impl rule__Var_Decl_Local__Group__2 )
            // InternalStructuredTextParser.g:2880:2: rule__Var_Decl_Local__Group__1__Impl rule__Var_Decl_Local__Group__2
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
    // InternalStructuredTextParser.g:2887:1: rule__Var_Decl_Local__Group__1__Impl : ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? ) ;
    public final void rule__Var_Decl_Local__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2891:1: ( ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? ) )
            // InternalStructuredTextParser.g:2892:1: ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? )
            {
            // InternalStructuredTextParser.g:2892:1: ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? )
            // InternalStructuredTextParser.g:2893:2: ( rule__Var_Decl_Local__ConstantAssignment_1 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getConstantAssignment_1()); 
            // InternalStructuredTextParser.g:2894:2: ( rule__Var_Decl_Local__ConstantAssignment_1 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==CONSTANT) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalStructuredTextParser.g:2894:3: rule__Var_Decl_Local__ConstantAssignment_1
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
    // InternalStructuredTextParser.g:2902:1: rule__Var_Decl_Local__Group__2 : rule__Var_Decl_Local__Group__2__Impl rule__Var_Decl_Local__Group__3 ;
    public final void rule__Var_Decl_Local__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2906:1: ( rule__Var_Decl_Local__Group__2__Impl rule__Var_Decl_Local__Group__3 )
            // InternalStructuredTextParser.g:2907:2: rule__Var_Decl_Local__Group__2__Impl rule__Var_Decl_Local__Group__3
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
    // InternalStructuredTextParser.g:2914:1: rule__Var_Decl_Local__Group__2__Impl : ( ( rule__Var_Decl_Local__NameAssignment_2 ) ) ;
    public final void rule__Var_Decl_Local__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2918:1: ( ( ( rule__Var_Decl_Local__NameAssignment_2 ) ) )
            // InternalStructuredTextParser.g:2919:1: ( ( rule__Var_Decl_Local__NameAssignment_2 ) )
            {
            // InternalStructuredTextParser.g:2919:1: ( ( rule__Var_Decl_Local__NameAssignment_2 ) )
            // InternalStructuredTextParser.g:2920:2: ( rule__Var_Decl_Local__NameAssignment_2 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getNameAssignment_2()); 
            // InternalStructuredTextParser.g:2921:2: ( rule__Var_Decl_Local__NameAssignment_2 )
            // InternalStructuredTextParser.g:2921:3: rule__Var_Decl_Local__NameAssignment_2
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
    // InternalStructuredTextParser.g:2929:1: rule__Var_Decl_Local__Group__3 : rule__Var_Decl_Local__Group__3__Impl rule__Var_Decl_Local__Group__4 ;
    public final void rule__Var_Decl_Local__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2933:1: ( rule__Var_Decl_Local__Group__3__Impl rule__Var_Decl_Local__Group__4 )
            // InternalStructuredTextParser.g:2934:2: rule__Var_Decl_Local__Group__3__Impl rule__Var_Decl_Local__Group__4
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
    // InternalStructuredTextParser.g:2941:1: rule__Var_Decl_Local__Group__3__Impl : ( ( rule__Var_Decl_Local__Group_3__0 )? ) ;
    public final void rule__Var_Decl_Local__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2945:1: ( ( ( rule__Var_Decl_Local__Group_3__0 )? ) )
            // InternalStructuredTextParser.g:2946:1: ( ( rule__Var_Decl_Local__Group_3__0 )? )
            {
            // InternalStructuredTextParser.g:2946:1: ( ( rule__Var_Decl_Local__Group_3__0 )? )
            // InternalStructuredTextParser.g:2947:2: ( rule__Var_Decl_Local__Group_3__0 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getGroup_3()); 
            // InternalStructuredTextParser.g:2948:2: ( rule__Var_Decl_Local__Group_3__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==AT) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalStructuredTextParser.g:2948:3: rule__Var_Decl_Local__Group_3__0
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
    // InternalStructuredTextParser.g:2956:1: rule__Var_Decl_Local__Group__4 : rule__Var_Decl_Local__Group__4__Impl rule__Var_Decl_Local__Group__5 ;
    public final void rule__Var_Decl_Local__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2960:1: ( rule__Var_Decl_Local__Group__4__Impl rule__Var_Decl_Local__Group__5 )
            // InternalStructuredTextParser.g:2961:2: rule__Var_Decl_Local__Group__4__Impl rule__Var_Decl_Local__Group__5
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
    // InternalStructuredTextParser.g:2968:1: rule__Var_Decl_Local__Group__4__Impl : ( Colon ) ;
    public final void rule__Var_Decl_Local__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2972:1: ( ( Colon ) )
            // InternalStructuredTextParser.g:2973:1: ( Colon )
            {
            // InternalStructuredTextParser.g:2973:1: ( Colon )
            // InternalStructuredTextParser.g:2974:2: Colon
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
    // InternalStructuredTextParser.g:2983:1: rule__Var_Decl_Local__Group__5 : rule__Var_Decl_Local__Group__5__Impl rule__Var_Decl_Local__Group__6 ;
    public final void rule__Var_Decl_Local__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2987:1: ( rule__Var_Decl_Local__Group__5__Impl rule__Var_Decl_Local__Group__6 )
            // InternalStructuredTextParser.g:2988:2: rule__Var_Decl_Local__Group__5__Impl rule__Var_Decl_Local__Group__6
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
    // InternalStructuredTextParser.g:2995:1: rule__Var_Decl_Local__Group__5__Impl : ( ( rule__Var_Decl_Local__Group_5__0 )? ) ;
    public final void rule__Var_Decl_Local__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2999:1: ( ( ( rule__Var_Decl_Local__Group_5__0 )? ) )
            // InternalStructuredTextParser.g:3000:1: ( ( rule__Var_Decl_Local__Group_5__0 )? )
            {
            // InternalStructuredTextParser.g:3000:1: ( ( rule__Var_Decl_Local__Group_5__0 )? )
            // InternalStructuredTextParser.g:3001:2: ( rule__Var_Decl_Local__Group_5__0 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getGroup_5()); 
            // InternalStructuredTextParser.g:3002:2: ( rule__Var_Decl_Local__Group_5__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==ARRAY) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalStructuredTextParser.g:3002:3: rule__Var_Decl_Local__Group_5__0
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
    // InternalStructuredTextParser.g:3010:1: rule__Var_Decl_Local__Group__6 : rule__Var_Decl_Local__Group__6__Impl rule__Var_Decl_Local__Group__7 ;
    public final void rule__Var_Decl_Local__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3014:1: ( rule__Var_Decl_Local__Group__6__Impl rule__Var_Decl_Local__Group__7 )
            // InternalStructuredTextParser.g:3015:2: rule__Var_Decl_Local__Group__6__Impl rule__Var_Decl_Local__Group__7
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
    // InternalStructuredTextParser.g:3022:1: rule__Var_Decl_Local__Group__6__Impl : ( ( rule__Var_Decl_Local__TypeAssignment_6 ) ) ;
    public final void rule__Var_Decl_Local__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3026:1: ( ( ( rule__Var_Decl_Local__TypeAssignment_6 ) ) )
            // InternalStructuredTextParser.g:3027:1: ( ( rule__Var_Decl_Local__TypeAssignment_6 ) )
            {
            // InternalStructuredTextParser.g:3027:1: ( ( rule__Var_Decl_Local__TypeAssignment_6 ) )
            // InternalStructuredTextParser.g:3028:2: ( rule__Var_Decl_Local__TypeAssignment_6 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getTypeAssignment_6()); 
            // InternalStructuredTextParser.g:3029:2: ( rule__Var_Decl_Local__TypeAssignment_6 )
            // InternalStructuredTextParser.g:3029:3: rule__Var_Decl_Local__TypeAssignment_6
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
    // InternalStructuredTextParser.g:3037:1: rule__Var_Decl_Local__Group__7 : rule__Var_Decl_Local__Group__7__Impl ;
    public final void rule__Var_Decl_Local__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3041:1: ( rule__Var_Decl_Local__Group__7__Impl )
            // InternalStructuredTextParser.g:3042:2: rule__Var_Decl_Local__Group__7__Impl
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
    // InternalStructuredTextParser.g:3048:1: rule__Var_Decl_Local__Group__7__Impl : ( ( rule__Var_Decl_Local__Group_7__0 )? ) ;
    public final void rule__Var_Decl_Local__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3052:1: ( ( ( rule__Var_Decl_Local__Group_7__0 )? ) )
            // InternalStructuredTextParser.g:3053:1: ( ( rule__Var_Decl_Local__Group_7__0 )? )
            {
            // InternalStructuredTextParser.g:3053:1: ( ( rule__Var_Decl_Local__Group_7__0 )? )
            // InternalStructuredTextParser.g:3054:2: ( rule__Var_Decl_Local__Group_7__0 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getGroup_7()); 
            // InternalStructuredTextParser.g:3055:2: ( rule__Var_Decl_Local__Group_7__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==ColonEqualsSign) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalStructuredTextParser.g:3055:3: rule__Var_Decl_Local__Group_7__0
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
    // InternalStructuredTextParser.g:3064:1: rule__Var_Decl_Local__Group_3__0 : rule__Var_Decl_Local__Group_3__0__Impl rule__Var_Decl_Local__Group_3__1 ;
    public final void rule__Var_Decl_Local__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3068:1: ( rule__Var_Decl_Local__Group_3__0__Impl rule__Var_Decl_Local__Group_3__1 )
            // InternalStructuredTextParser.g:3069:2: rule__Var_Decl_Local__Group_3__0__Impl rule__Var_Decl_Local__Group_3__1
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
    // InternalStructuredTextParser.g:3076:1: rule__Var_Decl_Local__Group_3__0__Impl : ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) ) ;
    public final void rule__Var_Decl_Local__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3080:1: ( ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) ) )
            // InternalStructuredTextParser.g:3081:1: ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) )
            {
            // InternalStructuredTextParser.g:3081:1: ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) )
            // InternalStructuredTextParser.g:3082:2: ( rule__Var_Decl_Local__LocatedAssignment_3_0 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocatedAssignment_3_0()); 
            // InternalStructuredTextParser.g:3083:2: ( rule__Var_Decl_Local__LocatedAssignment_3_0 )
            // InternalStructuredTextParser.g:3083:3: rule__Var_Decl_Local__LocatedAssignment_3_0
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
    // InternalStructuredTextParser.g:3091:1: rule__Var_Decl_Local__Group_3__1 : rule__Var_Decl_Local__Group_3__1__Impl ;
    public final void rule__Var_Decl_Local__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3095:1: ( rule__Var_Decl_Local__Group_3__1__Impl )
            // InternalStructuredTextParser.g:3096:2: rule__Var_Decl_Local__Group_3__1__Impl
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
    // InternalStructuredTextParser.g:3102:1: rule__Var_Decl_Local__Group_3__1__Impl : ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) ) ;
    public final void rule__Var_Decl_Local__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3106:1: ( ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) ) )
            // InternalStructuredTextParser.g:3107:1: ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) )
            {
            // InternalStructuredTextParser.g:3107:1: ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) )
            // InternalStructuredTextParser.g:3108:2: ( rule__Var_Decl_Local__LocationAssignment_3_1 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocationAssignment_3_1()); 
            // InternalStructuredTextParser.g:3109:2: ( rule__Var_Decl_Local__LocationAssignment_3_1 )
            // InternalStructuredTextParser.g:3109:3: rule__Var_Decl_Local__LocationAssignment_3_1
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
    // InternalStructuredTextParser.g:3118:1: rule__Var_Decl_Local__Group_5__0 : rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1 ;
    public final void rule__Var_Decl_Local__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3122:1: ( rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1 )
            // InternalStructuredTextParser.g:3123:2: rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1
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
    // InternalStructuredTextParser.g:3130:1: rule__Var_Decl_Local__Group_5__0__Impl : ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) ) ;
    public final void rule__Var_Decl_Local__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3134:1: ( ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) ) )
            // InternalStructuredTextParser.g:3135:1: ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) )
            {
            // InternalStructuredTextParser.g:3135:1: ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) )
            // InternalStructuredTextParser.g:3136:2: ( rule__Var_Decl_Local__ArrayAssignment_5_0 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayAssignment_5_0()); 
            // InternalStructuredTextParser.g:3137:2: ( rule__Var_Decl_Local__ArrayAssignment_5_0 )
            // InternalStructuredTextParser.g:3137:3: rule__Var_Decl_Local__ArrayAssignment_5_0
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
    // InternalStructuredTextParser.g:3145:1: rule__Var_Decl_Local__Group_5__1 : rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2 ;
    public final void rule__Var_Decl_Local__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3149:1: ( rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2 )
            // InternalStructuredTextParser.g:3150:2: rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2
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
    // InternalStructuredTextParser.g:3157:1: rule__Var_Decl_Local__Group_5__1__Impl : ( LeftSquareBracket ) ;
    public final void rule__Var_Decl_Local__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3161:1: ( ( LeftSquareBracket ) )
            // InternalStructuredTextParser.g:3162:1: ( LeftSquareBracket )
            {
            // InternalStructuredTextParser.g:3162:1: ( LeftSquareBracket )
            // InternalStructuredTextParser.g:3163:2: LeftSquareBracket
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
    // InternalStructuredTextParser.g:3172:1: rule__Var_Decl_Local__Group_5__2 : rule__Var_Decl_Local__Group_5__2__Impl rule__Var_Decl_Local__Group_5__3 ;
    public final void rule__Var_Decl_Local__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3176:1: ( rule__Var_Decl_Local__Group_5__2__Impl rule__Var_Decl_Local__Group_5__3 )
            // InternalStructuredTextParser.g:3177:2: rule__Var_Decl_Local__Group_5__2__Impl rule__Var_Decl_Local__Group_5__3
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
    // InternalStructuredTextParser.g:3184:1: rule__Var_Decl_Local__Group_5__2__Impl : ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) ) ;
    public final void rule__Var_Decl_Local__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3188:1: ( ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) ) )
            // InternalStructuredTextParser.g:3189:1: ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) )
            {
            // InternalStructuredTextParser.g:3189:1: ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) )
            // InternalStructuredTextParser.g:3190:2: ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayStartAssignment_5_2()); 
            // InternalStructuredTextParser.g:3191:2: ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 )
            // InternalStructuredTextParser.g:3191:3: rule__Var_Decl_Local__ArrayStartAssignment_5_2
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
    // InternalStructuredTextParser.g:3199:1: rule__Var_Decl_Local__Group_5__3 : rule__Var_Decl_Local__Group_5__3__Impl rule__Var_Decl_Local__Group_5__4 ;
    public final void rule__Var_Decl_Local__Group_5__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3203:1: ( rule__Var_Decl_Local__Group_5__3__Impl rule__Var_Decl_Local__Group_5__4 )
            // InternalStructuredTextParser.g:3204:2: rule__Var_Decl_Local__Group_5__3__Impl rule__Var_Decl_Local__Group_5__4
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
    // InternalStructuredTextParser.g:3211:1: rule__Var_Decl_Local__Group_5__3__Impl : ( FullStopFullStop ) ;
    public final void rule__Var_Decl_Local__Group_5__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3215:1: ( ( FullStopFullStop ) )
            // InternalStructuredTextParser.g:3216:1: ( FullStopFullStop )
            {
            // InternalStructuredTextParser.g:3216:1: ( FullStopFullStop )
            // InternalStructuredTextParser.g:3217:2: FullStopFullStop
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
    // InternalStructuredTextParser.g:3226:1: rule__Var_Decl_Local__Group_5__4 : rule__Var_Decl_Local__Group_5__4__Impl rule__Var_Decl_Local__Group_5__5 ;
    public final void rule__Var_Decl_Local__Group_5__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3230:1: ( rule__Var_Decl_Local__Group_5__4__Impl rule__Var_Decl_Local__Group_5__5 )
            // InternalStructuredTextParser.g:3231:2: rule__Var_Decl_Local__Group_5__4__Impl rule__Var_Decl_Local__Group_5__5
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
    // InternalStructuredTextParser.g:3238:1: rule__Var_Decl_Local__Group_5__4__Impl : ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) ) ;
    public final void rule__Var_Decl_Local__Group_5__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3242:1: ( ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) ) )
            // InternalStructuredTextParser.g:3243:1: ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) )
            {
            // InternalStructuredTextParser.g:3243:1: ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) )
            // InternalStructuredTextParser.g:3244:2: ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayStopAssignment_5_4()); 
            // InternalStructuredTextParser.g:3245:2: ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 )
            // InternalStructuredTextParser.g:3245:3: rule__Var_Decl_Local__ArrayStopAssignment_5_4
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
    // InternalStructuredTextParser.g:3253:1: rule__Var_Decl_Local__Group_5__5 : rule__Var_Decl_Local__Group_5__5__Impl rule__Var_Decl_Local__Group_5__6 ;
    public final void rule__Var_Decl_Local__Group_5__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3257:1: ( rule__Var_Decl_Local__Group_5__5__Impl rule__Var_Decl_Local__Group_5__6 )
            // InternalStructuredTextParser.g:3258:2: rule__Var_Decl_Local__Group_5__5__Impl rule__Var_Decl_Local__Group_5__6
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
    // InternalStructuredTextParser.g:3265:1: rule__Var_Decl_Local__Group_5__5__Impl : ( RightSquareBracket ) ;
    public final void rule__Var_Decl_Local__Group_5__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3269:1: ( ( RightSquareBracket ) )
            // InternalStructuredTextParser.g:3270:1: ( RightSquareBracket )
            {
            // InternalStructuredTextParser.g:3270:1: ( RightSquareBracket )
            // InternalStructuredTextParser.g:3271:2: RightSquareBracket
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
    // InternalStructuredTextParser.g:3280:1: rule__Var_Decl_Local__Group_5__6 : rule__Var_Decl_Local__Group_5__6__Impl ;
    public final void rule__Var_Decl_Local__Group_5__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3284:1: ( rule__Var_Decl_Local__Group_5__6__Impl )
            // InternalStructuredTextParser.g:3285:2: rule__Var_Decl_Local__Group_5__6__Impl
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
    // InternalStructuredTextParser.g:3291:1: rule__Var_Decl_Local__Group_5__6__Impl : ( OF ) ;
    public final void rule__Var_Decl_Local__Group_5__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3295:1: ( ( OF ) )
            // InternalStructuredTextParser.g:3296:1: ( OF )
            {
            // InternalStructuredTextParser.g:3296:1: ( OF )
            // InternalStructuredTextParser.g:3297:2: OF
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
    // InternalStructuredTextParser.g:3307:1: rule__Var_Decl_Local__Group_7__0 : rule__Var_Decl_Local__Group_7__0__Impl rule__Var_Decl_Local__Group_7__1 ;
    public final void rule__Var_Decl_Local__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3311:1: ( rule__Var_Decl_Local__Group_7__0__Impl rule__Var_Decl_Local__Group_7__1 )
            // InternalStructuredTextParser.g:3312:2: rule__Var_Decl_Local__Group_7__0__Impl rule__Var_Decl_Local__Group_7__1
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
    // InternalStructuredTextParser.g:3319:1: rule__Var_Decl_Local__Group_7__0__Impl : ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) ) ;
    public final void rule__Var_Decl_Local__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3323:1: ( ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) ) )
            // InternalStructuredTextParser.g:3324:1: ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) )
            {
            // InternalStructuredTextParser.g:3324:1: ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) )
            // InternalStructuredTextParser.g:3325:2: ( rule__Var_Decl_Local__InitalizedAssignment_7_0 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitalizedAssignment_7_0()); 
            // InternalStructuredTextParser.g:3326:2: ( rule__Var_Decl_Local__InitalizedAssignment_7_0 )
            // InternalStructuredTextParser.g:3326:3: rule__Var_Decl_Local__InitalizedAssignment_7_0
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
    // InternalStructuredTextParser.g:3334:1: rule__Var_Decl_Local__Group_7__1 : rule__Var_Decl_Local__Group_7__1__Impl ;
    public final void rule__Var_Decl_Local__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3338:1: ( rule__Var_Decl_Local__Group_7__1__Impl )
            // InternalStructuredTextParser.g:3339:2: rule__Var_Decl_Local__Group_7__1__Impl
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
    // InternalStructuredTextParser.g:3345:1: rule__Var_Decl_Local__Group_7__1__Impl : ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) ) ;
    public final void rule__Var_Decl_Local__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3349:1: ( ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) ) )
            // InternalStructuredTextParser.g:3350:1: ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) )
            {
            // InternalStructuredTextParser.g:3350:1: ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) )
            // InternalStructuredTextParser.g:3351:2: ( rule__Var_Decl_Local__InitialValueAssignment_7_1 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitialValueAssignment_7_1()); 
            // InternalStructuredTextParser.g:3352:2: ( rule__Var_Decl_Local__InitialValueAssignment_7_1 )
            // InternalStructuredTextParser.g:3352:3: rule__Var_Decl_Local__InitialValueAssignment_7_1
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
    // InternalStructuredTextParser.g:3361:1: rule__Stmt_List__Group__0 : rule__Stmt_List__Group__0__Impl rule__Stmt_List__Group__1 ;
    public final void rule__Stmt_List__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3365:1: ( rule__Stmt_List__Group__0__Impl rule__Stmt_List__Group__1 )
            // InternalStructuredTextParser.g:3366:2: rule__Stmt_List__Group__0__Impl rule__Stmt_List__Group__1
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
    // InternalStructuredTextParser.g:3373:1: rule__Stmt_List__Group__0__Impl : ( () ) ;
    public final void rule__Stmt_List__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3377:1: ( ( () ) )
            // InternalStructuredTextParser.g:3378:1: ( () )
            {
            // InternalStructuredTextParser.g:3378:1: ( () )
            // InternalStructuredTextParser.g:3379:2: ()
            {
             before(grammarAccess.getStmt_ListAccess().getStatementListAction_0()); 
            // InternalStructuredTextParser.g:3380:2: ()
            // InternalStructuredTextParser.g:3380:3: 
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
    // InternalStructuredTextParser.g:3388:1: rule__Stmt_List__Group__1 : rule__Stmt_List__Group__1__Impl ;
    public final void rule__Stmt_List__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3392:1: ( rule__Stmt_List__Group__1__Impl )
            // InternalStructuredTextParser.g:3393:2: rule__Stmt_List__Group__1__Impl
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
    // InternalStructuredTextParser.g:3399:1: rule__Stmt_List__Group__1__Impl : ( ( rule__Stmt_List__Group_1__0 )* ) ;
    public final void rule__Stmt_List__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3403:1: ( ( ( rule__Stmt_List__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:3404:1: ( ( rule__Stmt_List__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:3404:1: ( ( rule__Stmt_List__Group_1__0 )* )
            // InternalStructuredTextParser.g:3405:2: ( rule__Stmt_List__Group_1__0 )*
            {
             before(grammarAccess.getStmt_ListAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:3406:2: ( rule__Stmt_List__Group_1__0 )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==CONTINUE||(LA36_0>=REPEAT && LA36_0<=RETURN)||LA36_0==SUPER||LA36_0==WHILE||LA36_0==CASE||LA36_0==EXIT||LA36_0==TIME||LA36_0==FOR||(LA36_0>=DT && LA36_0<=LT)||LA36_0==Semicolon||LA36_0==T||LA36_0==RULE_ID) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalStructuredTextParser.g:3406:3: rule__Stmt_List__Group_1__0
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
    // InternalStructuredTextParser.g:3415:1: rule__Stmt_List__Group_1__0 : rule__Stmt_List__Group_1__0__Impl rule__Stmt_List__Group_1__1 ;
    public final void rule__Stmt_List__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3419:1: ( rule__Stmt_List__Group_1__0__Impl rule__Stmt_List__Group_1__1 )
            // InternalStructuredTextParser.g:3420:2: rule__Stmt_List__Group_1__0__Impl rule__Stmt_List__Group_1__1
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
    // InternalStructuredTextParser.g:3427:1: rule__Stmt_List__Group_1__0__Impl : ( ( rule__Stmt_List__StatementsAssignment_1_0 )? ) ;
    public final void rule__Stmt_List__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3431:1: ( ( ( rule__Stmt_List__StatementsAssignment_1_0 )? ) )
            // InternalStructuredTextParser.g:3432:1: ( ( rule__Stmt_List__StatementsAssignment_1_0 )? )
            {
            // InternalStructuredTextParser.g:3432:1: ( ( rule__Stmt_List__StatementsAssignment_1_0 )? )
            // InternalStructuredTextParser.g:3433:2: ( rule__Stmt_List__StatementsAssignment_1_0 )?
            {
             before(grammarAccess.getStmt_ListAccess().getStatementsAssignment_1_0()); 
            // InternalStructuredTextParser.g:3434:2: ( rule__Stmt_List__StatementsAssignment_1_0 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==CONTINUE||(LA37_0>=REPEAT && LA37_0<=RETURN)||LA37_0==SUPER||LA37_0==WHILE||LA37_0==CASE||LA37_0==EXIT||LA37_0==TIME||LA37_0==FOR||(LA37_0>=DT && LA37_0<=LT)||LA37_0==T||LA37_0==RULE_ID) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalStructuredTextParser.g:3434:3: rule__Stmt_List__StatementsAssignment_1_0
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
    // InternalStructuredTextParser.g:3442:1: rule__Stmt_List__Group_1__1 : rule__Stmt_List__Group_1__1__Impl ;
    public final void rule__Stmt_List__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3446:1: ( rule__Stmt_List__Group_1__1__Impl )
            // InternalStructuredTextParser.g:3447:2: rule__Stmt_List__Group_1__1__Impl
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
    // InternalStructuredTextParser.g:3453:1: rule__Stmt_List__Group_1__1__Impl : ( Semicolon ) ;
    public final void rule__Stmt_List__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3457:1: ( ( Semicolon ) )
            // InternalStructuredTextParser.g:3458:1: ( Semicolon )
            {
            // InternalStructuredTextParser.g:3458:1: ( Semicolon )
            // InternalStructuredTextParser.g:3459:2: Semicolon
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
    // InternalStructuredTextParser.g:3469:1: rule__Assign_Stmt__Group__0 : rule__Assign_Stmt__Group__0__Impl rule__Assign_Stmt__Group__1 ;
    public final void rule__Assign_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3473:1: ( rule__Assign_Stmt__Group__0__Impl rule__Assign_Stmt__Group__1 )
            // InternalStructuredTextParser.g:3474:2: rule__Assign_Stmt__Group__0__Impl rule__Assign_Stmt__Group__1
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
    // InternalStructuredTextParser.g:3481:1: rule__Assign_Stmt__Group__0__Impl : ( ( rule__Assign_Stmt__VariableAssignment_0 ) ) ;
    public final void rule__Assign_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3485:1: ( ( ( rule__Assign_Stmt__VariableAssignment_0 ) ) )
            // InternalStructuredTextParser.g:3486:1: ( ( rule__Assign_Stmt__VariableAssignment_0 ) )
            {
            // InternalStructuredTextParser.g:3486:1: ( ( rule__Assign_Stmt__VariableAssignment_0 ) )
            // InternalStructuredTextParser.g:3487:2: ( rule__Assign_Stmt__VariableAssignment_0 )
            {
             before(grammarAccess.getAssign_StmtAccess().getVariableAssignment_0()); 
            // InternalStructuredTextParser.g:3488:2: ( rule__Assign_Stmt__VariableAssignment_0 )
            // InternalStructuredTextParser.g:3488:3: rule__Assign_Stmt__VariableAssignment_0
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
    // InternalStructuredTextParser.g:3496:1: rule__Assign_Stmt__Group__1 : rule__Assign_Stmt__Group__1__Impl rule__Assign_Stmt__Group__2 ;
    public final void rule__Assign_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3500:1: ( rule__Assign_Stmt__Group__1__Impl rule__Assign_Stmt__Group__2 )
            // InternalStructuredTextParser.g:3501:2: rule__Assign_Stmt__Group__1__Impl rule__Assign_Stmt__Group__2
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
    // InternalStructuredTextParser.g:3508:1: rule__Assign_Stmt__Group__1__Impl : ( ColonEqualsSign ) ;
    public final void rule__Assign_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3512:1: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:3513:1: ( ColonEqualsSign )
            {
            // InternalStructuredTextParser.g:3513:1: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:3514:2: ColonEqualsSign
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
    // InternalStructuredTextParser.g:3523:1: rule__Assign_Stmt__Group__2 : rule__Assign_Stmt__Group__2__Impl ;
    public final void rule__Assign_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3527:1: ( rule__Assign_Stmt__Group__2__Impl )
            // InternalStructuredTextParser.g:3528:2: rule__Assign_Stmt__Group__2__Impl
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
    // InternalStructuredTextParser.g:3534:1: rule__Assign_Stmt__Group__2__Impl : ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) ) ;
    public final void rule__Assign_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3538:1: ( ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) ) )
            // InternalStructuredTextParser.g:3539:1: ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) )
            {
            // InternalStructuredTextParser.g:3539:1: ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) )
            // InternalStructuredTextParser.g:3540:2: ( rule__Assign_Stmt__ExpressionAssignment_2 )
            {
             before(grammarAccess.getAssign_StmtAccess().getExpressionAssignment_2()); 
            // InternalStructuredTextParser.g:3541:2: ( rule__Assign_Stmt__ExpressionAssignment_2 )
            // InternalStructuredTextParser.g:3541:3: rule__Assign_Stmt__ExpressionAssignment_2
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
    // InternalStructuredTextParser.g:3550:1: rule__Subprog_Ctrl_Stmt__Group_1__0 : rule__Subprog_Ctrl_Stmt__Group_1__0__Impl rule__Subprog_Ctrl_Stmt__Group_1__1 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3554:1: ( rule__Subprog_Ctrl_Stmt__Group_1__0__Impl rule__Subprog_Ctrl_Stmt__Group_1__1 )
            // InternalStructuredTextParser.g:3555:2: rule__Subprog_Ctrl_Stmt__Group_1__0__Impl rule__Subprog_Ctrl_Stmt__Group_1__1
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
    // InternalStructuredTextParser.g:3562:1: rule__Subprog_Ctrl_Stmt__Group_1__0__Impl : ( () ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3566:1: ( ( () ) )
            // InternalStructuredTextParser.g:3567:1: ( () )
            {
            // InternalStructuredTextParser.g:3567:1: ( () )
            // InternalStructuredTextParser.g:3568:2: ()
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getSuperStatementAction_1_0()); 
            // InternalStructuredTextParser.g:3569:2: ()
            // InternalStructuredTextParser.g:3569:3: 
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
    // InternalStructuredTextParser.g:3577:1: rule__Subprog_Ctrl_Stmt__Group_1__1 : rule__Subprog_Ctrl_Stmt__Group_1__1__Impl rule__Subprog_Ctrl_Stmt__Group_1__2 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3581:1: ( rule__Subprog_Ctrl_Stmt__Group_1__1__Impl rule__Subprog_Ctrl_Stmt__Group_1__2 )
            // InternalStructuredTextParser.g:3582:2: rule__Subprog_Ctrl_Stmt__Group_1__1__Impl rule__Subprog_Ctrl_Stmt__Group_1__2
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
    // InternalStructuredTextParser.g:3589:1: rule__Subprog_Ctrl_Stmt__Group_1__1__Impl : ( SUPER ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3593:1: ( ( SUPER ) )
            // InternalStructuredTextParser.g:3594:1: ( SUPER )
            {
            // InternalStructuredTextParser.g:3594:1: ( SUPER )
            // InternalStructuredTextParser.g:3595:2: SUPER
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
    // InternalStructuredTextParser.g:3604:1: rule__Subprog_Ctrl_Stmt__Group_1__2 : rule__Subprog_Ctrl_Stmt__Group_1__2__Impl rule__Subprog_Ctrl_Stmt__Group_1__3 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3608:1: ( rule__Subprog_Ctrl_Stmt__Group_1__2__Impl rule__Subprog_Ctrl_Stmt__Group_1__3 )
            // InternalStructuredTextParser.g:3609:2: rule__Subprog_Ctrl_Stmt__Group_1__2__Impl rule__Subprog_Ctrl_Stmt__Group_1__3
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
    // InternalStructuredTextParser.g:3616:1: rule__Subprog_Ctrl_Stmt__Group_1__2__Impl : ( LeftParenthesis ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3620:1: ( ( LeftParenthesis ) )
            // InternalStructuredTextParser.g:3621:1: ( LeftParenthesis )
            {
            // InternalStructuredTextParser.g:3621:1: ( LeftParenthesis )
            // InternalStructuredTextParser.g:3622:2: LeftParenthesis
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
    // InternalStructuredTextParser.g:3631:1: rule__Subprog_Ctrl_Stmt__Group_1__3 : rule__Subprog_Ctrl_Stmt__Group_1__3__Impl ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3635:1: ( rule__Subprog_Ctrl_Stmt__Group_1__3__Impl )
            // InternalStructuredTextParser.g:3636:2: rule__Subprog_Ctrl_Stmt__Group_1__3__Impl
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
    // InternalStructuredTextParser.g:3642:1: rule__Subprog_Ctrl_Stmt__Group_1__3__Impl : ( RightParenthesis ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3646:1: ( ( RightParenthesis ) )
            // InternalStructuredTextParser.g:3647:1: ( RightParenthesis )
            {
            // InternalStructuredTextParser.g:3647:1: ( RightParenthesis )
            // InternalStructuredTextParser.g:3648:2: RightParenthesis
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
    // InternalStructuredTextParser.g:3658:1: rule__Subprog_Ctrl_Stmt__Group_2__0 : rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3662:1: ( rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1 )
            // InternalStructuredTextParser.g:3663:2: rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1
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
    // InternalStructuredTextParser.g:3670:1: rule__Subprog_Ctrl_Stmt__Group_2__0__Impl : ( () ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3674:1: ( ( () ) )
            // InternalStructuredTextParser.g:3675:1: ( () )
            {
            // InternalStructuredTextParser.g:3675:1: ( () )
            // InternalStructuredTextParser.g:3676:2: ()
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getReturnStatementAction_2_0()); 
            // InternalStructuredTextParser.g:3677:2: ()
            // InternalStructuredTextParser.g:3677:3: 
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
    // InternalStructuredTextParser.g:3685:1: rule__Subprog_Ctrl_Stmt__Group_2__1 : rule__Subprog_Ctrl_Stmt__Group_2__1__Impl ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3689:1: ( rule__Subprog_Ctrl_Stmt__Group_2__1__Impl )
            // InternalStructuredTextParser.g:3690:2: rule__Subprog_Ctrl_Stmt__Group_2__1__Impl
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
    // InternalStructuredTextParser.g:3696:1: rule__Subprog_Ctrl_Stmt__Group_2__1__Impl : ( RETURN ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3700:1: ( ( RETURN ) )
            // InternalStructuredTextParser.g:3701:1: ( RETURN )
            {
            // InternalStructuredTextParser.g:3701:1: ( RETURN )
            // InternalStructuredTextParser.g:3702:2: RETURN
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
    // InternalStructuredTextParser.g:3712:1: rule__IF_Stmt__Group__0 : rule__IF_Stmt__Group__0__Impl rule__IF_Stmt__Group__1 ;
    public final void rule__IF_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3716:1: ( rule__IF_Stmt__Group__0__Impl rule__IF_Stmt__Group__1 )
            // InternalStructuredTextParser.g:3717:2: rule__IF_Stmt__Group__0__Impl rule__IF_Stmt__Group__1
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
    // InternalStructuredTextParser.g:3724:1: rule__IF_Stmt__Group__0__Impl : ( IF ) ;
    public final void rule__IF_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3728:1: ( ( IF ) )
            // InternalStructuredTextParser.g:3729:1: ( IF )
            {
            // InternalStructuredTextParser.g:3729:1: ( IF )
            // InternalStructuredTextParser.g:3730:2: IF
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
    // InternalStructuredTextParser.g:3739:1: rule__IF_Stmt__Group__1 : rule__IF_Stmt__Group__1__Impl rule__IF_Stmt__Group__2 ;
    public final void rule__IF_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3743:1: ( rule__IF_Stmt__Group__1__Impl rule__IF_Stmt__Group__2 )
            // InternalStructuredTextParser.g:3744:2: rule__IF_Stmt__Group__1__Impl rule__IF_Stmt__Group__2
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
    // InternalStructuredTextParser.g:3751:1: rule__IF_Stmt__Group__1__Impl : ( ( rule__IF_Stmt__ExpressionAssignment_1 ) ) ;
    public final void rule__IF_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3755:1: ( ( ( rule__IF_Stmt__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:3756:1: ( ( rule__IF_Stmt__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:3756:1: ( ( rule__IF_Stmt__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:3757:2: ( rule__IF_Stmt__ExpressionAssignment_1 )
            {
             before(grammarAccess.getIF_StmtAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:3758:2: ( rule__IF_Stmt__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:3758:3: rule__IF_Stmt__ExpressionAssignment_1
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
    // InternalStructuredTextParser.g:3766:1: rule__IF_Stmt__Group__2 : rule__IF_Stmt__Group__2__Impl rule__IF_Stmt__Group__3 ;
    public final void rule__IF_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3770:1: ( rule__IF_Stmt__Group__2__Impl rule__IF_Stmt__Group__3 )
            // InternalStructuredTextParser.g:3771:2: rule__IF_Stmt__Group__2__Impl rule__IF_Stmt__Group__3
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
    // InternalStructuredTextParser.g:3778:1: rule__IF_Stmt__Group__2__Impl : ( THEN ) ;
    public final void rule__IF_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3782:1: ( ( THEN ) )
            // InternalStructuredTextParser.g:3783:1: ( THEN )
            {
            // InternalStructuredTextParser.g:3783:1: ( THEN )
            // InternalStructuredTextParser.g:3784:2: THEN
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
    // InternalStructuredTextParser.g:3793:1: rule__IF_Stmt__Group__3 : rule__IF_Stmt__Group__3__Impl rule__IF_Stmt__Group__4 ;
    public final void rule__IF_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3797:1: ( rule__IF_Stmt__Group__3__Impl rule__IF_Stmt__Group__4 )
            // InternalStructuredTextParser.g:3798:2: rule__IF_Stmt__Group__3__Impl rule__IF_Stmt__Group__4
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
    // InternalStructuredTextParser.g:3805:1: rule__IF_Stmt__Group__3__Impl : ( ( rule__IF_Stmt__StatmentsAssignment_3 ) ) ;
    public final void rule__IF_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3809:1: ( ( ( rule__IF_Stmt__StatmentsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:3810:1: ( ( rule__IF_Stmt__StatmentsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:3810:1: ( ( rule__IF_Stmt__StatmentsAssignment_3 ) )
            // InternalStructuredTextParser.g:3811:2: ( rule__IF_Stmt__StatmentsAssignment_3 )
            {
             before(grammarAccess.getIF_StmtAccess().getStatmentsAssignment_3()); 
            // InternalStructuredTextParser.g:3812:2: ( rule__IF_Stmt__StatmentsAssignment_3 )
            // InternalStructuredTextParser.g:3812:3: rule__IF_Stmt__StatmentsAssignment_3
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
    // InternalStructuredTextParser.g:3820:1: rule__IF_Stmt__Group__4 : rule__IF_Stmt__Group__4__Impl rule__IF_Stmt__Group__5 ;
    public final void rule__IF_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3824:1: ( rule__IF_Stmt__Group__4__Impl rule__IF_Stmt__Group__5 )
            // InternalStructuredTextParser.g:3825:2: rule__IF_Stmt__Group__4__Impl rule__IF_Stmt__Group__5
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
    // InternalStructuredTextParser.g:3832:1: rule__IF_Stmt__Group__4__Impl : ( ( rule__IF_Stmt__ElseifAssignment_4 )* ) ;
    public final void rule__IF_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3836:1: ( ( ( rule__IF_Stmt__ElseifAssignment_4 )* ) )
            // InternalStructuredTextParser.g:3837:1: ( ( rule__IF_Stmt__ElseifAssignment_4 )* )
            {
            // InternalStructuredTextParser.g:3837:1: ( ( rule__IF_Stmt__ElseifAssignment_4 )* )
            // InternalStructuredTextParser.g:3838:2: ( rule__IF_Stmt__ElseifAssignment_4 )*
            {
             before(grammarAccess.getIF_StmtAccess().getElseifAssignment_4()); 
            // InternalStructuredTextParser.g:3839:2: ( rule__IF_Stmt__ElseifAssignment_4 )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==ELSIF) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // InternalStructuredTextParser.g:3839:3: rule__IF_Stmt__ElseifAssignment_4
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
    // InternalStructuredTextParser.g:3847:1: rule__IF_Stmt__Group__5 : rule__IF_Stmt__Group__5__Impl rule__IF_Stmt__Group__6 ;
    public final void rule__IF_Stmt__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3851:1: ( rule__IF_Stmt__Group__5__Impl rule__IF_Stmt__Group__6 )
            // InternalStructuredTextParser.g:3852:2: rule__IF_Stmt__Group__5__Impl rule__IF_Stmt__Group__6
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
    // InternalStructuredTextParser.g:3859:1: rule__IF_Stmt__Group__5__Impl : ( ( rule__IF_Stmt__ElseAssignment_5 )? ) ;
    public final void rule__IF_Stmt__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3863:1: ( ( ( rule__IF_Stmt__ElseAssignment_5 )? ) )
            // InternalStructuredTextParser.g:3864:1: ( ( rule__IF_Stmt__ElseAssignment_5 )? )
            {
            // InternalStructuredTextParser.g:3864:1: ( ( rule__IF_Stmt__ElseAssignment_5 )? )
            // InternalStructuredTextParser.g:3865:2: ( rule__IF_Stmt__ElseAssignment_5 )?
            {
             before(grammarAccess.getIF_StmtAccess().getElseAssignment_5()); 
            // InternalStructuredTextParser.g:3866:2: ( rule__IF_Stmt__ElseAssignment_5 )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==ELSE) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // InternalStructuredTextParser.g:3866:3: rule__IF_Stmt__ElseAssignment_5
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
    // InternalStructuredTextParser.g:3874:1: rule__IF_Stmt__Group__6 : rule__IF_Stmt__Group__6__Impl ;
    public final void rule__IF_Stmt__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3878:1: ( rule__IF_Stmt__Group__6__Impl )
            // InternalStructuredTextParser.g:3879:2: rule__IF_Stmt__Group__6__Impl
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
    // InternalStructuredTextParser.g:3885:1: rule__IF_Stmt__Group__6__Impl : ( END_IF ) ;
    public final void rule__IF_Stmt__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3889:1: ( ( END_IF ) )
            // InternalStructuredTextParser.g:3890:1: ( END_IF )
            {
            // InternalStructuredTextParser.g:3890:1: ( END_IF )
            // InternalStructuredTextParser.g:3891:2: END_IF
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
    // InternalStructuredTextParser.g:3901:1: rule__ELSIF_Clause__Group__0 : rule__ELSIF_Clause__Group__0__Impl rule__ELSIF_Clause__Group__1 ;
    public final void rule__ELSIF_Clause__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3905:1: ( rule__ELSIF_Clause__Group__0__Impl rule__ELSIF_Clause__Group__1 )
            // InternalStructuredTextParser.g:3906:2: rule__ELSIF_Clause__Group__0__Impl rule__ELSIF_Clause__Group__1
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
    // InternalStructuredTextParser.g:3913:1: rule__ELSIF_Clause__Group__0__Impl : ( ELSIF ) ;
    public final void rule__ELSIF_Clause__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3917:1: ( ( ELSIF ) )
            // InternalStructuredTextParser.g:3918:1: ( ELSIF )
            {
            // InternalStructuredTextParser.g:3918:1: ( ELSIF )
            // InternalStructuredTextParser.g:3919:2: ELSIF
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
    // InternalStructuredTextParser.g:3928:1: rule__ELSIF_Clause__Group__1 : rule__ELSIF_Clause__Group__1__Impl rule__ELSIF_Clause__Group__2 ;
    public final void rule__ELSIF_Clause__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3932:1: ( rule__ELSIF_Clause__Group__1__Impl rule__ELSIF_Clause__Group__2 )
            // InternalStructuredTextParser.g:3933:2: rule__ELSIF_Clause__Group__1__Impl rule__ELSIF_Clause__Group__2
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
    // InternalStructuredTextParser.g:3940:1: rule__ELSIF_Clause__Group__1__Impl : ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) ) ;
    public final void rule__ELSIF_Clause__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3944:1: ( ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:3945:1: ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:3945:1: ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:3946:2: ( rule__ELSIF_Clause__ExpressionAssignment_1 )
            {
             before(grammarAccess.getELSIF_ClauseAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:3947:2: ( rule__ELSIF_Clause__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:3947:3: rule__ELSIF_Clause__ExpressionAssignment_1
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
    // InternalStructuredTextParser.g:3955:1: rule__ELSIF_Clause__Group__2 : rule__ELSIF_Clause__Group__2__Impl rule__ELSIF_Clause__Group__3 ;
    public final void rule__ELSIF_Clause__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3959:1: ( rule__ELSIF_Clause__Group__2__Impl rule__ELSIF_Clause__Group__3 )
            // InternalStructuredTextParser.g:3960:2: rule__ELSIF_Clause__Group__2__Impl rule__ELSIF_Clause__Group__3
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
    // InternalStructuredTextParser.g:3967:1: rule__ELSIF_Clause__Group__2__Impl : ( THEN ) ;
    public final void rule__ELSIF_Clause__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3971:1: ( ( THEN ) )
            // InternalStructuredTextParser.g:3972:1: ( THEN )
            {
            // InternalStructuredTextParser.g:3972:1: ( THEN )
            // InternalStructuredTextParser.g:3973:2: THEN
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
    // InternalStructuredTextParser.g:3982:1: rule__ELSIF_Clause__Group__3 : rule__ELSIF_Clause__Group__3__Impl ;
    public final void rule__ELSIF_Clause__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3986:1: ( rule__ELSIF_Clause__Group__3__Impl )
            // InternalStructuredTextParser.g:3987:2: rule__ELSIF_Clause__Group__3__Impl
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
    // InternalStructuredTextParser.g:3993:1: rule__ELSIF_Clause__Group__3__Impl : ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) ) ;
    public final void rule__ELSIF_Clause__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3997:1: ( ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:3998:1: ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:3998:1: ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) )
            // InternalStructuredTextParser.g:3999:2: ( rule__ELSIF_Clause__StatementsAssignment_3 )
            {
             before(grammarAccess.getELSIF_ClauseAccess().getStatementsAssignment_3()); 
            // InternalStructuredTextParser.g:4000:2: ( rule__ELSIF_Clause__StatementsAssignment_3 )
            // InternalStructuredTextParser.g:4000:3: rule__ELSIF_Clause__StatementsAssignment_3
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
    // InternalStructuredTextParser.g:4009:1: rule__ELSE_Clause__Group__0 : rule__ELSE_Clause__Group__0__Impl rule__ELSE_Clause__Group__1 ;
    public final void rule__ELSE_Clause__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4013:1: ( rule__ELSE_Clause__Group__0__Impl rule__ELSE_Clause__Group__1 )
            // InternalStructuredTextParser.g:4014:2: rule__ELSE_Clause__Group__0__Impl rule__ELSE_Clause__Group__1
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
    // InternalStructuredTextParser.g:4021:1: rule__ELSE_Clause__Group__0__Impl : ( ELSE ) ;
    public final void rule__ELSE_Clause__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4025:1: ( ( ELSE ) )
            // InternalStructuredTextParser.g:4026:1: ( ELSE )
            {
            // InternalStructuredTextParser.g:4026:1: ( ELSE )
            // InternalStructuredTextParser.g:4027:2: ELSE
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
    // InternalStructuredTextParser.g:4036:1: rule__ELSE_Clause__Group__1 : rule__ELSE_Clause__Group__1__Impl ;
    public final void rule__ELSE_Clause__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4040:1: ( rule__ELSE_Clause__Group__1__Impl )
            // InternalStructuredTextParser.g:4041:2: rule__ELSE_Clause__Group__1__Impl
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
    // InternalStructuredTextParser.g:4047:1: rule__ELSE_Clause__Group__1__Impl : ( ( rule__ELSE_Clause__StatementsAssignment_1 ) ) ;
    public final void rule__ELSE_Clause__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4051:1: ( ( ( rule__ELSE_Clause__StatementsAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4052:1: ( ( rule__ELSE_Clause__StatementsAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4052:1: ( ( rule__ELSE_Clause__StatementsAssignment_1 ) )
            // InternalStructuredTextParser.g:4053:2: ( rule__ELSE_Clause__StatementsAssignment_1 )
            {
             before(grammarAccess.getELSE_ClauseAccess().getStatementsAssignment_1()); 
            // InternalStructuredTextParser.g:4054:2: ( rule__ELSE_Clause__StatementsAssignment_1 )
            // InternalStructuredTextParser.g:4054:3: rule__ELSE_Clause__StatementsAssignment_1
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
    // InternalStructuredTextParser.g:4063:1: rule__Case_Stmt__Group__0 : rule__Case_Stmt__Group__0__Impl rule__Case_Stmt__Group__1 ;
    public final void rule__Case_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4067:1: ( rule__Case_Stmt__Group__0__Impl rule__Case_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4068:2: rule__Case_Stmt__Group__0__Impl rule__Case_Stmt__Group__1
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
    // InternalStructuredTextParser.g:4075:1: rule__Case_Stmt__Group__0__Impl : ( CASE ) ;
    public final void rule__Case_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4079:1: ( ( CASE ) )
            // InternalStructuredTextParser.g:4080:1: ( CASE )
            {
            // InternalStructuredTextParser.g:4080:1: ( CASE )
            // InternalStructuredTextParser.g:4081:2: CASE
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
    // InternalStructuredTextParser.g:4090:1: rule__Case_Stmt__Group__1 : rule__Case_Stmt__Group__1__Impl rule__Case_Stmt__Group__2 ;
    public final void rule__Case_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4094:1: ( rule__Case_Stmt__Group__1__Impl rule__Case_Stmt__Group__2 )
            // InternalStructuredTextParser.g:4095:2: rule__Case_Stmt__Group__1__Impl rule__Case_Stmt__Group__2
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
    // InternalStructuredTextParser.g:4102:1: rule__Case_Stmt__Group__1__Impl : ( ( rule__Case_Stmt__ExpressionAssignment_1 ) ) ;
    public final void rule__Case_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4106:1: ( ( ( rule__Case_Stmt__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4107:1: ( ( rule__Case_Stmt__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4107:1: ( ( rule__Case_Stmt__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:4108:2: ( rule__Case_Stmt__ExpressionAssignment_1 )
            {
             before(grammarAccess.getCase_StmtAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:4109:2: ( rule__Case_Stmt__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:4109:3: rule__Case_Stmt__ExpressionAssignment_1
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
    // InternalStructuredTextParser.g:4117:1: rule__Case_Stmt__Group__2 : rule__Case_Stmt__Group__2__Impl rule__Case_Stmt__Group__3 ;
    public final void rule__Case_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4121:1: ( rule__Case_Stmt__Group__2__Impl rule__Case_Stmt__Group__3 )
            // InternalStructuredTextParser.g:4122:2: rule__Case_Stmt__Group__2__Impl rule__Case_Stmt__Group__3
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
    // InternalStructuredTextParser.g:4129:1: rule__Case_Stmt__Group__2__Impl : ( OF ) ;
    public final void rule__Case_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4133:1: ( ( OF ) )
            // InternalStructuredTextParser.g:4134:1: ( OF )
            {
            // InternalStructuredTextParser.g:4134:1: ( OF )
            // InternalStructuredTextParser.g:4135:2: OF
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
    // InternalStructuredTextParser.g:4144:1: rule__Case_Stmt__Group__3 : rule__Case_Stmt__Group__3__Impl rule__Case_Stmt__Group__4 ;
    public final void rule__Case_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4148:1: ( rule__Case_Stmt__Group__3__Impl rule__Case_Stmt__Group__4 )
            // InternalStructuredTextParser.g:4149:2: rule__Case_Stmt__Group__3__Impl rule__Case_Stmt__Group__4
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
    // InternalStructuredTextParser.g:4156:1: rule__Case_Stmt__Group__3__Impl : ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) ) ;
    public final void rule__Case_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4160:1: ( ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) ) )
            // InternalStructuredTextParser.g:4161:1: ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) )
            {
            // InternalStructuredTextParser.g:4161:1: ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) )
            // InternalStructuredTextParser.g:4162:2: ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* )
            {
            // InternalStructuredTextParser.g:4162:2: ( ( rule__Case_Stmt__CaseAssignment_3 ) )
            // InternalStructuredTextParser.g:4163:3: ( rule__Case_Stmt__CaseAssignment_3 )
            {
             before(grammarAccess.getCase_StmtAccess().getCaseAssignment_3()); 
            // InternalStructuredTextParser.g:4164:3: ( rule__Case_Stmt__CaseAssignment_3 )
            // InternalStructuredTextParser.g:4164:4: rule__Case_Stmt__CaseAssignment_3
            {
            pushFollow(FOLLOW_29);
            rule__Case_Stmt__CaseAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getCase_StmtAccess().getCaseAssignment_3()); 

            }

            // InternalStructuredTextParser.g:4167:2: ( ( rule__Case_Stmt__CaseAssignment_3 )* )
            // InternalStructuredTextParser.g:4168:3: ( rule__Case_Stmt__CaseAssignment_3 )*
            {
             before(grammarAccess.getCase_StmtAccess().getCaseAssignment_3()); 
            // InternalStructuredTextParser.g:4169:3: ( rule__Case_Stmt__CaseAssignment_3 )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==WSTRING||LA40_0==STRING||LA40_0==FALSE||LA40_0==LREAL||(LA40_0>=UDINT && LA40_0<=ULINT)||(LA40_0>=USINT && LA40_0<=WCHAR)||LA40_0==BOOL||LA40_0==CHAR||LA40_0==DINT||LA40_0==LINT||(LA40_0>=REAL && LA40_0<=SINT)||(LA40_0>=TRUE && LA40_0<=UINT)||LA40_0==INT||LA40_0==PlusSign||LA40_0==HyphenMinus||(LA40_0>=RULE_UNSIGNED_INT && LA40_0<=RULE_DATE)||(LA40_0>=RULE_BINARY_INT && LA40_0<=RULE_HEX_INT)||LA40_0==RULE_S_BYTE_CHAR_STR||LA40_0==RULE_D_BYTE_CHAR_STR) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalStructuredTextParser.g:4169:4: rule__Case_Stmt__CaseAssignment_3
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
    // InternalStructuredTextParser.g:4178:1: rule__Case_Stmt__Group__4 : rule__Case_Stmt__Group__4__Impl rule__Case_Stmt__Group__5 ;
    public final void rule__Case_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4182:1: ( rule__Case_Stmt__Group__4__Impl rule__Case_Stmt__Group__5 )
            // InternalStructuredTextParser.g:4183:2: rule__Case_Stmt__Group__4__Impl rule__Case_Stmt__Group__5
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
    // InternalStructuredTextParser.g:4190:1: rule__Case_Stmt__Group__4__Impl : ( ( rule__Case_Stmt__ElseAssignment_4 )? ) ;
    public final void rule__Case_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4194:1: ( ( ( rule__Case_Stmt__ElseAssignment_4 )? ) )
            // InternalStructuredTextParser.g:4195:1: ( ( rule__Case_Stmt__ElseAssignment_4 )? )
            {
            // InternalStructuredTextParser.g:4195:1: ( ( rule__Case_Stmt__ElseAssignment_4 )? )
            // InternalStructuredTextParser.g:4196:2: ( rule__Case_Stmt__ElseAssignment_4 )?
            {
             before(grammarAccess.getCase_StmtAccess().getElseAssignment_4()); 
            // InternalStructuredTextParser.g:4197:2: ( rule__Case_Stmt__ElseAssignment_4 )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==ELSE) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalStructuredTextParser.g:4197:3: rule__Case_Stmt__ElseAssignment_4
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
    // InternalStructuredTextParser.g:4205:1: rule__Case_Stmt__Group__5 : rule__Case_Stmt__Group__5__Impl ;
    public final void rule__Case_Stmt__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4209:1: ( rule__Case_Stmt__Group__5__Impl )
            // InternalStructuredTextParser.g:4210:2: rule__Case_Stmt__Group__5__Impl
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
    // InternalStructuredTextParser.g:4216:1: rule__Case_Stmt__Group__5__Impl : ( END_CASE ) ;
    public final void rule__Case_Stmt__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4220:1: ( ( END_CASE ) )
            // InternalStructuredTextParser.g:4221:1: ( END_CASE )
            {
            // InternalStructuredTextParser.g:4221:1: ( END_CASE )
            // InternalStructuredTextParser.g:4222:2: END_CASE
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
    // InternalStructuredTextParser.g:4232:1: rule__Case_Selection__Group__0 : rule__Case_Selection__Group__0__Impl rule__Case_Selection__Group__1 ;
    public final void rule__Case_Selection__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4236:1: ( rule__Case_Selection__Group__0__Impl rule__Case_Selection__Group__1 )
            // InternalStructuredTextParser.g:4237:2: rule__Case_Selection__Group__0__Impl rule__Case_Selection__Group__1
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
    // InternalStructuredTextParser.g:4244:1: rule__Case_Selection__Group__0__Impl : ( ( rule__Case_Selection__CaseAssignment_0 ) ) ;
    public final void rule__Case_Selection__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4248:1: ( ( ( rule__Case_Selection__CaseAssignment_0 ) ) )
            // InternalStructuredTextParser.g:4249:1: ( ( rule__Case_Selection__CaseAssignment_0 ) )
            {
            // InternalStructuredTextParser.g:4249:1: ( ( rule__Case_Selection__CaseAssignment_0 ) )
            // InternalStructuredTextParser.g:4250:2: ( rule__Case_Selection__CaseAssignment_0 )
            {
             before(grammarAccess.getCase_SelectionAccess().getCaseAssignment_0()); 
            // InternalStructuredTextParser.g:4251:2: ( rule__Case_Selection__CaseAssignment_0 )
            // InternalStructuredTextParser.g:4251:3: rule__Case_Selection__CaseAssignment_0
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
    // InternalStructuredTextParser.g:4259:1: rule__Case_Selection__Group__1 : rule__Case_Selection__Group__1__Impl rule__Case_Selection__Group__2 ;
    public final void rule__Case_Selection__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4263:1: ( rule__Case_Selection__Group__1__Impl rule__Case_Selection__Group__2 )
            // InternalStructuredTextParser.g:4264:2: rule__Case_Selection__Group__1__Impl rule__Case_Selection__Group__2
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
    // InternalStructuredTextParser.g:4271:1: rule__Case_Selection__Group__1__Impl : ( ( rule__Case_Selection__Group_1__0 )* ) ;
    public final void rule__Case_Selection__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4275:1: ( ( ( rule__Case_Selection__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:4276:1: ( ( rule__Case_Selection__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:4276:1: ( ( rule__Case_Selection__Group_1__0 )* )
            // InternalStructuredTextParser.g:4277:2: ( rule__Case_Selection__Group_1__0 )*
            {
             before(grammarAccess.getCase_SelectionAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:4278:2: ( rule__Case_Selection__Group_1__0 )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==Comma) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalStructuredTextParser.g:4278:3: rule__Case_Selection__Group_1__0
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
    // InternalStructuredTextParser.g:4286:1: rule__Case_Selection__Group__2 : rule__Case_Selection__Group__2__Impl rule__Case_Selection__Group__3 ;
    public final void rule__Case_Selection__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4290:1: ( rule__Case_Selection__Group__2__Impl rule__Case_Selection__Group__3 )
            // InternalStructuredTextParser.g:4291:2: rule__Case_Selection__Group__2__Impl rule__Case_Selection__Group__3
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
    // InternalStructuredTextParser.g:4298:1: rule__Case_Selection__Group__2__Impl : ( Colon ) ;
    public final void rule__Case_Selection__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4302:1: ( ( Colon ) )
            // InternalStructuredTextParser.g:4303:1: ( Colon )
            {
            // InternalStructuredTextParser.g:4303:1: ( Colon )
            // InternalStructuredTextParser.g:4304:2: Colon
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
    // InternalStructuredTextParser.g:4313:1: rule__Case_Selection__Group__3 : rule__Case_Selection__Group__3__Impl ;
    public final void rule__Case_Selection__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4317:1: ( rule__Case_Selection__Group__3__Impl )
            // InternalStructuredTextParser.g:4318:2: rule__Case_Selection__Group__3__Impl
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
    // InternalStructuredTextParser.g:4324:1: rule__Case_Selection__Group__3__Impl : ( ( rule__Case_Selection__StatementsAssignment_3 ) ) ;
    public final void rule__Case_Selection__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4328:1: ( ( ( rule__Case_Selection__StatementsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4329:1: ( ( rule__Case_Selection__StatementsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4329:1: ( ( rule__Case_Selection__StatementsAssignment_3 ) )
            // InternalStructuredTextParser.g:4330:2: ( rule__Case_Selection__StatementsAssignment_3 )
            {
             before(grammarAccess.getCase_SelectionAccess().getStatementsAssignment_3()); 
            // InternalStructuredTextParser.g:4331:2: ( rule__Case_Selection__StatementsAssignment_3 )
            // InternalStructuredTextParser.g:4331:3: rule__Case_Selection__StatementsAssignment_3
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
    // InternalStructuredTextParser.g:4340:1: rule__Case_Selection__Group_1__0 : rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1 ;
    public final void rule__Case_Selection__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4344:1: ( rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1 )
            // InternalStructuredTextParser.g:4345:2: rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1
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
    // InternalStructuredTextParser.g:4352:1: rule__Case_Selection__Group_1__0__Impl : ( Comma ) ;
    public final void rule__Case_Selection__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4356:1: ( ( Comma ) )
            // InternalStructuredTextParser.g:4357:1: ( Comma )
            {
            // InternalStructuredTextParser.g:4357:1: ( Comma )
            // InternalStructuredTextParser.g:4358:2: Comma
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
    // InternalStructuredTextParser.g:4367:1: rule__Case_Selection__Group_1__1 : rule__Case_Selection__Group_1__1__Impl ;
    public final void rule__Case_Selection__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4371:1: ( rule__Case_Selection__Group_1__1__Impl )
            // InternalStructuredTextParser.g:4372:2: rule__Case_Selection__Group_1__1__Impl
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
    // InternalStructuredTextParser.g:4378:1: rule__Case_Selection__Group_1__1__Impl : ( ( rule__Case_Selection__CaseAssignment_1_1 ) ) ;
    public final void rule__Case_Selection__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4382:1: ( ( ( rule__Case_Selection__CaseAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:4383:1: ( ( rule__Case_Selection__CaseAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:4383:1: ( ( rule__Case_Selection__CaseAssignment_1_1 ) )
            // InternalStructuredTextParser.g:4384:2: ( rule__Case_Selection__CaseAssignment_1_1 )
            {
             before(grammarAccess.getCase_SelectionAccess().getCaseAssignment_1_1()); 
            // InternalStructuredTextParser.g:4385:2: ( rule__Case_Selection__CaseAssignment_1_1 )
            // InternalStructuredTextParser.g:4385:3: rule__Case_Selection__CaseAssignment_1_1
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
    // InternalStructuredTextParser.g:4394:1: rule__Iteration_Stmt__Group_3__0 : rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1 ;
    public final void rule__Iteration_Stmt__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4398:1: ( rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1 )
            // InternalStructuredTextParser.g:4399:2: rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1
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
    // InternalStructuredTextParser.g:4406:1: rule__Iteration_Stmt__Group_3__0__Impl : ( () ) ;
    public final void rule__Iteration_Stmt__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4410:1: ( ( () ) )
            // InternalStructuredTextParser.g:4411:1: ( () )
            {
            // InternalStructuredTextParser.g:4411:1: ( () )
            // InternalStructuredTextParser.g:4412:2: ()
            {
             before(grammarAccess.getIteration_StmtAccess().getExitStatementAction_3_0()); 
            // InternalStructuredTextParser.g:4413:2: ()
            // InternalStructuredTextParser.g:4413:3: 
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
    // InternalStructuredTextParser.g:4421:1: rule__Iteration_Stmt__Group_3__1 : rule__Iteration_Stmt__Group_3__1__Impl ;
    public final void rule__Iteration_Stmt__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4425:1: ( rule__Iteration_Stmt__Group_3__1__Impl )
            // InternalStructuredTextParser.g:4426:2: rule__Iteration_Stmt__Group_3__1__Impl
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
    // InternalStructuredTextParser.g:4432:1: rule__Iteration_Stmt__Group_3__1__Impl : ( EXIT ) ;
    public final void rule__Iteration_Stmt__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4436:1: ( ( EXIT ) )
            // InternalStructuredTextParser.g:4437:1: ( EXIT )
            {
            // InternalStructuredTextParser.g:4437:1: ( EXIT )
            // InternalStructuredTextParser.g:4438:2: EXIT
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
    // InternalStructuredTextParser.g:4448:1: rule__Iteration_Stmt__Group_4__0 : rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1 ;
    public final void rule__Iteration_Stmt__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4452:1: ( rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1 )
            // InternalStructuredTextParser.g:4453:2: rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1
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
    // InternalStructuredTextParser.g:4460:1: rule__Iteration_Stmt__Group_4__0__Impl : ( () ) ;
    public final void rule__Iteration_Stmt__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4464:1: ( ( () ) )
            // InternalStructuredTextParser.g:4465:1: ( () )
            {
            // InternalStructuredTextParser.g:4465:1: ( () )
            // InternalStructuredTextParser.g:4466:2: ()
            {
             before(grammarAccess.getIteration_StmtAccess().getContinueStatementAction_4_0()); 
            // InternalStructuredTextParser.g:4467:2: ()
            // InternalStructuredTextParser.g:4467:3: 
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
    // InternalStructuredTextParser.g:4475:1: rule__Iteration_Stmt__Group_4__1 : rule__Iteration_Stmt__Group_4__1__Impl ;
    public final void rule__Iteration_Stmt__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4479:1: ( rule__Iteration_Stmt__Group_4__1__Impl )
            // InternalStructuredTextParser.g:4480:2: rule__Iteration_Stmt__Group_4__1__Impl
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
    // InternalStructuredTextParser.g:4486:1: rule__Iteration_Stmt__Group_4__1__Impl : ( CONTINUE ) ;
    public final void rule__Iteration_Stmt__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4490:1: ( ( CONTINUE ) )
            // InternalStructuredTextParser.g:4491:1: ( CONTINUE )
            {
            // InternalStructuredTextParser.g:4491:1: ( CONTINUE )
            // InternalStructuredTextParser.g:4492:2: CONTINUE
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
    // InternalStructuredTextParser.g:4502:1: rule__For_Stmt__Group__0 : rule__For_Stmt__Group__0__Impl rule__For_Stmt__Group__1 ;
    public final void rule__For_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4506:1: ( rule__For_Stmt__Group__0__Impl rule__For_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4507:2: rule__For_Stmt__Group__0__Impl rule__For_Stmt__Group__1
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
    // InternalStructuredTextParser.g:4514:1: rule__For_Stmt__Group__0__Impl : ( FOR ) ;
    public final void rule__For_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4518:1: ( ( FOR ) )
            // InternalStructuredTextParser.g:4519:1: ( FOR )
            {
            // InternalStructuredTextParser.g:4519:1: ( FOR )
            // InternalStructuredTextParser.g:4520:2: FOR
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
    // InternalStructuredTextParser.g:4529:1: rule__For_Stmt__Group__1 : rule__For_Stmt__Group__1__Impl rule__For_Stmt__Group__2 ;
    public final void rule__For_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4533:1: ( rule__For_Stmt__Group__1__Impl rule__For_Stmt__Group__2 )
            // InternalStructuredTextParser.g:4534:2: rule__For_Stmt__Group__1__Impl rule__For_Stmt__Group__2
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
    // InternalStructuredTextParser.g:4541:1: rule__For_Stmt__Group__1__Impl : ( ( rule__For_Stmt__VariableAssignment_1 ) ) ;
    public final void rule__For_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4545:1: ( ( ( rule__For_Stmt__VariableAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4546:1: ( ( rule__For_Stmt__VariableAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4546:1: ( ( rule__For_Stmt__VariableAssignment_1 ) )
            // InternalStructuredTextParser.g:4547:2: ( rule__For_Stmt__VariableAssignment_1 )
            {
             before(grammarAccess.getFor_StmtAccess().getVariableAssignment_1()); 
            // InternalStructuredTextParser.g:4548:2: ( rule__For_Stmt__VariableAssignment_1 )
            // InternalStructuredTextParser.g:4548:3: rule__For_Stmt__VariableAssignment_1
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
    // InternalStructuredTextParser.g:4556:1: rule__For_Stmt__Group__2 : rule__For_Stmt__Group__2__Impl rule__For_Stmt__Group__3 ;
    public final void rule__For_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4560:1: ( rule__For_Stmt__Group__2__Impl rule__For_Stmt__Group__3 )
            // InternalStructuredTextParser.g:4561:2: rule__For_Stmt__Group__2__Impl rule__For_Stmt__Group__3
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
    // InternalStructuredTextParser.g:4568:1: rule__For_Stmt__Group__2__Impl : ( ColonEqualsSign ) ;
    public final void rule__For_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4572:1: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:4573:1: ( ColonEqualsSign )
            {
            // InternalStructuredTextParser.g:4573:1: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:4574:2: ColonEqualsSign
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
    // InternalStructuredTextParser.g:4583:1: rule__For_Stmt__Group__3 : rule__For_Stmt__Group__3__Impl rule__For_Stmt__Group__4 ;
    public final void rule__For_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4587:1: ( rule__For_Stmt__Group__3__Impl rule__For_Stmt__Group__4 )
            // InternalStructuredTextParser.g:4588:2: rule__For_Stmt__Group__3__Impl rule__For_Stmt__Group__4
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
    // InternalStructuredTextParser.g:4595:1: rule__For_Stmt__Group__3__Impl : ( ( rule__For_Stmt__FromAssignment_3 ) ) ;
    public final void rule__For_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4599:1: ( ( ( rule__For_Stmt__FromAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4600:1: ( ( rule__For_Stmt__FromAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4600:1: ( ( rule__For_Stmt__FromAssignment_3 ) )
            // InternalStructuredTextParser.g:4601:2: ( rule__For_Stmt__FromAssignment_3 )
            {
             before(grammarAccess.getFor_StmtAccess().getFromAssignment_3()); 
            // InternalStructuredTextParser.g:4602:2: ( rule__For_Stmt__FromAssignment_3 )
            // InternalStructuredTextParser.g:4602:3: rule__For_Stmt__FromAssignment_3
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
    // InternalStructuredTextParser.g:4610:1: rule__For_Stmt__Group__4 : rule__For_Stmt__Group__4__Impl rule__For_Stmt__Group__5 ;
    public final void rule__For_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4614:1: ( rule__For_Stmt__Group__4__Impl rule__For_Stmt__Group__5 )
            // InternalStructuredTextParser.g:4615:2: rule__For_Stmt__Group__4__Impl rule__For_Stmt__Group__5
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
    // InternalStructuredTextParser.g:4622:1: rule__For_Stmt__Group__4__Impl : ( TO ) ;
    public final void rule__For_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4626:1: ( ( TO ) )
            // InternalStructuredTextParser.g:4627:1: ( TO )
            {
            // InternalStructuredTextParser.g:4627:1: ( TO )
            // InternalStructuredTextParser.g:4628:2: TO
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
    // InternalStructuredTextParser.g:4637:1: rule__For_Stmt__Group__5 : rule__For_Stmt__Group__5__Impl rule__For_Stmt__Group__6 ;
    public final void rule__For_Stmt__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4641:1: ( rule__For_Stmt__Group__5__Impl rule__For_Stmt__Group__6 )
            // InternalStructuredTextParser.g:4642:2: rule__For_Stmt__Group__5__Impl rule__For_Stmt__Group__6
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
    // InternalStructuredTextParser.g:4649:1: rule__For_Stmt__Group__5__Impl : ( ( rule__For_Stmt__ToAssignment_5 ) ) ;
    public final void rule__For_Stmt__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4653:1: ( ( ( rule__For_Stmt__ToAssignment_5 ) ) )
            // InternalStructuredTextParser.g:4654:1: ( ( rule__For_Stmt__ToAssignment_5 ) )
            {
            // InternalStructuredTextParser.g:4654:1: ( ( rule__For_Stmt__ToAssignment_5 ) )
            // InternalStructuredTextParser.g:4655:2: ( rule__For_Stmt__ToAssignment_5 )
            {
             before(grammarAccess.getFor_StmtAccess().getToAssignment_5()); 
            // InternalStructuredTextParser.g:4656:2: ( rule__For_Stmt__ToAssignment_5 )
            // InternalStructuredTextParser.g:4656:3: rule__For_Stmt__ToAssignment_5
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
    // InternalStructuredTextParser.g:4664:1: rule__For_Stmt__Group__6 : rule__For_Stmt__Group__6__Impl rule__For_Stmt__Group__7 ;
    public final void rule__For_Stmt__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4668:1: ( rule__For_Stmt__Group__6__Impl rule__For_Stmt__Group__7 )
            // InternalStructuredTextParser.g:4669:2: rule__For_Stmt__Group__6__Impl rule__For_Stmt__Group__7
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
    // InternalStructuredTextParser.g:4676:1: rule__For_Stmt__Group__6__Impl : ( ( rule__For_Stmt__Group_6__0 )? ) ;
    public final void rule__For_Stmt__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4680:1: ( ( ( rule__For_Stmt__Group_6__0 )? ) )
            // InternalStructuredTextParser.g:4681:1: ( ( rule__For_Stmt__Group_6__0 )? )
            {
            // InternalStructuredTextParser.g:4681:1: ( ( rule__For_Stmt__Group_6__0 )? )
            // InternalStructuredTextParser.g:4682:2: ( rule__For_Stmt__Group_6__0 )?
            {
             before(grammarAccess.getFor_StmtAccess().getGroup_6()); 
            // InternalStructuredTextParser.g:4683:2: ( rule__For_Stmt__Group_6__0 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==BY) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalStructuredTextParser.g:4683:3: rule__For_Stmt__Group_6__0
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
    // InternalStructuredTextParser.g:4691:1: rule__For_Stmt__Group__7 : rule__For_Stmt__Group__7__Impl rule__For_Stmt__Group__8 ;
    public final void rule__For_Stmt__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4695:1: ( rule__For_Stmt__Group__7__Impl rule__For_Stmt__Group__8 )
            // InternalStructuredTextParser.g:4696:2: rule__For_Stmt__Group__7__Impl rule__For_Stmt__Group__8
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
    // InternalStructuredTextParser.g:4703:1: rule__For_Stmt__Group__7__Impl : ( DO ) ;
    public final void rule__For_Stmt__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4707:1: ( ( DO ) )
            // InternalStructuredTextParser.g:4708:1: ( DO )
            {
            // InternalStructuredTextParser.g:4708:1: ( DO )
            // InternalStructuredTextParser.g:4709:2: DO
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
    // InternalStructuredTextParser.g:4718:1: rule__For_Stmt__Group__8 : rule__For_Stmt__Group__8__Impl rule__For_Stmt__Group__9 ;
    public final void rule__For_Stmt__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4722:1: ( rule__For_Stmt__Group__8__Impl rule__For_Stmt__Group__9 )
            // InternalStructuredTextParser.g:4723:2: rule__For_Stmt__Group__8__Impl rule__For_Stmt__Group__9
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
    // InternalStructuredTextParser.g:4730:1: rule__For_Stmt__Group__8__Impl : ( ( rule__For_Stmt__StatementsAssignment_8 ) ) ;
    public final void rule__For_Stmt__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4734:1: ( ( ( rule__For_Stmt__StatementsAssignment_8 ) ) )
            // InternalStructuredTextParser.g:4735:1: ( ( rule__For_Stmt__StatementsAssignment_8 ) )
            {
            // InternalStructuredTextParser.g:4735:1: ( ( rule__For_Stmt__StatementsAssignment_8 ) )
            // InternalStructuredTextParser.g:4736:2: ( rule__For_Stmt__StatementsAssignment_8 )
            {
             before(grammarAccess.getFor_StmtAccess().getStatementsAssignment_8()); 
            // InternalStructuredTextParser.g:4737:2: ( rule__For_Stmt__StatementsAssignment_8 )
            // InternalStructuredTextParser.g:4737:3: rule__For_Stmt__StatementsAssignment_8
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
    // InternalStructuredTextParser.g:4745:1: rule__For_Stmt__Group__9 : rule__For_Stmt__Group__9__Impl ;
    public final void rule__For_Stmt__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4749:1: ( rule__For_Stmt__Group__9__Impl )
            // InternalStructuredTextParser.g:4750:2: rule__For_Stmt__Group__9__Impl
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
    // InternalStructuredTextParser.g:4756:1: rule__For_Stmt__Group__9__Impl : ( END_FOR ) ;
    public final void rule__For_Stmt__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4760:1: ( ( END_FOR ) )
            // InternalStructuredTextParser.g:4761:1: ( END_FOR )
            {
            // InternalStructuredTextParser.g:4761:1: ( END_FOR )
            // InternalStructuredTextParser.g:4762:2: END_FOR
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
    // InternalStructuredTextParser.g:4772:1: rule__For_Stmt__Group_6__0 : rule__For_Stmt__Group_6__0__Impl rule__For_Stmt__Group_6__1 ;
    public final void rule__For_Stmt__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4776:1: ( rule__For_Stmt__Group_6__0__Impl rule__For_Stmt__Group_6__1 )
            // InternalStructuredTextParser.g:4777:2: rule__For_Stmt__Group_6__0__Impl rule__For_Stmt__Group_6__1
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
    // InternalStructuredTextParser.g:4784:1: rule__For_Stmt__Group_6__0__Impl : ( BY ) ;
    public final void rule__For_Stmt__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4788:1: ( ( BY ) )
            // InternalStructuredTextParser.g:4789:1: ( BY )
            {
            // InternalStructuredTextParser.g:4789:1: ( BY )
            // InternalStructuredTextParser.g:4790:2: BY
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
    // InternalStructuredTextParser.g:4799:1: rule__For_Stmt__Group_6__1 : rule__For_Stmt__Group_6__1__Impl ;
    public final void rule__For_Stmt__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4803:1: ( rule__For_Stmt__Group_6__1__Impl )
            // InternalStructuredTextParser.g:4804:2: rule__For_Stmt__Group_6__1__Impl
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
    // InternalStructuredTextParser.g:4810:1: rule__For_Stmt__Group_6__1__Impl : ( ( rule__For_Stmt__ByAssignment_6_1 ) ) ;
    public final void rule__For_Stmt__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4814:1: ( ( ( rule__For_Stmt__ByAssignment_6_1 ) ) )
            // InternalStructuredTextParser.g:4815:1: ( ( rule__For_Stmt__ByAssignment_6_1 ) )
            {
            // InternalStructuredTextParser.g:4815:1: ( ( rule__For_Stmt__ByAssignment_6_1 ) )
            // InternalStructuredTextParser.g:4816:2: ( rule__For_Stmt__ByAssignment_6_1 )
            {
             before(grammarAccess.getFor_StmtAccess().getByAssignment_6_1()); 
            // InternalStructuredTextParser.g:4817:2: ( rule__For_Stmt__ByAssignment_6_1 )
            // InternalStructuredTextParser.g:4817:3: rule__For_Stmt__ByAssignment_6_1
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
    // InternalStructuredTextParser.g:4826:1: rule__While_Stmt__Group__0 : rule__While_Stmt__Group__0__Impl rule__While_Stmt__Group__1 ;
    public final void rule__While_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4830:1: ( rule__While_Stmt__Group__0__Impl rule__While_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4831:2: rule__While_Stmt__Group__0__Impl rule__While_Stmt__Group__1
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
    // InternalStructuredTextParser.g:4838:1: rule__While_Stmt__Group__0__Impl : ( WHILE ) ;
    public final void rule__While_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4842:1: ( ( WHILE ) )
            // InternalStructuredTextParser.g:4843:1: ( WHILE )
            {
            // InternalStructuredTextParser.g:4843:1: ( WHILE )
            // InternalStructuredTextParser.g:4844:2: WHILE
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
    // InternalStructuredTextParser.g:4853:1: rule__While_Stmt__Group__1 : rule__While_Stmt__Group__1__Impl rule__While_Stmt__Group__2 ;
    public final void rule__While_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4857:1: ( rule__While_Stmt__Group__1__Impl rule__While_Stmt__Group__2 )
            // InternalStructuredTextParser.g:4858:2: rule__While_Stmt__Group__1__Impl rule__While_Stmt__Group__2
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
    // InternalStructuredTextParser.g:4865:1: rule__While_Stmt__Group__1__Impl : ( ( rule__While_Stmt__ExpressionAssignment_1 ) ) ;
    public final void rule__While_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4869:1: ( ( ( rule__While_Stmt__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4870:1: ( ( rule__While_Stmt__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4870:1: ( ( rule__While_Stmt__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:4871:2: ( rule__While_Stmt__ExpressionAssignment_1 )
            {
             before(grammarAccess.getWhile_StmtAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:4872:2: ( rule__While_Stmt__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:4872:3: rule__While_Stmt__ExpressionAssignment_1
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
    // InternalStructuredTextParser.g:4880:1: rule__While_Stmt__Group__2 : rule__While_Stmt__Group__2__Impl rule__While_Stmt__Group__3 ;
    public final void rule__While_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4884:1: ( rule__While_Stmt__Group__2__Impl rule__While_Stmt__Group__3 )
            // InternalStructuredTextParser.g:4885:2: rule__While_Stmt__Group__2__Impl rule__While_Stmt__Group__3
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
    // InternalStructuredTextParser.g:4892:1: rule__While_Stmt__Group__2__Impl : ( DO ) ;
    public final void rule__While_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4896:1: ( ( DO ) )
            // InternalStructuredTextParser.g:4897:1: ( DO )
            {
            // InternalStructuredTextParser.g:4897:1: ( DO )
            // InternalStructuredTextParser.g:4898:2: DO
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
    // InternalStructuredTextParser.g:4907:1: rule__While_Stmt__Group__3 : rule__While_Stmt__Group__3__Impl rule__While_Stmt__Group__4 ;
    public final void rule__While_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4911:1: ( rule__While_Stmt__Group__3__Impl rule__While_Stmt__Group__4 )
            // InternalStructuredTextParser.g:4912:2: rule__While_Stmt__Group__3__Impl rule__While_Stmt__Group__4
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
    // InternalStructuredTextParser.g:4919:1: rule__While_Stmt__Group__3__Impl : ( ( rule__While_Stmt__StatementsAssignment_3 ) ) ;
    public final void rule__While_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4923:1: ( ( ( rule__While_Stmt__StatementsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4924:1: ( ( rule__While_Stmt__StatementsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4924:1: ( ( rule__While_Stmt__StatementsAssignment_3 ) )
            // InternalStructuredTextParser.g:4925:2: ( rule__While_Stmt__StatementsAssignment_3 )
            {
             before(grammarAccess.getWhile_StmtAccess().getStatementsAssignment_3()); 
            // InternalStructuredTextParser.g:4926:2: ( rule__While_Stmt__StatementsAssignment_3 )
            // InternalStructuredTextParser.g:4926:3: rule__While_Stmt__StatementsAssignment_3
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
    // InternalStructuredTextParser.g:4934:1: rule__While_Stmt__Group__4 : rule__While_Stmt__Group__4__Impl ;
    public final void rule__While_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4938:1: ( rule__While_Stmt__Group__4__Impl )
            // InternalStructuredTextParser.g:4939:2: rule__While_Stmt__Group__4__Impl
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
    // InternalStructuredTextParser.g:4945:1: rule__While_Stmt__Group__4__Impl : ( END_WHILE ) ;
    public final void rule__While_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4949:1: ( ( END_WHILE ) )
            // InternalStructuredTextParser.g:4950:1: ( END_WHILE )
            {
            // InternalStructuredTextParser.g:4950:1: ( END_WHILE )
            // InternalStructuredTextParser.g:4951:2: END_WHILE
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
    // InternalStructuredTextParser.g:4961:1: rule__Repeat_Stmt__Group__0 : rule__Repeat_Stmt__Group__0__Impl rule__Repeat_Stmt__Group__1 ;
    public final void rule__Repeat_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4965:1: ( rule__Repeat_Stmt__Group__0__Impl rule__Repeat_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4966:2: rule__Repeat_Stmt__Group__0__Impl rule__Repeat_Stmt__Group__1
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
    // InternalStructuredTextParser.g:4973:1: rule__Repeat_Stmt__Group__0__Impl : ( REPEAT ) ;
    public final void rule__Repeat_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4977:1: ( ( REPEAT ) )
            // InternalStructuredTextParser.g:4978:1: ( REPEAT )
            {
            // InternalStructuredTextParser.g:4978:1: ( REPEAT )
            // InternalStructuredTextParser.g:4979:2: REPEAT
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
    // InternalStructuredTextParser.g:4988:1: rule__Repeat_Stmt__Group__1 : rule__Repeat_Stmt__Group__1__Impl rule__Repeat_Stmt__Group__2 ;
    public final void rule__Repeat_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4992:1: ( rule__Repeat_Stmt__Group__1__Impl rule__Repeat_Stmt__Group__2 )
            // InternalStructuredTextParser.g:4993:2: rule__Repeat_Stmt__Group__1__Impl rule__Repeat_Stmt__Group__2
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
    // InternalStructuredTextParser.g:5000:1: rule__Repeat_Stmt__Group__1__Impl : ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) ) ;
    public final void rule__Repeat_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5004:1: ( ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) ) )
            // InternalStructuredTextParser.g:5005:1: ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:5005:1: ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) )
            // InternalStructuredTextParser.g:5006:2: ( rule__Repeat_Stmt__StatementsAssignment_1 )
            {
             before(grammarAccess.getRepeat_StmtAccess().getStatementsAssignment_1()); 
            // InternalStructuredTextParser.g:5007:2: ( rule__Repeat_Stmt__StatementsAssignment_1 )
            // InternalStructuredTextParser.g:5007:3: rule__Repeat_Stmt__StatementsAssignment_1
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
    // InternalStructuredTextParser.g:5015:1: rule__Repeat_Stmt__Group__2 : rule__Repeat_Stmt__Group__2__Impl rule__Repeat_Stmt__Group__3 ;
    public final void rule__Repeat_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5019:1: ( rule__Repeat_Stmt__Group__2__Impl rule__Repeat_Stmt__Group__3 )
            // InternalStructuredTextParser.g:5020:2: rule__Repeat_Stmt__Group__2__Impl rule__Repeat_Stmt__Group__3
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
    // InternalStructuredTextParser.g:5027:1: rule__Repeat_Stmt__Group__2__Impl : ( UNTIL ) ;
    public final void rule__Repeat_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5031:1: ( ( UNTIL ) )
            // InternalStructuredTextParser.g:5032:1: ( UNTIL )
            {
            // InternalStructuredTextParser.g:5032:1: ( UNTIL )
            // InternalStructuredTextParser.g:5033:2: UNTIL
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
    // InternalStructuredTextParser.g:5042:1: rule__Repeat_Stmt__Group__3 : rule__Repeat_Stmt__Group__3__Impl rule__Repeat_Stmt__Group__4 ;
    public final void rule__Repeat_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5046:1: ( rule__Repeat_Stmt__Group__3__Impl rule__Repeat_Stmt__Group__4 )
            // InternalStructuredTextParser.g:5047:2: rule__Repeat_Stmt__Group__3__Impl rule__Repeat_Stmt__Group__4
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
    // InternalStructuredTextParser.g:5054:1: rule__Repeat_Stmt__Group__3__Impl : ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) ) ;
    public final void rule__Repeat_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5058:1: ( ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) ) )
            // InternalStructuredTextParser.g:5059:1: ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:5059:1: ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) )
            // InternalStructuredTextParser.g:5060:2: ( rule__Repeat_Stmt__ExpressionAssignment_3 )
            {
             before(grammarAccess.getRepeat_StmtAccess().getExpressionAssignment_3()); 
            // InternalStructuredTextParser.g:5061:2: ( rule__Repeat_Stmt__ExpressionAssignment_3 )
            // InternalStructuredTextParser.g:5061:3: rule__Repeat_Stmt__ExpressionAssignment_3
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
    // InternalStructuredTextParser.g:5069:1: rule__Repeat_Stmt__Group__4 : rule__Repeat_Stmt__Group__4__Impl ;
    public final void rule__Repeat_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5073:1: ( rule__Repeat_Stmt__Group__4__Impl )
            // InternalStructuredTextParser.g:5074:2: rule__Repeat_Stmt__Group__4__Impl
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
    // InternalStructuredTextParser.g:5080:1: rule__Repeat_Stmt__Group__4__Impl : ( END_REPEAT ) ;
    public final void rule__Repeat_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5084:1: ( ( END_REPEAT ) )
            // InternalStructuredTextParser.g:5085:1: ( END_REPEAT )
            {
            // InternalStructuredTextParser.g:5085:1: ( END_REPEAT )
            // InternalStructuredTextParser.g:5086:2: END_REPEAT
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
    // InternalStructuredTextParser.g:5096:1: rule__Or_Expression__Group__0 : rule__Or_Expression__Group__0__Impl rule__Or_Expression__Group__1 ;
    public final void rule__Or_Expression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5100:1: ( rule__Or_Expression__Group__0__Impl rule__Or_Expression__Group__1 )
            // InternalStructuredTextParser.g:5101:2: rule__Or_Expression__Group__0__Impl rule__Or_Expression__Group__1
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
    // InternalStructuredTextParser.g:5108:1: rule__Or_Expression__Group__0__Impl : ( ruleXor_Expr ) ;
    public final void rule__Or_Expression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5112:1: ( ( ruleXor_Expr ) )
            // InternalStructuredTextParser.g:5113:1: ( ruleXor_Expr )
            {
            // InternalStructuredTextParser.g:5113:1: ( ruleXor_Expr )
            // InternalStructuredTextParser.g:5114:2: ruleXor_Expr
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
    // InternalStructuredTextParser.g:5123:1: rule__Or_Expression__Group__1 : rule__Or_Expression__Group__1__Impl ;
    public final void rule__Or_Expression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5127:1: ( rule__Or_Expression__Group__1__Impl )
            // InternalStructuredTextParser.g:5128:2: rule__Or_Expression__Group__1__Impl
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
    // InternalStructuredTextParser.g:5134:1: rule__Or_Expression__Group__1__Impl : ( ( rule__Or_Expression__Group_1__0 )* ) ;
    public final void rule__Or_Expression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5138:1: ( ( ( rule__Or_Expression__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5139:1: ( ( rule__Or_Expression__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5139:1: ( ( rule__Or_Expression__Group_1__0 )* )
            // InternalStructuredTextParser.g:5140:2: ( rule__Or_Expression__Group_1__0 )*
            {
             before(grammarAccess.getOr_ExpressionAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5141:2: ( rule__Or_Expression__Group_1__0 )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==OR) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5141:3: rule__Or_Expression__Group_1__0
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
    // InternalStructuredTextParser.g:5150:1: rule__Or_Expression__Group_1__0 : rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1 ;
    public final void rule__Or_Expression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5154:1: ( rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1 )
            // InternalStructuredTextParser.g:5155:2: rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1
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
    // InternalStructuredTextParser.g:5162:1: rule__Or_Expression__Group_1__0__Impl : ( () ) ;
    public final void rule__Or_Expression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5166:1: ( ( () ) )
            // InternalStructuredTextParser.g:5167:1: ( () )
            {
            // InternalStructuredTextParser.g:5167:1: ( () )
            // InternalStructuredTextParser.g:5168:2: ()
            {
             before(grammarAccess.getOr_ExpressionAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5169:2: ()
            // InternalStructuredTextParser.g:5169:3: 
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
    // InternalStructuredTextParser.g:5177:1: rule__Or_Expression__Group_1__1 : rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2 ;
    public final void rule__Or_Expression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5181:1: ( rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2 )
            // InternalStructuredTextParser.g:5182:2: rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2
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
    // InternalStructuredTextParser.g:5189:1: rule__Or_Expression__Group_1__1__Impl : ( ( rule__Or_Expression__OperatorAssignment_1_1 ) ) ;
    public final void rule__Or_Expression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5193:1: ( ( ( rule__Or_Expression__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5194:1: ( ( rule__Or_Expression__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5194:1: ( ( rule__Or_Expression__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5195:2: ( rule__Or_Expression__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getOr_ExpressionAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5196:2: ( rule__Or_Expression__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5196:3: rule__Or_Expression__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:5204:1: rule__Or_Expression__Group_1__2 : rule__Or_Expression__Group_1__2__Impl ;
    public final void rule__Or_Expression__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5208:1: ( rule__Or_Expression__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5209:2: rule__Or_Expression__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:5215:1: rule__Or_Expression__Group_1__2__Impl : ( ( rule__Or_Expression__RightAssignment_1_2 ) ) ;
    public final void rule__Or_Expression__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5219:1: ( ( ( rule__Or_Expression__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5220:1: ( ( rule__Or_Expression__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5220:1: ( ( rule__Or_Expression__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5221:2: ( rule__Or_Expression__RightAssignment_1_2 )
            {
             before(grammarAccess.getOr_ExpressionAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5222:2: ( rule__Or_Expression__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5222:3: rule__Or_Expression__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:5231:1: rule__Xor_Expr__Group__0 : rule__Xor_Expr__Group__0__Impl rule__Xor_Expr__Group__1 ;
    public final void rule__Xor_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5235:1: ( rule__Xor_Expr__Group__0__Impl rule__Xor_Expr__Group__1 )
            // InternalStructuredTextParser.g:5236:2: rule__Xor_Expr__Group__0__Impl rule__Xor_Expr__Group__1
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
    // InternalStructuredTextParser.g:5243:1: rule__Xor_Expr__Group__0__Impl : ( ruleAnd_Expr ) ;
    public final void rule__Xor_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5247:1: ( ( ruleAnd_Expr ) )
            // InternalStructuredTextParser.g:5248:1: ( ruleAnd_Expr )
            {
            // InternalStructuredTextParser.g:5248:1: ( ruleAnd_Expr )
            // InternalStructuredTextParser.g:5249:2: ruleAnd_Expr
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
    // InternalStructuredTextParser.g:5258:1: rule__Xor_Expr__Group__1 : rule__Xor_Expr__Group__1__Impl ;
    public final void rule__Xor_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5262:1: ( rule__Xor_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5263:2: rule__Xor_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:5269:1: rule__Xor_Expr__Group__1__Impl : ( ( rule__Xor_Expr__Group_1__0 )* ) ;
    public final void rule__Xor_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5273:1: ( ( ( rule__Xor_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5274:1: ( ( rule__Xor_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5274:1: ( ( rule__Xor_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5275:2: ( rule__Xor_Expr__Group_1__0 )*
            {
             before(grammarAccess.getXor_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5276:2: ( rule__Xor_Expr__Group_1__0 )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==XOR) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5276:3: rule__Xor_Expr__Group_1__0
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
    // InternalStructuredTextParser.g:5285:1: rule__Xor_Expr__Group_1__0 : rule__Xor_Expr__Group_1__0__Impl rule__Xor_Expr__Group_1__1 ;
    public final void rule__Xor_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5289:1: ( rule__Xor_Expr__Group_1__0__Impl rule__Xor_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5290:2: rule__Xor_Expr__Group_1__0__Impl rule__Xor_Expr__Group_1__1
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
    // InternalStructuredTextParser.g:5297:1: rule__Xor_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Xor_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5301:1: ( ( () ) )
            // InternalStructuredTextParser.g:5302:1: ( () )
            {
            // InternalStructuredTextParser.g:5302:1: ( () )
            // InternalStructuredTextParser.g:5303:2: ()
            {
             before(grammarAccess.getXor_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5304:2: ()
            // InternalStructuredTextParser.g:5304:3: 
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
    // InternalStructuredTextParser.g:5312:1: rule__Xor_Expr__Group_1__1 : rule__Xor_Expr__Group_1__1__Impl rule__Xor_Expr__Group_1__2 ;
    public final void rule__Xor_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5316:1: ( rule__Xor_Expr__Group_1__1__Impl rule__Xor_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5317:2: rule__Xor_Expr__Group_1__1__Impl rule__Xor_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:5324:1: rule__Xor_Expr__Group_1__1__Impl : ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Xor_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5328:1: ( ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5329:1: ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5329:1: ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5330:2: ( rule__Xor_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getXor_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5331:2: ( rule__Xor_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5331:3: rule__Xor_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:5339:1: rule__Xor_Expr__Group_1__2 : rule__Xor_Expr__Group_1__2__Impl ;
    public final void rule__Xor_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5343:1: ( rule__Xor_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5344:2: rule__Xor_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:5350:1: rule__Xor_Expr__Group_1__2__Impl : ( ( rule__Xor_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Xor_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5354:1: ( ( ( rule__Xor_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5355:1: ( ( rule__Xor_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5355:1: ( ( rule__Xor_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5356:2: ( rule__Xor_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getXor_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5357:2: ( rule__Xor_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5357:3: rule__Xor_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:5366:1: rule__And_Expr__Group__0 : rule__And_Expr__Group__0__Impl rule__And_Expr__Group__1 ;
    public final void rule__And_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5370:1: ( rule__And_Expr__Group__0__Impl rule__And_Expr__Group__1 )
            // InternalStructuredTextParser.g:5371:2: rule__And_Expr__Group__0__Impl rule__And_Expr__Group__1
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
    // InternalStructuredTextParser.g:5378:1: rule__And_Expr__Group__0__Impl : ( ruleCompare_Expr ) ;
    public final void rule__And_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5382:1: ( ( ruleCompare_Expr ) )
            // InternalStructuredTextParser.g:5383:1: ( ruleCompare_Expr )
            {
            // InternalStructuredTextParser.g:5383:1: ( ruleCompare_Expr )
            // InternalStructuredTextParser.g:5384:2: ruleCompare_Expr
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
    // InternalStructuredTextParser.g:5393:1: rule__And_Expr__Group__1 : rule__And_Expr__Group__1__Impl ;
    public final void rule__And_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5397:1: ( rule__And_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5398:2: rule__And_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:5404:1: rule__And_Expr__Group__1__Impl : ( ( rule__And_Expr__Group_1__0 )* ) ;
    public final void rule__And_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5408:1: ( ( ( rule__And_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5409:1: ( ( rule__And_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5409:1: ( ( rule__And_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5410:2: ( rule__And_Expr__Group_1__0 )*
            {
             before(grammarAccess.getAnd_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5411:2: ( rule__And_Expr__Group_1__0 )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==AND||LA46_0==Ampersand) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5411:3: rule__And_Expr__Group_1__0
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
    // InternalStructuredTextParser.g:5420:1: rule__And_Expr__Group_1__0 : rule__And_Expr__Group_1__0__Impl rule__And_Expr__Group_1__1 ;
    public final void rule__And_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5424:1: ( rule__And_Expr__Group_1__0__Impl rule__And_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5425:2: rule__And_Expr__Group_1__0__Impl rule__And_Expr__Group_1__1
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
    // InternalStructuredTextParser.g:5432:1: rule__And_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__And_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5436:1: ( ( () ) )
            // InternalStructuredTextParser.g:5437:1: ( () )
            {
            // InternalStructuredTextParser.g:5437:1: ( () )
            // InternalStructuredTextParser.g:5438:2: ()
            {
             before(grammarAccess.getAnd_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5439:2: ()
            // InternalStructuredTextParser.g:5439:3: 
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
    // InternalStructuredTextParser.g:5447:1: rule__And_Expr__Group_1__1 : rule__And_Expr__Group_1__1__Impl rule__And_Expr__Group_1__2 ;
    public final void rule__And_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5451:1: ( rule__And_Expr__Group_1__1__Impl rule__And_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5452:2: rule__And_Expr__Group_1__1__Impl rule__And_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:5459:1: rule__And_Expr__Group_1__1__Impl : ( ( rule__And_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__And_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5463:1: ( ( ( rule__And_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5464:1: ( ( rule__And_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5464:1: ( ( rule__And_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5465:2: ( rule__And_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getAnd_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5466:2: ( rule__And_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5466:3: rule__And_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:5474:1: rule__And_Expr__Group_1__2 : rule__And_Expr__Group_1__2__Impl ;
    public final void rule__And_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5478:1: ( rule__And_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5479:2: rule__And_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:5485:1: rule__And_Expr__Group_1__2__Impl : ( ( rule__And_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__And_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5489:1: ( ( ( rule__And_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5490:1: ( ( rule__And_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5490:1: ( ( rule__And_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5491:2: ( rule__And_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getAnd_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5492:2: ( rule__And_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5492:3: rule__And_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:5501:1: rule__Compare_Expr__Group__0 : rule__Compare_Expr__Group__0__Impl rule__Compare_Expr__Group__1 ;
    public final void rule__Compare_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5505:1: ( rule__Compare_Expr__Group__0__Impl rule__Compare_Expr__Group__1 )
            // InternalStructuredTextParser.g:5506:2: rule__Compare_Expr__Group__0__Impl rule__Compare_Expr__Group__1
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
    // InternalStructuredTextParser.g:5513:1: rule__Compare_Expr__Group__0__Impl : ( ruleEqu_Expr ) ;
    public final void rule__Compare_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5517:1: ( ( ruleEqu_Expr ) )
            // InternalStructuredTextParser.g:5518:1: ( ruleEqu_Expr )
            {
            // InternalStructuredTextParser.g:5518:1: ( ruleEqu_Expr )
            // InternalStructuredTextParser.g:5519:2: ruleEqu_Expr
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
    // InternalStructuredTextParser.g:5528:1: rule__Compare_Expr__Group__1 : rule__Compare_Expr__Group__1__Impl ;
    public final void rule__Compare_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5532:1: ( rule__Compare_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5533:2: rule__Compare_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:5539:1: rule__Compare_Expr__Group__1__Impl : ( ( rule__Compare_Expr__Group_1__0 )* ) ;
    public final void rule__Compare_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5543:1: ( ( ( rule__Compare_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5544:1: ( ( rule__Compare_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5544:1: ( ( rule__Compare_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5545:2: ( rule__Compare_Expr__Group_1__0 )*
            {
             before(grammarAccess.getCompare_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5546:2: ( rule__Compare_Expr__Group_1__0 )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==LessThanSignGreaterThanSign||LA47_0==EqualsSign) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5546:3: rule__Compare_Expr__Group_1__0
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
    // InternalStructuredTextParser.g:5555:1: rule__Compare_Expr__Group_1__0 : rule__Compare_Expr__Group_1__0__Impl rule__Compare_Expr__Group_1__1 ;
    public final void rule__Compare_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5559:1: ( rule__Compare_Expr__Group_1__0__Impl rule__Compare_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5560:2: rule__Compare_Expr__Group_1__0__Impl rule__Compare_Expr__Group_1__1
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
    // InternalStructuredTextParser.g:5567:1: rule__Compare_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Compare_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5571:1: ( ( () ) )
            // InternalStructuredTextParser.g:5572:1: ( () )
            {
            // InternalStructuredTextParser.g:5572:1: ( () )
            // InternalStructuredTextParser.g:5573:2: ()
            {
             before(grammarAccess.getCompare_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5574:2: ()
            // InternalStructuredTextParser.g:5574:3: 
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
    // InternalStructuredTextParser.g:5582:1: rule__Compare_Expr__Group_1__1 : rule__Compare_Expr__Group_1__1__Impl rule__Compare_Expr__Group_1__2 ;
    public final void rule__Compare_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5586:1: ( rule__Compare_Expr__Group_1__1__Impl rule__Compare_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5587:2: rule__Compare_Expr__Group_1__1__Impl rule__Compare_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:5594:1: rule__Compare_Expr__Group_1__1__Impl : ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Compare_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5598:1: ( ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5599:1: ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5599:1: ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5600:2: ( rule__Compare_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getCompare_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5601:2: ( rule__Compare_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5601:3: rule__Compare_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:5609:1: rule__Compare_Expr__Group_1__2 : rule__Compare_Expr__Group_1__2__Impl ;
    public final void rule__Compare_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5613:1: ( rule__Compare_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5614:2: rule__Compare_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:5620:1: rule__Compare_Expr__Group_1__2__Impl : ( ( rule__Compare_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Compare_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5624:1: ( ( ( rule__Compare_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5625:1: ( ( rule__Compare_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5625:1: ( ( rule__Compare_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5626:2: ( rule__Compare_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getCompare_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5627:2: ( rule__Compare_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5627:3: rule__Compare_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:5636:1: rule__Equ_Expr__Group__0 : rule__Equ_Expr__Group__0__Impl rule__Equ_Expr__Group__1 ;
    public final void rule__Equ_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5640:1: ( rule__Equ_Expr__Group__0__Impl rule__Equ_Expr__Group__1 )
            // InternalStructuredTextParser.g:5641:2: rule__Equ_Expr__Group__0__Impl rule__Equ_Expr__Group__1
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
    // InternalStructuredTextParser.g:5648:1: rule__Equ_Expr__Group__0__Impl : ( ruleAdd_Expr ) ;
    public final void rule__Equ_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5652:1: ( ( ruleAdd_Expr ) )
            // InternalStructuredTextParser.g:5653:1: ( ruleAdd_Expr )
            {
            // InternalStructuredTextParser.g:5653:1: ( ruleAdd_Expr )
            // InternalStructuredTextParser.g:5654:2: ruleAdd_Expr
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
    // InternalStructuredTextParser.g:5663:1: rule__Equ_Expr__Group__1 : rule__Equ_Expr__Group__1__Impl ;
    public final void rule__Equ_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5667:1: ( rule__Equ_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5668:2: rule__Equ_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:5674:1: rule__Equ_Expr__Group__1__Impl : ( ( rule__Equ_Expr__Group_1__0 )* ) ;
    public final void rule__Equ_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5678:1: ( ( ( rule__Equ_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5679:1: ( ( rule__Equ_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5679:1: ( ( rule__Equ_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5680:2: ( rule__Equ_Expr__Group_1__0 )*
            {
             before(grammarAccess.getEqu_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5681:2: ( rule__Equ_Expr__Group_1__0 )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==LessThanSignEqualsSign||LA48_0==GreaterThanSignEqualsSign||LA48_0==LessThanSign||LA48_0==GreaterThanSign) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5681:3: rule__Equ_Expr__Group_1__0
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
    // InternalStructuredTextParser.g:5690:1: rule__Equ_Expr__Group_1__0 : rule__Equ_Expr__Group_1__0__Impl rule__Equ_Expr__Group_1__1 ;
    public final void rule__Equ_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5694:1: ( rule__Equ_Expr__Group_1__0__Impl rule__Equ_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5695:2: rule__Equ_Expr__Group_1__0__Impl rule__Equ_Expr__Group_1__1
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
    // InternalStructuredTextParser.g:5702:1: rule__Equ_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Equ_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5706:1: ( ( () ) )
            // InternalStructuredTextParser.g:5707:1: ( () )
            {
            // InternalStructuredTextParser.g:5707:1: ( () )
            // InternalStructuredTextParser.g:5708:2: ()
            {
             before(grammarAccess.getEqu_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5709:2: ()
            // InternalStructuredTextParser.g:5709:3: 
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
    // InternalStructuredTextParser.g:5717:1: rule__Equ_Expr__Group_1__1 : rule__Equ_Expr__Group_1__1__Impl rule__Equ_Expr__Group_1__2 ;
    public final void rule__Equ_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5721:1: ( rule__Equ_Expr__Group_1__1__Impl rule__Equ_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5722:2: rule__Equ_Expr__Group_1__1__Impl rule__Equ_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:5729:1: rule__Equ_Expr__Group_1__1__Impl : ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Equ_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5733:1: ( ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5734:1: ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5734:1: ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5735:2: ( rule__Equ_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getEqu_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5736:2: ( rule__Equ_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5736:3: rule__Equ_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:5744:1: rule__Equ_Expr__Group_1__2 : rule__Equ_Expr__Group_1__2__Impl ;
    public final void rule__Equ_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5748:1: ( rule__Equ_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5749:2: rule__Equ_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:5755:1: rule__Equ_Expr__Group_1__2__Impl : ( ( rule__Equ_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Equ_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5759:1: ( ( ( rule__Equ_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5760:1: ( ( rule__Equ_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5760:1: ( ( rule__Equ_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5761:2: ( rule__Equ_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getEqu_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5762:2: ( rule__Equ_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5762:3: rule__Equ_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:5771:1: rule__Add_Expr__Group__0 : rule__Add_Expr__Group__0__Impl rule__Add_Expr__Group__1 ;
    public final void rule__Add_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5775:1: ( rule__Add_Expr__Group__0__Impl rule__Add_Expr__Group__1 )
            // InternalStructuredTextParser.g:5776:2: rule__Add_Expr__Group__0__Impl rule__Add_Expr__Group__1
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
    // InternalStructuredTextParser.g:5783:1: rule__Add_Expr__Group__0__Impl : ( ruleTerm ) ;
    public final void rule__Add_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5787:1: ( ( ruleTerm ) )
            // InternalStructuredTextParser.g:5788:1: ( ruleTerm )
            {
            // InternalStructuredTextParser.g:5788:1: ( ruleTerm )
            // InternalStructuredTextParser.g:5789:2: ruleTerm
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
    // InternalStructuredTextParser.g:5798:1: rule__Add_Expr__Group__1 : rule__Add_Expr__Group__1__Impl ;
    public final void rule__Add_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5802:1: ( rule__Add_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5803:2: rule__Add_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:5809:1: rule__Add_Expr__Group__1__Impl : ( ( rule__Add_Expr__Group_1__0 )* ) ;
    public final void rule__Add_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5813:1: ( ( ( rule__Add_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5814:1: ( ( rule__Add_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5814:1: ( ( rule__Add_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5815:2: ( rule__Add_Expr__Group_1__0 )*
            {
             before(grammarAccess.getAdd_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5816:2: ( rule__Add_Expr__Group_1__0 )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==PlusSign||LA49_0==HyphenMinus) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5816:3: rule__Add_Expr__Group_1__0
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
    // InternalStructuredTextParser.g:5825:1: rule__Add_Expr__Group_1__0 : rule__Add_Expr__Group_1__0__Impl rule__Add_Expr__Group_1__1 ;
    public final void rule__Add_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5829:1: ( rule__Add_Expr__Group_1__0__Impl rule__Add_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5830:2: rule__Add_Expr__Group_1__0__Impl rule__Add_Expr__Group_1__1
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
    // InternalStructuredTextParser.g:5837:1: rule__Add_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Add_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5841:1: ( ( () ) )
            // InternalStructuredTextParser.g:5842:1: ( () )
            {
            // InternalStructuredTextParser.g:5842:1: ( () )
            // InternalStructuredTextParser.g:5843:2: ()
            {
             before(grammarAccess.getAdd_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5844:2: ()
            // InternalStructuredTextParser.g:5844:3: 
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
    // InternalStructuredTextParser.g:5852:1: rule__Add_Expr__Group_1__1 : rule__Add_Expr__Group_1__1__Impl rule__Add_Expr__Group_1__2 ;
    public final void rule__Add_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5856:1: ( rule__Add_Expr__Group_1__1__Impl rule__Add_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5857:2: rule__Add_Expr__Group_1__1__Impl rule__Add_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:5864:1: rule__Add_Expr__Group_1__1__Impl : ( ( rule__Add_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Add_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5868:1: ( ( ( rule__Add_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5869:1: ( ( rule__Add_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5869:1: ( ( rule__Add_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5870:2: ( rule__Add_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getAdd_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5871:2: ( rule__Add_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5871:3: rule__Add_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:5879:1: rule__Add_Expr__Group_1__2 : rule__Add_Expr__Group_1__2__Impl ;
    public final void rule__Add_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5883:1: ( rule__Add_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5884:2: rule__Add_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:5890:1: rule__Add_Expr__Group_1__2__Impl : ( ( rule__Add_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Add_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5894:1: ( ( ( rule__Add_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5895:1: ( ( rule__Add_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5895:1: ( ( rule__Add_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5896:2: ( rule__Add_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getAdd_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5897:2: ( rule__Add_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5897:3: rule__Add_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:5906:1: rule__Term__Group__0 : rule__Term__Group__0__Impl rule__Term__Group__1 ;
    public final void rule__Term__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5910:1: ( rule__Term__Group__0__Impl rule__Term__Group__1 )
            // InternalStructuredTextParser.g:5911:2: rule__Term__Group__0__Impl rule__Term__Group__1
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
    // InternalStructuredTextParser.g:5918:1: rule__Term__Group__0__Impl : ( rulePower_Expr ) ;
    public final void rule__Term__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5922:1: ( ( rulePower_Expr ) )
            // InternalStructuredTextParser.g:5923:1: ( rulePower_Expr )
            {
            // InternalStructuredTextParser.g:5923:1: ( rulePower_Expr )
            // InternalStructuredTextParser.g:5924:2: rulePower_Expr
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
    // InternalStructuredTextParser.g:5933:1: rule__Term__Group__1 : rule__Term__Group__1__Impl ;
    public final void rule__Term__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5937:1: ( rule__Term__Group__1__Impl )
            // InternalStructuredTextParser.g:5938:2: rule__Term__Group__1__Impl
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
    // InternalStructuredTextParser.g:5944:1: rule__Term__Group__1__Impl : ( ( rule__Term__Group_1__0 )* ) ;
    public final void rule__Term__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5948:1: ( ( ( rule__Term__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5949:1: ( ( rule__Term__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5949:1: ( ( rule__Term__Group_1__0 )* )
            // InternalStructuredTextParser.g:5950:2: ( rule__Term__Group_1__0 )*
            {
             before(grammarAccess.getTermAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5951:2: ( rule__Term__Group_1__0 )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==MOD||LA50_0==Asterisk||LA50_0==Solidus) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5951:3: rule__Term__Group_1__0
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
    // InternalStructuredTextParser.g:5960:1: rule__Term__Group_1__0 : rule__Term__Group_1__0__Impl rule__Term__Group_1__1 ;
    public final void rule__Term__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5964:1: ( rule__Term__Group_1__0__Impl rule__Term__Group_1__1 )
            // InternalStructuredTextParser.g:5965:2: rule__Term__Group_1__0__Impl rule__Term__Group_1__1
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
    // InternalStructuredTextParser.g:5972:1: rule__Term__Group_1__0__Impl : ( () ) ;
    public final void rule__Term__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5976:1: ( ( () ) )
            // InternalStructuredTextParser.g:5977:1: ( () )
            {
            // InternalStructuredTextParser.g:5977:1: ( () )
            // InternalStructuredTextParser.g:5978:2: ()
            {
             before(grammarAccess.getTermAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5979:2: ()
            // InternalStructuredTextParser.g:5979:3: 
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
    // InternalStructuredTextParser.g:5987:1: rule__Term__Group_1__1 : rule__Term__Group_1__1__Impl rule__Term__Group_1__2 ;
    public final void rule__Term__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5991:1: ( rule__Term__Group_1__1__Impl rule__Term__Group_1__2 )
            // InternalStructuredTextParser.g:5992:2: rule__Term__Group_1__1__Impl rule__Term__Group_1__2
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
    // InternalStructuredTextParser.g:5999:1: rule__Term__Group_1__1__Impl : ( ( rule__Term__OperatorAssignment_1_1 ) ) ;
    public final void rule__Term__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6003:1: ( ( ( rule__Term__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:6004:1: ( ( rule__Term__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:6004:1: ( ( rule__Term__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:6005:2: ( rule__Term__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getTermAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:6006:2: ( rule__Term__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:6006:3: rule__Term__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:6014:1: rule__Term__Group_1__2 : rule__Term__Group_1__2__Impl ;
    public final void rule__Term__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6018:1: ( rule__Term__Group_1__2__Impl )
            // InternalStructuredTextParser.g:6019:2: rule__Term__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:6025:1: rule__Term__Group_1__2__Impl : ( ( rule__Term__RightAssignment_1_2 ) ) ;
    public final void rule__Term__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6029:1: ( ( ( rule__Term__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:6030:1: ( ( rule__Term__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:6030:1: ( ( rule__Term__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:6031:2: ( rule__Term__RightAssignment_1_2 )
            {
             before(grammarAccess.getTermAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:6032:2: ( rule__Term__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:6032:3: rule__Term__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:6041:1: rule__Power_Expr__Group__0 : rule__Power_Expr__Group__0__Impl rule__Power_Expr__Group__1 ;
    public final void rule__Power_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6045:1: ( rule__Power_Expr__Group__0__Impl rule__Power_Expr__Group__1 )
            // InternalStructuredTextParser.g:6046:2: rule__Power_Expr__Group__0__Impl rule__Power_Expr__Group__1
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
    // InternalStructuredTextParser.g:6053:1: rule__Power_Expr__Group__0__Impl : ( ruleUnary_Expr ) ;
    public final void rule__Power_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6057:1: ( ( ruleUnary_Expr ) )
            // InternalStructuredTextParser.g:6058:1: ( ruleUnary_Expr )
            {
            // InternalStructuredTextParser.g:6058:1: ( ruleUnary_Expr )
            // InternalStructuredTextParser.g:6059:2: ruleUnary_Expr
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
    // InternalStructuredTextParser.g:6068:1: rule__Power_Expr__Group__1 : rule__Power_Expr__Group__1__Impl ;
    public final void rule__Power_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6072:1: ( rule__Power_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:6073:2: rule__Power_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:6079:1: rule__Power_Expr__Group__1__Impl : ( ( rule__Power_Expr__Group_1__0 )* ) ;
    public final void rule__Power_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6083:1: ( ( ( rule__Power_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:6084:1: ( ( rule__Power_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:6084:1: ( ( rule__Power_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:6085:2: ( rule__Power_Expr__Group_1__0 )*
            {
             before(grammarAccess.getPower_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:6086:2: ( rule__Power_Expr__Group_1__0 )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==AsteriskAsterisk) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalStructuredTextParser.g:6086:3: rule__Power_Expr__Group_1__0
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
    // InternalStructuredTextParser.g:6095:1: rule__Power_Expr__Group_1__0 : rule__Power_Expr__Group_1__0__Impl rule__Power_Expr__Group_1__1 ;
    public final void rule__Power_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6099:1: ( rule__Power_Expr__Group_1__0__Impl rule__Power_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:6100:2: rule__Power_Expr__Group_1__0__Impl rule__Power_Expr__Group_1__1
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
    // InternalStructuredTextParser.g:6107:1: rule__Power_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Power_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6111:1: ( ( () ) )
            // InternalStructuredTextParser.g:6112:1: ( () )
            {
            // InternalStructuredTextParser.g:6112:1: ( () )
            // InternalStructuredTextParser.g:6113:2: ()
            {
             before(grammarAccess.getPower_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:6114:2: ()
            // InternalStructuredTextParser.g:6114:3: 
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
    // InternalStructuredTextParser.g:6122:1: rule__Power_Expr__Group_1__1 : rule__Power_Expr__Group_1__1__Impl rule__Power_Expr__Group_1__2 ;
    public final void rule__Power_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6126:1: ( rule__Power_Expr__Group_1__1__Impl rule__Power_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:6127:2: rule__Power_Expr__Group_1__1__Impl rule__Power_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:6134:1: rule__Power_Expr__Group_1__1__Impl : ( ( rule__Power_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Power_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6138:1: ( ( ( rule__Power_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:6139:1: ( ( rule__Power_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:6139:1: ( ( rule__Power_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:6140:2: ( rule__Power_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getPower_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:6141:2: ( rule__Power_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:6141:3: rule__Power_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:6149:1: rule__Power_Expr__Group_1__2 : rule__Power_Expr__Group_1__2__Impl ;
    public final void rule__Power_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6153:1: ( rule__Power_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:6154:2: rule__Power_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:6160:1: rule__Power_Expr__Group_1__2__Impl : ( ( rule__Power_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Power_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6164:1: ( ( ( rule__Power_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:6165:1: ( ( rule__Power_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:6165:1: ( ( rule__Power_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:6166:2: ( rule__Power_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getPower_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:6167:2: ( rule__Power_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:6167:3: rule__Power_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:6176:1: rule__Unary_Expr__Group_0__0 : rule__Unary_Expr__Group_0__0__Impl rule__Unary_Expr__Group_0__1 ;
    public final void rule__Unary_Expr__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6180:1: ( rule__Unary_Expr__Group_0__0__Impl rule__Unary_Expr__Group_0__1 )
            // InternalStructuredTextParser.g:6181:2: rule__Unary_Expr__Group_0__0__Impl rule__Unary_Expr__Group_0__1
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
    // InternalStructuredTextParser.g:6188:1: rule__Unary_Expr__Group_0__0__Impl : ( () ) ;
    public final void rule__Unary_Expr__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6192:1: ( ( () ) )
            // InternalStructuredTextParser.g:6193:1: ( () )
            {
            // InternalStructuredTextParser.g:6193:1: ( () )
            // InternalStructuredTextParser.g:6194:2: ()
            {
             before(grammarAccess.getUnary_ExprAccess().getUnaryExpressionAction_0_0()); 
            // InternalStructuredTextParser.g:6195:2: ()
            // InternalStructuredTextParser.g:6195:3: 
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
    // InternalStructuredTextParser.g:6203:1: rule__Unary_Expr__Group_0__1 : rule__Unary_Expr__Group_0__1__Impl rule__Unary_Expr__Group_0__2 ;
    public final void rule__Unary_Expr__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6207:1: ( rule__Unary_Expr__Group_0__1__Impl rule__Unary_Expr__Group_0__2 )
            // InternalStructuredTextParser.g:6208:2: rule__Unary_Expr__Group_0__1__Impl rule__Unary_Expr__Group_0__2
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
    // InternalStructuredTextParser.g:6215:1: rule__Unary_Expr__Group_0__1__Impl : ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) ) ;
    public final void rule__Unary_Expr__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6219:1: ( ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) ) )
            // InternalStructuredTextParser.g:6220:1: ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) )
            {
            // InternalStructuredTextParser.g:6220:1: ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) )
            // InternalStructuredTextParser.g:6221:2: ( rule__Unary_Expr__OperatorAssignment_0_1 )
            {
             before(grammarAccess.getUnary_ExprAccess().getOperatorAssignment_0_1()); 
            // InternalStructuredTextParser.g:6222:2: ( rule__Unary_Expr__OperatorAssignment_0_1 )
            // InternalStructuredTextParser.g:6222:3: rule__Unary_Expr__OperatorAssignment_0_1
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
    // InternalStructuredTextParser.g:6230:1: rule__Unary_Expr__Group_0__2 : rule__Unary_Expr__Group_0__2__Impl ;
    public final void rule__Unary_Expr__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6234:1: ( rule__Unary_Expr__Group_0__2__Impl )
            // InternalStructuredTextParser.g:6235:2: rule__Unary_Expr__Group_0__2__Impl
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
    // InternalStructuredTextParser.g:6241:1: rule__Unary_Expr__Group_0__2__Impl : ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) ) ;
    public final void rule__Unary_Expr__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6245:1: ( ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) ) )
            // InternalStructuredTextParser.g:6246:1: ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) )
            {
            // InternalStructuredTextParser.g:6246:1: ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) )
            // InternalStructuredTextParser.g:6247:2: ( rule__Unary_Expr__ExpressionAssignment_0_2 )
            {
             before(grammarAccess.getUnary_ExprAccess().getExpressionAssignment_0_2()); 
            // InternalStructuredTextParser.g:6248:2: ( rule__Unary_Expr__ExpressionAssignment_0_2 )
            // InternalStructuredTextParser.g:6248:3: rule__Unary_Expr__ExpressionAssignment_0_2
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
    // InternalStructuredTextParser.g:6257:1: rule__Primary_Expr__Group_2__0 : rule__Primary_Expr__Group_2__0__Impl rule__Primary_Expr__Group_2__1 ;
    public final void rule__Primary_Expr__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6261:1: ( rule__Primary_Expr__Group_2__0__Impl rule__Primary_Expr__Group_2__1 )
            // InternalStructuredTextParser.g:6262:2: rule__Primary_Expr__Group_2__0__Impl rule__Primary_Expr__Group_2__1
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
    // InternalStructuredTextParser.g:6269:1: rule__Primary_Expr__Group_2__0__Impl : ( LeftParenthesis ) ;
    public final void rule__Primary_Expr__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6273:1: ( ( LeftParenthesis ) )
            // InternalStructuredTextParser.g:6274:1: ( LeftParenthesis )
            {
            // InternalStructuredTextParser.g:6274:1: ( LeftParenthesis )
            // InternalStructuredTextParser.g:6275:2: LeftParenthesis
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
    // InternalStructuredTextParser.g:6284:1: rule__Primary_Expr__Group_2__1 : rule__Primary_Expr__Group_2__1__Impl rule__Primary_Expr__Group_2__2 ;
    public final void rule__Primary_Expr__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6288:1: ( rule__Primary_Expr__Group_2__1__Impl rule__Primary_Expr__Group_2__2 )
            // InternalStructuredTextParser.g:6289:2: rule__Primary_Expr__Group_2__1__Impl rule__Primary_Expr__Group_2__2
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
    // InternalStructuredTextParser.g:6296:1: rule__Primary_Expr__Group_2__1__Impl : ( ruleExpression ) ;
    public final void rule__Primary_Expr__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6300:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:6301:1: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:6301:1: ( ruleExpression )
            // InternalStructuredTextParser.g:6302:2: ruleExpression
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
    // InternalStructuredTextParser.g:6311:1: rule__Primary_Expr__Group_2__2 : rule__Primary_Expr__Group_2__2__Impl ;
    public final void rule__Primary_Expr__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6315:1: ( rule__Primary_Expr__Group_2__2__Impl )
            // InternalStructuredTextParser.g:6316:2: rule__Primary_Expr__Group_2__2__Impl
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
    // InternalStructuredTextParser.g:6322:1: rule__Primary_Expr__Group_2__2__Impl : ( RightParenthesis ) ;
    public final void rule__Primary_Expr__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6326:1: ( ( RightParenthesis ) )
            // InternalStructuredTextParser.g:6327:1: ( RightParenthesis )
            {
            // InternalStructuredTextParser.g:6327:1: ( RightParenthesis )
            // InternalStructuredTextParser.g:6328:2: RightParenthesis
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
    // InternalStructuredTextParser.g:6338:1: rule__Func_Call__Group__0 : rule__Func_Call__Group__0__Impl rule__Func_Call__Group__1 ;
    public final void rule__Func_Call__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6342:1: ( rule__Func_Call__Group__0__Impl rule__Func_Call__Group__1 )
            // InternalStructuredTextParser.g:6343:2: rule__Func_Call__Group__0__Impl rule__Func_Call__Group__1
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
    // InternalStructuredTextParser.g:6350:1: rule__Func_Call__Group__0__Impl : ( ( rule__Func_Call__FuncAssignment_0 ) ) ;
    public final void rule__Func_Call__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6354:1: ( ( ( rule__Func_Call__FuncAssignment_0 ) ) )
            // InternalStructuredTextParser.g:6355:1: ( ( rule__Func_Call__FuncAssignment_0 ) )
            {
            // InternalStructuredTextParser.g:6355:1: ( ( rule__Func_Call__FuncAssignment_0 ) )
            // InternalStructuredTextParser.g:6356:2: ( rule__Func_Call__FuncAssignment_0 )
            {
             before(grammarAccess.getFunc_CallAccess().getFuncAssignment_0()); 
            // InternalStructuredTextParser.g:6357:2: ( rule__Func_Call__FuncAssignment_0 )
            // InternalStructuredTextParser.g:6357:3: rule__Func_Call__FuncAssignment_0
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
    // InternalStructuredTextParser.g:6365:1: rule__Func_Call__Group__1 : rule__Func_Call__Group__1__Impl rule__Func_Call__Group__2 ;
    public final void rule__Func_Call__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6369:1: ( rule__Func_Call__Group__1__Impl rule__Func_Call__Group__2 )
            // InternalStructuredTextParser.g:6370:2: rule__Func_Call__Group__1__Impl rule__Func_Call__Group__2
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
    // InternalStructuredTextParser.g:6377:1: rule__Func_Call__Group__1__Impl : ( LeftParenthesis ) ;
    public final void rule__Func_Call__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6381:1: ( ( LeftParenthesis ) )
            // InternalStructuredTextParser.g:6382:1: ( LeftParenthesis )
            {
            // InternalStructuredTextParser.g:6382:1: ( LeftParenthesis )
            // InternalStructuredTextParser.g:6383:2: LeftParenthesis
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
    // InternalStructuredTextParser.g:6392:1: rule__Func_Call__Group__2 : rule__Func_Call__Group__2__Impl rule__Func_Call__Group__3 ;
    public final void rule__Func_Call__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6396:1: ( rule__Func_Call__Group__2__Impl rule__Func_Call__Group__3 )
            // InternalStructuredTextParser.g:6397:2: rule__Func_Call__Group__2__Impl rule__Func_Call__Group__3
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
    // InternalStructuredTextParser.g:6404:1: rule__Func_Call__Group__2__Impl : ( ( rule__Func_Call__Group_2__0 )? ) ;
    public final void rule__Func_Call__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6408:1: ( ( ( rule__Func_Call__Group_2__0 )? ) )
            // InternalStructuredTextParser.g:6409:1: ( ( rule__Func_Call__Group_2__0 )? )
            {
            // InternalStructuredTextParser.g:6409:1: ( ( rule__Func_Call__Group_2__0 )? )
            // InternalStructuredTextParser.g:6410:2: ( rule__Func_Call__Group_2__0 )?
            {
             before(grammarAccess.getFunc_CallAccess().getGroup_2()); 
            // InternalStructuredTextParser.g:6411:2: ( rule__Func_Call__Group_2__0 )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==WSTRING||LA52_0==STRING||LA52_0==FALSE||LA52_0==LREAL||(LA52_0>=UDINT && LA52_0<=ULINT)||(LA52_0>=USINT && LA52_0<=WCHAR)||LA52_0==BOOL||LA52_0==CHAR||LA52_0==DINT||LA52_0==LINT||(LA52_0>=REAL && LA52_0<=SINT)||(LA52_0>=TIME && LA52_0<=UINT)||LA52_0==INT||LA52_0==NOT||LA52_0==DT||LA52_0==LT||LA52_0==LeftParenthesis||LA52_0==PlusSign||LA52_0==HyphenMinus||LA52_0==T||(LA52_0>=RULE_UNSIGNED_INT && LA52_0<=RULE_HEX_INT)||LA52_0==RULE_S_BYTE_CHAR_STR||LA52_0==RULE_D_BYTE_CHAR_STR) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // InternalStructuredTextParser.g:6411:3: rule__Func_Call__Group_2__0
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
    // InternalStructuredTextParser.g:6419:1: rule__Func_Call__Group__3 : rule__Func_Call__Group__3__Impl ;
    public final void rule__Func_Call__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6423:1: ( rule__Func_Call__Group__3__Impl )
            // InternalStructuredTextParser.g:6424:2: rule__Func_Call__Group__3__Impl
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
    // InternalStructuredTextParser.g:6430:1: rule__Func_Call__Group__3__Impl : ( RightParenthesis ) ;
    public final void rule__Func_Call__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6434:1: ( ( RightParenthesis ) )
            // InternalStructuredTextParser.g:6435:1: ( RightParenthesis )
            {
            // InternalStructuredTextParser.g:6435:1: ( RightParenthesis )
            // InternalStructuredTextParser.g:6436:2: RightParenthesis
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
    // InternalStructuredTextParser.g:6446:1: rule__Func_Call__Group_2__0 : rule__Func_Call__Group_2__0__Impl rule__Func_Call__Group_2__1 ;
    public final void rule__Func_Call__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6450:1: ( rule__Func_Call__Group_2__0__Impl rule__Func_Call__Group_2__1 )
            // InternalStructuredTextParser.g:6451:2: rule__Func_Call__Group_2__0__Impl rule__Func_Call__Group_2__1
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
    // InternalStructuredTextParser.g:6458:1: rule__Func_Call__Group_2__0__Impl : ( ( rule__Func_Call__ArgsAssignment_2_0 ) ) ;
    public final void rule__Func_Call__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6462:1: ( ( ( rule__Func_Call__ArgsAssignment_2_0 ) ) )
            // InternalStructuredTextParser.g:6463:1: ( ( rule__Func_Call__ArgsAssignment_2_0 ) )
            {
            // InternalStructuredTextParser.g:6463:1: ( ( rule__Func_Call__ArgsAssignment_2_0 ) )
            // InternalStructuredTextParser.g:6464:2: ( rule__Func_Call__ArgsAssignment_2_0 )
            {
             before(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_0()); 
            // InternalStructuredTextParser.g:6465:2: ( rule__Func_Call__ArgsAssignment_2_0 )
            // InternalStructuredTextParser.g:6465:3: rule__Func_Call__ArgsAssignment_2_0
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
    // InternalStructuredTextParser.g:6473:1: rule__Func_Call__Group_2__1 : rule__Func_Call__Group_2__1__Impl ;
    public final void rule__Func_Call__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6477:1: ( rule__Func_Call__Group_2__1__Impl )
            // InternalStructuredTextParser.g:6478:2: rule__Func_Call__Group_2__1__Impl
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
    // InternalStructuredTextParser.g:6484:1: rule__Func_Call__Group_2__1__Impl : ( ( rule__Func_Call__Group_2_1__0 )* ) ;
    public final void rule__Func_Call__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6488:1: ( ( ( rule__Func_Call__Group_2_1__0 )* ) )
            // InternalStructuredTextParser.g:6489:1: ( ( rule__Func_Call__Group_2_1__0 )* )
            {
            // InternalStructuredTextParser.g:6489:1: ( ( rule__Func_Call__Group_2_1__0 )* )
            // InternalStructuredTextParser.g:6490:2: ( rule__Func_Call__Group_2_1__0 )*
            {
             before(grammarAccess.getFunc_CallAccess().getGroup_2_1()); 
            // InternalStructuredTextParser.g:6491:2: ( rule__Func_Call__Group_2_1__0 )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==Comma) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalStructuredTextParser.g:6491:3: rule__Func_Call__Group_2_1__0
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
    // InternalStructuredTextParser.g:6500:1: rule__Func_Call__Group_2_1__0 : rule__Func_Call__Group_2_1__0__Impl rule__Func_Call__Group_2_1__1 ;
    public final void rule__Func_Call__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6504:1: ( rule__Func_Call__Group_2_1__0__Impl rule__Func_Call__Group_2_1__1 )
            // InternalStructuredTextParser.g:6505:2: rule__Func_Call__Group_2_1__0__Impl rule__Func_Call__Group_2_1__1
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
    // InternalStructuredTextParser.g:6512:1: rule__Func_Call__Group_2_1__0__Impl : ( Comma ) ;
    public final void rule__Func_Call__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6516:1: ( ( Comma ) )
            // InternalStructuredTextParser.g:6517:1: ( Comma )
            {
            // InternalStructuredTextParser.g:6517:1: ( Comma )
            // InternalStructuredTextParser.g:6518:2: Comma
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
    // InternalStructuredTextParser.g:6527:1: rule__Func_Call__Group_2_1__1 : rule__Func_Call__Group_2_1__1__Impl ;
    public final void rule__Func_Call__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6531:1: ( rule__Func_Call__Group_2_1__1__Impl )
            // InternalStructuredTextParser.g:6532:2: rule__Func_Call__Group_2_1__1__Impl
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
    // InternalStructuredTextParser.g:6538:1: rule__Func_Call__Group_2_1__1__Impl : ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) ) ;
    public final void rule__Func_Call__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6542:1: ( ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) ) )
            // InternalStructuredTextParser.g:6543:1: ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) )
            {
            // InternalStructuredTextParser.g:6543:1: ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) )
            // InternalStructuredTextParser.g:6544:2: ( rule__Func_Call__ArgsAssignment_2_1_1 )
            {
             before(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_1_1()); 
            // InternalStructuredTextParser.g:6545:2: ( rule__Func_Call__ArgsAssignment_2_1_1 )
            // InternalStructuredTextParser.g:6545:3: rule__Func_Call__ArgsAssignment_2_1_1
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
    // InternalStructuredTextParser.g:6554:1: rule__Param_Assign_In__Group__0 : rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1 ;
    public final void rule__Param_Assign_In__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6558:1: ( rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1 )
            // InternalStructuredTextParser.g:6559:2: rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1
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
    // InternalStructuredTextParser.g:6566:1: rule__Param_Assign_In__Group__0__Impl : ( ( rule__Param_Assign_In__Group_0__0 )? ) ;
    public final void rule__Param_Assign_In__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6570:1: ( ( ( rule__Param_Assign_In__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:6571:1: ( ( rule__Param_Assign_In__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:6571:1: ( ( rule__Param_Assign_In__Group_0__0 )? )
            // InternalStructuredTextParser.g:6572:2: ( rule__Param_Assign_In__Group_0__0 )?
            {
             before(grammarAccess.getParam_Assign_InAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:6573:2: ( rule__Param_Assign_In__Group_0__0 )?
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
                    // InternalStructuredTextParser.g:6573:3: rule__Param_Assign_In__Group_0__0
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
    // InternalStructuredTextParser.g:6581:1: rule__Param_Assign_In__Group__1 : rule__Param_Assign_In__Group__1__Impl ;
    public final void rule__Param_Assign_In__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6585:1: ( rule__Param_Assign_In__Group__1__Impl )
            // InternalStructuredTextParser.g:6586:2: rule__Param_Assign_In__Group__1__Impl
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
    // InternalStructuredTextParser.g:6592:1: rule__Param_Assign_In__Group__1__Impl : ( ( rule__Param_Assign_In__ExprAssignment_1 ) ) ;
    public final void rule__Param_Assign_In__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6596:1: ( ( ( rule__Param_Assign_In__ExprAssignment_1 ) ) )
            // InternalStructuredTextParser.g:6597:1: ( ( rule__Param_Assign_In__ExprAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:6597:1: ( ( rule__Param_Assign_In__ExprAssignment_1 ) )
            // InternalStructuredTextParser.g:6598:2: ( rule__Param_Assign_In__ExprAssignment_1 )
            {
             before(grammarAccess.getParam_Assign_InAccess().getExprAssignment_1()); 
            // InternalStructuredTextParser.g:6599:2: ( rule__Param_Assign_In__ExprAssignment_1 )
            // InternalStructuredTextParser.g:6599:3: rule__Param_Assign_In__ExprAssignment_1
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
    // InternalStructuredTextParser.g:6608:1: rule__Param_Assign_In__Group_0__0 : rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1 ;
    public final void rule__Param_Assign_In__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6612:1: ( rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1 )
            // InternalStructuredTextParser.g:6613:2: rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1
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
    // InternalStructuredTextParser.g:6620:1: rule__Param_Assign_In__Group_0__0__Impl : ( ( rule__Param_Assign_In__VarAssignment_0_0 ) ) ;
    public final void rule__Param_Assign_In__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6624:1: ( ( ( rule__Param_Assign_In__VarAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:6625:1: ( ( rule__Param_Assign_In__VarAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:6625:1: ( ( rule__Param_Assign_In__VarAssignment_0_0 ) )
            // InternalStructuredTextParser.g:6626:2: ( rule__Param_Assign_In__VarAssignment_0_0 )
            {
             before(grammarAccess.getParam_Assign_InAccess().getVarAssignment_0_0()); 
            // InternalStructuredTextParser.g:6627:2: ( rule__Param_Assign_In__VarAssignment_0_0 )
            // InternalStructuredTextParser.g:6627:3: rule__Param_Assign_In__VarAssignment_0_0
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
    // InternalStructuredTextParser.g:6635:1: rule__Param_Assign_In__Group_0__1 : rule__Param_Assign_In__Group_0__1__Impl ;
    public final void rule__Param_Assign_In__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6639:1: ( rule__Param_Assign_In__Group_0__1__Impl )
            // InternalStructuredTextParser.g:6640:2: rule__Param_Assign_In__Group_0__1__Impl
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
    // InternalStructuredTextParser.g:6646:1: rule__Param_Assign_In__Group_0__1__Impl : ( ColonEqualsSign ) ;
    public final void rule__Param_Assign_In__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6650:1: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:6651:1: ( ColonEqualsSign )
            {
            // InternalStructuredTextParser.g:6651:1: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:6652:2: ColonEqualsSign
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
    // InternalStructuredTextParser.g:6662:1: rule__Param_Assign_Out__Group__0 : rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1 ;
    public final void rule__Param_Assign_Out__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6666:1: ( rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1 )
            // InternalStructuredTextParser.g:6667:2: rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1
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
    // InternalStructuredTextParser.g:6674:1: rule__Param_Assign_Out__Group__0__Impl : ( ( rule__Param_Assign_Out__NotAssignment_0 )? ) ;
    public final void rule__Param_Assign_Out__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6678:1: ( ( ( rule__Param_Assign_Out__NotAssignment_0 )? ) )
            // InternalStructuredTextParser.g:6679:1: ( ( rule__Param_Assign_Out__NotAssignment_0 )? )
            {
            // InternalStructuredTextParser.g:6679:1: ( ( rule__Param_Assign_Out__NotAssignment_0 )? )
            // InternalStructuredTextParser.g:6680:2: ( rule__Param_Assign_Out__NotAssignment_0 )?
            {
             before(grammarAccess.getParam_Assign_OutAccess().getNotAssignment_0()); 
            // InternalStructuredTextParser.g:6681:2: ( rule__Param_Assign_Out__NotAssignment_0 )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==NOT) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalStructuredTextParser.g:6681:3: rule__Param_Assign_Out__NotAssignment_0
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
    // InternalStructuredTextParser.g:6689:1: rule__Param_Assign_Out__Group__1 : rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2 ;
    public final void rule__Param_Assign_Out__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6693:1: ( rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2 )
            // InternalStructuredTextParser.g:6694:2: rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2
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
    // InternalStructuredTextParser.g:6701:1: rule__Param_Assign_Out__Group__1__Impl : ( ( rule__Param_Assign_Out__VarAssignment_1 ) ) ;
    public final void rule__Param_Assign_Out__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6705:1: ( ( ( rule__Param_Assign_Out__VarAssignment_1 ) ) )
            // InternalStructuredTextParser.g:6706:1: ( ( rule__Param_Assign_Out__VarAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:6706:1: ( ( rule__Param_Assign_Out__VarAssignment_1 ) )
            // InternalStructuredTextParser.g:6707:2: ( rule__Param_Assign_Out__VarAssignment_1 )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getVarAssignment_1()); 
            // InternalStructuredTextParser.g:6708:2: ( rule__Param_Assign_Out__VarAssignment_1 )
            // InternalStructuredTextParser.g:6708:3: rule__Param_Assign_Out__VarAssignment_1
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
    // InternalStructuredTextParser.g:6716:1: rule__Param_Assign_Out__Group__2 : rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3 ;
    public final void rule__Param_Assign_Out__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6720:1: ( rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3 )
            // InternalStructuredTextParser.g:6721:2: rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3
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
    // InternalStructuredTextParser.g:6728:1: rule__Param_Assign_Out__Group__2__Impl : ( EqualsSignGreaterThanSign ) ;
    public final void rule__Param_Assign_Out__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6732:1: ( ( EqualsSignGreaterThanSign ) )
            // InternalStructuredTextParser.g:6733:1: ( EqualsSignGreaterThanSign )
            {
            // InternalStructuredTextParser.g:6733:1: ( EqualsSignGreaterThanSign )
            // InternalStructuredTextParser.g:6734:2: EqualsSignGreaterThanSign
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
    // InternalStructuredTextParser.g:6743:1: rule__Param_Assign_Out__Group__3 : rule__Param_Assign_Out__Group__3__Impl ;
    public final void rule__Param_Assign_Out__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6747:1: ( rule__Param_Assign_Out__Group__3__Impl )
            // InternalStructuredTextParser.g:6748:2: rule__Param_Assign_Out__Group__3__Impl
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
    // InternalStructuredTextParser.g:6754:1: rule__Param_Assign_Out__Group__3__Impl : ( ( rule__Param_Assign_Out__ExprAssignment_3 ) ) ;
    public final void rule__Param_Assign_Out__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6758:1: ( ( ( rule__Param_Assign_Out__ExprAssignment_3 ) ) )
            // InternalStructuredTextParser.g:6759:1: ( ( rule__Param_Assign_Out__ExprAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:6759:1: ( ( rule__Param_Assign_Out__ExprAssignment_3 ) )
            // InternalStructuredTextParser.g:6760:2: ( rule__Param_Assign_Out__ExprAssignment_3 )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getExprAssignment_3()); 
            // InternalStructuredTextParser.g:6761:2: ( rule__Param_Assign_Out__ExprAssignment_3 )
            // InternalStructuredTextParser.g:6761:3: rule__Param_Assign_Out__ExprAssignment_3
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
    // InternalStructuredTextParser.g:6770:1: rule__Variable__Group__0 : rule__Variable__Group__0__Impl rule__Variable__Group__1 ;
    public final void rule__Variable__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6774:1: ( rule__Variable__Group__0__Impl rule__Variable__Group__1 )
            // InternalStructuredTextParser.g:6775:2: rule__Variable__Group__0__Impl rule__Variable__Group__1
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
    // InternalStructuredTextParser.g:6782:1: rule__Variable__Group__0__Impl : ( ruleVariable_Subscript ) ;
    public final void rule__Variable__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6786:1: ( ( ruleVariable_Subscript ) )
            // InternalStructuredTextParser.g:6787:1: ( ruleVariable_Subscript )
            {
            // InternalStructuredTextParser.g:6787:1: ( ruleVariable_Subscript )
            // InternalStructuredTextParser.g:6788:2: ruleVariable_Subscript
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
    // InternalStructuredTextParser.g:6797:1: rule__Variable__Group__1 : rule__Variable__Group__1__Impl ;
    public final void rule__Variable__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6801:1: ( rule__Variable__Group__1__Impl )
            // InternalStructuredTextParser.g:6802:2: rule__Variable__Group__1__Impl
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
    // InternalStructuredTextParser.g:6808:1: rule__Variable__Group__1__Impl : ( ( rule__Variable__PartAssignment_1 )? ) ;
    public final void rule__Variable__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6812:1: ( ( ( rule__Variable__PartAssignment_1 )? ) )
            // InternalStructuredTextParser.g:6813:1: ( ( rule__Variable__PartAssignment_1 )? )
            {
            // InternalStructuredTextParser.g:6813:1: ( ( rule__Variable__PartAssignment_1 )? )
            // InternalStructuredTextParser.g:6814:2: ( rule__Variable__PartAssignment_1 )?
            {
             before(grammarAccess.getVariableAccess().getPartAssignment_1()); 
            // InternalStructuredTextParser.g:6815:2: ( rule__Variable__PartAssignment_1 )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( ((LA56_0>=B && LA56_0<=X)||LA56_0==FullStop) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // InternalStructuredTextParser.g:6815:3: rule__Variable__PartAssignment_1
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
    // InternalStructuredTextParser.g:6824:1: rule__Variable_Subscript__Group__0 : rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1 ;
    public final void rule__Variable_Subscript__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6828:1: ( rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1 )
            // InternalStructuredTextParser.g:6829:2: rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1
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
    // InternalStructuredTextParser.g:6836:1: rule__Variable_Subscript__Group__0__Impl : ( ( rule__Variable_Subscript__Alternatives_0 ) ) ;
    public final void rule__Variable_Subscript__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6840:1: ( ( ( rule__Variable_Subscript__Alternatives_0 ) ) )
            // InternalStructuredTextParser.g:6841:1: ( ( rule__Variable_Subscript__Alternatives_0 ) )
            {
            // InternalStructuredTextParser.g:6841:1: ( ( rule__Variable_Subscript__Alternatives_0 ) )
            // InternalStructuredTextParser.g:6842:2: ( rule__Variable_Subscript__Alternatives_0 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getAlternatives_0()); 
            // InternalStructuredTextParser.g:6843:2: ( rule__Variable_Subscript__Alternatives_0 )
            // InternalStructuredTextParser.g:6843:3: rule__Variable_Subscript__Alternatives_0
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
    // InternalStructuredTextParser.g:6851:1: rule__Variable_Subscript__Group__1 : rule__Variable_Subscript__Group__1__Impl ;
    public final void rule__Variable_Subscript__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6855:1: ( rule__Variable_Subscript__Group__1__Impl )
            // InternalStructuredTextParser.g:6856:2: rule__Variable_Subscript__Group__1__Impl
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
    // InternalStructuredTextParser.g:6862:1: rule__Variable_Subscript__Group__1__Impl : ( ( rule__Variable_Subscript__Group_1__0 )? ) ;
    public final void rule__Variable_Subscript__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6866:1: ( ( ( rule__Variable_Subscript__Group_1__0 )? ) )
            // InternalStructuredTextParser.g:6867:1: ( ( rule__Variable_Subscript__Group_1__0 )? )
            {
            // InternalStructuredTextParser.g:6867:1: ( ( rule__Variable_Subscript__Group_1__0 )? )
            // InternalStructuredTextParser.g:6868:2: ( rule__Variable_Subscript__Group_1__0 )?
            {
             before(grammarAccess.getVariable_SubscriptAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:6869:2: ( rule__Variable_Subscript__Group_1__0 )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==LeftSquareBracket) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalStructuredTextParser.g:6869:3: rule__Variable_Subscript__Group_1__0
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
    // InternalStructuredTextParser.g:6878:1: rule__Variable_Subscript__Group_1__0 : rule__Variable_Subscript__Group_1__0__Impl rule__Variable_Subscript__Group_1__1 ;
    public final void rule__Variable_Subscript__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6882:1: ( rule__Variable_Subscript__Group_1__0__Impl rule__Variable_Subscript__Group_1__1 )
            // InternalStructuredTextParser.g:6883:2: rule__Variable_Subscript__Group_1__0__Impl rule__Variable_Subscript__Group_1__1
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
    // InternalStructuredTextParser.g:6890:1: rule__Variable_Subscript__Group_1__0__Impl : ( () ) ;
    public final void rule__Variable_Subscript__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6894:1: ( ( () ) )
            // InternalStructuredTextParser.g:6895:1: ( () )
            {
            // InternalStructuredTextParser.g:6895:1: ( () )
            // InternalStructuredTextParser.g:6896:2: ()
            {
             before(grammarAccess.getVariable_SubscriptAccess().getArrayVariableArrayAction_1_0()); 
            // InternalStructuredTextParser.g:6897:2: ()
            // InternalStructuredTextParser.g:6897:3: 
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
    // InternalStructuredTextParser.g:6905:1: rule__Variable_Subscript__Group_1__1 : rule__Variable_Subscript__Group_1__1__Impl rule__Variable_Subscript__Group_1__2 ;
    public final void rule__Variable_Subscript__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6909:1: ( rule__Variable_Subscript__Group_1__1__Impl rule__Variable_Subscript__Group_1__2 )
            // InternalStructuredTextParser.g:6910:2: rule__Variable_Subscript__Group_1__1__Impl rule__Variable_Subscript__Group_1__2
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
    // InternalStructuredTextParser.g:6917:1: rule__Variable_Subscript__Group_1__1__Impl : ( LeftSquareBracket ) ;
    public final void rule__Variable_Subscript__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6921:1: ( ( LeftSquareBracket ) )
            // InternalStructuredTextParser.g:6922:1: ( LeftSquareBracket )
            {
            // InternalStructuredTextParser.g:6922:1: ( LeftSquareBracket )
            // InternalStructuredTextParser.g:6923:2: LeftSquareBracket
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
    // InternalStructuredTextParser.g:6932:1: rule__Variable_Subscript__Group_1__2 : rule__Variable_Subscript__Group_1__2__Impl rule__Variable_Subscript__Group_1__3 ;
    public final void rule__Variable_Subscript__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6936:1: ( rule__Variable_Subscript__Group_1__2__Impl rule__Variable_Subscript__Group_1__3 )
            // InternalStructuredTextParser.g:6937:2: rule__Variable_Subscript__Group_1__2__Impl rule__Variable_Subscript__Group_1__3
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
    // InternalStructuredTextParser.g:6944:1: rule__Variable_Subscript__Group_1__2__Impl : ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) ) ;
    public final void rule__Variable_Subscript__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6948:1: ( ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:6949:1: ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:6949:1: ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) )
            // InternalStructuredTextParser.g:6950:2: ( rule__Variable_Subscript__IndexAssignment_1_2 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_2()); 
            // InternalStructuredTextParser.g:6951:2: ( rule__Variable_Subscript__IndexAssignment_1_2 )
            // InternalStructuredTextParser.g:6951:3: rule__Variable_Subscript__IndexAssignment_1_2
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
    // InternalStructuredTextParser.g:6959:1: rule__Variable_Subscript__Group_1__3 : rule__Variable_Subscript__Group_1__3__Impl rule__Variable_Subscript__Group_1__4 ;
    public final void rule__Variable_Subscript__Group_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6963:1: ( rule__Variable_Subscript__Group_1__3__Impl rule__Variable_Subscript__Group_1__4 )
            // InternalStructuredTextParser.g:6964:2: rule__Variable_Subscript__Group_1__3__Impl rule__Variable_Subscript__Group_1__4
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
    // InternalStructuredTextParser.g:6971:1: rule__Variable_Subscript__Group_1__3__Impl : ( ( rule__Variable_Subscript__Group_1_3__0 )* ) ;
    public final void rule__Variable_Subscript__Group_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6975:1: ( ( ( rule__Variable_Subscript__Group_1_3__0 )* ) )
            // InternalStructuredTextParser.g:6976:1: ( ( rule__Variable_Subscript__Group_1_3__0 )* )
            {
            // InternalStructuredTextParser.g:6976:1: ( ( rule__Variable_Subscript__Group_1_3__0 )* )
            // InternalStructuredTextParser.g:6977:2: ( rule__Variable_Subscript__Group_1_3__0 )*
            {
             before(grammarAccess.getVariable_SubscriptAccess().getGroup_1_3()); 
            // InternalStructuredTextParser.g:6978:2: ( rule__Variable_Subscript__Group_1_3__0 )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==Comma) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // InternalStructuredTextParser.g:6978:3: rule__Variable_Subscript__Group_1_3__0
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
    // InternalStructuredTextParser.g:6986:1: rule__Variable_Subscript__Group_1__4 : rule__Variable_Subscript__Group_1__4__Impl ;
    public final void rule__Variable_Subscript__Group_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6990:1: ( rule__Variable_Subscript__Group_1__4__Impl )
            // InternalStructuredTextParser.g:6991:2: rule__Variable_Subscript__Group_1__4__Impl
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
    // InternalStructuredTextParser.g:6997:1: rule__Variable_Subscript__Group_1__4__Impl : ( RightSquareBracket ) ;
    public final void rule__Variable_Subscript__Group_1__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7001:1: ( ( RightSquareBracket ) )
            // InternalStructuredTextParser.g:7002:1: ( RightSquareBracket )
            {
            // InternalStructuredTextParser.g:7002:1: ( RightSquareBracket )
            // InternalStructuredTextParser.g:7003:2: RightSquareBracket
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
    // InternalStructuredTextParser.g:7013:1: rule__Variable_Subscript__Group_1_3__0 : rule__Variable_Subscript__Group_1_3__0__Impl rule__Variable_Subscript__Group_1_3__1 ;
    public final void rule__Variable_Subscript__Group_1_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7017:1: ( rule__Variable_Subscript__Group_1_3__0__Impl rule__Variable_Subscript__Group_1_3__1 )
            // InternalStructuredTextParser.g:7018:2: rule__Variable_Subscript__Group_1_3__0__Impl rule__Variable_Subscript__Group_1_3__1
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
    // InternalStructuredTextParser.g:7025:1: rule__Variable_Subscript__Group_1_3__0__Impl : ( Comma ) ;
    public final void rule__Variable_Subscript__Group_1_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7029:1: ( ( Comma ) )
            // InternalStructuredTextParser.g:7030:1: ( Comma )
            {
            // InternalStructuredTextParser.g:7030:1: ( Comma )
            // InternalStructuredTextParser.g:7031:2: Comma
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
    // InternalStructuredTextParser.g:7040:1: rule__Variable_Subscript__Group_1_3__1 : rule__Variable_Subscript__Group_1_3__1__Impl ;
    public final void rule__Variable_Subscript__Group_1_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7044:1: ( rule__Variable_Subscript__Group_1_3__1__Impl )
            // InternalStructuredTextParser.g:7045:2: rule__Variable_Subscript__Group_1_3__1__Impl
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
    // InternalStructuredTextParser.g:7051:1: rule__Variable_Subscript__Group_1_3__1__Impl : ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) ) ;
    public final void rule__Variable_Subscript__Group_1_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7055:1: ( ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) ) )
            // InternalStructuredTextParser.g:7056:1: ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) )
            {
            // InternalStructuredTextParser.g:7056:1: ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) )
            // InternalStructuredTextParser.g:7057:2: ( rule__Variable_Subscript__IndexAssignment_1_3_1 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_3_1()); 
            // InternalStructuredTextParser.g:7058:2: ( rule__Variable_Subscript__IndexAssignment_1_3_1 )
            // InternalStructuredTextParser.g:7058:3: rule__Variable_Subscript__IndexAssignment_1_3_1
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
    // InternalStructuredTextParser.g:7067:1: rule__Variable_Adapter__Group__0 : rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1 ;
    public final void rule__Variable_Adapter__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7071:1: ( rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1 )
            // InternalStructuredTextParser.g:7072:2: rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1
            {
            pushFollow(FOLLOW_11);
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
    // InternalStructuredTextParser.g:7079:1: rule__Variable_Adapter__Group__0__Impl : ( () ) ;
    public final void rule__Variable_Adapter__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7083:1: ( ( () ) )
            // InternalStructuredTextParser.g:7084:1: ( () )
            {
            // InternalStructuredTextParser.g:7084:1: ( () )
            // InternalStructuredTextParser.g:7085:2: ()
            {
             before(grammarAccess.getVariable_AdapterAccess().getAdapterVariableAction_0()); 
            // InternalStructuredTextParser.g:7086:2: ()
            // InternalStructuredTextParser.g:7086:3: 
            {
            }

             after(grammarAccess.getVariable_AdapterAccess().getAdapterVariableAction_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group__0__Impl"


    // $ANTLR start "rule__Variable_Adapter__Group__1"
    // InternalStructuredTextParser.g:7094:1: rule__Variable_Adapter__Group__1 : rule__Variable_Adapter__Group__1__Impl rule__Variable_Adapter__Group__2 ;
    public final void rule__Variable_Adapter__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7098:1: ( rule__Variable_Adapter__Group__1__Impl rule__Variable_Adapter__Group__2 )
            // InternalStructuredTextParser.g:7099:2: rule__Variable_Adapter__Group__1__Impl rule__Variable_Adapter__Group__2
            {
            pushFollow(FOLLOW_64);
            rule__Variable_Adapter__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__Group__2();

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
    // InternalStructuredTextParser.g:7106:1: rule__Variable_Adapter__Group__1__Impl : ( ( rule__Variable_Adapter__AdapterAssignment_1 ) ) ;
    public final void rule__Variable_Adapter__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7110:1: ( ( ( rule__Variable_Adapter__AdapterAssignment_1 ) ) )
            // InternalStructuredTextParser.g:7111:1: ( ( rule__Variable_Adapter__AdapterAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:7111:1: ( ( rule__Variable_Adapter__AdapterAssignment_1 ) )
            // InternalStructuredTextParser.g:7112:2: ( rule__Variable_Adapter__AdapterAssignment_1 )
            {
             before(grammarAccess.getVariable_AdapterAccess().getAdapterAssignment_1()); 
            // InternalStructuredTextParser.g:7113:2: ( rule__Variable_Adapter__AdapterAssignment_1 )
            // InternalStructuredTextParser.g:7113:3: rule__Variable_Adapter__AdapterAssignment_1
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__AdapterAssignment_1();

            state._fsp--;


            }

             after(grammarAccess.getVariable_AdapterAccess().getAdapterAssignment_1()); 

            }


            }

        }
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


    // $ANTLR start "rule__Variable_Adapter__Group__2"
    // InternalStructuredTextParser.g:7121:1: rule__Variable_Adapter__Group__2 : rule__Variable_Adapter__Group__2__Impl rule__Variable_Adapter__Group__3 ;
    public final void rule__Variable_Adapter__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7125:1: ( rule__Variable_Adapter__Group__2__Impl rule__Variable_Adapter__Group__3 )
            // InternalStructuredTextParser.g:7126:2: rule__Variable_Adapter__Group__2__Impl rule__Variable_Adapter__Group__3
            {
            pushFollow(FOLLOW_11);
            rule__Variable_Adapter__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__Group__3();

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
    // $ANTLR end "rule__Variable_Adapter__Group__2"


    // $ANTLR start "rule__Variable_Adapter__Group__2__Impl"
    // InternalStructuredTextParser.g:7133:1: rule__Variable_Adapter__Group__2__Impl : ( FullStop ) ;
    public final void rule__Variable_Adapter__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7137:1: ( ( FullStop ) )
            // InternalStructuredTextParser.g:7138:1: ( FullStop )
            {
            // InternalStructuredTextParser.g:7138:1: ( FullStop )
            // InternalStructuredTextParser.g:7139:2: FullStop
            {
             before(grammarAccess.getVariable_AdapterAccess().getFullStopKeyword_2()); 
            match(input,FullStop,FOLLOW_2); 
             after(grammarAccess.getVariable_AdapterAccess().getFullStopKeyword_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group__2__Impl"


    // $ANTLR start "rule__Variable_Adapter__Group__3"
    // InternalStructuredTextParser.g:7148:1: rule__Variable_Adapter__Group__3 : rule__Variable_Adapter__Group__3__Impl ;
    public final void rule__Variable_Adapter__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7152:1: ( rule__Variable_Adapter__Group__3__Impl )
            // InternalStructuredTextParser.g:7153:2: rule__Variable_Adapter__Group__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__Group__3__Impl();

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
    // $ANTLR end "rule__Variable_Adapter__Group__3"


    // $ANTLR start "rule__Variable_Adapter__Group__3__Impl"
    // InternalStructuredTextParser.g:7159:1: rule__Variable_Adapter__Group__3__Impl : ( ( rule__Variable_Adapter__VarAssignment_3 ) ) ;
    public final void rule__Variable_Adapter__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7163:1: ( ( ( rule__Variable_Adapter__VarAssignment_3 ) ) )
            // InternalStructuredTextParser.g:7164:1: ( ( rule__Variable_Adapter__VarAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:7164:1: ( ( rule__Variable_Adapter__VarAssignment_3 ) )
            // InternalStructuredTextParser.g:7165:2: ( rule__Variable_Adapter__VarAssignment_3 )
            {
             before(grammarAccess.getVariable_AdapterAccess().getVarAssignment_3()); 
            // InternalStructuredTextParser.g:7166:2: ( rule__Variable_Adapter__VarAssignment_3 )
            // InternalStructuredTextParser.g:7166:3: rule__Variable_Adapter__VarAssignment_3
            {
            pushFollow(FOLLOW_2);
            rule__Variable_Adapter__VarAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getVariable_AdapterAccess().getVarAssignment_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__Group__3__Impl"


    // $ANTLR start "rule__Multibit_Part_Access__Group_0__0"
    // InternalStructuredTextParser.g:7175:1: rule__Multibit_Part_Access__Group_0__0 : rule__Multibit_Part_Access__Group_0__0__Impl rule__Multibit_Part_Access__Group_0__1 ;
    public final void rule__Multibit_Part_Access__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7179:1: ( rule__Multibit_Part_Access__Group_0__0__Impl rule__Multibit_Part_Access__Group_0__1 )
            // InternalStructuredTextParser.g:7180:2: rule__Multibit_Part_Access__Group_0__0__Impl rule__Multibit_Part_Access__Group_0__1
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
    // InternalStructuredTextParser.g:7187:1: rule__Multibit_Part_Access__Group_0__0__Impl : ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7191:1: ( ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:7192:1: ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:7192:1: ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) )
            // InternalStructuredTextParser.g:7193:2: ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessAssignment_0_0()); 
            // InternalStructuredTextParser.g:7194:2: ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 )
            // InternalStructuredTextParser.g:7194:3: rule__Multibit_Part_Access__DwordaccessAssignment_0_0
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
    // InternalStructuredTextParser.g:7202:1: rule__Multibit_Part_Access__Group_0__1 : rule__Multibit_Part_Access__Group_0__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7206:1: ( rule__Multibit_Part_Access__Group_0__1__Impl )
            // InternalStructuredTextParser.g:7207:2: rule__Multibit_Part_Access__Group_0__1__Impl
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
    // InternalStructuredTextParser.g:7213:1: rule__Multibit_Part_Access__Group_0__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7217:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) ) )
            // InternalStructuredTextParser.g:7218:1: ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) )
            {
            // InternalStructuredTextParser.g:7218:1: ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) )
            // InternalStructuredTextParser.g:7219:2: ( rule__Multibit_Part_Access__IndexAssignment_0_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_0_1()); 
            // InternalStructuredTextParser.g:7220:2: ( rule__Multibit_Part_Access__IndexAssignment_0_1 )
            // InternalStructuredTextParser.g:7220:3: rule__Multibit_Part_Access__IndexAssignment_0_1
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
    // InternalStructuredTextParser.g:7229:1: rule__Multibit_Part_Access__Group_1__0 : rule__Multibit_Part_Access__Group_1__0__Impl rule__Multibit_Part_Access__Group_1__1 ;
    public final void rule__Multibit_Part_Access__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7233:1: ( rule__Multibit_Part_Access__Group_1__0__Impl rule__Multibit_Part_Access__Group_1__1 )
            // InternalStructuredTextParser.g:7234:2: rule__Multibit_Part_Access__Group_1__0__Impl rule__Multibit_Part_Access__Group_1__1
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
    // InternalStructuredTextParser.g:7241:1: rule__Multibit_Part_Access__Group_1__0__Impl : ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7245:1: ( ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) ) )
            // InternalStructuredTextParser.g:7246:1: ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) )
            {
            // InternalStructuredTextParser.g:7246:1: ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) )
            // InternalStructuredTextParser.g:7247:2: ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessAssignment_1_0()); 
            // InternalStructuredTextParser.g:7248:2: ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 )
            // InternalStructuredTextParser.g:7248:3: rule__Multibit_Part_Access__WordaccessAssignment_1_0
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
    // InternalStructuredTextParser.g:7256:1: rule__Multibit_Part_Access__Group_1__1 : rule__Multibit_Part_Access__Group_1__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7260:1: ( rule__Multibit_Part_Access__Group_1__1__Impl )
            // InternalStructuredTextParser.g:7261:2: rule__Multibit_Part_Access__Group_1__1__Impl
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
    // InternalStructuredTextParser.g:7267:1: rule__Multibit_Part_Access__Group_1__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7271:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:7272:1: ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:7272:1: ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) )
            // InternalStructuredTextParser.g:7273:2: ( rule__Multibit_Part_Access__IndexAssignment_1_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_1_1()); 
            // InternalStructuredTextParser.g:7274:2: ( rule__Multibit_Part_Access__IndexAssignment_1_1 )
            // InternalStructuredTextParser.g:7274:3: rule__Multibit_Part_Access__IndexAssignment_1_1
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
    // InternalStructuredTextParser.g:7283:1: rule__Multibit_Part_Access__Group_2__0 : rule__Multibit_Part_Access__Group_2__0__Impl rule__Multibit_Part_Access__Group_2__1 ;
    public final void rule__Multibit_Part_Access__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7287:1: ( rule__Multibit_Part_Access__Group_2__0__Impl rule__Multibit_Part_Access__Group_2__1 )
            // InternalStructuredTextParser.g:7288:2: rule__Multibit_Part_Access__Group_2__0__Impl rule__Multibit_Part_Access__Group_2__1
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
    // InternalStructuredTextParser.g:7295:1: rule__Multibit_Part_Access__Group_2__0__Impl : ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7299:1: ( ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) ) )
            // InternalStructuredTextParser.g:7300:1: ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) )
            {
            // InternalStructuredTextParser.g:7300:1: ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) )
            // InternalStructuredTextParser.g:7301:2: ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessAssignment_2_0()); 
            // InternalStructuredTextParser.g:7302:2: ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 )
            // InternalStructuredTextParser.g:7302:3: rule__Multibit_Part_Access__ByteaccessAssignment_2_0
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
    // InternalStructuredTextParser.g:7310:1: rule__Multibit_Part_Access__Group_2__1 : rule__Multibit_Part_Access__Group_2__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7314:1: ( rule__Multibit_Part_Access__Group_2__1__Impl )
            // InternalStructuredTextParser.g:7315:2: rule__Multibit_Part_Access__Group_2__1__Impl
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
    // InternalStructuredTextParser.g:7321:1: rule__Multibit_Part_Access__Group_2__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7325:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) ) )
            // InternalStructuredTextParser.g:7326:1: ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) )
            {
            // InternalStructuredTextParser.g:7326:1: ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) )
            // InternalStructuredTextParser.g:7327:2: ( rule__Multibit_Part_Access__IndexAssignment_2_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_2_1()); 
            // InternalStructuredTextParser.g:7328:2: ( rule__Multibit_Part_Access__IndexAssignment_2_1 )
            // InternalStructuredTextParser.g:7328:3: rule__Multibit_Part_Access__IndexAssignment_2_1
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
    // InternalStructuredTextParser.g:7337:1: rule__Multibit_Part_Access__Group_3__0 : rule__Multibit_Part_Access__Group_3__0__Impl rule__Multibit_Part_Access__Group_3__1 ;
    public final void rule__Multibit_Part_Access__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7341:1: ( rule__Multibit_Part_Access__Group_3__0__Impl rule__Multibit_Part_Access__Group_3__1 )
            // InternalStructuredTextParser.g:7342:2: rule__Multibit_Part_Access__Group_3__0__Impl rule__Multibit_Part_Access__Group_3__1
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
    // InternalStructuredTextParser.g:7349:1: rule__Multibit_Part_Access__Group_3__0__Impl : ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7353:1: ( ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) ) )
            // InternalStructuredTextParser.g:7354:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) )
            {
            // InternalStructuredTextParser.g:7354:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) )
            // InternalStructuredTextParser.g:7355:2: ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_3_0()); 
            // InternalStructuredTextParser.g:7356:2: ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 )
            // InternalStructuredTextParser.g:7356:3: rule__Multibit_Part_Access__BitaccessAssignment_3_0
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
    // InternalStructuredTextParser.g:7364:1: rule__Multibit_Part_Access__Group_3__1 : rule__Multibit_Part_Access__Group_3__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7368:1: ( rule__Multibit_Part_Access__Group_3__1__Impl )
            // InternalStructuredTextParser.g:7369:2: rule__Multibit_Part_Access__Group_3__1__Impl
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
    // InternalStructuredTextParser.g:7375:1: rule__Multibit_Part_Access__Group_3__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7379:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) ) )
            // InternalStructuredTextParser.g:7380:1: ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) )
            {
            // InternalStructuredTextParser.g:7380:1: ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) )
            // InternalStructuredTextParser.g:7381:2: ( rule__Multibit_Part_Access__IndexAssignment_3_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_3_1()); 
            // InternalStructuredTextParser.g:7382:2: ( rule__Multibit_Part_Access__IndexAssignment_3_1 )
            // InternalStructuredTextParser.g:7382:3: rule__Multibit_Part_Access__IndexAssignment_3_1
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
    // InternalStructuredTextParser.g:7391:1: rule__Multibit_Part_Access__Group_4__0 : rule__Multibit_Part_Access__Group_4__0__Impl rule__Multibit_Part_Access__Group_4__1 ;
    public final void rule__Multibit_Part_Access__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7395:1: ( rule__Multibit_Part_Access__Group_4__0__Impl rule__Multibit_Part_Access__Group_4__1 )
            // InternalStructuredTextParser.g:7396:2: rule__Multibit_Part_Access__Group_4__0__Impl rule__Multibit_Part_Access__Group_4__1
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
    // InternalStructuredTextParser.g:7403:1: rule__Multibit_Part_Access__Group_4__0__Impl : ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7407:1: ( ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) ) )
            // InternalStructuredTextParser.g:7408:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) )
            {
            // InternalStructuredTextParser.g:7408:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) )
            // InternalStructuredTextParser.g:7409:2: ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_4_0()); 
            // InternalStructuredTextParser.g:7410:2: ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 )
            // InternalStructuredTextParser.g:7410:3: rule__Multibit_Part_Access__BitaccessAssignment_4_0
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
    // InternalStructuredTextParser.g:7418:1: rule__Multibit_Part_Access__Group_4__1 : rule__Multibit_Part_Access__Group_4__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7422:1: ( rule__Multibit_Part_Access__Group_4__1__Impl )
            // InternalStructuredTextParser.g:7423:2: rule__Multibit_Part_Access__Group_4__1__Impl
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
    // InternalStructuredTextParser.g:7429:1: rule__Multibit_Part_Access__Group_4__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7433:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) ) )
            // InternalStructuredTextParser.g:7434:1: ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) )
            {
            // InternalStructuredTextParser.g:7434:1: ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) )
            // InternalStructuredTextParser.g:7435:2: ( rule__Multibit_Part_Access__IndexAssignment_4_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_4_1()); 
            // InternalStructuredTextParser.g:7436:2: ( rule__Multibit_Part_Access__IndexAssignment_4_1 )
            // InternalStructuredTextParser.g:7436:3: rule__Multibit_Part_Access__IndexAssignment_4_1
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
    // InternalStructuredTextParser.g:7445:1: rule__Int_Literal__Group__0 : rule__Int_Literal__Group__0__Impl rule__Int_Literal__Group__1 ;
    public final void rule__Int_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7449:1: ( rule__Int_Literal__Group__0__Impl rule__Int_Literal__Group__1 )
            // InternalStructuredTextParser.g:7450:2: rule__Int_Literal__Group__0__Impl rule__Int_Literal__Group__1
            {
            pushFollow(FOLLOW_65);
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
    // InternalStructuredTextParser.g:7457:1: rule__Int_Literal__Group__0__Impl : ( ( rule__Int_Literal__Group_0__0 )? ) ;
    public final void rule__Int_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7461:1: ( ( ( rule__Int_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:7462:1: ( ( rule__Int_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:7462:1: ( ( rule__Int_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:7463:2: ( rule__Int_Literal__Group_0__0 )?
            {
             before(grammarAccess.getInt_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:7464:2: ( rule__Int_Literal__Group_0__0 )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( ((LA59_0>=UDINT && LA59_0<=ULINT)||LA59_0==USINT||LA59_0==DINT||LA59_0==LINT||LA59_0==SINT||LA59_0==UINT||LA59_0==INT) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalStructuredTextParser.g:7464:3: rule__Int_Literal__Group_0__0
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
    // InternalStructuredTextParser.g:7472:1: rule__Int_Literal__Group__1 : rule__Int_Literal__Group__1__Impl ;
    public final void rule__Int_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7476:1: ( rule__Int_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:7477:2: rule__Int_Literal__Group__1__Impl
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
    // InternalStructuredTextParser.g:7483:1: rule__Int_Literal__Group__1__Impl : ( ( rule__Int_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Int_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7487:1: ( ( ( rule__Int_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:7488:1: ( ( rule__Int_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:7488:1: ( ( rule__Int_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:7489:2: ( rule__Int_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getInt_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:7490:2: ( rule__Int_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:7490:3: rule__Int_Literal__ValueAssignment_1
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
    // InternalStructuredTextParser.g:7499:1: rule__Int_Literal__Group_0__0 : rule__Int_Literal__Group_0__0__Impl rule__Int_Literal__Group_0__1 ;
    public final void rule__Int_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7503:1: ( rule__Int_Literal__Group_0__0__Impl rule__Int_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:7504:2: rule__Int_Literal__Group_0__0__Impl rule__Int_Literal__Group_0__1
            {
            pushFollow(FOLLOW_66);
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
    // InternalStructuredTextParser.g:7511:1: rule__Int_Literal__Group_0__0__Impl : ( ( rule__Int_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Int_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7515:1: ( ( ( rule__Int_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:7516:1: ( ( rule__Int_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:7516:1: ( ( rule__Int_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:7517:2: ( rule__Int_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getInt_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:7518:2: ( rule__Int_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:7518:3: rule__Int_Literal__TypeAssignment_0_0
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
    // InternalStructuredTextParser.g:7526:1: rule__Int_Literal__Group_0__1 : rule__Int_Literal__Group_0__1__Impl ;
    public final void rule__Int_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7530:1: ( rule__Int_Literal__Group_0__1__Impl )
            // InternalStructuredTextParser.g:7531:2: rule__Int_Literal__Group_0__1__Impl
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
    // InternalStructuredTextParser.g:7537:1: rule__Int_Literal__Group_0__1__Impl : ( NumberSign ) ;
    public final void rule__Int_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7541:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:7542:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:7542:1: ( NumberSign )
            // InternalStructuredTextParser.g:7543:2: NumberSign
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
    // InternalStructuredTextParser.g:7553:1: rule__Signed_Int__Group__0 : rule__Signed_Int__Group__0__Impl rule__Signed_Int__Group__1 ;
    public final void rule__Signed_Int__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7557:1: ( rule__Signed_Int__Group__0__Impl rule__Signed_Int__Group__1 )
            // InternalStructuredTextParser.g:7558:2: rule__Signed_Int__Group__0__Impl rule__Signed_Int__Group__1
            {
            pushFollow(FOLLOW_67);
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
    // InternalStructuredTextParser.g:7565:1: rule__Signed_Int__Group__0__Impl : ( ( rule__Signed_Int__Alternatives_0 )? ) ;
    public final void rule__Signed_Int__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7569:1: ( ( ( rule__Signed_Int__Alternatives_0 )? ) )
            // InternalStructuredTextParser.g:7570:1: ( ( rule__Signed_Int__Alternatives_0 )? )
            {
            // InternalStructuredTextParser.g:7570:1: ( ( rule__Signed_Int__Alternatives_0 )? )
            // InternalStructuredTextParser.g:7571:2: ( rule__Signed_Int__Alternatives_0 )?
            {
             before(grammarAccess.getSigned_IntAccess().getAlternatives_0()); 
            // InternalStructuredTextParser.g:7572:2: ( rule__Signed_Int__Alternatives_0 )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==PlusSign||LA60_0==HyphenMinus) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // InternalStructuredTextParser.g:7572:3: rule__Signed_Int__Alternatives_0
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
    // InternalStructuredTextParser.g:7580:1: rule__Signed_Int__Group__1 : rule__Signed_Int__Group__1__Impl ;
    public final void rule__Signed_Int__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7584:1: ( rule__Signed_Int__Group__1__Impl )
            // InternalStructuredTextParser.g:7585:2: rule__Signed_Int__Group__1__Impl
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
    // InternalStructuredTextParser.g:7591:1: rule__Signed_Int__Group__1__Impl : ( RULE_UNSIGNED_INT ) ;
    public final void rule__Signed_Int__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7595:1: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:7596:1: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:7596:1: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:7597:2: RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:7607:1: rule__Real_Literal__Group__0 : rule__Real_Literal__Group__0__Impl rule__Real_Literal__Group__1 ;
    public final void rule__Real_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7611:1: ( rule__Real_Literal__Group__0__Impl rule__Real_Literal__Group__1 )
            // InternalStructuredTextParser.g:7612:2: rule__Real_Literal__Group__0__Impl rule__Real_Literal__Group__1
            {
            pushFollow(FOLLOW_68);
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
    // InternalStructuredTextParser.g:7619:1: rule__Real_Literal__Group__0__Impl : ( ( rule__Real_Literal__Group_0__0 )? ) ;
    public final void rule__Real_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7623:1: ( ( ( rule__Real_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:7624:1: ( ( rule__Real_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:7624:1: ( ( rule__Real_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:7625:2: ( rule__Real_Literal__Group_0__0 )?
            {
             before(grammarAccess.getReal_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:7626:2: ( rule__Real_Literal__Group_0__0 )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==LREAL||LA61_0==REAL) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalStructuredTextParser.g:7626:3: rule__Real_Literal__Group_0__0
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
    // InternalStructuredTextParser.g:7634:1: rule__Real_Literal__Group__1 : rule__Real_Literal__Group__1__Impl ;
    public final void rule__Real_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7638:1: ( rule__Real_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:7639:2: rule__Real_Literal__Group__1__Impl
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
    // InternalStructuredTextParser.g:7645:1: rule__Real_Literal__Group__1__Impl : ( ( rule__Real_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Real_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7649:1: ( ( ( rule__Real_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:7650:1: ( ( rule__Real_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:7650:1: ( ( rule__Real_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:7651:2: ( rule__Real_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getReal_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:7652:2: ( rule__Real_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:7652:3: rule__Real_Literal__ValueAssignment_1
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
    // InternalStructuredTextParser.g:7661:1: rule__Real_Literal__Group_0__0 : rule__Real_Literal__Group_0__0__Impl rule__Real_Literal__Group_0__1 ;
    public final void rule__Real_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7665:1: ( rule__Real_Literal__Group_0__0__Impl rule__Real_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:7666:2: rule__Real_Literal__Group_0__0__Impl rule__Real_Literal__Group_0__1
            {
            pushFollow(FOLLOW_66);
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
    // InternalStructuredTextParser.g:7673:1: rule__Real_Literal__Group_0__0__Impl : ( ( rule__Real_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Real_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7677:1: ( ( ( rule__Real_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:7678:1: ( ( rule__Real_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:7678:1: ( ( rule__Real_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:7679:2: ( rule__Real_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getReal_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:7680:2: ( rule__Real_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:7680:3: rule__Real_Literal__TypeAssignment_0_0
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
    // InternalStructuredTextParser.g:7688:1: rule__Real_Literal__Group_0__1 : rule__Real_Literal__Group_0__1__Impl ;
    public final void rule__Real_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7692:1: ( rule__Real_Literal__Group_0__1__Impl )
            // InternalStructuredTextParser.g:7693:2: rule__Real_Literal__Group_0__1__Impl
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
    // InternalStructuredTextParser.g:7699:1: rule__Real_Literal__Group_0__1__Impl : ( NumberSign ) ;
    public final void rule__Real_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7703:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:7704:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:7704:1: ( NumberSign )
            // InternalStructuredTextParser.g:7705:2: NumberSign
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
    // InternalStructuredTextParser.g:7715:1: rule__Real_Value__Group__0 : rule__Real_Value__Group__0__Impl rule__Real_Value__Group__1 ;
    public final void rule__Real_Value__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7719:1: ( rule__Real_Value__Group__0__Impl rule__Real_Value__Group__1 )
            // InternalStructuredTextParser.g:7720:2: rule__Real_Value__Group__0__Impl rule__Real_Value__Group__1
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
    // InternalStructuredTextParser.g:7727:1: rule__Real_Value__Group__0__Impl : ( ruleSigned_Int ) ;
    public final void rule__Real_Value__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7731:1: ( ( ruleSigned_Int ) )
            // InternalStructuredTextParser.g:7732:1: ( ruleSigned_Int )
            {
            // InternalStructuredTextParser.g:7732:1: ( ruleSigned_Int )
            // InternalStructuredTextParser.g:7733:2: ruleSigned_Int
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
    // InternalStructuredTextParser.g:7742:1: rule__Real_Value__Group__1 : rule__Real_Value__Group__1__Impl rule__Real_Value__Group__2 ;
    public final void rule__Real_Value__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7746:1: ( rule__Real_Value__Group__1__Impl rule__Real_Value__Group__2 )
            // InternalStructuredTextParser.g:7747:2: rule__Real_Value__Group__1__Impl rule__Real_Value__Group__2
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
    // InternalStructuredTextParser.g:7754:1: rule__Real_Value__Group__1__Impl : ( FullStop ) ;
    public final void rule__Real_Value__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7758:1: ( ( FullStop ) )
            // InternalStructuredTextParser.g:7759:1: ( FullStop )
            {
            // InternalStructuredTextParser.g:7759:1: ( FullStop )
            // InternalStructuredTextParser.g:7760:2: FullStop
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
    // InternalStructuredTextParser.g:7769:1: rule__Real_Value__Group__2 : rule__Real_Value__Group__2__Impl rule__Real_Value__Group__3 ;
    public final void rule__Real_Value__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7773:1: ( rule__Real_Value__Group__2__Impl rule__Real_Value__Group__3 )
            // InternalStructuredTextParser.g:7774:2: rule__Real_Value__Group__2__Impl rule__Real_Value__Group__3
            {
            pushFollow(FOLLOW_69);
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
    // InternalStructuredTextParser.g:7781:1: rule__Real_Value__Group__2__Impl : ( RULE_UNSIGNED_INT ) ;
    public final void rule__Real_Value__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7785:1: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:7786:1: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:7786:1: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:7787:2: RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:7796:1: rule__Real_Value__Group__3 : rule__Real_Value__Group__3__Impl ;
    public final void rule__Real_Value__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7800:1: ( rule__Real_Value__Group__3__Impl )
            // InternalStructuredTextParser.g:7801:2: rule__Real_Value__Group__3__Impl
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
    // InternalStructuredTextParser.g:7807:1: rule__Real_Value__Group__3__Impl : ( ( rule__Real_Value__Group_3__0 )? ) ;
    public final void rule__Real_Value__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7811:1: ( ( ( rule__Real_Value__Group_3__0 )? ) )
            // InternalStructuredTextParser.g:7812:1: ( ( rule__Real_Value__Group_3__0 )? )
            {
            // InternalStructuredTextParser.g:7812:1: ( ( rule__Real_Value__Group_3__0 )? )
            // InternalStructuredTextParser.g:7813:2: ( rule__Real_Value__Group_3__0 )?
            {
             before(grammarAccess.getReal_ValueAccess().getGroup_3()); 
            // InternalStructuredTextParser.g:7814:2: ( rule__Real_Value__Group_3__0 )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==E) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalStructuredTextParser.g:7814:3: rule__Real_Value__Group_3__0
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
    // InternalStructuredTextParser.g:7823:1: rule__Real_Value__Group_3__0 : rule__Real_Value__Group_3__0__Impl rule__Real_Value__Group_3__1 ;
    public final void rule__Real_Value__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7827:1: ( rule__Real_Value__Group_3__0__Impl rule__Real_Value__Group_3__1 )
            // InternalStructuredTextParser.g:7828:2: rule__Real_Value__Group_3__0__Impl rule__Real_Value__Group_3__1
            {
            pushFollow(FOLLOW_67);
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
    // InternalStructuredTextParser.g:7835:1: rule__Real_Value__Group_3__0__Impl : ( E ) ;
    public final void rule__Real_Value__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7839:1: ( ( E ) )
            // InternalStructuredTextParser.g:7840:1: ( E )
            {
            // InternalStructuredTextParser.g:7840:1: ( E )
            // InternalStructuredTextParser.g:7841:2: E
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
    // InternalStructuredTextParser.g:7850:1: rule__Real_Value__Group_3__1 : rule__Real_Value__Group_3__1__Impl ;
    public final void rule__Real_Value__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7854:1: ( rule__Real_Value__Group_3__1__Impl )
            // InternalStructuredTextParser.g:7855:2: rule__Real_Value__Group_3__1__Impl
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
    // InternalStructuredTextParser.g:7861:1: rule__Real_Value__Group_3__1__Impl : ( ruleSigned_Int ) ;
    public final void rule__Real_Value__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7865:1: ( ( ruleSigned_Int ) )
            // InternalStructuredTextParser.g:7866:1: ( ruleSigned_Int )
            {
            // InternalStructuredTextParser.g:7866:1: ( ruleSigned_Int )
            // InternalStructuredTextParser.g:7867:2: ruleSigned_Int
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
    // InternalStructuredTextParser.g:7877:1: rule__Bool_Literal__Group__0 : rule__Bool_Literal__Group__0__Impl rule__Bool_Literal__Group__1 ;
    public final void rule__Bool_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7881:1: ( rule__Bool_Literal__Group__0__Impl rule__Bool_Literal__Group__1 )
            // InternalStructuredTextParser.g:7882:2: rule__Bool_Literal__Group__0__Impl rule__Bool_Literal__Group__1
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
    // InternalStructuredTextParser.g:7889:1: rule__Bool_Literal__Group__0__Impl : ( ( rule__Bool_Literal__Group_0__0 )? ) ;
    public final void rule__Bool_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7893:1: ( ( ( rule__Bool_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:7894:1: ( ( rule__Bool_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:7894:1: ( ( rule__Bool_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:7895:2: ( rule__Bool_Literal__Group_0__0 )?
            {
             before(grammarAccess.getBool_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:7896:2: ( rule__Bool_Literal__Group_0__0 )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==BOOL) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalStructuredTextParser.g:7896:3: rule__Bool_Literal__Group_0__0
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
    // InternalStructuredTextParser.g:7904:1: rule__Bool_Literal__Group__1 : rule__Bool_Literal__Group__1__Impl ;
    public final void rule__Bool_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7908:1: ( rule__Bool_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:7909:2: rule__Bool_Literal__Group__1__Impl
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
    // InternalStructuredTextParser.g:7915:1: rule__Bool_Literal__Group__1__Impl : ( ( rule__Bool_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Bool_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7919:1: ( ( ( rule__Bool_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:7920:1: ( ( rule__Bool_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:7920:1: ( ( rule__Bool_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:7921:2: ( rule__Bool_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getBool_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:7922:2: ( rule__Bool_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:7922:3: rule__Bool_Literal__ValueAssignment_1
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
    // InternalStructuredTextParser.g:7931:1: rule__Bool_Literal__Group_0__0 : rule__Bool_Literal__Group_0__0__Impl rule__Bool_Literal__Group_0__1 ;
    public final void rule__Bool_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7935:1: ( rule__Bool_Literal__Group_0__0__Impl rule__Bool_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:7936:2: rule__Bool_Literal__Group_0__0__Impl rule__Bool_Literal__Group_0__1
            {
            pushFollow(FOLLOW_66);
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
    // InternalStructuredTextParser.g:7943:1: rule__Bool_Literal__Group_0__0__Impl : ( ( rule__Bool_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Bool_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7947:1: ( ( ( rule__Bool_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:7948:1: ( ( rule__Bool_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:7948:1: ( ( rule__Bool_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:7949:2: ( rule__Bool_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getBool_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:7950:2: ( rule__Bool_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:7950:3: rule__Bool_Literal__TypeAssignment_0_0
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
    // InternalStructuredTextParser.g:7958:1: rule__Bool_Literal__Group_0__1 : rule__Bool_Literal__Group_0__1__Impl ;
    public final void rule__Bool_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7962:1: ( rule__Bool_Literal__Group_0__1__Impl )
            // InternalStructuredTextParser.g:7963:2: rule__Bool_Literal__Group_0__1__Impl
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
    // InternalStructuredTextParser.g:7969:1: rule__Bool_Literal__Group_0__1__Impl : ( NumberSign ) ;
    public final void rule__Bool_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7973:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:7974:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:7974:1: ( NumberSign )
            // InternalStructuredTextParser.g:7975:2: NumberSign
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
    // InternalStructuredTextParser.g:7985:1: rule__Char_Literal__Group__0 : rule__Char_Literal__Group__0__Impl rule__Char_Literal__Group__1 ;
    public final void rule__Char_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7989:1: ( rule__Char_Literal__Group__0__Impl rule__Char_Literal__Group__1 )
            // InternalStructuredTextParser.g:7990:2: rule__Char_Literal__Group__0__Impl rule__Char_Literal__Group__1
            {
            pushFollow(FOLLOW_70);
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
    // InternalStructuredTextParser.g:7997:1: rule__Char_Literal__Group__0__Impl : ( ( rule__Char_Literal__Group_0__0 )? ) ;
    public final void rule__Char_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8001:1: ( ( ( rule__Char_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:8002:1: ( ( rule__Char_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:8002:1: ( ( rule__Char_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:8003:2: ( rule__Char_Literal__Group_0__0 )?
            {
             before(grammarAccess.getChar_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:8004:2: ( rule__Char_Literal__Group_0__0 )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==WSTRING||LA64_0==STRING||LA64_0==WCHAR||LA64_0==CHAR) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalStructuredTextParser.g:8004:3: rule__Char_Literal__Group_0__0
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
    // InternalStructuredTextParser.g:8012:1: rule__Char_Literal__Group__1 : rule__Char_Literal__Group__1__Impl ;
    public final void rule__Char_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8016:1: ( rule__Char_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:8017:2: rule__Char_Literal__Group__1__Impl
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
    // InternalStructuredTextParser.g:8023:1: rule__Char_Literal__Group__1__Impl : ( ( rule__Char_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Char_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8027:1: ( ( ( rule__Char_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:8028:1: ( ( rule__Char_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:8028:1: ( ( rule__Char_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:8029:2: ( rule__Char_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getChar_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:8030:2: ( rule__Char_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:8030:3: rule__Char_Literal__ValueAssignment_1
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
    // InternalStructuredTextParser.g:8039:1: rule__Char_Literal__Group_0__0 : rule__Char_Literal__Group_0__0__Impl rule__Char_Literal__Group_0__1 ;
    public final void rule__Char_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8043:1: ( rule__Char_Literal__Group_0__0__Impl rule__Char_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:8044:2: rule__Char_Literal__Group_0__0__Impl rule__Char_Literal__Group_0__1
            {
            pushFollow(FOLLOW_71);
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
    // InternalStructuredTextParser.g:8051:1: rule__Char_Literal__Group_0__0__Impl : ( ( rule__Char_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Char_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8055:1: ( ( ( rule__Char_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:8056:1: ( ( rule__Char_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:8056:1: ( ( rule__Char_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:8057:2: ( rule__Char_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getChar_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:8058:2: ( rule__Char_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:8058:3: rule__Char_Literal__TypeAssignment_0_0
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
    // InternalStructuredTextParser.g:8066:1: rule__Char_Literal__Group_0__1 : rule__Char_Literal__Group_0__1__Impl rule__Char_Literal__Group_0__2 ;
    public final void rule__Char_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8070:1: ( rule__Char_Literal__Group_0__1__Impl rule__Char_Literal__Group_0__2 )
            // InternalStructuredTextParser.g:8071:2: rule__Char_Literal__Group_0__1__Impl rule__Char_Literal__Group_0__2
            {
            pushFollow(FOLLOW_71);
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
    // InternalStructuredTextParser.g:8078:1: rule__Char_Literal__Group_0__1__Impl : ( ( rule__Char_Literal__LengthAssignment_0_1 )? ) ;
    public final void rule__Char_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8082:1: ( ( ( rule__Char_Literal__LengthAssignment_0_1 )? ) )
            // InternalStructuredTextParser.g:8083:1: ( ( rule__Char_Literal__LengthAssignment_0_1 )? )
            {
            // InternalStructuredTextParser.g:8083:1: ( ( rule__Char_Literal__LengthAssignment_0_1 )? )
            // InternalStructuredTextParser.g:8084:2: ( rule__Char_Literal__LengthAssignment_0_1 )?
            {
             before(grammarAccess.getChar_LiteralAccess().getLengthAssignment_0_1()); 
            // InternalStructuredTextParser.g:8085:2: ( rule__Char_Literal__LengthAssignment_0_1 )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==RULE_UNSIGNED_INT) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalStructuredTextParser.g:8085:3: rule__Char_Literal__LengthAssignment_0_1
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
    // InternalStructuredTextParser.g:8093:1: rule__Char_Literal__Group_0__2 : rule__Char_Literal__Group_0__2__Impl ;
    public final void rule__Char_Literal__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8097:1: ( rule__Char_Literal__Group_0__2__Impl )
            // InternalStructuredTextParser.g:8098:2: rule__Char_Literal__Group_0__2__Impl
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
    // InternalStructuredTextParser.g:8104:1: rule__Char_Literal__Group_0__2__Impl : ( NumberSign ) ;
    public final void rule__Char_Literal__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8108:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:8109:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:8109:1: ( NumberSign )
            // InternalStructuredTextParser.g:8110:2: NumberSign
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
    // InternalStructuredTextParser.g:8120:1: rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 : ( ruleVar_Decl_Init ) ;
    public final void rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8124:1: ( ( ruleVar_Decl_Init ) )
            // InternalStructuredTextParser.g:8125:2: ( ruleVar_Decl_Init )
            {
            // InternalStructuredTextParser.g:8125:2: ( ruleVar_Decl_Init )
            // InternalStructuredTextParser.g:8126:3: ruleVar_Decl_Init
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
    // InternalStructuredTextParser.g:8135:1: rule__StructuredTextAlgorithm__StatementsAssignment_2 : ( ruleStmt_List ) ;
    public final void rule__StructuredTextAlgorithm__StatementsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8139:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8140:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8140:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8141:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8150:1: rule__Var_Decl_Local__ConstantAssignment_1 : ( ( CONSTANT ) ) ;
    public final void rule__Var_Decl_Local__ConstantAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8154:1: ( ( ( CONSTANT ) ) )
            // InternalStructuredTextParser.g:8155:2: ( ( CONSTANT ) )
            {
            // InternalStructuredTextParser.g:8155:2: ( ( CONSTANT ) )
            // InternalStructuredTextParser.g:8156:3: ( CONSTANT )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0()); 
            // InternalStructuredTextParser.g:8157:3: ( CONSTANT )
            // InternalStructuredTextParser.g:8158:4: CONSTANT
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
    // InternalStructuredTextParser.g:8169:1: rule__Var_Decl_Local__NameAssignment_2 : ( RULE_ID ) ;
    public final void rule__Var_Decl_Local__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8173:1: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:8174:2: ( RULE_ID )
            {
            // InternalStructuredTextParser.g:8174:2: ( RULE_ID )
            // InternalStructuredTextParser.g:8175:3: RULE_ID
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
    // InternalStructuredTextParser.g:8184:1: rule__Var_Decl_Local__LocatedAssignment_3_0 : ( ( AT ) ) ;
    public final void rule__Var_Decl_Local__LocatedAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8188:1: ( ( ( AT ) ) )
            // InternalStructuredTextParser.g:8189:2: ( ( AT ) )
            {
            // InternalStructuredTextParser.g:8189:2: ( ( AT ) )
            // InternalStructuredTextParser.g:8190:3: ( AT )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocatedATKeyword_3_0_0()); 
            // InternalStructuredTextParser.g:8191:3: ( AT )
            // InternalStructuredTextParser.g:8192:4: AT
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
    // InternalStructuredTextParser.g:8203:1: rule__Var_Decl_Local__LocationAssignment_3_1 : ( ruleVariable ) ;
    public final void rule__Var_Decl_Local__LocationAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8207:1: ( ( ruleVariable ) )
            // InternalStructuredTextParser.g:8208:2: ( ruleVariable )
            {
            // InternalStructuredTextParser.g:8208:2: ( ruleVariable )
            // InternalStructuredTextParser.g:8209:3: ruleVariable
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
    // InternalStructuredTextParser.g:8218:1: rule__Var_Decl_Local__ArrayAssignment_5_0 : ( ( ARRAY ) ) ;
    public final void rule__Var_Decl_Local__ArrayAssignment_5_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8222:1: ( ( ( ARRAY ) ) )
            // InternalStructuredTextParser.g:8223:2: ( ( ARRAY ) )
            {
            // InternalStructuredTextParser.g:8223:2: ( ( ARRAY ) )
            // InternalStructuredTextParser.g:8224:3: ( ARRAY )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayARRAYKeyword_5_0_0()); 
            // InternalStructuredTextParser.g:8225:3: ( ARRAY )
            // InternalStructuredTextParser.g:8226:4: ARRAY
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
    // InternalStructuredTextParser.g:8237:1: rule__Var_Decl_Local__ArrayStartAssignment_5_2 : ( ruleArray_Size ) ;
    public final void rule__Var_Decl_Local__ArrayStartAssignment_5_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8241:1: ( ( ruleArray_Size ) )
            // InternalStructuredTextParser.g:8242:2: ( ruleArray_Size )
            {
            // InternalStructuredTextParser.g:8242:2: ( ruleArray_Size )
            // InternalStructuredTextParser.g:8243:3: ruleArray_Size
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
    // InternalStructuredTextParser.g:8252:1: rule__Var_Decl_Local__ArrayStopAssignment_5_4 : ( ruleArray_Size ) ;
    public final void rule__Var_Decl_Local__ArrayStopAssignment_5_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8256:1: ( ( ruleArray_Size ) )
            // InternalStructuredTextParser.g:8257:2: ( ruleArray_Size )
            {
            // InternalStructuredTextParser.g:8257:2: ( ruleArray_Size )
            // InternalStructuredTextParser.g:8258:3: ruleArray_Size
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
    // InternalStructuredTextParser.g:8267:1: rule__Var_Decl_Local__TypeAssignment_6 : ( ( ruleType_Name ) ) ;
    public final void rule__Var_Decl_Local__TypeAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8271:1: ( ( ( ruleType_Name ) ) )
            // InternalStructuredTextParser.g:8272:2: ( ( ruleType_Name ) )
            {
            // InternalStructuredTextParser.g:8272:2: ( ( ruleType_Name ) )
            // InternalStructuredTextParser.g:8273:3: ( ruleType_Name )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeCrossReference_6_0()); 
            // InternalStructuredTextParser.g:8274:3: ( ruleType_Name )
            // InternalStructuredTextParser.g:8275:4: ruleType_Name
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
    // InternalStructuredTextParser.g:8286:1: rule__Var_Decl_Local__InitalizedAssignment_7_0 : ( ( ColonEqualsSign ) ) ;
    public final void rule__Var_Decl_Local__InitalizedAssignment_7_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8290:1: ( ( ( ColonEqualsSign ) ) )
            // InternalStructuredTextParser.g:8291:2: ( ( ColonEqualsSign ) )
            {
            // InternalStructuredTextParser.g:8291:2: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:8292:3: ( ColonEqualsSign )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitalizedColonEqualsSignKeyword_7_0_0()); 
            // InternalStructuredTextParser.g:8293:3: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:8294:4: ColonEqualsSign
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
    // InternalStructuredTextParser.g:8305:1: rule__Var_Decl_Local__InitialValueAssignment_7_1 : ( ruleConstant ) ;
    public final void rule__Var_Decl_Local__InitialValueAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8309:1: ( ( ruleConstant ) )
            // InternalStructuredTextParser.g:8310:2: ( ruleConstant )
            {
            // InternalStructuredTextParser.g:8310:2: ( ruleConstant )
            // InternalStructuredTextParser.g:8311:3: ruleConstant
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
    // InternalStructuredTextParser.g:8320:1: rule__Stmt_List__StatementsAssignment_1_0 : ( ruleStmt ) ;
    public final void rule__Stmt_List__StatementsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8324:1: ( ( ruleStmt ) )
            // InternalStructuredTextParser.g:8325:2: ( ruleStmt )
            {
            // InternalStructuredTextParser.g:8325:2: ( ruleStmt )
            // InternalStructuredTextParser.g:8326:3: ruleStmt
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
    // InternalStructuredTextParser.g:8335:1: rule__Assign_Stmt__VariableAssignment_0 : ( ruleVariable ) ;
    public final void rule__Assign_Stmt__VariableAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8339:1: ( ( ruleVariable ) )
            // InternalStructuredTextParser.g:8340:2: ( ruleVariable )
            {
            // InternalStructuredTextParser.g:8340:2: ( ruleVariable )
            // InternalStructuredTextParser.g:8341:3: ruleVariable
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
    // InternalStructuredTextParser.g:8350:1: rule__Assign_Stmt__ExpressionAssignment_2 : ( ruleExpression ) ;
    public final void rule__Assign_Stmt__ExpressionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8354:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8355:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8355:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8356:3: ruleExpression
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
    // InternalStructuredTextParser.g:8365:1: rule__IF_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__IF_Stmt__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8369:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8370:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8370:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8371:3: ruleExpression
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
    // InternalStructuredTextParser.g:8380:1: rule__IF_Stmt__StatmentsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__IF_Stmt__StatmentsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8384:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8385:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8385:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8386:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8395:1: rule__IF_Stmt__ElseifAssignment_4 : ( ruleELSIF_Clause ) ;
    public final void rule__IF_Stmt__ElseifAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8399:1: ( ( ruleELSIF_Clause ) )
            // InternalStructuredTextParser.g:8400:2: ( ruleELSIF_Clause )
            {
            // InternalStructuredTextParser.g:8400:2: ( ruleELSIF_Clause )
            // InternalStructuredTextParser.g:8401:3: ruleELSIF_Clause
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
    // InternalStructuredTextParser.g:8410:1: rule__IF_Stmt__ElseAssignment_5 : ( ruleELSE_Clause ) ;
    public final void rule__IF_Stmt__ElseAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8414:1: ( ( ruleELSE_Clause ) )
            // InternalStructuredTextParser.g:8415:2: ( ruleELSE_Clause )
            {
            // InternalStructuredTextParser.g:8415:2: ( ruleELSE_Clause )
            // InternalStructuredTextParser.g:8416:3: ruleELSE_Clause
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
    // InternalStructuredTextParser.g:8425:1: rule__ELSIF_Clause__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__ELSIF_Clause__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8429:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8430:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8430:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8431:3: ruleExpression
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
    // InternalStructuredTextParser.g:8440:1: rule__ELSIF_Clause__StatementsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__ELSIF_Clause__StatementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8444:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8445:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8445:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8446:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8455:1: rule__ELSE_Clause__StatementsAssignment_1 : ( ruleStmt_List ) ;
    public final void rule__ELSE_Clause__StatementsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8459:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8460:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8460:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8461:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8470:1: rule__Case_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__Case_Stmt__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8474:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8475:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8475:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8476:3: ruleExpression
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
    // InternalStructuredTextParser.g:8485:1: rule__Case_Stmt__CaseAssignment_3 : ( ruleCase_Selection ) ;
    public final void rule__Case_Stmt__CaseAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8489:1: ( ( ruleCase_Selection ) )
            // InternalStructuredTextParser.g:8490:2: ( ruleCase_Selection )
            {
            // InternalStructuredTextParser.g:8490:2: ( ruleCase_Selection )
            // InternalStructuredTextParser.g:8491:3: ruleCase_Selection
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
    // InternalStructuredTextParser.g:8500:1: rule__Case_Stmt__ElseAssignment_4 : ( ruleELSE_Clause ) ;
    public final void rule__Case_Stmt__ElseAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8504:1: ( ( ruleELSE_Clause ) )
            // InternalStructuredTextParser.g:8505:2: ( ruleELSE_Clause )
            {
            // InternalStructuredTextParser.g:8505:2: ( ruleELSE_Clause )
            // InternalStructuredTextParser.g:8506:3: ruleELSE_Clause
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
    // InternalStructuredTextParser.g:8515:1: rule__Case_Selection__CaseAssignment_0 : ( ruleConstant ) ;
    public final void rule__Case_Selection__CaseAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8519:1: ( ( ruleConstant ) )
            // InternalStructuredTextParser.g:8520:2: ( ruleConstant )
            {
            // InternalStructuredTextParser.g:8520:2: ( ruleConstant )
            // InternalStructuredTextParser.g:8521:3: ruleConstant
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
    // InternalStructuredTextParser.g:8530:1: rule__Case_Selection__CaseAssignment_1_1 : ( ruleConstant ) ;
    public final void rule__Case_Selection__CaseAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8534:1: ( ( ruleConstant ) )
            // InternalStructuredTextParser.g:8535:2: ( ruleConstant )
            {
            // InternalStructuredTextParser.g:8535:2: ( ruleConstant )
            // InternalStructuredTextParser.g:8536:3: ruleConstant
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
    // InternalStructuredTextParser.g:8545:1: rule__Case_Selection__StatementsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__Case_Selection__StatementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8549:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8550:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8550:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8551:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8560:1: rule__For_Stmt__VariableAssignment_1 : ( ruleVariable_Primary ) ;
    public final void rule__For_Stmt__VariableAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8564:1: ( ( ruleVariable_Primary ) )
            // InternalStructuredTextParser.g:8565:2: ( ruleVariable_Primary )
            {
            // InternalStructuredTextParser.g:8565:2: ( ruleVariable_Primary )
            // InternalStructuredTextParser.g:8566:3: ruleVariable_Primary
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
    // InternalStructuredTextParser.g:8575:1: rule__For_Stmt__FromAssignment_3 : ( ruleExpression ) ;
    public final void rule__For_Stmt__FromAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8579:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8580:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8580:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8581:3: ruleExpression
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
    // InternalStructuredTextParser.g:8590:1: rule__For_Stmt__ToAssignment_5 : ( ruleExpression ) ;
    public final void rule__For_Stmt__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8594:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8595:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8595:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8596:3: ruleExpression
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
    // InternalStructuredTextParser.g:8605:1: rule__For_Stmt__ByAssignment_6_1 : ( ruleExpression ) ;
    public final void rule__For_Stmt__ByAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8609:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8610:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8610:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8611:3: ruleExpression
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
    // InternalStructuredTextParser.g:8620:1: rule__For_Stmt__StatementsAssignment_8 : ( ruleStmt_List ) ;
    public final void rule__For_Stmt__StatementsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8624:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8625:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8625:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8626:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8635:1: rule__While_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__While_Stmt__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8639:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8640:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8640:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8641:3: ruleExpression
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
    // InternalStructuredTextParser.g:8650:1: rule__While_Stmt__StatementsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__While_Stmt__StatementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8654:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8655:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8655:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8656:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8665:1: rule__Repeat_Stmt__StatementsAssignment_1 : ( ruleStmt_List ) ;
    public final void rule__Repeat_Stmt__StatementsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8669:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8670:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8670:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8671:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8680:1: rule__Repeat_Stmt__ExpressionAssignment_3 : ( ruleExpression ) ;
    public final void rule__Repeat_Stmt__ExpressionAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8684:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8685:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8685:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8686:3: ruleExpression
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
    // InternalStructuredTextParser.g:8695:1: rule__Or_Expression__OperatorAssignment_1_1 : ( ruleOr_Operator ) ;
    public final void rule__Or_Expression__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8699:1: ( ( ruleOr_Operator ) )
            // InternalStructuredTextParser.g:8700:2: ( ruleOr_Operator )
            {
            // InternalStructuredTextParser.g:8700:2: ( ruleOr_Operator )
            // InternalStructuredTextParser.g:8701:3: ruleOr_Operator
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
    // InternalStructuredTextParser.g:8710:1: rule__Or_Expression__RightAssignment_1_2 : ( ruleXor_Expr ) ;
    public final void rule__Or_Expression__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8714:1: ( ( ruleXor_Expr ) )
            // InternalStructuredTextParser.g:8715:2: ( ruleXor_Expr )
            {
            // InternalStructuredTextParser.g:8715:2: ( ruleXor_Expr )
            // InternalStructuredTextParser.g:8716:3: ruleXor_Expr
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
    // InternalStructuredTextParser.g:8725:1: rule__Xor_Expr__OperatorAssignment_1_1 : ( ruleXor_Operator ) ;
    public final void rule__Xor_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8729:1: ( ( ruleXor_Operator ) )
            // InternalStructuredTextParser.g:8730:2: ( ruleXor_Operator )
            {
            // InternalStructuredTextParser.g:8730:2: ( ruleXor_Operator )
            // InternalStructuredTextParser.g:8731:3: ruleXor_Operator
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
    // InternalStructuredTextParser.g:8740:1: rule__Xor_Expr__RightAssignment_1_2 : ( ruleAnd_Expr ) ;
    public final void rule__Xor_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8744:1: ( ( ruleAnd_Expr ) )
            // InternalStructuredTextParser.g:8745:2: ( ruleAnd_Expr )
            {
            // InternalStructuredTextParser.g:8745:2: ( ruleAnd_Expr )
            // InternalStructuredTextParser.g:8746:3: ruleAnd_Expr
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
    // InternalStructuredTextParser.g:8755:1: rule__And_Expr__OperatorAssignment_1_1 : ( ruleAnd_Operator ) ;
    public final void rule__And_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8759:1: ( ( ruleAnd_Operator ) )
            // InternalStructuredTextParser.g:8760:2: ( ruleAnd_Operator )
            {
            // InternalStructuredTextParser.g:8760:2: ( ruleAnd_Operator )
            // InternalStructuredTextParser.g:8761:3: ruleAnd_Operator
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
    // InternalStructuredTextParser.g:8770:1: rule__And_Expr__RightAssignment_1_2 : ( ruleCompare_Expr ) ;
    public final void rule__And_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8774:1: ( ( ruleCompare_Expr ) )
            // InternalStructuredTextParser.g:8775:2: ( ruleCompare_Expr )
            {
            // InternalStructuredTextParser.g:8775:2: ( ruleCompare_Expr )
            // InternalStructuredTextParser.g:8776:3: ruleCompare_Expr
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
    // InternalStructuredTextParser.g:8785:1: rule__Compare_Expr__OperatorAssignment_1_1 : ( ruleCompare_Operator ) ;
    public final void rule__Compare_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8789:1: ( ( ruleCompare_Operator ) )
            // InternalStructuredTextParser.g:8790:2: ( ruleCompare_Operator )
            {
            // InternalStructuredTextParser.g:8790:2: ( ruleCompare_Operator )
            // InternalStructuredTextParser.g:8791:3: ruleCompare_Operator
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
    // InternalStructuredTextParser.g:8800:1: rule__Compare_Expr__RightAssignment_1_2 : ( ruleEqu_Expr ) ;
    public final void rule__Compare_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8804:1: ( ( ruleEqu_Expr ) )
            // InternalStructuredTextParser.g:8805:2: ( ruleEqu_Expr )
            {
            // InternalStructuredTextParser.g:8805:2: ( ruleEqu_Expr )
            // InternalStructuredTextParser.g:8806:3: ruleEqu_Expr
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
    // InternalStructuredTextParser.g:8815:1: rule__Equ_Expr__OperatorAssignment_1_1 : ( ruleEqu_Operator ) ;
    public final void rule__Equ_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8819:1: ( ( ruleEqu_Operator ) )
            // InternalStructuredTextParser.g:8820:2: ( ruleEqu_Operator )
            {
            // InternalStructuredTextParser.g:8820:2: ( ruleEqu_Operator )
            // InternalStructuredTextParser.g:8821:3: ruleEqu_Operator
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
    // InternalStructuredTextParser.g:8830:1: rule__Equ_Expr__RightAssignment_1_2 : ( ruleAdd_Expr ) ;
    public final void rule__Equ_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8834:1: ( ( ruleAdd_Expr ) )
            // InternalStructuredTextParser.g:8835:2: ( ruleAdd_Expr )
            {
            // InternalStructuredTextParser.g:8835:2: ( ruleAdd_Expr )
            // InternalStructuredTextParser.g:8836:3: ruleAdd_Expr
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
    // InternalStructuredTextParser.g:8845:1: rule__Add_Expr__OperatorAssignment_1_1 : ( ruleAdd_Operator ) ;
    public final void rule__Add_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8849:1: ( ( ruleAdd_Operator ) )
            // InternalStructuredTextParser.g:8850:2: ( ruleAdd_Operator )
            {
            // InternalStructuredTextParser.g:8850:2: ( ruleAdd_Operator )
            // InternalStructuredTextParser.g:8851:3: ruleAdd_Operator
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
    // InternalStructuredTextParser.g:8860:1: rule__Add_Expr__RightAssignment_1_2 : ( ruleTerm ) ;
    public final void rule__Add_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8864:1: ( ( ruleTerm ) )
            // InternalStructuredTextParser.g:8865:2: ( ruleTerm )
            {
            // InternalStructuredTextParser.g:8865:2: ( ruleTerm )
            // InternalStructuredTextParser.g:8866:3: ruleTerm
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
    // InternalStructuredTextParser.g:8875:1: rule__Term__OperatorAssignment_1_1 : ( ruleTerm_Operator ) ;
    public final void rule__Term__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8879:1: ( ( ruleTerm_Operator ) )
            // InternalStructuredTextParser.g:8880:2: ( ruleTerm_Operator )
            {
            // InternalStructuredTextParser.g:8880:2: ( ruleTerm_Operator )
            // InternalStructuredTextParser.g:8881:3: ruleTerm_Operator
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
    // InternalStructuredTextParser.g:8890:1: rule__Term__RightAssignment_1_2 : ( rulePower_Expr ) ;
    public final void rule__Term__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8894:1: ( ( rulePower_Expr ) )
            // InternalStructuredTextParser.g:8895:2: ( rulePower_Expr )
            {
            // InternalStructuredTextParser.g:8895:2: ( rulePower_Expr )
            // InternalStructuredTextParser.g:8896:3: rulePower_Expr
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
    // InternalStructuredTextParser.g:8905:1: rule__Power_Expr__OperatorAssignment_1_1 : ( rulePower_Operator ) ;
    public final void rule__Power_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8909:1: ( ( rulePower_Operator ) )
            // InternalStructuredTextParser.g:8910:2: ( rulePower_Operator )
            {
            // InternalStructuredTextParser.g:8910:2: ( rulePower_Operator )
            // InternalStructuredTextParser.g:8911:3: rulePower_Operator
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
    // InternalStructuredTextParser.g:8920:1: rule__Power_Expr__RightAssignment_1_2 : ( ruleUnary_Expr ) ;
    public final void rule__Power_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8924:1: ( ( ruleUnary_Expr ) )
            // InternalStructuredTextParser.g:8925:2: ( ruleUnary_Expr )
            {
            // InternalStructuredTextParser.g:8925:2: ( ruleUnary_Expr )
            // InternalStructuredTextParser.g:8926:3: ruleUnary_Expr
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
    // InternalStructuredTextParser.g:8935:1: rule__Unary_Expr__OperatorAssignment_0_1 : ( ruleUnary_Operator ) ;
    public final void rule__Unary_Expr__OperatorAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8939:1: ( ( ruleUnary_Operator ) )
            // InternalStructuredTextParser.g:8940:2: ( ruleUnary_Operator )
            {
            // InternalStructuredTextParser.g:8940:2: ( ruleUnary_Operator )
            // InternalStructuredTextParser.g:8941:3: ruleUnary_Operator
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
    // InternalStructuredTextParser.g:8950:1: rule__Unary_Expr__ExpressionAssignment_0_2 : ( rulePrimary_Expr ) ;
    public final void rule__Unary_Expr__ExpressionAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8954:1: ( ( rulePrimary_Expr ) )
            // InternalStructuredTextParser.g:8955:2: ( rulePrimary_Expr )
            {
            // InternalStructuredTextParser.g:8955:2: ( rulePrimary_Expr )
            // InternalStructuredTextParser.g:8956:3: rulePrimary_Expr
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
    // InternalStructuredTextParser.g:8965:1: rule__Func_Call__FuncAssignment_0 : ( ( rule__Func_Call__FuncAlternatives_0_0 ) ) ;
    public final void rule__Func_Call__FuncAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8969:1: ( ( ( rule__Func_Call__FuncAlternatives_0_0 ) ) )
            // InternalStructuredTextParser.g:8970:2: ( ( rule__Func_Call__FuncAlternatives_0_0 ) )
            {
            // InternalStructuredTextParser.g:8970:2: ( ( rule__Func_Call__FuncAlternatives_0_0 ) )
            // InternalStructuredTextParser.g:8971:3: ( rule__Func_Call__FuncAlternatives_0_0 )
            {
             before(grammarAccess.getFunc_CallAccess().getFuncAlternatives_0_0()); 
            // InternalStructuredTextParser.g:8972:3: ( rule__Func_Call__FuncAlternatives_0_0 )
            // InternalStructuredTextParser.g:8972:4: rule__Func_Call__FuncAlternatives_0_0
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
    // InternalStructuredTextParser.g:8980:1: rule__Func_Call__ArgsAssignment_2_0 : ( ruleParam_Assign ) ;
    public final void rule__Func_Call__ArgsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8984:1: ( ( ruleParam_Assign ) )
            // InternalStructuredTextParser.g:8985:2: ( ruleParam_Assign )
            {
            // InternalStructuredTextParser.g:8985:2: ( ruleParam_Assign )
            // InternalStructuredTextParser.g:8986:3: ruleParam_Assign
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
    // InternalStructuredTextParser.g:8995:1: rule__Func_Call__ArgsAssignment_2_1_1 : ( ruleParam_Assign ) ;
    public final void rule__Func_Call__ArgsAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8999:1: ( ( ruleParam_Assign ) )
            // InternalStructuredTextParser.g:9000:2: ( ruleParam_Assign )
            {
            // InternalStructuredTextParser.g:9000:2: ( ruleParam_Assign )
            // InternalStructuredTextParser.g:9001:3: ruleParam_Assign
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
    // InternalStructuredTextParser.g:9010:1: rule__Param_Assign_In__VarAssignment_0_0 : ( RULE_ID ) ;
    public final void rule__Param_Assign_In__VarAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9014:1: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:9015:2: ( RULE_ID )
            {
            // InternalStructuredTextParser.g:9015:2: ( RULE_ID )
            // InternalStructuredTextParser.g:9016:3: RULE_ID
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
    // InternalStructuredTextParser.g:9025:1: rule__Param_Assign_In__ExprAssignment_1 : ( ruleExpression ) ;
    public final void rule__Param_Assign_In__ExprAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9029:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9030:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9030:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9031:3: ruleExpression
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
    // InternalStructuredTextParser.g:9040:1: rule__Param_Assign_Out__NotAssignment_0 : ( ( NOT ) ) ;
    public final void rule__Param_Assign_Out__NotAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9044:1: ( ( ( NOT ) ) )
            // InternalStructuredTextParser.g:9045:2: ( ( NOT ) )
            {
            // InternalStructuredTextParser.g:9045:2: ( ( NOT ) )
            // InternalStructuredTextParser.g:9046:3: ( NOT )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0()); 
            // InternalStructuredTextParser.g:9047:3: ( NOT )
            // InternalStructuredTextParser.g:9048:4: NOT
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
    // InternalStructuredTextParser.g:9059:1: rule__Param_Assign_Out__VarAssignment_1 : ( RULE_ID ) ;
    public final void rule__Param_Assign_Out__VarAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9063:1: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:9064:2: ( RULE_ID )
            {
            // InternalStructuredTextParser.g:9064:2: ( RULE_ID )
            // InternalStructuredTextParser.g:9065:3: RULE_ID
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
    // InternalStructuredTextParser.g:9074:1: rule__Param_Assign_Out__ExprAssignment_3 : ( ruleVariable ) ;
    public final void rule__Param_Assign_Out__ExprAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9078:1: ( ( ruleVariable ) )
            // InternalStructuredTextParser.g:9079:2: ( ruleVariable )
            {
            // InternalStructuredTextParser.g:9079:2: ( ruleVariable )
            // InternalStructuredTextParser.g:9080:3: ruleVariable
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
    // InternalStructuredTextParser.g:9089:1: rule__Variable__PartAssignment_1 : ( ruleMultibit_Part_Access ) ;
    public final void rule__Variable__PartAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9093:1: ( ( ruleMultibit_Part_Access ) )
            // InternalStructuredTextParser.g:9094:2: ( ruleMultibit_Part_Access )
            {
            // InternalStructuredTextParser.g:9094:2: ( ruleMultibit_Part_Access )
            // InternalStructuredTextParser.g:9095:3: ruleMultibit_Part_Access
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
    // InternalStructuredTextParser.g:9104:1: rule__Variable_Subscript__IndexAssignment_1_2 : ( ruleExpression ) ;
    public final void rule__Variable_Subscript__IndexAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9108:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9109:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9109:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9110:3: ruleExpression
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
    // InternalStructuredTextParser.g:9119:1: rule__Variable_Subscript__IndexAssignment_1_3_1 : ( ruleExpression ) ;
    public final void rule__Variable_Subscript__IndexAssignment_1_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9123:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9124:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9124:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9125:3: ruleExpression
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


    // $ANTLR start "rule__Variable_Adapter__AdapterAssignment_1"
    // InternalStructuredTextParser.g:9134:1: rule__Variable_Adapter__AdapterAssignment_1 : ( ( ruleAdapter_Name ) ) ;
    public final void rule__Variable_Adapter__AdapterAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9138:1: ( ( ( ruleAdapter_Name ) ) )
            // InternalStructuredTextParser.g:9139:2: ( ( ruleAdapter_Name ) )
            {
            // InternalStructuredTextParser.g:9139:2: ( ( ruleAdapter_Name ) )
            // InternalStructuredTextParser.g:9140:3: ( ruleAdapter_Name )
            {
             before(grammarAccess.getVariable_AdapterAccess().getAdapterAdapterDeclarationCrossReference_1_0()); 
            // InternalStructuredTextParser.g:9141:3: ( ruleAdapter_Name )
            // InternalStructuredTextParser.g:9142:4: ruleAdapter_Name
            {
             before(grammarAccess.getVariable_AdapterAccess().getAdapterAdapterDeclarationAdapter_NameParserRuleCall_1_0_1()); 
            pushFollow(FOLLOW_2);
            ruleAdapter_Name();

            state._fsp--;

             after(grammarAccess.getVariable_AdapterAccess().getAdapterAdapterDeclarationAdapter_NameParserRuleCall_1_0_1()); 

            }

             after(grammarAccess.getVariable_AdapterAccess().getAdapterAdapterDeclarationCrossReference_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__AdapterAssignment_1"


    // $ANTLR start "rule__Variable_Adapter__VarAssignment_3"
    // InternalStructuredTextParser.g:9153:1: rule__Variable_Adapter__VarAssignment_3 : ( ( ruleVariable_Name ) ) ;
    public final void rule__Variable_Adapter__VarAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9157:1: ( ( ( ruleVariable_Name ) ) )
            // InternalStructuredTextParser.g:9158:2: ( ( ruleVariable_Name ) )
            {
            // InternalStructuredTextParser.g:9158:2: ( ( ruleVariable_Name ) )
            // InternalStructuredTextParser.g:9159:3: ( ruleVariable_Name )
            {
             before(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationCrossReference_3_0()); 
            // InternalStructuredTextParser.g:9160:3: ( ruleVariable_Name )
            // InternalStructuredTextParser.g:9161:4: ruleVariable_Name
            {
             before(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationVariable_NameParserRuleCall_3_0_1()); 
            pushFollow(FOLLOW_2);
            ruleVariable_Name();

            state._fsp--;

             after(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationVariable_NameParserRuleCall_3_0_1()); 

            }

             after(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationCrossReference_3_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Variable_Adapter__VarAssignment_3"


    // $ANTLR start "rule__Multibit_Part_Access__DwordaccessAssignment_0_0"
    // InternalStructuredTextParser.g:9172:1: rule__Multibit_Part_Access__DwordaccessAssignment_0_0 : ( ( D ) ) ;
    public final void rule__Multibit_Part_Access__DwordaccessAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9176:1: ( ( ( D ) ) )
            // InternalStructuredTextParser.g:9177:2: ( ( D ) )
            {
            // InternalStructuredTextParser.g:9177:2: ( ( D ) )
            // InternalStructuredTextParser.g:9178:3: ( D )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0()); 
            // InternalStructuredTextParser.g:9179:3: ( D )
            // InternalStructuredTextParser.g:9180:4: D
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
    // InternalStructuredTextParser.g:9191:1: rule__Multibit_Part_Access__IndexAssignment_0_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9195:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9196:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9196:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9197:3: rulePartial_Value
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
    // InternalStructuredTextParser.g:9206:1: rule__Multibit_Part_Access__WordaccessAssignment_1_0 : ( ( W ) ) ;
    public final void rule__Multibit_Part_Access__WordaccessAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9210:1: ( ( ( W ) ) )
            // InternalStructuredTextParser.g:9211:2: ( ( W ) )
            {
            // InternalStructuredTextParser.g:9211:2: ( ( W ) )
            // InternalStructuredTextParser.g:9212:3: ( W )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0()); 
            // InternalStructuredTextParser.g:9213:3: ( W )
            // InternalStructuredTextParser.g:9214:4: W
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
    // InternalStructuredTextParser.g:9225:1: rule__Multibit_Part_Access__IndexAssignment_1_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9229:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9230:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9230:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9231:3: rulePartial_Value
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
    // InternalStructuredTextParser.g:9240:1: rule__Multibit_Part_Access__ByteaccessAssignment_2_0 : ( ( B ) ) ;
    public final void rule__Multibit_Part_Access__ByteaccessAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9244:1: ( ( ( B ) ) )
            // InternalStructuredTextParser.g:9245:2: ( ( B ) )
            {
            // InternalStructuredTextParser.g:9245:2: ( ( B ) )
            // InternalStructuredTextParser.g:9246:3: ( B )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0()); 
            // InternalStructuredTextParser.g:9247:3: ( B )
            // InternalStructuredTextParser.g:9248:4: B
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
    // InternalStructuredTextParser.g:9259:1: rule__Multibit_Part_Access__IndexAssignment_2_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9263:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9264:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9264:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9265:3: rulePartial_Value
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
    // InternalStructuredTextParser.g:9274:1: rule__Multibit_Part_Access__BitaccessAssignment_3_0 : ( ( X ) ) ;
    public final void rule__Multibit_Part_Access__BitaccessAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9278:1: ( ( ( X ) ) )
            // InternalStructuredTextParser.g:9279:2: ( ( X ) )
            {
            // InternalStructuredTextParser.g:9279:2: ( ( X ) )
            // InternalStructuredTextParser.g:9280:3: ( X )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0()); 
            // InternalStructuredTextParser.g:9281:3: ( X )
            // InternalStructuredTextParser.g:9282:4: X
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
    // InternalStructuredTextParser.g:9293:1: rule__Multibit_Part_Access__IndexAssignment_3_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9297:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9298:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9298:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9299:3: rulePartial_Value
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
    // InternalStructuredTextParser.g:9308:1: rule__Multibit_Part_Access__BitaccessAssignment_4_0 : ( ( FullStop ) ) ;
    public final void rule__Multibit_Part_Access__BitaccessAssignment_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9312:1: ( ( ( FullStop ) ) )
            // InternalStructuredTextParser.g:9313:2: ( ( FullStop ) )
            {
            // InternalStructuredTextParser.g:9313:2: ( ( FullStop ) )
            // InternalStructuredTextParser.g:9314:3: ( FullStop )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0()); 
            // InternalStructuredTextParser.g:9315:3: ( FullStop )
            // InternalStructuredTextParser.g:9316:4: FullStop
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
    // InternalStructuredTextParser.g:9327:1: rule__Multibit_Part_Access__IndexAssignment_4_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9331:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9332:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9332:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9333:3: rulePartial_Value
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
    // InternalStructuredTextParser.g:9342:1: rule__Variable_Primary__VarAssignment : ( ( ruleVariable_Name ) ) ;
    public final void rule__Variable_Primary__VarAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9346:1: ( ( ( ruleVariable_Name ) ) )
            // InternalStructuredTextParser.g:9347:2: ( ( ruleVariable_Name ) )
            {
            // InternalStructuredTextParser.g:9347:2: ( ( ruleVariable_Name ) )
            // InternalStructuredTextParser.g:9348:3: ( ruleVariable_Name )
            {
             before(grammarAccess.getVariable_PrimaryAccess().getVarVarDeclarationCrossReference_0()); 
            // InternalStructuredTextParser.g:9349:3: ( ruleVariable_Name )
            // InternalStructuredTextParser.g:9350:4: ruleVariable_Name
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
    // InternalStructuredTextParser.g:9361:1: rule__Int_Literal__TypeAssignment_0_0 : ( ruleInt_Type_Name ) ;
    public final void rule__Int_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9365:1: ( ( ruleInt_Type_Name ) )
            // InternalStructuredTextParser.g:9366:2: ( ruleInt_Type_Name )
            {
            // InternalStructuredTextParser.g:9366:2: ( ruleInt_Type_Name )
            // InternalStructuredTextParser.g:9367:3: ruleInt_Type_Name
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
    // InternalStructuredTextParser.g:9376:1: rule__Int_Literal__ValueAssignment_1 : ( ( rule__Int_Literal__ValueAlternatives_1_0 ) ) ;
    public final void rule__Int_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9380:1: ( ( ( rule__Int_Literal__ValueAlternatives_1_0 ) ) )
            // InternalStructuredTextParser.g:9381:2: ( ( rule__Int_Literal__ValueAlternatives_1_0 ) )
            {
            // InternalStructuredTextParser.g:9381:2: ( ( rule__Int_Literal__ValueAlternatives_1_0 ) )
            // InternalStructuredTextParser.g:9382:3: ( rule__Int_Literal__ValueAlternatives_1_0 )
            {
             before(grammarAccess.getInt_LiteralAccess().getValueAlternatives_1_0()); 
            // InternalStructuredTextParser.g:9383:3: ( rule__Int_Literal__ValueAlternatives_1_0 )
            // InternalStructuredTextParser.g:9383:4: rule__Int_Literal__ValueAlternatives_1_0
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
    // InternalStructuredTextParser.g:9391:1: rule__Real_Literal__TypeAssignment_0_0 : ( ruleReal_Type_Name ) ;
    public final void rule__Real_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9395:1: ( ( ruleReal_Type_Name ) )
            // InternalStructuredTextParser.g:9396:2: ( ruleReal_Type_Name )
            {
            // InternalStructuredTextParser.g:9396:2: ( ruleReal_Type_Name )
            // InternalStructuredTextParser.g:9397:3: ruleReal_Type_Name
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
    // InternalStructuredTextParser.g:9406:1: rule__Real_Literal__ValueAssignment_1 : ( ruleReal_Value ) ;
    public final void rule__Real_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9410:1: ( ( ruleReal_Value ) )
            // InternalStructuredTextParser.g:9411:2: ( ruleReal_Value )
            {
            // InternalStructuredTextParser.g:9411:2: ( ruleReal_Value )
            // InternalStructuredTextParser.g:9412:3: ruleReal_Value
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
    // InternalStructuredTextParser.g:9421:1: rule__Bool_Literal__TypeAssignment_0_0 : ( ruleBool_Type_Name ) ;
    public final void rule__Bool_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9425:1: ( ( ruleBool_Type_Name ) )
            // InternalStructuredTextParser.g:9426:2: ( ruleBool_Type_Name )
            {
            // InternalStructuredTextParser.g:9426:2: ( ruleBool_Type_Name )
            // InternalStructuredTextParser.g:9427:3: ruleBool_Type_Name
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
    // InternalStructuredTextParser.g:9436:1: rule__Bool_Literal__ValueAssignment_1 : ( ruleBool_Value ) ;
    public final void rule__Bool_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9440:1: ( ( ruleBool_Value ) )
            // InternalStructuredTextParser.g:9441:2: ( ruleBool_Value )
            {
            // InternalStructuredTextParser.g:9441:2: ( ruleBool_Value )
            // InternalStructuredTextParser.g:9442:3: ruleBool_Value
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
    // InternalStructuredTextParser.g:9451:1: rule__Char_Literal__TypeAssignment_0_0 : ( ruleString_Type_Name ) ;
    public final void rule__Char_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9455:1: ( ( ruleString_Type_Name ) )
            // InternalStructuredTextParser.g:9456:2: ( ruleString_Type_Name )
            {
            // InternalStructuredTextParser.g:9456:2: ( ruleString_Type_Name )
            // InternalStructuredTextParser.g:9457:3: ruleString_Type_Name
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
    // InternalStructuredTextParser.g:9466:1: rule__Char_Literal__LengthAssignment_0_1 : ( RULE_UNSIGNED_INT ) ;
    public final void rule__Char_Literal__LengthAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9470:1: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:9471:2: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:9471:2: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:9472:3: RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:9481:1: rule__Char_Literal__ValueAssignment_1 : ( ( rule__Char_Literal__ValueAlternatives_1_0 ) ) ;
    public final void rule__Char_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9485:1: ( ( ( rule__Char_Literal__ValueAlternatives_1_0 ) ) )
            // InternalStructuredTextParser.g:9486:2: ( ( rule__Char_Literal__ValueAlternatives_1_0 ) )
            {
            // InternalStructuredTextParser.g:9486:2: ( ( rule__Char_Literal__ValueAlternatives_1_0 ) )
            // InternalStructuredTextParser.g:9487:3: ( rule__Char_Literal__ValueAlternatives_1_0 )
            {
             before(grammarAccess.getChar_LiteralAccess().getValueAlternatives_1_0()); 
            // InternalStructuredTextParser.g:9488:3: ( rule__Char_Literal__ValueAlternatives_1_0 )
            // InternalStructuredTextParser.g:9488:4: rule__Char_Literal__ValueAlternatives_1_0
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
    // InternalStructuredTextParser.g:9496:1: rule__Time_Literal__LiteralAssignment : ( ( rule__Time_Literal__LiteralAlternatives_0 ) ) ;
    public final void rule__Time_Literal__LiteralAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9500:1: ( ( ( rule__Time_Literal__LiteralAlternatives_0 ) ) )
            // InternalStructuredTextParser.g:9501:2: ( ( rule__Time_Literal__LiteralAlternatives_0 ) )
            {
            // InternalStructuredTextParser.g:9501:2: ( ( rule__Time_Literal__LiteralAlternatives_0 ) )
            // InternalStructuredTextParser.g:9502:3: ( rule__Time_Literal__LiteralAlternatives_0 )
            {
             before(grammarAccess.getTime_LiteralAccess().getLiteralAlternatives_0()); 
            // InternalStructuredTextParser.g:9503:3: ( rule__Time_Literal__LiteralAlternatives_0 )
            // InternalStructuredTextParser.g:9503:4: rule__Time_Literal__LiteralAlternatives_0
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
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0404490160000000L,0x0001C10000A00000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000000000L,0x0000010000A00000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0404690162000000L,0x0001C10000A00000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000004200088000L,0x0014000000000000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000010000010000L});

}