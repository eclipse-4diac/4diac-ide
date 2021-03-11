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


    // $ANTLR start "entryRuleFB_Call"
    // InternalStructuredTextParser.g:325:1: entryRuleFB_Call : ruleFB_Call EOF ;
    public final void entryRuleFB_Call() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:326:1: ( ruleFB_Call EOF )
            // InternalStructuredTextParser.g:327:1: ruleFB_Call EOF
            {
             before(grammarAccess.getFB_CallRule()); 
            pushFollow(FOLLOW_1);
            ruleFB_Call();

            state._fsp--;

             after(grammarAccess.getFB_CallRule()); 
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
    // $ANTLR end "entryRuleFB_Call"


    // $ANTLR start "ruleFB_Call"
    // InternalStructuredTextParser.g:334:1: ruleFB_Call : ( ( rule__FB_Call__Group__0 ) ) ;
    public final void ruleFB_Call() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:338:2: ( ( ( rule__FB_Call__Group__0 ) ) )
            // InternalStructuredTextParser.g:339:2: ( ( rule__FB_Call__Group__0 ) )
            {
            // InternalStructuredTextParser.g:339:2: ( ( rule__FB_Call__Group__0 ) )
            // InternalStructuredTextParser.g:340:3: ( rule__FB_Call__Group__0 )
            {
             before(grammarAccess.getFB_CallAccess().getGroup()); 
            // InternalStructuredTextParser.g:341:3: ( rule__FB_Call__Group__0 )
            // InternalStructuredTextParser.g:341:4: rule__FB_Call__Group__0
            {
            pushFollow(FOLLOW_2);
            rule__FB_Call__Group__0();

            state._fsp--;


            }

             after(grammarAccess.getFB_CallAccess().getGroup()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "ruleFB_Call"


    // $ANTLR start "entryRuleSelection_Stmt"
    // InternalStructuredTextParser.g:350:1: entryRuleSelection_Stmt : ruleSelection_Stmt EOF ;
    public final void entryRuleSelection_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:351:1: ( ruleSelection_Stmt EOF )
            // InternalStructuredTextParser.g:352:1: ruleSelection_Stmt EOF
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
    // InternalStructuredTextParser.g:359:1: ruleSelection_Stmt : ( ( rule__Selection_Stmt__Alternatives ) ) ;
    public final void ruleSelection_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:363:2: ( ( ( rule__Selection_Stmt__Alternatives ) ) )
            // InternalStructuredTextParser.g:364:2: ( ( rule__Selection_Stmt__Alternatives ) )
            {
            // InternalStructuredTextParser.g:364:2: ( ( rule__Selection_Stmt__Alternatives ) )
            // InternalStructuredTextParser.g:365:3: ( rule__Selection_Stmt__Alternatives )
            {
             before(grammarAccess.getSelection_StmtAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:366:3: ( rule__Selection_Stmt__Alternatives )
            // InternalStructuredTextParser.g:366:4: rule__Selection_Stmt__Alternatives
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
    // InternalStructuredTextParser.g:375:1: entryRuleIF_Stmt : ruleIF_Stmt EOF ;
    public final void entryRuleIF_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:376:1: ( ruleIF_Stmt EOF )
            // InternalStructuredTextParser.g:377:1: ruleIF_Stmt EOF
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
    // InternalStructuredTextParser.g:384:1: ruleIF_Stmt : ( ( rule__IF_Stmt__Group__0 ) ) ;
    public final void ruleIF_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:388:2: ( ( ( rule__IF_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:389:2: ( ( rule__IF_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:389:2: ( ( rule__IF_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:390:3: ( rule__IF_Stmt__Group__0 )
            {
             before(grammarAccess.getIF_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:391:3: ( rule__IF_Stmt__Group__0 )
            // InternalStructuredTextParser.g:391:4: rule__IF_Stmt__Group__0
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
    // InternalStructuredTextParser.g:400:1: entryRuleELSIF_Clause : ruleELSIF_Clause EOF ;
    public final void entryRuleELSIF_Clause() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:401:1: ( ruleELSIF_Clause EOF )
            // InternalStructuredTextParser.g:402:1: ruleELSIF_Clause EOF
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
    // InternalStructuredTextParser.g:409:1: ruleELSIF_Clause : ( ( rule__ELSIF_Clause__Group__0 ) ) ;
    public final void ruleELSIF_Clause() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:413:2: ( ( ( rule__ELSIF_Clause__Group__0 ) ) )
            // InternalStructuredTextParser.g:414:2: ( ( rule__ELSIF_Clause__Group__0 ) )
            {
            // InternalStructuredTextParser.g:414:2: ( ( rule__ELSIF_Clause__Group__0 ) )
            // InternalStructuredTextParser.g:415:3: ( rule__ELSIF_Clause__Group__0 )
            {
             before(grammarAccess.getELSIF_ClauseAccess().getGroup()); 
            // InternalStructuredTextParser.g:416:3: ( rule__ELSIF_Clause__Group__0 )
            // InternalStructuredTextParser.g:416:4: rule__ELSIF_Clause__Group__0
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
    // InternalStructuredTextParser.g:425:1: entryRuleELSE_Clause : ruleELSE_Clause EOF ;
    public final void entryRuleELSE_Clause() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:426:1: ( ruleELSE_Clause EOF )
            // InternalStructuredTextParser.g:427:1: ruleELSE_Clause EOF
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
    // InternalStructuredTextParser.g:434:1: ruleELSE_Clause : ( ( rule__ELSE_Clause__Group__0 ) ) ;
    public final void ruleELSE_Clause() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:438:2: ( ( ( rule__ELSE_Clause__Group__0 ) ) )
            // InternalStructuredTextParser.g:439:2: ( ( rule__ELSE_Clause__Group__0 ) )
            {
            // InternalStructuredTextParser.g:439:2: ( ( rule__ELSE_Clause__Group__0 ) )
            // InternalStructuredTextParser.g:440:3: ( rule__ELSE_Clause__Group__0 )
            {
             before(grammarAccess.getELSE_ClauseAccess().getGroup()); 
            // InternalStructuredTextParser.g:441:3: ( rule__ELSE_Clause__Group__0 )
            // InternalStructuredTextParser.g:441:4: rule__ELSE_Clause__Group__0
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
    // InternalStructuredTextParser.g:450:1: entryRuleCase_Stmt : ruleCase_Stmt EOF ;
    public final void entryRuleCase_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:451:1: ( ruleCase_Stmt EOF )
            // InternalStructuredTextParser.g:452:1: ruleCase_Stmt EOF
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
    // InternalStructuredTextParser.g:459:1: ruleCase_Stmt : ( ( rule__Case_Stmt__Group__0 ) ) ;
    public final void ruleCase_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:463:2: ( ( ( rule__Case_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:464:2: ( ( rule__Case_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:464:2: ( ( rule__Case_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:465:3: ( rule__Case_Stmt__Group__0 )
            {
             before(grammarAccess.getCase_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:466:3: ( rule__Case_Stmt__Group__0 )
            // InternalStructuredTextParser.g:466:4: rule__Case_Stmt__Group__0
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
    // InternalStructuredTextParser.g:475:1: entryRuleCase_Selection : ruleCase_Selection EOF ;
    public final void entryRuleCase_Selection() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:476:1: ( ruleCase_Selection EOF )
            // InternalStructuredTextParser.g:477:1: ruleCase_Selection EOF
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
    // InternalStructuredTextParser.g:484:1: ruleCase_Selection : ( ( rule__Case_Selection__Group__0 ) ) ;
    public final void ruleCase_Selection() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:488:2: ( ( ( rule__Case_Selection__Group__0 ) ) )
            // InternalStructuredTextParser.g:489:2: ( ( rule__Case_Selection__Group__0 ) )
            {
            // InternalStructuredTextParser.g:489:2: ( ( rule__Case_Selection__Group__0 ) )
            // InternalStructuredTextParser.g:490:3: ( rule__Case_Selection__Group__0 )
            {
             before(grammarAccess.getCase_SelectionAccess().getGroup()); 
            // InternalStructuredTextParser.g:491:3: ( rule__Case_Selection__Group__0 )
            // InternalStructuredTextParser.g:491:4: rule__Case_Selection__Group__0
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
    // InternalStructuredTextParser.g:500:1: entryRuleIteration_Stmt : ruleIteration_Stmt EOF ;
    public final void entryRuleIteration_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:501:1: ( ruleIteration_Stmt EOF )
            // InternalStructuredTextParser.g:502:1: ruleIteration_Stmt EOF
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
    // InternalStructuredTextParser.g:509:1: ruleIteration_Stmt : ( ( rule__Iteration_Stmt__Alternatives ) ) ;
    public final void ruleIteration_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:513:2: ( ( ( rule__Iteration_Stmt__Alternatives ) ) )
            // InternalStructuredTextParser.g:514:2: ( ( rule__Iteration_Stmt__Alternatives ) )
            {
            // InternalStructuredTextParser.g:514:2: ( ( rule__Iteration_Stmt__Alternatives ) )
            // InternalStructuredTextParser.g:515:3: ( rule__Iteration_Stmt__Alternatives )
            {
             before(grammarAccess.getIteration_StmtAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:516:3: ( rule__Iteration_Stmt__Alternatives )
            // InternalStructuredTextParser.g:516:4: rule__Iteration_Stmt__Alternatives
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
    // InternalStructuredTextParser.g:525:1: entryRuleFor_Stmt : ruleFor_Stmt EOF ;
    public final void entryRuleFor_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:526:1: ( ruleFor_Stmt EOF )
            // InternalStructuredTextParser.g:527:1: ruleFor_Stmt EOF
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
    // InternalStructuredTextParser.g:534:1: ruleFor_Stmt : ( ( rule__For_Stmt__Group__0 ) ) ;
    public final void ruleFor_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:538:2: ( ( ( rule__For_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:539:2: ( ( rule__For_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:539:2: ( ( rule__For_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:540:3: ( rule__For_Stmt__Group__0 )
            {
             before(grammarAccess.getFor_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:541:3: ( rule__For_Stmt__Group__0 )
            // InternalStructuredTextParser.g:541:4: rule__For_Stmt__Group__0
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
    // InternalStructuredTextParser.g:550:1: entryRuleWhile_Stmt : ruleWhile_Stmt EOF ;
    public final void entryRuleWhile_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:551:1: ( ruleWhile_Stmt EOF )
            // InternalStructuredTextParser.g:552:1: ruleWhile_Stmt EOF
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
    // InternalStructuredTextParser.g:559:1: ruleWhile_Stmt : ( ( rule__While_Stmt__Group__0 ) ) ;
    public final void ruleWhile_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:563:2: ( ( ( rule__While_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:564:2: ( ( rule__While_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:564:2: ( ( rule__While_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:565:3: ( rule__While_Stmt__Group__0 )
            {
             before(grammarAccess.getWhile_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:566:3: ( rule__While_Stmt__Group__0 )
            // InternalStructuredTextParser.g:566:4: rule__While_Stmt__Group__0
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
    // InternalStructuredTextParser.g:575:1: entryRuleRepeat_Stmt : ruleRepeat_Stmt EOF ;
    public final void entryRuleRepeat_Stmt() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:576:1: ( ruleRepeat_Stmt EOF )
            // InternalStructuredTextParser.g:577:1: ruleRepeat_Stmt EOF
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
    // InternalStructuredTextParser.g:584:1: ruleRepeat_Stmt : ( ( rule__Repeat_Stmt__Group__0 ) ) ;
    public final void ruleRepeat_Stmt() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:588:2: ( ( ( rule__Repeat_Stmt__Group__0 ) ) )
            // InternalStructuredTextParser.g:589:2: ( ( rule__Repeat_Stmt__Group__0 ) )
            {
            // InternalStructuredTextParser.g:589:2: ( ( rule__Repeat_Stmt__Group__0 ) )
            // InternalStructuredTextParser.g:590:3: ( rule__Repeat_Stmt__Group__0 )
            {
             before(grammarAccess.getRepeat_StmtAccess().getGroup()); 
            // InternalStructuredTextParser.g:591:3: ( rule__Repeat_Stmt__Group__0 )
            // InternalStructuredTextParser.g:591:4: rule__Repeat_Stmt__Group__0
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
    // InternalStructuredTextParser.g:600:1: entryRuleExpression : ruleExpression EOF ;
    public final void entryRuleExpression() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:601:1: ( ruleExpression EOF )
            // InternalStructuredTextParser.g:602:1: ruleExpression EOF
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
    // InternalStructuredTextParser.g:609:1: ruleExpression : ( ruleOr_Expression ) ;
    public final void ruleExpression() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:613:2: ( ( ruleOr_Expression ) )
            // InternalStructuredTextParser.g:614:2: ( ruleOr_Expression )
            {
            // InternalStructuredTextParser.g:614:2: ( ruleOr_Expression )
            // InternalStructuredTextParser.g:615:3: ruleOr_Expression
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
    // InternalStructuredTextParser.g:625:1: entryRuleOr_Expression : ruleOr_Expression EOF ;
    public final void entryRuleOr_Expression() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:626:1: ( ruleOr_Expression EOF )
            // InternalStructuredTextParser.g:627:1: ruleOr_Expression EOF
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
    // InternalStructuredTextParser.g:634:1: ruleOr_Expression : ( ( rule__Or_Expression__Group__0 ) ) ;
    public final void ruleOr_Expression() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:638:2: ( ( ( rule__Or_Expression__Group__0 ) ) )
            // InternalStructuredTextParser.g:639:2: ( ( rule__Or_Expression__Group__0 ) )
            {
            // InternalStructuredTextParser.g:639:2: ( ( rule__Or_Expression__Group__0 ) )
            // InternalStructuredTextParser.g:640:3: ( rule__Or_Expression__Group__0 )
            {
             before(grammarAccess.getOr_ExpressionAccess().getGroup()); 
            // InternalStructuredTextParser.g:641:3: ( rule__Or_Expression__Group__0 )
            // InternalStructuredTextParser.g:641:4: rule__Or_Expression__Group__0
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
    // InternalStructuredTextParser.g:650:1: entryRuleXor_Expr : ruleXor_Expr EOF ;
    public final void entryRuleXor_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:651:1: ( ruleXor_Expr EOF )
            // InternalStructuredTextParser.g:652:1: ruleXor_Expr EOF
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
    // InternalStructuredTextParser.g:659:1: ruleXor_Expr : ( ( rule__Xor_Expr__Group__0 ) ) ;
    public final void ruleXor_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:663:2: ( ( ( rule__Xor_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:664:2: ( ( rule__Xor_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:664:2: ( ( rule__Xor_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:665:3: ( rule__Xor_Expr__Group__0 )
            {
             before(grammarAccess.getXor_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:666:3: ( rule__Xor_Expr__Group__0 )
            // InternalStructuredTextParser.g:666:4: rule__Xor_Expr__Group__0
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
    // InternalStructuredTextParser.g:675:1: entryRuleAnd_Expr : ruleAnd_Expr EOF ;
    public final void entryRuleAnd_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:676:1: ( ruleAnd_Expr EOF )
            // InternalStructuredTextParser.g:677:1: ruleAnd_Expr EOF
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
    // InternalStructuredTextParser.g:684:1: ruleAnd_Expr : ( ( rule__And_Expr__Group__0 ) ) ;
    public final void ruleAnd_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:688:2: ( ( ( rule__And_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:689:2: ( ( rule__And_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:689:2: ( ( rule__And_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:690:3: ( rule__And_Expr__Group__0 )
            {
             before(grammarAccess.getAnd_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:691:3: ( rule__And_Expr__Group__0 )
            // InternalStructuredTextParser.g:691:4: rule__And_Expr__Group__0
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
    // InternalStructuredTextParser.g:700:1: entryRuleCompare_Expr : ruleCompare_Expr EOF ;
    public final void entryRuleCompare_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:701:1: ( ruleCompare_Expr EOF )
            // InternalStructuredTextParser.g:702:1: ruleCompare_Expr EOF
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
    // InternalStructuredTextParser.g:709:1: ruleCompare_Expr : ( ( rule__Compare_Expr__Group__0 ) ) ;
    public final void ruleCompare_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:713:2: ( ( ( rule__Compare_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:714:2: ( ( rule__Compare_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:714:2: ( ( rule__Compare_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:715:3: ( rule__Compare_Expr__Group__0 )
            {
             before(grammarAccess.getCompare_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:716:3: ( rule__Compare_Expr__Group__0 )
            // InternalStructuredTextParser.g:716:4: rule__Compare_Expr__Group__0
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
    // InternalStructuredTextParser.g:725:1: entryRuleEqu_Expr : ruleEqu_Expr EOF ;
    public final void entryRuleEqu_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:726:1: ( ruleEqu_Expr EOF )
            // InternalStructuredTextParser.g:727:1: ruleEqu_Expr EOF
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
    // InternalStructuredTextParser.g:734:1: ruleEqu_Expr : ( ( rule__Equ_Expr__Group__0 ) ) ;
    public final void ruleEqu_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:738:2: ( ( ( rule__Equ_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:739:2: ( ( rule__Equ_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:739:2: ( ( rule__Equ_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:740:3: ( rule__Equ_Expr__Group__0 )
            {
             before(grammarAccess.getEqu_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:741:3: ( rule__Equ_Expr__Group__0 )
            // InternalStructuredTextParser.g:741:4: rule__Equ_Expr__Group__0
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
    // InternalStructuredTextParser.g:750:1: entryRuleAdd_Expr : ruleAdd_Expr EOF ;
    public final void entryRuleAdd_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:751:1: ( ruleAdd_Expr EOF )
            // InternalStructuredTextParser.g:752:1: ruleAdd_Expr EOF
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
    // InternalStructuredTextParser.g:759:1: ruleAdd_Expr : ( ( rule__Add_Expr__Group__0 ) ) ;
    public final void ruleAdd_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:763:2: ( ( ( rule__Add_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:764:2: ( ( rule__Add_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:764:2: ( ( rule__Add_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:765:3: ( rule__Add_Expr__Group__0 )
            {
             before(grammarAccess.getAdd_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:766:3: ( rule__Add_Expr__Group__0 )
            // InternalStructuredTextParser.g:766:4: rule__Add_Expr__Group__0
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
    // InternalStructuredTextParser.g:775:1: entryRuleTerm : ruleTerm EOF ;
    public final void entryRuleTerm() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:776:1: ( ruleTerm EOF )
            // InternalStructuredTextParser.g:777:1: ruleTerm EOF
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
    // InternalStructuredTextParser.g:784:1: ruleTerm : ( ( rule__Term__Group__0 ) ) ;
    public final void ruleTerm() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:788:2: ( ( ( rule__Term__Group__0 ) ) )
            // InternalStructuredTextParser.g:789:2: ( ( rule__Term__Group__0 ) )
            {
            // InternalStructuredTextParser.g:789:2: ( ( rule__Term__Group__0 ) )
            // InternalStructuredTextParser.g:790:3: ( rule__Term__Group__0 )
            {
             before(grammarAccess.getTermAccess().getGroup()); 
            // InternalStructuredTextParser.g:791:3: ( rule__Term__Group__0 )
            // InternalStructuredTextParser.g:791:4: rule__Term__Group__0
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
    // InternalStructuredTextParser.g:800:1: entryRulePower_Expr : rulePower_Expr EOF ;
    public final void entryRulePower_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:801:1: ( rulePower_Expr EOF )
            // InternalStructuredTextParser.g:802:1: rulePower_Expr EOF
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
    // InternalStructuredTextParser.g:809:1: rulePower_Expr : ( ( rule__Power_Expr__Group__0 ) ) ;
    public final void rulePower_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:813:2: ( ( ( rule__Power_Expr__Group__0 ) ) )
            // InternalStructuredTextParser.g:814:2: ( ( rule__Power_Expr__Group__0 ) )
            {
            // InternalStructuredTextParser.g:814:2: ( ( rule__Power_Expr__Group__0 ) )
            // InternalStructuredTextParser.g:815:3: ( rule__Power_Expr__Group__0 )
            {
             before(grammarAccess.getPower_ExprAccess().getGroup()); 
            // InternalStructuredTextParser.g:816:3: ( rule__Power_Expr__Group__0 )
            // InternalStructuredTextParser.g:816:4: rule__Power_Expr__Group__0
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
    // InternalStructuredTextParser.g:825:1: entryRuleUnary_Expr : ruleUnary_Expr EOF ;
    public final void entryRuleUnary_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:826:1: ( ruleUnary_Expr EOF )
            // InternalStructuredTextParser.g:827:1: ruleUnary_Expr EOF
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
    // InternalStructuredTextParser.g:834:1: ruleUnary_Expr : ( ( rule__Unary_Expr__Alternatives ) ) ;
    public final void ruleUnary_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:838:2: ( ( ( rule__Unary_Expr__Alternatives ) ) )
            // InternalStructuredTextParser.g:839:2: ( ( rule__Unary_Expr__Alternatives ) )
            {
            // InternalStructuredTextParser.g:839:2: ( ( rule__Unary_Expr__Alternatives ) )
            // InternalStructuredTextParser.g:840:3: ( rule__Unary_Expr__Alternatives )
            {
             before(grammarAccess.getUnary_ExprAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:841:3: ( rule__Unary_Expr__Alternatives )
            // InternalStructuredTextParser.g:841:4: rule__Unary_Expr__Alternatives
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
    // InternalStructuredTextParser.g:850:1: entryRulePrimary_Expr : rulePrimary_Expr EOF ;
    public final void entryRulePrimary_Expr() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:851:1: ( rulePrimary_Expr EOF )
            // InternalStructuredTextParser.g:852:1: rulePrimary_Expr EOF
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
    // InternalStructuredTextParser.g:859:1: rulePrimary_Expr : ( ( rule__Primary_Expr__Alternatives ) ) ;
    public final void rulePrimary_Expr() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:863:2: ( ( ( rule__Primary_Expr__Alternatives ) ) )
            // InternalStructuredTextParser.g:864:2: ( ( rule__Primary_Expr__Alternatives ) )
            {
            // InternalStructuredTextParser.g:864:2: ( ( rule__Primary_Expr__Alternatives ) )
            // InternalStructuredTextParser.g:865:3: ( rule__Primary_Expr__Alternatives )
            {
             before(grammarAccess.getPrimary_ExprAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:866:3: ( rule__Primary_Expr__Alternatives )
            // InternalStructuredTextParser.g:866:4: rule__Primary_Expr__Alternatives
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
    // InternalStructuredTextParser.g:875:1: entryRuleFunc_Call : ruleFunc_Call EOF ;
    public final void entryRuleFunc_Call() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:876:1: ( ruleFunc_Call EOF )
            // InternalStructuredTextParser.g:877:1: ruleFunc_Call EOF
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
    // InternalStructuredTextParser.g:884:1: ruleFunc_Call : ( ( rule__Func_Call__Group__0 ) ) ;
    public final void ruleFunc_Call() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:888:2: ( ( ( rule__Func_Call__Group__0 ) ) )
            // InternalStructuredTextParser.g:889:2: ( ( rule__Func_Call__Group__0 ) )
            {
            // InternalStructuredTextParser.g:889:2: ( ( rule__Func_Call__Group__0 ) )
            // InternalStructuredTextParser.g:890:3: ( rule__Func_Call__Group__0 )
            {
             before(grammarAccess.getFunc_CallAccess().getGroup()); 
            // InternalStructuredTextParser.g:891:3: ( rule__Func_Call__Group__0 )
            // InternalStructuredTextParser.g:891:4: rule__Func_Call__Group__0
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
    // InternalStructuredTextParser.g:900:1: entryRuleParam_Assign : ruleParam_Assign EOF ;
    public final void entryRuleParam_Assign() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:901:1: ( ruleParam_Assign EOF )
            // InternalStructuredTextParser.g:902:1: ruleParam_Assign EOF
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
    // InternalStructuredTextParser.g:909:1: ruleParam_Assign : ( ( rule__Param_Assign__Alternatives ) ) ;
    public final void ruleParam_Assign() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:913:2: ( ( ( rule__Param_Assign__Alternatives ) ) )
            // InternalStructuredTextParser.g:914:2: ( ( rule__Param_Assign__Alternatives ) )
            {
            // InternalStructuredTextParser.g:914:2: ( ( rule__Param_Assign__Alternatives ) )
            // InternalStructuredTextParser.g:915:3: ( rule__Param_Assign__Alternatives )
            {
             before(grammarAccess.getParam_AssignAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:916:3: ( rule__Param_Assign__Alternatives )
            // InternalStructuredTextParser.g:916:4: rule__Param_Assign__Alternatives
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
    // InternalStructuredTextParser.g:925:1: entryRuleParam_Assign_In : ruleParam_Assign_In EOF ;
    public final void entryRuleParam_Assign_In() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:926:1: ( ruleParam_Assign_In EOF )
            // InternalStructuredTextParser.g:927:1: ruleParam_Assign_In EOF
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
    // InternalStructuredTextParser.g:934:1: ruleParam_Assign_In : ( ( rule__Param_Assign_In__Group__0 ) ) ;
    public final void ruleParam_Assign_In() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:938:2: ( ( ( rule__Param_Assign_In__Group__0 ) ) )
            // InternalStructuredTextParser.g:939:2: ( ( rule__Param_Assign_In__Group__0 ) )
            {
            // InternalStructuredTextParser.g:939:2: ( ( rule__Param_Assign_In__Group__0 ) )
            // InternalStructuredTextParser.g:940:3: ( rule__Param_Assign_In__Group__0 )
            {
             before(grammarAccess.getParam_Assign_InAccess().getGroup()); 
            // InternalStructuredTextParser.g:941:3: ( rule__Param_Assign_In__Group__0 )
            // InternalStructuredTextParser.g:941:4: rule__Param_Assign_In__Group__0
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
    // InternalStructuredTextParser.g:950:1: entryRuleParam_Assign_Out : ruleParam_Assign_Out EOF ;
    public final void entryRuleParam_Assign_Out() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:951:1: ( ruleParam_Assign_Out EOF )
            // InternalStructuredTextParser.g:952:1: ruleParam_Assign_Out EOF
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
    // InternalStructuredTextParser.g:959:1: ruleParam_Assign_Out : ( ( rule__Param_Assign_Out__Group__0 ) ) ;
    public final void ruleParam_Assign_Out() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:963:2: ( ( ( rule__Param_Assign_Out__Group__0 ) ) )
            // InternalStructuredTextParser.g:964:2: ( ( rule__Param_Assign_Out__Group__0 ) )
            {
            // InternalStructuredTextParser.g:964:2: ( ( rule__Param_Assign_Out__Group__0 ) )
            // InternalStructuredTextParser.g:965:3: ( rule__Param_Assign_Out__Group__0 )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getGroup()); 
            // InternalStructuredTextParser.g:966:3: ( rule__Param_Assign_Out__Group__0 )
            // InternalStructuredTextParser.g:966:4: rule__Param_Assign_Out__Group__0
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
    // InternalStructuredTextParser.g:975:1: entryRuleVariable : ruleVariable EOF ;
    public final void entryRuleVariable() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:976:1: ( ruleVariable EOF )
            // InternalStructuredTextParser.g:977:1: ruleVariable EOF
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
    // InternalStructuredTextParser.g:984:1: ruleVariable : ( ( rule__Variable__Group__0 ) ) ;
    public final void ruleVariable() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:988:2: ( ( ( rule__Variable__Group__0 ) ) )
            // InternalStructuredTextParser.g:989:2: ( ( rule__Variable__Group__0 ) )
            {
            // InternalStructuredTextParser.g:989:2: ( ( rule__Variable__Group__0 ) )
            // InternalStructuredTextParser.g:990:3: ( rule__Variable__Group__0 )
            {
             before(grammarAccess.getVariableAccess().getGroup()); 
            // InternalStructuredTextParser.g:991:3: ( rule__Variable__Group__0 )
            // InternalStructuredTextParser.g:991:4: rule__Variable__Group__0
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
    // InternalStructuredTextParser.g:1000:1: entryRuleVariable_Subscript : ruleVariable_Subscript EOF ;
    public final void entryRuleVariable_Subscript() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1001:1: ( ruleVariable_Subscript EOF )
            // InternalStructuredTextParser.g:1002:1: ruleVariable_Subscript EOF
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
    // InternalStructuredTextParser.g:1009:1: ruleVariable_Subscript : ( ( rule__Variable_Subscript__Group__0 ) ) ;
    public final void ruleVariable_Subscript() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1013:2: ( ( ( rule__Variable_Subscript__Group__0 ) ) )
            // InternalStructuredTextParser.g:1014:2: ( ( rule__Variable_Subscript__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1014:2: ( ( rule__Variable_Subscript__Group__0 ) )
            // InternalStructuredTextParser.g:1015:3: ( rule__Variable_Subscript__Group__0 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getGroup()); 
            // InternalStructuredTextParser.g:1016:3: ( rule__Variable_Subscript__Group__0 )
            // InternalStructuredTextParser.g:1016:4: rule__Variable_Subscript__Group__0
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
    // InternalStructuredTextParser.g:1025:1: entryRuleVariable_Adapter : ruleVariable_Adapter EOF ;
    public final void entryRuleVariable_Adapter() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1026:1: ( ruleVariable_Adapter EOF )
            // InternalStructuredTextParser.g:1027:1: ruleVariable_Adapter EOF
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
    // InternalStructuredTextParser.g:1034:1: ruleVariable_Adapter : ( ( rule__Variable_Adapter__Group__0 ) ) ;
    public final void ruleVariable_Adapter() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1038:2: ( ( ( rule__Variable_Adapter__Group__0 ) ) )
            // InternalStructuredTextParser.g:1039:2: ( ( rule__Variable_Adapter__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1039:2: ( ( rule__Variable_Adapter__Group__0 ) )
            // InternalStructuredTextParser.g:1040:3: ( rule__Variable_Adapter__Group__0 )
            {
             before(grammarAccess.getVariable_AdapterAccess().getGroup()); 
            // InternalStructuredTextParser.g:1041:3: ( rule__Variable_Adapter__Group__0 )
            // InternalStructuredTextParser.g:1041:4: rule__Variable_Adapter__Group__0
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
    // InternalStructuredTextParser.g:1050:1: entryRuleAdapterRoot : ruleAdapterRoot EOF ;
    public final void entryRuleAdapterRoot() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1051:1: ( ruleAdapterRoot EOF )
            // InternalStructuredTextParser.g:1052:1: ruleAdapterRoot EOF
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
    // InternalStructuredTextParser.g:1059:1: ruleAdapterRoot : ( ( rule__AdapterRoot__Group__0 ) ) ;
    public final void ruleAdapterRoot() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1063:2: ( ( ( rule__AdapterRoot__Group__0 ) ) )
            // InternalStructuredTextParser.g:1064:2: ( ( rule__AdapterRoot__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1064:2: ( ( rule__AdapterRoot__Group__0 ) )
            // InternalStructuredTextParser.g:1065:3: ( rule__AdapterRoot__Group__0 )
            {
             before(grammarAccess.getAdapterRootAccess().getGroup()); 
            // InternalStructuredTextParser.g:1066:3: ( rule__AdapterRoot__Group__0 )
            // InternalStructuredTextParser.g:1066:4: rule__AdapterRoot__Group__0
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
    // InternalStructuredTextParser.g:1075:1: entryRuleMultibit_Part_Access : ruleMultibit_Part_Access EOF ;
    public final void entryRuleMultibit_Part_Access() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1076:1: ( ruleMultibit_Part_Access EOF )
            // InternalStructuredTextParser.g:1077:1: ruleMultibit_Part_Access EOF
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
    // InternalStructuredTextParser.g:1084:1: ruleMultibit_Part_Access : ( ( rule__Multibit_Part_Access__Alternatives ) ) ;
    public final void ruleMultibit_Part_Access() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1088:2: ( ( ( rule__Multibit_Part_Access__Alternatives ) ) )
            // InternalStructuredTextParser.g:1089:2: ( ( rule__Multibit_Part_Access__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1089:2: ( ( rule__Multibit_Part_Access__Alternatives ) )
            // InternalStructuredTextParser.g:1090:3: ( rule__Multibit_Part_Access__Alternatives )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1091:3: ( rule__Multibit_Part_Access__Alternatives )
            // InternalStructuredTextParser.g:1091:4: rule__Multibit_Part_Access__Alternatives
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
    // InternalStructuredTextParser.g:1100:1: entryRuleAdapter_Name : ruleAdapter_Name EOF ;
    public final void entryRuleAdapter_Name() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1101:1: ( ruleAdapter_Name EOF )
            // InternalStructuredTextParser.g:1102:1: ruleAdapter_Name EOF
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
    // InternalStructuredTextParser.g:1109:1: ruleAdapter_Name : ( ( rule__Adapter_Name__Alternatives ) ) ;
    public final void ruleAdapter_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1113:2: ( ( ( rule__Adapter_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1114:2: ( ( rule__Adapter_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1114:2: ( ( rule__Adapter_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1115:3: ( rule__Adapter_Name__Alternatives )
            {
             before(grammarAccess.getAdapter_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1116:3: ( rule__Adapter_Name__Alternatives )
            // InternalStructuredTextParser.g:1116:4: rule__Adapter_Name__Alternatives
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
    // InternalStructuredTextParser.g:1125:1: entryRuleVariable_Primary : ruleVariable_Primary EOF ;
    public final void entryRuleVariable_Primary() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1126:1: ( ruleVariable_Primary EOF )
            // InternalStructuredTextParser.g:1127:1: ruleVariable_Primary EOF
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
    // InternalStructuredTextParser.g:1134:1: ruleVariable_Primary : ( ( rule__Variable_Primary__VarAssignment ) ) ;
    public final void ruleVariable_Primary() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1138:2: ( ( ( rule__Variable_Primary__VarAssignment ) ) )
            // InternalStructuredTextParser.g:1139:2: ( ( rule__Variable_Primary__VarAssignment ) )
            {
            // InternalStructuredTextParser.g:1139:2: ( ( rule__Variable_Primary__VarAssignment ) )
            // InternalStructuredTextParser.g:1140:3: ( rule__Variable_Primary__VarAssignment )
            {
             before(grammarAccess.getVariable_PrimaryAccess().getVarAssignment()); 
            // InternalStructuredTextParser.g:1141:3: ( rule__Variable_Primary__VarAssignment )
            // InternalStructuredTextParser.g:1141:4: rule__Variable_Primary__VarAssignment
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
    // InternalStructuredTextParser.g:1150:1: entryRuleVariable_Name : ruleVariable_Name EOF ;
    public final void entryRuleVariable_Name() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1151:1: ( ruleVariable_Name EOF )
            // InternalStructuredTextParser.g:1152:1: ruleVariable_Name EOF
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
    // InternalStructuredTextParser.g:1159:1: ruleVariable_Name : ( ( rule__Variable_Name__Alternatives ) ) ;
    public final void ruleVariable_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1163:2: ( ( ( rule__Variable_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1164:2: ( ( rule__Variable_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1164:2: ( ( rule__Variable_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1165:3: ( rule__Variable_Name__Alternatives )
            {
             before(grammarAccess.getVariable_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1166:3: ( rule__Variable_Name__Alternatives )
            // InternalStructuredTextParser.g:1166:4: rule__Variable_Name__Alternatives
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
    // InternalStructuredTextParser.g:1175:1: entryRuleConstant : ruleConstant EOF ;
    public final void entryRuleConstant() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1176:1: ( ruleConstant EOF )
            // InternalStructuredTextParser.g:1177:1: ruleConstant EOF
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
    // InternalStructuredTextParser.g:1184:1: ruleConstant : ( ( rule__Constant__Alternatives ) ) ;
    public final void ruleConstant() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1188:2: ( ( ( rule__Constant__Alternatives ) ) )
            // InternalStructuredTextParser.g:1189:2: ( ( rule__Constant__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1189:2: ( ( rule__Constant__Alternatives ) )
            // InternalStructuredTextParser.g:1190:3: ( rule__Constant__Alternatives )
            {
             before(grammarAccess.getConstantAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1191:3: ( rule__Constant__Alternatives )
            // InternalStructuredTextParser.g:1191:4: rule__Constant__Alternatives
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
    // InternalStructuredTextParser.g:1200:1: entryRuleNumeric_Literal : ruleNumeric_Literal EOF ;
    public final void entryRuleNumeric_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1201:1: ( ruleNumeric_Literal EOF )
            // InternalStructuredTextParser.g:1202:1: ruleNumeric_Literal EOF
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
    // InternalStructuredTextParser.g:1209:1: ruleNumeric_Literal : ( ( rule__Numeric_Literal__Alternatives ) ) ;
    public final void ruleNumeric_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1213:2: ( ( ( rule__Numeric_Literal__Alternatives ) ) )
            // InternalStructuredTextParser.g:1214:2: ( ( rule__Numeric_Literal__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1214:2: ( ( rule__Numeric_Literal__Alternatives ) )
            // InternalStructuredTextParser.g:1215:3: ( rule__Numeric_Literal__Alternatives )
            {
             before(grammarAccess.getNumeric_LiteralAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1216:3: ( rule__Numeric_Literal__Alternatives )
            // InternalStructuredTextParser.g:1216:4: rule__Numeric_Literal__Alternatives
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
    // InternalStructuredTextParser.g:1225:1: entryRuleInt_Literal : ruleInt_Literal EOF ;
    public final void entryRuleInt_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1226:1: ( ruleInt_Literal EOF )
            // InternalStructuredTextParser.g:1227:1: ruleInt_Literal EOF
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
    // InternalStructuredTextParser.g:1234:1: ruleInt_Literal : ( ( rule__Int_Literal__Group__0 ) ) ;
    public final void ruleInt_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1238:2: ( ( ( rule__Int_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1239:2: ( ( rule__Int_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1239:2: ( ( rule__Int_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1240:3: ( rule__Int_Literal__Group__0 )
            {
             before(grammarAccess.getInt_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1241:3: ( rule__Int_Literal__Group__0 )
            // InternalStructuredTextParser.g:1241:4: rule__Int_Literal__Group__0
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
    // InternalStructuredTextParser.g:1250:1: entryRuleSigned_Int : ruleSigned_Int EOF ;
    public final void entryRuleSigned_Int() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1251:1: ( ruleSigned_Int EOF )
            // InternalStructuredTextParser.g:1252:1: ruleSigned_Int EOF
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
    // InternalStructuredTextParser.g:1259:1: ruleSigned_Int : ( ( rule__Signed_Int__Group__0 ) ) ;
    public final void ruleSigned_Int() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1263:2: ( ( ( rule__Signed_Int__Group__0 ) ) )
            // InternalStructuredTextParser.g:1264:2: ( ( rule__Signed_Int__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1264:2: ( ( rule__Signed_Int__Group__0 ) )
            // InternalStructuredTextParser.g:1265:3: ( rule__Signed_Int__Group__0 )
            {
             before(grammarAccess.getSigned_IntAccess().getGroup()); 
            // InternalStructuredTextParser.g:1266:3: ( rule__Signed_Int__Group__0 )
            // InternalStructuredTextParser.g:1266:4: rule__Signed_Int__Group__0
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
    // InternalStructuredTextParser.g:1275:1: entryRulePartial_Value : rulePartial_Value EOF ;
    public final void entryRulePartial_Value() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1276:1: ( rulePartial_Value EOF )
            // InternalStructuredTextParser.g:1277:1: rulePartial_Value EOF
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
    // InternalStructuredTextParser.g:1284:1: rulePartial_Value : ( RULE_UNSIGNED_INT ) ;
    public final void rulePartial_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1288:2: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:1289:2: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:1289:2: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:1290:3: RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:1300:1: entryRuleArray_Size : ruleArray_Size EOF ;
    public final void entryRuleArray_Size() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1301:1: ( ruleArray_Size EOF )
            // InternalStructuredTextParser.g:1302:1: ruleArray_Size EOF
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
    // InternalStructuredTextParser.g:1309:1: ruleArray_Size : ( RULE_UNSIGNED_INT ) ;
    public final void ruleArray_Size() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1313:2: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:1314:2: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:1314:2: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:1315:3: RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:1325:1: entryRuleReal_Literal : ruleReal_Literal EOF ;
    public final void entryRuleReal_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1326:1: ( ruleReal_Literal EOF )
            // InternalStructuredTextParser.g:1327:1: ruleReal_Literal EOF
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
    // InternalStructuredTextParser.g:1334:1: ruleReal_Literal : ( ( rule__Real_Literal__Group__0 ) ) ;
    public final void ruleReal_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1338:2: ( ( ( rule__Real_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1339:2: ( ( rule__Real_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1339:2: ( ( rule__Real_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1340:3: ( rule__Real_Literal__Group__0 )
            {
             before(grammarAccess.getReal_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1341:3: ( rule__Real_Literal__Group__0 )
            // InternalStructuredTextParser.g:1341:4: rule__Real_Literal__Group__0
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
    // InternalStructuredTextParser.g:1350:1: entryRuleReal_Value : ruleReal_Value EOF ;
    public final void entryRuleReal_Value() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1351:1: ( ruleReal_Value EOF )
            // InternalStructuredTextParser.g:1352:1: ruleReal_Value EOF
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
    // InternalStructuredTextParser.g:1359:1: ruleReal_Value : ( ( rule__Real_Value__Group__0 ) ) ;
    public final void ruleReal_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1363:2: ( ( ( rule__Real_Value__Group__0 ) ) )
            // InternalStructuredTextParser.g:1364:2: ( ( rule__Real_Value__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1364:2: ( ( rule__Real_Value__Group__0 ) )
            // InternalStructuredTextParser.g:1365:3: ( rule__Real_Value__Group__0 )
            {
             before(grammarAccess.getReal_ValueAccess().getGroup()); 
            // InternalStructuredTextParser.g:1366:3: ( rule__Real_Value__Group__0 )
            // InternalStructuredTextParser.g:1366:4: rule__Real_Value__Group__0
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
    // InternalStructuredTextParser.g:1375:1: entryRuleBool_Literal : ruleBool_Literal EOF ;
    public final void entryRuleBool_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1376:1: ( ruleBool_Literal EOF )
            // InternalStructuredTextParser.g:1377:1: ruleBool_Literal EOF
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
    // InternalStructuredTextParser.g:1384:1: ruleBool_Literal : ( ( rule__Bool_Literal__Group__0 ) ) ;
    public final void ruleBool_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1388:2: ( ( ( rule__Bool_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1389:2: ( ( rule__Bool_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1389:2: ( ( rule__Bool_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1390:3: ( rule__Bool_Literal__Group__0 )
            {
             before(grammarAccess.getBool_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1391:3: ( rule__Bool_Literal__Group__0 )
            // InternalStructuredTextParser.g:1391:4: rule__Bool_Literal__Group__0
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
    // InternalStructuredTextParser.g:1400:1: entryRuleBool_Value : ruleBool_Value EOF ;
    public final void entryRuleBool_Value() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1401:1: ( ruleBool_Value EOF )
            // InternalStructuredTextParser.g:1402:1: ruleBool_Value EOF
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
    // InternalStructuredTextParser.g:1409:1: ruleBool_Value : ( ( rule__Bool_Value__Alternatives ) ) ;
    public final void ruleBool_Value() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1413:2: ( ( ( rule__Bool_Value__Alternatives ) ) )
            // InternalStructuredTextParser.g:1414:2: ( ( rule__Bool_Value__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1414:2: ( ( rule__Bool_Value__Alternatives ) )
            // InternalStructuredTextParser.g:1415:3: ( rule__Bool_Value__Alternatives )
            {
             before(grammarAccess.getBool_ValueAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1416:3: ( rule__Bool_Value__Alternatives )
            // InternalStructuredTextParser.g:1416:4: rule__Bool_Value__Alternatives
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
    // InternalStructuredTextParser.g:1425:1: entryRuleChar_Literal : ruleChar_Literal EOF ;
    public final void entryRuleChar_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1426:1: ( ruleChar_Literal EOF )
            // InternalStructuredTextParser.g:1427:1: ruleChar_Literal EOF
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
    // InternalStructuredTextParser.g:1434:1: ruleChar_Literal : ( ( rule__Char_Literal__Group__0 ) ) ;
    public final void ruleChar_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1438:2: ( ( ( rule__Char_Literal__Group__0 ) ) )
            // InternalStructuredTextParser.g:1439:2: ( ( rule__Char_Literal__Group__0 ) )
            {
            // InternalStructuredTextParser.g:1439:2: ( ( rule__Char_Literal__Group__0 ) )
            // InternalStructuredTextParser.g:1440:3: ( rule__Char_Literal__Group__0 )
            {
             before(grammarAccess.getChar_LiteralAccess().getGroup()); 
            // InternalStructuredTextParser.g:1441:3: ( rule__Char_Literal__Group__0 )
            // InternalStructuredTextParser.g:1441:4: rule__Char_Literal__Group__0
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
    // InternalStructuredTextParser.g:1450:1: entryRuleTime_Literal : ruleTime_Literal EOF ;
    public final void entryRuleTime_Literal() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1451:1: ( ruleTime_Literal EOF )
            // InternalStructuredTextParser.g:1452:1: ruleTime_Literal EOF
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
    // InternalStructuredTextParser.g:1459:1: ruleTime_Literal : ( ( rule__Time_Literal__LiteralAssignment ) ) ;
    public final void ruleTime_Literal() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1463:2: ( ( ( rule__Time_Literal__LiteralAssignment ) ) )
            // InternalStructuredTextParser.g:1464:2: ( ( rule__Time_Literal__LiteralAssignment ) )
            {
            // InternalStructuredTextParser.g:1464:2: ( ( rule__Time_Literal__LiteralAssignment ) )
            // InternalStructuredTextParser.g:1465:3: ( rule__Time_Literal__LiteralAssignment )
            {
             before(grammarAccess.getTime_LiteralAccess().getLiteralAssignment()); 
            // InternalStructuredTextParser.g:1466:3: ( rule__Time_Literal__LiteralAssignment )
            // InternalStructuredTextParser.g:1466:4: rule__Time_Literal__LiteralAssignment
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
    // InternalStructuredTextParser.g:1475:1: entryRuleType_Name : ruleType_Name EOF ;
    public final void entryRuleType_Name() throws RecognitionException {
        try {
            // InternalStructuredTextParser.g:1476:1: ( ruleType_Name EOF )
            // InternalStructuredTextParser.g:1477:1: ruleType_Name EOF
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
    // InternalStructuredTextParser.g:1484:1: ruleType_Name : ( ( rule__Type_Name__Alternatives ) ) ;
    public final void ruleType_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1488:2: ( ( ( rule__Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1489:2: ( ( rule__Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1489:2: ( ( rule__Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1490:3: ( rule__Type_Name__Alternatives )
            {
             before(grammarAccess.getType_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1491:3: ( rule__Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1491:4: rule__Type_Name__Alternatives
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
    // InternalStructuredTextParser.g:1500:1: ruleOr_Operator : ( ( OR ) ) ;
    public final void ruleOr_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1504:1: ( ( ( OR ) ) )
            // InternalStructuredTextParser.g:1505:2: ( ( OR ) )
            {
            // InternalStructuredTextParser.g:1505:2: ( ( OR ) )
            // InternalStructuredTextParser.g:1506:3: ( OR )
            {
             before(grammarAccess.getOr_OperatorAccess().getOREnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1507:3: ( OR )
            // InternalStructuredTextParser.g:1507:4: OR
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
    // InternalStructuredTextParser.g:1516:1: ruleXor_Operator : ( ( XOR ) ) ;
    public final void ruleXor_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1520:1: ( ( ( XOR ) ) )
            // InternalStructuredTextParser.g:1521:2: ( ( XOR ) )
            {
            // InternalStructuredTextParser.g:1521:2: ( ( XOR ) )
            // InternalStructuredTextParser.g:1522:3: ( XOR )
            {
             before(grammarAccess.getXor_OperatorAccess().getXOREnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1523:3: ( XOR )
            // InternalStructuredTextParser.g:1523:4: XOR
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
    // InternalStructuredTextParser.g:1532:1: ruleAnd_Operator : ( ( rule__And_Operator__Alternatives ) ) ;
    public final void ruleAnd_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1536:1: ( ( ( rule__And_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1537:2: ( ( rule__And_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1537:2: ( ( rule__And_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1538:3: ( rule__And_Operator__Alternatives )
            {
             before(grammarAccess.getAnd_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1539:3: ( rule__And_Operator__Alternatives )
            // InternalStructuredTextParser.g:1539:4: rule__And_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1548:1: ruleCompare_Operator : ( ( rule__Compare_Operator__Alternatives ) ) ;
    public final void ruleCompare_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1552:1: ( ( ( rule__Compare_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1553:2: ( ( rule__Compare_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1553:2: ( ( rule__Compare_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1554:3: ( rule__Compare_Operator__Alternatives )
            {
             before(grammarAccess.getCompare_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1555:3: ( rule__Compare_Operator__Alternatives )
            // InternalStructuredTextParser.g:1555:4: rule__Compare_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1564:1: ruleEqu_Operator : ( ( rule__Equ_Operator__Alternatives ) ) ;
    public final void ruleEqu_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1568:1: ( ( ( rule__Equ_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1569:2: ( ( rule__Equ_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1569:2: ( ( rule__Equ_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1570:3: ( rule__Equ_Operator__Alternatives )
            {
             before(grammarAccess.getEqu_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1571:3: ( rule__Equ_Operator__Alternatives )
            // InternalStructuredTextParser.g:1571:4: rule__Equ_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1580:1: ruleAdd_Operator : ( ( rule__Add_Operator__Alternatives ) ) ;
    public final void ruleAdd_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1584:1: ( ( ( rule__Add_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1585:2: ( ( rule__Add_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1585:2: ( ( rule__Add_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1586:3: ( rule__Add_Operator__Alternatives )
            {
             before(grammarAccess.getAdd_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1587:3: ( rule__Add_Operator__Alternatives )
            // InternalStructuredTextParser.g:1587:4: rule__Add_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1596:1: ruleTerm_Operator : ( ( rule__Term_Operator__Alternatives ) ) ;
    public final void ruleTerm_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1600:1: ( ( ( rule__Term_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1601:2: ( ( rule__Term_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1601:2: ( ( rule__Term_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1602:3: ( rule__Term_Operator__Alternatives )
            {
             before(grammarAccess.getTerm_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1603:3: ( rule__Term_Operator__Alternatives )
            // InternalStructuredTextParser.g:1603:4: rule__Term_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1612:1: rulePower_Operator : ( ( AsteriskAsterisk ) ) ;
    public final void rulePower_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1616:1: ( ( ( AsteriskAsterisk ) ) )
            // InternalStructuredTextParser.g:1617:2: ( ( AsteriskAsterisk ) )
            {
            // InternalStructuredTextParser.g:1617:2: ( ( AsteriskAsterisk ) )
            // InternalStructuredTextParser.g:1618:3: ( AsteriskAsterisk )
            {
             before(grammarAccess.getPower_OperatorAccess().getPOWEREnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1619:3: ( AsteriskAsterisk )
            // InternalStructuredTextParser.g:1619:4: AsteriskAsterisk
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
    // InternalStructuredTextParser.g:1628:1: ruleUnary_Operator : ( ( rule__Unary_Operator__Alternatives ) ) ;
    public final void ruleUnary_Operator() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1632:1: ( ( ( rule__Unary_Operator__Alternatives ) ) )
            // InternalStructuredTextParser.g:1633:2: ( ( rule__Unary_Operator__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1633:2: ( ( rule__Unary_Operator__Alternatives ) )
            // InternalStructuredTextParser.g:1634:3: ( rule__Unary_Operator__Alternatives )
            {
             before(grammarAccess.getUnary_OperatorAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1635:3: ( rule__Unary_Operator__Alternatives )
            // InternalStructuredTextParser.g:1635:4: rule__Unary_Operator__Alternatives
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
    // InternalStructuredTextParser.g:1644:1: ruleInt_Type_Name : ( ( rule__Int_Type_Name__Alternatives ) ) ;
    public final void ruleInt_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1648:1: ( ( ( rule__Int_Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1649:2: ( ( rule__Int_Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1649:2: ( ( rule__Int_Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1650:3: ( rule__Int_Type_Name__Alternatives )
            {
             before(grammarAccess.getInt_Type_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1651:3: ( rule__Int_Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1651:4: rule__Int_Type_Name__Alternatives
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
    // InternalStructuredTextParser.g:1660:1: ruleReal_Type_Name : ( ( rule__Real_Type_Name__Alternatives ) ) ;
    public final void ruleReal_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1664:1: ( ( ( rule__Real_Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1665:2: ( ( rule__Real_Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1665:2: ( ( rule__Real_Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1666:3: ( rule__Real_Type_Name__Alternatives )
            {
             before(grammarAccess.getReal_Type_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1667:3: ( rule__Real_Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1667:4: rule__Real_Type_Name__Alternatives
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
    // InternalStructuredTextParser.g:1676:1: ruleString_Type_Name : ( ( rule__String_Type_Name__Alternatives ) ) ;
    public final void ruleString_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1680:1: ( ( ( rule__String_Type_Name__Alternatives ) ) )
            // InternalStructuredTextParser.g:1681:2: ( ( rule__String_Type_Name__Alternatives ) )
            {
            // InternalStructuredTextParser.g:1681:2: ( ( rule__String_Type_Name__Alternatives ) )
            // InternalStructuredTextParser.g:1682:3: ( rule__String_Type_Name__Alternatives )
            {
             before(grammarAccess.getString_Type_NameAccess().getAlternatives()); 
            // InternalStructuredTextParser.g:1683:3: ( rule__String_Type_Name__Alternatives )
            // InternalStructuredTextParser.g:1683:4: rule__String_Type_Name__Alternatives
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
    // InternalStructuredTextParser.g:1692:1: ruleBool_Type_Name : ( ( BOOL ) ) ;
    public final void ruleBool_Type_Name() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1696:1: ( ( ( BOOL ) ) )
            // InternalStructuredTextParser.g:1697:2: ( ( BOOL ) )
            {
            // InternalStructuredTextParser.g:1697:2: ( ( BOOL ) )
            // InternalStructuredTextParser.g:1698:3: ( BOOL )
            {
             before(grammarAccess.getBool_Type_NameAccess().getBOOLEnumLiteralDeclaration()); 
            // InternalStructuredTextParser.g:1699:3: ( BOOL )
            // InternalStructuredTextParser.g:1699:4: BOOL
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
    // InternalStructuredTextParser.g:1707:1: rule__Stmt__Alternatives : ( ( ruleAssign_Stmt ) | ( ruleSubprog_Ctrl_Stmt ) | ( ruleSelection_Stmt ) | ( ruleIteration_Stmt ) );
    public final void rule__Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1711:1: ( ( ruleAssign_Stmt ) | ( ruleSubprog_Ctrl_Stmt ) | ( ruleSelection_Stmt ) | ( ruleIteration_Stmt ) )
            int alt1=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                switch ( input.LA(2) ) {
                case B:
                case D:
                case W:
                case X:
                case ColonEqualsSign:
                case LeftSquareBracket:
                    {
                    alt1=1;
                    }
                    break;
                case FullStop:
                    {
                    int LA1_6 = input.LA(3);

                    if ( (LA1_6==DT||LA1_6==LT||LA1_6==T||LA1_6==RULE_UNSIGNED_INT) ) {
                        alt1=1;
                    }
                    else if ( (LA1_6==RULE_ID) ) {
                        int LA1_7 = input.LA(4);

                        if ( (LA1_7==LeftParenthesis) ) {
                            alt1=2;
                        }
                        else if ( ((LA1_7>=B && LA1_7<=X)||LA1_7==ColonEqualsSign||LA1_7==FullStop||LA1_7==LeftSquareBracket) ) {
                            alt1=1;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 1, 7, input);

                            throw nvae;
                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 1, 6, input);

                        throw nvae;
                    }
                    }
                    break;
                case LeftParenthesis:
                    {
                    alt1=2;
                    }
                    break;
                default:
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
                    // InternalStructuredTextParser.g:1712:2: ( ruleAssign_Stmt )
                    {
                    // InternalStructuredTextParser.g:1712:2: ( ruleAssign_Stmt )
                    // InternalStructuredTextParser.g:1713:3: ruleAssign_Stmt
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
                    // InternalStructuredTextParser.g:1718:2: ( ruleSubprog_Ctrl_Stmt )
                    {
                    // InternalStructuredTextParser.g:1718:2: ( ruleSubprog_Ctrl_Stmt )
                    // InternalStructuredTextParser.g:1719:3: ruleSubprog_Ctrl_Stmt
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
                    // InternalStructuredTextParser.g:1724:2: ( ruleSelection_Stmt )
                    {
                    // InternalStructuredTextParser.g:1724:2: ( ruleSelection_Stmt )
                    // InternalStructuredTextParser.g:1725:3: ruleSelection_Stmt
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
                    // InternalStructuredTextParser.g:1730:2: ( ruleIteration_Stmt )
                    {
                    // InternalStructuredTextParser.g:1730:2: ( ruleIteration_Stmt )
                    // InternalStructuredTextParser.g:1731:3: ruleIteration_Stmt
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
    // InternalStructuredTextParser.g:1740:1: rule__Subprog_Ctrl_Stmt__Alternatives : ( ( ruleFunc_Call ) | ( ruleFB_Call ) | ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) ) | ( ( rule__Subprog_Ctrl_Stmt__Group_3__0 ) ) );
    public final void rule__Subprog_Ctrl_Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1744:1: ( ( ruleFunc_Call ) | ( ruleFB_Call ) | ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) ) | ( ( rule__Subprog_Ctrl_Stmt__Group_3__0 ) ) )
            int alt2=4;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==FullStop) ) {
                    alt2=2;
                }
                else if ( (LA2_1==LeftParenthesis) ) {
                    alt2=1;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 2, 1, input);

                    throw nvae;
                }
                }
                break;
            case TIME:
                {
                alt2=1;
                }
                break;
            case SUPER:
                {
                alt2=3;
                }
                break;
            case RETURN:
                {
                alt2=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // InternalStructuredTextParser.g:1745:2: ( ruleFunc_Call )
                    {
                    // InternalStructuredTextParser.g:1745:2: ( ruleFunc_Call )
                    // InternalStructuredTextParser.g:1746:3: ruleFunc_Call
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
                    // InternalStructuredTextParser.g:1751:2: ( ruleFB_Call )
                    {
                    // InternalStructuredTextParser.g:1751:2: ( ruleFB_Call )
                    // InternalStructuredTextParser.g:1752:3: ruleFB_Call
                    {
                     before(grammarAccess.getSubprog_Ctrl_StmtAccess().getFB_CallParserRuleCall_1()); 
                    pushFollow(FOLLOW_2);
                    ruleFB_Call();

                    state._fsp--;

                     after(grammarAccess.getSubprog_Ctrl_StmtAccess().getFB_CallParserRuleCall_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:1757:2: ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) )
                    {
                    // InternalStructuredTextParser.g:1757:2: ( ( rule__Subprog_Ctrl_Stmt__Group_2__0 ) )
                    // InternalStructuredTextParser.g:1758:3: ( rule__Subprog_Ctrl_Stmt__Group_2__0 )
                    {
                     before(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_2()); 
                    // InternalStructuredTextParser.g:1759:3: ( rule__Subprog_Ctrl_Stmt__Group_2__0 )
                    // InternalStructuredTextParser.g:1759:4: rule__Subprog_Ctrl_Stmt__Group_2__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Subprog_Ctrl_Stmt__Group_2__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:1763:2: ( ( rule__Subprog_Ctrl_Stmt__Group_3__0 ) )
                    {
                    // InternalStructuredTextParser.g:1763:2: ( ( rule__Subprog_Ctrl_Stmt__Group_3__0 ) )
                    // InternalStructuredTextParser.g:1764:3: ( rule__Subprog_Ctrl_Stmt__Group_3__0 )
                    {
                     before(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_3()); 
                    // InternalStructuredTextParser.g:1765:3: ( rule__Subprog_Ctrl_Stmt__Group_3__0 )
                    // InternalStructuredTextParser.g:1765:4: rule__Subprog_Ctrl_Stmt__Group_3__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__Subprog_Ctrl_Stmt__Group_3__0();

                    state._fsp--;


                    }

                     after(grammarAccess.getSubprog_Ctrl_StmtAccess().getGroup_3()); 

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
    // InternalStructuredTextParser.g:1773:1: rule__Selection_Stmt__Alternatives : ( ( ruleIF_Stmt ) | ( ruleCase_Stmt ) );
    public final void rule__Selection_Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1777:1: ( ( ruleIF_Stmt ) | ( ruleCase_Stmt ) )
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
                    // InternalStructuredTextParser.g:1778:2: ( ruleIF_Stmt )
                    {
                    // InternalStructuredTextParser.g:1778:2: ( ruleIF_Stmt )
                    // InternalStructuredTextParser.g:1779:3: ruleIF_Stmt
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
                    // InternalStructuredTextParser.g:1784:2: ( ruleCase_Stmt )
                    {
                    // InternalStructuredTextParser.g:1784:2: ( ruleCase_Stmt )
                    // InternalStructuredTextParser.g:1785:3: ruleCase_Stmt
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
    // InternalStructuredTextParser.g:1794:1: rule__Iteration_Stmt__Alternatives : ( ( ruleFor_Stmt ) | ( ruleWhile_Stmt ) | ( ruleRepeat_Stmt ) | ( ( rule__Iteration_Stmt__Group_3__0 ) ) | ( ( rule__Iteration_Stmt__Group_4__0 ) ) );
    public final void rule__Iteration_Stmt__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1798:1: ( ( ruleFor_Stmt ) | ( ruleWhile_Stmt ) | ( ruleRepeat_Stmt ) | ( ( rule__Iteration_Stmt__Group_3__0 ) ) | ( ( rule__Iteration_Stmt__Group_4__0 ) ) )
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
                    // InternalStructuredTextParser.g:1799:2: ( ruleFor_Stmt )
                    {
                    // InternalStructuredTextParser.g:1799:2: ( ruleFor_Stmt )
                    // InternalStructuredTextParser.g:1800:3: ruleFor_Stmt
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
                    // InternalStructuredTextParser.g:1805:2: ( ruleWhile_Stmt )
                    {
                    // InternalStructuredTextParser.g:1805:2: ( ruleWhile_Stmt )
                    // InternalStructuredTextParser.g:1806:3: ruleWhile_Stmt
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
                    // InternalStructuredTextParser.g:1811:2: ( ruleRepeat_Stmt )
                    {
                    // InternalStructuredTextParser.g:1811:2: ( ruleRepeat_Stmt )
                    // InternalStructuredTextParser.g:1812:3: ruleRepeat_Stmt
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
                    // InternalStructuredTextParser.g:1817:2: ( ( rule__Iteration_Stmt__Group_3__0 ) )
                    {
                    // InternalStructuredTextParser.g:1817:2: ( ( rule__Iteration_Stmt__Group_3__0 ) )
                    // InternalStructuredTextParser.g:1818:3: ( rule__Iteration_Stmt__Group_3__0 )
                    {
                     before(grammarAccess.getIteration_StmtAccess().getGroup_3()); 
                    // InternalStructuredTextParser.g:1819:3: ( rule__Iteration_Stmt__Group_3__0 )
                    // InternalStructuredTextParser.g:1819:4: rule__Iteration_Stmt__Group_3__0
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
                    // InternalStructuredTextParser.g:1823:2: ( ( rule__Iteration_Stmt__Group_4__0 ) )
                    {
                    // InternalStructuredTextParser.g:1823:2: ( ( rule__Iteration_Stmt__Group_4__0 ) )
                    // InternalStructuredTextParser.g:1824:3: ( rule__Iteration_Stmt__Group_4__0 )
                    {
                     before(grammarAccess.getIteration_StmtAccess().getGroup_4()); 
                    // InternalStructuredTextParser.g:1825:3: ( rule__Iteration_Stmt__Group_4__0 )
                    // InternalStructuredTextParser.g:1825:4: rule__Iteration_Stmt__Group_4__0
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
    // InternalStructuredTextParser.g:1833:1: rule__Unary_Expr__Alternatives : ( ( ( rule__Unary_Expr__Group_0__0 ) ) | ( rulePrimary_Expr ) | ( ruleConstant ) );
    public final void rule__Unary_Expr__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1837:1: ( ( ( rule__Unary_Expr__Group_0__0 ) ) | ( rulePrimary_Expr ) | ( ruleConstant ) )
            int alt5=3;
            switch ( input.LA(1) ) {
            case HyphenMinus:
                {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==RULE_UNSIGNED_INT) ) {
                    alt5=3;
                }
                else if ( (LA5_1==TIME||LA5_1==DT||LA5_1==LT||LA5_1==LeftParenthesis||LA5_1==T||LA5_1==RULE_ID) ) {
                    alt5=1;
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
                    // InternalStructuredTextParser.g:1838:2: ( ( rule__Unary_Expr__Group_0__0 ) )
                    {
                    // InternalStructuredTextParser.g:1838:2: ( ( rule__Unary_Expr__Group_0__0 ) )
                    // InternalStructuredTextParser.g:1839:3: ( rule__Unary_Expr__Group_0__0 )
                    {
                     before(grammarAccess.getUnary_ExprAccess().getGroup_0()); 
                    // InternalStructuredTextParser.g:1840:3: ( rule__Unary_Expr__Group_0__0 )
                    // InternalStructuredTextParser.g:1840:4: rule__Unary_Expr__Group_0__0
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
                    // InternalStructuredTextParser.g:1844:2: ( rulePrimary_Expr )
                    {
                    // InternalStructuredTextParser.g:1844:2: ( rulePrimary_Expr )
                    // InternalStructuredTextParser.g:1845:3: rulePrimary_Expr
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
                    // InternalStructuredTextParser.g:1850:2: ( ruleConstant )
                    {
                    // InternalStructuredTextParser.g:1850:2: ( ruleConstant )
                    // InternalStructuredTextParser.g:1851:3: ruleConstant
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
    // InternalStructuredTextParser.g:1860:1: rule__Primary_Expr__Alternatives : ( ( ruleVariable ) | ( ruleFunc_Call ) | ( ( rule__Primary_Expr__Group_2__0 ) ) );
    public final void rule__Primary_Expr__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1864:1: ( ( ruleVariable ) | ( ruleFunc_Call ) | ( ( rule__Primary_Expr__Group_2__0 ) ) )
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
                    // InternalStructuredTextParser.g:1865:2: ( ruleVariable )
                    {
                    // InternalStructuredTextParser.g:1865:2: ( ruleVariable )
                    // InternalStructuredTextParser.g:1866:3: ruleVariable
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
                    // InternalStructuredTextParser.g:1871:2: ( ruleFunc_Call )
                    {
                    // InternalStructuredTextParser.g:1871:2: ( ruleFunc_Call )
                    // InternalStructuredTextParser.g:1872:3: ruleFunc_Call
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
                    // InternalStructuredTextParser.g:1877:2: ( ( rule__Primary_Expr__Group_2__0 ) )
                    {
                    // InternalStructuredTextParser.g:1877:2: ( ( rule__Primary_Expr__Group_2__0 ) )
                    // InternalStructuredTextParser.g:1878:3: ( rule__Primary_Expr__Group_2__0 )
                    {
                     before(grammarAccess.getPrimary_ExprAccess().getGroup_2()); 
                    // InternalStructuredTextParser.g:1879:3: ( rule__Primary_Expr__Group_2__0 )
                    // InternalStructuredTextParser.g:1879:4: rule__Primary_Expr__Group_2__0
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
    // InternalStructuredTextParser.g:1887:1: rule__Func_Call__FuncAlternatives_0_0 : ( ( RULE_ID ) | ( TIME ) );
    public final void rule__Func_Call__FuncAlternatives_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1891:1: ( ( RULE_ID ) | ( TIME ) )
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
                    // InternalStructuredTextParser.g:1892:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:1892:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:1893:3: RULE_ID
                    {
                     before(grammarAccess.getFunc_CallAccess().getFuncIDTerminalRuleCall_0_0_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getFunc_CallAccess().getFuncIDTerminalRuleCall_0_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:1898:2: ( TIME )
                    {
                    // InternalStructuredTextParser.g:1898:2: ( TIME )
                    // InternalStructuredTextParser.g:1899:3: TIME
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
    // InternalStructuredTextParser.g:1908:1: rule__Param_Assign__Alternatives : ( ( ruleParam_Assign_In ) | ( ruleParam_Assign_Out ) );
    public final void rule__Param_Assign__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1912:1: ( ( ruleParam_Assign_In ) | ( ruleParam_Assign_Out ) )
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
                    // InternalStructuredTextParser.g:1913:2: ( ruleParam_Assign_In )
                    {
                    // InternalStructuredTextParser.g:1913:2: ( ruleParam_Assign_In )
                    // InternalStructuredTextParser.g:1914:3: ruleParam_Assign_In
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
                    // InternalStructuredTextParser.g:1919:2: ( ruleParam_Assign_Out )
                    {
                    // InternalStructuredTextParser.g:1919:2: ( ruleParam_Assign_Out )
                    // InternalStructuredTextParser.g:1920:3: ruleParam_Assign_Out
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
    // InternalStructuredTextParser.g:1929:1: rule__Variable_Subscript__Alternatives_0 : ( ( ruleVariable_Primary ) | ( ruleVariable_Adapter ) );
    public final void rule__Variable_Subscript__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1933:1: ( ( ruleVariable_Primary ) | ( ruleVariable_Adapter ) )
            int alt9=2;
            switch ( input.LA(1) ) {
            case RULE_ID:
                {
                int LA9_1 = input.LA(2);

                if ( (LA9_1==FullStop) ) {
                    int LA9_5 = input.LA(3);

                    if ( (LA9_5==DT||LA9_5==LT||LA9_5==T||LA9_5==RULE_ID) ) {
                        alt9=2;
                    }
                    else if ( (LA9_5==RULE_UNSIGNED_INT) ) {
                        alt9=1;
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

                    if ( (LA9_5==DT||LA9_5==LT||LA9_5==T||LA9_5==RULE_ID) ) {
                        alt9=2;
                    }
                    else if ( (LA9_5==RULE_UNSIGNED_INT) ) {
                        alt9=1;
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

                    if ( (LA9_5==DT||LA9_5==LT||LA9_5==T||LA9_5==RULE_ID) ) {
                        alt9=2;
                    }
                    else if ( (LA9_5==RULE_UNSIGNED_INT) ) {
                        alt9=1;
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

                if ( (LA9_4==FullStop) ) {
                    int LA9_5 = input.LA(3);

                    if ( (LA9_5==DT||LA9_5==LT||LA9_5==T||LA9_5==RULE_ID) ) {
                        alt9=2;
                    }
                    else if ( (LA9_5==RULE_UNSIGNED_INT) ) {
                        alt9=1;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 5, input);

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
                    // InternalStructuredTextParser.g:1934:2: ( ruleVariable_Primary )
                    {
                    // InternalStructuredTextParser.g:1934:2: ( ruleVariable_Primary )
                    // InternalStructuredTextParser.g:1935:3: ruleVariable_Primary
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
                    // InternalStructuredTextParser.g:1940:2: ( ruleVariable_Adapter )
                    {
                    // InternalStructuredTextParser.g:1940:2: ( ruleVariable_Adapter )
                    // InternalStructuredTextParser.g:1941:3: ruleVariable_Adapter
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
    // InternalStructuredTextParser.g:1950:1: rule__Multibit_Part_Access__Alternatives : ( ( ( rule__Multibit_Part_Access__Group_0__0 ) ) | ( ( rule__Multibit_Part_Access__Group_1__0 ) ) | ( ( rule__Multibit_Part_Access__Group_2__0 ) ) | ( ( rule__Multibit_Part_Access__Group_3__0 ) ) | ( ( rule__Multibit_Part_Access__Group_4__0 ) ) );
    public final void rule__Multibit_Part_Access__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1954:1: ( ( ( rule__Multibit_Part_Access__Group_0__0 ) ) | ( ( rule__Multibit_Part_Access__Group_1__0 ) ) | ( ( rule__Multibit_Part_Access__Group_2__0 ) ) | ( ( rule__Multibit_Part_Access__Group_3__0 ) ) | ( ( rule__Multibit_Part_Access__Group_4__0 ) ) )
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
                    // InternalStructuredTextParser.g:1955:2: ( ( rule__Multibit_Part_Access__Group_0__0 ) )
                    {
                    // InternalStructuredTextParser.g:1955:2: ( ( rule__Multibit_Part_Access__Group_0__0 ) )
                    // InternalStructuredTextParser.g:1956:3: ( rule__Multibit_Part_Access__Group_0__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_0()); 
                    // InternalStructuredTextParser.g:1957:3: ( rule__Multibit_Part_Access__Group_0__0 )
                    // InternalStructuredTextParser.g:1957:4: rule__Multibit_Part_Access__Group_0__0
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
                    // InternalStructuredTextParser.g:1961:2: ( ( rule__Multibit_Part_Access__Group_1__0 ) )
                    {
                    // InternalStructuredTextParser.g:1961:2: ( ( rule__Multibit_Part_Access__Group_1__0 ) )
                    // InternalStructuredTextParser.g:1962:3: ( rule__Multibit_Part_Access__Group_1__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_1()); 
                    // InternalStructuredTextParser.g:1963:3: ( rule__Multibit_Part_Access__Group_1__0 )
                    // InternalStructuredTextParser.g:1963:4: rule__Multibit_Part_Access__Group_1__0
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
                    // InternalStructuredTextParser.g:1967:2: ( ( rule__Multibit_Part_Access__Group_2__0 ) )
                    {
                    // InternalStructuredTextParser.g:1967:2: ( ( rule__Multibit_Part_Access__Group_2__0 ) )
                    // InternalStructuredTextParser.g:1968:3: ( rule__Multibit_Part_Access__Group_2__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_2()); 
                    // InternalStructuredTextParser.g:1969:3: ( rule__Multibit_Part_Access__Group_2__0 )
                    // InternalStructuredTextParser.g:1969:4: rule__Multibit_Part_Access__Group_2__0
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
                    // InternalStructuredTextParser.g:1973:2: ( ( rule__Multibit_Part_Access__Group_3__0 ) )
                    {
                    // InternalStructuredTextParser.g:1973:2: ( ( rule__Multibit_Part_Access__Group_3__0 ) )
                    // InternalStructuredTextParser.g:1974:3: ( rule__Multibit_Part_Access__Group_3__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_3()); 
                    // InternalStructuredTextParser.g:1975:3: ( rule__Multibit_Part_Access__Group_3__0 )
                    // InternalStructuredTextParser.g:1975:4: rule__Multibit_Part_Access__Group_3__0
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
                    // InternalStructuredTextParser.g:1979:2: ( ( rule__Multibit_Part_Access__Group_4__0 ) )
                    {
                    // InternalStructuredTextParser.g:1979:2: ( ( rule__Multibit_Part_Access__Group_4__0 ) )
                    // InternalStructuredTextParser.g:1980:3: ( rule__Multibit_Part_Access__Group_4__0 )
                    {
                     before(grammarAccess.getMultibit_Part_AccessAccess().getGroup_4()); 
                    // InternalStructuredTextParser.g:1981:3: ( rule__Multibit_Part_Access__Group_4__0 )
                    // InternalStructuredTextParser.g:1981:4: rule__Multibit_Part_Access__Group_4__0
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
    // InternalStructuredTextParser.g:1989:1: rule__Adapter_Name__Alternatives : ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) );
    public final void rule__Adapter_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:1993:1: ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) )
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
                    // InternalStructuredTextParser.g:1994:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:1994:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:1995:3: RULE_ID
                    {
                     before(grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getAdapter_NameAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2000:2: ( T )
                    {
                    // InternalStructuredTextParser.g:2000:2: ( T )
                    // InternalStructuredTextParser.g:2001:3: T
                    {
                     before(grammarAccess.getAdapter_NameAccess().getTKeyword_1()); 
                    match(input,T,FOLLOW_2); 
                     after(grammarAccess.getAdapter_NameAccess().getTKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2006:2: ( LT )
                    {
                    // InternalStructuredTextParser.g:2006:2: ( LT )
                    // InternalStructuredTextParser.g:2007:3: LT
                    {
                     before(grammarAccess.getAdapter_NameAccess().getLTKeyword_2()); 
                    match(input,LT,FOLLOW_2); 
                     after(grammarAccess.getAdapter_NameAccess().getLTKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2012:2: ( DT )
                    {
                    // InternalStructuredTextParser.g:2012:2: ( DT )
                    // InternalStructuredTextParser.g:2013:3: DT
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
    // InternalStructuredTextParser.g:2022:1: rule__Variable_Name__Alternatives : ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) );
    public final void rule__Variable_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2026:1: ( ( RULE_ID ) | ( T ) | ( LT ) | ( DT ) )
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
                    // InternalStructuredTextParser.g:2027:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:2027:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:2028:3: RULE_ID
                    {
                     before(grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getVariable_NameAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2033:2: ( T )
                    {
                    // InternalStructuredTextParser.g:2033:2: ( T )
                    // InternalStructuredTextParser.g:2034:3: T
                    {
                     before(grammarAccess.getVariable_NameAccess().getTKeyword_1()); 
                    match(input,T,FOLLOW_2); 
                     after(grammarAccess.getVariable_NameAccess().getTKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2039:2: ( LT )
                    {
                    // InternalStructuredTextParser.g:2039:2: ( LT )
                    // InternalStructuredTextParser.g:2040:3: LT
                    {
                     before(grammarAccess.getVariable_NameAccess().getLTKeyword_2()); 
                    match(input,LT,FOLLOW_2); 
                     after(grammarAccess.getVariable_NameAccess().getLTKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2045:2: ( DT )
                    {
                    // InternalStructuredTextParser.g:2045:2: ( DT )
                    // InternalStructuredTextParser.g:2046:3: DT
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
    // InternalStructuredTextParser.g:2055:1: rule__Constant__Alternatives : ( ( ruleNumeric_Literal ) | ( ruleChar_Literal ) | ( ruleTime_Literal ) | ( ruleBool_Literal ) );
    public final void rule__Constant__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2059:1: ( ( ruleNumeric_Literal ) | ( ruleChar_Literal ) | ( ruleTime_Literal ) | ( ruleBool_Literal ) )
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
                    // InternalStructuredTextParser.g:2060:2: ( ruleNumeric_Literal )
                    {
                    // InternalStructuredTextParser.g:2060:2: ( ruleNumeric_Literal )
                    // InternalStructuredTextParser.g:2061:3: ruleNumeric_Literal
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
                    // InternalStructuredTextParser.g:2066:2: ( ruleChar_Literal )
                    {
                    // InternalStructuredTextParser.g:2066:2: ( ruleChar_Literal )
                    // InternalStructuredTextParser.g:2067:3: ruleChar_Literal
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
                    // InternalStructuredTextParser.g:2072:2: ( ruleTime_Literal )
                    {
                    // InternalStructuredTextParser.g:2072:2: ( ruleTime_Literal )
                    // InternalStructuredTextParser.g:2073:3: ruleTime_Literal
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
                    // InternalStructuredTextParser.g:2078:2: ( ruleBool_Literal )
                    {
                    // InternalStructuredTextParser.g:2078:2: ( ruleBool_Literal )
                    // InternalStructuredTextParser.g:2079:3: ruleBool_Literal
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
    // InternalStructuredTextParser.g:2088:1: rule__Numeric_Literal__Alternatives : ( ( ruleInt_Literal ) | ( ruleReal_Literal ) );
    public final void rule__Numeric_Literal__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2092:1: ( ( ruleInt_Literal ) | ( ruleReal_Literal ) )
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
                    // InternalStructuredTextParser.g:2093:2: ( ruleInt_Literal )
                    {
                    // InternalStructuredTextParser.g:2093:2: ( ruleInt_Literal )
                    // InternalStructuredTextParser.g:2094:3: ruleInt_Literal
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
                    // InternalStructuredTextParser.g:2099:2: ( ruleReal_Literal )
                    {
                    // InternalStructuredTextParser.g:2099:2: ( ruleReal_Literal )
                    // InternalStructuredTextParser.g:2100:3: ruleReal_Literal
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
    // InternalStructuredTextParser.g:2109:1: rule__Int_Literal__ValueAlternatives_1_0 : ( ( ruleSigned_Int ) | ( RULE_BINARY_INT ) | ( RULE_OCTAL_INT ) | ( RULE_HEX_INT ) );
    public final void rule__Int_Literal__ValueAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2113:1: ( ( ruleSigned_Int ) | ( RULE_BINARY_INT ) | ( RULE_OCTAL_INT ) | ( RULE_HEX_INT ) )
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
                    // InternalStructuredTextParser.g:2114:2: ( ruleSigned_Int )
                    {
                    // InternalStructuredTextParser.g:2114:2: ( ruleSigned_Int )
                    // InternalStructuredTextParser.g:2115:3: ruleSigned_Int
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
                    // InternalStructuredTextParser.g:2120:2: ( RULE_BINARY_INT )
                    {
                    // InternalStructuredTextParser.g:2120:2: ( RULE_BINARY_INT )
                    // InternalStructuredTextParser.g:2121:3: RULE_BINARY_INT
                    {
                     before(grammarAccess.getInt_LiteralAccess().getValueBINARY_INTTerminalRuleCall_1_0_1()); 
                    match(input,RULE_BINARY_INT,FOLLOW_2); 
                     after(grammarAccess.getInt_LiteralAccess().getValueBINARY_INTTerminalRuleCall_1_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2126:2: ( RULE_OCTAL_INT )
                    {
                    // InternalStructuredTextParser.g:2126:2: ( RULE_OCTAL_INT )
                    // InternalStructuredTextParser.g:2127:3: RULE_OCTAL_INT
                    {
                     before(grammarAccess.getInt_LiteralAccess().getValueOCTAL_INTTerminalRuleCall_1_0_2()); 
                    match(input,RULE_OCTAL_INT,FOLLOW_2); 
                     after(grammarAccess.getInt_LiteralAccess().getValueOCTAL_INTTerminalRuleCall_1_0_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2132:2: ( RULE_HEX_INT )
                    {
                    // InternalStructuredTextParser.g:2132:2: ( RULE_HEX_INT )
                    // InternalStructuredTextParser.g:2133:3: RULE_HEX_INT
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
    // InternalStructuredTextParser.g:2142:1: rule__Signed_Int__Alternatives_0 : ( ( PlusSign ) | ( HyphenMinus ) );
    public final void rule__Signed_Int__Alternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2146:1: ( ( PlusSign ) | ( HyphenMinus ) )
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
                    // InternalStructuredTextParser.g:2147:2: ( PlusSign )
                    {
                    // InternalStructuredTextParser.g:2147:2: ( PlusSign )
                    // InternalStructuredTextParser.g:2148:3: PlusSign
                    {
                     before(grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0()); 
                    match(input,PlusSign,FOLLOW_2); 
                     after(grammarAccess.getSigned_IntAccess().getPlusSignKeyword_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2153:2: ( HyphenMinus )
                    {
                    // InternalStructuredTextParser.g:2153:2: ( HyphenMinus )
                    // InternalStructuredTextParser.g:2154:3: HyphenMinus
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
    // InternalStructuredTextParser.g:2163:1: rule__Bool_Value__Alternatives : ( ( FALSE ) | ( TRUE ) );
    public final void rule__Bool_Value__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2167:1: ( ( FALSE ) | ( TRUE ) )
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
                    // InternalStructuredTextParser.g:2168:2: ( FALSE )
                    {
                    // InternalStructuredTextParser.g:2168:2: ( FALSE )
                    // InternalStructuredTextParser.g:2169:3: FALSE
                    {
                     before(grammarAccess.getBool_ValueAccess().getFALSEKeyword_0()); 
                    match(input,FALSE,FOLLOW_2); 
                     after(grammarAccess.getBool_ValueAccess().getFALSEKeyword_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2174:2: ( TRUE )
                    {
                    // InternalStructuredTextParser.g:2174:2: ( TRUE )
                    // InternalStructuredTextParser.g:2175:3: TRUE
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
    // InternalStructuredTextParser.g:2184:1: rule__Char_Literal__ValueAlternatives_1_0 : ( ( RULE_S_BYTE_CHAR_STR ) | ( RULE_D_BYTE_CHAR_STR ) );
    public final void rule__Char_Literal__ValueAlternatives_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2188:1: ( ( RULE_S_BYTE_CHAR_STR ) | ( RULE_D_BYTE_CHAR_STR ) )
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
                    // InternalStructuredTextParser.g:2189:2: ( RULE_S_BYTE_CHAR_STR )
                    {
                    // InternalStructuredTextParser.g:2189:2: ( RULE_S_BYTE_CHAR_STR )
                    // InternalStructuredTextParser.g:2190:3: RULE_S_BYTE_CHAR_STR
                    {
                     before(grammarAccess.getChar_LiteralAccess().getValueS_BYTE_CHAR_STRTerminalRuleCall_1_0_0()); 
                    match(input,RULE_S_BYTE_CHAR_STR,FOLLOW_2); 
                     after(grammarAccess.getChar_LiteralAccess().getValueS_BYTE_CHAR_STRTerminalRuleCall_1_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2195:2: ( RULE_D_BYTE_CHAR_STR )
                    {
                    // InternalStructuredTextParser.g:2195:2: ( RULE_D_BYTE_CHAR_STR )
                    // InternalStructuredTextParser.g:2196:3: RULE_D_BYTE_CHAR_STR
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
    // InternalStructuredTextParser.g:2205:1: rule__Time_Literal__LiteralAlternatives_0 : ( ( RULE_TIME ) | ( RULE_DATE ) | ( RULE_TIMEOFDAY ) | ( RULE_DATETIME ) );
    public final void rule__Time_Literal__LiteralAlternatives_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2209:1: ( ( RULE_TIME ) | ( RULE_DATE ) | ( RULE_TIMEOFDAY ) | ( RULE_DATETIME ) )
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
                    // InternalStructuredTextParser.g:2210:2: ( RULE_TIME )
                    {
                    // InternalStructuredTextParser.g:2210:2: ( RULE_TIME )
                    // InternalStructuredTextParser.g:2211:3: RULE_TIME
                    {
                     before(grammarAccess.getTime_LiteralAccess().getLiteralTIMETerminalRuleCall_0_0()); 
                    match(input,RULE_TIME,FOLLOW_2); 
                     after(grammarAccess.getTime_LiteralAccess().getLiteralTIMETerminalRuleCall_0_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2216:2: ( RULE_DATE )
                    {
                    // InternalStructuredTextParser.g:2216:2: ( RULE_DATE )
                    // InternalStructuredTextParser.g:2217:3: RULE_DATE
                    {
                     before(grammarAccess.getTime_LiteralAccess().getLiteralDATETerminalRuleCall_0_1()); 
                    match(input,RULE_DATE,FOLLOW_2); 
                     after(grammarAccess.getTime_LiteralAccess().getLiteralDATETerminalRuleCall_0_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2222:2: ( RULE_TIMEOFDAY )
                    {
                    // InternalStructuredTextParser.g:2222:2: ( RULE_TIMEOFDAY )
                    // InternalStructuredTextParser.g:2223:3: RULE_TIMEOFDAY
                    {
                     before(grammarAccess.getTime_LiteralAccess().getLiteralTIMEOFDAYTerminalRuleCall_0_2()); 
                    match(input,RULE_TIMEOFDAY,FOLLOW_2); 
                     after(grammarAccess.getTime_LiteralAccess().getLiteralTIMEOFDAYTerminalRuleCall_0_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2228:2: ( RULE_DATETIME )
                    {
                    // InternalStructuredTextParser.g:2228:2: ( RULE_DATETIME )
                    // InternalStructuredTextParser.g:2229:3: RULE_DATETIME
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
    // InternalStructuredTextParser.g:2238:1: rule__Type_Name__Alternatives : ( ( RULE_ID ) | ( DINT ) | ( INT ) | ( SINT ) | ( LINT ) | ( UINT ) | ( USINT ) | ( UDINT ) | ( ULINT ) | ( REAL ) | ( LREAL ) | ( STRING ) | ( WSTRING ) | ( CHAR ) | ( WCHAR ) | ( TIME ) | ( LTIME ) | ( TIME_OF_DAY ) | ( LTIME_OF_DAY ) | ( TOD ) | ( LTOD ) | ( DATE ) | ( LDATE ) | ( DATE_AND_TIME ) | ( LDATE_AND_TIME ) | ( BOOL ) | ( LWORD ) | ( DWORD ) | ( WORD ) | ( BYTE ) );
    public final void rule__Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2242:1: ( ( RULE_ID ) | ( DINT ) | ( INT ) | ( SINT ) | ( LINT ) | ( UINT ) | ( USINT ) | ( UDINT ) | ( ULINT ) | ( REAL ) | ( LREAL ) | ( STRING ) | ( WSTRING ) | ( CHAR ) | ( WCHAR ) | ( TIME ) | ( LTIME ) | ( TIME_OF_DAY ) | ( LTIME_OF_DAY ) | ( TOD ) | ( LTOD ) | ( DATE ) | ( LDATE ) | ( DATE_AND_TIME ) | ( LDATE_AND_TIME ) | ( BOOL ) | ( LWORD ) | ( DWORD ) | ( WORD ) | ( BYTE ) )
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
                    // InternalStructuredTextParser.g:2243:2: ( RULE_ID )
                    {
                    // InternalStructuredTextParser.g:2243:2: ( RULE_ID )
                    // InternalStructuredTextParser.g:2244:3: RULE_ID
                    {
                     before(grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0()); 
                    match(input,RULE_ID,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getIDTerminalRuleCall_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2249:2: ( DINT )
                    {
                    // InternalStructuredTextParser.g:2249:2: ( DINT )
                    // InternalStructuredTextParser.g:2250:3: DINT
                    {
                     before(grammarAccess.getType_NameAccess().getDINTKeyword_1()); 
                    match(input,DINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDINTKeyword_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2255:2: ( INT )
                    {
                    // InternalStructuredTextParser.g:2255:2: ( INT )
                    // InternalStructuredTextParser.g:2256:3: INT
                    {
                     before(grammarAccess.getType_NameAccess().getINTKeyword_2()); 
                    match(input,INT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getINTKeyword_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2261:2: ( SINT )
                    {
                    // InternalStructuredTextParser.g:2261:2: ( SINT )
                    // InternalStructuredTextParser.g:2262:3: SINT
                    {
                     before(grammarAccess.getType_NameAccess().getSINTKeyword_3()); 
                    match(input,SINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getSINTKeyword_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:2267:2: ( LINT )
                    {
                    // InternalStructuredTextParser.g:2267:2: ( LINT )
                    // InternalStructuredTextParser.g:2268:3: LINT
                    {
                     before(grammarAccess.getType_NameAccess().getLINTKeyword_4()); 
                    match(input,LINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLINTKeyword_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:2273:2: ( UINT )
                    {
                    // InternalStructuredTextParser.g:2273:2: ( UINT )
                    // InternalStructuredTextParser.g:2274:3: UINT
                    {
                     before(grammarAccess.getType_NameAccess().getUINTKeyword_5()); 
                    match(input,UINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getUINTKeyword_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:2279:2: ( USINT )
                    {
                    // InternalStructuredTextParser.g:2279:2: ( USINT )
                    // InternalStructuredTextParser.g:2280:3: USINT
                    {
                     before(grammarAccess.getType_NameAccess().getUSINTKeyword_6()); 
                    match(input,USINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getUSINTKeyword_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalStructuredTextParser.g:2285:2: ( UDINT )
                    {
                    // InternalStructuredTextParser.g:2285:2: ( UDINT )
                    // InternalStructuredTextParser.g:2286:3: UDINT
                    {
                     before(grammarAccess.getType_NameAccess().getUDINTKeyword_7()); 
                    match(input,UDINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getUDINTKeyword_7()); 

                    }


                    }
                    break;
                case 9 :
                    // InternalStructuredTextParser.g:2291:2: ( ULINT )
                    {
                    // InternalStructuredTextParser.g:2291:2: ( ULINT )
                    // InternalStructuredTextParser.g:2292:3: ULINT
                    {
                     before(grammarAccess.getType_NameAccess().getULINTKeyword_8()); 
                    match(input,ULINT,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getULINTKeyword_8()); 

                    }


                    }
                    break;
                case 10 :
                    // InternalStructuredTextParser.g:2297:2: ( REAL )
                    {
                    // InternalStructuredTextParser.g:2297:2: ( REAL )
                    // InternalStructuredTextParser.g:2298:3: REAL
                    {
                     before(grammarAccess.getType_NameAccess().getREALKeyword_9()); 
                    match(input,REAL,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getREALKeyword_9()); 

                    }


                    }
                    break;
                case 11 :
                    // InternalStructuredTextParser.g:2303:2: ( LREAL )
                    {
                    // InternalStructuredTextParser.g:2303:2: ( LREAL )
                    // InternalStructuredTextParser.g:2304:3: LREAL
                    {
                     before(grammarAccess.getType_NameAccess().getLREALKeyword_10()); 
                    match(input,LREAL,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLREALKeyword_10()); 

                    }


                    }
                    break;
                case 12 :
                    // InternalStructuredTextParser.g:2309:2: ( STRING )
                    {
                    // InternalStructuredTextParser.g:2309:2: ( STRING )
                    // InternalStructuredTextParser.g:2310:3: STRING
                    {
                     before(grammarAccess.getType_NameAccess().getSTRINGKeyword_11()); 
                    match(input,STRING,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getSTRINGKeyword_11()); 

                    }


                    }
                    break;
                case 13 :
                    // InternalStructuredTextParser.g:2315:2: ( WSTRING )
                    {
                    // InternalStructuredTextParser.g:2315:2: ( WSTRING )
                    // InternalStructuredTextParser.g:2316:3: WSTRING
                    {
                     before(grammarAccess.getType_NameAccess().getWSTRINGKeyword_12()); 
                    match(input,WSTRING,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getWSTRINGKeyword_12()); 

                    }


                    }
                    break;
                case 14 :
                    // InternalStructuredTextParser.g:2321:2: ( CHAR )
                    {
                    // InternalStructuredTextParser.g:2321:2: ( CHAR )
                    // InternalStructuredTextParser.g:2322:3: CHAR
                    {
                     before(grammarAccess.getType_NameAccess().getCHARKeyword_13()); 
                    match(input,CHAR,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getCHARKeyword_13()); 

                    }


                    }
                    break;
                case 15 :
                    // InternalStructuredTextParser.g:2327:2: ( WCHAR )
                    {
                    // InternalStructuredTextParser.g:2327:2: ( WCHAR )
                    // InternalStructuredTextParser.g:2328:3: WCHAR
                    {
                     before(grammarAccess.getType_NameAccess().getWCHARKeyword_14()); 
                    match(input,WCHAR,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getWCHARKeyword_14()); 

                    }


                    }
                    break;
                case 16 :
                    // InternalStructuredTextParser.g:2333:2: ( TIME )
                    {
                    // InternalStructuredTextParser.g:2333:2: ( TIME )
                    // InternalStructuredTextParser.g:2334:3: TIME
                    {
                     before(grammarAccess.getType_NameAccess().getTIMEKeyword_15()); 
                    match(input,TIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getTIMEKeyword_15()); 

                    }


                    }
                    break;
                case 17 :
                    // InternalStructuredTextParser.g:2339:2: ( LTIME )
                    {
                    // InternalStructuredTextParser.g:2339:2: ( LTIME )
                    // InternalStructuredTextParser.g:2340:3: LTIME
                    {
                     before(grammarAccess.getType_NameAccess().getLTIMEKeyword_16()); 
                    match(input,LTIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLTIMEKeyword_16()); 

                    }


                    }
                    break;
                case 18 :
                    // InternalStructuredTextParser.g:2345:2: ( TIME_OF_DAY )
                    {
                    // InternalStructuredTextParser.g:2345:2: ( TIME_OF_DAY )
                    // InternalStructuredTextParser.g:2346:3: TIME_OF_DAY
                    {
                     before(grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17()); 
                    match(input,TIME_OF_DAY,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getTIME_OF_DAYKeyword_17()); 

                    }


                    }
                    break;
                case 19 :
                    // InternalStructuredTextParser.g:2351:2: ( LTIME_OF_DAY )
                    {
                    // InternalStructuredTextParser.g:2351:2: ( LTIME_OF_DAY )
                    // InternalStructuredTextParser.g:2352:3: LTIME_OF_DAY
                    {
                     before(grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18()); 
                    match(input,LTIME_OF_DAY,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLTIME_OF_DAYKeyword_18()); 

                    }


                    }
                    break;
                case 20 :
                    // InternalStructuredTextParser.g:2357:2: ( TOD )
                    {
                    // InternalStructuredTextParser.g:2357:2: ( TOD )
                    // InternalStructuredTextParser.g:2358:3: TOD
                    {
                     before(grammarAccess.getType_NameAccess().getTODKeyword_19()); 
                    match(input,TOD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getTODKeyword_19()); 

                    }


                    }
                    break;
                case 21 :
                    // InternalStructuredTextParser.g:2363:2: ( LTOD )
                    {
                    // InternalStructuredTextParser.g:2363:2: ( LTOD )
                    // InternalStructuredTextParser.g:2364:3: LTOD
                    {
                     before(grammarAccess.getType_NameAccess().getLTODKeyword_20()); 
                    match(input,LTOD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLTODKeyword_20()); 

                    }


                    }
                    break;
                case 22 :
                    // InternalStructuredTextParser.g:2369:2: ( DATE )
                    {
                    // InternalStructuredTextParser.g:2369:2: ( DATE )
                    // InternalStructuredTextParser.g:2370:3: DATE
                    {
                     before(grammarAccess.getType_NameAccess().getDATEKeyword_21()); 
                    match(input,DATE,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDATEKeyword_21()); 

                    }


                    }
                    break;
                case 23 :
                    // InternalStructuredTextParser.g:2375:2: ( LDATE )
                    {
                    // InternalStructuredTextParser.g:2375:2: ( LDATE )
                    // InternalStructuredTextParser.g:2376:3: LDATE
                    {
                     before(grammarAccess.getType_NameAccess().getLDATEKeyword_22()); 
                    match(input,LDATE,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLDATEKeyword_22()); 

                    }


                    }
                    break;
                case 24 :
                    // InternalStructuredTextParser.g:2381:2: ( DATE_AND_TIME )
                    {
                    // InternalStructuredTextParser.g:2381:2: ( DATE_AND_TIME )
                    // InternalStructuredTextParser.g:2382:3: DATE_AND_TIME
                    {
                     before(grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23()); 
                    match(input,DATE_AND_TIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDATE_AND_TIMEKeyword_23()); 

                    }


                    }
                    break;
                case 25 :
                    // InternalStructuredTextParser.g:2387:2: ( LDATE_AND_TIME )
                    {
                    // InternalStructuredTextParser.g:2387:2: ( LDATE_AND_TIME )
                    // InternalStructuredTextParser.g:2388:3: LDATE_AND_TIME
                    {
                     before(grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24()); 
                    match(input,LDATE_AND_TIME,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLDATE_AND_TIMEKeyword_24()); 

                    }


                    }
                    break;
                case 26 :
                    // InternalStructuredTextParser.g:2393:2: ( BOOL )
                    {
                    // InternalStructuredTextParser.g:2393:2: ( BOOL )
                    // InternalStructuredTextParser.g:2394:3: BOOL
                    {
                     before(grammarAccess.getType_NameAccess().getBOOLKeyword_25()); 
                    match(input,BOOL,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getBOOLKeyword_25()); 

                    }


                    }
                    break;
                case 27 :
                    // InternalStructuredTextParser.g:2399:2: ( LWORD )
                    {
                    // InternalStructuredTextParser.g:2399:2: ( LWORD )
                    // InternalStructuredTextParser.g:2400:3: LWORD
                    {
                     before(grammarAccess.getType_NameAccess().getLWORDKeyword_26()); 
                    match(input,LWORD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getLWORDKeyword_26()); 

                    }


                    }
                    break;
                case 28 :
                    // InternalStructuredTextParser.g:2405:2: ( DWORD )
                    {
                    // InternalStructuredTextParser.g:2405:2: ( DWORD )
                    // InternalStructuredTextParser.g:2406:3: DWORD
                    {
                     before(grammarAccess.getType_NameAccess().getDWORDKeyword_27()); 
                    match(input,DWORD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getDWORDKeyword_27()); 

                    }


                    }
                    break;
                case 29 :
                    // InternalStructuredTextParser.g:2411:2: ( WORD )
                    {
                    // InternalStructuredTextParser.g:2411:2: ( WORD )
                    // InternalStructuredTextParser.g:2412:3: WORD
                    {
                     before(grammarAccess.getType_NameAccess().getWORDKeyword_28()); 
                    match(input,WORD,FOLLOW_2); 
                     after(grammarAccess.getType_NameAccess().getWORDKeyword_28()); 

                    }


                    }
                    break;
                case 30 :
                    // InternalStructuredTextParser.g:2417:2: ( BYTE )
                    {
                    // InternalStructuredTextParser.g:2417:2: ( BYTE )
                    // InternalStructuredTextParser.g:2418:3: BYTE
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
    // InternalStructuredTextParser.g:2427:1: rule__And_Operator__Alternatives : ( ( ( AND ) ) | ( ( Ampersand ) ) );
    public final void rule__And_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2431:1: ( ( ( AND ) ) | ( ( Ampersand ) ) )
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
                    // InternalStructuredTextParser.g:2432:2: ( ( AND ) )
                    {
                    // InternalStructuredTextParser.g:2432:2: ( ( AND ) )
                    // InternalStructuredTextParser.g:2433:3: ( AND )
                    {
                     before(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2434:3: ( AND )
                    // InternalStructuredTextParser.g:2434:4: AND
                    {
                    match(input,AND,FOLLOW_2); 

                    }

                     after(grammarAccess.getAnd_OperatorAccess().getANDEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2438:2: ( ( Ampersand ) )
                    {
                    // InternalStructuredTextParser.g:2438:2: ( ( Ampersand ) )
                    // InternalStructuredTextParser.g:2439:3: ( Ampersand )
                    {
                     before(grammarAccess.getAnd_OperatorAccess().getAMPERSANDEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2440:3: ( Ampersand )
                    // InternalStructuredTextParser.g:2440:4: Ampersand
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
    // InternalStructuredTextParser.g:2448:1: rule__Compare_Operator__Alternatives : ( ( ( EqualsSign ) ) | ( ( LessThanSignGreaterThanSign ) ) );
    public final void rule__Compare_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2452:1: ( ( ( EqualsSign ) ) | ( ( LessThanSignGreaterThanSign ) ) )
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
                    // InternalStructuredTextParser.g:2453:2: ( ( EqualsSign ) )
                    {
                    // InternalStructuredTextParser.g:2453:2: ( ( EqualsSign ) )
                    // InternalStructuredTextParser.g:2454:3: ( EqualsSign )
                    {
                     before(grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2455:3: ( EqualsSign )
                    // InternalStructuredTextParser.g:2455:4: EqualsSign
                    {
                    match(input,EqualsSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getCompare_OperatorAccess().getEQEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2459:2: ( ( LessThanSignGreaterThanSign ) )
                    {
                    // InternalStructuredTextParser.g:2459:2: ( ( LessThanSignGreaterThanSign ) )
                    // InternalStructuredTextParser.g:2460:3: ( LessThanSignGreaterThanSign )
                    {
                     before(grammarAccess.getCompare_OperatorAccess().getNEEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2461:3: ( LessThanSignGreaterThanSign )
                    // InternalStructuredTextParser.g:2461:4: LessThanSignGreaterThanSign
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
    // InternalStructuredTextParser.g:2469:1: rule__Equ_Operator__Alternatives : ( ( ( LessThanSign ) ) | ( ( LessThanSignEqualsSign ) ) | ( ( GreaterThanSign ) ) | ( ( GreaterThanSignEqualsSign ) ) );
    public final void rule__Equ_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2473:1: ( ( ( LessThanSign ) ) | ( ( LessThanSignEqualsSign ) ) | ( ( GreaterThanSign ) ) | ( ( GreaterThanSignEqualsSign ) ) )
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
                    // InternalStructuredTextParser.g:2474:2: ( ( LessThanSign ) )
                    {
                    // InternalStructuredTextParser.g:2474:2: ( ( LessThanSign ) )
                    // InternalStructuredTextParser.g:2475:3: ( LessThanSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2476:3: ( LessThanSign )
                    // InternalStructuredTextParser.g:2476:4: LessThanSign
                    {
                    match(input,LessThanSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getEqu_OperatorAccess().getLTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2480:2: ( ( LessThanSignEqualsSign ) )
                    {
                    // InternalStructuredTextParser.g:2480:2: ( ( LessThanSignEqualsSign ) )
                    // InternalStructuredTextParser.g:2481:3: ( LessThanSignEqualsSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2482:3: ( LessThanSignEqualsSign )
                    // InternalStructuredTextParser.g:2482:4: LessThanSignEqualsSign
                    {
                    match(input,LessThanSignEqualsSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getEqu_OperatorAccess().getLEEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2486:2: ( ( GreaterThanSign ) )
                    {
                    // InternalStructuredTextParser.g:2486:2: ( ( GreaterThanSign ) )
                    // InternalStructuredTextParser.g:2487:3: ( GreaterThanSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2488:3: ( GreaterThanSign )
                    // InternalStructuredTextParser.g:2488:4: GreaterThanSign
                    {
                    match(input,GreaterThanSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getEqu_OperatorAccess().getGTEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2492:2: ( ( GreaterThanSignEqualsSign ) )
                    {
                    // InternalStructuredTextParser.g:2492:2: ( ( GreaterThanSignEqualsSign ) )
                    // InternalStructuredTextParser.g:2493:3: ( GreaterThanSignEqualsSign )
                    {
                     before(grammarAccess.getEqu_OperatorAccess().getGEEnumLiteralDeclaration_3()); 
                    // InternalStructuredTextParser.g:2494:3: ( GreaterThanSignEqualsSign )
                    // InternalStructuredTextParser.g:2494:4: GreaterThanSignEqualsSign
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
    // InternalStructuredTextParser.g:2502:1: rule__Add_Operator__Alternatives : ( ( ( PlusSign ) ) | ( ( HyphenMinus ) ) );
    public final void rule__Add_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2506:1: ( ( ( PlusSign ) ) | ( ( HyphenMinus ) ) )
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
                    // InternalStructuredTextParser.g:2507:2: ( ( PlusSign ) )
                    {
                    // InternalStructuredTextParser.g:2507:2: ( ( PlusSign ) )
                    // InternalStructuredTextParser.g:2508:3: ( PlusSign )
                    {
                     before(grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2509:3: ( PlusSign )
                    // InternalStructuredTextParser.g:2509:4: PlusSign
                    {
                    match(input,PlusSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getAdd_OperatorAccess().getADDEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2513:2: ( ( HyphenMinus ) )
                    {
                    // InternalStructuredTextParser.g:2513:2: ( ( HyphenMinus ) )
                    // InternalStructuredTextParser.g:2514:3: ( HyphenMinus )
                    {
                     before(grammarAccess.getAdd_OperatorAccess().getSUBEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2515:3: ( HyphenMinus )
                    // InternalStructuredTextParser.g:2515:4: HyphenMinus
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
    // InternalStructuredTextParser.g:2523:1: rule__Term_Operator__Alternatives : ( ( ( Asterisk ) ) | ( ( Solidus ) ) | ( ( MOD ) ) );
    public final void rule__Term_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2527:1: ( ( ( Asterisk ) ) | ( ( Solidus ) ) | ( ( MOD ) ) )
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
                    // InternalStructuredTextParser.g:2528:2: ( ( Asterisk ) )
                    {
                    // InternalStructuredTextParser.g:2528:2: ( ( Asterisk ) )
                    // InternalStructuredTextParser.g:2529:3: ( Asterisk )
                    {
                     before(grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2530:3: ( Asterisk )
                    // InternalStructuredTextParser.g:2530:4: Asterisk
                    {
                    match(input,Asterisk,FOLLOW_2); 

                    }

                     after(grammarAccess.getTerm_OperatorAccess().getMULEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2534:2: ( ( Solidus ) )
                    {
                    // InternalStructuredTextParser.g:2534:2: ( ( Solidus ) )
                    // InternalStructuredTextParser.g:2535:3: ( Solidus )
                    {
                     before(grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2536:3: ( Solidus )
                    // InternalStructuredTextParser.g:2536:4: Solidus
                    {
                    match(input,Solidus,FOLLOW_2); 

                    }

                     after(grammarAccess.getTerm_OperatorAccess().getDIVEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2540:2: ( ( MOD ) )
                    {
                    // InternalStructuredTextParser.g:2540:2: ( ( MOD ) )
                    // InternalStructuredTextParser.g:2541:3: ( MOD )
                    {
                     before(grammarAccess.getTerm_OperatorAccess().getMODEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2542:3: ( MOD )
                    // InternalStructuredTextParser.g:2542:4: MOD
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
    // InternalStructuredTextParser.g:2550:1: rule__Unary_Operator__Alternatives : ( ( ( HyphenMinus ) ) | ( ( PlusSign ) ) | ( ( NOT ) ) );
    public final void rule__Unary_Operator__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2554:1: ( ( ( HyphenMinus ) ) | ( ( PlusSign ) ) | ( ( NOT ) ) )
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
                    // InternalStructuredTextParser.g:2555:2: ( ( HyphenMinus ) )
                    {
                    // InternalStructuredTextParser.g:2555:2: ( ( HyphenMinus ) )
                    // InternalStructuredTextParser.g:2556:3: ( HyphenMinus )
                    {
                     before(grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2557:3: ( HyphenMinus )
                    // InternalStructuredTextParser.g:2557:4: HyphenMinus
                    {
                    match(input,HyphenMinus,FOLLOW_2); 

                    }

                     after(grammarAccess.getUnary_OperatorAccess().getMINUSEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2561:2: ( ( PlusSign ) )
                    {
                    // InternalStructuredTextParser.g:2561:2: ( ( PlusSign ) )
                    // InternalStructuredTextParser.g:2562:3: ( PlusSign )
                    {
                     before(grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2563:3: ( PlusSign )
                    // InternalStructuredTextParser.g:2563:4: PlusSign
                    {
                    match(input,PlusSign,FOLLOW_2); 

                    }

                     after(grammarAccess.getUnary_OperatorAccess().getPLUSEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2567:2: ( ( NOT ) )
                    {
                    // InternalStructuredTextParser.g:2567:2: ( ( NOT ) )
                    // InternalStructuredTextParser.g:2568:3: ( NOT )
                    {
                     before(grammarAccess.getUnary_OperatorAccess().getNOTEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2569:3: ( NOT )
                    // InternalStructuredTextParser.g:2569:4: NOT
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
    // InternalStructuredTextParser.g:2577:1: rule__Int_Type_Name__Alternatives : ( ( ( DINT ) ) | ( ( INT ) ) | ( ( SINT ) ) | ( ( LINT ) ) | ( ( UINT ) ) | ( ( USINT ) ) | ( ( UDINT ) ) | ( ( ULINT ) ) );
    public final void rule__Int_Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2581:1: ( ( ( DINT ) ) | ( ( INT ) ) | ( ( SINT ) ) | ( ( LINT ) ) | ( ( UINT ) ) | ( ( USINT ) ) | ( ( UDINT ) ) | ( ( ULINT ) ) )
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
                    // InternalStructuredTextParser.g:2582:2: ( ( DINT ) )
                    {
                    // InternalStructuredTextParser.g:2582:2: ( ( DINT ) )
                    // InternalStructuredTextParser.g:2583:3: ( DINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2584:3: ( DINT )
                    // InternalStructuredTextParser.g:2584:4: DINT
                    {
                    match(input,DINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getDINTEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2588:2: ( ( INT ) )
                    {
                    // InternalStructuredTextParser.g:2588:2: ( ( INT ) )
                    // InternalStructuredTextParser.g:2589:3: ( INT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2590:3: ( INT )
                    // InternalStructuredTextParser.g:2590:4: INT
                    {
                    match(input,INT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getINTEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2594:2: ( ( SINT ) )
                    {
                    // InternalStructuredTextParser.g:2594:2: ( ( SINT ) )
                    // InternalStructuredTextParser.g:2595:3: ( SINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2596:3: ( SINT )
                    // InternalStructuredTextParser.g:2596:4: SINT
                    {
                    match(input,SINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getSINTEnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2600:2: ( ( LINT ) )
                    {
                    // InternalStructuredTextParser.g:2600:2: ( ( LINT ) )
                    // InternalStructuredTextParser.g:2601:3: ( LINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3()); 
                    // InternalStructuredTextParser.g:2602:3: ( LINT )
                    // InternalStructuredTextParser.g:2602:4: LINT
                    {
                    match(input,LINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getLINTEnumLiteralDeclaration_3()); 

                    }


                    }
                    break;
                case 5 :
                    // InternalStructuredTextParser.g:2606:2: ( ( UINT ) )
                    {
                    // InternalStructuredTextParser.g:2606:2: ( ( UINT ) )
                    // InternalStructuredTextParser.g:2607:3: ( UINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4()); 
                    // InternalStructuredTextParser.g:2608:3: ( UINT )
                    // InternalStructuredTextParser.g:2608:4: UINT
                    {
                    match(input,UINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getUINTEnumLiteralDeclaration_4()); 

                    }


                    }
                    break;
                case 6 :
                    // InternalStructuredTextParser.g:2612:2: ( ( USINT ) )
                    {
                    // InternalStructuredTextParser.g:2612:2: ( ( USINT ) )
                    // InternalStructuredTextParser.g:2613:3: ( USINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5()); 
                    // InternalStructuredTextParser.g:2614:3: ( USINT )
                    // InternalStructuredTextParser.g:2614:4: USINT
                    {
                    match(input,USINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getUSINTEnumLiteralDeclaration_5()); 

                    }


                    }
                    break;
                case 7 :
                    // InternalStructuredTextParser.g:2618:2: ( ( UDINT ) )
                    {
                    // InternalStructuredTextParser.g:2618:2: ( ( UDINT ) )
                    // InternalStructuredTextParser.g:2619:3: ( UDINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6()); 
                    // InternalStructuredTextParser.g:2620:3: ( UDINT )
                    // InternalStructuredTextParser.g:2620:4: UDINT
                    {
                    match(input,UDINT,FOLLOW_2); 

                    }

                     after(grammarAccess.getInt_Type_NameAccess().getUDINTEnumLiteralDeclaration_6()); 

                    }


                    }
                    break;
                case 8 :
                    // InternalStructuredTextParser.g:2624:2: ( ( ULINT ) )
                    {
                    // InternalStructuredTextParser.g:2624:2: ( ( ULINT ) )
                    // InternalStructuredTextParser.g:2625:3: ( ULINT )
                    {
                     before(grammarAccess.getInt_Type_NameAccess().getULINTEnumLiteralDeclaration_7()); 
                    // InternalStructuredTextParser.g:2626:3: ( ULINT )
                    // InternalStructuredTextParser.g:2626:4: ULINT
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
    // InternalStructuredTextParser.g:2634:1: rule__Real_Type_Name__Alternatives : ( ( ( REAL ) ) | ( ( LREAL ) ) );
    public final void rule__Real_Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2638:1: ( ( ( REAL ) ) | ( ( LREAL ) ) )
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
                    // InternalStructuredTextParser.g:2639:2: ( ( REAL ) )
                    {
                    // InternalStructuredTextParser.g:2639:2: ( ( REAL ) )
                    // InternalStructuredTextParser.g:2640:3: ( REAL )
                    {
                     before(grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2641:3: ( REAL )
                    // InternalStructuredTextParser.g:2641:4: REAL
                    {
                    match(input,REAL,FOLLOW_2); 

                    }

                     after(grammarAccess.getReal_Type_NameAccess().getREALEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2645:2: ( ( LREAL ) )
                    {
                    // InternalStructuredTextParser.g:2645:2: ( ( LREAL ) )
                    // InternalStructuredTextParser.g:2646:3: ( LREAL )
                    {
                     before(grammarAccess.getReal_Type_NameAccess().getLREALEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2647:3: ( LREAL )
                    // InternalStructuredTextParser.g:2647:4: LREAL
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
    // InternalStructuredTextParser.g:2655:1: rule__String_Type_Name__Alternatives : ( ( ( STRING ) ) | ( ( WSTRING ) ) | ( ( CHAR ) ) | ( ( WCHAR ) ) );
    public final void rule__String_Type_Name__Alternatives() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2659:1: ( ( ( STRING ) ) | ( ( WSTRING ) ) | ( ( CHAR ) ) | ( ( WCHAR ) ) )
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
                    // InternalStructuredTextParser.g:2660:2: ( ( STRING ) )
                    {
                    // InternalStructuredTextParser.g:2660:2: ( ( STRING ) )
                    // InternalStructuredTextParser.g:2661:3: ( STRING )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0()); 
                    // InternalStructuredTextParser.g:2662:3: ( STRING )
                    // InternalStructuredTextParser.g:2662:4: STRING
                    {
                    match(input,STRING,FOLLOW_2); 

                    }

                     after(grammarAccess.getString_Type_NameAccess().getSTRINGEnumLiteralDeclaration_0()); 

                    }


                    }
                    break;
                case 2 :
                    // InternalStructuredTextParser.g:2666:2: ( ( WSTRING ) )
                    {
                    // InternalStructuredTextParser.g:2666:2: ( ( WSTRING ) )
                    // InternalStructuredTextParser.g:2667:3: ( WSTRING )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1()); 
                    // InternalStructuredTextParser.g:2668:3: ( WSTRING )
                    // InternalStructuredTextParser.g:2668:4: WSTRING
                    {
                    match(input,WSTRING,FOLLOW_2); 

                    }

                     after(grammarAccess.getString_Type_NameAccess().getWSTRINGEnumLiteralDeclaration_1()); 

                    }


                    }
                    break;
                case 3 :
                    // InternalStructuredTextParser.g:2672:2: ( ( CHAR ) )
                    {
                    // InternalStructuredTextParser.g:2672:2: ( ( CHAR ) )
                    // InternalStructuredTextParser.g:2673:3: ( CHAR )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2()); 
                    // InternalStructuredTextParser.g:2674:3: ( CHAR )
                    // InternalStructuredTextParser.g:2674:4: CHAR
                    {
                    match(input,CHAR,FOLLOW_2); 

                    }

                     after(grammarAccess.getString_Type_NameAccess().getCHAREnumLiteralDeclaration_2()); 

                    }


                    }
                    break;
                case 4 :
                    // InternalStructuredTextParser.g:2678:2: ( ( WCHAR ) )
                    {
                    // InternalStructuredTextParser.g:2678:2: ( ( WCHAR ) )
                    // InternalStructuredTextParser.g:2679:3: ( WCHAR )
                    {
                     before(grammarAccess.getString_Type_NameAccess().getWCHAREnumLiteralDeclaration_3()); 
                    // InternalStructuredTextParser.g:2680:3: ( WCHAR )
                    // InternalStructuredTextParser.g:2680:4: WCHAR
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
    // InternalStructuredTextParser.g:2688:1: rule__StructuredTextAlgorithm__Group__0 : rule__StructuredTextAlgorithm__Group__0__Impl rule__StructuredTextAlgorithm__Group__1 ;
    public final void rule__StructuredTextAlgorithm__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2692:1: ( rule__StructuredTextAlgorithm__Group__0__Impl rule__StructuredTextAlgorithm__Group__1 )
            // InternalStructuredTextParser.g:2693:2: rule__StructuredTextAlgorithm__Group__0__Impl rule__StructuredTextAlgorithm__Group__1
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
    // InternalStructuredTextParser.g:2700:1: rule__StructuredTextAlgorithm__Group__0__Impl : ( () ) ;
    public final void rule__StructuredTextAlgorithm__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2704:1: ( ( () ) )
            // InternalStructuredTextParser.g:2705:1: ( () )
            {
            // InternalStructuredTextParser.g:2705:1: ( () )
            // InternalStructuredTextParser.g:2706:2: ()
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getStructuredTextAlgorithmAction_0()); 
            // InternalStructuredTextParser.g:2707:2: ()
            // InternalStructuredTextParser.g:2707:3: 
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
    // InternalStructuredTextParser.g:2715:1: rule__StructuredTextAlgorithm__Group__1 : rule__StructuredTextAlgorithm__Group__1__Impl rule__StructuredTextAlgorithm__Group__2 ;
    public final void rule__StructuredTextAlgorithm__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2719:1: ( rule__StructuredTextAlgorithm__Group__1__Impl rule__StructuredTextAlgorithm__Group__2 )
            // InternalStructuredTextParser.g:2720:2: rule__StructuredTextAlgorithm__Group__1__Impl rule__StructuredTextAlgorithm__Group__2
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
    // InternalStructuredTextParser.g:2727:1: rule__StructuredTextAlgorithm__Group__1__Impl : ( ( rule__StructuredTextAlgorithm__Group_1__0 )? ) ;
    public final void rule__StructuredTextAlgorithm__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2731:1: ( ( ( rule__StructuredTextAlgorithm__Group_1__0 )? ) )
            // InternalStructuredTextParser.g:2732:1: ( ( rule__StructuredTextAlgorithm__Group_1__0 )? )
            {
            // InternalStructuredTextParser.g:2732:1: ( ( rule__StructuredTextAlgorithm__Group_1__0 )? )
            // InternalStructuredTextParser.g:2733:2: ( rule__StructuredTextAlgorithm__Group_1__0 )?
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:2734:2: ( rule__StructuredTextAlgorithm__Group_1__0 )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==VAR) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // InternalStructuredTextParser.g:2734:3: rule__StructuredTextAlgorithm__Group_1__0
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
    // InternalStructuredTextParser.g:2742:1: rule__StructuredTextAlgorithm__Group__2 : rule__StructuredTextAlgorithm__Group__2__Impl ;
    public final void rule__StructuredTextAlgorithm__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2746:1: ( rule__StructuredTextAlgorithm__Group__2__Impl )
            // InternalStructuredTextParser.g:2747:2: rule__StructuredTextAlgorithm__Group__2__Impl
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
    // InternalStructuredTextParser.g:2753:1: rule__StructuredTextAlgorithm__Group__2__Impl : ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) ) ;
    public final void rule__StructuredTextAlgorithm__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2757:1: ( ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) ) )
            // InternalStructuredTextParser.g:2758:1: ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) )
            {
            // InternalStructuredTextParser.g:2758:1: ( ( rule__StructuredTextAlgorithm__StatementsAssignment_2 ) )
            // InternalStructuredTextParser.g:2759:2: ( rule__StructuredTextAlgorithm__StatementsAssignment_2 )
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getStatementsAssignment_2()); 
            // InternalStructuredTextParser.g:2760:2: ( rule__StructuredTextAlgorithm__StatementsAssignment_2 )
            // InternalStructuredTextParser.g:2760:3: rule__StructuredTextAlgorithm__StatementsAssignment_2
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
    // InternalStructuredTextParser.g:2769:1: rule__StructuredTextAlgorithm__Group_1__0 : rule__StructuredTextAlgorithm__Group_1__0__Impl rule__StructuredTextAlgorithm__Group_1__1 ;
    public final void rule__StructuredTextAlgorithm__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2773:1: ( rule__StructuredTextAlgorithm__Group_1__0__Impl rule__StructuredTextAlgorithm__Group_1__1 )
            // InternalStructuredTextParser.g:2774:2: rule__StructuredTextAlgorithm__Group_1__0__Impl rule__StructuredTextAlgorithm__Group_1__1
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
    // InternalStructuredTextParser.g:2781:1: rule__StructuredTextAlgorithm__Group_1__0__Impl : ( VAR ) ;
    public final void rule__StructuredTextAlgorithm__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2785:1: ( ( VAR ) )
            // InternalStructuredTextParser.g:2786:1: ( VAR )
            {
            // InternalStructuredTextParser.g:2786:1: ( VAR )
            // InternalStructuredTextParser.g:2787:2: VAR
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
    // InternalStructuredTextParser.g:2796:1: rule__StructuredTextAlgorithm__Group_1__1 : rule__StructuredTextAlgorithm__Group_1__1__Impl rule__StructuredTextAlgorithm__Group_1__2 ;
    public final void rule__StructuredTextAlgorithm__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2800:1: ( rule__StructuredTextAlgorithm__Group_1__1__Impl rule__StructuredTextAlgorithm__Group_1__2 )
            // InternalStructuredTextParser.g:2801:2: rule__StructuredTextAlgorithm__Group_1__1__Impl rule__StructuredTextAlgorithm__Group_1__2
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
    // InternalStructuredTextParser.g:2808:1: rule__StructuredTextAlgorithm__Group_1__1__Impl : ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* ) ;
    public final void rule__StructuredTextAlgorithm__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2812:1: ( ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* ) )
            // InternalStructuredTextParser.g:2813:1: ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* )
            {
            // InternalStructuredTextParser.g:2813:1: ( ( rule__StructuredTextAlgorithm__Group_1_1__0 )* )
            // InternalStructuredTextParser.g:2814:2: ( rule__StructuredTextAlgorithm__Group_1_1__0 )*
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getGroup_1_1()); 
            // InternalStructuredTextParser.g:2815:2: ( rule__StructuredTextAlgorithm__Group_1_1__0 )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==CONSTANT||LA31_0==RULE_ID) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // InternalStructuredTextParser.g:2815:3: rule__StructuredTextAlgorithm__Group_1_1__0
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
    // InternalStructuredTextParser.g:2823:1: rule__StructuredTextAlgorithm__Group_1__2 : rule__StructuredTextAlgorithm__Group_1__2__Impl ;
    public final void rule__StructuredTextAlgorithm__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2827:1: ( rule__StructuredTextAlgorithm__Group_1__2__Impl )
            // InternalStructuredTextParser.g:2828:2: rule__StructuredTextAlgorithm__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:2834:1: rule__StructuredTextAlgorithm__Group_1__2__Impl : ( END_VAR ) ;
    public final void rule__StructuredTextAlgorithm__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2838:1: ( ( END_VAR ) )
            // InternalStructuredTextParser.g:2839:1: ( END_VAR )
            {
            // InternalStructuredTextParser.g:2839:1: ( END_VAR )
            // InternalStructuredTextParser.g:2840:2: END_VAR
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
    // InternalStructuredTextParser.g:2850:1: rule__StructuredTextAlgorithm__Group_1_1__0 : rule__StructuredTextAlgorithm__Group_1_1__0__Impl rule__StructuredTextAlgorithm__Group_1_1__1 ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2854:1: ( rule__StructuredTextAlgorithm__Group_1_1__0__Impl rule__StructuredTextAlgorithm__Group_1_1__1 )
            // InternalStructuredTextParser.g:2855:2: rule__StructuredTextAlgorithm__Group_1_1__0__Impl rule__StructuredTextAlgorithm__Group_1_1__1
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
    // InternalStructuredTextParser.g:2862:1: rule__StructuredTextAlgorithm__Group_1_1__0__Impl : ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) ) ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2866:1: ( ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) ) )
            // InternalStructuredTextParser.g:2867:1: ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) )
            {
            // InternalStructuredTextParser.g:2867:1: ( ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 ) )
            // InternalStructuredTextParser.g:2868:2: ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 )
            {
             before(grammarAccess.getStructuredTextAlgorithmAccess().getLocalVariablesAssignment_1_1_0()); 
            // InternalStructuredTextParser.g:2869:2: ( rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 )
            // InternalStructuredTextParser.g:2869:3: rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0
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
    // InternalStructuredTextParser.g:2877:1: rule__StructuredTextAlgorithm__Group_1_1__1 : rule__StructuredTextAlgorithm__Group_1_1__1__Impl ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2881:1: ( rule__StructuredTextAlgorithm__Group_1_1__1__Impl )
            // InternalStructuredTextParser.g:2882:2: rule__StructuredTextAlgorithm__Group_1_1__1__Impl
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
    // InternalStructuredTextParser.g:2888:1: rule__StructuredTextAlgorithm__Group_1_1__1__Impl : ( Semicolon ) ;
    public final void rule__StructuredTextAlgorithm__Group_1_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2892:1: ( ( Semicolon ) )
            // InternalStructuredTextParser.g:2893:1: ( Semicolon )
            {
            // InternalStructuredTextParser.g:2893:1: ( Semicolon )
            // InternalStructuredTextParser.g:2894:2: Semicolon
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
    // InternalStructuredTextParser.g:2904:1: rule__Var_Decl_Local__Group__0 : rule__Var_Decl_Local__Group__0__Impl rule__Var_Decl_Local__Group__1 ;
    public final void rule__Var_Decl_Local__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2908:1: ( rule__Var_Decl_Local__Group__0__Impl rule__Var_Decl_Local__Group__1 )
            // InternalStructuredTextParser.g:2909:2: rule__Var_Decl_Local__Group__0__Impl rule__Var_Decl_Local__Group__1
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
    // InternalStructuredTextParser.g:2916:1: rule__Var_Decl_Local__Group__0__Impl : ( () ) ;
    public final void rule__Var_Decl_Local__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2920:1: ( ( () ) )
            // InternalStructuredTextParser.g:2921:1: ( () )
            {
            // InternalStructuredTextParser.g:2921:1: ( () )
            // InternalStructuredTextParser.g:2922:2: ()
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocalVariableAction_0()); 
            // InternalStructuredTextParser.g:2923:2: ()
            // InternalStructuredTextParser.g:2923:3: 
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
    // InternalStructuredTextParser.g:2931:1: rule__Var_Decl_Local__Group__1 : rule__Var_Decl_Local__Group__1__Impl rule__Var_Decl_Local__Group__2 ;
    public final void rule__Var_Decl_Local__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2935:1: ( rule__Var_Decl_Local__Group__1__Impl rule__Var_Decl_Local__Group__2 )
            // InternalStructuredTextParser.g:2936:2: rule__Var_Decl_Local__Group__1__Impl rule__Var_Decl_Local__Group__2
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
    // InternalStructuredTextParser.g:2943:1: rule__Var_Decl_Local__Group__1__Impl : ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? ) ;
    public final void rule__Var_Decl_Local__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2947:1: ( ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? ) )
            // InternalStructuredTextParser.g:2948:1: ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? )
            {
            // InternalStructuredTextParser.g:2948:1: ( ( rule__Var_Decl_Local__ConstantAssignment_1 )? )
            // InternalStructuredTextParser.g:2949:2: ( rule__Var_Decl_Local__ConstantAssignment_1 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getConstantAssignment_1()); 
            // InternalStructuredTextParser.g:2950:2: ( rule__Var_Decl_Local__ConstantAssignment_1 )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==CONSTANT) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // InternalStructuredTextParser.g:2950:3: rule__Var_Decl_Local__ConstantAssignment_1
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
    // InternalStructuredTextParser.g:2958:1: rule__Var_Decl_Local__Group__2 : rule__Var_Decl_Local__Group__2__Impl rule__Var_Decl_Local__Group__3 ;
    public final void rule__Var_Decl_Local__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2962:1: ( rule__Var_Decl_Local__Group__2__Impl rule__Var_Decl_Local__Group__3 )
            // InternalStructuredTextParser.g:2963:2: rule__Var_Decl_Local__Group__2__Impl rule__Var_Decl_Local__Group__3
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
    // InternalStructuredTextParser.g:2970:1: rule__Var_Decl_Local__Group__2__Impl : ( ( rule__Var_Decl_Local__NameAssignment_2 ) ) ;
    public final void rule__Var_Decl_Local__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2974:1: ( ( ( rule__Var_Decl_Local__NameAssignment_2 ) ) )
            // InternalStructuredTextParser.g:2975:1: ( ( rule__Var_Decl_Local__NameAssignment_2 ) )
            {
            // InternalStructuredTextParser.g:2975:1: ( ( rule__Var_Decl_Local__NameAssignment_2 ) )
            // InternalStructuredTextParser.g:2976:2: ( rule__Var_Decl_Local__NameAssignment_2 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getNameAssignment_2()); 
            // InternalStructuredTextParser.g:2977:2: ( rule__Var_Decl_Local__NameAssignment_2 )
            // InternalStructuredTextParser.g:2977:3: rule__Var_Decl_Local__NameAssignment_2
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
    // InternalStructuredTextParser.g:2985:1: rule__Var_Decl_Local__Group__3 : rule__Var_Decl_Local__Group__3__Impl rule__Var_Decl_Local__Group__4 ;
    public final void rule__Var_Decl_Local__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:2989:1: ( rule__Var_Decl_Local__Group__3__Impl rule__Var_Decl_Local__Group__4 )
            // InternalStructuredTextParser.g:2990:2: rule__Var_Decl_Local__Group__3__Impl rule__Var_Decl_Local__Group__4
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
    // InternalStructuredTextParser.g:2997:1: rule__Var_Decl_Local__Group__3__Impl : ( ( rule__Var_Decl_Local__Group_3__0 )? ) ;
    public final void rule__Var_Decl_Local__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3001:1: ( ( ( rule__Var_Decl_Local__Group_3__0 )? ) )
            // InternalStructuredTextParser.g:3002:1: ( ( rule__Var_Decl_Local__Group_3__0 )? )
            {
            // InternalStructuredTextParser.g:3002:1: ( ( rule__Var_Decl_Local__Group_3__0 )? )
            // InternalStructuredTextParser.g:3003:2: ( rule__Var_Decl_Local__Group_3__0 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getGroup_3()); 
            // InternalStructuredTextParser.g:3004:2: ( rule__Var_Decl_Local__Group_3__0 )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==AT) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalStructuredTextParser.g:3004:3: rule__Var_Decl_Local__Group_3__0
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
    // InternalStructuredTextParser.g:3012:1: rule__Var_Decl_Local__Group__4 : rule__Var_Decl_Local__Group__4__Impl rule__Var_Decl_Local__Group__5 ;
    public final void rule__Var_Decl_Local__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3016:1: ( rule__Var_Decl_Local__Group__4__Impl rule__Var_Decl_Local__Group__5 )
            // InternalStructuredTextParser.g:3017:2: rule__Var_Decl_Local__Group__4__Impl rule__Var_Decl_Local__Group__5
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
    // InternalStructuredTextParser.g:3024:1: rule__Var_Decl_Local__Group__4__Impl : ( Colon ) ;
    public final void rule__Var_Decl_Local__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3028:1: ( ( Colon ) )
            // InternalStructuredTextParser.g:3029:1: ( Colon )
            {
            // InternalStructuredTextParser.g:3029:1: ( Colon )
            // InternalStructuredTextParser.g:3030:2: Colon
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
    // InternalStructuredTextParser.g:3039:1: rule__Var_Decl_Local__Group__5 : rule__Var_Decl_Local__Group__5__Impl rule__Var_Decl_Local__Group__6 ;
    public final void rule__Var_Decl_Local__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3043:1: ( rule__Var_Decl_Local__Group__5__Impl rule__Var_Decl_Local__Group__6 )
            // InternalStructuredTextParser.g:3044:2: rule__Var_Decl_Local__Group__5__Impl rule__Var_Decl_Local__Group__6
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
    // InternalStructuredTextParser.g:3051:1: rule__Var_Decl_Local__Group__5__Impl : ( ( rule__Var_Decl_Local__Group_5__0 )? ) ;
    public final void rule__Var_Decl_Local__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3055:1: ( ( ( rule__Var_Decl_Local__Group_5__0 )? ) )
            // InternalStructuredTextParser.g:3056:1: ( ( rule__Var_Decl_Local__Group_5__0 )? )
            {
            // InternalStructuredTextParser.g:3056:1: ( ( rule__Var_Decl_Local__Group_5__0 )? )
            // InternalStructuredTextParser.g:3057:2: ( rule__Var_Decl_Local__Group_5__0 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getGroup_5()); 
            // InternalStructuredTextParser.g:3058:2: ( rule__Var_Decl_Local__Group_5__0 )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==ARRAY) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalStructuredTextParser.g:3058:3: rule__Var_Decl_Local__Group_5__0
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
    // InternalStructuredTextParser.g:3066:1: rule__Var_Decl_Local__Group__6 : rule__Var_Decl_Local__Group__6__Impl rule__Var_Decl_Local__Group__7 ;
    public final void rule__Var_Decl_Local__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3070:1: ( rule__Var_Decl_Local__Group__6__Impl rule__Var_Decl_Local__Group__7 )
            // InternalStructuredTextParser.g:3071:2: rule__Var_Decl_Local__Group__6__Impl rule__Var_Decl_Local__Group__7
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
    // InternalStructuredTextParser.g:3078:1: rule__Var_Decl_Local__Group__6__Impl : ( ( rule__Var_Decl_Local__TypeAssignment_6 ) ) ;
    public final void rule__Var_Decl_Local__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3082:1: ( ( ( rule__Var_Decl_Local__TypeAssignment_6 ) ) )
            // InternalStructuredTextParser.g:3083:1: ( ( rule__Var_Decl_Local__TypeAssignment_6 ) )
            {
            // InternalStructuredTextParser.g:3083:1: ( ( rule__Var_Decl_Local__TypeAssignment_6 ) )
            // InternalStructuredTextParser.g:3084:2: ( rule__Var_Decl_Local__TypeAssignment_6 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getTypeAssignment_6()); 
            // InternalStructuredTextParser.g:3085:2: ( rule__Var_Decl_Local__TypeAssignment_6 )
            // InternalStructuredTextParser.g:3085:3: rule__Var_Decl_Local__TypeAssignment_6
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
    // InternalStructuredTextParser.g:3093:1: rule__Var_Decl_Local__Group__7 : rule__Var_Decl_Local__Group__7__Impl ;
    public final void rule__Var_Decl_Local__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3097:1: ( rule__Var_Decl_Local__Group__7__Impl )
            // InternalStructuredTextParser.g:3098:2: rule__Var_Decl_Local__Group__7__Impl
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
    // InternalStructuredTextParser.g:3104:1: rule__Var_Decl_Local__Group__7__Impl : ( ( rule__Var_Decl_Local__Group_7__0 )? ) ;
    public final void rule__Var_Decl_Local__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3108:1: ( ( ( rule__Var_Decl_Local__Group_7__0 )? ) )
            // InternalStructuredTextParser.g:3109:1: ( ( rule__Var_Decl_Local__Group_7__0 )? )
            {
            // InternalStructuredTextParser.g:3109:1: ( ( rule__Var_Decl_Local__Group_7__0 )? )
            // InternalStructuredTextParser.g:3110:2: ( rule__Var_Decl_Local__Group_7__0 )?
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getGroup_7()); 
            // InternalStructuredTextParser.g:3111:2: ( rule__Var_Decl_Local__Group_7__0 )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==ColonEqualsSign) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // InternalStructuredTextParser.g:3111:3: rule__Var_Decl_Local__Group_7__0
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
    // InternalStructuredTextParser.g:3120:1: rule__Var_Decl_Local__Group_3__0 : rule__Var_Decl_Local__Group_3__0__Impl rule__Var_Decl_Local__Group_3__1 ;
    public final void rule__Var_Decl_Local__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3124:1: ( rule__Var_Decl_Local__Group_3__0__Impl rule__Var_Decl_Local__Group_3__1 )
            // InternalStructuredTextParser.g:3125:2: rule__Var_Decl_Local__Group_3__0__Impl rule__Var_Decl_Local__Group_3__1
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
    // InternalStructuredTextParser.g:3132:1: rule__Var_Decl_Local__Group_3__0__Impl : ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) ) ;
    public final void rule__Var_Decl_Local__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3136:1: ( ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) ) )
            // InternalStructuredTextParser.g:3137:1: ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) )
            {
            // InternalStructuredTextParser.g:3137:1: ( ( rule__Var_Decl_Local__LocatedAssignment_3_0 ) )
            // InternalStructuredTextParser.g:3138:2: ( rule__Var_Decl_Local__LocatedAssignment_3_0 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocatedAssignment_3_0()); 
            // InternalStructuredTextParser.g:3139:2: ( rule__Var_Decl_Local__LocatedAssignment_3_0 )
            // InternalStructuredTextParser.g:3139:3: rule__Var_Decl_Local__LocatedAssignment_3_0
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
    // InternalStructuredTextParser.g:3147:1: rule__Var_Decl_Local__Group_3__1 : rule__Var_Decl_Local__Group_3__1__Impl ;
    public final void rule__Var_Decl_Local__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3151:1: ( rule__Var_Decl_Local__Group_3__1__Impl )
            // InternalStructuredTextParser.g:3152:2: rule__Var_Decl_Local__Group_3__1__Impl
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
    // InternalStructuredTextParser.g:3158:1: rule__Var_Decl_Local__Group_3__1__Impl : ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) ) ;
    public final void rule__Var_Decl_Local__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3162:1: ( ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) ) )
            // InternalStructuredTextParser.g:3163:1: ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) )
            {
            // InternalStructuredTextParser.g:3163:1: ( ( rule__Var_Decl_Local__LocationAssignment_3_1 ) )
            // InternalStructuredTextParser.g:3164:2: ( rule__Var_Decl_Local__LocationAssignment_3_1 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocationAssignment_3_1()); 
            // InternalStructuredTextParser.g:3165:2: ( rule__Var_Decl_Local__LocationAssignment_3_1 )
            // InternalStructuredTextParser.g:3165:3: rule__Var_Decl_Local__LocationAssignment_3_1
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
    // InternalStructuredTextParser.g:3174:1: rule__Var_Decl_Local__Group_5__0 : rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1 ;
    public final void rule__Var_Decl_Local__Group_5__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3178:1: ( rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1 )
            // InternalStructuredTextParser.g:3179:2: rule__Var_Decl_Local__Group_5__0__Impl rule__Var_Decl_Local__Group_5__1
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
    // InternalStructuredTextParser.g:3186:1: rule__Var_Decl_Local__Group_5__0__Impl : ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) ) ;
    public final void rule__Var_Decl_Local__Group_5__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3190:1: ( ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) ) )
            // InternalStructuredTextParser.g:3191:1: ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) )
            {
            // InternalStructuredTextParser.g:3191:1: ( ( rule__Var_Decl_Local__ArrayAssignment_5_0 ) )
            // InternalStructuredTextParser.g:3192:2: ( rule__Var_Decl_Local__ArrayAssignment_5_0 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayAssignment_5_0()); 
            // InternalStructuredTextParser.g:3193:2: ( rule__Var_Decl_Local__ArrayAssignment_5_0 )
            // InternalStructuredTextParser.g:3193:3: rule__Var_Decl_Local__ArrayAssignment_5_0
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
    // InternalStructuredTextParser.g:3201:1: rule__Var_Decl_Local__Group_5__1 : rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2 ;
    public final void rule__Var_Decl_Local__Group_5__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3205:1: ( rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2 )
            // InternalStructuredTextParser.g:3206:2: rule__Var_Decl_Local__Group_5__1__Impl rule__Var_Decl_Local__Group_5__2
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
    // InternalStructuredTextParser.g:3213:1: rule__Var_Decl_Local__Group_5__1__Impl : ( LeftSquareBracket ) ;
    public final void rule__Var_Decl_Local__Group_5__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3217:1: ( ( LeftSquareBracket ) )
            // InternalStructuredTextParser.g:3218:1: ( LeftSquareBracket )
            {
            // InternalStructuredTextParser.g:3218:1: ( LeftSquareBracket )
            // InternalStructuredTextParser.g:3219:2: LeftSquareBracket
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
    // InternalStructuredTextParser.g:3228:1: rule__Var_Decl_Local__Group_5__2 : rule__Var_Decl_Local__Group_5__2__Impl rule__Var_Decl_Local__Group_5__3 ;
    public final void rule__Var_Decl_Local__Group_5__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3232:1: ( rule__Var_Decl_Local__Group_5__2__Impl rule__Var_Decl_Local__Group_5__3 )
            // InternalStructuredTextParser.g:3233:2: rule__Var_Decl_Local__Group_5__2__Impl rule__Var_Decl_Local__Group_5__3
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
    // InternalStructuredTextParser.g:3240:1: rule__Var_Decl_Local__Group_5__2__Impl : ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) ) ;
    public final void rule__Var_Decl_Local__Group_5__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3244:1: ( ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) ) )
            // InternalStructuredTextParser.g:3245:1: ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) )
            {
            // InternalStructuredTextParser.g:3245:1: ( ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 ) )
            // InternalStructuredTextParser.g:3246:2: ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayStartAssignment_5_2()); 
            // InternalStructuredTextParser.g:3247:2: ( rule__Var_Decl_Local__ArrayStartAssignment_5_2 )
            // InternalStructuredTextParser.g:3247:3: rule__Var_Decl_Local__ArrayStartAssignment_5_2
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
    // InternalStructuredTextParser.g:3255:1: rule__Var_Decl_Local__Group_5__3 : rule__Var_Decl_Local__Group_5__3__Impl rule__Var_Decl_Local__Group_5__4 ;
    public final void rule__Var_Decl_Local__Group_5__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3259:1: ( rule__Var_Decl_Local__Group_5__3__Impl rule__Var_Decl_Local__Group_5__4 )
            // InternalStructuredTextParser.g:3260:2: rule__Var_Decl_Local__Group_5__3__Impl rule__Var_Decl_Local__Group_5__4
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
    // InternalStructuredTextParser.g:3267:1: rule__Var_Decl_Local__Group_5__3__Impl : ( FullStopFullStop ) ;
    public final void rule__Var_Decl_Local__Group_5__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3271:1: ( ( FullStopFullStop ) )
            // InternalStructuredTextParser.g:3272:1: ( FullStopFullStop )
            {
            // InternalStructuredTextParser.g:3272:1: ( FullStopFullStop )
            // InternalStructuredTextParser.g:3273:2: FullStopFullStop
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
    // InternalStructuredTextParser.g:3282:1: rule__Var_Decl_Local__Group_5__4 : rule__Var_Decl_Local__Group_5__4__Impl rule__Var_Decl_Local__Group_5__5 ;
    public final void rule__Var_Decl_Local__Group_5__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3286:1: ( rule__Var_Decl_Local__Group_5__4__Impl rule__Var_Decl_Local__Group_5__5 )
            // InternalStructuredTextParser.g:3287:2: rule__Var_Decl_Local__Group_5__4__Impl rule__Var_Decl_Local__Group_5__5
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
    // InternalStructuredTextParser.g:3294:1: rule__Var_Decl_Local__Group_5__4__Impl : ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) ) ;
    public final void rule__Var_Decl_Local__Group_5__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3298:1: ( ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) ) )
            // InternalStructuredTextParser.g:3299:1: ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) )
            {
            // InternalStructuredTextParser.g:3299:1: ( ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 ) )
            // InternalStructuredTextParser.g:3300:2: ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayStopAssignment_5_4()); 
            // InternalStructuredTextParser.g:3301:2: ( rule__Var_Decl_Local__ArrayStopAssignment_5_4 )
            // InternalStructuredTextParser.g:3301:3: rule__Var_Decl_Local__ArrayStopAssignment_5_4
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
    // InternalStructuredTextParser.g:3309:1: rule__Var_Decl_Local__Group_5__5 : rule__Var_Decl_Local__Group_5__5__Impl rule__Var_Decl_Local__Group_5__6 ;
    public final void rule__Var_Decl_Local__Group_5__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3313:1: ( rule__Var_Decl_Local__Group_5__5__Impl rule__Var_Decl_Local__Group_5__6 )
            // InternalStructuredTextParser.g:3314:2: rule__Var_Decl_Local__Group_5__5__Impl rule__Var_Decl_Local__Group_5__6
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
    // InternalStructuredTextParser.g:3321:1: rule__Var_Decl_Local__Group_5__5__Impl : ( RightSquareBracket ) ;
    public final void rule__Var_Decl_Local__Group_5__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3325:1: ( ( RightSquareBracket ) )
            // InternalStructuredTextParser.g:3326:1: ( RightSquareBracket )
            {
            // InternalStructuredTextParser.g:3326:1: ( RightSquareBracket )
            // InternalStructuredTextParser.g:3327:2: RightSquareBracket
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
    // InternalStructuredTextParser.g:3336:1: rule__Var_Decl_Local__Group_5__6 : rule__Var_Decl_Local__Group_5__6__Impl ;
    public final void rule__Var_Decl_Local__Group_5__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3340:1: ( rule__Var_Decl_Local__Group_5__6__Impl )
            // InternalStructuredTextParser.g:3341:2: rule__Var_Decl_Local__Group_5__6__Impl
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
    // InternalStructuredTextParser.g:3347:1: rule__Var_Decl_Local__Group_5__6__Impl : ( OF ) ;
    public final void rule__Var_Decl_Local__Group_5__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3351:1: ( ( OF ) )
            // InternalStructuredTextParser.g:3352:1: ( OF )
            {
            // InternalStructuredTextParser.g:3352:1: ( OF )
            // InternalStructuredTextParser.g:3353:2: OF
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
    // InternalStructuredTextParser.g:3363:1: rule__Var_Decl_Local__Group_7__0 : rule__Var_Decl_Local__Group_7__0__Impl rule__Var_Decl_Local__Group_7__1 ;
    public final void rule__Var_Decl_Local__Group_7__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3367:1: ( rule__Var_Decl_Local__Group_7__0__Impl rule__Var_Decl_Local__Group_7__1 )
            // InternalStructuredTextParser.g:3368:2: rule__Var_Decl_Local__Group_7__0__Impl rule__Var_Decl_Local__Group_7__1
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
    // InternalStructuredTextParser.g:3375:1: rule__Var_Decl_Local__Group_7__0__Impl : ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) ) ;
    public final void rule__Var_Decl_Local__Group_7__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3379:1: ( ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) ) )
            // InternalStructuredTextParser.g:3380:1: ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) )
            {
            // InternalStructuredTextParser.g:3380:1: ( ( rule__Var_Decl_Local__InitalizedAssignment_7_0 ) )
            // InternalStructuredTextParser.g:3381:2: ( rule__Var_Decl_Local__InitalizedAssignment_7_0 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitalizedAssignment_7_0()); 
            // InternalStructuredTextParser.g:3382:2: ( rule__Var_Decl_Local__InitalizedAssignment_7_0 )
            // InternalStructuredTextParser.g:3382:3: rule__Var_Decl_Local__InitalizedAssignment_7_0
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
    // InternalStructuredTextParser.g:3390:1: rule__Var_Decl_Local__Group_7__1 : rule__Var_Decl_Local__Group_7__1__Impl ;
    public final void rule__Var_Decl_Local__Group_7__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3394:1: ( rule__Var_Decl_Local__Group_7__1__Impl )
            // InternalStructuredTextParser.g:3395:2: rule__Var_Decl_Local__Group_7__1__Impl
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
    // InternalStructuredTextParser.g:3401:1: rule__Var_Decl_Local__Group_7__1__Impl : ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) ) ;
    public final void rule__Var_Decl_Local__Group_7__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3405:1: ( ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) ) )
            // InternalStructuredTextParser.g:3406:1: ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) )
            {
            // InternalStructuredTextParser.g:3406:1: ( ( rule__Var_Decl_Local__InitialValueAssignment_7_1 ) )
            // InternalStructuredTextParser.g:3407:2: ( rule__Var_Decl_Local__InitialValueAssignment_7_1 )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitialValueAssignment_7_1()); 
            // InternalStructuredTextParser.g:3408:2: ( rule__Var_Decl_Local__InitialValueAssignment_7_1 )
            // InternalStructuredTextParser.g:3408:3: rule__Var_Decl_Local__InitialValueAssignment_7_1
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
    // InternalStructuredTextParser.g:3417:1: rule__Stmt_List__Group__0 : rule__Stmt_List__Group__0__Impl rule__Stmt_List__Group__1 ;
    public final void rule__Stmt_List__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3421:1: ( rule__Stmt_List__Group__0__Impl rule__Stmt_List__Group__1 )
            // InternalStructuredTextParser.g:3422:2: rule__Stmt_List__Group__0__Impl rule__Stmt_List__Group__1
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
    // InternalStructuredTextParser.g:3429:1: rule__Stmt_List__Group__0__Impl : ( () ) ;
    public final void rule__Stmt_List__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3433:1: ( ( () ) )
            // InternalStructuredTextParser.g:3434:1: ( () )
            {
            // InternalStructuredTextParser.g:3434:1: ( () )
            // InternalStructuredTextParser.g:3435:2: ()
            {
             before(grammarAccess.getStmt_ListAccess().getStatementListAction_0()); 
            // InternalStructuredTextParser.g:3436:2: ()
            // InternalStructuredTextParser.g:3436:3: 
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
    // InternalStructuredTextParser.g:3444:1: rule__Stmt_List__Group__1 : rule__Stmt_List__Group__1__Impl ;
    public final void rule__Stmt_List__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3448:1: ( rule__Stmt_List__Group__1__Impl )
            // InternalStructuredTextParser.g:3449:2: rule__Stmt_List__Group__1__Impl
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
    // InternalStructuredTextParser.g:3455:1: rule__Stmt_List__Group__1__Impl : ( ( rule__Stmt_List__Group_1__0 )* ) ;
    public final void rule__Stmt_List__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3459:1: ( ( ( rule__Stmt_List__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:3460:1: ( ( rule__Stmt_List__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:3460:1: ( ( rule__Stmt_List__Group_1__0 )* )
            // InternalStructuredTextParser.g:3461:2: ( rule__Stmt_List__Group_1__0 )*
            {
             before(grammarAccess.getStmt_ListAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:3462:2: ( rule__Stmt_List__Group_1__0 )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==CONTINUE||(LA36_0>=REPEAT && LA36_0<=RETURN)||LA36_0==SUPER||LA36_0==WHILE||LA36_0==CASE||LA36_0==EXIT||LA36_0==TIME||LA36_0==FOR||(LA36_0>=DT && LA36_0<=LT)||LA36_0==Semicolon||LA36_0==T||LA36_0==RULE_ID) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // InternalStructuredTextParser.g:3462:3: rule__Stmt_List__Group_1__0
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
    // InternalStructuredTextParser.g:3471:1: rule__Stmt_List__Group_1__0 : rule__Stmt_List__Group_1__0__Impl rule__Stmt_List__Group_1__1 ;
    public final void rule__Stmt_List__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3475:1: ( rule__Stmt_List__Group_1__0__Impl rule__Stmt_List__Group_1__1 )
            // InternalStructuredTextParser.g:3476:2: rule__Stmt_List__Group_1__0__Impl rule__Stmt_List__Group_1__1
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
    // InternalStructuredTextParser.g:3483:1: rule__Stmt_List__Group_1__0__Impl : ( ( rule__Stmt_List__StatementsAssignment_1_0 )? ) ;
    public final void rule__Stmt_List__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3487:1: ( ( ( rule__Stmt_List__StatementsAssignment_1_0 )? ) )
            // InternalStructuredTextParser.g:3488:1: ( ( rule__Stmt_List__StatementsAssignment_1_0 )? )
            {
            // InternalStructuredTextParser.g:3488:1: ( ( rule__Stmt_List__StatementsAssignment_1_0 )? )
            // InternalStructuredTextParser.g:3489:2: ( rule__Stmt_List__StatementsAssignment_1_0 )?
            {
             before(grammarAccess.getStmt_ListAccess().getStatementsAssignment_1_0()); 
            // InternalStructuredTextParser.g:3490:2: ( rule__Stmt_List__StatementsAssignment_1_0 )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==CONTINUE||(LA37_0>=REPEAT && LA37_0<=RETURN)||LA37_0==SUPER||LA37_0==WHILE||LA37_0==CASE||LA37_0==EXIT||LA37_0==TIME||LA37_0==FOR||(LA37_0>=DT && LA37_0<=LT)||LA37_0==T||LA37_0==RULE_ID) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalStructuredTextParser.g:3490:3: rule__Stmt_List__StatementsAssignment_1_0
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
    // InternalStructuredTextParser.g:3498:1: rule__Stmt_List__Group_1__1 : rule__Stmt_List__Group_1__1__Impl ;
    public final void rule__Stmt_List__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3502:1: ( rule__Stmt_List__Group_1__1__Impl )
            // InternalStructuredTextParser.g:3503:2: rule__Stmt_List__Group_1__1__Impl
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
    // InternalStructuredTextParser.g:3509:1: rule__Stmt_List__Group_1__1__Impl : ( Semicolon ) ;
    public final void rule__Stmt_List__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3513:1: ( ( Semicolon ) )
            // InternalStructuredTextParser.g:3514:1: ( Semicolon )
            {
            // InternalStructuredTextParser.g:3514:1: ( Semicolon )
            // InternalStructuredTextParser.g:3515:2: Semicolon
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
    // InternalStructuredTextParser.g:3525:1: rule__Assign_Stmt__Group__0 : rule__Assign_Stmt__Group__0__Impl rule__Assign_Stmt__Group__1 ;
    public final void rule__Assign_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3529:1: ( rule__Assign_Stmt__Group__0__Impl rule__Assign_Stmt__Group__1 )
            // InternalStructuredTextParser.g:3530:2: rule__Assign_Stmt__Group__0__Impl rule__Assign_Stmt__Group__1
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
    // InternalStructuredTextParser.g:3537:1: rule__Assign_Stmt__Group__0__Impl : ( ( rule__Assign_Stmt__VariableAssignment_0 ) ) ;
    public final void rule__Assign_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3541:1: ( ( ( rule__Assign_Stmt__VariableAssignment_0 ) ) )
            // InternalStructuredTextParser.g:3542:1: ( ( rule__Assign_Stmt__VariableAssignment_0 ) )
            {
            // InternalStructuredTextParser.g:3542:1: ( ( rule__Assign_Stmt__VariableAssignment_0 ) )
            // InternalStructuredTextParser.g:3543:2: ( rule__Assign_Stmt__VariableAssignment_0 )
            {
             before(grammarAccess.getAssign_StmtAccess().getVariableAssignment_0()); 
            // InternalStructuredTextParser.g:3544:2: ( rule__Assign_Stmt__VariableAssignment_0 )
            // InternalStructuredTextParser.g:3544:3: rule__Assign_Stmt__VariableAssignment_0
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
    // InternalStructuredTextParser.g:3552:1: rule__Assign_Stmt__Group__1 : rule__Assign_Stmt__Group__1__Impl rule__Assign_Stmt__Group__2 ;
    public final void rule__Assign_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3556:1: ( rule__Assign_Stmt__Group__1__Impl rule__Assign_Stmt__Group__2 )
            // InternalStructuredTextParser.g:3557:2: rule__Assign_Stmt__Group__1__Impl rule__Assign_Stmt__Group__2
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
    // InternalStructuredTextParser.g:3564:1: rule__Assign_Stmt__Group__1__Impl : ( ColonEqualsSign ) ;
    public final void rule__Assign_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3568:1: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:3569:1: ( ColonEqualsSign )
            {
            // InternalStructuredTextParser.g:3569:1: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:3570:2: ColonEqualsSign
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
    // InternalStructuredTextParser.g:3579:1: rule__Assign_Stmt__Group__2 : rule__Assign_Stmt__Group__2__Impl ;
    public final void rule__Assign_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3583:1: ( rule__Assign_Stmt__Group__2__Impl )
            // InternalStructuredTextParser.g:3584:2: rule__Assign_Stmt__Group__2__Impl
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
    // InternalStructuredTextParser.g:3590:1: rule__Assign_Stmt__Group__2__Impl : ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) ) ;
    public final void rule__Assign_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3594:1: ( ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) ) )
            // InternalStructuredTextParser.g:3595:1: ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) )
            {
            // InternalStructuredTextParser.g:3595:1: ( ( rule__Assign_Stmt__ExpressionAssignment_2 ) )
            // InternalStructuredTextParser.g:3596:2: ( rule__Assign_Stmt__ExpressionAssignment_2 )
            {
             before(grammarAccess.getAssign_StmtAccess().getExpressionAssignment_2()); 
            // InternalStructuredTextParser.g:3597:2: ( rule__Assign_Stmt__ExpressionAssignment_2 )
            // InternalStructuredTextParser.g:3597:3: rule__Assign_Stmt__ExpressionAssignment_2
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


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__0"
    // InternalStructuredTextParser.g:3606:1: rule__Subprog_Ctrl_Stmt__Group_2__0 : rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3610:1: ( rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1 )
            // InternalStructuredTextParser.g:3611:2: rule__Subprog_Ctrl_Stmt__Group_2__0__Impl rule__Subprog_Ctrl_Stmt__Group_2__1
            {
            pushFollow(FOLLOW_21);
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
    // InternalStructuredTextParser.g:3618:1: rule__Subprog_Ctrl_Stmt__Group_2__0__Impl : ( () ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3622:1: ( ( () ) )
            // InternalStructuredTextParser.g:3623:1: ( () )
            {
            // InternalStructuredTextParser.g:3623:1: ( () )
            // InternalStructuredTextParser.g:3624:2: ()
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getSuperStatementAction_2_0()); 
            // InternalStructuredTextParser.g:3625:2: ()
            // InternalStructuredTextParser.g:3625:3: 
            {
            }

             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getSuperStatementAction_2_0()); 

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
    // InternalStructuredTextParser.g:3633:1: rule__Subprog_Ctrl_Stmt__Group_2__1 : rule__Subprog_Ctrl_Stmt__Group_2__1__Impl rule__Subprog_Ctrl_Stmt__Group_2__2 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3637:1: ( rule__Subprog_Ctrl_Stmt__Group_2__1__Impl rule__Subprog_Ctrl_Stmt__Group_2__2 )
            // InternalStructuredTextParser.g:3638:2: rule__Subprog_Ctrl_Stmt__Group_2__1__Impl rule__Subprog_Ctrl_Stmt__Group_2__2
            {
            pushFollow(FOLLOW_22);
            rule__Subprog_Ctrl_Stmt__Group_2__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_2__2();

            state._fsp--;


            }

        }
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
    // InternalStructuredTextParser.g:3645:1: rule__Subprog_Ctrl_Stmt__Group_2__1__Impl : ( SUPER ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3649:1: ( ( SUPER ) )
            // InternalStructuredTextParser.g:3650:1: ( SUPER )
            {
            // InternalStructuredTextParser.g:3650:1: ( SUPER )
            // InternalStructuredTextParser.g:3651:2: SUPER
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getSUPERKeyword_2_1()); 
            match(input,SUPER,FOLLOW_2); 
             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getSUPERKeyword_2_1()); 

            }


            }

        }
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


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__2"
    // InternalStructuredTextParser.g:3660:1: rule__Subprog_Ctrl_Stmt__Group_2__2 : rule__Subprog_Ctrl_Stmt__Group_2__2__Impl rule__Subprog_Ctrl_Stmt__Group_2__3 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3664:1: ( rule__Subprog_Ctrl_Stmt__Group_2__2__Impl rule__Subprog_Ctrl_Stmt__Group_2__3 )
            // InternalStructuredTextParser.g:3665:2: rule__Subprog_Ctrl_Stmt__Group_2__2__Impl rule__Subprog_Ctrl_Stmt__Group_2__3
            {
            pushFollow(FOLLOW_23);
            rule__Subprog_Ctrl_Stmt__Group_2__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_2__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__2"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__2__Impl"
    // InternalStructuredTextParser.g:3672:1: rule__Subprog_Ctrl_Stmt__Group_2__2__Impl : ( LeftParenthesis ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3676:1: ( ( LeftParenthesis ) )
            // InternalStructuredTextParser.g:3677:1: ( LeftParenthesis )
            {
            // InternalStructuredTextParser.g:3677:1: ( LeftParenthesis )
            // InternalStructuredTextParser.g:3678:2: LeftParenthesis
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getLeftParenthesisKeyword_2_2()); 
            match(input,LeftParenthesis,FOLLOW_2); 
             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getLeftParenthesisKeyword_2_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__2__Impl"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__3"
    // InternalStructuredTextParser.g:3687:1: rule__Subprog_Ctrl_Stmt__Group_2__3 : rule__Subprog_Ctrl_Stmt__Group_2__3__Impl ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3691:1: ( rule__Subprog_Ctrl_Stmt__Group_2__3__Impl )
            // InternalStructuredTextParser.g:3692:2: rule__Subprog_Ctrl_Stmt__Group_2__3__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_2__3__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__3"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_2__3__Impl"
    // InternalStructuredTextParser.g:3698:1: rule__Subprog_Ctrl_Stmt__Group_2__3__Impl : ( RightParenthesis ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_2__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3702:1: ( ( RightParenthesis ) )
            // InternalStructuredTextParser.g:3703:1: ( RightParenthesis )
            {
            // InternalStructuredTextParser.g:3703:1: ( RightParenthesis )
            // InternalStructuredTextParser.g:3704:2: RightParenthesis
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getRightParenthesisKeyword_2_3()); 
            match(input,RightParenthesis,FOLLOW_2); 
             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getRightParenthesisKeyword_2_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_2__3__Impl"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_3__0"
    // InternalStructuredTextParser.g:3714:1: rule__Subprog_Ctrl_Stmt__Group_3__0 : rule__Subprog_Ctrl_Stmt__Group_3__0__Impl rule__Subprog_Ctrl_Stmt__Group_3__1 ;
    public final void rule__Subprog_Ctrl_Stmt__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3718:1: ( rule__Subprog_Ctrl_Stmt__Group_3__0__Impl rule__Subprog_Ctrl_Stmt__Group_3__1 )
            // InternalStructuredTextParser.g:3719:2: rule__Subprog_Ctrl_Stmt__Group_3__0__Impl rule__Subprog_Ctrl_Stmt__Group_3__1
            {
            pushFollow(FOLLOW_24);
            rule__Subprog_Ctrl_Stmt__Group_3__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_3__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_3__0"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_3__0__Impl"
    // InternalStructuredTextParser.g:3726:1: rule__Subprog_Ctrl_Stmt__Group_3__0__Impl : ( () ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3730:1: ( ( () ) )
            // InternalStructuredTextParser.g:3731:1: ( () )
            {
            // InternalStructuredTextParser.g:3731:1: ( () )
            // InternalStructuredTextParser.g:3732:2: ()
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getReturnStatementAction_3_0()); 
            // InternalStructuredTextParser.g:3733:2: ()
            // InternalStructuredTextParser.g:3733:3: 
            {
            }

             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getReturnStatementAction_3_0()); 

            }


            }

        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_3__0__Impl"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_3__1"
    // InternalStructuredTextParser.g:3741:1: rule__Subprog_Ctrl_Stmt__Group_3__1 : rule__Subprog_Ctrl_Stmt__Group_3__1__Impl ;
    public final void rule__Subprog_Ctrl_Stmt__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3745:1: ( rule__Subprog_Ctrl_Stmt__Group_3__1__Impl )
            // InternalStructuredTextParser.g:3746:2: rule__Subprog_Ctrl_Stmt__Group_3__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__Subprog_Ctrl_Stmt__Group_3__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_3__1"


    // $ANTLR start "rule__Subprog_Ctrl_Stmt__Group_3__1__Impl"
    // InternalStructuredTextParser.g:3752:1: rule__Subprog_Ctrl_Stmt__Group_3__1__Impl : ( RETURN ) ;
    public final void rule__Subprog_Ctrl_Stmt__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3756:1: ( ( RETURN ) )
            // InternalStructuredTextParser.g:3757:1: ( RETURN )
            {
            // InternalStructuredTextParser.g:3757:1: ( RETURN )
            // InternalStructuredTextParser.g:3758:2: RETURN
            {
             before(grammarAccess.getSubprog_Ctrl_StmtAccess().getRETURNKeyword_3_1()); 
            match(input,RETURN,FOLLOW_2); 
             after(grammarAccess.getSubprog_Ctrl_StmtAccess().getRETURNKeyword_3_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__Subprog_Ctrl_Stmt__Group_3__1__Impl"


    // $ANTLR start "rule__FB_Call__Group__0"
    // InternalStructuredTextParser.g:3768:1: rule__FB_Call__Group__0 : rule__FB_Call__Group__0__Impl rule__FB_Call__Group__1 ;
    public final void rule__FB_Call__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3772:1: ( rule__FB_Call__Group__0__Impl rule__FB_Call__Group__1 )
            // InternalStructuredTextParser.g:3773:2: rule__FB_Call__Group__0__Impl rule__FB_Call__Group__1
            {
            pushFollow(FOLLOW_25);
            rule__FB_Call__Group__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FB_Call__Group__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__0"


    // $ANTLR start "rule__FB_Call__Group__0__Impl"
    // InternalStructuredTextParser.g:3780:1: rule__FB_Call__Group__0__Impl : ( ( rule__FB_Call__FbAssignment_0 ) ) ;
    public final void rule__FB_Call__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3784:1: ( ( ( rule__FB_Call__FbAssignment_0 ) ) )
            // InternalStructuredTextParser.g:3785:1: ( ( rule__FB_Call__FbAssignment_0 ) )
            {
            // InternalStructuredTextParser.g:3785:1: ( ( rule__FB_Call__FbAssignment_0 ) )
            // InternalStructuredTextParser.g:3786:2: ( rule__FB_Call__FbAssignment_0 )
            {
             before(grammarAccess.getFB_CallAccess().getFbAssignment_0()); 
            // InternalStructuredTextParser.g:3787:2: ( rule__FB_Call__FbAssignment_0 )
            // InternalStructuredTextParser.g:3787:3: rule__FB_Call__FbAssignment_0
            {
            pushFollow(FOLLOW_2);
            rule__FB_Call__FbAssignment_0();

            state._fsp--;


            }

             after(grammarAccess.getFB_CallAccess().getFbAssignment_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__0__Impl"


    // $ANTLR start "rule__FB_Call__Group__1"
    // InternalStructuredTextParser.g:3795:1: rule__FB_Call__Group__1 : rule__FB_Call__Group__1__Impl rule__FB_Call__Group__2 ;
    public final void rule__FB_Call__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3799:1: ( rule__FB_Call__Group__1__Impl rule__FB_Call__Group__2 )
            // InternalStructuredTextParser.g:3800:2: rule__FB_Call__Group__1__Impl rule__FB_Call__Group__2
            {
            pushFollow(FOLLOW_26);
            rule__FB_Call__Group__1__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FB_Call__Group__2();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__1"


    // $ANTLR start "rule__FB_Call__Group__1__Impl"
    // InternalStructuredTextParser.g:3807:1: rule__FB_Call__Group__1__Impl : ( FullStop ) ;
    public final void rule__FB_Call__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3811:1: ( ( FullStop ) )
            // InternalStructuredTextParser.g:3812:1: ( FullStop )
            {
            // InternalStructuredTextParser.g:3812:1: ( FullStop )
            // InternalStructuredTextParser.g:3813:2: FullStop
            {
             before(grammarAccess.getFB_CallAccess().getFullStopKeyword_1()); 
            match(input,FullStop,FOLLOW_2); 
             after(grammarAccess.getFB_CallAccess().getFullStopKeyword_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__1__Impl"


    // $ANTLR start "rule__FB_Call__Group__2"
    // InternalStructuredTextParser.g:3822:1: rule__FB_Call__Group__2 : rule__FB_Call__Group__2__Impl rule__FB_Call__Group__3 ;
    public final void rule__FB_Call__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3826:1: ( rule__FB_Call__Group__2__Impl rule__FB_Call__Group__3 )
            // InternalStructuredTextParser.g:3827:2: rule__FB_Call__Group__2__Impl rule__FB_Call__Group__3
            {
            pushFollow(FOLLOW_22);
            rule__FB_Call__Group__2__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FB_Call__Group__3();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__2"


    // $ANTLR start "rule__FB_Call__Group__2__Impl"
    // InternalStructuredTextParser.g:3834:1: rule__FB_Call__Group__2__Impl : ( ( rule__FB_Call__EventAssignment_2 ) ) ;
    public final void rule__FB_Call__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3838:1: ( ( ( rule__FB_Call__EventAssignment_2 ) ) )
            // InternalStructuredTextParser.g:3839:1: ( ( rule__FB_Call__EventAssignment_2 ) )
            {
            // InternalStructuredTextParser.g:3839:1: ( ( rule__FB_Call__EventAssignment_2 ) )
            // InternalStructuredTextParser.g:3840:2: ( rule__FB_Call__EventAssignment_2 )
            {
             before(grammarAccess.getFB_CallAccess().getEventAssignment_2()); 
            // InternalStructuredTextParser.g:3841:2: ( rule__FB_Call__EventAssignment_2 )
            // InternalStructuredTextParser.g:3841:3: rule__FB_Call__EventAssignment_2
            {
            pushFollow(FOLLOW_2);
            rule__FB_Call__EventAssignment_2();

            state._fsp--;


            }

             after(grammarAccess.getFB_CallAccess().getEventAssignment_2()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__2__Impl"


    // $ANTLR start "rule__FB_Call__Group__3"
    // InternalStructuredTextParser.g:3849:1: rule__FB_Call__Group__3 : rule__FB_Call__Group__3__Impl rule__FB_Call__Group__4 ;
    public final void rule__FB_Call__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3853:1: ( rule__FB_Call__Group__3__Impl rule__FB_Call__Group__4 )
            // InternalStructuredTextParser.g:3854:2: rule__FB_Call__Group__3__Impl rule__FB_Call__Group__4
            {
            pushFollow(FOLLOW_27);
            rule__FB_Call__Group__3__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FB_Call__Group__4();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__3"


    // $ANTLR start "rule__FB_Call__Group__3__Impl"
    // InternalStructuredTextParser.g:3861:1: rule__FB_Call__Group__3__Impl : ( LeftParenthesis ) ;
    public final void rule__FB_Call__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3865:1: ( ( LeftParenthesis ) )
            // InternalStructuredTextParser.g:3866:1: ( LeftParenthesis )
            {
            // InternalStructuredTextParser.g:3866:1: ( LeftParenthesis )
            // InternalStructuredTextParser.g:3867:2: LeftParenthesis
            {
             before(grammarAccess.getFB_CallAccess().getLeftParenthesisKeyword_3()); 
            match(input,LeftParenthesis,FOLLOW_2); 
             after(grammarAccess.getFB_CallAccess().getLeftParenthesisKeyword_3()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__3__Impl"


    // $ANTLR start "rule__FB_Call__Group__4"
    // InternalStructuredTextParser.g:3876:1: rule__FB_Call__Group__4 : rule__FB_Call__Group__4__Impl rule__FB_Call__Group__5 ;
    public final void rule__FB_Call__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3880:1: ( rule__FB_Call__Group__4__Impl rule__FB_Call__Group__5 )
            // InternalStructuredTextParser.g:3881:2: rule__FB_Call__Group__4__Impl rule__FB_Call__Group__5
            {
            pushFollow(FOLLOW_27);
            rule__FB_Call__Group__4__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FB_Call__Group__5();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__4"


    // $ANTLR start "rule__FB_Call__Group__4__Impl"
    // InternalStructuredTextParser.g:3888:1: rule__FB_Call__Group__4__Impl : ( ( rule__FB_Call__Group_4__0 )? ) ;
    public final void rule__FB_Call__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3892:1: ( ( ( rule__FB_Call__Group_4__0 )? ) )
            // InternalStructuredTextParser.g:3893:1: ( ( rule__FB_Call__Group_4__0 )? )
            {
            // InternalStructuredTextParser.g:3893:1: ( ( rule__FB_Call__Group_4__0 )? )
            // InternalStructuredTextParser.g:3894:2: ( rule__FB_Call__Group_4__0 )?
            {
             before(grammarAccess.getFB_CallAccess().getGroup_4()); 
            // InternalStructuredTextParser.g:3895:2: ( rule__FB_Call__Group_4__0 )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==WSTRING||LA38_0==STRING||LA38_0==FALSE||LA38_0==LREAL||(LA38_0>=UDINT && LA38_0<=ULINT)||(LA38_0>=USINT && LA38_0<=WCHAR)||LA38_0==BOOL||LA38_0==CHAR||LA38_0==DINT||LA38_0==LINT||(LA38_0>=REAL && LA38_0<=SINT)||(LA38_0>=TIME && LA38_0<=UINT)||LA38_0==INT||LA38_0==NOT||LA38_0==DT||LA38_0==LT||LA38_0==LeftParenthesis||LA38_0==PlusSign||LA38_0==HyphenMinus||LA38_0==T||(LA38_0>=RULE_UNSIGNED_INT && LA38_0<=RULE_HEX_INT)||LA38_0==RULE_S_BYTE_CHAR_STR||LA38_0==RULE_D_BYTE_CHAR_STR) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalStructuredTextParser.g:3895:3: rule__FB_Call__Group_4__0
                    {
                    pushFollow(FOLLOW_2);
                    rule__FB_Call__Group_4__0();

                    state._fsp--;


                    }
                    break;

            }

             after(grammarAccess.getFB_CallAccess().getGroup_4()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__4__Impl"


    // $ANTLR start "rule__FB_Call__Group__5"
    // InternalStructuredTextParser.g:3903:1: rule__FB_Call__Group__5 : rule__FB_Call__Group__5__Impl ;
    public final void rule__FB_Call__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3907:1: ( rule__FB_Call__Group__5__Impl )
            // InternalStructuredTextParser.g:3908:2: rule__FB_Call__Group__5__Impl
            {
            pushFollow(FOLLOW_2);
            rule__FB_Call__Group__5__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__5"


    // $ANTLR start "rule__FB_Call__Group__5__Impl"
    // InternalStructuredTextParser.g:3914:1: rule__FB_Call__Group__5__Impl : ( RightParenthesis ) ;
    public final void rule__FB_Call__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3918:1: ( ( RightParenthesis ) )
            // InternalStructuredTextParser.g:3919:1: ( RightParenthesis )
            {
            // InternalStructuredTextParser.g:3919:1: ( RightParenthesis )
            // InternalStructuredTextParser.g:3920:2: RightParenthesis
            {
             before(grammarAccess.getFB_CallAccess().getRightParenthesisKeyword_5()); 
            match(input,RightParenthesis,FOLLOW_2); 
             after(grammarAccess.getFB_CallAccess().getRightParenthesisKeyword_5()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group__5__Impl"


    // $ANTLR start "rule__FB_Call__Group_4__0"
    // InternalStructuredTextParser.g:3930:1: rule__FB_Call__Group_4__0 : rule__FB_Call__Group_4__0__Impl rule__FB_Call__Group_4__1 ;
    public final void rule__FB_Call__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3934:1: ( rule__FB_Call__Group_4__0__Impl rule__FB_Call__Group_4__1 )
            // InternalStructuredTextParser.g:3935:2: rule__FB_Call__Group_4__0__Impl rule__FB_Call__Group_4__1
            {
            pushFollow(FOLLOW_28);
            rule__FB_Call__Group_4__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FB_Call__Group_4__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group_4__0"


    // $ANTLR start "rule__FB_Call__Group_4__0__Impl"
    // InternalStructuredTextParser.g:3942:1: rule__FB_Call__Group_4__0__Impl : ( ( rule__FB_Call__ArgsAssignment_4_0 ) ) ;
    public final void rule__FB_Call__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3946:1: ( ( ( rule__FB_Call__ArgsAssignment_4_0 ) ) )
            // InternalStructuredTextParser.g:3947:1: ( ( rule__FB_Call__ArgsAssignment_4_0 ) )
            {
            // InternalStructuredTextParser.g:3947:1: ( ( rule__FB_Call__ArgsAssignment_4_0 ) )
            // InternalStructuredTextParser.g:3948:2: ( rule__FB_Call__ArgsAssignment_4_0 )
            {
             before(grammarAccess.getFB_CallAccess().getArgsAssignment_4_0()); 
            // InternalStructuredTextParser.g:3949:2: ( rule__FB_Call__ArgsAssignment_4_0 )
            // InternalStructuredTextParser.g:3949:3: rule__FB_Call__ArgsAssignment_4_0
            {
            pushFollow(FOLLOW_2);
            rule__FB_Call__ArgsAssignment_4_0();

            state._fsp--;


            }

             after(grammarAccess.getFB_CallAccess().getArgsAssignment_4_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group_4__0__Impl"


    // $ANTLR start "rule__FB_Call__Group_4__1"
    // InternalStructuredTextParser.g:3957:1: rule__FB_Call__Group_4__1 : rule__FB_Call__Group_4__1__Impl ;
    public final void rule__FB_Call__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3961:1: ( rule__FB_Call__Group_4__1__Impl )
            // InternalStructuredTextParser.g:3962:2: rule__FB_Call__Group_4__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__FB_Call__Group_4__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group_4__1"


    // $ANTLR start "rule__FB_Call__Group_4__1__Impl"
    // InternalStructuredTextParser.g:3968:1: rule__FB_Call__Group_4__1__Impl : ( ( rule__FB_Call__Group_4_1__0 )* ) ;
    public final void rule__FB_Call__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3972:1: ( ( ( rule__FB_Call__Group_4_1__0 )* ) )
            // InternalStructuredTextParser.g:3973:1: ( ( rule__FB_Call__Group_4_1__0 )* )
            {
            // InternalStructuredTextParser.g:3973:1: ( ( rule__FB_Call__Group_4_1__0 )* )
            // InternalStructuredTextParser.g:3974:2: ( rule__FB_Call__Group_4_1__0 )*
            {
             before(grammarAccess.getFB_CallAccess().getGroup_4_1()); 
            // InternalStructuredTextParser.g:3975:2: ( rule__FB_Call__Group_4_1__0 )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==Comma) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // InternalStructuredTextParser.g:3975:3: rule__FB_Call__Group_4_1__0
            	    {
            	    pushFollow(FOLLOW_29);
            	    rule__FB_Call__Group_4_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop39;
                }
            } while (true);

             after(grammarAccess.getFB_CallAccess().getGroup_4_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group_4__1__Impl"


    // $ANTLR start "rule__FB_Call__Group_4_1__0"
    // InternalStructuredTextParser.g:3984:1: rule__FB_Call__Group_4_1__0 : rule__FB_Call__Group_4_1__0__Impl rule__FB_Call__Group_4_1__1 ;
    public final void rule__FB_Call__Group_4_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:3988:1: ( rule__FB_Call__Group_4_1__0__Impl rule__FB_Call__Group_4_1__1 )
            // InternalStructuredTextParser.g:3989:2: rule__FB_Call__Group_4_1__0__Impl rule__FB_Call__Group_4_1__1
            {
            pushFollow(FOLLOW_20);
            rule__FB_Call__Group_4_1__0__Impl();

            state._fsp--;

            pushFollow(FOLLOW_2);
            rule__FB_Call__Group_4_1__1();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group_4_1__0"


    // $ANTLR start "rule__FB_Call__Group_4_1__0__Impl"
    // InternalStructuredTextParser.g:3996:1: rule__FB_Call__Group_4_1__0__Impl : ( Comma ) ;
    public final void rule__FB_Call__Group_4_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4000:1: ( ( Comma ) )
            // InternalStructuredTextParser.g:4001:1: ( Comma )
            {
            // InternalStructuredTextParser.g:4001:1: ( Comma )
            // InternalStructuredTextParser.g:4002:2: Comma
            {
             before(grammarAccess.getFB_CallAccess().getCommaKeyword_4_1_0()); 
            match(input,Comma,FOLLOW_2); 
             after(grammarAccess.getFB_CallAccess().getCommaKeyword_4_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group_4_1__0__Impl"


    // $ANTLR start "rule__FB_Call__Group_4_1__1"
    // InternalStructuredTextParser.g:4011:1: rule__FB_Call__Group_4_1__1 : rule__FB_Call__Group_4_1__1__Impl ;
    public final void rule__FB_Call__Group_4_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4015:1: ( rule__FB_Call__Group_4_1__1__Impl )
            // InternalStructuredTextParser.g:4016:2: rule__FB_Call__Group_4_1__1__Impl
            {
            pushFollow(FOLLOW_2);
            rule__FB_Call__Group_4_1__1__Impl();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group_4_1__1"


    // $ANTLR start "rule__FB_Call__Group_4_1__1__Impl"
    // InternalStructuredTextParser.g:4022:1: rule__FB_Call__Group_4_1__1__Impl : ( ( rule__FB_Call__ArgsAssignment_4_1_1 ) ) ;
    public final void rule__FB_Call__Group_4_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4026:1: ( ( ( rule__FB_Call__ArgsAssignment_4_1_1 ) ) )
            // InternalStructuredTextParser.g:4027:1: ( ( rule__FB_Call__ArgsAssignment_4_1_1 ) )
            {
            // InternalStructuredTextParser.g:4027:1: ( ( rule__FB_Call__ArgsAssignment_4_1_1 ) )
            // InternalStructuredTextParser.g:4028:2: ( rule__FB_Call__ArgsAssignment_4_1_1 )
            {
             before(grammarAccess.getFB_CallAccess().getArgsAssignment_4_1_1()); 
            // InternalStructuredTextParser.g:4029:2: ( rule__FB_Call__ArgsAssignment_4_1_1 )
            // InternalStructuredTextParser.g:4029:3: rule__FB_Call__ArgsAssignment_4_1_1
            {
            pushFollow(FOLLOW_2);
            rule__FB_Call__ArgsAssignment_4_1_1();

            state._fsp--;


            }

             after(grammarAccess.getFB_CallAccess().getArgsAssignment_4_1_1()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__Group_4_1__1__Impl"


    // $ANTLR start "rule__IF_Stmt__Group__0"
    // InternalStructuredTextParser.g:4038:1: rule__IF_Stmt__Group__0 : rule__IF_Stmt__Group__0__Impl rule__IF_Stmt__Group__1 ;
    public final void rule__IF_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4042:1: ( rule__IF_Stmt__Group__0__Impl rule__IF_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4043:2: rule__IF_Stmt__Group__0__Impl rule__IF_Stmt__Group__1
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
    // InternalStructuredTextParser.g:4050:1: rule__IF_Stmt__Group__0__Impl : ( IF ) ;
    public final void rule__IF_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4054:1: ( ( IF ) )
            // InternalStructuredTextParser.g:4055:1: ( IF )
            {
            // InternalStructuredTextParser.g:4055:1: ( IF )
            // InternalStructuredTextParser.g:4056:2: IF
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
    // InternalStructuredTextParser.g:4065:1: rule__IF_Stmt__Group__1 : rule__IF_Stmt__Group__1__Impl rule__IF_Stmt__Group__2 ;
    public final void rule__IF_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4069:1: ( rule__IF_Stmt__Group__1__Impl rule__IF_Stmt__Group__2 )
            // InternalStructuredTextParser.g:4070:2: rule__IF_Stmt__Group__1__Impl rule__IF_Stmt__Group__2
            {
            pushFollow(FOLLOW_30);
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
    // InternalStructuredTextParser.g:4077:1: rule__IF_Stmt__Group__1__Impl : ( ( rule__IF_Stmt__ExpressionAssignment_1 ) ) ;
    public final void rule__IF_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4081:1: ( ( ( rule__IF_Stmt__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4082:1: ( ( rule__IF_Stmt__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4082:1: ( ( rule__IF_Stmt__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:4083:2: ( rule__IF_Stmt__ExpressionAssignment_1 )
            {
             before(grammarAccess.getIF_StmtAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:4084:2: ( rule__IF_Stmt__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:4084:3: rule__IF_Stmt__ExpressionAssignment_1
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
    // InternalStructuredTextParser.g:4092:1: rule__IF_Stmt__Group__2 : rule__IF_Stmt__Group__2__Impl rule__IF_Stmt__Group__3 ;
    public final void rule__IF_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4096:1: ( rule__IF_Stmt__Group__2__Impl rule__IF_Stmt__Group__3 )
            // InternalStructuredTextParser.g:4097:2: rule__IF_Stmt__Group__2__Impl rule__IF_Stmt__Group__3
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
    // InternalStructuredTextParser.g:4104:1: rule__IF_Stmt__Group__2__Impl : ( THEN ) ;
    public final void rule__IF_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4108:1: ( ( THEN ) )
            // InternalStructuredTextParser.g:4109:1: ( THEN )
            {
            // InternalStructuredTextParser.g:4109:1: ( THEN )
            // InternalStructuredTextParser.g:4110:2: THEN
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
    // InternalStructuredTextParser.g:4119:1: rule__IF_Stmt__Group__3 : rule__IF_Stmt__Group__3__Impl rule__IF_Stmt__Group__4 ;
    public final void rule__IF_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4123:1: ( rule__IF_Stmt__Group__3__Impl rule__IF_Stmt__Group__4 )
            // InternalStructuredTextParser.g:4124:2: rule__IF_Stmt__Group__3__Impl rule__IF_Stmt__Group__4
            {
            pushFollow(FOLLOW_31);
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
    // InternalStructuredTextParser.g:4131:1: rule__IF_Stmt__Group__3__Impl : ( ( rule__IF_Stmt__StatmentsAssignment_3 ) ) ;
    public final void rule__IF_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4135:1: ( ( ( rule__IF_Stmt__StatmentsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4136:1: ( ( rule__IF_Stmt__StatmentsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4136:1: ( ( rule__IF_Stmt__StatmentsAssignment_3 ) )
            // InternalStructuredTextParser.g:4137:2: ( rule__IF_Stmt__StatmentsAssignment_3 )
            {
             before(grammarAccess.getIF_StmtAccess().getStatmentsAssignment_3()); 
            // InternalStructuredTextParser.g:4138:2: ( rule__IF_Stmt__StatmentsAssignment_3 )
            // InternalStructuredTextParser.g:4138:3: rule__IF_Stmt__StatmentsAssignment_3
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
    // InternalStructuredTextParser.g:4146:1: rule__IF_Stmt__Group__4 : rule__IF_Stmt__Group__4__Impl rule__IF_Stmt__Group__5 ;
    public final void rule__IF_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4150:1: ( rule__IF_Stmt__Group__4__Impl rule__IF_Stmt__Group__5 )
            // InternalStructuredTextParser.g:4151:2: rule__IF_Stmt__Group__4__Impl rule__IF_Stmt__Group__5
            {
            pushFollow(FOLLOW_31);
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
    // InternalStructuredTextParser.g:4158:1: rule__IF_Stmt__Group__4__Impl : ( ( rule__IF_Stmt__ElseifAssignment_4 )* ) ;
    public final void rule__IF_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4162:1: ( ( ( rule__IF_Stmt__ElseifAssignment_4 )* ) )
            // InternalStructuredTextParser.g:4163:1: ( ( rule__IF_Stmt__ElseifAssignment_4 )* )
            {
            // InternalStructuredTextParser.g:4163:1: ( ( rule__IF_Stmt__ElseifAssignment_4 )* )
            // InternalStructuredTextParser.g:4164:2: ( rule__IF_Stmt__ElseifAssignment_4 )*
            {
             before(grammarAccess.getIF_StmtAccess().getElseifAssignment_4()); 
            // InternalStructuredTextParser.g:4165:2: ( rule__IF_Stmt__ElseifAssignment_4 )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==ELSIF) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // InternalStructuredTextParser.g:4165:3: rule__IF_Stmt__ElseifAssignment_4
            	    {
            	    pushFollow(FOLLOW_32);
            	    rule__IF_Stmt__ElseifAssignment_4();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop40;
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
    // InternalStructuredTextParser.g:4173:1: rule__IF_Stmt__Group__5 : rule__IF_Stmt__Group__5__Impl rule__IF_Stmt__Group__6 ;
    public final void rule__IF_Stmt__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4177:1: ( rule__IF_Stmt__Group__5__Impl rule__IF_Stmt__Group__6 )
            // InternalStructuredTextParser.g:4178:2: rule__IF_Stmt__Group__5__Impl rule__IF_Stmt__Group__6
            {
            pushFollow(FOLLOW_31);
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
    // InternalStructuredTextParser.g:4185:1: rule__IF_Stmt__Group__5__Impl : ( ( rule__IF_Stmt__ElseAssignment_5 )? ) ;
    public final void rule__IF_Stmt__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4189:1: ( ( ( rule__IF_Stmt__ElseAssignment_5 )? ) )
            // InternalStructuredTextParser.g:4190:1: ( ( rule__IF_Stmt__ElseAssignment_5 )? )
            {
            // InternalStructuredTextParser.g:4190:1: ( ( rule__IF_Stmt__ElseAssignment_5 )? )
            // InternalStructuredTextParser.g:4191:2: ( rule__IF_Stmt__ElseAssignment_5 )?
            {
             before(grammarAccess.getIF_StmtAccess().getElseAssignment_5()); 
            // InternalStructuredTextParser.g:4192:2: ( rule__IF_Stmt__ElseAssignment_5 )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==ELSE) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalStructuredTextParser.g:4192:3: rule__IF_Stmt__ElseAssignment_5
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
    // InternalStructuredTextParser.g:4200:1: rule__IF_Stmt__Group__6 : rule__IF_Stmt__Group__6__Impl ;
    public final void rule__IF_Stmt__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4204:1: ( rule__IF_Stmt__Group__6__Impl )
            // InternalStructuredTextParser.g:4205:2: rule__IF_Stmt__Group__6__Impl
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
    // InternalStructuredTextParser.g:4211:1: rule__IF_Stmt__Group__6__Impl : ( END_IF ) ;
    public final void rule__IF_Stmt__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4215:1: ( ( END_IF ) )
            // InternalStructuredTextParser.g:4216:1: ( END_IF )
            {
            // InternalStructuredTextParser.g:4216:1: ( END_IF )
            // InternalStructuredTextParser.g:4217:2: END_IF
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
    // InternalStructuredTextParser.g:4227:1: rule__ELSIF_Clause__Group__0 : rule__ELSIF_Clause__Group__0__Impl rule__ELSIF_Clause__Group__1 ;
    public final void rule__ELSIF_Clause__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4231:1: ( rule__ELSIF_Clause__Group__0__Impl rule__ELSIF_Clause__Group__1 )
            // InternalStructuredTextParser.g:4232:2: rule__ELSIF_Clause__Group__0__Impl rule__ELSIF_Clause__Group__1
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
    // InternalStructuredTextParser.g:4239:1: rule__ELSIF_Clause__Group__0__Impl : ( ELSIF ) ;
    public final void rule__ELSIF_Clause__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4243:1: ( ( ELSIF ) )
            // InternalStructuredTextParser.g:4244:1: ( ELSIF )
            {
            // InternalStructuredTextParser.g:4244:1: ( ELSIF )
            // InternalStructuredTextParser.g:4245:2: ELSIF
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
    // InternalStructuredTextParser.g:4254:1: rule__ELSIF_Clause__Group__1 : rule__ELSIF_Clause__Group__1__Impl rule__ELSIF_Clause__Group__2 ;
    public final void rule__ELSIF_Clause__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4258:1: ( rule__ELSIF_Clause__Group__1__Impl rule__ELSIF_Clause__Group__2 )
            // InternalStructuredTextParser.g:4259:2: rule__ELSIF_Clause__Group__1__Impl rule__ELSIF_Clause__Group__2
            {
            pushFollow(FOLLOW_30);
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
    // InternalStructuredTextParser.g:4266:1: rule__ELSIF_Clause__Group__1__Impl : ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) ) ;
    public final void rule__ELSIF_Clause__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4270:1: ( ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4271:1: ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4271:1: ( ( rule__ELSIF_Clause__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:4272:2: ( rule__ELSIF_Clause__ExpressionAssignment_1 )
            {
             before(grammarAccess.getELSIF_ClauseAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:4273:2: ( rule__ELSIF_Clause__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:4273:3: rule__ELSIF_Clause__ExpressionAssignment_1
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
    // InternalStructuredTextParser.g:4281:1: rule__ELSIF_Clause__Group__2 : rule__ELSIF_Clause__Group__2__Impl rule__ELSIF_Clause__Group__3 ;
    public final void rule__ELSIF_Clause__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4285:1: ( rule__ELSIF_Clause__Group__2__Impl rule__ELSIF_Clause__Group__3 )
            // InternalStructuredTextParser.g:4286:2: rule__ELSIF_Clause__Group__2__Impl rule__ELSIF_Clause__Group__3
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
    // InternalStructuredTextParser.g:4293:1: rule__ELSIF_Clause__Group__2__Impl : ( THEN ) ;
    public final void rule__ELSIF_Clause__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4297:1: ( ( THEN ) )
            // InternalStructuredTextParser.g:4298:1: ( THEN )
            {
            // InternalStructuredTextParser.g:4298:1: ( THEN )
            // InternalStructuredTextParser.g:4299:2: THEN
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
    // InternalStructuredTextParser.g:4308:1: rule__ELSIF_Clause__Group__3 : rule__ELSIF_Clause__Group__3__Impl ;
    public final void rule__ELSIF_Clause__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4312:1: ( rule__ELSIF_Clause__Group__3__Impl )
            // InternalStructuredTextParser.g:4313:2: rule__ELSIF_Clause__Group__3__Impl
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
    // InternalStructuredTextParser.g:4319:1: rule__ELSIF_Clause__Group__3__Impl : ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) ) ;
    public final void rule__ELSIF_Clause__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4323:1: ( ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4324:1: ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4324:1: ( ( rule__ELSIF_Clause__StatementsAssignment_3 ) )
            // InternalStructuredTextParser.g:4325:2: ( rule__ELSIF_Clause__StatementsAssignment_3 )
            {
             before(grammarAccess.getELSIF_ClauseAccess().getStatementsAssignment_3()); 
            // InternalStructuredTextParser.g:4326:2: ( rule__ELSIF_Clause__StatementsAssignment_3 )
            // InternalStructuredTextParser.g:4326:3: rule__ELSIF_Clause__StatementsAssignment_3
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
    // InternalStructuredTextParser.g:4335:1: rule__ELSE_Clause__Group__0 : rule__ELSE_Clause__Group__0__Impl rule__ELSE_Clause__Group__1 ;
    public final void rule__ELSE_Clause__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4339:1: ( rule__ELSE_Clause__Group__0__Impl rule__ELSE_Clause__Group__1 )
            // InternalStructuredTextParser.g:4340:2: rule__ELSE_Clause__Group__0__Impl rule__ELSE_Clause__Group__1
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
    // InternalStructuredTextParser.g:4347:1: rule__ELSE_Clause__Group__0__Impl : ( ELSE ) ;
    public final void rule__ELSE_Clause__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4351:1: ( ( ELSE ) )
            // InternalStructuredTextParser.g:4352:1: ( ELSE )
            {
            // InternalStructuredTextParser.g:4352:1: ( ELSE )
            // InternalStructuredTextParser.g:4353:2: ELSE
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
    // InternalStructuredTextParser.g:4362:1: rule__ELSE_Clause__Group__1 : rule__ELSE_Clause__Group__1__Impl ;
    public final void rule__ELSE_Clause__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4366:1: ( rule__ELSE_Clause__Group__1__Impl )
            // InternalStructuredTextParser.g:4367:2: rule__ELSE_Clause__Group__1__Impl
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
    // InternalStructuredTextParser.g:4373:1: rule__ELSE_Clause__Group__1__Impl : ( ( rule__ELSE_Clause__StatementsAssignment_1 ) ) ;
    public final void rule__ELSE_Clause__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4377:1: ( ( ( rule__ELSE_Clause__StatementsAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4378:1: ( ( rule__ELSE_Clause__StatementsAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4378:1: ( ( rule__ELSE_Clause__StatementsAssignment_1 ) )
            // InternalStructuredTextParser.g:4379:2: ( rule__ELSE_Clause__StatementsAssignment_1 )
            {
             before(grammarAccess.getELSE_ClauseAccess().getStatementsAssignment_1()); 
            // InternalStructuredTextParser.g:4380:2: ( rule__ELSE_Clause__StatementsAssignment_1 )
            // InternalStructuredTextParser.g:4380:3: rule__ELSE_Clause__StatementsAssignment_1
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
    // InternalStructuredTextParser.g:4389:1: rule__Case_Stmt__Group__0 : rule__Case_Stmt__Group__0__Impl rule__Case_Stmt__Group__1 ;
    public final void rule__Case_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4393:1: ( rule__Case_Stmt__Group__0__Impl rule__Case_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4394:2: rule__Case_Stmt__Group__0__Impl rule__Case_Stmt__Group__1
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
    // InternalStructuredTextParser.g:4401:1: rule__Case_Stmt__Group__0__Impl : ( CASE ) ;
    public final void rule__Case_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4405:1: ( ( CASE ) )
            // InternalStructuredTextParser.g:4406:1: ( CASE )
            {
            // InternalStructuredTextParser.g:4406:1: ( CASE )
            // InternalStructuredTextParser.g:4407:2: CASE
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
    // InternalStructuredTextParser.g:4416:1: rule__Case_Stmt__Group__1 : rule__Case_Stmt__Group__1__Impl rule__Case_Stmt__Group__2 ;
    public final void rule__Case_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4420:1: ( rule__Case_Stmt__Group__1__Impl rule__Case_Stmt__Group__2 )
            // InternalStructuredTextParser.g:4421:2: rule__Case_Stmt__Group__1__Impl rule__Case_Stmt__Group__2
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
    // InternalStructuredTextParser.g:4428:1: rule__Case_Stmt__Group__1__Impl : ( ( rule__Case_Stmt__ExpressionAssignment_1 ) ) ;
    public final void rule__Case_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4432:1: ( ( ( rule__Case_Stmt__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4433:1: ( ( rule__Case_Stmt__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4433:1: ( ( rule__Case_Stmt__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:4434:2: ( rule__Case_Stmt__ExpressionAssignment_1 )
            {
             before(grammarAccess.getCase_StmtAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:4435:2: ( rule__Case_Stmt__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:4435:3: rule__Case_Stmt__ExpressionAssignment_1
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
    // InternalStructuredTextParser.g:4443:1: rule__Case_Stmt__Group__2 : rule__Case_Stmt__Group__2__Impl rule__Case_Stmt__Group__3 ;
    public final void rule__Case_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4447:1: ( rule__Case_Stmt__Group__2__Impl rule__Case_Stmt__Group__3 )
            // InternalStructuredTextParser.g:4448:2: rule__Case_Stmt__Group__2__Impl rule__Case_Stmt__Group__3
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
    // InternalStructuredTextParser.g:4455:1: rule__Case_Stmt__Group__2__Impl : ( OF ) ;
    public final void rule__Case_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4459:1: ( ( OF ) )
            // InternalStructuredTextParser.g:4460:1: ( OF )
            {
            // InternalStructuredTextParser.g:4460:1: ( OF )
            // InternalStructuredTextParser.g:4461:2: OF
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
    // InternalStructuredTextParser.g:4470:1: rule__Case_Stmt__Group__3 : rule__Case_Stmt__Group__3__Impl rule__Case_Stmt__Group__4 ;
    public final void rule__Case_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4474:1: ( rule__Case_Stmt__Group__3__Impl rule__Case_Stmt__Group__4 )
            // InternalStructuredTextParser.g:4475:2: rule__Case_Stmt__Group__3__Impl rule__Case_Stmt__Group__4
            {
            pushFollow(FOLLOW_33);
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
    // InternalStructuredTextParser.g:4482:1: rule__Case_Stmt__Group__3__Impl : ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) ) ;
    public final void rule__Case_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4486:1: ( ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) ) )
            // InternalStructuredTextParser.g:4487:1: ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) )
            {
            // InternalStructuredTextParser.g:4487:1: ( ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* ) )
            // InternalStructuredTextParser.g:4488:2: ( ( rule__Case_Stmt__CaseAssignment_3 ) ) ( ( rule__Case_Stmt__CaseAssignment_3 )* )
            {
            // InternalStructuredTextParser.g:4488:2: ( ( rule__Case_Stmt__CaseAssignment_3 ) )
            // InternalStructuredTextParser.g:4489:3: ( rule__Case_Stmt__CaseAssignment_3 )
            {
             before(grammarAccess.getCase_StmtAccess().getCaseAssignment_3()); 
            // InternalStructuredTextParser.g:4490:3: ( rule__Case_Stmt__CaseAssignment_3 )
            // InternalStructuredTextParser.g:4490:4: rule__Case_Stmt__CaseAssignment_3
            {
            pushFollow(FOLLOW_34);
            rule__Case_Stmt__CaseAssignment_3();

            state._fsp--;


            }

             after(grammarAccess.getCase_StmtAccess().getCaseAssignment_3()); 

            }

            // InternalStructuredTextParser.g:4493:2: ( ( rule__Case_Stmt__CaseAssignment_3 )* )
            // InternalStructuredTextParser.g:4494:3: ( rule__Case_Stmt__CaseAssignment_3 )*
            {
             before(grammarAccess.getCase_StmtAccess().getCaseAssignment_3()); 
            // InternalStructuredTextParser.g:4495:3: ( rule__Case_Stmt__CaseAssignment_3 )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( (LA42_0==WSTRING||LA42_0==STRING||LA42_0==FALSE||LA42_0==LREAL||(LA42_0>=UDINT && LA42_0<=ULINT)||(LA42_0>=USINT && LA42_0<=WCHAR)||LA42_0==BOOL||LA42_0==CHAR||LA42_0==DINT||LA42_0==LINT||(LA42_0>=REAL && LA42_0<=SINT)||(LA42_0>=TRUE && LA42_0<=UINT)||LA42_0==INT||LA42_0==PlusSign||LA42_0==HyphenMinus||(LA42_0>=RULE_UNSIGNED_INT && LA42_0<=RULE_DATE)||(LA42_0>=RULE_BINARY_INT && LA42_0<=RULE_HEX_INT)||LA42_0==RULE_S_BYTE_CHAR_STR||LA42_0==RULE_D_BYTE_CHAR_STR) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // InternalStructuredTextParser.g:4495:4: rule__Case_Stmt__CaseAssignment_3
            	    {
            	    pushFollow(FOLLOW_34);
            	    rule__Case_Stmt__CaseAssignment_3();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop42;
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
    // InternalStructuredTextParser.g:4504:1: rule__Case_Stmt__Group__4 : rule__Case_Stmt__Group__4__Impl rule__Case_Stmt__Group__5 ;
    public final void rule__Case_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4508:1: ( rule__Case_Stmt__Group__4__Impl rule__Case_Stmt__Group__5 )
            // InternalStructuredTextParser.g:4509:2: rule__Case_Stmt__Group__4__Impl rule__Case_Stmt__Group__5
            {
            pushFollow(FOLLOW_33);
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
    // InternalStructuredTextParser.g:4516:1: rule__Case_Stmt__Group__4__Impl : ( ( rule__Case_Stmt__ElseAssignment_4 )? ) ;
    public final void rule__Case_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4520:1: ( ( ( rule__Case_Stmt__ElseAssignment_4 )? ) )
            // InternalStructuredTextParser.g:4521:1: ( ( rule__Case_Stmt__ElseAssignment_4 )? )
            {
            // InternalStructuredTextParser.g:4521:1: ( ( rule__Case_Stmt__ElseAssignment_4 )? )
            // InternalStructuredTextParser.g:4522:2: ( rule__Case_Stmt__ElseAssignment_4 )?
            {
             before(grammarAccess.getCase_StmtAccess().getElseAssignment_4()); 
            // InternalStructuredTextParser.g:4523:2: ( rule__Case_Stmt__ElseAssignment_4 )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==ELSE) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalStructuredTextParser.g:4523:3: rule__Case_Stmt__ElseAssignment_4
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
    // InternalStructuredTextParser.g:4531:1: rule__Case_Stmt__Group__5 : rule__Case_Stmt__Group__5__Impl ;
    public final void rule__Case_Stmt__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4535:1: ( rule__Case_Stmt__Group__5__Impl )
            // InternalStructuredTextParser.g:4536:2: rule__Case_Stmt__Group__5__Impl
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
    // InternalStructuredTextParser.g:4542:1: rule__Case_Stmt__Group__5__Impl : ( END_CASE ) ;
    public final void rule__Case_Stmt__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4546:1: ( ( END_CASE ) )
            // InternalStructuredTextParser.g:4547:1: ( END_CASE )
            {
            // InternalStructuredTextParser.g:4547:1: ( END_CASE )
            // InternalStructuredTextParser.g:4548:2: END_CASE
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
    // InternalStructuredTextParser.g:4558:1: rule__Case_Selection__Group__0 : rule__Case_Selection__Group__0__Impl rule__Case_Selection__Group__1 ;
    public final void rule__Case_Selection__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4562:1: ( rule__Case_Selection__Group__0__Impl rule__Case_Selection__Group__1 )
            // InternalStructuredTextParser.g:4563:2: rule__Case_Selection__Group__0__Impl rule__Case_Selection__Group__1
            {
            pushFollow(FOLLOW_35);
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
    // InternalStructuredTextParser.g:4570:1: rule__Case_Selection__Group__0__Impl : ( ( rule__Case_Selection__CaseAssignment_0 ) ) ;
    public final void rule__Case_Selection__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4574:1: ( ( ( rule__Case_Selection__CaseAssignment_0 ) ) )
            // InternalStructuredTextParser.g:4575:1: ( ( rule__Case_Selection__CaseAssignment_0 ) )
            {
            // InternalStructuredTextParser.g:4575:1: ( ( rule__Case_Selection__CaseAssignment_0 ) )
            // InternalStructuredTextParser.g:4576:2: ( rule__Case_Selection__CaseAssignment_0 )
            {
             before(grammarAccess.getCase_SelectionAccess().getCaseAssignment_0()); 
            // InternalStructuredTextParser.g:4577:2: ( rule__Case_Selection__CaseAssignment_0 )
            // InternalStructuredTextParser.g:4577:3: rule__Case_Selection__CaseAssignment_0
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
    // InternalStructuredTextParser.g:4585:1: rule__Case_Selection__Group__1 : rule__Case_Selection__Group__1__Impl rule__Case_Selection__Group__2 ;
    public final void rule__Case_Selection__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4589:1: ( rule__Case_Selection__Group__1__Impl rule__Case_Selection__Group__2 )
            // InternalStructuredTextParser.g:4590:2: rule__Case_Selection__Group__1__Impl rule__Case_Selection__Group__2
            {
            pushFollow(FOLLOW_35);
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
    // InternalStructuredTextParser.g:4597:1: rule__Case_Selection__Group__1__Impl : ( ( rule__Case_Selection__Group_1__0 )* ) ;
    public final void rule__Case_Selection__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4601:1: ( ( ( rule__Case_Selection__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:4602:1: ( ( rule__Case_Selection__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:4602:1: ( ( rule__Case_Selection__Group_1__0 )* )
            // InternalStructuredTextParser.g:4603:2: ( rule__Case_Selection__Group_1__0 )*
            {
             before(grammarAccess.getCase_SelectionAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:4604:2: ( rule__Case_Selection__Group_1__0 )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==Comma) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // InternalStructuredTextParser.g:4604:3: rule__Case_Selection__Group_1__0
            	    {
            	    pushFollow(FOLLOW_29);
            	    rule__Case_Selection__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop44;
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
    // InternalStructuredTextParser.g:4612:1: rule__Case_Selection__Group__2 : rule__Case_Selection__Group__2__Impl rule__Case_Selection__Group__3 ;
    public final void rule__Case_Selection__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4616:1: ( rule__Case_Selection__Group__2__Impl rule__Case_Selection__Group__3 )
            // InternalStructuredTextParser.g:4617:2: rule__Case_Selection__Group__2__Impl rule__Case_Selection__Group__3
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
    // InternalStructuredTextParser.g:4624:1: rule__Case_Selection__Group__2__Impl : ( Colon ) ;
    public final void rule__Case_Selection__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4628:1: ( ( Colon ) )
            // InternalStructuredTextParser.g:4629:1: ( Colon )
            {
            // InternalStructuredTextParser.g:4629:1: ( Colon )
            // InternalStructuredTextParser.g:4630:2: Colon
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
    // InternalStructuredTextParser.g:4639:1: rule__Case_Selection__Group__3 : rule__Case_Selection__Group__3__Impl ;
    public final void rule__Case_Selection__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4643:1: ( rule__Case_Selection__Group__3__Impl )
            // InternalStructuredTextParser.g:4644:2: rule__Case_Selection__Group__3__Impl
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
    // InternalStructuredTextParser.g:4650:1: rule__Case_Selection__Group__3__Impl : ( ( rule__Case_Selection__StatementsAssignment_3 ) ) ;
    public final void rule__Case_Selection__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4654:1: ( ( ( rule__Case_Selection__StatementsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4655:1: ( ( rule__Case_Selection__StatementsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4655:1: ( ( rule__Case_Selection__StatementsAssignment_3 ) )
            // InternalStructuredTextParser.g:4656:2: ( rule__Case_Selection__StatementsAssignment_3 )
            {
             before(grammarAccess.getCase_SelectionAccess().getStatementsAssignment_3()); 
            // InternalStructuredTextParser.g:4657:2: ( rule__Case_Selection__StatementsAssignment_3 )
            // InternalStructuredTextParser.g:4657:3: rule__Case_Selection__StatementsAssignment_3
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
    // InternalStructuredTextParser.g:4666:1: rule__Case_Selection__Group_1__0 : rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1 ;
    public final void rule__Case_Selection__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4670:1: ( rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1 )
            // InternalStructuredTextParser.g:4671:2: rule__Case_Selection__Group_1__0__Impl rule__Case_Selection__Group_1__1
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
    // InternalStructuredTextParser.g:4678:1: rule__Case_Selection__Group_1__0__Impl : ( Comma ) ;
    public final void rule__Case_Selection__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4682:1: ( ( Comma ) )
            // InternalStructuredTextParser.g:4683:1: ( Comma )
            {
            // InternalStructuredTextParser.g:4683:1: ( Comma )
            // InternalStructuredTextParser.g:4684:2: Comma
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
    // InternalStructuredTextParser.g:4693:1: rule__Case_Selection__Group_1__1 : rule__Case_Selection__Group_1__1__Impl ;
    public final void rule__Case_Selection__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4697:1: ( rule__Case_Selection__Group_1__1__Impl )
            // InternalStructuredTextParser.g:4698:2: rule__Case_Selection__Group_1__1__Impl
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
    // InternalStructuredTextParser.g:4704:1: rule__Case_Selection__Group_1__1__Impl : ( ( rule__Case_Selection__CaseAssignment_1_1 ) ) ;
    public final void rule__Case_Selection__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4708:1: ( ( ( rule__Case_Selection__CaseAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:4709:1: ( ( rule__Case_Selection__CaseAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:4709:1: ( ( rule__Case_Selection__CaseAssignment_1_1 ) )
            // InternalStructuredTextParser.g:4710:2: ( rule__Case_Selection__CaseAssignment_1_1 )
            {
             before(grammarAccess.getCase_SelectionAccess().getCaseAssignment_1_1()); 
            // InternalStructuredTextParser.g:4711:2: ( rule__Case_Selection__CaseAssignment_1_1 )
            // InternalStructuredTextParser.g:4711:3: rule__Case_Selection__CaseAssignment_1_1
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
    // InternalStructuredTextParser.g:4720:1: rule__Iteration_Stmt__Group_3__0 : rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1 ;
    public final void rule__Iteration_Stmt__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4724:1: ( rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1 )
            // InternalStructuredTextParser.g:4725:2: rule__Iteration_Stmt__Group_3__0__Impl rule__Iteration_Stmt__Group_3__1
            {
            pushFollow(FOLLOW_36);
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
    // InternalStructuredTextParser.g:4732:1: rule__Iteration_Stmt__Group_3__0__Impl : ( () ) ;
    public final void rule__Iteration_Stmt__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4736:1: ( ( () ) )
            // InternalStructuredTextParser.g:4737:1: ( () )
            {
            // InternalStructuredTextParser.g:4737:1: ( () )
            // InternalStructuredTextParser.g:4738:2: ()
            {
             before(grammarAccess.getIteration_StmtAccess().getExitStatementAction_3_0()); 
            // InternalStructuredTextParser.g:4739:2: ()
            // InternalStructuredTextParser.g:4739:3: 
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
    // InternalStructuredTextParser.g:4747:1: rule__Iteration_Stmt__Group_3__1 : rule__Iteration_Stmt__Group_3__1__Impl ;
    public final void rule__Iteration_Stmt__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4751:1: ( rule__Iteration_Stmt__Group_3__1__Impl )
            // InternalStructuredTextParser.g:4752:2: rule__Iteration_Stmt__Group_3__1__Impl
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
    // InternalStructuredTextParser.g:4758:1: rule__Iteration_Stmt__Group_3__1__Impl : ( EXIT ) ;
    public final void rule__Iteration_Stmt__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4762:1: ( ( EXIT ) )
            // InternalStructuredTextParser.g:4763:1: ( EXIT )
            {
            // InternalStructuredTextParser.g:4763:1: ( EXIT )
            // InternalStructuredTextParser.g:4764:2: EXIT
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
    // InternalStructuredTextParser.g:4774:1: rule__Iteration_Stmt__Group_4__0 : rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1 ;
    public final void rule__Iteration_Stmt__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4778:1: ( rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1 )
            // InternalStructuredTextParser.g:4779:2: rule__Iteration_Stmt__Group_4__0__Impl rule__Iteration_Stmt__Group_4__1
            {
            pushFollow(FOLLOW_37);
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
    // InternalStructuredTextParser.g:4786:1: rule__Iteration_Stmt__Group_4__0__Impl : ( () ) ;
    public final void rule__Iteration_Stmt__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4790:1: ( ( () ) )
            // InternalStructuredTextParser.g:4791:1: ( () )
            {
            // InternalStructuredTextParser.g:4791:1: ( () )
            // InternalStructuredTextParser.g:4792:2: ()
            {
             before(grammarAccess.getIteration_StmtAccess().getContinueStatementAction_4_0()); 
            // InternalStructuredTextParser.g:4793:2: ()
            // InternalStructuredTextParser.g:4793:3: 
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
    // InternalStructuredTextParser.g:4801:1: rule__Iteration_Stmt__Group_4__1 : rule__Iteration_Stmt__Group_4__1__Impl ;
    public final void rule__Iteration_Stmt__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4805:1: ( rule__Iteration_Stmt__Group_4__1__Impl )
            // InternalStructuredTextParser.g:4806:2: rule__Iteration_Stmt__Group_4__1__Impl
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
    // InternalStructuredTextParser.g:4812:1: rule__Iteration_Stmt__Group_4__1__Impl : ( CONTINUE ) ;
    public final void rule__Iteration_Stmt__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4816:1: ( ( CONTINUE ) )
            // InternalStructuredTextParser.g:4817:1: ( CONTINUE )
            {
            // InternalStructuredTextParser.g:4817:1: ( CONTINUE )
            // InternalStructuredTextParser.g:4818:2: CONTINUE
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
    // InternalStructuredTextParser.g:4828:1: rule__For_Stmt__Group__0 : rule__For_Stmt__Group__0__Impl rule__For_Stmt__Group__1 ;
    public final void rule__For_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4832:1: ( rule__For_Stmt__Group__0__Impl rule__For_Stmt__Group__1 )
            // InternalStructuredTextParser.g:4833:2: rule__For_Stmt__Group__0__Impl rule__For_Stmt__Group__1
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
    // InternalStructuredTextParser.g:4840:1: rule__For_Stmt__Group__0__Impl : ( FOR ) ;
    public final void rule__For_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4844:1: ( ( FOR ) )
            // InternalStructuredTextParser.g:4845:1: ( FOR )
            {
            // InternalStructuredTextParser.g:4845:1: ( FOR )
            // InternalStructuredTextParser.g:4846:2: FOR
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
    // InternalStructuredTextParser.g:4855:1: rule__For_Stmt__Group__1 : rule__For_Stmt__Group__1__Impl rule__For_Stmt__Group__2 ;
    public final void rule__For_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4859:1: ( rule__For_Stmt__Group__1__Impl rule__For_Stmt__Group__2 )
            // InternalStructuredTextParser.g:4860:2: rule__For_Stmt__Group__1__Impl rule__For_Stmt__Group__2
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
    // InternalStructuredTextParser.g:4867:1: rule__For_Stmt__Group__1__Impl : ( ( rule__For_Stmt__VariableAssignment_1 ) ) ;
    public final void rule__For_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4871:1: ( ( ( rule__For_Stmt__VariableAssignment_1 ) ) )
            // InternalStructuredTextParser.g:4872:1: ( ( rule__For_Stmt__VariableAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:4872:1: ( ( rule__For_Stmt__VariableAssignment_1 ) )
            // InternalStructuredTextParser.g:4873:2: ( rule__For_Stmt__VariableAssignment_1 )
            {
             before(grammarAccess.getFor_StmtAccess().getVariableAssignment_1()); 
            // InternalStructuredTextParser.g:4874:2: ( rule__For_Stmt__VariableAssignment_1 )
            // InternalStructuredTextParser.g:4874:3: rule__For_Stmt__VariableAssignment_1
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
    // InternalStructuredTextParser.g:4882:1: rule__For_Stmt__Group__2 : rule__For_Stmt__Group__2__Impl rule__For_Stmt__Group__3 ;
    public final void rule__For_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4886:1: ( rule__For_Stmt__Group__2__Impl rule__For_Stmt__Group__3 )
            // InternalStructuredTextParser.g:4887:2: rule__For_Stmt__Group__2__Impl rule__For_Stmt__Group__3
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
    // InternalStructuredTextParser.g:4894:1: rule__For_Stmt__Group__2__Impl : ( ColonEqualsSign ) ;
    public final void rule__For_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4898:1: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:4899:1: ( ColonEqualsSign )
            {
            // InternalStructuredTextParser.g:4899:1: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:4900:2: ColonEqualsSign
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
    // InternalStructuredTextParser.g:4909:1: rule__For_Stmt__Group__3 : rule__For_Stmt__Group__3__Impl rule__For_Stmt__Group__4 ;
    public final void rule__For_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4913:1: ( rule__For_Stmt__Group__3__Impl rule__For_Stmt__Group__4 )
            // InternalStructuredTextParser.g:4914:2: rule__For_Stmt__Group__3__Impl rule__For_Stmt__Group__4
            {
            pushFollow(FOLLOW_38);
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
    // InternalStructuredTextParser.g:4921:1: rule__For_Stmt__Group__3__Impl : ( ( rule__For_Stmt__FromAssignment_3 ) ) ;
    public final void rule__For_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4925:1: ( ( ( rule__For_Stmt__FromAssignment_3 ) ) )
            // InternalStructuredTextParser.g:4926:1: ( ( rule__For_Stmt__FromAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:4926:1: ( ( rule__For_Stmt__FromAssignment_3 ) )
            // InternalStructuredTextParser.g:4927:2: ( rule__For_Stmt__FromAssignment_3 )
            {
             before(grammarAccess.getFor_StmtAccess().getFromAssignment_3()); 
            // InternalStructuredTextParser.g:4928:2: ( rule__For_Stmt__FromAssignment_3 )
            // InternalStructuredTextParser.g:4928:3: rule__For_Stmt__FromAssignment_3
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
    // InternalStructuredTextParser.g:4936:1: rule__For_Stmt__Group__4 : rule__For_Stmt__Group__4__Impl rule__For_Stmt__Group__5 ;
    public final void rule__For_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4940:1: ( rule__For_Stmt__Group__4__Impl rule__For_Stmt__Group__5 )
            // InternalStructuredTextParser.g:4941:2: rule__For_Stmt__Group__4__Impl rule__For_Stmt__Group__5
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
    // InternalStructuredTextParser.g:4948:1: rule__For_Stmt__Group__4__Impl : ( TO ) ;
    public final void rule__For_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4952:1: ( ( TO ) )
            // InternalStructuredTextParser.g:4953:1: ( TO )
            {
            // InternalStructuredTextParser.g:4953:1: ( TO )
            // InternalStructuredTextParser.g:4954:2: TO
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
    // InternalStructuredTextParser.g:4963:1: rule__For_Stmt__Group__5 : rule__For_Stmt__Group__5__Impl rule__For_Stmt__Group__6 ;
    public final void rule__For_Stmt__Group__5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4967:1: ( rule__For_Stmt__Group__5__Impl rule__For_Stmt__Group__6 )
            // InternalStructuredTextParser.g:4968:2: rule__For_Stmt__Group__5__Impl rule__For_Stmt__Group__6
            {
            pushFollow(FOLLOW_39);
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
    // InternalStructuredTextParser.g:4975:1: rule__For_Stmt__Group__5__Impl : ( ( rule__For_Stmt__ToAssignment_5 ) ) ;
    public final void rule__For_Stmt__Group__5__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4979:1: ( ( ( rule__For_Stmt__ToAssignment_5 ) ) )
            // InternalStructuredTextParser.g:4980:1: ( ( rule__For_Stmt__ToAssignment_5 ) )
            {
            // InternalStructuredTextParser.g:4980:1: ( ( rule__For_Stmt__ToAssignment_5 ) )
            // InternalStructuredTextParser.g:4981:2: ( rule__For_Stmt__ToAssignment_5 )
            {
             before(grammarAccess.getFor_StmtAccess().getToAssignment_5()); 
            // InternalStructuredTextParser.g:4982:2: ( rule__For_Stmt__ToAssignment_5 )
            // InternalStructuredTextParser.g:4982:3: rule__For_Stmt__ToAssignment_5
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
    // InternalStructuredTextParser.g:4990:1: rule__For_Stmt__Group__6 : rule__For_Stmt__Group__6__Impl rule__For_Stmt__Group__7 ;
    public final void rule__For_Stmt__Group__6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:4994:1: ( rule__For_Stmt__Group__6__Impl rule__For_Stmt__Group__7 )
            // InternalStructuredTextParser.g:4995:2: rule__For_Stmt__Group__6__Impl rule__For_Stmt__Group__7
            {
            pushFollow(FOLLOW_39);
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
    // InternalStructuredTextParser.g:5002:1: rule__For_Stmt__Group__6__Impl : ( ( rule__For_Stmt__Group_6__0 )? ) ;
    public final void rule__For_Stmt__Group__6__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5006:1: ( ( ( rule__For_Stmt__Group_6__0 )? ) )
            // InternalStructuredTextParser.g:5007:1: ( ( rule__For_Stmt__Group_6__0 )? )
            {
            // InternalStructuredTextParser.g:5007:1: ( ( rule__For_Stmt__Group_6__0 )? )
            // InternalStructuredTextParser.g:5008:2: ( rule__For_Stmt__Group_6__0 )?
            {
             before(grammarAccess.getFor_StmtAccess().getGroup_6()); 
            // InternalStructuredTextParser.g:5009:2: ( rule__For_Stmt__Group_6__0 )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==BY) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalStructuredTextParser.g:5009:3: rule__For_Stmt__Group_6__0
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
    // InternalStructuredTextParser.g:5017:1: rule__For_Stmt__Group__7 : rule__For_Stmt__Group__7__Impl rule__For_Stmt__Group__8 ;
    public final void rule__For_Stmt__Group__7() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5021:1: ( rule__For_Stmt__Group__7__Impl rule__For_Stmt__Group__8 )
            // InternalStructuredTextParser.g:5022:2: rule__For_Stmt__Group__7__Impl rule__For_Stmt__Group__8
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
    // InternalStructuredTextParser.g:5029:1: rule__For_Stmt__Group__7__Impl : ( DO ) ;
    public final void rule__For_Stmt__Group__7__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5033:1: ( ( DO ) )
            // InternalStructuredTextParser.g:5034:1: ( DO )
            {
            // InternalStructuredTextParser.g:5034:1: ( DO )
            // InternalStructuredTextParser.g:5035:2: DO
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
    // InternalStructuredTextParser.g:5044:1: rule__For_Stmt__Group__8 : rule__For_Stmt__Group__8__Impl rule__For_Stmt__Group__9 ;
    public final void rule__For_Stmt__Group__8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5048:1: ( rule__For_Stmt__Group__8__Impl rule__For_Stmt__Group__9 )
            // InternalStructuredTextParser.g:5049:2: rule__For_Stmt__Group__8__Impl rule__For_Stmt__Group__9
            {
            pushFollow(FOLLOW_40);
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
    // InternalStructuredTextParser.g:5056:1: rule__For_Stmt__Group__8__Impl : ( ( rule__For_Stmt__StatementsAssignment_8 ) ) ;
    public final void rule__For_Stmt__Group__8__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5060:1: ( ( ( rule__For_Stmt__StatementsAssignment_8 ) ) )
            // InternalStructuredTextParser.g:5061:1: ( ( rule__For_Stmt__StatementsAssignment_8 ) )
            {
            // InternalStructuredTextParser.g:5061:1: ( ( rule__For_Stmt__StatementsAssignment_8 ) )
            // InternalStructuredTextParser.g:5062:2: ( rule__For_Stmt__StatementsAssignment_8 )
            {
             before(grammarAccess.getFor_StmtAccess().getStatementsAssignment_8()); 
            // InternalStructuredTextParser.g:5063:2: ( rule__For_Stmt__StatementsAssignment_8 )
            // InternalStructuredTextParser.g:5063:3: rule__For_Stmt__StatementsAssignment_8
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
    // InternalStructuredTextParser.g:5071:1: rule__For_Stmt__Group__9 : rule__For_Stmt__Group__9__Impl ;
    public final void rule__For_Stmt__Group__9() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5075:1: ( rule__For_Stmt__Group__9__Impl )
            // InternalStructuredTextParser.g:5076:2: rule__For_Stmt__Group__9__Impl
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
    // InternalStructuredTextParser.g:5082:1: rule__For_Stmt__Group__9__Impl : ( END_FOR ) ;
    public final void rule__For_Stmt__Group__9__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5086:1: ( ( END_FOR ) )
            // InternalStructuredTextParser.g:5087:1: ( END_FOR )
            {
            // InternalStructuredTextParser.g:5087:1: ( END_FOR )
            // InternalStructuredTextParser.g:5088:2: END_FOR
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
    // InternalStructuredTextParser.g:5098:1: rule__For_Stmt__Group_6__0 : rule__For_Stmt__Group_6__0__Impl rule__For_Stmt__Group_6__1 ;
    public final void rule__For_Stmt__Group_6__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5102:1: ( rule__For_Stmt__Group_6__0__Impl rule__For_Stmt__Group_6__1 )
            // InternalStructuredTextParser.g:5103:2: rule__For_Stmt__Group_6__0__Impl rule__For_Stmt__Group_6__1
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
    // InternalStructuredTextParser.g:5110:1: rule__For_Stmt__Group_6__0__Impl : ( BY ) ;
    public final void rule__For_Stmt__Group_6__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5114:1: ( ( BY ) )
            // InternalStructuredTextParser.g:5115:1: ( BY )
            {
            // InternalStructuredTextParser.g:5115:1: ( BY )
            // InternalStructuredTextParser.g:5116:2: BY
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
    // InternalStructuredTextParser.g:5125:1: rule__For_Stmt__Group_6__1 : rule__For_Stmt__Group_6__1__Impl ;
    public final void rule__For_Stmt__Group_6__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5129:1: ( rule__For_Stmt__Group_6__1__Impl )
            // InternalStructuredTextParser.g:5130:2: rule__For_Stmt__Group_6__1__Impl
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
    // InternalStructuredTextParser.g:5136:1: rule__For_Stmt__Group_6__1__Impl : ( ( rule__For_Stmt__ByAssignment_6_1 ) ) ;
    public final void rule__For_Stmt__Group_6__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5140:1: ( ( ( rule__For_Stmt__ByAssignment_6_1 ) ) )
            // InternalStructuredTextParser.g:5141:1: ( ( rule__For_Stmt__ByAssignment_6_1 ) )
            {
            // InternalStructuredTextParser.g:5141:1: ( ( rule__For_Stmt__ByAssignment_6_1 ) )
            // InternalStructuredTextParser.g:5142:2: ( rule__For_Stmt__ByAssignment_6_1 )
            {
             before(grammarAccess.getFor_StmtAccess().getByAssignment_6_1()); 
            // InternalStructuredTextParser.g:5143:2: ( rule__For_Stmt__ByAssignment_6_1 )
            // InternalStructuredTextParser.g:5143:3: rule__For_Stmt__ByAssignment_6_1
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
    // InternalStructuredTextParser.g:5152:1: rule__While_Stmt__Group__0 : rule__While_Stmt__Group__0__Impl rule__While_Stmt__Group__1 ;
    public final void rule__While_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5156:1: ( rule__While_Stmt__Group__0__Impl rule__While_Stmt__Group__1 )
            // InternalStructuredTextParser.g:5157:2: rule__While_Stmt__Group__0__Impl rule__While_Stmt__Group__1
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
    // InternalStructuredTextParser.g:5164:1: rule__While_Stmt__Group__0__Impl : ( WHILE ) ;
    public final void rule__While_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5168:1: ( ( WHILE ) )
            // InternalStructuredTextParser.g:5169:1: ( WHILE )
            {
            // InternalStructuredTextParser.g:5169:1: ( WHILE )
            // InternalStructuredTextParser.g:5170:2: WHILE
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
    // InternalStructuredTextParser.g:5179:1: rule__While_Stmt__Group__1 : rule__While_Stmt__Group__1__Impl rule__While_Stmt__Group__2 ;
    public final void rule__While_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5183:1: ( rule__While_Stmt__Group__1__Impl rule__While_Stmt__Group__2 )
            // InternalStructuredTextParser.g:5184:2: rule__While_Stmt__Group__1__Impl rule__While_Stmt__Group__2
            {
            pushFollow(FOLLOW_41);
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
    // InternalStructuredTextParser.g:5191:1: rule__While_Stmt__Group__1__Impl : ( ( rule__While_Stmt__ExpressionAssignment_1 ) ) ;
    public final void rule__While_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5195:1: ( ( ( rule__While_Stmt__ExpressionAssignment_1 ) ) )
            // InternalStructuredTextParser.g:5196:1: ( ( rule__While_Stmt__ExpressionAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:5196:1: ( ( rule__While_Stmt__ExpressionAssignment_1 ) )
            // InternalStructuredTextParser.g:5197:2: ( rule__While_Stmt__ExpressionAssignment_1 )
            {
             before(grammarAccess.getWhile_StmtAccess().getExpressionAssignment_1()); 
            // InternalStructuredTextParser.g:5198:2: ( rule__While_Stmt__ExpressionAssignment_1 )
            // InternalStructuredTextParser.g:5198:3: rule__While_Stmt__ExpressionAssignment_1
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
    // InternalStructuredTextParser.g:5206:1: rule__While_Stmt__Group__2 : rule__While_Stmt__Group__2__Impl rule__While_Stmt__Group__3 ;
    public final void rule__While_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5210:1: ( rule__While_Stmt__Group__2__Impl rule__While_Stmt__Group__3 )
            // InternalStructuredTextParser.g:5211:2: rule__While_Stmt__Group__2__Impl rule__While_Stmt__Group__3
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
    // InternalStructuredTextParser.g:5218:1: rule__While_Stmt__Group__2__Impl : ( DO ) ;
    public final void rule__While_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5222:1: ( ( DO ) )
            // InternalStructuredTextParser.g:5223:1: ( DO )
            {
            // InternalStructuredTextParser.g:5223:1: ( DO )
            // InternalStructuredTextParser.g:5224:2: DO
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
    // InternalStructuredTextParser.g:5233:1: rule__While_Stmt__Group__3 : rule__While_Stmt__Group__3__Impl rule__While_Stmt__Group__4 ;
    public final void rule__While_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5237:1: ( rule__While_Stmt__Group__3__Impl rule__While_Stmt__Group__4 )
            // InternalStructuredTextParser.g:5238:2: rule__While_Stmt__Group__3__Impl rule__While_Stmt__Group__4
            {
            pushFollow(FOLLOW_42);
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
    // InternalStructuredTextParser.g:5245:1: rule__While_Stmt__Group__3__Impl : ( ( rule__While_Stmt__StatementsAssignment_3 ) ) ;
    public final void rule__While_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5249:1: ( ( ( rule__While_Stmt__StatementsAssignment_3 ) ) )
            // InternalStructuredTextParser.g:5250:1: ( ( rule__While_Stmt__StatementsAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:5250:1: ( ( rule__While_Stmt__StatementsAssignment_3 ) )
            // InternalStructuredTextParser.g:5251:2: ( rule__While_Stmt__StatementsAssignment_3 )
            {
             before(grammarAccess.getWhile_StmtAccess().getStatementsAssignment_3()); 
            // InternalStructuredTextParser.g:5252:2: ( rule__While_Stmt__StatementsAssignment_3 )
            // InternalStructuredTextParser.g:5252:3: rule__While_Stmt__StatementsAssignment_3
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
    // InternalStructuredTextParser.g:5260:1: rule__While_Stmt__Group__4 : rule__While_Stmt__Group__4__Impl ;
    public final void rule__While_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5264:1: ( rule__While_Stmt__Group__4__Impl )
            // InternalStructuredTextParser.g:5265:2: rule__While_Stmt__Group__4__Impl
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
    // InternalStructuredTextParser.g:5271:1: rule__While_Stmt__Group__4__Impl : ( END_WHILE ) ;
    public final void rule__While_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5275:1: ( ( END_WHILE ) )
            // InternalStructuredTextParser.g:5276:1: ( END_WHILE )
            {
            // InternalStructuredTextParser.g:5276:1: ( END_WHILE )
            // InternalStructuredTextParser.g:5277:2: END_WHILE
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
    // InternalStructuredTextParser.g:5287:1: rule__Repeat_Stmt__Group__0 : rule__Repeat_Stmt__Group__0__Impl rule__Repeat_Stmt__Group__1 ;
    public final void rule__Repeat_Stmt__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5291:1: ( rule__Repeat_Stmt__Group__0__Impl rule__Repeat_Stmt__Group__1 )
            // InternalStructuredTextParser.g:5292:2: rule__Repeat_Stmt__Group__0__Impl rule__Repeat_Stmt__Group__1
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
    // InternalStructuredTextParser.g:5299:1: rule__Repeat_Stmt__Group__0__Impl : ( REPEAT ) ;
    public final void rule__Repeat_Stmt__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5303:1: ( ( REPEAT ) )
            // InternalStructuredTextParser.g:5304:1: ( REPEAT )
            {
            // InternalStructuredTextParser.g:5304:1: ( REPEAT )
            // InternalStructuredTextParser.g:5305:2: REPEAT
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
    // InternalStructuredTextParser.g:5314:1: rule__Repeat_Stmt__Group__1 : rule__Repeat_Stmt__Group__1__Impl rule__Repeat_Stmt__Group__2 ;
    public final void rule__Repeat_Stmt__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5318:1: ( rule__Repeat_Stmt__Group__1__Impl rule__Repeat_Stmt__Group__2 )
            // InternalStructuredTextParser.g:5319:2: rule__Repeat_Stmt__Group__1__Impl rule__Repeat_Stmt__Group__2
            {
            pushFollow(FOLLOW_43);
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
    // InternalStructuredTextParser.g:5326:1: rule__Repeat_Stmt__Group__1__Impl : ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) ) ;
    public final void rule__Repeat_Stmt__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5330:1: ( ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) ) )
            // InternalStructuredTextParser.g:5331:1: ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:5331:1: ( ( rule__Repeat_Stmt__StatementsAssignment_1 ) )
            // InternalStructuredTextParser.g:5332:2: ( rule__Repeat_Stmt__StatementsAssignment_1 )
            {
             before(grammarAccess.getRepeat_StmtAccess().getStatementsAssignment_1()); 
            // InternalStructuredTextParser.g:5333:2: ( rule__Repeat_Stmt__StatementsAssignment_1 )
            // InternalStructuredTextParser.g:5333:3: rule__Repeat_Stmt__StatementsAssignment_1
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
    // InternalStructuredTextParser.g:5341:1: rule__Repeat_Stmt__Group__2 : rule__Repeat_Stmt__Group__2__Impl rule__Repeat_Stmt__Group__3 ;
    public final void rule__Repeat_Stmt__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5345:1: ( rule__Repeat_Stmt__Group__2__Impl rule__Repeat_Stmt__Group__3 )
            // InternalStructuredTextParser.g:5346:2: rule__Repeat_Stmt__Group__2__Impl rule__Repeat_Stmt__Group__3
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
    // InternalStructuredTextParser.g:5353:1: rule__Repeat_Stmt__Group__2__Impl : ( UNTIL ) ;
    public final void rule__Repeat_Stmt__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5357:1: ( ( UNTIL ) )
            // InternalStructuredTextParser.g:5358:1: ( UNTIL )
            {
            // InternalStructuredTextParser.g:5358:1: ( UNTIL )
            // InternalStructuredTextParser.g:5359:2: UNTIL
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
    // InternalStructuredTextParser.g:5368:1: rule__Repeat_Stmt__Group__3 : rule__Repeat_Stmt__Group__3__Impl rule__Repeat_Stmt__Group__4 ;
    public final void rule__Repeat_Stmt__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5372:1: ( rule__Repeat_Stmt__Group__3__Impl rule__Repeat_Stmt__Group__4 )
            // InternalStructuredTextParser.g:5373:2: rule__Repeat_Stmt__Group__3__Impl rule__Repeat_Stmt__Group__4
            {
            pushFollow(FOLLOW_44);
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
    // InternalStructuredTextParser.g:5380:1: rule__Repeat_Stmt__Group__3__Impl : ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) ) ;
    public final void rule__Repeat_Stmt__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5384:1: ( ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) ) )
            // InternalStructuredTextParser.g:5385:1: ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:5385:1: ( ( rule__Repeat_Stmt__ExpressionAssignment_3 ) )
            // InternalStructuredTextParser.g:5386:2: ( rule__Repeat_Stmt__ExpressionAssignment_3 )
            {
             before(grammarAccess.getRepeat_StmtAccess().getExpressionAssignment_3()); 
            // InternalStructuredTextParser.g:5387:2: ( rule__Repeat_Stmt__ExpressionAssignment_3 )
            // InternalStructuredTextParser.g:5387:3: rule__Repeat_Stmt__ExpressionAssignment_3
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
    // InternalStructuredTextParser.g:5395:1: rule__Repeat_Stmt__Group__4 : rule__Repeat_Stmt__Group__4__Impl ;
    public final void rule__Repeat_Stmt__Group__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5399:1: ( rule__Repeat_Stmt__Group__4__Impl )
            // InternalStructuredTextParser.g:5400:2: rule__Repeat_Stmt__Group__4__Impl
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
    // InternalStructuredTextParser.g:5406:1: rule__Repeat_Stmt__Group__4__Impl : ( END_REPEAT ) ;
    public final void rule__Repeat_Stmt__Group__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5410:1: ( ( END_REPEAT ) )
            // InternalStructuredTextParser.g:5411:1: ( END_REPEAT )
            {
            // InternalStructuredTextParser.g:5411:1: ( END_REPEAT )
            // InternalStructuredTextParser.g:5412:2: END_REPEAT
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
    // InternalStructuredTextParser.g:5422:1: rule__Or_Expression__Group__0 : rule__Or_Expression__Group__0__Impl rule__Or_Expression__Group__1 ;
    public final void rule__Or_Expression__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5426:1: ( rule__Or_Expression__Group__0__Impl rule__Or_Expression__Group__1 )
            // InternalStructuredTextParser.g:5427:2: rule__Or_Expression__Group__0__Impl rule__Or_Expression__Group__1
            {
            pushFollow(FOLLOW_45);
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
    // InternalStructuredTextParser.g:5434:1: rule__Or_Expression__Group__0__Impl : ( ruleXor_Expr ) ;
    public final void rule__Or_Expression__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5438:1: ( ( ruleXor_Expr ) )
            // InternalStructuredTextParser.g:5439:1: ( ruleXor_Expr )
            {
            // InternalStructuredTextParser.g:5439:1: ( ruleXor_Expr )
            // InternalStructuredTextParser.g:5440:2: ruleXor_Expr
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
    // InternalStructuredTextParser.g:5449:1: rule__Or_Expression__Group__1 : rule__Or_Expression__Group__1__Impl ;
    public final void rule__Or_Expression__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5453:1: ( rule__Or_Expression__Group__1__Impl )
            // InternalStructuredTextParser.g:5454:2: rule__Or_Expression__Group__1__Impl
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
    // InternalStructuredTextParser.g:5460:1: rule__Or_Expression__Group__1__Impl : ( ( rule__Or_Expression__Group_1__0 )* ) ;
    public final void rule__Or_Expression__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5464:1: ( ( ( rule__Or_Expression__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5465:1: ( ( rule__Or_Expression__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5465:1: ( ( rule__Or_Expression__Group_1__0 )* )
            // InternalStructuredTextParser.g:5466:2: ( rule__Or_Expression__Group_1__0 )*
            {
             before(grammarAccess.getOr_ExpressionAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5467:2: ( rule__Or_Expression__Group_1__0 )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==OR) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5467:3: rule__Or_Expression__Group_1__0
            	    {
            	    pushFollow(FOLLOW_46);
            	    rule__Or_Expression__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop46;
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
    // InternalStructuredTextParser.g:5476:1: rule__Or_Expression__Group_1__0 : rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1 ;
    public final void rule__Or_Expression__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5480:1: ( rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1 )
            // InternalStructuredTextParser.g:5481:2: rule__Or_Expression__Group_1__0__Impl rule__Or_Expression__Group_1__1
            {
            pushFollow(FOLLOW_45);
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
    // InternalStructuredTextParser.g:5488:1: rule__Or_Expression__Group_1__0__Impl : ( () ) ;
    public final void rule__Or_Expression__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5492:1: ( ( () ) )
            // InternalStructuredTextParser.g:5493:1: ( () )
            {
            // InternalStructuredTextParser.g:5493:1: ( () )
            // InternalStructuredTextParser.g:5494:2: ()
            {
             before(grammarAccess.getOr_ExpressionAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5495:2: ()
            // InternalStructuredTextParser.g:5495:3: 
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
    // InternalStructuredTextParser.g:5503:1: rule__Or_Expression__Group_1__1 : rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2 ;
    public final void rule__Or_Expression__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5507:1: ( rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2 )
            // InternalStructuredTextParser.g:5508:2: rule__Or_Expression__Group_1__1__Impl rule__Or_Expression__Group_1__2
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
    // InternalStructuredTextParser.g:5515:1: rule__Or_Expression__Group_1__1__Impl : ( ( rule__Or_Expression__OperatorAssignment_1_1 ) ) ;
    public final void rule__Or_Expression__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5519:1: ( ( ( rule__Or_Expression__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5520:1: ( ( rule__Or_Expression__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5520:1: ( ( rule__Or_Expression__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5521:2: ( rule__Or_Expression__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getOr_ExpressionAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5522:2: ( rule__Or_Expression__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5522:3: rule__Or_Expression__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:5530:1: rule__Or_Expression__Group_1__2 : rule__Or_Expression__Group_1__2__Impl ;
    public final void rule__Or_Expression__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5534:1: ( rule__Or_Expression__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5535:2: rule__Or_Expression__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:5541:1: rule__Or_Expression__Group_1__2__Impl : ( ( rule__Or_Expression__RightAssignment_1_2 ) ) ;
    public final void rule__Or_Expression__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5545:1: ( ( ( rule__Or_Expression__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5546:1: ( ( rule__Or_Expression__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5546:1: ( ( rule__Or_Expression__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5547:2: ( rule__Or_Expression__RightAssignment_1_2 )
            {
             before(grammarAccess.getOr_ExpressionAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5548:2: ( rule__Or_Expression__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5548:3: rule__Or_Expression__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:5557:1: rule__Xor_Expr__Group__0 : rule__Xor_Expr__Group__0__Impl rule__Xor_Expr__Group__1 ;
    public final void rule__Xor_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5561:1: ( rule__Xor_Expr__Group__0__Impl rule__Xor_Expr__Group__1 )
            // InternalStructuredTextParser.g:5562:2: rule__Xor_Expr__Group__0__Impl rule__Xor_Expr__Group__1
            {
            pushFollow(FOLLOW_47);
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
    // InternalStructuredTextParser.g:5569:1: rule__Xor_Expr__Group__0__Impl : ( ruleAnd_Expr ) ;
    public final void rule__Xor_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5573:1: ( ( ruleAnd_Expr ) )
            // InternalStructuredTextParser.g:5574:1: ( ruleAnd_Expr )
            {
            // InternalStructuredTextParser.g:5574:1: ( ruleAnd_Expr )
            // InternalStructuredTextParser.g:5575:2: ruleAnd_Expr
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
    // InternalStructuredTextParser.g:5584:1: rule__Xor_Expr__Group__1 : rule__Xor_Expr__Group__1__Impl ;
    public final void rule__Xor_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5588:1: ( rule__Xor_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5589:2: rule__Xor_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:5595:1: rule__Xor_Expr__Group__1__Impl : ( ( rule__Xor_Expr__Group_1__0 )* ) ;
    public final void rule__Xor_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5599:1: ( ( ( rule__Xor_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5600:1: ( ( rule__Xor_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5600:1: ( ( rule__Xor_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5601:2: ( rule__Xor_Expr__Group_1__0 )*
            {
             before(grammarAccess.getXor_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5602:2: ( rule__Xor_Expr__Group_1__0 )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==XOR) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5602:3: rule__Xor_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_48);
            	    rule__Xor_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop47;
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
    // InternalStructuredTextParser.g:5611:1: rule__Xor_Expr__Group_1__0 : rule__Xor_Expr__Group_1__0__Impl rule__Xor_Expr__Group_1__1 ;
    public final void rule__Xor_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5615:1: ( rule__Xor_Expr__Group_1__0__Impl rule__Xor_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5616:2: rule__Xor_Expr__Group_1__0__Impl rule__Xor_Expr__Group_1__1
            {
            pushFollow(FOLLOW_47);
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
    // InternalStructuredTextParser.g:5623:1: rule__Xor_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Xor_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5627:1: ( ( () ) )
            // InternalStructuredTextParser.g:5628:1: ( () )
            {
            // InternalStructuredTextParser.g:5628:1: ( () )
            // InternalStructuredTextParser.g:5629:2: ()
            {
             before(grammarAccess.getXor_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5630:2: ()
            // InternalStructuredTextParser.g:5630:3: 
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
    // InternalStructuredTextParser.g:5638:1: rule__Xor_Expr__Group_1__1 : rule__Xor_Expr__Group_1__1__Impl rule__Xor_Expr__Group_1__2 ;
    public final void rule__Xor_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5642:1: ( rule__Xor_Expr__Group_1__1__Impl rule__Xor_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5643:2: rule__Xor_Expr__Group_1__1__Impl rule__Xor_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:5650:1: rule__Xor_Expr__Group_1__1__Impl : ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Xor_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5654:1: ( ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5655:1: ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5655:1: ( ( rule__Xor_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5656:2: ( rule__Xor_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getXor_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5657:2: ( rule__Xor_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5657:3: rule__Xor_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:5665:1: rule__Xor_Expr__Group_1__2 : rule__Xor_Expr__Group_1__2__Impl ;
    public final void rule__Xor_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5669:1: ( rule__Xor_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5670:2: rule__Xor_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:5676:1: rule__Xor_Expr__Group_1__2__Impl : ( ( rule__Xor_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Xor_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5680:1: ( ( ( rule__Xor_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5681:1: ( ( rule__Xor_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5681:1: ( ( rule__Xor_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5682:2: ( rule__Xor_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getXor_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5683:2: ( rule__Xor_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5683:3: rule__Xor_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:5692:1: rule__And_Expr__Group__0 : rule__And_Expr__Group__0__Impl rule__And_Expr__Group__1 ;
    public final void rule__And_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5696:1: ( rule__And_Expr__Group__0__Impl rule__And_Expr__Group__1 )
            // InternalStructuredTextParser.g:5697:2: rule__And_Expr__Group__0__Impl rule__And_Expr__Group__1
            {
            pushFollow(FOLLOW_49);
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
    // InternalStructuredTextParser.g:5704:1: rule__And_Expr__Group__0__Impl : ( ruleCompare_Expr ) ;
    public final void rule__And_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5708:1: ( ( ruleCompare_Expr ) )
            // InternalStructuredTextParser.g:5709:1: ( ruleCompare_Expr )
            {
            // InternalStructuredTextParser.g:5709:1: ( ruleCompare_Expr )
            // InternalStructuredTextParser.g:5710:2: ruleCompare_Expr
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
    // InternalStructuredTextParser.g:5719:1: rule__And_Expr__Group__1 : rule__And_Expr__Group__1__Impl ;
    public final void rule__And_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5723:1: ( rule__And_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5724:2: rule__And_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:5730:1: rule__And_Expr__Group__1__Impl : ( ( rule__And_Expr__Group_1__0 )* ) ;
    public final void rule__And_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5734:1: ( ( ( rule__And_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5735:1: ( ( rule__And_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5735:1: ( ( rule__And_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5736:2: ( rule__And_Expr__Group_1__0 )*
            {
             before(grammarAccess.getAnd_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5737:2: ( rule__And_Expr__Group_1__0 )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==AND||LA48_0==Ampersand) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5737:3: rule__And_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_50);
            	    rule__And_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop48;
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
    // InternalStructuredTextParser.g:5746:1: rule__And_Expr__Group_1__0 : rule__And_Expr__Group_1__0__Impl rule__And_Expr__Group_1__1 ;
    public final void rule__And_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5750:1: ( rule__And_Expr__Group_1__0__Impl rule__And_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5751:2: rule__And_Expr__Group_1__0__Impl rule__And_Expr__Group_1__1
            {
            pushFollow(FOLLOW_49);
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
    // InternalStructuredTextParser.g:5758:1: rule__And_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__And_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5762:1: ( ( () ) )
            // InternalStructuredTextParser.g:5763:1: ( () )
            {
            // InternalStructuredTextParser.g:5763:1: ( () )
            // InternalStructuredTextParser.g:5764:2: ()
            {
             before(grammarAccess.getAnd_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5765:2: ()
            // InternalStructuredTextParser.g:5765:3: 
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
    // InternalStructuredTextParser.g:5773:1: rule__And_Expr__Group_1__1 : rule__And_Expr__Group_1__1__Impl rule__And_Expr__Group_1__2 ;
    public final void rule__And_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5777:1: ( rule__And_Expr__Group_1__1__Impl rule__And_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5778:2: rule__And_Expr__Group_1__1__Impl rule__And_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:5785:1: rule__And_Expr__Group_1__1__Impl : ( ( rule__And_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__And_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5789:1: ( ( ( rule__And_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5790:1: ( ( rule__And_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5790:1: ( ( rule__And_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5791:2: ( rule__And_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getAnd_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5792:2: ( rule__And_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5792:3: rule__And_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:5800:1: rule__And_Expr__Group_1__2 : rule__And_Expr__Group_1__2__Impl ;
    public final void rule__And_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5804:1: ( rule__And_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5805:2: rule__And_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:5811:1: rule__And_Expr__Group_1__2__Impl : ( ( rule__And_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__And_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5815:1: ( ( ( rule__And_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5816:1: ( ( rule__And_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5816:1: ( ( rule__And_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5817:2: ( rule__And_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getAnd_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5818:2: ( rule__And_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5818:3: rule__And_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:5827:1: rule__Compare_Expr__Group__0 : rule__Compare_Expr__Group__0__Impl rule__Compare_Expr__Group__1 ;
    public final void rule__Compare_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5831:1: ( rule__Compare_Expr__Group__0__Impl rule__Compare_Expr__Group__1 )
            // InternalStructuredTextParser.g:5832:2: rule__Compare_Expr__Group__0__Impl rule__Compare_Expr__Group__1
            {
            pushFollow(FOLLOW_51);
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
    // InternalStructuredTextParser.g:5839:1: rule__Compare_Expr__Group__0__Impl : ( ruleEqu_Expr ) ;
    public final void rule__Compare_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5843:1: ( ( ruleEqu_Expr ) )
            // InternalStructuredTextParser.g:5844:1: ( ruleEqu_Expr )
            {
            // InternalStructuredTextParser.g:5844:1: ( ruleEqu_Expr )
            // InternalStructuredTextParser.g:5845:2: ruleEqu_Expr
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
    // InternalStructuredTextParser.g:5854:1: rule__Compare_Expr__Group__1 : rule__Compare_Expr__Group__1__Impl ;
    public final void rule__Compare_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5858:1: ( rule__Compare_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5859:2: rule__Compare_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:5865:1: rule__Compare_Expr__Group__1__Impl : ( ( rule__Compare_Expr__Group_1__0 )* ) ;
    public final void rule__Compare_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5869:1: ( ( ( rule__Compare_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:5870:1: ( ( rule__Compare_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:5870:1: ( ( rule__Compare_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:5871:2: ( rule__Compare_Expr__Group_1__0 )*
            {
             before(grammarAccess.getCompare_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:5872:2: ( rule__Compare_Expr__Group_1__0 )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==LessThanSignGreaterThanSign||LA49_0==EqualsSign) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // InternalStructuredTextParser.g:5872:3: rule__Compare_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_52);
            	    rule__Compare_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop49;
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
    // InternalStructuredTextParser.g:5881:1: rule__Compare_Expr__Group_1__0 : rule__Compare_Expr__Group_1__0__Impl rule__Compare_Expr__Group_1__1 ;
    public final void rule__Compare_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5885:1: ( rule__Compare_Expr__Group_1__0__Impl rule__Compare_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:5886:2: rule__Compare_Expr__Group_1__0__Impl rule__Compare_Expr__Group_1__1
            {
            pushFollow(FOLLOW_51);
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
    // InternalStructuredTextParser.g:5893:1: rule__Compare_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Compare_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5897:1: ( ( () ) )
            // InternalStructuredTextParser.g:5898:1: ( () )
            {
            // InternalStructuredTextParser.g:5898:1: ( () )
            // InternalStructuredTextParser.g:5899:2: ()
            {
             before(grammarAccess.getCompare_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:5900:2: ()
            // InternalStructuredTextParser.g:5900:3: 
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
    // InternalStructuredTextParser.g:5908:1: rule__Compare_Expr__Group_1__1 : rule__Compare_Expr__Group_1__1__Impl rule__Compare_Expr__Group_1__2 ;
    public final void rule__Compare_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5912:1: ( rule__Compare_Expr__Group_1__1__Impl rule__Compare_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:5913:2: rule__Compare_Expr__Group_1__1__Impl rule__Compare_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:5920:1: rule__Compare_Expr__Group_1__1__Impl : ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Compare_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5924:1: ( ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:5925:1: ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:5925:1: ( ( rule__Compare_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:5926:2: ( rule__Compare_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getCompare_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:5927:2: ( rule__Compare_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:5927:3: rule__Compare_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:5935:1: rule__Compare_Expr__Group_1__2 : rule__Compare_Expr__Group_1__2__Impl ;
    public final void rule__Compare_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5939:1: ( rule__Compare_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:5940:2: rule__Compare_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:5946:1: rule__Compare_Expr__Group_1__2__Impl : ( ( rule__Compare_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Compare_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5950:1: ( ( ( rule__Compare_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:5951:1: ( ( rule__Compare_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:5951:1: ( ( rule__Compare_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:5952:2: ( rule__Compare_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getCompare_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:5953:2: ( rule__Compare_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:5953:3: rule__Compare_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:5962:1: rule__Equ_Expr__Group__0 : rule__Equ_Expr__Group__0__Impl rule__Equ_Expr__Group__1 ;
    public final void rule__Equ_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5966:1: ( rule__Equ_Expr__Group__0__Impl rule__Equ_Expr__Group__1 )
            // InternalStructuredTextParser.g:5967:2: rule__Equ_Expr__Group__0__Impl rule__Equ_Expr__Group__1
            {
            pushFollow(FOLLOW_53);
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
    // InternalStructuredTextParser.g:5974:1: rule__Equ_Expr__Group__0__Impl : ( ruleAdd_Expr ) ;
    public final void rule__Equ_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5978:1: ( ( ruleAdd_Expr ) )
            // InternalStructuredTextParser.g:5979:1: ( ruleAdd_Expr )
            {
            // InternalStructuredTextParser.g:5979:1: ( ruleAdd_Expr )
            // InternalStructuredTextParser.g:5980:2: ruleAdd_Expr
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
    // InternalStructuredTextParser.g:5989:1: rule__Equ_Expr__Group__1 : rule__Equ_Expr__Group__1__Impl ;
    public final void rule__Equ_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:5993:1: ( rule__Equ_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:5994:2: rule__Equ_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:6000:1: rule__Equ_Expr__Group__1__Impl : ( ( rule__Equ_Expr__Group_1__0 )* ) ;
    public final void rule__Equ_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6004:1: ( ( ( rule__Equ_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:6005:1: ( ( rule__Equ_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:6005:1: ( ( rule__Equ_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:6006:2: ( rule__Equ_Expr__Group_1__0 )*
            {
             before(grammarAccess.getEqu_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:6007:2: ( rule__Equ_Expr__Group_1__0 )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==LessThanSignEqualsSign||LA50_0==GreaterThanSignEqualsSign||LA50_0==LessThanSign||LA50_0==GreaterThanSign) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // InternalStructuredTextParser.g:6007:3: rule__Equ_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_54);
            	    rule__Equ_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop50;
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
    // InternalStructuredTextParser.g:6016:1: rule__Equ_Expr__Group_1__0 : rule__Equ_Expr__Group_1__0__Impl rule__Equ_Expr__Group_1__1 ;
    public final void rule__Equ_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6020:1: ( rule__Equ_Expr__Group_1__0__Impl rule__Equ_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:6021:2: rule__Equ_Expr__Group_1__0__Impl rule__Equ_Expr__Group_1__1
            {
            pushFollow(FOLLOW_53);
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
    // InternalStructuredTextParser.g:6028:1: rule__Equ_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Equ_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6032:1: ( ( () ) )
            // InternalStructuredTextParser.g:6033:1: ( () )
            {
            // InternalStructuredTextParser.g:6033:1: ( () )
            // InternalStructuredTextParser.g:6034:2: ()
            {
             before(grammarAccess.getEqu_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:6035:2: ()
            // InternalStructuredTextParser.g:6035:3: 
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
    // InternalStructuredTextParser.g:6043:1: rule__Equ_Expr__Group_1__1 : rule__Equ_Expr__Group_1__1__Impl rule__Equ_Expr__Group_1__2 ;
    public final void rule__Equ_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6047:1: ( rule__Equ_Expr__Group_1__1__Impl rule__Equ_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:6048:2: rule__Equ_Expr__Group_1__1__Impl rule__Equ_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:6055:1: rule__Equ_Expr__Group_1__1__Impl : ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Equ_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6059:1: ( ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:6060:1: ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:6060:1: ( ( rule__Equ_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:6061:2: ( rule__Equ_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getEqu_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:6062:2: ( rule__Equ_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:6062:3: rule__Equ_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:6070:1: rule__Equ_Expr__Group_1__2 : rule__Equ_Expr__Group_1__2__Impl ;
    public final void rule__Equ_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6074:1: ( rule__Equ_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:6075:2: rule__Equ_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:6081:1: rule__Equ_Expr__Group_1__2__Impl : ( ( rule__Equ_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Equ_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6085:1: ( ( ( rule__Equ_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:6086:1: ( ( rule__Equ_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:6086:1: ( ( rule__Equ_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:6087:2: ( rule__Equ_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getEqu_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:6088:2: ( rule__Equ_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:6088:3: rule__Equ_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:6097:1: rule__Add_Expr__Group__0 : rule__Add_Expr__Group__0__Impl rule__Add_Expr__Group__1 ;
    public final void rule__Add_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6101:1: ( rule__Add_Expr__Group__0__Impl rule__Add_Expr__Group__1 )
            // InternalStructuredTextParser.g:6102:2: rule__Add_Expr__Group__0__Impl rule__Add_Expr__Group__1
            {
            pushFollow(FOLLOW_55);
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
    // InternalStructuredTextParser.g:6109:1: rule__Add_Expr__Group__0__Impl : ( ruleTerm ) ;
    public final void rule__Add_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6113:1: ( ( ruleTerm ) )
            // InternalStructuredTextParser.g:6114:1: ( ruleTerm )
            {
            // InternalStructuredTextParser.g:6114:1: ( ruleTerm )
            // InternalStructuredTextParser.g:6115:2: ruleTerm
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
    // InternalStructuredTextParser.g:6124:1: rule__Add_Expr__Group__1 : rule__Add_Expr__Group__1__Impl ;
    public final void rule__Add_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6128:1: ( rule__Add_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:6129:2: rule__Add_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:6135:1: rule__Add_Expr__Group__1__Impl : ( ( rule__Add_Expr__Group_1__0 )* ) ;
    public final void rule__Add_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6139:1: ( ( ( rule__Add_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:6140:1: ( ( rule__Add_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:6140:1: ( ( rule__Add_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:6141:2: ( rule__Add_Expr__Group_1__0 )*
            {
             before(grammarAccess.getAdd_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:6142:2: ( rule__Add_Expr__Group_1__0 )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==PlusSign||LA51_0==HyphenMinus) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // InternalStructuredTextParser.g:6142:3: rule__Add_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_56);
            	    rule__Add_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop51;
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
    // InternalStructuredTextParser.g:6151:1: rule__Add_Expr__Group_1__0 : rule__Add_Expr__Group_1__0__Impl rule__Add_Expr__Group_1__1 ;
    public final void rule__Add_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6155:1: ( rule__Add_Expr__Group_1__0__Impl rule__Add_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:6156:2: rule__Add_Expr__Group_1__0__Impl rule__Add_Expr__Group_1__1
            {
            pushFollow(FOLLOW_55);
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
    // InternalStructuredTextParser.g:6163:1: rule__Add_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Add_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6167:1: ( ( () ) )
            // InternalStructuredTextParser.g:6168:1: ( () )
            {
            // InternalStructuredTextParser.g:6168:1: ( () )
            // InternalStructuredTextParser.g:6169:2: ()
            {
             before(grammarAccess.getAdd_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:6170:2: ()
            // InternalStructuredTextParser.g:6170:3: 
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
    // InternalStructuredTextParser.g:6178:1: rule__Add_Expr__Group_1__1 : rule__Add_Expr__Group_1__1__Impl rule__Add_Expr__Group_1__2 ;
    public final void rule__Add_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6182:1: ( rule__Add_Expr__Group_1__1__Impl rule__Add_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:6183:2: rule__Add_Expr__Group_1__1__Impl rule__Add_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:6190:1: rule__Add_Expr__Group_1__1__Impl : ( ( rule__Add_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Add_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6194:1: ( ( ( rule__Add_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:6195:1: ( ( rule__Add_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:6195:1: ( ( rule__Add_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:6196:2: ( rule__Add_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getAdd_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:6197:2: ( rule__Add_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:6197:3: rule__Add_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:6205:1: rule__Add_Expr__Group_1__2 : rule__Add_Expr__Group_1__2__Impl ;
    public final void rule__Add_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6209:1: ( rule__Add_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:6210:2: rule__Add_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:6216:1: rule__Add_Expr__Group_1__2__Impl : ( ( rule__Add_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Add_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6220:1: ( ( ( rule__Add_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:6221:1: ( ( rule__Add_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:6221:1: ( ( rule__Add_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:6222:2: ( rule__Add_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getAdd_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:6223:2: ( rule__Add_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:6223:3: rule__Add_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:6232:1: rule__Term__Group__0 : rule__Term__Group__0__Impl rule__Term__Group__1 ;
    public final void rule__Term__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6236:1: ( rule__Term__Group__0__Impl rule__Term__Group__1 )
            // InternalStructuredTextParser.g:6237:2: rule__Term__Group__0__Impl rule__Term__Group__1
            {
            pushFollow(FOLLOW_57);
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
    // InternalStructuredTextParser.g:6244:1: rule__Term__Group__0__Impl : ( rulePower_Expr ) ;
    public final void rule__Term__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6248:1: ( ( rulePower_Expr ) )
            // InternalStructuredTextParser.g:6249:1: ( rulePower_Expr )
            {
            // InternalStructuredTextParser.g:6249:1: ( rulePower_Expr )
            // InternalStructuredTextParser.g:6250:2: rulePower_Expr
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
    // InternalStructuredTextParser.g:6259:1: rule__Term__Group__1 : rule__Term__Group__1__Impl ;
    public final void rule__Term__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6263:1: ( rule__Term__Group__1__Impl )
            // InternalStructuredTextParser.g:6264:2: rule__Term__Group__1__Impl
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
    // InternalStructuredTextParser.g:6270:1: rule__Term__Group__1__Impl : ( ( rule__Term__Group_1__0 )* ) ;
    public final void rule__Term__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6274:1: ( ( ( rule__Term__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:6275:1: ( ( rule__Term__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:6275:1: ( ( rule__Term__Group_1__0 )* )
            // InternalStructuredTextParser.g:6276:2: ( rule__Term__Group_1__0 )*
            {
             before(grammarAccess.getTermAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:6277:2: ( rule__Term__Group_1__0 )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==MOD||LA52_0==Asterisk||LA52_0==Solidus) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // InternalStructuredTextParser.g:6277:3: rule__Term__Group_1__0
            	    {
            	    pushFollow(FOLLOW_58);
            	    rule__Term__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop52;
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
    // InternalStructuredTextParser.g:6286:1: rule__Term__Group_1__0 : rule__Term__Group_1__0__Impl rule__Term__Group_1__1 ;
    public final void rule__Term__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6290:1: ( rule__Term__Group_1__0__Impl rule__Term__Group_1__1 )
            // InternalStructuredTextParser.g:6291:2: rule__Term__Group_1__0__Impl rule__Term__Group_1__1
            {
            pushFollow(FOLLOW_57);
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
    // InternalStructuredTextParser.g:6298:1: rule__Term__Group_1__0__Impl : ( () ) ;
    public final void rule__Term__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6302:1: ( ( () ) )
            // InternalStructuredTextParser.g:6303:1: ( () )
            {
            // InternalStructuredTextParser.g:6303:1: ( () )
            // InternalStructuredTextParser.g:6304:2: ()
            {
             before(grammarAccess.getTermAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:6305:2: ()
            // InternalStructuredTextParser.g:6305:3: 
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
    // InternalStructuredTextParser.g:6313:1: rule__Term__Group_1__1 : rule__Term__Group_1__1__Impl rule__Term__Group_1__2 ;
    public final void rule__Term__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6317:1: ( rule__Term__Group_1__1__Impl rule__Term__Group_1__2 )
            // InternalStructuredTextParser.g:6318:2: rule__Term__Group_1__1__Impl rule__Term__Group_1__2
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
    // InternalStructuredTextParser.g:6325:1: rule__Term__Group_1__1__Impl : ( ( rule__Term__OperatorAssignment_1_1 ) ) ;
    public final void rule__Term__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6329:1: ( ( ( rule__Term__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:6330:1: ( ( rule__Term__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:6330:1: ( ( rule__Term__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:6331:2: ( rule__Term__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getTermAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:6332:2: ( rule__Term__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:6332:3: rule__Term__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:6340:1: rule__Term__Group_1__2 : rule__Term__Group_1__2__Impl ;
    public final void rule__Term__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6344:1: ( rule__Term__Group_1__2__Impl )
            // InternalStructuredTextParser.g:6345:2: rule__Term__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:6351:1: rule__Term__Group_1__2__Impl : ( ( rule__Term__RightAssignment_1_2 ) ) ;
    public final void rule__Term__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6355:1: ( ( ( rule__Term__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:6356:1: ( ( rule__Term__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:6356:1: ( ( rule__Term__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:6357:2: ( rule__Term__RightAssignment_1_2 )
            {
             before(grammarAccess.getTermAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:6358:2: ( rule__Term__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:6358:3: rule__Term__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:6367:1: rule__Power_Expr__Group__0 : rule__Power_Expr__Group__0__Impl rule__Power_Expr__Group__1 ;
    public final void rule__Power_Expr__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6371:1: ( rule__Power_Expr__Group__0__Impl rule__Power_Expr__Group__1 )
            // InternalStructuredTextParser.g:6372:2: rule__Power_Expr__Group__0__Impl rule__Power_Expr__Group__1
            {
            pushFollow(FOLLOW_59);
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
    // InternalStructuredTextParser.g:6379:1: rule__Power_Expr__Group__0__Impl : ( ruleUnary_Expr ) ;
    public final void rule__Power_Expr__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6383:1: ( ( ruleUnary_Expr ) )
            // InternalStructuredTextParser.g:6384:1: ( ruleUnary_Expr )
            {
            // InternalStructuredTextParser.g:6384:1: ( ruleUnary_Expr )
            // InternalStructuredTextParser.g:6385:2: ruleUnary_Expr
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
    // InternalStructuredTextParser.g:6394:1: rule__Power_Expr__Group__1 : rule__Power_Expr__Group__1__Impl ;
    public final void rule__Power_Expr__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6398:1: ( rule__Power_Expr__Group__1__Impl )
            // InternalStructuredTextParser.g:6399:2: rule__Power_Expr__Group__1__Impl
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
    // InternalStructuredTextParser.g:6405:1: rule__Power_Expr__Group__1__Impl : ( ( rule__Power_Expr__Group_1__0 )* ) ;
    public final void rule__Power_Expr__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6409:1: ( ( ( rule__Power_Expr__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:6410:1: ( ( rule__Power_Expr__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:6410:1: ( ( rule__Power_Expr__Group_1__0 )* )
            // InternalStructuredTextParser.g:6411:2: ( rule__Power_Expr__Group_1__0 )*
            {
             before(grammarAccess.getPower_ExprAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:6412:2: ( rule__Power_Expr__Group_1__0 )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==AsteriskAsterisk) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // InternalStructuredTextParser.g:6412:3: rule__Power_Expr__Group_1__0
            	    {
            	    pushFollow(FOLLOW_60);
            	    rule__Power_Expr__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop53;
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
    // InternalStructuredTextParser.g:6421:1: rule__Power_Expr__Group_1__0 : rule__Power_Expr__Group_1__0__Impl rule__Power_Expr__Group_1__1 ;
    public final void rule__Power_Expr__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6425:1: ( rule__Power_Expr__Group_1__0__Impl rule__Power_Expr__Group_1__1 )
            // InternalStructuredTextParser.g:6426:2: rule__Power_Expr__Group_1__0__Impl rule__Power_Expr__Group_1__1
            {
            pushFollow(FOLLOW_59);
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
    // InternalStructuredTextParser.g:6433:1: rule__Power_Expr__Group_1__0__Impl : ( () ) ;
    public final void rule__Power_Expr__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6437:1: ( ( () ) )
            // InternalStructuredTextParser.g:6438:1: ( () )
            {
            // InternalStructuredTextParser.g:6438:1: ( () )
            // InternalStructuredTextParser.g:6439:2: ()
            {
             before(grammarAccess.getPower_ExprAccess().getBinaryExpressionLeftAction_1_0()); 
            // InternalStructuredTextParser.g:6440:2: ()
            // InternalStructuredTextParser.g:6440:3: 
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
    // InternalStructuredTextParser.g:6448:1: rule__Power_Expr__Group_1__1 : rule__Power_Expr__Group_1__1__Impl rule__Power_Expr__Group_1__2 ;
    public final void rule__Power_Expr__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6452:1: ( rule__Power_Expr__Group_1__1__Impl rule__Power_Expr__Group_1__2 )
            // InternalStructuredTextParser.g:6453:2: rule__Power_Expr__Group_1__1__Impl rule__Power_Expr__Group_1__2
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
    // InternalStructuredTextParser.g:6460:1: rule__Power_Expr__Group_1__1__Impl : ( ( rule__Power_Expr__OperatorAssignment_1_1 ) ) ;
    public final void rule__Power_Expr__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6464:1: ( ( ( rule__Power_Expr__OperatorAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:6465:1: ( ( rule__Power_Expr__OperatorAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:6465:1: ( ( rule__Power_Expr__OperatorAssignment_1_1 ) )
            // InternalStructuredTextParser.g:6466:2: ( rule__Power_Expr__OperatorAssignment_1_1 )
            {
             before(grammarAccess.getPower_ExprAccess().getOperatorAssignment_1_1()); 
            // InternalStructuredTextParser.g:6467:2: ( rule__Power_Expr__OperatorAssignment_1_1 )
            // InternalStructuredTextParser.g:6467:3: rule__Power_Expr__OperatorAssignment_1_1
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
    // InternalStructuredTextParser.g:6475:1: rule__Power_Expr__Group_1__2 : rule__Power_Expr__Group_1__2__Impl ;
    public final void rule__Power_Expr__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6479:1: ( rule__Power_Expr__Group_1__2__Impl )
            // InternalStructuredTextParser.g:6480:2: rule__Power_Expr__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:6486:1: rule__Power_Expr__Group_1__2__Impl : ( ( rule__Power_Expr__RightAssignment_1_2 ) ) ;
    public final void rule__Power_Expr__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6490:1: ( ( ( rule__Power_Expr__RightAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:6491:1: ( ( rule__Power_Expr__RightAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:6491:1: ( ( rule__Power_Expr__RightAssignment_1_2 ) )
            // InternalStructuredTextParser.g:6492:2: ( rule__Power_Expr__RightAssignment_1_2 )
            {
             before(grammarAccess.getPower_ExprAccess().getRightAssignment_1_2()); 
            // InternalStructuredTextParser.g:6493:2: ( rule__Power_Expr__RightAssignment_1_2 )
            // InternalStructuredTextParser.g:6493:3: rule__Power_Expr__RightAssignment_1_2
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
    // InternalStructuredTextParser.g:6502:1: rule__Unary_Expr__Group_0__0 : rule__Unary_Expr__Group_0__0__Impl rule__Unary_Expr__Group_0__1 ;
    public final void rule__Unary_Expr__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6506:1: ( rule__Unary_Expr__Group_0__0__Impl rule__Unary_Expr__Group_0__1 )
            // InternalStructuredTextParser.g:6507:2: rule__Unary_Expr__Group_0__0__Impl rule__Unary_Expr__Group_0__1
            {
            pushFollow(FOLLOW_61);
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
    // InternalStructuredTextParser.g:6514:1: rule__Unary_Expr__Group_0__0__Impl : ( () ) ;
    public final void rule__Unary_Expr__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6518:1: ( ( () ) )
            // InternalStructuredTextParser.g:6519:1: ( () )
            {
            // InternalStructuredTextParser.g:6519:1: ( () )
            // InternalStructuredTextParser.g:6520:2: ()
            {
             before(grammarAccess.getUnary_ExprAccess().getUnaryExpressionAction_0_0()); 
            // InternalStructuredTextParser.g:6521:2: ()
            // InternalStructuredTextParser.g:6521:3: 
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
    // InternalStructuredTextParser.g:6529:1: rule__Unary_Expr__Group_0__1 : rule__Unary_Expr__Group_0__1__Impl rule__Unary_Expr__Group_0__2 ;
    public final void rule__Unary_Expr__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6533:1: ( rule__Unary_Expr__Group_0__1__Impl rule__Unary_Expr__Group_0__2 )
            // InternalStructuredTextParser.g:6534:2: rule__Unary_Expr__Group_0__1__Impl rule__Unary_Expr__Group_0__2
            {
            pushFollow(FOLLOW_62);
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
    // InternalStructuredTextParser.g:6541:1: rule__Unary_Expr__Group_0__1__Impl : ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) ) ;
    public final void rule__Unary_Expr__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6545:1: ( ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) ) )
            // InternalStructuredTextParser.g:6546:1: ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) )
            {
            // InternalStructuredTextParser.g:6546:1: ( ( rule__Unary_Expr__OperatorAssignment_0_1 ) )
            // InternalStructuredTextParser.g:6547:2: ( rule__Unary_Expr__OperatorAssignment_0_1 )
            {
             before(grammarAccess.getUnary_ExprAccess().getOperatorAssignment_0_1()); 
            // InternalStructuredTextParser.g:6548:2: ( rule__Unary_Expr__OperatorAssignment_0_1 )
            // InternalStructuredTextParser.g:6548:3: rule__Unary_Expr__OperatorAssignment_0_1
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
    // InternalStructuredTextParser.g:6556:1: rule__Unary_Expr__Group_0__2 : rule__Unary_Expr__Group_0__2__Impl ;
    public final void rule__Unary_Expr__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6560:1: ( rule__Unary_Expr__Group_0__2__Impl )
            // InternalStructuredTextParser.g:6561:2: rule__Unary_Expr__Group_0__2__Impl
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
    // InternalStructuredTextParser.g:6567:1: rule__Unary_Expr__Group_0__2__Impl : ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) ) ;
    public final void rule__Unary_Expr__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6571:1: ( ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) ) )
            // InternalStructuredTextParser.g:6572:1: ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) )
            {
            // InternalStructuredTextParser.g:6572:1: ( ( rule__Unary_Expr__ExpressionAssignment_0_2 ) )
            // InternalStructuredTextParser.g:6573:2: ( rule__Unary_Expr__ExpressionAssignment_0_2 )
            {
             before(grammarAccess.getUnary_ExprAccess().getExpressionAssignment_0_2()); 
            // InternalStructuredTextParser.g:6574:2: ( rule__Unary_Expr__ExpressionAssignment_0_2 )
            // InternalStructuredTextParser.g:6574:3: rule__Unary_Expr__ExpressionAssignment_0_2
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
    // InternalStructuredTextParser.g:6583:1: rule__Primary_Expr__Group_2__0 : rule__Primary_Expr__Group_2__0__Impl rule__Primary_Expr__Group_2__1 ;
    public final void rule__Primary_Expr__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6587:1: ( rule__Primary_Expr__Group_2__0__Impl rule__Primary_Expr__Group_2__1 )
            // InternalStructuredTextParser.g:6588:2: rule__Primary_Expr__Group_2__0__Impl rule__Primary_Expr__Group_2__1
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
    // InternalStructuredTextParser.g:6595:1: rule__Primary_Expr__Group_2__0__Impl : ( LeftParenthesis ) ;
    public final void rule__Primary_Expr__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6599:1: ( ( LeftParenthesis ) )
            // InternalStructuredTextParser.g:6600:1: ( LeftParenthesis )
            {
            // InternalStructuredTextParser.g:6600:1: ( LeftParenthesis )
            // InternalStructuredTextParser.g:6601:2: LeftParenthesis
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
    // InternalStructuredTextParser.g:6610:1: rule__Primary_Expr__Group_2__1 : rule__Primary_Expr__Group_2__1__Impl rule__Primary_Expr__Group_2__2 ;
    public final void rule__Primary_Expr__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6614:1: ( rule__Primary_Expr__Group_2__1__Impl rule__Primary_Expr__Group_2__2 )
            // InternalStructuredTextParser.g:6615:2: rule__Primary_Expr__Group_2__1__Impl rule__Primary_Expr__Group_2__2
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
    // InternalStructuredTextParser.g:6622:1: rule__Primary_Expr__Group_2__1__Impl : ( ruleExpression ) ;
    public final void rule__Primary_Expr__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6626:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:6627:1: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:6627:1: ( ruleExpression )
            // InternalStructuredTextParser.g:6628:2: ruleExpression
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
    // InternalStructuredTextParser.g:6637:1: rule__Primary_Expr__Group_2__2 : rule__Primary_Expr__Group_2__2__Impl ;
    public final void rule__Primary_Expr__Group_2__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6641:1: ( rule__Primary_Expr__Group_2__2__Impl )
            // InternalStructuredTextParser.g:6642:2: rule__Primary_Expr__Group_2__2__Impl
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
    // InternalStructuredTextParser.g:6648:1: rule__Primary_Expr__Group_2__2__Impl : ( RightParenthesis ) ;
    public final void rule__Primary_Expr__Group_2__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6652:1: ( ( RightParenthesis ) )
            // InternalStructuredTextParser.g:6653:1: ( RightParenthesis )
            {
            // InternalStructuredTextParser.g:6653:1: ( RightParenthesis )
            // InternalStructuredTextParser.g:6654:2: RightParenthesis
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
    // InternalStructuredTextParser.g:6664:1: rule__Func_Call__Group__0 : rule__Func_Call__Group__0__Impl rule__Func_Call__Group__1 ;
    public final void rule__Func_Call__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6668:1: ( rule__Func_Call__Group__0__Impl rule__Func_Call__Group__1 )
            // InternalStructuredTextParser.g:6669:2: rule__Func_Call__Group__0__Impl rule__Func_Call__Group__1
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
    // InternalStructuredTextParser.g:6676:1: rule__Func_Call__Group__0__Impl : ( ( rule__Func_Call__FuncAssignment_0 ) ) ;
    public final void rule__Func_Call__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6680:1: ( ( ( rule__Func_Call__FuncAssignment_0 ) ) )
            // InternalStructuredTextParser.g:6681:1: ( ( rule__Func_Call__FuncAssignment_0 ) )
            {
            // InternalStructuredTextParser.g:6681:1: ( ( rule__Func_Call__FuncAssignment_0 ) )
            // InternalStructuredTextParser.g:6682:2: ( rule__Func_Call__FuncAssignment_0 )
            {
             before(grammarAccess.getFunc_CallAccess().getFuncAssignment_0()); 
            // InternalStructuredTextParser.g:6683:2: ( rule__Func_Call__FuncAssignment_0 )
            // InternalStructuredTextParser.g:6683:3: rule__Func_Call__FuncAssignment_0
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
    // InternalStructuredTextParser.g:6691:1: rule__Func_Call__Group__1 : rule__Func_Call__Group__1__Impl rule__Func_Call__Group__2 ;
    public final void rule__Func_Call__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6695:1: ( rule__Func_Call__Group__1__Impl rule__Func_Call__Group__2 )
            // InternalStructuredTextParser.g:6696:2: rule__Func_Call__Group__1__Impl rule__Func_Call__Group__2
            {
            pushFollow(FOLLOW_27);
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
    // InternalStructuredTextParser.g:6703:1: rule__Func_Call__Group__1__Impl : ( LeftParenthesis ) ;
    public final void rule__Func_Call__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6707:1: ( ( LeftParenthesis ) )
            // InternalStructuredTextParser.g:6708:1: ( LeftParenthesis )
            {
            // InternalStructuredTextParser.g:6708:1: ( LeftParenthesis )
            // InternalStructuredTextParser.g:6709:2: LeftParenthesis
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
    // InternalStructuredTextParser.g:6718:1: rule__Func_Call__Group__2 : rule__Func_Call__Group__2__Impl rule__Func_Call__Group__3 ;
    public final void rule__Func_Call__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6722:1: ( rule__Func_Call__Group__2__Impl rule__Func_Call__Group__3 )
            // InternalStructuredTextParser.g:6723:2: rule__Func_Call__Group__2__Impl rule__Func_Call__Group__3
            {
            pushFollow(FOLLOW_27);
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
    // InternalStructuredTextParser.g:6730:1: rule__Func_Call__Group__2__Impl : ( ( rule__Func_Call__Group_2__0 )? ) ;
    public final void rule__Func_Call__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6734:1: ( ( ( rule__Func_Call__Group_2__0 )? ) )
            // InternalStructuredTextParser.g:6735:1: ( ( rule__Func_Call__Group_2__0 )? )
            {
            // InternalStructuredTextParser.g:6735:1: ( ( rule__Func_Call__Group_2__0 )? )
            // InternalStructuredTextParser.g:6736:2: ( rule__Func_Call__Group_2__0 )?
            {
             before(grammarAccess.getFunc_CallAccess().getGroup_2()); 
            // InternalStructuredTextParser.g:6737:2: ( rule__Func_Call__Group_2__0 )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==WSTRING||LA54_0==STRING||LA54_0==FALSE||LA54_0==LREAL||(LA54_0>=UDINT && LA54_0<=ULINT)||(LA54_0>=USINT && LA54_0<=WCHAR)||LA54_0==BOOL||LA54_0==CHAR||LA54_0==DINT||LA54_0==LINT||(LA54_0>=REAL && LA54_0<=SINT)||(LA54_0>=TIME && LA54_0<=UINT)||LA54_0==INT||LA54_0==NOT||LA54_0==DT||LA54_0==LT||LA54_0==LeftParenthesis||LA54_0==PlusSign||LA54_0==HyphenMinus||LA54_0==T||(LA54_0>=RULE_UNSIGNED_INT && LA54_0<=RULE_HEX_INT)||LA54_0==RULE_S_BYTE_CHAR_STR||LA54_0==RULE_D_BYTE_CHAR_STR) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // InternalStructuredTextParser.g:6737:3: rule__Func_Call__Group_2__0
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
    // InternalStructuredTextParser.g:6745:1: rule__Func_Call__Group__3 : rule__Func_Call__Group__3__Impl ;
    public final void rule__Func_Call__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6749:1: ( rule__Func_Call__Group__3__Impl )
            // InternalStructuredTextParser.g:6750:2: rule__Func_Call__Group__3__Impl
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
    // InternalStructuredTextParser.g:6756:1: rule__Func_Call__Group__3__Impl : ( RightParenthesis ) ;
    public final void rule__Func_Call__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6760:1: ( ( RightParenthesis ) )
            // InternalStructuredTextParser.g:6761:1: ( RightParenthesis )
            {
            // InternalStructuredTextParser.g:6761:1: ( RightParenthesis )
            // InternalStructuredTextParser.g:6762:2: RightParenthesis
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
    // InternalStructuredTextParser.g:6772:1: rule__Func_Call__Group_2__0 : rule__Func_Call__Group_2__0__Impl rule__Func_Call__Group_2__1 ;
    public final void rule__Func_Call__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6776:1: ( rule__Func_Call__Group_2__0__Impl rule__Func_Call__Group_2__1 )
            // InternalStructuredTextParser.g:6777:2: rule__Func_Call__Group_2__0__Impl rule__Func_Call__Group_2__1
            {
            pushFollow(FOLLOW_28);
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
    // InternalStructuredTextParser.g:6784:1: rule__Func_Call__Group_2__0__Impl : ( ( rule__Func_Call__ArgsAssignment_2_0 ) ) ;
    public final void rule__Func_Call__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6788:1: ( ( ( rule__Func_Call__ArgsAssignment_2_0 ) ) )
            // InternalStructuredTextParser.g:6789:1: ( ( rule__Func_Call__ArgsAssignment_2_0 ) )
            {
            // InternalStructuredTextParser.g:6789:1: ( ( rule__Func_Call__ArgsAssignment_2_0 ) )
            // InternalStructuredTextParser.g:6790:2: ( rule__Func_Call__ArgsAssignment_2_0 )
            {
             before(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_0()); 
            // InternalStructuredTextParser.g:6791:2: ( rule__Func_Call__ArgsAssignment_2_0 )
            // InternalStructuredTextParser.g:6791:3: rule__Func_Call__ArgsAssignment_2_0
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
    // InternalStructuredTextParser.g:6799:1: rule__Func_Call__Group_2__1 : rule__Func_Call__Group_2__1__Impl ;
    public final void rule__Func_Call__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6803:1: ( rule__Func_Call__Group_2__1__Impl )
            // InternalStructuredTextParser.g:6804:2: rule__Func_Call__Group_2__1__Impl
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
    // InternalStructuredTextParser.g:6810:1: rule__Func_Call__Group_2__1__Impl : ( ( rule__Func_Call__Group_2_1__0 )* ) ;
    public final void rule__Func_Call__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6814:1: ( ( ( rule__Func_Call__Group_2_1__0 )* ) )
            // InternalStructuredTextParser.g:6815:1: ( ( rule__Func_Call__Group_2_1__0 )* )
            {
            // InternalStructuredTextParser.g:6815:1: ( ( rule__Func_Call__Group_2_1__0 )* )
            // InternalStructuredTextParser.g:6816:2: ( rule__Func_Call__Group_2_1__0 )*
            {
             before(grammarAccess.getFunc_CallAccess().getGroup_2_1()); 
            // InternalStructuredTextParser.g:6817:2: ( rule__Func_Call__Group_2_1__0 )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==Comma) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // InternalStructuredTextParser.g:6817:3: rule__Func_Call__Group_2_1__0
            	    {
            	    pushFollow(FOLLOW_29);
            	    rule__Func_Call__Group_2_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop55;
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
    // InternalStructuredTextParser.g:6826:1: rule__Func_Call__Group_2_1__0 : rule__Func_Call__Group_2_1__0__Impl rule__Func_Call__Group_2_1__1 ;
    public final void rule__Func_Call__Group_2_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6830:1: ( rule__Func_Call__Group_2_1__0__Impl rule__Func_Call__Group_2_1__1 )
            // InternalStructuredTextParser.g:6831:2: rule__Func_Call__Group_2_1__0__Impl rule__Func_Call__Group_2_1__1
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
    // InternalStructuredTextParser.g:6838:1: rule__Func_Call__Group_2_1__0__Impl : ( Comma ) ;
    public final void rule__Func_Call__Group_2_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6842:1: ( ( Comma ) )
            // InternalStructuredTextParser.g:6843:1: ( Comma )
            {
            // InternalStructuredTextParser.g:6843:1: ( Comma )
            // InternalStructuredTextParser.g:6844:2: Comma
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
    // InternalStructuredTextParser.g:6853:1: rule__Func_Call__Group_2_1__1 : rule__Func_Call__Group_2_1__1__Impl ;
    public final void rule__Func_Call__Group_2_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6857:1: ( rule__Func_Call__Group_2_1__1__Impl )
            // InternalStructuredTextParser.g:6858:2: rule__Func_Call__Group_2_1__1__Impl
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
    // InternalStructuredTextParser.g:6864:1: rule__Func_Call__Group_2_1__1__Impl : ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) ) ;
    public final void rule__Func_Call__Group_2_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6868:1: ( ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) ) )
            // InternalStructuredTextParser.g:6869:1: ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) )
            {
            // InternalStructuredTextParser.g:6869:1: ( ( rule__Func_Call__ArgsAssignment_2_1_1 ) )
            // InternalStructuredTextParser.g:6870:2: ( rule__Func_Call__ArgsAssignment_2_1_1 )
            {
             before(grammarAccess.getFunc_CallAccess().getArgsAssignment_2_1_1()); 
            // InternalStructuredTextParser.g:6871:2: ( rule__Func_Call__ArgsAssignment_2_1_1 )
            // InternalStructuredTextParser.g:6871:3: rule__Func_Call__ArgsAssignment_2_1_1
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
    // InternalStructuredTextParser.g:6880:1: rule__Param_Assign_In__Group__0 : rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1 ;
    public final void rule__Param_Assign_In__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6884:1: ( rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1 )
            // InternalStructuredTextParser.g:6885:2: rule__Param_Assign_In__Group__0__Impl rule__Param_Assign_In__Group__1
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
    // InternalStructuredTextParser.g:6892:1: rule__Param_Assign_In__Group__0__Impl : ( ( rule__Param_Assign_In__Group_0__0 )? ) ;
    public final void rule__Param_Assign_In__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6896:1: ( ( ( rule__Param_Assign_In__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:6897:1: ( ( rule__Param_Assign_In__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:6897:1: ( ( rule__Param_Assign_In__Group_0__0 )? )
            // InternalStructuredTextParser.g:6898:2: ( rule__Param_Assign_In__Group_0__0 )?
            {
             before(grammarAccess.getParam_Assign_InAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:6899:2: ( rule__Param_Assign_In__Group_0__0 )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==RULE_ID) ) {
                int LA56_1 = input.LA(2);

                if ( (LA56_1==ColonEqualsSign) ) {
                    alt56=1;
                }
            }
            switch (alt56) {
                case 1 :
                    // InternalStructuredTextParser.g:6899:3: rule__Param_Assign_In__Group_0__0
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
    // InternalStructuredTextParser.g:6907:1: rule__Param_Assign_In__Group__1 : rule__Param_Assign_In__Group__1__Impl ;
    public final void rule__Param_Assign_In__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6911:1: ( rule__Param_Assign_In__Group__1__Impl )
            // InternalStructuredTextParser.g:6912:2: rule__Param_Assign_In__Group__1__Impl
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
    // InternalStructuredTextParser.g:6918:1: rule__Param_Assign_In__Group__1__Impl : ( ( rule__Param_Assign_In__ExprAssignment_1 ) ) ;
    public final void rule__Param_Assign_In__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6922:1: ( ( ( rule__Param_Assign_In__ExprAssignment_1 ) ) )
            // InternalStructuredTextParser.g:6923:1: ( ( rule__Param_Assign_In__ExprAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:6923:1: ( ( rule__Param_Assign_In__ExprAssignment_1 ) )
            // InternalStructuredTextParser.g:6924:2: ( rule__Param_Assign_In__ExprAssignment_1 )
            {
             before(grammarAccess.getParam_Assign_InAccess().getExprAssignment_1()); 
            // InternalStructuredTextParser.g:6925:2: ( rule__Param_Assign_In__ExprAssignment_1 )
            // InternalStructuredTextParser.g:6925:3: rule__Param_Assign_In__ExprAssignment_1
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
    // InternalStructuredTextParser.g:6934:1: rule__Param_Assign_In__Group_0__0 : rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1 ;
    public final void rule__Param_Assign_In__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6938:1: ( rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1 )
            // InternalStructuredTextParser.g:6939:2: rule__Param_Assign_In__Group_0__0__Impl rule__Param_Assign_In__Group_0__1
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
    // InternalStructuredTextParser.g:6946:1: rule__Param_Assign_In__Group_0__0__Impl : ( ( rule__Param_Assign_In__VarAssignment_0_0 ) ) ;
    public final void rule__Param_Assign_In__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6950:1: ( ( ( rule__Param_Assign_In__VarAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:6951:1: ( ( rule__Param_Assign_In__VarAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:6951:1: ( ( rule__Param_Assign_In__VarAssignment_0_0 ) )
            // InternalStructuredTextParser.g:6952:2: ( rule__Param_Assign_In__VarAssignment_0_0 )
            {
             before(grammarAccess.getParam_Assign_InAccess().getVarAssignment_0_0()); 
            // InternalStructuredTextParser.g:6953:2: ( rule__Param_Assign_In__VarAssignment_0_0 )
            // InternalStructuredTextParser.g:6953:3: rule__Param_Assign_In__VarAssignment_0_0
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
    // InternalStructuredTextParser.g:6961:1: rule__Param_Assign_In__Group_0__1 : rule__Param_Assign_In__Group_0__1__Impl ;
    public final void rule__Param_Assign_In__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6965:1: ( rule__Param_Assign_In__Group_0__1__Impl )
            // InternalStructuredTextParser.g:6966:2: rule__Param_Assign_In__Group_0__1__Impl
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
    // InternalStructuredTextParser.g:6972:1: rule__Param_Assign_In__Group_0__1__Impl : ( ColonEqualsSign ) ;
    public final void rule__Param_Assign_In__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6976:1: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:6977:1: ( ColonEqualsSign )
            {
            // InternalStructuredTextParser.g:6977:1: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:6978:2: ColonEqualsSign
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
    // InternalStructuredTextParser.g:6988:1: rule__Param_Assign_Out__Group__0 : rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1 ;
    public final void rule__Param_Assign_Out__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:6992:1: ( rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1 )
            // InternalStructuredTextParser.g:6993:2: rule__Param_Assign_Out__Group__0__Impl rule__Param_Assign_Out__Group__1
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
    // InternalStructuredTextParser.g:7000:1: rule__Param_Assign_Out__Group__0__Impl : ( ( rule__Param_Assign_Out__NotAssignment_0 )? ) ;
    public final void rule__Param_Assign_Out__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7004:1: ( ( ( rule__Param_Assign_Out__NotAssignment_0 )? ) )
            // InternalStructuredTextParser.g:7005:1: ( ( rule__Param_Assign_Out__NotAssignment_0 )? )
            {
            // InternalStructuredTextParser.g:7005:1: ( ( rule__Param_Assign_Out__NotAssignment_0 )? )
            // InternalStructuredTextParser.g:7006:2: ( rule__Param_Assign_Out__NotAssignment_0 )?
            {
             before(grammarAccess.getParam_Assign_OutAccess().getNotAssignment_0()); 
            // InternalStructuredTextParser.g:7007:2: ( rule__Param_Assign_Out__NotAssignment_0 )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==NOT) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalStructuredTextParser.g:7007:3: rule__Param_Assign_Out__NotAssignment_0
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
    // InternalStructuredTextParser.g:7015:1: rule__Param_Assign_Out__Group__1 : rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2 ;
    public final void rule__Param_Assign_Out__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7019:1: ( rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2 )
            // InternalStructuredTextParser.g:7020:2: rule__Param_Assign_Out__Group__1__Impl rule__Param_Assign_Out__Group__2
            {
            pushFollow(FOLLOW_63);
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
    // InternalStructuredTextParser.g:7027:1: rule__Param_Assign_Out__Group__1__Impl : ( ( rule__Param_Assign_Out__VarAssignment_1 ) ) ;
    public final void rule__Param_Assign_Out__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7031:1: ( ( ( rule__Param_Assign_Out__VarAssignment_1 ) ) )
            // InternalStructuredTextParser.g:7032:1: ( ( rule__Param_Assign_Out__VarAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:7032:1: ( ( rule__Param_Assign_Out__VarAssignment_1 ) )
            // InternalStructuredTextParser.g:7033:2: ( rule__Param_Assign_Out__VarAssignment_1 )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getVarAssignment_1()); 
            // InternalStructuredTextParser.g:7034:2: ( rule__Param_Assign_Out__VarAssignment_1 )
            // InternalStructuredTextParser.g:7034:3: rule__Param_Assign_Out__VarAssignment_1
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
    // InternalStructuredTextParser.g:7042:1: rule__Param_Assign_Out__Group__2 : rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3 ;
    public final void rule__Param_Assign_Out__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7046:1: ( rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3 )
            // InternalStructuredTextParser.g:7047:2: rule__Param_Assign_Out__Group__2__Impl rule__Param_Assign_Out__Group__3
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
    // InternalStructuredTextParser.g:7054:1: rule__Param_Assign_Out__Group__2__Impl : ( EqualsSignGreaterThanSign ) ;
    public final void rule__Param_Assign_Out__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7058:1: ( ( EqualsSignGreaterThanSign ) )
            // InternalStructuredTextParser.g:7059:1: ( EqualsSignGreaterThanSign )
            {
            // InternalStructuredTextParser.g:7059:1: ( EqualsSignGreaterThanSign )
            // InternalStructuredTextParser.g:7060:2: EqualsSignGreaterThanSign
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
    // InternalStructuredTextParser.g:7069:1: rule__Param_Assign_Out__Group__3 : rule__Param_Assign_Out__Group__3__Impl ;
    public final void rule__Param_Assign_Out__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7073:1: ( rule__Param_Assign_Out__Group__3__Impl )
            // InternalStructuredTextParser.g:7074:2: rule__Param_Assign_Out__Group__3__Impl
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
    // InternalStructuredTextParser.g:7080:1: rule__Param_Assign_Out__Group__3__Impl : ( ( rule__Param_Assign_Out__ExprAssignment_3 ) ) ;
    public final void rule__Param_Assign_Out__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7084:1: ( ( ( rule__Param_Assign_Out__ExprAssignment_3 ) ) )
            // InternalStructuredTextParser.g:7085:1: ( ( rule__Param_Assign_Out__ExprAssignment_3 ) )
            {
            // InternalStructuredTextParser.g:7085:1: ( ( rule__Param_Assign_Out__ExprAssignment_3 ) )
            // InternalStructuredTextParser.g:7086:2: ( rule__Param_Assign_Out__ExprAssignment_3 )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getExprAssignment_3()); 
            // InternalStructuredTextParser.g:7087:2: ( rule__Param_Assign_Out__ExprAssignment_3 )
            // InternalStructuredTextParser.g:7087:3: rule__Param_Assign_Out__ExprAssignment_3
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
    // InternalStructuredTextParser.g:7096:1: rule__Variable__Group__0 : rule__Variable__Group__0__Impl rule__Variable__Group__1 ;
    public final void rule__Variable__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7100:1: ( rule__Variable__Group__0__Impl rule__Variable__Group__1 )
            // InternalStructuredTextParser.g:7101:2: rule__Variable__Group__0__Impl rule__Variable__Group__1
            {
            pushFollow(FOLLOW_64);
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
    // InternalStructuredTextParser.g:7108:1: rule__Variable__Group__0__Impl : ( ruleVariable_Subscript ) ;
    public final void rule__Variable__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7112:1: ( ( ruleVariable_Subscript ) )
            // InternalStructuredTextParser.g:7113:1: ( ruleVariable_Subscript )
            {
            // InternalStructuredTextParser.g:7113:1: ( ruleVariable_Subscript )
            // InternalStructuredTextParser.g:7114:2: ruleVariable_Subscript
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
    // InternalStructuredTextParser.g:7123:1: rule__Variable__Group__1 : rule__Variable__Group__1__Impl ;
    public final void rule__Variable__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7127:1: ( rule__Variable__Group__1__Impl )
            // InternalStructuredTextParser.g:7128:2: rule__Variable__Group__1__Impl
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
    // InternalStructuredTextParser.g:7134:1: rule__Variable__Group__1__Impl : ( ( rule__Variable__PartAssignment_1 )? ) ;
    public final void rule__Variable__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7138:1: ( ( ( rule__Variable__PartAssignment_1 )? ) )
            // InternalStructuredTextParser.g:7139:1: ( ( rule__Variable__PartAssignment_1 )? )
            {
            // InternalStructuredTextParser.g:7139:1: ( ( rule__Variable__PartAssignment_1 )? )
            // InternalStructuredTextParser.g:7140:2: ( rule__Variable__PartAssignment_1 )?
            {
             before(grammarAccess.getVariableAccess().getPartAssignment_1()); 
            // InternalStructuredTextParser.g:7141:2: ( rule__Variable__PartAssignment_1 )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( ((LA58_0>=B && LA58_0<=X)||LA58_0==FullStop) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // InternalStructuredTextParser.g:7141:3: rule__Variable__PartAssignment_1
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
    // InternalStructuredTextParser.g:7150:1: rule__Variable_Subscript__Group__0 : rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1 ;
    public final void rule__Variable_Subscript__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7154:1: ( rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1 )
            // InternalStructuredTextParser.g:7155:2: rule__Variable_Subscript__Group__0__Impl rule__Variable_Subscript__Group__1
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
    // InternalStructuredTextParser.g:7162:1: rule__Variable_Subscript__Group__0__Impl : ( ( rule__Variable_Subscript__Alternatives_0 ) ) ;
    public final void rule__Variable_Subscript__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7166:1: ( ( ( rule__Variable_Subscript__Alternatives_0 ) ) )
            // InternalStructuredTextParser.g:7167:1: ( ( rule__Variable_Subscript__Alternatives_0 ) )
            {
            // InternalStructuredTextParser.g:7167:1: ( ( rule__Variable_Subscript__Alternatives_0 ) )
            // InternalStructuredTextParser.g:7168:2: ( rule__Variable_Subscript__Alternatives_0 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getAlternatives_0()); 
            // InternalStructuredTextParser.g:7169:2: ( rule__Variable_Subscript__Alternatives_0 )
            // InternalStructuredTextParser.g:7169:3: rule__Variable_Subscript__Alternatives_0
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
    // InternalStructuredTextParser.g:7177:1: rule__Variable_Subscript__Group__1 : rule__Variable_Subscript__Group__1__Impl ;
    public final void rule__Variable_Subscript__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7181:1: ( rule__Variable_Subscript__Group__1__Impl )
            // InternalStructuredTextParser.g:7182:2: rule__Variable_Subscript__Group__1__Impl
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
    // InternalStructuredTextParser.g:7188:1: rule__Variable_Subscript__Group__1__Impl : ( ( rule__Variable_Subscript__Group_1__0 )? ) ;
    public final void rule__Variable_Subscript__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7192:1: ( ( ( rule__Variable_Subscript__Group_1__0 )? ) )
            // InternalStructuredTextParser.g:7193:1: ( ( rule__Variable_Subscript__Group_1__0 )? )
            {
            // InternalStructuredTextParser.g:7193:1: ( ( rule__Variable_Subscript__Group_1__0 )? )
            // InternalStructuredTextParser.g:7194:2: ( rule__Variable_Subscript__Group_1__0 )?
            {
             before(grammarAccess.getVariable_SubscriptAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:7195:2: ( rule__Variable_Subscript__Group_1__0 )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==LeftSquareBracket) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalStructuredTextParser.g:7195:3: rule__Variable_Subscript__Group_1__0
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
    // InternalStructuredTextParser.g:7204:1: rule__Variable_Subscript__Group_1__0 : rule__Variable_Subscript__Group_1__0__Impl rule__Variable_Subscript__Group_1__1 ;
    public final void rule__Variable_Subscript__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7208:1: ( rule__Variable_Subscript__Group_1__0__Impl rule__Variable_Subscript__Group_1__1 )
            // InternalStructuredTextParser.g:7209:2: rule__Variable_Subscript__Group_1__0__Impl rule__Variable_Subscript__Group_1__1
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
    // InternalStructuredTextParser.g:7216:1: rule__Variable_Subscript__Group_1__0__Impl : ( () ) ;
    public final void rule__Variable_Subscript__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7220:1: ( ( () ) )
            // InternalStructuredTextParser.g:7221:1: ( () )
            {
            // InternalStructuredTextParser.g:7221:1: ( () )
            // InternalStructuredTextParser.g:7222:2: ()
            {
             before(grammarAccess.getVariable_SubscriptAccess().getArrayVariableArrayAction_1_0()); 
            // InternalStructuredTextParser.g:7223:2: ()
            // InternalStructuredTextParser.g:7223:3: 
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
    // InternalStructuredTextParser.g:7231:1: rule__Variable_Subscript__Group_1__1 : rule__Variable_Subscript__Group_1__1__Impl rule__Variable_Subscript__Group_1__2 ;
    public final void rule__Variable_Subscript__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7235:1: ( rule__Variable_Subscript__Group_1__1__Impl rule__Variable_Subscript__Group_1__2 )
            // InternalStructuredTextParser.g:7236:2: rule__Variable_Subscript__Group_1__1__Impl rule__Variable_Subscript__Group_1__2
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
    // InternalStructuredTextParser.g:7243:1: rule__Variable_Subscript__Group_1__1__Impl : ( LeftSquareBracket ) ;
    public final void rule__Variable_Subscript__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7247:1: ( ( LeftSquareBracket ) )
            // InternalStructuredTextParser.g:7248:1: ( LeftSquareBracket )
            {
            // InternalStructuredTextParser.g:7248:1: ( LeftSquareBracket )
            // InternalStructuredTextParser.g:7249:2: LeftSquareBracket
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
    // InternalStructuredTextParser.g:7258:1: rule__Variable_Subscript__Group_1__2 : rule__Variable_Subscript__Group_1__2__Impl rule__Variable_Subscript__Group_1__3 ;
    public final void rule__Variable_Subscript__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7262:1: ( rule__Variable_Subscript__Group_1__2__Impl rule__Variable_Subscript__Group_1__3 )
            // InternalStructuredTextParser.g:7263:2: rule__Variable_Subscript__Group_1__2__Impl rule__Variable_Subscript__Group_1__3
            {
            pushFollow(FOLLOW_65);
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
    // InternalStructuredTextParser.g:7270:1: rule__Variable_Subscript__Group_1__2__Impl : ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) ) ;
    public final void rule__Variable_Subscript__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7274:1: ( ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:7275:1: ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:7275:1: ( ( rule__Variable_Subscript__IndexAssignment_1_2 ) )
            // InternalStructuredTextParser.g:7276:2: ( rule__Variable_Subscript__IndexAssignment_1_2 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_2()); 
            // InternalStructuredTextParser.g:7277:2: ( rule__Variable_Subscript__IndexAssignment_1_2 )
            // InternalStructuredTextParser.g:7277:3: rule__Variable_Subscript__IndexAssignment_1_2
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
    // InternalStructuredTextParser.g:7285:1: rule__Variable_Subscript__Group_1__3 : rule__Variable_Subscript__Group_1__3__Impl rule__Variable_Subscript__Group_1__4 ;
    public final void rule__Variable_Subscript__Group_1__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7289:1: ( rule__Variable_Subscript__Group_1__3__Impl rule__Variable_Subscript__Group_1__4 )
            // InternalStructuredTextParser.g:7290:2: rule__Variable_Subscript__Group_1__3__Impl rule__Variable_Subscript__Group_1__4
            {
            pushFollow(FOLLOW_65);
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
    // InternalStructuredTextParser.g:7297:1: rule__Variable_Subscript__Group_1__3__Impl : ( ( rule__Variable_Subscript__Group_1_3__0 )* ) ;
    public final void rule__Variable_Subscript__Group_1__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7301:1: ( ( ( rule__Variable_Subscript__Group_1_3__0 )* ) )
            // InternalStructuredTextParser.g:7302:1: ( ( rule__Variable_Subscript__Group_1_3__0 )* )
            {
            // InternalStructuredTextParser.g:7302:1: ( ( rule__Variable_Subscript__Group_1_3__0 )* )
            // InternalStructuredTextParser.g:7303:2: ( rule__Variable_Subscript__Group_1_3__0 )*
            {
             before(grammarAccess.getVariable_SubscriptAccess().getGroup_1_3()); 
            // InternalStructuredTextParser.g:7304:2: ( rule__Variable_Subscript__Group_1_3__0 )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==Comma) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // InternalStructuredTextParser.g:7304:3: rule__Variable_Subscript__Group_1_3__0
            	    {
            	    pushFollow(FOLLOW_29);
            	    rule__Variable_Subscript__Group_1_3__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop60;
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
    // InternalStructuredTextParser.g:7312:1: rule__Variable_Subscript__Group_1__4 : rule__Variable_Subscript__Group_1__4__Impl ;
    public final void rule__Variable_Subscript__Group_1__4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7316:1: ( rule__Variable_Subscript__Group_1__4__Impl )
            // InternalStructuredTextParser.g:7317:2: rule__Variable_Subscript__Group_1__4__Impl
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
    // InternalStructuredTextParser.g:7323:1: rule__Variable_Subscript__Group_1__4__Impl : ( RightSquareBracket ) ;
    public final void rule__Variable_Subscript__Group_1__4__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7327:1: ( ( RightSquareBracket ) )
            // InternalStructuredTextParser.g:7328:1: ( RightSquareBracket )
            {
            // InternalStructuredTextParser.g:7328:1: ( RightSquareBracket )
            // InternalStructuredTextParser.g:7329:2: RightSquareBracket
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
    // InternalStructuredTextParser.g:7339:1: rule__Variable_Subscript__Group_1_3__0 : rule__Variable_Subscript__Group_1_3__0__Impl rule__Variable_Subscript__Group_1_3__1 ;
    public final void rule__Variable_Subscript__Group_1_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7343:1: ( rule__Variable_Subscript__Group_1_3__0__Impl rule__Variable_Subscript__Group_1_3__1 )
            // InternalStructuredTextParser.g:7344:2: rule__Variable_Subscript__Group_1_3__0__Impl rule__Variable_Subscript__Group_1_3__1
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
    // InternalStructuredTextParser.g:7351:1: rule__Variable_Subscript__Group_1_3__0__Impl : ( Comma ) ;
    public final void rule__Variable_Subscript__Group_1_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7355:1: ( ( Comma ) )
            // InternalStructuredTextParser.g:7356:1: ( Comma )
            {
            // InternalStructuredTextParser.g:7356:1: ( Comma )
            // InternalStructuredTextParser.g:7357:2: Comma
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
    // InternalStructuredTextParser.g:7366:1: rule__Variable_Subscript__Group_1_3__1 : rule__Variable_Subscript__Group_1_3__1__Impl ;
    public final void rule__Variable_Subscript__Group_1_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7370:1: ( rule__Variable_Subscript__Group_1_3__1__Impl )
            // InternalStructuredTextParser.g:7371:2: rule__Variable_Subscript__Group_1_3__1__Impl
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
    // InternalStructuredTextParser.g:7377:1: rule__Variable_Subscript__Group_1_3__1__Impl : ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) ) ;
    public final void rule__Variable_Subscript__Group_1_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7381:1: ( ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) ) )
            // InternalStructuredTextParser.g:7382:1: ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) )
            {
            // InternalStructuredTextParser.g:7382:1: ( ( rule__Variable_Subscript__IndexAssignment_1_3_1 ) )
            // InternalStructuredTextParser.g:7383:2: ( rule__Variable_Subscript__IndexAssignment_1_3_1 )
            {
             before(grammarAccess.getVariable_SubscriptAccess().getIndexAssignment_1_3_1()); 
            // InternalStructuredTextParser.g:7384:2: ( rule__Variable_Subscript__IndexAssignment_1_3_1 )
            // InternalStructuredTextParser.g:7384:3: rule__Variable_Subscript__IndexAssignment_1_3_1
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
    // InternalStructuredTextParser.g:7393:1: rule__Variable_Adapter__Group__0 : rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1 ;
    public final void rule__Variable_Adapter__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7397:1: ( rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1 )
            // InternalStructuredTextParser.g:7398:2: rule__Variable_Adapter__Group__0__Impl rule__Variable_Adapter__Group__1
            {
            pushFollow(FOLLOW_25);
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
    // InternalStructuredTextParser.g:7405:1: rule__Variable_Adapter__Group__0__Impl : ( ruleAdapterRoot ) ;
    public final void rule__Variable_Adapter__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7409:1: ( ( ruleAdapterRoot ) )
            // InternalStructuredTextParser.g:7410:1: ( ruleAdapterRoot )
            {
            // InternalStructuredTextParser.g:7410:1: ( ruleAdapterRoot )
            // InternalStructuredTextParser.g:7411:2: ruleAdapterRoot
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
    // InternalStructuredTextParser.g:7420:1: rule__Variable_Adapter__Group__1 : rule__Variable_Adapter__Group__1__Impl ;
    public final void rule__Variable_Adapter__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7424:1: ( rule__Variable_Adapter__Group__1__Impl )
            // InternalStructuredTextParser.g:7425:2: rule__Variable_Adapter__Group__1__Impl
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
    // InternalStructuredTextParser.g:7431:1: rule__Variable_Adapter__Group__1__Impl : ( ( ( rule__Variable_Adapter__Group_1__0 ) ) ( ( rule__Variable_Adapter__Group_1__0 )* ) ) ;
    public final void rule__Variable_Adapter__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7435:1: ( ( ( ( rule__Variable_Adapter__Group_1__0 ) ) ( ( rule__Variable_Adapter__Group_1__0 )* ) ) )
            // InternalStructuredTextParser.g:7436:1: ( ( ( rule__Variable_Adapter__Group_1__0 ) ) ( ( rule__Variable_Adapter__Group_1__0 )* ) )
            {
            // InternalStructuredTextParser.g:7436:1: ( ( ( rule__Variable_Adapter__Group_1__0 ) ) ( ( rule__Variable_Adapter__Group_1__0 )* ) )
            // InternalStructuredTextParser.g:7437:2: ( ( rule__Variable_Adapter__Group_1__0 ) ) ( ( rule__Variable_Adapter__Group_1__0 )* )
            {
            // InternalStructuredTextParser.g:7437:2: ( ( rule__Variable_Adapter__Group_1__0 ) )
            // InternalStructuredTextParser.g:7438:3: ( rule__Variable_Adapter__Group_1__0 )
            {
             before(grammarAccess.getVariable_AdapterAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:7439:3: ( rule__Variable_Adapter__Group_1__0 )
            // InternalStructuredTextParser.g:7439:4: rule__Variable_Adapter__Group_1__0
            {
            pushFollow(FOLLOW_66);
            rule__Variable_Adapter__Group_1__0();

            state._fsp--;


            }

             after(grammarAccess.getVariable_AdapterAccess().getGroup_1()); 

            }

            // InternalStructuredTextParser.g:7442:2: ( ( rule__Variable_Adapter__Group_1__0 )* )
            // InternalStructuredTextParser.g:7443:3: ( rule__Variable_Adapter__Group_1__0 )*
            {
             before(grammarAccess.getVariable_AdapterAccess().getGroup_1()); 
            // InternalStructuredTextParser.g:7444:3: ( rule__Variable_Adapter__Group_1__0 )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==FullStop) ) {
                    int LA61_2 = input.LA(2);

                    if ( (LA61_2==DT||LA61_2==LT||LA61_2==T||LA61_2==RULE_ID) ) {
                        alt61=1;
                    }


                }


                switch (alt61) {
            	case 1 :
            	    // InternalStructuredTextParser.g:7444:4: rule__Variable_Adapter__Group_1__0
            	    {
            	    pushFollow(FOLLOW_66);
            	    rule__Variable_Adapter__Group_1__0();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop61;
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
    // InternalStructuredTextParser.g:7454:1: rule__Variable_Adapter__Group_1__0 : rule__Variable_Adapter__Group_1__0__Impl rule__Variable_Adapter__Group_1__1 ;
    public final void rule__Variable_Adapter__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7458:1: ( rule__Variable_Adapter__Group_1__0__Impl rule__Variable_Adapter__Group_1__1 )
            // InternalStructuredTextParser.g:7459:2: rule__Variable_Adapter__Group_1__0__Impl rule__Variable_Adapter__Group_1__1
            {
            pushFollow(FOLLOW_25);
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
    // InternalStructuredTextParser.g:7466:1: rule__Variable_Adapter__Group_1__0__Impl : ( () ) ;
    public final void rule__Variable_Adapter__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7470:1: ( ( () ) )
            // InternalStructuredTextParser.g:7471:1: ( () )
            {
            // InternalStructuredTextParser.g:7471:1: ( () )
            // InternalStructuredTextParser.g:7472:2: ()
            {
             before(grammarAccess.getVariable_AdapterAccess().getAdapterVariableCurrAction_1_0()); 
            // InternalStructuredTextParser.g:7473:2: ()
            // InternalStructuredTextParser.g:7473:3: 
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
    // InternalStructuredTextParser.g:7481:1: rule__Variable_Adapter__Group_1__1 : rule__Variable_Adapter__Group_1__1__Impl rule__Variable_Adapter__Group_1__2 ;
    public final void rule__Variable_Adapter__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7485:1: ( rule__Variable_Adapter__Group_1__1__Impl rule__Variable_Adapter__Group_1__2 )
            // InternalStructuredTextParser.g:7486:2: rule__Variable_Adapter__Group_1__1__Impl rule__Variable_Adapter__Group_1__2
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
    // InternalStructuredTextParser.g:7493:1: rule__Variable_Adapter__Group_1__1__Impl : ( FullStop ) ;
    public final void rule__Variable_Adapter__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7497:1: ( ( FullStop ) )
            // InternalStructuredTextParser.g:7498:1: ( FullStop )
            {
            // InternalStructuredTextParser.g:7498:1: ( FullStop )
            // InternalStructuredTextParser.g:7499:2: FullStop
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
    // InternalStructuredTextParser.g:7508:1: rule__Variable_Adapter__Group_1__2 : rule__Variable_Adapter__Group_1__2__Impl ;
    public final void rule__Variable_Adapter__Group_1__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7512:1: ( rule__Variable_Adapter__Group_1__2__Impl )
            // InternalStructuredTextParser.g:7513:2: rule__Variable_Adapter__Group_1__2__Impl
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
    // InternalStructuredTextParser.g:7519:1: rule__Variable_Adapter__Group_1__2__Impl : ( ( rule__Variable_Adapter__VarAssignment_1_2 ) ) ;
    public final void rule__Variable_Adapter__Group_1__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7523:1: ( ( ( rule__Variable_Adapter__VarAssignment_1_2 ) ) )
            // InternalStructuredTextParser.g:7524:1: ( ( rule__Variable_Adapter__VarAssignment_1_2 ) )
            {
            // InternalStructuredTextParser.g:7524:1: ( ( rule__Variable_Adapter__VarAssignment_1_2 ) )
            // InternalStructuredTextParser.g:7525:2: ( rule__Variable_Adapter__VarAssignment_1_2 )
            {
             before(grammarAccess.getVariable_AdapterAccess().getVarAssignment_1_2()); 
            // InternalStructuredTextParser.g:7526:2: ( rule__Variable_Adapter__VarAssignment_1_2 )
            // InternalStructuredTextParser.g:7526:3: rule__Variable_Adapter__VarAssignment_1_2
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
    // InternalStructuredTextParser.g:7535:1: rule__AdapterRoot__Group__0 : rule__AdapterRoot__Group__0__Impl rule__AdapterRoot__Group__1 ;
    public final void rule__AdapterRoot__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7539:1: ( rule__AdapterRoot__Group__0__Impl rule__AdapterRoot__Group__1 )
            // InternalStructuredTextParser.g:7540:2: rule__AdapterRoot__Group__0__Impl rule__AdapterRoot__Group__1
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
    // InternalStructuredTextParser.g:7547:1: rule__AdapterRoot__Group__0__Impl : ( () ) ;
    public final void rule__AdapterRoot__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7551:1: ( ( () ) )
            // InternalStructuredTextParser.g:7552:1: ( () )
            {
            // InternalStructuredTextParser.g:7552:1: ( () )
            // InternalStructuredTextParser.g:7553:2: ()
            {
             before(grammarAccess.getAdapterRootAccess().getAdapterRootAction_0()); 
            // InternalStructuredTextParser.g:7554:2: ()
            // InternalStructuredTextParser.g:7554:3: 
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
    // InternalStructuredTextParser.g:7562:1: rule__AdapterRoot__Group__1 : rule__AdapterRoot__Group__1__Impl ;
    public final void rule__AdapterRoot__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7566:1: ( rule__AdapterRoot__Group__1__Impl )
            // InternalStructuredTextParser.g:7567:2: rule__AdapterRoot__Group__1__Impl
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
    // InternalStructuredTextParser.g:7573:1: rule__AdapterRoot__Group__1__Impl : ( ( rule__AdapterRoot__AdapterAssignment_1 ) ) ;
    public final void rule__AdapterRoot__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7577:1: ( ( ( rule__AdapterRoot__AdapterAssignment_1 ) ) )
            // InternalStructuredTextParser.g:7578:1: ( ( rule__AdapterRoot__AdapterAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:7578:1: ( ( rule__AdapterRoot__AdapterAssignment_1 ) )
            // InternalStructuredTextParser.g:7579:2: ( rule__AdapterRoot__AdapterAssignment_1 )
            {
             before(grammarAccess.getAdapterRootAccess().getAdapterAssignment_1()); 
            // InternalStructuredTextParser.g:7580:2: ( rule__AdapterRoot__AdapterAssignment_1 )
            // InternalStructuredTextParser.g:7580:3: rule__AdapterRoot__AdapterAssignment_1
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
    // InternalStructuredTextParser.g:7589:1: rule__Multibit_Part_Access__Group_0__0 : rule__Multibit_Part_Access__Group_0__0__Impl rule__Multibit_Part_Access__Group_0__1 ;
    public final void rule__Multibit_Part_Access__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7593:1: ( rule__Multibit_Part_Access__Group_0__0__Impl rule__Multibit_Part_Access__Group_0__1 )
            // InternalStructuredTextParser.g:7594:2: rule__Multibit_Part_Access__Group_0__0__Impl rule__Multibit_Part_Access__Group_0__1
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
    // InternalStructuredTextParser.g:7601:1: rule__Multibit_Part_Access__Group_0__0__Impl : ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7605:1: ( ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:7606:1: ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:7606:1: ( ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 ) )
            // InternalStructuredTextParser.g:7607:2: ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessAssignment_0_0()); 
            // InternalStructuredTextParser.g:7608:2: ( rule__Multibit_Part_Access__DwordaccessAssignment_0_0 )
            // InternalStructuredTextParser.g:7608:3: rule__Multibit_Part_Access__DwordaccessAssignment_0_0
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
    // InternalStructuredTextParser.g:7616:1: rule__Multibit_Part_Access__Group_0__1 : rule__Multibit_Part_Access__Group_0__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7620:1: ( rule__Multibit_Part_Access__Group_0__1__Impl )
            // InternalStructuredTextParser.g:7621:2: rule__Multibit_Part_Access__Group_0__1__Impl
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
    // InternalStructuredTextParser.g:7627:1: rule__Multibit_Part_Access__Group_0__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7631:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) ) )
            // InternalStructuredTextParser.g:7632:1: ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) )
            {
            // InternalStructuredTextParser.g:7632:1: ( ( rule__Multibit_Part_Access__IndexAssignment_0_1 ) )
            // InternalStructuredTextParser.g:7633:2: ( rule__Multibit_Part_Access__IndexAssignment_0_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_0_1()); 
            // InternalStructuredTextParser.g:7634:2: ( rule__Multibit_Part_Access__IndexAssignment_0_1 )
            // InternalStructuredTextParser.g:7634:3: rule__Multibit_Part_Access__IndexAssignment_0_1
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
    // InternalStructuredTextParser.g:7643:1: rule__Multibit_Part_Access__Group_1__0 : rule__Multibit_Part_Access__Group_1__0__Impl rule__Multibit_Part_Access__Group_1__1 ;
    public final void rule__Multibit_Part_Access__Group_1__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7647:1: ( rule__Multibit_Part_Access__Group_1__0__Impl rule__Multibit_Part_Access__Group_1__1 )
            // InternalStructuredTextParser.g:7648:2: rule__Multibit_Part_Access__Group_1__0__Impl rule__Multibit_Part_Access__Group_1__1
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
    // InternalStructuredTextParser.g:7655:1: rule__Multibit_Part_Access__Group_1__0__Impl : ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_1__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7659:1: ( ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) ) )
            // InternalStructuredTextParser.g:7660:1: ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) )
            {
            // InternalStructuredTextParser.g:7660:1: ( ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 ) )
            // InternalStructuredTextParser.g:7661:2: ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessAssignment_1_0()); 
            // InternalStructuredTextParser.g:7662:2: ( rule__Multibit_Part_Access__WordaccessAssignment_1_0 )
            // InternalStructuredTextParser.g:7662:3: rule__Multibit_Part_Access__WordaccessAssignment_1_0
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
    // InternalStructuredTextParser.g:7670:1: rule__Multibit_Part_Access__Group_1__1 : rule__Multibit_Part_Access__Group_1__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_1__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7674:1: ( rule__Multibit_Part_Access__Group_1__1__Impl )
            // InternalStructuredTextParser.g:7675:2: rule__Multibit_Part_Access__Group_1__1__Impl
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
    // InternalStructuredTextParser.g:7681:1: rule__Multibit_Part_Access__Group_1__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_1__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7685:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) ) )
            // InternalStructuredTextParser.g:7686:1: ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) )
            {
            // InternalStructuredTextParser.g:7686:1: ( ( rule__Multibit_Part_Access__IndexAssignment_1_1 ) )
            // InternalStructuredTextParser.g:7687:2: ( rule__Multibit_Part_Access__IndexAssignment_1_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_1_1()); 
            // InternalStructuredTextParser.g:7688:2: ( rule__Multibit_Part_Access__IndexAssignment_1_1 )
            // InternalStructuredTextParser.g:7688:3: rule__Multibit_Part_Access__IndexAssignment_1_1
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
    // InternalStructuredTextParser.g:7697:1: rule__Multibit_Part_Access__Group_2__0 : rule__Multibit_Part_Access__Group_2__0__Impl rule__Multibit_Part_Access__Group_2__1 ;
    public final void rule__Multibit_Part_Access__Group_2__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7701:1: ( rule__Multibit_Part_Access__Group_2__0__Impl rule__Multibit_Part_Access__Group_2__1 )
            // InternalStructuredTextParser.g:7702:2: rule__Multibit_Part_Access__Group_2__0__Impl rule__Multibit_Part_Access__Group_2__1
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
    // InternalStructuredTextParser.g:7709:1: rule__Multibit_Part_Access__Group_2__0__Impl : ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_2__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7713:1: ( ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) ) )
            // InternalStructuredTextParser.g:7714:1: ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) )
            {
            // InternalStructuredTextParser.g:7714:1: ( ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 ) )
            // InternalStructuredTextParser.g:7715:2: ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessAssignment_2_0()); 
            // InternalStructuredTextParser.g:7716:2: ( rule__Multibit_Part_Access__ByteaccessAssignment_2_0 )
            // InternalStructuredTextParser.g:7716:3: rule__Multibit_Part_Access__ByteaccessAssignment_2_0
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
    // InternalStructuredTextParser.g:7724:1: rule__Multibit_Part_Access__Group_2__1 : rule__Multibit_Part_Access__Group_2__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_2__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7728:1: ( rule__Multibit_Part_Access__Group_2__1__Impl )
            // InternalStructuredTextParser.g:7729:2: rule__Multibit_Part_Access__Group_2__1__Impl
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
    // InternalStructuredTextParser.g:7735:1: rule__Multibit_Part_Access__Group_2__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_2__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7739:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) ) )
            // InternalStructuredTextParser.g:7740:1: ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) )
            {
            // InternalStructuredTextParser.g:7740:1: ( ( rule__Multibit_Part_Access__IndexAssignment_2_1 ) )
            // InternalStructuredTextParser.g:7741:2: ( rule__Multibit_Part_Access__IndexAssignment_2_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_2_1()); 
            // InternalStructuredTextParser.g:7742:2: ( rule__Multibit_Part_Access__IndexAssignment_2_1 )
            // InternalStructuredTextParser.g:7742:3: rule__Multibit_Part_Access__IndexAssignment_2_1
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
    // InternalStructuredTextParser.g:7751:1: rule__Multibit_Part_Access__Group_3__0 : rule__Multibit_Part_Access__Group_3__0__Impl rule__Multibit_Part_Access__Group_3__1 ;
    public final void rule__Multibit_Part_Access__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7755:1: ( rule__Multibit_Part_Access__Group_3__0__Impl rule__Multibit_Part_Access__Group_3__1 )
            // InternalStructuredTextParser.g:7756:2: rule__Multibit_Part_Access__Group_3__0__Impl rule__Multibit_Part_Access__Group_3__1
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
    // InternalStructuredTextParser.g:7763:1: rule__Multibit_Part_Access__Group_3__0__Impl : ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7767:1: ( ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) ) )
            // InternalStructuredTextParser.g:7768:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) )
            {
            // InternalStructuredTextParser.g:7768:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 ) )
            // InternalStructuredTextParser.g:7769:2: ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_3_0()); 
            // InternalStructuredTextParser.g:7770:2: ( rule__Multibit_Part_Access__BitaccessAssignment_3_0 )
            // InternalStructuredTextParser.g:7770:3: rule__Multibit_Part_Access__BitaccessAssignment_3_0
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
    // InternalStructuredTextParser.g:7778:1: rule__Multibit_Part_Access__Group_3__1 : rule__Multibit_Part_Access__Group_3__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7782:1: ( rule__Multibit_Part_Access__Group_3__1__Impl )
            // InternalStructuredTextParser.g:7783:2: rule__Multibit_Part_Access__Group_3__1__Impl
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
    // InternalStructuredTextParser.g:7789:1: rule__Multibit_Part_Access__Group_3__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7793:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) ) )
            // InternalStructuredTextParser.g:7794:1: ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) )
            {
            // InternalStructuredTextParser.g:7794:1: ( ( rule__Multibit_Part_Access__IndexAssignment_3_1 ) )
            // InternalStructuredTextParser.g:7795:2: ( rule__Multibit_Part_Access__IndexAssignment_3_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_3_1()); 
            // InternalStructuredTextParser.g:7796:2: ( rule__Multibit_Part_Access__IndexAssignment_3_1 )
            // InternalStructuredTextParser.g:7796:3: rule__Multibit_Part_Access__IndexAssignment_3_1
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
    // InternalStructuredTextParser.g:7805:1: rule__Multibit_Part_Access__Group_4__0 : rule__Multibit_Part_Access__Group_4__0__Impl rule__Multibit_Part_Access__Group_4__1 ;
    public final void rule__Multibit_Part_Access__Group_4__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7809:1: ( rule__Multibit_Part_Access__Group_4__0__Impl rule__Multibit_Part_Access__Group_4__1 )
            // InternalStructuredTextParser.g:7810:2: rule__Multibit_Part_Access__Group_4__0__Impl rule__Multibit_Part_Access__Group_4__1
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
    // InternalStructuredTextParser.g:7817:1: rule__Multibit_Part_Access__Group_4__0__Impl : ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) ) ;
    public final void rule__Multibit_Part_Access__Group_4__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7821:1: ( ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) ) )
            // InternalStructuredTextParser.g:7822:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) )
            {
            // InternalStructuredTextParser.g:7822:1: ( ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 ) )
            // InternalStructuredTextParser.g:7823:2: ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessAssignment_4_0()); 
            // InternalStructuredTextParser.g:7824:2: ( rule__Multibit_Part_Access__BitaccessAssignment_4_0 )
            // InternalStructuredTextParser.g:7824:3: rule__Multibit_Part_Access__BitaccessAssignment_4_0
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
    // InternalStructuredTextParser.g:7832:1: rule__Multibit_Part_Access__Group_4__1 : rule__Multibit_Part_Access__Group_4__1__Impl ;
    public final void rule__Multibit_Part_Access__Group_4__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7836:1: ( rule__Multibit_Part_Access__Group_4__1__Impl )
            // InternalStructuredTextParser.g:7837:2: rule__Multibit_Part_Access__Group_4__1__Impl
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
    // InternalStructuredTextParser.g:7843:1: rule__Multibit_Part_Access__Group_4__1__Impl : ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) ) ;
    public final void rule__Multibit_Part_Access__Group_4__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7847:1: ( ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) ) )
            // InternalStructuredTextParser.g:7848:1: ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) )
            {
            // InternalStructuredTextParser.g:7848:1: ( ( rule__Multibit_Part_Access__IndexAssignment_4_1 ) )
            // InternalStructuredTextParser.g:7849:2: ( rule__Multibit_Part_Access__IndexAssignment_4_1 )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getIndexAssignment_4_1()); 
            // InternalStructuredTextParser.g:7850:2: ( rule__Multibit_Part_Access__IndexAssignment_4_1 )
            // InternalStructuredTextParser.g:7850:3: rule__Multibit_Part_Access__IndexAssignment_4_1
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
    // InternalStructuredTextParser.g:7859:1: rule__Int_Literal__Group__0 : rule__Int_Literal__Group__0__Impl rule__Int_Literal__Group__1 ;
    public final void rule__Int_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7863:1: ( rule__Int_Literal__Group__0__Impl rule__Int_Literal__Group__1 )
            // InternalStructuredTextParser.g:7864:2: rule__Int_Literal__Group__0__Impl rule__Int_Literal__Group__1
            {
            pushFollow(FOLLOW_67);
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
    // InternalStructuredTextParser.g:7871:1: rule__Int_Literal__Group__0__Impl : ( ( rule__Int_Literal__Group_0__0 )? ) ;
    public final void rule__Int_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7875:1: ( ( ( rule__Int_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:7876:1: ( ( rule__Int_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:7876:1: ( ( rule__Int_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:7877:2: ( rule__Int_Literal__Group_0__0 )?
            {
             before(grammarAccess.getInt_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:7878:2: ( rule__Int_Literal__Group_0__0 )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( ((LA62_0>=UDINT && LA62_0<=ULINT)||LA62_0==USINT||LA62_0==DINT||LA62_0==LINT||LA62_0==SINT||LA62_0==UINT||LA62_0==INT) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // InternalStructuredTextParser.g:7878:3: rule__Int_Literal__Group_0__0
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
    // InternalStructuredTextParser.g:7886:1: rule__Int_Literal__Group__1 : rule__Int_Literal__Group__1__Impl ;
    public final void rule__Int_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7890:1: ( rule__Int_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:7891:2: rule__Int_Literal__Group__1__Impl
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
    // InternalStructuredTextParser.g:7897:1: rule__Int_Literal__Group__1__Impl : ( ( rule__Int_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Int_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7901:1: ( ( ( rule__Int_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:7902:1: ( ( rule__Int_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:7902:1: ( ( rule__Int_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:7903:2: ( rule__Int_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getInt_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:7904:2: ( rule__Int_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:7904:3: rule__Int_Literal__ValueAssignment_1
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
    // InternalStructuredTextParser.g:7913:1: rule__Int_Literal__Group_0__0 : rule__Int_Literal__Group_0__0__Impl rule__Int_Literal__Group_0__1 ;
    public final void rule__Int_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7917:1: ( rule__Int_Literal__Group_0__0__Impl rule__Int_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:7918:2: rule__Int_Literal__Group_0__0__Impl rule__Int_Literal__Group_0__1
            {
            pushFollow(FOLLOW_68);
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
    // InternalStructuredTextParser.g:7925:1: rule__Int_Literal__Group_0__0__Impl : ( ( rule__Int_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Int_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7929:1: ( ( ( rule__Int_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:7930:1: ( ( rule__Int_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:7930:1: ( ( rule__Int_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:7931:2: ( rule__Int_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getInt_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:7932:2: ( rule__Int_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:7932:3: rule__Int_Literal__TypeAssignment_0_0
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
    // InternalStructuredTextParser.g:7940:1: rule__Int_Literal__Group_0__1 : rule__Int_Literal__Group_0__1__Impl ;
    public final void rule__Int_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7944:1: ( rule__Int_Literal__Group_0__1__Impl )
            // InternalStructuredTextParser.g:7945:2: rule__Int_Literal__Group_0__1__Impl
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
    // InternalStructuredTextParser.g:7951:1: rule__Int_Literal__Group_0__1__Impl : ( NumberSign ) ;
    public final void rule__Int_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7955:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:7956:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:7956:1: ( NumberSign )
            // InternalStructuredTextParser.g:7957:2: NumberSign
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
    // InternalStructuredTextParser.g:7967:1: rule__Signed_Int__Group__0 : rule__Signed_Int__Group__0__Impl rule__Signed_Int__Group__1 ;
    public final void rule__Signed_Int__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7971:1: ( rule__Signed_Int__Group__0__Impl rule__Signed_Int__Group__1 )
            // InternalStructuredTextParser.g:7972:2: rule__Signed_Int__Group__0__Impl rule__Signed_Int__Group__1
            {
            pushFollow(FOLLOW_69);
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
    // InternalStructuredTextParser.g:7979:1: rule__Signed_Int__Group__0__Impl : ( ( rule__Signed_Int__Alternatives_0 )? ) ;
    public final void rule__Signed_Int__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7983:1: ( ( ( rule__Signed_Int__Alternatives_0 )? ) )
            // InternalStructuredTextParser.g:7984:1: ( ( rule__Signed_Int__Alternatives_0 )? )
            {
            // InternalStructuredTextParser.g:7984:1: ( ( rule__Signed_Int__Alternatives_0 )? )
            // InternalStructuredTextParser.g:7985:2: ( rule__Signed_Int__Alternatives_0 )?
            {
             before(grammarAccess.getSigned_IntAccess().getAlternatives_0()); 
            // InternalStructuredTextParser.g:7986:2: ( rule__Signed_Int__Alternatives_0 )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==PlusSign||LA63_0==HyphenMinus) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalStructuredTextParser.g:7986:3: rule__Signed_Int__Alternatives_0
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
    // InternalStructuredTextParser.g:7994:1: rule__Signed_Int__Group__1 : rule__Signed_Int__Group__1__Impl ;
    public final void rule__Signed_Int__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:7998:1: ( rule__Signed_Int__Group__1__Impl )
            // InternalStructuredTextParser.g:7999:2: rule__Signed_Int__Group__1__Impl
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
    // InternalStructuredTextParser.g:8005:1: rule__Signed_Int__Group__1__Impl : ( RULE_UNSIGNED_INT ) ;
    public final void rule__Signed_Int__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8009:1: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:8010:1: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:8010:1: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:8011:2: RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:8021:1: rule__Real_Literal__Group__0 : rule__Real_Literal__Group__0__Impl rule__Real_Literal__Group__1 ;
    public final void rule__Real_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8025:1: ( rule__Real_Literal__Group__0__Impl rule__Real_Literal__Group__1 )
            // InternalStructuredTextParser.g:8026:2: rule__Real_Literal__Group__0__Impl rule__Real_Literal__Group__1
            {
            pushFollow(FOLLOW_70);
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
    // InternalStructuredTextParser.g:8033:1: rule__Real_Literal__Group__0__Impl : ( ( rule__Real_Literal__Group_0__0 )? ) ;
    public final void rule__Real_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8037:1: ( ( ( rule__Real_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:8038:1: ( ( rule__Real_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:8038:1: ( ( rule__Real_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:8039:2: ( rule__Real_Literal__Group_0__0 )?
            {
             before(grammarAccess.getReal_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:8040:2: ( rule__Real_Literal__Group_0__0 )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==LREAL||LA64_0==REAL) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalStructuredTextParser.g:8040:3: rule__Real_Literal__Group_0__0
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
    // InternalStructuredTextParser.g:8048:1: rule__Real_Literal__Group__1 : rule__Real_Literal__Group__1__Impl ;
    public final void rule__Real_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8052:1: ( rule__Real_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:8053:2: rule__Real_Literal__Group__1__Impl
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
    // InternalStructuredTextParser.g:8059:1: rule__Real_Literal__Group__1__Impl : ( ( rule__Real_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Real_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8063:1: ( ( ( rule__Real_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:8064:1: ( ( rule__Real_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:8064:1: ( ( rule__Real_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:8065:2: ( rule__Real_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getReal_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:8066:2: ( rule__Real_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:8066:3: rule__Real_Literal__ValueAssignment_1
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
    // InternalStructuredTextParser.g:8075:1: rule__Real_Literal__Group_0__0 : rule__Real_Literal__Group_0__0__Impl rule__Real_Literal__Group_0__1 ;
    public final void rule__Real_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8079:1: ( rule__Real_Literal__Group_0__0__Impl rule__Real_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:8080:2: rule__Real_Literal__Group_0__0__Impl rule__Real_Literal__Group_0__1
            {
            pushFollow(FOLLOW_68);
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
    // InternalStructuredTextParser.g:8087:1: rule__Real_Literal__Group_0__0__Impl : ( ( rule__Real_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Real_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8091:1: ( ( ( rule__Real_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:8092:1: ( ( rule__Real_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:8092:1: ( ( rule__Real_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:8093:2: ( rule__Real_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getReal_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:8094:2: ( rule__Real_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:8094:3: rule__Real_Literal__TypeAssignment_0_0
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
    // InternalStructuredTextParser.g:8102:1: rule__Real_Literal__Group_0__1 : rule__Real_Literal__Group_0__1__Impl ;
    public final void rule__Real_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8106:1: ( rule__Real_Literal__Group_0__1__Impl )
            // InternalStructuredTextParser.g:8107:2: rule__Real_Literal__Group_0__1__Impl
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
    // InternalStructuredTextParser.g:8113:1: rule__Real_Literal__Group_0__1__Impl : ( NumberSign ) ;
    public final void rule__Real_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8117:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:8118:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:8118:1: ( NumberSign )
            // InternalStructuredTextParser.g:8119:2: NumberSign
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
    // InternalStructuredTextParser.g:8129:1: rule__Real_Value__Group__0 : rule__Real_Value__Group__0__Impl rule__Real_Value__Group__1 ;
    public final void rule__Real_Value__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8133:1: ( rule__Real_Value__Group__0__Impl rule__Real_Value__Group__1 )
            // InternalStructuredTextParser.g:8134:2: rule__Real_Value__Group__0__Impl rule__Real_Value__Group__1
            {
            pushFollow(FOLLOW_25);
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
    // InternalStructuredTextParser.g:8141:1: rule__Real_Value__Group__0__Impl : ( ruleSigned_Int ) ;
    public final void rule__Real_Value__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8145:1: ( ( ruleSigned_Int ) )
            // InternalStructuredTextParser.g:8146:1: ( ruleSigned_Int )
            {
            // InternalStructuredTextParser.g:8146:1: ( ruleSigned_Int )
            // InternalStructuredTextParser.g:8147:2: ruleSigned_Int
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
    // InternalStructuredTextParser.g:8156:1: rule__Real_Value__Group__1 : rule__Real_Value__Group__1__Impl rule__Real_Value__Group__2 ;
    public final void rule__Real_Value__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8160:1: ( rule__Real_Value__Group__1__Impl rule__Real_Value__Group__2 )
            // InternalStructuredTextParser.g:8161:2: rule__Real_Value__Group__1__Impl rule__Real_Value__Group__2
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
    // InternalStructuredTextParser.g:8168:1: rule__Real_Value__Group__1__Impl : ( FullStop ) ;
    public final void rule__Real_Value__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8172:1: ( ( FullStop ) )
            // InternalStructuredTextParser.g:8173:1: ( FullStop )
            {
            // InternalStructuredTextParser.g:8173:1: ( FullStop )
            // InternalStructuredTextParser.g:8174:2: FullStop
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
    // InternalStructuredTextParser.g:8183:1: rule__Real_Value__Group__2 : rule__Real_Value__Group__2__Impl rule__Real_Value__Group__3 ;
    public final void rule__Real_Value__Group__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8187:1: ( rule__Real_Value__Group__2__Impl rule__Real_Value__Group__3 )
            // InternalStructuredTextParser.g:8188:2: rule__Real_Value__Group__2__Impl rule__Real_Value__Group__3
            {
            pushFollow(FOLLOW_71);
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
    // InternalStructuredTextParser.g:8195:1: rule__Real_Value__Group__2__Impl : ( RULE_UNSIGNED_INT ) ;
    public final void rule__Real_Value__Group__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8199:1: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:8200:1: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:8200:1: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:8201:2: RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:8210:1: rule__Real_Value__Group__3 : rule__Real_Value__Group__3__Impl ;
    public final void rule__Real_Value__Group__3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8214:1: ( rule__Real_Value__Group__3__Impl )
            // InternalStructuredTextParser.g:8215:2: rule__Real_Value__Group__3__Impl
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
    // InternalStructuredTextParser.g:8221:1: rule__Real_Value__Group__3__Impl : ( ( rule__Real_Value__Group_3__0 )? ) ;
    public final void rule__Real_Value__Group__3__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8225:1: ( ( ( rule__Real_Value__Group_3__0 )? ) )
            // InternalStructuredTextParser.g:8226:1: ( ( rule__Real_Value__Group_3__0 )? )
            {
            // InternalStructuredTextParser.g:8226:1: ( ( rule__Real_Value__Group_3__0 )? )
            // InternalStructuredTextParser.g:8227:2: ( rule__Real_Value__Group_3__0 )?
            {
             before(grammarAccess.getReal_ValueAccess().getGroup_3()); 
            // InternalStructuredTextParser.g:8228:2: ( rule__Real_Value__Group_3__0 )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==E) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalStructuredTextParser.g:8228:3: rule__Real_Value__Group_3__0
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
    // InternalStructuredTextParser.g:8237:1: rule__Real_Value__Group_3__0 : rule__Real_Value__Group_3__0__Impl rule__Real_Value__Group_3__1 ;
    public final void rule__Real_Value__Group_3__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8241:1: ( rule__Real_Value__Group_3__0__Impl rule__Real_Value__Group_3__1 )
            // InternalStructuredTextParser.g:8242:2: rule__Real_Value__Group_3__0__Impl rule__Real_Value__Group_3__1
            {
            pushFollow(FOLLOW_69);
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
    // InternalStructuredTextParser.g:8249:1: rule__Real_Value__Group_3__0__Impl : ( E ) ;
    public final void rule__Real_Value__Group_3__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8253:1: ( ( E ) )
            // InternalStructuredTextParser.g:8254:1: ( E )
            {
            // InternalStructuredTextParser.g:8254:1: ( E )
            // InternalStructuredTextParser.g:8255:2: E
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
    // InternalStructuredTextParser.g:8264:1: rule__Real_Value__Group_3__1 : rule__Real_Value__Group_3__1__Impl ;
    public final void rule__Real_Value__Group_3__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8268:1: ( rule__Real_Value__Group_3__1__Impl )
            // InternalStructuredTextParser.g:8269:2: rule__Real_Value__Group_3__1__Impl
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
    // InternalStructuredTextParser.g:8275:1: rule__Real_Value__Group_3__1__Impl : ( ruleSigned_Int ) ;
    public final void rule__Real_Value__Group_3__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8279:1: ( ( ruleSigned_Int ) )
            // InternalStructuredTextParser.g:8280:1: ( ruleSigned_Int )
            {
            // InternalStructuredTextParser.g:8280:1: ( ruleSigned_Int )
            // InternalStructuredTextParser.g:8281:2: ruleSigned_Int
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
    // InternalStructuredTextParser.g:8291:1: rule__Bool_Literal__Group__0 : rule__Bool_Literal__Group__0__Impl rule__Bool_Literal__Group__1 ;
    public final void rule__Bool_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8295:1: ( rule__Bool_Literal__Group__0__Impl rule__Bool_Literal__Group__1 )
            // InternalStructuredTextParser.g:8296:2: rule__Bool_Literal__Group__0__Impl rule__Bool_Literal__Group__1
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
    // InternalStructuredTextParser.g:8303:1: rule__Bool_Literal__Group__0__Impl : ( ( rule__Bool_Literal__Group_0__0 )? ) ;
    public final void rule__Bool_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8307:1: ( ( ( rule__Bool_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:8308:1: ( ( rule__Bool_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:8308:1: ( ( rule__Bool_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:8309:2: ( rule__Bool_Literal__Group_0__0 )?
            {
             before(grammarAccess.getBool_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:8310:2: ( rule__Bool_Literal__Group_0__0 )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==BOOL) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalStructuredTextParser.g:8310:3: rule__Bool_Literal__Group_0__0
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
    // InternalStructuredTextParser.g:8318:1: rule__Bool_Literal__Group__1 : rule__Bool_Literal__Group__1__Impl ;
    public final void rule__Bool_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8322:1: ( rule__Bool_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:8323:2: rule__Bool_Literal__Group__1__Impl
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
    // InternalStructuredTextParser.g:8329:1: rule__Bool_Literal__Group__1__Impl : ( ( rule__Bool_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Bool_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8333:1: ( ( ( rule__Bool_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:8334:1: ( ( rule__Bool_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:8334:1: ( ( rule__Bool_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:8335:2: ( rule__Bool_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getBool_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:8336:2: ( rule__Bool_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:8336:3: rule__Bool_Literal__ValueAssignment_1
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
    // InternalStructuredTextParser.g:8345:1: rule__Bool_Literal__Group_0__0 : rule__Bool_Literal__Group_0__0__Impl rule__Bool_Literal__Group_0__1 ;
    public final void rule__Bool_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8349:1: ( rule__Bool_Literal__Group_0__0__Impl rule__Bool_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:8350:2: rule__Bool_Literal__Group_0__0__Impl rule__Bool_Literal__Group_0__1
            {
            pushFollow(FOLLOW_68);
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
    // InternalStructuredTextParser.g:8357:1: rule__Bool_Literal__Group_0__0__Impl : ( ( rule__Bool_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Bool_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8361:1: ( ( ( rule__Bool_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:8362:1: ( ( rule__Bool_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:8362:1: ( ( rule__Bool_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:8363:2: ( rule__Bool_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getBool_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:8364:2: ( rule__Bool_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:8364:3: rule__Bool_Literal__TypeAssignment_0_0
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
    // InternalStructuredTextParser.g:8372:1: rule__Bool_Literal__Group_0__1 : rule__Bool_Literal__Group_0__1__Impl ;
    public final void rule__Bool_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8376:1: ( rule__Bool_Literal__Group_0__1__Impl )
            // InternalStructuredTextParser.g:8377:2: rule__Bool_Literal__Group_0__1__Impl
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
    // InternalStructuredTextParser.g:8383:1: rule__Bool_Literal__Group_0__1__Impl : ( NumberSign ) ;
    public final void rule__Bool_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8387:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:8388:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:8388:1: ( NumberSign )
            // InternalStructuredTextParser.g:8389:2: NumberSign
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
    // InternalStructuredTextParser.g:8399:1: rule__Char_Literal__Group__0 : rule__Char_Literal__Group__0__Impl rule__Char_Literal__Group__1 ;
    public final void rule__Char_Literal__Group__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8403:1: ( rule__Char_Literal__Group__0__Impl rule__Char_Literal__Group__1 )
            // InternalStructuredTextParser.g:8404:2: rule__Char_Literal__Group__0__Impl rule__Char_Literal__Group__1
            {
            pushFollow(FOLLOW_72);
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
    // InternalStructuredTextParser.g:8411:1: rule__Char_Literal__Group__0__Impl : ( ( rule__Char_Literal__Group_0__0 )? ) ;
    public final void rule__Char_Literal__Group__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8415:1: ( ( ( rule__Char_Literal__Group_0__0 )? ) )
            // InternalStructuredTextParser.g:8416:1: ( ( rule__Char_Literal__Group_0__0 )? )
            {
            // InternalStructuredTextParser.g:8416:1: ( ( rule__Char_Literal__Group_0__0 )? )
            // InternalStructuredTextParser.g:8417:2: ( rule__Char_Literal__Group_0__0 )?
            {
             before(grammarAccess.getChar_LiteralAccess().getGroup_0()); 
            // InternalStructuredTextParser.g:8418:2: ( rule__Char_Literal__Group_0__0 )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==WSTRING||LA67_0==STRING||LA67_0==WCHAR||LA67_0==CHAR) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalStructuredTextParser.g:8418:3: rule__Char_Literal__Group_0__0
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
    // InternalStructuredTextParser.g:8426:1: rule__Char_Literal__Group__1 : rule__Char_Literal__Group__1__Impl ;
    public final void rule__Char_Literal__Group__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8430:1: ( rule__Char_Literal__Group__1__Impl )
            // InternalStructuredTextParser.g:8431:2: rule__Char_Literal__Group__1__Impl
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
    // InternalStructuredTextParser.g:8437:1: rule__Char_Literal__Group__1__Impl : ( ( rule__Char_Literal__ValueAssignment_1 ) ) ;
    public final void rule__Char_Literal__Group__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8441:1: ( ( ( rule__Char_Literal__ValueAssignment_1 ) ) )
            // InternalStructuredTextParser.g:8442:1: ( ( rule__Char_Literal__ValueAssignment_1 ) )
            {
            // InternalStructuredTextParser.g:8442:1: ( ( rule__Char_Literal__ValueAssignment_1 ) )
            // InternalStructuredTextParser.g:8443:2: ( rule__Char_Literal__ValueAssignment_1 )
            {
             before(grammarAccess.getChar_LiteralAccess().getValueAssignment_1()); 
            // InternalStructuredTextParser.g:8444:2: ( rule__Char_Literal__ValueAssignment_1 )
            // InternalStructuredTextParser.g:8444:3: rule__Char_Literal__ValueAssignment_1
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
    // InternalStructuredTextParser.g:8453:1: rule__Char_Literal__Group_0__0 : rule__Char_Literal__Group_0__0__Impl rule__Char_Literal__Group_0__1 ;
    public final void rule__Char_Literal__Group_0__0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8457:1: ( rule__Char_Literal__Group_0__0__Impl rule__Char_Literal__Group_0__1 )
            // InternalStructuredTextParser.g:8458:2: rule__Char_Literal__Group_0__0__Impl rule__Char_Literal__Group_0__1
            {
            pushFollow(FOLLOW_73);
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
    // InternalStructuredTextParser.g:8465:1: rule__Char_Literal__Group_0__0__Impl : ( ( rule__Char_Literal__TypeAssignment_0_0 ) ) ;
    public final void rule__Char_Literal__Group_0__0__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8469:1: ( ( ( rule__Char_Literal__TypeAssignment_0_0 ) ) )
            // InternalStructuredTextParser.g:8470:1: ( ( rule__Char_Literal__TypeAssignment_0_0 ) )
            {
            // InternalStructuredTextParser.g:8470:1: ( ( rule__Char_Literal__TypeAssignment_0_0 ) )
            // InternalStructuredTextParser.g:8471:2: ( rule__Char_Literal__TypeAssignment_0_0 )
            {
             before(grammarAccess.getChar_LiteralAccess().getTypeAssignment_0_0()); 
            // InternalStructuredTextParser.g:8472:2: ( rule__Char_Literal__TypeAssignment_0_0 )
            // InternalStructuredTextParser.g:8472:3: rule__Char_Literal__TypeAssignment_0_0
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
    // InternalStructuredTextParser.g:8480:1: rule__Char_Literal__Group_0__1 : rule__Char_Literal__Group_0__1__Impl rule__Char_Literal__Group_0__2 ;
    public final void rule__Char_Literal__Group_0__1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8484:1: ( rule__Char_Literal__Group_0__1__Impl rule__Char_Literal__Group_0__2 )
            // InternalStructuredTextParser.g:8485:2: rule__Char_Literal__Group_0__1__Impl rule__Char_Literal__Group_0__2
            {
            pushFollow(FOLLOW_73);
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
    // InternalStructuredTextParser.g:8492:1: rule__Char_Literal__Group_0__1__Impl : ( ( rule__Char_Literal__LengthAssignment_0_1 )? ) ;
    public final void rule__Char_Literal__Group_0__1__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8496:1: ( ( ( rule__Char_Literal__LengthAssignment_0_1 )? ) )
            // InternalStructuredTextParser.g:8497:1: ( ( rule__Char_Literal__LengthAssignment_0_1 )? )
            {
            // InternalStructuredTextParser.g:8497:1: ( ( rule__Char_Literal__LengthAssignment_0_1 )? )
            // InternalStructuredTextParser.g:8498:2: ( rule__Char_Literal__LengthAssignment_0_1 )?
            {
             before(grammarAccess.getChar_LiteralAccess().getLengthAssignment_0_1()); 
            // InternalStructuredTextParser.g:8499:2: ( rule__Char_Literal__LengthAssignment_0_1 )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==RULE_UNSIGNED_INT) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalStructuredTextParser.g:8499:3: rule__Char_Literal__LengthAssignment_0_1
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
    // InternalStructuredTextParser.g:8507:1: rule__Char_Literal__Group_0__2 : rule__Char_Literal__Group_0__2__Impl ;
    public final void rule__Char_Literal__Group_0__2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8511:1: ( rule__Char_Literal__Group_0__2__Impl )
            // InternalStructuredTextParser.g:8512:2: rule__Char_Literal__Group_0__2__Impl
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
    // InternalStructuredTextParser.g:8518:1: rule__Char_Literal__Group_0__2__Impl : ( NumberSign ) ;
    public final void rule__Char_Literal__Group_0__2__Impl() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8522:1: ( ( NumberSign ) )
            // InternalStructuredTextParser.g:8523:1: ( NumberSign )
            {
            // InternalStructuredTextParser.g:8523:1: ( NumberSign )
            // InternalStructuredTextParser.g:8524:2: NumberSign
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
    // InternalStructuredTextParser.g:8534:1: rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0 : ( ruleVar_Decl_Init ) ;
    public final void rule__StructuredTextAlgorithm__LocalVariablesAssignment_1_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8538:1: ( ( ruleVar_Decl_Init ) )
            // InternalStructuredTextParser.g:8539:2: ( ruleVar_Decl_Init )
            {
            // InternalStructuredTextParser.g:8539:2: ( ruleVar_Decl_Init )
            // InternalStructuredTextParser.g:8540:3: ruleVar_Decl_Init
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
    // InternalStructuredTextParser.g:8549:1: rule__StructuredTextAlgorithm__StatementsAssignment_2 : ( ruleStmt_List ) ;
    public final void rule__StructuredTextAlgorithm__StatementsAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8553:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8554:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8554:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8555:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8564:1: rule__Var_Decl_Local__ConstantAssignment_1 : ( ( CONSTANT ) ) ;
    public final void rule__Var_Decl_Local__ConstantAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8568:1: ( ( ( CONSTANT ) ) )
            // InternalStructuredTextParser.g:8569:2: ( ( CONSTANT ) )
            {
            // InternalStructuredTextParser.g:8569:2: ( ( CONSTANT ) )
            // InternalStructuredTextParser.g:8570:3: ( CONSTANT )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getConstantCONSTANTKeyword_1_0()); 
            // InternalStructuredTextParser.g:8571:3: ( CONSTANT )
            // InternalStructuredTextParser.g:8572:4: CONSTANT
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
    // InternalStructuredTextParser.g:8583:1: rule__Var_Decl_Local__NameAssignment_2 : ( RULE_ID ) ;
    public final void rule__Var_Decl_Local__NameAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8587:1: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:8588:2: ( RULE_ID )
            {
            // InternalStructuredTextParser.g:8588:2: ( RULE_ID )
            // InternalStructuredTextParser.g:8589:3: RULE_ID
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
    // InternalStructuredTextParser.g:8598:1: rule__Var_Decl_Local__LocatedAssignment_3_0 : ( ( AT ) ) ;
    public final void rule__Var_Decl_Local__LocatedAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8602:1: ( ( ( AT ) ) )
            // InternalStructuredTextParser.g:8603:2: ( ( AT ) )
            {
            // InternalStructuredTextParser.g:8603:2: ( ( AT ) )
            // InternalStructuredTextParser.g:8604:3: ( AT )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getLocatedATKeyword_3_0_0()); 
            // InternalStructuredTextParser.g:8605:3: ( AT )
            // InternalStructuredTextParser.g:8606:4: AT
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
    // InternalStructuredTextParser.g:8617:1: rule__Var_Decl_Local__LocationAssignment_3_1 : ( ruleVariable ) ;
    public final void rule__Var_Decl_Local__LocationAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8621:1: ( ( ruleVariable ) )
            // InternalStructuredTextParser.g:8622:2: ( ruleVariable )
            {
            // InternalStructuredTextParser.g:8622:2: ( ruleVariable )
            // InternalStructuredTextParser.g:8623:3: ruleVariable
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
    // InternalStructuredTextParser.g:8632:1: rule__Var_Decl_Local__ArrayAssignment_5_0 : ( ( ARRAY ) ) ;
    public final void rule__Var_Decl_Local__ArrayAssignment_5_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8636:1: ( ( ( ARRAY ) ) )
            // InternalStructuredTextParser.g:8637:2: ( ( ARRAY ) )
            {
            // InternalStructuredTextParser.g:8637:2: ( ( ARRAY ) )
            // InternalStructuredTextParser.g:8638:3: ( ARRAY )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getArrayARRAYKeyword_5_0_0()); 
            // InternalStructuredTextParser.g:8639:3: ( ARRAY )
            // InternalStructuredTextParser.g:8640:4: ARRAY
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
    // InternalStructuredTextParser.g:8651:1: rule__Var_Decl_Local__ArrayStartAssignment_5_2 : ( ruleArray_Size ) ;
    public final void rule__Var_Decl_Local__ArrayStartAssignment_5_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8655:1: ( ( ruleArray_Size ) )
            // InternalStructuredTextParser.g:8656:2: ( ruleArray_Size )
            {
            // InternalStructuredTextParser.g:8656:2: ( ruleArray_Size )
            // InternalStructuredTextParser.g:8657:3: ruleArray_Size
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
    // InternalStructuredTextParser.g:8666:1: rule__Var_Decl_Local__ArrayStopAssignment_5_4 : ( ruleArray_Size ) ;
    public final void rule__Var_Decl_Local__ArrayStopAssignment_5_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8670:1: ( ( ruleArray_Size ) )
            // InternalStructuredTextParser.g:8671:2: ( ruleArray_Size )
            {
            // InternalStructuredTextParser.g:8671:2: ( ruleArray_Size )
            // InternalStructuredTextParser.g:8672:3: ruleArray_Size
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
    // InternalStructuredTextParser.g:8681:1: rule__Var_Decl_Local__TypeAssignment_6 : ( ( ruleType_Name ) ) ;
    public final void rule__Var_Decl_Local__TypeAssignment_6() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8685:1: ( ( ( ruleType_Name ) ) )
            // InternalStructuredTextParser.g:8686:2: ( ( ruleType_Name ) )
            {
            // InternalStructuredTextParser.g:8686:2: ( ( ruleType_Name ) )
            // InternalStructuredTextParser.g:8687:3: ( ruleType_Name )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getTypeDataTypeCrossReference_6_0()); 
            // InternalStructuredTextParser.g:8688:3: ( ruleType_Name )
            // InternalStructuredTextParser.g:8689:4: ruleType_Name
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
    // InternalStructuredTextParser.g:8700:1: rule__Var_Decl_Local__InitalizedAssignment_7_0 : ( ( ColonEqualsSign ) ) ;
    public final void rule__Var_Decl_Local__InitalizedAssignment_7_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8704:1: ( ( ( ColonEqualsSign ) ) )
            // InternalStructuredTextParser.g:8705:2: ( ( ColonEqualsSign ) )
            {
            // InternalStructuredTextParser.g:8705:2: ( ( ColonEqualsSign ) )
            // InternalStructuredTextParser.g:8706:3: ( ColonEqualsSign )
            {
             before(grammarAccess.getVar_Decl_LocalAccess().getInitalizedColonEqualsSignKeyword_7_0_0()); 
            // InternalStructuredTextParser.g:8707:3: ( ColonEqualsSign )
            // InternalStructuredTextParser.g:8708:4: ColonEqualsSign
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
    // InternalStructuredTextParser.g:8719:1: rule__Var_Decl_Local__InitialValueAssignment_7_1 : ( ruleConstant ) ;
    public final void rule__Var_Decl_Local__InitialValueAssignment_7_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8723:1: ( ( ruleConstant ) )
            // InternalStructuredTextParser.g:8724:2: ( ruleConstant )
            {
            // InternalStructuredTextParser.g:8724:2: ( ruleConstant )
            // InternalStructuredTextParser.g:8725:3: ruleConstant
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
    // InternalStructuredTextParser.g:8734:1: rule__Stmt_List__StatementsAssignment_1_0 : ( ruleStmt ) ;
    public final void rule__Stmt_List__StatementsAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8738:1: ( ( ruleStmt ) )
            // InternalStructuredTextParser.g:8739:2: ( ruleStmt )
            {
            // InternalStructuredTextParser.g:8739:2: ( ruleStmt )
            // InternalStructuredTextParser.g:8740:3: ruleStmt
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
    // InternalStructuredTextParser.g:8749:1: rule__Assign_Stmt__VariableAssignment_0 : ( ruleVariable ) ;
    public final void rule__Assign_Stmt__VariableAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8753:1: ( ( ruleVariable ) )
            // InternalStructuredTextParser.g:8754:2: ( ruleVariable )
            {
            // InternalStructuredTextParser.g:8754:2: ( ruleVariable )
            // InternalStructuredTextParser.g:8755:3: ruleVariable
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
    // InternalStructuredTextParser.g:8764:1: rule__Assign_Stmt__ExpressionAssignment_2 : ( ruleExpression ) ;
    public final void rule__Assign_Stmt__ExpressionAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8768:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8769:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8769:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8770:3: ruleExpression
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


    // $ANTLR start "rule__FB_Call__FbAssignment_0"
    // InternalStructuredTextParser.g:8779:1: rule__FB_Call__FbAssignment_0 : ( ( RULE_ID ) ) ;
    public final void rule__FB_Call__FbAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8783:1: ( ( ( RULE_ID ) ) )
            // InternalStructuredTextParser.g:8784:2: ( ( RULE_ID ) )
            {
            // InternalStructuredTextParser.g:8784:2: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:8785:3: ( RULE_ID )
            {
             before(grammarAccess.getFB_CallAccess().getFbFBCrossReference_0_0()); 
            // InternalStructuredTextParser.g:8786:3: ( RULE_ID )
            // InternalStructuredTextParser.g:8787:4: RULE_ID
            {
             before(grammarAccess.getFB_CallAccess().getFbFBIDTerminalRuleCall_0_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getFB_CallAccess().getFbFBIDTerminalRuleCall_0_0_1()); 

            }

             after(grammarAccess.getFB_CallAccess().getFbFBCrossReference_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__FbAssignment_0"


    // $ANTLR start "rule__FB_Call__EventAssignment_2"
    // InternalStructuredTextParser.g:8798:1: rule__FB_Call__EventAssignment_2 : ( ( RULE_ID ) ) ;
    public final void rule__FB_Call__EventAssignment_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8802:1: ( ( ( RULE_ID ) ) )
            // InternalStructuredTextParser.g:8803:2: ( ( RULE_ID ) )
            {
            // InternalStructuredTextParser.g:8803:2: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:8804:3: ( RULE_ID )
            {
             before(grammarAccess.getFB_CallAccess().getEventEventCrossReference_2_0()); 
            // InternalStructuredTextParser.g:8805:3: ( RULE_ID )
            // InternalStructuredTextParser.g:8806:4: RULE_ID
            {
             before(grammarAccess.getFB_CallAccess().getEventEventIDTerminalRuleCall_2_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getFB_CallAccess().getEventEventIDTerminalRuleCall_2_0_1()); 

            }

             after(grammarAccess.getFB_CallAccess().getEventEventCrossReference_2_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__EventAssignment_2"


    // $ANTLR start "rule__FB_Call__ArgsAssignment_4_0"
    // InternalStructuredTextParser.g:8817:1: rule__FB_Call__ArgsAssignment_4_0 : ( ruleParam_Assign ) ;
    public final void rule__FB_Call__ArgsAssignment_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8821:1: ( ( ruleParam_Assign ) )
            // InternalStructuredTextParser.g:8822:2: ( ruleParam_Assign )
            {
            // InternalStructuredTextParser.g:8822:2: ( ruleParam_Assign )
            // InternalStructuredTextParser.g:8823:3: ruleParam_Assign
            {
             before(grammarAccess.getFB_CallAccess().getArgsParam_AssignParserRuleCall_4_0_0()); 
            pushFollow(FOLLOW_2);
            ruleParam_Assign();

            state._fsp--;

             after(grammarAccess.getFB_CallAccess().getArgsParam_AssignParserRuleCall_4_0_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__ArgsAssignment_4_0"


    // $ANTLR start "rule__FB_Call__ArgsAssignment_4_1_1"
    // InternalStructuredTextParser.g:8832:1: rule__FB_Call__ArgsAssignment_4_1_1 : ( ruleParam_Assign ) ;
    public final void rule__FB_Call__ArgsAssignment_4_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8836:1: ( ( ruleParam_Assign ) )
            // InternalStructuredTextParser.g:8837:2: ( ruleParam_Assign )
            {
            // InternalStructuredTextParser.g:8837:2: ( ruleParam_Assign )
            // InternalStructuredTextParser.g:8838:3: ruleParam_Assign
            {
             before(grammarAccess.getFB_CallAccess().getArgsParam_AssignParserRuleCall_4_1_1_0()); 
            pushFollow(FOLLOW_2);
            ruleParam_Assign();

            state._fsp--;

             after(grammarAccess.getFB_CallAccess().getArgsParam_AssignParserRuleCall_4_1_1_0()); 

            }


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }
        finally {

            	restoreStackSize(stackSize);

        }
        return ;
    }
    // $ANTLR end "rule__FB_Call__ArgsAssignment_4_1_1"


    // $ANTLR start "rule__IF_Stmt__ExpressionAssignment_1"
    // InternalStructuredTextParser.g:8847:1: rule__IF_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__IF_Stmt__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8851:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8852:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8852:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8853:3: ruleExpression
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
    // InternalStructuredTextParser.g:8862:1: rule__IF_Stmt__StatmentsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__IF_Stmt__StatmentsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8866:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8867:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8867:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8868:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8877:1: rule__IF_Stmt__ElseifAssignment_4 : ( ruleELSIF_Clause ) ;
    public final void rule__IF_Stmt__ElseifAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8881:1: ( ( ruleELSIF_Clause ) )
            // InternalStructuredTextParser.g:8882:2: ( ruleELSIF_Clause )
            {
            // InternalStructuredTextParser.g:8882:2: ( ruleELSIF_Clause )
            // InternalStructuredTextParser.g:8883:3: ruleELSIF_Clause
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
    // InternalStructuredTextParser.g:8892:1: rule__IF_Stmt__ElseAssignment_5 : ( ruleELSE_Clause ) ;
    public final void rule__IF_Stmt__ElseAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8896:1: ( ( ruleELSE_Clause ) )
            // InternalStructuredTextParser.g:8897:2: ( ruleELSE_Clause )
            {
            // InternalStructuredTextParser.g:8897:2: ( ruleELSE_Clause )
            // InternalStructuredTextParser.g:8898:3: ruleELSE_Clause
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
    // InternalStructuredTextParser.g:8907:1: rule__ELSIF_Clause__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__ELSIF_Clause__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8911:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8912:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8912:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8913:3: ruleExpression
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
    // InternalStructuredTextParser.g:8922:1: rule__ELSIF_Clause__StatementsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__ELSIF_Clause__StatementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8926:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8927:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8927:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8928:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8937:1: rule__ELSE_Clause__StatementsAssignment_1 : ( ruleStmt_List ) ;
    public final void rule__ELSE_Clause__StatementsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8941:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:8942:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:8942:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:8943:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:8952:1: rule__Case_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__Case_Stmt__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8956:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:8957:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:8957:2: ( ruleExpression )
            // InternalStructuredTextParser.g:8958:3: ruleExpression
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
    // InternalStructuredTextParser.g:8967:1: rule__Case_Stmt__CaseAssignment_3 : ( ruleCase_Selection ) ;
    public final void rule__Case_Stmt__CaseAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8971:1: ( ( ruleCase_Selection ) )
            // InternalStructuredTextParser.g:8972:2: ( ruleCase_Selection )
            {
            // InternalStructuredTextParser.g:8972:2: ( ruleCase_Selection )
            // InternalStructuredTextParser.g:8973:3: ruleCase_Selection
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
    // InternalStructuredTextParser.g:8982:1: rule__Case_Stmt__ElseAssignment_4 : ( ruleELSE_Clause ) ;
    public final void rule__Case_Stmt__ElseAssignment_4() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:8986:1: ( ( ruleELSE_Clause ) )
            // InternalStructuredTextParser.g:8987:2: ( ruleELSE_Clause )
            {
            // InternalStructuredTextParser.g:8987:2: ( ruleELSE_Clause )
            // InternalStructuredTextParser.g:8988:3: ruleELSE_Clause
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
    // InternalStructuredTextParser.g:8997:1: rule__Case_Selection__CaseAssignment_0 : ( ruleConstant ) ;
    public final void rule__Case_Selection__CaseAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9001:1: ( ( ruleConstant ) )
            // InternalStructuredTextParser.g:9002:2: ( ruleConstant )
            {
            // InternalStructuredTextParser.g:9002:2: ( ruleConstant )
            // InternalStructuredTextParser.g:9003:3: ruleConstant
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
    // InternalStructuredTextParser.g:9012:1: rule__Case_Selection__CaseAssignment_1_1 : ( ruleConstant ) ;
    public final void rule__Case_Selection__CaseAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9016:1: ( ( ruleConstant ) )
            // InternalStructuredTextParser.g:9017:2: ( ruleConstant )
            {
            // InternalStructuredTextParser.g:9017:2: ( ruleConstant )
            // InternalStructuredTextParser.g:9018:3: ruleConstant
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
    // InternalStructuredTextParser.g:9027:1: rule__Case_Selection__StatementsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__Case_Selection__StatementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9031:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:9032:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:9032:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:9033:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:9042:1: rule__For_Stmt__VariableAssignment_1 : ( ruleVariable_Primary ) ;
    public final void rule__For_Stmt__VariableAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9046:1: ( ( ruleVariable_Primary ) )
            // InternalStructuredTextParser.g:9047:2: ( ruleVariable_Primary )
            {
            // InternalStructuredTextParser.g:9047:2: ( ruleVariable_Primary )
            // InternalStructuredTextParser.g:9048:3: ruleVariable_Primary
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
    // InternalStructuredTextParser.g:9057:1: rule__For_Stmt__FromAssignment_3 : ( ruleExpression ) ;
    public final void rule__For_Stmt__FromAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9061:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9062:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9062:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9063:3: ruleExpression
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
    // InternalStructuredTextParser.g:9072:1: rule__For_Stmt__ToAssignment_5 : ( ruleExpression ) ;
    public final void rule__For_Stmt__ToAssignment_5() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9076:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9077:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9077:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9078:3: ruleExpression
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
    // InternalStructuredTextParser.g:9087:1: rule__For_Stmt__ByAssignment_6_1 : ( ruleExpression ) ;
    public final void rule__For_Stmt__ByAssignment_6_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9091:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9092:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9092:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9093:3: ruleExpression
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
    // InternalStructuredTextParser.g:9102:1: rule__For_Stmt__StatementsAssignment_8 : ( ruleStmt_List ) ;
    public final void rule__For_Stmt__StatementsAssignment_8() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9106:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:9107:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:9107:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:9108:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:9117:1: rule__While_Stmt__ExpressionAssignment_1 : ( ruleExpression ) ;
    public final void rule__While_Stmt__ExpressionAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9121:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9122:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9122:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9123:3: ruleExpression
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
    // InternalStructuredTextParser.g:9132:1: rule__While_Stmt__StatementsAssignment_3 : ( ruleStmt_List ) ;
    public final void rule__While_Stmt__StatementsAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9136:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:9137:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:9137:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:9138:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:9147:1: rule__Repeat_Stmt__StatementsAssignment_1 : ( ruleStmt_List ) ;
    public final void rule__Repeat_Stmt__StatementsAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9151:1: ( ( ruleStmt_List ) )
            // InternalStructuredTextParser.g:9152:2: ( ruleStmt_List )
            {
            // InternalStructuredTextParser.g:9152:2: ( ruleStmt_List )
            // InternalStructuredTextParser.g:9153:3: ruleStmt_List
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
    // InternalStructuredTextParser.g:9162:1: rule__Repeat_Stmt__ExpressionAssignment_3 : ( ruleExpression ) ;
    public final void rule__Repeat_Stmt__ExpressionAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9166:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9167:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9167:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9168:3: ruleExpression
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
    // InternalStructuredTextParser.g:9177:1: rule__Or_Expression__OperatorAssignment_1_1 : ( ruleOr_Operator ) ;
    public final void rule__Or_Expression__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9181:1: ( ( ruleOr_Operator ) )
            // InternalStructuredTextParser.g:9182:2: ( ruleOr_Operator )
            {
            // InternalStructuredTextParser.g:9182:2: ( ruleOr_Operator )
            // InternalStructuredTextParser.g:9183:3: ruleOr_Operator
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
    // InternalStructuredTextParser.g:9192:1: rule__Or_Expression__RightAssignment_1_2 : ( ruleXor_Expr ) ;
    public final void rule__Or_Expression__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9196:1: ( ( ruleXor_Expr ) )
            // InternalStructuredTextParser.g:9197:2: ( ruleXor_Expr )
            {
            // InternalStructuredTextParser.g:9197:2: ( ruleXor_Expr )
            // InternalStructuredTextParser.g:9198:3: ruleXor_Expr
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
    // InternalStructuredTextParser.g:9207:1: rule__Xor_Expr__OperatorAssignment_1_1 : ( ruleXor_Operator ) ;
    public final void rule__Xor_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9211:1: ( ( ruleXor_Operator ) )
            // InternalStructuredTextParser.g:9212:2: ( ruleXor_Operator )
            {
            // InternalStructuredTextParser.g:9212:2: ( ruleXor_Operator )
            // InternalStructuredTextParser.g:9213:3: ruleXor_Operator
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
    // InternalStructuredTextParser.g:9222:1: rule__Xor_Expr__RightAssignment_1_2 : ( ruleAnd_Expr ) ;
    public final void rule__Xor_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9226:1: ( ( ruleAnd_Expr ) )
            // InternalStructuredTextParser.g:9227:2: ( ruleAnd_Expr )
            {
            // InternalStructuredTextParser.g:9227:2: ( ruleAnd_Expr )
            // InternalStructuredTextParser.g:9228:3: ruleAnd_Expr
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
    // InternalStructuredTextParser.g:9237:1: rule__And_Expr__OperatorAssignment_1_1 : ( ruleAnd_Operator ) ;
    public final void rule__And_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9241:1: ( ( ruleAnd_Operator ) )
            // InternalStructuredTextParser.g:9242:2: ( ruleAnd_Operator )
            {
            // InternalStructuredTextParser.g:9242:2: ( ruleAnd_Operator )
            // InternalStructuredTextParser.g:9243:3: ruleAnd_Operator
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
    // InternalStructuredTextParser.g:9252:1: rule__And_Expr__RightAssignment_1_2 : ( ruleCompare_Expr ) ;
    public final void rule__And_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9256:1: ( ( ruleCompare_Expr ) )
            // InternalStructuredTextParser.g:9257:2: ( ruleCompare_Expr )
            {
            // InternalStructuredTextParser.g:9257:2: ( ruleCompare_Expr )
            // InternalStructuredTextParser.g:9258:3: ruleCompare_Expr
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
    // InternalStructuredTextParser.g:9267:1: rule__Compare_Expr__OperatorAssignment_1_1 : ( ruleCompare_Operator ) ;
    public final void rule__Compare_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9271:1: ( ( ruleCompare_Operator ) )
            // InternalStructuredTextParser.g:9272:2: ( ruleCompare_Operator )
            {
            // InternalStructuredTextParser.g:9272:2: ( ruleCompare_Operator )
            // InternalStructuredTextParser.g:9273:3: ruleCompare_Operator
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
    // InternalStructuredTextParser.g:9282:1: rule__Compare_Expr__RightAssignment_1_2 : ( ruleEqu_Expr ) ;
    public final void rule__Compare_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9286:1: ( ( ruleEqu_Expr ) )
            // InternalStructuredTextParser.g:9287:2: ( ruleEqu_Expr )
            {
            // InternalStructuredTextParser.g:9287:2: ( ruleEqu_Expr )
            // InternalStructuredTextParser.g:9288:3: ruleEqu_Expr
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
    // InternalStructuredTextParser.g:9297:1: rule__Equ_Expr__OperatorAssignment_1_1 : ( ruleEqu_Operator ) ;
    public final void rule__Equ_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9301:1: ( ( ruleEqu_Operator ) )
            // InternalStructuredTextParser.g:9302:2: ( ruleEqu_Operator )
            {
            // InternalStructuredTextParser.g:9302:2: ( ruleEqu_Operator )
            // InternalStructuredTextParser.g:9303:3: ruleEqu_Operator
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
    // InternalStructuredTextParser.g:9312:1: rule__Equ_Expr__RightAssignment_1_2 : ( ruleAdd_Expr ) ;
    public final void rule__Equ_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9316:1: ( ( ruleAdd_Expr ) )
            // InternalStructuredTextParser.g:9317:2: ( ruleAdd_Expr )
            {
            // InternalStructuredTextParser.g:9317:2: ( ruleAdd_Expr )
            // InternalStructuredTextParser.g:9318:3: ruleAdd_Expr
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
    // InternalStructuredTextParser.g:9327:1: rule__Add_Expr__OperatorAssignment_1_1 : ( ruleAdd_Operator ) ;
    public final void rule__Add_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9331:1: ( ( ruleAdd_Operator ) )
            // InternalStructuredTextParser.g:9332:2: ( ruleAdd_Operator )
            {
            // InternalStructuredTextParser.g:9332:2: ( ruleAdd_Operator )
            // InternalStructuredTextParser.g:9333:3: ruleAdd_Operator
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
    // InternalStructuredTextParser.g:9342:1: rule__Add_Expr__RightAssignment_1_2 : ( ruleTerm ) ;
    public final void rule__Add_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9346:1: ( ( ruleTerm ) )
            // InternalStructuredTextParser.g:9347:2: ( ruleTerm )
            {
            // InternalStructuredTextParser.g:9347:2: ( ruleTerm )
            // InternalStructuredTextParser.g:9348:3: ruleTerm
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
    // InternalStructuredTextParser.g:9357:1: rule__Term__OperatorAssignment_1_1 : ( ruleTerm_Operator ) ;
    public final void rule__Term__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9361:1: ( ( ruleTerm_Operator ) )
            // InternalStructuredTextParser.g:9362:2: ( ruleTerm_Operator )
            {
            // InternalStructuredTextParser.g:9362:2: ( ruleTerm_Operator )
            // InternalStructuredTextParser.g:9363:3: ruleTerm_Operator
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
    // InternalStructuredTextParser.g:9372:1: rule__Term__RightAssignment_1_2 : ( rulePower_Expr ) ;
    public final void rule__Term__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9376:1: ( ( rulePower_Expr ) )
            // InternalStructuredTextParser.g:9377:2: ( rulePower_Expr )
            {
            // InternalStructuredTextParser.g:9377:2: ( rulePower_Expr )
            // InternalStructuredTextParser.g:9378:3: rulePower_Expr
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
    // InternalStructuredTextParser.g:9387:1: rule__Power_Expr__OperatorAssignment_1_1 : ( rulePower_Operator ) ;
    public final void rule__Power_Expr__OperatorAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9391:1: ( ( rulePower_Operator ) )
            // InternalStructuredTextParser.g:9392:2: ( rulePower_Operator )
            {
            // InternalStructuredTextParser.g:9392:2: ( rulePower_Operator )
            // InternalStructuredTextParser.g:9393:3: rulePower_Operator
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
    // InternalStructuredTextParser.g:9402:1: rule__Power_Expr__RightAssignment_1_2 : ( ruleUnary_Expr ) ;
    public final void rule__Power_Expr__RightAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9406:1: ( ( ruleUnary_Expr ) )
            // InternalStructuredTextParser.g:9407:2: ( ruleUnary_Expr )
            {
            // InternalStructuredTextParser.g:9407:2: ( ruleUnary_Expr )
            // InternalStructuredTextParser.g:9408:3: ruleUnary_Expr
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
    // InternalStructuredTextParser.g:9417:1: rule__Unary_Expr__OperatorAssignment_0_1 : ( ruleUnary_Operator ) ;
    public final void rule__Unary_Expr__OperatorAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9421:1: ( ( ruleUnary_Operator ) )
            // InternalStructuredTextParser.g:9422:2: ( ruleUnary_Operator )
            {
            // InternalStructuredTextParser.g:9422:2: ( ruleUnary_Operator )
            // InternalStructuredTextParser.g:9423:3: ruleUnary_Operator
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
    // InternalStructuredTextParser.g:9432:1: rule__Unary_Expr__ExpressionAssignment_0_2 : ( rulePrimary_Expr ) ;
    public final void rule__Unary_Expr__ExpressionAssignment_0_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9436:1: ( ( rulePrimary_Expr ) )
            // InternalStructuredTextParser.g:9437:2: ( rulePrimary_Expr )
            {
            // InternalStructuredTextParser.g:9437:2: ( rulePrimary_Expr )
            // InternalStructuredTextParser.g:9438:3: rulePrimary_Expr
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
    // InternalStructuredTextParser.g:9447:1: rule__Func_Call__FuncAssignment_0 : ( ( rule__Func_Call__FuncAlternatives_0_0 ) ) ;
    public final void rule__Func_Call__FuncAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9451:1: ( ( ( rule__Func_Call__FuncAlternatives_0_0 ) ) )
            // InternalStructuredTextParser.g:9452:2: ( ( rule__Func_Call__FuncAlternatives_0_0 ) )
            {
            // InternalStructuredTextParser.g:9452:2: ( ( rule__Func_Call__FuncAlternatives_0_0 ) )
            // InternalStructuredTextParser.g:9453:3: ( rule__Func_Call__FuncAlternatives_0_0 )
            {
             before(grammarAccess.getFunc_CallAccess().getFuncAlternatives_0_0()); 
            // InternalStructuredTextParser.g:9454:3: ( rule__Func_Call__FuncAlternatives_0_0 )
            // InternalStructuredTextParser.g:9454:4: rule__Func_Call__FuncAlternatives_0_0
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
    // InternalStructuredTextParser.g:9462:1: rule__Func_Call__ArgsAssignment_2_0 : ( ruleParam_Assign ) ;
    public final void rule__Func_Call__ArgsAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9466:1: ( ( ruleParam_Assign ) )
            // InternalStructuredTextParser.g:9467:2: ( ruleParam_Assign )
            {
            // InternalStructuredTextParser.g:9467:2: ( ruleParam_Assign )
            // InternalStructuredTextParser.g:9468:3: ruleParam_Assign
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
    // InternalStructuredTextParser.g:9477:1: rule__Func_Call__ArgsAssignment_2_1_1 : ( ruleParam_Assign ) ;
    public final void rule__Func_Call__ArgsAssignment_2_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9481:1: ( ( ruleParam_Assign ) )
            // InternalStructuredTextParser.g:9482:2: ( ruleParam_Assign )
            {
            // InternalStructuredTextParser.g:9482:2: ( ruleParam_Assign )
            // InternalStructuredTextParser.g:9483:3: ruleParam_Assign
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
    // InternalStructuredTextParser.g:9492:1: rule__Param_Assign_In__VarAssignment_0_0 : ( ( RULE_ID ) ) ;
    public final void rule__Param_Assign_In__VarAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9496:1: ( ( ( RULE_ID ) ) )
            // InternalStructuredTextParser.g:9497:2: ( ( RULE_ID ) )
            {
            // InternalStructuredTextParser.g:9497:2: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:9498:3: ( RULE_ID )
            {
             before(grammarAccess.getParam_Assign_InAccess().getVarVarDeclarationCrossReference_0_0_0()); 
            // InternalStructuredTextParser.g:9499:3: ( RULE_ID )
            // InternalStructuredTextParser.g:9500:4: RULE_ID
            {
             before(grammarAccess.getParam_Assign_InAccess().getVarVarDeclarationIDTerminalRuleCall_0_0_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getParam_Assign_InAccess().getVarVarDeclarationIDTerminalRuleCall_0_0_0_1()); 

            }

             after(grammarAccess.getParam_Assign_InAccess().getVarVarDeclarationCrossReference_0_0_0()); 

            }


            }

        }
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
    // InternalStructuredTextParser.g:9511:1: rule__Param_Assign_In__ExprAssignment_1 : ( ruleExpression ) ;
    public final void rule__Param_Assign_In__ExprAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9515:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9516:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9516:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9517:3: ruleExpression
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
    // InternalStructuredTextParser.g:9526:1: rule__Param_Assign_Out__NotAssignment_0 : ( ( NOT ) ) ;
    public final void rule__Param_Assign_Out__NotAssignment_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9530:1: ( ( ( NOT ) ) )
            // InternalStructuredTextParser.g:9531:2: ( ( NOT ) )
            {
            // InternalStructuredTextParser.g:9531:2: ( ( NOT ) )
            // InternalStructuredTextParser.g:9532:3: ( NOT )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getNotNOTKeyword_0_0()); 
            // InternalStructuredTextParser.g:9533:3: ( NOT )
            // InternalStructuredTextParser.g:9534:4: NOT
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
    // InternalStructuredTextParser.g:9545:1: rule__Param_Assign_Out__VarAssignment_1 : ( ( RULE_ID ) ) ;
    public final void rule__Param_Assign_Out__VarAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9549:1: ( ( ( RULE_ID ) ) )
            // InternalStructuredTextParser.g:9550:2: ( ( RULE_ID ) )
            {
            // InternalStructuredTextParser.g:9550:2: ( ( RULE_ID ) )
            // InternalStructuredTextParser.g:9551:3: ( RULE_ID )
            {
             before(grammarAccess.getParam_Assign_OutAccess().getVarVarDeclarationCrossReference_1_0()); 
            // InternalStructuredTextParser.g:9552:3: ( RULE_ID )
            // InternalStructuredTextParser.g:9553:4: RULE_ID
            {
             before(grammarAccess.getParam_Assign_OutAccess().getVarVarDeclarationIDTerminalRuleCall_1_0_1()); 
            match(input,RULE_ID,FOLLOW_2); 
             after(grammarAccess.getParam_Assign_OutAccess().getVarVarDeclarationIDTerminalRuleCall_1_0_1()); 

            }

             after(grammarAccess.getParam_Assign_OutAccess().getVarVarDeclarationCrossReference_1_0()); 

            }


            }

        }
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
    // InternalStructuredTextParser.g:9564:1: rule__Param_Assign_Out__ExprAssignment_3 : ( ruleVariable ) ;
    public final void rule__Param_Assign_Out__ExprAssignment_3() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9568:1: ( ( ruleVariable ) )
            // InternalStructuredTextParser.g:9569:2: ( ruleVariable )
            {
            // InternalStructuredTextParser.g:9569:2: ( ruleVariable )
            // InternalStructuredTextParser.g:9570:3: ruleVariable
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
    // InternalStructuredTextParser.g:9579:1: rule__Variable__PartAssignment_1 : ( ruleMultibit_Part_Access ) ;
    public final void rule__Variable__PartAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9583:1: ( ( ruleMultibit_Part_Access ) )
            // InternalStructuredTextParser.g:9584:2: ( ruleMultibit_Part_Access )
            {
            // InternalStructuredTextParser.g:9584:2: ( ruleMultibit_Part_Access )
            // InternalStructuredTextParser.g:9585:3: ruleMultibit_Part_Access
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
    // InternalStructuredTextParser.g:9594:1: rule__Variable_Subscript__IndexAssignment_1_2 : ( ruleExpression ) ;
    public final void rule__Variable_Subscript__IndexAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9598:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9599:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9599:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9600:3: ruleExpression
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
    // InternalStructuredTextParser.g:9609:1: rule__Variable_Subscript__IndexAssignment_1_3_1 : ( ruleExpression ) ;
    public final void rule__Variable_Subscript__IndexAssignment_1_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9613:1: ( ( ruleExpression ) )
            // InternalStructuredTextParser.g:9614:2: ( ruleExpression )
            {
            // InternalStructuredTextParser.g:9614:2: ( ruleExpression )
            // InternalStructuredTextParser.g:9615:3: ruleExpression
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
    // InternalStructuredTextParser.g:9624:1: rule__Variable_Adapter__VarAssignment_1_2 : ( ( ruleVariable_Name ) ) ;
    public final void rule__Variable_Adapter__VarAssignment_1_2() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9628:1: ( ( ( ruleVariable_Name ) ) )
            // InternalStructuredTextParser.g:9629:2: ( ( ruleVariable_Name ) )
            {
            // InternalStructuredTextParser.g:9629:2: ( ( ruleVariable_Name ) )
            // InternalStructuredTextParser.g:9630:3: ( ruleVariable_Name )
            {
             before(grammarAccess.getVariable_AdapterAccess().getVarVarDeclarationCrossReference_1_2_0()); 
            // InternalStructuredTextParser.g:9631:3: ( ruleVariable_Name )
            // InternalStructuredTextParser.g:9632:4: ruleVariable_Name
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
    // InternalStructuredTextParser.g:9643:1: rule__AdapterRoot__AdapterAssignment_1 : ( ( ruleAdapter_Name ) ) ;
    public final void rule__AdapterRoot__AdapterAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9647:1: ( ( ( ruleAdapter_Name ) ) )
            // InternalStructuredTextParser.g:9648:2: ( ( ruleAdapter_Name ) )
            {
            // InternalStructuredTextParser.g:9648:2: ( ( ruleAdapter_Name ) )
            // InternalStructuredTextParser.g:9649:3: ( ruleAdapter_Name )
            {
             before(grammarAccess.getAdapterRootAccess().getAdapterVarDeclarationCrossReference_1_0()); 
            // InternalStructuredTextParser.g:9650:3: ( ruleAdapter_Name )
            // InternalStructuredTextParser.g:9651:4: ruleAdapter_Name
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
    // InternalStructuredTextParser.g:9662:1: rule__Multibit_Part_Access__DwordaccessAssignment_0_0 : ( ( D ) ) ;
    public final void rule__Multibit_Part_Access__DwordaccessAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9666:1: ( ( ( D ) ) )
            // InternalStructuredTextParser.g:9667:2: ( ( D ) )
            {
            // InternalStructuredTextParser.g:9667:2: ( ( D ) )
            // InternalStructuredTextParser.g:9668:3: ( D )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getDwordaccessDKeyword_0_0_0()); 
            // InternalStructuredTextParser.g:9669:3: ( D )
            // InternalStructuredTextParser.g:9670:4: D
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
    // InternalStructuredTextParser.g:9681:1: rule__Multibit_Part_Access__IndexAssignment_0_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9685:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9686:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9686:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9687:3: rulePartial_Value
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
    // InternalStructuredTextParser.g:9696:1: rule__Multibit_Part_Access__WordaccessAssignment_1_0 : ( ( W ) ) ;
    public final void rule__Multibit_Part_Access__WordaccessAssignment_1_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9700:1: ( ( ( W ) ) )
            // InternalStructuredTextParser.g:9701:2: ( ( W ) )
            {
            // InternalStructuredTextParser.g:9701:2: ( ( W ) )
            // InternalStructuredTextParser.g:9702:3: ( W )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getWordaccessWKeyword_1_0_0()); 
            // InternalStructuredTextParser.g:9703:3: ( W )
            // InternalStructuredTextParser.g:9704:4: W
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
    // InternalStructuredTextParser.g:9715:1: rule__Multibit_Part_Access__IndexAssignment_1_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_1_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9719:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9720:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9720:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9721:3: rulePartial_Value
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
    // InternalStructuredTextParser.g:9730:1: rule__Multibit_Part_Access__ByteaccessAssignment_2_0 : ( ( B ) ) ;
    public final void rule__Multibit_Part_Access__ByteaccessAssignment_2_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9734:1: ( ( ( B ) ) )
            // InternalStructuredTextParser.g:9735:2: ( ( B ) )
            {
            // InternalStructuredTextParser.g:9735:2: ( ( B ) )
            // InternalStructuredTextParser.g:9736:3: ( B )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getByteaccessBKeyword_2_0_0()); 
            // InternalStructuredTextParser.g:9737:3: ( B )
            // InternalStructuredTextParser.g:9738:4: B
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
    // InternalStructuredTextParser.g:9749:1: rule__Multibit_Part_Access__IndexAssignment_2_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_2_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9753:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9754:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9754:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9755:3: rulePartial_Value
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
    // InternalStructuredTextParser.g:9764:1: rule__Multibit_Part_Access__BitaccessAssignment_3_0 : ( ( X ) ) ;
    public final void rule__Multibit_Part_Access__BitaccessAssignment_3_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9768:1: ( ( ( X ) ) )
            // InternalStructuredTextParser.g:9769:2: ( ( X ) )
            {
            // InternalStructuredTextParser.g:9769:2: ( ( X ) )
            // InternalStructuredTextParser.g:9770:3: ( X )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessXKeyword_3_0_0()); 
            // InternalStructuredTextParser.g:9771:3: ( X )
            // InternalStructuredTextParser.g:9772:4: X
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
    // InternalStructuredTextParser.g:9783:1: rule__Multibit_Part_Access__IndexAssignment_3_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_3_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9787:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9788:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9788:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9789:3: rulePartial_Value
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
    // InternalStructuredTextParser.g:9798:1: rule__Multibit_Part_Access__BitaccessAssignment_4_0 : ( ( FullStop ) ) ;
    public final void rule__Multibit_Part_Access__BitaccessAssignment_4_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9802:1: ( ( ( FullStop ) ) )
            // InternalStructuredTextParser.g:9803:2: ( ( FullStop ) )
            {
            // InternalStructuredTextParser.g:9803:2: ( ( FullStop ) )
            // InternalStructuredTextParser.g:9804:3: ( FullStop )
            {
             before(grammarAccess.getMultibit_Part_AccessAccess().getBitaccessFullStopKeyword_4_0_0()); 
            // InternalStructuredTextParser.g:9805:3: ( FullStop )
            // InternalStructuredTextParser.g:9806:4: FullStop
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
    // InternalStructuredTextParser.g:9817:1: rule__Multibit_Part_Access__IndexAssignment_4_1 : ( rulePartial_Value ) ;
    public final void rule__Multibit_Part_Access__IndexAssignment_4_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9821:1: ( ( rulePartial_Value ) )
            // InternalStructuredTextParser.g:9822:2: ( rulePartial_Value )
            {
            // InternalStructuredTextParser.g:9822:2: ( rulePartial_Value )
            // InternalStructuredTextParser.g:9823:3: rulePartial_Value
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
    // InternalStructuredTextParser.g:9832:1: rule__Variable_Primary__VarAssignment : ( ( ruleVariable_Name ) ) ;
    public final void rule__Variable_Primary__VarAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9836:1: ( ( ( ruleVariable_Name ) ) )
            // InternalStructuredTextParser.g:9837:2: ( ( ruleVariable_Name ) )
            {
            // InternalStructuredTextParser.g:9837:2: ( ( ruleVariable_Name ) )
            // InternalStructuredTextParser.g:9838:3: ( ruleVariable_Name )
            {
             before(grammarAccess.getVariable_PrimaryAccess().getVarVarDeclarationCrossReference_0()); 
            // InternalStructuredTextParser.g:9839:3: ( ruleVariable_Name )
            // InternalStructuredTextParser.g:9840:4: ruleVariable_Name
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
    // InternalStructuredTextParser.g:9851:1: rule__Int_Literal__TypeAssignment_0_0 : ( ruleInt_Type_Name ) ;
    public final void rule__Int_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9855:1: ( ( ruleInt_Type_Name ) )
            // InternalStructuredTextParser.g:9856:2: ( ruleInt_Type_Name )
            {
            // InternalStructuredTextParser.g:9856:2: ( ruleInt_Type_Name )
            // InternalStructuredTextParser.g:9857:3: ruleInt_Type_Name
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
    // InternalStructuredTextParser.g:9866:1: rule__Int_Literal__ValueAssignment_1 : ( ( rule__Int_Literal__ValueAlternatives_1_0 ) ) ;
    public final void rule__Int_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9870:1: ( ( ( rule__Int_Literal__ValueAlternatives_1_0 ) ) )
            // InternalStructuredTextParser.g:9871:2: ( ( rule__Int_Literal__ValueAlternatives_1_0 ) )
            {
            // InternalStructuredTextParser.g:9871:2: ( ( rule__Int_Literal__ValueAlternatives_1_0 ) )
            // InternalStructuredTextParser.g:9872:3: ( rule__Int_Literal__ValueAlternatives_1_0 )
            {
             before(grammarAccess.getInt_LiteralAccess().getValueAlternatives_1_0()); 
            // InternalStructuredTextParser.g:9873:3: ( rule__Int_Literal__ValueAlternatives_1_0 )
            // InternalStructuredTextParser.g:9873:4: rule__Int_Literal__ValueAlternatives_1_0
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
    // InternalStructuredTextParser.g:9881:1: rule__Real_Literal__TypeAssignment_0_0 : ( ruleReal_Type_Name ) ;
    public final void rule__Real_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9885:1: ( ( ruleReal_Type_Name ) )
            // InternalStructuredTextParser.g:9886:2: ( ruleReal_Type_Name )
            {
            // InternalStructuredTextParser.g:9886:2: ( ruleReal_Type_Name )
            // InternalStructuredTextParser.g:9887:3: ruleReal_Type_Name
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
    // InternalStructuredTextParser.g:9896:1: rule__Real_Literal__ValueAssignment_1 : ( ruleReal_Value ) ;
    public final void rule__Real_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9900:1: ( ( ruleReal_Value ) )
            // InternalStructuredTextParser.g:9901:2: ( ruleReal_Value )
            {
            // InternalStructuredTextParser.g:9901:2: ( ruleReal_Value )
            // InternalStructuredTextParser.g:9902:3: ruleReal_Value
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
    // InternalStructuredTextParser.g:9911:1: rule__Bool_Literal__TypeAssignment_0_0 : ( ruleBool_Type_Name ) ;
    public final void rule__Bool_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9915:1: ( ( ruleBool_Type_Name ) )
            // InternalStructuredTextParser.g:9916:2: ( ruleBool_Type_Name )
            {
            // InternalStructuredTextParser.g:9916:2: ( ruleBool_Type_Name )
            // InternalStructuredTextParser.g:9917:3: ruleBool_Type_Name
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
    // InternalStructuredTextParser.g:9926:1: rule__Bool_Literal__ValueAssignment_1 : ( ruleBool_Value ) ;
    public final void rule__Bool_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9930:1: ( ( ruleBool_Value ) )
            // InternalStructuredTextParser.g:9931:2: ( ruleBool_Value )
            {
            // InternalStructuredTextParser.g:9931:2: ( ruleBool_Value )
            // InternalStructuredTextParser.g:9932:3: ruleBool_Value
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
    // InternalStructuredTextParser.g:9941:1: rule__Char_Literal__TypeAssignment_0_0 : ( ruleString_Type_Name ) ;
    public final void rule__Char_Literal__TypeAssignment_0_0() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9945:1: ( ( ruleString_Type_Name ) )
            // InternalStructuredTextParser.g:9946:2: ( ruleString_Type_Name )
            {
            // InternalStructuredTextParser.g:9946:2: ( ruleString_Type_Name )
            // InternalStructuredTextParser.g:9947:3: ruleString_Type_Name
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
    // InternalStructuredTextParser.g:9956:1: rule__Char_Literal__LengthAssignment_0_1 : ( RULE_UNSIGNED_INT ) ;
    public final void rule__Char_Literal__LengthAssignment_0_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9960:1: ( ( RULE_UNSIGNED_INT ) )
            // InternalStructuredTextParser.g:9961:2: ( RULE_UNSIGNED_INT )
            {
            // InternalStructuredTextParser.g:9961:2: ( RULE_UNSIGNED_INT )
            // InternalStructuredTextParser.g:9962:3: RULE_UNSIGNED_INT
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
    // InternalStructuredTextParser.g:9971:1: rule__Char_Literal__ValueAssignment_1 : ( ( rule__Char_Literal__ValueAlternatives_1_0 ) ) ;
    public final void rule__Char_Literal__ValueAssignment_1() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9975:1: ( ( ( rule__Char_Literal__ValueAlternatives_1_0 ) ) )
            // InternalStructuredTextParser.g:9976:2: ( ( rule__Char_Literal__ValueAlternatives_1_0 ) )
            {
            // InternalStructuredTextParser.g:9976:2: ( ( rule__Char_Literal__ValueAlternatives_1_0 ) )
            // InternalStructuredTextParser.g:9977:3: ( rule__Char_Literal__ValueAlternatives_1_0 )
            {
             before(grammarAccess.getChar_LiteralAccess().getValueAlternatives_1_0()); 
            // InternalStructuredTextParser.g:9978:3: ( rule__Char_Literal__ValueAlternatives_1_0 )
            // InternalStructuredTextParser.g:9978:4: rule__Char_Literal__ValueAlternatives_1_0
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
    // InternalStructuredTextParser.g:9986:1: rule__Time_Literal__LiteralAssignment : ( ( rule__Time_Literal__LiteralAlternatives_0 ) ) ;
    public final void rule__Time_Literal__LiteralAssignment() throws RecognitionException {

        		int stackSize = keepStackSize();
        	
        try {
            // InternalStructuredTextParser.g:9990:1: ( ( ( rule__Time_Literal__LiteralAlternatives_0 ) ) )
            // InternalStructuredTextParser.g:9991:2: ( ( rule__Time_Literal__LiteralAlternatives_0 ) )
            {
            // InternalStructuredTextParser.g:9991:2: ( ( rule__Time_Literal__LiteralAlternatives_0 ) )
            // InternalStructuredTextParser.g:9992:3: ( rule__Time_Literal__LiteralAlternatives_0 )
            {
             before(grammarAccess.getTime_LiteralAccess().getLiteralAlternatives_0()); 
            // InternalStructuredTextParser.g:9993:3: ( rule__Time_Literal__LiteralAlternatives_0 )
            // InternalStructuredTextParser.g:9993:4: rule__Time_Literal__LiteralAlternatives_0
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
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x1407694B62888000L,0x0015FF0100AC1400L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000000002L,0x0000000000400000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000020000410000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000400002L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000020000001000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0406694B62888002L,0x0015DF0000A00000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000000000000000L,0x0000000004400000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0201042410060800L,0x0000200100001C00L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000300L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0000000000000100L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0000000000000002L,0x0000000000004000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x0100000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x0100000000000002L,0x0000000000020000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000010L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000002L,0x0000000020000010L});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000050000048L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000002L,0x0000000050000048L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0000000000A00000L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000000000000002L,0x0000000000A00000L});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0800000000000000L,0x0000000002100000L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0800000000000002L,0x0000000002100000L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000001L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x1000000000000000L,0x0000000000A00000L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0001000000000000L,0x0000200100041400L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x00F0000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000000000L,0x0000000400400000L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000002L,0x0000000001000000L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0404490160000000L,0x0001C10000A00000L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000010000A00000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0404690162000000L,0x0001C10000A00000L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000004200088000L,0x0014000000000000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000000L,0x0000010000010000L});

}